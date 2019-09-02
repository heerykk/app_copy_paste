package android.support.p000v4.animation;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.animation.GingerbreadAnimatorCompatProvider */
class GingerbreadAnimatorCompatProvider implements AnimatorProvider {

    /* renamed from: android.support.v4.animation.GingerbreadAnimatorCompatProvider$GingerbreadFloatValueAnimator */
    private static class GingerbreadFloatValueAnimator implements ValueAnimatorCompat {
        private long mDuration = 200;
        private boolean mEnded = false;
        private float mFraction = 0.0f;
        List<AnimatorListenerCompat> mListeners = new ArrayList();
        private Runnable mLoopRunnable = new Runnable(this) {
            final /* synthetic */ GingerbreadFloatValueAnimator this$0;

            {
                GingerbreadFloatValueAnimator this$02 = r5;
                GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                long access$000 = GingerbreadFloatValueAnimator.access$000(this.this$0) - GingerbreadFloatValueAnimator.access$100(this.this$0);
                long j = access$000;
                float access$200 = (((float) access$000) * 1.0f) / ((float) GingerbreadFloatValueAnimator.access$200(this.this$0));
                float fraction = access$200;
                if ((access$200 > 1.0f) || this.this$0.mTarget.getParent() == null) {
                    fraction = 1.0f;
                }
                float access$302 = GingerbreadFloatValueAnimator.access$302(this.this$0, fraction);
                GingerbreadFloatValueAnimator.access$400(this.this$0);
                if (GingerbreadFloatValueAnimator.access$300(this.this$0) >= 1.0f) {
                    GingerbreadFloatValueAnimator.access$500(this.this$0);
                } else {
                    boolean postDelayed = this.this$0.mTarget.postDelayed(GingerbreadFloatValueAnimator.access$600(this.this$0), 16);
                }
            }
        };
        private long mStartTime;
        private boolean mStarted = false;
        View mTarget;
        List<AnimatorUpdateListenerCompat> mUpdateListeners = new ArrayList();

        static /* synthetic */ long access$000(GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator) {
            GingerbreadFloatValueAnimator x0 = gingerbreadFloatValueAnimator;
            GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator2 = x0;
            return x0.getTime();
        }

        static /* synthetic */ long access$100(GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator) {
            GingerbreadFloatValueAnimator x0 = gingerbreadFloatValueAnimator;
            GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator2 = x0;
            return x0.mStartTime;
        }

        static /* synthetic */ long access$200(GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator) {
            GingerbreadFloatValueAnimator x0 = gingerbreadFloatValueAnimator;
            GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator2 = x0;
            return x0.mDuration;
        }

        static /* synthetic */ float access$300(GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator) {
            GingerbreadFloatValueAnimator x0 = gingerbreadFloatValueAnimator;
            GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator2 = x0;
            return x0.mFraction;
        }

        static /* synthetic */ float access$302(GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator, float f) {
            GingerbreadFloatValueAnimator x0 = gingerbreadFloatValueAnimator;
            float x1 = f;
            GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator2 = x0;
            float f2 = x1;
            x0.mFraction = x1;
            return x1;
        }

        static /* synthetic */ void access$400(GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator) {
            GingerbreadFloatValueAnimator x0 = gingerbreadFloatValueAnimator;
            GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator2 = x0;
            x0.notifyUpdateListeners();
        }

        static /* synthetic */ void access$500(GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator) {
            GingerbreadFloatValueAnimator x0 = gingerbreadFloatValueAnimator;
            GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator2 = x0;
            x0.dispatchEnd();
        }

        static /* synthetic */ Runnable access$600(GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator) {
            GingerbreadFloatValueAnimator x0 = gingerbreadFloatValueAnimator;
            GingerbreadFloatValueAnimator gingerbreadFloatValueAnimator2 = x0;
            return x0.mLoopRunnable;
        }

        public GingerbreadFloatValueAnimator() {
        }

        private void notifyUpdateListeners() {
            for (int i = this.mUpdateListeners.size() - 1; i >= 0; i--) {
                ((AnimatorUpdateListenerCompat) this.mUpdateListeners.get(i)).onAnimationUpdate(this);
            }
        }

        public void setTarget(View view) {
            View view2 = view;
            View view3 = view2;
            this.mTarget = view2;
        }

        public void addListener(AnimatorListenerCompat animatorListenerCompat) {
            AnimatorListenerCompat listener = animatorListenerCompat;
            AnimatorListenerCompat animatorListenerCompat2 = listener;
            boolean add = this.mListeners.add(listener);
        }

        public void setDuration(long j) {
            long duration = j;
            long j2 = duration;
            if (!this.mStarted) {
                this.mDuration = duration;
            }
        }

        public void start() {
            if (!this.mStarted) {
                this.mStarted = true;
                dispatchStart();
                this.mFraction = 0.0f;
                this.mStartTime = getTime();
                boolean postDelayed = this.mTarget.postDelayed(this.mLoopRunnable, 16);
            }
        }

        private long getTime() {
            return this.mTarget.getDrawingTime();
        }

        private void dispatchStart() {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                ((AnimatorListenerCompat) this.mListeners.get(i)).onAnimationStart(this);
            }
        }

        private void dispatchEnd() {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                ((AnimatorListenerCompat) this.mListeners.get(i)).onAnimationEnd(this);
            }
        }

        private void dispatchCancel() {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                ((AnimatorListenerCompat) this.mListeners.get(i)).onAnimationCancel(this);
            }
        }

        public void cancel() {
            if (!this.mEnded) {
                this.mEnded = true;
                if (this.mStarted) {
                    dispatchCancel();
                }
                dispatchEnd();
            }
        }

        public void addUpdateListener(AnimatorUpdateListenerCompat animatorUpdateListenerCompat) {
            AnimatorUpdateListenerCompat animatorUpdateListener = animatorUpdateListenerCompat;
            AnimatorUpdateListenerCompat animatorUpdateListenerCompat2 = animatorUpdateListener;
            boolean add = this.mUpdateListeners.add(animatorUpdateListener);
        }

        public float getAnimatedFraction() {
            return this.mFraction;
        }
    }

    GingerbreadAnimatorCompatProvider() {
    }

    public ValueAnimatorCompat emptyValueAnimator() {
        return new GingerbreadFloatValueAnimator();
    }

    public void clearInterpolator(View view) {
        View view2 = view;
    }
}
