package com.example.questions.dadpter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.questions.Item
import com.example.questions.QuestionInfo
import com.example.questions.widegt.*

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/14.
 */
class QuestionAdapter(
    val isHeader: Boolean = true,
    val isFooter: Boolean = false,
    val isFeedback: Boolean = true,
    val isAnalysis: Boolean = false,
    val isSingle: Boolean = true,
    val mData: MutableList<Item> = mutableListOf(),
    val select: (String) -> Unit
) : RecyclerView.Adapter<Holder>() {
    var itemX: Item? = null
    var questionInfo: QuestionInfo? = null
    var name: String? = null
    var headerView: View? = null
    var footerView: View? = null
    private var currentView: View? = null
    private var answer = ""
    private fun text(item: Item, view: OptionView01?) {
        view?.setData(
            item,
            if (questionInfo?.useranswer().isNullOrBlank()) itemX?.useranswer() else questionInfo?.useranswer(),
            if (questionInfo?.rightanswer().isNullOrBlank()) itemX?.rightanswer() else questionInfo?.rightanswer(),
            isAnalysis
        )
        view?.setOnClickListener {
            if (isAnalysis) return@setOnClickListener
            if (isSingle) {
                (currentView as? OptionView02)?.unselect()
                (currentView as? OptionView01)?.unselect()
                view.select()
                currentView = view
                select(item.order() ?: "")
            } else {
                val order = item.order() ?: ""
                if (order in answer) {
                    view.unselect()
                    answer = answer.replace(order, "")
                } else {
                    view.select()
                    answer+=order
                }
                select(answer)
            }
        }
    }

    private fun img(item: Item, view: OptionView02?) {
        view?.setData(
            item,
            if (questionInfo?.useranswer().isNullOrBlank()) itemX?.useranswer() else questionInfo?.useranswer(),
            if (questionInfo?.rightanswer().isNullOrBlank()) itemX?.rightanswer() else questionInfo?.rightanswer(),
            isAnalysis
        )
        view?.setOnClickListener {
            if (isAnalysis) return@setOnClickListener
            if (isSingle) {
                (currentView as? OptionView02)?.unselect()
                (currentView as? OptionView01)?.unselect()
                view.select()
                currentView = view
                select(item.order() ?: "")
            } else {
                val order = item.order() ?: ""
                if (order in answer) {
                    view.unselect()
                    answer.replace(order, "")
                } else {
                    view.select()
                    answer.plus(order)
                }
                select(answer)
            }
        }
    }

    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): Holder {
        return when (viewType) {
            HEADER -> Holder((headerView ?: View(group.context)).apply {
                layoutParams = ViewGroup.LayoutParams(-1, -2)
            })
            TEXT -> Holder(OptionView01(group.context).apply {
                layoutParams = ViewGroup.LayoutParams(-1, -2)
            })
            IMG -> Holder(OptionView02(group.context).apply {
                layoutParams = ViewGroup.LayoutParams(-1, -2)
            })
            FOOTER -> Holder((footerView ?: View(group.context)).apply {
                layoutParams = ViewGroup.LayoutParams(-1, -2)
            })
            else -> Holder(View(group.context).apply {
                layoutParams = ViewGroup.LayoutParams(-1, -2)
            })
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return when (holder.itemViewType) {
            HEADER -> {
            }
            TEXT -> {
                text(mData[position - if (isHeader) 1 else 0], holder.itemView as? OptionView01)
            }
            IMG -> {
                img(mData[position - if (isHeader) 1 else 0], holder.itemView as? OptionView02)
            }
            FOOTER -> {
            }
            else -> {
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (isHeader && position == 0) return HEADER
        if ((isAnalysis || isFooter||isFeedback) && position == itemCount - 1) return FOOTER
        if (mData[position - if (isHeader) 1 else 0].imgs().isNullOrBlank()) return TEXT
        if (mData[position - if (isHeader) 1 else 0].content().isNullOrBlank()) return IMG
        return DEFAULT
    }

    override fun getItemCount(): Int {
        return mData.size +
                (if (isFooter || isAnalysis||isFeedback) 1 else 0) +
                (if (isHeader) 1 else 0)
    }

    fun setHeader(view: View?) {
        headerView = view
    }

    fun setFooter(view: View) {
        footerView = view
    }

    fun setData(data: List<Item>?) {
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


    companion object {
        private val HEADER = 0x24//题干
        private val TEXT = 0xa6 //文本
        private val IMG = 0xb7//图片
        private val FOOTER = 0xff//解析
        private val DEFAULT = 0
    }
}