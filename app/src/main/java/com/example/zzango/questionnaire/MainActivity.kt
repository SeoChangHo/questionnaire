package com.example.zzango.questionnaire


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firstFragment = FirstFragment()

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_right, firstFragment).commit()

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)

        button1.setBackgroundColor(Color.parseColor("#233F78"))

        listButton.setOnClickListener{

            startActivity(Intent(this@MainActivity, ListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

    }

    //color
    //0 : first, 1 : second, 2 : third, 3 : four
    override fun onClick(v: View?) {
        button1.setBackgroundColor(Color.parseColor("#3760B2"))
        button2.setBackgroundColor(Color.parseColor("#3760B2"))
        button3.setBackgroundColor(Color.parseColor("#3760B2"))
        button4.setBackgroundColor(Color.parseColor("#3760B2"))
        button5.setBackgroundColor(Color.parseColor("#3760B2"))


        var fragment: Fragment? = null

        println(v!!.id)
        when (v!!.id) {
            R.id.button1 -> {
                fragment = FirstFragment()
            }

            R.id.button2 -> {
                fragment = SecondFragment()
            }

            R.id.button3 -> {
                fragment = ThirdFragment()
            }

            R.id.button4 -> {
                fragment = FourthFragment()
            }

            R.id.button5 -> {
                fragment = FiveFragment()
            }
        }

        v.setBackgroundColor(Color.parseColor("#233F78"))

        if (fragment != null) {

            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_right, fragment!!).commit()
        }
    }
}
