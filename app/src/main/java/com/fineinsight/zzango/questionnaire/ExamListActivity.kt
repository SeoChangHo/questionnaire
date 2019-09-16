package com.fineinsight.zzango.questionnaire

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.LocalList.HospitalList
import com.fineinsight.zzango.questionnaire.UserList.UserList
import kotlinx.android.synthetic.main.activity_exam_list.*
import kotlinx.android.synthetic.main.activity_exam_list.btnList
import kotlinx.android.synthetic.main.activity_exam_list.button1
import kotlinx.android.synthetic.main.activity_exam_list.button2
import kotlinx.android.synthetic.main.activity_exam_list.button3
import kotlinx.android.synthetic.main.activity_exam_list.button4
import kotlinx.android.synthetic.main.activity_exam_list.button5
import kotlinx.android.synthetic.main.activity_exam_list.main_image_logo
import kotlinx.android.synthetic.main.activity_exam_list.selected_button1
import kotlinx.android.synthetic.main.activity_exam_list.selected_button2
import kotlinx.android.synthetic.main.activity_exam_list.selected_button3
import kotlinx.android.synthetic.main.activity_exam_list.selected_button4
import kotlinx.android.synthetic.main.activity_exam_list.selected_button5
import kotlinx.android.synthetic.main.activity_exam_list.user_image
import kotlinx.android.synthetic.main.activity_exam_list.user_login
import kotlinx.android.synthetic.main.activity_login.view.*

class ExamListActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_list)

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        listButton.setOnClickListener(this)

        // 초기 화면 설정
        selected_button1.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().add(R.id.fragment_right, FirstFragment()).commit()
        logoSetting()
        user_login.text = MainActivity.login_user_name +"님"

        btnList.setOnClickListener {
            finish()
        }

        user_image.setOnClickListener {
            user_login.text = "사용자 등록하기"
            user_image.setImageResource(R.drawable.regi)

            MainActivity.login_user_name = ""
            MainActivity.user_first_serial = ""
            MainActivity.user_last_serial = ""
            Toast.makeText(this, "사용자가 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            MainActivity.chart.clear()

            //추가
            MainActivity.canvas_motion = null

            if(MainActivity.manager_name == "bestian"){
                startActivity(Intent(this, LoginExamActivity::class.java))
            } else {
                startActivity(Intent(this, Main2Activity::class.java))
            }
        }

    }

    fun logoSetting(){

        main_image_logo.visibility = View.VISIBLE

        if(MainActivity.hospital == HospitalList.hospital.Mokpo){
            main_image_logo.setImageResource(R.drawable.logo2)
        }else if(MainActivity.hospital == HospitalList.hospital.test){
            main_image_logo.setImageResource(R.drawable.logo)
        }else if(MainActivity.hospital == HospitalList.hospital.Osong){
            main_image_logo.setImageResource(R.drawable.bestianlogo)
        }
    }



    override fun onClick(v: View?) {

        selected_button1.visibility = View.GONE
        selected_button2.visibility = View.GONE
        selected_button3.visibility = View.GONE
        selected_button4.visibility = View.GONE
        selected_button5.visibility = View.GONE

        var fragment: Fragment? = null
        when (v!!.id) {
            R.id.button1 -> {
                fragment = FirstFragment()
                selected_button1.visibility = View.VISIBLE
            }
            R.id.button2 -> {
                fragment = SecondFragment()
                selected_button2.visibility = View.VISIBLE
            }
            R.id.button3 -> {
                fragment = ThirdFragment()
                selected_button3.visibility = View.VISIBLE
            }
            R.id.button4 -> {
                fragment = FourthFragment()
                selected_button4.visibility = View.VISIBLE
            }
            R.id.button5 -> {
                fragment = FiveFragment()
                selected_button5.visibility = View.VISIBLE
            }
            R.id.listButton -> {
                popuplogin()
            }
        }
        //v.setBackgroundColor(Color.parseColor("#2B53A2"))

        if (fragment != null) {

            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_right, fragment).commit()
        }
    }

    fun popuplogin() {

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_login, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)

        if (MainActivity.manager_name == "") {
            //다이얼로그 뒤로가기 버튼 막기
            dialog.setCancelable(false)
            //밖에부분 터치 막기
            dialog.setCanceledOnTouchOutside(false)
        } else {
            dialog_view.login_id.setText(MainActivity.manager_name)
            dialog_view.login_id.isFocusableInTouchMode = false
            dialog_view.login_password.isFocusableInTouchMode = true
        }

        dialog_view.login_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (dialog_view.login_id.text.toString() != "" && dialog_view.login_password.text.toString() != "") {
                    dialog_view.Login.isEnabled = true
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button_blue)
                } else {
                    dialog_view.Login.isEnabled = false
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        dialog_view.login_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (dialog_view.login_id.text.toString() != "" && dialog_view.login_password.text.toString() != "") {
                    dialog_view.Login.isEnabled = true
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button_blue)
                } else {
                    dialog_view.Login.isEnabled = false
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        //개발용
        dialog_view.login_id.setText("hanshin")
        dialog_view.login_password.setText("hanshin1678")

        val login = dialog_view.findViewById(R.id.Login) as Button
        login.setOnClickListener {

            if (dialog_view.login_id.text.toString() != "") {

                var UserArray: ArrayList<UserList> = ArrayList()

                val user = dialog_view.login_id.text.toString()
                var pass = dialog_view.login_password.text.toString()

                UserArray.add(UserList(user, pass))

                val sql_db = LocalDBhelper(this).writableDatabase

                val datacount = LocalDBhelper(this).UserCheck(sql_db!!, UserArray)

                if (datacount == 0) {
                    Toast.makeText(applicationContext, "유저정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    dialog.dismiss()
                    Handler().postDelayed({
                        startActivity(Intent(this, SettingActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    }, 125)

                }
            }
        }
        dialog.show()

    }


}
