package android.support.p000v4.p002os;

import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/* renamed from: android.support.v4.os.EnvironmentCompat */
public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    public static String getStorageState(File file) {
        File path = file;
        File file2 = path;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i >= 19) {
            return EnvironmentCompatKitKat.getStorageState(path);
        }
        try {
            if (path.getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath())) {
                return Environment.getExternalStorageState();
            }
        } catch (IOException e) {
            IOException iOException = e;
            int w = Log.w(TAG, "Failed to resolve canonical path: " + e);
        }
        return MEDIA_UNKNOWN;
    }

    private EnvironmentCompat() {
    }
}
