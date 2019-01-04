package com.example.zzango.questionnaire

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


class DrinkingExaminationActivity : AppCompatActivity(){

    var exam_result : ArrayList<SmokingExaminationActivity.ExamInfo>? = null
    var sql_db : SQLiteDatabase? = null
    var popup = false

    data class ExamInfo (@SerializedName("exam_date") @Expose var exam_date : String,
                         @SerializedName("exam_bun_no") @Expose var exam_bun_no : String,
                         @SerializedName("exam_email_yn") @Expose var exam_email_yn : String,
                         @SerializedName("name") @Expose var name : String,
                         @SerializedName("first_serial") @Expose var first_serial : String,
                         @SerializedName("last_serial") @Expose var last_serial : String,
                         @SerializedName("category") @Expose var category : String,
                         @SerializedName("sg2_spSmoke1") @Expose var sg2_spSmoke1 : String,
                         @SerializedName("sg2_spSmoke2") @Expose var sg2_spSmoke2 : String,
                         @SerializedName("sg2_spSmoke3") @Expose var sg2_spSmoke3 : String,
                         @SerializedName("sg2_spSmoke4") @Expose var sg2_spSmoke4 : String,
                         @SerializedName("sg2_spSmoke5") @Expose var sg2_spSmoke5 : String,
                         @SerializedName("sg2_spSmoke6") @Expose var sg2_spSmoke6 : String,
                         @SerializedName("sg2_spSmoke7") @Expose var sg2_spSmoke7 : String,
                         @SerializedName("sg2_spSmoke8") @Expose var sg2_spSmoke8 : String,
                         @SerializedName("sg2_spSmokeSum") @Expose var sg2_spSmokeSum : String)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinking_exam)




    }

}