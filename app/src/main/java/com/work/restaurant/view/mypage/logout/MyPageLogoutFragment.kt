package com.work.restaurant.view.mypage.logout

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.work.restaurant.R
import com.work.restaurant.databinding.MypageLogoutFragmentBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutContract
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class MyPageLogoutFragment : BaseFragment<MypageLogoutFragmentBinding>(
    MypageLogoutFragmentBinding::bind,
    R.layout.mypage_logout_fragment
), View.OnClickListener,
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
        presenter = get { parametersOf(this) }

        binding.btnLogoutCancel.setOnClickListener(this)
        binding.btnLogoutOk.setOnClickListener(this)
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
        binding.pbLogout.apply {
            bringToFront()
            isVisible = state
        }
        binding.btnLogoutCancel.isClickable != state
        binding.btnLogoutOk.isClickable != state
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
