package com.denizd.diary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "modified_time",
    foreignKeys = [
        ForeignKey(
            entity = Entry::class,
            parentColumns = ["id"],
            childColumns = ["entry_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ModifiedTime(
    val time: Long,
    @ColumnInfo(name = "entry_id") val entryId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long
)