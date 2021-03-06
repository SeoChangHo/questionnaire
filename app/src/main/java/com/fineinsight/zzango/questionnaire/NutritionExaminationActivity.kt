package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import com.fineinsight.zzango.questionnaire.LocalList.Paper_NUTRITION
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_cognitive_exam.*
import kotlinx.android.synthetic.main.activity_nutrition_exam.*
import kotlinx.android.synthetic.main.activity_nutrition_exam.Progress_circle
import kotlinx.android.synthetic.main.activity_nutrition_exam.Signature
import kotlinx.android.synthetic.main.activity_nutrition_exam.first_serial
import kotlinx.android.synthetic.main.activity_nutrition_exam.last_serial
import kotlinx.android.synthetic.main.activity_nutrition_exam.name_edit
import kotlinx.android.synthetic.main.activity_nutrition_exam.name_text
import kotlinx.android.synthetic.main.activity_nutrition_exam.progress_constraintLayout
import kotlinx.android.synthetic.main.progressbar2.*
import java.io.Serializable
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("NewApi", "SimpleDateFormat")
class NutritionExaminationActivity :RootActivity() {

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition_exam)

        //서명정보 가져오는거
        if(!Examinee.USER.info.SIGN.contentEquals(Examinee.USER.EMPTY_BYTE_ARRAY))
        {
            signature = Examinee.USER.info.SIGN
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(Examinee.USER.info.SIGN))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        nutrition_examination_save.setOnClickListener {
            //check function 리턴하는 boolean 값에 따라 진행
            if(check()){
                startActivity(Intent(this@NutritionExaminationActivity, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
        }

        nutrition_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        //BMI 지수

        height.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
              if(height.text.toString() != "" && weight.text.toString() != ""){
                  val heightValue = java.lang.Float.parseFloat(height.text.toString()) / 100
                  val weightValue = java.lang.Float.parseFloat(weight.text.toString())

                  val resultBmi = weightValue / (heightValue * heightValue)

                  val result = (DecimalFormat("#.#").format(resultBmi)).toDouble()

                  bmi.setText(result.toString())
              }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        weight.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(height.text.toString() != "" && weight.text.toString() != ""){
                    val heightValue = java.lang.Float.parseFloat(height.text.toString()) / 100
                    val weightValue = java.lang.Float.parseFloat(weight.text.toString())

                    val resultBmi = weightValue / (heightValue * heightValue)

                    val result = (DecimalFormat("#.#").format(resultBmi)).toDouble()

                    bmi.setText(result.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        if(SavePaper.Total.temp_Nutrition != null){
            whenTempLoad(SavePaper.Total.temp_Nutrition!!)
        }else if(SavedListObject.SavedList.savedDataClass.nutritionSaved){
            whenTempLoad(SavePaper.Total.Array[6] as Paper_NUTRITION)
        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_NUTRITION) {

                var paper = intent.getSerializableExtra("paper") as Paper_NUTRITION

                GetPaper(paper)

                try {
//                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)
//
//                    Signature.setImageBitmap(bmp)

                } catch (e: Exception) {
                    println(e.message)
                }

                nutrition_edit_submit.setOnClickListener {

                    finish()

                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Life

                GetPaper(paper)

                nutrition_edit_submit.text = "다음"

                nutrition_edit_submit.setOnClickListener {

                    nutrition_exam_server_getPaper()
                    finish()

                }

            }

        }else{

            name_edit.text = Examinee.USER.info.NAME
            first_serial.text = Examinee.USER.info.JUMIN1
            last_serial.text = Examinee.USER.info.JUMIN2


            nutrition_examination_save.text = "다음"

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

    fun nutrition_exam_server_getPaper(){

        startActivity(Intent(this@NutritionExaminationActivity, SmokingExaminationActivity::class.java).putExtra("paper", intent.getSerializableExtra("paper") as ServerPaper_Life).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun whenTempSave(){

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.NUTRITION.EN_NM
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
        var sg2_spFoodSum = ""
        var sg2_spFatHeight = ""
        var sg2_spFatWeight = ""
        var sg2_spFatWaistSize = ""
        var sg2_spFatBmi = ""
        var sg2_spFat1 = ""
        var sg2_spFat2 = ""
        var sg2_spFat3 = ""

        if (!name_text.text.isNullOrEmpty()) {

            name = name_edit.text.toString()

        }

        if (!first_serial.text.isNullOrEmpty()) {

            first_serial_text = first_serial.text.toString()

        }

        if(!last_serial.text.isNullOrEmpty()){

            last_serial_text = last_serial.text.toString()

        }

        sg2_spFood1 = when {
            nutrition_1_1.isChecked -> "1"
            nutrition_1_2.isChecked -> "2"
            nutrition_1_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood2 = when {
            nutrition_2_1.isChecked -> "1"
            nutrition_2_2.isChecked -> "2"
            nutrition_2_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood3 = when {
            nutrition_3_1.isChecked -> "1"
            nutrition_3_2.isChecked -> "2"
            nutrition_3_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood4 = when {
            nutrition_4_1.isChecked -> "1"
            nutrition_4_2.isChecked -> "2"
            nutrition_4_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood5 = when {
            nutrition_5_1.isChecked -> "1"
            nutrition_5_2.isChecked -> "2"
            nutrition_5_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood6 = when {
            nutrition_6_1.isChecked -> "1"
            nutrition_6_2.isChecked -> "2"
            nutrition_6_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood7 = when {
            nutrition_7_1.isChecked -> "1"
            nutrition_7_2.isChecked -> "2"
            nutrition_7_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood8 = when {
            nutrition_8_1.isChecked -> "1"
            nutrition_8_2.isChecked -> "2"
            nutrition_8_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood9 = when {
            nutrition_9_1.isChecked -> "1"
            nutrition_9_2.isChecked -> "2"
            nutrition_9_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood10 = when {
            nutrition_10_1.isChecked -> "1"
            nutrition_10_2.isChecked -> "2"
            nutrition_10_3.isChecked -> "3"
            else -> ""
        }

        sg2_spFood11 = when {
            nutrition_11_1.isChecked -> "1"
            nutrition_11_2.isChecked -> "2"
            nutrition_11_3.isChecked -> "3"
            else -> ""
        }

//        sg2_spFatHeight = when {
//
//            !height.text.toString().isNullOrEmpty() -> height.text.toString()
//            else -> {
//
//                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()
//
//                return false
//
//            }
//        }
//
//        sg2_spFatWeight = when {
//
//            !weight.text.toString().isNullOrEmpty() -> weight.text.toString()
//            else -> {
//
//                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()
//
//                return false
//
//            }
//        }
//
//        sg2_spFatWaistSize = when {
//
//            !waist_size.text.toString().isNullOrEmpty() -> waist_size.text.toString()
//            else -> {
//
//                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()
//
//                return false
//
//            }
//        }
//
//        sg2_spFatBmi = when {
//
//            !bmi.text.toString().isNullOrEmpty() -> bmi.text.toString()
//            else -> {
//
//                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()
//
//                return false
//
//            }
//        }

        sg2_spFat1 = when {
            fat_1_true.isChecked -> "1"
            fat_1_false.isChecked -> "2"
            else -> ""
        }

        sg2_spFat2 = when {
            fat_2_no.isChecked -> "1"
            fat_2_1.isChecked -> "2"
            fat_2_2.isChecked -> "3"
            fat_2_3.isChecked -> "4"
            else -> ""
        }

        sg2_spFat3 = when {
            fat_3_1.isChecked -> "1"
            fat_3_2.isChecked -> "2"
            fat_3_3.isChecked -> "3"
            else -> ""
        }

        SavePaper.Total.temp_Nutrition = Paper_NUTRITION(exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category, sg2_spFood1, sg2_spFood2,
                sg2_spFood3, sg2_spFood4, sg2_spFood5, sg2_spFood6, sg2_spFood7, sg2_spFood8, sg2_spFood9, sg2_spFood10,
                sg2_spFood11, sg2_spFoodSum, sg2_spFatHeight, sg2_spFatWeight, sg2_spFatWaistSize, sg2_spFatBmi, sg2_spFat1, sg2_spFat2, sg2_spFat3)

    }

    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
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
        var sg2_spFoodSum = ""
        var sg2_spFatHeight = ""
        var sg2_spFatWeight = ""
        var sg2_spFatWaistSize = ""
        var sg2_spFatBmi = ""
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

//        sg2_spFatHeight = when {
//
//            !height.text.toString().isNullOrEmpty() -> height.text.toString()
//            else -> {
//
//                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()
//
//                return false
//
//            }
//        }
//
//        sg2_spFatWeight = when {
//
//            !weight.text.toString().isNullOrEmpty() -> weight.text.toString()
//            else -> {
//
//                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()
//
//                return false
//
//            }
//        }
//
//        sg2_spFatWaistSize = when {
//
//            !waist_size.text.toString().isNullOrEmpty() -> waist_size.text.toString()
//            else -> {
//
//                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()
//
//                return false
//
//            }
//        }
//
//        sg2_spFatBmi = when {
//
//            !bmi.text.toString().isNullOrEmpty() -> bmi.text.toString()
//            else -> {
//
//                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()
//
//                return false
//
//            }
//        }

        sg2_spFat1 = when {
            fat_1_true.isChecked -> "1"
            fat_1_false.isChecked -> "2"
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFat2 = when {
            fat_2_no.isChecked -> "1"
            fat_2_1.isChecked -> "2"
            fat_2_2.isChecked -> "3"
            fat_2_3.isChecked -> "4"
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

        SavePaper.Total.Array[6] = Paper_NUTRITION(exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category, sg2_spFood1, sg2_spFood2,
        sg2_spFood3, sg2_spFood4, sg2_spFood5, sg2_spFood6, sg2_spFood7, sg2_spFood8, sg2_spFood9, sg2_spFood10,
        sg2_spFood11, sg2_spFoodSum, sg2_spFatHeight, sg2_spFatWeight, sg2_spFatWaistSize, sg2_spFatBmi, sg2_spFat1, sg2_spFat2, sg2_spFat3)

        SavedListObject.SavedList.savedDataClass.nutritionSaved = true

        SavePaper.Total.temp_Nutrition = null

        ChartDivision.ChartDivision.ProgressAction(true, this)

        return true

    }

    fun whenTempLoad(paper:Paper_NUTRITION){

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        when {
            paper.sg2_spFood1=="1" -> nutrition_1_1.isChecked = true
            paper.sg2_spFood1=="2" -> nutrition_1_2.isChecked = true
            paper.sg2_spFood1=="3" -> nutrition_1_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood2=="1" -> nutrition_2_1.isChecked = true
            paper.sg2_spFood2=="2" -> nutrition_2_2.isChecked = true
            paper.sg2_spFood2=="3" -> nutrition_2_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood3=="1" -> nutrition_3_1.isChecked = true
            paper.sg2_spFood3=="2" -> nutrition_3_2.isChecked = true
            paper.sg2_spFood3=="3" -> nutrition_3_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood4=="1" -> nutrition_4_1.isChecked = true
            paper.sg2_spFood4=="2" -> nutrition_4_2.isChecked = true
            paper.sg2_spFood4=="3" -> nutrition_4_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood5=="1" -> nutrition_5_1.isChecked = true
            paper.sg2_spFood5=="2" -> nutrition_5_2.isChecked = true
            paper.sg2_spFood5=="3" -> nutrition_5_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood6=="1" -> nutrition_6_1.isChecked = true
            paper.sg2_spFood6=="2" -> nutrition_6_2.isChecked = true
            paper.sg2_spFood6=="3" -> nutrition_6_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood7=="1" -> nutrition_7_1.isChecked = true
            paper.sg2_spFood7=="2" -> nutrition_7_2.isChecked = true
            paper.sg2_spFood7=="3" -> nutrition_7_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood8=="1" -> nutrition_8_1.isChecked = true
            paper.sg2_spFood8=="2" -> nutrition_8_2.isChecked = true
            paper.sg2_spFood8=="3" -> nutrition_8_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood9=="1" -> nutrition_9_1.isChecked = true
            paper.sg2_spFood9=="2" -> nutrition_9_2.isChecked = true
            paper.sg2_spFood9=="3" -> nutrition_9_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood10=="1" -> nutrition_10_1.isChecked = true
            paper.sg2_spFood10=="2" -> nutrition_10_2.isChecked = true
            paper.sg2_spFood10=="3" -> nutrition_10_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFood11=="1" -> nutrition_11_1.isChecked = true
            paper.sg2_spFood11=="2" -> nutrition_11_2.isChecked = true
            paper.sg2_spFood11=="3" -> nutrition_11_3.isChecked = true
            else -> {}
        }

        height.setText(paper.sg2_spHeight)
        weight.setText(paper.sg2_spWeight)
        waist_size.setText(paper.sg2_spWaistSize)
        bmi.setText(paper.sg2_spBmi)

        when {
            paper.sg2_spFat1=="1" -> fat_1_true.isChecked = true
            paper.sg2_spFat1=="2" -> fat_1_false.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFat2=="1" -> fat_2_no.isChecked = true
            paper.sg2_spFat2=="2" -> fat_2_1.isChecked = true
            paper.sg2_spFat2=="3" -> fat_2_2.isChecked = true
            paper.sg2_spFat2=="4" -> fat_2_3.isChecked = true
            else -> {}
        }

        when {
            paper.sg2_spFat3=="1" -> fat_3_1.isChecked = true
            paper.sg2_spFat3=="2" -> fat_3_2.isChecked = true
            paper.sg2_spFat3=="3" -> fat_3_3.isChecked = true
            else -> {}
        }

    }

    fun GetPaper(paper:Paper_NUTRITION){

        state = "getPaper"

        cannotEditQuestionnaire(nutrition_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        nutrition_examination_save.visibility = View.GONE
        nutrition_examination_cancel.visibility = View.GONE
        nutrition_edit_submit.visibility = View.VISIBLE

        if(paper.sg2_spFood1=="1"){

            nutrition_1_1.isChecked = true

        }
        else if(paper.sg2_spFood1=="2"){

            nutrition_1_2.isChecked = true

        }else{

            nutrition_1_3.isChecked = true

        }

        if(paper.sg2_spFood2=="1"){

            nutrition_2_1.isChecked = true

        }
        else if(paper.sg2_spFood2=="2"){

            nutrition_2_2.isChecked = true

        }else{

            nutrition_2_3.isChecked = true

        }

        if(paper.sg2_spFood3=="1"){

            nutrition_3_1.isChecked = true

        }
        else if(paper.sg2_spFood3=="2"){

            nutrition_3_2.isChecked = true

        }else{

            nutrition_3_3.isChecked = true

        }

        if(paper.sg2_spFood4=="1"){

            nutrition_4_1.isChecked = true

        }
        else if(paper.sg2_spFood4=="2"){

            nutrition_4_2.isChecked = true

        }else{

            nutrition_4_3.isChecked = true

        }

        if(paper.sg2_spFood5=="1"){

            nutrition_5_1.isChecked = true

        }
        else if(paper.sg2_spFood5=="2"){

            nutrition_5_2.isChecked = true

        }else{

            nutrition_5_3.isChecked = true

        }

        if(paper.sg2_spFood6=="1"){

            nutrition_6_1.isChecked = true

        }
        else if(paper.sg2_spFood6=="2"){

            nutrition_6_2.isChecked = true

        }else{

            nutrition_6_3.isChecked = true

        }

        if(paper.sg2_spFood7=="1"){

            nutrition_7_1.isChecked = true

        }
        else if(paper.sg2_spFood7=="2"){

            nutrition_7_2.isChecked = true

        }else{

            nutrition_7_3.isChecked = true

        }

        if(paper.sg2_spFood8=="1"){

            nutrition_8_1.isChecked = true

        }
        else if(paper.sg2_spFood8=="2"){

            nutrition_8_2.isChecked = true

        }else{

            nutrition_8_3.isChecked = true

        }

        if(paper.sg2_spFood9=="1"){

            nutrition_9_1.isChecked = true

        }
        else if(paper.sg2_spFood1=="2"){

            nutrition_9_2.isChecked = true

        }else{

            nutrition_9_3.isChecked = true

        }

        if(paper.sg2_spFood10=="1"){

            nutrition_10_1.isChecked = true

        }
        else if(paper.sg2_spFood10=="2"){

            nutrition_10_2.isChecked = true

        }else{

            nutrition_10_3.isChecked = true

        }

        if(paper.sg2_spFood11=="1"){

            nutrition_11_1.isChecked = true

        }
        else if(paper.sg2_spFood11=="2"){

            nutrition_11_2.isChecked = true

        }else{

            nutrition_11_3.isChecked = true

        }

//        height.setText(paper.sg2_spHeight)
//        weight.setText(paper.sg2_spWeight)
//        waist_size.setText(paper.sg2_spWaistSize)
//        bmi.setText(paper.sg2_spBmi)

        if(paper.sg2_spFat1=="1"){

            fat_1_true.isChecked = true

        }else{

            fat_1_false.isChecked = true

        }

        if(paper.sg2_spFat2=="1"){

            fat_2_no.isChecked = true

        }else if(paper.sg2_spFat2=="2"){

            fat_2_1.isChecked = true

        }else if(paper.sg2_spFat2=="3"){

            fat_2_2.isChecked = true

        }else{

            fat_2_3.isChecked = true

        }

        if(paper.sg2_spFat3=="1"){

            fat_3_1.isChecked = true

        }else if(paper.sg2_spFat3=="2"){

            fat_3_2.isChecked = true

        }else{

            fat_3_3.isChecked = true

        }

    }

    fun GetPaper(paper:ServerPaper_Life){

        state = "getPaper"

        cannotEditQuestionnaire(nutrition_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.sg2_name
        first_serial.text = paper.sg2_jumin.substring(0, 6)
        last_serial.text = paper.sg2_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        nutrition_examination_save.visibility = View.GONE
        nutrition_examination_cancel.visibility = View.GONE
        nutrition_edit_submit.visibility = View.VISIBLE

        if(paper.sg2_spFood1=="1"){

            nutrition_1_1.isChecked = true

        }
        else if(paper.sg2_spFood1=="2"){

            nutrition_1_2.isChecked = true

        }else{

            nutrition_1_3.isChecked = true

        }

        if(paper.sg2_spFood2=="1"){

            nutrition_2_1.isChecked = true

        }
        else if(paper.sg2_spFood2=="2"){

            nutrition_2_2.isChecked = true

        }else{

            nutrition_2_3.isChecked = true

        }

        if(paper.sg2_spFood3=="1"){

            nutrition_3_1.isChecked = true

        }
        else if(paper.sg2_spFood3=="2"){

            nutrition_3_2.isChecked = true

        }else{

            nutrition_3_3.isChecked = true

        }

        if(paper.sg2_spFood4=="1"){

            nutrition_4_1.isChecked = true

        }
        else if(paper.sg2_spFood4=="2"){

            nutrition_4_2.isChecked = true

        }else{

            nutrition_4_3.isChecked = true

        }

        if(paper.sg2_spFood5=="1"){

            nutrition_5_1.isChecked = true

        }
        else if(paper.sg2_spFood5=="2"){

            nutrition_5_2.isChecked = true

        }else{

            nutrition_5_3.isChecked = true

        }

        if(paper.sg2_spFood6=="1"){

            nutrition_6_1.isChecked = true

        }
        else if(paper.sg2_spFood6=="2"){

            nutrition_6_2.isChecked = true

        }else{

            nutrition_6_3.isChecked = true

        }

        if(paper.sg2_spFood7=="1"){

            nutrition_7_1.isChecked = true

        }
        else if(paper.sg2_spFood7=="2"){

            nutrition_7_2.isChecked = true

        }else{

            nutrition_7_3.isChecked = true

        }

        if(paper.sg2_spFood8=="1"){

            nutrition_8_1.isChecked = true

        }
        else if(paper.sg2_spFood8=="2"){

            nutrition_8_2.isChecked = true

        }else{

            nutrition_8_3.isChecked = true

        }

        if(paper.sg2_spFood9=="1"){

            nutrition_9_1.isChecked = true

        }
        else if(paper.sg2_spFood1=="2"){

            nutrition_9_2.isChecked = true

        }else{

            nutrition_9_3.isChecked = true

        }

        if(paper.sg2_spFood10=="1"){

            nutrition_10_1.isChecked = true

        }
        else if(paper.sg2_spFood10=="2"){

            nutrition_10_2.isChecked = true

        }else{

            nutrition_10_3.isChecked = true

        }

        if(paper.sg2_spFood11=="1"){

            nutrition_11_1.isChecked = true

        }
        else if(paper.sg2_spFood11=="2"){

            nutrition_11_2.isChecked = true

        }else{

            nutrition_11_3.isChecked = true

        }

//        height.setText(paper.sg2_spHeight)
//        weight.setText(paper.sg2_spWeight)
//        waist_size.setText(paper.sg2_spWaistSize)
//        bmi.setText(paper.sg2_spBmi)

        if(paper.sg2_spFat1=="1"){

            fat_1_true.isChecked = true

        }else{

            fat_1_false.isChecked = true

        }

        if(paper.sg2_spFat2=="1"){

            fat_2_no.isChecked = true

        }else if(paper.sg2_spFat2=="2"){

            fat_2_1.isChecked = true

        }else if(paper.sg2_spFat2=="3"){

            fat_2_2.isChecked = true

        }else{

            fat_2_3.isChecked = true

        }

        if(paper.sg2_spFat3=="1"){

            fat_3_1.isChecked = true

        }else if(paper.sg2_spFat3=="2"){

            fat_3_2.isChecked = true

        }else{

            fat_3_3.isChecked = true

        }

    }

}