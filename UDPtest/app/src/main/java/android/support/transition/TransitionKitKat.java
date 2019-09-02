package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(19)
@RequiresApi(19)
class TransitionKitKat extends TransitionImpl {
    private CompatListener mCompatListener;
    TransitionInterface mExternalTransition;
    Transition mTransition;

    private class CompatListener implements TransitionListener {
        private final ArrayList<TransitionInterfaceListener> mListeners = new ArrayList<>();
        final /* synthetic */ TransitionKitKat this$0;

        CompatListener(TransitionKitKat transitionKitKat) {
            this.this$0 = transitionKitKat;
        }

        /* access modifiers changed from: 0000 */
        public void addListener(TransitionInterfaceListener transitionInterfaceListener) {
            TransitionInterfaceListener listener = transitionInterfaceListener;
            TransitionInterfaceListener transitionInterfaceListener2 = listener;
            boolean add = this.mListeners.add(listener);
        }

        /* access modifiers changed from: 0000 */
        public void removeListener(TransitionInterfaceListener transitionInterfaceListener) {
            TransitionInterfaceListener listener = transitionInterfaceListener;
            TransitionInterfaceListener transitionInterfaceListener2 = listener;
            boolean remove = this.mListeners.remove(listener);
        }

        /* access modifiers changed from: 0000 */
        public boolean isEmpty() {
            return this.mListeners.isEmpty();
        }

        public void onTransitionStart(Transition transition) {
            Transition transition2 = transition;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionStart(this.this$0.mExternalTransition);
            }
        }

        public void onTransitionEnd(Transition transition) {
            Transition transition2 = transition;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionEnd(this.this$0.mExternalTransition);
            }
        }

        public void onTransitionCancel(Transition transition) {
            Transition transition2 = transition;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionCancel(this.this$0.mExternalTransition);
            }
        }

        public void onTransitionPause(Transition transition) {
            Transition transition2 = transition;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionPause(this.this$0.mExternalTransition);
            }
        }

        public void onTransitionResume(Transition transition) {
            Transition transition2 = transition;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                TransitionInterfaceListener transitionInterfaceListener = (TransitionInterfaceListener) it.next();
                TransitionInterfaceListener transitionInterfaceListener2 = transitionInterfaceListener;
                transitionInterfaceListener.onTransitionResume(this.this$0.mExternalTransition);
            }
        }
    }

    private static class TransitionWrapper extends Transition {
        private TransitionInterface mTransition;

        public TransitionWrapper(TransitionInterface transitionInterface) {
            TransitionInterface transition = transitionInterface;
            TransitionInterface transitionInterface2 = transition;
            this.mTransition = transition;
        }

        public void captureStartValues(TransitionValues transitionValues) {
            TransitionValues transitionValues2 = transitionValues;
            TransitionValues transitionValues3 = transitionValues2;
            TransitionKitKat.wrapCaptureStartValues(this.mTransition, transitionValues2);
        }

        public void captureEndValues(TransitionValues transitionValues) {
            TransitionValues transitionValues2 = transitionValues;
            TransitionValues transitionValues3 = transitionValues2;
            TransitionKitKat.wrapCaptureEndValues(this.mTransition, transitionValues2);
        }

        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            ViewGroup sceneRoot = viewGroup;
            TransitionValues startValues = transitionValues;
            TransitionValues endValues = transitionValues2;
            ViewGroup viewGroup2 = sceneRoot;
            TransitionValues transitionValues3 = startValues;
            TransitionValues transitionValues4 = endValues;
            return this.mTransition.createAnimator(sceneRoot, TransitionKitKat.convertToSupport(startValues), TransitionKitKat.convertToSupport(endValues));
        }
    }

    TransitionKitKat() {
    }

    static void copyValues(TransitionValues transitionValues, TransitionValues transitionValues2) {
        TransitionValues source = transitionValues;
        TransitionValues dest = transitionValues2;
        TransitionValues transitionValues3 = source;
        TransitionValues transitionValues4 = dest;
        if (source != null) {
            dest.view = source.view;
            if (source.values.size() > 0) {
                dest.values.putAll(source.values);
            }
        }
    }

    static void copyValues(TransitionValues transitionValues, TransitionValues transitionValues2) {
        TransitionValues source = transitionValues;
        TransitionValues dest = transitionValues2;
        TransitionValues transitionValues3 = source;
        TransitionValues transitionValues4 = dest;
        if (source != null) {
            dest.view = source.view;
            if (source.values.size() > 0) {
                dest.values.putAll(source.values);
            }
        }
    }

    static void wrapCaptureStartValues(TransitionInterface transitionInterface, TransitionValues transitionValues) {
        TransitionInterface transition = transitionInterface;
        TransitionValues transitionValues2 = transitionValues;
        TransitionInterface transitionInterface2 = transition;
        TransitionValues transitionValues3 = transitionValues2;
        TransitionValues externalValues = new TransitionValues();
        copyValues(transitionValues2, externalValues);
        transition.captureStartValues(externalValues);
        copyValues(externalValues, transitionValues2);
    }

    static void wrapCaptureEndValues(TransitionInterface transitionInterface, TransitionValues transitionValues) {
        TransitionInterface transition = transitionInterface;
        TransitionValues transitionValues2 = transitionValues;
        TransitionInterface transitionInterface2 = transition;
        TransitionValues transitionValues3 = transitionValues2;
        TransitionValues externalValues = new TransitionValues();
        copyValues(transitionValues2, externalValues);
        transition.captureEndValues(externalValues);
        copyValues(externalValues, transitionValues2);
    }

    static TransitionValues convertToSupport(TransitionValues transitionValues) {
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        if (values == null) {
            return null;
        }
        TransitionValues supportValues = new TransitionValues();
        copyValues(values, supportValues);
        return supportValues;
    }

    static TransitionValues convertToPlatform(TransitionValues transitionValues) {
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        if (values == null) {
            return null;
        }
        TransitionValues platformValues = new TransitionValues();
        copyValues(values, platformValues);
        return platformValues;
    }

    public void init(TransitionInterface transitionInterface, Object obj) {
        TransitionInterface external = transitionInterface;
        Object internal = obj;
        TransitionInterface transitionInterface2 = external;
        Object obj2 = internal;
        this.mExternalTransition = external;
        if (internal != null) {
            this.mTransition = (Transition) internal;
        } else {
            this.mTransition = new TransitionWrapper(external);
        }
    }

    public TransitionImpl addListener(TransitionInterfaceListener transitionInterfaceListener) {
        TransitionInterfaceListener listener = transitionInterfaceListener;
        TransitionInterfaceListener transitionInterfaceListener2 = listener;
        if (this.mCompatListener == null) {
            this.mCompatListener = new CompatListener(this);
            Transition addListener = this.mTransition.addListener(this.mCompatListener);
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
            Transition removeListener = this.mTransition.removeListener(this.mCompatListener);
            this.mCompatListener = null;
        }
        return this;
    }

    public TransitionImpl addTarget(View view) {
        View target = view;
        View view2 = target;
        Transition addTarget = this.mTransition.addTarget(target);
        return this;
    }

    public TransitionImpl addTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        Transition addTarget = this.mTransition.addTarget(targetId);
        return this;
    }

    public void captureEndValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        TransitionValues internalValues = new TransitionValues();
        copyValues(transitionValues2, internalValues);
        this.mTransition.captureEndValues(internalValues);
        copyValues(internalValues, transitionValues2);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        TransitionValues internalValues = new TransitionValues();
        copyValues(transitionValues2, internalValues);
        this.mTransition.captureStartValues(internalValues);
        copyValues(internalValues, transitionValues2);
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        TransitionValues internalStartValues;
        TransitionValues internalEndValues;
        ViewGroup sceneRoot = viewGroup;
        TransitionValues startValues = transitionValues;
        TransitionValues endValues = transitionValues2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValues transitionValues3 = startValues;
        TransitionValues transitionValues4 = endValues;
        if (startValues == null) {
            internalStartValues = null;
        } else {
            internalStartValues = new TransitionValues();
            copyValues(startValues, internalStartValues);
        }
        if (endValues == null) {
            internalEndValues = null;
        } else {
            internalEndValues = new TransitionValues();
            copyValues(endValues, internalEndValues);
        }
        return this.mTransition.createAnimator(sceneRoot, internalStartValues, internalEndValues);
    }

    public TransitionImpl excludeChildren(View view, boolean z) {
        View target = view;
        View view2 = target;
        Transition excludeChildren = this.mTransition.excludeChildren(target, z);
        return this;
    }

    public TransitionImpl excludeChildren(int i, boolean z) {
        int targetId = i;
        int i2 = targetId;
        Transition excludeChildren = this.mTransition.excludeChildren(targetId, z);
        return this;
    }

    public TransitionImpl excludeChildren(Class cls, boolean z) {
        Class type = cls;
        Class cls2 = type;
        Transition excludeChildren = this.mTransition.excludeChildren(type, z);
        return this;
    }

    public TransitionImpl excludeTarget(View view, boolean z) {
        View target = view;
        View view2 = target;
        Transition excludeTarget = this.mTransition.excludeTarget(target, z);
        return this;
    }

    public TransitionImpl excludeTarget(int i, boolean z) {
        int targetId = i;
        int i2 = targetId;
        Transition excludeTarget = this.mTransition.excludeTarget(targetId, z);
        return this;
    }

    public TransitionImpl excludeTarget(Class cls, boolean z) {
        Class type = cls;
        Class cls2 = type;
        Transition excludeTarget = this.mTransition.excludeTarget(type, z);
        return this;
    }

    public long getDuration() {
        return this.mTransition.getDuration();
    }

    public TransitionImpl setDuration(long j) {
        long duration = j;
        long j2 = duration;
        Transition duration2 = this.mTransition.setDuration(duration);
        return this;
    }

    public TimeInterpolator getInterpolator() {
        return this.mTransition.getInterpolator();
    }

    public TransitionImpl setInterpolator(TimeInterpolator timeInterpolator) {
        TimeInterpolator interpolator = timeInterpolator;
        TimeInterpolator timeInterpolator2 = interpolator;
        Transition interpolator2 = this.mTransition.setInterpolator(interpolator);
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
        Transition startDelay2 = this.mTransition.setStartDelay(startDelay);
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
        boolean start = z;
        TransitionValues values = new TransitionValues();
        copyValues(this.mTransition.getTransitionValues(view2, start), values);
        return values;
    }

    public TransitionImpl removeTarget(View view) {
        View target = view;
        View view2 = target;
        Transition removeTarget = this.mTransition.removeTarget(target);
        return this;
    }

    public TransitionImpl removeTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        if (targetId > 0) {
            boolean remove = getTargetIds().remove(Integer.valueOf(targetId));
        }
        return this;
    }

    public String toString() {
        return this.mTransition.toString();
    }
}
