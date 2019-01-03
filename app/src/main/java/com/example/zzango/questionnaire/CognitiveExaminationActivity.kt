package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_cognitive_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class CognitiveExaminationActivity : AppCompatActivity(){

    var exam_result : ArrayList<CognitiveExaminationActivity.ExamInfo>? = null
    var sql_db : SQLiteDatabase? = null
    var popup = false

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("mj_inji_1") @Expose var mj_inji_1 : String,
                         @SerializedName("mj_inji_2") @Expose var mj_inji_2 : String,
                         @SerializedName("mj_inji_3") @Expose var mj_inji_3 : String,
                         @SerializedName("mj_inji_4") @Expose var mj_inji_4 : String,
                         @SerializedName("mj_inji_5") @Expose var mj_inji_5 : String,
                         @SerializedName("mj_inji_6") @Expose var mj_inji_6 : String,
                         @SerializedName("mj_inji_7") @Expose var mj_inji_7 : String,
                         @SerializedName("mj_inji_8") @Expose var mj_inji_8 : String,
                         @SerializedName("mj_inji_9") @Expose var mj_inji_9 : String,
                         @SerializedName("mj_inji_10") @Expose var mj_inji_10 : String,
                         @SerializedName("mj_inji_11") @Expose var mj_inji_11 : String,
                         @SerializedName("mj_inji_12") @Expose var mj_inji_12 : String,
                         @SerializedName("mj_inji_13") @Expose var mj_inji_13 : String,
                         @SerializedName("mj_inji_14") @Expose var mj_inji_14 : String,
                         @SerializedName("mj_inji_15") @Expose var mj_inji_15 : String,
                         @SerializedName("mj_inji_sum") @Expose var mj_inji_sum : String)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cognitive_exam)


        sql_db = LocalDBhelper(this).writableDatabase

        cognitive_examination_save.setOnClickListener {

            if(check()){

                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    cognitive_exam_local_insert()

                }else{

                    cognitive_exam_server_insert()

                }

            }

        }

        cognitive_examination_cancel.setOnClickListener {

            finish()

        }

    }

    fun cognitive_exam_local_insert(){

        println("로컬")

        LocalDBhelper(this).cognitiveCreate(sql_db)

        LocalDBhelper(this).cognitiveSaveLocal(sql_db!!, exam_result!!)

        saveCompleteAlert()

    }

    fun cognitive_exam_server_insert(){

        println("서버")

        OracleUtill().cognitive_examination().cognitiveServer(exam_result!!).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        println(response.body())
                        Toast.makeText(this@CognitiveExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        saveCompleteAlert()

                    }

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                Toast.makeText(this@CognitiveExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }

        })

    }

    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE

        popup = false

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_complete_alert, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.save_complete_alert_text.setText("저장이 완료 되었습니다")

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

        dialog_view.return_alert.setOnClickListener {

            startActivity(Intent(this@CognitiveExaminationActivity, MainActivity::class.java).putExtra("from", "oral").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            dialog.dismiss()

        }

    }

    @SuppressLint("NewApi")
    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var no = System.currentTimeMillis().toString()
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = "cognitive"
        var mj_inji_1 = ""
        var mj_inji_2 = ""
        var mj_inji_3 = ""
        var mj_inji_4 = ""
        var mj_inji_5 = ""
        var mj_inji_6 = ""
        var mj_inji_7 = ""
        var mj_inji_8 = ""
        var mj_inji_9 = ""
        var mj_inji_10 = ""
        var mj_inji_11 = ""
        var mj_inji_12 = ""
        var mj_inji_13 = ""
        var mj_inji_14 = ""
        var mj_inji_15 = ""
        var mj_inji_sum = ""


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

        if(cognitive_1_1.isChecked){
            mj_inji_1 = "1"
        }else if(cognitive_1_2.isChecked){
            mj_inji_1 = "2"
        }else if(cognitive_1_3.isChecked){
            mj_inji_1 = "3"
        }else{
            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cognitive_2_1.isChecked){
            mj_inji_2 = "1"
        }else if(cognitive_2_2.isChecked){
            mj_inji_2 = "2"
        }else if(cognitive_2_3.isChecked){
            mj_inji_2 = "3"
        }else{
            Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_3_1.isChecked){
            mj_inji_3 = "1"
        }else if(cognitive_3_2.isChecked){
            mj_inji_3 = "2"
        }else if(cognitive_3_3.isChecked){
            mj_inji_3 = "3"
        }else{
            Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_4_1.isChecked){
            mj_inji_4 = "1"
        }else if(cognitive_4_2.isChecked){
            mj_inji_4 = "2"
        }else if(cognitive_4_3.isChecked){
            mj_inji_4 = "3"
        }else{
            Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_5_1.isChecked){
            mj_inji_5 = "1"
        }else if(cognitive_5_2.isChecked){
            mj_inji_5 = "2"
        }else if(cognitive_5_3.isChecked){
            mj_inji_5 = "3"
        }else{
            Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_6_1.isChecked){
            mj_inji_6 = "1"
        }else if(cognitive_6_2.isChecked){
            mj_inji_6 = "2"
        }else if(cognitive_6_3.isChecked){
            mj_inji_6 = "3"
        }else{
            Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_7_1.isChecked){
            mj_inji_7 = "1"
        }else if(cognitive_7_2.isChecked){
            mj_inji_7 = "2"
        }else if(cognitive_7_3.isChecked){
            mj_inji_7 = "3"
        }else{
            Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_8_1.isChecked){
            mj_inji_8 = "1"
        }else if(cognitive_8_2.isChecked){
            mj_inji_8 = "2"
        }else if(cognitive_8_3.isChecked){
            mj_inji_8 = "3"
        }else{
            Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_9_1.isChecked){
            mj_inji_9 = "1"
        }else if(cognitive_9_2.isChecked){
            mj_inji_9 = "2"
        }else if(cognitive_9_3.isChecked){
            mj_inji_9 = "3"
        }else{
            Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_10_1.isChecked){
            mj_inji_10 = "1"
        }else if(cognitive_10_2.isChecked){
            mj_inji_10 = "2"
        }else if(cognitive_10_3.isChecked){
            mj_inji_10 = "3"
        }else{
            Toast.makeText(this, "10번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cognitive_11_1.isChecked){
            mj_inji_11 = "1"
        }else if(cognitive_11_2.isChecked){
            mj_inji_11 = "2"
        }else if(cognitive_11_3.isChecked){
            mj_inji_11 = "3"
        }else{
            Toast.makeText(this, "11번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_12_1.isChecked){
            mj_inji_12 = "1"
        }else if(cognitive_12_2.isChecked){
            mj_inji_12 = "2"
        }else if(cognitive_12_3.isChecked){
            mj_inji_12 = "3"
        }else{
            Toast.makeText(this, "12번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cognitive_13_1.isChecked){
            mj_inji_13 = "1"
        }else if(cognitive_13_2.isChecked){
            mj_inji_13 = "2"
        }else if(cognitive_13_3.isChecked){
            mj_inji_13 = "3"
        }else{
            Toast.makeText(this, "13번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_14_1.isChecked){
            mj_inji_14 = "1"
        }else if(cognitive_14_2.isChecked){
            mj_inji_14 = "2"
        }else if(cognitive_14_3.isChecked){
            mj_inji_14 = "3"
        }else{
            Toast.makeText(this, "14번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cognitive_15_1.isChecked){
            mj_inji_15 = "1"
        }else if(cognitive_15_2.isChecked){
            mj_inji_15 = "2"
        }else if(cognitive_15_3.isChecked){
            mj_inji_15 = "3"
        }else{
            Toast.makeText(this, "15번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        var arr = ArrayList<CognitiveExaminationActivity.ExamInfo>()

        arr.add(CognitiveExaminationActivity.ExamInfo(
                exam_date, no, "", name, first_serial_text, last_serial_text, category,
                mj_inji_1, mj_inji_2, mj_inji_3, mj_inji_4, mj_inji_5, mj_inji_6, mj_inji_7, mj_inji_8, mj_inji_9,
                mj_inji_10, mj_inji_11, mj_inji_12, mj_inji_13, mj_inji_14, mj_inji_15, mj_inji_sum
                ))

        exam_result = arr

        return true

    }

}