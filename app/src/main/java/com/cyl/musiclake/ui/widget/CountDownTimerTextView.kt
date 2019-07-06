package com.cyl.musiclake.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.cyl.musiclake.utils.CountDownUtils


class CountDownTimerTextView :TextView{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        CountDownUtils.addTextView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        CountDownUtils.removeTextView(this)
    }
}