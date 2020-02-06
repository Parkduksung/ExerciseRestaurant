package com.work.restaurant.view.search.lookfor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.util.App
import com.work.restaurant.view.adapter.AdapterDataListener

class LookForAdapter : RecyclerView.Adapter<LookForAdapter.ViewHolder>() {

    private val searchLookList = ArrayList<KakaoSearchModel>()

    private lateinit var adapterListener: AdapterDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_look_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        searchLookList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::adapterListener.isInitialized) {
            holder.bind(searchLookList[position])
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val searchLookName: TextView = itemView.findViewById(R.id.tv_search_look_name)
        private val searchBookmarkCheckbox: CheckBox =
            itemView.findViewById(R.id.cb_search_add_bookmark)

        fun bind(item: KakaoSearchModel) {

            searchLookName.text = item.placeName

            searchBookmarkCheckbox.setButtonDrawable(R.drawable.selector_checkbox_drawable)

            if (::adapterListener.isInitialized) {
                searchLookName.setOnClickListener {
                    adapterListener.getData(item.placeName)
                }

                searchBookmarkCheckbox.setOnCheckedChangeListener { _, _ ->
                    if (searchBookmarkCheckbox.isChecked) {
                        Toast.makeText(App.instance.context(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        Toast.makeText(App.instance.context(), "즐겨찾기에 제거되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            } else {
                adapterListener = object : AdapterDataListener {
                    override fun getData(data: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
                searchLookName.setOnClickListener {
                    adapterListener.getData(item.placeName)
                }

                searchBookmarkCheckbox.setOnCheckedChangeListener { _, _ ->
                    if (searchBookmarkCheckbox.isChecked) {
                        Toast.makeText(App.instance.context(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        Toast.makeText(App.instance.context(), "즐겨찾기에 제거되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            }


        }
    }

    fun addAllData(kakaoSearchModelList: List<KakaoSearchModel>) =
        searchLookList.addAll(kakaoSearchModelList)


    fun addData(kakaoSearchModel: KakaoSearchModel) {
        searchLookList.add(kakaoSearchModel)
    }

    fun clearListData() {
        searchLookList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(listenerAdapterAdapter: AdapterDataListener) {
        adapterListener = listenerAdapterAdapter
    }

}