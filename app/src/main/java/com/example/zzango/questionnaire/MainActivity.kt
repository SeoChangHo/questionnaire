package com.example.zzango.questionnaire


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.ListActivity
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.save_location.view.*

class MainActivity : AppCompatActivity() , View.OnClickListener {

    var popup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!this.intent.hasExtra("from")) {

            popuplogin()

        }

        var wfm = getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager

        getSharedPreferences("connection_state", Context.MODE_PRIVATE)

        if(wfm.isWifiEnabled){

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()

            data_save_mode_image.setImageResource(R.drawable.server_white)

            data_save_mode_text.setText("server")

        }else{

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()

            data_save_mode_image.setImageResource(R.drawable.local_white)

            data_save_mode_text.setText("local")

        }

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_right, FirstFragment()).commit()

        button1.setBackgroundColor(Color.parseColor("#2B53A2"))

        listButton.setOnClickListener{

            startActivity(Intent(this@MainActivity, ListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        data_save_mode.setOnClickListener {

            saveAlert()

        }

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)

    }

    fun popuplogin(){

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_login, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog.setCanceledOnTouchOutside(false)


        dialog_view.login_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(dialog_view.login_id.text.toString() != "" && dialog_view.login_password.text.toString() != ""){
                    dialog_view.Login.isClickable = true
                    dialog_view.Login.setBackgroundColor(Color.parseColor("#2B53A2"))
                }else{
                    dialog_view.Login.isClickable = false
                    dialog_view.Login.setBackgroundColor(Color.parseColor("#b1b1b1"))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        dialog_view.login_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(dialog_view.login_id.text.toString() != "" && dialog_view.login_password.text.toString() != ""){
                    dialog_view.Login.isClickable = true
                    dialog_view.Login.setBackgroundColor(Color.parseColor("#2B53A2"))
                }else{
                    dialog_view.Login.isClickable = false
                    dialog_view.Login.setBackgroundColor(Color.parseColor("#b1b1b1"))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        val login = dialog_view.findViewById(R.id.Login) as Button
        login.setOnClickListener{

            if(dialog_view.login_id.text.toString() != ""){

                if(dialog_view.login_password.text.toString() == "1111"){
                    Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }else{
                    Toast.makeText(applicationContext, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }

            }



        }

        dialog.show()

    }

    fun saveAlert(){

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_location, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)

        if(!popup) {

            dialog.show().let {

                popup = true

            }

        }




        dialog.setOnDismissListener {

            popup = false
            dialog = null

        }

        dialog_view.local.setOnClickListener {

            data_save_mode_image.setImageResource(R.drawable.local_white)

            data_save_mode_text.setText("local")

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()

            dialog.dismiss()

        }

        dialog_view.server.setOnClickListener {

            data_save_mode_image.setImageResource(R.drawable.server_white)

            data_save_mode_text.setText("server")

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()

            dialog.dismiss()

        }

    }

    override fun onClick(v: View?) {

        button1.setBackgroundColor(Color.parseColor("#3C6FD1"))
        button2.setBackgroundColor(Color.parseColor("#3C6FD1"))
        button3.setBackgroundColor(Color.parseColor("#3C6FD1"))
        button4.setBackgroundColor(Color.parseColor("#3C6FD1"))
        button5.setBackgroundColor(Color.parseColor("#3C6FD1"))

        var fragment: Fragment? = null

        when (v!!.id) {
            R.id.button1 -> {
                fragment = FirstFragment()
            }

            R.id.button2 -> {
                fragment = SecondFragment()
            }

            R.id.button3 -> {
                fragment = ThirdFragment()
            }

            R.id.button4 -> {
                fragment = FourthFragment()
            }

            R.id.button5 -> {
                fragment = FiveFragment()
            }
        }

        v.setBackgroundColor(Color.parseColor("#2B53A2"))

        if (fragment != null) {

            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_right, fragment!!).commit()
        }
    }
}
