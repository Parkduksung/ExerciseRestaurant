package com.work.restaurant.view.diary.main

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DateModel
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.diary.add_eat.AddEatFragment
import com.work.restaurant.view.diary.add_exercise.AddExerciseFragment
import com.work.restaurant.view.diary.main.adapter.DiaryAdapter
import com.work.restaurant.view.diary.main.presenter.DiaryContract
import com.work.restaurant.view.diary.main.presenter.DiaryPresenter
import kotlinx.android.synthetic.main.diary_main.*
import java.text.SimpleDateFormat
import java.util.*


class DiaryFragment : BaseFragment(R.layout.diary_main),
    View.OnClickListener,
    DiaryContract.View,
    AdapterDataListener {
    override fun getData(data: String) {

    }

    override fun showData(data: List<DateModel>) {


        recyclerview_diary.run {

            this.adapter = diaryAdapter

            diaryAdapter.clearListData()
            diaryAdapter.addAllData(data)

            layoutManager = LinearLayoutManager(this.context)
        }

    }


    private lateinit var presenter: DiaryPresenter
    private lateinit var diaryAdapter: DiaryAdapter

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
        return inflater.inflate(R.layout.diary_main, container, false).also {
            diaryAdapter = DiaryAdapter()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        presenter = DiaryPresenter(
            this,
            Injection.provideEatRepository()
        )
        diaryAdapter.setItemClickListener(this)
        init()

        btn_add_eat.setOnClickListener(this)
        btn_add_exercise.setOnClickListener(this)


    }


    private fun init() {

        val currentTime = Calendar.getInstance().time
        val dateText =
            SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime)
        tv_today.text = dateText

        presenter.data()


    }


    companion object {
        private const val TAG = "DiaryFragment"

    }


    override fun onResume() {
        super.onResume()

    }

}
