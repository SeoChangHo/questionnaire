package com.fineinsight.zzango.questionnaire

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.*
import android.widget.Button
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.LocalList.HospitalList
import com.fineinsight.zzango.questionnaire.UserList.UserList
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login_exam.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main2.listButton
import kotlinx.android.synthetic.main.quit_alert.*
import kotlinx.android.synthetic.main.quit_alert.view.*

class Main2Activity : AppCompatActivity() {

    var popup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnSetting()

        listButton.setOnClickListener {
            popuplogin()
        }

    }

    fun btnSetting(){

        btn_agreement.setOnClickListener {
            startActivity(Intent(this, LoginAgreeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        btn_exam.setOnClickListener {
            startActivity(Intent(this, LoginExamActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
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
                    dialog_view.Login.setBackgroundResource(R.drawable.start_login_button)
                } else {
                    dialog_view.Login.isEnabled = false
                    dialog_view.Login.setBackgroundResource(R.drawable.start_login_button_default)
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
                    dialog_view.Login.setBackgroundResource(R.drawable.start_login_button)
                } else {
                    dialog_view.Login.isEnabled = false
                    dialog_view.Login.setBackgroundResource(R.drawable.start_login_button_default)
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
                    if (MainActivity.manager_name == "") {
                        MainActivity.manager_name = user
                        setHospitalList()
                        listButton.visibility = View.VISIBLE
                        Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        dialog.dismiss()
                        Handler().postDelayed({
                            startActivity(Intent(this, SettingActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        }, 125)

                    }

                }
            }


        }

        dialog.show()

    }

    fun setHospitalList() {

        if (MainActivity.manager_name == "fine") {

            MainActivity.hospital = HospitalList.hospital.test
            //main_logo.setImageResource(R.drawable.logo)

        } else if (MainActivity.manager_name == "mokpohos") {

            MainActivity.hospital = HospitalList.hospital.Mokpo
            //main_logo.setImageResource(R.drawable.logo2)

        } else if (MainActivity.manager_name == "hanshin") {

            MainActivity.hospital = HospitalList.hospital.Banpo

        } else if (MainActivity.manager_name == "bestian") {

            MainActivity.hospital = HospitalList.hospital.Osong
            //main_logo.setImageResource(R.drawable.bestianlogo)
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
