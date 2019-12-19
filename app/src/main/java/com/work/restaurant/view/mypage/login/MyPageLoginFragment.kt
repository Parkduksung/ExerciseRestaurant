package com.work.restaurant.view.mypage.login

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.mypage.find.MyPageFindPassFragment
import com.work.restaurant.view.mypage.login.presenter.MyPageLoginContract
import com.work.restaurant.view.mypage.login.presenter.MyPageLoginPresenter
import com.work.restaurant.view.mypage.main.MyPageFragment
import com.work.restaurant.view.mypage.register.MyPageRegisterFragment
import kotlinx.android.synthetic.main.mypage_login_fragment.*

class MyPageLoginFragment : Fragment(), View.OnClickListener, MyPageLoginContract.View {

    private lateinit var presenter: MyPageLoginContract.Presenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                presenter.login(et_email.text.toString(), et_pass.text.toString())
            }

            R.id.ib_login_back -> {
                presenter.backPage()
            }

            R.id.tv_login_register -> {
                presenter.registerPage()
            }

            R.id.tv_login_find -> {
                presenter.findPass()
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mypage_login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        presenter = MyPageLoginPresenter(this)
        btn_login.setOnClickListener(this)
        ib_login_back.setOnClickListener(this)
        tv_login_register.setOnClickListener(this)
        tv_login_find.setOnClickListener(this)


    }


    override fun showLoginNo() {
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
                    Log.d("1111", "실패")
                }
            })
        alertDialog.show()

    }

    override fun showLoginOk(nickName: String) {

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

                    this@MyPageLoginFragment.requireFragmentManager()
                        .beginTransaction()
                        .replace(
                            R.id.mypage_main_container,
                            MyPageFragment()
                        ).addToBackStack(null).commit().also {
                            val data = Intent()
                            data.putExtra("id", et_email.text.toString())
                            data.putExtra("nickname", nickName)
                            targetFragment?.onActivityResult(
                                targetRequestCode,
                                Activity.RESULT_OK,
                                data
                            )

                        }
                }
            })
        alertDialog.show()

    }


    override fun showBackPage() {
        this.requireFragmentManager().beginTransaction().remove(
            this
        ).addToBackStack(null).commit()
    }

    override fun showRegisterPage() {

        this.requireFragmentManager().beginTransaction().replace(
            R.id.mypage_main_container,
            MyPageRegisterFragment()
        ).addToBackStack(null).commit()
    }

    override fun showFindPassPage() {
        this.requireFragmentManager().beginTransaction().replace(
            R.id.main_container,
            MyPageFindPassFragment()
        ).addToBackStack(null).commit()
    }

    companion object {
        private const val TAG = "MyPageLoginFragment"
    }


}

