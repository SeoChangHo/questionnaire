package com.fineinsight.zzango.questionnaire.DataClass

import com.fineinsight.zzango.questionnaire.LocalList.*

class SavePaper {

    object Total
    {
        var Array = ArrayList<Any>()
        var temp_Mental : Paper_MENTAL? = null
        var temp_Cognitive : Paper_COGNITIVE? = null
        var temp_Elderly : Paper_ELDERLY? = null
        var temp_Exercise : Paper_EXERCISE? = null
        var temp_Nutrition : Paper_NUTRITION? = null
        var temp_Smoking : Paper_SMOKING? = null
        var temp_Drinking : Paper_DRINKING? = null
        var temp_Oral : Paper_ORAL? = null
        var temp_Cancer : Paper_CANCER? = null

        fun Init()
        {
            Array = ArrayList()
            Array.clear()

            temp_Mental = null
            temp_Cognitive = null
            temp_Elderly = null
            temp_Exercise = null
            temp_Nutrition = null
            temp_Smoking = null
            temp_Drinking = null
            temp_Oral = null
            temp_Cancer = null

            Array.add("")
            Array.add(Paper_COMMON("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_MENTAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_COGNITIVE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_ELDERLY("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_EXERCISE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_NUTRITION("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_SMOKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_DRINKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_ORAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            Array.add(Paper_CANCER("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
        }
    }

}