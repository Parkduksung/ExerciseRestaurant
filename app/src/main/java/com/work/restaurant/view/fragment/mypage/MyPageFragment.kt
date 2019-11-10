package com.work.restaurant.view.fragment.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.fragment.mypage.MyPageLoginFragment.Companion.userNickname
import kotlinx.android.synthetic.main.mypage_fragment.*

class MyPageFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_ll -> {
                this.requireFragmentManager().beginTransaction().replace(
                    R.id.mypage_main_container,
                    MyPageLoginFragment()
                ).commit()
            }

            R.id.tv_page_late_view -> {

                this.requireFragmentManager().beginTransaction().replace(
                    R.id.loading_container,
                    MyPageWithdrawalFragment()
                ).commit()

            }

            R.id.tv_page_logout -> {
                this.requireFragmentManager().beginTransaction().replace(
                    R.id.loading_container,
                    MyPageLogoutFragment()
                ).commit()
            }


            R.id.tv_page_withdrawal -> {
                this.requireFragmentManager().beginTransaction().replace(
                    R.id.loading_container,
                    MyPageWithdrawalFragment()
                ).commit()
            }


        }
    }


    override fun onAttach(context: Context) {
        Log.d(fragmentName, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onCreate")
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mypage_fragment, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        if (loginState) {
            iv_login.visibility = View.INVISIBLE
            login_ok_ll.visibility = View.VISIBLE
            tv_login_id.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
//            tv_login_id.text = userId + " 님 환영합니다"
            tv_login_id.text = userNickname + " 님\n 환영합니다."
        } else {
            login_ok_ll.visibility = View.INVISIBLE

        }

        login_ll.setOnClickListener(this)
        tv_page_logout.setOnClickListener(this)
        tv_page_withdrawal.setOnClickListener(this)
        tv_page_late_view.setOnClickListener(this)


    }


    override fun onStart() {
        Log.d(fragmentName, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(fragmentName, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(fragmentName, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(fragmentName, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(fragmentName, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(fragmentName, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(fragmentName, "onDetach")
        super.onDetach()
    }


    companion object {
        var loginState = false
        private const val fragmentName = "MyPageFragment"
    }

}