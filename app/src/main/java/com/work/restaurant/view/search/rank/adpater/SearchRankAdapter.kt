package com.work.restaurant.view.search.rank.adpater

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

class SearchRankAdapter : RecyclerView.Adapter<SearchRankAdapter.ViewHolder>() {


    private val kakaoList = mutableListOf<DisplayBookmarkKakaoModel>()

    private lateinit var adapterListener: AdapterDataListener.GetDisplayBookmarkKakaoModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fitness_rank_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        kakaoList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(kakaoList[position])
        }
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val kakaoDistance: TextView = itemView.findViewById(R.id.kakao_distance_tv)
        private val kakaoName: TextView = itemView.findViewById(R.id.kakao_name_tv)
        private val kakaoAddress: TextView = itemView.findViewById(R.id.kakao_address_tv)
        private val kakaoBookmark: CheckBox = itemView.findViewById(R.id.cb_bookmark)

        fun bind(item: DisplayBookmarkKakaoModel) {

            val kakaoItem: DisplayBookmarkKakaoModel = item


            kakaoBookmark.setButtonDrawable(R.drawable.selector_checkbox_bookmark1)

            kakaoBookmark.isChecked = kakaoItem.toggleBookmark

            itemView.setOnClickListener {
                if (::adapterListener.isInitialized) {
                    adapterListener.getDisplayBookmarkKakaoData(1, kakaoItem, 0)
                } else {
                    adapterListener =
                        object : AdapterDataListener.GetDisplayBookmarkKakaoModel {
                            override fun getDisplayBookmarkKakaoData(
                                select: Int,
                                data: DisplayBookmarkKakaoModel,
                                selectPosition: Int
                            ) {
                            }
                        }
                    adapterListener.getDisplayBookmarkKakaoData(1, kakaoItem, 0)
                }
            }


            kakaoBookmark.setOnClickListener {
                if (::adapterListener.isInitialized) {
                    if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                        if (kakaoBookmark.isChecked) {
                            adapterListener.getDisplayBookmarkKakaoData(
                                2,
                                kakaoItem,
                                adapterPosition
                            )
                        } else {
                            adapterListener.getDisplayBookmarkKakaoData(
                                3,
                                kakaoItem,
                                adapterPosition
                            )
                        }
                    } else {
                        kakaoBookmark.isChecked = false
                        adapterListener.getDisplayBookmarkKakaoData(4, item, 0)
                    }
                } else {
                    adapterListener =
                        object : AdapterDataListener.GetDisplayBookmarkKakaoModel {
                            override fun getDisplayBookmarkKakaoData(
                                select: Int,
                                data: DisplayBookmarkKakaoModel,
                                selectPosition: Int
                            ) {

                            }
                        }
                    if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                        if (kakaoBookmark.isChecked) {
                            adapterListener.getDisplayBookmarkKakaoData(
                                2,
                                kakaoItem,
                                adapterPosition
                            )
                        } else {
                            adapterListener.getDisplayBookmarkKakaoData(
                                3,
                                kakaoItem,
                                adapterPosition
                            )
                        }
                    } else {
                        kakaoBookmark.isChecked = false
                        adapterListener.getDisplayBookmarkKakaoData(4, item, 0)
                    }

                }
            }


            if (kakaoItem.distance.toInt() >= 1000) {
                val convertKm = kakaoItem.distance.toInt() / 1000
                val convertM = (kakaoItem.distance.toInt() - (convertKm * 1000))

                if (convertM / 100 > 0) {
                    val decimalPoint = ((kakaoItem.distance.toInt() % 1000).toString())[0]
                    kakaoDistance.text = "$convertKm" + "." + "${decimalPoint}km"
                } else {
                    kakaoDistance.text = "$convertKm" + ".0km"
                }
            } else {
                kakaoDistance.text = kakaoItem.distance + "m"
            }

            kakaoName.text = kakaoItem.displayName
            kakaoAddress.text = kakaoItem.displayAddress

        }
    }

    fun stateChange(position: Int) {
        kakaoList[position].toggleBookmark = !kakaoList[position].toggleBookmark
        notifyItemChanged(position)
    }


    fun addAllData(documents: List<DisplayBookmarkKakaoModel>) {
        kakaoList.addAll(documents)
        notifyDataSetChanged()
    }

    fun clearListData() {
        kakaoList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: AdapterDataListener.GetDisplayBookmarkKakaoModel) {
        adapterListener = listener
    }

}

