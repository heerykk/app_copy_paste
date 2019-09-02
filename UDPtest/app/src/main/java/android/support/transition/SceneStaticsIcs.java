package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class SceneStaticsIcs extends SceneStaticsImpl {
    SceneStaticsIcs() {
    }

    public SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        ViewGroup sceneRoot = viewGroup;
        int layoutId = i;
        Context context2 = context;
        ViewGroup viewGroup2 = sceneRoot;
        int i2 = layoutId;
        Context context3 = context2;
        SceneIcs sceneIcs = new SceneIcs();
        SceneIcs scene = sceneIcs;
        sceneIcs.mScene = ScenePort.getSceneForLayout(sceneRoot, layoutId, context2);
        return scene;
    }
}
