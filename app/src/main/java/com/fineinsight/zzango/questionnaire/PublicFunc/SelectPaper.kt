package com.fineinsight.zzango.questionnaire.PublicFunc

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import com.fineinsight.zzango.questionnaire.LocalDBhelper
import com.fineinsight.zzango.questionnaire.LocalList.*

class SelectPaper {

    object Select{

        fun Return_ORAL(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_ORAL>
        {
            var PaperArray = ArrayList<Paper_ORAL>()

            val data = LocalDBhelper(activity).Select_Local_ORAL(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_ORAL(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("oral_1")),
                        data.getString(data.getColumnIndex("oral_2")),
                        data.getString(data.getColumnIndex("oral_3")),
                        data.getString(data.getColumnIndex("oral_4")),
                        data.getString(data.getColumnIndex("oral_5")),
                        data.getString(data.getColumnIndex("oral_6")),
                        data.getString(data.getColumnIndex("oral_7")),
                        data.getString(data.getColumnIndex("oral_8")),
                        data.getString(data.getColumnIndex("oral_9")),
                        data.getString(data.getColumnIndex("oral_10")),
                        data.getString(data.getColumnIndex("oral_11")),
                        data.getString(data.getColumnIndex("oral_12")),
                        data.getString(data.getColumnIndex("oral_13")),
                        data.getString(data.getColumnIndex("oral_14")),
                        data.getString(data.getColumnIndex("oral_15")),
                        data.getString(data.getColumnIndex("oral_16")),
                        data.getString(data.getColumnIndex("oral_Remark"))
                ))
                data.moveToNext()
            }




            return PaperArray
        }

        fun Return_COMMON(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_COMMON>
        {
            var PaperArray = ArrayList<Paper_COMMON>()

            val data = LocalDBhelper(activity).Select_Local_COMMON(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){


                PaperArray.add(Paper_COMMON(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("mj1_1_1")),
                        data.getString(data.getColumnIndex("mj1_1_2")),
                        data.getString(data.getColumnIndex("mj1_2_1")),
                        data.getString(data.getColumnIndex("mj1_2_2")),
                        data.getString(data.getColumnIndex("mj1_3_1")),
                        data.getString(data.getColumnIndex("mj1_3_2")),
                        data.getString(data.getColumnIndex("mj1_4_1")),
                        data.getString(data.getColumnIndex("mj1_4_2")),
                        data.getString(data.getColumnIndex("mj1_5_1")),
                        data.getString(data.getColumnIndex("mj1_5_2")),
                        data.getString(data.getColumnIndex("mj1_6_1")),
                        data.getString(data.getColumnIndex("mj1_6_2")),
                        data.getString(data.getColumnIndex("mj1_7_1")),
                        data.getString(data.getColumnIndex("mj1_7_2")),
                        data.getString(data.getColumnIndex("mj2_1")),
                        data.getString(data.getColumnIndex("mj2_2")),
                        data.getString(data.getColumnIndex("mj2_3")),
                        data.getString(data.getColumnIndex("mj2_4")),
                        data.getString(data.getColumnIndex("mj2_5")),
                        data.getString(data.getColumnIndex("mj3")),
                        data.getString(data.getColumnIndex("mj4")),
                        data.getString(data.getColumnIndex("mj4_1_1")),
                        data.getString(data.getColumnIndex("mj4_1_2")),
                        data.getString(data.getColumnIndex("mj4_2_1")),
                        data.getString(data.getColumnIndex("mj4_2_2")),
                        data.getString(data.getColumnIndex("mj4_2_3")),
                        data.getString(data.getColumnIndex("mj5")),
                        data.getString(data.getColumnIndex("mj5_1_1")),
                        data.getString(data.getColumnIndex("mj5_1_2")),
                        data.getString(data.getColumnIndex("mj5_2_1")),
                        data.getString(data.getColumnIndex("mj5_2_2")),
                        data.getString(data.getColumnIndex("mj5_2_3")),
                        data.getString(data.getColumnIndex("mj6")),
                        data.getString(data.getColumnIndex("mj6_1")),
                        data.getString(data.getColumnIndex("mj71")),
                        data.getString(data.getColumnIndex("mj72")),
                        data.getString(data.getColumnIndex("mj73")),
                        data.getString(data.getColumnIndex("mj74")),
                        data.getString(data.getColumnIndex("mj7_1_11")),
                        data.getString(data.getColumnIndex("mj7_1_12")),
                        data.getString(data.getColumnIndex("mj7_1_13")),
                        data.getString(data.getColumnIndex("mj7_1_14")),
                        data.getString(data.getColumnIndex("mj7_1_21")),
                        data.getString(data.getColumnIndex("mj7_1_22")),
                        data.getString(data.getColumnIndex("mj7_1_23")),
                        data.getString(data.getColumnIndex("mj7_1_24")),
                        data.getString(data.getColumnIndex("mj7_1_31")),
                        data.getString(data.getColumnIndex("mj7_1_32")),
                        data.getString(data.getColumnIndex("mj7_1_33")),
                        data.getString(data.getColumnIndex("mj7_1_34")),
                        data.getString(data.getColumnIndex("mj7_1_41")),
                        data.getString(data.getColumnIndex("mj7_1_42")),
                        data.getString(data.getColumnIndex("mj7_1_43")),
                        data.getString(data.getColumnIndex("mj7_1_44")),
                        data.getString(data.getColumnIndex("mj7_1_51")),
                        data.getString(data.getColumnIndex("mj7_1_52")),
                        data.getString(data.getColumnIndex("mj7_1_53")),
                        data.getString(data.getColumnIndex("mj7_1_54")),
                        data.getString(data.getColumnIndex("mj7_1_etc")),
                        data.getString(data.getColumnIndex("mj7_2_11")),
                        data.getString(data.getColumnIndex("mj7_2_12")),
                        data.getString(data.getColumnIndex("mj7_2_13")),
                        data.getString(data.getColumnIndex("mj7_2_14")),
                        data.getString(data.getColumnIndex("mj7_2_21")),
                        data.getString(data.getColumnIndex("mj7_2_22")),
                        data.getString(data.getColumnIndex("mj7_2_23")),
                        data.getString(data.getColumnIndex("mj7_2_24")),
                        data.getString(data.getColumnIndex("mj7_2_31")),
                        data.getString(data.getColumnIndex("mj7_2_32")),
                        data.getString(data.getColumnIndex("mj7_2_33")),
                        data.getString(data.getColumnIndex("mj7_2_34")),
                        data.getString(data.getColumnIndex("mj7_2_41")),
                        data.getString(data.getColumnIndex("mj7_2_42")),
                        data.getString(data.getColumnIndex("mj7_2_43")),
                        data.getString(data.getColumnIndex("mj7_2_44")),
                        data.getString(data.getColumnIndex("mj7_2_51")),
                        data.getString(data.getColumnIndex("mj7_2_52")),
                        data.getString(data.getColumnIndex("mj7_2_53")),
                        data.getString(data.getColumnIndex("mj7_2_54")),
                        data.getString(data.getColumnIndex("mj7_2_etc")),
                        data.getString(data.getColumnIndex("mj8_1")),
                        data.getString(data.getColumnIndex("mj8_2_1")),
                        data.getString(data.getColumnIndex("mj8_2_2")),
                        data.getString(data.getColumnIndex("mj9_1")),
                        data.getString(data.getColumnIndex("mj9_2_1")),
                        data.getString(data.getColumnIndex("mj9_2_2")),
                        data.getString(data.getColumnIndex("mj10"))
                ))
                data.moveToNext()
            }
            return PaperArray
        }

        fun Return_COGNITIVE(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_COGNITIVE>
        {
            var PaperArray = ArrayList<Paper_COGNITIVE>()

            val data = LocalDBhelper(activity).Select_Local_COGNITIVE(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_COGNITIVE(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("mj_inji_1")),
                        data.getString(data.getColumnIndex("mj_inji_2")),
                        data.getString(data.getColumnIndex("mj_inji_3")),
                        data.getString(data.getColumnIndex("mj_inji_4")),
                        data.getString(data.getColumnIndex("mj_inji_5")),
                        data.getString(data.getColumnIndex("mj_inji_6")),
                        data.getString(data.getColumnIndex("mj_inji_7")),
                        data.getString(data.getColumnIndex("mj_inji_8")),
                        data.getString(data.getColumnIndex("mj_inji_9")),
                        data.getString(data.getColumnIndex("mj_inji_10")),
                        data.getString(data.getColumnIndex("mj_inji_11")),
                        data.getString(data.getColumnIndex("mj_inji_12")),
                        data.getString(data.getColumnIndex("mj_inji_13")),
                        data.getString(data.getColumnIndex("mj_inji_14")),
                        data.getString(data.getColumnIndex("mj_inji_15")),
                        data.getString(data.getColumnIndex("mj_inji_sum"))
                ))
                data.moveToNext()
            }

            return PaperArray
        }


        fun Return_MENTAL(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_MENTAL>
        {
            var PaperArray = ArrayList<Paper_MENTAL>()

            val data = LocalDBhelper(activity).Select_Local_MENTAL(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_MENTAL(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("mj_mtl_1")),
                        data.getString(data.getColumnIndex("mj_mtl_2")),
                        data.getString(data.getColumnIndex("mj_mtl_3")),
                        data.getString(data.getColumnIndex("mj_mtl_4")),
                        data.getString(data.getColumnIndex("mj_mtl_5")),
                        data.getString(data.getColumnIndex("mj_mtl_6")),
                        data.getString(data.getColumnIndex("mj_mtl_7")),
                        data.getString(data.getColumnIndex("mj_mtl_8")),
                        data.getString(data.getColumnIndex("mj_mtl_9")),
                        data.getString(data.getColumnIndex("mj_mtl_sum"))
                ))
                data.moveToNext()
            }

            return PaperArray
        }


        fun Return_ELDERLY(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_ELDERLY>
        {
            var PaperArray = ArrayList<Paper_ELDERLY>()

            val data = LocalDBhelper(activity).Select_Local_ELDERLY(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_ELDERLY(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("mj66_1")),
                        data.getString(data.getColumnIndex("mj66_2")),
                        data.getString(data.getColumnIndex("mj66_3_1")),
                        data.getString(data.getColumnIndex("mj66_3_2")),
                        data.getString(data.getColumnIndex("mj66_3_3")),
                        data.getString(data.getColumnIndex("mj66_3_4")),
                        data.getString(data.getColumnIndex("mj66_3_5")),
                        data.getString(data.getColumnIndex("mj66_3_6")),
                        data.getString(data.getColumnIndex("mj66_4")),
                        data.getString(data.getColumnIndex("mj66_5"))
                ))
                data.moveToNext()
            }

            return PaperArray
        }

        fun Return_SMOKING(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_SMOKING>
        {
            var PaperArray = ArrayList<Paper_SMOKING>()

            val data = LocalDBhelper(activity).Select_Local_SMOKING(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_SMOKING(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("sg2_spSmoke1")),
                        data.getString(data.getColumnIndex("sg2_spSmoke2")),
                        data.getString(data.getColumnIndex("sg2_spSmoke3")),
                        data.getString(data.getColumnIndex("sg2_spSmoke4")),
                        data.getString(data.getColumnIndex("sg2_spSmoke5")),
                        data.getString(data.getColumnIndex("sg2_spSmoke6")),
                        data.getString(data.getColumnIndex("sg2_spSmoke7")),
                        data.getString(data.getColumnIndex("sg2_spSmoke8")),
                        data.getString(data.getColumnIndex("sg2_spSmokeSum"))
                ))
                data.moveToNext()
            }

            return PaperArray
        }

        fun Return_DRINKING(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_DRINKING>
        {
            var PaperArray = ArrayList<Paper_DRINKING>()

            val data = LocalDBhelper(activity).Select_Local_DRINKING(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_DRINKING(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("sg2_spDrink1")),
                        data.getString(data.getColumnIndex("sg2_spDrink2_1")),
                        data.getString(data.getColumnIndex("sg2_spDrink2_2")),
                        data.getString(data.getColumnIndex("sg2_spDrink3")),
                        data.getString(data.getColumnIndex("sg2_spDrink4")),
                        data.getString(data.getColumnIndex("sg2_spDrink5")),
                        data.getString(data.getColumnIndex("sg2_spDrink6")),
                        data.getString(data.getColumnIndex("sg2_spDrink7")),
                        data.getString(data.getColumnIndex("sg2_spDrink8")),
                        data.getString(data.getColumnIndex("sg2_spDrink9")),
                        data.getString(data.getColumnIndex("sg2_spDrink10")),
                        data.getString(data.getColumnIndex("sg2_spDrinkSum"))
                ))
                data.moveToNext()
            }

            return PaperArray
        }

        fun Return_CANCER(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_CANCER>
        {
            var Paper = ArrayList<Paper_CANCER>()

            val data = LocalDBhelper(activity).Select_Local_CANCER(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                Paper.add(Paper_CANCER(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("ck1")),
                        data.getString(data.getColumnIndex("ck1_1")),
                        data.getString(data.getColumnIndex("ck2")),
                        data.getString(data.getColumnIndex("ck2_1")),
                        data.getString(data.getColumnIndex("ck3_1")),
                        data.getString(data.getColumnIndex("ck3_1_1")),
                        data.getString(data.getColumnIndex("ck3_1_2")),
                        data.getString(data.getColumnIndex("ck3_1_3")),
                        data.getString(data.getColumnIndex("ck3_1_4")),
                        data.getString(data.getColumnIndex("ck3_1_5")),
                        data.getString(data.getColumnIndex("ck3_2")),
                        data.getString(data.getColumnIndex("ck3_2_1")),
                        data.getString(data.getColumnIndex("ck3_2_2")),
                        data.getString(data.getColumnIndex("ck3_2_3")),
                        data.getString(data.getColumnIndex("ck3_2_4")),
                        data.getString(data.getColumnIndex("ck3_2_5")),
                        data.getString(data.getColumnIndex("ck3_3")),
                        data.getString(data.getColumnIndex("ck3_3_1")),
                        data.getString(data.getColumnIndex("ck3_3_2")),
                        data.getString(data.getColumnIndex("ck3_3_3")),
                        data.getString(data.getColumnIndex("ck3_3_4")),
                        data.getString(data.getColumnIndex("ck3_3_5")),
                        data.getString(data.getColumnIndex("ck3_4")),
                        data.getString(data.getColumnIndex("ck3_4_1")),
                        data.getString(data.getColumnIndex("ck3_4_2")),
                        data.getString(data.getColumnIndex("ck3_4_3")),
                        data.getString(data.getColumnIndex("ck3_4_4")),
                        data.getString(data.getColumnIndex("ck3_4_5")),
                        data.getString(data.getColumnIndex("ck3_5")),
                        data.getString(data.getColumnIndex("ck3_5_1")),
                        data.getString(data.getColumnIndex("ck3_5_2")),
                        data.getString(data.getColumnIndex("ck3_5_3")),
                        data.getString(data.getColumnIndex("ck3_5_4")),
                        data.getString(data.getColumnIndex("ck3_5_5")),
                        data.getString(data.getColumnIndex("ck3_6")),
                        data.getString(data.getColumnIndex("ck3_6_1")),
                        data.getString(data.getColumnIndex("ck3_6_2")),
                        data.getString(data.getColumnIndex("ck3_6_3")),
                        data.getString(data.getColumnIndex("ck3_6_4")),
                        data.getString(data.getColumnIndex("ck3_6_5")),
                        data.getString(data.getColumnIndex("ck3_6_kita")),
                        data.getString(data.getColumnIndex("ck4_1")),
                        data.getString(data.getColumnIndex("ck4_2")),
                        data.getString(data.getColumnIndex("ck4_3")),
                        data.getString(data.getColumnIndex("ck4_4")),
                        data.getString(data.getColumnIndex("ck4_5")),
                        data.getString(data.getColumnIndex("ck4_6")),
                        data.getString(data.getColumnIndex("ck4_7")),
                        data.getString(data.getColumnIndex("ck4_8")),
                        data.getString(data.getColumnIndex("ck4_9")),
                        data.getString(data.getColumnIndex("ck5_1")),
                        data.getString(data.getColumnIndex("ck5_2")),
                        data.getString(data.getColumnIndex("ck5_3")),
                        data.getString(data.getColumnIndex("ck5_4")),
                        data.getString(data.getColumnIndex("ck5_5")),
                        data.getString(data.getColumnIndex("ck6_1")),
                        data.getString(data.getColumnIndex("ck6_2")),
                        data.getString(data.getColumnIndex("ck6_3")),
                        data.getString(data.getColumnIndex("ck6_4")),
                        data.getString(data.getColumnIndex("ck6_5")),
                        data.getString(data.getColumnIndex("ck7_1")),
                        data.getString(data.getColumnIndex("ck7_2")),
                        data.getString(data.getColumnIndex("ck7_3")),
                        data.getString(data.getColumnIndex("ck7_4")),
                        data.getString(data.getColumnIndex("ck7_5")),
                        data.getString(data.getColumnIndex("ck8_1")),
                        data.getString(data.getColumnIndex("ck8_2")),
                        data.getString(data.getColumnIndex("ck9_1")),
                        data.getString(data.getColumnIndex("ck9_2")),
                        data.getString(data.getColumnIndex("ck10")),
                        data.getString(data.getColumnIndex("ck11")),
                        data.getString(data.getColumnIndex("ck12")),
                        data.getString(data.getColumnIndex("ck13")),
                        data.getString(data.getColumnIndex("ck14")),
                        data.getString(data.getColumnIndex("ck15_5")),
                        data.getString(data.getColumnIndex("ck15_5_1")),
                        data.getString(data.getColumnIndex("ck15_5_2")),
                        data.getString(data.getColumnIndex("ck15_5_3")),
                        data.getString(data.getColumnIndex("ck15_5_4")),
                        data.getString(data.getColumnIndex("ck15_5_5")),
                        data.getString(data.getColumnIndex("ck16_1")),
                        data.getString(data.getColumnIndex("ck16_2")),
                        data.getString(data.getColumnIndex("ck16_3")),
                        data.getString(data.getColumnIndex("ck16_4")),
                        data.getString(data.getColumnIndex("ck16_5")),
                        data.getString(data.getColumnIndex("ck16_6"))
                ))
                data.moveToNext()
            }
            return Paper
        }

        fun Return_EXERCISE(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_EXERCISE>
        {
            var PaperArray = ArrayList<Paper_EXERCISE>()

            val data = LocalDBhelper(activity).Select_Local_EXERCISE(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_EXERCISE(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("sg2_spSports1_1")),
                        data.getString(data.getColumnIndex("sg2_spSports1_2")),
                        data.getString(data.getColumnIndex("sg2_spSports1_3_1")),
                        data.getString(data.getColumnIndex("sg2_spSports1_3_2")),
                        data.getString(data.getColumnIndex("sg2_spSports1_4")),
                        data.getString(data.getColumnIndex("sg2_spSports1_5")),
                        data.getString(data.getColumnIndex("sg2_spSports1_6_1")),
                        data.getString(data.getColumnIndex("sg2_spSports1_6_2")),
                        data.getString(data.getColumnIndex("sg2_spSports2_1")),
                        data.getString(data.getColumnIndex("sg2_spSports2_2")),
                        data.getString(data.getColumnIndex("sg2_spSports2_3_1")),
                        data.getString(data.getColumnIndex("sg2_spSports2_3_2")),
                        data.getString(data.getColumnIndex("sg2_spSports3_1")),
                        data.getString(data.getColumnIndex("sg2_spSports3_2")),
                        data.getString(data.getColumnIndex("sg2_spSports3_3_1")),
                        data.getString(data.getColumnIndex("sg2_spSports3_3_2")),
                        data.getString(data.getColumnIndex("sg2_spSports3_4")),
                        data.getString(data.getColumnIndex("sg2_spSports3_5")),
                        data.getString(data.getColumnIndex("sg2_spSports3_6_1")),
                        data.getString(data.getColumnIndex("sg2_spSports3_6_2")),
                        data.getString(data.getColumnIndex("sg2_spSports4_1_1")),
                        data.getString(data.getColumnIndex("sg2_spSports4_1_2")),
                        data.getString(data.getColumnIndex("sg2_spSports5")),
                        data.getString(data.getColumnIndex("sg2_spSports6")),
                        data.getString(data.getColumnIndex("sg2_spSports7")),
                        data.getString(data.getColumnIndex("sg2_spSports8")),
                        data.getString(data.getColumnIndex("sg2_spSports9")),
                        data.getString(data.getColumnIndex("sg2_spSports10")),
                        data.getString(data.getColumnIndex("sg2_spSports11")),
                        data.getString(data.getColumnIndex("sg2_spSports12")),
                        data.getString(data.getColumnIndex("sg2_spSportsSum"))
                ))
                data.moveToNext()
            }

            return PaperArray
        }

        fun Return_NUTRITION(exam_no:String, name:String, jumin:String, sql_db:SQLiteDatabase, activity: Activity): ArrayList<Paper_NUTRITION> {
            var PaperArray = ArrayList<Paper_NUTRITION>()

            val data = LocalDBhelper(activity).Select_Local_NUTRITION(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_NUTRITION(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("sg2_spFood1")),
                        data.getString(data.getColumnIndex("sg2_spFood2")),
                        data.getString(data.getColumnIndex("sg2_spFood3")),
                        data.getString(data.getColumnIndex("sg2_spFood4")),
                        data.getString(data.getColumnIndex("sg2_spFood5")),
                        data.getString(data.getColumnIndex("sg2_spFood6")),
                        data.getString(data.getColumnIndex("sg2_spFood7")),
                        data.getString(data.getColumnIndex("sg2_spFood8")),
                        data.getString(data.getColumnIndex("sg2_spFood9")),
                        data.getString(data.getColumnIndex("sg2_spFood10")),
                        data.getString(data.getColumnIndex("sg2_spFood11")),
                        data.getString(data.getColumnIndex("sg2_spFoodSum")),
                        data.getString(data.getColumnIndex("sg2_spFatHeight")),
                        data.getString(data.getColumnIndex("sg2_spFatWeight")),
                        data.getString(data.getColumnIndex("sg2_spFatWaistSize")),
                        data.getString(data.getColumnIndex("sg2_spFatBmi")),
                        data.getString(data.getColumnIndex("sg2_spFat1")),
                        data.getString(data.getColumnIndex("sg2_spFat2")),
                        data.getString(data.getColumnIndex("sg2_spFat3"))
                ))
                data.moveToNext()
            }

            return PaperArray
        }
    }
}