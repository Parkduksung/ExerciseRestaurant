package com.work.restaurant.view.mypage.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.databinding.MypageRegisterFragmentBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.util.Keyboard
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.register.presenter.MyPageRegisterContract
import com.work.restaurant.view.mypage.register.presenter.MyPageRegisterPresenter
import com.work.restaurant.view.mypage.register_ok.MyPageRegisterOkFragment
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class MyPageRegisterFragment : BaseFragment<MypageRegisterFragmentBinding>(
    MypageRegisterFragmentBinding::bind,
    R.layout.mypage_register_fragment
),
    View.OnClickListener, MyPageRegisterContract.View {

    private lateinit var presenter: MyPageRegisterContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = get { parametersOf(this) }

        binding.btnEmailVerifiedShow

        binding.ibRegisterBack.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.btnEmailVerifiedShow.setOnClickListener(this)
        binding.btnEmailVerifiedCheck.setOnClickListener(this)

        binding.etRegisterNickname.requestFocus()
        inputState(binding.etRegisterNickname, binding.ivNicknameState)
        inputState(binding.etRegisterEmail, binding.ivEmailState)
        inputState(binding.etRegisterPass, binding.ivPassState)
        inputState(binding.etRegisterPassOk, binding.ivPassOkState)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_register_back -> {
                fragmentManager?.popBackStack()
            }
            R.id.btn_register -> {
                registerState()
            }
            R.id.btn_email_verified_show -> {
                presenter.emailDuplicationCheck(binding.etRegisterEmail.text.toString().trim())
            }

            R.id.btn_email_verified_check -> {
                verifiedCheck(binding.etRegisterEmailVerified.text.toString())
            }
        }
    }

    override fun showEmailDuplicationCheck(check: Boolean) {

        binding.btnEmailVerifiedShow.isClickable = false

        if (check) {
            sendAuthCodeToEmail(binding.etRegisterEmail.text.toString().trim())
        } else {
            showProgressState(false)
            binding.btnEmailVerifiedShow.isVisible = false

            binding.ivEmailState.apply {
                isVisible = false
                setImageResource(R.drawable.ic_no)
                tag = R.drawable.ic_no
            }
            showToast(getString(R.string.register_duplicate))
        }
    }

    override fun showProgressState(state: Boolean) {
        binding.pbRegister.apply {
            bringToFront()
            isVisible = state
        }
        binding.btnRegister.isClickable = !state
        binding.ibRegisterBack.isClickable = !state
    }

    override fun showRegisterOk() {
        showProgressState(false)
        val data = Intent().apply {
            putExtra(REGISTER_ID, binding.etRegisterEmail.text.toString())
            putExtra(REGISTER_NICKNAME, binding.etRegisterNickname.text.toString())
        }
        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            data
        )

        requireFragmentManager()
            .beginTransaction()
            .replace(
                R.id.myPage_register_container,
                MyPageRegisterOkFragment(),
                MyPageRegisterOkFragment.TAG
            ).commit()
    }

    override fun showRegisterNo(sort: Int) {
        showProgressState(false)

        when (sort) {

            MyPageRegisterPresenter.DUPLICATE_REGISTER -> {

                ShowAlertDialog(context).apply {
                    titleAndMessage(
                        getString(R.string.register_no),
                        getString(R.string.register_duplicate)
                    )
                    alertDialog.setPositiveButton(getString(R.string.common_ok)) { _, _ -> }
                    showDialog()
                }

            }
            MyPageRegisterPresenter.NOT_VERIFIED_REGISTER -> {

                ShowAlertDialog(context).apply {
                    titleAndMessage(
                        getString(R.string.register_no),
                        getString(R.string.register_not_verify)
                    )
                    alertDialog.setPositiveButton(getString(R.string.common_ok)) { _, _ -> }
                    showDialog()
                }

            }

        }
    }

    private fun verifiedCheck(checkNum: String) {
        binding.btnEmailVerifiedShow.isVisible = false

        if (checkNum.isEmpty()) {
            showToast(getString(R.string.register_not_input_checkNum))
            binding.ivEmailState.apply {
                isVisible = true
                setImageResource(R.drawable.ic_no)
                tag = R.drawable.ic_no
            }

        } else {
            if (checkNum.contains(EMPTY_TEXT)) {
                showToast(getString(R.string.common_have_trim))
                binding.ivEmailState.apply {
                    isVisible = true
                    setImageResource(R.drawable.ic_no)
                    tag = R.drawable.ic_no
                }
            } else {

                if (checkNum.toInt() == randomVerifyNum) {
                    showToast(getString(R.string.register_checkNum_ok))
                    binding.ivEmailState.apply {
                        isVisible = true
                        setImageResource(R.drawable.ic_ok)
                        tag = R.drawable.ic_ok
                    }
                    binding.llRegisterEmailVerified.isVisible = false

                    showProgressState(false)
                    randomVerifyNum = 0
                    toggleVerifiedEmail = true
                    toggleDuplicationEmail = true

                    binding.etRegisterEmailVerified.text.clear()

                    Keyboard.hideEditText(context, binding.etRegisterEmailVerified)

                } else {
                    showToast(getString(R.string.register_not_same_checkNum))
                    binding.ivEmailState.apply {
                        isVisible = true
                        setImageResource(R.drawable.ic_no)
                        tag = R.drawable.ic_no
                    }

                }
            }
        }
    }

    private fun registerState() {
        if (binding.ivNicknameState.tag == R.drawable.ic_ok &&
            binding.ivEmailState.tag == R.drawable.ic_ok &&
            binding.ivPassState.tag == R.drawable.ic_ok &&
            binding.ivPassOkState.tag == R.drawable.ic_ok &&
            toggleVerifiedEmail
        ) {

            toggleVerifiedEmail = false

            presenter.register(
                binding.etRegisterNickname.text.toString().trim(),
                binding.etRegisterEmail.text.toString().trim(),
                binding.etRegisterPass.text.toString().trim()
            )
        } else {
            if (binding.ivNicknameState.tag == R.drawable.ic_no &&
                binding.ivEmailState.tag == R.drawable.ic_ok &&
                binding.ivPassState.tag == R.drawable.ic_ok &&
                binding.ivPassOkState.tag == R.drawable.ic_ok
            ) {
                showToast(getString(R.string.register_not_proper_nickname))

            } else if (binding.ivNicknameState.tag == R.drawable.ic_ok &&
                binding.ivEmailState.tag == R.drawable.ic_no &&
                binding.ivPassState.tag == R.drawable.ic_ok &&
                binding.ivPassOkState.tag == R.drawable.ic_ok
            ) {
                showToast(getString(R.string.register_not_proper_email))
            } else if (binding.ivNicknameState.tag == R.drawable.ic_ok &&
                binding.ivEmailState.tag == R.drawable.ic_ok &&
                binding.ivPassState.tag == R.drawable.ic_no &&
                binding.ivPassOkState.tag == R.drawable.ic_ok
            ) {
                showToast(getString(R.string.register_not_proper_pass))

            } else if (binding.ivNicknameState.tag == R.drawable.ic_ok &&
                binding.ivEmailState.tag == R.drawable.ic_ok &&
                binding.ivPassState.tag == R.drawable.ic_ok &&
                binding.ivPassOkState.tag == R.drawable.ic_no
            ) {
                showToast(getString(R.string.register_not_proper_passOk))

            } else if (!toggleVerifiedEmail) {
                showToast(getString(R.string.register_not_verified_email))
            } else {
                showToast(getString(R.string.register_not_enough))
            }
        }

    }

    private fun inputState(editText: EditText, imageView: ImageView) {

        editText.setOnFocusChangeListener { v, hasFocus ->
            if (v != null) {
                if (hasFocus) {
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            imageView.setImageResource(0)

                            if (editText == binding.etRegisterPass || editText == binding.etRegisterPassOk) {
                                if (editText.length() < LIMIT_PASS_LENGTH) {
                                    imageView.apply {
                                        setImageResource(R.drawable.ic_no)
                                        tag = R.drawable.ic_no
                                    }

                                } else {
                                    if (editText == binding.etRegisterPassOk) {
                                        if (binding.etRegisterPass.text.toString() == binding.etRegisterPassOk.text.toString()) {
                                            imageView.apply {
                                                setImageResource(R.drawable.ic_ok)
                                                tag = R.drawable.ic_ok
                                            }
                                        } else {
                                            imageView.apply {
                                                setImageResource(R.drawable.ic_no)
                                                tag = R.drawable.ic_no
                                            }
                                        }
                                    }
                                }
                            }

                            if (editText == binding.etRegisterEmail) {

                                if (RelateLogin.isValidEmail(binding.etRegisterEmail.text.toString())) {
                                    if (binding.etRegisterEmail.text.toString().contains(
                                            CONTAIN_OR_SPLIT_EMAIL_TEXT
                                        )
                                    ) {
                                        binding.llRegisterEmailVerified.isVisible = false
                                        showProgressState(false)

                                        toggleDuplicationEmail = false
                                        toggleVerifiedEmail = false

                                        if (getSplitEmailLastText(binding.etRegisterEmail.text.toString()).length in 2..3) {

                                            binding.btnEmailVerifiedShow.apply {
                                                isClickable = true
                                                isVisible = true
                                            }

                                        } else {
                                            binding.btnEmailVerifiedShow.isVisible = false

                                            imageView.apply {
                                                isVisible = true
                                                setImageResource(R.drawable.ic_no)
                                                tag = R.drawable.ic_no
                                            }

                                        }
                                    }
                                } else {
                                    binding.btnEmailVerifiedShow.isVisible = false

                                    imageView.apply {
                                        isVisible = true
                                        setImageResource(R.drawable.ic_no)
                                        tag = R.drawable.ic_no
                                    }

                                    binding.llRegisterEmailVerified.visibility = View.GONE
                                }

                            }
                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                            if (editText == binding.etRegisterEmail) {

                                if (RelateLogin.isValidEmail(binding.etRegisterEmail.text.toString())) {
                                    if (binding.etRegisterEmail.text.toString().contains(
                                            CONTAIN_OR_SPLIT_EMAIL_TEXT
                                        )
                                    ) {
                                        binding.llRegisterEmailVerified.isVisible = false
                                        showProgressState(false)

                                        toggleDuplicationEmail = false
                                        toggleVerifiedEmail = false

                                        if (getSplitEmailLastText(binding.etRegisterEmail.text.toString()).length in 2..3) {

                                            binding.btnEmailVerifiedShow.apply {
                                                isClickable = true
                                                isVisible = true
                                            }

                                        } else {
                                            binding.btnEmailVerifiedShow.isVisible = false

                                            imageView.apply {
                                                isVisible = true
                                                setImageResource(R.drawable.ic_no)
                                                tag = R.drawable.ic_no
                                            }

                                        }
                                    }
                                } else {
                                    binding.btnEmailVerifiedShow.isVisible = false

                                    imageView.apply {
                                        isVisible = true
                                        setImageResource(R.drawable.ic_no)
                                        tag = R.drawable.ic_no
                                    }
                                    binding.llRegisterEmailVerified.visibility = View.GONE
                                }

                            }

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            if (editText == binding.etRegisterEmail) {

                                if (RelateLogin.isValidEmail(binding.etRegisterEmail.text.toString())) {
                                    if (binding.etRegisterEmail.text.toString().contains(
                                            CONTAIN_OR_SPLIT_EMAIL_TEXT
                                        )
                                    ) {
                                        binding.llRegisterEmailVerified.isVisible = false
                                        showProgressState(false)
                                        toggleDuplicationEmail = false
                                        toggleVerifiedEmail = false

                                        if (getSplitEmailLastText(binding.etRegisterEmail.text.toString()).length in 2..3) {

                                            binding.btnEmailVerifiedShow.apply {
                                                isClickable = true
                                                isVisible = true
                                            }
                                        } else {
                                            binding.btnEmailVerifiedShow.isVisible = false

                                            imageView.apply {
                                                isVisible = true
                                                setImageResource(R.drawable.ic_no)
                                                tag = R.drawable.ic_no
                                            }
                                        }
                                    }
                                } else {
                                    binding.btnEmailVerifiedShow.isVisible = false

                                    imageView.apply {
                                        isVisible = true
                                        setImageResource(R.drawable.ic_no)
                                        tag = R.drawable.ic_no
                                    }
                                    binding.llRegisterEmailVerified.visibility = View.GONE
                                }
                            }
                            imageView.setImageResource(0)
                        }
                    })
                } else {
                    if (editText.text.toString().isNotEmpty()) {

                        if (editText == binding.etRegisterPass || editText == binding.etRegisterPassOk) {
                            if (editText.length() < LIMIT_PASS_LENGTH) {
                                imageView.apply {
                                    setImageResource(R.drawable.ic_no)
                                    tag = R.drawable.ic_no
                                }
                            } else {
                                imageView.apply {
                                    setImageResource(R.drawable.ic_ok)
                                    tag = R.drawable.ic_ok
                                }
                            }
                        } else {
                            imageView.apply {
                                setImageResource(R.drawable.ic_ok)
                                tag = R.drawable.ic_ok
                            }
                        }

                        if (binding.etRegisterPassOk.text.toString().isNotEmpty()) {
                            if (binding.etRegisterPass.text.toString() == binding.etRegisterPassOk.text.toString()) {
                                binding.ivPassOkState.apply {
                                    setImageResource(R.drawable.ic_ok)
                                    tag = R.drawable.ic_ok
                                }
                            } else {
                                binding.ivPassOkState.apply {
                                    setImageResource(R.drawable.ic_no)
                                    tag = R.drawable.ic_no
                                }
                            }
                        }
                    }
                    if (!toggleDuplicationEmail) {
                        binding.ivEmailState.apply {
                            setImageResource(R.drawable.ic_no)
                            tag = R.drawable.ic_no
                        }
                    }
                }

            }

        }
    }

    private fun getSplitEmailLastText(email: String): String {
        val splitToPoint =
            email.split(CONTAIN_OR_SPLIT_EMAIL_TEXT)
        return splitToPoint[splitToPoint.size - 1]
    }

    private fun sendAuthCodeToEmail(
        sendEmail: String
    ) {

        AppExecutors().diskIO.execute {


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

            val props =
                Properties().apply {
                    setProperty("mail.transport.protocol", "smtp")
                    setProperty("mail.host", "smtp.gmail.com")
                    put("mail.smtp.starttls.enable", "true")
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.starttls.enable", "true")
                }

            val session = Session.getDefaultInstance(props)
            session.debug = true

            try {
                val message =
                    MimeMessage(session)
                message.setFrom(InternetAddress(username))

                message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(sendEmail)
                )
                message.subject = sendTitle
                message.setText(sendBody)


                val trans =
                    session.getTransport("smtp")
                trans.connect("smtp.gmail.com", 587, username, password)
                trans.sendMessage(message, message.allRecipients)

                AppExecutors().mainThread.execute {
                    showProgressState(false)
                    binding.llRegisterEmailVerified.isVisible = true
                    binding.etRegisterEmailVerified.requestFocus()
                    showToast(getString(R.string.register_send_checkNum))
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }


    companion object {
        private const val TAG = "MyPageRegisterFragment"

        private const val LIMIT_PASS_LENGTH = 6
        private const val CONTAIN_OR_SPLIT_EMAIL_TEXT = "."
        private const val EMPTY_TEXT = " "

        private var toggleVerifiedEmail = false
        private var toggleDuplicationEmail = false

        var randomVerifyNum = 0

        const val REGISTER_ID = "id"
        const val REGISTER_NICKNAME = "nickname"

    }

}