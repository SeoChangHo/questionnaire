package com.example.zzango.questionnaire

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.zzango.questionnaire.LocalList.ListActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var num = getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")


        if(num != "wifi"){

//            local_button.setBackgroundColor(Color.parseColor("#3C6FD1"))
//            local_imageView.setImageResource(R.drawable.local)
//            local_textView.setTextColor(Color.parseColor("#FFFFFF"))
//
//            server_button.setBackgroundResource(R.drawable.border_top)
//            server_imageView.setImageResource(R.drawable.server_blue)
//            server_textView.setTextColor(Color.parseColor("#2B53A2"))

        }else{

//            local_button.setBackgroundResource(R.drawable.border_top)
//            local_imageView.setImageResource(R.drawable.local_blue)
//            local_textView.setTextColor(Color.parseColor("#2B53A2"))
//
//            server_button.setBackgroundColor(Color.parseColor("#3C6FD1"))
//            server_imageView.setImageResource(R.drawable.server)
//            server_textView.setTextColor(Color.parseColor("#FFFFFF"))

        }

        listViewButton.setOnClickListener {

            startActivity(Intent(this@SettingActivity, ListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

//        local_button.setOnClickListener {
//
//            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()
//
//            local_button.setBackgroundColor(Color.parseColor("#3C6FD1"))
//            local_imageView.setImageResource(R.drawable.local)
//            local_textView.setTextColor(Color.parseColor("#FFFFFF"))
//
//            server_button.setBackgroundResource(R.drawable.border_top)
//            server_imageView.setImageResource(R.drawable.server_blue)
//            server_textView.setTextColor(Color.parseColor("#2B53A2"))
//
//
//        }
//
//        server_button.setOnClickListener {
//
//            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()
//
//            local_button.setBackgroundResource(R.drawable.border_top)
//            local_imageView.setImageResource(R.drawable.local_blue)
//            local_textView.setTextColor(Color.parseColor("#2B53A2"))
//
//            server_button.setBackgroundColor(Color.parseColor("#3C6FD1"))
//            server_imageView.setImageResource(R.drawable.server)
//            server_textView.setTextColor(Color.parseColor("#FFFFFF"))
//
//        }


    }

}