package com.work.restaurant.view.fragment.mypage.register

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.work.restaurant.R
import com.work.restaurant.data.repository.user.UserRepositoryImpl
import com.work.restaurant.data.source.remote.user.UserRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.view.fragment.base.BaseFragment
import com.work.restaurant.view.fragment.mypage.login.MyPageLoginFragment
import com.work.restaurant.view.fragment.mypage.main.MyPageFragment
import com.work.restaurant.view.fragment.mypage.main.MyPageFragment.Companion.loginState
import com.work.restaurant.view.fragment.mypage.main.MyPageFragment.Companion.userId
import com.work.restaurant.view.fragment.mypage.main.MyPageFragment.Companion.userNickname
import com.work.restaurant.view.fragment.mypage.register.presenter.MyPageRegisterContract
import com.work.restaurant.view.fragment.mypage.register.presenter.MyPageRegisterPresenter
import com.work.restaurant.view.fragment.mypage.register_ok.MyPageRegisterOkFragment
import kotlinx.android.synthetic.main.mypage_register_fragment.*


class MyPageRegisterFragment : BaseFragment(R.layout.mypage_register_fragment),
    View.OnClickListener, MyPageRegisterContract.View {

    private lateinit var presenter: MyPageRegisterContract.Presenter

    override fun onClick(v: View?) {


        when (v?.id) {

            R.id.ib_register_back -> {
                presenter.backPage()
            }

            R.id.btn_register -> {
                registerState()
            }
        }
    }

    private fun registerState() {

        if (iv_nickname_state.tag == R.drawable.ic_ok &&
            iv_email_state.tag == R.drawable.ic_ok &&
            iv_pass_state.tag == R.drawable.ic_ok &&
            iv_pass_ok_state.tag == R.drawable.ic_ok
        ) {
            presenter.register(
                et_register_nickname.text.toString(),
                et_register_email.text.toString(),
                et_register_pass.text.toString()
            )
        } else {
            val alertDialog =
                AlertDialog.Builder(
                    ContextThemeWrapper(
                        activity,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )

            alertDialog.setTitle("실패")
            alertDialog.setMessage("정보 입력란을 확인하세요.")
            alertDialog.setPositiveButton("확인",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })
            alertDialog.show()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        presenter = MyPageRegisterPresenter(
            this, UserRepositoryImpl.getInstance(
                UserRemoteDataSourceImpl.getInstance(
                    RetrofitInstance.getInstance(
                        MyPageFragment.URL
                    )
                )
            )
        )
        ib_register_back.setOnClickListener(this)
        btn_register.setOnClickListener(this)

        et_register_nickname.requestFocus()
        inputState(et_register_nickname, iv_nickname_state)
        inputState(et_register_email, iv_email_state)
        inputState(et_register_pass, iv_pass_state)
        inputState(et_register_pass_ok, iv_pass_ok_state)


    }


    private fun inputState(editText: EditText, imageView: ImageView) {

        editText.setOnFocusChangeListener { v, hasFocus ->

            if (v != null) {
                if (hasFocus) {
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            imageView.setImageResource(0)


                            if (editText == et_register_pass || editText == et_register_pass_ok) {

                                if (editText.length() < 6) {
                                    imageView.setImageResource(R.drawable.ic_no)
                                    imageView.tag = R.drawable.ic_no
                                } else {
                                    if (editText == et_register_pass_ok) {
                                        if (et_register_pass.text.toString() == et_register_pass_ok.text.toString()) {
                                            imageView.setImageResource(R.drawable.ic_ok)
                                            imageView.tag = R.drawable.ic_ok
                                        } else {
                                            imageView.setImageResource(R.drawable.ic_no)
                                            imageView.tag = R.drawable.ic_no
                                        }
                                    } else {
                                        imageView.setImageResource(R.drawable.ic_ok)
                                        imageView.tag = R.drawable.ic_ok
                                    }

                                }
                            }

                            if (editText == et_register_email) {

                                if (presenter.isEmailValid(et_register_email.text.toString())) {
                                    imageView.setImageResource(R.drawable.ic_ok)
                                    imageView.tag = R.drawable.ic_ok

                                } else {
                                    imageView.setImageResource(R.drawable.ic_no)
                                    imageView.tag = R.drawable.ic_no
                                }

                            }

                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            imageView.setImageResource(0)
                        }
                    })

                } else {
                    if (editText.text.toString() != "") {
                        imageView.setImageResource(R.drawable.ic_ok)
                        imageView.tag = R.drawable.ic_ok

                        if (et_register_pass_ok.text.toString() != "") {

                            if (et_register_pass.text.toString() == et_register_pass_ok.text.toString()) {
                                iv_pass_ok_state.setImageResource(R.drawable.ic_ok)
                                iv_pass_ok_state.tag = R.drawable.ic_ok
                            } else {
                                iv_pass_ok_state.setImageResource(R.drawable.ic_no)
                                iv_pass_ok_state.tag = R.drawable.ic_no
                            }

                        }


                    } else {
                        imageView.setImageResource(R.drawable.ic_no)
                        imageView.tag = R.drawable.ic_no
                    }


                }

            }

        }
    }


    override fun showRegisterOk(nickName: String) {

        this@MyPageRegisterFragment.requireFragmentManager()
            .beginTransaction()
            .replace(
                R.id.mypage_main_container,
                MyPageRegisterOkFragment()
            ).commit().also {
                //                val data = Intent()
//                data.putExtra("id", et_register_email.text.toString())
//                data.putExtra("nickname", et_register_nickname.text.toString())
//                targetFragment?.onActivityResult(
//                    targetRequestCode,
//                    Activity.RESULT_OK,
//                    data
//                )
                userId = et_register_email.text.toString()
                userNickname = nickName
                loginState = true

            }
    }

    override fun showRegisterNo() {

        val alertDialog =
            AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Theme_AppCompat_Light_Dialog))

        alertDialog.setTitle("회원가입 실패")
        alertDialog.setMessage("회원 탈퇴를 실패하였습니다.")
        alertDialog.setPositiveButton("확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }

            })
        alertDialog.show()
    }

    override fun showBackPage() {
        this.requireFragmentManager().beginTransaction().remove(
            this
        ).replace(
            R.id.mypage_main_container,
            MyPageLoginFragment()
        ).addToBackStack(null).commit()
    }

    companion object {
        private const val TAG = "MyPageRegisterFragment"

    }

}