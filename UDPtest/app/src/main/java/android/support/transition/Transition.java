package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.os.Build.VERSION;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class Transition implements TransitionInterface {
    TransitionImpl mImpl;

    public interface TransitionListener extends TransitionInterfaceListener<Transition> {
        void onTransitionCancel(@NonNull Transition transition);

        void onTransitionEnd(@NonNull Transition transition);

        void onTransitionPause(@NonNull Transition transition);

        void onTransitionResume(@NonNull Transition transition);

        void onTransitionStart(@NonNull Transition transition);
    }

    public abstract void captureEndValues(@NonNull TransitionValues transitionValues);

    public abstract void captureStartValues(@NonNull TransitionValues transitionValues);

    public Transition() {
        this(false);
    }

    Transition(boolean z) {
        if (!z) {
            if (VERSION.SDK_INT >= 23) {
                this.mImpl = new TransitionApi23();
            } else if (VERSION.SDK_INT < 19) {
                this.mImpl = new TransitionIcs();
            } else {
                this.mImpl = new TransitionKitKat();
            }
            this.mImpl.init(this);
        }
    }

    @NonNull
    public Transition addListener(@NonNull TransitionListener transitionListener) {
        TransitionListener listener = transitionListener;
        TransitionListener transitionListener2 = listener;
        TransitionImpl addListener = this.mImpl.addListener(listener);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull View view) {
        View target = view;
        View view2 = target;
        TransitionImpl addTarget = this.mImpl.addTarget(target);
        return this;
    }

    @NonNull
    public Transition addTarget(@IdRes int i) {
        int targetId = i;
        int i2 = targetId;
        TransitionImpl addTarget = this.mImpl.addTarget(targetId);
        return this;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        ViewGroup viewGroup2 = viewGroup;
        TransitionValues transitionValues3 = transitionValues;
        TransitionValues transitionValues4 = transitionValues2;
        return null;
    }

    @NonNull
    public Transition excludeChildren(@NonNull View view, boolean z) {
        View target = view;
        View view2 = target;
        TransitionImpl excludeChildren = this.mImpl.excludeChildren(target, z);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@IdRes int i, boolean z) {
        int targetId = i;
        int i2 = targetId;
        TransitionImpl excludeChildren = this.mImpl.excludeChildren(targetId, z);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull Class cls, boolean z) {
        Class type = cls;
        Class cls2 = type;
        TransitionImpl excludeChildren = this.mImpl.excludeChildren(type, z);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull View view, boolean z) {
        View target = view;
        View view2 = target;
        TransitionImpl excludeTarget = this.mImpl.excludeTarget(target, z);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@IdRes int i, boolean z) {
        int targetId = i;
        int i2 = targetId;
        TransitionImpl excludeTarget = this.mImpl.excludeTarget(targetId, z);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class cls, boolean z) {
        Class type = cls;
        Class cls2 = type;
        TransitionImpl excludeTarget = this.mImpl.excludeTarget(type, z);
        return this;
    }

    public long getDuration() {
        return this.mImpl.getDuration();
    }

    @NonNull
    public Transition setDuration(long j) {
        long duration = j;
        long j2 = duration;
        TransitionImpl duration2 = this.mImpl.setDuration(duration);
        return this;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.mImpl.getInterpolator();
    }

    @NonNull
    public Transition setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        TimeInterpolator interpolator = timeInterpolator;
        TimeInterpolator timeInterpolator2 = interpolator;
        TransitionImpl interpolator2 = this.mImpl.setInterpolator(interpolator);
        return this;
    }

    @NonNull
    public String getName() {
        return this.mImpl.getName();
    }

    public long getStartDelay() {
        return this.mImpl.getStartDelay();
    }

    @NonNull
    public Transition setStartDelay(long j) {
        long startDelay = j;
        long j2 = startDelay;
        TransitionImpl startDelay2 = this.mImpl.setStartDelay(startDelay);
        return this;
    }

    @NonNull
    public List<Integer> getTargetIds() {
        return this.mImpl.getTargetIds();
    }

    @NonNull
    public List<View> getTargets() {
        return this.mImpl.getTargets();
    }

    @Nullable
    public String[] getTransitionProperties() {
        return this.mImpl.getTransitionProperties();
    }

    @NonNull
    public TransitionValues getTransitionValues(@NonNull View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        return this.mImpl.getTransitionValues(view2, z);
    }

    @NonNull
    public Transition removeListener(@NonNull TransitionListener transitionListener) {
        TransitionListener listener = transitionListener;
        TransitionListener transitionListener2 = listener;
        TransitionImpl removeListener = this.mImpl.removeListener(listener);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull View view) {
        View target = view;
        View view2 = target;
        TransitionImpl removeTarget = this.mImpl.removeTarget(target);
        return this;
    }

    @NonNull
    public Transition removeTarget(@IdRes int i) {
        int targetId = i;
        int i2 = targetId;
        TransitionImpl removeTarget = this.mImpl.removeTarget(targetId);
        return this;
    }

    public String toString() {
        return this.mImpl.toString();
    }
}
