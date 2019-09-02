package android.support.p000v4.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.p002os.BuildCompat;
import android.util.Log;
import android.util.TypedValue;
import java.io.File;

/* renamed from: android.support.v4.content.ContextCompat */
public class ContextCompat {
    private static final String DIR_ANDROID = "Android";
    private static final String DIR_OBB = "obb";
    private static final String TAG = "ContextCompat";
    private static final Object sLock = new Object();
    private static TypedValue sTempValue;

    protected ContextCompat() {
    }

    public static boolean startActivities(Context context, Intent[] intentArr) {
        Context context2 = context;
        Intent[] intents = intentArr;
        Context context3 = context2;
        Intent[] intentArr2 = intents;
        return startActivities(context2, intents, null);
    }

    public static boolean startActivities(Context context, Intent[] intentArr, Bundle bundle) {
        Context context2 = context;
        Intent[] intents = intentArr;
        Bundle options = bundle;
        Context context3 = context2;
        Intent[] intentArr2 = intents;
        Bundle bundle2 = options;
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 16) {
            ContextCompatJellybean.startActivities(context2, intents, options);
            return true;
        } else if (version < 11) {
            return false;
        } else {
            ContextCompatHoneycomb.startActivities(context2, intents);
            return true;
        }
    }

    public static void startActivity(Context context, Intent intent, @Nullable Bundle bundle) {
        Context context2 = context;
        Intent intent2 = intent;
        Bundle options = bundle;
        Context context3 = context2;
        Intent intent3 = intent2;
        Bundle bundle2 = options;
        if (VERSION.SDK_INT < 16) {
            context2.startActivity(intent2);
        } else {
            ContextCompatJellybean.startActivity(context2, intent2, options);
        }
    }

    public static File getDataDir(Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (BuildCompat.isAtLeastN()) {
            return ContextCompatApi24.getDataDir(context2);
        }
        String str = context2.getApplicationInfo().dataDir;
        return str == null ? null : new File(str);
    }

    public static File[] getObbDirs(Context context) {
        File single;
        Context context2 = context;
        Context context3 = context2;
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 19) {
            return ContextCompatKitKat.getObbDirs(context2);
        }
        if (version < 11) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            String[] strArr = new String[3];
            strArr[0] = DIR_ANDROID;
            strArr[1] = DIR_OBB;
            strArr[2] = context2.getPackageName();
            single = buildPath(externalStorageDirectory, strArr);
        } else {
            single = ContextCompatHoneycomb.getObbDir(context2);
        }
        return new File[]{single};
    }

    public static File[] getExternalFilesDirs(Context context, String str) {
        Context context2 = context;
        String type = str;
        Context context3 = context2;
        String str2 = type;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i >= 19) {
            return ContextCompatKitKat.getExternalFilesDirs(context2, type);
        }
        File[] fileArr = new File[1];
        fileArr[0] = context2.getExternalFilesDir(type);
        return fileArr;
    }

    public static File[] getExternalCacheDirs(Context context) {
        Context context2 = context;
        Context context3 = context2;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i >= 19) {
            return ContextCompatKitKat.getExternalCacheDirs(context2);
        }
        File[] fileArr = new File[1];
        fileArr[0] = context2.getExternalCacheDir();
        return fileArr;
    }

    private static File buildPath(File file, String... strArr) {
        File base = file;
        String[] segments = strArr;
        File file2 = base;
        String[] strArr2 = segments;
        File cur = base;
        for (String segment : segments) {
            if (cur == null) {
                cur = new File(segment);
            } else if (segment != null) {
                cur = new File(cur, segment);
            }
        }
        return cur;
    }

    public static final Drawable getDrawable(Context context, @DrawableRes int i) {
        Context context2 = context;
        int id = i;
        Context context3 = context2;
        int i2 = id;
        int i3 = VERSION.SDK_INT;
        int version = i3;
        if (i3 >= 21) {
            return ContextCompatApi21.getDrawable(context2, id);
        }
        if (version >= 16) {
            return context2.getResources().getDrawable(id);
        }
        Object obj = sLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (sTempValue == null) {
                    sTempValue = new TypedValue();
                }
                context2.getResources().getValue(id, sTempValue, true);
                int i4 = sTempValue.resourceId;
                int i5 = i4;
                return context2.getResources().getDrawable(i4);
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public static final ColorStateList getColorStateList(Context context, @ColorRes int i) {
        Context context2 = context;
        int id = i;
        Context context3 = context2;
        int i2 = id;
        int i3 = VERSION.SDK_INT;
        int i4 = i3;
        if (i3 < 23) {
            return context2.getResources().getColorStateList(id);
        }
        return ContextCompatApi23.getColorStateList(context2, id);
    }

    @ColorInt
    public static final int getColor(Context context, @ColorRes int i) {
        Context context2 = context;
        int id = i;
        Context context3 = context2;
        int i2 = id;
        int i3 = VERSION.SDK_INT;
        int i4 = i3;
        if (i3 < 23) {
            return context2.getResources().getColor(id);
        }
        return ContextCompatApi23.getColor(context2, id);
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String str) {
        Context context2 = context;
        String permission = str;
        Context context3 = context2;
        String str2 = permission;
        if (permission != null) {
            return context2.checkPermission(permission, Process.myPid(), Process.myUid());
        }
        throw new IllegalArgumentException("permission is null");
    }

    public static final File getNoBackupFilesDir(Context context) {
        Context context2 = context;
        Context context3 = context2;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 21) {
            return createFilesDir(new File(context2.getApplicationInfo().dataDir, "no_backup"));
        }
        return ContextCompatApi21.getNoBackupFilesDir(context2);
    }

    public static File getCodeCacheDir(Context context) {
        Context context2 = context;
        Context context3 = context2;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 21) {
            return createFilesDir(new File(context2.getApplicationInfo().dataDir, "code_cache"));
        }
        return ContextCompatApi21.getCodeCacheDir(context2);
    }

    private static synchronized File createFilesDir(File file) {
        File file2 = file;
        synchronized (ContextCompat.class) {
            File file3 = file2;
            if (!file2.exists()) {
                if (!file2.mkdirs()) {
                    if (!file2.exists()) {
                        int w = Log.w(TAG, "Unable to create files subdir " + file2.getPath());
                        return null;
                    }
                    File file4 = file2;
                    return file4;
                }
            }
            File file5 = file2;
            return file5;
        }
    }

    public static Context createDeviceProtectedStorageContext(Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (!BuildCompat.isAtLeastN()) {
            return null;
        }
        return ContextCompatApi24.createDeviceProtectedStorageContext(context2);
    }

    public static boolean isDeviceProtectedStorage(Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (!BuildCompat.isAtLeastN()) {
            return false;
        }
        return ContextCompatApi24.isDeviceProtectedStorage(context2);
    }
}
