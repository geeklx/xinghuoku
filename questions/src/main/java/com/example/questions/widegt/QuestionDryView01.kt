package com.example.questions.widegt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questions.Question
import com.example.questions.QuestionInfo
import com.example.questions.R
import com.example.questions.dadpter.QuestionAdapter
import java.io.File
import kotlin.properties.Delegates

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class QuestionDryView01 : LinearLayout {
    private var tvName by Delegates.notNull<TextView>()
    private var tvTitle by Delegates.notNull<TextView>()
    private var llSubjective by Delegates.notNull<View>()
    private var llRight by Delegates.notNull<View>()
    private var llError by Delegates.notNull<View>()
    private var view by Delegates.notNull<View>()
    private var total by Delegates.notNull<TextView>()
    private var position by Delegates.notNull<TextView>()
    private var recyclerDry by Delegates.notNull<RecyclerView>()
    private var qv_audio by Delegates.notNull<QuestionView02>()
    private var header: View? = null

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
//        val view = View.inflate(context, R.layout.questions_view, this)
        view = LayoutInflater.from(context).inflate(R.layout.question_dry_01, this, true)
        init()
    }

    private fun init() {
        tvName = findViewById(R.id.tv_name)
        tvTitle = findViewById(R.id.tv_title)
        position = findViewById(R.id.tv_position)
        total = findViewById(R.id.tv_total)
        llSubjective = findViewById(R.id.ll_subjective)
        llRight = findViewById(R.id.ll_right)
        llError = findViewById(R.id.ll_error)
        recyclerDry = findViewById(R.id.recycler_dry)
        qv_audio = findViewById(R.id.qv_audio)
        recyclerDry.layoutManager = LinearLayoutManager(context)
    }

    fun setData(str: String, totalCount: String, question: Question?, adapter: QuestionAdapter) {
        question?.questionInfo()?.let {
            val questionInfo = it
            tvName.text = str
            position.text = question.sort() ?: ""// TODO: 2019/1/14 11:18 霍述雷 题号
            total.text = "/$totalCount"
            if (adapter.isAnalysis && "1" == it.questionType() && !it.audioFile().isNullOrEmpty() && !it.lrcUrl().isNullOrEmpty()) {
                qv_audio.visibility = VISIBLE
                qv_audio.setData(it, adapter.isAnalysis)
            } else {
                qv_audio.visibility = GONE
                header = getHeader(questionInfo)
                adapter.setHeader(header)
            }
            if (adapter.isFooter || adapter.isAnalysis) adapter.setFooter(getFooter(questionInfo, adapter.isAnalysis))
//            else adapter.setFooter(getFeedback(questionInfo, adapter.isAnalysis))
            recyclerDry.adapter = adapter
            adapter.setData(questionInfo.item())
            adapter.questionInfo = questionInfo
            llSubjective.visibility = if (adapter.isFooter && !adapter.isAnalysis) View.VISIBLE else View.GONE
            llRight.setOnClickListener {
                if (adapter.isAnalysis) return@setOnClickListener
                questionInfo.isright("1")
                adapter.select("1")
                it.setBackgroundResource(R.drawable.bg_shape_3_1482ff_ff)
                llError.setBackgroundResource(R.drawable.bg_shape_3_f7f7f7_ff)
            }
            llError.setOnClickListener {
                if (adapter.isAnalysis) return@setOnClickListener
                questionInfo.isright("0")
                adapter.select("0")
                it.setBackgroundResource(R.drawable.bg_shape_3_1482ff_ff)
                llRight.setBackgroundResource(R.drawable.bg_shape_3_f7f7f7_ff)
            }
        }
        tvTitle.text = question?.groupName() ?: ""
        if (question?.groupName().isNullOrBlank()){
            tvTitle.visibility=View.GONE
        }else{
            tvTitle.visibility=View.VISIBLE
        }
    }
    private fun getFeedback(question: QuestionInfo, analysis: Boolean): View {
        return FeedbackView(context).apply {
            this.setData(analysis, question.questionKey() ?: "")
        } }

    private fun getFooter(question: QuestionInfo, analysis: Boolean): View {

        return AnalysisView(context).apply {
            this.setData(analysis, question.analyze() ?: "",question.questionKey()?:"")
        }
    }

    private fun getHeader(question: QuestionInfo): View {
        return when {
            question.questionType() == "6" -> QuestionView04(context).apply {
                this.setData(question)
            }
            question.audioFile().isNullOrBlank() -> QuestionView01(context).apply {
                this.setData(question)
            }
            else -> QuestionView02(context).apply {
                this.setData(question)
            }
        }
    }

    fun start(duration: Int) {
        if (qv_audio.visibility == VISIBLE) {
            qv_audio.start(duration)
        } else {
            (header as? QuestionView02)?.start(duration)
        }
    }

    fun initLrcData(lrcFile: File) {
        if (qv_audio.visibility == VISIBLE) {
            qv_audio.initLrcData(lrcFile)
        } else {
            (header as? QuestionView02)?.initLrcData(lrcFile)
        }
    }

    fun onCompletion() {
        if (qv_audio.visibility == VISIBLE) {
            qv_audio.onCompletion()
        } else {
            (header as? QuestionView02)?.onCompletion()
        }
    }

    fun status(isPlay: Boolean) {
        if (qv_audio.visibility == VISIBLE) {
            qv_audio.status(isPlay)
        } else {
            (header as? QuestionView02)?.status(isPlay)
        }
    }

    fun progress(progress: Int) {
        if (qv_audio.visibility == VISIBLE) {
            qv_audio.progress(progress)
        } else {
            (header as? QuestionView02)?.progress(progress)
        }
    }
}
