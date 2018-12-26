package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.Paper
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_oral_examination.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import kotlinx.android.synthetic.main.save_location.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class OralExamination : AppCompatActivity()/*, View.OnTouchListener*/ {

    var exam_result : ArrayList<ExamInfo>? = null
//    var exam_result : HashMap<String, ExamInfo>? = null
    var sql_db : SQLiteDatabase? = null
    var popup = false

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : Date,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("exam_1") @Expose var exam_1 : String,
                         @SerializedName("exam_2") @Expose var exam_2 : String,
                         @SerializedName("exam_3") @Expose var exam_3 : String,
                         @SerializedName("exam_4") @Expose var exam_4 : String,
                         @SerializedName("exam_5") @Expose var exam_5 : String,
                         @SerializedName("exam_6") @Expose var exam_6 : String,
                         @SerializedName("exam_7") @Expose var exam_7 : String,
                         @SerializedName("exam_8") @Expose var exam_8 : String,
                         @SerializedName("exam_9") @Expose var exam_9 : String,
                         @SerializedName("exam_10") @Expose var exam_10 : String,
                         @SerializedName("exam_11") @Expose var exam_11 : String,
                         @SerializedName("exam_12") @Expose var exam_12 : String,
                         @SerializedName("exam_13") @Expose var exam_13 : String,
                         @SerializedName("exam_14") @Expose var exam_14 : String,
                         @SerializedName("exam_15") @Expose var exam_15 : String,
                         @SerializedName("exam_16") @Expose var exam_16 : String,
                         @SerializedName("exam_17") @Expose var exam_17 : String,
                         @SerializedName("exam_18") @Expose var exam_18 : String,
                         @SerializedName("exam_19") @Expose var exam_19 : String,
                         @SerializedName("exam_20") @Expose var exam_20 : String)

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oral_examination)



        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper
            println("작성자는 "+paper.name+"입니다.")

            oral_questionnaire_name_input.setText(paper.name)
            first_serial.setText(paper.serial_first)
            last_serial.setText(paper.serial_last)
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)// edittext 키보드 올라왔을때 화면 자동조정

        sql_db = LocalDBhelper(this).writableDatabase

        oral_exam_inside_scroll_layout.focusable = ViewGroup.FOCUSABLE
        oral_exam_inside_scroll_layout.isFocusableInTouchMode = true

        oral_exam_inside_scroll_layout.setOnTouchListener {

            v, event ->

            oral_exam_inside_scroll_layout.requestFocus()

            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, 0)

            true

        }

        oral_9_count.setOnFocusChangeListener {

            v, hasFocus ->

            if(hasFocus){

                oral_9_etc.isChecked = true

            }else{

                oral_9_etc.isChecked = false

            }

        }

        oral_9_etc.setOnCheckedChangeListener {

            buttonView, isChecked ->

            if(!isChecked){

                oral_9_count.text = null
                oral_9_count.clearFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, 0)

            }else{

                oral_9_count.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(oral_9_count, 0)

            }

        }

        oral_examination_save.setOnClickListener {

            if(check()){

                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    oral_exam_local_insert()

                }else{

                    oral_exam_server_insert()

                }

            }

        }

        oral_examination_cancel.setOnClickListener {

            finish()

        }

//        check_local_save.setOnClickListener {
//
//            local_data_load()
//
//        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if(ev!!.action == MotionEvent.ACTION_DOWN) {

            if (currentFocus!! !is EditText) {

                currentFocus!!.requestFocus()

                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, 0)

            }

        }

        return super.dispatchTouchEvent(ev)

    }

//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//
//
//
//    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//
//        println("it work")
//
//        return super.onTouchEvent(event)
//
//    }

//    fun local_data_load(){
//
//        LocalDBhelper(this).checkLocal(sql_db!!)
//
//    }

    fun oral_exam_local_insert(){

        println("로컬")

        LocalDBhelper(this).onCreate(sql_db)

        LocalDBhelper(this).saveLocal(sql_db!!, exam_result!!)

        saveCompleteAlert()

    }

    fun oral_exam_server_insert(){

        println("서버")

        OracleUtill().oral_examination().oracleServer(exam_result!!).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        println(response.body())
                        Toast.makeText(this@OralExamination, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        saveCompleteAlert()

                    }

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                Toast.makeText(this@OralExamination, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }

        })

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

        var exam_date = Date()
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = "oral"
        var exam_1 = ""
        var exam_2 = ""
        var exam_3 = ""
        var exam_4 = ""
        var exam_5 = ""
        var exam_6 = ""
        var exam_7 = ""
        var exam_8 = ""
        var exam_9 = ""
        var exam_10 = ""
        var exam_11 = ""
        var exam_12 = ""
        var exam_13 = ""
        var exam_14 = ""
        var exam_15 = ""
        var exam_20 = ""

        if(!oral_questionnaire_name_input.text.isNullOrEmpty()){

            name = oral_questionnaire_name_input.text.toString()

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

        if(oral_1_true.isChecked) {

            exam_1 = "y"

        }else if(oral_1_false.isChecked){

            exam_1 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_2_true.isChecked) {

            exam_2 = "y"

        }else if(oral_2_false.isChecked){

            exam_2 = "n"

        }else if(oral_2_do_not_know.isChecked){

            exam_2 = "d"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_3_true.isChecked) {

            exam_3 = "y"

        }else if(oral_3_false.isChecked){

            exam_3 = "n"

        }else if(oral_3_do_not_know.isChecked){

            exam_3 = "d"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_4_true.isChecked) {

            exam_4 = "y"

        }else if(oral_4_false.isChecked){

            exam_4 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_5_true.isChecked) {

            exam_5 = "y"

        }else if(oral_5_false.isChecked){

            exam_5 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_6_true.isChecked) {

            exam_6 = "y"

        }else if(oral_6_false.isChecked){

            exam_6 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_7_very_good.isChecked) {

            exam_7 = "5"

        }else if(oral_7_good.isChecked){

            exam_7 = "4"

        }else if(oral_7_normal.isChecked){

            exam_7 = "3"

        }else if(oral_7_bad.isChecked){

            exam_7 = "2"

        }else if(oral_7_very_bad.isChecked){

            exam_7 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_8_true.isChecked) {

            exam_8 = "y"

        }else if(oral_8_false.isChecked){

            exam_8 = "n"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_9_5.isChecked) {

            exam_9 = "5"

        }else if(oral_9_4.isChecked){

            exam_9 = "4"

        }else if(oral_9_3.isChecked){

            exam_9 = "3"

        }else if(oral_9_2.isChecked){

            exam_9 = "2"

        }else if(oral_9_1.isChecked){

            exam_9 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_10_4.isChecked) {

            exam_10 = "4"

        }else if(oral_10_3.isChecked){

            exam_10 = "3"

        }else if(oral_10_2.isChecked){

            exam_10 = "2"

        }else if(oral_10_1.isChecked){

            exam_10 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_11_5.isChecked) {

            exam_11 = "5"

        }else if(oral_11_4.isChecked){

            exam_11 = "4"

        }else if(oral_11_3.isChecked){

            exam_11 = "3"

        }else if(oral_11_2.isChecked){

            exam_11 = "2"

        }else if(oral_11_1.isChecked){

            exam_11 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_12_true.isChecked) {

            exam_12 = "y"

        }else if(oral_12_false.isChecked){

            exam_12 = "n"

        }else if(oral_12_do_not_know.isChecked){

            exam_12 = "d"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_13_5.isChecked) {

            exam_13 = "5"

        }else if(oral_13_4.isChecked){

            exam_13 = "4"

        }else if(oral_13_3.isChecked){

            exam_13 = "3"

        }else if(oral_13_2.isChecked){

            exam_13 = "2"

        }else if(oral_13_1.isChecked){

            exam_13 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_14_5.isChecked) {

            exam_14 = "5"

        }else if(oral_14_4.isChecked){

            exam_14 = "4"

        }else if(oral_14_3.isChecked){

            exam_14 = "3"

        }else if(oral_14_2.isChecked){

            exam_14 = "2"

        }else if(oral_14_1.isChecked){

            exam_14 = "1"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_15_true.isChecked){

            exam_15 = "y"

        }else if(oral_15_false.isChecked){

            exam_15 = "n"

        }else if(oral_15_do_not_know.isChecked){

            exam_15 = "d"

        }else{

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(!remark_content.text.toString().isNullOrEmpty()){

            exam_20 = remark_content.text.toString()

        }else{

            exam_20 = ""

        }

        var arr = ArrayList<ExamInfo>()

        arr.add(ExamInfo(
                exam_date, "", "", name, first_serial_text, last_serial_text, category, exam_1, exam_2,
                exam_3, exam_4, exam_5, exam_6, exam_7, exam_8, exam_9, exam_10,
                exam_11, exam_12, exam_13, exam_14, exam_15, "", "", "", "", exam_20
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