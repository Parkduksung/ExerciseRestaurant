package com.work.restaurant.view.mypage.logout

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutContract
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutPresenter
import kotlinx.android.synthetic.main.mypage_logout_fragment.*

class MyPageLogoutFragment : BaseFragment(R.layout.mypage_logout_fragment), View.OnClickListener,
    MyPageLogoutContract.View {


    private lateinit var presenter: MyPageLogoutContract.Presenter


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout_cancel -> {
                fragmentManager?.popBackStack()
            }

            R.id.btn_logout_ok -> {

                val bundle = arguments
                val getUserId = bundle?.getString(LOGOUT_ID).orEmpty()

                if (getUserId.isNotEmpty()) {
                    presenter.logoutOk(getUserId)
                } else {
                    Toast.makeText(App.instance.context(), "로그아웃을 실패하였습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState).also {
            it?.setBackgroundColor(
                ContextCompat.getColor(
                    App.instance.context(),
                    R.color.transparent
                )
            )
            it?.setOnTouchListener { _, _ ->
                true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter =
            MyPageLogoutPresenter(
            this,
            Injection.provideLoginRepository()
        )
        btn_logout_cancel.setOnClickListener(this)
        btn_logout_ok.setOnClickListener(this)

    }

    override fun showLogoutOk() {

        FirebaseAuth.getInstance().signOut()

        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            null
        )
        fragmentManager?.popBackStack()
    }

    override fun showLogoutNo() {
        Toast.makeText(App.instance.context(), "로그아웃을 실패하였습니다.", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MyPageLogoutFragment"

        const val LOGOUT_ID = "id"

        fun newInstance(
            userId: String
        ) = MyPageLogoutFragment().apply {
            arguments = Bundle().apply {
                putString(LOGOUT_ID, userId)
            }
        }

    }

}
