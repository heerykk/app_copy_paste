package android.support.transition;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class Scene {
    private static SceneStaticsImpl sImpl;
    SceneImpl mImpl;

    static {
        if (VERSION.SDK_INT >= 21) {
            sImpl = new SceneStaticsApi21();
        } else if (VERSION.SDK_INT < 19) {
            sImpl = new SceneStaticsIcs();
        } else {
            sImpl = new SceneStaticsKitKat();
        }
    }

    public Scene(@NonNull ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        this.mImpl = createSceneImpl();
        this.mImpl.init(sceneRoot);
    }

    public Scene(@NonNull ViewGroup viewGroup, @NonNull View view) {
        ViewGroup sceneRoot = viewGroup;
        View layout = view;
        ViewGroup viewGroup2 = sceneRoot;
        View view2 = layout;
        this.mImpl = createSceneImpl();
        this.mImpl.init(sceneRoot, layout);
    }

    private Scene(SceneImpl sceneImpl) {
        SceneImpl scene = sceneImpl;
        SceneImpl sceneImpl2 = scene;
        this.mImpl = scene;
    }

    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup viewGroup, @LayoutRes int i, @NonNull Context context) {
        ViewGroup sceneRoot = viewGroup;
        int layoutId = i;
        Context context2 = context;
        ViewGroup viewGroup2 = sceneRoot;
        int i2 = layoutId;
        Context context3 = context2;
        SparseArray sparseArray = (SparseArray) sceneRoot.getTag(C0071R.C0072id.transition_scene_layoutid_cache);
        SparseArray sparseArray2 = sparseArray;
        if (sparseArray == null) {
            sparseArray2 = new SparseArray();
            sceneRoot.setTag(C0071R.C0072id.transition_scene_layoutid_cache, sparseArray2);
        }
        Scene scene = (Scene) sparseArray2.get(layoutId);
        Scene scene2 = scene;
        if (scene != null) {
            return scene2;
        }
        Scene scene3 = new Scene(sImpl.getSceneForLayout(sceneRoot, layoutId, context2));
        sparseArray2.put(layoutId, scene3);
        return scene3;
    }

    private SceneImpl createSceneImpl() {
        if (VERSION.SDK_INT >= 21) {
            return new SceneApi21();
        }
        if (VERSION.SDK_INT < 19) {
            return new SceneIcs();
        }
        return new SceneKitKat();
    }

    @NonNull
    public ViewGroup getSceneRoot() {
        return this.mImpl.getSceneRoot();
    }

    public void exit() {
        this.mImpl.exit();
    }

    public void enter() {
        this.mImpl.enter();
    }

    public void setEnterAction(@Nullable Runnable runnable) {
        Runnable action = runnable;
        Runnable runnable2 = action;
        this.mImpl.setEnterAction(action);
    }

    public void setExitAction(@Nullable Runnable runnable) {
        Runnable action = runnable;
        Runnable runnable2 = action;
        this.mImpl.setExitAction(action);
    }
}
