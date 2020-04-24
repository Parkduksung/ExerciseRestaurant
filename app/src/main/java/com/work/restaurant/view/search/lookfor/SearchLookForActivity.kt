package com.work.restaurant.view.search.lookfor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DisplayBookmarkKakaoModel
import com.work.restaurant.util.App
import com.work.restaurant.util.Keyboard
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.util.ShowAlertDialog
import com.work.restaurant.view.ExerciseRestaurantActivity
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseActivity
import com.work.restaurant.view.home.main.HomeFragment
import com.work.restaurant.view.search.bookmarks.SearchBookmarksFragment
import com.work.restaurant.view.search.itemdetails.SearchItemDetailsFragment
import com.work.restaurant.view.search.lookfor.adapter.LookForAdapter
import com.work.restaurant.view.search.lookfor.presenter.SearchLookForContract
import com.work.restaurant.view.search.lookfor.presenter.SearchLookForPresenter
import com.work.restaurant.view.search.rank.SearchRankFragment
import kotlinx.android.synthetic.main.search_item_details_fragment.*
import kotlinx.android.synthetic.main.search_look_for_main.*


class SearchLookForActivity : BaseActivity(R.layout.search_look_for_main),
    View.OnClickListener,
    AdapterDataListener,
    AdapterDataListener.GetDisplayBookmarkKakaoModel,
    SearchLookForContract.View {

    private val lookForAdapter: LookForAdapter by lazy { LookForAdapter() }
    private lateinit var presenter: SearchLookForPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter =
            SearchLookForPresenter(
                this,
                Injection.provideKakaoRepository(),
                Injection.provideBookmarkRepository()
            )

        rv_look.run {
            this.adapter = lookForAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        lookForAdapter.setItemClickListener(this)
        lookForAdapter.setBookmarkListener(this)
        ib_search_look_back.setOnClickListener(this)

        getRecyclerClickData()
        getBookmarkData()
        getMarkerData()

        searchItem(et_search_look_for_item)

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

                et_search_look_for_item.text.clear()
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
                et_search_look_for_item.text.clear()
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
                et_search_look_for_item.text.clear()
            }
        }

    }

    override fun showSearchLook(searchModel: List<DisplayBookmarkKakaoModel>) {
        if (searchModel.isNotEmpty()) {
            lookForAdapter.clearListData()
            lookForAdapter.addAllData(searchModel)
        } else {
            Toast.makeText(
                App.instance.context(),
                getString(R.string.lookFor_no_result),
                Toast.LENGTH_SHORT
            ).show()

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

                Toast.makeText(this, getString(R.string.bookmark_state_ok), Toast.LENGTH_LONG)
                    .show()

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
                Toast.makeText(this, getString(R.string.bookmark_state_no), Toast.LENGTH_LONG)
                    .show()
                lookForAdapter.stateChange(selectPosition)

            }
            SearchLookForPresenter.FAIL_ADD -> {
                Toast.makeText(this, getString(R.string.bookmark_add_no_message), Toast.LENGTH_LONG)
                    .show()
            }

            SearchLookForPresenter.FAIL_DELETE -> {
                Toast.makeText(
                    this,
                    getString(R.string.bookmark_delete_no_message),
                    Toast.LENGTH_LONG
                )
                    .show()
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
                Toast.makeText(
                    this,
                    getString(R.string.bookmark_state_no_login_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun getData(data: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_look_sub_container, SearchItemDetailsFragment.newInstance(data))
            .addToBackStack(null)
            .commit()
        et_search_look_for_item.text.clear()
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