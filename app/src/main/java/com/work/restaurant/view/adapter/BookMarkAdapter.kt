package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.GlideApp
import kotlinx.android.synthetic.main.bookmark_item.view.*

class BookMarkAdapter : RecyclerView.Adapter<BookMarkAdapter.ViewHolder>() {

    private val bookmarkList = ArrayList<FitnessCenterItemResponse>()


    private var adapterListener: AdapterDataListener? = null


    override fun onCreateViewHolder(holder: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(holder.context).inflate(
                R.layout.bookmark_item,
                holder,
                false
            )
        )


    override fun getItemCount(): Int =
        bookmarkList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.bookmarkName.setOnClickListener {
            val context = it.context
            Toast.makeText(context, bookmarkList[position].fitnessCenterName, Toast.LENGTH_LONG)
                .show()

            adapterListener?.getData(bookmarkList[position].fitnessCenterName)

        }


        holder.bookmarkCancel.setOnClickListener {
            bookmarkList.removeAt(position)
            notifyDataSetChanged()

        }


        val fitnessCenterItemResponse: FitnessCenterItemResponse = bookmarkList[position]

        holder.run {
            bookmarkName.text = fitnessCenterItemResponse.fitnessCenterName

            GlideApp.with(holder.itemView.context)
                .load(fitnessCenterItemResponse.fitnessCenterImage)
                .override(100, 100)
                .into(holder.bookmarkImage)

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val bookmarkName: TextView = itemView.tv_bookmark_name
        val bookmarkImage: ImageView = itemView.iv_bookmark_image
        val bookmarkCancel: ImageButton = itemView.ib_bookmark_cancel

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