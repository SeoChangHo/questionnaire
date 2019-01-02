package com.example.zzango.questionnaire

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_second.view.*

class SecondFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.fragment_second, container, false)

        view.second_imageButton1.setOnClickListener {

            activity!!.login_appbar_loading_progress.visibility = View.VISIBLE
            activity!!.login_appbar_loading_progress_bg.visibility = View.VISIBLE

            startActivity(Intent(activity!!, MentalExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        view.second_imageButton2.setOnClickListener {

            activity!!.login_appbar_loading_progress.visibility = View.VISIBLE
            activity!!.login_appbar_loading_progress_bg.visibility = View.VISIBLE

            startActivity(Intent(activity!!, CognitiveExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        view.second_imageButton3.setOnClickListener {

            activity!!.login_appbar_loading_progress.visibility = View.VISIBLE
            activity!!.login_appbar_loading_progress_bg.visibility = View.VISIBLE

            startActivity(Intent(activity!!, ElderlyExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }


        return view
    }

    override fun onResume() {

        activity!!.login_appbar_loading_progress.visibility = View.GONE
        activity!!.login_appbar_loading_progress_bg.visibility = View.GONE

        super.onResume()

    }


}