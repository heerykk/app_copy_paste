package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(19)
@RequiresApi(19)
class SceneKitKat extends SceneWrapper {
    private static Field sEnterAction;
    private static Method sSetCurrentScene;
    private View mLayout;

    SceneKitKat() {
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
        if (!(layout instanceof ViewGroup)) {
            this.mScene = new Scene(sceneRoot);
            this.mLayout = layout;
            return;
        }
        this.mScene = new Scene(sceneRoot, (ViewGroup) layout);
    }

    public void enter() {
        if (this.mLayout == null) {
            this.mScene.enter();
            return;
        }
        ViewGroup sceneRoot = getSceneRoot();
        ViewGroup root = sceneRoot;
        sceneRoot.removeAllViews();
        root.addView(this.mLayout);
        invokeEnterAction();
        updateCurrentScene(root);
    }

    private void invokeEnterAction() {
        if (sEnterAction == null) {
            try {
                sEnterAction = Scene.class.getDeclaredField("mEnterAction");
                sEnterAction.setAccessible(true);
            } catch (NoSuchFieldException e) {
                NoSuchFieldException noSuchFieldException = e;
                throw new RuntimeException(e);
            }
        }
        try {
            Runnable runnable = (Runnable) sEnterAction.get(this.mScene);
            Runnable enterAction = runnable;
            if (runnable != null) {
                enterAction.run();
            }
        } catch (IllegalAccessException e2) {
            IllegalAccessException illegalAccessException = e2;
            throw new RuntimeException(e2);
        }
    }

    private void updateCurrentScene(View view) {
        View view2 = view;
        View view3 = view2;
        if (sSetCurrentScene == null) {
            Class<Scene> cls = Scene.class;
            String str = "setCurrentScene";
            try {
                Class[] clsArr = new Class[2];
                clsArr[0] = View.class;
                clsArr[1] = Scene.class;
                sSetCurrentScene = cls.getDeclaredMethod(str, clsArr);
                sSetCurrentScene.setAccessible(true);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                throw new RuntimeException(e);
            }
        }
        try {
            Method method = sSetCurrentScene;
            Object[] objArr = new Object[2];
            objArr[0] = view2;
            objArr[1] = this.mScene;
            Object invoke = method.invoke(null, objArr);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            Throwable th = e2;
            throw new RuntimeException(e2);
        }
    }
}
