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


        listViewButton.setOnClickListener {

            startActivity(Intent(this@SettingActivity, ListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        local_server_switch.setOnClickListener {

            if(local_server_switch.isChecked){
                getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()
            }else{
                getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()
            }

        }


    }

}