package android.support.p000v4.p002os;

import android.os.AsyncTask;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.os.AsyncTaskCompat */
public final class AsyncTaskCompat {
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> executeParallel(AsyncTask<Params, Progress, Result> asyncTask, Params... paramsArr) {
        AsyncTask<Params, Progress, Result> task = asyncTask;
        Params[] params = paramsArr;
        AsyncTask<Params, Progress, Result> asyncTask2 = task;
        Params[] paramsArr2 = params;
        if (task != null) {
            if (VERSION.SDK_INT < 11) {
                AsyncTask execute = task.execute(params);
            } else {
                AsyncTaskCompatHoneycomb.executeParallel(task, params);
            }
            return task;
        }
        throw new IllegalArgumentException("task can not be null");
    }

    private AsyncTaskCompat() {
    }
}
