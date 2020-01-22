package com.work.restaurant.view.diary.add_eat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import kotlinx.android.synthetic.main.diary_add_eat.*
import java.text.SimpleDateFormat
import java.util.*


class AddEatFragment : BaseFragment(R.layout.diary_add_eat),
    View.OnClickListener {


    override fun onClick(v: View?) {

        when (v?.id) {


        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        add_eat_radio_group.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {

                R.id.rb_meal -> {
                    Toast.makeText(this.context, "라디오 그룹 버튼1 눌렸습니다.", Toast.LENGTH_SHORT)
                        .show()

                }

                R.id.rb_snack -> {
                    Toast.makeText(this.context, "라디오 그룹 버튼2 눌렸습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


//        btn_add_eat_main.setOnClickListener {
//
//            val btn = EditText(this.context)
//
//            ll_add_eat_main.addView(btn)
//
//        }

    }

    private fun init() {
        val currentTime = Calendar.getInstance().time
        val dateText =
            SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime)

        val timeText =
            SimpleDateFormat("a h시 mm분", Locale.getDefault()).format(currentTime)

        tv_add_today.text = dateText
        btn_add_eat_time.text = timeText

    }


    companion object {
        private const val TAG = "CalendarFragment"

    }

}
