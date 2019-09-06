package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import com.fineinsight.zzango.questionnaire.LocalList.Paper_DRINKING
import com.fineinsight.zzango.questionnaire.LocalList.Paper_SMOKING
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_drinking_exam.*
import kotlinx.android.synthetic.main.progressbar2.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("NewApi", "SimpleDateFormat")
class DrinkingExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinking_exam)

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        drinking_0_true.isChecked = true

        drinking_0_true.setOnCheckedChangeListener {

            buttonView, isChecked ->

            checkCondition(isChecked, drinking_question_constraintLayout)

        }

        drinking_examination_save.setOnClickListener {
//            AdditionalArr.over.checkAll = false
            if(check()){
                if(MainActivity.chart.isEmpty()){
                    if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local")){
                        ChartDivision.ChartDivision.local_each_insert(this, 4)
                    }else{
                        ChartDivision.ChartDivision.server_insert(this)
                    }
                }else{
                    ChartDivision.ChartDivision.chart_array_insert(this, 4, getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local"))
                }
            }
        }

        drinking_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        drinking_edit_submit.setOnClickListener {

            AdditionalArr.over.checkAll = false
            finish()

        }

        if(AdditionalArr.over.checkAll){
            if(AdditionalArr.over.isDrinking){
                drinking_0_true.isChecked = true
                drinking_0_false.isChecked = false
                drinking_0_true.isEnabled = false
                drinking_0_false.isEnabled = false
                if(SavePaper.Total.temp_Drinking != null){
                    SavePaper.Total.temp_Drinking!!.sg2_spDrinkSum = "1"
                }else if((SavePaper.Total.Array[8] as Paper_DRINKING).sg2_spDrinkSum.isNotEmpty()){
                    (SavePaper.Total.Array[8] as Paper_DRINKING).sg2_spDrinkSum = "1"
                }
            }else if(AdditionalArr.over.isDrinking2){
                drinking_0_true.isChecked = true
                drinking_0_false.isChecked = false
                drinking_0_true.isEnabled = false
                drinking_0_false.isEnabled = false
                if(SavePaper.Total.temp_Drinking != null){
                    SavePaper.Total.temp_Drinking!!.sg2_spDrinkSum = "1"
                }else if((SavePaper.Total.Array[8] as Paper_DRINKING).sg2_spDrinkSum.isNotEmpty()){
                    (SavePaper.Total.Array[8] as Paper_DRINKING).sg2_spDrinkSum = "1"
                }
            }else{
                drinking_0_true.isChecked = false
                drinking_0_false.isChecked = true
                drinking_0_true.isEnabled = false
                drinking_0_false.isEnabled = false
                if(SavePaper.Total.temp_Drinking != null){
                    SavePaper.Total.temp_Drinking!!.sg2_spDrinkSum = "0"
                }else if((SavePaper.Total.Array[8] as Paper_DRINKING).sg2_spDrinkSum.isNotEmpty()){
                    (SavePaper.Total.Array[8] as Paper_DRINKING).sg2_spDrinkSum = "0"
                }
            }
        }else{
            drinking_0_true.isEnabled = true
            drinking_0_false.isEnabled = true
        }

        if(SavePaper.Total.temp_Drinking != null){
            whenTempLoad(SavePaper.Total.temp_Drinking!!)
        }else if(SavedListObject.SavedList.savedDataClass.drinkingSaved){
            whenTempLoad(SavePaper.Total.Array[8] as Paper_DRINKING)
        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_DRINKING) {

                var paper = intent.getSerializableExtra("paper") as Paper_DRINKING

                GetPaper(paper)

                try {
//                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)
//
//                    Signature.setImageBitmap(bmp)

                } catch (e: Exception) {
                    println(e.message)
                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Life

                GetPaper(paper)

            }

        }else{
            name_edit.text = MainActivity.login_user_name
            first_serial.text = MainActivity.user_first_serial
            last_serial.text = MainActivity.user_last_serial


            if(MainActivity.chart.isEmpty()){
                drinking_examination_save.text = "저장"
            }else{
                if(ChartDivision.ChartDivision.next_or_save(4)){
                    drinking_examination_save.text = "다음"
                }else{
                    drinking_examination_save.text = "저장"
                }
            }

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        var category = PaperNameInfo.PC.DRINKING.EN_NM
        var sg2_spDrink1 = ""
        var sg2_spDrink2_1 = ""
        var sg2_spDrink2_2 = ""
        var sg2_spDrink3 = ""
        var sg2_spDrink4 = ""
        var sg2_spDrink5 = ""
        var sg2_spDrink6 = ""
        var sg2_spDrink7 = ""
        var sg2_spDrink8 = ""
        var sg2_spDrink9 = ""
        var sg2_spDrink10 = ""
        var sg2_spDrinkSum = ""

        if (!name_edit.text.isNullOrEmpty()) {
            name = name_edit.text.toString()
        }

        if (!first_serial.text.isNullOrEmpty()) {
            first_serial_text = first_serial.text.toString()
        }

        if (!last_serial.text.isNullOrEmpty()) {
            last_serial_text = last_serial.text.toString()
        }

        if(drinking_0_false.isChecked){
            sg2_spDrinkSum = "0"
        }else {
            sg2_spDrinkSum = "1"

            sg2_spDrink1 = when {
                drinking_1_1.isChecked -> "1"
                drinking_1_2.isChecked -> "2"
                drinking_1_3.isChecked -> "3"
                drinking_1_4.isChecked -> "4"
                else -> ""
            }

            sg2_spDrink2_1 = when {
                drinking_2_1_1.isChecked -> "1"
                drinking_2_1_2.isChecked -> "2"
                drinking_2_1_3.isChecked -> "3"
                drinking_2_1_4.isChecked -> "4"
                drinking_2_1_5.isChecked -> "5"
                else -> ""
            }

            sg2_spDrink2_2 = when {
                drinking_2_2_1.isChecked -> "1"
                drinking_2_2_2.isChecked -> "2"
                drinking_2_2_3.isChecked -> "3"
                drinking_2_2_4.isChecked -> "4"
                drinking_2_2_5.isChecked -> "5"
                else -> ""
            }

            sg2_spDrink3 = when {
                drinking_3_1.isChecked -> "1"
                drinking_3_2.isChecked -> "2"
                drinking_3_3.isChecked -> "3"
                drinking_3_4.isChecked -> "4"
                drinking_3_5.isChecked -> "5"
                else -> ""
            }

            sg2_spDrink4 = when {
                drinking_4_1.isChecked -> "1"
                drinking_4_2.isChecked -> "2"
                drinking_4_3.isChecked -> "3"
                drinking_4_4.isChecked -> "4"
                drinking_4_5.isChecked -> "5"
                else -> ""
            }

            sg2_spDrink5 = when {
                drinking_5_1.isChecked -> "1"
                drinking_5_2.isChecked -> "2"
                drinking_5_3.isChecked -> "3"
                drinking_5_4.isChecked -> "4"
                drinking_5_5.isChecked -> "5"
                else -> ""
            }


            sg2_spDrink6 = when {
                drinking_6_1.isChecked -> "1"
                drinking_6_2.isChecked -> "2"
                drinking_6_3.isChecked -> "3"
                drinking_6_4.isChecked -> "4"
                drinking_6_5.isChecked -> "5"
                else -> ""
            }


            sg2_spDrink7 = when {
                drinking_7_1.isChecked -> "1"
                drinking_7_2.isChecked -> "2"
                drinking_7_3.isChecked -> "3"
                drinking_7_4.isChecked -> "4"
                drinking_7_5.isChecked -> "5"
                else -> ""
            }


            sg2_spDrink8 = when {
                drinking_8_1.isChecked -> "1"
                drinking_8_2.isChecked -> "2"
                drinking_8_3.isChecked -> "3"
                drinking_8_4.isChecked -> "4"
                drinking_8_5.isChecked -> "5"
                else -> ""
            }

            sg2_spDrink9 = when {
                drinking_9_1.isChecked -> "1"
                drinking_9_2.isChecked -> "2"
                drinking_9_3.isChecked -> "3"
                else -> ""
            }


            sg2_spDrink10 = when {
                drinking_10_1.isChecked -> "1"
                drinking_10_2.isChecked -> "2"
                drinking_10_3.isChecked -> "3"
                else -> ""
            }
        }

        SavePaper.Total.temp_Drinking = Paper_DRINKING(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no,  name, first_serial_text, last_serial_text, category,
                sg2_spDrink1, sg2_spDrink2_1, sg2_spDrink2_2, sg2_spDrink3, sg2_spDrink4, sg2_spDrink5,
                sg2_spDrink6, sg2_spDrink7, sg2_spDrink8, sg2_spDrink9, sg2_spDrink10, sg2_spDrinkSum
        )

    }

    fun check() : Boolean {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = "drinking"
        var sg2_spDrink1 = ""
        var sg2_spDrink2_1 = ""
        var sg2_spDrink2_2 = ""
        var sg2_spDrink3 = ""
        var sg2_spDrink4 = ""
        var sg2_spDrink5 = ""
        var sg2_spDrink6 = ""
        var sg2_spDrink7 = ""
        var sg2_spDrink8 = ""
        var sg2_spDrink9 = ""
        var sg2_spDrink10 = ""
        var sg2_spDrinkSum = ""

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

        if(drinking_0_false.isChecked){
            sg2_spDrinkSum = "0"
        }else {
            sg2_spDrinkSum = "1"

            if (drinking_1_1.isChecked) {
                sg2_spDrink1 = "1"
            } else if (drinking_1_2.isChecked) {
                sg2_spDrink1 = "2"
            } else if (drinking_1_3.isChecked) {
                sg2_spDrink1 = "3"
            } else if (drinking_1_4.isChecked) {
                sg2_spDrink1 = "4"
            } else {
                Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (drinking_2_1_1.isChecked) {
                sg2_spDrink2_1 = "1"
            } else if (drinking_2_1_2.isChecked) {
                sg2_spDrink2_1 = "2"
            } else if (drinking_2_1_3.isChecked) {
                sg2_spDrink2_1 = "3"
            } else if (drinking_2_1_4.isChecked) {
                sg2_spDrink2_1 = "4"
            } else if (drinking_2_1_5.isChecked) {
                sg2_spDrink2_1 = "5"
            } else {
                Toast.makeText(this, "2-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (drinking_2_2_1.isChecked) {
                sg2_spDrink2_2 = "1"
            } else if (drinking_2_2_2.isChecked) {
                sg2_spDrink2_2 = "2"
            } else if (drinking_2_2_3.isChecked) {
                sg2_spDrink2_2 = "3"
            } else if (drinking_2_2_4.isChecked) {
                sg2_spDrink2_2 = "4"
            } else if (drinking_2_2_5.isChecked) {
                sg2_spDrink2_2 = "5"
            } else {
                Toast.makeText(this, "2-2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (drinking_3_1.isChecked) {
                sg2_spDrink3 = "1"
            } else if (drinking_3_2.isChecked) {
                sg2_spDrink3 = "2"
            } else if (drinking_3_3.isChecked) {
                sg2_spDrink3 = "3"
            } else if (drinking_3_4.isChecked) {
                sg2_spDrink3 = "4"
            } else if (drinking_3_5.isChecked) {
                sg2_spDrink3 = "5"
            } else {
                Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (drinking_4_1.isChecked) {
                sg2_spDrink4 = "1"
            } else if (drinking_4_2.isChecked) {
                sg2_spDrink4 = "2"
            } else if (drinking_4_3.isChecked) {
                sg2_spDrink4 = "3"
            } else if (drinking_4_4.isChecked) {
                sg2_spDrink4 = "4"
            } else if (drinking_4_5.isChecked) {
                sg2_spDrink4 = "5"
            } else {
                Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (drinking_5_1.isChecked) {
                sg2_spDrink5 = "1"
            } else if (drinking_5_2.isChecked) {
                sg2_spDrink5 = "2"
            } else if (drinking_5_3.isChecked) {
                sg2_spDrink5 = "3"
            } else if (drinking_5_4.isChecked) {
                sg2_spDrink5 = "4"
            } else if (drinking_5_5.isChecked) {
                sg2_spDrink5 = "5"
            } else {
                Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }


            if (drinking_6_1.isChecked) {
                sg2_spDrink6 = "1"
            } else if (drinking_6_2.isChecked) {
                sg2_spDrink6 = "2"
            } else if (drinking_6_3.isChecked) {
                sg2_spDrink6 = "3"
            } else if (drinking_6_4.isChecked) {
                sg2_spDrink6 = "4"
            } else if (drinking_6_5.isChecked) {
                sg2_spDrink6 = "5"
            } else {
                Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }


            if (drinking_7_1.isChecked) {
                sg2_spDrink7 = "1"
            } else if (drinking_7_2.isChecked) {
                sg2_spDrink7 = "2"
            } else if (drinking_7_3.isChecked) {
                sg2_spDrink7 = "3"
            } else if (drinking_7_4.isChecked) {
                sg2_spDrink7 = "4"
            } else if (drinking_7_5.isChecked) {
                sg2_spDrink7 = "5"
            } else {
                Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }


            if (drinking_8_1.isChecked) {
                sg2_spDrink8 = "1"
            } else if (drinking_8_2.isChecked) {
                sg2_spDrink8 = "2"
            } else if (drinking_8_3.isChecked) {
                sg2_spDrink8 = "3"
            } else if (drinking_8_4.isChecked) {
                sg2_spDrink8 = "4"
            } else if (drinking_8_5.isChecked) {
                sg2_spDrink8 = "5"
            } else {
                Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }

            if (drinking_9_1.isChecked) {
                sg2_spDrink9 = "1"
            } else if (drinking_9_2.isChecked) {
                sg2_spDrink9 = "2"
            } else if (drinking_9_3.isChecked) {
                sg2_spDrink9 = "3"
            } else {
                Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }


            if (drinking_10_1.isChecked) {
                sg2_spDrink10 = "1"
            } else if (drinking_10_2.isChecked) {
                sg2_spDrink10 = "2"
            } else if (drinking_10_3.isChecked) {
                sg2_spDrink10 = "3"
            } else {
                Toast.makeText(this, "10번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }

        SavePaper.Total.Array[8] = Paper_DRINKING(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                sg2_spDrink1, sg2_spDrink2_1, sg2_spDrink2_2, sg2_spDrink3, sg2_spDrink4, sg2_spDrink5,
                sg2_spDrink6, sg2_spDrink7, sg2_spDrink8, sg2_spDrink9, sg2_spDrink10, sg2_spDrinkSum
        )

        if (ChartDivision.ChartDivision.next_or_save(4)) {
            SavedListObject.SavedList.savedDataClass.drinkingSaved = true
        }

        SavePaper.Total.temp_Drinking = null

        return true

    }

    fun whenTempLoad(paper: Paper_DRINKING) {

        ChartDivision.ChartDivision.ProgressAction(true, this)

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        if(paper.sg2_spDrinkSum=="0"){
            drinking_0_false.isChecked = true
        }else if(paper.sg2_spDrinkSum=="1"){
            drinking_0_true.isChecked = true
        }

        if(paper.sg2_spDrink1 == "1"){
            drinking_1_1.isChecked = true
        }else if(paper.sg2_spDrink1 == "2"){
            drinking_1_2.isChecked = true
        }else if(paper.sg2_spDrink1 == "3"){
            drinking_1_3.isChecked = true
        }else if(paper.sg2_spDrink1 == "4"){
            drinking_1_4.isChecked = true
        }


        if(paper.sg2_spDrink2_1 == "1"){
            drinking_2_1_1.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "2"){
            drinking_2_1_2.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "3"){
            drinking_2_1_3.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "4"){
            drinking_2_1_4.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "5"){
            drinking_2_1_5.isChecked = true
        }


        if(paper.sg2_spDrink2_2 == "1"){
            drinking_2_2_1.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "2"){
            drinking_2_2_2.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "3"){
            drinking_2_2_3.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "4"){
            drinking_2_2_4.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "5"){
            drinking_2_2_5.isChecked = true
        }


        if(paper.sg2_spDrink3 == "1"){
            drinking_3_1.isChecked = true
        }else if(paper.sg2_spDrink3 == "2"){
            drinking_3_2.isChecked = true
        }else if(paper.sg2_spDrink3 == "3"){
            drinking_3_3.isChecked = true
        }else if(paper.sg2_spDrink3 == "4"){
            drinking_3_4.isChecked = true
        }else if(paper.sg2_spDrink3 == "5"){
            drinking_3_5.isChecked = true
        }


        if(paper.sg2_spDrink4 == "1"){
            drinking_4_1.isChecked = true
        }else if(paper.sg2_spDrink4 == "2"){
            drinking_4_2.isChecked = true
        }else if(paper.sg2_spDrink4 == "3"){
            drinking_4_3.isChecked = true
        }else if(paper.sg2_spDrink4 == "4"){
            drinking_4_4.isChecked = true
        }else if(paper.sg2_spDrink4 == "5"){
            drinking_4_5.isChecked = true
        }


        if(paper.sg2_spDrink5 == "1"){
            drinking_5_1.isChecked = true
        }else if(paper.sg2_spDrink5 == "2"){
            drinking_5_2.isChecked = true
        }else if(paper.sg2_spDrink5 == "3"){
            drinking_5_3.isChecked = true
        }else if(paper.sg2_spDrink5 == "4"){
            drinking_5_4.isChecked = true
        }else if(paper.sg2_spDrink5 == "5"){
            drinking_5_5.isChecked = true
        }


        if(paper.sg2_spDrink6 == "1"){
            drinking_6_1.isChecked = true
        }else if(paper.sg2_spDrink6 == "2"){
            drinking_6_2.isChecked = true
        }else if(paper.sg2_spDrink6 == "3"){
            drinking_6_3.isChecked = true
        }else if(paper.sg2_spDrink6 == "4"){
            drinking_6_4.isChecked = true
        }else if(paper.sg2_spDrink6 == "5"){
            drinking_6_5.isChecked = true
        }


        if(paper.sg2_spDrink7 == "1"){
            drinking_7_1.isChecked = true
        }else if(paper.sg2_spDrink7 == "2"){
            drinking_7_2.isChecked = true
        }else if(paper.sg2_spDrink7 == "3"){
            drinking_7_3.isChecked = true
        }else if(paper.sg2_spDrink7 == "4"){
            drinking_7_4.isChecked = true
        }else if(paper.sg2_spDrink7 == "5"){
            drinking_7_5.isChecked = true
        }

        if(paper.sg2_spDrink8 == "1"){
            drinking_8_1.isChecked = true
        }else if(paper.sg2_spDrink8 == "2"){
            drinking_8_2.isChecked = true
        }else if(paper.sg2_spDrink8 == "3"){
            drinking_8_3.isChecked = true
        }else if(paper.sg2_spDrink8 == "4"){
            drinking_8_4.isChecked = true
        }else if(paper.sg2_spDrink8 == "5"){
            drinking_8_5.isChecked = true
        }


        if(paper.sg2_spDrink9 == "1"){
            drinking_9_1.isChecked = true
        }else if(paper.sg2_spDrink9 == "2"){
            drinking_9_2.isChecked = true
        }else if(paper.sg2_spDrink9 == "3"){
            drinking_9_3.isChecked = true
        }


        if(paper.sg2_spDrink10 == "1"){
            drinking_10_1.isChecked = true
        }else if(paper.sg2_spDrink10 == "2"){
            drinking_10_2.isChecked = true
        }else if(paper.sg2_spDrink10 == "3"){
            drinking_10_3.isChecked = true
        }

    }

    fun GetPaper(paper: Paper_DRINKING) {

        state = "getPaper"

        cannotEditQuestionnaire(drinking_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        drinking_examination_save.visibility = View.GONE
        drinking_examination_cancel.visibility = View.GONE
        drinking_edit_submit.visibility = View.VISIBLE

        if(paper.sg2_spDrinkSum=="0"){
            drinking_0_false.isChecked = true
        }else if(paper.sg2_spDrinkSum=="1"){
            drinking_0_true.isChecked = true
        }

        if(paper.sg2_spDrink1 == "1"){
            drinking_1_1.isChecked = true
        }else if(paper.sg2_spDrink1 == "2"){
            drinking_1_2.isChecked = true
        }else if(paper.sg2_spDrink1 == "3"){
            drinking_1_3.isChecked = true
        }else if(paper.sg2_spDrink1 == "4"){
            drinking_1_4.isChecked = true
        }


        if(paper.sg2_spDrink2_1 == "1"){
            drinking_2_1_1.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "2"){
            drinking_2_1_2.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "3"){
            drinking_2_1_3.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "4"){
            drinking_2_1_4.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "5"){
            drinking_2_1_5.isChecked = true
        }


        if(paper.sg2_spDrink2_2 == "1"){
            drinking_2_2_1.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "2"){
            drinking_2_2_2.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "3"){
            drinking_2_2_3.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "4"){
            drinking_2_2_4.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "5"){
            drinking_2_2_5.isChecked = true
        }


        if(paper.sg2_spDrink3 == "1"){
            drinking_3_1.isChecked = true
        }else if(paper.sg2_spDrink3 == "2"){
            drinking_3_2.isChecked = true
        }else if(paper.sg2_spDrink3 == "3"){
            drinking_3_3.isChecked = true
        }else if(paper.sg2_spDrink3 == "4"){
            drinking_3_4.isChecked = true
        }else if(paper.sg2_spDrink3 == "5"){
            drinking_3_5.isChecked = true
        }


        if(paper.sg2_spDrink4 == "1"){
            drinking_4_1.isChecked = true
        }else if(paper.sg2_spDrink4 == "2"){
            drinking_4_2.isChecked = true
        }else if(paper.sg2_spDrink4 == "3"){
            drinking_4_3.isChecked = true
        }else if(paper.sg2_spDrink4 == "4"){
            drinking_4_4.isChecked = true
        }else if(paper.sg2_spDrink4 == "5"){
            drinking_4_5.isChecked = true
        }


        if(paper.sg2_spDrink5 == "1"){
            drinking_5_1.isChecked = true
        }else if(paper.sg2_spDrink5 == "2"){
            drinking_5_2.isChecked = true
        }else if(paper.sg2_spDrink5 == "3"){
            drinking_5_3.isChecked = true
        }else if(paper.sg2_spDrink5 == "4"){
            drinking_5_4.isChecked = true
        }else if(paper.sg2_spDrink5 == "5"){
            drinking_5_5.isChecked = true
        }


        if(paper.sg2_spDrink6 == "1"){
            drinking_6_1.isChecked = true
        }else if(paper.sg2_spDrink6 == "2"){
            drinking_6_2.isChecked = true
        }else if(paper.sg2_spDrink6 == "3"){
            drinking_6_3.isChecked = true
        }else if(paper.sg2_spDrink6 == "4"){
            drinking_6_4.isChecked = true
        }else if(paper.sg2_spDrink6 == "5"){
            drinking_6_5.isChecked = true
        }


        if(paper.sg2_spDrink7 == "1"){
            drinking_7_1.isChecked = true
        }else if(paper.sg2_spDrink7 == "2"){
            drinking_7_2.isChecked = true
        }else if(paper.sg2_spDrink7 == "3"){
            drinking_7_3.isChecked = true
        }else if(paper.sg2_spDrink7 == "4"){
            drinking_7_4.isChecked = true
        }else if(paper.sg2_spDrink7 == "5"){
            drinking_7_5.isChecked = true
        }

        if(paper.sg2_spDrink8 == "1"){
            drinking_8_1.isChecked = true
        }else if(paper.sg2_spDrink8 == "2"){
            drinking_8_2.isChecked = true
        }else if(paper.sg2_spDrink8 == "3"){
            drinking_8_3.isChecked = true
        }else if(paper.sg2_spDrink8 == "4"){
            drinking_8_4.isChecked = true
        }else if(paper.sg2_spDrink8 == "5"){
            drinking_8_5.isChecked = true
        }


        if(paper.sg2_spDrink9 == "1"){
            drinking_9_1.isChecked = true
        }else if(paper.sg2_spDrink9 == "2"){
            drinking_9_2.isChecked = true
        }else if(paper.sg2_spDrink9 == "3"){
            drinking_9_3.isChecked = true
        }


        if(paper.sg2_spDrink10 == "1"){
            drinking_10_1.isChecked = true
        }else if(paper.sg2_spDrink10 == "2"){
            drinking_10_2.isChecked = true
        }else if(paper.sg2_spDrink10 == "3"){
            drinking_10_3.isChecked = true
        }

    }

    fun GetPaper(paper: ServerPaper_Life) {

        state = "getPaper"

        cannotEditQuestionnaire(drinking_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.sg2_name
        first_serial.text = paper.sg2_jumin.substring(0, 6)
        last_serial.text = paper.sg2_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        println(paper)

        drinking_examination_save.visibility = View.GONE
        drinking_examination_cancel.visibility = View.GONE
        drinking_edit_submit.visibility = View.VISIBLE

        if(paper.sg2_spDrinkSum=="0"){
            drinking_0_false.isChecked = true
        }else if(paper.sg2_spDrinkSum=="1"){
            drinking_0_true.isChecked = true
        }

        if(paper.sg2_spDrink1 == "1"){
            drinking_1_1.isChecked = true
        }else if(paper.sg2_spDrink1 == "2"){
            drinking_1_2.isChecked = true
        }else if(paper.sg2_spDrink1 == "3"){
            drinking_1_3.isChecked = true
        }else if(paper.sg2_spDrink1 == "4"){
            drinking_1_4.isChecked = true
        }


        if(paper.sg2_spDrink2_1 == "1"){
            drinking_2_1_1.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "2"){
            drinking_2_1_2.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "3"){
            drinking_2_1_3.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "4"){
            drinking_2_1_4.isChecked = true
        }else if(paper.sg2_spDrink2_1 == "5"){
            drinking_2_1_5.isChecked = true
        }


        if(paper.sg2_spDrink2_2 == "1"){
            drinking_2_2_1.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "2"){
            drinking_2_2_2.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "3"){
            drinking_2_2_3.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "4"){
            drinking_2_2_4.isChecked = true
        }else if(paper.sg2_spDrink2_2 == "5"){
            drinking_2_2_5.isChecked = true
        }


        if(paper.sg2_spDrink3 == "1"){
            drinking_3_1.isChecked = true
        }else if(paper.sg2_spDrink3 == "2"){
            drinking_3_2.isChecked = true
        }else if(paper.sg2_spDrink3 == "3"){
            drinking_3_3.isChecked = true
        }else if(paper.sg2_spDrink3 == "4"){
            drinking_3_4.isChecked = true
        }else if(paper.sg2_spDrink3 == "5"){
            drinking_3_5.isChecked = true
        }


        if(paper.sg2_spDrink4 == "1"){
            drinking_4_1.isChecked = true
        }else if(paper.sg2_spDrink4 == "2"){
            drinking_4_2.isChecked = true
        }else if(paper.sg2_spDrink4 == "3"){
            drinking_4_3.isChecked = true
        }else if(paper.sg2_spDrink4 == "4"){
            drinking_4_4.isChecked = true
        }else if(paper.sg2_spDrink4 == "5"){
            drinking_4_5.isChecked = true
        }


        if(paper.sg2_spDrink5 == "1"){
            drinking_5_1.isChecked = true
        }else if(paper.sg2_spDrink5 == "2"){
            drinking_5_2.isChecked = true
        }else if(paper.sg2_spDrink5 == "3"){
            drinking_5_3.isChecked = true
        }else if(paper.sg2_spDrink5 == "4"){
            drinking_5_4.isChecked = true
        }else if(paper.sg2_spDrink5 == "5"){
            drinking_5_5.isChecked = true
        }


        if(paper.sg2_spDrink6 == "1"){
            drinking_6_1.isChecked = true
        }else if(paper.sg2_spDrink6 == "2"){
            drinking_6_2.isChecked = true
        }else if(paper.sg2_spDrink6 == "3"){
            drinking_6_3.isChecked = true
        }else if(paper.sg2_spDrink6 == "4"){
            drinking_6_4.isChecked = true
        }else if(paper.sg2_spDrink6 == "5"){
            drinking_6_5.isChecked = true
        }


        if(paper.sg2_spDrink7 == "1"){
            drinking_7_1.isChecked = true
        }else if(paper.sg2_spDrink7 == "2"){
            drinking_7_2.isChecked = true
        }else if(paper.sg2_spDrink7 == "3"){
            drinking_7_3.isChecked = true
        }else if(paper.sg2_spDrink7 == "4"){
            drinking_7_4.isChecked = true
        }else if(paper.sg2_spDrink7 == "5"){
            drinking_7_5.isChecked = true
        }

        if(paper.sg2_spDrink8 == "1"){
            drinking_8_1.isChecked = true
        }else if(paper.sg2_spDrink8 == "2"){
            drinking_8_2.isChecked = true
        }else if(paper.sg2_spDrink8 == "3"){
            drinking_8_3.isChecked = true
        }else if(paper.sg2_spDrink8 == "4"){
            drinking_8_4.isChecked = true
        }else if(paper.sg2_spDrink8 == "5"){
            drinking_8_5.isChecked = true
        }


        if(paper.sg2_spDrink9 == "1"){
            drinking_9_1.isChecked = true
        }else if(paper.sg2_spDrink9 == "2"){
            drinking_9_2.isChecked = true
        }else if(paper.sg2_spDrink9 == "3"){
            drinking_9_3.isChecked = true
        }


        if(paper.sg2_spDrink10 == "1"){
            drinking_10_1.isChecked = true
        }else if(paper.sg2_spDrink10 == "2"){
            drinking_10_2.isChecked = true
        }else if(paper.sg2_spDrink10 == "3"){
            drinking_10_3.isChecked = true
        }

    }

}