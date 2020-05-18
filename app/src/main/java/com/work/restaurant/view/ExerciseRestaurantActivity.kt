package com.work.restaurant.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.data.model.NotificationModel
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.adapter.RenewBookmarkAndRankListener
import com.work.restaurant.view.adapter.ViewPagerAdapter
import com.work.restaurant.view.base.BaseActivity
import com.work.restaurant.view.calendar.CalendarFragment
import com.work.restaurant.view.diary.main.DiaryFragment
import com.work.restaurant.view.home.main.HomeFragment
import com.work.restaurant.view.mypage.main.MyPageFragment
import com.work.restaurant.view.mypage.notification.NotificationDataListener
import com.work.restaurant.view.mypage.notification_detail.MyPageNotificationDetailsFragment
import com.work.restaurant.view.search.bookmarks.SearchBookmarksFragment
import com.work.restaurant.view.search.main.SearchFragment
import com.work.restaurant.view.search.rank.SearchRankFragment
import kotlinx.android.synthetic.main.activity_main.*


class ExerciseRestaurantActivity : BaseActivity(R.layout.activity_main),
    NotificationDataListener,
    DiaryFragment.RenewDataListener,
    RenewBookmarkAndRankListener,
    SearchRankFragment.LoginListener {

    private val mainTabList = resources.getStringArray(R.array.tab_main).toList()

    private val viewPagerAdapter by lazy {
        object : ViewPagerAdapter(
            supportFragmentManager,
            mainTabList
        ) {
            override fun getItem(position: Int): Fragment =
                when (mainTabList[position]) {
                    "홈" -> {
                        HomeFragment()
                    }
                    "헬스장검색" -> {
                        SearchFragment()
                    }
                    "다이어리" -> {
                        DiaryFragment()
                    }
                    "켈린더" -> {
                        CalendarFragment()
                    }
                    "마이페이지" -> {
                        MyPageFragment()
                    }
                    else -> throw RuntimeException()
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startView()

    }

    private fun startView() {

        vp_main.run {
            this.adapter = viewPagerAdapter
            offscreenPageLimit = SCREEN_LIMIT_COUNT
            setSwipePagingEnabled(false)
        }

        tl_main.run {
            setupWithViewPager(vp_main)
            getTabAt(HOME)?.setIcon(R.drawable.ic_home)
            getTabAt(SEARCH)?.setIcon(R.drawable.ic_search)
            getTabAt(DIARY)?.setIcon(R.drawable.write)
            getTabAt(CALENDAR)?.setIcon(R.drawable.calendar)
            getTabAt(MY_PAGE)?.setIcon(R.drawable.ic_mypage)
        }

        if (!RelateLogin.loginState()) {
            noLoginMessage()
        }
    }

    private fun noLoginMessage() {

        ShowAlertDialog(this@ExerciseRestaurantActivity).apply {
            titleAndMessage(
                getString(R.string.noLoginMessage_title),
                getString(R.string.noLoginMessage_message)
            )
            cancelable(false)

            alertDialog.setPositiveButton(
                getString(R.string.common_ok)
            ) { _, _ ->
                loginCallbackListener()
            }

            alertDialog.setNegativeButton(
                getString(R.string.common_no)
            ) { _, _ -> }

            showDialog()
        }

    }


    override fun loginCallbackListener() {
        tl_main.run {
            getTabAt(MY_PAGE)?.select()
        }
    }

    override fun onReceivedData(msg: Boolean) {
        if (msg) {
            supportFragmentManager.fragments.forEach {

                when (it) {

                    is CalendarFragment -> {
                        it.renewDot()
                    }

                }
            }
        }
    }

    override fun renewBookmarkAndRank() {
        supportFragmentManager.fragments.forEach {

            when (it) {
                is SearchBookmarksFragment -> {
                    it.renewBookmark()
                }

                is SearchRankFragment -> {
                    it.renewRank()
                }

                is DiaryFragment -> {
                    it.load()
                }

                is CalendarFragment -> {
                    it.renewDot()
                }
            }

        }
    }

    override fun getNotificationData(data: NotificationModel) {

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.myPage_main_container,
                MyPageNotificationDetailsFragment
                    .newInstance(
                        data.notificationDate,
                        data.notificationSubject,
                        data.notificationContent
                    )
            )
            .addToBackStack(null)
            .commit()
    }

    companion object {

        private const val HOME = 0
        private const val SEARCH = 1
        private const val DIARY = 2
        private const val CALENDAR = 3
        private const val MY_PAGE = 4

        private const val SCREEN_LIMIT_COUNT = 5

        var selectAll = ""
    }

}


