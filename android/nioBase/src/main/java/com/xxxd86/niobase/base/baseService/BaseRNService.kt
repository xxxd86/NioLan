package com.xxxd86.niobase.base.baseService

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import com.xxxd86.niobase.utils.FileTools
import com.xxxd86.niobase.utils.RNDebugLog
import com.xxxd86.niobase.utils.TimeUtil
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * @param 整个程序启动的service,初始化整个程序所需要的内容以及所必须加载的包，如zip包 ,以及图像包，xml包等等，原因是为了是整个程序的大小尽量控制
 * @property 需要自定义BaseUrl或者是远程访问接口，BaseService提供外接url下载  默认位置
 * @param 创建本地LogFile，方便读写
 * @exception 不允许继承
 * @param 实现自动更新，调用onStartCommand可以进行操作
 *
 */
class BaseRNService:Service() {
    var m_zipFileName: String? = null
    val mStrPath = Environment.getExternalStorageDirectory().path + "/RNDebugLog";
    private var m_startTime:Long = 0
    var m_StartCommedTime:Long = 0
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        m_startTime = TimeUtil.getCurrentTime()
        RNDebugLog.Service_Debug_Log("BaseService","基础Service已经启动")
        FileTools.createLogFile(mStrPath)//创建debug文件
//        downLoadResourceAndReleaseLocal(m_zipFileName)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        m_StartCommedTime= TimeUtil.getCurrentTime()

        return super.onStartCommand(intent, flags, startId)
        //上传debugLog,并标注debug时间段,交给完成,目前先不进行网络请求只做

    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        m_StartCommedTime= TimeUtil.getCurrentTime()
    }

    private fun downLoadResourceAndReleaseLocal(zipFileName: String?){
        val zipFile = ZipFile(zipFileName)
        for (entry in ZipFile(zipFileName).entries()) {
            if (entry.isDirectory) {
                File(zipFileName?.let { File(it).parentFile }, entry.name).mkdirs()
            } else {
                File(
                    zipFileName?.let { File(it).parentFile }, entry.name
                ).writeBytes(zipFile.getInputStream(entry as ZipEntry).readBytes())
            }
        }
    }

    fun setZipFileName(zipFileName: String){
        m_zipFileName = zipFileName
    }




}