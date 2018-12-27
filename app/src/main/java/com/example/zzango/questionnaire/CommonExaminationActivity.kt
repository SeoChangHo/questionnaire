package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_common_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import java.util.*


class CommonExaminationActivity : AppCompatActivity() {

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : Date,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("mj1_1_1") @Expose var exam_1 : String,
                         @SerializedName("mj1_1_2") @Expose var exam_2 : String,
                         @SerializedName("mj1_2_1") @Expose var exam_3 : String,
                         @SerializedName("mj1_2_2") @Expose var exam_4 : String,
                         @SerializedName("mj1_3_1") @Expose var exam_5 : String,
                         @SerializedName("mj1_3_2") @Expose var exam_6 : String,
                         @SerializedName("mj1_4_1") @Expose var exam_7 : String,
                         @SerializedName("mj1_4_2") @Expose var exam_8 : String,
                         @SerializedName("mj1_5_1") @Expose var exam_9 : String,
                         @SerializedName("mj1_5_2") @Expose var exam_10 : String,
                         @SerializedName("mj1_6_1") @Expose var exam_11 : String,
                         @SerializedName("mj1_6_2") @Expose var exam_12 : String,
                         @SerializedName("mj1_7_1") @Expose var exam_13 : String,
                         @SerializedName("mj1_7_2") @Expose var exam_14 : String,
                         @SerializedName("mj1_7_etc") @Expose var exam_15 : String,
                         @SerializedName("mj2_1") @Expose var exam_16 : String,
                         @SerializedName("mj2_2") @Expose var exam_17 : String,
                         @SerializedName("mj2_3") @Expose var exam_18 : String,
                         @SerializedName("mj2_4") @Expose var exam_19 : String,
                         @SerializedName("mj2_5") @Expose var exam_20 : String)

    var popup : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_exam)

        diagnosis_medication_etc_examination_check.setOnCheckedChangeListener {

            buttonView, isChecked ->

            if(isChecked){

                diagnosis_medication_etc_disease_name.visibility = View.VISIBLE
                diagnosis_medication_etc_disease_name_input.visibility = View.VISIBLE

            }else{

                if(!diagnosis_medication_etc_medication_check.isChecked){

                    diagnosis_medication_etc_disease_name.visibility = View.GONE
                    diagnosis_medication_etc_disease_name_input.visibility = View.GONE

                }

            }

        }

        diagnosis_medication_etc_medication_check.setOnCheckedChangeListener {

            buttonView, isChecked ->

            if(isChecked){

                diagnosis_medication_etc_disease_name.visibility = View.VISIBLE
                diagnosis_medication_etc_disease_name_input.visibility = View.VISIBLE

            }else{

                if(!diagnosis_medication_etc_examination_check.isChecked){

                    diagnosis_medication_etc_disease_name.visibility = View.GONE
                    diagnosis_medication_etc_disease_name_input.visibility = View.GONE

                }

            }

        }

        diagnosis_medication_etc_disease_name_input.setOnFocusChangeListener {

            v, hasFocus ->

            v.clearFocus()

        }

        common_4_false.setOnClickListener {
            textView20.visibility = View.GONE
            common_4_1_radio.visibility = View.GONE

            textView42.visibility = View.GONE
            textView43.visibility = View.GONE
            textView44.visibility = View.GONE
            editText_4_1_1.visibility = View.GONE
            editText_4_1_2.visibility = View.GONE

            textView45.visibility = View.GONE
            textView46.visibility = View.GONE
            textView47.visibility = View.GONE
            textView48.visibility = View.GONE
            editText_4_1_3.visibility = View.GONE
            editText_4_1_4.visibility = View.GONE
            editText_4_1_5.visibility = View.GONE
        }

        common_4_true.setOnClickListener {
            textView20.visibility = View.VISIBLE
            common_4_1_radio.visibility = View.VISIBLE
        }

        common_4_1_true.setOnClickListener{
            textView42.visibility = View.VISIBLE
            textView43.visibility = View.VISIBLE
            textView44.visibility = View.VISIBLE
            editText_4_1_1.visibility = View.VISIBLE
            editText_4_1_2.visibility = View.VISIBLE

            textView45.visibility = View.GONE
            textView46.visibility = View.GONE
            textView47.visibility = View.GONE
            textView48.visibility = View.GONE
            editText_4_1_3.visibility = View.GONE
            editText_4_1_4.visibility = View.GONE
            editText_4_1_5.visibility = View.GONE
        }

        common_4_1_false.setOnClickListener {
            textView42.visibility = View.GONE
            textView43.visibility = View.GONE
            textView44.visibility = View.GONE
            editText_4_1_1.visibility = View.GONE
            editText_4_1_2.visibility = View.GONE

            textView45.visibility = View.VISIBLE
            textView46.visibility = View.VISIBLE
            textView47.visibility = View.VISIBLE
            textView48.visibility = View.VISIBLE
            editText_4_1_3.visibility = View.VISIBLE
            editText_4_1_4.visibility = View.VISIBLE
            editText_4_1_5.visibility = View.VISIBLE
        }

        common_5_false.setOnClickListener {
            textView23.visibility = View.GONE
            common_5_1_radio.visibility = View.GONE

            textView49.visibility = View.GONE
            textView50.visibility = View.GONE
            textView51.visibility = View.GONE
            editText_5_1_1.visibility = View.GONE
            editText_5_1_2.visibility = View.GONE

            textView52.visibility = View.GONE
            textView53.visibility = View.GONE
            textView54.visibility = View.GONE
            textView55.visibility = View.GONE
            editText_5_1_3.visibility = View.GONE
            editText_5_1_4.visibility = View.GONE
            editText_5_1_5.visibility = View.GONE
        }

        common_5_true.setOnClickListener {
            textView23.visibility = View.VISIBLE
            common_5_1_radio.visibility = View.VISIBLE
        }

        common_5_1_true.setOnClickListener{
            textView49.visibility = View.VISIBLE
            textView50.visibility = View.VISIBLE
            textView51.visibility = View.VISIBLE
            editText_5_1_1.visibility = View.VISIBLE
            editText_5_1_2.visibility = View.VISIBLE

            textView52.visibility = View.GONE
            textView53.visibility = View.GONE
            textView54.visibility = View.GONE
            textView55.visibility = View.GONE
            editText_5_1_3.visibility = View.GONE
            editText_5_1_4.visibility = View.GONE
            editText_5_1_5.visibility = View.GONE
        }

        common_5_1_false.setOnClickListener {
            textView49.visibility = View.GONE
            textView50.visibility = View.GONE
            textView51.visibility = View.GONE
            editText_5_1_1.visibility = View.GONE
            editText_5_1_2.visibility = View.GONE

            textView52.visibility = View.VISIBLE
            textView53.visibility = View.VISIBLE
            textView54.visibility = View.VISIBLE
            textView55.visibility = View.VISIBLE
            editText_5_1_3.visibility = View.VISIBLE
            editText_5_1_4.visibility = View.VISIBLE
            editText_5_1_5.visibility = View.VISIBLE
        }

        common_6_true.setOnClickListener {
            textView25.visibility = View.VISIBLE
            common_6_1_radio.visibility = View.VISIBLE
        }

        common_6_false.setOnClickListener {
            textView25.visibility = View.GONE
            common_6_1_radio.visibility = View.GONE
        }

        checkBox1.setOnClickListener {
            if(checkBox1.isChecked == true){
                common_7_1_1_radio.visibility = View.VISIBLE
                table_edit_1.visibility = View.VISIBLE
                table_text_1.visibility = View.VISIBLE
            }else{
                common_7_1_1_radio.visibility = View.GONE
                table_edit_1.visibility = View.GONE
                table_text_1.visibility = View.GONE
            }
        }

        checkBox2.setOnClickListener {
            if(checkBox2.isChecked == true) {
                common_7_1_2_radio.visibility = View.VISIBLE
                table_edit_2.visibility = View.VISIBLE
                table_text_2.visibility = View.VISIBLE
            }else{
                common_7_1_2_radio.visibility = View.GONE
                table_edit_2.visibility = View.GONE
                table_text_2.visibility = View.GONE
            }
        }

        checkBox3.setOnClickListener {
            if(checkBox3.isChecked == true) {
                common_7_1_3_radio.visibility = View.VISIBLE
                table_edit_3.visibility = View.VISIBLE
                table_text_3.visibility = View.VISIBLE
            }else{
                common_7_1_3_radio.visibility = View.GONE
                table_edit_3.visibility = View.GONE
                table_text_3.visibility = View.GONE
            }
        }

        checkBox4.setOnClickListener {

            if(checkBox4.isChecked == true) {
                common_7_1_4_radio.visibility = View.VISIBLE
                table_edit_4.visibility = View.VISIBLE
                table_text_4.visibility = View.VISIBLE
            }else{
                common_7_1_4_radio.visibility = View.GONE
                table_edit_4.visibility = View.GONE
                table_text_4.visibility = View.GONE
            }
        }

        checkBox5.setOnClickListener {

            if(checkBox5.isChecked == true) {
                common_7_1_5_radio.visibility = View.VISIBLE
                table_edit_5.visibility = View.VISIBLE
                table_text_5.visibility = View.VISIBLE
            }else{
                common_7_1_5_radio.visibility = View.GONE
                table_edit_5.visibility = View.GONE
                table_text_5.visibility = View.GONE
            }
        }

        checkBox6.setOnClickListener {
            if(checkBox6.isChecked == true) {
                common_7_2_1_radio.visibility = View.VISIBLE
                table_edit_6.visibility = View.VISIBLE
                table_text_6.visibility = View.VISIBLE
            }else{
                common_7_2_1_radio.visibility = View.GONE
                table_edit_6.visibility = View.GONE
                table_text_6.visibility = View.GONE
            }
        }

        checkBox7.setOnClickListener {
            if(checkBox7.isChecked == true) {
                common_7_2_2_radio.visibility = View.VISIBLE
                table_edit_7.visibility = View.VISIBLE
                table_text_7.visibility = View.VISIBLE
            }else{
                common_7_2_2_radio.visibility = View.GONE
                table_edit_7.visibility = View.GONE
                table_text_7.visibility = View.GONE
            }
        }

        checkBox8.setOnClickListener {
            if(checkBox8.isChecked == true) {
                common_7_2_3_radio.visibility = View.VISIBLE
                table_edit_8.visibility = View.VISIBLE
                table_text_8.visibility = View.VISIBLE
            }else{
                common_7_2_3_radio.visibility = View.GONE
                table_edit_8.visibility = View.GONE
                table_text_8.visibility = View.GONE
            }
        }

        checkBox9.setOnClickListener {
            if(checkBox9.isChecked == true) {
                common_7_2_4_radio.visibility = View.VISIBLE
                table_edit_9.visibility = View.VISIBLE
                table_text_9.visibility = View.VISIBLE
            }else{
                common_7_2_4_radio.visibility = View.GONE
                table_edit_9.visibility = View.GONE
                table_text_9.visibility = View.GONE
            }
        }

        checkBox10.setOnClickListener {
            if(checkBox10.isChecked == true) {
                common_7_2_5_radio.visibility = View.VISIBLE
                table_edit_10.visibility = View.VISIBLE
                table_text_10.visibility = View.VISIBLE
            }else{
                common_7_2_5_radio.visibility = View.GONE
                table_edit_10.visibility = View.GONE
                table_text_10.visibility = View.GONE
            }
        }

        common_examination_save.setOnClickListener{
            saveCompleteAlert()
        }

        common_examination_cancel.setOnClickListener {

            finish()

        }

    }

    fun saveCompleteAlert() {

        popup = false

        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_complete_alert, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.save_complete_alert_text.setText("현재 저장되지 않습니다.")

        if (!popup) {

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

            //            startActivity(Intent(this@OralExamination, /*메인화면 클래스*/).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            dialog.dismiss()

        }

    }

    @SuppressLint("NewApi")
    fun check() : Boolean {

        var exam_date = Date()
        var name = ""
        var first_serial_text = ""
        var last_serial_text = ""
        var category = "common"
        var mj1_1_1 = ""
        var mj1_1_2 = ""
        var mj1_2_1 = ""
        var mj1_2_2 = ""
        var mj1_3_1 = ""
        var mj1_3_2 = ""
        var mj1_4_1 = ""
        var mj1_4_2 = ""
        var mj1_5_1 = ""
        var mj1_5_2 = ""
        var mj1_6_1 = ""
        var mj1_6_2 = ""
        var mj1_7_1 = ""
        var mj1_7_2 = ""
        var mj1_7_etc = ""
        var mj2_1 = ""
        var mj2_2 = ""
        var mj2_3 = ""
        var mj2_4 = ""
        var mj2_5 = ""
        var mj2_etc = ""
        var mj3 = ""
        var mj4 = ""
        var mj4_1_1 = ""
        var mj4_1_2 = ""
        var mj4_2_1 = ""
        var mj4_2_2 = ""
        var mj5 = ""
        var mj5_1 = ""
        var mj61 = ""
        var mj62 = ""
        var mj63 = ""
        var mj64 = ""
        var mj6_1_11 = ""
        var mj6_1_12 = ""
        var mj6_1_13 = ""
        var mj6_1_14 = ""
        var mj6_1_21 = ""
        var mj6_1_22 = ""
        var mj6_1_23 = ""
        var mj6_1_24 = ""
        var mj6_1_31 = ""
        var mj6_1_32 = ""
        var mj6_1_33 = ""
        var mj6_1_34 = ""
        var mj6_1_41 = ""
        var mj6_1_42 = ""
        var mj6_1_43 = ""
        var mj6_1_44 = ""
        var mj6_1_51 = ""
        var mj6_1_52 = ""
        var mj6_1_53 = ""
        var mj6_1_54 = ""
        var mj6_1_etc = ""
        var mj6_2_11 = ""
        var mj6_2_12 = ""
        var mj6_2_13 = ""
        var mj6_2_14 = ""
        var mj6_2_21 = ""
        var mj6_2_22 = ""
        var mj6_2_23 = ""
        var mj6_2_24 = ""
        var mj6_2_31 = ""
        var mj6_2_32 = ""
        var mj6_2_33 = ""
        var mj6_2_34 = ""
        var mj6_2_41 = ""
        var mj6_2_42 = ""
        var mj6_2_43 = ""
        var mj6_2_44 = ""
        var mj6_2_51 = ""
        var mj6_2_52 = ""
        var mj6_2_53 = ""
        var mj6_2_54 = ""
        var mj6_2_etc = ""
        var mj7_1 = ""
        var mj7_2_1 = ""
        var mj7_2_2 = ""
        var mj8_1 = ""
        var mj8_2_1 = ""
        var mj8_2_2 = ""
        var mj9 = ""
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
        var mj_key = ""
        var mj_email = ""
        var mj_email_yn = ""
        var mj_year = ""
        var mj_doc_no = ""
        var mj_doc_name = ""

        return true
    }

}
