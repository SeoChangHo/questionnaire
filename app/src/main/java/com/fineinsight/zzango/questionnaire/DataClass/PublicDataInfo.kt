package com.fineinsight.zzango.questionnaire.DataClass

data class PublicDataInfo (
        val hospital: String,
        val name: String,
        val first_serial: String,
        val last_serial: String,
        val signature: ByteArray,
        val exam_no: String
)