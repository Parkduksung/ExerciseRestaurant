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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.search.itemdetails.SearchItemDetailsFragment
import com.work.restaurant.view.search.lookfor.adapter.LookForAdapter
import com.work.restaurant.view.search.lookfor.presenter.SearchLookForContract
import com.work.restaurant.view.search.lookfor.presenter.SearchLookForPresenter
import kotlinx.android.synthetic.main.search_item_details_fragment.*
import kotlinx.android.synthetic.main.search_look_for_main.*


class SearchLookForActivity : AppCompatActivity(),
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
            wb_search_item_detail.clearHistory()
            super.onBackPressed()
        }
    }

    override fun showBookmarkResult(msg: String) {
        when (msg) {
            SUCCESS_ADD -> {
                runOnUiThread {
                    Toast.makeText(this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_LONG).show()
                }
            }

            SUCCESS_DELETE -> {
                runOnUiThread {
                    Toast.makeText(this, "즐겨찾기에 제거되었습니다.", Toast.LENGTH_LONG).show()
                }
            }

            RESULT_FAILURE -> {
                runOnUiThread {
                    Toast.makeText(this, "즐겨찾기에 추가를 실패하였습니다.", Toast.LENGTH_LONG).show()
                }
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


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_search_look_back -> {
                presenter.backPage()
            }
            R.id.ib_search_item_look -> {
                presenter.searchLook(et_search_look_for_item.text.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_look_for_main)
        presenter = SearchLookForPresenter(
            this,
            Injection.provideKakaoRepository(),
            Injection.provideBookmarkRepository()
        )
        lookForAdapter.setItemClickListener(this)
        lookForAdapter.setBookmarkListener(this)
        ib_search_item_look.setOnClickListener(this)
        ib_search_look_back.setOnClickListener(this)

        toggleData()
        searchItem(et_search_look_for_item)

    }

    private fun searchItem(editText: EditText) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
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

    private fun toggleData() {

        val intent = intent

        clickData = intent.extras?.getString(GET_DATA).toString()
        toggleClickData = intent.extras?.getBoolean(GET_TOGGLE) ?: false

        if (toggleClickData) {
            getData(clickData)
        }
    }


    override fun getData(data: String) {

        if (toggleClickData) {
            val searchItemDetailsFragment =
                SearchItemDetailsFragment.newInstance(data)
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.search_look_sub_container, searchItemDetailsFragment)
                .addToBackStack(null)
                .commit()
            et_search_look_for_item.text.clear()
            toggleClickData = false
        }
    }

    override fun showSearchLook(searchKakaoList: List<KakaoSearchModel>) {


        recyclerview_look.run {

            this.adapter = lookForAdapter

            lookForAdapter.clearListData()

            lookForAdapter.addAllData(searchKakaoList)

            layoutManager = LinearLayoutManager(this.context)

        }
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

    override fun showBackPage() =
        this@SearchLookForActivity.finish()


    companion object {
        var clickData = ""
        var toggleClickData = false

        private const val TAG = "SearchLookForActivity"

        private const val ADD_BOOKMARK = 1
        private const val DELETE_BOOKMARK = 2
        private const val SUCCESS_ADD = "successAdd"
        private const val SUCCESS_DELETE = "successDelete"
        private const val RESULT_FAILURE = "error"

        private const val GET_DATA = "data"
        private const val GET_TOGGLE = "toggle"

        var toggleWebPage = false

    }
}