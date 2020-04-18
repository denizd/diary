package com.denizd.diary.viewmodel

import com.denizd.diary.model.Entry

class OverviewViewModel : BaseViewModel() {

    val allEntries = repo.db.allEntries

    fun insert(entry: Entry): Long = returnBlocking { repo.db.insert(entry) }
}