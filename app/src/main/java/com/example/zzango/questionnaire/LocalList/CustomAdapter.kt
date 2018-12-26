package com.example.zzango.questionnaire.LocalList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.TextView
import com.example.zzango.questionnaire.OralExamination
import com.example.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.list_header_layout.view.*

class CustomAdapter(val PaperList: ArrayList<Paper>, var Activity: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val HEADER = 0
    val CONTENT = 1


    @SuppressLint("StaticFieldLeak")
    object myCheckBox {
        var chk_all : CheckBox? = null
        var chk_each : ArrayList<CheckBox>? = null
    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        var v : View? = null

        if(p1 == HEADER) {

            v = LayoutInflater.from(p0?.context).inflate(R.layout.list_header_layout, p0, false)
            myCheckBox.chk_each = ArrayList()

            return HeaderViewHolder(v)

        }else {
            v = LayoutInflater.from(p0?.context).inflate(R.layout.list_layout, p0, false)

            return ContentViewHolder(v)

        }

    }

    override fun getItemCount(): Int {

        if(PaperList.size!=0)
        {
            return PaperList.size + 1
        }
        else
        {
            return 0
        }
    }

    override fun getItemViewType(position: Int): Int {

        when(position){

            0 -> { return HEADER }

            else -> { return CONTENT}

        }

    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {


        if(p1 == HEADER) {

            p0 as HeaderViewHolder

            myCheckBox.chk_all = p0.select_all_checkbox

            p0.select_all_checkbox.setOnCheckedChangeListener{ buttonView, isChecked ->

                println("전체갯수는 "+myCheckBox.chk_each!!.size.toString()+"개네요.")


                if (isChecked==true)
                {
                    println("전체선택")

                    for(item in myCheckBox.chk_each!!)
                    {
                     item.isChecked = true
                    }

                }
                else
                {

                    var count = 0

                    for(item in myCheckBox.chk_each!!)
                    {
                        if(item.isChecked)
                        {
                            count++
                        }
                    }

                    if(count==PaperList.size)
                    {
                        println("전체선택취소")
                        for(item in myCheckBox.chk_each!!)
                        {
                            item.isChecked = false
                        }
                    }
                }
            }

        }else{

            p0 as ContentViewHolder

            myCheckBox.chk_each!!.add(p0.chkbox)

            var paper: Paper = PaperList[p1-1]

            p0?.chkbox?.isChecked = paper.isChecked
            p0?.txtCategory?.text = paper.category
            p0?.txtName?.text = paper.name
            p0?.txtSerial?.text = paper.serial_first + "-" + paper.serial_last

            //List Item 클릭 이벤트 설정하는 곳
            p0?.setOnClickListener(object :ItemOnClickListener{
                override fun onItemClickListener(view: View, post: Int) {
                    println(paper.name)
                    startActivity(Activity, Intent(Activity, OralExamination::class.java).putExtra("paper", paper).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), null)
                }
            })


            p0?.chkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                println(p1.toString() + "번째 의 값이 " + isChecked + "로 변경되었습니다.")
                paper.isChecked = isChecked


                var count = 0
                for (item in PaperList) {
                    if (item.isChecked == true) {
                        count++
                    }
                }
                        //선택된 카운트가 0일때
                        if (count == 0) {
                            //전체선택이 체크되어 있다면
                            if(myCheckBox.chk_all!!.isChecked)
                            {
                                //전체선택 체크 해제
                                myCheckBox.chk_all!!.isChecked = false
                            }
                            Activity.txtBottomMent.text = "문진표를 선택해주세요."
                            Activity.btnSave.visibility = View.GONE
                            Activity.btnDelete.visibility = View.GONE
                        }
                        else//선택된 카운트가 0이 아닐 때
                        {
                            //전체갯수 만큼 선택되어 있다면
                            if(count == myCheckBox.chk_each!!.size )
                            {
                                if(!myCheckBox.chk_all!!.isChecked)
                                {
                                    myCheckBox.chk_all!!.isChecked = true
                                }
                            }
                            else//전체갯수보다 적게 선택되어 있다면
                            {
                                if(myCheckBox.chk_all!!.isChecked)
                                {
                                    myCheckBox.chk_all!!.isChecked = false
                                }
                            }
                            Activity.txtBottomMent.text = "선택한 " + count.toString() + "개의 문진표를"
                            Activity.btnSave.visibility = View.VISIBLE
                            Activity.btnDelete.visibility = View.VISIBLE
                        }
            }
        }
    }

    class ContentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val chkbox = itemView.findViewById(R.id.chk_upload) as CheckBox
        val txtCategory = itemView.findViewById(R.id.txtCategory) as TextView
        val txtName = itemView.findViewById(R.id.txtName) as TextView
        val txtSerial = itemView.findViewById(R.id.txtSerial) as TextView

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

    class HeaderViewHolder(view : View) : RecyclerView.ViewHolder(view){

        var select_all_checkbox = view.select_all_checkbox
    }





}