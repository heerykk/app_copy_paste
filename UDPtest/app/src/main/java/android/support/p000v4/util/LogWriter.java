package android.support.p000v4.util;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import java.io.Writer;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.util.LogWriter */
public class LogWriter extends Writer {
    private StringBuilder mBuilder = new StringBuilder(128);
    private final String mTag;

    public LogWriter(String str) {
        String tag = str;
        String str2 = tag;
        this.mTag = tag;
    }

    public void close() {
        flushBuilder();
    }

    public void flush() {
        flushBuilder();
    }

    public void write(char[] cArr, int i, int i2) {
        char[] buf = cArr;
        int offset = i;
        int count = i2;
        char[] cArr2 = buf;
        int i3 = offset;
        int i4 = count;
        for (int i5 = 0; i5 < count; i5++) {
            char c = buf[offset + i5];
            char c2 = c;
            if (c != 10) {
                StringBuilder append = this.mBuilder.append(c2);
            } else {
                flushBuilder();
            }
        }
    }

    private void flushBuilder() {
        if (this.mBuilder.length() > 0) {
            int d = Log.d(this.mTag, this.mBuilder.toString());
            StringBuilder delete = this.mBuilder.delete(0, this.mBuilder.length());
        }
    }
}
