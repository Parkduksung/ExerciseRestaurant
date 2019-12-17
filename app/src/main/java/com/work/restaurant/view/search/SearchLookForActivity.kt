package com.work.restaurant.view.search

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.search.adapter.LookForAdapter
import com.work.restaurant.view.search.itemdetails.SearchItemDetailsFragment
import com.work.restaurant.view.search.rank.SearchRankFragment
import kotlinx.android.synthetic.main.search_look_for_main.*


class SearchLookForActivity : AppCompatActivity(), View.OnClickListener, AdapterDataListener,
    SearchLookForContract.View, SearchRankFragment.ItemClickListener {


    private lateinit var lookForAdapter: LookForAdapter
    private lateinit var presenter: SearchLookForPresenter


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

    override fun clickItemData(data: String) {

        clickData = data
        toggle = true

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.search_look_for_main)
        presenter = SearchLookForPresenter(this)
        lookForAdapter = LookForAdapter()
        lookForAdapter.setItemClickListener(this)
        ib_search_item_look.setOnClickListener(this)
        ib_search_look_back.setOnClickListener(this)

        toggleData()


    }


    private fun toggleData() {
        if (toggle) {
            getData(clickData)
        }
    }


    override fun getData(data: String) {

        et_search_look_for_item.text.clear()

        val searchItemDetailsFragment =
            SearchItemDetailsFragment()
        searchItemDetailsFragment.setSelectItem(data)


        this.supportFragmentManager.beginTransaction()
            .replace(R.id.search_look_sub_container, searchItemDetailsFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()


    }

    override fun showSearchLook(fitnessList: MutableList<FitnessCenterItemResponse>) {

        this.supportFragmentManager.popBackStack()

        recyclerview_look.run {

            this.adapter = lookForAdapter

            lookForAdapter.clearListData()

            fitnessList.forEach { fitnessCenterItemModel ->
                lookForAdapter.addData(fitnessCenterItemModel)
            }

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
            "확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })
        alertDialog.show()
    }

    override fun showBackPage() {
        toggle = false
        this@SearchLookForActivity.finish()
    }


    companion object {
        var clickData = ""
        var toggle = false
        private const val TAG = "SearchLookForActivity"
    }
}