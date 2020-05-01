package com.work.restaurant.view.mypage.logout

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutContract
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutPresenter
import kotlinx.android.synthetic.main.mypage_fragment.*
import kotlinx.android.synthetic.main.mypage_logout_fragment.*

class MyPageLogoutFragment : BaseFragment(R.layout.mypage_logout_fragment), View.OnClickListener,
    MyPageLogoutContract.View {


    private lateinit var presenter: MyPageLogoutContract.Presenter

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout_cancel -> {
                fragmentManager?.popBackStack()
            }

            R.id.btn_logout_ok -> {

                val getUserId = arguments?.getString(LOGOUT_ID).orEmpty()

                if (getUserId.isNotEmpty()) {
                    showProgressState(true)
                    presenter.logoutOk(getUserId)
                } else {
                    showToast(getString(R.string.logout_no))
                }
            }
        }
    }

    override fun showProgressState(state: Boolean) {

        pb_logout?.let {
            pb_logout.bringToFront()
            pb_logout.isVisible = state
        }
        btn_logout_cancel?.let {
            btn_logout_cancel.isClickable != state
        }
        btn_logout?.let {
            btn_logout_ok.isClickable != state
        }

    }

    override fun showLogoutOk() {

        showProgressState(false)
        FirebaseAuth.getInstance().signOut()

        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            null
        )
        fragmentManager?.popBackStack()
    }

    override fun showLogoutNo() {
        showProgressState(false)
        showToast(getString(R.string.logout_no))
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
