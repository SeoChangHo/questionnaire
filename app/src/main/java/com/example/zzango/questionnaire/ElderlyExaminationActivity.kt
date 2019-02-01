package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.PaperArray
import com.example.zzango.questionnaire.LocalList.Paper_ELDERLY
import com.example.zzango.questionnaire.Signature.BitmapFun
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


class ElderlyExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    lateinit var signature:ByteArray

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("mj66_1") @Expose var mj66_1 : String,
                         @SerializedName("mj66_2") @Expose var mj66_2 : String,
                         @SerializedName("mj66_3_1") @Expose var mj66_3_1 : String,
                         @SerializedName("mj66_3_2") @Expose var mj66_3_2 : String,
                         @SerializedName("mj66_3_3") @Expose var mj66_3_3 : String,
                         @SerializedName("mj66_3_4") @Expose var mj66_3_4 : String,
                         @SerializedName("mj66_3_5") @Expose var mj66_3_5 : String,
                         @SerializedName("mj66_3_6") @Expose var mj66_3_6 : String,
                         @SerializedName("mj66_4") @Expose var mj66_4 : String,
                         @SerializedName("mj66_5") @Expose var mj66_5 : String)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elderly_exam)

        controlProgress(this, questionnaire_progress_wrapper, progress_constraintLayout, questionnaire_progress, progress_guideline, progress_guideline2, progress_guideline3, progress_guideline4, progress_guideline5, progress_guideline6, progress_guideline7, progress_guideline8)

        //서명정보 가져오는거
        if(MainActivity.user_stream!=null)
        {
            signature = MainActivity.user_stream!!
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(MainActivity.user_stream!!))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        elderly_examination_save.setOnClickListener {

            if(check()){

                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    elderly_exam_local_insert()

                }else{

                    elderly_exam_server_insert()

                }

            }

        }

        elderly_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        elderly_edit_submit.setOnClickListener {

            finish()

        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            var paper = intent.getSerializableExtra("paper") as Paper_ELDERLY

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

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    fun elderly_exam_local_insert(){

        if(MainActivity.chart == "SET4"){

            LocalDBhelper(this).onCreate(sql_db)
            LocalDBhelper(this).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!, MainActivity.chart)

            LocalDBhelper(this).commonExaminationDB(sql_db)
            LocalDBhelper(this).commonSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)

            LocalDBhelper(this).cognitiveCreate(sql_db)
            LocalDBhelper(this).cognitiveSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COGNITIVE!!)

            LocalDBhelper(this).elderlyCreate(sql_db)
            LocalDBhelper(this).elderlySaveLocal(sql_db!!, PaperArray.PaperList.Arr_ELDERLY!!)

            MainActivity.login_user_name = ""
            MainActivity.user_first_serial = ""
            MainActivity.user_last_serial = ""
            saveCompleteAlert()

        }else if(MainActivity.chart == "SET6"){

            LocalDBhelper(this).onCreate(sql_db)
            LocalDBhelper(this).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!, MainActivity.chart)

            LocalDBhelper(this).commonExaminationDB(sql_db)
            LocalDBhelper(this).commonSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)

            LocalDBhelper(this).cognitiveCreate(sql_db)
            LocalDBhelper(this).cognitiveSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COGNITIVE!!)

            LocalDBhelper(this).mentalCreate(sql_db)
            LocalDBhelper(this).mentalSaveLocal(sql_db!!, PaperArray.PaperList.Arr_MENTAL!!)

            LocalDBhelper(this).exerciseCreate(sql_db)
            LocalDBhelper(this).exerciseSaveLocal(sql_db!!, PaperArray.PaperList.Arr_EXERCISE!!)

            LocalDBhelper(this).nutritionCreate(sql_db)
            LocalDBhelper(this).nutritionSaveLocal(sql_db!!, PaperArray.PaperList.Arr_NUTRITION!!)

            LocalDBhelper(this).smokingCreate(sql_db)
            LocalDBhelper(this).smokingSaveLocal(sql_db!!, PaperArray.PaperList.Arr_SMOKING!!)

            LocalDBhelper(this).drinkingCreate(sql_db)
            LocalDBhelper(this).drinkingSaveLocal(sql_db!!, PaperArray.PaperList.Arr_DRINKING!!)

            LocalDBhelper(this).elderlyCreate(sql_db)
            LocalDBhelper(this).elderlySaveLocal(sql_db!!, PaperArray.PaperList.Arr_ELDERLY!!)

            MainActivity.login_user_name = ""
            MainActivity.user_first_serial = ""
            MainActivity.user_last_serial = ""
            saveCompleteAlert()

        }else if(MainActivity.chart == "SET0"){

            LocalDBhelper(this).onCreate(sql_db)
            LocalDBhelper(this).LocalListElderlyInsert(sql_db!!, PaperArray.PaperList.Arr_ELDERLY!!, "SET12")

            LocalDBhelper(this).elderlyCreate(sql_db)
            LocalDBhelper(this).elderlySaveLocal(sql_db!!, PaperArray.PaperList.Arr_ELDERLY!!)
            saveCompleteAlert()
        }

    }

    fun elderly_exam_server_insert(){

        println("서버")

        if(MainActivity.chart != "SET0"){
            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            OracleUtill().save_papers().savePapersServer(PaperArray.PaperList.Arr_RESULT!!).enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.isSuccessful) {

                        if (!response.body()!!.equals("S")) {

                            login_appbar_loading_progress.visibility = View.GONE
                            login_appbar_loading_progress_bg.visibility = View.GONE
                            this@ElderlyExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this@ElderlyExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                        } else {

                            saveCompleteAlert()

                        }

                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                    login_appbar_loading_progress.visibility = View.GONE
                    login_appbar_loading_progress_bg.visibility = View.GONE
                    this@ElderlyExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Toast.makeText(this@ElderlyExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                    println(t.toString())
                }

            })

        }else{

            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            OracleUtill().elderly_examination().elderlyServer(PaperArray.PaperList.Arr_ELDERLY!!).enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.isSuccessful) {

                        if (!response.body()!!.equals("S")) {

                            login_appbar_loading_progress.visibility = View.GONE
                            login_appbar_loading_progress_bg.visibility = View.GONE
                            this@ElderlyExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this@ElderlyExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                        } else {

                            MainActivity.login_user_name = ""
                            MainActivity.user_first_serial = ""
                            MainActivity.user_last_serial = ""
                            saveCompleteAlert()

                        }

                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                    login_appbar_loading_progress.visibility = View.GONE
                    login_appbar_loading_progress_bg.visibility = View.GONE
                    this@ElderlyExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Toast.makeText(this@ElderlyExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                    println(t.toString())
                }

            })

        }

    }

    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@ElderlyExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        popup = false

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_complete_alert, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.save_complete_alert_text.text = "저장이 완료 되었습니다"

        if(!popup) {

            dialog.show().let {

                popup = true

            }

        }

        var displayMetrics = DisplayMetrics()
        dialog.window.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // The absolute width of the available display size in pixels.
        var displayWidth = displayMetrics.widthPixels
        // The absolute height of the available display size in pixels.
        var displayHeight = displayMetrics.heightPixels

        // Initialize a new window manager layout parameters
        var layoutParams = WindowManager.LayoutParams()

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.window.attributes)

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set alert dialog width equal to screen width 70%
        var dialogWindowWidth = (displayWidth * 0.7f).toInt()
        // Set alert dialog height equal to screen height 70%
        var dialogWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth
        layoutParams.height = dialogWindowHeight

        // Apply the newly created layout parameters to the alert dialog window
        dialog.window.attributes = layoutParams


        dialog.setOnDismissListener {

            popup = false
            dialog = null

        }

        dialog_view.return_alert.setOnClickListener {

            startActivity(Intent(this@ElderlyExaminationActivity, MainActivity::class.java).putExtra("from", "elderly").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            dialog.dismiss()

        }

    }

    @SuppressLint("NewApi")
    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = "elderly"
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

        if(MainActivity.chart == "SET0"){
            PaperArray.PaperArrFunction.ArrayListInit()
            exam_no = System.currentTimeMillis().toString()
        }else{
            exam_no = MainActivity.exam_no
        }

        PaperArray.PaperList.Arr_ELDERLY!!.add(Paper_ELDERLY(
                exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category,
                mj66_1, mj66_2, mj66_3_1, mj66_3_2, mj66_3_3, mj66_3_4, mj66_3_5, mj66_3_6, mj66_4, mj66_5
        ))

        PaperArray.PaperList.Arr_RESULT!!.add(PaperArray.PaperList.Arr_ELDERLY!!)

        return true

    }

    fun GetPaper(paper:Paper_ELDERLY) {

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

}