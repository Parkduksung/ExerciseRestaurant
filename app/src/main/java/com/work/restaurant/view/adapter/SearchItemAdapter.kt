package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.view.GlideApp
import kotlinx.android.synthetic.main.search_look_item.view.*

class SearchItemAdapter : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {

    private val searchLookList = ArrayList<FitnessCenterItemModel>()

    private var searchItemAdapterDataListener: SearchItemAdapterDataListener? = null

    override fun onCreateViewHolder(holder: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(holder.context).inflate(
                R.layout.search_look_item,
                holder,
                false
            )
        )


    override fun getItemCount(): Int =
        searchLookList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.searchLookName.setOnClickListener {

            searchItemAdapterDataListener?.getSearchItemData(searchLookList[position].fitnessCenterName)

        }

        val fitnessCenterItemModel: FitnessCenterItemModel = searchLookList[position]

        holder.run {
            searchLookName.text = fitnessCenterItemModel.fitnessCenterName

            GlideApp.with(holder.itemView.context)
                .load(fitnessCenterItemModel.fitnessCenterImage)
                .override(100, 100)
                .into(holder.searchLookImage)

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val searchLookName: TextView = itemView.tv_search_look_name
        val searchLookImage: ImageView = itemView.iv_search_look_image

    }

    fun addAllData(fitnessCenterItemModel: List<FitnessCenterItemModel>) =
        searchLookList.addAll(fitnessCenterItemModel)


    fun addData(fitnessCenterItemModel: FitnessCenterItemModel) {
        searchLookList.add(fitnessCenterItemModel)
    }

    fun clearListData() {
        searchLookList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listenerAdapter: SearchItemAdapterDataListener) {
        searchItemAdapterDataListener = listenerAdapter
    }

}