package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.transition.TransitionPort.TransitionListener;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(14)
@RequiresApi(14)
class TransitionIcs extends TransitionImpl {
    private CompatListener mCompatListener;
    TransitionInterface mExternalTransition;
    TransitionPort mTransition;

    private class CompatListener implements TransitionListener {
        private final ArrayList<TransitionInterfaceListener> mListeners = new ArrayList<>();
        final /* synthetic */ TransitionIcs this$0;

        CompatListener(TransitionIcs transitionIcs) {
            this.this$0 = transitionIcs;
        }

        public void addListener(TransitionInterfaceListener transitionInterfaceListener) {
            TransitionInterfaceListener listener = transitionInterfaceListener;
            TransitionInterfaceListener transitionInterfaceListener2 = listener;
            boolean add = this.mListeners.add(listener);
        }

        public void removeListener(TransitionInterfaceListener transitionInterfaceListener) {
            TransitionInterfaceListener listener = transitionInterfaceListener;
            TransitionInterfaceListener transitionInterfaceListener2 = listener;
            boolean remove = this.mListeners.remove(listener);
        }

        public boolean isEmpty() {
            return this.mListeners.isEmpty();
        }

        public void onTransitionStart(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionStart(this.this$0.mExternalTransition);
            }
        }

        public void onTransitionEnd(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionEnd(this.this$0.mExternalTransition);
            }
        }

        public void onTransitionCancel(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionCancel(this.this$0.mExternalTransition);
            }
        }

        public void onTransitionPause(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionPause(this.this$0.mExternalTransition);
            }
        }

        public void onTransitionResume(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionResume(this.this$0.mExternalTransition);
            }
        }
    }

    private static class TransitionWrapper extends TransitionPort {
        private TransitionInterface mTransition;

        public TransitionWrapper(TransitionInterface transitionInterface) {
            TransitionInterface transition = transitionInterface;
            TransitionInterface transitionInterface2 = transition;
            this.mTransition = transition;
        }

        public void captureStartValues(TransitionValues transitionValues) {
            TransitionValues transitionValues2 = transitionValues;
            TransitionValues transitionValues3 = transitionValues2;
            this.mTransition.captureStartValues(transitionValues2);
        }

        public void captureEndValues(TransitionValues transitionValues) {
            TransitionValues transitionValues2 = transitionValues;
            TransitionValues transitionValues3 = transitionValues2;
            this.mTransition.captureEndValues(transitionValues2);
        }

        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            ViewGroup sceneRoot = viewGroup;
            TransitionValues startValues = transitionValues;
            TransitionValues endValues = transitionValues2;
            ViewGroup viewGroup2 = sceneRoot;
            TransitionValues transitionValues3 = startValues;
            TransitionValues transitionValues4 = endValues;
            return this.mTransition.createAnimator(sceneRoot, startValues, endValues);
        }
    }

    TransitionIcs() {
    }

    public void init(TransitionInterface transitionInterface, Object obj) {
        TransitionInterface external = transitionInterface;
        Object internal = obj;
        TransitionInterface transitionInterface2 = external;
        Object obj2 = internal;
        this.mExternalTransition = external;
        if (internal != null) {
            this.mTransition = (TransitionPort) internal;
        } else {
            this.mTransition = new TransitionWrapper(external);
        }
    }

    public TransitionImpl addListener(TransitionInterfaceListener transitionInterfaceListener) {
        TransitionInterfaceListener listener = transitionInterfaceListener;
        TransitionInterfaceListener transitionInterfaceListener2 = listener;
        if (this.mCompatListener == null) {
            this.mCompatListener = new CompatListener(this);
            TransitionPort addListener = this.mTransition.addListener(this.mCompatListener);
        }
        this.mCompatListener.addListener(listener);
        return this;
    }

    public TransitionImpl removeListener(TransitionInterfaceListener transitionInterfaceListener) {
        TransitionInterfaceListener listener = transitionInterfaceListener;
        TransitionInterfaceListener transitionInterfaceListener2 = listener;
        if (this.mCompatListener == null) {
            return this;
        }
        this.mCompatListener.removeListener(listener);
        if (this.mCompatListener.isEmpty()) {
            TransitionPort removeListener = this.mTransition.removeListener(this.mCompatListener);
            this.mCompatListener = null;
        }
        return this;
    }

    public TransitionImpl addTarget(View view) {
        View target = view;
        View view2 = target;
        TransitionPort addTarget = this.mTransition.addTarget(target);
        return this;
    }

    public TransitionImpl addTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        TransitionPort addTarget = this.mTransition.addTarget(targetId);
        return this;
    }

    public void captureEndValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        this.mTransition.captureEndValues(transitionValues2);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        this.mTransition.captureStartValues(transitionValues2);
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ViewGroup sceneRoot = viewGroup;
        TransitionValues startValues = transitionValues;
        TransitionValues endValues = transitionValues2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValues transitionValues3 = startValues;
        TransitionValues transitionValues4 = endValues;
        return this.mTransition.createAnimator(sceneRoot, startValues, endValues);
    }

    public TransitionImpl excludeChildren(View view, boolean z) {
        View target = view;
        View view2 = target;
        TransitionPort excludeChildren = this.mTransition.excludeChildren(target, z);
        return this;
    }

    public TransitionImpl excludeChildren(int i, boolean z) {
        int targetId = i;
        int i2 = targetId;
        TransitionPort excludeChildren = this.mTransition.excludeChildren(targetId, z);
        return this;
    }

    public TransitionImpl excludeChildren(Class cls, boolean z) {
        Class type = cls;
        Class cls2 = type;
        TransitionPort excludeChildren = this.mTransition.excludeChildren(type, z);
        return this;
    }

    public TransitionImpl excludeTarget(View view, boolean z) {
        View target = view;
        View view2 = target;
        TransitionPort excludeTarget = this.mTransition.excludeTarget(target, z);
        return this;
    }

    public TransitionImpl excludeTarget(int i, boolean z) {
        int targetId = i;
        int i2 = targetId;
        TransitionPort excludeTarget = this.mTransition.excludeTarget(targetId, z);
        return this;
    }

    public TransitionImpl excludeTarget(Class cls, boolean z) {
        Class type = cls;
        Class cls2 = type;
        TransitionPort excludeTarget = this.mTransition.excludeTarget(type, z);
        return this;
    }

    public long getDuration() {
        return this.mTransition.getDuration();
    }

    public TransitionImpl setDuration(long j) {
        long duration = j;
        long j2 = duration;
        TransitionPort duration2 = this.mTransition.setDuration(duration);
        return this;
    }

    public TimeInterpolator getInterpolator() {
        return this.mTransition.getInterpolator();
    }

    public TransitionImpl setInterpolator(TimeInterpolator timeInterpolator) {
        TimeInterpolator interpolator = timeInterpolator;
        TimeInterpolator timeInterpolator2 = interpolator;
        TransitionPort interpolator2 = this.mTransition.setInterpolator(interpolator);
        return this;
    }

    public String getName() {
        return this.mTransition.getName();
    }

    public long getStartDelay() {
        return this.mTransition.getStartDelay();
    }

    public TransitionImpl setStartDelay(long j) {
        long startDelay = j;
        long j2 = startDelay;
        TransitionPort startDelay2 = this.mTransition.setStartDelay(startDelay);
        return this;
    }

    public List<Integer> getTargetIds() {
        return this.mTransition.getTargetIds();
    }

    public List<View> getTargets() {
        return this.mTransition.getTargets();
    }

    public String[] getTransitionProperties() {
        return this.mTransition.getTransitionProperties();
    }

    public TransitionValues getTransitionValues(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        return this.mTransition.getTransitionValues(view2, z);
    }

    public TransitionImpl removeTarget(View view) {
        View target = view;
        View view2 = target;
        TransitionPort removeTarget = this.mTransition.removeTarget(target);
        return this;
    }

    public TransitionImpl removeTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        TransitionPort removeTarget = this.mTransition.removeTarget(targetId);
        return this;
    }

    public String toString() {
        return this.mTransition.toString();
    }
}
