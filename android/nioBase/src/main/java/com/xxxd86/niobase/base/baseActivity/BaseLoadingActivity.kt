package com.xxxd86.niobase.base.baseActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.xxxd86.niobase.utils.DeviceUtils
import com.xxxd86.niobase.utils.ScreenState


/**
 * @author wh
 * @sample 加载动画页面，适用于首页加载,动画加载，页面Page，以及插件点击
 * 添加一系列如同界面广告跳转，以及网络判断,设置页面大小
 * 默认全屏显示fullScrean
 * 动画加载网络库 使用Glide
 */
abstract class BaseLoadingActivity <VM:ViewModel,VB : ViewBinding> (override var bindingFactory: (LayoutInflater) -> VB): BaseVMActivity<ViewModel, ViewBinding>(bindingFactory){
    var fullScreen = true
    override val binding: VB by lazy { bindingFactory(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (fullScreen){
            DeviceUtils.setScreen(ScreenState.STATE_FULL,this)
        }
    }

    /**
     * 加载进入时广告加载，Invoke点击方法，使用array方式进行储存
     */
    fun resolveLoadingResource(array: ArrayList<String>,view:View){

    }
    /**
     * 实现Splash每次进入加载广告三秒
     * @param SPLASH_RE
     */
    override fun onRestart() {
        super.onRestart()

    }
}
enum class LoadingType{
    SPLASH_NO,SPLASH_RE,JUMP,LOAD
}
