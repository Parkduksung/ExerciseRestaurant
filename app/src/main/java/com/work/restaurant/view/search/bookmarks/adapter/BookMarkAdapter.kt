package com.work.restaurant.view.search.bookmarks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.util.GlideApp
import com.work.restaurant.view.adapter.AdapterDataListener

class BookMarkAdapter : RecyclerView.Adapter<BookMarkAdapter.ViewHolder>() {

    private val bookmarkList = ArrayList<FitnessCenterItemResponse>()

    private lateinit var adapterListener: AdapterDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.bookmark_item,
                parent,
                false
            ),
            adapterListener
        )

    override fun getItemCount(): Int =
        bookmarkList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(bookmarkList[position])
        }
    }


    inner class ViewHolder(itemView: View, private val adapterListener: AdapterDataListener) :
        RecyclerView.ViewHolder(itemView) {

        private val bookmarkName: TextView = itemView.findViewById(R.id.tv_bookmark_name)
        private val bookmarkImage: ImageView = itemView.findViewById(R.id.iv_bookmark_image)
        private val bookmarkCancel: ImageButton = itemView.findViewById(R.id.ib_bookmark_cancel)


        fun bind(item: FitnessCenterItemResponse) {
            val fitnessCenterItemResponse: FitnessCenterItemResponse = item

            itemView.setOnClickListener {
                adapterListener.getData(item.fitnessCenterName)
            }


            bookmarkCancel.setOnClickListener {
                bookmarkList.remove(item)
                notifyDataSetChanged()
            }

            bookmarkName.text = fitnessCenterItemResponse.fitnessCenterName

            GlideApp.with(itemView.context)
                .load(fitnessCenterItemResponse.fitnessCenterImage)
                .override(100, 100)
                .into(bookmarkImage)

        }

    }

    fun addAllData(fitnessCenterItemResponse: List<FitnessCenterItemResponse>) =
        bookmarkList.addAll(fitnessCenterItemResponse)


    fun addData(fitnessCenterItemResponse: FitnessCenterItemResponse) =
        bookmarkList.add(fitnessCenterItemResponse)

    fun clearListData() {
        bookmarkList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }


}

