@file:JvmName("-JvmUtils")
package space.iseki.ktxser

import kotlinx.serialization.SerializationException
import java.time.format.DateTimeParseException

internal inline fun <T> wrapForDateTimeParseException(block: () -> T): T {
    try {
        return block()
    } catch (e: DateTimeParseException) {
        convertException(e)
    }
}

internal fun convertException(e: DateTimeParseException): Nothing {
    throw SerializationException(e.message, e)
}
