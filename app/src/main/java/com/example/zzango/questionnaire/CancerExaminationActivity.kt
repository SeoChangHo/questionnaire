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


}