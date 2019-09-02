package android.support.p000v4.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: android.support.v4.content.ModernAsyncTask */
abstract class ModernAsyncTask<Params, Progress, Result> {
    private static final int CORE_POOL_SIZE = 5;
    private static final int KEEP_ALIVE = 1;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor sDefaultExecutor = THREAD_POOL_EXECUTOR;
    private static InternalHandler sHandler;
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue(10);
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable runnable) {
            Runnable r = runnable;
            Runnable runnable2 = r;
            return new Thread(r, "ModernAsyncTask #" + this.mCount.getAndIncrement());
        }
    };
    private final AtomicBoolean mCancelled = new AtomicBoolean();
    private final FutureTask<Result> mFuture = new FutureTask<Result>(this, this.mWorker) {
        final /* synthetic */ ModernAsyncTask this$0;

        {
            ModernAsyncTask this$02 = r8;
            Callable callable = r9;
            ModernAsyncTask modernAsyncTask = this$02;
            Callable callable2 = callable;
            this.this$0 = this$02;
        }

        /* access modifiers changed from: protected */
        public void done() {
            try {
                this.this$0.postResultIfNotInvoked(get());
            } catch (InterruptedException e) {
                InterruptedException interruptedException = e;
                int w = Log.w(ModernAsyncTask.LOG_TAG, e);
            } catch (ExecutionException e2) {
                ExecutionException executionException = e2;
                throw new RuntimeException("An error occurred while executing doInBackground()", e2.getCause());
            } catch (CancellationException e3) {
                CancellationException cancellationException = e3;
                this.this$0.postResultIfNotInvoked(null);
            } catch (Throwable th) {
                Throwable th2 = th;
                throw new RuntimeException("An error occurred while executing doInBackground()", th);
            }
        }
    };
    private volatile Status mStatus = Status.PENDING;
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();
    private final WorkerRunnable<Params, Result> mWorker = new WorkerRunnable<Params, Result>(this) {
        final /* synthetic */ ModernAsyncTask this$0;

        {
            ModernAsyncTask this$02 = r5;
            ModernAsyncTask modernAsyncTask = this$02;
            this.this$0 = this$02;
        }

        public Result call() throws Exception {
            ModernAsyncTask.access$000(this.this$0).set(true);
            Result result = null;
            try {
                Process.setThreadPriority(10);
                result = this.this$0.doInBackground(this.mParams);
                Binder.flushPendingCommands();
                Object postResult = this.this$0.postResult(result);
                return result;
            } catch (Throwable th) {
                Object postResult2 = this.this$0.postResult(result);
                throw th;
            }
        }
    };

    /* renamed from: android.support.v4.content.ModernAsyncTask$4 */
    static /* synthetic */ class C01234 {
        static final /* synthetic */ int[] $SwitchMap$android$support$v4$content$ModernAsyncTask$Status = new int[Status.values().length];

        static {
            try {
                $SwitchMap$android$support$v4$content$ModernAsyncTask$Status[Status.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$support$v4$content$ModernAsyncTask$Status[Status.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* renamed from: android.support.v4.content.ModernAsyncTask$AsyncTaskResult */
    private static class AsyncTaskResult<Data> {
        final Data[] mData;
        final ModernAsyncTask mTask;

        AsyncTaskResult(ModernAsyncTask modernAsyncTask, Data... dataArr) {
            ModernAsyncTask task = modernAsyncTask;
            Data[] data = dataArr;
            ModernAsyncTask modernAsyncTask2 = task;
            Data[] dataArr2 = data;
            this.mTask = task;
            this.mData = data;
        }
    }

    /* renamed from: android.support.v4.content.ModernAsyncTask$InternalHandler */
    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            Message msg = message;
            Message message2 = msg;
            AsyncTaskResult result = (AsyncTaskResult) msg.obj;
            switch (msg.what) {
                case 1:
                    result.mTask.finish(result.mData[0]);
                    return;
                case 2:
                    result.mTask.onProgressUpdate(result.mData);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: android.support.v4.content.ModernAsyncTask$Status */
    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    /* renamed from: android.support.v4.content.ModernAsyncTask$WorkerRunnable */
    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;

        WorkerRunnable() {
        }
    }

    /* access modifiers changed from: protected */
    public abstract Result doInBackground(Params... paramsArr);

    static /* synthetic */ AtomicBoolean access$000(ModernAsyncTask modernAsyncTask) {
        ModernAsyncTask x0 = modernAsyncTask;
        ModernAsyncTask modernAsyncTask2 = x0;
        return x0.mTaskInvoked;
    }

    static /* synthetic */ AtomicBoolean access$100(ModernAsyncTask modernAsyncTask) {
        ModernAsyncTask x0 = modernAsyncTask;
        ModernAsyncTask modernAsyncTask2 = x0;
        return x0.mCancelled;
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    public ModernAsyncTask() {
    }

    private static Handler getHandler() {
        Class<ModernAsyncTask> cls = ModernAsyncTask.class;
        Class<ModernAsyncTask> cls2 = cls;
        synchronized (cls) {
            try {
                if (sHandler == null) {
                    sHandler = new InternalHandler();
                }
                InternalHandler internalHandler = sHandler;
                return internalHandler;
            } finally {
                Class<ModernAsyncTask> cls3 = cls2;
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static void setDefaultExecutor(Executor executor) {
        Executor exec = executor;
        Executor executor2 = exec;
        sDefaultExecutor = exec;
    }

    /* access modifiers changed from: 0000 */
    public void postResultIfNotInvoked(Result result) {
        Result result2 = result;
        Result result3 = result2;
        boolean z = this.mTaskInvoked.get();
        boolean z2 = z;
        if (!z) {
            Object postResult = postResult(result2);
        }
    }

    /* access modifiers changed from: 0000 */
    public Result postResult(Result result) {
        Result result2 = result;
        Result result3 = result2;
        Message obtainMessage = getHandler().obtainMessage(1, new AsyncTaskResult(this, result2));
        Message message = obtainMessage;
        obtainMessage.sendToTarget();
        return result2;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Result result) {
        Result result2 = result;
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(Progress... progressArr) {
        Progress[] progressArr2 = progressArr;
    }

    /* access modifiers changed from: protected */
    public void onCancelled(Result result) {
        Result result2 = result;
        onCancelled();
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    public final boolean cancel(boolean z) {
        boolean mayInterruptIfRunning = z;
        this.mCancelled.set(true);
        return this.mFuture.cancel(mayInterruptIfRunning);
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.mFuture.get();
    }

    public final Result get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        long timeout = j;
        TimeUnit unit = timeUnit;
        long j2 = timeout;
        TimeUnit timeUnit2 = unit;
        return this.mFuture.get(timeout, unit);
    }

    public final ModernAsyncTask<Params, Progress, Result> execute(Params... paramsArr) {
        Params[] params = paramsArr;
        Params[] paramsArr2 = params;
        return executeOnExecutor(sDefaultExecutor, params);
    }

    public final ModernAsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params... paramsArr) {
        Executor exec = executor;
        Params[] params = paramsArr;
        Executor executor2 = exec;
        Params[] paramsArr2 = params;
        if (this.mStatus != Status.PENDING) {
            switch (C01234.$SwitchMap$android$support$v4$content$ModernAsyncTask$Status[this.mStatus.ordinal()]) {
                case 1:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case 2:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.mStatus = Status.RUNNING;
        onPreExecute();
        this.mWorker.mParams = params;
        exec.execute(this.mFuture);
        return this;
    }

    public static void execute(Runnable runnable) {
        Runnable runnable2 = runnable;
        Runnable runnable3 = runnable2;
        sDefaultExecutor.execute(runnable2);
    }

    /* access modifiers changed from: protected */
    public final void publishProgress(Progress... progressArr) {
        Progress[] values = progressArr;
        Progress[] progressArr2 = values;
        if (!isCancelled()) {
            getHandler().obtainMessage(2, new AsyncTaskResult(this, values)).sendToTarget();
        }
    }

    /* access modifiers changed from: 0000 */
    public void finish(Result result) {
        Result result2 = result;
        Result result3 = result2;
        if (!isCancelled()) {
            onPostExecute(result2);
        } else {
            onCancelled(result2);
        }
        this.mStatus = Status.FINISHED;
    }
}
