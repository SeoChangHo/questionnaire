package com.fineinsight.zzango.questionnaire.DataClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.NetworkCapabilities
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat.startActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.*
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.LocalList.*
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.chart
import kotlinx.android.synthetic.main.save_complete_alert.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChartDivision{

    object ChartDivision : RootActivity(){

        fun next_or_save(index: Int) : Boolean{

            if(index+1 <= MainActivity.chart.size) {
                for (i in index + 1..MainActivity.chart.size - 1) {

                    if (chart[i].isbool) {
                        return true
                    }

                }
            }

            return false
        }

        fun local_each_insert(activity: Activity, index: Int){

            var sql_db = LocalDBhelper(activity).writableDatabase

            if(MainActivity.chart.isEmpty()){

                when(index){

                    0 ->{
                        LocalDBhelper(activity).onCreate(sql_db)
                        LocalDBhelper(activity).LocalListInsert(sql_db!!, SavePaper.Total.Array[0] as PublicDataInfo)

                        LocalDBhelper(activity).commonExaminationDB(sql_db)
                        LocalDBhelper(activity).commonSaveLocal(sql_db!!, SavePaper.Total.Array[1] as Paper_COMMON)
                        saveCompleteAlert(activity)
                    }
                    1 ->{
                        LocalDBhelper(activity).onCreate(sql_db)
                        LocalDBhelper(activity).LocalListMentalInsert(sql_db!!, SavePaper.Total.Array[2] as Paper_MENTAL)

                        LocalDBhelper(activity).mentalCreate(sql_db)
                        LocalDBhelper(activity).mentalSaveLocal(sql_db!!, SavePaper.Total.Array[2] as Paper_MENTAL)
                        saveCompleteAlert(activity)
                    }
                    2 ->{
                        LocalDBhelper(activity).onCreate(sql_db)
                        LocalDBhelper(activity).LocalListCognitiveInsert(sql_db!!, SavePaper.Total.Array[3] as Paper_COGNITIVE)

                        LocalDBhelper(activity).cognitiveCreate(sql_db)
                        LocalDBhelper(activity).cognitiveSaveLocal(sql_db!!, SavePaper.Total.Array[3] as Paper_COGNITIVE)
                        saveCompleteAlert(activity)
                    }
                    3 ->{
                        LocalDBhelper(activity).onCreate(sql_db)
                        LocalDBhelper(activity).LocalListElderlyInsert(sql_db!!, SavePaper.Total.Array[4] as Paper_ELDERLY)

                        LocalDBhelper(activity).elderlyCreate(sql_db)
                        LocalDBhelper(activity).elderlySaveLocal(sql_db!!, SavePaper.Total.Array[4] as Paper_ELDERLY)
                        saveCompleteAlert(activity)
                    }
                    4 ->{
                        LocalDBhelper(activity).onCreate(sql_db)
                        LocalDBhelper(activity).LocalListDrinkingInsert(sql_db!!, SavePaper.Total.Array[8] as Paper_DRINKING)

                        LocalDBhelper(activity).exerciseCreate(sql_db)
                        LocalDBhelper(activity).exerciseSaveLocal(sql_db!!, SavePaper.Total.Array[5] as Paper_EXERCISE)

                        LocalDBhelper(activity).nutritionCreate(sql_db)
                        LocalDBhelper(activity).nutritionSaveLocal(sql_db!!, SavePaper.Total.Array[6] as Paper_NUTRITION)

                        LocalDBhelper(activity).smokingCreate(sql_db)
                        LocalDBhelper(activity).smokingSaveLocal(sql_db!!, SavePaper.Total.Array[7] as Paper_SMOKING)

                        LocalDBhelper(activity).drinkingCreate(sql_db)
                        LocalDBhelper(activity).drinkingSaveLocal(sql_db!!, SavePaper.Total.Array[8] as Paper_DRINKING)
                        saveCompleteAlert(activity)
                    }
                    5 ->{
                        LocalDBhelper(activity).onCreate(sql_db)
                        LocalDBhelper(activity).LocalListOralInsert(sql_db!!, SavePaper.Total.Array[9] as Paper_ORAL)

                        LocalDBhelper(activity).oralCreate(sql_db)
                        LocalDBhelper(activity).oralSaveLocal(sql_db!!, SavePaper.Total.Array[9] as Paper_ORAL)
                        saveCompleteAlert(activity)
                    }
                    6 ->{
                        LocalDBhelper(activity).onCreate(sql_db)
                        LocalDBhelper(activity).LocalListCancerInsert(sql_db!!, SavePaper.Total.Array[10] as Paper_CANCER)

                        LocalDBhelper(activity).cancerCreate(sql_db)
                        LocalDBhelper(activity).cancerSaveLocal(sql_db!!, SavePaper.Total.Array[10] as Paper_CANCER)
                        saveCompleteAlert(activity)
                    }

                }

            }

        }

        fun chart_array_insert(activity: Activity, index: Int, isSaveAtLocal:Boolean){

            if(index+1 <= MainActivity.chart.size){

                for(i in index+1..MainActivity.chart.size-1){

                    var chartName = chart[i].chartName
                    var isbool = chart[i].isbool

                    if(isbool){

                        var act = activity

                        println("들어오냐능" + i)

                        when(chartName){

                            PaperNameInfo.PC.COMMON.EN_NM -> {
                                activity.startActivity(Intent(act, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                            PaperNameInfo.PC.MENTAL.EN_NM -> {
                                activity.startActivity(Intent(act, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                            PaperNameInfo.PC.COGNITIVE.EN_NM -> {
                                activity.startActivity(Intent(act, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                            PaperNameInfo.PC.ELDERLY.EN_NM -> {
                                activity.startActivity(Intent(act, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                            PaperNameInfo.PC.LIFE.EN_NM -> {
                                activity.startActivity(Intent(act, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                            PaperNameInfo.PC.ORAL.EN_NM -> {
                                activity.startActivity(Intent(act, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                            PaperNameInfo.PC.CANCER.EN_NM -> {
                                activity.startActivity(Intent(act, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }

                        }

                        break
                    }
                    else
                    {
                        when(chartName)
                        {
                            PaperNameInfo.PC.COMMON.EN_NM ->
                            {
                                SavePaper.Total.Array[1] = Paper_COMMON("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.MENTAL.EN_NM ->
                            {
                                SavePaper.Total.Array[2] = Paper_MENTAL("", "","", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.COGNITIVE.EN_NM ->
                            {
                                SavePaper.Total.Array[3] = Paper_COGNITIVE("", "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.ELDERLY.EN_NM ->
                            {
                                SavePaper.Total.Array[4] = Paper_ELDERLY("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.LIFE.EN_NM ->
                            {
                                SavePaper.Total.Array[5] = Paper_EXERCISE("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[6] = Paper_NUTRITION("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[7] = Paper_SMOKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                                SavePaper.Total.Array[8] = Paper_DRINKING("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.ORAL.EN_NM ->
                            {
                                SavePaper.Total.Array[9] = Paper_ORAL("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                            }

                            PaperNameInfo.PC.CANCER.EN_NM ->
                            {
                                SavePaper.Total.Array[10] = Paper_CANCER("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")

                                if (isSaveAtLocal)
                                {
                                    local_insert(activity)
                                }
                                else
                                {
                                    server_insert(activity)
                                }
                            }
                        }

                    }


                }



            }

        }

        fun local_insert(activity : Activity){

            println("dd??")

            var sql_db = LocalDBhelper(activity).writableDatabase

            if(chart.isNotEmpty()){

                try
                {

                    LocalDBhelper(activity).onCreate(sql_db)
                    LocalDBhelper(activity).LocalListInsert(sql_db, SavePaper.Total.Array[0] as PublicDataInfo)

                    LocalDBhelper(activity).commonExaminationDB(sql_db)
                    LocalDBhelper(activity).commonSaveLocal(sql_db, SavePaper.Total.Array[1] as Paper_COMMON)

                    LocalDBhelper(activity).mentalCreate(sql_db)
                    LocalDBhelper(activity).mentalSaveLocal(sql_db, SavePaper.Total.Array[2] as Paper_MENTAL)

                    LocalDBhelper(activity).cognitiveCreate(sql_db)
                    LocalDBhelper(activity).cognitiveSaveLocal(sql_db, SavePaper.Total.Array[3] as Paper_COGNITIVE)

                    LocalDBhelper(activity).elderlyCreate(sql_db)
                    LocalDBhelper(activity).elderlySaveLocal(sql_db, SavePaper.Total.Array[4] as Paper_ELDERLY)

                    LocalDBhelper(activity).exerciseCreate(sql_db)
                    LocalDBhelper(activity).exerciseSaveLocal(sql_db, SavePaper.Total.Array[5] as Paper_EXERCISE)

                    LocalDBhelper(activity).nutritionCreate(sql_db)
                    LocalDBhelper(activity).nutritionSaveLocal(sql_db, SavePaper.Total.Array[6] as Paper_NUTRITION)

                    LocalDBhelper(activity).smokingCreate(sql_db)
                    LocalDBhelper(activity).smokingSaveLocal(sql_db, SavePaper.Total.Array[7] as Paper_SMOKING)

                    LocalDBhelper(activity).drinkingCreate(sql_db)
                    LocalDBhelper(activity).drinkingSaveLocal(sql_db, SavePaper.Total.Array[8] as Paper_DRINKING)

                    LocalDBhelper(activity).oralCreate(sql_db)
                    LocalDBhelper(activity).oralSaveLocal(sql_db, SavePaper.Total.Array[9] as Paper_ORAL)

                    LocalDBhelper(activity).cancerCreate(sql_db)
                    LocalDBhelper(activity).cancerSaveLocal(sql_db, SavePaper.Total.Array[10] as Paper_CANCER)

                    saveCompleteAlert(activity)
                }
                catch (e:Exception)
                {
                    println(e.message)
                }
            }
            else
            {
                println("else..??")
            }

        }

        fun server_insert(activity: Activity){


//            if(wfm!!.isWifiEnabled || (connectivityManager!!.activeNetwork != null && connectivityManager!!.getNetworkCapabilities(connectivityManager!!.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))) {

                activity.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)


                OracleUtill().newsave_papers().newsavePapersServer(SavePaper.Total.Array).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {

                        if (response.isSuccessful) {

                            if (!response.body()!!.equals("S")) {

                                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                                Toast.makeText(activity, "전송을 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()

                            } else {

                                saveCompleteAlert(activity)

                            }

                        }

                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {


                        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Toast.makeText(activity, "오류 발생 : " + t.toString(), Toast.LENGTH_LONG).show()
                        println(t.toString())
                    }

                })

//            }else{
//
////                    login_appbar_loading_progress.visibility = View.GONE
////                    login_appbar_loading_progress_bg.visibility = View.GONE
//                wifiCheck()
//
//            }

        }

        fun saveCompleteAlert(activity: Activity) {


            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            popup = false

            var dialog = AlertDialog.Builder(activity).create()
            var dialog_view = LayoutInflater.from(activity).inflate(R.layout.save_complete_alert, null)

            dialog.setCancelable(false)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setView(dialog_view)
            dialog_view.save_complete_alert_text.text = "저장이 완료 되었습니다"

            if (!popup) {

                dialog.show().let {

                    popup = true

                }

            }

            var displayMetrics = DisplayMetrics()
            dialog.window.windowManager.defaultDisplay.getMetrics(displayMetrics)
            // The absolute width of the available display size in pixels.
            var displayWidth = displayMetrics.widthPixels
            // The absolute height of the available display size in pixels.
            var displayHeight = displayMetrics.heightPixels

            // Initialize a new window manager layout parameters
            var layoutParams = WindowManager.LayoutParams()

            // Copy the alert dialog window attributes to new layout parameter instance
            layoutParams.copyFrom(dialog.window.attributes)

            // Set the alert dialog window width and height
            // Set alert dialog width equal to screen width 90%
            // int dialogWindowWidth = (int) (displayWidth * 0.9f);
            // Set alert dialog height equal to screen height 90%
            // int dialogWindowHeight = (int) (displayHeight * 0.9f);

            // Set alert dialog width equal to screen width 70%
            var dialogWindowWidth = (displayWidth * 0.7f).toInt()
            // Set alert dialog height equal to screen height 70%
            var dialogWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT

            // Set the width and height for the layout parameters
            // This will bet the width and height of alert dialog
            layoutParams.width = dialogWindowWidth
            layoutParams.height = dialogWindowHeight

            // Apply the newly created layout parameters to the alert dialog window
            dialog.window.attributes = layoutParams


            dialog.setOnDismissListener {

                popup = false
                dialog = null

            }

            dialog_view.return_alert.setOnClickListener {

                MainActivity.login_user_name = ""
                MainActivity.user_first_serial = ""
                MainActivity.user_last_serial = ""

                MainActivity.userLogin!!.text = "사용자 등록하기"
                MainActivity.userImage!!.setImageResource(R.drawable.regi)

                activity.startActivity(Intent(activity, MainActivity::class.java).putExtra("from", "common").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

                dialog.dismiss()

            }

        }

        fun ProgressAction(isShow:Boolean, activity: Activity)
        {
            val Progress_circle = activity.findViewById<ProgressBar>(R.id.Progress_circle)
            val Progress_bg = activity.findViewById<ConstraintLayout>(R.id.Progress_bg)

            if(isShow)
            {

                Progress_circle.visibility = View.VISIBLE
                Progress_bg.visibility = View.VISIBLE
                activity.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
            else
            {
                Progress_circle.visibility = View.GONE
                Progress_bg.visibility = View.GONE
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }

    }

}
