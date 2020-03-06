package com.work.restaurant.view.mypage.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.adapter.RenewBookmarkAndRankListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.find.MyPageFindPassFragment
import com.work.restaurant.view.mypage.logout.MyPageLogoutFragment
import com.work.restaurant.view.mypage.main.presenter.MyPageContract
import com.work.restaurant.view.mypage.main.presenter.MyPagePresenter
import com.work.restaurant.view.mypage.notification.MyPageNotificationFragment
import com.work.restaurant.view.mypage.question.MyPageQuestionFragment
import com.work.restaurant.view.mypage.register.MyPageRegisterFragment
import com.work.restaurant.view.mypage.withdraw.MyPageWithdrawalFragment
import kotlinx.android.synthetic.main.mypage_fragment.*

class MyPageFragment : BaseFragment(R.layout.mypage_fragment), MyPageContract.View,
    View.OnClickListener {


    private lateinit var presenter: MyPageContract.Presenter

    private lateinit var renewBookmarkAndRankListener: RenewBookmarkAndRankListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? RenewBookmarkAndRankListener)?.let {
            renewBookmarkAndRankListener = it
        }
    }


    override fun showProgress() {
        pb_login.bringToFront()
        pb_login.visibility = View.VISIBLE
        btn_login.isClickable = false
        tv_main_register.isClickable = false
        tv_main_find.isClickable = false

    }

    override fun showEnd() {
        pb_login.visibility = View.GONE
        btn_login.isClickable = true
        tv_main_register.isClickable = true
        tv_main_find.isClickable = true
    }


    override fun showLoginOk(email: String, nickname: String) {
        toggleLoginState = true
        tv_login_nickname.text =
            getString(
                R.string.mypage_login_state_nickname,
                nickname
            )
        tv_login_id.text = email
        userNickname = nickname
        App.prefs.login_state = true
        App.prefs.login_state_id = email
        renewBookmarkAndRankListener.renewBookmarkAndRank()
        loginState()
        showEnd()
    }

    override fun showLoginNo() {
        Toast.makeText(this.context, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT)
            .show()
        showEnd()
    }

    override fun showInit() {
        toggleLoginState = false
        userNickname = ""
        tv_login_nickname.text = ""
        tv_login_id.text = ""
        App.prefs.login_state_id = ""
        App.prefs.login_state = false
        renewBookmarkAndRankListener.renewBookmarkAndRank()
        loginState()
    }

    override fun showMaintainLogin(email: String, nickname: String) {
        toggleLoginState = true
        tv_login_nickname.text =
            getString(
                R.string.mypage_login_state_nickname,
                nickname
            )
        tv_login_id.text = email
        userNickname = nickname
        loginState()
    }


    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_main_find -> {
                requireFragmentManager().beginTransaction().replace(
                    R.id.main_container,
                    MyPageFindPassFragment()
                ).addToBackStack(null).commit()
            }

            R.id.tv_main_register -> {

                val myPageRegisterFragment = MyPageRegisterFragment()

                myPageRegisterFragment.setTargetFragment(
                    this,
                    REGISTER
                )
                requireFragmentManager().beginTransaction().replace(
                    R.id.mypage_main_container,
                    myPageRegisterFragment
                ).addToBackStack(null).commit()
            }

            R.id.btn_login -> {
                if (et_email.text.toString().isNotEmpty() && et_pass.text.toString().isNotEmpty()) {

                    if (et_email.text.toString().contains(" ") || et_pass.text.toString().contains(" ")) {
                        Toast.makeText(this.context, "입력한 정보에 공백을 제거하세요.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (isValidEmail(et_email.text.toString())) {
                            showProgress()
                            presenter.login(
                                et_email.text.toString(),
                                et_pass.text.toString()
                            )
                        } else {
                            Toast.makeText(this.context, "형식에 맞는 아이디를 입력하세요.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else if (et_email.text.toString().trim().isNotEmpty() && et_pass.text.toString().trim().isEmpty()) {
                    Toast.makeText(this.context, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                } else if (et_email.text.toString().trim().isEmpty() && et_pass.text.toString().trim().isNotEmpty()) {
                    Toast.makeText(this.context, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this.context, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                }
            }


            R.id.ll_logout -> {
                val myPageLogoutFragment =
                    MyPageLogoutFragment.newInstance(tv_login_id.text.toString())

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
                    .commit()
            }

            R.id.tv_page_withdrawal -> {
                val myPageWithdrawalFragment =
                    MyPageWithdrawalFragment.newInstance(
                        tv_login_id.text.toString(),
                        userNickname
                    )
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
        presenter = MyPagePresenter(
            this,
            Injection.provideLoginRepository(),
            Injection.provideUserRepository()
        )
        iv_login.setOnClickListener(this)
        ll_logout.setOnClickListener(this)
        tv_page_withdrawal.setOnClickListener(this)
        ll_identity.setOnClickListener(this)
        ll_notification.setOnClickListener(this)
        ll_question.setOnClickListener(this)
        tv_main_register.setOnClickListener(this)
        tv_main_find.setOnClickListener(this)
        btn_login.setOnClickListener(this)


        presenter.getLoginState()


    }

    private fun loginState() {
        ll_mypage_main_login.isVisible = toggleLoginState
        ll_mypage_main_init.isVisible = !toggleLoginState
    }

    private fun isValidEmail(charSequence: CharSequence): Boolean =
        !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == LOGOUT) {
            if (resultCode == Activity.RESULT_OK) {
                et_email.text.clear()
                et_pass.text.clear()
                showInit()
            }
        }

        if (requestCode == WITHDRAW) {
            if (resultCode == Activity.RESULT_OK) {
                et_email.text.clear()
                et_pass.text.clear()
                showInit()
            }
        }

        if (requestCode == REGISTER) {
            if (resultCode == Activity.RESULT_OK) {
                val loginEmail =
                    data?.extras?.getString(MyPageRegisterFragment.REGISTER_ID).orEmpty()
                val loginNickname =
                    data?.extras?.getString(MyPageRegisterFragment.REGISTER_NICKNAME).orEmpty()
                showLoginOk(loginEmail, loginNickname)
            }

        }
    }

    override fun onDetach() {
        super.onDetach()
        toggleLoginState = false

    }

    override fun onResume() {
        et_email.text.clear()
        et_pass.text.clear()
        super.onResume()
    }


    companion object {
        var toggleLoginState = false
        var userNickname = ""


        private const val TAG = "MyPageFragment"

        private const val REGISTER = 1
        private const val LOGOUT = 2
        private const val WITHDRAW = 3


    }

}