package com.example.zzango.questionnaire

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class CommonExaminationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_exam)

//        diagnosis_medication_etc_examination_check.setOnCheckedChangeListener {
//
//            buttonView, isChecked ->
//
//            if(isChecked){
//
//                diagnosis_medication_etc_disease_name.visibility = View.VISIBLE
//                diagnosis_medication_etc_disease_name_input.visibility = View.VISIBLE
//
//            }else{
//
//                if(!diagnosis_medication_etc_medication_check.isChecked){
//
//                    diagnosis_medication_etc_disease_name.visibility = View.GONE
//                    diagnosis_medication_etc_disease_name_input.visibility = View.GONE
//
//                }
//
//            }
//
//        }
//
//        diagnosis_medication_etc_medication_check.setOnCheckedChangeListener {
//
//            buttonView, isChecked ->
//
//            if(isChecked){
//
//                diagnosis_medication_etc_disease_name.visibility = View.VISIBLE
//                diagnosis_medication_etc_disease_name_input.visibility = View.VISIBLE
//
//            }else{
//
//                if(!diagnosis_medication_etc_examination_check.isChecked){
//
//                    diagnosis_medication_etc_disease_name.visibility = View.GONE
//                    diagnosis_medication_etc_disease_name_input.visibility = View.GONE
//
//                }
//
//            }
//
//        }
//
//        diagnosis_medication_etc_disease_name_input.setOnFocusChangeListener {
//
//            v, hasFocus ->
//
//            v.clearFocus()
//
//        }

    }
}
