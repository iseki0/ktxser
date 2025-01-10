package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

data object IntRangeSerializers {
    data object Text : KSerializer<IntRange> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): IntRange {
            return parseIntRange(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: IntRange) {
            encoder.encodeString("${value.first}..${value.last}")
        }
    }

    data object Object : KSerializer<IntRange> {
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
