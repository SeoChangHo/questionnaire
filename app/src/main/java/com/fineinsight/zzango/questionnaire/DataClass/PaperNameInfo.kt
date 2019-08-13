package com.fineinsight.zzango.questionnaire.DataClass

class PaperNameInfo {

    //PaperCategoryClass
    data class PC_Class(
            val KO_NM:String,
            var EN_NM:String
    )


    //PaperCategory
    object PC
    {
        val ORAL = PC_Class("구강", "ORAL")
        val COMMON = PC_Class("공통", "COMMON")
        val MENTAL = PC_Class("정신건강", "MENTAL")
        val COGNITIVE = PC_Class("인지기능", "COGNITIVE")
        val ELDERLY = PC_Class("노인기능", "ELDERLY")
        val LIFE = PC_Class("생활습관", "LIFE")
        val CANCER = PC_Class("암", "CANCER")


        //생활습관 4개항목
        /*
        val EXERCISE = PC_Class("운동", "EXERCISE")
        val NUTRITION = PC_Class("영양", "NUTRITION")
        val SMOKING = PC_Class("흡연", "SMOKING")
        val DRINKING = PC_Class("음주", "DRINKING")
         */
    }
}