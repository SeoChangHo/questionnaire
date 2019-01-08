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
import com.example.zzango.questionnaire.LocalList.Paper_CANCER
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_cancer_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class CancerExaminationActivity : AppCompatActivity(){

    var exam_result : ArrayList<CancerExaminationActivity.ExamInfo>? = null
    var sql_db : SQLiteDatabase? = null
    var popup = false

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("ck1") @Expose var ck1 : String,
                         @SerializedName("ck1_1") @Expose var ck1_1 : String,
                         @SerializedName("ck2") @Expose var ck2 : String,
                         @SerializedName("ck2_1") @Expose var ck2_1 : String,
                         @SerializedName("c_fam_can1") @Expose var c_fam_can1 : String,
                         @SerializedName("c_fam_can1_rel1") @Expose var c_fam_can1_rel1 : String,
                         @SerializedName("c_fam_can2") @Expose var c_fam_can2 : String,
                         @SerializedName("c_fam_can1_rel2") @Expose var c_fam_can1_rel2 : String,
                         @SerializedName("c_fam_can3") @Expose var c_fam_can3 : String,
                         @SerializedName("c_fam_can1_rel3") @Expose var c_fam_can1_rel3 : String,
                         @SerializedName("c_fam_can4") @Expose var c_fam_can4 : String,
                         @SerializedName("c_fam_can1_rel4") @Expose var c_fam_can1_rel4 : String,
                         @SerializedName("c_fam_can5") @Expose var c_fam_can5 : String,
                         @SerializedName("c_fam_can1_rel5") @Expose var c_fam_can1_rel5 : String,
                         @SerializedName("c_fam_can6") @Expose var c_fam_can6 : String,
                         @SerializedName("c_fam_can1_rel6") @Expose var c_fam_can1_rel6 : String,
                         @SerializedName("ck4_1_1") @Expose var ck4_1_1 : String,
                         @SerializedName("ck4_1_2") @Expose var ck4_1_2 : String,
                         @SerializedName("ck4_2_1") @Expose var ck4_2_1 : String,
                         @SerializedName("ck4_3_1") @Expose var ck4_3_1 : String,
                         @SerializedName("ck4_3_2") @Expose var ck4_3_2 : String,
                         @SerializedName("ck4_3_3") @Expose var ck4_3_3 : String,
                         @SerializedName("ck4_4_1") @Expose var ck4_4_1 : String,
                         @SerializedName("ck4_5_1") @Expose var ck4_5_1 : String,
                         @SerializedName("ck5_1") @Expose var ck5_1 : String,
                         @SerializedName("ck5_2") @Expose var ck5_2 : String,
                         @SerializedName("ck5_3") @Expose var ck5_3 : String,
                         @SerializedName("ck5_4") @Expose var ck5_4 : String,
                         @SerializedName("ck5_5") @Expose var ck5_5 : String,
                         @SerializedName("ck5_6") @Expose var ck5_6 : String,
                         @SerializedName("ck6_1") @Expose var ck6_1 : String,
                         @SerializedName("ck6_2") @Expose var ck6_2 : String,
                         @SerializedName("ck6_3") @Expose var ck6_3 : String,
                         @SerializedName("ck6_4") @Expose var ck6_4 : String,
                         @SerializedName("ck6_5") @Expose var ck6_5 : String,
                         @SerializedName("ck6_6") @Expose var ck6_6 : String,
                         @SerializedName("ck7_1") @Expose var ck7_1 : String,
                         @SerializedName("ck7_2") @Expose var ck7_2 : String,
                         @SerializedName("ck7_3") @Expose var ck7_3 : String,
                         @SerializedName("ck7_4") @Expose var ck7_4 : String,
                         @SerializedName("ck7_5") @Expose var ck7_5 : String,
                         @SerializedName("ck7_6") @Expose var ck7_6 : String,
                         @SerializedName("ck8") @Expose var ck8 : String,
                         @SerializedName("ck8_1") @Expose var ck8_1 : String,
                         @SerializedName("ck9") @Expose var ck9 : String,
                         @SerializedName("ck9_1") @Expose var ck9_1 : String,
                         @SerializedName("ck10") @Expose var ck10 : String,
                         @SerializedName("ck11") @Expose var ck11 : String,
                         @SerializedName("ck12") @Expose var ck12 : String,
                         @SerializedName("ck13") @Expose var ck13 : String,
                         @SerializedName("ck14") @Expose var ck14 : String)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancer_exam)

        sql_db = LocalDBhelper(this).writableDatabase


        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper_CANCER

            GetPaper(paper)
        }else{
            name_edit.setText(MainActivity.login_user_name)
            first_serial.setText(MainActivity.user_first_serial)
            last_serial.setText(MainActivity.user_last_serial)
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////


        cancer_examination_save.setOnClickListener {

            if(check()){

                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    cancer_exam_local_insert()

                }else{

                    cancer_exam_server_insert()

                }

            }

        }

        cancer_examination_cancel.setOnClickListener {

            finish()

        }

        cancer_edit_submit.setOnClickListener {

            finish()

        }

    }

    override fun onBackPressed() {

        if(login_appbar_loading_progress.visibility != View.VISIBLE){

            super.onBackPressed()

        }

    }


    fun cancer_exam_local_insert(){

        println("로컬")

        LocalDBhelper(this).cancerCreate(sql_db)

        LocalDBhelper(this).cancerSaveLocal(sql_db!!, exam_result!!)

        saveCompleteAlert()

    }

    fun cancer_exam_server_insert(){

        println("서버")

        this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        OracleUtill().cancer_examination().cancerServer(exam_result!!).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE
                        this@CancerExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Toast.makeText(this@CancerExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        saveCompleteAlert()

                    }

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                login_appbar_loading_progress.visibility = View.GONE
                login_appbar_loading_progress_bg.visibility = View.GONE
                this@CancerExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(this@CancerExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }

        })

    }

    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@CancerExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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

            startActivity(Intent(this@CancerExaminationActivity, MainActivity::class.java).putExtra("from", "cancer").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            dialog.dismiss()

        }

    }

    @SuppressLint("NewApi")
    fun check() : Boolean {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = System.currentTimeMillis().toString()
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = "cancer"
        var ck1 = ""
        var ck1_1 = ""
        var ck2 = ""
        var ck2_1 = ""
        var c_fam_can1 = ""
        var c_fam_can1_rel1 = ""
        var c_fam_can2 = ""
        var c_fam_can1_rel2 = ""
        var c_fam_can3 = ""
        var c_fam_can1_rel3 = ""
        var c_fam_can4 = ""
        var c_fam_can1_rel4 = ""
        var c_fam_can5 = ""
        var c_fam_can1_rel5 = ""
        var c_fam_can6 = ""
        var c_fam_can1_rel6 = ""
        var ck4_1_1 = ""
        var ck4_1_2 = ""
        var ck4_2_1 = ""
        var ck4_3_1 = ""
        var ck4_3_2 = ""
        var ck4_3_3 = ""
        var ck4_4_1 = ""
        var ck4_5_1 = ""
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
        var ck8 = ""
        var ck8_1 = ""
        var ck9 = ""
        var ck9_1 = ""
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
            c_fam_can1 = "1"
        }else if(cancer_3_1_2.isChecked){
            c_fam_can1 = "2"
        }else if(cancer_3_1_3.isChecked){
            c_fam_can1 = "3"
            if(cancer_3_1_checkBox1.isChecked){
                c_fam_can1_rel1 = "1"
            }else if(cancer_3_1_checkBox2.isChecked){
                c_fam_can1_rel1 = "2"
            }else if(cancer_3_1_checkBox3.isChecked){
                c_fam_can1_rel1 = "3"
            }else if(cancer_3_1_checkBox4.isChecked){
                c_fam_can1_rel1 = "4"
            }else if(cancer_3_1_checkBox5.isChecked){
                c_fam_can1_rel1 = "5"
            }else{
                Toast.makeText(this, "3-1번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else{
            Toast.makeText(this, "3-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_2_1.isChecked){
            c_fam_can2 = "1"
        }else if(cancer_3_2_2.isChecked){
            c_fam_can2 = "2"
        }else if(cancer_3_2_3.isChecked){
            c_fam_can2 = "3"
            if(cancer_3_2_checkBox1.isChecked){
                c_fam_can1_rel2 = "1"
            }else if(cancer_3_2_checkBox2.isChecked){
                c_fam_can1_rel2 = "2"
            }else if(cancer_3_2_checkBox3.isChecked){
                c_fam_can1_rel2 = "3"
            }else if(cancer_3_2_checkBox4.isChecked){
                c_fam_can1_rel2 = "4"
            }else if(cancer_3_2_checkBox5.isChecked){
                c_fam_can1_rel2 = "5"
            }else{
                Toast.makeText(this, "3-2번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else{
            Toast.makeText(this, "3-2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_3_1.isChecked){
            c_fam_can3 = "1"
        }else if(cancer_3_3_2.isChecked){
            c_fam_can3 = "2"
        }else if(cancer_3_3_3.isChecked){
            c_fam_can3 = "3"
            if(cancer_3_3_checkBox1.isChecked){
                c_fam_can1_rel3 = "1"
            }else if(cancer_3_3_checkBox2.isChecked){
                c_fam_can1_rel3 = "2"
            }else if(cancer_3_3_checkBox3.isChecked){
                c_fam_can1_rel3 = "3"
            }else if(cancer_3_3_checkBox4.isChecked){
                c_fam_can1_rel3 = "4"
            }else if(cancer_3_3_checkBox5.isChecked){
                c_fam_can1_rel3 = "5"
            }else{
                Toast.makeText(this, "3-3번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else{
            Toast.makeText(this, "3-3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cancer_3_4_1.isChecked){
            c_fam_can4 = "1"
        }else if(cancer_3_4_2.isChecked){
            c_fam_can4 = "2"
        }else if(cancer_3_4_3.isChecked){
            c_fam_can4 = "3"
            if(cancer_3_4_checkBox1.isChecked){
                c_fam_can1_rel4 = "1"
            }else if(cancer_3_4_checkBox2.isChecked){
                c_fam_can1_rel4 = "2"
            }else if(cancer_3_4_checkBox3.isChecked){
                c_fam_can1_rel4 = "3"
            }else if(cancer_3_4_checkBox4.isChecked){
                c_fam_can1_rel4 = "4"
            }else if(cancer_3_4_checkBox5.isChecked){
                c_fam_can1_rel4 = "5"
            }else{
                Toast.makeText(this, "3-4번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else{
            Toast.makeText(this, "3-4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_5_1.isChecked){
            c_fam_can5 = "1"
        }else if(cancer_3_5_2.isChecked){
            c_fam_can5 = "2"
        }else if(cancer_3_5_3.isChecked){
            c_fam_can5 = "3"
            if(cancer_3_5_checkBox1.isChecked){
                c_fam_can1_rel5 = "1"
            }else if(cancer_3_5_checkBox2.isChecked){
                c_fam_can1_rel5 = "2"
            }else if(cancer_3_5_checkBox3.isChecked){
                c_fam_can1_rel5 = "3"
            }else if(cancer_3_5_checkBox4.isChecked){
                c_fam_can1_rel5 = "4"
            }else if(cancer_3_5_checkBox5.isChecked){
                c_fam_can1_rel5 = "5"
            }else{
                Toast.makeText(this, "3-5번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else{
            Toast.makeText(this, "3-5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_6_1.isChecked){
            c_fam_can6 = "1"
        }else if(cancer_3_6_2.isChecked){
            c_fam_can6 = "2"
        }else if(cancer_3_6_3.isChecked){
            c_fam_can6 = "3"
            if(cancer_3_6_checkBox1.isChecked){
                c_fam_can1_rel6 = "1"
            }else if(cancer_3_6_checkBox2.isChecked){
                c_fam_can1_rel6 = "2"
            }else if(cancer_3_6_checkBox3.isChecked){
                c_fam_can1_rel6 = "3"
            }else if(cancer_3_6_checkBox4.isChecked){
                c_fam_can1_rel6 = "4"
            }else if(cancer_3_6_checkBox5.isChecked){
                c_fam_can1_rel6 = "5"
            }else{
                Toast.makeText(this, "3-6번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else{
            Toast.makeText(this, "3-6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_4_1_1.isChecked){
            ck4_1_1 = "1"
        }else if(cancer_4_1_2.isChecked){
            ck4_1_1 = "2"
        }else if(cancer_4_1_3.isChecked){
            ck4_1_1 = "3"
        }else if(cancer_4_1_4.isChecked){
            ck4_1_1 = "4"
        }

        if(cancer_4_2_1.isChecked){
            ck4_1_2 = "1"
        }else if(cancer_4_2_2.isChecked){
            ck4_1_2 = "2"
        }else if(cancer_4_2_3.isChecked){
            ck4_1_2 = "3"
        }else if(cancer_4_2_4.isChecked){
            ck4_1_2 = "4"
        }

        if(cancer_4_3_1.isChecked){
            ck4_2_1 = "1"
        }else if(cancer_4_3_2.isChecked){
            ck4_2_1 = "2"
        }else if(cancer_4_3_3.isChecked){
            ck4_2_1 = "3"
        }else if(cancer_4_3_4.isChecked){
            ck4_2_1 = "4"
        }

        if(cancer_4_4_1.isChecked){
            ck4_3_1 = "1"
        }else if(cancer_4_4_2.isChecked){
            ck4_3_1 = "2"
        }else if(cancer_4_4_3.isChecked){
            ck4_3_1 = "3"
        }else if(cancer_4_4_4.isChecked){
            ck4_3_1 = "4"
        }

        if(cancer_4_5_1.isChecked){
            ck4_3_2 = "1"
        }else if(cancer_4_5_2.isChecked){
            ck4_3_2 = "2"
        }else if(cancer_4_5_3.isChecked){
            ck4_3_2 = "3"
        }else if(cancer_4_5_4.isChecked){
            ck4_3_2 = "4"
        }


        if(cancer_4_6_1.isChecked){
            ck4_3_3 = "1"
        }else if(cancer_4_6_2.isChecked){
            ck4_3_3 = "2"
        }else if(cancer_4_6_3.isChecked){
            ck4_3_3 = "3"
        }else if(cancer_4_6_4.isChecked){
            ck4_3_3 = "4"
        }

        if(cancer_4_7_1.isChecked){
            ck4_4_1 = "1"
        }else if(cancer_4_7_2.isChecked){
            ck4_4_1 = "2"
        }else if(cancer_4_7_3.isChecked){
            ck4_4_1 = "3"
        }else if(cancer_4_7_4.isChecked){
            ck4_4_1 = "4"
        }

        if(cancer_4_8_1.isChecked){
            ck4_5_1 = "1"
        }else if(cancer_4_8_2.isChecked){
            ck4_5_1 = "2"
        }else if(cancer_4_8_3.isChecked){
            ck4_5_1 = "3"
        }else if(cancer_4_8_4.isChecked){
            ck4_5_1 = "4"
        }

        if(cancer_5_1_checkBox.isChecked){
            ck5_1 = "2"
        }else{
            ck5_1 = "1"
        }

        if(cancer_5_2_checkBox.isChecked){
            ck5_2 = "2"
        }else{
            ck5_2 = "1"
        }

        if(cancer_5_3_checkBox.isChecked){
            ck5_3 = "2"
        }else{
            ck5_3 = "1"
        }

        if(cancer_5_4_checkBox.isChecked){
            ck5_4 = "2"
        }else{
            ck5_4 = "1"
        }

        if(cancer_5_5_checkBox.isChecked){
            ck5_5 = "2"
        }else{
            ck5_5 = "1"
        }

        if(cancer_5_6_checkBox.isChecked){
            ck5_6 = "2"
        }else{
            ck5_6 = "1"
        }

        if(cancer_6_1_checkBox.isChecked){
            ck6_1 = "2"
        }else{
            ck6_1 = "1"
        }

        if(cancer_6_2_checkBox.isChecked){
            ck6_2 = "2"
        }else{
            ck6_2 = "1"
        }

        if(cancer_6_3_checkBox.isChecked){
            ck6_3 = "2"
        }else{
            ck6_3 = "1"
        }

        if(cancer_6_4_checkBox.isChecked){
            ck6_4 = "2"
        }else{
            ck6_4 = "1"
        }

        if(cancer_6_5_checkBox.isChecked){
            ck6_5 = "2"
        }else{
            ck6_5 = "1"
        }

        if(cancer_6_6_checkBox.isChecked){
            ck6_6 = "2"
        }else{
            ck6_6 = "1"
        }

        if(cancer_7_1_checkBox.isChecked){
            ck7_1 = "2"
        }else{
            ck7_1 = "1"
        }

        if(cancer_7_2_checkBox.isChecked){
            ck7_2 = "2"
        }else{
            ck7_2 = "1"
        }

        if(cancer_7_3_checkBox.isChecked){
            ck7_3 = "2"
        }else{
            ck7_3 = "1"
        }

        if(cancer_7_4_checkBox.isChecked){
            ck7_4 = "2"
        }else{
            ck7_4 = "1"
        }

        if(cancer_7_5_checkBox.isChecked){
            ck7_5 = "2"
        }else{
            ck7_5 = "1"
        }

        if(cancer_7_6_checkBox.isChecked){
            ck7_6 = "2"
        }else{
            ck7_6 = "1"
        }

        if(cancer_8_1.isChecked){
            ck8 = "1"
            if(!cancer_editText3.text.isNullOrEmpty()){
                ck8_1 = cancer_editText3.text.toString()
            }else{
                Toast.makeText(this, "8번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else if(cancer_8_2.isChecked){
            ck8 = "2"
        }else{
            Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_9_1.isChecked){
            ck9 = "1"
        }else if(cancer_9_2.isChecked){
            ck9 = "2"
        }else if(cancer_9_3.isChecked){
            ck9 = "3"
            if(!cancer_editText4.text.isNullOrEmpty()){
                ck9_1 = cancer_editText4.text.toString()
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
        }else{
            Toast.makeText(this, "14번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        var arr = ArrayList<CancerExaminationActivity.ExamInfo>()

        arr.add(CancerExaminationActivity.ExamInfo(
                exam_date, exam_no, "", name, first_serial_text, last_serial_text, category,
                ck1, ck1_1, ck2, ck2_1, c_fam_can1, c_fam_can1_rel1, c_fam_can2, c_fam_can1_rel2,
                c_fam_can3, c_fam_can1_rel3, c_fam_can4, c_fam_can1_rel4, c_fam_can5, c_fam_can1_rel5,
                c_fam_can6, c_fam_can1_rel6, ck4_1_1, ck4_1_2, ck4_2_1, ck4_3_1, ck4_3_2, ck4_3_3,
                ck4_4_1, ck4_5_1, ck5_1, ck5_2, ck5_3, ck5_4, ck5_5, ck5_6, ck6_1, ck6_2, ck6_3, ck6_4,
                ck6_5, ck6_6, ck7_1, ck7_2, ck7_3, ck7_4, ck7_5, ck7_6, ck8, ck8_1, ck9, ck9_1,
                ck10, ck11, ck12, ck13, ck14
        ))

        exam_result = arr

        return true

    }


    fun GetPaper(paper: Paper_CANCER) {


        name_edit.setText(paper.name)
        first_serial.setText(paper.first_serial)
        last_serial.setText(paper.last_serial)

        println(paper)

        cancer_examination_save.visibility = View.GONE
        cancer_examination_cancel.visibility = View.GONE
        cancer_edit_submit.visibility = View.VISIBLE


        if(paper.ck1 == "1"){
            cancer_1_1.isChecked = true
            if(paper.ck1_1.isNullOrEmpty()){
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
                cancer_editText2.setText(paper.ck2_1)
            }
        }


        if(paper.c_fam_can1 == "1"){
            cancer_3_1_1.isChecked = true

        }else if(paper.c_fam_can1 == "2"){
            cancer_3_1_2.isChecked = true

        }else if(paper.c_fam_can1 == "3"){
            cancer_3_1_3.isChecked = true

            cancer_3_1_checkBox1.visibility = View.VISIBLE
            cancer_3_1_checkBox2.visibility = View.VISIBLE
            cancer_3_1_checkBox3.visibility = View.VISIBLE
            cancer_3_1_checkBox4.visibility = View.VISIBLE
            cancer_3_1_checkBox5.visibility = View.VISIBLE

            if(paper.c_fam_can1_rel1 == "1"){
                cancer_3_1_checkBox1.isChecked = true
            }else if(paper.c_fam_can1_rel1 == "2"){
                cancer_3_1_checkBox2.isChecked = true
            }else if(paper.c_fam_can1_rel1 == "3"){
                cancer_3_1_checkBox3.isChecked = true
            }else if(paper.c_fam_can1_rel1 == "4"){
                cancer_3_1_checkBox4.isChecked = true
            }else if(paper.c_fam_can1_rel1 == "5"){
                cancer_3_1_checkBox5.isChecked = true
            }

        }

        if(paper.c_fam_can2 == "1"){
            cancer_3_2_1.isChecked = true

        }else if(paper.c_fam_can2 == "2"){
            cancer_3_2_2.isChecked = true

        }else if(paper.c_fam_can2 == "3"){
            cancer_3_2_3.isChecked = true

            cancer_3_2_checkBox1.visibility = View.VISIBLE
            cancer_3_2_checkBox2.visibility = View.VISIBLE
            cancer_3_2_checkBox3.visibility = View.VISIBLE
            cancer_3_2_checkBox4.visibility = View.VISIBLE
            cancer_3_2_checkBox5.visibility = View.VISIBLE

            if(paper.c_fam_can1_rel2 == "1"){
                cancer_3_2_checkBox1.isChecked = true
            }else if(paper.c_fam_can1_rel2 == "2"){
                cancer_3_2_checkBox2.isChecked = true
            }else if(paper.c_fam_can1_rel2 == "3"){
                cancer_3_2_checkBox3.isChecked = true
            }else if(paper.c_fam_can1_rel2 == "4"){
                cancer_3_2_checkBox4.isChecked = true
            }else if(paper.c_fam_can1_rel2 == "5"){
                cancer_3_2_checkBox5.isChecked = true
            }

        }

        if(paper.c_fam_can3 == "1"){
            cancer_3_3_1.isChecked = true

        }else if(paper.c_fam_can3 == "2"){
            cancer_3_3_2.isChecked = true

        }else if(paper.c_fam_can3 == "3"){
            cancer_3_3_3.isChecked = true

            cancer_3_3_checkBox1.visibility = View.VISIBLE
            cancer_3_3_checkBox2.visibility = View.VISIBLE
            cancer_3_3_checkBox3.visibility = View.VISIBLE
            cancer_3_3_checkBox4.visibility = View.VISIBLE
            cancer_3_3_checkBox5.visibility = View.VISIBLE

            if(paper.c_fam_can1_rel3 == "1"){
                cancer_3_3_checkBox1.isChecked = true
            }else if(paper.c_fam_can1_rel3 == "2"){
                cancer_3_3_checkBox2.isChecked = true
            }else if(paper.c_fam_can1_rel3 == "3"){
                cancer_3_3_checkBox3.isChecked = true
            }else if(paper.c_fam_can1_rel3 == "4"){
                cancer_3_3_checkBox4.isChecked = true
            }else if(paper.c_fam_can1_rel3 == "5"){
                cancer_3_3_checkBox5.isChecked = true
            }

        }

        if(paper.c_fam_can4 == "1"){
            cancer_3_4_1.isChecked = true

        }else if(paper.c_fam_can4 == "2"){
            cancer_3_4_2.isChecked = true

        }else if(paper.c_fam_can4 == "3"){
            cancer_3_4_3.isChecked = true

            cancer_3_4_checkBox1.visibility = View.VISIBLE
            cancer_3_4_checkBox2.visibility = View.VISIBLE
            cancer_3_4_checkBox3.visibility = View.VISIBLE
            cancer_3_4_checkBox4.visibility = View.VISIBLE
            cancer_3_4_checkBox5.visibility = View.VISIBLE

            if(paper.c_fam_can1_rel4 == "1"){
                cancer_3_4_checkBox1.isChecked = true
            }else if(paper.c_fam_can1_rel4 == "2"){
                cancer_3_4_checkBox2.isChecked = true
            }else if(paper.c_fam_can1_rel4 == "3"){
                cancer_3_4_checkBox3.isChecked = true
            }else if(paper.c_fam_can1_rel4 == "4"){
                cancer_3_4_checkBox4.isChecked = true
            }else if(paper.c_fam_can1_rel4 == "5"){
                cancer_3_4_checkBox5.isChecked = true
            }

        }

        if(paper.c_fam_can5 == "1"){
            cancer_3_5_1.isChecked = true

        }else if(paper.c_fam_can5 == "2"){
            cancer_3_5_2.isChecked = true

        }else if(paper.c_fam_can5 == "3"){
            cancer_3_5_3.isChecked = true

            cancer_3_5_checkBox1.visibility = View.VISIBLE
            cancer_3_5_checkBox2.visibility = View.VISIBLE
            cancer_3_5_checkBox3.visibility = View.VISIBLE
            cancer_3_5_checkBox4.visibility = View.VISIBLE
            cancer_3_5_checkBox5.visibility = View.VISIBLE

            if(paper.c_fam_can1_rel5 == "1"){
                cancer_3_5_checkBox1.isChecked = true
            }else if(paper.c_fam_can1_rel5 == "2"){
                cancer_3_5_checkBox2.isChecked = true
            }else if(paper.c_fam_can1_rel5 == "3"){
                cancer_3_5_checkBox3.isChecked = true
            }else if(paper.c_fam_can1_rel5 == "4"){
                cancer_3_5_checkBox4.isChecked = true
            }else if(paper.c_fam_can1_rel5 == "5"){
                cancer_3_5_checkBox5.isChecked = true
            }

        }


        if(paper.c_fam_can6 == "1"){
            cancer_3_6_1.isChecked = true

        }else if(paper.c_fam_can6 == "2"){
            cancer_3_6_2.isChecked = true

        }else if(paper.c_fam_can6 == "3"){
            cancer_3_6_3.isChecked = true

            cancer_3_6_checkBox1.visibility = View.VISIBLE
            cancer_3_6_checkBox2.visibility = View.VISIBLE
            cancer_3_6_checkBox3.visibility = View.VISIBLE
            cancer_3_6_checkBox4.visibility = View.VISIBLE
            cancer_3_6_checkBox5.visibility = View.VISIBLE

            if(paper.c_fam_can1_rel6 == "1"){
                cancer_3_6_checkBox1.isChecked = true
            }else if(paper.c_fam_can1_rel6 == "2"){
                cancer_3_6_checkBox2.isChecked = true
            }else if(paper.c_fam_can1_rel6 == "3"){
                cancer_3_6_checkBox3.isChecked = true
            }else if(paper.c_fam_can1_rel6 == "4"){
                cancer_3_6_checkBox4.isChecked = true
            }else if(paper.c_fam_can1_rel6 == "5"){
                cancer_3_6_checkBox5.isChecked = true
            }

        }


        if(paper.ck4_1_1 == "1"){
            cancer_4_1_1.isChecked = true
        }else if(paper.ck4_1_1 == "2"){
            cancer_4_1_2.isChecked = true
        }else if(paper.ck4_1_1 == "3"){
            cancer_4_1_3.isChecked = true
        }else if(paper.ck4_1_1 == "4"){
            cancer_4_1_4.isChecked = true
        }


        if(paper.ck4_1_2 == "1"){
            cancer_4_2_1.isChecked = true
        }else if(paper.ck4_1_2 == "2"){
            cancer_4_2_2.isChecked = true
        }else if(paper.ck4_1_2 == "3"){
            cancer_4_2_3.isChecked = true
        }else if(paper.ck4_1_2 == "4"){
            cancer_4_2_4.isChecked = true
        }


        if(paper.ck4_2_1 == "1"){
            cancer_4_3_1.isChecked = true
        }else if(paper.ck4_2_1 == "2"){
            cancer_4_3_2.isChecked = true
        }else if(paper.ck4_2_1 == "3"){
            cancer_4_3_3.isChecked = true
        }else if(paper.ck4_2_1 == "4"){
            cancer_4_3_4.isChecked = true
        }

        if(paper.ck4_3_1 == "1"){
            cancer_4_4_1.isChecked = true
        }else if(paper.ck4_3_1 == "2"){
            cancer_4_4_2.isChecked = true
        }else if(paper.ck4_3_1 == "3"){
            cancer_4_4_3.isChecked = true
        }else if(paper.ck4_3_1 == "4"){
            cancer_4_4_4.isChecked = true
        }


        if(paper.ck4_3_2 == "1"){
            cancer_4_5_1.isChecked = true
        }else if(paper.ck4_3_2 == "2"){
            cancer_4_5_2.isChecked = true
        }else if(paper.ck4_3_2 == "3"){
            cancer_4_5_3.isChecked = true
        }else if(paper.ck4_3_2 == "4"){
            cancer_4_5_4.isChecked = true
        }


        if(paper.ck4_3_3 == "1"){
            cancer_4_6_1.isChecked = true
        }else if(paper.ck4_3_3 == "2"){
            cancer_4_6_2.isChecked = true
        }else if(paper.ck4_3_3 == "3"){
            cancer_4_6_3.isChecked = true
        }else if(paper.ck4_3_3 == "4"){
            cancer_4_6_4.isChecked = true
        }

        if(paper.ck4_4_1 == "1"){
            cancer_4_7_1.isChecked = true
        }else if(paper.ck4_4_1 == "2"){
            cancer_4_7_2.isChecked = true
        }else if(paper.ck4_4_1 == "3"){
            cancer_4_7_3.isChecked = true
        }else if(paper.ck4_4_1 == "4"){
            cancer_4_7_4.isChecked = true
        }


        if(paper.ck4_5_1 == "1"){
            cancer_4_8_1.isChecked = true
        }else if(paper.ck4_5_1 == "2"){
            cancer_4_8_2.isChecked = true
        }else if(paper.ck4_5_1 == "3"){
            cancer_4_8_3.isChecked = true
        }else if(paper.ck4_5_1 == "4"){
            cancer_4_8_4.isChecked = true
        }

        if(paper.ck5_1 == "2"){
            cancer_5_1_checkBox.isChecked = true
        }
        if(paper.ck5_2 == "2"){
            cancer_5_2_checkBox.isChecked = true
        }
        if(paper.ck5_3 == "2"){
            cancer_5_3_checkBox.isChecked = true
        }
        if(paper.ck5_4 == "2"){
            cancer_5_4_checkBox.isChecked = true
        }
        if(paper.ck5_5 == "2"){
            cancer_5_5_checkBox.isChecked = true
        }
        if(paper.ck5_6 == "2"){
            cancer_5_6_checkBox.isChecked = true
        }


        if(paper.ck6_1 == "2"){
            cancer_6_1_checkBox.isChecked = true
        }
        if(paper.ck6_2 == "2"){
            cancer_6_2_checkBox.isChecked = true
        }
        if(paper.ck6_3 == "2"){
            cancer_6_3_checkBox.isChecked = true
        }
        if(paper.ck6_4 == "2"){
            cancer_6_4_checkBox.isChecked = true
        }
        if(paper.ck6_5 == "2"){
            cancer_6_5_checkBox.isChecked = true
        }
        if(paper.ck6_6 == "2"){
            cancer_6_6_checkBox.isChecked = true
        }

        if(paper.ck7_1 == "2"){
            cancer_7_1_checkBox.isChecked = true
        }
        if(paper.ck7_2 == "2"){
            cancer_7_2_checkBox.isChecked = true
        }
        if(paper.ck7_3 == "2"){
            cancer_7_3_checkBox.isChecked = true
        }
        if(paper.ck7_4 == "2"){
            cancer_7_4_checkBox.isChecked = true
        }
        if(paper.ck7_5 == "2"){
            cancer_7_5_checkBox.isChecked = true
        }
        if(paper.ck7_6 == "2"){
            cancer_7_6_checkBox.isChecked = true
        }



        if(paper.ck8 == "1"){
            cancer_8_1.isChecked = true
            if(paper.ck8_1.isNullOrEmpty()){
                cancer_editText3.setText(paper.ck8_1)
            }
        }else if(paper.ck8 == "2"){
            cancer_8_2.isChecked = true
        }

        if(paper.ck9 == "1"){
            cancer_9_1.isChecked = true
        }else if(paper.ck9 == "2"){
            cancer_9_2.isChecked = true
        }else if(paper.ck9 == "3"){
            cancer_9_3.isChecked = true
            if(paper.ck9_1.isNullOrEmpty()){
                cancer_editText4.setText(paper.ck9_1)
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