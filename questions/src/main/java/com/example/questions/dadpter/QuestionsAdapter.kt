//package com.example.questions.dadpter
//
//import android.view.View
//import android.view.ViewGroup
//import com.example.questions.Question
//import com.example.questions.widegt.QuestionDryView01
//import com.example.questions.widegt.QuestionDryView02
//import com.example.questions.widegt.QuestionTitleView
//
///**
// * 创建者： 霍述雷
// * 时间：  2019/1/8.
// */
//class QuestionsAdapter : BaseAdapter<Question>() {
//    var name: String = ""
//    var totalCount: String = ""
//    override fun getItemCount(): Int = mData.size
//
//    override fun selectedItem(index: Int) {
//
//    }
//
//    override fun setData(data: List<Question>?) {
//        totalCount = data?.last()?.questionInfo()?.sort() ?: ""
//        super.setData(data)
//    }
//
//    private fun title(position: Int, view: QuestionTitleView?) {
//        val question = mData[position]
//        view?.setData(question, if (position == 0) name else null)
//    }
//
//    private fun option(question: Question, view: QuestionDryView01?) {
//        view?.setData(name, totalCount, question.questionInfo(), QuestionAdapter{})
//    }
//
//    private fun options(question: Question, view: QuestionDryView01?) {
//        view?.setData(name, totalCount, question.questionInfo(), QuestionAdapter(isSingle = false){})
//    }
//
//    private fun judge(question: Question, view: QuestionDryView01?) {
//        question.questionInfo()?.item()?.let {
//            for (i in 0 until it.size) {
//                if (i == 0) {
//                    val item = it[i]
//                    item.order("N")
//                    item.content("错误")
//                }
//                if (i == 1) {
//                    val item = it[i]
//                    item.order("Y")
//                    item.content("正确")
//                }
//            }
//        }
//        view?.setData(name, totalCount, question.questionInfo(), QuestionAdapter(){})
//    }
//
//    private fun fillBlank(question: Question, view: QuestionDryView02?) {
//        view?.setData(name, totalCount, question, QuestionAdapter(){})
//    }
//    private fun material(question: Question, view: QuestionDryView02?) {
//        view?.setData(name, totalCount, question, QuestionAdapter(){})
//    }
//    private fun subjective(question: Question, view: QuestionDryView01?) {
//        view?.setData(name, totalCount, question.questionInfo(), QuestionAdapter(isFooter = true){})
//    }
//
//    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): Holder {
//        return when (viewType) {
//            TITLE -> Holder(QuestionTitleView(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            OPTION -> Holder(QuestionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            OPTIONS -> Holder(QuestionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            JUDGE -> Holder(QuestionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            FILLBLANK -> Holder(QuestionDryView02(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//                scrollTo={
//
//                }
//            })
//            MATERIAL -> Holder(QuestionDryView02(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//                scrollTo={
//
//                }
//            })
//            SUBJECTIVE -> Holder(QuestionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            else -> Holder(View(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//        }
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        return when (holder.itemViewType) {
//            TITLE -> {
//                title(position, holder.itemView as? QuestionTitleView)
//            }
//            OPTION -> {
//                option(mData[position], holder.itemView as? QuestionDryView01)
//            }
//            OPTIONS -> {
//                options(mData[position], holder.itemView as? QuestionDryView01)
//            }
//            JUDGE -> {
//                judge(mData[position], holder.itemView as? QuestionDryView01)
//            }
//            FILLBLANK -> {
//                fillBlank(mData[position], holder.itemView as? QuestionDryView02)
//            }
//            MATERIAL -> {
//                material(mData[position], holder.itemView as? QuestionDryView02)
//            }
//            SUBJECTIVE -> {
//                subjective(mData[position], holder.itemView as? QuestionDryView01)
//            }
//            else -> {
//            }
//        }
//
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        if (mData[position].questionType() == null) return TITLE
//        if (mData[position].questionType() == "1") return OPTION
//        if (mData[position].questionType() == "2") return OPTIONS
//        if (mData[position].questionType() == "3") return JUDGE
//        if (mData[position].questionType() == "4") return FILLBLANK
//        if (mData[position].questionType() == "5") return MATERIAL
//        if (mData[position].questionType() == "6") return SUBJECTIVE
//        return DEFAULT
//    }
//
//    companion object {
//        private val TITLE = 0x25
//        private val OPTION = 0xa6 //选择
//        private val OPTIONS = 0xb7//多选
//        private val JUDGE = 0xc8//判断
//        private val FILLBLANK = 0xd9//填空
//        private val MATERIAL = 0xe0//材料
//        private val SUBJECTIVE = 0xf1//主观
//        private val DEFAULT = 0
//    }
//}
