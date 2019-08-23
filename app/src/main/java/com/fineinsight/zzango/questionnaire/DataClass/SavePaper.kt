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


            var temp_Mental = null
            var temp_Cognitive = null
            var temp_Elderly = null
            var temp_Exercise = null
            var temp_Nutrition = null
            var temp_Smoking = null
            var temp_Drinking = null
            var temp_Oral = null
            var temp_Cancer = null

            SavePaper.Total.Array.add("")
            SavePaper.Total.Array.add(Paper_COMMON("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_MENTAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_COGNITIVE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_ELDERLY("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_EXERCISE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_NUTRITION("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_SMOKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_DRINKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_ORAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_CANCER("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
        }
    }

}