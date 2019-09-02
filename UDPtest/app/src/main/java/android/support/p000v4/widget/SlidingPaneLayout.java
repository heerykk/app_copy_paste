package android.support.p000v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* renamed from: android.support.v4.widget.SlidingPaneLayout */
public class SlidingPaneLayout extends ViewGroup {
    private static final int DEFAULT_FADE_COLOR = -858993460;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    static final SlidingPanelLayoutImpl IMPL;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final String TAG = "SlidingPaneLayout";
    private boolean mCanSlide;
    private int mCoveredFadeColor;
    final ViewDragHelper mDragHelper;
    private boolean mFirstLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    boolean mIsUnableToDrag;
    private final int mOverhangSize;
    private PanelSlideListener mPanelSlideListener;
    private int mParallaxBy;
    private float mParallaxOffset;
    final ArrayList<DisableLayerRunnable> mPostedRunnables;
    boolean mPreservedOpenState;
    private Drawable mShadowDrawableLeft;
    private Drawable mShadowDrawableRight;
    float mSlideOffset;
    int mSlideRange;
    View mSlideableView;
    private int mSliderFadeColor;
    private final Rect mTmpRect;

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$AccessibilityDelegate */
    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();
        final /* synthetic */ SlidingPaneLayout this$0;

        AccessibilityDelegate(SlidingPaneLayout slidingPaneLayout) {
            SlidingPaneLayout this$02 = slidingPaneLayout;
            SlidingPaneLayout slidingPaneLayout2 = this$02;
            this.this$0 = this$02;
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            AccessibilityNodeInfoCompat superNode = AccessibilityNodeInfoCompat.obtain(info);
            super.onInitializeAccessibilityNodeInfo(host, superNode);
            copyNodeInfoNoChildren(info, superNode);
            superNode.recycle();
            info.setClassName(SlidingPaneLayout.class.getName());
            info.setSource(host);
            ViewParent parentForAccessibility = ViewCompat.getParentForAccessibility(host);
            ViewParent parent = parentForAccessibility;
            if (parentForAccessibility instanceof View) {
                info.setParent((View) parent);
            }
            int childCount = this.this$0.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = this.this$0.getChildAt(i);
                if (!filter(child) && child.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility(child, 1);
                    info.addChild(child);
                }
            }
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(SlidingPaneLayout.class.getName());
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            ViewGroup host = viewGroup;
            View child = view;
            AccessibilityEvent event = accessibilityEvent;
            ViewGroup viewGroup2 = host;
            View view2 = child;
            AccessibilityEvent accessibilityEvent2 = event;
            if (filter(child)) {
                return false;
            }
            return super.onRequestSendAccessibilityEvent(host, child, event);
        }

        public boolean filter(View view) {
            View child = view;
            View view2 = child;
            return this.this$0.isDimmed(child);
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            AccessibilityNodeInfoCompat dest = accessibilityNodeInfoCompat;
            AccessibilityNodeInfoCompat src = accessibilityNodeInfoCompat2;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat3 = dest;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat4 = src;
            Rect rect = this.mTmpRect;
            src.getBoundsInParent(rect);
            dest.setBoundsInParent(rect);
            src.getBoundsInScreen(rect);
            dest.setBoundsInScreen(rect);
            dest.setVisibleToUser(src.isVisibleToUser());
            dest.setPackageName(src.getPackageName());
            dest.setClassName(src.getClassName());
            dest.setContentDescription(src.getContentDescription());
            dest.setEnabled(src.isEnabled());
            dest.setClickable(src.isClickable());
            dest.setFocusable(src.isFocusable());
            dest.setFocused(src.isFocused());
            dest.setAccessibilityFocused(src.isAccessibilityFocused());
            dest.setSelected(src.isSelected());
            dest.setLongClickable(src.isLongClickable());
            dest.addAction(src.getActions());
            dest.setMovementGranularities(src.getMovementGranularities());
        }
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$DisableLayerRunnable */
    private class DisableLayerRunnable implements Runnable {
        final View mChildView;
        final /* synthetic */ SlidingPaneLayout this$0;

        DisableLayerRunnable(SlidingPaneLayout slidingPaneLayout, View view) {
            View childView = view;
            View view2 = childView;
            this.this$0 = slidingPaneLayout;
            this.mChildView = childView;
        }

        public void run() {
            if (this.mChildView.getParent() == this.this$0) {
                ViewCompat.setLayerType(this.mChildView, 0, null);
                this.this$0.invalidateChildRegion(this.mChildView);
            }
            boolean remove = this.this$0.mPostedRunnables.remove(this);
        }
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$DragHelperCallback */
    private class DragHelperCallback extends Callback {
        final /* synthetic */ SlidingPaneLayout this$0;

        DragHelperCallback(SlidingPaneLayout slidingPaneLayout) {
            this.this$0 = slidingPaneLayout;
        }

        public boolean tryCaptureView(View view, int i) {
            View child = view;
            View view2 = child;
            int i2 = i;
            if (!this.this$0.mIsUnableToDrag) {
                return ((LayoutParams) child.getLayoutParams()).slideable;
            }
            return false;
        }

        public void onViewDragStateChanged(int i) {
            int i2 = i;
            if (this.this$0.mDragHelper.getViewDragState() == 0) {
                if (this.this$0.mSlideOffset == 0.0f) {
                    this.this$0.updateObscuredViewsVisibility(this.this$0.mSlideableView);
                    this.this$0.dispatchOnPanelClosed(this.this$0.mSlideableView);
                    this.this$0.mPreservedOpenState = false;
                    return;
                }
                this.this$0.dispatchOnPanelOpened(this.this$0.mSlideableView);
                this.this$0.mPreservedOpenState = true;
            }
        }

        public void onViewCaptured(View view, int i) {
            View view2 = view;
            int i2 = i;
            this.this$0.setAllChildrenVisible();
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            int left = i;
            View view2 = view;
            int i5 = left;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            this.this$0.onPanelDragged(left);
            this.this$0.invalidate();
        }

        public void onViewReleased(View view, float f, float f2) {
            int left;
            View releasedChild = view;
            float xvel = f;
            View view2 = releasedChild;
            float f3 = xvel;
            float f4 = f2;
            LayoutParams lp = (LayoutParams) releasedChild.getLayoutParams();
            if (!this.this$0.isLayoutRtlSupport()) {
                int paddingLeft = this.this$0.getPaddingLeft() + lp.leftMargin;
                left = paddingLeft;
                if ((xvel > 0.0f) || (xvel == 0.0f && this.this$0.mSlideOffset > 0.5f)) {
                    left = paddingLeft + this.this$0.mSlideRange;
                }
            } else {
                int paddingRight = this.this$0.getPaddingRight() + lp.rightMargin;
                int startToRight = paddingRight;
                if ((xvel < 0.0f) || (xvel == 0.0f && this.this$0.mSlideOffset > 0.5f)) {
                    startToRight = paddingRight + this.this$0.mSlideRange;
                }
                left = (this.this$0.getWidth() - startToRight) - this.this$0.mSlideableView.getWidth();
            }
            boolean z = this.this$0.mDragHelper.settleCapturedViewAt(left, releasedChild.getTop());
            this.this$0.invalidate();
        }

        public int getViewHorizontalDragRange(View view) {
            View view2 = view;
            return this.this$0.mSlideRange;
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            int newLeft;
            int left = i;
            View view2 = view;
            int i3 = left;
            int i4 = i2;
            LayoutParams lp = (LayoutParams) this.this$0.mSlideableView.getLayoutParams();
            if (!this.this$0.isLayoutRtlSupport()) {
                int paddingLeft = this.this$0.getPaddingLeft() + lp.leftMargin;
                int endBound = paddingLeft + this.this$0.mSlideRange;
                newLeft = Math.min(Math.max(left, paddingLeft), endBound);
            } else {
                int width = this.this$0.getWidth() - ((this.this$0.getPaddingRight() + lp.rightMargin) + this.this$0.mSlideableView.getWidth());
                int endBound2 = width - this.this$0.mSlideRange;
                newLeft = Math.max(Math.min(left, width), endBound2);
            }
            return newLeft;
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            View child = view;
            View view2 = child;
            int i3 = i;
            int i4 = i2;
            return child.getTop();
        }

        public void onEdgeDragStarted(int i, int i2) {
            int pointerId = i2;
            int i3 = i;
            int i4 = pointerId;
            this.this$0.mDragHelper.captureChildView(this.this$0.mSlideableView, pointerId);
        }
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$LayoutParams */
    public static class LayoutParams extends MarginLayoutParams {
        private static final int[] ATTRS;
        Paint dimPaint;
        boolean dimWhenOffset;
        boolean slideable;
        public float weight = 0.0f;

        static {
            int[] iArr = new int[1];
            iArr[0] = 16843137;
            ATTRS = iArr;
        }

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, ATTRS);
            this.weight = a.getFloat(0, 0.0f);
            a.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            LayoutParams source = layoutParams;
            LayoutParams layoutParams2 = source;
            super(source);
            this.weight = source.weight;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams source = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = source;
            super(source);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            super(source);
        }
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$PanelSlideListener */
    public interface PanelSlideListener {
        void onPanelClosed(View view);

        void onPanelOpened(View view);

        void onPanelSlide(View view, float f);
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$SavedState */
    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                Parcel in = parcel;
                ClassLoader loader = classLoader;
                Parcel parcel2 = in;
                ClassLoader classLoader2 = loader;
                return new SavedState(in, loader);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        });
        boolean isOpen;

        SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel in = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = in;
            ClassLoader classLoader2 = loader;
            super(in, loader);
            this.isOpen = in.readInt() != 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel out = parcel;
            int flags = i;
            Parcel parcel2 = out;
            int i2 = flags;
            super.writeToParcel(out, flags);
            out.writeInt(!this.isOpen ? 0 : 1);
        }
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$SimplePanelSlideListener */
    public static class SimplePanelSlideListener implements PanelSlideListener {
        public SimplePanelSlideListener() {
        }

        public void onPanelSlide(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void onPanelOpened(View view) {
            View view2 = view;
        }

        public void onPanelClosed(View view) {
            View view2 = view;
        }
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$SlidingPanelLayoutImpl */
    interface SlidingPanelLayoutImpl {
        void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view);
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$SlidingPanelLayoutImplBase */
    static class SlidingPanelLayoutImplBase implements SlidingPanelLayoutImpl {
        SlidingPanelLayoutImplBase() {
        }

        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            SlidingPaneLayout parent = slidingPaneLayout;
            View child = view;
            SlidingPaneLayout slidingPaneLayout2 = parent;
            View view2 = child;
            ViewCompat.postInvalidateOnAnimation(parent, child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$SlidingPanelLayoutImplJB */
    static class SlidingPanelLayoutImplJB extends SlidingPanelLayoutImplBase {
        private Method mGetDisplayList;
        private Field mRecreateDisplayList;

        SlidingPanelLayoutImplJB() {
            try {
                this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                int e2 = Log.e(SlidingPaneLayout.TAG, "Couldn't fetch getDisplayList method; dimming won't work right.", e);
            }
            try {
                this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
                this.mRecreateDisplayList.setAccessible(true);
            } catch (NoSuchFieldException e3) {
                NoSuchFieldException noSuchFieldException = e3;
                int e4 = Log.e(SlidingPaneLayout.TAG, "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e3);
            }
        }

        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            SlidingPaneLayout parent = slidingPaneLayout;
            View child = view;
            SlidingPaneLayout slidingPaneLayout2 = parent;
            View view2 = child;
            if (this.mGetDisplayList == null || this.mRecreateDisplayList == null) {
                child.invalidate();
                return;
            }
            try {
                this.mRecreateDisplayList.setBoolean(child, true);
                Object invoke = this.mGetDisplayList.invoke(child, null);
            } catch (Exception e) {
                Exception exc = e;
                int e2 = Log.e(SlidingPaneLayout.TAG, "Error refreshing display list state", e);
            }
            super.invalidateChildRegion(parent, child);
        }
    }

    /* renamed from: android.support.v4.widget.SlidingPaneLayout$SlidingPanelLayoutImplJBMR1 */
    static class SlidingPanelLayoutImplJBMR1 extends SlidingPanelLayoutImplBase {
        SlidingPanelLayoutImplJBMR1() {
        }

        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            View child = view;
            SlidingPaneLayout slidingPaneLayout2 = slidingPaneLayout;
            View view2 = child;
            ViewCompat.setLayerPaint(child, ((LayoutParams) child.getLayoutParams()).dimPaint);
        }
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        this.mSliderFadeColor = DEFAULT_FADE_COLOR;
        this.mFirstLayout = true;
        this.mTmpRect = new Rect();
        this.mPostedRunnables = new ArrayList<>();
        float f = context2.getResources().getDisplayMetrics().density;
        float f2 = f;
        this.mOverhangSize = (int) ((32.0f * f) + 0.5f);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context2);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate(this));
        ViewCompat.setImportantForAccessibility(this, 1);
        this.mDragHelper = ViewDragHelper.create(this, 0.5f, new DragHelperCallback(this));
        this.mDragHelper.setMinVelocity(400.0f * f);
    }

    static {
        int i = VERSION.SDK_INT;
        int deviceVersion = i;
        if (i >= 17) {
            IMPL = new SlidingPanelLayoutImplJBMR1();
        } else if (deviceVersion < 16) {
            IMPL = new SlidingPanelLayoutImplBase();
        } else {
            IMPL = new SlidingPanelLayoutImplJB();
        }
    }

    public SlidingPaneLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setParallaxDistance(int i) {
        int parallaxBy = i;
        int i2 = parallaxBy;
        this.mParallaxBy = parallaxBy;
        requestLayout();
    }

    public int getParallaxDistance() {
        return this.mParallaxBy;
    }

    public void setSliderFadeColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mSliderFadeColor = color;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.mSliderFadeColor;
    }

    public void setCoveredFadeColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mCoveredFadeColor = color;
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.mCoveredFadeColor;
    }

    public void setPanelSlideListener(PanelSlideListener panelSlideListener) {
        PanelSlideListener listener = panelSlideListener;
        PanelSlideListener panelSlideListener2 = listener;
        this.mPanelSlideListener = listener;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelSlide(View view) {
        View panel = view;
        View view2 = panel;
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelSlide(panel, this.mSlideOffset);
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelOpened(View view) {
        View panel = view;
        View view2 = panel;
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelOpened(panel);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelClosed(View view) {
        View panel = view;
        View view2 = panel;
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelClosed(panel);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void updateObscuredViewsVisibility(View view) {
        int left;
        int right;
        int top;
        int bottom;
        int vis;
        View panel = view;
        View view2 = panel;
        boolean isLayoutRtlSupport = isLayoutRtlSupport();
        boolean isLayoutRtl = isLayoutRtlSupport;
        int startBound = !isLayoutRtlSupport ? getPaddingLeft() : getWidth() - getPaddingRight();
        int endBound = !isLayoutRtl ? getWidth() - getPaddingRight() : getPaddingLeft();
        int topBound = getPaddingTop();
        int bottomBound = getHeight() - getPaddingBottom();
        if (panel != null && viewIsOpaque(panel)) {
            left = panel.getLeft();
            right = panel.getRight();
            top = panel.getTop();
            bottom = panel.getBottom();
        } else {
            bottom = 0;
            top = 0;
            right = 0;
            left = 0;
        }
        int i = 0;
        int childCount = getChildCount();
        while (i < childCount) {
            View childAt = getChildAt(i);
            View child = childAt;
            if (childAt != panel) {
                if (child.getVisibility() != 8) {
                    int clampedChildLeft = Math.max(!isLayoutRtl ? startBound : endBound, child.getLeft());
                    int clampedChildTop = Math.max(topBound, child.getTop());
                    int clampedChildRight = Math.min(!isLayoutRtl ? endBound : startBound, child.getRight());
                    int clampedChildBottom = Math.min(bottomBound, child.getBottom());
                    if (clampedChildLeft >= left && clampedChildTop >= top && clampedChildRight <= right && clampedChildBottom <= bottom) {
                        vis = 4;
                    } else {
                        vis = 0;
                    }
                    child.setVisibility(vis);
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setAllChildrenVisible() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            View child = childAt;
            if (childAt.getVisibility() == 4) {
                child.setVisibility(0);
            }
        }
    }

    private static boolean viewIsOpaque(View view) {
        View v = view;
        View view2 = v;
        if (v.isOpaque()) {
            return true;
        }
        if (VERSION.SDK_INT >= 18) {
            return false;
        }
        Drawable background = v.getBackground();
        Drawable bg = background;
        if (background == null) {
            return false;
        }
        return bg.getOpacity() == -1;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int count = this.mPostedRunnables.size();
        for (int i = 0; i < count; i++) {
            DisableLayerRunnable disableLayerRunnable = (DisableLayerRunnable) this.mPostedRunnables.get(i);
            DisableLayerRunnable disableLayerRunnable2 = disableLayerRunnable;
            disableLayerRunnable.run();
        }
        this.mPostedRunnables.clear();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int childHeightSpec;
        int childHeightSpec2;
        int childWidthSpec;
        int childHeightSpec3;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == 1073741824) {
            if (heightMode == 0) {
                if (!isInEditMode()) {
                    IllegalStateException illegalStateException = new IllegalStateException("Height must not be UNSPECIFIED");
                    throw illegalStateException;
                } else if (heightMode == 0) {
                    heightMode = Integer.MIN_VALUE;
                    heightSize = 300;
                }
            }
        } else if (!isInEditMode()) {
            IllegalStateException illegalStateException2 = new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            throw illegalStateException2;
        } else if (widthMode != Integer.MIN_VALUE) {
            if (widthMode == 0) {
                widthSize = 300;
            }
        }
        int layoutHeight = 0;
        int maxLayoutHeight = -1;
        switch (heightMode) {
            case Integer.MIN_VALUE:
                maxLayoutHeight = (heightSize - getPaddingTop()) - getPaddingBottom();
                break;
            case 1073741824:
                int paddingTop = (heightSize - getPaddingTop()) - getPaddingBottom();
                maxLayoutHeight = paddingTop;
                layoutHeight = paddingTop;
                break;
        }
        float weightSum = 0.0f;
        boolean canSlide = false;
        int paddingLeft = (widthSize - getPaddingLeft()) - getPaddingRight();
        int widthAvailable = paddingLeft;
        int widthRemaining = paddingLeft;
        int childCount = getChildCount();
        int childCount2 = childCount;
        if (childCount > 2) {
            int e = Log.e(TAG, "onMeasure: More than two child views are not supported.");
        }
        this.mSlideableView = null;
        for (int i5 = 0; i5 < childCount2; i5++) {
            View childAt = getChildAt(i5);
            View child = childAt;
            LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
            if (child.getVisibility() != 8) {
                if (lp.weight > 0.0f) {
                    weightSum += lp.weight;
                    if (lp.width == 0) {
                    }
                }
                int horizontalMargin = lp.leftMargin + lp.rightMargin;
                if (lp.width == -2) {
                    childWidthSpec = MeasureSpec.makeMeasureSpec(widthAvailable - horizontalMargin, Integer.MIN_VALUE);
                } else if (lp.width != -1) {
                    childWidthSpec = MeasureSpec.makeMeasureSpec(lp.width, 1073741824);
                } else {
                    childWidthSpec = MeasureSpec.makeMeasureSpec(widthAvailable - horizontalMargin, 1073741824);
                }
                if (lp.height == -2) {
                    childHeightSpec3 = MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                } else if (lp.height != -1) {
                    childHeightSpec3 = MeasureSpec.makeMeasureSpec(lp.height, 1073741824);
                } else {
                    childHeightSpec3 = MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                }
                child.measure(childWidthSpec, childHeightSpec3);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                if (heightMode == Integer.MIN_VALUE && childHeight > layoutHeight) {
                    layoutHeight = Math.min(childHeight, maxLayoutHeight);
                }
                widthRemaining -= childWidth;
                boolean z = canSlide;
                boolean z2 = widthRemaining < 0;
                boolean z3 = z2;
                lp.slideable = z2;
                canSlide = z | z3;
                if (lp.slideable) {
                    this.mSlideableView = child;
                }
            } else {
                lp.dimWhenOffset = false;
            }
        }
        if (canSlide || weightSum > 0.0f) {
            int fixedPanelWidthLimit = widthAvailable - this.mOverhangSize;
            for (int i6 = 0; i6 < childCount2; i6++) {
                View childAt2 = getChildAt(i6);
                View child2 = childAt2;
                if (childAt2.getVisibility() != 8) {
                    LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                    if (child2.getVisibility() != 8) {
                        boolean skippedFirstPass = lp2.width == 0 && lp2.weight > 0.0f;
                        int measuredWidth = !skippedFirstPass ? child2.getMeasuredWidth() : 0;
                        if (canSlide && child2 != this.mSlideableView) {
                            if (lp2.width < 0 && (measuredWidth > fixedPanelWidthLimit || lp2.weight > 0.0f)) {
                                if (!skippedFirstPass) {
                                    childHeightSpec = MeasureSpec.makeMeasureSpec(child2.getMeasuredHeight(), 1073741824);
                                } else if (lp2.height == -2) {
                                    childHeightSpec = MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                                } else if (lp2.height != -1) {
                                    childHeightSpec = MeasureSpec.makeMeasureSpec(lp2.height, 1073741824);
                                } else {
                                    childHeightSpec = MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                                }
                                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(fixedPanelWidthLimit, 1073741824);
                                int i7 = makeMeasureSpec;
                                child2.measure(makeMeasureSpec, childHeightSpec);
                            }
                        } else if (lp2.weight > 0.0f) {
                            if (lp2.width != 0) {
                                childHeightSpec2 = MeasureSpec.makeMeasureSpec(child2.getMeasuredHeight(), 1073741824);
                            } else if (lp2.height == -2) {
                                childHeightSpec2 = MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                            } else if (lp2.height != -1) {
                                childHeightSpec2 = MeasureSpec.makeMeasureSpec(lp2.height, 1073741824);
                            } else {
                                childHeightSpec2 = MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                            }
                            if (!canSlide) {
                                int max = Math.max(0, widthRemaining);
                                int i8 = max;
                                int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(measuredWidth + ((int) ((lp2.weight * ((float) max)) / weightSum)), 1073741824);
                                int i9 = makeMeasureSpec2;
                                child2.measure(makeMeasureSpec2, childHeightSpec2);
                            } else {
                                int i10 = widthAvailable - (lp2.leftMargin + lp2.rightMargin);
                                int newWidth = i10;
                                int childWidthSpec2 = MeasureSpec.makeMeasureSpec(i10, 1073741824);
                                if (measuredWidth != newWidth) {
                                    child2.measure(childWidthSpec2, childHeightSpec2);
                                }
                            }
                        }
                    }
                }
            }
        }
        int paddingTop2 = layoutHeight + getPaddingTop() + getPaddingBottom();
        int i11 = paddingTop2;
        setMeasuredDimension(widthSize, paddingTop2);
        this.mCanSlide = canSlide;
        if (this.mDragHelper.getViewDragState() != 0 && !canSlide) {
            this.mDragHelper.abort();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childRight;
        int childLeft;
        int l = i;
        int r = i3;
        boolean z2 = z;
        int i5 = l;
        int i6 = i2;
        int i7 = r;
        int i8 = i4;
        boolean isLayoutRtlSupport = isLayoutRtlSupport();
        boolean isLayoutRtl = isLayoutRtlSupport;
        if (!isLayoutRtlSupport) {
            this.mDragHelper.setEdgeTrackingEnabled(1);
        } else {
            this.mDragHelper.setEdgeTrackingEnabled(2);
        }
        int width = r - l;
        int paddingStart = !isLayoutRtl ? getPaddingLeft() : getPaddingRight();
        int paddingEnd = !isLayoutRtl ? getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        int xStart = paddingStart;
        int nextXStart = paddingStart;
        if (this.mFirstLayout) {
            this.mSlideOffset = (this.mCanSlide && this.mPreservedOpenState) ? 1.0f : 0.0f;
        }
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth();
                int offset = 0;
                if (lp.slideable) {
                    int min = (Math.min(nextXStart, (width - paddingEnd) - this.mOverhangSize) - xStart) - (lp.leftMargin + lp.rightMargin);
                    int range = min;
                    this.mSlideRange = min;
                    int lpMargin = !isLayoutRtl ? lp.leftMargin : lp.rightMargin;
                    lp.dimWhenOffset = ((xStart + lpMargin) + range) + (childWidth / 2) > width - paddingEnd;
                    int pos = (int) (((float) range) * this.mSlideOffset);
                    xStart += pos + lpMargin;
                    this.mSlideOffset = ((float) pos) / ((float) this.mSlideRange);
                } else if (this.mCanSlide && this.mParallaxBy != 0) {
                    offset = (int) ((1.0f - this.mSlideOffset) * ((float) this.mParallaxBy));
                    xStart = nextXStart;
                } else {
                    xStart = nextXStart;
                }
                if (!isLayoutRtl) {
                    int i10 = xStart - offset;
                    childLeft = i10;
                    childRight = i10 + childWidth;
                } else {
                    int i11 = (width - xStart) + offset;
                    childRight = i11;
                    childLeft = i11 - childWidth;
                }
                int i12 = paddingTop;
                int measuredHeight = paddingTop + child.getMeasuredHeight();
                int i13 = measuredHeight;
                child.layout(childLeft, paddingTop, childRight, measuredHeight);
                nextXStart += child.getWidth();
            }
        }
        if (this.mFirstLayout) {
            if (!this.mCanSlide) {
                for (int i14 = 0; i14 < childCount; i14++) {
                    dimChildView(getChildAt(i14), 0.0f, this.mSliderFadeColor);
                }
            } else {
                if (this.mParallaxBy != 0) {
                    parallaxOtherViews(this.mSlideOffset);
                }
                if (((LayoutParams) this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                }
            }
            updateObscuredViewsVisibility(this.mSlideableView);
        }
        this.mFirstLayout = false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        int w = i;
        int h = i2;
        int oldw = i3;
        int oldh = i4;
        int i5 = w;
        int i6 = h;
        int i7 = oldw;
        int i8 = oldh;
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            this.mFirstLayout = true;
        }
    }

    public void requestChildFocus(View view, View view2) {
        View child = view;
        View focused = view2;
        View view3 = child;
        View view4 = focused;
        super.requestChildFocus(child, focused);
        if (!isInTouchMode() && !this.mCanSlide) {
            this.mPreservedOpenState = child == this.mSlideableView;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int action = MotionEventCompat.getActionMasked(ev);
        if (!this.mCanSlide && action == 0 && getChildCount() > 1) {
            View childAt = getChildAt(1);
            View secondChild = childAt;
            if (childAt != null) {
                this.mPreservedOpenState = !this.mDragHelper.isViewUnder(secondChild, (int) ev.getX(), (int) ev.getY());
            }
        }
        if (!this.mCanSlide || (this.mIsUnableToDrag && action != 0)) {
            this.mDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        } else if (action == 3 || action == 1) {
            this.mDragHelper.cancel();
            return false;
        } else {
            boolean interceptTap = false;
            switch (action) {
                case 0:
                    this.mIsUnableToDrag = false;
                    float x = ev.getX();
                    float y = ev.getY();
                    float f = y;
                    this.mInitialMotionX = x;
                    this.mInitialMotionY = y;
                    if (this.mDragHelper.isViewUnder(this.mSlideableView, (int) x, (int) y) && isDimmed(this.mSlideableView)) {
                        interceptTap = true;
                        break;
                    }
                case 2:
                    float x2 = ev.getX();
                    float y2 = ev.getY();
                    float adx = Math.abs(x2 - this.mInitialMotionX);
                    float ady = Math.abs(y2 - this.mInitialMotionY);
                    int touchSlop = this.mDragHelper.getTouchSlop();
                    int i = touchSlop;
                    if (adx > ((float) touchSlop) && ady > adx) {
                        this.mDragHelper.cancel();
                        this.mIsUnableToDrag = true;
                        return false;
                    }
            }
            boolean shouldInterceptTouchEvent = this.mDragHelper.shouldInterceptTouchEvent(ev);
            boolean z = shouldInterceptTouchEvent;
            return shouldInterceptTouchEvent || interceptTap;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        if (!this.mCanSlide) {
            return super.onTouchEvent(ev);
        }
        this.mDragHelper.processTouchEvent(ev);
        switch (ev.getAction() & 255) {
            case 0:
                float x = ev.getX();
                float y = ev.getY();
                float f = y;
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                break;
            case 1:
                if (isDimmed(this.mSlideableView)) {
                    float x2 = ev.getX();
                    float y2 = ev.getY();
                    float dx = x2 - this.mInitialMotionX;
                    float dy = y2 - this.mInitialMotionY;
                    int touchSlop = this.mDragHelper.getTouchSlop();
                    int i = touchSlop;
                    if ((dx * dx) + (dy * dy) < ((float) (touchSlop * touchSlop))) {
                        if (this.mDragHelper.isViewUnder(this.mSlideableView, (int) x2, (int) y2)) {
                            boolean closePane = closePane(this.mSlideableView, 0);
                            break;
                        }
                    }
                }
                break;
        }
        return true;
    }

    private boolean closePane(View view, int i) {
        int initialVelocity = i;
        View view2 = view;
        int i2 = initialVelocity;
        if (!this.mFirstLayout && !smoothSlideTo(0.0f, initialVelocity)) {
            return false;
        }
        this.mPreservedOpenState = false;
        return true;
    }

    private boolean openPane(View view, int i) {
        int initialVelocity = i;
        View view2 = view;
        int i2 = initialVelocity;
        if (!this.mFirstLayout && !smoothSlideTo(1.0f, initialVelocity)) {
            return false;
        }
        this.mPreservedOpenState = true;
        return true;
    }

    @Deprecated
    public void smoothSlideOpen() {
        boolean openPane = openPane();
    }

    public boolean openPane() {
        return openPane(this.mSlideableView, 0);
    }

    @Deprecated
    public void smoothSlideClosed() {
        boolean closePane = closePane();
    }

    public boolean closePane() {
        return closePane(this.mSlideableView, 0);
    }

    public boolean isOpen() {
        return !this.mCanSlide || this.mSlideOffset == 1.0f;
    }

    @Deprecated
    public boolean canSlide() {
        return this.mCanSlide;
    }

    public boolean isSlideable() {
        return this.mCanSlide;
    }

    /* access modifiers changed from: 0000 */
    public void onPanelDragged(int i) {
        int newLeft = i;
        int i2 = newLeft;
        if (this.mSlideableView != null) {
            boolean isLayoutRtl = isLayoutRtlSupport();
            LayoutParams lp = (LayoutParams) this.mSlideableView.getLayoutParams();
            int paddingLeft = (!isLayoutRtl ? getPaddingLeft() : getPaddingRight()) + (!isLayoutRtl ? lp.leftMargin : lp.rightMargin);
            int i3 = paddingLeft;
            this.mSlideOffset = ((float) ((!isLayoutRtl ? newLeft : (getWidth() - newLeft) - this.mSlideableView.getWidth()) - paddingLeft)) / ((float) this.mSlideRange);
            if (this.mParallaxBy != 0) {
                parallaxOtherViews(this.mSlideOffset);
            }
            if (lp.dimWhenOffset) {
                dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
            }
            dispatchOnPanelSlide(this.mSlideableView);
            return;
        }
        this.mSlideOffset = 0.0f;
    }

    private void dimChildView(View view, float f, int i) {
        View v = view;
        float mag = f;
        int fadeColor = i;
        View view2 = v;
        float f2 = mag;
        int i2 = fadeColor;
        LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
        LayoutParams lp = layoutParams;
        if (mag > 0.0f && fadeColor != 0) {
            int i3 = (fadeColor & ViewCompat.MEASURED_STATE_MASK) >>> 24;
            int i4 = i3;
            int i5 = (int) (((float) i3) * mag);
            int i6 = i5;
            int i7 = (i5 << 24) | (fadeColor & ViewCompat.MEASURED_SIZE_MASK);
            int i8 = i7;
            if (layoutParams.dimPaint == null) {
                lp.dimPaint = new Paint();
            }
            Paint paint = lp.dimPaint;
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i7, Mode.SRC_OVER);
            ColorFilter colorFilter = paint.setColorFilter(porterDuffColorFilter);
            if (ViewCompat.getLayerType(v) != 2) {
                ViewCompat.setLayerType(v, 2, lp.dimPaint);
            }
            invalidateChildRegion(v);
        } else if (ViewCompat.getLayerType(v) != 0) {
            if (lp.dimPaint != null) {
                ColorFilter colorFilter2 = lp.dimPaint.setColorFilter(null);
            }
            DisableLayerRunnable dlr = new DisableLayerRunnable(this, v);
            boolean add = this.mPostedRunnables.add(dlr);
            ViewCompat.postOnAnimation(this, dlr);
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        boolean result;
        Canvas canvas2 = canvas;
        View child = view;
        long drawingTime = j;
        Canvas canvas3 = canvas2;
        View view2 = child;
        long j2 = drawingTime;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int save = canvas2.save(2);
        if (this.mCanSlide && !lp.slideable && this.mSlideableView != null) {
            boolean clipBounds = canvas2.getClipBounds(this.mTmpRect);
            if (!isLayoutRtlSupport()) {
                this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
            } else {
                this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
            }
            boolean clipRect = canvas2.clipRect(this.mTmpRect);
        }
        if (VERSION.SDK_INT >= 11) {
            result = super.drawChild(canvas2, child, drawingTime);
        } else if (lp.dimWhenOffset && this.mSlideOffset > 0.0f) {
            if (!child.isDrawingCacheEnabled()) {
                child.setDrawingCacheEnabled(true);
            }
            Bitmap drawingCache = child.getDrawingCache();
            Bitmap cache = drawingCache;
            if (drawingCache == null) {
                int e = Log.e(TAG, "drawChild: child view " + child + " returned null drawing cache");
                result = super.drawChild(canvas2, child, drawingTime);
            } else {
                canvas2.drawBitmap(cache, (float) child.getLeft(), (float) child.getTop(), lp.dimPaint);
                result = false;
            }
        } else {
            if (child.isDrawingCacheEnabled()) {
                child.setDrawingCacheEnabled(false);
            }
            result = super.drawChild(canvas2, child, drawingTime);
        }
        canvas2.restoreToCount(save);
        return result;
    }

    /* access modifiers changed from: 0000 */
    public void invalidateChildRegion(View view) {
        View v = view;
        View view2 = v;
        IMPL.invalidateChildRegion(this, v);
    }

    /* access modifiers changed from: 0000 */
    public boolean smoothSlideTo(float f, int i) {
        int x;
        float slideOffset = f;
        float f2 = slideOffset;
        int i2 = i;
        if (!this.mCanSlide) {
            return false;
        }
        boolean isLayoutRtlSupport = isLayoutRtlSupport();
        boolean z = isLayoutRtlSupport;
        LayoutParams lp = (LayoutParams) this.mSlideableView.getLayoutParams();
        if (!isLayoutRtlSupport) {
            int paddingLeft = getPaddingLeft() + lp.leftMargin;
            int i3 = paddingLeft;
            x = (int) (((float) paddingLeft) + (slideOffset * ((float) this.mSlideRange)));
        } else {
            x = (int) (((float) getWidth()) - ((((float) (getPaddingRight() + lp.rightMargin)) + (slideOffset * ((float) this.mSlideRange))) + ((float) this.mSlideableView.getWidth())));
        }
        if (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, x, this.mSlideableView.getTop())) {
            return false;
        }
        setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void computeScroll() {
        if (this.mDragHelper.continueSettling(true)) {
            if (this.mCanSlide) {
                ViewCompat.postInvalidateOnAnimation(this);
            } else {
                this.mDragHelper.abort();
            }
        }
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        setShadowDrawableLeft(d);
    }

    public void setShadowDrawableLeft(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mShadowDrawableLeft = d;
    }

    public void setShadowDrawableRight(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        this.mShadowDrawableRight = d;
    }

    @Deprecated
    public void setShadowResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        setShadowDrawable(getResources().getDrawable(resId));
    }

    public void setShadowResourceLeft(int i) {
        int resId = i;
        int i2 = resId;
        setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), resId));
    }

    public void setShadowResourceRight(int i) {
        int resId = i;
        int i2 = resId;
        setShadowDrawableRight(ContextCompat.getDrawable(getContext(), resId));
    }

    public void draw(Canvas canvas) {
        Drawable shadowDrawable;
        int left;
        int right;
        Canvas c = canvas;
        Canvas canvas2 = c;
        super.draw(c);
        boolean isLayoutRtlSupport = isLayoutRtlSupport();
        boolean z = isLayoutRtlSupport;
        if (!isLayoutRtlSupport) {
            shadowDrawable = this.mShadowDrawableLeft;
        } else {
            shadowDrawable = this.mShadowDrawableRight;
        }
        View shadowView = getChildCount() <= 1 ? null : getChildAt(1);
        if (shadowView != null && shadowDrawable != null) {
            int top = shadowView.getTop();
            int bottom = shadowView.getBottom();
            int shadowWidth = shadowDrawable.getIntrinsicWidth();
            if (!isLayoutRtlSupport()) {
                int left2 = shadowView.getLeft();
                right = left2;
                left = left2 - shadowWidth;
            } else {
                int right2 = shadowView.getRight();
                left = right2;
                right = right2 + shadowWidth;
            }
            shadowDrawable.setBounds(left, top, right, bottom);
            shadowDrawable.draw(c);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parallaxOtherViews(float r31) {
        /*
            r30 = this;
            r3 = r30
            r4 = r31
            r5 = r3
            r6 = r4
            boolean r7 = r3.isLayoutRtlSupport()
            r8 = r7
            r9 = r8
            android.view.View r10 = r3.mSlideableView
            android.view.ViewGroup$LayoutParams r10 = r10.getLayoutParams()
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r10 = (android.support.p000v4.widget.SlidingPaneLayout.LayoutParams) r10
            r11 = r10
            boolean r12 = r10.dimWhenOffset
            r8 = r12
            r13 = 0
            if (r8 != r13) goto L_0x0029
        L_0x001b:
            r8 = 0
        L_0x001c:
            r14 = r8
            int r8 = r3.getChildCount()
            r15 = r8
            r16 = 0
        L_0x0024:
            r0 = r16
            if (r0 < r15) goto L_0x0037
            return
        L_0x0029:
            r13 = 0
            if (r9 != r13) goto L_0x0032
            int r8 = r11.leftMargin
        L_0x002e:
            r13 = 0
            if (r8 <= r13) goto L_0x0035
            goto L_0x001b
        L_0x0032:
            int r8 = r11.rightMargin
            goto L_0x002e
        L_0x0035:
            r8 = 1
            goto L_0x001c
        L_0x0037:
            r0 = r16
            android.view.View r10 = r3.getChildAt(r0)
            r17 = r10
            r10 = r10
            android.view.View r0 = r3.mSlideableView
            r18 = r0
            r0 = r18
            if (r10 == r0) goto L_0x008c
            float r0 = r3.mParallaxOffset
            r19 = r0
            r21 = 1065353216(0x3f800000, float:1.0)
            float r20 = r21 - r19
            int r0 = r3.mParallaxBy
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r19 = r0
            float r20 = r20 * r19
            r0 = r20
            int r8 = (int) r0
            r23 = r8
            r3.mParallaxOffset = r4
            r21 = 1065353216(0x3f800000, float:1.0)
            float r20 = r21 - r4
            int r0 = r3.mParallaxBy
            r22 = r0
            r0 = r22
            float r0 = (float) r0
            r19 = r0
            float r20 = r20 * r19
            r0 = r20
            int r8 = (int) r0
            r24 = r8
            int r8 = r23 - r24
            r25 = r8
            r13 = 0
            if (r9 != r13) goto L_0x008d
            r22 = r8
        L_0x007f:
            r0 = r17
            r1 = r22
            r0.offsetLeftAndRight(r1)
            r13 = 0
            if (r14 != r13) goto L_0x0091
        L_0x0089:
            int r16 = r16 + 1
            goto L_0x0024
        L_0x008c:
            goto L_0x0089
        L_0x008d:
            int r0 = -r8
            r22 = r0
            goto L_0x007f
        L_0x0091:
            r13 = 0
            if (r9 != r13) goto L_0x00ac
            float r0 = r3.mParallaxOffset
            r27 = r0
            r21 = 1065353216(0x3f800000, float:1.0)
            float r26 = r21 - r27
        L_0x009c:
            int r0 = r3.mCoveredFadeColor
            r28 = r0
            r29 = r17
            r0 = r29
            r1 = r26
            r2 = r28
            r3.dimChildView(r0, r1, r2)
            goto L_0x0089
        L_0x00ac:
            float r0 = r3.mParallaxOffset
            r26 = r0
            r21 = 1065353216(0x3f800000, float:1.0)
            float r26 = r26 - r21
            goto L_0x009c
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.widget.SlidingPaneLayout.parallaxOtherViews(float):void");
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        boolean z2;
        View v = view;
        int dx = i;
        int x = i2;
        int y = i3;
        View view2 = v;
        boolean checkV = z;
        int i4 = dx;
        int i5 = x;
        int i6 = y;
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            int childCount = group.getChildCount();
            int i7 = childCount;
            for (int i8 = childCount - 1; i8 >= 0; i8--) {
                View childAt = group.getChildAt(i8);
                View child = childAt;
                if (x + scrollX >= childAt.getLeft() && x + scrollX < childAt.getRight() && y + scrollY >= childAt.getTop() && y + scrollY < childAt.getBottom()) {
                    if (canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (checkV) {
            if (ViewCompat.canScrollHorizontally(v, !isLayoutRtlSupport() ? -dx : dx)) {
                z2 = true;
                return z2;
            }
        }
        z2 = false;
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean isDimmed(View view) {
        View child = view;
        View view2 = child;
        if (child == null) {
            return false;
        }
        return this.mCanSlide && ((LayoutParams) child.getLayoutParams()).dimWhenOffset && this.mSlideOffset > 0.0f;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return !(p instanceof MarginLayoutParams) ? new LayoutParams(p) : new LayoutParams((MarginLayoutParams) p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return (p instanceof LayoutParams) && super.checkLayoutParams(p);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState ss = savedState;
        savedState.isOpen = !isSlideable() ? this.mPreservedOpenState : isOpen();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            if (!ss.isOpen) {
                boolean closePane = closePane();
            } else {
                boolean openPane = openPane();
            }
            this.mPreservedOpenState = ss.isOpen;
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: 0000 */
    public boolean isLayoutRtlSupport() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }
}
