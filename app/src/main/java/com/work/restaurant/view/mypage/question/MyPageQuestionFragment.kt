package com.work.restaurant.view.mypage.question

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.databinding.MypageQuestionFragmentBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.question.presenter.MyPageQuestionContract
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class MyPageQuestionFragment : BaseFragment<MypageQuestionFragmentBinding>(
    MypageQuestionFragmentBinding::bind,
    R.layout.mypage_question_fragment
),
    View.OnClickListener, MyPageQuestionContract.View {

    private lateinit var presenter: MyPageQuestionContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = get { parametersOf(this) }

        binding.ibQuestionBack.setOnClickListener(this)
        binding.btnSendQuestion.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_question_back -> {
                fragmentManager?.popBackStack()
            }
            R.id.btn_send_question -> {
                if (binding.etQuestionContent.text.toString().isNotEmpty()) {
                    showProgressState(true)
                    presenter.sendQuestion(binding.etQuestionContent.text.toString())
                } else {
                    showToast(getString(R.string.question_not_input))
                }
            }
        }
    }

    override fun showResult(resultState: Boolean) {

        if (resultState) {
            showToast(getString(R.string.question_send_ok))
            fragmentManager?.popBackStack()
        } else {
            showToast(getString(R.string.question_send_no))
        }

    }

    override fun showProgressState(state: Boolean) {

        binding.pbQuestion.apply {
            bringToFront()
            isVisible = state
        }
        binding.btnSendQuestion.isClickable = !state
    }
}