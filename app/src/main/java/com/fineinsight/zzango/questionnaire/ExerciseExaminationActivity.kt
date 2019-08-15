package com.fineinsight.zzango.questionnaire

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
import com.fineinsight.zzango.questionnaire.DataClass.PaperNameInfo
import com.fineinsight.zzango.questionnaire.DataClass.SavePaper
import com.fineinsight.zzango.questionnaire.DataClass.SavedListObject
import com.fineinsight.zzango.questionnaire.DataClass.ServerPaper_Life
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import com.fineinsight.zzango.questionnaire.LocalList.Paper_EXERCISE
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import kotlinx.android.synthetic.main.activity_exercise_exam.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("NewApi", "SimpleDateFormat")
class ExerciseExaminationActivity : RootActivity() {

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_exam)

        controlProgress(this)

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

//                login_appbar_loading_progress.visibility = View.VISIBLE
//                login_appbar_loading_progress_bg.visibility = View.VISIBLE

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

           if(intent.getSerializableExtra("paper") is Paper_EXERCISE) {

               var paper = intent.getSerializableExtra("paper") as Paper_EXERCISE

               GetPaper(paper)

               try {
                   var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)

                   Signature.setImageBitmap(bmp)

               } catch (e: Exception) {
                   println(e.message)
               }

               exercise_edit_submit.setOnClickListener {

                   finish()

               }

           }else{

               var paper = intent.getSerializableExtra("paper") as ServerPaper_Life

               GetPaper(paper)

               exercise_edit_submit.text = "다음"

               exercise_edit_submit.setOnClickListener {

                   exercise_exam_server_getPaper()
                   finish()

               }

           }

        }else{

            name_edit.text = MainActivity.login_user_name
            first_serial.text = MainActivity.user_first_serial
            last_serial.text = MainActivity.user_last_serial
            exercise_examination_save.text = "다음"

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //초기화 or 완료된 문진 액티비티로 다시 왔을때
        //표시되는 뷰 (수정가능)
        if(PaperArray.PaperList.Arr_EXERCISE != null && PaperArray.PaperList.Arr_EXERCISE!!.size != 0){

            whenTempLoad(PaperArray.PaperList.Arr_EXERCISE!![0])

        }else if (PaperArray.PaperList.temp_Arr_EXERCISE != null && PaperArray.PaperList.temp_Arr_EXERCISE!!.size != 0){

            whenTempLoad(PaperArray.PaperList.temp_Arr_EXERCISE!![0])

        }

    }

    override fun onResume() {

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        super.onResume()

    }

    override fun onBackPressed() {

        if(login_appbar_loading_progress.visibility != View.VISIBLE){

            cancelAlert()

        }

    }

    fun exercise_exam_local_insert(){

        startActivity(Intent(this@ExerciseExaminationActivity, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun exercise_exam_server_insert() {

        startActivity(Intent(this@ExerciseExaminationActivity, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun exercise_exam_server_getPaper(){

        startActivity(Intent(this@ExerciseExaminationActivity, NutritionExaminationActivity::class.java).putExtra("paper", intent.getSerializableExtra("paper") as ServerPaper_Life).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun whenTempSave() {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.EXERCISE.EN_NM
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

        }

        if (!first_serial.text.isNullOrEmpty()) {

            first_serial_text = first_serial.text.toString()

        }

        if(!last_serial.text.isNullOrEmpty()){

            last_serial_text = last_serial.text.toString()

        }

        sg2_spSports1_1 = when {
            exercise_1_true.isChecked -> "1"
            exercise_1_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time.selectedItem != null) {

            sg2_spSports1_2 = pick_time.selectedItemPosition.toString()

        }

        sg2_spSports1_3_1 = when {
            !exercise_1_3_hour.text.isNullOrEmpty() -> exercise_1_3_hour.text.toString()
            exercise_1_3_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports1_3_2 = when {
            exercise_1_3_minute.text != null -> exercise_1_3_minute.text.toString()
            exercise_1_3_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports1_4 = when {
            exercise_2_true.isChecked -> "1"
            exercise_2_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time2.selectedItem != null) {

            sg2_spSports1_5 = pick_time2.selectedItemPosition.toString()

        }

        sg2_spSports1_6_1 = when {
            !exercise_2_3_hour.text.isNullOrEmpty() -> exercise_2_3_hour.text.toString()
            exercise_2_3_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports1_6_2 = when {
            !exercise_2_3_minute.text.isNullOrEmpty() -> exercise_2_3_minute.text.toString()
            exercise_2_3_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports2_1 = when {
            exercise_3_true.isChecked -> "1"
            exercise_3_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time3.selectedItem != null) {

            sg2_spSports2_2 = pick_time3.selectedItemPosition.toString()

        }

        sg2_spSports2_3_1 = when {
            !exercise_3_3_hour.text.isNullOrEmpty() -> exercise_3_3_hour.text.toString()
            exercise_3_3_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports2_3_2 = when {
            !exercise_3_3_minute.text.isNullOrEmpty() -> exercise_3_3_minute.text.toString()
            exercise_3_3_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports3_1 = when {
            exercise_4_true.isChecked -> "1"
            exercise_4_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time4.selectedItem != null) {

            sg2_spSports3_2 = pick_time4.selectedItemPosition.toString()

        }

        sg2_spSports3_3_1 = when {
            !exercise_4_1_hour.text.isNullOrEmpty() -> exercise_4_1_hour.text.toString()
            exercise_4_1_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports3_3_2 = when {
            !exercise_4_1_minute.text.isNullOrEmpty() -> exercise_4_1_minute.text.toString()
            exercise_4_1_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports3_4 = when {
            exercise_5_true.isChecked -> "1"
            exercise_5_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time5.selectedItem != null) {

            sg2_spSports3_5 = pick_time5.selectedItemPosition.toString()

        }

        sg2_spSports3_6_1 = when {
            !exercise_3_6_hour.text.isNullOrEmpty() -> exercise_3_6_hour.text.toString()
            exercise_3_6_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports3_6_2 = when {
            !exercise_3_6_minute.text.isNullOrEmpty() -> exercise_3_6_minute.text.toString()
            exercise_3_6_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports4_1_1 = when {
            !exercise_4_1_hour.text.isNullOrEmpty() -> exercise_4_1_hour.text.toString()
            exercise_4_1_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports4_1_2 = when {
            exercise_4_1_minute.text !=null -> exercise_4_1_minute.text.toString()
            exercise_4_1_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports5 = when {
            exercise_6_no.isChecked -> "1"
            exercise_6_1.isChecked -> "2"
            exercise_6_2.isChecked -> "3"
            exercise_6_3.isChecked -> "4"
            exercise_6_4.isChecked -> "5"
            exercise_6_5.isChecked -> "6"
            else -> ""
        }

        sg2_spSports6 = when {
            exercise_7_true.isChecked -> "1"
            exercise_7_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports7 = when {
            exercise_8_true.isChecked -> "1"
            exercise_8_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports8 = when {
            exercise_9_true.isChecked -> "1"
            exercise_9_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports9 = when {
            exercise_10_true.isChecked -> "1"
            exercise_10_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports10 = when {
            exercise_11_true.isChecked -> "1"
            exercise_11_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports11 = when {
            exercise_12_true.isChecked -> "1"
            exercise_12_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports12 = when {
            exercise_13_true.isChecked -> "1"
            exercise_13_false.isChecked -> "2"
            else -> ""
        }

        if(MainActivity.chart.isEmpty()){
            if(!PaperArray.initCheck) {
                PaperArray.PaperArrFunction.ArrayListInit()
            }
            exam_no = System.currentTimeMillis().toString()
            MainActivity.exam_no = exam_no
        }else{
            exam_no = MainActivity.exam_no
        }

//        PaperArray.PaperArrFunction.ArrayListInit()
//        exam_no = System.currentTimeMillis().toString()

        if(PaperArray.PaperList.temp_Arr_EXERCISE!!.size < 1) {

            PaperArray.PaperList.temp_Arr_EXERCISE!!.add(Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                    sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                    sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                    sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                    sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                    sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum))

        }else{

            PaperArray.PaperList.temp_Arr_EXERCISE!!.removeAt(0)
            PaperArray.PaperList.temp_Arr_EXERCISE!!.add(Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                    sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                    sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                    sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                    sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                    sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum))

        }

    }

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

        if(MainActivity.chart.isEmpty()){
//            if(!PaperArray.initCheck) {
//                PaperArray.PaperArrFunction.ArrayListInit()
//            }
            exam_no = System.currentTimeMillis().toString()
            MainActivity.exam_no = exam_no
        }else{
            exam_no = MainActivity.exam_no
        }

        SavePaper.Total.Array[5] = Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum)
/*
        //해당 배열이 존재할경우
        //clear 후 다시 문항을 체크하고 배열을 add한다
        //저장된 해당문진에서 수정하는 경우 대비
        if(PaperArray.PaperList.Arr_EXERCISE != null && PaperArray.PaperList.Arr_EXERCISE!!.size >= 1) {

            PaperArray.PaperList.Arr_EXERCISE!!.clear()
            PaperArray.PaperList.Arr_EXERCISE!!.add(Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                    sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                    sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                    sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                    sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                    sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum))

            //iterator를 써야 오류가 안난다
            //java.util.ConcurrentModificationException
            //index 문제
            //증가된 index와 list의 size를 비교하는데 있어서 문제가 발생하게된다.
            var iter = PaperArray.PaperList.Arr_RESULT?.listIterator()

            //result 배열에서 paper_exercise인 인덱스를 삭제하고
            //새 paper_exercise배열을 다시 그 자리에 추가하는 반복문
           while(iter!!.hasNext()){
                var arr = iter.next()
                if((arr as ArrayList<*>)[0] is Paper_EXERCISE){
                    iter.remove()
                    iter.add(PaperArray.PaperList.Arr_EXERCISE!!)
                }
            }

            for ((i, arr) in PaperArray.PaperList.Arr_RESULT!!.withIndex()) {

                if ((arr as ArrayList<*>)[0] is Paper_EXERCISE) {

                    PaperArray.PaperList.Arr_RESULT!!.remove(i)
                    PaperArray.PaperList.Arr_RESULT!!.add(i, PaperArray.PaperList.Arr_EXERCISE!!)

                }

            }

        }else {

            PaperArray.PaperList.Arr_EXERCISE!!.add(Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                    sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                    sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                    sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                    sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                    sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum))

            SavePaper.Total.Array.add(Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                    sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                    sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                    sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                    sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                    sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum))

            PaperArray.PaperList.Arr_RESULT!!.add(PaperArray.PaperList.Arr_EXERCISE!!)
            PaperArray.PaperList.temp_Arr_EXERCISE = ArrayList()

        }
*/
        return true

    }

    //이미 저장된 배열이 있을경우 수정된 사항이 있는지 체크한다
    //해당 배열이 저장된 인덱스를 지우고 다시 수정된 값을 넣는다
    fun check2() {

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

        }

        if (!first_serial.text.isNullOrEmpty()) {

            first_serial_text = first_serial.text.toString()

        }

        if(!last_serial.text.isNullOrEmpty()){

            last_serial_text = last_serial.text.toString()

        }

        sg2_spSports1_1 = when {
            exercise_1_true.isChecked -> "1"
            exercise_1_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time.selectedItem != null) {

            sg2_spSports1_2 = pick_time.selectedItemPosition.toString()

        }

        sg2_spSports1_3_1 = when {
            !exercise_1_3_hour.text.isNullOrEmpty() -> exercise_1_3_hour.text.toString()
            exercise_1_3_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports1_3_2 = when {
            exercise_1_3_minute.text != null -> exercise_1_3_minute.text.toString()
            exercise_1_3_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports1_4 = when {
            exercise_2_true.isChecked -> "1"
            exercise_2_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time2.selectedItem != null) {

            sg2_spSports1_5 = pick_time2.selectedItemPosition.toString()

        }

        sg2_spSports1_6_1 = when {
            !exercise_2_3_hour.text.isNullOrEmpty() -> exercise_2_3_hour.text.toString()
            exercise_2_3_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports1_6_2 = when {
            !exercise_2_3_minute.text.isNullOrEmpty() -> exercise_2_3_minute.text.toString()
            exercise_2_3_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports2_1 = when {
            exercise_3_true.isChecked -> "1"
            exercise_3_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time3.selectedItem != null) {

            sg2_spSports2_2 = pick_time3.selectedItemPosition.toString()

        }

        sg2_spSports2_3_1 = when {
            !exercise_3_3_hour.text.isNullOrEmpty() -> exercise_3_3_hour.text.toString()
            exercise_3_3_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports2_3_2 = when {
            !exercise_3_3_minute.text.isNullOrEmpty() -> exercise_3_3_minute.text.toString()
            exercise_3_3_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports3_1 = when {
            exercise_4_true.isChecked -> "1"
            exercise_4_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time4.selectedItem != null) {

            sg2_spSports3_2 = pick_time4.selectedItemPosition.toString()

        }

        sg2_spSports3_3_1 = when {
            !exercise_4_1_hour.text.isNullOrEmpty() -> exercise_4_1_hour.text.toString()
            exercise_4_1_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports3_3_2 = when {
            !exercise_4_1_minute.text.isNullOrEmpty() -> exercise_4_1_minute.text.toString()
            exercise_4_1_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports3_4 = when {
            exercise_5_true.isChecked -> "1"
            exercise_5_false.isChecked -> "2"
            else -> ""
        }

        if (pick_time5.selectedItem != null) {

            sg2_spSports3_5 = pick_time5.selectedItemPosition.toString()

        }

        sg2_spSports3_6_1 = when {
            !exercise_3_6_hour.text.isNullOrEmpty() -> exercise_3_6_hour.text.toString()
            exercise_3_6_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports3_6_2 = when {
            !exercise_3_6_minute.text.isNullOrEmpty() -> exercise_3_6_minute.text.toString()
            exercise_3_6_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports4_1_1 = when {
            !exercise_4_1_hour.text.isNullOrEmpty() -> exercise_4_1_hour.text.toString()
            exercise_4_1_hour.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports4_1_2 = when {
            exercise_4_1_minute.text !=null -> exercise_4_1_minute.text.toString()
            exercise_4_1_minute.text.isEmpty() -> "0"
            else -> ""
        }

        sg2_spSports5 = when {
            exercise_6_no.isChecked -> "1"
            exercise_6_1.isChecked -> "2"
            exercise_6_2.isChecked -> "3"
            exercise_6_3.isChecked -> "4"
            exercise_6_4.isChecked -> "5"
            exercise_6_5.isChecked -> "6"
            else -> ""
        }

        sg2_spSports6 = when {
            exercise_7_true.isChecked -> "1"
            exercise_7_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports7 = when {
            exercise_8_true.isChecked -> "1"
            exercise_8_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports8 = when {
            exercise_9_true.isChecked -> "1"
            exercise_9_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports9 = when {
            exercise_10_true.isChecked -> "1"
            exercise_10_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports10 = when {
            exercise_11_true.isChecked -> "1"
            exercise_11_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports11 = when {
            exercise_12_true.isChecked -> "1"
            exercise_12_false.isChecked -> "2"
            else -> ""
        }

        sg2_spSports12 = when {
            exercise_13_true.isChecked -> "1"
            exercise_13_false.isChecked -> "2"
            else -> ""
        }

//        if(MainActivity.chart.isEmpty()){
//            if(!PaperArray.initCheck) {
//                PaperArray.PaperArrFunction.ArrayListInit()
//            }
//            exam_no = System.currentTimeMillis().toString()
//            MainActivity.exam_no = exam_no
//        }else{
        exam_no = MainActivity.exam_no
//        }

        SavePaper.Total.Array[5] = Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum)

/*
        //해당 배열이 존재할경우
        //clear 후 다시 문항을 체크하고 배열을 add한다
        //저장된 해당문진에서 수정하는 경우 대비
        if(PaperArray.PaperList.Arr_EXERCISE != null && PaperArray.PaperList.Arr_EXERCISE!!.size >= 1) {

            PaperArray.PaperList.Arr_EXERCISE!!.clear()
            PaperArray.PaperList.Arr_EXERCISE!!.add(Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                    sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                    sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                    sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                    sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                    sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum))

            //iterator를 써야 오류가 안난다
            //java.util.ConcurrentModificationException
            //index 문제
            //증가된 index와 list의 size를 비교하는데 있어서 문제가 발생하게된다.
            var iter = PaperArray.PaperList.Arr_RESULT?.listIterator()

            //result 배열에서 paper_exercise인 인덱스를 삭제하고
            //새 paper_exercise배열을 다시 그 자리에 추가하는 반복문
            while(iter!!.hasNext()){
                var arr = iter.next()
                if((arr as ArrayList<*>)[0] is Paper_EXERCISE){
                    iter.remove()
                    iter.add(PaperArray.PaperList.Arr_EXERCISE!!)
                }
            }

//            for ((i, arr) in PaperArray.PaperList.Arr_RESULT!!.withIndex()) {
//
//                if ((arr as ArrayList<*>)[0] is Paper_EXERCISE) {
//
//                    PaperArray.PaperList.Arr_RESULT!!.remove(i)
//                    PaperArray.PaperList.Arr_RESULT!!.add(i, PaperArray.PaperList.Arr_EXERCISE!!)
//
//                }
//
//            }

        }else {

            PaperArray.PaperList.Arr_EXERCISE!!.add(Paper_EXERCISE(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spSports1_1, sg2_spSports1_2,
                    sg2_spSports1_3_1, sg2_spSports1_3_2, sg2_spSports1_4, sg2_spSports1_5, sg2_spSports1_6_1, sg2_spSports1_6_2,
                    sg2_spSports2_1, sg2_spSports2_2, sg2_spSports2_3_1, sg2_spSports2_3_2, sg2_spSports3_1, sg2_spSports3_2,
                    sg2_spSports3_3_1, sg2_spSports3_3_2, sg2_spSports3_4, sg2_spSports3_5, sg2_spSports3_6_1, sg2_spSports3_6_2,
                    sg2_spSports4_1_1, sg2_spSports4_1_2, sg2_spSports5, sg2_spSports6, sg2_spSports7, sg2_spSports8,
                    sg2_spSports9, sg2_spSports10, sg2_spSports11, sg2_spSports12, sg2_spSportsSum))


            PaperArray.PaperList.Arr_RESULT!!.add(PaperArray.PaperList.Arr_EXERCISE!!)
            PaperArray.PaperList.temp_Arr_EXERCISE = ArrayList()

        }
*/
    }

    fun whenTempLoad(paper:Paper_EXERCISE) {

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        when {
            paper.sg2_spSports1_1 == "1" -> exercise_1_true.isChecked = true
            paper.sg2_spSports1_1 == "2" -> exercise_1_false.isChecked = true
            else -> { }
        }

        pick_time.setSelection(paper.sg2_spSports1_2.toInt())

        exercise_1_3_hour.setText(paper.sg2_spSports1_3_1)
        exercise_1_3_minute.setText(paper.sg2_spSports1_3_2)

        when {
            paper.sg2_spSports1_4 == "1" -> exercise_2_true.isChecked = true
            paper.sg2_spSports1_4 == "2" -> exercise_2_false.isChecked = true
            else -> { }
        }

        pick_time2.setSelection(paper.sg2_spSports1_5.toInt())

        exercise_1_6_hour.setText(paper.sg2_spSports1_6_1)
        exercise_1_6_minute.setText(paper.sg2_spSports1_6_2)

        when {
            paper.sg2_spSports2_1=="1" -> exercise_3_true.isChecked = true
            paper.sg2_spSports2_1=="2" -> exercise_3_false.isChecked = true
            else -> { }
        }

        pick_time3.setSelection(paper.sg2_spSports2_2.toInt())

        exercise_2_3_hour.setText(paper.sg2_spSports2_3_1)
        exercise_2_3_minute.setText(paper.sg2_spSports2_3_2)

        when {
            paper.sg2_spSports3_1=="1" -> exercise_4_true.isChecked = true
            paper.sg2_spSports3_1=="2" -> exercise_4_false.isChecked = true
            else -> { }
        }

        pick_time4.setSelection(paper.sg2_spSports3_2.toInt())

        exercise_3_3_hour.setText(paper.sg2_spSports3_3_1)
        exercise_3_3_minute.setText(paper.sg2_spSports3_3_2)

        when {
            paper.sg2_spSports3_4=="1" -> exercise_5_true.isChecked = true
            paper.sg2_spSports3_4=="2" -> exercise_5_false.isChecked = true
            else -> { }
        }



        pick_time5.setSelection(paper.sg2_spSports3_5.toInt())

        exercise_3_6_hour.setText(paper.sg2_spSports3_6_1)
        exercise_3_6_minute.setText(paper.sg2_spSports3_6_2)

        exercise_4_1_hour.setText(paper.sg2_spSports4_1_1)
        exercise_4_1_minute.setText(paper.sg2_spSports4_1_2)

        when {
            paper.sg2_spSports5=="1" -> exercise_6_no.isChecked = true
            paper.sg2_spSports5=="2" -> exercise_6_1.isChecked = true
            paper.sg2_spSports5=="3" -> exercise_6_2.isChecked = true
            paper.sg2_spSports5=="4" -> exercise_6_3.isChecked = true
            paper.sg2_spSports5=="5" -> exercise_6_4.isChecked = true
            paper.sg2_spSports5=="6" -> exercise_6_5.isChecked = true
            else -> { }
        }

        when {
            paper.sg2_spSports6=="1" -> exercise_7_true.isChecked = true
            paper.sg2_spSports6=="2" -> exercise_7_false.isChecked = true
            else -> { }
        }

        when {
            paper.sg2_spSports7=="1" -> exercise_8_true.isChecked = true
            paper.sg2_spSports7=="2" -> exercise_8_false.isChecked = true
            else -> { }
        }

        when {
            paper.sg2_spSports8=="1" -> exercise_9_true.isChecked = true
            paper.sg2_spSports8=="2" -> exercise_9_false.isChecked = true
            else -> { }
        }

        when {
            paper.sg2_spSports9=="1" -> exercise_10_true.isChecked = true
            paper.sg2_spSports9=="2" -> exercise_10_true.isChecked = true
            else -> { }
        }

        when {
            paper.sg2_spSports10=="1" -> exercise_11_true.isChecked = true
            paper.sg2_spSports10=="2" -> exercise_11_false.isChecked = true
            else -> { }
        }

        when {
            paper.sg2_spSports11=="1" -> exercise_12_true.isChecked = true
            paper.sg2_spSports11=="2" -> exercise_12_false.isChecked = true
            else -> { }
        }

        when {
            paper.sg2_spSports12=="1" -> exercise_13_true.isChecked = true
            paper.sg2_spSports12=="2" -> exercise_13_false.isChecked = true
            else -> { }
        }

    }

    fun GetPaper(paper:Paper_EXERCISE)
    {

        state = "getPaper"

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

    fun GetPaper(paper:ServerPaper_Life)
    {

        state = "getPaper"

        cannotEditQuestionnaire(exercise_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.sg2_name
        first_serial.text = paper.sg2_jumin.substring(0, 6)
        last_serial.text = paper.sg2_jumin.substring(6, 7)
        Signature.visibility = View.GONE

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
/*
    //해당 액티비티에 Exercise 배열이 저장안되어 있다면 temp_arr를 저장함
    //해당 액티비티를 정상적으로 완료하고 이동하면 temp_arr를 초기화함
    //PaperList Arr EXERCISE가 존재하면 check2를 실행한다
    override fun onPause() {

        if(SavedListObject.SavedList.savedDataClass.exerciseSaved){

            PaperArray.PaperList.temp_Arr_EXERCISE = null
            check2()

        }else{

            if(state != "getPaper") {

                whenTempSave()

            }

        }

        super.onPause()

    }
*/
}