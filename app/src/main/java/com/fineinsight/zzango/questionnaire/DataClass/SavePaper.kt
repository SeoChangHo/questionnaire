package com.fineinsight.zzango.questionnaire.DataClass

import com.fineinsight.zzango.questionnaire.LocalList.*

class SavePaper {

    object Total
    {
        var Array = ArrayList<Any>()

        fun Init()
        {
            Array = ArrayList()
            val eb = ByteArray(0)

            SavePaper.Total.Array.add("")
            SavePaper.Total.Array.add(Paper_COMMON("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_MENTAL("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_COGNITIVE("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_ELDERLY("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_EXERCISE("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_NUTRITION("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_SMOKING("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_DRINKING("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_ORAL("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
            SavePaper.Total.Array.add(Paper_CANCER("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
        }
    }

}