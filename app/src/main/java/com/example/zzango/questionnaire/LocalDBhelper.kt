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
            db.delete("EXAMINATION", "name=?", arrayOf(Paper[i].name))
        }

        db.close()
    }

}