package android.support.p000v4.content;

import android.content.Context;
import android.os.Binder;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.p000v4.app.AppOpsManagerCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v4.content.PermissionChecker */
public final class PermissionChecker {
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_DENIED_APP_OP = -2;
    public static final int PERMISSION_GRANTED = 0;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.content.PermissionChecker$PermissionResult */
    public @interface PermissionResult {
    }

    private PermissionChecker() {
    }

    public static int checkPermission(@NonNull Context context, @NonNull String str, int i, int i2, String str2) {
        Context context2 = context;
        String permission = str;
        int pid = i;
        int uid = i2;
        String packageName = str2;
        Context context3 = context2;
        String str3 = permission;
        int i3 = pid;
        int i4 = uid;
        String packageName2 = packageName;
        if (context2.checkPermission(permission, pid, uid) == -1) {
            return -1;
        }
        String permissionToOp = AppOpsManagerCompat.permissionToOp(permission);
        String op = permissionToOp;
        if (permissionToOp == null) {
            return 0;
        }
        if (packageName == null) {
            String[] packagesForUid = context2.getPackageManager().getPackagesForUid(uid);
            String[] packageNames = packagesForUid;
            if (packagesForUid == null || packageNames.length <= 0) {
                return -1;
            }
            packageName2 = packageNames[0];
        }
        if (AppOpsManagerCompat.noteProxyOp(context2, op, packageName2) == 0) {
            return 0;
        }
        return -2;
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String str) {
        Context context2 = context;
        String permission = str;
        Context context3 = context2;
        String str2 = permission;
        return checkPermission(context2, permission, Process.myPid(), Process.myUid(), context2.getPackageName());
    }

    public static int checkCallingPermission(@NonNull Context context, @NonNull String str, String str2) {
        Context context2 = context;
        String permission = str;
        String packageName = str2;
        Context context3 = context2;
        String str3 = permission;
        String str4 = packageName;
        if (Binder.getCallingPid() == Process.myPid()) {
            return -1;
        }
        return checkPermission(context2, permission, Binder.getCallingPid(), Binder.getCallingUid(), packageName);
    }

    public static int checkCallingOrSelfPermission(@NonNull Context context, @NonNull String str) {
        Context context2 = context;
        String permission = str;
        Context context3 = context2;
        String str2 = permission;
        return checkPermission(context2, permission, Binder.getCallingPid(), Binder.getCallingUid(), Binder.getCallingPid() != Process.myPid() ? null : context2.getPackageName());
    }
}
