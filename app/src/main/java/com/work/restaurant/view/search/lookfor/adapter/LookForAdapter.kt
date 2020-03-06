package com.work.restaurant.view.search.lookfor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.util.App
import com.work.restaurant.view.adapter.AdapterDataListener

class LookForAdapter : RecyclerView.Adapter<LookForAdapter.ViewHolder>() {

    private val searchLookList = mutableListOf<DisplayBookmarkKakaoModel>()

    private lateinit var adapterListener: AdapterDataListener
    private lateinit var bookmarkListener: AdapterDataListener.GetDisplayBookmarkKakaoModel

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
        private val searchLookAddress: TextView = itemView.findViewById(R.id.tv_search_look_address)


        fun bind(item: DisplayBookmarkKakaoModel) {

            searchLookName.text = item.displayName
            searchLookAddress.text = item.displayAddress
            searchBookmarkCheckbox.setButtonDrawable(R.drawable.selector_checkbox_drawable)

            if (item.toggleBookmark) {
                searchBookmarkCheckbox.isChecked = true
            }

            if (::adapterListener.isInitialized && ::bookmarkListener.isInitialized) {

                searchLookName.setOnClickListener {
                    adapterListener.getData(item.displayUrl)
                }


                searchBookmarkCheckbox.setOnCheckedChangeListener { _, _ ->
                    if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                        if (searchBookmarkCheckbox.isChecked) {
                            bookmarkListener.getDisplayBookmarkKakaoData(1, item)
                        } else {
                            bookmarkListener.getDisplayBookmarkKakaoData(2, item)
                        }
                    } else {
                        searchBookmarkCheckbox.isChecked = false
                        bookmarkListener.getDisplayBookmarkKakaoData(3, item)
                    }
                }

            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {

                    }
                }

                bookmarkListener = object : AdapterDataListener.GetDisplayBookmarkKakaoModel {
                    override fun getDisplayBookmarkKakaoData(
                        select: Int,
                        data: DisplayBookmarkKakaoModel
                    ) {

                    }
                }

                searchLookName.setOnClickListener {
                    adapterListener.getData(item.displayUrl)
                }


                searchBookmarkCheckbox.setOnCheckedChangeListener { _, _ ->

                    if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                        if (searchBookmarkCheckbox.isChecked) {
                            bookmarkListener.getDisplayBookmarkKakaoData(1, item)
                        } else {
                            bookmarkListener.getDisplayBookmarkKakaoData(2, item)
                        }
                    } else {
                        searchBookmarkCheckbox.isChecked = false
                        bookmarkListener.getDisplayBookmarkKakaoData(3, item)
                    }
                }

            }

        }
    }

    fun addAllData(searchList: List<DisplayBookmarkKakaoModel>) =
        searchLookList.addAll(searchList)


    fun addData(searchList: DisplayBookmarkKakaoModel) {
        searchLookList.add(searchList)
    }

    fun clearListData() {
        searchLookList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: AdapterDataListener) {
        adapterListener = itemClickListener
    }


    fun setBookmarkListener(listener: AdapterDataListener.GetDisplayBookmarkKakaoModel) {
        bookmarkListener = listener
    }

}