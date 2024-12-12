package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LongRangeSerializers {
    object Text : KSerializer<LongRange> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): LongRange {
            return JUtils.parseLongRange(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: LongRange) {
            encoder.encodeString("${value.first}..${value.last}")
        }
    }

    object Object : KSerializer<LongRange> {
        @Serializable
        private data class D(val first: Long, val last: Long)

        override val descriptor: SerialDescriptor
            get() = D.serializer().descriptor

        override fun deserialize(decoder: Decoder): LongRange =
            decoder.decodeSerializableValue(D.serializer()).let { it.first..it.last }

        override fun serialize(encoder: Encoder, value: LongRange) {
            encoder.encodeSerializableValue(D.serializer(), D(value.first, value.last))
        }
    }
}

