package com.xxxd86.niobase.base.baseActivity

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.xxxd86.niobase.base.DeviceChange

/**
 * 底层Activity
 * 对于深色背景进行判断，并传出handler对UI进行绘制
 * @param  class BaseLoadingActivity : BaseVMActivity<BaseLoadingActivityViewModel, ActivityBaseLoadingBinding>()
 * 1.VM框架
 * @param  class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)
 * 2.懒加载Binding
 * 3.无懒加载
 * 4.背景颜色
 */
abstract class BaseActivity<B : ViewBinding?> : AppCompatActivity() ,
    DeviceChange {
        var binding: B? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
    }

    /**
     * 检测当前暗黑状态
     */
    override fun onNightChange(onSuccess: () -> Unit):Boolean {

        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                onSuccess.invoke()
                true
            }

            else -> false
        }

    }

    /**
     * 需要作者额外添加对于某一页面的实现语言更改
     */
    override fun onLanguageChange(onSuccess: () -> Unit): Boolean {
        return false
    }
    /**
     * 对于屏幕状态的更改  enum 横向，纵向，折叠屏横向：竖向，并在在此处理滑动冲突，
     */
    override fun onScreanChange(onSuccess: () -> Unit) {

    }
}
abstract class BaseActivityLate<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {
    val binding: B by lazy { bindingFactory(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }
    open fun initView(){}
}

abstract class BaseActivityCommon<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {
    private lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }
}

