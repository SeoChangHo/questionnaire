package com.example.zzango.questionnaire.LocalList

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.zzango.questionnaire.R

class ListActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        val recyclerView = findViewById(R.id.recyclertView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //여기다가 Local DB 가져와서 갯수만큼 for문 돌려서 뿌려주기
        val papers = ArrayList<Paper>()

        papers.add(Paper("방가루"))
        papers.add(Paper("할룽"))

        val adapter = CustomAdapter(papers)

        recyclerView.adapter = adapter
    }

}