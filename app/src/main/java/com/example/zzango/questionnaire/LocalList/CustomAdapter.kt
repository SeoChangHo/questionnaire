package com.example.zzango.questionnaire.LocalList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.zzango.questionnaire.OralExamination
import com.example.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_list.*

class CustomAdapter(val PaperList: ArrayList<Paper>, var Activity: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    @SuppressLint("StaticFieldLeak")
    object myCheckBox {
        //var chk_each :ArrayList<CheckBox>? = null
        var chk_each = ArrayList<CheckBox>()
    }

    fun CheckBoxInit()
    {
//        myCheckBox.chk_each = ArrayList()
//
//        println("ddddd")
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

        var paper: Paper = PaperList[p1]

        p0?.chkbox?.isChecked = paper.isChecked
        p0?.txtCategory?.text = paper.category
        p0?.txtName?.text = paper.name
        //p0?.txtSerial?.text = paper.serial_first + "-" + paper.serial_last
        p0?.txtDate?.text = paper.oral_date

        //List Item 클릭 이벤트 설정하는 곳
        p0?.setOnClickListener(object :ItemOnClickListener{
            override fun onItemClickListener(view: View, post: Int) {
                println(paper.name)
                startActivity(Activity, Intent(Activity, OralExamination::class.java).putExtra("paper", paper).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
            }
        })


        p0?.chkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            println("*******************************************************")
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")


            println(p1.toString() + "번째 의 값이 " + isChecked + "로 변경되었습니다.")
            paper.isChecked = isChecked


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
                        println("zzzz")
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

    class ContentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val chkbox = itemView.findViewById(R.id.chk_upload) as CheckBox
        val txtCategory = itemView.findViewById(R.id.txtCategory) as TextView
        val txtName = itemView.findViewById(R.id.txtName) as TextView
        val txtDate = itemView.findViewById(R.id.txtDate) as TextView

        var CustomItemClick:ItemOnClickListener?=null

        init {
            itemView.setOnClickListener(this)
        }

        fun setOnClickListener(itemClickListener: ItemOnClickListener){
            this.CustomItemClick = itemClickListener
        }

        override fun onClick(v: View?) {
            this.CustomItemClick!!.onItemClickListener(v!!, adapterPosition)
        }

    }







}