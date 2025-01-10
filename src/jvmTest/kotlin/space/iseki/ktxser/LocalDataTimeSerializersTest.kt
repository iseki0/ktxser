package space.iseki.ktxser

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalDataTimeSerializersTest {

    @Test
    fun serializeText() {
        val dateTime = LocalDateTime.parse("2023-01-01T12:00:00")
        val json = Json.encodeToString(LocalDataTimeSerializers.Text, dateTime)
        assertEquals("\"2023-01-01T12:00:00\"", json)
    }

    @Test
    fun deserializeText() {
        val json = "\"2023-01-01T12:00:00\""
        val dateTime = Json.decodeFromString(LocalDataTimeSerializers.Text, json)
        assertEquals(LocalDateTime.parse("2023-01-01T12:00:00"), dateTime)
    }

    @Test
    fun deserializeTextInvalidFormat() {
        val json = "\"2023-01-01 12:00:00\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LocalDataTimeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeTextInvalidDate() {
        val json = "\"2023-13-01T12:00:00\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LocalDataTimeSerializers.Text, json)
        }
    }

    @Test
    fun serializeTextNano() {
        val dateTime = LocalDateTime.parse("2023-01-01T12:00:00.123456789")
        val json = Json.encodeToString(LocalDataTimeSerializers.Text, dateTime)
        assertEquals("\"2023-01-01T12:00:00.123456789\"", json)
    }

    @Test
    fun deserializeTextNano() {
        val json = "\"2023-01-01T12:00:00.123456789\""
        val dateTime = Json.decodeFromString(LocalDataTimeSerializers.Text, json)
        assertEquals(LocalDateTime.parse("2023-01-01T12:00:00.123456789"), dateTime)
    }

    @Test
    fun deserializeTextInvalidFormatNano() {
        val json = "\"2023-01-01 12:00:00.123456789\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LocalDataTimeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeTextInvalidDateNano() {
        val json = "\"2023-13-01T12:00:00.123456789\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LocalDataTimeSerializers.Text, json)
        }
    }
}