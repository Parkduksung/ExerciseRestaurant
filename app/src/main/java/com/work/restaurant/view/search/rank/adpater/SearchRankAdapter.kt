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


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val kakaoDistance: TextView = itemView.findViewById(R.id.kakao_distance_tv)
        private val kakaoName: TextView = itemView.findViewById(R.id.kakao_name_tv)
        private val kakaoAddress: TextView = itemView.findViewById(R.id.kakao_address_tv)
        private val kakaoMoreVert: ImageButton = itemView.findViewById(R.id.ib_more_vert)


        fun bind(item: DisplayBookmarkKakaoModel) {

            val kakaoItem: DisplayBookmarkKakaoModel = item

            val menuBuilder = MenuBuilder(itemView.context)
            val inflater = MenuInflater(itemView.context)
            inflater.inflate(R.menu.kakao_item_menu, menuBuilder)
            menuBuilder.findItem(R.id.kakao_location_item).title = kakaoItem.displayAddress


            val optionMenu =
                MenuPopupHelper(itemView.context, menuBuilder, kakaoMoreVert)
            optionMenu.setForceShowIcon(true)

            kakaoMoreVert.setOnClickListener {

                if (kakaoItem.toggleBookmark) {
                    menuBuilder.findItem(R.id.kakao_bookmark_item).title = "즐겨찾기 항목에 제거"
                } else {
                    menuBuilder.findItem(R.id.kakao_bookmark_item).title = "즐겨찾기 항목에 추가"
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
                                                kakaoItem
                                            )
                                            itemView.setBackgroundColor(
                                                ContextCompat.getColor(
                                                    App.instance.context(),
                                                    R.color.colorWhite
                                                )
                                            )
                                        } else {
                                            adapterListener.getDisplayBookmarkKakaoData(
                                                2,
                                                kakaoItem
                                            )
                                            itemView.setBackgroundColor(
                                                ContextCompat.getColor(
                                                    App.instance.context(),
                                                    R.color.colorLeanYellow
                                                )
                                            )
                                        }
                                    } else {
                                        adapterListener.getDisplayBookmarkKakaoData(4, kakaoItem)
                                    }

                                } else {
                                    adapterListener =
                                        object : AdapterDataListener.GetDisplayBookmarkKakaoModel {
                                            override fun getDisplayBookmarkKakaoData(
                                                select: Int,
                                                data: DisplayBookmarkKakaoModel
                                            ) {

                                            }
                                        }
                                    if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                                        if (kakaoItem.toggleBookmark) {
                                            adapterListener.getDisplayBookmarkKakaoData(
                                                3,
                                                kakaoItem
                                            )
                                            itemView.setBackgroundColor(
                                                ContextCompat.getColor(
                                                    App.instance.context(),
                                                    R.color.colorWhite
                                                )
                                            )
                                        } else {
                                            adapterListener.getDisplayBookmarkKakaoData(
                                                2,
                                                kakaoItem
                                            )
                                            itemView.setBackgroundColor(
                                                ContextCompat.getColor(
                                                    App.instance.context(),
                                                    R.color.colorLeanYellow
                                                )
                                            )
                                        }
                                    } else {
                                        adapterListener.getDisplayBookmarkKakaoData(4, kakaoItem)
                                    }
                                }
                            }
                        }
                        return true
                    }
                })
                optionMenu.show()
            }

            if (::adapterListener.isInitialized) {
                itemView.setOnClickListener {
                    adapterListener.getDisplayBookmarkKakaoData(1, kakaoItem)
                }
            } else {
                adapterListener = object : AdapterDataListener.GetDisplayBookmarkKakaoModel {
                    override fun getDisplayBookmarkKakaoData(
                        select: Int,
                        data: DisplayBookmarkKakaoModel
                    ) {

                    }
                }
                itemView.setOnClickListener {
                    adapterListener.getDisplayBookmarkKakaoData(1, kakaoItem)
                }
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

            if (kakaoItem.toggleBookmark && App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        App.instance.context(),
                        R.color.colorLeanYellow
                    )
                )

                menuBuilder.findItem(R.id.kakao_bookmark_item).title = "즐겨찾기 항목에 제거"
                //글자색으로 구분하려면 이걸로
//                kakaoName.setTextColor(
//                    ContextCompat.getColorStateList(
//                        App.instance.context(),
//                        R.color.colorAccent
//                    )
//                )
            } else {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        App.instance.context(),
                        R.color.colorWhite
                    )
                )
                menuBuilder.findItem(R.id.kakao_bookmark_item).title = "즐겨찾기 항목에 추가"
            }

            kakaoName.text = kakaoItem.displayName
            kakaoAddress.text = kakaoItem.displayAddress

        }

    }


    fun addAllData(documents: List<DisplayBookmarkKakaoModel>) {
        kakaoList.addAll(documents)
        notifyItemInserted(kakaoList.lastIndex)
    }


    fun clearListData() {
        kakaoList.clear()
        notifyDataSetChanged()
    }


    fun setItemClickListener(listener: AdapterDataListener.GetDisplayBookmarkKakaoModel) {
        adapterListener = listener
    }

}

