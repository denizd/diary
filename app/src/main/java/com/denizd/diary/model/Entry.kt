package com.denizd.diary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entry(
    val title: String,
    val content: String,
    val emotion: String,
    @ColumnInfo(name = "time_created") val timeCreated: Long,
    @ColumnInfo(name = "time_last_modified") val timeLastModified: Long,
    @PrimaryKey(autoGenerate = true) val id: Long
) {
    companion object {
        val empty get() = Entry(
            "",
            "",
            "\uD83D\uDE10",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            0
        )
    }
}