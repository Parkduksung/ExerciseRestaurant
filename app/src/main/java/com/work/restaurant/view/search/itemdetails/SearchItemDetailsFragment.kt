package com.work.restaurant.view.search.itemdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.search.itemdetails.presenter.SearchItemDetailsContract
import com.work.restaurant.view.search.itemdetails.presenter.SearchItemDetailsPresenter
import com.work.restaurant.view.search.lookfor.SearchLookForActivity.Companion.toggleWebPage
import kotlinx.android.synthetic.main.search_item_details_fragment.*


class SearchItemDetailsFragment : BaseFragment(R.layout.search_item_details_fragment),
    SearchItemDetailsContract.View {


    private lateinit var detailsPresenter: SearchItemDetailsPresenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsPresenter =
            SearchItemDetailsPresenter(
                this
            )

        searchResult()
    }


    private fun searchResult() {

        val bundle = arguments
        val getData = bundle?.getString(DATA).toString()

        showUrl(wb_search_item_detail, getData)
    }


    private fun showUrl(webview: WebView, url: String) {
        webview.loadUrl(url)
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true

        webview.webViewClient = object : WebViewClient() {

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

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                toggleWebPage = webview.canGoBack()
            }
        }

        webview.setOnKeyListener { _, keyCode, _ ->

            if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
                webview.goBack()
                true
            }
            false
        }
    }


    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
        toggleWebPage = false
    }


    companion object {
        private const val TAG = "SearchItemDetailsFragment"
        private const val DATA = "data"
        private const val Toggle = "toggle"

        fun newInstance(
            data: String
        ) = SearchItemDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(DATA, data)
            }
        }


    }


}