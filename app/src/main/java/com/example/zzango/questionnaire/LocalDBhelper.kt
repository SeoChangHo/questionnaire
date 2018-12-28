package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
                "EXAMINATION" +
                "(exam_date DATETIME," +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
//                " exam_bun_no INTEGER," +
//                " exam_email_yn TEXT," +
                " exam_1 TEXT," +
                " exam_2 TEXT," +
                " exam_3 TEXT," +
                " exam_4 TEXT," +
                " exam_5 TEXT," +
                " exam_6 TEXT," +
                " exam_7 TEXT," +
                " exam_8 TEXT," +
                " exam_9 TEXT," +
                " exam_10 TEXT," +
                " exam_11 TEXT," +
                " exam_12 TEXT," +
                " exam_13 TEXT," +
                " exam_14 TEXT," +
                " exam_15 TEXT," +
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
                " mj2_etc TEXT," +
                " mj3 TEXT," +
                " mj4 TEXT," +
                " mj4_1_1 TEXT," +
                " mj4_1_2 TEXT," +
                " mj4_2_1 TEXT," +
                " mj4_2_2 TEXT," +
                " mj5 TEXT," +
                " mj5_1 TEXT," +
                " mj61 TEXT," +
                " mj62 TEXT," +
                " mj63 TEXT," +
                " mj64 TEXT," +
                " mj6_1_11 TEXT," +
                " mj6_1_12 TEXT," +
                " mj6_1_13 TEXT," +
                " mj6_1_14 TEXT," +
                " mj6_1_21 TEXT," +
                " mj6_1_22 TEXT," +
                " mj6_1_23 TEXT," +
                " mj6_1_24 TEXT," +
                " mj6_1_31 TEXT," +
                " mj6_1_32 TEXT," +
                " mj6_1_33 TEXT," +
                " mj6_1_34 TEXT," +
                " mj6_1_41 TEXT," +
                " mj6_1_42 TEXT," +
                " mj6_1_43 TEXT," +
                " mj6_1_44 TEXT," +
                " mj6_1_51 TEXT," +
                " mj6_1_52 TEXT," +
                " mj6_1_53 TEXT," +
                " mj6_1_54 TEXT," +
                " mj6_1_etc TEXT," +
                " mj6_2_11 TEXT," +
                " mj6_2_12 TEXT," +
                " mj6_2_13 TEXT," +
                " mj6_2_14 TEXT," +
                " mj6_2_21 TEXT," +
                " mj6_2_22 TEXT," +
                " mj6_2_23 TEXT," +
                " mj6_2_24 TEXT," +
                " mj6_2_31 TEXT," +
                " mj6_2_32 TEXT," +
                " mj6_2_33 TEXT," +
                " mj6_2_34 TEXT," +
                " mj6_2_41 TEXT," +
                " mj6_2_42 TEXT," +
                " mj6_2_43 TEXT," +
                " mj6_2_44 TEXT," +
                " mj6_2_51 TEXT," +
                " mj6_2_52 TEXT," +
                " mj6_2_53 TEXT," +
                " mj6_2_54 TEXT," +
                " mj6_2_etc TEXT," +
                " mj7_1 TEXT," +
                " mj7_2_1 TEXT," +
                " mj7_2_2 TEXT," +
                " mj8_1 TEXT," +
                " mj8_2_1 TEXT," +
                " mj8_2_2 TEXT," +
                " mj9 TEXT," +
                " mj66_1 TEXT," +
                " mj66_2 TEXT," +
                " mj66_3_1 TEXT," +
                " mj66_3_2 TEXT," +
                " mj66_3_3 TEXT," +
                " mj66_3_4 TEXT," +
                " mj66_3_5 TEXT," +
                " mj66_3_6 TEXT," +
                " mj66_4 TEXT," +
                " mj66_5 TEXT," +
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
                " mj_inji_sum TEXT," +
                " mj_mtl_1 TEXT," +
                " mj_mtl_2 TEXT," +
                " mj_mtl_3 TEXT," +
                " mj_mtl_4 TEXT," +
                " mj_mtl_5 TEXT," +
                " mj_mtl_6 TEXT," +
                " mj_mtl_7 TEXT," +
                " mj_mtl_8 TEXT," +
                " mj_mtl_9 TEXT," +
                " mj_mtl_sum TEXT," +
                " mj_key TEXT," +
                " mj_email TEXT," +
                " mj_email_yn TEXT," +
                " mj_year TEXT," +
                " mj_doc_no TEXT," +
                " mj_doc_name TEXT);")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun saveLocal(db : SQLiteDatabase, ex : ArrayList<OralExamination.ExamInfo>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO EXAMINATION" +
                "(exam_date," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "exam_1, exam_2, exam_3, exam_4, exam_5, exam_6, exam_7," +
                "exam_8, exam_9, exam_10, exam_11, exam_12, exam_13, exam_14," +
                "exam_15, remark)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.exam_1}', '${columnValue.exam_2}', '${columnValue.exam_3}', '${columnValue.exam_4}'" +
                ", '${columnValue.exam_5}', '${columnValue.exam_6}', '${columnValue.exam_7}', '${columnValue.exam_8}'," +
                " '${columnValue.exam_9}', '${columnValue.exam_10}', '${columnValue.exam_11}', '${columnValue.exam_12}'," +
                " '${columnValue.exam_13}', '${columnValue.exam_14}', '${columnValue.exam_15}', '${columnValue.exam_20}'"
                +");")

    }

    fun commonSaveLocal(db : SQLiteDatabase, ex : ArrayList<CommonExaminationActivity.ExamInfo>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO EXAMINATION" +
                "(exam_date," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "exam_1, exam_2, exam_3, exam_4, exam_5, exam_6, exam_7," +
                "exam_8, exam_9, exam_10, exam_11, exam_12, exam_13, exam_14," +
                "exam_15, remark)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.exam_1}', '${columnValue.exam_2}', '${columnValue.exam_3}', '${columnValue.exam_4}'" +
                ", '${columnValue.exam_5}', '${columnValue.exam_6}', '${columnValue.exam_7}', '${columnValue.exam_8}'," +
                " '${columnValue.exam_9}', '${columnValue.exam_10}', '${columnValue.exam_11}', '${columnValue.exam_12}'," +
                " '${columnValue.exam_13}', '${columnValue.exam_14}', '${columnValue.exam_15}', '${columnValue.exam_20}'"
                +");")

    }



    @SuppressLint("Recycle")
    fun checkLocal(db : SQLiteDatabase): Cursor{

        var data = db.rawQuery("SELECT * FROM EXAMINATION;", null)



        return data

    }

    fun deletePaper(db : SQLiteDatabase, Paper: ArrayList<Paper>)
    {
        for(i in 0..Paper.size-1)
        {
            db.delete("EXAMINATION", "name=?", arrayOf(Paper[i].name))
        }

        db.close()
    }

}