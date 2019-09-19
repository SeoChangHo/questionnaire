package com.fineinsight.zzango.questionnaire.DataClass

import java.io.ByteArrayOutputStream


class Examinee {
    object USER{


        var EMPTY_BYTE_ARRAY = ByteArrayOutputStream().toByteArray()

        var info: ExamineeInfo = ExamineeInfo(
                "",
                "",
                "",
                "",
                EMPTY_BYTE_ARRAY,
                false,
                false)

        fun init()
        {
            info = ExamineeInfo(
            "",
            "",
            "",
            "",
            EMPTY_BYTE_ARRAY,
            false,
            false)
        }
    }
}


data class ExamineeInfo(
        var NAME:String,
        var JUMIN1:String,
        var JUMIN2:String,
        var CHARTNO:String,
        var SIGN:ByteArray,
        var IS_WRITE_AGREE:Boolean,
        var IS_LOGIN:Boolean
)

