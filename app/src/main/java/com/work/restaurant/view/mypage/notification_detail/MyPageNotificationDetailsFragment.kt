package com.work.restaurant.view.mypage.notification_detail

import android.os.Bundle
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import kotlinx.android.synthetic.main.mypage_notification_detail_fragment.*

class MyPageNotificationDetailsFragment :
    BaseFragment(R.layout.mypage_notification_detail_fragment),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ib_notification_detail_back.setOnClickListener(this)

        loadNotificationData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_notification_detail_back -> {
                fragmentManager?.popBackStack()
            }
        }
    }

    private fun loadNotificationData() {

        val getNotificationDate = arguments?.getString(DATE).orEmpty()
        val getNotificationSubject = arguments?.getString(SUBJECT).orEmpty()
        val getNotificationContent = arguments?.getString(CONTENT).orEmpty()

        val splitContent = getNotificationContent.split(SPLIT_CONTEXT)

        var toConvertContent = ""

        splitContent.forEach {
            toConvertContent += "$it."
            toConvertContent += "\n"
        }

        tv_notification_detail_date.text = getNotificationDate
        tv_notification_detail_subject.text = getNotificationSubject
        tv_notification_detail_content.text = toConvertContent
    }

    companion object {

        private const val SPLIT_CONTEXT = "."

        private const val DATE = "date"
        private const val SUBJECT = "subject"
        private const val CONTENT = "content"

        fun newInstance(
            date: String,
            subject: String,
            content: String
        ) = MyPageNotificationDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(DATE, date)
                putString(SUBJECT, subject)
                putString(CONTENT, content)
            }
        }


    }

}