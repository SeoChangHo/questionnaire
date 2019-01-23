package com.example.zzango.questionnaire

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import kotlinx.android.synthetic.main.progressbar.*
import kotlinx.android.synthetic.main.quit_alert.view.*

open class RootActivity : AppCompatActivity() {

    var popup = false

    //radio button check change listener와 연동 bool은 라디오 버튼 체크 값, view는 라디오 버튼과 연계된 wrapper view
    fun checkCondition(bool: Boolean, view : View){

        if(bool){

            view.visibility = View.VISIBLE

        }else{

            view.visibility = View.GONE

        }

    }

    fun editTextCondition(bool: Boolean, editText: EditText){

        if(!bool){

            editText.setText("")

        }

    }

    //진행바 제어하는 메서드
    fun controlProgress(context : Context, layout : ConstraintLayout, view : View, guideline : Guideline, guideline2 : Guideline){

        when(MainActivity.chart){

            "SET1" -> { view.visibility = View.GONE }

            "SET2" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                    "MentalExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

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

                    }

                    "MentalExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                    "ExerciseExaminationActivity", "DrinkingExaminationActivity", "NutritionExaminationActivity", "SmokingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline3.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                }

                guideline.setGuidelinePercent(0.33f)
                guideline2.setGuidelinePercent(0.66f)
                guideline3.setGuidelinePercent(1f)

            }

            "SET4" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                    "MentalExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                    "ExerciseExaminationActivity", "DrinkingExaminationActivity", "NutritionExaminationActivity", "SmokingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline3.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

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

                    }

                    "MentalExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                    "ExerciseExaminationActivity", "DrinkingExaminationActivity", "NutritionExaminationActivity", "SmokingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline3.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                }

                guideline.setGuidelinePercent(0.33f)
                guideline2.setGuidelinePercent(0.66f)
                guideline3.setGuidelinePercent(1f)

            }

            "SET6" -> {

                when(context.javaClass.kotlin.simpleName) {

                    "CommonExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                    "MentalExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline2.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                    "ExerciseExaminationActivity", "DrinkingExaminationActivity", "NutritionExaminationActivity", "SmokingExaminationActivity" -> {

                        var constraintSet = ConstraintSet()
                        constraintSet.clone(layout)
                        constraintSet.connect(view.id, ConstraintSet.END, guideline3.id, ConstraintSet.START)
                        constraintSet.applyTo(layout)

                    }

                }

                guideline.setGuidelinePercent(0.33f)
                guideline2.setGuidelinePercent(0.66f)
                guideline3.setGuidelinePercent(1f)

            }

        }

    }

    fun controlProgressDetail(layout : ConstraintLayout, view : View, guideline : Guideline, guideline2 : Guideline){



    }

    //뷰에 포커스를 총괄하는 메서드
    open fun focusControl(view : View){

        if(view !is EditText){

            if(view !is RadioButton){

                if(view is ConstraintLayout) {

                    (view as View).isFocusableInTouchMode = true

                    view.requestFocus()

                    (view as View).isFocusableInTouchMode = false

                }else {

                    (view.parent as View).isFocusableInTouchMode = true

                    (view.parent as View).requestFocus()

                    (view.parent as View).isFocusableInTouchMode = false

                }

            }else{

                (view.parent.parent as View).isFocusableInTouchMode = true

                (view.parent.parent as View).requestFocus()

                (view.parent.parent as View).isFocusableInTouchMode = false

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

            startActivity(Intent(this, MainActivity::class.java).putExtra("from", "exam").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

    }

}