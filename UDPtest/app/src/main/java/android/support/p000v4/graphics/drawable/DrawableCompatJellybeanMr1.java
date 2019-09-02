package android.support.p000v4.graphics.drawable;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.Method;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.graphics.drawable.DrawableCompatJellybeanMr1 */
class DrawableCompatJellybeanMr1 {
    private static final String TAG = "DrawableCompatJellybeanMr1";
    private static Method sGetLayoutDirectionMethod;
    private static boolean sGetLayoutDirectionMethodFetched;
    private static Method sSetLayoutDirectionMethod;
    private static boolean sSetLayoutDirectionMethodFetched;

    DrawableCompatJellybeanMr1() {
    }

    public static boolean setLayoutDirection(Drawable drawable, int i) {
        Drawable drawable2 = drawable;
        int layoutDirection = i;
        Drawable drawable3 = drawable2;
        int i2 = layoutDirection;
        if (!sSetLayoutDirectionMethodFetched) {
            Class<Drawable> cls = Drawable.class;
            String str = "setLayoutDirection";
            try {
                Class[] clsArr = new Class[1];
                clsArr[0] = Integer.TYPE;
                sSetLayoutDirectionMethod = cls.getDeclaredMethod(str, clsArr);
                sSetLayoutDirectionMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int i3 = Log.i(TAG, "Failed to retrieve setLayoutDirection(int) method", e);
            }
            sSetLayoutDirectionMethodFetched = true;
        }
        if (sSetLayoutDirectionMethod != null) {
            try {
                Method method = sSetLayoutDirectionMethod;
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(layoutDirection);
                Object invoke = method.invoke(drawable2, objArr);
                return true;
            } catch (Exception e2) {
                Exception exc = e2;
                int i4 = Log.i(TAG, "Failed to invoke setLayoutDirection(int) via reflection", e2);
                sSetLayoutDirectionMethod = null;
            }
        }
        return false;
    }

    public static int getLayoutDirection(Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (!sGetLayoutDirectionMethodFetched) {
            try {
                sGetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
                sGetLayoutDirectionMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int i = Log.i(TAG, "Failed to retrieve getLayoutDirection() method", e);
            }
            sGetLayoutDirectionMethodFetched = true;
        }
        if (sGetLayoutDirectionMethod != null) {
            try {
                return ((Integer) sGetLayoutDirectionMethod.invoke(drawable2, new Object[0])).intValue();
            } catch (Exception e2) {
                Exception exc = e2;
                int i2 = Log.i(TAG, "Failed to invoke getLayoutDirection() via reflection", e2);
                sGetLayoutDirectionMethod = null;
            }
        }
        return -1;
    }
}
