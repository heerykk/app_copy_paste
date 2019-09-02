package android.support.p000v4.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.p000v4.app.ActivityCompatApi23.RequestPermissionsRequestCodeValidator;
import android.support.p000v4.internal.view.SupportMenu;
import android.support.p000v4.media.session.MediaControllerCompat;
import android.support.p000v4.util.SimpleArrayMap;
import android.support.p000v4.util.SparseArrayCompat;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* renamed from: android.support.v4.app.FragmentActivity */
public class FragmentActivity extends BaseFragmentActivityJB implements OnRequestPermissionsResultCallback, RequestPermissionsRequestCodeValidator {
    static final String ALLOCATED_REQUEST_INDICIES_TAG = "android:support:request_indicies";
    static final String FRAGMENTS_TAG = "android:support:fragments";
    static final int MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS = 65534;
    static final int MSG_REALLY_STOPPED = 1;
    static final int MSG_RESUME_PENDING = 2;
    static final String NEXT_CANDIDATE_REQUEST_INDEX_TAG = "android:support:next_request_index";
    static final String REQUEST_FRAGMENT_WHO_TAG = "android:support:request_fragment_who";
    private static final String TAG = "FragmentActivity";
    boolean mCreated;
    final FragmentController mFragments = FragmentController.createController(new HostCallbacks(this));
    final Handler mHandler = new Handler(this) {
        final /* synthetic */ FragmentActivity this$0;

        {
            FragmentActivity this$02 = r5;
            FragmentActivity fragmentActivity = this$02;
            this.this$0 = this$02;
        }

        public void handleMessage(Message message) {
            Message msg = message;
            Message message2 = msg;
            switch (msg.what) {
                case 1:
                    if (this.this$0.mStopped) {
                        this.this$0.doReallyStop(false);
                        return;
                    }
                    return;
                case 2:
                    this.this$0.onResumeFragments();
                    boolean execPendingActions = this.this$0.mFragments.execPendingActions();
                    return;
                default:
                    super.handleMessage(msg);
                    return;
            }
        }
    };
    int mNextCandidateRequestIndex;
    boolean mOptionsMenuInvalidated;
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    boolean mReallyStopped;
    boolean mRequestedPermissionsFromFragment;
    boolean mResumed;
    boolean mRetaining;
    boolean mStopped;

    /* renamed from: android.support.v4.app.FragmentActivity$HostCallbacks */
    class HostCallbacks extends FragmentHostCallback<FragmentActivity> {
        final /* synthetic */ FragmentActivity this$0;

        public HostCallbacks(FragmentActivity fragmentActivity) {
            FragmentActivity this$02 = fragmentActivity;
            FragmentActivity fragmentActivity2 = this$02;
            this.this$0 = this$02;
            super(this$02);
        }

        @SuppressLint({"NewApi"})
        public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            String prefix = str;
            FileDescriptor fd = fileDescriptor;
            PrintWriter writer = printWriter;
            String[] args = strArr;
            String str2 = prefix;
            FileDescriptor fileDescriptor2 = fd;
            PrintWriter printWriter2 = writer;
            String[] strArr2 = args;
            this.this$0.dump(prefix, fd, writer, args);
        }

        public boolean onShouldSaveFragmentState(Fragment fragment) {
            Fragment fragment2 = fragment;
            return !this.this$0.isFinishing();
        }

        public LayoutInflater onGetLayoutInflater() {
            return this.this$0.getLayoutInflater().cloneInContext(this.this$0);
        }

        public FragmentActivity onGetHost() {
            return this.this$0;
        }

        public void onSupportInvalidateOptionsMenu() {
            this.this$0.supportInvalidateOptionsMenu();
        }

        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i) {
            Fragment fragment2 = fragment;
            Intent intent2 = intent;
            int requestCode = i;
            Fragment fragment3 = fragment2;
            Intent intent3 = intent2;
            int i2 = requestCode;
            this.this$0.startActivityFromFragment(fragment2, intent2, requestCode);
        }

        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int i, @Nullable Bundle bundle) {
            Fragment fragment2 = fragment;
            Intent intent2 = intent;
            int requestCode = i;
            Bundle options = bundle;
            Fragment fragment3 = fragment2;
            Intent intent3 = intent2;
            int i2 = requestCode;
            Bundle bundle2 = options;
            this.this$0.startActivityFromFragment(fragment2, intent2, requestCode, options);
        }

        public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) throws SendIntentException {
            Fragment fragment2 = fragment;
            IntentSender intent2 = intentSender;
            int requestCode = i;
            Intent fillInIntent = intent;
            int flagsMask = i2;
            int flagsValues = i3;
            int extraFlags = i4;
            Bundle options = bundle;
            Fragment fragment3 = fragment2;
            IntentSender intentSender2 = intent2;
            int i5 = requestCode;
            Intent intent3 = fillInIntent;
            int i6 = flagsMask;
            int i7 = flagsValues;
            int i8 = extraFlags;
            Bundle bundle2 = options;
            this.this$0.startIntentSenderFromFragment(fragment2, intent2, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        }

        public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
            Fragment fragment2 = fragment;
            String[] permissions = strArr;
            int requestCode = i;
            Fragment fragment3 = fragment2;
            String[] strArr2 = permissions;
            int i2 = requestCode;
            this.this$0.requestPermissionsFromFragment(fragment2, permissions, requestCode);
        }

        public boolean onShouldShowRequestPermissionRationale(@NonNull String str) {
            String permission = str;
            String str2 = permission;
            return ActivityCompat.shouldShowRequestPermissionRationale(this.this$0, permission);
        }

        public boolean onHasWindowAnimations() {
            return this.this$0.getWindow() != null;
        }

        public int onGetWindowAnimations() {
            Window window = this.this$0.getWindow();
            return window != null ? window.getAttributes().windowAnimations : 0;
        }

        public void onAttachFragment(Fragment fragment) {
            Fragment fragment2 = fragment;
            Fragment fragment3 = fragment2;
            this.this$0.onAttachFragment(fragment2);
        }

        @Nullable
        public View onFindViewById(int i) {
            int id = i;
            int i2 = id;
            return this.this$0.findViewById(id);
        }

        public boolean onHasView() {
            Window window = this.this$0.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }
    }

    /* renamed from: android.support.v4.app.FragmentActivity$NonConfigurationInstances */
    static final class NonConfigurationInstances {
        Object custom;
        FragmentManagerNonConfig fragments;
        SimpleArrayMap<String, LoaderManager> loaders;

        NonConfigurationInstances() {
        }
    }

    public FragmentActivity() {
    }

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    @RequiresApi(16)
    public /* bridge */ /* synthetic */ void startActivityForResult(Intent intent, int i, @Nullable Bundle bundle) {
        super.startActivityForResult(intent, i, bundle);
    }

    public /* bridge */ /* synthetic */ void startIntentSenderForResult(IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4) throws SendIntentException {
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4);
    }

    @RequiresApi(16)
    public /* bridge */ /* synthetic */ void startIntentSenderForResult(IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) throws SendIntentException {
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        int requestCode = i;
        int resultCode = i2;
        Intent data = intent;
        int i3 = requestCode;
        int i4 = resultCode;
        Intent intent2 = data;
        this.mFragments.noteStateNotSaved();
        int i5 = requestCode >> 16;
        int requestIndex = i5;
        if (i5 == 0) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        int requestIndex2 = requestIndex - 1;
        String who = (String) this.mPendingFragmentActivityResults.get(requestIndex2);
        this.mPendingFragmentActivityResults.remove(requestIndex2);
        if (who != null) {
            Fragment findFragmentByWho = this.mFragments.findFragmentByWho(who);
            Fragment targetFragment = findFragmentByWho;
            if (findFragmentByWho != null) {
                targetFragment.onActivityResult(requestCode & SupportMenu.USER_MASK, resultCode, data);
            } else {
                int w = Log.w(TAG, "Activity result no fragment exists for who: " + who);
            }
            return;
        }
        int w2 = Log.w(TAG, "Activity result delivered for unknown Fragment.");
    }

    public void onBackPressed() {
        if (!this.mFragments.getSupportFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

    @Deprecated
    public final void setSupportMediaController(MediaControllerCompat mediaControllerCompat) {
        MediaControllerCompat mediaController = mediaControllerCompat;
        MediaControllerCompat mediaControllerCompat2 = mediaController;
        MediaControllerCompat.setMediaController(this, mediaController);
    }

    @Deprecated
    public final MediaControllerCompat getSupportMediaController() {
        return MediaControllerCompat.getMediaController(this);
    }

    public void supportFinishAfterTransition() {
        ActivityCompat.finishAfterTransition(this);
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback callback = sharedElementCallback;
        SharedElementCallback sharedElementCallback2 = callback;
        ActivityCompat.setEnterSharedElementCallback(this, callback);
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback listener = sharedElementCallback;
        SharedElementCallback sharedElementCallback2 = listener;
        ActivityCompat.setExitSharedElementCallback(this, listener);
    }

    public void supportPostponeEnterTransition() {
        ActivityCompat.postponeEnterTransition(this);
    }

    public void supportStartPostponedEnterTransition() {
        ActivityCompat.startPostponedEnterTransition(this);
    }

    @CallSuper
    public void onMultiWindowModeChanged(boolean z) {
        this.mFragments.dispatchMultiWindowModeChanged(z);
    }

    @CallSuper
    public void onPictureInPictureModeChanged(boolean z) {
        this.mFragments.dispatchPictureInPictureModeChanged(z);
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        super.onConfigurationChanged(newConfig);
        this.mFragments.dispatchConfigurationChanged(newConfig);
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        this.mFragments.attachHost(null);
        super.onCreate(savedInstanceState);
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance();
        NonConfigurationInstances nc = nonConfigurationInstances;
        if (nonConfigurationInstances != null) {
            this.mFragments.restoreLoaderNonConfig(nc.loaders);
        }
        if (savedInstanceState != null) {
            this.mFragments.restoreAllState(savedInstanceState.getParcelable(FRAGMENTS_TAG), nc == null ? null : nc.fragments);
            if (savedInstanceState.containsKey(NEXT_CANDIDATE_REQUEST_INDEX_TAG)) {
                this.mNextCandidateRequestIndex = savedInstanceState.getInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG);
                int[] requestCodes = savedInstanceState.getIntArray(ALLOCATED_REQUEST_INDICIES_TAG);
                String[] fragmentWhos = savedInstanceState.getStringArray(REQUEST_FRAGMENT_WHO_TAG);
                if (requestCodes == null || fragmentWhos == null || requestCodes.length != fragmentWhos.length) {
                    int w = Log.w(TAG, "Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.mPendingFragmentActivityResults = new SparseArrayCompat<>(requestCodes.length);
                    for (int i = 0; i < requestCodes.length; i++) {
                        this.mPendingFragmentActivityResults.put(requestCodes[i], fragmentWhos[i]);
                    }
                }
            }
        }
        if (this.mPendingFragmentActivityResults == null) {
            this.mPendingFragmentActivityResults = new SparseArrayCompat<>();
            this.mNextCandidateRequestIndex = 0;
        }
        this.mFragments.dispatchCreate();
    }

    public boolean onCreatePanelMenu(int i, Menu menu) {
        int featureId = i;
        Menu menu2 = menu;
        int i2 = featureId;
        Menu menu3 = menu2;
        if (featureId != 0) {
            return super.onCreatePanelMenu(featureId, menu2);
        }
        boolean onCreatePanelMenu = super.onCreatePanelMenu(featureId, menu2);
        boolean z = onCreatePanelMenu;
        boolean show = onCreatePanelMenu | this.mFragments.dispatchCreateOptionsMenu(menu2, getMenuInflater());
        if (VERSION.SDK_INT < 11) {
            return true;
        }
        return show;
    }

    /* access modifiers changed from: 0000 */
    public final View dispatchFragmentsOnCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View parent = view;
        String name = str;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        View view2 = parent;
        String str2 = name;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        return this.mFragments.onCreateView(parent, name, context2, attrs);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        doReallyStop(false);
        this.mFragments.dispatchDestroy();
        this.mFragments.doLoaderDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mFragments.dispatchLowMemory();
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        int featureId = i;
        MenuItem item = menuItem;
        int i2 = featureId;
        MenuItem menuItem2 = item;
        if (super.onMenuItemSelected(featureId, item)) {
            return true;
        }
        switch (featureId) {
            case 0:
                return this.mFragments.dispatchOptionsItemSelected(item);
            case 6:
                return this.mFragments.dispatchContextItemSelected(item);
            default:
                return false;
        }
    }

    public void onPanelClosed(int i, Menu menu) {
        int featureId = i;
        Menu menu2 = menu;
        int i2 = featureId;
        Menu menu3 = menu2;
        switch (featureId) {
            case 0:
                this.mFragments.dispatchOptionsMenuClosed(menu2);
                break;
        }
        super.onPanelClosed(featureId, menu2);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mResumed = false;
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
            onResumeFragments();
        }
        this.mFragments.dispatchPause();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        super.onNewIntent(intent2);
        this.mFragments.noteStateNotSaved();
    }

    public void onStateNotSaved() {
        this.mFragments.noteStateNotSaved();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        boolean sendEmptyMessage = this.mHandler.sendEmptyMessage(2);
        this.mResumed = true;
        boolean execPendingActions = this.mFragments.execPendingActions();
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        this.mHandler.removeMessages(2);
        onResumeFragments();
        boolean execPendingActions = this.mFragments.execPendingActions();
    }

    /* access modifiers changed from: protected */
    public void onResumeFragments() {
        this.mFragments.dispatchResume();
    }

    public boolean onPreparePanel(int i, View view, Menu menu) {
        int featureId = i;
        View view2 = view;
        Menu menu2 = menu;
        int i2 = featureId;
        View view3 = view2;
        Menu menu3 = menu2;
        if (featureId == 0 && menu2 != null) {
            if (this.mOptionsMenuInvalidated) {
                this.mOptionsMenuInvalidated = false;
                menu2.clear();
                boolean onCreatePanelMenu = onCreatePanelMenu(featureId, menu2);
            }
            boolean onPrepareOptionsPanel = onPrepareOptionsPanel(view2, menu2);
            boolean z = onPrepareOptionsPanel;
            boolean dispatchPrepareOptionsMenu = onPrepareOptionsPanel | this.mFragments.dispatchPrepareOptionsMenu(menu2);
            boolean goforit = dispatchPrepareOptionsMenu;
            return dispatchPrepareOptionsMenu;
        }
        return super.onPreparePanel(featureId, view2, menu2);
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean onPrepareOptionsPanel(View view, Menu menu) {
        View view2 = view;
        Menu menu2 = menu;
        View view3 = view2;
        Menu menu3 = menu2;
        return super.onPreparePanel(0, view2, menu2);
    }

    public final Object onRetainNonConfigurationInstance() {
        if (this.mStopped) {
            doReallyStop(true);
        }
        Object custom = onRetainCustomNonConfigurationInstance();
        FragmentManagerNonConfig fragments = this.mFragments.retainNestedNonConfig();
        SimpleArrayMap retainLoaderNonConfig = this.mFragments.retainLoaderNonConfig();
        if (fragments == null && retainLoaderNonConfig == null && custom == null) {
            return null;
        }
        NonConfigurationInstances nonConfigurationInstances = new NonConfigurationInstances();
        NonConfigurationInstances nci = nonConfigurationInstances;
        nonConfigurationInstances.custom = custom;
        nci.fragments = fragments;
        nci.loaders = retainLoaderNonConfig;
        return nci;
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        Bundle outState = bundle;
        Bundle bundle2 = outState;
        super.onSaveInstanceState(outState);
        Parcelable saveAllState = this.mFragments.saveAllState();
        Parcelable p = saveAllState;
        if (saveAllState != null) {
            outState.putParcelable(FRAGMENTS_TAG, p);
        }
        if (this.mPendingFragmentActivityResults.size() > 0) {
            outState.putInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG, this.mNextCandidateRequestIndex);
            int[] requestCodes = new int[this.mPendingFragmentActivityResults.size()];
            String[] fragmentWhos = new String[this.mPendingFragmentActivityResults.size()];
            for (int i = 0; i < this.mPendingFragmentActivityResults.size(); i++) {
                requestCodes[i] = this.mPendingFragmentActivityResults.keyAt(i);
                fragmentWhos[i] = (String) this.mPendingFragmentActivityResults.valueAt(i);
            }
            outState.putIntArray(ALLOCATED_REQUEST_INDICIES_TAG, requestCodes);
            outState.putStringArray(REQUEST_FRAGMENT_WHO_TAG, fragmentWhos);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mStopped = false;
        this.mReallyStopped = false;
        this.mHandler.removeMessages(1);
        if (!this.mCreated) {
            this.mCreated = true;
            this.mFragments.dispatchActivityCreated();
        }
        this.mFragments.noteStateNotSaved();
        boolean execPendingActions = this.mFragments.execPendingActions();
        this.mFragments.doLoaderStart();
        this.mFragments.dispatchStart();
        this.mFragments.reportLoaderStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mStopped = true;
        boolean sendEmptyMessage = this.mHandler.sendEmptyMessage(1);
        this.mFragments.dispatchStop();
    }

    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    public Object getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance();
        NonConfigurationInstances nc = nonConfigurationInstances;
        if (nonConfigurationInstances == null) {
            return null;
        }
        return nc.custom;
    }

    public void supportInvalidateOptionsMenu() {
        if (VERSION.SDK_INT < 11) {
            this.mOptionsMenuInvalidated = true;
        } else {
            ActivityCompatHoneycomb.invalidateOptionsMenu(this);
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
        if (VERSION.SDK_INT < 11) {
        }
        writer.print(prefix);
        writer.print("Local FragmentActivity ");
        writer.print(Integer.toHexString(System.identityHashCode(this)));
        writer.println(" State:");
        String innerPrefix = prefix + "  ";
        writer.print(innerPrefix);
        writer.print("mCreated=");
        writer.print(this.mCreated);
        writer.print("mResumed=");
        writer.print(this.mResumed);
        writer.print(" mStopped=");
        writer.print(this.mStopped);
        writer.print(" mReallyStopped=");
        writer.println(this.mReallyStopped);
        this.mFragments.dumpLoaders(innerPrefix, fd, writer, args);
        this.mFragments.getSupportFragmentManager().dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.println("View Hierarchy:");
        dumpViewHierarchy(prefix + "  ", writer, getWindow().getDecorView());
    }

    private static String viewToString(View view) {
        String pkgname;
        View view2 = view;
        View view3 = view2;
        StringBuilder sb = new StringBuilder(128);
        StringBuilder out = sb;
        StringBuilder append = sb.append(view2.getClass().getName());
        StringBuilder append2 = out.append('{');
        StringBuilder append3 = out.append(Integer.toHexString(System.identityHashCode(view2)));
        StringBuilder append4 = out.append(' ');
        switch (view2.getVisibility()) {
            case 0:
                StringBuilder append5 = out.append('V');
                break;
            case 4:
                StringBuilder append6 = out.append('I');
                break;
            case 8:
                StringBuilder append7 = out.append('G');
                break;
            default:
                StringBuilder append8 = out.append('.');
                break;
        }
        StringBuilder append9 = out.append(!view2.isFocusable() ? '.' : 'F');
        StringBuilder append10 = out.append(!view2.isEnabled() ? '.' : 'E');
        StringBuilder append11 = out.append(!view2.willNotDraw() ? 'D' : '.');
        StringBuilder append12 = out.append(!view2.isHorizontalScrollBarEnabled() ? '.' : 'H');
        StringBuilder append13 = out.append(!view2.isVerticalScrollBarEnabled() ? '.' : 'V');
        StringBuilder append14 = out.append(!view2.isClickable() ? '.' : 'C');
        StringBuilder append15 = out.append(!view2.isLongClickable() ? '.' : 'L');
        StringBuilder append16 = out.append(' ');
        StringBuilder append17 = out.append(!view2.isFocused() ? '.' : 'F');
        StringBuilder append18 = out.append(!view2.isSelected() ? '.' : 'S');
        StringBuilder append19 = out.append(!view2.isPressed() ? '.' : 'P');
        StringBuilder append20 = out.append(' ');
        StringBuilder append21 = out.append(view2.getLeft());
        StringBuilder append22 = out.append(',');
        StringBuilder append23 = out.append(view2.getTop());
        StringBuilder append24 = out.append('-');
        StringBuilder append25 = out.append(view2.getRight());
        StringBuilder append26 = out.append(',');
        StringBuilder append27 = out.append(view2.getBottom());
        int id = view2.getId();
        int id2 = id;
        if (id != -1) {
            StringBuilder append28 = out.append(" #");
            StringBuilder append29 = out.append(Integer.toHexString(id2));
            Resources resources = view2.getResources();
            Resources r = resources;
            if (!(id2 == 0 || resources == null)) {
                switch (id2 & ViewCompat.MEASURED_STATE_MASK) {
                    case 16777216:
                        pkgname = "android";
                        break;
                    case 2130706432:
                        pkgname = "app";
                        break;
                    default:
                        try {
                            pkgname = r.getResourcePackageName(id2);
                            break;
                        } catch (NotFoundException e) {
                            break;
                        }
                }
                String typename = r.getResourceTypeName(id2);
                String entryname = r.getResourceEntryName(id2);
                StringBuilder append30 = out.append(" ");
                StringBuilder append31 = out.append(pkgname);
                StringBuilder append32 = out.append(":");
                StringBuilder append33 = out.append(typename);
                StringBuilder append34 = out.append("/");
                StringBuilder append35 = out.append(entryname);
            }
        }
        StringBuilder append36 = out.append("}");
        return out.toString();
    }

    private void dumpViewHierarchy(String str, PrintWriter printWriter, View view) {
        String prefix = str;
        PrintWriter writer = printWriter;
        View view2 = view;
        String str2 = prefix;
        PrintWriter printWriter2 = writer;
        View view3 = view2;
        writer.print(prefix);
        if (view2 != null) {
            writer.println(viewToString(view2));
            if (view2 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view2;
                ViewGroup grp = viewGroup;
                int childCount = viewGroup.getChildCount();
                int N = childCount;
                if (childCount > 0) {
                    String prefix2 = prefix + "  ";
                    for (int i = 0; i < N; i++) {
                        dumpViewHierarchy(prefix2, writer, grp.getChildAt(i));
                    }
                    return;
                }
                return;
            }
            return;
        }
        writer.println("null");
    }

    /* access modifiers changed from: 0000 */
    public void doReallyStop(boolean z) {
        boolean retaining = z;
        if (!this.mReallyStopped) {
            this.mReallyStopped = true;
            this.mRetaining = retaining;
            this.mHandler.removeMessages(1);
            onReallyStop();
        } else if (retaining) {
            this.mFragments.doLoaderStart();
            this.mFragments.doLoaderStop(true);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onReallyStop() {
        this.mFragments.doLoaderStop(this.mRetaining);
        this.mFragments.dispatchReallyStop();
    }

    public void onAttachFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mFragments.getSupportFragmentManager();
    }

    public LoaderManager getSupportLoaderManager() {
        return this.mFragments.getSupportLoaderManager();
    }

    public void startActivityForResult(Intent intent, int i) {
        Intent intent2 = intent;
        int requestCode = i;
        Intent intent3 = intent2;
        int i2 = requestCode;
        if (!this.mStartedActivityFromFragment && requestCode != -1) {
            checkForValidRequestCode(requestCode);
        }
        super.startActivityForResult(intent2, requestCode);
    }

    public final void validateRequestPermissionsRequestCode(int i) {
        int requestCode = i;
        int i2 = requestCode;
        if (!this.mRequestedPermissionsFromFragment && requestCode != -1) {
            checkForValidRequestCode(requestCode);
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        int requestCode = i;
        String[] permissions = strArr;
        int[] grantResults = iArr;
        int i2 = requestCode;
        String[] strArr2 = permissions;
        int[] iArr2 = grantResults;
        int i3 = (requestCode >> 16) & SupportMenu.USER_MASK;
        int index = i3;
        if (i3 != 0) {
            int index2 = index - 1;
            String who = (String) this.mPendingFragmentActivityResults.get(index2);
            this.mPendingFragmentActivityResults.remove(index2);
            if (who != null) {
                Fragment findFragmentByWho = this.mFragments.findFragmentByWho(who);
                Fragment frag = findFragmentByWho;
                if (findFragmentByWho != null) {
                    frag.onRequestPermissionsResult(requestCode & SupportMenu.USER_MASK, permissions, grantResults);
                } else {
                    int w = Log.w(TAG, "Activity result no fragment exists for who: " + who);
                }
            } else {
                int w2 = Log.w(TAG, "Activity result delivered for unknown Fragment.");
            }
        }
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int i) {
        Fragment fragment2 = fragment;
        Intent intent2 = intent;
        int requestCode = i;
        Fragment fragment3 = fragment2;
        Intent intent3 = intent2;
        int i2 = requestCode;
        startActivityFromFragment(fragment2, intent2, requestCode, null);
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int i, @Nullable Bundle bundle) {
        Fragment fragment2 = fragment;
        Intent intent2 = intent;
        int requestCode = i;
        Bundle options = bundle;
        Fragment fragment3 = fragment2;
        Intent intent3 = intent2;
        int i2 = requestCode;
        Bundle bundle2 = options;
        this.mStartedActivityFromFragment = true;
        if (requestCode != -1) {
            try {
                checkForValidRequestCode(requestCode);
                int allocateRequestIndex = allocateRequestIndex(fragment2);
                int i3 = allocateRequestIndex;
                ActivityCompat.startActivityForResult(this, intent2, ((allocateRequestIndex + 1) << 16) + (requestCode & SupportMenu.USER_MASK), options);
            } finally {
                this.mStartedActivityFromFragment = false;
            }
        } else {
            ActivityCompat.startActivityForResult(this, intent2, -1, options);
            this.mStartedActivityFromFragment = false;
        }
    }

    public void startIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) throws SendIntentException {
        Fragment fragment2 = fragment;
        IntentSender intent2 = intentSender;
        int requestCode = i;
        Intent fillInIntent = intent;
        int flagsMask = i2;
        int flagsValues = i3;
        int extraFlags = i4;
        Bundle options = bundle;
        Fragment fragment3 = fragment2;
        IntentSender intentSender2 = intent2;
        int i5 = requestCode;
        Intent intent3 = fillInIntent;
        int i6 = flagsMask;
        int i7 = flagsValues;
        int i8 = extraFlags;
        Bundle bundle2 = options;
        this.mStartedIntentSenderFromFragment = true;
        if (requestCode != -1) {
            try {
                checkForValidRequestCode(requestCode);
                int allocateRequestIndex = allocateRequestIndex(fragment2);
                int i9 = allocateRequestIndex;
                ActivityCompat.startIntentSenderForResult(this, intent2, ((allocateRequestIndex + 1) << 16) + (requestCode & SupportMenu.USER_MASK), fillInIntent, flagsMask, flagsValues, extraFlags, options);
            } finally {
                this.mStartedIntentSenderFromFragment = false;
            }
        } else {
            ActivityCompat.startIntentSenderForResult(this, intent2, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
            this.mStartedIntentSenderFromFragment = false;
        }
    }

    private int allocateRequestIndex(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        if (this.mPendingFragmentActivityResults.size() < MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS) {
            while (this.mPendingFragmentActivityResults.indexOfKey(this.mNextCandidateRequestIndex) >= 0) {
                this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS;
            }
            int i = this.mNextCandidateRequestIndex;
            int i2 = i;
            this.mPendingFragmentActivityResults.put(i, fragment2.mWho);
            this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS;
            return i;
        }
        throw new IllegalStateException("Too many pending Fragment activity results.");
    }

    /* access modifiers changed from: 0000 */
    public void requestPermissionsFromFragment(Fragment fragment, String[] strArr, int i) {
        Fragment fragment2 = fragment;
        String[] permissions = strArr;
        int requestCode = i;
        Fragment fragment3 = fragment2;
        String[] strArr2 = permissions;
        int i2 = requestCode;
        if (requestCode != -1) {
            checkForValidRequestCode(requestCode);
            try {
                this.mRequestedPermissionsFromFragment = true;
                int allocateRequestIndex = allocateRequestIndex(fragment2);
                int i3 = allocateRequestIndex;
                ActivityCompat.requestPermissions(this, permissions, ((allocateRequestIndex + 1) << 16) + (requestCode & SupportMenu.USER_MASK));
            } finally {
                this.mRequestedPermissionsFromFragment = false;
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }
}
