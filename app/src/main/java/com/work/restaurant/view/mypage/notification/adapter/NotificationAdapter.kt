package com.work.restaurant.view.mypage.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.NotificationModel
import com.work.restaurant.view.adapter.AdapterDataListener

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    private val notificationList = mutableListOf<NotificationModel>()

    private lateinit var adapterListener: AdapterDataListener.GetNotificationList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notification_item,
                parent,
                false
            )
        )


    override fun getItemCount(): Int =
        notificationList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(notificationList[position])
        }
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val notificationSubject: TextView =
            itemView.findViewById(R.id.tv_notification_subject)
        private val notificationDate: TextView = itemView.findViewById(R.id.tv_notification_date)

        fun bind(item: NotificationModel) {

            if (::adapterListener.isInitialized) {

                itemView.setOnClickListener {

                    adapterListener.getData(item)
                }

            } else {
                adapterListener = object : AdapterDataListener.GetNotificationList {
                    override fun getData(data: NotificationModel) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }

                itemView.setOnClickListener {

                    adapterListener.getData(item)
                }
            }

            notificationSubject.text = item.notificationSubject
            notificationDate.text = item.notificationDate

        }


    }


    fun addData(listt: List<NotificationModel>) =
        notificationList.addAll(listt)

    fun clearListData() {
        notificationList.clear()
        notifyDataSetChanged()
    }


    fun setItemClickListener(listener: AdapterDataListener.GetNotificationList) {
        adapterListener = listener
    }
}

