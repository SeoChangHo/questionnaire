package com.example.zzango.questionnaire.LocalList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)



        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper

            txtListTitle.setText(paper.name+" 님의 문진표")
        }



    }
}
