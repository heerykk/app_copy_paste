package android.support.p000v4.app;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.app.AppOpsManagerCompat */
public final class AppOpsManagerCompat {
    private static final AppOpsManagerImpl IMPL;
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_IGNORED = 1;

    /* renamed from: android.support.v4.app.AppOpsManagerCompat$AppOpsManager23 */
    private static class AppOpsManager23 extends AppOpsManagerImpl {
        AppOpsManager23() {
        }

        public String permissionToOp(String str) {
            String permission = str;
            String str2 = permission;
            return AppOpsManagerCompat23.permissionToOp(permission);
        }

        public int noteOp(Context context, String str, int i, String str2) {
            Context context2 = context;
            String op = str;
            int uid = i;
            String packageName = str2;
            Context context3 = context2;
            String str3 = op;
            int i2 = uid;
            String str4 = packageName;
            return AppOpsManagerCompat23.noteOp(context2, op, uid, packageName);
        }

        public int noteProxyOp(Context context, String str, String str2) {
            Context context2 = context;
            String op = str;
            String proxiedPackageName = str2;
            Context context3 = context2;
            String str3 = op;
            String str4 = proxiedPackageName;
            return AppOpsManagerCompat23.noteProxyOp(context2, op, proxiedPackageName);
        }
    }

    /* renamed from: android.support.v4.app.AppOpsManagerCompat$AppOpsManagerImpl */
    private static class AppOpsManagerImpl {
        AppOpsManagerImpl() {
        }

        public String permissionToOp(String str) {
            String str2 = str;
            return null;
        }

        public int noteOp(Context context, String str, int i, String str2) {
            Context context2 = context;
            String str3 = str;
            int i2 = i;
            String str4 = str2;
            return 1;
        }

        public int noteProxyOp(Context context, String str, String str2) {
            Context context2 = context;
            String str3 = str;
            String str4 = str2;
            return 1;
        }
    }

    static {
        if (VERSION.SDK_INT < 23) {
            IMPL = new AppOpsManagerImpl();
        } else {
            IMPL = new AppOpsManager23();
        }
    }

    private AppOpsManagerCompat() {
    }

    public static String permissionToOp(@NonNull String str) {
        String permission = str;
        String str2 = permission;
        return IMPL.permissionToOp(permission);
    }

    public static int noteOp(@NonNull Context context, @NonNull String str, int i, @NonNull String str2) {
        Context context2 = context;
        String op = str;
        int uid = i;
        String packageName = str2;
        Context context3 = context2;
        String str3 = op;
        int i2 = uid;
        String str4 = packageName;
        return IMPL.noteOp(context2, op, uid, packageName);
    }

    public static int noteProxyOp(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        Context context2 = context;
        String op = str;
        String proxiedPackageName = str2;
        Context context3 = context2;
        String str3 = op;
        String str4 = proxiedPackageName;
        return IMPL.noteProxyOp(context2, op, proxiedPackageName);
    }
}
