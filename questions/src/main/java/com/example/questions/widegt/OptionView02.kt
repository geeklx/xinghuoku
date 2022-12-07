package com.example.questions.widegt

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.questions.Item
import com.example.questions.R
import org.jetbrains.anko.textColor
import kotlin.properties.Delegates

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/8.
 */
class OptionView02 : LinearLayout {
    private var option by Delegates.notNull<TextView>()
    private var view by Delegates.notNull<View>()
    private var optionContent by Delegates.notNull<ImageView>()

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        view = LayoutInflater.from(context).inflate(R.layout.option_02, this, true)
        init()
    }

    private fun init() {
        option = findViewById(R.id.tv_option)
        optionContent = findViewById(R.id.tv_option_content)
    }

    fun setData(item: Item, userAnswer: String?, rightAnswer: String?, analysis: Boolean = false) {
        option.text = item.order() ?: ""
        Glide.with(context)
            .load(
                item.imgs() ?: "https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg"
            )// TODO: 2019/1/14 14:37 霍述雷 moren
            .into(optionContent)

        if (!analysis) return
        if (item.order() == userAnswer) {
            error()
        }
        if (item.order() == rightAnswer) {
            right()
        }
    }

    fun select() {
        view.setBackgroundResource(R.drawable.bg_shape_3_1482ff_ff)
        option.setBackgroundResource(R.drawable.bg_shape_3_1482ff)
        option.textColor = Color.parseColor("#ffffff")
    }

    fun unselect() {
        view.setBackgroundResource(R.drawable.bg_shape_3_1482ff_ff)
        option.setBackgroundResource(R.drawable.bg_shape_3_1482ff)
        option.textColor = Color.parseColor("#1482ff")
    }

    fun error() {
        view.setBackgroundResource(R.drawable.bg_shape_3_fa7062_ff)
        option.setBackgroundResource(R.drawable.bg_shape_3_fa7062)
        option.textColor = Color.parseColor("#ffffff")
    }

    fun right() {
        view.setBackgroundResource(R.drawable.bg_shape_3_4cd964_ff)
        option.setBackgroundResource(R.drawable.bg_shape_3_4cd964)
        option.textColor = Color.parseColor("#ffffff")
    }


}
