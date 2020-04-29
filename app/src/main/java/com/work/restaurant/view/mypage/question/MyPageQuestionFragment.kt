package com.work.restaurant.view.mypage.question

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.question.presenter.MyPageQuestionContract
import com.work.restaurant.view.mypage.question.presenter.MyPageQuestionPresenter
import kotlinx.android.synthetic.main.mypage_question_fragment.*

class MyPageQuestionFragment : BaseFragment(R.layout.mypage_question_fragment),
    View.OnClickListener, MyPageQuestionContract.View {

    private lateinit var presenter: MyPageQuestionPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
            MyPageQuestionPresenter(
                this,
                Injection.provideQuestionRepository()
            )

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
                    Toast.makeText(
                        App.instance.context(),
                        getString(R.string.question_not_input),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    override fun showResult(resultState: Boolean) {

        if (resultState) {
            Toast.makeText(
                App.instance.context(),
                getString(R.string.question_send_ok), Toast.LENGTH_SHORT
            ).show()
            fragmentManager?.popBackStack()
        } else {
            Toast.makeText(
               context,
                getString(R.string.question_send_no),
                Toast.LENGTH_SHORT
            ).show()
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