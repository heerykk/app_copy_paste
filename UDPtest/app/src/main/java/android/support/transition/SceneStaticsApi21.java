package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.ViewGroup;

@TargetApi(21)
@RequiresApi(21)
class SceneStaticsApi21 extends SceneStaticsImpl {
    SceneStaticsApi21() {
    }

    public SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        ViewGroup sceneRoot = viewGroup;
        int layoutId = i;
        Context context2 = context;
        ViewGroup viewGroup2 = sceneRoot;
        int i2 = layoutId;
        Context context3 = context2;
        SceneApi21 sceneApi21 = new SceneApi21();
        SceneApi21 scene = sceneApi21;
        sceneApi21.mScene = Scene.getSceneForLayout(sceneRoot, layoutId, context2);
        return scene;
    }
}
