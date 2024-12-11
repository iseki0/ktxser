package space.iseki.ktxser

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.OffsetDateTime
import kotlin.test.Test
import kotlin.test.assertFailsWith

class OffsetDateTimeSerializersTest {
    @Test
    fun deserializeText() {
        val json = "\"2023-01-01T12:00:00Z\""
        val dateTime = Json.decodeFromString(OffsetDateTimeSerializers.Text, json)
        assertEquals(OffsetDateTime.parse("2023-01-01T12:00:00+00:00"), dateTime)
    }

    @Test
    fun deserializeTextInvalidFormat() {
        val json = "\"2023-01-01 12:00:00\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(OffsetDateTimeSerializers.Text, json)
        }
    }

    @Test
    fun deserializeTextInvalidDate() {
        val json = "\"2023-13-01T12:00:00Z\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(OffsetDateTimeSerializers.Text, json)
        }
    }
}