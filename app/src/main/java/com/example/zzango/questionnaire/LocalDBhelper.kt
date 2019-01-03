package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.zzango.questionnaire.LocalList.CustomAdapter
import com.example.zzango.questionnaire.LocalList.Paper
import java.util.*

class LocalDBhelper(context : Context) : SQLiteOpenHelper(context, "oraltest.db", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "LOCALSAVELIST" +
                "(no INTEGER," +
                "category TEXT," +
                "name TEXT," +
                "date TEXT);")

    }

    fun oralCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "ORAL_EXAM" +
                "(exam_date DATETIME," +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
//                " oral_bun_no INTEGER," +
//                " oral_email_yn TEXT," +
                " oral_1 TEXT," +
                " oral_2 TEXT," +
                " oral_3 TEXT," +
                " oral_4 TEXT," +
                " oral_5 TEXT," +
                " oral_6 TEXT," +
                " oral_7 TEXT," +
                " oral_8 TEXT," +
                " oral_9 TEXT," +
                " oral_10 TEXT," +
                " oral_11 TEXT," +
                " oral_12 TEXT," +
                " oral_13 TEXT," +
                " oral_14 TEXT," +
                " oral_15 TEXT," +
                " remark TEXT);")

    }

    fun commonExaminationDB(db: SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "COMMON_EXAM" +
                "(exam_date DATETIME," +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " mj1_1_1 TEXT," +
                " mj1_1_2 TEXT," +
                " mj1_2_1 TEXT," +
                " mj1_2_2 TEXT," +
                " mj1_3_1 TEXT," +
                " mj1_3_2 TEXT," +
                " mj1_4_1 TEXT," +
                " mj1_4_2 TEXT," +
                " mj1_5_1 TEXT," +
                " mj1_5_2 TEXT," +
                " mj1_6_1 TEXT," +
                " mj1_6_2 TEXT," +
                " mj1_7_1 TEXT," +
                " mj1_7_2 TEXT," +
                " mj1_7_etc TEXT," +
                " mj2_1 TEXT," +
                " mj2_2 TEXT," +
                " mj2_3 TEXT," +
                " mj2_4 TEXT," +
                " mj2_5 TEXT," +
                " mj3 TEXT," +
                " mj4 TEXT," +
                " mj4_1_1 TEXT," +
                " mj4_1_2 TEXT," +
                " mj4_2_1 TEXT," +
                " mj4_2_2 TEXT," +
                " mj4_2_3 TEXT," +
                " mj5 TEXT," +
                " mj5_1_1 TEXT," +
                " mj5_1_2 TEXT," +
                " mj5_2_1 TEXT," +
                " mj5_2_2 TEXT," +
                " mj5_2_3 TEXT," +
                " mj6 TEXT," +
                " mj6_1 TEXT," +
                " mj71 TEXT," +
                " mj72 TEXT," +
                " mj73 TEXT," +
                " mj74 TEXT," +
                " mj7_1_11 TEXT," +
                " mj7_1_12 TEXT," +
                " mj7_1_13 TEXT," +
                " mj7_1_14 TEXT," +
                " mj7_1_21 TEXT," +
                " mj7_1_22 TEXT," +
                " mj7_1_23 TEXT," +
                " mj7_1_24 TEXT," +
                " mj7_1_31 TEXT," +
                " mj7_1_32 TEXT," +
                " mj7_1_33 TEXT," +
                " mj7_1_34 TEXT," +
                " mj7_1_41 TEXT," +
                " mj7_1_42 TEXT," +
                " mj7_1_43 TEXT," +
                " mj7_1_44 TEXT," +
                " mj7_1_51 TEXT," +
                " mj7_1_52 TEXT," +
                " mj7_1_53 TEXT," +
                " mj7_1_54 TEXT," +
                " mj7_1_etc TEXT," +
                " mj7_2_11 TEXT," +
                " mj7_2_12 TEXT," +
                " mj7_2_13 TEXT," +
                " mj7_2_14 TEXT," +
                " mj7_2_21 TEXT," +
                " mj7_2_22 TEXT," +
                " mj7_2_23 TEXT," +
                " mj7_2_24 TEXT," +
                " mj7_2_31 TEXT," +
                " mj7_2_32 TEXT," +
                " mj7_2_33 TEXT," +
                " mj7_2_34 TEXT," +
                " mj7_2_41 TEXT," +
                " mj7_2_42 TEXT," +
                " mj7_2_43 TEXT," +
                " mj7_2_44 TEXT," +
                " mj7_2_51 TEXT," +
                " mj7_2_52 TEXT," +
                " mj7_2_53 TEXT," +
                " mj7_2_54 TEXT," +
                " mj7_2_etc TEXT," +
                " mj8_1 TEXT," +
                " mj8_2_1 TEXT," +
                " mj8_2_2 TEXT," +
                " mj9_1 TEXT," +
                " mj9_2_1 TEXT," +
                " mj9_2_2 TEXT," +
                " mj10 TEXT);")

    }

    fun mentalCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "MENTAL_EXAM" +
                "(exam_date DATETIME," +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " mj_mtl_1 TEXT," +
                " mj_mtl_2 TEXT," +
                " mj_mtl_3 TEXT," +
                " mj_mtl_4 TEXT," +
                " mj_mtl_5 TEXT," +
                " mj_mtl_6 TEXT," +
                " mj_mtl_7 TEXT," +
                " mj_mtl_8 TEXT," +
                " mj_mtl_9 TEXT," +
                " mj_mtl_sum TEXT);")

    }

    fun cognitiveCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "COGNITIVE_EXAM" +
                "(exam_date DATETIME," +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " mj_inji_1 TEXT," +
                " mj_inji_2 TEXT," +
                " mj_inji_3 TEXT," +
                " mj_inji_4 TEXT," +
                " mj_inji_5 TEXT," +
                " mj_inji_6 TEXT," +
                " mj_inji_7 TEXT," +
                " mj_inji_8 TEXT," +
                " mj_inji_9 TEXT," +
                " mj_inji_10 TEXT," +
                " mj_inji_11 TEXT," +
                " mj_inji_12 TEXT," +
                " mj_inji_13 TEXT," +
                " mj_inji_14 TEXT," +
                " mj_inji_15 TEXT," +
                " mj_inji_sum TEXT);")

    }

    fun elderlyCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "ELDERLY_EXAM" +
                "(exam_date DATETIME," +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " mj66_1 TEXT," +
                " mj66_2 TEXT," +
                " mj66_3_1 TEXT," +
                " mj66_3_2 TEXT," +
                " mj66_3_3 TEXT," +
                " mj66_3_4 TEXT," +
                " mj66_3_5 TEXT," +
                " mj66_3_6 TEXT," +
                " mj66_4 TEXT," +
                " mj66_5 TEXT);")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun oralSaveLocal(db : SQLiteDatabase, ex : ArrayList<OralExamination.ExamInfo>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO LOCALSAVELIST" +
                "(no, category, name, date)" +
                " VALUES (" +
                " 123, '${columnValue.category}', '${columnValue.name}', '${columnValue.exam_date}');")

        db.execSQL("INSERT INTO ORAL_EXAM" +
                "(exam_date," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "oral_1, oral_2, oral_3, oral_4, oral_5, oral_6, oral_7," +
                "oral_8, oral_9, oral_10, oral_11, oral_12, oral_13, oral_14," +
                "oral_15, remark)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.oral_1}', '${columnValue.oral_2}', '${columnValue.oral_3}', '${columnValue.oral_4}'" +
                ", '${columnValue.oral_5}', '${columnValue.oral_6}', '${columnValue.oral_7}', '${columnValue.oral_8}'," +
                " '${columnValue.oral_9}', '${columnValue.oral_10}', '${columnValue.oral_11}', '${columnValue.oral_12}'," +
                " '${columnValue.oral_13}', '${columnValue.oral_14}', '${columnValue.oral_15}', '${columnValue.oral_20}'"
                +");")

    }

    fun commonSaveLocal(db : SQLiteDatabase, ex : ArrayList<CommonExaminationActivity.ExamInfo>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO LOCALSAVELIST" +
                "(no, category, name, date)" +
                " VALUES (" +
                " 123, '${columnValue.category}', '${columnValue.name}', '${columnValue.exam_date}');")

        db.execSQL("INSERT INTO COMMON_EXAM" +
                "(exam_date, name, first_serial, last_serial, category, " +
                " mj1_1_1, mj1_1_2, mj1_2_1, mj1_2_2, mj1_3_1, mj1_3_2, mj1_4_1, mj1_4_2," +
                " mj1_5_1, mj1_5_2, mj1_6_1, mj1_6_2, mj1_7_1, mj1_7_2, mj2_1," +
                " mj2_2, mj2_3, mj2_4, mj2_5, mj3, mj4, mj4_1_1, mj4_1_2, mj4_2_1," +
                " mj4_2_2, mj4_2_3, mj5, mj5_1_1, mj5_1_2, mj5_2_1, mj5_2_2, mj5_2_3," +
                " mj6, mj6_1, mj71, mj72, mj73, mj74, mj7_1_11, mj7_1_12, mj7_1_13, mj7_1_14," +
                " mj7_1_21, mj7_1_22, mj7_1_23, mj7_1_24, mj7_1_31, mj7_1_32, mj7_1_33," +
                " mj7_1_34, mj7_1_41, mj7_1_42, mj7_1_43, mj7_1_44, mj7_1_51, mj7_1_52, mj7_1_53," +
                " mj7_1_54, mj7_1_etc, mj7_2_11, mj7_2_12, mj7_2_13, mj7_2_14, mj7_2_21, mj7_2_22," +
                " mj7_2_23, mj7_2_24, mj7_2_31, mj7_2_32, mj7_2_33, mj7_2_34, mj7_2_41, mj7_2_42," +
                " mj7_2_43, mj7_2_44, mj7_2_51, mj7_2_52, mj7_2_53, mj7_2_54, mj7_2_etc, mj8_1," +
                " mj8_2_1, mj8_2_2, mj9_1, mj9_2_1, mj9_2_2, mj10)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.mj1_1_1}', '${columnValue.mj1_1_2}', '${columnValue.mj1_2_1}'" +
                ", '${columnValue.mj1_2_2}', '${columnValue.mj1_3_1}', '${columnValue.mj1_3_2}', '${columnValue.mj1_4_1}'" +
                ", '${columnValue.mj1_4_2}', '${columnValue.mj1_5_1}', '${columnValue.mj1_5_2}', '${columnValue.mj1_6_1}'" +
                ", '${columnValue.mj1_6_2}', '${columnValue.mj1_7_1}', '${columnValue.mj1_7_2}'" +
                ", '${columnValue.mj2_1}', '${columnValue.mj2_2}', '${columnValue.mj2_3}', '${columnValue.mj2_4}'" +
                ", '${columnValue.mj2_5}', '${columnValue.mj3}', '${columnValue.mj4}'" +
                ", '${columnValue.mj4_1_1}', '${columnValue.mj4_1_2}', '${columnValue.mj4_2_1}', '${columnValue.mj4_2_2}'" +
                ", '${columnValue.mj4_2_3}', '${columnValue.mj5}', '${columnValue.mj5_1_1}'" +
                ", '${columnValue.mj5_1_2}', '${columnValue.mj5_2_1}', '${columnValue.mj5_2_2}', '${columnValue.mj5_2_3}'" +
                ", '${columnValue.mj6}', '${columnValue.mj6_1}', '${columnValue.mj71}', '${columnValue.mj72}'" +
                ", '${columnValue.mj73}', '${columnValue.mj74}', '${columnValue.mj7_1_11}', '${columnValue.mj7_1_12}'" +
                ", '${columnValue.mj7_1_13}', '${columnValue.mj7_1_14}', '${columnValue.mj7_1_21}', '${columnValue.mj7_1_22}'" +
                ", '${columnValue.mj7_1_23}', '${columnValue.mj7_1_24}', '${columnValue.mj7_1_31}', '${columnValue.mj7_1_32}'" +
                ", '${columnValue.mj7_1_33}', '${columnValue.mj7_1_34}', '${columnValue.mj7_1_41}', '${columnValue.mj7_1_42}'" +
                ", '${columnValue.mj7_1_43}', '${columnValue.mj7_1_44}', '${columnValue.mj7_1_51}', '${columnValue.mj7_1_52}'" +
                ", '${columnValue.mj7_1_53}', '${columnValue.mj7_1_54}', '${columnValue.mj7_1_etc}', '${columnValue.mj7_2_11}'" +
                ", '${columnValue.mj7_2_12}', '${columnValue.mj7_2_13}', '${columnValue.mj7_2_14}', '${columnValue.mj7_2_21}'" +
                ", '${columnValue.mj7_2_22}', '${columnValue.mj7_2_23}', '${columnValue.mj7_2_24}', '${columnValue.mj7_2_31}'" +
                ", '${columnValue.mj7_2_32}', '${columnValue.mj7_2_33}', '${columnValue.mj7_2_34}', '${columnValue.mj7_2_41}'" +
                ", '${columnValue.mj7_2_42}', '${columnValue.mj7_2_43}', '${columnValue.mj7_2_44}', '${columnValue.mj7_2_51}'" +
                ", '${columnValue.mj7_2_52}', '${columnValue.mj7_2_53}', '${columnValue.mj7_2_54}', '${columnValue.mj7_2_etc}'" +
                ", '${columnValue.mj8_1}', '${columnValue.mj8_2_1}', '${columnValue.mj8_2_2}', '${columnValue.mj9_1}'" +
                ", '${columnValue.mj9_2_1}', '${columnValue.mj9_2_2}', '${columnValue.mj10}');")
    }


    fun mentalSaveLocal(db : SQLiteDatabase, ex : ArrayList<MentalExaminationActivity.ExamInfo>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO LOCALSAVELIST" +
                "(no, category, name, date)" +
                " VALUES (" +
                " 123, '${columnValue.category}', '${columnValue.name}', '${columnValue.exam_date}');")

        db.execSQL("INSERT INTO MENTAL_EXAM" +
                "(exam_date," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "mj_mtl_1, mj_mtl_2, mj_mtl_3, mj_mtl_4, mj_mtl_5, mj_mtl_6, mj_mtl_7," +
                "mj_mtl_8, mj_mtl_9, mj_mtl_sum)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.mj_mtl_1}', '${columnValue.mj_mtl_2}', '${columnValue.mj_mtl_3}', '${columnValue.mj_mtl_4}'" +
                ", '${columnValue.mj_mtl_5}', '${columnValue.mj_mtl_6}', '${columnValue.mj_mtl_7}', '${columnValue.mj_mtl_8}'" +
                ", '${columnValue.mj_mtl_9}', '${columnValue.mj_mtl_sum}');")

    }

    fun cognitiveSaveLocal(db : SQLiteDatabase, ex : ArrayList<CognitiveExaminationActivity.ExamInfo>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO LOCALSAVELIST" +
                "(no, category, name, date)" +
                " VALUES (" +
                " 123, '${columnValue.category}', '${columnValue.name}', '${columnValue.exam_date}');")

        db.execSQL("INSERT INTO COGNITIVE_EXAM" +
                "(exam_date," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "mj_inji_1, mj_inji_2, mj_inji_3, mj_inji_4, mj_inji_5, mj_inji_6, mj_inji_7," +
                "mj_inji_8, mj_inji_9, mj_inji_10, mj_inji_11, mj_inji_12, mj_inji_13, mj_inji_14, mj_inji_15, mj_inji_sum)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.mj_inji_1}', '${columnValue.mj_inji_2}', '${columnValue.mj_inji_3}', '${columnValue.mj_inji_4}'" +
                ", '${columnValue.mj_inji_5}', '${columnValue.mj_inji_6}', '${columnValue.mj_inji_7}', '${columnValue.mj_inji_8}'" +
                ", '${columnValue.mj_inji_9}', '${columnValue.mj_inji_10}', '${columnValue.mj_inji_11}', '${columnValue.mj_inji_12}'" +
                ", '${columnValue.mj_inji_13}', '${columnValue.mj_inji_14}', '${columnValue.mj_inji_15}', '${columnValue.mj_inji_sum}');")

    }

    fun elderlySaveLocal(db : SQLiteDatabase, ex : ArrayList<ElderlyExaminationActivity.ExamInfo>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO LOCALSAVELIST" +
                "(no, category, name, date)" +
                " VALUES (" +
                " 123, '${columnValue.category}', '${columnValue.name}', '${columnValue.exam_date}');")

        db.execSQL("INSERT INTO ELDERLY_EXAM" +
                "(exam_date," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "mj66_1, mj66_2, mj66_3_1, mj66_3_2, mj66_3_3, mj66_3_4, mj66_3_5," +
                "mj66_3_6, mj66_4, mj66_5)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.mj66_1}', '${columnValue.mj66_2}', '${columnValue.mj66_3_1}', '${columnValue.mj66_3_2}'" +
                ", '${columnValue.mj66_3_3}', '${columnValue.mj66_3_4}', '${columnValue.mj66_3_5}', '${columnValue.mj66_3_6}'" +
                ", '${columnValue.mj66_4}', '${columnValue.mj66_5}');")
    }


    @SuppressLint("Recycle")
    fun checkLocal(db : SQLiteDatabase): Cursor{

        var data = db.rawQuery("SELECT * FROM EXAMINATION;", null)



        return data

    }

    @SuppressLint("Recycle")
    fun checkLocalList(db : SQLiteDatabase): Cursor{

        var data = db.rawQuery("SELECT * FROM LOCALSAVELIST;", null)



        return data

    }

    fun deletePaper(db : SQLiteDatabase, Paper: ArrayList<Paper>)
    {
        for(i in 0..Paper.size-1)
        {
            db.delete("LOCALSAVELIST", "date=?", arrayOf(Paper[i].date))


            when (Paper[i].category)
            {
                CustomAdapter.Category.COMMON -> {
                    println("공통검진입니다.")
                    db.delete("EXAMINATION", "exam_date=?", arrayOf(Paper[i].date))
                }
                CustomAdapter.Category.ORAL -> {
                    println("구강검진입니다.")
                    db.delete("COMMON_EXAM", "exam_date=?", arrayOf(Paper[i].date))
                }
                else -> {
                    println("확인불가")
                }
            }





        }

        db.close()
    }
}