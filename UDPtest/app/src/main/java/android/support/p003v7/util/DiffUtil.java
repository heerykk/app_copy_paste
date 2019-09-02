package android.support.p003v7.util;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p003v7.widget.RecyclerView.Adapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* renamed from: android.support.v7.util.DiffUtil */
public class DiffUtil {
    private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator<Snake>() {
        public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return compare((Snake) obj, (Snake) obj2);
        }

        public int compare(Snake snake, Snake snake2) {
            Snake o1 = snake;
            Snake o2 = snake2;
            Snake snake3 = o1;
            Snake snake4 = o2;
            int i = o1.f24x - o2.f24x;
            return i != 0 ? i : o1.f25y - o2.f25y;
        }
    };

    /* renamed from: android.support.v7.util.DiffUtil$Callback */
    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        public abstract int getNewListSize();

        public abstract int getOldListSize();

        public Callback() {
        }

        @Nullable
        public Object getChangePayload(int i, int i2) {
            int i3 = i;
            int i4 = i2;
            return null;
        }
    }

    /* renamed from: android.support.v7.util.DiffUtil$DiffResult */
    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_IGNORE = 16;
        private static final int FLAG_MASK = 31;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 5;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;
        private final List<Snake> mSnakes;

        DiffResult(Callback callback, List<Snake> list, int[] iArr, int[] iArr2, boolean z) {
            Callback callback2 = callback;
            List<Snake> snakes = list;
            int[] oldItemStatuses = iArr;
            int[] newItemStatuses = iArr2;
            Callback callback3 = callback2;
            List<Snake> list2 = snakes;
            int[] iArr3 = oldItemStatuses;
            int[] iArr4 = newItemStatuses;
            boolean detectMoves = z;
            this.mSnakes = snakes;
            this.mOldItemStatuses = oldItemStatuses;
            this.mNewItemStatuses = newItemStatuses;
            Arrays.fill(this.mOldItemStatuses, 0);
            Arrays.fill(this.mNewItemStatuses, 0);
            this.mCallback = callback2;
            this.mOldListSize = callback2.getOldListSize();
            this.mNewListSize = callback2.getNewListSize();
            this.mDetectMoves = detectMoves;
            addRootSnake();
            findMatchingItems();
        }

        private void addRootSnake() {
            Snake firstSnake = !this.mSnakes.isEmpty() ? (Snake) this.mSnakes.get(0) : null;
            if (firstSnake == null || firstSnake.f24x != 0 || firstSnake.f25y != 0) {
                Snake snake = new Snake();
                Snake root = snake;
                snake.f24x = 0;
                root.f25y = 0;
                root.removal = false;
                root.size = 0;
                root.reverse = false;
                this.mSnakes.add(0, root);
            }
        }

        private void findMatchingItems() {
            int posOld = this.mOldListSize;
            int posNew = this.mNewListSize;
            for (int i = this.mSnakes.size() - 1; i >= 0; i--) {
                Snake snake = (Snake) this.mSnakes.get(i);
                Snake snake2 = snake;
                int endX = snake.f24x + snake2.size;
                int endY = snake2.f25y + snake2.size;
                if (this.mDetectMoves) {
                    while (posOld > endX) {
                        findAddition(posOld, posNew, i);
                        posOld--;
                    }
                    while (posNew > endY) {
                        findRemoval(posOld, posNew, i);
                        posNew--;
                    }
                }
                for (int j = 0; j < snake2.size; j++) {
                    int oldItemPos = snake2.f24x + j;
                    int i2 = snake2.f25y + j;
                    int newItemPos = i2;
                    boolean areContentsTheSame = this.mCallback.areContentsTheSame(oldItemPos, i2);
                    boolean z = areContentsTheSame;
                    int changeFlag = !areContentsTheSame ? 2 : 1;
                    this.mOldItemStatuses[oldItemPos] = (newItemPos << 5) | changeFlag;
                    this.mNewItemStatuses[newItemPos] = (oldItemPos << 5) | changeFlag;
                }
                posOld = snake2.f24x;
                posNew = snake2.f25y;
            }
        }

        private void findAddition(int i, int i2, int i3) {
            int x = i;
            int y = i2;
            int snakeIndex = i3;
            int i4 = x;
            int i5 = y;
            int i6 = snakeIndex;
            if (this.mOldItemStatuses[x - 1] == 0) {
                boolean findMatchingItem = findMatchingItem(x, y, snakeIndex, false);
            }
        }

        private void findRemoval(int i, int i2, int i3) {
            int x = i;
            int y = i2;
            int snakeIndex = i3;
            int i4 = x;
            int i5 = y;
            int i6 = snakeIndex;
            if (this.mNewItemStatuses[y - 1] == 0) {
                boolean findMatchingItem = findMatchingItem(x, y, snakeIndex, true);
            }
        }

        private boolean findMatchingItem(int i, int i2, int i3, boolean z) {
            int myItemPos;
            int curX;
            int curY;
            int x = i;
            int y = i2;
            int snakeIndex = i3;
            int i4 = x;
            int i5 = y;
            int i6 = snakeIndex;
            boolean removal = z;
            if (!removal) {
                myItemPos = x - 1;
                curX = x - 1;
                curY = y;
            } else {
                myItemPos = y - 1;
                curX = x;
                curY = y - 1;
            }
            for (int i7 = snakeIndex; i7 >= 0; i7--) {
                Snake snake = (Snake) this.mSnakes.get(i7);
                Snake snake2 = snake;
                int endX = snake.f24x + snake2.size;
                int endY = snake2.f25y + snake2.size;
                if (!removal) {
                    int pos = curY - 1;
                    while (pos >= endY) {
                        if (!this.mCallback.areItemsTheSame(myItemPos, pos)) {
                            pos--;
                        } else {
                            boolean areContentsTheSame = this.mCallback.areContentsTheSame(myItemPos, pos);
                            boolean z2 = areContentsTheSame;
                            int changeFlag = !areContentsTheSame ? 4 : 8;
                            this.mOldItemStatuses[x - 1] = (pos << 5) | 16;
                            this.mNewItemStatuses[pos] = ((x - 1) << 5) | changeFlag;
                            return true;
                        }
                    }
                    continue;
                } else {
                    int pos2 = curX - 1;
                    while (pos2 >= endX) {
                        if (!this.mCallback.areItemsTheSame(pos2, myItemPos)) {
                            pos2--;
                        } else {
                            boolean areContentsTheSame2 = this.mCallback.areContentsTheSame(pos2, myItemPos);
                            boolean z3 = areContentsTheSame2;
                            int changeFlag2 = !areContentsTheSame2 ? 4 : 8;
                            this.mNewItemStatuses[myItemPos] = (pos2 << 5) | 16;
                            this.mOldItemStatuses[pos2] = (myItemPos << 5) | changeFlag2;
                            return true;
                        }
                    }
                    continue;
                }
                curX = snake2.f24x;
                curY = snake2.f25y;
            }
            return false;
        }

        public void dispatchUpdatesTo(Adapter adapter) {
            Adapter adapter2 = adapter;
            Adapter adapter3 = adapter2;
            final Adapter adapter4 = adapter2;
            dispatchUpdatesTo((ListUpdateCallback) new ListUpdateCallback(this) {
                final /* synthetic */ DiffResult this$0;

                {
                    DiffResult this$02 = r6;
                    Adapter adapter = r7;
                    DiffResult diffResult = this$02;
                    this.this$0 = this$02;
                }

                public void onInserted(int i, int i2) {
                    int position = i;
                    int count = i2;
                    int i3 = position;
                    int i4 = count;
                    adapter4.notifyItemRangeInserted(position, count);
                }

                public void onRemoved(int i, int i2) {
                    int position = i;
                    int count = i2;
                    int i3 = position;
                    int i4 = count;
                    adapter4.notifyItemRangeRemoved(position, count);
                }

                public void onMoved(int i, int i2) {
                    int fromPosition = i;
                    int toPosition = i2;
                    int i3 = fromPosition;
                    int i4 = toPosition;
                    adapter4.notifyItemMoved(fromPosition, toPosition);
                }

                public void onChanged(int i, int i2, Object obj) {
                    int position = i;
                    int count = i2;
                    Object payload = obj;
                    int i3 = position;
                    int i4 = count;
                    Object obj2 = payload;
                    adapter4.notifyItemRangeChanged(position, count, payload);
                }
            });
        }

        public void dispatchUpdatesTo(ListUpdateCallback listUpdateCallback) {
            BatchingListUpdateCallback batchingCallback;
            ListUpdateCallback updateCallback = listUpdateCallback;
            ListUpdateCallback listUpdateCallback2 = updateCallback;
            if (!(updateCallback instanceof BatchingListUpdateCallback)) {
                ListUpdateCallback batchingListUpdateCallback = new BatchingListUpdateCallback(updateCallback);
                batchingCallback = batchingListUpdateCallback;
                ListUpdateCallback updateCallback2 = batchingListUpdateCallback;
            } else {
                batchingCallback = (BatchingListUpdateCallback) updateCallback;
            }
            ArrayList arrayList = new ArrayList();
            int posOld = this.mOldListSize;
            int posNew = this.mNewListSize;
            for (int snakeIndex = this.mSnakes.size() - 1; snakeIndex >= 0; snakeIndex--) {
                Snake snake = (Snake) this.mSnakes.get(snakeIndex);
                Snake snake2 = snake;
                int snakeSize = snake.size;
                int endX = snake2.f24x + snakeSize;
                int endY = snake2.f25y + snakeSize;
                if (endX < posOld) {
                    dispatchRemovals(arrayList, batchingCallback, endX, posOld - endX, endX);
                }
                if (endY < posNew) {
                    dispatchAdditions(arrayList, batchingCallback, endX, posNew - endY, endY);
                }
                for (int i = snakeSize - 1; i >= 0; i--) {
                    if ((this.mOldItemStatuses[snake2.f24x + i] & 31) == 2) {
                        batchingCallback.onChanged(snake2.f24x + i, 1, this.mCallback.getChangePayload(snake2.f24x + i, snake2.f25y + i));
                    }
                }
                posOld = snake2.f24x;
                posNew = snake2.f25y;
            }
            batchingCallback.dispatchLastEvent();
        }

        private static PostponedUpdate removePostponedUpdate(List<PostponedUpdate> list, int i, boolean z) {
            List<PostponedUpdate> updates = list;
            int pos = i;
            List<PostponedUpdate> list2 = updates;
            int i2 = pos;
            boolean removal = z;
            for (int i3 = updates.size() - 1; i3 >= 0; i3--) {
                PostponedUpdate postponedUpdate = (PostponedUpdate) updates.get(i3);
                PostponedUpdate update = postponedUpdate;
                if (postponedUpdate.posInOwnerList == pos && update.removal == removal) {
                    Object remove = updates.remove(i3);
                    for (int j = i3; j < updates.size(); j++) {
                        PostponedUpdate postponedUpdate2 = (PostponedUpdate) updates.get(j);
                        postponedUpdate2.currentPos += !removal ? -1 : 1;
                    }
                    return update;
                }
            }
            return null;
        }

        private void dispatchAdditions(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            List<PostponedUpdate> postponedUpdates = list;
            ListUpdateCallback updateCallback = listUpdateCallback;
            int start = i;
            int count = i2;
            int globalIndex = i3;
            List<PostponedUpdate> list2 = postponedUpdates;
            ListUpdateCallback listUpdateCallback2 = updateCallback;
            int i4 = start;
            int i5 = count;
            int i6 = globalIndex;
            if (this.mDetectMoves) {
                for (int i7 = count - 1; i7 >= 0; i7--) {
                    int i8 = this.mNewItemStatuses[globalIndex + i7] & 31;
                    int status = i8;
                    switch (i8) {
                        case 0:
                            updateCallback.onInserted(start, 1);
                            for (PostponedUpdate postponedUpdate : postponedUpdates) {
                                PostponedUpdate postponedUpdate2 = postponedUpdate;
                                PostponedUpdate postponedUpdate3 = postponedUpdate;
                                postponedUpdate3.currentPos++;
                            }
                            break;
                        case 4:
                        case 8:
                            int i9 = this.mNewItemStatuses[globalIndex + i7] >> 5;
                            int pos = i9;
                            updateCallback.onMoved(removePostponedUpdate(postponedUpdates, i9, true).currentPos, start);
                            if (status == 4) {
                                updateCallback.onChanged(start, 1, this.mCallback.getChangePayload(pos, globalIndex + i7));
                                break;
                            } else {
                                break;
                            }
                        case 16:
                            PostponedUpdate postponedUpdate4 = new PostponedUpdate(globalIndex + i7, start, false);
                            boolean add = postponedUpdates.add(postponedUpdate4);
                            break;
                        default:
                            IllegalStateException illegalStateException = new IllegalStateException("unknown flag for pos " + (globalIndex + i7) + " " + Long.toBinaryString((long) status));
                            throw illegalStateException;
                    }
                }
                return;
            }
            updateCallback.onInserted(start, count);
        }

        private void dispatchRemovals(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            List<PostponedUpdate> postponedUpdates = list;
            ListUpdateCallback updateCallback = listUpdateCallback;
            int start = i;
            int count = i2;
            int globalIndex = i3;
            List<PostponedUpdate> list2 = postponedUpdates;
            ListUpdateCallback listUpdateCallback2 = updateCallback;
            int i4 = start;
            int i5 = count;
            int i6 = globalIndex;
            if (this.mDetectMoves) {
                for (int i7 = count - 1; i7 >= 0; i7--) {
                    int i8 = this.mOldItemStatuses[globalIndex + i7] & 31;
                    int status = i8;
                    switch (i8) {
                        case 0:
                            updateCallback.onRemoved(start + i7, 1);
                            for (PostponedUpdate postponedUpdate : postponedUpdates) {
                                PostponedUpdate postponedUpdate2 = postponedUpdate;
                                PostponedUpdate postponedUpdate3 = postponedUpdate;
                                postponedUpdate3.currentPos--;
                            }
                            break;
                        case 4:
                        case 8:
                            int i9 = this.mOldItemStatuses[globalIndex + i7] >> 5;
                            int pos = i9;
                            PostponedUpdate update = removePostponedUpdate(postponedUpdates, i9, false);
                            updateCallback.onMoved(start + i7, update.currentPos - 1);
                            if (status == 4) {
                                updateCallback.onChanged(update.currentPos - 1, 1, this.mCallback.getChangePayload(globalIndex + i7, pos));
                                break;
                            } else {
                                break;
                            }
                        case 16:
                            PostponedUpdate postponedUpdate4 = new PostponedUpdate(globalIndex + i7, start + i7, true);
                            boolean add = postponedUpdates.add(postponedUpdate4);
                            break;
                        default:
                            IllegalStateException illegalStateException = new IllegalStateException("unknown flag for pos " + (globalIndex + i7) + " " + Long.toBinaryString((long) status));
                            throw illegalStateException;
                    }
                }
                return;
            }
            updateCallback.onRemoved(start, count);
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public List<Snake> getSnakes() {
            return this.mSnakes;
        }
    }

    /* renamed from: android.support.v7.util.DiffUtil$PostponedUpdate */
    private static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        public PostponedUpdate(int i, int i2, boolean z) {
            int posInOwnerList2 = i;
            int currentPos2 = i2;
            int i3 = posInOwnerList2;
            int i4 = currentPos2;
            boolean removal2 = z;
            this.posInOwnerList = posInOwnerList2;
            this.currentPos = currentPos2;
            this.removal = removal2;
        }
    }

    /* renamed from: android.support.v7.util.DiffUtil$Range */
    static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range() {
        }

        public Range(int i, int i2, int i3, int i4) {
            int oldListStart2 = i;
            int oldListEnd2 = i2;
            int newListStart2 = i3;
            int newListEnd2 = i4;
            int i5 = oldListStart2;
            int i6 = oldListEnd2;
            int i7 = newListStart2;
            int i8 = newListEnd2;
            this.oldListStart = oldListStart2;
            this.oldListEnd = oldListEnd2;
            this.newListStart = newListStart2;
            this.newListEnd = newListEnd2;
        }
    }

    /* renamed from: android.support.v7.util.DiffUtil$Snake */
    static class Snake {
        boolean removal;
        boolean reverse;
        int size;

        /* renamed from: x */
        int f24x;

        /* renamed from: y */
        int f25y;

        Snake() {
        }
    }

    private DiffUtil() {
    }

    public static DiffResult calculateDiff(Callback callback) {
        Callback cb = callback;
        Callback callback2 = cb;
        return calculateDiff(cb, true);
    }

    public static DiffResult calculateDiff(Callback callback, boolean z) {
        Callback cb = callback;
        Callback callback2 = cb;
        boolean detectMoves = z;
        int oldSize = cb.getOldListSize();
        int newListSize = cb.getNewListSize();
        int newSize = newListSize;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = arrayList2;
        Range range = new Range(0, oldSize, 0, newListSize);
        boolean add = arrayList4.add(range);
        int abs = oldSize + newSize + Math.abs(oldSize - newSize);
        int max = abs;
        int[] forward = new int[(abs * 2)];
        int[] backward = new int[(max * 2)];
        ArrayList arrayList5 = new ArrayList();
        while (!arrayList3.isEmpty()) {
            Range range2 = (Range) arrayList3.remove(arrayList3.size() - 1);
            Snake diffPartial = diffPartial(cb, range2.oldListStart, range2.oldListEnd, range2.newListStart, range2.newListEnd, forward, backward, max);
            Snake snake = diffPartial;
            if (diffPartial == null) {
                boolean add2 = arrayList5.add(range2);
            } else {
                if (snake.size > 0) {
                    boolean add3 = arrayList.add(snake);
                }
                snake.f24x += range2.oldListStart;
                snake.f25y += range2.newListStart;
                Range left = !arrayList5.isEmpty() ? (Range) arrayList5.remove(arrayList5.size() - 1) : new Range();
                left.oldListStart = range2.oldListStart;
                left.newListStart = range2.newListStart;
                if (snake.reverse) {
                    left.oldListEnd = snake.f24x;
                    left.newListEnd = snake.f25y;
                } else if (!snake.removal) {
                    left.oldListEnd = snake.f24x;
                    left.newListEnd = snake.f25y - 1;
                } else {
                    left.oldListEnd = snake.f24x - 1;
                    left.newListEnd = snake.f25y;
                }
                boolean add4 = arrayList3.add(left);
                Range range3 = range2;
                if (!snake.reverse) {
                    range2.oldListStart = snake.f24x + snake.size;
                    range2.newListStart = snake.f25y + snake.size;
                } else if (!snake.removal) {
                    range2.oldListStart = snake.f24x + snake.size;
                    range2.newListStart = snake.f25y + snake.size + 1;
                } else {
                    range2.oldListStart = snake.f24x + snake.size + 1;
                    range2.newListStart = snake.f25y + snake.size;
                }
                boolean add5 = arrayList3.add(range2);
            }
        }
        Collections.sort(arrayList, SNAKE_COMPARATOR);
        DiffResult diffResult = new DiffResult(cb, arrayList, forward, backward, detectMoves);
        return diffResult;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x011b, code lost:
        if (r9[(r11 + r36) - 1] >= r9[(r11 + r36) + 1]) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x021a, code lost:
        if (r10[(r11 + r40) - 1] >= r10[(r11 + r40) + 1]) goto L_0x01f7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x016a A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0271 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x00ef A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01e6 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.support.p003v7.util.DiffUtil.Snake diffPartial(android.support.p003v7.util.DiffUtil.Callback r72, int r73, int r74, int r75, int r76, int[] r77, int[] r78, int r79) {
        /*
            r4 = r72
            r5 = r73
            r6 = r74
            r7 = r75
            r8 = r76
            r9 = r77
            r10 = r78
            r11 = r79
            r12 = r4
            r13 = r5
            r14 = r6
            r15 = r7
            r16 = r8
            r17 = r9
            r18 = r10
            r19 = r11
            int r20 = r6 - r5
            r21 = r20
            int r20 = r8 - r7
            r22 = r20
            int r20 = r6 - r5
            r23 = 1
            r0 = r20
            r1 = r23
            if (r0 >= r1) goto L_0x0035
        L_0x002e:
            r24 = 0
            r26 = 0
            r25 = r26
            return r25
        L_0x0035:
            int r20 = r8 - r7
            r23 = 1
            r0 = r20
            r1 = r23
            if (r0 < r1) goto L_0x002e
            int r20 = r21 - r22
            r27 = r20
            int r20 = r21 + r22
            int r20 = r20 + 1
            int r20 = r20 / 2
            r28 = r20
            int r29 = r11 - r20
            int r29 = r29 + -1
            int r30 = r11 + r20
            int r30 = r30 + 1
            r31 = 0
            r32 = r9
            r0 = r32
            r1 = r29
            r2 = r30
            r3 = r31
            java.util.Arrays.fill(r0, r1, r2, r3)
            int r29 = r11 - r20
            int r29 = r29 + -1
            int r29 = r29 + r27
            int r30 = r11 + r20
            int r30 = r30 + 1
            int r30 = r30 + r27
            r33 = r10
            r0 = r33
            r1 = r29
            r2 = r30
            r3 = r21
            java.util.Arrays.fill(r0, r1, r2, r3)
            int r20 = r27 % 2
            r23 = 0
            r0 = r20
            r1 = r23
            if (r0 != r1) goto L_0x00a0
            r20 = 0
        L_0x0087:
            r34 = r20
            r35 = 0
        L_0x008b:
            r0 = r35
            r1 = r28
            if (r0 <= r1) goto L_0x00a3
            java.lang.IllegalStateException r24 = new java.lang.IllegalStateException
            java.lang.String r70 = "DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation."
            r71 = r70
            r0 = r24
            r1 = r71
            r0.<init>(r1)
            throw r24
        L_0x00a0:
            r20 = 1
            goto L_0x0087
        L_0x00a3:
            r0 = r35
            int r0 = -r0
            r20 = r0
            r36 = r20
        L_0x00aa:
            r0 = r36
            r1 = r35
            if (r0 <= r1) goto L_0x00c0
            r0 = r35
            int r0 = -r0
            r20 = r0
            r36 = r20
        L_0x00b7:
            r0 = r36
            r1 = r35
            if (r0 <= r1) goto L_0x01b4
            int r35 = r35 + 1
            goto L_0x008b
        L_0x00c0:
            r0 = r35
            int r0 = -r0
            r29 = r0
            r0 = r36
            r1 = r29
            if (r0 != r1) goto L_0x00f2
        L_0x00cb:
            int r29 = r11 + r36
            int r29 = r29 + 1
            r39 = r9
            r20 = r39[r29]
            r40 = r20
            r41 = 0
        L_0x00d7:
            int r20 = r40 - r36
            r43 = r20
        L_0x00db:
            r0 = r40
            r1 = r21
            if (r0 < r1) goto L_0x011e
        L_0x00e1:
            int r29 = r11 + r36
            r45 = r9
            r45[r29] = r40
            r23 = 0
            r0 = r34
            r1 = r23
            if (r0 != r1) goto L_0x0141
        L_0x00ef:
            int r36 = r36 + 2
            goto L_0x00aa
        L_0x00f2:
            r0 = r36
            r1 = r35
            if (r0 != r1) goto L_0x0107
        L_0x00f8:
            int r29 = r11 + r36
            int r29 = r29 + -1
            r42 = r9
            r20 = r42[r29]
            int r20 = r20 + 1
            r40 = r20
            r41 = 1
            goto L_0x00d7
        L_0x0107:
            int r29 = r11 + r36
            int r29 = r29 + -1
            r37 = r9
            r20 = r37[r29]
            int r30 = r11 + r36
            int r30 = r30 + 1
            r38 = r9
            r29 = r38[r30]
            r0 = r20
            r1 = r29
            if (r0 < r1) goto L_0x00cb
            goto L_0x00f8
        L_0x011e:
            r0 = r43
            r1 = r22
            if (r0 < r1) goto L_0x0125
            goto L_0x00e1
        L_0x0125:
            int r29 = r5 + r40
            int r30 = r7 + r43
            r0 = r29
            r1 = r30
            boolean r44 = r4.areItemsTheSame(r0, r1)
            r20 = r44
            r23 = 0
            r0 = r20
            r1 = r23
            if (r0 != r1) goto L_0x013c
            goto L_0x00e1
        L_0x013c:
            int r40 = r40 + 1
            int r43 = r43 + 1
            goto L_0x00db
        L_0x0141:
            int r29 = r27 - r35
            int r29 = r29 + 1
            r0 = r36
            r1 = r29
            if (r0 >= r1) goto L_0x014c
            goto L_0x00ef
        L_0x014c:
            int r29 = r27 + r35
            int r29 = r29 + -1
            r0 = r36
            r1 = r29
            if (r0 <= r1) goto L_0x0157
            goto L_0x00ef
        L_0x0157:
            int r29 = r11 + r36
            r46 = r9
            r20 = r46[r29]
            int r30 = r11 + r36
            r47 = r10
            r29 = r47[r30]
            r0 = r20
            r1 = r29
            if (r0 >= r1) goto L_0x016a
            goto L_0x00ef
        L_0x016a:
            android.support.v7.util.DiffUtil$Snake r24 = new android.support.v7.util.DiffUtil$Snake
            r24.<init>()
            r48 = r24
            r24 = r24
            int r30 = r11 + r36
            r49 = r10
            r29 = r49[r30]
            r0 = r29
            r1 = r24
            r1.f24x = r0
            r0 = r48
            int r0 = r0.f24x
            r29 = r0
            int r29 = r29 - r36
            r0 = r29
            r1 = r48
            r1.f25y = r0
            int r30 = r11 + r36
            r50 = r9
            r29 = r50[r30]
            int r31 = r11 + r36
            r51 = r10
            r30 = r51[r31]
            int r29 = r29 - r30
            r0 = r29
            r1 = r48
            r1.size = r0
            r52 = r41
            r0 = r52
            r1 = r48
            r1.removal = r0
            r53 = 0
            r0 = r53
            r1 = r48
            r1.reverse = r0
            r54 = r48
            return r54
        L_0x01b4:
            int r20 = r36 + r27
            r40 = r20
            int r29 = r35 + r27
            r0 = r20
            r1 = r29
            if (r0 != r1) goto L_0x01ea
        L_0x01c0:
            int r29 = r11 + r40
            int r29 = r29 + -1
            r57 = r10
            r20 = r57[r29]
            r41 = r20
            r43 = 0
        L_0x01cc:
            int r20 = r41 - r40
            r59 = r20
        L_0x01d0:
            r23 = 0
            r0 = r41
            r1 = r23
            if (r0 > r1) goto L_0x021d
        L_0x01d8:
            int r29 = r11 + r40
            r61 = r10
            r61[r29] = r41
            r23 = 0
            r0 = r34
            r1 = r23
            if (r0 == r1) goto L_0x0246
        L_0x01e6:
            int r36 = r36 + 2
            goto L_0x00b7
        L_0x01ea:
            r0 = r35
            int r0 = -r0
            r29 = r0
            int r29 = r29 + r27
            r0 = r40
            r1 = r29
            if (r0 != r1) goto L_0x0206
        L_0x01f7:
            int r29 = r11 + r40
            int r29 = r29 + 1
            r58 = r10
            r20 = r58[r29]
            int r20 = r20 + -1
            r41 = r20
            r43 = 1
            goto L_0x01cc
        L_0x0206:
            int r29 = r11 + r40
            int r29 = r29 + -1
            r55 = r10
            r20 = r55[r29]
            int r30 = r11 + r40
            int r30 = r30 + 1
            r56 = r10
            r29 = r56[r30]
            r0 = r20
            r1 = r29
            if (r0 < r1) goto L_0x01c0
            goto L_0x01f7
        L_0x021d:
            r23 = 0
            r0 = r59
            r1 = r23
            if (r0 > r1) goto L_0x0226
            goto L_0x01d8
        L_0x0226:
            int r29 = r5 + r41
            int r29 = r29 + -1
            int r30 = r7 + r59
            int r30 = r30 + -1
            r0 = r29
            r1 = r30
            boolean r60 = r4.areItemsTheSame(r0, r1)
            r20 = r60
            r23 = 0
            r0 = r20
            r1 = r23
            if (r0 != r1) goto L_0x0241
            goto L_0x01d8
        L_0x0241:
            int r41 = r41 + -1
            int r59 = r59 + -1
            goto L_0x01d0
        L_0x0246:
            int r20 = r36 + r27
            r0 = r35
            int r0 = -r0
            r29 = r0
            r0 = r20
            r1 = r29
            if (r0 >= r1) goto L_0x0254
            goto L_0x01e6
        L_0x0254:
            int r20 = r36 + r27
            r0 = r20
            r1 = r35
            if (r0 <= r1) goto L_0x025d
            goto L_0x01e6
        L_0x025d:
            int r29 = r11 + r40
            r62 = r9
            r20 = r62[r29]
            int r30 = r11 + r40
            r63 = r10
            r29 = r63[r30]
            r0 = r20
            r1 = r29
            if (r0 >= r1) goto L_0x0271
            goto L_0x01e6
        L_0x0271:
            android.support.v7.util.DiffUtil$Snake r24 = new android.support.v7.util.DiffUtil$Snake
            r24.<init>()
            r64 = r24
            r24 = r24
            int r30 = r11 + r40
            r65 = r10
            r29 = r65[r30]
            r0 = r29
            r1 = r24
            r1.f24x = r0
            r0 = r64
            int r0 = r0.f24x
            r29 = r0
            int r29 = r29 - r40
            r0 = r29
            r1 = r64
            r1.f25y = r0
            int r30 = r11 + r40
            r66 = r9
            r29 = r66[r30]
            int r31 = r11 + r40
            r67 = r10
            r30 = r67[r31]
            int r29 = r29 - r30
            r0 = r29
            r1 = r64
            r1.size = r0
            r68 = r43
            r0 = r68
            r1 = r64
            r1.removal = r0
            r53 = 1
            r0 = r53
            r1 = r64
            r1.reverse = r0
            r69 = r64
            return r69
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.util.DiffUtil.diffPartial(android.support.v7.util.DiffUtil$Callback, int, int, int, int, int[], int[], int):android.support.v7.util.DiffUtil$Snake");
    }
}
