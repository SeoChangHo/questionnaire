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
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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

        popuplogin()

        var wfm = getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager

        getSharedPreferences("connection_state", Context.MODE_PRIVATE)

        if(wfm.isWifiEnabled){

            getSharedPreferences("connection_state", Context.MODE_PRIVATE).edit().putString("wifi", "").apply()

            data_save_mode.setImageResource(R.drawable.ic_rss_feed)

        }else{

            getSharedPreferences("connection_state", Context.MODE_PRIVATE).edit().putString("local", "").apply()

            data_save_mode.setImageResource(R.drawable.ic_sd_storage)

        }

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_right, FirstFragment()).commit()

        button1.setBackgroundColor(Color.parseColor("#233F78"))

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


        val login = dialog_view.findViewById(R.id.Login) as Button
        login.setOnClickListener{

            if(dialog_view.login_id.text.toString() != ""){

                if(dialog_view.login_password.text.toString() == "1111"){
                    Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }else{
                    Toast.makeText(applicationContext, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(applicationContext, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }



        }

        dialog.show()

    }

    fun saveAlert(){

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_location, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.save_location_text.setText("저장할 곳을 골라주세요")

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

        dialog_view.local.setOnClickListener {

            data_save_mode.setImageResource(R.drawable.ic_sd_storage)

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "local").apply()

            dialog.dismiss()

        }

        dialog_view.server.setOnClickListener {

            data_save_mode.setImageResource(R.drawable.ic_rss_feed)

            getSharedPreferences("connection", Context.MODE_PRIVATE).edit().putString("state", "wifi").apply()

            dialog.dismiss()

        }

    }

    override fun onClick(v: View?) {

        button1.setBackgroundColor(Color.parseColor("#3760B2"))
        button2.setBackgroundColor(Color.parseColor("#3760B2"))
        button3.setBackgroundColor(Color.parseColor("#3760B2"))
        button4.setBackgroundColor(Color.parseColor("#3760B2"))
        button5.setBackgroundColor(Color.parseColor("#3760B2"))

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

        v.setBackgroundColor(Color.parseColor("#233F78"))

        if (fragment != null) {

            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_right, fragment!!).commit()
        }
    }
}
