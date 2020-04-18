package com.denizd.diary.view

import android.content.Context
import android.text.method.KeyListener
import android.util.AttributeSet
import com.denizd.diary.R
import com.denizd.diary.util.asFormattedSpan
import com.google.android.material.textfield.TextInputEditText

class RichEditText : TextInputEditText {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        isEditable = false
    }

    private val _keyListener: KeyListener = keyListener
    var isEditable: Boolean
        set(value) {
            field = value
        }
    private var rawText = ""

    override fun setText(text: CharSequence?, type: BufferType?) {
        rawText = text.toString()
        super.setText(
            if (isEditable) rawText else rawText.asFormattedSpan(),
            BufferType.SPANNABLE
        )
    }

    fun refreshText() {
        setText(text, null)
    }
}