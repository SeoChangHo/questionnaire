package com.example.zzango.questionnaire.LocalList

import java.io.Serializable

//로컬 저장 리스트
data class Paper(var isChecked: Boolean, var no: Int, var category: String, var name: String, var date: String) : Serializable

//구강검진 리스트
//data class Paper_Oral()

//data class Paper(var isChecked: Boolean, var category: String, var name: String, var serial_first: String, var serial_last: String,
//                 var exam_date: String, var exam_bun_no:String , var exam_email_yn: String,
//                 var exam_1:String, var exam_2:String, var exam_3:String, var exam_4:String,
//                 var exam_5:String, var exam_6:String, var exam_7:String, var exam_8:String,
//                 var exam_9:String, var exam_10:String, var exam_11:String, var exam_12:String,
//                 var exam_13:String, var exam_14:String, var exam_15:String, var exam_16:String,
//                 var exam_17:String, var exam_18:String, var exam_19:String, var exam_20:String) : Serializable