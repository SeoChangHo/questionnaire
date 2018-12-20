package com.example.zzango.questionnaire.LocalList

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.zzango.questionnaire.LocalDBhelper
import com.example.zzango.questionnaire.R

class ListActivity : Activity() {

    var sql_db : SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        sql_db = LocalDBhelper(this).writableDatabase

        val recyclerView = findViewById(R.id.recyclertView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        val papers = ArrayList<Paper>()





        val data = LocalDBhelper(this).checkLocal(sql_db!!)

        println("*****")
        println(data)
        println("*****")

        data.moveToFirst()

        while(!data.isAfterLast){
            papers.add(Paper(data.getString(data.getColumnIndex("remark"))))
            data.moveToNext()

        }

        val adapter = CustomAdapter(papers)

        recyclerView.adapter = adapter



    }

}