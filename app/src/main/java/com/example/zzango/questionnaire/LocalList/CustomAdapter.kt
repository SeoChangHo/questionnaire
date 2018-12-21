package com.example.zzango.questionnaire.LocalList

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_list.view.*

class CustomAdapter(val PaperList: ArrayList<Paper>, val Activity: Activity): RecyclerView.Adapter<CustomAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.list_layout, p0, false)

        return ViewHolder(v, Activity)
    }

    override fun getItemCount(): Int {
        return PaperList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        var paper: Paper = PaperList[p1]



        p0?.chkbox?.isChecked = paper.isChecked
        p0?.txtCategory?.text = paper.category
        p0?.txtName?.text = paper.name
        p0?.txtSerial?.text = paper.serial_first+"-"+paper.serial_last


        p0?.chkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            println(p1.toString()+"번째 의 값이 "+isChecked+"로 변경되었습니다.")
            paper.isChecked = isChecked


            var count = 0
            for(item in PaperList)
            {
                if(item.isChecked==true)
                {
                    count++
                }
            }

            if(count==0)
            {
                p0.txt.text = "문진표를 선택해주세요."
                p0.btnsave.visibility = View.GONE
                p0.btndelete.visibility = View.GONE
            }
            else
            {
                p0.txt.text = "선택한 "+count.toString()+"개의 문진표를"
                p0.btnsave.visibility = View.VISIBLE
                p0.btndelete.visibility = View.VISIBLE
            }

        }



    }

    class ViewHolder(itemView: View, Activity:Activity): RecyclerView.ViewHolder(itemView)
    {
        val chkbox = itemView.findViewById(R.id.chk_upload) as CheckBox
        val txtCategory = itemView.findViewById(R.id.txtCategory) as TextView
        val txtName = itemView.findViewById(R.id.txtName) as TextView
        val txtSerial = itemView.findViewById(R.id.txtSerial) as TextView

        val txt = Activity.txtBottomMent
        val btnsave = Activity.btnSave
        val btndelete = Activity.btnDelete


    }
}