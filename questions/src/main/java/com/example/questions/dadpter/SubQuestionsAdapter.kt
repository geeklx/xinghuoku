//package com.example.questions.dadpter
//
//import android.view.View
//import android.view.ViewGroup
//import com.example.questions.Item
//import com.example.questions.Question
//import com.example.questions.widegt.OptionDryView01
//import com.example.questions.widegt.QuestionDryView01
//import com.example.questions.widegt.QuestionDryView02
//import com.example.questions.widegt.QuestionTitleView
//
///**
// * 创建者： 霍述雷
// * 时间：  2019/1/8.
// */
//class SubQuestionsAdapter : BaseAdapter<Item>() {
//    var name: String = ""
//    var totalCount: String = ""
//    override fun getItemCount(): Int = mData.size
//
//    override fun selectedItem(index: Int) {
//
//    }
//
//    override fun setData(data: List<Item>?) {
//        totalCount = "$itemCount"
//        super.setData(data)
//    }
//
//
//    private fun option(question: Item, view: OptionDryView01?) {
//        view?.setData(name, totalCount, question, QuestionAdapter{
//            question.useranswer()
//        })
//    }
//
//    private fun options(question: Item, view: OptionDryView01?) {
//        view?.setData(name, totalCount, question, QuestionAdapter{
//
//        })
//    }
//
//    private fun judge(question: Item, view: OptionDryView01?) {
//        question.item()?.let {
//            for (i in 0 until it.size) {
//                if (i == 0) {
//                    it[i].order("N")
//                    it[i].content("错误")
//                }
//                if (i == 1) {
//                    it[i].order("Y")
//                    it[i].content("正确")
//                }
//            }
//        }
//        view?.setData(name, totalCount, question, QuestionAdapter{
//
//        })
//    }
//
//    private fun fillBlank(question: Item, view: OptionDryView01?) {
//        view?.setData(name, totalCount, question, QuestionAdapter{
//
//        })
//    }
//
//    private fun material(question: Item, view: OptionDryView01?) {
//        view?.setData(name, totalCount, question, QuestionAdapter{
//
//        })
//    }
//
//    private fun subjective(question: Item, view: OptionDryView01?) {
//        view?.setData(name, totalCount, question, QuestionAdapter(isFooter = true){
//
//        })
//    }
//
//    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): Holder {
//        return when (viewType) {
//            OPTION -> Holder(OptionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            OPTIONS -> Holder(OptionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            JUDGE -> Holder(OptionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            FILLBLANK -> Holder(OptionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            MATERIAL -> Holder(OptionDryView01(group.context).apply {
//                layoutParams = ViewGroup.LayoutParams(-1, -1)
//            })
//            SUBJECTIVE -> Holder(OptionDryView01(group.context).apply {
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
//            OPTION -> {
//                option(mData[position], holder.itemView as? OptionDryView01)
//            }
//            OPTIONS -> {
//                options(mData[position], holder.itemView as? OptionDryView01)
//            }
//            JUDGE -> {
//                judge(mData[position], holder.itemView as? OptionDryView01)
//            }
//            FILLBLANK -> {
//                fillBlank(mData[position], holder.itemView as? OptionDryView01)
//            }
//            MATERIAL -> {
//                material(mData[position], holder.itemView as? OptionDryView01)
//            }
//            SUBJECTIVE -> {
//                subjective(mData[position], holder.itemView as? OptionDryView01)
//            }
//            else -> {
//            }
//        }
//
//    }
//
//    override fun getItemViewType(position: Int): Int {
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
//        private val OPTION = 0xa6 //选择
//        private val OPTIONS = 0xb7//多选
//        private val JUDGE = 0xc8//判断
//        private val FILLBLANK = 0xd9//填空
//        private val MATERIAL = 0xe0//材料
//        private val SUBJECTIVE = 0xf1//主观
//        private val DEFAULT = 0
//    }
//}
