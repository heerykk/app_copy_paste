package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import java.io.File;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.content.ContextCompatKitKat */
class ContextCompatKitKat {
    ContextCompatKitKat() {
    }

    public static File[] getExternalCacheDirs(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getExternalCacheDirs();
    }

    public static File[] getExternalFilesDirs(Context context, String str) {
        Context context2 = context;
        String type = str;
        Context context3 = context2;
        String str2 = type;
        return context2.getExternalFilesDirs(type);
    }

    public static File[] getObbDirs(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getObbDirs();
    }
}
