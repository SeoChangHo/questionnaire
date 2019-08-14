package com.fineinsight.zzango.questionnaire.DataClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.NetworkCapabilities
import android.support.v4.content.ContextCompat.startActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.fineinsight.zzango.questionnaire.*
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.LocalList.*
import com.fineinsight.zzango.questionnaire.MainActivity.Companion.chart
import kotlinx.android.synthetic.main.save_complete_alert.view.*


class ChartDivision{

    object ChartDivision{

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

            fun each_insert(activity: Activity, index: Int){

                var sql_db = LocalDBhelper(activity).writableDatabase

                if(MainActivity.chart.isEmpty()){

                    when(index){

                        1 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)

                            LocalDBhelper(activity).commonExaminationDB(sql_db)
                            LocalDBhelper(activity).commonSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)
                        }
                        2 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListMentalInsert(sql_db!!, PaperArray.PaperList.Arr_MENTAL!!)

                            LocalDBhelper(activity).mentalCreate(sql_db)
                            LocalDBhelper(activity).mentalSaveLocal(sql_db!!, PaperArray.PaperList.Arr_MENTAL!!)

                        }
                        3 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListCognitiveInsert(sql_db!!, PaperArray.PaperList.Arr_COGNITIVE!!)

                            LocalDBhelper(activity).cognitiveCreate(sql_db)
                            LocalDBhelper(activity).cognitiveSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COGNITIVE!!)

                        }
                        4 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListElderlyInsert(sql_db!!, PaperArray.PaperList.Arr_ELDERLY!!)

                            LocalDBhelper(activity).elderlyCreate(sql_db)
                            LocalDBhelper(activity).elderlySaveLocal(sql_db!!, PaperArray.PaperList.Arr_ELDERLY!!)

                        }
                        5 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListDrinkingInsert(sql_db!!, PaperArray.PaperList.Arr_DRINKING!!)

                            LocalDBhelper(activity).exerciseCreate(sql_db)
                            LocalDBhelper(activity).exerciseSaveLocal(sql_db!!, PaperArray.PaperList.Arr_EXERCISE!!)

                            LocalDBhelper(activity).nutritionCreate(sql_db)
                            LocalDBhelper(activity).nutritionSaveLocal(sql_db!!, PaperArray.PaperList.Arr_NUTRITION!!)

                            LocalDBhelper(activity).smokingCreate(sql_db)
                            LocalDBhelper(activity).smokingSaveLocal(sql_db!!, PaperArray.PaperList.Arr_SMOKING!!)

                            LocalDBhelper(activity).drinkingCreate(sql_db)
                            LocalDBhelper(activity).drinkingSaveLocal(sql_db!!, PaperArray.PaperList.Arr_DRINKING!!)
                        }
                        6 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListOralInsert(sql_db!!, PaperArray.PaperList.Arr_ORAL!!)

                            LocalDBhelper(activity).oralCreate(sql_db)
                            LocalDBhelper(activity).oralSaveLocal(sql_db!!, PaperArray.PaperList.Arr_ORAL!!)

                        }
                        7 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListCancerInsert(sql_db!!, PaperArray.PaperList.Arr_CANCER!!)

                            LocalDBhelper(activity).cancerCreate(sql_db)
                            LocalDBhelper(activity).cancerSaveLocal(sql_db!!, PaperArray.PaperList.Arr_CANCER!!)

                        }

                    }

                }

            }

            fun chart_array_insert(activity: Activity, index: Int){


                var index = chart[index+1].index

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
                        }
                        else
                        {
                            //empty byte
                            val eb = ByteArray(0)

                            when(chartName)
                            {
                                PaperNameInfo.PC.COMMON.EN_NM ->
                                {
                                    SavePaper.Total.Array.add(Paper_COMMON("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                }

                                PaperNameInfo.PC.MENTAL.EN_NM ->
                                {
                                    SavePaper.Total.Array.add(Paper_MENTAL("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                }

                                PaperNameInfo.PC.COGNITIVE.EN_NM ->
                                {
                                    SavePaper.Total.Array.add(Paper_COGNITIVE("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                }

                                PaperNameInfo.PC.ELDERLY.EN_NM ->
                                {
                                    SavePaper.Total.Array.add(Paper_ELDERLY("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                }

                                PaperNameInfo.PC.LIFE.EN_NM ->
                                {
                                    SavePaper.Total.Array.add(Paper_EXERCISE("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                    SavePaper.Total.Array.add(Paper_NUTRITION("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                    SavePaper.Total.Array.add(Paper_SMOKING("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                    SavePaper.Total.Array.add(Paper_DRINKING("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                }

                                PaperNameInfo.PC.ORAL.EN_NM ->
                                {
                                    SavePaper.Total.Array.add(Paper_ORAL("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                }

                                PaperNameInfo.PC.CANCER.EN_NM ->
                                {
                                    SavePaper.Total.Array.add(Paper_CANCER("", "", eb, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                }
                            }
                        }


                    }



                }

            }

            fun saveCompleteAlert(activity: Activity) {

//                login_appbar_loading_progress.visibility = View.GONE
//                login_appbar_loading_progress_bg.visibility = View.GONE
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


        }

    companion object {
        var popup = false
    }

}
