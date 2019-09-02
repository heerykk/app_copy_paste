package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class SceneStaticsKitKat extends SceneStaticsImpl {
    SceneStaticsKitKat() {
    }

    public SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        ViewGroup sceneRoot = viewGroup;
        int layoutId = i;
        Context context2 = context;
        ViewGroup viewGroup2 = sceneRoot;
        int i2 = layoutId;
        Context context3 = context2;
        SceneKitKat sceneKitKat = new SceneKitKat();
        SceneKitKat scene = sceneKitKat;
        sceneKitKat.mScene = Scene.getSceneForLayout(sceneRoot, layoutId, context2);
        return scene;
    }
}
