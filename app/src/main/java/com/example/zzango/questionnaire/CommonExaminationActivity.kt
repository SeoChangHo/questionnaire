package com.example.zzango.questionnaire

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
import kotlinx.android.synthetic.main.activity_common_exam.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*


class CommonExaminationActivity : AppCompatActivity() {

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


}
