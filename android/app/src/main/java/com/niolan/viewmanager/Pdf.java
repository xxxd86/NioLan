package com.niolan.viewmanager;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

public class Pdf extends WebView {
    private int page;// 获取当前的页面
    private int pageSize;//获取pdf的页面总数
    private String asset;
    private String path;//本地文件转换?
    private float scale = 1;
    private float minScale = 1;
    private float maxScale = 3;
    private int spacing = 10;//pdf间隔
    private String password = "";
    private float originalWidth = 0;
    private float lastPageWidth = 0;
    private float lastPageHeight = 0;
    private String errorMessage = "";//错误信息

    private PdfType pdfType = PdfType.LOCAL;//默认为本地的pdf创建
    public Pdf(@NonNull Context context) {
        super(context);
    }

    public Pdf(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Pdf(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void display(String uri){
         if(pdfType == PdfType.LOCAL){
             this.loadUrl("file:///android_asset/viewer.html?file="+ getURI(uri));
         }else {
             //权限
             String substring = uri.substring(uri.lastIndexOf("/")+1);
             File file = new File(this.getContext().getFilesDir(),substring);
             new DownloadFileAsyncTask(file, new DownloadFileAsyncTask.OnDownloadListener() {
                 @Override
                 public void onDownloadSuccess(String path) {
                     loadUrl("file:///android_asset/viewer.html?file="+path);
                 }
                 @Override
                 public void onDownloading(int progress) {
//                     Log.e(this.getSimpleName(), "onDownloading: "+progress);
                 }

                 @Override
                 public void onDownloadFailed(String msg) {
//                     Log.e(MainActivity.class.getSimpleName(), "onDownloadFailed: "+msg);
//                     Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                 }
             }).execute(uri);
             this.loadUrl("file:///android_asset/viewer.html?file="+uri);
         }

    }
    public void drawPdf(){
        display("file:///android_asset/demo.pdf");
    }
    //实现加载本地pdf
    private Uri getURI(final String uri) {
        Uri parsed = Uri.parse(uri);

        if (parsed.getScheme() == null || parsed.getScheme().isEmpty()) {
            return Uri.fromFile(new File(uri));
        }
        return parsed;
    }

    @Override
    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getMinScale() {
        return minScale;
    }

    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    public float getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(float maxScale) {
        this.maxScale = maxScale;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(float originalWidth) {
        this.originalWidth = originalWidth;
    }

    public float getLastPageWidth() {
        return lastPageWidth;
    }

    public void setLastPageWidth(float lastPageWidth) {
        this.lastPageWidth = lastPageWidth;
    }

    public float getLastPageHeight() {
        return lastPageHeight;
    }

    public void setLastPageHeight(float lastPageHeight) {
        this.lastPageHeight = lastPageHeight;
    }

    public PdfType getPdfType() {
        return pdfType;
    }

    public void setPdfType(int pdfType) {
        switch(pdfType){
            case 0:
                this.pdfType = PdfType.LOCAL;
                break;
            case 1:
                this.pdfType = PdfType.WEB;
                break;
            case 2:
            default:
            {
                this.pdfType = PdfType.LOCAL;
                break;
            }
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

