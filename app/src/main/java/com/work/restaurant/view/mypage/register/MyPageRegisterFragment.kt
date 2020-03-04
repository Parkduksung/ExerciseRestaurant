package com.work.restaurant.view.mypage.register

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.register.presenter.MyPageRegisterContract
import com.work.restaurant.view.mypage.register.presenter.MyPageRegisterPresenter
import com.work.restaurant.view.mypage.register_ok.MyPageRegisterOkFragment
import kotlinx.android.synthetic.main.mypage_register_fragment.*
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class MyPageRegisterFragment : BaseFragment(R.layout.mypage_register_fragment),
    View.OnClickListener, MyPageRegisterContract.View {


    private lateinit var presenter: MyPageRegisterContract.Presenter


    override fun onClick(v: View?) {


        when (v?.id) {
            R.id.ib_register_back -> {
                fragmentManager?.popBackStack()
            }
            R.id.btn_register -> {
                registerState()
            }
            R.id.btn_email_verified_show -> {
                pb_register.bringToFront()
                pb_register.visibility = View.VISIBLE
                btn_email_verified_show.isClickable = false
                btn_register.isClickable = false
                ib_register_back.isClickable = false
                ll_register_email_verified.visibility = View.GONE
                presenter.emailDuplicationCheck(et_register_email.text.toString().trim())
            }

            R.id.btn_email_verified_check -> {
                if (et_register_email_verified.text.isEmpty()) {
                    Toast.makeText(this.context, "인증번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                    iv_email_state.visibility = View.VISIBLE
                    iv_email_state.setImageResource(R.drawable.ic_no)
                    iv_email_state.tag = R.drawable.ic_no
                } else {
                    if (et_register_email_verified.text.toString().contains(" ")) {
                        Toast.makeText(this.context, "입력한 정보에 공백을 제거하세요.", Toast.LENGTH_SHORT)
                            .show()
                        iv_email_state.visibility = View.VISIBLE
                        iv_email_state.setImageResource(R.drawable.ic_no)
                        iv_email_state.tag = R.drawable.ic_no
                    } else {
                        if (et_register_email_verified.text.toString().toInt() == randomVerifyNum) {
                            Toast.makeText(this.context, "인증에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                            btn_email_verified_show.visibility = View.GONE
                            iv_email_state.visibility = View.VISIBLE
                            iv_email_state.setImageResource(R.drawable.ic_ok)
                            iv_email_state.tag = R.drawable.ic_ok
                            ll_register_email_verified.visibility = View.GONE
                            btn_register.isClickable = true
                            ib_register_back.isClickable = true
                            randomVerifyNum = 0
                            toggleVerifiedEmail = true
                            toggleDuplicationEmail = true
                            et_register_email_verified.text.clear()
                        } else {
                            Toast.makeText(
                                this.context,
                                "인증번호가 일치하지 않습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            iv_email_state.visibility = View.VISIBLE
                            iv_email_state.setImageResource(R.drawable.ic_no)
                            iv_email_state.tag = R.drawable.ic_no
                        }
                    }
                }
            }
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
        btn_email_verified_show.setOnClickListener(this)
        btn_email_verified_check.setOnClickListener(this)

        et_register_nickname.requestFocus()
        inputState(et_register_nickname, iv_nickname_state)
        inputState(et_register_email, iv_email_state)
        inputState(et_register_pass, iv_pass_state)
        inputState(et_register_pass_ok, iv_pass_ok_state)

    }

    override fun showEmailDuplicationCheck(check: Boolean) {
        if (check) {
            sendAuthCodeToEmail(et_register_email.text.toString().trim())
        } else {
            pb_register.bringToFront()
            pb_register.visibility = View.GONE
            btn_email_verified_show.visibility = View.GONE
            iv_email_state.visibility = View.VISIBLE
            iv_email_state.setImageResource(R.drawable.ic_no)
            iv_email_state.tag = R.drawable.ic_no
            ll_register_email_verified.visibility = View.GONE
            btn_email_verified_show.isClickable = true
            btn_register.isClickable = true
            ib_register_back.isClickable = true
            Toast.makeText(this.context, "중복된 이메일입니다. 다른 이메일을 사용해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerState() {

        if (iv_nickname_state.tag == R.drawable.ic_ok &&
            iv_email_state.tag == R.drawable.ic_ok &&
            iv_pass_state.tag == R.drawable.ic_ok &&
            iv_pass_ok_state.tag == R.drawable.ic_ok &&
            toggleVerifiedEmail
        ) {
            pb_register.bringToFront()
            pb_register.visibility = View.VISIBLE
            btn_register.isClickable = false
            ib_register_back.isClickable = false
            toggleVerifiedEmail = false

            presenter.register(
                et_register_nickname.text.toString().trim(),
                et_register_email.text.toString().trim(),
                et_register_pass.text.toString().trim()
            )
        } else {

            if (iv_nickname_state.tag == R.drawable.ic_no &&
                iv_email_state.tag == R.drawable.ic_ok &&
                iv_pass_state.tag == R.drawable.ic_ok &&
                iv_pass_ok_state.tag == R.drawable.ic_ok
            ) {
                Toast.makeText(
                    this.context,
                    "닉네임이 적합하지 않습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (iv_nickname_state.tag == R.drawable.ic_ok &&
                iv_email_state.tag == R.drawable.ic_no &&
                iv_pass_state.tag == R.drawable.ic_ok &&
                iv_pass_ok_state.tag == R.drawable.ic_ok
            ) {
                Toast.makeText(
                    this.context,
                    "이메일이 적합하지 않습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (iv_nickname_state.tag == R.drawable.ic_ok &&
                iv_email_state.tag == R.drawable.ic_ok &&
                iv_pass_state.tag == R.drawable.ic_no &&
                iv_pass_ok_state.tag == R.drawable.ic_ok
            ) {
                Toast.makeText(
                    this.context,
                    "6자리 이상 비밀번호를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (iv_nickname_state.tag == R.drawable.ic_ok &&
                iv_email_state.tag == R.drawable.ic_ok &&
                iv_pass_state.tag == R.drawable.ic_ok &&
                iv_pass_ok_state.tag == R.drawable.ic_no
            ) {
                Toast.makeText(
                    this.context,
                    "동일한 비밀번호를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!toggleVerifiedEmail) {
                Toast.makeText(
                    this.context,
                    "이메일 인증이 필요합니다.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this.context,
                    "틀린부분을 확인하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            btn_register.isClickable = true
            ib_register_back.isClickable = true
        }

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

                                if (isValidEmail(et_register_email.text.toString())) {
                                    if (et_register_email.text.toString().contains(".")) {
                                        ll_register_email_verified.visibility = View.GONE
                                        btn_register.isClickable = true
                                        ib_register_back.isClickable = true
                                        toggleDuplicationEmail = false
                                        toggleVerifiedEmail = false
                                        val splitToPoint =
                                            et_register_email.text.toString().split(".")
                                        val lastSplit = splitToPoint[splitToPoint.size - 1]
                                        if (lastSplit.length in 2..3) {
                                            btn_email_verified_show.isClickable = true
                                            btn_email_verified_show.visibility = View.VISIBLE
                                            imageView.visibility = View.GONE
                                        } else {
                                            btn_email_verified_show.visibility = View.GONE
                                            imageView.visibility = View.VISIBLE
                                            imageView.setImageResource(R.drawable.ic_no)
                                            imageView.tag = R.drawable.ic_no
                                        }
                                    }
                                } else {
                                    btn_email_verified_show.isClickable = true
                                    btn_email_verified_show.visibility = View.GONE
                                    imageView.visibility = View.VISIBLE
                                    imageView.setImageResource(R.drawable.ic_no)
                                    imageView.tag = R.drawable.ic_no
                                    ll_register_email_verified.visibility = View.GONE
//                                    til_register_email.error = "형식에 맞지않는 이메일입니다."
                                }

                            }

                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                            if (editText == et_register_email) {

                                if (isValidEmail(et_register_email.text.toString())) {
                                    if (et_register_email.text.toString().contains(".")) {
                                        ll_register_email_verified.visibility = View.GONE
                                        btn_register.isClickable = true
                                        ib_register_back.isClickable = true
                                        toggleDuplicationEmail = false
                                        toggleVerifiedEmail = false
                                        val splitToPoint =
                                            et_register_email.text.toString().split(".")
                                        val lastSplit = splitToPoint[splitToPoint.size - 1]
                                        if (lastSplit.length in 2..3) {
                                            btn_email_verified_show.visibility = View.VISIBLE
                                            imageView.visibility = View.GONE
                                            btn_email_verified_show.isClickable = true
                                        } else {
                                            btn_email_verified_show.visibility = View.GONE
                                            imageView.visibility = View.VISIBLE
                                            imageView.setImageResource(R.drawable.ic_no)
                                            imageView.tag = R.drawable.ic_no
                                        }
                                    }
                                } else {
                                    btn_email_verified_show.isClickable = true
                                    btn_email_verified_show.visibility = View.GONE
                                    imageView.visibility = View.VISIBLE
                                    imageView.setImageResource(R.drawable.ic_no)
                                    imageView.tag = R.drawable.ic_no
                                    ll_register_email_verified.visibility = View.GONE
//                                    til_register_email.error = "형식에 맞지않는 이메일입니다."
                                }

                            }


                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            if (editText == et_register_email) {

                                if (isValidEmail(et_register_email.text.toString())) {
                                    if (et_register_email.text.toString().contains(".")) {
                                        ll_register_email_verified.visibility = View.GONE
                                        btn_register.isClickable = true
                                        ib_register_back.isClickable = true
                                        toggleDuplicationEmail = false
                                        toggleVerifiedEmail = false
                                        val splitToPoint =
                                            et_register_email.text.toString().split(".")
                                        val lastSplit = splitToPoint[splitToPoint.size - 1]
                                        if (lastSplit.length in 2..3) {
                                            btn_email_verified_show.visibility = View.VISIBLE
                                            imageView.visibility = View.GONE
                                            btn_email_verified_show.isClickable = true
                                        } else {
                                            btn_email_verified_show.visibility = View.GONE
                                            imageView.visibility = View.VISIBLE
                                            imageView.setImageResource(R.drawable.ic_no)
                                            imageView.tag = R.drawable.ic_no
                                        }
                                    }
                                } else {
                                    btn_email_verified_show.isClickable = true
                                    btn_email_verified_show.visibility = View.GONE
                                    imageView.visibility = View.VISIBLE
                                    imageView.setImageResource(R.drawable.ic_no)
                                    imageView.tag = R.drawable.ic_no
                                    ll_register_email_verified.visibility = View.GONE
//                                    til_register_email.error = "형식에 맞지않는 이메일입니다."
                                }

                            }

                            imageView.setImageResource(0)
                        }
                    })
                } else {
                    if (editText.text.toString().isNotEmpty()) {
                        imageView.setImageResource(R.drawable.ic_ok)
                        imageView.tag = R.drawable.ic_ok

                        if (et_register_pass_ok.text.toString().isNotEmpty()) {
                            if (et_register_pass.text.toString() == et_register_pass_ok.text.toString()) {
                                iv_pass_ok_state.setImageResource(R.drawable.ic_ok)
                                iv_pass_ok_state.tag = R.drawable.ic_ok
                            } else {
                                iv_pass_ok_state.setImageResource(R.drawable.ic_no)
                                iv_pass_ok_state.tag = R.drawable.ic_no
                            }

                        }
                    }
                    if (!toggleDuplicationEmail) {
                        iv_email_state.setImageResource(R.drawable.ic_no)
                        iv_email_state.tag = R.drawable.ic_no
                    }


                }

            }

        }
    }

    override fun showRegisterOk() {
        pb_register.visibility = View.GONE
        btn_register.isClickable = true
        ib_register_back.isClickable = true

        val data = Intent().apply {
            putExtra(REGISTER_ID, et_register_email.text.toString())
            putExtra(REGISTER_NICKNAME, et_register_nickname.text.toString())
        }
        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            data
        )

        requireFragmentManager()
            .beginTransaction()
            .replace(
                R.id.mypage_register_container,
                MyPageRegisterOkFragment()
            ).commit()

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

    private fun sendAuthCodeToEmail(
        sendEmail: String       // 받는 메일 주소
    ) {

        AppExecutors().diskIO.execute {

            //6자리 인증번호
            val authNum = Random().nextInt(899999) + 100000

            randomVerifyNum = authNum
            // 보내는 메일 주소와 비밀번호
            val username = getString(R.string.verified_email_user)
            val password = getString(R.string.verified_email_pass)
            val sendTitle = getString(R.string.verified_email_title)
            val sendBody = getString(
                R.string.verified_email_content,
                authNum.toString()
            )

            val props = Properties().apply {
                setProperty("mail.transport.protocol", "smtp")
                setProperty("mail.host", "smtp.gmail.com")
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
            }

            val session = Session.getDefaultInstance(props)
            session.debug = true

            try {
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(username))

                message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(sendEmail)
                )
                message.subject = sendTitle
                message.setText(sendBody)


                val trans = session.getTransport("smtp")
                trans.connect("smtp.gmail.com", 587, username, password)
                trans.sendMessage(message, message.allRecipients)

                AppExecutors().mainThread.execute {
                    btn_register.isClickable = true
                    ib_register_back.isClickable = true
                    pb_register.visibility = View.GONE
                    btn_email_verified_show.visibility = View.GONE
                    ll_register_email_verified.visibility = View.VISIBLE
                    et_register_email_verified.requestFocus()
                    Toast.makeText(
                        App.instance.context(),
                        "입력한 이메일로 인증번호를 전송하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }

    private fun isValidEmail(charSequence: CharSequence): Boolean =
        !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()


    companion object {
        private const val TAG = "MyPageRegisterFragment"

        var randomVerifyNum = 0

        private var toggleVerifiedEmail = false

        private var toggleDuplicationEmail = false

        const val REGISTER_ID = "id"
        const val REGISTER_NICKNAME = "nickname"

    }

}