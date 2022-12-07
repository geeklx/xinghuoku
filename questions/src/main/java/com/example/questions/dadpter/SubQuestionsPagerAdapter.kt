package com.example.questions.dadpter

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.example.questions.Item
import com.example.questions.widegt.*

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/15.
 */
class SubQuestionsPagerAdapter(
        val mData: MutableList<Item> = mutableListOf(),
        val views: MutableMap<Int, View> = mutableMapOf(),
        val scrollTo: () -> Unit
) : PagerAdapter() {
    var name: String = ""
    var totalCount: String = ""
    private var isAnalysis = false
    override fun getCount(): Int {
        return mData.size
    }

    fun setData(data: List<Item>?, isAnalysis: Boolean = false) {
        this.isAnalysis = isAnalysis
        data?.let {
            mData.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun instantiateItem(group: ViewGroup, position: Int): Any {
        var view = views[position]
        if (view == null) {
            val item = mData[position]
            view = when {
                item.questionType() == "1" -> OptionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
//                    item.item()?.forEach {
//                        it.questionKey(item.questionKey())
//                    }
                    setData(name, totalCount, item, QuestionAdapter(isAnalysis = isAnalysis) {
                        item.useranswer(it)
                        scrollTo()
                    })
                }
                item.questionType() == "2" -> OptionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
//                    item.item()?.forEach {
//                        it.questionKey(item.questionKey())
//                    }
                    setData(name, totalCount, item, QuestionAdapter(isAnalysis = isAnalysis, isSingle = false) {
                        item.useranswer(it)
//                        scrollTo()
                    })

                }
                item.questionType() == "3" -> OptionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                    item.item()?.let {
                        for (i in 0 until it.size) {
                            if (i == 0) {
                                it[i].order("N")
                                it[i].content("错误")
                            }
                            if (i == 1) {
                                it[i].order("Y")
                                it[i].content("正确")
                            }
                        }
                    }
//                    item.item()?.forEach {
//                        it.questionKey(item.questionKey())
//                    }
                    setData(name, totalCount, item, QuestionAdapter(isAnalysis = isAnalysis) {
                        item.useranswer(it)
                        scrollTo()
                    })

                }
                item.questionType() == "4" -> OptionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
//                    item.item()?.forEach {
//                        it.questionKey(item.questionKey())
//                    }
                    setData(name, totalCount, item, QuestionAdapter(isFooter = false, isAnalysis = isAnalysis) {
                        item.useranswer(it)
                        scrollTo()
                    }, isAnalysis)

                }
                item.questionType() == "5" -> OptionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
//                    item.item()?.forEach {
//                        it.questionKey(item.questionKey())
//                    }
                    setData(name, totalCount, item, QuestionAdapter() {
                        item.useranswer(it)
                        scrollTo()
                    })

                }
                item.questionType() == "6" -> OptionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
//                    item.item()?.forEach {
//                        it.questionKey(item.questionKey())
//                    }
                    setData(name, totalCount, item, QuestionAdapter(isAnalysis = isAnalysis, isFooter = true) {
                        item.useranswer(it)
                        scrollTo()

                    })
                }
                else -> View(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                }
            }
            views[position] = view
        }
        group.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
        if (`object` is View) container.removeView(`object`)
    }
}
