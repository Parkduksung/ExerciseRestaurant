package com.work.restaurant.view.search.lookfor

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.util.App
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
    AdapterDataListener.GetKakaoData,
    SearchLookForContract.View {


    private val lookForAdapter: LookForAdapter by lazy { LookForAdapter() }
    private lateinit var presenter: SearchLookForPresenter

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

    override fun showBookmarkResult(msg: Int) {
        when (msg) {
            ADD_BOOKMARK -> {
                Toast.makeText(this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_LONG).show()
            }

            DELETE_BOOKMARK -> {
                Toast.makeText(this, "즐겨찾기에 제거되었습니다.", Toast.LENGTH_LONG).show()
            }

            RESULT_FAILURE -> {
                Toast.makeText(this, "즐겨찾기에 추가를 실패하였습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun getKakaoData(select: Int, data: KakaoSearchModel) {
        when (select) {

            ADD_BOOKMARK -> {
                val toBookmarkModel = data.toBookmarkModel()
                presenter.addBookmark(toBookmarkModel)
            }

            DELETE_BOOKMARK -> {
                val toBookmarkModel = data.toBookmarkModel()
                presenter.deleteBookmark(toBookmarkModel)
            }

        }
    }

    private fun getRecyclerClickData() {

        val intent = intent

        val getData =
            intent.extras?.getString(SearchRankFragment.RECYCLERVIEW_CLICK_DATA).toString()

        val getToggle =
            intent.extras?.getBoolean(SearchRankFragment.RECYCLERVIEW_CLICK_TOGGLE)

        if (getToggle != null) {
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

        val intent = intent

        val getData =
            intent.extras?.getString(HomeFragment.MARKER_CLICK_DATA).toString()

        val getToggle =
            intent.extras?.getBoolean(HomeFragment.MARKER_CLICK_TOGGLE)

        if (getToggle != null) {
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
        val intent = intent

        val getData =
            intent.extras?.getString(SearchBookmarksFragment.BOOKMARK_DATA).toString()

        val getToggle =
            intent.extras?.getBoolean(SearchBookmarksFragment.BOOKMARK_TOGGLE)

        if (getToggle != null) {
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_search_look_back -> {
                this@SearchLookForActivity.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = SearchLookForPresenter(
            this,
            Injection.provideKakaoRepository(),
            Injection.provideBookmarkRepository()
        )
        lookForAdapter.setItemClickListener(this)
        lookForAdapter.setBookmarkListener(this)
        ib_search_look_back.setOnClickListener(this)

        getRecyclerClickData()
        getBookmarkData()
        getMarkerData()

        searchItem(et_search_look_for_item)

    }

    private fun searchItem(editText: EditText) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(editText)
                presenter.searchLook(editText.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun hideKeyboard(editText: EditText) {
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }


    override fun getData(data: String) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.search_look_sub_container, SearchItemDetailsFragment.newInstance(data))
            .addToBackStack(null)
            .commit()
        et_search_look_for_item.text.clear()
    }

    override fun showSearchLook(searchKakaoList: List<KakaoSearchModel>) {

        if (searchKakaoList.isNotEmpty()) {
            recyclerview_look.run {

                this.adapter = lookForAdapter

                lookForAdapter.clearListData()

                lookForAdapter.addAllData(searchKakaoList)

                layoutManager = LinearLayoutManager(this.context)

            }
        } else {
            Toast.makeText(
                App.instance.context(),
                "검색한 헬스장이 없습니다. 다시 한번 입력해주세요",
                Toast.LENGTH_SHORT
            ).show()
        }

        toggleSearch = true
        supportFragmentManager.popBackStack()

    }

    override fun showSearchNoFind() {
        val alertDialog =
            AlertDialog.Builder(
                ContextThemeWrapper(
                    this@SearchLookForActivity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )

        alertDialog.setTitle("검색 실패")
        alertDialog.setMessage("다시 입력해 주세요.")
        alertDialog.setPositiveButton(
            "확인"
        ) { _, _ -> }
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        toggleSearch = false
    }

    companion object {

        private const val TAG = "SearchLookForActivity"

        private const val ADD_BOOKMARK = 1
        private const val DELETE_BOOKMARK = 2

        private const val RESULT_FAILURE = 3

        private var toggleSearch = false

        var toggleWebPage = false

    }
}