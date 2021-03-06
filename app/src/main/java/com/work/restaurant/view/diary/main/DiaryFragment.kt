package com.work.restaurant.view.diary.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.databinding.DiaryMainBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.diary.add_eat.AddEatFragment
import com.work.restaurant.view.diary.add_exercise.AddExerciseFragment
import com.work.restaurant.view.diary.main.adapter.DiaryDetailsAdapter
import com.work.restaurant.view.diary.main.adapter.DiaryMainAdapter
import com.work.restaurant.view.diary.main.presenter.DiaryContract
import com.work.restaurant.view.diary.update_or_delete_eat.UpdateOrDeleteEatFragment
import com.work.restaurant.view.diary.update_or_delete_exercise.UpdateOrDeleteExerciseFragment
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class DiaryFragment : BaseFragment<DiaryMainBinding>(DiaryMainBinding::bind, R.layout.diary_main),
    View.OnClickListener,
    View.OnLongClickListener,
    DiaryContract.View,
    AdapterDataListener.GetList {

    private lateinit var presenter: DiaryContract.Presenter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = get { parametersOf(this) }

        startView()

        binding.btnAddEat.setOnClickListener(this)
        binding.btnAddExercise.setOnClickListener(this)
        binding.ibDiaryLeftButton.setOnClickListener(this)
        binding.ibDiaryRightButton.setOnClickListener(this)
        binding.ibDiaryLeftButton.setOnLongClickListener(this)
        binding.ibDiaryRightButton.setOnLongClickListener(this)
        diaryDetailsAdapter.setItemClickListener(this)
        diaryMainAdapter.setItemClickListener(this)
    }

    private fun startView() {

        oldTodayDate = DateAndTime.currentDate()
        binding.tvDiaryDay.text = DateAndTime.currentDate()

        binding.rvDiaryMain.run {
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
                                binding.tvDiaryDay.text =
                                    DateAndTime.beforeDate(
                                        DateAndTime.convertDate(binding.tvDiaryDay.text.toString())
                                    )
                                oldPosition = lastVisible
                                load()

                            } else if (oldPosition < lastVisible) {
                                binding.tvDiaryDay.text =
                                    DateAndTime.afterDate(
                                        DateAndTime.convertDate(binding.tvDiaryDay.text.toString())
                                    )
                                oldPosition = lastVisible
                                load()
                            }
                    }
                }
            })
        }
        load()

    }

    override fun onLongClick(v: View?): Boolean {
        when (v?.id) {
            R.id.ib_diary_left_button -> {
                diaryMainAdapter.clearListData()
                oldPosition -= 7
                binding.rvDiaryMain.scrollToPosition(oldPosition)
                binding.tvDiaryDay.text =
                    DateAndTime.beforeWeek(
                        DateAndTime.convertDate(binding.tvDiaryDay.text.toString())
                    )
                load()
            }

            R.id.ib_diary_right_button -> {
                diaryMainAdapter.clearListData()
                oldPosition += 7
                binding.rvDiaryMain.scrollToPosition(oldPosition)
                binding.tvDiaryDay.text =
                    DateAndTime.afterWeek(
                        DateAndTime.convertDate(binding.tvDiaryDay.text.toString())
                    )
                load()
            }
        }

        return true
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_add_eat -> {
                if (RelateLogin.loginState()) {
                    val addEatFragment =
                        AddEatFragment.newInstance(binding.tvDiaryDay.text.toString())
                    addEatFragment.setTargetFragment(
                        this,
                        REGISTER_EAT
                    )
                    fragmentManager?.let {
                        addEatFragment.show(it, AddEatFragment.TAG)
                    }
                } else {
                    showToast(getString(R.string.diary_login_state_no1))
                }
            }

            R.id.btn_add_exercise -> {
                if (RelateLogin.loginState()) {
                    val addExerciseFragment =
                        AddExerciseFragment.newInstance(binding.tvDiaryDay.text.toString())
                    addExerciseFragment.setTargetFragment(
                        this,
                        REGISTER_EXERCISE
                    )
                    fragmentManager?.let {
                        addExerciseFragment.show(it, AddExerciseFragment.TAG)
                    }
                } else {
                    showToast(getString(R.string.diary_login_state_no1))
                }

            }

            R.id.ib_diary_left_button -> {
                diaryMainAdapter.clearListData()
                oldPosition--
                binding.rvDiaryMain.scrollToPosition(oldPosition)
                binding.tvDiaryDay.text =
                    DateAndTime.beforeDate(
                        DateAndTime.convertDate(binding.tvDiaryDay.text.toString())
                    )
                load()
            }

            R.id.ib_diary_right_button -> {
                diaryMainAdapter.clearListData()
                oldPosition++
                binding.rvDiaryMain.scrollToPosition(oldPosition)
                binding.tvDiaryDay.text =
                    DateAndTime.afterDate(
                        DateAndTime.convertDate(binding.tvDiaryDay.text.toString())
                    )
                load()
            }
        }
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

    override fun showResult(msg: Boolean) {
        if (msg) {
            renewDot()
            showToast(getString(R.string.common_delete_ok))
        } else {
            showToast(getString(R.string.common_delete_no))
        }
    }

    override fun getData(data: DiaryModel) {

        when (data.kind) {

            DiaryDetailsAdapter.KIND_EAT -> {
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

            DiaryDetailsAdapter.KIND_EXERCISE -> {
                val updateOrDeleteExerciseFragment =
                    UpdateOrDeleteExerciseFragment.newInstance(data.toExerciseModel())

                updateOrDeleteExerciseFragment.setTargetFragment(
                    this,
                    RENEW_EXERCISE
                )
                fragmentManager?.let {
                    updateOrDeleteExerciseFragment.show(it, UpdateOrDeleteExerciseFragment.TAG)
                }
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

        val getDiaryModel =
            data.map {
                it.toDiaryModel()
            }
        diaryModel.addAll(getDiaryModel)
        showResultData()
    }

    override fun showEatData(data: List<EatModel>) {

        showLoadingState(false)
        toggleEatData = true

        val getDiaryModel =
            data.map {
                it.toDiaryModel()
            }
        diaryModel.addAll(getDiaryModel)
        showResultData()
    }

    private fun getDayOfWeek(textView: TextView) {
        textView.text =
            DateAndTime.convertDayOfWeek(binding.tvDiaryDay.text.toString())
    }

    override fun showLoadingState(state: Boolean) {
        binding.pbDiaryDetails.isVisible = state
    }

    override fun showLoginState(state: Boolean) {
        binding.rvDiaryMain.isVisible = state
        binding.tvDiaryMessage.isInvisible = state
    }

    private fun renewDot() {
        if (::renewDataListener.isInitialized) {
            renewDataListener.onReceivedData(true)
        }
    }

    fun load() {
        diaryModel.clear()
        getDayOfWeek(binding.tvDiaryDayOfWeek)
        toggleExerciseData = false
        toggleEatData = false

        if (RelateLogin.loginState()) {
            showLoginState(true)
            presenter.todayEatData(
                RelateLogin.getLoginId(),
                binding.tvDiaryDay.text.toString()
            )
            presenter.todayExerciseData(
                RelateLogin.getLoginId(),
                binding.tvDiaryDay.text.toString()
            )
        } else {
            showLoadingState(false)
            showLoginState(false)
        }
    }

    override fun onResume() {
        if (oldTodayDate != DateAndTime.currentDate()) {
            binding.tvDiaryDay.text = DateAndTime.currentDate()
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
