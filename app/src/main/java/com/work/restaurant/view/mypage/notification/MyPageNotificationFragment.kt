package com.work.restaurant.view.mypage.notification

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.NotificationModel
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.notification.adapter.NotificationAdapter
import com.work.restaurant.view.mypage.notification.presenter.MyPageNotificationContract
import com.work.restaurant.view.mypage.notification.presenter.MyPageNotificationPresenter
import kotlinx.android.synthetic.main.mypage_notification_fragment.*

class MyPageNotificationFragment : BaseFragment(R.layout.mypage_notification_fragment),
    MyPageNotificationContract.View, AdapterDataListener.GetNotificationList, View.OnClickListener {

    private lateinit var presenter: MyPageNotificationPresenter

    private val notificationAdapter: NotificationAdapter by lazy { NotificationAdapter() }

    private lateinit var notificationDataListener: NotificationDataListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NotificationDataListener) {
            notificationDataListener = context
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_notification_back -> {
                requireFragmentManager().popBackStack()
            }
        }
    }

    override fun getData(data: NotificationModel) {

        notificationDataListener.getNotificationData(data)
    }


    override fun showNotificationList(list: List<NotificationModel>) {

        recyclerview_notification.run {
            this.adapter = notificationAdapter
            notificationAdapter.clearListData()
            notificationAdapter.addData(list)
            layoutManager = LinearLayoutManager(this.context)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MyPageNotificationPresenter(
            this,
            Injection.provideNotificationRepository()
        )

        presenter.getNotificationList()

        notificationAdapter.setItemClickListener(this)

        ib_notification_back.setOnClickListener(this)
    }


}