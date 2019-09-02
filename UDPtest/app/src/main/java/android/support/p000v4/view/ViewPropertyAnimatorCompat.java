package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat */
public final class ViewPropertyAnimatorCompat {
    static final ViewPropertyAnimatorCompatImpl IMPL;
    static final int LISTENER_TAG_ID = 2113929216;
    private static final String TAG = "ViewAnimatorCompat";
    Runnable mEndAction = null;
    int mOldLayerType = -1;
    Runnable mStartAction = null;
    private WeakReference<View> mView;

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$BaseViewPropertyAnimatorCompatImpl */
    static class BaseViewPropertyAnimatorCompatImpl implements ViewPropertyAnimatorCompatImpl {
        WeakHashMap<View, Runnable> mStarterMap = null;

        /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$BaseViewPropertyAnimatorCompatImpl$Starter */
        class Starter implements Runnable {
            WeakReference<View> mViewRef;
            ViewPropertyAnimatorCompat mVpa;
            final /* synthetic */ BaseViewPropertyAnimatorCompatImpl this$0;

            Starter(BaseViewPropertyAnimatorCompatImpl baseViewPropertyAnimatorCompatImpl, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
                BaseViewPropertyAnimatorCompatImpl this$02 = baseViewPropertyAnimatorCompatImpl;
                ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
                View view2 = view;
                BaseViewPropertyAnimatorCompatImpl baseViewPropertyAnimatorCompatImpl2 = this$02;
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
                View view3 = view2;
                this.this$0 = this$02;
                this.mViewRef = new WeakReference<>(view2);
                this.mVpa = vpa;
            }

            public void run() {
                View view = (View) this.mViewRef.get();
                View view2 = view;
                if (view != null) {
                    this.this$0.startAnimation(this.mVpa, view2);
                }
            }
        }

        BaseViewPropertyAnimatorCompatImpl() {
        }

        public void setDuration(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, long j) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            long j2 = j;
        }

        public void alpha(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void translationX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void translationY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void withEndAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            Runnable runnable2 = runnable;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            Runnable runnable3 = runnable2;
            vpa.mEndAction = runnable2;
            postStartMessage(vpa, view2);
        }

        public long getDuration(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            return 0;
        }

        public void setInterpolator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Interpolator interpolator) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            Interpolator interpolator2 = interpolator;
        }

        public Interpolator getInterpolator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            return null;
        }

        public void setStartDelay(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, long j) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            long j2 = j;
        }

        public long getStartDelay(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            return 0;
        }

        public void alphaBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void rotation(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void rotationBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void rotationX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void rotationXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void rotationY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void rotationYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void scaleX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void scaleXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void scaleY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void scaleYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void cancel(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            postStartMessage(vpa, view2);
        }

        /* renamed from: x */
        public void mo4008x(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void xBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        /* renamed from: y */
        public void mo4010y(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void yBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        /* renamed from: z */
        public void mo4012z(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            float f2 = f;
        }

        public void zBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            float f2 = f;
        }

        public void translationXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void translationYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            float f2 = f;
            postStartMessage(vpa, view2);
        }

        public void translationZ(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            float f2 = f;
        }

        public void translationZBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            float f2 = f;
        }

        public void start(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            removeStartMessage(view2);
            startAnimation(vpa, view2);
        }

        public void withLayer(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
        }

        public void withStartAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            Runnable runnable2 = runnable;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            Runnable runnable3 = runnable2;
            vpa.mStartAction = runnable2;
            postStartMessage(vpa, view2);
        }

        public void setListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
            View view2 = view;
            ViewPropertyAnimatorListener listener = viewPropertyAnimatorListener;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener2 = listener;
            view2.setTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID, listener);
        }

        public void setUpdateListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener2 = viewPropertyAnimatorUpdateListener;
        }

        /* access modifiers changed from: 0000 */
        public void startAnimation(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            Object listenerTag = view2.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
            ViewPropertyAnimatorListener listener = null;
            if (listenerTag instanceof ViewPropertyAnimatorListener) {
                listener = (ViewPropertyAnimatorListener) listenerTag;
            }
            Runnable startAction = vpa.mStartAction;
            Runnable endAction = vpa.mEndAction;
            vpa.mStartAction = null;
            vpa.mEndAction = null;
            if (startAction != null) {
                startAction.run();
            }
            if (listener != null) {
                listener.onAnimationStart(view2);
                listener.onAnimationEnd(view2);
            }
            if (endAction != null) {
                endAction.run();
            }
            if (this.mStarterMap != null) {
                Object remove = this.mStarterMap.remove(view2);
            }
        }

        private void removeStartMessage(View view) {
            View view2 = view;
            View view3 = view2;
            if (this.mStarterMap != null) {
                Runnable runnable = (Runnable) this.mStarterMap.get(view2);
                Runnable starter = runnable;
                if (runnable != null) {
                    boolean removeCallbacks = view2.removeCallbacks(starter);
                }
            }
        }

        private void postStartMessage(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            Runnable starter = null;
            if (this.mStarterMap != null) {
                starter = (Runnable) this.mStarterMap.get(view2);
            }
            if (starter == null) {
                starter = new Starter(this, vpa, view2);
                if (this.mStarterMap == null) {
                    this.mStarterMap = new WeakHashMap<>();
                }
                Object put = this.mStarterMap.put(view2, starter);
            }
            boolean removeCallbacks = view2.removeCallbacks(starter);
            boolean post = view2.post(starter);
        }
    }

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$ICSViewPropertyAnimatorCompatImpl */
    static class ICSViewPropertyAnimatorCompatImpl extends BaseViewPropertyAnimatorCompatImpl {
        WeakHashMap<View, Integer> mLayerMap = null;

        /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$ICSViewPropertyAnimatorCompatImpl$MyVpaListener */
        static class MyVpaListener implements ViewPropertyAnimatorListener {
            boolean mAnimEndCalled;
            ViewPropertyAnimatorCompat mVpa;

            MyVpaListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
                ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
                this.mVpa = vpa;
            }

            public void onAnimationStart(View view) {
                View view2 = view;
                View view3 = view2;
                this.mAnimEndCalled = false;
                if (this.mVpa.mOldLayerType >= 0) {
                    ViewCompat.setLayerType(view2, 2, null);
                }
                if (this.mVpa.mStartAction != null) {
                    Runnable startAction = this.mVpa.mStartAction;
                    this.mVpa.mStartAction = null;
                    startAction.run();
                }
                Object listenerTag = view2.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                ViewPropertyAnimatorListener listener = null;
                if (listenerTag instanceof ViewPropertyAnimatorListener) {
                    listener = (ViewPropertyAnimatorListener) listenerTag;
                }
                if (listener != null) {
                    listener.onAnimationStart(view2);
                }
            }

            public void onAnimationEnd(View view) {
                View view2 = view;
                View view3 = view2;
                if (this.mVpa.mOldLayerType >= 0) {
                    ViewCompat.setLayerType(view2, this.mVpa.mOldLayerType, null);
                    this.mVpa.mOldLayerType = -1;
                }
                if (VERSION.SDK_INT >= 16 || !this.mAnimEndCalled) {
                    if (this.mVpa.mEndAction != null) {
                        Runnable endAction = this.mVpa.mEndAction;
                        this.mVpa.mEndAction = null;
                        endAction.run();
                    }
                    Object listenerTag = view2.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                    ViewPropertyAnimatorListener listener = null;
                    if (listenerTag instanceof ViewPropertyAnimatorListener) {
                        listener = (ViewPropertyAnimatorListener) listenerTag;
                    }
                    if (listener != null) {
                        listener.onAnimationEnd(view2);
                    }
                    this.mAnimEndCalled = true;
                }
            }

            public void onAnimationCancel(View view) {
                View view2 = view;
                View view3 = view2;
                Object listenerTag = view2.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                ViewPropertyAnimatorListener listener = null;
                if (listenerTag instanceof ViewPropertyAnimatorListener) {
                    listener = (ViewPropertyAnimatorListener) listenerTag;
                }
                if (listener != null) {
                    listener.onAnimationCancel(view2);
                }
            }
        }

        ICSViewPropertyAnimatorCompatImpl() {
        }

        public void setDuration(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, long j) {
            View view2 = view;
            long value = j;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            long j2 = value;
            ViewPropertyAnimatorCompatICS.setDuration(view2, value);
        }

        public void alpha(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.alpha(view2, value);
        }

        public void translationX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.translationX(view2, value);
        }

        public void translationY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.translationY(view2, value);
        }

        public long getDuration(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            return ViewPropertyAnimatorCompatICS.getDuration(view2);
        }

        public void setInterpolator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Interpolator interpolator) {
            View view2 = view;
            Interpolator value = interpolator;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            Interpolator interpolator2 = value;
            ViewPropertyAnimatorCompatICS.setInterpolator(view2, value);
        }

        public void setStartDelay(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, long j) {
            View view2 = view;
            long value = j;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            long j2 = value;
            ViewPropertyAnimatorCompatICS.setStartDelay(view2, value);
        }

        public long getStartDelay(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            return ViewPropertyAnimatorCompatICS.getStartDelay(view2);
        }

        public void alphaBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.alphaBy(view2, value);
        }

        public void rotation(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.rotation(view2, value);
        }

        public void rotationBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.rotationBy(view2, value);
        }

        public void rotationX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.rotationX(view2, value);
        }

        public void rotationXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.rotationXBy(view2, value);
        }

        public void rotationY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.rotationY(view2, value);
        }

        public void rotationYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.rotationYBy(view2, value);
        }

        public void scaleX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.scaleX(view2, value);
        }

        public void scaleXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.scaleXBy(view2, value);
        }

        public void scaleY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.scaleY(view2, value);
        }

        public void scaleYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.scaleYBy(view2, value);
        }

        public void cancel(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            ViewPropertyAnimatorCompatICS.cancel(view2);
        }

        /* renamed from: x */
        public void mo4008x(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.m26x(view2, value);
        }

        public void xBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.xBy(view2, value);
        }

        /* renamed from: y */
        public void mo4010y(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.m27y(view2, value);
        }

        public void yBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.yBy(view2, value);
        }

        public void translationXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.translationXBy(view2, value);
        }

        public void translationYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatICS.translationYBy(view2, value);
        }

        public void start(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            ViewPropertyAnimatorCompatICS.start(view2);
        }

        public void setListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorListener listener = viewPropertyAnimatorListener;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener2 = listener;
            view2.setTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID, listener);
            ViewPropertyAnimatorCompatICS.setListener(view2, new MyVpaListener(vpa));
        }

        public void withEndAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            Runnable runnable2 = runnable;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            Runnable runnable3 = runnable2;
            ViewPropertyAnimatorCompatICS.setListener(view2, new MyVpaListener(vpa));
            vpa.mEndAction = runnable2;
        }

        public void withStartAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            Runnable runnable2 = runnable;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            Runnable runnable3 = runnable2;
            ViewPropertyAnimatorCompatICS.setListener(view2, new MyVpaListener(vpa));
            vpa.mStartAction = runnable2;
        }

        public void withLayer(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompat vpa = viewPropertyAnimatorCompat;
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = vpa;
            View view3 = view2;
            vpa.mOldLayerType = ViewCompat.getLayerType(view2);
            ViewPropertyAnimatorCompatICS.setListener(view2, new MyVpaListener(vpa));
        }
    }

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$JBMr2ViewPropertyAnimatorCompatImpl */
    static class JBMr2ViewPropertyAnimatorCompatImpl extends JBViewPropertyAnimatorCompatImpl {
        JBMr2ViewPropertyAnimatorCompatImpl() {
        }

        public Interpolator getInterpolator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            return ViewPropertyAnimatorCompatJellybeanMr2.getInterpolator(view2);
        }
    }

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$JBViewPropertyAnimatorCompatImpl */
    static class JBViewPropertyAnimatorCompatImpl extends ICSViewPropertyAnimatorCompatImpl {
        JBViewPropertyAnimatorCompatImpl() {
        }

        public void setListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
            View view2 = view;
            ViewPropertyAnimatorListener listener = viewPropertyAnimatorListener;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener2 = listener;
            ViewPropertyAnimatorCompatJB.setListener(view2, listener);
        }

        public void withStartAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            View view2 = view;
            Runnable runnable2 = runnable;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            Runnable runnable3 = runnable2;
            ViewPropertyAnimatorCompatJB.withStartAction(view2, runnable2);
        }

        public void withEndAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            View view2 = view;
            Runnable runnable2 = runnable;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            Runnable runnable3 = runnable2;
            ViewPropertyAnimatorCompatJB.withEndAction(view2, runnable2);
        }

        public void withLayer(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            View view2 = view;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            ViewPropertyAnimatorCompatJB.withLayer(view2);
        }
    }

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$KitKatViewPropertyAnimatorCompatImpl */
    static class KitKatViewPropertyAnimatorCompatImpl extends JBMr2ViewPropertyAnimatorCompatImpl {
        KitKatViewPropertyAnimatorCompatImpl() {
        }

        public void setUpdateListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
            View view2 = view;
            ViewPropertyAnimatorUpdateListener listener = viewPropertyAnimatorUpdateListener;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener2 = listener;
            ViewPropertyAnimatorCompatKK.setUpdateListener(view2, listener);
        }
    }

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$LollipopViewPropertyAnimatorCompatImpl */
    static class LollipopViewPropertyAnimatorCompatImpl extends KitKatViewPropertyAnimatorCompatImpl {
        LollipopViewPropertyAnimatorCompatImpl() {
        }

        public void translationZ(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatLollipop.translationZ(view2, value);
        }

        public void translationZBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatLollipop.translationZBy(view2, value);
        }

        /* renamed from: z */
        public void mo4012z(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatLollipop.m28z(view2, value);
        }

        public void zBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            View view2 = view;
            float value = f;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            View view3 = view2;
            float f2 = value;
            ViewPropertyAnimatorCompatLollipop.zBy(view2, value);
        }
    }

    /* renamed from: android.support.v4.view.ViewPropertyAnimatorCompat$ViewPropertyAnimatorCompatImpl */
    interface ViewPropertyAnimatorCompatImpl {
        void alpha(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void alphaBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void cancel(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        long getDuration(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        Interpolator getInterpolator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        long getStartDelay(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        void rotation(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void rotationBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void rotationX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void rotationXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void rotationY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void rotationYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void scaleX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void scaleXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void scaleY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void scaleYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void setDuration(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, long j);

        void setInterpolator(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Interpolator interpolator);

        void setListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener);

        void setStartDelay(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, long j);

        void setUpdateListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener);

        void start(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        void translationX(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void translationXBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void translationY(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void translationYBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void translationZ(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void translationZBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void withEndAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable);

        void withLayer(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        void withStartAction(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable);

        /* renamed from: x */
        void mo4008x(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void xBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        /* renamed from: y */
        void mo4010y(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void yBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        /* renamed from: z */
        void mo4012z(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void zBy(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);
    }

    ViewPropertyAnimatorCompat(View view) {
        View view2 = view;
        View view3 = view2;
        this.mView = new WeakReference<>(view2);
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 21) {
            IMPL = new LollipopViewPropertyAnimatorCompatImpl();
        } else if (version >= 19) {
            IMPL = new KitKatViewPropertyAnimatorCompatImpl();
        } else if (version >= 18) {
            IMPL = new JBMr2ViewPropertyAnimatorCompatImpl();
        } else if (version >= 16) {
            IMPL = new JBViewPropertyAnimatorCompatImpl();
        } else if (version < 14) {
            IMPL = new BaseViewPropertyAnimatorCompatImpl();
        } else {
            IMPL = new ICSViewPropertyAnimatorCompatImpl();
        }
    }

    public ViewPropertyAnimatorCompat setDuration(long j) {
        long value = j;
        long j2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.setDuration(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat alpha(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.alpha(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat alphaBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.alphaBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationX(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.translationX(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationY(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.translationY(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withEndAction(Runnable runnable) {
        Runnable runnable2 = runnable;
        Runnable runnable3 = runnable2;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.withEndAction(this, view2, runnable2);
        }
        return this;
    }

    public long getDuration() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view == null) {
            return 0;
        }
        return IMPL.getDuration(this, view2);
    }

    public ViewPropertyAnimatorCompat setInterpolator(Interpolator interpolator) {
        Interpolator value = interpolator;
        Interpolator interpolator2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.setInterpolator(this, view2, value);
        }
        return this;
    }

    public Interpolator getInterpolator() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view == null) {
            return null;
        }
        return IMPL.getInterpolator(this, view2);
    }

    public ViewPropertyAnimatorCompat setStartDelay(long j) {
        long value = j;
        long j2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.setStartDelay(this, view2, value);
        }
        return this;
    }

    public long getStartDelay() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view == null) {
            return 0;
        }
        return IMPL.getStartDelay(this, view2);
    }

    public ViewPropertyAnimatorCompat rotation(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.rotation(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.rotationBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationX(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.rotationX(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationXBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.rotationXBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationY(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.rotationY(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationYBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.rotationYBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleX(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.scaleX(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleXBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.scaleXBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleY(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.scaleY(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleYBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.scaleYBy(this, view2, value);
        }
        return this;
    }

    public void cancel() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.cancel(this, view2);
        }
    }

    /* renamed from: x */
    public ViewPropertyAnimatorCompat mo3970x(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.mo4008x(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat xBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.xBy(this, view2, value);
        }
        return this;
    }

    /* renamed from: y */
    public ViewPropertyAnimatorCompat mo3972y(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.mo4010y(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat yBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.yBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationXBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.translationXBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationYBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.translationYBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.translationZBy(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZ(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.translationZ(this, view2, value);
        }
        return this;
    }

    /* renamed from: z */
    public ViewPropertyAnimatorCompat mo3974z(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.mo4012z(this, view2, value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat zBy(float f) {
        float value = f;
        float f2 = value;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.zBy(this, view2, value);
        }
        return this;
    }

    public void start() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.start(this, view2);
        }
    }

    public ViewPropertyAnimatorCompat withLayer() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.withLayer(this, view2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withStartAction(Runnable runnable) {
        Runnable runnable2 = runnable;
        Runnable runnable3 = runnable2;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.withStartAction(this, view2, runnable2);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        ViewPropertyAnimatorListener listener = viewPropertyAnimatorListener;
        ViewPropertyAnimatorListener viewPropertyAnimatorListener2 = listener;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.setListener(this, view2, listener);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setUpdateListener(ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        ViewPropertyAnimatorUpdateListener listener = viewPropertyAnimatorUpdateListener;
        ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener2 = listener;
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            IMPL.setUpdateListener(this, view2, listener);
        }
        return this;
    }
}
