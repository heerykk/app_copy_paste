package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import java.io.File;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.content.ContextCompatApi21 */
class ContextCompatApi21 {
    ContextCompatApi21() {
    }

    public static Drawable getDrawable(Context context, int i) {
        Context context2 = context;
        int id = i;
        Context context3 = context2;
        int i2 = id;
        return context2.getDrawable(id);
    }

    public static File getNoBackupFilesDir(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getNoBackupFilesDir();
    }

    public static File getCodeCacheDir(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getCodeCacheDir();
    }
}
