package com.fineinsight.zzango.questionnaire

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Button
import com.fineinsight.zzango.questionnaire.LocalList.HospitalList
import com.fineinsight.zzango.questionnaire.LocalList.ListActivity
import com.fineinsight.zzango.questionnaire.LocalList.ListAgreeActivity
import com.fineinsight.zzango.questionnaire.ServerList.ServerListActivity
import com.fineinsight.zzango.questionnaire.ServerList.ServerListAgreeActivity
import kotlinx.android.synthetic.main.activity_setting_back.*
import kotlinx.android.synthetic.main.activity_setting_back.listViewButton
import kotlinx.android.synthetic.main.activity_setting_back.manager_logout
import kotlinx.android.synthetic.main.activity_setting_back.setting_image

class SettingActivity : AppCompatActivity() {

    var popup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_back)

        manager_logout.text = MainActivity.manager_name+"님"
        var num = getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")

        if(num != "wifi"){

            local_button.setBackgroundResource(R.drawable.setting_save_select_left)
            local_imageView.setImageResource(R.drawable.local)
            local_textView.setTextColor(Color.parseColor("#FFFFFF"))

            server_button.setBackgroundResource(R.drawable.setting_save_unselect_right)
            server_imageView.setImageResource(R.drawable.server_blue)
            server_textView.setTextColor(Color.parseColor("#2B53A2"))

        }else{

            local_button.setBackgroundResource(R.drawable.setting_save_unselect_left)
            local_imageView.setImageResource(R.drawable.local_blue)
            local_textView.setTextColor(Color.parseColor("#2B53A2"))

            server_button.setBackgroundResource(R.drawable.setting_save_select_right)
            server_imageView.setImageResource(R.drawable.server)
            server_textView.setTextColor(Color.parseColor("#FFFFFF"))

        }


        button_back.setOnClickListener {
            finish()
        }


//        switch1.isChecked = num == "wifi"

        //문진 - 로컬리스트
        listViewButton.setOnClickListener {
            startActivity(Intent(this@SettingActivity, ListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        //문진 - 서버리스트
        listViewButton2.setOnClickListener {
            startActivity(Intent(this@SettingActivity, ServerListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        //개인정보동의서 - 로컬리스트
        btnAgreeLocal.setOnClickListener {
            startActivity(Intent(this@SettingActivity, ListAgreeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        //개인정보동의서 - 서버리스트
        btnAgreeServer.setOnClickListener {
            startActivity(Intent(this@SettingActivity, ServerListAgreeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

//        switch1.setOnClickListener {
//
//            if(switch1.isChecked){
//                getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()
//            }else{
//                getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()
//            }
//
//        }

        local_button.setOnClickListener {

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()

            local_button.setBackgroundResource(R.drawable.setting_save_select_left)
            local_imageView.setImageResource(R.drawable.local)
            local_textView.setTextColor(Color.parseColor("#FFFFFF"))

            server_button.setBackgroundResource(R.drawable.setting_save_unselect_right)
            server_imageView.setImageResource(R.drawable.server_blue)
            server_textView.setTextColor(Color.parseColor("#2B53A2"))


        }

        server_button.setOnClickListener {

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()

            local_button.setBackgroundResource(R.drawable.setting_save_unselect_left)
            local_imageView.setImageResource(R.drawable.local_blue)
            local_textView.setTextColor(Color.parseColor("#2B53A2"))

            server_button.setBackgroundResource(R.drawable.setting_save_select_right)
            server_imageView.setImageResource(R.drawable.server)
            server_textView.setTextColor(Color.parseColor("#FFFFFF"))

        }


        manager_logout.setOnClickListener {

            var dialog = AlertDialog.Builder(this).create()
            var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_user_logout, null)

            //다이얼로그 뒤로가기 버튼 막기
            dialog.setCancelable(false)

            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setView(dialog_view)
            dialog.setCanceledOnTouchOutside(false)

            val logout = dialog_view.findViewById(R.id.user_logout) as Button
            val cancel = dialog_view.findViewById(R.id.user_logout_cancel) as Button

            dialog.show()

            logout.setOnClickListener {

                MainActivity.login_user_name = ""
                MainActivity.user_first_serial = ""
                MainActivity.user_last_serial = ""
                MainActivity.manager_name = ""
                MainActivity.chart.clear()
                MainActivity.manager_name = ""
                MainActivity.exam_no = ""
                MainActivity.alert_view = null
                MainActivity.ValidationBool = false
                MainActivity.canvas_motion = null
                MainActivity.hospital = ""

                startActivity(Intent(this@SettingActivity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                dialog.dismiss()

            }

            cancel.setOnClickListener {
                dialog.dismiss()
            }

        }



        if(MainActivity.hospital == HospitalList.hospital.Mokpo){
            setting_image.setImageResource(R.drawable.logo2)
        }else if(MainActivity.hospital == HospitalList.hospital.test){
            setting_image.setImageResource(R.drawable.logo)
        }else if(MainActivity.hospital == HospitalList.hospital.Osong){
            setting_image.setImageResource(R.drawable.bestianlogo)
        }

    }

}