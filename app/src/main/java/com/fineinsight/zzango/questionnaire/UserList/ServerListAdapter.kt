package fineinsight.indonesia.examination.indonesia_healthcare.Activity_ModeMonitoring


import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fineinsight.zzango.questionnaire.DataClass.SelectInfo
import com.fineinsight.zzango.questionnaire.R
import com.fineinsight.zzango.questionnaire.ServerListActivity


class ServerListAdapter (var userList: ArrayList<SelectInfo>,val Activity : ServerListActivity) : RecyclerView.Adapter<ServerListAdapter.ViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.server_list_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return userList.size

    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.userName.text = userList[p1].userName
        p0.userJumin.text = userList[p1].userNumber
        p0.userGender.text = userList[p1].userNumber

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