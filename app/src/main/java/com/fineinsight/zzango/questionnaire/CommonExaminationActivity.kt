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
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.ChartDivision
import com.fineinsight.zzango.questionnaire.DataClass.PaperNameInfo
import com.fineinsight.zzango.questionnaire.DataClass.SavePaper
import com.fineinsight.zzango.questionnaire.DataClass.ServerPaper_Common
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import com.fineinsight.zzango.questionnaire.LocalList.Paper_COMMON
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import kotlinx.android.synthetic.main.activity_common_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class CommonExaminationActivity : RootActivity() {

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_exam)

//        controlProgress(this, questionnaire_progress_wrapper, progress_constraintLayout, questionnaire_progress, progress_guideline, progress_guideline2, progress_guideline3, progress_guideline4, progress_guideline5, progress_guideline6, progress_guideline7, progress_guideline8)

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        common_4_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout4_wrapper)

        }

        common_4_1_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout4_wrapper_inside_constraintLayout)

        }

        common_4_1_false.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout4_wrapper_inside_constraintLayout2)

        }

        common_5_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout5_wrapper)

        }

        common_5_1_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout5_wrapper_inside_constraintLayout)

        }

        common_5_1_false.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout5_wrapper_inside_constraintLayout2)

        }

        common_6_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout6_wrapper)

        }

        common_7_1.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_1_wrapper)

        }

        common_7_2.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_2_wrapper)

        }

        common_7_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_3_wrapper)

        }

        common_7_4.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition1(isChecked, common_subtitle_1)
            checkCondition1(isChecked, textView27)
            checkCondition1(isChecked, table_view_1)
            checkCondition1(isChecked, textView28)
            checkCondition1(isChecked, table_view_2)

        }

        checkBox1.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_1_1_radio_wrapper)

        }

        checkBox2.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_1_2_radio_wrapper)

        }

        checkBox3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_1_3_radio_wrapper)

        }

        checkBox4.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_1_4_radio_wrapper)

        }

        checkBox5.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_1_5_radio_wrapper)

        }

        checkBox6.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_2_1_radio_wrapper)

        }

        checkBox7.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_2_2_radio_wrapper)

        }

        checkBox8.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_2_3_radio_wrapper)

        }

        checkBox9.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_2_4_radio_wrapper)

        }

        checkBox10.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, common_7_2_5_radio_wrapper)

        }

        common_examination_save.setOnClickListener{

            if(check()){

                Drinking()

//                login_appbar_loading_progress.visibility = View.VISIBLE
//                login_appbar_loading_progress_bg.visibility = View.VISIBLE


                if(MainActivity.chart.isEmpty()){
                    if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local")){
                        ChartDivision.ChartDivision.local_each_insert(this, 0)
                    }else{
                        ChartDivision.ChartDivision.server_insert(this)
                    }
                }else{
                    ChartDivision.ChartDivision.chart_array_insert(this, 0)
                }

            }

        }

        common_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        common_edit_submit.setOnClickListener {

//            Drinking()

            finish()

        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_COMMON) {

                var paper = intent.getSerializableExtra("paper") as Paper_COMMON

                GetPaper(paper)

                try {
//                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)
//
//                    Signature.setImageBitmap(bmp)

                } catch (e: Exception) {
                    println(e.message)
                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Common

                GetPaper(paper)

            }

        }else{
            name_edit.text = MainActivity.login_user_name
            first_serial.text = MainActivity.user_first_serial
            last_serial.text = MainActivity.user_last_serial

            if(MainActivity.chart.isEmpty()){
                common_examination_save.text = "저장"
            }else{
                if(ChartDivision.ChartDivision.next_or_save(0)){
                    common_examination_save.text = "다음"
                }else{
                    common_examination_save.text = "저장"
                }
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

//    fun common_exam_local_insert(){
//
//        if(MainActivity.chart.isEmpty()){
//            LocalDBhelper(this).onCreate(sql_db)
//            //LocalDBhelper(this).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!, "SET1")
//            LocalDBhelper(this).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)
//
//            LocalDBhelper(this).commonExaminationDB(sql_db)
//            LocalDBhelper(this).commonSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COMMON!! )
//
//            saveCompleteAlert()
//        }else if(MainActivity.chart[1].isNullOrEmpty()){
//
//            LocalDBhelper(this).onCreate(sql_db)
//            //LocalDBhelper(this).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!, MainActivity.chart)
//            LocalDBhelper(this).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)
//
//            LocalDBhelper(this).commonExaminationDB(sql_db)
//            LocalDBhelper(this).commonSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COMMON!! )
//
//            saveCompleteAlert()
//        }else{
//
//            when(MainActivity.chart[1]){
//                "mental" -> {
//                    startActivity(Intent(this@CommonExaminationActivity, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                }
//                "cognitive" -> {
//                    startActivity(Intent(this@CommonExaminationActivity, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                }
//            }
//
//        }
//
//
//    }
//
//    fun common_exam_server_insert(){
//
//        if(MainActivity.chart[1].isNullOrEmpty() || MainActivity.chart.isEmpty()){
//
//            if(wfm!!.isWifiEnabled || (connectivityManager!!.activeNetwork != null && connectivityManager!!.getNetworkCapabilities(connectivityManager!!.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))) {
//
//                this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//
//
//                var SaveArr = ArrayList<Any>()
//                var OneArr = ArrayList<Any>()
//                var InfoArr = ArrayList<String>()
//
//
//                InfoArr.add("SET1")
//                InfoArr.add(MainActivity.hospital)
//
//
//                OneArr.add(PaperArray.PaperList.Arr_COMMON!!)
//                SaveArr.add(InfoArr)
//                SaveArr.add(OneArr)
//
//
//                OracleUtill().save_papers().savePapersServer(SaveArr).enqueue(object : Callback<String> {
//                    override fun onResponse(call: Call<String>, response: Response<String>) {
//
//                        if (response.isSuccessful) {
//
//                            if (!response.body()!!.equals("S")) {
//
//                                login_appbar_loading_progress.visibility = View.GONE
//                                login_appbar_loading_progress_bg.visibility = View.GONE
//                                this@CommonExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//                                Toast.makeText(this@CommonExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
//
//                            } else {
//
//                                saveCompleteAlert()
//
//                            }
//
//                        }
//
//                    }
//
//                    override fun onFailure(call: Call<String>, t: Throwable) {
//
//                        login_appbar_loading_progress.visibility = View.GONE
//                        login_appbar_loading_progress_bg.visibility = View.GONE
//                        this@CommonExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//                        Toast.makeText(this@CommonExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
//                        println(t.toString())
//                    }
//
//                })
//
//            }else{
//
//                login_appbar_loading_progress.visibility = View.GONE
//                login_appbar_loading_progress_bg.visibility = View.GONE
//                wifiCheck()
//
//            }
//
//        }else{
//
//            when(MainActivity.chart[1]){
//                "mental" -> {
//                    startActivity(Intent(this@CommonExaminationActivity, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                }
//                "cognitive" -> {
//                    startActivity(Intent(this@CommonExaminationActivity, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//                }
//            }
//        }
//
//    }

    fun saveCompleteAlert() {

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@CommonExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        popup = false

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_complete_alert, null)

        dialog.setCancelable(false)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.save_complete_alert_text.text = "저장이 완료 되었습니다"

        if (!popup) {

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

        dialog_view.return_alert.setOnClickListener {

            MainActivity.login_user_name = ""
            MainActivity.user_first_serial = ""
            MainActivity.user_last_serial = ""

            MainActivity.userLogin!!.text = "사용자 등록하기"
            MainActivity.userImage!!.setImageResource(R.drawable.regi)

            startActivity(Intent(this@CommonExaminationActivity, MainActivity::class.java).putExtra("from", "common").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            dialog.dismiss()

        }

    }

    @SuppressLint("NewApi")
    fun check() : Boolean {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.COMMON.EN_NM
        var mj1_1_1 = ""
        var mj1_1_2 = ""
        var mj1_2_1 = ""
        var mj1_2_2 = ""
        var mj1_3_1 = ""
        var mj1_3_2 = ""
        var mj1_4_1 = ""
        var mj1_4_2 = ""
        var mj1_5_1 = ""
        var mj1_5_2 = ""
        var mj1_6_1 = ""
        var mj1_6_2 = ""
        var mj1_7_1 = ""
        var mj1_7_2 = ""
        var mj2_1 = ""
        var mj2_2 = ""
        var mj2_3 = ""
        var mj2_4 = ""
        var mj2_5 = ""
        var mj3 = ""
        var mj4 = ""
        var mj4_1_1 = ""
        var mj4_1_2 = ""
        var mj4_2_1 = ""
        var mj4_2_2 = ""
        var mj4_2_3 = ""
        var mj5 = ""
        var mj5_1_1 = ""
        var mj5_1_2 = ""
        var mj5_2_1 = ""
        var mj5_2_2 = ""
        var mj5_2_3 = ""
        var mj6 = ""
        var mj6_1 = ""
        var mj71 = ""
        var mj72 = ""
        var mj73 = ""
        var mj74 = ""
        var mj7_1_11 = ""
        var mj7_1_12 = ""
        var mj7_1_13 = ""
        var mj7_1_14 = ""
        var mj7_1_21 = ""
        var mj7_1_22 = ""
        var mj7_1_23 = ""
        var mj7_1_24 = ""
        var mj7_1_31 = ""
        var mj7_1_32 = ""
        var mj7_1_33 = ""
        var mj7_1_34 = ""
        var mj7_1_41 = ""
        var mj7_1_42 = ""
        var mj7_1_43 = ""
        var mj7_1_44 = ""
        var mj7_1_51 = ""
        var mj7_1_52 = ""
        var mj7_1_53 = ""
        var mj7_1_54 = ""
        var mj7_1_etc = ""
        var mj7_2_11 = ""
        var mj7_2_12 = ""
        var mj7_2_13 = ""
        var mj7_2_14 = ""
        var mj7_2_21 = ""
        var mj7_2_22 = ""
        var mj7_2_23 = ""
        var mj7_2_24 = ""
        var mj7_2_31 = ""
        var mj7_2_32 = ""
        var mj7_2_33 = ""
        var mj7_2_34 = ""
        var mj7_2_41 = ""
        var mj7_2_42 = ""
        var mj7_2_43 = ""
        var mj7_2_44 = ""
        var mj7_2_51 = ""
        var mj7_2_52 = ""
        var mj7_2_53 = ""
        var mj7_2_54 = ""
        var mj7_2_etc = ""
        var mj8_1 = ""
        var mj8_2_1 = ""
        var mj8_2_2 = ""
        var mj9_1 = ""
        var mj9_2_1 = ""
        var mj9_2_2 = ""
        var mj10 = ""

        if(!name_edit.text.isNullOrEmpty()){
            name = name_edit.text.toString()
        }else{
            Toast.makeText(this, "성명 또는 주민번호란을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(!first_serial.text.isNullOrEmpty()){
            first_serial_text = first_serial.text.toString()
        }else{
            Toast.makeText(this, "성명 또는 주민번호란을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(!last_serial.text.isNullOrEmpty()){
            last_serial_text = last_serial.text.toString()
        }else{
            Toast.makeText(this, "성명 또는 주민번호란을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(diagnosis_medication_stroke_examination_check.isChecked){
            mj1_1_1 = "1"
        }else{
            mj1_1_1 = "2"
        }

        if(diagnosis_medication_stroke_medication_check.isChecked){
            mj1_1_2 = "1"
        }else{
            mj1_1_2 = "2"
        }

        if(diagnosis_medication_myocardial_examination_check.isChecked){
            mj1_2_1 = "1"
        }else{
            mj1_2_1 = "2"
        }

        if(diagnosis_medication_myocardial_medication_check.isChecked){
            mj1_2_2 = "1"
        }else{
            mj1_2_2 = "2"
        }

        if(diagnosis_medication_high_blood_pressure_examination_check.isChecked){
            mj1_3_1 = "1"
        }else{
            mj1_3_1 = "2"
        }

        if(diagnosis_medication_high_blood_pressure_medication_check.isChecked){
            mj1_3_2 = "1"
        }else{
            mj1_3_2 = "2"
        }

        if(diagnosis_medication_diabetes_examination_check.isChecked){
            mj1_4_1 = "1"
        }else{
            mj1_4_1 = "2"
        }

        if(diagnosis_medication_diabetes_medication_check.isChecked){
            mj1_4_2 = "1"
        }else{
            mj1_4_2 = "2"
        }

        if(diagnosis_medication_dyslipidemia_examination_check.isChecked){
            mj1_5_1 = "1"
        }else{
            mj1_5_1 = "2"
        }

        if(diagnosis_medication_dyslipidemia_medication_check.isChecked){
            mj1_5_2 = "1"
        }else{
            mj1_5_2 = "2"
        }

        if(diagnosis_medication_tuberculosis_examination_check.isChecked){
            mj1_6_1 = "1"
        }else{
            mj1_6_1 = "2"
        }

        if(diagnosis_medication_tuberculosis_medication_check.isChecked){
            mj1_6_2 = "1"
        }else{
            mj1_6_2 = "2"
        }

        if(diagnosis_medication_etc_examination_check.isChecked){
            mj1_7_1 = "1"
        }else{
            mj1_7_1 = "2"
        }

        if(diagnosis_medication_etc_medication_check.isChecked){
            mj1_7_2 = "1"
        }else{
            mj1_7_2 = "2"
        }

        if(family_history_disease_stroke_examination_check.isChecked){
            mj2_1 = "1"
        }else{
            mj2_1 = "2"
        }

        if(family_history_disease_myocardial_examination_check.isChecked){
            mj2_2 = "1"
        }else{
            mj2_2 = "2"
        }


        if(family_history_disease_high_blood_pressure_examination_check.isChecked){
            mj2_3 = "1"
        }else{
            mj2_3 = "2"
        }


        if(family_history_disease_diabetes_examination_check.isChecked){
            mj2_4 = "1"
        }else{
            mj2_4 = "2"
        }

        if(family_history_etc_examination_check.isChecked){
            mj2_5 = "1"
        }else{
            mj2_5 = "2"
        }

        if(common_3_true.isChecked){
            mj3 = "1"
        }else if(common_3_false.isChecked){
            mj3 = "2"
        }else if(common_3_do_not_know.isChecked){
            mj3 = "3"
        }else{
            Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(common_4_true.isChecked){
            mj4 = "2"
            if(common_4_1_true.isChecked){
                if(!editText_4_1_1.text.isNullOrEmpty() && !editText_4_1_2.text.isNullOrEmpty()){
                    mj4_1_1 = editText_4_1_1.text.toString()
                    mj4_1_2 = editText_4_1_2.text.toString()
                    AdditionalArr.over.isSmoking = true
                }else{
                    Toast.makeText(this, "4-1번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                    return false
                }
            }else if(common_4_1_false.isChecked){
                if(!editText_4_1_3.text.isNullOrEmpty() && !editText_4_1_4.text.isNullOrEmpty() && !editText_4_1_5.text.isNullOrEmpty()){
                    mj4_2_1 = editText_4_1_3.text.toString()
                    mj4_2_2 = editText_4_1_4.text.toString()
                    mj4_2_3 = editText_4_1_5.text.toString()
                    AdditionalArr.over.isSmoking = false
                }else{
                    Toast.makeText(this, "4-1번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                    return false
                }
            }else{
                Toast.makeText(this, "4-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

        }else if(common_4_false.isChecked){

            mj4 = "1"
            AdditionalArr.over.isSmoking = false

        }else{
            Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(common_5_true.isChecked){
            mj5 = "2"

            if(common_5_1_true.isChecked){
                if(!editText_5_1_1.text.isNullOrEmpty() && !editText_5_1_2.text.isNullOrEmpty()){
                    mj5_1_1 = editText_5_1_1.text.toString()
                    mj5_1_2 = editText_5_1_2.text.toString()
                }else{
                    Toast.makeText(this, "5-1번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                    return false
                }
            }else if(common_5_1_false.isChecked){
                if(!editText_5_1_3.text.isNullOrEmpty() && !editText_5_1_4.text.isNullOrEmpty() && !editText_5_1_5.text.isNullOrEmpty()){
                    mj5_2_1 = editText_5_1_3.text.toString()
                    mj5_2_2 = editText_5_1_4.text.toString()
                    mj5_2_3 = editText_5_1_5.text.toString()
                }else{
                    Toast.makeText(this, "5-1번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                    return false
                }
            }else{
                Toast.makeText(this, "5-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

        }else if(common_5_false.isChecked){
            mj5 = "1"

        }else{
            Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(common_6_true.isChecked){
            mj6 = "1"

            if(common_6_1_1.isChecked){
                mj6_1 = "1"
            }else if(common_6_1_2.isChecked){
                mj6_1 = "2"
            }else if(common_6_1_3.isChecked){
                mj6_1 = "3"
            }else if(common_6_1_4.isChecked){
                mj6_1 = "4"
            }else if(common_6_1_5.isChecked){
                mj6_1 = "5"
            }else{
                Toast.makeText(this, "6-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

        }else if(common_6_false.isChecked){
            mj6 = "2"
        }else{
            Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(common_7_1.isChecked){
            mj71 = common_7_1_editText.text.toString()
            if(common_7_1_editText.text.isNullOrEmpty()){
                Toast.makeText(this, "7번 문항 첫번째 항목을 작성해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else if(common_7_2.isChecked){
            mj72 = common_7_2_editText.text.toString()
            if(common_7_2_editText.text.isNullOrEmpty()){
                Toast.makeText(this, "7번 문항 두번째 항목을 작성해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else if(common_7_3.isChecked){
            mj73 = common_7_3_editText.text.toString()
            if(common_7_3_editText.text.isNullOrEmpty()){
                Toast.makeText(this, "7번 문항 세번째 항목을 작성해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else if(common_7_4.isChecked){
            mj74 = "1"
        }else{
            Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(checkBox1.isChecked){
            if(common_7_1_1_1.isChecked){
                mj7_1_11 = table_edit_1.text.toString()
            }else if(common_7_1_1_2.isChecked){
                mj7_1_12 = table_edit_1.text.toString()
            }else if(common_7_1_1_3.isChecked){
                mj7_1_13 = table_edit_1.text.toString()
            }else if(common_7_1_1_4.isChecked){
                mj7_1_14 = table_edit_1.text.toString()
            }else{
                Toast.makeText(this, "7-1번 체크 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(checkBox2.isChecked){
            if(common_7_1_2_1.isChecked){
                mj7_1_21 = table_edit_2.text.toString()
            }else if(common_7_1_2_2.isChecked){
                mj7_1_22 = table_edit_2.text.toString()
            }else if(common_7_1_2_3.isChecked){
                mj7_1_23 = table_edit_2.text.toString()
            }else if(common_7_1_2_4.isChecked){
                mj7_1_24 = table_edit_2.text.toString()
            }else{
                Toast.makeText(this, "7-1번 체크 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(checkBox3.isChecked){
            if(common_7_1_3_1.isChecked){
                mj7_1_31 = table_edit_3.text.toString()
            }else if(common_7_1_3_2.isChecked){
                mj7_1_32 = table_edit_3.text.toString()
            }else if(common_7_1_3_3.isChecked){
                mj7_1_33 = table_edit_3.text.toString()
            }else if(common_7_1_3_4.isChecked){
                mj7_1_34 = table_edit_3.text.toString()
            }else{
                Toast.makeText(this, "7-1번 체크 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(checkBox4.isChecked){
            if(common_7_1_4_1.isChecked){
                mj7_1_41 = table_edit_4.text.toString()
            }else if(common_7_1_4_2.isChecked){
                mj7_1_42 = table_edit_4.text.toString()
            }else if(common_7_1_4_3.isChecked){
                mj7_1_43 = table_edit_4.text.toString()
            }else if(common_7_1_4_4.isChecked){
                mj7_1_44 = table_edit_4.text.toString()
            }else{
                Toast.makeText(this, "7-1번 체크 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(checkBox5.isChecked){
            if(common_7_1_5_1.isChecked){
                mj7_1_51 = table_edit_5.text.toString()
            }else if(common_7_1_5_2.isChecked){
                mj7_1_52 = table_edit_5.text.toString()
            }else if(common_7_1_5_3.isChecked){
                mj7_1_53 = table_edit_5.text.toString()
            }else if(common_7_1_5_4.isChecked){
                mj7_1_54 = table_edit_5.text.toString()
            }else{
                Toast.makeText(this, "7-1번 체크 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }


        if(checkBox6.isChecked){
            if(common_7_2_1_1.isChecked){
                mj7_2_11 = table_edit_6.text.toString()
            }else if(common_7_2_1_2.isChecked){
                mj7_2_12 = table_edit_6.text.toString()
            }else if(common_7_2_1_3.isChecked){
                mj7_2_13 = table_edit_6.text.toString()
            }else if(common_7_2_1_4.isChecked){
                mj7_2_14 = table_edit_6.text.toString()
            }else{
                Toast.makeText(this, "7-2번 체크 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(checkBox7.isChecked){
            if(common_7_2_2_1.isChecked){
                mj7_2_21 = table_edit_7.text.toString()
            }else if(common_7_2_2_2.isChecked){
                mj7_2_22 = table_edit_7.text.toString()
            }else if(common_7_2_2_3.isChecked){
                mj7_2_23 = table_edit_7.text.toString()
            }else if(common_7_2_2_4.isChecked){
                mj7_2_24 = table_edit_7.text.toString()
            }else{
                Toast.makeText(this, "7-2번 체크 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(checkBox8.isChecked){
            if(common_7_2_3_1.isChecked){
                mj7_2_31 = table_edit_8.text.toString()
            }else if(common_7_2_3_2.isChecked){
                mj7_2_32 = table_edit_8.text.toString()
            }else if(common_7_2_3_3.isChecked){
                mj7_2_33 = table_edit_8.text.toString()
            }else if(common_7_2_3_4.isChecked){
                mj7_2_34 = table_edit_8.text.toString()
            }else{
                Toast.makeText(this, "7-2번 체크 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(checkBox9.isChecked){
            if(common_7_2_4_1.isChecked){
                mj7_2_41 = table_edit_9.text.toString()
            }else if(common_7_2_4_2.isChecked){
                mj7_2_42 = table_edit_9.text.toString()
            }else if(common_7_2_4_3.isChecked){
                mj7_2_43 = table_edit_9.text.toString()
            }else if(common_7_2_4_4.isChecked){
                mj7_2_44 = table_edit_9.text.toString()
            }else{
                Toast.makeText(this, "7-2번 체크 된 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(checkBox10.isChecked){
            if(common_7_2_5_1.isChecked){
                mj7_2_51 = table_edit_10.text.toString()
            }else if(common_7_2_5_2.isChecked){
                mj7_2_52 = table_edit_10.text.toString()
            }else if(common_7_2_5_3.isChecked){
                mj7_2_53 = table_edit_10.text.toString()
            }else if(common_7_2_5_4.isChecked){
                mj7_2_54 = table_edit_10.text.toString()
            }else{
                Toast.makeText(this, "7-2번 체크 된 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(common_8_1_0.isChecked) {
            mj8_1 = "0"
        }else if(common_8_1_1.isChecked){
            mj8_1 = "1"
        }else if(common_8_1_2.isChecked){
            mj8_1 = "2"
        }else if(common_8_1_3.isChecked){
            mj8_1 = "3"
        }else if(common_8_1_4.isChecked){
            mj8_1 = "4"
        }else if(common_8_1_5.isChecked){
            mj8_1 = "5"
        }else if(common_8_1_6.isChecked){
            mj8_1 = "6"
        }else if(common_8_1_7.isChecked){
            mj8_1 = "7"
        }else{
            Toast.makeText(this, "8-1번 문항을 체크해주세요.", Toast.LENGTH_LONG).show()
            return false
        }

        if(!editText_time_1.text.isNullOrEmpty()){
            mj8_2_1 = editText_time_1.text.toString()
        }else{
            Toast.makeText(this, "8-2번 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
            return false
        }

        if(!editText_minute_1.text.isNullOrEmpty()){
            mj8_2_2 = editText_minute_1.text.toString()
        }else{
            Toast.makeText(this, "8-2번 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
            return false
        }

        if(common_9_1_0.isChecked){
            mj9_1 = "0"
        }else if(common_9_1_1.isChecked){
            mj9_1 = "1"
        }else if(common_9_1_2.isChecked){
            mj9_1 = "2"
        }else if(common_9_1_3.isChecked){
            mj9_1 = "3"
        }else if(common_9_1_4.isChecked){
            mj9_1 = "4"
        }else if(common_9_1_5.isChecked){
            mj9_1 = "5"
        }else if(common_9_1_6.isChecked){
            mj9_1 = "6"
        }else if(common_9_1_7.isChecked){
            mj9_1 = "7"
        }else{
            Toast.makeText(this, "9-1번 문항을 체크해주세요.", Toast.LENGTH_LONG).show()
            return false
        }

        if(!editText_time_2.text.isNullOrEmpty()){
            mj9_2_1 = editText_time_2.text.toString()
        }else{
            Toast.makeText(this, "9-2번 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
            return false
        }

        if(!editText_minute_2.text.isNullOrEmpty()){
            mj9_2_2 = editText_minute_2.text.toString()
        }else{
            Toast.makeText(this, "9-2번 문항을 작성해주세요.", Toast.LENGTH_LONG).show()
            return false
        }

        if(common_10_0.isChecked) {
            mj10 = "0"
        }else if(common_10_1.isChecked){
            mj10 = "1"
        }else if(common_10_2.isChecked){
            mj10 = "2"
        }else if(common_10_3.isChecked){
            mj10 = "3"
        }else if(common_10_4.isChecked){
            mj10 = "4"
        }else if(common_10_5.isChecked){
            mj10 = "5"
        }else if(common_10_6.isChecked){
            mj10 = "6"
        }else if(common_10_7.isChecked){
            mj10 = "7"
        }else{
            Toast.makeText(this, "10번 문항을 체크해주세요.", Toast.LENGTH_LONG).show()
            return false
        }

        //exam_no
        exam_no = System.currentTimeMillis().toString()
        MainActivity.exam_no = exam_no

        PaperArray.PaperArrFunction.ArrayListInit() //<--처음 이닛

        PaperArray.PaperList.Arr_COMMON!!.add(Paper_COMMON(exam_date, exam_no, name,
                first_serial_text, last_serial_text, category,
                mj1_1_1, mj1_1_2, mj1_2_1, mj1_2_2, mj1_3_1, mj1_3_2, mj1_4_1, mj1_4_2,
                mj1_5_1, mj1_5_2, mj1_6_1, mj1_6_2, mj1_7_1, mj1_7_2, mj2_1,
                mj2_2, mj2_3, mj2_4, mj2_5, mj3, mj4, mj4_1_1, mj4_1_2, mj4_2_1,
                mj4_2_2, mj4_2_3, mj5, mj5_1_1, mj5_1_2, mj5_2_1, mj5_2_2,
                mj5_2_3, mj6, mj6_1, mj71, mj72, mj73, mj74, mj7_1_11, mj7_1_12, mj7_1_13, mj7_1_14,
                mj7_1_21, mj7_1_22, mj7_1_23, mj7_1_24, mj7_1_31, mj7_1_32, mj7_1_33,
                mj7_1_34, mj7_1_41, mj7_1_42, mj7_1_43, mj7_1_44, mj7_1_51, mj7_1_52, mj7_1_53,
                mj7_1_54, mj7_1_etc, mj7_2_11, mj7_2_12, mj7_2_13, mj7_2_14, mj7_2_21, mj7_2_22,
                mj7_2_23, mj7_2_24, mj7_2_31, mj7_2_32, mj7_2_33, mj7_2_34, mj7_2_41, mj7_2_42,
                mj7_2_43, mj7_2_44, mj7_2_51, mj7_2_52, mj7_2_53, mj7_2_54, mj7_2_etc, mj8_1,
                mj8_2_1, mj8_2_2, mj9_1, mj9_2_1, mj9_2_2, mj10))

        SavePaper.Total.Array[1] = Paper_COMMON(exam_date, exam_no, name,
                first_serial_text, last_serial_text, category,
                mj1_1_1, mj1_1_2, mj1_2_1, mj1_2_2, mj1_3_1, mj1_3_2, mj1_4_1, mj1_4_2,
                mj1_5_1, mj1_5_2, mj1_6_1, mj1_6_2, mj1_7_1, mj1_7_2, mj2_1,
                mj2_2, mj2_3, mj2_4, mj2_5, mj3, mj4, mj4_1_1, mj4_1_2, mj4_2_1,
                mj4_2_2, mj4_2_3, mj5, mj5_1_1, mj5_1_2, mj5_2_1, mj5_2_2,
                mj5_2_3, mj6, mj6_1, mj71, mj72, mj73, mj74, mj7_1_11, mj7_1_12, mj7_1_13, mj7_1_14,
                mj7_1_21, mj7_1_22, mj7_1_23, mj7_1_24, mj7_1_31, mj7_1_32, mj7_1_33,
                mj7_1_34, mj7_1_41, mj7_1_42, mj7_1_43, mj7_1_44, mj7_1_51, mj7_1_52, mj7_1_53,
                mj7_1_54, mj7_1_etc, mj7_2_11, mj7_2_12, mj7_2_13, mj7_2_14, mj7_2_21, mj7_2_22,
                mj7_2_23, mj7_2_24, mj7_2_31, mj7_2_32, mj7_2_33, mj7_2_34, mj7_2_41, mj7_2_42,
                mj7_2_43, mj7_2_44, mj7_2_51, mj7_2_52, mj7_2_53, mj7_2_54, mj7_2_etc, mj8_1,
                mj8_2_1, mj8_2_2, mj9_1, mj9_2_1, mj9_2_2, mj10)

        PaperArray.PaperList.Arr_RESULT!!.add(PaperArray.PaperList.Arr_COMMON!!)

        return true
    }

    fun GetPaper(paper: Paper_COMMON) {

        state = "getPaper"

        cannotEditQuestionnaire(common_exam_inside_scroll_layout)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        common_examination_save.visibility = View.GONE
        common_examination_cancel.visibility = View.GONE
        common_edit_submit.visibility = View.VISIBLE


        if(paper.mj1_1_1 == "1"){
            diagnosis_medication_stroke_examination_check.isChecked = true
        }else if(paper.mj1_1_1 == "2"){
            diagnosis_medication_stroke_examination_check.isChecked = false
        }


        if(paper.mj1_1_2 == "1"){
            diagnosis_medication_stroke_medication_check.isChecked = true
        }else if(paper.mj1_1_2 == "2"){
            diagnosis_medication_stroke_medication_check.isChecked = false
        }

        if(paper.mj1_2_1 == "1"){
            diagnosis_medication_myocardial_examination_check.isChecked = true
        }else if(paper.mj1_2_1 == "2"){
            diagnosis_medication_myocardial_examination_check.isChecked = false
        }


        if(paper.mj1_2_2 == "1"){
            diagnosis_medication_myocardial_medication_check.isChecked = true
        }else if(paper.mj1_2_2 == "2"){
            diagnosis_medication_myocardial_medication_check.isChecked = false
        }


        if(paper.mj1_3_1 == "1"){
            diagnosis_medication_high_blood_pressure_examination_check.isChecked = true
        }else if(paper.mj1_3_1 == "2"){
            diagnosis_medication_high_blood_pressure_examination_check.isChecked = false
        }


        if(paper.mj1_3_2 == "1"){
            diagnosis_medication_high_blood_pressure_medication_check.isChecked = true
        }else if(paper.mj1_3_2 == "2"){
            diagnosis_medication_high_blood_pressure_medication_check.isChecked = false
        }


        if(paper.mj1_4_1 == "1"){
            diagnosis_medication_diabetes_examination_check.isChecked = true
        }else if(paper.mj1_4_1 == "2"){
            diagnosis_medication_diabetes_examination_check.isChecked = false
        }

        if(paper.mj1_4_2 == "1"){
            diagnosis_medication_diabetes_medication_check.isChecked = true
        }else if(paper.mj1_4_2 == "2"){
            diagnosis_medication_diabetes_medication_check.isChecked = false
        }


        if(paper.mj1_5_1 == "1"){
            diagnosis_medication_dyslipidemia_examination_check.isChecked = true
        }else if(paper.mj1_5_1 == "2"){
            diagnosis_medication_dyslipidemia_examination_check.isChecked = false
        }

        if(paper.mj1_5_2 == "1"){
            diagnosis_medication_dyslipidemia_medication_check.isChecked = true
        }else if(paper.mj1_5_2 == "2"){
            diagnosis_medication_dyslipidemia_medication_check.isChecked = false
        }


        if(paper.mj1_6_1 == "1"){
            diagnosis_medication_tuberculosis_examination_check.isChecked = true
        }else if(paper.mj1_6_1 == "2"){
            diagnosis_medication_tuberculosis_examination_check.isChecked = false
        }


        if(paper.mj1_6_2 == "1"){
            diagnosis_medication_tuberculosis_medication_check.isChecked = true
        }else if(paper.mj1_6_2 == "2"){
            diagnosis_medication_tuberculosis_medication_check.isChecked = false
        }


        if(paper.mj1_7_1 == "1"){
            diagnosis_medication_etc_examination_check.isChecked = true
        }else if(paper.mj1_7_1 == "2"){
            diagnosis_medication_etc_examination_check.isChecked = false
        }


        if(paper.mj1_7_2 == "1"){
            diagnosis_medication_etc_medication_check.isChecked = true
        }else if(paper.mj1_7_2 == "2"){
            diagnosis_medication_etc_medication_check.isChecked = false
        }


        if(paper.mj2_1 == "1"){
            family_history_disease_stroke_examination_check.isChecked = true
        }else if(paper.mj2_1 == "2"){
            family_history_disease_stroke_examination_check.isChecked = false
        }


        if(paper.mj2_2 == "1"){
            family_history_disease_myocardial_examination_check.isChecked = true
        }else if(paper.mj2_2 == "2"){
            family_history_disease_myocardial_examination_check.isChecked = false
        }



        if(paper.mj2_3 == "1"){
            family_history_disease_high_blood_pressure_examination_check.isChecked = true
        }else if(paper.mj2_3 == "2"){
            family_history_disease_high_blood_pressure_examination_check.isChecked = false
        }


        if(paper.mj2_4 == "1"){
            family_history_disease_diabetes_examination_check.isChecked = true
        }else if(paper.mj2_4 == "2"){
            family_history_disease_diabetes_examination_check.isChecked = false
        }



        if(paper.mj2_5 == "1"){
            family_history_etc_examination_check.isChecked = true
        }else if(paper.mj2_5 == "2"){
            family_history_etc_examination_check.isChecked = false
        }


        if(paper.mj3 == "1"){
            common_3_true.isChecked = true
        }else if(paper.mj3 == "2"){
            common_3_false.isChecked = true
        }else if(paper.mj3 == "3"){
            common_3_do_not_know.isChecked = true
        }


        if(paper.mj4 == "2"){
            common_4_true.isChecked = true

            textView20.visibility = View.VISIBLE
            common_4_1_radio.visibility = View.VISIBLE

            if(!paper.mj4_1_1.isNullOrEmpty()){

                common_4_1_true.isChecked = true
                textView42.visibility = View.VISIBLE
                textView43.visibility = View.VISIBLE
                textView44.visibility = View.VISIBLE
                editText_4_1_1.visibility = View.VISIBLE
                editText_4_1_2.visibility = View.VISIBLE

                editText_4_1_1.setText(paper.mj4_1_1)
                editText_4_1_2.setText(paper.mj4_1_2)
            }else if(!paper.mj4_2_1.isNullOrEmpty()){

                common_4_1_false.isChecked = true
                textView45.visibility = View.VISIBLE
                textView46.visibility = View.VISIBLE
                textView47.visibility = View.VISIBLE
                textView48.visibility = View.VISIBLE
                editText_4_1_3.visibility = View.VISIBLE
                editText_4_1_4.visibility = View.VISIBLE
                editText_4_1_5.visibility = View.VISIBLE


                editText_4_1_3.setText(paper.mj4_2_1)
                editText_4_1_4.setText(paper.mj4_2_2)
                editText_4_1_5.setText(paper.mj4_2_3)
            }
        }else if(paper.mj4 == "1"){
            common_4_false.isChecked = true
        }


        if(paper.mj5 == "2"){
            common_5_true.isChecked = true

            textView23.visibility = View.VISIBLE
            common_5_1_radio.visibility = View.VISIBLE


            if(!paper.mj5_1_1.isNullOrEmpty()){
                common_5_1_true.isChecked = true
                textView49.visibility = View.VISIBLE
                textView50.visibility = View.VISIBLE
                textView51.visibility = View.VISIBLE
                editText_5_1_1.visibility = View.VISIBLE
                editText_5_1_2.visibility = View.VISIBLE

                editText_5_1_1.setText(paper.mj5_1_1)
                editText_5_1_2.setText(paper.mj5_1_2)
            }else if(!paper.mj5_2_1.isNullOrEmpty()){
                common_5_1_false.isChecked = true
                textView52.visibility = View.VISIBLE
                textView53.visibility = View.VISIBLE
                textView54.visibility = View.VISIBLE
                textView55.visibility = View.VISIBLE
                editText_5_1_3.visibility = View.VISIBLE
                editText_5_1_4.visibility = View.VISIBLE
                editText_5_1_5.visibility = View.VISIBLE

                editText_5_1_3.setText(paper.mj5_2_1)
                editText_5_1_4.setText(paper.mj5_2_2)
                editText_5_1_5.setText(paper.mj5_2_3)
            }
        }else if(paper.mj5 == "1"){
            common_5_false.isChecked = true
        }


        if(paper.mj6 == "1"){
            common_6_true.isChecked = true

            textView25.visibility = View.VISIBLE
            common_6_1_radio.visibility = View.VISIBLE


            if(paper.mj6_1 == "1"){
                common_6_1_1.isChecked = true
            }else if(paper.mj6_1 == "2"){
                common_6_1_2.isChecked = true
            }else if(paper.mj6_1 == "3"){
                common_6_1_3.isChecked = true
            }else if(paper.mj6_1 == "4"){
                common_6_1_4.isChecked = true
            }else if(paper.mj6_1 == "5"){
                common_6_1_5.isChecked = true
            }

        }else if(paper.mj6 == "2"){
            common_6_false.isChecked = true
        }


        if(!paper.mj71.isNullOrEmpty()){
            println("나오는 값은?" + paper.mj71)
            common_7_1.isChecked = true
            common_7_1_editText.setText(paper.mj71)
        }else if(!paper.mj72.isNullOrEmpty()){
            common_7_2.isChecked = true
            common_7_2_editText.setText(paper.mj72)
        }else if(!paper.mj73.isNullOrEmpty()){
            common_7_3.isChecked = true
            common_7_3_editText.setText(paper.mj73)
        }else if(!paper.mj74.isNullOrEmpty()){
            common_7_4.isChecked = true
        }

        common_7_1_1_radio.visibility = View.VISIBLE
        table_edit_1.visibility = View.VISIBLE
        table_text_1.visibility = View.VISIBLE
        common_7_1_2_radio.visibility = View.VISIBLE
        table_edit_2.visibility = View.VISIBLE
        table_text_2.visibility = View.VISIBLE
        common_7_1_3_radio.visibility = View.VISIBLE
        table_edit_3.visibility = View.VISIBLE
        table_text_3.visibility = View.VISIBLE
        common_7_1_4_radio.visibility = View.VISIBLE
        table_edit_4.visibility = View.VISIBLE
        table_text_4.visibility = View.VISIBLE
        common_7_1_5_radio.visibility = View.VISIBLE
        table_edit_5.visibility = View.VISIBLE
        table_text_5.visibility = View.VISIBLE
        common_7_2_1_radio.visibility = View.VISIBLE
        table_edit_6.visibility = View.VISIBLE
        table_text_6.visibility = View.VISIBLE
        common_7_2_2_radio.visibility = View.VISIBLE
        table_edit_7.visibility = View.VISIBLE
        table_text_7.visibility = View.VISIBLE
        common_7_2_3_radio.visibility = View.VISIBLE
        table_edit_8.visibility = View.VISIBLE
        table_text_8.visibility = View.VISIBLE
        common_7_2_4_radio.visibility = View.VISIBLE
        table_edit_9.visibility = View.VISIBLE
        table_text_9.visibility = View.VISIBLE
        common_7_2_5_radio.visibility = View.VISIBLE
        table_edit_10.visibility = View.VISIBLE
        table_text_10.visibility = View.VISIBLE


        if(!paper.mj7_1_11.isNullOrEmpty()){
            checkBox1.isChecked = true
            common_7_1_1_1.isChecked = true
            table_edit_1.setText(paper.mj7_1_11)
        }else if(!paper.mj7_1_12.isNullOrEmpty()){
            checkBox1.isChecked = true
            common_7_1_1_2.isChecked = true
            table_edit_1.setText(paper.mj7_1_12)
        }else if(!paper.mj7_1_13.isNullOrEmpty()){
            checkBox1.isChecked = true
            common_7_1_1_3.isChecked = true
            table_edit_1.setText(paper.mj7_1_13)
        }else if(!paper.mj7_1_14.isNullOrEmpty()){
            checkBox1.isChecked = true
            common_7_1_1_4.isChecked = true
            table_edit_1.setText(paper.mj7_1_14)
        }


        if(!paper.mj7_1_21.isNullOrEmpty()){
            checkBox2.isChecked = true
            common_7_1_2_1.isChecked = true
            table_edit_2.setText(paper.mj7_1_21)
        }else if(!paper.mj7_1_22.isNullOrEmpty()){
            checkBox2.isChecked = true
            common_7_1_2_2.isChecked = true
            table_edit_2.setText(paper.mj7_1_22)
        }else if(!paper.mj7_1_23.isNullOrEmpty()){
            checkBox2.isChecked = true
            common_7_1_2_3.isChecked = true
            table_edit_2.setText(paper.mj7_1_23)
        }else if(!paper.mj7_1_24.isNullOrEmpty()){
            checkBox2.isChecked = true
            common_7_1_2_4.isChecked = true
            table_edit_2.setText(paper.mj7_1_24)
        }


        if(!paper.mj7_1_31.isNullOrEmpty()){
            checkBox3.isChecked = true
            common_7_1_3_1.isChecked = true
            table_edit_3.setText(paper.mj7_1_31)
        }else if(!paper.mj7_1_32.isNullOrEmpty()){
            checkBox3.isChecked = true
            common_7_1_3_2.isChecked = true
            table_edit_3.setText(paper.mj7_1_32)
        }else if(!paper.mj7_1_33.isNullOrEmpty()){
            checkBox3.isChecked = true
            common_7_1_3_3.isChecked = true
            table_edit_3.setText(paper.mj7_1_33)
        }else if(!paper.mj7_1_34.isNullOrEmpty()){
            checkBox3.isChecked = true
            common_7_1_3_4.isChecked = true
            table_edit_3.setText(paper.mj7_1_34)
        }


        if(!paper.mj7_1_41.isNullOrEmpty()){
            checkBox4.isChecked = true
            common_7_1_4_1.isChecked = true
            table_edit_4.setText(paper.mj7_1_41)
        }else if(!paper.mj7_1_42.isNullOrEmpty()){
            checkBox4.isChecked = true
            common_7_1_4_2.isChecked = true
            table_edit_4.setText(paper.mj7_1_42)
        }else if(!paper.mj7_1_43.isNullOrEmpty()){
            checkBox4.isChecked = true
            common_7_1_4_3.isChecked = true
            table_edit_4.setText(paper.mj7_1_43)
        }else if(!paper.mj7_1_44.isNullOrEmpty()){
            checkBox4.isChecked = true
            common_7_1_4_4.isChecked = true
            table_edit_4.setText(paper.mj7_1_44)
        }



        if(!paper.mj7_1_51.isNullOrEmpty()){
            checkBox5.isChecked = true
            common_7_1_5_1.isChecked = true
            table_edit_5.setText(paper.mj7_1_51)
        }else if(!paper.mj7_1_52.isNullOrEmpty()){
            checkBox5.isChecked = true
            common_7_1_5_2.isChecked = true
            table_edit_5.setText(paper.mj7_1_52)
        }else if(!paper.mj7_1_53.isNullOrEmpty()){
            checkBox5.isChecked = true
            common_7_1_5_3.isChecked = true
            table_edit_5.setText(paper.mj7_1_53)
        }else if(!paper.mj7_1_54.isNullOrEmpty()){
            checkBox5.isChecked = true
            common_7_1_5_4.isChecked = true
            table_edit_5.setText(paper.mj7_1_54)
        }


        if(!paper.mj7_2_11.isNullOrEmpty()){
            checkBox6.isChecked = true
            common_7_2_1_1.isChecked = true
            table_edit_6.setText(paper.mj7_2_11)
        }else if(!paper.mj7_2_12.isNullOrEmpty()){
            checkBox6.isChecked = true
            common_7_2_1_2.isChecked = true
            table_edit_6.setText(paper.mj7_2_12)
        }else if(!paper.mj7_2_13.isNullOrEmpty()){
            checkBox6.isChecked = true
            common_7_2_1_3.isChecked = true
            table_edit_6.setText(paper.mj7_2_13)
        }else if(!paper.mj7_2_14.isNullOrEmpty()){
            checkBox6.isChecked = true
            common_7_2_1_4.isChecked = true
            table_edit_6.setText(paper.mj7_2_14)
        }


        if(!paper.mj7_2_21.isNullOrEmpty()){
            checkBox7.isChecked = true
            common_7_2_2_1.isChecked = true
            table_edit_7.setText(paper.mj7_2_21)
        }else if(!paper.mj7_2_22.isNullOrEmpty()){
            checkBox7.isChecked = true
            common_7_2_2_2.isChecked = true
            table_edit_7.setText(paper.mj7_2_22)
        }else if(!paper.mj7_2_23.isNullOrEmpty()){
            checkBox7.isChecked = true
            common_7_2_2_3.isChecked = true
            table_edit_7.setText(paper.mj7_2_23)
        }else if(!paper.mj7_2_24.isNullOrEmpty()){
            checkBox7.isChecked = true
            common_7_2_2_4.isChecked = true
            table_edit_7.setText(paper.mj7_2_24)
        }


        if(!paper.mj7_2_31.isNullOrEmpty()){
            checkBox8.isChecked = true
            common_7_2_3_1.isChecked = true
            table_edit_8.setText(paper.mj7_2_31)
        }else if(!paper.mj7_2_32.isNullOrEmpty()){
            checkBox8.isChecked = true
            common_7_2_3_2.isChecked = true
            table_edit_8.setText(paper.mj7_2_32)
        }else if(!paper.mj7_2_33.isNullOrEmpty()){
            checkBox8.isChecked = true
            common_7_2_3_3.isChecked = true
            table_edit_8.setText(paper.mj7_2_33)
        }else if(!paper.mj7_2_34.isNullOrEmpty()){
            checkBox8.isChecked = true
            common_7_2_3_4.isChecked = true
            table_edit_8.setText(paper.mj7_2_34)
        }


        if(!paper.mj7_2_41.isNullOrEmpty()){
            checkBox9.isChecked = true
            common_7_2_4_1.isChecked = true
            table_edit_9.setText(paper.mj7_2_41)
        }else if(!paper.mj7_2_42.isNullOrEmpty()){
            checkBox9.isChecked = true
            common_7_2_4_2.isChecked = true
            table_edit_9.setText(paper.mj7_2_42)
        }else if(!paper.mj7_2_43.isNullOrEmpty()){
            checkBox9.isChecked = true
            common_7_2_4_3.isChecked = true
            table_edit_9.setText(paper.mj7_2_43)
        }else if(!paper.mj7_2_44.isNullOrEmpty()){
            checkBox9.isChecked = true
            common_7_2_4_4.isChecked = true
            table_edit_9.setText(paper.mj7_2_44)
        }


        if(!paper.mj7_2_51.isNullOrEmpty()){
            checkBox10.isChecked = true
            common_7_2_5_1.isChecked = true
            table_edit_10.setText(paper.mj7_2_51)
        }else if(!paper.mj7_2_52.isNullOrEmpty()){
            checkBox10.isChecked = true
            common_7_2_5_2.isChecked = true
            table_edit_10.setText(paper.mj7_2_52)
        }else if(!paper.mj7_2_53.isNullOrEmpty()){
            checkBox10.isChecked = true
            common_7_2_5_3.isChecked = true
            table_edit_10.setText(paper.mj7_2_53)
        }else if(!paper.mj7_2_54.isNullOrEmpty()){
            checkBox10.isChecked = true
            common_7_2_5_4.isChecked = true
            table_edit_10.setText(paper.mj7_2_54)
        }

        if(paper.mj8_1 == "0"){
            common_8_1_0.isChecked = true
        }else if(paper.mj8_1 == "1"){
            common_8_1_1.isChecked = true
        }else if(paper.mj8_1 == "2"){
            common_8_1_2.isChecked = true
        }else if(paper.mj8_1 == "3"){
            common_8_1_3.isChecked = true
        }else if(paper.mj8_1 == "4"){
            common_8_1_4.isChecked = true
        }else if(paper.mj8_1 == "5"){
            common_8_1_5.isChecked = true
        }else if(paper.mj8_1 == "6"){
            common_8_1_6.isChecked = true
        }else if(paper.mj8_1 == "7"){
            common_8_1_7.isChecked = true
        }


        if(!paper.mj8_2_1.isNullOrEmpty()){
            editText_time_1.setText(paper.mj8_2_1)
        }

        if(!paper.mj8_2_2.isNullOrEmpty()){
            editText_minute_1.setText(paper.mj8_2_2)
        }


        if(paper.mj9_1 == "0"){
            common_9_1_0.isChecked = true
        }else if(paper.mj9_1 == "1"){
            common_9_1_1.isChecked = true
        }else if(paper.mj9_1 == "2"){
            common_9_1_2.isChecked = true
        }else if(paper.mj9_1 == "3"){
            common_9_1_3.isChecked = true
        }else if(paper.mj9_1 == "4"){
            common_9_1_4.isChecked = true
        }else if(paper.mj9_1 == "5"){
            common_9_1_5.isChecked = true
        }else if(paper.mj9_1 == "6"){
            common_9_1_6.isChecked = true
        }else if(paper.mj9_1 == "7"){
            common_9_1_7.isChecked = true
        }


        if(!paper.mj9_2_1.isNullOrEmpty()){
            editText_time_2.setText(paper.mj9_2_1)
        }

        if(!paper.mj9_2_2.isNullOrEmpty()){
            editText_minute_2.setText(paper.mj9_2_2)
        }

        if(paper.mj10 == "0"){
            common_10_0.isChecked = true
        }else if(paper.mj10 == "1"){
            common_10_1.isChecked = true
        }else if(paper.mj10 == "2"){
            common_10_2.isChecked = true
        }else if(paper.mj10 == "3"){
            common_10_3.isChecked = true
        }else if(paper.mj10 == "4"){
            common_10_4.isChecked = true
        }else if(paper.mj10 == "5"){
            common_10_5.isChecked = true
        }else if(paper.mj10 == "6"){
            common_10_6.isChecked = true
        }else if(paper.mj10 == "7"){
            common_10_7.isChecked = true
        }


    }

    fun GetPaper(paper: ServerPaper_Common) {

        state = "getPaper"

        cannotEditQuestionnaire(common_exam_inside_scroll_layout)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.mj_name
        first_serial.text = paper.mj_jumin.substring(0, 6)
        last_serial.text = paper.mj_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        println(paper)

        common_examination_save.visibility = View.GONE
        common_examination_cancel.visibility = View.GONE
        common_edit_submit.visibility = View.VISIBLE


        if(paper.mj1_1_1 == "1"){
            diagnosis_medication_stroke_examination_check.isChecked = true
        }else if(paper.mj1_1_1 == "2"){
            diagnosis_medication_stroke_examination_check.isChecked = false
        }


        if(paper.mj1_1_2 == "1"){
            diagnosis_medication_stroke_medication_check.isChecked = true
        }else if(paper.mj1_1_2 == "2"){
            diagnosis_medication_stroke_medication_check.isChecked = false
        }

        if(paper.mj1_2_1 == "1"){
            diagnosis_medication_myocardial_examination_check.isChecked = true
        }else if(paper.mj1_2_1 == "2"){
            diagnosis_medication_myocardial_examination_check.isChecked = false
        }


        if(paper.mj1_2_2 == "1"){
            diagnosis_medication_myocardial_medication_check.isChecked = true
        }else if(paper.mj1_2_2 == "2"){
            diagnosis_medication_myocardial_medication_check.isChecked = false
        }


        if(paper.mj1_3_1 == "1"){
            diagnosis_medication_high_blood_pressure_examination_check.isChecked = true
        }else if(paper.mj1_3_1 == "2"){
            diagnosis_medication_high_blood_pressure_examination_check.isChecked = false
        }


        if(paper.mj1_3_2 == "1"){
            diagnosis_medication_high_blood_pressure_medication_check.isChecked = true
        }else if(paper.mj1_3_2 == "2"){
            diagnosis_medication_high_blood_pressure_medication_check.isChecked = false
        }


        if(paper.mj1_4_1 == "1"){
            diagnosis_medication_diabetes_examination_check.isChecked = true
        }else if(paper.mj1_4_1 == "2"){
            diagnosis_medication_diabetes_examination_check.isChecked = false
        }

        if(paper.mj1_4_2 == "1"){
            diagnosis_medication_diabetes_medication_check.isChecked = true
        }else if(paper.mj1_4_2 == "2"){
            diagnosis_medication_diabetes_medication_check.isChecked = false
        }


        if(paper.mj1_5_1 == "1"){
            diagnosis_medication_dyslipidemia_examination_check.isChecked = true
        }else if(paper.mj1_5_1 == "2"){
            diagnosis_medication_dyslipidemia_examination_check.isChecked = false
        }

        if(paper.mj1_5_2 == "1"){
            diagnosis_medication_dyslipidemia_medication_check.isChecked = true
        }else if(paper.mj1_5_2 == "2"){
            diagnosis_medication_dyslipidemia_medication_check.isChecked = false
        }


        if(paper.mj1_6_1 == "1"){
            diagnosis_medication_tuberculosis_examination_check.isChecked = true
        }else if(paper.mj1_6_1 == "2"){
            diagnosis_medication_tuberculosis_examination_check.isChecked = false
        }


        if(paper.mj1_6_2 == "1"){
            diagnosis_medication_tuberculosis_medication_check.isChecked = true
        }else if(paper.mj1_6_2 == "2"){
            diagnosis_medication_tuberculosis_medication_check.isChecked = false
        }


        if(paper.mj1_7_1 == "1"){
            diagnosis_medication_etc_examination_check.isChecked = true
        }else if(paper.mj1_7_1 == "2"){
            diagnosis_medication_etc_examination_check.isChecked = false
        }


        if(paper.mj1_7_2 == "1"){
            diagnosis_medication_etc_medication_check.isChecked = true
        }else if(paper.mj1_7_2 == "2"){
            diagnosis_medication_etc_medication_check.isChecked = false
        }


        if(paper.mj2_1 == "1"){
            family_history_disease_stroke_examination_check.isChecked = true
        }else if(paper.mj2_1 == "2"){
            family_history_disease_stroke_examination_check.isChecked = false
        }


        if(paper.mj2_2 == "1"){
            family_history_disease_myocardial_examination_check.isChecked = true
        }else if(paper.mj2_2 == "2"){
            family_history_disease_myocardial_examination_check.isChecked = false
        }



        if(paper.mj2_3 == "1"){
            family_history_disease_high_blood_pressure_examination_check.isChecked = true
        }else if(paper.mj2_3 == "2"){
            family_history_disease_high_blood_pressure_examination_check.isChecked = false
        }


        if(paper.mj2_4 == "1"){
            family_history_disease_diabetes_examination_check.isChecked = true
        }else if(paper.mj2_4 == "2"){
            family_history_disease_diabetes_examination_check.isChecked = false
        }



        if(paper.mj2_5 == "1"){
            family_history_etc_examination_check.isChecked = true
        }else if(paper.mj2_5 == "2"){
            family_history_etc_examination_check.isChecked = false
        }


        if(paper.mj3 == "1"){
            common_3_true.isChecked = true
        }else if(paper.mj3 == "2"){
            common_3_false.isChecked = true
        }else if(paper.mj3 == "3"){
            common_3_do_not_know.isChecked = true
        }


        if(paper.mj4 == "2"){
            common_4_true.isChecked = true

            textView20.visibility = View.VISIBLE
            common_4_1_radio.visibility = View.VISIBLE

            if(!paper.mj4_1_1.isNullOrEmpty()){

                common_4_1_true.isChecked = true
                textView42.visibility = View.VISIBLE
                textView43.visibility = View.VISIBLE
                textView44.visibility = View.VISIBLE
                editText_4_1_1.visibility = View.VISIBLE
                editText_4_1_2.visibility = View.VISIBLE

                editText_4_1_1.setText(paper.mj4_1_1)
                editText_4_1_2.setText(paper.mj4_1_2)
            }else if(!paper.mj4_2_1.isNullOrEmpty()){

                common_4_1_false.isChecked = true
                textView45.visibility = View.VISIBLE
                textView46.visibility = View.VISIBLE
                textView47.visibility = View.VISIBLE
                textView48.visibility = View.VISIBLE
                editText_4_1_3.visibility = View.VISIBLE
                editText_4_1_4.visibility = View.VISIBLE
                editText_4_1_5.visibility = View.VISIBLE


                editText_4_1_3.setText(paper.mj4_2_1)
                editText_4_1_4.setText(paper.mj4_2_2)
                editText_4_1_5.setText(paper.mj4_2_3)
            }
        }else if(paper.mj4 == "1"){
            common_4_false.isChecked = true
        }


        if(paper.mj5 == "2"){
            common_5_true.isChecked = true

            textView23.visibility = View.VISIBLE
            common_5_1_radio.visibility = View.VISIBLE


            if(!paper.mj5_1_1.isNullOrEmpty()){
                common_5_1_true.isChecked = true
                textView49.visibility = View.VISIBLE
                textView50.visibility = View.VISIBLE
                textView51.visibility = View.VISIBLE
                editText_5_1_1.visibility = View.VISIBLE
                editText_5_1_2.visibility = View.VISIBLE

                editText_5_1_1.setText(paper.mj5_1_1)
                editText_5_1_2.setText(paper.mj5_1_2)
            }else if(!paper.mj5_2_1.isNullOrEmpty()){
                common_5_1_false.isChecked = true
                textView52.visibility = View.VISIBLE
                textView53.visibility = View.VISIBLE
                textView54.visibility = View.VISIBLE
                textView55.visibility = View.VISIBLE
                editText_5_1_3.visibility = View.VISIBLE
                editText_5_1_4.visibility = View.VISIBLE
                editText_5_1_5.visibility = View.VISIBLE

                editText_5_1_3.setText(paper.mj5_2_1)
                editText_5_1_4.setText(paper.mj5_2_2)
                editText_5_1_5.setText(paper.mj5_2_3)
            }
        }else if(paper.mj5 == "1"){
            common_5_false.isChecked = true
        }


        if(paper.mj6 == "1"){
            common_6_true.isChecked = true

            textView25.visibility = View.VISIBLE
            common_6_1_radio.visibility = View.VISIBLE


            if(paper.mj6_1 == "1"){
                common_6_1_1.isChecked = true
            }else if(paper.mj6_1 == "2"){
                common_6_1_2.isChecked = true
            }else if(paper.mj6_1 == "3"){
                common_6_1_3.isChecked = true
            }else if(paper.mj6_1 == "4"){
                common_6_1_4.isChecked = true
            }else if(paper.mj6_1 == "5"){
                common_6_1_5.isChecked = true
            }

        }else if(paper.mj6 == "2"){
            common_6_false.isChecked = true
        }


        if(!paper.mj71.isNullOrEmpty()){
            println("나오는 값은?" + paper.mj71)
            common_7_1.isChecked = true
            common_7_1_editText.setText(paper.mj71)
        }else if(!paper.mj72.isNullOrEmpty()){
            common_7_2.isChecked = true
            common_7_2_editText.setText(paper.mj72)
        }else if(!paper.mj73.isNullOrEmpty()){
            common_7_3.isChecked = true
            common_7_3_editText.setText(paper.mj73)
        }else if(!paper.mj74.isNullOrEmpty()){
            common_7_4.isChecked = true
        }

        common_7_1_1_radio.visibility = View.VISIBLE
        table_edit_1.visibility = View.VISIBLE
        table_text_1.visibility = View.VISIBLE
        common_7_1_2_radio.visibility = View.VISIBLE
        table_edit_2.visibility = View.VISIBLE
        table_text_2.visibility = View.VISIBLE
        common_7_1_3_radio.visibility = View.VISIBLE
        table_edit_3.visibility = View.VISIBLE
        table_text_3.visibility = View.VISIBLE
        common_7_1_4_radio.visibility = View.VISIBLE
        table_edit_4.visibility = View.VISIBLE
        table_text_4.visibility = View.VISIBLE
        common_7_1_5_radio.visibility = View.VISIBLE
        table_edit_5.visibility = View.VISIBLE
        table_text_5.visibility = View.VISIBLE
        common_7_2_1_radio.visibility = View.VISIBLE
        table_edit_6.visibility = View.VISIBLE
        table_text_6.visibility = View.VISIBLE
        common_7_2_2_radio.visibility = View.VISIBLE
        table_edit_7.visibility = View.VISIBLE
        table_text_7.visibility = View.VISIBLE
        common_7_2_3_radio.visibility = View.VISIBLE
        table_edit_8.visibility = View.VISIBLE
        table_text_8.visibility = View.VISIBLE
        common_7_2_4_radio.visibility = View.VISIBLE
        table_edit_9.visibility = View.VISIBLE
        table_text_9.visibility = View.VISIBLE
        common_7_2_5_radio.visibility = View.VISIBLE
        table_edit_10.visibility = View.VISIBLE
        table_text_10.visibility = View.VISIBLE


        if(!paper.mj7_1_11.isNullOrEmpty()){
            checkBox1.isChecked = true
            common_7_1_1_1.isChecked = true
            table_edit_1.setText(paper.mj7_1_11)
        }else if(!paper.mj7_1_12.isNullOrEmpty()){
            checkBox1.isChecked = true
            common_7_1_1_2.isChecked = true
            table_edit_1.setText(paper.mj7_1_12)
        }else if(!paper.mj7_1_13.isNullOrEmpty()){
            checkBox1.isChecked = true
            common_7_1_1_3.isChecked = true
            table_edit_1.setText(paper.mj7_1_13)
        }else if(!paper.mj7_1_14.isNullOrEmpty()){
            checkBox1.isChecked = true
            common_7_1_1_4.isChecked = true
            table_edit_1.setText(paper.mj7_1_14)
        }


        if(!paper.mj7_1_21.isNullOrEmpty()){
            checkBox2.isChecked = true
            common_7_1_2_1.isChecked = true
            table_edit_2.setText(paper.mj7_1_21)
        }else if(!paper.mj7_1_22.isNullOrEmpty()){
            checkBox2.isChecked = true
            common_7_1_2_2.isChecked = true
            table_edit_2.setText(paper.mj7_1_22)
        }else if(!paper.mj7_1_23.isNullOrEmpty()){
            checkBox2.isChecked = true
            common_7_1_2_3.isChecked = true
            table_edit_2.setText(paper.mj7_1_23)
        }else if(!paper.mj7_1_24.isNullOrEmpty()){
            checkBox2.isChecked = true
            common_7_1_2_4.isChecked = true
            table_edit_2.setText(paper.mj7_1_24)
        }


        if(!paper.mj7_1_31.isNullOrEmpty()){
            checkBox3.isChecked = true
            common_7_1_3_1.isChecked = true
            table_edit_3.setText(paper.mj7_1_31)
        }else if(!paper.mj7_1_32.isNullOrEmpty()){
            checkBox3.isChecked = true
            common_7_1_3_2.isChecked = true
            table_edit_3.setText(paper.mj7_1_32)
        }else if(!paper.mj7_1_33.isNullOrEmpty()){
            checkBox3.isChecked = true
            common_7_1_3_3.isChecked = true
            table_edit_3.setText(paper.mj7_1_33)
        }else if(!paper.mj7_1_34.isNullOrEmpty()){
            checkBox3.isChecked = true
            common_7_1_3_4.isChecked = true
            table_edit_3.setText(paper.mj7_1_34)
        }


        if(!paper.mj7_1_41.isNullOrEmpty()){
            checkBox4.isChecked = true
            common_7_1_4_1.isChecked = true
            table_edit_4.setText(paper.mj7_1_41)
        }else if(!paper.mj7_1_42.isNullOrEmpty()){
            checkBox4.isChecked = true
            common_7_1_4_2.isChecked = true
            table_edit_4.setText(paper.mj7_1_42)
        }else if(!paper.mj7_1_43.isNullOrEmpty()){
            checkBox4.isChecked = true
            common_7_1_4_3.isChecked = true
            table_edit_4.setText(paper.mj7_1_43)
        }else if(!paper.mj7_1_44.isNullOrEmpty()){
            checkBox4.isChecked = true
            common_7_1_4_4.isChecked = true
            table_edit_4.setText(paper.mj7_1_44)
        }



        if(!paper.mj7_1_51.isNullOrEmpty()){
            checkBox5.isChecked = true
            common_7_1_5_1.isChecked = true
            table_edit_5.setText(paper.mj7_1_51)
        }else if(!paper.mj7_1_52.isNullOrEmpty()){
            checkBox5.isChecked = true
            common_7_1_5_2.isChecked = true
            table_edit_5.setText(paper.mj7_1_52)
        }else if(!paper.mj7_1_53.isNullOrEmpty()){
            checkBox5.isChecked = true
            common_7_1_5_3.isChecked = true
            table_edit_5.setText(paper.mj7_1_53)
        }else if(!paper.mj7_1_54.isNullOrEmpty()){
            checkBox5.isChecked = true
            common_7_1_5_4.isChecked = true
            table_edit_5.setText(paper.mj7_1_54)
        }


        if(!paper.mj7_2_11.isNullOrEmpty()){
            checkBox6.isChecked = true
            common_7_2_1_1.isChecked = true
            table_edit_6.setText(paper.mj7_2_11)
        }else if(!paper.mj7_2_12.isNullOrEmpty()){
            checkBox6.isChecked = true
            common_7_2_1_2.isChecked = true
            table_edit_6.setText(paper.mj7_2_12)
        }else if(!paper.mj7_2_13.isNullOrEmpty()){
            checkBox6.isChecked = true
            common_7_2_1_3.isChecked = true
            table_edit_6.setText(paper.mj7_2_13)
        }else if(!paper.mj7_2_14.isNullOrEmpty()){
            checkBox6.isChecked = true
            common_7_2_1_4.isChecked = true
            table_edit_6.setText(paper.mj7_2_14)
        }


        if(!paper.mj7_2_21.isNullOrEmpty()){
            checkBox7.isChecked = true
            common_7_2_2_1.isChecked = true
            table_edit_7.setText(paper.mj7_2_21)
        }else if(!paper.mj7_2_22.isNullOrEmpty()){
            checkBox7.isChecked = true
            common_7_2_2_2.isChecked = true
            table_edit_7.setText(paper.mj7_2_22)
        }else if(!paper.mj7_2_23.isNullOrEmpty()){
            checkBox7.isChecked = true
            common_7_2_2_3.isChecked = true
            table_edit_7.setText(paper.mj7_2_23)
        }else if(!paper.mj7_2_24.isNullOrEmpty()){
            checkBox7.isChecked = true
            common_7_2_2_4.isChecked = true
            table_edit_7.setText(paper.mj7_2_24)
        }


        if(!paper.mj7_2_31.isNullOrEmpty()){
            checkBox8.isChecked = true
            common_7_2_3_1.isChecked = true
            table_edit_8.setText(paper.mj7_2_31)
        }else if(!paper.mj7_2_32.isNullOrEmpty()){
            checkBox8.isChecked = true
            common_7_2_3_2.isChecked = true
            table_edit_8.setText(paper.mj7_2_32)
        }else if(!paper.mj7_2_33.isNullOrEmpty()){
            checkBox8.isChecked = true
            common_7_2_3_3.isChecked = true
            table_edit_8.setText(paper.mj7_2_33)
        }else if(!paper.mj7_2_34.isNullOrEmpty()){
            checkBox8.isChecked = true
            common_7_2_3_4.isChecked = true
            table_edit_8.setText(paper.mj7_2_34)
        }


        if(!paper.mj7_2_41.isNullOrEmpty()){
            checkBox9.isChecked = true
            common_7_2_4_1.isChecked = true
            table_edit_9.setText(paper.mj7_2_41)
        }else if(!paper.mj7_2_42.isNullOrEmpty()){
            checkBox9.isChecked = true
            common_7_2_4_2.isChecked = true
            table_edit_9.setText(paper.mj7_2_42)
        }else if(!paper.mj7_2_43.isNullOrEmpty()){
            checkBox9.isChecked = true
            common_7_2_4_3.isChecked = true
            table_edit_9.setText(paper.mj7_2_43)
        }else if(!paper.mj7_2_44.isNullOrEmpty()){
            checkBox9.isChecked = true
            common_7_2_4_4.isChecked = true
            table_edit_9.setText(paper.mj7_2_44)
        }


        if(!paper.mj7_2_51.isNullOrEmpty()){
            checkBox10.isChecked = true
            common_7_2_5_1.isChecked = true
            table_edit_10.setText(paper.mj7_2_51)
        }else if(!paper.mj7_2_52.isNullOrEmpty()){
            checkBox10.isChecked = true
            common_7_2_5_2.isChecked = true
            table_edit_10.setText(paper.mj7_2_52)
        }else if(!paper.mj7_2_53.isNullOrEmpty()){
            checkBox10.isChecked = true
            common_7_2_5_3.isChecked = true
            table_edit_10.setText(paper.mj7_2_53)
        }else if(!paper.mj7_2_54.isNullOrEmpty()){
            checkBox10.isChecked = true
            common_7_2_5_4.isChecked = true
            table_edit_10.setText(paper.mj7_2_54)
        }

        if(paper.mj8_1 == "0"){
            common_8_1_0.isChecked = true
        }else if(paper.mj8_1 == "1"){
            common_8_1_1.isChecked = true
        }else if(paper.mj8_1 == "2"){
            common_8_1_2.isChecked = true
        }else if(paper.mj8_1 == "3"){
            common_8_1_3.isChecked = true
        }else if(paper.mj8_1 == "4"){
            common_8_1_4.isChecked = true
        }else if(paper.mj8_1 == "5"){
            common_8_1_5.isChecked = true
        }else if(paper.mj8_1 == "6"){
            common_8_1_6.isChecked = true
        }else if(paper.mj8_1 == "7"){
            common_8_1_7.isChecked = true
        }


        if(!paper.mj8_2_1.isNullOrEmpty()){
            editText_time_1.setText(paper.mj8_2_1)
        }

        if(!paper.mj8_2_2.isNullOrEmpty()){
            editText_minute_1.setText(paper.mj8_2_2)
        }


        if(paper.mj9_1 == "0"){
            common_9_1_0.isChecked = true
        }else if(paper.mj9_1 == "1"){
            common_9_1_1.isChecked = true
        }else if(paper.mj9_1 == "2"){
            common_9_1_2.isChecked = true
        }else if(paper.mj9_1 == "3"){
            common_9_1_3.isChecked = true
        }else if(paper.mj9_1 == "4"){
            common_9_1_4.isChecked = true
        }else if(paper.mj9_1 == "5"){
            common_9_1_5.isChecked = true
        }else if(paper.mj9_1 == "6"){
            common_9_1_6.isChecked = true
        }else if(paper.mj9_1 == "7"){
            common_9_1_7.isChecked = true
        }


        if(!paper.mj9_2_1.isNullOrEmpty()){
            editText_time_2.setText(paper.mj9_2_1)
        }

        if(!paper.mj9_2_2.isNullOrEmpty()){
            editText_minute_2.setText(paper.mj9_2_2)
        }

        if(paper.mj10 == "0"){
            common_10_0.isChecked = true
        }else if(paper.mj10 == "1"){
            common_10_1.isChecked = true
        }else if(paper.mj10 == "2"){
            common_10_2.isChecked = true
        }else if(paper.mj10 == "3"){
            common_10_3.isChecked = true
        }else if(paper.mj10 == "4"){
            common_10_4.isChecked = true
        }else if(paper.mj10 == "5"){
            common_10_5.isChecked = true
        }else if(paper.mj10 == "6"){
            common_10_6.isChecked = true
        }else if(paper.mj10 == "7"){
            common_10_7.isChecked = true
        }


    }

    fun Drinking(){

        var common1 = 0.00
        var common2 = 0.00
        var common3 = 0.00

        var num1 = 0.00
        var num2 = 0.00
        var num3 = 0.00
        var num4 = 0.00
        var num5 = 0.00

        var num6 = 0.00
        var num7 = 0.00
        var num8 = 0.00
        var num9 = 0.00
        var num10 = 0.00


        if(common_7_1.isChecked){
            common1 = common_7_1_editText.text.toString().toDouble()

        }else if(common_7_2.isChecked){
            var numbering = common_7_2_editText.text.toString().toDouble()/4
            common1 = String.format("%.2f", numbering).toDouble()

        }else if(common_7_3.isChecked){
            var numbering = common_7_3_editText.text.toString().toDouble()/48
            common1 = String.format("%.2f", numbering).toDouble()

        }else if(common_7_4.isChecked){
            AdditionalArr.over.isDrinking = false
            AdditionalArr.over.isDrinking2 = false
        }



        if(checkBox1.isChecked){
            if(common_7_1_1_1.isChecked){
                num1 = (table_edit_1.text.toString().toDouble() * 4)/7
            }else if(common_7_1_1_2.isChecked){
                num1 = (table_edit_1.text.toString().toDouble() * 4)
            }else if(common_7_1_1_3.isChecked){

            }else if(common_7_1_1_4.isChecked){
                num1 = (table_edit_1.text.toString().toDouble() * 90)
            }
        }

        if(checkBox2.isChecked){
            if(common_7_1_2_1.isChecked){
                num2 = (table_edit_2.text.toString().toDouble() * 200)/350
            }else if(common_7_1_2_2.isChecked){
                num2 = (table_edit_2.text.toString().toDouble() * 500)/350
            }else if(common_7_1_2_3.isChecked){
                num2 = table_edit_2.text.toString().toDouble()
            }else if(common_7_1_2_4.isChecked){
                num2 = table_edit_2.text.toString().toDouble() / 350
            }
        }

        if(checkBox3.isChecked){
            if(common_7_1_3_1.isChecked){
                num3 = table_edit_3.text.toString().toDouble()
            }else if(common_7_1_3_2.isChecked){
                num3 = (table_edit_3.text.toString().toDouble() * 500)/45
            }else if(common_7_1_3_3.isChecked){

            }else if(common_7_1_3_4.isChecked){
                num3 = table_edit_3.text.toString().toDouble()/45
            }
        }

        if(checkBox4.isChecked){
            if(common_7_1_4_1.isChecked){
                num4 = table_edit_4.text.toString().toDouble()
            }else if(common_7_1_4_2.isChecked){
                num4 = (table_edit_4.text.toString().toDouble() * 750)/300
            }else if(common_7_1_4_3.isChecked){

            }else if(common_7_1_4_4.isChecked){
                num4 = table_edit_4.text.toString().toDouble()/300
            }
        }

        if(checkBox5.isChecked){
            if(common_7_1_5_1.isChecked){
                num5 = table_edit_5.text.toString().toDouble()
            }else if(common_7_1_5_2.isChecked){
                num5 = (table_edit_5.text.toString().toDouble() * 750)/300
            }else if(common_7_1_5_3.isChecked){

            }else if(common_7_1_5_4.isChecked){
                num5 = table_edit_5.text.toString().toDouble()/150
            }
        }


        var numbering = (num1 + num2 + num3 + num4 + num5) * common1
        common2 = String.format("%.2f", numbering).toDouble()



        if(checkBox6.isChecked){
            if(common_7_2_1_1.isChecked){
                num6 = (table_edit_6.text.toString().toDouble() * 4) / 7
            }else if(common_7_2_1_2.isChecked){
                num6 = table_edit_6.text.toString().toDouble() * 4
            }else if(common_7_2_1_3.isChecked){

            }else if(common_7_2_1_4.isChecked){
                num6 = table_edit_6.text.toString().toDouble() * 90
            }
        }

        if(checkBox7.isChecked){
            if(common_7_2_2_1.isChecked){
                num7 = (table_edit_7.text.toString().toDouble() * 200)/350
            }else if(common_7_2_2_2.isChecked){
                num7 = (table_edit_7.text.toString().toDouble() * 500)/350
            }else if(common_7_2_2_3.isChecked){
                num7 = table_edit_7.text.toString().toDouble()
            }else if(common_7_2_2_4.isChecked){
                num7 = table_edit_7.text.toString().toDouble()/350
            }
        }

        if(checkBox8.isChecked){
            if(common_7_2_3_1.isChecked){
                num8 = table_edit_8.text.toString().toDouble()
            }else if(common_7_2_3_2.isChecked){
                num8 = (table_edit_8.text.toString().toDouble() * 500)/45
            }else if(common_7_2_3_3.isChecked){

            }else if(common_7_2_3_4.isChecked){
                num8 = table_edit_8.text.toString().toDouble() / 45
            }
        }

        if(checkBox9.isChecked){
            if(common_7_2_4_1.isChecked){
                num9 = table_edit_9.text.toString().toDouble()
            }else if(common_7_2_4_2.isChecked){
                num9 = (table_edit_9.text.toString().toDouble() * 750)/300
            }else if(common_7_2_4_3.isChecked){

            }else if(common_7_2_4_4.isChecked){
                num9 = table_edit_9.text.toString().toDouble()/300
            }
        }

        if(checkBox10.isChecked){
            if(common_7_2_5_1.isChecked){
                num10 = table_edit_10.text.toString().toDouble()
            }else if(common_7_2_5_2.isChecked){
                num10 = (table_edit_10.text.toString().toDouble() * 750)/150
            }else if(common_7_2_5_3.isChecked){

            }else if(common_7_2_5_4.isChecked){
                num10 = table_edit_10.text.toString().toDouble()/150
            }
        }


        var numbering2 = (num6 + num7 + num8 + num9 + num10)
        common3 = String.format("%.1f", numbering2).toDouble()

        var gender = "M"

        if(AdditionalArr.Gender.isFemale){
            gender = "F"
        }else{
            gender = "M"
        }

        var yyStr = MainActivity.user_first_serial.substring(0,2)
        var birthyear = ("19"+yyStr).toInt()
        var nowyear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")).toInt()
        var age =  nowyear - birthyear

        Run(gender, age ,common2, common3)
    }

    fun Run(Gender:String, Age:Int, WeekDrink:Double, OverDrink:Double)
    {
        //Gender: 성별
        //Age: 나이
        //WeekDrink: 과음측정 기준 음주량
        //OverDrink: 폭음측정 기준 음주량

        //과음판정
        var isManyDrink:Boolean = false

        //폭음판정
        var isOverDrink:Boolean = false

        ////////////////////과음측정 시작////////////////////
        when
        {
            Age<65 ->
            {
                if (Gender=="M") //65세 미만 남자
                {
                    if(WeekDrink > 14)
                    {
                        isManyDrink = true
                    }
                }
                else //65세 미만 여자
                {
                    if(WeekDrink > 7)
                    {
                        isManyDrink = true
                    }
                }
            }
            Age>=65 ->
            {
                if (Gender=="M") //65세 이상 남자
                {
                    if(WeekDrink > 7)
                    {
                        isManyDrink = true
                    }
                }
                else //65세 이상 여자
                {
                    if(WeekDrink > 3)
                    {
                        isManyDrink = true
                    }
                }
            }
        }
        ////////////////////과음측정 끝////////////////////



        ////////////////////폭음측정 시작////////////////////
        when
        {
            //남자일 때
            Gender=="M" ->
            {
                if(OverDrink > 4)
                {
                    isOverDrink = true
                }
            }

            //여자일 때
            Gender=="F" ->
            {
                if(OverDrink > 3)
                {
                    isOverDrink = true
                }
            }

        }
        ////////////////////폭음측정 끝////////////////////

        AdditionalArr.over.isDrinking = isOverDrink
        AdditionalArr.over.isDrinking2 = isManyDrink
        AdditionalArr.over.checkAll = true

    }

}
