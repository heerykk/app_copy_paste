package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.app.BundleCompatGingerbread */
class BundleCompatGingerbread {
    private static final String TAG = "BundleCompatGingerbread";
    private static Method sGetIBinderMethod;
    private static boolean sGetIBinderMethodFetched;
    private static Method sPutIBinderMethod;
    private static boolean sPutIBinderMethodFetched;

    BundleCompatGingerbread() {
    }

    public static IBinder getBinder(Bundle bundle, String str) {
        Bundle bundle2 = bundle;
        String key = str;
        Bundle bundle3 = bundle2;
        String str2 = key;
        if (!sGetIBinderMethodFetched) {
            Class<Bundle> cls = Bundle.class;
            String str3 = "getIBinder";
            try {
                Class[] clsArr = new Class[1];
                clsArr[0] = String.class;
                sGetIBinderMethod = cls.getMethod(str3, clsArr);
                sGetIBinderMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int i = Log.i(TAG, "Failed to retrieve getIBinder method", e);
            }
            sGetIBinderMethodFetched = true;
        }
        if (sGetIBinderMethod != null) {
            try {
                return (IBinder) sGetIBinderMethod.invoke(bundle2, new Object[]{key});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                Throwable th = e2;
                int i2 = Log.i(TAG, "Failed to invoke getIBinder via reflection", e2);
                sGetIBinderMethod = null;
            }
        }
        return null;
    }

    public static void putBinder(Bundle bundle, String str, IBinder iBinder) {
        Bundle bundle2 = bundle;
        String key = str;
        IBinder binder = iBinder;
        Bundle bundle3 = bundle2;
        String str2 = key;
        IBinder iBinder2 = binder;
        if (!sPutIBinderMethodFetched) {
            Class<Bundle> cls = Bundle.class;
            String str3 = "putIBinder";
            try {
                Class[] clsArr = new Class[2];
                clsArr[0] = String.class;
                clsArr[1] = IBinder.class;
                sPutIBinderMethod = cls.getMethod(str3, clsArr);
                sPutIBinderMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int i = Log.i(TAG, "Failed to retrieve putIBinder method", e);
            }
            sPutIBinderMethodFetched = true;
        }
        if (sPutIBinderMethod != null) {
            try {
                Object invoke = sPutIBinderMethod.invoke(bundle2, new Object[]{key, binder});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                Throwable th = e2;
                int i2 = Log.i(TAG, "Failed to invoke putIBinder via reflection", e2);
                sPutIBinderMethod = null;
            }
        }
    }
}
