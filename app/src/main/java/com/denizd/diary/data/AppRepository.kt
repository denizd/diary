package com.denizd.diary.data

import android.content.Context

class AppRepository private constructor(appContext: Context) {

    val db: AppDao = AppDatabase.getInstance(appContext).dao()

    companion object {
        lateinit var instance: AppRepository
        fun init(appContext: Context): AppRepository {
            instance = AppRepository(appContext)
            return instance
        }
    }


}