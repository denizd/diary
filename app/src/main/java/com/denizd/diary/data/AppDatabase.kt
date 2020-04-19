package com.denizd.diary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.denizd.diary.model.Entry

@Database(entities = [Entry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): AppDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(appContext: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        appContext,
                        AppDatabase::class.java,
                        "diary_db"
                    ).build()
                }
            }
            return instance!!
        }
    }
}