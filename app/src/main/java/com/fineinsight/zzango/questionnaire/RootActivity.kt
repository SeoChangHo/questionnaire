package com.fineinsight.zzango.questionnaire

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.fineinsight.zzango.questionnaire.AdditionalPage.AdditionalArr
import com.fineinsight.zzango.questionnaire.DataClass.SavePaper
import com.fineinsight.zzango.questionnaire.DataClass.SavedListObject
import com.fineinsight.zzango.questionnaire.LocalList.PaperArray
import kotlinx.android.synthetic.main.progressbar2.*
import kotlinx.android.synthetic.main.quit_alert.view.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*

@Suppress("LeakingThis")
open class RootActivity : AppCompatActivity() {

    var popup = false
    var wfm : WifiManager? = null
    var connectivityManager : ConnectivityManager? = null
    var state = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        wfm = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    }

    //radio button check change listener와 연동 bool은 라디오 버튼 체크 값, view는 라디오 버튼과 연계된 wrapper view
    fun checkCondition(bool: Boolean, view : View){

        if(bool){

            view.visibility = View.VISIBLE

        }else{

            view.visibility = View.GONE

        }

    }

    fun checkCondition1(bool: Boolean, view : View){

        if(bool){

            view.visibility = View.GONE

        }else{

            view.visibility = View.VISIBLE

        }

    }

    //로컬에 저장된 검진을 다시 불러와서 확인할때 체크된 사항들을 수정 못하도록 막는 메서드
    fun cannotEditQuestionnaire(vg : ViewGroup){

        for(i in 0 until vg.childCount){

            var child = vg.getChildAt(i)

            if(child is ViewGroup) {

                cannotEditQuestionnaire(child)

            }else{

                if(child is RadioButton || child is EditText || child is Spinner || child is CheckBox){

                    child.isEnabled = false

                }

            }

        }

    }

    fun editTextCondition(bool: Boolean, editText: EditText){

        if(!bool){

            editText.setText("")

        }

    }

    //진행바 제어하는 메서드
    //진행중, 완료, 미실행 으로 구분
    //각 문진을 클릭하면 이동 미실행인 부분은 이동 불가
    //이동할때 해당 액티비티에 check2 메서드로 현재 수정사항을 저장함
    fun controlProgress(context : Context){

        when(MainActivity.chart.isEmpty()){

            //단일 문진 진행
            true -> {

                eachProgress(context)

            }

            //세트 문진 진행
            false -> {

                when {

                    MainActivity.chart[0].isbool && MainActivity.chart[1].isbool
                            && !MainActivity.chart[2].isbool && !MainActivity.chart[3].isbool
                            && !MainActivity.chart[4].isbool -> {

                        progress.visibility = View.VISIBLE
                        progress2.visibility = View.VISIBLE
                        progress_text.text = "공통"
                        progress_text2.text = "우울증"
                        var constraintSet = ConstraintSet()
                        constraintSet.clone(questionnaire_progress_wrapper)
                        println(AdditionalArr.Page.isOralChecked)
                        println(AdditionalArr.Page.isCancerChecked)

                        when {

                            AdditionalArr.Page.isOralChecked && AdditionalArr.Page.isCancerChecked ->{

                                constraintSet.setVisibility(progress3.id, View.VISIBLE)
                                constraintSet.setVisibility(progress4.id, View.VISIBLE)
                                progress_text3.text = "구강"
                                progress_text4.text = "암"

                                when (context.javaClass.kotlin.simpleName) {

                                    "CommonExaminationActivity" -> {

                                        when {

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Mental != null -> {

                                                progress2.isClickable = true
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text.textSize = 26f
                                        progress_text.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "MentalExaminationActivity" -> {

                                        when {

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                progress.isClickable = true

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as MentalExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }
                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.textSize = 26f
                                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "OralExaminationActivity" -> {

                                        when {

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true
                                                progress4.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as OralExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                                progress2.setOnClickListener {
                                                    (context as OralExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.textSize = 26f
                                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "CancerExaminationActivity" -> {

                                        progress.isClickable = true
                                        progress2.isClickable = true
                                        progress3.isClickable = true
                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                        progress.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        progress2.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        progress3.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text4.textSize = 26f
                                        progress_text4.typeface = Typeface.DEFAULT_BOLD

                                    }

                                }

                            }

                            AdditionalArr.Page.isOralChecked -> {

                                constraintSet.setVisibility(progress3.id, View.VISIBLE)
                                progress_text3.text = "구강"

                                when (context.javaClass.kotlin.simpleName) {

                                    "CommonExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if(SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if(SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Mental != null -> {

                                                progress2.isClickable = true
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if(SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text.textSize = 26f
                                        progress_text.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "MentalExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if(SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if(SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                progress.isClickable = true

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as MentalExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }
                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.textSize = 26f
                                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "OralExaminationActivity" -> {

                                        progress.isClickable = true
                                        progress2.isClickable = true
                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                        progress.setOnClickListener {
                                            (context as OralExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        progress2.setOnClickListener {
                                            (context as OralExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.textSize = 26f
                                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                                    }

                                }

                            }

                            AdditionalArr.Page.isCancerChecked -> {

                                constraintSet.setVisibility(progress3.id, View.VISIBLE)
                                progress_text3.text = "암"

                                when (context.javaClass.kotlin.simpleName) {

                                    "CommonExaminationActivity" -> {

                                        when {

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Mental != null -> {

                                                progress2.isClickable = true
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text.textSize = 26f
                                        progress_text.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "MentalExaminationActivity" -> {

                                        when {

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                                        if ((context as MentalExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                progress.isClickable = true

                                                progress.setOnClickListener {
                                                    (context as MentalExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }
                                        }

                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.textSize = 26f
                                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "CancerExaminationActivity" -> {

                                        progress.isClickable = true
                                        progress2.isClickable = true
                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                        progress.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        progress2.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.textSize = 26f
                                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                                    }

                                }

                            }

                            else -> {

                                when (context.javaClass.kotlin.simpleName) {

                                    "CommonExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Mental != null -> {

                                                progress2.isClickable = true
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if(SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text.textSize = 26f
                                        progress_text.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "MentalExaminationActivity" -> {

                                        progress.isClickable = true

                                        progress.setOnClickListener {
                                            (context as MentalExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.textSize = 26f
                                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                                    }

                                }

                            }

                        }

                    }

                    MainActivity.chart[0].isbool && MainActivity.chart[1].isbool
                            && !MainActivity.chart[2].isbool && !MainActivity.chart[3].isbool
                            && MainActivity.chart[4].isbool -> {

                        set2(context)

                    }

                    MainActivity.chart[0].isbool && !MainActivity.chart[1].isbool
                            && MainActivity.chart[2].isbool && MainActivity.chart[3].isbool
                            && !MainActivity.chart[4].isbool ->{

                        progress.visibility = View.VISIBLE
                        progress2.visibility = View.VISIBLE
                        progress3.visibility = View.VISIBLE
                        progress_text.text = "공통"
                        progress_text2.text = "인지기능"
                        progress_text3.text = "노인기능"
                        var constraintSet = ConstraintSet()
                        constraintSet.clone(questionnaire_progress_wrapper)

                        when {

                            AdditionalArr.Page.isOralChecked && AdditionalArr.Page.isCancerChecked ->{
                                constraintSet.setVisibility(progress4.id, View.VISIBLE)
                                constraintSet.setVisibility(progress5.id, View.VISIBLE)
                                progress_text4.text = "구강"
                                progress_text5.text = "암"

                                when (context.javaClass.kotlin.simpleName) {

                                    "CommonExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true
                                                progress5.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress5.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Elderly != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Cognitive != null -> {

                                                progress2.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text.textSize = 26f
                                        progress_text.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "CognitiveExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true
                                                progress5.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress5.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Elderly != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as CognitiveExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.textSize = 26f
                                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "ElderlyExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true
                                                progress4.isClickable = true
                                                progress5.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress5.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true
                                                progress4.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as ElderlyExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }
                                                progress2.setOnClickListener {
                                                    (context as ElderlyExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.textSize = 26f
                                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "OralExaminationActivity" -> {

                                        when {

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress5.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress5.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true
                                                progress3.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                        if ((context as OralExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress4.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text4.textSize = 26f
                                        progress_text4.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "CancerExaminationActivity" -> {

                                        progress.isClickable = true
                                        progress2.isClickable = true
                                        progress3.isClickable = true
                                        progress4.isClickable = true

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                        progress.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                        progress2.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                        progress3.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                        progress4.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress5.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text5.textSize = 26f
                                        progress_text5.typeface = Typeface.DEFAULT_BOLD

                                    }

                                }

                            }

                            AdditionalArr.Page.isOralChecked -> {

                                when (context.javaClass.kotlin.simpleName) {

                                    "CommonExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Elderly != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Cognitive != null -> {

                                                progress2.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text.textSize = 26f
                                        progress_text.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "CognitiveExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Elderly != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as CognitiveExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.textSize = 26f
                                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "ElderlyExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Oral != null -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true
                                                progress4.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as ElderlyExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }
                                                progress2.setOnClickListener {
                                                    (context as ElderlyExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.textSize = 26f
                                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "OralExaminationActivity" -> {

                                        progress.isClickable = true
                                        progress2.isClickable = true
                                        progress3.isClickable = true

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                        progress.setOnClickListener {
                                            if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                if ((context as OralExaminationActivity).check()) {
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }
                                            }
                                        }

                                        progress2.setOnClickListener {
                                            if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                if ((context as OralExaminationActivity).check()) {
                                                    startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }
                                            }
                                        }

                                        progress3.setOnClickListener {
                                            if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                                                if ((context as OralExaminationActivity).check()) {
                                                    startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }
                                            }
                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress4.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text4.textSize = 26f
                                        progress_text4.typeface = Typeface.DEFAULT_BOLD

                                    }

                                }

                            }

                            AdditionalArr.Page.isCancerChecked -> {

                                when (context.javaClass.kotlin.simpleName) {

                                    "CommonExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Elderly != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Cognitive != null -> {

                                                progress2.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text.textSize = 26f
                                        progress_text.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "CognitiveExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true
                                                progress4.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Elderly != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as CognitiveExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.textSize = 26f
                                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "ElderlyExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Cancer != null -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true
                                                progress4.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress4.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                                                        if ((context as ElderlyExaminationActivity).check()) {
                                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress2.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as ElderlyExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }
                                                progress2.setOnClickListener {
                                                    (context as ElderlyExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.textSize = 26f
                                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "CancerExaminationActivity" -> {

                                        progress.isClickable = true
                                        progress2.isClickable = true
                                        progress3.isClickable = true

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                        progress.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                        progress2.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                        progress3.setOnClickListener {
                                            (context as CancerExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress4.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text4.textSize = 26f
                                        progress_text4.typeface = Typeface.DEFAULT_BOLD

                                    }

                                }

                            }

                            else -> {

                                when (context.javaClass.kotlin.simpleName) {

                                    "CommonExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Elderly != null -> {

                                                progress2.isClickable = true
                                                progress3.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            SavePaper.Total.temp_Cognitive != null -> {

                                                progress2.isClickable = true

                                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                                progress2.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                                        if ((context as CommonExaminationActivity).check()) {
                                                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text.textSize = 26f
                                        progress_text.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "CognitiveExaminationActivity" -> {

                                        when{

                                            SavePaper.Total.temp_Elderly != null -> {

                                                progress.isClickable = true
                                                progress3.isClickable = true

                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                                progress3.setOnClickListener {
                                                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                                                        if ((context as CognitiveExaminationActivity).check()) {
                                                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                        }
                                                    }
                                                }

                                            }

                                            else -> {

                                                progress.isClickable = true
                                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                                progress.setOnClickListener {
                                                    (context as CognitiveExaminationActivity).whenTempSave()
                                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                }

                                            }

                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.textSize = 26f
                                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                                    }

                                    "ElderlyExaminationActivity" -> {

                                        progress.isClickable = true
                                        progress2.isClickable = true

                                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                        progress.setOnClickListener {
                                            (context as ElderlyExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                        progress2.setOnClickListener {
                                            (context as ElderlyExaminationActivity).whenTempSave()
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }

                                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                                        constraintSet.applyTo(questionnaire_progress_wrapper)

                                        progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                        progress_text6.textSize = 26f
                                        progress_text6.typeface = Typeface.DEFAULT_BOLD

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

    }

    fun commonProgressTouchListenerCheck(context : Context) : Boolean{

        return SavedListObject.SavedList.savedDataClass.commonSaved && (context as CommonExaminationActivity).check()

    }

    fun eachProgress(context : Context){

        progress.visibility = View.VISIBLE
        progress2.visibility = View.VISIBLE
        progress3.visibility = View.VISIBLE
        progress4.visibility = View.VISIBLE
        progress_text.text = "운동"
        progress_text2.text = "영양"
        progress_text3.text = "흡연"
        progress_text4.text = "음주"
        var constraintSet = ConstraintSet()
        constraintSet.clone(questionnaire_progress_wrapper)

        when(context.javaClass.kotlin.simpleName){

            "ExerciseExaminationActivity" -> {
                //PaperList 쓰지 않음 -> temp object를 따로 생성
                //when문은 진행도를 표시하기 위함
                //각 액티비티별로 check 메서드가 실행될 경우 해당 액티비티 temp data class 초기화
                //temp data class SavedListObject에 해당 액티비티 boolean 값이 false 일 경우 실행
                when {
                    SavePaper.Total.temp_Drinking != null -> {

                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress4.isClickable = true
                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                        progress2.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                if((context as ExerciseExaminationActivity).check()) {
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }
                        progress3.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                if ((context as ExerciseExaminationActivity).check()) {
                                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }
                        progress4.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                if ((context as ExerciseExaminationActivity).check()) {
                                    startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                    }
                    SavePaper.Total.temp_Smoking != null -> {

                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress4.isClickable = false
                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                        progress2.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                if ((context as ExerciseExaminationActivity).check()) {
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }
                        progress3.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                if ((context as ExerciseExaminationActivity).check()) {
                                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                    }
                    SavePaper.Total.temp_Nutrition != null -> {

                        progress2.isClickable = true
                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                        progress2.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                if ((context as ExerciseExaminationActivity).check()) {
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                    }

                }

                constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                constraintSet.applyTo(questionnaire_progress_wrapper)

                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text.textSize = 26f
                progress_text.typeface = Typeface.DEFAULT_BOLD

            }

            "NutritionExaminationActivity" -> {

                when {
                    SavePaper.Total.temp_Drinking != null -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress4.isClickable = true
                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                        progress.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                if ((context as NutritionExaminationActivity).check()) {
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }
                        progress3.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                if ((context as NutritionExaminationActivity).check()) {
                                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }
                        progress4.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                if ((context as NutritionExaminationActivity).check()) {
                                    startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                    }
                    SavePaper.Total.temp_Smoking != null -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                        progress.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                if ((context as NutritionExaminationActivity).check()) {
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }
                        progress3.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                if ((context as NutritionExaminationActivity).check()) {
                                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                    }
                    else -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))

                        progress.setOnClickListener {
                            (context as NutritionExaminationActivity).whenTempSave()
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                    }

                }

                constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                constraintSet.applyTo(questionnaire_progress_wrapper)
                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text2.textSize = 26f
                progress_text2.typeface = Typeface.DEFAULT_BOLD

            }

            "SmokingExaminationActivity" -> {

                when {
                    SavePaper.Total.temp_Drinking != null -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress4.isClickable = true
                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                        progress.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                if ((context as SmokingExaminationActivity).check()) {
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }
                        progress2.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                if ((context as SmokingExaminationActivity).check()) {
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }
                        progress4.setOnClickListener {
                            if(SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                if ((context as SmokingExaminationActivity).check()) {
                                    startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                            }
                        }

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                    }
                    else -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress3.isClickable = true

                        progress.setOnClickListener {
                            (context as SmokingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress2.setOnClickListener {
                            (context as SmokingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                    }

                }

                constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                constraintSet.applyTo(questionnaire_progress_wrapper)
                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text3.textSize = 26f
                progress_text3.typeface = Typeface.DEFAULT_BOLD

            }

            "DrinkingExaminationActivity" -> {

                progress.isClickable = true
                progress2.isClickable = true
                progress3.isClickable = true
                progress4.isClickable = true

                progress.setOnClickListener {
                    (context as DrinkingExaminationActivity).whenTempSave()
                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
                progress2.setOnClickListener {
                    (context as DrinkingExaminationActivity).whenTempSave()
                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
                progress3.setOnClickListener {
                    (context as DrinkingExaminationActivity).whenTempSave()
                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }

                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                constraintSet.connect(triangle.id, ConstraintSet.START, progress4.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                constraintSet.applyTo(questionnaire_progress_wrapper)
                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                progress_text4.textSize = 26f
                progress_text4.typeface = Typeface.DEFAULT_BOLD

            }

            else -> { questionnaire_progress_wrapper.visibility = View.GONE }

        }

    }

    fun set2(context : Context){
        progress.visibility = View.VISIBLE
        progress2.visibility = View.VISIBLE
        progress3.visibility = View.VISIBLE
        progress4.visibility = View.VISIBLE
        progress5.visibility = View.VISIBLE
        progress6.visibility = View.VISIBLE
        progress_text.text = "공통"
        progress_text2.text = "우울증"
        progress_text3.text = "운동"
        progress_text4.text = "영양"
        progress_text5.text = "흡연"
        progress_text6.text = "음주"
        var constraintSet = ConstraintSet()
        constraintSet.clone(questionnaire_progress_wrapper)


        if(SavedListObject.SavedList.savedDataClass.oralSaved) {
            progress8.isClickable = true
            progress_text8.setTextColor(resources.getColor(R.color.mainBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
            progress8.setOnClickListener {
                if(commonProgressTouchListenerCheck(context)){
                    startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }

        if(SavedListObject.SavedList.savedDataClass.drinkingSaved) {
            progress7.isClickable = true
            progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
            progress7.setOnClickListener {
                if(commonProgressTouchListenerCheck(context)){
                    startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }

        if(SavedListObject.SavedList.savedDataClass.smokingSaved) {
            progress6.isClickable = true
            progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
            progress6.setOnClickListener {
                if(commonProgressTouchListenerCheck(context)){
                    startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }

        if(SavedListObject.SavedList.savedDataClass.nutritionSaved) {
            progress5.isClickable = true
            progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
            progress5.setOnClickListener {
                if(commonProgressTouchListenerCheck(context)){
                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }


        if(SavedListObject.SavedList.savedDataClass.exerciseSaved) {
            progress4.isClickable = true
            progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
            progress4.setOnClickListener {
                if(commonProgressTouchListenerCheck(context)){
                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }


        if(SavedListObject.SavedList.savedDataClass.mentalSaved) {
            progress3.isClickable = true
            progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
            progress3.setOnClickListener {
                if(commonProgressTouchListenerCheck(context)){
                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }


        if(SavedListObject.SavedList.savedDataClass.commonSaved) {
            progress2.isClickable = true
            progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)
            progress2.setOnClickListener {
                if(commonProgressTouchListenerCheck(context)){
                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }

        when {

            AdditionalArr.Page.isOralChecked && AdditionalArr.Page.isCancerChecked -> {

                constraintSet.setVisibility(progress7.id, View.VISIBLE)
                constraintSet.setVisibility(progress8.id, View.VISIBLE)
                progress_text7.text = "구강"
                progress_text8.text = "암"

                when (context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

//                        when {
//
//                            SavePaper.Total.temp_Cancer != null -> {
//
//
//
//                            }
//
//                            SavePaper.Total.temp_Oral != null -> {
//
//
//
//                            }
//
//                            SavePaper.Total.temp_Drinking != null -> {
//
//
//
//                            }
//
//                            SavePaper.Total.temp_Smoking != null -> {
//
//
//
//                            }
//
//                            SavePaper.Total.temp_Nutrition != null -> {
//
//
//
//                            }
//
//                            SavePaper.Total.temp_Exercise != null -> {
//
//
//
//                            }
//
//                            SavePaper.Total.temp_Mental != null -> {
//
//
//
//                            }
//
//                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress8.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text.textSize = 26f
                        progress_text.typeface = Typeface.DEFAULT_BOLD

                    }

                    "MentalExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true
                                progress8.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text8.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress8.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Exercise != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as MentalExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress8.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text2.textSize = 26f
                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                    }

                    "ExerciseExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as ExerciseExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as ExerciseExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress8.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.textSize = 26f
                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                    }

                    "NutritionExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress4.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress8.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.textSize = 26f
                        progress_text4.typeface = Typeface.DEFAULT_BOLD

                    }

                    "SmokingExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress4.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress5.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress8.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text5.textSize = 26f
                        progress_text5.typeface = Typeface.DEFAULT_BOLD

                    }

                    "DrinkingExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress4.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress5.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress6.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress8.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text6.textSize = 26f
                        progress_text6.typeface = Typeface.DEFAULT_BOLD

                    }

                    "OralExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as OralExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as OralExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as OralExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress4.setOnClickListener {
                                    (context as OralExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress5.setOnClickListener {
                                    (context as OralExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                                progress6.setOnClickListener {
                                    (context as OralExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress7.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress8.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text7.textSize = 26f
                        progress_text7.typeface = Typeface.DEFAULT_BOLD

                    }

                    "CancerExaminationActivity" -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress4.isClickable = true
                        progress5.isClickable = true
                        progress6.isClickable = true
                        progress7.isClickable = true

                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress7.id, ConstraintSet.END)

                        progress.setOnClickListener {
                            (context as CancerExaminationActivity).whenTempSave()
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress2.setOnClickListener {
                            (context as CancerExaminationActivity).whenTempSave()
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress3.setOnClickListener {
                            (context as CancerExaminationActivity).whenTempSave()
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress4.setOnClickListener {
                            (context as CancerExaminationActivity).whenTempSave()
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress5.setOnClickListener {
                            (context as CancerExaminationActivity).whenTempSave()
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        progress6.setOnClickListener {
                            (context as CancerExaminationActivity).whenTempSave()
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        progress7.setOnClickListener {
                            (context as CancerExaminationActivity).whenTempSave()
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress8.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress8.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text8.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text8.textSize = 26f
                        progress_text8.typeface = Typeface.DEFAULT_BOLD

                    }

                }

            }

            AdditionalArr.Page.isOralChecked -> {

                constraintSet.setVisibility(progress7.id, View.VISIBLE)
                progress_text7.text = "구강"

                when (context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Oral != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Exercise != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Mental != null -> {

                                progress2.isClickable = true
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text.textSize = 26f
                        progress_text.typeface = Typeface.DEFAULT_BOLD

                    }

                    "MentalExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Exercise != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as MentalExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text2.textSize = 26f
                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                    }

                    "ExerciseExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as ExerciseExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as ExerciseExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.textSize = 26f
                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                    }

                    "NutritionExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress4.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.textSize = 26f
                        progress_text4.typeface = Typeface.DEFAULT_BOLD

                    }

                    "SmokingExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress4.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress5.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text5.textSize = 26f
                        progress_text5.typeface = Typeface.DEFAULT_BOLD

                    }

                    "DrinkingExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Oral != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress4.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress5.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress6.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text6.textSize = 26f
                        progress_text6.typeface = Typeface.DEFAULT_BOLD

                    }

                    "OralExaminationActivity" -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress4.isClickable = true
                        progress5.isClickable = true
                        progress6.isClickable = true

                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                        progress.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress2.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress3.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress4.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress5.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        progress6.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress7.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text7.textSize = 26f
                        progress_text7.typeface = Typeface.DEFAULT_BOLD

                    }

                }

            }

            AdditionalArr.Page.isCancerChecked -> {

                constraintSet.setVisibility(progress7.id, View.VISIBLE)
                progress_text7.text = "암"

                when (context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Exercise != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Mental != null -> {

                                progress2.isClickable = true
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text.textSize = 26f
                        progress_text.typeface = Typeface.DEFAULT_BOLD

                    }

                    "MentalExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Exercise != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as MentalExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text2.textSize = 26f
                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                    }

                    "ExerciseExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as ExerciseExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as ExerciseExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.textSize = 26f
                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                    }

                    "NutritionExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress4.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.textSize = 26f
                        progress_text4.typeface = Typeface.DEFAULT_BOLD

                    }

                    "SmokingExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress6.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress4.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress5.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text5.textSize = 26f
                        progress_text5.typeface = Typeface.DEFAULT_BOLD

                    }

                    "DrinkingExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Cancer != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress7.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress7.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                                        if ((context as DrinkingExaminationActivity).check()) {
                                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress4.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress5.setOnClickListener {
                                    (context as DrinkingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress6.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text6.textSize = 26f
                        progress_text6.typeface = Typeface.DEFAULT_BOLD

                    }

                    "CancerExaminationActivity" -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress4.isClickable = true
                        progress5.isClickable = true
                        progress6.isClickable = true

                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress6.id, ConstraintSet.END)

                        progress.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress2.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress3.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress4.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress5.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        progress6.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress7.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress7.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text7.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text7.textSize = 26f
                        progress_text7.typeface = Typeface.DEFAULT_BOLD

                    }

                }

            }

            else -> {

                when (context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Drinking != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Exercise != null -> {

                                progress2.isClickable = true
                                progress3.isClickable = true

                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Mental != null -> {

                                progress2.isClickable = true
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress.id, ConstraintSet.END)

                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                                        if ((context as CommonExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text.textSize = 26f
                        progress_text.typeface = Typeface.DEFAULT_BOLD

                    }

                    "MentalExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Exercise != null -> {

                                progress.isClickable = true
                                progress3.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                                        if ((context as MentalExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as MentalExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress2.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress2.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text2.textSize = 26f
                        progress_text2.typeface = Typeface.DEFAULT_BOLD

                    }

                    "ExerciseExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            SavePaper.Total.temp_Nutrition != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                                        if ((context as ExerciseExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress2.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as ExerciseExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as ExerciseExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress3.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress3.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.textSize = 26f
                        progress_text3.typeface = Typeface.DEFAULT_BOLD

                    }

                    "NutritionExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            SavePaper.Total.temp_Smoking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress5.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress5.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                                        if ((context as NutritionExaminationActivity).check()) {
                                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }

                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress3.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as NutritionExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress4.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress4.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.textSize = 26f
                        progress_text4.typeface = Typeface.DEFAULT_BOLD

                    }

                    "SmokingExaminationActivity" -> {

                        when {

                            SavePaper.Total.temp_Drinking != null -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true
                                progress6.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress2.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress3.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress4.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                                progress6.setOnClickListener {
                                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                                        if ((context as SmokingExaminationActivity).check()) {
                                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        }
                                    }
                                }
                            }

                            else -> {

                                progress.isClickable = true
                                progress2.isClickable = true
                                progress3.isClickable = true
                                progress4.isClickable = true

                                progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                                progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))

                                constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress4.id, ConstraintSet.END)

                                progress.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress2.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress3.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }
                                progress4.setOnClickListener {
                                    (context as SmokingExaminationActivity).whenTempSave()
                                    startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                }

                            }

                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress5.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress5.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text5.textSize = 26f
                        progress_text5.typeface = Typeface.DEFAULT_BOLD

                    }

                    "DrinkingExaminationActivity" -> {

                        progress.isClickable = true
                        progress2.isClickable = true
                        progress3.isClickable = true
                        progress4.isClickable = true
                        progress5.isClickable = true

                        progress_text.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text2.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text3.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text4.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text5.setTextColor(resources.getColor(R.color.mainBlue, null))

                        constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, progress5.id, ConstraintSet.END)

                        progress.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress2.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress3.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress4.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                        progress5.setOnClickListener {
                            (context as DrinkingExaminationActivity).whenTempSave()
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }

                        constraintSet.connect(triangle.id, ConstraintSet.START, progress6.id, ConstraintSet.START)
                        constraintSet.connect(triangle.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.connect(questionnaire_progressbg.id, ConstraintSet.END, progress6.id, ConstraintSet.END)
                        constraintSet.applyTo(questionnaire_progress_wrapper)

                        progress_text6.setTextColor(resources.getColor(R.color.mainBlue, null))
                        progress_text6.textSize = 26f
                        progress_text6.typeface = Typeface.DEFAULT_BOLD

                    }

                }

            }

        }
    }

    //뷰에 포커스를 총괄하는 메서드
    open fun focusControl(view : View){

        if(view !is EditText){

            if(view !is RadioButton){

                if(view is ConstraintLayout) {

                    if(view.id != R.id.smoking_question_constraintLayout && view.id != R.id.drinking_question_constraintLayout) {

                        (view as View).isFocusableInTouchMode = true

                        view.requestFocus()

                        (view as View).isFocusableInTouchMode = false

                    }else{

                        (view.parent as View).isFocusableInTouchMode = true

                        (view.parent as View).requestFocus()

                        (view.parent as View).isFocusableInTouchMode = false

                    }

                }else {

                    (view.parent as View).isFocusableInTouchMode = true

                    (view.parent as View).requestFocus()

                    (view.parent as View).isFocusableInTouchMode = false

                }

            }else{

                if((view.parent.parent as View).id != R.id.smoking_question_constraintLayout && (view.parent.parent as View).id != R.id.drinking_question_constraintLayout) {

                    (view.parent.parent as View).isFocusableInTouchMode = true

                    (view.parent.parent as View).requestFocus()

                    (view.parent.parent as View).isFocusableInTouchMode = false

                }else{

                    (view.parent.parent.parent as View).isFocusableInTouchMode = true

                    (view.parent.parent.parent as View).requestFocus()

                    (view.parent.parent.parent as View).isFocusableInTouchMode = false

                }

            }

            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, 0)

        }else{

            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(view, 0)

        }

    }

    //창 닫기를 하면 모든 PaperArray가 초기화 된다
    fun cancelAlert(){

        if(state != "getPaper") {

            var dialog = android.app.AlertDialog.Builder(this).create()
            var dialog_view = LayoutInflater.from(this).inflate(R.layout.quit_alert, null)

            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setView(dialog_view)
            dialog_view.notice.text = "문진 내용이 초기화됩니다. \n메인페이지로 가시겠습니까?"

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

            dialog_view.cancel.setOnClickListener {

                dialog.dismiss()

            }

            dialog_view.finish.setOnClickListener {

                //MainActivity.chart = "SET0"
                PaperArray.PaperArrFunction.ArrayListInit()
                startActivity(Intent(this, MainActivity::class.java).putExtra("from", "exam").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

            }

        }else{

            MainActivity.login_user_name = ""
            MainActivity.user_first_serial = ""
            MainActivity.user_last_serial = ""

            MainActivity.userLogin!!.text = "사용자 등록하기"
            MainActivity.userImage!!.setImageResource(R.drawable.regi)

            super.onBackPressed()

        }

    }

    fun wifiCheck(){

        popup = false

        var dialog = android.app.AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_complete_alert, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.save_complete_alert_text.text = "모바일 데이터 연결상태를 확인해주세요"

        if(!popup) {

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

            dialog.dismiss()

        }

    }

}