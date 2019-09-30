package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.DataClass.*
import com.fineinsight.zzango.questionnaire.LocalList.Paper_ORAL
import com.fineinsight.zzango.questionnaire.Signature.BitmapFun
import kotlinx.android.synthetic.main.activity_oral_exam.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NewApi")
class OralExaminationActivity : RootActivity() {

    var sql_db : SQLiteDatabase? = null
    var signature:ByteArray = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oral_exam)

        //서명정보 가져오는거
        if(!Examinee.USER.info.SIGN.contentEquals(Examinee.USER.EMPTY_BYTE_ARRAY))
        {
            signature = Examinee.USER.info.SIGN
            Signature.setImageBitmap(BitmapFun.Fuc.getImage(Examinee.USER.info.SIGN))
        }

        sql_db = LocalDBhelper(this).writableDatabase

        oral_9_count.setOnFocusChangeListener {

            v, hasFocus ->

            if(hasFocus){

                oral_9_etc.isChecked = true

            }

        }

        oral_examination_save.setOnClickListener {

            if(check()){

                if(MainActivity.chart.isEmpty()){
                    if(getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local")){
                        ChartDivision.ChartDivision.local_each_insert(this, 5)
                    }else{
                        ChartDivision.ChartDivision.server_insert(this)
                    }
                }else{
                    ChartDivision.ChartDivision.chart_array_insert(this, 5, getSharedPreferences("connection", Context.MODE_PRIVATE).getString("state", "")!!.equals("local"))
                }


            }

        }

        oral_examination_cancel.setOnClickListener {

            cancelAlert()

        }

        oral_edit_submit.setOnClickListener {

            finish()

        }

        if(SavePaper.Total.temp_Oral != null){
            whenTempLoad(SavePaper.Total.temp_Oral!!)
        }else if(SavedListObject.SavedList.savedDataClass.oralSaved){
            whenTempLoad(SavePaper.Total.Array[9] as Paper_ORAL)
        }

        //로컬 리스트로부터 들어온 것일 때/////////////////////////////////////////////////////////////////////////////////
        if(intent.hasExtra("paper")){

            if(intent.getSerializableExtra("paper") is Paper_ORAL) {

                var paper = intent.getSerializableExtra("paper") as Paper_ORAL

                GetPaper(paper)

                try {
//                    var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature, 0, paper.signature.size)
//
//                    Signature.setImageBitmap(bmp)

                } catch (e: Exception) {
                    println(e.message)
                }

            }else{

                var paper = intent.getSerializableExtra("paper") as ServerPaper_Oral

                GetPaper(paper)

            }

        }else{
            name_edit.text = Examinee.USER.info.NAME
            first_serial.text = Examinee.USER.info.JUMIN1
            last_serial.text = Examinee.USER.info.JUMIN2

            if(MainActivity.chart.isEmpty()){

            }else{

                if(MainActivity.chart.isEmpty()){
                    oral_examination_save.text = "저장"
                }else{
                    if(ChartDivision.ChartDivision.next_or_save(5)){
                        oral_examination_save.text = "다음"
                    }else{
                        oral_examination_save.text = "저장"
                    }
                }

            }


        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        controlProgress(this)

        registrationNumber(last_serial)

    }

    //뷰에 포커스를 총괄하는 메서드
    override fun focusControl(view : View){

        if(view !is EditText){

            if(view != oral_9_etc) {

                oral_exam_inside_scroll_layout.isFocusableInTouchMode = true

                //포커스 이동을 강제로 만들기 위함
                oral_exam_inside_scroll_layout.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, 0)

                //9번 라디오 그룹은 예외이기 때문에 9번 라디오 버튼들을 조건으로 놓음
                //횟수를 입력하는 마지막 에디트텍스트를 초기화 시킴
                if(view == oral_9_1 || view == oral_9_2 || view == oral_9_3 || view == oral_9_4 || view == oral_9_5){

                    oral_9_count.setText("")

                }

                oral_exam_inside_scroll_layout.isFocusableInTouchMode = false

            }else{

                oral_9_count.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(oral_9_count, 0)

            }

        }

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
        var category = PaperNameInfo.PC.ORAL.EN_NM
        var oral_1 = ""
        var oral_2 = ""
        var oral_3 = ""
        var oral_4 = ""
        var oral_5 = ""
        var oral_6 = ""
        var oral_7 = ""
        var oral_8 = ""
        var oral_9 = ""
        var oral_10 = ""
        var oral_11 = ""
        var oral_12 = ""
        var oral_13 = ""
        var oral_14 = ""
        var oral_15 = ""
        var oral_16 = ""
        var oral_Remark = ""


        //서명정보 가져오는거
        if(!Examinee.USER.info.SIGN.contentEquals(Examinee.USER.EMPTY_BYTE_ARRAY))
        {
            signature = Examinee.USER.info.SIGN
        }



        if(!name_edit.text.isNullOrEmpty()){

            name = name_edit.text.toString()

        }

        if(!first_serial.text.isNullOrEmpty()){

            first_serial_text = first_serial.text.toString()

        }

        if(!last_serial.text.isNullOrEmpty()){

            last_serial_text = last_serial.text.toString()

        }

        oral_1 = when {
            oral_1_true.isChecked -> "1"
            oral_1_false.isChecked -> "2"
            else -> ""
        }

        oral_2 = when {
            oral_2_true.isChecked -> "1"
            oral_2_false.isChecked -> "2"
            oral_2_do_not_know.isChecked -> "3"
            else -> ""
        }

        oral_3 = when {
            oral_3_true.isChecked -> "1"
            oral_3_false.isChecked -> "2"
            oral_3_do_not_know.isChecked -> "3"
            else -> ""
        }

        oral_4 = when {
            oral_4_true.isChecked -> "1"
            oral_4_false.isChecked -> "2"
            else -> ""
        }

        oral_5 = when {
            oral_5_true.isChecked -> "1"
            oral_5_false.isChecked -> "2"
            else -> ""
        }

        oral_6 = when {
            oral_6_true.isChecked -> "1"
            oral_6_false.isChecked -> "2"
            else -> ""
        }

        oral_7 = when {
            oral_7_very_good.isChecked -> "1"
            oral_7_good.isChecked -> "2"
            oral_7_normal.isChecked -> "3"
            oral_7_bad.isChecked -> "4"
            oral_7_very_bad.isChecked -> "5"
            else -> ""
        }

        oral_8 = when {
            oral_8_true.isChecked -> "1"
            oral_8_false.isChecked -> "2"
            else -> ""
        }

        oral_9 = when {
            oral_9_5.isChecked -> "1"
            oral_9_4.isChecked -> "2"
            oral_9_3.isChecked -> "3"
            oral_9_2.isChecked -> "4"
            oral_9_1.isChecked -> "5"
            oral_9_etc.isChecked -> oral_9_count.text.toString()
            else -> ""
        }

        oral_10 = when {
            oral_10_4.isChecked -> "4"
            oral_10_3.isChecked -> "3"
            oral_10_2.isChecked -> "2"
            oral_10_1.isChecked -> "1"
            else -> ""

        }

        oral_11 = when {
            oral_11_5.isChecked -> "5"
            oral_11_4.isChecked -> "4"
            oral_11_3.isChecked -> "3"
            oral_11_2.isChecked -> "2"
            oral_11_1.isChecked -> "1"
            else -> ""
        }

        oral_12 = when {
            oral_12_true.isChecked -> "1"
            oral_12_false.isChecked -> "2"
            oral_12_do_not_know.isChecked -> "3"
            else -> ""
        }

        oral_13 = when {
            oral_13_5.isChecked -> "1"
            oral_13_4.isChecked -> "2"
            oral_13_3.isChecked -> "3"
            oral_13_2.isChecked -> "4"
            oral_13_1.isChecked -> "5"
            else -> ""
        }

        oral_14 = when {
            oral_14_5.isChecked -> "5"
            oral_14_4.isChecked -> "4"
            oral_14_3.isChecked -> "3"
            oral_14_2.isChecked -> "2"
            oral_14_1.isChecked -> "1"
            else -> ""
        }

        oral_15 = when {
            oral_15_true.isChecked -> "1"
            oral_15_false.isChecked -> "2"
            oral_15_do_not_know.isChecked -> "3"
            else -> ""
        }

        oral_Remark = when {
            !remark_content.text.toString().isNullOrEmpty() -> remark_content.text.toString()
            else -> ""
        }

        SavePaper.Total.temp_Oral = Paper_ORAL(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category, oral_1, oral_2,
                oral_3, oral_4, oral_5, oral_6, oral_7, oral_8, oral_9, oral_10,
                oral_11, oral_12, oral_13, oral_14, oral_15, oral_16, oral_Remark)

    }

    fun check() : Boolean{

        var exam_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = PaperNameInfo.PC.ORAL.EN_NM
        var oral_1 = ""
        var oral_2 = ""
        var oral_3 = ""
        var oral_4 = ""
        var oral_5 = ""
        var oral_6 = ""
        var oral_7 = ""
        var oral_8 = ""
        var oral_9 = ""
        var oral_10 = ""
        var oral_11 = ""
        var oral_12 = ""
        var oral_13 = ""
        var oral_14 = ""
        var oral_15 = ""
        var oral_16 = ""
        var oral_Remark = ""


        //서명정보 가져오는거
        if(!Examinee.USER.info.SIGN.contentEquals(Examinee.USER.EMPTY_BYTE_ARRAY))
        {
            signature = Examinee.USER.info.SIGN
        }



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

        if(oral_1_true.isChecked) {

            oral_1 = "1"

        }else if(oral_1_false.isChecked){

            oral_1 = "2"

        }else{

            Toast.makeText(this, "1번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_2_true.isChecked) {

            oral_2 = "1"

        }else if(oral_2_false.isChecked){

            oral_2 = "2"

        }else if(oral_2_do_not_know.isChecked){

            oral_2 = "3"

        }else{

            Toast.makeText(this, "2번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_3_true.isChecked) {

            oral_3 = "1"

        }else if(oral_3_false.isChecked){

            oral_3 = "2"

        }else if(oral_3_do_not_know.isChecked){

            oral_3 = "3"

        }else{

            Toast.makeText(this, "3번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_4_true.isChecked) {

            oral_4 = "1"

        }else if(oral_4_false.isChecked){

            oral_4 = "2"

        }else{

            Toast.makeText(this, "4번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_5_true.isChecked) {

            oral_5 = "1"

        }else if(oral_5_false.isChecked){

            oral_5 = "2"

        }else{

            Toast.makeText(this, "5번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_6_true.isChecked) {

            oral_6 = "1"

        }else if(oral_6_false.isChecked){

            oral_6 = "2"

        }else{

            Toast.makeText(this, "6번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_7_very_good.isChecked) {

            oral_7 = "1"

        }else if(oral_7_good.isChecked){

            oral_7 = "2"

        }else if(oral_7_normal.isChecked){

            oral_7 = "3"

        }else if(oral_7_bad.isChecked){

            oral_7 = "4"

        }else if(oral_7_very_bad.isChecked){

            oral_7 = "5"

        }else{

            Toast.makeText(this, "7번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_8_true.isChecked) {

            oral_8 = "1"

        }else if(oral_8_false.isChecked){

            oral_8 = "2"

        }else{

            Toast.makeText(this, "8번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_9_5.isChecked) {

            oral_9 = "1"

        }else if(oral_9_4.isChecked){

            oral_9 = "2"

        }else if(oral_9_3.isChecked){

            oral_9 = "3"

        }else if(oral_9_2.isChecked){

            oral_9 = "4"

        }else if(oral_9_1.isChecked){

            oral_9 = "5"

        }else if(oral_9_etc.isChecked) {

            oral_9 = oral_9_count.text.toString()

        }else{

            Toast.makeText(this, "9번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_10_4.isChecked) {

            oral_10 = "4"

        }else if(oral_10_3.isChecked){

            oral_10 = "3"

        }else if(oral_10_2.isChecked){

            oral_10 = "2"

        }else if(oral_10_1.isChecked){

            oral_10 = "1"

        }else{

            Toast.makeText(this, "10번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_11_5.isChecked) {

            oral_11 = "5"

        }else if(oral_11_4.isChecked){

            oral_11 = "4"

        }else if(oral_11_3.isChecked){

            oral_11 = "3"

        }else if(oral_11_2.isChecked){

            oral_11 = "2"

        }else if(oral_11_1.isChecked){

            oral_11 = "1"

        }else{

            Toast.makeText(this, "11번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_12_true.isChecked) {

            oral_12 = "1"

        }else if(oral_12_false.isChecked){

            oral_12 = "2"

        }else if(oral_12_do_not_know.isChecked){

            oral_12 = "3"

        }else{

            Toast.makeText(this, "12번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_13_5.isChecked) {

            oral_13 = "1"

        }else if(oral_13_4.isChecked){

            oral_13 = "2"

        }else if(oral_13_3.isChecked){

            oral_13 = "3"

        }else if(oral_13_2.isChecked){

            oral_13 = "4"

        }else if(oral_13_1.isChecked){

            oral_13 = "5"

        }else{

            Toast.makeText(this, "13번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_14_5.isChecked) {

            oral_14 = "5"

        }else if(oral_14_4.isChecked){

            oral_14 = "4"

        }else if(oral_14_3.isChecked){

            oral_14 = "3"

        }else if(oral_14_2.isChecked){

            oral_14 = "2"

        }else if(oral_14_1.isChecked){

            oral_14 = "1"

        }else{

            Toast.makeText(this, "14번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(oral_15_true.isChecked){

            oral_15 = "1"

        }else if(oral_15_false.isChecked){

            oral_15 = "2"

        }else if(oral_15_do_not_know.isChecked){

            oral_15 = "3"

        }else{

            Toast.makeText(this, "15번 문항을 체크해주세요", Toast.LENGTH_LONG).show()

            return false

        }

        if(!remark_content.text.toString().isNullOrEmpty()){

            oral_Remark = remark_content.text.toString()

        }else{

            oral_Remark = ""

        }

        SavePaper.Total.Array[9] = Paper_ORAL(
                exam_date, (SavePaper.Total.Array[0] as PublicDataInfo).exam_no, name, first_serial_text, last_serial_text, category, oral_1, oral_2,
                oral_3, oral_4, oral_5, oral_6, oral_7, oral_8, oral_9, oral_10,
                oral_11, oral_12, oral_13, oral_14, oral_15, oral_16, oral_Remark)

        if (ChartDivision.ChartDivision.next_or_save(5)) {
            SavedListObject.SavedList.savedDataClass.oralSaved = true
        }

        SavePaper.Total.temp_Oral = null

        ChartDivision.ChartDivision.ProgressAction(true, this)

        return true

    }

    fun whenTempLoad(paper:Paper_ORAL)
    {

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        //1번
        if(paper.oral_1=="1")
        {
            oral_1_true.isChecked = true
        }
        else if(paper.oral_1=="2")
        {
            oral_1_false.isChecked = true
        }



        //2번
        if(paper.oral_2=="1")
        {
            oral_2_true.isChecked = true
        }
        else if(paper.oral_2=="2")
        {
            oral_2_false.isChecked = true
        }
        else if(paper.oral_2=="3")
        {
            oral_2_do_not_know.isChecked = true
        }


        //3번
        if(paper.oral_3=="1")
        {
            oral_3_true.isChecked = true
        }
        else if(paper.oral_3=="2")
        {
            oral_3_false.isChecked = true
        }
        else if(paper.oral_3=="3")
        {
            oral_3_do_not_know.isChecked = true
        }


        //4번
        if(paper.oral_4=="1")
        {
            oral_4_true.isChecked = true
        }
        else if(paper.oral_4=="2")
        {
            oral_4_false.isChecked = true
        }

        //5번
        if(paper.oral_5=="1")
        {
            oral_5_true.isChecked = true
        }
        else if(paper.oral_5=="2")
        {
            oral_5_false.isChecked = true
        }

        //6번
        if(paper.oral_6=="1")
        {
            oral_6_true.isChecked = true
        }
        else if(paper.oral_6=="2")
        {
            oral_6_false.isChecked = true
        }


        //7번
        if(paper.oral_7=="1")
        {
            oral_7_very_good.isChecked = true
        }
        else if(paper.oral_7=="2")
        {
            oral_7_good.isChecked = true
        }
        else if(paper.oral_7=="3")
        {
            oral_7_normal.isChecked = true
        }
        else if(paper.oral_7=="4")
        {
            oral_7_bad.isChecked = true
        }
        else if(paper.oral_7=="5")
        {
            oral_7_very_bad.isChecked = true
        }


        //8번
        if(paper.oral_8=="1")
        {
            oral_8_true.isChecked = true
        }
        else if(paper.oral_8=="2")
        {
            oral_8_false.isChecked = true
        }


        //9번
        if(paper.oral_9=="1")
        {
            oral_9_1.isChecked = true
        }
        else if(paper.oral_9=="2")
        {
            oral_9_2.isChecked = true
        }
        else if(paper.oral_9=="3")
        {
            oral_9_3.isChecked = true
        }
        else if(paper.oral_9=="4")
        {
            oral_9_4.isChecked = true
        }
        else if(paper.oral_9=="5")
        {
            oral_9_5.isChecked = true
        }
        else if(!paper.oral_9.isNullOrEmpty())
        {
            oral_9_etc.isChecked = true
            oral_9_count.setText(paper.oral_9.toString())
        }


        //10번
        if(paper.oral_10=="1")
        {
            oral_10_1.isChecked = true
        }
        else if(paper.oral_10=="2")
        {
            oral_10_2.isChecked = true
        }
        else if(paper.oral_10=="3")
        {
            oral_10_3.isChecked = true
        }
        else if(paper.oral_10=="4")
        {
            oral_10_4.isChecked = true
        }




        //11번
        if(paper.oral_11=="1")
        {
            oral_11_1.isChecked = true
        }
        else if(paper.oral_11=="2")
        {
            oral_11_2.isChecked = true
        }
        else if(paper.oral_11=="3")
        {
            oral_11_3.isChecked = true
        }
        else if(paper.oral_11=="4")
        {
            oral_11_4.isChecked = true
        }
        else if(paper.oral_11=="5")
        {
            oral_11_5.isChecked = true
        }


        //12번
        if(paper.oral_12=="1")
        {
            oral_12_true.isChecked = true
        }
        else if(paper.oral_12=="2")
        {
            oral_12_false.isChecked = true
        }
        else if(paper.oral_12=="3")
        {
            oral_12_do_not_know.isChecked = true
        }


        //13번
        if(paper.oral_13=="1")
        {
            oral_13_1.isChecked = true
        }
        else if(paper.oral_13=="2")
        {
            oral_13_2.isChecked = true
        }
        else if(paper.oral_13=="3")
        {
            oral_13_3.isChecked = true
        }
        else if(paper.oral_13=="4")
        {
            oral_13_4.isChecked = true
        }
        else if(paper.oral_13=="5")
        {
            oral_13_5.isChecked = true
        }



        //14번
        if(paper.oral_14=="1")
        {
            oral_14_1.isChecked = true
        }
        else if(paper.oral_14=="2")
        {
            oral_14_2.isChecked = true
        }
        else if(paper.oral_14=="3")
        {
            oral_14_3.isChecked = true
        }
        else if(paper.oral_14=="4")
        {
            oral_14_4.isChecked = true
        }
        else if(paper.oral_14=="5")
        {
            oral_14_5.isChecked = true
        }


        //15번
        if(paper.oral_15=="1")
        {
            oral_15_true.isChecked = true
        }
        else if(paper.oral_15=="2")
        {
            oral_15_false.isChecked = true
        }
        else if(paper.oral_15=="3")
        {
            oral_15_do_not_know.isChecked = true
        }

        remark_content.setText(paper.oral_Remark)

    }

    fun GetPaper(paper:Paper_ORAL)
    {

        state = "getPaper"

        cannotEditQuestionnaire(oral_exam_inside_scroll_layout)

        name_edit.text = paper.name
        first_serial.text = paper.first_serial
        last_serial.text = paper.last_serial

        println(paper)

        oral_examination_save.visibility = View.GONE
        oral_examination_cancel.visibility = View.GONE
        oral_edit_submit.visibility = View.VISIBLE

        //1번
        if(paper.oral_1=="1")
        {
            oral_1_true.isChecked = true
        }
        else if(paper.oral_1=="2")
        {
            oral_1_false.isChecked = true
        }



        //2번
        if(paper.oral_2=="1")
        {
            oral_2_true.isChecked = true
        }
        else if(paper.oral_2=="2")
        {
            oral_2_false.isChecked = true
        }
        else if(paper.oral_2=="3")
        {
            oral_2_do_not_know.isChecked = true
        }


        //3번
        if(paper.oral_3=="1")
        {
            oral_3_true.isChecked = true
        }
        else if(paper.oral_3=="2")
        {
            oral_3_false.isChecked = true
        }
        else if(paper.oral_3=="3")
        {
            oral_3_do_not_know.isChecked = true
        }


        //4번
        if(paper.oral_4=="1")
        {
            oral_4_true.isChecked = true
        }
        else if(paper.oral_4=="2")
        {
            oral_4_false.isChecked = true
        }

        //5번
        if(paper.oral_5=="1")
        {
            oral_5_true.isChecked = true
        }
        else if(paper.oral_5=="2")
        {
            oral_5_false.isChecked = true
        }

        //6번
        if(paper.oral_6=="1")
        {
            oral_6_true.isChecked = true
        }
        else if(paper.oral_6=="2")
        {
            oral_6_false.isChecked = true
        }


        //7번
        if(paper.oral_7=="1")
        {
            oral_7_very_good.isChecked = true
        }
        else if(paper.oral_7=="2")
        {
            oral_7_good.isChecked = true
        }
        else if(paper.oral_7=="3")
        {
            oral_7_normal.isChecked = true
        }
        else if(paper.oral_7=="4")
        {
            oral_7_bad.isChecked = true
        }
        else if(paper.oral_7=="5")
        {
            oral_7_very_bad.isChecked = true
        }


        //8번
        if(paper.oral_8=="1")
        {
            oral_8_true.isChecked = true
        }
        else if(paper.oral_8=="2")
        {
            oral_8_false.isChecked = true
        }


        //9번
        if(paper.oral_9=="1")
        {
            oral_9_1.isChecked = true
        }
        else if(paper.oral_9=="2")
        {
            oral_9_2.isChecked = true
        }
        else if(paper.oral_9=="3")
        {
            oral_9_3.isChecked = true
        }
        else if(paper.oral_9=="4")
        {
            oral_9_4.isChecked = true
        }
        else if(paper.oral_9=="5")
        {
            oral_9_5.isChecked = true
        }
        else if(!paper.oral_9.isNullOrEmpty())
        {
            oral_9_etc.isChecked = true
            oral_9_count.setText(paper.oral_9.toString())
        }


        //10번
        if(paper.oral_10=="1")
        {
            oral_10_1.isChecked = true
        }
        else if(paper.oral_10=="2")
        {
            oral_10_2.isChecked = true
        }
        else if(paper.oral_10=="3")
        {
            oral_10_3.isChecked = true
        }
        else if(paper.oral_10=="4")
        {
            oral_10_4.isChecked = true
        }




        //11번
        if(paper.oral_11=="1")
        {
            oral_11_1.isChecked = true
        }
        else if(paper.oral_11=="2")
        {
            oral_11_2.isChecked = true
        }
        else if(paper.oral_11=="3")
        {
            oral_11_3.isChecked = true
        }
        else if(paper.oral_11=="4")
        {
            oral_11_4.isChecked = true
        }
        else if(paper.oral_11=="5")
        {
            oral_11_5.isChecked = true
        }


        //12번
        if(paper.oral_12=="1")
        {
            oral_12_true.isChecked = true
        }
        else if(paper.oral_12=="2")
        {
            oral_12_false.isChecked = true
        }
        else if(paper.oral_12=="3")
        {
            oral_12_do_not_know.isChecked = true
        }


        //13번
        if(paper.oral_13=="1")
        {
            oral_13_1.isChecked = true
        }
        else if(paper.oral_13=="2")
        {
            oral_13_2.isChecked = true
        }
        else if(paper.oral_13=="3")
        {
            oral_13_3.isChecked = true
        }
        else if(paper.oral_13=="4")
        {
            oral_13_4.isChecked = true
        }
        else if(paper.oral_13=="5")
        {
            oral_13_5.isChecked = true
        }



        //14번
        if(paper.oral_14=="1")
        {
            oral_14_1.isChecked = true
        }
        else if(paper.oral_14=="2")
        {
            oral_14_2.isChecked = true
        }
        else if(paper.oral_14=="3")
        {
            oral_14_3.isChecked = true
        }
        else if(paper.oral_14=="4")
        {
            oral_14_4.isChecked = true
        }
        else if(paper.oral_14=="5")
        {
            oral_14_5.isChecked = true
        }


        //15번
        if(paper.oral_15=="1")
        {
            oral_15_true.isChecked = true
        }
        else if(paper.oral_15=="2")
        {
            oral_15_false.isChecked = true
        }
        else if(paper.oral_15=="3")
        {
            oral_15_do_not_know.isChecked = true
        }

        remark_content.setText(paper.oral_Remark)

    }

    fun GetPaper(paper:ServerPaper_Oral)
    {

        state = "getPaper"

        cannotEditQuestionnaire(oral_exam_inside_scroll_layout)

        name_edit.text = paper.oral_name
        first_serial.text = paper.oral_jumin.substring(0, 6)
        last_serial.text = paper.oral_jumin.substring(6, 7)
        Signature.visibility = View.GONE

        println(paper)

        oral_examination_save.visibility = View.GONE
        oral_examination_cancel.visibility = View.GONE
        oral_edit_submit.visibility = View.VISIBLE

        //1번
        if(paper.oral_1=="1")
        {
            oral_1_true.isChecked = true
        }
        else if(paper.oral_1=="2")
        {
            oral_1_false.isChecked = true
        }



        //2번
        if(paper.oral_2=="1")
        {
            oral_2_true.isChecked = true
        }
        else if(paper.oral_2=="2")
        {
            oral_2_false.isChecked = true
        }
        else if(paper.oral_2=="3")
        {
            oral_2_do_not_know.isChecked = true
        }


        //3번
        if(paper.oral_3=="1")
        {
            oral_3_true.isChecked = true
        }
        else if(paper.oral_3=="2")
        {
            oral_3_false.isChecked = true
        }
        else if(paper.oral_3=="3")
        {
            oral_3_do_not_know.isChecked = true
        }


        //4번
        if(paper.oral_4=="1")
        {
            oral_4_true.isChecked = true
        }
        else if(paper.oral_4=="2")
        {
            oral_4_false.isChecked = true
        }

        //5번
        if(paper.oral_5=="1")
        {
            oral_5_true.isChecked = true
        }
        else if(paper.oral_5=="2")
        {
            oral_5_false.isChecked = true
        }

        //6번
        if(paper.oral_6=="1")
        {
            oral_6_true.isChecked = true
        }
        else if(paper.oral_6=="2")
        {
            oral_6_false.isChecked = true
        }


        //7번
        if(paper.oral_7=="1")
        {
            oral_7_very_good.isChecked = true
        }
        else if(paper.oral_7=="2")
        {
            oral_7_good.isChecked = true
        }
        else if(paper.oral_7=="3")
        {
            oral_7_normal.isChecked = true
        }
        else if(paper.oral_7=="4")
        {
            oral_7_bad.isChecked = true
        }
        else if(paper.oral_7=="5")
        {
            oral_7_very_bad.isChecked = true
        }


        //8번
        if(paper.oral_8=="1")
        {
            oral_8_true.isChecked = true
        }
        else if(paper.oral_8=="2")
        {
            oral_8_false.isChecked = true
        }


        //9번
        if(paper.oral_9=="1")
        {
            oral_9_1.isChecked = true
        }
        else if(paper.oral_9=="2")
        {
            oral_9_2.isChecked = true
        }
        else if(paper.oral_9=="3")
        {
            oral_9_3.isChecked = true
        }
        else if(paper.oral_9=="4")
        {
            oral_9_4.isChecked = true
        }
        else if(paper.oral_9=="5")
        {
            oral_9_5.isChecked = true
        }
        else if(!paper.oral_9.isNullOrEmpty())
        {
            oral_9_etc.isChecked = true
            oral_9_count.setText(paper.oral_9.toString())
        }


        //10번
        if(paper.oral_10=="1")
        {
            oral_10_1.isChecked = true
        }
        else if(paper.oral_10=="2")
        {
            oral_10_2.isChecked = true
        }
        else if(paper.oral_10=="3")
        {
            oral_10_3.isChecked = true
        }
        else if(paper.oral_10=="4")
        {
            oral_10_4.isChecked = true
        }




        //11번
        if(paper.oral_11=="1")
        {
            oral_11_1.isChecked = true
        }
        else if(paper.oral_11=="2")
        {
            oral_11_2.isChecked = true
        }
        else if(paper.oral_11=="3")
        {
            oral_11_3.isChecked = true
        }
        else if(paper.oral_11=="4")
        {
            oral_11_4.isChecked = true
        }
        else if(paper.oral_11=="5")
        {
            oral_11_5.isChecked = true
        }


        //12번
        if(paper.oral_12=="1")
        {
            oral_12_true.isChecked = true
        }
        else if(paper.oral_12=="2")
        {
            oral_12_false.isChecked = true
        }
        else if(paper.oral_12=="3")
        {
            oral_12_do_not_know.isChecked = true
        }


        //13번
        if(paper.oral_13=="1")
        {
            oral_13_1.isChecked = true
        }
        else if(paper.oral_13=="2")
        {
            oral_13_2.isChecked = true
        }
        else if(paper.oral_13=="3")
        {
            oral_13_3.isChecked = true
        }
        else if(paper.oral_13=="4")
        {
            oral_13_4.isChecked = true
        }
        else if(paper.oral_13=="5")
        {
            oral_13_5.isChecked = true
        }



        //14번
        if(paper.oral_14=="1")
        {
            oral_14_1.isChecked = true
        }
        else if(paper.oral_14=="2")
        {
            oral_14_2.isChecked = true
        }
        else if(paper.oral_14=="3")
        {
            oral_14_3.isChecked = true
        }
        else if(paper.oral_14=="4")
        {
            oral_14_4.isChecked = true
        }
        else if(paper.oral_14=="5")
        {
            oral_14_5.isChecked = true
        }


        //15번
        if(paper.oral_15=="1")
        {
            oral_15_true.isChecked = true
        }
        else if(paper.oral_15=="2")
        {
            oral_15_false.isChecked = true
        }
        else if(paper.oral_15=="3")
        {
            oral_15_do_not_know.isChecked = true
        }

        remark_content.setText(paper.oral_Remark)

    }

}
