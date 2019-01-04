package com.example.zzango.questionnaire.LocalList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.zzango.questionnaire.*
import kotlinx.android.synthetic.main.activity_list.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(val PaperList: ArrayList<Paper>, var Activity: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var sql_db : SQLiteDatabase? = null


    @SuppressLint("StaticFieldLeak")
    object myCheckBox {
        var chk_each :ArrayList<CheckBox>? = null
        //var chk_each = ArrayList<CheckBox>()
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
    }


    fun CheckBoxInit()
    {
        myCheckBox.chk_each = ArrayList()
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

        myCheckBox.chk_each!!.add(p0.chkbox)

        println("Add 합니다.")

        var paper: Paper = PaperList[p1]

        p0?.chkbox?.isChecked = paper.isChecked
        p0?.txtCategory?.text = getCategory(paper.category)
        p0?.txtName?.text = paper.name
        //p0?.txtSerial?.text = paper.serial_first + "-" + paper.serial_last

        var date = SimpleDateFormat("yyyy-MM-dd").format(Date(paper.exam_no.toLong()))



        p0?.txtDate?.text = date
//
//        println(paper.date)

        println(System.currentTimeMillis())




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
                                data.getString(data.getColumnIndex("mj_mtl_sum"))
                        ))
                        data.moveToNext()
                    }


                    startActivity(Activity, Intent(Activity, CommonExaminationActivity::class.java).putExtra("paper", paper).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
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
                                data.getString(data.getColumnIndex("mj_mtl_sum"))
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
                else ->
                {
                    println("확인되지 않습니다.")
                }
            }


        }




        p0?.chkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            println("*******************************************************")
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")


            println(p1.toString() + "번째 의 값이 " + isChecked + "로 변경되었습니다.")
            paper.isChecked = isChecked


            println("PaperList의 갯수는 "+PaperList.size.toString()+" 입니다.")

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
//                if(Activity.select_all_checkbox.isChecked)
//                {
//                    //전체선택 체크 해제
//                    Activity.select_all_checkbox.isChecked = false
//                }
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
//                    if(!Activity.select_all_checkbox.isChecked)
//                    {
//                        Activity.select_all_checkbox.isChecked = true
//                    }
                }
                else//전체갯수보다 적게 선택되어 있다면
                {
                    println("카운트와 체크박스의 수가 다릅니다.")
//                    if(Activity.select_all_checkbox.isChecked)
//                    {
//                        println("카운트의 수가 전체 갯수보다 작은데 전체선택이 활성화 되어있으므로 전체선택 해제")
//                        Activity.select_all_checkbox.isChecked = false
//                    }
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
            else ->
            {
                return "확인불가"
            }
        }
    }





}