package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.PaperArray
import com.example.zzango.questionnaire.LocalList.Paper_NUTRITION
import com.example.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_nutrition_exam.*
import kotlinx.android.synthetic.main.progressbar2.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class NutritionExaminationActivity :RootActivity() {

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("sg2_spFood1") @Expose var sg2_spFood1 : String,
                         @SerializedName("sg2_spFood2") @Expose var sg2_spFood2 : String,
                         @SerializedName("sg2_spFood3") @Expose var sg2_spFood3 : String,
                         @SerializedName("sg2_spFood4") @Expose var sg2_spFood4 : String,
                         @SerializedName("sg2_spFood5") @Expose var sg2_spFood5 : String,
                         @SerializedName("sg2_spFood6") @Expose var sg2_spFood6 : String,
                         @SerializedName("sg2_spFood7") @Expose var sg2_spFood7 : String,
                         @SerializedName("sg2_spFood8") @Expose var sg2_spFood8 : String,
                         @SerializedName("sg2_spFood9") @Expose var sg2_spFood9 : String,
                         @SerializedName("sg2_spFood10") @Expose var sg2_spFood10 : String,
                         @SerializedName("sg2_spFood11") @Expose var sg2_spFood11 : String,
                         @SerializedName("sg2_spFoodSum") @Expose var sg2_spFoodSum : String,
                         @SerializedName("sg2_spFatHeight") @Expose var sg2_spFatHeight : String,
                         @SerializedName("sg2_spFatWeight") @Expose var sg2_spFatWeight : String,
                         @SerializedName("sg2_spFatWaistSize") @Expose var sg2_spFatWaistSize : String,
                         @SerializedName("sg2_spFatBmi") @Expose var sg2_spFatBmi : String,
                         @SerializedName("sg2_spFat1") @Expose var sg2_spFat1 : String,
                         @SerializedName("sg2_spFat2") @Expose var sg2_spFat2 : String,
                         @SerializedName("sg2_spFat3") @Expose var sg2_spFat3 : String) : Serializable
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition_exam)

        controlProgress(this, questionnaire_progress_wrapper, questionnaire_progress, progress_guideline, progress_guideline2, progress_guideline3, progress_guideline4, progress_guideline5, progress_guideline6, progress_guideline7, progress_guideline8)

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper_NUTRITION

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
            nutrition_examination_save.text = "다음"

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        nutrition_examination_save.setOnClickListener {

            //check function 리턴하는 boolean 값에 따라 진행
            if(check()){

                //진행상태 표시
                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                //메인 액티비티에서 네트워크 연결상태가 문자열로 저장돼있다 그걸로 구분한다.
                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    //로컬 저장
                    nutrition_exam_local_insert()

                }
                else{

                    //서버 저장
                    nutrition_exam_server_insert()

                }

            }

        }

        nutrition_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        nutrition_edit_submit.setOnClickListener {

            finish()

        }

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

    fun nutrition_exam_local_insert(){

        startActivity(Intent(this@NutritionExaminationActivity, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    fun nutrition_exam_server_insert(){

        startActivity(Intent(this@NutritionExaminationActivity, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    @SuppressLint("NewApi")
    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
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

        sg2_spFatHeight = when {

            !height.text.toString().isNullOrEmpty() -> height.text.toString()
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFatWeight = when {

            !weight.text.toString().isNullOrEmpty() -> weight.text.toString()
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFatWaistSize = when {

            !waist_size.text.toString().isNullOrEmpty() -> waist_size.text.toString()
            else -> {

                Toast.makeText(this, "체크 안된 문항이 있는지 확인해주세요", Toast.LENGTH_LONG).show()

                return false

            }
        }

        sg2_spFatBmi = when {

            !bmi.text.toString().isNullOrEmpty() -> bmi.text.toString()
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

        exam_no = MainActivity.exam_no


        PaperArray.PaperList.Arr_NUTRITION!!.add(Paper_NUTRITION(exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category, sg2_spFood1, sg2_spFood2,
                sg2_spFood3, sg2_spFood4, sg2_spFood5, sg2_spFood6, sg2_spFood7, sg2_spFood8, sg2_spFood9, sg2_spFood10,
                sg2_spFood11, sg2_spFoodSum, sg2_spFatHeight, sg2_spFatWeight, sg2_spFatWaistSize, sg2_spFatBmi, sg2_spFat1, sg2_spFat2, sg2_spFat3
        ))


        PaperArray.PaperList.Arr_RESULT!!.add(PaperArray.PaperList.Arr_NUTRITION!!)

        return true

    }

    fun GetPaper(paper:Paper_NUTRITION){

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

        height.setText(paper.sg2_spHeight)
        weight.setText(paper.sg2_spWeight)
        waist_size.setText(paper.sg2_spWaistSize)
        bmi.setText(paper.sg2_spBmi)

        if(paper.sg2_spFat1=="1"){

            fat_1_true.isChecked = true

        }else{

            fat_1_false.isChecked = true

        }

        if(paper.sg2_spFat2=="no"){

            fat_2_no.isChecked = true

        }else if(paper.sg2_spFat2=="1"){

            fat_2_1.isChecked = true

        }else if(paper.sg2_spFat2=="2"){

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