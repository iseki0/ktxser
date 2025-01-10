package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

data object LongSerializers {
    data object UnsignedHex : KSerializer<Long> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): Long {
            return decoder.decodeString().toULong(16).toLong()
        }

        @OptIn(ExperimentalStdlibApi::class)
        override fun serialize(encoder: Encoder, value: Long) {
            encoder.encodeString(value.toULong().toHexString())
        }
    }
}

