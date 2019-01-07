package com.example.zzango.questionnaire

import com.example.zzango.questionnaire.LocalList.Paper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OracleInterface {

    @POST("https://finepaperweight.herokuapp.com/insertOral")
    @Headers("Content-type: application/json")
    fun oracleServer(@Body examInfo: ArrayList<OralExaminationActivity.ExamInfo>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/insertOral")
    @Headers("Content-type: application/json")
    fun oracleServer1(@Body examInfo: ArrayList<Paper>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertNormal")
    @Headers("Content-type: application/json")
    fun commonServer(@Body examInfo: ArrayList<CommonExaminationActivity.ExamInfo>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertMental")
    @Headers("Content-type: application/json")
    fun mentalServer(@Body examInfo: ArrayList<MentalExaminationActivity.ExamInfo>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertCognitive")
    @Headers("Content-type: application/json")
    fun cognitiveServer(@Body examInfo: ArrayList<CognitiveExaminationActivity.ExamInfo>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertElderly")
    @Headers("Content-type: application/json")
    fun elderlyServer(@Body examInfo: ArrayList<ElderlyExaminationActivity.ExamInfo>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertSmoking")
    @Headers("Content-type: application/json")
    fun smokingServer(@Body examInfo: ArrayList<SmokingExaminationActivity.ExamInfo>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertDrinking")
    @Headers("Content-type: application/json")
    fun drinkingServer(@Body examInfo: ArrayList<DrinkingExaminationActivity.ExamInfo>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertCancer")
    @Headers("Content-type: application/json")
    fun cancerServer(@Body examInfo: ArrayList<CancerExaminationActivity.ExamInfo>) : Call<String>

}