package space.iseki.ktxser

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import kotlin.test.assertFailsWith

class IntRangeSerializersTest {

    @Test
    fun serializeText() {
        val range = 1..10
        val json = Json.encodeToString(IntRangeSerializers.Text, range)
        assertEquals("\"1..10\"", json)
    }

    @Test
    fun deserializeText() {
        val json = "\"1..10\""
        val range = Json.decodeFromString(IntRangeSerializers.Text, json)
        assertEquals(1..10, range)
    }

    @Test
    fun deserializeTextInvalidFormat() {
        val json = "\"1-10\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(IntRangeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeTextNonInteger() {
        val json = "\"a..b\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(IntRangeSerializers.Text, json)
        }
    }

    @Test
    fun serializeObject() {
        val range = 1..10
        val json = Json.encodeToString(IntRangeSerializers.Object, range)
        assertEquals("{\"first\":1,\"last\":10}", json)
    }

    @Test
    fun deserializeObject() {
        val json = "{\"first\":1,\"last\":10}"
        val range = Json.decodeFromString(IntRangeSerializers.Object, json)
        assertEquals(1..10, range)
    }

    @Test
    fun deserializeObjectInvalidFormat() {
        val json = "{\"start\":1,\"end\":10}"
        assertFailsWith<SerializationException> {
            Json.decodeFromString(IntRangeSerializers.Object, json)
        }
    }

    @Test
    fun deserializeTextOverflow() {
        val json = "\"${Int.MAX_VALUE}..${Int.MAX_VALUE + 1L}\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(IntRangeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeObjectOverflow() {
        val json = "{\"first\":${Int.MAX_VALUE},\"last\":${Int.MAX_VALUE + 1L}}"
        assertFailsWith<SerializationException> {
            Json.decodeFromString(IntRangeSerializers.Object, json)
        }
    }

    @Test
    fun deserializeTextUnderflow() {
        val json = "\"${Int.MIN_VALUE}..${Int.MIN_VALUE - 1L}\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(IntRangeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeObjectUnderflow() {
        val json = "{\"first\":${Int.MIN_VALUE},\"last\":${Int.MIN_VALUE - 1L}}"
        assertFailsWith<SerializationException> {
            Json.decodeFromString(IntRangeSerializers.Object, json)
        }
    }
}