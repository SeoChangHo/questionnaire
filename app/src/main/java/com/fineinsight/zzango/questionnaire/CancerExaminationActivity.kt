package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.ChartDivision
import com.fineinsight.zzango.questionnaire.DataClass.SavePaper
import com.fineinsight.zzango.questionnaire.DataClass.ServerPaper_Cancer
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import com.fineinsight.zzango.questionnaire.LocalList.Paper_CANCER
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_cancer_exam.*
import kotlinx.android.synthetic.main.activity_cancer_exam.Signature
import kotlinx.android.synthetic.main.activity_cancer_exam.first_serial
import kotlinx.android.synthetic.main.activity_cancer_exam.last_serial
import kotlinx.android.synthetic.main.activity_cancer_exam.login_appbar_loading_progress
import kotlinx.android.synthetic.main.activity_cancer_exam.login_appbar_loading_progress_bg
import kotlinx.android.synthetic.main.activity_cancer_exam.name_edit
import kotlinx.android.synthetic.main.activity_elderly_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class CancerExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancer_exam)


        var gender = ""
        if(AdditionalArr.Gender.isFemale)
        {
            gender = "여자"
        }
        else
        {
            gender = "남자"
        }
        println("성별: ${gender}")

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        cancer_1_1.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_1_true_detail)

            editTextCondition(isChecked, cancer_editText1)

        }

        cancer_2_2.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_2_false_detail)

            editTextCondition(isChecked, cancer_editText2)

        }

        cancer_3_1_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_1_wrapper)

        }

        cancer_3_2_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_2_wrapper)

        }

        cancer_3_3_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_3_wrapper)

        }

        cancer_3_4_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_4_wrapper)

        }

        cancer_3_5_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_5_wrapper)

        }

        cancer_3_6_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_6_wrapper)

            checkCondition(isChecked, cancer_3_6_1_editText)

            editTextCondition(isChecked, cancer_3_6_1_editText)

        }

        cancer_5_1_checkBox.setOnClickListener {
            cancer_5_2_checkBox.isChecked = false
            cancer_5_3_checkBox.isChecked = false
            cancer_5_4_checkBox.isChecked = false
            cancer_5_5_checkBox.isChecked = false
            cancer_5_6_checkBox.isChecked = false
        }

        cancer_5_2_checkBox.setOnClickListener {
            cancer_5_1_checkBox.isChecked = false
        }
        cancer_5_3_checkBox.setOnClickListener {
            cancer_5_1_checkBox.isChecked = false
        }
        cancer_5_4_checkBox.setOnClickListener {
            cancer_5_1_checkBox.isChecked = false
        }
        cancer_5_5_checkBox.setOnClickListener {
            cancer_5_1_checkBox.isChecked = false
        }
        cancer_5_6_checkBox.setOnClickListener {
            cancer_5_1_checkBox.isChecked = false
        }

        cancer_6_1_checkBox.setOnClickListener {
            cancer_6_2_checkBox.isChecked = false
            cancer_6_3_checkBox.isChecked = false
            cancer_6_4_checkBox.isChecked = false
            cancer_6_5_checkBox.isChecked = false
            cancer_6_6_checkBox.isChecked = false
        }

        cancer_6_2_checkBox.setOnClickListener {
            cancer_6_1_checkBox.isChecked = false
        }
        cancer_6_3_checkBox.setOnClickListener {
            cancer_6_1_checkBox.isChecked = false
        }
        cancer_6_4_checkBox.setOnClickListener {
            cancer_6_1_checkBox.isChecked = false
        }
        cancer_6_5_checkBox.setOnClickListener {
            cancer_6_1_checkBox.isChecked = false
        }
        cancer_6_6_checkBox.setOnClickListener {
            cancer_6_1_checkBox.isChecked = false
        }

        cancer_7_1_checkBox.setOnClickListener {
            cancer_7_2_checkBox.isChecked = false
            cancer_7_3_checkBox.isChecked = false
            cancer_7_4_checkBox.isChecked = false
            cancer_7_5_checkBox.isChecked = false
            cancer_7_6_checkBox.isChecked = false
        }

        cancer_7_2_checkBox.setOnClickListener {
            cancer_7_1_checkBox.isChecked = false
        }
        cancer_7_3_checkBox.setOnClickListener {
            cancer_7_1_checkBox.isChecked = false
        }
        cancer_7_4_checkBox.setOnClickListener {
            cancer_7_1_checkBox.isChecked = false
        }
        cancer_7_5_checkBox.setOnClickListener {
            cancer_7_1_checkBox.isChecked = false
        }
        cancer_7_6_checkBox.setOnClickListener {
            cancer_7_1_checkBox.isChecked = false
        }

        cancer_8_1.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_8_detail)

            editTextCondition(isChecked, cancer_editText3)

        }

        cancer_9_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_9_detail)

            editTextCondition(isChecked, cancer_editText4)

        }

        cancer_examination_save.setOnClickListener {

            if(check()){

                if(ChartDivision.ChartDivision.next_or_save(6)){
                    ChartDivision.ChartDivision.chart_array_insert(this, 6)
                }else {
                    ChartDivision.ChartDivision.each_insert(this, 6)
                }
            }

        }

        cancer_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        cancer_edit_submit.setOnClickListener {

            finish()

        }

        if(!AdditionalArr.Gender.isFemale){

            cancer_question_4_3.visibility = View.GONE
            cancer_4_3_radio.visibility = View.GONE

            cancer_question_4_7.visibility = View.GONE
            cancer_4_7_radio.visibility = View.GONE

            family_medical_history3.visibility = View.GONE

            cancer_question_8.visibility = View.GONE
            cancer_question_9.visibility = View.GONE
            cancer_question_10.visibility = View.GONE
            cancer_question_11.visibility = View.GONE
            cancer_question_12.visibility = View.GONE
            cancer_question_13.visibility = View.GONE
            cancer_question_14.visibility = View.GONE

            cancer_8_radio.visibility = View.GONE
            cancer_9_radio.visibility = View.GONE
            cancer_10_radio.visibility = View.GONE
            cancer_11_radio.visibility = View.GONE
            cancer_12_radio.visibility = View.GONE
            cancer_13_radio.visibility = View.GONE
            cancer_14_radio.visibility = View.GONE

        }else{
            cancer_question_4_3.visibility = View.VISIBLE
            cancer_4_3_radio.visibility = View.VISIBLE

            cancer_question_4_7.visibility = View.VISIBLE
            cancer_4_7_radio.visibility = View.VISIBLE

            family_medical_history3.visibility = View.VISIBLE

            cancer_question_8.visibility = View.VISIBLE
            cancer_question_9.visibility = View.VISIBLE
            cancer_question_10.visibility = View.VISIBLE
            cancer_question_11.visibility = View.VISIBLE
            cancer_question_12.visibility = View.VISIBLE
            cancer_question_13.visibility = View.VISIBLE
            cancer_question_14.visibility = View.VISIBLE

            cancer_8_radio.visibility = View.VISIBLE
            cancer_9_radio.visibility = View.VISIBLE
            cancer_10_radio.visibility = View.VISIBLE
            cancer_11_radio.visibility = View.VISIBLE
            cancer_12_radio.visibility = View.VISIBLE
            cancer_13_radio.visibility = View.VISIBLE
            cancer_14_radio.visibility = View.VISIBLE

        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        //서버에서 들어온것 일때 구분 사인은 안보이게 처리
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_CANCER) {

                var paper = intent.getSerializableExtra("paper") as Paper_CANCER

                GetPaper(paper)

                try {
                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature,0, paper.signature.size)

                    Signature.setImageBitmap(bmp)

                }
                catch (e:Exception)
                {
                    println(e.message)
                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Cancer

                GetPaper(paper)

            }

        }else{
            name_edit.text = MainActivity.login_user_name
            first_serial.text = MainActivity.user_first_serial
            last_serial.text = MainActivity.user_last_serial

            if(ChartDivision.ChartDivision.next_or_save(6)){
                cancer_examination_save.text = "다음"
            }else {
                elderly_examination_save.text = "저장"
            }

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    override fun onResume() {

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        super.onResume()

    }

    override fun onBackPressed() {

        if(login_appbar_loading_progress.visibility != View.VISIBLE){

            cancelAlert()

        }

    }


//    fun cancer_exam_local_insert(){
//
//        LocalDBhelper(this).LocalListCancerInsert(sql_db!!, PaperArray.PaperList.Arr_CANCER!!)
//
//        LocalDBhelper(this).cancerCreate(sql_db)
//        LocalDBhelper(this).cancerSaveLocal(sql_db!!, PaperArray.PaperList.Arr_CANCER!!)
//        saveCompleteAlert()
//
//    }
//
//    fun cancer_exam_server_insert(){
//
//    if(wfm!!.isWifiEnabled || (connectivityManager!!.activeNetwork != null && connectivityManager!!.getNetworkCapabilities(connectivityManager!!.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))) {
//
//            login_appbar_loading_progress.visibility = View.VISIBLE
//            login_appbar_loading_progress_bg.visibility = View.VISIBLE
//            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//
//            var SaveArr = ArrayList<Any>()
//            var OneArr = ArrayList<Any>()
//
//            var InfoArr = ArrayList<String>()
//
//
//            InfoArr.add("SET8")
//            InfoArr.add(MainActivity.hospital)
//
//            OneArr.add(PaperArray.PaperList.Arr_CANCER!!)
//            SaveArr.add(InfoArr)
//            SaveArr.add(OneArr)
//
//
//            OracleUtill().save_papers().savePapersServer(SaveArr).enqueue(object : Callback<String> {
//
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//
//                    if (response.isSuccessful) {
//
//                        if (!response.body()!!.equals("S")) {
//
//                            login_appbar_loading_progress.visibility = View.GONE
//                            login_appbar_loading_progress_bg.visibility = View.GONE
//                            this@CancerExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//                            Toast.makeText(this@CancerExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
//
//                        } else {
//
//                            saveCompleteAlert()
//
//                        }
//
//                    }
//
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//
//                    login_appbar_loading_progress.visibility = View.GONE
//                    login_appbar_loading_progress_bg.visibility = View.GONE
//                    this@CancerExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//                    Toast.makeText(this@CancerExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
//                    println(t.toString())
//                }
//
//            })
//
//        }else{
//
//            wifiCheck()
//
//        }
//
//    }
//
//    fun saveCompleteAlert(){
//
//        login_appbar_loading_progress.visibility = View.GONE
//        login_appbar_loading_progress_bg.visibility = View.GONE
//        this@CancerExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//
//        popup = false
//
//        var dialog = AlertDialog.Builder(this).create()
//        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_complete_alert, null)
//
//        dialog.setCancelable(false)
//        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        dialog.setView(dialog_view)
//        dialog_view.save_complete_alert_text.text = "저장이 완료 되었습니다"
//
//        if(!popup) {
//
//            dialog.show().let {
//
//                popup = true
//
//            }
//
//        }
//
//        var displayMetrics = DisplayMetrics()
//        dialog.window.windowManager.defaultDisplay.getMetrics(displayMetrics)
//        // The absolute width of the available display size in pixels.
//        var displayWidth = displayMetrics.widthPixels
//        // The absolute height of the available display size in pixels.
//        var displayHeight = displayMetrics.heightPixels
//
//        // Initialize a new window manager layout parameters
//        var layoutParams = WindowManager.LayoutParams()
//
//        // Copy the alert dialog window attributes to new layout parameter instance
//        layoutParams.copyFrom(dialog.window.attributes)
//
//        // Set the alert dialog window width and height
//        // Set alert dialog width equal to screen width 90%
//        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
//        // Set alert dialog height equal to screen height 90%
//        // int dialogWindowHeight = (int) (displayHeight * 0.9f);
//
//        // Set alert dialog width equal to screen width 70%
//        var dialogWindowWidth = (displayWidth * 0.7f).toInt()
//        // Set alert dialog height equal to screen height 70%
//        var dialogWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT
//
//        // Set the width and height for the layout parameters
//        // This will bet the width and height of alert dialog
//        layoutParams.width = dialogWindowWidth
//        layoutParams.height = dialogWindowHeight
//
//        // Apply the newly created layout parameters to the alert dialog window
//        dialog.window.attributes = layoutParams
//
//
//        dialog.setOnDismissListener {
//
//            popup = false
//            dialog = null
//
//        }
//
//        dialog_view.return_alert.setOnClickListener {
//
//            MainActivity.login_user_name = ""
//            MainActivity.user_first_serial = ""
//            MainActivity.user_last_serial = ""
//
//            MainActivity.userLogin!!.text = "사용자 등록하기"
//            MainActivity.userImage!!.setImageResource(R.drawable.regi)
//
//            startActivity(Intent(this@CancerExaminationActivity, MainActivity::class.java).putExtra("from", "cancer").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//
//            dialog.dismiss()
//
//        }
//
//    }

    @SuppressLint("NewApi")
    fun check() : Boolean {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = "cancer"
        var ck1 = ""
        var ck1_1 = ""
        var ck2 = ""
        var ck2_1 = ""
        var ck3_1 = ""
        var ck3_1_1 = ""
        var ck3_1_2 = ""
        var ck3_1_3 = ""
        var ck3_1_4 = ""
        var ck3_1_5 = ""
        var ck3_2 = ""
        var ck3_2_1 = ""
        var ck3_2_2 = ""
        var ck3_2_3 = ""
        var ck3_2_4 = ""
        var ck3_2_5 = ""
        var ck3_3 = ""
        var ck3_3_1 = ""
        var ck3_3_2 = ""
        var ck3_3_3 = ""
        var ck3_3_4 = ""
        var ck3_3_5 = ""
        var ck3_4 = ""
        var ck3_4_1 = ""
        var ck3_4_2 = ""
        var ck3_4_3 = ""
        var ck3_4_4 = ""
        var ck3_4_5 = ""
        var ck3_5 = ""
        var ck3_5_1 = ""
        var ck3_5_2 = ""
        var ck3_5_3 = ""
        var ck3_5_4 = ""
        var ck3_5_5 = ""
        var ck3_6 = ""
        var ck3_6_1 = ""
        var ck3_6_2 = ""
        var ck3_6_3 = ""
        var ck3_6_4 = ""
        var ck3_6_5 = ""
        var ck3_6_kita = ""
        var ck4_1 = ""
        var ck4_2 = ""
        var ck4_3 = ""
        var ck4_4 = ""
        var ck4_5 = ""
        var ck4_6 = ""
        var ck4_7 = ""
        var ck4_8 = ""
        var ck5_1 = ""
        var ck5_2 = ""
        var ck5_3 = ""
        var ck5_4 = ""
        var ck5_5 = ""
        var ck5_6 = ""
        var ck6_1 = ""
        var ck6_2 = ""
        var ck6_3 = ""
        var ck6_4 = ""
        var ck6_5 = ""
        var ck6_6 = ""
        var ck7_1 = ""
        var ck7_2 = ""
        var ck7_3 = ""
        var ck7_4 = ""
        var ck7_5 = ""
        var ck7_6 = ""
        var ck8_1 = ""
        var ck8_2 = ""
        var ck9_1 = ""
        var ck9_2 = ""
        var ck10 = ""
        var ck11 = ""
        var ck12 = ""
        var ck13 = ""
        var ck14 = ""


        if (!name_edit.text.isNullOrEmpty()) {
            name = name_edit.text.toString()
        } else {
            Toast.makeText(this, "성명 또는 주민번호란을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if (!first_serial.text.isNullOrEmpty()) {
            first_serial_text = first_serial.text.toString()
        } else {
            Toast.makeText(this, "성명 또는 주민번호란을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if (!last_serial.text.isNullOrEmpty()) {
            last_serial_text = last_serial.text.toString()
        } else {
            Toast.makeText(this, "성명 또는 주민번호란을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_1_1.isChecked){
            ck1 = "1"
            if(!cancer_editText1.text.isNullOrEmpty()){
                ck1_1 = cancer_editText1.text.toString()
            }else{
                Toast.makeText(this, "1번 증상을 작성해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else if(cancer_1_2.isChecked){
            ck1 = "2"
        }else{
            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_2_1.isChecked){
            ck2 = "1"
        }else if(cancer_2_2.isChecked){
            ck2 = "2"
            if(!cancer_editText2.text.isNullOrEmpty()){
                ck2_1 = cancer_editText2.text.toString()
            }else{
                Toast.makeText(this, "2번 증상을 작성해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else{
            Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_1_1.isChecked){
            ck3_1 = "1"
        }else if(cancer_3_1_2.isChecked){
            ck3_1 = "3"
        }else if(cancer_3_1_3.isChecked){
            ck3_1 = "2"

            if(cancer_3_1_checkBox1.isChecked){
                ck3_1_1 = "2"
            }else{
                ck3_1_1 = "1"
            }
            if(cancer_3_1_checkBox2.isChecked){
                ck3_1_2 = "2"
            }else{
                ck3_1_2 = "1"
            }
            if(cancer_3_1_checkBox3.isChecked){
                ck3_1_3 = "2"
            }else{
                ck3_1_3 = "1"
            }
            if(cancer_3_1_checkBox4.isChecked){
                ck3_1_4 = "2"
            }else{
                ck3_1_4 = "1"
            }
            if(cancer_3_1_checkBox5.isChecked){
                ck3_1_5 = "2"
            }else{
                ck3_1_5 = "1"
            }

            if(cancer_3_1_checkBox1.isChecked){
            }else if(cancer_3_1_checkBox2.isChecked){
            }else if(cancer_3_1_checkBox3.isChecked){
            }else if(cancer_3_1_checkBox4.isChecked){
            }else if(cancer_3_1_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-1번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

        }else{
            Toast.makeText(this, "3-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_2_1.isChecked){
            ck3_2 = "1"
        }else if(cancer_3_2_2.isChecked){
            ck3_2 = "3"
        }else if(cancer_3_2_3.isChecked){
            ck3_2 = "2"

            if(cancer_3_2_checkBox1.isChecked){
            }else if(cancer_3_2_checkBox2.isChecked){
            }else if(cancer_3_2_checkBox3.isChecked){
            }else if(cancer_3_2_checkBox4.isChecked){
            }else if(cancer_3_2_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-2번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_2_checkBox1.isChecked){
                ck3_2_1 = "2"
            }else{
                ck3_2_1 = "1"
            }
            if(cancer_3_2_checkBox2.isChecked){
                ck3_2_2 = "2"
            }else{
                ck3_2_2 = "1"
            }
            if(cancer_3_2_checkBox3.isChecked){
                ck3_2_3 = "2"
            }else{
                ck3_2_3 = "1"
            }
            if(cancer_3_2_checkBox4.isChecked){
                ck3_2_4 = "2"
            }else{
                ck3_2_4 = "1"
            }
            if(cancer_3_2_checkBox5.isChecked){
                ck3_2_5 = "2"
            }else{
                ck3_2_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_3_1.isChecked){
            ck3_3 = "1"
        }else if(cancer_3_3_2.isChecked){
            ck3_3 = "3"
        }else if(cancer_3_3_3.isChecked){
            ck3_3 = "2"
            if(cancer_3_3_checkBox1.isChecked){
            }else if(cancer_3_3_checkBox2.isChecked){
            }else if(cancer_3_3_checkBox3.isChecked){
            }else if(cancer_3_3_checkBox4.isChecked){
            }else if(cancer_3_3_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-3번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_3_checkBox1.isChecked) {
                ck3_3_1 = "2"
            }else{
                ck3_3_1 = "1"
            }
            if(cancer_3_3_checkBox2.isChecked){
                ck3_3_2 = "2"
            }else{
                ck3_3_2 = "1"
            }
            if(cancer_3_3_checkBox3.isChecked){
                ck3_3_3 = "2"
            }else{
                ck3_3_3 = "1"
            }
            if(cancer_3_3_checkBox4.isChecked){
                ck3_3_4 = "2"
            }else{
                ck3_3_4 = "1"
            }
            if(cancer_3_3_checkBox5.isChecked){
                ck3_3_5 = "2"
            }else{
                ck3_3_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cancer_3_4_1.isChecked){
            ck3_4 = "1"
        }else if(cancer_3_4_2.isChecked){
            ck3_4 = "3"
        }else if(cancer_3_4_3.isChecked){
            ck3_4 = "2"
            if(cancer_3_4_checkBox1.isChecked){
            }else if(cancer_3_4_checkBox2.isChecked){
            }else if(cancer_3_4_checkBox3.isChecked){
            }else if(cancer_3_4_checkBox4.isChecked){
            }else if(cancer_3_4_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-4번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_4_checkBox1.isChecked){
                ck3_4_1 = "2"
            }else{
                ck3_4_1 = "1"
            }
            if(cancer_3_4_checkBox2.isChecked){
                ck3_4_2 = "2"
            }else{
                ck3_4_2 = "1"
            }
            if(cancer_3_4_checkBox3.isChecked){
                ck3_4_3 = "2"
            }else{
                ck3_4_3 = "1"
            }
            if(cancer_3_4_checkBox4.isChecked){
                ck3_4_4 = "2"
            }else{
                ck3_4_4 = "1"
            }
            if(cancer_3_4_checkBox5.isChecked){
                ck3_4_5 = "2"
            }else{
                ck3_4_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_5_1.isChecked){
            ck3_5 = "1"
        }else if(cancer_3_5_2.isChecked){
            ck3_5 = "3"
        }else if(cancer_3_5_3.isChecked){
            ck3_5 = "2"
            if(cancer_3_5_checkBox1.isChecked){
            }else if(cancer_3_5_checkBox2.isChecked){
            }else if(cancer_3_5_checkBox3.isChecked){
            }else if(cancer_3_5_checkBox4.isChecked){
            }else if(cancer_3_5_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-5번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_5_checkBox1.isChecked){
                ck3_5_1 = "2"
            }else{
                ck3_5_1 = "1"
            }
            if(cancer_3_5_checkBox2.isChecked){
                ck3_5_2 = "2"
            }else{
                ck3_5_2 = "1"
            }
            if(cancer_3_5_checkBox3.isChecked){
                ck3_5_3 = "2"
            }else{
                ck3_5_3 = "1"
            }
            if(cancer_3_5_checkBox4.isChecked){
                ck3_5_4 = "2"
            }else{
                ck3_5_4 = "1"
            }
            if(cancer_3_5_checkBox5.isChecked){
                ck3_5_5 = "2"
            }else{
                ck3_5_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_6_1.isChecked){
            ck3_6 = "1"
        }else if(cancer_3_6_2.isChecked){
            ck3_6 = "3"
        }else if(cancer_3_6_3.isChecked){
            ck3_6 = "2"
            if(cancer_3_6_checkBox1.isChecked){
            }else if(cancer_3_6_checkBox2.isChecked){
            }else if(cancer_3_6_checkBox3.isChecked){
            }else if(cancer_3_6_checkBox4.isChecked){
            }else if(cancer_3_6_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-6번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_6_checkBox1.isChecked){
                ck3_6_1 = "2"
            }else{
                ck3_6_1 = "1"
            }
            if(cancer_3_6_checkBox2.isChecked){
                ck3_6_2 = "2"
            }else{
                ck3_6_2 = "1"
            }
            if(cancer_3_6_checkBox3.isChecked){
                ck3_6_3 = "2"
            }else{
                ck3_6_3 = "1"
            }
            if(cancer_3_6_checkBox4.isChecked){
                ck3_6_4 = "2"
            }else{
                ck3_6_4 = "1"
            }
            if(cancer_3_6_checkBox5.isChecked){
                ck3_6_5 = "2"
            }else{
                ck3_6_5 = "1"
            }

            if(cancer_3_6_1_editText.text.toString() != ""){
                ck3_6_kita = cancer_3_6_1_editText.text.toString()
            }

        }else{
            Toast.makeText(this, "3-6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_4_1_1.isChecked){
            ck4_1 = "1"
        }else if(cancer_4_1_2.isChecked){
            ck4_1 = "2"
        }else if(cancer_4_1_3.isChecked){
            ck4_1 = "3"
        }else if(cancer_4_1_4.isChecked){
            ck4_1 = "4"
        }

        if(cancer_4_2_1.isChecked){
            ck4_2 = "1"
        }else if(cancer_4_2_2.isChecked){
            ck4_2 = "2"
        }else if(cancer_4_2_3.isChecked){
            ck4_2 = "3"
        }else if(cancer_4_2_4.isChecked){
            ck4_2 = "4"
        }

        if(AdditionalArr.Gender.isFemale){
            if(cancer_4_3_1.isChecked){
                ck4_3 = "1"
            }else if(cancer_4_3_2.isChecked){
                ck4_3 = "2"
            }else if(cancer_4_3_3.isChecked){
                ck4_3 = "3"
            }else if(cancer_4_3_4.isChecked){
                ck4_3 = "4"
            }
        }

        if(cancer_4_4_1.isChecked){
            ck4_4 = "1"
        }else if(cancer_4_4_2.isChecked){
            ck4_4 = "2"
        }else if(cancer_4_4_3.isChecked){
            ck4_4 = "3"
        }else if(cancer_4_4_4.isChecked){
            ck4_4 = "4"
        }

        if(cancer_4_5_1.isChecked){
            ck4_5 = "1"
        }else if(cancer_4_5_2.isChecked){
            ck4_5 = "2"
        }else if(cancer_4_5_3.isChecked){
            ck4_5 = "3"
        }else if(cancer_4_5_4.isChecked){
            ck4_5 = "4"
        }


        if(cancer_4_6_1.isChecked){
            ck4_6 = "1"
        }else if(cancer_4_6_2.isChecked){
            ck4_6 = "2"
        }else if(cancer_4_6_3.isChecked){
            ck4_6 = "3"
        }else if(cancer_4_6_4.isChecked){
            ck4_6 = "4"
        }

        if(AdditionalArr.Gender.isFemale){
            if(cancer_4_7_1.isChecked){
                ck4_7 = "1"
            }else if(cancer_4_7_2.isChecked){
                ck4_7 = "2"
            }else if(cancer_4_7_3.isChecked){
                ck4_7 = "3"
            }else if(cancer_4_7_4.isChecked){
                ck4_7 = "4"
            }
        }

        if(cancer_4_8_1.isChecked){
            ck4_8 = "1"
        }else if(cancer_4_8_2.isChecked){
            ck4_8 = "2"
        }else if(cancer_4_8_3.isChecked){
            ck4_8 = "3"
        }else if(cancer_4_8_4.isChecked){
            ck4_8 = "4"
        }

        if(cancer_5_1_checkBox.isChecked){
            ck5_6 = "2"
        }else{
            ck5_6 = "1"
        }

        if(cancer_5_2_checkBox.isChecked){
            ck5_1 = "2"
        }else{
            ck5_1 = "1"
        }

        if(cancer_5_3_checkBox.isChecked){
            ck5_2 = "2"
        }else{
            ck5_2 = "1"
        }

        if(cancer_5_4_checkBox.isChecked){
            ck5_3 = "2"
        }else{
            ck5_3 = "1"
        }

        if(cancer_5_5_checkBox.isChecked){
            ck5_4 = "2"
        }else{
            ck5_4 = "1"
        }

        if(cancer_5_6_checkBox.isChecked){
            ck5_5 = "2"
        }else{
            ck5_5 = "1"
        }

        if(cancer_6_1_checkBox.isChecked){
            ck6_6 = "2"
        }else{
            ck6_6 = "1"
        }

        if(cancer_6_2_checkBox.isChecked){
            ck6_1 = "2"
        }else{
            ck6_1 = "1"
        }

        if(cancer_6_3_checkBox.isChecked){
            ck6_2 = "2"
        }else{
            ck6_2 = "1"
        }

        if(cancer_6_4_checkBox.isChecked){
            ck6_3 = "2"
        }else{
            ck6_3 = "1"
        }

        if(cancer_6_5_checkBox.isChecked){
            ck6_4 = "2"
        }else{
            ck6_4 = "1"
        }

        if(cancer_6_6_checkBox.isChecked){
            ck6_5 = "2"
        }else{
            ck6_5 = "1"
        }

        if(cancer_7_1_checkBox.isChecked){
            ck7_6 = "2"
        }else{
            ck7_6 = "1"
        }

        if(cancer_7_2_checkBox.isChecked){
            ck7_1 = "2"
        }else{
            ck7_1 = "1"
        }

        if(cancer_7_3_checkBox.isChecked){
            ck7_2 = "2"
        }else{
            ck7_2 = "1"
        }

        if(cancer_7_4_checkBox.isChecked){
            ck7_3 = "2"
        }else{
            ck7_3 = "1"
        }

        if(cancer_7_5_checkBox.isChecked){
            ck7_4 = "2"
        }else{
            ck7_4 = "1"
        }

        if(cancer_7_6_checkBox.isChecked){
            ck7_5 = "2"
        }else{
            ck7_5 = "1"
        }

        if(AdditionalArr.Gender.isFemale){
            if(cancer_8_1.isChecked){
                ck8_1 = "1"
                if(!cancer_editText3.text.isNullOrEmpty()){
                    ck8_2 = cancer_editText3.text.toString()
                }else{
                    Toast.makeText(this, "8번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                    return false
                }
            }else if(cancer_8_2.isChecked){
                ck8_1 = "2"
            }else{
                Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_9_1.isChecked){
                ck9_1 = "1"
            }else if(cancer_9_2.isChecked){
                ck9_1 = "2"
            }else if(cancer_9_3.isChecked){
                ck9_1 = "3"
                if(!cancer_editText4.text.isNullOrEmpty()){
                    ck9_2 = cancer_editText4.text.toString()
                }else{
                    Toast.makeText(this, "9번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                    return false
                }
            }else{
                Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_10_1.isChecked){
                ck10 = "1"
            }else if(cancer_10_2.isChecked){
                ck10 = "2"
            }else if(cancer_10_3.isChecked){
                ck10 = "3"
            }else if(cancer_10_4.isChecked){
                ck10 = "4"
            }else if(cancer_10_5.isChecked){
                ck10 = "5"
            }else{
                Toast.makeText(this, "10번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_11_1.isChecked){
                ck11 = "1"
            }else if(cancer_11_2.isChecked){
                ck11 = "2"
            }else if(cancer_11_3.isChecked){
                ck11 = "3"
            }else{
                Toast.makeText(this, "11번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_12_1.isChecked){
                ck12 = "1"
            }else if(cancer_12_2.isChecked){
                ck12 = "2"
            }else if(cancer_12_3.isChecked){
                ck12 = "3"
            }else if(cancer_12_4.isChecked){
                ck12 = "4"
            }else{
                Toast.makeText(this, "12번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_13_1.isChecked){
                ck13 = "1"
            }else if(cancer_13_2.isChecked){
                ck13 = "2"
            }else if(cancer_13_3.isChecked){
                ck13 = "3"
            }else{
                Toast.makeText(this, "13번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_14_1.isChecked){
                ck14 = "1"
            }else if(cancer_14_2.isChecked){
                ck14 = "2"
            }else if(cancer_14_3.isChecked){
                ck14 = "3"
            }else if(cancer_14_4.isChecked){
                ck14 = "4"
            }else{
                Toast.makeText(this, "14번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }

        PaperArray.PaperArrFunction.ArrayListInit()
        exam_no = System.currentTimeMillis().toString()

        PaperArray.PaperList.Arr_CANCER!!.add(Paper_CANCER(
                exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category,
                ck1, ck1_1, ck2, ck2_1,
                ck3_1, ck3_1_1, ck3_1_2, ck3_1_3, ck3_1_4, ck3_1_5,
                ck3_2, ck3_2_1, ck3_2_2, ck3_2_3, ck3_2_4, ck3_2_5,
                ck3_3, ck3_3_1, ck3_3_2, ck3_3_3, ck3_3_4, ck3_3_5,
                ck3_4, ck3_4_1, ck3_4_2, ck3_4_3, ck3_4_4, ck3_4_5,
                ck3_5, ck3_5_1, ck3_5_2, ck3_5_3, ck3_5_4, ck3_5_5,
                ck3_6, ck3_6_1, ck3_6_2, ck3_6_3, ck3_6_4, ck3_6_5, ck3_6_kita,
                ck4_1, ck4_2, ck4_3, ck4_4, ck4_5, ck4_6,
                ck4_7, ck4_8, ck5_1, ck5_2, ck5_3, ck5_4, ck5_5, ck5_6, ck6_1, ck6_2, ck6_3, ck6_4,
                ck6_5, ck6_6, ck7_1, ck7_2, ck7_3, ck7_4, ck7_5, ck7_6, ck8_1, ck8_2, ck9_1, ck9_2,
                ck10, ck11, ck12, ck13, ck14
        ))

        SavePaper.Total.Array[9] = Paper_CANCER(
                exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category,
                ck1, ck1_1, ck2, ck2_1,
                ck3_1, ck3_1_1, ck3_1_2, ck3_1_3, ck3_1_4, ck3_1_5,
                ck3_2, ck3_2_1, ck3_2_2, ck3_2_3, ck3_2_4, ck3_2_5,
                ck3_3, ck3_3_1, ck3_3_2, ck3_3_3, ck3_3_4, ck3_3_5,
                ck3_4, ck3_4_1, ck3_4_2, ck3_4_3, ck3_4_4, ck3_4_5,
                ck3_5, ck3_5_1, ck3_5_2, ck3_5_3, ck3_5_4, ck3_5_5,
                ck3_6, ck3_6_1, ck3_6_2, ck3_6_3, ck3_6_4, ck3_6_5, ck3_6_kita,
                ck4_1, ck4_2, ck4_3, ck4_4, ck4_5, ck4_6,
                ck4_7, ck4_8, ck5_1, ck5_2, ck5_3, ck5_4, ck5_5, ck5_6, ck6_1, ck6_2, ck6_3, ck6_4,
                ck6_5, ck6_6, ck7_1, ck7_2, ck7_3, ck7_4, ck7_5, ck7_6, ck8_1, ck8_2, ck9_1, ck9_2,
                ck10, ck11, ck12, ck13, ck14)

        return true

    }


    fun GetPaper(paper: Paper_CANCER) {

        state = "getPaper"

        cannotEditQuestionnaire(cancer_root)

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        cancer_examination_save.visibility = View.GONE
        cancer_examination_cancel.visibility = View.GONE
        cancer_edit_submit.visibility = View.VISIBLE


        if(paper.ck1 == "1"){
            cancer_1_1.isChecked = true
            if(paper.ck1_1.isNullOrEmpty()){

            }else{
                cancer_editText1.setText(paper.ck1_1)
            }
        }else if(paper.ck1 == "2"){
            cancer_1_2.isChecked = true
        }


        if(paper.ck2 == "1"){
            cancer_2_1.isChecked = true

        }else if(paper.ck2 == "2"){
            cancer_2_2.isChecked = true

            if(paper.ck2_1.isNullOrEmpty()){

            }else{
                cancer_editText2.setText(paper.ck2_1)
            }
        }


        if(paper.ck3_1 == "1"){
            cancer_3_1_1.isChecked = true

        }else if(paper.ck3_1 == "3"){
            cancer_3_1_2.isChecked = true

        }else if(paper.ck3_1 == "2"){
            cancer_3_1_3.isChecked = true

            cancer_3_1_checkBox1.visibility = View.VISIBLE
            cancer_3_1_checkBox2.visibility = View.VISIBLE
            cancer_3_1_checkBox3.visibility = View.VISIBLE
            cancer_3_1_checkBox4.visibility = View.VISIBLE
            cancer_3_1_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_1_1 == "1"){
                cancer_3_1_checkBox1.isChecked = true
            }
            if(paper.ck3_1_2 == "2"){
                cancer_3_1_checkBox2.isChecked = true
            }
            if(paper.ck3_1_3 == "3"){
                cancer_3_1_checkBox3.isChecked = true
            }
            if(paper.ck3_1_4 == "4"){
                cancer_3_1_checkBox4.isChecked = true
            }
            if(paper.ck3_1_5 == "5"){
                cancer_3_1_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_2 == "1"){
            cancer_3_2_1.isChecked = true

        }else if(paper.ck3_2 == "3"){
            cancer_3_2_2.isChecked = true

        }else if(paper.ck3_2 == "2"){
            cancer_3_2_3.isChecked = true

            cancer_3_2_checkBox1.visibility = View.VISIBLE
            cancer_3_2_checkBox2.visibility = View.VISIBLE
            cancer_3_2_checkBox3.visibility = View.VISIBLE
            cancer_3_2_checkBox4.visibility = View.VISIBLE
            cancer_3_2_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_2_1 == "1"){
                cancer_3_2_checkBox1.isChecked = true
            }
            if(paper.ck3_2_2 == "2"){
                cancer_3_2_checkBox2.isChecked = true
            }
            if(paper.ck3_2_3 == "3"){
                cancer_3_2_checkBox3.isChecked = true
            }
            if(paper.ck3_2_4 == "4"){
                cancer_3_2_checkBox4.isChecked = true
            }
            if(paper.ck3_2_5 == "5"){
                cancer_3_2_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_3 == "1"){
            cancer_3_3_1.isChecked = true

        }else if(paper.ck3_3 == "3"){
            cancer_3_3_2.isChecked = true

        }else if(paper.ck3_3 == "2"){
            cancer_3_3_3.isChecked = true

            cancer_3_3_checkBox1.visibility = View.VISIBLE
            cancer_3_3_checkBox2.visibility = View.VISIBLE
            cancer_3_3_checkBox3.visibility = View.VISIBLE
            cancer_3_3_checkBox4.visibility = View.VISIBLE
            cancer_3_3_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_3_1 == "1"){
                cancer_3_3_checkBox1.isChecked = true
            }
            if(paper.ck3_3_2 == "2"){
                cancer_3_3_checkBox2.isChecked = true
            }
            if(paper.ck3_3_3 == "3"){
                cancer_3_3_checkBox3.isChecked = true
            }
            if(paper.ck3_3_4 == "4"){
                cancer_3_3_checkBox4.isChecked = true
            }
            if(paper.ck3_3_5 == "5"){
                cancer_3_3_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_4 == "1"){
            cancer_3_4_1.isChecked = true

        }else if(paper.ck3_4 == "3"){
            cancer_3_4_2.isChecked = true

        }else if(paper.ck3_4 == "2"){
            cancer_3_4_3.isChecked = true

            cancer_3_4_checkBox1.visibility = View.VISIBLE
            cancer_3_4_checkBox2.visibility = View.VISIBLE
            cancer_3_4_checkBox3.visibility = View.VISIBLE
            cancer_3_4_checkBox4.visibility = View.VISIBLE
            cancer_3_4_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_4_1 == "1"){
                cancer_3_4_checkBox1.isChecked = true
            }
            if(paper.ck3_4_2 == "2"){
                cancer_3_4_checkBox2.isChecked = true
            }
            if(paper.ck3_4_3 == "3"){
                cancer_3_4_checkBox3.isChecked = true
            }
            if(paper.ck3_4_4 == "4"){
                cancer_3_4_checkBox4.isChecked = true
            }
            if(paper.ck3_4_5 == "5"){
                cancer_3_4_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_5 == "1"){
            cancer_3_5_1.isChecked = true

        }else if(paper.ck3_5 == "3"){
            cancer_3_5_2.isChecked = true

        }else if(paper.ck3_5 == "2"){
            cancer_3_5_3.isChecked = true

            cancer_3_5_checkBox1.visibility = View.VISIBLE
            cancer_3_5_checkBox2.visibility = View.VISIBLE
            cancer_3_5_checkBox3.visibility = View.VISIBLE
            cancer_3_5_checkBox4.visibility = View.VISIBLE
            cancer_3_5_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_5_1 == "1"){
                cancer_3_5_checkBox1.isChecked = true
            }
            if(paper.ck3_5_2 == "2"){
                cancer_3_5_checkBox2.isChecked = true
            }
            if(paper.ck3_5_3 == "3"){
                cancer_3_5_checkBox3.isChecked = true
            }
            if(paper.ck3_5_4 == "4"){
                cancer_3_5_checkBox4.isChecked = true
            }
            if(paper.ck3_5_5 == "5"){
                cancer_3_5_checkBox5.isChecked = true
            }

        }


        if(paper.ck3_6 == "1"){
            cancer_3_6_1.isChecked = true

        }else if(paper.ck3_6 == "3"){
            cancer_3_6_2.isChecked = true

        }else if(paper.ck3_6 == "2"){
            cancer_3_6_3.isChecked = true

            cancer_3_6_checkBox1.visibility = View.VISIBLE
            cancer_3_6_checkBox2.visibility = View.VISIBLE
            cancer_3_6_checkBox3.visibility = View.VISIBLE
            cancer_3_6_checkBox4.visibility = View.VISIBLE
            cancer_3_6_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_6_1 == "1"){
                cancer_3_6_checkBox1.isChecked = true
            }
            if(paper.ck3_6_2 == "2"){
                cancer_3_6_checkBox2.isChecked = true
            }
            if(paper.ck3_6_3 == "3"){
                cancer_3_6_checkBox3.isChecked = true
            }
            if(paper.ck3_6_4 == "4"){
                cancer_3_6_checkBox4.isChecked = true
            }
            if(paper.ck3_6_5 == "5"){
                cancer_3_6_checkBox5.isChecked = true
            }

            if(paper.ck3_6_kita != ""){
                cancer_3_6_1_editText.setText(paper.ck3_6_kita)
            }

        }


        if(paper.ck4_1 == "1"){
            cancer_4_1_1.isChecked = true
        }else if(paper.ck4_1 == "2"){
            cancer_4_1_2.isChecked = true
        }else if(paper.ck4_1 == "3"){
            cancer_4_1_3.isChecked = true
        }else if(paper.ck4_1 == "4"){
            cancer_4_1_4.isChecked = true
        }


        if(paper.ck4_2 == "1"){
            cancer_4_2_1.isChecked = true
        }else if(paper.ck4_2 == "2"){
            cancer_4_2_2.isChecked = true
        }else if(paper.ck4_2 == "3"){
            cancer_4_2_3.isChecked = true
        }else if(paper.ck4_2 == "4"){
            cancer_4_2_4.isChecked = true
        }


        if(paper.ck4_3 == "1"){
            cancer_4_3_1.isChecked = true
        }else if(paper.ck4_3 == "2"){
            cancer_4_3_2.isChecked = true
        }else if(paper.ck4_3 == "3"){
            cancer_4_3_3.isChecked = true
        }else if(paper.ck4_3 == "4"){
            cancer_4_3_4.isChecked = true
        }


        if(paper.ck4_4 == "1"){
            cancer_4_4_1.isChecked = true
        }else if(paper.ck4_4 == "2"){
            cancer_4_4_2.isChecked = true
        }else if(paper.ck4_4 == "3"){
            cancer_4_4_3.isChecked = true
        }else if(paper.ck4_4 == "4"){
            cancer_4_4_4.isChecked = true
        }


        if(paper.ck4_5 == "1"){
            cancer_4_5_1.isChecked = true
        }else if(paper.ck4_5 == "2"){
            cancer_4_5_2.isChecked = true
        }else if(paper.ck4_5 == "3"){
            cancer_4_5_3.isChecked = true
        }else if(paper.ck4_5 == "4"){
            cancer_4_5_4.isChecked = true
        }


        if(paper.ck4_6 == "1"){
            cancer_4_6_1.isChecked = true
        }else if(paper.ck4_6 == "2"){
            cancer_4_6_2.isChecked = true
        }else if(paper.ck4_6 == "3"){
            cancer_4_6_3.isChecked = true
        }else if(paper.ck4_6 == "4"){
            cancer_4_6_4.isChecked = true
        }


        if(paper.ck4_7 == "1"){
            cancer_4_7_1.isChecked = true
        }else if(paper.ck4_7 == "2"){
            cancer_4_7_2.isChecked = true
        }else if(paper.ck4_7 == "3"){
            cancer_4_7_3.isChecked = true
        }else if(paper.ck4_7 == "4"){
            cancer_4_7_4.isChecked = true
        }


        if(paper.ck4_8 == "1"){
            cancer_4_8_1.isChecked = true
        }else if(paper.ck4_8 == "2"){
            cancer_4_8_2.isChecked = true
        }else if(paper.ck4_8 == "3"){
            cancer_4_8_3.isChecked = true
        }else if(paper.ck4_8 == "4"){
            cancer_4_8_4.isChecked = true
        }

        if(paper.ck5_1 == "2"){
            cancer_5_2_checkBox.isChecked = true
        }
        if(paper.ck5_2 == "2"){
            cancer_5_3_checkBox.isChecked = true
        }
        if(paper.ck5_3 == "2"){
            cancer_5_4_checkBox.isChecked = true
        }
        if(paper.ck5_4 == "2"){
            cancer_5_5_checkBox.isChecked = true
        }
        if(paper.ck5_5 == "2"){
            cancer_5_6_checkBox.isChecked = true
        }
        if(paper.ck5_6 == "2"){
            cancer_5_1_checkBox.isChecked = true
        }


        if(paper.ck6_1 == "2"){
            cancer_6_2_checkBox.isChecked = true
        }
        if(paper.ck6_2 == "2"){
            cancer_6_3_checkBox.isChecked = true
        }
        if(paper.ck6_3 == "2"){
            cancer_6_4_checkBox.isChecked = true
        }
        if(paper.ck6_4 == "2"){
            cancer_6_5_checkBox.isChecked = true
        }
        if(paper.ck6_5 == "2"){
            cancer_6_6_checkBox.isChecked = true
        }
        if(paper.ck6_6 == "2"){
            cancer_6_1_checkBox.isChecked = true
        }

        if(paper.ck7_1 == "2"){
            cancer_7_2_checkBox.isChecked = true
        }
        if(paper.ck7_2 == "2"){
            cancer_7_3_checkBox.isChecked = true
        }
        if(paper.ck7_3 == "2"){
            cancer_7_4_checkBox.isChecked = true
        }
        if(paper.ck7_4 == "2"){
            cancer_7_5_checkBox.isChecked = true
        }
        if(paper.ck7_5 == "2"){
            cancer_7_6_checkBox.isChecked = true
        }
        if(paper.ck7_6 == "2"){
            cancer_7_1_checkBox.isChecked = true
        }



        if(paper.ck8_1 == "1"){
            cancer_8_1.isChecked = true
            if(paper.ck8_2.isNullOrEmpty()){

            }else{
                cancer_editText3.setText(paper.ck8_2)
            }
        }else if(paper.ck8_1 == "2"){
            cancer_8_2.isChecked = true
        }

        if(paper.ck9_1 == "1"){
            cancer_9_1.isChecked = true
        }else if(paper.ck9_1 == "2"){
            cancer_9_2.isChecked = true
        }else if(paper.ck9_1 == "3"){
            cancer_9_3.isChecked = true
            if(paper.ck9_2.isNullOrEmpty()){

            }else{
                cancer_editText4.setText(paper.ck9_2)
            }
        }

        if(paper.ck10 == "1"){
            cancer_10_1.isChecked = true
        }else if(paper.ck10 == "2"){
            cancer_10_2.isChecked = true
        }else if(paper.ck10 == "3"){
            cancer_10_3.isChecked = true
        }else if(paper.ck10 == "4"){
            cancer_10_4.isChecked = true
        }else if(paper.ck10 == "5"){
            cancer_10_5.isChecked = true
        }


        if(paper.ck11 == "1"){
            cancer_11_1.isChecked = true
        }else if(paper.ck11 == "2"){
            cancer_11_2.isChecked = true
        }else if(paper.ck11 == "3"){
            cancer_11_3.isChecked = true
        }


        if(paper.ck12 == "1"){
            cancer_12_1.isChecked = true
        }else if(paper.ck12 == "2"){
            cancer_12_2.isChecked = true
        }else if(paper.ck12 == "3"){
            cancer_12_3.isChecked = true
        }else if(paper.ck12 == "4"){
            cancer_12_4.isChecked = true
        }

        if(paper.ck13 == "1"){
            cancer_13_1.isChecked = true
        }else if(paper.ck13 == "2"){
            cancer_13_2.isChecked = true
        }else if(paper.ck13 == "3"){
            cancer_13_3.isChecked = true
        }

        if(paper.ck14 == "1"){
            cancer_14_1.isChecked = true
        }else if(paper.ck14 == "2"){
            cancer_14_2.isChecked = true
        }else if(paper.ck14 == "3"){
            cancer_14_3.isChecked = true
        }

    }

    fun GetPaper(paper: ServerPaper_Cancer) {

        state = "getPaper"

        cannotEditQuestionnaire(cancer_root)

        name_edit.text = paper.c_name
        first_serial.text = paper.c_jumin.substring(0, 6)
        last_serial.text = paper.c_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        println(paper)

        cancer_examination_save.visibility = View.GONE
        cancer_examination_cancel.visibility = View.GONE
        cancer_edit_submit.visibility = View.VISIBLE


        if(paper.ck1 == "1"){
            cancer_1_1.isChecked = true
            if(paper.ck1_1.isNullOrEmpty()){

            }else{
                cancer_editText1.setText(paper.ck1_1)
            }
        }else if(paper.ck1 == "2"){
            cancer_1_2.isChecked = true
        }


        if(paper.ck2 == "1"){
            cancer_2_1.isChecked = true

        }else if(paper.ck2 == "2"){
            cancer_2_2.isChecked = true

            if(paper.ck2_1.isNullOrEmpty()){

            }else{
                cancer_editText2.setText(paper.ck2_1)
            }
        }


        if(paper.ck3_1 == "1"){
            cancer_3_1_1.isChecked = true

        }else if(paper.ck3_1 == "3"){
            cancer_3_1_2.isChecked = true

        }else if(paper.ck3_1 == "2"){
            cancer_3_1_3.isChecked = true

            cancer_3_1_checkBox1.visibility = View.VISIBLE
            cancer_3_1_checkBox2.visibility = View.VISIBLE
            cancer_3_1_checkBox3.visibility = View.VISIBLE
            cancer_3_1_checkBox4.visibility = View.VISIBLE
            cancer_3_1_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_1_1 == "1"){
                cancer_3_1_checkBox1.isChecked = true
            }
            if(paper.ck3_1_2 == "2"){
                cancer_3_1_checkBox2.isChecked = true
            }
            if(paper.ck3_1_3 == "3"){
                cancer_3_1_checkBox3.isChecked = true
            }
            if(paper.ck3_1_4 == "4"){
                cancer_3_1_checkBox4.isChecked = true
            }
            if(paper.ck3_1_5 == "5"){
                cancer_3_1_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_2 == "1"){
            cancer_3_2_1.isChecked = true

        }else if(paper.ck3_2 == "3"){
            cancer_3_2_2.isChecked = true

        }else if(paper.ck3_2 == "2"){
            cancer_3_2_3.isChecked = true

            cancer_3_2_checkBox1.visibility = View.VISIBLE
            cancer_3_2_checkBox2.visibility = View.VISIBLE
            cancer_3_2_checkBox3.visibility = View.VISIBLE
            cancer_3_2_checkBox4.visibility = View.VISIBLE
            cancer_3_2_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_2_1 == "1"){
                cancer_3_2_checkBox1.isChecked = true
            }
            if(paper.ck3_2_2 == "2"){
                cancer_3_2_checkBox2.isChecked = true
            }
            if(paper.ck3_2_3 == "3"){
                cancer_3_2_checkBox3.isChecked = true
            }
            if(paper.ck3_2_4 == "4"){
                cancer_3_2_checkBox4.isChecked = true
            }
            if(paper.ck3_2_5 == "5"){
                cancer_3_2_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_3 == "1"){
            cancer_3_3_1.isChecked = true

        }else if(paper.ck3_3 == "3"){
            cancer_3_3_2.isChecked = true

        }else if(paper.ck3_3 == "2"){
            cancer_3_3_3.isChecked = true

            cancer_3_3_checkBox1.visibility = View.VISIBLE
            cancer_3_3_checkBox2.visibility = View.VISIBLE
            cancer_3_3_checkBox3.visibility = View.VISIBLE
            cancer_3_3_checkBox4.visibility = View.VISIBLE
            cancer_3_3_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_3_1 == "1"){
                cancer_3_3_checkBox1.isChecked = true
            }
            if(paper.ck3_3_2 == "2"){
                cancer_3_3_checkBox2.isChecked = true
            }
            if(paper.ck3_3_3 == "3"){
                cancer_3_3_checkBox3.isChecked = true
            }
            if(paper.ck3_3_4 == "4"){
                cancer_3_3_checkBox4.isChecked = true
            }
            if(paper.ck3_3_5 == "5"){
                cancer_3_3_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_4 == "1"){
            cancer_3_4_1.isChecked = true

        }else if(paper.ck3_4 == "3"){
            cancer_3_4_2.isChecked = true

        }else if(paper.ck3_4 == "2"){
            cancer_3_4_3.isChecked = true

            cancer_3_4_checkBox1.visibility = View.VISIBLE
            cancer_3_4_checkBox2.visibility = View.VISIBLE
            cancer_3_4_checkBox3.visibility = View.VISIBLE
            cancer_3_4_checkBox4.visibility = View.VISIBLE
            cancer_3_4_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_4_1 == "1"){
                cancer_3_4_checkBox1.isChecked = true
            }
            if(paper.ck3_4_2 == "2"){
                cancer_3_4_checkBox2.isChecked = true
            }
            if(paper.ck3_4_3 == "3"){
                cancer_3_4_checkBox3.isChecked = true
            }
            if(paper.ck3_4_4 == "4"){
                cancer_3_4_checkBox4.isChecked = true
            }
            if(paper.ck3_4_5 == "5"){
                cancer_3_4_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_5 == "1"){
            cancer_3_5_1.isChecked = true

        }else if(paper.ck3_5 == "3"){
            cancer_3_5_2.isChecked = true

        }else if(paper.ck3_5 == "2"){
            cancer_3_5_3.isChecked = true

            cancer_3_5_checkBox1.visibility = View.VISIBLE
            cancer_3_5_checkBox2.visibility = View.VISIBLE
            cancer_3_5_checkBox3.visibility = View.VISIBLE
            cancer_3_5_checkBox4.visibility = View.VISIBLE
            cancer_3_5_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_5_1 == "1"){
                cancer_3_5_checkBox1.isChecked = true
            }
            if(paper.ck3_5_2 == "2"){
                cancer_3_5_checkBox2.isChecked = true
            }
            if(paper.ck3_5_3 == "3"){
                cancer_3_5_checkBox3.isChecked = true
            }
            if(paper.ck3_5_4 == "4"){
                cancer_3_5_checkBox4.isChecked = true
            }
            if(paper.ck3_5_5 == "5"){
                cancer_3_5_checkBox5.isChecked = true
            }

        }


        if(paper.ck3_6 == "1"){
            cancer_3_6_1.isChecked = true

        }else if(paper.ck3_6 == "3"){
            cancer_3_6_2.isChecked = true

        }else if(paper.ck3_6 == "2"){
            cancer_3_6_3.isChecked = true

            cancer_3_6_checkBox1.visibility = View.VISIBLE
            cancer_3_6_checkBox2.visibility = View.VISIBLE
            cancer_3_6_checkBox3.visibility = View.VISIBLE
            cancer_3_6_checkBox4.visibility = View.VISIBLE
            cancer_3_6_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_6_1 == "1"){
                cancer_3_6_checkBox1.isChecked = true
            }
            if(paper.ck3_6_2 == "2"){
                cancer_3_6_checkBox2.isChecked = true
            }
            if(paper.ck3_6_3 == "3"){
                cancer_3_6_checkBox3.isChecked = true
            }
            if(paper.ck3_6_4 == "4"){
                cancer_3_6_checkBox4.isChecked = true
            }
            if(paper.ck3_6_5 == "5"){
                cancer_3_6_checkBox5.isChecked = true
            }

            if(paper.ck3_6_kita != ""){
                cancer_3_6_1_editText.setText(paper.ck3_6_kita)
            }

        }


        if(paper.ck4_1 == "1"){
            cancer_4_1_1.isChecked = true
        }else if(paper.ck4_1 == "2"){
            cancer_4_1_2.isChecked = true
        }else if(paper.ck4_1 == "3"){
            cancer_4_1_3.isChecked = true
        }else if(paper.ck4_1 == "4"){
            cancer_4_1_4.isChecked = true
        }


        if(paper.ck4_2 == "1"){
            cancer_4_2_1.isChecked = true
        }else if(paper.ck4_2 == "2"){
            cancer_4_2_2.isChecked = true
        }else if(paper.ck4_2 == "3"){
            cancer_4_2_3.isChecked = true
        }else if(paper.ck4_2 == "4"){
            cancer_4_2_4.isChecked = true
        }


        if(paper.ck4_3 == "1"){
            cancer_4_3_1.isChecked = true
        }else if(paper.ck4_3 == "2"){
            cancer_4_3_2.isChecked = true
        }else if(paper.ck4_3 == "3"){
            cancer_4_3_3.isChecked = true
        }else if(paper.ck4_3 == "4"){
            cancer_4_3_4.isChecked = true
        }


        if(paper.ck4_4 == "1"){
            cancer_4_4_1.isChecked = true
        }else if(paper.ck4_4 == "2"){
            cancer_4_4_2.isChecked = true
        }else if(paper.ck4_4 == "3"){
            cancer_4_4_3.isChecked = true
        }else if(paper.ck4_4 == "4"){
            cancer_4_4_4.isChecked = true
        }


        if(paper.ck4_5 == "1"){
            cancer_4_5_1.isChecked = true
        }else if(paper.ck4_5 == "2"){
            cancer_4_5_2.isChecked = true
        }else if(paper.ck4_5 == "3"){
            cancer_4_5_3.isChecked = true
        }else if(paper.ck4_5 == "4"){
            cancer_4_5_4.isChecked = true
        }


        if(paper.ck4_6 == "1"){
            cancer_4_6_1.isChecked = true
        }else if(paper.ck4_6 == "2"){
            cancer_4_6_2.isChecked = true
        }else if(paper.ck4_6 == "3"){
            cancer_4_6_3.isChecked = true
        }else if(paper.ck4_6 == "4"){
            cancer_4_6_4.isChecked = true
        }


        if(paper.ck4_7 == "1"){
            cancer_4_7_1.isChecked = true
        }else if(paper.ck4_7 == "2"){
            cancer_4_7_2.isChecked = true
        }else if(paper.ck4_7 == "3"){
            cancer_4_7_3.isChecked = true
        }else if(paper.ck4_7 == "4"){
            cancer_4_7_4.isChecked = true
        }


        if(paper.ck4_8 == "1"){
            cancer_4_8_1.isChecked = true
        }else if(paper.ck4_8 == "2"){
            cancer_4_8_2.isChecked = true
        }else if(paper.ck4_8 == "3"){
            cancer_4_8_3.isChecked = true
        }else if(paper.ck4_8 == "4"){
            cancer_4_8_4.isChecked = true
        }

        if(paper.ck5_1 == "2"){
            cancer_5_2_checkBox.isChecked = true
        }
        if(paper.ck5_2 == "2"){
            cancer_5_3_checkBox.isChecked = true
        }
        if(paper.ck5_3 == "2"){
            cancer_5_4_checkBox.isChecked = true
        }
        if(paper.ck5_4 == "2"){
            cancer_5_5_checkBox.isChecked = true
        }
        if(paper.ck5_5 == "2"){
            cancer_5_6_checkBox.isChecked = true
        }
        if(paper.ck5_6 == "2"){
            cancer_5_1_checkBox.isChecked = true
        }


        if(paper.ck6_1 == "2"){
            cancer_6_2_checkBox.isChecked = true
        }
        if(paper.ck6_2 == "2"){
            cancer_6_3_checkBox.isChecked = true
        }
        if(paper.ck6_3 == "2"){
            cancer_6_4_checkBox.isChecked = true
        }
        if(paper.ck6_4 == "2"){
            cancer_6_5_checkBox.isChecked = true
        }
        if(paper.ck6_5 == "2"){
            cancer_6_6_checkBox.isChecked = true
        }
        if(paper.ck6_6 == "2"){
            cancer_6_1_checkBox.isChecked = true
        }

        if(paper.ck7_1 == "2"){
            cancer_7_2_checkBox.isChecked = true
        }
        if(paper.ck7_2 == "2"){
            cancer_7_3_checkBox.isChecked = true
        }
        if(paper.ck7_3 == "2"){
            cancer_7_4_checkBox.isChecked = true
        }
        if(paper.ck7_4 == "2"){
            cancer_7_5_checkBox.isChecked = true
        }
        if(paper.ck7_5 == "2"){
            cancer_7_6_checkBox.isChecked = true
        }
        if(paper.ck7_6 == "2"){
            cancer_7_1_checkBox.isChecked = true
        }



        if(paper.ck8_1 == "1"){
            cancer_8_1.isChecked = true
            if(paper.ck8_2.isNullOrEmpty()){

            }else{
                cancer_editText3.setText(paper.ck8_2)
            }
        }else if(paper.ck8_1 == "2"){
            cancer_8_2.isChecked = true
        }

        if(paper.ck9_1 == "1"){
            cancer_9_1.isChecked = true
        }else if(paper.ck9_1 == "2"){
            cancer_9_2.isChecked = true
        }else if(paper.ck9_1 == "3"){
            cancer_9_3.isChecked = true
            if(paper.ck9_2.isNullOrEmpty()){

            }else{
                cancer_editText4.setText(paper.ck9_2)
            }
        }

        if(paper.ck10 == "1"){
            cancer_10_1.isChecked = true
        }else if(paper.ck10 == "2"){
            cancer_10_2.isChecked = true
        }else if(paper.ck10 == "3"){
            cancer_10_3.isChecked = true
        }else if(paper.ck10 == "4"){
            cancer_10_4.isChecked = true
        }else if(paper.ck10 == "5"){
            cancer_10_5.isChecked = true
        }


        if(paper.ck11 == "1"){
            cancer_11_1.isChecked = true
        }else if(paper.ck11 == "2"){
            cancer_11_2.isChecked = true
        }else if(paper.ck11 == "3"){
            cancer_11_3.isChecked = true
        }


        if(paper.ck12 == "1"){
            cancer_12_1.isChecked = true
        }else if(paper.ck12 == "2"){
            cancer_12_2.isChecked = true
        }else if(paper.ck12 == "3"){
            cancer_12_3.isChecked = true
        }else if(paper.ck12 == "4"){
            cancer_12_4.isChecked = true
        }

        if(paper.ck13 == "1"){
            cancer_13_1.isChecked = true
        }else if(paper.ck13 == "2"){
            cancer_13_2.isChecked = true
        }else if(paper.ck13 == "3"){
            cancer_13_3.isChecked = true
        }

        if(paper.ck14 == "1"){
            cancer_14_1.isChecked = true
        }else if(paper.ck14 == "2"){
            cancer_14_2.isChecked = true
        }else if(paper.ck14 == "3"){
            cancer_14_3.isChecked = true
        }

    }

}