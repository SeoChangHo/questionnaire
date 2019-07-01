package com.fineinsight.zzango.questionnaire

import com.fineinsight.zzango.questionnaire.DataClass.MokpoCheck
import com.fineinsight.zzango.questionnaire.LocalList.*
import retrofit2.Call
import retrofit2.http.*

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

    @POST("https://finepaper.herokuapp.com/SavePapers")
    @Headers("Content-type: application/json")
    fun savePapersServer(@Body examInfo: Any) : Call<String>

    @POST("https://finepaper.herokuapp.com/SelectMokpoCheckPaper")
    @FormUrlEncoded
    fun SelectMokpoCheckPaper(@FieldMap sql : Map<String, String>) : Call<List<MokpoCheck>>

}