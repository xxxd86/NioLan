package com.xxxd86.niobase.base.baseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
/**
 * 底层Fragment
 * @param  class MyFragment:ViewFragment2<FragmentMyBinding>(FragmentMyBinding::inflate){
 *
 * }
 * 1.VM框架
 * @param  class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)
 * 2.懒加载Binding
 * 3.无懒加载
 */
abstract class BaseFragment<VM:ViewModel,VB: ViewBinding>: Fragment(){
    abstract val factory: ViewModelProvider.Factory
    lateinit var viewModel: VM
    lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java,ViewGroup::class.java,Boolean::class.java)
        viewModel = ViewModelProvider(this, factory).get(getViewModelClass())
        binding = method.invoke(null,layoutInflater,container,false) as VB
        return binding.root
    }
    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }
}
