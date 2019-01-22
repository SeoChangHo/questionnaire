package com.example.zzango.questionnaire

import com.example.zzango.questionnaire.LocalList.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OracleInterface {

    @POST("https://finepaperweight.herokuapp.com/insertOral")
    @Headers("Content-type: application/json")
    fun oracleServer(@Body examInfo: ArrayList<Paper_ORAL>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/insertOral")
    @Headers("Content-type: application/json")
    fun oracleServer1(@Body examInfo: ArrayList<Paper>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertNormal")
    @Headers("Content-type: application/json")
    fun commonServer(@Body examInfo: ArrayList<Paper_COMMON>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertMental")
    @Headers("Content-type: application/json")
    fun mentalServer(@Body examInfo: ArrayList<Paper_MENTAL>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertCognitive")
    @Headers("Content-type: application/json")
    fun cognitiveServer(@Body examInfo: ArrayList<Paper_COGNITIVE>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertElderly")
    @Headers("Content-type: application/json")
    fun elderlyServer(@Body examInfo: ArrayList<Paper_ELDERLY>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertSmoking")
    @Headers("Content-type: application/json")
    fun smokingServer(@Body examInfo: ArrayList<Paper_SMOKING>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertDrinking")
    @Headers("Content-type: application/json")
    fun drinkingServer(@Body examInfo: ArrayList<Paper_DRINKING>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertCancer")
    @Headers("Content-type: application/json")
    fun cancerServer(@Body examInfo: ArrayList<Paper_CANCER>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertExercise")
    @Headers("Content-type: application/json")
    fun exerciseServer(@Body examInfo: ArrayList<Paper_EXERCISE>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/InsertNutrition")
    @Headers("Content-type: application/json")
    fun nutritionServer(@Body examInfo: ArrayList<Paper_NUTRITION>) : Call<String>

    @POST("https://finepaperweight.herokuapp.com/SavePapers")
    @Headers("Content-type: application/json")
    fun savePapersServer(@Body examInfo: ArrayList<Any>) : Call<String>

}