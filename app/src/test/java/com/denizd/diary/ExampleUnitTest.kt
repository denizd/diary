package com.denizd.diary

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun stringIndex() {
        assertEquals(1, "abcdef".indexOf("bcde"))
    }

    @Test
    fun regexTest() {
        val s = "{title=\"\"#content=\"\"#emotion=\"\"#timeCreated=0#timeLastModified=0#id=0EOO}{title=\"\"#content=\"\"#emotion=\"\"#timeCreated=0#timeLastModified=0#id=0EOO}"
        val l = listOf(
            "{title=\"\"#content=\"\"#emotion=\"\"#timeCreated=0#timeLastModified=0#id=0EOO}",
            "{title=\"\"#content=\"\"#emotion=\"\"#timeCreated=0#timeLastModified=0#id=0EOO}"
        )
        assertEquals(l, s.split(Regex("(?=(\\{title=))")).filter { it != "" })
    }
}