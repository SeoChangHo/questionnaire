package com.fineinsight.zzango.questionnaire

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.Examinee
import com.fineinsight.zzango.questionnaire.DataClass.ExamineeInfo
import com.fineinsight.zzango.questionnaire.DataClass.PublicDataInfo
import com.fineinsight.zzango.questionnaire.DataClass.SavePaper
import com.fineinsight.zzango.questionnaire.LocalList.HospitalList
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.ValidationBool
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.alert_view
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.canvas_motion
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.chart
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.exam_no
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.hospital
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.isJuminValidated
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
import java.io.ByteArrayOutputStream

class ExamListActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_list)

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)

        // 초기 화면 설정
        selected_button1.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().add(R.id.fragment_right, FirstFragment()).commit()
        logoSetting()
        user_login.text = Examinee.USER.info.NAME +"님"

        btnList.setOnClickListener {
            finish()
        }

        user_image.setOnClickListener {
            user_login.text = ""
            user_image.setImageResource(R.drawable.regi)

            Examinee.USER.init()

            Toast.makeText(this, "사용자가 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            MainActivity.chart.clear()

            //추가
            MainActivity.canvas_motion = null

            if(MainActivity.manager_name == "bestian"){
                startActivity(Intent(this, LoginExamActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            } else {
                startActivity(Intent(this, Main2Activity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
        }

        listButton.setOnClickListener {
            popuplogin()
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

    //개별 클릭했을 때 뜨는 팝업
    fun userlogin(view : Button, view2 : ImageView, context : Context, startPage : String){

//        println("isUserLogin: ${isUserLogin}")
//
//        if(!isUserLogin){
//            chart.clear()
//            var dialog = AlertDialog.Builder(context).create()
//            var dialog_view = LayoutInflater.from(context).inflate(R.layout.activity_user_login, null)
//            alert_view = dialog_view
//            ValidationBool = false
//            canvas_motion = null
//
//            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//            dialog.setView(dialog_view)
//            dialog.setCanceledOnTouchOutside(false)
//            dialog_view.user_login_button.isEnabled = false
//
//            //////////😎😎😎서명을 위한 공간😎😎😎/////// ///
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
//            canvasView = dialog_view.canvas
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
//
//
//            dialog_view.user_name.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                    if(dialog_view.user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
//                        dialog_view.user_login_button.isEnabled = true
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
//                    }else{
//                        dialog_view.user_login_button.isEnabled = false
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
//                    }
//                }
//
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//                }
//
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                }
//            })
//
//
//            dialog_view.first_serial.addTextChangedListener(object : TextWatcher {
//
//                override fun afterTextChanged(s: Editable?) {
//                    if(dialog_view.user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
//                        dialog_view.user_login_button.isEnabled = true
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
//                    }else{
//                        dialog_view.user_login_button.isEnabled = false
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
//                    }
//                }
//
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    if(s!!.length==6){
//
//                        var Jumin = dialog_view.first_serial.text.toString()
//
//                        validationInside = JuminValidation(Jumin, context)
//
//                        if(validationInside)
//                        {
//                            dialog_view.last_serial.requestFocus()
//                        }
//                        else
//                        {
//                            dialog_view.user_login_button.isEnabled = false
//                            dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
//                        }
//
//                    }
//                    else if(s.length<6)
//                    {
//                        isJuminValidated = false
//                        validationInside = false
//                        dialog_view.user_login_button.isEnabled = false
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
//                    }
//                }
//            })
//
//            dialog_view.last_serial.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                    if(dialog_view.user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
//                        dialog_view.user_login_button.isEnabled = true
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
//                    }else{
//                        dialog_view.user_login_button.isEnabled = false
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
//                    }
//                }
//
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    if(s!!.length == 1){
//
//                        ValidationBool = true
//                        dialog_view.user_login_button.isEnabled = true
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
//
//                    }
//                    else if(s.length<1)
//                    {
//                        ValidationBool = false
//                        dialog_view.user_login_button.isEnabled = false
//                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
//                    }
//                }
//            })
//
//            val login = dialog_view.findViewById(R.id.user_login_button) as Button
//            var reSign = dialog_view.findViewById(R.id.btnReSign) as ImageView
//
//            reSign.setOnClickListener {
//                canvasView.ClearCanvas()
//                dialog_view.user_login_button.isEnabled = false
//                dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
//            }
//
//            login.setOnClickListener{
//
//                if(isJuminValidated)
//                {
//                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
//                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
//                    var bitmap: Bitmap = Bitmap.createBitmap(canvasView.width, canvasView.height, Bitmap.Config.ARGB_8888)
//                    var canvas: Canvas = Canvas(bitmap)
//                    canvasView.draw(canvas)
//
//                    var stream = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                    //MainActivity.user_signature = bitmap
//
//
//                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
//                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
//
//
//
//
//                    Examinee.USER.info= ExamineeInfo(
//                            dialog_view.user_name.text.toString(),
//                            dialog_view.first_serial.text.toString(),
//                            dialog_view.last_serial.text.toString(),
//                            "",
//                            stream.toByteArray(),
//                            false,
//                            true
//                    )
//
//
//                    //chart(user_first_serial, false, false)
//                    chart.clear()
//
//
//                    println("user_last_serial.toInt(): ${Examinee.USER.info.JUMIN1.toInt()}")
//                    println("user_last_serial.toInt()%2: ${Examinee.USER.info.JUMIN1.toInt()%2}")
//                    println("user_last_serial.toInt()%2 == 0 : ->${Examinee.USER.info.JUMIN1.toInt()%2==0}")
//                    AdditionalArr.Gender.isFemale = Examinee.USER.info.JUMIN1.toInt()%2 == 0
//
//                    Toast.makeText(context, "사용자가 등록되었습니다.", Toast.LENGTH_SHORT).show()
//                    view.text = Examinee.USER.info.NAME+"님"
//                    view2.setImageResource(R.drawable.exit)
//                    dialog.dismiss()

                    //login_appbar_loading_progress.visibility = View.VISIBLE
                    //login_appbar_loading_progress_bg.visibility = View.VISIBLE

//                    SavePaper.Total.Init()
//                    var PArray = ArrayList<PublicDataInfo>()
//                    exam_no = System.currentTimeMillis().toString()
//
//
//                    SavePaper.Total.Array[0] = PublicDataInfo(hospital, Examinee.USER.info.NAME, Examinee.USER.info.JUMIN1, Examinee.USER.info.JUMIN2, Examinee.USER.info.SIGN, exam_no)
//
//                    when(startPage){
//                        "CommonExaminationActivity" -> {
//                            Handler().postDelayed({
//                                startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                            },125)
//                        }
//                        "MentalExaminationActivity" -> {
//                            Handler().postDelayed({
//                                startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                            },125)
//                        }
//                        "CognitiveExaminationActivity" -> {
//                            Handler().postDelayed({
//                                startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                            },125)
//                        }
//                        "ElderlyExaminationActivity" -> {
//                            Handler().postDelayed({
//                                startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                            },125)
//                        }
//                        "ExerciseExaminationActivity" -> {
//                            Handler().postDelayed({
//                                startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                            },125)
//                        }
//                        "OralExaminationActivity" -> {
//                            Handler().postDelayed({
//                                startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                            },125)
//                        }
//                        "CancerExaminationActivity" -> {
//                            Handler().postDelayed({
//                                startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                            },125)
//                        }
//
//                    }
//                }
//                else
//                {
//                    Toast.makeText(this, "주민번호 형식을 확인해주세요.", Toast.LENGTH_LONG).show()
//                }
//            }
//
////            dialog.show()
//        }
//        else
//        {
            chart.clear()


            SavePaper.Total.Init()
            exam_no = System.currentTimeMillis().toString()


            SavePaper.Total.Array[0] = PublicDataInfo(hospital, Examinee.USER.info.NAME, Examinee.USER.info.JUMIN1, Examinee.USER.info.JUMIN2, Examinee.USER.info.SIGN, exam_no)

            when(startPage){
                "CommonExaminationActivity" -> {
                    Handler().postDelayed({
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    },125)
                }
                "MentalExaminationActivity" -> {
                    Handler().postDelayed({
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    },125)
                }
                "CognitiveExaminationActivity" -> {
                    Handler().postDelayed({
                        startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    },125)
                }
                "ElderlyExaminationActivity" -> {
                    Handler().postDelayed({
                        startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    },125)
                }
                "ExerciseExaminationActivity" -> {
                    Handler().postDelayed({
                        startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    },125)
                }
                "OralExaminationActivity" -> {
                    Handler().postDelayed({
                        startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    },125)
                }
                "CancerExaminationActivity" -> {
                    Handler().postDelayed({
                        startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    },125)
                }

            }
        }
//    }

}
