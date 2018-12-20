package com.example.zzango.questionnaire.LocalList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.zzango.questionnaire.R

class CustomAdapter(val PaperList: ArrayList<Paper>): RecyclerView.Adapter<CustomAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.list_layout, p0, false)
        return ViewHolder(v)
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
        }



    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val chkbox = itemView.findViewById(R.id.chk_upload) as CheckBox
        val txtCategory = itemView.findViewById(R.id.txtCategory) as TextView
        val txtName = itemView.findViewById(R.id.txtName) as TextView
        val txtSerial = itemView.findViewById(R.id.txtSerial) as TextView




    }
}