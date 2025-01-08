package space.iseki.ktxser

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class LongSerializersTest {
    @Test
    fun testHex() {
        listOf(
            "\"0000000000000001\"" to 1L,
            "\"ffffffffffffffff\"" to -1L,
        ).forEach { (s, l) ->
            val jsonText = Json.encodeToString(LongSerializers.UnsignedHex, l)
            assertEquals(s, jsonText)
            val long = Json.decodeFromString(LongSerializers.UnsignedHex, jsonText)
            assertEquals(l, long)
        }
    }
}

