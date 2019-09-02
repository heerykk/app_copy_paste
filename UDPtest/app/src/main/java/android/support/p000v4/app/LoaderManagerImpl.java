package android.support.p000v4.app;

import android.os.Bundle;
import android.support.p000v4.app.LoaderManager.LoaderCallbacks;
import android.support.p000v4.content.Loader;
import android.support.p000v4.content.Loader.OnLoadCanceledListener;
import android.support.p000v4.content.Loader.OnLoadCompleteListener;
import android.support.p000v4.util.DebugUtils;
import android.support.p000v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

/* renamed from: android.support.v4.app.LoaderManagerImpl */
/* compiled from: LoaderManager */
class LoaderManagerImpl extends LoaderManager {
    static boolean DEBUG = false;
    static final String TAG = "LoaderManager";
    boolean mCreatingLoader;
    FragmentHostCallback mHost;
    final SparseArrayCompat<LoaderInfo> mInactiveLoaders = new SparseArrayCompat<>();
    final SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat<>();
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    final String mWho;

    /* renamed from: android.support.v4.app.LoaderManagerImpl$LoaderInfo */
    /* compiled from: LoaderManager */
    final class LoaderInfo implements OnLoadCompleteListener<Object>, OnLoadCanceledListener<Object> {
        final Bundle mArgs;
        LoaderCallbacks<Object> mCallbacks;
        Object mData;
        boolean mDeliveredData;
        boolean mDestroyed;
        boolean mHaveData;
        final int mId;
        boolean mListenerRegistered;
        Loader<Object> mLoader;
        LoaderInfo mPendingLoader;
        boolean mReportNextStart;
        boolean mRetaining;
        boolean mRetainingStarted;
        boolean mStarted;
        final /* synthetic */ LoaderManagerImpl this$0;

        public LoaderInfo(LoaderManagerImpl loaderManagerImpl, int i, Bundle bundle, LoaderCallbacks<Object> loaderCallbacks) {
            LoaderManagerImpl this$02 = loaderManagerImpl;
            int id = i;
            Bundle args = bundle;
            LoaderCallbacks<Object> callbacks = loaderCallbacks;
            LoaderManagerImpl loaderManagerImpl2 = this$02;
            int i2 = id;
            Bundle bundle2 = args;
            LoaderCallbacks<Object> loaderCallbacks2 = callbacks;
            this.this$0 = this$02;
            this.mId = id;
            this.mArgs = args;
            this.mCallbacks = callbacks;
        }

        /* access modifiers changed from: 0000 */
        public void start() {
            if (this.mRetaining && this.mRetainingStarted) {
                this.mStarted = true;
            } else if (!this.mStarted) {
                this.mStarted = true;
                if (LoaderManagerImpl.DEBUG) {
                    int v = Log.v(LoaderManagerImpl.TAG, "  Starting: " + this);
                }
                if (this.mLoader == null && this.mCallbacks != null) {
                    this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
                }
                if (this.mLoader != null) {
                    if (this.mLoader.getClass().isMemberClass() && !Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
                        throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
                    }
                    if (!this.mListenerRegistered) {
                        this.mLoader.registerListener(this.mId, this);
                        this.mLoader.registerOnLoadCanceledListener(this);
                        this.mListenerRegistered = true;
                    }
                    this.mLoader.startLoading();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void retain() {
            if (LoaderManagerImpl.DEBUG) {
                int v = Log.v(LoaderManagerImpl.TAG, "  Retaining: " + this);
            }
            this.mRetaining = true;
            this.mRetainingStarted = this.mStarted;
            this.mStarted = false;
            this.mCallbacks = null;
        }

        /* access modifiers changed from: 0000 */
        public void finishRetain() {
            if (this.mRetaining) {
                if (LoaderManagerImpl.DEBUG) {
                    int v = Log.v(LoaderManagerImpl.TAG, "  Finished Retaining: " + this);
                }
                this.mRetaining = false;
                if (this.mStarted != this.mRetainingStarted && !this.mStarted) {
                    stop();
                }
            }
            if (this.mStarted && this.mHaveData && !this.mReportNextStart) {
                callOnLoadFinished(this.mLoader, this.mData);
            }
        }

        /* access modifiers changed from: 0000 */
        public void reportStart() {
            if (this.mStarted && this.mReportNextStart) {
                this.mReportNextStart = false;
                if (this.mHaveData && !this.mRetaining) {
                    callOnLoadFinished(this.mLoader, this.mData);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void stop() {
            if (LoaderManagerImpl.DEBUG) {
                int v = Log.v(LoaderManagerImpl.TAG, "  Stopping: " + this);
            }
            this.mStarted = false;
            if (!this.mRetaining && this.mLoader != null && this.mListenerRegistered) {
                this.mListenerRegistered = false;
                this.mLoader.unregisterListener(this);
                this.mLoader.unregisterOnLoadCanceledListener(this);
                this.mLoader.stopLoading();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean cancel() {
            if (LoaderManagerImpl.DEBUG) {
                int v = Log.v(LoaderManagerImpl.TAG, "  Canceling: " + this);
            }
            if (!this.mStarted || this.mLoader == null || !this.mListenerRegistered) {
                return false;
            }
            boolean cancelLoad = this.mLoader.cancelLoad();
            boolean cancelLoadResult = cancelLoad;
            if (!cancelLoad) {
                onLoadCanceled(this.mLoader);
            }
            return cancelLoadResult;
        }

        /* access modifiers changed from: 0000 */
        public void destroy() {
            if (LoaderManagerImpl.DEBUG) {
                int v = Log.v(LoaderManagerImpl.TAG, "  Destroying: " + this);
            }
            this.mDestroyed = true;
            boolean needReset = this.mDeliveredData;
            this.mDeliveredData = false;
            if (this.mCallbacks != null && this.mLoader != null && this.mHaveData && needReset) {
                if (LoaderManagerImpl.DEBUG) {
                    int v2 = Log.v(LoaderManagerImpl.TAG, "  Resetting: " + this);
                }
                String lastBecause = null;
                if (this.this$0.mHost != null) {
                    lastBecause = this.this$0.mHost.mFragmentManager.mNoTransactionsBecause;
                    this.this$0.mHost.mFragmentManager.mNoTransactionsBecause = "onLoaderReset";
                }
                try {
                    this.mCallbacks.onLoaderReset(this.mLoader);
                    if (this.this$0.mHost != null) {
                        this.this$0.mHost.mFragmentManager.mNoTransactionsBecause = lastBecause;
                    }
                } catch (Throwable th) {
                    if (this.this$0.mHost != null) {
                        this.this$0.mHost.mFragmentManager.mNoTransactionsBecause = lastBecause;
                    }
                    throw th;
                }
            }
            this.mCallbacks = null;
            this.mData = null;
            this.mHaveData = false;
            if (this.mLoader != null) {
                if (this.mListenerRegistered) {
                    this.mListenerRegistered = false;
                    this.mLoader.unregisterListener(this);
                    this.mLoader.unregisterOnLoadCanceledListener(this);
                }
                this.mLoader.reset();
            }
            if (this.mPendingLoader != null) {
                this.mPendingLoader.destroy();
            }
        }

        public void onLoadCanceled(Loader<Object> loader) {
            Loader<Object> loader2 = loader;
            if (LoaderManagerImpl.DEBUG) {
                int v = Log.v(LoaderManagerImpl.TAG, "onLoadCanceled: " + this);
            }
            if (this.mDestroyed) {
                if (LoaderManagerImpl.DEBUG) {
                    int v2 = Log.v(LoaderManagerImpl.TAG, "  Ignoring load canceled -- destroyed");
                }
            } else if (this.this$0.mLoaders.get(this.mId) == this) {
                LoaderInfo loaderInfo = this.mPendingLoader;
                LoaderInfo pending = loaderInfo;
                if (loaderInfo != null) {
                    if (LoaderManagerImpl.DEBUG) {
                        int v3 = Log.v(LoaderManagerImpl.TAG, "  Switching to pending loader: " + pending);
                    }
                    this.mPendingLoader = null;
                    this.this$0.mLoaders.put(this.mId, null);
                    destroy();
                    this.this$0.installLoader(pending);
                }
            } else {
                if (LoaderManagerImpl.DEBUG) {
                    int v4 = Log.v(LoaderManagerImpl.TAG, "  Ignoring load canceled -- not active");
                }
            }
        }

        public void onLoadComplete(Loader<Object> loader, Object obj) {
            Loader<Object> loader2 = loader;
            Object data = obj;
            Loader<Object> loader3 = loader2;
            Object obj2 = data;
            if (LoaderManagerImpl.DEBUG) {
                int v = Log.v(LoaderManagerImpl.TAG, "onLoadComplete: " + this);
            }
            if (this.mDestroyed) {
                if (LoaderManagerImpl.DEBUG) {
                    int v2 = Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- destroyed");
                }
            } else if (this.this$0.mLoaders.get(this.mId) == this) {
                LoaderInfo loaderInfo = this.mPendingLoader;
                LoaderInfo pending = loaderInfo;
                if (loaderInfo == null) {
                    if (this.mData != data || !this.mHaveData) {
                        this.mData = data;
                        this.mHaveData = true;
                        if (this.mStarted) {
                            callOnLoadFinished(loader2, data);
                        }
                    }
                    LoaderInfo loaderInfo2 = (LoaderInfo) this.this$0.mInactiveLoaders.get(this.mId);
                    LoaderInfo info = loaderInfo2;
                    if (!(loaderInfo2 == null || info == this)) {
                        info.mDeliveredData = false;
                        info.destroy();
                        this.this$0.mInactiveLoaders.remove(this.mId);
                    }
                    if (this.this$0.mHost != null && !this.this$0.hasRunningLoaders()) {
                        this.this$0.mHost.mFragmentManager.startPendingDeferredFragments();
                    }
                    return;
                }
                if (LoaderManagerImpl.DEBUG) {
                    int v3 = Log.v(LoaderManagerImpl.TAG, "  Switching to pending loader: " + pending);
                }
                this.mPendingLoader = null;
                this.this$0.mLoaders.put(this.mId, null);
                destroy();
                this.this$0.installLoader(pending);
            } else {
                if (LoaderManagerImpl.DEBUG) {
                    int v4 = Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- not active");
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void callOnLoadFinished(Loader<Object> loader, Object obj) {
            Loader<Object> loader2 = loader;
            Object data = obj;
            Loader<Object> loader3 = loader2;
            Object obj2 = data;
            if (this.mCallbacks != null) {
                String lastBecause = null;
                if (this.this$0.mHost != null) {
                    lastBecause = this.this$0.mHost.mFragmentManager.mNoTransactionsBecause;
                    this.this$0.mHost.mFragmentManager.mNoTransactionsBecause = "onLoadFinished";
                }
                try {
                    if (LoaderManagerImpl.DEBUG) {
                        int v = Log.v(LoaderManagerImpl.TAG, "  onLoadFinished in " + loader2 + ": " + loader2.dataToString(data));
                    }
                    this.mCallbacks.onLoadFinished(loader2, data);
                    this.mDeliveredData = true;
                } finally {
                    if (this.this$0.mHost != null) {
                        this.this$0.mHost.mFragmentManager.mNoTransactionsBecause = lastBecause;
                    }
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            StringBuilder sb2 = sb;
            StringBuilder append = sb.append("LoaderInfo{");
            StringBuilder append2 = sb2.append(Integer.toHexString(System.identityHashCode(this)));
            StringBuilder append3 = sb2.append(" #");
            StringBuilder append4 = sb2.append(this.mId);
            StringBuilder append5 = sb2.append(" : ");
            DebugUtils.buildShortClassTag(this.mLoader, sb2);
            StringBuilder append6 = sb2.append("}}");
            return sb2.toString();
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
            writer.print(prefix);
            writer.print("mId=");
            writer.print(this.mId);
            writer.print(" mArgs=");
            writer.println(this.mArgs);
            writer.print(prefix);
            writer.print("mCallbacks=");
            writer.println(this.mCallbacks);
            writer.print(prefix);
            writer.print("mLoader=");
            writer.println(this.mLoader);
            if (this.mLoader != null) {
                this.mLoader.dump(prefix + "  ", fd, writer, args);
            }
            if (this.mHaveData || this.mDeliveredData) {
                writer.print(prefix);
                writer.print("mHaveData=");
                writer.print(this.mHaveData);
                writer.print("  mDeliveredData=");
                writer.println(this.mDeliveredData);
                writer.print(prefix);
                writer.print("mData=");
                writer.println(this.mData);
            }
            writer.print(prefix);
            writer.print("mStarted=");
            writer.print(this.mStarted);
            writer.print(" mReportNextStart=");
            writer.print(this.mReportNextStart);
            writer.print(" mDestroyed=");
            writer.println(this.mDestroyed);
            writer.print(prefix);
            writer.print("mRetaining=");
            writer.print(this.mRetaining);
            writer.print(" mRetainingStarted=");
            writer.print(this.mRetainingStarted);
            writer.print(" mListenerRegistered=");
            writer.println(this.mListenerRegistered);
            if (this.mPendingLoader != null) {
                writer.print(prefix);
                writer.println("Pending Loader ");
                writer.print(this.mPendingLoader);
                writer.println(":");
                this.mPendingLoader.dump(prefix + "  ", fd, writer, args);
            }
        }
    }

    LoaderManagerImpl(String str, FragmentHostCallback fragmentHostCallback, boolean z) {
        String who = str;
        FragmentHostCallback host = fragmentHostCallback;
        String str2 = who;
        FragmentHostCallback fragmentHostCallback2 = host;
        boolean started = z;
        this.mWho = who;
        this.mHost = host;
        this.mStarted = started;
    }

    /* access modifiers changed from: 0000 */
    public void updateHostController(FragmentHostCallback fragmentHostCallback) {
        FragmentHostCallback host = fragmentHostCallback;
        FragmentHostCallback fragmentHostCallback2 = host;
        this.mHost = host;
    }

    private LoaderInfo createLoader(int i, Bundle bundle, LoaderCallbacks<Object> loaderCallbacks) {
        int id = i;
        Bundle args = bundle;
        LoaderCallbacks<Object> callback = loaderCallbacks;
        int i2 = id;
        Bundle bundle2 = args;
        LoaderCallbacks<Object> loaderCallbacks2 = callback;
        LoaderInfo info = new LoaderInfo(this, id, args, callback);
        info.mLoader = callback.onCreateLoader(id, args);
        return info;
    }

    /* JADX INFO: finally extract failed */
    private LoaderInfo createAndInstallLoader(int i, Bundle bundle, LoaderCallbacks<Object> loaderCallbacks) {
        int id = i;
        Bundle args = bundle;
        LoaderCallbacks<Object> callback = loaderCallbacks;
        int i2 = id;
        Bundle bundle2 = args;
        LoaderCallbacks<Object> loaderCallbacks2 = callback;
        try {
            this.mCreatingLoader = true;
            LoaderInfo info = createLoader(id, args, callback);
            installLoader(info);
            this.mCreatingLoader = false;
            return info;
        } catch (Throwable th) {
            this.mCreatingLoader = false;
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public void installLoader(LoaderInfo loaderInfo) {
        LoaderInfo info = loaderInfo;
        LoaderInfo loaderInfo2 = info;
        this.mLoaders.put(info.mId, info);
        if (this.mStarted) {
            info.start();
        }
    }

    public <D> Loader<D> initLoader(int i, Bundle bundle, LoaderCallbacks<D> loaderCallbacks) {
        int id = i;
        Bundle args = bundle;
        LoaderCallbacks<D> callback = loaderCallbacks;
        int i2 = id;
        Bundle bundle2 = args;
        LoaderCallbacks<D> loaderCallbacks2 = callback;
        if (!this.mCreatingLoader) {
            LoaderInfo info = (LoaderInfo) this.mLoaders.get(id);
            if (DEBUG) {
                int v = Log.v(TAG, "initLoader in " + this + ": args=" + args);
            }
            if (info != null) {
                if (DEBUG) {
                    int v2 = Log.v(TAG, "  Re-using existing loader " + info);
                }
                info.mCallbacks = callback;
            } else {
                info = createAndInstallLoader(id, args, callback);
                if (DEBUG) {
                    int v3 = Log.v(TAG, "  Created new loader " + info);
                }
            }
            if (info.mHaveData && this.mStarted) {
                info.callOnLoadFinished(info.mLoader, info.mData);
            }
            return info.mLoader;
        }
        throw new IllegalStateException("Called while creating a loader");
    }

    public <D> Loader<D> restartLoader(int i, Bundle bundle, LoaderCallbacks<D> loaderCallbacks) {
        int id = i;
        Bundle args = bundle;
        LoaderCallbacks<D> callback = loaderCallbacks;
        int i2 = id;
        Bundle bundle2 = args;
        LoaderCallbacks<D> loaderCallbacks2 = callback;
        if (!this.mCreatingLoader) {
            LoaderInfo info = (LoaderInfo) this.mLoaders.get(id);
            if (DEBUG) {
                int v = Log.v(TAG, "restartLoader in " + this + ": args=" + args);
            }
            if (info != null) {
                LoaderInfo loaderInfo = (LoaderInfo) this.mInactiveLoaders.get(id);
                LoaderInfo inactive = loaderInfo;
                if (loaderInfo == null) {
                    if (DEBUG) {
                        int v2 = Log.v(TAG, "  Making last loader inactive: " + info);
                    }
                    info.mLoader.abandon();
                    this.mInactiveLoaders.put(id, info);
                } else if (info.mHaveData) {
                    if (DEBUG) {
                        int v3 = Log.v(TAG, "  Removing last inactive loader: " + info);
                    }
                    inactive.mDeliveredData = false;
                    inactive.destroy();
                    info.mLoader.abandon();
                    this.mInactiveLoaders.put(id, info);
                } else if (info.cancel()) {
                    if (DEBUG) {
                        int v4 = Log.v(TAG, "  Current loader is running; configuring pending loader");
                    }
                    if (info.mPendingLoader != null) {
                        if (DEBUG) {
                            int v5 = Log.v(TAG, "  Removing pending loader: " + info.mPendingLoader);
                        }
                        info.mPendingLoader.destroy();
                        info.mPendingLoader = null;
                    }
                    if (DEBUG) {
                        int v6 = Log.v(TAG, "  Enqueuing as new pending loader");
                    }
                    info.mPendingLoader = createLoader(id, args, callback);
                    return info.mPendingLoader.mLoader;
                } else {
                    if (DEBUG) {
                        int v7 = Log.v(TAG, "  Current loader is stopped; replacing");
                    }
                    this.mLoaders.put(id, null);
                    info.destroy();
                }
            }
            LoaderInfo createAndInstallLoader = createAndInstallLoader(id, args, callback);
            LoaderInfo info2 = createAndInstallLoader;
            return createAndInstallLoader.mLoader;
        }
        throw new IllegalStateException("Called while creating a loader");
    }

    public void destroyLoader(int i) {
        int id = i;
        int i2 = id;
        if (!this.mCreatingLoader) {
            if (DEBUG) {
                int v = Log.v(TAG, "destroyLoader in " + this + " of " + id);
            }
            int indexOfKey = this.mLoaders.indexOfKey(id);
            int idx = indexOfKey;
            if (indexOfKey >= 0) {
                LoaderInfo info = (LoaderInfo) this.mLoaders.valueAt(idx);
                this.mLoaders.removeAt(idx);
                info.destroy();
            }
            int indexOfKey2 = this.mInactiveLoaders.indexOfKey(id);
            int idx2 = indexOfKey2;
            if (indexOfKey2 >= 0) {
                LoaderInfo info2 = (LoaderInfo) this.mInactiveLoaders.valueAt(idx2);
                this.mInactiveLoaders.removeAt(idx2);
                info2.destroy();
            }
            if (this.mHost != null && !hasRunningLoaders()) {
                this.mHost.mFragmentManager.startPendingDeferredFragments();
                return;
            }
            return;
        }
        throw new IllegalStateException("Called while creating a loader");
    }

    public <D> Loader<D> getLoader(int i) {
        int id = i;
        int i2 = id;
        if (!this.mCreatingLoader) {
            LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.get(id);
            LoaderInfo loaderInfo2 = loaderInfo;
            if (loaderInfo == null) {
                return null;
            }
            if (loaderInfo2.mPendingLoader == null) {
                return loaderInfo2.mLoader;
            }
            return loaderInfo2.mPendingLoader.mLoader;
        }
        throw new IllegalStateException("Called while creating a loader");
    }

    /* access modifiers changed from: 0000 */
    public void doStart() {
        if (DEBUG) {
            int v = Log.v(TAG, "Starting in " + this);
        }
        if (!this.mStarted) {
            this.mStarted = true;
            for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
                ((LoaderInfo) this.mLoaders.valueAt(i)).start();
            }
            return;
        }
        RuntimeException runtimeException = new RuntimeException("here");
        RuntimeException e = runtimeException;
        Throwable fillInStackTrace = runtimeException.fillInStackTrace();
        int w = Log.w(TAG, "Called doStart when already started: " + this, e);
    }

    /* access modifiers changed from: 0000 */
    public void doStop() {
        if (DEBUG) {
            int v = Log.v(TAG, "Stopping in " + this);
        }
        if (this.mStarted) {
            for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
                ((LoaderInfo) this.mLoaders.valueAt(i)).stop();
            }
            this.mStarted = false;
            return;
        }
        RuntimeException runtimeException = new RuntimeException("here");
        RuntimeException e = runtimeException;
        Throwable fillInStackTrace = runtimeException.fillInStackTrace();
        int w = Log.w(TAG, "Called doStop when not started: " + this, e);
    }

    /* access modifiers changed from: 0000 */
    public void doRetain() {
        if (DEBUG) {
            int v = Log.v(TAG, "Retaining in " + this);
        }
        if (this.mStarted) {
            this.mRetaining = true;
            this.mStarted = false;
            for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
                ((LoaderInfo) this.mLoaders.valueAt(i)).retain();
            }
            return;
        }
        RuntimeException runtimeException = new RuntimeException("here");
        RuntimeException e = runtimeException;
        Throwable fillInStackTrace = runtimeException.fillInStackTrace();
        int w = Log.w(TAG, "Called doRetain when not started: " + this, e);
    }

    /* access modifiers changed from: 0000 */
    public void finishRetain() {
        if (this.mRetaining) {
            if (DEBUG) {
                int v = Log.v(TAG, "Finished Retaining in " + this);
            }
            this.mRetaining = false;
            for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
                ((LoaderInfo) this.mLoaders.valueAt(i)).finishRetain();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void doReportNextStart() {
        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
            ((LoaderInfo) this.mLoaders.valueAt(i)).mReportNextStart = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void doReportStart() {
        for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
            ((LoaderInfo) this.mLoaders.valueAt(i)).reportStart();
        }
    }

    /* access modifiers changed from: 0000 */
    public void doDestroy() {
        if (!this.mRetaining) {
            if (DEBUG) {
                int v = Log.v(TAG, "Destroying Active in " + this);
            }
            for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
                ((LoaderInfo) this.mLoaders.valueAt(i)).destroy();
            }
            this.mLoaders.clear();
        }
        if (DEBUG) {
            int v2 = Log.v(TAG, "Destroying Inactive in " + this);
        }
        for (int i2 = this.mInactiveLoaders.size() - 1; i2 >= 0; i2--) {
            ((LoaderInfo) this.mInactiveLoaders.valueAt(i2)).destroy();
        }
        this.mInactiveLoaders.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        StringBuilder sb2 = sb;
        StringBuilder append = sb.append("LoaderManager{");
        StringBuilder append2 = sb2.append(Integer.toHexString(System.identityHashCode(this)));
        StringBuilder append3 = sb2.append(" in ");
        DebugUtils.buildShortClassTag(this.mHost, sb2);
        StringBuilder append4 = sb2.append("}}");
        return sb2.toString();
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
        if (this.mLoaders.size() > 0) {
            writer.print(prefix);
            writer.println("Active Loaders:");
            String innerPrefix = prefix + "    ";
            for (int i = 0; i < this.mLoaders.size(); i++) {
                LoaderInfo li = (LoaderInfo) this.mLoaders.valueAt(i);
                writer.print(prefix);
                writer.print("  #");
                writer.print(this.mLoaders.keyAt(i));
                writer.print(": ");
                writer.println(li.toString());
                li.dump(innerPrefix, fd, writer, args);
            }
        }
        if (this.mInactiveLoaders.size() > 0) {
            writer.print(prefix);
            writer.println("Inactive Loaders:");
            String innerPrefix2 = prefix + "    ";
            for (int i2 = 0; i2 < this.mInactiveLoaders.size(); i2++) {
                LoaderInfo li2 = (LoaderInfo) this.mInactiveLoaders.valueAt(i2);
                writer.print(prefix);
                writer.print("  #");
                writer.print(this.mInactiveLoaders.keyAt(i2));
                writer.print(": ");
                writer.println(li2.toString());
                li2.dump(innerPrefix2, fd, writer, args);
            }
        }
    }

    public boolean hasRunningLoaders() {
        boolean loadersRunning = false;
        for (int i = 0; i < this.mLoaders.size(); i++) {
            LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.valueAt(i);
            LoaderInfo loaderInfo2 = loaderInfo;
            loadersRunning |= loaderInfo.mStarted && !loaderInfo.mDeliveredData;
        }
        return loadersRunning;
    }
}
