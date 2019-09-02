package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.util.ArrayMap;
import android.support.p000v4.util.LongSparseArray;
import android.support.p000v4.util.SimpleArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(14)
@RequiresApi(14)
abstract class TransitionPort implements Cloneable {
    static final boolean DBG = false;
    private static final String LOG_TAG = "Transition";
    private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> sRunningAnimators = new ThreadLocal<>();
    ArrayList<Animator> mAnimators = new ArrayList<>();
    boolean mCanRemoveViews = false;
    ArrayList<Animator> mCurrentAnimators = new ArrayList<>();
    long mDuration = -1;
    private TransitionValuesMaps mEndValues = new TransitionValuesMaps();
    private boolean mEnded = false;
    TimeInterpolator mInterpolator = null;
    ArrayList<TransitionListener> mListeners = null;
    private String mName = getClass().getName();
    int mNumInstances = 0;
    TransitionSetPort mParent = null;
    boolean mPaused = false;
    ViewGroup mSceneRoot = null;
    long mStartDelay = -1;
    private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
    ArrayList<View> mTargetChildExcludes = null;
    ArrayList<View> mTargetExcludes = null;
    ArrayList<Integer> mTargetIdChildExcludes = null;
    ArrayList<Integer> mTargetIdExcludes = null;
    ArrayList<Integer> mTargetIds = new ArrayList<>();
    ArrayList<Class> mTargetTypeChildExcludes = null;
    ArrayList<Class> mTargetTypeExcludes = null;
    ArrayList<View> mTargets = new ArrayList<>();

    private static class AnimationInfo {
        String name;
        TransitionValues values;
        View view;
        WindowIdPort windowId;

        AnimationInfo(View view2, String str, WindowIdPort windowIdPort, TransitionValues transitionValues) {
            View view3 = view2;
            String name2 = str;
            WindowIdPort windowId2 = windowIdPort;
            TransitionValues values2 = transitionValues;
            View view4 = view3;
            String str2 = name2;
            WindowIdPort windowIdPort2 = windowId2;
            TransitionValues transitionValues2 = values2;
            this.view = view3;
            this.name = name2;
            this.values = values2;
            this.windowId = windowId2;
        }
    }

    private static class ArrayListManager {
        private ArrayListManager() {
        }

        static <T> ArrayList<T> add(ArrayList<T> arrayList, T t) {
            ArrayList<T> list = arrayList;
            T item = t;
            ArrayList<T> list2 = list;
            T t2 = item;
            if (list == null) {
                list2 = new ArrayList<>();
            }
            if (!list2.contains(item)) {
                boolean add = list2.add(item);
            }
            return list2;
        }

        static <T> ArrayList<T> remove(ArrayList<T> arrayList, T t) {
            ArrayList<T> list = arrayList;
            T item = t;
            ArrayList<T> list2 = list;
            T t2 = item;
            if (list != null) {
                boolean remove = list.remove(item);
                if (list.isEmpty()) {
                    list2 = null;
                }
            }
            return list2;
        }
    }

    public interface TransitionListener {
        void onTransitionCancel(TransitionPort transitionPort);

        void onTransitionEnd(TransitionPort transitionPort);

        void onTransitionPause(TransitionPort transitionPort);

        void onTransitionResume(TransitionPort transitionPort);

        void onTransitionStart(TransitionPort transitionPort);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class TransitionListenerAdapter implements TransitionListener {
        public TransitionListenerAdapter() {
        }

        public void onTransitionStart(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
        }

        public void onTransitionEnd(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
        }

        public void onTransitionCancel(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
        }

        public void onTransitionPause(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
        }

        public void onTransitionResume(TransitionPort transitionPort) {
            TransitionPort transitionPort2 = transitionPort;
        }
    }

    public abstract void captureEndValues(TransitionValues transitionValues);

    public abstract void captureStartValues(TransitionValues transitionValues);

    public TransitionPort() {
    }

    private static ArrayMap<Animator, AnimationInfo> getRunningAnimators() {
        ArrayMap arrayMap = (ArrayMap) sRunningAnimators.get();
        ArrayMap arrayMap2 = arrayMap;
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            sRunningAnimators.set(arrayMap2);
        }
        return arrayMap2;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public TransitionPort setDuration(long j) {
        long duration = j;
        long j2 = duration;
        this.mDuration = duration;
        return this;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    public TransitionPort setStartDelay(long j) {
        long startDelay = j;
        long j2 = startDelay;
        this.mStartDelay = startDelay;
        return this;
    }

    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public TransitionPort setInterpolator(TimeInterpolator timeInterpolator) {
        TimeInterpolator interpolator = timeInterpolator;
        TimeInterpolator timeInterpolator2 = interpolator;
        this.mInterpolator = interpolator;
        return this;
    }

    public String[] getTransitionProperties() {
        return null;
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ViewGroup viewGroup2 = viewGroup;
        TransitionValues transitionValues3 = transitionValues;
        TransitionValues transitionValues4 = transitionValues2;
        return null;
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        View view;
        ViewGroup sceneRoot = viewGroup;
        TransitionValuesMaps startValues = transitionValuesMaps;
        TransitionValuesMaps endValues = transitionValuesMaps2;
        ViewGroup viewGroup2 = sceneRoot;
        TransitionValuesMaps transitionValuesMaps3 = startValues;
        TransitionValuesMaps transitionValuesMaps4 = endValues;
        ArrayMap arrayMap = new ArrayMap((SimpleArrayMap) endValues.viewValues);
        SparseArray sparseArray = new SparseArray(endValues.idValues.size());
        for (int i = 0; i < endValues.idValues.size(); i++) {
            int keyAt = endValues.idValues.keyAt(i);
            int i2 = keyAt;
            sparseArray.put(keyAt, endValues.idValues.valueAt(i));
        }
        LongSparseArray longSparseArray = new LongSparseArray(endValues.itemIdValues.size());
        for (int i3 = 0; i3 < endValues.itemIdValues.size(); i3++) {
            long keyAt2 = endValues.itemIdValues.keyAt(i3);
            long j = keyAt2;
            longSparseArray.put(keyAt2, endValues.itemIdValues.valueAt(i3));
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (View view2 : startValues.viewValues.keySet()) {
            TransitionValues end = null;
            boolean isInListView = false;
            if (view2.getParent() instanceof ListView) {
                isInListView = true;
            }
            if (isInListView) {
                ListView listView = (ListView) view2.getParent();
                ListView parent = listView;
                if (listView.getAdapter().hasStableIds()) {
                    int positionForView = parent.getPositionForView(view2);
                    int i4 = positionForView;
                    long itemIdAtPosition = parent.getItemIdAtPosition(positionForView);
                    long j2 = itemIdAtPosition;
                    TransitionValues start = (TransitionValues) startValues.itemIdValues.get(itemIdAtPosition);
                    longSparseArray.remove(itemIdAtPosition);
                    boolean add = arrayList.add(start);
                    boolean add2 = arrayList2.add(null);
                }
            } else {
                int id = view2.getId();
                int id2 = id;
                TransitionValues start2 = startValues.viewValues.get(view2) == null ? (TransitionValues) startValues.idValues.get(id) : (TransitionValues) startValues.viewValues.get(view2);
                if (endValues.viewValues.get(view2) != null) {
                    end = (TransitionValues) endValues.viewValues.get(view2);
                    Object remove = arrayMap.remove(view2);
                } else if (id != -1) {
                    end = (TransitionValues) endValues.idValues.get(id2);
                    View removeView = null;
                    for (View view3 : arrayMap.keySet()) {
                        View viewToRemove = view3;
                        if (view3.getId() == id2) {
                            removeView = viewToRemove;
                        }
                    }
                    if (removeView != null) {
                        Object remove2 = arrayMap.remove(removeView);
                    }
                }
                sparseArray.remove(id2);
                if (isValidTarget(view2, (long) id2)) {
                    boolean add3 = arrayList.add(start2);
                    boolean add4 = arrayList2.add(end);
                }
            }
        }
        int startItemIdCopySize = startValues.itemIdValues.size();
        for (int i5 = 0; i5 < startItemIdCopySize; i5++) {
            long keyAt3 = startValues.itemIdValues.keyAt(i5);
            long j3 = keyAt3;
            if (isValidTarget(null, keyAt3)) {
                TransitionValues start3 = (TransitionValues) startValues.itemIdValues.get(keyAt3);
                TransitionValues end2 = (TransitionValues) endValues.itemIdValues.get(keyAt3);
                longSparseArray.remove(keyAt3);
                boolean add5 = arrayList.add(start3);
                boolean add6 = arrayList2.add(end2);
            }
        }
        for (View view4 : arrayMap.keySet()) {
            View view5 = view4;
            int id3 = view4.getId();
            int id4 = id3;
            if (isValidTarget(view5, (long) id3)) {
                TransitionValues start4 = (TransitionValues) (startValues.viewValues.get(view5) == null ? startValues.idValues.get(id4) : startValues.viewValues.get(view5));
                TransitionValues end3 = (TransitionValues) arrayMap.get(view5);
                sparseArray.remove(id4);
                boolean add7 = arrayList.add(start4);
                boolean add8 = arrayList2.add(end3);
            }
        }
        int endIdCopySize = sparseArray.size();
        for (int i6 = 0; i6 < endIdCopySize; i6++) {
            int keyAt4 = sparseArray.keyAt(i6);
            int id5 = keyAt4;
            if (isValidTarget(null, (long) keyAt4)) {
                TransitionValues end4 = (TransitionValues) sparseArray.get(id5);
                boolean add9 = arrayList.add((TransitionValues) startValues.idValues.get(id5));
                boolean add10 = arrayList2.add(end4);
            }
        }
        int endItemIdCopySize = longSparseArray.size();
        for (int i7 = 0; i7 < endItemIdCopySize; i7++) {
            long keyAt5 = longSparseArray.keyAt(i7);
            long j4 = keyAt5;
            TransitionValues transitionValues = (TransitionValues) longSparseArray.get(keyAt5);
            boolean add11 = arrayList.add((TransitionValues) startValues.itemIdValues.get(keyAt5));
            boolean add12 = arrayList2.add(transitionValues);
        }
        ArrayMap runningAnimators = getRunningAnimators();
        for (int i8 = 0; i8 < arrayList.size(); i8++) {
            TransitionValues start5 = (TransitionValues) arrayList.get(i8);
            TransitionValues end5 = (TransitionValues) arrayList2.get(i8);
            if (!(start5 == null && end5 == null) && (start5 == null || !start5.equals(end5))) {
                Animator createAnimator = createAnimator(sceneRoot, start5, end5);
                Animator animator = createAnimator;
                if (createAnimator != null) {
                    TransitionValues infoValues = null;
                    if (end5 == null) {
                        view = start5.view;
                    } else {
                        view = end5.view;
                        String[] properties = getTransitionProperties();
                        if (view != null && properties != null && properties.length > 0) {
                            TransitionValues transitionValues2 = new TransitionValues();
                            infoValues = transitionValues2;
                            transitionValues2.view = view;
                            TransitionValues transitionValues3 = (TransitionValues) endValues.viewValues.get(view);
                            TransitionValues newValues = transitionValues3;
                            if (transitionValues3 != null) {
                                for (int j5 = 0; j5 < properties.length; j5++) {
                                    Object put = infoValues.values.put(properties[j5], newValues.values.get(properties[j5]));
                                }
                            }
                            int numExistingAnims = runningAnimators.size();
                            int j6 = 0;
                            while (true) {
                                if (j6 >= numExistingAnims) {
                                    break;
                                }
                                AnimationInfo animationInfo = (AnimationInfo) runningAnimators.get((Animator) runningAnimators.keyAt(j6));
                                AnimationInfo info = animationInfo;
                                if (animationInfo.values != null && info.view == view && (((info.name == null && getName() == null) || info.name.equals(getName())) && info.values.equals(infoValues))) {
                                    animator = null;
                                    break;
                                }
                                j6++;
                            }
                        }
                    }
                    if (animator != null) {
                        Object put2 = runningAnimators.put(animator, new AnimationInfo(view, getName(), WindowIdPort.getWindowId(sceneRoot), infoValues));
                        boolean add13 = this.mAnimators.add(animator);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isValidTarget(View view, long j) {
        View target = view;
        long targetId = j;
        View view2 = target;
        long j2 = targetId;
        if (this.mTargetIdExcludes != null && this.mTargetIdExcludes.contains(Integer.valueOf((int) targetId))) {
            return false;
        }
        if (this.mTargetExcludes != null && this.mTargetExcludes.contains(target)) {
            return false;
        }
        if (!(this.mTargetTypeExcludes == null || target == null)) {
            int numTypes = this.mTargetTypeExcludes.size();
            for (int i = 0; i < numTypes; i++) {
                Class cls = (Class) this.mTargetTypeExcludes.get(i);
                Class cls2 = cls;
                if (cls.isInstance(target)) {
                    return false;
                }
            }
        }
        if (this.mTargetIds.size() == 0 && this.mTargets.size() == 0) {
            return true;
        }
        if (this.mTargetIds.size() > 0) {
            for (int i2 = 0; i2 < this.mTargetIds.size(); i2++) {
                if (((long) ((Integer) this.mTargetIds.get(i2)).intValue()) == targetId) {
                    return true;
                }
            }
        }
        if (target != null && this.mTargets.size() > 0) {
            for (int i3 = 0; i3 < this.mTargets.size(); i3++) {
                if (this.mTargets.get(i3) == target) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void runAnimators() {
        start();
        ArrayMap runningAnimators = getRunningAnimators();
        Iterator it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator anim = (Animator) it.next();
            if (runningAnimators.containsKey(anim)) {
                start();
                runAnimator(anim, runningAnimators);
            }
        }
        this.mAnimators.clear();
        end();
    }

    private void runAnimator(Animator animator, ArrayMap<Animator, AnimationInfo> arrayMap) {
        Animator animator2 = animator;
        ArrayMap<Animator, AnimationInfo> runningAnimators = arrayMap;
        Animator animator3 = animator2;
        ArrayMap<Animator, AnimationInfo> arrayMap2 = runningAnimators;
        if (animator2 != null) {
            final ArrayMap<Animator, AnimationInfo> arrayMap3 = runningAnimators;
            animator2.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ TransitionPort this$0;

                {
                    TransitionPort this$02 = r6;
                    ArrayMap arrayMap = r7;
                    TransitionPort transitionPort = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationStart(Animator animator) {
                    Animator animation = animator;
                    Animator animator2 = animation;
                    boolean add = this.this$0.mCurrentAnimators.add(animation);
                }

                public void onAnimationEnd(Animator animator) {
                    Animator animation = animator;
                    Animator animator2 = animation;
                    Object remove = arrayMap3.remove(animation);
                    boolean remove2 = this.this$0.mCurrentAnimators.remove(animation);
                }
            });
            animate(animator2);
        }
    }

    public TransitionPort addTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        if (targetId > 0) {
            boolean add = this.mTargetIds.add(Integer.valueOf(targetId));
        }
        return this;
    }

    public TransitionPort removeTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        if (targetId > 0) {
            boolean remove = this.mTargetIds.remove(Integer.valueOf(targetId));
        }
        return this;
    }

    public TransitionPort excludeTarget(int i, boolean z) {
        int targetId = i;
        int i2 = targetId;
        this.mTargetIdExcludes = excludeId(this.mTargetIdExcludes, targetId, z);
        return this;
    }

    public TransitionPort excludeChildren(int i, boolean z) {
        int targetId = i;
        int i2 = targetId;
        this.mTargetIdChildExcludes = excludeId(this.mTargetIdChildExcludes, targetId, z);
        return this;
    }

    private ArrayList<Integer> excludeId(ArrayList<Integer> arrayList, int i, boolean z) {
        ArrayList<Integer> list = arrayList;
        int targetId = i;
        ArrayList<Integer> list2 = list;
        int i2 = targetId;
        boolean exclude = z;
        if (targetId > 0) {
            if (!exclude) {
                list2 = ArrayListManager.remove(list, Integer.valueOf(targetId));
            } else {
                list2 = ArrayListManager.add(list, Integer.valueOf(targetId));
            }
        }
        return list2;
    }

    public TransitionPort excludeTarget(View view, boolean z) {
        View target = view;
        View view2 = target;
        this.mTargetExcludes = excludeView(this.mTargetExcludes, target, z);
        return this;
    }

    public TransitionPort excludeChildren(View view, boolean z) {
        View target = view;
        View view2 = target;
        this.mTargetChildExcludes = excludeView(this.mTargetChildExcludes, target, z);
        return this;
    }

    private ArrayList<View> excludeView(ArrayList<View> arrayList, View view, boolean z) {
        ArrayList<View> list = arrayList;
        View target = view;
        ArrayList<View> list2 = list;
        View view2 = target;
        boolean exclude = z;
        if (target != null) {
            if (!exclude) {
                list2 = ArrayListManager.remove(list, target);
            } else {
                list2 = ArrayListManager.add(list, target);
            }
        }
        return list2;
    }

    public TransitionPort excludeTarget(Class cls, boolean z) {
        Class type = cls;
        Class cls2 = type;
        this.mTargetTypeExcludes = excludeType(this.mTargetTypeExcludes, type, z);
        return this;
    }

    public TransitionPort excludeChildren(Class cls, boolean z) {
        Class type = cls;
        Class cls2 = type;
        this.mTargetTypeChildExcludes = excludeType(this.mTargetTypeChildExcludes, type, z);
        return this;
    }

    private ArrayList<Class> excludeType(ArrayList<Class> arrayList, Class cls, boolean z) {
        ArrayList<Class> list = arrayList;
        Class type = cls;
        ArrayList<Class> list2 = list;
        Class cls2 = type;
        boolean exclude = z;
        if (type != null) {
            if (!exclude) {
                list2 = ArrayListManager.remove(list, type);
            } else {
                list2 = ArrayListManager.add(list, type);
            }
        }
        return list2;
    }

    public TransitionPort addTarget(View view) {
        View target = view;
        View view2 = target;
        boolean add = this.mTargets.add(target);
        return this;
    }

    public TransitionPort removeTarget(View view) {
        View target = view;
        View view2 = target;
        if (target != null) {
            boolean remove = this.mTargets.remove(target);
        }
        return this;
    }

    public List<Integer> getTargetIds() {
        return this.mTargetIds;
    }

    public List<View> getTargets() {
        return this.mTargets;
    }

    /* access modifiers changed from: 0000 */
    public void captureValues(ViewGroup viewGroup, boolean z) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        boolean start = z;
        clearValues(start);
        if (this.mTargetIds.size() <= 0 && this.mTargets.size() <= 0) {
            captureHierarchy(sceneRoot, start);
            return;
        }
        if (this.mTargetIds.size() > 0) {
            for (int i = 0; i < this.mTargetIds.size(); i++) {
                int intValue = ((Integer) this.mTargetIds.get(i)).intValue();
                int id = intValue;
                View findViewById = sceneRoot.findViewById(intValue);
                View view = findViewById;
                if (findViewById != null) {
                    TransitionValues transitionValues = new TransitionValues();
                    TransitionValues values = transitionValues;
                    transitionValues.view = view;
                    if (!start) {
                        captureEndValues(values);
                    } else {
                        captureStartValues(values);
                    }
                    if (!start) {
                        Object put = this.mEndValues.viewValues.put(view, values);
                        if (id >= 0) {
                            this.mEndValues.idValues.put(id, values);
                        }
                    } else {
                        Object put2 = this.mStartValues.viewValues.put(view, values);
                        if (id >= 0) {
                            this.mStartValues.idValues.put(id, values);
                        }
                    }
                }
            }
        }
        if (this.mTargets.size() > 0) {
            for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                View view2 = (View) this.mTargets.get(i2);
                View view3 = view2;
                if (view2 != null) {
                    TransitionValues transitionValues2 = new TransitionValues();
                    TransitionValues values2 = transitionValues2;
                    transitionValues2.view = view3;
                    if (!start) {
                        captureEndValues(values2);
                    } else {
                        captureStartValues(values2);
                    }
                    if (!start) {
                        Object put3 = this.mEndValues.viewValues.put(view3, values2);
                    } else {
                        Object put4 = this.mStartValues.viewValues.put(view3, values2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearValues(boolean z) {
        if (!z) {
            this.mEndValues.viewValues.clear();
            this.mEndValues.idValues.clear();
            this.mEndValues.itemIdValues.clear();
            return;
        }
        this.mStartValues.viewValues.clear();
        this.mStartValues.idValues.clear();
        this.mStartValues.itemIdValues.clear();
    }

    private void captureHierarchy(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        boolean start = z;
        if (view2 != null) {
            boolean isListViewItem = false;
            if (view2.getParent() instanceof ListView) {
                isListViewItem = true;
            }
            if (!isListViewItem || ((ListView) view2.getParent()).getAdapter().hasStableIds()) {
                int id = -1;
                long itemId = -1;
                if (isListViewItem) {
                    ListView listView = (ListView) view2.getParent();
                    ListView listview = listView;
                    int positionForView = listView.getPositionForView(view2);
                    int i = positionForView;
                    itemId = listview.getItemIdAtPosition(positionForView);
                } else {
                    id = view2.getId();
                }
                if (this.mTargetIdExcludes == null || !this.mTargetIdExcludes.contains(Integer.valueOf(id))) {
                    if (this.mTargetExcludes == null || !this.mTargetExcludes.contains(view2)) {
                        if (!(this.mTargetTypeExcludes == null || view2 == null)) {
                            int numTypes = this.mTargetTypeExcludes.size();
                            int i2 = 0;
                            while (i2 < numTypes) {
                                if (!((Class) this.mTargetTypeExcludes.get(i2)).isInstance(view2)) {
                                    i2++;
                                } else {
                                    return;
                                }
                            }
                        }
                        TransitionValues transitionValues = new TransitionValues();
                        TransitionValues values = transitionValues;
                        transitionValues.view = view2;
                        if (!start) {
                            captureEndValues(values);
                        } else {
                            captureStartValues(values);
                        }
                        if (!start) {
                            if (isListViewItem) {
                                this.mEndValues.itemIdValues.put(itemId, values);
                            } else {
                                Object put = this.mEndValues.viewValues.put(view2, values);
                                if (id >= 0) {
                                    this.mEndValues.idValues.put(id, values);
                                }
                            }
                        } else if (isListViewItem) {
                            this.mStartValues.itemIdValues.put(itemId, values);
                        } else {
                            Object put2 = this.mStartValues.viewValues.put(view2, values);
                            if (id >= 0) {
                                this.mStartValues.idValues.put(id, values);
                            }
                        }
                        if (!(view2 instanceof ViewGroup) || (this.mTargetIdChildExcludes != null && this.mTargetIdChildExcludes.contains(Integer.valueOf(id)))) {
                            return;
                        }
                        if (this.mTargetChildExcludes == null || !this.mTargetChildExcludes.contains(view2)) {
                            if (!(this.mTargetTypeChildExcludes == null || view2 == null)) {
                                int numTypes2 = this.mTargetTypeChildExcludes.size();
                                int i3 = 0;
                                while (i3 < numTypes2) {
                                    if (!((Class) this.mTargetTypeChildExcludes.get(i3)).isInstance(view2)) {
                                        i3++;
                                    } else {
                                        return;
                                    }
                                }
                            }
                            ViewGroup parent = (ViewGroup) view2;
                            for (int i4 = 0; i4 < parent.getChildCount(); i4++) {
                                captureHierarchy(parent.getChildAt(i4), start);
                            }
                        }
                    }
                }
            }
        }
    }

    public TransitionValues getTransitionValues(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        boolean start = z;
        if (this.mParent != null) {
            return this.mParent.getTransitionValues(view2, start);
        }
        TransitionValuesMaps valuesMaps = !start ? this.mEndValues : this.mStartValues;
        TransitionValues transitionValues = (TransitionValues) valuesMaps.viewValues.get(view2);
        TransitionValues values = transitionValues;
        if (transitionValues == null) {
            int id = view2.getId();
            int id2 = id;
            if (id >= 0) {
                values = (TransitionValues) valuesMaps.idValues.get(id2);
            }
            if (values == null && (view2.getParent() instanceof ListView)) {
                ListView listView = (ListView) view2.getParent();
                ListView listview = listView;
                int positionForView = listView.getPositionForView(view2);
                int i = positionForView;
                long itemIdAtPosition = listview.getItemIdAtPosition(positionForView);
                long j = itemIdAtPosition;
                values = (TransitionValues) valuesMaps.itemIdValues.get(itemIdAtPosition);
            }
        }
        return values;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void pause(View view) {
        View sceneRoot = view;
        View view2 = sceneRoot;
        if (!this.mEnded) {
            ArrayMap runningAnimators = getRunningAnimators();
            ArrayMap arrayMap = runningAnimators;
            int size = runningAnimators.size();
            int i = size;
            WindowIdPort windowId = WindowIdPort.getWindowId(sceneRoot);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                AnimationInfo animationInfo = (AnimationInfo) arrayMap.valueAt(i2);
                AnimationInfo info = animationInfo;
                if (animationInfo.view != null && windowId.equals(info.windowId)) {
                    Animator animator = (Animator) arrayMap.keyAt(i2);
                    Animator animator2 = animator;
                    animator.cancel();
                }
            }
            if (this.mListeners != null && this.mListeners.size() > 0) {
                ArrayList arrayList = (ArrayList) this.mListeners.clone();
                ArrayList arrayList2 = arrayList;
                int numListeners = arrayList.size();
                for (int i3 = 0; i3 < numListeners; i3++) {
                    ((TransitionListener) arrayList2.get(i3)).onTransitionPause(this);
                }
            }
            this.mPaused = true;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void resume(View view) {
        View sceneRoot = view;
        View view2 = sceneRoot;
        if (this.mPaused) {
            if (!this.mEnded) {
                ArrayMap runningAnimators = getRunningAnimators();
                ArrayMap arrayMap = runningAnimators;
                int size = runningAnimators.size();
                int i = size;
                WindowIdPort windowId = WindowIdPort.getWindowId(sceneRoot);
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    AnimationInfo animationInfo = (AnimationInfo) arrayMap.valueAt(i2);
                    AnimationInfo info = animationInfo;
                    if (animationInfo.view != null && windowId.equals(info.windowId)) {
                        Animator animator = (Animator) arrayMap.keyAt(i2);
                        Animator animator2 = animator;
                        animator.end();
                    }
                }
                if (this.mListeners != null && this.mListeners.size() > 0) {
                    ArrayList arrayList = (ArrayList) this.mListeners.clone();
                    ArrayList arrayList2 = arrayList;
                    int numListeners = arrayList.size();
                    for (int i3 = 0; i3 < numListeners; i3++) {
                        ((TransitionListener) arrayList2.get(i3)).onTransitionResume(this);
                    }
                }
            }
            this.mPaused = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void playTransition(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        ArrayMap runningAnimators = getRunningAnimators();
        ArrayMap arrayMap = runningAnimators;
        int size = runningAnimators.size();
        int i = size;
        for (int i2 = size - 1; i2 >= 0; i2--) {
            Animator animator = (Animator) arrayMap.keyAt(i2);
            Animator anim = animator;
            if (animator != null) {
                AnimationInfo animationInfo = (AnimationInfo) arrayMap.get(anim);
                AnimationInfo oldInfo = animationInfo;
                if (!(animationInfo == null || oldInfo.view == null || oldInfo.view.getContext() != sceneRoot.getContext())) {
                    boolean cancel = false;
                    TransitionValues oldValues = oldInfo.values;
                    View oldView = oldInfo.view;
                    TransitionValues newValues = this.mEndValues.viewValues == null ? null : (TransitionValues) this.mEndValues.viewValues.get(oldView);
                    if (newValues == null) {
                        newValues = (TransitionValues) this.mEndValues.idValues.get(oldView.getId());
                    }
                    if (oldValues != null && newValues != null) {
                        Iterator it = oldValues.values.keySet().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            String key = (String) it.next();
                            Object oldValue = oldValues.values.get(key);
                            Object newValue = newValues.values.get(key);
                            if (oldValue != null && newValue != null && !oldValue.equals(newValue)) {
                                cancel = true;
                                break;
                            }
                        }
                    }
                    if (cancel) {
                        if (!anim.isRunning() && !anim.isStarted()) {
                            Object remove = arrayMap.remove(anim);
                        } else {
                            anim.cancel();
                        }
                    }
                }
            }
        }
        createAnimators(sceneRoot, this.mStartValues, this.mEndValues);
        runAnimators();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void animate(Animator animator) {
        Animator animator2 = animator;
        Animator animator3 = animator2;
        if (animator2 != null) {
            if (!(getDuration() < 0)) {
                Animator duration = animator2.setDuration(getDuration());
            }
            if (!(getStartDelay() < 0)) {
                animator2.setStartDelay(getStartDelay());
            }
            if (getInterpolator() != null) {
                animator2.setInterpolator(getInterpolator());
            }
            C00752 r0 = new AnimatorListenerAdapter(this) {
                final /* synthetic */ TransitionPort this$0;

                {
                    TransitionPort this$02 = r5;
                    TransitionPort transitionPort = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationEnd(Animator animator) {
                    Animator animation = animator;
                    Animator animator2 = animation;
                    this.this$0.end();
                    animation.removeListener(this);
                }
            };
            animator2.addListener(r0);
            animator2.start();
            return;
        }
        end();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void start() {
        if (this.mNumInstances == 0) {
            if (this.mListeners != null && this.mListeners.size() > 0) {
                ArrayList arrayList = (ArrayList) this.mListeners.clone();
                ArrayList arrayList2 = arrayList;
                int numListeners = arrayList.size();
                for (int i = 0; i < numListeners; i++) {
                    ((TransitionListener) arrayList2.get(i)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        this.mNumInstances++;
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void end() {
        this.mNumInstances--;
        if (this.mNumInstances == 0) {
            if (this.mListeners != null && this.mListeners.size() > 0) {
                ArrayList arrayList = (ArrayList) this.mListeners.clone();
                ArrayList arrayList2 = arrayList;
                int numListeners = arrayList.size();
                for (int i = 0; i < numListeners; i++) {
                    ((TransitionListener) arrayList2.get(i)).onTransitionEnd(this);
                }
            }
            for (int i2 = 0; i2 < this.mStartValues.itemIdValues.size(); i2++) {
                TransitionValues transitionValues = (TransitionValues) this.mStartValues.itemIdValues.valueAt(i2);
                TransitionValues transitionValues2 = transitionValues;
                View view = transitionValues.view;
            }
            for (int i3 = 0; i3 < this.mEndValues.itemIdValues.size(); i3++) {
                TransitionValues transitionValues3 = (TransitionValues) this.mEndValues.itemIdValues.valueAt(i3);
                TransitionValues transitionValues4 = transitionValues3;
                View view2 = transitionValues3.view;
            }
            this.mEnded = true;
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void cancel() {
        int size = this.mCurrentAnimators.size();
        int i = size;
        for (int i2 = size - 1; i2 >= 0; i2--) {
            Animator animator = (Animator) this.mCurrentAnimators.get(i2);
            Animator animator2 = animator;
            animator.cancel();
        }
        if (this.mListeners != null && this.mListeners.size() > 0) {
            ArrayList arrayList = (ArrayList) this.mListeners.clone();
            ArrayList arrayList2 = arrayList;
            int numListeners = arrayList.size();
            for (int i3 = 0; i3 < numListeners; i3++) {
                ((TransitionListener) arrayList2.get(i3)).onTransitionCancel(this);
            }
        }
    }

    public TransitionPort addListener(TransitionListener transitionListener) {
        TransitionListener listener = transitionListener;
        TransitionListener transitionListener2 = listener;
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        boolean add = this.mListeners.add(listener);
        return this;
    }

    public TransitionPort removeListener(TransitionListener transitionListener) {
        TransitionListener listener = transitionListener;
        TransitionListener transitionListener2 = listener;
        if (this.mListeners == null) {
            return this;
        }
        boolean remove = this.mListeners.remove(listener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public TransitionPort setSceneRoot(ViewGroup viewGroup) {
        ViewGroup sceneRoot = viewGroup;
        ViewGroup viewGroup2 = sceneRoot;
        this.mSceneRoot = sceneRoot;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void setCanRemoveViews(boolean z) {
        this.mCanRemoveViews = z;
    }

    public String toString() {
        return toString("");
    }

    public TransitionPort clone() {
        TransitionPort clone = null;
        try {
            TransitionPort transitionPort = (TransitionPort) super.clone();
            clone = transitionPort;
            transitionPort.mAnimators = new ArrayList<>();
            clone.mStartValues = new TransitionValuesMaps();
            clone.mEndValues = new TransitionValuesMaps();
        } catch (CloneNotSupportedException e) {
        }
        return clone;
    }

    public String getName() {
        return this.mName;
    }

    /* access modifiers changed from: 0000 */
    public String toString(String str) {
        String indent = str;
        String str2 = indent;
        String result = indent + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ": ";
        if (this.mDuration != -1) {
            result = result + "dur(" + this.mDuration + ") ";
        }
        if (this.mStartDelay != -1) {
            result = result + "dly(" + this.mStartDelay + ") ";
        }
        if (this.mInterpolator != null) {
            result = result + "interp(" + this.mInterpolator + ") ";
        }
        if (this.mTargetIds.size() > 0 || this.mTargets.size() > 0) {
            String result2 = result + "tgts(";
            if (this.mTargetIds.size() > 0) {
                for (int i = 0; i < this.mTargetIds.size(); i++) {
                    if (i > 0) {
                        result2 = result2 + ", ";
                    }
                    result2 = result2 + this.mTargetIds.get(i);
                }
            }
            if (this.mTargets.size() > 0) {
                for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                    if (i2 > 0) {
                        result2 = result2 + ", ";
                    }
                    result2 = result2 + this.mTargets.get(i2);
                }
            }
            result = result2 + ")";
        }
        return result;
    }
}
