package com.example.zzango.questionnaire


import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.PaperArray
import com.example.zzango.questionnaire.Signature.CanvasView
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_login.view.*
import kotlinx.android.synthetic.main.quit_alert.view.*
import kotlinx.android.synthetic.main.save_location.view.*
import java.io.*
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity() , View.OnClickListener {

    var popup = false
    lateinit var canvasView: CanvasView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var wfm = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

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
            userlogin(user_login, user_image,this@MainActivity)
        }

        if(MainActivity.login_user_name != ""){
            user_login.text = MainActivity.login_user_name+"님"
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
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button_blue)
                }else{
                    dialog_view.Login.isClickable = false
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
                    dialog_view.Login.isClickable = true
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button_blue)
                }else{
                    dialog_view.Login.isClickable = false
                    dialog_view.Login.setBackgroundResource(R.drawable.user_login_button)
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

    fun userlogin(view : Button, view2 : ImageView, context : Context){

        if(view.text == "사용자 등록하기"){



            var dialog = AlertDialog.Builder(context).create()
            var dialog_view = LayoutInflater.from(context).inflate(R.layout.activity_user_login, null)

            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setView(dialog_view)
            dialog.setCanceledOnTouchOutside(false)
            dialog_view.user_login_button.isClickable = false

            var ValidationBool:Boolean = false

//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
            canvasView = dialog_view.canvas
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////
//            //////////😎😎😎서명을 위한 공간😎😎😎//////////


            dialog_view.user_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(dialog_view.user_name.text.toString() != "" && dialog_view.first_serial.text.toString() != "" && dialog_view.last_serial.text.toString() != "")
                    {
                        if(ValidationBool)
                        {
                            dialog_view.user_login_button.isClickable = true
                            dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
                        }
                    }else{
                        dialog_view.user_login_button.isClickable = false
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
                    if(dialog_view.user_name.text.toString() != "" && dialog_view.first_serial.text.toString() != "" && dialog_view.last_serial.text.toString() != ""){

                        if(ValidationBool)
                        {
                            dialog_view.user_login_button.isClickable = true
                            dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
                        }

                    }else{
                        dialog_view.user_login_button.isClickable = false
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s!!.length==6){

                        var Jumin = dialog_view.first_serial.text.toString()

                        ValidationBool = JuminValidation(Jumin, context)

                        if(ValidationBool)
                        {
                            dialog_view.last_serial.requestFocus()
                        }
                        else
                        {
                            dialog_view.user_login_button.isClickable = false
                            dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                        }

                    }
                    else if(s.length<6)
                    {
                        ValidationBool = false
                        dialog_view.user_login_button.isClickable = false
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                    }
                }
            })

            dialog_view.last_serial.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(dialog_view.user_name.text.toString() != "" && dialog_view.first_serial.text.toString() != "" && dialog_view.last_serial.text.toString() != ""){
                        if(ValidationBool) {
                            dialog_view.user_login_button.isClickable = true
                            dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button_blue)
                        }
                    }else{
                        dialog_view.user_login_button.isClickable = false
                        dialog_view.user_login_button.setBackgroundResource(R.drawable.user_login_button)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            val login = dialog_view.findViewById(R.id.user_login_button) as Button
            login.setOnClickListener{


                //////////😎😎😎서명을 위한 공간😎😎😎//////////
                //////////😎😎😎서명을 위한 공간😎😎😎//////////
                var bitmap:Bitmap = Bitmap.createBitmap(canvasView.width, canvasView.height, Bitmap.Config.ARGB_8888)
                var canvas:Canvas = Canvas(bitmap)
                canvasView.draw(canvas)

                var stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                //MainActivity.user_signature = bitmap

                MainActivity.user_stream = stream.toByteArray()
                //////////😎😎😎서명을 위한 공간😎😎😎//////////
                //////////😎😎😎서명을 위한 공간😎😎😎//////////



                MainActivity.login_user_name = dialog_view.user_name.text.toString()
                MainActivity.user_first_serial = dialog_view.first_serial.text.toString()
                MainActivity.user_last_serial = dialog_view.last_serial.text.toString()

                chart(user_first_serial)

                Toast.makeText(context, "사용자가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                view.text = MainActivity.login_user_name+"님"
                view2.setImageResource(R.drawable.exit)
                dialog.dismiss()

                //login_appbar_loading_progress.visibility = View.VISIBLE
                //login_appbar_loading_progress_bg.visibility = View.VISIBLE

                Handler().postDelayed({
                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                },125)

            }



            dialog.show()
        }

        if(view.text == MainActivity.login_user_name+"님"){

            MainActivity.login_user_name = ""
            MainActivity.user_first_serial = ""
            MainActivity.user_last_serial = ""

            Toast.makeText(context, "사용자가 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            view.text = "사용자 등록하기"
            view2.setImageResource(R.drawable.regi)

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
                    .replace(R.id.fragment_right, fragment).commit()
        }

    }

    override fun onBackPressed() {

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
        var login_user_name = ""
        var user_first_serial = ""
        var user_last_serial = ""
        var user_stream:ByteArray? = null
        var chart = ""
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

        var yy = Jumin.substring(0,2)
        var mm = Jumin.substring(2,4)
        var dd = Jumin.substring(4,6)

        println(yy+" "+mm+" "+dd)

        if(mm.toInt()==0)
        {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(12<mm.toInt())
        {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        if(dd.toInt()==0)
        {
            Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
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
                return false
            }
        }//30일까지 있는 달일 때
        else if(mm.toInt()==4 || mm.toInt()==6 ||mm.toInt()==8 ||mm.toInt()==11)
        {
            if(30<dd.toInt())
            {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                return false
            }
        }//2월일때 (윤달 미포함)
        else if(mm.toInt()==2)
        {
            if(29<dd.toInt())
            {
                Toast.makeText(context, "주민번호 앞자리 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
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
            MainActivity.chart = PaperArray.SetList.SET2
        }else if(yy == three || yy == three2 || yy == three3 ){
            //우울증 생활습관 포함
            MainActivity.chart = PaperArray.SetList.SET3
        }else if(yy == four || yy == four2){
            //인지기능 노인신체기능검사 포함
            MainActivity.chart = PaperArray.SetList.SET4
        }else if(yy == five || yy == five2 || yy == five3 || yy == five4 || yy == five5 || yy == five6 || yy == five7 || yy == five8){
            //인지기능 포함
            MainActivity.chart = PaperArray.SetList.SET5
        }else if(yy == six){
            //인지기능 우울증 생활습관 노인신체기능검사 포함
            MainActivity.chart = PaperArray.SetList.SET6
        }else{
            //기본검사
            MainActivity.chart = PaperArray.SetList.SET1
        }

    }

}
