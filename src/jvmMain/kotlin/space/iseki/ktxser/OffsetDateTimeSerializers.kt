package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data object OffsetDateTimeSerializers {

    data object Text : KSerializer<OffsetDateTime> {
        override fun toString(): String = "OffsetDateTimeSerializers.Text"
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): OffsetDateTime {
            return wrapForDateTimeParseException { OffsetDateTime.parse(decoder.decodeString()) }
        }

        override fun serialize(encoder: Encoder, value: OffsetDateTime) {
            encoder.encodeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        }
    }
}

