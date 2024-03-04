package com.xxxd86.niobase.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.zip.ZipEntry
import java.util.zip.ZipFile


/**
 * @param 日常读取文件，打开文件页面，图片页面,使用Retrofit监听技术
 *
 */
object FileTools {

    fun intentToFileReturn(activity: Activity, fileType:String, fileNumber:Int, callBack:()->Unit){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*")//fileType
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        activity.startActivityForResult(intent,101)
    }
    private suspend fun <T> withZipFromUri(
        context: Context,
        uri: Uri, block: suspend (ZipFile) -> T
    ) : T {
        val file = File(context.filesDir, "tempzip.zip")

        try {
            return withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    context.contentResolver.openInputStream(uri).use { input ->
                        if (input == null) throw FileNotFoundException("openInputStream failed")
                        file.outputStream().use { input.copyTo(it) }
                    }
                    ZipFile(file, ZipFile.OPEN_READ).use { block.invoke(it) }
                }.getOrThrow()
            }
        } finally {
            file.delete()
        }
    }
    suspend fun doStuffWithZip(context: Context, uri: Uri) {
        withZipFromUri(context, uri) { // it: ZipFile
            for (entry in it.entries()) {
                Log.v("ENTRY","entry: ${entry.name}") // or whatever
            }
        }
    }
    fun unZipFile(zipFile: String, targetFile: String) {
        var outputStream: OutputStream? = null
        var inputStream: InputStream? = null
        try {
            File(targetFile).run {
                if (!exists()) {
                    mkdirs()
                }
            }
            val zf = ZipFile(zipFile)
            val entries = zf.entries()
            while (entries.hasMoreElements()) {
                val zipEntry: ZipEntry = entries.nextElement()
                val zipEntryName = zipEntry.name
                inputStream = zf.getInputStream(zipEntry)
                if (zipEntryName.startsWith("__MACOSX")) {
                    continue
                }
                File(targetFile, zipEntryName).apply {
                    if (isDirectory) {
                        if (!exists()) {
                            mkdirs()
                        }
                    } else {
                        createNewFile()
                        writeStreamToFile(inputStream, this)
                    }
                }
                inputStream?.close()
                outputStream?.close()
            }
            File(zipFile).delete()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }


    private fun writeStreamToFile(stream: InputStream, file: File) {
        try {
            val fos = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var len: Int
            var total = 0
            while ((stream.read(buffer).also { len = it }) != -1) {
                fos.write(buffer, 0, len)
                total += len
            }

            fos.flush()
            fos.close()
            stream.close()
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }


    fun getMapRootPath(context: Context): String {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            context.filesDir.absolutePath + "filename"
        } else {
            context.getExternalFilesDir("filename")?.absolutePath
                ?: context.cacheDir.absolutePath
        }
    }
     fun makedir(file:File){
         if (!file.parentFile.exists()) {

             file.parentFile.mkdirs()
         }
         if (file.exists()) {
             file.delete()
         }
     }
    /**
     * 创建DebugLog文件,以及目录
     */
     fun createLogFile(mStrPath:String) {
        //传入路径 + 文件名
        val mFile = File(mStrPath)
        makedir(mFile)
        val m_All_File = File("$mStrPath/all.txt")
        val m_Service_File = File("$mStrPath/Service/service.txt")
        makedir(m_Service_File)
        val m_DownLoad_File = File("$mStrPath/DownLoad/download.txt")
        makedir(m_DownLoad_File)
        val m_FileTools_File = File("$mStrPath/FileTools/fileTools.txt")
        makedir(m_FileTools_File)
        //判断文件是否存在，存在就删除
        if (mFile.exists()) {
            mFile.delete()
        }
        try {
            //创建文件
            mFile.createNewFile()
            m_All_File.createNewFile()
            m_Service_File.createNewFile()
            m_DownLoad_File.createNewFile()
            m_FileTools_File.createNewFile()
            Log.i("文件创建", "文件创建成功")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 写入debug文件
     */
    fun writeTxt(fileName: String?, content: String?) {
        try {   //要指定编码方式，否则会出现乱码
            val osw = OutputStreamWriter(FileOutputStream(fileName, true), "UTF-8")
            osw.write(content)
            osw.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}