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
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import com.fineinsight.zzango.questionnaire.LocalList.Paper_SMOKING
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_smoking_exam.*
import kotlinx.android.synthetic.main.activity_smoking_exam.Signature
import kotlinx.android.synthetic.main.activity_smoking_exam.first_serial
import kotlinx.android.synthetic.main.activity_smoking_exam.last_serial
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

        //서명정보 가져오는거
        if(!Examinee.USER.info.SIGN.contentEquals(Examinee.USER.EMPTY_BYTE_ARRAY))
        {
            signature = Examinee.USER.info.SIGN
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(Examinee.USER.info.SIGN))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        smoking_0_true.isChecked = true

        smoking_0_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, smoking_question_constraintLayout)

        }

        smoking_examination_save.setOnClickListener {
            if(check()){
                startActivity(Intent(this@SmokingExaminationActivity, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
        }

        smoking_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        if(AdditionalArr.over.checkAll){
            if(AdditionalArr.over.isSmoking){
                smoking_0_true.isChecked = true
                smoking_0_false.isChecked = false
                smoking_0_true.isEnabled = false
                smoking_0_false.isEnabled = false
                if(SavePaper.Total.temp_Smoking != null){
                    SavePaper.Total.temp_Smoking!!.sg2_spSmokeSum = "1"
                }else if((SavePaper.Total.Array[7] as Paper_SMOKING).sg2_spSmokeSum.isNotEmpty()){
                    (SavePaper.Total.Array[7] as Paper_SMOKING).sg2_spSmokeSum = "1"
                }
            }else{
                smoking_0_true.isChecked = false
                smoking_0_false.isChecked = true
                smoking_0_true.isEnabled = false
                smoking_0_false.isEnabled = false
                if(SavePaper.Total.temp_Smoking != null){
                    SavePaper.Total.temp_Smoking!!.sg2_spSmokeSum = "0"
                }else if((SavePaper.Total.Array[7] as Paper_SMOKING).sg2_spSmokeSum.isNotEmpty()){
                    (SavePaper.Total.Array[7] as Paper_SMOKING).sg2_spSmokeSum = "0"
                }
            }
        }else{
            smoking_0_true.isEnabled = true
            smoking_0_false.isEnabled = true
        }

        if(SavePaper.Total.temp_Smoking != null){
            whenTempLoad(SavePaper.Total.temp_Smoking!!)
        }else if(SavedListObject.SavedList.savedDataClass.smokingSaved){
            whenTempLoad(SavePaper.Total.Array[7] as Paper_SMOKING)
        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_SMOKING) {

                var paper = intent.getSerializableExtra("paper") as Paper_SMOKING

                GetPaper(paper)

                try {
//                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)
//                    Signature.setImageBitmap(bmp)

                } catch (e: Exception) {
                    println(e.message)
                }

                smoking_edit_submit.setOnClickListener {

                    finish()

                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Life

                GetPaper(paper)

                smoking_edit_submit.text = "다음"

                smoking_edit_submit.setOnClickListener {

                    smoking_exam_server_getPaper()
                    finish()

                }

            }

        }else{

            name_edit.text = Examinee.USER.info.NAME
            first_serial.text = Examinee.USER.info.JUMIN1
            last_serial.text = Examinee.USER.info.JUMIN2
            smoking_examination_save.text = "다음"

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        controlProgress(this)

        registrationNumber(last_serial)

    }

    override fun onResume() {
        super.onResume()
        ChartDivision.ChartDivision.ProgressAction(false, this)

    }

    override fun onBackPressed() {

        if(Progress_circle.visibility != View.VISIBLE){

            cancelAlert()

        }

    }

    fun smoking_exam_server_getPaper(){

        startActivity(Intent(this@SmokingExaminationActivity, DrinkingExaminationActivity::class.java).putExtra("paper", intent.getSerializableExtra("paper") as ServerPaper_Life).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun whenTempSave(){

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.SMOKING.EN_NM
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

        SavePaper.Total.temp_Smoking = Paper_SMOKING(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                sg2_spSmoke1, sg2_spSmoke2, sg2_spSmoke3, sg2_spSmoke4, sg2_spSmoke5, sg2_spSmoke6,
                sg2_spSmoke7, sg2_spSmoke8, sg2_spSmokeSum)

    }

    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
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

        SavePaper.Total.Array[7] = Paper_SMOKING(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                sg2_spSmoke1, sg2_spSmoke2, sg2_spSmoke3, sg2_spSmoke4, sg2_spSmoke5, sg2_spSmoke6,
                sg2_spSmoke7, sg2_spSmoke8, sg2_spSmokeSum)

        SavedListObject.SavedList.savedDataClass.smokingSaved = true

        SavePaper.Total.temp_Smoking = null

        ChartDivision.ChartDivision.ProgressAction(true, this)

        return true

    }

    fun whenTempLoad(paper: Paper_SMOKING) {

        ChartDivision.ChartDivision.ProgressAction(true, this)

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

    fun GetPaper(paper: ServerPaper_Life) {

        state = "getPaper"

        cannotEditQuestionnaire(smoking_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.sg2_name
        first_serial.text = paper.sg2_jumin.substring(0, 6)
        last_serial.text = paper.sg2_jumin.substring(6, 7)
        Signature.visibility = View.GONE

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

}