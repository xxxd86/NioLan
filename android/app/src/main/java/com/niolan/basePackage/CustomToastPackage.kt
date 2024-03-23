package com.niolan.basePackage

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import com.niolan.baseModule.ToastModule
import com.niolan.viewmanager.RCTPdfManager
import java.util.Collections

class CustomToastPackage : ReactPackage{
    override fun createNativeModules(p0: ReactApplicationContext): MutableList<NativeModule> {
        val modules = arrayListOf<NativeModule>()
        modules.add(ToastModule(p0))
        return modules
    }

    override fun createViewManagers(p0: ReactApplicationContext): MutableList<ViewManager<View, ReactShadowNode<*>>> {
        return Collections.emptyList()
    }
}