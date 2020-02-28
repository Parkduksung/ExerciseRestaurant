package com.work.restaurant.view.mypage.login

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.find.MyPageFindPassFragment
import com.work.restaurant.view.mypage.login.presenter.MyPageLoginContract
import com.work.restaurant.view.mypage.login.presenter.MyPageLoginPresenter
import com.work.restaurant.view.mypage.register.MyPageRegisterFragment
import kotlinx.android.synthetic.main.mypage_login_fragment.*

class MyPageLoginFragment : BaseFragment(R.layout.mypage_login_fragment), View.OnClickListener,
    MyPageLoginContract.View {


    private lateinit var presenter: MyPageLoginContract.Presenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                if (et_email.text.toString().isNotEmpty() && et_pass.text.toString().isNotEmpty()) {
                    pb_login.bringToFront()
                    pb_login.visibility = View.VISIBLE
                    btn_login.isClickable = false
                    ib_login_back.isClickable = false
                    tv_login_register.isClickable = false
                    tv_login_find.isClickable = false
                    presenter.login(et_email.text.toString().trim(), et_pass.text.toString().trim())
                } else {
                    Toast.makeText(this.context, "정확한 입력을 부탁드립니다!", Toast.LENGTH_SHORT).show()
                }

            }

            R.id.ib_login_back -> {
                fragmentManager?.popBackStack()
            }

            R.id.tv_login_register -> {
                presenter.registerPage()
            }

            R.id.tv_login_find -> {
                presenter.findPass()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MyPageLoginPresenter(
            this,
            Injection.provideUserRepository(),
            Injection.provideLoginRepository()
        )
        btn_login.setOnClickListener(this)
        ib_login_back.setOnClickListener(this)
        tv_login_register.setOnClickListener(this)
        tv_login_find.setOnClickListener(this)
    }


    override fun showLoginNo() {

        pb_login.visibility = View.GONE
        btn_login.isClickable = true
        ib_login_back.isClickable = true
        tv_login_register.isClickable = true
        tv_login_find.isClickable = true
        et_pass.text.clear()
        val alertDialog =
            AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )

        alertDialog.setTitle("로그인 실패")
        alertDialog.setMessage("입력한 정보를 확인바랍니다.")
        alertDialog.setPositiveButton(
            "확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })
        alertDialog.show()

    }


    override fun showLoginStateOk(nickName: String) {
        val alertDialog =
            AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )

        alertDialog.setTitle("로그인 성공")
        alertDialog.setMessage(et_email.text.toString() + "님 환영합니다!")
        alertDialog.setPositiveButton(
            "확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })

        val data = Intent().apply {
            putExtra(LOGIN_ID, et_email.text.toString())
            putExtra(LOGIN_NICKNAME, nickName)
        }
        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            data
        )
        et_email.text.clear()
        et_pass.text.clear()


        pb_login.visibility = View.GONE
        btn_login.isClickable = true
        ib_login_back.isClickable = true
        tv_login_register.isClickable = true
        tv_login_find.isClickable = true

        fragmentManager?.popBackStack()

        alertDialog.show()
    }

    override fun showLoginOk(nickName: String) {
        presenter.changeState(et_email.text.toString(), nickName)
    }

    override fun showRegisterPage() {

        requireFragmentManager().beginTransaction().replace(
            R.id.mypage_main_container,
            MyPageRegisterFragment()
        ).addToBackStack(null).commit()
    }

    override fun showFindPassPage() {
        requireFragmentManager().beginTransaction().replace(
            R.id.main_container,
            MyPageFindPassFragment()
        ).addToBackStack(null).commit()
    }

    override fun onResume() {
        super.onResume()
        et_email.text.clear()
        et_pass.text.clear()
    }


    companion object {
        private const val TAG = "MyPageLoginFragment"

        const val LOGIN_ID = "id"
        const val LOGIN_NICKNAME = "nickname"
    }


}

