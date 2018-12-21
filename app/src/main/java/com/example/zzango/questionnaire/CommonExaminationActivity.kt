package com.example.zzango.questionnaire

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_common_exam.*


class CommonExaminationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_exam)

        EnableState_4_1()
        EnableState_5_1()

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

        common_4_1_true.setOnClickListener{
            EnableState_4_1()
            editText_4_1_1.isEnabled = true
            editText_4_1_2.isEnabled = true
        }

        common_4_1_false.setOnClickListener {
            EnableState_4_1()
            editText_4_1_3.isEnabled = true
            editText_4_1_4.isEnabled = true
            editText_4_1_5.isEnabled = true
        }

        common_5_1_true.setOnClickListener{
            EnableState_5_1()
            editText_5_1_1.isEnabled = true
            editText_5_1_2.isEnabled = true
        }

        common_5_1_false.setOnClickListener {
            EnableState_5_1()
            editText_5_1_3.isEnabled = true
            editText_5_1_4.isEnabled = true
            editText_5_1_5.isEnabled = true
        }

        checkBox1.setOnClickListener {
            if(checkBox1.isChecked == true){
                common_7_1_1_1.isEnabled = true
                common_7_1_1_2.isEnabled = true
                common_7_1_1_3.isEnabled = true
                common_7_1_1_4.isEnabled = true
                table_edit_1.isEnabled = true
            }else{
                common_7_1_1_1.isEnabled = false
                common_7_1_1_2.isEnabled = false
                common_7_1_1_3.isEnabled = false
                common_7_1_1_4.isEnabled = false
                table_edit_1.isEnabled = false
            }
        }

        checkBox2.setOnClickListener {
            if(checkBox2.isChecked == true) {
                common_7_1_2_1.isEnabled = true
                common_7_1_2_2.isEnabled = true
                common_7_1_2_3.isEnabled = true
                common_7_1_2_4.isEnabled = true
                table_edit_2.isEnabled = true
            }else{
                common_7_1_2_1.isEnabled = false
                common_7_1_2_2.isEnabled = false
                common_7_1_2_3.isEnabled = false
                common_7_1_2_4.isEnabled = false
                table_edit_2.isEnabled = false
            }
        }

        checkBox3.setOnClickListener {
            if(checkBox3.isChecked == true) {
                common_7_1_3_1.isEnabled = true
                common_7_1_3_2.isEnabled = true
                common_7_1_3_3.isEnabled = true
                common_7_1_3_4.isEnabled = true
                table_edit_3.isEnabled = true
            }else{
                common_7_1_3_1.isEnabled = false
                common_7_1_3_2.isEnabled = false
                common_7_1_3_3.isEnabled = false
                common_7_1_3_4.isEnabled = false
                table_edit_3.isEnabled = false
            }
        }

        checkBox4.setOnClickListener {

            if(checkBox4.isChecked == true) {
                common_7_1_4_1.isEnabled = true
                common_7_1_4_2.isEnabled = true
                common_7_1_4_3.isEnabled = true
                common_7_1_4_4.isEnabled = true
                table_edit_4.isEnabled = true
            }else{
                common_7_1_4_1.isEnabled = false
                common_7_1_4_2.isEnabled = false
                common_7_1_4_3.isEnabled = false
                common_7_1_4_4.isEnabled = false
                table_edit_4.isEnabled = false
            }
        }

        checkBox5.setOnClickListener {

            if(checkBox5.isChecked == true) {
                common_7_1_5_1.isEnabled = true
                common_7_1_5_2.isEnabled = true
                common_7_1_5_3.isEnabled = true
                common_7_1_5_4.isEnabled = true
                table_edit_5.isEnabled = true
            }else{
                common_7_1_5_1.isEnabled = false
                common_7_1_5_2.isEnabled = false
                common_7_1_5_3.isEnabled = false
                common_7_1_5_4.isEnabled = false
                table_edit_5.isEnabled = false
            }
        }

        checkBox6.setOnClickListener {
            if(checkBox6.isChecked == true) {
                common_7_2_1_1.isEnabled = true
                common_7_2_1_2.isEnabled = true
                common_7_2_1_3.isEnabled = true
                common_7_2_1_4.isEnabled = true
                table_edit_6.isEnabled = true
            }else{
                common_7_2_1_1.isEnabled = false
                common_7_2_1_2.isEnabled = false
                common_7_2_1_3.isEnabled = false
                common_7_2_1_4.isEnabled = false
                table_edit_6.isEnabled = false
            }
        }

        checkBox7.setOnClickListener {
            if(checkBox7.isChecked == true) {
                common_7_2_2_1.isEnabled = true
                common_7_2_2_2.isEnabled = true
                common_7_2_2_3.isEnabled = true
                common_7_2_2_4.isEnabled = true
                table_edit_7.isEnabled = true
            }else{
                common_7_2_2_1.isEnabled = false
                common_7_2_2_2.isEnabled = false
                common_7_2_2_3.isEnabled = false
                common_7_2_2_4.isEnabled = false
                table_edit_7.isEnabled = false
            }
        }

        checkBox8.setOnClickListener {
            if(checkBox8.isChecked == true) {
                common_7_2_3_1.isEnabled = true
                common_7_2_3_2.isEnabled = true
                common_7_2_3_3.isEnabled = true
                common_7_2_3_4.isEnabled = true
                table_edit_8.isEnabled = true
            }else{
                common_7_2_3_1.isEnabled = false
                common_7_2_3_2.isEnabled = false
                common_7_2_3_3.isEnabled = false
                common_7_2_3_4.isEnabled = false
                table_edit_8.isEnabled = false
            }
        }

        checkBox9.setOnClickListener {
            if(checkBox9.isChecked == true) {
                common_7_2_4_1.isEnabled = true
                common_7_2_4_2.isEnabled = true
                common_7_2_4_3.isEnabled = true
                common_7_2_4_4.isEnabled = true
                table_edit_9.isEnabled = true
            }else{
                common_7_2_4_1.isEnabled = false
                common_7_2_4_2.isEnabled = false
                common_7_2_4_3.isEnabled = false
                common_7_2_4_4.isEnabled = false
                table_edit_9.isEnabled = false
            }
        }

        checkBox10.setOnClickListener {
            if(checkBox10.isChecked == true) {
                common_7_2_5_1.isEnabled = true
                common_7_2_5_2.isEnabled = true
                common_7_2_5_3.isEnabled = true
                common_7_2_5_4.isEnabled = true
                table_edit_10.isEnabled = true
            }else{
                common_7_2_5_1.isEnabled = false
                common_7_2_5_2.isEnabled = false
                common_7_2_5_3.isEnabled = false
                common_7_2_5_4.isEnabled = false
                table_edit_10.isEnabled = false
            }
        }

    }

    fun EnableState_4_1(){

        editText_4_1_1.isEnabled = false
        editText_4_1_2.isEnabled = false
        editText_4_1_3.isEnabled = false
        editText_4_1_4.isEnabled = false
        editText_4_1_5.isEnabled = false

    }

    fun EnableState_5_1(){
        editText_5_1_1.isEnabled = false
        editText_5_1_2.isEnabled = false
        editText_5_1_3.isEnabled = false
        editText_5_1_4.isEnabled = false
        editText_5_1_5.isEnabled = false
    }


}
