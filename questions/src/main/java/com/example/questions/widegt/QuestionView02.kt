package com.example.questions.widegt

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.example.questions.Item
import com.example.questions.QuestionInfo
import com.example.questions.R
import com.example.questions.utils.MediaPlayerUtlis
import com.example.questions.utils.MyLrcDataBuilder
import com.github.moon.RichText
import com.hw.lrcviewlib.ILrcViewSeekListener
import com.hw.lrcviewlib.IRowsParser
import com.hw.lrcviewlib.LrcRow
import com.hw.lrcviewlib.LrcView
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.runOnUiThread
import java.io.File
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

/**
 * QuestionDryView02事件
 *做题音频播放页面
 */
class QuestionView02 : LinearLayout {
    private var tvTitle by Delegates.notNull<RichText>()
    private var view by Delegates.notNull<View>()
    private var ivPlay by Delegates.notNull<ImageView>()
    private var tvCurrentTime by Delegates.notNull<TextView>()
    private var tvTotalTime by Delegates.notNull<TextView>()
    private var seekBar by Delegates.notNull<SeekBar>()
    private var lrc_view by Delegates.notNull<LrcView>()

    private val sdf by lazy { SimpleDateFormat("mm:ss") }

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
//        val view = View.inflate(context, R.layout.questions_view, this)
        view = LayoutInflater.from(context).inflate(R.layout.question_02, this, true)
        init()
    }

    /**
     * 初始化歌词显示相关
     */
    private val parser by lazy {
        object : IRowsParser {
            override fun parse(lrcRowDada: String): MutableList<LrcRow>? {
                try {
                    val lastIndexOfRightBracket = lrcRowDada.indexOf("]")
                    val content = lrcRowDada.substring(lastIndexOfRightBracket + 1, lrcRowDada.length)
                    val times = lrcRowDada.substring(0, lastIndexOfRightBracket + 1).replace("[", "-").replace("]", "-")
                    val arrTimes = times.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val listTimes = mutableListOf<LrcRow>()
                    val var10 = arrTimes.size
                    for (var11 in 0 until var10) {
                        val temp = arrTimes[var11]
                        if (temp.trim { it <= ' ' }.isNotEmpty()) {
                            val lrcRow = LrcRow(content, temp, this.timeConvert(temp))
                            listTimes.add(lrcRow)
                        }
                    }
                    return listTimes
                } catch (var14: Exception) {
                    return null
                }

            }

            private fun timeConvert(timeString: String): Long {
                var timeStr = timeString
                timeStr = timeStr.replace('.', ':')
                val times = timeStr.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                return when {
                    times.size == 4 -> (Integer.valueOf(times[0]) * 60 * 60 * 1000 + Integer.valueOf(times[1]) * 60 * 1000 + Integer.valueOf(times[2]) * 1000 + Integer.valueOf(times[3])).toLong()
                    times.size == 3 -> (Integer.valueOf(times[0]) * 60 * 1000 + Integer.valueOf(times[1]) * 1000 + Integer.valueOf(times[2])).toLong()
                    times.size == 2 -> (Integer.valueOf(times[0]) * 60 * 1000 + Integer.valueOf(times[1]) * 1000).toLong()
                    times.size == 1 -> (Integer.valueOf(times[0]) * 1000).toLong()
                    else -> 0
                }
            }

        }
    }

    /**
     * 2019/6/17 新增 --- 修改字幕显示方式
     */
    private var lrcListener = ILrcViewSeekListener { _, time ->
        MediaPlayerUtlis.setProgress(time.toInt())
    }

    private fun initLrcView() {
        lrc_view.lrcSetting
                .setTimeTextSize(32)//时间字体大小
                .setSelectLineColor(Color.parseColor("#2CA7FF"))//选中线颜色
                .setSelectLineTextSize(32)//选中线大小
                .setHeightRowColor(Color.parseColor("#2CA7FF"))//高亮字体颜色
                .setNormalRowTextSize(32)//正常行字体大小
                .setHeightLightRowTextSize(32)//高亮行字体大小
                .setTrySelectRowTextSize(32)//尝试选中行字体大小
                .setTimeTextColor(Color.parseColor("#2CA7FF"))//时间字体颜色
                .setTrySelectRowColor(Color.parseColor("#aa4c84ff"))//尝试选中字体颜色
                .setNormalRowColor(Color.parseColor("#C3C7CB"))
                .setMessageColor(Color.parseColor("#C3C7CB"))
                .setMessagePaintTextSize(15)
        lrc_view.commitLrcSettings()
        lrc_view.setLrcViewSeekListener(lrcListener)
        lrc_view.setNoDataMessage("暂无字幕")
        lrc_view.setLrcData(null)
    }

    private fun init() {
        tvTitle = findViewById(R.id.tv_title)
        ivPlay = findViewById(R.id.iv_play)
        tvCurrentTime = findViewById(R.id.tv_current_time)
        tvTotalTime = findViewById(R.id.tv_total_time)
        seekBar = findViewById(R.id.seek_bar)
        lrc_view = findViewById(R.id.lrc_view)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    MediaPlayerUtlis.setProgress(progress)
                }
                lrc_view.seekLrcToTime(progress.toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        ivPlay.setOnClickListener {
            MediaPlayerUtlis.play()
        }
        initLrcView()
    }

    fun setData(question: QuestionInfo, isAnalysis: Boolean = false) {
//        Utils.htmlText(question.stem() ?: "", tvTitle)
//        tvTitle.text=Html.fromHtml(question.stem()?:"")
        tvTitle.text(question.stem() ?: "")
        lrc_view.visibility = if (isAnalysis) View.VISIBLE else View.GONE
        //        initLrcData(question.lrcUrl()!!)
    }

    fun setData(question: Item) {
//        Utils.htmlText(question.stem() ?: "", tvTitle)
//        tvTitle.text=Html.fromHtml(question.stem()?:"")
        tvTitle.text(question.stem() ?: "")
    }

    fun initLrcData(lrcFile: File) {
        if (lrcFile.exists()) {
            lrc_view.setLrcData(MyLrcDataBuilder().Build(lrcFile, parser))
        }
    }

    fun start(duration: Int) {
        tvCurrentTime.text = "00:00"
        tvTotalTime.text = time(duration)
        seekBar.progress = 0
        seekBar.secondaryProgress = 0
        seekBar.max = duration
    }

    fun onCompletion() {
        tvCurrentTime.text = "00:00"
//        tvTotalTime.text = "00:00"
        seekBar.progress = 0
        seekBar.secondaryProgress = 0
//        seekBar.max = 0
    }

    fun status(play: Boolean) {
//        ivPlay.isSelected=play
        ivPlay.imageResource = if (play) R.drawable.ic_audio_play_q_selected else R.drawable.ic_audio_play_q_normal
    }

    fun progress(progress: Int) {
        context.runOnUiThread {
            seekBar.progress = progress
            tvCurrentTime.text = time(progress)
        }
    }

    fun time(time: Int): String {
        val i = time / 1000
        val s = i % 60
        val m = i / 60 % 60
        val h = i / 60 / 60 % 60
        return "${if (h < 10) "0$h" else "$h"}:${if (m < 10) "0$m" else "$m"}:${if (s < 10) "0$s" else "$s"}"
    }


}
