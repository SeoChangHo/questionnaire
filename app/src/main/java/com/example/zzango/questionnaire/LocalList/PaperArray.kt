package com.example.zzango.questionnaire.LocalList

class PaperArray {


    object PaperList
    {
        var Arr_COMMON:ArrayList<Paper_COMMON>? = null

        var Arr_MENTAL:ArrayList<Paper_MENTAL>? = null
        var Arr_COGNITIVE:ArrayList<Paper_COGNITIVE>? = null
        var Arr_ELDERLY:ArrayList<Paper_ELDERLY>? = null

        var Arr_DRINKING:ArrayList<Paper_DRINKING>? = null
        var Arr_EXERCISE:ArrayList<Paper_EXERCISE>? = null
        var Arr_NUTRITION:ArrayList<Paper_NUTRITION>? = null
        var Arr_SMOKING:ArrayList<Paper_SMOKING>? = null

        var Arr_ORAL:ArrayList<Paper_ORAL>? = null

        var Arr_CANCER:ArrayList<Paper_CANCER>? = null

        var Arr_RESULT:ArrayList<Any>? = null
    }

    object PaperArrFunction
    {
        fun ArrayListInit()
        {
            PaperList.Arr_COMMON = ArrayList<Paper_COMMON>()

            PaperList.Arr_MENTAL = ArrayList<Paper_MENTAL>()
            PaperList.Arr_COGNITIVE = ArrayList<Paper_COGNITIVE>()
            PaperList.Arr_ELDERLY = ArrayList<Paper_ELDERLY>()

            PaperList.Arr_DRINKING = ArrayList<Paper_DRINKING>()
            PaperList.Arr_EXERCISE = ArrayList<Paper_EXERCISE>()
            PaperList.Arr_NUTRITION = ArrayList<Paper_NUTRITION>()
            PaperList.Arr_SMOKING = ArrayList<Paper_SMOKING>()

            PaperList.Arr_ORAL = ArrayList<Paper_ORAL>()

            PaperList.Arr_CANCER = ArrayList<Paper_CANCER>()

            PaperList.Arr_RESULT = ArrayList<Any>()
        }
    }

    object SetList
    {
        //기본검사
        val SET1 = "SET1"


        //기본검사 우울증
        val SET2 = "SET2"


        //기본검사 우울증 생활습관
        val SET3 = "SET3"


        //기본검사 인지기능 노인신체기능검사
        val SET4 = "SET4"


        //기본검사 인지기능
        val SET5 = "SET5"


        //기본검사 인지기능 우울증 생활습관 노인신체기능검사
        val SET6 = "SET6"
    }

}