package com.fineinsight.zzango.questionnaire

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.fineinsight.zzango.questionnaire.LocalList.Paper_AGREE
import kotlinx.android.synthetic.main.activity_agreement.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class AgreementActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement)

        BtnSetting()
    }

    fun BtnSetting()
    {
        //전체동의
        agreeAll.setOnClickListener { agreeAll() }

        //전체비동의
        disAgreeAll.setOnClickListener { disAgreeAll() }

        //확인
        agreementSubmit.setOnClickListener {
            if (AgreeValidation())
            {
                var stream = ByteArrayOutputStream()

                var now = System.currentTimeMillis().toString()
                var AgreeInfo:Paper_AGREE = Paper_AGREE(
                        MainActivity.hospital,
                        now,
                        "",
                        now,
                        "",
                        "0",
                        if(noticeWarningAgree.isChecked) "Y" else "N",          //개인정보 최소정보 제공동의
                        if(beforeAfterInfoProvideAgree.isChecked) "Y" else "N", //건진실시 따른 사전사후 서비스 관련 정보 제공
                        if(mobileInfoAgree.isChecked) "Y" else "N",             //진료예약, 입원 및 검사예정에 따른 모바일 안내
                        if(hospitalEventInfoAgree.isChecked) "Y" else "N",      //병원이용 및 병원의 새로운 서비스, 행사정보안내
                        if(sendSMSAgree.isChecked) "Y" else "N",                //건강정보 발송을 위한 휴대폰 SMS 발송
                        if(MedicalCooperationInfoAgree.isChecked) "Y" else "N", //병원간의 상호 진료협력을 위해 의료정보 제공
                        if(patientInfoAgree.isChecked) "Y" else "N",            //환자대리인 정보이용 동의
                        if(uniqueInfoAgree.isChecked) "Y" else "N",             //고유식별 정보
                        if(sensitiveInfoAgree.isChecked) "Y" else "N",          //민감정보
                        "",
                        "",
                        "TESTNAME",
                        "9005051020123",
                        stream.toByteArray())




                var SERVER = "SERVER"
                var LOCAL = "LOCAL"

                var MODE = LOCAL

                if (MODE == SERVER)
                {
                    LocalUpload(AgreeInfo)
                }
                else
                {
                    ServerUpload(AgreeInfo)
                }


            }
            else
            {
                //전체동의해야 넘어갈 수 있음
                println("ㄴㄴ")
            }
        }
    }

    fun LocalUpload(AgreeInfo:Paper_AGREE)
    {
        var sql_db = LocalDBhelper(this).writableDatabase
        LocalDBhelper(this).AgreeSaveLocal(sql_db, AgreeInfo)

        println("ㅇㅋ")
    }


    fun ServerUpload(AgreeInfo:Paper_AGREE)
    {
        ProgressAction(true)


        OracleUtill().save_agreepaper().SaveAgreePapers(AgreeInfo).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {
                    if (!response.body()!!.equals("S")) {
                        println("실패")
                        ProgressAction(false)
                    } else {
                        println("성공")
                        ProgressAction(false)
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                ProgressAction(false)
                println(t.toString())
            }
        })
    }

    fun agreeAll(){
        noticeWarningAgree.isChecked = true //개인정보 최소정보 제공동의
        uniqueInfoAgree.isChecked = true //고유식별 정보
        sensitiveInfoAgree.isChecked = true //민감정보
        beforeAfterInfoProvideAgree.isChecked = true //건진실시 따른 사전사후 서비스 관련 정보 제공
        mobileInfoAgree.isChecked = true //진료예약, 입원 및 검사예정에 따른 모바일 안내
        hospitalEventInfoAgree.isChecked = true //병원이용 및 병원의 새로운 서비스, 행사정보안내
        sendSMSAgree.isChecked = true //건강정보 발송을 위한 휴대폰 SMS 발송
        MedicalCooperationInfoAgree.isChecked = true //병원간의 상호 진료협력을 위해 의료정보 제공
        patientInfoAgree.isChecked = true //환자대리인 정보이용 동의
    }

    fun disAgreeAll(){
        noticeWarningDisAgree.isChecked = true
        uniqueInfoDisAgree.isChecked = true
        sensitiveInfoDisAgree.isChecked = true
        beforeAfterInfoProvideDisAgree.isChecked = true
        mobileInfoDisAgree.isChecked = true
        hospitalEventInfoDisAgree.isChecked = true
        sendSMSDisAgree.isChecked = true
        MedicalCooperationInfoDisAgree.isChecked = true
        patientInfoDisAgree.isChecked = true
    }

    fun AgreeValidation():Boolean
    {
        return noticeWarningAgree.isChecked &&
                uniqueInfoAgree.isChecked &&
                sensitiveInfoAgree.isChecked &&
                beforeAfterInfoProvideAgree.isChecked &&
                mobileInfoAgree.isChecked &&
                hospitalEventInfoAgree.isChecked &&
                sendSMSAgree.isChecked &&
                MedicalCooperationInfoAgree.isChecked &&
                patientInfoAgree.isChecked
    }

    fun ProgressAction(isShow:Boolean)
    {
        if(isShow)
        {

            Progress_circle.visibility = View.VISIBLE
            Progress_bg.visibility = View.VISIBLE
            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        else
        {
            Progress_circle.visibility = View.GONE
            Progress_bg.visibility = View.GONE
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }


}