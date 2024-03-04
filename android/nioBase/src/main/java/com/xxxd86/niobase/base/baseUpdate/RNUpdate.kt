package com.xxxd86.niobase.base.baseUpdate

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * 实现静默下载，以及非静默下载 ， ！！！！非无须重新安装
 * 默认非静默安装，为RNBase提供安装流程监听
 */
class RNUpdate() {
    /**
     * @param 强烈建议再初始页面Splash中调用
     */
    fun initOkHttpBuilder(){
//        //如果你想使用okhttp作为下载的载体，那么你需要自己依赖okhttp，更新库不强制依赖okhttp！可以使用如下代码创建一个OkHttpClient 并在UpdateConfig中配置setCustomDownloadConnectionCreator start
//        val builder = OkHttpClient.Builder()
//
//        builder.connectTimeout(30000, TimeUnit.SECONDS)
//            .readTimeout(30000, TimeUnit.SECONDS)
//            .writeTimeout(30000, TimeUnit.SECONDS) //如果你需要信任所有的证书，可解决根证书不被信任导致无法下载的问题 start
//            .sslSocketFactory(SSLUtils.createSSLSocketFactory())
//            .hostnameVerifier(TrustAllHostnameVerifier()) //如果你需要信任所有的证书，可解决根证书不被信任导致无法下载的问题 end
//            .retryOnConnectionFailure(true)
//        //如果你想使用okhttp作为下载的载体，那么你需要自己依赖okhttp，更新库不强制依赖okhttp！可以使用如下代码创建一个OkHttpClient 并在UpdateConfig中配置setCustomDownloadConnectionCreator end
//        //当你希望自己提供json数据给插件，让插件自己解析并实现更新
//        val updateConfig: UpdateConfig = UpdateConfig()
//            .setDebug(true) //是否是Debug模式
//            .setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_JSON) //设置获取更新信息的方式为JSON方式
//            .setShowNotification(true) //配置更新的过程中是否在通知栏显示进度
//            .setNotificationIconRes(R.mipmap.download_icon) //配置通知栏显示的图标
//            .setUiThemeType(TypeConfig.UI_THEME_AUTO) //配置UI的样式，一种有12种样式可供选择
//            .setAutoDownloadBackground(false) //是否需要后台静默下载，如果设置为true，则调用checkUpdate方法之后会直接下载安装，不会弹出更新页面。当你选择UI样式为TypeConfig.UI_THEME_CUSTOM，静默安装失效，您需要在自定义的Activity中自主实现静默下载，使用这种方式的时候建议setShowNotification(false)，这样基本上用户就会对下载无感知了
//            .setCustomActivityClass(CustomActivity::class.java) //如果你选择的UI样式为TypeConfig.UI_THEME_CUSTOM，那么你需要自定义一个Activity继承自RootActivity，并参照demo实现功能，在此处填写自定义Activity的class，否则不用设置
//            .setNeedFileMD5Check(false) //是否需要进行文件的MD5检验，如果开启需要提供文件本身正确的MD5校验码，DEMO中提供了获取文件MD5检验码的工具页面，也提供了加密工具类Md5Utils
//            .setCustomDownloadConnectionCreator(Creator(builder)) //如果你想使用okhttp作为下载的载体，可以使用如下代码创建一个OkHttpClient，并使用demo中提供的OkHttp3Connection构建一个ConnectionCreator传入，在这里可以配置信任所有的证书，可解决根证书不被信任导致无法下载apk的问题
//            .setModelClass(UpdateModel()) //这里设置JSON解析之后对应的Model 用于json解析
//
//        AppUpdateUtils.init(this, updateConfig) //执行初始化

    }

}