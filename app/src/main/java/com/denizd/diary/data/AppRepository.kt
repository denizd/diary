package com.denizd.diary.data

import android.content.Context
import android.util.Log
import com.denizd.diary.model.Entry
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.net.InetSocketAddress
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
    fun syncDown(host: String): Boolean {
        return try {
            BufferedReader(host.connect().getInputStream().reader()).use { reader ->
                db.clearEntries()
                db.insertAll(reader.getEntriesFromNetwork())
            }
            true
        } catch (e: Exception) {
            Log.e("e", "e", e)
            false
        }
    }

    fun syncUp(host: String): Boolean {
        return try {
            BufferedWriter(host.connect().getOutputStream().writer()).use { writer ->
                writer.write(db.getEntries().asProtocolString())
                writer.flush()
            }
            true
        } catch (e: IOException) {
            Log.e("e", "e", e)
            false
        }
    }

    private fun String.connect() = Socket().also { s -> s.connect(InetSocketAddress(this, 23754), 1000) }

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