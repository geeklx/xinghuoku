package com.example.questions.utils

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import java.util.*


/**
 * 创建者： 霍述雷
 * 时间：  2017/11/15.
 */

object MediaPlayerUtlis {
    private var callBack: (Int) -> Unit = {}
    private var onCompletion: () -> Unit = {}
    private var onError: (String) -> Unit = {}
    private var onStart: (Int) -> Unit = { }
    private var status: (Boolean) -> Unit = {}
    private val timer by lazy { Timer(true) }
    private var isPerpared = false

    private val mediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setOnCompletionListener { mp ->
                onCompletion()
//                reset()
            }
            setOnErrorListener { player, what, extra ->
                onError("")
                reset()
                isPerpared = false
                false
            }
            setOnPreparedListener {
                isPerpared = true
                if (!it.isPlaying) {
                    onStart(duration)
//                    it.start()
                }
            }
            //添加播放完成监听
            timer.schedule(object : TimerTask() {
                override fun run() {
                    try {
                        status(isPlaying)
                        if (isPlaying) {
                            val index = currentPosition
                            callBack(index)
                        }
                    } catch (e: Exception) {
                    }

                }
            }, 200, 200)
        }
    }

    fun start(
            uri: String,
            onStart: (Int) -> Unit,
            onCompletion: () -> Unit,
            onError: (String) -> Unit,
            status: (Boolean) -> Unit,
            callBack: (Int) -> Unit = {}
    ) {
        this.onCompletion = onCompletion
        this.onError = onError
        this.onStart = onStart
        this.status = status
        this.callBack = callBack
        reset()
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepareAsync()
    }

    fun play() {
        if (isPerpared)
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }

    }

    private var isPause: Boolean = false

    fun pause() {
        if (isPerpared)
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                isPause = true
            }
    }

    fun start() {
        if (isPause)
            if (isPerpared)
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                    isPause = false
                }
    }

    fun reset() {
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    fun release() {
        timer.cancel()
        mediaPlayer.release()
    }

    fun setProgress(progress: Int) {
        try {
            mediaPlayer.seekTo(progress)
        } catch (e: Exception) {
        }
    }
}
