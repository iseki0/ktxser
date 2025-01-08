package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant

object InstantSerializers {
    object Unix : KSerializer<Instant> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<Long>()

        override fun deserialize(decoder: Decoder): Instant {
            return wrapForDateTimeParseException { Instant.ofEpochSecond(decoder.decodeLong()) }
        }

        override fun serialize(encoder: Encoder, value: Instant) {
            encoder.encodeLong(value.epochSecond)
        }
    }

    object UnixMillis : KSerializer<Instant> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<Long>()

        override fun deserialize(decoder: Decoder): Instant {
            return wrapForDateTimeParseException { Instant.ofEpochMilli(decoder.decodeLong()) }
        }

        override fun serialize(encoder: Encoder, value: Instant) {
            encoder.encodeLong(value.toEpochMilli())
        }
    }

    object Text : KSerializer<Instant> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): Instant {
            return wrapForDateTimeParseException { Instant.parse(decoder.decodeString()) }
        }

        override fun serialize(encoder: Encoder, value: Instant) {
            encoder.encodeString(value.toString())
        }
    }
}
