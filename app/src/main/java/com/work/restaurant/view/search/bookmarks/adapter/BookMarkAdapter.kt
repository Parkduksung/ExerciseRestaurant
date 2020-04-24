package com.work.restaurant.view.search.bookmarks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.BookmarkModel
import com.work.restaurant.view.adapter.AdapterDataListener

class BookMarkAdapter : RecyclerView.Adapter<BookMarkAdapter.ViewHolder>() {

    private val bookmarkList = mutableListOf<BookmarkModel>()

    private lateinit var adapterListener: AdapterDataListener.GetBookmarkData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.bookmark_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        bookmarkList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(bookmarkList[position])
        }
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val bookmarkName: TextView = itemView.findViewById(R.id.tv_bookmark_name)
        private val bookmarkAddress: TextView = itemView.findViewById(R.id.tv_bookmark_address)
        private val bookmarkCancel: ImageButton = itemView.findViewById(R.id.ib_bookmark_cancel)

        fun bind(item: BookmarkModel) {

            if (::adapterListener.isInitialized) {
                itemView.setOnClickListener {
                    adapterListener.getBookmarkData(SELECT_URL, item)
                }
                bookmarkCancel.setOnClickListener {
                    adapterListener.getBookmarkData(DELETE_BOOKMARK, item)
                    bookmarkList.remove(item)
                    notifyDataSetChanged()
                }

            } else {
                adapterListener = object : AdapterDataListener.GetBookmarkData {
                    override fun getBookmarkData(select: Int, data: BookmarkModel) {

                    }

                }
                itemView.setOnClickListener {
                    adapterListener.getBookmarkData(SELECT_URL, item)
                }
                bookmarkCancel.setOnClickListener {
                    adapterListener.getBookmarkData(DELETE_BOOKMARK, item)
                    bookmarkList.remove(item)
                    notifyDataSetChanged()
                }
            }

            bookmarkAddress.text = item.bookmarkAddress
            bookmarkName.text = item.bookmarkName
        }
    }

    fun addAllData(bookmarkModelList: List<BookmarkModel>) =
        bookmarkList.addAll(bookmarkModelList)

    fun clearListData() {
        bookmarkList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: AdapterDataListener.GetBookmarkData) {
        adapterListener = listener
    }

    companion object {

        const val SELECT_URL = 1
        const val DELETE_BOOKMARK = 2

    }

}

