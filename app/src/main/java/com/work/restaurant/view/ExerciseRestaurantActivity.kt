package com.work.restaurant.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.data.model.NotificationModel
import com.work.restaurant.view.adapter.ViewPagerAdapter
import com.work.restaurant.view.calendar.CalendarFragment
import com.work.restaurant.view.diary.main.DiaryFragment
import com.work.restaurant.view.home.main.HomeFragment
import com.work.restaurant.view.mypage.main.MyPageFragment
import com.work.restaurant.view.mypage.notification.NotificationDataListener
import com.work.restaurant.view.mypage.notification_detail.MyPageNotificationDetailsFragment
import com.work.restaurant.view.search.main.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class ExerciseRestaurantActivity : AppCompatActivity(),
    ExerciseRestaurantContract.View,
    NotificationDataListener {


    private lateinit var presenter: ExerciseRestaurantContract.Presenter

    private var fragmentMap = emptyMap<String, Fragment>()

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(
            this.supportFragmentManager,
            fragmentMap
        )
    }

    private var mBackWait: Long = 0

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }


    override fun getNotificationData(data: NotificationModel) {

        val myPageNotificationDetailsFragment =
            MyPageNotificationDetailsFragment
                .newInstance(
                    data.notificationDate,
                    data.notificationSubject,
                    data.notificationContent
                )

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.mypage_main_container,
                myPageNotificationDetailsFragment
            )
            .addToBackStack(null)
            .commit()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ExerciseRestaurantPresenter(this)
        start()


    }

    private fun start() {
        presenter.init()
    }


    override fun showInit() {

        fragmentMap = mapOf(
            resources.getStringArray(R.array.tab_main)[0] to HomeFragment(),
            resources.getStringArray(R.array.tab_main)[1] to SearchFragment(),
            resources.getStringArray(R.array.tab_main)[2] to DiaryFragment(),
            resources.getStringArray(R.array.tab_main)[3] to CalendarFragment(),
            resources.getStringArray(R.array.tab_main)[4] to MyPageFragment()
        )

        val adapter = viewPagerAdapter

        vp_main.adapter = adapter
        vp_main.offscreenPageLimit = 5

        vp_main.setSwipePagingEnabled(false)

        tl_main.run {
            setupWithViewPager(vp_main)
            getTabAt(0)?.setIcon(R.drawable.ic_home)
            getTabAt(1)?.setIcon(R.drawable.ic_search)
            getTabAt(2)?.setIcon(R.drawable.write)
            getTabAt(3)?.setIcon(R.drawable.calendar)
            getTabAt(4)?.setIcon(R.drawable.ic_mypage)
        }


    }

    companion object {

        var selectAll = ""
    }


}


