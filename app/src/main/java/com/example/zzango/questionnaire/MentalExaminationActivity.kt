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
import com.example.zzango.questionnaire.LocalList.Paper_MENTAL
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_mental_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MentalExaminationActivity : AppCompatActivity(){

    var exam_result : ArrayList<MentalExaminationActivity.ExamInfo>? = null
    var sql_db : SQLiteDatabase? = null
    var popup = false

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("mj_mtl_1") @Expose var mj_mtl_1 : String,
                         @SerializedName("mj_mtl_2") @Expose var mj_mtl_2 : String,
                         @SerializedName("mj_mtl_3") @Expose var mj_mtl_3 : String,
                         @SerializedName("mj_mtl_4") @Expose var mj_mtl_4 : String,
                         @SerializedName("mj_mtl_5") @Expose var mj_mtl_5 : String,
                         @SerializedName("mj_mtl_6") @Expose var mj_mtl_6 : String,
                         @SerializedName("mj_mtl_7") @Expose var mj_mtl_7 : String,
                         @SerializedName("mj_mtl_8") @Expose var mj_mtl_8 : String,
                         @SerializedName("mj_mtl_9") @Expose var mj_mtl_9 : String,
                         @SerializedName("mj_mtl_sum") @Expose var mj_mtl_sum : String)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mental_exam)

        sql_db = LocalDBhelper(this).writableDatabase

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper_MENTAL

            GetPaper(paper)
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        name_edit.setText(MainActivity.login_user_name)
        first_serial.setText(MainActivity.user_first_serial)
        last_serial.setText(MainActivity.user_last_serial)

        mental_examination_save.setOnClickListener {

            if(check()){

                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    mental_exam_local_insert()

                }else{

                    mental_exam_server_insert()

                }

            }

        }

        mental_examination_cancel.setOnClickListener {

            finish()

        }

        mental_edit_submit.setOnClickListener {

            finish()

        }


    }

    override fun onBackPressed() {

        if(login_appbar_loading_progress.visibility != View.VISIBLE){

            super.onBackPressed()

        }

    }

    fun mental_exam_local_insert(){

        println("로컬")

        LocalDBhelper(this).mentalCreate(sql_db)

        LocalDBhelper(this).mentalSaveLocal(sql_db!!, exam_result!!)

        saveCompleteAlert()

    }

    fun mental_exam_server_insert(){

        println("서버")

        this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        OracleUtill().mental_examination().mentalServer(exam_result!!).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE
                        this@MentalExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Toast.makeText(this@MentalExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        saveCompleteAlert()

                    }

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                login_appbar_loading_progress.visibility = View.GONE
                login_appbar_loading_progress_bg.visibility = View.GONE
                this@MentalExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(this@MentalExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }

        })

    }

    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@MentalExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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

            startActivity(Intent(this@MentalExaminationActivity, MainActivity::class.java).putExtra("from", "oral").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            dialog.dismiss()

        }

    }

    @SuppressLint("NewApi")
    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = System.currentTimeMillis().toString()
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = "mental"
        var mj_mtl_1 = ""
        var mj_mtl_2 = ""
        var mj_mtl_3 = ""
        var mj_mtl_4 = ""
        var mj_mtl_5 = ""
        var mj_mtl_6 = ""
        var mj_mtl_7 = ""
        var mj_mtl_8 = ""
        var mj_mtl_9 = ""
        var mj_mtl_sum = ""

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

        if(mental_1_1.isChecked){
            mj_mtl_1 = "1"
        }else if(mental_1_2.isChecked){
            mj_mtl_1 = "2"
        }else if(mental_1_3.isChecked){
            mj_mtl_1 = "3"
        }else if(mental_1_4.isChecked){
            mj_mtl_1 = "4"
        }else{
            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_2_1.isChecked){
            mj_mtl_2 = "1"
        }else if(mental_2_2.isChecked){
            mj_mtl_2 = "2"
        }else if(mental_2_3.isChecked){
            mj_mtl_2 = "3"
        }else if(mental_2_4.isChecked){
            mj_mtl_2 = "4"
        }else{
            Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_3_1.isChecked){
            mj_mtl_3 = "1"
        }else if(mental_3_2.isChecked){
            mj_mtl_3 = "2"
        }else if(mental_3_3.isChecked){
            mj_mtl_3 = "3"
        }else if(mental_3_4.isChecked){
            mj_mtl_3 = "4"
        }else{
            Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_4_1.isChecked){
            mj_mtl_4 = "1"
        }else if(mental_4_2.isChecked){
            mj_mtl_4 = "2"
        }else if(mental_4_3.isChecked){
            mj_mtl_4 = "3"
        }else if(mental_4_4.isChecked){
            mj_mtl_4 = "4"
        }else{
            Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_5_1.isChecked){
            mj_mtl_5 = "1"
        }else if(mental_5_2.isChecked){
            mj_mtl_5 = "2"
        }else if(mental_5_3.isChecked){
            mj_mtl_5 = "3"
        }else if(mental_5_4.isChecked){
            mj_mtl_5 = "4"
        }else{
            Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_6_1.isChecked){
            mj_mtl_6 = "1"
        }else if(mental_6_2.isChecked){
            mj_mtl_6 = "2"
        }else if(mental_6_3.isChecked){
            mj_mtl_6 = "3"
        }else if(mental_6_4.isChecked){
            mj_mtl_6 = "4"
        }else{
            Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_7_1.isChecked){
            mj_mtl_7 = "1"
        }else if(mental_7_2.isChecked){
            mj_mtl_7 = "2"
        }else if(mental_7_3.isChecked){
            mj_mtl_7 = "3"
        }else if(mental_7_4.isChecked){
            mj_mtl_7 = "4"
        }else{
            Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_8_1.isChecked){
            mj_mtl_8 = "1"
        }else if(mental_8_2.isChecked){
            mj_mtl_8 = "2"
        }else if(mental_8_3.isChecked){
            mj_mtl_8 = "3"
        }else if(mental_8_4.isChecked){
            mj_mtl_8 = "4"
        }else{
            Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_9_1.isChecked){
            mj_mtl_9 = "1"
        }else if(mental_9_2.isChecked){
            mj_mtl_9 = "2"
        }else if(mental_9_3.isChecked){
            mj_mtl_9 = "3"
        }else if(mental_9_4.isChecked){
            mj_mtl_9 = "4"
        }else{
            Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        var arr = ArrayList<ExamInfo>()

        arr.add(ExamInfo(
                exam_date, exam_no, "", name, first_serial_text, last_serial_text, category,
                mj_mtl_1, mj_mtl_2, mj_mtl_3, mj_mtl_4, mj_mtl_5, mj_mtl_6, mj_mtl_7, mj_mtl_8, mj_mtl_9, mj_mtl_sum
        ))

        exam_result = arr

        return true

    }


    fun GetPaper(paper:Paper_MENTAL){


        name_edit.setText(paper.name)
        first_serial.setText(paper.first_serial)
        last_serial.setText(paper.last_serial)

        println(paper)

        mental_examination_save.visibility = View.GONE
        mental_examination_cancel.visibility = View.GONE
        mental_edit_submit.visibility = View.VISIBLE


        if(paper.mj_mtl_1 == "1"){
            mental_1_1.isChecked = true
        }else if(paper.mj_mtl_1 == "2"){
            mental_1_2.isChecked = true
        }else if(paper.mj_mtl_1 == "3"){
            mental_1_3.isChecked = true
        }else if(paper.mj_mtl_1 == "4"){
            mental_1_4.isChecked = true
        }


        if(paper.mj_mtl_2 == "1"){
            mental_2_1.isChecked = true
        }else if(paper.mj_mtl_2 == "2"){
            mental_2_2.isChecked = true
        }else if(paper.mj_mtl_2 == "3"){
            mental_2_3.isChecked = true
        }else if(paper.mj_mtl_2 == "4"){
            mental_2_4.isChecked = true
        }


        if(paper.mj_mtl_3 == "1"){
            mental_3_1.isChecked = true
        }else if(paper.mj_mtl_3 == "2"){
            mental_3_2.isChecked = true
        }else if(paper.mj_mtl_3 == "3"){
            mental_3_3.isChecked = true
        }else if(paper.mj_mtl_3 == "4"){
            mental_3_4.isChecked = true
        }


        if(paper.mj_mtl_4 == "1"){
            mental_4_1.isChecked = true
        }else if(paper.mj_mtl_4 == "2"){
            mental_4_2.isChecked = true
        }else if(paper.mj_mtl_4 == "3"){
            mental_4_3.isChecked = true
        }else if(paper.mj_mtl_4 == "4"){
            mental_4_4.isChecked = true
        }


        if(paper.mj_mtl_5 == "1"){
            mental_5_1.isChecked = true
        }else if(paper.mj_mtl_5 == "2"){
            mental_5_2.isChecked = true
        }else if(paper.mj_mtl_5 == "3"){
            mental_5_3.isChecked = true
        }else if(paper.mj_mtl_5 == "4"){
            mental_5_4.isChecked = true
        }


        if(paper.mj_mtl_6 == "1"){
            mental_6_1.isChecked = true
        }else if(paper.mj_mtl_6 == "2"){
            mental_6_2.isChecked = true
        }else if(paper.mj_mtl_6 == "3"){
            mental_6_3.isChecked = true
        }else if(paper.mj_mtl_6 == "4"){
            mental_6_4.isChecked = true
        }


        if(paper.mj_mtl_7 == "1"){
            mental_7_1.isChecked = true
        }else if(paper.mj_mtl_7 == "2"){
            mental_7_2.isChecked = true
        }else if(paper.mj_mtl_7 == "3"){
            mental_7_3.isChecked = true
        }else if(paper.mj_mtl_7 == "4"){
            mental_7_4.isChecked = true
        }


        if(paper.mj_mtl_8 == "1"){
            mental_8_1.isChecked = true
        }else if(paper.mj_mtl_8 == "2"){
            mental_8_2.isChecked = true
        }else if(paper.mj_mtl_8 == "3"){
            mental_8_3.isChecked = true
        }else if(paper.mj_mtl_8 == "4"){
            mental_8_4.isChecked = true
        }


        if(paper.mj_mtl_9 == "1"){
            mental_9_1.isChecked = true
        }else if(paper.mj_mtl_9 == "2"){
            mental_9_2.isChecked = true
        }else if(paper.mj_mtl_9 == "3"){
            mental_9_3.isChecked = true
        }else if(paper.mj_mtl_9 == "4"){
            mental_9_4.isChecked = true
        }


    }

}