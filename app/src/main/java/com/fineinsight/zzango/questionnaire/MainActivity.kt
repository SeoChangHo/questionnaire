package com.fineinsight.zzango.questionnaire


import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.*
import com.fineinsight.zzango.questionnaire.Network.NetworkCheck
import com.fineinsight.zzango.questionnaire.Signature.CanvasView
import com.fineinsight.zzango.questionnaire.UserList.User
import com.fineinsight.zzango.questionnaire.UserList.UserList
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.first_serial
import kotlinx.android.synthetic.main.activity_main.last_serial
import kotlinx.android.synthetic.main.activity_user_login.view.*
import kotlinx.android.synthetic.main.quit_alert.view.*
import kotlinx.android.synthetic.main.save_location.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity() , View.OnClickListener {

    var popup = false
    lateinit var canvasView: CanvasView
    var sql_db : SQLiteDatabase? = null
    var validationInside = false
    var userlogin_buttonClick = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CreatePaperTable()

        NetworkCheck.Func.Check(this)

        //앱을 구동하는 동안 화면이 절대 꺼지지 않도록 함
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        userLogin = user_login
        userImage = user_image

        UserCheck()

        var wfm = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager


        if(!this.intent.hasExtra("from")) {
            startLogin()
        }else{
            userlogin2(this@MainActivity)
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

        //button1.setBackgroundColor(Color.parseColor("#2B53A2"))
        selected_button1.visibility = View.VISIBLE

        listButton.setOnClickListener{
            popuplogin()
        }


        btnList.setOnClickListener {
            main_start_list.visibility = View.VISIBLE
            main_start_login1.visibility = View.GONE
            main_start_login2.visibility = View.GONE
        }


        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)


        if(login_user_name != login_user_name+"님"){
            user_login.text = login_user_name+"님"
            user_image.setImageResource(R.drawable.exit)
            first_view.visibility = View.VISIBLE
            second_view.visibility = View.GONE
        }else{
            user_login.text = "사용자 등록하기"
            user_image.setImageResource(R.drawable.regi)
            first_view.visibility = View.GONE
            second_view.visibility = View.VISIBLE
        }

    }

    fun CreatePaperTable()
    {
        val sql_db = LocalDBhelper(this).writableDatabase
        LocalDBhelper(this).CreatePaperTable(sql_db)
    }

    fun popuplogin(){

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_login, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)

        if(manager_name == ""){
            //다이얼로그 뒤로가기 버튼 막기
            dialog.setCancelable(false)
            //밖에부분 터치 막기
            dialog.setCanceledOnTouchOutside(false)
        }else{
            dialog_view.login_id.setText(manager_name)
            dialog_view.login_id.isFocusableInTouchMode = false
            dialog_view.login_password.isFocusableInTouchMode = true
        }

        dialog_view.login_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(dialog_view.login_id.text.toString() != "" && dialog_view.login_password.text.toString() != ""){
                    dialog_view.Login.isEnabled = true
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button_blue)
                }else{
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
                if(dialog_view.login_id.text.toString() != "" && dialog_view.login_password.text.toString() != ""){
                    dialog_view.Login.isEnabled = true
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button_blue)
                }else{
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
        dialog_view.login_id.setText("bestian")
        dialog_view.login_password.setText("best1234")

        val login = dialog_view.findViewById(R.id.Login) as Button
        login.setOnClickListener{

            if(dialog_view.login_id.text.toString() != ""){

                var UserArray:ArrayList<UserList> = ArrayList()

                val user = dialog_view.login_id.text.toString()
                var pass = dialog_view.login_password.text.toString()

                UserArray.add(UserList(user, pass))

                val sql_db = LocalDBhelper(this).writableDatabase

                val datacount = LocalDBhelper(this).UserCheck(sql_db!!, UserArray)

                if(datacount==0)
                {
                    Toast.makeText(applicationContext, "유저정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    if(manager_name == ""){
                        manager_name = user
                        setHospitalList()
                        Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }else{
                        dialog.dismiss()
                        Handler().postDelayed({
                            startActivity(Intent(this@MainActivity, SettingActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        },125)

                    }

                }
            }



        }

        dialog.show()

    }

    fun startLogin(){

        main_start_login1.visibility = View.VISIBLE
        main_start_login2.visibility = View.GONE

        login_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(login_id.text.toString() != "" && login_password.text.toString() != ""){
                    Login.isEnabled = true
                    Login.setBackgroundResource(R.drawable.start_login_button)
                }else{
                    Login.isEnabled = false
                    Login.setBackgroundResource(R.drawable.start_login_back)
                }

                if(login_id.text.toString() != ""){
                    login_id.setTextColor(Color.GRAY)
                    login_id.setBackgroundResource(R.drawable.start_login_back2)
                }else{
                    login_id.setTextColor(Color.WHITE)
                    login_id.setBackgroundResource(R.drawable.start_login_back)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(login_id.text.toString() != ""){
                    login_id.setTextColor(Color.GRAY)
                    login_id.setBackgroundResource(R.drawable.start_login_back2)
                }else{
                    login_id.setTextColor(Color.WHITE)
                    login_id.setBackgroundResource(R.drawable.start_login_back)
                }
            }
        })

        login_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(login_id.text.toString() != "" && login_password.text.toString() != ""){
                    Login.isEnabled = true
                    Login.setBackgroundResource(R.drawable.start_login_button)
                }else{
                    Login.isEnabled = false
                    Login.setBackgroundResource(R.drawable.start_login_back)
                }

                if(login_password.text.toString() != ""){
                    login_password.setTextColor(Color.GRAY)
                    login_password.setBackgroundResource(R.drawable.start_login_back2)
                }else{
                    login_password.setTextColor(Color.WHITE)
                    login_password.setBackgroundResource(R.drawable.start_login_back)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(login_password.text.toString() != ""){
                    login_password.setTextColor(Color.GRAY)
                    login_password.setBackgroundResource(R.drawable.start_login_back2)
                }else{
                    login_password.setTextColor(Color.WHITE)
                    login_password.setBackgroundResource(R.drawable.start_login_back)
                }
            }
        })

        //개발용
        login_id.setText("bestian")
        login_password.setText("best1234")

        Login.setOnClickListener{

            if(login_id.text.toString() != ""){

                var UserArray:ArrayList<UserList> = ArrayList()

                val user = login_id.text.toString()
                var pass = login_password.text.toString()

                UserArray.add(UserList(user, pass))

                val sql_db = LocalDBhelper(this).writableDatabase

                val datacount = LocalDBhelper(this).UserCheck(sql_db!!, UserArray)

                if(datacount==0)
                {
                    Toast.makeText(applicationContext, "유저정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    if(manager_name == ""){
                        manager_name = user
                        setHospitalList()
                        Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()

                        main_start_login1.visibility = View.GONE
                        main_start_login2.visibility = View.VISIBLE
                        userlogin2(this@MainActivity)

                    }else{

                        main_start_login1.visibility = View.VISIBLE
                        main_start_login2.visibility = View.GONE
                        Handler().postDelayed({
                            startActivity(Intent(this@MainActivity, SettingActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                        },125)

                    }

                }
            }

        }

    }

    //개별 클릭했을 때 뜨는 팝업
    fun userlogin(view : Button, view2 : ImageView, context : Context, startPage : String){
        if(view.text == "사용자 등록하기"){
            chart.clear()
            var dialog = AlertDialog.Builder(context).create()
            var dialog_view = LayoutInflater.from(context).inflate(R.layout.activity_user_login, null)
            alert_view = dialog_view
            ValidationBool = false
            canvas_motion = null

            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setView(dialog_view)
            dialog.setCanceledOnTouchOutside(false)
            dialog_view.user_login_button.isEnabled = false

            //////////😎😎😎서명을 위한 공간😎😎😎/////// ///
            //////////😎😎😎서명을 위한 공간😎😎😎//////////
            canvasView = dialog_view.canvas
            //////////😎😎😎서명을 위한 공간😎😎😎//////////
            //////////😎😎😎서명을 위한 공간😎😎😎//////////


            dialog_view.user_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(dialog_view.user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
                        dialog_view.user_login_button.isEnabled = true
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
                    }else{
                        dialog_view.user_login_button.isEnabled = false
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }


                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })


            dialog_view.first_serial.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    if(dialog_view.user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
                        dialog_view.user_login_button.isEnabled = true
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
                    }else{
                        dialog_view.user_login_button.isEnabled = false
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s!!.length==6){

                        var Jumin = dialog_view.first_serial.text.toString()

                        validationInside = JuminValidation(Jumin, context)

                        if(validationInside)
                        {
                            dialog_view.last_serial.requestFocus()
                        }
                        else
                        {
                            dialog_view.user_login_button.isEnabled = false
                            dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                        }

                    }
                    else if(s.length<6)
                    {
                        isJuminValidated = false
                        validationInside = false
                        dialog_view.user_login_button.isEnabled = false
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                    }
                }
            })

            dialog_view.last_serial.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(dialog_view.user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
                        dialog_view.user_login_button.isEnabled = true
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
                    }else{
                        dialog_view.user_login_button.isEnabled = false
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s!!.length == 1){

                        ValidationBool = true
                        dialog_view.user_login_button.isEnabled = true
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)

                    }
                    else if(s.length<1)
                    {
                        ValidationBool = false
                        dialog_view.user_login_button.isEnabled = false
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                    }
                }
            })

            val login = dialog_view.findViewById(R.id.user_login_button) as Button
            var reSign = dialog_view.findViewById(R.id.btnReSign) as ImageView

            reSign.setOnClickListener {
                canvasView.ClearCanvas()
                dialog_view.user_login_button.isEnabled = false
                dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
            }

            login.setOnClickListener{

                if(isJuminValidated)
                {
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    var bitmap:Bitmap = Bitmap.createBitmap(canvasView.width, canvasView.height, Bitmap.Config.ARGB_8888)
                    var canvas:Canvas = Canvas(bitmap)
                    canvasView.draw(canvas)

                    var stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    //MainActivity.user_signature = bitmap

                    user_stream = stream.toByteArray()
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////


                    login_user_name = dialog_view.user_name.text.toString()
                    user_first_serial = dialog_view.first_serial.text.toString()
                    user_last_serial = dialog_view.last_serial.text.toString()


                    //chart(user_first_serial, false, false)


                    println("user_last_serial.toInt(): ${user_last_serial.toInt()}")
                    println("user_last_serial.toInt()%2: ${user_last_serial.toInt()%2}")
                    println("user_last_serial.toInt()%2 == 0 : ->${user_last_serial.toInt()%2==0}")
                    AdditionalArr.Gender.isFemale = user_last_serial.toInt()%2 == 0

                    Toast.makeText(context, "사용자가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    view.text = login_user_name+"님"
                    view2.setImageResource(R.drawable.exit)
                    dialog.dismiss()

                    //login_appbar_loading_progress.visibility = View.VISIBLE
                    //login_appbar_loading_progress_bg.visibility = View.VISIBLE

                    SavePaper.Total.Init()
                    var PArray = ArrayList<PublicDataInfo>()
                    exam_no = System.currentTimeMillis().toString()


                    SavePaper.Total.Array[0] = PublicDataInfo(hospital, login_user_name, user_first_serial, user_last_serial, user_stream!!, exam_no)

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
                else
                {
                    Toast.makeText(this, "주민번호 형식을 확인해주세요.", Toast.LENGTH_LONG).show()
                }



            }

            dialog.show()
        }

    }


    //기본 로그인 화면
    fun userlogin2(context : Context){

        main_start_login1.visibility = View.GONE
        main_start_login2.visibility = View.VISIBLE

        if(user_login.text == "님"){

            userLoginButton = user_login_button
            userName = user_name

            ValidationBool = false
            canvas_motion = null

            user_login_button.isEnabled = false

//            //////////😎😎😎서명을 위한 공간😎😎😎/////// ///
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
            canvasView = canvas
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////


            user_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)
                    }else{
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    if(user_name.text.toString() != ""){
                        user_name.setBackgroundResource(R.drawable.start_login_back2)
                    }else{
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
                    if(user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)
                    }else{
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    if(first_serial.text.toString() != ""){
                        first_serial.setBackgroundResource(R.drawable.start_login_back2)
                    }else{
                        first_serial.setBackgroundResource(R.drawable.start_login_back)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s!!.length==6){

                        var Jumin = first_serial.text.toString()

                        validationInside = JuminValidation(Jumin, context)

                        if(validationInside)
                        {
                            last_serial.requestFocus()
                        }
                        else
                        {
                            user_login_button.isEnabled = false
                            user_login_button.setBackgroundResource(R.drawable.start_login_back)
                        }

                    }
                    else if(s.length<6)
                    {
                        isJuminValidated = false
                        validationInside = false
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }
                }
            })

            last_serial.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(user_name.text.toString() != "" && ValidationBool && canvas_motion != null && isJuminValidated){
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)
                    }else{
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    if(last_serial.text.toString() != ""){
                        last_serial.setBackgroundResource(R.drawable.start_login_back2)
                    }else{
                        last_serial.setBackgroundResource(R.drawable.start_login_back)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s!!.length == 1){

                        ValidationBool = true
                        user_login_button.isEnabled = true
                        user_login_button.setBackgroundResource(R.drawable.start_login_button)

                    }
                    else if(s.length<1)
                    {
                        ValidationBool = false
                        user_login_button.isEnabled = false
                        user_login_button.setBackgroundResource(R.drawable.start_login_back)
                    }

                    println(user_name.text.toString() + "," + ValidationBool + "," + canvas_motion + "," + isJuminValidated)
                }
            })

            btnReSign.setOnClickListener {
                canvasView.ClearCanvas()
                user_login_button.isEnabled = false
                user_login_button.setBackgroundResource(R.drawable.start_login_back)
            }

            user_login_button.setOnClickListener{

                if(isJuminValidated)
                {

                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    var bitmap:Bitmap = Bitmap.createBitmap(canvasView.width, canvasView.height, Bitmap.Config.ARGB_8888)
                    var canvas:Canvas = Canvas(bitmap)
                    canvasView.draw(canvas)

                    var stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    //MainActivity.user_signature = bitmap

                    user_stream = stream.toByteArray()
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////
                    //////////😎😎😎서명을 위한 공간😎😎😎//////////



                    login_user_name = user_name.text.toString()
                    user_first_serial = first_serial.text.toString()
                    user_last_serial = last_serial.text.toString()


                    Toast.makeText(context, "사용자가 등록되었습니다.", Toast.LENGTH_SHORT).show()
//                    user_login.text = login_user_name+"님"

                    SavePaper.Total.Init()
                    exam_no = System.currentTimeMillis().toString()
                    SavePaper.Total.Array[0] = PublicDataInfo(hospital, login_user_name, user_first_serial, user_last_serial, user_stream!!, exam_no)

                    user_image.setImageResource(R.drawable.exit)

                    //login_appbar_loading_progress.visibility = View.VISIBLE
                    //login_appbar_loading_progress_bg.visibility = View.VISIBLE


                    println("user_last_serial.toInt(): ${user_last_serial.toInt()}")
                    println("user_last_serial.toInt()%2: ${user_last_serial.toInt()%2}")
                    println("user_last_serial.toInt()%2 == 0 : ->${user_last_serial.toInt()%2==0}")
                    AdditionalArr.Gender.isFemale = user_last_serial.toInt()%2 == 0

                    //현재 접속병원이 목포한국병원이면서 네트워크가 켜져 있을 때
                    if(hospital == HospitalList.hospital.Mokpo && isNetworkAvailable())
                    {
                        var now = LocalDate.now()

                        var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        var NAME = login_user_name
                        var JUMIN = user_first_serial
                        var JUMIN2 = user_last_serial

                        MokpoCheckPaper(context, Strnow, NAME, JUMIN, JUMIN2)
                    }
                    else
                    {
                        println("목포병원이 아니거나 네트워크가 꺼져있습니다")
                        var EmptyStringArr = ArrayList<String>()
                        ShowPaperDIALOG(context, EmptyStringArr, user_first_serial)
                    }
                }
                else
                {
                    Toast.makeText(this, "주민번호 형식을 확인해주세요.", Toast.LENGTH_LONG).show()
                }

                userlogin_buttonClick = true

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

    fun ShowPaperDIALOG(context: Context, arr:ArrayList<String>, JUMIN1:String)
    {


        first_view.visibility = View.GONE

        starticon.animate().alpha(1f).duration = 1000

        ObjectAnimator.ofFloat(starticon, View.Y, 500f, 2000f).apply{
            duration = 1000
            start()
        }

        Handler().postDelayed({
            ObjectAnimator.ofFloat(starticon, View.Y, 2000f, 500f).apply{
                duration = 1000
                start()
            }

            second_view.visibility=View.VISIBLE
            ObjectAnimator.ofFloat(second_view, View.Y, 2200f, 700f).apply{
                duration = 1000
                start()
            }

        }, 1000)


        val ok = findViewById(R.id.user_ok) as Button
        val title = findViewById(R.id.notice_Title) as TextView
        val text = findViewById(R.id.notice_textView) as TextView
        val text2 = findViewById(R.id.notice_textView2) as TextView
        val text3 = findViewById(R.id.notice_textView3) as TextView
        val text4 = findViewById(R.id.notice_textView4) as TextView
        val text5 = findViewById(R.id.notice_textView5) as TextView
        var chkOral = findViewById(R.id.chkOral) as CheckBox
        var chkCancer = findViewById(R.id.chkCancer) as CheckBox


        for (item in arr)
        {
            //암검진 체크
            if (item.contains("03"))
            {
                chkCancer.isChecked = true
            }

            //구강문진 체크
            if (item.contains("75"))
            {
                chkOral.isChecked = true
            }
        }

        chart(JUMIN1)

        title.text = login_user_name+"님"
        user_login.text = login_user_name+"님"

        for(i in chart){

            if(i.isbool){
                when(i.chartName){
                    PaperNameInfo.PC.COMMON.EN_NM -> {

                    }
                    PaperNameInfo.PC.MENTAL.EN_NM -> {
                        text3.visibility = View.VISIBLE
                    }
                    PaperNameInfo.PC.ELDERLY.EN_NM -> {
                        text5.visibility = View.VISIBLE
                    }
                    PaperNameInfo.PC.COGNITIVE.EN_NM -> {
                        text2.visibility = View.VISIBLE
                    }
                    PaperNameInfo.PC.LIFE.EN_NM -> {
                        text4.visibility = View.VISIBLE
                    }
                    else -> {

                    }
                }
            }

        }


        ok.setOnClickListener {

            ProgressAction(true)

            AdditionalArr.Page.init()
            if(chkOral.isChecked)
            {
                AdditionalArr.Page.isOralChecked = true
            }
            if(chkCancer.isChecked)
            {
                AdditionalArr.Page.isCancerChecked = true
            }


            if(chkOral.isChecked){
                chart.add(ChartInfo(PaperNameInfo.PC.ORAL.EN_NM, true, 5))
            }else{
                chart.add(ChartInfo(PaperNameInfo.PC.ORAL.EN_NM, false, 5))
            }

            if(chkCancer.isChecked){
                chart.add(ChartInfo(PaperNameInfo.PC.CANCER.EN_NM, true, 6))
            }else{
                chart.add(ChartInfo(PaperNameInfo.PC.CANCER.EN_NM, false, 6))
            }

            Handler().postDelayed({
                //startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))


                //초기화
                SavePaper.Total.Init()
                exam_no = System.currentTimeMillis().toString()
                SavePaper.Total.Array[0] = PublicDataInfo(hospital, login_user_name, user_first_serial, user_last_serial, user_stream!!, exam_no)



                for(item in chart)
                {
                    if (item.isbool)
                    {
                        when(item.chartName)
                        {
                            PaperNameInfo.PC.COMMON.EN_NM ->
                            {
                                startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.MENTAL.EN_NM ->
                            {
                                startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.COGNITIVE.EN_NM ->
                            {
                                startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.ELDERLY.EN_NM ->
                            {
                                startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.LIFE.EN_NM ->
                            {
                                startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.ORAL.EN_NM ->
                            {
                                startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                            PaperNameInfo.PC.CANCER.EN_NM ->
                            {
                                startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                        }
                        break
                    }
                    else
                    {
                        when(item.chartName)
                        {

                            PaperNameInfo.PC.COMMON.EN_NM ->
                            {
                                SavePaper.Total.Array[1] = Paper_COMMON("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.MENTAL.EN_NM ->
                            {
                                SavePaper.Total.Array[2] = Paper_MENTAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.COGNITIVE.EN_NM ->
                            {
                                SavePaper.Total.Array[3] =Paper_COGNITIVE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.ELDERLY.EN_NM ->
                            {
                                SavePaper.Total.Array[4] = Paper_ELDERLY("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.LIFE.EN_NM ->
                            {
                                SavePaper.Total.Array[5] = Paper_EXERCISE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[6] = Paper_NUTRITION("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[7] = Paper_SMOKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[8] = Paper_DRINKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.ORAL.EN_NM ->
                            {
                                SavePaper.Total.Array[9] = Paper_ORAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.CANCER.EN_NM ->
                            {
                                SavePaper.Total.Array[10] = Paper_CANCER("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }
                        }
                    }
                }

            },100)

        }

        ProgressAction(false)

    }

    fun MokpoCheckPaper(context:Context, DATE:String, NAME:String, JUMIN1:String, JUMIN2:String)
    {

        val MokpoCheckParam = HashMap<String, String>()
        MokpoCheckParam["DATE"] = DATE.trim()
        MokpoCheckParam["NAME"] = NAME.trim()
        MokpoCheckParam["JUMIN"] = JUMIN1.trim() + JUMIN2.trim()

        var CheckArr = ArrayList<String>()


        OracleUtill().getMokpoCheck().SelectMokpoCheckPaper(MokpoCheckParam).enqueue(object : Callback<List<MokpoCheck>> {

            override fun onResponse(call: Call<List<MokpoCheck>>, response: Response<List<MokpoCheck>>) {

                if(response.isSuccessful){

                    val MOKPODATA = response.body()

                    //값이 있음
                    if(!MOKPODATA!!.isEmpty()) {

                        println("값 확인")
                        for (document in MOKPODATA) {
                            println(document.gj_jong)
                            CheckArr.add(document.gj_jong)
                        }
                        ProgressAction(false)
                        ShowPaperDIALOG(context, CheckArr, JUMIN1)
                    }
                    else//값이 없음
                    {
                        println("값이 없음")
                        ProgressAction(false)
                        ShowPaperDIALOG(context, CheckArr, JUMIN1)
                    }

                }
                else//요청실패
                {
                    println("값이 없음")
                    ProgressAction(false)
                    ShowPaperDIALOG(context, CheckArr, JUMIN1)
                }
            }

            //요청 오류
            override fun onFailure(call: Call<List<MokpoCheck>>, t: Throwable) {

                println("값이 없음")
                ProgressAction(false)
                ShowPaperDIALOG(context, CheckArr, JUMIN1)

            }

        })
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

    override fun onBackPressed() {

        userlogin_buttonClick = true

        var dialog = android.app.AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.quit_alert, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.notice.text = "앱을 종료하시겠습니까?"

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
        var isJuminValidated = false
        var login_user_name = ""
        var user_first_serial = ""
        var user_last_serial = ""
        var user_stream:ByteArray? = null
        var chart = ArrayList<ChartInfo>()
        var chartNumber = 0
        var manager_name = ""
        var exam_no = ""
        @SuppressLint("StaticFieldLeak")
        var alert_view : View? = null
        var ValidationBool = false
        var canvas_motion : MotionEvent? = null
        var hospital = ""
        var userLogin : Button? = null
        var userImage : ImageView? = null
        var userLoginButton : Button? = null
        var userName : TextView? = null
    }

    fun assetsToBitmap(fileName:String):Bitmap?{
        return try{
            val stream = assets.open(fileName)
            BitmapFactory.decodeStream(stream)
        }catch (e:IOException){
            e.printStackTrace()
            null
        }
    }

    fun bitmapToFile(bitmap:Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

    fun JuminValidation(Jumin : String, context : Context): Boolean
    {
        isJuminValidated = false
        var yy = Jumin.substring(0,2)
        var mm = Jumin.substring(2,4)
        var dd = Jumin.substring(4,6)

        println(yy+" "+mm+" "+dd)

        if(mm.toInt()==0)
        {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            isJuminValidated = false
            return false
        }
        else if(12<mm.toInt())
        {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            isJuminValidated = false
            return false
        }

        if(dd.toInt()==0)
        {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            isJuminValidated = false
            return false
        }
        //1 3 5 7 9 10  12
        //   4 6 8    11
        // 2

        //31일까지 있는 달일 때
        if(mm.toInt()==1 || mm.toInt()==3 ||mm.toInt()==5 ||mm.toInt()==7 ||mm.toInt()==9 ||mm.toInt()==10 ||mm.toInt()==12)
        {
            if(31<dd.toInt())
            {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                isJuminValidated = false
                return false
            }
        }//30일까지 있는 달일 때
        else if(mm.toInt()==4 || mm.toInt()==6 ||mm.toInt()==8 ||mm.toInt()==11)
        {
            if(30<dd.toInt())
            {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                isJuminValidated = false
                return false
            }
        }//2월일때 (윤달 미포함)
        else if(mm.toInt()==2)
        {
            if(29<dd.toInt())
            {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                isJuminValidated = false
                return false
            }
        }
        isJuminValidated = true
        println("true입니다!!")
        return true
    }

    fun UserCheck()
    {
        println("유저체크")

        sql_db = LocalDBhelper(this).writableDatabase

        LocalDBhelper(this).UserTableCreate(sql_db!!)
        val data = LocalDBhelper(this).UserUpdateCheck(sql_db!!)

        if(data.count==0)//앱 깔고 첫 구동 유저가 없음. 유저 정보 삽입
        {
            var UserListArray:ArrayList<UserList> = ArrayList()

            println("유저정보 삽입")
            println(User.Map)

            for (item in User.Map)
            {
                println(item.key)
                println(item.value)

                UserListArray.add(UserList(item.key, item.value))
            }

            LocalDBhelper(this).UserInsert(sql_db!!, UserListArray)

        }
        else
        {
            var SqlArray:ArrayList<UserList> = ArrayList()
            var UserListArray:ArrayList<UserList> = ArrayList()

            println("유저정보가 업데이트 있나 확인함")

            data.moveToFirst()


            //SQL USER 정보 가져오기
            while(!data.isAfterLast){

                SqlArray.add(UserList(data.getString(data.getColumnIndex("user")),
                        data.getString(data.getColumnIndex("pass"))))
                data.moveToNext()
            }

            //로컬 기기 USER 정보 가져오기
            for (item in User.Map)
            {
                println(item.key)
                println(item.value)

                UserListArray.add(UserList(item.key, item.value))
            }

            //기존정보와 같다 아무것도 하지 않는다.
            if(UserListArray==SqlArray)
            {
                println("기존정보와 같다.")
            }
            else//기존정보와 다르다. 삭제하고 다시 삽입한다.
            {
                LocalDBhelper(this).UserDeleteAndInsert(sql_db!!, UserListArray)
            }
        }


    }

    @SuppressLint("NewApi")
    fun chart(Jumin : String){

        var yy = Jumin.substring(0,2)

        var currentDateTime = LocalDateTime.now()
        var currentYear = currentDateTime.year

        //만 20세, 30세
        var two = (currentYear - 20).toString().substring(2,4)
        var two2 = (currentYear - 30).toString().substring(2,4)
        //만 40세, 50세, 60세
        var three = (currentYear - 40).toString().substring(2,4)
        var three2 = (currentYear - 50).toString().substring(2,4)
        var three3 = (currentYear - 60).toString().substring(2,4)
        //만 66세, 80세
        var four = (currentYear - 66).toString().substring(2,4)
        var four2 = (currentYear - 80).toString().substring(2,4)
        //만 68세, 72세, 74세, 76세, 77세, 82세, 84세, 86세
        var five = (currentYear - 68).toString().substring(2,4)
        var five2 = (currentYear - 72).toString().substring(2,4)
        var five3 = (currentYear - 74).toString().substring(2,4)
        var five4 = (currentYear - 76).toString().substring(2,4)
        var five5 = (currentYear - 77).toString().substring(2,4)
        var five6 = (currentYear - 82).toString().substring(2,4)
        var five7= (currentYear - 84).toString().substring(2,4)
        var five8 = (currentYear - 86).toString().substring(2,4)
        //만 70세
        var six = (currentYear - 70).toString().substring(2,4)

        println(two + two2)


        if(yy == two || yy == two2){
            //우울증 포함
            //chart = PaperArray.SetList.SET2
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, true, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, false, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, false, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, false,  4))
        }else if(yy == three || yy == three2 || yy == three3 ){
            //우울증 생활습관 포함
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, true, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, false, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, false, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, true, 4))
        }else if(yy == four || yy == four2){
            //인지기능 노인신체기능검사 포함
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, false, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, true, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, true, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, false, 4))
        }else if(yy == five || yy == five2 || yy == five3 || yy == five4 || yy == five5 || yy == five6 || yy == five7 || yy == five8){
            //인지기능 포함
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, false, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, true, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, false, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, false, 4))
        }else if(yy == six){
            //인지기능 우울증 생활습관 노인신체기능검사 포함
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, true, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, true, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, true, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, true, 4))
        }else{
            //기본검사
            chart.add(ChartInfo(PaperNameInfo.PC.COMMON.EN_NM, true, 0))
            chart.add(ChartInfo(PaperNameInfo.PC.MENTAL.EN_NM, false, 1))
            chart.add(ChartInfo(PaperNameInfo.PC.COGNITIVE.EN_NM, false, 2))
            chart.add(ChartInfo(PaperNameInfo.PC.ELDERLY.EN_NM, false, 3))
            chart.add(ChartInfo(PaperNameInfo.PC.LIFE.EN_NM, false, 4))
        }

    }

    fun setHospitalList(){

//        if(manager_name == "fine"){
//
//            hospital = HospitalList.hospital.test
//            main_logo.setImageResource(R.drawable.logo)
//
//        }else if(manager_name == "mokpohos"){
//
//            hospital = HospitalList.hospital.Mokpo
//            main_logo.setImageResource(R.drawable.logo2)
//
//        }else if(manager_name == "hanshin"){
//
//            hospital = HospitalList.hospital.Banpo
//
//        }else if(manager_name == "bestian"){
//
//            hospital = HospitalList.hospital.Osong
//            main_logo.setImageResource(R.drawable.bestianlogo)
//
//        }

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    fun ProgressAction(isShow:Boolean)
    {
        if(isShow)
        {
            login_appbar_loading_progress.visibility = View.VISIBLE
            login_appbar_loading_progress_bg.visibility = View.VISIBLE
            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        else
        {
            login_appbar_loading_progress.visibility = View.GONE
            login_appbar_loading_progress_bg.visibility = View.GONE
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onResume() {
        super.onResume()
        chart.clear()
        println("Chart Clear!")
    }



}