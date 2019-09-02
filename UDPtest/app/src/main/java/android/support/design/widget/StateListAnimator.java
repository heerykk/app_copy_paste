package android.support.design.widget;

import android.util.StateSet;
import java.util.ArrayList;

final class StateListAnimator {
    private final AnimatorListener mAnimationListener = new AnimatorListenerAdapter(this) {
        final /* synthetic */ StateListAnimator this$0;

        {
            StateListAnimator this$02 = r5;
            StateListAnimator stateListAnimator = this$02;
            this.this$0 = this$02;
        }

        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat animator = valueAnimatorCompat;
            ValueAnimatorCompat valueAnimatorCompat2 = animator;
            if (this.this$0.mRunningAnimator == animator) {
                this.this$0.mRunningAnimator = null;
            }
        }
    };
    private Tuple mLastMatch = null;
    ValueAnimatorCompat mRunningAnimator = null;
    private final ArrayList<Tuple> mTuples = new ArrayList<>();

    static class Tuple {
        final ValueAnimatorCompat mAnimator;
        final int[] mSpecs;

        Tuple(int[] iArr, ValueAnimatorCompat valueAnimatorCompat) {
            int[] specs = iArr;
            ValueAnimatorCompat animator = valueAnimatorCompat;
            int[] iArr2 = specs;
            ValueAnimatorCompat valueAnimatorCompat2 = animator;
            this.mSpecs = specs;
            this.mAnimator = animator;
        }
    }

    StateListAnimator() {
    }

    public void addState(int[] iArr, ValueAnimatorCompat valueAnimatorCompat) {
        int[] specs = iArr;
        ValueAnimatorCompat animator = valueAnimatorCompat;
        int[] iArr2 = specs;
        ValueAnimatorCompat valueAnimatorCompat2 = animator;
        Tuple tuple = new Tuple(specs, animator);
        animator.addListener(this.mAnimationListener);
        boolean add = this.mTuples.add(tuple);
    }

    /* access modifiers changed from: 0000 */
    public void setState(int[] iArr) {
        int[] state = iArr;
        int[] iArr2 = state;
        Tuple match = null;
        int count = this.mTuples.size();
        int i = 0;
        while (true) {
            if (i < count) {
                Tuple tuple = (Tuple) this.mTuples.get(i);
                Tuple tuple2 = tuple;
                if (StateSet.stateSetMatches(tuple.mSpecs, state)) {
                    match = tuple2;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        if (match != this.mLastMatch) {
            if (this.mLastMatch != null) {
                cancel();
            }
            this.mLastMatch = match;
            if (match != null) {
                start(match);
            }
        }
    }

    private void start(Tuple tuple) {
        Tuple match = tuple;
        Tuple tuple2 = match;
        this.mRunningAnimator = match.mAnimator;
        this.mRunningAnimator.start();
    }

    private void cancel() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.cancel();
            this.mRunningAnimator = null;
        }
    }

    public void jumpToCurrentState() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.end();
            this.mRunningAnimator = null;
        }
    }
}
