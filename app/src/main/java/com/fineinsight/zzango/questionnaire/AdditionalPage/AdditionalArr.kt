package com.fineinsight.zzango.questionnaire.AdditionalPage

import android.app.Activity

class AdditionalArr {

    object Page
    {
        var isOralChecked = false
        var isCancerChecked = false


        fun init()
        {
            isOralChecked = false
            isCancerChecked = false
        }
    }

    object Gender
    {
        var isGender = false
    }

    object over
    {
        var checkAll = false
        var isDrinking = false
        var isDrinking2 = false
        var isSmoking = false
    }

}