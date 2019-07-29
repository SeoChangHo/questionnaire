package com.fineinsight.zzango.questionnaire.DataClass

import java.io.Serializable

data class SelectDetailInfo(
        var TableName: String,
        var userName: String,
        var userNumber: String,
        var dateInfo: String
): Serializable
