package space.iseki.ktxser

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LongRangeSerializersTest {

    @Test
    fun serializeText() {
        val range = 1L..10L
        val json = Json.encodeToString(LongRangeSerializers.Text, range)
        assertEquals("\"1..10\"", json)
    }

    @Test
    fun deserializeText() {
        val json = "\"1..10\""
        val range = Json.decodeFromString(LongRangeSerializers.Text, json)
        assertEquals(1L..10L, range)
    }

    @Test
    fun deserializeTextInvalidFormat() {
        val json = "\"1-10\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LongRangeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeTextNonInteger() {
        val json = "\"a..b\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LongRangeSerializers.Text, json)
        }
    }

    @Test
    fun serializeObject() {
        val range = 1L..10L
        val json = Json.encodeToString(LongRangeSerializers.Object, range)
        assertEquals("{\"first\":1,\"last\":10}", json)
    }

    @Test
    fun deserializeObject() {
        val json = "{\"first\":1,\"last\":10}"
        val range = Json.decodeFromString(LongRangeSerializers.Object, json)
        assertEquals(1L..10L, range)
    }

    @Test
    fun deserializeObjectInvalidFormat() {
        val json = "{\"start\":1,\"end\":10}"
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LongRangeSerializers.Object, json)
        }
    }

    @Test
    fun deserializeTextOverflow() {
        val json = "\"9223372036854775807..9223372036854775808\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LongRangeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeObjectOverflow() {
        val json = "{\"first\":9223372036854775807,\"last\":9223372036854775808}"
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LongRangeSerializers.Object, json)
        }
    }

    @Test
    fun deserializeTextUnderflow() {
        val json = "\"-9223372036854775808..-9223372036854775809\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LongRangeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeObjectUnderflow() {
        val json = "{\"first\":-9223372036854775808,\"last\":-9223372036854775809}"
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LongRangeSerializers.Object, json)
        }
    }
}