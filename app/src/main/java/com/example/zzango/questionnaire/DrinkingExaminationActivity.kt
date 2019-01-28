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
import com.example.zzango.questionnaire.LocalList.Paper_DRINKING
import com.example.zzango.questionnaire.Signature.BitmapFun
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


class DrinkingExaminationActivity : RootActivity(){

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("sg2_spDrink1") @Expose var sg2_spDrink1 : String,
                         @SerializedName("sg2_spDrink2_1") @Expose var sg2_spDrink2_1 : String,
                         @SerializedName("sg2_spDrink2_2") @Expose var sg2_spDrink2_2 : String,
                         @SerializedName("sg2_spDrink3") @Expose var sg2_spDrink3 : String,
                         @SerializedName("sg2_spDrink4") @Expose var sg2_spDrink4 : String,
                         @SerializedName("sg2_spDrink5") @Expose var sg2_spDrink5 : String,
                         @SerializedName("sg2_spDrink6") @Expose var sg2_spDrink6 : String,
                         @SerializedName("sg2_spDrink7") @Expose var sg2_spDrink7 : String,
                         @SerializedName("sg2_spDrink8") @Expose var sg2_spDrink8 : String,
                         @SerializedName("sg2_spDrink9") @Expose var sg2_spDrink9 : String,
                         @SerializedName("sg2_spDrink10") @Expose var sg2_spDrink10 : String,
                         @SerializedName("sg2_spDrinkSum") @Expose var sg2_spDrinkSum : String)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinking_exam)

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

            var paper = intent.getSerializableExtra("paper") as Paper_DRINKING

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

            if(MainActivity.chart != "SET3"){
                drinking_examination_save.text = "다음"
            }
            if(MainActivity.chart == "SET0"){
                drinking_examination_save.text = "저장"
            }

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        drinking_examination_save.setOnClickListener {

            if(check()){

                login_appbar_loading_progress.visibility = View.VISIBLE
                login_appbar_loading_progress_bg.visibility = View.VISIBLE

                if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state","")!!.equals("local")){

                    drinking_exam_local_insert()

                }else{

                    drinking_exam_server_insert()

                }

            }

        }

        drinking_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        drinking_edit_submit.setOnClickListener {

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

    fun drinking_exam_local_insert(){

        println("로컬")

        if(MainActivity.chart == "SET3"){

            LocalDBhelper(this).onCreate(sql_db)
            LocalDBhelper(this).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!, MainActivity.chart)

            LocalDBhelper(this).commonExaminationDB(sql_db)
            LocalDBhelper(this).commonSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)

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

            MainActivity.login_user_name = ""
            MainActivity.user_first_serial = ""
            MainActivity.user_last_serial = ""
            saveCompleteAlert()

        }else if(MainActivity.chart == "SET6"){

            startActivity(Intent(this@DrinkingExaminationActivity, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }else if(MainActivity.chart == "SET0"){

            LocalDBhelper(this).onCreate(sql_db)
            LocalDBhelper(this).LocalListDrinkingInsert(sql_db!!, PaperArray.PaperList.Arr_DRINKING!!, "SET11")

            LocalDBhelper(this).exerciseCreate(sql_db)
            LocalDBhelper(this).exerciseSaveLocal(sql_db!!, PaperArray.PaperList.Arr_EXERCISE!!)

            LocalDBhelper(this).nutritionCreate(sql_db)
            LocalDBhelper(this).nutritionSaveLocal(sql_db!!, PaperArray.PaperList.Arr_NUTRITION!!)

            LocalDBhelper(this).smokingCreate(sql_db)
            LocalDBhelper(this).smokingSaveLocal(sql_db!!, PaperArray.PaperList.Arr_SMOKING!!)

            LocalDBhelper(this).drinkingCreate(sql_db)
            LocalDBhelper(this).drinkingSaveLocal(sql_db!!, PaperArray.PaperList.Arr_DRINKING!!)
            saveCompleteAlert()
        }

    }

    fun drinking_exam_server_insert(){

        this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)


        if(MainActivity.chart == "SET3"){

            OracleUtill().save_papers().savePapersServer(PaperArray.PaperList.Arr_RESULT!!).enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.isSuccessful) {

                        if (!response.body()!!.equals("S")) {

                            login_appbar_loading_progress.visibility = View.GONE
                            login_appbar_loading_progress_bg.visibility = View.GONE
                            this@DrinkingExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this@DrinkingExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

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
                    this@DrinkingExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Toast.makeText(this@DrinkingExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                    println(t.toString())
                }
            })

        }else if(MainActivity.chart == "SET6"){

            startActivity(Intent(this@DrinkingExaminationActivity, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }else if(MainActivity.chart == "SET0"){
            OracleUtill().save_papers().savePapersServer(PaperArray.PaperList.Arr_RESULT!!).enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.isSuccessful) {

                        if (!response.body()!!.equals("S")) {

                            login_appbar_loading_progress.visibility = View.GONE
                            login_appbar_loading_progress_bg.visibility = View.GONE
                            this@DrinkingExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this@DrinkingExaminationActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                        } else {

                            saveCompleteAlert()

                        }

                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                    login_appbar_loading_progress.visibility = View.GONE
                    login_appbar_loading_progress_bg.visibility = View.GONE
                    this@DrinkingExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Toast.makeText(this@DrinkingExaminationActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                    println(t.toString())
                }
            })
        }

    }

    fun saveCompleteAlert(){

        login_appbar_loading_progress.visibility = View.GONE
        login_appbar_loading_progress_bg.visibility = View.GONE
        this@DrinkingExaminationActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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

            startActivity(Intent(this@DrinkingExaminationActivity, MainActivity::class.java).putExtra("from", "drinking").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            dialog.dismiss()

        }

    }

    @SuppressLint("NewApi")
    fun check() : Boolean {

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var exam_no = ""
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

        if(drinking_1_1.isChecked){
            sg2_spDrink1 = "1"
        }else if(drinking_1_2.isChecked){
            sg2_spDrink1 = "2"
        }else if(drinking_1_3.isChecked){
            sg2_spDrink1 = "3"
        }else if(drinking_1_4.isChecked){
            sg2_spDrink1 = "4"
        }else{
            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(drinking_2_1_1.isChecked){
            sg2_spDrink2_1 = "1"
        }else if(drinking_2_1_2.isChecked){
            sg2_spDrink2_1 = "2"
        }else if(drinking_2_1_3.isChecked){
            sg2_spDrink2_1 = "3"
        }else if(drinking_2_1_4.isChecked){
            sg2_spDrink2_1 = "4"
        }else if(drinking_2_1_5.isChecked){
            sg2_spDrink2_1 = "5"
        }else{
            Toast.makeText(this, "2-1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(drinking_2_2_1.isChecked){
            sg2_spDrink2_2 = "1"
        }else if(drinking_2_2_2.isChecked){
            sg2_spDrink2_2 = "2"
        }else if(drinking_2_2_3.isChecked){
            sg2_spDrink2_2 = "3"
        }else if(drinking_2_2_4.isChecked){
            sg2_spDrink2_2 = "4"
        }else if(drinking_2_2_5.isChecked){
            sg2_spDrink2_2 = "5"
        }else{
            Toast.makeText(this, "2-2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(drinking_3_1.isChecked){
            sg2_spDrink3 = "1"
        }else if(drinking_3_2.isChecked){
            sg2_spDrink3 = "2"
        }else if(drinking_3_3.isChecked){
            sg2_spDrink3 = "3"
        }else if(drinking_3_4.isChecked){
            sg2_spDrink3 = "4"
        }else if(drinking_3_5.isChecked){
            sg2_spDrink3 = "5"
        }else{
            Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(drinking_4_1.isChecked){
            sg2_spDrink4 = "1"
        }else if(drinking_4_2.isChecked){
            sg2_spDrink4 = "2"
        }else if(drinking_4_3.isChecked){
            sg2_spDrink4 = "3"
        }else if(drinking_4_4.isChecked){
            sg2_spDrink4 = "4"
        }else if(drinking_4_5.isChecked){
            sg2_spDrink4 = "5"
        }else{
            Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(drinking_5_1.isChecked){
            sg2_spDrink5 = "1"
        }else if(drinking_5_2.isChecked){
            sg2_spDrink5 = "2"
        }else if(drinking_5_3.isChecked){
            sg2_spDrink5 = "3"
        }else if(drinking_5_4.isChecked){
            sg2_spDrink5 = "4"
        }else if(drinking_5_5.isChecked){
            sg2_spDrink5 = "5"
        }else{
            Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(drinking_6_1.isChecked){
            sg2_spDrink6 = "1"
        }else if(drinking_6_2.isChecked){
            sg2_spDrink6 = "2"
        }else if(drinking_6_3.isChecked){
            sg2_spDrink6 = "3"
        }else if(drinking_6_4.isChecked){
            sg2_spDrink6 = "4"
        }else if(drinking_6_5.isChecked){
            sg2_spDrink6 = "5"
        }else{
            Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(drinking_7_1.isChecked){
            sg2_spDrink7 = "1"
        }else if(drinking_7_2.isChecked){
            sg2_spDrink7 = "2"
        }else if(drinking_7_3.isChecked){
            sg2_spDrink7 = "3"
        }else if(drinking_7_4.isChecked){
            sg2_spDrink7 = "4"
        }else if(drinking_7_5.isChecked){
            sg2_spDrink7 = "5"
        }else{
            Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(drinking_8_1.isChecked){
            sg2_spDrink8 = "1"
        }else if(drinking_8_2.isChecked){
            sg2_spDrink8 = "2"
        }else if(drinking_8_3.isChecked){
            sg2_spDrink8 = "3"
        }else if(drinking_8_4.isChecked){
            sg2_spDrink8 = "4"
        }else if(drinking_8_5.isChecked){
            sg2_spDrink8 = "5"
        }else{
            Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(drinking_9_1.isChecked){
            sg2_spDrink9 = "1"
        }else if(drinking_9_2.isChecked){
            sg2_spDrink9 = "2"
        }else if(drinking_9_3.isChecked){
            sg2_spDrink9 = "3"
        }else{
            Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }


        if(drinking_10_1.isChecked){
            sg2_spDrink10 = "1"
        }else if(drinking_10_2.isChecked){
            sg2_spDrink10 = "2"
        }else if(drinking_10_3.isChecked){
            sg2_spDrink10 = "3"
        }else{
            Toast.makeText(this, "10번 문항을 체크해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        exam_no = MainActivity.exam_no


        PaperArray.PaperList.Arr_DRINKING!!.add(Paper_DRINKING(
                exam_date, exam_no, signature, name, first_serial_text, last_serial_text, category,
                sg2_spDrink1, sg2_spDrink2_1, sg2_spDrink2_2, sg2_spDrink3, sg2_spDrink4, sg2_spDrink5,
                sg2_spDrink6, sg2_spDrink7, sg2_spDrink8, sg2_spDrink9, sg2_spDrink10, sg2_spDrinkSum
        ))

        PaperArray.PaperList.Arr_RESULT!!.add(PaperArray.PaperList.Arr_SMOKING!!)

        return true

    }

    fun GetPaper(paper: Paper_DRINKING) {


        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        drinking_examination_save.visibility = View.GONE
        drinking_examination_cancel.visibility = View.GONE
        drinking_edit_submit.visibility = View.VISIBLE


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