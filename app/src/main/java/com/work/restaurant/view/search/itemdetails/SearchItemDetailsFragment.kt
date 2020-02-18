package com.work.restaurant.view.search.itemdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
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

        pb_item_details.bringToFront()

        searchResult()
    }


    private fun searchResult() {

        pb_item_details.visibility = View.VISIBLE

        val bundle = arguments
        val getData = bundle?.getString(DATA).toString()

        showUrl(wb_search_item_detail, getData)

    }


    private fun showUrl(webview: WebView, url: String) {
        webview.loadUrl(url)
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.d("뒤로가기갑지가하면왜죽지", "shouldOverrideUrlLoading11")
                return super.shouldOverrideUrlLoading(view, request)


            }

            override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
                super.onScaleChanged(view, oldScale, newScale)
                Log.d("뒤로가기갑지가하면왜죽지?", "onScaleChanged")
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                pb_item_details.visibility = View.GONE
                Log.d("뒤로가기갑지가하면왜죽지?", "onPageCommitVisible")
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                Log.d("뒤로가기갑지가하면왜죽지?", "shouldOverrideUrlLoading")
//                pb_item_details.visibility = View.VISIBLE

                if (url != null) {
                    if (url.startsWith("tel:")) {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                        startActivity(intent)
                        pb_item_details.visibility = View.GONE
                        return true
                    }
                }

                return false

            }


            //페이지가 켜졌을때
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("뒤로가기갑지가하면왜죽지?", "onPageFinished")

//                pb_item_details.visibility = View.GONE
                toggleWebPage = webview.canGoBack()


            }
        }


    }


    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }


    companion object {
        private const val TAG = "SearchItemDetailsFragment"
        private const val DATA = "data"

        fun newInstance(
            data: String
        ) = SearchItemDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(DATA, data)
            }
        }


    }


}