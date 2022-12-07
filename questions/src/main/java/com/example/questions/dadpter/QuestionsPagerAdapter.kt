package com.example.questions.dadpter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.questions.Question
import com.example.questions.QuestionlistBean
import com.example.questions.widegt.QuestionDryView01
import com.example.questions.widegt.QuestionDryView02
import com.example.questions.widegt.QuestionTitleView

/**
 * QuestionsView-QuestionsPagerAdapter-QuestionDryView01-QuestionDryView02
 * 做题viewpater
 */
class QuestionsPagerAdapter(
        val mData: MutableList<Question> = mutableListOf(),
        val views: MutableMap<Int, View> = mutableMapOf(),
        val scrollTo: () -> Unit
) : PagerAdapter() {
    var name: String = ""
    var totalCount: String = ""
    private var isAnalysis = false
    override fun getCount(): Int {
        return mData.size
    }

    fun setData(data: List<Question>?, isAnalysis: Boolean = false) {
        this.isAnalysis = isAnalysis
//        val questionInfo = data?.last()?.questionInfo()
//        totalCount = questionInfo?.sort() ?: questionInfo?.item()?.last()?.sort() ?: ""
        totalCount = "${data?.size}"
        data?.let {
            mData.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun instantiateItem(group: ViewGroup, position: Int): Any {
        var view = views[position]
        if (view == null) {
            val question = mData[position]
            view = when {
                question.questionType() == null -> QuestionTitleView(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                    setData(question, if (position == 0) name else null, isAnalysis)
                }
                question.questionType() == "1" -> QuestionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                    setData(name, totalCount, question, QuestionAdapter(isAnalysis = isAnalysis) {
                        question.questionInfo()?.useranswer(it)
                        scrollTo()
                    })
                }
                question.questionType() == "2" -> QuestionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                    setData(name, totalCount, question, QuestionAdapter(isAnalysis = isAnalysis, isSingle = false) {
                        question.questionInfo()?.useranswer(it)
//                        scrollTo()
                    })
                }
                question.questionType() == "3" -> QuestionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                    question.questionInfo()?.item()?.let {
                        if (it.isNotEmpty()) {
                            val item = it[0]
                            item.order("N")
                            item.content("错误")
                        } else {
                            val item = QuestionlistBean.QuestionInfoBean.ItemBean()
                            item.order("N")
                            item.content("错误")
                            (it as? MutableList)?.add(item)
                        }
                        if (it.size > 1) {
                            val item = it[1]
                            item.order("Y")
                            item.content("正确")
                        } else {
                            val item = QuestionlistBean.QuestionInfoBean.ItemBean()
                            item.order("Y")
                            item.content("正确")
                            (it as? MutableList)?.add(item)
                        }
                    }
                    setData(name, totalCount, question, QuestionAdapter(isAnalysis = isAnalysis) {
                        question.questionInfo()?.useranswer(it)
                        scrollTo()
                    })
                }
                question.questionType() == "4" -> QuestionDryView02(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                    question.questionInfo()?.item()?.let {
                        it.forEach {
                            it.questionType("4")
                            it.analyze(question.questionInfo()?.analyze())
                        }
                    }
                    setData(name, totalCount, question, QuestionAdapter(isFooter = false, isFeedback = false) {
                        //                        question.questionInfo()?.useranswer(it)
                        scrollTo()
                    }, isAnalysis)
                    scrollTo = {
                        question.questionInfo()?.item()?.forEach {
                            question.questionInfo()?.useranswer(question.questionInfo()?.useranswer() + it.useranswer())
                        }
                        scrollTo()
                    }
                }
                question.questionType() == "5" -> QuestionDryView02(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                    setData(name, totalCount, question, QuestionAdapter(isFooter = false, isFeedback = false) {
                        question.questionInfo()?.useranswer(it)
                        scrollTo()
                    }, isAnalysis)
                    scrollTo = {
                        scrollTo()
                    }
                }
                question.questionType() == "6" -> QuestionDryView01(group.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, -1)
                    setData(name,
                            totalCount,
                            question,
                            QuestionAdapter(isAnalysis = isAnalysis, isFooter = true) {
                                question.questionInfo()?.useranswer(it)
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

    fun currentItem(position: Int): View? {
        return views[position]
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (`object` is View) container.removeView(`object`)
    }

}
