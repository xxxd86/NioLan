package com.xxxd86.niobase.base.baseActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * 注意声明   android:usesCleartextTraffic="true"，以及网络信息
 */

abstract class BaseWebActivity<VM: ViewModel,VB : ViewBinding> (override var bindingFactory: (LayoutInflater) -> VB): BaseVMActivity<ViewModel, ViewBinding>(bindingFactory) {
     var m_webView: WebView? = null
    override val binding: VB by lazy { bindingFactory(layoutInflater) }
    @SuppressLint("SetJavaScriptEnabled")
    fun setWebView(webView: WebView){
        m_webView = webView
        m_webView?.settings?.apply {
            //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript

            javaScriptEnabled = true;



    //支持插件

            pluginState = WebSettings.PluginState.ON


            //设置自适应屏幕，两者合用

            useWideViewPort = true; //将图片调整到适合webview的大小

            loadWithOverviewMode = true; // 缩放至屏幕的大小



           //缩放操作

          setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。

           builtInZoomControls = true; //设置内置的缩放控件。若为false，则该WebView不可缩放

            displayZoomControls = false; //隐藏原生的缩放控件



    //其他细节操作

           cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK; //关闭webview中缓存

           allowFileAccess = true; //设置可以访问文件

          javaScriptCanOpenWindowsAutomatically = true; //支持通过JS打开新窗口

          loadsImagesAutomatically = true; //支持自动加载图片

          defaultTextEncodingName = "utf-8";//设置编码格式
            m_webView?.webViewClient = object : WebViewClient() {
                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                   val hit = view.hitTestResult;
                    //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                    if (TextUtils.isEmpty(hit.extra) || hit.type == 0) {
                        //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                        Log.e("重定向", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
                        Log.e("重定向", "GetURL: " + view.getUrl() + "\n" +"getOriginalUrl()"+ view.getOriginalUrl());
                        Log.d("重定向", "URL: " + url);
                    }

                    if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
                        view.loadUrl(url);
                        return false; //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
                    } else { //加载的url是自定义协议地址
                        try {
                            val intent =  Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        } catch (e:Exception) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    //设定加载开始的操作
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    //设定加载结束的操作
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    //设定加载资源的操作
                }

                @Deprecated("Deprecated in Java")
                override fun onReceivedError(
                    view: WebView,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    when (errorCode) {
    //                    .SC_NOT_FOUND -> view.loadUrl("file:///android_assets/error_handle.html")
                    }
                }

                @SuppressLint("WebViewClientOnReceivedSslError")
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler,
                    error: SslError?
                ) {
                    handler.proceed() //表示等待证书响应

                    // handler.cancel(); //表示挂起连接，为默认方式

                    // handler.handleMessage(null); //可做其他处理
                }
            }
            m_webView?.webChromeClient = object : WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress < 100) {
                        val progress:String = "$newProgress%";

    //                    progress.setText(progress);

                    } else {
    //                    progress.setText(“100%”);

                    }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                }
            }
        }

    }
    fun loadUrl(url:String){
        m_webView?.loadUrl(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(m_webView!=null){
            m_webView?.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            m_webView?.clearHistory()
            (m_webView?.parent as ViewGroup).removeView(m_webView)
            m_webView?.destroy()
            m_webView = null
        }
    }

}