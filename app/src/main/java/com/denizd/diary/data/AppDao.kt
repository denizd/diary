package com.denizd.diary.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.denizd.diary.model.Entry

@Dao
interface AppDao {

    @get:Query("SELECT * FROM entry ORDER BY time_created DESC")
    val allEntries: LiveData<List<Entry>>

    @Query("SELECT * FROM entry ORDER BY time_created DESC")
    fun getEntries(): List<Entry>

    @Insert
    fun insert(entry: Entry): Long

    @Query("SELECT * FROM entry WHERE id = :id")
    fun getEntry(id: Long): Entry

    @Update
    fun updateEntry(entry: Entry)

    @Query("DELETE FROM entry")
    fun clearEntries()

    @Insert
    fun insertAll(entries: List<Entry>)
}