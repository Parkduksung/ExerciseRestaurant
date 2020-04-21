package com.work.restaurant.view.diary.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.view.adapter.AdapterDataListener

class DiaryMainAdapter :
    RecyclerView.Adapter<DiaryMainAdapter.ViewHolder>() {

    private val diaryDetailsAdapter: DiaryDetailsAdapter by lazy { DiaryDetailsAdapter() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.diary_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind()


    override fun getItemCount(): Int =
        Int.MAX_VALUE


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val diaryDetailsRecyclerView =
            itemView.findViewById<RecyclerView>(R.id.rv_diary_details)

        fun bind() {
            diaryDetailsRecyclerView.run {
                this.adapter = diaryDetailsAdapter
                layoutManager = LinearLayoutManager(this.context)
                setHasFixedSize(true)
            }

        }
    }

    fun addDetailsData(diaryModel: List<DiaryModel>) {
        diaryDetailsAdapter.clearListData()
        diaryDetailsAdapter.addAllData(diaryModel)
    }


    fun clearListData() =
        diaryDetailsAdapter.clearListData()


    fun setItemClickListener(listener: AdapterDataListener.GetList) {
        diaryDetailsAdapter.setItemClickListener(listener)
    }


}

