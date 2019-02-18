package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.PaperArray
import com.example.zzango.questionnaire.LocalList.Paper_EXERCISE
import com.example.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_exercise_exam.*
import kotlinx.android.synthetic.main.progressbar2.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class ExerciseExaminationActivity : RootActivity() {

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

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
                         @SerializedName("sg2_spSportsSum") @Expose var sg2_spSportsSum : String) : Serializable

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_exam)

        controlProgress(this, questionnaire_progress_wrapper, progress_constraintLayout, questionnaire_progress, progress_guideline, progress_guideline2, progress_guideline3, progress_guideline4, progress_guideline5, progress_guideline6, progress_guideline7, progress_guideline8)

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

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

            cancelAlert()

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

            try {
                var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature,0, paper.signature.size)

                Signature.setImageBitmap(bmp)

            }
            catch (e:Exception)
            {
                println(e.message)
            }

        }else{

            name_edit.text = MainActivity.login_user_name
            first_serial.text = MainActivity.user_first_serial
            last_serial.text = MainActivity.user_last_serial
            exercise_examination_save.text = "다음"

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    override fun onResume() {

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        super.onResume()

    }

    override fun onBackPressed() {

        if(login_appbar_loading_progress.visibility != View.VISIBLE){

            super.onBackPressed()

        }

    }

    fun exercise_exam_local_insert(){

        startActivity(Intent(this@ExerciseExaminationActivity, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun exercise_exam_server_insert() {

        startActivity(Intent(this@ExerciseExaminationActivity, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    @SuppressLint("NewApi")
    fun check() : Boolean {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
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
            exercise_1_true.isChecked -> "1"
            exercise_1_false.isChecked -> "2"
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
            exercise_2_true.isChecked -> "1"
            exercise_2_false.isChecked -> "2"
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
            exercise_3_true.isChecked -> "1"
            exercise_3_false.isChecked -> "2"
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
            exercise_4_true.isChecked -> "1"
            exercise_4_false.isChecked -> "2"
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
            exercise_5_true.isChecked -> "1"
            exercise_5_false.isChecked -> "2"
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
            exercise_7_true.isChecked -> "1"
            exercise_7_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports7 = when {
            exercise_8_true.isChecked -> "1"
            exercise_8_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports8 = when {
            exercise_9_true.isChecked -> "1"
            exercise_9_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports9 = when {
            exercise_10_true.isChecked -> "1"
            exercise_10_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports10 = when {
            exercise_11_true.isChecked -> "1"
            exercise_11_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports11 = when {
            exercise_12_true.isChecked -> "1"
            exercise_12_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spSports12 = when {
            exercise_13_true.isChecked -> "1"
            exercise_13_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        if(MainActivity.chart == "SET0"){
            PaperArray.PaperArrFunction.ArrayListInit()
            exam_no = System.currentTimeMillis().toString()
            MainActivity.exam_no = exam_no
        }else{
            exam_no = MainActivity.exam_no
        }

        PaperArray.PaperList.Arr_EXERCISE!!.add(Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum))


        PaperArray.PaperList.Arr_RESULT!!.add(PaperArray.PaperList.Arr_EXERCISE!!)

        return true

    }

    fun GetPaper(paper:Paper_EXERCISE)
    {

        cannotEditQuestionnaire(exercise_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        exercise_examination_save.visibility = View.GONE
        exercise_examination_cancel.visibility = View.GONE
        exercise_edit_submit.visibility = View.VISIBLE

        if(paper.sg2_spSports1_1=="1")
        {
            exercise_1_true.isChecked = true
        }
        else{
            exercise_1_false.isChecked = true
        }

        pick_time.setSelection(paper.sg2_spSports1_2.toInt())

        exercise_1_3_hour.setText(paper.sg2_spSports1_3_1)
        exercise_1_3_minute.setText(paper.sg2_spSports1_3_2)

        if(paper.sg2_spSports1_4=="1")
        {
            exercise_2_true.isChecked = true
        }
        else{
            exercise_2_false.isChecked = true
        }

        pick_time2.setSelection(paper.sg2_spSports1_5.toInt())

        exercise_1_6_hour.setText(paper.sg2_spSports1_6_1)
        exercise_1_6_minute.setText(paper.sg2_spSports1_6_2)

        if(paper.sg2_spSports2_1=="1"){

            exercise_3_true.isChecked = true

        }else{

            exercise_3_false.isChecked = true

        }

        pick_time3.setSelection(paper.sg2_spSports2_2.toInt())

        exercise_2_3_hour.setText(paper.sg2_spSports2_3_1)
        exercise_2_3_minute.setText(paper.sg2_spSports2_3_2)

        if(paper.sg2_spSports3_1=="1")
        {
            exercise_4_true.isChecked = true
        }
        else{
            exercise_4_false.isChecked = true
        }

        pick_time4.setSelection(paper.sg2_spSports3_2.toInt())

        exercise_3_3_hour.setText(paper.sg2_spSports3_3_1)
        exercise_3_3_minute.setText(paper.sg2_spSports3_3_2)

        if(paper.sg2_spSports3_4=="1")
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

        if(paper.sg2_spSports6=="1")
        {
            exercise_7_true.isChecked = true
        }
        else
        {
            exercise_7_false.isChecked = true
        }

        if(paper.sg2_spSports7=="1")
        {
            exercise_8_true.isChecked = true
        }
        else
        {
            exercise_8_false.isChecked = true
        }

        if(paper.sg2_spSports8=="1")
        {
            exercise_9_true.isChecked = true
        }
        else{
            exercise_9_false.isChecked = true
        }

        if(paper.sg2_spSports9=="1")
        {
            exercise_10_true.isChecked = true
        }
        else
        {
            exercise_10_false.isChecked = true
        }

        if(paper.sg2_spSports10=="1")
        {
            exercise_11_true.isChecked = true
        }
        else
        {
            exercise_11_false.isChecked = true
        }

        if(paper.sg2_spSports11=="1")
        {
            exercise_12_true.isChecked = true
        }
        else
        {
            exercise_12_false.isChecked = true
        }

        if(paper.sg2_spSports12=="1"){

            exercise_13_true.isChecked = true

        }else{

            exercise_13_false.isChecked = true

        }

    }

}