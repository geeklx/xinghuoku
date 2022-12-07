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
class FeedbackView : LinearLayout {
    private var feedback by Delegates.notNull<TextView>()

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.feedback, this, true)
        init()
    }

    private fun init() {
        feedback = findViewById(R.id.tv_feedback)

    }

    fun setData(isShow: Boolean, str: String) {
        feedback.setOnClickListener {
            QuestionsView.feedback(str)
        }
    }

}
