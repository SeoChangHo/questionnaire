package com.example.zzango.questionnaire

import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.ArrayList

interface OracleInterface {

    @POST("https://finepaperweight.herokuapp.com/insertOral")
    @Headers("Content-type: application/json")
    fun oracleServer(@Body examInfo: ArrayList<OralExamination.ExamInfo>) : Call<String>

}