package android.support.transition;

import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.transition.TransitionPort.TransitionListener;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
@RequiresApi(14)
class TransitionSetPort extends TransitionPort {
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    int mCurrentListeners;
    private boolean mPlayTogether = true;
    boolean mStarted = false;
    ArrayList<TransitionPort> mTransitions = new ArrayList<>();

    static class TransitionSetListener extends TransitionListenerAdapter {
        TransitionSetPort mTransitionSet;

        TransitionSetListener(TransitionSetPort transitionSetPort) {
            TransitionSetPort transitionSet = transitionSetPort;
            TransitionSetPort transitionSetPort2 = transitionSet;
            this.mTransitionSet = transitionSet;
        }

        public void onTransitionStart(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
            if (!this.mTransitionSet.mStarted) {
                this.mTransitionSet.start();
                this.mTransitionSet.mStarted = true;
            }
        }

        public void onTransitionEnd(TransitionPort transitionPort) {
            TransitionPort transition = transitionPort;
            TransitionPort transitionPort2 = transition;
            this.mTransitionSet.mCurrentListeners--;
            if (this.mTransitionSet.mCurrentListeners == 0) {
                this.mTransitionSet.mStarted = false;
                this.mTransitionSet.end();
            }
            TransitionPort removeListener = transition.removeListener(this);
        }
    }

    public TransitionSetPort() {
    }

    public int getOrdering() {
        return !this.mPlayTogether ? 1 : 0;
    }

    public TransitionSetPort setOrdering(int i) {
        int ordering = i;
        int i2 = ordering;
        switch (ordering) {
            case 0:
                this.mPlayTogether = true;
                break;
            case 1:
                this.mPlayTogether = false;
                break;
            default:
                throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + ordering);
        }
        return this;
    }

    public TransitionSetPort addTransition(TransitionPort transitionPort) {
        TransitionPort transition = transitionPort;
        TransitionPort transitionPort2 = transition;
        if (transition != null) {
            boolean add = this.mTransitions.add(transition);
            transition.mParent = this;
            if (!(this.mDuration < 0)) {
                TransitionPort duration = transition.setDuration(this.mDuration);
            }
        }
        return this;
    }

    public TransitionSetPort setDuration(long j) {
        long duration = j;
        long j2 = duration;
        TransitionPort duration2 = super.setDuration(duration);
        if (!(this.mDuration < 0)) {
            int numTransitions = this.mTransitions.size();
            for (int i = 0; i < numTransitions; i++) {
                TransitionPort duration3 = ((TransitionPort) this.mTransitions.get(i)).setDuration(duration);
            }
        }
        return this;
    }

    public TransitionSetPort setStartDelay(long j) {
        long startDelay = j;
        long j2 = startDelay;
        return (TransitionSetPort) super.setStartDelay(startDelay);
    }

    public TransitionSetPort setInterpolator(TimeInterpolator timeInterpolator) {
        TimeInterpolator interpolator = timeInterpolator;
        TimeInterpolator timeInterpolator2 = interpolator;
        return (TransitionSetPort) super.setInterpolator(interpolator);
    }

    public TransitionSetPort addTarget(View view) {
        View target = view;
        View view2 = target;
        return (TransitionSetPort) super.addTarget(target);
    }

    public TransitionSetPort addTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        return (TransitionSetPort) super.addTarget(targetId);
    }

    public TransitionSetPort addListener(TransitionListener transitionListener) {
        TransitionListener listener = transitionListener;
        TransitionListener transitionListener2 = listener;
        return (TransitionSetPort) super.addListener(listener);
    }

    public TransitionSetPort removeTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        return (TransitionSetPort) super.removeTarget(targetId);
    }

    public TransitionSetPort removeTarget(View view) {
        View target = view;
        View view2 = target;
        return (TransitionSetPort) super.removeTarget(target);
    }

    public TransitionSetPort removeListener(TransitionListener transitionListener) {
        TransitionListener listener = transitionListener;
        TransitionListener transitionListener2 = listener;
        return (TransitionSetPort) super.removeListener(listener);
    }

    public TransitionSetPort removeTransition(TransitionPort transitionPort) {
        TransitionPort transition = transitionPort;
        TransitionPort transitionPort2 = transition;
        boolean remove = this.mTransitions.remove(transition);
        transition.mParent = null;
        return this;
    }

    private void setupStartEndListeners() {
        TransitionSetListener listener = new TransitionSetListener(this);
        Iterator it = this.mTransitions.iterator();
        while (it.hasNext()) {
            TransitionPort transitionPort = (TransitionPort) it.next();
            TransitionPort transitionPort2 = transitionPort;
            TransitionPort addListener = transitionPort.addListener(listener);
        }
        this.mCurrentListeners = this.mTransitions.size();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        ViewGroup sceneRoot = viewGroup;
        TransitionValuesMaps startValues = transitionValuesMaps;
        TransitionValuesMaps endValues = transitionValuesMaps2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValuesMaps transitionValuesMaps3 = startValues;
        TransitionValuesMaps transitionValuesMaps4 = endValues;
        Iterator it = this.mTransitions.iterator();
        while (it.hasNext()) {
            TransitionPort transitionPort = (TransitionPort) it.next();
            TransitionPort transitionPort2 = transitionPort;
            transitionPort.createAnimators(sceneRoot, startValues, endValues);
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void runAnimators() {
        if (!this.mTransitions.isEmpty()) {
            setupStartEndListeners();
            if (this.mPlayTogether) {
                Iterator it = this.mTransitions.iterator();
                while (it.hasNext()) {
                    TransitionPort transitionPort = (TransitionPort) it.next();
                    TransitionPort transitionPort2 = transitionPort;
                    transitionPort.runAnimators();
                }
            } else {
                for (int i = 1; i < this.mTransitions.size(); i++) {
                    final TransitionPort transitionPort3 = (TransitionPort) this.mTransitions.get(i);
                    TransitionPort addListener = ((TransitionPort) this.mTransitions.get(i - 1)).addListener(new TransitionListenerAdapter(this) {
                        final /* synthetic */ TransitionSetPort this$0;

                        {
                            TransitionSetPort this$02 = r6;
                            TransitionPort transitionPort = r7;
                            TransitionSetPort transitionSetPort = this$02;
                            this.this$0 = this$02;
                        }

                        public void onTransitionEnd(TransitionPort transitionPort) {
                            TransitionPort transition = transitionPort;
                            TransitionPort transitionPort2 = transition;
                            transitionPort3.runAnimators();
                            TransitionPort removeListener = transition.removeListener(this);
                        }
                    });
                }
                TransitionPort transitionPort4 = (TransitionPort) this.mTransitions.get(0);
                TransitionPort firstTransition = transitionPort4;
                if (transitionPort4 != null) {
                    firstTransition.runAnimators();
                }
            }
            return;
        }
        start();
        end();
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        int id = transitionValues2.view.getId();
        int targetId = id;
        if (isValidTarget(transitionValues2.view, (long) id)) {
            Iterator it = this.mTransitions.iterator();
            while (it.hasNext()) {
                TransitionPort transitionPort = (TransitionPort) it.next();
                TransitionPort childTransition = transitionPort;
                if (transitionPort.isValidTarget(transitionValues2.view, (long) targetId)) {
                    childTransition.captureStartValues(transitionValues2);
                }
            }
        }
    }

    public void captureEndValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        int id = transitionValues2.view.getId();
        int targetId = id;
        if (isValidTarget(transitionValues2.view, (long) id)) {
            Iterator it = this.mTransitions.iterator();
            while (it.hasNext()) {
                TransitionPort transitionPort = (TransitionPort) it.next();
                TransitionPort childTransition = transitionPort;
                if (transitionPort.isValidTarget(transitionValues2.view, (long) targetId)) {
                    childTransition.captureEndValues(transitionValues2);
                }
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void pause(View view) {
        View sceneRoot = view;
        View view2 = sceneRoot;
        super.pause(sceneRoot);
        int numTransitions = this.mTransitions.size();
        for (int i = 0; i < numTransitions; i++) {
            ((TransitionPort) this.mTransitions.get(i)).pause(sceneRoot);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void resume(View view) {
        View sceneRoot = view;
        View view2 = sceneRoot;
        super.resume(sceneRoot);
        int numTransitions = this.mTransitions.size();
        for (int i = 0; i < numTransitions; i++) {
            ((TransitionPort) this.mTransitions.get(i)).resume(sceneRoot);
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void cancel() {
        super.cancel();
        int numTransitions = this.mTransitions.size();
        for (int i = 0; i < numTransitions; i++) {
            ((TransitionPort) this.mTransitions.get(i)).cancel();
        }
    }

    /* access modifiers changed from: 0000 */
    public TransitionSetPort setSceneRoot(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionPort sceneRoot2 = super.setSceneRoot(sceneRoot);
        int numTransitions = this.mTransitions.size();
        for (int i = 0; i < numTransitions; i++) {
            TransitionPort sceneRoot3 = ((TransitionPort) this.mTransitions.get(i)).setSceneRoot(sceneRoot);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void setCanRemoveViews(boolean z) {
        boolean canRemoveViews = z;
        super.setCanRemoveViews(canRemoveViews);
        int numTransitions = this.mTransitions.size();
        for (int i = 0; i < numTransitions; i++) {
            ((TransitionPort) this.mTransitions.get(i)).setCanRemoveViews(canRemoveViews);
        }
    }

    /* access modifiers changed from: 0000 */
    public String toString(String str) {
        String indent = str;
        String str2 = indent;
        String result = super.toString(indent);
        for (int i = 0; i < this.mTransitions.size(); i++) {
            result = result + "\n" + ((TransitionPort) this.mTransitions.get(i)).toString(indent + "  ");
        }
        return result;
    }

    public TransitionSetPort clone() {
        TransitionSetPort transitionSetPort = (TransitionSetPort) super.clone();
        TransitionSetPort clone = transitionSetPort;
        transitionSetPort.mTransitions = new ArrayList<>();
        int numTransitions = this.mTransitions.size();
        for (int i = 0; i < numTransitions; i++) {
            TransitionSetPort addTransition = clone.addTransition(((TransitionPort) this.mTransitions.get(i)).clone());
        }
        return clone;
    }
}
