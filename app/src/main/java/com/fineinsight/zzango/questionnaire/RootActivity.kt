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
import com.fineinsight.zzango.questionnaire.DataClass.SavePaper
import com.fineinsight.zzango.questionnaire.DataClass.SavedListObject
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
    fun controlProgress(context : Context){

        progressDisable(context)

        progressTouchCheck(context)

        progressVisible(context)

        constraintSet.clone(questionnaire_progress_wrapper)

        proressRoute(context, constraintSet)

    }

    fun progressDisable(context : Context){

        when (context.javaClass.kotlin.simpleName) {

            "CommonExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "MentalExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "CognitiveExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "ElderlyExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "ExerciseExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "NutritionExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "SmokingExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "DrinkingExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "OralExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

            "CancerExaminationActivity" -> {
                common.isEnabled = false
                mental.isEnabled = false
                cognitive.isEnabled = false
                elderly.isEnabled = false
                exercise.isEnabled = false
                nutrition.isEnabled = false
                smoking.isEnabled = false
                drinking.isEnabled = false
                oral.isEnabled = false
                cancer.isEnabled = false
            }

        }

    }

    //MainActivity.chart로 진행바 내부 액티비티 표시 뷰를 visible 처리하는 메서드
    fun progressVisible(context : Context){

        //세트진행시
        if(MainActivity.chart.isNotEmpty()) {

            var charts = MainActivity.chart

            for ((i, chart) in charts.withIndex()) {
                when {
                    (i == 0 && chart.isbool) -> { common.visibility = View.VISIBLE }
                    (i == 1 && chart.isbool) -> { mental.visibility = View.VISIBLE }
                    (i == 2 && chart.isbool) -> { cognitive.visibility = View.VISIBLE }
                    (i == 3 && chart.isbool) -> { elderly.visibility = View.VISIBLE }
                    (i == 4 && chart.isbool) -> {
                        exercise.visibility = View.VISIBLE
                        nutrition.visibility = View.VISIBLE
                        smoking.visibility = View.VISIBLE
                        drinking.visibility = View.VISIBLE
                    }
                    (i == 5 && chart.isbool) -> { oral.visibility = View.VISIBLE}
                    (i == 6 && chart.isbool) -> { cancer.visibility = View.VISIBLE}
                }
            }
        //단일 진행시
        }else{

            when(context.javaClass.kotlin.simpleName){

                "ExerciseExaminationActivity" ,"NutritionExaminationActivity",
                "SmokingExaminationActivity", "DrinkingExaminationActivity" -> {
                    exercise.visibility = View.VISIBLE
                    nutrition.visibility = View.VISIBLE
                    smoking.visibility = View.VISIBLE
                    drinking.visibility = View.VISIBLE
                }

                else -> { (questionnaire_progress_wrapper.parent as View).visibility = View.GONE }

            }

        }

    }

    //SavedList boolean을 통해서 현재 진행도를 처리하는 메서드
    fun proressRoute(context : Context, constraintSet : ConstraintSet) {

        common_text.setTextColor(resources.getColor(R.color.examBlue, null))

        if (SavedListObject.SavedList.savedDataClass.commonSaved) {
            common.isEnabled = true
            when {
                mental.visibility == View.VISIBLE -> {
                    mental.isEnabled = true
                    mental_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                cognitive.visibility == View.VISIBLE -> {
                    cognitive.isEnabled = true
                    cognitive_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                elderly.visibility == View.VISIBLE -> {
                    elderly.isEnabled = true
                    elderly_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                exercise.visibility == View.VISIBLE -> {
                    exercise.isEnabled = true
                    exercise_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                oral.visibility == View.VISIBLE -> {
                    oral.isEnabled = true
                    oral_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                cancer.visibility == View.VISIBLE -> {
                    cancer.isEnabled = true
                    cancer_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
            }
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, common.id, ConstraintSet.END)
        }

        if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
            mental.isEnabled = true
            mental_text.setTextColor(resources.getColor(R.color.examBlue, null))
            when {
                cognitive.visibility == View.VISIBLE -> {
                    cognitive.isEnabled = true
                    cognitive_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                elderly.visibility == View.VISIBLE -> {
                    elderly.isEnabled = true
                    elderly_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                exercise.visibility == View.VISIBLE -> {
                    exercise.isEnabled = true
                    exercise_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                oral.visibility == View.VISIBLE -> {
                    oral.isEnabled = true
                    oral_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                cancer.visibility == View.VISIBLE -> {
                    cancer.isEnabled = true
                    cancer_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
            }
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, mental.id, ConstraintSet.END)
        }

        if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
            cognitive.isEnabled = true
            cognitive_text.setTextColor(resources.getColor(R.color.examBlue, null))
            when {
                elderly.visibility == View.VISIBLE -> {
                    elderly.isEnabled = true
                    elderly_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                exercise.visibility == View.VISIBLE -> {
                    exercise.isEnabled = true
                    exercise_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                oral.visibility == View.VISIBLE -> {
                    oral.isEnabled = true
                    oral_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                cancer.visibility == View.VISIBLE -> {
                    cancer.isEnabled = true
                    cancer_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
            }
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, cognitive.id, ConstraintSet.END)
        }

        if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
            elderly.isEnabled = true
            elderly_text.setTextColor(resources.getColor(R.color.examBlue, null))
            when {
                exercise.visibility == View.VISIBLE -> {
                    exercise.isEnabled = true
                    exercise_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                oral.visibility == View.VISIBLE -> {
                    oral.isEnabled = true
                    oral_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
                cancer.visibility == View.VISIBLE -> {
                    cancer.isEnabled = true
                    cancer_text.setTextColor(resources.getColor(R.color.examBlue, null))
                }
            }
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, elderly.id, ConstraintSet.END)
        }

        if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
            exercise.isEnabled = true
            exercise_text.setTextColor(resources.getColor(R.color.examBlue, null))
            nutrition.isEnabled = true
            nutrition_text.setTextColor(resources.getColor(R.color.examBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, exercise.id, ConstraintSet.END)
        }

        if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
            nutrition.isEnabled = true
            nutrition_text.setTextColor(resources.getColor(R.color.examBlue, null))
            smoking.isEnabled = true
            smoking_text.setTextColor(resources.getColor(R.color.examBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, nutrition.id, ConstraintSet.END)
        }

        if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
            smoking.isEnabled = true
            smoking_text.setTextColor(resources.getColor(R.color.examBlue, null))
            drinking.isEnabled = true
            drinking_text.setTextColor(resources.getColor(R.color.examBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, smoking.id, ConstraintSet.END)
        }

        if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
            if (oral.visibility == View.VISIBLE) {
                oral.isEnabled = true
                oral_text.setTextColor(resources.getColor(R.color.examBlue, null))
            } else {
                cancer.isEnabled = true
                cancer_text.setTextColor(resources.getColor(R.color.examBlue, null))
            }
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, drinking.id, ConstraintSet.END)
        }

        if (SavedListObject.SavedList.savedDataClass.oralSaved) {
            cancer.isEnabled = true
            cancer_text.setTextColor(resources.getColor(R.color.examBlue, null))
            constraintSet.connect(questionnaire_progress.id, ConstraintSet.END, oral.id, ConstraintSet.END)
        }

        when (context.javaClass.kotlin.simpleName) {

            "CommonExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, common.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, common.id, ConstraintSet.END)

                common_text.textSize = 26f
                common_text.typeface = Typeface.DEFAULT_BOLD

            }

            "MentalExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, mental.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, mental.id, ConstraintSet.END)

                mental_text.setTextColor(resources.getColor(R.color.examBlue, null))
                mental_text.textSize = 26f
                mental_text.typeface = Typeface.DEFAULT_BOLD

            }

            "CognitiveExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, cognitive.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, cognitive.id, ConstraintSet.END)

                cognitive_text.setTextColor(resources.getColor(R.color.examBlue, null))
                cognitive_text.textSize = 26f
                cognitive_text.typeface = Typeface.DEFAULT_BOLD

            }

            "ElderlyExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, elderly.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, elderly.id, ConstraintSet.END)

                elderly_text.setTextColor(resources.getColor(R.color.examBlue, null))
                elderly_text.textSize = 26f
                elderly_text.typeface = Typeface.DEFAULT_BOLD

            }

            "ExerciseExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, exercise.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, exercise.id, ConstraintSet.END)

                exercise_text.setTextColor(resources.getColor(R.color.examBlue, null))
                exercise_text.textSize = 26f
                exercise_text.typeface = Typeface.DEFAULT_BOLD

            }

            "NutritionExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, nutrition.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, nutrition.id, ConstraintSet.END)

                nutrition_text.setTextColor(resources.getColor(R.color.examBlue, null))
                nutrition_text.textSize = 26f
                nutrition_text.typeface = Typeface.DEFAULT_BOLD

            }

            "SmokingExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, smoking.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, smoking.id, ConstraintSet.END)

                smoking_text.setTextColor(resources.getColor(R.color.examBlue, null))
                smoking_text.textSize = 26f
                smoking_text.typeface = Typeface.DEFAULT_BOLD

            }

            "DrinkingExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, drinking.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, drinking.id, ConstraintSet.END)

                drinking_text.setTextColor(resources.getColor(R.color.examBlue, null))
                drinking_text.textSize = 26f
                drinking_text.typeface = Typeface.DEFAULT_BOLD

            }

            "OralExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, oral.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, oral.id, ConstraintSet.END)

                oral_text.setTextColor(resources.getColor(R.color.examBlue, null))
                oral_text.textSize = 26f
                oral_text.typeface = Typeface.DEFAULT_BOLD

            }

            "CancerExaminationActivity" -> {

                constraintSet.connect(triangle.id, ConstraintSet.START, cancer.id, ConstraintSet.START)
                constraintSet.connect(triangle.id, ConstraintSet.END, cancer.id, ConstraintSet.END)

                cancer_text.setTextColor(resources.getColor(R.color.examBlue, null))
                cancer_text.textSize = 26f
                cancer_text.typeface = Typeface.DEFAULT_BOLD

            }

        }

        with(constraintSet) { applyTo(questionnaire_progress_wrapper) }

    }

    //progress 클릭시 저장 조건에 따라 임시저장 or 수정을 결정하고 페이지 이동하는 메서드
    fun progressTouchCheck(context : Context){

        common.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> { }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) startActivity(Intent(context, CommonExaminationActivity::class.java).run { setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) })
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        mental.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {}
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        cognitive.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "CognitiveExaminationActivity" -> { }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        elderly.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }

                }
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ElderlyExaminationActivity" -> { }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        exercise.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ExerciseExaminationActivity" -> { }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        nutrition.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }

                }
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "NutritionExaminationActivity" -> { }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        smoking.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "SmokingExaminationActivity" -> { }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        drinking.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }

                }
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "DrinkingExaminationActivity" -> { }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        oral.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "OralExaminationActivity" -> { }
                "CancerExaminationActivity" -> {
                    context as CancerExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cancerSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    } else {
                        context.whenTempSave()
                        startActivity(Intent(context, OralExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            }
        }

        cancer.setOnClickListener {
            when(context.javaClass.kotlin.simpleName){
                "CommonExaminationActivity" -> {
                    context as CommonExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.commonSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "MentalExaminationActivity" -> {
                    context as MentalExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.mentalSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "CognitiveExaminationActivity" -> {
                    context as CognitiveExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.cognitiveSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ElderlyExaminationActivity" -> {
                    context as ElderlyExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.elderlySaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "ExerciseExaminationActivity" -> {
                    context as ExerciseExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.exerciseSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "NutritionExaminationActivity" -> {
                    context as NutritionExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.nutritionSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "SmokingExaminationActivity" -> {
                    context as SmokingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.smokingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "DrinkingExaminationActivity" -> {
                    context as DrinkingExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.drinkingSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "OralExaminationActivity" -> {
                    context as OralExaminationActivity
                    if (SavedListObject.SavedList.savedDataClass.oralSaved) {
                        if (context.check()) {
                            startActivity(Intent(context, CancerExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
                "CancerExaminationActivity" -> { }
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
                SavePaper.Total.Init()
                SavedListObject.SavedList.savedDataClass.commonSaved = false
                SavedListObject.SavedList.savedDataClass.mentalSaved = false
                SavedListObject.SavedList.savedDataClass.cognitiveSaved = false
                SavedListObject.SavedList.savedDataClass.elderlySaved = false
                SavedListObject.SavedList.savedDataClass.exerciseSaved = false
                SavedListObject.SavedList.savedDataClass.nutritionSaved = false
                SavedListObject.SavedList.savedDataClass.smokingSaved = false
                SavedListObject.SavedList.savedDataClass.drinkingSaved = false
                SavedListObject.SavedList.savedDataClass.oralSaved = false
                SavedListObject.SavedList.savedDataClass.cancerSaved = false
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

    companion object {
        var constraintSet = ConstraintSet()
    }

    override fun onPause() {

        constraintSet = ConstraintSet()

        super.onPause()

    }

}