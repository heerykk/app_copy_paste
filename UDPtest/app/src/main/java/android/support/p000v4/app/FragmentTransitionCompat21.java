package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.Transition.EpicenterCallback;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.app.FragmentTransitionCompat21 */
class FragmentTransitionCompat21 {
    FragmentTransitionCompat21() {
    }

    static /* synthetic */ String access$000(Map map, String str) {
        Map x0 = map;
        String x1 = str;
        Map map2 = x0;
        String str2 = x1;
        return findKeyForValue(x0, x1);
    }

    public static Object cloneTransition(Object obj) {
        Object transition = obj;
        Object obj2 = transition;
        if (transition == null) {
            return null;
        }
        return ((Transition) transition).clone();
    }

    public static Object wrapTransitionInSet(Object obj) {
        Object transition = obj;
        Object obj2 = transition;
        if (transition == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        TransitionSet transitionSet2 = transitionSet;
        TransitionSet addTransition = transitionSet.addTransition((Transition) transition);
        return transitionSet2;
    }

    public static void setSharedElementTargets(Object obj, View view, ArrayList<View> arrayList) {
        Object transitionObj = obj;
        View nonExistentView = view;
        ArrayList<View> sharedViews = arrayList;
        Object obj2 = transitionObj;
        View view2 = nonExistentView;
        ArrayList<View> arrayList2 = sharedViews;
        TransitionSet transitionSet = (TransitionSet) transitionObj;
        TransitionSet transition = transitionSet;
        List targets = transitionSet.getTargets();
        List list = targets;
        targets.clear();
        int count = sharedViews.size();
        for (int i = 0; i < count; i++) {
            bfsAddViewChildren(list, (View) sharedViews.get(i));
        }
        boolean add = list.add(nonExistentView);
        boolean add2 = sharedViews.add(nonExistentView);
        addTargets(transition, sharedViews);
    }

    private static void bfsAddViewChildren(List<View> list, View view) {
        List<View> views = list;
        View startView = view;
        List<View> list2 = views;
        View view2 = startView;
        int size = views.size();
        int startIndex = size;
        if (!containedBeforeIndex(views, startView, size)) {
            boolean add = views.add(startView);
            for (int index = startIndex; index < views.size(); index++) {
                View view3 = (View) views.get(index);
                View view4 = view3;
                if (view3 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view4;
                    ViewGroup viewGroup2 = viewGroup;
                    int childCount = viewGroup.getChildCount();
                    for (int childIndex = 0; childIndex < childCount; childIndex++) {
                        View child = viewGroup2.getChildAt(childIndex);
                        if (!containedBeforeIndex(views, child, startIndex)) {
                            boolean add2 = views.add(child);
                        }
                    }
                }
            }
        }
    }

    private static boolean containedBeforeIndex(List<View> list, View view, int i) {
        List<View> views = list;
        View view2 = view;
        int maxIndex = i;
        List<View> list2 = views;
        View view3 = view2;
        int i2 = maxIndex;
        for (int i3 = 0; i3 < maxIndex; i3++) {
            if (views.get(i3) == view2) {
                return true;
            }
        }
        return false;
    }

    public static void setEpicenter(Object obj, View view) {
        Object transitionObj = obj;
        View view2 = view;
        Object obj2 = transitionObj;
        View view3 = view2;
        if (view2 != null) {
            Transition transition = (Transition) transitionObj;
            Rect epicenter = new Rect();
            getBoundsOnScreen(view2, epicenter);
            final Rect rect = epicenter;
            transition.setEpicenterCallback(new EpicenterCallback() {
                public Rect onGetEpicenter(Transition transition) {
                    Transition transition2 = transition;
                    return rect;
                }
            });
        }
    }

    public static void getBoundsOnScreen(View view, Rect rect) {
        View view2 = view;
        Rect epicenter = rect;
        View view3 = view2;
        Rect rect2 = epicenter;
        int[] loc = new int[2];
        view2.getLocationOnScreen(loc);
        epicenter.set(loc[0], loc[1], loc[0] + view2.getWidth(), loc[1] + view2.getHeight());
    }

    public static void addTargets(Object obj, ArrayList<View> arrayList) {
        Object transitionObj = obj;
        ArrayList<View> views = arrayList;
        Object obj2 = transitionObj;
        ArrayList<View> arrayList2 = views;
        Transition transition = (Transition) transitionObj;
        Transition transition2 = transition;
        if (transition != null) {
            if (transition2 instanceof TransitionSet) {
                TransitionSet transitionSet = (TransitionSet) transition2;
                TransitionSet set = transitionSet;
                int numTransitions = transitionSet.getTransitionCount();
                for (int i = 0; i < numTransitions; i++) {
                    Transition transitionAt = set.getTransitionAt(i);
                    Transition transition3 = transitionAt;
                    addTargets(transitionAt, views);
                }
            } else if (!hasSimpleTarget(transition2)) {
                List targets = transition2.getTargets();
                List list = targets;
                if (isNullOrEmpty(targets)) {
                    int numViews = views.size();
                    for (int i2 = 0; i2 < numViews; i2++) {
                        Transition addTarget = transition2.addTarget((View) views.get(i2));
                    }
                }
            }
        }
    }

    private static boolean hasSimpleTarget(Transition transition) {
        Transition transition2 = transition;
        Transition transition3 = transition2;
        return !isNullOrEmpty(transition2.getTargetIds()) || !isNullOrEmpty(transition2.getTargetNames()) || !isNullOrEmpty(transition2.getTargetTypes());
    }

    private static boolean isNullOrEmpty(List list) {
        List list2 = list;
        List list3 = list2;
        return list2 == null || list2.isEmpty();
    }

    public static Object mergeTransitionsTogether(Object obj, Object obj2, Object obj3) {
        Object transition1 = obj;
        Object transition2 = obj2;
        Object transition3 = obj3;
        Object obj4 = transition1;
        Object obj5 = transition2;
        Object obj6 = transition3;
        TransitionSet transitionSet = new TransitionSet();
        if (transition1 != null) {
            TransitionSet addTransition = transitionSet.addTransition((Transition) transition1);
        }
        if (transition2 != null) {
            TransitionSet addTransition2 = transitionSet.addTransition((Transition) transition2);
        }
        if (transition3 != null) {
            TransitionSet addTransition3 = transitionSet.addTransition((Transition) transition3);
        }
        return transitionSet;
    }

    public static void scheduleHideFragmentView(Object obj, View view, ArrayList<View> arrayList) {
        Object exitTransitionObj = obj;
        View fragmentView = view;
        ArrayList<View> exitingViews = arrayList;
        Object obj2 = exitTransitionObj;
        View view2 = fragmentView;
        ArrayList<View> arrayList2 = exitingViews;
        Transition transition = (Transition) exitTransitionObj;
        Transition transition2 = transition;
        final View view3 = fragmentView;
        final ArrayList<View> arrayList3 = exitingViews;
        Transition addListener = transition.addListener(new TransitionListener() {
            {
                ArrayList arrayList = r6;
            }

            public void onTransitionStart(Transition transition) {
                Transition transition2 = transition;
            }

            public void onTransitionEnd(Transition transition) {
                Transition transition2 = transition;
                Transition transition3 = transition2;
                Transition removeListener = transition2.removeListener(this);
                view3.setVisibility(8);
                int numViews = arrayList3.size();
                for (int i = 0; i < numViews; i++) {
                    ((View) arrayList3.get(i)).setVisibility(0);
                }
            }

            public void onTransitionCancel(Transition transition) {
                Transition transition2 = transition;
            }

            public void onTransitionPause(Transition transition) {
                Transition transition2 = transition;
            }

            public void onTransitionResume(Transition transition) {
                Transition transition2 = transition;
            }
        });
    }

    public static Object mergeTransitionsInSequence(Object obj, Object obj2, Object obj3) {
        Object exitTransitionObj = obj;
        Object enterTransitionObj = obj2;
        Object sharedElementTransitionObj = obj3;
        Object obj4 = exitTransitionObj;
        Object obj5 = enterTransitionObj;
        Object obj6 = sharedElementTransitionObj;
        Transition staggered = null;
        Transition exitTransition = (Transition) exitTransitionObj;
        Transition enterTransition = (Transition) enterTransitionObj;
        Transition sharedElementTransition = (Transition) sharedElementTransitionObj;
        if (exitTransition != null && enterTransition != null) {
            staggered = new TransitionSet().addTransition(exitTransition).addTransition(enterTransition).setOrdering(1);
        } else if (exitTransition != null) {
            staggered = exitTransition;
        } else if (enterTransition != null) {
            staggered = enterTransition;
        }
        if (sharedElementTransition == null) {
            return staggered;
        }
        TransitionSet together = new TransitionSet();
        if (staggered != null) {
            TransitionSet addTransition = together.addTransition(staggered);
        }
        TransitionSet addTransition2 = together.addTransition(sharedElementTransition);
        return together;
    }

    public static void beginDelayedTransition(ViewGroup viewGroup, Object obj) {
        ViewGroup sceneRoot = viewGroup;
        Object transition = obj;
        ViewGroup viewGroup2 = sceneRoot;
        Object obj2 = transition;
        TransitionManager.beginDelayedTransition(sceneRoot, (Transition) transition);
    }

    public static ArrayList<String> prepareSetNameOverridesOptimized(ArrayList<View> arrayList) {
        ArrayList<View> sharedElementsIn = arrayList;
        ArrayList<View> arrayList2 = sharedElementsIn;
        ArrayList arrayList3 = new ArrayList();
        int numSharedElements = sharedElementsIn.size();
        for (int i = 0; i < numSharedElements; i++) {
            View view = (View) sharedElementsIn.get(i);
            boolean add = arrayList3.add(view.getTransitionName());
            view.setTransitionName(null);
        }
        return arrayList3;
    }

    public static void setNameOverridesOptimized(View view, ArrayList<View> arrayList, ArrayList<View> arrayList2, ArrayList<String> arrayList3, Map<String, String> map) {
        View sceneRoot = view;
        ArrayList<View> sharedElementsOut = arrayList;
        ArrayList<View> sharedElementsIn = arrayList2;
        ArrayList<String> inNames = arrayList3;
        Map<String, String> nameOverrides = map;
        View view2 = sceneRoot;
        ArrayList<View> arrayList4 = sharedElementsOut;
        ArrayList<View> arrayList5 = sharedElementsIn;
        ArrayList<String> arrayList6 = inNames;
        Map<String, String> map2 = nameOverrides;
        int numSharedElements = sharedElementsIn.size();
        ArrayList arrayList7 = new ArrayList();
        for (int i = 0; i < numSharedElements; i++) {
            View view3 = (View) sharedElementsOut.get(i);
            View view4 = view3;
            String name = view3.getTransitionName();
            boolean add = arrayList7.add(name);
            if (name != null) {
                view4.setTransitionName(null);
                String inName = (String) nameOverrides.get(name);
                int j = 0;
                while (true) {
                    if (j < numSharedElements) {
                        if (inName.equals(inNames.get(j))) {
                            ((View) sharedElementsIn.get(j)).setTransitionName(name);
                            break;
                        }
                        j++;
                    } else {
                        break;
                    }
                }
            }
        }
        final int i2 = numSharedElements;
        final ArrayList<View> arrayList8 = sharedElementsIn;
        final ArrayList<String> arrayList9 = inNames;
        final ArrayList<View> arrayList10 = sharedElementsOut;
        final ArrayList arrayList11 = arrayList7;
        C01093 r0 = new Runnable() {
            {
                ArrayList arrayList = r9;
                ArrayList arrayList2 = r10;
                ArrayList arrayList3 = r11;
                ArrayList arrayList4 = r12;
            }

            public void run() {
                for (int i = 0; i < i2; i++) {
                    ((View) arrayList8.get(i)).setTransitionName((String) arrayList9.get(i));
                    ((View) arrayList10.get(i)).setTransitionName((String) arrayList11.get(i));
                }
            }
        };
        OneShotPreDrawListener add2 = OneShotPreDrawListener.add(sceneRoot, r0);
    }

    public static void captureTransitioningViews(ArrayList<View> arrayList, View view) {
        ArrayList<View> transitioningViews = arrayList;
        View view2 = view;
        ArrayList<View> arrayList2 = transitioningViews;
        View view3 = view2;
        if (view2.getVisibility() == 0) {
            if (!(view2 instanceof ViewGroup)) {
                boolean add = transitioningViews.add(view2);
                return;
            }
            ViewGroup viewGroup = (ViewGroup) view2;
            ViewGroup viewGroup2 = viewGroup;
            if (!viewGroup.isTransitionGroup()) {
                int count = viewGroup2.getChildCount();
                for (int i = 0; i < count; i++) {
                    captureTransitioningViews(transitioningViews, viewGroup2.getChildAt(i));
                }
                return;
            }
            boolean add2 = transitioningViews.add(viewGroup2);
        }
    }

    public static void findNamedViews(Map<String, View> map, View view) {
        Map<String, View> namedViews = map;
        View view2 = view;
        Map<String, View> map2 = namedViews;
        View view3 = view2;
        if (view2.getVisibility() == 0) {
            String transitionName = view2.getTransitionName();
            String transitionName2 = transitionName;
            if (transitionName != null) {
                Object put = namedViews.put(transitionName2, view2);
            }
            if (view2 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view2;
                ViewGroup viewGroup2 = viewGroup;
                int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    findNamedViews(namedViews, viewGroup2.getChildAt(i));
                }
            }
        }
    }

    public static void setNameOverridesUnoptimized(View view, ArrayList<View> arrayList, Map<String, String> map) {
        View sceneRoot = view;
        ArrayList<View> sharedElementsIn = arrayList;
        Map<String, String> nameOverrides = map;
        View view2 = sceneRoot;
        ArrayList<View> arrayList2 = sharedElementsIn;
        Map<String, String> map2 = nameOverrides;
        final ArrayList<View> arrayList3 = sharedElementsIn;
        final Map<String, String> map3 = nameOverrides;
        OneShotPreDrawListener add = OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            {
                Map map = r6;
            }

            public void run() {
                int numSharedElements = arrayList3.size();
                for (int i = 0; i < numSharedElements; i++) {
                    View view = (View) arrayList3.get(i);
                    View view2 = view;
                    String transitionName = view.getTransitionName();
                    String name = transitionName;
                    if (transitionName != null) {
                        view2.setTransitionName(FragmentTransitionCompat21.access$000(map3, name));
                    }
                }
            }
        });
    }

    private static String findKeyForValue(Map<String, String> map, String str) {
        Map<String, String> map2 = map;
        String value = str;
        Map<String, String> map3 = map2;
        String str2 = value;
        for (Entry entry : map2.entrySet()) {
            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public static void scheduleRemoveTargets(Object obj, Object obj2, ArrayList<View> arrayList, Object obj3, ArrayList<View> arrayList2, Object obj4, ArrayList<View> arrayList3) {
        Object overallTransitionObj = obj;
        Object enterTransition = obj2;
        ArrayList<View> enteringViews = arrayList;
        Object exitTransition = obj3;
        ArrayList<View> exitingViews = arrayList2;
        Object sharedElementTransition = obj4;
        ArrayList<View> sharedElementsIn = arrayList3;
        Object obj5 = overallTransitionObj;
        Object obj6 = enterTransition;
        ArrayList<View> arrayList4 = enteringViews;
        Object obj7 = exitTransition;
        ArrayList<View> arrayList5 = exitingViews;
        Object obj8 = sharedElementTransition;
        ArrayList<View> arrayList6 = sharedElementsIn;
        Transition transition = (Transition) overallTransitionObj;
        Transition transition2 = transition;
        Transition transition3 = transition;
        final Object obj9 = enterTransition;
        final ArrayList<View> arrayList7 = enteringViews;
        final Object obj10 = exitTransition;
        final ArrayList<View> arrayList8 = exitingViews;
        final Object obj11 = sharedElementTransition;
        final ArrayList<View> arrayList9 = sharedElementsIn;
        C01115 r0 = new TransitionListener() {
            {
                ArrayList arrayList = r10;
                Object obj = r11;
                ArrayList arrayList2 = r12;
                Object obj2 = r13;
                ArrayList arrayList3 = r14;
            }

            public void onTransitionStart(Transition transition) {
                Transition transition2 = transition;
                if (obj9 != null) {
                    FragmentTransitionCompat21.replaceTargets(obj9, arrayList7, null);
                }
                if (obj10 != null) {
                    FragmentTransitionCompat21.replaceTargets(obj10, arrayList8, null);
                }
                if (obj11 != null) {
                    FragmentTransitionCompat21.replaceTargets(obj11, arrayList9, null);
                }
            }

            public void onTransitionEnd(Transition transition) {
                Transition transition2 = transition;
            }

            public void onTransitionCancel(Transition transition) {
                Transition transition2 = transition;
            }

            public void onTransitionPause(Transition transition) {
                Transition transition2 = transition;
            }

            public void onTransitionResume(Transition transition) {
                Transition transition2 = transition;
            }
        };
        Transition addListener = transition3.addListener(r0);
    }

    public static void swapSharedElementTargets(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        Object sharedElementTransitionObj = obj;
        ArrayList<View> sharedElementsOut = arrayList;
        ArrayList<View> sharedElementsIn = arrayList2;
        Object obj2 = sharedElementTransitionObj;
        ArrayList<View> arrayList3 = sharedElementsOut;
        ArrayList<View> arrayList4 = sharedElementsIn;
        TransitionSet transitionSet = (TransitionSet) sharedElementTransitionObj;
        TransitionSet sharedElementTransition = transitionSet;
        if (transitionSet != null) {
            sharedElementTransition.getTargets().clear();
            boolean addAll = sharedElementTransition.getTargets().addAll(sharedElementsIn);
            replaceTargets(sharedElementTransition, sharedElementsOut, sharedElementsIn);
        }
    }

    public static void replaceTargets(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        Object transitionObj = obj;
        ArrayList<View> oldTargets = arrayList;
        ArrayList<View> newTargets = arrayList2;
        Object obj2 = transitionObj;
        ArrayList<View> arrayList3 = oldTargets;
        ArrayList<View> arrayList4 = newTargets;
        Transition transition = (Transition) transitionObj;
        Transition transition2 = transition;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition2;
            TransitionSet set = transitionSet;
            int numTransitions = transitionSet.getTransitionCount();
            for (int i = 0; i < numTransitions; i++) {
                Transition transitionAt = set.getTransitionAt(i);
                Transition transition3 = transitionAt;
                replaceTargets(transitionAt, oldTargets, newTargets);
            }
        } else if (!hasSimpleTarget(transition2)) {
            List targets = transition2.getTargets();
            List list = targets;
            if (targets != null && list.size() == oldTargets.size() && list.containsAll(oldTargets)) {
                int targetCount = newTargets != null ? newTargets.size() : 0;
                for (int i2 = 0; i2 < targetCount; i2++) {
                    Transition addTarget = transition2.addTarget((View) newTargets.get(i2));
                }
                for (int i3 = oldTargets.size() - 1; i3 >= 0; i3--) {
                    Transition removeTarget = transition2.removeTarget((View) oldTargets.get(i3));
                }
            }
        }
    }

    public static void addTarget(Object obj, View view) {
        Object transitionObj = obj;
        View view2 = view;
        Object obj2 = transitionObj;
        View view3 = view2;
        if (transitionObj != null) {
            Transition transition = (Transition) transitionObj;
            Transition transition2 = transition;
            Transition addTarget = transition.addTarget(view2);
        }
    }

    public static void removeTarget(Object obj, View view) {
        Object transitionObj = obj;
        View view2 = view;
        Object obj2 = transitionObj;
        View view3 = view2;
        if (transitionObj != null) {
            Transition transition = (Transition) transitionObj;
            Transition transition2 = transition;
            Transition removeTarget = transition.removeTarget(view2);
        }
    }

    public static void setEpicenter(Object obj, Rect rect) {
        Object transitionObj = obj;
        Rect epicenter = rect;
        Object obj2 = transitionObj;
        Rect rect2 = epicenter;
        if (transitionObj != null) {
            Transition transition = (Transition) transitionObj;
            Transition transition2 = transition;
            final Rect rect3 = epicenter;
            transition.setEpicenterCallback(new EpicenterCallback() {
                public Rect onGetEpicenter(Transition transition) {
                    Transition transition2 = transition;
                    if (rect3 != null && !rect3.isEmpty()) {
                        return rect3;
                    }
                    return null;
                }
            });
        }
    }

    public static void scheduleNameReset(ViewGroup viewGroup, ArrayList<View> arrayList, Map<String, String> map) {
        ViewGroup sceneRoot = viewGroup;
        ArrayList<View> sharedElementsIn = arrayList;
        Map<String, String> nameOverrides = map;
        ViewGroup viewGroup2 = sceneRoot;
        ArrayList<View> arrayList2 = sharedElementsIn;
        Map<String, String> map2 = nameOverrides;
        final ArrayList<View> arrayList3 = sharedElementsIn;
        final Map<String, String> map3 = nameOverrides;
        OneShotPreDrawListener add = OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            {
                Map map = r6;
            }

            public void run() {
                int numSharedElements = arrayList3.size();
                for (int i = 0; i < numSharedElements; i++) {
                    View view = (View) arrayList3.get(i);
                    view.setTransitionName((String) map3.get(view.getTransitionName()));
                }
            }
        });
    }
}
