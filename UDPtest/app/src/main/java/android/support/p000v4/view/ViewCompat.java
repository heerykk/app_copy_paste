package android.support.p000v4.view;

import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.p002os.BuildCompat;
import android.support.p000v4.view.ViewCompatLollipop.OnApplyWindowInsetsListenerBridge;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* renamed from: android.support.v4.view.ViewCompat */
public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    private static final long FAKE_FRAME_TIME = 10;
    static final ViewCompatImpl IMPL;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    @Deprecated
    public static final int OVER_SCROLL_ALWAYS = 0;
    @Deprecated
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    @Deprecated
    public static final int OVER_SCROLL_NEVER = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    private static final String TAG = "ViewCompat";

    /* renamed from: android.support.v4.view.ViewCompat$Api24ViewCompatImpl */
    static class Api24ViewCompatImpl extends MarshmallowViewCompatImpl {
        Api24ViewCompatImpl() {
        }

        public void setPointerIcon(View view, PointerIconCompat pointerIconCompat) {
            View view2 = view;
            PointerIconCompat pointerIconCompat2 = pointerIconCompat;
            View view3 = view2;
            PointerIconCompat pointerIconCompat3 = pointerIconCompat2;
            ViewCompatApi24.setPointerIcon(view2, pointerIconCompat2 == null ? null : pointerIconCompat2.getPointerIcon());
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$BaseViewCompatImpl */
    static class BaseViewCompatImpl implements ViewCompatImpl {
        private static Method sChildrenDrawingOrderMethod;
        private Method mDispatchFinishTemporaryDetach;
        private Method mDispatchStartTemporaryDetach;
        private boolean mTempDetachBound;
        WeakHashMap<View, ViewPropertyAnimatorCompat> mViewPropertyAnimatorCompatMap = null;

        BaseViewCompatImpl() {
        }

        public boolean canScrollHorizontally(View view, int i) {
            View v = view;
            int direction = i;
            View view2 = v;
            int i2 = direction;
            return (v instanceof ScrollingView) && canScrollingViewScrollHorizontally((ScrollingView) v, direction);
        }

        public boolean canScrollVertically(View view, int i) {
            View v = view;
            int direction = i;
            View view2 = v;
            int i2 = direction;
            return (v instanceof ScrollingView) && canScrollingViewScrollVertically((ScrollingView) v, direction);
        }

        public void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
            View view2 = view;
            AccessibilityDelegateCompat accessibilityDelegateCompat2 = accessibilityDelegateCompat;
        }

        public boolean hasAccessibilityDelegate(View view) {
            View view2 = view;
            return false;
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View view2 = view;
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View view2 = view;
            AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View view2 = view;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = accessibilityNodeInfoCompat;
        }

        public boolean hasTransientState(View view) {
            View view2 = view;
            return false;
        }

        public void setHasTransientState(View view, boolean z) {
            View view2 = view;
            boolean z2 = z;
        }

        public void postInvalidateOnAnimation(View view) {
            View view2 = view;
            View view3 = view2;
            view2.invalidate();
        }

        public void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
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
            view2.invalidate(left, top, right, bottom);
        }

        public void postOnAnimation(View view, Runnable runnable) {
            View view2 = view;
            Runnable action = runnable;
            View view3 = view2;
            Runnable runnable2 = action;
            boolean postDelayed = view2.postDelayed(action, getFrameTime());
        }

        public void postOnAnimationDelayed(View view, Runnable runnable, long j) {
            View view2 = view;
            Runnable action = runnable;
            long delayMillis = j;
            View view3 = view2;
            Runnable runnable2 = action;
            long j2 = delayMillis;
            boolean postDelayed = view2.postDelayed(action, getFrameTime() + delayMillis);
        }

        /* access modifiers changed from: 0000 */
        public long getFrameTime() {
            return ViewCompat.FAKE_FRAME_TIME;
        }

        public int getImportantForAccessibility(View view) {
            View view2 = view;
            return 0;
        }

        public void setImportantForAccessibility(View view, int i) {
            View view2 = view;
            int i2 = i;
        }

        public boolean isImportantForAccessibility(View view) {
            View view2 = view;
            return true;
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            View view2 = view;
            int i2 = i;
            Bundle bundle2 = bundle;
            return false;
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            View view2 = view;
            return null;
        }

        public float getAlpha(View view) {
            View view2 = view;
            return 1.0f;
        }

        public void setLayerType(View view, int i, Paint paint) {
            View view2 = view;
            int i2 = i;
            Paint paint2 = paint;
        }

        public int getLayerType(View view) {
            View view2 = view;
            return 0;
        }

        public int getLabelFor(View view) {
            View view2 = view;
            return 0;
        }

        public void setLabelFor(View view, int i) {
            View view2 = view;
            int i2 = i;
        }

        public void setLayerPaint(View view, Paint paint) {
            View view2 = view;
            Paint paint2 = paint;
        }

        public int getLayoutDirection(View view) {
            View view2 = view;
            return 0;
        }

        public void setLayoutDirection(View view, int i) {
            View view2 = view;
            int i2 = i;
        }

        public ViewParent getParentForAccessibility(View view) {
            View view2 = view;
            View view3 = view2;
            return view2.getParent();
        }

        public int resolveSizeAndState(int i, int i2, int i3) {
            int size = i;
            int measureSpec = i2;
            int i4 = size;
            int i5 = measureSpec;
            int i6 = i3;
            return View.resolveSize(size, measureSpec);
        }

        public int getMeasuredWidthAndState(View view) {
            View view2 = view;
            View view3 = view2;
            return view2.getMeasuredWidth();
        }

        public int getMeasuredHeightAndState(View view) {
            View view2 = view;
            View view3 = view2;
            return view2.getMeasuredHeight();
        }

        public int getMeasuredState(View view) {
            View view2 = view;
            return 0;
        }

        public int getAccessibilityLiveRegion(View view) {
            View view2 = view;
            return 0;
        }

        public void setAccessibilityLiveRegion(View view, int i) {
            View view2 = view;
            int i2 = i;
        }

        public int getPaddingStart(View view) {
            View view2 = view;
            View view3 = view2;
            return view2.getPaddingLeft();
        }

        public int getPaddingEnd(View view) {
            View view2 = view;
            View view3 = view2;
            return view2.getPaddingRight();
        }

        public void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
            View view2 = view;
            int start = i;
            int top = i2;
            int end = i3;
            int bottom = i4;
            View view3 = view2;
            int i5 = start;
            int i6 = top;
            int i7 = end;
            int i8 = bottom;
            view2.setPadding(start, top, end, bottom);
        }

        public void dispatchStartTemporaryDetach(View view) {
            View view2 = view;
            View view3 = view2;
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchStartTemporaryDetach == null) {
                view2.onStartTemporaryDetach();
                return;
            }
            try {
                Object invoke = this.mDispatchStartTemporaryDetach.invoke(view2, new Object[0]);
            } catch (Exception e) {
                Exception exc = e;
                int d = Log.d(ViewCompat.TAG, "Error calling dispatchStartTemporaryDetach", e);
            }
        }

        public void dispatchFinishTemporaryDetach(View view) {
            View view2 = view;
            View view3 = view2;
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchFinishTemporaryDetach == null) {
                view2.onFinishTemporaryDetach();
                return;
            }
            try {
                Object invoke = this.mDispatchFinishTemporaryDetach.invoke(view2, new Object[0]);
            } catch (Exception e) {
                Exception exc = e;
                int d = Log.d(ViewCompat.TAG, "Error calling dispatchFinishTemporaryDetach", e);
            }
        }

        public boolean hasOverlappingRendering(View view) {
            View view2 = view;
            return true;
        }

        private void bindTempDetach() {
            try {
                this.mDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
                this.mDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int e2 = Log.e(ViewCompat.TAG, "Couldn't find method", e);
            }
            this.mTempDetachBound = true;
        }

        public float getTranslationX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getTranslationY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getX(View view) {
            View view2 = view;
            View view3 = view2;
            return (float) view2.getLeft();
        }

        public float getY(View view) {
            View view2 = view;
            View view3 = view2;
            return (float) view2.getTop();
        }

        public float getRotation(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getRotationX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getRotationY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getScaleX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getScaleY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public Matrix getMatrix(View view) {
            View view2 = view;
            return null;
        }

        public int getMinimumWidth(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatBase.getMinimumWidth(view2);
        }

        public int getMinimumHeight(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatBase.getMinimumHeight(view2);
        }

        public ViewPropertyAnimatorCompat animate(View view) {
            View view2 = view;
            View view3 = view2;
            return new ViewPropertyAnimatorCompat(view2);
        }

        public void setRotation(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setTranslationX(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setTranslationY(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setAlpha(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setRotationX(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setRotationY(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setScaleX(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setScaleY(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setX(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setY(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setPivotX(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void setPivotY(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public float getPivotX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getPivotY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public void setTransitionName(View view, String str) {
            View view2 = view;
            String str2 = str;
        }

        public String getTransitionName(View view) {
            View view2 = view;
            return null;
        }

        public int getWindowSystemUiVisibility(View view) {
            View view2 = view;
            return 0;
        }

        public void requestApplyInsets(View view) {
            View view2 = view;
        }

        public void setElevation(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public float getElevation(View view) {
            View view2 = view;
            return 0.0f;
        }

        public void setTranslationZ(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public float getTranslationZ(View view) {
            View view2 = view;
            return 0.0f;
        }

        public void setClipBounds(View view, Rect rect) {
            View view2 = view;
            Rect rect2 = rect;
        }

        public Rect getClipBounds(View view) {
            View view2 = view;
            return null;
        }

        public void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
            ViewGroup viewGroup2 = viewGroup;
            ViewGroup viewGroup3 = viewGroup2;
            boolean enabled = z;
            if (sChildrenDrawingOrderMethod == null) {
                Class<ViewGroup> cls = ViewGroup.class;
                String str = "setChildrenDrawingOrderEnabled";
                try {
                    Class[] clsArr = new Class[1];
                    clsArr[0] = Boolean.TYPE;
                    sChildrenDrawingOrderMethod = cls.getDeclaredMethod(str, clsArr);
                } catch (NoSuchMethodException e) {
                    NoSuchMethodException noSuchMethodException = e;
                    int e2 = Log.e(ViewCompat.TAG, "Unable to find childrenDrawingOrderEnabled", e);
                }
                sChildrenDrawingOrderMethod.setAccessible(true);
            }
            try {
                Method method = sChildrenDrawingOrderMethod;
                Object[] objArr = new Object[1];
                objArr[0] = Boolean.valueOf(enabled);
                Object invoke = method.invoke(viewGroup2, objArr);
            } catch (IllegalAccessException e3) {
                IllegalAccessException illegalAccessException = e3;
                int e4 = Log.e(ViewCompat.TAG, "Unable to invoke childrenDrawingOrderEnabled", e3);
            } catch (IllegalArgumentException e5) {
                IllegalArgumentException illegalArgumentException = e5;
                int e6 = Log.e(ViewCompat.TAG, "Unable to invoke childrenDrawingOrderEnabled", e5);
            } catch (InvocationTargetException e7) {
                InvocationTargetException invocationTargetException = e7;
                int e8 = Log.e(ViewCompat.TAG, "Unable to invoke childrenDrawingOrderEnabled", e7);
            }
        }

        public boolean getFitsSystemWindows(View view) {
            View view2 = view;
            return false;
        }

        public void setFitsSystemWindows(View view, boolean z) {
            View view2 = view;
            boolean z2 = z;
        }

        public void jumpDrawablesToCurrentState(View view) {
            View view2 = view;
        }

        public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            View view2 = view;
            OnApplyWindowInsetsListener onApplyWindowInsetsListener2 = onApplyWindowInsetsListener;
        }

        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            WindowInsetsCompat insets = windowInsetsCompat;
            View view2 = view;
            WindowInsetsCompat windowInsetsCompat2 = insets;
            return insets;
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            WindowInsetsCompat insets = windowInsetsCompat;
            View view2 = view;
            WindowInsetsCompat windowInsetsCompat2 = insets;
            return insets;
        }

        public void setSaveFromParentEnabled(View view, boolean z) {
            View view2 = view;
            boolean z2 = z;
        }

        public void setActivated(View view, boolean z) {
            View view2 = view;
            boolean z2 = z;
        }

        public boolean isPaddingRelative(View view) {
            View view2 = view;
            return false;
        }

        public void setNestedScrollingEnabled(View view, boolean z) {
            View view2 = view;
            View view3 = view2;
            boolean enabled = z;
            if (view2 instanceof NestedScrollingChild) {
                ((NestedScrollingChild) view2).setNestedScrollingEnabled(enabled);
            }
        }

        public boolean isNestedScrollingEnabled(View view) {
            View view2 = view;
            View view3 = view2;
            if (!(view2 instanceof NestedScrollingChild)) {
                return false;
            }
            return ((NestedScrollingChild) view2).isNestedScrollingEnabled();
        }

        public void setBackground(View view, Drawable drawable) {
            View view2 = view;
            Drawable background = drawable;
            View view3 = view2;
            Drawable drawable2 = background;
            view2.setBackgroundDrawable(background);
        }

        public ColorStateList getBackgroundTintList(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatBase.getBackgroundTintList(view2);
        }

        public void setBackgroundTintList(View view, ColorStateList colorStateList) {
            View view2 = view;
            ColorStateList tintList = colorStateList;
            View view3 = view2;
            ColorStateList colorStateList2 = tintList;
            ViewCompatBase.setBackgroundTintList(view2, tintList);
        }

        public void setBackgroundTintMode(View view, Mode mode) {
            View view2 = view;
            Mode mode2 = mode;
            View view3 = view2;
            Mode mode3 = mode2;
            ViewCompatBase.setBackgroundTintMode(view2, mode2);
        }

        public Mode getBackgroundTintMode(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatBase.getBackgroundTintMode(view2);
        }

        private boolean canScrollingViewScrollHorizontally(ScrollingView scrollingView, int i) {
            ScrollingView view = scrollingView;
            int direction = i;
            ScrollingView scrollingView2 = view;
            int i2 = direction;
            int offset = view.computeHorizontalScrollOffset();
            int computeHorizontalScrollRange = view.computeHorizontalScrollRange() - view.computeHorizontalScrollExtent();
            int range = computeHorizontalScrollRange;
            if (computeHorizontalScrollRange == 0) {
                return false;
            }
            if (direction >= 0) {
                return offset < range + -1;
            }
            return offset > 0;
        }

        private boolean canScrollingViewScrollVertically(ScrollingView scrollingView, int i) {
            ScrollingView view = scrollingView;
            int direction = i;
            ScrollingView scrollingView2 = view;
            int i2 = direction;
            int offset = view.computeVerticalScrollOffset();
            int computeVerticalScrollRange = view.computeVerticalScrollRange() - view.computeVerticalScrollExtent();
            int range = computeVerticalScrollRange;
            if (computeVerticalScrollRange == 0) {
                return false;
            }
            if (direction >= 0) {
                return offset < range + -1;
            }
            return offset > 0;
        }

        public boolean startNestedScroll(View view, int i) {
            View view2 = view;
            int axes = i;
            View view3 = view2;
            int i2 = axes;
            if (!(view2 instanceof NestedScrollingChild)) {
                return false;
            }
            return ((NestedScrollingChild) view2).startNestedScroll(axes);
        }

        public void stopNestedScroll(View view) {
            View view2 = view;
            View view3 = view2;
            if (view2 instanceof NestedScrollingChild) {
                ((NestedScrollingChild) view2).stopNestedScroll();
            }
        }

        public boolean hasNestedScrollingParent(View view) {
            View view2 = view;
            View view3 = view2;
            if (!(view2 instanceof NestedScrollingChild)) {
                return false;
            }
            return ((NestedScrollingChild) view2).hasNestedScrollingParent();
        }

        public boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr) {
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
            if (!(view2 instanceof NestedScrollingChild)) {
                return false;
            }
            return ((NestedScrollingChild) view2).dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        }

        public boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2) {
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
            if (!(view2 instanceof NestedScrollingChild)) {
                return false;
            }
            return ((NestedScrollingChild) view2).dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
        }

        public boolean dispatchNestedFling(View view, float f, float f2, boolean z) {
            View view2 = view;
            float velocityX = f;
            float velocityY = f2;
            View view3 = view2;
            float f3 = velocityX;
            float f4 = velocityY;
            boolean consumed = z;
            if (!(view2 instanceof NestedScrollingChild)) {
                return false;
            }
            return ((NestedScrollingChild) view2).dispatchNestedFling(velocityX, velocityY, consumed);
        }

        public boolean dispatchNestedPreFling(View view, float f, float f2) {
            View view2 = view;
            float velocityX = f;
            float velocityY = f2;
            View view3 = view2;
            float f3 = velocityX;
            float f4 = velocityY;
            if (!(view2 instanceof NestedScrollingChild)) {
                return false;
            }
            return ((NestedScrollingChild) view2).dispatchNestedPreFling(velocityX, velocityY);
        }

        public boolean isInLayout(View view) {
            View view2 = view;
            return false;
        }

        public boolean isLaidOut(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatBase.isLaidOut(view2);
        }

        public boolean isLayoutDirectionResolved(View view) {
            View view2 = view;
            return false;
        }

        public int combineMeasuredStates(int i, int i2) {
            int curState = i;
            int newState = i2;
            int i3 = curState;
            int i4 = newState;
            return curState | newState;
        }

        public float getZ(View view) {
            View view2 = view;
            View view3 = view2;
            return getTranslationZ(view2) + getElevation(view2);
        }

        public void setZ(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public boolean isAttachedToWindow(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatBase.isAttachedToWindow(view2);
        }

        public boolean hasOnClickListeners(View view) {
            View view2 = view;
            return false;
        }

        public int getScrollIndicators(View view) {
            View view2 = view;
            return 0;
        }

        public void setScrollIndicators(View view, int i) {
            View view2 = view;
            int i2 = i;
        }

        public void setScrollIndicators(View view, int i, int i2) {
            View view2 = view;
            int i3 = i;
            int i4 = i2;
        }

        public void offsetLeftAndRight(View view, int i) {
            View view2 = view;
            int offset = i;
            View view3 = view2;
            int i2 = offset;
            ViewCompatBase.offsetLeftAndRight(view2, offset);
        }

        public void offsetTopAndBottom(View view, int i) {
            View view2 = view;
            int offset = i;
            View view3 = view2;
            int i2 = offset;
            ViewCompatBase.offsetTopAndBottom(view2, offset);
        }

        public void setPointerIcon(View view, PointerIconCompat pointerIconCompat) {
            View view2 = view;
            PointerIconCompat pointerIconCompat2 = pointerIconCompat;
        }

        public Display getDisplay(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatBase.getDisplay(view2);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$FocusDirection */
    public @interface FocusDirection {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$FocusRealDirection */
    public @interface FocusRealDirection {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$FocusRelativeDirection */
    public @interface FocusRelativeDirection {
    }

    /* renamed from: android.support.v4.view.ViewCompat$HCViewCompatImpl */
    static class HCViewCompatImpl extends BaseViewCompatImpl {
        HCViewCompatImpl() {
        }

        /* access modifiers changed from: 0000 */
        public long getFrameTime() {
            return ViewCompatHC.getFrameTime();
        }

        public float getAlpha(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getAlpha(view2);
        }

        public void setLayerType(View view, int i, Paint paint) {
            View view2 = view;
            int layerType = i;
            Paint paint2 = paint;
            View view3 = view2;
            int i2 = layerType;
            Paint paint3 = paint2;
            ViewCompatHC.setLayerType(view2, layerType, paint2);
        }

        public int getLayerType(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getLayerType(view2);
        }

        public void setLayerPaint(View view, Paint paint) {
            View view2 = view;
            Paint paint2 = paint;
            View view3 = view2;
            Paint paint3 = paint2;
            setLayerType(view2, getLayerType(view2), paint2);
            view2.invalidate();
        }

        public int resolveSizeAndState(int i, int i2, int i3) {
            int size = i;
            int measureSpec = i2;
            int childMeasuredState = i3;
            int i4 = size;
            int i5 = measureSpec;
            int i6 = childMeasuredState;
            return ViewCompatHC.resolveSizeAndState(size, measureSpec, childMeasuredState);
        }

        public int getMeasuredWidthAndState(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getMeasuredWidthAndState(view2);
        }

        public int getMeasuredHeightAndState(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getMeasuredHeightAndState(view2);
        }

        public int getMeasuredState(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getMeasuredState(view2);
        }

        public float getTranslationX(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getTranslationX(view2);
        }

        public float getTranslationY(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getTranslationY(view2);
        }

        public Matrix getMatrix(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getMatrix(view2);
        }

        public void setTranslationX(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setTranslationX(view2, value);
        }

        public void setTranslationY(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setTranslationY(view2, value);
        }

        public void setAlpha(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setAlpha(view2, value);
        }

        public void setX(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setX(view2, value);
        }

        public void setY(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setY(view2, value);
        }

        public void setRotation(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setRotation(view2, value);
        }

        public void setRotationX(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setRotationX(view2, value);
        }

        public void setRotationY(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setRotationY(view2, value);
        }

        public void setScaleX(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setScaleX(view2, value);
        }

        public void setScaleY(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setScaleY(view2, value);
        }

        public void setPivotX(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setPivotX(view2, value);
        }

        public void setPivotY(View view, float f) {
            View view2 = view;
            float value = f;
            View view3 = view2;
            float f2 = value;
            ViewCompatHC.setPivotY(view2, value);
        }

        public float getX(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getX(view2);
        }

        public float getY(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getY(view2);
        }

        public float getRotation(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getRotation(view2);
        }

        public float getRotationX(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getRotationX(view2);
        }

        public float getRotationY(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getRotationY(view2);
        }

        public float getScaleX(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getScaleX(view2);
        }

        public float getScaleY(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getScaleY(view2);
        }

        public float getPivotX(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getPivotX(view2);
        }

        public float getPivotY(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatHC.getPivotY(view2);
        }

        public void jumpDrawablesToCurrentState(View view) {
            View view2 = view;
            View view3 = view2;
            ViewCompatHC.jumpDrawablesToCurrentState(view2);
        }

        public void setSaveFromParentEnabled(View view, boolean z) {
            View view2 = view;
            View view3 = view2;
            ViewCompatHC.setSaveFromParentEnabled(view2, z);
        }

        public void setActivated(View view, boolean z) {
            View view2 = view;
            View view3 = view2;
            ViewCompatHC.setActivated(view2, z);
        }

        public int combineMeasuredStates(int i, int i2) {
            int curState = i;
            int newState = i2;
            int i3 = curState;
            int i4 = newState;
            return ViewCompatHC.combineMeasuredStates(curState, newState);
        }

        public void offsetLeftAndRight(View view, int i) {
            View view2 = view;
            int offset = i;
            View view3 = view2;
            int i2 = offset;
            ViewCompatHC.offsetLeftAndRight(view2, offset);
        }

        public void offsetTopAndBottom(View view, int i) {
            View view2 = view;
            int offset = i;
            View view3 = view2;
            int i2 = offset;
            ViewCompatHC.offsetTopAndBottom(view2, offset);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$ICSMr1ViewCompatImpl */
    static class ICSMr1ViewCompatImpl extends ICSViewCompatImpl {
        ICSMr1ViewCompatImpl() {
        }

        public boolean hasOnClickListeners(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatICSMr1.hasOnClickListeners(view2);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$ICSViewCompatImpl */
    static class ICSViewCompatImpl extends HCViewCompatImpl {
        static boolean accessibilityDelegateCheckFailed = false;
        static Field mAccessibilityDelegateField;

        ICSViewCompatImpl() {
        }

        public boolean canScrollHorizontally(View view, int i) {
            View v = view;
            int direction = i;
            View view2 = v;
            int i2 = direction;
            return ViewCompatICS.canScrollHorizontally(v, direction);
        }

        public boolean canScrollVertically(View view, int i) {
            View v = view;
            int direction = i;
            View view2 = v;
            int i2 = direction;
            return ViewCompatICS.canScrollVertically(v, direction);
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View v = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = v;
            AccessibilityEvent accessibilityEvent2 = event;
            ViewCompatICS.onPopulateAccessibilityEvent(v, event);
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View v = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = v;
            AccessibilityEvent accessibilityEvent2 = event;
            ViewCompatICS.onInitializeAccessibilityEvent(v, event);
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View v = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = v;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            ViewCompatICS.onInitializeAccessibilityNodeInfo(v, info.getInfo());
        }

        public void setAccessibilityDelegate(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat) {
            Object obj;
            View v = view;
            AccessibilityDelegateCompat delegate = accessibilityDelegateCompat;
            View view2 = v;
            AccessibilityDelegateCompat accessibilityDelegateCompat2 = delegate;
            if (delegate != null) {
                obj = delegate.getBridge();
            } else {
                obj = null;
            }
            ViewCompatICS.setAccessibilityDelegate(v, obj);
        }

        public boolean hasAccessibilityDelegate(View view) {
            boolean z;
            View v = view;
            View view2 = v;
            if (accessibilityDelegateCheckFailed) {
                return false;
            }
            if (mAccessibilityDelegateField == null) {
                try {
                    mAccessibilityDelegateField = View.class.getDeclaredField("mAccessibilityDelegate");
                    mAccessibilityDelegateField.setAccessible(true);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    accessibilityDelegateCheckFailed = true;
                    return false;
                }
            }
            try {
                if (mAccessibilityDelegateField.get(v) == null) {
                    z = false;
                } else {
                    z = true;
                }
                return z;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                accessibilityDelegateCheckFailed = true;
                return false;
            }
        }

        public ViewPropertyAnimatorCompat animate(View view) {
            View view2 = view;
            View view3 = view2;
            if (this.mViewPropertyAnimatorCompatMap == null) {
                this.mViewPropertyAnimatorCompatMap = new WeakHashMap();
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat) this.mViewPropertyAnimatorCompatMap.get(view2);
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            if (viewPropertyAnimatorCompat == null) {
                vpa = new ViewPropertyAnimatorCompat(view2);
                Object put = this.mViewPropertyAnimatorCompatMap.put(view2, vpa);
            }
            return vpa;
        }

        public void setFitsSystemWindows(View view, boolean z) {
            View view2 = view;
            View view3 = view2;
            ViewCompatICS.setFitsSystemWindows(view2, z);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$JBViewCompatImpl */
    static class JBViewCompatImpl extends ICSMr1ViewCompatImpl {
        JBViewCompatImpl() {
        }

        public boolean hasTransientState(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJB.hasTransientState(view2);
        }

        public void setHasTransientState(View view, boolean z) {
            View view2 = view;
            View view3 = view2;
            ViewCompatJB.setHasTransientState(view2, z);
        }

        public void postInvalidateOnAnimation(View view) {
            View view2 = view;
            View view3 = view2;
            ViewCompatJB.postInvalidateOnAnimation(view2);
        }

        public void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
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
            ViewCompatJB.postInvalidateOnAnimation(view2, left, top, right, bottom);
        }

        public void postOnAnimation(View view, Runnable runnable) {
            View view2 = view;
            Runnable action = runnable;
            View view3 = view2;
            Runnable runnable2 = action;
            ViewCompatJB.postOnAnimation(view2, action);
        }

        public void postOnAnimationDelayed(View view, Runnable runnable, long j) {
            View view2 = view;
            Runnable action = runnable;
            long delayMillis = j;
            View view3 = view2;
            Runnable runnable2 = action;
            long j2 = delayMillis;
            ViewCompatJB.postOnAnimationDelayed(view2, action, delayMillis);
        }

        public int getImportantForAccessibility(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJB.getImportantForAccessibility(view2);
        }

        public void setImportantForAccessibility(View view, int i) {
            View view2 = view;
            int mode = i;
            View view3 = view2;
            int mode2 = mode;
            if (mode == 4) {
                mode2 = 2;
            }
            ViewCompatJB.setImportantForAccessibility(view2, mode2);
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            View view2 = view;
            int action = i;
            Bundle arguments = bundle;
            View view3 = view2;
            int i2 = action;
            Bundle bundle2 = arguments;
            return ViewCompatJB.performAccessibilityAction(view2, action, arguments);
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            View view2 = view;
            View view3 = view2;
            Object accessibilityNodeProvider = ViewCompatJB.getAccessibilityNodeProvider(view2);
            Object compat = accessibilityNodeProvider;
            if (accessibilityNodeProvider == null) {
                return null;
            }
            return new AccessibilityNodeProviderCompat(compat);
        }

        public ViewParent getParentForAccessibility(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJB.getParentForAccessibility(view2);
        }

        public int getMinimumWidth(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJB.getMinimumWidth(view2);
        }

        public int getMinimumHeight(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJB.getMinimumHeight(view2);
        }

        public void requestApplyInsets(View view) {
            View view2 = view;
            View view3 = view2;
            ViewCompatJB.requestApplyInsets(view2);
        }

        public boolean getFitsSystemWindows(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJB.getFitsSystemWindows(view2);
        }

        public boolean hasOverlappingRendering(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJB.hasOverlappingRendering(view2);
        }

        public void setBackground(View view, Drawable drawable) {
            View view2 = view;
            Drawable background = drawable;
            View view3 = view2;
            Drawable drawable2 = background;
            ViewCompatJB.setBackground(view2, background);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$JbMr1ViewCompatImpl */
    static class JbMr1ViewCompatImpl extends JBViewCompatImpl {
        JbMr1ViewCompatImpl() {
        }

        public int getLabelFor(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr1.getLabelFor(view2);
        }

        public void setLabelFor(View view, int i) {
            View view2 = view;
            int id = i;
            View view3 = view2;
            int i2 = id;
            ViewCompatJellybeanMr1.setLabelFor(view2, id);
        }

        public void setLayerPaint(View view, Paint paint) {
            View view2 = view;
            Paint paint2 = paint;
            View view3 = view2;
            Paint paint3 = paint2;
            ViewCompatJellybeanMr1.setLayerPaint(view2, paint2);
        }

        public int getLayoutDirection(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr1.getLayoutDirection(view2);
        }

        public void setLayoutDirection(View view, int i) {
            View view2 = view;
            int layoutDirection = i;
            View view3 = view2;
            int i2 = layoutDirection;
            ViewCompatJellybeanMr1.setLayoutDirection(view2, layoutDirection);
        }

        public int getPaddingStart(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr1.getPaddingStart(view2);
        }

        public int getPaddingEnd(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr1.getPaddingEnd(view2);
        }

        public void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
            View view2 = view;
            int start = i;
            int top = i2;
            int end = i3;
            int bottom = i4;
            View view3 = view2;
            int i5 = start;
            int i6 = top;
            int i7 = end;
            int i8 = bottom;
            ViewCompatJellybeanMr1.setPaddingRelative(view2, start, top, end, bottom);
        }

        public int getWindowSystemUiVisibility(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr1.getWindowSystemUiVisibility(view2);
        }

        public boolean isPaddingRelative(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr1.isPaddingRelative(view2);
        }

        public Display getDisplay(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr1.getDisplay(view2);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$JbMr2ViewCompatImpl */
    static class JbMr2ViewCompatImpl extends JbMr1ViewCompatImpl {
        JbMr2ViewCompatImpl() {
        }

        public void setClipBounds(View view, Rect rect) {
            View view2 = view;
            Rect clipBounds = rect;
            View view3 = view2;
            Rect rect2 = clipBounds;
            ViewCompatJellybeanMr2.setClipBounds(view2, clipBounds);
        }

        public Rect getClipBounds(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr2.getClipBounds(view2);
        }

        public boolean isInLayout(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatJellybeanMr2.isInLayout(view2);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$KitKatViewCompatImpl */
    static class KitKatViewCompatImpl extends JbMr2ViewCompatImpl {
        KitKatViewCompatImpl() {
        }

        public int getAccessibilityLiveRegion(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatKitKat.getAccessibilityLiveRegion(view2);
        }

        public void setAccessibilityLiveRegion(View view, int i) {
            View view2 = view;
            int mode = i;
            View view3 = view2;
            int i2 = mode;
            ViewCompatKitKat.setAccessibilityLiveRegion(view2, mode);
        }

        public void setImportantForAccessibility(View view, int i) {
            View view2 = view;
            int mode = i;
            View view3 = view2;
            int i2 = mode;
            ViewCompatJB.setImportantForAccessibility(view2, mode);
        }

        public boolean isLaidOut(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatKitKat.isLaidOut(view2);
        }

        public boolean isLayoutDirectionResolved(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatKitKat.isLayoutDirectionResolved(view2);
        }

        public boolean isAttachedToWindow(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatKitKat.isAttachedToWindow(view2);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$LollipopViewCompatImpl */
    static class LollipopViewCompatImpl extends KitKatViewCompatImpl {
        LollipopViewCompatImpl() {
        }

        public void setTransitionName(View view, String str) {
            View view2 = view;
            String transitionName = str;
            View view3 = view2;
            String str2 = transitionName;
            ViewCompatLollipop.setTransitionName(view2, transitionName);
        }

        public String getTransitionName(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.getTransitionName(view2);
        }

        public void requestApplyInsets(View view) {
            View view2 = view;
            View view3 = view2;
            ViewCompatLollipop.requestApplyInsets(view2);
        }

        public void setElevation(View view, float f) {
            View view2 = view;
            float elevation = f;
            View view3 = view2;
            float f2 = elevation;
            ViewCompatLollipop.setElevation(view2, elevation);
        }

        public float getElevation(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.getElevation(view2);
        }

        public void setTranslationZ(View view, float f) {
            View view2 = view;
            float translationZ = f;
            View view3 = view2;
            float f2 = translationZ;
            ViewCompatLollipop.setTranslationZ(view2, translationZ);
        }

        public float getTranslationZ(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.getTranslationZ(view2);
        }

        public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            View view2 = view;
            OnApplyWindowInsetsListener listener = onApplyWindowInsetsListener;
            View view3 = view2;
            OnApplyWindowInsetsListener onApplyWindowInsetsListener2 = listener;
            if (listener != null) {
                final OnApplyWindowInsetsListener onApplyWindowInsetsListener3 = listener;
                ViewCompatLollipop.setOnApplyWindowInsetsListener(view2, new OnApplyWindowInsetsListenerBridge(this) {
                    final /* synthetic */ LollipopViewCompatImpl this$0;

                    {
                        LollipopViewCompatImpl this$02 = r6;
                        OnApplyWindowInsetsListener onApplyWindowInsetsListener = r7;
                        LollipopViewCompatImpl lollipopViewCompatImpl = this$02;
                        this.this$0 = this$02;
                    }

                    public Object onApplyWindowInsets(View view, Object obj) {
                        View v = view;
                        Object insets = obj;
                        View view2 = v;
                        Object obj2 = insets;
                        WindowInsetsCompat onApplyWindowInsets = onApplyWindowInsetsListener3.onApplyWindowInsets(v, WindowInsetsCompat.wrap(insets));
                        WindowInsetsCompat compatInsets = onApplyWindowInsets;
                        return WindowInsetsCompat.unwrap(onApplyWindowInsets);
                    }
                });
                return;
            }
            ViewCompatLollipop.setOnApplyWindowInsetsListener(view2, null);
        }

        public void setNestedScrollingEnabled(View view, boolean z) {
            View view2 = view;
            View view3 = view2;
            ViewCompatLollipop.setNestedScrollingEnabled(view2, z);
        }

        public boolean isNestedScrollingEnabled(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.isNestedScrollingEnabled(view2);
        }

        public boolean startNestedScroll(View view, int i) {
            View view2 = view;
            int axes = i;
            View view3 = view2;
            int i2 = axes;
            return ViewCompatLollipop.startNestedScroll(view2, axes);
        }

        public void stopNestedScroll(View view) {
            View view2 = view;
            View view3 = view2;
            ViewCompatLollipop.stopNestedScroll(view2);
        }

        public boolean hasNestedScrollingParent(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.hasNestedScrollingParent(view2);
        }

        public boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr) {
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
            return ViewCompatLollipop.dispatchNestedScroll(view2, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        }

        public boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2) {
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
            return ViewCompatLollipop.dispatchNestedPreScroll(view2, dx, dy, consumed, offsetInWindow);
        }

        public boolean dispatchNestedFling(View view, float f, float f2, boolean z) {
            View view2 = view;
            float velocityX = f;
            float velocityY = f2;
            View view3 = view2;
            float f3 = velocityX;
            float f4 = velocityY;
            return ViewCompatLollipop.dispatchNestedFling(view2, velocityX, velocityY, z);
        }

        public boolean dispatchNestedPreFling(View view, float f, float f2) {
            View view2 = view;
            float velocityX = f;
            float velocityY = f2;
            View view3 = view2;
            float f3 = velocityX;
            float f4 = velocityY;
            return ViewCompatLollipop.dispatchNestedPreFling(view2, velocityX, velocityY);
        }

        public boolean isImportantForAccessibility(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.isImportantForAccessibility(view2);
        }

        public ColorStateList getBackgroundTintList(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.getBackgroundTintList(view2);
        }

        public void setBackgroundTintList(View view, ColorStateList colorStateList) {
            View view2 = view;
            ColorStateList tintList = colorStateList;
            View view3 = view2;
            ColorStateList colorStateList2 = tintList;
            ViewCompatLollipop.setBackgroundTintList(view2, tintList);
        }

        public void setBackgroundTintMode(View view, Mode mode) {
            View view2 = view;
            Mode mode2 = mode;
            View view3 = view2;
            Mode mode3 = mode2;
            ViewCompatLollipop.setBackgroundTintMode(view2, mode2);
        }

        public Mode getBackgroundTintMode(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.getBackgroundTintMode(view2);
        }

        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            View v = view;
            WindowInsetsCompat insets = windowInsetsCompat;
            View view2 = v;
            WindowInsetsCompat windowInsetsCompat2 = insets;
            return WindowInsetsCompat.wrap(ViewCompatLollipop.onApplyWindowInsets(v, WindowInsetsCompat.unwrap(insets)));
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            View v = view;
            WindowInsetsCompat insets = windowInsetsCompat;
            View view2 = v;
            WindowInsetsCompat windowInsetsCompat2 = insets;
            return WindowInsetsCompat.wrap(ViewCompatLollipop.dispatchApplyWindowInsets(v, WindowInsetsCompat.unwrap(insets)));
        }

        public float getZ(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatLollipop.getZ(view2);
        }

        public void setZ(View view, float f) {
            View view2 = view;
            float z = f;
            View view3 = view2;
            float f2 = z;
            ViewCompatLollipop.setZ(view2, z);
        }

        public void offsetLeftAndRight(View view, int i) {
            View view2 = view;
            int offset = i;
            View view3 = view2;
            int i2 = offset;
            ViewCompatLollipop.offsetLeftAndRight(view2, offset);
        }

        public void offsetTopAndBottom(View view, int i) {
            View view2 = view;
            int offset = i;
            View view3 = view2;
            int i2 = offset;
            ViewCompatLollipop.offsetTopAndBottom(view2, offset);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$MarshmallowViewCompatImpl */
    static class MarshmallowViewCompatImpl extends LollipopViewCompatImpl {
        MarshmallowViewCompatImpl() {
        }

        public void setScrollIndicators(View view, int i) {
            View view2 = view;
            int indicators = i;
            View view3 = view2;
            int i2 = indicators;
            ViewCompatMarshmallow.setScrollIndicators(view2, indicators);
        }

        public void setScrollIndicators(View view, int i, int i2) {
            View view2 = view;
            int indicators = i;
            int mask = i2;
            View view3 = view2;
            int i3 = indicators;
            int i4 = mask;
            ViewCompatMarshmallow.setScrollIndicators(view2, indicators, mask);
        }

        public int getScrollIndicators(View view) {
            View view2 = view;
            View view3 = view2;
            return ViewCompatMarshmallow.getScrollIndicators(view2);
        }

        public void offsetLeftAndRight(View view, int i) {
            View view2 = view;
            int offset = i;
            View view3 = view2;
            int i2 = offset;
            ViewCompatMarshmallow.offsetLeftAndRight(view2, offset);
        }

        public void offsetTopAndBottom(View view, int i) {
            View view2 = view;
            int offset = i;
            View view3 = view2;
            int i2 = offset;
            ViewCompatMarshmallow.offsetTopAndBottom(view2, offset);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$ScrollIndicators */
    public @interface ScrollIndicators {
    }

    /* renamed from: android.support.v4.view.ViewCompat$ViewCompatImpl */
    interface ViewCompatImpl {
        ViewPropertyAnimatorCompat animate(View view);

        boolean canScrollHorizontally(View view, int i);

        boolean canScrollVertically(View view, int i);

        int combineMeasuredStates(int i, int i2);

        WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat);

        void dispatchFinishTemporaryDetach(View view);

        boolean dispatchNestedFling(View view, float f, float f2, boolean z);

        boolean dispatchNestedPreFling(View view, float f, float f2);

        boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2);

        boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr);

        void dispatchStartTemporaryDetach(View view);

        int getAccessibilityLiveRegion(View view);

        AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view);

        float getAlpha(View view);

        ColorStateList getBackgroundTintList(View view);

        Mode getBackgroundTintMode(View view);

        Rect getClipBounds(View view);

        Display getDisplay(View view);

        float getElevation(View view);

        boolean getFitsSystemWindows(View view);

        int getImportantForAccessibility(View view);

        int getLabelFor(View view);

        int getLayerType(View view);

        int getLayoutDirection(View view);

        @Nullable
        Matrix getMatrix(View view);

        int getMeasuredHeightAndState(View view);

        int getMeasuredState(View view);

        int getMeasuredWidthAndState(View view);

        int getMinimumHeight(View view);

        int getMinimumWidth(View view);

        int getPaddingEnd(View view);

        int getPaddingStart(View view);

        ViewParent getParentForAccessibility(View view);

        float getPivotX(View view);

        float getPivotY(View view);

        float getRotation(View view);

        float getRotationX(View view);

        float getRotationY(View view);

        float getScaleX(View view);

        float getScaleY(View view);

        int getScrollIndicators(View view);

        String getTransitionName(View view);

        float getTranslationX(View view);

        float getTranslationY(View view);

        float getTranslationZ(View view);

        int getWindowSystemUiVisibility(View view);

        float getX(View view);

        float getY(View view);

        float getZ(View view);

        boolean hasAccessibilityDelegate(View view);

        boolean hasNestedScrollingParent(View view);

        boolean hasOnClickListeners(View view);

        boolean hasOverlappingRendering(View view);

        boolean hasTransientState(View view);

        boolean isAttachedToWindow(View view);

        boolean isImportantForAccessibility(View view);

        boolean isInLayout(View view);

        boolean isLaidOut(View view);

        boolean isLayoutDirectionResolved(View view);

        boolean isNestedScrollingEnabled(View view);

        boolean isPaddingRelative(View view);

        void jumpDrawablesToCurrentState(View view);

        void offsetLeftAndRight(View view, int i);

        void offsetTopAndBottom(View view, int i);

        WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat);

        void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

        void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        boolean performAccessibilityAction(View view, int i, Bundle bundle);

        void postInvalidateOnAnimation(View view);

        void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4);

        void postOnAnimation(View view, Runnable runnable);

        void postOnAnimationDelayed(View view, Runnable runnable, long j);

        void requestApplyInsets(View view);

        int resolveSizeAndState(int i, int i2, int i3);

        void setAccessibilityDelegate(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat);

        void setAccessibilityLiveRegion(View view, int i);

        void setActivated(View view, boolean z);

        void setAlpha(View view, float f);

        void setBackground(View view, Drawable drawable);

        void setBackgroundTintList(View view, ColorStateList colorStateList);

        void setBackgroundTintMode(View view, Mode mode);

        void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z);

        void setClipBounds(View view, Rect rect);

        void setElevation(View view, float f);

        void setFitsSystemWindows(View view, boolean z);

        void setHasTransientState(View view, boolean z);

        void setImportantForAccessibility(View view, int i);

        void setLabelFor(View view, int i);

        void setLayerPaint(View view, Paint paint);

        void setLayerType(View view, int i, Paint paint);

        void setLayoutDirection(View view, int i);

        void setNestedScrollingEnabled(View view, boolean z);

        void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener);

        void setPaddingRelative(View view, int i, int i2, int i3, int i4);

        void setPivotX(View view, float f);

        void setPivotY(View view, float f);

        void setPointerIcon(View view, PointerIconCompat pointerIconCompat);

        void setRotation(View view, float f);

        void setRotationX(View view, float f);

        void setRotationY(View view, float f);

        void setSaveFromParentEnabled(View view, boolean z);

        void setScaleX(View view, float f);

        void setScaleY(View view, float f);

        void setScrollIndicators(View view, int i);

        void setScrollIndicators(View view, int i, int i2);

        void setTransitionName(View view, String str);

        void setTranslationX(View view, float f);

        void setTranslationY(View view, float f);

        void setTranslationZ(View view, float f);

        void setX(View view, float f);

        void setY(View view, float f);

        void setZ(View view, float f);

        boolean startNestedScroll(View view, int i);

        void stopNestedScroll(View view);
    }

    static {
        int version = VERSION.SDK_INT;
        if (BuildCompat.isAtLeastN()) {
            IMPL = new Api24ViewCompatImpl();
        } else if (version >= 23) {
            IMPL = new MarshmallowViewCompatImpl();
        } else if (version >= 21) {
            IMPL = new LollipopViewCompatImpl();
        } else if (version >= 19) {
            IMPL = new KitKatViewCompatImpl();
        } else if (version >= 18) {
            IMPL = new JbMr2ViewCompatImpl();
        } else if (version >= 17) {
            IMPL = new JbMr1ViewCompatImpl();
        } else if (version >= 16) {
            IMPL = new JBViewCompatImpl();
        } else if (version >= 15) {
            IMPL = new ICSMr1ViewCompatImpl();
        } else if (version >= 14) {
            IMPL = new ICSViewCompatImpl();
        } else if (version < 11) {
            IMPL = new BaseViewCompatImpl();
        } else {
            IMPL = new HCViewCompatImpl();
        }
    }

    public static boolean canScrollHorizontally(View view, int i) {
        View v = view;
        int direction = i;
        View view2 = v;
        int i2 = direction;
        return IMPL.canScrollHorizontally(v, direction);
    }

    public static boolean canScrollVertically(View view, int i) {
        View v = view;
        int direction = i;
        View view2 = v;
        int i2 = direction;
        return IMPL.canScrollVertically(v, direction);
    }

    @Deprecated
    public static int getOverScrollMode(View view) {
        View v = view;
        View view2 = v;
        return v.getOverScrollMode();
    }

    @Deprecated
    public static void setOverScrollMode(View view, int i) {
        View v = view;
        int overScrollMode = i;
        View view2 = v;
        int i2 = overScrollMode;
        v.setOverScrollMode(overScrollMode);
    }

    public static void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View v = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = v;
        AccessibilityEvent accessibilityEvent2 = event;
        IMPL.onPopulateAccessibilityEvent(v, event);
    }

    public static void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View v = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = v;
        AccessibilityEvent accessibilityEvent2 = event;
        IMPL.onInitializeAccessibilityEvent(v, event);
    }

    public static void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        View v = view;
        AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
        View view2 = v;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
        IMPL.onInitializeAccessibilityNodeInfo(v, info);
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        View v = view;
        AccessibilityDelegateCompat delegate = accessibilityDelegateCompat;
        View view2 = v;
        AccessibilityDelegateCompat accessibilityDelegateCompat2 = delegate;
        IMPL.setAccessibilityDelegate(v, delegate);
    }

    public static boolean hasAccessibilityDelegate(View view) {
        View v = view;
        View view2 = v;
        return IMPL.hasAccessibilityDelegate(v);
    }

    public static boolean hasTransientState(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.hasTransientState(view2);
    }

    public static void setHasTransientState(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        IMPL.setHasTransientState(view2, z);
    }

    public static void postInvalidateOnAnimation(View view) {
        View view2 = view;
        View view3 = view2;
        IMPL.postInvalidateOnAnimation(view2);
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
        IMPL.postInvalidateOnAnimation(view2, left, top, right, bottom);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        View view2 = view;
        Runnable action = runnable;
        View view3 = view2;
        Runnable runnable2 = action;
        IMPL.postOnAnimation(view2, action);
    }

    public static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
        View view2 = view;
        Runnable action = runnable;
        long delayMillis = j;
        View view3 = view2;
        Runnable runnable2 = action;
        long j2 = delayMillis;
        IMPL.postOnAnimationDelayed(view2, action, delayMillis);
    }

    public static int getImportantForAccessibility(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getImportantForAccessibility(view2);
    }

    public static void setImportantForAccessibility(View view, int i) {
        View view2 = view;
        int mode = i;
        View view3 = view2;
        int i2 = mode;
        IMPL.setImportantForAccessibility(view2, mode);
    }

    public static boolean isImportantForAccessibility(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.isImportantForAccessibility(view2);
    }

    public static boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        View view2 = view;
        int action = i;
        Bundle arguments = bundle;
        View view3 = view2;
        int i2 = action;
        Bundle bundle2 = arguments;
        return IMPL.performAccessibilityAction(view2, action, arguments);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getAccessibilityNodeProvider(view2);
    }

    public static float getAlpha(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getAlpha(view2);
    }

    public static void setLayerType(View view, int i, Paint paint) {
        View view2 = view;
        int layerType = i;
        Paint paint2 = paint;
        View view3 = view2;
        int i2 = layerType;
        Paint paint3 = paint2;
        IMPL.setLayerType(view2, layerType, paint2);
    }

    public static int getLayerType(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getLayerType(view2);
    }

    public static int getLabelFor(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getLabelFor(view2);
    }

    public static void setLabelFor(View view, @IdRes int i) {
        View view2 = view;
        int labeledId = i;
        View view3 = view2;
        int i2 = labeledId;
        IMPL.setLabelFor(view2, labeledId);
    }

    public static void setLayerPaint(View view, Paint paint) {
        View view2 = view;
        Paint paint2 = paint;
        View view3 = view2;
        Paint paint3 = paint2;
        IMPL.setLayerPaint(view2, paint2);
    }

    public static int getLayoutDirection(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getLayoutDirection(view2);
    }

    public static void setLayoutDirection(View view, int i) {
        View view2 = view;
        int layoutDirection = i;
        View view3 = view2;
        int i2 = layoutDirection;
        IMPL.setLayoutDirection(view2, layoutDirection);
    }

    public static ViewParent getParentForAccessibility(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getParentForAccessibility(view2);
    }

    @Deprecated
    public static boolean isOpaque(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.isOpaque();
    }

    public static int resolveSizeAndState(int i, int i2, int i3) {
        int size = i;
        int measureSpec = i2;
        int childMeasuredState = i3;
        int i4 = size;
        int i5 = measureSpec;
        int i6 = childMeasuredState;
        return IMPL.resolveSizeAndState(size, measureSpec, childMeasuredState);
    }

    public static int getMeasuredWidthAndState(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getMeasuredWidthAndState(view2);
    }

    public static int getMeasuredHeightAndState(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getMeasuredHeightAndState(view2);
    }

    public static int getMeasuredState(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getMeasuredState(view2);
    }

    public static int combineMeasuredStates(int i, int i2) {
        int curState = i;
        int newState = i2;
        int i3 = curState;
        int i4 = newState;
        return IMPL.combineMeasuredStates(curState, newState);
    }

    public static int getAccessibilityLiveRegion(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getAccessibilityLiveRegion(view2);
    }

    public static void setAccessibilityLiveRegion(View view, int i) {
        View view2 = view;
        int mode = i;
        View view3 = view2;
        int i2 = mode;
        IMPL.setAccessibilityLiveRegion(view2, mode);
    }

    public static int getPaddingStart(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getPaddingStart(view2);
    }

    public static int getPaddingEnd(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getPaddingEnd(view2);
    }

    public static void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
        View view2 = view;
        int start = i;
        int top = i2;
        int end = i3;
        int bottom = i4;
        View view3 = view2;
        int i5 = start;
        int i6 = top;
        int i7 = end;
        int i8 = bottom;
        IMPL.setPaddingRelative(view2, start, top, end, bottom);
    }

    public static void dispatchStartTemporaryDetach(View view) {
        View view2 = view;
        View view3 = view2;
        IMPL.dispatchStartTemporaryDetach(view2);
    }

    public static void dispatchFinishTemporaryDetach(View view) {
        View view2 = view;
        View view3 = view2;
        IMPL.dispatchFinishTemporaryDetach(view2);
    }

    public static float getTranslationX(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getTranslationX(view2);
    }

    public static float getTranslationY(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getTranslationY(view2);
    }

    @Nullable
    public static Matrix getMatrix(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getMatrix(view2);
    }

    public static int getMinimumWidth(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getMinimumWidth(view2);
    }

    public static int getMinimumHeight(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getMinimumHeight(view2);
    }

    public static ViewPropertyAnimatorCompat animate(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.animate(view2);
    }

    public static void setTranslationX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setTranslationX(view2, value);
    }

    public static void setTranslationY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setTranslationY(view2, value);
    }

    public static void setAlpha(View view, @FloatRange(from = 0.0d, mo5to = 1.0d) float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setAlpha(view2, value);
    }

    public static void setX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setX(view2, value);
    }

    public static void setY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setY(view2, value);
    }

    public static void setRotation(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setRotation(view2, value);
    }

    public static void setRotationX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setRotationX(view2, value);
    }

    public static void setRotationY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setRotationY(view2, value);
    }

    public static void setScaleX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setScaleX(view2, value);
    }

    public static void setScaleY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setScaleY(view2, value);
    }

    public static float getPivotX(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getPivotX(view2);
    }

    public static void setPivotX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setPivotX(view2, value);
    }

    public static float getPivotY(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getPivotY(view2);
    }

    public static void setPivotY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        IMPL.setPivotY(view2, value);
    }

    public static float getRotation(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getRotation(view2);
    }

    public static float getRotationX(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getRotationX(view2);
    }

    public static float getRotationY(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getRotationY(view2);
    }

    public static float getScaleX(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getScaleX(view2);
    }

    public static float getScaleY(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getScaleY(view2);
    }

    public static float getX(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getX(view2);
    }

    public static float getY(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getY(view2);
    }

    public static void setElevation(View view, float f) {
        View view2 = view;
        float elevation = f;
        View view3 = view2;
        float f2 = elevation;
        IMPL.setElevation(view2, elevation);
    }

    public static float getElevation(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getElevation(view2);
    }

    public static void setTranslationZ(View view, float f) {
        View view2 = view;
        float translationZ = f;
        View view3 = view2;
        float f2 = translationZ;
        IMPL.setTranslationZ(view2, translationZ);
    }

    public static float getTranslationZ(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getTranslationZ(view2);
    }

    public static void setTransitionName(View view, String str) {
        View view2 = view;
        String transitionName = str;
        View view3 = view2;
        String str2 = transitionName;
        IMPL.setTransitionName(view2, transitionName);
    }

    public static String getTransitionName(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getTransitionName(view2);
    }

    public static int getWindowSystemUiVisibility(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getWindowSystemUiVisibility(view2);
    }

    public static void requestApplyInsets(View view) {
        View view2 = view;
        View view3 = view2;
        IMPL.requestApplyInsets(view2);
    }

    public static void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
        ViewGroup viewGroup2 = viewGroup;
        ViewGroup viewGroup3 = viewGroup2;
        IMPL.setChildrenDrawingOrderEnabled(viewGroup2, z);
    }

    public static boolean getFitsSystemWindows(View view) {
        View v = view;
        View view2 = v;
        return IMPL.getFitsSystemWindows(v);
    }

    public static void setFitsSystemWindows(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        IMPL.setFitsSystemWindows(view2, z);
    }

    public static void jumpDrawablesToCurrentState(View view) {
        View v = view;
        View view2 = v;
        IMPL.jumpDrawablesToCurrentState(v);
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        View v = view;
        OnApplyWindowInsetsListener listener = onApplyWindowInsetsListener;
        View view2 = v;
        OnApplyWindowInsetsListener onApplyWindowInsetsListener2 = listener;
        IMPL.setOnApplyWindowInsetsListener(v, listener);
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        View view2 = view;
        WindowInsetsCompat insets = windowInsetsCompat;
        View view3 = view2;
        WindowInsetsCompat windowInsetsCompat2 = insets;
        return IMPL.onApplyWindowInsets(view2, insets);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        View view2 = view;
        WindowInsetsCompat insets = windowInsetsCompat;
        View view3 = view2;
        WindowInsetsCompat windowInsetsCompat2 = insets;
        return IMPL.dispatchApplyWindowInsets(view2, insets);
    }

    public static void setSaveFromParentEnabled(View view, boolean z) {
        View v = view;
        View view2 = v;
        IMPL.setSaveFromParentEnabled(v, z);
    }

    public static void setActivated(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        IMPL.setActivated(view2, z);
    }

    public static boolean hasOverlappingRendering(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.hasOverlappingRendering(view2);
    }

    public static boolean isPaddingRelative(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.isPaddingRelative(view2);
    }

    public static void setBackground(View view, Drawable drawable) {
        View view2 = view;
        Drawable background = drawable;
        View view3 = view2;
        Drawable drawable2 = background;
        IMPL.setBackground(view2, background);
    }

    public static ColorStateList getBackgroundTintList(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getBackgroundTintList(view2);
    }

    public static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        View view2 = view;
        ColorStateList tintList = colorStateList;
        View view3 = view2;
        ColorStateList colorStateList2 = tintList;
        IMPL.setBackgroundTintList(view2, tintList);
    }

    public static Mode getBackgroundTintMode(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getBackgroundTintMode(view2);
    }

    public static void setBackgroundTintMode(View view, Mode mode) {
        View view2 = view;
        Mode mode2 = mode;
        View view3 = view2;
        Mode mode3 = mode2;
        IMPL.setBackgroundTintMode(view2, mode2);
    }

    public static void setNestedScrollingEnabled(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        IMPL.setNestedScrollingEnabled(view2, z);
    }

    public static boolean isNestedScrollingEnabled(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.isNestedScrollingEnabled(view2);
    }

    public static boolean startNestedScroll(View view, int i) {
        View view2 = view;
        int axes = i;
        View view3 = view2;
        int i2 = axes;
        return IMPL.startNestedScroll(view2, axes);
    }

    public static void stopNestedScroll(View view) {
        View view2 = view;
        View view3 = view2;
        IMPL.stopNestedScroll(view2);
    }

    public static boolean hasNestedScrollingParent(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.hasNestedScrollingParent(view2);
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
        return IMPL.dispatchNestedScroll(view2, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
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
        return IMPL.dispatchNestedPreScroll(view2, dx, dy, consumed, offsetInWindow);
    }

    public static boolean dispatchNestedFling(View view, float f, float f2, boolean z) {
        View view2 = view;
        float velocityX = f;
        float velocityY = f2;
        View view3 = view2;
        float f3 = velocityX;
        float f4 = velocityY;
        return IMPL.dispatchNestedFling(view2, velocityX, velocityY, z);
    }

    public static boolean dispatchNestedPreFling(View view, float f, float f2) {
        View view2 = view;
        float velocityX = f;
        float velocityY = f2;
        View view3 = view2;
        float f3 = velocityX;
        float f4 = velocityY;
        return IMPL.dispatchNestedPreFling(view2, velocityX, velocityY);
    }

    public static boolean isInLayout(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.isInLayout(view2);
    }

    public static boolean isLaidOut(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.isLaidOut(view2);
    }

    public static boolean isLayoutDirectionResolved(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.isLayoutDirectionResolved(view2);
    }

    public static float getZ(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getZ(view2);
    }

    public static void setZ(View view, float f) {
        View view2 = view;
        float z = f;
        View view3 = view2;
        float f2 = z;
        IMPL.setZ(view2, z);
    }

    public static void offsetTopAndBottom(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        IMPL.offsetTopAndBottom(view2, offset);
    }

    public static void offsetLeftAndRight(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        IMPL.offsetLeftAndRight(view2, offset);
    }

    public static void setClipBounds(View view, Rect rect) {
        View view2 = view;
        Rect clipBounds = rect;
        View view3 = view2;
        Rect rect2 = clipBounds;
        IMPL.setClipBounds(view2, clipBounds);
    }

    public static Rect getClipBounds(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getClipBounds(view2);
    }

    public static boolean isAttachedToWindow(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.isAttachedToWindow(view2);
    }

    public static boolean hasOnClickListeners(View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.hasOnClickListeners(view2);
    }

    public static void setScrollIndicators(@NonNull View view, int i) {
        View view2 = view;
        int indicators = i;
        View view3 = view2;
        int i2 = indicators;
        IMPL.setScrollIndicators(view2, indicators);
    }

    public static void setScrollIndicators(@NonNull View view, int i, int i2) {
        View view2 = view;
        int indicators = i;
        int mask = i2;
        View view3 = view2;
        int i3 = indicators;
        int i4 = mask;
        IMPL.setScrollIndicators(view2, indicators, mask);
    }

    public static int getScrollIndicators(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getScrollIndicators(view2);
    }

    public static void setPointerIcon(@NonNull View view, PointerIconCompat pointerIconCompat) {
        View view2 = view;
        PointerIconCompat pointerIcon = pointerIconCompat;
        View view3 = view2;
        PointerIconCompat pointerIconCompat2 = pointerIcon;
        IMPL.setPointerIcon(view2, pointerIcon);
    }

    public static Display getDisplay(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        return IMPL.getDisplay(view2);
    }

    protected ViewCompat() {
    }
}
