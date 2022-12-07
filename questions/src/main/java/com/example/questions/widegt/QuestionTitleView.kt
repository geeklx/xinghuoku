package com.example.questions.widegt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.questions.Question
import com.example.questions.R
import kotlin.properties.Delegates

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class QuestionTitleView : LinearLayout {
    private var tvName by Delegates.notNull<TextView>()
    private var tvSub by Delegates.notNull<TextView>()
    private var tvSubContent by Delegates.notNull<TextView>()
    private var view by Delegates.notNull<View>()

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        view = LayoutInflater.from(context).inflate(R.layout.question_title, this, true)
        init()
    }

    private fun init() {
        tvName = findViewById(R.id.tv_name)
        tvSub = findViewById(R.id.tv_sub)
        tvSubContent = findViewById(R.id.tv_sub_content)
    }

    fun setData(question: Question, name: String? = null, isAnalysis: Boolean = false) {
        tvName.text = name ?: ""
        tvSub.text = question.groupName() ?: ""
        tvSubContent.text = question.description() ?: ""
    }
}
