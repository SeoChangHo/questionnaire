package com.fineinsight.zzango.questionnaire

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fineinsight.zzango.questionnaire.LocalList.*
import com.fineinsight.zzango.questionnaire.UserList.UserList

class LocalDBhelper(context : Context) : SQLiteOpenHelper(context, "oraltest.db", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL("CREATE TABLE IF NOT EXISTS " +
                "LOCALSAVELIST" +
                "(exam_no TEXT," +
                "setno TEXT," +
                "signature BLOB," +
                "name TEXT);")
    }

    fun LocalListInsert(db : SQLiteDatabase, ex : ArrayList<Paper_COMMON>, setno:String) {
        val columnValue = ex.get(0)

        val cv = ContentValues()

        cv.put("exam_no", columnValue.exam_no)
        cv.put("setno", setno)
        cv.put("signature", columnValue.signature)
        cv.put("name", columnValue.name)

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


    fun LocalListOralInsert(db : SQLiteDatabase, ex : ArrayList<Paper_ORAL>, set:String) {
        val columnValue = ex.get(0)

        val cv = ContentValues()

        cv.put("exam_no", columnValue.exam_no)
        cv.put("setno", set)
        cv.put("signature", columnValue.signature)
        cv.put("name", columnValue.name)

        db.insert("LOCALSAVELIST", null, cv)
    }


    fun LocalListCancerInsert(db : SQLiteDatabase, ex : ArrayList<Paper_CANCER>, set:String) {
        val columnValue = ex.get(0)

        val cv = ContentValues()

        cv.put("exam_no", columnValue.exam_no)
        cv.put("setno", set)
        cv.put("signature", columnValue.signature)
        cv.put("name", columnValue.name)

        db.insert("LOCALSAVELIST", null, cv)
    }

    //정신
    fun LocalListMentalInsert(db : SQLiteDatabase, ex : ArrayList<Paper_MENTAL>, set:String) {
        val columnValue = ex.get(0)

        val cv = ContentValues()

        cv.put("exam_no", columnValue.exam_no)
        cv.put("setno", set)
        cv.put("signature", columnValue.signature)
        cv.put("name", columnValue.name)

        db.insert("LOCALSAVELIST", null, cv)
    }

    //인지
    fun LocalListCognitiveInsert(db : SQLiteDatabase, ex : ArrayList<Paper_COGNITIVE>, set:String) {
        val columnValue = ex.get(0)

        val cv = ContentValues()

        cv.put("exam_no", columnValue.exam_no)
        cv.put("setno", set)
        cv.put("signature", columnValue.signature)
        cv.put("name", columnValue.name)

        db.insert("LOCALSAVELIST", null, cv)
    }

    //노인
    fun LocalListElderlyInsert(db : SQLiteDatabase, ex : ArrayList<Paper_ELDERLY>, set:String) {
        val columnValue = ex.get(0)

        val cv = ContentValues()

        cv.put("exam_no", columnValue.exam_no)
        cv.put("setno", set)
        cv.put("signature", columnValue.signature)
        cv.put("name", columnValue.name)

        db.insert("LOCALSAVELIST", null, cv)
    }

    //생활
    fun LocalListDrinkingInsert(db : SQLiteDatabase, ex : ArrayList<Paper_DRINKING>, set:String) {
        val columnValue = ex.get(0)

        val cv = ContentValues()

        cv.put("exam_no", columnValue.exam_no)
        cv.put("setno", set)
        cv.put("signature", columnValue.signature)
        cv.put("name", columnValue.name)

        db.insert("LOCALSAVELIST", null, cv)
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
                " oral_16 TEXT);")

    }

    fun commonExaminationDB(db: SQLiteDatabase?){

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
                " ck5_1 TEXT," +
                " ck5_2 TEXT," +
                " ck5_3 TEXT," +
                " ck5_4 TEXT," +
                " ck5_5 TEXT," +
                " ck5_6 TEXT," +
                " ck6_1 TEXT," +
                " ck6_2 TEXT," +
                " ck6_3 TEXT," +
                " ck6_4 TEXT," +
                " ck6_5 TEXT," +
                " ck6_6 TEXT," +
                " ck7_1 TEXT," +
                " ck7_2 TEXT," +
                " ck7_3 TEXT," +
                " ck7_4 TEXT," +
                " ck7_5 TEXT," +
                " ck7_6 TEXT," +
                " ck8_1 TEXT," +
                " ck8_2 TEXT," +
                " ck9_1 TEXT," +
                " ck9_2 TEXT," +
                " ck10 TEXT," +
                " ck11 TEXT," +
                " ck12 TEXT," +
                " ck13 TEXT," +
                " ck14 TEXT);")

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun oralSaveLocal(db : SQLiteDatabase, ex : ArrayList<Paper_ORAL>){


        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO ORAL_EXAM" +
                "(exam_date," +
                "exam_no," +
                "name," +
                "first_serial," +
                "last_serial," +
                "category, " +
                "oral_1, oral_2, oral_3, oral_4, oral_5, oral_6, oral_7," +
                "oral_8, oral_9, oral_10, oral_11, oral_12, oral_13, oral_14," +
                "oral_15, oral_16)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.exam_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.oral_1}', '${columnValue.oral_2}', '${columnValue.oral_3}', '${columnValue.oral_4}'" +
                ", '${columnValue.oral_5}', '${columnValue.oral_6}', '${columnValue.oral_7}', '${columnValue.oral_8}'," +
                " '${columnValue.oral_9}', '${columnValue.oral_10}', '${columnValue.oral_11}', '${columnValue.oral_12}'," +
                " '${columnValue.oral_13}', '${columnValue.oral_14}', '${columnValue.oral_15}', '${columnValue.oral_16}'"
                +");")
    }

    fun commonSaveLocal(db : SQLiteDatabase, ex : ArrayList<Paper_COMMON>){

        val columnValue = ex.get(0)

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
                "'${columnValue.exam_date}', '${columnValue.exam_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.mj1_1_1}', '${columnValue.mj1_1_2}', '${columnValue.mj1_2_1}'" +
                ", '${columnValue.mj1_2_2}', '${columnValue.mj1_3_1}', '${columnValue.mj1_3_2}', '${columnValue.mj1_4_1}'" +
                ", '${columnValue.mj1_4_2}', '${columnValue.mj1_5_1}', '${columnValue.mj1_5_2}', '${columnValue.mj1_6_1}'" +
                ", '${columnValue.mj1_6_2}', '${columnValue.mj1_7_1}', '${columnValue.mj1_7_2}'" +
                ", '${columnValue.mj2_1}', '${columnValue.mj2_2}', '${columnValue.mj2_3}', '${columnValue.mj2_4}'" +
                ", '${columnValue.mj2_5}', '${columnValue.mj3}', '${columnValue.mj4}'" +
                ", '${columnValue.mj4_1_1}', '${columnValue.mj4_1_2}', '${columnValue.mj4_2_1}', '${columnValue.mj4_2_2}'" +
                ", '${columnValue.mj4_2_3}', '${columnValue.mj5}', '${columnValue.mj5_1_1}'" +
                ", '${columnValue.mj5_1_2}', '${columnValue.mj5_2_1}', '${columnValue.mj5_2_2}', '${columnValue.mj5_2_3}'" +
                ", '${columnValue.mj6}', '${columnValue.mj6_1}', '${columnValue.mj71}', '${columnValue.mj72}'" +
                ", '${columnValue.mj73}', '${columnValue.mj74}', '${columnValue.mj7_1_11}', '${columnValue.mj7_1_12}'" +
                ", '${columnValue.mj7_1_13}', '${columnValue.mj7_1_14}', '${columnValue.mj7_1_21}', '${columnValue.mj7_1_22}'" +
                ", '${columnValue.mj7_1_23}', '${columnValue.mj7_1_24}', '${columnValue.mj7_1_31}', '${columnValue.mj7_1_32}'" +
                ", '${columnValue.mj7_1_33}', '${columnValue.mj7_1_34}', '${columnValue.mj7_1_41}', '${columnValue.mj7_1_42}'" +
                ", '${columnValue.mj7_1_43}', '${columnValue.mj7_1_44}', '${columnValue.mj7_1_51}', '${columnValue.mj7_1_52}'" +
                ", '${columnValue.mj7_1_53}', '${columnValue.mj7_1_54}', '${columnValue.mj7_1_etc}', '${columnValue.mj7_2_11}'" +
                ", '${columnValue.mj7_2_12}', '${columnValue.mj7_2_13}', '${columnValue.mj7_2_14}', '${columnValue.mj7_2_21}'" +
                ", '${columnValue.mj7_2_22}', '${columnValue.mj7_2_23}', '${columnValue.mj7_2_24}', '${columnValue.mj7_2_31}'" +
                ", '${columnValue.mj7_2_32}', '${columnValue.mj7_2_33}', '${columnValue.mj7_2_34}', '${columnValue.mj7_2_41}'" +
                ", '${columnValue.mj7_2_42}', '${columnValue.mj7_2_43}', '${columnValue.mj7_2_44}', '${columnValue.mj7_2_51}'" +
                ", '${columnValue.mj7_2_52}', '${columnValue.mj7_2_53}', '${columnValue.mj7_2_54}', '${columnValue.mj7_2_etc}'" +
                ", '${columnValue.mj8_1}', '${columnValue.mj8_2_1}', '${columnValue.mj8_2_2}', '${columnValue.mj9_1}'" +
                ", '${columnValue.mj9_2_1}', '${columnValue.mj9_2_2}', '${columnValue.mj10}');")
    }


    fun mentalSaveLocal(db : SQLiteDatabase, ex : ArrayList<Paper_MENTAL>){

        val columnValue = ex.get(0)

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
                "'${columnValue.exam_date}', '${columnValue.exam_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.mj_mtl_1}', '${columnValue.mj_mtl_2}', '${columnValue.mj_mtl_3}', '${columnValue.mj_mtl_4}'" +
                ", '${columnValue.mj_mtl_5}', '${columnValue.mj_mtl_6}', '${columnValue.mj_mtl_7}', '${columnValue.mj_mtl_8}'" +
                ", '${columnValue.mj_mtl_9}', '${columnValue.mj_mtl_sum}');")

    }

    fun cognitiveSaveLocal(db : SQLiteDatabase, ex : ArrayList<Paper_COGNITIVE>){

        val columnValue = ex.get(0)

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
                "'${columnValue.exam_date}', '${columnValue.exam_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.mj_inji_1}', '${columnValue.mj_inji_2}', '${columnValue.mj_inji_3}', '${columnValue.mj_inji_4}'" +
                ", '${columnValue.mj_inji_5}', '${columnValue.mj_inji_6}', '${columnValue.mj_inji_7}', '${columnValue.mj_inji_8}'" +
                ", '${columnValue.mj_inji_9}', '${columnValue.mj_inji_10}', '${columnValue.mj_inji_11}', '${columnValue.mj_inji_12}'" +
                ", '${columnValue.mj_inji_13}', '${columnValue.mj_inji_14}', '${columnValue.mj_inji_15}', '${columnValue.mj_inji_sum}');")

    }

    fun elderlySaveLocal(db : SQLiteDatabase, ex : ArrayList<Paper_ELDERLY>){

        val columnValue = ex.get(0)

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
                "'${columnValue.exam_date}', '${columnValue.exam_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.mj66_1}', '${columnValue.mj66_2}', '${columnValue.mj66_3_1}', '${columnValue.mj66_3_2}'" +
                ", '${columnValue.mj66_3_3}', '${columnValue.mj66_3_4}', '${columnValue.mj66_3_5}', '${columnValue.mj66_3_6}'" +
                ", '${columnValue.mj66_4}', '${columnValue.mj66_5}');")
    }

    fun smokingSaveLocal(db : SQLiteDatabase, ex : ArrayList<Paper_SMOKING>){

        val columnValue = ex.get(0)

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
                "'${columnValue.exam_date}', '${columnValue.exam_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.sg2_spSmoke1}', '${columnValue.sg2_spSmoke2}', '${columnValue.sg2_spSmoke3}', '${columnValue.sg2_spSmoke4}'" +
                ", '${columnValue.sg2_spSmoke5}', '${columnValue.sg2_spSmoke6}', '${columnValue.sg2_spSmoke7}', '${columnValue.sg2_spSmoke8}'" +
                ", '${columnValue.sg2_spSmokeSum}');")
    }


    fun drinkingSaveLocal(db : SQLiteDatabase, ex : ArrayList<Paper_DRINKING>){

        val columnValue = ex.get(0)

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
                "'${columnValue.exam_date}', '${columnValue.exam_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.sg2_spDrink1}', '${columnValue.sg2_spDrink2_1}', '${columnValue.sg2_spDrink2_2}', '${columnValue.sg2_spDrink3}'" +
                ", '${columnValue.sg2_spDrink4}', '${columnValue.sg2_spDrink5}', '${columnValue.sg2_spDrink6}', '${columnValue.sg2_spDrink7}'" +
                ", '${columnValue.sg2_spDrink8}', '${columnValue.sg2_spDrink9}', '${columnValue.sg2_spDrink10}', '${columnValue.sg2_spDrinkSum}');")
    }

    fun exerciseSaveLocal(db: SQLiteDatabase, ex : ArrayList<Paper_EXERCISE>){

        val columnValue = ex.get(0)

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
                "'${columnValue.exam_date}', '${columnValue.exam_bun_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.sg2_spSports1_1}', '${columnValue.sg2_spSports1_2}', '${columnValue.sg2_spSports1_3_1}'" +
                ", '${columnValue.sg2_spSports1_3_2}', '${columnValue.sg2_spSports1_4}', '${columnValue.sg2_spSports1_5}'" +
                ", '${columnValue.sg2_spSports1_6_1}', '${columnValue.sg2_spSports1_6_2}', '${columnValue.sg2_spSports2_1}'" +
                ", '${columnValue.sg2_spSports2_2}', '${columnValue.sg2_spSports2_3_1}', '${columnValue.sg2_spSports2_3_2}'" +
                ", '${columnValue.sg2_spSports3_1}', '${columnValue.sg2_spSports3_2}', '${columnValue.sg2_spSports3_3_1}'" +
                ", '${columnValue.sg2_spSports3_3_2}', '${columnValue.sg2_spSports3_4}', '${columnValue.sg2_spSports3_5}'" +
                ", '${columnValue.sg2_spSports3_6_1}', '${columnValue.sg2_spSports3_6_2}', '${columnValue.sg2_spSports4_1_1}'" +
                ", '${columnValue.sg2_spSports4_1_2}', '${columnValue.sg2_spSports5}', '${columnValue.sg2_spSports6}'" +
                ", '${columnValue.sg2_spSports7}', '${columnValue.sg2_spSports8}', '${columnValue.sg2_spSports9}'" +
                ", '${columnValue.sg2_spSports10}', '${columnValue.sg2_spSports11}', '${columnValue.sg2_spSports12}', '${columnValue.sg2_spSportsSum}');")

    }

    fun nutritionSaveLocal(db: SQLiteDatabase, ex : ArrayList<Paper_NUTRITION>){

        val columnValue = ex.get(0)

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
                "'${columnValue.exam_date}', '${columnValue.exam_bun_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.sg2_spFood1}', '${columnValue.sg2_spFood2}', '${columnValue.sg2_spFood3}'" +
                ", '${columnValue.sg2_spFood4}', '${columnValue.sg2_spFood5}', '${columnValue.sg2_spFood6}'" +
                ", '${columnValue.sg2_spFood7}', '${columnValue.sg2_spFood8}', '${columnValue.sg2_spFood9}'" +
                ", '${columnValue.sg2_spFood10}', '${columnValue.sg2_spFood11}', '${columnValue.sg2_spFoodSum}'" +
                ", '${columnValue.sg2_spHeight}', '${columnValue.sg2_spWeight}', '${columnValue.sg2_spWaistSize}', '${columnValue.sg2_spBmi}'" +
                ", '${columnValue.sg2_spFat1}', '${columnValue.sg2_spFat2}', '${columnValue.sg2_spFat3}');")

    }

    fun cancerSaveLocal(db : SQLiteDatabase, ex : ArrayList<Paper_CANCER>){

        val columnValue = ex.get(0)

        db.execSQL("INSERT INTO CANCER_EXAM" +
                "(exam_date," +
                "exam_no,"  +
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
                "ck4_1, ck4_2, ck4_3, ck4_4, ck4_5, ck4_6, ck4_7, ck4_8, ck5_1, ck5_2, ck5_3, ck5_4, ck5_5," +
                "ck5_6, ck6_1, ck6_2, ck6_3, ck6_4, ck6_5, ck6_6, ck7_1, ck7_2, ck7_3, ck7_4, ck7_5, ck7_6, ck8_1, ck8_2," +
                "ck9_1, ck9_2, ck10, ck11, ck12, ck13, ck14)" +
                " VALUES (" +
                "'${columnValue.exam_date}', '${columnValue.exam_no}', '${columnValue.name}', '${columnValue.first_serial}', '${columnValue.last_serial}'" +
                ", '${columnValue.category}', '${columnValue.ck1}', '${columnValue.ck1_1}', '${columnValue.ck2}', '${columnValue.ck2_1}'" +
                ", '${columnValue.ck3_1}', '${columnValue.ck3_1_1}', '${columnValue.ck3_1_2}', '${columnValue.ck3_1_3}', '${columnValue.ck3_1_4}', '${columnValue.ck3_1_5}'" +
                ", '${columnValue.ck3_2}', '${columnValue.ck3_2_1}', '${columnValue.ck3_2_2}', '${columnValue.ck3_2_3}', '${columnValue.ck3_2_4}', '${columnValue.ck3_2_5}'" +
                ", '${columnValue.ck3_3}', '${columnValue.ck3_3_1}', '${columnValue.ck3_3_2}', '${columnValue.ck3_3_3}', '${columnValue.ck3_3_4}', '${columnValue.ck3_3_5}'" +
                ", '${columnValue.ck3_4}', '${columnValue.ck3_4_1}', '${columnValue.ck3_4_2}', '${columnValue.ck3_4_3}', '${columnValue.ck3_4_4}', '${columnValue.ck3_4_5}'" +
                ", '${columnValue.ck3_5}', '${columnValue.ck3_5_1}', '${columnValue.ck3_5_2}', '${columnValue.ck3_5_3}', '${columnValue.ck3_5_4}', '${columnValue.ck3_5_5}'" +
                ", '${columnValue.ck3_6}', '${columnValue.ck3_6_1}', '${columnValue.ck3_6_2}', '${columnValue.ck3_6_3}', '${columnValue.ck3_6_4}', '${columnValue.ck3_6_5}', '${columnValue.ck3_6_kita}'" +
                ", '${columnValue.ck4_1}', '${columnValue.ck4_2}', '${columnValue.ck4_3}', '${columnValue.ck4_4}'" +
                ", '${columnValue.ck4_5}', '${columnValue.ck4_6}', '${columnValue.ck4_7}', '${columnValue.ck4_8}'" +
                ", '${columnValue.ck5_1}', '${columnValue.ck5_2}', '${columnValue.ck5_3}', '${columnValue.ck5_4}'" +
                ", '${columnValue.ck5_5}', '${columnValue.ck5_6}', '${columnValue.ck6_1}', '${columnValue.ck6_2}'" +
                ", '${columnValue.ck6_3}', '${columnValue.ck6_4}', '${columnValue.ck6_5}', '${columnValue.ck6_6}'" +
                ", '${columnValue.ck7_1}', '${columnValue.ck7_2}', '${columnValue.ck7_3}', '${columnValue.ck7_4}'" +
                ", '${columnValue.ck7_5}', '${columnValue.ck7_6}', '${columnValue.ck8_1}', '${columnValue.ck8_2}'" +
                ", '${columnValue.ck9_1}', '${columnValue.ck9_2}', '${columnValue.ck10}', '${columnValue.ck11}'" +
                ", '${columnValue.ck12}', '${columnValue.ck13}', '${columnValue.ck14}');")
    }

    @SuppressLint("Recycle")
    fun Select_Local_ORAL(db : SQLiteDatabase, getno: String): Cursor{

        var sql = "SELECT * FROM ORAL_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))



        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_COMMON(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM COMMON_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))

        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_COGNITIVE(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM COGNITIVE_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))

        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_ELDERLY(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM ELDERLY_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))

        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_MENTAL(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM MENTAL_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))
        return data
    }

    @SuppressLint("Recycle")
    fun Select_Local_EXERCISE(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM EXERCISE_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))
        return data

    }

    @SuppressLint("Recycle")
    fun Select_Local_SMOKING(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM SMOKING_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))
        return data

    }

    @SuppressLint("Recycle")
    fun Select_Local_DRINKING(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM DRINKING_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))
        return data

    }

    @SuppressLint("Recycle")
    fun Select_Local_CANCER(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM CANCER_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))
        return data

    }

    @SuppressLint("Recycle")
    fun Select_Local_NUTRITION(db : SQLiteDatabase, getno: String): Cursor{


        var sql = "SELECT * FROM NUTRITION_EXAM WHERE exam_no =?;"

        var data = db.rawQuery(sql, arrayOf(getno))
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
            db.delete("LOCALSAVELIST", "exam_no=?", arrayOf(Paper[i].exam_no))


            when (Paper[i].setno)
            {
                CustomAdapter.Category.COMMON -> {
                    println("공통검진입니다.")
                    db.delete("COMMON_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.ORAL -> {
                    println("구강검진입니다.")
                    db.delete("ORAL_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.MENTAL -> {
                    println("정신건강검진입니다.")
                    db.delete("MENTAL_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.COGNITIVE -> {
                    println("인지기능입니다.")
                    db.delete("COGNITIVE_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.ELDERLY -> {
                    println("노인기능입니다.")
                    db.delete("ELDERLY_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.EXERCISE -> {
                    println("운동입니다..")
                    db.delete("EXERCISE_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.NUTRITION -> {
                    println("영양입니다..")
                    db.delete("NUTRITION_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.DRINKING -> {
                    println("음주입니다..")
                    db.delete("DRINKING_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.SMOKING -> {
                    println("흡연입니다..")
                    db.delete("SMOKING_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                CustomAdapter.Category.CANCER -> {
                    println("암입니다..")
                    db.delete("CANCER_EXAM", "exam_no=?", arrayOf(Paper[i].exam_no))
                }
                else -> {
                    println("확인불가")
                }
            }





        }

        db.close()
    }
















    fun deletePaperEach(db : SQLiteDatabase, Paper: Paper)
    {

        println(Paper.name+"님의 문진표 삭제시도")
        println(Paper.setno)

            db.delete("LOCALSAVELIST", "exam_no=?", arrayOf(Paper.exam_no))


        when (Paper.setno)
        {
            PaperArray.SetList.SET1 -> {
                db.delete("COMMON_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET2 -> {
                db.delete("COMMON_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("MENTAL_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET3 -> {
                db.delete("COMMON_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("MENTAL_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("EXERCISE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("NUTRITION_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("DRINKING_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("SMOKING_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET4 -> {
                db.delete("COMMON_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("COGNITIVE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("ELDERLY_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET5 -> {
                db.delete("COMMON_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("COGNITIVE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET6 -> {
                db.delete("COMMON_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("COGNITIVE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("MENTAL_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("EXERCISE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("NUTRITION_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("DRINKING_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("SMOKING_EXAM", "exam_no=?", arrayOf(Paper.exam_no))

                db.delete("ELDERLY_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET7 -> {
                db.delete("ORAL_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET8 -> {
                db.delete("CANCER_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET9 -> {
                db.delete("COGNITIVE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET10 -> {
                db.delete("MENTAL_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET11 -> {
                db.delete("EXERCISE_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("NUTRITION_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("DRINKING_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
                db.delete("SMOKING_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            PaperArray.SetList.SET12 -> {
                db.delete("ELDERLY_EXAM", "exam_no=?", arrayOf(Paper.exam_no))
            }
            else -> {
                println("확인불가")
            }
        }
        db.close()
    }
}