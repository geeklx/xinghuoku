package com.example.questions

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.questions.dadpter.QuestionsPagerAdapter
import com.example.questions.utils.MediaPlayerUtlis
import com.example.questions.widegt.QuestionDryView01
import com.example.questions.widegt.QuestionDryView02
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import kotlin.properties.Delegates

/**
 * QuestionsPagerAdapter-QuestionDryView01-QuestionDryView02
 * 做题页面
 */
class QuestionsView : LinearLayout {
    //    private var adapter: QuestionsAdapter? = null
    private var pagerAdapter: QuestionsPagerAdapter? = null
    private var ivBack by Delegates.notNull<Toolbar>()
    private var viewPager by Delegates.notNull<ViewPager>()
    var ivAnswers by Delegates.notNull<ImageView>()
    var chTime by Delegates.notNull<Chronometer>()
    var onBackPressed = {}
    var userAnswers = {}
    var submit = {}
    var resInfo: (String, function: (String?) -> Unit) -> Unit = { _, _ -> }

    var lrcInfo: (String, function: (File) -> Unit) -> Unit = { _, _ -> }

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.questions_view, this, true)
        init()
    }

    private fun init() {
//        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
//        adapter = QuestionsAdapter()
//        recyclerView.adapter = adapter
        viewPager = findViewById<ViewPager>(R.id.view_pager)
        ivBack = findViewById(R.id.toolbar)
        ivAnswers = findViewById(R.id.iv_answers)
        chTime = findViewById(R.id.ch_time)
        ivBack.setNavigationOnClickListener {
            onBackPressed()
        }
        ivAnswers.setOnClickListener {
            userAnswers()
        }
        pagerAdapter = QuestionsPagerAdapter {
            val currentPage = viewPager.currentItem + 1
            if (currentPage < pagerAdapter?.count ?: 0) {
                viewPager.currentItem = currentPage
            } else {
                // : 2019/1/15 15:22 霍述雷 完成 做题
                submit()
            }
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                // : 2019/1/15 13:20 霍述雷 音频播放
                play(position)
            }

        })
        viewPager.adapter = pagerAdapter
//        assets()
    }

    fun setData(data: List<Question>?, name: String, isAnalysis: Boolean = false) {
//        adapter?.setData(data)
//        adapter?.name = "测试数据"
        pagerAdapter?.name = name
        pagerAdapter?.setData(data, isAnalysis)
        if (isAnalysis) {
            ivAnswers.visibility = View.GONE
            chTime.visibility = View.GONE
        }
        postDelayed({
            play(0, isAnalysis)
            if (!isAnalysis) {
                chTime.base = SystemClock.elapsedRealtime()
                chTime.start()
            }
        }, 500)
    }

    private fun play(position: Int, isAnalysis: Boolean = false) {
        MediaPlayerUtlis.reset()
        val view = pagerAdapter?.currentItem(position)
        val audioFile = pagerAdapter?.mData?.get(position)?.questionInfo()?.audioFile()
        val lrcUrl = pagerAdapter?.mData?.get(position)?.questionInfo()?.lrcUrl()
        if (audioFile.isNullOrBlank()) return
        resInfo(audioFile) {
            if (it.isNullOrBlank()) return@resInfo
            MediaPlayerUtlis.start(it,
                    onStart = {
                        if (view is QuestionDryView01) {
                            view.start(it)
                        }
                        if (view is QuestionDryView02) {
                            view.start(it)
                        }
                    },
                    onCompletion = {
                        if (view is QuestionDryView01) {
                            view.onCompletion()
                        }
                        if (view is QuestionDryView02) {
                            view.onCompletion()
                        }
                    },
                    onError = {

                    },
                    status = {
                        if (view is QuestionDryView01) {
                            view.status(it)
                        }
                        if (view is QuestionDryView02) {
                            view.status(it)
                        }
                    },
                    callBack = {
                        if (view is QuestionDryView01) {
                            view.progress(it)
                        }
                        if (view is QuestionDryView02) {
                            view.progress(it)
                        }
                    })
        }
        lrcInfo(lrcUrl!!) {
                if (view is QuestionDryView02) {
                    view.initLrcData(it)
                }
                if (view is QuestionDryView01) {
                    view.initLrcData(it)
                }
        }
    }


    fun assets() {
        val data = mutableListOf<Question>()
        try {
            val sb = StringBuilder()
            val bf = BufferedReader(InputStreamReader(context.assets.open("asaa.json")))
            var line = bf.readLine()
            while (line != null) {
                sb.append(line)
                line = bf.readLine()
            }
            val s = sb.toString().replace("\"item\": {", "\"itemAny\": {")
            val options: Questions? = Gson().fromJson(s, Questions::class.java)
            options?.let {
                it.questionlist?.let {
                    it.forEach {
                        //                        data.add(it)
                        val questionlist = it
                        it.questionlist()?.let {
                            it.forEach {
                                it.groupName(questionlist.groupName())
                            }
                            data.addAll(it)
                        }
                    }
                }
            }
            setData(data, "", true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private var millis = 0L
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (System.currentTimeMillis() - millis < 200)
                    return true
            }
            MotionEvent.ACTION_UP -> {
                if (System.currentTimeMillis() - millis > 200)
                    millis = System.currentTimeMillis()
            }
            else -> {

            }
        }

        return super.dispatchTouchEvent(ev)
    }

    override fun onDetachedFromWindow() {
        MediaPlayerUtlis.reset()// TODO: 2019/1/15 13:57 霍述雷 释放资源
        super.onDetachedFromWindow()
    }

    fun scrollToItem(sort: String) {
        pagerAdapter?.mData?.let {
            val list = it
            list.forEach lit@{
                val question = it
                if (question.questionInfo()?.sort() == sort) {
                    viewPager.currentItem = list.indexOf(question)
                    return@lit
                } else {
                    question.questionInfo()?.item()?.let {
                        val items = it
                        items.forEach {
                            val item = it
                            if (item.sort() == sort) {
                                viewPager.currentItem = list.indexOf(question)
                                val index = items.indexOf(item)
                                val view = pagerAdapter?.currentItem(viewPager.currentItem)
                                (view as? QuestionDryView02)?.scrollToItem(index)
                                return@lit
                            }
                        }
                    }
                }

            }
        }
    }

    fun pause() {
        MediaPlayerUtlis.pause()
    }

    fun start() {
        MediaPlayerUtlis.start()
    }
    companion object {
        var feedback:(String?)->Unit = {}
    }
}
