package com.work.restaurant.view.diary.main

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.diary.add_eat.AddEatFragment
import com.work.restaurant.view.diary.add_exercise.AddExerciseFragment
import kotlinx.android.synthetic.main.diary_main.*
import java.text.SimpleDateFormat
import java.util.*


class DiaryFragment : BaseFragment(R.layout.diary_main),
    View.OnClickListener {


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_add_eat -> {

                requireFragmentManager().beginTransaction()
                    .replace(R.id.main_container, AddEatFragment())
                    .addToBackStack(null)
                    .commit()
            }

            R.id.btn_add_exercise -> {
                requireFragmentManager().beginTransaction()
                    .replace(R.id.main_container, AddExerciseFragment())
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.diary_main, container, false)
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentTime = Calendar.getInstance().time
        val dateText =
            SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime)
        tv_today.text = dateText

        btn_add_eat.setOnClickListener(this)
        btn_add_exercise.setOnClickListener(this)

    }

    companion object {
        private const val TAG = "DiaryFragment"

    }

}
