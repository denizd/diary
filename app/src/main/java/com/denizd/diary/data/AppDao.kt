package com.denizd.diary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.denizd.diary.model.Entry

@Dao
interface AppDao {

    @get:Query("SELECT * FROM entry ORDER BY time DESC")
    val allEntries: LiveData<List<Entry>>

    @Insert
    fun insert(entry: Entry): Long

    @Query("SELECT * FROM entry WHERE id = :id")
    fun getEntry(id: Long): Entry

    @Update
    fun updateEntry(entry: Entry)
}