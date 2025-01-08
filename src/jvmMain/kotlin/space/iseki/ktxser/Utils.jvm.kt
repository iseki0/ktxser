package space.iseki.ktxser

internal actual fun parseLongRange(s: String): LongRange {
    return JUtils.parseLongRange(s)
}

internal actual fun parseIntRange(s: String): IntRange {
    return JUtils.parseIntRange(s)
}
