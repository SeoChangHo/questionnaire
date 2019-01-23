package com.example.zzango.questionnaire.LocalList

import android.app.Activity
import android.icu.util.ULocale
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.zzango.questionnaire.R

class CustomDetailAdapter(var PaperList: Paper, var Activity: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var CategoryArr = ArrayList<String>()


    object Category
    {
        //구강검진
        var ORAL = "oral"

        //공통검진
        var COMMON = "common"

        //정신건강
        var MENTAL = "mental"

        //인지기능
        var COGNITIVE = "cognitive"

        //노인기능
        var ELDERLY = "elderly"

        //흡연
        var SMOKING = "smoking"

        //음주
        var DRINKING = "drinking"

        //암
        var CANCER = "cancer"

        //운동
        var EXERCISE = "exercise"

        //영양
        var NUTRITION = "nutrition"
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var v : View = LayoutInflater.from(p0?.context).inflate(R.layout.listdetail_layout, p0, false)
        return ContentDetailViewHolder(v)
    }

    override fun getItemCount(): Int {

        return SetToCount()
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        p0 as ContentDetailViewHolder
    }


    class ContentDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val txtCategory = itemView.findViewById(R.id.txtCategory1) as TextView
        val txtName = itemView.findViewById(R.id.txtName1) as TextView
        val txtDate = itemView.findViewById(R.id.txtDate1) as TextView
    }

    fun SetToCount():Int
    {
        var SET = PaperList.setno
        CategoryArr = ArrayList()

        when(SET)
        {
            PaperArray.SetList.SET1->
            {
                CategoryArr.add(Category.COMMON)
            }
            PaperArray.SetList.SET2->
            {
                CategoryArr.add(Category.COMMON)
                CategoryArr.add(Category.MENTAL)
            }
            PaperArray.SetList.SET3->
            {
                CategoryArr.add(Category.COMMON)
                CategoryArr.add(Category.MENTAL)
                //생활습관 4개
                CategoryArr.add(Category.EXERCISE)
                CategoryArr.add(Category.NUTRITION)
                CategoryArr.add(Category.SMOKING)
                CategoryArr.add(Category.DRINKING)

            }
            PaperArray.SetList.SET4->
            {
                CategoryArr.add(Category.COMMON)
                CategoryArr.add(Category.COGNITIVE)
                CategoryArr.add(Category.ELDERLY)
            }
            PaperArray.SetList.SET5->
            {
                CategoryArr.add(Category.COMMON)
                CategoryArr.add(Category.COGNITIVE)
            }
            PaperArray.SetList.SET6->
            {
                CategoryArr.add(Category.COMMON)
                CategoryArr.add(Category.COGNITIVE)
                CategoryArr.add(Category.MENTAL)
                //생활습관 4개
                CategoryArr.add(Category.EXERCISE)
                CategoryArr.add(Category.NUTRITION)
                CategoryArr.add(Category.SMOKING)
                CategoryArr.add(Category.DRINKING)

                CategoryArr.add(Category.ELDERLY)
            }
            PaperArray.SetList.SET7->
            {
                CategoryArr.add(Category.ORAL)
            }
            PaperArray.SetList.SET8->
            {
                CategoryArr.add(Category.CANCER)
            }
            else->
            {
                return 0
            }
        }

        return CategoryArr.size
    }
}
