package com.xxxd86.niobase.base.baseActivity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.xxxd86.niobase.base.DeviceChange
import com.xxxd86.niobase.utils.DeviceUtils
import com.xxxd86.niobase.utils.ScreenState
import java.lang.reflect.ParameterizedType

/**
 * MVVM框架基础
 */
abstract class BaseVMActivity<VM : ViewModel, VB : ViewBinding> (open val bindingFactory: (LayoutInflater) -> VB) : AppCompatActivity(),
    DeviceChange {
    lateinit var viewModel: VM
    open val binding: VB by lazy { bindingFactory(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceUtils.setScreen(ScreenState.STATE_FULL,this)
        viewModel = ViewModelProvider(this).get(getViewModelClass())
        setContentView(binding.root)
        initView()
    }

    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }

    /**
     * 实现调用Glide
     */
    open fun getImageSoureGlide(context: Context, url:String , view:ImageView){
        Glide.with(context)
            .load(url)
            .into(view)
    }

    open fun initView(){}
}
