package com.fineinsight.zzango.questionnaire

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.progressbar2.*
import kotlinx.android.synthetic.main.quit_alert.view.*
import kotlinx.android.synthetic.main.save_complete_alert.view.*

open class RootActivity : AppCompatActivity() {

    var popup = false
    var wfm : WifiManager? = null
    @Suppress("LeakingThis")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        wfm = this.getSystemService(Context.WIFI_SERVICE) as WifiManager

    }

    //radio button check change listener와 연동 bool은 라디오 버튼 체크 값, view는 라디오 버튼과 연계된 wrapper view
    fun checkCondition(bool: Boolean, view : View){

        if(bool){

            view.visibility = View.VISIBLE

        }else{

            view.visibility = View.GONE

        }

    }

    //로컬에 저장된 검진을 다시 불러와서 확인할때 체크된 사항들을 수정 못하도록 막는 메서드
    fun cannotEditQuestionnaire(vg : ViewGroup){

        for(i in 0 until vg.childCount){

            var child = vg.getChildAt(i)

            if(child is ViewGroup) {

                cannotEditQuestionnaire(child)

            }else{

                if(child is RadioButton || child is EditText || child is Spinner){

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
    fun controlProgress(context : Context, layout : ConstraintLayout, layout2 : ConstraintLayout, view : View, guideline : Guideline, guideline2 : Guideline, guideline3: Guideline, guideline4: Guideline, guideline5: Guideline, guideline6: Guideline, guideline7: Guideline, guideline8: Guideline){

        when(MainActivity.chart){

            "SET0" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "ExerciseExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "1/4 진행중"

                    }

                    "NutritionExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "2/4 진행중"

                    }

                    "SmokingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline3.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "3/4 진행중"

                    }

                    "DrinkingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline4.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "4/4 진행중"
                        questionnaire_page.setTextColor(ContextCompat.getColor(this, R.color.white))

                    }

                    else ->{

                        layout2.visibility = View.GONE

                    }

                }

                guideline.setGuidelinePercent(0.25f)
                guideline2.setGuidelinePercent(0.5f)
                guideline3.setGuidelinePercent(0.75f)
                guideline4.setGuidelinePercent(1f)

            }

            "SET1" -> { layout2.visibility = View.GONE }

            "SET2" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "1/2 진행중"

                    }

                    "MentalExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "2/2 진행중"
                        questionnaire_page.setTextColor(ContextCompat.getColor(this, R.color.white))

                    }

                }

                guideline.setGuidelinePercent(0.5f)
                guideline2.setGuidelinePercent(1f)

            }

            "SET3" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "1/6 진행중"

                    }

                    "MentalExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "2/6 진행중"

                    }

                    "ExerciseExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline3.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "3/6 진행중"

                    }

                    "NutritionExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline4.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "4/6 진행중"

                    }

                    "SmokingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline5.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "5/6 진행중"

                    }

                    "DrinkingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline6.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "6/6 진행중"
                        questionnaire_page.setTextColor(ContextCompat.getColor(this, R.color.white))

                    }

                }

                guideline.setGuidelinePercent(0.16f)
                guideline2.setGuidelinePercent(0.33f)
                guideline3.setGuidelinePercent(0.49f)
                guideline4.setGuidelinePercent(0.66f)
                guideline5.setGuidelinePercent(0.88f)
                guideline6.setGuidelinePercent(1f)

            }

            "SET4" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "1/3 진행중"

                    }

                    "CognitiveExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "2/3 진행중"

                    }

                    "ElderlyExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline3.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "3/3 진행중"
                        questionnaire_page.setTextColor(ContextCompat.getColor(this, R.color.white))

                    }

                }

                guideline.setGuidelinePercent(0.33f)
                guideline2.setGuidelinePercent(0.66f)
                guideline3.setGuidelinePercent(1f)

            }

            "SET5" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "1/2 진행중"

                    }

                    "CognitiveExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "2/2 진행중"
                        questionnaire_page.setTextColor(ContextCompat.getColor(this, R.color.white))

                    }

                }

                guideline.setGuidelinePercent(0.5f)
                guideline2.setGuidelinePercent(1f)

            }

            "SET6" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "1/8 진행중"

                    }

                    "CognitiveExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "2/8 진행중"

                    }

                    "MentalExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline3.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "3/8 진행중"

                    }

                    "ExerciseExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline4.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "4/8 진행중"

                    }

                    "NutritionExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline5.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "5/8 진행중"

                    }

                    "SmokingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline6.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "6/8 진행중"

                    }

                    "DrinkingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline7.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "7/8 진행중"

                    }

                    "ElderlyExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline8.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)
                        questionnaire_page.text = "8/8 진행중"
                        questionnaire_page.setTextColor(ContextCompat.getColor(this, R.color.white))

                    }

                }

                guideline.setGuidelinePercent(0.125f)
                guideline2.setGuidelinePercent(0.25f)
                guideline3.setGuidelinePercent(0.375f)
                guideline4.setGuidelinePercent(0.5f)
                guideline5.setGuidelinePercent(0.625f)
                guideline6.setGuidelinePercent(0.75f)
                guideline7.setGuidelinePercent(0.875f)
                guideline8.setGuidelinePercent(1f)

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

    fun cancelAlert(){

        var dialog = android.app.AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.quit_alert, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.notice.text = "문진 내용이 초기화됩니다. \n메인페이지로 가시겠습니까?"

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

        dialog_view.cancel.setOnClickListener {

            dialog.dismiss()

        }

        dialog_view.finish.setOnClickListener {

            MainActivity.chart = "SET0"
            startActivity(Intent(this, MainActivity::class.java).putExtra("from", "exam").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

    }

    fun wifiCheck(){

        popup = false

        var dialog = android.app.AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.save_complete_alert, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog_view.save_complete_alert_text.text = "와이파이 연결상태를 확인해주세요"

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