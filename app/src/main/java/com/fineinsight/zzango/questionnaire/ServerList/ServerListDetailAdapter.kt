package com.fineinsight.zzango.questionnaire.ServerList


import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fineinsight.zzango.questionnaire.DataClass.SelectDetailInfo
import com.fineinsight.zzango.questionnaire.R


class ServerListDetailAdapter (var userList: ArrayList<SelectDetailInfo>,val Activity : ServerListDetailActivity) : RecyclerView.Adapter<ServerListDetailAdapter.ViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.serverdetail_list_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.userName.text = userList[p1].userName
        p0.userTable.text = userList[p1].TableName
        //p0.userGender.text = userList[p1].userNumber


        p0.listlayoutBack.setOnClickListener {

        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        //라벨
        val userName: TextView = itemView.findViewById(R.id.txtName)
        val userTable: TextView = itemView.findViewById(R.id.txtTable)
        val userGender: TextView = itemView.findViewById(R.id.txtGender)


        val listlayoutBack: ConstraintLayout = itemView.findViewById(R.id.server_list_layout_back)
    }


}