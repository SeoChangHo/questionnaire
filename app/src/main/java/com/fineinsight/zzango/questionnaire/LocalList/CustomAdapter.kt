package com.fineinsight.zzango.questionnaire.LocalList

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
import android.widget.TextView
import com.fineinsight.zzango.questionnaire.LocalDBhelper
import com.fineinsight.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_list.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(var PaperList: ArrayList<Paper>, var Activity: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var sql_db : SQLiteDatabase? = null
    var isFirstTime:Boolean = true
    val EmptyBytes:ByteArray = ByteArray(0)




    @SuppressLint("StaticFieldLeak")
    object myCheckBox {
        var chk_each :ArrayList<CheckBox>? = null
        var chk_All = ArrayList<CheckBox>()
    }

    object Category
    {
        //구강검진
        var ORAL = "ORAL"

        //공통검진
        var COMMON = "COMMON"

        //정신건강
        var MENTAL = "MENTAL"

        //인지기능
        var COGNITIVE = "COGNITIVE"

        //노인기능
        var ELDERLY = "ELDERLY"

        //흡연
        var SMOKING = "SMOKING"

        //음주
        var DRINKING = "DRINKING"

        //암
        var CANCER = "CANCER"

        //운동
        var EXERCISE = "EXERCISE"

        //영양
        var NUTRITION = "NUTRITION"
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
        var v : View = LayoutInflater.from(p0.context).inflate(R.layout.list_layout, p0, false)
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
        p0.chkbox.isChecked = paper.isChecked
//        p0.txtCategory.text = getSetNo(paper.setno)
        p0.txtName.text = paper.name



        try {
            var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.signature,0, paper.signature.size)

            //p0.ImgTest.setImageBitmap(bmp)

        }
        catch (e:Exception)
        {
            println(e.message)
        }


        var date = SimpleDateFormat("yyyy-MM-dd").format(Date(paper.exam_no.toLong()))

        p0.txtDate.text = date

        //Recyclerview Item Click
        p0.constraint.setOnClickListener{


            sql_db = LocalDBhelper(Activity.applicationContext).writableDatabase
            println(paper.exam_no)
            println(paper.signature.size)

            startActivity(Activity, Intent(Activity, ListDetailActivity::class.java).putExtra("paper", paper).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)

        }

        p0.chkbox.setOnCheckedChangeListener(null)

        p0.chkbox.isChecked = PaperList[p1].isChecked


        p0.chkbox.setOnCheckedChangeListener { buttonView, isChecked ->

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
}

class ContentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{

    val chkbox = itemView.findViewById(R.id.chk_upload) as CheckBox
    val txtCategory = itemView.findViewById(R.id.txtCategory) as TextView
    val txtName = itemView.findViewById(R.id.txtName) as TextView
    val txtDate = itemView.findViewById(R.id.txtDate) as TextView

    //var ImgTest = itemView.findViewById(R.id.imageView12) as ImageView

    val constraint = itemView.findViewById(R.id.constraintLayoutArea) as ConstraintLayout
}



fun getSetNo(setno: String):String
    {
        when(setno)
        {
            PaperArray.SetList.SET1 ->
            {
                return "공통"
            }
            PaperArray.SetList.SET2 ->
            {
                return "공통 외 1건"
            }
            PaperArray.SetList.SET3 ->
            {
                return "공통 외 5건"
            }
            PaperArray.SetList.SET4 ->
            {
                return "공통 외 2건"
            }
            PaperArray.SetList.SET5 ->
            {
                return "공통 외 1건"
            }
            PaperArray.SetList.SET6 ->
            {
                return "공통 외 7건"
            }
            PaperArray.SetList.SET7 ->
            {
                return "구강"
            }
            PaperArray.SetList.SET8 ->
            {
                return "암"
            }
            PaperArray.SetList.SET9 ->
            {
                return "인지기능"
            }
            PaperArray.SetList.SET10 ->
            {
                return "우울증"
            }
            PaperArray.SetList.SET11 ->
            {
                return "생활습관"
            }
            PaperArray.SetList.SET12 ->
            {
                return "노인신체기능"
            }
            else ->
            {
                return "확인불가"
            }
        }
    }
