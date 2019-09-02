package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.util.Pools.Pool;
import android.support.p000v4.util.Pools.SimplePool;
import android.support.p000v4.util.SimpleArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

final class DirectedAcyclicGraph<T> {
    private final SimpleArrayMap<T, ArrayList<T>> mGraph = new SimpleArrayMap<>();
    private final Pool<ArrayList<T>> mListPool = new SimplePool(10);
    private final ArrayList<T> mSortResult = new ArrayList<>();
    private final HashSet<T> mSortTmpMarked = new HashSet<>();

    DirectedAcyclicGraph() {
    }

    /* access modifiers changed from: 0000 */
    public void addNode(@NonNull T t) {
        T node = t;
        T t2 = node;
        if (!this.mGraph.containsKey(node)) {
            Object put = this.mGraph.put(node, null);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean contains(@NonNull T t) {
        T node = t;
        T t2 = node;
        return this.mGraph.containsKey(node);
    }

    /* access modifiers changed from: 0000 */
    public void addEdge(@NonNull T t, @NonNull T t2) {
        T node = t;
        T incomingEdge = t2;
        T t3 = node;
        T t4 = incomingEdge;
        if (this.mGraph.containsKey(node) && this.mGraph.containsKey(incomingEdge)) {
            ArrayList arrayList = (ArrayList) this.mGraph.get(node);
            ArrayList arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = getEmptyList();
                Object put = this.mGraph.put(node, arrayList2);
            }
            boolean add = arrayList2.add(incomingEdge);
            return;
        }
        throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public List getIncomingEdges(@NonNull T t) {
        T node = t;
        T t2 = node;
        return (List) this.mGraph.get(node);
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public List getOutgoingEdges(@NonNull T t) {
        T node = t;
        T t2 = node;
        ArrayList arrayList = null;
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList2 = (ArrayList) this.mGraph.valueAt(i);
            ArrayList arrayList3 = arrayList2;
            if (arrayList2 != null && arrayList3.contains(node)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                boolean add = arrayList.add(this.mGraph.keyAt(i));
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasOutgoingEdges(@NonNull T t) {
        T node = t;
        T t2 = node;
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.mGraph.valueAt(i);
            ArrayList arrayList2 = arrayList;
            if (arrayList != null && arrayList2.contains(node)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void clear() {
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.mGraph.valueAt(i);
            ArrayList arrayList2 = arrayList;
            if (arrayList != null) {
                poolList(arrayList2);
            }
        }
        this.mGraph.clear();
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public ArrayList<T> getSortedList() {
        this.mSortResult.clear();
        this.mSortTmpMarked.clear();
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            dfs(this.mGraph.keyAt(i), this.mSortResult, this.mSortTmpMarked);
        }
        return this.mSortResult;
    }

    private void dfs(T t, ArrayList<T> arrayList, HashSet<T> hashSet) {
        T node = t;
        ArrayList<T> result = arrayList;
        HashSet<T> tmpMarked = hashSet;
        T t2 = node;
        ArrayList<T> arrayList2 = result;
        HashSet<T> hashSet2 = tmpMarked;
        if (result.contains(node)) {
            return;
        }
        if (!tmpMarked.contains(node)) {
            boolean add = tmpMarked.add(node);
            ArrayList arrayList3 = (ArrayList) this.mGraph.get(node);
            ArrayList arrayList4 = arrayList3;
            if (arrayList3 != null) {
                int size = arrayList4.size();
                for (int i = 0; i < size; i++) {
                    dfs(arrayList4.get(i), result, tmpMarked);
                }
            }
            boolean remove = tmpMarked.remove(node);
            boolean add2 = result.add(node);
            return;
        }
        throw new RuntimeException("This graph contains cyclic dependencies");
    }

    /* access modifiers changed from: 0000 */
    public int size() {
        return this.mGraph.size();
    }

    @NonNull
    private ArrayList<T> getEmptyList() {
        ArrayList arrayList = (ArrayList) this.mListPool.acquire();
        ArrayList arrayList2 = arrayList;
        if (arrayList == null) {
            arrayList2 = new ArrayList();
        }
        return arrayList2;
    }

    private void poolList(@NonNull ArrayList<T> arrayList) {
        ArrayList<T> list = arrayList;
        ArrayList<T> arrayList2 = list;
        list.clear();
        boolean release = this.mListPool.release(list);
    }
}
