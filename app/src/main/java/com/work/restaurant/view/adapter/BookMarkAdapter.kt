package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.view.GlideApp
import kotlinx.android.synthetic.main.bookmark_item.view.*

class BookMarkAdapter : RecyclerView.Adapter<BookMarkAdapter.ViewHolder>() {

    private val bookmarkList = ArrayList<FitnessCenterItemModel>()


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


        holder.bookmarkCancel.setOnClickListener {
            bookmarkList.removeAt(position)
            notifyDataSetChanged()

        }


        val fitnessCenterItemModel: FitnessCenterItemModel = bookmarkList[position]

        holder.run {
            bookmarkName.text = fitnessCenterItemModel.fitnessCenterName

            GlideApp.with(holder.itemView.context)
                .load(fitnessCenterItemModel.fitnessCenterImage)
                .override(100, 100)
                .into(holder.bookmarkImage)



        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val bookmarkName: TextView = itemView.tv_bookmark_name
        val bookmarkImage: ImageView = itemView.iv_bookmark_image
        val bookmarkCancel: ImageButton = itemView.ib_bookmark_cancel

    }

    fun addAllData(fitnessCenterItemModel: List<FitnessCenterItemModel>) =
        bookmarkList.addAll(fitnessCenterItemModel)


    fun addData(fitnessCenterItemModel: FitnessCenterItemModel) =
        bookmarkList.add(fitnessCenterItemModel)

    fun clearListData() {
        bookmarkList.clear()
        notifyDataSetChanged()
    }

}