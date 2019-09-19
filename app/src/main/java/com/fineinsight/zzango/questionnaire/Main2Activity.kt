package com.fineinsight.zzango.questionnaire

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.fineinsight.zzango.questionnaire.DataClass.SavePaper
import com.fineinsight.zzango.questionnaire.DataClass.SavedListObject
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.quit_alert.*
import kotlinx.android.synthetic.main.quit_alert.view.*

class Main2Activity : AppCompatActivity() {

    var popup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnSetting()
    }

    fun btnSetting(){

        btn_agreement.setOnClickListener {
            startActivity(Intent(this, LoginAgreeActivity::class.java))
        }

        btn_exam.setOnClickListener {
            startActivity(Intent(this, LoginExamActivity::class.java))
        }
    }

    override fun onBackPressed() {

        var customDialog = Dialog(this)
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customDialog.setContentView(R.layout.quit_alert)
        customDialog.window!!.decorView.setBackgroundResource(R.drawable.alert_shape)
        customDialog.create()

        customDialog.notice.text = "앱을 종료하시겠습니까?"
        customDialog.finish.text = "네"
        customDialog.cancel.text = "아니요"

        if(!popup) {

            customDialog.show().let { popup = true }

        }

        customDialog.setOnDismissListener {

            popup = false
            customDialog = Dialog(this)

        }

        customDialog.finish.setOnClickListener {

            ActivityCompat.finishAffinity(this)

            System.runFinalization()
            System.exit(0)
            customDialog.dismiss()

        }

        customDialog.cancel.setOnClickListener {

            customDialog.dismiss()

        }

    }



}
