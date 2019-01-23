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

class CustomDetailAdapter(var PaperList: Paper, var Activity: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        println("%%%")
        var v : View = LayoutInflater.from(p0.context).inflate(R.layout.listdetail_layout, p0, false)
        return ContentDetailViewHolder(v)
    }

    override fun getItemCount(): Int {

        println("$$")
        println(GetPaperCount(PaperList.setno))

        return GetPaperCount(PaperList.setno)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        p0 as ContentDetailViewHolder
    }


    class ContentDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val txtCategory = itemView.findViewById(R.id.txtCategory1) as TextView
        val txtName = itemView.findViewById(R.id.txtName1) as TextView
        val txtDate = itemView.findViewById(R.id.txtDate1) as TextView
    }

    fun GetPaperCount(setNo: String):Int
    {
        when(setNo)
        {
            PaperArray.SetList.SET1 ->
            {
                return 1
            }
            PaperArray.SetList.SET2 ->
            {
                return 2
            }
            PaperArray.SetList.SET3 ->
            {
                return 2+4
            }
            PaperArray.SetList.SET4 ->
            {
                return 3
            }
            PaperArray.SetList.SET5 ->
            {
                return 2
            }
            PaperArray.SetList.SET6 ->
            {
                return 8
            }
            PaperArray.SetList.SET1 ->
            {
                return 1
            }
            PaperArray.SetList.SET1 ->
            {
                return 1
            }
            else ->
            {
                return 0
            }
        }
    }
}