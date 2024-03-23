package com.niolan.viewmanager

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class PdfView : WebView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        privateBrowsing: Boolean
    ) : super(context, attrs, defStyleAttr, privateBrowsing)
   //仅限本地url
    private fun display(url: String) {
        this.loadUrl("file:///android_asset/viewer.html?file=$url")
    }
    fun drawPdf() {
        //进行网络请求得到pdf?
        display("file:///android_asset/demo.pdf")
    }
    //监听当前pagechange

}