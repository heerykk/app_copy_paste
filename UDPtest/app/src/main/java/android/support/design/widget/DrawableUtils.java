package android.support.design.widget;

import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.util.Log;
import java.lang.reflect.Method;

class DrawableUtils {
    private static final String LOG_TAG = "DrawableUtils";
    private static Method sSetConstantStateMethod;
    private static boolean sSetConstantStateMethodFetched;

    private DrawableUtils() {
    }

    static boolean setContainerConstantState(DrawableContainer drawableContainer, ConstantState constantState) {
        DrawableContainer drawable = drawableContainer;
        ConstantState constantState2 = constantState;
        DrawableContainer drawableContainer2 = drawable;
        ConstantState constantState3 = constantState2;
        return setContainerConstantStateV9(drawable, constantState2);
    }

    private static boolean setContainerConstantStateV9(DrawableContainer drawableContainer, ConstantState constantState) {
        DrawableContainer drawable = drawableContainer;
        ConstantState constantState2 = constantState;
        DrawableContainer drawableContainer2 = drawable;
        ConstantState constantState3 = constantState2;
        if (!sSetConstantStateMethodFetched) {
            Class<DrawableContainer> cls = DrawableContainer.class;
            String str = "setConstantState";
            try {
                Class[] clsArr = new Class[1];
                clsArr[0] = DrawableContainerState.class;
                sSetConstantStateMethod = cls.getDeclaredMethod(str, clsArr);
                sSetConstantStateMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int e2 = Log.e(LOG_TAG, "Could not fetch setConstantState(). Oh well.");
            }
            sSetConstantStateMethodFetched = true;
        }
        if (sSetConstantStateMethod != null) {
            try {
                Object invoke = sSetConstantStateMethod.invoke(drawable, new Object[]{constantState2});
                return true;
            } catch (Exception e3) {
                Exception exc = e3;
                int e4 = Log.e(LOG_TAG, "Could not invoke setConstantState(). Oh well.");
            }
        }
        return false;
    }
}
