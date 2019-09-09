package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fineinsight.zzango.questionnaire.DataClass.PublicDataInfo
import com.fineinsight.zzango.questionnaire.LocalList.*
import com.fineinsight.zzango.questionnaire.UserList.UserList

class LocalDBhelper(context : Context) : SQLiteOpenHelper(context, "oraltest.db", null, 1){

    override fun onCreate(db: SQLiteDatabase) {
    }

    fun CreatePaperTable(db: SQLiteDatabase)
    {
        CreateLocalListTable(db)
        CheckLocalListTable(db)

        commonCreate(db)
        commontableCheck(db)

        mentalCreate(db)
        mentaltableCheck(db)

        cognitiveCreate(db)
        cognitivetableCheck(db)

        elderlyCreate(db)
        elderlytableCheck(db)

        exerciseCreate(db)
        exercisetableCheck(db)

        nutritionCreate(db)
        nutritiontableCheck(db)

        smokingCreate(db)
        smokingtableCheck(db)

        drinkingCreate(db)
        drinkingtableCheck(db)

        oralCreate(db)
        oraltableCheck(db)

        cancerCreate(db)
        cancertableCheck(db)

        db.close()
    }




    fun CreateLocalListTable(db: SQLiteDatabase)
    {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "LOCALSAVELIST" +
                "(exam_no TEXT," +
                "signature BLOB," +
                "first_serial TEXT," +
                "last_serial TEXT," +
                "name TEXT);")
    }



    fun CheckLocalListTable(db: SQLiteDatabase)
    {

        println("oraltableCheck")

        var cursor = db.query("LOCALSAVELIST", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()


        Array.add("exam_no")
        Array.add("signature")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("last_serial")
        Array.add("name")


        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")

                    if(item == "signature")
                    {
//                        db.execSQL("ALTER TABLE ORAL_EXAM ADD COLUMN "+item+" BLOB default ''");
                    }
                    else
                    {
                        db.execSQL("ALTER TABLE LOCALSAVELIST ADD COLUMN "+item+" TEXT default ''");
                    }
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }



    fun LocalListInsert(db : SQLiteDatabase, ex : PublicDataInfo) {


        val cv = ContentValues()

        cv.put("exam_no", ex.exam_no)
        cv.put("signature", ex.signature)
        cv.put("first_serial", ex.first_serial)
        cv.put("last_serial", ex.last_serial)
        cv.put("name", ex.name)


        db.insert("LOCALSAVELIST", null, cv)
    }

    fun UserTableCreate(db: SQLiteDatabase?) {

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "USERTABLE" +
                "(user TEXT," +
                "pass TEXT);")
    }

    @SuppressLint("Recycle")
    fun UserUpdateCheck(db : SQLiteDatabase): Cursor{

        var data = db.rawQuery("SELECT * FROM USERTABLE;", null)


        return data

    }

    fun UserInsert(db : SQLiteDatabase, arr : ArrayList<UserList>)
    {
        for (item in arr)
        {
            val cv = ContentValues()
            cv.put("user", item.user)
            cv.put("pass", item.pass)

            db.insert("USERTABLE", null, cv)
        }
    }

    fun UserDeleteAndInsert(db : SQLiteDatabase, arr : ArrayList<UserList>)
    {
        db.delete("USERTABLE", null, null)

        println("유저정보를 제거 합니다.")

        println("유저정보를 새로 입력합니다!")

        println("*********************")

        for (item in arr)
        {

            println("*** user: "+item.user+"***")
            println("*** pass: "+item.pass+"***")
            val cv = ContentValues()
            cv.put("user", item.user)
            cv.put("pass", item.pass)

            db.insert("USERTABLE", null, cv)
        }

        println("*********************")
    }

    fun UserCheck(db : SQLiteDatabase, arr : ArrayList<UserList>) : Int
    {
        var sql = "SELECT * FROM USERTABLE WHERE user =? AND pass=?;"



        var data = db.rawQuery(sql, arrayOf(arr[0].user, arr[0].pass))

        println("user:" + arr[0].user)
        println("pass:" + arr[0].pass)
        println("count: "+data.count)


        return data.count
    }


    fun oralCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "ORAL_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT," +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " oral_1 TEXT," +
                " oral_2 TEXT," +
                " oral_3 TEXT," +
                " oral_4 TEXT," +
                " oral_5 TEXT," +
                " oral_6 TEXT," +
                " oral_7 TEXT," +
                " oral_8 TEXT," +
                " oral_9 TEXT," +
                " oral_10 TEXT," +
                " oral_11 TEXT," +
                " oral_12 TEXT," +
                " oral_13 TEXT," +
                " oral_14 TEXT," +
                " oral_15 TEXT," +
                " oral_16 TEXT," +
                " oral_Remark TEXT);")

    }

    fun oraltableCheck(db: SQLiteDatabase)
    {

        println("oraltableCheck")

        var cursor = db.query("ORAL_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()

        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("oral_1")
        Array.add("oral_2")
        Array.add("oral_3")
        Array.add("oral_4")
        Array.add("oral_5")
        Array.add("oral_6")
        Array.add("oral_7")
        Array.add("oral_8")
        Array.add("oral_9")
        Array.add("oral_10")
        Array.add("oral_11")
        Array.add("oral_12")
        Array.add("oral_13")
        Array.add("oral_14")
        Array.add("oral_15")
        Array.add("oral_16")
        Array.add("oral_Remark")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE ORAL_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun commonCreate(db: SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "COMMON_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT," +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " mj1_1_1 TEXT," +
                " mj1_1_2 TEXT," +
                " mj1_2_1 TEXT," +
                " mj1_2_2 TEXT," +
                " mj1_3_1 TEXT," +
                " mj1_3_2 TEXT," +
                " mj1_4_1 TEXT," +
                " mj1_4_2 TEXT," +
                " mj1_5_1 TEXT," +
                " mj1_5_2 TEXT," +
                " mj1_6_1 TEXT," +
                " mj1_6_2 TEXT," +
                " mj1_7_1 TEXT," +
                " mj1_7_2 TEXT," +
                " mj2_1 TEXT," +
                " mj2_2 TEXT," +
                " mj2_3 TEXT," +
                " mj2_4 TEXT," +
                " mj2_5 TEXT," +
                " mj3 TEXT," +
                " mj4 TEXT," +
                " mj4_1_1 TEXT," +
                " mj4_1_2 TEXT," +
                " mj4_2_1 TEXT," +
                " mj4_2_2 TEXT," +
                " mj4_2_3 TEXT," +
                " mj5 TEXT," +
                " mj5_1_1 TEXT," +
                " mj5_1_2 TEXT," +
                " mj5_2_1 TEXT," +
                " mj5_2_2 TEXT," +
                " mj5_2_3 TEXT," +
                " mj6 TEXT," +
                " mj6_1 TEXT," +
                " mj71 TEXT," +
                " mj72 TEXT," +
                " mj73 TEXT," +
                " mj74 TEXT," +
                " mj7_1_11 TEXT," +
                " mj7_1_12 TEXT," +
                " mj7_1_13 TEXT," +
                " mj7_1_14 TEXT," +
                " mj7_1_21 TEXT," +
                " mj7_1_22 TEXT," +
                " mj7_1_23 TEXT," +
                " mj7_1_24 TEXT," +
                " mj7_1_31 TEXT," +
                " mj7_1_32 TEXT," +
                " mj7_1_33 TEXT," +
                " mj7_1_34 TEXT," +
                " mj7_1_41 TEXT," +
                " mj7_1_42 TEXT," +
                " mj7_1_43 TEXT," +
                " mj7_1_44 TEXT," +
                " mj7_1_51 TEXT," +
                " mj7_1_52 TEXT," +
                " mj7_1_53 TEXT," +
                " mj7_1_54 TEXT," +
                " mj7_1_etc TEXT," +
                " mj7_2_11 TEXT," +
                " mj7_2_12 TEXT," +
                " mj7_2_13 TEXT," +
                " mj7_2_14 TEXT," +
                " mj7_2_21 TEXT," +
                " mj7_2_22 TEXT," +
                " mj7_2_23 TEXT," +
                " mj7_2_24 TEXT," +
                " mj7_2_31 TEXT," +
                " mj7_2_32 TEXT," +
                " mj7_2_33 TEXT," +
                " mj7_2_34 TEXT," +
                " mj7_2_41 TEXT," +
                " mj7_2_42 TEXT," +
                " mj7_2_43 TEXT," +
                " mj7_2_44 TEXT," +
                " mj7_2_51 TEXT," +
                " mj7_2_52 TEXT," +
                " mj7_2_53 TEXT," +
                " mj7_2_54 TEXT," +
                " mj7_2_etc TEXT," +
                " mj8_1 TEXT," +
                " mj8_2_1 TEXT," +
                " mj8_2_2 TEXT," +
                " mj9_1 TEXT," +
                " mj9_2_1 TEXT," +
                " mj9_2_2 TEXT," +
                " mj10 TEXT);")

    }


    fun commontableCheck(db: SQLiteDatabase)
    {

        println("commontableCheck")


        var cursor = db.query("COMMON_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()

        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("mj1_1_1")
        Array.add("mj1_1_2")
        Array.add("mj1_2_1")
        Array.add("mj1_2_2")
        Array.add("mj1_3_1")
        Array.add("mj1_3_2")
        Array.add("mj1_4_1")
        Array.add("mj1_4_2")
        Array.add("mj1_5_1")
        Array.add("mj1_5_2")
        Array.add("mj1_6_1")
        Array.add("mj1_6_2")
        Array.add("mj1_7_1")
        Array.add("mj1_7_2")
        Array.add("mj2_1")
        Array.add("mj2_2")
        Array.add("mj2_3")
        Array.add("mj2_4")
        Array.add("mj2_5")
        Array.add("mj3")
        Array.add("mj4")
        Array.add("mj4_1_1")
        Array.add("mj4_1_2")
        Array.add("mj4_2_1")
        Array.add("mj4_2_2")
        Array.add("mj4_2_3")
        Array.add("mj5")
        Array.add("mj5_1_1")
        Array.add("mj5_1_2")
        Array.add("mj5_2_1")
        Array.add("mj5_2_2")
        Array.add("mj5_2_3")
        Array.add("mj6")
        Array.add("mj6_1")
        Array.add("mj71")
        Array.add("mj72")
        Array.add("mj73")
        Array.add("mj74")
        Array.add("mj7_1_11")
        Array.add("mj7_1_12")
        Array.add("mj7_1_13")
        Array.add("mj7_1_14")
        Array.add("mj7_1_21")
        Array.add("mj7_1_22")
        Array.add("mj7_1_23")
        Array.add("mj7_1_24")
        Array.add("mj7_1_31")
        Array.add("mj7_1_32")
        Array.add("mj7_1_33")
        Array.add("mj7_1_34")
        Array.add("mj7_1_41")
        Array.add("mj7_1_42")
        Array.add("mj7_1_43")
        Array.add("mj7_1_44")
        Array.add("mj7_1_51")
        Array.add("mj7_1_52")
        Array.add("mj7_1_53")
        Array.add("mj7_1_54")
        Array.add("mj7_1_etc")
        Array.add("mj7_2_11")
        Array.add("mj7_2_12")
        Array.add("mj7_2_13")
        Array.add("mj7_2_14")
        Array.add("mj7_2_21")
        Array.add("mj7_2_22")
        Array.add("mj7_2_23")
        Array.add("mj7_2_24")
        Array.add("mj7_2_31")
        Array.add("mj7_2_32")
        Array.add("mj7_2_33")
        Array.add("mj7_2_34")
        Array.add("mj7_2_41")
        Array.add("mj7_2_42")
        Array.add("mj7_2_43")
        Array.add("mj7_2_44")
        Array.add("mj7_2_51")
        Array.add("mj7_2_52")
        Array.add("mj7_2_53")
        Array.add("mj7_2_54")
        Array.add("mj7_2_etc")
        Array.add("mj8_1")
        Array.add("mj8_2_1")
        Array.add("mj8_2_2")
        Array.add("mj9_1")
        Array.add("mj9_2_1")
        Array.add("mj9_2_2")
        Array.add("mj10")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE COMMON_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun mentalCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "MENTAL_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT,"  +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " mj_mtl_1 TEXT," +
                " mj_mtl_2 TEXT," +
                " mj_mtl_3 TEXT," +
                " mj_mtl_4 TEXT," +
                " mj_mtl_5 TEXT," +
                " mj_mtl_6 TEXT," +
                " mj_mtl_7 TEXT," +
                " mj_mtl_8 TEXT," +
                " mj_mtl_9 TEXT," +
                " mj_mtl_sum TEXT);")
    }


    fun mentaltableCheck(db: SQLiteDatabase)
    {

        println("mentaltableCheck")


        var cursor = db.query("MENTAL_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()


        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("mj_mtl_1")
        Array.add("mj_mtl_2")
        Array.add("mj_mtl_3")
        Array.add("mj_mtl_4")
        Array.add("mj_mtl_5")
        Array.add("mj_mtl_6")
        Array.add("mj_mtl_7")
        Array.add("mj_mtl_8")
        Array.add("mj_mtl_9")
        Array.add("mj_mtl_sum")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE MENTAL_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun cognitiveCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "COGNITIVE_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT,"  +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " mj_inji_1 TEXT," +
                " mj_inji_2 TEXT," +
                " mj_inji_3 TEXT," +
                " mj_inji_4 TEXT," +
                " mj_inji_5 TEXT," +
                " mj_inji_6 TEXT," +
                " mj_inji_7 TEXT," +
                " mj_inji_8 TEXT," +
                " mj_inji_9 TEXT," +
                " mj_inji_10 TEXT," +
                " mj_inji_11 TEXT," +
                " mj_inji_12 TEXT," +
                " mj_inji_13 TEXT," +
                " mj_inji_14 TEXT," +
                " mj_inji_15 TEXT," +
                " mj_inji_sum TEXT);")

    }

    fun cognitivetableCheck(db: SQLiteDatabase)
    {

        println("cognitivetableCheck")


        var cursor = db.query("COGNITIVE_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()


        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("mj_inji_1")
        Array.add("mj_inji_2")
        Array.add("mj_inji_3")
        Array.add("mj_inji_4")
        Array.add("mj_inji_5")
        Array.add("mj_inji_6")
        Array.add("mj_inji_7")
        Array.add("mj_inji_8")
        Array.add("mj_inji_9")
        Array.add("mj_inji_10")
        Array.add("mj_inji_11")
        Array.add("mj_inji_12")
        Array.add("mj_inji_13")
        Array.add("mj_inji_14")
        Array.add("mj_inji_15")
        Array.add("mj_inji_sum")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE COGNITIVE_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun elderlyCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "ELDERLY_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT,"  +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " mj66_1 TEXT," +
                " mj66_2 TEXT," +
                " mj66_3_1 TEXT," +
                " mj66_3_2 TEXT," +
                " mj66_3_3 TEXT," +
                " mj66_3_4 TEXT," +
                " mj66_3_5 TEXT," +
                " mj66_3_6 TEXT," +
                " mj66_4 TEXT," +
                " mj66_5 TEXT);")

    }

    fun elderlytableCheck(db: SQLiteDatabase)
    {


        println("elderlytableCheck")


        var cursor = db.query("ELDERLY_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()


        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("mj66_1")
        Array.add("mj66_2")
        Array.add("mj66_3_1")
        Array.add("mj66_3_2")
        Array.add("mj66_3_3")
        Array.add("mj66_3_4")
        Array.add("mj66_3_5")
        Array.add("mj66_3_6")
        Array.add("mj66_4")
        Array.add("mj66_5")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE ELDERLY_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun smokingCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "SMOKING_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT,"  +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " sg2_spSmoke1 TEXT," +
                " sg2_spSmoke2 TEXT," +
                " sg2_spSmoke3 TEXT," +
                " sg2_spSmoke4 TEXT," +
                " sg2_spSmoke5 TEXT," +
                " sg2_spSmoke6 TEXT," +
                " sg2_spSmoke7 TEXT," +
                " sg2_spSmoke8 TEXT," +
                " sg2_spSmokeSum TEXT);")

    }

    fun smokingtableCheck(db: SQLiteDatabase)
    {

        println("smokingtableCheck")


        var cursor = db.query("SMOKING_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()

        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("sg2_spSmoke1")
        Array.add("sg2_spSmoke2")
        Array.add("sg2_spSmoke3")
        Array.add("sg2_spSmoke4")
        Array.add("sg2_spSmoke5")
        Array.add("sg2_spSmoke6")
        Array.add("sg2_spSmoke7")
        Array.add("sg2_spSmoke8")
        Array.add("sg2_spSmokeSum")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE SMOKING_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun drinkingCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "DRINKING_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT,"  +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " sg2_spDrink1 TEXT," +
                " sg2_spDrink2_1 TEXT," +
                " sg2_spDrink2_2 TEXT," +
                " sg2_spDrink3 TEXT," +
                " sg2_spDrink4 TEXT," +
                " sg2_spDrink5 TEXT," +
                " sg2_spDrink6 TEXT," +
                " sg2_spDrink7 TEXT," +
                " sg2_spDrink8 TEXT," +
                " sg2_spDrink9 TEXT," +
                " sg2_spDrink10 TEXT," +
                " sg2_spDrinkSum TEXT);")

    }

    fun drinkingtableCheck(db: SQLiteDatabase)
    {

        println("drinkingtableCheck")


        var cursor = db.query("DRINKING_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()


        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("sg2_spDrink1")
        Array.add("sg2_spDrink2_1")
        Array.add("sg2_spDrink2_2")
        Array.add("sg2_spDrink3")
        Array.add("sg2_spDrink4")
        Array.add("sg2_spDrink5")
        Array.add("sg2_spDrink6")
        Array.add("sg2_spDrink7")
        Array.add("sg2_spDrink8")
        Array.add("sg2_spDrink9")
        Array.add("sg2_spDrink10")
        Array.add("sg2_spDrinkSum")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE DRINKING_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun exerciseCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "EXERCISE_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT,"  +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " sg2_spSports1_1 TEXT," +
                " sg2_spSports1_2 TEXT," +
                " sg2_spSports1_3_1 TEXT," +
                " sg2_spSports1_3_2 TEXT," +
                " sg2_spSports1_4 TEXT," +
                " sg2_spSports1_5 TEXT," +
                " sg2_spSports1_6_1 TEXT," +
                " sg2_spSports1_6_2 TEXT," +
                " sg2_spSports2_1 TEXT," +
                " sg2_spSports2_2 TEXT," +
                " sg2_spSports2_3_1 TEXT," +
                " sg2_spSports2_3_2 TEXT," +
                " sg2_spSports3_1 TEXT," +
                " sg2_spSports3_2 TEXT," +
                " sg2_spSports3_3_1 TEXT," +
                " sg2_spSports3_3_2 TEXT," +
                " sg2_spSports3_4 TEXT," +
                " sg2_spSports3_5 TEXT," +
                " sg2_spSports3_6_1 TEXT," +
                " sg2_spSports3_6_2 TEXT," +
                " sg2_spSports4_1_1 TEXT," +
                " sg2_spSports4_1_2 TEXT," +
                " sg2_spSports5 TEXT," +
                " sg2_spSports6 TEXT," +
                " sg2_spSports7 TEXT," +
                " sg2_spSports8 TEXT," +
                " sg2_spSports9 TEXT," +
                " sg2_spSports10 TEXT," +
                " sg2_spSports11 TEXT," +
                " sg2_spSports12 TEXT," +
                " sg2_spSportsSum TEXT);")

    }

    fun exercisetableCheck(db: SQLiteDatabase)
    {

        println("exercisetableCheck")


        var cursor = db.query("EXERCISE_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()


        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("sg2_spSports1_1")
        Array.add("sg2_spSports1_2")
        Array.add("sg2_spSports1_3_1")
        Array.add("sg2_spSports1_3_2")
        Array.add("sg2_spSports1_4")
        Array.add("sg2_spSports1_5")
        Array.add("sg2_spSports1_6_1")
        Array.add("sg2_spSports1_6_2")
        Array.add("sg2_spSports2_1")
        Array.add("sg2_spSports2_2")
        Array.add("sg2_spSports2_3_1")
        Array.add("sg2_spSports2_3_2")
        Array.add("sg2_spSports3_1")
        Array.add("sg2_spSports3_2")
        Array.add("sg2_spSports3_3_1")
        Array.add("sg2_spSports3_3_2")
        Array.add("sg2_spSports3_4")
        Array.add("sg2_spSports3_5")
        Array.add("sg2_spSports3_6_1")
        Array.add("sg2_spSports3_6_2")
        Array.add("sg2_spSports4_1_1")
        Array.add("sg2_spSports4_1_2")
        Array.add("sg2_spSports5")
        Array.add("sg2_spSports6")
        Array.add("sg2_spSports7")
        Array.add("sg2_spSports8")
        Array.add("sg2_spSports9")
        Array.add("sg2_spSports10")
        Array.add("sg2_spSports11")
        Array.add("sg2_spSports12")
        Array.add("sg2_spSportsSum")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE EXERCISE_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun nutritionCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "NUTRITION_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT,"  +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " sg2_spFood1 TEXT," +
                " sg2_spFood2 TEXT," +
                " sg2_spFood3 TEXT," +
                " sg2_spFood4 TEXT," +
                " sg2_spFood5 TEXT," +
                " sg2_spFood6 TEXT," +
                " sg2_spFood7 TEXT," +
                " sg2_spFood8 TEXT," +
                " sg2_spFood9 TEXT," +
                " sg2_spFood10 TEXT," +
                " sg2_spFood11 TEXT," +
                " sg2_spFoodSum TEXT," +
                " sg2_spFatHeight TEXT," +
                " sg2_spFatWeight TEXT," +
                " sg2_spFatWaistSize TEXT," +
                " sg2_spFatBmi TEXT," +
                " sg2_spFat1 TEXT," +
                " sg2_spFat2 TEXT," +
                " sg2_spFat3 TEXT," +
                " sg2_spFatSum TEXT);")

    }

    fun nutritiontableCheck(db: SQLiteDatabase)
    {

        println("nutritiontableCheck")


        var cursor = db.query("NUTRITION_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()


        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("sg2_spFood1")
        Array.add("sg2_spFood2")
        Array.add("sg2_spFood3")
        Array.add("sg2_spFood4")
        Array.add("sg2_spFood5")
        Array.add("sg2_spFood6")
        Array.add("sg2_spFood7")
        Array.add("sg2_spFood8")
        Array.add("sg2_spFood9")
        Array.add("sg2_spFood10")
        Array.add("sg2_spFood11")
        Array.add("sg2_spFoodSum")
        Array.add("sg2_spFatHeight")
        Array.add("sg2_spFatWeight")
        Array.add("sg2_spFatWaistSize")
        Array.add("sg2_spFatBmi")
        Array.add("sg2_spFat1")
        Array.add("sg2_spFat2")
        Array.add("sg2_spFat3")
        Array.add("sg2_spFatSum")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE NUTRITION_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    fun cancerCreate(db : SQLiteDatabase?){

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "CANCER_EXAM" +
                "(exam_date TEXT," +
                " exam_no TEXT,"  +
                " name TEXT," +
                " first_serial TEXT," +
                " last_serial TEXT," +
                " category TEXT," +
                " ck1 TEXT," +
                " ck1_1 TEXT," +
                " ck2 TEXT," +
                " ck2_1 TEXT," +
                " ck3_1 TEXT," +
                " ck3_1_1 TEXT," +
                " ck3_1_2 TEXT," +
                " ck3_1_3 TEXT," +
                " ck3_1_4 TEXT," +
                " ck3_1_5 TEXT," +
                " ck3_2 TEXT," +
                " ck3_2_1 TEXT," +
                " ck3_2_2 TEXT," +
                " ck3_2_3 TEXT," +
                " ck3_2_4 TEXT," +
                " ck3_2_5 TEXT," +
                " ck3_3 TEXT," +
                " ck3_3_1 TEXT," +
                " ck3_3_2 TEXT," +
                " ck3_3_3 TEXT," +
                " ck3_3_4 TEXT," +
                " ck3_3_5 TEXT," +
                " ck3_4 TEXT," +
                " ck3_4_1 TEXT," +
                " ck3_4_2 TEXT," +
                " ck3_4_3 TEXT," +
                " ck3_4_4 TEXT," +
                " ck3_4_5 TEXT," +
                " ck3_5 TEXT," +
                " ck3_5_1 TEXT," +
                " ck3_5_2 TEXT," +
                " ck3_5_3 TEXT," +
                " ck3_5_4 TEXT," +
                " ck3_5_5 TEXT," +
                " ck3_6 TEXT," +
                " ck3_6_1 TEXT," +
                " ck3_6_2 TEXT," +
                " ck3_6_3 TEXT," +
                " ck3_6_4 TEXT," +
                " ck3_6_5 TEXT," +
                " ck3_6_kita TEXT," +
                " ck4_1 TEXT," +
                " ck4_2 TEXT," +
                " ck4_3 TEXT," +
                " ck4_4 TEXT," +
                " ck4_5 TEXT," +
                " ck4_6 TEXT," +
                " ck4_7 TEXT," +
                " ck4_8 TEXT," +
                " ck4_9 TEXT," +
                " ck5_1 TEXT," +
                " ck5_2 TEXT," +
                " ck5_3 TEXT," +
                " ck5_4 TEXT," +
                " ck5_5 TEXT," +
                " ck6_1 TEXT," +
                " ck6_2 TEXT," +
                " ck6_3 TEXT," +
                " ck6_4 TEXT," +
                " ck6_5 TEXT," +
                " ck7_1 TEXT," +
                " ck7_2 TEXT," +
                " ck7_3 TEXT," +
                " ck7_4 TEXT," +
                " ck7_5 TEXT," +
                " ck8_1 TEXT," +
                " ck8_2 TEXT," +
                " ck9_1 TEXT," +
                " ck9_2 TEXT," +
                " ck10 TEXT," +
                " ck11 TEXT," +
                " ck12 TEXT," +
                " ck13 TEXT," +
                " ck14 TEXT," +
                " ck15_5 TEXT," +
                " ck15_5_1 TEXT," +
                " ck15_5_2 TEXT," +
                " ck15_5_3 TEXT," +
                " ck15_5_4 TEXT," +
                " ck15_5_5 TEXT," +
                " ck16_1 TEXT," +
                " ck16_2 TEXT," +
                " ck16_3 TEXT," +
                " ck16_4 TEXT," +
                " ck16_5 TEXT," +
                " ck16_6 TEXT);")

    }

    fun cancertableCheck(db: SQLiteDatabase)
    {

        println("cancertableCheck")



        var cursor = db.query("CANCER_EXAM", null, null, null, null, null, null)

        var columnArr = cursor.columnNames

        var Array = ArrayList<String>()


        Array.add("exam_date")
        Array.add("exam_no")
        Array.add("name")
        Array.add("first_serial")
        Array.add("last_serial")
        Array.add("category")
        Array.add("ck1")
        Array.add("ck1_1")
        Array.add("ck2")
        Array.add("ck2_1")
        Array.add("ck3_1")
        Array.add("ck3_1_1")
        Array.add("ck3_1_2")
        Array.add("ck3_1_3")
        Array.add("ck3_1_4")
        Array.add("ck3_1_5")
        Array.add("ck3_2")
        Array.add("ck3_2_1")
        Array.add("ck3_2_2")
        Array.add("ck3_2_3")
        Array.add("ck3_2_4")
        Array.add("ck3_2_5")
        Array.add("ck3_3")
        Array.add("ck3_3_1")
        Array.add("ck3_3_2")
        Array.add("ck3_3_3")
        Array.add("ck3_3_4")
        Array.add("ck3_3_5")
        Array.add("ck3_4")
        Array.add("ck3_4_1")
        Array.add("ck3_4_2")
        Array.add("ck3_4_3")
        Array.add("ck3_4_4")
        Array.add("ck3_4_5")
        Array.add("ck3_5")
        Array.add("ck3_5_1")
        Array.add("ck3_5_2")
        Array.add("ck3_5_3")
        Array.add("ck3_5_4")
        Array.add("ck3_5_5")
        Array.add("ck3_6")
        Array.add("ck3_6_1")
        Array.add("ck3_6_2")
        Array.add("ck3_6_3")
        Array.add("ck3_6_4")
        Array.add("ck3_6_5")
        Array.add("ck3_6_kita")
        Array.add("ck4_1")
        Array.add("ck4_2")
        Array.add("ck4_3")
        Array.add("ck4_4")
        Array.add("ck4_5")
        Array.add("ck4_6")
        Array.add("ck4_7")
        Array.add("ck4_8")
        Array.add("ck4_9")
        Array.add("ck5_1")
        Array.add("ck5_2")
        Array.add("ck5_3")
        Array.add("ck5_4")
        Array.add("ck5_5")
        Array.add("ck6_1")
        Array.add("ck6_2")
        Array.add("ck6_3")
        Array.add("ck6_4")
        Array.add("ck6_5")
        Array.add("ck7_1")
        Array.add("ck7_2")
        Array.add("ck7_3")
        Array.add("ck7_4")
        Array.add("ck7_5")
        Array.add("ck8_1")
        Array.add("ck8_2")
        Array.add("ck9_1")
        Array.add("ck9_2")
        Array.add("ck10")
        Array.add("ck11")
        Array.add("ck12")
        Array.add("ck13")
        Array.add("ck14")
        Array.add("ck15_5")
        Array.add("ck15_5_1")
        Array.add("ck15_5_2")
        Array.add("ck15_5_3")
        Array.add("ck15_5_4")
        Array.add("ck15_5_5")
        Array.add("ck16_1")
        Array.add("ck16_2")
        Array.add("ck16_3")
        Array.add("ck16_4")
        Array.add("ck16_5")
        Array.add("ck16_6")

        for(item in Array)
        {
            if(!columnArr.contains(item))
            {
                try {
                    println("${item} 컬럼이 없어서 추가합니다.")
                    db.execSQL("ALTER TABLE CANCER_EXAM ADD COLUMN "+item+" TEXT default ''");
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun oralSaveLocal(db : SQLiteDatabase, ex : Paper_ORAL){




        db.execSQL("INSERT INTO ORAL_EXAM" +
                "(exam_date," +
                "exam_no," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "oral_1, oral_2, oral_3, oral_4, oral_5, oral_6, oral_7," +
                "oral_8, oral_9, oral_10, oral_11, oral_12, oral_13, oral_14," +
                "oral_15, oral_16, oral_Remark)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.oral_1}', '${ex.oral_2}', '${ex.oral_3}', '${ex.oral_4}'" +
                ", '${ex.oral_5}', '${ex.oral_6}', '${ex.oral_7}', '${ex.oral_8}'," +
                " '${ex.oral_9}', '${ex.oral_10}', '${ex.oral_11}', '${ex.oral_12}'," +
                " '${ex.oral_13}', '${ex.oral_14}', '${ex.oral_15}', '${ex.oral_16}', '${ex.oral_Remark}'"
                +");")
    }

    fun commonSaveLocal(db : SQLiteDatabase, ex : Paper_COMMON){



        db.execSQL("INSERT INTO COMMON_EXAM" +
                "(exam_date, exam_no, name, first_serial, last_serial, category, " +
                " mj1_1_1, mj1_1_2, mj1_2_1, mj1_2_2, mj1_3_1, mj1_3_2, mj1_4_1, mj1_4_2," +
                " mj1_5_1, mj1_5_2, mj1_6_1, mj1_6_2, mj1_7_1, mj1_7_2, mj2_1," +
                " mj2_2, mj2_3, mj2_4, mj2_5, mj3, mj4, mj4_1_1, mj4_1_2, mj4_2_1," +
                " mj4_2_2, mj4_2_3, mj5, mj5_1_1, mj5_1_2, mj5_2_1, mj5_2_2, mj5_2_3," +
                " mj6, mj6_1, mj71, mj72, mj73, mj74, mj7_1_11, mj7_1_12, mj7_1_13, mj7_1_14," +
                " mj7_1_21, mj7_1_22, mj7_1_23, mj7_1_24, mj7_1_31, mj7_1_32, mj7_1_33," +
                " mj7_1_34, mj7_1_41, mj7_1_42, mj7_1_43, mj7_1_44, mj7_1_51, mj7_1_52, mj7_1_53," +
                " mj7_1_54, mj7_1_etc, mj7_2_11, mj7_2_12, mj7_2_13, mj7_2_14, mj7_2_21, mj7_2_22," +
                " mj7_2_23, mj7_2_24, mj7_2_31, mj7_2_32, mj7_2_33, mj7_2_34, mj7_2_41, mj7_2_42," +
                " mj7_2_43, mj7_2_44, mj7_2_51, mj7_2_52, mj7_2_53, mj7_2_54, mj7_2_etc, mj8_1," +
                " mj8_2_1, mj8_2_2, mj9_1, mj9_2_1, mj9_2_2, mj10)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.mj1_1_1}', '${ex.mj1_1_2}', '${ex.mj1_2_1}'" +
                ", '${ex.mj1_2_2}', '${ex.mj1_3_1}', '${ex.mj1_3_2}', '${ex.mj1_4_1}'" +
                ", '${ex.mj1_4_2}', '${ex.mj1_5_1}', '${ex.mj1_5_2}', '${ex.mj1_6_1}'" +
                ", '${ex.mj1_6_2}', '${ex.mj1_7_1}', '${ex.mj1_7_2}'" +
                ", '${ex.mj2_1}', '${ex.mj2_2}', '${ex.mj2_3}', '${ex.mj2_4}'" +
                ", '${ex.mj2_5}', '${ex.mj3}', '${ex.mj4}'" +
                ", '${ex.mj4_1_1}', '${ex.mj4_1_2}', '${ex.mj4_2_1}', '${ex.mj4_2_2}'" +
                ", '${ex.mj4_2_3}', '${ex.mj5}', '${ex.mj5_1_1}'" +
                ", '${ex.mj5_1_2}', '${ex.mj5_2_1}', '${ex.mj5_2_2}', '${ex.mj5_2_3}'" +
                ", '${ex.mj6}', '${ex.mj6_1}', '${ex.mj71}', '${ex.mj72}'" +
                ", '${ex.mj73}', '${ex.mj74}', '${ex.mj7_1_11}', '${ex.mj7_1_12}'" +
                ", '${ex.mj7_1_13}', '${ex.mj7_1_14}', '${ex.mj7_1_21}', '${ex.mj7_1_22}'" +
                ", '${ex.mj7_1_23}', '${ex.mj7_1_24}', '${ex.mj7_1_31}', '${ex.mj7_1_32}'" +
                ", '${ex.mj7_1_33}', '${ex.mj7_1_34}', '${ex.mj7_1_41}', '${ex.mj7_1_42}'" +
                ", '${ex.mj7_1_43}', '${ex.mj7_1_44}', '${ex.mj7_1_51}', '${ex.mj7_1_52}'" +
                ", '${ex.mj7_1_53}', '${ex.mj7_1_54}', '${ex.mj7_1_etc}', '${ex.mj7_2_11}'" +
                ", '${ex.mj7_2_12}', '${ex.mj7_2_13}', '${ex.mj7_2_14}', '${ex.mj7_2_21}'" +
                ", '${ex.mj7_2_22}', '${ex.mj7_2_23}', '${ex.mj7_2_24}', '${ex.mj7_2_31}'" +
                ", '${ex.mj7_2_32}', '${ex.mj7_2_33}', '${ex.mj7_2_34}', '${ex.mj7_2_41}'" +
                ", '${ex.mj7_2_42}', '${ex.mj7_2_43}', '${ex.mj7_2_44}', '${ex.mj7_2_51}'" +
                ", '${ex.mj7_2_52}', '${ex.mj7_2_53}', '${ex.mj7_2_54}', '${ex.mj7_2_etc}'" +
                ", '${ex.mj8_1}', '${ex.mj8_2_1}', '${ex.mj8_2_2}', '${ex.mj9_1}'" +
                ", '${ex.mj9_2_1}', '${ex.mj9_2_2}', '${ex.mj10}');")
    }


    fun mentalSaveLocal(db : SQLiteDatabase, ex : Paper_MENTAL){



        db.execSQL("INSERT INTO MENTAL_EXAM" +
                "(exam_date," +
                "exam_no,"  +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "mj_mtl_1, mj_mtl_2, mj_mtl_3, mj_mtl_4, mj_mtl_5, mj_mtl_6, mj_mtl_7," +
                "mj_mtl_8, mj_mtl_9, mj_mtl_sum)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.mj_mtl_1}', '${ex.mj_mtl_2}', '${ex.mj_mtl_3}', '${ex.mj_mtl_4}'" +
                ", '${ex.mj_mtl_5}', '${ex.mj_mtl_6}', '${ex.mj_mtl_7}', '${ex.mj_mtl_8}'" +
                ", '${ex.mj_mtl_9}', '${ex.mj_mtl_sum}');")

    }

    fun cognitiveSaveLocal(db : SQLiteDatabase, ex : Paper_COGNITIVE){



        db.execSQL("INSERT INTO COGNITIVE_EXAM" +
                "(exam_date," +
                "exam_no,"  +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "mj_inji_1, mj_inji_2, mj_inji_3, mj_inji_4, mj_inji_5, mj_inji_6, mj_inji_7," +
                "mj_inji_8, mj_inji_9, mj_inji_10, mj_inji_11, mj_inji_12, mj_inji_13, mj_inji_14, mj_inji_15, mj_inji_sum)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.mj_inji_1}', '${ex.mj_inji_2}', '${ex.mj_inji_3}', '${ex.mj_inji_4}'" +
                ", '${ex.mj_inji_5}', '${ex.mj_inji_6}', '${ex.mj_inji_7}', '${ex.mj_inji_8}'" +
                ", '${ex.mj_inji_9}', '${ex.mj_inji_10}', '${ex.mj_inji_11}', '${ex.mj_inji_12}'" +
                ", '${ex.mj_inji_13}', '${ex.mj_inji_14}', '${ex.mj_inji_15}', '${ex.mj_inji_sum}');")

    }

    fun elderlySaveLocal(db : SQLiteDatabase, ex : Paper_ELDERLY){



        db.execSQL("INSERT INTO ELDERLY_EXAM" +
                "(exam_date," +
                "exam_no,"  +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "mj66_1, mj66_2, mj66_3_1, mj66_3_2, mj66_3_3, mj66_3_4, mj66_3_5," +
                "mj66_3_6, mj66_4, mj66_5)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.mj66_1}', '${ex.mj66_2}', '${ex.mj66_3_1}', '${ex.mj66_3_2}'" +
                ", '${ex.mj66_3_3}', '${ex.mj66_3_4}', '${ex.mj66_3_5}', '${ex.mj66_3_6}'" +
                ", '${ex.mj66_4}', '${ex.mj66_5}');")
    }

    fun smokingSaveLocal(db : SQLiteDatabase, ex : Paper_SMOKING){



        db.execSQL("INSERT INTO SMOKING_EXAM" +
                "(exam_date," +
                "exam_no,"  +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "sg2_spSmoke1, sg2_spSmoke2, sg2_spSmoke3, sg2_spSmoke4, sg2_spSmoke5, sg2_spSmoke6, sg2_spSmoke7," +
                "sg2_spSmoke8, sg2_spSmokeSum)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.sg2_spSmoke1}', '${ex.sg2_spSmoke2}', '${ex.sg2_spSmoke3}', '${ex.sg2_spSmoke4}'" +
                ", '${ex.sg2_spSmoke5}', '${ex.sg2_spSmoke6}', '${ex.sg2_spSmoke7}', '${ex.sg2_spSmoke8}'" +
                ", '${ex.sg2_spSmokeSum}');")
    }


    fun drinkingSaveLocal(db : SQLiteDatabase, ex : Paper_DRINKING){



        db.execSQL("INSERT INTO DRINKING_EXAM" +
                "(exam_date," +
                "exam_no,"  +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "sg2_spDrink1, sg2_spDrink2_1, sg2_spDrink2_2, sg2_spDrink3, sg2_spDrink4, sg2_spDrink5, sg2_spDrink6," +
                "sg2_spDrink7, sg2_spDrink8, sg2_spDrink9, sg2_spDrink10, sg2_spDrinkSum)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.sg2_spDrink1}', '${ex.sg2_spDrink2_1}', '${ex.sg2_spDrink2_2}', '${ex.sg2_spDrink3}'" +
                ", '${ex.sg2_spDrink4}', '${ex.sg2_spDrink5}', '${ex.sg2_spDrink6}', '${ex.sg2_spDrink7}'" +
                ", '${ex.sg2_spDrink8}', '${ex.sg2_spDrink9}', '${ex.sg2_spDrink10}', '${ex.sg2_spDrinkSum}');")
    }

    fun exerciseSaveLocal(db: SQLiteDatabase, ex : Paper_EXERCISE){



        db.execSQL("INSERT INTO EXERCISE_EXAM" +
                "(exam_date," +
                "exam_no,"  +
                "name," +
                "first_serial," +
                "last_serial," +
                "category," +
                "sg2_spSports1_1,sg2_spSports1_2,sg2_spSports1_3_1,sg2_spSports1_3_2,sg2_spSports1_4,sg2_spSports1_5," +
                "sg2_spSports1_6_1,sg2_spSports1_6_2,sg2_spSports2_1,sg2_spSports2_2,sg2_spSports2_3_1,sg2_spSports2_3_2," +
                "sg2_spSports3_1,sg2_spSports3_2,sg2_spSports3_3_1,sg2_spSports3_3_2,sg2_spSports3_4,sg2_spSports3_5," +
                "sg2_spSports3_6_1,sg2_spSports3_6_2,sg2_spSports4_1_1,sg2_spSports4_1_2,sg2_spSports5,sg2_spSports6," +
                "sg2_spSports7,sg2_spSports8,sg2_spSports9,sg2_spSports10,sg2_spSports11,sg2_spSports12,sg2_spSportsSum)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_bun_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.sg2_spSports1_1}', '${ex.sg2_spSports1_2}', '${ex.sg2_spSports1_3_1}'" +
                ", '${ex.sg2_spSports1_3_2}', '${ex.sg2_spSports1_4}', '${ex.sg2_spSports1_5}'" +
                ", '${ex.sg2_spSports1_6_1}', '${ex.sg2_spSports1_6_2}', '${ex.sg2_spSports2_1}'" +
                ", '${ex.sg2_spSports2_2}', '${ex.sg2_spSports2_3_1}', '${ex.sg2_spSports2_3_2}'" +
                ", '${ex.sg2_spSports3_1}', '${ex.sg2_spSports3_2}', '${ex.sg2_spSports3_3_1}'" +
                ", '${ex.sg2_spSports3_3_2}', '${ex.sg2_spSports3_4}', '${ex.sg2_spSports3_5}'" +
                ", '${ex.sg2_spSports3_6_1}', '${ex.sg2_spSports3_6_2}', '${ex.sg2_spSports4_1_1}'" +
                ", '${ex.sg2_spSports4_1_2}', '${ex.sg2_spSports5}', '${ex.sg2_spSports6}'" +
                ", '${ex.sg2_spSports7}', '${ex.sg2_spSports8}', '${ex.sg2_spSports9}'" +
                ", '${ex.sg2_spSports10}', '${ex.sg2_spSports11}', '${ex.sg2_spSports12}', '${ex.sg2_spSportsSum}');")

    }

    fun nutritionSaveLocal(db: SQLiteDatabase, ex : Paper_NUTRITION){



        db.execSQL("INSERT INTO NUTRITION_EXAM" +
                "(exam_date," +
                "exam_no,"  +
                "name," +
                "first_serial," +
                "last_serial," +
                "category," +
                "sg2_spFood1,sg2_spFood2,sg2_spFood3,sg2_spFood4,sg2_spFood5,sg2_spFood6," +
                "sg2_spFood7,sg2_spFood8,sg2_spFood9,sg2_spFood10,sg2_spFood11,sg2_spFoodSum," +
                "sg2_spFatHeight,sg2_spFatWeight,sg2_spFatWaistSize,sg2_spFatBmi,sg2_spFat1,sg2_spFat2,sg2_spFat3)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_bun_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.sg2_spFood1}', '${ex.sg2_spFood2}', '${ex.sg2_spFood3}'" +
                ", '${ex.sg2_spFood4}', '${ex.sg2_spFood5}', '${ex.sg2_spFood6}'" +
                ", '${ex.sg2_spFood7}', '${ex.sg2_spFood8}', '${ex.sg2_spFood9}'" +
                ", '${ex.sg2_spFood10}', '${ex.sg2_spFood11}', '${ex.sg2_spFoodSum}'" +
                ", '${ex.sg2_spHeight}', '${ex.sg2_spWeight}', '${ex.sg2_spWaistSize}', '${ex.sg2_spBmi}'" +
                ", '${ex.sg2_spFat1}', '${ex.sg2_spFat2}', '${ex.sg2_spFat3}');")

    }

    fun cancerSaveLocal(db : SQLiteDatabase, ex : Paper_CANCER){
        db.execSQL("INSERT INTO CANCER_EXAM" +
                "(exam_date," +
                "exam_no," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "ck1, ck1_1, ck2, ck2_1," +
                "ck3_1, ck3_1_1, ck3_1_2, ck3_1_3, ck3_1_4, ck3_1_5," +
                "ck3_2, ck3_2_1, ck3_2_2, ck3_2_3, ck3_2_4, ck3_2_5," +
                "ck3_3, ck3_3_1, ck3_3_2, ck3_3_3, ck3_3_4, ck3_3_5," +
                "ck3_4, ck3_4_1, ck3_4_2, ck3_4_3, ck3_4_4, ck3_4_5," +
                "ck3_5, ck3_5_1, ck3_5_2, ck3_5_3, ck3_5_4, ck3_5_5," +
                "ck3_6, ck3_6_1, ck3_6_2, ck3_6_3, ck3_6_4, ck3_6_5, ck3_6_kita," +
                "ck4_1, ck4_2, ck4_3, ck4_4, ck4_5, ck4_6, ck4_7, ck4_8, ck4_9, ck5_1, ck5_2, ck5_3, ck5_4, ck5_5," +
                "ck6_1, ck6_2, ck6_3, ck6_4, ck6_5, ck7_1, ck7_2, ck7_3, ck7_4, ck7_5, ck8_1, ck8_2," +
                "ck9_1, ck9_2, ck10, ck11, ck12, ck13, ck14, ck15_5, ck15_5_1, ck15_5_2, ck15_5_3, ck15_5_4, ck15_5_5, ck16_1, ck16_2, ck16_3, ck16_4, ck16_5, ck16_6)" +
                " VALUES (" +
                "'${ex.exam_date}', '${ex.exam_no}', '${ex.name}', '${ex.first_serial}', '${ex.last_serial}'" +
                ", '${ex.category}', '${ex.ck1}', '${ex.ck1_1}', '${ex.ck2}', '${ex.ck2_1}'" +
                ", '${ex.ck3_1}', '${ex.ck3_1_1}', '${ex.ck3_1_2}', '${ex.ck3_1_3}', '${ex.ck3_1_4}', '${ex.ck3_1_5}'" +
                ", '${ex.ck3_2}', '${ex.ck3_2_1}', '${ex.ck3_2_2}', '${ex.ck3_2_3}', '${ex.ck3_2_4}', '${ex.ck3_2_5}'" +
                ", '${ex.ck3_3}', '${ex.ck3_3_1}', '${ex.ck3_3_2}', '${ex.ck3_3_3}', '${ex.ck3_3_4}', '${ex.ck3_3_5}'" +
                ", '${ex.ck3_4}', '${ex.ck3_4_1}', '${ex.ck3_4_2}', '${ex.ck3_4_3}', '${ex.ck3_4_4}', '${ex.ck3_4_5}'" +
                ", '${ex.ck3_5}', '${ex.ck3_5_1}', '${ex.ck3_5_2}', '${ex.ck3_5_3}', '${ex.ck3_5_4}', '${ex.ck3_5_5}'" +
                ", '${ex.ck3_6}', '${ex.ck3_6_1}', '${ex.ck3_6_2}', '${ex.ck3_6_3}', '${ex.ck3_6_4}', '${ex.ck3_6_5}', '${ex.ck3_6_kita}'" +
                ", '${ex.ck4_1}', '${ex.ck4_2}', '${ex.ck4_3}', '${ex.ck4_4}'" +
                ", '${ex.ck4_5}', '${ex.ck4_6}', '${ex.ck4_7}', '${ex.ck4_8}', '${ex.ck4_9}'" +
                ", '${ex.ck5_1}', '${ex.ck5_2}', '${ex.ck5_3}', '${ex.ck5_4}'" +
                ", '${ex.ck5_5}', '${ex.ck6_1}', '${ex.ck6_2}'" +
                ", '${ex.ck6_3}', '${ex.ck6_4}', '${ex.ck6_5}'" +
                ", '${ex.ck7_1}', '${ex.ck7_2}', '${ex.ck7_3}', '${ex.ck7_4}'" +
                ", '${ex.ck7_5}', '${ex.ck8_1}', '${ex.ck8_2}'" +
                ", '${ex.ck9_1}', '${ex.ck9_2}', '${ex.ck10}', '${ex.ck11}'" +
                ", '${ex.ck12}', '${ex.ck13}', '${ex.ck14}', '${ex.ck15_5}', '${ex.ck15_5_1}', '${ex.ck15_5_2}', '${ex.ck15_5_3}', '${ex.ck15_5_4}', '${ex.ck15_5_5}'" +
                ", '${ex.ck16_1}', '${ex.ck16_2}', '${ex.ck16_3}', '${ex.ck16_4}', '${ex.ck16_5}', '${ex.ck16_6}');")
    }

    @SuppressLint("Recycle")
    fun Select_Local_ORAL(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{

        var sql = "SELECT * FROM ORAL_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))



        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_COMMON(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM COMMON_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))

        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_COGNITIVE(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM COGNITIVE_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))

        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_ELDERLY(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM ELDERLY_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))

        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_MENTAL(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM MENTAL_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))
        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_EXERCISE(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM EXERCISE_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))
        return data

    }

    @SuppressLint("Recycle")
    fun Select_Local_SMOKING(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM SMOKING_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))
        return data

    }

    @SuppressLint("Recycle")
    fun Select_Local_DRINKING(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM DRINKING_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))
        return data

    }

    @SuppressLint("Recycle")
    fun Select_Local_CANCER(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM CANCER_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))
        return data

    }

    @SuppressLint("Recycle")
    fun Select_Local_NUTRITION(db : SQLiteDatabase, getno: String, getname:String, first_serial:String): Cursor{


        var sql = "SELECT * FROM NUTRITION_EXAM WHERE exam_no =? AND name=? AND first_serial=?;"

        var data = db.rawQuery(sql, arrayOf(getno, getname, first_serial))
        return data

    }

    @SuppressLint("Recycle")
    fun checkLocalList(db : SQLiteDatabase): Cursor{

        var data = db.rawQuery("SELECT * FROM LOCALSAVELIST;", null)
        return data

    }

    fun deletePaper(db : SQLiteDatabase, Paper: ArrayList<Paper>)
    {
        for(i in 0..Paper.size-1)
        {
            deletePaperEach(db, Paper[i])
        }
        db.close()
    }


    fun deletePaperEach(db : SQLiteDatabase, Paper: Paper)
    {
        println(Paper.name+"님의 문진표 삭제시도")
        db.delete("LOCALSAVELIST", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("COMMON_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("MENTAL_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("COGNITIVE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("ELDERLY_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("EXERCISE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("NUTRITION_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("DRINKING_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("SMOKING_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("ORAL_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        db.delete("CANCER_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
        }
}