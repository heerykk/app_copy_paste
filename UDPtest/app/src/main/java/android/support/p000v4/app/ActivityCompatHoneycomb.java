package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import java.io.FileDescriptor;
import java.io.PrintWriter;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.app.ActivityCompatHoneycomb */
class ActivityCompatHoneycomb {
    ActivityCompatHoneycomb() {
    }

    static void invalidateOptionsMenu(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        activity2.invalidateOptionsMenu();
    }

    static void dump(Activity activity, String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Activity activity2 = activity;
        String prefix = str;
        FileDescriptor fd = fileDescriptor;
        PrintWriter writer = printWriter;
        String[] args = strArr;
        Activity activity3 = activity2;
        String str2 = prefix;
        FileDescriptor fileDescriptor2 = fd;
        PrintWriter printWriter2 = writer;
        String[] strArr2 = args;
        activity2.dump(prefix, fd, writer, args);
    }
}
