package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionManager;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class TransitionManagerStaticsKitKat extends TransitionManagerStaticsImpl {
    TransitionManagerStaticsKitKat() {
    }

    /* renamed from: go */
    public void mo1182go(SceneImpl sceneImpl) {
        SceneImpl scene = sceneImpl;
        SceneImpl sceneImpl2 = scene;
        TransitionManager.go(((SceneWrapper) scene).mScene);
    }

    /* renamed from: go */
    public void mo1183go(SceneImpl sceneImpl, TransitionImpl transitionImpl) {
        SceneImpl scene = sceneImpl;
        TransitionImpl transition = transitionImpl;
        SceneImpl sceneImpl2 = scene;
        TransitionImpl transitionImpl2 = transition;
        TransitionManager.go(((SceneWrapper) scene).mScene, transition != null ? ((TransitionKitKat) transition).mTransition : null);
    }

    public void beginDelayedTransition(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionManager.beginDelayedTransition(sceneRoot);
    }

    public void beginDelayedTransition(ViewGroup viewGroup, TransitionImpl transitionImpl) {
        ViewGroup sceneRoot = viewGroup;
        TransitionImpl transition = transitionImpl;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionImpl transitionImpl2 = transition;
        TransitionManager.beginDelayedTransition(sceneRoot, transition != null ? ((TransitionKitKat) transition).mTransition : null);
    }
}
