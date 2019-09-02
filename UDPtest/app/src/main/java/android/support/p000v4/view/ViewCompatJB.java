package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewParent;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.view.ViewCompatJB */
class ViewCompatJB {
    ViewCompatJB() {
    }

    public static boolean hasTransientState(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.hasTransientState();
    }

    public static void setHasTransientState(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        view2.setHasTransientState(z);
    }

    public static void postInvalidateOnAnimation(View view) {
        View view2 = view;
        View view3 = view2;
        view2.postInvalidateOnAnimation();
    }

    public static void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
        View view2 = view;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        View view3 = view2;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        view2.postInvalidate(left, top, right, bottom);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        View view2 = view;
        Runnable action = runnable;
        View view3 = view2;
        Runnable runnable2 = action;
        view2.postOnAnimation(action);
    }

    public static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
        View view2 = view;
        Runnable action = runnable;
        long delayMillis = j;
        View view3 = view2;
        Runnable runnable2 = action;
        long j2 = delayMillis;
        view2.postOnAnimationDelayed(action, delayMillis);
    }

    public static int getImportantForAccessibility(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getImportantForAccessibility();
    }

    public static void setImportantForAccessibility(View view, int i) {
        View view2 = view;
        int mode = i;
        View view3 = view2;
        int i2 = mode;
        view2.setImportantForAccessibility(mode);
    }

    public static boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        View view2 = view;
        int action = i;
        Bundle arguments = bundle;
        View view3 = view2;
        int i2 = action;
        Bundle bundle2 = arguments;
        return view2.performAccessibilityAction(action, arguments);
    }

    public static Object getAccessibilityNodeProvider(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getAccessibilityNodeProvider();
    }

    public static ViewParent getParentForAccessibility(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getParentForAccessibility();
    }

    public static int getMinimumWidth(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getMinimumWidth();
    }

    public static int getMinimumHeight(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getMinimumHeight();
    }

    public static void requestApplyInsets(View view) {
        View view2 = view;
        View view3 = view2;
        view2.requestFitSystemWindows();
    }

    public static boolean getFitsSystemWindows(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getFitsSystemWindows();
    }

    public static boolean hasOverlappingRendering(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.hasOverlappingRendering();
    }

    public static void setBackground(View view, Drawable drawable) {
        View view2 = view;
        Drawable drawable2 = drawable;
        View view3 = view2;
        Drawable drawable3 = drawable2;
        view2.setBackground(drawable2);
    }
}
