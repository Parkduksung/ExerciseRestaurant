package com.work.restaurant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.view.fragment.search.RankItem
import kotlinx.android.synthetic.main.rank_list.view.*

class SearchLookAdapter : RecyclerView.Adapter<SearchLookAdapter.ViewHolder>() {

    private var rankItemtList = mutableListOf<RankItem>()

    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(holder.context).inflate(R.layout.rank_list, holder, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rankItem: RankItem = rankItemtList[position]

        holder.run {
            rankName.text = rankItem.rankName
            rankNumber.text = rankItem.rankNumber
            rankImage.setImageBitmap(rankItem.rankImage)
        }

    }

    override fun getItemCount(): Int =
        rankItemtList.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val rankName = itemView.rank_name_tv
        val rankNumber = itemView.rank_number_tv
        val rankImage = itemView.rank_image_iv

    }

    fun addData(product: List<RankItem>) =
        rankItemtList.addAll(product)

    fun clearListData() {
        rankItemtList.clear()
        notifyDataSetChanged()
    }


}
