package com.work.restaurant.view.search.lookfor.presenter

import com.work.restaurant.data.model.KakaoSearchModel

interface SearchLookForContract {
    interface View {


        fun showSearchLook(searchKakaoList: List<KakaoSearchModel>)

        fun showSearchNoFind()

        fun showBackPage()

    }

    interface Presenter {

        fun searchLook(searchItem: String)

        fun backPage()


    }
}