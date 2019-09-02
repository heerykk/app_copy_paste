package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import java.lang.reflect.Field;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.view.ViewCompatBase */
class ViewCompatBase {
    private static final String TAG = "ViewCompatBase";
    private static Field sMinHeightField;
    private static boolean sMinHeightFieldFetched;
    private static Field sMinWidthField;
    private static boolean sMinWidthFieldFetched;

    ViewCompatBase() {
    }

    static ColorStateList getBackgroundTintList(View view) {
        View view2 = view;
        View view3 = view2;
        return !(view2 instanceof TintableBackgroundView) ? null : ((TintableBackgroundView) view2).getSupportBackgroundTintList();
    }

    static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        View view2 = view;
        ColorStateList tintList = colorStateList;
        View view3 = view2;
        ColorStateList colorStateList2 = tintList;
        if (view2 instanceof TintableBackgroundView) {
            ((TintableBackgroundView) view2).setSupportBackgroundTintList(tintList);
        }
    }

    static Mode getBackgroundTintMode(View view) {
        View view2 = view;
        View view3 = view2;
        return !(view2 instanceof TintableBackgroundView) ? null : ((TintableBackgroundView) view2).getSupportBackgroundTintMode();
    }

    static void setBackgroundTintMode(View view, Mode mode) {
        View view2 = view;
        Mode mode2 = mode;
        View view3 = view2;
        Mode mode3 = mode2;
        if (view2 instanceof TintableBackgroundView) {
            ((TintableBackgroundView) view2).setSupportBackgroundTintMode(mode2);
        }
    }

    static boolean isLaidOut(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getWidth() > 0 && view2.getHeight() > 0;
    }

    static int getMinimumWidth(View view) {
        View view2 = view;
        View view3 = view2;
        if (!sMinWidthFieldFetched) {
            try {
                sMinWidthField = View.class.getDeclaredField("mMinWidth");
                sMinWidthField.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            sMinWidthFieldFetched = true;
        }
        if (sMinWidthField != null) {
            try {
                return ((Integer) sMinWidthField.get(view2)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    static int getMinimumHeight(View view) {
        View view2 = view;
        View view3 = view2;
        if (!sMinHeightFieldFetched) {
            try {
                sMinHeightField = View.class.getDeclaredField("mMinHeight");
                sMinHeightField.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            sMinHeightFieldFetched = true;
        }
        if (sMinHeightField != null) {
            try {
                return ((Integer) sMinHeightField.get(view2)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    static boolean isAttachedToWindow(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getWindowToken() != null;
    }

    static void offsetTopAndBottom(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        int currentTop = view2.getTop();
        view2.offsetTopAndBottom(offset);
        if (offset != 0) {
            ViewParent parent = view2.getParent();
            ViewParent parent2 = parent;
            if (!(parent instanceof View)) {
                view2.invalidate();
                return;
            }
            int abs = Math.abs(offset);
            int i3 = abs;
            ((View) parent2).invalidate(view2.getLeft(), currentTop - abs, view2.getRight(), currentTop + view2.getHeight() + abs);
        }
    }

    static void offsetLeftAndRight(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        int currentLeft = view2.getLeft();
        view2.offsetLeftAndRight(offset);
        if (offset != 0) {
            ViewParent parent = view2.getParent();
            ViewParent parent2 = parent;
            if (!(parent instanceof View)) {
                view2.invalidate();
                return;
            }
            int abs = Math.abs(offset);
            int i3 = abs;
            ((View) parent2).invalidate(currentLeft - abs, view2.getTop(), currentLeft + view2.getWidth() + abs, view2.getBottom());
        }
    }

    static Display getDisplay(View view) {
        View view2 = view;
        View view3 = view2;
        if (!isAttachedToWindow(view2)) {
            return null;
        }
        WindowManager windowManager = (WindowManager) view2.getContext().getSystemService("window");
        WindowManager windowManager2 = windowManager;
        return windowManager.getDefaultDisplay();
    }
}
