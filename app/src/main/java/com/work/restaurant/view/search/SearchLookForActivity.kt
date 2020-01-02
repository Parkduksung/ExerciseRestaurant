package com.work.restaurant.view.search

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.fitness.FitnessCenterRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.search.adapter.LookForAdapter
import com.work.restaurant.view.search.itemdetails.SearchItemDetailsFragment
import com.work.restaurant.view.search.main.SearchFragment
import kotlinx.android.synthetic.main.search_look_for_main.*


class SearchLookForActivity : AppCompatActivity(), View.OnClickListener, AdapterDataListener,
    SearchLookForContract.View {


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.search_look_for_main)
        presenter = SearchLookForPresenter(
            this, FitnessItemRepositoryImpl.getInstance(
                FitnessCenterRemoteDataSourceImpl.getInstance(
                    RetrofitInstance.getInstance(
                        SearchFragment.URL
                    )
                )
            )
        )
        lookForAdapter = LookForAdapter()
        lookForAdapter.setItemClickListener(this)
        ib_search_item_look.setOnClickListener(this)
        ib_search_look_back.setOnClickListener(this)

        toggleData()



        searchItem(et_search_look_for_item)

    }

    private fun searchItem(editText: EditText) {

        editText.setOnKeyListener { _, _, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                Log.d("눌럿어", "돼?")
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

        clickData = intent.extras?.getString("data").toString()
        toggle = intent.extras?.getBoolean("toggle") ?: true

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

    override fun showBackPage() =
        this@SearchLookForActivity.finish()


    companion object {
        var clickData = ""
        var toggle = false
        private const val TAG = "SearchLookForActivity"
    }
}