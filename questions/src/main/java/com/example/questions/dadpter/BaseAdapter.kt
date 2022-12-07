package com.example.questions.dadpter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.questions.utils.PagingScrollHelper

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/11.
 */

abstract class BaseAdapter<T>(
    val mData: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<Holder>() {
    var mContext: Context? = null
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        if (mContext == null) mContext = recyclerView.context
        val helper = PagingScrollHelper()
        helper.setUpRecycleView(recyclerView)
        helper.setOnPageChangeListener(object : PagingScrollHelper.onPageChangeListener {
            override fun onPageChange(index: Int) {
                selectedItem(index)
            }
        })
    }

    abstract fun selectedItem(index: Int)
    open fun setData(data: List<T>?) {
        clear()
        notifyDataSetChanged()
        data?.let {
            mData.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

}