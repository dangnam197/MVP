package com.example.mvp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.webview.*
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mvp.R
import com.example.mvp.ui.main.MainActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





class SelectTruyenActivity :AppCompatActivity() {
    lateinit var link:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl(intent.getStringExtra("link"))
        web_view.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    link = url
                    return false
                }
                return true
            }
        })
        btn_open.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("LINK",link)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            super.onBackPressed()
        }
    }
}