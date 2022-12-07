package com.example.questions.widegt

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.questions.Item
import com.example.questions.R
import com.github.moon.RichText
import org.jetbrains.anko.textColor
import kotlin.properties.Delegates

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class OptionView01 : LinearLayout {
    private var option by Delegates.notNull<TextView>()
    private var view by Delegates.notNull<View>()
    private var llOptions by Delegates.notNull<View>()
    private var optionContent by Delegates.notNull<RichText>()

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        view = LayoutInflater.from(context).inflate(R.layout.option_01, this, true)
        init()
    }

    private fun init() {
        option = findViewById(R.id.tv_option)
        llOptions = findViewById(R.id.ll_options)
        optionContent = findViewById(R.id.tv_option_content)
//        view.setOnClickListener {
//            it.setBackgroundResource(R.drawable.bg_shape_3_1482ff_ff)
//            option.setBackgroundResource(R.drawable.bg_shape_3_1482ff)
//        }
    }

    fun setData(item: Item, userAnswer: String?, rightAnswer: String?, analysis: Boolean = false) {
        option.text = item.order() ?: ""
//        Utils.htmlText(item.content() ?: "", optionContent)
        optionContent.text(item.content() ?: "")
        if (!analysis) return
        if ((item.order() ?: "NULL") in (userAnswer ?: "")) {
            error()
        }
        if ((item.order() ?: "NULL") in (rightAnswer ?: "")) {
            right()
        }
    }

    fun select() {
        llOptions.setBackgroundResource(R.drawable.bg_shape_3_1482ff_ff)
        option.setBackgroundResource(R.drawable.bg_shape_3_1482ff)
        option.textColor = Color.parseColor("#ffffff")
    }

    fun unselect() {
        llOptions.setBackgroundResource(R.drawable.bg_shape_3_f7f7f7_ff)
        option.setBackgroundResource(R.drawable.bg_shape_3_f7f7f7_ff)
        option.textColor = Color.parseColor("#1482ff")
    }

    fun error() {
        llOptions.setBackgroundResource(R.drawable.bg_shape_3_fa7062_ff)
        option.setBackgroundResource(R.drawable.bg_shape_3_fa7062)
        option.textColor = Color.parseColor("#ffffff")
    }

    fun right() {
        llOptions.setBackgroundResource(R.drawable.bg_shape_3_4cd964_ff)
        option.setBackgroundResource(R.drawable.bg_shape_3_4cd964)
        option.textColor = Color.parseColor("#ffffff")
    }


}
