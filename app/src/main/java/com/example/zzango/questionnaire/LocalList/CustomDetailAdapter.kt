package com.example.zzango.questionnaire.LocalList

import android.app.Activity
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.zzango.questionnaire.R

class CustomDetailAdapter(var PaperList: ArrayList<Paper>, var Activity: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var v : View = LayoutInflater.from(p0?.context).inflate(R.layout.listdetail_layout, p0, false)
        return CustomDetailAdapter.ContentDetailViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        
    }


    class ContentDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val txtCategory = itemView.findViewById(R.id.txtCategory1) as TextView
        val txtName = itemView.findViewById(R.id.txtName1) as TextView
        val txtDate = itemView.findViewById(R.id.txtDate1) as TextView
    }

}