package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.util.ArrayMap;
import android.support.p000v4.view.ViewCompat;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
@RequiresApi(14)
class TransitionManagerPort {
    private static final String[] EMPTY_STRINGS = new String[0];
    private static String LOG_TAG = "TransitionManager";
    private static TransitionPort sDefaultTransition = new AutoTransitionPort();
    static ArrayList<ViewGroup> sPendingTransitions = new ArrayList<>();
    private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<TransitionPort>>>> sRunningTransitions = new ThreadLocal<>();
    ArrayMap<String, ArrayMap<ScenePort, TransitionPort>> mNameSceneTransitions = new ArrayMap<>();
    ArrayMap<ScenePort, ArrayMap<String, TransitionPort>> mSceneNameTransitions = new ArrayMap<>();
    ArrayMap<ScenePort, ArrayMap<ScenePort, TransitionPort>> mScenePairTransitions = new ArrayMap<>();
    ArrayMap<ScenePort, TransitionPort> mSceneTransitions = new ArrayMap<>();

    private static class MultiListener implements OnPreDrawListener, OnAttachStateChangeListener {
        ViewGroup mSceneRoot;
        TransitionPort mTransition;

        MultiListener(TransitionPort transitionPort, ViewGroup viewGroup) {
            TransitionPort transition = transitionPort;
            ViewGroup sceneRoot = viewGroup;
            TransitionPort transitionPort2 = transition;
            ViewGroup viewGroup2 = sceneRoot;
            this.mTransition = transition;
            this.mSceneRoot = sceneRoot;
        }

        private void removeListeners() {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
        }

        public void onViewAttachedToWindow(View view) {
            View view2 = view;
        }

        public void onViewDetachedFromWindow(View view) {
            View view2 = view;
            removeListeners();
            boolean remove = TransitionManagerPort.sPendingTransitions.remove(this.mSceneRoot);
            ArrayList arrayList = (ArrayList) TransitionManagerPort.getRunningTransitions().get(this.mSceneRoot);
            ArrayList arrayList2 = arrayList;
            if (arrayList != null && arrayList2.size() > 0) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    TransitionPort transitionPort = (TransitionPort) it.next();
                    TransitionPort transitionPort2 = transitionPort;
                    transitionPort.resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

        public boolean onPreDraw() {
            removeListeners();
            boolean remove = TransitionManagerPort.sPendingTransitions.remove(this.mSceneRoot);
            ArrayMap runningTransitions = TransitionManagerPort.getRunningTransitions();
            ArrayMap arrayMap = runningTransitions;
            ArrayList arrayList = (ArrayList) runningTransitions.get(this.mSceneRoot);
            ArrayList arrayList2 = null;
            if (arrayList == null) {
                arrayList = new ArrayList();
                Object put = arrayMap.put(this.mSceneRoot, arrayList);
            } else if (arrayList.size() > 0) {
                arrayList2 = new ArrayList(arrayList);
            }
            boolean add = arrayList.add(this.mTransition);
            final ArrayMap arrayMap2 = arrayMap;
            TransitionPort addListener = this.mTransition.addListener(new TransitionListenerAdapter(this) {
                final /* synthetic */ MultiListener this$0;

                {
                    MultiListener this$02 = r6;
                    ArrayMap arrayMap = r7;
                    MultiListener multiListener = this$02;
                    this.this$0 = this$02;
                }

                public void onTransitionEnd(TransitionPort transitionPort) {
                    TransitionPort transition = transitionPort;
                    TransitionPort transitionPort2 = transition;
                    ArrayList arrayList = (ArrayList) arrayMap2.get(this.this$0.mSceneRoot);
                    ArrayList arrayList2 = arrayList;
                    boolean remove = arrayList.remove(transition);
                }
            });
            this.mTransition.captureValues(this.mSceneRoot, false);
            if (arrayList2 != null) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    TransitionPort transitionPort = (TransitionPort) it.next();
                    TransitionPort transitionPort2 = transitionPort;
                    transitionPort.resume(this.mSceneRoot);
                }
            }
            this.mTransition.playTransition(this.mSceneRoot);
            return true;
        }
    }

    TransitionManagerPort() {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static TransitionPort getDefaultTransition() {
        return sDefaultTransition;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setDefaultTransition(TransitionPort transitionPort) {
        TransitionPort transition = transitionPort;
        TransitionPort transitionPort2 = transition;
        sDefaultTransition = transition;
    }

    private static void changeScene(ScenePort scenePort, TransitionPort transitionPort) {
        ScenePort scene = scenePort;
        TransitionPort transition = transitionPort;
        ScenePort scenePort2 = scene;
        TransitionPort transitionPort2 = transition;
        ViewGroup sceneRoot = scene.getSceneRoot();
        TransitionPort transitionClone = null;
        if (transition != null) {
            TransitionPort clone = transition.clone();
            transitionClone = clone;
            TransitionPort sceneRoot2 = clone.setSceneRoot(sceneRoot);
        }
        ScenePort currentScene = ScenePort.getCurrentScene(sceneRoot);
        ScenePort oldScene = currentScene;
        if (currentScene != null && oldScene.isCreatedFromLayoutResource()) {
            transitionClone.setCanRemoveViews(true);
        }
        sceneChangeSetup(sceneRoot, transitionClone);
        scene.enter();
        sceneChangeRunTransition(sceneRoot, transitionClone);
    }

    static ArrayMap<ViewGroup, ArrayList<TransitionPort>> getRunningTransitions() {
        WeakReference weakReference = (WeakReference) sRunningTransitions.get();
        WeakReference weakReference2 = weakReference;
        if (weakReference == null || weakReference2.get() == null) {
            weakReference2 = new WeakReference(new ArrayMap());
            sRunningTransitions.set(weakReference2);
        }
        return (ArrayMap) weakReference2.get();
    }

    private static void sceneChangeRunTransition(ViewGroup viewGroup, TransitionPort transitionPort) {
        ViewGroup sceneRoot = viewGroup;
        TransitionPort transition = transitionPort;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionPort transitionPort2 = transition;
        if (transition != null && sceneRoot != null) {
            MultiListener listener = new MultiListener(transition, sceneRoot);
            sceneRoot.addOnAttachStateChangeListener(listener);
            sceneRoot.getViewTreeObserver().addOnPreDrawListener(listener);
        }
    }

    private static void sceneChangeSetup(ViewGroup viewGroup, TransitionPort transitionPort) {
        ViewGroup sceneRoot = viewGroup;
        TransitionPort transition = transitionPort;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionPort transitionPort2 = transition;
        ArrayList arrayList = (ArrayList) getRunningTransitions().get(sceneRoot);
        ArrayList arrayList2 = arrayList;
        if (arrayList != null && arrayList2.size() > 0) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                TransitionPort transitionPort3 = (TransitionPort) it.next();
                TransitionPort transitionPort4 = transitionPort3;
                transitionPort3.pause(sceneRoot);
            }
        }
        if (transition != null) {
            transition.captureValues(sceneRoot, true);
        }
        ScenePort currentScene = ScenePort.getCurrentScene(sceneRoot);
        ScenePort scenePort = currentScene;
        if (currentScene != null) {
            scenePort.exit();
        }
    }

    /* renamed from: go */
    public static void m4go(ScenePort scenePort) {
        ScenePort scene = scenePort;
        ScenePort scenePort2 = scene;
        changeScene(scene, sDefaultTransition);
    }

    /* renamed from: go */
    public static void m5go(ScenePort scenePort, TransitionPort transitionPort) {
        ScenePort scene = scenePort;
        TransitionPort transition = transitionPort;
        ScenePort scenePort2 = scene;
        TransitionPort transitionPort2 = transition;
        changeScene(scene, transition);
    }

    public static void beginDelayedTransition(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        beginDelayedTransition(sceneRoot, null);
    }

    public static void beginDelayedTransition(ViewGroup viewGroup, TransitionPort transitionPort) {
        ViewGroup sceneRoot = viewGroup;
        TransitionPort transition = transitionPort;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionPort transition2 = transition;
        if (!sPendingTransitions.contains(sceneRoot) && ViewCompat.isLaidOut(sceneRoot)) {
            boolean add = sPendingTransitions.add(sceneRoot);
            if (transition == null) {
                transition2 = sDefaultTransition;
            }
            TransitionPort transitionClone = transition2.clone();
            sceneChangeSetup(sceneRoot, transitionClone);
            ScenePort.setCurrentScene(sceneRoot, null);
            sceneChangeRunTransition(sceneRoot, transitionClone);
        }
    }

    public void setTransition(ScenePort scenePort, TransitionPort transitionPort) {
        ScenePort scene = scenePort;
        TransitionPort transition = transitionPort;
        ScenePort scenePort2 = scene;
        TransitionPort transitionPort2 = transition;
        Object put = this.mSceneTransitions.put(scene, transition);
    }

    public void setTransition(ScenePort scenePort, ScenePort scenePort2, TransitionPort transitionPort) {
        ScenePort fromScene = scenePort;
        ScenePort toScene = scenePort2;
        TransitionPort transition = transitionPort;
        ScenePort scenePort3 = fromScene;
        ScenePort scenePort4 = toScene;
        TransitionPort transitionPort2 = transition;
        ArrayMap arrayMap = (ArrayMap) this.mScenePairTransitions.get(toScene);
        ArrayMap arrayMap2 = arrayMap;
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            Object put = this.mScenePairTransitions.put(toScene, arrayMap2);
        }
        Object put2 = arrayMap2.put(fromScene, transition);
    }

    private TransitionPort getTransition(ScenePort scenePort) {
        TransitionPort transitionPort;
        ScenePort scene = scenePort;
        ScenePort scenePort2 = scene;
        ViewGroup sceneRoot = scene.getSceneRoot();
        ViewGroup sceneRoot2 = sceneRoot;
        if (sceneRoot != null) {
            ScenePort currentScene = ScenePort.getCurrentScene(sceneRoot2);
            ScenePort currScene = currentScene;
            if (currentScene != null) {
                ArrayMap arrayMap = (ArrayMap) this.mScenePairTransitions.get(scene);
                ArrayMap arrayMap2 = arrayMap;
                if (arrayMap != null) {
                    TransitionPort transitionPort2 = (TransitionPort) arrayMap2.get(currScene);
                    TransitionPort transition = transitionPort2;
                    if (transitionPort2 != null) {
                        return transition;
                    }
                }
            }
        }
        TransitionPort transitionPort3 = (TransitionPort) this.mSceneTransitions.get(scene);
        TransitionPort transition2 = transitionPort3;
        if (transitionPort3 == null) {
            transitionPort = sDefaultTransition;
        } else {
            transitionPort = transition2;
        }
        return transitionPort;
    }

    public TransitionPort getNamedTransition(String str, ScenePort scenePort) {
        String fromName = str;
        ScenePort toScene = scenePort;
        String str2 = fromName;
        ScenePort scenePort2 = toScene;
        ArrayMap arrayMap = (ArrayMap) this.mNameSceneTransitions.get(fromName);
        ArrayMap arrayMap2 = arrayMap;
        if (arrayMap == null) {
            return null;
        }
        return (TransitionPort) arrayMap2.get(toScene);
    }

    public TransitionPort getNamedTransition(ScenePort scenePort, String str) {
        ScenePort fromScene = scenePort;
        String toName = str;
        ScenePort scenePort2 = fromScene;
        String str2 = toName;
        ArrayMap arrayMap = (ArrayMap) this.mSceneNameTransitions.get(fromScene);
        ArrayMap arrayMap2 = arrayMap;
        if (arrayMap == null) {
            return null;
        }
        return (TransitionPort) arrayMap2.get(toName);
    }

    public String[] getTargetSceneNames(ScenePort scenePort) {
        ScenePort fromScene = scenePort;
        ScenePort scenePort2 = fromScene;
        ArrayMap arrayMap = (ArrayMap) this.mSceneNameTransitions.get(fromScene);
        ArrayMap arrayMap2 = arrayMap;
        if (arrayMap == null) {
            return EMPTY_STRINGS;
        }
        int size = arrayMap2.size();
        int count = size;
        String[] result = new String[size];
        for (int i = 0; i < count; i++) {
            result[i] = (String) arrayMap2.keyAt(i);
        }
        return result;
    }

    public void setTransition(ScenePort scenePort, String str, TransitionPort transitionPort) {
        ScenePort fromScene = scenePort;
        String toName = str;
        TransitionPort transition = transitionPort;
        ScenePort scenePort2 = fromScene;
        String str2 = toName;
        TransitionPort transitionPort2 = transition;
        ArrayMap arrayMap = (ArrayMap) this.mSceneNameTransitions.get(fromScene);
        ArrayMap arrayMap2 = arrayMap;
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            Object put = this.mSceneNameTransitions.put(fromScene, arrayMap2);
        }
        Object put2 = arrayMap2.put(toName, transition);
    }

    public void setTransition(String str, ScenePort scenePort, TransitionPort transitionPort) {
        String fromName = str;
        ScenePort toScene = scenePort;
        TransitionPort transition = transitionPort;
        String str2 = fromName;
        ScenePort scenePort2 = toScene;
        TransitionPort transitionPort2 = transition;
        ArrayMap arrayMap = (ArrayMap) this.mNameSceneTransitions.get(fromName);
        ArrayMap arrayMap2 = arrayMap;
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            Object put = this.mNameSceneTransitions.put(fromName, arrayMap2);
        }
        Object put2 = arrayMap2.put(toScene, transition);
    }

    public void transitionTo(ScenePort scenePort) {
        ScenePort scene = scenePort;
        ScenePort scenePort2 = scene;
        changeScene(scene, getTransition(scene));
    }
}
