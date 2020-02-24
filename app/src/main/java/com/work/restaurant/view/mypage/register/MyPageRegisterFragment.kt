package com.work.restaurant.view.mypage.register

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.register.presenter.MyPageRegisterContract
import com.work.restaurant.view.mypage.register.presenter.MyPageRegisterPresenter
import com.work.restaurant.view.mypage.register_ok.MyPageRegisterOkFragment
import kotlinx.android.synthetic.main.mypage_register_fragment.*


class MyPageRegisterFragment : BaseFragment(R.layout.mypage_register_fragment),
    View.OnClickListener, MyPageRegisterContract.View {


    private lateinit var presenter: MyPageRegisterContract.Presenter

    private lateinit var registerListener: RegisterListener


    interface RegisterListener {
        fun registerOk(state: Boolean, userId: String)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity != null && activity is RegisterListener) {
            registerListener = activity as RegisterListener
        }
    }


    override fun onClick(v: View?) {


        when (v?.id) {

            R.id.ib_register_back -> {
                requireFragmentManager().popBackStack()
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
            pb_register.bringToFront()
            pb_register.visibility = View.VISIBLE
            btn_register.isClickable = false
            ib_register_back.isClickable = false

            presenter.register(
                et_register_nickname.text.toString().trim(),
                et_register_email.text.toString().trim(),
                et_register_pass.text.toString().trim()
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

            btn_register.isClickable = true
            ib_register_back.isClickable = true
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MyPageRegisterPresenter(
            this,
            Injection.provideUserRepository(),
            Injection.provideLoginRepository()
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

    override fun showLoginState() {

        registerOk()

    }

    override fun showRegisterState() {
        presenter.registerLogin(
            et_register_nickname.text.toString().trim(),
            et_register_email.text.toString().trim(),
            et_register_pass.text.toString().trim(),
            true
        )

    }


    override fun showRegisterOk(nickName: String) {

        registerListener.registerOk(
            true,
            et_register_email.text.toString().trim()
        )

        pb_register.visibility = View.GONE
        btn_register.isClickable = true
        ib_register_back.isClickable = true

        requireFragmentManager()
            .beginTransaction()
            .replace(
                R.id.mypage_register_container,
                MyPageRegisterOkFragment()
            ).commit()
    }


    private fun registerOk() {
        presenter.loginForRegister(
            et_register_email.text.toString().trim(),
            et_register_pass.text.toString().trim()
        )
    }


    override fun showRegisterNo(sort: Int) {

        if (sort == 0) {
            pb_register.visibility = View.GONE
            btn_register.isClickable = true
            ib_register_back.isClickable = true

            val alertDialog =
                AlertDialog.Builder(
                    ContextThemeWrapper(
                        activity,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )

            alertDialog.setTitle("회원가입 실패")
            alertDialog.setMessage(" 이미 가입된 이메일입니다. \n 다른 이메일을 입력해주세요.")
            alertDialog.setPositiveButton("확인",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        et_register_email.text.clear()
                    }
                })
            alertDialog.show()
        } else {
            pb_register.visibility = View.GONE
            btn_register.isClickable = true
            ib_register_back.isClickable = true

            val alertDialog =
                AlertDialog.Builder(
                    ContextThemeWrapper(
                        activity,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )

            alertDialog.setTitle("회원가입 실패")
            alertDialog.setMessage(" 저장할 수 없는 이메일입니다. \n 다른 이메일을 입력해주세요.")
            alertDialog.setPositiveButton("확인",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        et_register_email.text.clear()
                    }
                })
            alertDialog.show()
        }
    }


    companion object {
        private const val TAG = "MyPageRegisterFragment"

    }

}