package com.xxxd86.niobase.utils

import android.content.Context
import android.os.Environment

object FilePathTools {
    /**
     * 获取路径：/data/user/0/应用包名/files
     * 该目录是应用的文件存储目录，应用被卸载时，该目录一同被系统删除。
     * 不会因为系统内存不足而被清空。
     * 默认存在，默认具备读写权限(6.0系统可以不用向用户申请)
     * @param context
     * @return
     */
    fun getFileDir(context: Context): String {
        return context.filesDir.absolutePath
    }

    /**
     * 获取路径：/data/user/0/应用包名/cache
     * 该目录是应用的文件缓存目录，应用被卸载时，该目录一同被系统删除。
     * 默认存在，默认具备读写权限。不同于getFileDir，该目录下的文件在系统内存紧张时，会被清空文件，来腾出空间供系统使用。
     * 著名的图片加载库ImageLoader就是在没有外置存储读写权限时使用此文件夹。
     * getFileDir，不会因为系统内存不足而被清空。(6.0系统可以不用向用户申请)
     * @param context
     * @return
     */
    fun getCacheDir(context: Context): String {
        return context.cacheDir.absolutePath
    }

    /**
     * 获取路径：/storage/emulated/0/Android/obb/应用包名
     * 该目录是应用的数据存放目录，一般被用来存放游戏数据包obb文件。
     * 默认存在，可读写(6.0系统可以不用向用户申请)
     * @param context
     * @return
     */
    fun getObbDir(context: Context): String {
        return context.obbDir.absolutePath
    }

    /**
     * 获取路径：/data/user/0/应用包名/code_cache
     * 默认存在，可读写。(6.0系统可以不用向用户申请)
     *
     * @param context
     * @return
     */
    fun getCodeCacheDir(context: Context): String {
        return context.codeCacheDir.absolutePath
    }

    /**
     * 获取路径:(以下载目录为准) /storage/emulated/0/Android/data/应用包名/files/Download
     * 默认存在，可读写。(6.0系统可以不用向用户申请)
     * @param context
     * @param s
     * @return
     */
    fun getExternalFilesDir(context: Context, s: String?): String {
        return context.getExternalFilesDir(s)!!.absolutePath
    }

    /**
     * 获取路径：/storage/emulated/0/Android/data/应用包名/cache
     * 默认存在，可读写。(6.0系统可以不用向用户申请)
     * @param context
     * @return
     */
    fun getExternalCacheDir(context: Context): String {
        return context.externalCacheDir!!.absolutePath
    }

    /**
     * 获取路径：/data/user/0/应用包名/databases/参数名
     * 默认不存在，可读写。(6.0系统可以不用向用户申请)
     * @param context
     * @param s
     * @return
     */
    fun getDatabasePath(context: Context, s: String?): String {
        return context.getDatabasePath(s).absolutePath
    }

    /**
     * 获取路径：/data/user/0/应用包名/app_参数名
     * 默认存在，可读写。分为Private等三个权限，private代表仅能自己访问。(6.0系统可以不用向用户申请)
     * @param context
     * @param s
     * @param i Context.MODE_PRIVATE
     * @return
     */
    fun getDir(context: Context, s: String?, i: Int): String {
        return context.getDir(s, i).absolutePath
    }

    /**
     * 获取路径：/data/app/应用包名-1/base.apk
     * 默认存在，获取apk包路径
     * @param context
     * @return
     */
    fun getPackageCodePath(context: Context): String {
        return context.packageCodePath
    }

    /**
     * 获取路径：/storage/emulated/0
     * 默认存在，声明权限则可读写（6.0和以后系统还需要向用户申请同意才可以）
     * @return
     */
    val externalStorageDirectory: String
        get() = Environment.getExternalStorageDirectory().absolutePath

    /**
     * 获取路径：/storage/emulated/0/Download（以下载目录为例）
     * 默认存在，声明权限则可读写（6.0和以后系统还需要向用户申请同意才可以）
     * @param s
     * @return
     */
    fun getExternalStoragePublicDirectory(s: String?): String {
        return Environment.getExternalStoragePublicDirectory(s).absolutePath
    }

    /**
     * 获取路径：/data/cache
     * 默认存在，声明权限则可读写（6.0和以后系统还需要向用户申请同意才可以）
     * @return
     */
    val downloadCacheDirectory: String
        get() = Environment.getDownloadCacheDirectory().absolutePath

    /**
     * 获取路径：/data/user/应用包名/files/download（示例download）
     * 该目录是应用的文件存储目录，应用被卸载时，该目录一同被系统删除。
     * 默认存在，默认具备读写权限(6.0系统可以不用向用户申请)
     * @param context
     * @param s
     * @return
     */
    fun getFileStreamPath(context: Context, s: String?): String {
        return context.getFileStreamPath(s).absolutePath
    }

}