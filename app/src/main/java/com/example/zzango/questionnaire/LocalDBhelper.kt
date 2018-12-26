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