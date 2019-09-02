package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import java.io.File;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.content.ContextCompatApi24 */
class ContextCompatApi24 {
    ContextCompatApi24() {
    }

    public static File getDataDir(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getDataDir();
    }

    public static Context createDeviceProtectedStorageContext(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.createDeviceProtectedStorageContext();
    }

    public static boolean isDeviceProtectedStorage(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.isDeviceProtectedStorage();
    }
}
