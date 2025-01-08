package space.iseki.ktxser

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class InstantSerializersTest {

    @Test
    fun serializeUnix() {
        val instant = Instant.ofEpochSecond(1672531200)
        val json = Json.encodeToString(InstantSerializers.Unix, instant)
        assertEquals("1672531200", json)
    }

    @Test
    fun deserializeUnix() {
        val json = "1672531200"
        val instant = Json.decodeFromString(InstantSerializers.Unix, json)
        assertEquals(Instant.ofEpochSecond(1672531200), instant)
    }

    @Test
    fun serializeUnixMillis() {
        val instant = Instant.ofEpochMilli(1672531200000)
        val json = Json.encodeToString(InstantSerializers.UnixMillis, instant)
        assertEquals("1672531200000", json)
    }

    @Test
    fun deserializeUnixMillis() {
        val json = "1672531200000"
        val instant = Json.decodeFromString(InstantSerializers.UnixMillis, json)
        assertEquals(Instant.ofEpochMilli(1672531200000), instant)
    }

    @Test
    fun serializeText() {
        val instant = Instant.parse("2023-01-01T00:00:00Z")
        val json = Json.encodeToString(InstantSerializers.Text, instant)
        assertEquals("\"2023-01-01T00:00:00Z\"", json)
    }

    @Test
    fun deserializeText() {
        val json = "\"2023-01-01T00:00:00Z\""
        val instant = Json.decodeFromString(InstantSerializers.Text, json)
        assertEquals(Instant.parse("2023-01-01T00:00:00Z"), instant)
    }

    @Test
    fun deserializeTextInvalidFormat() {
        val json = "\"2023-01-01 00:00:00\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(InstantSerializers.Text, json)
        }
    }

    @Test
    fun deserializeTextInvalidDate() {
        val json = "\"2023-13-01T00:00:00Z\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(InstantSerializers.Text, json)
        }
    }
}