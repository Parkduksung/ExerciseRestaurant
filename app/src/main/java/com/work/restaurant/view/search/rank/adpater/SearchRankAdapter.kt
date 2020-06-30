package com.work.restaurant.view.search.rank.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.util.RelateLogin
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
                    adapterListener.getDisplayBookmarkKakaoData(SELECT_URL, kakaoItem, NOT_SELECT)
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
                    adapterListener.getDisplayBookmarkKakaoData(SELECT_URL, kakaoItem, NOT_SELECT)
                }
            }


            kakaoBookmark.setOnClickListener {
                if (::adapterListener.isInitialized) {
                    if (RelateLogin.loginState()) {
                        if (kakaoBookmark.isChecked) {
                            adapterListener.getDisplayBookmarkKakaoData(
                                ADD_BOOKMARK,
                                kakaoItem,
                                adapterPosition
                            )
                        } else {
                            adapterListener.getDisplayBookmarkKakaoData(
                                DELETE_BOOKMARK,
                                kakaoItem,
                                adapterPosition
                            )
                        }
                    } else {
                        kakaoBookmark.isChecked = false
                        adapterListener.getDisplayBookmarkKakaoData(
                            NOT_LOGIN_STATE,
                            item,
                            NOT_SELECT
                        )
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
                    if (RelateLogin.loginState()) {
                        if (kakaoBookmark.isChecked) {
                            adapterListener.getDisplayBookmarkKakaoData(
                                ADD_BOOKMARK,
                                kakaoItem,
                                adapterPosition
                            )
                        } else {
                            adapterListener.getDisplayBookmarkKakaoData(
                                DELETE_BOOKMARK,
                                kakaoItem,
                                adapterPosition
                            )
                        }
                    } else {
                        kakaoBookmark.isChecked = false
                        adapterListener.getDisplayBookmarkKakaoData(
                            NOT_LOGIN_STATE,
                            item,
                            NOT_SELECT
                        )
                    }

                }
            }


            kakaoDistance.text = convertDistance(kakaoItem.distance)

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


    private fun convertDistance(distance: String): String {

        return if (distance.toInt() >= 1000) {
            val convertKm = distance.toInt() / 1000
            val convertM = (distance.toInt() - (convertKm * 1000))

            if (convertM / 100 > 0) {
                val decimalPoint = ((distance.toInt() % 1000).toString())[0]
                "$convertKm" + "." + "${decimalPoint}km"
            } else {
                "$convertKm" + ".0km"
            }
        } else {
            distance + "m"
        }

    }


    companion object {

        private const val NOT_SELECT = 0

        const val SELECT_URL = 0
        const val ADD_BOOKMARK = 1
        const val DELETE_BOOKMARK = 2
        const val NOT_LOGIN_STATE = 3

    }
}

