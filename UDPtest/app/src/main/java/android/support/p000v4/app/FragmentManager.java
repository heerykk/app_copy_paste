package android.support.p000v4.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.p000v4.app.Fragment.SavedState;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* renamed from: android.support.v4.app.FragmentManager */
public abstract class FragmentManager {
    public static final int POP_BACK_STACK_INCLUSIVE = 1;

    /* renamed from: android.support.v4.app.FragmentManager$BackStackEntry */
    public interface BackStackEntry {
        CharSequence getBreadCrumbShortTitle();

        @StringRes
        int getBreadCrumbShortTitleRes();

        CharSequence getBreadCrumbTitle();

        @StringRes
        int getBreadCrumbTitleRes();

        int getId();

        String getName();
    }

    /* renamed from: android.support.v4.app.FragmentManager$FragmentLifecycleCallbacks */
    public abstract class FragmentLifecycleCallbacks {
        final /* synthetic */ FragmentManager this$0;

        public FragmentLifecycleCallbacks(FragmentManager fragmentManager) {
            FragmentManager this$02 = fragmentManager;
            FragmentManager fragmentManager2 = this$02;
            this.this$0 = this$02;
        }

        public void onFragmentPreAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
            Context context2 = context;
        }

        public void onFragmentAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
            Context context2 = context;
        }

        public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
            Bundle bundle2 = bundle;
        }

        public void onFragmentActivityCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
            Bundle bundle2 = bundle;
        }

        public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
            View view2 = view;
            Bundle bundle2 = bundle;
        }

        public void onFragmentStarted(FragmentManager fragmentManager, Fragment fragment) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
        }

        public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
        }

        public void onFragmentPaused(FragmentManager fragmentManager, Fragment fragment) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
        }

        public void onFragmentStopped(FragmentManager fragmentManager, Fragment fragment) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
        }

        public void onFragmentSaveInstanceState(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
            Bundle bundle2 = bundle;
        }

        public void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
        }

        public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
        }

        public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
            FragmentManager fragmentManager2 = fragmentManager;
            Fragment fragment2 = fragment;
        }
    }

    /* renamed from: android.support.v4.app.FragmentManager$OnBackStackChangedListener */
    public interface OnBackStackChangedListener {
        void onBackStackChanged();
    }

    public abstract void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener);

    public abstract FragmentTransaction beginTransaction();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract boolean executePendingTransactions();

    public abstract Fragment findFragmentById(@IdRes int i);

    public abstract Fragment findFragmentByTag(String str);

    public abstract BackStackEntry getBackStackEntryAt(int i);

    public abstract int getBackStackEntryCount();

    public abstract Fragment getFragment(Bundle bundle, String str);

    @RestrictTo({Scope.LIBRARY_GROUP})
    public abstract List<Fragment> getFragments();

    public abstract boolean isDestroyed();

    public abstract void popBackStack();

    public abstract void popBackStack(int i, int i2);

    public abstract void popBackStack(String str, int i);

    public abstract boolean popBackStackImmediate();

    public abstract boolean popBackStackImmediate(int i, int i2);

    public abstract boolean popBackStackImmediate(String str, int i);

    public abstract void putFragment(Bundle bundle, String str, Fragment fragment);

    public abstract void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z);

    public abstract void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener);

    public abstract SavedState saveFragmentInstanceState(Fragment fragment);

    public abstract void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks);

    public FragmentManager() {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    public static void enableDebugLogging(boolean z) {
        FragmentManagerImpl.DEBUG = z;
    }
}
