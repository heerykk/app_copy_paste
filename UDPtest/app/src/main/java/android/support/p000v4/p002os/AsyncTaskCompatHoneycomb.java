package android.support.p000v4.p002os;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.support.annotation.RequiresApi;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.os.AsyncTaskCompatHoneycomb */
class AsyncTaskCompatHoneycomb {
    AsyncTaskCompatHoneycomb() {
    }

    static <Params, Progress, Result> void executeParallel(AsyncTask<Params, Progress, Result> asyncTask, Params... paramsArr) {
        AsyncTask<Params, Progress, Result> task = asyncTask;
        Params[] params = paramsArr;
        AsyncTask<Params, Progress, Result> asyncTask2 = task;
        Params[] paramsArr2 = params;
        AsyncTask executeOnExecutor = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }
}
