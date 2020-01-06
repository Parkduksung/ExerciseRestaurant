package com.work.restaurant.view.fragment.mypage.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.work.restaurant.R
import com.work.restaurant.view.fragment.base.BaseFragment
import com.work.restaurant.view.fragment.mypage.login.MyPageLoginFragment
import com.work.restaurant.view.fragment.mypage.logout.MyPageLogoutFragment
import com.work.restaurant.view.fragment.mypage.main.presenter.MyPageContract
import com.work.restaurant.view.fragment.mypage.main.presenter.MyPagePresenter
import com.work.restaurant.view.fragment.mypage.withdraw.MyPageWithdrawalFragment
import kotlinx.android.synthetic.main.mypage_fragment.*

class MyPageFragment : BaseFragment(R.layout.mypage_fragment), MyPageContract.View,
    View.OnClickListener {


    private lateinit var presenter: MyPageContract.Presenter


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_login -> {
                presenter.logIn()
            }

            R.id.tv_page_late_view -> {
                presenter.lateView()
            }

            R.id.tv_page_logout -> {
                presenter.logOut()
            }

            R.id.tv_page_withdrawal -> {
                presenter.withDraw()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        presenter = MyPagePresenter(this)
        iv_login.setOnClickListener(this)
        tv_page_logout.setOnClickListener(this)
        tv_page_withdrawal.setOnClickListener(this)
        tv_page_late_view.setOnClickListener(this)


        loginState()

    }


    private fun loginState() {
        if (loginState) {
            iv_login.visibility = View.INVISIBLE
            login_ok_ll.visibility = View.VISIBLE
            tv_login_id.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
            tv_login_id.text = "$userId 님 환영합니다"
            tv_login_id.text = "$userNickname 님\n 환영합니다."
        } else {
            login_ok_ll.visibility = View.INVISIBLE
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                val loginEmail = data?.extras?.getString("id")
                val loginNickname = data?.extras?.getString("nickname")

                userId = loginEmail.toString()
                userNickname = loginNickname.toString()
                loginState = true
            }
        }

        if (requestCode == LOGOUT) {
            if (resultCode == Activity.RESULT_OK) {
                val loginEmail = data?.extras?.getString("id")
                val loginNickname = data?.extras?.getString("nickname")


                userId = loginEmail.toString()
                userNickname = loginNickname.toString()
                loginState = false
            }
        }

        if (requestCode == WITHDRAW) {
            if (resultCode == Activity.RESULT_OK) {
                val loginEmail = data?.extras?.getString("id")
                val loginNickname = data?.extras?.getString("nickname")


                userId = loginEmail.toString()
                userNickname = loginNickname.toString()
                loginState = false
            }

        }

//        if (requestCode == REGISTER) {
//            if (resultCode == Activity.RESULT_OK) {
//                val loginEmail = data?.extras?.getString("id")
//                val loginNickname = data?.extras?.getString("nickname")
//
//                Log.d("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]", "z")
//                Log.d("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]", "z")
//
//                userId = loginEmail.toString()
//                userNickname = loginNickname.toString()
//                loginState = true
//            }
//
//        }


    }

    override fun showLogIn() {

        val myPageLoginFragment = MyPageLoginFragment()
        myPageLoginFragment.setTargetFragment(
            this,
            LOGIN
        )

        this.requireFragmentManager().beginTransaction().replace(
            R.id.mypage_main_container,
            myPageLoginFragment
        ).commit()
    }

    override fun showLogOut() {


        val myPageLogoutFragment = MyPageLogoutFragment()

        myPageLogoutFragment.setTargetFragment(
            this,
            LOGOUT
        )

        this.requireFragmentManager().beginTransaction().replace(
            R.id.main_container,
            myPageLogoutFragment
        ).commit().apply {
            FirebaseAuth.getInstance().signOut()
        }
    }

    override fun showWithDraw() {
        val myPageWithdrawalFragment =
            MyPageWithdrawalFragment()
        myPageWithdrawalFragment.setTargetFragment(
            this,
            WITHDRAW
        )

        this.requireFragmentManager().beginTransaction().replace(
            R.id.main_container,
            myPageWithdrawalFragment
        ).commit()
    }

    override fun showLateView() {
        this.requireFragmentManager().beginTransaction().replace(
            R.id.main_container,
            MyPageWithdrawalFragment()
        ).commit()
    }


    companion object {
        var loginState = false
        var userId = ""
        var userNickname = ""
        private const val TAG = "MyPageFragment"
        const val URL = "https://duksung12.cafe24.com"
        private const val LOGIN = 1
        private const val LOGOUT = 2
        private const val WITHDRAW = 3
        private const val REGISTER = 4
    }

}