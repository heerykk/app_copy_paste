package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(21)
@RequiresApi(21)
class SceneApi21 extends SceneWrapper {
    SceneApi21() {
    }

    public void init(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        this.mScene = new Scene(sceneRoot);
    }

    public void init(ViewGroup viewGroup, View view) {
        ViewGroup sceneRoot = viewGroup;
        View layout = view;
        ViewGroup viewGroup2 = sceneRoot;
        View view2 = layout;
        this.mScene = new Scene(sceneRoot, layout);
    }

    public void enter() {
        this.mScene.enter();
    }
}
