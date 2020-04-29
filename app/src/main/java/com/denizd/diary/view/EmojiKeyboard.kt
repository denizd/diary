package com.denizd.diary.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denizd.diary.R

class EmojiKeyboard : RecyclerView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun initialise(listener: EmojiAdapter.EmojiClickListener) {
        adapter = EmojiAdapter(listener)
        layoutManager = GridLayoutManager(context, 6)
    }
}