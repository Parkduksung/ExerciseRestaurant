package com.work.restaurant.view.search.itemdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.KakaoSearchModel
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.search.itemdetails.adapter.KakaoImageAdapter
import com.work.restaurant.view.search.itemdetails.presenter.SearchItemDetailsContract
import com.work.restaurant.view.search.itemdetails.presenter.SearchItemDetailsPresenter
import kotlinx.android.synthetic.main.search_item_details_fragment.*


class SearchItemDetailsFragment : BaseFragment(R.layout.search_item_details_fragment),
    View.OnClickListener, SearchItemDetailsContract.View {

    private lateinit var detailsPresenter: SearchItemDetailsPresenter
    private lateinit var intent: Intent
    private lateinit var kakaoImageAdapter: KakaoImageAdapter


    override fun showKakaoItemInfoDetail(searchList: List<KakaoSearchModel>) {


        val webSettings = wb_search_item_detail.settings
        webSettings.javaScriptEnabled = true

        wb_search_item_detail.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                if (url != null) {
                    if (url.startsWith("tel:")) {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))

                        startActivity(intent)
                        return true
                    }
                }
                return false
            }
        }

        wb_search_item_detail.loadUrl(searchList[0].placeUrl)
    }


    override fun onClick(v: View?) {

        when (v?.id) {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_item_details_fragment, container, false).also {
            intent = Intent(Intent.ACTION_DIAL)
            kakaoImageAdapter = KakaoImageAdapter()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsPresenter =
            SearchItemDetailsPresenter(
                this,
                Injection.provideKakaoRepository()
            )

        searchResult()

    }


    private fun searchResult() {

        val bundle = arguments

        if (bundle?.getBoolean("toggle") != null) {
            if (bundle.getBoolean("toggle")) {

                val webSettings = wb_search_item_detail.settings
                webSettings.javaScriptEnabled = true

                wb_search_item_detail.webViewClient = object : WebViewClient() {

                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                        if (url != null) {
                            if (url.startsWith("tel:")) {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))

                                startActivity(intent)
                                return true
                            }
                        }
                        return false
                    }
                }
                wb_search_item_detail.loadUrl(bundle.getString("data"))

            } else {

                bundle.getString("data")?.let { detailsPresenter.kakaoItemInfoDetail(it) }
            }
        }
    }


    companion object {
        private const val TAG = "SearchOkItemFragment"


        fun newInstance(
            data: String,
            toggle1: Boolean
        ) = SearchItemDetailsFragment().apply {
            arguments = Bundle().apply {
                putString("data", data)
                putBoolean("toggle", toggle1)
            }
        }


    }


}