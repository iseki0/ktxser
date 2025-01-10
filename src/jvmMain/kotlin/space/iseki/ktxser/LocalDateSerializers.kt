package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

data object LocalDateSerializers {
    data object Text : KSerializer<LocalDate> {
        override fun toString(): String = "LocalDateSerializers.Text"
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): LocalDate {
            return wrapForDateTimeParseException { decoder.decodeString().let(LocalDate::parse) }
        }

        override fun serialize(encoder: Encoder, value: LocalDate) {
            encoder.encodeString(value.toString())
        }
    }

}
