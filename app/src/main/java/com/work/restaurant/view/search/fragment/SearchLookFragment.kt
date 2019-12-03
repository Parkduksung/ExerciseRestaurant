package com.work.restaurant.view.search.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.R
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.view.adapter.SearchItemAdapter
import com.work.restaurant.view.adapter.SearchItemAdapterDataListener
import com.work.restaurant.view.search.contract.SearchLookContract
import com.work.restaurant.view.search.presenter.SearchLookPresenter
import kotlinx.android.synthetic.main.search_look_fragment.*

class SearchLookFragment : Fragment(), View.OnClickListener, SearchItemAdapterDataListener,
    SearchLookContract.View {


    private lateinit var searchItemAdapter: SearchItemAdapter
    private lateinit var presenter: SearchLookPresenter

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ib_search_look_back -> {
                presenter.backPage()
            }
            R.id.ib_search_item_look -> {
                presenter.searchLook(et_search_item.text.toString())
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_look_fragment, container, false).also {
            searchItemAdapter = SearchItemAdapter()
            presenter = SearchLookPresenter(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        ib_search_look_back.setOnClickListener(this)
        ib_search_item_look.setOnClickListener(this)
        searchItemAdapter.setItemClickListener(this)

    }

    override fun showBackPage() {
        this.requireFragmentManager().beginTransaction().remove(this).commit()
    }

    override fun showSearchLook(
        fitnessList: MutableList<FitnessCenterItemModel>
    ) {

        recyclerview_look.run {

            this.adapter = searchItemAdapter

            searchItemAdapter.clearListData()

            fitnessList.forEach { fitnessCenterItemModel ->
                searchItemAdapter.addData(fitnessCenterItemModel)
            }

            layoutManager = LinearLayoutManager(this.context)

        }


    }

    override fun showSearchNoFind() {
        val alertDialog =
            AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
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


    override fun getSearchItemData(data: String) {
        Toast.makeText(context, data, Toast.LENGTH_LONG).show()
        val searchOkItemFragment = SearchItemFragment()
        searchOkItemFragment.setSelectItem(data)
        this.requireFragmentManager().beginTransaction()
            .replace(R.id.search_look_sub_container, searchOkItemFragment).commit()
    }


    companion object {
        private const val TAG = "SearchLookFragment"
    }


}