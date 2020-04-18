package com.denizd.diary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entry(
    val title: String,
    val content: String,
    val emotion: String,
    val time: Long,
    @PrimaryKey(autoGenerate = true) val id: Long
) {
    companion object {
        val empty get() = Entry("", "", "\uD83D\uDE10", System.currentTimeMillis(), 0)
    }
}