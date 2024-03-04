package com.xxxd86.niobase.utils

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import java.util.Locale


/**
 * 页面控制以及权限控制
 */
object DeviceUtils {
    private val REQUEST_CODE = 1024
    /**
     * 整个页面都是图片，隐藏状态栏和底部导航栏。
     */
    private fun setFullScreen(activity:AppCompatActivity?){
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        activity?.supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= 28) {
            val params = activity?.window?.attributes
            params?.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            activity?.window?.attributes = params
        }
    }

    /**
     * 自定义状态栏颜色，不收起来
     */
    private fun setTransTableScreen(activity:AppCompatActivity?,color:Int){
        activity?.window?.statusBarColor = color
    }

    /**
     * 收起状态栏
     */
    private fun setFullScreenWithStateBar(activity: AppCompatActivity?){
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAGS_CHANGED,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
    fun setScreen(screenState: ScreenState, activity:AppCompatActivity?, state_Color: Int = 0){
        when(screenState){
            ScreenState.STATE_FULL -> setFullScreen(activity)
            ScreenState.STATE_PAT -> setTransTableScreen(activity,state_Color)
            ScreenState.STATE_COM -> setFullScreenWithStateBar(activity)
        }
    }
    fun getInternPermission(){

    }
    private fun lacksPermission(mContexts: Context, permission: String): Boolean {

        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED
    }
    fun checkLocationPermission(activity: AppCompatActivity,mPermissions:String,lackPermission:()->Unit,havePermission:()->Unit) {
        if (lacksPermission(activity.applicationContext,mPermissions)) {

            lackPermission.invoke()
            requestPermissions(
                activity, arrayOf(mPermissions),
                1
            )
        } else {
            //权限开启
            havePermission.invoke()
        }
    }

    //申请录音权限
    private const val GET_RECODE_AUDIO = 1
    private val PERMISSION_AUDIO = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )
    /*
    * 申请录音权限*/
    fun verifyAudioPermissions(activity: Activity?) {
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.RECORD_AUDIO
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                activity, PERMISSION_AUDIO,
                GET_RECODE_AUDIO
            )
        }
    }
    fun getAllFilePermission(activity: Activity,callback:(Intent)->Unit){
        if (Build.VERSION.SDK_INT >= 23) { // 6.0
            val perms = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            )
            for (p in perms) {
                val f = ContextCompat.checkSelfPermission(
                    activity.applicationContext,
                    p
                )
                Log.d("---", String.format("%s - %d", p, f))
                if (f != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(activity,perms, 0XCF)
                    break
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {// android 11  且 不是已经被拒绝
            // 先判断有没有权限
            if (!Environment.isExternalStorageManager()) {
                val intent =  Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                callback.invoke(intent)
            }
        }
    }
    fun getLanguageInfo(): String? {
        val locale = Locale.getDefault()
        return LocalUtil.getString(locale)
    }
    fun getFilePermission(application: AppCompatActivity){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // 先判断有没有权限
                if (Environment.isExternalStorageManager()) {

                } else {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.setData(Uri.parse("package:" + application.applicationContext.getPackageName()))
                    application.startActivityForResult(intent, REQUEST_CODE)
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 先判断有没有权限
                if (ActivityCompat.checkSelfPermission(
                        application,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        application,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                } else {
                    requestPermissions(
                        application,
                        arrayOf<String>(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        REQUEST_CODE
                    )
                }
            } else {
            }

    }
}

/**
 * @param 设置屏幕的状态
 * 主要分为三个状态
 * 1.全屏 —— 广告加载
 * 2.状态栏符合最近的颜色状态
 * 3.普通状态,无状态栏
 * 4.收起特殊栏保留状态栏
 */
enum class ScreenState{
      STATE_FULL,STATE_PAT,STATE_COM
}