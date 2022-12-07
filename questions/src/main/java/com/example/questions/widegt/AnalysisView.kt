package com.example.questions.widegt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.questions.QuestionsView
import com.example.questions.R
import com.github.moon.RichText
import kotlin.properties.Delegates

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class AnalysisView : LinearLayout {
    private var analysis by Delegates.notNull<TextView>()
    private var feedback by Delegates.notNull<TextView>()
    private var analysisContent by Delegates.notNull<RichText>()
    private var show = false

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.analysis, this, true)
        init()
    }

    private fun init() {
        analysis = findViewById(R.id.tv_analysis)
        feedback = findViewById(R.id.tv_feedback)
        analysisContent = findViewById(R.id.tv_analysis_content)
        analysis.setOnClickListener {
            if (show) return@setOnClickListener
            if (it.isSelected) {
                analysis.text = "查看解析"
                analysisContent.visibility = View.GONE
                it.isSelected = false

            } else {
                analysis.text = "解析"
                analysisContent.visibility = View.VISIBLE
                it.isSelected = true
            }
        }
    }

    fun setData(isShow: Boolean, str: String,key:String) {
        show = isShow
        this.analysis.isSelected = isShow
        if (isShow) {
            this.analysis.text = "解析"
            this.analysis.visibility = View.VISIBLE
            analysisContent.visibility = View.VISIBLE
            feedback.visibility = View.VISIBLE
        } else {
            this.analysis.text = "查看解析"
            this.analysis.visibility = View.GONE
            analysisContent.visibility = View.GONE
            feedback.visibility = View.GONE
        }
//        Utils.htmlText(str, analysisContent)
        analysisContent.text(str ?: "")
        feedback.setOnClickListener {
            QuestionsView.feedback(key)
        }
    }
}
