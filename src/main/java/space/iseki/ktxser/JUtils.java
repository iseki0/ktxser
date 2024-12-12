package space.iseki.ktxser;

import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import kotlinx.serialization.SerializationException;

class JUtils {
    public static IntRange parseIntRange(String s) {
        var i = s.indexOf("..");
        if (i == -1) {
            throwRangeException();
        }
        try {
            var first = Integer.parseInt(s, 0, i, 10);
            var last = Integer.parseInt(s, i + 2, s.length(), 10);
            return new IntRange(first, last);
        } catch (NumberFormatException e) {
            throwRangeException();
        }
        return null;
    }

    public static LongRange parseLongRange(String s) {
        var i = s.indexOf("..");
        if (i == -1) {
            throwRangeException();
        }
        try {
            var first = Long.parseLong(s, 0, i, 10);
            var last = Long.parseLong(s, i + 2, s.length(), 10);
            return new LongRange(first, last);
        } catch (NumberFormatException e) {
            throwRangeException();
        }
        return null;
    }

    private static void throwRangeException() {
        throw new SerializationException("Expected a string in the form of 'start..end', where start and end are integers");
    }
}
