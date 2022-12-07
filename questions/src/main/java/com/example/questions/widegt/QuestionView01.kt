package com.example.questions.widegt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.questions.Item
import com.example.questions.QuestionInfo
import com.example.questions.R
import com.github.moon.RichText
import kotlin.properties.Delegates

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class QuestionView01 : LinearLayout {
    private var view by Delegates.notNull<View>()
    private var tvTitle by Delegates.notNull<RichText>()

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
//        val view = View.inflate(context, R.layout.questions_view, this)
        view = LayoutInflater.from(context).inflate(R.layout.question_01, this, true)
        init()
    }

    private fun init() {
        tvTitle = findViewById(R.id.tv_title)
    }

    fun setData(question: QuestionInfo) {
//        Utils.htmlText(question.stem() ?: "", tvTitle)
        tvTitle.apply {
            text(question.stem() ?: "")
        }
    }
    fun setData(question: Item) {
//        Utils.htmlText(question.stem() ?: "", tvTitle)
        tvTitle.apply {
            text(question.stem() ?: "")
        }
    }
}
