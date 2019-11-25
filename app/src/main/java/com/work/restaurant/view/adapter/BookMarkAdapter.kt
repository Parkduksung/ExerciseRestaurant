package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.FitnessCenterItem
import com.work.restaurant.view.GlideApp
import kotlinx.android.synthetic.main.bookmark_item.view.*

class BookMarkAdapter : RecyclerView.Adapter<BookMarkAdapter.ViewHolder>() {

    private val bookmarkArr = ArrayList<FitnessCenterItem>()


    override fun onCreateViewHolder(holder: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(holder.context).inflate(
                R.layout.bookmark_item,
                holder,
                false
            )
        )


    override fun getItemCount(): Int =
        bookmarkArr.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.bookmarkCancel.setOnClickListener {
            bookmarkArr.removeAt(position)
            notifyDataSetChanged()
        }


        val fitnessCenterItem: FitnessCenterItem = bookmarkArr[position]

        holder.run {
            bookmarkName.text = fitnessCenterItem.fitnessCenterName

            GlideApp.with(holder.itemView.context)
                .load(fitnessCenterItem.fitnessCenterImage)
                .override(100, 100)
                .into(holder.bookmarkImage)

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val bookmarkName: TextView = itemView.tv_bookmark_name
        val bookmarkImage: ImageView = itemView.iv_bookmark_image
        val bookmarkCancel: ImageButton = itemView.ib_bookmark_cancel

    }

    fun addAllData(fitnessCenterItem: List<FitnessCenterItem>) =
        bookmarkArr.addAll(fitnessCenterItem)


    fun addData(fitnessCenterItem: FitnessCenterItem) =
        bookmarkArr.add(fitnessCenterItem)

    fun clearListData() {
        bookmarkArr.clear()
        notifyDataSetChanged()
    }

}