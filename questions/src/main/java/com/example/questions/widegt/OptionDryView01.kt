package com.example.questions.widegt

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.questions.Item
import com.example.questions.R
import com.example.questions.dadpter.QuestionAdapter
import kotlin.properties.Delegates

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class OptionDryView01 : LinearLayout {
    private var tvName by Delegates.notNull<TextView>()
    private var view by Delegates.notNull<View>()
    private var llSubjective by Delegates.notNull<View>()
    private var llRight by Delegates.notNull<View>()
    private var llError by Delegates.notNull<View>()
    private var total by Delegates.notNull<TextView>()
    private var position by Delegates.notNull<TextView>()
    private var recyclerDry by Delegates.notNull<RecyclerView>()

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
//        val view = View.inflate(context, R.layout.questions_view, this)
        view = LayoutInflater.from(context).inflate(R.layout.options_dry_01, this, true)
        init()
    }

    private fun init() {
        tvName = findViewById(R.id.tv_name)
        position = findViewById(R.id.tv_position)
        total = findViewById(R.id.tv_total)
        llSubjective = findViewById(R.id.ll_subjective)
        llRight = findViewById(R.id.ll_right)
        llError = findViewById(R.id.ll_error)
        recyclerDry = findViewById(R.id.recycler_dry)
        recyclerDry.layoutManager = LinearLayoutManager(context)
    }


    fun setData(str: String, totalCount: String, question: Item?, adapter: QuestionAdapter, isClick: Boolean = false) {
        question?.let {
            tvName.text = str
            position.text = question.sort() ?: ""// TODO: 2019/1/14 11:18 霍述雷 题号
            total.text = "/$totalCount"
            adapter.setHeader(getHeader(question, adapter, isClick))
            if (adapter.isFooter || adapter.isAnalysis) adapter.setFooter(getFooter(question, adapter.isAnalysis))
//            else adapter.setFooter(getFeedback(question, adapter.isAnalysis))
            recyclerDry.adapter = adapter
            adapter.setData(question.item())
            adapter.itemX = question
            llSubjective.visibility = if (adapter.isFooter && !adapter.isAnalysis) View.VISIBLE else View.GONE
            llRight.setOnClickListener {
                if (adapter.isAnalysis) return@setOnClickListener
                question.isright("1")
                adapter.select("1")
                it.setBackgroundResource(R.drawable.bg_shape_3_1482ff_ff)
                llError.setBackgroundResource(R.drawable.bg_shape_3_f7f7f7_ff)
            }
            llError.setOnClickListener {
                if (adapter.isAnalysis) return@setOnClickListener
                question.isright("0")
                adapter.select("0")
                it.setBackgroundResource(R.drawable.bg_shape_3_1482ff_ff)
                llRight.setBackgroundResource(R.drawable.bg_shape_3_f7f7f7_ff)
            }
        }
    }

    private fun getFeedback(question: Item, analysis: Boolean): View {
        return FeedbackView(context).apply {
            this.setData(analysis, question?.questionKey()?:"")
        } }


    private fun getFooter(question: Item, analysis: Boolean = false): View {

        return AnalysisView(context).apply {
            this.setData(analysis, question.analyze() ?: "",question.questionKey()?:"")
        }
    }


    private fun getHeader(
        question: Item,
        adapter: QuestionAdapter,
        click: Boolean = false
    ): View {
        return when {
            question.questionType() == "6" -> QuestionView04(context).apply {
                this.setData(question)
            }
            question.questionType() == "4" -> QuestionView05(context).apply {
                this.setData(question, click)
                this.select = {
                    if (!click) {
                        question.useranswer(it)
                        adapter.select(it)
                    }
                }
            }
            question.audioFile().isNullOrBlank() -> QuestionView01(context).apply {
                this.setData(question)
            }
            else -> QuestionView01(context).apply {
                this.setData(question)
            }
        }
    }
}
