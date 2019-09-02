package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionManager;

@TargetApi(19)
@RequiresApi(19)
class TransitionManagerKitKat extends TransitionManagerImpl {
    private final TransitionManager mTransitionManager = new TransitionManager();

    TransitionManagerKitKat() {
    }

    public void setTransition(SceneImpl sceneImpl, TransitionImpl transitionImpl) {
        SceneImpl scene = sceneImpl;
        TransitionImpl transition = transitionImpl;
        SceneImpl sceneImpl2 = scene;
        TransitionImpl transitionImpl2 = transition;
        this.mTransitionManager.setTransition(((SceneWrapper) scene).mScene, transition != null ? ((TransitionKitKat) transition).mTransition : null);
    }

    public void setTransition(SceneImpl sceneImpl, SceneImpl sceneImpl2, TransitionImpl transitionImpl) {
        SceneImpl fromScene = sceneImpl;
        SceneImpl toScene = sceneImpl2;
        TransitionImpl transition = transitionImpl;
        SceneImpl sceneImpl3 = fromScene;
        SceneImpl sceneImpl4 = toScene;
        TransitionImpl transitionImpl2 = transition;
        this.mTransitionManager.setTransition(((SceneWrapper) fromScene).mScene, ((SceneWrapper) toScene).mScene, transition != null ? ((TransitionKitKat) transition).mTransition : null);
    }

    public void transitionTo(SceneImpl sceneImpl) {
        SceneImpl scene = sceneImpl;
        SceneImpl sceneImpl2 = scene;
        this.mTransitionManager.transitionTo(((SceneWrapper) scene).mScene);
    }
}
