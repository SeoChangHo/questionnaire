package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import com.fineinsight.zzango.questionnaire.LocalList.Paper_MENTAL
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import kotlinx.android.synthetic.main.activity_mental_exam.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NewApi")
class MentalExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mental_exam)

        controlProgress(this)

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        mental_examination_save.setOnClickListener {

            if(check()){

                if(MainActivity.chart.isEmpty()){
                    if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local")){
                        ChartDivision.ChartDivision.local_each_insert(this, 1)
                    }else{
                        ChartDivision.ChartDivision.server_insert(this)
                    }
                }else{
                    ChartDivision.ChartDivision.chart_array_insert(this, 1, getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local"))
                }
            }

        }

        mental_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        mental_edit_submit.setOnClickListener {

            finish()

        }

        if(SavePaper.Total.temp_Mental != null){
            whenTempLoad(SavePaper.Total.temp_Mental!!)
        }else if(SavedListObject.SavedList.savedDataClass.mentalSaved){
            whenTempLoad(SavePaper.Total.Array[2] as Paper_MENTAL)
        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_MENTAL) {

                var paper = intent.getSerializableExtra("paper") as Paper_MENTAL

                GetPaper(paper)

                try {
//                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)
//
//                    Signature.setImageBitmap(bmp)

                } catch (e: Exception) {
                    println(e.message)
                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Mental

                GetPaper(paper)

            }

        }else{
            name_edit.text = MainActivity.login_user_name
            first_serial.text = MainActivity.user_first_serial
            last_serial.text = MainActivity.user_last_serial


            if(MainActivity.chart.isEmpty()){
                mental_examination_save.text = "저장"
            }else{
                if(ChartDivision.ChartDivision.next_or_save(1)){
                    mental_examination_save.text = "다음"
                }else{
                    mental_examination_save.text = "저장"
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
        var category = PaperNameInfo.PC.MENTAL.EN_NM
        var mj_mtl_1 = ""
        var mj_mtl_2 = ""
        var mj_mtl_3 = ""
        var mj_mtl_4 = ""
        var mj_mtl_5 = ""
        var mj_mtl_6 = ""
        var mj_mtl_7 = ""
        var mj_mtl_8 = ""
        var mj_mtl_9 = ""
        var mj_mtl_sum = ""

        if(!name_edit.text.isNullOrEmpty()){
            name = name_edit.text.toString()
        }
        if(!first_serial.text.isNullOrEmpty()){
            first_serial_text = first_serial.text.toString()
        }

        if(!last_serial.text.isNullOrEmpty()){
            last_serial_text = last_serial.text.toString()
        }

        mj_mtl_1 = when {
            mental_1_1.isChecked -> "0"
            mental_1_2.isChecked -> "1"
            mental_1_3.isChecked -> "2"
            mental_1_4.isChecked -> "3"
            else -> ""
        }

        mj_mtl_2 = when {
            mental_2_1.isChecked -> "0"
            mental_2_2.isChecked -> "1"
            mental_2_3.isChecked -> "2"
            mental_2_4.isChecked -> "3"
            else -> ""
        }

        mj_mtl_3 = when {
            mental_3_1.isChecked -> "0"
            mental_3_2.isChecked -> "1"
            mental_3_3.isChecked -> "2"
            mental_3_4.isChecked -> "3"
            else -> ""
        }

        mj_mtl_4 = when {
            mental_4_1.isChecked -> "0"
            mental_4_2.isChecked -> "1"
            mental_4_3.isChecked -> "2"
            mental_4_4.isChecked -> "3"
            else -> ""
        }

        mj_mtl_5 = when {
            mental_5_1.isChecked -> "0"
            mental_5_2.isChecked -> "1"
            mental_5_3.isChecked -> "2"
            mental_5_4.isChecked -> "3"
            else -> ""
        }

        mj_mtl_6 = when {
            mental_6_1.isChecked -> "0"
            mental_6_2.isChecked -> "1"
            mental_6_3.isChecked -> "2"
            mental_6_4.isChecked -> "3"
            else -> ""
        }

        mj_mtl_7 = when {
            mental_7_1.isChecked -> "0"
            mental_7_2.isChecked -> "1"
            mental_7_3.isChecked -> "2"
            mental_7_4.isChecked -> "3"
            else -> ""
        }

        mj_mtl_8 = when {
            mental_8_1.isChecked -> "0"
            mental_8_2.isChecked -> "1"
            mental_8_3.isChecked -> "2"
            mental_8_4.isChecked -> "3"
            else -> ""
        }

        mj_mtl_9 = when {
            mental_9_1.isChecked -> "0"
            mental_9_2.isChecked -> "1"
            mental_9_3.isChecked -> "2"
            mental_9_4.isChecked -> "3"
            else -> ""
        }

        SavePaper.Total.temp_Mental = Paper_MENTAL(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                mj_mtl_1, mj_mtl_2, mj_mtl_3, mj_mtl_4, mj_mtl_5, mj_mtl_6, mj_mtl_7, mj_mtl_8, mj_mtl_9, mj_mtl_sum)

    }

    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.MENTAL.EN_NM
        var mj_mtl_1 = ""
        var mj_mtl_2 = ""
        var mj_mtl_3 = ""
        var mj_mtl_4 = ""
        var mj_mtl_5 = ""
        var mj_mtl_6 = ""
        var mj_mtl_7 = ""
        var mj_mtl_8 = ""
        var mj_mtl_9 = ""
        var mj_mtl_sum = ""

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

        if(mental_1_1.isChecked){
            mj_mtl_1 = "0"
        }else if(mental_1_2.isChecked){
            mj_mtl_1 = "1"
        }else if(mental_1_3.isChecked){
            mj_mtl_1 = "2"
        }else if(mental_1_4.isChecked){
            mj_mtl_1 = "3"
        }else{
            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_2_1.isChecked){
            mj_mtl_2 = "0"
        }else if(mental_2_2.isChecked){
            mj_mtl_2 = "1"
        }else if(mental_2_3.isChecked){
            mj_mtl_2 = "2"
        }else if(mental_2_4.isChecked){
            mj_mtl_2 = "3"
        }else{
            Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_3_1.isChecked){
            mj_mtl_3 = "0"
        }else if(mental_3_2.isChecked){
            mj_mtl_3 = "1"
        }else if(mental_3_3.isChecked){
            mj_mtl_3 = "2"
        }else if(mental_3_4.isChecked){
            mj_mtl_3 = "3"
        }else{
            Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_4_1.isChecked){
            mj_mtl_4 = "0"
        }else if(mental_4_2.isChecked){
            mj_mtl_4 = "1"
        }else if(mental_4_3.isChecked){
            mj_mtl_4 = "2"
        }else if(mental_4_4.isChecked){
            mj_mtl_4 = "3"
        }else{
            Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_5_1.isChecked){
            mj_mtl_5 = "0"
        }else if(mental_5_2.isChecked){
            mj_mtl_5 = "1"
        }else if(mental_5_3.isChecked){
            mj_mtl_5 = "2"
        }else if(mental_5_4.isChecked){
            mj_mtl_5 = "3"
        }else{
            Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_6_1.isChecked){
            mj_mtl_6 = "0"
        }else if(mental_6_2.isChecked){
            mj_mtl_6 = "1"
        }else if(mental_6_3.isChecked){
            mj_mtl_6 = "2"
        }else if(mental_6_4.isChecked){
            mj_mtl_6 = "3"
        }else{
            Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_7_1.isChecked){
            mj_mtl_7 = "0"
        }else if(mental_7_2.isChecked){
            mj_mtl_7 = "1"
        }else if(mental_7_3.isChecked){
            mj_mtl_7 = "2"
        }else if(mental_7_4.isChecked){
            mj_mtl_7 = "3"
        }else{
            Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_8_1.isChecked){
            mj_mtl_8 = "0"
        }else if(mental_8_2.isChecked){
            mj_mtl_8 = "1"
        }else if(mental_8_3.isChecked){
            mj_mtl_8 = "2"
        }else if(mental_8_4.isChecked){
            mj_mtl_8 = "3"
        }else{
            Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(mental_9_1.isChecked){
            mj_mtl_9 = "0"
        }else if(mental_9_2.isChecked){
            mj_mtl_9 = "1"
        }else if(mental_9_3.isChecked){
            mj_mtl_9 = "2"
        }else if(mental_9_4.isChecked){
            mj_mtl_9 = "3"
        }else{
            Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        SavePaper.Total.Array[2] = Paper_MENTAL(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category,
                mj_mtl_1, mj_mtl_2, mj_mtl_3, mj_mtl_4, mj_mtl_5, mj_mtl_6, mj_mtl_7, mj_mtl_8, mj_mtl_9, mj_mtl_sum)

        if(ChartDivision.ChartDivision.next_or_save(1)) {
            SavedListObject.SavedList.savedDataClass.mentalSaved = true
        }

        SavePaper.Total.temp_Mental = null

        return true

    }

    fun whenTempLoad(paper:Paper_MENTAL){

        ChartDivision.ChartDivision.ProgressAction(true, this)

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        if(paper.mj_mtl_1 == "0"){
            mental_1_1.isChecked = true
        }else if(paper.mj_mtl_1 == "1"){
            mental_1_2.isChecked = true
        }else if(paper.mj_mtl_1 == "2"){
            mental_1_3.isChecked = true
        }else if(paper.mj_mtl_1 == "3"){
            mental_1_4.isChecked = true
        }


        if(paper.mj_mtl_2 == "0"){
            mental_2_1.isChecked = true
        }else if(paper.mj_mtl_2 == "1"){
            mental_2_2.isChecked = true
        }else if(paper.mj_mtl_2 == "2"){
            mental_2_3.isChecked = true
        }else if(paper.mj_mtl_2 == "3"){
            mental_2_4.isChecked = true
        }


        if(paper.mj_mtl_3 == "0"){
            mental_3_1.isChecked = true
        }else if(paper.mj_mtl_3 == "1"){
            mental_3_2.isChecked = true
        }else if(paper.mj_mtl_3 == "2"){
            mental_3_3.isChecked = true
        }else if(paper.mj_mtl_3 == "3"){
            mental_3_4.isChecked = true
        }


        if(paper.mj_mtl_4 == "0"){
            mental_4_1.isChecked = true
        }else if(paper.mj_mtl_4 == "1"){
            mental_4_2.isChecked = true
        }else if(paper.mj_mtl_4 == "2"){
            mental_4_3.isChecked = true
        }else if(paper.mj_mtl_4 == "3"){
            mental_4_4.isChecked = true
        }


        if(paper.mj_mtl_5 == "0"){
            mental_5_1.isChecked = true
        }else if(paper.mj_mtl_5 == "1"){
            mental_5_2.isChecked = true
        }else if(paper.mj_mtl_5 == "2"){
            mental_5_3.isChecked = true
        }else if(paper.mj_mtl_5 == "3"){
            mental_5_4.isChecked = true
        }


        if(paper.mj_mtl_6 == "0"){
            mental_6_1.isChecked = true
        }else if(paper.mj_mtl_6 == "1"){
            mental_6_2.isChecked = true
        }else if(paper.mj_mtl_6 == "2"){
            mental_6_3.isChecked = true
        }else if(paper.mj_mtl_6 == "3"){
            mental_6_4.isChecked = true
        }


        if(paper.mj_mtl_7 == "0"){
            mental_7_1.isChecked = true
        }else if(paper.mj_mtl_7 == "1"){
            mental_7_2.isChecked = true
        }else if(paper.mj_mtl_7 == "2"){
            mental_7_3.isChecked = true
        }else if(paper.mj_mtl_7 == "3"){
            mental_7_4.isChecked = true
        }


        if(paper.mj_mtl_8 == "0"){
            mental_8_1.isChecked = true
        }else if(paper.mj_mtl_8 == "1"){
            mental_8_2.isChecked = true
        }else if(paper.mj_mtl_8 == "2"){
            mental_8_3.isChecked = true
        }else if(paper.mj_mtl_8 == "3"){
            mental_8_4.isChecked = true
        }


        if(paper.mj_mtl_9 == "0"){
            mental_9_1.isChecked = true
        }else if(paper.mj_mtl_9 == "1"){
            mental_9_2.isChecked = true
        }else if(paper.mj_mtl_9 == "2"){
            mental_9_3.isChecked = true
        }else if(paper.mj_mtl_9 == "3"){
            mental_9_4.isChecked = true
        }


    }

    fun GetPaper(paper:Paper_MENTAL){

        state = "getPaper"

        cannotEditQuestionnaire(mental_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        mental_examination_save.visibility = View.GONE
        mental_examination_cancel.visibility = View.GONE
        mental_edit_submit.visibility = View.VISIBLE


        if(paper.mj_mtl_1 == "0"){
            mental_1_1.isChecked = true
        }else if(paper.mj_mtl_1 == "1"){
            mental_1_2.isChecked = true
        }else if(paper.mj_mtl_1 == "2"){
            mental_1_3.isChecked = true
        }else if(paper.mj_mtl_1 == "3"){
            mental_1_4.isChecked = true
        }


        if(paper.mj_mtl_2 == "0"){
            mental_2_1.isChecked = true
        }else if(paper.mj_mtl_2 == "1"){
            mental_2_2.isChecked = true
        }else if(paper.mj_mtl_2 == "2"){
            mental_2_3.isChecked = true
        }else if(paper.mj_mtl_2 == "3"){
            mental_2_4.isChecked = true
        }


        if(paper.mj_mtl_3 == "0"){
            mental_3_1.isChecked = true
        }else if(paper.mj_mtl_3 == "1"){
            mental_3_2.isChecked = true
        }else if(paper.mj_mtl_3 == "2"){
            mental_3_3.isChecked = true
        }else if(paper.mj_mtl_3 == "3"){
            mental_3_4.isChecked = true
        }


        if(paper.mj_mtl_4 == "0"){
            mental_4_1.isChecked = true
        }else if(paper.mj_mtl_4 == "1"){
            mental_4_2.isChecked = true
        }else if(paper.mj_mtl_4 == "2"){
            mental_4_3.isChecked = true
        }else if(paper.mj_mtl_4 == "3"){
            mental_4_4.isChecked = true
        }


        if(paper.mj_mtl_5 == "0"){
            mental_5_1.isChecked = true
        }else if(paper.mj_mtl_5 == "1"){
            mental_5_2.isChecked = true
        }else if(paper.mj_mtl_5 == "2"){
            mental_5_3.isChecked = true
        }else if(paper.mj_mtl_5 == "3"){
            mental_5_4.isChecked = true
        }


        if(paper.mj_mtl_6 == "0"){
            mental_6_1.isChecked = true
        }else if(paper.mj_mtl_6 == "1"){
            mental_6_2.isChecked = true
        }else if(paper.mj_mtl_6 == "2"){
            mental_6_3.isChecked = true
        }else if(paper.mj_mtl_6 == "3"){
            mental_6_4.isChecked = true
        }


        if(paper.mj_mtl_7 == "0"){
            mental_7_1.isChecked = true
        }else if(paper.mj_mtl_7 == "1"){
            mental_7_2.isChecked = true
        }else if(paper.mj_mtl_7 == "2"){
            mental_7_3.isChecked = true
        }else if(paper.mj_mtl_7 == "3"){
            mental_7_4.isChecked = true
        }


        if(paper.mj_mtl_8 == "0"){
            mental_8_1.isChecked = true
        }else if(paper.mj_mtl_8 == "1"){
            mental_8_2.isChecked = true
        }else if(paper.mj_mtl_8 == "2"){
            mental_8_3.isChecked = true
        }else if(paper.mj_mtl_8 == "3"){
            mental_8_4.isChecked = true
        }


        if(paper.mj_mtl_9 == "0"){
            mental_9_1.isChecked = true
        }else if(paper.mj_mtl_9 == "1"){
            mental_9_2.isChecked = true
        }else if(paper.mj_mtl_9 == "2"){
            mental_9_3.isChecked = true
        }else if(paper.mj_mtl_9 == "3"){
            mental_9_4.isChecked = true
        }


    }

    fun GetPaper(paper:ServerPaper_Mental){

        state = "getPaper"

        cannotEditQuestionnaire(mental_root)

        progress_constraintLayout.visibility = View.GONE

        name_edit.text = paper.mj_name
        first_serial.text = paper.mj_jumin.substring(0, 6)
        last_serial.text = paper.mj_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        println(paper)

        mental_examination_save.visibility = View.GONE
        mental_examination_cancel.visibility = View.GONE
        mental_edit_submit.visibility = View.VISIBLE


        if(paper.mj_mtl_1 == "0"){
            mental_1_1.isChecked = true
        }else if(paper.mj_mtl_1 == "1"){
            mental_1_2.isChecked = true
        }else if(paper.mj_mtl_1 == "2"){
            mental_1_3.isChecked = true
        }else if(paper.mj_mtl_1 == "3"){
            mental_1_4.isChecked = true
        }


        if(paper.mj_mtl_2 == "0"){
            mental_2_1.isChecked = true
        }else if(paper.mj_mtl_2 == "1"){
            mental_2_2.isChecked = true
        }else if(paper.mj_mtl_2 == "2"){
            mental_2_3.isChecked = true
        }else if(paper.mj_mtl_2 == "3"){
            mental_2_4.isChecked = true
        }


        if(paper.mj_mtl_3 == "0"){
            mental_3_1.isChecked = true
        }else if(paper.mj_mtl_3 == "1"){
            mental_3_2.isChecked = true
        }else if(paper.mj_mtl_3 == "2"){
            mental_3_3.isChecked = true
        }else if(paper.mj_mtl_3 == "3"){
            mental_3_4.isChecked = true
        }


        if(paper.mj_mtl_4 == "0"){
            mental_4_1.isChecked = true
        }else if(paper.mj_mtl_4 == "1"){
            mental_4_2.isChecked = true
        }else if(paper.mj_mtl_4 == "2"){
            mental_4_3.isChecked = true
        }else if(paper.mj_mtl_4 == "3"){
            mental_4_4.isChecked = true
        }


        if(paper.mj_mtl_5 == "0"){
            mental_5_1.isChecked = true
        }else if(paper.mj_mtl_5 == "1"){
            mental_5_2.isChecked = true
        }else if(paper.mj_mtl_5 == "2"){
            mental_5_3.isChecked = true
        }else if(paper.mj_mtl_5 == "3"){
            mental_5_4.isChecked = true
        }


        if(paper.mj_mtl_6 == "0"){
            mental_6_1.isChecked = true
        }else if(paper.mj_mtl_6 == "1"){
            mental_6_2.isChecked = true
        }else if(paper.mj_mtl_6 == "2"){
            mental_6_3.isChecked = true
        }else if(paper.mj_mtl_6 == "3"){
            mental_6_4.isChecked = true
        }


        if(paper.mj_mtl_7 == "0"){
            mental_7_1.isChecked = true
        }else if(paper.mj_mtl_7 == "1"){
            mental_7_2.isChecked = true
        }else if(paper.mj_mtl_7 == "2"){
            mental_7_3.isChecked = true
        }else if(paper.mj_mtl_7 == "3"){
            mental_7_4.isChecked = true
        }


        if(paper.mj_mtl_8 == "0"){
            mental_8_1.isChecked = true
        }else if(paper.mj_mtl_8 == "1"){
            mental_8_2.isChecked = true
        }else if(paper.mj_mtl_8 == "2"){
            mental_8_3.isChecked = true
        }else if(paper.mj_mtl_8 == "3"){
            mental_8_4.isChecked = true
        }


        if(paper.mj_mtl_9 == "0"){
            mental_9_1.isChecked = true
        }else if(paper.mj_mtl_9 == "1"){
            mental_9_2.isChecked = true
        }else if(paper.mj_mtl_9 == "2"){
            mental_9_3.isChecked = true
        }else if(paper.mj_mtl_9 == "3"){
            mental_9_4.isChecked = true
        }


    }

}