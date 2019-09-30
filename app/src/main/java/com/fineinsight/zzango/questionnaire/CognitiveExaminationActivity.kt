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
import com.fineinsight.zzango.questionnaire.LocalList.Paper_COGNITIVE
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_cognitive_exam.*
import kotlinx.android.synthetic.main.progressbar2.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NewApi")
class CognitiveExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    lateinit var signature:ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cognitive_exam)

        //서명정보 가져오는거
        if(!Examinee.USER.info.SIGN.contentEquals(Examinee.USER.EMPTY_BYTE_ARRAY))
        {
            signature = Examinee.USER.info.SIGN
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(Examinee.USER.info.SIGN))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        cognitive_examination_save.setOnClickListener {

            if(check()){
                if(MainActivity.chart.isEmpty()){
                    if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local")){
                        ChartDivision.ChartDivision.local_each_insert(this, 2)
                    }else{
                        ChartDivision.ChartDivision.server_insert(this)
                    }
                }else{
                    ChartDivision.ChartDivision.chart_array_insert(this, 2, getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local"))
                }


            }

        }

        cognitive_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        cognitive_edit_submit.setOnClickListener {

            finish()

        }

        if(SavePaper.Total.temp_Cognitive != null){
            whenTempLoad(SavePaper.Total.temp_Cognitive!!)
        }else if(SavedListObject.SavedList.savedDataClass.cognitiveSaved){
            whenTempLoad(SavePaper.Total.Array[3] as Paper_COGNITIVE)
        }


        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_COGNITIVE) {

                var paper = intent.getSerializableExtra("paper") as Paper_COGNITIVE

                GetPaper(paper)

                try {
//                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)
//
//                    Signature.setImageBitmap(bmp)

                } catch (e: Exception) {
                    println(e.message)
                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Cognitive

                GetPaper(paper)

            }

        }else{
            name_edit.text = Examinee.USER.info.NAME
            first_serial.text = Examinee.USER.info.JUMIN1
            last_serial.text = Examinee.USER.info.JUMIN2


            if(MainActivity.chart.isEmpty()){
                cognitive_examination_save.text = "저장"
            }else{
                if(ChartDivision.ChartDivision.next_or_save(2)){
                    cognitive_examination_save.text = "다음"
                }else{
                    cognitive_examination_save.text = "저장"
                }
            }

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

            super.onBackPressed()

        }

    }

    fun whenTempSave() {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.COGNITIVE.EN_NM
        var mj_inji_1 = ""
        var mj_inji_2 = ""
        var mj_inji_3 = ""
        var mj_inji_4 = ""
        var mj_inji_5 = ""
        var mj_inji_6 = ""
        var mj_inji_7 = ""
        var mj_inji_8 = ""
        var mj_inji_9 = ""
        var mj_inji_10 = ""
        var mj_inji_11 = ""
        var mj_inji_12 = ""
        var mj_inji_13 = ""
        var mj_inji_14 = ""
        var mj_inji_15 = ""
        var mj_inji_sum = ""


        if(!name_edit.text.isNullOrEmpty()){
            name = name_edit.text.toString()
        }

        if(!first_serial.text.isNullOrEmpty()){
            first_serial_text = first_serial.text.toString()
        }

        if(!last_serial.text.isNullOrEmpty()){
            last_serial_text = last_serial.text.toString()
        }

        mj_inji_1 = when {
            cognitive_1_1.isChecked -> "0"
            cognitive_1_2.isChecked -> "1"
            cognitive_1_3.isChecked -> "2"
            else -> ""
        }


        mj_inji_2 = when {
            cognitive_2_1.isChecked -> "0"
            cognitive_2_2.isChecked -> "1"
            cognitive_2_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_3 = when {
            cognitive_3_1.isChecked -> "0"
            cognitive_3_2.isChecked -> "1"
            cognitive_3_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_4 = when {
            cognitive_4_1.isChecked -> "0"
            cognitive_4_2.isChecked -> "1"
            cognitive_4_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_5 = when {
            cognitive_5_1.isChecked -> "0"
            cognitive_5_2.isChecked -> "1"
            cognitive_5_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_6 = when {
            cognitive_6_1.isChecked -> "0"
            cognitive_6_2.isChecked -> "1"
            cognitive_6_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_7 = when {
            cognitive_7_1.isChecked -> "0"
            cognitive_7_2.isChecked -> "1"
            cognitive_7_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_8 = when {
            cognitive_8_1.isChecked -> "0"
            cognitive_8_2.isChecked -> "1"
            cognitive_8_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_9 = when {
            cognitive_9_1.isChecked -> "0"
            cognitive_9_2.isChecked -> "1"
            cognitive_9_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_10 = when {
            cognitive_10_1.isChecked -> "0"
            cognitive_10_2.isChecked -> "1"
            cognitive_10_3.isChecked -> "2"
            else -> ""
        }


        mj_inji_11 = when {
            cognitive_11_1.isChecked -> "0"
            cognitive_11_2.isChecked -> "1"
            cognitive_11_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_12 = when {
            cognitive_12_1.isChecked -> "0"
            cognitive_12_2.isChecked -> "1"
            cognitive_12_3.isChecked -> "2"
            else -> ""
        }


        mj_inji_13 = when {
            cognitive_13_1.isChecked -> "0"
            cognitive_13_2.isChecked -> "1"
            cognitive_13_3.isChecked -> "2"
            else -> ""
        }

        mj_inji_14 = when {
            cognitive_14_1.isChecked -> "0"
            cognitive_14_2.isChecked -> "1"
            cognitive_14_3.isChecked -> "2"
            else -> ""
        }


        mj_inji_15 = when {
            cognitive_15_1.isChecked -> "0"
            cognitive_15_2.isChecked -> "1"
            cognitive_15_3.isChecked -> "2"
            else -> ""
        }

//        mj_inji_sum = (mj_inji_1.toInt() + mj_inji_2.toInt() + + mj_inji_3.toInt() + + mj_inji_4.toInt() + mj_inji_5.toInt()+ mj_inji_6.toInt()+ mj_inji_7.toInt()+ mj_inji_8.toInt()+ mj_inji_9.toInt()+ mj_inji_10.toInt() + mj_inji_11.toInt()+ mj_inji_12.toInt()+ mj_inji_13.toInt()+ mj_inji_14.toInt()+ mj_inji_15.toInt()).toString()
        mj_inji_sum = "0"

        SavePaper.Total.temp_Cognitive = Paper_COGNITIVE(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                mj_inji_1, mj_inji_2, mj_inji_3, mj_inji_4, mj_inji_5, mj_inji_6, mj_inji_7, mj_inji_8, mj_inji_9,
                mj_inji_10, mj_inji_11, mj_inji_12, mj_inji_13, mj_inji_14, mj_inji_15, mj_inji_sum)

    }

    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.COGNITIVE.EN_NM
        var mj_inji_1 = ""
        var mj_inji_2 = ""
        var mj_inji_3 = ""
        var mj_inji_4 = ""
        var mj_inji_5 = ""
        var mj_inji_6 = ""
        var mj_inji_7 = ""
        var mj_inji_8 = ""
        var mj_inji_9 = ""
        var mj_inji_10 = ""
        var mj_inji_11 = ""
        var mj_inji_12 = ""
        var mj_inji_13 = ""
        var mj_inji_14 = ""
        var mj_inji_15 = ""
        var mj_inji_sum = ""


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

        if(cognitive_1_1.isChecked){
            mj_inji_1 = "0"
        }else if(cognitive_1_2.isChecked){
            mj_inji_1 = "1"
        }else if(cognitive_1_3.isChecked){
            mj_inji_1 = "2"
        }else{
            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cognitive_2_1.isChecked){
            mj_inji_2 = "0"
        }else if(cognitive_2_2.isChecked){
            mj_inji_2 = "1"
        }else if(cognitive_2_3.isChecked){
            mj_inji_2 = "2"
        }else{
            Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_3_1.isChecked){
            mj_inji_3 = "0"
        }else if(cognitive_3_2.isChecked){
            mj_inji_3 = "1"
        }else if(cognitive_3_3.isChecked){
            mj_inji_3 = "2"
        }else{
            Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_4_1.isChecked){
            mj_inji_4 = "0"
        }else if(cognitive_4_2.isChecked){
            mj_inji_4 = "1"
        }else if(cognitive_4_3.isChecked){
            mj_inji_4 = "2"
        }else{
            Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_5_1.isChecked){
            mj_inji_5 = "0"
        }else if(cognitive_5_2.isChecked){
            mj_inji_5 = "1"
        }else if(cognitive_5_3.isChecked){
            mj_inji_5 = "2"
        }else{
            Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_6_1.isChecked){
            mj_inji_6 = "0"
        }else if(cognitive_6_2.isChecked){
            mj_inji_6 = "1"
        }else if(cognitive_6_3.isChecked){
            mj_inji_6 = "2"
        }else{
            Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_7_1.isChecked){
            mj_inji_7 = "0"
        }else if(cognitive_7_2.isChecked){
            mj_inji_7 = "1"
        }else if(cognitive_7_3.isChecked){
            mj_inji_7 = "2"
        }else{
            Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_8_1.isChecked){
            mj_inji_8 = "0"
        }else if(cognitive_8_2.isChecked){
            mj_inji_8 = "1"
        }else if(cognitive_8_3.isChecked){
            mj_inji_8 = "2"
        }else{
            Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_9_1.isChecked){
            mj_inji_9 = "0"
        }else if(cognitive_9_2.isChecked){
            mj_inji_9 = "1"
        }else if(cognitive_9_3.isChecked){
            mj_inji_9 = "2"
        }else{
            Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_10_1.isChecked){
            mj_inji_10 = "0"
        }else if(cognitive_10_2.isChecked){
            mj_inji_10 = "1"
        }else if(cognitive_10_3.isChecked){
            mj_inji_10 = "2"
        }else{
            Toast.makeText(this, "10번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cognitive_11_1.isChecked){
            mj_inji_11 = "0"
        }else if(cognitive_11_2.isChecked){
            mj_inji_11 = "1"
        }else if(cognitive_11_3.isChecked){
            mj_inji_11 = "2"
        }else{
            Toast.makeText(this, "11번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_12_1.isChecked){
            mj_inji_12 = "0"
        }else if(cognitive_12_2.isChecked){
            mj_inji_12 = "1"
        }else if(cognitive_12_3.isChecked){
            mj_inji_12 = "2"
        }else{
            Toast.makeText(this, "12번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cognitive_13_1.isChecked){
            mj_inji_13 = "0"
        }else if(cognitive_13_2.isChecked){
            mj_inji_13 = "1"
        }else if(cognitive_13_3.isChecked){
            mj_inji_13 = "2"
        }else{
            Toast.makeText(this, "13번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(cognitive_14_1.isChecked){
            mj_inji_14 = "0"
        }else if(cognitive_14_2.isChecked){
            mj_inji_14 = "1"
        }else if(cognitive_14_3.isChecked){
            mj_inji_14 = "2"
        }else{
            Toast.makeText(this, "14번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(cognitive_15_1.isChecked){
            mj_inji_15 = "0"
        }else if(cognitive_15_2.isChecked){
            mj_inji_15 = "1"
        }else if(cognitive_15_3.isChecked){
            mj_inji_15 = "2"
        }else{
            Toast.makeText(this, "15번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        mj_inji_sum = (mj_inji_1.toInt() + mj_inji_2.toInt() + + mj_inji_3.toInt() + + mj_inji_4.toInt() + mj_inji_5.toInt()+ mj_inji_6.toInt()+ mj_inji_7.toInt()+ mj_inji_8.toInt()+ mj_inji_9.toInt()+ mj_inji_10.toInt() + mj_inji_11.toInt()+ mj_inji_12.toInt()+ mj_inji_13.toInt()+ mj_inji_14.toInt()+ mj_inji_15.toInt()).toString()

        SavePaper.Total.Array[3] = Paper_COGNITIVE(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                mj_inji_1, mj_inji_2, mj_inji_3, mj_inji_4, mj_inji_5, mj_inji_6, mj_inji_7, mj_inji_8, mj_inji_9,
                mj_inji_10, mj_inji_11, mj_inji_12, mj_inji_13, mj_inji_14, mj_inji_15, mj_inji_sum)

        if(ChartDivision.ChartDivision.next_or_save(2)) {
            SavedListObject.SavedList.savedDataClass.cognitiveSaved = true
        }

        SavePaper.Total.temp_Cognitive = null

        ChartDivision.ChartDivision.ProgressAction(true, this)

        return true

    }

    fun whenTempLoad(paper: Paper_COGNITIVE) {

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        if(paper.mj_inji_1 == "0"){
            cognitive_1_1.isChecked = true
        }else if(paper.mj_inji_1 == "1"){
            cognitive_1_2.isChecked = true
        }else if(paper.mj_inji_1 == "2"){
            cognitive_1_3.isChecked = true
        }


        if(paper.mj_inji_2 == "0"){
            cognitive_2_1.isChecked = true
        }else if(paper.mj_inji_2 == "1"){
            cognitive_2_2.isChecked = true
        }else if(paper.mj_inji_2 == "2"){
            cognitive_2_3.isChecked = true
        }


        if(paper.mj_inji_3 == "0"){
            cognitive_3_1.isChecked = true
        }else if(paper.mj_inji_3 == "1"){
            cognitive_3_2.isChecked = true
        }else if(paper.mj_inji_3 == "2"){
            cognitive_3_3.isChecked = true
        }


        if(paper.mj_inji_4 == "0"){
            cognitive_4_1.isChecked = true
        }else if(paper.mj_inji_4 == "1"){
            cognitive_4_2.isChecked = true
        }else if(paper.mj_inji_4 == "2"){
            cognitive_4_3.isChecked = true
        }


        if(paper.mj_inji_5 == "0"){
            cognitive_5_1.isChecked = true
        }else if(paper.mj_inji_5 == "1"){
            cognitive_5_2.isChecked = true
        }else if(paper.mj_inji_5 == "2"){
            cognitive_5_3.isChecked = true
        }


        if(paper.mj_inji_6 == "0"){
            cognitive_6_1.isChecked = true
        }else if(paper.mj_inji_6 == "1"){
            cognitive_6_2.isChecked = true
        }else if(paper.mj_inji_6 == "2"){
            cognitive_6_3.isChecked = true
        }

        if(paper.mj_inji_7 == "0"){
            cognitive_7_1.isChecked = true
        }else if(paper.mj_inji_7 == "1"){
            cognitive_7_2.isChecked = true
        }else if(paper.mj_inji_7 == "2"){
            cognitive_7_3.isChecked = true
        }


        if(paper.mj_inji_8 == "0"){
            cognitive_8_1.isChecked = true
        }else if(paper.mj_inji_8 == "1"){
            cognitive_8_2.isChecked = true
        }else if(paper.mj_inji_8 == "2"){
            cognitive_8_3.isChecked = true
        }


        if(paper.mj_inji_9 == "0"){
            cognitive_9_1.isChecked = true
        }else if(paper.mj_inji_9 == "1"){
            cognitive_9_2.isChecked = true
        }else if(paper.mj_inji_9 == "2"){
            cognitive_9_3.isChecked = true
        }


        if(paper.mj_inji_10 == "0"){
            cognitive_10_1.isChecked = true
        }else if(paper.mj_inji_10 == "1"){
            cognitive_10_2.isChecked = true
        }else if(paper.mj_inji_10 == "2"){
            cognitive_10_3.isChecked = true
        }


        if(paper.mj_inji_11 == "0"){
            cognitive_11_1.isChecked = true
        }else if(paper.mj_inji_11 == "1"){
            cognitive_11_2.isChecked = true
        }else if(paper.mj_inji_11 == "2"){
            cognitive_11_3.isChecked = true
        }


        if(paper.mj_inji_12 == "0"){
            cognitive_12_1.isChecked = true
        }else if(paper.mj_inji_12 == "1"){
            cognitive_12_2.isChecked = true
        }else if(paper.mj_inji_12 == "2"){
            cognitive_12_3.isChecked = true
        }


        if(paper.mj_inji_13 == "0"){
            cognitive_13_1.isChecked = true
        }else if(paper.mj_inji_13 == "1"){
            cognitive_13_2.isChecked = true
        }else if(paper.mj_inji_13 == "2"){
            cognitive_13_3.isChecked = true
        }


        if(paper.mj_inji_14 == "0"){
            cognitive_14_1.isChecked = true
        }else if(paper.mj_inji_14 == "1"){
            cognitive_14_2.isChecked = true
        }else if(paper.mj_inji_14 == "2"){
            cognitive_14_3.isChecked = true
        }


        if(paper.mj_inji_15 == "0"){
            cognitive_15_1.isChecked = true
        }else if(paper.mj_inji_15 == "1"){
            cognitive_15_2.isChecked = true
        }else if(paper.mj_inji_15 == "2"){
            cognitive_15_3.isChecked = true
        }


    }

    fun GetPaper(paper: Paper_COGNITIVE) {

        state = "getPaper"

        cannotEditQuestionnaire(cognitive_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        cognitive_examination_save.visibility = View.GONE
        cognitive_examination_cancel.visibility = View.GONE
        cognitive_edit_submit.visibility = View.VISIBLE


        if(paper.mj_inji_1 == "0"){
            cognitive_1_1.isChecked = true
        }else if(paper.mj_inji_1 == "1"){
            cognitive_1_2.isChecked = true
        }else if(paper.mj_inji_1 == "2"){
            cognitive_1_3.isChecked = true
        }


        if(paper.mj_inji_2 == "0"){
            cognitive_2_1.isChecked = true
        }else if(paper.mj_inji_2 == "1"){
            cognitive_2_2.isChecked = true
        }else if(paper.mj_inji_2 == "2"){
            cognitive_2_3.isChecked = true
        }


        if(paper.mj_inji_3 == "0"){
            cognitive_3_1.isChecked = true
        }else if(paper.mj_inji_3 == "1"){
            cognitive_3_2.isChecked = true
        }else if(paper.mj_inji_3 == "2"){
            cognitive_3_3.isChecked = true
        }


        if(paper.mj_inji_4 == "0"){
            cognitive_4_1.isChecked = true
        }else if(paper.mj_inji_4 == "1"){
            cognitive_4_2.isChecked = true
        }else if(paper.mj_inji_4 == "2"){
            cognitive_4_3.isChecked = true
        }


        if(paper.mj_inji_5 == "0"){
            cognitive_5_1.isChecked = true
        }else if(paper.mj_inji_5 == "1"){
            cognitive_5_2.isChecked = true
        }else if(paper.mj_inji_5 == "2"){
            cognitive_5_3.isChecked = true
        }


        if(paper.mj_inji_6 == "0"){
            cognitive_6_1.isChecked = true
        }else if(paper.mj_inji_6 == "1"){
            cognitive_6_2.isChecked = true
        }else if(paper.mj_inji_6 == "2"){
            cognitive_6_3.isChecked = true
        }

        if(paper.mj_inji_7 == "0"){
            cognitive_7_1.isChecked = true
        }else if(paper.mj_inji_7 == "1"){
            cognitive_7_2.isChecked = true
        }else if(paper.mj_inji_7 == "2"){
            cognitive_7_3.isChecked = true
        }


        if(paper.mj_inji_8 == "0"){
            cognitive_8_1.isChecked = true
        }else if(paper.mj_inji_8 == "1"){
            cognitive_8_2.isChecked = true
        }else if(paper.mj_inji_8 == "2"){
            cognitive_8_3.isChecked = true
        }


        if(paper.mj_inji_9 == "0"){
            cognitive_9_1.isChecked = true
        }else if(paper.mj_inji_9 == "1"){
            cognitive_9_2.isChecked = true
        }else if(paper.mj_inji_9 == "2"){
            cognitive_9_3.isChecked = true
        }


        if(paper.mj_inji_10 == "0"){
            cognitive_10_1.isChecked = true
        }else if(paper.mj_inji_10 == "1"){
            cognitive_10_2.isChecked = true
        }else if(paper.mj_inji_10 == "2"){
            cognitive_10_3.isChecked = true
        }


        if(paper.mj_inji_11 == "0"){
            cognitive_11_1.isChecked = true
        }else if(paper.mj_inji_11 == "1"){
            cognitive_11_2.isChecked = true
        }else if(paper.mj_inji_11 == "2"){
            cognitive_11_3.isChecked = true
        }


        if(paper.mj_inji_12 == "0"){
            cognitive_12_1.isChecked = true
        }else if(paper.mj_inji_12 == "1"){
            cognitive_12_2.isChecked = true
        }else if(paper.mj_inji_12 == "2"){
            cognitive_12_3.isChecked = true
        }


        if(paper.mj_inji_13 == "0"){
            cognitive_13_1.isChecked = true
        }else if(paper.mj_inji_13 == "1"){
            cognitive_13_2.isChecked = true
        }else if(paper.mj_inji_13 == "2"){
            cognitive_13_3.isChecked = true
        }


        if(paper.mj_inji_14 == "0"){
            cognitive_14_1.isChecked = true
        }else if(paper.mj_inji_14 == "1"){
            cognitive_14_2.isChecked = true
        }else if(paper.mj_inji_14 == "2"){
            cognitive_14_3.isChecked = true
        }


        if(paper.mj_inji_15 == "0"){
            cognitive_15_1.isChecked = true
        }else if(paper.mj_inji_15 == "1"){
            cognitive_15_2.isChecked = true
        }else if(paper.mj_inji_15 == "2"){
            cognitive_15_3.isChecked = true
        }


    }

    fun GetPaper(paper: ServerPaper_Cognitive) {

        state = "getPaper"

        cannotEditQuestionnaire(cognitive_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.mj_name
        first_serial.text = paper.mj_jumin.substring(0, 6)
        last_serial.text = paper.mj_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        println(paper)

        cognitive_examination_save.visibility = View.GONE
        cognitive_examination_cancel.visibility = View.GONE
        cognitive_edit_submit.visibility = View.VISIBLE


        if(paper.mj_inji_1 == "0"){
            cognitive_1_1.isChecked = true
        }else if(paper.mj_inji_1 == "1"){
            cognitive_1_2.isChecked = true
        }else if(paper.mj_inji_1 == "2"){
            cognitive_1_3.isChecked = true
        }


        if(paper.mj_inji_2 == "0"){
            cognitive_2_1.isChecked = true
        }else if(paper.mj_inji_2 == "1"){
            cognitive_2_2.isChecked = true
        }else if(paper.mj_inji_2 == "2"){
            cognitive_2_3.isChecked = true
        }


        if(paper.mj_inji_3 == "0"){
            cognitive_3_1.isChecked = true
        }else if(paper.mj_inji_3 == "1"){
            cognitive_3_2.isChecked = true
        }else if(paper.mj_inji_3 == "2"){
            cognitive_3_3.isChecked = true
        }


        if(paper.mj_inji_4 == "0"){
            cognitive_4_1.isChecked = true
        }else if(paper.mj_inji_4 == "1"){
            cognitive_4_2.isChecked = true
        }else if(paper.mj_inji_4 == "2"){
            cognitive_4_3.isChecked = true
        }


        if(paper.mj_inji_5 == "0"){
            cognitive_5_1.isChecked = true
        }else if(paper.mj_inji_5 == "1"){
            cognitive_5_2.isChecked = true
        }else if(paper.mj_inji_5 == "2"){
            cognitive_5_3.isChecked = true
        }


        if(paper.mj_inji_6 == "0"){
            cognitive_6_1.isChecked = true
        }else if(paper.mj_inji_6 == "1"){
            cognitive_6_2.isChecked = true
        }else if(paper.mj_inji_6 == "2"){
            cognitive_6_3.isChecked = true
        }

        if(paper.mj_inji_7 == "0"){
            cognitive_7_1.isChecked = true
        }else if(paper.mj_inji_7 == "1"){
            cognitive_7_2.isChecked = true
        }else if(paper.mj_inji_7 == "2"){
            cognitive_7_3.isChecked = true
        }


        if(paper.mj_inji_8 == "0"){
            cognitive_8_1.isChecked = true
        }else if(paper.mj_inji_8 == "1"){
            cognitive_8_2.isChecked = true
        }else if(paper.mj_inji_8 == "2"){
            cognitive_8_3.isChecked = true
        }


        if(paper.mj_inji_9 == "0"){
            cognitive_9_1.isChecked = true
        }else if(paper.mj_inji_9 == "1"){
            cognitive_9_2.isChecked = true
        }else if(paper.mj_inji_9 == "2"){
            cognitive_9_3.isChecked = true
        }


        if(paper.mj_inji_10 == "0"){
            cognitive_10_1.isChecked = true
        }else if(paper.mj_inji_10 == "1"){
            cognitive_10_2.isChecked = true
        }else if(paper.mj_inji_10 == "2"){
            cognitive_10_3.isChecked = true
        }


        if(paper.mj_inji_11 == "0"){
            cognitive_11_1.isChecked = true
        }else if(paper.mj_inji_11 == "1"){
            cognitive_11_2.isChecked = true
        }else if(paper.mj_inji_11 == "2"){
            cognitive_11_3.isChecked = true
        }


        if(paper.mj_inji_12 == "0"){
            cognitive_12_1.isChecked = true
        }else if(paper.mj_inji_12 == "1"){
            cognitive_12_2.isChecked = true
        }else if(paper.mj_inji_12 == "2"){
            cognitive_12_3.isChecked = true
        }


        if(paper.mj_inji_13 == "0"){
            cognitive_13_1.isChecked = true
        }else if(paper.mj_inji_13 == "1"){
            cognitive_13_2.isChecked = true
        }else if(paper.mj_inji_13 == "2"){
            cognitive_13_3.isChecked = true
        }


        if(paper.mj_inji_14 == "0"){
            cognitive_14_1.isChecked = true
        }else if(paper.mj_inji_14 == "1"){
            cognitive_14_2.isChecked = true
        }else if(paper.mj_inji_14 == "2"){
            cognitive_14_3.isChecked = true
        }


        if(paper.mj_inji_15 == "0"){
            cognitive_15_1.isChecked = true
        }else if(paper.mj_inji_15 == "1"){
            cognitive_15_2.isChecked = true
        }else if(paper.mj_inji_15 == "2"){
            cognitive_15_3.isChecked = true
        }

    }

}