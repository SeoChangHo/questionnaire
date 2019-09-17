package com.fineinsight.zzango.questionnaire.LocalList

import android.net.NetworkCapabilities
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.*
import com.fineinsight.zzango.questionnaire.DataClass.PublicDataInfo
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.progress_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListAgreeActivity : RootActivity() {

    var papers = ArrayList<Paper_AGREE_Check>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_agree)

        ListSetting(false)
        btnSetting()
        SelectAllSetting()
    }

    fun ListSetting(bool:Boolean)
    {
        papers = ArrayList()


        val sql_db = LocalDBhelper(this).writableDatabase

        val recyclerView = findViewById<RecyclerView>(R.id.recyclertView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        val data = LocalDBhelper(this).Select_Local_AGREE_ALL(sql_db!!)

        println(data)
        data.moveToFirst()

        while(!data.isAfterLast){

            papers.add(Paper_AGREE_Check(bool,
                    MainActivity.hospital,
                    data.getString(data.getColumnIndex("SYS_DATE")),
                    data.getString(data.getColumnIndex("USER_ID")),
                    data.getString(data.getColumnIndex("UPD_DATE")),
                    data.getString(data.getColumnIndex("BUNHO")),
                    data.getString(data.getColumnIndex("IO_GUBUN")),
                    data.getString(data.getColumnIndex("BASIC")),
                    data.getString(data.getColumnIndex("GUNJIN")),
                    data.getString(data.getColumnIndex("MOBILE")),
                    data.getString(data.getColumnIndex("EVENT")),
                    data.getString(data.getColumnIndex("SMS")),
                    data.getString(data.getColumnIndex("CONSULT")),
                    data.getString(data.getColumnIndex("DAERI")),
                    data.getString(data.getColumnIndex("GOYU")),
                    data.getString(data.getColumnIndex("MINGAM")),
                    data.getString(data.getColumnIndex("SCAN")),
                    data.getString(data.getColumnIndex("CAR_NO")),
                    data.getString(data.getColumnIndex("NAME")),
                    data.getString(data.getColumnIndex("JUMIN")),
                    data.getBlob(data.getColumnIndex("SIGN"))))

            data.moveToNext()

        }

        sql_db.close()

        for (item in papers)
        {
            println("item.SYS_DATE: ${item.SYS_DATE}")
            println("item.JUMIN: ${item.JUMIN}")
        }

        val adapter = CustomAgreeAdapter(papers, this)

        recyclerView.adapter = adapter
    }

    fun btnSetting()
    {
        val sql_db = LocalDBhelper(this).writableDatabase



        //서버저장
        btnSave.setOnClickListener {
            if(wfm!!.isWifiEnabled || (connectivityManager!!.activeNetwork != null && connectivityManager!!.getNetworkCapabilities(connectivityManager!!.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)))
            {
                var removeArr = ArrayList<Paper_AGREE_Check>()

                for (item in papers) {
                    if (item.isChecked == true) {
                        removeArr.add(item)

                        println("&*&*&*&*&*&*")
                        println(item)
                        println("&*&*&*&*&*&*")
                    }
                }

                val mdialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
                val mBuilder = AlertDialog.Builder(this)
                        .setView(mdialogView)
                        .setCancelable(false)
                val mAlertDialog = mBuilder.show()

                UploadAgree(removeArr, 0, removeArr.size, mdialogView, mAlertDialog)
            }else{
                wifiCheck()
            }
        }


        btnDelete.setOnClickListener {
            var removeArr = ArrayList<Paper_AGREE_Check>()
            for(item in papers)
            {
                if(item.isChecked==true)
                {
                    removeArr.add(item)
                }
            }

            LocalDBhelper(this).deleteAgreeMent(sql_db!!, removeArr)
            ListSetting(false)
            btnSave.visibility = View.GONE
            btnDelete.visibility = View.GONE
            constraintLayout_bottom.visibility = View.GONE
            txtBottomMent.text = "동의서를 선택해주세요."
            select_all_checkbox.isChecked = false
        }
    }



    //재귀호출함수
    fun UploadAgree(removeArr:ArrayList<Paper_AGREE_Check>, startIndex:Int, TotalIndex:Int, dialogView: View, Alertdialog: AlertDialog)
    {
        println("업로드 들어옴")
        println("Array의 크기는 "+TotalIndex.toString()+" 개 입니다.")
        println("현재는 "+startIndex.toString()+" 번째 입니다.")



        dialogView.txtMent.text = "${(startIndex+1)}/${TotalIndex} 저장중.."

        val double_start_index = (startIndex+1).toDouble()
        val double_total_index = TotalIndex.toDouble()

        dialogView.stickProgress.progress = ((double_start_index/double_total_index)*100).toInt()

        println("진행률: ${(double_start_index/double_total_index)*100}")


        var UploadAgreeInfo = Paper_AGREE(
                removeArr[startIndex].HOSPITAL,
                removeArr[startIndex].SYS_DATE,
                removeArr[startIndex].USER_ID,
                removeArr[startIndex].UPD_DATE,
                removeArr[startIndex].BUNHO,
                removeArr[startIndex].IO_GUBUN,
                removeArr[startIndex].BASIC,
                removeArr[startIndex].GUNJIN,
                removeArr[startIndex].MOBILE,
                removeArr[startIndex].EVENT,
                removeArr[startIndex].SMS,
                removeArr[startIndex].CONSULT,
                removeArr[startIndex].DAERI,
                removeArr[startIndex].GOYU,
                removeArr[startIndex].MINGAM,
                removeArr[startIndex].SCAN,
                removeArr[startIndex].CAR_NO,
                removeArr[startIndex].NAME,
                removeArr[startIndex].JUMIN,
                removeArr[startIndex].SIGN
        )


        OracleUtill().save_agreepaper().SaveAgreePapers(UploadAgreeInfo).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {
                    if (!response.body()!!.equals("S")) {

                        println(startIndex.toString()+"번째 요청 실패")
                        Alertdialog.dismiss()

                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Toast.makeText(this@ListAgreeActivity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                    } else {

                        val sql_db = LocalDBhelper(this@ListAgreeActivity).writableDatabase
                        LocalDBhelper(this@ListAgreeActivity).deleteAgreeEach(sql_db!!, removeArr[startIndex])

                        if(startIndex+1<TotalIndex)
                        {
                            //할게 더 남아서 재귀호출


                            UploadAgree(removeArr, startIndex+1, TotalIndex, dialogView, Alertdialog)
                        }
                        else
                        {
                            //끝
                            println("모든 업로드가 완료되었습니다.")
                            Alertdialog.dismiss()

                            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this@ListAgreeActivity, "전송 완료", Toast.LENGTH_LONG).show()

                            //LocalDBhelper(this@ListActivity).deletePaper(sql_db!!, removeArr)

                            ListSetting(false)
                            btnSave.visibility = View.GONE
                            btnDelete.visibility = View.GONE
                            txtBottomMent.text = "동의서를 선택해주세요."
                            select_all_checkbox.isChecked = false
                            constraintLayout_bottom.visibility = View.GONE
                        }
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(this@ListAgreeActivity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                println(t.toString())
            }
        })
    }




        //전체선택
        fun SelectAllSetting()
        {
            select_all_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

                //전체선택
                if(isChecked)
                {
                    ListSetting(true)

                    var count = 0
                    for (item in papers) {
                        if (item.isChecked == true) {
                            count++
                        }
                    }
                    if(count>0)
                    {
                        constraintLayout_bottom.visibility = View.VISIBLE
                        txtBottomMent.text = "선택한 " + count.toString() + "개의 동의서를"
                        btnSave.visibility = View.VISIBLE
                        btnDelete.visibility = View.VISIBLE
                    }
                }
                else//전체선택 해제
                {
                    var count = 0

                    for(item in papers)
                    {
                        if(item.isChecked)
                        {
                            count++
                        }
                    }

                    if(count==papers.size)
                    {
                        ListSetting(false)
                    }

                    btnSave.visibility = View.GONE
                    btnDelete.visibility = View.GONE
                    txtBottomMent.text = "동의서를 선택해주세요."
                    constraintLayout_bottom.visibility = View.GONE
                }
            }
        }

    //    override fun onBackPressed() {
    //        if(Progress_bg.visibility != View.VISIBLE){
    //            super.onBackPressed()
    //        }
    //    }


        fun Return_ORAL(exam_no:String, name:String, jumin:String): ArrayList<Paper_ORAL>
        {
            val sql_db = LocalDBhelper(this).writableDatabase
            var PaperArray = ArrayList<Paper_ORAL>()

            val data = LocalDBhelper(this).Select_Local_ORAL(sql_db!!, exam_no, name, jumin)
            data.moveToFirst()

            while(!data.isAfterLast){
                PaperArray.add(Paper_ORAL(
                        data.getString(data.getColumnIndex("exam_date")),
                        data.getString(data.getColumnIndex("exam_no")),
                        data.getString(data.getColumnIndex("name")),
                        data.getString(data.getColumnIndex("first_serial")),
                        data.getString(data.getColumnIndex("last_serial")),
                        data.getString(data.getColumnIndex("category")),
                        data.getString(data.getColumnIndex("oral_1")),
                        data.getString(data.getColumnIndex("oral_2")),
                        data.getString(data.getColumnIndex("oral_3")),
                        data.getString(data.getColumnIndex("oral_4")),
                        data.getString(data.getColumnIndex("oral_5")),
                        data.getString(data.getColumnIndex("oral_6")),
                        data.getString(data.getColumnIndex("oral_7")),
                        data.getString(data.getColumnIndex("oral_8")),
                        data.getString(data.getColumnIndex("oral_9")),
                        data.getString(data.getColumnIndex("oral_10")),
                        data.getString(data.getColumnIndex("oral_11")),
                        data.getString(data.getColumnIndex("oral_12")),
                        data.getString(data.getColumnIndex("oral_13")),
                        data.getString(data.getColumnIndex("oral_14")),
                        data.getString(data.getColumnIndex("oral_15")),
                        data.getString(data.getColumnIndex("oral_16")),
                        data.getString(data.getColumnIndex("oral_Remark"))
                ))
                data.moveToNext()
            }


            sql_db.close()


            return PaperArray
        }



        override fun onResume() {
            super.onResume()

            ProgressAction(false)
        }

    fun ProgressAction(isShow:Boolean)
    {
        if(isShow)
        {
            Progress_circle.visibility = View.VISIBLE
            Progress_bg.visibility = View.VISIBLE
            this.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, 	WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        else
        {
            Progress_circle.visibility = View.GONE
            Progress_bg.visibility = View.GONE
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

}

















