package com.work.restaurant.view.search.itemdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.work.restaurant.R
import com.work.restaurant.databinding.SearchItemDetailsFragmentBinding
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.search.lookfor.SearchLookForActivity.Companion.toggleWebPage


class SearchItemDetailsFragment : BaseFragment<SearchItemDetailsFragmentBinding>(
    SearchItemDetailsFragmentBinding::bind,
    R.layout.search_item_details_fragment
) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetails()
    }


    private fun showDetails() {

        binding.pbItemDetails.bringToFront()

        showStateProgressBar(true)

        val getData =
            arguments?.getString(DATA).toString()

        showUrl(binding.wbSearchItemDetail, getData)

    }

    private fun showStateProgressBar(state: Boolean) {
        binding.pbItemDetails.isVisible = state
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun showUrl(webView: WebView, getUrl: String) {
        webView.loadUrl(getUrl)
        webView.settings.apply {
            setGeolocationEnabled(true)
            javaScriptEnabled = true
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback?
            ) {
                callback?.invoke(origin, true, false)
            }
        }


        webView.webViewClient = object : WebViewClient() {


            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                showStateProgressBar(true)

                url?.let {
                    if (url.startsWith("tel:")) {
                        val intent =
                            Intent(Intent.ACTION_DIAL, Uri.parse(url))
                        startActivity(intent)
                        showStateProgressBar(false)
                        return true
                    } else if (url.startsWith("intent:")) {

                        val schemeIntent =
                            Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        val existPackage =
                            Package.getPackage(schemeIntent.`package`)

                        return if (existPackage != null) {
                            val existPackageIntent =
                                Intent(Intent.ACTION_VIEW)
                            existPackageIntent.data =
                                Uri.parse("https://play.google.com/store/apps/details?id=${existPackage.name}&hl=ko")
                            startActivity(existPackageIntent)
                            showStateProgressBar(false)
                            true
                        } else {
                            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                                startActivity(this)
                            }
                            true
                        }
                    }
                }
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                showStateProgressBar(false)

                toggleWebPage = webView.canGoBack()

            }
        }


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