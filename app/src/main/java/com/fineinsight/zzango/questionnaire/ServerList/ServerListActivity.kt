package com.fineinsight.zzango.questionnaire.ServerList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.DataClass.SelectDetailInfo
import com.fineinsight.zzango.questionnaire.DataClass.SelectInfo
import com.fineinsight.zzango.questionnaire.MainActivity
import com.fineinsight.zzango.questionnaire.OracleUtill
import com.fineinsight.zzango.questionnaire.R
import kotlinx.android.synthetic.main.server_list_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerListActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.server_list_activity)

        loadList()

    }


    fun loadList(){

        var selectDate = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        selectDate["DATE"] = "2019-07-05"
        selectDate["AREA"] = MainActivity.hospital

        login_appbar_loading_progress.visibility = View.VISIBLE
        login_appbar_loading_progress_bg.visibility = View.VISIBLE

        OracleUtill().getUserCheck().SelectList(selectDate).enqueue(object : Callback<ArrayList<SelectInfo>> {

            override fun onResponse(call: Call<ArrayList<SelectInfo>>, response: Response<ArrayList<SelectInfo>>) {

                if(response.isSuccessful){

                    if(response.body()!!.size == 0){

                        Toast.makeText(this@ServerListActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE

                    }else{

                        println("성공")
                        var userList = ArrayList<SelectInfo>()

                        for(i in 0..response.body()!!.size - 1){
                            userList.add(
                                    SelectInfo(
                                            response.body()!!.get(i).userName,
                                            response.body()!!.get(i).userNumber,
                                            response.body()!!.get(i).dateInfo
                                    )
                            )
                        }

                        server_recyclertView.layoutManager = LinearLayoutManager(this@ServerListActivity)
                        server_recyclertView.adapter = ServerListAdapter(userList, this@ServerListActivity)

                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE
                    }


                }else{
                    login_appbar_loading_progress.visibility = View.GONE
                    login_appbar_loading_progress_bg.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ArrayList<SelectInfo>>, t: Throwable) {

                login_appbar_loading_progress.visibility = View.GONE
                login_appbar_loading_progress_bg.visibility = View.GONE
            }
        })
    }


    fun loadDetailList(NAME:String, JUMIN:String, DATE:String){

        var MAP = java.util.HashMap<String, String>()
        //selectDate["DATE"] = LocalDate.now().toString()
        MAP["DATE"] = DATE
        MAP["AREA"] = MainActivity.hospital
        MAP["JUMIN"] = JUMIN
        MAP["NAME"] = NAME

        login_appbar_loading_progress.visibility = View.VISIBLE
        login_appbar_loading_progress_bg.visibility = View.VISIBLE

        OracleUtill().getUserDetailCheck().SelectListDetail(MAP).enqueue(object : Callback<ArrayList<SelectDetailInfo>> {

            override fun onResponse(call: Call<ArrayList<SelectDetailInfo>>, response: Response<ArrayList<SelectDetailInfo>>) {

                if(response.isSuccessful){

                    if(response.body()!!.size == 0){

                        Toast.makeText(this@ServerListActivity, "저장된 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE

                    }else{

                        println("성공")
                        var userDetailList = ArrayList<SelectDetailInfo>()

                        for(i in 0..response.body()!!.size - 1){
                            userDetailList.add(
                                    SelectDetailInfo(
                                            response.body()!!.get(i).TableName,
                                            response.body()!!.get(i).userName,
                                            response.body()!!.get(i).userNumber,
                                            response.body()!!.get(i).dateInfo
                                    )
                            )
                        }

                        for (item in userDetailList)
                        {
                            println(item.TableName)
                        }

                        val ServerListDetailActivity = Intent(this@ServerListActivity, ServerListDetailActivity::class.java).putExtra("ARR", userDetailList)
                        startActivity(ServerListDetailActivity)


                        login_appbar_loading_progress.visibility = View.GONE
                        login_appbar_loading_progress_bg.visibility = View.GONE
                    }


                }else{
                    login_appbar_loading_progress.visibility = View.GONE
                    login_appbar_loading_progress_bg.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ArrayList<SelectDetailInfo>>, t: Throwable) {

                login_appbar_loading_progress.visibility = View.GONE
                login_appbar_loading_progress_bg.visibility = View.GONE
            }
        })
    }


}



























