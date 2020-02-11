package com.work.restaurant.view.mypage.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.login.MyPageLoginFragment
import com.work.restaurant.view.mypage.logout.MyPageLogoutFragment
import com.work.restaurant.view.mypage.main.presenter.MyPageContract
import com.work.restaurant.view.mypage.main.presenter.MyPagePresenter
import com.work.restaurant.view.mypage.notification.MyPageNotificationFragment
import com.work.restaurant.view.mypage.question.MyPageQuestionFragment
import com.work.restaurant.view.mypage.withdraw.MyPageWithdrawalFragment
import kotlinx.android.synthetic.main.mypage_fragment.*

class MyPageFragment : BaseFragment(R.layout.mypage_fragment), MyPageContract.View,
    View.OnClickListener {


    private lateinit var presenter: MyPageContract.Presenter


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_login -> {
                presenter.logIn()
            }

            R.id.ll_logout -> {
                presenter.logOut()
            }

            R.id.tv_page_withdrawal -> {
                presenter.withDraw()
            }

            R.id.ll_identity -> {

                val alertDialog =
                    AlertDialog.Builder(
                        this.context
                    )

                alertDialog.setView(
                    LayoutInflater.from(context).inflate(
                        R.layout.identify_item,
                        null
                    )
                )
                alertDialog.setTitle("개인정보 처리방침")
                alertDialog.setPositiveButton(
                    "확인"
                ) { _, _ -> }
                alertDialog.show()

            }

            R.id.ll_notification -> {
                requireFragmentManager()
                    .beginTransaction()
                    .replace(
                        R.id.mypage_main_container,
                        MyPageNotificationFragment()
                    )
                    .addToBackStack(null)
                    .commit()
            }
            R.id.ll_question -> {

                requireFragmentManager()
                    .beginTransaction()
                    .replace(
                        R.id.mypage_main_container,
                        MyPageQuestionFragment()
                    )
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MyPagePresenter(this)
        iv_login.setOnClickListener(this)
        ll_logout.setOnClickListener(this)
        tv_page_withdrawal.setOnClickListener(this)
        ll_identity.setOnClickListener(this)
        ll_notification.setOnClickListener(this)
        ll_question.setOnClickListener(this)

        loginState()

    }


    private fun loginState() {
        if (loginState) {
            iv_login.setImageResource(R.drawable.user)
            iv_login.isClickable = false
            login_ok_ll.visibility = View.VISIBLE
            tv_login_id.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
            tv_login_id.text = "$userId 님 환영합니다"
            tv_login_id.text = "$userNickname 님\n 환영합니다."
        } else {
            login_ok_ll.visibility = View.INVISIBLE
            iv_login.isClickable = true
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

        if (requestCode == REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                val loginEmail = data?.extras?.getString("id")
                val loginNickname = data?.extras?.getString("nickname")

                userId = loginEmail.toString()
                userNickname = loginNickname.toString()
                loginState = true
            }

        }


    }

    override fun showLogIn() {

        val myPageLoginFragment = MyPageLoginFragment()
        myPageLoginFragment.setTargetFragment(
            this,
            LOGIN
        )

        requireFragmentManager()
            .beginTransaction()
            .replace(
                R.id.mypage_main_container,
                myPageLoginFragment
            )
            .addToBackStack(null)
            .commit()
    }

    override fun showLogOut() {


        val myPageLogoutFragment = MyPageLogoutFragment()

        myPageLogoutFragment.setTargetFragment(
            this,
            LOGOUT
        )
        requireFragmentManager()
            .beginTransaction()
            .replace(
                R.id.main_container,
                myPageLogoutFragment
            )
            .addToBackStack(null)
            .commit().apply {
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

        requireFragmentManager()
            .beginTransaction()
            .replace(
                R.id.main_container,
                myPageWithdrawalFragment
            )
            .addToBackStack(null)
            .commit()
    }

    override fun showLateView() {
        this.requireFragmentManager()
            .beginTransaction()
            .replace(
                R.id.main_container,
                MyPageWithdrawalFragment()
            )
            .addToBackStack(null)
            .commit()
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