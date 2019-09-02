package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.RequiresApi;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.app.NotificationManagerCompatKitKat */
class NotificationManagerCompatKitKat {
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    NotificationManagerCompatKitKat() {
    }

    public static boolean areNotificationsEnabled(Context context) {
        Context context2 = context;
        Context context3 = context2;
        AppOpsManager appOps = (AppOpsManager) context2.getSystemService("appops");
        ApplicationInfo appInfo = context2.getApplicationInfo();
        String pkg = context2.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        try {
            Class cls = Class.forName(AppOpsManager.class.getName());
            Class cls2 = cls;
            Class cls3 = cls;
            String str = CHECK_OP_NO_THROW;
            Class[] clsArr = new Class[3];
            clsArr[0] = Integer.TYPE;
            clsArr[1] = Integer.TYPE;
            clsArr[2] = String.class;
            Method checkOpNoThrowMethod = cls3.getMethod(str, clsArr);
            Field declaredField = cls2.getDeclaredField(OP_POST_NOTIFICATION);
            Field field = declaredField;
            int intValue = ((Integer) declaredField.get(Integer.class)).intValue();
            int i = intValue;
            Object[] objArr = new Object[3];
            objArr[0] = Integer.valueOf(intValue);
            objArr[1] = Integer.valueOf(uid);
            objArr[2] = pkg;
            return ((Integer) checkOpNoThrowMethod.invoke(appOps, objArr)).intValue() == 0;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException e) {
            Object obj = e;
            return true;
        }
    }
}
