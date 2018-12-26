package com.example.zzango.questionnaire.LocalList

import java.io.Serializable


data class Paper(var isChecked: Boolean, var category: String, var name: String, var serial_first: String, var serial_last: String,
                 var oral_date: String, var oral_bun_no:String , var oral_email_yn: String,
                 var oral_1:String, var oral_2:String, var oral_3:String, var oral_4:String,
                 var oral_5:String, var oral_6:String, var oral_7:String, var oral_8:String,
                 var oral_9:String, var oral_10:String, var oral_11:String, var oral_12:String,
                 var oral_13:String, var oral_14:String, var oral_15:String, var oral_16:String,
                 var oral_17:String, var oral_18:String, var oral_19:String, var oral_20:String) : Serializable