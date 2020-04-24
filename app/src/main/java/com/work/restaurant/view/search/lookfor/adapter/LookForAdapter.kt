package com.work.restaurant.view.search.lookfor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.util.RelateLogin
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
        private val searchLookLayout: LinearLayout = itemView.findViewById(R.id.ll_search_look_item)
        private val searchLookImage: ImageView = itemView.findViewById(R.id.iv_search_look_image)
        private val searchBookmarkCheckbox: CheckBox = itemView.findViewById(R.id.cb_search_add_bookmark)
        private val searchLookAddress: TextView = itemView.findViewById(R.id.tv_search_look_address)


        fun bind(item: DisplayBookmarkKakaoModel) {

            searchLookName.text = item.displayName
            searchLookAddress.text = item.displayAddress
            searchBookmarkCheckbox.setButtonDrawable(R.drawable.selector_checkbox_bookmark1)
            searchBookmarkCheckbox.isChecked = item.toggleBookmark


            if (::adapterListener.isInitialized && ::bookmarkListener.isInitialized) {

                searchLookName.setOnClickListener {
                    adapterListener.getData(item.displayUrl)
                }

                searchLookLayout.setOnClickListener {
                    adapterListener.getData(item.displayUrl)
                }

                searchLookImage.setOnClickListener {
                    adapterListener.getData(item.displayUrl)
                }

                searchBookmarkCheckbox.setOnClickListener {
                    if (RelateLogin.loginState()) {
                        if (searchBookmarkCheckbox.isChecked) {
                            bookmarkListener.getDisplayBookmarkKakaoData(
                                ADD_BOOKMARK,
                                item,
                                adapterPosition
                            )
                        } else {
                            bookmarkListener.getDisplayBookmarkKakaoData(
                                DELETE_BOOKMARK,
                                item,
                                adapterPosition
                            )
                        }
                    } else {
                        searchBookmarkCheckbox.isChecked = false
                        bookmarkListener.getDisplayBookmarkKakaoData(
                            NOT_LOGIN_STATE,
                            item,
                            NOT_SELECT
                        )
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
                        data: DisplayBookmarkKakaoModel,
                        selectPosition: Int
                    ) {

                    }
                }

                searchLookName.setOnClickListener {
                    adapterListener.getData(item.displayUrl)
                }
                searchLookLayout.setOnClickListener {
                    adapterListener.getData(item.displayUrl)
                }

                searchLookImage.setOnClickListener {
                    adapterListener.getData(item.displayUrl)
                }


                searchBookmarkCheckbox.setOnClickListener {

                    if (RelateLogin.loginState()) {
                        if (searchBookmarkCheckbox.isChecked) {
                            bookmarkListener.getDisplayBookmarkKakaoData(
                                ADD_BOOKMARK,
                                item,
                                adapterPosition
                            )
                        } else {
                            bookmarkListener.getDisplayBookmarkKakaoData(
                                DELETE_BOOKMARK,
                                item,
                                adapterPosition
                            )
                        }
                    } else {
                        searchBookmarkCheckbox.isChecked = false
                        bookmarkListener.getDisplayBookmarkKakaoData(
                            NOT_LOGIN_STATE,
                            item,
                            NOT_SELECT
                        )
                    }
                }

            }

        }
    }

    fun addAllData(searchList: List<DisplayBookmarkKakaoModel>) =
        searchLookList.addAll(searchList)

    fun clearListData() {
        searchLookList.clear()
        notifyDataSetChanged()
    }

    fun stateChange(position: Int) {
        searchLookList[position].toggleBookmark =
            !searchLookList[position].toggleBookmark
        notifyItemChanged(position)
    }

    fun setItemClickListener(itemClickListener: AdapterDataListener) {
        adapterListener = itemClickListener
    }


    fun setBookmarkListener(listener: AdapterDataListener.GetDisplayBookmarkKakaoModel) {
        bookmarkListener = listener
    }


    companion object {

        private const val NOT_SELECT = 0

        const val ADD_BOOKMARK = 1
        const val DELETE_BOOKMARK = 2
        const val NOT_LOGIN_STATE = 3

    }
}