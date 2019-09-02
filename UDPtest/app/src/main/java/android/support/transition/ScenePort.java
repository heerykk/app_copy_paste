package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
final class ScenePort {
    private Context mContext;
    Runnable mEnterAction;
    Runnable mExitAction;
    private View mLayout;
    private int mLayoutId = -1;
    private ViewGroup mSceneRoot;

    public ScenePort(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        this.mSceneRoot = sceneRoot;
    }

    private ScenePort(ViewGroup viewGroup, int i, Context context) {
        ViewGroup sceneRoot = viewGroup;
        int layoutId = i;
        Context context2 = context;
        ViewGroup viewGroup2 = sceneRoot;
        int i2 = layoutId;
        Context context3 = context2;
        this.mContext = context2;
        this.mSceneRoot = sceneRoot;
        this.mLayoutId = layoutId;
    }

    public ScenePort(ViewGroup viewGroup, View view) {
        ViewGroup sceneRoot = viewGroup;
        View layout = view;
        ViewGroup viewGroup2 = sceneRoot;
        View view2 = layout;
        this.mSceneRoot = sceneRoot;
        this.mLayout = layout;
    }

    public static ScenePort getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        ViewGroup sceneRoot = viewGroup;
        int layoutId = i;
        Context context2 = context;
        ViewGroup viewGroup2 = sceneRoot;
        int i2 = layoutId;
        Context context3 = context2;
        return new ScenePort(sceneRoot, layoutId, context2);
    }

    static void setCurrentScene(View view, ScenePort scenePort) {
        View view2 = view;
        ScenePort scene = scenePort;
        View view3 = view2;
        ScenePort scenePort2 = scene;
        view2.setTag(C0071R.C0072id.transition_current_scene, scene);
    }

    static ScenePort getCurrentScene(View view) {
        View view2 = view;
        View view3 = view2;
        return (ScenePort) view2.getTag(C0071R.C0072id.transition_current_scene);
    }

    public ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    public void exit() {
        if (getCurrentScene(this.mSceneRoot) == this && this.mExitAction != null) {
            this.mExitAction.run();
        }
    }

    public void enter() {
        if (this.mLayoutId > 0 || this.mLayout != null) {
            getSceneRoot().removeAllViews();
            if (this.mLayoutId <= 0) {
                this.mSceneRoot.addView(this.mLayout);
            } else {
                View inflate = LayoutInflater.from(this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
            }
        }
        if (this.mEnterAction != null) {
            this.mEnterAction.run();
        }
        setCurrentScene(this.mSceneRoot, this);
    }

    public void setEnterAction(Runnable runnable) {
        Runnable action = runnable;
        Runnable runnable2 = action;
        this.mEnterAction = action;
    }

    public void setExitAction(Runnable runnable) {
        Runnable action = runnable;
        Runnable runnable2 = action;
        this.mExitAction = action;
    }

    /* access modifiers changed from: 0000 */
    public boolean isCreatedFromLayoutResource() {
        return this.mLayoutId > 0;
    }
}
