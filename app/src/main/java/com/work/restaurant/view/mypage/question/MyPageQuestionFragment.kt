package com.work.restaurant.view.mypage.question

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.ext.showToast
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.question.presenter.MyPageQuestionContract
import kotlinx.android.synthetic.main.mypage_question_fragment.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class MyPageQuestionFragment : BaseFragment(R.layout.mypage_question_fragment),
    View.OnClickListener, MyPageQuestionContract.View {

    private lateinit var presenter: MyPageQuestionContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = get { parametersOf(this) }

        ib_question_back.setOnClickListener(this)
        btn_send_question.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_question_back -> {
                fragmentManager?.popBackStack()
            }
            R.id.btn_send_question -> {
                if (et_question_content.text.toString().isNotEmpty()) {
                    showProgressState(true)
                    presenter.sendQuestion(et_question_content.text.toString())
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
        pb_question?.let {
            pb_question.bringToFront()
            pb_question.isVisible = state
        }

        btn_send_question?.let {
            btn_send_question.isClickable = !state
        }
    }
}