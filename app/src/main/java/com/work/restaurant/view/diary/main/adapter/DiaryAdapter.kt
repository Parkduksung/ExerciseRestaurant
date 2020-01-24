package com.work.restaurant.view.diary.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.DateModel
import com.work.restaurant.view.adapter.AdapterDataListener

class DiaryAdapter : RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    private lateinit var adapterListener: AdapterDataListener
    private val addEatList = ArrayList<DateModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.diary_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(addEatList[position])
        }
    }

    override fun getItemCount(): Int =
        addEatList.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val type = listOf("식사", "간식")

        private val addType: TextView = itemView.findViewById(R.id.add_type)
        private val addTime: TextView = itemView.findViewById(R.id.add_time)
        private val addMemo: TextView = itemView.findViewById(R.id.add_memo)

        fun bind(item: DateModel) {

            if (::adapterListener.isInitialized) {
                itemView.setOnClickListener {
                    adapterListener.getData(item.memo)
                }
            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
                itemView.setOnClickListener {
                    adapterListener.getData(item.memo)
                }
            }

            val dateModel: DateModel = item

            addType.text = type[dateModel.type]
            addTime.text = dateModel.time
            addMemo.text = dateModel.memo

        }
    }


    fun addAllData(dateModelList: List<DateModel>) =
        addEatList.addAll(dateModelList)

    fun addData(dateModel: DateModel) {
        addEatList.add(dateModel)
        notifyDataSetChanged()
    }

    fun clearListData() {
        addEatList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }

}

