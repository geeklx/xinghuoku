package com.example.questions.widegt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent.*
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.questions.Question
import com.example.questions.QuestionInfo
import com.example.questions.R
import com.example.questions.dadpter.QuestionAdapter
import com.example.questions.dadpter.SubQuestionsPagerAdapter
import org.jetbrains.anko.dip
import java.io.File
import kotlin.properties.Delegates

/**
 * QuestionsView-QuestionsPagerAdapter-QuestionDryView01-QuestionDryView02
 * 非主观题页面，做题页面
 */
class QuestionDryView02 : LinearLayout {
    private var tvName by Delegates.notNull<TextView>()
    private var tvTitle by Delegates.notNull<TextView>()
    private var view by Delegates.notNull<View>()
    private var total by Delegates.notNull<TextView>()
    private var tvPosition by Delegates.notNull<TextView>()
    private var tvOptionsName by Delegates.notNull<TextView>()
    private var tvOptionsNum by Delegates.notNull<TextView>()
    private var tvOptionsTotal by Delegates.notNull<TextView>()
    private var viewOptions by Delegates.notNull<View>()
    private var llOptions by Delegates.notNull<View>()
    private var recyclerDry by Delegates.notNull<RecyclerView>()
    private var viewPagerOptions by Delegates.notNull<ViewPager>()
    private var qv_audio by Delegates.notNull<QuestionView02>()
    private var header: View? = null
    private val tabMaxHeight by lazy { dip(450) }
    private val tabMinHeight by lazy { dip(50) }
    var scrollTo: (() -> Unit)? = null
    private var subAdapter: SubQuestionsPagerAdapter? = null

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.question_dry_02, this, true)
        init()
    }

    private fun init() {
        tvTitle = findViewById(R.id.tv_title)
        tvName = findViewById(R.id.tv_name)
        tvPosition = findViewById(R.id.tv_position)
        total = findViewById(R.id.tv_total)
        recyclerDry = findViewById(R.id.recycler_dry)
        viewPagerOptions = findViewById(R.id.view_pager_options)
        tvOptionsName = findViewById(R.id.tv_options_name)
        tvOptionsNum = findViewById(R.id.tv_options_num)
        tvOptionsTotal = findViewById(R.id.tv_options_total)
        viewOptions = findViewById(R.id.view_options)
        llOptions = findViewById(R.id.ll_options)
        qv_audio = findViewById(R.id.qv_audio)
        recyclerDry.layoutManager = LinearLayoutManager(context)
        viewPagerOptions.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                tvOptionsNum.text = "${position + 1}"
//                val sort = subAdapter?.mData?.get(position)?.sort()
//                if (sort.isNullOrBlank()) return
//                tvPosition.text = sort
            }

        })
//        drop()
        subAdapter = SubQuestionsPagerAdapter {
            val currentPage = viewPagerOptions.currentItem + 1
            if (currentPage < subAdapter?.count ?: 0) {
                viewPagerOptions.currentItem = currentPage
            } else {
                scrollTo?.invoke()
            }
        }
        viewPagerOptions.adapter = subAdapter
    }
    fun setData(
            str: String,
            totalCount: String,
            question: Question,
            adapter: QuestionAdapter,
            isAnalysis: Boolean = false
    ) {
        question.questionInfo()?.let {
            tvName.text = str
            tvPosition.text = question.sort()
            total.text = "/$totalCount"
            if (it.questionType() != "6" && !it.audioFile().isNullOrEmpty()) {
                qv_audio.visibility = VISIBLE
                recyclerDry.visibility = GONE
                qv_audio.setData(it, isAnalysis)
            } else {
                qv_audio.visibility = GONE
                recyclerDry.visibility = VISIBLE
                header = getHeader(it)
                adapter.setHeader(header)
                if (adapter.isFooter || adapter.isAnalysis) adapter.setFooter(getFooter(it, isAnalysis))
//                else adapter.setFooter(getFeedback(it, adapter.isAnalysis))
                recyclerDry.adapter = adapter
            }
            subAdapter?.setData(it.item(), isAnalysis)
            tvOptionsTotal.text = "/${it.item()?.size ?: ""}"

        }
        tvTitle.text = question.groupName() ?: ""
        tvOptionsName.text = question.groupName() ?: ""
        tvOptionsNum.text = "1"
        if (question.groupName().isNullOrBlank()) {
            tvTitle.visibility = View.GONE
        } else {
            tvTitle.visibility = View.VISIBLE
        }
    }

    fun setData(
            str: String,
            totalCount: String,
            question: QuestionInfo,
            adapter: QuestionAdapter,
            isAnalysis: Boolean = false
    ) {
        question?.let {
            tvName.text = ""
            tvPosition.text = ""
            total.text = ""
            if (it.questionType() != "6" && !it.audioFile().isNullOrEmpty()) {
                qv_audio.visibility = VISIBLE
                recyclerDry.visibility = GONE
                qv_audio.setData(it, isAnalysis)
            } else {
                qv_audio.visibility = GONE
                recyclerDry.visibility = VISIBLE
                header = getHeader(it)
                adapter.setHeader(header)
                if (adapter.isFooter || adapter.isAnalysis) adapter.setFooter(getFooter(it, isAnalysis))
                recyclerDry.adapter = adapter
            }
            subAdapter?.setData(it.item(), isAnalysis)
            tvOptionsTotal.text = "/${it.item()?.size ?: ""}"
        }
        tvOptionsName.text = ""
        tvOptionsNum.text = "1"
        tvTitle.visibility = View.GONE
    }

    private fun getFeedback(question: QuestionInfo, analysis: Boolean): View {
        return FeedbackView(context).apply {
            this.setData(analysis, question.questionKey() ?: "")
        }
    }

    private fun getFooter(question: QuestionInfo, analysis: Boolean = false): View {

        return AnalysisView(context).apply {
            this.setData(analysis, question.analyze() ?: "", question.questionKey() ?: "")
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

    private fun drop() {
        var params: ViewGroup.LayoutParams? = null
        var y = 0f
        viewOptions.setOnTouchListener { v, event ->
            when (event.action) {
                ACTION_DOWN -> {
                    params = llOptions.layoutParams
                    y = event.rawY
                }
                ACTION_MOVE -> {
                    params!!.height += (y - event.rawY).toInt()//计算滑动距离
                    y = event.rawY
                    if (params!!.height in tabMinHeight..tabMaxHeight) {//判断是否超出边界
                        llOptions.layoutParams = params //重新绘制
                    }
                }
                ACTION_UP -> {
                    if (params!!.height < tabMinHeight) params!!.height = tabMinHeight
                    if (params!!.height > tabMaxHeight) params!!.height = tabMaxHeight
                    llOptions.layoutParams = params
                }
            }
            true
        }
    }

    fun initLrcData(lrcFile: File) {
//        (header as? QuestionView02)?.initLrcData(lrcFile)
        qv_audio.initLrcData(lrcFile)
    }

    fun start(duration: Int) {
//        (header as? QuestionView02)?.start(duration)
        qv_audio.start(duration)
    }

    fun onCompletion() {
//        (header as? QuestionView02)?.onCompletion()
        qv_audio.onCompletion()
    }

    fun status(isPlay: Boolean) {
//        (header as? QuestionView02)?.status(isPlay)
        qv_audio.status(isPlay)
    }

    fun progress(progress: Int) {
//        (header as? QuestionView02)?.progress(progress)
        qv_audio.progress(progress)
    }

    fun scrollToItem(index: Int) {
        viewPagerOptions.currentItem = index
    }

}
