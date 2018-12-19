package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_oral_examination.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import kotlinx.android.synthetic.main.save_location.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class OralExamination : AppCompatActivity() {

    var exam_result : ArrayList<ExamInfo>? = null
//    var exam_result : HashMap<String, ExamInfo>? = null
    var sql_db : SQLiteDatabase? = null
    var popup = false

    data class ExamInfo (@SerializedName("oral_date") @Expose var oral_date : Date,
                         @SerializedName("oral_bun_no") @Expose var oral_bun_no : String,
                         @SerializedName("oral_email_yn") @Expose var oral_email_yn : String,
                         @SerializedName("oral_1") @Expose var oral_1 : String,
                         @SerializedName("oral_2") @Expose var oral_2 : String,
                         @SerializedName("oral_3") @Expose var oral_3 : String,
                         @SerializedName("oral_4") @Expose var oral_4 : String,
                         @SerializedName("oral_5") @Expose var oral_5 : String,
                         @SerializedName("oral_6") @Expose var oral_6 : String,
                         @SerializedName("oral_7") @Expose var oral_7 : String,
                         @SerializedName("oral_8") @Expose var oral_8 : String,
                         @SerializedName("oral_9") @Expose var oral_9 : String,
                         @SerializedName("oral_10") @Expose var oral_10 : String,
                         @SerializedName("oral_11") @Expose var oral_11 : String,
                         @SerializedName("oral_12") @Expose var oral_12 : String,
                         @SerializedName("oral_13") @Expose var oral_13 : String,
                         @SerializedName("oral_14") @Expose var oral_14 : String,
                         @SerializedName("oral_15") @Expose var oral_15 : String,
                         @SerializedName("oral_16") @Expose var oral_16 : String,
                         @SerializedName("oral_17") @Expose var oral_17 : String,
                         @SerializedName("oral_18") @Expose var oral_18 : String,
                         @SerializedName("oral_19") @Expose var oral_19 : String,
                         @SerializedName("oral_20") @Expose var oral_20 : String)

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oral_examination)

        sql_db = LocalDBhelper(this).writableDatabase

        oral_9_etc.setOnCheckedChangeListener {

            buttonView, isChecked ->

            if(!isChecked){

                oral_9_count.text = null
                oral_9_count.clearFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(oral_9_count.windowToken, 0)

            }else{

                oral_9_count.requestFocus()

            }

        }

        oral_examination_save.setOnClickListener {

            if(check()){

                saveAlert()

            }

        }

        oral_examination_cancel.setOnClickListener {

//            startActivity() 메인화면으로

        }

//        check_local_save.setOnClickListener {
//
//            local_data_load()
//
//        }

    }

//    fun local_data_load(){
//
//        LocalDBhelper(this).checkLocal(sql_db!!)
//
//    }

    fun oral_exam_local_insert(){

        if(check()) {

            LocalDBhelper(this).onCreate(sql_db)

            LocalDBhelper(this).saveLocal(sql_db!!, exam_result!!)

        }

    }

    fun oral_exam_server_insert(){

        OracleUtill().oral_examination().oracleServer(exam_result!!).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        println(response.body())
//                            Toast.makeText(this@OralExamination, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        Toast.makeText(this@OralExamination, "전송 완료", Toast.LENGTH_LONG).show()

                    }

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                Toast.makeText(this@OralExamination, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }

        })

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

            oral_exam_local_insert()

            saveCompleteAlert()

            dialog.dismiss()

        }

        dialog_view.server.setOnClickListener {

            oral_exam_server_insert()

            saveCompleteAlert()

            dialog.dismiss()

        }

    }

    fun saveCompleteAlert(){

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

            //            startActivity(Intent(this@OralExamination, /*메인화면 클래스*/).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            dialog.dismiss()

        }

    }

    @SuppressLint("NewApi")
    fun check() : Boolean{

        var oral_date = Date()
        var oral_bun_no = ""
        var oral_email_yn = ""
        var oral_1 = ""
        var oral_2 = ""
        var oral_3 = ""
        var oral_4 = ""
        var oral_5 = ""
        var oral_6 = ""
        var oral_7 = ""
        var oral_8 = ""
        var oral_9 = ""
        var oral_10 = ""
        var oral_11 = ""
        var oral_12 = ""
        var oral_13 = ""
        var oral_14 = ""
        var oral_15 = ""
        var oral_20 = ""

        if(oral_1_true.isChecked) {

            oral_1 = "y"

        }else if(oral_1_false.isChecked){

            oral_1 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_2_true.isChecked) {

            oral_2 = "y"

        }else if(oral_2_false.isChecked){

            oral_2 = "n"

        }else if(oral_2_do_not_know.isChecked){

            oral_2 = "d"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_3_true.isChecked) {

            oral_3 = "y"

        }else if(oral_3_false.isChecked){

            oral_3 = "n"

        }else if(oral_3_do_not_know.isChecked){

            oral_3 = "d"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_4_true.isChecked) {

            oral_4 = "y"

        }else if(oral_4_false.isChecked){

            oral_4 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_5_true.isChecked) {

            oral_5 = "y"

        }else if(oral_5_false.isChecked){

            oral_5 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_6_true.isChecked) {

            oral_6 = "y"

        }else if(oral_6_false.isChecked){

            oral_6 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_7_very_good.isChecked) {

            oral_7 = "5"

        }else if(oral_7_good.isChecked){

            oral_7 = "4"

        }else if(oral_7_normal.isChecked){

            oral_7 = "3"

        }else if(oral_7_bad.isChecked){

            oral_7 = "2"

        }else if(oral_7_very_bad.isChecked){

            oral_7 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_8_true.isChecked) {

            oral_8 = "y"

        }else if(oral_8_false.isChecked){

            oral_8 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_9_5.isChecked) {

            oral_9 = "5"

        }else if(oral_9_4.isChecked){

            oral_9 = "4"

        }else if(oral_9_3.isChecked){

            oral_9 = "3"

        }else if(oral_9_2.isChecked){

            oral_9 = "2"

        }else if(oral_9_1.isChecked){

            oral_9 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_10_4.isChecked) {

            oral_10 = "4"

        }else if(oral_10_3.isChecked){

            oral_10 = "3"

        }else if(oral_10_2.isChecked){

            oral_10 = "2"

        }else if(oral_10_1.isChecked){

            oral_10 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_11_5.isChecked) {

            oral_11 = "5"

        }else if(oral_11_4.isChecked){

            oral_11 = "4"

        }else if(oral_11_3.isChecked){

            oral_11 = "3"

        }else if(oral_11_2.isChecked){

            oral_11 = "2"

        }else if(oral_11_1.isChecked){

            oral_11 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_12_true.isChecked) {

            oral_12 = "y"

        }else if(oral_12_false.isChecked){

            oral_12 = "n"

        }else if(oral_12_do_not_know.isChecked){

            oral_12 = "d"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_13_5.isChecked) {

            oral_13 = "5"

        }else if(oral_13_4.isChecked){

            oral_13 = "4"

        }else if(oral_13_3.isChecked){

            oral_13 = "3"

        }else if(oral_13_2.isChecked){

            oral_13 = "2"

        }else if(oral_13_1.isChecked){

            oral_13 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_14_5.isChecked) {

            oral_14 = "5"

        }else if(oral_14_4.isChecked){

            oral_14 = "4"

        }else if(oral_14_3.isChecked){

            oral_14 = "3"

        }else if(oral_14_2.isChecked){

            oral_14 = "2"

        }else if(oral_14_1.isChecked){

            oral_14 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_15_true.isChecked){

            oral_15 = "y"

        }else if(oral_15_false.isChecked){

            oral_15 = "n"

        }else if(oral_15_do_not_know.isChecked){

            oral_15 = "d"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(!remark_content.text.toString().isNullOrEmpty()){

            oral_20 = remark_content.text.toString()

        }else{

            oral_20 = ""

        }

        var arr = ArrayList<ExamInfo>()

        arr.add(ExamInfo(
                oral_date, oral_bun_no, oral_email_yn, oral_1, oral_2,
                oral_3, oral_4, oral_5, oral_6, oral_7, oral_8, oral_9, oral_10,
                oral_11, oral_12, oral_13, oral_14, oral_15, "", "", "", "", oral_20
        ))

//        arr.add(ExamInfo(
//                Date(), "1", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n",
//                "n", "n", "n", "n", "n", "qwerty"
//        ))

        exam_result = arr

//        exam_result = HashMap()
//
//        exam_result!!["${oral_questionnaire_name_input.text}"] =
//
//                ExamInfo(
//                oral_date, oral_bun_no, oral_email_yn, oral_1, oral_2,
//                oral_3, oral_4, oral_5, oral_6, oral_7, oral_8, oral_9, oral_10,
//                oral_11, oral_12, oral_13, oral_14, oral_15, "", "", "", "", oral_20
//        )

        return true

    }

}