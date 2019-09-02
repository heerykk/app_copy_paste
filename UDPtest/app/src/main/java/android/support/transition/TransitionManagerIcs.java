package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@TargetApi(14)
@RequiresApi(14)
class TransitionManagerIcs extends TransitionManagerImpl {
    private final TransitionManagerPort mTransitionManager = new TransitionManagerPort();

    TransitionManagerIcs() {
    }

    public void setTransition(SceneImpl sceneImpl, TransitionImpl transitionImpl) {
        SceneImpl scene = sceneImpl;
        TransitionImpl transition = transitionImpl;
        SceneImpl sceneImpl2 = scene;
        TransitionImpl transitionImpl2 = transition;
        this.mTransitionManager.setTransition(((SceneIcs) scene).mScene, transition != null ? ((TransitionIcs) transition).mTransition : null);
    }

    public void setTransition(SceneImpl sceneImpl, SceneImpl sceneImpl2, TransitionImpl transitionImpl) {
        SceneImpl fromScene = sceneImpl;
        SceneImpl toScene = sceneImpl2;
        TransitionImpl transition = transitionImpl;
        SceneImpl sceneImpl3 = fromScene;
        SceneImpl sceneImpl4 = toScene;
        TransitionImpl transitionImpl2 = transition;
        this.mTransitionManager.setTransition(((SceneIcs) fromScene).mScene, ((SceneIcs) toScene).mScene, transition != null ? ((TransitionIcs) transition).mTransition : null);
    }

    public void transitionTo(SceneImpl sceneImpl) {
        SceneImpl scene = sceneImpl;
        SceneImpl sceneImpl2 = scene;
        this.mTransitionManager.transitionTo(((SceneIcs) scene).mScene);
    }
}
