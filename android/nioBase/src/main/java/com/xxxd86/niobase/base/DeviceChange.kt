package com.xxxd86.niobase.base

interface DeviceChange {
    fun onNightChange(onSuccess:() ->Unit):Boolean
    fun onLanguageChange(onSuccess: () -> Unit):Boolean

    fun onScreanChange(onSuccess: () -> Unit)
}