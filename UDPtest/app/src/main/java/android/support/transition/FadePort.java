package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.p000v4.view.ViewCompat;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class FadePort extends VisibilityPort {
    private static boolean DBG = false;

    /* renamed from: IN */
    public static final int f5IN = 1;
    private static final String LOG_TAG = "Fade";
    public static final int OUT = 2;
    private static final String PROPNAME_SCREEN_X = "android:fade:screenX";
    private static final String PROPNAME_SCREEN_Y = "android:fade:screenY";
    private int mFadingMode;

    public FadePort() {
        this(3);
    }

    public FadePort(int i) {
        int fadingMode = i;
        int i2 = fadingMode;
        this.mFadingMode = fadingMode;
    }

    private Animator createAnimation(View view, float f, float f2, AnimatorListenerAdapter animatorListenerAdapter) {
        View view2 = view;
        float startAlpha = f;
        float endAlpha = f2;
        AnimatorListenerAdapter listener = animatorListenerAdapter;
        View view3 = view2;
        float f3 = startAlpha;
        float f4 = endAlpha;
        AnimatorListenerAdapter animatorListenerAdapter2 = listener;
        if (startAlpha == endAlpha) {
            if (listener != null) {
                listener.onAnimationEnd(null);
            }
            return null;
        }
        ObjectAnimator anim = ObjectAnimator.ofFloat(view2, "alpha", new float[]{startAlpha, endAlpha});
        if (DBG) {
            int d = Log.d(LOG_TAG, "Created animator " + anim);
        }
        if (listener != null) {
            anim.addListener(listener);
        }
        return anim;
    }

    private void captureValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        int[] loc = new int[2];
        transitionValues2.view.getLocationOnScreen(loc);
        Object put = transitionValues2.values.put(PROPNAME_SCREEN_X, Integer.valueOf(loc[0]));
        Object put2 = transitionValues2.values.put(PROPNAME_SCREEN_Y, Integer.valueOf(loc[1]));
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = transitionValues;
        TransitionValues transitionValues3 = transitionValues2;
        super.captureStartValues(transitionValues2);
        captureValues(transitionValues2);
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        TransitionValues startValues = transitionValues;
        int startVisibility = i;
        TransitionValues endValues = transitionValues2;
        int endVisibility = i2;
        ViewGroup viewGroup2 = viewGroup;
        TransitionValues transitionValues3 = startValues;
        int i3 = startVisibility;
        TransitionValues transitionValues4 = endValues;
        int i4 = endVisibility;
        if ((this.mFadingMode & 1) != 1 || endValues == null) {
            return null;
        }
        View endView = endValues.view;
        if (DBG) {
            int d = Log.d(LOG_TAG, "Fade.onAppear: startView, startVis, endView, endVis = " + (startValues == null ? null : startValues.view) + ", " + startVisibility + ", " + endView + ", " + endVisibility);
        }
        endView.setAlpha(0.0f);
        final View view = endView;
        C00681 r0 = new TransitionListenerAdapter(this) {
            boolean mCanceled = false;
            float mPausedAlpha;
            final /* synthetic */ FadePort this$0;

            {
                FadePort this$02 = r7;
                View view = r8;
                FadePort fadePort = this$02;
                this.this$0 = this$02;
            }

            public void onTransitionCancel(TransitionPort transitionPort) {
                TransitionPort transitionPort2 = transitionPort;
                view.setAlpha(1.0f);
                this.mCanceled = true;
            }

            public void onTransitionEnd(TransitionPort transitionPort) {
                TransitionPort transitionPort2 = transitionPort;
                if (!this.mCanceled) {
                    view.setAlpha(1.0f);
                }
            }

            public void onTransitionPause(TransitionPort transitionPort) {
                TransitionPort transitionPort2 = transitionPort;
                this.mPausedAlpha = view.getAlpha();
                view.setAlpha(1.0f);
            }

            public void onTransitionResume(TransitionPort transitionPort) {
                TransitionPort transitionPort2 = transitionPort;
                view.setAlpha(this.mPausedAlpha);
            }
        };
        TransitionPort addListener = addListener(r0);
        return createAnimation(endView, 0.0f, 1.0f, null);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        ViewGroup sceneRoot = viewGroup;
        TransitionValues startValues = transitionValues;
        int startVisibility = i;
        TransitionValues endValues = transitionValues2;
        int endVisibility = i2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValues transitionValues3 = startValues;
        int i3 = startVisibility;
        TransitionValues transitionValues4 = endValues;
        int i4 = endVisibility;
        if ((this.mFadingMode & 2) != 2) {
            return null;
        }
        View view = null;
        View startView = startValues == null ? null : startValues.view;
        View endView = endValues == null ? null : endValues.view;
        if (DBG) {
            int d = Log.d(LOG_TAG, "Fade.onDisappear: startView, startVis, endView, endVis = " + startView + ", " + startVisibility + ", " + endView + ", " + endVisibility);
        }
        View overlayView = null;
        View viewToKeep = null;
        if (endView == null || endView.getParent() == null) {
            if (endView != null) {
                overlayView = endView;
                view = endView;
            } else if (startView != null) {
                if (startView.getParent() == null) {
                    overlayView = startView;
                    view = startView;
                } else if ((startView.getParent() instanceof View) && startView.getParent().getParent() == null) {
                    View view2 = (View) startView.getParent();
                    View view3 = view2;
                    int id = view2.getId();
                    int id2 = id;
                    if (!(id == -1 || sceneRoot.findViewById(id2) == null || !this.mCanRemoveViews)) {
                        overlayView = startView;
                        view = startView;
                    }
                }
            }
        } else if (endVisibility == 4) {
            view = endView;
            viewToKeep = endView;
        } else if (startView != endView) {
            view = startView;
            overlayView = startView;
        } else {
            view = endView;
            viewToKeep = endView;
        }
        int i5 = endVisibility;
        if (overlayView != null) {
            int screenX = ((Integer) startValues.values.get(PROPNAME_SCREEN_X)).intValue();
            int screenY = ((Integer) startValues.values.get(PROPNAME_SCREEN_Y)).intValue();
            int[] loc = new int[2];
            sceneRoot.getLocationOnScreen(loc);
            ViewCompat.offsetLeftAndRight(overlayView, (screenX - loc[0]) - overlayView.getLeft());
            ViewCompat.offsetTopAndBottom(overlayView, (screenY - loc[1]) - overlayView.getTop());
            ViewGroupOverlay.createFrom(sceneRoot).add(overlayView);
            ViewGroup viewGroup3 = sceneRoot;
            final View view4 = view;
            final View view5 = viewToKeep;
            final int i6 = endVisibility;
            final View view6 = overlayView;
            final ViewGroup viewGroup4 = sceneRoot;
            C00692 r0 = new AnimatorListenerAdapter(this) {
                final /* synthetic */ FadePort this$0;

                {
                    FadePort this$02 = r10;
                    View view = r11;
                    View view2 = r12;
                    int i = r13;
                    View view3 = r14;
                    ViewGroup viewGroup = r15;
                    FadePort fadePort = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationEnd(Animator animator) {
                    Animator animator2 = animator;
                    view4.setAlpha(1.0f);
                    if (view5 != null) {
                        view5.setVisibility(i6);
                    }
                    if (view6 != null) {
                        ViewGroupOverlay.createFrom(viewGroup4).remove(view6);
                    }
                }
            };
            return createAnimation(view, 1.0f, 0.0f, r0);
        } else if (viewToKeep == null) {
            return null;
        } else {
            viewToKeep.setVisibility(0);
            ViewGroup viewGroup5 = sceneRoot;
            final View view7 = view;
            final View view8 = viewToKeep;
            final int i7 = endVisibility;
            final View view9 = overlayView;
            final ViewGroup viewGroup6 = sceneRoot;
            C00703 r02 = new AnimatorListenerAdapter(this) {
                boolean mCanceled = false;
                float mPausedAlpha = -1.0f;
                final /* synthetic */ FadePort this$0;

                {
                    FadePort this$02 = r12;
                    View view = r13;
                    View view2 = r14;
                    int i = r15;
                    View view3 = r16;
                    ViewGroup viewGroup = r17;
                    FadePort fadePort = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationCancel(Animator animator) {
                    Animator animator2 = animator;
                    this.mCanceled = true;
                    if (this.mPausedAlpha >= 0.0f) {
                        view7.setAlpha(this.mPausedAlpha);
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    Animator animator2 = animator;
                    if (!this.mCanceled) {
                        view7.setAlpha(1.0f);
                    }
                    if (view8 != null && !this.mCanceled) {
                        view8.setVisibility(i7);
                    }
                    if (view9 != null) {
                        ViewGroupOverlay.createFrom(viewGroup6).add(view9);
                    }
                }
            };
            return createAnimation(view, 1.0f, 0.0f, r02);
        }
    }
}
