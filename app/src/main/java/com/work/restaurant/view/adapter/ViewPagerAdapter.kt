package com.work.restaurant.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


@Suppress("DEPRECATION")
class ViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val fragmentMap: Map<String, Fragment>
) :
    FragmentPagerAdapter(supportFragmentManager) {


    ////    private val fragmentTitleIconList= intArrayOf(R.drawable.ic_home,R.drawable.ic_search,R.drawable.ic_community,R.drawable.ic_mypage)
    //
    //    //    private val fragmentTitleList2 : CharSequence = arrayOf(R.drawable.ic_home,R.drawable.ic_search,R.drawable.ic_community,R.drawable.ic_mypage)
    override fun getItem(position: Int): Fragment =
        fragmentMap.map { it.value }[position]
//        when (position) {
//            0 -> {
//
//
////                SpannableString(fragmentTitleList[position]).setSpan(
////                    ForegroundColorSpan(Color.parseColor("#4180b9")),
////                    0,
////                    fragmentTitleList[position].length,
////                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
////                )
////
////                val changeText = SpannableString(fragmentTitleList[position]).setSpan(
////                    ForegroundColorSpan(Color.parseColor(R.color.colorBlue.toString())),
////                    0,
////                    fragmentTitleList[position].length,
////                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
////                )
////
////                fragmentTitleList.set(0,changeText.toString())
//
//
//                return HomeFragment()
//
//            }
//            1 -> {
//                return SearchFragment()
//            }
//            2 -> {
//                return CommunityFragment()
//            }
//            3 ->{
//                return MyPageFragment()
//            }
//            else -> {
//                return HomeFragment()
//            }

    override fun getCount(): Int =
        fragmentMap.size

    override fun getPageTitle(position: Int): CharSequence =
        fragmentMap.map { it.key }[position]

//    fun getDrawableId(position: Int) : Int{
//        return fragmentTitleIconList[position]
//    }


}

