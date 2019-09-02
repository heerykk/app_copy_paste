package android.support.transition;

public class AutoTransition extends TransitionSet {
    public AutoTransition() {
        TransitionSet ordering = setOrdering(1);
        TransitionSet addTransition = addTransition(new Fade(2)).addTransition(new ChangeBounds()).addTransition(new Fade(1));
    }
}
