package com.xxxd86.niobase.base.baseAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xxxd86.niobase.base.DeviceChange
import com.xxxd86.niobase.utils.DpUtils

/**
 * 设置Lists的class以及设置binding
 * T 为 绑定List
 */
abstract class BaseAdapter<B : ViewBinding, BH : BaseAdapter.BaseViewHolder<B>>(
    private val bindingFactory: (LayoutInflater, ViewGroup, Boolean) -> B
) : RecyclerView.Adapter<BH>(), DeviceChange {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BH {
        val itemBinding = bindingFactory(LayoutInflater.from(parent.context), parent, false)
        return createViewHolder(itemBinding)
    }

    // 具体的数据绑定逻辑
    abstract fun onBind(holder: BH, position: Int)

    abstract fun createViewHolder(binding: B): BH


    abstract class BaseViewHolder<B : ViewBinding>(val binding: B) : ViewHolder(binding.root){

        /**
         * 设置默认大小25px
         */
        fun setImage(view: ImageView?, url: String?, options: RequestOptions? = null, default: Boolean = true) {

            if (url.isNullOrEmpty() || view == null) {
                return
            }
            if(default){
                val moptions =  RequestOptions().transform(
                    CenterCrop(),
                    RoundedCorners(DpUtils.dp2px(binding.root.context, 25))
                )
                Glide.with(binding.root)
                    .load(url)
                    .apply(moptions)
                    .into(view)
            }else{
                if (options != null) {
                    Glide.with(binding.root)
                        .load(url)
                        .apply(options)
                        .into(view)
                }
            }
        }
    }
}
