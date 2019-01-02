package com.example.zzango.questionnaire

import com.example.zzango.questionnaire.LocalList.Paper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OracleInterface {

    @POST("https://finepaperweight.herokuapp.com/insertOral")
    @Headers("Content-type: application/json")
    fun oracleServer(@Body examInfo: ArrayList<OralExamination.ExamInfo>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/insertOral")
    @Headers("Content-type: application/json")
    fun oracleServer1(@Body examInfo: ArrayList<Paper>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertNormal")
    @Headers("Content-type: application/json")
    fun commonServer(@Body examInfo: ArrayList<CommonExaminationActivity.ExamInfo>) : Call<String>

}