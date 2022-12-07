package com.example.questions.widegt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.questions.R

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class QuestionView03 : LinearLayout {
    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
//        val view = View.inflate(context, R.layout.questions_view, this)
        LayoutInflater.from(context).inflate(R.layout.question_03,this,true)
        init()
    }

    private fun init() {

    }

}
