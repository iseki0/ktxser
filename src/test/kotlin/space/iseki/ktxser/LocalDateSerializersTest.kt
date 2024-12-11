package space.iseki.ktxser
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertFailsWith

class LocalDateSerializersTest {

    @Test
    fun serializeText() {
        val date = LocalDate.parse("2023-01-01")
        val json = Json.encodeToString(LocalDateSerializers.Text, date)
        assertEquals("\"2023-01-01\"", json)
    }

    @Test
    fun deserializeText() {
        val json = "\"2023-01-01\""
        val date = Json.decodeFromString(LocalDateSerializers.Text, json)
        assertEquals(LocalDate.parse("2023-01-01"), date)
    }

    @Test
    fun deserializeTextInvalidFormat() {
        val json = "\"2023-01-01 12:00:00\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LocalDateSerializers.Text, json)
        }
    }

    @Test
    fun deserializeTextInvalidDate() {
        val json = "\"2023-13-01\""
        assertFailsWith<SerializationException> {
            Json.decodeFromString(LocalDateSerializers.Text, json)
        }
    }
}