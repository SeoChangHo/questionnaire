package com.fineinsight.zzango.questionnaire.DataClass

import android.app.Activity
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

            fun each_insert(activity: Activity, index: Int){

                var sql_db = LocalDBhelper(activity).writableDatabase

                if(MainActivity.chart.isEmpty()){

                    when(index){

                        0 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)

                            LocalDBhelper(activity).commonExaminationDB(sql_db)
                            LocalDBhelper(activity).commonSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)
                        }
                        1 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListMentalInsert(sql_db!!, PaperArray.PaperList.Arr_MENTAL!!)

                            LocalDBhelper(activity).mentalCreate(sql_db)
                            LocalDBhelper(activity).mentalSaveLocal(sql_db!!, PaperArray.PaperList.Arr_MENTAL!!)

                        }
                        2 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListCognitiveInsert(sql_db!!, PaperArray.PaperList.Arr_COGNITIVE!!)

                            LocalDBhelper(activity).cognitiveCreate(sql_db)
                            LocalDBhelper(activity).cognitiveSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COGNITIVE!!)

                        }
                        3 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListElderlyInsert(sql_db!!, PaperArray.PaperList.Arr_ELDERLY!!)

                            LocalDBhelper(activity).elderlyCreate(sql_db)
                            LocalDBhelper(activity).elderlySaveLocal(sql_db!!, PaperArray.PaperList.Arr_ELDERLY!!)

                        }
                        4 ->{
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
                        5 ->{
                            LocalDBhelper(activity).onCreate(sql_db)
                            LocalDBhelper(activity).LocalListOralInsert(sql_db!!, PaperArray.PaperList.Arr_ORAL!!)

                            LocalDBhelper(activity).oralCreate(sql_db)
                            LocalDBhelper(activity).oralSaveLocal(sql_db!!, PaperArray.PaperList.Arr_ORAL!!)

                        }
                        6 ->{
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


        }

}
