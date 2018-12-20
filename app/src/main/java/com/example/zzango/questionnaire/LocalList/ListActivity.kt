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

        data.moveToFirst()

        while(!data.isAfterLast){
            papers.add(Paper(false,
                             data.getString(data.getColumnIndex("category")),
                             data.getString(data.getColumnIndex("name")),
                             data.getString(data.getColumnIndex("serial_first")),
                             data.getString(data.getColumnIndex("serial_last"))))

            println("category:"+data.getString(data.getColumnIndex("category")))
            println("name:"+data.getString(data.getColumnIndex("name")))
            println("serial_first:"+data.getString(data.getColumnIndex("serial_first")))
            println("serial_last:"+data.getString(data.getColumnIndex("serial_last")))

            data.moveToNext()

        }

        val adapter = CustomAdapter(papers)

        recyclerView.adapter = adapter



    }

}