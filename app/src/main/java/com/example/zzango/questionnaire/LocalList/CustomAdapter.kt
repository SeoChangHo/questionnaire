package com.example.zzango.questionnaire.LocalList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val paper: Paper = PaperList[p1]
        p0?.textViewTitle?.text = paper.name
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val textViewTitle = itemView.findViewById(R.id.txt_Title) as TextView
    }
}