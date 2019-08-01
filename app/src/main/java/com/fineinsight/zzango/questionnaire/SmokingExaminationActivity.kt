package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import com.fineinsight.zzango.questionnaire.LocalList.Paper_SMOKING
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_drinking_exam.*
import kotlinx.android.synthetic.main.activity_smoking_exam.*
import kotlinx.android.synthetic.main.activity_smoking_exam.Signature
import kotlinx.android.synthetic.main.activity_smoking_exam.first_serial
import kotlinx.android.synthetic.main.activity_smoking_exam.last_serial
import kotlinx.android.synthetic.main.activity_smoking_exam.login_appbar_loading_progress
import kotlinx.android.synthetic.main.activity_smoking_exam.login_appbar_loading_progress_bg
import kotlinx.android.synthetic.main.activity_smoking_exam.name_edit
import kotlinx.android.synthetic.main.activity_smoking_exam.progress_constraintLayout
import kotlinx.android.synthetic.main.progressbar2.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("NewApi", "SimpleDateFormat")
class SmokingExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smoking_exam)

//        controlProgress(this, questionnaire_progress_wrapper, progress_constraintLayout, questionnaire_progress, progress_guideline, progress_guideline2, progress_guideline3, progress_guideline4, progress_guideline5, progress_guideline6, progress_guideline7, progress_guideline8)
        controlProgress(this)

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        smoking_0_true.isChecked = true

        smoking_0_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, smoking_question_constraintLayout)

        }

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

            cancelAlert()

        }

        smoking_edit_submit.setOnClickListener {

            finish()

        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper_SMOKING

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
            smoking_examination_save.text = "다음"

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////


        if(AdditionalArr.over.checkAll){
            if(AdditionalArr.over.isSmoking){
                smoking_0_true.isChecked = true
                smoking_0_false.isChecked = false
                smoking_0_true.isEnabled = false
                smoking_0_false.isEnabled = false
            }else{
                smoking_0_true.isChecked = false
                smoking_0_false.isChecked = true
                smoking_0_true.isEnabled = false
                smoking_0_false.isEnabled = false
            }
        }else{
            smoking_0_true.isEnabled = true
            smoking_0_false.isEnabled = true
        }


        if(PaperArray.PaperList.Arr_SMOKING != null && PaperArray.PaperList.Arr_SMOKING!!.size != 0){

            whenTempLoad(PaperArray.PaperList.Arr_SMOKING!![0])

        }else if(PaperArray.PaperList.temp_Arr_SMOKING != null && PaperArray.PaperList.temp_Arr_SMOKING!!.size != 0){

            whenTempLoad(PaperArray.PaperList.temp_Arr_SMOKING!![0])

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

    fun smoking_exam_local_insert(){

        startActivity(Intent(this@SmokingExaminationActivity, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun smoking_exam_server_insert(){

        startActivity(Intent(this@SmokingExaminationActivity, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun whenTempSave(){

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
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

        if (!name_edit.text.isNullOrEmpty()) {
            name = name_edit.text.toString()
        }

        if (!first_serial.text.isNullOrEmpty()) {
            first_serial_text = first_serial.text.toString()
        }

        if (!last_serial.text.isNullOrEmpty()) {
            last_serial_text = last_serial.text.toString()
        }

        if(smoking_0_false.isChecked){
            sg2_spSmokeSum = "0"
        }else {
            sg2_spSmokeSum = "1"

            sg2_spSmoke1 = when {
                smoking_1_1.isChecked -> "1"
                smoking_1_2.isChecked -> "2"
                smoking_1_3.isChecked -> "3"
                smoking_1_4.isChecked -> "4"
                else -> ""
            }

            sg2_spSmoke2 = when {
                smoking_2_1.isChecked -> "0"
                smoking_2_2.isChecked -> "1"
                smoking_2_3.isChecked -> "2"
                smoking_2_4.isChecked -> "3"
                smoking_2_5.isChecked -> "4"
                smoking_2_6.isChecked -> "5"
                smoking_2_7.isChecked -> "6"
                smoking_2_8.isChecked -> "7"
                else -> ""
            }

            sg2_spSmoke3 = when {
                smoking_3_1.isChecked -> "1"
                smoking_3_2.isChecked -> "2"
                smoking_3_3.isChecked -> "3"
                smoking_3_4.isChecked -> "4"
                else -> ""
            }


            sg2_spSmoke4 = when {
                smoking_4_1.isChecked -> "1"
                smoking_4_2.isChecked -> "2"
                else -> ""
            }

            sg2_spSmoke5 = when {
                smoking_5_1.isChecked -> "1"
                smoking_5_2.isChecked -> "2"
                else -> ""
            }

            sg2_spSmoke6 = when {
                smoking_6_1.isChecked -> "1"
                smoking_6_2.isChecked -> "2"
                smoking_6_3.isChecked -> "3"
                smoking_6_4.isChecked -> "4"
                else -> ""
            }

            sg2_spSmoke7 = when {
                smoking_7_1.isChecked -> "1"
                smoking_7_2.isChecked -> "2"
                else -> ""
            }

            sg2_spSmoke8 = when {
                smoking_8_1.isChecked -> "1"
                smoking_8_2.isChecked -> "2"
                else -> ""
            }
        }

        exam_no = MainActivity.exam_no

        PaperArray.PaperList.temp_Arr_SMOKING!!.add(Paper_SMOKING(
                exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category,
                sg2_spSmoke1, sg2_spSmoke2, sg2_spSmoke3, sg2_spSmoke4, sg2_spSmoke5, sg2_spSmoke6,
                sg2_spSmoke7, sg2_spSmoke8, sg2_spSmokeSum
        ))

    }

    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
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

        if(smoking_0_false.isChecked){
            sg2_spSmokeSum = "0"
        }else {
            sg2_spSmokeSum = "1"

            if (smoking_1_1.isChecked) {
                sg2_spSmoke1 = "1"
            } else if (smoking_1_2.isChecked) {
                sg2_spSmoke1 = "2"
            } else if (smoking_1_3.isChecked) {
                sg2_spSmoke1 = "3"
            } else if (smoking_1_4.isChecked) {
                sg2_spSmoke1 = "4"
            } else {
                Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (smoking_2_1.isChecked) {
                sg2_spSmoke2 = "0"
            } else if (smoking_2_2.isChecked) {
                sg2_spSmoke2 = "1"
            } else if (smoking_2_3.isChecked) {
                sg2_spSmoke2 = "2"
            } else if (smoking_2_4.isChecked) {
                sg2_spSmoke2 = "3"
            } else if (smoking_2_5.isChecked) {
                sg2_spSmoke2 = "4"
            } else if (smoking_2_6.isChecked) {
                sg2_spSmoke2 = "5"
            } else if (smoking_2_7.isChecked) {
                sg2_spSmoke2 = "6"
            } else if (smoking_2_8.isChecked) {
                sg2_spSmoke2 = "7"
            } else {
                Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (smoking_3_1.isChecked) {
                sg2_spSmoke3 = "1"
            } else if (smoking_3_2.isChecked) {
                sg2_spSmoke3 = "2"
            } else if (smoking_3_3.isChecked) {
                sg2_spSmoke3 = "3"
            } else if (smoking_3_4.isChecked) {
                sg2_spSmoke3 = "4"
            } else {
                Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }


            if (smoking_4_1.isChecked) {
                sg2_spSmoke4 = "1"
            } else if (smoking_4_2.isChecked) {
                sg2_spSmoke4 = "2"
            } else {
                Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (smoking_5_1.isChecked) {
                sg2_spSmoke5 = "1"
            } else if (smoking_5_2.isChecked) {
                sg2_spSmoke5 = "2"
            } else {
                Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (smoking_6_1.isChecked) {
                sg2_spSmoke6 = "1"
            } else if (smoking_6_2.isChecked) {
                sg2_spSmoke6 = "2"
            } else if (smoking_6_3.isChecked) {
                sg2_spSmoke6 = "3"
            } else if (smoking_6_4.isChecked) {
                sg2_spSmoke6 = "4"
            } else {
                Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (smoking_7_1.isChecked) {
                sg2_spSmoke7 = "1"
            } else if (smoking_7_2.isChecked) {
                sg2_spSmoke7 = "2"
            } else {
                Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (smoking_8_1.isChecked) {
                sg2_spSmoke8 = "1"
            } else if (smoking_8_2.isChecked) {
                sg2_spSmoke8 = "2"
            } else {
                Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }

        exam_no = MainActivity.exam_no

        PaperArray.PaperList.Arr_SMOKING!!.add(Paper_SMOKING(
                exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category,
                sg2_spSmoke1, sg2_spSmoke2, sg2_spSmoke3, sg2_spSmoke4, sg2_spSmoke5, sg2_spSmoke6,
                sg2_spSmoke7, sg2_spSmoke8, sg2_spSmokeSum
        ))


        PaperArray.PaperList.Arr_RESULT!!.add(PaperArray.PaperList.Arr_SMOKING!!)

        PaperArray.PaperList.temp_Arr_SMOKING = ArrayList()

        return true

    }

    fun whenTempLoad(paper: Paper_SMOKING) {

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        if(paper.sg2_spSmokeSum=="0"){
            smoking_0_false.isChecked = true

        }else if(paper.sg2_spSmokeSum=="1"){
            smoking_0_true.isChecked = true
        }

        if(paper.sg2_spSmoke1 == "1"){
            smoking_1_1.isChecked = true
        }else if(paper.sg2_spSmoke1 == "2"){
            smoking_1_2.isChecked = true
        }else if(paper.sg2_spSmoke1 == "3"){
            smoking_1_3.isChecked = true
        }else if(paper.sg2_spSmoke1 == "4"){
            smoking_1_4.isChecked = true
        }

        if(paper.sg2_spSmoke2 == "0"){
            smoking_2_1.isChecked = true
        }else if(paper.sg2_spSmoke2 == "1"){
            smoking_2_2.isChecked = true
        }else if(paper.sg2_spSmoke2 == "2"){
            smoking_2_3.isChecked = true
        }else if(paper.sg2_spSmoke2 == "3"){
            smoking_2_4.isChecked = true
        }else if(paper.sg2_spSmoke2 == "4"){
            smoking_2_5.isChecked = true
        }else if(paper.sg2_spSmoke2 == "5"){
            smoking_2_6.isChecked = true
        }else if(paper.sg2_spSmoke2 == "6"){
            smoking_2_7.isChecked = true
        }else if(paper.sg2_spSmoke2 == "7"){
            smoking_2_8.isChecked = true
        }

        if(paper.sg2_spSmoke3 == "1"){
            smoking_3_1.isChecked = true
        }else if(paper.sg2_spSmoke3 == "2"){
            smoking_3_2.isChecked = true
        }else if(paper.sg2_spSmoke3 == "3"){
            smoking_3_3.isChecked = true
        }else if(paper.sg2_spSmoke3 == "4"){
            smoking_3_4.isChecked = true
        }

        if(paper.sg2_spSmoke4 == "1"){
            smoking_4_1.isChecked = true
        }else if(paper.sg2_spSmoke4 == "2"){
            smoking_4_2.isChecked = true
        }


        if(paper.sg2_spSmoke5 == "1"){
            smoking_5_1.isChecked = true
        }else if(paper.sg2_spSmoke5 == "2"){
            smoking_5_2.isChecked = true
        }


        if(paper.sg2_spSmoke6 == "1"){
            smoking_6_1.isChecked = true
        }else if(paper.sg2_spSmoke6 == "2"){
            smoking_6_2.isChecked = true
        }else if(paper.sg2_spSmoke6 == "3"){
            smoking_6_3.isChecked = true
        }else if(paper.sg2_spSmoke6 == "4"){
            smoking_6_4.isChecked = true
        }


        if(paper.sg2_spSmoke7 == "1"){
            smoking_7_1.isChecked = true
        }else if(paper.sg2_spSmoke7 == "2"){
            smoking_7_2.isChecked = true
        }


        if(paper.sg2_spSmoke8 == "1"){
            smoking_8_1.isChecked = true
        }else if(paper.sg2_spSmoke8 == "2"){
            smoking_8_2.isChecked = true
        }

    }

    fun GetPaper(paper: Paper_SMOKING) {

        state = "getPaper"

        cannotEditQuestionnaire(smoking_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        smoking_examination_save.visibility = View.GONE
        smoking_examination_cancel.visibility = View.GONE
        smoking_edit_submit.visibility = View.VISIBLE

        if(paper.sg2_spSmokeSum=="0"){
            smoking_0_false.isChecked = true

        }else if(paper.sg2_spSmokeSum=="1"){
            smoking_0_true.isChecked = true
        }

        if(paper.sg2_spSmoke1 == "1"){
            smoking_1_1.isChecked = true
        }else if(paper.sg2_spSmoke1 == "2"){
            smoking_1_2.isChecked = true
        }else if(paper.sg2_spSmoke1 == "3"){
            smoking_1_3.isChecked = true
        }else if(paper.sg2_spSmoke1 == "4"){
            smoking_1_4.isChecked = true
        }

        if(paper.sg2_spSmoke2 == "0"){
            smoking_2_1.isChecked = true
        }else if(paper.sg2_spSmoke2 == "1"){
            smoking_2_2.isChecked = true
        }else if(paper.sg2_spSmoke2 == "2"){
            smoking_2_3.isChecked = true
        }else if(paper.sg2_spSmoke2 == "3"){
            smoking_2_4.isChecked = true
        }else if(paper.sg2_spSmoke2 == "4"){
            smoking_2_5.isChecked = true
        }else if(paper.sg2_spSmoke2 == "5"){
            smoking_2_6.isChecked = true
        }else if(paper.sg2_spSmoke2 == "6"){
            smoking_2_7.isChecked = true
        }else if(paper.sg2_spSmoke2 == "7"){
            smoking_2_8.isChecked = true
        }

        if(paper.sg2_spSmoke3 == "1"){
            smoking_3_1.isChecked = true
        }else if(paper.sg2_spSmoke3 == "2"){
            smoking_3_2.isChecked = true
        }else if(paper.sg2_spSmoke3 == "3"){
            smoking_3_3.isChecked = true
        }else if(paper.sg2_spSmoke3 == "4"){
            smoking_3_4.isChecked = true
        }

        if(paper.sg2_spSmoke4 == "1"){
            smoking_4_1.isChecked = true
        }else if(paper.sg2_spSmoke4 == "2"){
            smoking_4_2.isChecked = true
        }


        if(paper.sg2_spSmoke5 == "1"){
            smoking_5_1.isChecked = true
        }else if(paper.sg2_spSmoke5 == "2"){
            smoking_5_2.isChecked = true
        }


        if(paper.sg2_spSmoke6 == "1"){
            smoking_6_1.isChecked = true
        }else if(paper.sg2_spSmoke6 == "2"){
            smoking_6_2.isChecked = true
        }else if(paper.sg2_spSmoke6 == "3"){
            smoking_6_3.isChecked = true
        }else if(paper.sg2_spSmoke6 == "4"){
            smoking_6_4.isChecked = true
        }


        if(paper.sg2_spSmoke7 == "1"){
            smoking_7_1.isChecked = true
        }else if(paper.sg2_spSmoke7 == "2"){
            smoking_7_2.isChecked = true
        }


        if(paper.sg2_spSmoke8 == "1"){
            smoking_8_1.isChecked = true
        }else if(paper.sg2_spSmoke8 == "2"){
            smoking_8_2.isChecked = true
        }

    }

    override fun onPause() {

        if(PaperArray.PaperList.Arr_SMOKING != null && PaperArray.PaperList.Arr_SMOKING!!.size != 0){

            PaperArray.PaperList.temp_Arr_SMOKING = null

        }else{

            if(state != "getPaper") {

                whenTempSave()

            }

        }

        super.onPause()

    }

}