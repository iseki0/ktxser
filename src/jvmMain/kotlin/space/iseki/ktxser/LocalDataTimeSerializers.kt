package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data object LocalDataTimeSerializers {
    data object Text : KSerializer<LocalDateTime> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): LocalDateTime {
            return wrapForDateTimeParseException { decoder.decodeString().let(LocalDateTime::parse) }
        }

        override fun serialize(encoder: Encoder, value: LocalDateTime) {
            encoder.encodeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        }

    }
}
