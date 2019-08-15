package com.fineinsight.zzango.questionnaire.LocalList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import com.fineinsight.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        if(intent.hasExtra("paper")){

            println("!!!!!!!!")

            var paper = intent.getSerializableExtra("paper") as Paper

            txtListTitle.text = "${paper.name} 님의 문진표"

            val adapter = CustomDetailAdapter(paper, this)

            println("@@@")
            println(paper.signature.size)
            detail_recyclertView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

            detail_recyclertView.adapter = adapter
        }
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

