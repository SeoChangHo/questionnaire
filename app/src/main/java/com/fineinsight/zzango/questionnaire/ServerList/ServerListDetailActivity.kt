package com.fineinsight.zzango.questionnaire.ServerList

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.Switch
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.DataClass.SelectDetailInfo
import com.fineinsight.zzango.questionnaire.DataClass.ServerPaper_Cancer
import com.fineinsight.zzango.questionnaire.LocalList.Paper_CANCER
import com.fineinsight.zzango.questionnaire.MainActivity
import com.fineinsight.zzango.questionnaire.OracleUtill
import com.fineinsight.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_server_list_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerListDetailActivity : AppCompatActivity() {

    var SelectDetailArr = ArrayList<SelectDetailInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_list_detail)

        GETDetailList()
    }

    fun GETDetailList()
    {
        if (intent.hasExtra("ARR"))
        {
            SelectDetailArr = intent.getSerializableExtra("ARR") as ArrayList<SelectDetailInfo>

            for (item in SelectDetailArr)
            {
                println("TABLENAME: ${item.TableName}")
            }

            serverdetail_recyclertView.layoutManager = LinearLayoutManager(this@ServerListDetailActivity)
            serverdetail_recyclertView.adapter = ServerListDetailAdapter(SelectDetailArr, this@ServerListDetailActivity)
        }
    }

    fun LoadServerPaper(SEQ:String, DATE:String, NAME:String, JUMIN:String, TABLE:String)
    {
        println("seq: ${SEQ}")
        println("date: ${DATE}")
        println("name: ${NAME}")
        println("jumin: ${JUMIN}")
        println("table: ${TABLE}")

        when
        {
            TABLE.contains("암") ->
            {
                LoadServer_Cancer(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("생활") ->
            {
                LoadServer_Life(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("공통") ->
            {
                LoadServer_Common(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("정신") ->
            {
                LoadServer_Mental(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("인지") ->
            {
                LoadServer_Cognitive(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("노인") ->
            {
                LoadServer_Elderly(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("구강") ->
            {
                LoadServer_Oral(SEQ, DATE, NAME, JUMIN)
            }
        }

    }

    fun LoadServer_Cancer(SEQ:String, DATE:String, NAME:String, JUMIN:String)
    {
        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME
        MAP["SEQ"] = SEQ


        ProgressAction(true)

        OracleUtill().getPaper_cancer().SelectPaper_cancer(MAP).enqueue(object : Callback<ArrayList<ServerPaper_Cancer>> {

            override fun onResponse(call: Call<ArrayList<ServerPaper_Cancer>>, response: Response<ArrayList<ServerPaper_Cancer>>) {

                if(response.isSuccessful){

                    if(response.body()!!.size == 0){

                        Toast.makeText(this@ServerListDetailActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    }else{

                        try {

                            println("성공")
                            var userDetailList = ArrayList<ServerPaper_Cancer>()

                            for(i in 0 until response.body()!!.size){
                                userDetailList.add(
                                        ServerPaper_Cancer(
                                                response.body()!![i].c_date,
                                                response.body()!![i].c_bun,
                                                response.body()!![i].c_year,
                                                response.body()!![i].c_email,
                                                response.body()!![i].c_jungyn,
                                                response.body()!![i].ck1,
                                                response.body()!![i].ck1_1 ,
                                                response.body()!![i].ck2,
                                                response.body()!![i].ck2_1,
                                                response.body()!![i].ck3_1,
                                                response.body()!![i].ck3_1_1,
                                                response.body()!![i].ck3_1_2,
                                                response.body()!![i].ck3_1_3,
                                                response.body()!![i].ck3_1_4,
                                                response.body()!![i].ck3_1_5,
                                                response.body()!![i].ck3_2,
                                                response.body()!![i].ck3_2_1,
                                                response.body()!![i].ck3_2_2,
                                                response.body()!![i].ck3_2_3,
                                                response.body()!![i].ck3_2_4,
                                                response.body()!![i].ck3_2_5,
                                                response.body()!![i].ck3_3,
                                                response.body()!![i].ck3_3_1,
                                                response.body()!![i].ck3_3_2,
                                                response.body()!![i].ck3_3_3,
                                                response.body()!![i].ck3_3_4,
                                                response.body()!![i].ck3_3_5,
                                                response.body()!![i].ck3_4,
                                                response.body()!![i].ck3_4_1,
                                                response.body()!![i].ck3_4_2,
                                                response.body()!![i].ck3_4_3,
                                                response.body()!![i].ck3_4_4,
                                                response.body()!![i].ck3_4_5,
                                                response.body()!![i].ck3_5,
                                                response.body()!![i].ck3_5_1,
                                                response.body()!![i].ck3_5_2,
                                                response.body()!![i].ck3_5_3,
                                                response.body()!![i].ck3_5_4,
                                                response.body()!![i].ck3_5_5,
                                                response.body()!![i].ck3_6,
                                                response.body()!![i].ck3_6_1,
                                                response.body()!![i].ck3_6_2,
                                                response.body()!![i].ck3_6_3,
                                                response.body()!![i].ck3_6_4,
                                                response.body()!![i].ck3_6_5,
                                                response.body()!![i].ck3_6_kita,
                                                response.body()!![i].ck4_1,
                                                response.body()!![i].ck4_2,
                                                response.body()!![i].ck4_3,
                                                response.body()!![i].ck4_4,
                                                response.body()!![i].ck4_5,
                                                response.body()!![i].ck4_6,
                                                response.body()!![i].ck4_7,
                                                response.body()!![i].ck4_8,
                                                response.body()!![i].ck5_1,
                                                response.body()!![i].ck5_2,
                                                response.body()!![i].ck5_3,
                                                response.body()!![i].ck5_4,
                                                response.body()!![i].ck5_5,
                                                response.body()!![i].ck5_6,
                                                response.body()!![i].ck6_1,
                                                response.body()!![i].ck6_2,
                                                response.body()!![i].ck6_3,
                                                response.body()!![i].ck6_4,
                                                response.body()!![i].ck6_5,
                                                response.body()!![i].ck6_6,
                                                response.body()!![i].ck7_1,
                                                response.body()!![i].ck7_2,
                                                response.body()!![i].ck7_3,
                                                response.body()!![i].ck7_4,
                                                response.body()!![i].ck7_5,
                                                response.body()!![i].ck7_6,
                                                response.body()!![i].ck8_1 ,
                                                response.body()!![i].ck8_2 ,
                                                response.body()!![i].ck9_1 ,
                                                response.body()!![i].ck9_2 ,
                                                response.body()!![i].ck10 ,
                                                response.body()!![i].ck11 ,
                                                response.body()!![i].ck12 ,
                                                response.body()!![i].ck13 ,
                                                response.body()!![i].ck14 ,
                                                response.body()!![i].c_name,
                                                response.body()!![i].c_jumin
                                        )
                                )
                            }


                            println("SIZE: ${userDetailList.size}")

                            for (item in userDetailList)
                            {
                                println("SEQ: ${item.c_bun}")
                            }
                            ProgressAction(false)

                        }catch (E:Exception)
                        {
                            ProgressAction(false)
                            println("ERR!!! -> ${E.message}")
                        }
                    }
                }else{
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<ServerPaper_Cancer>>, t: Throwable) {

                ProgressAction(false)
            }
        })
    }

    fun LoadServer_Cognitive(SEQ:String, DATE:String, NAME:String, JUMIN:String)
    {

    }

    fun LoadServer_Common(SEQ:String, DATE:String, NAME:String, JUMIN:String)
    {

    }

    fun LoadServer_Elderly(SEQ:String, DATE:String, NAME:String, JUMIN:String)
    {

    }

    fun LoadServer_Life(SEQ:String, DATE:String, NAME:String, JUMIN:String)
    {

    }

    fun LoadServer_Mental(SEQ:String, DATE:String, NAME:String, JUMIN:String)
    {

    }

    fun LoadServer_Oral(SEQ:String, DATE:String, NAME:String, JUMIN:String)
    {

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
