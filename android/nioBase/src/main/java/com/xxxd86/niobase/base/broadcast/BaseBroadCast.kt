package com.xxxd86.niobase.base.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

object BaseBroadCast {
    fun RegistryUI_ModeChangeBroadcastReciver(application:AppCompatActivity,handlerUiModeChange:(Int?)->Unit){
        val uiModeChangeReceiver = object :BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                val currentUiMode = p0?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
                //处理UI Mode变化
                handlerUiModeChange.invoke(currentUiMode)
            }
        }

        //注册广播接收器
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED)
        application.registerReceiver(uiModeChangeReceiver,filter)
    }
    fun sendNotification(){

    }
}