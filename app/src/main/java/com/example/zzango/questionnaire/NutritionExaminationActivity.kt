package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.activity_nutrition_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import java.text.SimpleDateFormat
import java.util.*

class NutritionExaminationActivity :AppCompatActivity() {

    var exam_result : ArrayList<ExamInfo>? = null
    var sql_db : SQLiteDatabase? = null
    var popup = false

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("sg2_spFood1") @Expose var sg2_spFood1_1 : String,
                         @SerializedName("sg2_spFood2") @Expose var sg2_spFood1_2 : String,
                         @SerializedName("sg2_spFood3") @Expose var sg2_spFood1_4 : String,
                         @SerializedName("sg2_spFood4") @Expose var sg2_spFood1_5 : String,
                         @SerializedName("sg2_spFood5") @Expose var sg2_spFood2_1 : String,
                         @SerializedName("sg2_spFood6") @Expose var sg2_spFood6 : String,
                         @SerializedName("sg2_spFood7") @Expose var sg2_spFood7 : String,
                         @SerializedName("sg2_spFood8") @Expose var sg2_spFood8 : String,
                         @SerializedName("sg2_spFood9") @Expose var sg2_spFood9 : String,
                         @SerializedName("sg2_spFood10") @Expose var sg2_spFood10 : String,
                         @SerializedName("sg2_spFood11") @Expose var sg2_spFood11 : String,
                         @SerializedName("sg2_spFoodSum") @Expose var sg2_spFoodSum : String,
                         @SerializedName("sg2_spFat1") @Expose var sg2_spFat1 : String,
                         @SerializedName("sg2_spFat2") @Expose var sg2_spFat2 : String,
                         @SerializedName("sg2_spFat3") @Expose var sg2_spFat3 : String)
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition_exam)

        nutrition_examination_cancel.setOnClickListener {

            finish()

        }

        nutrition_edit_submit.setOnClickListener {

            finish()

        }

    }

//    fun exercise_exam_local_insert(){
//
//        LocalDBhelper(this).exerciseCreate(sql_db)
//
//        LocalDBhelper(this).exerciseSaveLocal(sql_db!!, exam_result!!)
//
//        saveCompleteAlert()
//
//    }
//
//    fun exercise_exam_server_insert(){
//
//        this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//
//        OracleUtill().exercise_examination().exerciseServer(exam_result!!).enqueue(object : Callback<String> {
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//
//                if (response.isSuccessful) {
//
//                    if (!response.body()!!.equals("S")) {
//
//                        login_appbar_loading_progress.visibility = View.GONE
//                        login_appbar_loading_progress_bg.visibility = View.GONE
//                        this@ExerciseExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//                        Toast.makeText(this@ExerciseExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
//
//                    } else {
//
//                        saveCompleteAlert()
//
//                    }
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//
//                login_appbar_loading_progress.visibility = View.GONE
//                login_appbar_loading_progress_bg.visibility = View.GONE
//                this@ExerciseExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//                Toast.makeText(this@ExerciseExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
//                println(t.toString())
//            }
//
//        })
//
//    }
//
    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@NutritionExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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

            startActivity(Intent(this@NutritionExaminationActivity, MainActivity::class.java).putExtra("from", "exam").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

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
        var category = "nutrition"
        var sg2_spFood1 = ""
        var sg2_spFood2 = ""
        var sg2_spFood3 = ""
        var sg2_spFood4 = ""
        var sg2_spFood5 = ""
        var sg2_spFood6 = ""
        var sg2_spFood7 = ""
        var sg2_spFood8 = ""
        var sg2_spFood9 = ""
        var sg2_spFood10 = ""
        var sg2_spFood11 = ""
        var sg2_spFoodSum =""
        var sg2_spFat1 = ""
        var sg2_spFat2 = ""
        var sg2_spFat3 = ""

        if (!name_text.text.isNullOrEmpty()) {

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

        if(!last_serial.text.isNullOrEmpty()){

            last_serial_text = last_serial.text.toString()

        }else{

            Toast.makeText(this, "성명 또는 주민번호란을 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        sg2_spFood1 = when {
            nutrition_1_1.isChecked -> "1"
            nutrition_1_2.isChecked -> "2"
            nutrition_1_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood2 = when {
            nutrition_2_1.isChecked -> "1"
            nutrition_2_2.isChecked -> "2"
            nutrition_2_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood3 = when {
            nutrition_3_1.isChecked -> "1"
            nutrition_3_2.isChecked -> "2"
            nutrition_3_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood4 = when {
            nutrition_4_1.isChecked -> "1"
            nutrition_4_2.isChecked -> "2"
            nutrition_4_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood5 = when {
            nutrition_5_1.isChecked -> "1"
            nutrition_5_2.isChecked -> "2"
            nutrition_5_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood6 = when {
            nutrition_6_1.isChecked -> "1"
            nutrition_6_2.isChecked -> "2"
            nutrition_6_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood7 = when {
            nutrition_7_1.isChecked -> "1"
            nutrition_7_2.isChecked -> "2"
            nutrition_7_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood8 = when {
            nutrition_8_1.isChecked -> "1"
            nutrition_8_2.isChecked -> "2"
            nutrition_8_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood9 = when {
            nutrition_9_1.isChecked -> "1"
            nutrition_9_2.isChecked -> "2"
            nutrition_9_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood10 = when {
            nutrition_10_1.isChecked -> "1"
            nutrition_10_2.isChecked -> "2"
            nutrition_10_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFood11 = when {
            nutrition_11_1.isChecked -> "1"
            nutrition_11_2.isChecked -> "2"
            nutrition_11_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFat1 = when {
            fat_1_true.isChecked -> "1"
            fat_1_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFat2 = when {
            fat_2_no.isChecked -> "no"
            fat_2_1.isChecked -> "1"
            fat_2_2.isChecked -> "2"
            fat_2_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFat3 = when {
            fat_3_1.isChecked -> "1"
            fat_3_2.isChecked -> "2"
            fat_3_3.isChecked -> "3"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        return true

    }

}