package com.example.zzango.questionnaire


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.zzango.questionnaire.LocalList.ListActivity
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //popup login
        popuplogin()

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_right, FirstFragment()).commit()

        button1.setBackgroundColor(Color.parseColor("#233F78"))

        listButton.setOnClickListener{

            startActivity(Intent(this@MainActivity, ListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))

        }

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        button1.setBackgroundColor(Color.parseColor("#3760B2"))
        button2.setBackgroundColor(Color.parseColor("#3760B2"))
        button3.setBackgroundColor(Color.parseColor("#3760B2"))
        button4.setBackgroundColor(Color.parseColor("#3760B2"))
        button5.setBackgroundColor(Color.parseColor("#3760B2"))

        var fragment: Fragment? = null

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

    fun popuplogin(){
//        val popupView = layoutInflater.inflate(R.layout.activity_login, null)
//        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
//        val mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//
//        // 외부 영역 선택시 PopUp 종료
//        mPopupWindow.setFocusable(true)
//
//        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)
//
//
//        val cancel = popupView.findViewById(R.id.Login) as Button
//        cancel.setOnClickListener{
//            mPopupWindow.dismiss()
//        }
//
//        val ok = popupView.findViewById(R.id.Cancel) as Button
//        ok.setOnClickListener{
//            Toast.makeText(applicationContext, "Ok", Toast.LENGTH_SHORT).show()
//        }


        var dialog = AlertDialog.Builder(this).create()
        var dialog_view = LayoutInflater.from(this).inflate(R.layout.activity_login, null)

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setView(dialog_view)
        dialog.setCanceledOnTouchOutside(false)


        val login = dialog_view.findViewById(R.id.Login) as Button
        login.setOnClickListener{

            if(dialog_view.login_id.text.toString() != ""){

                if(dialog_view.login_password.text.toString() == "1111"){
                    Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }else{
                    Toast.makeText(applicationContext, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(applicationContext, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }



        }

        dialog.show()

    }

}
