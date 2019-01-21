package com.example.zzango.questionnaire.LocalList

class PaperArray {


    object PaperList
    {
        var Arr_ORAL:ArrayList<Paper_ORAL>? = null
        var Arr_COMMON:ArrayList<Paper_COMMON>? = null
    }

    object PaperArrFunction
    {
        fun ArrayListInit()
    {
        PaperList.Arr_ORAL = ArrayList<Paper_ORAL>()
        PaperList.Arr_COMMON = ArrayList<Paper_COMMON>()
    }
    }
}