package android.support.p000v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* renamed from: android.support.v4.app.FragmentHostCallback */
public abstract class FragmentHostCallback<E> extends FragmentContainer {
    private final Activity mActivity;
    private SimpleArrayMap<String, LoaderManager> mAllLoaderManagers;
    private boolean mCheckedForLoaderManager;
    final Context mContext;
    final FragmentManagerImpl mFragmentManager;
    private final Handler mHandler;
    private LoaderManagerImpl mLoaderManager;
    private boolean mLoadersStarted;
    private boolean mRetainLoaders;
    final int mWindowAnimations;

    @Nullable
    public abstract E onGetHost();

    FragmentHostCallback(Activity activity, Context context, Handler handler, int i) {
        Activity activity2 = activity;
        Context context2 = context;
        Handler handler2 = handler;
        int windowAnimations = i;
        Activity activity3 = activity2;
        Context context3 = context2;
        Handler handler3 = handler2;
        int i2 = windowAnimations;
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = activity2;
        this.mContext = context2;
        this.mHandler = handler2;
        this.mWindowAnimations = windowAnimations;
    }

    public FragmentHostCallback(Context context, Handler handler, int i) {
        Context context2 = context;
        Handler handler2 = handler;
        int windowAnimations = i;
        Context context3 = context2;
        Handler handler3 = handler2;
        int i2 = windowAnimations;
        this(!(context2 instanceof Activity) ? null : (Activity) context2, context2, handler2, windowAnimations);
    }

    FragmentHostCallback(FragmentActivity fragmentActivity) {
        FragmentActivity activity = fragmentActivity;
        FragmentActivity fragmentActivity2 = activity;
        this(activity, activity, activity.mHandler, 0);
    }

    public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2 = str;
        FileDescriptor fileDescriptor2 = fileDescriptor;
        PrintWriter printWriter2 = printWriter;
        String[] strArr2 = strArr;
    }

    public boolean onShouldSaveFragmentState(Fragment fragment) {
        Fragment fragment2 = fragment;
        return true;
    }

    public LayoutInflater onGetLayoutInflater() {
        return (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public void onSupportInvalidateOptionsMenu() {
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i) {
        Fragment fragment2 = fragment;
        Intent intent2 = intent;
        int requestCode = i;
        Fragment fragment3 = fragment2;
        Intent intent3 = intent2;
        int i2 = requestCode;
        onStartActivityFromFragment(fragment2, intent2, requestCode, null);
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i, @Nullable Bundle bundle) {
        Intent intent2 = intent;
        int requestCode = i;
        Fragment fragment2 = fragment;
        Intent intent3 = intent2;
        int i2 = requestCode;
        Bundle bundle2 = bundle;
        if (requestCode == -1) {
            this.mContext.startActivity(intent2);
            return;
        }
        throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
    }

    public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) throws SendIntentException {
        IntentSender intent2 = intentSender;
        int requestCode = i;
        Intent fillInIntent = intent;
        int flagsMask = i2;
        int flagsValues = i3;
        int extraFlags = i4;
        Bundle options = bundle;
        Fragment fragment2 = fragment;
        IntentSender intentSender2 = intent2;
        int i5 = requestCode;
        Intent intent3 = fillInIntent;
        int i6 = flagsMask;
        int i7 = flagsValues;
        int i8 = extraFlags;
        Bundle bundle2 = options;
        if (requestCode == -1) {
            ActivityCompat.startIntentSenderForResult(this.mActivity, intent2, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        } else {
            IllegalStateException illegalStateException = new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
            throw illegalStateException;
        }
    }

    public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
        Fragment fragment2 = fragment;
        String[] strArr2 = strArr;
        int i2 = i;
    }

    public boolean onShouldShowRequestPermissionRationale(@NonNull String str) {
        String str2 = str;
        return false;
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public int onGetWindowAnimations() {
        return this.mWindowAnimations;
    }

    @Nullable
    public View onFindViewById(int i) {
        int i2 = i;
        return null;
    }

    public boolean onHasView() {
        return true;
    }

    /* access modifiers changed from: 0000 */
    public Activity getActivity() {
        return this.mActivity;
    }

    /* access modifiers changed from: 0000 */
    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: 0000 */
    public Handler getHandler() {
        return this.mHandler;
    }

    /* access modifiers changed from: 0000 */
    public FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager;
    }

    /* access modifiers changed from: 0000 */
    public LoaderManagerImpl getLoaderManagerImpl() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    /* access modifiers changed from: 0000 */
    public void inactivateFragment(String str) {
        String who = str;
        String str2 = who;
        if (this.mAllLoaderManagers != null) {
            LoaderManagerImpl loaderManagerImpl = (LoaderManagerImpl) this.mAllLoaderManagers.get(who);
            LoaderManagerImpl lm = loaderManagerImpl;
            if (loaderManagerImpl != null && !lm.mRetaining) {
                lm.doDestroy();
                Object remove = this.mAllLoaderManagers.remove(who);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onAttachFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
    }

    /* access modifiers changed from: 0000 */
    public boolean getRetainLoaders() {
        return this.mRetainLoaders;
    }

    /* access modifiers changed from: 0000 */
    public void doLoaderStart() {
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (this.mLoaderManager != null) {
                this.mLoaderManager.doStart();
            } else if (!this.mCheckedForLoaderManager) {
                this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, false);
                if (this.mLoaderManager != null && !this.mLoaderManager.mStarted) {
                    this.mLoaderManager.doStart();
                }
            }
            this.mCheckedForLoaderManager = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void doLoaderStop(boolean z) {
        boolean retain = z;
        this.mRetainLoaders = retain;
        if (this.mLoaderManager != null && this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (!retain) {
                this.mLoaderManager.doStop();
            } else {
                this.mLoaderManager.doRetain();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void doLoaderRetain() {
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doRetain();
        }
    }

    /* access modifiers changed from: 0000 */
    public void doLoaderDestroy() {
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doDestroy();
        }
    }

    /* access modifiers changed from: 0000 */
    public void reportLoaderStart() {
        if (this.mAllLoaderManagers != null) {
            int size = this.mAllLoaderManagers.size();
            int N = size;
            LoaderManagerImpl[] loaders = new LoaderManagerImpl[size];
            for (int i = N - 1; i >= 0; i--) {
                loaders[i] = (LoaderManagerImpl) this.mAllLoaderManagers.valueAt(i);
            }
            for (int i2 = 0; i2 < N; i2++) {
                LoaderManagerImpl loaderManagerImpl = loaders[i2];
                LoaderManagerImpl lm = loaderManagerImpl;
                loaderManagerImpl.finishRetain();
                lm.doReportStart();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public LoaderManagerImpl getLoaderManager(String str, boolean z, boolean z2) {
        String who = str;
        String str2 = who;
        boolean started = z;
        boolean create = z2;
        if (this.mAllLoaderManagers == null) {
            this.mAllLoaderManagers = new SimpleArrayMap<>();
        }
        LoaderManagerImpl loaderManagerImpl = (LoaderManagerImpl) this.mAllLoaderManagers.get(who);
        LoaderManagerImpl lm = loaderManagerImpl;
        if (loaderManagerImpl != null) {
            lm.updateHostController(this);
        } else if (create) {
            lm = new LoaderManagerImpl(who, this, started);
            Object put = this.mAllLoaderManagers.put(who, lm);
        }
        return lm;
    }

    /* access modifiers changed from: 0000 */
    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        boolean retainLoaders = false;
        if (this.mAllLoaderManagers != null) {
            int size = this.mAllLoaderManagers.size();
            int N = size;
            LoaderManagerImpl[] loaders = new LoaderManagerImpl[size];
            for (int i = N - 1; i >= 0; i--) {
                loaders[i] = (LoaderManagerImpl) this.mAllLoaderManagers.valueAt(i);
            }
            int i2 = getRetainLoaders();
            for (int i3 = 0; i3 < N; i3++) {
                LoaderManagerImpl loaderManagerImpl = loaders[i3];
                LoaderManagerImpl lm = loaderManagerImpl;
                if (!loaderManagerImpl.mRetaining && i2 != 0) {
                    if (!lm.mStarted) {
                        lm.doStart();
                    }
                    lm.doRetain();
                }
                if (!lm.mRetaining) {
                    lm.doDestroy();
                    Object remove = this.mAllLoaderManagers.remove(lm.mWho);
                } else {
                    retainLoaders = true;
                }
            }
        }
        if (!retainLoaders) {
            return null;
        }
        return this.mAllLoaderManagers;
    }

    /* access modifiers changed from: 0000 */
    public void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
        SimpleArrayMap<String, LoaderManager> loaderManagers = simpleArrayMap;
        SimpleArrayMap<String, LoaderManager> simpleArrayMap2 = loaderManagers;
        this.mAllLoaderManagers = loaderManagers;
    }

    /* access modifiers changed from: 0000 */
    public void dumpLoaders(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String prefix = str;
        FileDescriptor fd = fileDescriptor;
        PrintWriter writer = printWriter;
        String[] args = strArr;
        String str2 = prefix;
        FileDescriptor fileDescriptor2 = fd;
        PrintWriter printWriter2 = writer;
        String[] strArr2 = args;
        writer.print(prefix);
        writer.print("mLoadersStarted=");
        writer.println(this.mLoadersStarted);
        if (this.mLoaderManager != null) {
            writer.print(prefix);
            writer.print("Loader Manager ");
            writer.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
            writer.println(":");
            this.mLoaderManager.dump(prefix + "  ", fd, writer, args);
        }
    }
}
