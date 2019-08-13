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
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import kotlinx.android.synthetic.main.save_complete_alert.view.*


class ChartDivision{


        object ChartDivision{

            fun local_insert(activity: Activity, index: Int){

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

                }else{

                    var chartName = MainActivity.chart[MainActivity.chartNumber].chartName
                    var isbool = MainActivity.chart[MainActivity.chartNumber].isbool
                    var index = MainActivity.chart[MainActivity.chartNumber].index

                    if(index+1 <= MainActivity.chart.size){
                        for(i in index..MainActivity.chart.size){


                        }

                    }else{

                    }



                    if(isbool){

                        when(chartName){

                            PaperNameInfo.PC.COMMON.EN_NM -> {
                                activity.startActivity(Intent(activity, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                            }
                            PaperNameInfo.PC.MENTAL.EN_NM -> {

                            }
                            PaperNameInfo.PC.COGNITIVE.EN_NM -> {

                            }
                            PaperNameInfo.PC.ELDERLY.EN_NM -> {

                            }
                            PaperNameInfo.PC.LIFE.EN_NM -> {

                            }
                            PaperNameInfo.PC.ORAL.EN_NM -> {

                            }
                            PaperNameInfo.PC.CANCER.EN_NM -> {

                            }

                        }

                    }


                }


            }


        }

}
