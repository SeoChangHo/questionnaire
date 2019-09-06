package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.Paper_CANCER
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import kotlinx.android.synthetic.main.activity_cancer_exam.*
import kotlinx.android.synthetic.main.activity_cancer_exam.Signature
import kotlinx.android.synthetic.main.activity_cancer_exam.first_serial
import kotlinx.android.synthetic.main.activity_cancer_exam.last_serial
import kotlinx.android.synthetic.main.activity_cancer_exam.name_edit
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NewApi")
class CancerExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancer_exam)

        var gender = ""
        if(AdditionalArr.Gender.isFemale)
        {
            gender = "여자"
        }
        else
        {
            gender = "남자"
        }
        println("성별: ${gender}")

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        cancer_1_1.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_1_true_detail)

            editTextCondition(isChecked, cancer_editText1)

        }

        cancer_2_2.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_2_false_detail)

            editTextCondition(isChecked, cancer_editText2)

        }

        cancer_3_1_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_1_wrapper)

            whenRadioChanged(buttonView, isChecked)

        }

        cancer_3_2_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_2_wrapper)

            whenRadioChanged(buttonView, isChecked)

        }

        cancer_3_3_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_3_wrapper)

            whenRadioChanged(buttonView, isChecked)

        }

        cancer_3_4_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_4_wrapper)

            whenRadioChanged(buttonView, isChecked)

        }

        cancer_3_5_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_5_wrapper)

            whenRadioChanged(buttonView, isChecked)

        }

        cancer_15_5_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_15_5_wrapper)

            whenRadioChanged(buttonView, isChecked)

        }

        cancer_3_6_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_3_6_wrapper)

            checkCondition(isChecked, cancer_3_6_1_editText)

            editTextCondition(isChecked, cancer_3_6_1_editText)

            whenRadioChanged(buttonView, isChecked)

        }

        cancer_5_0_checkBox.setOnCheckedChangeListener {

            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_5_1_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_5_2_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_5_3_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_5_4_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_5_5_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_6_0_checkBox.setOnCheckedChangeListener {

            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_6_1_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_6_2_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_6_3_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_6_4_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_6_5_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_7_0_checkBox.setOnCheckedChangeListener {

            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_7_1_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_7_2_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_7_3_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_7_4_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_7_5_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_16_0_checkBox.setOnCheckedChangeListener {

            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_16_1_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_16_2_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_16_3_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_16_4_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_16_5_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_16_6_checkBox.setOnCheckedChangeListener {
            buttonView, isChecked ->

            cancerCheckCondition(buttonView, isChecked)

        }

        cancer_8_1.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_8_detail)

            editTextCondition(isChecked, cancer_editText3)

        }

        cancer_9_3.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, cancer_9_detail)

            editTextCondition(isChecked, cancer_editText4)

        }

        cancer_examination_save.setOnClickListener {

            if(check()){
                if(MainActivity.chart.isEmpty()){
                    if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local")){
                        ChartDivision.ChartDivision.local_each_insert(this, 6)
                    }else{
                        ChartDivision.ChartDivision.server_insert(this)
                    }
                }else{
                    if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local")){
                        ChartDivision.ChartDivision.local_insert(this)
                    }else{
                        ChartDivision.ChartDivision.server_insert(this)
                    }
                }

            }

        }

        cancer_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        cancer_edit_submit.setOnClickListener {

            finish()

        }

        if(SavePaper.Total.temp_Cancer != null){
            whenTempLoad(SavePaper.Total.temp_Cancer!!)
        }else if(SavedListObject.SavedList.savedDataClass.cancerSaved){
            whenTempLoad(SavePaper.Total.Array[10] as Paper_CANCER)
        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        //서버에서 들어온것 일때 구분 사인은 안보이게 처리
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_CANCER) {

                var paper = intent.getSerializableExtra("paper") as Paper_CANCER

                GetPaper(paper)

                try {
                    //var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature,0, paper.signature.size)

                    //Signature.setImageBitmap(bmp)

                }
                catch (e:Exception)
                {
                    println(e.message)
                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Cancer

                GetPaper(paper)

            }

        }else{
            name_edit.text = MainActivity.login_user_name
            first_serial.text = MainActivity.user_first_serial
            last_serial.text = MainActivity.user_last_serial

            if(MainActivity.chart.isEmpty()){
                cancer_examination_save.text = "저장"
            }else{
                if(ChartDivision.ChartDivision.next_or_save(6)){
                    cancer_examination_save.text = "다음"
                }else{
                    cancer_examination_save.text = "저장"
                }
            }

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if(!AdditionalArr.Gender.isFemale){

            cancer_question_4_3.visibility = View.GONE
            cancer_4_3_radio.visibility = View.GONE

            cancer_question_4_7.visibility = View.GONE
            cancer_4_7_radio.visibility = View.GONE

            family_medical_history3.visibility = View.GONE

            cancer_question_8.visibility = View.GONE
            cancer_question_9.visibility = View.GONE
            cancer_question_10.visibility = View.GONE
            cancer_question_11.visibility = View.GONE
            cancer_question_12.visibility = View.GONE
            cancer_question_13.visibility = View.GONE
            cancer_question_14.visibility = View.GONE

            cancer_8_radio.visibility = View.GONE
            cancer_9_radio.visibility = View.GONE
            cancer_10_radio.visibility = View.GONE
            cancer_11_radio.visibility = View.GONE
            cancer_12_radio.visibility = View.GONE
            cancer_13_radio.visibility = View.GONE
            cancer_14_radio.visibility = View.GONE

        }else{
            cancer_question_4_3.visibility = View.VISIBLE
            cancer_4_3_radio.visibility = View.VISIBLE

            cancer_question_4_7.visibility = View.VISIBLE
            cancer_4_7_radio.visibility = View.VISIBLE

            family_medical_history3.visibility = View.VISIBLE

            cancer_question_8.visibility = View.VISIBLE
            cancer_question_9.visibility = View.VISIBLE
            cancer_question_10.visibility = View.VISIBLE
            cancer_question_11.visibility = View.VISIBLE
            cancer_question_12.visibility = View.VISIBLE
            cancer_question_13.visibility = View.VISIBLE
            cancer_question_14.visibility = View.VISIBLE

            cancer_8_radio.visibility = View.VISIBLE
            cancer_9_radio.visibility = View.VISIBLE
            cancer_10_radio.visibility = View.VISIBLE
            cancer_11_radio.visibility = View.VISIBLE
            cancer_12_radio.visibility = View.VISIBLE
            cancer_13_radio.visibility = View.VISIBLE
            cancer_14_radio.visibility = View.VISIBLE

        }

        controlProgress(this)

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

    fun whenTempSave() {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.CANCER.EN_NM
        var ck1 = ""
        var ck1_1 = ""
        var ck2 = ""
        var ck2_1 = ""
        var ck3_1 = ""
        var ck3_1_1 = ""
        var ck3_1_2 = ""
        var ck3_1_3 = ""
        var ck3_1_4 = ""
        var ck3_1_5 = ""
        var ck3_2 = ""
        var ck3_2_1 = ""
        var ck3_2_2 = ""
        var ck3_2_3 = ""
        var ck3_2_4 = ""
        var ck3_2_5 = ""
        var ck3_3 = ""
        var ck3_3_1 = ""
        var ck3_3_2 = ""
        var ck3_3_3 = ""
        var ck3_3_4 = ""
        var ck3_3_5 = ""
        var ck3_4 = ""
        var ck3_4_1 = ""
        var ck3_4_2 = ""
        var ck3_4_3 = ""
        var ck3_4_4 = ""
        var ck3_4_5 = ""
        var ck3_5 = ""
        var ck3_5_1 = ""
        var ck3_5_2 = ""
        var ck3_5_3 = ""
        var ck3_5_4 = ""
        var ck3_5_5 = ""
        var ck3_6 = ""
        var ck3_6_1 = ""
        var ck3_6_2 = ""
        var ck3_6_3 = ""
        var ck3_6_4 = ""
        var ck3_6_5 = ""
        var ck3_6_kita = ""
        var ck4_1 = ""
        var ck4_2 = ""
        var ck4_3 = ""
        var ck4_4 = ""
        var ck4_5 = ""
        var ck4_6 = ""
        var ck4_7 = ""
        var ck4_8 = ""
        var ck4_9 = ""
        var ck5_1 = ""
        var ck5_2 = ""
        var ck5_3 = ""
        var ck5_4 = ""
        var ck5_5 = ""
        var ck6_1 = ""
        var ck6_2 = ""
        var ck6_3 = ""
        var ck6_4 = ""
        var ck6_5 = ""
        var ck7_1 = ""
        var ck7_2 = ""
        var ck7_3 = ""
        var ck7_4 = ""
        var ck7_5 = ""
        var ck8_1 = ""
        var ck8_2 = ""
        var ck9_1 = ""
        var ck9_2 = ""
        var ck10 = ""
        var ck11 = ""
        var ck12 = ""
        var ck13 = ""
        var ck14 = ""
        var ck15_5 = ""
        var ck15_5_1 = ""
        var ck15_5_2 = ""
        var ck15_5_3 = ""
        var ck15_5_4 = ""
        var ck15_5_5 = ""
        var ck16_1 = ""
        var ck16_2 = ""
        var ck16_3 = ""
        var ck16_4 = ""
        var ck16_5 = ""
        var ck16_6 = ""


        if (!name_edit.text.isNullOrEmpty()) {
            name = name_edit.text.toString()
        }

        if (!first_serial.text.isNullOrEmpty()) {
            first_serial_text = first_serial.text.toString()
        }

        if (!last_serial.text.isNullOrEmpty()) {
            last_serial_text = last_serial.text.toString()
        }

        when {
            cancer_1_1.isChecked -> {
                ck1 = "1"
                ck1_1 = when {
                    !cancer_editText1.text.isNullOrEmpty() -> cancer_editText1.text.toString()
                    else -> ""
                }
            }
            cancer_1_2.isChecked -> ck1 = "2"
            else -> ""
        }

        when {
            cancer_2_1.isChecked -> ck2 = "1"
            cancer_2_2.isChecked -> {
                ck2 = "2"
                ck2_1 = when {
                    !cancer_editText2.text.isNullOrEmpty() -> cancer_editText2.text.toString()
                    else -> ""
                }
            }
            else -> ""
        }

        when {
            cancer_3_1_1.isChecked -> ck3_1 = "1"
            cancer_3_1_2.isChecked -> ck3_1 = "3"
            cancer_3_1_3.isChecked -> {
                ck3_1 = "2"

                ck3_1_1 = when {
                    cancer_3_1_checkBox1.isChecked -> "2"
                    else -> "1"
                }
                ck3_1_2 = when {
                    cancer_3_1_checkBox2.isChecked -> "2"
                    else -> "1"
                }
                ck3_1_3 = when {
                    cancer_3_1_checkBox3.isChecked -> "2"
                    else -> "1"
                }
                ck3_1_4 = when {
                    cancer_3_1_checkBox4.isChecked -> "2"
                    else -> "1"
                }
                ck3_1_5 = when {
                    cancer_3_1_checkBox5.isChecked -> "2"
                    else -> "1"
                }

                when {
                    cancer_3_1_checkBox1.isChecked -> {
                    }
                    cancer_3_1_checkBox2.isChecked -> {
                    }
                    cancer_3_1_checkBox3.isChecked -> {
                    }
                    cancer_3_1_checkBox4.isChecked -> {
                    }
                    cancer_3_1_checkBox5.isChecked -> {
                    }
                    else -> ""
                }

            }
            else -> ""
        }

        when {
            cancer_3_2_1.isChecked -> ck3_2 = "1"
            cancer_3_2_2.isChecked -> ck3_2 = "3"
            cancer_3_2_3.isChecked -> {
                ck3_2 = "2"

                when {
                    cancer_3_2_checkBox1.isChecked -> {
                    }
                    cancer_3_2_checkBox2.isChecked -> {
                    }
                    cancer_3_2_checkBox3.isChecked -> {
                    }
                    cancer_3_2_checkBox4.isChecked -> {
                    }
                    cancer_3_2_checkBox5.isChecked -> {
                    }
                    else -> ""
                }

                ck3_2_1 = when {
                    cancer_3_2_checkBox1.isChecked -> "2"
                    else -> "1"
                }
                ck3_2_2 = when {
                    cancer_3_2_checkBox2.isChecked -> "2"
                    else -> "1"
                }
                ck3_2_3 = when {
                    cancer_3_2_checkBox3.isChecked -> "2"
                    else -> "1"
                }
                ck3_2_4 = when {
                    cancer_3_2_checkBox4.isChecked -> "2"
                    else -> "1"
                }
                ck3_2_5 = when {
                    cancer_3_2_checkBox5.isChecked -> "2"
                    else -> "1"
                }

            }
            else -> ""
        }

        when {
            cancer_3_3_1.isChecked -> ck3_3 = "1"
            cancer_3_3_2.isChecked -> ck3_3 = "3"
            cancer_3_3_3.isChecked -> {
                ck3_3 = "2"
                when {
                    cancer_3_3_checkBox1.isChecked -> {
                    }
                    cancer_3_3_checkBox2.isChecked -> {
                    }
                    cancer_3_3_checkBox3.isChecked -> {
                    }
                    cancer_3_3_checkBox4.isChecked -> {
                    }
                    cancer_3_3_checkBox5.isChecked -> {
                    }
                    else -> ""
                }

                ck3_3_1 = when {
                    cancer_3_3_checkBox1.isChecked -> "2"
                    else -> "1"
                }
                ck3_3_2 = when {
                    cancer_3_3_checkBox2.isChecked -> "2"
                    else -> "1"
                }
                ck3_3_3 = when {
                    cancer_3_3_checkBox3.isChecked -> "2"
                    else -> "1"
                }
                ck3_3_4 = when {
                    cancer_3_3_checkBox4.isChecked -> "2"
                    else -> "1"
                }
                ck3_3_5 = when {
                    cancer_3_3_checkBox5.isChecked -> "2"
                    else -> "1"
                }

            }
            else -> ""
        }


        when {
            cancer_3_4_1.isChecked -> ck3_4 = "1"
            cancer_3_4_2.isChecked -> ck3_4 = "3"
            cancer_3_4_3.isChecked -> {
                ck3_4 = "2"
                when {
                    cancer_3_4_checkBox1.isChecked -> {
                    }
                    cancer_3_4_checkBox2.isChecked -> {
                    }
                    cancer_3_4_checkBox3.isChecked -> {
                    }
                    cancer_3_4_checkBox4.isChecked -> {
                    }
                    cancer_3_4_checkBox5.isChecked -> {
                    }
                    else -> ""
                }

                ck3_4_1 = when {
                    cancer_3_4_checkBox1.isChecked -> "2"
                    else -> "1"
                }
                ck3_4_2 = when {
                    cancer_3_4_checkBox2.isChecked -> "2"
                    else -> "1"
                }
                ck3_4_3 = when {
                    cancer_3_4_checkBox3.isChecked -> "2"
                    else -> "1"
                }
                ck3_4_4 = when {
                    cancer_3_4_checkBox4.isChecked -> "2"
                    else -> "1"
                }
                ck3_4_5 = when {
                    cancer_3_4_checkBox5.isChecked -> "2"
                    else -> "1"
                }

            }
            else -> ""
        }

        when {
            cancer_3_5_1.isChecked -> ck3_5 = "1"
            cancer_3_5_2.isChecked -> ck3_5 = "3"
            cancer_3_5_3.isChecked -> {
                ck3_5 = "2"
                when {
                    cancer_3_5_checkBox1.isChecked -> {
                    }
                    cancer_3_5_checkBox2.isChecked -> {
                    }
                    cancer_3_5_checkBox3.isChecked -> {
                    }
                    cancer_3_5_checkBox4.isChecked -> {
                    }
                    cancer_3_5_checkBox5.isChecked -> {
                    }
                    else -> ""
                }

                ck3_5_1 = when {
                    cancer_3_5_checkBox1.isChecked -> "2"
                    else -> "1"
                }
                ck3_5_2 = when {
                    cancer_3_5_checkBox2.isChecked -> "2"
                    else -> "1"
                }
                ck3_5_3 = when {
                    cancer_3_5_checkBox3.isChecked -> "2"
                    else -> "1"
                }
                ck3_5_4 = when {
                    cancer_3_5_checkBox4.isChecked -> "2"
                    else -> "1"
                }
                ck3_5_5 = when {
                    cancer_3_5_checkBox5.isChecked -> "2"
                    else -> "1"
                }

            }
            else -> ""
        }

        when {
            cancer_3_6_1.isChecked -> ck3_6 = "1"
            cancer_3_6_2.isChecked -> ck3_6 = "3"
            cancer_3_6_3.isChecked -> {
                ck3_6 = "2"
                when {
                    cancer_3_6_checkBox1.isChecked -> {
                    }
                    cancer_3_6_checkBox2.isChecked -> {
                    }
                    cancer_3_6_checkBox3.isChecked -> {
                    }
                    cancer_3_6_checkBox4.isChecked -> {
                    }
                    cancer_3_6_checkBox5.isChecked -> {
                    }
                    else -> ""
                }

                ck3_6_1 = when {
                    cancer_3_6_checkBox1.isChecked -> "2"
                    else -> "1"
                }
                ck3_6_2 = when {
                    cancer_3_6_checkBox2.isChecked -> "2"
                    else -> "1"
                }
                ck3_6_3 = when {
                    cancer_3_6_checkBox3.isChecked -> "2"
                    else -> "1"
                }
                ck3_6_4 = when {
                    cancer_3_6_checkBox4.isChecked -> "2"
                    else -> "1"
                }
                ck3_6_5 = when {
                    cancer_3_6_checkBox5.isChecked -> "2"
                    else -> "1"
                }

                when {
                    cancer_3_6_1_editText.text.toString() != "" -> ck3_6_kita = cancer_3_6_1_editText.text.toString()
                }

            }
            else -> ""
        }

        when {
            cancer_4_1_1.isChecked -> ck4_1 = "1"
            cancer_4_1_2.isChecked -> ck4_1 = "2"
            cancer_4_1_3.isChecked -> ck4_1 = "3"
            cancer_4_1_4.isChecked -> ck4_1 = "4"
        }

        when {
            cancer_4_2_1.isChecked -> ck4_2 = "1"
            cancer_4_2_2.isChecked -> ck4_2 = "2"
            cancer_4_2_3.isChecked -> ck4_2 = "3"
            cancer_4_2_4.isChecked -> ck4_2 = "4"
        }

        when {
            AdditionalArr.Gender.isFemale -> {
                when {
                    cancer_4_3_1.isChecked -> ck4_3 = "1"
                    cancer_4_3_2.isChecked -> ck4_3 = "2"
                    cancer_4_3_3.isChecked -> ck4_3 = "3"
                    cancer_4_3_4.isChecked -> ck4_3 = "4"
                }
            }
        }

        when {
            cancer_4_4_1.isChecked -> ck4_4 = "1"
            cancer_4_4_2.isChecked -> ck4_4 = "2"
            cancer_4_4_3.isChecked -> ck4_4 = "3"
            cancer_4_4_4.isChecked -> ck4_4 = "4"
        }

        when {
            cancer_4_5_1.isChecked -> ck4_5 = "1"
            cancer_4_5_2.isChecked -> ck4_5 = "2"
            cancer_4_5_3.isChecked -> ck4_5 = "3"
            cancer_4_5_4.isChecked -> ck4_5 = "4"
        }

        when {
            cancer_4_6_1.isChecked -> ck4_6 = "1"
            cancer_4_6_2.isChecked -> ck4_6 = "2"
            cancer_4_6_3.isChecked -> ck4_6 = "3"
            cancer_4_6_4.isChecked -> ck4_6 = "4"
        }

        when {
            AdditionalArr.Gender.isFemale -> {
                when {
                    cancer_4_7_1.isChecked -> ck4_7 = "1"
                    cancer_4_7_2.isChecked -> ck4_7 = "2"
                    cancer_4_7_3.isChecked -> ck4_7 = "3"
                    cancer_4_7_4.isChecked -> ck4_7 = "4"
                }
            }
        }

        when {
            cancer_4_9_1.isChecked -> ck4_9 = "1"
            cancer_4_9_2.isChecked -> ck4_9 = "2"
            cancer_4_9_3.isChecked -> ck4_9 = "3"
            cancer_4_9_4.isChecked -> ck4_9 = "4"
        }

        when {
            cancer_4_8_1.isChecked -> ck4_8 = "1"
            cancer_4_8_2.isChecked -> ck4_8 = "2"
            cancer_4_8_3.isChecked -> ck4_8 = "3"
            cancer_4_8_4.isChecked -> ck4_8 = "4"
        }

        ck5_1 = when {
            cancer_5_1_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck5_2 = when {
            cancer_5_2_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck5_3 = when {
            cancer_5_3_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck5_4 = when {
            cancer_5_4_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck5_5 = when {
            cancer_5_5_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck6_1 = when {
            cancer_6_1_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck6_2 = when {
            cancer_6_2_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck6_3 = when {
            cancer_6_3_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck6_4 = when {
            cancer_6_4_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck6_5 = when {
            cancer_6_5_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck7_1 = when {
            cancer_7_1_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck7_2 = when {
            cancer_7_2_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck7_3 = when {
            cancer_7_3_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck7_4 = when {
            cancer_7_4_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck7_5 = when {
            cancer_7_5_checkBox.isChecked -> "2"
            else -> "1"
        }


        ck16_1 = when {
            cancer_16_1_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck16_2 = when {
            cancer_16_2_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck16_3 = when {
            cancer_16_3_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck16_4 = when {
            cancer_16_4_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck16_5 = when {
            cancer_16_5_checkBox.isChecked -> "2"
            else -> "1"
        }

        ck16_6 = when {
            cancer_16_6_checkBox.isChecked -> "2"
            else -> "1"
        }

        when {
            AdditionalArr.Gender.isFemale -> {
                when {
                    cancer_8_1.isChecked -> {
                        ck8_1 = "1"
                        when {
                            !cancer_editText3.text.isNullOrEmpty() -> ck8_2 = cancer_editText3.text.toString()
                            else -> ""
                        }
                    }
                    cancer_8_2.isChecked -> ck8_1 = "2"
                    else -> ""
                }

                when {
                    cancer_9_1.isChecked -> ck9_1 = "1"
                    cancer_9_2.isChecked -> ck9_1 = "2"
                    cancer_9_3.isChecked -> {
                        ck9_1 = "3"
                        ck9_2 = when {
                            !cancer_editText4.text.isNullOrEmpty() -> cancer_editText4.text.toString()
                            else -> ""
                        }
                    }
                    else -> ""
                }

                ck10 = when {
                    cancer_10_1.isChecked -> "1"
                    cancer_10_2.isChecked -> "2"
                    cancer_10_3.isChecked -> "3"
                    cancer_10_4.isChecked -> "4"
                    cancer_10_5.isChecked -> "5"
                    else -> ""
                }

                ck11 = when {
                    cancer_11_1.isChecked -> "1"
                    cancer_11_2.isChecked -> "2"
                    cancer_11_3.isChecked -> "3"
                    else -> ""
                }

                ck12 = when {
                    cancer_12_1.isChecked -> "1"
                    cancer_12_2.isChecked -> "2"
                    cancer_12_3.isChecked -> "3"
                    cancer_12_4.isChecked -> "4"
                    else -> ""
                }

                ck13 = when {
                    cancer_13_1.isChecked -> "1"
                    cancer_13_2.isChecked -> "2"
                    cancer_13_3.isChecked -> "3"
                    else -> ""
                }

                ck14 = when {
                    cancer_14_1.isChecked -> "1"
                    cancer_14_2.isChecked -> "2"
                    cancer_14_3.isChecked -> "3"
                    cancer_14_4.isChecked -> "4"
                    else -> ""
                }
            }
        }

        SavePaper.Total.temp_Cancer = Paper_CANCER(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                ck1, ck1_1, ck2, ck2_1,
                ck3_1, ck3_1_1, ck3_1_2, ck3_1_3, ck3_1_4, ck3_1_5,
                ck3_2, ck3_2_1, ck3_2_2, ck3_2_3, ck3_2_4, ck3_2_5,
                ck3_3, ck3_3_1, ck3_3_2, ck3_3_3, ck3_3_4, ck3_3_5,
                ck3_4, ck3_4_1, ck3_4_2, ck3_4_3, ck3_4_4, ck3_4_5,
                ck3_5, ck3_5_1, ck3_5_2, ck3_5_3, ck3_5_4, ck3_5_5,
                ck3_6, ck3_6_1, ck3_6_2, ck3_6_3, ck3_6_4, ck3_6_5, ck3_6_kita,
                ck4_1, ck4_2, ck4_3, ck4_4, ck4_5, ck4_6,
                ck4_7, ck4_8, ck4_9, ck5_1, ck5_2, ck5_3, ck5_4, ck5_5, ck6_1, ck6_2, ck6_3, ck6_4, ck6_5, ck7_1, ck7_2, ck7_3, ck7_4, ck7_5, ck8_1, ck8_2, ck9_1, ck9_2, ck10, ck11, ck12, ck13, ck14, ck15_5, ck15_5_1, ck15_5_2, ck15_5_3, ck15_5_4, ck15_5_5, ck16_1, ck16_2, ck16_3, ck16_4, ck16_5, ck16_6)

    }

    fun check() : Boolean {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.CANCER.EN_NM
        var ck1 = ""
        var ck1_1 = ""
        var ck2 = ""
        var ck2_1 = ""
        var ck3_1 = ""
        var ck3_1_1 = ""
        var ck3_1_2 = ""
        var ck3_1_3 = ""
        var ck3_1_4 = ""
        var ck3_1_5 = ""
        var ck3_2 = ""
        var ck3_2_1 = ""
        var ck3_2_2 = ""
        var ck3_2_3 = ""
        var ck3_2_4 = ""
        var ck3_2_5 = ""
        var ck3_3 = ""
        var ck3_3_1 = ""
        var ck3_3_2 = ""
        var ck3_3_3 = ""
        var ck3_3_4 = ""
        var ck3_3_5 = ""
        var ck3_4 = ""
        var ck3_4_1 = ""
        var ck3_4_2 = ""
        var ck3_4_3 = ""
        var ck3_4_4 = ""
        var ck3_4_5 = ""
        var ck3_5 = ""
        var ck3_5_1 = ""
        var ck3_5_2 = ""
        var ck3_5_3 = ""
        var ck3_5_4 = ""
        var ck3_5_5 = ""
        var ck3_6 = ""
        var ck3_6_1 = ""
        var ck3_6_2 = ""
        var ck3_6_3 = ""
        var ck3_6_4 = ""
        var ck3_6_5 = ""
        var ck3_6_kita = ""
        var ck4_1 = ""
        var ck4_2 = ""
        var ck4_3 = ""
        var ck4_4 = ""
        var ck4_5 = ""
        var ck4_6 = ""
        var ck4_7 = ""
        var ck4_8 = ""
        var ck4_9 = ""
        var ck5_1 = ""
        var ck5_2 = ""
        var ck5_3 = ""
        var ck5_4 = ""
        var ck5_5 = ""
        var ck6_1 = ""
        var ck6_2 = ""
        var ck6_3 = ""
        var ck6_4 = ""
        var ck6_5 = ""
        var ck7_1 = ""
        var ck7_2 = ""
        var ck7_3 = ""
        var ck7_4 = ""
        var ck7_5 = ""
        var ck8_1 = ""
        var ck8_2 = ""
        var ck9_1 = ""
        var ck9_2 = ""
        var ck10 = ""
        var ck11 = ""
        var ck12 = ""
        var ck13 = ""
        var ck14 = ""
        var ck15_5 = ""
        var ck15_5_1 = ""
        var ck15_5_2 = ""
        var ck15_5_3 = ""
        var ck15_5_4 = ""
        var ck15_5_5 = ""
        var ck16_1 = ""
        var ck16_2 = ""
        var ck16_3 = ""
        var ck16_4 = ""
        var ck16_5 = ""
        var ck16_6 = ""

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

        if(cancer_1_1.isChecked){
            ck1 = "1"
            if(!cancer_editText1.text.isNullOrEmpty()){
                ck1_1 = cancer_editText1.text.toString()
            }else{
                Toast.makeText(this, "1번 증상을 작성해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else if(cancer_1_2.isChecked){
            ck1 = "2"
        }else{
            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_2_1.isChecked){
            ck2 = "1"
        }else if(cancer_2_2.isChecked){
            ck2 = "2"
            if(!cancer_editText2.text.isNullOrEmpty()){
                ck2_1 = cancer_editText2.text.toString()
            }else{
                Toast.makeText(this, "2번 증상을 작성해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }else{
            Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_1_1.isChecked){
            ck3_1 = "1"
        }else if(cancer_3_1_2.isChecked){
            ck3_1 = "3"
        }else if(cancer_3_1_3.isChecked){
            ck3_1 = "2"

            if(cancer_3_1_checkBox1.isChecked){
                ck3_1_1 = "2"
            }else{
                ck3_1_1 = "1"
            }
            if(cancer_3_1_checkBox2.isChecked){
                ck3_1_2 = "2"
            }else{
                ck3_1_2 = "1"
            }
            if(cancer_3_1_checkBox3.isChecked){
                ck3_1_3 = "2"
            }else{
                ck3_1_3 = "1"
            }
            if(cancer_3_1_checkBox4.isChecked){
                ck3_1_4 = "2"
            }else{
                ck3_1_4 = "1"
            }
            if(cancer_3_1_checkBox5.isChecked){
                ck3_1_5 = "2"
            }else{
                ck3_1_5 = "1"
            }

            if(cancer_3_1_checkBox1.isChecked){
            }else if(cancer_3_1_checkBox2.isChecked){
            }else if(cancer_3_1_checkBox3.isChecked){
            }else if(cancer_3_1_checkBox4.isChecked){
            }else if(cancer_3_1_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-1번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

        }else{
            Toast.makeText(this, "3-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_2_1.isChecked){
            ck3_2 = "1"
        }else if(cancer_3_2_2.isChecked){
            ck3_2 = "3"
        }else if(cancer_3_2_3.isChecked){
            ck3_2 = "2"

            if(cancer_3_2_checkBox1.isChecked){
            }else if(cancer_3_2_checkBox2.isChecked){
            }else if(cancer_3_2_checkBox3.isChecked){
            }else if(cancer_3_2_checkBox4.isChecked){
            }else if(cancer_3_2_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-2번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_2_checkBox1.isChecked){
                ck3_2_1 = "2"
            }else{
                ck3_2_1 = "1"
            }
            if(cancer_3_2_checkBox2.isChecked){
                ck3_2_2 = "2"
            }else{
                ck3_2_2 = "1"
            }
            if(cancer_3_2_checkBox3.isChecked){
                ck3_2_3 = "2"
            }else{
                ck3_2_3 = "1"
            }
            if(cancer_3_2_checkBox4.isChecked){
                ck3_2_4 = "2"
            }else{
                ck3_2_4 = "1"
            }
            if(cancer_3_2_checkBox5.isChecked){
                ck3_2_5 = "2"
            }else{
                ck3_2_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_3_1.isChecked){
            ck3_3 = "1"
        }else if(cancer_3_3_2.isChecked){
            ck3_3 = "3"
        }else if(cancer_3_3_3.isChecked){
            ck3_3 = "2"
            if(cancer_3_3_checkBox1.isChecked){
            }else if(cancer_3_3_checkBox2.isChecked){
            }else if(cancer_3_3_checkBox3.isChecked){
            }else if(cancer_3_3_checkBox4.isChecked){
            }else if(cancer_3_3_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-3번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_3_checkBox1.isChecked) {
                ck3_3_1 = "2"
            }else{
                ck3_3_1 = "1"
            }
            if(cancer_3_3_checkBox2.isChecked){
                ck3_3_2 = "2"
            }else{
                ck3_3_2 = "1"
            }
            if(cancer_3_3_checkBox3.isChecked){
                ck3_3_3 = "2"
            }else{
                ck3_3_3 = "1"
            }
            if(cancer_3_3_checkBox4.isChecked){
                ck3_3_4 = "2"
            }else{
                ck3_3_4 = "1"
            }
            if(cancer_3_3_checkBox5.isChecked){
                ck3_3_5 = "2"
            }else{
                ck3_3_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cancer_3_4_1.isChecked){
            ck3_4 = "1"
        }else if(cancer_3_4_2.isChecked){
            ck3_4 = "3"
        }else if(cancer_3_4_3.isChecked){
            ck3_4 = "2"
            if(cancer_3_4_checkBox1.isChecked){
            }else if(cancer_3_4_checkBox2.isChecked){
            }else if(cancer_3_4_checkBox3.isChecked){
            }else if(cancer_3_4_checkBox4.isChecked){
            }else if(cancer_3_4_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-4번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_4_checkBox1.isChecked){
                ck3_4_1 = "2"
            }else{
                ck3_4_1 = "1"
            }
            if(cancer_3_4_checkBox2.isChecked){
                ck3_4_2 = "2"
            }else{
                ck3_4_2 = "1"
            }
            if(cancer_3_4_checkBox3.isChecked){
                ck3_4_3 = "2"
            }else{
                ck3_4_3 = "1"
            }
            if(cancer_3_4_checkBox4.isChecked){
                ck3_4_4 = "2"
            }else{
                ck3_4_4 = "1"
            }
            if(cancer_3_4_checkBox5.isChecked){
                ck3_4_5 = "2"
            }else{
                ck3_4_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_5_1.isChecked){
            ck3_5 = "1"
        }else if(cancer_3_5_2.isChecked){
            ck3_5 = "3"
        }else if(cancer_3_5_3.isChecked){
            ck3_5 = "2"
            if(cancer_3_5_checkBox1.isChecked){
            }else if(cancer_3_5_checkBox2.isChecked){
            }else if(cancer_3_5_checkBox3.isChecked){
            }else if(cancer_3_5_checkBox4.isChecked){
            }else if(cancer_3_5_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-5번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_5_checkBox1.isChecked){
                ck3_5_1 = "2"
            }else{
                ck3_5_1 = "1"
            }
            if(cancer_3_5_checkBox2.isChecked){
                ck3_5_2 = "2"
            }else{
                ck3_5_2 = "1"
            }
            if(cancer_3_5_checkBox3.isChecked){
                ck3_5_3 = "2"
            }else{
                ck3_5_3 = "1"
            }
            if(cancer_3_5_checkBox4.isChecked){
                ck3_5_4 = "2"
            }else{
                ck3_5_4 = "1"
            }
            if(cancer_3_5_checkBox5.isChecked){
                ck3_5_5 = "2"
            }else{
                ck3_5_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_15_5_1.isChecked){
            ck15_5 = "1"
        }else if(cancer_15_5_2.isChecked){
            ck15_5 = "3"
        }else if(cancer_15_5_3.isChecked){
            ck15_5 = "2"
            if(cancer_15_5_checkBox1.isChecked){
            }else if(cancer_15_5_checkBox2.isChecked){
            }else if(cancer_15_5_checkBox3.isChecked){
            }else if(cancer_15_5_checkBox4.isChecked){
            }else if(cancer_15_5_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-6번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_15_5_checkBox1.isChecked){
                ck15_5_1 = "2"
            }else{
                ck15_5_1 = "1"
            }
            if(cancer_15_5_checkBox2.isChecked){
                ck15_5_2 = "2"
            }else{
                ck15_5_2 = "1"
            }
            if(cancer_15_5_checkBox3.isChecked){
                ck15_5_3 = "2"
            }else{
                ck15_5_3 = "1"
            }
            if(cancer_15_5_checkBox4.isChecked){
                ck15_5_4 = "2"
            }else{
                ck15_5_4 = "1"
            }
            if(cancer_15_5_checkBox5.isChecked){
                ck15_5_5 = "2"
            }else{
                ck15_5_5 = "1"
            }

        }else{
            Toast.makeText(this, "3-6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_3_6_1.isChecked){
            ck3_6 = "1"
        }else if(cancer_3_6_2.isChecked){
            ck3_6 = "3"
        }else if(cancer_3_6_3.isChecked){
            ck3_6 = "2"
            if(cancer_3_6_checkBox1.isChecked){
            }else if(cancer_3_6_checkBox2.isChecked){
            }else if(cancer_3_6_checkBox3.isChecked){
            }else if(cancer_3_6_checkBox4.isChecked){
            }else if(cancer_3_6_checkBox5.isChecked){
            }else{
                Toast.makeText(this, "3-7번 가족관계를 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_3_6_checkBox1.isChecked){
                ck3_6_1 = "2"
            }else{
                ck3_6_1 = "1"
            }
            if(cancer_3_6_checkBox2.isChecked){
                ck3_6_2 = "2"
            }else{
                ck3_6_2 = "1"
            }
            if(cancer_3_6_checkBox3.isChecked){
                ck3_6_3 = "2"
            }else{
                ck3_6_3 = "1"
            }
            if(cancer_3_6_checkBox4.isChecked){
                ck3_6_4 = "2"
            }else{
                ck3_6_4 = "1"
            }
            if(cancer_3_6_checkBox5.isChecked){
                ck3_6_5 = "2"
            }else{
                ck3_6_5 = "1"
            }

            if(cancer_3_6_1_editText.text.toString() != ""){
                ck3_6_kita = cancer_3_6_1_editText.text.toString()
            }

        }else{
            Toast.makeText(this, "3-7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_4_1_1.isChecked){
            ck4_1 = "1"
        }else if(cancer_4_1_2.isChecked){
            ck4_1 = "2"
        }else if(cancer_4_1_3.isChecked){
            ck4_1 = "3"
        }else if(cancer_4_1_4.isChecked){
            ck4_1 = "4"
        }else{
            Toast.makeText(this, "4-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_4_2_1.isChecked){
            ck4_2 = "1"
        }else if(cancer_4_2_2.isChecked){
            ck4_2 = "2"
        }else if(cancer_4_2_3.isChecked){
            ck4_2 = "3"
        }else if(cancer_4_2_4.isChecked){
            ck4_2 = "4"
        }else{
            Toast.makeText(this, "4-2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(AdditionalArr.Gender.isFemale){
            if(cancer_4_3_1.isChecked){
                ck4_3 = "1"
            }else if(cancer_4_3_2.isChecked){
                ck4_3 = "2"
            }else if(cancer_4_3_3.isChecked){
                ck4_3 = "3"
            }else if(cancer_4_3_4.isChecked){
                ck4_3 = "4"
            }else{
                Toast.makeText(this, "4-3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(cancer_4_4_1.isChecked){
            ck4_4 = "1"
        }else if(cancer_4_4_2.isChecked){
            ck4_4 = "2"
        }else if(cancer_4_4_3.isChecked){
            ck4_4 = "3"
        }else if(cancer_4_4_4.isChecked){
            ck4_4 = "4"
        }else{
            Toast.makeText(this, "4-4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_4_5_1.isChecked){
            ck4_5 = "1"
        }else if(cancer_4_5_2.isChecked){
            ck4_5 = "2"
        }else if(cancer_4_5_3.isChecked){
            ck4_5 = "3"
        }else if(cancer_4_5_4.isChecked){
            ck4_5 = "4"
        }else{
            Toast.makeText(this, "4-5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cancer_4_6_1.isChecked){
            ck4_6 = "1"
        }else if(cancer_4_6_2.isChecked){
            ck4_6 = "2"
        }else if(cancer_4_6_3.isChecked){
            ck4_6 = "3"
        }else if(cancer_4_6_4.isChecked){
            ck4_6 = "4"
        }else{
            Toast.makeText(this, "4-6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(AdditionalArr.Gender.isFemale){
            if(cancer_4_7_1.isChecked){
                ck4_7 = "1"
            }else if(cancer_4_7_2.isChecked){
                ck4_7 = "2"
            }else if(cancer_4_7_3.isChecked){
                ck4_7 = "3"
            }else if(cancer_4_7_4.isChecked){
                ck4_7 = "4"
            }else{
                Toast.makeText(this, "4-7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if(cancer_4_9_1.isChecked){
            ck4_9 = "1"
        }else if(cancer_4_9_2.isChecked){
            ck4_9 = "2"
        }else if(cancer_4_9_3.isChecked){
            ck4_9 = "3"
        }else if(cancer_4_9_4.isChecked){
            ck4_9 = "4"
        }else{
            Toast.makeText(this, "4-8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_4_8_1.isChecked){
            ck4_8 = "1"
        }else if(cancer_4_8_2.isChecked){
            ck4_8 = "2"
        }else if(cancer_4_8_3.isChecked){
            ck4_8 = "3"
        }else if(cancer_4_8_4.isChecked){
            ck4_8 = "4"
        }else{
            Toast.makeText(this, "4-9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cancer_5_1_checkBox.isChecked){
            ck5_1 = "2"
        }else{
            ck5_1 = "1"
        }

        if(cancer_5_2_checkBox.isChecked){
            ck5_2 = "2"
        }else{
            ck5_2 = "1"
        }

        if(cancer_5_3_checkBox.isChecked){
            ck5_3 = "2"
        }else{
            ck5_3 = "1"
        }

        if(cancer_5_4_checkBox.isChecked){
            ck5_4 = "2"
        }else{
            ck5_4 = "1"
        }

        if(cancer_5_5_checkBox.isChecked){
            ck5_5 = "2"
        }else{
            ck5_5 = "1"
        }

        if(cancer_6_1_checkBox.isChecked){
            ck6_1 = "2"
        }else{
            ck6_1 = "1"
        }

        if(cancer_6_2_checkBox.isChecked){
            ck6_2 = "2"
        }else{
            ck6_2 = "1"
        }

        if(cancer_6_3_checkBox.isChecked){
            ck6_3 = "2"
        }else{
            ck6_3 = "1"
        }

        if(cancer_6_4_checkBox.isChecked){
            ck6_4 = "2"
        }else{
            ck6_4 = "1"
        }

        if(cancer_6_5_checkBox.isChecked){
            ck6_5 = "2"
        }else{
            ck6_5 = "1"
        }

        if(cancer_7_1_checkBox.isChecked){
            ck7_1 = "2"
        }else{
            ck7_1 = "1"
        }

        if(cancer_7_2_checkBox.isChecked){
            ck7_2 = "2"
        }else{
            ck7_2 = "1"
        }

        if(cancer_7_3_checkBox.isChecked){
            ck7_3 = "2"
        }else{
            ck7_3 = "1"
        }

        if(cancer_7_4_checkBox.isChecked){
            ck7_4 = "2"
        }else{
            ck7_4 = "1"
        }

        if(cancer_7_5_checkBox.isChecked){
            ck7_5 = "2"
        }else{
            ck7_5 = "1"
        }

        if(cancer_16_1_checkBox.isChecked){
            ck16_1 = "2"
        }else{
            ck16_1 = "1"
        }

        if(cancer_16_2_checkBox.isChecked){
            ck16_2 = "2"
        }else{
            ck16_2 = "1"
        }

        if(cancer_16_3_checkBox.isChecked){
            ck16_3 = "2"
        }else{
            ck16_3 = "1"
        }

        if(cancer_16_4_checkBox.isChecked){
            ck16_4 = "2"
        }else{
            ck16_4 = "1"
        }

        if(cancer_16_5_checkBox.isChecked){
            ck16_5 = "2"
        }else{
            ck16_5 = "1"
        }

        if(cancer_16_6_checkBox.isChecked){
            ck16_6 = "2"
        }else{
            ck16_6 = "1"
        }

        if(AdditionalArr.Gender.isFemale){
            if(cancer_8_1.isChecked){
                ck8_1 = "1"
                if(!cancer_editText3.text.isNullOrEmpty()){
                    ck8_2 = cancer_editText3.text.toString()
                }else{
                    Toast.makeText(this, "9번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                    return false
                }
            }else if(cancer_8_2.isChecked){
                ck8_1 = "2"
            }else{
                Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_9_1.isChecked){
                ck9_1 = "1"
            }else if(cancer_9_2.isChecked){
                ck9_1 = "2"
            }else if(cancer_9_3.isChecked){
                ck9_1 = "3"
                if(!cancer_editText4.text.isNullOrEmpty()){
                    ck9_2 = cancer_editText4.text.toString()
                }else{
                    Toast.makeText(this, "10번 문항을 작성해주세요", Toast.LENGTH_LONG).show()
                    return false
                }
            }else{
                Toast.makeText(this, "10번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_10_1.isChecked){
                ck10 = "1"
            }else if(cancer_10_2.isChecked){
                ck10 = "2"
            }else if(cancer_10_3.isChecked){
                ck10 = "3"
            }else if(cancer_10_4.isChecked){
                ck10 = "4"
            }else if(cancer_10_5.isChecked){
                ck10 = "5"
            }else{
                Toast.makeText(this, "11번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_11_1.isChecked){
                ck11 = "1"
            }else if(cancer_11_2.isChecked){
                ck11 = "2"
            }else if(cancer_11_3.isChecked){
                ck11 = "3"
            }else{
                Toast.makeText(this, "12번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_12_1.isChecked){
                ck12 = "1"
            }else if(cancer_12_2.isChecked){
                ck12 = "2"
            }else if(cancer_12_3.isChecked){
                ck12 = "3"
            }else if(cancer_12_4.isChecked){
                ck12 = "4"
            }else{
                Toast.makeText(this, "13번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_13_1.isChecked){
                ck13 = "1"
            }else if(cancer_13_2.isChecked){
                ck13 = "2"
            }else if(cancer_13_3.isChecked){
                ck13 = "3"
            }else{
                Toast.makeText(this, "14번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if(cancer_14_1.isChecked){
                ck14 = "1"
            }else if(cancer_14_2.isChecked){
                ck14 = "2"
            }else if(cancer_14_3.isChecked){
                ck14 = "3"
            }else if(cancer_14_4.isChecked){
                ck14 = "4"
            }else{
                Toast.makeText(this, "15번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }

        SavePaper.Total.Array[10] = Paper_CANCER(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                ck1, ck1_1, ck2, ck2_1,
                ck3_1, ck3_1_1, ck3_1_2, ck3_1_3, ck3_1_4, ck3_1_5,
                ck3_2, ck3_2_1, ck3_2_2, ck3_2_3, ck3_2_4, ck3_2_5,
                ck3_3, ck3_3_1, ck3_3_2, ck3_3_3, ck3_3_4, ck3_3_5,
                ck3_4, ck3_4_1, ck3_4_2, ck3_4_3, ck3_4_4, ck3_4_5,
                ck3_5, ck3_5_1, ck3_5_2, ck3_5_3, ck3_5_4, ck3_5_5,
                ck3_6, ck3_6_1, ck3_6_2, ck3_6_3, ck3_6_4, ck3_6_5, ck3_6_kita,
                ck4_1, ck4_2, ck4_3, ck4_4, ck4_5, ck4_6,
                ck4_7, ck4_8, ck4_9, ck5_1, ck5_2, ck5_3, ck5_4, ck5_5, ck6_1, ck6_2, ck6_3, ck6_4,
                ck6_5, ck7_1, ck7_2, ck7_3, ck7_4, ck7_5, ck8_1, ck8_2, ck9_1, ck9_2,
                ck10, ck11, ck12, ck13, ck14, ck15_5, ck15_5_1, ck15_5_2, ck15_5_3, ck15_5_4, ck15_5_5, ck16_1, ck16_2, ck16_3, ck16_4, ck16_5, ck16_6)

        if(ChartDivision.ChartDivision.next_or_save(6)){
            SavedListObject.SavedList.savedDataClass.cancerSaved = true
        }

        ChartDivision.ChartDivision.ProgressAction(true, this)

        return true

    }

    fun whenTempLoad(paper: Paper_CANCER) {

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        if(paper.last_serial == "2" || paper.last_serial == "4"){
            AdditionalArr.Gender.isFemale = true
        }

        if(paper.ck1 == "1"){
            cancer_1_1.isChecked = true
            if(paper.ck1_1.isNullOrEmpty()){

            }else{
                cancer_editText1.setText(paper.ck1_1)
            }
        }else if(paper.ck1 == "2"){
            cancer_1_2.isChecked = true
        }


        if(paper.ck2 == "1"){
            cancer_2_1.isChecked = true

        }else if(paper.ck2 == "2"){
            cancer_2_2.isChecked = true

            if(paper.ck2_1.isNullOrEmpty()){

            }else{
                cancer_editText2.setText(paper.ck2_1)
            }
        }


        if(paper.ck3_1 == "1"){
            cancer_3_1_1.isChecked = true

        }else if(paper.ck3_1 == "3"){
            cancer_3_1_2.isChecked = true

        }else if(paper.ck3_1 == "2"){
            cancer_3_1_3.isChecked = true

            cancer_3_1_checkBox1.visibility = View.VISIBLE
            cancer_3_1_checkBox2.visibility = View.VISIBLE
            cancer_3_1_checkBox3.visibility = View.VISIBLE
            cancer_3_1_checkBox4.visibility = View.VISIBLE
            cancer_3_1_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_1_1 == "2"){
                cancer_3_1_checkBox1.isChecked = true
            }
            if(paper.ck3_1_2 == "2"){
                cancer_3_1_checkBox2.isChecked = true
            }
            if(paper.ck3_1_3 == "2"){
                cancer_3_1_checkBox3.isChecked = true
            }
            if(paper.ck3_1_4 == "2"){
                cancer_3_1_checkBox4.isChecked = true
            }
            if(paper.ck3_1_5 == "2"){
                cancer_3_1_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_2 == "1"){
            cancer_3_2_1.isChecked = true

        }else if(paper.ck3_2 == "3"){
            cancer_3_2_2.isChecked = true

        }else if(paper.ck3_2 == "2"){
            cancer_3_2_3.isChecked = true

            cancer_3_2_checkBox1.visibility = View.VISIBLE
            cancer_3_2_checkBox2.visibility = View.VISIBLE
            cancer_3_2_checkBox3.visibility = View.VISIBLE
            cancer_3_2_checkBox4.visibility = View.VISIBLE
            cancer_3_2_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_2_1 == "2"){
                cancer_3_2_checkBox1.isChecked = true
            }
            if(paper.ck3_2_2 == "2"){
                cancer_3_2_checkBox2.isChecked = true
            }
            if(paper.ck3_2_3 == "2"){
                cancer_3_2_checkBox3.isChecked = true
            }
            if(paper.ck3_2_4 == "2"){
                cancer_3_2_checkBox4.isChecked = true
            }
            if(paper.ck3_2_5 == "2"){
                cancer_3_2_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_3 == "1"){
            cancer_3_3_1.isChecked = true

        }else if(paper.ck3_3 == "3"){
            cancer_3_3_2.isChecked = true

        }else if(paper.ck3_3 == "2"){
            cancer_3_3_3.isChecked = true

            cancer_3_3_checkBox1.visibility = View.VISIBLE
            cancer_3_3_checkBox2.visibility = View.VISIBLE
            cancer_3_3_checkBox3.visibility = View.VISIBLE
            cancer_3_3_checkBox4.visibility = View.VISIBLE
            cancer_3_3_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_3_1 == "2"){
                cancer_3_3_checkBox1.isChecked = true
            }
            if(paper.ck3_3_2 == "2"){
                cancer_3_3_checkBox2.isChecked = true
            }
            if(paper.ck3_3_3 == "2"){
                cancer_3_3_checkBox3.isChecked = true
            }
            if(paper.ck3_3_4 == "2"){
                cancer_3_3_checkBox4.isChecked = true
            }
            if(paper.ck3_3_5 == "2"){
                cancer_3_3_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_4 == "1"){
            cancer_3_4_1.isChecked = true

        }else if(paper.ck3_4 == "3"){
            cancer_3_4_2.isChecked = true

        }else if(paper.ck3_4 == "2"){
            cancer_3_4_3.isChecked = true

            cancer_3_4_checkBox1.visibility = View.VISIBLE
            cancer_3_4_checkBox2.visibility = View.VISIBLE
            cancer_3_4_checkBox3.visibility = View.VISIBLE
            cancer_3_4_checkBox4.visibility = View.VISIBLE
            cancer_3_4_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_4_1 == "2"){
                cancer_3_4_checkBox1.isChecked = true
            }
            if(paper.ck3_4_2 == "2"){
                cancer_3_4_checkBox2.isChecked = true
            }
            if(paper.ck3_4_3 == "2"){
                cancer_3_4_checkBox3.isChecked = true
            }
            if(paper.ck3_4_4 == "2"){
                cancer_3_4_checkBox4.isChecked = true
            }
            if(paper.ck3_4_5 == "2"){
                cancer_3_4_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_5 == "1"){
            cancer_3_5_1.isChecked = true

        }else if(paper.ck3_5 == "3"){
            cancer_3_5_2.isChecked = true

        }else if(paper.ck3_5 == "2"){
            cancer_3_5_3.isChecked = true

            cancer_3_5_checkBox1.visibility = View.VISIBLE
            cancer_3_5_checkBox2.visibility = View.VISIBLE
            cancer_3_5_checkBox3.visibility = View.VISIBLE
            cancer_3_5_checkBox4.visibility = View.VISIBLE
            cancer_3_5_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_5_1 == "2"){
                cancer_3_5_checkBox1.isChecked = true
            }
            if(paper.ck3_5_2 == "2"){
                cancer_3_5_checkBox2.isChecked = true
            }
            if(paper.ck3_5_3 == "2"){
                cancer_3_5_checkBox3.isChecked = true
            }
            if(paper.ck3_5_4 == "2"){
                cancer_3_5_checkBox4.isChecked = true
            }
            if(paper.ck3_5_5 == "2"){
                cancer_3_5_checkBox5.isChecked = true
            }

        }

        if(paper.ck15_5 == "1"){
            cancer_15_5_1.isChecked = true

        }else if(paper.ck15_5 == "3"){
            cancer_15_5_2.isChecked = true

        }else if(paper.ck15_5 == "2"){
            cancer_15_5_3.isChecked = true

            cancer_15_5_checkBox1.visibility = View.VISIBLE
            cancer_15_5_checkBox2.visibility = View.VISIBLE
            cancer_15_5_checkBox3.visibility = View.VISIBLE
            cancer_15_5_checkBox4.visibility = View.VISIBLE
            cancer_15_5_checkBox5.visibility = View.VISIBLE

            if(paper.ck15_5_1 == "2"){
                cancer_15_5_checkBox1.isChecked = true
            }
            if(paper.ck15_5_2 == "2"){
                cancer_15_5_checkBox2.isChecked = true
            }
            if(paper.ck15_5_3 == "2"){
                cancer_15_5_checkBox3.isChecked = true
            }
            if(paper.ck15_5_4 == "2"){
                cancer_15_5_checkBox4.isChecked = true
            }
            if(paper.ck15_5_5 == "2"){
                cancer_15_5_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_6 == "1"){
            cancer_3_6_1.isChecked = true

        }else if(paper.ck3_6 == "3"){
            cancer_3_6_2.isChecked = true

        }else if(paper.ck3_6 == "2"){
            cancer_3_6_3.isChecked = true

            cancer_3_6_checkBox1.visibility = View.VISIBLE
            cancer_3_6_checkBox2.visibility = View.VISIBLE
            cancer_3_6_checkBox3.visibility = View.VISIBLE
            cancer_3_6_checkBox4.visibility = View.VISIBLE
            cancer_3_6_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_6_1 == "2"){
                cancer_3_6_checkBox1.isChecked = true
            }
            if(paper.ck3_6_2 == "2"){
                cancer_3_6_checkBox2.isChecked = true
            }
            if(paper.ck3_6_3 == "2"){
                cancer_3_6_checkBox3.isChecked = true
            }
            if(paper.ck3_6_4 == "2"){
                cancer_3_6_checkBox4.isChecked = true
            }
            if(paper.ck3_6_5 == "2"){
                cancer_3_6_checkBox5.isChecked = true
            }

            if(paper.ck3_6_kita != ""){
                cancer_3_6_1_editText.setText(paper.ck3_6_kita)
            }

        }

        if(paper.ck4_1 == "1"){
            cancer_4_1_1.isChecked = true
        }else if(paper.ck4_1 == "2"){
            cancer_4_1_2.isChecked = true
        }else if(paper.ck4_1 == "3"){
            cancer_4_1_3.isChecked = true
        }else if(paper.ck4_1 == "4"){
            cancer_4_1_4.isChecked = true
        }


        if(paper.ck4_2 == "1"){
            cancer_4_2_1.isChecked = true
        }else if(paper.ck4_2 == "2"){
            cancer_4_2_2.isChecked = true
        }else if(paper.ck4_2 == "3"){
            cancer_4_2_3.isChecked = true
        }else if(paper.ck4_2 == "4"){
            cancer_4_2_4.isChecked = true
        }


        if(paper.ck4_3 == "1"){
            cancer_4_3_1.isChecked = true
        }else if(paper.ck4_3 == "2"){
            cancer_4_3_2.isChecked = true
        }else if(paper.ck4_3 == "3"){
            cancer_4_3_3.isChecked = true
        }else if(paper.ck4_3 == "4"){
            cancer_4_3_4.isChecked = true
        }

        if(paper.ck4_4 == "1"){
            cancer_4_4_1.isChecked = true
        }else if(paper.ck4_4 == "2"){
            cancer_4_4_2.isChecked = true
        }else if(paper.ck4_4 == "3"){
            cancer_4_4_3.isChecked = true
        }else if(paper.ck4_4 == "4"){
            cancer_4_4_4.isChecked = true
        }

        if(paper.ck4_5 == "1"){
            cancer_4_5_1.isChecked = true
        }else if(paper.ck4_5 == "2"){
            cancer_4_5_2.isChecked = true
        }else if(paper.ck4_5 == "3"){
            cancer_4_5_3.isChecked = true
        }else if(paper.ck4_5 == "4"){
            cancer_4_5_4.isChecked = true
        }

        if(paper.ck4_6 == "1"){
            cancer_4_6_1.isChecked = true
        }else if(paper.ck4_6 == "2"){
            cancer_4_6_2.isChecked = true
        }else if(paper.ck4_6 == "3"){
            cancer_4_6_3.isChecked = true
        }else if(paper.ck4_6 == "4"){
            cancer_4_6_4.isChecked = true
        }

        if(paper.ck4_7 == "1"){
            cancer_4_7_1.isChecked = true
        }else if(paper.ck4_7 == "2"){
            cancer_4_7_2.isChecked = true
        }else if(paper.ck4_7 == "3"){
            cancer_4_7_3.isChecked = true
        }else if(paper.ck4_7 == "4"){
            cancer_4_7_4.isChecked = true
        }

        if(paper.ck4_9 == "1"){
            cancer_4_9_1.isChecked = true
        }else if(paper.ck4_8 == "2"){
            cancer_4_9_2.isChecked = true
        }else if(paper.ck4_8 == "3"){
            cancer_4_9_3.isChecked = true
        }else if(paper.ck4_8 == "4"){
            cancer_4_9_4.isChecked = true
        }

        if(paper.ck4_8 == "1"){
            cancer_4_8_1.isChecked = true
        }else if(paper.ck4_8 == "2"){
            cancer_4_8_2.isChecked = true
        }else if(paper.ck4_8 == "3"){
            cancer_4_8_3.isChecked = true
        }else if(paper.ck4_8 == "4"){
            cancer_4_8_4.isChecked = true
        }

        if(paper.ck5_1 != "2" && paper.ck5_2 != "2" && paper.ck5_3 != "2" && paper.ck5_4 != "2" && paper.ck5_5 != "2"){
            cancer_5_0_checkBox.isChecked = true
        }
        if(paper.ck5_1 == "2"){
            cancer_5_1_checkBox.isChecked = true
        }
        if(paper.ck5_2 == "2"){
            cancer_5_2_checkBox.isChecked = true
        }
        if(paper.ck5_3 == "2"){
            cancer_5_3_checkBox.isChecked = true
        }
        if(paper.ck5_4 == "2"){
            cancer_5_4_checkBox.isChecked = true
        }
        if(paper.ck5_5 == "2"){
            cancer_5_5_checkBox.isChecked = true
        }

        if(paper.ck6_1 != "2" && paper.ck6_2 != "2" && paper.ck6_3 != "2" && paper.ck6_4 != "2" && paper.ck6_5 != "2"){
            cancer_6_0_checkBox.isChecked = true
        }
        if(paper.ck6_1 == "2"){
            cancer_6_1_checkBox.isChecked = true
        }
        if(paper.ck6_2 == "2"){
            cancer_6_2_checkBox.isChecked = true
        }
        if(paper.ck6_3 == "2"){
            cancer_6_3_checkBox.isChecked = true
        }
        if(paper.ck6_4 == "2"){
            cancer_6_4_checkBox.isChecked = true
        }
        if(paper.ck6_5 == "2"){
            cancer_6_5_checkBox.isChecked = true
        }


        if(paper.ck7_1 != "2" && paper.ck7_2 != "2" && paper.ck7_3 != "2" && paper.ck7_4 != "2" && paper.ck7_5 != "2"){
            cancer_7_0_checkBox.isChecked = true
        }
        if(paper.ck7_1 == "2"){
            cancer_7_1_checkBox.isChecked = true
        }
        if(paper.ck7_2 == "2"){
            cancer_7_2_checkBox.isChecked = true
        }
        if(paper.ck7_3 == "2"){
            cancer_7_3_checkBox.isChecked = true
        }
        if(paper.ck7_4 == "2"){
            cancer_7_4_checkBox.isChecked = true
        }
        if(paper.ck7_5 == "2"){
            cancer_7_5_checkBox.isChecked = true
        }

        if(paper.ck16_1 != "2" && paper.ck16_2 != "2" && paper.ck16_3 != "2" && paper.ck16_4 != "2" && paper.ck16_5 != "2" && paper.ck16_6 != "2"){
            cancer_16_0_checkBox.isChecked = true
        }
        if(paper.ck16_1 == "2"){
            cancer_16_1_checkBox.isChecked = true
        }
        if(paper.ck16_2 == "2"){
            cancer_16_2_checkBox.isChecked = true
        }
        if(paper.ck16_3 == "2"){
            cancer_16_3_checkBox.isChecked = true
        }
        if(paper.ck16_4 == "2"){
            cancer_16_4_checkBox.isChecked = true
        }
        if(paper.ck16_5 == "2"){
            cancer_16_5_checkBox.isChecked = true
        }
        if(paper.ck16_6 == "2"){
            cancer_16_6_checkBox.isChecked = true
        }

        if(paper.ck8_1 == "1"){
            cancer_8_1.isChecked = true
            if(paper.ck8_2.isNullOrEmpty()){

            }else{
                cancer_editText3.setText(paper.ck8_2)
            }
        }else if(paper.ck8_1 == "2"){
            cancer_8_2.isChecked = true
        }

        if(paper.ck9_1 == "1"){
            cancer_9_1.isChecked = true
        }else if(paper.ck9_1 == "2"){
            cancer_9_2.isChecked = true
        }else if(paper.ck9_1 == "3"){
            cancer_9_3.isChecked = true
            if(paper.ck9_2.isNullOrEmpty()){

            }else{
                cancer_editText4.setText(paper.ck9_2)
            }
        }

        if(paper.ck10 == "1"){
            cancer_10_1.isChecked = true
        }else if(paper.ck10 == "2"){
            cancer_10_2.isChecked = true
        }else if(paper.ck10 == "3"){
            cancer_10_3.isChecked = true
        }else if(paper.ck10 == "4"){
            cancer_10_4.isChecked = true
        }else if(paper.ck10 == "5"){
            cancer_10_5.isChecked = true
        }


        if(paper.ck11 == "1"){
            cancer_11_1.isChecked = true
        }else if(paper.ck11 == "2"){
            cancer_11_2.isChecked = true
        }else if(paper.ck11 == "3"){
            cancer_11_3.isChecked = true
        }


        if(paper.ck12 == "1"){
            cancer_12_1.isChecked = true
        }else if(paper.ck12 == "2"){
            cancer_12_2.isChecked = true
        }else if(paper.ck12 == "3"){
            cancer_12_3.isChecked = true
        }else if(paper.ck12 == "4"){
            cancer_12_4.isChecked = true
        }

        if(paper.ck13 == "1"){
            cancer_13_1.isChecked = true
        }else if(paper.ck13 == "2"){
            cancer_13_2.isChecked = true
        }else if(paper.ck13 == "3"){
            cancer_13_3.isChecked = true
        }

        if(paper.ck14 == "1"){
            cancer_14_1.isChecked = true
        }else if(paper.ck14 == "2"){
            cancer_14_2.isChecked = true
        }else if(paper.ck14 == "3"){
            cancer_14_3.isChecked = true
        }

    }

    fun GetPaper(paper: Paper_CANCER) {

        state = "getPaper"

        cannotEditQuestionnaire(cancer_root)

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        cancer_examination_save.visibility = View.GONE
        cancer_examination_cancel.visibility = View.GONE
        cancer_edit_submit.visibility = View.VISIBLE

        if(paper.last_serial == "2" || paper.last_serial == "4"){
            AdditionalArr.Gender.isFemale = true
        }

        if(paper.ck1 == "1"){
            cancer_1_1.isChecked = true
            if(paper.ck1_1.isNullOrEmpty()){

            }else{
                cancer_editText1.setText(paper.ck1_1)
            }
        }else if(paper.ck1 == "2"){
            cancer_1_2.isChecked = true
        }


        if(paper.ck2 == "1"){
            cancer_2_1.isChecked = true

        }else if(paper.ck2 == "2"){
            cancer_2_2.isChecked = true

            if(paper.ck2_1.isNullOrEmpty()){

            }else{
                cancer_editText2.setText(paper.ck2_1)
            }
        }


        if(paper.ck3_1 == "1"){
            cancer_3_1_1.isChecked = true

        }else if(paper.ck3_1 == "3"){
            cancer_3_1_2.isChecked = true

        }else if(paper.ck3_1 == "2"){
            cancer_3_1_3.isChecked = true

            cancer_3_1_checkBox1.visibility = View.VISIBLE
            cancer_3_1_checkBox2.visibility = View.VISIBLE
            cancer_3_1_checkBox3.visibility = View.VISIBLE
            cancer_3_1_checkBox4.visibility = View.VISIBLE
            cancer_3_1_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_1_1 == "2"){
                cancer_3_1_checkBox1.isChecked = true
            }
            if(paper.ck3_1_2 == "2"){
                cancer_3_1_checkBox2.isChecked = true
            }
            if(paper.ck3_1_3 == "2"){
                cancer_3_1_checkBox3.isChecked = true
            }
            if(paper.ck3_1_4 == "2"){
                cancer_3_1_checkBox4.isChecked = true
            }
            if(paper.ck3_1_5 == "2"){
                cancer_3_1_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_2 == "1"){
            cancer_3_2_1.isChecked = true

        }else if(paper.ck3_2 == "3"){
            cancer_3_2_2.isChecked = true

        }else if(paper.ck3_2 == "2"){
            cancer_3_2_3.isChecked = true

            cancer_3_2_checkBox1.visibility = View.VISIBLE
            cancer_3_2_checkBox2.visibility = View.VISIBLE
            cancer_3_2_checkBox3.visibility = View.VISIBLE
            cancer_3_2_checkBox4.visibility = View.VISIBLE
            cancer_3_2_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_2_1 == "2"){
                cancer_3_2_checkBox1.isChecked = true
            }
            if(paper.ck3_2_2 == "2"){
                cancer_3_2_checkBox2.isChecked = true
            }
            if(paper.ck3_2_3 == "2"){
                cancer_3_2_checkBox3.isChecked = true
            }
            if(paper.ck3_2_4 == "2"){
                cancer_3_2_checkBox4.isChecked = true
            }
            if(paper.ck3_2_5 == "2"){
                cancer_3_2_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_3 == "1"){
            cancer_3_3_1.isChecked = true

        }else if(paper.ck3_3 == "3"){
            cancer_3_3_2.isChecked = true

        }else if(paper.ck3_3 == "2"){
            cancer_3_3_3.isChecked = true

            cancer_3_3_checkBox1.visibility = View.VISIBLE
            cancer_3_3_checkBox2.visibility = View.VISIBLE
            cancer_3_3_checkBox3.visibility = View.VISIBLE
            cancer_3_3_checkBox4.visibility = View.VISIBLE
            cancer_3_3_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_3_1 == "2"){
                cancer_3_3_checkBox1.isChecked = true
            }
            if(paper.ck3_3_2 == "2"){
                cancer_3_3_checkBox2.isChecked = true
            }
            if(paper.ck3_3_3 == "2"){
                cancer_3_3_checkBox3.isChecked = true
            }
            if(paper.ck3_3_4 == "2"){
                cancer_3_3_checkBox4.isChecked = true
            }
            if(paper.ck3_3_5 == "2"){
                cancer_3_3_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_4 == "1"){
            cancer_3_4_1.isChecked = true

        }else if(paper.ck3_4 == "3"){
            cancer_3_4_2.isChecked = true

        }else if(paper.ck3_4 == "2"){
            cancer_3_4_3.isChecked = true

            cancer_3_4_checkBox1.visibility = View.VISIBLE
            cancer_3_4_checkBox2.visibility = View.VISIBLE
            cancer_3_4_checkBox3.visibility = View.VISIBLE
            cancer_3_4_checkBox4.visibility = View.VISIBLE
            cancer_3_4_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_4_1 == "2"){
                cancer_3_4_checkBox1.isChecked = true
            }
            if(paper.ck3_4_2 == "2"){
                cancer_3_4_checkBox2.isChecked = true
            }
            if(paper.ck3_4_3 == "2"){
                cancer_3_4_checkBox3.isChecked = true
            }
            if(paper.ck3_4_4 == "2"){
                cancer_3_4_checkBox4.isChecked = true
            }
            if(paper.ck3_4_5 == "2"){
                cancer_3_4_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_5 == "1"){
            cancer_3_5_1.isChecked = true

        }else if(paper.ck3_5 == "3"){
            cancer_3_5_2.isChecked = true

        }else if(paper.ck3_5 == "2"){
            cancer_3_5_3.isChecked = true

            cancer_3_5_checkBox1.visibility = View.VISIBLE
            cancer_3_5_checkBox2.visibility = View.VISIBLE
            cancer_3_5_checkBox3.visibility = View.VISIBLE
            cancer_3_5_checkBox4.visibility = View.VISIBLE
            cancer_3_5_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_5_1 == "2"){
                cancer_3_5_checkBox1.isChecked = true
            }
            if(paper.ck3_5_2 == "2"){
                cancer_3_5_checkBox2.isChecked = true
            }
            if(paper.ck3_5_3 == "2"){
                cancer_3_5_checkBox3.isChecked = true
            }
            if(paper.ck3_5_4 == "2"){
                cancer_3_5_checkBox4.isChecked = true
            }
            if(paper.ck3_5_5 == "2"){
                cancer_3_5_checkBox5.isChecked = true
            }

        }

        if(paper.ck15_5 == "1"){
            cancer_15_5_1.isChecked = true

        }else if(paper.ck15_5 == "3"){
            cancer_15_5_2.isChecked = true

        }else if(paper.ck15_5 == "2"){
            cancer_15_5_3.isChecked = true

            cancer_15_5_checkBox1.visibility = View.VISIBLE
            cancer_15_5_checkBox2.visibility = View.VISIBLE
            cancer_15_5_checkBox3.visibility = View.VISIBLE
            cancer_15_5_checkBox4.visibility = View.VISIBLE
            cancer_15_5_checkBox5.visibility = View.VISIBLE

            if(paper.ck15_5_1 == "2"){
                cancer_15_5_checkBox1.isChecked = true
            }
            if(paper.ck15_5_2 == "2"){
                cancer_15_5_checkBox2.isChecked = true
            }
            if(paper.ck15_5_3 == "2"){
                cancer_15_5_checkBox3.isChecked = true
            }
            if(paper.ck15_5_4 == "2"){
                cancer_15_5_checkBox4.isChecked = true
            }
            if(paper.ck15_5_5 == "2"){
                cancer_15_5_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_6 == "1"){
            cancer_3_6_1.isChecked = true

        }else if(paper.ck3_6 == "3"){
            cancer_3_6_2.isChecked = true

        }else if(paper.ck3_6 == "2"){
            cancer_3_6_3.isChecked = true

            cancer_3_6_checkBox1.visibility = View.VISIBLE
            cancer_3_6_checkBox2.visibility = View.VISIBLE
            cancer_3_6_checkBox3.visibility = View.VISIBLE
            cancer_3_6_checkBox4.visibility = View.VISIBLE
            cancer_3_6_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_6_1 == "2"){
                cancer_3_6_checkBox1.isChecked = true
            }
            if(paper.ck3_6_2 == "2"){
                cancer_3_6_checkBox2.isChecked = true
            }
            if(paper.ck3_6_3 == "2"){
                cancer_3_6_checkBox3.isChecked = true
            }
            if(paper.ck3_6_4 == "2"){
                cancer_3_6_checkBox4.isChecked = true
            }
            if(paper.ck3_6_5 == "2"){
                cancer_3_6_checkBox5.isChecked = true
            }

            if(paper.ck3_6_kita != ""){
                cancer_3_6_1_editText.setText(paper.ck3_6_kita)
            }

        }


        if(paper.ck4_1 == "1"){
            cancer_4_1_1.isChecked = true
        }else if(paper.ck4_1 == "2"){
            cancer_4_1_2.isChecked = true
        }else if(paper.ck4_1 == "3"){
            cancer_4_1_3.isChecked = true
        }else if(paper.ck4_1 == "4"){
            cancer_4_1_4.isChecked = true
        }


        if(paper.ck4_2 == "1"){
            cancer_4_2_1.isChecked = true
        }else if(paper.ck4_2 == "2"){
            cancer_4_2_2.isChecked = true
        }else if(paper.ck4_2 == "3"){
            cancer_4_2_3.isChecked = true
        }else if(paper.ck4_2 == "4"){
            cancer_4_2_4.isChecked = true
        }


        if(paper.ck4_3 == "1"){
            cancer_4_3_1.isChecked = true
        }else if(paper.ck4_3 == "2"){
            cancer_4_3_2.isChecked = true
        }else if(paper.ck4_3 == "3"){
            cancer_4_3_3.isChecked = true
        }else if(paper.ck4_3 == "4"){
            cancer_4_3_4.isChecked = true
        }


        if(paper.ck4_4 == "1"){
            cancer_4_4_1.isChecked = true
        }else if(paper.ck4_4 == "2"){
            cancer_4_4_2.isChecked = true
        }else if(paper.ck4_4 == "3"){
            cancer_4_4_3.isChecked = true
        }else if(paper.ck4_4 == "4"){
            cancer_4_4_4.isChecked = true
        }


        if(paper.ck4_5 == "1"){
            cancer_4_5_1.isChecked = true
        }else if(paper.ck4_5 == "2"){
            cancer_4_5_2.isChecked = true
        }else if(paper.ck4_5 == "3"){
            cancer_4_5_3.isChecked = true
        }else if(paper.ck4_5 == "4"){
            cancer_4_5_4.isChecked = true
        }


        if(paper.ck4_6 == "1"){
            cancer_4_6_1.isChecked = true
        }else if(paper.ck4_6 == "2"){
            cancer_4_6_2.isChecked = true
        }else if(paper.ck4_6 == "3"){
            cancer_4_6_3.isChecked = true
        }else if(paper.ck4_6 == "4"){
            cancer_4_6_4.isChecked = true
        }


        if(paper.ck4_7 == "1"){
            cancer_4_7_1.isChecked = true
        }else if(paper.ck4_7 == "2"){
            cancer_4_7_2.isChecked = true
        }else if(paper.ck4_7 == "3"){
            cancer_4_7_3.isChecked = true
        }else if(paper.ck4_7 == "4"){
            cancer_4_7_4.isChecked = true
        }

        if(paper.ck4_9 == "1"){
            cancer_4_9_1.isChecked = true
        }else if(paper.ck4_8 == "2"){
            cancer_4_9_2.isChecked = true
        }else if(paper.ck4_8 == "3"){
            cancer_4_9_3.isChecked = true
        }else if(paper.ck4_8 == "4"){
            cancer_4_9_4.isChecked = true
        }


        if(paper.ck4_8 == "1"){
            cancer_4_8_1.isChecked = true
        }else if(paper.ck4_8 == "2"){
            cancer_4_8_2.isChecked = true
        }else if(paper.ck4_8 == "3"){
            cancer_4_8_3.isChecked = true
        }else if(paper.ck4_8 == "4"){
            cancer_4_8_4.isChecked = true
        }

        if(paper.ck5_1 != "2" && paper.ck5_2 != "2" && paper.ck5_3 != "2" && paper.ck5_4 != "2" && paper.ck5_5 != "2"){
            cancer_5_0_checkBox.isChecked = true
        }
        if(paper.ck5_1 == "2"){
            cancer_5_1_checkBox.isChecked = true
        }
        if(paper.ck5_2 == "2"){
            cancer_5_2_checkBox.isChecked = true
        }
        if(paper.ck5_3 == "2"){
            cancer_5_3_checkBox.isChecked = true
        }
        if(paper.ck5_4 == "2"){
            cancer_5_4_checkBox.isChecked = true
        }
        if(paper.ck5_5 == "2"){
            cancer_5_5_checkBox.isChecked = true
        }

        if(paper.ck6_1 != "2" && paper.ck6_2 != "2" && paper.ck6_3 != "2" && paper.ck6_4 != "2" && paper.ck6_5 != "2"){
            cancer_6_0_checkBox.isChecked = true
        }
        if(paper.ck6_1 == "2"){
            cancer_6_1_checkBox.isChecked = true
        }
        if(paper.ck6_2 == "2"){
            cancer_6_2_checkBox.isChecked = true
        }
        if(paper.ck6_3 == "2"){
            cancer_6_3_checkBox.isChecked = true
        }
        if(paper.ck6_4 == "2"){
            cancer_6_4_checkBox.isChecked = true
        }
        if(paper.ck6_5 == "2"){
            cancer_6_5_checkBox.isChecked = true
        }


        if(paper.ck7_1 != "2" && paper.ck7_2 != "2" && paper.ck7_3 != "2" && paper.ck7_4 != "2" && paper.ck7_5 != "2"){
            cancer_7_0_checkBox.isChecked = true
        }
        if(paper.ck7_1 == "2"){
            cancer_7_1_checkBox.isChecked = true
        }
        if(paper.ck7_2 == "2"){
            cancer_7_2_checkBox.isChecked = true
        }
        if(paper.ck7_3 == "2"){
            cancer_7_3_checkBox.isChecked = true
        }
        if(paper.ck7_4 == "2"){
            cancer_7_4_checkBox.isChecked = true
        }
        if(paper.ck7_5 == "2"){
            cancer_7_5_checkBox.isChecked = true
        }

        if(paper.ck16_1 != "2" && paper.ck16_2 != "2" && paper.ck16_3 != "2" && paper.ck16_4 != "2" && paper.ck16_5 != "2" && paper.ck16_6 != "2"){
            cancer_16_0_checkBox.isChecked = true
        }
        if(paper.ck16_1 == "2"){
            cancer_16_1_checkBox.isChecked = true
        }
        if(paper.ck16_2 == "2"){
            cancer_16_2_checkBox.isChecked = true
        }
        if(paper.ck16_3 == "2"){
            cancer_16_3_checkBox.isChecked = true
        }
        if(paper.ck16_4 == "2"){
            cancer_16_4_checkBox.isChecked = true
        }
        if(paper.ck16_5 == "2"){
            cancer_16_5_checkBox.isChecked = true
        }
        if(paper.ck16_6 == "2"){
            cancer_16_6_checkBox.isChecked = true
        }

        if(paper.ck8_1 == "1"){
            cancer_8_1.isChecked = true
            if(paper.ck8_2.isNullOrEmpty()){

            }else{
                cancer_editText3.setText(paper.ck8_2)
            }
        }else if(paper.ck8_1 == "2"){
            cancer_8_2.isChecked = true
        }

        if(paper.ck9_1 == "1"){
            cancer_9_1.isChecked = true
        }else if(paper.ck9_1 == "2"){
            cancer_9_2.isChecked = true
        }else if(paper.ck9_1 == "3"){
            cancer_9_3.isChecked = true
            if(paper.ck9_2.isNullOrEmpty()){

            }else{
                cancer_editText4.setText(paper.ck9_2)
            }
        }

        if(paper.ck10 == "1"){
            cancer_10_1.isChecked = true
        }else if(paper.ck10 == "2"){
            cancer_10_2.isChecked = true
        }else if(paper.ck10 == "3"){
            cancer_10_3.isChecked = true
        }else if(paper.ck10 == "4"){
            cancer_10_4.isChecked = true
        }else if(paper.ck10 == "5"){
            cancer_10_5.isChecked = true
        }


        if(paper.ck11 == "1"){
            cancer_11_1.isChecked = true
        }else if(paper.ck11 == "2"){
            cancer_11_2.isChecked = true
        }else if(paper.ck11 == "3"){
            cancer_11_3.isChecked = true
        }


        if(paper.ck12 == "1"){
            cancer_12_1.isChecked = true
        }else if(paper.ck12 == "2"){
            cancer_12_2.isChecked = true
        }else if(paper.ck12 == "3"){
            cancer_12_3.isChecked = true
        }else if(paper.ck12 == "4"){
            cancer_12_4.isChecked = true
        }

        if(paper.ck13 == "1"){
            cancer_13_1.isChecked = true
        }else if(paper.ck13 == "2"){
            cancer_13_2.isChecked = true
        }else if(paper.ck13 == "3"){
            cancer_13_3.isChecked = true
        }

        if(paper.ck14 == "1"){
            cancer_14_1.isChecked = true
        }else if(paper.ck14 == "2"){
            cancer_14_2.isChecked = true
        }else if(paper.ck14 == "3"){
            cancer_14_3.isChecked = true
        }else if(paper.ck14 == "4"){
            cancer_14_4.isChecked = true
        }

    }

    fun GetPaper(paper: ServerPaper_Cancer) {

        state = "getPaper"

        cannotEditQuestionnaire(cancer_root)

        name_edit.text = paper.c_name
        first_serial.text = paper.c_jumin.substring(0, 6)
        last_serial.text = paper.c_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        println(paper)

        cancer_examination_save.visibility = View.GONE
        cancer_examination_cancel.visibility = View.GONE
        cancer_edit_submit.visibility = View.VISIBLE

        if(last_serial.text == "2" || last_serial.text == "4"){
            AdditionalArr.Gender.isFemale = true
        }

        if(paper.ck1 == "1"){
            cancer_1_1.isChecked = true
            if(paper.ck1_1.isNullOrEmpty()){

            }else{
                cancer_editText1.setText(paper.ck1_1)
            }
        }else if(paper.ck1 == "2"){
            cancer_1_2.isChecked = true
        }


        if(paper.ck2 == "1"){
            cancer_2_1.isChecked = true

        }else if(paper.ck2 == "2"){
            cancer_2_2.isChecked = true

            if(paper.ck2_1.isNullOrEmpty()){

            }else{
                cancer_editText2.setText(paper.ck2_1)
            }
        }


        if(paper.ck3_1 == "1"){
            cancer_3_1_1.isChecked = true

        }else if(paper.ck3_1 == "3"){
            cancer_3_1_2.isChecked = true

        }else if(paper.ck3_1 == "2"){
            cancer_3_1_3.isChecked = true

            cancer_3_1_checkBox1.visibility = View.VISIBLE
            cancer_3_1_checkBox2.visibility = View.VISIBLE
            cancer_3_1_checkBox3.visibility = View.VISIBLE
            cancer_3_1_checkBox4.visibility = View.VISIBLE
            cancer_3_1_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_1_1 == "2"){
                cancer_3_1_checkBox1.isChecked = true
            }
            if(paper.ck3_1_2 == "2"){
                cancer_3_1_checkBox2.isChecked = true
            }
            if(paper.ck3_1_3 == "2"){
                cancer_3_1_checkBox3.isChecked = true
            }
            if(paper.ck3_1_4 == "2"){
                cancer_3_1_checkBox4.isChecked = true
            }
            if(paper.ck3_1_5 == "2"){
                cancer_3_1_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_2 == "1"){
            cancer_3_2_1.isChecked = true

        }else if(paper.ck3_2 == "3"){
            cancer_3_2_2.isChecked = true

        }else if(paper.ck3_2 == "2"){
            cancer_3_2_3.isChecked = true

            cancer_3_2_checkBox1.visibility = View.VISIBLE
            cancer_3_2_checkBox2.visibility = View.VISIBLE
            cancer_3_2_checkBox3.visibility = View.VISIBLE
            cancer_3_2_checkBox4.visibility = View.VISIBLE
            cancer_3_2_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_2_1 == "2"){
                cancer_3_2_checkBox1.isChecked = true
            }
            if(paper.ck3_2_2 == "2"){
                cancer_3_2_checkBox2.isChecked = true
            }
            if(paper.ck3_2_3 == "2"){
                cancer_3_2_checkBox3.isChecked = true
            }
            if(paper.ck3_2_4 == "2"){
                cancer_3_2_checkBox4.isChecked = true
            }
            if(paper.ck3_2_5 == "2"){
                cancer_3_2_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_3 == "1"){
            cancer_3_3_1.isChecked = true

        }else if(paper.ck3_3 == "3"){
            cancer_3_3_2.isChecked = true

        }else if(paper.ck3_3 == "2"){
            cancer_3_3_3.isChecked = true

            cancer_3_3_checkBox1.visibility = View.VISIBLE
            cancer_3_3_checkBox2.visibility = View.VISIBLE
            cancer_3_3_checkBox3.visibility = View.VISIBLE
            cancer_3_3_checkBox4.visibility = View.VISIBLE
            cancer_3_3_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_3_1 == "2"){
                cancer_3_3_checkBox1.isChecked = true
            }
            if(paper.ck3_3_2 == "2"){
                cancer_3_3_checkBox2.isChecked = true
            }
            if(paper.ck3_3_3 == "2"){
                cancer_3_3_checkBox3.isChecked = true
            }
            if(paper.ck3_3_4 == "2"){
                cancer_3_3_checkBox4.isChecked = true
            }
            if(paper.ck3_3_5 == "2"){
                cancer_3_3_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_4 == "1"){
            cancer_3_4_1.isChecked = true

        }else if(paper.ck3_4 == "3"){
            cancer_3_4_2.isChecked = true

        }else if(paper.ck3_4 == "2"){
            cancer_3_4_3.isChecked = true

            cancer_3_4_checkBox1.visibility = View.VISIBLE
            cancer_3_4_checkBox2.visibility = View.VISIBLE
            cancer_3_4_checkBox3.visibility = View.VISIBLE
            cancer_3_4_checkBox4.visibility = View.VISIBLE
            cancer_3_4_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_4_1 == "2"){
                cancer_3_4_checkBox1.isChecked = true
            }
            if(paper.ck3_4_2 == "2"){
                cancer_3_4_checkBox2.isChecked = true
            }
            if(paper.ck3_4_3 == "2"){
                cancer_3_4_checkBox3.isChecked = true
            }
            if(paper.ck3_4_4 == "2"){
                cancer_3_4_checkBox4.isChecked = true
            }
            if(paper.ck3_4_5 == "2"){
                cancer_3_4_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_5 == "1"){
            cancer_3_5_1.isChecked = true

        }else if(paper.ck3_5 == "3"){
            cancer_3_5_2.isChecked = true

        }else if(paper.ck3_5 == "2"){
            cancer_3_5_3.isChecked = true

            cancer_3_5_checkBox1.visibility = View.VISIBLE
            cancer_3_5_checkBox2.visibility = View.VISIBLE
            cancer_3_5_checkBox3.visibility = View.VISIBLE
            cancer_3_5_checkBox4.visibility = View.VISIBLE
            cancer_3_5_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_5_1 == "2"){
                cancer_3_5_checkBox1.isChecked = true
            }
            if(paper.ck3_5_2 == "2"){
                cancer_3_5_checkBox2.isChecked = true
            }
            if(paper.ck3_5_3 == "2"){
                cancer_3_5_checkBox3.isChecked = true
            }
            if(paper.ck3_5_4 == "2"){
                cancer_3_5_checkBox4.isChecked = true
            }
            if(paper.ck3_5_5 == "2"){
                cancer_3_5_checkBox5.isChecked = true
            }

        }

        if(paper.ck15_5 == "1"){
            cancer_15_5_1.isChecked = true

        }else if(paper.ck15_5 == "3"){
            cancer_15_5_2.isChecked = true

        }else if(paper.ck15_5 == "2"){
            cancer_15_5_3.isChecked = true

            cancer_15_5_checkBox1.visibility = View.VISIBLE
            cancer_15_5_checkBox2.visibility = View.VISIBLE
            cancer_15_5_checkBox3.visibility = View.VISIBLE
            cancer_15_5_checkBox4.visibility = View.VISIBLE
            cancer_15_5_checkBox5.visibility = View.VISIBLE

            if(paper.ck15_5_1 == "2"){
                cancer_15_5_checkBox1.isChecked = true
            }
            if(paper.ck15_5_2 == "2"){
                cancer_15_5_checkBox2.isChecked = true
            }
            if(paper.ck15_5_3 == "2"){
                cancer_15_5_checkBox3.isChecked = true
            }
            if(paper.ck15_5_4 == "2"){
                cancer_15_5_checkBox4.isChecked = true
            }
            if(paper.ck15_5_5 == "2"){
                cancer_15_5_checkBox5.isChecked = true
            }

        }

        if(paper.ck3_6 == "1"){
            cancer_3_6_1.isChecked = true

        }else if(paper.ck3_6 == "3"){
            cancer_3_6_2.isChecked = true

        }else if(paper.ck3_6 == "2"){
            cancer_3_6_3.isChecked = true

            cancer_3_6_checkBox1.visibility = View.VISIBLE
            cancer_3_6_checkBox2.visibility = View.VISIBLE
            cancer_3_6_checkBox3.visibility = View.VISIBLE
            cancer_3_6_checkBox4.visibility = View.VISIBLE
            cancer_3_6_checkBox5.visibility = View.VISIBLE

            if(paper.ck3_6_1 == "2"){
                cancer_3_6_checkBox1.isChecked = true
            }
            if(paper.ck3_6_2 == "2"){
                cancer_3_6_checkBox2.isChecked = true
            }
            if(paper.ck3_6_3 == "2"){
                cancer_3_6_checkBox3.isChecked = true
            }
            if(paper.ck3_6_4 == "2"){
                cancer_3_6_checkBox4.isChecked = true
            }
            if(paper.ck3_6_5 == "2"){
                cancer_3_6_checkBox5.isChecked = true
            }

            if(paper.ck3_6_kita != ""){
                cancer_3_6_1_editText.setText(paper.ck3_6_kita)
            }

        }

        if(paper.ck4_1 == "1"){
            cancer_4_1_1.isChecked = true
        }else if(paper.ck4_1 == "2"){
            cancer_4_1_2.isChecked = true
        }else if(paper.ck4_1 == "3"){
            cancer_4_1_3.isChecked = true
        }else if(paper.ck4_1 == "4"){
            cancer_4_1_4.isChecked = true
        }


        if(paper.ck4_2 == "1"){
            cancer_4_2_1.isChecked = true
        }else if(paper.ck4_2 == "2"){
            cancer_4_2_2.isChecked = true
        }else if(paper.ck4_2 == "3"){
            cancer_4_2_3.isChecked = true
        }else if(paper.ck4_2 == "4"){
            cancer_4_2_4.isChecked = true
        }


        if(paper.ck4_3 == "1"){
            cancer_4_3_1.isChecked = true
        }else if(paper.ck4_3 == "2"){
            cancer_4_3_2.isChecked = true
        }else if(paper.ck4_3 == "3"){
            cancer_4_3_3.isChecked = true
        }else if(paper.ck4_3 == "4"){
            cancer_4_3_4.isChecked = true
        }

        if(paper.ck4_4 == "1"){
            cancer_4_4_1.isChecked = true
        }else if(paper.ck4_4 == "2"){
            cancer_4_4_2.isChecked = true
        }else if(paper.ck4_4 == "3"){
            cancer_4_4_3.isChecked = true
        }else if(paper.ck4_4 == "4"){
            cancer_4_4_4.isChecked = true
        }

        if(paper.ck4_5 == "1"){
            cancer_4_5_1.isChecked = true
        }else if(paper.ck4_5 == "2"){
            cancer_4_5_2.isChecked = true
        }else if(paper.ck4_5 == "3"){
            cancer_4_5_3.isChecked = true
        }else if(paper.ck4_5 == "4"){
            cancer_4_5_4.isChecked = true
        }

        if(paper.ck4_6 == "1"){
            cancer_4_6_1.isChecked = true
        }else if(paper.ck4_6 == "2"){
            cancer_4_6_2.isChecked = true
        }else if(paper.ck4_6 == "3"){
            cancer_4_6_3.isChecked = true
        }else if(paper.ck4_6 == "4"){
            cancer_4_6_4.isChecked = true
        }

        if(paper.ck4_7 == "1"){
            cancer_4_7_1.isChecked = true
        }else if(paper.ck4_7 == "2"){
            cancer_4_7_2.isChecked = true
        }else if(paper.ck4_7 == "3"){
            cancer_4_7_3.isChecked = true
        }else if(paper.ck4_7 == "4"){
            cancer_4_7_4.isChecked = true
        }

        if(paper.ck4_9 == "1"){
            cancer_4_9_1.isChecked = true
        }else if(paper.ck4_8 == "2"){
            cancer_4_9_2.isChecked = true
        }else if(paper.ck4_8 == "3"){
            cancer_4_9_3.isChecked = true
        }else if(paper.ck4_8 == "4"){
            cancer_4_9_4.isChecked = true
        }

        if(paper.ck4_8 == "1"){
            cancer_4_8_1.isChecked = true
        }else if(paper.ck4_8 == "2"){
            cancer_4_8_2.isChecked = true
        }else if(paper.ck4_8 == "3"){
            cancer_4_8_3.isChecked = true
        }else if(paper.ck4_8 == "4"){
            cancer_4_8_4.isChecked = true
        }

        if(paper.ck5_1 != "2" && paper.ck5_2 != "2" && paper.ck5_3 != "2" && paper.ck5_4 != "2" && paper.ck5_5 != "2"){
            cancer_5_0_checkBox.isChecked = true
        }
        if(paper.ck5_1 == "2"){
            cancer_5_1_checkBox.isChecked = true
        }
        if(paper.ck5_2 == "2"){
            cancer_5_2_checkBox.isChecked = true
        }
        if(paper.ck5_3 == "2"){
            cancer_5_3_checkBox.isChecked = true
        }
        if(paper.ck5_4 == "2"){
            cancer_5_4_checkBox.isChecked = true
        }
        if(paper.ck5_5 == "2"){
            cancer_5_5_checkBox.isChecked = true
        }

        if(paper.ck6_1 != "2" && paper.ck6_2 != "2" && paper.ck6_3 != "2" && paper.ck6_4 != "2" && paper.ck6_5 != "2"){
            cancer_6_0_checkBox.isChecked = true
        }
        if(paper.ck6_1 == "2"){
            cancer_6_1_checkBox.isChecked = true
        }
        if(paper.ck6_2 == "2"){
            cancer_6_2_checkBox.isChecked = true
        }
        if(paper.ck6_3 == "2"){
            cancer_6_3_checkBox.isChecked = true
        }
        if(paper.ck6_4 == "2"){
            cancer_6_4_checkBox.isChecked = true
        }
        if(paper.ck6_5 == "2"){
            cancer_6_5_checkBox.isChecked = true
        }


        if(paper.ck7_1 != "2" && paper.ck7_2 != "2" && paper.ck7_3 != "2" && paper.ck7_4 != "2" && paper.ck7_5 != "2"){
            cancer_7_0_checkBox.isChecked = true
        }
        if(paper.ck7_1 == "2"){
            cancer_7_1_checkBox.isChecked = true
        }
        if(paper.ck7_2 == "2"){
            cancer_7_2_checkBox.isChecked = true
        }
        if(paper.ck7_3 == "2"){
            cancer_7_3_checkBox.isChecked = true
        }
        if(paper.ck7_4 == "2"){
            cancer_7_4_checkBox.isChecked = true
        }
        if(paper.ck7_5 == "2"){
            cancer_7_5_checkBox.isChecked = true
        }

        if(paper.ck16_1 != "2" && paper.ck16_2 != "2" && paper.ck16_3 != "2" && paper.ck16_4 != "2" && paper.ck16_5 != "2" && paper.ck16_6 != "2"){
            cancer_16_0_checkBox.isChecked = true
        }
        if(paper.ck16_1 == "2"){
            cancer_16_1_checkBox.isChecked = true
        }
        if(paper.ck16_2 == "2"){
            cancer_16_2_checkBox.isChecked = true
        }
        if(paper.ck16_3 == "2"){
            cancer_16_3_checkBox.isChecked = true
        }
        if(paper.ck16_4 == "2"){
            cancer_16_4_checkBox.isChecked = true
        }
        if(paper.ck16_5 == "2"){
            cancer_16_5_checkBox.isChecked = true
        }
        if(paper.ck16_6 == "2"){
            cancer_16_6_checkBox.isChecked = true
        }

        if(paper.ck8_1 == "1"){
            cancer_8_1.isChecked = true
            if(paper.ck8_2.isNullOrEmpty()){

            }else{
                cancer_editText3.setText(paper.ck8_2)
            }
        }else if(paper.ck8_1 == "2"){
            cancer_8_2.isChecked = true
        }

        if(paper.ck9_1 == "1"){
            cancer_9_1.isChecked = true
        }else if(paper.ck9_1 == "2"){
            cancer_9_2.isChecked = true
        }else if(paper.ck9_1 == "3"){
            cancer_9_3.isChecked = true
            if(paper.ck9_2.isNullOrEmpty()){

            }else{
                cancer_editText4.setText(paper.ck9_2)
            }
        }

        if(paper.ck10 == "1"){
            cancer_10_1.isChecked = true
        }else if(paper.ck10 == "2"){
            cancer_10_2.isChecked = true
        }else if(paper.ck10 == "3"){
            cancer_10_3.isChecked = true
        }else if(paper.ck10 == "4"){
            cancer_10_4.isChecked = true
        }else if(paper.ck10 == "5"){
            cancer_10_5.isChecked = true
        }


        if(paper.ck11 == "1"){
            cancer_11_1.isChecked = true
        }else if(paper.ck11 == "2"){
            cancer_11_2.isChecked = true
        }else if(paper.ck11 == "3"){
            cancer_11_3.isChecked = true
        }


        if(paper.ck12 == "1"){
            cancer_12_1.isChecked = true
        }else if(paper.ck12 == "2"){
            cancer_12_2.isChecked = true
        }else if(paper.ck12 == "3"){
            cancer_12_3.isChecked = true
        }else if(paper.ck12 == "4"){
            cancer_12_4.isChecked = true
        }

        if(paper.ck13 == "1"){
            cancer_13_1.isChecked = true
        }else if(paper.ck13 == "2"){
            cancer_13_2.isChecked = true
        }else if(paper.ck13 == "3"){
            cancer_13_3.isChecked = true
        }

        if(paper.ck14 == "1"){
            cancer_14_1.isChecked = true
        }else if(paper.ck14 == "2"){
            cancer_14_2.isChecked = true
        }else if(paper.ck14 == "3"){
            cancer_14_3.isChecked = true
        }else if(paper.ck14 == "4"){
            cancer_14_4.isChecked = true
        }

    }

    fun whenRadioChanged(buttonView : View, isChecked : Boolean){

        when(buttonView.id){

            R.id.cancer_3_1_3 -> {
                if(!isChecked){
                    cancer_3_1_checkBox1.isChecked = false
                    cancer_3_1_checkBox2.isChecked = false
                    cancer_3_1_checkBox3.isChecked = false
                    cancer_3_1_checkBox4.isChecked = false
                    cancer_3_1_checkBox5.isChecked = false
                }
            }
            R.id.cancer_3_2_3 -> {
                if(!isChecked){
                    cancer_3_2_checkBox1.isChecked = false
                    cancer_3_2_checkBox2.isChecked = false
                    cancer_3_2_checkBox3.isChecked = false
                    cancer_3_2_checkBox4.isChecked = false
                    cancer_3_2_checkBox5.isChecked = false
                }
            }
            R.id.cancer_3_3_3 -> {
                if(!isChecked){
                    cancer_3_3_checkBox1.isChecked = false
                    cancer_3_3_checkBox2.isChecked = false
                    cancer_3_3_checkBox3.isChecked = false
                    cancer_3_3_checkBox4.isChecked = false
                    cancer_3_3_checkBox5.isChecked = false
                }
            }
            R.id.cancer_3_4_3 -> {
                if(!isChecked){
                    cancer_3_4_checkBox1.isChecked = false
                    cancer_3_4_checkBox2.isChecked = false
                    cancer_3_4_checkBox3.isChecked = false
                    cancer_3_4_checkBox4.isChecked = false
                    cancer_3_4_checkBox5.isChecked = false
                }
            }
            R.id.cancer_3_5_3 -> {
                if(!isChecked){
                    cancer_3_5_checkBox1.isChecked = false
                    cancer_3_5_checkBox2.isChecked = false
                    cancer_3_5_checkBox3.isChecked = false
                    cancer_3_5_checkBox4.isChecked = false
                    cancer_3_5_checkBox5.isChecked = false
                }
            }
            R.id.cancer_3_6_3 -> {
                if(!isChecked){
                    cancer_3_6_checkBox1.isChecked = false
                    cancer_3_6_checkBox2.isChecked = false
                    cancer_3_6_checkBox3.isChecked = false
                    cancer_3_6_checkBox4.isChecked = false
                    cancer_3_6_checkBox5.isChecked = false
                }
            }
            R.id.cancer_15_5_3 -> {
                if(!isChecked){
                    cancer_15_5_checkBox1.isChecked = false
                    cancer_15_5_checkBox2.isChecked = false
                    cancer_15_5_checkBox3.isChecked = false
                    cancer_15_5_checkBox4.isChecked = false
                    cancer_15_5_checkBox5.isChecked = false
                }
            }
        }

    }

    fun cancerCheckCondition(buttonView: View, isChecked: Boolean){
        when(buttonView.id){

            R.id.cancer_5_0_checkBox -> {
                if(isChecked){
                    cancer_5_1_checkBox.isChecked = false
                    cancer_5_2_checkBox.isChecked = false
                    cancer_5_3_checkBox.isChecked = false
                    cancer_5_4_checkBox.isChecked = false
                    cancer_5_5_checkBox.isChecked = false
                }
            }
            R.id.cancer_5_1_checkBox -> { if (isChecked) cancer_5_0_checkBox.isChecked = false }
            R.id.cancer_5_2_checkBox -> { if (isChecked) cancer_5_0_checkBox.isChecked = false }
            R.id.cancer_5_3_checkBox -> { if (isChecked) cancer_5_0_checkBox.isChecked = false }
            R.id.cancer_5_4_checkBox -> { if (isChecked) cancer_5_0_checkBox.isChecked = false }
            R.id.cancer_5_5_checkBox -> { if (isChecked) cancer_5_0_checkBox.isChecked = false }

            R.id.cancer_6_0_checkBox -> {
                if(isChecked){
                    cancer_6_1_checkBox.isChecked = false
                    cancer_6_2_checkBox.isChecked = false
                    cancer_6_3_checkBox.isChecked = false
                    cancer_6_4_checkBox.isChecked = false
                    cancer_6_5_checkBox.isChecked = false
                }
            }
            R.id.cancer_6_1_checkBox -> { if (isChecked) cancer_6_0_checkBox.isChecked = false }
            R.id.cancer_6_2_checkBox -> { if (isChecked) cancer_6_0_checkBox.isChecked = false }
            R.id.cancer_6_3_checkBox -> { if (isChecked) cancer_6_0_checkBox.isChecked = false }
            R.id.cancer_6_4_checkBox -> { if (isChecked) cancer_6_0_checkBox.isChecked = false }
            R.id.cancer_6_5_checkBox -> { if (isChecked) cancer_6_0_checkBox.isChecked = false }

            R.id.cancer_7_0_checkBox -> {
                if(isChecked){
                    cancer_7_1_checkBox.isChecked = false
                    cancer_7_2_checkBox.isChecked = false
                    cancer_7_3_checkBox.isChecked = false
                    cancer_7_4_checkBox.isChecked = false
                    cancer_7_5_checkBox.isChecked = false
                }
            }
            R.id.cancer_7_1_checkBox -> {if (isChecked) cancer_7_0_checkBox.isChecked = false }
            R.id.cancer_7_2_checkBox -> {if (isChecked) cancer_7_0_checkBox.isChecked = false }
            R.id.cancer_7_3_checkBox -> {if (isChecked) cancer_7_0_checkBox.isChecked = false }
            R.id.cancer_7_4_checkBox -> {if (isChecked) cancer_7_0_checkBox.isChecked = false }
            R.id.cancer_7_5_checkBox -> {if (isChecked) cancer_7_0_checkBox.isChecked = false }

            R.id.cancer_16_0_checkBox -> {
                if(isChecked){
                    cancer_16_1_checkBox.isChecked = false
                    cancer_16_2_checkBox.isChecked = false
                    cancer_16_3_checkBox.isChecked = false
                    cancer_16_4_checkBox.isChecked = false
                    cancer_16_5_checkBox.isChecked = false
                    cancer_16_6_checkBox.isChecked = false
                }
            }
            R.id.cancer_16_1_checkBox -> { if (isChecked) cancer_16_0_checkBox.isChecked = false }
            R.id.cancer_16_2_checkBox -> { if (isChecked) cancer_16_0_checkBox.isChecked = false }
            R.id.cancer_16_3_checkBox -> { if (isChecked) cancer_16_0_checkBox.isChecked = false }
            R.id.cancer_16_4_checkBox -> { if (isChecked) cancer_16_0_checkBox.isChecked = false }
            R.id.cancer_16_5_checkBox -> { if (isChecked) cancer_16_0_checkBox.isChecked = false }
            R.id.cancer_16_6_checkBox -> { if (isChecked) cancer_16_0_checkBox.isChecked = false }
        }
    }

}