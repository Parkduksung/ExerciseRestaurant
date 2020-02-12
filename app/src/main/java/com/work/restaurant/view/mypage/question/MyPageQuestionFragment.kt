package com.work.restaurant.view.mypage.question

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.question.presenter.MyPageQuestionContract
import com.work.restaurant.view.mypage.question.presenter.MyPageQuestionPresenter
import kotlinx.android.synthetic.main.mypage_question_fragment.*

class MyPageQuestionFragment : BaseFragment(R.layout.mypage_question_fragment),
    View.OnClickListener, MyPageQuestionContract.View {

    private lateinit var presenter: MyPageQuestionPresenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_question_back -> {
                activity?.onBackPressed()
            }
            R.id.btn_send_question -> {

                if (et_question_content.text.toString() != "") {
                    presenter.sendQuestion(et_question_content.text.toString())
                }
            }
        }
    }

    override fun showResult(message: String) {
        if (message == "success") {
            Toast.makeText(this.context, "문의사항이 전달되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this.context, "문의사항이 전달되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MyPageQuestionPresenter(
            this,
            Injection.provideQuestionRepository()
        )

        ib_question_back.setOnClickListener(this)
        btn_send_question.setOnClickListener(this)

    }
}