package com.example.questions.widegt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.questions.Item
import com.example.questions.R
import org.jetbrains.anko.backgroundResource
import kotlin.properties.Delegates

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class QuestionView05 : LinearLayout {
    private var option by Delegates.notNull<EditText>()
    private var view by Delegates.notNull<View>()
    private var btCommit by Delegates.notNull<Button>()
    var select: ((String) -> Unit)? = null

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        view = LayoutInflater.from(context).inflate(R.layout.question_05, this, true)
        init()
    }

    private fun init() {
        option = findViewById(R.id.tv_option)
        btCommit = findViewById(R.id.bt_commit)
        btCommit.setOnClickListener {
            select?.invoke(option.text.toString().trim())
        }
    }

    fun setData(item: Item, analysis: Boolean) {
        if (analysis) {
            option.isFocusable = false
            option.setText(item.useranswer()?:"")
            if (item.useranswer()==item.answer()){
                option.backgroundResource=R.drawable.bg_shape_3_4cd964_ff
            }else{
                option.backgroundResource=R.drawable.bg_shape_3_fa7062_ff
            }
        }
    }


}
