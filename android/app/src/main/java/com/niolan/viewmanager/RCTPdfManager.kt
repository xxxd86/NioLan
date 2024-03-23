package com.niolan.viewmanager

import android.webkit.WebSettings
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import java.util.Enumeration


class RCTPdfManager : SimpleViewManager<PdfView>{
    val REACT_CLASS = "RCTPdf"
    var mCallerContext: ReactApplicationContext? = null

    constructor(mCallerContext: ReactApplicationContext?) : super() {
        this.mCallerContext = mCallerContext
    }

    override fun getName(): String {
        return REACT_CLASS
    }

    override fun createViewInstance(p0: ThemedReactContext): PdfView {
        var pdfView = PdfView(p0)
        val webSettings: WebSettings? = pdfView.settings
        webSettings?.setSupportZoom(true)
        webSettings?.builtInZoomControls = true
        webSettings?.displayZoomControls = false
        webSettings?.useWideViewPort = true
        webSettings?.loadWithOverviewMode = true
        webSettings?.javaScriptEnabled = true
        webSettings?.allowFileAccess = true
        webSettings?.allowFileAccessFromFileURLs = true
        webSettings?.allowUniversalAccessFromFileURLs = true
        pdfView.drawPdf()
       return pdfView
    }
}