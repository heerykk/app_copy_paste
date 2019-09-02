package android.support.p000v4.app;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.p000v4.util.ArrayMap;
import android.support.p000v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;

/* renamed from: android.support.v4.app.FragmentTransition */
class FragmentTransition {
    private static final int[] INVERSE_OPS;

    /* renamed from: android.support.v4.app.FragmentTransition$FragmentContainerTransition */
    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

    FragmentTransition() {
    }

    static /* synthetic */ void access$000(ArrayList arrayList, int i) {
        ArrayList x0 = arrayList;
        int x1 = i;
        ArrayList arrayList2 = x0;
        int i2 = x1;
        setViewVisibility(x0, x1);
    }

    static /* synthetic */ ArrayList access$100(Object obj, Fragment fragment, ArrayList arrayList, View view) {
        Object x0 = obj;
        Fragment x1 = fragment;
        ArrayList x2 = arrayList;
        View x3 = view;
        Object obj2 = x0;
        Fragment fragment2 = x1;
        ArrayList arrayList2 = x2;
        View view2 = x3;
        return configureEnteringExitingViews(x0, x1, x2, x3);
    }

    static /* synthetic */ void access$200(Fragment fragment, Fragment fragment2, boolean z, ArrayMap arrayMap, boolean z2) {
        Fragment x0 = fragment;
        Fragment x1 = fragment2;
        ArrayMap x3 = arrayMap;
        Fragment fragment3 = x0;
        Fragment fragment4 = x1;
        ArrayMap arrayMap2 = x3;
        callSharedElementStartEnd(x0, x1, z, x3, z2);
    }

    static /* synthetic */ ArrayMap access$300(ArrayMap arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        ArrayMap x0 = arrayMap;
        Object x1 = obj;
        FragmentContainerTransition x2 = fragmentContainerTransition;
        ArrayMap arrayMap2 = x0;
        Object obj2 = x1;
        FragmentContainerTransition fragmentContainerTransition2 = x2;
        return captureInSharedElements(x0, x1, x2);
    }

    static /* synthetic */ View access$400(ArrayMap arrayMap, FragmentContainerTransition fragmentContainerTransition, Object obj, boolean z) {
        ArrayMap x0 = arrayMap;
        FragmentContainerTransition x1 = fragmentContainerTransition;
        Object x2 = obj;
        ArrayMap arrayMap2 = x0;
        FragmentContainerTransition fragmentContainerTransition2 = x1;
        Object obj2 = x2;
        return getInEpicenterView(x0, x1, x2, z);
    }

    static {
        int[] iArr = new int[8];
        iArr[0] = 0;
        iArr[1] = 3;
        iArr[2] = 0;
        iArr[3] = 1;
        iArr[4] = 5;
        iArr[5] = 4;
        iArr[6] = 7;
        iArr[7] = 6;
        INVERSE_OPS = iArr;
    }

    static void startTransitions(FragmentManagerImpl fragmentManagerImpl, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z) {
        FragmentManagerImpl fragmentManager = fragmentManagerImpl;
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        int startIndex = i;
        int endIndex = i2;
        FragmentManagerImpl fragmentManagerImpl2 = fragmentManager;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        int i3 = startIndex;
        int i4 = endIndex;
        boolean isOptimized = z;
        if (fragmentManager.mCurState >= 1 && VERSION.SDK_INT >= 21) {
            SparseArray sparseArray = new SparseArray();
            for (int i5 = startIndex; i5 < endIndex; i5++) {
                BackStackRecord record = (BackStackRecord) records.get(i5);
                boolean booleanValue = ((Boolean) isRecordPop.get(i5)).booleanValue();
                boolean z2 = booleanValue;
                if (!booleanValue) {
                    calculateFragments(record, sparseArray, isOptimized);
                } else {
                    calculatePopFragments(record, sparseArray, isOptimized);
                }
            }
            if (sparseArray.size() != 0) {
                View view = new View(fragmentManager.mHost.getContext());
                View nonExistentView = view;
                int numContainers = sparseArray.size();
                for (int i6 = 0; i6 < numContainers; i6++) {
                    int keyAt = sparseArray.keyAt(i6);
                    int containerId = keyAt;
                    ArrayMap calculateNameOverrides = calculateNameOverrides(keyAt, records, isRecordPop, startIndex, endIndex);
                    FragmentContainerTransition containerTransition = (FragmentContainerTransition) sparseArray.valueAt(i6);
                    if (!isOptimized) {
                        configureTransitionsUnoptimized(fragmentManager, containerId, containerTransition, nonExistentView, calculateNameOverrides);
                    } else {
                        configureTransitionsOptimized(fragmentManager, containerId, containerTransition, nonExistentView, calculateNameOverrides);
                    }
                }
            }
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int i, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList<String> targets;
        ArrayList<String> sources;
        int containerId = i;
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        int startIndex = i2;
        int endIndex = i3;
        int i4 = containerId;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        int i5 = startIndex;
        int i6 = endIndex;
        ArrayMap arrayMap = new ArrayMap();
        for (int recordNum = endIndex - 1; recordNum >= startIndex; recordNum--) {
            BackStackRecord backStackRecord = (BackStackRecord) records.get(recordNum);
            BackStackRecord record = backStackRecord;
            if (backStackRecord.interactsWith(containerId)) {
                boolean isPop = ((Boolean) isRecordPop.get(recordNum)).booleanValue();
                if (record.mSharedElementSourceNames != null) {
                    int numSharedElements = record.mSharedElementSourceNames.size();
                    if (!isPop) {
                        sources = record.mSharedElementSourceNames;
                        targets = record.mSharedElementTargetNames;
                    } else {
                        targets = record.mSharedElementSourceNames;
                        sources = record.mSharedElementTargetNames;
                    }
                    for (int i7 = 0; i7 < numSharedElements; i7++) {
                        String sourceName = (String) sources.get(i7);
                        String targetName = (String) targets.get(i7);
                        String str = (String) arrayMap.remove(targetName);
                        String previousTarget = str;
                        if (str == null) {
                            Object put = arrayMap.put(sourceName, targetName);
                        } else {
                            Object put2 = arrayMap.put(sourceName, previousTarget);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    private static void configureTransitionsOptimized(FragmentManagerImpl fragmentManagerImpl, int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap) {
        FragmentManagerImpl fragmentManager = fragmentManagerImpl;
        int containerId = i;
        FragmentContainerTransition fragments = fragmentContainerTransition;
        View nonExistentView = view;
        ArrayMap<String, String> nameOverrides = arrayMap;
        FragmentManagerImpl fragmentManagerImpl2 = fragmentManager;
        int i2 = containerId;
        FragmentContainerTransition fragmentContainerTransition2 = fragments;
        View view2 = nonExistentView;
        ArrayMap<String, String> arrayMap2 = nameOverrides;
        ViewGroup sceneRoot = null;
        if (fragmentManager.mContainer.onHasView()) {
            sceneRoot = (ViewGroup) fragmentManager.mContainer.onFindViewById(containerId);
        }
        if (sceneRoot != null) {
            Fragment inFragment = fragments.lastIn;
            Fragment outFragment = fragments.firstOut;
            boolean inIsPop = fragments.lastInIsPop;
            boolean z = fragments.firstOutIsPop;
            boolean z2 = z;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Object enterTransition = getEnterTransition(inFragment, inIsPop);
            Object exitTransition = getExitTransition(outFragment, z);
            Object sharedElementTransition = configureSharedElementsOptimized(sceneRoot, nonExistentView, nameOverrides, fragments, arrayList2, arrayList, enterTransition, exitTransition);
            if (enterTransition != null || sharedElementTransition != null || exitTransition != null) {
                ArrayList configureEnteringExitingViews = configureEnteringExitingViews(exitTransition, outFragment, arrayList2, nonExistentView);
                ArrayList configureEnteringExitingViews2 = configureEnteringExitingViews(enterTransition, inFragment, arrayList, nonExistentView);
                ArrayList arrayList3 = configureEnteringExitingViews2;
                setViewVisibility(configureEnteringExitingViews2, 4);
                Object mergeTransitions = mergeTransitions(enterTransition, exitTransition, sharedElementTransition, inFragment, inIsPop);
                Object transition = mergeTransitions;
                if (mergeTransitions != null) {
                    replaceHide(exitTransition, outFragment, configureEnteringExitingViews);
                    ArrayList prepareSetNameOverridesOptimized = FragmentTransitionCompat21.prepareSetNameOverridesOptimized(arrayList);
                    FragmentTransitionCompat21.scheduleRemoveTargets(transition, enterTransition, arrayList3, exitTransition, configureEnteringExitingViews, sharedElementTransition, arrayList);
                    FragmentTransitionCompat21.beginDelayedTransition(sceneRoot, transition);
                    FragmentTransitionCompat21.setNameOverridesOptimized(sceneRoot, arrayList2, arrayList, prepareSetNameOverridesOptimized, nameOverrides);
                    setViewVisibility(arrayList3, 0);
                    FragmentTransitionCompat21.swapSharedElementTargets(sharedElementTransition, arrayList2, arrayList);
                }
            }
        }
    }

    private static void replaceHide(Object obj, Fragment fragment, ArrayList<View> arrayList) {
        Object exitTransition = obj;
        Fragment exitingFragment = fragment;
        ArrayList<View> exitingViews = arrayList;
        Object obj2 = exitTransition;
        Fragment fragment2 = exitingFragment;
        ArrayList<View> arrayList2 = exitingViews;
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            FragmentTransitionCompat21.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), exitingViews);
            ViewGroup viewGroup = exitingFragment.mContainer;
            ViewGroup viewGroup2 = viewGroup;
            final ArrayList<View> arrayList3 = exitingViews;
            OneShotPreDrawListener add = OneShotPreDrawListener.add(viewGroup, new Runnable() {
                public void run() {
                    FragmentTransition.access$000(arrayList3, 4);
                }
            });
        }
    }

    private static void configureTransitionsUnoptimized(FragmentManagerImpl fragmentManagerImpl, int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap) {
        FragmentManagerImpl fragmentManager = fragmentManagerImpl;
        int containerId = i;
        FragmentContainerTransition fragments = fragmentContainerTransition;
        View nonExistentView = view;
        ArrayMap<String, String> nameOverrides = arrayMap;
        FragmentManagerImpl fragmentManagerImpl2 = fragmentManager;
        int i2 = containerId;
        FragmentContainerTransition fragmentContainerTransition2 = fragments;
        View view2 = nonExistentView;
        ArrayMap<String, String> arrayMap2 = nameOverrides;
        ViewGroup sceneRoot = null;
        if (fragmentManager.mContainer.onHasView()) {
            sceneRoot = (ViewGroup) fragmentManager.mContainer.onFindViewById(containerId);
        }
        if (sceneRoot != null) {
            Fragment inFragment = fragments.lastIn;
            Fragment outFragment = fragments.firstOut;
            boolean inIsPop = fragments.lastInIsPop;
            boolean z = fragments.firstOutIsPop;
            boolean z2 = z;
            Object enterTransition = getEnterTransition(inFragment, inIsPop);
            Object exitTransition = getExitTransition(outFragment, z);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Object sharedElementTransition = configureSharedElementsUnoptimized(sceneRoot, nonExistentView, nameOverrides, fragments, arrayList, arrayList2, enterTransition, exitTransition);
            if (enterTransition != null || sharedElementTransition != null || exitTransition != null) {
                ArrayList configureEnteringExitingViews = configureEnteringExitingViews(exitTransition, outFragment, arrayList, nonExistentView);
                ArrayList arrayList3 = configureEnteringExitingViews;
                if (configureEnteringExitingViews == null || arrayList3.isEmpty()) {
                    exitTransition = null;
                }
                FragmentTransitionCompat21.addTarget(enterTransition, nonExistentView);
                Object mergeTransitions = mergeTransitions(enterTransition, exitTransition, sharedElementTransition, inFragment, fragments.lastInIsPop);
                Object transition = mergeTransitions;
                if (mergeTransitions != null) {
                    ArrayList arrayList4 = new ArrayList();
                    FragmentTransitionCompat21.scheduleRemoveTargets(transition, enterTransition, arrayList4, exitTransition, arrayList3, sharedElementTransition, arrayList2);
                    scheduleTargetChange(sceneRoot, inFragment, nonExistentView, arrayList2, enterTransition, arrayList4, exitTransition, arrayList3);
                    FragmentTransitionCompat21.setNameOverridesUnoptimized(sceneRoot, arrayList2, nameOverrides);
                    FragmentTransitionCompat21.beginDelayedTransition(sceneRoot, transition);
                    FragmentTransitionCompat21.scheduleNameReset(sceneRoot, arrayList2, nameOverrides);
                }
            }
        }
    }

    private static void scheduleTargetChange(ViewGroup viewGroup, Fragment fragment, View view, ArrayList<View> arrayList, Object obj, ArrayList<View> arrayList2, Object obj2, ArrayList<View> arrayList3) {
        ViewGroup sceneRoot = viewGroup;
        Fragment inFragment = fragment;
        View nonExistentView = view;
        ArrayList<View> sharedElementsIn = arrayList;
        Object enterTransition = obj;
        ArrayList<View> enteringViews = arrayList2;
        Object exitTransition = obj2;
        ArrayList<View> exitingViews = arrayList3;
        ViewGroup viewGroup2 = sceneRoot;
        Fragment fragment2 = inFragment;
        View view2 = nonExistentView;
        ArrayList<View> arrayList4 = sharedElementsIn;
        Object obj3 = enterTransition;
        ArrayList<View> arrayList5 = enteringViews;
        Object obj4 = exitTransition;
        ArrayList<View> arrayList6 = exitingViews;
        final Object obj5 = enterTransition;
        final View view3 = nonExistentView;
        final Fragment fragment3 = inFragment;
        final ArrayList<View> arrayList7 = sharedElementsIn;
        final ArrayList<View> arrayList8 = enteringViews;
        final ArrayList<View> arrayList9 = exitingViews;
        final Object obj6 = exitTransition;
        C01042 r0 = new Runnable() {
            {
                View view = r11;
                Fragment fragment = r12;
                ArrayList arrayList = r13;
                ArrayList arrayList2 = r14;
                ArrayList arrayList3 = r15;
                Object obj = r16;
            }

            public void run() {
                if (obj5 != null) {
                    FragmentTransitionCompat21.removeTarget(obj5, view3);
                    boolean addAll = arrayList8.addAll(FragmentTransition.access$100(obj5, fragment3, arrayList7, view3));
                }
                if (arrayList9 != null) {
                    if (obj6 != null) {
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = arrayList;
                        boolean add = arrayList.add(view3);
                        FragmentTransitionCompat21.replaceTargets(obj6, arrayList9, arrayList2);
                    }
                    arrayList9.clear();
                    boolean add2 = arrayList9.add(view3);
                }
            }
        };
        OneShotPreDrawListener add = OneShotPreDrawListener.add(sceneRoot, r0);
    }

    private static Object getSharedElementTransition(Fragment fragment, Fragment fragment2, boolean z) {
        Object sharedElementReturnTransition;
        Fragment inFragment = fragment;
        Fragment outFragment = fragment2;
        Fragment fragment3 = inFragment;
        Fragment fragment4 = outFragment;
        boolean isPop = z;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (!isPop) {
            sharedElementReturnTransition = inFragment.getSharedElementEnterTransition();
        } else {
            sharedElementReturnTransition = outFragment.getSharedElementReturnTransition();
        }
        Object cloneTransition = FragmentTransitionCompat21.cloneTransition(sharedElementReturnTransition);
        Object obj = cloneTransition;
        return FragmentTransitionCompat21.wrapTransitionInSet(cloneTransition);
    }

    private static Object getEnterTransition(Fragment fragment, boolean z) {
        Object reenterTransition;
        Fragment inFragment = fragment;
        Fragment fragment2 = inFragment;
        boolean isPop = z;
        if (inFragment == null) {
            return null;
        }
        if (!isPop) {
            reenterTransition = inFragment.getEnterTransition();
        } else {
            reenterTransition = inFragment.getReenterTransition();
        }
        return FragmentTransitionCompat21.cloneTransition(reenterTransition);
    }

    private static Object getExitTransition(Fragment fragment, boolean z) {
        Object returnTransition;
        Fragment outFragment = fragment;
        Fragment fragment2 = outFragment;
        boolean isPop = z;
        if (outFragment == null) {
            return null;
        }
        if (!isPop) {
            returnTransition = outFragment.getExitTransition();
        } else {
            returnTransition = outFragment.getReturnTransition();
        }
        return FragmentTransitionCompat21.cloneTransition(returnTransition);
    }

    private static Object configureSharedElementsOptimized(ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        Rect epicenter;
        View epicenterView;
        ViewGroup sceneRoot = viewGroup;
        View nonExistentView = view;
        ArrayMap<String, String> nameOverrides = arrayMap;
        FragmentContainerTransition fragments = fragmentContainerTransition;
        ArrayList<View> sharedElementsOut = arrayList;
        ArrayList<View> sharedElementsIn = arrayList2;
        Object enterTransition = obj;
        Object exitTransition = obj2;
        ViewGroup viewGroup2 = sceneRoot;
        View view2 = nonExistentView;
        ArrayMap<String, String> arrayMap2 = nameOverrides;
        FragmentContainerTransition fragmentContainerTransition2 = fragments;
        ArrayList<View> arrayList3 = sharedElementsOut;
        ArrayList<View> arrayList4 = sharedElementsIn;
        Object obj4 = enterTransition;
        Object obj5 = exitTransition;
        Fragment inFragment = fragments.lastIn;
        Fragment outFragment = fragments.firstOut;
        if (inFragment != null) {
            inFragment.getView().setVisibility(0);
        }
        if (inFragment == null || outFragment == null) {
            return null;
        }
        boolean inIsPop = fragments.lastInIsPop;
        if (!nameOverrides.isEmpty()) {
            obj3 = getSharedElementTransition(inFragment, outFragment, inIsPop);
        } else {
            obj3 = null;
        }
        Object sharedElementTransition = obj3;
        ArrayMap captureOutSharedElements = captureOutSharedElements(nameOverrides, sharedElementTransition, fragments);
        ArrayMap captureInSharedElements = captureInSharedElements(nameOverrides, sharedElementTransition, fragments);
        if (!nameOverrides.isEmpty()) {
            addSharedElementsWithMatchingNames(sharedElementsOut, captureOutSharedElements, nameOverrides.keySet());
            addSharedElementsWithMatchingNames(sharedElementsIn, captureInSharedElements, nameOverrides.values());
        } else {
            sharedElementTransition = null;
            if (captureOutSharedElements != null) {
                captureOutSharedElements.clear();
            }
            if (captureInSharedElements != null) {
                captureInSharedElements.clear();
            }
        }
        if (enterTransition == null && exitTransition == null && sharedElementTransition == null) {
            return null;
        }
        callSharedElementStartEnd(inFragment, outFragment, inIsPop, captureOutSharedElements, true);
        if (sharedElementTransition == null) {
            epicenter = null;
            epicenterView = null;
        } else {
            boolean add = sharedElementsIn.add(nonExistentView);
            FragmentTransitionCompat21.setSharedElementTargets(sharedElementTransition, nonExistentView, sharedElementsOut);
            boolean z = fragments.firstOutIsPop;
            boolean z2 = z;
            setOutEpicenter(sharedElementTransition, exitTransition, captureOutSharedElements, z, fragments.firstOutTransaction);
            epicenter = new Rect();
            View inEpicenterView = getInEpicenterView(captureInSharedElements, fragments, enterTransition, inIsPop);
            epicenterView = inEpicenterView;
            if (inEpicenterView != null) {
                FragmentTransitionCompat21.setEpicenter(enterTransition, epicenter);
            }
        }
        final Fragment fragment = inFragment;
        final Fragment fragment2 = outFragment;
        final boolean z3 = inIsPop;
        final ArrayMap arrayMap3 = captureInSharedElements;
        final View view3 = epicenterView;
        final Rect rect = epicenter;
        OneShotPreDrawListener add2 = OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            {
                Fragment fragment = r12;
                ArrayMap arrayMap = r14;
                View view = r15;
                Rect rect = r16;
                boolean z = r13;
            }

            public void run() {
                FragmentTransition.access$200(fragment, fragment2, z3, arrayMap3, false);
                if (view3 != null) {
                    FragmentTransitionCompat21.getBoundsOnScreen(view3, rect);
                }
            }
        });
        return sharedElementTransition;
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        ArrayList<View> views = arrayList;
        ArrayMap<String, View> sharedElements = arrayMap;
        Collection<String> nameOverridesSet = collection;
        ArrayList<View> arrayList2 = views;
        ArrayMap<String, View> arrayMap2 = sharedElements;
        Collection<String> collection2 = nameOverridesSet;
        for (int i = sharedElements.size() - 1; i >= 0; i--) {
            View view = (View) sharedElements.valueAt(i);
            if (nameOverridesSet.contains(ViewCompat.getTransitionName(view))) {
                boolean add = views.add(view);
            }
        }
    }

    private static Object configureSharedElementsUnoptimized(ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        Rect inEpicenter;
        ViewGroup sceneRoot = viewGroup;
        View nonExistentView = view;
        ArrayMap<String, String> nameOverrides = arrayMap;
        FragmentContainerTransition fragments = fragmentContainerTransition;
        ArrayList<View> sharedElementsOut = arrayList;
        ArrayList<View> sharedElementsIn = arrayList2;
        Object enterTransition = obj;
        Object exitTransition = obj2;
        ViewGroup viewGroup2 = sceneRoot;
        View view2 = nonExistentView;
        ArrayMap<String, String> arrayMap2 = nameOverrides;
        FragmentContainerTransition fragmentContainerTransition2 = fragments;
        ArrayList<View> arrayList3 = sharedElementsOut;
        ArrayList<View> arrayList4 = sharedElementsIn;
        Object obj4 = enterTransition;
        Object obj5 = exitTransition;
        Fragment inFragment = fragments.lastIn;
        Fragment outFragment = fragments.firstOut;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        boolean inIsPop = fragments.lastInIsPop;
        if (!nameOverrides.isEmpty()) {
            obj3 = getSharedElementTransition(inFragment, outFragment, inIsPop);
        } else {
            obj3 = null;
        }
        Object sharedElementTransition = obj3;
        ArrayMap captureOutSharedElements = captureOutSharedElements(nameOverrides, sharedElementTransition, fragments);
        if (!nameOverrides.isEmpty()) {
            boolean addAll = sharedElementsOut.addAll(captureOutSharedElements.values());
        } else {
            sharedElementTransition = null;
        }
        if (enterTransition == null && exitTransition == null && sharedElementTransition == null) {
            return null;
        }
        callSharedElementStartEnd(inFragment, outFragment, inIsPop, captureOutSharedElements, true);
        if (sharedElementTransition == null) {
            inEpicenter = null;
        } else {
            inEpicenter = new Rect();
            FragmentTransitionCompat21.setSharedElementTargets(sharedElementTransition, nonExistentView, sharedElementsOut);
            boolean z = fragments.firstOutIsPop;
            boolean z2 = z;
            setOutEpicenter(sharedElementTransition, exitTransition, captureOutSharedElements, z, fragments.firstOutTransaction);
            if (enterTransition != null) {
                FragmentTransitionCompat21.setEpicenter(enterTransition, inEpicenter);
            }
        }
        final ArrayMap<String, String> arrayMap3 = nameOverrides;
        final Object obj6 = sharedElementTransition;
        final FragmentContainerTransition fragmentContainerTransition3 = fragments;
        final ArrayList<View> arrayList5 = sharedElementsIn;
        final View view3 = nonExistentView;
        final Fragment fragment = inFragment;
        final Fragment fragment2 = outFragment;
        final boolean z3 = inIsPop;
        final ArrayList<View> arrayList6 = sharedElementsOut;
        final Object obj7 = enterTransition;
        final Rect rect = inEpicenter;
        C01064 r0 = new Runnable() {
            {
                Object obj = r17;
                FragmentContainerTransition fragmentContainerTransition = r18;
                ArrayList arrayList = r19;
                View view = r20;
                Fragment fragment = r21;
                Fragment fragment2 = r22;
                ArrayList arrayList2 = r24;
                Object obj2 = r25;
                Rect rect = r26;
                boolean z = r23;
            }

            public void run() {
                ArrayMap access$300 = FragmentTransition.access$300(arrayMap3, obj6, fragmentContainerTransition3);
                ArrayMap arrayMap = access$300;
                if (access$300 != null) {
                    boolean addAll = arrayList5.addAll(arrayMap.values());
                    boolean add = arrayList5.add(view3);
                }
                FragmentTransition.access$200(fragment, fragment2, z3, arrayMap, false);
                if (obj6 != null) {
                    FragmentTransitionCompat21.swapSharedElementTargets(obj6, arrayList6, arrayList5);
                    View access$400 = FragmentTransition.access$400(arrayMap, fragmentContainerTransition3, obj7, z3);
                    View inEpicenterView = access$400;
                    if (access$400 != null) {
                        FragmentTransitionCompat21.getBoundsOnScreen(inEpicenterView, rect);
                    }
                }
            }
        };
        OneShotPreDrawListener add = OneShotPreDrawListener.add(sceneRoot, r0);
        return sharedElementTransition;
    }

    private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> names;
        ArrayMap<String, String> nameOverrides = arrayMap;
        Object sharedElementTransition = obj;
        FragmentContainerTransition fragments = fragmentContainerTransition;
        ArrayMap<String, String> arrayMap2 = nameOverrides;
        Object obj2 = sharedElementTransition;
        FragmentContainerTransition fragmentContainerTransition2 = fragments;
        if (!nameOverrides.isEmpty() && sharedElementTransition != null) {
            Fragment outFragment = fragments.firstOut;
            ArrayMap arrayMap3 = new ArrayMap();
            ArrayMap arrayMap4 = arrayMap3;
            FragmentTransitionCompat21.findNamedViews(arrayMap3, outFragment.getView());
            BackStackRecord outTransaction = fragments.firstOutTransaction;
            if (!fragments.firstOutIsPop) {
                sharedElementCallback = outFragment.getExitTransitionCallback();
                names = outTransaction.mSharedElementSourceNames;
            } else {
                sharedElementCallback = outFragment.getEnterTransitionCallback();
                names = outTransaction.mSharedElementTargetNames;
            }
            boolean retainAll = arrayMap4.retainAll(names);
            if (sharedElementCallback == null) {
                boolean retainAll2 = nameOverrides.retainAll(arrayMap4.keySet());
            } else {
                sharedElementCallback.onMapSharedElements(names, arrayMap4);
                for (int i = names.size() - 1; i >= 0; i--) {
                    String name = (String) names.get(i);
                    View view = (View) arrayMap4.get(name);
                    View view2 = view;
                    if (view == null) {
                        Object remove = nameOverrides.remove(name);
                    } else if (!name.equals(ViewCompat.getTransitionName(view2))) {
                        Object put = nameOverrides.put(ViewCompat.getTransitionName(view2), (String) nameOverrides.remove(name));
                    }
                }
            }
            return arrayMap4;
        }
        nameOverrides.clear();
        return null;
    }

    private static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> names;
        ArrayMap<String, String> nameOverrides = arrayMap;
        Object sharedElementTransition = obj;
        FragmentContainerTransition fragments = fragmentContainerTransition;
        ArrayMap<String, String> arrayMap2 = nameOverrides;
        Object obj2 = sharedElementTransition;
        FragmentContainerTransition fragmentContainerTransition2 = fragments;
        Fragment fragment = fragments.lastIn;
        Fragment inFragment = fragment;
        View fragmentView = fragment.getView();
        if (nameOverrides.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            nameOverrides.clear();
            return null;
        }
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = arrayMap3;
        FragmentTransitionCompat21.findNamedViews(arrayMap3, fragmentView);
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (!fragments.lastInIsPop) {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
            names = inTransaction.mSharedElementTargetNames;
        } else {
            sharedElementCallback = inFragment.getExitTransitionCallback();
            names = inTransaction.mSharedElementSourceNames;
        }
        boolean retainAll = arrayMap4.retainAll(names);
        if (sharedElementCallback == null) {
            retainValues(nameOverrides, arrayMap4);
        } else {
            sharedElementCallback.onMapSharedElements(names, arrayMap4);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = (String) names.get(i);
                View view = (View) arrayMap4.get(name);
                View view2 = view;
                if (view == null) {
                    String findKeyForValue = findKeyForValue(nameOverrides, name);
                    String key = findKeyForValue;
                    if (findKeyForValue != null) {
                        Object remove = nameOverrides.remove(key);
                    }
                } else if (!name.equals(ViewCompat.getTransitionName(view2))) {
                    String findKeyForValue2 = findKeyForValue(nameOverrides, name);
                    String key2 = findKeyForValue2;
                    if (findKeyForValue2 != null) {
                        Object put = nameOverrides.put(key2, ViewCompat.getTransitionName(view2));
                    }
                }
            }
        }
        return arrayMap4;
    }

    private static String findKeyForValue(ArrayMap<String, String> arrayMap, String str) {
        ArrayMap<String, String> map = arrayMap;
        String value = str;
        ArrayMap<String, String> arrayMap2 = map;
        String str2 = value;
        int numElements = map.size();
        for (int i = 0; i < numElements; i++) {
            if (value.equals(map.valueAt(i))) {
                return (String) map.keyAt(i);
            }
        }
        return null;
    }

    private static View getInEpicenterView(ArrayMap<String, View> arrayMap, FragmentContainerTransition fragmentContainerTransition, Object obj, boolean z) {
        String str;
        ArrayMap<String, View> inSharedElements = arrayMap;
        FragmentContainerTransition fragments = fragmentContainerTransition;
        Object enterTransition = obj;
        ArrayMap<String, View> arrayMap2 = inSharedElements;
        FragmentContainerTransition fragmentContainerTransition2 = fragments;
        Object obj2 = enterTransition;
        boolean inIsPop = z;
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition == null || inTransaction.mSharedElementSourceNames == null || inTransaction.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        if (!inIsPop) {
            str = (String) inTransaction.mSharedElementTargetNames.get(0);
        } else {
            str = (String) inTransaction.mSharedElementSourceNames.get(0);
        }
        return (View) inSharedElements.get(str);
    }

    private static void setOutEpicenter(Object obj, Object obj2, ArrayMap<String, View> arrayMap, boolean z, BackStackRecord backStackRecord) {
        String str;
        Object sharedElementTransition = obj;
        Object exitTransition = obj2;
        ArrayMap<String, View> outSharedElements = arrayMap;
        BackStackRecord outTransaction = backStackRecord;
        Object obj3 = sharedElementTransition;
        Object obj4 = exitTransition;
        ArrayMap<String, View> arrayMap2 = outSharedElements;
        boolean outIsPop = z;
        BackStackRecord backStackRecord2 = outTransaction;
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            if (!outIsPop) {
                str = (String) outTransaction.mSharedElementSourceNames.get(0);
            } else {
                str = (String) outTransaction.mSharedElementTargetNames.get(0);
            }
            View outEpicenterView = (View) outSharedElements.get(str);
            FragmentTransitionCompat21.setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                FragmentTransitionCompat21.setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    private static void retainValues(ArrayMap<String, String> arrayMap, ArrayMap<String, View> arrayMap2) {
        ArrayMap<String, String> nameOverrides = arrayMap;
        ArrayMap<String, View> namedViews = arrayMap2;
        ArrayMap<String, String> arrayMap3 = nameOverrides;
        ArrayMap<String, View> arrayMap4 = namedViews;
        for (int i = nameOverrides.size() - 1; i >= 0; i--) {
            if (!namedViews.containsKey((String) nameOverrides.valueAt(i))) {
                Object removeAt = nameOverrides.removeAt(i);
            }
        }
    }

    private static void callSharedElementStartEnd(Fragment fragment, Fragment fragment2, boolean z, ArrayMap<String, View> arrayMap, boolean z2) {
        SharedElementCallback enterTransitionCallback;
        Fragment inFragment = fragment;
        Fragment outFragment = fragment2;
        ArrayMap<String, View> sharedElements = arrayMap;
        Fragment fragment3 = inFragment;
        Fragment fragment4 = outFragment;
        ArrayMap<String, View> arrayMap2 = sharedElements;
        boolean isStart = z2;
        if (!z) {
            enterTransitionCallback = inFragment.getEnterTransitionCallback();
        } else {
            enterTransitionCallback = outFragment.getEnterTransitionCallback();
        }
        SharedElementCallback sharedElementCallback = enterTransitionCallback;
        if (sharedElementCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int count = sharedElements != null ? sharedElements.size() : 0;
            for (int i = 0; i < count; i++) {
                boolean add = arrayList2.add(sharedElements.keyAt(i));
                boolean add2 = arrayList.add(sharedElements.valueAt(i));
            }
            if (!isStart) {
                sharedElementCallback.onSharedElementEnd(arrayList2, arrayList, null);
            } else {
                sharedElementCallback.onSharedElementStart(arrayList2, arrayList, null);
            }
        }
    }

    private static ArrayList<View> configureEnteringExitingViews(Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        Object transition = obj;
        Fragment fragment2 = fragment;
        ArrayList<View> sharedElements = arrayList;
        View nonExistentView = view;
        Object obj2 = transition;
        Fragment fragment3 = fragment2;
        ArrayList<View> arrayList2 = sharedElements;
        View view2 = nonExistentView;
        ArrayList arrayList3 = null;
        if (transition != null) {
            arrayList3 = new ArrayList();
            FragmentTransitionCompat21.captureTransitioningViews(arrayList3, fragment2.getView());
            if (sharedElements != null) {
                boolean removeAll = arrayList3.removeAll(sharedElements);
            }
            if (!arrayList3.isEmpty()) {
                boolean add = arrayList3.add(nonExistentView);
                FragmentTransitionCompat21.addTargets(transition, arrayList3);
            }
        }
        return arrayList3;
    }

    private static void setViewVisibility(ArrayList<View> arrayList, int i) {
        ArrayList<View> views = arrayList;
        int visibility = i;
        ArrayList<View> arrayList2 = views;
        int i2 = visibility;
        if (views != null) {
            for (int i3 = views.size() - 1; i3 >= 0; i3--) {
                View view = (View) views.get(i3);
                View view2 = view;
                view.setVisibility(visibility);
            }
        }
    }

    private static Object mergeTransitions(Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        boolean allowReturnTransitionOverlap;
        Object enterTransition = obj;
        Object exitTransition = obj2;
        Object sharedElementTransition = obj3;
        Fragment inFragment = fragment;
        Object obj4 = enterTransition;
        Object obj5 = exitTransition;
        Object obj6 = sharedElementTransition;
        Fragment fragment2 = inFragment;
        boolean isPop = z;
        boolean overlap = true;
        if (!(enterTransition == null || exitTransition == null || inFragment == null)) {
            if (!isPop) {
                allowReturnTransitionOverlap = inFragment.getAllowEnterTransitionOverlap();
            } else {
                allowReturnTransitionOverlap = inFragment.getAllowReturnTransitionOverlap();
            }
            overlap = allowReturnTransitionOverlap;
        }
        if (!overlap) {
            return FragmentTransitionCompat21.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
        }
        return FragmentTransitionCompat21.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition);
    }

    public static void calculateFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) {
        BackStackRecord transaction = backStackRecord;
        SparseArray<FragmentContainerTransition> transitioningFragments = sparseArray;
        BackStackRecord backStackRecord2 = transaction;
        SparseArray<FragmentContainerTransition> sparseArray2 = transitioningFragments;
        boolean isOptimized = z;
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            addToFirstInLastOut(transaction, (C0091Op) transaction.mOps.get(opNum), transitioningFragments, false, isOptimized);
        }
    }

    public static void calculatePopFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) {
        BackStackRecord transaction = backStackRecord;
        SparseArray<FragmentContainerTransition> transitioningFragments = sparseArray;
        BackStackRecord backStackRecord2 = transaction;
        SparseArray<FragmentContainerTransition> sparseArray2 = transitioningFragments;
        boolean isOptimized = z;
        if (transaction.mManager.mContainer.onHasView()) {
            int size = transaction.mOps.size();
            int i = size;
            for (int opNum = size - 1; opNum >= 0; opNum--) {
                addToFirstInLastOut(transaction, (C0091Op) transaction.mOps.get(opNum), transitioningFragments, true, isOptimized);
            }
        }
    }

    private static void addToFirstInLastOut(BackStackRecord backStackRecord, C0091Op op, SparseArray<FragmentContainerTransition> sparseArray, boolean z, boolean z2) {
        boolean setLastIn;
        boolean setFirstOut;
        BackStackRecord transaction = backStackRecord;
        C0091Op op2 = op;
        SparseArray<FragmentContainerTransition> transitioningFragments = sparseArray;
        BackStackRecord backStackRecord2 = transaction;
        C0091Op op3 = op2;
        SparseArray<FragmentContainerTransition> sparseArray2 = transitioningFragments;
        boolean isPop = z;
        boolean isOptimizedTransaction = z2;
        Fragment fragment = op2.fragment;
        Fragment fragment2 = fragment;
        int i = fragment.mContainerId;
        int containerId = i;
        if (i != 0) {
            boolean setLastIn2 = false;
            boolean wasRemoved = false;
            boolean setFirstOut2 = false;
            boolean wasAdded = false;
            switch (!isPop ? op2.cmd : INVERSE_OPS[op2.cmd]) {
                case 1:
                case 7:
                    if (!isOptimizedTransaction) {
                        setLastIn2 = !fragment2.mAdded && !fragment2.mHidden;
                    } else {
                        setLastIn2 = fragment2.mIsNewlyAdded;
                    }
                    wasAdded = true;
                    break;
                case 3:
                case 6:
                    if (!isOptimizedTransaction) {
                        setFirstOut2 = fragment2.mAdded && !fragment2.mHidden;
                    } else {
                        setFirstOut2 = !fragment2.mAdded && fragment2.mView != null && fragment2.mView.getVisibility() == 0 && fragment2.mPostponedAlpha >= 0.0f;
                    }
                    wasRemoved = true;
                    break;
                case 4:
                    if (!isOptimizedTransaction) {
                        setFirstOut = fragment2.mAdded && !fragment2.mHidden;
                    } else {
                        setFirstOut = fragment2.mHiddenChanged && fragment2.mAdded && fragment2.mHidden;
                    }
                    wasRemoved = true;
                    break;
                case 5:
                    if (!isOptimizedTransaction) {
                        setLastIn = fragment2.mHidden;
                    } else {
                        setLastIn = fragment2.mHiddenChanged && !fragment2.mHidden && fragment2.mAdded;
                    }
                    wasAdded = true;
                    break;
            }
            FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) transitioningFragments.get(containerId);
            FragmentContainerTransition containerTransition = fragmentContainerTransition;
            if (setLastIn2) {
                FragmentContainerTransition ensureContainer = ensureContainer(fragmentContainerTransition, transitioningFragments, containerId);
                containerTransition = ensureContainer;
                ensureContainer.lastIn = fragment2;
                containerTransition.lastInIsPop = isPop;
                containerTransition.lastInTransaction = transaction;
            }
            if (!isOptimizedTransaction && wasAdded) {
                if (containerTransition != null && containerTransition.firstOut == fragment2) {
                    containerTransition.firstOut = null;
                }
                FragmentManagerImpl manager = transaction.mManager;
                if (fragment2.mState < 1 && manager.mCurState >= 1 && !transaction.mAllowOptimization) {
                    manager.makeActive(fragment2);
                    manager.moveToState(fragment2, 1, 0, 0, false);
                }
            }
            if (setFirstOut2 && (containerTransition == null || containerTransition.firstOut == null)) {
                FragmentContainerTransition ensureContainer2 = ensureContainer(containerTransition, transitioningFragments, containerId);
                containerTransition = ensureContainer2;
                ensureContainer2.firstOut = fragment2;
                containerTransition.firstOutIsPop = isPop;
                containerTransition.firstOutTransaction = transaction;
            }
            if (!isOptimizedTransaction && wasRemoved && containerTransition != null && containerTransition.lastIn == fragment2) {
                containerTransition.lastIn = null;
            }
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray<FragmentContainerTransition> sparseArray, int i) {
        FragmentContainerTransition containerTransition = fragmentContainerTransition;
        SparseArray<FragmentContainerTransition> transitioningFragments = sparseArray;
        int containerId = i;
        FragmentContainerTransition containerTransition2 = containerTransition;
        SparseArray<FragmentContainerTransition> sparseArray2 = transitioningFragments;
        int i2 = containerId;
        if (containerTransition == null) {
            containerTransition2 = new FragmentContainerTransition();
            transitioningFragments.put(containerId, containerTransition2);
        }
        return containerTransition2;
    }
}
