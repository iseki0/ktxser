package space.iseki.ktxser

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ULongSerializersTest {
    @Test
    fun testHex() {
        listOf(
            "\"0000000000000001\"" to 1UL,
            "\"ffffffffffffffff\"" to ULong.MAX_VALUE,
        ).forEach { (s, l) ->
            val jsonText = Json.encodeToString(ULongSerializers.Hex, l)
            assertEquals(s, jsonText)
            val long = Json.decodeFromString(ULongSerializers.Hex, jsonText)
            assertEquals(l, long)
        }
    }
}
