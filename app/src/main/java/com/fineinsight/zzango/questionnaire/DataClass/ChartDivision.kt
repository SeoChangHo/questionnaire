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

            fun local_insert(activity: Activity){

                var sql_db = LocalDBhelper(activity).writableDatabase

                if(MainActivity.chart.isEmpty()){

                    LocalDBhelper(activity).onCreate(sql_db)
                    //LocalDBhelper(this).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!, "SET1")
                    LocalDBhelper(activity).LocalListInsert(sql_db!!, PaperArray.PaperList.Arr_COMMON!!)

                    LocalDBhelper(activity).commonExaminationDB(sql_db)
                    LocalDBhelper(activity).commonSaveLocal(sql_db!!, PaperArray.PaperList.Arr_COMMON!! )

                    //saveCompleteAlert()

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
