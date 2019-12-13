package com.work.restaurant.view.search.look_for

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
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.search.itemdetails.SearchItemDetailsFragment
import com.work.restaurant.view.search.look_for.adapter.LookForAdapter
import com.work.restaurant.view.search.look_for.presenter.SearchLookForContract
import com.work.restaurant.view.search.look_for.presenter.SearchLookForPresenter
import kotlinx.android.synthetic.main.search_look_for_fragment.*

class SearchLookForFragment : Fragment(), View.OnClickListener, AdapterDataListener,
    SearchLookForContract.View {


    private lateinit var lookForAdapter: LookForAdapter
    private lateinit var presenter: SearchLookForPresenter

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
        return inflater.inflate(R.layout.search_look_for_fragment, container, false).also {
            lookForAdapter = LookForAdapter()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        //뒷화면 터치막기
        root_layout.setOnTouchListener { v, event ->
            true
        }

        presenter = SearchLookForPresenter(this)
        ib_search_look_back.setOnClickListener(this)
        ib_search_item_look.setOnClickListener(this)
        lookForAdapter.setItemClickListener(this)

    }


    override fun getData(data: String) {
        Toast.makeText(context, data, Toast.LENGTH_LONG).show()
        val searchOkItemFragment =
            SearchItemDetailsFragment()
        searchOkItemFragment.setSelectItem(data)
        this.requireFragmentManager().beginTransaction()
            .add(R.id.search_look_sub_container, searchOkItemFragment).commit()
    }


    override fun showBackPage() {
        this.requireFragmentManager().beginTransaction().remove(this).commit()
    }

    override fun showSearchLook(
        fitnessList: MutableList<FitnessCenterItemResponse>
    ) {

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

//    fun ttt(data: String) {
//        val searchOkItemFragment =
//            SearchItemDetailsFragment()
//        searchOkItemFragment.setSelectItem(data)
//        this.requireFragmentManager().beginTransaction()
//            .replace(R.id.search_look_sub_container, searchOkItemFragment).commit()
//    }


    companion object {
        private const val TAG = "SearchLookFragment"
    }


}