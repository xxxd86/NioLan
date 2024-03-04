package com.xxxd86.niobase.base.baseAdapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.xxxd86.niobase.base.DeviceChange

/**
 * 使用recyclerview实例,处理滑动冲突
 * 并绑定DeviceChange
 */
abstract class BaseRecyclerView : RecyclerView, DeviceChange {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


}