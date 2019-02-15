package com.example.zzango.questionnaire.LocalList

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.zzango.questionnaire.LocalDBhelper
import com.example.zzango.questionnaire.OracleUtill
import com.example.zzango.questionnaire.R
import com.example.zzango.questionnaire.Signature.BitmapFun
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : Activity() {

    var sql_db : SQLiteDatabase? = null
    var papers = ArrayList<Paper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        ListSetting(false)
        btnSeeting()
        SelectAllSetting()
    }

    fun ListSetting(bool:Boolean)
    {
        papers = ArrayList<Paper>()


        sql_db = LocalDBhelper(this).writableDatabase

        val recyclerView = findViewById<RecyclerView>(R.id.recyclertView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        val data = LocalDBhelper(this).checkLocalList(sql_db!!)
        data.moveToFirst()

        while(!data.isAfterLast){
                papers.add(Paper(bool,
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("setno")),
                        data.getBlob(data.getColumnIndex("signature")),
                        data.getString(data.getColumnIndex("name"))))

            data.moveToNext()

        }
        val adapter = CustomAdapter(papers, this)

        recyclerView.adapter = adapter
    }

    fun btnSeeting()
    {
        sql_db = LocalDBhelper(this).writableDatabase

        var btnSave = findViewById<Button>(R.id.btnSave)
        var btnDelete = findViewById<Button>(R.id.btnDelete)
        var txtBottomMent = findViewById<TextView>(R.id.txtBottomMent)

        //저장하는거
        btnSave.setOnClickListener {




            var removeArr = ArrayList<Paper>()
            var SaveArr = ArrayList<Any>()
            var SetArr = ArrayList<String>()


            for(item in papers)
            {
                if(item.isChecked==true)
                {
                    removeArr.add(item)
                }
            }

            for(i in 0..removeArr.size-1)
            {
                var CategoryArr = ArrayList<Any>()

                println("SETNO: "+removeArr[i].setno)

                when(removeArr[i].setno)
                {
                    PaperArray.SetList.SET1 ->
                    {
                        var COMMONArr = Return_COMMON(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(COMMONArr)
                    }
                    PaperArray.SetList.SET2 ->
                    {
                        var COMMONArr = Return_COMMON(removeArr[i].exam_no, removeArr[i].signature)
                        var MENTALArr = Return_MENTAL(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(COMMONArr)
                        CategoryArr.add(MENTALArr)
                    }
                    PaperArray.SetList.SET3 ->
                    {
                        var COMMONArr = Return_COMMON(removeArr[i].exam_no, removeArr[i].signature)
                        var MENTALArr = Return_MENTAL(removeArr[i].exam_no, removeArr[i].signature)

                        var DRINKINGArr = Return_DRINKING(removeArr[i].exam_no, removeArr[i].signature)
                        var EXERCISEArr = Return_EXERCISE(removeArr[i].exam_no, removeArr[i].signature)
                        var NUTRITIONArr = Return_NUTRITION(removeArr[i].exam_no, removeArr[i].signature)
                        var SMOKING = Return_SMOKING(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(COMMONArr)
                        CategoryArr.add(MENTALArr)
                        CategoryArr.add(DRINKINGArr)
                        CategoryArr.add(EXERCISEArr)
                        CategoryArr.add(NUTRITIONArr)
                        CategoryArr.add(SMOKING)
                    }
                    PaperArray.SetList.SET4 ->
                    {
                        var COMMONArr = Return_COMMON(removeArr[i].exam_no, removeArr[i].signature)
                        var COGNITIVEArr = Return_COGNITIVE(removeArr[i].exam_no, removeArr[i].signature)
                        var ELDERLYArr = Return_ELDERLY(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(COMMONArr)
                        CategoryArr.add(COGNITIVEArr)
                        CategoryArr.add(ELDERLYArr)
                    }
                    PaperArray.SetList.SET5 ->
                    {
                        var COMMONArr = Return_COMMON(removeArr[i].exam_no, removeArr[i].signature)
                        var COGNITIVEArr = Return_COGNITIVE(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(COMMONArr)
                        CategoryArr.add(COGNITIVEArr)
                    }
                    PaperArray.SetList.SET6 ->
                    {
                        var COMMONArr = Return_COMMON(removeArr[i].exam_no, removeArr[i].signature)
                        var COGNITIVEArr = Return_COGNITIVE(removeArr[i].exam_no, removeArr[i].signature)
                        var MENTALArr = Return_MENTAL(removeArr[i].exam_no, removeArr[i].signature)

                        var DRINKINGArr = Return_DRINKING(removeArr[i].exam_no, removeArr[i].signature)
                        var EXERCISEArr = Return_EXERCISE(removeArr[i].exam_no, removeArr[i].signature)
                        var NUTRITIONArr = Return_NUTRITION(removeArr[i].exam_no, removeArr[i].signature)
                        var SMOKING = Return_SMOKING(removeArr[i].exam_no, removeArr[i].signature)

                        var ELDERLYArr = Return_ELDERLY(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(COMMONArr)
                        CategoryArr.add(COGNITIVEArr)
                        CategoryArr.add(MENTALArr)
                        CategoryArr.add(DRINKINGArr)
                        CategoryArr.add(EXERCISEArr)
                        CategoryArr.add(NUTRITIONArr)
                        CategoryArr.add(SMOKING)
                        CategoryArr.add(ELDERLYArr)
                    }
                    PaperArray.SetList.SET7 ->
                    {
                        var ORALArr = Return_ORAL(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(ORALArr)
                    }
                    PaperArray.SetList.SET8 ->
                    {
                        var CANCERArr = Return_CANCER(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(CANCERArr)
                    }
                    PaperArray.SetList.SET9 ->
                    {
                        var COGNITIVEArr = Return_COGNITIVE(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(COGNITIVEArr)
                    }
                    PaperArray.SetList.SET10 ->
                    {
                        var MENTALArr = Return_MENTAL(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(MENTALArr)
                    }
                    PaperArray.SetList.SET11 ->
                    {
                        var DRINKINGArr = Return_DRINKING(removeArr[i].exam_no, removeArr[i].signature)
                        var EXERCISEArr = Return_EXERCISE(removeArr[i].exam_no, removeArr[i].signature)
                        var NUTRITIONArr = Return_NUTRITION(removeArr[i].exam_no, removeArr[i].signature)
                        var SMOKING = Return_SMOKING(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(DRINKINGArr)
                        CategoryArr.add(EXERCISEArr)
                        CategoryArr.add(NUTRITIONArr)
                        CategoryArr.add(SMOKING)
                    }
                    PaperArray.SetList.SET12 ->
                    {
                        var ELDERLYArr = Return_ELDERLY(removeArr[i].exam_no, removeArr[i].signature)

                        CategoryArr.add(ELDERLYArr)
                    }
                    else ->
                    {
                        println("확인불가")
                    }
                }
                SaveArr.add(CategoryArr)
                SetArr.add(removeArr[i].setno)

            }

            println("**********SAVE ARRAY**********")
            println(SaveArr)
            println("**********SAVE ARRAY**********")


            UploadPaper(SaveArr, SetArr, removeArr,0, SaveArr.size)



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
    fun UploadPaper(SaveArr:ArrayList<Any>, SetArr:ArrayList<String>, removeArr:ArrayList<Paper>, startIndex:Int, TotalIndex:Int)
    {
        sql_db = LocalDBhelper(this).writableDatabase
        println("업로드 들어옴")
        println("Array의 크기는 "+TotalIndex.toString()+" 개 입니다.")
        println("현재는 "+startIndex.toString()+" 번째 입니다.")

        println("세트번호는 "+SetArr[startIndex]+" 입니다.")

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        login_appbar_loading_progress_bg.visibility = View.VISIBLE
        login_appbar_loading_progress.visibility = View.VISIBLE




        var body = ArrayList<Any>()

        body.add(SetArr[startIndex])
        body.add(SaveArr[startIndex])


        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        login_appbar_loading_progress_bg.visibility = View.GONE
        login_appbar_loading_progress.visibility = View.GONE


        OracleUtill().save_papers().savePapersServer(body).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        println(startIndex.toString()+"번째 요청 실패")

                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        login_appbar_loading_progress_bg.visibility = View.GONE
                        login_appbar_loading_progress.visibility = View.GONE
                        Toast.makeText(this@ListActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        LocalDBhelper(this@ListActivity).deletePaperEach(sql_db!!, removeArr[startIndex])


                        if(startIndex+1<TotalIndex)
                        {
                            //할게 더 남아서 재귀호출


                            UploadPaper(SaveArr, SetArr, removeArr, startIndex+1, TotalIndex)
                        }
                        else
                        {
                            //끝
                            println("모든 업로드가 완료되었습니다.")

                            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            login_appbar_loading_progress_bg.visibility = View.GONE
                            login_appbar_loading_progress.visibility = View.GONE
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

    override fun onBackPressed() {
        if(login_appbar_loading_progress.visibility != View.VISIBLE){
            super.onBackPressed()
        }
    }


    fun Return_ORAL(exam_no:String, signature:ByteArray): ArrayList<Paper_ORAL>
    {
        var PaperArray = ArrayList<Paper_ORAL>()

        val data = LocalDBhelper(this).Select_Local_ORAL(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_ORAL(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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




        return PaperArray
    }

    fun Return_COMMON(exam_no:String, signature:ByteArray): ArrayList<Paper_COMMON>
    {
        var PaperArray = ArrayList<Paper_COMMON>()

        val data = LocalDBhelper(this).Select_Local_COMMON(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){


            PaperArray.add(Paper_COMMON(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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
        return PaperArray
    }

    fun Return_COGNITIVE(exam_no:String, signature:ByteArray): ArrayList<Paper_COGNITIVE>
    {
        var PaperArray = ArrayList<Paper_COGNITIVE>()

        val data = LocalDBhelper(this).Select_Local_COGNITIVE(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_COGNITIVE(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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

        return PaperArray
    }


    fun Return_MENTAL(exam_no:String, signature:ByteArray): ArrayList<Paper_MENTAL>
    {
        var PaperArray = ArrayList<Paper_MENTAL>()

        val data = LocalDBhelper(this).Select_Local_MENTAL(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_MENTAL(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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

        return PaperArray
    }


    fun Return_ELDERLY(exam_no:String, signature:ByteArray): ArrayList<Paper_ELDERLY>
    {
        var PaperArray = ArrayList<Paper_ELDERLY>()

        val data = LocalDBhelper(this).Select_Local_ELDERLY(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_ELDERLY(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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

        return PaperArray
    }

    fun Return_SMOKING(exam_no:String, signature:ByteArray): ArrayList<Paper_SMOKING>
    {
        var PaperArray = ArrayList<Paper_SMOKING>()

        val data = LocalDBhelper(this).Select_Local_SMOKING(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_SMOKING(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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

        return PaperArray
    }

    fun Return_DRINKING(exam_no:String, signature:ByteArray): ArrayList<Paper_DRINKING>
    {
        var PaperArray = ArrayList<Paper_DRINKING>()

        val data = LocalDBhelper(this).Select_Local_DRINKING(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_DRINKING(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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

        return PaperArray
    }

    fun Return_CANCER(exam_no:String, signature:ByteArray): ArrayList<Paper_CANCER>
    {
        var PaperArray = ArrayList<Paper_CANCER>()

        val data = LocalDBhelper(this).Select_Local_CANCER(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_CANCER(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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

        return PaperArray
    }

    fun Return_EXERCISE(exam_no:String, signature:ByteArray): ArrayList<Paper_EXERCISE>
    {
        var PaperArray = ArrayList<Paper_EXERCISE>()

        val data = LocalDBhelper(this).Select_Local_EXERCISE(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_EXERCISE(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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

        return PaperArray
    }

    fun Return_NUTRITION(exam_no:String, signature:ByteArray): ArrayList<Paper_NUTRITION> {
        var PaperArray = ArrayList<Paper_NUTRITION>()

        val data = LocalDBhelper(this).Select_Local_NUTRITION(sql_db!!, exam_no)
        data.moveToFirst()

        while(!data.isAfterLast){
            PaperArray.add(Paper_NUTRITION(
                    data.getString(data.getColumnIndex("exam_date")),
                    data.getString(data.getColumnIndex("exam_no")),
                    signature,
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

        return PaperArray
    }


}






/*

CustomAdapter.Category.ORAL ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_ORAL>()

                        val data = LocalDBhelper(applicationContext).Select_Local_ORAL(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_ORAL(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.MENTAL ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_MENTAL>()

                        val data = LocalDBhelper(applicationContext).Select_Local_MENTAL(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_MENTAL(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.COGNITIVE ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_COGNITIVE>()

                        val data = LocalDBhelper(applicationContext).Select_Local_COGNITIVE(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_COGNITIVE(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.ELDERLY ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_ELDERLY>()

                        val data = LocalDBhelper(applicationContext).Select_Local_ELDERLY(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_ELDERLY(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.COMMON ->
                    {

                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_COMMON>()

                        val data = LocalDBhelper(applicationContext).Select_Local_COMMON(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_COMMON(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.DRINKING ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_DRINKING>()

                        val data = LocalDBhelper(applicationContext).Select_Local_DRINKING(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_DRINKING(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.SMOKING ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_SMOKING>()

                        val data = LocalDBhelper(applicationContext).Select_Local_SMOKING(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_SMOKING(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.EXERCISE ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_EXERCISE>()

                        val data = LocalDBhelper(applicationContext).Select_Local_EXERCISE(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast) {
                            PaperArray.add(Paper_EXERCISE(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.NUTRITION ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_NUTRITION>()

                        val data = LocalDBhelper(applicationContext).Select_Local_NUTRITION(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_NUTRITION(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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

                        SaveArr.add(PaperArray)
                    }
                    CustomAdapter.Category.CANCER ->
                    {
                        println(removeArr[i].setno)

                        var PaperArray = ArrayList<Paper_CANCER>()

                        val data = LocalDBhelper(applicationContext).Select_Local_CANCER(sql_db!!, removeArr[i].exam_no)
                        data.moveToFirst()

                        while(!data.isAfterLast){
                            PaperArray.add(Paper_CANCER(
                                    data.getString(data.getColumnIndex("exam_date")),
                                    data.getString(data.getColumnIndex("exam_no")),
                                    EmptyBytes,
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
                        SaveArr.add(PaperArray)
                    }
                    else ->
                    {
                        println("확인불가")
                    }
 */



















