package android.support.p000v4.app;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.p000v4.util.DebugUtils;
import android.support.p000v4.util.SimpleArrayMap;
import android.support.p000v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* renamed from: android.support.v4.app.Fragment */
public class Fragment implements ComponentCallbacks, OnCreateContextMenuListener {
    static final int ACTIVITY_CREATED = 2;
    static final int CREATED = 1;
    static final int INITIALIZING = 0;
    static final int RESUMED = 5;
    static final int STARTED = 4;
    static final int STOPPED = 3;
    static final Object USE_DEFAULT_TRANSITION = new Object();
    private static final SimpleArrayMap<String, Class<?>> sClassMap = new SimpleArrayMap<>();
    boolean mAdded;
    AnimationInfo mAnimationInfo;
    Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    boolean mCheckedForLoaderManager;
    FragmentManagerImpl mChildFragmentManager;
    FragmentManagerNonConfig mChildNonConfig;
    ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    int mFragmentId;
    FragmentManagerImpl mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    FragmentHostCallback mHost;
    boolean mInLayout;
    int mIndex = -1;
    View mInnerView;
    boolean mIsNewlyAdded;
    LoaderManagerImpl mLoaderManager;
    boolean mLoadersStarted;
    boolean mMenuVisible = true;
    Fragment mParentFragment;
    float mPostponedAlpha;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    Bundle mSavedFragmentState;
    SparseArray<Parcelable> mSavedViewState;
    int mState = 0;
    String mTag;
    Fragment mTarget;
    int mTargetIndex = -1;
    int mTargetRequestCode;
    boolean mUserVisibleHint = true;
    View mView;
    String mWho;

    /* renamed from: android.support.v4.app.Fragment$AnimationInfo */
    static class AnimationInfo {
        private Boolean mAllowEnterTransitionOverlap;
        private Boolean mAllowReturnTransitionOverlap;
        View mAnimatingAway;
        private Object mEnterTransition = null;
        SharedElementCallback mEnterTransitionCallback = null;
        boolean mEnterTransitionPostponed;
        private Object mExitTransition = null;
        SharedElementCallback mExitTransitionCallback = null;
        boolean mIsHideReplaced;
        int mNextAnim;
        int mNextTransition;
        int mNextTransitionStyle;
        private Object mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
        private Object mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
        private Object mSharedElementEnterTransition = null;
        private Object mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
        OnStartEnterTransitionListener mStartEnterTransitionListener;
        int mStateAfterAnimating;

        AnimationInfo() {
        }

        static /* synthetic */ Object access$000(AnimationInfo animationInfo) {
            AnimationInfo x0 = animationInfo;
            AnimationInfo animationInfo2 = x0;
            return x0.mEnterTransition;
        }

        static /* synthetic */ Object access$002(AnimationInfo animationInfo, Object obj) {
            AnimationInfo x0 = animationInfo;
            Object x1 = obj;
            AnimationInfo animationInfo2 = x0;
            Object obj2 = x1;
            x0.mEnterTransition = x1;
            return x1;
        }

        static /* synthetic */ Object access$100(AnimationInfo animationInfo) {
            AnimationInfo x0 = animationInfo;
            AnimationInfo animationInfo2 = x0;
            return x0.mReturnTransition;
        }

        static /* synthetic */ Object access$102(AnimationInfo animationInfo, Object obj) {
            AnimationInfo x0 = animationInfo;
            Object x1 = obj;
            AnimationInfo animationInfo2 = x0;
            Object obj2 = x1;
            x0.mReturnTransition = x1;
            return x1;
        }

        static /* synthetic */ Object access$200(AnimationInfo animationInfo) {
            AnimationInfo x0 = animationInfo;
            AnimationInfo animationInfo2 = x0;
            return x0.mExitTransition;
        }

        static /* synthetic */ Object access$202(AnimationInfo animationInfo, Object obj) {
            AnimationInfo x0 = animationInfo;
            Object x1 = obj;
            AnimationInfo animationInfo2 = x0;
            Object obj2 = x1;
            x0.mExitTransition = x1;
            return x1;
        }

        static /* synthetic */ Object access$300(AnimationInfo animationInfo) {
            AnimationInfo x0 = animationInfo;
            AnimationInfo animationInfo2 = x0;
            return x0.mReenterTransition;
        }

        static /* synthetic */ Object access$302(AnimationInfo animationInfo, Object obj) {
            AnimationInfo x0 = animationInfo;
            Object x1 = obj;
            AnimationInfo animationInfo2 = x0;
            Object obj2 = x1;
            x0.mReenterTransition = x1;
            return x1;
        }

        static /* synthetic */ Object access$400(AnimationInfo animationInfo) {
            AnimationInfo x0 = animationInfo;
            AnimationInfo animationInfo2 = x0;
            return x0.mSharedElementEnterTransition;
        }

        static /* synthetic */ Object access$402(AnimationInfo animationInfo, Object obj) {
            AnimationInfo x0 = animationInfo;
            Object x1 = obj;
            AnimationInfo animationInfo2 = x0;
            Object obj2 = x1;
            x0.mSharedElementEnterTransition = x1;
            return x1;
        }

        static /* synthetic */ Object access$500(AnimationInfo animationInfo) {
            AnimationInfo x0 = animationInfo;
            AnimationInfo animationInfo2 = x0;
            return x0.mSharedElementReturnTransition;
        }

        static /* synthetic */ Object access$502(AnimationInfo animationInfo, Object obj) {
            AnimationInfo x0 = animationInfo;
            Object x1 = obj;
            AnimationInfo animationInfo2 = x0;
            Object obj2 = x1;
            x0.mSharedElementReturnTransition = x1;
            return x1;
        }

        static /* synthetic */ Boolean access$600(AnimationInfo animationInfo) {
            AnimationInfo x0 = animationInfo;
            AnimationInfo animationInfo2 = x0;
            return x0.mAllowEnterTransitionOverlap;
        }

        static /* synthetic */ Boolean access$602(AnimationInfo animationInfo, Boolean bool) {
            AnimationInfo x0 = animationInfo;
            Boolean x1 = bool;
            AnimationInfo animationInfo2 = x0;
            Boolean bool2 = x1;
            x0.mAllowEnterTransitionOverlap = x1;
            return x1;
        }

        static /* synthetic */ Boolean access$700(AnimationInfo animationInfo) {
            AnimationInfo x0 = animationInfo;
            AnimationInfo animationInfo2 = x0;
            return x0.mAllowReturnTransitionOverlap;
        }

        static /* synthetic */ Boolean access$702(AnimationInfo animationInfo, Boolean bool) {
            AnimationInfo x0 = animationInfo;
            Boolean x1 = bool;
            AnimationInfo animationInfo2 = x0;
            Boolean bool2 = x1;
            x0.mAllowReturnTransitionOverlap = x1;
            return x1;
        }
    }

    /* renamed from: android.support.v4.app.Fragment$InstantiationException */
    public static class InstantiationException extends RuntimeException {
        public InstantiationException(String str, Exception exc) {
            String msg = str;
            Exception cause = exc;
            String str2 = msg;
            Exception exc2 = cause;
            super(msg, cause);
        }
    }

    /* renamed from: android.support.v4.app.Fragment$OnStartEnterTransitionListener */
    interface OnStartEnterTransitionListener {
        void onStartEnterTransition();

        void startListening();
    }

    /* renamed from: android.support.v4.app.Fragment$SavedState */
    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                Parcel in = parcel;
                Parcel parcel2 = in;
                return new SavedState(in, null);
            }

            public SavedState[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new SavedState[size];
            }
        };
        final Bundle mState;

        SavedState(Bundle bundle) {
            Bundle state = bundle;
            Bundle bundle2 = state;
            this.mState = state;
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            Parcel in = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = in;
            ClassLoader classLoader2 = loader;
            this.mState = in.readBundle();
            if (loader != null && this.mState != null) {
                this.mState.setClassLoader(loader);
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            Parcel parcel2 = dest;
            int i2 = i;
            dest.writeBundle(this.mState);
        }
    }

    static /* synthetic */ void access$800(Fragment fragment) {
        Fragment x0 = fragment;
        Fragment fragment2 = x0;
        x0.callStartTransitionListener();
    }

    public Fragment() {
    }

    public static Fragment instantiate(Context context, String str) {
        Context context2 = context;
        String fname = str;
        Context context3 = context2;
        String str2 = fname;
        return instantiate(context2, fname, null);
    }

    public static Fragment instantiate(Context context, String str, @Nullable Bundle bundle) {
        Context context2 = context;
        String fname = str;
        Bundle args = bundle;
        Context context3 = context2;
        String str2 = fname;
        Bundle bundle2 = args;
        try {
            Class cls = (Class) sClassMap.get(fname);
            Class cls2 = cls;
            if (cls == null) {
                cls2 = context2.getClassLoader().loadClass(fname);
                Object put = sClassMap.put(fname, cls2);
            }
            Fragment f = (Fragment) cls2.newInstance();
            if (args != null) {
                args.setClassLoader(f.getClass().getClassLoader());
                f.mArguments = args;
            }
            return f;
        } catch (ClassNotFoundException e) {
            ClassNotFoundException classNotFoundException = e;
            throw new InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e);
        } catch (InstantiationException e2) {
            InstantiationException instantiationException = e2;
            throw new InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e2);
        } catch (IllegalAccessException e3) {
            IllegalAccessException illegalAccessException = e3;
            throw new InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e3);
        }
    }

    static boolean isSupportFragmentClass(Context context, String str) {
        Context context2 = context;
        String fname = str;
        Context context3 = context2;
        String str2 = fname;
        try {
            Class cls = (Class) sClassMap.get(fname);
            Class cls2 = cls;
            if (cls == null) {
                cls2 = context2.getClassLoader().loadClass(fname);
                Object put = sClassMap.put(fname, cls2);
            }
            return Fragment.class.isAssignableFrom(cls2);
        } catch (ClassNotFoundException e) {
            ClassNotFoundException classNotFoundException = e;
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void restoreViewState(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        if (this.mSavedViewState != null) {
            this.mInnerView.restoreHierarchyState(this.mSavedViewState);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        onViewStateRestored(savedInstanceState);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void setIndex(int i, Fragment fragment) {
        int index = i;
        Fragment parent = fragment;
        int i2 = index;
        Fragment fragment2 = parent;
        this.mIndex = index;
        if (parent == null) {
            this.mWho = "android:fragment:" + this.mIndex;
        } else {
            this.mWho = parent.mWho + ":" + this.mIndex;
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean isInBackStack() {
        return this.mBackStackNesting > 0;
    }

    public final boolean equals(Object obj) {
        Object o = obj;
        Object obj2 = o;
        return super.equals(o);
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        DebugUtils.buildShortClassTag(this, sb);
        if (this.mIndex >= 0) {
            StringBuilder append = sb.append(" #");
            StringBuilder append2 = sb.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            StringBuilder append3 = sb.append(" id=0x");
            StringBuilder append4 = sb.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            StringBuilder append5 = sb.append(" ");
            StringBuilder append6 = sb.append(this.mTag);
        }
        StringBuilder append7 = sb.append('}');
        return sb.toString();
    }

    public final int getId() {
        return this.mFragmentId;
    }

    public final String getTag() {
        return this.mTag;
    }

    public void setArguments(Bundle bundle) {
        Bundle args = bundle;
        Bundle bundle2 = args;
        if (this.mIndex < 0) {
            this.mArguments = args;
            return;
        }
        throw new IllegalStateException("Fragment already active");
    }

    public final Bundle getArguments() {
        return this.mArguments;
    }

    public void setInitialSavedState(SavedState savedState) {
        SavedState state = savedState;
        SavedState savedState2 = state;
        if (this.mIndex < 0) {
            this.mSavedFragmentState = (state == null || state.mState == null) ? null : state.mState;
            return;
        }
        throw new IllegalStateException("Fragment already active");
    }

    public void setTargetFragment(Fragment fragment, int i) {
        Fragment fragment2 = fragment;
        int requestCode = i;
        Fragment fragment3 = fragment2;
        int i2 = requestCode;
        this.mTarget = fragment2;
        this.mTargetRequestCode = requestCode;
    }

    public final Fragment getTargetFragment() {
        return this.mTarget;
    }

    public final int getTargetRequestCode() {
        return this.mTargetRequestCode;
    }

    public Context getContext() {
        return this.mHost != null ? this.mHost.getContext() : null;
    }

    public final FragmentActivity getActivity() {
        return this.mHost != null ? (FragmentActivity) this.mHost.getActivity() : null;
    }

    public final Object getHost() {
        if (this.mHost != null) {
            return this.mHost.onGetHost();
        }
        return null;
    }

    public final Resources getResources() {
        if (this.mHost != null) {
            return this.mHost.getContext().getResources();
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    public final CharSequence getText(@StringRes int i) {
        int resId = i;
        int i2 = resId;
        return getResources().getText(resId);
    }

    public final String getString(@StringRes int i) {
        int resId = i;
        int i2 = resId;
        return getResources().getString(resId);
    }

    public final String getString(@StringRes int i, Object... objArr) {
        int resId = i;
        Object[] formatArgs = objArr;
        int i2 = resId;
        Object[] objArr2 = formatArgs;
        return getResources().getString(resId, formatArgs);
    }

    public final FragmentManager getFragmentManager() {
        return this.mFragmentManager;
    }

    public final FragmentManager getChildFragmentManager() {
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
            if (this.mState >= 5) {
                this.mChildFragmentManager.dispatchResume();
            } else if (this.mState >= 4) {
                this.mChildFragmentManager.dispatchStart();
            } else if (this.mState >= 2) {
                this.mChildFragmentManager.dispatchActivityCreated();
            } else if (this.mState >= 1) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
        return this.mChildFragmentManager;
    }

    /* access modifiers changed from: 0000 */
    public FragmentManager peekChildFragmentManager() {
        return this.mChildFragmentManager;
    }

    public final Fragment getParentFragment() {
        return this.mParentFragment;
    }

    public final boolean isAdded() {
        return this.mHost != null && this.mAdded;
    }

    public final boolean isDetached() {
        return this.mDetached;
    }

    public final boolean isRemoving() {
        return this.mRemoving;
    }

    public final boolean isInLayout() {
        return this.mInLayout;
    }

    public final boolean isResumed() {
        return this.mState >= 5;
    }

    public final boolean isVisible() {
        return isAdded() && !isHidden() && this.mView != null && this.mView.getWindowToken() != null && this.mView.getVisibility() == 0;
    }

    public final boolean isHidden() {
        return this.mHidden;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public final boolean hasOptionsMenu() {
        return this.mHasMenu;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public final boolean isMenuVisible() {
        return this.mMenuVisible;
    }

    public void onHiddenChanged(boolean z) {
        boolean z2 = z;
    }

    public void setRetainInstance(boolean z) {
        this.mRetainInstance = z;
    }

    public final boolean getRetainInstance() {
        return this.mRetainInstance;
    }

    public void setHasOptionsMenu(boolean z) {
        boolean hasMenu = z;
        if (this.mHasMenu != hasMenu) {
            this.mHasMenu = hasMenu;
            if (isAdded() && !isHidden()) {
                this.mHost.onSupportInvalidateOptionsMenu();
            }
        }
    }

    public void setMenuVisibility(boolean z) {
        boolean menuVisible = z;
        if (this.mMenuVisible != menuVisible) {
            this.mMenuVisible = menuVisible;
            if (this.mHasMenu && isAdded() && !isHidden()) {
                this.mHost.onSupportInvalidateOptionsMenu();
            }
        }
    }

    public void setUserVisibleHint(boolean z) {
        boolean isVisibleToUser = z;
        if (!this.mUserVisibleHint && isVisibleToUser && this.mState < 4 && this.mFragmentManager != null && isAdded()) {
            this.mFragmentManager.performPendingDeferredStart(this);
        }
        this.mUserVisibleHint = isVisibleToUser;
        this.mDeferStart = this.mState < 4 && !isVisibleToUser;
    }

    public boolean getUserVisibleHint() {
        return this.mUserVisibleHint;
    }

    public LoaderManager getLoaderManager() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        if (this.mHost != null) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, true);
            return this.mLoaderManager;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    public void startActivity(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        startActivity(intent2, null);
    }

    public void startActivity(Intent intent, @Nullable Bundle bundle) {
        Intent intent2 = intent;
        Bundle options = bundle;
        Intent intent3 = intent2;
        Bundle bundle2 = options;
        if (this.mHost != null) {
            this.mHost.onStartActivityFromFragment(this, intent2, -1, options);
            return;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    public void startActivityForResult(Intent intent, int i) {
        Intent intent2 = intent;
        int requestCode = i;
        Intent intent3 = intent2;
        int i2 = requestCode;
        startActivityForResult(intent2, requestCode, null);
    }

    public void startActivityForResult(Intent intent, int i, @Nullable Bundle bundle) {
        Intent intent2 = intent;
        int requestCode = i;
        Bundle options = bundle;
        Intent intent3 = intent2;
        int i2 = requestCode;
        Bundle bundle2 = options;
        if (this.mHost != null) {
            this.mHost.onStartActivityFromFragment(this, intent2, requestCode, options);
            return;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) throws SendIntentException {
        IntentSender intent2 = intentSender;
        int requestCode = i;
        Intent fillInIntent = intent;
        int flagsMask = i2;
        int flagsValues = i3;
        int extraFlags = i4;
        Bundle options = bundle;
        IntentSender intentSender2 = intent2;
        int i5 = requestCode;
        Intent intent3 = fillInIntent;
        int i6 = flagsMask;
        int i7 = flagsValues;
        int i8 = extraFlags;
        Bundle bundle2 = options;
        if (this.mHost != null) {
            this.mHost.onStartIntentSenderFromFragment(this, intent2, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
            return;
        }
        IllegalStateException illegalStateException = new IllegalStateException("Fragment " + this + " not attached to Activity");
        throw illegalStateException;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        int i3 = i;
        int i4 = i2;
        Intent intent2 = intent;
    }

    public final void requestPermissions(@NonNull String[] strArr, int i) {
        String[] permissions = strArr;
        int requestCode = i;
        String[] strArr2 = permissions;
        int i2 = requestCode;
        if (this.mHost != null) {
            this.mHost.onRequestPermissionsFromFragment(this, permissions, requestCode);
            return;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        int i2 = i;
        String[] strArr2 = strArr;
        int[] iArr2 = iArr;
    }

    public boolean shouldShowRequestPermissionRationale(@NonNull String str) {
        String permission = str;
        String str2 = permission;
        if (this.mHost == null) {
            return false;
        }
        return this.mHost.onShouldShowRequestPermissionRationale(permission);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public LayoutInflater getLayoutInflater(Bundle bundle) {
        Bundle bundle2 = bundle;
        LayoutInflater result = this.mHost.onGetLayoutInflater();
        FragmentManager childFragmentManager = getChildFragmentManager();
        LayoutInflaterCompat.setFactory(result, this.mChildFragmentManager.getLayoutInflaterFactory());
        return result;
    }

    @CallSuper
    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        AttributeSet attrs = attributeSet;
        Bundle savedInstanceState = bundle;
        Context context2 = context;
        AttributeSet attributeSet2 = attrs;
        Bundle bundle2 = savedInstanceState;
        this.mCalled = true;
        Activity hostActivity = this.mHost != null ? this.mHost.getActivity() : null;
        if (hostActivity != null) {
            this.mCalled = false;
            onInflate(hostActivity, attrs, savedInstanceState);
        }
    }

    @Deprecated
    @CallSuper
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        Activity activity2 = activity;
        AttributeSet attributeSet2 = attributeSet;
        Bundle bundle2 = bundle;
        this.mCalled = true;
    }

    public void onAttachFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
    }

    @CallSuper
    public void onAttach(Context context) {
        Context context2 = context;
        this.mCalled = true;
        Activity hostActivity = this.mHost != null ? this.mHost.getActivity() : null;
        if (hostActivity != null) {
            this.mCalled = false;
            onAttach(hostActivity);
        }
    }

    @Deprecated
    @CallSuper
    public void onAttach(Activity activity) {
        Activity activity2 = activity;
        this.mCalled = true;
    }

    public Animation onCreateAnimation(int i, boolean z, int i2) {
        int i3 = i;
        boolean z2 = z;
        int i4 = i2;
        return null;
    }

    @CallSuper
    public void onCreate(@Nullable Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        this.mCalled = true;
        restoreChildFragmentState(savedInstanceState);
        if (this.mChildFragmentManager != null && !this.mChildFragmentManager.isStateAtLeast(1)) {
            this.mChildFragmentManager.dispatchCreate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void restoreChildFragmentState(@Nullable Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        if (savedInstanceState != null) {
            Parcelable parcelable = savedInstanceState.getParcelable("android:support:fragments");
            Parcelable p = parcelable;
            if (parcelable != null) {
                if (this.mChildFragmentManager == null) {
                    instantiateChildFragmentManager();
                }
                this.mChildFragmentManager.restoreAllState(p, this.mChildNonConfig);
                this.mChildNonConfig = null;
                this.mChildFragmentManager.dispatchCreate();
            }
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        LayoutInflater layoutInflater2 = layoutInflater;
        ViewGroup viewGroup2 = viewGroup;
        Bundle bundle2 = bundle;
        return null;
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        View view2 = view;
        Bundle bundle2 = bundle;
    }

    @Nullable
    public View getView() {
        return this.mView;
    }

    @CallSuper
    public void onActivityCreated(@Nullable Bundle bundle) {
        Bundle bundle2 = bundle;
        this.mCalled = true;
    }

    @CallSuper
    public void onViewStateRestored(@Nullable Bundle bundle) {
        Bundle bundle2 = bundle;
        this.mCalled = true;
    }

    @CallSuper
    public void onStart() {
        this.mCalled = true;
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
            }
            if (this.mLoaderManager != null) {
                this.mLoaderManager.doStart();
            }
        }
    }

    @CallSuper
    public void onResume() {
        this.mCalled = true;
    }

    public void onSaveInstanceState(Bundle bundle) {
        Bundle bundle2 = bundle;
    }

    public void onMultiWindowModeChanged(boolean z) {
        boolean z2 = z;
    }

    public void onPictureInPictureModeChanged(boolean z) {
        boolean z2 = z;
    }

    @CallSuper
    public void onConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = configuration;
        this.mCalled = true;
    }

    @CallSuper
    public void onPause() {
        this.mCalled = true;
    }

    @CallSuper
    public void onStop() {
        this.mCalled = true;
    }

    @CallSuper
    public void onLowMemory() {
        this.mCalled = true;
    }

    @CallSuper
    public void onDestroyView() {
        this.mCalled = true;
    }

    @CallSuper
    public void onDestroy() {
        this.mCalled = true;
        if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
        }
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doDestroy();
        }
    }

    /* access modifiers changed from: 0000 */
    public void initState() {
        this.mIndex = -1;
        this.mWho = null;
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = null;
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
        this.mRetaining = false;
        this.mLoaderManager = null;
        this.mLoadersStarted = false;
        this.mCheckedForLoaderManager = false;
    }

    @CallSuper
    public void onDetach() {
        this.mCalled = true;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Menu menu2 = menu;
        MenuInflater menuInflater2 = menuInflater;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        Menu menu2 = menu;
    }

    public void onDestroyOptionsMenu() {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MenuItem menuItem2 = menuItem;
        return false;
    }

    public void onOptionsMenuClosed(Menu menu) {
        Menu menu2 = menu;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo) {
        ContextMenu menu = contextMenu;
        View v = view;
        ContextMenuInfo menuInfo = contextMenuInfo;
        ContextMenu contextMenu2 = menu;
        View view2 = v;
        ContextMenuInfo contextMenuInfo2 = menuInfo;
        getActivity().onCreateContextMenu(menu, v, menuInfo);
    }

    public void registerForContextMenu(View view) {
        View view2 = view;
        View view3 = view2;
        view2.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(View view) {
        View view2 = view;
        View view3 = view2;
        view2.setOnCreateContextMenuListener(null);
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        MenuItem menuItem2 = menuItem;
        return false;
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback callback = sharedElementCallback;
        SharedElementCallback sharedElementCallback2 = callback;
        ensureAnimationInfo().mEnterTransitionCallback = callback;
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback callback = sharedElementCallback;
        SharedElementCallback sharedElementCallback2 = callback;
        ensureAnimationInfo().mExitTransitionCallback = callback;
    }

    public void setEnterTransition(Object obj) {
        Object transition = obj;
        Object obj2 = transition;
        Object access$002 = AnimationInfo.access$002(ensureAnimationInfo(), transition);
    }

    public Object getEnterTransition() {
        if (this.mAnimationInfo != null) {
            return AnimationInfo.access$000(this.mAnimationInfo);
        }
        return null;
    }

    public void setReturnTransition(Object obj) {
        Object transition = obj;
        Object obj2 = transition;
        Object access$102 = AnimationInfo.access$102(ensureAnimationInfo(), transition);
    }

    public Object getReturnTransition() {
        Object enterTransition;
        if (this.mAnimationInfo == null) {
            return null;
        }
        if (AnimationInfo.access$100(this.mAnimationInfo) != USE_DEFAULT_TRANSITION) {
            enterTransition = AnimationInfo.access$100(this.mAnimationInfo);
        } else {
            enterTransition = getEnterTransition();
        }
        return enterTransition;
    }

    public void setExitTransition(Object obj) {
        Object transition = obj;
        Object obj2 = transition;
        Object access$202 = AnimationInfo.access$202(ensureAnimationInfo(), transition);
    }

    public Object getExitTransition() {
        if (this.mAnimationInfo != null) {
            return AnimationInfo.access$200(this.mAnimationInfo);
        }
        return null;
    }

    public void setReenterTransition(Object obj) {
        Object transition = obj;
        Object obj2 = transition;
        Object access$302 = AnimationInfo.access$302(ensureAnimationInfo(), transition);
    }

    public Object getReenterTransition() {
        Object exitTransition;
        if (this.mAnimationInfo == null) {
            return null;
        }
        if (AnimationInfo.access$300(this.mAnimationInfo) != USE_DEFAULT_TRANSITION) {
            exitTransition = AnimationInfo.access$300(this.mAnimationInfo);
        } else {
            exitTransition = getExitTransition();
        }
        return exitTransition;
    }

    public void setSharedElementEnterTransition(Object obj) {
        Object transition = obj;
        Object obj2 = transition;
        Object access$402 = AnimationInfo.access$402(ensureAnimationInfo(), transition);
    }

    public Object getSharedElementEnterTransition() {
        if (this.mAnimationInfo != null) {
            return AnimationInfo.access$400(this.mAnimationInfo);
        }
        return null;
    }

    public void setSharedElementReturnTransition(Object obj) {
        Object transition = obj;
        Object obj2 = transition;
        Object access$502 = AnimationInfo.access$502(ensureAnimationInfo(), transition);
    }

    public Object getSharedElementReturnTransition() {
        Object sharedElementEnterTransition;
        if (this.mAnimationInfo == null) {
            return null;
        }
        if (AnimationInfo.access$500(this.mAnimationInfo) != USE_DEFAULT_TRANSITION) {
            sharedElementEnterTransition = AnimationInfo.access$500(this.mAnimationInfo);
        } else {
            sharedElementEnterTransition = getSharedElementEnterTransition();
        }
        return sharedElementEnterTransition;
    }

    public void setAllowEnterTransitionOverlap(boolean z) {
        boolean allow = z;
        Boolean access$602 = AnimationInfo.access$602(ensureAnimationInfo(), Boolean.valueOf(allow));
    }

    public boolean getAllowEnterTransitionOverlap() {
        boolean booleanValue;
        if (this.mAnimationInfo == null || AnimationInfo.access$600(this.mAnimationInfo) == null) {
            booleanValue = true;
        } else {
            booleanValue = AnimationInfo.access$600(this.mAnimationInfo).booleanValue();
        }
        return booleanValue;
    }

    public void setAllowReturnTransitionOverlap(boolean z) {
        boolean allow = z;
        Boolean access$702 = AnimationInfo.access$702(ensureAnimationInfo(), Boolean.valueOf(allow));
    }

    public boolean getAllowReturnTransitionOverlap() {
        boolean booleanValue;
        if (this.mAnimationInfo == null || AnimationInfo.access$700(this.mAnimationInfo) == null) {
            booleanValue = true;
        } else {
            booleanValue = AnimationInfo.access$700(this.mAnimationInfo).booleanValue();
        }
        return booleanValue;
    }

    public void postponeEnterTransition() {
        ensureAnimationInfo().mEnterTransitionPostponed = true;
    }

    public void startPostponedEnterTransition() {
        if (this.mFragmentManager == null || this.mFragmentManager.mHost == null) {
            ensureAnimationInfo().mEnterTransitionPostponed = false;
        } else if (Looper.myLooper() == this.mFragmentManager.mHost.getHandler().getLooper()) {
            callStartTransitionListener();
        } else {
            boolean postAtFrontOfQueue = this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new Runnable(this) {
                final /* synthetic */ Fragment this$0;

                {
                    Fragment this$02 = r5;
                    Fragment fragment = this$02;
                    this.this$0 = this$02;
                }

                public void run() {
                    Fragment.access$800(this.this$0);
                }
            });
        }
    }

    private void callStartTransitionListener() {
        OnStartEnterTransitionListener listener;
        if (this.mAnimationInfo != null) {
            this.mAnimationInfo.mEnterTransitionPostponed = false;
            listener = this.mAnimationInfo.mStartEnterTransitionListener;
            this.mAnimationInfo.mStartEnterTransitionListener = null;
        } else {
            listener = null;
        }
        if (listener != null) {
            listener.onStartEnterTransition();
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
        writer.print(prefix);
        writer.print("mFragmentId=#");
        writer.print(Integer.toHexString(this.mFragmentId));
        writer.print(" mContainerId=#");
        writer.print(Integer.toHexString(this.mContainerId));
        writer.print(" mTag=");
        writer.println(this.mTag);
        writer.print(prefix);
        writer.print("mState=");
        writer.print(this.mState);
        writer.print(" mIndex=");
        writer.print(this.mIndex);
        writer.print(" mWho=");
        writer.print(this.mWho);
        writer.print(" mBackStackNesting=");
        writer.println(this.mBackStackNesting);
        writer.print(prefix);
        writer.print("mAdded=");
        writer.print(this.mAdded);
        writer.print(" mRemoving=");
        writer.print(this.mRemoving);
        writer.print(" mFromLayout=");
        writer.print(this.mFromLayout);
        writer.print(" mInLayout=");
        writer.println(this.mInLayout);
        writer.print(prefix);
        writer.print("mHidden=");
        writer.print(this.mHidden);
        writer.print(" mDetached=");
        writer.print(this.mDetached);
        writer.print(" mMenuVisible=");
        writer.print(this.mMenuVisible);
        writer.print(" mHasMenu=");
        writer.println(this.mHasMenu);
        writer.print(prefix);
        writer.print("mRetainInstance=");
        writer.print(this.mRetainInstance);
        writer.print(" mRetaining=");
        writer.print(this.mRetaining);
        writer.print(" mUserVisibleHint=");
        writer.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            writer.print(prefix);
            writer.print("mFragmentManager=");
            writer.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            writer.print(prefix);
            writer.print("mHost=");
            writer.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            writer.print(prefix);
            writer.print("mParentFragment=");
            writer.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            writer.print(prefix);
            writer.print("mArguments=");
            writer.println(this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            writer.print(prefix);
            writer.print("mSavedFragmentState=");
            writer.println(this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            writer.print(prefix);
            writer.print("mSavedViewState=");
            writer.println(this.mSavedViewState);
        }
        if (this.mTarget != null) {
            writer.print(prefix);
            writer.print("mTarget=");
            writer.print(this.mTarget);
            writer.print(" mTargetRequestCode=");
            writer.println(this.mTargetRequestCode);
        }
        if (getNextAnim() != 0) {
            writer.print(prefix);
            writer.print("mNextAnim=");
            writer.println(getNextAnim());
        }
        if (this.mContainer != null) {
            writer.print(prefix);
            writer.print("mContainer=");
            writer.println(this.mContainer);
        }
        if (this.mView != null) {
            writer.print(prefix);
            writer.print("mView=");
            writer.println(this.mView);
        }
        if (this.mInnerView != null) {
            writer.print(prefix);
            writer.print("mInnerView=");
            writer.println(this.mView);
        }
        if (getAnimatingAway() != null) {
            writer.print(prefix);
            writer.print("mAnimatingAway=");
            writer.println(getAnimatingAway());
            writer.print(prefix);
            writer.print("mStateAfterAnimating=");
            writer.println(getStateAfterAnimating());
        }
        if (this.mLoaderManager != null) {
            writer.print(prefix);
            writer.println("Loader Manager:");
            this.mLoaderManager.dump(prefix + "  ", fd, writer, args);
        }
        if (this.mChildFragmentManager != null) {
            writer.print(prefix);
            writer.println("Child " + this.mChildFragmentManager + ":");
            this.mChildFragmentManager.dump(prefix + "  ", fd, writer, args);
        }
    }

    /* access modifiers changed from: 0000 */
    public Fragment findFragmentByWho(String str) {
        String who = str;
        String str2 = who;
        if (who.equals(this.mWho)) {
            return this;
        }
        if (this.mChildFragmentManager == null) {
            return null;
        }
        return this.mChildFragmentManager.findFragmentByWho(who);
    }

    /* access modifiers changed from: 0000 */
    public void instantiateChildFragmentManager() {
        this.mChildFragmentManager = new FragmentManagerImpl();
        this.mChildFragmentManager.attachController(this.mHost, new FragmentContainer(this) {
            final /* synthetic */ Fragment this$0;

            {
                Fragment this$02 = r5;
                Fragment fragment = this$02;
                this.this$0 = this$02;
            }

            @Nullable
            public View onFindViewById(int i) {
                int id = i;
                int i2 = id;
                if (this.this$0.mView != null) {
                    return this.this$0.mView.findViewById(id);
                }
                throw new IllegalStateException("Fragment does not have a view");
            }

            public boolean onHasView() {
                return this.this$0.mView != null;
            }
        }, this);
    }

    /* access modifiers changed from: 0000 */
    public void performCreate(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mState = 1;
        this.mCalled = false;
        onCreate(savedInstanceState);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
        }
    }

    /* access modifiers changed from: 0000 */
    public View performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LayoutInflater inflater = layoutInflater;
        ViewGroup container = viewGroup;
        Bundle savedInstanceState = bundle;
        LayoutInflater layoutInflater2 = inflater;
        ViewGroup viewGroup2 = container;
        Bundle bundle2 = savedInstanceState;
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        return onCreateView(inflater, container, savedInstanceState);
    }

    /* access modifiers changed from: 0000 */
    public void performActivityCreated(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mState = 2;
        this.mCalled = false;
        onActivityCreated(savedInstanceState);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
        } else if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchActivityCreated();
        }
    }

    /* access modifiers changed from: 0000 */
    public void performStart() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
            boolean execPendingActions = this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 4;
        this.mCalled = false;
        onStart();
        if (this.mCalled) {
            if (this.mChildFragmentManager != null) {
                this.mChildFragmentManager.dispatchStart();
            }
            if (this.mLoaderManager != null) {
                this.mLoaderManager.doReportStart();
                return;
            }
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
    }

    /* access modifiers changed from: 0000 */
    public void performResume() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
            boolean execPendingActions = this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 5;
        this.mCalled = false;
        onResume();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
        } else if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchResume();
            boolean execPendingActions2 = this.mChildFragmentManager.execPendingActions();
        }
    }

    /* access modifiers changed from: 0000 */
    public void performMultiWindowModeChanged(boolean z) {
        boolean isInMultiWindowMode = z;
        onMultiWindowModeChanged(isInMultiWindowMode);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchMultiWindowModeChanged(isInMultiWindowMode);
        }
    }

    /* access modifiers changed from: 0000 */
    public void performPictureInPictureModeChanged(boolean z) {
        boolean isInPictureInPictureMode = z;
        onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPictureInPictureModeChanged(isInPictureInPictureMode);
        }
    }

    /* access modifiers changed from: 0000 */
    public void performConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        onConfigurationChanged(newConfig);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchConfigurationChanged(newConfig);
        }
    }

    /* access modifiers changed from: 0000 */
    public void performLowMemory() {
        onLowMemory();
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchLowMemory();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean performCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Menu menu2 = menu;
        MenuInflater inflater = menuInflater;
        Menu menu3 = menu2;
        MenuInflater menuInflater2 = inflater;
        boolean show = false;
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                show = true;
                onCreateOptionsMenu(menu2, inflater);
            }
            if (this.mChildFragmentManager != null) {
                show |= this.mChildFragmentManager.dispatchCreateOptionsMenu(menu2, inflater);
            }
        }
        return show;
    }

    /* access modifiers changed from: 0000 */
    public boolean performPrepareOptionsMenu(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        boolean show = false;
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                show = true;
                onPrepareOptionsMenu(menu2);
            }
            if (this.mChildFragmentManager != null) {
                show |= this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu2);
            }
        }
        return show;
    }

    /* access modifiers changed from: 0000 */
    public boolean performOptionsItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(item)) {
                return true;
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchOptionsItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean performContextItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (!this.mHidden) {
            if (onContextItemSelected(item)) {
                return true;
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchContextItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void performOptionsMenuClosed(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                onOptionsMenuClosed(menu2);
            }
            if (this.mChildFragmentManager != null) {
                this.mChildFragmentManager.dispatchOptionsMenuClosed(menu2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performSaveInstanceState(Bundle bundle) {
        Bundle outState = bundle;
        Bundle bundle2 = outState;
        onSaveInstanceState(outState);
        if (this.mChildFragmentManager != null) {
            Parcelable saveAllState = this.mChildFragmentManager.saveAllState();
            Parcelable p = saveAllState;
            if (saveAllState != null) {
                outState.putParcelable("android:support:fragments", p);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performPause() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPause();
        }
        this.mState = 4;
        this.mCalled = false;
        onPause();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
        }
    }

    /* access modifiers changed from: 0000 */
    public void performStop() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStop();
        }
        this.mState = 3;
        this.mCalled = false;
        onStop();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
        }
    }

    /* access modifiers changed from: 0000 */
    public void performReallyStop() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchReallyStop();
        }
        this.mState = 2;
        if (this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
            }
            if (this.mLoaderManager != null) {
                if (!this.mHost.getRetainLoaders()) {
                    this.mLoaderManager.doStop();
                } else {
                    this.mLoaderManager.doRetain();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performDestroyView() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroyView();
        }
        this.mState = 1;
        this.mCalled = false;
        onDestroyView();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
        } else if (this.mLoaderManager != null) {
            this.mLoaderManager.doReportNextStart();
        }
    }

    /* access modifiers changed from: 0000 */
    public void performDestroy() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroy();
        }
        this.mState = 0;
        this.mCalled = false;
        onDestroy();
        if (this.mCalled) {
            this.mChildFragmentManager = null;
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
    }

    /* access modifiers changed from: 0000 */
    public void performDetach() {
        this.mCalled = false;
        onDetach();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDetach()");
        } else if (this.mChildFragmentManager != null) {
            if (this.mRetaining) {
                this.mChildFragmentManager.dispatchDestroy();
                this.mChildFragmentManager = null;
                return;
            }
            throw new IllegalStateException("Child FragmentManager of " + this + " was not " + " destroyed and this fragment is not retaining instance");
        }
    }

    /* access modifiers changed from: 0000 */
    public void setOnStartEnterTransitionListener(OnStartEnterTransitionListener onStartEnterTransitionListener) {
        OnStartEnterTransitionListener listener = onStartEnterTransitionListener;
        OnStartEnterTransitionListener onStartEnterTransitionListener2 = listener;
        AnimationInfo ensureAnimationInfo = ensureAnimationInfo();
        if (listener == this.mAnimationInfo.mStartEnterTransitionListener) {
            return;
        }
        if (listener == null || this.mAnimationInfo.mStartEnterTransitionListener == null) {
            if (this.mAnimationInfo.mEnterTransitionPostponed) {
                this.mAnimationInfo.mStartEnterTransitionListener = listener;
            }
            if (listener != null) {
                listener.startListening();
            }
            return;
        }
        throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
    }

    private AnimationInfo ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new AnimationInfo();
        }
        return this.mAnimationInfo;
    }

    /* access modifiers changed from: 0000 */
    public int getNextAnim() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mNextAnim;
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void setNextAnim(int i) {
        int animResourceId = i;
        int i2 = animResourceId;
        if (this.mAnimationInfo != null || animResourceId != 0) {
            ensureAnimationInfo().mNextAnim = animResourceId;
        }
    }

    /* access modifiers changed from: 0000 */
    public int getNextTransition() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mNextTransition;
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void setNextTransition(int i, int i2) {
        int nextTransition = i;
        int nextTransitionStyle = i2;
        int i3 = nextTransition;
        int i4 = nextTransitionStyle;
        if (this.mAnimationInfo != null || nextTransition != 0 || nextTransitionStyle != 0) {
            AnimationInfo ensureAnimationInfo = ensureAnimationInfo();
            this.mAnimationInfo.mNextTransition = nextTransition;
            this.mAnimationInfo.mNextTransitionStyle = nextTransitionStyle;
        }
    }

    /* access modifiers changed from: 0000 */
    public int getNextTransitionStyle() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mNextTransitionStyle;
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public SharedElementCallback getEnterTransitionCallback() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mEnterTransitionCallback;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public SharedElementCallback getExitTransitionCallback() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mExitTransitionCallback;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public View getAnimatingAway() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mAnimatingAway;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void setAnimatingAway(View view) {
        View view2 = view;
        View view3 = view2;
        ensureAnimationInfo().mAnimatingAway = view2;
    }

    /* access modifiers changed from: 0000 */
    public int getStateAfterAnimating() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mStateAfterAnimating;
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void setStateAfterAnimating(int i) {
        int state = i;
        int i2 = state;
        ensureAnimationInfo().mStateAfterAnimating = state;
    }

    /* access modifiers changed from: 0000 */
    public boolean isPostponed() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mEnterTransitionPostponed;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean isHideReplaced() {
        if (this.mAnimationInfo != null) {
            return this.mAnimationInfo.mIsHideReplaced;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void setHideReplaced(boolean z) {
        ensureAnimationInfo().mIsHideReplaced = z;
    }
}
