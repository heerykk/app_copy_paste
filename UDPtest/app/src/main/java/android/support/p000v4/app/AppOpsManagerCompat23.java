package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.app.AppOpsManagerCompat23 */
class AppOpsManagerCompat23 {
    AppOpsManagerCompat23() {
    }

    public static String permissionToOp(String str) {
        String permission = str;
        String str2 = permission;
        return AppOpsManager.permissionToOp(permission);
    }

    public static int noteOp(Context context, String str, int i, String str2) {
        Context context2 = context;
        String op = str;
        int uid = i;
        String packageName = str2;
        Context context3 = context2;
        String str3 = op;
        int i2 = uid;
        String str4 = packageName;
        AppOpsManager appOpsManager = (AppOpsManager) context2.getSystemService(AppOpsManager.class);
        AppOpsManager appOpsManager2 = appOpsManager;
        return appOpsManager.noteOp(op, uid, packageName);
    }

    public static int noteProxyOp(Context context, String str, String str2) {
        Context context2 = context;
        String op = str;
        String proxiedPackageName = str2;
        Context context3 = context2;
        String str3 = op;
        String str4 = proxiedPackageName;
        AppOpsManager appOpsManager = (AppOpsManager) context2.getSystemService(AppOpsManager.class);
        AppOpsManager appOpsManager2 = appOpsManager;
        return appOpsManager.noteProxyOp(op, proxiedPackageName);
    }
}
