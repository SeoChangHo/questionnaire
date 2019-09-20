package com.fineinsight.zzango.questionnaire

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.*
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.chart
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.manager_name
import com.fineinsight.zzango.questionnaire.Signature.CanvasView
import com.fineinsight.zzango.questionnaire.UserList.UserList
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login_agree.*
import kotlinx.android.synthetic.main.activity_login_exam.*
import kotlinx.android.synthetic.main.activity_login_exam.btnList
import kotlinx.android.synthetic.main.activity_login_exam.btnReSign
import kotlinx.android.synthetic.main.activity_login_exam.canvas
import kotlinx.android.synthetic.main.activity_login_exam.constraintLayout16
import kotlinx.android.synthetic.main.activity_login_exam.first_serial
import kotlinx.android.synthetic.main.activity_login_exam.first_view
import kotlinx.android.synthetic.main.activity_login_exam.last_serial
import kotlinx.android.synthetic.main.activity_login_exam.listButton
import kotlinx.android.synthetic.main.activity_login_exam.login_appbar_loading_progress
import kotlinx.android.synthetic.main.activity_login_exam.login_appbar_loading_progress_bg
import kotlinx.android.synthetic.main.activity_login_exam.starticon
import kotlinx.android.synthetic.main.activity_login_exam.textView261
import kotlinx.android.synthetic.main.activity_login_exam.textView72
import kotlinx.android.synthetic.main.activity_login_exam.user_login_button
import kotlinx.android.synthetic.main.activity_login_exam.user_name
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.quit_alert.*
import kotlinx.android.synthetic.main.quit_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginExamActivity : AppCompatActivity() {

    var isUserLogin: Boolean = false
    lateinit var canvasView: CanvasView
    var validationInside = false
    var userlogin_buttonClick = true
    var popup = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_exam)

        userlogin2(this)

        btnList.setOnClickListener {
            startActivity(Intent(this, ExamListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        listButton.setOnClickListener {
            popuplogin()
        }

        if(Examinee.USER.info.NAME.isNotEmpty()){ user_name.setText((Examinee.USER.info.NAME)) }
        if(Examinee.USER.info.JUMIN1.isNotEmpty()){ first_serial.setText(Examinee.USER.info.JUMIN1) }
        if(Examinee.USER.info.JUMIN2.isNotEmpty()){ last_serial.setText(Examinee.USER.info.JUMIN2) }

    }

    fun userlogin2(context: Context) {


        //if(user_login.text == "님"){
        if (!isUserLogin) {

            MainActivity.userLoginButton = user_login_button
            MainActivity.userName = user_name

            MainActivity.ValidationBool = false
            MainActivity.canvas_motion = null

            user_login_button.isEnabled = false

//            //////////😎😎😎서명을 위한 공간😎😎😎/////// ///
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
            canvasView = canvas
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////


            user_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {


                    if (user_name.text.toString() != "" && MainActivity.ValidationBool && MainActivity.canvas_motion != null && MainActivity.isJuminValidated) {
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)
                    } else {
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    if (user_name.text.toString() != "") {
                        user_name.setBackgroundResource(R.drawable.start_login_back2)
                    } else {
                        user_name.setBackgroundResource(R.drawable.start_login_back)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }


                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })


            first_serial.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {

                    if (user_name.text.toString() != "" && MainActivity.ValidationBool && MainActivity.canvas_motion != null && MainActivity.isJuminValidated) {
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)
                    } else {
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    if (first_serial.text.toString() != "") {
                        first_serial.setBackgroundResource(R.drawable.start_login_back2)
                    } else {
                        first_serial.setBackgroundResource(R.drawable.start_login_back)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.length == 6) {

                        var Jumin = first_serial.text.toString()

                        validationInside = JuminValidation(Jumin, context)

                        if (validationInside) {
                            last_serial.requestFocus()
                        } else {
                            user_login_button.isEnabled = false
                            user_login_button.setBackgroundResource(R.drawable.start_login_back)
                        }

                    } else if (s.length < 6) {
                        MainActivity.isJuminValidated = false
                        validationInside = false
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }
                }
            })

            last_serial.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (user_name.text.toString() != "" && MainActivity.ValidationBool && MainActivity.canvas_motion != null && MainActivity.isJuminValidated) {
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)

                        if (user_name.text.toString() != "" && MainActivity.ValidationBool) {
                            println("aaa")
                            CloseKeyboard()
                        }

                    } else {
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)

                        if (user_name.text.toString() != "" && MainActivity.ValidationBool) {
                            println("bbb")
                            CloseKeyboard()
                        }
                    }

                    if (last_serial.text.toString() != "") {
                        last_serial.setBackgroundResource(R.drawable.start_login_back2)
                    } else {
                        last_serial.setBackgroundResource(R.drawable.start_login_back)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.length == 1) {

                        MainActivity.ValidationBool = true
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)

                    } else if (s.length < 1) {
                        MainActivity.ValidationBool = false
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    println(user_name.text.toString() + "," + MainActivity.ValidationBool + "," + MainActivity.canvas_motion + "," + MainActivity.isJuminValidated)
                }
            })

            btnReSign.setOnClickListener {
                canvasView.ClearCanvas()
                user_login_button.isEnabled = false
                user_login_button.setBackgroundResource(R.drawable.start_login_back)
            }

            user_login_button.setOnClickListener {

                if (MainActivity.isJuminValidated) {

                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    var bitmap: Bitmap = Bitmap.createBitmap(canvasView.width, canvasView.height, Bitmap.Config.ARGB_8888)
                    var canvas: Canvas = Canvas(bitmap)
                    canvasView.draw(canvas)

                    var stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    //MainActivity.user_signature = bitmap


                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////


                    UserHandler(true)





                    Toast.makeText(context, "사용자가 등록되었습니다.", Toast.LENGTH_SHORT).show()
//                    user_login.text = login_user_name+"님"



                    Examinee.USER.info= ExamineeInfo(
                            user_name.text.toString(),
                            first_serial.text.toString(),
                            last_serial.text.toString(),
                            "",
                            stream.toByteArray(),
                            false,
                            true
                    )


                    //입력창 초기화
                    user_name.text.clear()
                    first_serial.text.clear()
                    last_serial.text.clear()
                    canvasView.ClearCanvas()
                    user_login_button.isEnabled = false
                    user_login_button.setBackgroundResource(R.drawable.start_login_back)

                    MainActivity.chart.clear()




                    SavePaper.Total.Init()
                    MainActivity.exam_no = System.currentTimeMillis().toString()
                    SavePaper.Total.Array[0] = PublicDataInfo(MainActivity.hospital, Examinee.USER.info.NAME, Examinee.USER.info.JUMIN1, Examinee.USER.info.JUMIN2, Examinee.USER.info.SIGN, MainActivity.exam_no)

                    //user_image.setImageResource(R.drawable.exit)

                    //login_appbar_loading_progress.visibility = View.VISIBLE
                    //login_appbar_loading_progress_bg.visibility = View.VISIBLE


                    println("user_last_serial.toInt(): ${Examinee.USER.info.JUMIN2.toInt()}")
                    println("user_last_serial.toInt()%2: ${Examinee.USER.info.JUMIN2.toInt() % 2}")
                    println("user_last_serial.toInt()%2 == 0 : ->${Examinee.USER.info.JUMIN2.toInt() % 2 == 0}")
                    AdditionalArr.Gender.isFemale = Examinee.USER.info.JUMIN2.toInt() % 2 == 0

                    //현재 접속병원이 목포한국병원이면서 네트워크가 켜져 있을 때
                    if (MainActivity.hospital == HospitalList.hospital.Mokpo && isNetworkAvailable()) {


                        var now = LocalDate.now()

                        var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        var NAME = Examinee.USER.info.NAME
                        var JUMIN = Examinee.USER.info.JUMIN1
                        var JUMIN2 = Examinee.USER.info.JUMIN2

                        MokpoCheckPaper(context, Strnow, NAME, JUMIN, JUMIN2)


                    } else {
                        println("목포병원이 아니거나 네트워크가 꺼져있습니다")
                        var EmptyStringArr = ArrayList<String>()
                        ShowPaperDIALOG(context, EmptyStringArr, Examinee.USER.info.JUMIN1)
                    }
                } else {
                    Toast.makeText(this, "주민번호 형식을 확인해주세요.", Toast.LENGTH_LONG).show()
                }

                userlogin_buttonClick = true

            }

        }

        //로그아웃시 옮김

//        if(view.text == login_user_name+"님"){
//
//            var dialog = AlertDialog.Builder(this).create()
//            var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_user_logout, null)
//
//            //다이얼로그 뒤로가기 버튼 막기
//            dialog.setCancelable(false)
//
//            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//            dialog.setView(dialog_view)
//            dialog.setCanceledOnTouchOutside(false)
//
//            val logout = dialog_view.findViewById(R.id.user_logout) as Button
//            val cancel = dialog_view.findViewById(R.id.user_logout_cancel) as Button
//
//            dialog.show()
//
//            logout.setOnClickListener {
//
//                login_user_name = ""
//                user_first_serial = ""
//                user_last_serial = ""
//
//                Toast.makeText(context, "사용자가 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
//                view.text = "사용자 등록하기"
//                view2.setImageResource(R.drawable.regi)
//
//                canvas_motion = null
//
//                chart.clear()
//                dialog.dismiss()
//                userlogin_buttonClick = true
//            }
//
//            cancel.setOnClickListener {
//
//                canvas_motion = null
//                userlogin_buttonClick = true
//                dialog.dismiss()
//            }
//
//        }

    }

    fun JuminValidation(Jumin: String, context: Context): Boolean {
        MainActivity.isJuminValidated = false
        var yy = Jumin.substring(0, 2)
        var mm = Jumin.substring(2, 4)
        var dd = Jumin.substring(4, 6)

        println(yy + " " + mm + " " + dd)

        if (mm.toInt() == 0) {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            MainActivity.isJuminValidated = false
            return false
        } else if (12 < mm.toInt()) {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            MainActivity.isJuminValidated = false
            return false
        }

        if (dd.toInt() == 0) {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            MainActivity.isJuminValidated = false
            return false
        }
        //1 3 5 7 9 10  12
        //   4 6 8    11
        // 2

        //31일까지 있는 달일 때
        if (mm.toInt() == 1 || mm.toInt() == 3 || mm.toInt() == 5 || mm.toInt() == 7 || mm.toInt() == 9 || mm.toInt() == 10 || mm.toInt() == 12) {
            if (31 < dd.toInt()) {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                MainActivity.isJuminValidated = false
                return false
            }
        }//30일까지 있는 달일 때
        else if (mm.toInt() == 4 || mm.toInt() == 6 || mm.toInt() == 8 || mm.toInt() == 11) {
            if (30 < dd.toInt()) {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                MainActivity.isJuminValidated = false
                return false
            }
        }//2월일때 (윤달 미포함)
        else if (mm.toInt() == 2) {
            if (29 < dd.toInt()) {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                MainActivity.isJuminValidated = false
                return false
            }
        }
        MainActivity.isJuminValidated = true
        println("true입니다!!")
        return true
    }

    fun MokpoCheckPaper(context: Context, DATE: String, NAME: String, JUMIN1: String, JUMIN2: String) {

        val MokpoCheckParam = HashMap<String, String>()
        MokpoCheckParam["DATE"] = DATE.trim()
        MokpoCheckParam["NAME"] = NAME.trim()
        MokpoCheckParam["JUMIN"] = JUMIN1.trim() + JUMIN2.trim()

        var CheckArr = ArrayList<String>()

        ProgressAction(true)
        MokpoControlHandler(true)



        OracleUtill().getMokpoCheck().SelectMokpoCheckPaper(MokpoCheckParam).enqueue(object : Callback<List<MokpoCheck>> {

            override fun onResponse(call: Call<List<MokpoCheck>>, response: Response<List<MokpoCheck>>) {

                if (response.isSuccessful) {

                    val MOKPODATA = response.body()

                    //값이 있음
                    if (!MOKPODATA!!.isEmpty()) {

                        println("값 확인")
                        for (document in MOKPODATA) {
                            println(document.gj_jong)
                            CheckArr.add(document.gj_jong)
                        }
                        ProgressAction(false)
                        MokpoControlHandler(false)
                        ShowPaperDIALOG(context, CheckArr, JUMIN1)
                    } else//값이 없음
                    {
                        println("값이 없음")
                        ProgressAction(false)
                        MokpoControlHandler(false)
                        ShowPaperDIALOG(context, CheckArr, JUMIN1)
                    }

                } else//요청실패
                {
                    println("값이 없음")
                    ProgressAction(false)
                    MokpoControlHandler(false)
                    ShowPaperDIALOG(context, CheckArr, JUMIN1)
                }
            }

            //요청 오류
            override fun onFailure(call: Call<List<MokpoCheck>>, t: Throwable) {

                println("값이 없음")
                ProgressAction(false)
                MokpoControlHandler(false)
                ShowPaperDIALOG(context, CheckArr, JUMIN1)

            }

        })
    }

    fun ShowPaperDIALOG(context: Context, arr: ArrayList<String>, JUMIN1: String) {

//        Glide.with(this).load(R.drawable.check).into(starticon)

        first_view.visibility = View.GONE

//        Handler().postDelayed({
//            starticon.setImageDrawable(getDrawable(R.drawable.usericon))

            starticon.animate().alpha(1f).duration = 1000

            ObjectAnimator.ofFloat(starticon, View.Y, 500f, 2000f).apply {
                duration = 1000
                start()
            }

            Handler().postDelayed({
                ObjectAnimator.ofFloat(starticon, View.Y, 2000f, 500f).apply {
                    duration = 1000
                    start()
                }

                second_view.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(second_view, View.Y, 2200f, 700f).apply {
                    duration = 1000
                    start()
                }

            }, 1000)

//        }, 1000)


        val ok = findViewById(R.id.user_ok) as Button
        val title = findViewById(R.id.notice_Title) as TextView
        val text = findViewById(R.id.notice_textView) as TextView
        val text2 = findViewById(R.id.notice_textView2) as TextView
        val text3 = findViewById(R.id.notice_textView3) as TextView
        val text4 = findViewById(R.id.notice_textView4) as TextView
        val text5 = findViewById(R.id.notice_textView5) as TextView
        var chkOral = findViewById(R.id.chkOral) as CheckBox
        var chkCancer = findViewById(R.id.chkCancer) as CheckBox


        for (item in arr) {
            //암검진 체크
            if (item.contains("03")) {
                chkCancer.isChecked = true
            }

            //구강문진 체크
            if (item.contains("75")) {
                chkOral.isChecked = true
            }
        }

        chart(JUMIN1)

        title.text = Examinee.USER.info.NAME
//        user_login.text = MainActivity.login_user_name + "님"

        var count = 0

        for (i in MainActivity.chart) {

            if (i.isbool) {
                count++
                when (i.chartName) {
                    PaperNameInfo.PC.COMMON.EN_NM -> {

                    }
                    PaperNameInfo.PC.MENTAL.EN_NM -> {
                        text3.visibility = View.VISIBLE
                    }
                    PaperNameInfo.PC.ELDERLY.EN_NM -> {
                        text5.visibility = View.VISIBLE
                    }
                    PaperNameInfo.PC.COGNITIVE.EN_NM -> {
                        text2.visibility = View.VISIBLE
                    }
                    PaperNameInfo.PC.LIFE.EN_NM -> {
                        text4.visibility = View.VISIBLE
                    }
                    else -> {

                    }
                }
            }

        }

        noticeCount_textView.text = count.toString() + "개"



        ok.setOnClickListener {


            ProgressAction(true)

            AdditionalArr.Page.init()
            if (chkOral.isChecked) {
                AdditionalArr.Page.isOralChecked = true
            }
            if (chkCancer.isChecked) {
                AdditionalArr.Page.isCancerChecked = true
            }


            if (chkOral.isChecked) {
                MainActivity.chart.add(ChartInfo(PaperNameInfo.PC.ORAL.EN_NM, true, 5))
            } else {
                MainActivity.chart.add(ChartInfo(PaperNameInfo.PC.ORAL.EN_NM, false, 5))
            }

            if (chkCancer.isChecked) {
                MainActivity.chart.add(ChartInfo(PaperNameInfo.PC.CANCER.EN_NM, true, 6))
            } else {
                MainActivity.chart.add(ChartInfo(PaperNameInfo.PC.CANCER.EN_NM, false, 6))
            }

            Handler().postDelayed({
                //startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))


                //초기화
                SavePaper.Total.Init()
                MainActivity.exam_no = System.currentTimeMillis().toString()
                SavePaper.Total.Array[0] = PublicDataInfo(MainActivity.hospital, Examinee.USER.info.NAME, Examinee.USER.info.JUMIN1, Examinee.USER.info.JUMIN2, Examinee.USER.info.SIGN, MainActivity.exam_no)



                for (item in MainActivity.chart) {
                    if (item.isbool) {
                        when (item.chartName) {
                            PaperNameInfo.PC.COMMON.EN_NM -> {
                                startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.MENTAL.EN_NM -> {
                                startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.COGNITIVE.EN_NM -> {
                                startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.ELDERLY.EN_NM -> {
                                startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.LIFE.EN_NM -> {
                                startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.ORAL.EN_NM -> {
                                startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.CANCER.EN_NM -> {
                                startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                        }
                        break
                    } else {
                        when (item.chartName) {

                            PaperNameInfo.PC.COMMON.EN_NM -> {
                                SavePaper.Total.Array[1] = Paper_COMMON("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.MENTAL.EN_NM -> {
                                SavePaper.Total.Array[2] = Paper_MENTAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.COGNITIVE.EN_NM -> {
                                SavePaper.Total.Array[3] = Paper_COGNITIVE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.ELDERLY.EN_NM -> {
                                SavePaper.Total.Array[4] = Paper_ELDERLY("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.LIFE.EN_NM -> {
                                SavePaper.Total.Array[5] = Paper_EXERCISE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[6] = Paper_NUTRITION("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[7] = Paper_SMOKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[8] = Paper_DRINKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.ORAL.EN_NM -> {
                                SavePaper.Total.Array[9] = Paper_ORAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.CANCER.EN_NM -> {
                                SavePaper.Total.Array[10] = Paper_CANCER("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }
                        }
                    }
                }

            }, 100)

        }

        ProgressAction(false)

    }

    @SuppressLint("NewApi")
    fun chart(Jumin: String) {


        var yy = Jumin.substring(0, 2)

        var currentDateTime = LocalDateTime.now()
        var currentYear = currentDateTime.year

        //만 20세, 30세
        var two = (currentYear - 20).toString().substring(2, 4)
        var two2 = (currentYear - 30).toString().substring(2, 4)
        //만 40세, 50세, 60세
        var three = (currentYear - 40).toString().substring(2, 4)
        var three2 = (currentYear - 50).toString().substring(2, 4)
        var three3 = (currentYear - 60).toString().substring(2, 4)
        //만 66세, 80세
        var four = (currentYear - 66).toString().substring(2, 4)
        var four2 = (currentYear - 80).toString().substring(2, 4)
        //만 68세, 72세, 74세, 76세, 77세, 82세, 84세, 86세
        var five = (currentYear - 68).toString().substring(2, 4)
        var five2 = (currentYear - 72).toString().substring(2, 4)
        var five3 = (currentYear - 74).toString().substring(2, 4)
        var five4 = (currentYear - 76).toString().substring(2, 4)
        var five5 = (currentYear - 77).toString().substring(2, 4)
        var five6 = (currentYear - 82).toString().substring(2, 4)
        var five7 = (currentYear - 84).toString().substring(2, 4)
        var five8 = (currentYear - 86).toString().substring(2, 4)
        //만 70세
        var six = (currentYear - 70).toString().substring(2, 4)

        println(two + two2)


        if (yy == two || yy == two2) {
            //우울증 포함
            //chart = PaperArray.SetList.SET2
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, true, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, false, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, false, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, false, 4))
        } else if (yy == three || yy == three2 || yy == three3) {
            //우울증 생활습관 포함
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, true, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, false, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, false, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, true, 4))
        } else if (yy == four || yy == four2) {
            //인지기능 노인신체기능검사 포함
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, false, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, true, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, true, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, false, 4))
        } else if (yy == five || yy == five2 || yy == five3 || yy == five4 || yy == five5 || yy == five6 || yy == five7 || yy == five8) {
            //인지기능 포함
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, false, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, true, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, false, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, false, 4))
        } else if (yy == six) {
            //인지기능 우울증 생활습관 노인신체기능검사 포함
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, true, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, true, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, true, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, true, 4))
        } else {
            //기본검사
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, false, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, false, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, false, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, false, 4))
        }


    }

    fun CloseKeyboard() {
        var view = this.currentFocus

        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun UserHandler(isLogin: Boolean) {
        isUserLogin = isLogin



        if (isLogin) {
            //user_login.text = MainActivity.login_user_name + "님"
            //user_image.setImageResource(R.drawable.exit)
//            first_view.visibility = View.VISIBLE
//            second_view.visibility = View.GONE
            btnList.visibility = View.VISIBLE

        } else {
            //user_login.text = "사용자 등록하기"
            //user_image.setImageResource(R.drawable.regi)
            first_view.visibility = View.VISIBLE
            second_view.visibility = View.GONE

            Examinee.USER.init()


            Toast.makeText(this, "사용자가 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            MainActivity.chart.clear()

            btnList.visibility = View.GONE

            //추가
            MainActivity.canvas_motion = null
            userlogin_buttonClick = true
        }


    }

    fun MokpoControlHandler(isHide: Boolean) {
        if (isHide) {
            user_name.visibility = View.GONE
            first_serial.visibility = View.GONE
            last_serial.visibility = View.GONE
            textView261.visibility = View.GONE
            textView72.visibility = View.GONE
            canvas.visibility = View.GONE
            user_login_button.visibility = View.GONE
            btnReSign.visibility = View.GONE
            constraintLayout16.visibility = View.GONE
        } else {
            user_name.visibility = View.VISIBLE
            first_serial.visibility = View.VISIBLE
            last_serial.visibility = View.VISIBLE
            textView261.visibility = View.VISIBLE
            textView72.visibility = View.VISIBLE
            canvas.visibility = View.VISIBLE
            user_login_button.visibility = View.VISIBLE
            btnReSign.visibility = View.VISIBLE
            constraintLayout16.visibility = View.VISIBLE
        }

    }

    fun popuplogin() {

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_login, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)

        if (MainActivity.manager_name == "") {
            //다이얼로그 뒤로가기 버튼 막기
            dialog.setCancelable(false)
            //밖에부분 터치 막기
            dialog.setCanceledOnTouchOutside(false)
        } else {
            dialog_view.login_id.setText(MainActivity.manager_name)
            dialog_view.login_id.isFocusableInTouchMode = false
            dialog_view.login_password.isFocusableInTouchMode = true
        }

        dialog_view.login_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (dialog_view.login_id.text.toString() != "" && dialog_view.login_password.text.toString() != "") {
                    dialog_view.Login.isEnabled = true
                    dialog_view.Login.setBackgroundResource(R.drawable.start_login_button)
                } else {
                    dialog_view.Login.isEnabled = false
                    dialog_view.Login.setBackgroundResource(R.drawable.start_login_button_default)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        dialog_view.login_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (dialog_view.login_id.text.toString() != "" && dialog_view.login_password.text.toString() != "") {
                    dialog_view.Login.isEnabled = true
                    dialog_view.Login.setBackgroundResource(R.drawable.start_login_button)
                } else {
                    dialog_view.Login.isEnabled = false
                    dialog_view.Login.setBackgroundResource(R.drawable.start_login_button_default)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        //개발용
        dialog_view.login_id.setText("hanshin")
        dialog_view.login_password.setText("hanshin1678")

        val login = dialog_view.findViewById(R.id.Login) as Button
        login.setOnClickListener {

            if (dialog_view.login_id.text.toString() != "") {


                var UserArray: ArrayList<UserList> = ArrayList()

                val user = dialog_view.login_id.text.toString()
                var pass = dialog_view.login_password.text.toString()

                UserArray.add(UserList(user, pass))

                val sql_db = LocalDBhelper(this).writableDatabase

                val datacount = LocalDBhelper(this).UserCheck(sql_db!!, UserArray)

                if (datacount == 0) {
                    Toast.makeText(applicationContext, "유저정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    if (MainActivity.manager_name == "") {
                        MainActivity.manager_name = user
                        setHospitalList()
                        listButton.visibility = View.VISIBLE
                        Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        dialog.dismiss()
                        Handler().postDelayed({
                            startActivity(Intent(this, SettingActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        }, 125)

                    }

                }
            }


        }

        dialog.show()

    }

    fun setHospitalList() {

        if (MainActivity.manager_name == "fine") {

            MainActivity.hospital = HospitalList.hospital.test
            //main_logo.setImageResource(R.drawable.logo)

        } else if (MainActivity.manager_name == "mokpohos") {

            MainActivity.hospital = HospitalList.hospital.Mokpo
            //main_logo.setImageResource(R.drawable.logo2)

        } else if (MainActivity.manager_name == "hanshin") {

            MainActivity.hospital = HospitalList.hospital.Banpo

        } else if (MainActivity.manager_name == "bestian") {

            MainActivity.hospital = HospitalList.hospital.Osong
            //main_logo.setImageResource(R.drawable.bestianlogo)
        }
    }


    override fun onBackPressed() {

        if (first_view.visibility == View.GONE) {

            var customDialog = Dialog(this)
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            customDialog.setContentView(R.layout.quit_alert)
            customDialog.window!!.decorView.setBackgroundResource(R.drawable.alert_shape)
            customDialog.create()

            customDialog.notice.text = "사용자 등록을 취소하시겠습니까?"
            customDialog.finish.text = "네"
            customDialog.cancel.text = "아니요"

            if(!popup) {

                customDialog.show().let { popup = true }

            }

            customDialog.setOnDismissListener {

                popup = false
                customDialog = Dialog(this)

            }

            customDialog.finish.setOnClickListener {

                UserHandler(false)
                customDialog.dismiss()

            }

            customDialog.cancel.setOnClickListener {

                customDialog.dismiss()

            }


        } else if (MainActivity.manager_name == "bestian") {

            var customDialog = Dialog(this)
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            customDialog.setContentView(R.layout.quit_alert)
            customDialog.window!!.decorView.setBackgroundResource(R.drawable.alert_shape)
            customDialog.create()

            customDialog.notice.text = "앱을 종료하시겠습니까?"
            customDialog.finish.text = "네"
            customDialog.cancel.text = "아니요"

            if(!popup) {

                customDialog.show().let { popup = true }

            }

            customDialog.setOnDismissListener {

                popup = false
                customDialog = Dialog(this)

            }

            customDialog.finish.setOnClickListener {

                ActivityCompat.finishAffinity(this)

                System.runFinalization()
                System.exit(0)
                customDialog.dismiss()

            }

            customDialog.cancel.setOnClickListener {

                customDialog.dismiss()

            }

        } else {
            startActivity(Intent(this, Main2Activity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

    }

    fun ProgressAction(isShow: Boolean) {
        if (isShow) {
            login_appbar_loading_progress.visibility = View.VISIBLE
            login_appbar_loading_progress_bg.visibility = View.VISIBLE
            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            login_appbar_loading_progress.visibility = View.GONE
            login_appbar_loading_progress_bg.visibility = View.GONE
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}

