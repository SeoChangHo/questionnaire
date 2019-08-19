package com.fineinsight.zzango.questionnaire.LocalList

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.*
import com.fineinsight.zzango.questionnaire.DataClass.PublicDataInfo
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.progress_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : RootActivity() {


    var papers = ArrayList<Paper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        ListSetting(false)
        btnSetting()
        SelectAllSetting()
    }

    fun ListSetting(bool:Boolean)
    {
        papers = ArrayList<Paper>()


        val sql_db = LocalDBhelper(this).writableDatabase

        val recyclerView = findViewById<RecyclerView>(R.id.recyclertView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        val data = LocalDBhelper(this).checkLocalList(sql_db!!)
        data.moveToFirst()

        while(!data.isAfterLast){
                papers.add(Paper(bool,
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getBlob(data.getColumnIndex("signature")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("name"))))

            data.moveToNext()

        }

        sql_db.close()

        val adapter = CustomAdapter(papers, this)

        recyclerView.adapter = adapter
    }

    fun btnSetting()
    {
        val sql_db = LocalDBhelper(this).writableDatabase

        var btnSave = findViewById<Button>(R.id.btnSave)
        var btnDelete = findViewById<Button>(R.id.btnDelete)
        var txtBottomMent = findViewById<TextView>(R.id.txtBottomMent)

        //저장하는거
        btnSave.setOnClickListener {

            if(wfm!!.isWifiEnabled) {

                var removeArr = ArrayList<Paper>()
                var SaveArr = ArrayList<Any>()




                for (item in papers) {
                    if (item.isChecked == true) {
                        removeArr.add(item)

                        println("&*&*&*&*&*&*")
                        println(item)
                        println("&*&*&*&*&*&*")
                    }
                }

                for (i in 0 until removeArr.size) {
                    var CategoryArr = ArrayList<Any>()

                    SaveArr.add(CategoryArr)
                }




                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)



                val mdialogView = layoutInflater.inflate(R.layout.progress_dialog, null)

                val mBuilder = AlertDialog.Builder(this)
                        .setView(mdialogView)
                        .setCancelable(false)

                val mAlertDialog = mBuilder.show()


                UploadPaper(SaveArr, removeArr, 0, SaveArr.size, mdialogView, mAlertDialog)

            }else{

                wifiCheck()

            }

        }





        //삭제하는거
        btnDelete.setOnClickListener{

            var removeArr = ArrayList<Paper>()

            for(item in papers)
            {
                if(item.isChecked==true)
                {
                    removeArr.add(item)
                }
            }
            LocalDBhelper(this).deletePaper(sql_db!!, removeArr)
            ListSetting(false)
            btnSave.visibility = View.GONE
            btnDelete.visibility = View.GONE
            constraintLayout_bottom.visibility = View.GONE
            txtBottomMent.text = "문진표를 선택해주세요."
            select_all_checkbox.isChecked = false
        }
    }


    //재귀호출함수
    fun UploadPaper(SaveArr:ArrayList<Any>, removeArr:ArrayList<Paper>, startIndex:Int, TotalIndex:Int, dialogView:View, Alertdialog:AlertDialog)
    {
        println("업로드 들어옴")
        println("Array의 크기는 "+TotalIndex.toString()+" 개 입니다.")
        println("현재는 "+startIndex.toString()+" 번째 입니다.")



        dialogView.txtMent.text = "${(startIndex+1)}/${TotalIndex} 저장중.."

        val double_start_index = (startIndex+1).toDouble()
        val double_total_index = TotalIndex.toDouble()

        dialogView.stickProgress.progress = ((double_start_index/double_total_index)*100).toInt()

        println("진행률: ${(double_start_index/double_total_index)*100}")









        var body = ArrayList<Any>()


        /*
        body의 1번째 Row -> PublicDataInfo
        val hospital: String,
        val name: String,
        val first_serial: String,
        val last_serial: String,
        val signature: ByteArray,
        val exam_no: String
         */



        body.add(PublicDataInfo(
                MainActivity.hospital,
                removeArr[startIndex].name,
                removeArr[startIndex].first_serial,
                removeArr[startIndex].last_serial,
                removeArr[startIndex].signature,
                removeArr[startIndex].exam_no))

        var SELECT_COMMON = Return_COMMON(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_MENTAL = Return_MENTAL(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_COGNITIVE = Return_COGNITIVE(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_ELDERLY = Return_ELDERLY(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_EXERCISE = Return_EXERCISE(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_NUTRITION = Return_NUTRITION(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_SMOKING = Return_SMOKING(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_DRINKING = Return_DRINKING(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_ORAL = Return_ORAL(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)
        var SELECT_CANCER = Return_CANCER(removeArr[startIndex].exam_no, removeArr[startIndex].name, removeArr[startIndex].first_serial)



        body.add(
                if (SELECT_COMMON.size>0)
                {
                    SELECT_COMMON[0]
                }
                else
                {
                    Paper_COMMON("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_MENTAL.size>0)
                {
                    SELECT_MENTAL[0]
                }
                else
                {
                    Paper_MENTAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_COGNITIVE.size>0)
                {
                    SELECT_COGNITIVE[0]
                }
                else
                {
                    Paper_COGNITIVE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_ELDERLY.size>0)
                {
                    SELECT_ELDERLY[0]
                }
                else
                {
                    Paper_ELDERLY("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_EXERCISE.size>0)
                {
                    SELECT_EXERCISE[0]
                }
                else
                {
                    Paper_EXERCISE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_NUTRITION.size>0)
                {
                    SELECT_NUTRITION[0]
                }
                else
                {
                    Paper_NUTRITION("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_SMOKING.size>0)
                {
                    SELECT_SMOKING[0]
                }
                else
                {
                    Paper_SMOKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_DRINKING.size>0)
                {
                    SELECT_DRINKING[0]
                }
                else
                {
                    Paper_DRINKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_ORAL.size>0)
                {
                    SELECT_ORAL[0]
                }
                else
                {
                    Paper_ORAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )

        body.add(
                if (SELECT_CANCER.size>0)
                {
                    SELECT_CANCER[0]
                }
                else
                {
                    Paper_CANCER("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                }
        )




        OracleUtill().newsave_papers().newsavePapersServer(body).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        println(startIndex.toString()+"번째 요청 실패")
                        Alertdialog.dismiss()

                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Toast.makeText(this@ListActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        //LocalDBhelper(this@ListActivity).deletePaperEach(sql_db!!, removeArr[startIndex])

                        if(startIndex+1<TotalIndex)
                        {
                            //할게 더 남아서 재귀호출


                            UploadPaper(SaveArr,  removeArr, startIndex+1, TotalIndex, dialogView, Alertdialog)
                        }
                        else
                        {
                            //끝
                            println("모든 업로드가 완료되었습니다.")
                            Alertdialog.dismiss()

                            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this@ListActivity, "전송 완료", Toast.LENGTH_LONG).show()

                            //LocalDBhelper(this@ListActivity).deletePaper(sql_db!!, removeArr)

                            ListSetting(false)
                            btnSave.visibility = View.GONE
                            btnDelete.visibility = View.GONE
                            txtBottomMent.text = "문진표를 선택해주세요."
                            select_all_checkbox.isChecked = false
                            constraintLayout_bottom.visibility = View.GONE
                        }
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(this@ListActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }
        })
    }




    //전체선택
    fun SelectAllSetting()
    {
        select_all_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

                //전체선택
                if(isChecked)
                {
                    ListSetting(true)

                    var count = 0
                    for (item in papers) {
                        if (item.isChecked == true) {
                            count++
                        }
                    }
                    if(count>0)
                    {
                        constraintLayout_bottom.visibility = View.VISIBLE
                        txtBottomMent.text = "선택한 " + count.toString() + "개의 문진표를"
                        btnSave.visibility = View.VISIBLE
                        btnDelete.visibility = View.VISIBLE
                    }
                }
                else//전체선택 해제
                {
                    var count = 0

                    for(item in papers)
                    {
                        if(item.isChecked)
                        {
                            count++
                        }
                    }

                    if(count==papers.size)
                    {
                        ListSetting(false)
                    }

                    btnSave.visibility = View.GONE
                    btnDelete.visibility = View.GONE
                    txtBottomMent.text = "문진표를 선택해주세요."
                    constraintLayout_bottom.visibility = View.GONE
                }
            }
    }

//    override fun onBackPressed() {
//        if(Progress_bg.visibility != View.VISIBLE){
//            super.onBackPressed()
//        }
//    }


    fun Return_ORAL(exam_no:String, name:String, jumin:String): ArrayList<Paper_ORAL>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_ORAL>()

        val data = LocalDBhelper(this).Select_Local_ORAL(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_ORAL(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("oral_1")),
                    data.getString(data.getColumnIndex("oral_2")),
                    data.getString(data.getColumnIndex("oral_3")),
                    data.getString(data.getColumnIndex("oral_4")),
                    data.getString(data.getColumnIndex("oral_5")),
                    data.getString(data.getColumnIndex("oral_6")),
                    data.getString(data.getColumnIndex("oral_7")),
                    data.getString(data.getColumnIndex("oral_8")),
                    data.getString(data.getColumnIndex("oral_9")),
                    data.getString(data.getColumnIndex("oral_10")),
                    data.getString(data.getColumnIndex("oral_11")),
                    data.getString(data.getColumnIndex("oral_12")),
                    data.getString(data.getColumnIndex("oral_13")),
                    data.getString(data.getColumnIndex("oral_14")),
                    data.getString(data.getColumnIndex("oral_15")),
                    data.getString(data.getColumnIndex("oral_16"))
            ))
            data.moveToNext()
        }


        sql_db.close()


        return PaperArray
    }

    fun Return_COMMON(exam_no:String, name:String, jumin:String): ArrayList<Paper_COMMON>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_COMMON>()

        val data = LocalDBhelper(this).Select_Local_COMMON(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){


            PaperArray.add(Paper_COMMON(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("mj1_1_1")),
                    data.getString(data.getColumnIndex("mj1_1_2")),
                    data.getString(data.getColumnIndex("mj1_2_1")),
                    data.getString(data.getColumnIndex("mj1_2_2")),
                    data.getString(data.getColumnIndex("mj1_3_1")),
                    data.getString(data.getColumnIndex("mj1_3_2")),
                    data.getString(data.getColumnIndex("mj1_4_1")),
                    data.getString(data.getColumnIndex("mj1_4_2")),
                    data.getString(data.getColumnIndex("mj1_5_1")),
                    data.getString(data.getColumnIndex("mj1_5_2")),
                    data.getString(data.getColumnIndex("mj1_6_1")),
                    data.getString(data.getColumnIndex("mj1_6_2")),
                    data.getString(data.getColumnIndex("mj1_7_1")),
                    data.getString(data.getColumnIndex("mj1_7_2")),
                    data.getString(data.getColumnIndex("mj2_1")),
                    data.getString(data.getColumnIndex("mj2_2")),
                    data.getString(data.getColumnIndex("mj2_3")),
                    data.getString(data.getColumnIndex("mj2_4")),
                    data.getString(data.getColumnIndex("mj2_5")),
                    data.getString(data.getColumnIndex("mj3")),
                    data.getString(data.getColumnIndex("mj4")),
                    data.getString(data.getColumnIndex("mj4_1_1")),
                    data.getString(data.getColumnIndex("mj4_1_2")),
                    data.getString(data.getColumnIndex("mj4_2_1")),
                    data.getString(data.getColumnIndex("mj4_2_2")),
                    data.getString(data.getColumnIndex("mj4_2_3")),
                    data.getString(data.getColumnIndex("mj5")),
                    data.getString(data.getColumnIndex("mj5_1_1")),
                    data.getString(data.getColumnIndex("mj5_1_2")),
                    data.getString(data.getColumnIndex("mj5_2_1")),
                    data.getString(data.getColumnIndex("mj5_2_2")),
                    data.getString(data.getColumnIndex("mj5_2_3")),
                    data.getString(data.getColumnIndex("mj6")),
                    data.getString(data.getColumnIndex("mj6_1")),
                    data.getString(data.getColumnIndex("mj71")),
                    data.getString(data.getColumnIndex("mj72")),
                    data.getString(data.getColumnIndex("mj73")),
                    data.getString(data.getColumnIndex("mj74")),
                    data.getString(data.getColumnIndex("mj7_1_11")),
                    data.getString(data.getColumnIndex("mj7_1_12")),
                    data.getString(data.getColumnIndex("mj7_1_13")),
                    data.getString(data.getColumnIndex("mj7_1_14")),
                    data.getString(data.getColumnIndex("mj7_1_21")),
                    data.getString(data.getColumnIndex("mj7_1_22")),
                    data.getString(data.getColumnIndex("mj7_1_23")),
                    data.getString(data.getColumnIndex("mj7_1_24")),
                    data.getString(data.getColumnIndex("mj7_1_31")),
                    data.getString(data.getColumnIndex("mj7_1_32")),
                    data.getString(data.getColumnIndex("mj7_1_33")),
                    data.getString(data.getColumnIndex("mj7_1_34")),
                    data.getString(data.getColumnIndex("mj7_1_41")),
                    data.getString(data.getColumnIndex("mj7_1_42")),
                    data.getString(data.getColumnIndex("mj7_1_43")),
                    data.getString(data.getColumnIndex("mj7_1_44")),
                    data.getString(data.getColumnIndex("mj7_1_51")),
                    data.getString(data.getColumnIndex("mj7_1_52")),
                    data.getString(data.getColumnIndex("mj7_1_53")),
                    data.getString(data.getColumnIndex("mj7_1_54")),
                    data.getString(data.getColumnIndex("mj7_1_etc")),
                    data.getString(data.getColumnIndex("mj7_2_11")),
                    data.getString(data.getColumnIndex("mj7_2_12")),
                    data.getString(data.getColumnIndex("mj7_2_13")),
                    data.getString(data.getColumnIndex("mj7_2_14")),
                    data.getString(data.getColumnIndex("mj7_2_21")),
                    data.getString(data.getColumnIndex("mj7_2_22")),
                    data.getString(data.getColumnIndex("mj7_2_23")),
                    data.getString(data.getColumnIndex("mj7_2_24")),
                    data.getString(data.getColumnIndex("mj7_2_31")),
                    data.getString(data.getColumnIndex("mj7_2_32")),
                    data.getString(data.getColumnIndex("mj7_2_33")),
                    data.getString(data.getColumnIndex("mj7_2_34")),
                    data.getString(data.getColumnIndex("mj7_2_41")),
                    data.getString(data.getColumnIndex("mj7_2_42")),
                    data.getString(data.getColumnIndex("mj7_2_43")),
                    data.getString(data.getColumnIndex("mj7_2_44")),
                    data.getString(data.getColumnIndex("mj7_2_51")),
                    data.getString(data.getColumnIndex("mj7_2_52")),
                    data.getString(data.getColumnIndex("mj7_2_53")),
                    data.getString(data.getColumnIndex("mj7_2_54")),
                    data.getString(data.getColumnIndex("mj7_2_etc")),
                    data.getString(data.getColumnIndex("mj8_1")),
                    data.getString(data.getColumnIndex("mj8_2_1")),
                    data.getString(data.getColumnIndex("mj8_2_2")),
                    data.getString(data.getColumnIndex("mj9_1")),
                    data.getString(data.getColumnIndex("mj9_2_1")),
                    data.getString(data.getColumnIndex("mj9_2_2")),
                    data.getString(data.getColumnIndex("mj10"))
            ))
            data.moveToNext()
        }
        sql_db.close()
        return PaperArray
    }

    fun Return_COGNITIVE(exam_no:String, name:String, jumin:String): ArrayList<Paper_COGNITIVE>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_COGNITIVE>()

        val data = LocalDBhelper(this).Select_Local_COGNITIVE(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_COGNITIVE(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("mj_inji_1")),
                    data.getString(data.getColumnIndex("mj_inji_2")),
                    data.getString(data.getColumnIndex("mj_inji_3")),
                    data.getString(data.getColumnIndex("mj_inji_4")),
                    data.getString(data.getColumnIndex("mj_inji_5")),
                    data.getString(data.getColumnIndex("mj_inji_6")),
                    data.getString(data.getColumnIndex("mj_inji_7")),
                    data.getString(data.getColumnIndex("mj_inji_8")),
                    data.getString(data.getColumnIndex("mj_inji_9")),
                    data.getString(data.getColumnIndex("mj_inji_10")),
                    data.getString(data.getColumnIndex("mj_inji_11")),
                    data.getString(data.getColumnIndex("mj_inji_12")),
                    data.getString(data.getColumnIndex("mj_inji_13")),
                    data.getString(data.getColumnIndex("mj_inji_14")),
                    data.getString(data.getColumnIndex("mj_inji_15")),
                    data.getString(data.getColumnIndex("mj_inji_sum"))
            ))
            data.moveToNext()
        }

        sql_db.close()
        return PaperArray
    }


    fun Return_MENTAL(exam_no:String, name:String, jumin:String): ArrayList<Paper_MENTAL>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_MENTAL>()

        val data = LocalDBhelper(this).Select_Local_MENTAL(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_MENTAL(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("mj_mtl_1")),
                    data.getString(data.getColumnIndex("mj_mtl_2")),
                    data.getString(data.getColumnIndex("mj_mtl_3")),
                    data.getString(data.getColumnIndex("mj_mtl_4")),
                    data.getString(data.getColumnIndex("mj_mtl_5")),
                    data.getString(data.getColumnIndex("mj_mtl_6")),
                    data.getString(data.getColumnIndex("mj_mtl_7")),
                    data.getString(data.getColumnIndex("mj_mtl_8")),
                    data.getString(data.getColumnIndex("mj_mtl_9")),
                    data.getString(data.getColumnIndex("mj_mtl_sum"))
            ))
            data.moveToNext()
        }

        sql_db.close()
        return PaperArray
    }


    fun Return_ELDERLY(exam_no:String, name:String, jumin:String): ArrayList<Paper_ELDERLY>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_ELDERLY>()

        val data = LocalDBhelper(this).Select_Local_ELDERLY(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_ELDERLY(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("mj66_1")),
                    data.getString(data.getColumnIndex("mj66_2")),
                    data.getString(data.getColumnIndex("mj66_3_1")),
                    data.getString(data.getColumnIndex("mj66_3_2")),
                    data.getString(data.getColumnIndex("mj66_3_3")),
                    data.getString(data.getColumnIndex("mj66_3_4")),
                    data.getString(data.getColumnIndex("mj66_3_5")),
                    data.getString(data.getColumnIndex("mj66_3_6")),
                    data.getString(data.getColumnIndex("mj66_4")),
                    data.getString(data.getColumnIndex("mj66_5"))
            ))
            data.moveToNext()
        }

        sql_db.close()
        return PaperArray
    }

    fun Return_SMOKING(exam_no:String, name:String, jumin:String): ArrayList<Paper_SMOKING>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_SMOKING>()

        val data = LocalDBhelper(this).Select_Local_SMOKING(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_SMOKING(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("sg2_spSmoke1")),
                    data.getString(data.getColumnIndex("sg2_spSmoke2")),
                    data.getString(data.getColumnIndex("sg2_spSmoke3")),
                    data.getString(data.getColumnIndex("sg2_spSmoke4")),
                    data.getString(data.getColumnIndex("sg2_spSmoke5")),
                    data.getString(data.getColumnIndex("sg2_spSmoke6")),
                    data.getString(data.getColumnIndex("sg2_spSmoke7")),
                    data.getString(data.getColumnIndex("sg2_spSmoke8")),
                    data.getString(data.getColumnIndex("sg2_spSmokeSum"))
            ))
            data.moveToNext()
        }

        sql_db.close()
        return PaperArray
    }

    fun Return_DRINKING(exam_no:String, name:String, jumin:String): ArrayList<Paper_DRINKING>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_DRINKING>()

        val data = LocalDBhelper(this).Select_Local_DRINKING(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_DRINKING(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("sg2_spDrink1")),
                    data.getString(data.getColumnIndex("sg2_spDrink2_1")),
                    data.getString(data.getColumnIndex("sg2_spDrink2_2")),
                    data.getString(data.getColumnIndex("sg2_spDrink3")),
                    data.getString(data.getColumnIndex("sg2_spDrink4")),
                    data.getString(data.getColumnIndex("sg2_spDrink5")),
                    data.getString(data.getColumnIndex("sg2_spDrink6")),
                    data.getString(data.getColumnIndex("sg2_spDrink7")),
                    data.getString(data.getColumnIndex("sg2_spDrink8")),
                    data.getString(data.getColumnIndex("sg2_spDrink9")),
                    data.getString(data.getColumnIndex("sg2_spDrink10")),
                    data.getString(data.getColumnIndex("sg2_spDrinkSum"))
            ))
            data.moveToNext()
        }

        sql_db.close()
        return PaperArray
    }

    fun Return_CANCER(exam_no:String, name:String, jumin:String): ArrayList<Paper_CANCER>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var Paper = ArrayList<Paper_CANCER>()

        val data = LocalDBhelper(this).Select_Local_CANCER(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            Paper.add(Paper_CANCER(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("ck1")),
                    data.getString(data.getColumnIndex("ck1_1")),
                    data.getString(data.getColumnIndex("ck2")),
                    data.getString(data.getColumnIndex("ck2_1")),
                    data.getString(data.getColumnIndex("ck3_1")),
                    data.getString(data.getColumnIndex("ck3_1_1")),
                    data.getString(data.getColumnIndex("ck3_1_2")),
                    data.getString(data.getColumnIndex("ck3_1_3")),
                    data.getString(data.getColumnIndex("ck3_1_4")),
                    data.getString(data.getColumnIndex("ck3_1_5")),
                    data.getString(data.getColumnIndex("ck3_2")),
                    data.getString(data.getColumnIndex("ck3_2_1")),
                    data.getString(data.getColumnIndex("ck3_2_2")),
                    data.getString(data.getColumnIndex("ck3_2_3")),
                    data.getString(data.getColumnIndex("ck3_2_4")),
                    data.getString(data.getColumnIndex("ck3_2_5")),
                    data.getString(data.getColumnIndex("ck3_3")),
                    data.getString(data.getColumnIndex("ck3_3_1")),
                    data.getString(data.getColumnIndex("ck3_3_2")),
                    data.getString(data.getColumnIndex("ck3_3_3")),
                    data.getString(data.getColumnIndex("ck3_3_4")),
                    data.getString(data.getColumnIndex("ck3_3_5")),
                    data.getString(data.getColumnIndex("ck3_4")),
                    data.getString(data.getColumnIndex("ck3_4_1")),
                    data.getString(data.getColumnIndex("ck3_4_2")),
                    data.getString(data.getColumnIndex("ck3_4_3")),
                    data.getString(data.getColumnIndex("ck3_4_4")),
                    data.getString(data.getColumnIndex("ck3_4_5")),
                    data.getString(data.getColumnIndex("ck3_5")),
                    data.getString(data.getColumnIndex("ck3_5_1")),
                    data.getString(data.getColumnIndex("ck3_5_2")),
                    data.getString(data.getColumnIndex("ck3_5_3")),
                    data.getString(data.getColumnIndex("ck3_5_4")),
                    data.getString(data.getColumnIndex("ck3_5_5")),
                    data.getString(data.getColumnIndex("ck3_6")),
                    data.getString(data.getColumnIndex("ck3_6_1")),
                    data.getString(data.getColumnIndex("ck3_6_2")),
                    data.getString(data.getColumnIndex("ck3_6_3")),
                    data.getString(data.getColumnIndex("ck3_6_4")),
                    data.getString(data.getColumnIndex("ck3_6_5")),
                    data.getString(data.getColumnIndex("ck3_6_kita")),
                    data.getString(data.getColumnIndex("ck4_1")),
                    data.getString(data.getColumnIndex("ck4_2")),
                    data.getString(data.getColumnIndex("ck4_3")),
                    data.getString(data.getColumnIndex("ck4_4")),
                    data.getString(data.getColumnIndex("ck4_5")),
                    data.getString(data.getColumnIndex("ck4_6")),
                    data.getString(data.getColumnIndex("ck4_7")),
                    data.getString(data.getColumnIndex("ck4_8")),
                    data.getString(data.getColumnIndex("ck5_1")),
                    data.getString(data.getColumnIndex("ck5_2")),
                    data.getString(data.getColumnIndex("ck5_3")),
                    data.getString(data.getColumnIndex("ck5_4")),
                    data.getString(data.getColumnIndex("ck5_5")),
                    data.getString(data.getColumnIndex("ck5_6")),
                    data.getString(data.getColumnIndex("ck6_1")),
                    data.getString(data.getColumnIndex("ck6_2")),
                    data.getString(data.getColumnIndex("ck6_3")),
                    data.getString(data.getColumnIndex("ck6_4")),
                    data.getString(data.getColumnIndex("ck6_5")),
                    data.getString(data.getColumnIndex("ck6_6")),
                    data.getString(data.getColumnIndex("ck7_1")),
                    data.getString(data.getColumnIndex("ck7_2")),
                    data.getString(data.getColumnIndex("ck7_3")),
                    data.getString(data.getColumnIndex("ck7_4")),
                    data.getString(data.getColumnIndex("ck7_5")),
                    data.getString(data.getColumnIndex("ck7_6")),
                    data.getString(data.getColumnIndex("ck8_1")),
                    data.getString(data.getColumnIndex("ck8_2")),
                    data.getString(data.getColumnIndex("ck9_1")),
                    data.getString(data.getColumnIndex("ck9_2")),
                    data.getString(data.getColumnIndex("ck10")),
                    data.getString(data.getColumnIndex("ck11")),
                    data.getString(data.getColumnIndex("ck12")),
                    data.getString(data.getColumnIndex("ck13")),
                    data.getString(data.getColumnIndex("ck14"))
            ))
            data.moveToNext()
        }
        sql_db.close()
        return Paper
    }

    fun Return_EXERCISE(exam_no:String, name:String, jumin:String): ArrayList<Paper_EXERCISE>
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_EXERCISE>()

        val data = LocalDBhelper(this).Select_Local_EXERCISE(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_EXERCISE(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("sg2_spSports1_1")),
                    data.getString(data.getColumnIndex("sg2_spSports1_2")),
                    data.getString(data.getColumnIndex("sg2_spSports1_3_1")),
                    data.getString(data.getColumnIndex("sg2_spSports1_3_2")),
                    data.getString(data.getColumnIndex("sg2_spSports1_4")),
                    data.getString(data.getColumnIndex("sg2_spSports1_5")),
                    data.getString(data.getColumnIndex("sg2_spSports1_6_1")),
                    data.getString(data.getColumnIndex("sg2_spSports1_6_2")),
                    data.getString(data.getColumnIndex("sg2_spSports2_1")),
                    data.getString(data.getColumnIndex("sg2_spSports2_2")),
                    data.getString(data.getColumnIndex("sg2_spSports2_3_1")),
                    data.getString(data.getColumnIndex("sg2_spSports2_3_2")),
                    data.getString(data.getColumnIndex("sg2_spSports3_1")),
                    data.getString(data.getColumnIndex("sg2_spSports3_2")),
                    data.getString(data.getColumnIndex("sg2_spSports3_3_1")),
                    data.getString(data.getColumnIndex("sg2_spSports3_3_2")),
                    data.getString(data.getColumnIndex("sg2_spSports3_4")),
                    data.getString(data.getColumnIndex("sg2_spSports3_5")),
                    data.getString(data.getColumnIndex("sg2_spSports3_6_1")),
                    data.getString(data.getColumnIndex("sg2_spSports3_6_2")),
                    data.getString(data.getColumnIndex("sg2_spSports4_1_1")),
                    data.getString(data.getColumnIndex("sg2_spSports4_1_2")),
                    data.getString(data.getColumnIndex("sg2_spSports5")),
                    data.getString(data.getColumnIndex("sg2_spSports6")),
                    data.getString(data.getColumnIndex("sg2_spSports7")),
                    data.getString(data.getColumnIndex("sg2_spSports8")),
                    data.getString(data.getColumnIndex("sg2_spSports9")),
                    data.getString(data.getColumnIndex("sg2_spSports10")),
                    data.getString(data.getColumnIndex("sg2_spSports11")),
                    data.getString(data.getColumnIndex("sg2_spSports12")),
                    data.getString(data.getColumnIndex("sg2_spSportsSum"))
            ))
            data.moveToNext()
        }

        sql_db.close()
        return PaperArray
    }

    fun Return_NUTRITION(exam_no:String, name:String, jumin:String): ArrayList<Paper_NUTRITION> {
        val sql_db = LocalDBhelper(this).writableDatabase
        var PaperArray = ArrayList<Paper_NUTRITION>()

        val data = LocalDBhelper(this).Select_Local_NUTRITION(sql_db!!, exam_no, name, jumin)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_NUTRITION(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("sg2_spFood1")),
                    data.getString(data.getColumnIndex("sg2_spFood2")),
                    data.getString(data.getColumnIndex("sg2_spFood3")),
                    data.getString(data.getColumnIndex("sg2_spFood4")),
                    data.getString(data.getColumnIndex("sg2_spFood5")),
                    data.getString(data.getColumnIndex("sg2_spFood6")),
                    data.getString(data.getColumnIndex("sg2_spFood7")),
                    data.getString(data.getColumnIndex("sg2_spFood8")),
                    data.getString(data.getColumnIndex("sg2_spFood9")),
                    data.getString(data.getColumnIndex("sg2_spFood10")),
                    data.getString(data.getColumnIndex("sg2_spFood11")),
                    data.getString(data.getColumnIndex("sg2_spFoodSum")),
                    data.getString(data.getColumnIndex("sg2_spFatHeight")),
                    data.getString(data.getColumnIndex("sg2_spFatWeight")),
                    data.getString(data.getColumnIndex("sg2_spFatWaistSize")),
                    data.getString(data.getColumnIndex("sg2_spFatBmi")),
                    data.getString(data.getColumnIndex("sg2_spFat1")),
                    data.getString(data.getColumnIndex("sg2_spFat2")),
                    data.getString(data.getColumnIndex("sg2_spFat3"))
            ))
            data.moveToNext()
        }

        sql_db.close()
        return PaperArray
    }

    override fun onResume() {
        super.onResume()

        ProgressAction(false)
    }

    fun ProgressAction(isShow:Boolean)
    {
        if(isShow)
        {
            Progress_circle.visibility = View.VISIBLE
            Progress_bg.visibility = View.VISIBLE
            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, 	WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        else
        {
            Progress_circle.visibility = View.GONE
            Progress_bg.visibility = View.GONE
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
}
















