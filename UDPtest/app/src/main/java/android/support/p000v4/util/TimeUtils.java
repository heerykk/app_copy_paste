package android.support.p000v4.util;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.io.PrintWriter;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.util.TimeUtils */
public final class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static char[] sFormatStr = new char[24];
    private static final Object sFormatSync = new Object();

    private static int accumField(int i, int i2, boolean z, int i3) {
        int amt = i;
        int suffix = i2;
        int zeropad = i3;
        int i4 = amt;
        int i5 = suffix;
        boolean always = z;
        int i6 = zeropad;
        if (amt > 99 || (always && zeropad >= 3)) {
            return 3 + suffix;
        }
        if (amt > 9 || (always && zeropad >= 2)) {
            return 2 + suffix;
        }
        if (!always && amt <= 0) {
            return 0;
        }
        return 1 + suffix;
    }

    private static int printField(char[] cArr, int i, char c, int i2, boolean z, int i3) {
        char[] formatStr = cArr;
        int amt = i;
        int pos = i2;
        int zeropad = i3;
        char[] cArr2 = formatStr;
        int amt2 = amt;
        char suffix = c;
        int pos2 = pos;
        boolean always = z;
        int i4 = zeropad;
        if (always || amt > 0) {
            int i5 = pos;
            if ((always && zeropad >= 3) || amt > 99) {
                int i6 = amt / 100;
                int dig = i6;
                formatStr[pos] = (char) ((char) (i6 + 48));
                pos2 = pos + 1;
                amt2 = amt - (dig * 100);
            }
            if ((always && zeropad >= 2) || amt2 > 9 || pos != pos2) {
                int i7 = amt2 / 10;
                int dig2 = i7;
                formatStr[pos2] = (char) ((char) (i7 + 48));
                pos2++;
                amt2 -= dig2 * 10;
            }
            formatStr[pos2] = (char) ((char) (amt2 + 48));
            int pos3 = pos2 + 1;
            formatStr[pos3] = (char) suffix;
            pos2 = pos3 + 1;
        }
        return pos2;
    }

    private static int formatDurationLocked(long j, int i) {
        char prefix;
        long duration = j;
        int fieldLen = i;
        long duration2 = duration;
        int i2 = fieldLen;
        if (sFormatStr.length < fieldLen) {
            sFormatStr = new char[fieldLen];
        }
        char[] formatStr = sFormatStr;
        if (duration == 0) {
            int fieldLen2 = fieldLen - 1;
            while (0 < fieldLen2) {
                formatStr[0] = ' ';
            }
            formatStr[0] = '0';
            return 1;
        }
        if (!(duration <= 0)) {
            prefix = '+';
        } else {
            prefix = '-';
            duration2 = -duration;
        }
        int millis = (int) (duration2 % 1000);
        int seconds = (int) Math.floor((double) (duration2 / 1000));
        int days = 0;
        int hours = 0;
        int minutes = 0;
        if (seconds > SECONDS_PER_DAY) {
            days = seconds / SECONDS_PER_DAY;
            seconds -= days * SECONDS_PER_DAY;
        }
        if (seconds > SECONDS_PER_HOUR) {
            hours = seconds / SECONDS_PER_HOUR;
            seconds -= hours * SECONDS_PER_HOUR;
        }
        if (seconds > 60) {
            minutes = seconds / 60;
            seconds -= minutes * 60;
        }
        int pos = 0;
        if (fieldLen != 0) {
            int accumField = accumField(days, 1, false, 0);
            int accumField2 = accumField + accumField(hours, 1, accumField > 0, 2);
            int accumField3 = accumField2 + accumField(minutes, 1, accumField2 > 0, 2);
            int accumField4 = accumField3 + accumField(seconds, 1, accumField3 > 0, 2);
            for (int myLen = accumField4 + accumField(millis, 2, true, accumField4 <= 0 ? 0 : 3) + 1; myLen < fieldLen; myLen++) {
                formatStr[pos] = ' ';
                pos++;
            }
        }
        formatStr[pos] = (char) prefix;
        int pos2 = pos + 1;
        int start = pos2;
        boolean zeropad = fieldLen != 0;
        int printField = printField(formatStr, days, 'd', pos2, false, 0);
        int pos3 = printField;
        int printField2 = printField(formatStr, hours, 'h', printField, printField != start, !zeropad ? 0 : 2);
        int pos4 = printField2;
        int printField3 = printField(formatStr, minutes, 'm', printField2, printField2 != start, !zeropad ? 0 : 2);
        int pos5 = printField3;
        int printField4 = printField(formatStr, seconds, 's', printField3, printField3 != start, !zeropad ? 0 : 2);
        int pos6 = printField4;
        int printField5 = printField(formatStr, millis, 'm', printField4, true, (zeropad && printField4 != start) ? 3 : 0);
        int pos7 = printField5;
        formatStr[printField5] = 's';
        return printField5 + 1;
    }

    public static void formatDuration(long j, StringBuilder sb) {
        long duration = j;
        StringBuilder builder = sb;
        long j2 = duration;
        StringBuilder sb2 = builder;
        Object obj = sFormatSync;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                int formatDurationLocked = formatDurationLocked(duration, 0);
                int i = formatDurationLocked;
                StringBuilder append = builder.append(sFormatStr, 0, formatDurationLocked);
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    public static void formatDuration(long j, PrintWriter printWriter, int i) {
        long duration = j;
        PrintWriter pw = printWriter;
        int fieldLen = i;
        long j2 = duration;
        PrintWriter printWriter2 = pw;
        int i2 = fieldLen;
        Object obj = sFormatSync;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                int formatDurationLocked = formatDurationLocked(duration, fieldLen);
                int i3 = formatDurationLocked;
                pw.print(new String(sFormatStr, 0, formatDurationLocked));
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    public static void formatDuration(long j, PrintWriter printWriter) {
        long duration = j;
        PrintWriter pw = printWriter;
        long j2 = duration;
        PrintWriter printWriter2 = pw;
        formatDuration(duration, pw, 0);
    }

    public static void formatDuration(long j, long j2, PrintWriter printWriter) {
        long time = j;
        long now = j2;
        PrintWriter pw = printWriter;
        long j3 = time;
        long j4 = now;
        PrintWriter printWriter2 = pw;
        if (time == 0) {
            pw.print("--");
        } else {
            formatDuration(time - now, pw, 0);
        }
    }

    private TimeUtils() {
    }
}
