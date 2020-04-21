package com.work.restaurant.view.diary.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.util.App
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.diary.add_eat.AddEatFragment
import com.work.restaurant.view.diary.add_exercise.AddExerciseFragment
import com.work.restaurant.view.diary.main.adapter.DiaryDetailsAdapter
import com.work.restaurant.view.diary.main.adapter.DiaryMainAdapter
import com.work.restaurant.view.diary.main.presenter.DiaryContract
import com.work.restaurant.view.diary.main.presenter.DiaryPresenter
import com.work.restaurant.view.diary.update_or_delete_eat.UpdateOrDeleteEatFragment
import com.work.restaurant.view.diary.update_or_delete_exercise.UpdateOrDeleteExerciseFragment
import kotlinx.android.synthetic.main.diary_main.*


class DiaryFragment : BaseFragment(R.layout.diary_main),
    View.OnClickListener,
    View.OnLongClickListener,
    DiaryContract.View,
    AdapterDataListener.GetList {

    private lateinit var presenter: DiaryPresenter
    private lateinit var renewDataListener: RenewDataListener

    private val diaryDetailsAdapter: DiaryDetailsAdapter by lazy { DiaryDetailsAdapter() }
    private val diaryModel = mutableSetOf<DiaryModel>()

    private val snapHelper: PagerSnapHelper by lazy { PagerSnapHelper() }

    private val diaryMainAdapter: DiaryMainAdapter by lazy { DiaryMainAdapter() }


    interface RenewDataListener {
        fun onReceivedData(msg: Boolean)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as? RenewDataListener)?.let {
            renewDataListener = it
        }
    }

    override fun showResult(msg: Boolean) {
        if (msg) {
            renewDot()
            Toast.makeText(
                this.context,
                getString(R.string.diary_delete_ok_message),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this.context,
                getString(R.string.diary_delete_no_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getData(data: DiaryModel) {

        if (data.kind == 1) {

            //register exercise update
            val updateOrDeleteExerciseFragment1 =
                UpdateOrDeleteExerciseFragment.newInstance(data.toExerciseModel())

            updateOrDeleteExerciseFragment1.setTargetFragment(
                this,
                RENEW_EXERCISE
            )
            fragmentManager?.let {
                updateOrDeleteExerciseFragment1.show(it, UpdateOrDeleteExerciseFragment.TAG)
            }

        } else {

            //register eat update
            val updateOrDeleteEatFragment =
                UpdateOrDeleteEatFragment.newInstance(
                    data.toEatModel()
                )

            updateOrDeleteEatFragment.setTargetFragment(
                this,
                RENEW_EAT
            )
            fragmentManager?.let {
                updateOrDeleteEatFragment.show(it, UpdateOrDeleteEatFragment.TAG)
            }
        }
    }


    private fun showResultData() {

        if (toggleEatData && toggleExerciseData) {
            if (diaryModel.isEmpty()) {
                diaryMainAdapter.addDetailsData(emptyList())
            } else {
                diaryMainAdapter.addDetailsData(diaryModel.toList().sortedBy { it.time })
            }
        }
    }

    override fun showExerciseData(data: List<ExerciseModel>) {

        showLoadingState(false)
        toggleExerciseData = true


        val getDiaryModel = data.map {
            it.toDiaryModel()
        }

        diaryModel.addAll(getDiaryModel)
        showResultData()

    }


    override fun showEatData(data: List<EatModel>) {

        showLoadingState(false)

        toggleEatData = true

        val getDiaryModel = data.map {
            it.toDiaryModel()
        }

        diaryModel.addAll(getDiaryModel)
        showResultData()

    }

    override fun onLongClick(v: View?): Boolean {
        when (v?.id) {
            R.id.ib_diary_left_button -> {
                diaryMainAdapter.clearListData()
                oldPosition -= 7
                rv_diary_main.scrollToPosition(oldPosition)
                tv_diary_day.text =
                    DateAndTime.beforeWeek(
                        DateAndTime.convertDate(tv_diary_day.text.toString())
                    )
                load()
            }

            R.id.ib_diary_right_button -> {
                diaryMainAdapter.clearListData()
                oldPosition += 7
                rv_diary_main.scrollToPosition(oldPosition)
                tv_diary_day.text =
                    DateAndTime.afterWeek(
                        DateAndTime.convertDate(tv_diary_day.text.toString())
                    )
                load()
            }

        }

        return true
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_add_eat -> {
                if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                    val addEatFragment =
                        AddEatFragment.newInstance(tv_diary_day.text.toString())
                    addEatFragment.setTargetFragment(
                        this,
                        REGISTER_EAT
                    )
                    fragmentManager?.let {
                        addEatFragment.show(it, AddEatFragment.TAG)
                    }
                } else {
                    Toast.makeText(
                        this.context,
                        getString(R.string.diary_login_state_no),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }

            R.id.btn_add_exercise -> {

                if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                    val addExerciseFragment =
                        AddExerciseFragment.newInstance(tv_diary_day.text.toString())
                    addExerciseFragment.setTargetFragment(
                        this,
                        REGISTER_EXERCISE
                    )
                    fragmentManager?.let {
                        addExerciseFragment.show(it, AddExerciseFragment.TAG)
                    }
                } else {
                    Toast.makeText(
                        this.context,
                        getString(R.string.diary_login_state_no),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }

            R.id.ib_diary_left_button -> {

                diaryMainAdapter.clearListData()
                oldPosition--
                rv_diary_main.scrollToPosition(oldPosition)
                tv_diary_day.text =
                    DateAndTime.beforeDate(
                        DateAndTime.convertDate(tv_diary_day.text.toString())
                    )
                load()
            }

            R.id.ib_diary_right_button -> {

                diaryMainAdapter.clearListData()
                oldPosition++
                rv_diary_main.scrollToPosition(oldPosition)
                tv_diary_day.text =
                    DateAndTime.afterDate(
                        DateAndTime.convertDate(tv_diary_day.text.toString())
                    )
                load()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = DiaryPresenter(
            this,
            Injection.provideEatRepository(),
            Injection.provideExerciseRepository()
        )

        oldTodayDate = DateAndTime.currentDate()

        tv_diary_day.text =
            DateAndTime.currentDate()


        rv_diary_main.run {
            adapter = diaryMainAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            scrollToPosition(startPosition)
            snapHelper.attachToRecyclerView(this)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    diaryMainAdapter.clearListData()

                    val lastVisible =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                    if (lastVisible != -1) {
                        if (recyclerView.scrollState == 2 || recyclerView.scrollState == 0)

                            if (oldPosition > lastVisible) {
                                tv_diary_day.text =
                                    DateAndTime.beforeDate(
                                        DateAndTime.convertDate(tv_diary_day.text.toString())
                                    )
                                oldPosition = lastVisible
                                load()

                            } else if (oldPosition < lastVisible) {
                                tv_diary_day.text =
                                    DateAndTime.afterDate(
                                        DateAndTime.convertDate(tv_diary_day.text.toString())
                                    )
                                oldPosition = lastVisible
                                load()
                            }
                    }
                }
            })
        }

        load()
        btn_add_eat.setOnClickListener(this)
        btn_add_exercise.setOnClickListener(this)
        ib_diary_left_button.setOnClickListener(this)
        ib_diary_right_button.setOnClickListener(this)
        ib_diary_left_button.setOnLongClickListener(this)
        ib_diary_right_button.setOnLongClickListener(this)
        diaryDetailsAdapter.setItemClickListener(this)
        diaryMainAdapter.setItemClickListener(this)

    }

    private fun getDayOfWeek(textView: TextView) {
        textView.text = DateAndTime.convertDayOfWeek(tv_diary_day.text.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            REGISTER_EAT -> {
                if (resultCode == Activity.RESULT_OK) {
                    load()
                    renewDot()
                }
            }
            REGISTER_EXERCISE -> {
                if (resultCode == Activity.RESULT_OK) {
                    load()
                    renewDot()
                }
            }
            RENEW_EAT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val result =
                        data?.extras?.getInt(UpdateOrDeleteEatFragment.SEND_RESULT_NUM)
                    result?.let { resultNum ->

                        when (resultNum) {
                            UpdateOrDeleteEatFragment.DELETE_EAT -> {
                                load()
                                renewDot()
                            }

                            UpdateOrDeleteEatFragment.UPDATE_EAT -> {
                                load()
                                renewDot()
                            }
                        }
                    }

                }
            }

            RENEW_EXERCISE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val result =
                        data?.extras?.getInt(UpdateOrDeleteExerciseFragment.SEND_RESULT_NUM)
                    result?.let { resultNum ->

                        when (resultNum) {
                            UpdateOrDeleteExerciseFragment.DELETE_EXERCISE -> {
                                load()
                                renewDot()
                            }
                            UpdateOrDeleteExerciseFragment.UPDATE_EXERCISE -> {
                                load()
                                renewDot()
                            }
                        }
                    }

                }
            }

        }
    }

    override fun showLoadingState(state: Boolean) {
        pb_diary_details.isVisible = state
    }

    override fun showLoginState(state: Boolean) {
        rv_diary_main.isVisible = state
        tv_diary_message.isInvisible = state
    }

    private fun renewDot() {
        if (::renewDataListener.isInitialized) {
            renewDataListener.onReceivedData(true)
        }
    }

    fun load() {
        diaryModel.clear()
        getDayOfWeek(tv_diary_day_of_week)
        toggleExerciseData = false
        toggleEatData = false

        if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
            showLoginState(true)
            presenter.todayEatData(
                App.prefs.login_state_id,
                tv_diary_day.text.toString()
            )
            presenter.todayExerciseData(
                App.prefs.login_state_id,
                tv_diary_day.text.toString()
            )
        } else {
            showLoadingState(false)
            showLoginState(false)
        }
    }


    //다음날로 넘어가는데 이전에 키다가 shared 쓰면 안넘어가니까.
    override fun onResume() {
        if (oldTodayDate != DateAndTime.currentDate()) {
            tv_diary_day.text = DateAndTime.currentDate()
            load()
        }
        super.onResume()
    }


    companion object {
        private const val TAG = "DiaryFragment"
        private const val REGISTER_EAT = 1
        private const val REGISTER_EXERCISE = 2
        private const val RENEW_EAT = 3
        private const val RENEW_EXERCISE = 4


        private var toggleEatData = false
        private var toggleExerciseData = false

        private var oldTodayDate = ""
        private var oldPosition = ((Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % 3))
        private const val startPosition = Int.MAX_VALUE / 2
    }

}
