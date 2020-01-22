package com.work.restaurant.view.diary.add_exercise

import android.os.Bundle
import android.view.View
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment

class AddExerciseFragment : BaseFragment(R.layout.diary_add_exercise_main),
    View.OnClickListener {


    override fun onClick(v: View?) {

        when (v?.id) {


        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        private const val TAG = "CalendarFragment"

    }

}