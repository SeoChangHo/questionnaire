package com.fineinsight.zzango.questionnaire.ServerList

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.AgreementActivity
import com.fineinsight.zzango.questionnaire.DataClass.SelectInfo
import com.fineinsight.zzango.questionnaire.LocalList.READ_AGREE
import com.fineinsight.zzango.questionnaire.MainActivity
import com.fineinsight.zzango.questionnaire.OracleUtill
import com.fineinsight.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_server_list_agree.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ServerListAgreeActivity : AppCompatActivity() {

    var SelectDate:String = LocalDate.now().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_list_agree)

        DateSetting()
        loadList()
    }

    fun DateSetting()
    {
        var now = LocalDate.now().toString()
        SelectDate = now
        println("SelectDate: ${SelectDate}")
        txtDate.text = "${now.split("-")[0]} 년   ${(now.split("-")[1]).toString().padStart(2, '0')} 월   ${now.split("-")[2].toString().padStart(2, '0')} 일"


        txtDate.setOnClickListener {

            var MYyear = 0
            var MYmonth = 0
            var MYday = 0


            var splitdate = SelectDate.split("-")
            var CheckMYyear = splitdate[0].toIntOrNull()
            var CheckMYmonth = splitdate[1].toIntOrNull()
            var CheckMYday = splitdate[2].toIntOrNull()

            println("splitdate: ${splitdate}")
            println("CheckMYyear: ${CheckMYyear}")
            println("CheckMYmonth: ${CheckMYmonth}")
            println("CheckMYday: ${CheckMYday}")

            if (CheckMYyear != null && CheckMYmonth != null && CheckMYday != null)
            {
                MYyear = splitdate[0].toInt()
                MYmonth = splitdate[1].toInt()-1
                MYday = splitdate[2].toInt()

                val dp = DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    txtDate.text = "${year} 년   ${(month+1).toString().padStart(2, '0')} 월   ${dayOfMonth.toString().padStart(2, '0')} 일"
                    SelectDate = "${year}-${(month+1).toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}"
                    loadList()
                }, MYyear, MYmonth, MYday)

                dp.setOnCancelListener {

                }

                dp.datePicker.maxDate = Calendar.getInstance().timeInMillis

                dp.show()
            }
        }
    }


    fun loadList(){

        var EMPTYList = ArrayList<SelectInfo>()

        server_recyclertView.layoutManager = LinearLayoutManager(this@ServerListAgreeActivity)
        server_recyclertView.adapter = ServerListAgreeAdapter(EMPTYList, this@ServerListAgreeActivity)


        var selectDate = HashMap<String, String>()
        selectDate["DATE"] = SelectDate
        selectDate["AREA"] = MainActivity.hospital

        ProgressAction(true)

        OracleUtill().getUserAgreeCheck().SelectAgreeList(selectDate).enqueue(object : Callback<ArrayList<SelectInfo>> {
            override fun onResponse(call: Call<ArrayList<SelectInfo>>, response: Response<ArrayList<SelectInfo>>) {
                if(response.isSuccessful){
                    if(response.body()!!.size == 0){

                        Toast.makeText(this@ServerListAgreeActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                        constraintLayout6.visibility = View.GONE
                        constraint_null.visibility = View.VISIBLE

                    }else{

                        constraintLayout6.visibility = View.VISIBLE
                        constraint_null.visibility = View.GONE
                        var userList = ArrayList<SelectInfo>()
                        for(i in 0 until response.body()!!.size){
                            userList.add(
                                    SelectInfo(
                                            response.body()!!.get(i).userName,
                                            response.body()!!.get(i).userNumber,
                                            response.body()!!.get(i).dateInfo
                                    )
                            )
                        }
                        server_recyclertView.layoutManager = LinearLayoutManager(this@ServerListAgreeActivity)
                        server_recyclertView.adapter = ServerListAgreeAdapter(userList, this@ServerListAgreeActivity)

                        ProgressAction(false)
                    }


                }else{
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<SelectInfo>>, t: Throwable) {
                ProgressAction(false)
            }
        })
    }


    fun loadDetailList(NAME:String, JUMIN:String, DATE:String){

        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME

        ProgressAction(true)

        OracleUtill().getUserAgreeDetailCheck().SelectAgreeListDetail(MAP).enqueue(object : Callback<ArrayList<READ_AGREE>> {

            override fun onResponse(call: Call<ArrayList<READ_AGREE>>, response: Response<ArrayList<READ_AGREE>>) {

                if(response.isSuccessful){

                    if(response.body()!!.size < 1){

                        Toast.makeText(this@ServerListAgreeActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    }else{

                        println("성공")

                        try{
                            var AgreeListArr = ArrayList<READ_AGREE>()

                            for(i in 0 until response.body()!!.size){
                                AgreeListArr.add(
                                        READ_AGREE(
                                                response.body()!!.get(i).HOSPITAL,
                                                response.body()!!.get(i).SYS_DATE,
                                                response.body()!!.get(i).USER_ID,
                                                response.body()!!.get(i).UPD_DATE,
                                                response.body()!!.get(i).BUNHO,
                                                response.body()!!.get(i).IO_GUBUN,
                                                response.body()!!.get(i).BASIC,
                                                response.body()!!.get(i).GUNJIN,
                                                response.body()!!.get(i).MOBILE,
                                                response.body()!!.get(i).EVENT,
                                                response.body()!!.get(i).SMS,
                                                response.body()!!.get(i).CONSULT,
                                                response.body()!!.get(i).DAERI,
                                                response.body()!!.get(i).GOYU,
                                                response.body()!!.get(i).MINGAM,
                                                response.body()!!.get(i).SCAN,
                                                response.body()!!.get(i).CAR_NO,
                                                response.body()!!.get(i).NAME,
                                                response.body()!!.get(i).JUMIN,
                                                response.body()!!.get(i).SIGN
                                        )
                                )
                            }


                            val AgreementActivity = Intent(this@ServerListAgreeActivity, AgreementActivity::class.java).putExtra("AgreeListArr", AgreeListArr).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            startActivity(AgreementActivity)
                            ProgressAction(false)
                        }
                        catch (e:Exception)
                        {
                            println("e.message: ${e.message}")
                        }


                    }


                }else{
                    println("?")
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<READ_AGREE>>, t: Throwable) {
                println("??")
                println(t.message)
                ProgressAction(false)
            }
        })
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


