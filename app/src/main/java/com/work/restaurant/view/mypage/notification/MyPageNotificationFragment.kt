package com.work.restaurant.view.mypage.notification

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.data.model.NotificationModel
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.mypage.notification.adapter.NotificationAdapter
import com.work.restaurant.view.mypage.notification.presenter.MyPageNotificationContract
import kotlinx.android.synthetic.main.mypage_notification_fragment.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class MyPageNotificationFragment : BaseFragment(R.layout.mypage_notification_fragment),
    MyPageNotificationContract.View, AdapterDataListener.GetNotificationList, View.OnClickListener {

    private lateinit var presenter: MyPageNotificationContract.Presenter

    private lateinit var notificationDataListener: NotificationDataListener

    private val notificationAdapter: NotificationAdapter by lazy { NotificationAdapter() }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as NotificationDataListener).let {
            notificationDataListener = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = get { parametersOf(this) }
        notificationAdapter.setItemClickListener(this)
        ib_notification_back.setOnClickListener(this)

        rv_notification.run {
            this.adapter = notificationAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        presenter.getNotificationList()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_notification_back -> {
                fragmentManager?.popBackStack()
            }
        }
    }

    override fun getData(data: NotificationModel) {
        notificationDataListener.getNotificationData(data)
    }

    override fun showNotificationList(list: List<NotificationModel>) {
        notificationAdapter.clearListData()
        notificationAdapter.addData(list)
    }


}