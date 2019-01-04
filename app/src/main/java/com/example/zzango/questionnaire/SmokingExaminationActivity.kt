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
import kotlinx.android.synthetic.main.activity_smoking_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SmokingExaminationActivity : AppCompatActivity(){

    var exam_result : ArrayList<SmokingExaminationActivity.ExamInfo>? = null
    var sql_db : SQLiteDatabase? = null
    var popup = false

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("sg2_spSmoke1") @Expose var sg2_spSmoke1 : String,
                         @SerializedName("sg2_spSmoke2") @Expose var sg2_spSmoke2 : String,
                         @SerializedName("sg2_spSmoke3") @Expose var sg2_spSmoke3 : String,
                         @SerializedName("sg2_spSmoke4") @Expose var sg2_spSmoke4 : String,
                         @SerializedName("sg2_spSmoke5") @Expose var sg2_spSmoke5 : String,
                         @SerializedName("sg2_spSmoke6") @Expose var sg2_spSmoke6 : String,
                         @SerializedName("sg2_spSmoke7") @Expose var sg2_spSmoke7 : String,
                         @SerializedName("sg2_spSmoke8") @Expose var sg2_spSmoke8 : String,
                         @SerializedName("sg2_spSmokeSum") @Expose var sg2_spSmokeSum : String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smoking_exam)

        sql_db = LocalDBhelper(this).writableDatabase

        smoking_examination_save.setOnClickListener {

            if(check()){

                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    smoking_exam_local_insert()

                }else{

                    smoking_exam_server_insert()

                }

            }

        }

        smoking_examination_cancel.setOnClickListener {

            finish()

        }

        smoking_edit_submit.setOnClickListener {

            finish()

        }

    }

    override fun onBackPressed() {

        if(login_appbar_loading_progress.visibility != View.VISIBLE){

            super.onBackPressed()

        }

    }


    fun smoking_exam_local_insert(){

        println("로컬")

        LocalDBhelper(this).smokingCreate(sql_db)

        LocalDBhelper(this).smokingSaveLocal(sql_db!!, exam_result!!)

        saveCompleteAlert()

    }

    fun smoking_exam_server_insert(){

        println("서버")

        this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        OracleUtill().smoking_examination().smokingServer(exam_result!!).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE
                        this@SmokingExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Toast.makeText(this@SmokingExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        saveCompleteAlert()

                    }

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                login_appbar_loading_progress.visibility = View.GONE
                login_appbar_loading_progress_bg.visibility = View.GONE
                this@SmokingExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(this@SmokingExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }

        })

    }

    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@SmokingExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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

            startActivity(Intent(this@SmokingExaminationActivity, MainActivity::class.java).putExtra("from", "oral").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

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
        var category = "smoking"
        var sg2_spSmoke1 = ""
        var sg2_spSmoke2 = ""
        var sg2_spSmoke3 = ""
        var sg2_spSmoke4 = ""
        var sg2_spSmoke5 = ""
        var sg2_spSmoke6 = ""
        var sg2_spSmoke7 = ""
        var sg2_spSmoke8 = ""
        var sg2_spSmokeSum = ""


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

        if(smoking_1_1.isChecked){
            sg2_spSmoke1 = "1"
        }else if(smoking_1_2.isChecked){
            sg2_spSmoke1 = "2"
        }else if(smoking_1_3.isChecked){
            sg2_spSmoke1 = "3"
        }else if(smoking_1_4.isChecked){
            sg2_spSmoke1 = "4"
        }

        if(smoking_2_1.isChecked){
            sg2_spSmoke2 = "1"
        }else if(smoking_2_2.isChecked){
            sg2_spSmoke2 = "2"
        }else if(smoking_2_3.isChecked){
            sg2_spSmoke2 = "3"
        }else if(smoking_2_4.isChecked){
            sg2_spSmoke2 = "4"
        }else if(smoking_2_5.isChecked){
            sg2_spSmoke2 = "5"
        }else if(smoking_2_6.isChecked){
            sg2_spSmoke2 = "6"
        }else if(smoking_2_7.isChecked){
            sg2_spSmoke2 = "7"
        }else if(smoking_2_8.isChecked){
            sg2_spSmoke2 = "8"
        }

        if(smoking_3_1.isChecked){
            sg2_spSmoke3 = "1"
        }else if(smoking_3_2.isChecked){
            sg2_spSmoke3 = "2"
        }else if(smoking_3_3.isChecked){
            sg2_spSmoke3 = "3"
        }else if(smoking_3_4.isChecked){
            sg2_spSmoke3 = "4"
        }


        if(smoking_4_1.isChecked){
            sg2_spSmoke4 = "1"
        }else if(smoking_4_2.isChecked){
            sg2_spSmoke4 = "2"
        }

        if(smoking_5_1.isChecked){
            sg2_spSmoke5 = "1"
        }else if(smoking_5_2.isChecked){
            sg2_spSmoke5 = "2"
        }

        if(smoking_6_1.isChecked){
            sg2_spSmoke6 = "1"
        }else if(smoking_6_2.isChecked){
            sg2_spSmoke6 = "2"
        }else if(smoking_6_3.isChecked){
            sg2_spSmoke6 = "3"
        }else if(smoking_6_4.isChecked){
            sg2_spSmoke6 = "4"
        }

        if(smoking_7_1.isChecked){
            sg2_spSmoke7 = "1"
        }else if(smoking_7_2.isChecked){
            sg2_spSmoke7 = "2"
        }

        if(smoking_8_1.isChecked){
            sg2_spSmoke8 = "1"
        }else if(smoking_8_2.isChecked){
            sg2_spSmoke8 = "2"
        }



        var arr = ArrayList<SmokingExaminationActivity.ExamInfo>()

        arr.add(SmokingExaminationActivity.ExamInfo(
                exam_date, exam_no, "", name, first_serial_text, last_serial_text, category,
                sg2_spSmoke1, sg2_spSmoke2, sg2_spSmoke3, sg2_spSmoke4, sg2_spSmoke5, sg2_spSmoke6,
                sg2_spSmoke7, sg2_spSmoke8, sg2_spSmokeSum
        ))

        exam_result = arr

        return true

    }

}