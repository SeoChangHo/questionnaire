package com.fineinsight.zzango.questionnaire

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.Examinee
import com.fineinsight.zzango.questionnaire.DataClass.ExamineeInfo
import com.fineinsight.zzango.questionnaire.DataClass.PublicDataInfo
import com.fineinsight.zzango.questionnaire.DataClass.SavePaper
import com.fineinsight.zzango.questionnaire.LocalList.HospitalList
import com.fineinsight.zzango.questionnaire.Signature.CanvasView
import com.fineinsight.zzango.questionnaire.UserList.UserList
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login_agree.btnList
import kotlinx.android.synthetic.main.activity_login_agree.btnReSign
import kotlinx.android.synthetic.main.activity_login_agree.canvas
import kotlinx.android.synthetic.main.activity_login_agree.first_serial
import kotlinx.android.synthetic.main.activity_login_agree.first_view
import kotlinx.android.synthetic.main.activity_login_agree.last_serial
import kotlinx.android.synthetic.main.activity_login_agree.listButton
import kotlinx.android.synthetic.main.activity_login_agree.user_login_button
import kotlinx.android.synthetic.main.activity_login_agree.user_name
import kotlinx.android.synthetic.main.activity_login_exam.*
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LoginAgreeActivity : AppCompatActivity() {

    var isUserLogin: Boolean = false
    lateinit var canvasView: CanvasView
    var validationInside = false
    var userlogin_buttonClick = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_agree)

        userlogin2(this)

        listButton.setOnClickListener {
            popuplogin()
        }

    }

    fun userlogin2(context: Context) {


        //if(user_login.text == "님"){
        if (!isUserLogin) {

            MainActivity.userLoginButton = user_login_button
            MainActivity.userName = user_name

            MainActivity.ValidationBool = false
            MainActivity.canvas_motion = null

            user_login_button.isEnabled = false

//            //////////😎😎😎서명을 위한 공간😎😎😎/////// ///
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
            canvasView = canvas
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////


            user_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {


                    if (user_name.text.toString() != "" && MainActivity.ValidationBool && MainActivity.canvas_motion != null && MainActivity.isJuminValidated) {
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)
                    } else {
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    if (user_name.text.toString() != "") {
                        user_name.setBackgroundResource(R.drawable.start_login_back2)
                    } else {
                        user_name.setBackgroundResource(R.drawable.start_login_back)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }


                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })


            first_serial.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {

                    if (user_name.text.toString() != "" && MainActivity.ValidationBool && MainActivity.canvas_motion != null && MainActivity.isJuminValidated) {
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)
                    } else {
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    if (first_serial.text.toString() != "") {
                        first_serial.setBackgroundResource(R.drawable.start_login_back2)
                    } else {
                        first_serial.setBackgroundResource(R.drawable.start_login_back)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.length == 6) {

                        var Jumin = first_serial.text.toString()

                        validationInside = JuminValidation(Jumin, context)

                        if (validationInside) {
                            last_serial.requestFocus()
                        } else {
                            user_login_button.isEnabled = false
                            user_login_button.setBackgroundResource(R.drawable.start_login_back)
                        }

                    } else if (s.length < 6) {
                        MainActivity.isJuminValidated = false
                        validationInside = false
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }
                }
            })

            last_serial.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (user_name.text.toString() != "" && MainActivity.ValidationBool && MainActivity.canvas_motion != null && MainActivity.isJuminValidated) {
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)

                        if (user_name.text.toString() != "" && MainActivity.ValidationBool) {
                            println("aaa")
                            CloseKeyboard()
                        }

                    } else {
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)

                        if (user_name.text.toString() != "" && MainActivity.ValidationBool) {
                            println("bbb")
                            CloseKeyboard()
                        }
                    }

                    if (last_serial.text.toString() != "") {
                        last_serial.setBackgroundResource(R.drawable.start_login_back2)
                    } else {
                        last_serial.setBackgroundResource(R.drawable.start_login_back)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.length == 7) {

                        MainActivity.ValidationBool = true
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)

                    } else if (s.length < 7) {
                        MainActivity.ValidationBool = false
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    println(user_name.text.toString() + "," + MainActivity.ValidationBool + "," + MainActivity.canvas_motion + "," + MainActivity.isJuminValidated)
                }
            })

            btnReSign.setOnClickListener {
                canvasView.ClearCanvas()
                user_login_button.isEnabled = false
                user_login_button.setBackgroundResource(R.drawable.start_login_back)
            }

            user_login_button.setOnClickListener {

                if (MainActivity.isJuminValidated) {

                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    var bitmap: Bitmap = Bitmap.createBitmap(canvasView.width, canvasView.height, Bitmap.Config.ARGB_8888)
                    var canvas: Canvas = Canvas(bitmap)
                    canvasView.draw(canvas)

                    var stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    //MainActivity.user_signature = bitmap


                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////


                    Examinee.USER.info= ExamineeInfo(
                            user_name.text.toString(),
                            first_serial.text.toString(),
                            last_serial.text.toString(),
                            "",
                            stream.toByteArray(),
                            false,
                            true
                    )

                    //UserHandler(true)

                    Toast.makeText(context, "사용자가 등록되었습니다.", Toast.LENGTH_SHORT).show()
//                    user_login.text = login_user_name+"님"

                    Examinee.USER.info= ExamineeInfo(
                            user_name.text.toString(),
                            first_serial.text.toString(),
                            last_serial.text.toString(),
                            "",
                            stream.toByteArray(),
                            true,
                            true
                    )


                    SavePaper.Total.Init()
                    MainActivity.exam_no = System.currentTimeMillis().toString()
                    SavePaper.Total.Array[0] = PublicDataInfo(MainActivity.hospital, Examinee.USER.info.NAME, Examinee.USER.info.JUMIN1, Examinee.USER.info.JUMIN2, Examinee.USER.info.SIGN, MainActivity.exam_no)

                    //user_image.setImageResource(R.drawable.exit)

                    //login_appbar_loading_progress.visibility = View.VISIBLE
                    //login_appbar_loading_progress_bg.visibility = View.VISIBLE


                    println("user_last_serial.toInt(): ${Examinee.USER.info.JUMIN1.toInt()}")
                    println("user_last_serial.toInt()%2: ${Examinee.USER.info.JUMIN1.toInt() % 2}")
                    println("user_last_serial.toInt()%2 == 0 : ->${Examinee.USER.info.JUMIN1.toInt() % 2 == 0}")
                    AdditionalArr.Gender.isFemale = Examinee.USER.info.JUMIN1.toInt() % 2 == 0

                } else {
                    Toast.makeText(this, "주민번호 형식을 확인해주세요.", Toast.LENGTH_LONG).show()
                }

                userlogin_buttonClick = true

                startActivity(Intent(this, AgreementActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                //입력창 초기화
                user_name.text.clear()
                first_serial.text.clear()
                last_serial.text.clear()
                canvasView.ClearCanvas()
                user_login_button.isEnabled = false
                user_login_button.setBackgroundResource(R.drawable.start_login_back)

                MainActivity.chart.clear()

            }

        }

        //로그아웃시 옮김

//        if(view.text == login_user_name+"님"){
//
//            var dialog = AlertDialog.Builder(this).create()
//            var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_user_logout, null)
//
//            //다이얼로그 뒤로가기 버튼 막기
//            dialog.setCancelable(false)
//
//            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//            dialog.setView(dialog_view)
//            dialog.setCanceledOnTouchOutside(false)
//
//            val logout = dialog_view.findViewById(R.id.user_logout) as Button
//            val cancel = dialog_view.findViewById(R.id.user_logout_cancel) as Button
//
//            dialog.show()
//
//            logout.setOnClickListener {
//
//                login_user_name = ""
//                user_first_serial = ""
//                user_last_serial = ""
//
//                Toast.makeText(context, "사용자가 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
//                view.text = "사용자 등록하기"
//                view2.setImageResource(R.drawable.regi)
//
//                canvas_motion = null
//
//                chart.clear()
//                dialog.dismiss()
//                userlogin_buttonClick = true
//            }
//
//            cancel.setOnClickListener {
//
//                canvas_motion = null
//                userlogin_buttonClick = true
//                dialog.dismiss()
//            }
//
//        }

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


    fun JuminValidation(Jumin: String, context: Context): Boolean {
        MainActivity.isJuminValidated = false
        var yy = Jumin.substring(0, 2)
        var mm = Jumin.substring(2, 4)
        var dd = Jumin.substring(4, 6)

        println(yy + " " + mm + " " + dd)

        if (mm.toInt() == 0) {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            MainActivity.isJuminValidated = false
            return false
        } else if (12 < mm.toInt()) {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            MainActivity.isJuminValidated = false
            return false
        }

        if (dd.toInt() == 0) {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            MainActivity.isJuminValidated = false
            return false
        }
        //1 3 5 7 9 10  12
        //   4 6 8    11
        // 2

        //31일까지 있는 달일 때
        if (mm.toInt() == 1 || mm.toInt() == 3 || mm.toInt() == 5 || mm.toInt() == 7 || mm.toInt() == 9 || mm.toInt() == 10 || mm.toInt() == 12) {
            if (31 < dd.toInt()) {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                MainActivity.isJuminValidated = false
                return false
            }
        }//30일까지 있는 달일 때
        else if (mm.toInt() == 4 || mm.toInt() == 6 || mm.toInt() == 8 || mm.toInt() == 11) {
            if (30 < dd.toInt()) {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                MainActivity.isJuminValidated = false
                return false
            }
        }//2월일때 (윤달 미포함)
        else if (mm.toInt() == 2) {
            if (29 < dd.toInt()) {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                MainActivity.isJuminValidated = false
                return false
            }
        }
        MainActivity.isJuminValidated = true
        println("true입니다!!")
        return true
    }

    fun CloseKeyboard() {
        var view = this.currentFocus

        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}
