package com.work.restaurant.view.mypage.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.mypage.contract.MyPageRegisterContract
import com.work.restaurant.view.mypage.fragment.MyPageFragment.Companion.loginState
import com.work.restaurant.view.mypage.fragment.MyPageFragment.Companion.userId
import com.work.restaurant.view.mypage.fragment.MyPageFragment.Companion.userNickname
import com.work.restaurant.view.mypage.presenter.MyPageRegisterPresenter
import kotlinx.android.synthetic.main.mypage_register_fragment.*

class MyPageRegisterFragment : Fragment(), View.OnClickListener, MyPageRegisterContract.View {

    private lateinit var presenter: MyPageRegisterContract.Presenter
    private var emailState = false
    private var nicknameState = false
    private var passState = false
    private var passSameState = false


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
        if (et_register_nickname.text.toString() != "") {
            nicknameState = true
            iv_nickname_state.setImageResource(R.drawable.ic_ok)
            Log.d("ddddddddddddddddd", "$nicknameState")
        } else {
            nicknameState = false
            iv_nickname_state.setImageResource(R.drawable.ic_no)
            Log.d("ddddddddddddddddd", "$nicknameState")
        }

        if (et_register_email.text.toString() != "") {
            emailState = true
            iv_email_state.setImageResource(R.drawable.ic_ok)
            Log.d("ddddddddddddddddd", "$emailState")
        } else {
            emailState = false
            iv_email_state.setImageResource(R.drawable.ic_no)
            Log.d("ddddddddddddddddd", "$emailState")
        }

        if (et_register_pass.text.toString() != "") {
            passState = true
            iv_pass_state.setImageResource(R.drawable.ic_ok)
            Log.d("ddddddddddddddddd", "$passState")

        } else {
            passState = false
            iv_pass_state.setImageResource(R.drawable.ic_no)
            Log.d("ddddddddddddddddd", "$passState")

        }

        if (et_register_pass.text.toString() == et_register_pass_ok.text.toString()) {
            passSameState = true
            iv_pass_ok_state.setImageResource(R.drawable.ic_ok)
            Log.d("ddddddddddddddddd", "$passSameState")
        } else {
            passSameState = false
            iv_pass_ok_state.setImageResource(R.drawable.ic_no)
            Log.d("ddddddddddddddddd", "$passSameState")
        }


        if (nicknameState && emailState && passState && passSameState) {
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mypage_register_fragment, container, false).also {
            presenter = MyPageRegisterPresenter(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        ib_register_back.setOnClickListener(this)

        btn_register.setOnClickListener(this)

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
        ).commit()
    }


    companion object {
        private const val TAG = "MyPageRegisterFragment"

    }

}