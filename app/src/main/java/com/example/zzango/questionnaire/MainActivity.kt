package com.example.zzango.questionnaire


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.example.zzango.questionnaire.Signature.CanvasView
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_login.*
import kotlinx.android.synthetic.main.activity_user_login.view.*
import kotlinx.android.synthetic.main.quit_alert.view.*
import kotlinx.android.synthetic.main.save_location.view.*

class MainActivity : AppCompatActivity() , View.OnClickListener {

    var popup = false
    lateinit var canvasView: CanvasView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var wfm = getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager

        if(!this.intent.hasExtra("from")) {

            popuplogin()

        }

//        if (wfm.isWifiEnabled) {
//
//            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()
//
//            data_save_mode_image.setImageResource(R.drawable.server_white)
//
//            data_save_mode_text.setText("server")
//
//        } else {
//
//            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()
//
//            data_save_mode_image.setImageResource(R.drawable.local_white)
//
//            data_save_mode_text.setText("local")
//
//        }

        if(getSharedPreferences("connection", 0).getString("state", "").isNullOrEmpty()) {

            getSharedPreferences("connection", 0).edit().putString("state", "local").apply()

        }

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_right, FirstFragment()).commit()

        button1.setBackgroundColor(Color.parseColor("#2B53A2"))

        listButton.setOnClickListener{

            startActivity(Intent(this@MainActivity, SettingActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }


        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)

        user_login.setOnClickListener{
            userlogin()
        }

        if(MainActivity.login_user_name != ""){
            user_login.setText(MainActivity.login_user_name+"님")
            user_image.setImageResource(R.drawable.exit)
        }

    }

    fun popuplogin(){

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_login, null)

        //다이얼로그 뒤로가기 버튼 막기
        dialog.setCancelable(false)

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

    fun userlogin(){

        if(user_login.text == "사용자 등록하기"){



            var dialog = AlertDialog.Builder(this).create()
            var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_user_login, null)

            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setView(dialog_view)
            dialog.setCanceledOnTouchOutside(false)

            canvasView = dialog_view.canvas


            dialog_view.user_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(dialog_view.user_name.text.toString() != "" && dialog_view.first_serial.text.toString() != "" && dialog_view.last_serial.text.toString() != ""){
                        dialog_view.user_login_button.isClickable = true
                        dialog_view.user_login_button.setBackgroundColor(Color.parseColor("#2B53A2"))
                    }else{
                        dialog_view.user_login_button.isClickable = false
                        dialog_view.user_login_button.setBackgroundColor(Color.parseColor("#b1b1b1"))
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            dialog_view.first_serial.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(dialog_view.user_name.text.toString() != "" && dialog_view.first_serial.text.toString() != "" && dialog_view.last_serial.text.toString() != ""){
                        dialog_view.user_login_button.isClickable = true
                        dialog_view.user_login_button.setBackgroundColor(Color.parseColor("#2B53A2"))
                    }else{
                        dialog_view.user_login_button.isClickable = false
                        dialog_view.user_login_button.setBackgroundColor(Color.parseColor("#b1b1b1"))
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            dialog_view.last_serial.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(dialog_view.user_name.text.toString() != "" && dialog_view.first_serial.text.toString() != "" && dialog_view.last_serial.text.toString() != ""){
                        dialog_view.user_login_button.isClickable = true
                        dialog_view.user_login_button.setBackgroundColor(Color.parseColor("#2B53A2"))
                    }else{
                        dialog_view.user_login_button.isClickable = false
                        dialog_view.user_login_button.setBackgroundColor(Color.parseColor("#b1b1b1"))
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            val login = dialog_view.findViewById(R.id.user_login_button) as Button
            login.setOnClickListener{

                MainActivity.login_user_name = dialog_view.user_name.text.toString()
                MainActivity.user_first_serial = dialog_view.first_serial.text.toString()
                MainActivity.user_last_serial = dialog_view.last_serial.text.toString()

                Toast.makeText(applicationContext, "사용자가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                user_login.setText(MainActivity.login_user_name+"님")
                user_image.setImageResource(R.drawable.exit)
                dialog.dismiss()

            }



            dialog.show()
        }

        if(user_login.text == MainActivity.login_user_name+"님"){

            MainActivity.login_user_name = ""
            MainActivity.user_first_serial = ""
            MainActivity.user_last_serial = ""

            Toast.makeText(applicationContext, "사용자가 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            user_login.setText("사용자 등록하기")
            user_image.setImageResource(R.drawable.regi)

        }

    }

    fun dataSaveLocationAlert(){

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

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()

            dialog.dismiss()

        }

        dialog_view.server.setOnClickListener {

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

    override fun onBackPressed() {

        var dialog = android.app.AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.quit_alert, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.notice.setText("앱을 종료하시겠습니까?")

        if(!popup) {

            dialog.show().let {

                popup = true

            }

        }

        var displayMetrics = DisplayMetrics()
        dialog.window.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // The absolute width of the available display size in pixels.
        var displayWidth = displayMetrics.widthPixels
        // The absolute height of the available display size in pixels.
        var displayHeight = displayMetrics.heightPixels

        // Initialize a new window manager layout parameters
        var layoutParams = WindowManager.LayoutParams()

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.window.attributes)

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set alert dialog width equal to screen width 70%
        var dialogWindowWidth = (displayWidth * 0.7f).toInt()
        // Set alert dialog height equal to screen height 70%
        var dialogWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth
        layoutParams.height = dialogWindowHeight

        // Apply the newly created layout parameters to the alert dialog window
        dialog.window.attributes = layoutParams


        dialog.setOnDismissListener {

            popup = false
            dialog = null

        }

        dialog_view.cancel.setOnClickListener {

            dialog.dismiss()

        }

        dialog_view.finish.setOnClickListener {

            ActivityCompat.finishAffinity(this)

            System.runFinalization()
            System.exit(0)
            dialog.dismiss()

        }

    }

    companion object {
        var login_user_name = ""
        var user_first_serial = ""
        var user_last_serial = ""
    }

}
