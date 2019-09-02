package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class TransitionManagerStaticsIcs extends TransitionManagerStaticsImpl {
    TransitionManagerStaticsIcs() {
    }

    /* renamed from: go */
    public void mo1182go(SceneImpl sceneImpl) {
        SceneImpl scene = sceneImpl;
        SceneImpl sceneImpl2 = scene;
        TransitionManagerPort.m4go(((SceneIcs) scene).mScene);
    }

    /* renamed from: go */
    public void mo1183go(SceneImpl sceneImpl, TransitionImpl transitionImpl) {
        SceneImpl scene = sceneImpl;
        TransitionImpl transition = transitionImpl;
        SceneImpl sceneImpl2 = scene;
        TransitionImpl transitionImpl2 = transition;
        TransitionManagerPort.m5go(((SceneIcs) scene).mScene, transition != null ? ((TransitionIcs) transition).mTransition : null);
    }

    public void beginDelayedTransition(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionManagerPort.beginDelayedTransition(sceneRoot);
    }

    public void beginDelayedTransition(ViewGroup viewGroup, TransitionImpl transitionImpl) {
        ViewGroup sceneRoot = viewGroup;
        TransitionImpl transition = transitionImpl;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionImpl transitionImpl2 = transition;
        TransitionManagerPort.beginDelayedTransition(sceneRoot, transition != null ? ((TransitionIcs) transition).mTransition : null);
    }
}
