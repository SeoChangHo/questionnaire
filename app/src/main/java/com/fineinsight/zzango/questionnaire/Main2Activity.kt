package com.fineinsight.zzango.questionnaire

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnSetting()
    }

    fun btnSetting(){

        btn_agreement.setOnClickListener {
            startActivity(Intent(this, AgreementActivity::class.java))
        }

        btn_exam.setOnClickListener {
            startActivity(Intent(this, LoginExamActivity::class.java))
        }
    }
}
