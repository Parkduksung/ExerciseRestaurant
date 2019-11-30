package com.work.restaurant.view

import androidx.fragment.app.Fragment
import com.work.restaurant.view.community.fragment.CommunityFragment
import com.work.restaurant.view.home.fragment.HomeFragment
import com.work.restaurant.view.mypage.fragment.MyPageFragment
import com.work.restaurant.view.search.fragment.SearchFragment

class ExerciseRestaurantPresenter(private val exerciseRestaurantView: ExerciseRestaurantContract.View) :
    ExerciseRestaurantContract.Presenter {
    override fun loading() {
        exerciseRestaurantView.showLoading()
    }

    override fun init() {
        exerciseRestaurantView.showInit()
    }


    override fun setFragmentMap(): Map<String, Fragment> =
        mapOf(
            "홈" to HomeFragment(),
            "헬스장검색" to SearchFragment(),
            "커뮤니티" to CommunityFragment(),
            "마이페이지" to MyPageFragment()
        )

}



