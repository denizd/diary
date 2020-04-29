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

        // {title=""#content=""#emotion=""#timeCreated=0#timeLastModified=0#id=0}
        fun getFromProtocol(protocol: String) = Entry(
            title = protocol.substring(7, protocol.indexOf("#content=")),
            content = protocol.substring(protocol.indexOf("#content=") + 9, protocol.indexOf("#emotion=")),
            emotion = protocol.substring(protocol.indexOf("#emotion=") + 9, protocol.indexOf("#timeCreated=")),
            timeCreated = protocol.substring(protocol.indexOf("#timeCreated=") + 13, protocol.indexOf("#timeLastModified=")).toLong(),
            timeLastModified = protocol.substring(protocol.indexOf("#timeLastModified=") + 18, protocol.indexOf("#id=")).toLong(),
            id = protocol.substring(protocol.indexOf("#id=") + 4, protocol.indexOf("EOO}")).toLong()
        )
    }
}