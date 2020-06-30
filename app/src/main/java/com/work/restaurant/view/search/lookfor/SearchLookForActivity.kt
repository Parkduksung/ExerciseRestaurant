package com.work.restaurant.view.search.lookfor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.databinding.SearchLookForMainBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.Keyboard
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.ExerciseRestaurantActivity
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.base.BaseActivity
import com.work.restaurant.view.home.main.HomeFragment
import com.work.restaurant.view.search.bookmarks.SearchBookmarksFragment
import com.work.restaurant.view.search.itemdetails.SearchItemDetailsFragment
import com.work.restaurant.view.search.lookfor.adapter.LookForAdapter
import com.work.restaurant.view.search.lookfor.presenter.SearchLookForContract
import com.work.restaurant.view.search.lookfor.presenter.SearchLookForPresenter
import com.work.restaurant.view.search.rank.SearchRankFragment
import kotlinx.android.synthetic.main.search_item_details_fragment.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf


class SearchLookForActivity : BaseActivity<SearchLookForMainBinding>(
    SearchLookForMainBinding::inflate,
    R.layout.search_look_for_main
),
    View.OnClickListener,
    AdapterDataListener,
    AdapterDataListener.GetDisplayBookmarkKakaoModel,
    SearchLookForContract.View {

    private val lookForAdapter: LookForAdapter by lazy { LookForAdapter() }
    private lateinit var presenter: SearchLookForContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = get { parametersOf(this) }

        binding.rvLook.run {
            this.adapter = lookForAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        lookForAdapter.setItemClickListener(this)
        lookForAdapter.setBookmarkListener(this)
        binding.ibSearchLookBack.setOnClickListener(this)

        getRecyclerClickData()
        getBookmarkData()
        getMarkerData()

        searchItem(binding.etSearchLookForItem)

    }

    override fun onBackPressed() {

        if (toggleWebPage) {
            wb_search_item_detail.goBack()
        } else {
            if (toggleSearch) {
                super.onBackPressed()
            } else {
                this@SearchLookForActivity.finish()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_search_look_back -> {
                this@SearchLookForActivity.finish()
            }
        }
    }

    private fun searchItem(editText: EditText) {
        editText.setOnEditorActionListener { _, actionId, _ ->

            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    Keyboard.hideEditText(this, editText)
                    presenter.searchLook(editText.text.toString())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun getRecyclerClickData() {


        val getData =
            intent.extras?.getString(SearchRankFragment.CLICK_ITEM_DATA).toString()

        val getToggle =
            intent.extras?.getBoolean(SearchRankFragment.CLICK_ITEM_TOGGLE)

        getToggle?.let {
            if (getToggle) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.search_look_sub_container,
                        SearchItemDetailsFragment.newInstance(getData)
                    )
                    .addToBackStack(null)
                    .commit()

                binding.etSearchLookForItem.text.clear()
            }
        }

    }

    private fun getMarkerData() {

        val getData =
            intent.extras?.getString(HomeFragment.MARKER_CLICK_DATA).toString()

        val getToggle =
            intent.extras?.getBoolean(HomeFragment.MARKER_CLICK_TOGGLE)

        getToggle?.let {
            if (getToggle) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.search_look_sub_container,
                        SearchItemDetailsFragment.newInstance(getData)
                    )
                    .addToBackStack(null)
                    .commit()
                binding.etSearchLookForItem.text.clear()
            }
        }
    }

    private fun getBookmarkData() {

        val getData =
            intent.extras?.getString(SearchBookmarksFragment.BOOKMARK_DATA).toString()

        val getToggle =
            intent.extras?.getBoolean(SearchBookmarksFragment.BOOKMARK_TOGGLE)

        getToggle?.let {
            if (getToggle) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.search_look_sub_container,
                        SearchItemDetailsFragment.newInstance(getData)
                    )
                    .addToBackStack(null)
                    .commit()
                binding.etSearchLookForItem.text.clear()
            }
        }

    }

    override fun showSearchLook(searchModel: List<DisplayBookmarkKakaoModel>) {
        if (searchModel.isNotEmpty()) {
            lookForAdapter.clearListData()
            lookForAdapter.addAllData(searchModel)
        } else {
            showToast(getString(R.string.lookFor_no_result))
        }
        presenter.resetData()
        toggleSearch = true
        supportFragmentManager.popBackStack()
    }

    override fun showSearchNoFind() {
        ShowAlertDialog(this@SearchLookForActivity).apply {
            titleAndMessage(
                getString(R.string.noSearchMessage_title),
                getString(R.string.noSearchMessage_message)
            )
            alertDialog.setPositiveButton(
                getString(R.string.common_ok)
            ) { _, _ -> }
            showDialog()
        }
    }

    override fun showBookmarkResult(msg: Int, selectPosition: Int) {
        when (msg) {
            SearchLookForPresenter.ADD_BOOKMARK -> {
                val addressAllIntent =
                    Intent(
                        this@SearchLookForActivity,
                        ExerciseRestaurantActivity::class.java
                    ).apply {
                        putExtra(RENEW, true)
                    }

                setResult(RESULT_OK, addressAllIntent)

                showToast(getString(R.string.bookmark_state_ok))

                lookForAdapter.stateChange(selectPosition)
            }

            SearchLookForPresenter.DELETE_BOOKMARK -> {
                val addressAllIntent =
                    Intent(
                        this@SearchLookForActivity,
                        ExerciseRestaurantActivity::class.java
                    ).apply {
                        putExtra(RENEW, true)
                    }

                setResult(RESULT_OK, addressAllIntent)
                showToast(getString(R.string.bookmark_state_no))
            }
            SearchLookForPresenter.FAIL_ADD -> {
                showToast(getString(R.string.bookmark_add_no_message))
            }

            SearchLookForPresenter.FAIL_DELETE -> {
                showToast(getString(R.string.bookmark_delete_no_message))
            }
        }
    }

    override fun getDisplayBookmarkKakaoData(
        select: Int,
        data: DisplayBookmarkKakaoModel,
        selectPosition: Int
    ) {
        when (select) {
            LookForAdapter.ADD_BOOKMARK -> {
                val toBookmarkModel =
                    data.toBookmarkModel(RelateLogin.getLoginId())
                presenter.addBookmark(toBookmarkModel, selectPosition)
            }
            LookForAdapter.DELETE_BOOKMARK -> {
                val toBookmarkModel =
                    data.toBookmarkModel(RelateLogin.getLoginId())
                presenter.deleteBookmark(toBookmarkModel, selectPosition)

            }
            LookForAdapter.NOT_LOGIN_STATE -> {
                showToast(getString(R.string.bookmark_state_no_login_message))
            }
        }
    }

    override fun getData(data: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_look_sub_container, SearchItemDetailsFragment.newInstance(data))
            .addToBackStack(null)
            .commit()
        binding.etSearchLookForItem.text.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        toggleSearch = false
    }

    companion object {

        private const val TAG = "SearchLookForActivity"
        private var toggleSearch = false

        const val RENEW = "renew"
        var toggleWebPage = false

    }
}