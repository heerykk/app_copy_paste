package android.support.transition;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public class TransitionManager {
    private static TransitionManagerStaticsImpl sImpl;
    private TransitionManagerImpl mImpl;

    static {
        if (VERSION.SDK_INT >= 19) {
            sImpl = new TransitionManagerStaticsKitKat();
        } else {
            sImpl = new TransitionManagerStaticsIcs();
        }
    }

    public TransitionManager() {
        if (VERSION.SDK_INT >= 19) {
            this.mImpl = new TransitionManagerKitKat();
        } else {
            this.mImpl = new TransitionManagerIcs();
        }
    }

    /* renamed from: go */
    public static void m2go(@NonNull Scene scene) {
        Scene scene2 = scene;
        Scene scene3 = scene2;
        sImpl.mo1182go(scene2.mImpl);
    }

    /* renamed from: go */
    public static void m3go(@NonNull Scene scene, @Nullable Transition transition) {
        Scene scene2 = scene;
        Transition transition2 = transition;
        Scene scene3 = scene2;
        Transition transition3 = transition2;
        sImpl.mo1183go(scene2.mImpl, transition2 != null ? transition2.mImpl : null);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        sImpl.beginDelayedTransition(sceneRoot);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup, @Nullable Transition transition) {
        ViewGroup sceneRoot = viewGroup;
        Transition transition2 = transition;
        ViewGroup viewGroup2 = sceneRoot;
        Transition transition3 = transition2;
        sImpl.beginDelayedTransition(sceneRoot, transition2 != null ? transition2.mImpl : null);
    }

    public void setTransition(@NonNull Scene scene, @Nullable Transition transition) {
        Scene scene2 = scene;
        Transition transition2 = transition;
        Scene scene3 = scene2;
        Transition transition3 = transition2;
        this.mImpl.setTransition(scene2.mImpl, transition2 != null ? transition2.mImpl : null);
    }

    public void setTransition(@NonNull Scene scene, @NonNull Scene scene2, @Nullable Transition transition) {
        Scene fromScene = scene;
        Scene toScene = scene2;
        Transition transition2 = transition;
        Scene scene3 = fromScene;
        Scene scene4 = toScene;
        Transition transition3 = transition2;
        this.mImpl.setTransition(fromScene.mImpl, toScene.mImpl, transition2 != null ? transition2.mImpl : null);
    }

    public void transitionTo(@NonNull Scene scene) {
        Scene scene2 = scene;
        Scene scene3 = scene2;
        this.mImpl.transitionTo(scene2.mImpl);
    }
}
