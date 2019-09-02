package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.ViewDragHelper;
import android.support.p000v4.widget.ViewDragHelper.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class SwipeDismissBehavior<V extends View> extends Behavior<V> {
    private static final float DEFAULT_ALPHA_END_DISTANCE = 0.5f;
    private static final float DEFAULT_ALPHA_START_DISTANCE = 0.0f;
    private static final float DEFAULT_DRAG_DISMISS_THRESHOLD = 0.5f;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public static final int SWIPE_DIRECTION_ANY = 2;
    public static final int SWIPE_DIRECTION_END_TO_START = 1;
    public static final int SWIPE_DIRECTION_START_TO_END = 0;
    float mAlphaEndSwipeDistance = 0.5f;
    float mAlphaStartSwipeDistance = 0.0f;
    private final Callback mDragCallback = new Callback(this) {
        private static final int INVALID_POINTER_ID = -1;
        private int mActivePointerId = -1;
        private int mOriginalCapturedViewLeft;
        final /* synthetic */ SwipeDismissBehavior this$0;

        {
            SwipeDismissBehavior this$02 = r6;
            SwipeDismissBehavior swipeDismissBehavior = this$02;
            this.this$0 = this$02;
        }

        public boolean tryCaptureView(View view, int i) {
            View child = view;
            View view2 = child;
            int i2 = i;
            return this.mActivePointerId == -1 && this.this$0.canSwipeDismissView(child);
        }

        public void onViewCaptured(View view, int i) {
            View capturedChild = view;
            int activePointerId = i;
            View view2 = capturedChild;
            int i2 = activePointerId;
            this.mActivePointerId = activePointerId;
            this.mOriginalCapturedViewLeft = capturedChild.getLeft();
            ViewParent parent = capturedChild.getParent();
            ViewParent parent2 = parent;
            if (parent != null) {
                parent2.requestDisallowInterceptTouchEvent(true);
            }
        }

        public void onViewDragStateChanged(int i) {
            int state = i;
            int i2 = state;
            if (this.this$0.mListener != null) {
                this.this$0.mListener.onDragStateChanged(state);
            }
        }

        public void onViewReleased(View view, float f, float f2) {
            int targetLeft;
            View child = view;
            float xvel = f;
            View view2 = child;
            float f3 = xvel;
            float f4 = f2;
            this.mActivePointerId = -1;
            int childWidth = child.getWidth();
            boolean dismiss = false;
            if (!shouldDismiss(child, xvel)) {
                targetLeft = this.mOriginalCapturedViewLeft;
            } else {
                targetLeft = child.getLeft() >= this.mOriginalCapturedViewLeft ? this.mOriginalCapturedViewLeft + childWidth : this.mOriginalCapturedViewLeft - childWidth;
                dismiss = true;
            }
            if (this.this$0.mViewDragHelper.settleCapturedViewAt(targetLeft, child.getTop())) {
                SettleRunnable settleRunnable = new SettleRunnable(this.this$0, child, dismiss);
                ViewCompat.postOnAnimation(child, settleRunnable);
            } else if (dismiss && this.this$0.mListener != null) {
                this.this$0.mListener.onDismiss(child);
            }
        }

        private boolean shouldDismiss(View view, float f) {
            boolean z;
            View child = view;
            float xvel = f;
            View view2 = child;
            float f2 = xvel;
            if (xvel != 0.0f) {
                boolean isRtl = ViewCompat.getLayoutDirection(child) == 1;
                if (this.this$0.mSwipeDirection == 2) {
                    return true;
                }
                if (this.this$0.mSwipeDirection == 0) {
                    boolean z2 = !isRtl ? xvel > 0.0f : xvel < 0.0f;
                    return z2;
                } else if (this.this$0.mSwipeDirection != 1) {
                    return false;
                } else {
                    boolean z3 = !isRtl ? xvel < 0.0f : xvel > 0.0f;
                    return z3;
                }
            } else {
                if (Math.abs(child.getLeft() - this.mOriginalCapturedViewLeft) < Math.round(((float) child.getWidth()) * this.this$0.mDragDismissThreshold)) {
                    z = false;
                } else {
                    z = true;
                }
                return z;
            }
        }

        public int getViewHorizontalDragRange(View view) {
            View child = view;
            View view2 = child;
            return child.getWidth();
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            int min;
            int max;
            View child = view;
            int left = i;
            View view2 = child;
            int i3 = left;
            int i4 = i2;
            boolean isRtl = ViewCompat.getLayoutDirection(child) == 1;
            if (this.this$0.mSwipeDirection != 0) {
                if (this.this$0.mSwipeDirection != 1) {
                    min = this.mOriginalCapturedViewLeft - child.getWidth();
                    max = this.mOriginalCapturedViewLeft + child.getWidth();
                } else if (!isRtl) {
                    min = this.mOriginalCapturedViewLeft - child.getWidth();
                    max = this.mOriginalCapturedViewLeft;
                } else {
                    min = this.mOriginalCapturedViewLeft;
                    max = this.mOriginalCapturedViewLeft + child.getWidth();
                }
            } else if (!isRtl) {
                min = this.mOriginalCapturedViewLeft;
                max = this.mOriginalCapturedViewLeft + child.getWidth();
            } else {
                min = this.mOriginalCapturedViewLeft - child.getWidth();
                max = this.mOriginalCapturedViewLeft;
            }
            return SwipeDismissBehavior.clamp(min, left, max);
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            View child = view;
            View view2 = child;
            int i3 = i;
            int i4 = i2;
            return child.getTop();
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            View child = view;
            int left = i;
            View view2 = child;
            int i5 = left;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            float startAlphaDistance = ((float) this.mOriginalCapturedViewLeft) + (((float) child.getWidth()) * this.this$0.mAlphaStartSwipeDistance);
            float endAlphaDistance = ((float) this.mOriginalCapturedViewLeft) + (((float) child.getWidth()) * this.this$0.mAlphaEndSwipeDistance);
            if (((float) left) <= startAlphaDistance) {
                ViewCompat.setAlpha(child, 1.0f);
            } else if (((float) left) >= endAlphaDistance) {
                ViewCompat.setAlpha(child, 0.0f);
            } else {
                float fraction = SwipeDismissBehavior.fraction(startAlphaDistance, endAlphaDistance, (float) left);
                float f = fraction;
                ViewCompat.setAlpha(child, SwipeDismissBehavior.clamp(0.0f, 1.0f - fraction, 1.0f));
            }
        }
    };
    float mDragDismissThreshold = 0.5f;
    private boolean mInterceptingEvents;
    OnDismissListener mListener;
    private float mSensitivity = 0.0f;
    private boolean mSensitivitySet;
    int mSwipeDirection = 2;
    ViewDragHelper mViewDragHelper;

    public interface OnDismissListener {
        void onDismiss(View view);

        void onDragStateChanged(int i);
    }

    private class SettleRunnable implements Runnable {
        private final boolean mDismiss;
        private final View mView;
        final /* synthetic */ SwipeDismissBehavior this$0;

        SettleRunnable(SwipeDismissBehavior swipeDismissBehavior, View view, boolean z) {
            View view2 = view;
            View view3 = view2;
            boolean dismiss = z;
            this.this$0 = swipeDismissBehavior;
            this.mView = view2;
            this.mDismiss = dismiss;
        }

        public void run() {
            if (this.this$0.mViewDragHelper != null && this.this$0.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
            } else if (this.mDismiss && this.this$0.mListener != null) {
                this.this$0.mListener.onDismiss(this.mView);
            }
        }
    }

    public SwipeDismissBehavior() {
    }

    public void setListener(OnDismissListener onDismissListener) {
        OnDismissListener listener = onDismissListener;
        OnDismissListener onDismissListener2 = listener;
        this.mListener = listener;
    }

    public void setSwipeDirection(int i) {
        int direction = i;
        int i2 = direction;
        this.mSwipeDirection = direction;
    }

    public void setDragDismissDistance(float f) {
        float distance = f;
        float f2 = distance;
        this.mDragDismissThreshold = clamp(0.0f, distance, 1.0f);
    }

    public void setStartAlphaSwipeDistance(float f) {
        float fraction = f;
        float f2 = fraction;
        this.mAlphaStartSwipeDistance = clamp(0.0f, fraction, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float f) {
        float fraction = f;
        float f2 = fraction;
        this.mAlphaEndSwipeDistance = clamp(0.0f, fraction, 1.0f);
    }

    public void setSensitivity(float f) {
        float sensitivity = f;
        float f2 = sensitivity;
        this.mSensitivity = sensitivity;
        this.mSensitivitySet = true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        CoordinatorLayout parent = coordinatorLayout;
        V child = v;
        MotionEvent event = motionEvent;
        CoordinatorLayout coordinatorLayout2 = parent;
        V v2 = child;
        MotionEvent motionEvent2 = event;
        boolean dispatchEventToHelper = this.mInterceptingEvents;
        switch (MotionEventCompat.getActionMasked(event)) {
            case 0:
                this.mInterceptingEvents = parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY());
                dispatchEventToHelper = this.mInterceptingEvents;
                break;
            case 1:
            case 3:
                this.mInterceptingEvents = false;
                break;
        }
        if (!dispatchEventToHelper) {
            return false;
        }
        ensureViewDragHelper(parent);
        return this.mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
        V v2 = v;
        MotionEvent motionEvent2 = event;
        if (this.mViewDragHelper == null) {
            return false;
        }
        this.mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public boolean canSwipeDismissView(@NonNull View view) {
        View view2 = view;
        return true;
    }

    private void ensureViewDragHelper(ViewGroup viewGroup) {
        ViewDragHelper create;
        ViewGroup parent = viewGroup;
        ViewGroup viewGroup2 = parent;
        if (this.mViewDragHelper == null) {
            if (!this.mSensitivitySet) {
                create = ViewDragHelper.create(parent, this.mDragCallback);
            } else {
                create = ViewDragHelper.create(parent, this.mSensitivity, this.mDragCallback);
            }
            this.mViewDragHelper = create;
        }
    }

    static float clamp(float f, float f2, float f3) {
        float min = f;
        float value = f2;
        float max = f3;
        float f4 = min;
        float f5 = value;
        float f6 = max;
        return Math.min(Math.max(min, value), max);
    }

    static int clamp(int i, int i2, int i3) {
        int min = i;
        int value = i2;
        int max = i3;
        int i4 = min;
        int i5 = value;
        int i6 = max;
        return Math.min(Math.max(min, value), max);
    }

    public int getDragState() {
        return this.mViewDragHelper == null ? 0 : this.mViewDragHelper.getViewDragState();
    }

    static float fraction(float f, float f2, float f3) {
        float startValue = f;
        float endValue = f2;
        float value = f3;
        float f4 = startValue;
        float f5 = endValue;
        float f6 = value;
        return (value - startValue) / (endValue - startValue);
    }
}
