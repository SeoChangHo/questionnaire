package com.fineinsight.zzango.questionnaire.ServerList

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.Switch
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.*
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.Paper_CANCER
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

    fun GETDetailList() {
        if (intent.hasExtra("ARR")) {
            SelectDetailArr = intent.getSerializableExtra("ARR") as ArrayList<SelectDetailInfo>

            for (item in SelectDetailArr) {
                println("TABLENAME: ${item.TableName}")
            }

            serverdetail_recyclertView.layoutManager = LinearLayoutManager(this@ServerListDetailActivity)
            serverdetail_recyclertView.adapter = ServerListDetailAdapter(SelectDetailArr, this@ServerListDetailActivity)
        }
    }

    fun LoadServerPaper(SEQ: String, DATE: String, NAME: String, JUMIN: String, TABLE: String) {
        println("seq: ${SEQ}")
        println("date: ${DATE}")
        println("name: ${NAME}")
        println("jumin: ${JUMIN}")
        println("table: ${TABLE}")

        when {
            TABLE.contains("암") -> {
                LoadServer_Cancer(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("생활") -> {
                LoadServer_Life(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("공통") -> {
                LoadServer_Common(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("정신") -> {
                LoadServer_Mental(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("인지") -> {
                LoadServer_Cognitive(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("노인") -> {
                LoadServer_Elderly(SEQ, DATE, NAME, JUMIN)
            }

            TABLE.contains("구강") -> {
                LoadServer_Oral(SEQ, DATE, NAME, JUMIN)
            }
        }

    }

    fun LoadServer_Cancer(SEQ: String, DATE: String, NAME: String, JUMIN: String) {
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

                if (response.isSuccessful) {

                    if (response.body()!!.size == 0) {

                        Toast.makeText(this@ServerListDetailActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    } else {

                        try {

                            println("성공")
                            var userDetailList = ArrayList<ServerPaper_Cancer>()

                            for (i in 0 until response.body()!!.size) {
                                userDetailList.add(
                                        ServerPaper_Cancer(
                                                if (response.body()!![i].c_date.isNullOrBlank()) "" else response.body()!![i].c_date,
                                                if (response.body()!![i].c_bun.isNullOrBlank()) "" else response.body()!![i].c_bun,
                                                if (response.body()!![i].c_year.isNullOrBlank()) "" else response.body()!![i].c_year,
                                                if (response.body()!![i].c_email.isNullOrBlank()) "" else response.body()!![i].c_email,
                                                if (response.body()!![i].c_jungyn.isNullOrBlank()) "" else response.body()!![i].c_jungyn,
                                                if (response.body()!![i].ck1.isNullOrBlank()) "" else response.body()!![i].ck1,
                                                if (response.body()!![i].ck1_1.isNullOrBlank()) "" else response.body()!![i].ck1_1,
                                                if (response.body()!![i].ck2.isNullOrBlank()) "" else response.body()!![i].ck2,
                                                if (response.body()!![i].ck2_1.isNullOrBlank()) "" else response.body()!![i].ck2_1,
                                                if (response.body()!![i].ck3_1.isNullOrBlank()) "" else response.body()!![i].ck3_1,
                                                if (response.body()!![i].ck3_1_1.isNullOrBlank()) "" else response.body()!![i].ck3_1_1,
                                                if (response.body()!![i].ck3_1_2.isNullOrBlank()) "" else response.body()!![i].ck3_1_2,
                                                if (response.body()!![i].ck3_1_3.isNullOrBlank()) "" else response.body()!![i].ck3_1_3,
                                                if (response.body()!![i].ck3_1_4.isNullOrBlank()) "" else response.body()!![i].ck3_1_4,
                                                if (response.body()!![i].ck3_1_5.isNullOrBlank()) "" else response.body()!![i].ck3_1_5,
                                                if (response.body()!![i].ck3_2.isNullOrBlank()) "" else response.body()!![i].ck3_2,
                                                if (response.body()!![i].ck3_2_1.isNullOrBlank()) "" else response.body()!![i].ck3_2_1,
                                                if (response.body()!![i].ck3_2_2.isNullOrBlank()) "" else response.body()!![i].ck3_2_2,
                                                if (response.body()!![i].ck3_2_3.isNullOrBlank()) "" else response.body()!![i].ck3_2_3,
                                                if (response.body()!![i].ck3_2_4.isNullOrBlank()) "" else response.body()!![i].ck3_2_4,
                                                if (response.body()!![i].ck3_2_5.isNullOrBlank()) "" else response.body()!![i].ck3_2_5,
                                                if (response.body()!![i].ck3_3.isNullOrBlank()) "" else response.body()!![i].ck3_3,
                                                if (response.body()!![i].ck3_3_1.isNullOrBlank()) "" else response.body()!![i].ck3_3_1,
                                                if (response.body()!![i].ck3_3_2.isNullOrBlank()) "" else response.body()!![i].ck3_3_2,
                                                if (response.body()!![i].ck3_3_3.isNullOrBlank()) "" else response.body()!![i].ck3_3_3,
                                                if (response.body()!![i].ck3_3_4.isNullOrBlank()) "" else response.body()!![i].ck3_3_4,
                                                if (response.body()!![i].ck3_3_5.isNullOrBlank()) "" else response.body()!![i].ck3_3_5,
                                                if (response.body()!![i].ck3_4.isNullOrBlank()) "" else response.body()!![i].ck3_4,
                                                if (response.body()!![i].ck3_4_1.isNullOrBlank()) "" else response.body()!![i].ck3_4_1,
                                                if (response.body()!![i].ck3_4_2.isNullOrBlank()) "" else response.body()!![i].ck3_4_2,
                                                if (response.body()!![i].ck3_4_3.isNullOrBlank()) "" else response.body()!![i].ck3_4_3,
                                                if (response.body()!![i].ck3_4_4.isNullOrBlank()) "" else response.body()!![i].ck3_4_4,
                                                if (response.body()!![i].ck3_4_5.isNullOrBlank()) "" else response.body()!![i].ck3_4_5,
                                                if (response.body()!![i].ck3_5.isNullOrBlank()) "" else response.body()!![i].ck3_5,
                                                if (response.body()!![i].ck3_5_1.isNullOrBlank()) "" else response.body()!![i].ck3_5_1,
                                                if (response.body()!![i].ck3_5_2.isNullOrBlank()) "" else response.body()!![i].ck3_5_2,
                                                if (response.body()!![i].ck3_5_3.isNullOrBlank()) "" else response.body()!![i].ck3_5_3,
                                                if (response.body()!![i].ck3_5_4.isNullOrBlank()) "" else response.body()!![i].ck3_5_4,
                                                if (response.body()!![i].ck3_5_5.isNullOrBlank()) "" else response.body()!![i].ck3_5_5,
                                                if (response.body()!![i].ck3_6.isNullOrBlank()) "" else response.body()!![i].ck3_6,
                                                if (response.body()!![i].ck3_6_1.isNullOrBlank()) "" else response.body()!![i].ck3_6_1,
                                                if (response.body()!![i].ck3_6_2.isNullOrBlank()) "" else response.body()!![i].ck3_6_2,
                                                if (response.body()!![i].ck3_6_3.isNullOrBlank()) "" else response.body()!![i].ck3_6_3,
                                                if (response.body()!![i].ck3_6_4.isNullOrBlank()) "" else response.body()!![i].ck3_6_4,
                                                if (response.body()!![i].ck3_6_5.isNullOrBlank()) "" else response.body()!![i].ck3_6_5,
                                                if (response.body()!![i].ck3_6_kita.isNullOrBlank()) "" else response.body()!![i].ck3_6_kita,
                                                if (response.body()!![i].ck4_1.isNullOrBlank()) "" else response.body()!![i].ck4_1,
                                                if (response.body()!![i].ck4_2.isNullOrBlank()) "" else response.body()!![i].ck4_2,
                                                if (response.body()!![i].ck4_3.isNullOrBlank()) "" else response.body()!![i].ck4_3,
                                                if (response.body()!![i].ck4_4.isNullOrBlank()) "" else response.body()!![i].ck4_4,
                                                if (response.body()!![i].ck4_5.isNullOrBlank()) "" else response.body()!![i].ck4_5,
                                                if (response.body()!![i].ck4_6.isNullOrBlank()) "" else response.body()!![i].ck4_6,
                                                if (response.body()!![i].ck4_7.isNullOrBlank()) "" else response.body()!![i].ck4_7,
                                                if (response.body()!![i].ck4_8.isNullOrBlank()) "" else response.body()!![i].ck4_8,
                                                if (response.body()!![i].ck4_9.isNullOrBlank()) "" else response.body()!![i].ck4_9,
                                                if (response.body()!![i].ck5_1.isNullOrBlank()) "" else response.body()!![i].ck5_1,
                                                if (response.body()!![i].ck5_2.isNullOrBlank()) "" else response.body()!![i].ck5_2,
                                                if (response.body()!![i].ck5_3.isNullOrBlank()) "" else response.body()!![i].ck5_3,
                                                if (response.body()!![i].ck5_4.isNullOrBlank()) "" else response.body()!![i].ck5_4,
                                                if (response.body()!![i].ck5_5.isNullOrBlank()) "" else response.body()!![i].ck5_5,
                                                if (response.body()!![i].ck6_1.isNullOrBlank()) "" else response.body()!![i].ck6_1,
                                                if (response.body()!![i].ck6_2.isNullOrBlank()) "" else response.body()!![i].ck6_2,
                                                if (response.body()!![i].ck6_3.isNullOrBlank()) "" else response.body()!![i].ck6_3,
                                                if (response.body()!![i].ck6_4.isNullOrBlank()) "" else response.body()!![i].ck6_4,
                                                if (response.body()!![i].ck6_5.isNullOrBlank()) "" else response.body()!![i].ck6_5,
                                                if (response.body()!![i].ck7_1.isNullOrBlank()) "" else response.body()!![i].ck7_1,
                                                if (response.body()!![i].ck7_2.isNullOrBlank()) "" else response.body()!![i].ck7_2,
                                                if (response.body()!![i].ck7_3.isNullOrBlank()) "" else response.body()!![i].ck7_3,
                                                if (response.body()!![i].ck7_4.isNullOrBlank()) "" else response.body()!![i].ck7_4,
                                                if (response.body()!![i].ck7_5.isNullOrBlank()) "" else response.body()!![i].ck7_5,
                                                if (response.body()!![i].ck8_1.isNullOrBlank()) "" else response.body()!![i].ck8_1,
                                                if (response.body()!![i].ck8_2.isNullOrBlank()) "" else response.body()!![i].ck8_2,
                                                if (response.body()!![i].ck9_1.isNullOrBlank()) "" else response.body()!![i].ck9_1,
                                                if (response.body()!![i].ck9_2.isNullOrBlank()) "" else response.body()!![i].ck9_2,
                                                if (response.body()!![i].ck10.isNullOrBlank()) "" else response.body()!![i].ck10,
                                                if (response.body()!![i].ck11.isNullOrBlank()) "" else response.body()!![i].ck11,
                                                if (response.body()!![i].ck12.isNullOrBlank()) "" else response.body()!![i].ck12,
                                                if (response.body()!![i].ck13.isNullOrBlank()) "" else response.body()!![i].ck13,
                                                if (response.body()!![i].ck14.isNullOrBlank()) "" else response.body()!![i].ck14,
                                                if (response.body()!![i].ck15_5.isNullOrBlank()) "" else response.body()!![i].ck15_5,
                                                if (response.body()!![i].ck15_5_1.isNullOrBlank()) "" else response.body()!![i].ck15_5_1,
                                                if (response.body()!![i].ck15_5_2.isNullOrBlank()) "" else response.body()!![i].ck15_5_2,
                                                if (response.body()!![i].ck15_5_3.isNullOrBlank()) "" else response.body()!![i].ck15_5_3,
                                                if (response.body()!![i].ck15_5_4.isNullOrBlank()) "" else response.body()!![i].ck15_5_4,
                                                if (response.body()!![i].ck15_5_5.isNullOrBlank()) "" else response.body()!![i].ck15_5_5,
                                                if (response.body()!![i].ck16_1.isNullOrBlank()) "" else response.body()!![i].ck16_1,
                                                if (response.body()!![i].ck16_2.isNullOrBlank()) "" else response.body()!![i].ck16_2,
                                                if (response.body()!![i].ck16_3.isNullOrBlank()) "" else response.body()!![i].ck16_3,
                                                if (response.body()!![i].ck16_4.isNullOrBlank()) "" else response.body()!![i].ck16_4,
                                                if (response.body()!![i].ck16_5.isNullOrBlank()) "" else response.body()!![i].ck16_5,
                                                if (response.body()!![i].ck16_6.isNullOrBlank()) "" else response.body()!![i].ck16_6,
                                                if (response.body()!![i].c_name.isNullOrBlank()) "" else response.body()!![i].c_name,
                                                if (response.body()!![i].c_jumin.isNullOrBlank()) "" else response.body()!![i].c_jumin
                                        )
                                )
                            }


                            println("SIZE: ${userDetailList.size}")
                            //스타트액티비티 putextra userDetailList as ServerPaper_Cancer

                            startActivity(Intent(this@ServerListDetailActivity, CancerExaminationActivity::class.java).putExtra("paper", userDetailList[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                            for (item in userDetailList) {
                                println("SEQ: ${item.c_bun}")
                            }
                            ProgressAction(false)

                        } catch (E: Exception) {
                            ProgressAction(false)
                            println("ERR!!! -> ${E.message}")
                        }
                    }
                } else {
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<ServerPaper_Cancer>>, t: Throwable) {

                ProgressAction(false)
            }
        })
    }

    fun LoadServer_Cognitive(SEQ: String, DATE: String, NAME: String, JUMIN: String) {
        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME
        MAP["SEQ"] = SEQ


        ProgressAction(true)

        OracleUtill().getPaper_cognitive().SelectPaper_cognitive(MAP).enqueue(object : Callback<ArrayList<ServerPaper_Cognitive>> {

            override fun onResponse(call: Call<ArrayList<ServerPaper_Cognitive>>, response: Response<ArrayList<ServerPaper_Cognitive>>) {

                if (response.isSuccessful) {

                    if (response.body()!!.size == 0) {

                        Toast.makeText(this@ServerListDetailActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    } else {

                        try {

                            println("성공")
                            var userDetailList = ArrayList<ServerPaper_Cognitive>()

                            for (i in 0 until response.body()!!.size) {
                                userDetailList.add(
                                        ServerPaper_Cognitive(
                                                if (response.body()!![i].mj_date.isNullOrBlank()) "" else response.body()!![i].mj_date,
                                                if (response.body()!![i].mj_no.isNullOrBlank()) "" else response.body()!![i].mj_no,
                                                if (response.body()!![i].mj_inji_1.isNullOrBlank()) "" else response.body()!![i].mj_inji_1,
                                                if (response.body()!![i].mj_inji_2.isNullOrBlank()) "" else response.body()!![i].mj_inji_2,
                                                if (response.body()!![i].mj_inji_3.isNullOrBlank()) "" else response.body()!![i].mj_inji_3,
                                                if (response.body()!![i].mj_inji_4.isNullOrBlank()) "" else response.body()!![i].mj_inji_4,
                                                if (response.body()!![i].mj_inji_5.isNullOrBlank()) "" else response.body()!![i].mj_inji_5,
                                                if (response.body()!![i].mj_inji_6.isNullOrBlank()) "" else response.body()!![i].mj_inji_6,
                                                if (response.body()!![i].mj_inji_7.isNullOrBlank()) "" else response.body()!![i].mj_inji_7,
                                                if (response.body()!![i].mj_inji_8.isNullOrBlank()) "" else response.body()!![i].mj_inji_8,
                                                if (response.body()!![i].mj_inji_9.isNullOrBlank()) "" else response.body()!![i].mj_inji_9,
                                                if (response.body()!![i].mj_inji_10.isNullOrBlank()) "" else response.body()!![i].mj_inji_10,
                                                if (response.body()!![i].mj_inji_11.isNullOrBlank()) "" else response.body()!![i].mj_inji_11,
                                                if (response.body()!![i].mj_inji_12.isNullOrBlank()) "" else response.body()!![i].mj_inji_12,
                                                if (response.body()!![i].mj_inji_13.isNullOrBlank()) "" else response.body()!![i].mj_inji_13,
                                                if (response.body()!![i].mj_inji_14.isNullOrBlank()) "" else response.body()!![i].mj_inji_14,
                                                if (response.body()!![i].mj_inji_15.isNullOrBlank()) "" else response.body()!![i].mj_inji_15,
                                                if (response.body()!![i].mj_inji_sum.isNullOrBlank()) "" else response.body()!![i].mj_inji_sum,
                                                if (response.body()!![i].mj_name.isNullOrBlank()) "" else response.body()!![i].mj_name,
                                                if (response.body()!![i].mj_jumin.isNullOrBlank()) "" else response.body()!![i].mj_jumin
                                        )
                                )
                            }


                            println("SIZE: ${userDetailList.size}")

                            startActivity(Intent(this@ServerListDetailActivity, CognitiveExaminationActivity::class.java).putExtra("paper", userDetailList[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                            for (item in userDetailList) {
                                println("SEQ: ${item.mj_no}")
                            }
                            ProgressAction(false)

                        } catch (E: Exception) {
                            ProgressAction(false)
                            println("ERR!!! -> ${E.message}")
                        }
                    }
                } else {
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<ServerPaper_Cognitive>>, t: Throwable) {

                ProgressAction(false)
            }
        })
    }

    fun LoadServer_Common(SEQ: String, DATE: String, NAME: String, JUMIN: String) {
        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME
        MAP["SEQ"] = SEQ


        ProgressAction(true)

        OracleUtill().getPaper_common().SelectPaper_common(MAP).enqueue(object : Callback<ArrayList<ServerPaper_Common>> {

            override fun onResponse(call: Call<ArrayList<ServerPaper_Common>>, response: Response<ArrayList<ServerPaper_Common>>) {

                if (response.isSuccessful) {

                    if (response.body()!!.size == 0) {

                        Toast.makeText(this@ServerListDetailActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    } else {

                        try {

                            println("성공")
                            var userDetailList = ArrayList<ServerPaper_Common>()

                            for (i in 0 until response.body()!!.size) {
                                userDetailList.add(
                                        ServerPaper_Common(
                                                if (response.body()!![i].mj_date.isNullOrBlank()) "" else response.body()!![i].mj_date,
                                                if (response.body()!![i].mj_no.isNullOrBlank()) "" else response.body()!![i].mj_no,
                                                if (response.body()!![i].mj1_1_1.isNullOrBlank()) "" else response.body()!![i].mj1_1_1,
                                                if (response.body()!![i].mj1_1_2.isNullOrBlank()) "" else response.body()!![i].mj1_1_2,
                                                if (response.body()!![i].mj1_2_1.isNullOrBlank()) "" else response.body()!![i].mj1_2_1,
                                                if (response.body()!![i].mj1_2_2.isNullOrBlank()) "" else response.body()!![i].mj1_2_2,
                                                if (response.body()!![i].mj1_3_1.isNullOrBlank()) "" else response.body()!![i].mj1_3_1,
                                                if (response.body()!![i].mj1_3_2.isNullOrBlank()) "" else response.body()!![i].mj1_3_2,
                                                if (response.body()!![i].mj1_4_1.isNullOrBlank()) "" else response.body()!![i].mj1_4_1,
                                                if (response.body()!![i].mj1_4_2.isNullOrBlank()) "" else response.body()!![i].mj1_4_2,
                                                if (response.body()!![i].mj1_5_1.isNullOrBlank()) "" else response.body()!![i].mj1_5_1,
                                                if (response.body()!![i].mj1_5_2.isNullOrBlank()) "" else response.body()!![i].mj1_5_2,
                                                if (response.body()!![i].mj1_6_1.isNullOrBlank()) "" else response.body()!![i].mj1_6_1,
                                                if (response.body()!![i].mj1_6_2.isNullOrBlank()) "" else response.body()!![i].mj1_6_2,
                                                if (response.body()!![i].mj1_7_1.isNullOrBlank()) "" else response.body()!![i].mj1_7_1,
                                                if (response.body()!![i].mj1_7_2.isNullOrBlank()) "" else response.body()!![i].mj1_7_2,
                                                if (response.body()!![i].mj1_7_etc.isNullOrBlank()) "" else response.body()!![i].mj1_7_etc,
                                                if (response.body()!![i].mj2_1.isNullOrBlank()) "" else response.body()!![i].mj2_1,
                                                if (response.body()!![i].mj2_2.isNullOrBlank()) "" else response.body()!![i].mj2_2,
                                                if (response.body()!![i].mj2_3.isNullOrBlank()) "" else response.body()!![i].mj2_3,
                                                if (response.body()!![i].mj2_4.isNullOrBlank()) "" else response.body()!![i].mj2_4,
                                                if (response.body()!![i].mj2_5.isNullOrBlank()) "" else response.body()!![i].mj2_5,
                                                if (response.body()!![i].mj2_etc.isNullOrBlank()) "" else response.body()!![i].mj2_etc,
                                                if (response.body()!![i].mj3.isNullOrBlank()) "" else response.body()!![i].mj3,
                                                if (response.body()!![i].mj4.isNullOrBlank()) "" else response.body()!![i].mj4,
                                                if (response.body()!![i].mj4_1_1.isNullOrBlank()) "" else response.body()!![i].mj4_1_1,
                                                if (response.body()!![i].mj4_1_2.isNullOrBlank()) "" else response.body()!![i].mj4_1_2,
                                                if (response.body()!![i].mj4_2_1.isNullOrBlank()) "" else response.body()!![i].mj4_2_1,
                                                if (response.body()!![i].mj4_2_2.isNullOrBlank()) "" else response.body()!![i].mj4_2_2,
                                                if (response.body()!![i].mj4_2_3.isNullOrBlank()) "" else response.body()!![i].mj4_2_3,
                                                if (response.body()!![i].mj5.isNullOrBlank()) "" else response.body()!![i].mj5,
                                                if (response.body()!![i].mj5_1_1.isNullOrBlank()) "" else response.body()!![i].mj5_1_1,
                                                if (response.body()!![i].mj5_1_2.isNullOrBlank()) "" else response.body()!![i].mj5_1_2,
                                                if (response.body()!![i].mj5_2_1.isNullOrBlank()) "" else response.body()!![i].mj5_2_1,
                                                if (response.body()!![i].mj5_2_2.isNullOrBlank()) "" else response.body()!![i].mj5_2_2,
                                                if (response.body()!![i].mj5_2_3.isNullOrBlank()) "" else response.body()!![i].mj5_2_3,
                                                if (response.body()!![i].mj6.isNullOrBlank()) "" else response.body()!![i].mj6,
                                                if (response.body()!![i].mj6_1.isNullOrBlank()) "" else response.body()!![i].mj6_1,
                                                if (response.body()!![i].mj71.isNullOrBlank()) "" else response.body()!![i].mj71,
                                                if (response.body()!![i].mj72.isNullOrBlank()) "" else response.body()!![i].mj72,
                                                if (response.body()!![i].mj73.isNullOrBlank()) "" else response.body()!![i].mj73,
                                                if (response.body()!![i].mj74.isNullOrBlank()) "" else response.body()!![i].mj74,
                                                if (response.body()!![i].mj7_1_11.isNullOrBlank()) "" else response.body()!![i].mj7_1_11,
                                                if (response.body()!![i].mj7_1_12.isNullOrBlank()) "" else response.body()!![i].mj7_1_12,
                                                if (response.body()!![i].mj7_1_13.isNullOrBlank()) "" else response.body()!![i].mj7_1_13,
                                                if (response.body()!![i].mj7_1_14.isNullOrBlank()) "" else response.body()!![i].mj7_1_14,
                                                if (response.body()!![i].mj7_1_21.isNullOrBlank()) "" else response.body()!![i].mj7_1_21,
                                                if (response.body()!![i].mj7_1_22.isNullOrBlank()) "" else response.body()!![i].mj7_1_22,
                                                if (response.body()!![i].mj7_1_23.isNullOrBlank()) "" else response.body()!![i].mj7_1_23,
                                                if (response.body()!![i].mj7_1_24.isNullOrBlank()) "" else response.body()!![i].mj7_1_24,
                                                if (response.body()!![i].mj7_1_31.isNullOrBlank()) "" else response.body()!![i].mj7_1_31,
                                                if (response.body()!![i].mj7_1_32.isNullOrBlank()) "" else response.body()!![i].mj7_1_32,
                                                if (response.body()!![i].mj7_1_33.isNullOrBlank()) "" else response.body()!![i].mj7_1_33,
                                                if (response.body()!![i].mj7_1_34.isNullOrBlank()) "" else response.body()!![i].mj7_1_34,
                                                if (response.body()!![i].mj7_1_41.isNullOrBlank()) "" else response.body()!![i].mj7_1_41,
                                                if (response.body()!![i].mj7_1_42.isNullOrBlank()) "" else response.body()!![i].mj7_1_42,
                                                if (response.body()!![i].mj7_1_43.isNullOrBlank()) "" else response.body()!![i].mj7_1_43,
                                                if (response.body()!![i].mj7_1_44.isNullOrBlank()) "" else response.body()!![i].mj7_1_44,
                                                if (response.body()!![i].mj7_1_51.isNullOrBlank()) "" else response.body()!![i].mj7_1_51,
                                                if (response.body()!![i].mj7_1_52.isNullOrBlank()) "" else response.body()!![i].mj7_1_52,
                                                if (response.body()!![i].mj7_1_53.isNullOrBlank()) "" else response.body()!![i].mj7_1_53,
                                                if (response.body()!![i].mj7_1_54.isNullOrBlank()) "" else response.body()!![i].mj7_1_54,
                                                if (response.body()!![i].mj7_1_etc.isNullOrBlank()) "" else response.body()!![i].mj7_1_etc,
                                                if (response.body()!![i].mj7_2_11.isNullOrBlank()) "" else response.body()!![i].mj7_2_11,
                                                if (response.body()!![i].mj7_2_12.isNullOrBlank()) "" else response.body()!![i].mj7_2_12,
                                                if (response.body()!![i].mj7_2_13.isNullOrBlank()) "" else response.body()!![i].mj7_2_13,
                                                if (response.body()!![i].mj7_2_14.isNullOrBlank()) "" else response.body()!![i].mj7_2_14,
                                                if (response.body()!![i].mj7_2_21.isNullOrBlank()) "" else response.body()!![i].mj7_2_21,
                                                if (response.body()!![i].mj7_2_22.isNullOrBlank()) "" else response.body()!![i].mj7_2_22,
                                                if (response.body()!![i].mj7_2_23.isNullOrBlank()) "" else response.body()!![i].mj7_2_23,
                                                if (response.body()!![i].mj7_2_24.isNullOrBlank()) "" else response.body()!![i].mj7_2_24,
                                                if (response.body()!![i].mj7_2_31.isNullOrBlank()) "" else response.body()!![i].mj7_2_31,
                                                if (response.body()!![i].mj7_2_32.isNullOrBlank()) "" else response.body()!![i].mj7_2_32,
                                                if (response.body()!![i].mj7_2_33.isNullOrBlank()) "" else response.body()!![i].mj7_2_33,
                                                if (response.body()!![i].mj7_2_34.isNullOrBlank()) "" else response.body()!![i].mj7_2_34,
                                                if (response.body()!![i].mj7_2_41.isNullOrBlank()) "" else response.body()!![i].mj7_2_41,
                                                if (response.body()!![i].mj7_2_42.isNullOrBlank()) "" else response.body()!![i].mj7_2_42,
                                                if (response.body()!![i].mj7_2_43.isNullOrBlank()) "" else response.body()!![i].mj7_2_43,
                                                if (response.body()!![i].mj7_2_44.isNullOrBlank()) "" else response.body()!![i].mj7_2_44,
                                                if (response.body()!![i].mj7_2_51.isNullOrBlank()) "" else response.body()!![i].mj7_2_51,
                                                if (response.body()!![i].mj7_2_52.isNullOrBlank()) "" else response.body()!![i].mj7_2_52,
                                                if (response.body()!![i].mj7_2_53.isNullOrBlank()) "" else response.body()!![i].mj7_2_53,
                                                if (response.body()!![i].mj7_2_54.isNullOrBlank()) "" else response.body()!![i].mj7_2_54,
                                                if (response.body()!![i].mj7_2_etc.isNullOrBlank()) "" else response.body()!![i].mj7_2_etc,
                                                if (response.body()!![i].mj8_1.isNullOrBlank()) "" else response.body()!![i].mj8_1,
                                                if (response.body()!![i].mj8_2_1.isNullOrBlank()) "" else response.body()!![i].mj8_2_1,
                                                if (response.body()!![i].mj8_2_2.isNullOrBlank()) "" else response.body()!![i].mj8_2_2,
                                                if (response.body()!![i].mj9_1.isNullOrBlank()) "" else response.body()!![i].mj9_1,
                                                if (response.body()!![i].mj9_2_1.isNullOrBlank()) "" else response.body()!![i].mj9_2_1,
                                                if (response.body()!![i].mj9_2_2.isNullOrBlank()) "" else response.body()!![i].mj9_2_2,
                                                if (response.body()!![i].mj10.isNullOrBlank()) "" else response.body()!![i].mj10,
                                                if (response.body()!![i].mj_name.isNullOrBlank()) "" else response.body()!![i].mj_name,
                                                if (response.body()!![i].mj_jumin.isNullOrBlank()) "" else response.body()!![i].mj_jumin
                                        )
                                )
                            }


                            println("SIZE: ${userDetailList.size}")

                            startActivity(Intent(this@ServerListDetailActivity, CommonExaminationActivity::class.java).putExtra("paper", userDetailList[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                            for (item in userDetailList) {
                                println("SEQ: ${item.mj_no}")
                            }
                            ProgressAction(false)

                        } catch (E: Exception) {
                            ProgressAction(false)
                            println("ERR!!! -> ${E.message}")
                        }
                    }
                } else {
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<ServerPaper_Common>>, t: Throwable) {

                ProgressAction(false)
            }
        })
    }

    fun LoadServer_Elderly(SEQ: String, DATE: String, NAME: String, JUMIN: String) {
        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME
        MAP["SEQ"] = SEQ


        ProgressAction(true)

        OracleUtill().getPaper_elderly().SelectPaper_elderly(MAP).enqueue(object : Callback<ArrayList<ServerPaper_Elderly>> {

            override fun onResponse(call: Call<ArrayList<ServerPaper_Elderly>>, response: Response<ArrayList<ServerPaper_Elderly>>) {

                if (response.isSuccessful) {

                    if (response.body()!!.size == 0) {

                        Toast.makeText(this@ServerListDetailActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    } else {

                        try {

                            println("성공")
                            var userDetailList = ArrayList<ServerPaper_Elderly>()

                            for (i in 0 until response.body()!!.size) {
                                userDetailList.add(
                                        ServerPaper_Elderly(
                                                if (response.body()!![i].mj_date.isNullOrBlank()) "" else response.body()!![i].mj_date,
                                                if (response.body()!![i].mj_no.isNullOrBlank()) "" else response.body()!![i].mj_no,
                                                if (response.body()!![i].mj66_1.isNullOrBlank()) "" else response.body()!![i].mj66_1,
                                                if (response.body()!![i].mj66_2.isNullOrBlank()) "" else response.body()!![i].mj66_2,
                                                if (response.body()!![i].mj66_3_1.isNullOrBlank()) "" else response.body()!![i].mj66_3_1,
                                                if (response.body()!![i].mj66_3_2.isNullOrBlank()) "" else response.body()!![i].mj66_3_2,
                                                if (response.body()!![i].mj66_3_3.isNullOrBlank()) "" else response.body()!![i].mj66_3_3,
                                                if (response.body()!![i].mj66_3_4.isNullOrBlank()) "" else response.body()!![i].mj66_3_4,
                                                if (response.body()!![i].mj66_3_5.isNullOrBlank()) "" else response.body()!![i].mj66_3_5,
                                                if (response.body()!![i].mj66_3_6.isNullOrBlank()) "" else response.body()!![i].mj66_3_6,
                                                if (response.body()!![i].mj66_4.isNullOrBlank()) "" else response.body()!![i].mj66_4,
                                                if (response.body()!![i].mj66_5.isNullOrBlank()) "" else response.body()!![i].mj66_5,
                                                if (response.body()!![i].mj_name.isNullOrBlank()) "" else response.body()!![i].mj_name,
                                                if (response.body()!![i].mj_jumin.isNullOrBlank()) "" else response.body()!![i].mj_jumin
                                        )
                                )
                            }


                            println("SIZE: ${userDetailList.size}")

                            startActivity(Intent(this@ServerListDetailActivity, ElderlyExaminationActivity::class.java).putExtra("paper", userDetailList[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                            for (item in userDetailList) {
                                println("SEQ: ${item.mj_no}")
                            }
                            ProgressAction(false)

                        } catch (E: Exception) {
                            ProgressAction(false)
                            println("ERR!!! -> ${E.message}")
                        }
                    }
                } else {
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<ServerPaper_Elderly>>, t: Throwable) {

                ProgressAction(false)
            }
        })
    }

    fun LoadServer_Life(SEQ: String, DATE: String, NAME: String, JUMIN: String) {
        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME
        MAP["SEQ"] = SEQ


        ProgressAction(true)

        OracleUtill().getPaper_life().SelectPaper_life(MAP).enqueue(object : Callback<ArrayList<ServerPaper_Life>> {

            override fun onResponse(call: Call<ArrayList<ServerPaper_Life>>, response: Response<ArrayList<ServerPaper_Life>>) {

                if (response.isSuccessful) {

                    if (response.body()!!.size == 0) {

                        Toast.makeText(this@ServerListDetailActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    } else {

                        try {

                            println("성공")
                            var userDetailList = ArrayList<ServerPaper_Life>()

                            for (i in 0 until response.body()!!.size) {
                                userDetailList.add(
                                        ServerPaper_Life(
                                                if (response.body()!![i].sg2_spDate.isNullOrBlank()) "" else response.body()!![i].sg2_spDate,
                                                if (response.body()!![i].sg2_spNo.isNullOrBlank()) "" else response.body()!![i].sg2_spNo,
                                                if (response.body()!![i].sg2_spKey.isNullOrBlank()) "" else response.body()!![i].sg2_spKey,
                                                if (response.body()!![i].sg2_spSmoke1.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke1,
                                                if (response.body()!![i].sg2_spSmoke2.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke2,
                                                if (response.body()!![i].sg2_spSmoke3.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke3,
                                                if (response.body()!![i].sg2_spSmoke4.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke4,
                                                if (response.body()!![i].sg2_spSmoke5.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke5,
                                                if (response.body()!![i].sg2_spSmoke6.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke6,
                                                if (response.body()!![i].sg2_spSmoke7.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke7,
                                                if (response.body()!![i].sg2_spSmoke8.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke8,
                                                if (response.body()!![i].sg2_spSmoke9.isNullOrBlank()) "" else response.body()!![i].sg2_spSmoke9,
                                                if (response.body()!![i].sg2_spSmokeSum.isNullOrBlank()) "" else response.body()!![i].sg2_spSmokeSum,
                                                if (response.body()!![i].sg2_spDrink1.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink1,
                                                if (response.body()!![i].sg2_spDrink2_1.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink2_1,
                                                if (response.body()!![i].sg2_spDrink2_2.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink2_2,
                                                if (response.body()!![i].sg2_spDrink3.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink3,
                                                if (response.body()!![i].sg2_spDrink4.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink4,
                                                if (response.body()!![i].sg2_spDrink5.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink5,
                                                if (response.body()!![i].sg2_spDrink6.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink6,
                                                if (response.body()!![i].sg2_spDrink7.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink7,
                                                if (response.body()!![i].sg2_spDrink8.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink8,
                                                if (response.body()!![i].sg2_spDrink9.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink9,
                                                if (response.body()!![i].sg2_spDrink10.isNullOrBlank()) "" else response.body()!![i].sg2_spDrink10,
                                                if (response.body()!![i].sg2_spDrinkSum.isNullOrBlank()) "" else response.body()!![i].sg2_spDrinkSum,
                                                if (response.body()!![i].sg2_spSports1_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports1_1,
                                                if (response.body()!![i].sg2_spSports1_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports1_2,
                                                if (response.body()!![i].sg2_spSports1_3_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports1_3_1,
                                                if (response.body()!![i].sg2_spSports1_3_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports1_3_2,
                                                if (response.body()!![i].sg2_spSports1_4.isNullOrBlank()) "" else response.body()!![i].sg2_spSports1_4,
                                                if (response.body()!![i].sg2_spSports1_5.isNullOrBlank()) "" else response.body()!![i].sg2_spSports1_5,
                                                if (response.body()!![i].sg2_spSports1_6_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports1_6_1,
                                                if (response.body()!![i].sg2_spSports1_6_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports1_6_2,
                                                if (response.body()!![i].sg2_spSports2_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports2_1,
                                                if (response.body()!![i].sg2_spSports2_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports2_2,
                                                if (response.body()!![i].sg2_spSports2_3_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports2_3_1,
                                                if (response.body()!![i].sg2_spSports2_3_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports2_3_2,
                                                if (response.body()!![i].sg2_spSports3_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports3_1,
                                                if (response.body()!![i].sg2_spSports3_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports3_2,
                                                if (response.body()!![i].sg2_spSports3_3_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports3_3_1,
                                                if (response.body()!![i].sg2_spSports3_3_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports3_3_2,
                                                if (response.body()!![i].sg2_spSports3_4.isNullOrBlank()) "" else response.body()!![i].sg2_spSports3_4,
                                                if (response.body()!![i].sg2_spSports3_5.isNullOrBlank()) "" else response.body()!![i].sg2_spSports3_5,
                                                if (response.body()!![i].sg2_spSports3_6_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports3_6_1,
                                                if (response.body()!![i].sg2_spSports3_6_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports3_6_2,
                                                if (response.body()!![i].sg2_spSports4_1_1.isNullOrBlank()) "" else response.body()!![i].sg2_spSports4_1_1,
                                                if (response.body()!![i].sg2_spSports4_1_2.isNullOrBlank()) "" else response.body()!![i].sg2_spSports4_1_2,
                                                if (response.body()!![i].sg2_spSports5.isNullOrBlank()) "" else response.body()!![i].sg2_spSports5,
                                                if (response.body()!![i].sg2_spSports6.isNullOrBlank()) "" else response.body()!![i].sg2_spSports6,
                                                if (response.body()!![i].sg2_spSports7.isNullOrBlank()) "" else response.body()!![i].sg2_spSports7,
                                                if (response.body()!![i].sg2_spSports8.isNullOrBlank()) "" else response.body()!![i].sg2_spSports8,
                                                if (response.body()!![i].sg2_spSports9.isNullOrBlank()) "" else response.body()!![i].sg2_spSports9,
                                                if (response.body()!![i].sg2_spSports10.isNullOrBlank()) "" else response.body()!![i].sg2_spSports10,
                                                if (response.body()!![i].sg2_spSports11.isNullOrBlank()) "" else response.body()!![i].sg2_spSports11,
                                                if (response.body()!![i].sg2_spSports12.isNullOrBlank()) "" else response.body()!![i].sg2_spSports12,
                                                if (response.body()!![i].sg2_spSportsSum.isNullOrBlank()) "" else response.body()!![i].sg2_spSportsSum,
                                                if (response.body()!![i].sg2_spFood1.isNullOrBlank()) "" else response.body()!![i].sg2_spFood1,
                                                if (response.body()!![i].sg2_spFood2.isNullOrBlank()) "" else response.body()!![i].sg2_spFood2,
                                                if (response.body()!![i].sg2_spFood3.isNullOrBlank()) "" else response.body()!![i].sg2_spFood3,
                                                if (response.body()!![i].sg2_spFood4.isNullOrBlank()) "" else response.body()!![i].sg2_spFood4,
                                                if (response.body()!![i].sg2_spFood5.isNullOrBlank()) "" else response.body()!![i].sg2_spFood5,
                                                if (response.body()!![i].sg2_spFood6.isNullOrBlank()) "" else response.body()!![i].sg2_spFood6,
                                                if (response.body()!![i].sg2_spFood7.isNullOrBlank()) "" else response.body()!![i].sg2_spFood7,
                                                if (response.body()!![i].sg2_spFood8.isNullOrBlank()) "" else response.body()!![i].sg2_spFood8,
                                                if (response.body()!![i].sg2_spFood9.isNullOrBlank()) "" else response.body()!![i].sg2_spFood9,
                                                if (response.body()!![i].sg2_spFood10.isNullOrBlank()) "" else response.body()!![i].sg2_spFood10,
                                                if (response.body()!![i].sg2_spFood11.isNullOrBlank()) "" else response.body()!![i].sg2_spFood11,
                                                if (response.body()!![i].sg2_spFoodSum.isNullOrBlank()) "" else response.body()!![i].sg2_spFoodSum,
                                                if (response.body()!![i].sg2_spFat1.isNullOrBlank()) "" else response.body()!![i].sg2_spFat1,
                                                if (response.body()!![i].sg2_spFat2.isNullOrBlank()) "" else response.body()!![i].sg2_spFat2,
                                                if (response.body()!![i].sg2_spFat3.isNullOrBlank()) "" else response.body()!![i].sg2_spFat3,
                                                if (response.body()!![i].sg2_name.isNullOrBlank()) "" else response.body()!![i].sg2_name,
                                                if (response.body()!![i].sg2_jumin.isNullOrBlank()) "" else response.body()!![i].sg2_jumin
                                        )
                                )
                            }


                            println("SIZE: ${userDetailList.size}")
                            startActivity(Intent(this@ServerListDetailActivity, ExerciseExaminationActivity::class.java).putExtra("paper", userDetailList[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                            for (item in userDetailList) {
                                println("SEQ: ${item.sg2_spNo}")
                            }
                            ProgressAction(false)

                        } catch (E: Exception) {
                            ProgressAction(false)
                            println("ERR!!! -> ${E.message}")
                        }
                    }
                } else {
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<ServerPaper_Life>>, t: Throwable) {

                ProgressAction(false)
            }
        })
    }

    fun LoadServer_Mental(SEQ: String, DATE: String, NAME: String, JUMIN: String) {
        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME
        MAP["SEQ"] = SEQ


        ProgressAction(true)

        OracleUtill().getPaper_mental().SelectPaper_mental(MAP).enqueue(object : Callback<ArrayList<ServerPaper_Mental>> {

            override fun onResponse(call: Call<ArrayList<ServerPaper_Mental>>, response: Response<ArrayList<ServerPaper_Mental>>) {

                if (response.isSuccessful) {

                    if (response.body()!!.size == 0) {

                        Toast.makeText(this@ServerListDetailActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    } else {

                        try {

                            println("성공")
                            var userDetailList = ArrayList<ServerPaper_Mental>()

                            for (i in 0 until response.body()!!.size) {
                                userDetailList.add(
                                        ServerPaper_Mental(
                                                if (response.body()!![i].mj_date.isNullOrBlank()) "" else response.body()!![i].mj_date,
                                                if (response.body()!![i].mj_no.isNullOrBlank()) "" else response.body()!![i].mj_no,
                                                if (response.body()!![i].mj_mtl_1.isNullOrBlank()) "" else response.body()!![i].mj_mtl_1,
                                                if (response.body()!![i].mj_mtl_2.isNullOrBlank()) "" else response.body()!![i].mj_mtl_2,
                                                if (response.body()!![i].mj_mtl_3.isNullOrBlank()) "" else response.body()!![i].mj_mtl_3,
                                                if (response.body()!![i].mj_mtl_4.isNullOrBlank()) "" else response.body()!![i].mj_mtl_4,
                                                if (response.body()!![i].mj_mtl_5.isNullOrBlank()) "" else response.body()!![i].mj_mtl_5,
                                                if (response.body()!![i].mj_mtl_6.isNullOrBlank()) "" else response.body()!![i].mj_mtl_6,
                                                if (response.body()!![i].mj_mtl_7.isNullOrBlank()) "" else response.body()!![i].mj_mtl_7,
                                                if (response.body()!![i].mj_mtl_8.isNullOrBlank()) "" else response.body()!![i].mj_mtl_8,
                                                if (response.body()!![i].mj_mtl_9.isNullOrBlank()) "" else response.body()!![i].mj_mtl_9,
                                                if (response.body()!![i].mj_mtl_sum.isNullOrBlank()) "" else response.body()!![i].mj_mtl_sum,
                                                if (response.body()!![i].mj_name.isNullOrBlank()) "" else response.body()!![i].mj_name,
                                                if (response.body()!![i].mj_jumin.isNullOrBlank()) "" else response.body()!![i].mj_jumin
                                        )
                                )
                            }


                            println("SIZE: ${userDetailList.size}")

                            startActivity(Intent(this@ServerListDetailActivity, MentalExaminationActivity::class.java).putExtra("paper", userDetailList[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                            for (item in userDetailList) {
                                println("SEQ: ${item.mj_no}")
                            }
                            ProgressAction(false)

                        } catch (E: Exception) {
                            ProgressAction(false)
                            println("ERR!!! -> ${E.message}")
                        }
                    }
                } else {
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<ServerPaper_Mental>>, t: Throwable) {

                ProgressAction(false)
            }
        })
    }

    fun LoadServer_Oral(SEQ: String, DATE: String, NAME: String, JUMIN: String) {
        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME
        MAP["SEQ"] = SEQ


        ProgressAction(true)

        OracleUtill().getPaper_oral().SelectPaper_oral(MAP).enqueue(object : Callback<ArrayList<ServerPaper_Oral>> {

            override fun onResponse(call: Call<ArrayList<ServerPaper_Oral>>, response: Response<ArrayList<ServerPaper_Oral>>) {

                if (response.isSuccessful) {

                    if (response.body()!!.size == 0) {

                        Toast.makeText(this@ServerListDetailActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        ProgressAction(false)

                    } else {

                        try {

                            println("성공")
                            var userDetailList = ArrayList<ServerPaper_Oral>()

                            for (i in 0 until response.body()!!.size) {
                                userDetailList.add(
                                        ServerPaper_Oral(
                                                if (response.body()!![i].oral_date.isNullOrBlank()) "" else response.body()!![i].oral_date,
                                                if (response.body()!![i].oral_bun_no.isNullOrBlank()) "" else response.body()!![i].oral_bun_no,
                                                if (response.body()!![i].oral_email.isNullOrBlank()) "" else response.body()!![i].oral_email,
                                                if (response.body()!![i].oral_jungyn.isNullOrBlank()) "" else response.body()!![i].oral_jungyn,
                                                if (response.body()!![i].oral_1.isNullOrBlank()) "" else response.body()!![i].oral_1,
                                                if (response.body()!![i].oral_2.isNullOrBlank()) "" else response.body()!![i].oral_2,
                                                if (response.body()!![i].oral_3.isNullOrBlank()) "" else response.body()!![i].oral_3,
                                                if (response.body()!![i].oral_4.isNullOrBlank()) "" else response.body()!![i].oral_4,
                                                if (response.body()!![i].oral_5.isNullOrBlank()) "" else response.body()!![i].oral_5,
                                                if (response.body()!![i].oral_6.isNullOrBlank()) "" else response.body()!![i].oral_6,
                                                if (response.body()!![i].oral_7.isNullOrBlank()) "" else response.body()!![i].oral_7,
                                                if (response.body()!![i].oral_8.isNullOrBlank()) "" else response.body()!![i].oral_8,
                                                if (response.body()!![i].oral_9.isNullOrBlank()) "" else response.body()!![i].oral_9,
                                                if (response.body()!![i].oral_10.isNullOrBlank()) "" else response.body()!![i].oral_10,
                                                if (response.body()!![i].oral_11.isNullOrBlank()) "" else response.body()!![i].oral_11,
                                                if (response.body()!![i].oral_12.isNullOrBlank()) "" else response.body()!![i].oral_12,
                                                if (response.body()!![i].oral_13.isNullOrBlank()) "" else response.body()!![i].oral_13,
                                                if (response.body()!![i].oral_14.isNullOrBlank()) "" else response.body()!![i].oral_14,
                                                if (response.body()!![i].oral_15.isNullOrBlank()) "" else response.body()!![i].oral_15,
                                                if (response.body()!![i].oral_16.isNullOrBlank()) "" else response.body()!![i].oral_16,
                                                if (response.body()!![i].oral_17.isNullOrBlank()) "" else response.body()!![i].oral_17,
                                                if (response.body()!![i].oral_18.isNullOrBlank()) "" else response.body()!![i].oral_18,
                                                if (response.body()!![i].oral_19.isNullOrBlank()) "" else response.body()!![i].oral_19,
                                                if (response.body()!![i].oral_Remark.isNullOrBlank()) "" else response.body()!![i].oral_Remark,
                                                if (response.body()!![i].oral_name.isNullOrBlank()) "" else response.body()!![i].oral_name,
                                                if (response.body()!![i].oral_jumin.isNullOrBlank()) "" else response.body()!![i].oral_jumin
                                        )
                                )
                            }


                            println("SIZE: ${userDetailList.size}")

                            startActivity(Intent(this@ServerListDetailActivity, OralExaminationActivity::class.java).putExtra("paper", userDetailList[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                            for (item in userDetailList) {
                                println("SEQ: ${item.oral_bun_no}")
                            }
                            ProgressAction(false)

                        } catch (E: Exception) {
                            ProgressAction(false)
                            println("ERR!!! -> ${E.message}")
                        }
                    }
                } else {
                    ProgressAction(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<ServerPaper_Oral>>, t: Throwable) {

                ProgressAction(false)
            }
        })
    }

    fun ProgressAction(isShow: Boolean) {
        if (isShow) {

            Progress_circle.visibility = View.VISIBLE
            Progress_bg.visibility = View.VISIBLE
            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            Progress_circle.visibility = View.GONE
            Progress_bg.visibility = View.GONE
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

}
