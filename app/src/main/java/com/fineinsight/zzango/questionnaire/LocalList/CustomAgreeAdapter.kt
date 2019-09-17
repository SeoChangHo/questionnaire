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
import com.fineinsight.zzango.questionnaire.AgreementActivity
import com.fineinsight.zzango.questionnaire.LocalDBhelper
import com.fineinsight.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_list.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomAgreeAdapter(var PaperList: ArrayList<Paper_AGREE_Check>, var activity: ListAgreeActivity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var sql_db : SQLiteDatabase? = null
    var isFirstTime:Boolean = true


    @SuppressLint("StaticFieldLeak")
    object myCheckBox {
        var chk_each :ArrayList<CheckBox>? = null
        var chk_All = ArrayList<CheckBox>()
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
        var v : View = LayoutInflater.from(p0.context).inflate(R.layout.agree_list_layout, p0, false)
        return ContentAgreeViewHolder(v)
    }
    override fun getItemCount(): Int {
        return PaperList.size
    }
    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        p0 as ContentAgreeViewHolder


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




        var paper: Paper_AGREE_Check = PaperList[p1]
        println("paper.SYS_DATE: ${paper.SYS_DATE}")

        println(p1.toString()+"번째의 체크값은 "+paper.isChecked.toString()+"입니다.")
        p0.chkbox.isChecked = paper.isChecked
        p0.txtCategory.text = "개인정보 수집/이용 동의서"
        p0.txtName.text = paper.NAME



        try {
            //var bmp: Bitmap = BitmapFactory.decodeByteArray(paper.SIGN,0, paper.SIGN.size)

            //p0.ImgTest.setImageBitmap(bmp)

        }
        catch (e:Exception)
        {
            println(e.message)
        }


        var date = SimpleDateFormat("yyyy-MM-dd").format(Date(paper.SYS_DATE.toLong()))

        p0.txtDate.text = date

        //Recyclerview Item Click
        p0.constraint.setOnClickListener{
            var AgreeListArr = ArrayList<READ_AGREE>()

            AgreeListArr.add(READ_AGREE(
                    PaperList[p1].HOSPITAL,
                    PaperList[p1].SYS_DATE,
                    PaperList[p1].USER_ID,
                    PaperList[p1].UPD_DATE,
                    PaperList[p1].BUNHO,
                    PaperList[p1].IO_GUBUN,
                    PaperList[p1].BASIC,
                    PaperList[p1].GUNJIN,
                    PaperList[p1].MOBILE,
                    PaperList[p1].EVENT,
                    PaperList[p1].SMS,
                    PaperList[p1].CONSULT,
                    PaperList[p1].DAERI,
                    PaperList[p1].GOYU,
                    PaperList[p1].MINGAM,
                    PaperList[p1].SCAN,
                    PaperList[p1].CAR_NO,
                    PaperList[p1].NAME,
                    PaperList[p1].JUMIN,
                    SIGNINFO("Buffer", PaperList[p1].SIGN)
            ))

            activity.ProgressAction(true)
            val AgreementActivity = Intent(activity, AgreementActivity::class.java).putExtra("AgreeListArr", AgreeListArr).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            activity.startActivity(AgreementActivity)
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
                if(activity.select_all_checkbox.isChecked)
                {
                    //전체선택 체크 해제
                    activity.select_all_checkbox.isChecked = false
                }
                activity.constraintLayout_bottom.visibility = View.GONE
            }
            else//선택된 카운트가 0이 아닐 때
            {

                println("선택된 카운트가 0이 아니라 "+count.toString()+"입니다.")
                println("그리고 체크박스 배열의 크기는 "+myCheckBox.chk_each!!.size.toString()+" 입니다.")

                //전체갯수 만큼 선택되어 있다면
                if(count == myCheckBox.chk_each!!.size)
                {
                    println("카운트와 체크박스의 수가 같습니다.")
                    if(!activity.select_all_checkbox.isChecked)
                    {
                        activity.select_all_checkbox.isChecked = true
                    }
                }
                else//전체갯수보다 적게 선택되어 있다면
                {
                    println("카운트와 체크박스의 수가 다릅니다.")
                    if(activity.select_all_checkbox.isChecked)
                    {
                        println("카운트의 수가 전체 갯수보다 작은데 전체선택이 활성화 되어있으므로 전체선택 해제")
                        activity.select_all_checkbox.isChecked = false
                    }
                }
                activity.constraintLayout_bottom.visibility = View.VISIBLE
                activity.txtBottomMent.text = "선택한 " + count.toString() + "개의 문진표를"
                activity.btnSave.visibility = View.VISIBLE
                activity.btnDelete.visibility = View.VISIBLE
            }
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
            println("*******************************************************")
        }
    }

    /*
    fun getPaperCount(ExamNo: String, Name:String, Jumin:String):String
    {

        var FirstPaper = ""
        var count = -1



        if (activity.Return_COMMON(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "공통"
            }
        }

        if (activity.Return_MENTAL(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "정신건강"
            }
        }

        if (activity.Return_COGNITIVE(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "인지기능"
            }
        }

        if (activity.Return_ELDERLY(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "노인기능"
            }
        }

        if (activity.Return_EXERCISE(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "운동"
            }
        }

        if (activity.Return_NUTRITION(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "영양"
            }
        }

        if (activity.Return_SMOKING(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "흡연"
            }
        }

        if (activity.Return_DRINKING(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "음"
            }
        }

        if (activity.Return_ORAL(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "구강"
            }
        }

        if (activity.Return_CANCER(ExamNo, Name, Jumin).size>0)
        {
            count++
            if (FirstPaper.isEmpty())
            {
                FirstPaper = "암"
            }
        }

        println("count: ${count}")

        if (count>0) return "${FirstPaper} 외 ${count}건" else return FirstPaper
    }

     */


}

class ContentAgreeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{

    val chkbox = itemView.findViewById(R.id.chk_upload) as CheckBox
    val txtCategory = itemView.findViewById(R.id.txtCategory) as TextView
    val txtName = itemView.findViewById(R.id.txtName) as TextView
    val txtDate = itemView.findViewById(R.id.txtDate) as TextView




    val constraint = itemView.findViewById(R.id.constraintLayoutArea) as ConstraintLayout
}




