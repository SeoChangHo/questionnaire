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
import com.fineinsight.zzango.questionnaire.LocalList.Paper_ELDERLY
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_elderly_exam.*
import kotlinx.android.synthetic.main.progressbar2.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NewApi")
class ElderlyExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    lateinit var signature:ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elderly_exam)

//        controlProgress(this, questionnaire_progress_wrapper, progress_constraintLayout, questionnaire_progress, progress_guideline, progress_guideline2, progress_guideline3, progress_guideline4, progress_guideline5, progress_guideline6, progress_guideline7, progress_guideline8)

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        elderly_examination_save.setOnClickListener {

            if(check()){

//                login_appbar_loading_progress.visibility = View.VISIBLE
//                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                if(MainActivity.chart.isEmpty()){
                    if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local")){
                        ChartDivision.ChartDivision.local_each_insert(this, 3)
                    }else{
                        ChartDivision.ChartDivision.server_insert(this)
                    }
                }else{
                    ChartDivision.ChartDivision.chart_array_insert(this, 3, getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local"))
                }
            }

        }

        elderly_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        elderly_edit_submit.setOnClickListener {

            finish()

        }

        if(SavePaper.Total.temp_Elderly != null){
            whenTempLoad(SavePaper.Total.temp_Elderly!!)
        }else if(SavedListObject.SavedList.savedDataClass.elderlySaved){
            whenTempLoad(SavePaper.Total.Array[4] as Paper_ELDERLY)
        }


        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_ELDERLY) {

                var paper = intent.getSerializableExtra("paper") as Paper_ELDERLY

                GetPaper(paper)

                try {
//                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)
//
//                    Signature.setImageBitmap(bmp)

                } catch (e: Exception) {
                    println(e.message)
                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Elderly

                GetPaper(paper)

            }

        }else{
            name_edit.text = MainActivity.login_user_name
            first_serial.text = MainActivity.user_first_serial
            last_serial.text = MainActivity.user_last_serial


            if(MainActivity.chart.isEmpty()){
                elderly_examination_save.text = "저장"
            }else{
                if(ChartDivision.ChartDivision.next_or_save(3)){
                    elderly_examination_save.text = "다음"
                }else{
                    elderly_examination_save.text = "저장"
                }
            }

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        var category = PaperNameInfo.PC.ELDERLY.EN_NM
        var mj66_1 = ""
        var mj66_2 = ""
        var mj66_3_1 = ""
        var mj66_3_2 = ""
        var mj66_3_3 = ""
        var mj66_3_4 = ""
        var mj66_3_5 = ""
        var mj66_3_6 = ""
        var mj66_4 = ""
        var mj66_5 = ""


        if(!name_edit.text.isNullOrEmpty()){
            name = name_edit.text.toString()
        }

        if(!first_serial.text.isNullOrEmpty()){
            first_serial_text = first_serial.text.toString()
        }

        if(!last_serial.text.isNullOrEmpty()){
            last_serial_text = last_serial.text.toString()
        }

        mj66_1 = when {
            elderly_1_true.isChecked -> "1"
            elderly_1_false.isChecked -> "2"
            else -> ""
        }

        mj66_2 = when {
            elderly_2_true.isChecked -> "1"
            elderly_2_false.isChecked -> "2"
            else -> ""
        }

        mj66_3_1 = when {
            elderly_3_1_true.isChecked -> "1"
            elderly_3_1_false.isChecked -> "2"
            else -> ""
        }

        mj66_3_2 = when {
            elderly_3_2_true.isChecked -> "1"
            elderly_3_2_false.isChecked -> "2"
            else -> ""
        }

        mj66_3_3 = when {
            elderly_3_3_true.isChecked -> "1"
            elderly_3_3_false.isChecked -> "2"
            else -> ""
        }

        mj66_3_4 = when {
            elderly_3_4_true.isChecked -> "1"
            elderly_3_4_false.isChecked -> "2"
            else -> ""
        }

        mj66_3_5 = when {
            elderly_3_5_true.isChecked -> "1"
            elderly_3_5_false.isChecked -> "2"
            else -> ""
        }

        mj66_3_6 = when {
            elderly_3_6_true.isChecked -> "1"
            elderly_3_6_false.isChecked -> "2"
            else -> ""
        }

        mj66_4 = when {
            elderly_4_true.isChecked -> "1"
            elderly_4_false.isChecked -> "2"
            else -> ""
        }

        mj66_5 = when {
            elderly_5_true.isChecked -> "1"
            elderly_5_false.isChecked -> "2"
            else -> ""
        }

        SavePaper.Total.temp_Elderly = Paper_ELDERLY(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                mj66_1, mj66_2, mj66_3_1, mj66_3_2, mj66_3_3, mj66_3_4, mj66_3_5, mj66_3_6, mj66_4, mj66_5)

    }

    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.ELDERLY.EN_NM
        var mj66_1 = ""
        var mj66_2 = ""
        var mj66_3_1 = ""
        var mj66_3_2 = ""
        var mj66_3_3 = ""
        var mj66_3_4 = ""
        var mj66_3_5 = ""
        var mj66_3_6 = ""
        var mj66_4 = ""
        var mj66_5 = ""


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

        if(elderly_1_true.isChecked){
            mj66_1 = "1"
        }else if(elderly_1_false.isChecked){
            mj66_1 = "2"
        }else{
            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_2_true.isChecked){
            mj66_2 = "1"
        }else if(elderly_2_false.isChecked){
            mj66_2 = "2"
        }else{
            Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_3_1_true.isChecked){
            mj66_3_1 = "1"
        }else if(elderly_3_1_false.isChecked){
            mj66_3_1 = "2"
        }else{
            Toast.makeText(this, "3-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_3_2_true.isChecked){
            mj66_3_2 = "1"
        }else if(elderly_3_2_false.isChecked){
            mj66_3_2 = "2"
        }else{
            Toast.makeText(this, "3-2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_3_3_true.isChecked){
            mj66_3_3 = "1"
        }else if(elderly_3_3_false.isChecked){
            mj66_3_3 = "2"
        }else{
            Toast.makeText(this, "3-3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_3_4_true.isChecked){
            mj66_3_4 = "1"
        }else if(elderly_3_4_false.isChecked){
            mj66_3_4 = "2"
        }else{
            Toast.makeText(this, "3-4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_3_5_true.isChecked){
            mj66_3_5 = "1"
        }else if(elderly_3_5_false.isChecked){
            mj66_3_5 = "2"
        }else{
            Toast.makeText(this, "3-5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_3_6_true.isChecked){
            mj66_3_6 = "1"
        }else if(elderly_3_6_false.isChecked){
            mj66_3_6 = "2"
        }else{
            Toast.makeText(this, "3-6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_4_true.isChecked){
            mj66_4 = "1"
        }else if(elderly_4_false.isChecked){
            mj66_4 = "2"
        }else{
            Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(elderly_5_true.isChecked){
            mj66_5 = "1"
        }else if(elderly_5_false.isChecked){
            mj66_5 = "2"
        }else{
            Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        SavePaper.Total.Array[4] = Paper_ELDERLY(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                mj66_1, mj66_2, mj66_3_1, mj66_3_2, mj66_3_3, mj66_3_4, mj66_3_5, mj66_3_6, mj66_4, mj66_5)

        if(ChartDivision.ChartDivision.next_or_save(3)) {
            SavedListObject.SavedList.savedDataClass.elderlySaved = true
        }

        SavePaper.Total.temp_Elderly = null

        return true

    }

    fun whenTempLoad(paper:Paper_ELDERLY) {

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial


        if(paper.mj66_1 == "1"){
            elderly_1_true.isChecked = true
        }else if(paper.mj66_1 == "2"){
            elderly_1_false.isChecked = true
        }


        if(paper.mj66_2 == "1"){
            elderly_2_true.isChecked = true
        }else if(paper.mj66_2 == "2"){
            elderly_2_false.isChecked = true
        }


        if(paper.mj66_3_1 == "1"){
            elderly_3_1_true.isChecked = true
        }else if(paper.mj66_3_1 == "2"){
            elderly_3_1_false.isChecked = true
        }


        if(paper.mj66_3_2 == "1"){
            elderly_3_2_true.isChecked = true
        }else if(paper.mj66_3_2 == "2"){
            elderly_3_2_false.isChecked = true
        }


        if(paper.mj66_3_3 == "1"){
            elderly_3_3_true.isChecked = true
        }else if(paper.mj66_3_3 == "2"){
            elderly_3_3_false.isChecked = true
        }


        if(paper.mj66_3_4 == "1"){
            elderly_3_4_true.isChecked = true
        }else if(paper.mj66_3_4 == "2"){
            elderly_3_4_false.isChecked = true
        }


        if(paper.mj66_3_5 == "1"){
            elderly_3_5_true.isChecked = true
        }else if(paper.mj66_3_5 == "2"){
            elderly_3_5_false.isChecked = true
        }


        if(paper.mj66_3_6 == "1"){
            elderly_3_6_true.isChecked = true
        }else if(paper.mj66_3_6 == "2"){
            elderly_3_6_false.isChecked = true
        }


        if(paper.mj66_4 == "1"){
            elderly_4_true.isChecked = true
        }else if(paper.mj66_4 == "2"){
            elderly_4_false.isChecked = true
        }


        if(paper.mj66_5 == "1"){
            elderly_5_true.isChecked = true
        }else if(paper.mj66_5 == "2"){
            elderly_5_false.isChecked = true
        }

    }

    fun GetPaper(paper:Paper_ELDERLY) {

        state = "getPaper"

        cannotEditQuestionnaire(elderly_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        elderly_examination_save.visibility = View.GONE
        elderly_examination_cancel.visibility = View.GONE
        elderly_edit_submit.visibility = View.VISIBLE



        if(paper.mj66_1 == "1"){
            elderly_1_true.isChecked = true
        }else if(paper.mj66_1 == "2"){
            elderly_1_false.isChecked = true
        }


        if(paper.mj66_2 == "1"){
            elderly_2_true.isChecked = true
        }else if(paper.mj66_2 == "2"){
            elderly_2_false.isChecked = true
        }


        if(paper.mj66_3_1 == "1"){
            elderly_3_1_true.isChecked = true
        }else if(paper.mj66_3_1 == "2"){
            elderly_3_1_false.isChecked = true
        }


        if(paper.mj66_3_2 == "1"){
            elderly_3_2_true.isChecked = true
        }else if(paper.mj66_3_2 == "2"){
            elderly_3_2_false.isChecked = true
        }


        if(paper.mj66_3_3 == "1"){
            elderly_3_3_true.isChecked = true
        }else if(paper.mj66_3_3 == "2"){
            elderly_3_3_false.isChecked = true
        }


        if(paper.mj66_3_4 == "1"){
            elderly_3_4_true.isChecked = true
        }else if(paper.mj66_3_4 == "2"){
            elderly_3_4_false.isChecked = true
        }


        if(paper.mj66_3_5 == "1"){
            elderly_3_5_true.isChecked = true
        }else if(paper.mj66_3_5 == "2"){
            elderly_3_5_false.isChecked = true
        }


        if(paper.mj66_3_6 == "1"){
            elderly_3_6_true.isChecked = true
        }else if(paper.mj66_3_6 == "2"){
            elderly_3_6_false.isChecked = true
        }


        if(paper.mj66_4 == "1"){
            elderly_4_true.isChecked = true
        }else if(paper.mj66_4 == "2"){
            elderly_4_false.isChecked = true
        }


        if(paper.mj66_5 == "1"){
            elderly_5_true.isChecked = true
        }else if(paper.mj66_5 == "2"){
            elderly_5_false.isChecked = true
        }

    }

    fun GetPaper(paper:ServerPaper_Elderly) {

        state = "getPaper"

        cannotEditQuestionnaire(elderly_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.mj_name
        first_serial.text = paper.mj_jumin.substring(0, 6)
        last_serial.text = paper.mj_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        println(paper)

        elderly_examination_save.visibility = View.GONE
        elderly_examination_cancel.visibility = View.GONE
        elderly_edit_submit.visibility = View.VISIBLE



        if(paper.mj66_1 == "1"){
            elderly_1_true.isChecked = true
        }else if(paper.mj66_1 == "2"){
            elderly_1_false.isChecked = true
        }


        if(paper.mj66_2 == "1"){
            elderly_2_true.isChecked = true
        }else if(paper.mj66_2 == "2"){
            elderly_2_false.isChecked = true
        }


        if(paper.mj66_3_1 == "1"){
            elderly_3_1_true.isChecked = true
        }else if(paper.mj66_3_1 == "2"){
            elderly_3_1_false.isChecked = true
        }


        if(paper.mj66_3_2 == "1"){
            elderly_3_2_true.isChecked = true
        }else if(paper.mj66_3_2 == "2"){
            elderly_3_2_false.isChecked = true
        }


        if(paper.mj66_3_3 == "1"){
            elderly_3_3_true.isChecked = true
        }else if(paper.mj66_3_3 == "2"){
            elderly_3_3_false.isChecked = true
        }


        if(paper.mj66_3_4 == "1"){
            elderly_3_4_true.isChecked = true
        }else if(paper.mj66_3_4 == "2"){
            elderly_3_4_false.isChecked = true
        }


        if(paper.mj66_3_5 == "1"){
            elderly_3_5_true.isChecked = true
        }else if(paper.mj66_3_5 == "2"){
            elderly_3_5_false.isChecked = true
        }


        if(paper.mj66_3_6 == "1"){
            elderly_3_6_true.isChecked = true
        }else if(paper.mj66_3_6 == "2"){
            elderly_3_6_false.isChecked = true
        }


        if(paper.mj66_4 == "1"){
            elderly_4_true.isChecked = true
        }else if(paper.mj66_4 == "2"){
            elderly_4_false.isChecked = true
        }


        if(paper.mj66_5 == "1"){
            elderly_5_true.isChecked = true
        }else if(paper.mj66_5 == "2"){
            elderly_5_false.isChecked = true
        }

    }

}