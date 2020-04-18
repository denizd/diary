package com.denizd.diary.util

import java.text.SimpleDateFormat
import java.util.*

private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
private val sdfDateTime = SimpleDateFormat("yyyy-MM-dd, HH:mm", Locale.ROOT)

fun Long.asDate(time: Boolean = false): String = (if (time) sdfDateTime else sdfDate).format(Date(this))