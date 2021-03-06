package com.fineinsight.zzango.questionnaire

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_exam_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.login_appbar_loading_progress
import kotlinx.android.synthetic.main.activity_main.login_appbar_loading_progress_bg
import kotlinx.android.synthetic.main.fragment_first.view.*

class FirstFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.fragment_first, container, false)

        view.first_imageButton1.setOnClickListener {

//            if(activity!!.user_login.text != "사용자 등록하기") {
//
//                activity!!.login_appbar_loading_progress.visibility = View.VISIBLE
//                activity!!.login_appbar_loading_progress_bg.visibility = View.VISIBLE
//
//                startActivity(Intent(activity!!, CommonExaminationActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
//
//            }else{

                (activity as ExamListActivity).userlogin(activity!!.user_login, activity!!.user_image, activity as Context, "CommonExaminationActivity")

//            }

        }


        return view

    }

    override fun onResume() {

        println("resume")

        activity!!.login_appbar_loading_progress.visibility = View.GONE
        activity!!.login_appbar_loading_progress_bg.visibility = View.GONE

        super.onResume()

    }

}