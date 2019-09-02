package android.support.p003v7.util;

import android.os.Handler;
import android.os.Looper;
import android.support.p000v4.content.ParallelExecutorCompat;
import android.support.p003v7.util.ThreadUtil.BackgroundCallback;
import android.support.p003v7.util.ThreadUtil.MainThreadCallback;
import android.support.p003v7.util.TileList.Tile;
import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: android.support.v7.util.MessageThreadUtil */
class MessageThreadUtil<T> implements ThreadUtil<T> {

    /* renamed from: android.support.v7.util.MessageThreadUtil$MessageQueue */
    static class MessageQueue {
        private SyncQueueItem mRoot;

        MessageQueue() {
        }

        /* access modifiers changed from: 0000 */
        public synchronized SyncQueueItem next() {
            synchronized (this) {
                if (this.mRoot == null) {
                    return null;
                }
                SyncQueueItem next = this.mRoot;
                this.mRoot = SyncQueueItem.access$000(this.mRoot);
                SyncQueueItem syncQueueItem = next;
                return syncQueueItem;
            }
        }

        /* access modifiers changed from: 0000 */
        public synchronized void sendMessageAtFrontOfQueue(SyncQueueItem syncQueueItem) {
            SyncQueueItem item = syncQueueItem;
            synchronized (this) {
                SyncQueueItem syncQueueItem2 = item;
                SyncQueueItem access$002 = SyncQueueItem.access$002(item, this.mRoot);
                this.mRoot = item;
            }
        }

        /* access modifiers changed from: 0000 */
        public synchronized void sendMessage(SyncQueueItem syncQueueItem) {
            SyncQueueItem item = syncQueueItem;
            synchronized (this) {
                SyncQueueItem syncQueueItem2 = item;
                if (this.mRoot != null) {
                    SyncQueueItem syncQueueItem3 = this.mRoot;
                    while (true) {
                        SyncQueueItem last = syncQueueItem3;
                        if (SyncQueueItem.access$000(last) == null) {
                            SyncQueueItem access$002 = SyncQueueItem.access$002(last, item);
                            return;
                        }
                        syncQueueItem3 = SyncQueueItem.access$000(last);
                    }
                } else {
                    this.mRoot = item;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public synchronized void removeMessages(int i) {
            int what = i;
            synchronized (this) {
                int i2 = what;
                while (true) {
                    if (this.mRoot == null) {
                        break;
                    } else if (this.mRoot.what != what) {
                        break;
                    } else {
                        SyncQueueItem item = this.mRoot;
                        this.mRoot = SyncQueueItem.access$000(this.mRoot);
                        item.recycle();
                    }
                }
                if (this.mRoot != null) {
                    SyncQueueItem syncQueueItem = this.mRoot;
                    SyncQueueItem prev = syncQueueItem;
                    SyncQueueItem item2 = SyncQueueItem.access$000(syncQueueItem);
                    while (item2 != null) {
                        SyncQueueItem next = SyncQueueItem.access$000(item2);
                        if (item2.what != what) {
                            prev = item2;
                        } else {
                            SyncQueueItem access$002 = SyncQueueItem.access$002(prev, next);
                            item2.recycle();
                        }
                        item2 = next;
                    }
                }
            }
        }
    }

    /* renamed from: android.support.v7.util.MessageThreadUtil$SyncQueueItem */
    static class SyncQueueItem {
        private static SyncQueueItem sPool;
        private static final Object sPoolLock = new Object();
        public int arg1;
        public int arg2;
        public int arg3;
        public int arg4;
        public int arg5;
        public Object data;
        private SyncQueueItem next;
        public int what;

        SyncQueueItem() {
        }

        static /* synthetic */ SyncQueueItem access$000(SyncQueueItem syncQueueItem) {
            SyncQueueItem x0 = syncQueueItem;
            SyncQueueItem syncQueueItem2 = x0;
            return x0.next;
        }

        static /* synthetic */ SyncQueueItem access$002(SyncQueueItem syncQueueItem, SyncQueueItem syncQueueItem2) {
            SyncQueueItem x0 = syncQueueItem;
            SyncQueueItem x1 = syncQueueItem2;
            SyncQueueItem syncQueueItem3 = x0;
            SyncQueueItem syncQueueItem4 = x1;
            x0.next = x1;
            return x1;
        }

        /* access modifiers changed from: 0000 */
        public void recycle() {
            this.next = null;
            this.arg5 = 0;
            this.arg4 = 0;
            this.arg3 = 0;
            this.arg2 = 0;
            this.arg1 = 0;
            this.what = 0;
            this.data = null;
            Object obj = sPoolLock;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    if (sPool != null) {
                        this.next = sPool;
                    }
                    sPool = this;
                } finally {
                    Object obj3 = obj2;
                }
            }
        }

        /* JADX INFO: finally extract failed */
        static SyncQueueItem obtainMessage(int i, int i2, int i3, int i4, int i5, int i6, Object obj) {
            SyncQueueItem item;
            int what2 = i;
            int arg12 = i2;
            int arg22 = i3;
            int arg32 = i4;
            int arg42 = i5;
            int arg52 = i6;
            Object data2 = obj;
            int i7 = what2;
            int i8 = arg12;
            int i9 = arg22;
            int i10 = arg32;
            int i11 = arg42;
            int i12 = arg52;
            Object obj2 = data2;
            Object obj3 = sPoolLock;
            Object obj4 = obj3;
            synchronized (obj3) {
                try {
                    if (sPool != null) {
                        item = sPool;
                        sPool = sPool.next;
                        item.next = null;
                    } else {
                        item = new SyncQueueItem();
                    }
                    item.what = what2;
                    item.arg1 = arg12;
                    item.arg2 = arg22;
                    item.arg3 = arg32;
                    item.arg4 = arg42;
                    item.arg5 = arg52;
                    item.data = data2;
                    SyncQueueItem syncQueueItem = item;
                    return syncQueueItem;
                } catch (Throwable th) {
                    Object obj5 = obj4;
                    throw th;
                }
            }
        }

        static SyncQueueItem obtainMessage(int i, int i2, int i3) {
            int what2 = i;
            int arg12 = i2;
            int arg22 = i3;
            int i4 = what2;
            int i5 = arg12;
            int i6 = arg22;
            return obtainMessage(what2, arg12, arg22, 0, 0, 0, null);
        }

        static SyncQueueItem obtainMessage(int i, int i2, Object obj) {
            int what2 = i;
            int arg12 = i2;
            Object data2 = obj;
            int i3 = what2;
            int i4 = arg12;
            Object obj2 = data2;
            return obtainMessage(what2, arg12, 0, 0, 0, 0, data2);
        }
    }

    MessageThreadUtil() {
    }

    public MainThreadCallback<T> getMainThreadProxy(MainThreadCallback<T> mainThreadCallback) {
        MainThreadCallback<T> callback = mainThreadCallback;
        MainThreadCallback<T> mainThreadCallback2 = callback;
        final MainThreadCallback<T> mainThreadCallback3 = callback;
        return new MainThreadCallback<T>(this) {
            static final int ADD_TILE = 2;
            static final int REMOVE_TILE = 3;
            static final int UPDATE_ITEM_COUNT = 1;
            private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
            private Runnable mMainThreadRunnable = new Runnable(this) {
                final /* synthetic */ C02771 this$1;

                {
                    C02771 this$12 = r5;
                    C02771 r3 = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    SyncQueueItem next = this.this$1.mQueue.next();
                    while (true) {
                        SyncQueueItem msg = next;
                        if (msg != null) {
                            switch (msg.what) {
                                case 1:
                                    mainThreadCallback3.updateItemCount(msg.arg1, msg.arg2);
                                    break;
                                case 2:
                                    mainThreadCallback3.addTile(msg.arg1, (Tile) msg.data);
                                    break;
                                case 3:
                                    mainThreadCallback3.removeTile(msg.arg1, msg.arg2);
                                    break;
                                default:
                                    int e = Log.e("ThreadUtil", "Unsupported message, what=" + msg.what);
                                    break;
                            }
                            next = this.this$1.mQueue.next();
                        } else {
                            return;
                        }
                    }
                }
            };
            final MessageQueue mQueue = new MessageQueue();
            final /* synthetic */ MessageThreadUtil this$0;

            {
                MessageThreadUtil this$02 = r10;
                MainThreadCallback mainThreadCallback = r11;
                MessageThreadUtil messageThreadUtil = this$02;
                this.this$0 = this$02;
            }

            public void updateItemCount(int i, int i2) {
                int generation = i;
                int itemCount = i2;
                int i3 = generation;
                int i4 = itemCount;
                sendMessage(SyncQueueItem.obtainMessage(1, generation, itemCount));
            }

            public void addTile(int i, Tile<T> tile) {
                int generation = i;
                Tile<T> tile2 = tile;
                int i2 = generation;
                Tile<T> tile3 = tile2;
                sendMessage(SyncQueueItem.obtainMessage(2, generation, (Object) tile2));
            }

            public void removeTile(int i, int i2) {
                int generation = i;
                int position = i2;
                int i3 = generation;
                int i4 = position;
                sendMessage(SyncQueueItem.obtainMessage(3, generation, position));
            }

            private void sendMessage(SyncQueueItem syncQueueItem) {
                SyncQueueItem msg = syncQueueItem;
                SyncQueueItem syncQueueItem2 = msg;
                this.mQueue.sendMessage(msg);
                boolean post = this.mMainThreadHandler.post(this.mMainThreadRunnable);
            }
        };
    }

    public BackgroundCallback<T> getBackgroundProxy(BackgroundCallback<T> backgroundCallback) {
        BackgroundCallback<T> callback = backgroundCallback;
        BackgroundCallback<T> backgroundCallback2 = callback;
        final BackgroundCallback<T> backgroundCallback3 = callback;
        return new BackgroundCallback<T>(this) {
            static final int LOAD_TILE = 3;
            static final int RECYCLE_TILE = 4;
            static final int REFRESH = 1;
            static final int UPDATE_RANGE = 2;
            private Runnable mBackgroundRunnable = new Runnable(this) {
                final /* synthetic */ C02792 this$1;

                {
                    C02792 this$12 = r5;
                    C02792 r3 = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    while (true) {
                        SyncQueueItem next = this.this$1.mQueue.next();
                        SyncQueueItem msg = next;
                        if (next != null) {
                            switch (msg.what) {
                                case 1:
                                    this.this$1.mQueue.removeMessages(1);
                                    backgroundCallback3.refresh(msg.arg1);
                                    break;
                                case 2:
                                    this.this$1.mQueue.removeMessages(2);
                                    this.this$1.mQueue.removeMessages(3);
                                    backgroundCallback3.updateRange(msg.arg1, msg.arg2, msg.arg3, msg.arg4, msg.arg5);
                                    break;
                                case 3:
                                    backgroundCallback3.loadTile(msg.arg1, msg.arg2);
                                    break;
                                case 4:
                                    backgroundCallback3.recycleTile((Tile) msg.data);
                                    break;
                                default:
                                    int e = Log.e("ThreadUtil", "Unsupported message, what=" + msg.what);
                                    break;
                            }
                        } else {
                            this.this$1.mBackgroundRunning.set(false);
                            return;
                        }
                    }
                }
            };
            AtomicBoolean mBackgroundRunning = new AtomicBoolean(false);
            private final Executor mExecutor = ParallelExecutorCompat.getParallelExecutor();
            final MessageQueue mQueue = new MessageQueue();
            final /* synthetic */ MessageThreadUtil this$0;

            {
                MessageThreadUtil this$02 = r9;
                BackgroundCallback backgroundCallback = r10;
                MessageThreadUtil messageThreadUtil = this$02;
                this.this$0 = this$02;
            }

            public void refresh(int i) {
                int generation = i;
                int i2 = generation;
                sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(1, generation, (Object) null));
            }

            public void updateRange(int i, int i2, int i3, int i4, int i5) {
                int rangeStart = i;
                int rangeEnd = i2;
                int extRangeStart = i3;
                int extRangeEnd = i4;
                int scrollHint = i5;
                int i6 = rangeStart;
                int i7 = rangeEnd;
                int i8 = extRangeStart;
                int i9 = extRangeEnd;
                int i10 = scrollHint;
                sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(2, rangeStart, rangeEnd, extRangeStart, extRangeEnd, scrollHint, null));
            }

            public void loadTile(int i, int i2) {
                int position = i;
                int scrollHint = i2;
                int i3 = position;
                int i4 = scrollHint;
                sendMessage(SyncQueueItem.obtainMessage(3, position, scrollHint));
            }

            public void recycleTile(Tile<T> tile) {
                Tile<T> tile2 = tile;
                Tile<T> tile3 = tile2;
                sendMessage(SyncQueueItem.obtainMessage(4, 0, (Object) tile2));
            }

            private void sendMessage(SyncQueueItem syncQueueItem) {
                SyncQueueItem msg = syncQueueItem;
                SyncQueueItem syncQueueItem2 = msg;
                this.mQueue.sendMessage(msg);
                maybeExecuteBackgroundRunnable();
            }

            private void sendMessageAtFrontOfQueue(SyncQueueItem syncQueueItem) {
                SyncQueueItem msg = syncQueueItem;
                SyncQueueItem syncQueueItem2 = msg;
                this.mQueue.sendMessageAtFrontOfQueue(msg);
                maybeExecuteBackgroundRunnable();
            }

            private void maybeExecuteBackgroundRunnable() {
                if (this.mBackgroundRunning.compareAndSet(false, true)) {
                    this.mExecutor.execute(this.mBackgroundRunnable);
                }
            }
        };
    }
}
