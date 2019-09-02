package android.support.p000v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/* renamed from: android.support.v4.view.GestureDetectorCompat */
public final class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    /* renamed from: android.support.v4.view.GestureDetectorCompat$GestureDetectorCompatImpl */
    interface GestureDetectorCompatImpl {
        boolean isLongpressEnabled();

        boolean onTouchEvent(MotionEvent motionEvent);

        void setIsLongpressEnabled(boolean z);

        void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener);
    }

    /* renamed from: android.support.v4.view.GestureDetectorCompat$GestureDetectorCompatImplBase */
    static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
        private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
        private static final int LONG_PRESS = 2;
        private static final int SHOW_PRESS = 1;
        private static final int TAP = 3;
        private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
        private boolean mAlwaysInBiggerTapRegion;
        private boolean mAlwaysInTapRegion;
        MotionEvent mCurrentDownEvent;
        boolean mDeferConfirmSingleTap;
        OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        final OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        /* renamed from: android.support.v4.view.GestureDetectorCompat$GestureDetectorCompatImplBase$GestureHandler */
        private class GestureHandler extends Handler {
            final /* synthetic */ GestureDetectorCompatImplBase this$0;

            GestureHandler(GestureDetectorCompatImplBase gestureDetectorCompatImplBase) {
                this.this$0 = gestureDetectorCompatImplBase;
            }

            GestureHandler(GestureDetectorCompatImplBase gestureDetectorCompatImplBase, Handler handler) {
                Handler handler2 = handler;
                Handler handler3 = handler2;
                this.this$0 = gestureDetectorCompatImplBase;
                super(handler2.getLooper());
            }

            public void handleMessage(Message message) {
                Message msg = message;
                Message message2 = msg;
                switch (msg.what) {
                    case 1:
                        this.this$0.mListener.onShowPress(this.this$0.mCurrentDownEvent);
                        return;
                    case 2:
                        this.this$0.dispatchLongPress();
                        return;
                    case 3:
                        if (this.this$0.mDoubleTapListener != null) {
                            if (this.this$0.mStillDown) {
                                this.this$0.mDeferConfirmSingleTap = true;
                                return;
                            } else {
                                boolean onSingleTapConfirmed = this.this$0.mDoubleTapListener.onSingleTapConfirmed(this.this$0.mCurrentDownEvent);
                                return;
                            }
                        } else {
                            return;
                        }
                    default:
                        throw new RuntimeException("Unknown message " + msg);
                }
            }
        }

        public GestureDetectorCompatImplBase(Context context, OnGestureListener onGestureListener, Handler handler) {
            Context context2 = context;
            OnGestureListener listener = onGestureListener;
            Handler handler2 = handler;
            Context context3 = context2;
            OnGestureListener onGestureListener2 = listener;
            Handler handler3 = handler2;
            if (handler2 == null) {
                this.mHandler = new GestureHandler(this);
            } else {
                this.mHandler = new GestureHandler(this, handler2);
            }
            this.mListener = listener;
            if (listener instanceof OnDoubleTapListener) {
                setOnDoubleTapListener((OnDoubleTapListener) listener);
            }
            init(context2);
        }

        private void init(Context context) {
            Context context2 = context;
            Context context3 = context2;
            if (context2 == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.mListener != null) {
                this.mIsLongpressEnabled = true;
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context2);
                ViewConfiguration configuration = viewConfiguration;
                int touchSlop = viewConfiguration.getScaledTouchSlop();
                int scaledDoubleTapSlop = configuration.getScaledDoubleTapSlop();
                int i = scaledDoubleTapSlop;
                this.mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
                this.mTouchSlopSquare = touchSlop * touchSlop;
                this.mDoubleTapSlopSquare = scaledDoubleTapSlop * scaledDoubleTapSlop;
            } else {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            }
        }

        public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
            OnDoubleTapListener onDoubleTapListener2 = onDoubleTapListener;
            OnDoubleTapListener onDoubleTapListener3 = onDoubleTapListener2;
            this.mDoubleTapListener = onDoubleTapListener2;
        }

        public void setIsLongpressEnabled(boolean z) {
            this.mIsLongpressEnabled = z;
        }

        public boolean isLongpressEnabled() {
            return this.mIsLongpressEnabled;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            MotionEvent ev = motionEvent;
            MotionEvent motionEvent2 = ev;
            int action = ev.getAction();
            int action2 = action;
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(ev);
            boolean pointerUp = (action & 255) == 6;
            int skipIndex = !pointerUp ? -1 : MotionEventCompat.getActionIndex(ev);
            float sumX = 0.0f;
            float sumY = 0.0f;
            int count = ev.getPointerCount();
            for (int i = 0; i < count; i++) {
                if (skipIndex != i) {
                    sumX += ev.getX(i);
                    sumY += ev.getY(i);
                }
            }
            int div = !pointerUp ? count : count - 1;
            float focusX = sumX / ((float) div);
            float f = sumY / ((float) div);
            float focusY = f;
            boolean handled = false;
            switch (action2 & 255) {
                case 0:
                    if (this.mDoubleTapListener != null) {
                        boolean hasMessages = this.mHandler.hasMessages(3);
                        boolean hadTapMessage = hasMessages;
                        if (hasMessages) {
                            this.mHandler.removeMessages(3);
                        }
                        if (!(this.mCurrentDownEvent == null || this.mPreviousUpEvent == null || !hadTapMessage)) {
                            if (isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, ev)) {
                                this.mIsDoubleTapping = true;
                                boolean onDoubleTap = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent);
                                boolean handled2 = onDoubleTap;
                                handled = onDoubleTap | this.mDoubleTapListener.onDoubleTapEvent(ev);
                            }
                        }
                        boolean sendEmptyMessageDelayed = this.mHandler.sendEmptyMessageDelayed(3, (long) DOUBLE_TAP_TIMEOUT);
                    }
                    this.mLastFocusX = focusX;
                    this.mDownFocusX = focusX;
                    this.mLastFocusY = f;
                    this.mDownFocusY = f;
                    if (this.mCurrentDownEvent != null) {
                        this.mCurrentDownEvent.recycle();
                    }
                    this.mCurrentDownEvent = MotionEvent.obtain(ev);
                    this.mAlwaysInTapRegion = true;
                    this.mAlwaysInBiggerTapRegion = true;
                    this.mStillDown = true;
                    this.mInLongPress = false;
                    this.mDeferConfirmSingleTap = false;
                    if (this.mIsLongpressEnabled) {
                        this.mHandler.removeMessages(2);
                        boolean sendEmptyMessageAtTime = this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + ((long) TAP_TIMEOUT) + ((long) LONGPRESS_TIMEOUT));
                    }
                    boolean sendEmptyMessageAtTime2 = this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + ((long) TAP_TIMEOUT));
                    handled |= this.mListener.onDown(ev);
                    break;
                case 1:
                    this.mStillDown = false;
                    MotionEvent currentUpEvent = MotionEvent.obtain(ev);
                    if (this.mIsDoubleTapping) {
                        handled = false | this.mDoubleTapListener.onDoubleTapEvent(ev);
                    } else if (this.mInLongPress) {
                        this.mHandler.removeMessages(3);
                        this.mInLongPress = false;
                    } else if (!this.mAlwaysInTapRegion) {
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        int pointerId = ev.getPointerId(0);
                        int i2 = pointerId;
                        velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                        float velocityY = VelocityTrackerCompat.getYVelocity(velocityTracker, pointerId);
                        float velocityX = VelocityTrackerCompat.getXVelocity(velocityTracker, pointerId);
                        if ((Math.abs(velocityY) > ((float) this.mMinimumFlingVelocity)) || Math.abs(velocityX) > ((float) this.mMinimumFlingVelocity)) {
                            handled = this.mListener.onFling(this.mCurrentDownEvent, ev, velocityX, velocityY);
                        }
                    } else {
                        handled = this.mListener.onSingleTapUp(ev);
                        if (this.mDeferConfirmSingleTap && this.mDoubleTapListener != null) {
                            boolean onSingleTapConfirmed = this.mDoubleTapListener.onSingleTapConfirmed(ev);
                        }
                    }
                    if (this.mPreviousUpEvent != null) {
                        this.mPreviousUpEvent.recycle();
                    }
                    this.mPreviousUpEvent = currentUpEvent;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                    this.mIsDoubleTapping = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                    break;
                case 2:
                    if (!this.mInLongPress) {
                        float scrollX = this.mLastFocusX - focusX;
                        float scrollY = this.mLastFocusY - focusY;
                        if (!this.mIsDoubleTapping) {
                            if (this.mAlwaysInTapRegion) {
                                int deltaX = (int) (focusX - this.mDownFocusX);
                                int deltaY = (int) (focusY - this.mDownFocusY);
                                int i3 = (deltaX * deltaX) + (deltaY * deltaY);
                                int distance = i3;
                                if (i3 > this.mTouchSlopSquare) {
                                    handled = this.mListener.onScroll(this.mCurrentDownEvent, ev, scrollX, scrollY);
                                    this.mLastFocusX = focusX;
                                    this.mLastFocusY = focusY;
                                    this.mAlwaysInTapRegion = false;
                                    this.mHandler.removeMessages(3);
                                    this.mHandler.removeMessages(1);
                                    this.mHandler.removeMessages(2);
                                }
                                if (distance > this.mTouchSlopSquare) {
                                    this.mAlwaysInBiggerTapRegion = false;
                                    break;
                                }
                            } else {
                                if ((Math.abs(scrollX) >= 1.0f) || Math.abs(scrollY) >= 1.0f) {
                                    handled = this.mListener.onScroll(this.mCurrentDownEvent, ev, scrollX, scrollY);
                                    this.mLastFocusX = focusX;
                                    this.mLastFocusY = focusY;
                                    break;
                                }
                            }
                        } else {
                            handled = false | this.mDoubleTapListener.onDoubleTapEvent(ev);
                            break;
                        }
                    }
                    break;
                case 3:
                    cancel();
                    break;
                case 5:
                    this.mLastFocusX = focusX;
                    this.mDownFocusX = focusX;
                    this.mLastFocusY = f;
                    this.mDownFocusY = f;
                    cancelTaps();
                    break;
                case 6:
                    this.mLastFocusX = focusX;
                    this.mDownFocusX = focusX;
                    this.mLastFocusY = f;
                    this.mDownFocusY = f;
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                    int actionIndex = MotionEventCompat.getActionIndex(ev);
                    int upIndex = actionIndex;
                    int pointerId2 = ev.getPointerId(actionIndex);
                    int i4 = pointerId2;
                    float x1 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, pointerId2);
                    float y1 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, pointerId2);
                    int i5 = 0;
                    while (true) {
                        if (i5 >= count) {
                            break;
                        } else {
                            if (i5 != upIndex) {
                                int pointerId3 = ev.getPointerId(i5);
                                int i6 = pointerId3;
                                float xVelocity = (x1 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, pointerId3)) + (y1 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, pointerId3));
                                float f2 = xVelocity;
                                if (xVelocity < 0.0f) {
                                    this.mVelocityTracker.clear();
                                    break;
                                }
                            }
                            i5++;
                        }
                    }
            }
            return handled;
        }

        private void cancel() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mIsDoubleTapping = false;
            this.mStillDown = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void cancelTaps() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private boolean isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            MotionEvent firstDown = motionEvent;
            MotionEvent firstUp = motionEvent2;
            MotionEvent secondDown = motionEvent3;
            MotionEvent motionEvent4 = firstDown;
            MotionEvent motionEvent5 = firstUp;
            MotionEvent motionEvent6 = secondDown;
            if (!this.mAlwaysInBiggerTapRegion) {
                return false;
            }
            if (!(secondDown.getEventTime() - firstUp.getEventTime() <= ((long) DOUBLE_TAP_TIMEOUT))) {
                return false;
            }
            int deltaX = ((int) firstDown.getX()) - ((int) secondDown.getX());
            int deltaY = ((int) firstDown.getY()) - ((int) secondDown.getY());
            return (deltaX * deltaX) + (deltaY * deltaY) < this.mDoubleTapSlopSquare;
        }

        /* access modifiers changed from: 0000 */
        public void dispatchLongPress() {
            this.mHandler.removeMessages(3);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }
    }

    /* renamed from: android.support.v4.view.GestureDetectorCompat$GestureDetectorCompatImplJellybeanMr2 */
    static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        public GestureDetectorCompatImplJellybeanMr2(Context context, OnGestureListener onGestureListener, Handler handler) {
            Context context2 = context;
            OnGestureListener listener = onGestureListener;
            Handler handler2 = handler;
            Context context3 = context2;
            OnGestureListener onGestureListener2 = listener;
            Handler handler3 = handler2;
            this.mDetector = new GestureDetector(context2, listener, handler2);
        }

        public boolean isLongpressEnabled() {
            return this.mDetector.isLongpressEnabled();
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            MotionEvent ev = motionEvent;
            MotionEvent motionEvent2 = ev;
            return this.mDetector.onTouchEvent(ev);
        }

        public void setIsLongpressEnabled(boolean z) {
            this.mDetector.setIsLongpressEnabled(z);
        }

        public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
            OnDoubleTapListener listener = onDoubleTapListener;
            OnDoubleTapListener onDoubleTapListener2 = listener;
            this.mDetector.setOnDoubleTapListener(listener);
        }
    }

    public GestureDetectorCompat(Context context, OnGestureListener onGestureListener) {
        Context context2 = context;
        OnGestureListener listener = onGestureListener;
        Context context3 = context2;
        OnGestureListener onGestureListener2 = listener;
        this(context2, listener, null);
    }

    public GestureDetectorCompat(Context context, OnGestureListener onGestureListener, Handler handler) {
        Context context2 = context;
        OnGestureListener listener = onGestureListener;
        Handler handler2 = handler;
        Context context3 = context2;
        OnGestureListener onGestureListener2 = listener;
        Handler handler3 = handler2;
        if (VERSION.SDK_INT <= 17) {
            this.mImpl = new GestureDetectorCompatImplBase(context2, listener, handler2);
        } else {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context2, listener, handler2);
        }
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return this.mImpl.onTouchEvent(event);
    }

    public void setIsLongpressEnabled(boolean z) {
        this.mImpl.setIsLongpressEnabled(z);
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        OnDoubleTapListener listener = onDoubleTapListener;
        OnDoubleTapListener onDoubleTapListener2 = listener;
        this.mImpl.setOnDoubleTapListener(listener);
    }
}
