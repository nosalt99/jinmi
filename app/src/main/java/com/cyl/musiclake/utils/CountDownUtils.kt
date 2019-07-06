package com.cyl.musiclake.utils

import android.os.CountDownTimer
import android.widget.TextView
import com.cyl.musiclake.player.PlayManager


object CountDownUtils {
    private var countDown: CountDownTimer? = null
    private val textViewList = mutableListOf<TextView?>()
    var type = 0

    var isOpenSleepSwitch = true


    val times = intArrayOf(0, 15, 30, 45, 60)
    val selectItems = arrayListOf("不开启", "15分钟", "30分钟", "45分钟", "60分钟", "自定义")

    var totalTime = 0L



    fun starCountDownById(id: Int) {
        totalTime = times[id] * 60 * 1000L
        start(totalTime, id)
    }


    fun starCountDownByTime(time: Int) {
        totalTime = time * 60 * 1000L
        start(totalTime, 5)
    }



    fun start(time: Long, type: Int) {
        this.type = type
        try {
            countDown?.cancel()
        } catch (e: Throwable) {

        } finally {
            countDown = object : CountDownTimer(time, 1000) {
                override fun onFinish() {
                    textViewList.forEach {
                        it?.text = null
                    }
                    //如果正在播放暂停播放
                    if (PlayManager.isPlaying() && !isOpenSleepSwitch) {
                        PlayManager.playPause()
                    }
                    this@CountDownUtils.type = -1
                }

                override fun onTick(millisUntilFinished: Long) {
                    textViewList.forEach {
                        it?.text = FormatUtil.formatTime(millisUntilFinished)
                    }
                }
            }.start()
        }
    }


    fun addTextView(tv: TextView) {
        textViewList.add(tv)
    }


    fun removeTextView(tv: TextView) {
        textViewList.remove(tv)
    }


    fun cancel() {
        type = 0
        try {
            textViewList.forEach {
                it?.text = null
            }
            countDown?.cancel()
        } catch (e: Throwable) {
        }
    }

}