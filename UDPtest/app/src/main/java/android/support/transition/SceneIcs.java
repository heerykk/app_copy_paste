package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class SceneIcs extends SceneImpl {
    ScenePort mScene;

    SceneIcs() {
    }

    public void init(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        this.mScene = new ScenePort(sceneRoot);
    }

    public void init(ViewGroup viewGroup, View view) {
        ViewGroup sceneRoot = viewGroup;
        View layout = view;
        ViewGroup viewGroup2 = sceneRoot;
        View view2 = layout;
        this.mScene = new ScenePort(sceneRoot, layout);
    }

    public void enter() {
        this.mScene.enter();
    }

    public void exit() {
        this.mScene.exit();
    }

    public ViewGroup getSceneRoot() {
        return this.mScene.getSceneRoot();
    }

    public void setEnterAction(Runnable runnable) {
        Runnable action = runnable;
        Runnable runnable2 = action;
        this.mScene.setEnterAction(action);
    }

    public void setExitAction(Runnable runnable) {
        Runnable action = runnable;
        Runnable runnable2 = action;
        this.mScene.setExitAction(action);
    }
}
