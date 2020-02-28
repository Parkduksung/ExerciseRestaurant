package com.work.restaurant.view.mypage.question

import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_question_back -> {
                fragmentManager?.popBackStack()
            }
            R.id.btn_send_question -> {

                if (et_question_content.text.toString().isNotEmpty()) {
                    pb_question.bringToFront()
                    pb_question.visibility = View.VISIBLE
                    btn_send_question.isClickable = false
                    presenter.sendQuestion(et_question_content.text.toString())
                } else {
                    Toast.makeText(App.instance.context(), "정확한 입력을 부탁드립니다!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun showResult(message: String) {
        if (pb_question != null) {

            pb_question.visibility = View.GONE
            btn_send_question.isClickable = true
            if (message == "success") {

                Toast.makeText(App.instance.context(), "문의사항이 전달되었습니다.", Toast.LENGTH_SHORT).show()
                fragmentManager?.popBackStack()
            } else {
                Toast.makeText(this.context, "문의사항이 전달되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }

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