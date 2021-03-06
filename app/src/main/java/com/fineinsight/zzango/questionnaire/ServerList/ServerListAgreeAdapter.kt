package com.fineinsight.zzango.questionnaire.ServerList

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fineinsight.zzango.questionnaire.DataClass.SelectInfo
import com.fineinsight.zzango.questionnaire.R

class ServerListAgreeAdapter (var userList: ArrayList<SelectInfo>, val Activity : ServerListAgreeActivity) : RecyclerView.Adapter<ServerListAgreeAdapter.ViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.server_list_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        println("userList.size: ${userList.size}")
        return userList.size
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        val JUMIN = userList[p1].userNumber

        val FirstJUMIN = JUMIN.substring(0, 6)
        val LastJUMIN = JUMIN.substring(6)

        val LASTNUM = JUMIN.substring(LastJUMIN.length-1, LastJUMIN.length).toIntOrNull()

        if (LASTNUM != null)
        {
            if (LASTNUM %2 == 0) p0.userGender.text="여자" else p0.userGender.text="남자"
        }

        p0.userName.text = userList[p1].userName
        p0.userJumin.text = FirstJUMIN
        p0.listlayoutBack.setOnClickListener {

            Activity.loadDetailList(
                    userList[p1].userName,
                    userList[p1].userNumber,
                    userList[p1].dateInfo)
            println("${p1}번째 클릭")
            println("이름: ${userList[p1].userName}")
            println("주민: ${userList[p1].userNumber}")
            println("날짜: ${userList[p1].dateInfo}")
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        //라벨
        val userName: TextView = itemView.findViewById(R.id.txtName)
        val userJumin: TextView = itemView.findViewById(R.id.txtJumin)
        val userGender: TextView = itemView.findViewById(R.id.txtGender)


        val listlayoutBack: ConstraintLayout = itemView.findViewById(R.id.server_list_layout_back)
    }
}