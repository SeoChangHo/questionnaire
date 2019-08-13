package com.fineinsight.zzango.questionnaire.DataClass

import java.io.Serializable

data class ChartInfo(
        var chartName : String,
        var isbool : Boolean,
        var index : Int
):Serializable
