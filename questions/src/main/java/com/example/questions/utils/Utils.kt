package com.example.questions.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.TextView
//import com.bumptech.glide.request.target.SimpleTarget
//import com.bumptech.glide.request.transition.Transition
import me.wcy.htmltext.HtmlImageLoader
import me.wcy.htmltext.HtmlText
import me.wcy.htmltext.OnTagClickListener

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/10.
 */
object Utils {
    fun htmlText(str: String?, view: TextView, listener: OnTagClickListener?=null) {
        HtmlText.from(str).setImageLoader(object : HtmlImageLoader {
            override fun getErrorDrawable(): Drawable? {
                return null
            }

            override fun getMaxWidth(): Int {
                return view.width
            }

            override fun loadImage(url: String?, callback: HtmlImageLoader.Callback?) {
                // : 2019/1/10 15:49 霍述雷 图片加载
//                url?.let {
//                    QGlide.with(view.context)
//                        .asBitmap()
//                        .load(url)
//                        .into(object : SimpleTarget<Bitmap>() {
//                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                                callback?.onLoadComplete(resource)
//                            }
//
//                            override fun onLoadFailed(errorDrawable: Drawable?) {
//                                callback?.onLoadFailed()
//                            }
//                        })
//
//                }

            }

            override fun fitWidth(): Boolean {
                return false
            }

            override fun getDefaultDrawable(): Drawable? {
                return null
            }

        }).setOnTagClickListener(listener)
            .into(view)
    }
}