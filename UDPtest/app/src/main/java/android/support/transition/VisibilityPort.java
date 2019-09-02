package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
abstract class VisibilityPort extends TransitionPort {
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String[] sTransitionProperties;

    private static class VisibilityInfo {
        ViewGroup endParent;
        int endVisibility;
        boolean fadeIn;
        ViewGroup startParent;
        int startVisibility;
        boolean visibilityChange;

        VisibilityInfo() {
        }
    }

    VisibilityPort() {
    }

    static {
        String[] strArr = new String[2];
        strArr[0] = PROPNAME_VISIBILITY;
        strArr[1] = PROPNAME_PARENT;
        sTransitionProperties = strArr;
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        int visibility = transitionValues2.view.getVisibility();
        int i = visibility;
        Object put = transitionValues2.values.put(PROPNAME_VISIBILITY, Integer.valueOf(visibility));
        Object put2 = transitionValues2.values.put(PROPNAME_PARENT, transitionValues2.view.getParent());
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        captureValues(transitionValues2);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        captureValues(transitionValues2);
    }

    public boolean isVisible(TransitionValues transitionValues) {
        boolean z;
        TransitionValues values = transitionValues;
        TransitionValues transitionValues2 = values;
        if (values == null) {
            return false;
        }
        int intValue = ((Integer) values.values.get(PROPNAME_VISIBILITY)).intValue();
        int i = intValue;
        View view = (View) values.values.get(PROPNAME_PARENT);
        View view2 = view;
        if (intValue == 0 && view != null) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        TransitionValues startValues = transitionValues;
        TransitionValues endValues = transitionValues2;
        TransitionValues transitionValues3 = startValues;
        TransitionValues transitionValues4 = endValues;
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        VisibilityInfo visInfo = visibilityInfo;
        visibilityInfo.visibilityChange = false;
        visInfo.fadeIn = false;
        if (startValues == null) {
            visInfo.startVisibility = -1;
            visInfo.startParent = null;
        } else {
            visInfo.startVisibility = ((Integer) startValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visInfo.startParent = (ViewGroup) startValues.values.get(PROPNAME_PARENT);
        }
        if (endValues == null) {
            visInfo.endVisibility = -1;
            visInfo.endParent = null;
        } else {
            visInfo.endVisibility = ((Integer) endValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visInfo.endParent = (ViewGroup) endValues.values.get(PROPNAME_PARENT);
        }
        if (!(startValues == null || endValues == null)) {
            if (visInfo.startVisibility == visInfo.endVisibility && visInfo.startParent == visInfo.endParent) {
                return visInfo;
            }
            if (visInfo.startVisibility == visInfo.endVisibility) {
                if (visInfo.startParent != visInfo.endParent) {
                    if (visInfo.endParent == null) {
                        visInfo.fadeIn = false;
                        visInfo.visibilityChange = true;
                    } else if (visInfo.startParent == null) {
                        visInfo.fadeIn = true;
                        visInfo.visibilityChange = true;
                    }
                }
            } else if (visInfo.startVisibility == 0) {
                visInfo.fadeIn = false;
                visInfo.visibilityChange = true;
            } else if (visInfo.endVisibility == 0) {
                visInfo.fadeIn = true;
                visInfo.visibilityChange = true;
            }
        }
        if (startValues == null) {
            visInfo.fadeIn = true;
            visInfo.visibilityChange = true;
        } else if (endValues == null) {
            visInfo.fadeIn = false;
            visInfo.visibilityChange = true;
        }
        return visInfo;
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        boolean z;
        ViewGroup sceneRoot = viewGroup;
        TransitionValues startValues = transitionValues;
        TransitionValues endValues = transitionValues2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValues transitionValues3 = startValues;
        TransitionValues transitionValues4 = endValues;
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(startValues, endValues);
        VisibilityInfo visInfo = visibilityChangeInfo;
        if (visibilityChangeInfo.visibilityChange) {
            boolean isTarget = false;
            if (this.mTargets.size() > 0 || this.mTargetIds.size() > 0) {
                View startView = startValues == null ? null : startValues.view;
                View endView = endValues == null ? null : endValues.view;
                int startId = startView == null ? -1 : startView.getId();
                int endId = endView == null ? -1 : endView.getId();
                if (!isValidTarget(startView, (long) startId)) {
                    if (!isValidTarget(endView, (long) endId)) {
                        z = false;
                        isTarget = z;
                    }
                }
                z = true;
                isTarget = z;
            }
            if (!(!isTarget && visInfo.startParent == null && visInfo.endParent == null)) {
                if (!visInfo.fadeIn) {
                    return onDisappear(sceneRoot, startValues, visInfo.startVisibility, endValues, visInfo.endVisibility);
                }
                return onAppear(sceneRoot, startValues, visInfo.startVisibility, endValues, visInfo.endVisibility);
            }
        }
        return null;
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        ViewGroup viewGroup2 = viewGroup;
        TransitionValues transitionValues3 = transitionValues;
        int i3 = i;
        TransitionValues transitionValues4 = transitionValues2;
        int i4 = i2;
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        ViewGroup viewGroup2 = viewGroup;
        TransitionValues transitionValues3 = transitionValues;
        int i3 = i;
        TransitionValues transitionValues4 = transitionValues2;
        int i4 = i2;
        return null;
    }
}
