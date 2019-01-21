package com.example.zzango.questionnaire.LocalList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.zzango.questionnaire.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_oral_exam.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(var PaperList: ArrayList<Paper>, var Activity: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var sql_db : SQLiteDatabase? = null
    var isFirstTime:Boolean = true




    @SuppressLint("StaticFieldLeak")
    object myCheckBox {
        var chk_each :ArrayList<CheckBox>? = null
        var chk_All = ArrayList<CheckBox>()
    }

    object Category
    {
        //구강검진
        var ORAL = "oral"

        //공통검진
        var COMMON = "common"

        //정신건강
        var MENTAL = "mental"

        //인지기능
        var COGNITIVE = "cognitive"

        //노인기능
        var ELDERLY = "elderly"

        //흡연
        var SMOKING = "smoking"

        //음주
        var DRINKING = "drinking"

        //암
        var CANCER = "cancer"

        //운동
        var EXERCISE = "exercise"

        //영양
        var NUTRITION = "nutrition"
    }


    fun CheckBoxInit(count:Int)
    {
        println("[CheckBox Init] Count:"+count.toString())
        myCheckBox.chk_each = ArrayList<CheckBox>(PaperList.size)
    }


    //이거 있어야 스크롤 내려도 리사이클러뷰 다시 안그리고 체크박스 유지됨
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var v : View = LayoutInflater.from(p0?.context).inflate(R.layout.list_layout, p0, false)
        return ContentViewHolder(v)
    }
    override fun getItemCount(): Int {
        return PaperList.size
    }
    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        p0 as ContentViewHolder


        if(isFirstTime)
        {
            isFirstTime=false
            CheckBoxInit(PaperList.size)
        }



        p0.chkbox.isChecked = PaperList[p1].isChecked

//        if(PaperList.size!=myCheckBox.chk_each!!.size)
//        {
//            //myCheckBox.chk_each!!.add(p0.chkbox)
//            println(p1.toString()+"번째 자리에 Add 합니다.")
//            myCheckBox.chk_each!![p1] = p0.chkbox
//        }




        var paper: Paper = PaperList[p1]


        println(p1.toString()+"번째의 체크값은 "+paper.isChecked.toString()+"입니다.")
        p0?.chkbox?.isChecked = paper.isChecked
        p0?.txtCategory?.text = getCategory(paper.category)
        p0?.txtName?.text = paper.name



        if(MainActivity.user_stream==paper.signature)
        {
            println("같다.")
        }
        else
        {
            println("다르다.")


            println("paper.signature = ")
            println(paper.signature)
            println(paper.signature.size)
        }

        try {
            var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature,0, paper.signature!!.size)

            p0?.ImgTest.setImageBitmap(bmp)

        }
        catch (e:Exception)
        {
            println(e.message)
        }





        var date = SimpleDateFormat("yyyy-MM-dd").format(Date(paper.exam_no.toLong()))



        p0?.txtDate?.text = date



        //Recyclerview Item Click
        p0?.constraint.setOnClickListener{


            sql_db = LocalDBhelper(Activity.applicationContext).writableDatabase
            println(paper.exam_no)

            when(paper.category)
            {
                Category.ORAL ->
                {
                    var PaperArray = ArrayList<Paper_ORAL>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_ORAL(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_ORAL(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("oral_1")),
                                data.getString(data.getColumnIndex("oral_2")),
                                data.getString(data.getColumnIndex("oral_3")),
                                data.getString(data.getColumnIndex("oral_4")),
                                data.getString(data.getColumnIndex("oral_5")),
                                data.getString(data.getColumnIndex("oral_6")),
                                data.getString(data.getColumnIndex("oral_7")),
                                data.getString(data.getColumnIndex("oral_8")),
                                data.getString(data.getColumnIndex("oral_9")),
                                data.getString(data.getColumnIndex("oral_10")),
                                data.getString(data.getColumnIndex("oral_11")),
                                data.getString(data.getColumnIndex("oral_12")),
                                data.getString(data.getColumnIndex("oral_13")),
                                data.getString(data.getColumnIndex("oral_14")),
                                data.getString(data.getColumnIndex("oral_15")),
                                data.getString(data.getColumnIndex("remark"))
                                ))
                        data.moveToNext()
                    }


                    startActivity(Activity, Intent(Activity, OralExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.COMMON ->
                {

                    var PaperArray = ArrayList<Paper_COMMON>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_COMMON(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_COMMON(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("mj1_1_1")),
                                data.getString(data.getColumnIndex("mj1_1_2")),
                                data.getString(data.getColumnIndex("mj1_2_1")),
                                data.getString(data.getColumnIndex("mj1_2_2")),
                                data.getString(data.getColumnIndex("mj1_3_1")),
                                data.getString(data.getColumnIndex("mj1_3_2")),
                                data.getString(data.getColumnIndex("mj1_4_1")),
                                data.getString(data.getColumnIndex("mj1_4_2")),
                                data.getString(data.getColumnIndex("mj1_5_1")),
                                data.getString(data.getColumnIndex("mj1_5_2")),
                                data.getString(data.getColumnIndex("mj1_6_1")),
                                data.getString(data.getColumnIndex("mj1_6_2")),
                                data.getString(data.getColumnIndex("mj1_7_1")),
                                data.getString(data.getColumnIndex("mj1_7_2")),
                                data.getString(data.getColumnIndex("mj2_1")),
                                data.getString(data.getColumnIndex("mj2_2")),
                                data.getString(data.getColumnIndex("mj2_3")),
                                data.getString(data.getColumnIndex("mj2_4")),
                                data.getString(data.getColumnIndex("mj2_5")),
                                data.getString(data.getColumnIndex("mj3")),
                                data.getString(data.getColumnIndex("mj4")),
                                data.getString(data.getColumnIndex("mj4_1_1")),
                                data.getString(data.getColumnIndex("mj4_1_2")),
                                data.getString(data.getColumnIndex("mj4_2_1")),
                                data.getString(data.getColumnIndex("mj4_2_2")),
                                data.getString(data.getColumnIndex("mj4_2_3")),
                                data.getString(data.getColumnIndex("mj5")),
                                data.getString(data.getColumnIndex("mj5_1_1")),
                                data.getString(data.getColumnIndex("mj5_1_2")),
                                data.getString(data.getColumnIndex("mj5_2_1")),
                                data.getString(data.getColumnIndex("mj5_2_2")),
                                data.getString(data.getColumnIndex("mj5_2_3")),
                                data.getString(data.getColumnIndex("mj6")),
                                data.getString(data.getColumnIndex("mj6_1")),
                                data.getString(data.getColumnIndex("mj71")),
                                data.getString(data.getColumnIndex("mj72")),
                                data.getString(data.getColumnIndex("mj73")),
                                data.getString(data.getColumnIndex("mj74")),
                                data.getString(data.getColumnIndex("mj7_1_11")),
                                data.getString(data.getColumnIndex("mj7_1_12")),
                                data.getString(data.getColumnIndex("mj7_1_13")),
                                data.getString(data.getColumnIndex("mj7_1_14")),
                                data.getString(data.getColumnIndex("mj7_1_21")),
                                data.getString(data.getColumnIndex("mj7_1_22")),
                                data.getString(data.getColumnIndex("mj7_1_23")),
                                data.getString(data.getColumnIndex("mj7_1_24")),
                                data.getString(data.getColumnIndex("mj7_1_31")),
                                data.getString(data.getColumnIndex("mj7_1_32")),
                                data.getString(data.getColumnIndex("mj7_1_33")),
                                data.getString(data.getColumnIndex("mj7_1_34")),
                                data.getString(data.getColumnIndex("mj7_1_41")),
                                data.getString(data.getColumnIndex("mj7_1_42")),
                                data.getString(data.getColumnIndex("mj7_1_43")),
                                data.getString(data.getColumnIndex("mj7_1_44")),
                                data.getString(data.getColumnIndex("mj7_1_51")),
                                data.getString(data.getColumnIndex("mj7_1_52")),
                                data.getString(data.getColumnIndex("mj7_1_53")),
                                data.getString(data.getColumnIndex("mj7_1_54")),
                                data.getString(data.getColumnIndex("mj7_1_etc")),
                                data.getString(data.getColumnIndex("mj7_2_11")),
                                data.getString(data.getColumnIndex("mj7_2_12")),
                                data.getString(data.getColumnIndex("mj7_2_13")),
                                data.getString(data.getColumnIndex("mj7_2_14")),
                                data.getString(data.getColumnIndex("mj7_2_21")),
                                data.getString(data.getColumnIndex("mj7_2_22")),
                                data.getString(data.getColumnIndex("mj7_2_23")),
                                data.getString(data.getColumnIndex("mj7_2_24")),
                                data.getString(data.getColumnIndex("mj7_2_31")),
                                data.getString(data.getColumnIndex("mj7_2_32")),
                                data.getString(data.getColumnIndex("mj7_2_33")),
                                data.getString(data.getColumnIndex("mj7_2_34")),
                                data.getString(data.getColumnIndex("mj7_2_41")),
                                data.getString(data.getColumnIndex("mj7_2_42")),
                                data.getString(data.getColumnIndex("mj7_2_43")),
                                data.getString(data.getColumnIndex("mj7_2_44")),
                                data.getString(data.getColumnIndex("mj7_2_51")),
                                data.getString(data.getColumnIndex("mj7_2_52")),
                                data.getString(data.getColumnIndex("mj7_2_53")),
                                data.getString(data.getColumnIndex("mj7_2_54")),
                                data.getString(data.getColumnIndex("mj7_2_etc")),
                                data.getString(data.getColumnIndex("mj8_1")),
                                data.getString(data.getColumnIndex("mj8_2_1")),
                                data.getString(data.getColumnIndex("mj8_2_2")),
                                data.getString(data.getColumnIndex("mj9_1")),
                                data.getString(data.getColumnIndex("mj9_2_1")),
                                data.getString(data.getColumnIndex("mj9_2_2")),
                                data.getString(data.getColumnIndex("mj10"))
                        ))
                        data.moveToNext()
                    }


                    startActivity(Activity, Intent(Activity, CommonExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.COGNITIVE ->
                {

                    var PaperArray = ArrayList<Paper_COGNITIVE>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_COGNITIVE(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_COGNITIVE(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("mj_inji_1")),
                                data.getString(data.getColumnIndex("mj_inji_2")),
                                data.getString(data.getColumnIndex("mj_inji_3")),
                                data.getString(data.getColumnIndex("mj_inji_4")),
                                data.getString(data.getColumnIndex("mj_inji_5")),
                                data.getString(data.getColumnIndex("mj_inji_6")),
                                data.getString(data.getColumnIndex("mj_inji_7")),
                                data.getString(data.getColumnIndex("mj_inji_8")),
                                data.getString(data.getColumnIndex("mj_inji_9")),
                                data.getString(data.getColumnIndex("mj_inji_10")),
                                data.getString(data.getColumnIndex("mj_inji_11")),
                                data.getString(data.getColumnIndex("mj_inji_12")),
                                data.getString(data.getColumnIndex("mj_inji_13")),
                                data.getString(data.getColumnIndex("mj_inji_14")),
                                data.getString(data.getColumnIndex("mj_inji_15")),
                                data.getString(data.getColumnIndex("mj_inji_sum"))
                        ))
                        data.moveToNext()
                    }

                    startActivity(Activity, Intent(Activity, CognitiveExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.ELDERLY ->
                {
                    var PaperArray = ArrayList<Paper_ELDERLY>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_ELDERLY(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_ELDERLY(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("mj66_1")),
                                data.getString(data.getColumnIndex("mj66_2")),
                                data.getString(data.getColumnIndex("mj66_3_1")),
                                data.getString(data.getColumnIndex("mj66_3_2")),
                                data.getString(data.getColumnIndex("mj66_3_3")),
                                data.getString(data.getColumnIndex("mj66_3_4")),
                                data.getString(data.getColumnIndex("mj66_3_5")),
                                data.getString(data.getColumnIndex("mj66_3_6")),
                                data.getString(data.getColumnIndex("mj66_4")),
                                data.getString(data.getColumnIndex("mj66_5"))
                        ))
                        data.moveToNext()
                    }

                    startActivity(Activity, Intent(Activity, ElderlyExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.EXERCISE ->
                {

                    var PaperArray = ArrayList<Paper_EXERCISE>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_EXERCISE(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_EXERCISE(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("sg2_spSports1_1")),
                                data.getString(data.getColumnIndex("sg2_spSports1_2")),
                                data.getString(data.getColumnIndex("sg2_spSports1_3_1")),
                                data.getString(data.getColumnIndex("sg2_spSports1_3_2")),
                                data.getString(data.getColumnIndex("sg2_spSports1_4")),
                                data.getString(data.getColumnIndex("sg2_spSports1_5")),
                                data.getString(data.getColumnIndex("sg2_spSports1_6_1")),
                                data.getString(data.getColumnIndex("sg2_spSports1_6_2")),
                                data.getString(data.getColumnIndex("sg2_spSports2_1")),
                                data.getString(data.getColumnIndex("sg2_spSports2_2")),
                                data.getString(data.getColumnIndex("sg2_spSports2_3_1")),
                                data.getString(data.getColumnIndex("sg2_spSports2_3_2")),
                                data.getString(data.getColumnIndex("sg2_spSports3_1")),
                                data.getString(data.getColumnIndex("sg2_spSports3_2")),
                                data.getString(data.getColumnIndex("sg2_spSports3_3_1")),
                                data.getString(data.getColumnIndex("sg2_spSports3_3_2")),
                                data.getString(data.getColumnIndex("sg2_spSports3_4")),
                                data.getString(data.getColumnIndex("sg2_spSports3_5")),
                                data.getString(data.getColumnIndex("sg2_spSports3_6_1")),
                                data.getString(data.getColumnIndex("sg2_spSports3_6_2")),
                                data.getString(data.getColumnIndex("sg2_spSports4_1_1")),
                                data.getString(data.getColumnIndex("sg2_spSports4_1_2")),
                                data.getString(data.getColumnIndex("sg2_spSports5")),
                                data.getString(data.getColumnIndex("sg2_spSports6")),
                                data.getString(data.getColumnIndex("sg2_spSports7")),
                                data.getString(data.getColumnIndex("sg2_spSports8")),
                                data.getString(data.getColumnIndex("sg2_spSports9")),
                                data.getString(data.getColumnIndex("sg2_spSports10")),
                                data.getString(data.getColumnIndex("sg2_spSports11")),
                                data.getString(data.getColumnIndex("sg2_spSports12")),
                                data.getString(data.getColumnIndex("sg2_spSportsSum"))
                        ))
                        data.moveToNext()
                    }
                    startActivity(Activity, Intent(Activity, ExerciseExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.NUTRITION ->
                {

                    var PaperArray = ArrayList<Paper_NUTRITION>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_NUTRITION(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_NUTRITION(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("sg2_spFood1")),
                                data.getString(data.getColumnIndex("sg2_spFood2")),
                                data.getString(data.getColumnIndex("sg2_spFood3")),
                                data.getString(data.getColumnIndex("sg2_spFood4")),
                                data.getString(data.getColumnIndex("sg2_spFood5")),
                                data.getString(data.getColumnIndex("sg2_spFood6")),
                                data.getString(data.getColumnIndex("sg2_spFood7")),
                                data.getString(data.getColumnIndex("sg2_spFood8")),
                                data.getString(data.getColumnIndex("sg2_spFood9")),
                                data.getString(data.getColumnIndex("sg2_spFood10")),
                                data.getString(data.getColumnIndex("sg2_spFood11")),
                                data.getString(data.getColumnIndex("sg2_spFoodSum")),
                                data.getString(data.getColumnIndex("sg2_spFatHeight")),
                                data.getString(data.getColumnIndex("sg2_spFatWeight")),
                                data.getString(data.getColumnIndex("sg2_spFatWaistSize")),
                                data.getString(data.getColumnIndex("sg2_spFatBmi")),
                                data.getString(data.getColumnIndex("sg2_spFat1")),
                                data.getString(data.getColumnIndex("sg2_spFat2")),
                                data.getString(data.getColumnIndex("sg2_spFat3"))
                        ))
                        data.moveToNext()
                    }
                    startActivity(Activity, Intent(Activity, NutritionExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.MENTAL ->
                {

                    var PaperArray = ArrayList<Paper_MENTAL>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_MENTAL(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_MENTAL(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("mj_mtl_1")),
                                data.getString(data.getColumnIndex("mj_mtl_2")),
                                data.getString(data.getColumnIndex("mj_mtl_3")),
                                data.getString(data.getColumnIndex("mj_mtl_4")),
                                data.getString(data.getColumnIndex("mj_mtl_5")),
                                data.getString(data.getColumnIndex("mj_mtl_6")),
                                data.getString(data.getColumnIndex("mj_mtl_7")),
                                data.getString(data.getColumnIndex("mj_mtl_8")),
                                data.getString(data.getColumnIndex("mj_mtl_9")),
                                data.getString(data.getColumnIndex("mj_mtl_sum"))
                        ))
                        data.moveToNext()
                    }
                    startActivity(Activity, Intent(Activity, MentalExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.SMOKING ->
                {

                    var PaperArray = ArrayList<Paper_SMOKING>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_SMOKING(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_SMOKING(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("sg2_spSmoke1")),
                                data.getString(data.getColumnIndex("sg2_spSmoke2")),
                                data.getString(data.getColumnIndex("sg2_spSmoke3")),
                                data.getString(data.getColumnIndex("sg2_spSmoke4")),
                                data.getString(data.getColumnIndex("sg2_spSmoke5")),
                                data.getString(data.getColumnIndex("sg2_spSmoke6")),
                                data.getString(data.getColumnIndex("sg2_spSmoke7")),
                                data.getString(data.getColumnIndex("sg2_spSmoke8")),
                                data.getString(data.getColumnIndex("sg2_spSmokeSum"))
                        ))
                        data.moveToNext()
                    }
                    startActivity(Activity, Intent(Activity, SmokingExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.DRINKING ->
                {

                    var PaperArray = ArrayList<Paper_DRINKING>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_DRINKING(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_DRINKING(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("sg2_spDrink1")),
                                data.getString(data.getColumnIndex("sg2_spDrink2_1")),
                                data.getString(data.getColumnIndex("sg2_spDrink2_2")),
                                data.getString(data.getColumnIndex("sg2_spDrink3")),
                                data.getString(data.getColumnIndex("sg2_spDrink4")),
                                data.getString(data.getColumnIndex("sg2_spDrink5")),
                                data.getString(data.getColumnIndex("sg2_spDrink6")),
                                data.getString(data.getColumnIndex("sg2_spDrink7")),
                                data.getString(data.getColumnIndex("sg2_spDrink8")),
                                data.getString(data.getColumnIndex("sg2_spDrink9")),
                                data.getString(data.getColumnIndex("sg2_spDrink10")),
                                data.getString(data.getColumnIndex("sg2_spDrinkSum"))
                        ))
                        data.moveToNext()
                    }
                    startActivity(Activity, Intent(Activity, DrinkingExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
                Category.CANCER ->
                {

                    var PaperArray = ArrayList<Paper_CANCER>()

                    val data = LocalDBhelper(Activity.applicationContext).Select_Local_CANCER(sql_db!!, paper.exam_no)
                    data.moveToFirst()

                    while(!data.isAfterLast){
                        PaperArray.add(Paper_CANCER(
                                data.getString(data.getColumnIndex("exam_date")),
                                data.getString(data.getColumnIndex("exam_no")),
                                data.getString(data.getColumnIndex("name")),
                                data.getString(data.getColumnIndex("first_serial")),
                                data.getString(data.getColumnIndex("last_serial")),
                                data.getString(data.getColumnIndex("category")),
                                data.getString(data.getColumnIndex("ck1")),
                                data.getString(data.getColumnIndex("ck1_1")),
                                data.getString(data.getColumnIndex("ck2")),
                                data.getString(data.getColumnIndex("ck2_1")),
                                data.getString(data.getColumnIndex("ck3_1")),
                                data.getString(data.getColumnIndex("ck3_1_1")),
                                data.getString(data.getColumnIndex("ck3_1_2")),
                                data.getString(data.getColumnIndex("ck3_1_3")),
                                data.getString(data.getColumnIndex("ck3_1_4")),
                                data.getString(data.getColumnIndex("ck3_1_5")),
                                data.getString(data.getColumnIndex("ck3_2")),
                                data.getString(data.getColumnIndex("ck3_2_1")),
                                data.getString(data.getColumnIndex("ck3_2_2")),
                                data.getString(data.getColumnIndex("ck3_2_3")),
                                data.getString(data.getColumnIndex("ck3_2_4")),
                                data.getString(data.getColumnIndex("ck3_2_5")),
                                data.getString(data.getColumnIndex("ck3_3")),
                                data.getString(data.getColumnIndex("ck3_3_1")),
                                data.getString(data.getColumnIndex("ck3_3_2")),
                                data.getString(data.getColumnIndex("ck3_3_3")),
                                data.getString(data.getColumnIndex("ck3_3_4")),
                                data.getString(data.getColumnIndex("ck3_3_5")),
                                data.getString(data.getColumnIndex("ck3_4")),
                                data.getString(data.getColumnIndex("ck3_4_1")),
                                data.getString(data.getColumnIndex("ck3_4_2")),
                                data.getString(data.getColumnIndex("ck3_4_3")),
                                data.getString(data.getColumnIndex("ck3_4_4")),
                                data.getString(data.getColumnIndex("ck3_4_5")),
                                data.getString(data.getColumnIndex("ck3_5")),
                                data.getString(data.getColumnIndex("ck3_5_1")),
                                data.getString(data.getColumnIndex("ck3_5_2")),
                                data.getString(data.getColumnIndex("ck3_5_3")),
                                data.getString(data.getColumnIndex("ck3_5_4")),
                                data.getString(data.getColumnIndex("ck3_5_5")),
                                data.getString(data.getColumnIndex("ck3_6")),
                                data.getString(data.getColumnIndex("ck3_6_1")),
                                data.getString(data.getColumnIndex("ck3_6_2")),
                                data.getString(data.getColumnIndex("ck3_6_3")),
                                data.getString(data.getColumnIndex("ck3_6_4")),
                                data.getString(data.getColumnIndex("ck3_6_5")),
                                data.getString(data.getColumnIndex("ck3_6_kita")),
                                data.getString(data.getColumnIndex("ck4_1")),
                                data.getString(data.getColumnIndex("ck4_2")),
                                data.getString(data.getColumnIndex("ck4_3")),
                                data.getString(data.getColumnIndex("ck4_4")),
                                data.getString(data.getColumnIndex("ck4_5")),
                                data.getString(data.getColumnIndex("ck4_6")),
                                data.getString(data.getColumnIndex("ck4_7")),
                                data.getString(data.getColumnIndex("ck4_8")),
                                data.getString(data.getColumnIndex("ck5_1")),
                                data.getString(data.getColumnIndex("ck5_2")),
                                data.getString(data.getColumnIndex("ck5_3")),
                                data.getString(data.getColumnIndex("ck5_4")),
                                data.getString(data.getColumnIndex("ck5_5")),
                                data.getString(data.getColumnIndex("ck5_6")),
                                data.getString(data.getColumnIndex("ck6_1")),
                                data.getString(data.getColumnIndex("ck6_2")),
                                data.getString(data.getColumnIndex("ck6_3")),
                                data.getString(data.getColumnIndex("ck6_4")),
                                data.getString(data.getColumnIndex("ck6_5")),
                                data.getString(data.getColumnIndex("ck6_6")),
                                data.getString(data.getColumnIndex("ck7_1")),
                                data.getString(data.getColumnIndex("ck7_2")),
                                data.getString(data.getColumnIndex("ck7_3")),
                                data.getString(data.getColumnIndex("ck7_4")),
                                data.getString(data.getColumnIndex("ck7_5")),
                                data.getString(data.getColumnIndex("ck7_6")),
                                data.getString(data.getColumnIndex("ck8_1")),
                                data.getString(data.getColumnIndex("ck8_2")),
                                data.getString(data.getColumnIndex("ck9_1")),
                                data.getString(data.getColumnIndex("ck9_2")),
                                data.getString(data.getColumnIndex("ck10")),
                                data.getString(data.getColumnIndex("ck11")),
                                data.getString(data.getColumnIndex("ck12")),
                                data.getString(data.getColumnIndex("ck13")),
                                data.getString(data.getColumnIndex("ck14"))
                        ))
                        data.moveToNext()
                    }
                    startActivity(Activity, Intent(Activity, CancerExaminationActivity::class.java).putExtra("paper", PaperArray[0]).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }


                else ->
                {
                    println("확인되지 않습니다.")
                }
            }


        }

        p0?.chkbox.setOnCheckedChangeListener(null)

        p0?.chkbox.isChecked = PaperList[p1].isChecked


        p0?.chkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            println("*******************************************************")
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")

            paper.isChecked = isChecked

            println(p1.toString()+"번째의 값을"+isChecked.toString()+"(으)로 변경합니다.")

            var count = 0
            for (item in PaperList) {
                if (item.isChecked == true) {
                    count++
                }
            }

            println("현재 선택된 카운트는 "+count.toString()+" 입니다.")

            //선택된 카운트가 0일때
            if (count == 0) {
                //전체선택이 체크되어 있다면
                if(Activity.select_all_checkbox.isChecked)
                {
                    //전체선택 체크 해제
                    Activity.select_all_checkbox.isChecked = false
                }
                Activity.constraintLayout_bottom.visibility = View.GONE
            }
            else//선택된 카운트가 0이 아닐 때
            {

                println("선택된 카운트가 0이 아니라 "+count.toString()+"입니다.")
                println("그리고 체크박스 배열의 크기는 "+myCheckBox.chk_each!!.size.toString()+" 입니다.")

                //전체갯수 만큼 선택되어 있다면
                if(count == myCheckBox.chk_each!!.size)
                {
                    println("카운트와 체크박스의 수가 같습니다.")
                    if(!Activity.select_all_checkbox.isChecked)
                    {
                        Activity.select_all_checkbox.isChecked = true
                    }
                }
                else//전체갯수보다 적게 선택되어 있다면
                {
                    println("카운트와 체크박스의 수가 다릅니다.")
                    if(Activity.select_all_checkbox.isChecked)
                    {
                        println("카운트의 수가 전체 갯수보다 작은데 전체선택이 활성화 되어있으므로 전체선택 해제")
                        Activity.select_all_checkbox.isChecked = false
                    }
                }
                Activity.constraintLayout_bottom.visibility = View.VISIBLE
                Activity.txtBottomMent.text = "선택한 " + count.toString() + "개의 문진표를"
                Activity.btnSave.visibility = View.VISIBLE
                Activity.btnDelete.visibility = View.VISIBLE
            }
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
            println("*******************************************************")
        }
    }

    class ContentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {

        val chkbox = itemView.findViewById(R.id.chk_upload) as CheckBox
        val txtCategory = itemView.findViewById(R.id.txtCategory) as TextView
        val txtName = itemView.findViewById(R.id.txtName) as TextView
        val txtDate = itemView.findViewById(R.id.txtDate) as TextView

        var ImgTest = itemView.findViewById(R.id.imageView12) as ImageView

        val constraint = itemView.findViewById(R.id.constraintLayoutArea) as ConstraintLayout
    }


    fun getCategory(category: String):String
    {
        when(category)
        {
            Category.ORAL ->
            {
                return "구강"
            }
            Category.COMMON ->
            {
                return "건강(공통)"
            }
            Category.COGNITIVE ->
            {
                return "인지기능"
            }
            Category.ELDERLY ->
            {
                return "노인기능"
            }
            Category.MENTAL ->
            {
                return "정신건강"
            }
            Category.DRINKING ->
            {
                return "음주"
            }
            Category.SMOKING ->
            {
                return "흡연"
            }
            Category.CANCER ->
            {
                return "암"
            }
            Category.EXERCISE ->
            {
                return "운동"
            }
            Category.NUTRITION ->
            {
                return "영양/비만"
            }
            else ->
            {
                return "확인불가"
            }
        }
    }





}