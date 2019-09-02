package android.support.p003v7.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ViewUtils */
public class ViewUtils {
    private static final String TAG = "ViewUtils";
    private static Method sComputeFitSystemWindowsMethod;

    static {
        if (VERSION.SDK_INT >= 18) {
            Class<View> cls = View.class;
            String str = "computeFitSystemWindows";
            try {
                Class[] clsArr = new Class[2];
                clsArr[0] = Rect.class;
                clsArr[1] = Rect.class;
                sComputeFitSystemWindowsMethod = cls.getDeclaredMethod(str, clsArr);
                if (!sComputeFitSystemWindowsMethod.isAccessible()) {
                    sComputeFitSystemWindowsMethod.setAccessible(true);
                }
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int d = Log.d(TAG, "Could not find method computeFitSystemWindows. Oh well.");
            }
        }
    }

    private ViewUtils() {
    }

    public static boolean isLayoutRtl(View view) {
        View view2 = view;
        View view3 = view2;
        return ViewCompat.getLayoutDirection(view2) == 1;
    }

    public static int combineMeasuredStates(int i, int i2) {
        int curState = i;
        int newState = i2;
        int i3 = curState;
        int i4 = newState;
        return curState | newState;
    }

    public static void computeFitSystemWindows(View view, Rect rect, Rect rect2) {
        View view2 = view;
        Rect inoutInsets = rect;
        Rect outLocalInsets = rect2;
        View view3 = view2;
        Rect rect3 = inoutInsets;
        Rect rect4 = outLocalInsets;
        if (sComputeFitSystemWindowsMethod != null) {
            try {
                Object invoke = sComputeFitSystemWindowsMethod.invoke(view2, new Object[]{inoutInsets, outLocalInsets});
            } catch (Exception e) {
                Exception exc = e;
                int d = Log.d(TAG, "Could not invoke computeFitSystemWindows", e);
            }
        }
    }

    public static void makeOptionalFitsSystemWindows(View view) {
        View view2 = view;
        View view3 = view2;
        if (VERSION.SDK_INT >= 16) {
            try {
                Method method = view2.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
                Method method2 = method;
                if (!method.isAccessible()) {
                    method2.setAccessible(true);
                }
                Object invoke = method2.invoke(view2, new Object[0]);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int d = Log.d(TAG, "Could not find method makeOptionalFitsSystemWindows. Oh well...");
            } catch (InvocationTargetException e2) {
                InvocationTargetException invocationTargetException = e2;
                int d2 = Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e2);
            } catch (IllegalAccessException e3) {
                IllegalAccessException illegalAccessException = e3;
                int d3 = Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e3);
            }
        }
    }
}
