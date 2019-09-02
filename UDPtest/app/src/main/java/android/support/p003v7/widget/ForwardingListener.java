package android.support.p003v7.widget;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.view.menu.ShowableListMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ForwardingListener */
public abstract class ForwardingListener implements OnTouchListener {
    private int mActivePointerId;
    private Runnable mDisallowIntercept;
    private boolean mForwarding;
    private final int mLongPressTimeout;
    private final float mScaledTouchSlop;
    final View mSrc;
    private final int mTapTimeout;
    private final int[] mTmpLocation = new int[2];
    private Runnable mTriggerLongPress;

    /* renamed from: android.support.v7.widget.ForwardingListener$DisallowIntercept */
    private class DisallowIntercept implements Runnable {
        final /* synthetic */ ForwardingListener this$0;

        DisallowIntercept(ForwardingListener forwardingListener) {
            this.this$0 = forwardingListener;
        }

        public void run() {
            ViewParent parent = this.this$0.mSrc.getParent();
            ViewParent parent2 = parent;
            if (parent != null) {
                parent2.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    /* renamed from: android.support.v7.widget.ForwardingListener$TriggerLongPress */
    private class TriggerLongPress implements Runnable {
        final /* synthetic */ ForwardingListener this$0;

        TriggerLongPress(ForwardingListener forwardingListener) {
            this.this$0 = forwardingListener;
        }

        public void run() {
            this.this$0.onLongPress();
        }
    }

    public abstract ShowableListMenu getPopup();

    static /* synthetic */ void access$000(ForwardingListener forwardingListener) {
        ForwardingListener x0 = forwardingListener;
        ForwardingListener forwardingListener2 = x0;
        x0.onDetachedFromWindow();
    }

    public ForwardingListener(View view) {
        View src = view;
        View view2 = src;
        this.mSrc = src;
        src.setLongClickable(true);
        if (VERSION.SDK_INT < 12) {
            addDetachListenerBase(src);
        } else {
            addDetachListenerApi12(src);
        }
        this.mScaledTouchSlop = (float) ViewConfiguration.get(src.getContext()).getScaledTouchSlop();
        this.mTapTimeout = ViewConfiguration.getTapTimeout();
        this.mLongPressTimeout = (this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
    }

    @TargetApi(12)
    @RequiresApi(12)
    private void addDetachListenerApi12(View view) {
        View src = view;
        View view2 = src;
        src.addOnAttachStateChangeListener(new OnAttachStateChangeListener(this) {
            final /* synthetic */ ForwardingListener this$0;

            {
                ForwardingListener this$02 = r5;
                ForwardingListener forwardingListener = this$02;
                this.this$0 = this$02;
            }

            public void onViewAttachedToWindow(View view) {
                View view2 = view;
            }

            public void onViewDetachedFromWindow(View view) {
                View view2 = view;
                ForwardingListener.access$000(this.this$0);
            }
        });
    }

    private void addDetachListenerBase(View view) {
        View src = view;
        View view2 = src;
        src.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            boolean mIsAttached = ViewCompat.isAttachedToWindow(this.this$0.mSrc);
            final /* synthetic */ ForwardingListener this$0;

            {
                ForwardingListener this$02 = r10;
                ForwardingListener forwardingListener = this$02;
                this.this$0 = this$02;
            }

            public void onGlobalLayout() {
                boolean z = this.mIsAttached;
                boolean z2 = z;
                this.mIsAttached = ViewCompat.isAttachedToWindow(this.this$0.mSrc);
                if (z && !this.mIsAttached) {
                    ForwardingListener.access$000(this.this$0);
                }
            }
        });
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean forwarding;
        boolean z;
        MotionEvent event = motionEvent;
        View view2 = view;
        MotionEvent motionEvent2 = event;
        boolean z2 = this.mForwarding;
        boolean wasForwarding = z2;
        if (!z2) {
            forwarding = onTouchObserved(event) && onForwardingStarted();
            if (forwarding) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent e = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                boolean onTouchEvent = this.mSrc.onTouchEvent(e);
                e.recycle();
            }
        } else {
            forwarding = onTouchForwarded(event) || !onForwardingStopped();
        }
        this.mForwarding = forwarding;
        if (!forwarding && !wasForwarding) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    private void onDetachedFromWindow() {
        this.mForwarding = false;
        this.mActivePointerId = -1;
        if (this.mDisallowIntercept != null) {
            boolean removeCallbacks = this.mSrc.removeCallbacks(this.mDisallowIntercept);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onForwardingStarted() {
        ShowableListMenu popup = getPopup();
        ShowableListMenu popup2 = popup;
        if (popup != null && !popup2.isShowing()) {
            popup2.show();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onForwardingStopped() {
        ShowableListMenu popup = getPopup();
        ShowableListMenu popup2 = popup;
        if (popup != null && popup2.isShowing()) {
            popup2.dismiss();
        }
        return true;
    }

    private boolean onTouchObserved(MotionEvent motionEvent) {
        MotionEvent srcEvent = motionEvent;
        MotionEvent motionEvent2 = srcEvent;
        View view = this.mSrc;
        View src = view;
        if (!view.isEnabled()) {
            return false;
        }
        int actionMasked = MotionEventCompat.getActionMasked(srcEvent);
        int i = actionMasked;
        switch (actionMasked) {
            case 0:
                this.mActivePointerId = srcEvent.getPointerId(0);
                if (this.mDisallowIntercept == null) {
                    this.mDisallowIntercept = new DisallowIntercept(this);
                }
                boolean postDelayed = src.postDelayed(this.mDisallowIntercept, (long) this.mTapTimeout);
                if (this.mTriggerLongPress == null) {
                    TriggerLongPress triggerLongPress = new TriggerLongPress(this);
                    this.mTriggerLongPress = triggerLongPress;
                }
                boolean postDelayed2 = src.postDelayed(this.mTriggerLongPress, (long) this.mLongPressTimeout);
                break;
            case 1:
            case 3:
                clearCallbacks();
                break;
            case 2:
                int findPointerIndex = srcEvent.findPointerIndex(this.mActivePointerId);
                int activePointerIndex = findPointerIndex;
                if (findPointerIndex >= 0) {
                    float x = srcEvent.getX(activePointerIndex);
                    float y = srcEvent.getY(activePointerIndex);
                    float f = y;
                    if (!pointInView(src, x, y, this.mScaledTouchSlop)) {
                        clearCallbacks();
                        src.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private void clearCallbacks() {
        if (this.mTriggerLongPress != null) {
            boolean removeCallbacks = this.mSrc.removeCallbacks(this.mTriggerLongPress);
        }
        if (this.mDisallowIntercept != null) {
            boolean removeCallbacks2 = this.mSrc.removeCallbacks(this.mDisallowIntercept);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onLongPress() {
        clearCallbacks();
        View view = this.mSrc;
        View src = view;
        if (view.isEnabled() && !src.isLongClickable() && onForwardingStarted()) {
            src.getParent().requestDisallowInterceptTouchEvent(true);
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent e = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            boolean onTouchEvent = src.onTouchEvent(e);
            e.recycle();
            this.mForwarding = true;
        }
    }

    private boolean onTouchForwarded(MotionEvent motionEvent) {
        MotionEvent srcEvent = motionEvent;
        MotionEvent motionEvent2 = srcEvent;
        View src = this.mSrc;
        ShowableListMenu popup = getPopup();
        ShowableListMenu popup2 = popup;
        if (popup == null || !popup2.isShowing()) {
            return false;
        }
        DropDownListView dropDownListView = (DropDownListView) popup2.getListView();
        DropDownListView dst = dropDownListView;
        if (dropDownListView == null || !dst.isShown()) {
            return false;
        }
        MotionEvent dstEvent = MotionEvent.obtainNoHistory(srcEvent);
        boolean globalMotionEvent = toGlobalMotionEvent(src, dstEvent);
        boolean localMotionEvent = toLocalMotionEvent(dst, dstEvent);
        boolean handled = dst.onForwardedEvent(dstEvent, this.mActivePointerId);
        dstEvent.recycle();
        int actionMasked = MotionEventCompat.getActionMasked(srcEvent);
        return handled && (actionMasked != 1 && actionMasked != 3);
    }

    private static boolean pointInView(View view, float f, float f2, float f3) {
        View view2 = view;
        float localX = f;
        float localY = f2;
        float slop = f3;
        View view3 = view2;
        float f4 = localX;
        float f5 = localY;
        float f6 = slop;
        return localX >= (-slop) && localY >= (-slop) && localX < ((float) (view2.getRight() - view2.getLeft())) + slop && localY < ((float) (view2.getBottom() - view2.getTop())) + slop;
    }

    private boolean toLocalMotionEvent(View view, MotionEvent motionEvent) {
        View view2 = view;
        MotionEvent event = motionEvent;
        View view3 = view2;
        MotionEvent motionEvent2 = event;
        int[] loc = this.mTmpLocation;
        view2.getLocationOnScreen(loc);
        event.offsetLocation((float) (-loc[0]), (float) (-loc[1]));
        return true;
    }

    private boolean toGlobalMotionEvent(View view, MotionEvent motionEvent) {
        View view2 = view;
        MotionEvent event = motionEvent;
        View view3 = view2;
        MotionEvent motionEvent2 = event;
        int[] loc = this.mTmpLocation;
        view2.getLocationOnScreen(loc);
        event.offsetLocation((float) loc[0], (float) loc[1]);
        return true;
    }
}
