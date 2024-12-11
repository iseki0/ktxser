package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IntRangeSerializers {
    object Text : KSerializer<IntRange> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): IntRange {
            val s = decoder.decodeString().split("..", limit = 2)
            if (s.size != 2) {
                throw SerializationException("Expected a string in the form of 'start..end', where start and end are integers")
            }
            try {
                return s[0].toInt()..s[1].toInt()
            } catch (_: NumberFormatException) {
                throw SerializationException("Expected a string in the form of 'start..end', where start and end are integers")
            }
        }

        override fun serialize(encoder: Encoder, value: IntRange) {
            encoder.encodeString("${value.first}..${value.last}")
        }
    }

    object Object : KSerializer<IntRange> {
        @Serializable
        private data class D(val first: Int, val last: Int)

        override val descriptor: SerialDescriptor
            get() = D.serializer().descriptor

        override fun deserialize(decoder: Decoder): IntRange =
            decoder.decodeSerializableValue(D.serializer()).let { it.first..it.last }

        override fun serialize(encoder: Encoder, value: IntRange) {
            encoder.encodeSerializableValue(D.serializer(), D(value.first, value.last))
        }
    }
}
