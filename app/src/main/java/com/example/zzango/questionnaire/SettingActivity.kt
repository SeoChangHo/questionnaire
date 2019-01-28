package com.example.zzango.questionnaire

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Button
import com.example.zzango.questionnaire.LocalList.ListActivity
import kotlinx.android.synthetic.main.activity_setting_back.*

class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_back)

        manager_logout.setText(MainActivity.manager_name)
        var num = getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")


        if(num != "wifi"){

            local_button.setBackgroundColor(Color.parseColor("#3C6FD1"))
            local_imageView.setImageResource(R.drawable.local)
            local_textView.setTextColor(Color.parseColor("#FFFFFF"))

            server_button.setBackgroundResource(R.drawable.border_top)
            server_imageView.setImageResource(R.drawable.server_blue)
            server_textView.setTextColor(Color.parseColor("#2B53A2"))

        }else{

            local_button.setBackgroundResource(R.drawable.border_top)
            local_imageView.setImageResource(R.drawable.local_blue)
            local_textView.setTextColor(Color.parseColor("#2B53A2"))

            server_button.setBackgroundColor(Color.parseColor("#3C6FD1"))
            server_imageView.setImageResource(R.drawable.server)
            server_textView.setTextColor(Color.parseColor("#FFFFFF"))

        }

//        switch1.isChecked = num == "wifi"

        listViewButton.setOnClickListener {

            startActivity(Intent(this@SettingActivity, ListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

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

            local_button.setBackgroundColor(Color.parseColor("#3C6FD1"))
            local_imageView.setImageResource(R.drawable.local)
            local_textView.setTextColor(Color.parseColor("#FFFFFF"))

            server_button.setBackgroundResource(R.drawable.border_top)
            server_imageView.setImageResource(R.drawable.server_blue)
            server_textView.setTextColor(Color.parseColor("#2B53A2"))


        }

        server_button.setOnClickListener {

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()

            local_button.setBackgroundResource(R.drawable.border_top)
            local_imageView.setImageResource(R.drawable.local_blue)
            local_textView.setTextColor(Color.parseColor("#2B53A2"))

            server_button.setBackgroundColor(Color.parseColor("#3C6FD1"))
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

                startActivity(Intent(this@SettingActivity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                dialog.dismiss()

            }

            cancel.setOnClickListener {
                dialog.dismiss()
            }

        }



    }

}