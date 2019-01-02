package com.example.zzango.questionnaire

class OracleUtill {

    val oral_url : String = "https://finepaperweight.herokuapp.com/insertOral/"

    fun oral_examination() : OracleInterface {

        return OracleRequest().getStringResponse(oral_url)!!.create(OracleInterface::class.java)

    }

    val common_url : String = "https://finepaperweight.herokuapp.com/InsertNormal/"

    fun common_examination() : OracleInterface {

        return OracleRequest().getStringResponse(common_url)!!.create(OracleInterface::class.java)

    }

}