package com.xxxd86.niobase.utils

import android.os.Environment
import android.util.Log

/**
 * 模仿大公司使用将debug的打印日志过程给写入文件之中，并进行一定的分类处理
 * 简单来说可以分为四大组件以及总的文件，一共的话是5个文件
 * 主要用于帮助与反馈界面，上传本地的debugLog信息
 */
object RNDebugLog {
    val mStrPath = Environment.getExternalStorageDirectory().path + "/RNDebugLog";
    val SERVICE_LOG_TAG = "[Service_Debug_Log]:"
    val FILETOOLS_LOG_TAG = "[FILETOOLS_Debug_Log]:"
    //所有涉及service的代码的Log都应该使用这个
    fun Service_Debug_Log(service:String,message:String){
        Log.v(SERVICE_LOG_TAG,"ServiceName:$service>>>>>>>>>>$message")
        //加载到并写入独立文件之中
        FileTools.writeTxt(mStrPath +"/Service/service.txt","ServiceName:$service>>>>>>>>>>$message")
        //加载写入所有文件文件之中
        FileTools.writeTxt(mStrPath +"/all.txt","ServiceName:$service>>>>>>>>>>$message")
    }
    fun FileTools_Debug_Log(file:String,message:String){
        Log.v(FILETOOLS_LOG_TAG,"FileName:$file>>>>>>>>>>$message")
        //加载到文件之中
    }

    fun uploadDebugLog(){

    }


}