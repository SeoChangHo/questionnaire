package com.example.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class LocalDBhelper(context : Context) : SQLiteOpenHelper(context, "oraltest.db", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "ORAL_EXAM" +
                "(oral_date DATETIME," +
                " oral_bun_no INTEGER," +
                " oral_email_yn TEXT," +
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

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun saveLocal(db : SQLiteDatabase, ex : ArrayList<OralExamination.ExamInfo>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO ORAL_EXAM" +
                "(oral_date," +
                " oral_bun_no," +
                "oral_email_yn," +
                "oral_1, oral_2, oral_3, oral_4, oral_5, oral_6, oral_7," +
                "oral_8, oral_9, oral_10, oral_11, oral_12, oral_13, oral_14," +
                "oral_15, remark)" +
                " VALUES (" +
                "'${columnValue.oral_date}', '${columnValue.oral_bun_no}', '${columnValue.oral_email_yn}', '${columnValue.oral_1}', '${columnValue.oral_2}', '${columnValue.oral_3}', '${columnValue.oral_4}', '${columnValue.oral_5}', '${columnValue.oral_6}', '${columnValue.oral_7}'," +
                " '${columnValue.oral_8}', '${columnValue.oral_9}', '${columnValue.oral_10}', '${columnValue.oral_11}', '${columnValue.oral_12}', '${columnValue.oral_13}', '${columnValue.oral_14}', '${columnValue.oral_15}', '${columnValue.oral_20}'"+");")

    }

    @SuppressLint("Recycle")
    fun checkLocal(db : SQLiteDatabase){

        var data = db.rawQuery("SELECT * FROM ORAL_EXAM;", null)

        data.moveToFirst()

        while(!data.isAfterLast){

            println(data.getString(1))
            println(data.getString(2))
            println(data.getString(3))
            println(data.getString(4))
            println(data.getString(5))
            println(data.getString(6))
            println(data.getString(7))
            println(data.getString(8))
            println(data.getString(9))
            println(data.getString(10))
            println(data.getString(11))
            println(data.getString(12))
            println(data.getString(13))
            println(data.getString(14))
            println(data.getString(15))

            data.moveToNext()

        }

    }

}