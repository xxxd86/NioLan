package com.xxxd86.niobase.utils

import android.text.TextUtils
import java.util.Locale

object LocalUtil {
    fun getString(locale: Locale?): String? {
        if (locale == null) return ""
        var language = locale.language
        if (TextUtils.equals("iw", language)) {
            language = "he"
        } else if (TextUtils.equals("ji", language)) {
            language = "yi"
        } else if (TextUtils.equals("in", language)) {
            language = "id"
        }
        val country = locale.country
        return if (TextUtils.isEmpty(country)) {
            language
        } else language + "_" + country
    }
}