package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewParent;
import android.view.WindowInsets;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.view.ViewCompatLollipop */
class ViewCompatLollipop {
    private static ThreadLocal<Rect> sThreadLocalRect;

    /* renamed from: android.support.v4.view.ViewCompatLollipop$OnApplyWindowInsetsListenerBridge */
    public interface OnApplyWindowInsetsListenerBridge {
        Object onApplyWindowInsets(View view, Object obj);
    }

    ViewCompatLollipop() {
    }

    public static void setTransitionName(View view, String str) {
        View view2 = view;
        String transitionName = str;
        View view3 = view2;
        String str2 = transitionName;
        view2.setTransitionName(transitionName);
    }

    public static String getTransitionName(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getTransitionName();
    }

    public static void requestApplyInsets(View view) {
        View view2 = view;
        View view3 = view2;
        view2.requestApplyInsets();
    }

    public static void setElevation(View view, float f) {
        View view2 = view;
        float elevation = f;
        View view3 = view2;
        float f2 = elevation;
        view2.setElevation(elevation);
    }

    public static float getElevation(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getElevation();
    }

    public static void setTranslationZ(View view, float f) {
        View view2 = view;
        float translationZ = f;
        View view3 = view2;
        float f2 = translationZ;
        view2.setTranslationZ(translationZ);
    }

    public static float getTranslationZ(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getTranslationZ();
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListenerBridge onApplyWindowInsetsListenerBridge) {
        View view2 = view;
        OnApplyWindowInsetsListenerBridge bridge = onApplyWindowInsetsListenerBridge;
        View view3 = view2;
        OnApplyWindowInsetsListenerBridge onApplyWindowInsetsListenerBridge2 = bridge;
        if (bridge != null) {
            final OnApplyWindowInsetsListenerBridge onApplyWindowInsetsListenerBridge3 = bridge;
            view2.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    View view2 = view;
                    WindowInsets insets = windowInsets;
                    View view3 = view2;
                    WindowInsets windowInsets2 = insets;
                    return (WindowInsets) onApplyWindowInsetsListenerBridge3.onApplyWindowInsets(view2, insets);
                }
            });
            return;
        }
        view2.setOnApplyWindowInsetsListener(null);
    }

    public static boolean isImportantForAccessibility(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.isImportantForAccessibility();
    }

    static ColorStateList getBackgroundTintList(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getBackgroundTintList();
    }

    static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        View view2 = view;
        ColorStateList tintList = colorStateList;
        View view3 = view2;
        ColorStateList colorStateList2 = tintList;
        view2.setBackgroundTintList(tintList);
        if (VERSION.SDK_INT == 21) {
            Drawable background = view2.getBackground();
            boolean hasTint = (view2.getBackgroundTintList() == null || view2.getBackgroundTintMode() == null) ? false : true;
            if (background != null && hasTint) {
                if (background.isStateful()) {
                    boolean state = background.setState(view2.getDrawableState());
                }
                view2.setBackground(background);
            }
        }
    }

    static Mode getBackgroundTintMode(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getBackgroundTintMode();
    }

    static void setBackgroundTintMode(View view, Mode mode) {
        View view2 = view;
        Mode mode2 = mode;
        View view3 = view2;
        Mode mode3 = mode2;
        view2.setBackgroundTintMode(mode2);
        if (VERSION.SDK_INT == 21) {
            Drawable background = view2.getBackground();
            boolean hasTint = (view2.getBackgroundTintList() == null || view2.getBackgroundTintMode() == null) ? false : true;
            if (background != null && hasTint) {
                if (background.isStateful()) {
                    boolean state = background.setState(view2.getDrawableState());
                }
                view2.setBackground(background);
            }
        }
    }

    public static Object onApplyWindowInsets(View view, Object obj) {
        View v = view;
        Object insets = obj;
        View view2 = v;
        Object insets2 = insets;
        WindowInsets unwrapped = (WindowInsets) insets;
        WindowInsets onApplyWindowInsets = v.onApplyWindowInsets(unwrapped);
        WindowInsets result = onApplyWindowInsets;
        if (onApplyWindowInsets == unwrapped) {
            return insets2;
        }
        return new WindowInsets(result);
    }

    public static Object dispatchApplyWindowInsets(View view, Object obj) {
        View v = view;
        Object insets = obj;
        View view2 = v;
        Object insets2 = insets;
        WindowInsets unwrapped = (WindowInsets) insets;
        WindowInsets dispatchApplyWindowInsets = v.dispatchApplyWindowInsets(unwrapped);
        WindowInsets result = dispatchApplyWindowInsets;
        if (dispatchApplyWindowInsets == unwrapped) {
            return insets2;
        }
        return new WindowInsets(result);
    }

    public static void setNestedScrollingEnabled(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        view2.setNestedScrollingEnabled(z);
    }

    public static boolean isNestedScrollingEnabled(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.isNestedScrollingEnabled();
    }

    public static boolean startNestedScroll(View view, int i) {
        View view2 = view;
        int axes = i;
        View view3 = view2;
        int i2 = axes;
        return view2.startNestedScroll(axes);
    }

    public static void stopNestedScroll(View view) {
        View view2 = view;
        View view3 = view2;
        view2.stopNestedScroll();
    }

    public static boolean hasNestedScrollingParent(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.hasNestedScrollingParent();
    }

    public static boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr) {
        View view2 = view;
        int dxConsumed = i;
        int dyConsumed = i2;
        int dxUnconsumed = i3;
        int dyUnconsumed = i4;
        int[] offsetInWindow = iArr;
        View view3 = view2;
        int i5 = dxConsumed;
        int i6 = dyConsumed;
        int i7 = dxUnconsumed;
        int i8 = dyUnconsumed;
        int[] iArr2 = offsetInWindow;
        return view2.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public static boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2) {
        View view2 = view;
        int dx = i;
        int dy = i2;
        int[] consumed = iArr;
        int[] offsetInWindow = iArr2;
        View view3 = view2;
        int i3 = dx;
        int i4 = dy;
        int[] iArr3 = consumed;
        int[] iArr4 = offsetInWindow;
        return view2.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public static boolean dispatchNestedFling(View view, float f, float f2, boolean z) {
        View view2 = view;
        float velocityX = f;
        float velocityY = f2;
        View view3 = view2;
        float f3 = velocityX;
        float f4 = velocityY;
        return view2.dispatchNestedFling(velocityX, velocityY, z);
    }

    public static boolean dispatchNestedPreFling(View view, float f, float f2) {
        View view2 = view;
        float velocityX = f;
        float velocityY = f2;
        View view3 = view2;
        float f3 = velocityX;
        float f4 = velocityY;
        return view2.dispatchNestedPreFling(velocityX, velocityY);
    }

    public static float getZ(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getZ();
    }

    public static void setZ(View view, float f) {
        View view2 = view;
        float z = f;
        View view3 = view2;
        float f2 = z;
        view2.setZ(z);
    }

    static void offsetTopAndBottom(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        Rect parentRect = getEmptyTempRect();
        boolean needInvalidateWorkaround = false;
        ViewParent parent = view2.getParent();
        ViewParent parent2 = parent;
        if (parent instanceof View) {
            View p = (View) parent2;
            parentRect.set(p.getLeft(), p.getTop(), p.getRight(), p.getBottom());
            needInvalidateWorkaround = !parentRect.intersects(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
        }
        ViewCompatHC.offsetTopAndBottom(view2, offset);
        if (needInvalidateWorkaround) {
            if (parentRect.intersect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom())) {
                ((View) parent2).invalidate(parentRect);
            }
        }
    }

    static void offsetLeftAndRight(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        Rect parentRect = getEmptyTempRect();
        boolean needInvalidateWorkaround = false;
        ViewParent parent = view2.getParent();
        ViewParent parent2 = parent;
        if (parent instanceof View) {
            View p = (View) parent2;
            parentRect.set(p.getLeft(), p.getTop(), p.getRight(), p.getBottom());
            needInvalidateWorkaround = !parentRect.intersects(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
        }
        ViewCompatHC.offsetLeftAndRight(view2, offset);
        if (needInvalidateWorkaround) {
            if (parentRect.intersect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom())) {
                ((View) parent2).invalidate(parentRect);
            }
        }
    }

    private static Rect getEmptyTempRect() {
        if (sThreadLocalRect == null) {
            sThreadLocalRect = new ThreadLocal<>();
        }
        Rect rect = (Rect) sThreadLocalRect.get();
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
            sThreadLocalRect.set(rect2);
        }
        rect2.setEmpty();
        return rect2;
    }
}
