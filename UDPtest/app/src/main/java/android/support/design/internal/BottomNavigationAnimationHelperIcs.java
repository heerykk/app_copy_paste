package android.support.design.internal;

import android.support.p000v4.view.animation.FastOutSlowInInterpolator;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.view.ViewGroup;

class BottomNavigationAnimationHelperIcs extends BottomNavigationAnimationHelperBase {
    private static final long ACTIVE_ANIMATION_DURATION_MS = 115;
    private final TransitionSet mSet = new AutoTransition();

    BottomNavigationAnimationHelperIcs() {
        TransitionSet ordering = this.mSet.setOrdering(0);
        Transition duration = this.mSet.setDuration(ACTIVE_ANIMATION_DURATION_MS);
        Transition interpolator = this.mSet.setInterpolator(new FastOutSlowInInterpolator());
        TransitionSet addTransition = this.mSet.addTransition(new TextScale());
    }

    /* access modifiers changed from: 0000 */
    public void beginDelayedTransition(ViewGroup viewGroup) {
        ViewGroup view = viewGroup;
        ViewGroup viewGroup2 = view;
        TransitionManager.beginDelayedTransition(view, this.mSet);
    }
}
