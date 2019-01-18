package com.example.zzango.questionnaire

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
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.Paper_EXERCISE
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_exercise_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExerciseExaminationActivity : RootActivity() {

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
                         @SerializedName("sg2_spSports1_1") @Expose var sg2_spSports1_1 : String,
                         @SerializedName("sg2_spSports1_2") @Expose var sg2_spSports1_2 : String,
                         @SerializedName("sg2_spSports1_3_1") @Expose var sg2_spSports1_3_1 : String,
                         @SerializedName("sg2_spSports1_3_2") @Expose var sg2_spSports1_3_2 : String,
                         @SerializedName("sg2_spSports1_4") @Expose var sg2_spSports1_4 : String,
                         @SerializedName("sg2_spSports1_5") @Expose var sg2_spSports1_5 : String,
                         @SerializedName("sg2_spSports1_6_1") @Expose var sg2_spSports1_6_1 : String,
                         @SerializedName("sg2_spSports1_6_2") @Expose var sg2_spSports1_6_2 : String,
                         @SerializedName("sg2_spSports2_1") @Expose var sg2_spSports2_1 : String,
                         @SerializedName("sg2_spSports2_2") @Expose var sg2_spSports2_2 : String,
                         @SerializedName("sg2_spSports2_3_1") @Expose var sg2_spSports2_3_1 : String,
                         @SerializedName("sg2_spSports2_3_2") @Expose var sg2_spSports2_3_2 : String,
                         @SerializedName("sg2_spSports3_1") @Expose var sg2_spSports3_1 : String,
                         @SerializedName("sg2_spSports3_2") @Expose var sg2_spSports3_2 : String,
                         @SerializedName("sg2_spSports3_3_1") @Expose var sg2_spSports3_3_1 : String,
                         @SerializedName("sg2_spSports3_3_2") @Expose var sg2_spSports3_3_2 : String,
                         @SerializedName("sg2_spSports3_4") @Expose var sg2_spSports3_4 : String,
                         @SerializedName("sg2_spSports3_5") @Expose var sg2_spSports3_5 : String,
                         @SerializedName("sg2_spSports3_6_1") @Expose var sg2_spSports3_6_1 : String,
                         @SerializedName("sg2_spSports3_6_2") @Expose var sg2_spSports3_6_2 : String,
                         @SerializedName("sg2_spSports4_1_1") @Expose var sg2_spSports4_1_1 : String,
                         @SerializedName("sg2_spSports4_1_2") @Expose var sg2_spSports4_1_2 : String,
                         @SerializedName("sg2_spSports5") @Expose var sg2_spSports5 : String,
                         @SerializedName("sg2_spSports6") @Expose var sg2_spSports6 : String,
                         @SerializedName("sg2_spSports7") @Expose var sg2_spSports7 : String,
                         @SerializedName("sg2_spSports8") @Expose var sg2_spSports8 : String,
                         @SerializedName("sg2_spSports9") @Expose var sg2_spSports9 : String,
                         @SerializedName("sg2_spSports10") @Expose var sg2_spSports10 : String,
                         @SerializedName("sg2_spSports11") @Expose var sg2_spSports11 : String,
                         @SerializedName("sg2_spSports12") @Expose var sg2_spSports12 : String,
                         @SerializedName("sg2_spSportsSum") @Expose var sg2_spSportsSum : String)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_exam)


        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            var bmp: Bitmap = BitmapFactory.decodeByteArray(MainActivity.user_stream,0,MainActivity.user_stream!!.size)
            Signature.setImageBitmap(bmp)
        }

        sql_db = LocalDBhelper(this).writableDatabase

        name_edit.setText(MainActivity.login_user_name)
        first_serial.setText(MainActivity.user_first_serial)
        last_serial.setText(MainActivity.user_last_serial)

        exercise_1_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout)

        }

        exercise_2_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout2)

        }

        exercise_3_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout3)

        }

        exercise_4_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout4)

        }

        exercise_5_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, constraintLayout5)

        }

        exercise_examination_save.setOnClickListener {

            //check function 리턴하는 boolean 값에 따라 진행
            if(check()){

                //진행상태 표시
                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                //메인 액티비티에서 네트워크 연결상태가 문자열로 저장돼있다 그걸로 구분한다.
                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    //로컬 저장
                    exercise_exam_local_insert()

                }else{

                    //서버 저장
                    exercise_exam_server_insert()

                }

            }

        }

        exercise_examination_cancel.setOnClickListener {

            finish()

        }

        exercise_edit_submit.setOnClickListener {

            finish()

        }

        //spinner에 값을 넣는 adapter들
        pick_time.adapter =
                ArrayAdapter(this,
                        android.R.layout.simple_list_item_1,
                        arrayListOf("0일", "1일", "2일", "3일", "4일", "5일", "6일", "7일"))

        pick_time2.adapter =
                ArrayAdapter(this,
                        android.R.layout.simple_list_item_1,
                        arrayListOf("0일", "1일", "2일", "3일", "4일", "5일", "6일", "7일"))

        pick_time3.adapter =
                ArrayAdapter(this,
                        android.R.layout.simple_list_item_1,
                        arrayListOf("0일", "1일", "2일", "3일", "4일", "5일", "6일", "7일"))

        pick_time4.adapter =
                ArrayAdapter(this,
                        android.R.layout.simple_list_item_1,
                        arrayListOf("0일", "1일", "2일", "3일", "4일", "5일", "6일", "7일"))

        pick_time5.adapter =
                ArrayAdapter(this,
                        android.R.layout.simple_list_item_1,
                        arrayListOf("0일", "1일", "2일", "3일", "4일", "5일", "6일", "7일"))

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper_EXERCISE

            GetPaper(paper)

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    fun exercise_exam_local_insert(){

        LocalDBhelper(this).exerciseCreate(sql_db)

        LocalDBhelper(this).exerciseSaveLocal(sql_db!!, exam_result!!)

        saveCompleteAlert()

    }

    fun exercise_exam_server_insert(){

        this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        OracleUtill().exercise_examination().exerciseServer(exam_result!!).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    if (!response.body()!!.equals("S")) {

                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE
                        this@ExerciseExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Toast.makeText(this@ExerciseExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        saveCompleteAlert()

                    }

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                login_appbar_loading_progress.visibility = View.GONE
                login_appbar_loading_progress_bg.visibility = View.GONE
                this@ExerciseExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(this@ExerciseExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }

        })

    }

    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@ExerciseExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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

            startActivity(Intent(this@ExerciseExaminationActivity, MainActivity::class.java).putExtra("from", "exam").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

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
        var category = "exercise"
        var sg2_spSports1_1 = ""
        var sg2_spSports1_2 = ""
        var sg2_spSports1_3_1 = ""
        var sg2_spSports1_3_2 = ""
        var sg2_spSports1_4 = ""
        var sg2_spSports1_5 = ""
        var sg2_spSports1_6_1 = ""
        var sg2_spSports1_6_2 = ""
        var sg2_spSports2_1 = ""
        var sg2_spSports2_2 = ""
        var sg2_spSports2_3_1 = ""
        var sg2_spSports2_3_2 = ""
        var sg2_spSports3_1 = ""
        var sg2_spSports3_2 = ""
        var sg2_spSports3_3_1 = ""
        var sg2_spSports3_3_2 = ""
        var sg2_spSports3_4 = ""
        var sg2_spSports3_5 = ""
        var sg2_spSports3_6_1 = ""
        var sg2_spSports3_6_2 = ""
        var sg2_spSports4_1_1 = ""
        var sg2_spSports4_1_2 = ""
        var sg2_spSports5 = ""
        var sg2_spSports6 = ""
        var sg2_spSports7 = ""
        var sg2_spSports8 = ""
        var sg2_spSports9 = ""
        var sg2_spSports10 = ""
        var sg2_spSports11 = ""
        var sg2_spSports12 = ""
        var sg2_spSportsSum = ""

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

        sg2_spSports1_1 = when {
            exercise_1_true.isChecked -> "y"
            exercise_1_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        if (pick_time.selectedItem != null) {

            sg2_spSports1_2 = pick_time.selectedItemPosition.toString()

        } else {

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        sg2_spSports1_3_1 = if (!exercise_1_3_hour.text.isNullOrEmpty()) {

            exercise_1_3_hour.text.toString()

        } else if(exercise_1_3_hour.text.isEmpty()) {

            "0"

        } else {

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        sg2_spSports1_3_2 = when {
            exercise_1_3_minute.text != null -> exercise_1_3_minute.text.toString()
            exercise_1_3_minute.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports1_4 = when {
            exercise_2_true.isChecked -> "y"
            exercise_2_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        if (pick_time2.selectedItem != null) {

            sg2_spSports1_5 = pick_time2.selectedItemPosition.toString()

        } else {

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        sg2_spSports1_6_1 = when {
            !exercise_2_3_hour.text.isNullOrEmpty() -> exercise_2_3_hour.text.toString()
            exercise_2_3_hour.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports1_6_2 = when {
            !exercise_2_3_minute.text.isNullOrEmpty() -> exercise_2_3_minute.text.toString()
            exercise_2_3_minute.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports2_1 = when {
            exercise_3_true.isChecked -> "y"
            exercise_3_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        if (pick_time3.selectedItem != null) {

            sg2_spSports2_2 = pick_time3.selectedItemPosition.toString()

        } else {

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        sg2_spSports2_3_1 = when {
            !exercise_3_3_hour.text.isNullOrEmpty() -> exercise_3_3_hour.text.toString()
            exercise_3_3_hour.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports2_3_2 = when {
            !exercise_3_3_minute.text.isNullOrEmpty() -> exercise_3_3_minute.text.toString()
            exercise_3_3_minute.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports3_1 = when {
            exercise_4_true.isChecked -> "y"
            exercise_4_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        if (pick_time4.selectedItem != null) {

            sg2_spSports3_2 = pick_time4.selectedItemPosition.toString()

        } else {

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        sg2_spSports3_3_1 = when {
            !exercise_4_1_hour.text.isNullOrEmpty() -> exercise_4_1_hour.text.toString()
            exercise_4_1_hour.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports3_3_2 = when {
            !exercise_4_1_minute.text.isNullOrEmpty() -> exercise_4_1_minute.text.toString()
            exercise_4_1_minute.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports3_4 = when {
            exercise_5_true.isChecked -> "y"
            exercise_5_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        if (pick_time5.selectedItem != null) {

            sg2_spSports3_5 = pick_time5.selectedItemPosition.toString()

        } else {

            Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        sg2_spSports3_6_1 = when {
            !exercise_3_6_hour.text.isNullOrEmpty() -> exercise_3_6_hour.text.toString()
            exercise_3_6_hour.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports3_6_2 = when {
            !exercise_3_6_minute.text.isNullOrEmpty() -> exercise_3_6_minute.text.toString()
            exercise_3_6_minute.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports4_1_1 = when {
            !exercise_4_1_hour.text.isNullOrEmpty() -> exercise_4_1_hour.text.toString()
            exercise_4_1_hour.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports4_1_2 = when {
            exercise_4_1_minute.text !=null -> exercise_4_1_minute.text.toString()
            exercise_4_1_minute.text.isEmpty() -> "0"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports5 = when {
            exercise_6_no.isChecked -> "1"
            exercise_6_1.isChecked -> "2"
            exercise_6_2.isChecked -> "3"
            exercise_6_3.isChecked -> "4"
            exercise_6_4.isChecked -> "5"
            exercise_6_5.isChecked -> "6"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports6 = when {
            exercise_7_true.isChecked -> "y"
            exercise_7_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports7 = when {
            exercise_8_true.isChecked -> "y"
            exercise_8_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports8 = when {
            exercise_9_true.isChecked -> "y"
            exercise_9_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports9 = when {
            exercise_10_true.isChecked -> "y"
            exercise_10_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports10 = when {
            exercise_11_true.isChecked -> "y"
            exercise_11_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports11 = when {
            exercise_12_true.isChecked -> "y"
            exercise_12_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports12 = when {
            exercise_13_true.isChecked -> "y"
            exercise_13_false.isChecked -> "n"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        var arr = ArrayList<ExamInfo>()

        arr.add(ExamInfo(
                exam_date, exam_no, "", name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum
        ))

        exam_result = arr

        return true

    }

    fun GetPaper(paper:Paper_EXERCISE)
    {
        name_edit.setText(paper.name)
        first_serial.setText(paper.first_serial)
        last_serial.setText(paper.last_serial)

        println(paper)

        exercise_examination_save.visibility = View.GONE
        exercise_examination_cancel.visibility = View.GONE
        exercise_edit_submit.visibility = View.VISIBLE

        if(paper.sg2_spSports1_1=="y")
        {
            exercise_1_true.isChecked = true
        }
        else{
            exercise_1_false.isChecked = true
        }

        pick_time.setSelection(paper.sg2_spSports1_2.toInt())

        exercise_1_3_hour.setText(paper.sg2_spSports1_3_1)
        exercise_1_3_minute.setText(paper.sg2_spSports1_3_2)

        if(paper.sg2_spSports1_4=="y")
        {
            exercise_2_true.isChecked = true
        }
        else{
            exercise_2_false.isChecked = true
        }

        pick_time2.setSelection(paper.sg2_spSports1_5.toInt())

        exercise_1_6_hour.setText(paper.sg2_spSports1_6_1)
        exercise_1_6_minute.setText(paper.sg2_spSports1_6_2)

        if(paper.sg2_spSports2_1=="y"){

            exercise_3_true.isChecked = true

        }else{

            exercise_3_false.isChecked = true

        }

        pick_time3.setSelection(paper.sg2_spSports2_2.toInt())

        exercise_2_3_hour.setText(paper.sg2_spSports2_3_1)
        exercise_2_3_minute.setText(paper.sg2_spSports2_3_2)

        if(paper.sg2_spSports3_1=="y")
        {
            exercise_4_true.isChecked = true
        }
        else{
            exercise_4_false.isChecked = true
        }

        pick_time4.setSelection(paper.sg2_spSports3_2.toInt())

        exercise_3_3_hour.setText(paper.sg2_spSports3_3_1)
        exercise_3_3_minute.setText(paper.sg2_spSports3_3_2)

        if(paper.sg2_spSports3_4=="y")
        {
            exercise_5_true.isChecked = true
        }
        else
        {
            exercise_5_false.isChecked = true
        }



        pick_time5.setSelection(paper.sg2_spSports3_5.toInt())

        exercise_3_6_hour.setText(paper.sg2_spSports3_6_1)
        exercise_3_6_minute.setText(paper.sg2_spSports3_6_2)

        exercise_4_1_hour.setText(paper.sg2_spSports4_1_1)
        exercise_4_1_minute.setText(paper.sg2_spSports4_1_2)

        if(paper.sg2_spSports5=="1") {

            exercise_6_no.isChecked = true

        }else if(paper.sg2_spSports5=="2") {

            exercise_6_1.isChecked = true

        }else if(paper.sg2_spSports5=="3") {

            exercise_6_2.isChecked = true

        }else if(paper.sg2_spSports5=="4") {

            exercise_6_3.isChecked = true

        }else if(paper.sg2_spSports5=="5") {

            exercise_6_4.isChecked = true

        }else{

            exercise_6_5.isChecked = true

        }

        if(paper.sg2_spSports6=="y")
        {
            exercise_7_true.isChecked = true
        }
        else
        {
            exercise_7_false.isChecked = true
        }

        if(paper.sg2_spSports7=="y")
        {
            exercise_8_true.isChecked = true
        }
        else
        {
            exercise_8_false.isChecked = true
        }

        if(paper.sg2_spSports8=="y")
        {
            exercise_9_true.isChecked = true
        }
        else{
            exercise_9_false.isChecked = true
        }

        if(paper.sg2_spSports9=="y")
        {
            exercise_10_true.isChecked = true
        }
        else
        {
            exercise_10_false.isChecked = true
        }

        if(paper.sg2_spSports10=="y")
        {
            exercise_11_true.isChecked = true
        }
        else
        {
            exercise_11_false.isChecked = true
        }

        if(paper.sg2_spSports11=="y")
        {
            exercise_12_true.isChecked = true
        }
        else
        {
            exercise_12_false.isChecked = true
        }

        if(paper.sg2_spSports12=="y"){

            exercise_13_true.isChecked = true

        }else{

            exercise_13_false.isChecked = true

        }

    }

}