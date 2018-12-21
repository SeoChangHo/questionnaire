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
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : Activity() {

    var sql_db : SQLiteDatabase? = null
    var papers = ArrayList<Paper>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        ListSetting()
        btnSeeting()




    }

    fun ListSetting()
    {

        papers = ArrayList<Paper>()
        sql_db = LocalDBhelper(this).writableDatabase
        val recyclerView = findViewById(R.id.recyclertView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        val data = LocalDBhelper(this).checkLocal(sql_db!!)

        data.moveToFirst()

        while(!data.isAfterLast){
            papers.add(Paper(false,
                    data.getString(data.getColumnIndex("category")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("first_serial")),
                    data.getString(data.getColumnIndex("last_serial")),
                    "2018-12-21", "", "",
                    "", "", "", "",
                    "", "", "", "",
                    "", "", "", "",
                    "", "", "", "",
                    "", "", "", ""))

            data.moveToNext()

        }

        val adapter = CustomAdapter(papers, this)

        recyclerView.adapter = adapter
    }

    fun btnSeeting()
    {

        var btnSave = findViewById(R.id.btnSave) as Button
        var btnDelete = findViewById(R.id.btnDelete) as Button
        var txtBottomMent = findViewById(R.id.txtBottomMent) as TextView

        //저장하는거
        btnSave.setOnClickListener {

            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            login_appbar_loading_progress_bg.visibility = View.VISIBLE
            login_appbar_loading_progress.visibility = View.VISIBLE

            var removeArr = ArrayList<Paper>()

            for(item in papers)
            {
                if(item.isChecked==true)
                {
                    removeArr.add(item)
                }
            }

            OracleUtill().oral_examination().oracleServer1(removeArr!!).enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.isSuccessful) {

                        if (!response.body()!!.equals("S")) {

                            println(response.body())
//                            Toast.makeText(this@OralExamination, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                        } else {

                            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                            login_appbar_loading_progress_bg.visibility = View.GONE
                            login_appbar_loading_progress.visibility = View.GONE

                            Toast.makeText(this@ListActivity, "전송 완료", Toast.LENGTH_LONG).show()

                            LocalDBhelper(this@ListActivity).deletePaper(sql_db!!, removeArr)
                            ListSetting()
                            btnSave.visibility = View.GONE
                            btnDelete.visibility = View.GONE
                            txtBottomMent.text = "문진표를 선택해주세요."
                        }

                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                    Toast.makeText(this@ListActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                    println(t.toString())
                }

            })

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
            ListSetting()
            btnSave.visibility = View.GONE
            btnDelete.visibility = View.GONE
            txtBottomMent.text = "문진표를 선택해주세요."
        }

    }

    override fun onBackPressed() {

        if(login_appbar_loading_progress.visibility != View.VISIBLE){

            super.onBackPressed()

        }

    }

}



























