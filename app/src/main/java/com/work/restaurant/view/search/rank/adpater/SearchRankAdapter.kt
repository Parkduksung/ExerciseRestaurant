package com.work.restaurant.view.search.rank.adpater

import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.core.content.ContextCompat
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

    fun stateChange(position: Int) {
        kakaoList[position].toggleBookmark = !kakaoList[position].toggleBookmark
        notifyItemChanged(position)
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val kakaoDistance: TextView = itemView.findViewById(R.id.kakao_distance_tv)
        private val kakaoName: TextView = itemView.findViewById(R.id.kakao_name_tv)
        private val kakaoAddress: TextView = itemView.findViewById(R.id.kakao_address_tv)
        private val kakaoMoreVert: ImageButton = itemView.findViewById(R.id.ib_more_vert)

        fun bind(item: DisplayBookmarkKakaoModel) {

            val kakaoItem: DisplayBookmarkKakaoModel = item

            kakaoMoreVert.setOnClickListener {
                val menuBuilder = MenuBuilder(itemView.context)
                val inflater = MenuInflater(itemView.context)
                inflater.inflate(R.menu.kakao_item_menu, menuBuilder)
                menuBuilder.findItem(R.id.kakao_location_item).title = kakaoItem.displayAddress

                val optionMenu =
                    MenuPopupHelper(itemView.context, menuBuilder, kakaoMoreVert)
                optionMenu.setForceShowIcon(true)

                if (kakaoItem.toggleBookmark) {
                    menuBuilder.findItem(R.id.kakao_bookmark_item).title =
                        App.instance.getString(R.string.bookmark_content_delete)
                } else {
                    menuBuilder.findItem(R.id.kakao_bookmark_item).title =
                        App.instance.getString(R.string.bookmark_content_add)
                }

                menuBuilder.setCallback(object : MenuBuilder.Callback {
                    override fun onMenuModeChange(menu: MenuBuilder?) {
                    }

                    override fun onMenuItemSelected(
                        menu: MenuBuilder?,
                        item: MenuItem?
                    ): Boolean {
                        when (item?.itemId) {
                            R.id.kakao_bookmark_item -> {
                                if (::adapterListener.isInitialized) {
                                    if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                                        if (kakaoItem.toggleBookmark) {
                                            adapterListener.getDisplayBookmarkKakaoData(
                                                3,
                                                kakaoItem,
                                                adapterPosition
                                            )

                                        } else {
                                            adapterListener.getDisplayBookmarkKakaoData(
                                                2,
                                                kakaoItem,
                                                adapterPosition
                                            )
                                        }
                                    } else {
                                        adapterListener.getDisplayBookmarkKakaoData(4, kakaoItem, 0)
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
                                        if (kakaoItem.toggleBookmark) {
                                            adapterListener.getDisplayBookmarkKakaoData(
                                                3,
                                                kakaoItem,
                                                adapterPosition
                                            )

                                        } else {
                                            adapterListener.getDisplayBookmarkKakaoData(
                                                2,
                                                kakaoItem,
                                                adapterPosition
                                            )
                                        }
                                    } else {
                                        adapterListener.getDisplayBookmarkKakaoData(4, kakaoItem, 0)
                                    }
                                }
                            }
                        }
                        return true
                    }
                })
                optionMenu.show()
            }


            if (kakaoItem.distance.toInt() >= 1000) {
                val convertKm = kakaoItem.distance.toInt() / 1000
                val convertM = (kakaoItem.distance.toInt() - (convertKm * 1000))

                if (convertM / 100 > 0) {
                    val decimalPoint = ((kakaoItem.distance.toInt() % 1000).toString())[0]
                    kakaoDistance.text = "$convertKm" + "." + "${decimalPoint}Km"
                } else {
                    kakaoDistance.text = "$convertKm" + ".0Km"
                }
            } else {
                kakaoDistance.text = kakaoItem.distance + "M"
            }

            if (item.toggleBookmark) {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        App.instance.context(),
                        R.color.colorLeanYellow
                    )
                )
            } else {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        App.instance.context(),
                        R.color.colorWhite
                    )
                )
            }

            kakaoName.text = kakaoItem.displayName
            kakaoAddress.text = kakaoItem.displayAddress

        }
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

