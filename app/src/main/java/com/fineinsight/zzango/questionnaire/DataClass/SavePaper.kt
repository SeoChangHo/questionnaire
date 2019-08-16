package com.fineinsight.zzango.questionnaire.DataClass

import com.fineinsight.zzango.questionnaire.LocalList.*

class SavePaper {

    object Total
    {
        var Array = ArrayList<Any>()

        fun Init()
        {
            Array = ArrayList()

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