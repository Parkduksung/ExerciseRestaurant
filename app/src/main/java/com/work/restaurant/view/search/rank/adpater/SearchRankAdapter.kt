package com.work.restaurant.view.search.rank.adpater

import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.view.adapter.AdapterDataListener

class SearchRankAdapter : RecyclerView.Adapter<SearchRankAdapter.ViewHolder>() {


    private val kakaoList = ArrayList<KakaoSearchModel>()

    private lateinit var adapterListener: AdapterDataListener.GetKakaoData


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
        private val kakaoMoreVert: ImageButton = itemView.findViewById(R.id.ib_more_vert)

        fun bind(item: KakaoSearchModel) {

            val kakaoItem: KakaoSearchModel = item

            if (::adapterListener.isInitialized) {

                kakaoMoreVert.setOnClickListener {

                    val menuBuilder = MenuBuilder(itemView.context)
                    val inflater = MenuInflater(itemView.context)
                    inflater.inflate(R.menu.kakao_item_menu, menuBuilder)

                    if (kakaoItem.phone == "") {
//                        menuBuilder.findItem(R.id.kakao_calling_item).isVisible = false
                    }

                    menuBuilder.findItem(R.id.kakao_location_item).title = kakaoItem.addressName
                    menuBuilder.findItem(R.id.kakao_bookmark_item).title = "즐겨찾기 항목에 추가"
                    val optionMenu =
                        MenuPopupHelper(itemView.context, menuBuilder, kakaoMoreVert)
                    optionMenu.setForceShowIcon(true)
//                    optionMenu.gravity = Gravity.LEFT
                    menuBuilder.setCallback(object : MenuBuilder.Callback {
                        override fun onMenuModeChange(menu: MenuBuilder?) {
                        }

                        override fun onMenuItemSelected(
                            menu: MenuBuilder?,
                            item: MenuItem?
                        ): Boolean {

                            when (item?.itemId) {

                                R.id.kakao_bookmark_item -> {
                                    adapterListener.getKakaoData(2, kakaoItem)
                                }

                            }
                            return true
                        }
                    })

                    optionMenu.show()

                }


                itemView.setOnClickListener {

                    adapterListener.getKakaoData(1, kakaoItem)
                }


            } else {
                adapterListener = object : AdapterDataListener.GetKakaoData {
                    override fun getKakaoData(select: Int, data: KakaoSearchModel) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
                kakaoMoreVert.setOnClickListener {

                    val menuBuilder = MenuBuilder(itemView.context)
                    val inflater = MenuInflater(itemView.context)
                    inflater.inflate(R.menu.kakao_item_menu, menuBuilder)


                    menuBuilder.findItem(R.id.kakao_location_item).title = kakaoItem.addressName
                    menuBuilder.findItem(R.id.kakao_bookmark_item).title = "즐겨찾기 항목에 추가"
                    val optionMenu =
                        MenuPopupHelper(itemView.context, menuBuilder, kakaoMoreVert)
                    optionMenu.setForceShowIcon(true)
//                    optionMenu.gravity = Gravity.LEFT
                    menuBuilder.setCallback(object : MenuBuilder.Callback {
                        override fun onMenuModeChange(menu: MenuBuilder?) {
                        }

                        override fun onMenuItemSelected(
                            menu: MenuBuilder?,
                            item: MenuItem?
                        ): Boolean {

                            when (item?.itemId) {
                                R.id.kakao_bookmark_item -> {
                                    adapterListener.getKakaoData(2, kakaoItem)
                                }

                            }
                            return true
                        }
                    })
                    optionMenu.show()
                }

                itemView.setOnClickListener {

                    adapterListener.getKakaoData(1, kakaoItem)
                }
            }

            kakaoDistance.text = kakaoItem.distance + "M"
            kakaoName.text = kakaoItem.placeName

        }


    }


    fun addData(documents: List<KakaoSearchModel>) =
        kakaoList.addAll(documents)


    fun clearListData() {
        kakaoList.clear()
        notifyDataSetChanged()
    }


    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener.GetKakaoData) {
        adapterListener = listenerAdapterAdapter
    }
}

