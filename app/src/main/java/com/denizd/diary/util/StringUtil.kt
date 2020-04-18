package com.denizd.diary.util

import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
private val sdfDateTime = SimpleDateFormat("yyyy-MM-dd, HH:mm", Locale.ROOT)

fun Long.asDate(time: Boolean = false): String = (if (time) sdfDateTime else sdfDate).format(Date(this))

private val formattingOptions = listOf('~', '*', '_', '`')

private fun getFormat(char: Char): Any = when (char) {
    '~' -> StrikethroughSpan()
    '*' -> StyleSpan(Typeface.BOLD)
    '_' -> StyleSpan(Typeface.ITALIC)
    '`' -> TypefaceSpan("monospace")
    else -> throw IllegalArgumentException("No formatting option for character $char available")
}

fun String.asFormattedSpan(): SpannableString {
    val span = SpannableString(this)

    // cycles through all formatting options
    for (option in formattingOptions) {
        var startIndex = -1
        var previousChar = (-1).toChar()

        span.forEachIndexed { index, char ->
            if (char == option) {
                startIndex = if (startIndex == -1) {
                    if (previousChar == ' ' || previousChar == '\n') index else -1
                } else {
                    span.setSpan(getFormat(option), startIndex, index + 1, 0)
                    -1
                }
            }
            previousChar = char
        }
    }

    return span
}