package com.work.restaurant.view.search.lookfor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.view.adapter.AdapterDataListener

class LookForAdapter : RecyclerView.Adapter<LookForAdapter.ViewHolder>() {

    private val searchLookList = mutableListOf<KakaoSearchModel>()

    private lateinit var adapterListener: AdapterDataListener
    private lateinit var bookmarkListener: AdapterDataListener.GetKakaoData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_look_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        searchLookList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(searchLookList[position])
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val searchLookName: TextView = itemView.findViewById(R.id.tv_search_look_name)
        private val searchBookmarkCheckbox: CheckBox =
            itemView.findViewById(R.id.cb_search_add_bookmark)

        fun bind(item: KakaoSearchModel) {

            searchLookName.text = item.placeName

            searchBookmarkCheckbox.setButtonDrawable(R.drawable.selector_checkbox_drawable)

            if (::adapterListener.isInitialized && ::bookmarkListener.isInitialized) {
                searchLookName.setOnClickListener {
                    adapterListener.getData(item.placeUrl)
                }

                searchBookmarkCheckbox.setOnCheckedChangeListener { _, _ ->
                    if (searchBookmarkCheckbox.isChecked) {
                        bookmarkListener.getKakaoData(1, item)
                    } else {
                        bookmarkListener.getKakaoData(2, item)
                    }
                }

            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {

                    }
                }

                bookmarkListener = object : AdapterDataListener.GetKakaoData {
                    override fun getKakaoData(select: Int, data: KakaoSearchModel) {

                    }

                }
                searchLookName.setOnClickListener {
                    adapterListener.getData(item.placeName)
                }

                searchBookmarkCheckbox.setOnCheckedChangeListener { _, _ ->
                    if (searchBookmarkCheckbox.isChecked) {
                        bookmarkListener.getKakaoData(1, item)
                    } else {
                        bookmarkListener.getKakaoData(2, item)
                    }
                }

            }


        }
    }

    fun addAllData(kakaoSearchModelList: List<KakaoSearchModel>) =
        searchLookList.addAll(kakaoSearchModelList)


    fun addData(kakaoSearchModel: KakaoSearchModel) {
        searchLookList.add(kakaoSearchModel)
    }

    fun clearListData() {
        searchLookList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: AdapterDataListener) {
        adapterListener = itemClickListener
    }

    fun setBookmarkListener(listener: AdapterDataListener.GetKakaoData) {
        bookmarkListener = listener
    }

}