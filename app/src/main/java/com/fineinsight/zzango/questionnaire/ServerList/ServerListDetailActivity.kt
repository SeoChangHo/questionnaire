package com.fineinsight.zzango.questionnaire.ServerList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.fineinsight.zzango.questionnaire.DataClass.SelectDetailInfo
import com.fineinsight.zzango.questionnaire.R
import kotlinx.android.synthetic.main.activity_server_list_detail.*

class ServerListDetailActivity : AppCompatActivity() {

    var SelectDetailArr = ArrayList<SelectDetailInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_list_detail)

        GETDetailList()
    }

    fun GETDetailList()
    {
        if (intent.hasExtra("ARR"))
        {
            SelectDetailArr = intent.getSerializableExtra("ARR") as ArrayList<SelectDetailInfo>

            for (item in SelectDetailArr)
            {
                println("TABLENAME: ${item.TableName}")
            }

            serverdetail_recyclertView.layoutManager = LinearLayoutManager(this@ServerListDetailActivity)
            serverdetail_recyclertView.adapter = ServerListDetailAdapter(SelectDetailArr, this@ServerListDetailActivity)
        }
    }
}
