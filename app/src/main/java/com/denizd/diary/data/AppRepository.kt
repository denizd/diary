package com.denizd.diary.data

import android.content.Context
import android.util.Log
import com.denizd.diary.model.Entry
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException

class AppRepository private constructor(appContext: Context) {

    val db: AppDao = AppDatabase.getInstance(appContext).dao()

    companion object {
        lateinit var instance: AppRepository
        fun init(appContext: Context): AppRepository {
            instance = AppRepository(appContext)
            return instance
        }
    }

    /**
     * Syncs data from remote server
     *
     * @return success status
     */
    fun syncDown(): Boolean {
        return try {
            BufferedReader(Socket("192.168.2.109", 23754).getInputStream().reader()).use { reader ->
                db.clearEntries()
                db.insertAll(reader.getEntriesFromNetwork())
            }
            true
        } catch (e: Exception) {
            Log.e("TAG", e.message ?: "errrrrrrrrror")
            false
        }
    }

    fun syncUp(): Boolean {
        return try {
            BufferedWriter(Socket("192.168.2.109", 23754).getOutputStream().writer()).use { writer ->
                writer.write(db.getEntries().asProtocolString())
                writer.flush()
            }
            true
        } catch (e: IOException) {
            false
        }
    }

    /**
     * Gets Entry objects from a local sync application conforming to protocol:
     * {title=""#content=""#emotion=""#timeCreated=0#timeLastModified=0#id=0EOO}
     *
     * @return a list of all Entry objects
     */
    private fun BufferedReader.getEntriesFromNetwork(): List<Entry> {
        val list = arrayListOf<Entry>()
        readText().split(Regex("(?=(\\{title=))")).filter { it != "" }.forEach {
            list.add(Entry.getFromProtocol(it))
        }
        return list
    }

    private fun List<Entry>.asProtocolString(): String {
        var string = ""
        forEach {
            string += "{title=${it.title}#content=${it.content}#emotion=${it.emotion}#timeCreated=${it.timeCreated}#timeLastModified=${it.timeLastModified}#id=${it.id}EOO}"
        }
        return string
    }
}