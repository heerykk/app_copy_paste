package android.support.p000v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.p002os.OperationCanceledException;
import android.support.p000v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/* renamed from: android.support.v4.content.AsyncTaskLoader */
public abstract class AsyncTaskLoader<D> extends Loader<D> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncTaskLoader";
    volatile LoadTask mCancellingTask;
    private final Executor mExecutor;
    Handler mHandler;
    long mLastLoadCompleteTime;
    volatile LoadTask mTask;
    long mUpdateThrottle;

    /* renamed from: android.support.v4.content.AsyncTaskLoader$LoadTask */
    final class LoadTask extends ModernAsyncTask<Void, Void, D> implements Runnable {
        private final CountDownLatch mDone = new CountDownLatch(1);
        final /* synthetic */ AsyncTaskLoader this$0;
        boolean waiting;

        LoadTask(AsyncTaskLoader asyncTaskLoader) {
            AsyncTaskLoader this$02 = asyncTaskLoader;
            AsyncTaskLoader asyncTaskLoader2 = this$02;
            this.this$0 = this$02;
        }

        /* access modifiers changed from: protected */
        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return doInBackground((Void[]) objArr);
        }

        /* access modifiers changed from: protected */
        public D doInBackground(Void... voidArr) {
            Void[] voidArr2 = voidArr;
            try {
                Object onLoadInBackground = this.this$0.onLoadInBackground();
                Object obj = onLoadInBackground;
                return onLoadInBackground;
            } catch (OperationCanceledException e) {
                OperationCanceledException operationCanceledException = e;
                if (isCancelled()) {
                    return null;
                }
                throw e;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(D d) {
            D data = d;
            D d2 = data;
            try {
                this.this$0.dispatchOnLoadComplete(this, data);
            } finally {
                this.mDone.countDown();
            }
        }

        /* access modifiers changed from: protected */
        public void onCancelled(D d) {
            D data = d;
            D d2 = data;
            try {
                this.this$0.dispatchOnCancelled(this, data);
            } finally {
                this.mDone.countDown();
            }
        }

        public void run() {
            this.waiting = false;
            this.this$0.executePendingTask();
        }

        public void waitForLoader() {
            try {
                this.mDone.await();
            } catch (InterruptedException e) {
            }
        }
    }

    public abstract D loadInBackground();

    private AsyncTaskLoader(Context context, Executor executor) {
        Context context2 = context;
        Executor executor2 = executor;
        Context context3 = context2;
        Executor executor3 = executor2;
        super(context2);
        this.mLastLoadCompleteTime = -10000;
        this.mExecutor = executor2;
    }

    public AsyncTaskLoader(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, ModernAsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void setUpdateThrottle(long j) {
        long delayMS = j;
        long j2 = delayMS;
        this.mUpdateThrottle = delayMS;
        if (delayMS != 0) {
            this.mHandler = new Handler();
        }
    }

    /* access modifiers changed from: protected */
    public void onForceLoad() {
        super.onForceLoad();
        boolean cancelLoad = cancelLoad();
        this.mTask = new LoadTask<>(this);
        executePendingTask();
    }

    /* access modifiers changed from: protected */
    public boolean onCancelLoad() {
        if (this.mTask == null) {
            return false;
        }
        if (this.mCancellingTask != null) {
            if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            this.mTask = null;
            return false;
        } else if (!this.mTask.waiting) {
            boolean cancel = this.mTask.cancel(false);
            boolean cancelled = cancel;
            if (cancel) {
                this.mCancellingTask = this.mTask;
                cancelLoadInBackground();
            }
            this.mTask = null;
            return cancelled;
        } else {
            this.mTask.waiting = false;
            this.mHandler.removeCallbacks(this.mTask);
            this.mTask = null;
            return false;
        }
    }

    public void onCanceled(D d) {
        D d2 = d;
    }

    /* access modifiers changed from: 0000 */
    public void executePendingTask() {
        if (this.mCancellingTask == null && this.mTask != null) {
            if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            if (!(this.mUpdateThrottle <= 0)) {
                long uptimeMillis = SystemClock.uptimeMillis();
                long j = uptimeMillis;
                if (!(uptimeMillis >= this.mLastLoadCompleteTime + this.mUpdateThrottle)) {
                    this.mTask.waiting = true;
                    boolean postAtTime = this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
                    return;
                }
            }
            ModernAsyncTask executeOnExecutor = this.mTask.executeOnExecutor(this.mExecutor, null);
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnCancelled(LoadTask loadTask, D d) {
        LoadTask task = loadTask;
        D data = d;
        LoadTask loadTask2 = task;
        D d2 = data;
        onCanceled(data);
        if (this.mCancellingTask == task) {
            rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            deliverCancellation();
            executePendingTask();
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnLoadComplete(LoadTask loadTask, D d) {
        LoadTask task = loadTask;
        D data = d;
        LoadTask loadTask2 = task;
        D d2 = data;
        if (this.mTask != task) {
            dispatchOnCancelled(task, data);
        } else if (!isAbandoned()) {
            commitContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mTask = null;
            deliverResult(data);
        } else {
            onCanceled(data);
        }
    }

    /* access modifiers changed from: protected */
    public D onLoadInBackground() {
        return loadInBackground();
    }

    public void cancelLoadInBackground() {
    }

    public boolean isLoadInBackgroundCanceled() {
        return this.mCancellingTask != null;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void waitForLoader() {
        LoadTask loadTask = this.mTask;
        LoadTask task = loadTask;
        if (loadTask != null) {
            task.waitForLoader();
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String prefix = str;
        FileDescriptor fd = fileDescriptor;
        PrintWriter writer = printWriter;
        String[] args = strArr;
        String str2 = prefix;
        FileDescriptor fileDescriptor2 = fd;
        PrintWriter printWriter2 = writer;
        String[] strArr2 = args;
        super.dump(prefix, fd, writer, args);
        if (this.mTask != null) {
            writer.print(prefix);
            writer.print("mTask=");
            writer.print(this.mTask);
            writer.print(" waiting=");
            writer.println(this.mTask.waiting);
        }
        if (this.mCancellingTask != null) {
            writer.print(prefix);
            writer.print("mCancellingTask=");
            writer.print(this.mCancellingTask);
            writer.print(" waiting=");
            writer.println(this.mCancellingTask.waiting);
        }
        if (this.mUpdateThrottle != 0) {
            writer.print(prefix);
            writer.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.mUpdateThrottle, writer);
            writer.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), writer);
            writer.println();
        }
    }
}
