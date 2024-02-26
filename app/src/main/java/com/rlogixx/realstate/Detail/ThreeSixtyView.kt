package com.rlogixx.realstate.Detail

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.rlogixx.realstate.R


class ThreeSixtyView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three_sixty_view)
        val webView = findViewById<View>(R.id.webview) as WebView
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl("https://gothru.co/PqtTjPR0l?index=scene_3&hlookat=177&vlookat=16&fov=120")
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true
            }

}