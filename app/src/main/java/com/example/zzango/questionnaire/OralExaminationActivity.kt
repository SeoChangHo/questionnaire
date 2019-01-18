package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.Paper_ORAL
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_oral_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class OralExaminationActivity : RootActivity() {

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
        setContentView(R.layout.activity_oral_exam)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)// edittext 키보드 올라왔을때 화면 자동조정

        sql_db = LocalDBhelper(this).writableDatabase

        oral_9_count.setOnFocusChangeListener {

            v, hasFocus ->

            if(hasFocus){

                oral_9_etc.isChecked = true

            }

        }

        oral_examination_save.setOnClickListener {

            if(check()){

                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

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

        oral_edit_submit.setOnClickListener {

            finish()

        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper_ORAL

            GetPaper(paper)
        }else{
            name_edit.setText(MainActivity.login_user_name)
            first_serial.setText(MainActivity.user_first_serial)
            last_serial.setText(MainActivity.user_last_serial)
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    //뷰에 포커스를 총괄하는 메서드
    override fun focusControl(view : View){

        if(view !is EditText){

            if(view != oral_9_etc) {

                oral_exam_inside_scroll_layout.isFocusableInTouchMode = true

                //포커스 이동을 강제로 만들기 위함
                oral_exam_inside_scroll_layout.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, 0)

                //9번 라디오 그룹은 예외이기 때문에 9번 라디오 버튼들을 조건으로 놓음
                //횟수를 입력하는 마지막 에디트텍스트를 초기화 시킴
                if(view == oral_9_1 || view == oral_9_2 || view == oral_9_3 || view == oral_9_4 || view == oral_9_5){

                    oral_9_count.setText("")

                }

                oral_exam_inside_scroll_layout.isFocusableInTouchMode = false

            }else{

                oral_9_count.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(oral_9_count, 0)

            }

        }

    }

    override fun onBackPressed() {

        if(login_appbar_loading_progress.visibility != View.VISIBLE){

            super.onBackPressed()

        }

    }

    fun oral_exam_local_insert(){

        LocalDBhelper(this).oralCreate(sql_db)

        LocalDBhelper(this).oralSaveLocal(sql_db!!, exam_result!!)

        saveCompleteAlert()

    }

    fun oral_exam_server_insert(){

        this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        OracleUtill().oral_examination().oracleServer(exam_result!!).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE
                        this@OralExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Toast.makeText(this@OralExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        saveCompleteAlert()

                    }

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                login_appbar_loading_progress.visibility = View.GONE
                login_appbar_loading_progress_bg.visibility = View.GONE
                this@OralExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(this@OralExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }

        })

    }

    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@OralExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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

            startActivity(Intent(this@OralExaminationActivity, MainActivity::class.java).putExtra("from", "exam").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

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
        var category = "oral"
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

        }else if(oral_9_etc.isChecked) {

            oral_9 = oral_9_count.text.toString()

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
                exam_date, exam_no, "", name, first_serial_text, last_serial_text, category, oral_1, oral_2,
                oral_3, oral_4, oral_5, oral_6, oral_7, oral_8, oral_9, oral_10,
                oral_11, oral_12, oral_13, oral_14, oral_15, "", "", "", "", oral_20
        ))

        exam_result = arr

        return true

    }

    fun GetPaper(paper:Paper_ORAL)
    {
        name_edit.setText(paper.name)
        first_serial.setText(paper.first_serial)
        last_serial.setText(paper.last_serial)

        println(paper)

        oral_examination_save.visibility = View.GONE
        oral_examination_cancel.visibility = View.GONE
        oral_edit_submit.visibility = View.VISIBLE

        //1번
        if(paper.oral_1=="y")
        {
            oral_1_true.isChecked = true
        }
        else if(paper.oral_1=="n")
        {
            oral_1_false.isChecked = true
        }



        //2번
        if(paper.oral_2=="y")
        {
            oral_2_true.isChecked = true
        }
        else if(paper.oral_2=="n")
        {
            oral_2_false.isChecked = true
        }
        else if(paper.oral_2=="d")
        {
            oral_2_do_not_know.isChecked = true
        }


        //3번
        if(paper.oral_3=="y")
        {
            oral_3_true.isChecked = true
        }
        else if(paper.oral_3=="n")
        {
            oral_3_false.isChecked = true
        }
        else if(paper.oral_3=="d")
        {
            oral_3_do_not_know.isChecked = true
        }


        //4번
        if(paper.oral_4=="y")
        {
            oral_4_true.isChecked = true
        }
        else if(paper.oral_4=="n")
        {
            oral_4_false.isChecked = true
        }

        //5번
        if(paper.oral_5=="y")
        {
            oral_5_true.isChecked = true
        }
        else if(paper.oral_5=="n")
        {
            oral_5_false.isChecked = true
        }

        //6번
        if(paper.oral_6=="y")
        {
            oral_6_true.isChecked = true
        }
        else if(paper.oral_6=="n")
        {
            oral_6_false.isChecked = true
        }


        //7번
        if(paper.oral_7=="1")
        {
            oral_7_very_good.isChecked = true
        }
        else if(paper.oral_7=="2")
        {
            oral_7_good.isChecked = true
        }
        else if(paper.oral_7=="3")
        {
            oral_7_normal.isChecked = true
        }
        else if(paper.oral_7=="4")
        {
            oral_7_bad.isChecked = true
        }
        else if(paper.oral_7=="5")
        {
            oral_7_very_bad.isChecked = true
        }


        //8번
        if(paper.oral_8=="y")
        {
            oral_8_true.isChecked = true
        }
        else if(paper.oral_8=="n")
        {
            oral_8_false.isChecked = true
        }


        //9번
        if(paper.oral_9=="1")
        {
            oral_9_1.isChecked = true
        }
        else if(paper.oral_9=="2")
        {
            oral_9_2.isChecked = true
        }
        else if(paper.oral_9=="3")
        {
            oral_9_3.isChecked = true
        }
        else if(paper.oral_9=="4")
        {
            oral_9_4.isChecked = true
        }
        else if(paper.oral_9=="5")
        {
            oral_9_5.isChecked = true
        }
        else if(paper.oral_9=="6")
        {
            oral_9_etc.isChecked = true
        }


        //10번
        if(paper.oral_10=="1")
        {
            oral_10_1.isChecked = true
        }
        else if(paper.oral_10=="2")
        {
            oral_10_2.isChecked = true
        }
        else if(paper.oral_10=="3")
        {
            oral_10_3.isChecked = true
        }
        else if(paper.oral_10=="4")
        {
            oral_10_4.isChecked = true
        }




        //11번
        if(paper.oral_11=="1")
        {
            oral_11_1.isChecked = true
        }
        else if(paper.oral_11=="2")
        {
            oral_11_2.isChecked = true
        }
        else if(paper.oral_11=="3")
        {
            oral_11_3.isChecked = true
        }
        else if(paper.oral_11=="4")
        {
            oral_11_4.isChecked = true
        }
        else if(paper.oral_11=="5")
        {
            oral_11_5.isChecked = true
        }


        //12번
        if(paper.oral_12=="y")
        {
            oral_12_true.isChecked = true
        }
        else if(paper.oral_12=="n")
        {
            oral_12_false.isChecked = true
        }
        else if(paper.oral_12=="d")
        {
            oral_12_do_not_know.isChecked = true
        }


        //13번
        if(paper.oral_13=="1")
        {
            oral_13_1.isChecked = true
        }
        else if(paper.oral_13=="2")
        {
            oral_13_2.isChecked = true
        }
        else if(paper.oral_13=="3")
        {
            oral_13_3.isChecked = true
        }
        else if(paper.oral_13=="4")
        {
            oral_13_4.isChecked = true
        }
        else if(paper.oral_13=="5")
        {
            oral_13_5.isChecked = true
        }



        //14번
        if(paper.oral_14=="1")
        {
            oral_14_1.isChecked = true
        }
        else if(paper.oral_14=="2")
        {
            oral_14_2.isChecked = true
        }
        else if(paper.oral_14=="3")
        {
            oral_14_3.isChecked = true
        }
        else if(paper.oral_14=="4")
        {
            oral_14_4.isChecked = true
        }
        else if(paper.oral_14=="5")
        {
            oral_14_5.isChecked = true
        }


        //15번
        if(paper.oral_15=="y")
        {
            oral_15_true.isChecked = true
        }
        else if(paper.oral_15=="n")
        {
            oral_15_false.isChecked = true
        }
        else if(paper.oral_15=="d")
        {
            oral_15_do_not_know.isChecked = true
        }

        remark_content.setText(paper.remark)

    }

}
