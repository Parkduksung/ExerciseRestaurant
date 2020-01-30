package com.work.restaurant.view.search.itemdetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.network.model.kakaoImage.KakaoImageDocuments
import com.work.restaurant.util.GlideApp

class KakaoImageAdapter : RecyclerView.Adapter<KakaoImageAdapter.ViewHolder>() {

    private val kakaoList = ArrayList<KakaoImageDocuments>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.scroll_image_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        kakaoList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(kakaoList[position])


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val kakaoImage: ImageView = itemView.findViewById(R.id.iv_scroll_image)

        fun bind(item: KakaoImageDocuments) {

            GlideApp.with(itemView.context)
                .asBitmap()
                .load(item.imageUrl)
                .into(kakaoImage)
        }

    }

    fun addData(documents: List<KakaoImageDocuments>) =
        kakaoList.addAll(documents)


    fun clearListData() {
        kakaoList.clear()
        notifyDataSetChanged()
    }


}

