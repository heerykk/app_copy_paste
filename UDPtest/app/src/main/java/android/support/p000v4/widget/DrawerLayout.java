package android.support.p000v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewGroupCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import android.support.p000v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.widget.DrawerLayout */
public class DrawerLayout extends ViewGroup implements DrawerLayoutImpl {
    private static final boolean ALLOW_EDGE_LOCK = false;
    static final boolean CAN_HIDE_DESCENDANTS = (VERSION.SDK_INT < 19 ? false : CHILDREN_DISALLOW_INTERCEPT);
    private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final int DRAWER_ELEVATION = 10;
    static final DrawerLayoutCompatImpl IMPL;
    static final int[] LAYOUT_ATTRS;
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNDEFINED = 3;
    public static final int LOCK_MODE_UNLOCKED = 0;
    private static final int MIN_DRAWER_MARGIN = 64;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int PEEK_DELAY = 160;
    private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "DrawerLayout";
    private static final float TOUCH_SLOP_SENSITIVITY = 1.0f;
    private final ChildAccessibilityDelegate mChildAccessibilityDelegate;
    private boolean mChildrenCanceledTouch;
    private boolean mDisallowInterceptRequested;
    private boolean mDrawStatusBarBackground;
    private float mDrawerElevation;
    private int mDrawerState;
    private boolean mFirstLayout;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private Object mLastInsets;
    private final ViewDragCallback mLeftCallback;
    private final ViewDragHelper mLeftDragger;
    @Nullable
    private DrawerListener mListener;
    private List<DrawerListener> mListeners;
    private int mLockModeEnd;
    private int mLockModeLeft;
    private int mLockModeRight;
    private int mLockModeStart;
    private int mMinDrawerMargin;
    private final ArrayList<View> mNonDrawerViews;
    private final ViewDragCallback mRightCallback;
    private final ViewDragHelper mRightDragger;
    private int mScrimColor;
    private float mScrimOpacity;
    private Paint mScrimPaint;
    private Drawable mShadowEnd;
    private Drawable mShadowLeft;
    private Drawable mShadowLeftResolved;
    private Drawable mShadowRight;
    private Drawable mShadowRightResolved;
    private Drawable mShadowStart;
    private Drawable mStatusBarBackground;
    private CharSequence mTitleLeft;
    private CharSequence mTitleRight;

    /* renamed from: android.support.v4.widget.DrawerLayout$AccessibilityDelegate */
    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();
        final /* synthetic */ DrawerLayout this$0;

        AccessibilityDelegate(DrawerLayout drawerLayout) {
            DrawerLayout this$02 = drawerLayout;
            DrawerLayout drawerLayout2 = this$02;
            this.this$0 = this$02;
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            if (!DrawerLayout.CAN_HIDE_DESCENDANTS) {
                AccessibilityNodeInfoCompat superNode = AccessibilityNodeInfoCompat.obtain(info);
                super.onInitializeAccessibilityNodeInfo(host, superNode);
                info.setSource(host);
                ViewParent parentForAccessibility = ViewCompat.getParentForAccessibility(host);
                ViewParent parent = parentForAccessibility;
                if (parentForAccessibility instanceof View) {
                    info.setParent((View) parent);
                }
                copyNodeInfoNoChildren(info, superNode);
                superNode.recycle();
                addChildrenForAccessibility(info, (ViewGroup) host);
            } else {
                super.onInitializeAccessibilityNodeInfo(host, info);
            }
            info.setClassName(DrawerLayout.class.getName());
            info.setFocusable(false);
            info.setFocused(false);
            boolean removeAction = info.removeAction(AccessibilityActionCompat.ACTION_FOCUS);
            boolean removeAction2 = info.removeAction(AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(DrawerLayout.class.getName());
        }

        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            View host = view;
            AccessibilityEvent event = accessibilityEvent;
            View view2 = host;
            AccessibilityEvent accessibilityEvent2 = event;
            if (event.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent(host, event);
            }
            List text = event.getText();
            View findVisibleDrawer = this.this$0.findVisibleDrawer();
            View visibleDrawer = findVisibleDrawer;
            if (findVisibleDrawer != null) {
                int drawerViewAbsoluteGravity = this.this$0.getDrawerViewAbsoluteGravity(visibleDrawer);
                int i = drawerViewAbsoluteGravity;
                CharSequence drawerTitle = this.this$0.getDrawerTitle(drawerViewAbsoluteGravity);
                CharSequence title = drawerTitle;
                if (drawerTitle != null) {
                    boolean add = text.add(title);
                }
            }
            return DrawerLayout.CHILDREN_DISALLOW_INTERCEPT;
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            ViewGroup host = viewGroup;
            View child = view;
            AccessibilityEvent event = accessibilityEvent;
            ViewGroup viewGroup2 = host;
            View view2 = child;
            AccessibilityEvent accessibilityEvent2 = event;
            if (!DrawerLayout.CAN_HIDE_DESCENDANTS && !DrawerLayout.includeChildForAccessibility(child)) {
                return false;
            }
            return super.onRequestSendAccessibilityEvent(host, child, event);
        }

        private void addChildrenForAccessibility(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, ViewGroup viewGroup) {
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            ViewGroup v = viewGroup;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            ViewGroup viewGroup2 = v;
            int childCount = v.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = v.getChildAt(i);
                View child = childAt;
                if (DrawerLayout.includeChildForAccessibility(childAt)) {
                    info.addChild(child);
                }
            }
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
        }
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$ChildAccessibilityDelegate */
    final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
        final /* synthetic */ DrawerLayout this$0;

        ChildAccessibilityDelegate(DrawerLayout drawerLayout) {
            DrawerLayout this$02 = drawerLayout;
            DrawerLayout drawerLayout2 = this$02;
            this.this$0 = this$02;
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View child = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = child;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            super.onInitializeAccessibilityNodeInfo(child, info);
            if (!DrawerLayout.includeChildForAccessibility(child)) {
                info.setParent(null);
            }
        }
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$DrawerLayoutCompatImpl */
    interface DrawerLayoutCompatImpl {
        void applyMarginInsets(MarginLayoutParams marginLayoutParams, Object obj, int i);

        void configureApplyInsets(View view);

        void dispatchChildInsets(View view, Object obj, int i);

        Drawable getDefaultStatusBarBackground(Context context);

        int getTopInset(Object obj);
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$DrawerLayoutCompatImplApi21 */
    static class DrawerLayoutCompatImplApi21 implements DrawerLayoutCompatImpl {
        DrawerLayoutCompatImplApi21() {
        }

        public void configureApplyInsets(View view) {
            View drawerLayout = view;
            View view2 = drawerLayout;
            DrawerLayoutCompatApi21.configureApplyInsets(drawerLayout);
        }

        public void dispatchChildInsets(View view, Object obj, int i) {
            View child = view;
            Object insets = obj;
            int drawerGravity = i;
            View view2 = child;
            Object obj2 = insets;
            int i2 = drawerGravity;
            DrawerLayoutCompatApi21.dispatchChildInsets(child, insets, drawerGravity);
        }

        public void applyMarginInsets(MarginLayoutParams marginLayoutParams, Object obj, int i) {
            MarginLayoutParams lp = marginLayoutParams;
            Object insets = obj;
            int drawerGravity = i;
            MarginLayoutParams marginLayoutParams2 = lp;
            Object obj2 = insets;
            int i2 = drawerGravity;
            DrawerLayoutCompatApi21.applyMarginInsets(lp, insets, drawerGravity);
        }

        public int getTopInset(Object obj) {
            Object insets = obj;
            Object obj2 = insets;
            return DrawerLayoutCompatApi21.getTopInset(insets);
        }

        public Drawable getDefaultStatusBarBackground(Context context) {
            Context context2 = context;
            Context context3 = context2;
            return DrawerLayoutCompatApi21.getDefaultStatusBarBackground(context2);
        }
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$DrawerLayoutCompatImplBase */
    static class DrawerLayoutCompatImplBase implements DrawerLayoutCompatImpl {
        DrawerLayoutCompatImplBase() {
        }

        public void configureApplyInsets(View view) {
            View view2 = view;
        }

        public void dispatchChildInsets(View view, Object obj, int i) {
            View view2 = view;
            Object obj2 = obj;
            int i2 = i;
        }

        public void applyMarginInsets(MarginLayoutParams marginLayoutParams, Object obj, int i) {
            MarginLayoutParams marginLayoutParams2 = marginLayoutParams;
            Object obj2 = obj;
            int i2 = i;
        }

        public int getTopInset(Object obj) {
            Object obj2 = obj;
            return 0;
        }

        public Drawable getDefaultStatusBarBackground(Context context) {
            Context context2 = context;
            return null;
        }
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$DrawerListener */
    public interface DrawerListener {
        void onDrawerClosed(View view);

        void onDrawerOpened(View view);

        void onDrawerSlide(View view, float f);

        void onDrawerStateChanged(int i);
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$LayoutParams */
    public static class LayoutParams extends MarginLayoutParams {
        private static final int FLAG_IS_CLOSING = 4;
        private static final int FLAG_IS_OPENED = 1;
        private static final int FLAG_IS_OPENING = 2;
        public int gravity;
        boolean isPeeking;
        float onScreen;
        int openState;

        public LayoutParams(int i, int i2) {
            int width = i;
            int height = i2;
            int i3 = width;
            int i4 = height;
            super(width, height);
            this.gravity = 0;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            Context c = context;
            AttributeSet attrs = attributeSet;
            Context context2 = c;
            AttributeSet attributeSet2 = attrs;
            super(c, attrs);
            this.gravity = 0;
            TypedArray a = c.obtainStyledAttributes(attrs, DrawerLayout.LAYOUT_ATTRS);
            this.gravity = a.getInt(0, 0);
            a.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            LayoutParams source = layoutParams;
            LayoutParams layoutParams2 = source;
            super(source);
            this.gravity = 0;
            this.gravity = source.gravity;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            android.view.ViewGroup.LayoutParams source = layoutParams;
            android.view.ViewGroup.LayoutParams layoutParams2 = source;
            super(source);
            this.gravity = 0;
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            MarginLayoutParams source = marginLayoutParams;
            MarginLayoutParams marginLayoutParams2 = source;
            super(source);
            this.gravity = 0;
        }

        public LayoutParams(int i, int i2, int i3) {
            int width = i;
            int height = i2;
            int gravity2 = i3;
            int i4 = width;
            int i5 = height;
            int i6 = gravity2;
            this(width, height);
            this.gravity = gravity2;
        }
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$SavedState */
    protected static class SavedState extends AbsSavedState {
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
        int lockModeEnd;
        int lockModeLeft;
        int lockModeRight;
        int lockModeStart;
        int openDrawerGravity = 0;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel in = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = in;
            ClassLoader classLoader2 = loader;
            super(in, loader);
            this.openDrawerGravity = in.readInt();
            this.lockModeLeft = in.readInt();
            this.lockModeRight = in.readInt();
            this.lockModeStart = in.readInt();
            this.lockModeEnd = in.readInt();
        }

        public SavedState(Parcelable parcelable) {
            Parcelable superState = parcelable;
            Parcelable parcelable2 = superState;
            super(superState);
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            super.writeToParcel(dest, flags);
            dest.writeInt(this.openDrawerGravity);
            dest.writeInt(this.lockModeLeft);
            dest.writeInt(this.lockModeRight);
            dest.writeInt(this.lockModeStart);
            dest.writeInt(this.lockModeEnd);
        }
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$SimpleDrawerListener */
    public static abstract class SimpleDrawerListener implements DrawerListener {
        public SimpleDrawerListener() {
        }

        public void onDrawerSlide(View view, float f) {
            View view2 = view;
            float f2 = f;
        }

        public void onDrawerOpened(View view) {
            View view2 = view;
        }

        public void onDrawerClosed(View view) {
            View view2 = view;
        }

        public void onDrawerStateChanged(int i) {
            int i2 = i;
        }
    }

    /* renamed from: android.support.v4.widget.DrawerLayout$ViewDragCallback */
    private class ViewDragCallback extends Callback {
        private final int mAbsGravity;
        private ViewDragHelper mDragger;
        private final Runnable mPeekRunnable = new Runnable(this) {
            final /* synthetic */ ViewDragCallback this$1;

            {
                ViewDragCallback this$12 = r5;
                ViewDragCallback viewDragCallback = this$12;
                this.this$1 = this$12;
            }

            public void run() {
                this.this$1.peekDrawer();
            }
        };
        final /* synthetic */ DrawerLayout this$0;

        ViewDragCallback(DrawerLayout drawerLayout, int i) {
            int gravity = i;
            int i2 = gravity;
            this.this$0 = drawerLayout;
            this.mAbsGravity = gravity;
        }

        public void setDragger(ViewDragHelper viewDragHelper) {
            ViewDragHelper dragger = viewDragHelper;
            ViewDragHelper viewDragHelper2 = dragger;
            this.mDragger = dragger;
        }

        public void removeCallbacks() {
            boolean removeCallbacks = this.this$0.removeCallbacks(this.mPeekRunnable);
        }

        public boolean tryCaptureView(View view, int i) {
            boolean z;
            View child = view;
            View view2 = child;
            int i2 = i;
            if (this.this$0.isDrawerView(child)) {
                if (this.this$0.checkDrawerViewAbsoluteGravity(child, this.mAbsGravity) && this.this$0.getDrawerLockMode(child) == 0) {
                    z = DrawerLayout.CHILDREN_DISALLOW_INTERCEPT;
                    return z;
                }
            }
            z = false;
            return z;
        }

        public void onViewDragStateChanged(int i) {
            int state = i;
            int i2 = state;
            this.this$0.updateDrawerState(this.mAbsGravity, state, this.mDragger.getCapturedView());
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            float offset;
            View changedView = view;
            int left = i;
            View view2 = changedView;
            int i5 = left;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            int childWidth = changedView.getWidth();
            if (!this.this$0.checkDrawerViewAbsoluteGravity(changedView, 3)) {
                int width = this.this$0.getWidth();
                int i9 = width;
                offset = ((float) (width - left)) / ((float) childWidth);
            } else {
                offset = ((float) (childWidth + left)) / ((float) childWidth);
            }
            this.this$0.setDrawerViewOffset(changedView, offset);
            changedView.setVisibility(offset == 0.0f ? 4 : 0);
            this.this$0.invalidate();
        }

        public void onViewCaptured(View view, int i) {
            View capturedChild = view;
            View view2 = capturedChild;
            int i2 = i;
            LayoutParams layoutParams = (LayoutParams) capturedChild.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            layoutParams.isPeeking = false;
            closeOtherDrawer();
        }

        private void closeOtherDrawer() {
            View findDrawerWithGravity = this.this$0.findDrawerWithGravity(this.mAbsGravity != 3 ? 3 : 5);
            View toClose = findDrawerWithGravity;
            if (findDrawerWithGravity != null) {
                this.this$0.closeDrawer(toClose);
            }
        }

        public void onViewReleased(View view, float f, float f2) {
            int left;
            View releasedChild = view;
            float xvel = f;
            View view2 = releasedChild;
            float f3 = xvel;
            float f4 = f2;
            float offset = this.this$0.getDrawerViewOffset(releasedChild);
            int childWidth = releasedChild.getWidth();
            if (!this.this$0.checkDrawerViewAbsoluteGravity(releasedChild, 3)) {
                int width = this.this$0.getWidth();
                int i = width;
                left = (((xvel > 0.0f ? 1 : (xvel == 0.0f ? 0 : -1)) < 0 ? DrawerLayout.CHILDREN_DISALLOW_INTERCEPT : false) || (xvel == 0.0f && offset > 0.5f)) ? width - childWidth : width;
            } else {
                left = (((xvel > 0.0f ? 1 : (xvel == 0.0f ? 0 : -1)) > 0 ? DrawerLayout.CHILDREN_DISALLOW_INTERCEPT : false) || (xvel == 0.0f && offset > 0.5f)) ? 0 : -childWidth;
            }
            boolean z = this.mDragger.settleCapturedViewAt(left, releasedChild.getTop());
            this.this$0.invalidate();
        }

        public void onEdgeTouched(int i, int i2) {
            int i3 = i;
            int i4 = i2;
            boolean postDelayed = this.this$0.postDelayed(this.mPeekRunnable, 160);
        }

        /* access modifiers changed from: 0000 */
        public void peekDrawer() {
            View toCapture;
            int childLeft;
            int peekDistance = this.mDragger.getEdgeSize();
            boolean leftEdge = this.mAbsGravity != 3 ? false : DrawerLayout.CHILDREN_DISALLOW_INTERCEPT;
            if (!leftEdge) {
                toCapture = this.this$0.findDrawerWithGravity(5);
                childLeft = this.this$0.getWidth() - peekDistance;
            } else {
                View findDrawerWithGravity = this.this$0.findDrawerWithGravity(3);
                toCapture = findDrawerWithGravity;
                childLeft = (findDrawerWithGravity == null ? 0 : -toCapture.getWidth()) + peekDistance;
            }
            if (toCapture != null) {
                if (((leftEdge && toCapture.getLeft() < childLeft) || (!leftEdge && toCapture.getLeft() > childLeft)) && this.this$0.getDrawerLockMode(toCapture) == 0) {
                    LayoutParams lp = (LayoutParams) toCapture.getLayoutParams();
                    boolean smoothSlideViewTo = this.mDragger.smoothSlideViewTo(toCapture, childLeft, toCapture.getTop());
                    lp.isPeeking = DrawerLayout.CHILDREN_DISALLOW_INTERCEPT;
                    this.this$0.invalidate();
                    closeOtherDrawer();
                    this.this$0.cancelChildViewTouch();
                }
            }
        }

        public boolean onEdgeLock(int i) {
            int i2 = i;
            return false;
        }

        public void onEdgeDragStarted(int i, int i2) {
            View toCapture;
            int edgeFlags = i;
            int pointerId = i2;
            int i3 = edgeFlags;
            int i4 = pointerId;
            if ((edgeFlags & 1) != 1) {
                toCapture = this.this$0.findDrawerWithGravity(5);
            } else {
                toCapture = this.this$0.findDrawerWithGravity(3);
            }
            if (toCapture != null && this.this$0.getDrawerLockMode(toCapture) == 0) {
                this.mDragger.captureChildView(toCapture, pointerId);
            }
        }

        public int getViewHorizontalDragRange(View view) {
            View child = view;
            View view2 = child;
            return !this.this$0.isDrawerView(child) ? 0 : child.getWidth();
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            View child = view;
            int left = i;
            View view2 = child;
            int i3 = left;
            int i4 = i2;
            if (this.this$0.checkDrawerViewAbsoluteGravity(child, 3)) {
                return Math.max(-child.getWidth(), Math.min(left, 0));
            }
            int width = this.this$0.getWidth();
            return Math.max(width - child.getWidth(), Math.min(left, width));
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            View child = view;
            View view2 = child;
            int i3 = i;
            int i4 = i2;
            return child.getTop();
        }
    }

    static {
        boolean z;
        int[] iArr = new int[1];
        iArr[0] = 16842931;
        LAYOUT_ATTRS = iArr;
        if (VERSION.SDK_INT < 21) {
            z = false;
        } else {
            z = CHILDREN_DISALLOW_INTERCEPT;
        }
        SET_DRAWER_SHADOW_FROM_ELEVATION = z;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 21) {
            IMPL = new DrawerLayoutCompatImplBase();
        } else {
            IMPL = new DrawerLayoutCompatImplApi21();
        }
    }

    public DrawerLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        this.mChildAccessibilityDelegate = new ChildAccessibilityDelegate(this);
        this.mScrimColor = DEFAULT_SCRIM_COLOR;
        this.mScrimPaint = new Paint();
        this.mFirstLayout = CHILDREN_DISALLOW_INTERCEPT;
        this.mLockModeLeft = 3;
        this.mLockModeRight = 3;
        this.mLockModeStart = 3;
        this.mLockModeEnd = 3;
        this.mShadowStart = null;
        this.mShadowEnd = null;
        this.mShadowLeft = null;
        this.mShadowRight = null;
        setDescendantFocusability(262144);
        float f = getResources().getDisplayMetrics().density;
        float density = f;
        this.mMinDrawerMargin = (int) ((64.0f * f) + 0.5f);
        float f2 = 400.0f * density;
        float f3 = f2;
        this.mLeftCallback = new ViewDragCallback(this, 3);
        this.mRightCallback = new ViewDragCallback(this, 5);
        this.mLeftDragger = ViewDragHelper.create(this, TOUCH_SLOP_SENSITIVITY, this.mLeftCallback);
        this.mLeftDragger.setEdgeTrackingEnabled(1);
        this.mLeftDragger.setMinVelocity(f2);
        this.mLeftCallback.setDragger(this.mLeftDragger);
        this.mRightDragger = ViewDragHelper.create(this, TOUCH_SLOP_SENSITIVITY, this.mRightCallback);
        this.mRightDragger.setEdgeTrackingEnabled(2);
        this.mRightDragger.setMinVelocity(f2);
        this.mRightCallback.setDragger(this.mRightDragger);
        setFocusableInTouchMode(CHILDREN_DISALLOW_INTERCEPT);
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate(this));
        ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
        if (ViewCompat.getFitsSystemWindows(this)) {
            IMPL.configureApplyInsets(this);
            this.mStatusBarBackground = IMPL.getDefaultStatusBarBackground(context2);
        }
        this.mDrawerElevation = 10.0f * density;
        this.mNonDrawerViews = new ArrayList<>();
    }

    public DrawerLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setDrawerElevation(float f) {
        float elevation = f;
        float f2 = elevation;
        this.mDrawerElevation = elevation;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (isDrawerView(child)) {
                ViewCompat.setElevation(child, this.mDrawerElevation);
            }
        }
    }

    public float getDrawerElevation() {
        if (!SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return 0.0f;
        }
        return this.mDrawerElevation;
    }

    public void setChildInsets(Object obj, boolean z) {
        Object insets = obj;
        Object obj2 = insets;
        boolean draw = z;
        this.mLastInsets = insets;
        this.mDrawStatusBarBackground = draw;
        setWillNotDraw((!draw && getBackground() == null) ? CHILDREN_DISALLOW_INTERCEPT : false);
        requestLayout();
    }

    public void setDrawerShadow(Drawable drawable, int i) {
        Drawable shadowDrawable = drawable;
        int gravity = i;
        Drawable drawable2 = shadowDrawable;
        int i2 = gravity;
        if (!SET_DRAWER_SHADOW_FROM_ELEVATION) {
            if ((gravity & GravityCompat.START) == 8388611) {
                this.mShadowStart = shadowDrawable;
            } else if ((gravity & GravityCompat.END) == 8388613) {
                this.mShadowEnd = shadowDrawable;
            } else if ((gravity & 3) == 3) {
                this.mShadowLeft = shadowDrawable;
            } else if ((gravity & 5) == 5) {
                this.mShadowRight = shadowDrawable;
            } else {
                return;
            }
            resolveShadowDrawables();
            invalidate();
        }
    }

    public void setDrawerShadow(@DrawableRes int i, int i2) {
        int resId = i;
        int gravity = i2;
        int i3 = resId;
        int i4 = gravity;
        setDrawerShadow(ContextCompat.getDrawable(getContext(), resId), gravity);
    }

    public void setScrimColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mScrimColor = color;
        invalidate();
    }

    @Deprecated
    public void setDrawerListener(DrawerListener drawerListener) {
        DrawerListener listener = drawerListener;
        DrawerListener drawerListener2 = listener;
        if (this.mListener != null) {
            removeDrawerListener(this.mListener);
        }
        if (listener != null) {
            addDrawerListener(listener);
        }
        this.mListener = listener;
    }

    public void addDrawerListener(@NonNull DrawerListener drawerListener) {
        DrawerListener listener = drawerListener;
        DrawerListener drawerListener2 = listener;
        if (listener != null) {
            if (this.mListeners == null) {
                this.mListeners = new ArrayList();
            }
            boolean add = this.mListeners.add(listener);
        }
    }

    public void removeDrawerListener(@NonNull DrawerListener drawerListener) {
        DrawerListener listener = drawerListener;
        DrawerListener drawerListener2 = listener;
        if (listener != null && this.mListeners != null) {
            boolean remove = this.mListeners.remove(listener);
        }
    }

    public void setDrawerLockMode(int i) {
        int lockMode = i;
        int i2 = lockMode;
        setDrawerLockMode(lockMode, 3);
        setDrawerLockMode(lockMode, 5);
    }

    public void setDrawerLockMode(int i, int i2) {
        int lockMode = i;
        int edgeGravity = i2;
        int i3 = lockMode;
        int i4 = edgeGravity;
        int absGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
        switch (edgeGravity) {
            case 3:
                this.mLockModeLeft = lockMode;
                break;
            case 5:
                this.mLockModeRight = lockMode;
                break;
            case GravityCompat.START /*8388611*/:
                this.mLockModeStart = lockMode;
                break;
            case GravityCompat.END /*8388613*/:
                this.mLockModeEnd = lockMode;
                break;
        }
        if (lockMode != 0) {
            (absGravity != 3 ? this.mRightDragger : this.mLeftDragger).cancel();
        }
        switch (lockMode) {
            case 1:
                View findDrawerWithGravity = findDrawerWithGravity(absGravity);
                View toClose = findDrawerWithGravity;
                if (findDrawerWithGravity != null) {
                    closeDrawer(toClose);
                    return;
                }
                return;
            case 2:
                View findDrawerWithGravity2 = findDrawerWithGravity(absGravity);
                View toOpen = findDrawerWithGravity2;
                if (findDrawerWithGravity2 != null) {
                    openDrawer(toOpen);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setDrawerLockMode(int i, View view) {
        int lockMode = i;
        View drawerView = view;
        int i2 = lockMode;
        View view2 = drawerView;
        if (isDrawerView(drawerView)) {
            int i3 = ((LayoutParams) drawerView.getLayoutParams()).gravity;
            int i4 = i3;
            setDrawerLockMode(lockMode, i3);
            return;
        }
        throw new IllegalArgumentException("View " + drawerView + " is not a " + "drawer with appropriate layout_gravity");
    }

    public int getDrawerLockMode(int i) {
        int edgeGravity = i;
        int i2 = edgeGravity;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        switch (edgeGravity) {
            case 3:
                if (this.mLockModeLeft != 3) {
                    return this.mLockModeLeft;
                }
                int leftLockMode = layoutDirection != 0 ? this.mLockModeEnd : this.mLockModeStart;
                if (leftLockMode != 3) {
                    return leftLockMode;
                }
                break;
            case 5:
                if (this.mLockModeRight != 3) {
                    return this.mLockModeRight;
                }
                int rightLockMode = layoutDirection != 0 ? this.mLockModeStart : this.mLockModeEnd;
                if (rightLockMode != 3) {
                    return rightLockMode;
                }
                break;
            case GravityCompat.START /*8388611*/:
                if (this.mLockModeStart != 3) {
                    return this.mLockModeStart;
                }
                int startLockMode = layoutDirection != 0 ? this.mLockModeRight : this.mLockModeLeft;
                if (startLockMode != 3) {
                    return startLockMode;
                }
                break;
            case GravityCompat.END /*8388613*/:
                if (this.mLockModeEnd != 3) {
                    return this.mLockModeEnd;
                }
                int endLockMode = layoutDirection != 0 ? this.mLockModeLeft : this.mLockModeRight;
                if (endLockMode != 3) {
                    return endLockMode;
                }
                break;
        }
        return 0;
    }

    public int getDrawerLockMode(View view) {
        View drawerView = view;
        View view2 = drawerView;
        if (isDrawerView(drawerView)) {
            int i = ((LayoutParams) drawerView.getLayoutParams()).gravity;
            int i2 = i;
            return getDrawerLockMode(i);
        }
        throw new IllegalArgumentException("View " + drawerView + " is not a drawer");
    }

    public void setDrawerTitle(int i, CharSequence charSequence) {
        int edgeGravity = i;
        CharSequence title = charSequence;
        int i2 = edgeGravity;
        CharSequence charSequence2 = title;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
        int absGravity = absoluteGravity;
        if (absoluteGravity == 3) {
            this.mTitleLeft = title;
        } else if (absGravity == 5) {
            this.mTitleRight = title;
        }
    }

    @Nullable
    public CharSequence getDrawerTitle(int i) {
        int edgeGravity = i;
        int i2 = edgeGravity;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(edgeGravity, ViewCompat.getLayoutDirection(this));
        int absGravity = absoluteGravity;
        if (absoluteGravity == 3) {
            return this.mTitleLeft;
        }
        if (absGravity != 5) {
            return null;
        }
        return this.mTitleRight;
    }

    /* access modifiers changed from: 0000 */
    public void updateDrawerState(int i, int i2, View view) {
        int state;
        int activeState = i2;
        View activeDrawer = view;
        int i3 = i;
        int i4 = activeState;
        View view2 = activeDrawer;
        int leftState = this.mLeftDragger.getViewDragState();
        int rightState = this.mRightDragger.getViewDragState();
        if (leftState == 1 || rightState == 1) {
            state = 1;
        } else if (leftState == 2 || rightState == 2) {
            state = 2;
        } else {
            state = 0;
        }
        if (activeDrawer != null && activeState == 0) {
            LayoutParams layoutParams = (LayoutParams) activeDrawer.getLayoutParams();
            LayoutParams lp = layoutParams;
            if (layoutParams.onScreen == 0.0f) {
                dispatchOnDrawerClosed(activeDrawer);
            } else if (lp.onScreen == TOUCH_SLOP_SENSITIVITY) {
                dispatchOnDrawerOpened(activeDrawer);
            }
        }
        if (state != this.mDrawerState) {
            this.mDrawerState = state;
            if (this.mListeners != null) {
                int size = this.mListeners.size();
                int i5 = size;
                for (int i6 = size - 1; i6 >= 0; i6--) {
                    ((DrawerListener) this.mListeners.get(i6)).onDrawerStateChanged(state);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnDrawerClosed(View view) {
        View drawerView = view;
        View view2 = drawerView;
        LayoutParams layoutParams = (LayoutParams) drawerView.getLayoutParams();
        LayoutParams lp = layoutParams;
        if ((layoutParams.openState & 1) == 1) {
            lp.openState = 0;
            if (this.mListeners != null) {
                int size = this.mListeners.size();
                int i = size;
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    ((DrawerListener) this.mListeners.get(i2)).onDrawerClosed(drawerView);
                }
            }
            updateChildrenImportantForAccessibility(drawerView, false);
            if (hasWindowFocus()) {
                View rootView = getRootView();
                View rootView2 = rootView;
                if (rootView != null) {
                    rootView2.sendAccessibilityEvent(32);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnDrawerOpened(View view) {
        View drawerView = view;
        View view2 = drawerView;
        LayoutParams layoutParams = (LayoutParams) drawerView.getLayoutParams();
        LayoutParams lp = layoutParams;
        if ((layoutParams.openState & 1) == 0) {
            lp.openState = 1;
            if (this.mListeners != null) {
                int size = this.mListeners.size();
                int i = size;
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    ((DrawerListener) this.mListeners.get(i2)).onDrawerOpened(drawerView);
                }
            }
            updateChildrenImportantForAccessibility(drawerView, CHILDREN_DISALLOW_INTERCEPT);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
        }
    }

    private void updateChildrenImportantForAccessibility(View view, boolean z) {
        View drawerView = view;
        View view2 = drawerView;
        boolean isDrawerOpen = z;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if ((!isDrawerOpen && !isDrawerView(child)) || (isDrawerOpen && child == drawerView)) {
                ViewCompat.setImportantForAccessibility(child, 1);
            } else {
                ViewCompat.setImportantForAccessibility(child, 4);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnDrawerSlide(View view, float f) {
        View drawerView = view;
        float slideOffset = f;
        View view2 = drawerView;
        float f2 = slideOffset;
        if (this.mListeners != null) {
            int size = this.mListeners.size();
            int i = size;
            for (int i2 = size - 1; i2 >= 0; i2--) {
                ((DrawerListener) this.mListeners.get(i2)).onDrawerSlide(drawerView, slideOffset);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setDrawerViewOffset(View view, float f) {
        View drawerView = view;
        float slideOffset = f;
        View view2 = drawerView;
        float f2 = slideOffset;
        LayoutParams layoutParams = (LayoutParams) drawerView.getLayoutParams();
        LayoutParams layoutParams2 = layoutParams;
        if (slideOffset != layoutParams.onScreen) {
            layoutParams.onScreen = slideOffset;
            dispatchOnDrawerSlide(drawerView, slideOffset);
        }
    }

    /* access modifiers changed from: 0000 */
    public float getDrawerViewOffset(View view) {
        View drawerView = view;
        View view2 = drawerView;
        return ((LayoutParams) drawerView.getLayoutParams()).onScreen;
    }

    /* access modifiers changed from: 0000 */
    public int getDrawerViewAbsoluteGravity(View view) {
        View drawerView = view;
        View view2 = drawerView;
        int i = ((LayoutParams) drawerView.getLayoutParams()).gravity;
        int i2 = i;
        return GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
    }

    /* access modifiers changed from: 0000 */
    public boolean checkDrawerViewAbsoluteGravity(View view, int i) {
        View drawerView = view;
        int checkFor = i;
        View view2 = drawerView;
        int i2 = checkFor;
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(drawerView);
        int i3 = drawerViewAbsoluteGravity;
        return (drawerViewAbsoluteGravity & checkFor) != checkFor ? false : CHILDREN_DISALLOW_INTERCEPT;
    }

    /* access modifiers changed from: 0000 */
    public View findOpenDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            View child = childAt;
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            if ((layoutParams.openState & 1) == 1) {
                return child;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void moveDrawerToOffset(View view, float f) {
        View drawerView = view;
        float slideOffset = f;
        View view2 = drawerView;
        float f2 = slideOffset;
        float oldOffset = getDrawerViewOffset(drawerView);
        int width = drawerView.getWidth();
        int oldPos = (int) (((float) width) * oldOffset);
        int i = (int) (((float) width) * slideOffset);
        int i2 = i;
        int i3 = i - oldPos;
        int i4 = i3;
        drawerView.offsetLeftAndRight(!checkDrawerViewAbsoluteGravity(drawerView, 3) ? -i3 : i3);
        setDrawerViewOffset(drawerView, slideOffset);
    }

    /* access modifiers changed from: 0000 */
    public View findDrawerWithGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        int absHorizGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this)) & 7;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View child = getChildAt(i3);
            int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(child);
            int i4 = drawerViewAbsoluteGravity;
            if ((drawerViewAbsoluteGravity & 7) == absHorizGravity) {
                return child;
            }
        }
        return null;
    }

    static String gravityToString(int i) {
        int gravity = i;
        int i2 = gravity;
        if ((gravity & 3) == 3) {
            return "LEFT";
        }
        if ((gravity & 5) != 5) {
            return Integer.toHexString(gravity);
        }
        return "RIGHT";
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = CHILDREN_DISALLOW_INTERCEPT;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = CHILDREN_DISALLOW_INTERCEPT;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (!(widthMode == 1073741824 && heightMode == 1073741824)) {
            if (!isInEditMode()) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
                throw illegalArgumentException;
            }
            if (widthMode != Integer.MIN_VALUE) {
                if (widthMode == 0) {
                    widthSize = 300;
                }
            }
            if (heightMode != Integer.MIN_VALUE) {
                if (heightMode == 0) {
                    heightSize = 300;
                }
            }
        }
        setMeasuredDimension(widthSize, heightSize);
        boolean applyInsets = (this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this)) ? CHILDREN_DISALLOW_INTERCEPT : false;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        boolean hasDrawerOnLeftEdge = false;
        boolean hasDrawerOnRightEdge = false;
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                LayoutParams lp = layoutParams;
                if (applyInsets) {
                    int cgrav = GravityCompat.getAbsoluteGravity(layoutParams.gravity, layoutDirection);
                    if (!ViewCompat.getFitsSystemWindows(child)) {
                        IMPL.applyMarginInsets(lp, this.mLastInsets, cgrav);
                    } else {
                        IMPL.dispatchChildInsets(child, this.mLastInsets, cgrav);
                    }
                }
                if (isContentView(child)) {
                    int contentWidthSpec = MeasureSpec.makeMeasureSpec((widthSize - lp.leftMargin) - lp.rightMargin, 1073741824);
                    int makeMeasureSpec = MeasureSpec.makeMeasureSpec((heightSize - lp.topMargin) - lp.bottomMargin, 1073741824);
                    int i6 = makeMeasureSpec;
                    child.measure(contentWidthSpec, makeMeasureSpec);
                } else if (!isDrawerView(child)) {
                    IllegalStateException illegalStateException = new IllegalStateException("Child " + child + " at index " + i5 + " does not have a valid layout_gravity - must be Gravity.LEFT, " + "Gravity.RIGHT or Gravity.NO_GRAVITY");
                    throw illegalStateException;
                } else {
                    if (SET_DRAWER_SHADOW_FROM_ELEVATION && ViewCompat.getElevation(child) != this.mDrawerElevation) {
                        ViewCompat.setElevation(child, this.mDrawerElevation);
                    }
                    int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(child) & 7;
                    int childGravity = drawerViewAbsoluteGravity;
                    boolean isLeftEdgeDrawer = drawerViewAbsoluteGravity != 3 ? false : CHILDREN_DISALLOW_INTERCEPT;
                    if ((isLeftEdgeDrawer && hasDrawerOnLeftEdge) || (!isLeftEdgeDrawer && hasDrawerOnRightEdge)) {
                        IllegalStateException illegalStateException2 = new IllegalStateException("Child drawer has absolute gravity " + gravityToString(childGravity) + " but this " + TAG + " already has a " + "drawer view along that edge");
                        throw illegalStateException2;
                    }
                    if (!isLeftEdgeDrawer) {
                        hasDrawerOnRightEdge = CHILDREN_DISALLOW_INTERCEPT;
                    } else {
                        hasDrawerOnLeftEdge = CHILDREN_DISALLOW_INTERCEPT;
                    }
                    int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec, this.mMinDrawerMargin + lp.leftMargin + lp.rightMargin, lp.width);
                    int childMeasureSpec = getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin, lp.height);
                    int i7 = childMeasureSpec;
                    child.measure(drawerWidthSpec, childMeasureSpec);
                }
            }
        }
    }

    private void resolveShadowDrawables() {
        if (!SET_DRAWER_SHADOW_FROM_ELEVATION) {
            this.mShadowLeftResolved = resolveLeftShadow();
            this.mShadowRightResolved = resolveRightShadow();
        }
    }

    private Drawable resolveLeftShadow() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int layoutDirection2 = layoutDirection;
        if (layoutDirection != 0) {
            if (this.mShadowEnd != null) {
                boolean mirror = mirror(this.mShadowEnd, layoutDirection2);
                return this.mShadowEnd;
            }
        } else if (this.mShadowStart != null) {
            boolean mirror2 = mirror(this.mShadowStart, layoutDirection2);
            return this.mShadowStart;
        }
        return this.mShadowLeft;
    }

    private Drawable resolveRightShadow() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int layoutDirection2 = layoutDirection;
        if (layoutDirection != 0) {
            if (this.mShadowStart != null) {
                boolean mirror = mirror(this.mShadowStart, layoutDirection2);
                return this.mShadowStart;
            }
        } else if (this.mShadowEnd != null) {
            boolean mirror2 = mirror(this.mShadowEnd, layoutDirection2);
            return this.mShadowEnd;
        }
        return this.mShadowRight;
    }

    private boolean mirror(Drawable drawable, int i) {
        Drawable drawable2 = drawable;
        int layoutDirection = i;
        Drawable drawable3 = drawable2;
        int i2 = layoutDirection;
        if (drawable2 == null || !DrawableCompat.isAutoMirrored(drawable2)) {
            return false;
        }
        boolean layoutDirection2 = DrawableCompat.setLayoutDirection(drawable2, layoutDirection);
        return CHILDREN_DISALLOW_INTERCEPT;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childLeft;
        float newOffset;
        int l = i;
        int t = i2;
        int r = i3;
        int b = i4;
        boolean z2 = z;
        int i5 = l;
        int i6 = t;
        int i7 = r;
        int i8 = b;
        this.mInLayout = CHILDREN_DISALLOW_INTERCEPT;
        int width = r - l;
        int childCount = getChildCount();
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            View child = childAt;
            if (childAt.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (!isContentView(child)) {
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    if (!checkDrawerViewAbsoluteGravity(child, 3)) {
                        childLeft = width - ((int) (((float) childWidth) * lp.onScreen));
                        newOffset = ((float) (width - childLeft)) / ((float) childWidth);
                    } else {
                        childLeft = (-childWidth) + ((int) (((float) childWidth) * lp.onScreen));
                        newOffset = ((float) (childWidth + childLeft)) / ((float) childWidth);
                    }
                    boolean changeOffset = newOffset != lp.onScreen ? CHILDREN_DISALLOW_INTERCEPT : false;
                    int i10 = lp.gravity & 112;
                    int i11 = i10;
                    switch (i10) {
                        case 16:
                            int i12 = b - t;
                            int height = i12;
                            int i13 = (i12 - childHeight) / 2;
                            int childTop = i13;
                            if (i13 >= lp.topMargin) {
                                if (childTop + childHeight > height - lp.bottomMargin) {
                                    childTop = (height - lp.bottomMargin) - childHeight;
                                }
                            } else {
                                childTop = lp.topMargin;
                            }
                            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                            break;
                        case 80:
                            int i14 = b - t;
                            int i15 = i14;
                            child.layout(childLeft, (i14 - lp.bottomMargin) - child.getMeasuredHeight(), childLeft + childWidth, i14 - lp.bottomMargin);
                            break;
                        default:
                            child.layout(childLeft, lp.topMargin, childLeft + childWidth, lp.topMargin + childHeight);
                            break;
                    }
                    if (changeOffset) {
                        setDrawerViewOffset(child, newOffset);
                    }
                    int newVisibility = lp.onScreen > 0.0f ? 0 : 4;
                    if (child.getVisibility() != newVisibility) {
                        child.setVisibility(newVisibility);
                    }
                } else {
                    child.layout(lp.leftMargin, lp.topMargin, lp.leftMargin + child.getMeasuredWidth(), lp.topMargin + child.getMeasuredHeight());
                }
            }
        }
        this.mInLayout = false;
        this.mFirstLayout = false;
    }

    public void requestLayout() {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    public void computeScroll() {
        int childCount = getChildCount();
        float scrimOpacity = 0.0f;
        for (int i = 0; i < childCount; i++) {
            scrimOpacity = Math.max(scrimOpacity, ((LayoutParams) getChildAt(i).getLayoutParams()).onScreen);
        }
        this.mScrimOpacity = scrimOpacity;
        if (this.mLeftDragger.continueSettling(CHILDREN_DISALLOW_INTERCEPT) || this.mRightDragger.continueSettling(CHILDREN_DISALLOW_INTERCEPT)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private static boolean hasOpaqueBackground(View view) {
        View v = view;
        View view2 = v;
        Drawable background = v.getBackground();
        Drawable bg = background;
        if (background == null) {
            return false;
        }
        return bg.getOpacity() != -1 ? false : CHILDREN_DISALLOW_INTERCEPT;
    }

    public void setStatusBarBackground(Drawable drawable) {
        Drawable bg = drawable;
        Drawable drawable2 = bg;
        this.mStatusBarBackground = bg;
        invalidate();
    }

    public Drawable getStatusBarBackgroundDrawable() {
        return this.mStatusBarBackground;
    }

    public void setStatusBarBackground(int i) {
        int resId = i;
        int i2 = resId;
        this.mStatusBarBackground = resId == 0 ? null : ContextCompat.getDrawable(getContext(), resId);
        invalidate();
    }

    public void setStatusBarBackgroundColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mStatusBarBackground = new ColorDrawable(color);
        invalidate();
    }

    public void onRtlPropertiesChanged(int i) {
        int i2 = i;
        resolveShadowDrawables();
    }

    public void onDraw(Canvas canvas) {
        Canvas c = canvas;
        Canvas canvas2 = c;
        super.onDraw(c);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            int topInset = IMPL.getTopInset(this.mLastInsets);
            int inset = topInset;
            if (topInset > 0) {
                this.mStatusBarBackground.setBounds(0, 0, getWidth(), inset);
                this.mStatusBarBackground.draw(c);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        Canvas canvas2 = canvas;
        View child = view;
        long drawingTime = j;
        Canvas canvas3 = canvas2;
        View view2 = child;
        long j2 = drawingTime;
        int height = getHeight();
        boolean drawingContent = isContentView(child);
        int clipLeft = 0;
        int clipRight = getWidth();
        int restoreCount = canvas2.save();
        if (drawingContent) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                View v = childAt;
                if (childAt != child && v.getVisibility() == 0 && hasOpaqueBackground(v) && isDrawerView(v) && v.getHeight() >= height) {
                    if (!checkDrawerViewAbsoluteGravity(v, 3)) {
                        int left = v.getLeft();
                        int vleft = left;
                        if (left < clipRight) {
                            clipRight = vleft;
                        }
                    } else {
                        int right = v.getRight();
                        int vright = right;
                        if (right > clipLeft) {
                            clipLeft = vright;
                        }
                    }
                }
            }
            boolean clipRect = canvas2.clipRect(clipLeft, 0, clipRight, getHeight());
        }
        boolean result = super.drawChild(canvas2, child, drawingTime);
        canvas2.restoreToCount(restoreCount);
        if (this.mScrimOpacity > 0.0f && drawingContent) {
            int i2 = (this.mScrimColor & ViewCompat.MEASURED_STATE_MASK) >>> 24;
            int i3 = i2;
            int i4 = (int) (((float) i2) * this.mScrimOpacity);
            int i5 = i4;
            int i6 = (i4 << 24) | (this.mScrimColor & ViewCompat.MEASURED_SIZE_MASK);
            int i7 = i6;
            this.mScrimPaint.setColor(i6);
            canvas2.drawRect((float) clipLeft, 0.0f, (float) clipRight, (float) getHeight(), this.mScrimPaint);
        } else if (this.mShadowLeftResolved != null && checkDrawerViewAbsoluteGravity(child, 3)) {
            int shadowWidth = this.mShadowLeftResolved.getIntrinsicWidth();
            int childRight = child.getRight();
            int edgeSize = this.mLeftDragger.getEdgeSize();
            int i8 = edgeSize;
            float max = Math.max(0.0f, Math.min(((float) childRight) / ((float) edgeSize), TOUCH_SLOP_SENSITIVITY));
            float f = max;
            this.mShadowLeftResolved.setBounds(childRight, child.getTop(), childRight + shadowWidth, child.getBottom());
            this.mShadowLeftResolved.setAlpha((int) (255.0f * max));
            this.mShadowLeftResolved.draw(canvas2);
        } else if (this.mShadowRightResolved != null && checkDrawerViewAbsoluteGravity(child, 5)) {
            int shadowWidth2 = this.mShadowRightResolved.getIntrinsicWidth();
            int childLeft = child.getLeft();
            int showing = getWidth() - childLeft;
            int edgeSize2 = this.mRightDragger.getEdgeSize();
            int i9 = edgeSize2;
            float max2 = Math.max(0.0f, Math.min(((float) showing) / ((float) edgeSize2), TOUCH_SLOP_SENSITIVITY));
            float f2 = max2;
            this.mShadowRightResolved.setBounds(childLeft - shadowWidth2, child.getTop(), childLeft, child.getBottom());
            this.mShadowRightResolved.setAlpha((int) (255.0f * max2));
            this.mShadowRightResolved.draw(canvas2);
        }
        return result;
    }

    /* access modifiers changed from: 0000 */
    public boolean isContentView(View view) {
        View child = view;
        View view2 = child;
        return ((LayoutParams) child.getLayoutParams()).gravity != 0 ? false : CHILDREN_DISALLOW_INTERCEPT;
    }

    /* access modifiers changed from: 0000 */
    public boolean isDrawerView(View view) {
        View child = view;
        View view2 = child;
        int i = ((LayoutParams) child.getLayoutParams()).gravity;
        int i2 = i;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(child));
        int absGravity = absoluteGravity;
        if ((absoluteGravity & 3) != 0) {
            return CHILDREN_DISALLOW_INTERCEPT;
        }
        if ((absGravity & 5) == 0) {
            return false;
        }
        return CHILDREN_DISALLOW_INTERCEPT;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        boolean interceptForDrag = this.mLeftDragger.shouldInterceptTouchEvent(ev) | this.mRightDragger.shouldInterceptTouchEvent(ev);
        boolean interceptForTap = false;
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 0:
                float x = ev.getX();
                float y = ev.getY();
                float y2 = y;
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                if (this.mScrimOpacity > 0.0f) {
                    View findTopChildUnder = this.mLeftDragger.findTopChildUnder((int) x, (int) y2);
                    View child = findTopChildUnder;
                    if (findTopChildUnder != null && isContentView(child)) {
                        interceptForTap = CHILDREN_DISALLOW_INTERCEPT;
                    }
                }
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            case 1:
            case 3:
                closeDrawers(CHILDREN_DISALLOW_INTERCEPT);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            case 2:
                if (this.mLeftDragger.checkTouchSlop(3)) {
                    this.mLeftCallback.removeCallbacks();
                    this.mRightCallback.removeCallbacks();
                    break;
                }
                break;
        }
        return (!interceptForDrag && !interceptForTap && !hasPeekingDrawer() && !this.mChildrenCanceledTouch) ? false : CHILDREN_DISALLOW_INTERCEPT;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        this.mLeftDragger.processTouchEvent(ev);
        this.mRightDragger.processTouchEvent(ev);
        switch (ev.getAction() & 255) {
            case 0:
                float x = ev.getX();
                float y = ev.getY();
                float f = y;
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            case 1:
                float x2 = ev.getX();
                float y2 = ev.getY();
                float y3 = y2;
                boolean peekingOnly = CHILDREN_DISALLOW_INTERCEPT;
                View findTopChildUnder = this.mLeftDragger.findTopChildUnder((int) x2, (int) y2);
                View touchedView = findTopChildUnder;
                if (findTopChildUnder != null && isContentView(touchedView)) {
                    float dx = x2 - this.mInitialMotionX;
                    float dy = y3 - this.mInitialMotionY;
                    int touchSlop = this.mLeftDragger.getTouchSlop();
                    int i = touchSlop;
                    if ((dx * dx) + (dy * dy) < ((float) (touchSlop * touchSlop))) {
                        View findOpenDrawer = findOpenDrawer();
                        View openDrawer = findOpenDrawer;
                        if (findOpenDrawer != null) {
                            peekingOnly = getDrawerLockMode(openDrawer) != 2 ? false : CHILDREN_DISALLOW_INTERCEPT;
                        }
                    }
                }
                closeDrawers(peekingOnly);
                this.mDisallowInterceptRequested = false;
                break;
            case 3:
                closeDrawers(CHILDREN_DISALLOW_INTERCEPT);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
        }
        return CHILDREN_DISALLOW_INTERCEPT;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        boolean disallowIntercept = z;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        this.mDisallowInterceptRequested = disallowIntercept;
        if (disallowIntercept) {
            closeDrawers(CHILDREN_DISALLOW_INTERCEPT);
        }
    }

    public void closeDrawers() {
        closeDrawers(false);
    }

    /* access modifiers changed from: 0000 */
    public void closeDrawers(boolean z) {
        boolean peekingOnly = z;
        boolean needsInvalidate = false;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            View child = childAt;
            LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
            if (isDrawerView(child) && (!peekingOnly || lp.isPeeking)) {
                int childWidth = child.getWidth();
                if (!checkDrawerViewAbsoluteGravity(child, 3)) {
                    needsInvalidate |= this.mRightDragger.smoothSlideViewTo(child, getWidth(), child.getTop());
                } else {
                    needsInvalidate |= this.mLeftDragger.smoothSlideViewTo(child, -childWidth, child.getTop());
                }
                lp.isPeeking = false;
            }
        }
        this.mLeftCallback.removeCallbacks();
        this.mRightCallback.removeCallbacks();
        if (needsInvalidate) {
            invalidate();
        }
    }

    public void openDrawer(View view) {
        View drawerView = view;
        View view2 = drawerView;
        openDrawer(drawerView, (boolean) CHILDREN_DISALLOW_INTERCEPT);
    }

    public void openDrawer(View view, boolean z) {
        View drawerView = view;
        View view2 = drawerView;
        boolean animate = z;
        if (isDrawerView(drawerView)) {
            LayoutParams lp = (LayoutParams) drawerView.getLayoutParams();
            if (this.mFirstLayout) {
                lp.onScreen = TOUCH_SLOP_SENSITIVITY;
                lp.openState = 1;
                updateChildrenImportantForAccessibility(drawerView, CHILDREN_DISALLOW_INTERCEPT);
            } else if (!animate) {
                moveDrawerToOffset(drawerView, TOUCH_SLOP_SENSITIVITY);
                updateDrawerState(lp.gravity, 0, drawerView);
                drawerView.setVisibility(0);
            } else {
                lp.openState |= 2;
                if (!checkDrawerViewAbsoluteGravity(drawerView, 3)) {
                    boolean smoothSlideViewTo = this.mRightDragger.smoothSlideViewTo(drawerView, getWidth() - drawerView.getWidth(), drawerView.getTop());
                } else {
                    boolean smoothSlideViewTo2 = this.mLeftDragger.smoothSlideViewTo(drawerView, 0, drawerView.getTop());
                }
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + drawerView + " is not a sliding drawer");
    }

    public void openDrawer(int i) {
        int gravity = i;
        int i2 = gravity;
        openDrawer(gravity, (boolean) CHILDREN_DISALLOW_INTERCEPT);
    }

    public void openDrawer(int i, boolean z) {
        int gravity = i;
        int i2 = gravity;
        boolean animate = z;
        View findDrawerWithGravity = findDrawerWithGravity(gravity);
        View drawerView = findDrawerWithGravity;
        if (findDrawerWithGravity != null) {
            openDrawer(drawerView, animate);
            return;
        }
        throw new IllegalArgumentException("No drawer view found with gravity " + gravityToString(gravity));
    }

    public void closeDrawer(View view) {
        View drawerView = view;
        View view2 = drawerView;
        closeDrawer(drawerView, (boolean) CHILDREN_DISALLOW_INTERCEPT);
    }

    public void closeDrawer(View view, boolean z) {
        View drawerView = view;
        View view2 = drawerView;
        boolean animate = z;
        if (isDrawerView(drawerView)) {
            LayoutParams lp = (LayoutParams) drawerView.getLayoutParams();
            if (this.mFirstLayout) {
                lp.onScreen = 0.0f;
                lp.openState = 0;
            } else if (!animate) {
                moveDrawerToOffset(drawerView, 0.0f);
                updateDrawerState(lp.gravity, 0, drawerView);
                drawerView.setVisibility(4);
            } else {
                lp.openState |= 4;
                if (!checkDrawerViewAbsoluteGravity(drawerView, 3)) {
                    boolean smoothSlideViewTo = this.mRightDragger.smoothSlideViewTo(drawerView, getWidth(), drawerView.getTop());
                } else {
                    boolean smoothSlideViewTo2 = this.mLeftDragger.smoothSlideViewTo(drawerView, -drawerView.getWidth(), drawerView.getTop());
                }
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + drawerView + " is not a sliding drawer");
    }

    public void closeDrawer(int i) {
        int gravity = i;
        int i2 = gravity;
        closeDrawer(gravity, (boolean) CHILDREN_DISALLOW_INTERCEPT);
    }

    public void closeDrawer(int i, boolean z) {
        int gravity = i;
        int i2 = gravity;
        boolean animate = z;
        View findDrawerWithGravity = findDrawerWithGravity(gravity);
        View drawerView = findDrawerWithGravity;
        if (findDrawerWithGravity != null) {
            closeDrawer(drawerView, animate);
            return;
        }
        throw new IllegalArgumentException("No drawer view found with gravity " + gravityToString(gravity));
    }

    public boolean isDrawerOpen(View view) {
        View drawer = view;
        View view2 = drawer;
        if (isDrawerView(drawer)) {
            LayoutParams layoutParams = (LayoutParams) drawer.getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            return (layoutParams.openState & 1) != 1 ? false : CHILDREN_DISALLOW_INTERCEPT;
        }
        throw new IllegalArgumentException("View " + drawer + " is not a drawer");
    }

    public boolean isDrawerOpen(int i) {
        int drawerGravity = i;
        int i2 = drawerGravity;
        View findDrawerWithGravity = findDrawerWithGravity(drawerGravity);
        View drawerView = findDrawerWithGravity;
        if (findDrawerWithGravity == null) {
            return false;
        }
        return isDrawerOpen(drawerView);
    }

    public boolean isDrawerVisible(View view) {
        View drawer = view;
        View view2 = drawer;
        if (isDrawerView(drawer)) {
            return ((LayoutParams) drawer.getLayoutParams()).onScreen > 0.0f ? CHILDREN_DISALLOW_INTERCEPT : false;
        }
        throw new IllegalArgumentException("View " + drawer + " is not a drawer");
    }

    public boolean isDrawerVisible(int i) {
        int drawerGravity = i;
        int i2 = drawerGravity;
        View findDrawerWithGravity = findDrawerWithGravity(drawerGravity);
        View drawerView = findDrawerWithGravity;
        if (findDrawerWithGravity == null) {
            return false;
        }
        return isDrawerVisible(drawerView);
    }

    private boolean hasPeekingDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            if (layoutParams.isPeeking) {
                return CHILDREN_DISALLOW_INTERCEPT;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        LayoutParams layoutParams3 = !(p instanceof LayoutParams) ? !(p instanceof MarginLayoutParams) ? new LayoutParams(p) : new LayoutParams((MarginLayoutParams) p) : new LayoutParams((LayoutParams) p);
        return layoutParams3;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams p = layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = p;
        return ((p instanceof LayoutParams) && super.checkLayoutParams(p)) ? CHILDREN_DISALLOW_INTERCEPT : false;
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attrs = attributeSet;
        AttributeSet attributeSet2 = attrs;
        return new LayoutParams(getContext(), attrs);
    }

    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ArrayList<View> views = arrayList;
        int direction = i;
        int focusableMode = i2;
        ArrayList<View> arrayList2 = views;
        int i3 = direction;
        int i4 = focusableMode;
        if (getDescendantFocusability() != 393216) {
            int childCount = getChildCount();
            boolean isDrawerOpen = false;
            for (int i5 = 0; i5 < childCount; i5++) {
                View child = getChildAt(i5);
                if (!isDrawerView(child)) {
                    boolean add = this.mNonDrawerViews.add(child);
                } else if (isDrawerOpen(child)) {
                    isDrawerOpen = CHILDREN_DISALLOW_INTERCEPT;
                    child.addFocusables(views, direction, focusableMode);
                }
            }
            if (!isDrawerOpen) {
                int nonDrawerViewsCount = this.mNonDrawerViews.size();
                for (int i6 = 0; i6 < nonDrawerViewsCount; i6++) {
                    View view = (View) this.mNonDrawerViews.get(i6);
                    View child2 = view;
                    if (view.getVisibility() == 0) {
                        child2.addFocusables(views, direction, focusableMode);
                    }
                }
            }
            this.mNonDrawerViews.clear();
        }
    }

    private boolean hasVisibleDrawer() {
        return findVisibleDrawer() == null ? false : CHILDREN_DISALLOW_INTERCEPT;
    }

    /* access modifiers changed from: 0000 */
    public View findVisibleDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (isDrawerView(child) && isDrawerVisible(child)) {
                return child;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void cancelChildViewTouch() {
        if (!this.mChildrenCanceledTouch) {
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent cancelEvent = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                boolean dispatchTouchEvent = getChildAt(i).dispatchTouchEvent(cancelEvent);
            }
            cancelEvent.recycle();
            this.mChildrenCanceledTouch = CHILDREN_DISALLOW_INTERCEPT;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        if (keyCode != 4 || !hasVisibleDrawer()) {
            return super.onKeyDown(keyCode, event);
        }
        event.startTracking();
        return CHILDREN_DISALLOW_INTERCEPT;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        int i2 = keyCode;
        KeyEvent keyEvent2 = event;
        if (keyCode != 4) {
            return super.onKeyUp(keyCode, event);
        }
        View findVisibleDrawer = findVisibleDrawer();
        View visibleDrawer = findVisibleDrawer;
        if (findVisibleDrawer != null && getDrawerLockMode(visibleDrawer) == 0) {
            closeDrawers();
        }
        return visibleDrawer == null ? false : CHILDREN_DISALLOW_INTERCEPT;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable state = parcelable;
        Parcelable parcelable2 = state;
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            if (ss.openDrawerGravity != 0) {
                View findDrawerWithGravity = findDrawerWithGravity(ss.openDrawerGravity);
                View toOpen = findDrawerWithGravity;
                if (findDrawerWithGravity != null) {
                    openDrawer(toOpen);
                }
            }
            if (ss.lockModeLeft != 3) {
                setDrawerLockMode(ss.lockModeLeft, 3);
            }
            if (ss.lockModeRight != 3) {
                setDrawerLockMode(ss.lockModeRight, 5);
            }
            if (ss.lockModeStart != 3) {
                setDrawerLockMode(ss.lockModeStart, (int) GravityCompat.START);
            }
            if (ss.lockModeEnd != 3) {
                setDrawerLockMode(ss.lockModeEnd, (int) GravityCompat.END);
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0048, code lost:
        r6.openDrawerGravity = r11.gravity;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Parcelable onSaveInstanceState() {
        /*
            r17 = this;
            r0 = r17
            r1 = r0
            r2 = r0
            android.os.Parcelable r3 = super.onSaveInstanceState()
            r4 = r3
            android.support.v4.widget.DrawerLayout$SavedState r3 = new android.support.v4.widget.DrawerLayout$SavedState
            r5 = r4
            r3.<init>(r5)
            r6 = r3
            int r7 = r0.getChildCount()
            r8 = r7
            r9 = 0
        L_0x0016:
            if (r9 < r8) goto L_0x002b
        L_0x0018:
            int r15 = r0.mLockModeLeft
            r6.lockModeLeft = r15
            int r15 = r0.mLockModeRight
            r6.lockModeRight = r15
            int r15 = r0.mLockModeStart
            r6.lockModeStart = r15
            int r15 = r0.mLockModeEnd
            r6.lockModeEnd = r15
            r16 = r6
            return r16
        L_0x002b:
            android.view.View r3 = r0.getChildAt(r9)
            r10 = r3
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.support.v4.widget.DrawerLayout$LayoutParams r3 = (android.support.p000v4.widget.DrawerLayout.LayoutParams) r3
            r11 = r3
            int r7 = r3.openState
            r12 = 1
            if (r7 == r12) goto L_0x004d
            r7 = 0
        L_0x003d:
            r13 = r7
            int r7 = r11.openState
            r12 = 2
            if (r7 == r12) goto L_0x004f
            r7 = 0
        L_0x0044:
            r14 = r7
            r12 = 0
            if (r13 == r12) goto L_0x0051
        L_0x0048:
            int r15 = r11.gravity
            r6.openDrawerGravity = r15
            goto L_0x0018
        L_0x004d:
            r7 = 1
            goto L_0x003d
        L_0x004f:
            r7 = 1
            goto L_0x0044
        L_0x0051:
            r12 = 0
            if (r14 != r12) goto L_0x0048
            int r9 = r9 + 1
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.widget.DrawerLayout.onSaveInstanceState():android.os.Parcelable");
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        View child = view;
        int index = i;
        android.view.ViewGroup.LayoutParams params = layoutParams;
        View view2 = child;
        int i2 = index;
        android.view.ViewGroup.LayoutParams layoutParams2 = params;
        super.addView(child, index, params);
        View findOpenDrawer = findOpenDrawer();
        View view3 = findOpenDrawer;
        if (findOpenDrawer == null && !isDrawerView(child)) {
            ViewCompat.setImportantForAccessibility(child, 1);
        } else {
            ViewCompat.setImportantForAccessibility(child, 4);
        }
        if (!CAN_HIDE_DESCENDANTS) {
            ViewCompat.setAccessibilityDelegate(child, this.mChildAccessibilityDelegate);
        }
    }

    static boolean includeChildForAccessibility(View view) {
        View child = view;
        View view2 = child;
        return (ViewCompat.getImportantForAccessibility(child) == 4 || ViewCompat.getImportantForAccessibility(child) == 2) ? false : CHILDREN_DISALLOW_INTERCEPT;
    }
}
