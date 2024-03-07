package com.niolan.baseModule

import android.widget.Toast
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod





class ToastModule (val context: ReactApplicationContext): ReactContextBaseJavaModule(){
    private val DURATION_SHORT_KEY = "short"
    private val DURATION_LONG_KEY = "LONG"


    override fun getName(): String {
        return "ToastExample"
    }
    override fun getConstants(): Map<String, Any>? {
        val constants: MutableMap<String, Any> = HashMap()
        constants[DURATION_SHORT_KEY] = Toast.LENGTH_SHORT
        constants[DURATION_LONG_KEY] = Toast.LENGTH_LONG
        return constants
    }

    @ReactMethod
    fun show(message: String?, duration: Int) {
        Toast.makeText(reactApplicationContext, message, duration).show()
    }

}