package space.iseki.ktxser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

data object ULongSerializers {
    data object Hex : KSerializer<ULong> {
        override val descriptor: SerialDescriptor
            get() = serialDescriptor<String>()

        override fun deserialize(decoder: Decoder): ULong {
            return decoder.decodeString().toULong(16)
        }

        @OptIn(ExperimentalStdlibApi::class)
        override fun serialize(encoder: Encoder, value: ULong) {
            encoder.encodeString(value.toHexString())
        }
    }
}
