package com.example.zzango.questionnaire

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_third.view.*

class ThirdFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.fragment_third, container, false)


        view.third_imageButton1.setOnClickListener {

            activity!!.login_appbar_loading_progress.visibility = View.VISIBLE
            activity!!.login_appbar_loading_progress_bg.visibility = View.VISIBLE

            startActivity(Intent(activity!!, ExerciseExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        view.third_imageButton2.setOnClickListener {

            activity!!.login_appbar_loading_progress.visibility = View.VISIBLE
            activity!!.login_appbar_loading_progress_bg.visibility = View.VISIBLE

            startActivity(Intent(activity!!, NutritionExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        view.third_imageButton3.setOnClickListener {

            activity!!.login_appbar_loading_progress.visibility = View.VISIBLE
            activity!!.login_appbar_loading_progress_bg.visibility = View.VISIBLE

            startActivity(Intent(activity!!, SmokingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        view.third_imageButton4.setOnClickListener {

            activity!!.login_appbar_loading_progress.visibility = View.VISIBLE
            activity!!.login_appbar_loading_progress_bg.visibility = View.VISIBLE

            startActivity(Intent(activity!!, DrinkingExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        return view
    }

    override fun onResume() {

        activity!!.login_appbar_loading_progress.visibility = View.GONE
        activity!!.login_appbar_loading_progress_bg.visibility = View.GONE

        super.onResume()

    }


}