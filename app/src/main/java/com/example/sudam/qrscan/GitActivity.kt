package com.example.sudam.qrscan

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_git.*
import android.graphics.Bitmap
import android.os.Handler
import android.widget.Toast


class GitActivity : AppCompatActivity() {

    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git)
        setTitle(resources.getString(R.string.gitHub))

        webview.setWebViewClient(myWebClient())
        webview.getSettings().setJavaScriptEnabled(true)
        webview.getSettings().setBuiltInZoomControls(true)
        webview.getSettings().setDisplayZoomControls(false)
        webview.loadUrl(intent.getStringExtra("url"))
    }

    inner class myWebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            progressBar.visibility = View.VISIBLE
            view.loadUrl(url)
            return true

        }

        override fun onPageFinished(view: WebView, url: String) {

            super.onPageFinished(view, url)

            progressBar.visibility = View.GONE
        }

    }

    @Override
    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press Back again to exit.", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
            return
        }
    }

}