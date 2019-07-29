package com.fineinsight.zzango.questionnaire

class OracleUtill {

    val oral_url : String = "https://finepaperweight.herokuapp.com/insertOral/"

    fun oral_examination() : OracleInterface {

        return OracleRequest().getStringResponse(oral_url)!!.create(OracleInterface::class.java)

    }

    val common_url : String = "https://finepaperweight.herokuapp.com/InsertNormal/"

    fun common_examination() : OracleInterface {

        return OracleRequest().getStringResponse(common_url)!!.create(OracleInterface::class.java)

    }

    val mental_url : String = "https://finepaperweight.herokuapp.com/InsertMental/"

    fun mental_examination() : OracleInterface {

        return OracleRequest().getStringResponse(mental_url)!!.create(OracleInterface::class.java)

    }

    val cognitive_url : String = "https://finepaperweight.herokuapp.com/InsertCognitive/"

    fun cognitive_examination() : OracleInterface {

        return OracleRequest().getStringResponse(cognitive_url)!!.create(OracleInterface::class.java)

    }

    val elderly_url : String = "https://finepaperweight.herokuapp.com/InsertElderly/"

    fun elderly_examination() : OracleInterface {

        return OracleRequest().getStringResponse(elderly_url)!!.create(OracleInterface::class.java)

    }

    val smoking_url : String = "https://finepaperweight.herokuapp.com/InsertSmoking/"

    fun smoking_examination() : OracleInterface {

        return OracleRequest().getStringResponse(smoking_url)!!.create(OracleInterface::class.java)

    }

    val drinking_url : String = "https://finepaperweight.herokuapp.com/InsertDrinking/"

    fun drinking_examination() : OracleInterface {

        return OracleRequest().getStringResponse(drinking_url)!!.create(OracleInterface::class.java)

    }

    val cancer_url : String = "https://finepaperweight.herokuapp.com/InsertCancer/"

    fun cancer_examination() : OracleInterface {

        return OracleRequest().getStringResponse(cancer_url)!!.create(OracleInterface::class.java)

    }

    val exercise_url : String = "https://finepaperweight.herokuapp.com/InsertExercise/"

    fun exercise_examination() : OracleInterface {

        return OracleRequest().getStringResponse(exercise_url)!!.create(OracleInterface::class.java)

    }

    val nutrition_url : String = "https://finepaperweight.herokuapp.com/InsertNutrition/"

    fun nutrition_examination() : OracleInterface {

        return OracleRequest().getStringResponse(nutrition_url)!!.create(OracleInterface::class.java)

    }

    val save_papers_url : String = "https://finepaper.herokuapp.com//SavePapers/"

    fun save_papers() : OracleInterface {

        return OracleRequest().getStringResponse(save_papers_url)!!.create(OracleInterface::class.java)

    }

    val mokpo_check_url : String = "https://finepaper.herokuapp.com//SelectMokpoCheckPaper/"

    fun getMokpoCheck() : OracleInterface{

        return OracleRequest().getResponse(mokpo_check_url)!!.create(OracleInterface::class.java)

    }

    val list_view_url : String = "https://finepaper.herokuapp.com//SelectList/"

    fun getUserCheck() : OracleInterface{

        return OracleRequest().getResponse(list_view_url)!!.create(OracleInterface::class.java)

    }


    val listdetail_view_url : String = "https://finepaper.herokuapp.com//SelectListDetail/"

    fun getUserDetailCheck() : OracleInterface{

        return OracleRequest().getResponse(listdetail_view_url)!!.create(OracleInterface::class.java)

    }


}