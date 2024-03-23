package com.niolan.viewmanager;

import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import org.json.JSONException;
import org.json.JSONObject;

public class RCTPdfM extends SimpleViewManager<Pdf> {

    public static final String REACT_CLASS = "RCTPDFVIEWER";
    ReactApplicationContext mCallerContext;

    public RCTPdfM(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }
    @Override
    public Pdf createViewInstance(ThemedReactContext context) {
        Pdf pdfViewJs = new Pdf(context,null);
        WebSettings webSettings = pdfViewJs.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        pdfViewJs.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pdfViewJs.drawPdf();
        return pdfViewJs;
    }
    @ReactProp(name = "url")
    public void setUrl(Pdf pdf, String url){
        Log.e("WebView","url is "+url);
        initWebViewSetting(pdf);
//        pdf.loadUrl(url);
    }
    private void initWebViewSetting(WebView webView){
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 获取页面内容
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementsByTagName('html')[0].innerHTML);");
                view.evaluateJavascript("(function() { return JSON.stringify(data); })();", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        // 处理 JSON 数据
                        String jsonData = value.replaceAll("^\"|\"$", "");
                        try {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            // 处理 JSON 对象
                            Log.v("JSONObject:",jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
//                view.evaluateJavascript("PDFViewerApplication.pagesCount", new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String value) {
//                        // 处理返回值
//                        int pageCount = Integer.parseInt(value);
//                        // ...
//                        Log.v("pageCount", String.valueOf(pageCount));
//                    }
//                });
            }
        });
    }
    @ReactProp(name = "path")
    public void setPath(Pdf pdfView,String path){
        pdfView.setPath(path);
    }
    @ReactProp(name = "page")
    public void setPage(Pdf pdfView, int page){
        pdfView.setPage(page);
    }
    @ReactProp(name = "scale")
    public void setScale(Pdf pdfView, float scale) {
        pdfView.setScale(scale);
    }

    @ReactProp(name = "minScale")
    public void setMinScale(Pdf pdfView, float minScale) {
        pdfView.setMinScale(minScale);
    }

    @ReactProp(name = "maxScale")
    public void setMaxScale(Pdf pdfView, float maxScale) {
        pdfView.setMaxScale(maxScale);
    }
    @ReactProp(name = "pdfType")
    public void setPdfType(Pdf pdfView,int pdfType){
        pdfView.setPdfType(pdfType);
    }

    public static final class InJavaScriptLocalObj
    {
        @JavascriptInterface
        public void showSource(String html) {
            Log.v("====>html=",html);
            //获取data-page-number
        }

        @JavascriptInterface
        public void showDescription(String str) {
            Log.v("====>html_dsc=",str);
        }
    }


}
