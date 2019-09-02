package android.support.p000v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.p000v4.app.Fragment.SavedState;
import android.support.p000v4.app.FragmentManager.BackStackEntry;
import android.support.p000v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.support.p000v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.p000v4.p002os.BuildCompat;
import android.support.p000v4.util.ArraySet;
import android.support.p000v4.util.DebugUtils;
import android.support.p000v4.util.LogWriter;
import android.support.p000v4.util.Pair;
import android.support.p000v4.view.LayoutInflaterFactory;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: android.support.v4.app.FragmentManagerImpl */
/* compiled from: FragmentManager */
final class FragmentManagerImpl extends FragmentManager implements LayoutInflaterFactory {
    static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
    static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
    static final boolean HONEYCOMB;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    static Field sAnimationListenerField = null;
    ArrayList<Fragment> mActive;
    ArrayList<Fragment> mAdded;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<Integer> mAvailIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new Runnable(this) {
        final /* synthetic */ FragmentManagerImpl this$0;

        {
            FragmentManagerImpl this$02 = r5;
            FragmentManagerImpl fragmentManagerImpl = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            boolean execPendingActions = this.this$0.execPendingActions();
        }
    };
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    private CopyOnWriteArrayList<Pair<FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<OpGenerator> mPendingActions;
    ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    Runnable[] mTmpActions;
    ArrayList<Fragment> mTmpAddedFragments;
    ArrayList<Boolean> mTmpIsPop;
    ArrayList<BackStackRecord> mTmpRecords;

    /* renamed from: android.support.v4.app.FragmentManagerImpl$AnimateOnHWLayerIfNeededListener */
    /* compiled from: FragmentManager */
    static class AnimateOnHWLayerIfNeededListener implements AnimationListener {
        private AnimationListener mOriginalListener;
        private boolean mShouldRunOnHWLayer;
        View mView;

        public AnimateOnHWLayerIfNeededListener(View view, Animation animation) {
            View v = view;
            Animation anim = animation;
            View view2 = v;
            Animation animation2 = anim;
            if (v != null && anim != null) {
                this.mView = v;
            }
        }

        public AnimateOnHWLayerIfNeededListener(View view, Animation animation, AnimationListener animationListener) {
            View v = view;
            Animation anim = animation;
            AnimationListener listener = animationListener;
            View view2 = v;
            Animation animation2 = anim;
            AnimationListener animationListener2 = listener;
            if (v != null && anim != null) {
                this.mOriginalListener = listener;
                this.mView = v;
                this.mShouldRunOnHWLayer = true;
            }
        }

        @CallSuper
        public void onAnimationStart(Animation animation) {
            Animation animation2 = animation;
            Animation animation3 = animation2;
            if (this.mOriginalListener != null) {
                this.mOriginalListener.onAnimationStart(animation2);
            }
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            Animation animation2 = animation;
            Animation animation3 = animation2;
            if (this.mView != null && this.mShouldRunOnHWLayer) {
                if (!ViewCompat.isAttachedToWindow(this.mView) && !BuildCompat.isAtLeastN()) {
                    ViewCompat.setLayerType(this.mView, 0, null);
                } else {
                    boolean post = this.mView.post(new Runnable(this) {
                        final /* synthetic */ AnimateOnHWLayerIfNeededListener this$0;

                        {
                            AnimateOnHWLayerIfNeededListener this$02 = r5;
                            AnimateOnHWLayerIfNeededListener animateOnHWLayerIfNeededListener = this$02;
                            this.this$0 = this$02;
                        }

                        public void run() {
                            ViewCompat.setLayerType(this.this$0.mView, 0, null);
                        }
                    });
                }
            }
            if (this.mOriginalListener != null) {
                this.mOriginalListener.onAnimationEnd(animation2);
            }
        }

        public void onAnimationRepeat(Animation animation) {
            Animation animation2 = animation;
            Animation animation3 = animation2;
            if (this.mOriginalListener != null) {
                this.mOriginalListener.onAnimationRepeat(animation2);
            }
        }
    }

    /* renamed from: android.support.v4.app.FragmentManagerImpl$FragmentTag */
    /* compiled from: FragmentManager */
    static class FragmentTag {
        public static final int[] Fragment;
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() {
        }

        static {
            int[] iArr = new int[3];
            iArr[0] = 16842755;
            iArr[1] = 16842960;
            iArr[2] = 16842961;
            Fragment = iArr;
        }
    }

    /* renamed from: android.support.v4.app.FragmentManagerImpl$OpGenerator */
    /* compiled from: FragmentManager */
    interface OpGenerator {
        boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2);
    }

    /* renamed from: android.support.v4.app.FragmentManagerImpl$PopBackStackState */
    /* compiled from: FragmentManager */
    private class PopBackStackState implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;
        final /* synthetic */ FragmentManagerImpl this$0;

        PopBackStackState(FragmentManagerImpl fragmentManagerImpl, String str, int i, int i2) {
            String name = str;
            int id = i;
            int flags = i2;
            String str2 = name;
            int i3 = id;
            int i4 = flags;
            this.this$0 = fragmentManagerImpl;
            this.mName = name;
            this.mId = id;
            this.mFlags = flags;
        }

        public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            ArrayList<BackStackRecord> records = arrayList;
            ArrayList<Boolean> isRecordPop = arrayList2;
            ArrayList<BackStackRecord> arrayList3 = records;
            ArrayList<Boolean> arrayList4 = isRecordPop;
            return this.this$0.popBackStackState(records, isRecordPop, this.mName, this.mId, this.mFlags);
        }
    }

    /* renamed from: android.support.v4.app.FragmentManagerImpl$StartEnterTransitionListener */
    /* compiled from: FragmentManager */
    static class StartEnterTransitionListener implements OnStartEnterTransitionListener {
        private final boolean mIsBack;
        private int mNumPostponed;
        private final BackStackRecord mRecord;

        static /* synthetic */ boolean access$000(StartEnterTransitionListener startEnterTransitionListener) {
            StartEnterTransitionListener x0 = startEnterTransitionListener;
            StartEnterTransitionListener startEnterTransitionListener2 = x0;
            return x0.mIsBack;
        }

        static /* synthetic */ BackStackRecord access$100(StartEnterTransitionListener startEnterTransitionListener) {
            StartEnterTransitionListener x0 = startEnterTransitionListener;
            StartEnterTransitionListener startEnterTransitionListener2 = x0;
            return x0.mRecord;
        }

        StartEnterTransitionListener(BackStackRecord backStackRecord, boolean z) {
            BackStackRecord record = backStackRecord;
            BackStackRecord backStackRecord2 = record;
            this.mIsBack = z;
            this.mRecord = record;
        }

        public void onStartEnterTransition() {
            this.mNumPostponed--;
            if (this.mNumPostponed == 0) {
                FragmentManagerImpl.access$200(this.mRecord.mManager);
            }
        }

        public void startListening() {
            this.mNumPostponed++;
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        public void completeTransaction() {
            boolean canceled = this.mNumPostponed > 0;
            FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
            FragmentManagerImpl manager = fragmentManagerImpl;
            int numAdded = fragmentManagerImpl.mAdded.size();
            for (int i = 0; i < numAdded; i++) {
                Fragment fragment = (Fragment) manager.mAdded.get(i);
                Fragment fragment2 = fragment;
                fragment.setOnStartEnterTransitionListener(null);
                if (canceled && fragment2.isPostponed()) {
                    fragment2.startPostponedEnterTransition();
                }
            }
            FragmentManagerImpl.access$300(this.mRecord.mManager, this.mRecord, this.mIsBack, !canceled, true);
        }

        public void cancelTransaction() {
            FragmentManagerImpl.access$300(this.mRecord.mManager, this.mRecord, this.mIsBack, false, false);
        }
    }

    FragmentManagerImpl() {
    }

    static /* synthetic */ void access$200(FragmentManagerImpl fragmentManagerImpl) {
        FragmentManagerImpl x0 = fragmentManagerImpl;
        FragmentManagerImpl fragmentManagerImpl2 = x0;
        x0.scheduleCommit();
    }

    static /* synthetic */ void access$300(FragmentManagerImpl fragmentManagerImpl, BackStackRecord backStackRecord, boolean z, boolean z2, boolean z3) {
        FragmentManagerImpl x0 = fragmentManagerImpl;
        BackStackRecord x1 = backStackRecord;
        FragmentManagerImpl fragmentManagerImpl2 = x0;
        BackStackRecord backStackRecord2 = x1;
        x0.completeExecute(x1, z, z2, z3);
    }

    static {
        boolean z;
        if (VERSION.SDK_INT < 11) {
            z = false;
        } else {
            z = true;
        }
        HONEYCOMB = z;
    }

    static boolean modifiesAlpha(Animation animation) {
        Animation anim = animation;
        Animation animation2 = anim;
        if (anim instanceof AlphaAnimation) {
            return true;
        }
        if (anim instanceof AnimationSet) {
            List animations = ((AnimationSet) anim).getAnimations();
            for (int i = 0; i < animations.size(); i++) {
                if (animations.get(i) instanceof AlphaAnimation) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean shouldRunOnHWLayer(View view, Animation animation) {
        View v = view;
        Animation anim = animation;
        View view2 = v;
        Animation animation2 = anim;
        return VERSION.SDK_INT >= 19 && ViewCompat.getLayerType(v) == 0 && ViewCompat.hasOverlappingRendering(v) && modifiesAlpha(anim);
    }

    private void throwException(RuntimeException runtimeException) {
        RuntimeException ex = runtimeException;
        RuntimeException runtimeException2 = ex;
        int e = Log.e(TAG, ex.getMessage());
        int e2 = Log.e(TAG, "Activity state:");
        PrintWriter pw = new PrintWriter(new LogWriter(TAG));
        if (this.mHost == null) {
            try {
                dump("  ", null, pw, new String[0]);
            } catch (Exception e3) {
                Exception exc = e3;
                int e4 = Log.e(TAG, "Failed dumping state", e3);
            }
        } else {
            try {
                this.mHost.onDump("  ", null, pw, new String[0]);
            } catch (Exception e5) {
                Exception exc2 = e5;
                int e6 = Log.e(TAG, "Failed dumping state", e5);
            }
        }
        throw ex;
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        boolean execPendingActions = execPendingActions();
        boolean z = execPendingActions;
        forcePostponedTransactions();
        return execPendingActions;
    }

    public void popBackStack() {
        enqueueAction(new PopBackStackState(this, null, -1, 0), false);
    }

    public boolean popBackStackImmediate() {
        checkStateLoss();
        return popBackStackImmediate(null, -1, 0);
    }

    public void popBackStack(String str, int i) {
        String name = str;
        int flags = i;
        String str2 = name;
        int i2 = flags;
        enqueueAction(new PopBackStackState(this, name, -1, flags), false);
    }

    public boolean popBackStackImmediate(String str, int i) {
        String name = str;
        int flags = i;
        String str2 = name;
        int i2 = flags;
        checkStateLoss();
        return popBackStackImmediate(name, -1, flags);
    }

    public void popBackStack(int i, int i2) {
        int id = i;
        int flags = i2;
        int i3 = id;
        int i4 = flags;
        if (id >= 0) {
            enqueueAction(new PopBackStackState(this, null, id, flags), false);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + id);
    }

    public boolean popBackStackImmediate(int i, int i2) {
        int id = i;
        int flags = i2;
        int i3 = id;
        int i4 = flags;
        checkStateLoss();
        boolean execPendingActions = execPendingActions();
        if (id >= 0) {
            return popBackStackImmediate(null, id, flags);
        }
        throw new IllegalArgumentException("Bad id: " + id);
    }

    private boolean popBackStackImmediate(String str, int i, int i2) {
        String name = str;
        int id = i;
        int flags = i2;
        String str2 = name;
        int i3 = id;
        int i4 = flags;
        boolean execPendingActions = execPendingActions();
        ensureExecReady(true);
        boolean popBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, name, id, flags);
        boolean executePop = popBackStackState;
        if (popBackStackState) {
            this.mExecutingActions = true;
            try {
                optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        return executePop;
    }

    public int getBackStackEntryCount() {
        return this.mBackStack == null ? 0 : this.mBackStack.size();
    }

    public BackStackEntry getBackStackEntryAt(int i) {
        int index = i;
        int i2 = index;
        return (BackStackEntry) this.mBackStack.get(index);
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {
        OnBackStackChangedListener listener = onBackStackChangedListener;
        OnBackStackChangedListener onBackStackChangedListener2 = listener;
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        boolean add = this.mBackStackChangeListeners.add(listener);
    }

    public void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {
        OnBackStackChangedListener listener = onBackStackChangedListener;
        OnBackStackChangedListener onBackStackChangedListener2 = listener;
        if (this.mBackStackChangeListeners != null) {
            boolean remove = this.mBackStackChangeListeners.remove(listener);
        }
    }

    public void putFragment(Bundle bundle, String str, Fragment fragment) {
        Bundle bundle2 = bundle;
        String key = str;
        Fragment fragment2 = fragment;
        Bundle bundle3 = bundle2;
        String str2 = key;
        Fragment fragment3 = fragment2;
        if (fragment2.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment2 + " is not currently in the FragmentManager"));
        }
        bundle2.putInt(key, fragment2.mIndex);
    }

    public Fragment getFragment(Bundle bundle, String str) {
        Bundle bundle2 = bundle;
        String key = str;
        Bundle bundle3 = bundle2;
        String str2 = key;
        int i = bundle2.getInt(key, -1);
        int index = i;
        if (i == -1) {
            return null;
        }
        if (index >= this.mActive.size()) {
            IllegalStateException illegalStateException = new IllegalStateException("Fragment no longer exists for key " + key + ": index " + index);
            throwException(illegalStateException);
        }
        Fragment fragment = (Fragment) this.mActive.get(index);
        Fragment f = fragment;
        if (fragment == null) {
            IllegalStateException illegalStateException2 = new IllegalStateException("Fragment no longer exists for key " + key + ": index " + index);
            throwException(illegalStateException2);
        }
        return f;
    }

    public List<Fragment> getFragments() {
        return this.mActive;
    }

    public SavedState saveFragmentInstanceState(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        if (fragment2.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment2 + " is not currently in the FragmentManager"));
        }
        if (fragment2.mState <= 0) {
            return null;
        }
        Bundle saveFragmentBasicState = saveFragmentBasicState(fragment2);
        return saveFragmentBasicState == null ? null : new SavedState(saveFragmentBasicState);
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        StringBuilder sb2 = sb;
        StringBuilder append = sb.append("FragmentManager{");
        StringBuilder append2 = sb2.append(Integer.toHexString(System.identityHashCode(this)));
        StringBuilder append3 = sb2.append(" in ");
        if (this.mParent == null) {
            DebugUtils.buildShortClassTag(this.mHost, sb2);
        } else {
            DebugUtils.buildShortClassTag(this.mParent, sb2);
        }
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
        String innerPrefix = prefix + "    ";
        if (this.mActive != null) {
            int size = this.mActive.size();
            int N = size;
            if (size > 0) {
                writer.print(prefix);
                writer.print("Active Fragments in ");
                writer.print(Integer.toHexString(System.identityHashCode(this)));
                writer.println(":");
                for (int i = 0; i < N; i++) {
                    Fragment f = (Fragment) this.mActive.get(i);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i);
                    writer.print(": ");
                    writer.println(f);
                    if (f != null) {
                        f.dump(innerPrefix, fd, writer, args);
                    }
                }
            }
        }
        if (this.mAdded != null) {
            int size2 = this.mAdded.size();
            int N2 = size2;
            if (size2 > 0) {
                writer.print(prefix);
                writer.println("Added Fragments:");
                for (int i2 = 0; i2 < N2; i2++) {
                    Fragment f2 = (Fragment) this.mAdded.get(i2);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i2);
                    writer.print(": ");
                    writer.println(f2.toString());
                }
            }
        }
        if (this.mCreatedMenus != null) {
            int size3 = this.mCreatedMenus.size();
            int N3 = size3;
            if (size3 > 0) {
                writer.print(prefix);
                writer.println("Fragments Created Menus:");
                for (int i3 = 0; i3 < N3; i3++) {
                    Fragment f3 = (Fragment) this.mCreatedMenus.get(i3);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i3);
                    writer.print(": ");
                    writer.println(f3.toString());
                }
            }
        }
        if (this.mBackStack != null) {
            int size4 = this.mBackStack.size();
            int N4 = size4;
            if (size4 > 0) {
                writer.print(prefix);
                writer.println("Back Stack:");
                for (int i4 = 0; i4 < N4; i4++) {
                    BackStackRecord bs = (BackStackRecord) this.mBackStack.get(i4);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i4);
                    writer.print(": ");
                    writer.println(bs.toString());
                    bs.dump(innerPrefix, fd, writer, args);
                }
            }
        }
        synchronized (this) {
            try {
                if (this.mBackStackIndices != null) {
                    int size5 = this.mBackStackIndices.size();
                    int N5 = size5;
                    if (size5 > 0) {
                        writer.print(prefix);
                        writer.println("Back Stack Indices:");
                        for (int i5 = 0; i5 < N5; i5++) {
                            BackStackRecord bs2 = (BackStackRecord) this.mBackStackIndices.get(i5);
                            writer.print(prefix);
                            writer.print("  #");
                            writer.print(i5);
                            writer.print(": ");
                            writer.println(bs2);
                        }
                    }
                }
                if (this.mAvailBackStackIndices != null) {
                    if (this.mAvailBackStackIndices.size() > 0) {
                        writer.print(prefix);
                        writer.print("mAvailBackStackIndices: ");
                        writer.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
                    }
                }
            } finally {
            }
        }
        if (this.mPendingActions != null) {
            int size6 = this.mPendingActions.size();
            int N6 = size6;
            if (size6 > 0) {
                writer.print(prefix);
                writer.println("Pending Actions:");
                for (int i6 = 0; i6 < N6; i6++) {
                    OpGenerator r = (OpGenerator) this.mPendingActions.get(i6);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i6);
                    writer.print(": ");
                    writer.println(r);
                }
            }
        }
        writer.print(prefix);
        writer.println("FragmentManager misc state:");
        writer.print(prefix);
        writer.print("  mHost=");
        writer.println(this.mHost);
        writer.print(prefix);
        writer.print("  mContainer=");
        writer.println(this.mContainer);
        if (this.mParent != null) {
            writer.print(prefix);
            writer.print("  mParent=");
            writer.println(this.mParent);
        }
        writer.print(prefix);
        writer.print("  mCurState=");
        writer.print(this.mCurState);
        writer.print(" mStateSaved=");
        writer.print(this.mStateSaved);
        writer.print(" mDestroyed=");
        writer.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            writer.print(prefix);
            writer.print("  mNeedMenuInvalidate=");
            writer.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            writer.print(prefix);
            writer.print("  mNoTransactionsBecause=");
            writer.println(this.mNoTransactionsBecause);
        }
        if (this.mAvailIndices != null && this.mAvailIndices.size() > 0) {
            writer.print(prefix);
            writer.print("  mAvailIndices: ");
            writer.println(Arrays.toString(this.mAvailIndices.toArray()));
        }
    }

    static Animation makeOpenCloseAnimation(Context context, float f, float f2, float f3, float f4) {
        float startScale = f;
        float endScale = f2;
        float startAlpha = f3;
        float endAlpha = f4;
        Context context2 = context;
        float f5 = startScale;
        float f6 = endScale;
        float f7 = startAlpha;
        float f8 = endAlpha;
        AnimationSet set = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(startScale, endScale, startScale, endScale, 1, 0.5f, 1, 0.5f);
        ScaleAnimation scale = scaleAnimation;
        scaleAnimation.setInterpolator(DECELERATE_QUINT);
        scale.setDuration(220);
        set.addAnimation(scale);
        AlphaAnimation alphaAnimation = new AlphaAnimation(startAlpha, endAlpha);
        AlphaAnimation alpha = alphaAnimation;
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        alpha.setDuration(220);
        set.addAnimation(alpha);
        return set;
    }

    static Animation makeFadeAnimation(Context context, float f, float f2) {
        float start = f;
        float end = f2;
        Context context2 = context;
        float f3 = start;
        float f4 = end;
        AlphaAnimation alphaAnimation = new AlphaAnimation(start, end);
        AlphaAnimation anim = alphaAnimation;
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        anim.setDuration(220);
        return anim;
    }

    /* access modifiers changed from: 0000 */
    public Animation loadAnimation(Fragment fragment, int i, boolean z, int i2) {
        Fragment fragment2 = fragment;
        int transit = i;
        int transitionStyle = i2;
        Fragment fragment3 = fragment2;
        int i3 = transit;
        boolean enter = z;
        int transitionStyle2 = transitionStyle;
        Animation onCreateAnimation = fragment2.onCreateAnimation(transit, enter, fragment2.getNextAnim());
        Animation animObj = onCreateAnimation;
        if (onCreateAnimation != null) {
            return animObj;
        }
        if (fragment2.getNextAnim() != 0) {
            Animation loadAnimation = AnimationUtils.loadAnimation(this.mHost.getContext(), fragment2.getNextAnim());
            Animation anim = loadAnimation;
            if (loadAnimation != null) {
                return anim;
            }
        }
        if (transit == 0) {
            return null;
        }
        int transitToStyleIndex = transitToStyleIndex(transit, enter);
        int styleIndex = transitToStyleIndex;
        if (transitToStyleIndex < 0) {
            return null;
        }
        switch (styleIndex) {
            case 1:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            case 6:
                return makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
            default:
                if (transitionStyle == 0 && this.mHost.onHasWindowAnimations()) {
                    transitionStyle2 = this.mHost.onGetWindowAnimations();
                }
                if (transitionStyle2 != 0) {
                    return null;
                }
                return null;
        }
    }

    public void performPendingDeferredStart(Fragment fragment) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        if (f.mDeferStart) {
            if (!this.mExecutingActions) {
                f.mDeferStart = false;
                moveToState(f, this.mCurState, 0, 0, false);
            } else {
                this.mHavePendingDeferredStart = true;
            }
        }
    }

    private void setHWLayerAnimListenerIfAlpha(View view, Animation animation) {
        View v = view;
        Animation anim = animation;
        View view2 = v;
        Animation animation2 = anim;
        if (!(v == null || anim == null || !shouldRunOnHWLayer(v, anim))) {
            AnimationListener originalListener = null;
            try {
                if (sAnimationListenerField == null) {
                    sAnimationListenerField = Animation.class.getDeclaredField("mListener");
                    sAnimationListenerField.setAccessible(true);
                }
                originalListener = (AnimationListener) sAnimationListenerField.get(anim);
            } catch (NoSuchFieldException e) {
                NoSuchFieldException noSuchFieldException = e;
                int e2 = Log.e(TAG, "No field with the name mListener is found in Animation class", e);
            } catch (IllegalAccessException e3) {
                IllegalAccessException illegalAccessException = e3;
                int e4 = Log.e(TAG, "Cannot access Animation's mListener field", e3);
            }
            ViewCompat.setLayerType(v, 2, null);
            AnimateOnHWLayerIfNeededListener animateOnHWLayerIfNeededListener = new AnimateOnHWLayerIfNeededListener(v, anim, originalListener);
            anim.setAnimationListener(animateOnHWLayerIfNeededListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isStateAtLeast(int i) {
        int state = i;
        int i2 = state;
        return this.mCurState >= state;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x04b3, code lost:
        if (r7.mContainerId == -1) goto L_0x04d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x04b5, code lost:
        r27 = (android.view.ViewGroup) r6.mContainer.onFindViewById(r7.mContainerId);
        r93 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x04cf, code lost:
        if (r27 == null) goto L_0x050e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x04d2, code lost:
        r0 = new java.lang.IllegalArgumentException("Cannot create fragment " + r7 + " for a container view with no id");
        throwException(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x051a, code lost:
        if (r7.mRestored == false) goto L_0x051e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x052d, code lost:
        r99 = r7.getResources().getResourceName(r7.mContainerId);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0596, code lost:
        r100 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0597, code lost:
        r101 = r100;
        r99 = android.support.p000v4.p002os.EnvironmentCompat.MEDIA_UNKNOWN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01d1, code lost:
        if (r14 > 1) goto L_0x03e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x03ed, code lost:
        if (DEBUG != false) goto L_0x0431;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x03fb, code lost:
        if (r7.mFromLayout == false) goto L_0x0459;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0431, code lost:
        r19 = android.util.Log.v(TAG, "moveto ACTIVITY_CREATED: " + r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0459, code lost:
        r93 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0467, code lost:
        if (r7.mContainerId != 0) goto L_0x04a9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x0a35  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveToState(android.support.p000v4.app.Fragment r201, int r202, int r203, int r204, boolean r205) {
        /*
            r200 = this;
            r6 = r200
            r7 = r201
            r8 = r202
            r9 = r203
            r10 = r204
            r11 = r205
            r12 = r6
            r13 = r7
            r14 = r8
            r15 = r9
            r16 = r10
            r17 = r11
            boolean r0 = r7.mAdded
            r18 = r0
            r19 = r18
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x005d
        L_0x0022:
            r20 = 1
            r0 = r20
            if (r8 > r0) goto L_0x006c
        L_0x0028:
            boolean r0 = r7.mRemoving
            r22 = r0
            r19 = r22
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x006e
        L_0x0036:
            boolean r0 = r7.mDeferStart
            r24 = r0
            r19 = r24
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0082
        L_0x0044:
            int r0 = r7.mState
            r19 = r0
            r0 = r19
            if (r0 < r14) goto L_0x0098
            int r0 = r7.mState
            r19 = r0
            r0 = r19
            if (r0 > r14) goto L_0x0704
        L_0x0054:
            int r0 = r7.mState
            r19 = r0
            r0 = r19
            if (r0 != r14) goto L_0x0a35
        L_0x005c:
            return
        L_0x005d:
            boolean r0 = r7.mDetached
            r21 = r0
            r19 = r21
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0022
            goto L_0x0028
        L_0x006c:
            r14 = 1
            goto L_0x0028
        L_0x006e:
            r19 = r14
            int r0 = r7.mState
            r23 = r0
            r0 = r19
            r1 = r23
            if (r0 > r1) goto L_0x007b
            goto L_0x0036
        L_0x007b:
            int r0 = r7.mState
            r19 = r0
            r14 = r19
            goto L_0x0036
        L_0x0082:
            int r0 = r7.mState
            r19 = r0
            r20 = 4
            r0 = r19
            r1 = r20
            if (r0 < r1) goto L_0x008f
            goto L_0x0044
        L_0x008f:
            r20 = 3
            r0 = r20
            if (r14 > r0) goto L_0x0096
            goto L_0x0044
        L_0x0096:
            r14 = 3
            goto L_0x0044
        L_0x0098:
            boolean r0 = r7.mFromLayout
            r25 = r0
            r19 = r25
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x00ba
        L_0x00a6:
            android.view.View r27 = r7.getAnimatingAway()
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x00ca
        L_0x00b2:
            int r0 = r7.mState
            r19 = r0
            switch(r19) {
                case 0: goto L_0x00f0;
                case 1: goto L_0x01e7;
                case 2: goto L_0x01e8;
                case 3: goto L_0x01e9;
                case 4: goto L_0x01ea;
                default: goto L_0x00b9;
            }
        L_0x00b9:
            goto L_0x0054
        L_0x00ba:
            boolean r0 = r7.mInLayout
            r26 = r0
            r19 = r26
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x00c9
            goto L_0x00a6
        L_0x00c9:
            return
        L_0x00ca:
            r29 = 0
            r28 = 0
            r30 = r28
            r0 = r30
            r7.setAnimatingAway(r0)
            int r31 = r7.getStateAfterAnimating()
            r32 = 0
            r33 = 0
            r34 = r7
            r35 = 1
            r0 = r6
            r1 = r34
            r2 = r31
            r3 = r32
            r4 = r33
            r5 = r35
            r0.moveToState(r1, r2, r3, r4, r5)
            goto L_0x00b2
        L_0x00f0:
            boolean r36 = DEBUG
            r19 = r36
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x01eb
        L_0x00fc:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0214
        L_0x0108:
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r29 = r0
            r0 = r29
            r7.mHost = r0
            android.support.v4.app.Fragment r0 = r6.mParent
            r29 = r0
            r0 = r29
            r7.mParentFragment = r0
            android.support.v4.app.Fragment r0 = r6.mParent
            r29 = r0
            r28 = 0
            r0 = r29
            r1 = r28
            if (r0 != r1) goto L_0x02bd
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r29 = r0
            android.support.v4.app.FragmentManagerImpl r29 = r29.getFragmentManagerImpl()
        L_0x012c:
            r0 = r29
            r7.mFragmentManager = r0
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r37 = r0
            android.content.Context r37 = r37.getContext()
            r53 = r7
            r54 = r37
            r55 = 0
            r0 = r53
            r1 = r54
            r2 = r55
            r6.dispatchOnFragmentPreAttached(r0, r1, r2)
            r52 = 0
            r0 = r52
            r7.mCalled = r0
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r29 = r0
            android.content.Context r29 = r29.getContext()
            r56 = r29
            r0 = r56
            r7.onAttach(r0)
            boolean r0 = r7.mCalled
            r57 = r0
            r19 = r57
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x02c9
            android.support.v4.app.Fragment r0 = r7.mParentFragment
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 == r1) goto L_0x02fe
            android.support.v4.app.Fragment r0 = r7.mParentFragment
            r27 = r0
            r62 = r7
            r0 = r27
            r1 = r62
            r0.onAttachFragment(r1)
        L_0x0183:
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r37 = r0
            android.content.Context r37 = r37.getContext()
            r63 = r7
            r64 = r37
            r65 = 0
            r0 = r63
            r1 = r64
            r2 = r65
            r6.dispatchOnFragmentAttached(r0, r1, r2)
            boolean r0 = r7.mRetaining
            r66 = r0
            r19 = r66
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x030d
            android.os.Bundle r0 = r7.mSavedFragmentState
            r29 = r0
            r71 = r29
            r0 = r71
            r7.restoreChildFragmentState(r0)
            r20 = 1
            r0 = r20
            r7.mState = r0
        L_0x01b9:
            r52 = 0
            r0 = r52
            r7.mRetaining = r0
            boolean r0 = r7.mFromLayout
            r72 = r0
            r19 = r72
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x032d
        L_0x01cd:
            r20 = 1
            r0 = r20
            if (r14 > r0) goto L_0x03e3
        L_0x01d3:
            r20 = 2
            r0 = r20
            if (r14 > r0) goto L_0x0664
        L_0x01d9:
            r20 = 3
            r0 = r20
            if (r14 > r0) goto L_0x066c
        L_0x01df:
            r20 = 4
            r0 = r20
            if (r14 > r0) goto L_0x06b0
            goto L_0x00b9
        L_0x01e7:
            goto L_0x01cd
        L_0x01e8:
            goto L_0x01d3
        L_0x01e9:
            goto L_0x01d9
        L_0x01ea:
            goto L_0x01df
        L_0x01eb:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "moveto CREATED: "
            r38 = r37
            r0 = r29
            r1 = r38
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r39 = r27
            r40 = r29
            int r19 = android.util.Log.v(r39, r40)
            goto L_0x00fc
        L_0x0214:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r27 = r0
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r29 = r0
            android.content.Context r29 = r29.getContext()
            java.lang.ClassLoader r29 = r29.getClassLoader()
            r41 = r29
            r0 = r27
            r1 = r41
            r0.setClassLoader(r1)
            android.os.Bundle r0 = r7.mSavedFragmentState
            r29 = r0
            java.lang.String r37 = "android:view_state"
            r42 = r37
            r0 = r29
            r1 = r42
            android.util.SparseArray r29 = r0.getSparseParcelableArray(r1)
            r0 = r29
            r7.mSavedViewState = r0
            android.os.Bundle r0 = r7.mSavedFragmentState
            r37 = r0
            java.lang.String r43 = "android:target_state"
            r44 = r37
            r45 = r43
            r0 = r44
            r1 = r45
            android.support.v4.app.Fragment r29 = r6.getFragment(r0, r1)
            r0 = r29
            r7.mTarget = r0
            android.support.v4.app.Fragment r0 = r7.mTarget
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0292
        L_0x0265:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r29 = r0
            java.lang.String r37 = "android:user_visible_hint"
            r47 = r37
            r48 = 1
            r0 = r29
            r1 = r47
            r2 = r48
            boolean r49 = r0.getBoolean(r1, r2)
            r23 = r49
            r50 = r23
            r0 = r50
            r7.mUserVisibleHint = r0
            boolean r0 = r7.mUserVisibleHint
            r51 = r0
            r19 = r51
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x02ac
            goto L_0x0108
        L_0x0292:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r29 = r0
            java.lang.String r37 = "android:target_req_state"
            r32 = 0
            r46 = r37
            r0 = r29
            r1 = r46
            r2 = r32
            int r23 = r0.getInt(r1, r2)
            r0 = r23
            r7.mTargetRequestCode = r0
            goto L_0x0265
        L_0x02ac:
            r52 = 1
            r0 = r52
            r7.mDeferStart = r0
            r20 = 3
            r0 = r20
            if (r14 > r0) goto L_0x02ba
            goto L_0x0108
        L_0x02ba:
            r14 = 3
            goto L_0x0108
        L_0x02bd:
            android.support.v4.app.Fragment r0 = r6.mParent
            r29 = r0
            r0 = r29
            android.support.v4.app.FragmentManagerImpl r0 = r0.mChildFragmentManager
            r29 = r0
            goto L_0x012c
        L_0x02c9:
            android.support.v4.app.SuperNotCalledException r27 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r37 = new java.lang.StringBuilder
            r37.<init>()
            java.lang.String r43 = "Fragment "
            r58 = r43
            r0 = r37
            r1 = r58
            java.lang.StringBuilder r37 = r0.append(r1)
            r0 = r37
            java.lang.StringBuilder r37 = r0.append(r7)
            java.lang.String r43 = " did not call through to super.onAttach()"
            r59 = r43
            r0 = r37
            r1 = r59
            java.lang.StringBuilder r37 = r0.append(r1)
            java.lang.String r37 = r37.toString()
            r60 = r37
            r0 = r27
            r1 = r60
            r0.<init>(r1)
            throw r27
        L_0x02fe:
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r27 = r0
            r61 = r7
            r0 = r27
            r1 = r61
            r0.onAttachFragment(r1)
            goto L_0x0183
        L_0x030d:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r29 = r0
            r67 = r29
            r0 = r67
            r7.performCreate(r0)
            android.os.Bundle r0 = r7.mSavedFragmentState
            r37 = r0
            r68 = r7
            r69 = r37
            r70 = 0
            r0 = r68
            r1 = r69
            r2 = r70
            r6.dispatchOnFragmentCreated(r0, r1, r2)
            goto L_0x01b9
        L_0x032d:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r43 = r0
            r73 = r43
            r0 = r73
            android.view.LayoutInflater r37 = r7.getLayoutInflater(r0)
            r43 = 0
            android.os.Bundle r0 = r7.mSavedFragmentState
            r74 = r0
            r75 = r37
            r28 = 0
            r76 = r28
            r77 = r74
            r0 = r75
            r1 = r76
            r2 = r77
            android.view.View r29 = r7.performCreateView(r0, r1, r2)
            r0 = r29
            r7.mView = r0
            android.view.View r0 = r7.mView
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x036b
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mInnerView = r0
            goto L_0x01cd
        L_0x036b:
            android.view.View r0 = r7.mView
            r29 = r0
            r0 = r29
            r7.mInnerView = r0
            int r19 = android.os.Build.VERSION.SDK_INT
            r20 = 11
            r0 = r19
            r1 = r20
            if (r0 >= r1) goto L_0x03c9
            android.view.View r0 = r7.mView
            r29 = r0
            r80 = r29
            android.view.ViewGroup r29 = android.support.p000v4.app.NoSaveStateFrameLayout.wrap(r80)
            r0 = r29
            r7.mView = r0
        L_0x038b:
            boolean r0 = r7.mHidden
            r81 = r0
            r19 = r81
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x03d5
        L_0x0399:
            android.view.View r0 = r7.mView
            r29 = r0
            android.os.Bundle r0 = r7.mSavedFragmentState
            r37 = r0
            r82 = r29
            r83 = r37
            r0 = r82
            r1 = r83
            r7.onViewCreated(r0, r1)
            android.view.View r0 = r7.mView
            r37 = r0
            android.os.Bundle r0 = r7.mSavedFragmentState
            r43 = r0
            r84 = r7
            r85 = r37
            r86 = r43
            r87 = 0
            r0 = r84
            r1 = r85
            r2 = r86
            r3 = r87
            r6.dispatchOnFragmentViewCreated(r0, r1, r2, r3)
            goto L_0x01cd
        L_0x03c9:
            android.view.View r0 = r7.mView
            r27 = r0
            r78 = r27
            r79 = 0
            android.support.p000v4.view.ViewCompat.setSaveFromParentEnabled(r78, r79)
            goto L_0x038b
        L_0x03d5:
            android.view.View r0 = r7.mView
            r27 = r0
            r23 = 8
            r0 = r27
            r1 = r23
            r0.setVisibility(r1)
            goto L_0x0399
        L_0x03e3:
            boolean r88 = DEBUG
            r19 = r88
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0431
        L_0x03ef:
            boolean r0 = r7.mFromLayout
            r92 = r0
            r19 = r92
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x0459
        L_0x03fd:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r29 = r0
            r125 = r29
            r0 = r125
            r7.performActivityCreated(r0)
            android.os.Bundle r0 = r7.mSavedFragmentState
            r37 = r0
            r126 = r7
            r127 = r37
            r128 = 0
            r0 = r126
            r1 = r127
            r2 = r128
            r6.dispatchOnFragmentActivityCreated(r0, r1, r2)
            android.view.View r0 = r7.mView
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0657
        L_0x0427:
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mSavedFragmentState = r0
            goto L_0x01d3
        L_0x0431:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "moveto ACTIVITY_CREATED: "
            r89 = r37
            r0 = r29
            r1 = r89
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r90 = r27
            r91 = r29
            int r19 = android.util.Log.v(r90, r91)
            goto L_0x03ef
        L_0x0459:
            r27 = 0
            r93 = 0
            int r0 = r7.mContainerId
            r19 = r0
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x04a9
        L_0x0469:
            r0 = r93
            r7.mContainer = r0
            android.os.Bundle r0 = r7.mSavedFragmentState
            r43 = r0
            r109 = r43
            r0 = r109
            android.view.LayoutInflater r37 = r7.getLayoutInflater(r0)
            r43 = r93
            android.os.Bundle r0 = r7.mSavedFragmentState
            r74 = r0
            r110 = r37
            r111 = r43
            r112 = r74
            r0 = r110
            r1 = r111
            r2 = r112
            android.view.View r29 = r7.performCreateView(r0, r1, r2)
            r0 = r29
            r7.mView = r0
            android.view.View r0 = r7.mView
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x059f
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mInnerView = r0
            goto L_0x03fd
        L_0x04a9:
            int r0 = r7.mContainerId
            r19 = r0
            r20 = -1
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x04d2
        L_0x04b5:
            android.support.v4.app.FragmentContainer r0 = r6.mContainer
            r27 = r0
            int r0 = r7.mContainerId
            r23 = r0
            r0 = r27
            r1 = r23
            android.view.View r27 = r0.onFindViewById(r1)
            android.view.ViewGroup r27 = (android.view.ViewGroup) r27
            r93 = r27
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 == r1) goto L_0x050e
            goto L_0x0469
        L_0x04d2:
            java.lang.IllegalArgumentException r29 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r43 = new java.lang.StringBuilder
            r43.<init>()
            java.lang.String r74 = "Cannot create fragment "
            r94 = r74
            r0 = r43
            r1 = r94
            java.lang.StringBuilder r43 = r0.append(r1)
            r0 = r43
            java.lang.StringBuilder r43 = r0.append(r7)
            java.lang.String r74 = " for a container view with no id"
            r95 = r74
            r0 = r43
            r1 = r95
            java.lang.StringBuilder r43 = r0.append(r1)
            java.lang.String r43 = r43.toString()
            r96 = r43
            r0 = r29
            r1 = r96
            r0.<init>(r1)
            r97 = r29
            r0 = r97
            r6.throwException(r0)
            goto L_0x04b5
        L_0x050e:
            boolean r0 = r7.mRestored
            r98 = r0
            r19 = r98
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x051e
            goto L_0x0469
        L_0x051e:
            android.content.res.Resources r27 = r7.getResources()     // Catch:{ NotFoundException -> 0x0596 }
            int r0 = r7.mContainerId     // Catch:{ NotFoundException -> 0x0596 }
            r23 = r0
            r0 = r27
            r1 = r23
            java.lang.String r27 = r0.getResourceName(r1)     // Catch:{ NotFoundException -> 0x0596 }
            r99 = r27
        L_0x0530:
            java.lang.IllegalArgumentException r29 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r43 = new java.lang.StringBuilder
            r43.<init>()
            java.lang.String r74 = "No view found for id 0x"
            r102 = r74
            r0 = r43
            r1 = r102
            java.lang.StringBuilder r43 = r0.append(r1)
            int r0 = r7.mContainerId
            r33 = r0
            java.lang.String r74 = java.lang.Integer.toHexString(r33)
            r103 = r74
            r0 = r43
            r1 = r103
            java.lang.StringBuilder r43 = r0.append(r1)
            java.lang.String r74 = " ("
            r104 = r74
            r0 = r43
            r1 = r104
            java.lang.StringBuilder r43 = r0.append(r1)
            r105 = r99
            r0 = r43
            r1 = r105
            java.lang.StringBuilder r43 = r0.append(r1)
            java.lang.String r74 = ") for fragment "
            r106 = r74
            r0 = r43
            r1 = r106
            java.lang.StringBuilder r43 = r0.append(r1)
            r0 = r43
            java.lang.StringBuilder r43 = r0.append(r7)
            java.lang.String r43 = r43.toString()
            r107 = r43
            r0 = r29
            r1 = r107
            r0.<init>(r1)
            r108 = r29
            r0 = r108
            r6.throwException(r0)
            goto L_0x0469
        L_0x0596:
            r100 = move-exception
            r101 = r100
            java.lang.String r27 = "unknown"
            r99 = r27
            goto L_0x0530
        L_0x059f:
            android.view.View r0 = r7.mView
            r29 = r0
            r0 = r29
            r7.mInnerView = r0
            int r19 = android.os.Build.VERSION.SDK_INT
            r20 = 11
            r0 = r19
            r1 = r20
            if (r0 >= r1) goto L_0x061d
            android.view.View r0 = r7.mView
            r29 = r0
            r115 = r29
            android.view.ViewGroup r29 = android.support.p000v4.app.NoSaveStateFrameLayout.wrap(r115)
            r0 = r29
            r7.mView = r0
        L_0x05bf:
            r28 = 0
            r0 = r93
            r1 = r28
            if (r0 != r1) goto L_0x0629
        L_0x05c7:
            boolean r0 = r7.mHidden
            r117 = r0
            r19 = r117
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0639
        L_0x05d5:
            android.view.View r0 = r7.mView
            r29 = r0
            android.os.Bundle r0 = r7.mSavedFragmentState
            r37 = r0
            r118 = r29
            r119 = r37
            r0 = r118
            r1 = r119
            r7.onViewCreated(r0, r1)
            android.view.View r0 = r7.mView
            r37 = r0
            android.os.Bundle r0 = r7.mSavedFragmentState
            r43 = r0
            r120 = r7
            r121 = r37
            r122 = r43
            r123 = 0
            r0 = r120
            r1 = r121
            r2 = r122
            r3 = r123
            r6.dispatchOnFragmentViewCreated(r0, r1, r2, r3)
            android.view.View r0 = r7.mView
            r29 = r0
            int r23 = r29.getVisibility()
            r20 = 0
            r0 = r23
            r1 = r20
            if (r0 == r1) goto L_0x0647
        L_0x0613:
            r23 = 0
        L_0x0615:
            r124 = r23
            r0 = r124
            r7.mIsNewlyAdded = r0
            goto L_0x03fd
        L_0x061d:
            android.view.View r0 = r7.mView
            r27 = r0
            r113 = r27
            r114 = 0
            android.support.p000v4.view.ViewCompat.setSaveFromParentEnabled(r113, r114)
            goto L_0x05bf
        L_0x0629:
            r27 = r93
            android.view.View r0 = r7.mView
            r29 = r0
            r116 = r29
            r0 = r27
            r1 = r116
            r0.addView(r1)
            goto L_0x05c7
        L_0x0639:
            android.view.View r0 = r7.mView
            r27 = r0
            r23 = 8
            r0 = r27
            r1 = r23
            r0.setVisibility(r1)
            goto L_0x05d5
        L_0x0647:
            android.view.ViewGroup r0 = r7.mContainer
            r29 = r0
            r28 = 0
            r0 = r29
            r1 = r28
            if (r0 != r1) goto L_0x0654
            goto L_0x0613
        L_0x0654:
            r23 = 1
            goto L_0x0615
        L_0x0657:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r29 = r0
            r129 = r29
            r0 = r129
            r7.restoreViewState(r0)
            goto L_0x0427
        L_0x0664:
            r20 = 3
            r0 = r20
            r7.mState = r0
            goto L_0x01d9
        L_0x066c:
            boolean r130 = DEBUG
            r19 = r130
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0688
        L_0x0678:
            r7.performStart()
            r134 = r7
            r135 = 0
            r0 = r134
            r1 = r135
            r6.dispatchOnFragmentStarted(r0, r1)
            goto L_0x01df
        L_0x0688:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "moveto STARTED: "
            r131 = r37
            r0 = r29
            r1 = r131
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r132 = r27
            r133 = r29
            int r19 = android.util.Log.v(r132, r133)
            goto L_0x0678
        L_0x06b0:
            boolean r136 = DEBUG
            r19 = r136
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x06dc
        L_0x06bc:
            r7.performResume()
            r140 = r7
            r141 = 0
            r0 = r140
            r1 = r141
            r6.dispatchOnFragmentResumed(r0, r1)
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mSavedFragmentState = r0
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mSavedViewState = r0
            goto L_0x00b9
        L_0x06dc:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "moveto RESUMED: "
            r137 = r37
            r0 = r29
            r1 = r137
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r138 = r27
            r139 = r29
            int r19 = android.util.Log.v(r138, r139)
            goto L_0x06bc
        L_0x0704:
            int r0 = r7.mState
            r19 = r0
            switch(r19) {
                case 1: goto L_0x070d;
                case 2: goto L_0x0715;
                case 3: goto L_0x071c;
                case 4: goto L_0x0723;
                case 5: goto L_0x072a;
                default: goto L_0x070b;
            }
        L_0x070b:
            goto L_0x0054
        L_0x070d:
            r20 = 1
            r0 = r20
            if (r14 < r0) goto L_0x0953
            goto L_0x0054
        L_0x0715:
            r20 = 2
            r0 = r20
            if (r14 < r0) goto L_0x07f0
            goto L_0x070d
        L_0x071c:
            r20 = 3
            r0 = r20
            if (r14 < r0) goto L_0x07b7
            goto L_0x0715
        L_0x0723:
            r20 = 4
            r0 = r20
            if (r14 < r0) goto L_0x0774
            goto L_0x071c
        L_0x072a:
            r20 = 5
            r0 = r20
            if (r14 < r0) goto L_0x0731
            goto L_0x0723
        L_0x0731:
            boolean r142 = DEBUG
            r19 = r142
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x074c
        L_0x073d:
            r7.performPause()
            r146 = r7
            r147 = 0
            r0 = r146
            r1 = r147
            r6.dispatchOnFragmentPaused(r0, r1)
            goto L_0x0723
        L_0x074c:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "movefrom RESUMED: "
            r143 = r37
            r0 = r29
            r1 = r143
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r144 = r27
            r145 = r29
            int r19 = android.util.Log.v(r144, r145)
            goto L_0x073d
        L_0x0774:
            boolean r148 = DEBUG
            r19 = r148
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x078f
        L_0x0780:
            r7.performStop()
            r152 = r7
            r153 = 0
            r0 = r152
            r1 = r153
            r6.dispatchOnFragmentStopped(r0, r1)
            goto L_0x071c
        L_0x078f:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "movefrom STARTED: "
            r149 = r37
            r0 = r29
            r1 = r149
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r150 = r27
            r151 = r29
            int r19 = android.util.Log.v(r150, r151)
            goto L_0x0780
        L_0x07b7:
            boolean r154 = DEBUG
            r19 = r154
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x07c8
        L_0x07c3:
            r7.performReallyStop()
            goto L_0x0715
        L_0x07c8:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "movefrom STOPPED: "
            r155 = r37
            r0 = r29
            r1 = r155
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r156 = r27
            r157 = r29
            int r19 = android.util.Log.v(r156, r157)
            goto L_0x07c3
        L_0x07f0:
            boolean r158 = DEBUG
            r19 = r158
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x083c
        L_0x07fc:
            android.view.View r0 = r7.mView
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0864
        L_0x0808:
            r7.performDestroyView()
            r165 = r7
            r166 = 0
            r0 = r165
            r1 = r166
            r6.dispatchOnFragmentViewDestroyed(r0, r1)
            android.view.View r0 = r7.mView
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0894
        L_0x0822:
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mContainer = r0
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mView = r0
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mInnerView = r0
            goto L_0x070d
        L_0x083c:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "movefrom ACTIVITY_CREATED: "
            r159 = r37
            r0 = r29
            r1 = r159
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r160 = r27
            r161 = r29
            int r19 = android.util.Log.v(r160, r161)
            goto L_0x07fc
        L_0x0864:
            android.support.v4.app.FragmentHostCallback r0 = r6.mHost
            r27 = r0
            r162 = r7
            r0 = r27
            r1 = r162
            boolean r163 = r0.onShouldSaveFragmentState(r1)
            r19 = r163
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x087d
            goto L_0x0808
        L_0x087d:
            android.util.SparseArray<android.os.Parcelable> r0 = r7.mSavedViewState
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 == r1) goto L_0x088b
            goto L_0x0808
        L_0x088b:
            r164 = r7
            r0 = r164
            r6.saveFragmentViewState(r0)
            goto L_0x0808
        L_0x0894:
            android.view.ViewGroup r0 = r7.mContainer
            r27 = r0
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x08a1
            goto L_0x0822
        L_0x08a1:
            r27 = 0
            r93 = 0
            int r0 = r6.mCurState
            r19 = r0
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 > r1) goto L_0x08d2
        L_0x08b1:
            r169 = 0
            r0 = r169
            r7.mPostponedAlpha = r0
            r28 = 0
            r0 = r93
            r1 = r28
            if (r0 != r1) goto L_0x090c
        L_0x08bf:
            android.view.ViewGroup r0 = r7.mContainer
            r27 = r0
            android.view.View r0 = r7.mView
            r29 = r0
            r180 = r29
            r0 = r27
            r1 = r180
            r0.removeView(r1)
            goto L_0x0822
        L_0x08d2:
            boolean r0 = r6.mDestroyed
            r167 = r0
            r19 = r167
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x08e1
            goto L_0x08b1
        L_0x08e1:
            android.view.View r0 = r7.mView
            r27 = r0
            int r19 = r27.getVisibility()
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x08f2
            goto L_0x08b1
        L_0x08f2:
            float r0 = r7.mPostponedAlpha
            r168 = r0
            r169 = 0
            int r170 = (r168 > r169 ? 1 : (r168 == r169 ? 0 : -1))
            if (r170 < 0) goto L_0x090b
            r171 = r7
            r172 = 0
            r0 = r171
            r1 = r172
            android.view.animation.Animation r27 = r6.loadAnimation(r0, r9, r1, r10)
            r93 = r27
            goto L_0x08b1
        L_0x090b:
            goto L_0x08b1
        L_0x090c:
            r99 = r7
            android.view.View r0 = r7.mView
            r29 = r0
            r173 = r29
            r0 = r173
            r7.setAnimatingAway(r0)
            r7.setStateAfterAnimating(r14)
            android.view.View r0 = r7.mView
            r27 = r0
            r101 = r27
            r27 = r93
            android.support.v4.app.FragmentManagerImpl$2 r29 = new android.support.v4.app.FragmentManagerImpl$2
            r174 = r6
            r175 = r101
            r176 = r93
            r177 = r7
            r0 = r29
            r1 = r174
            r2 = r175
            r3 = r176
            r4 = r177
            r0.<init>(r1, r2, r3, r4)
            r178 = r29
            r0 = r27
            r1 = r178
            r0.setAnimationListener(r1)
            android.view.View r0 = r7.mView
            r27 = r0
            r179 = r93
            r0 = r27
            r1 = r179
            r0.startAnimation(r1)
            goto L_0x08bf
        L_0x0953:
            boolean r0 = r6.mDestroyed
            r181 = r0
            r19 = r181
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x09a5
        L_0x0961:
            android.view.View r27 = r7.getAnimatingAway()
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x09c7
            boolean r183 = DEBUG
            r19 = r183
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x09cd
        L_0x0979:
            boolean r0 = r7.mRetaining
            r187 = r0
            r19 = r187
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x09f5
            r20 = 0
            r0 = r20
            r7.mState = r0
        L_0x098d:
            r7.performDetach()
            r190 = r7
            r191 = 0
            r0 = r190
            r1 = r191
            r6.dispatchOnFragmentDetached(r0, r1)
            r20 = 0
            r0 = r17
            r1 = r20
            if (r0 == r1) goto L_0x0a04
            goto L_0x0054
        L_0x09a5:
            android.view.View r27 = r7.getAnimatingAway()
            r28 = 0
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x09b2
            goto L_0x0961
        L_0x09b2:
            android.view.View r27 = r7.getAnimatingAway()
            r93 = r27
            r29 = 0
            r28 = 0
            r182 = r28
            r0 = r182
            r7.setAnimatingAway(r0)
            r93.clearAnimation()
            goto L_0x0961
        L_0x09c7:
            r7.setStateAfterAnimating(r14)
            r14 = 1
            goto L_0x0054
        L_0x09cd:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "movefrom CREATED: "
            r184 = r37
            r0 = r29
            r1 = r184
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r29 = r29.toString()
            r185 = r27
            r186 = r29
            int r19 = android.util.Log.v(r185, r186)
            goto L_0x0979
        L_0x09f5:
            r7.performDestroy()
            r188 = r7
            r189 = 0
            r0 = r188
            r1 = r189
            r6.dispatchOnFragmentDestroyed(r0, r1)
            goto L_0x098d
        L_0x0a04:
            boolean r0 = r7.mRetaining
            r192 = r0
            r19 = r192
            r20 = 0
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x0a2c
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mHost = r0
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mParentFragment = r0
            r29 = 0
            r28 = 0
            r0 = r28
            r7.mFragmentManager = r0
            goto L_0x0054
        L_0x0a2c:
            r193 = r7
            r0 = r193
            r6.makeInactive(r0)
            goto L_0x0054
        L_0x0a35:
            java.lang.String r27 = "FragmentManager"
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r37 = "moveToState: Fragment state for "
            r194 = r37
            r0 = r29
            r1 = r194
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r7)
            java.lang.String r37 = " not updated inline; "
            r195 = r37
            r0 = r29
            r1 = r195
            java.lang.StringBuilder r29 = r0.append(r1)
            java.lang.String r37 = "expected state "
            r196 = r37
            r0 = r29
            r1 = r196
            java.lang.StringBuilder r29 = r0.append(r1)
            r0 = r29
            java.lang.StringBuilder r29 = r0.append(r14)
            java.lang.String r37 = " found "
            r197 = r37
            r0 = r29
            r1 = r197
            java.lang.StringBuilder r29 = r0.append(r1)
            int r0 = r7.mState
            r31 = r0
            r0 = r29
            r1 = r31
            java.lang.StringBuilder r29 = r0.append(r1)
            java.lang.String r29 = r29.toString()
            r198 = r27
            r199 = r29
            int r19 = android.util.Log.w(r198, r199)
            r7.mState = r14
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    /* access modifiers changed from: 0000 */
    public void moveToState(Fragment fragment) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        moveToState(f, this.mCurState, 0, 0, false);
    }

    /* access modifiers changed from: 0000 */
    public void completeShowHideFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        if (fragment2.mView != null) {
            Animation loadAnimation = loadAnimation(fragment2, fragment2.getNextTransition(), !fragment2.mHidden, fragment2.getNextTransitionStyle());
            Animation anim = loadAnimation;
            if (loadAnimation != null) {
                setHWLayerAnimListenerIfAlpha(fragment2.mView, anim);
                fragment2.mView.startAnimation(anim);
                setHWLayerAnimListenerIfAlpha(fragment2.mView, anim);
                anim.start();
            }
            fragment2.mView.setVisibility((fragment2.mHidden && !fragment2.isHideReplaced()) ? 8 : 0);
            if (fragment2.isHideReplaced()) {
                fragment2.setHideReplaced(false);
            }
        }
        if (fragment2.mAdded && fragment2.mHasMenu && fragment2.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment2.mHiddenChanged = false;
        fragment2.onHiddenChanged(fragment2.mHidden);
    }

    /* access modifiers changed from: 0000 */
    public void moveFragmentToExpectedState(Fragment fragment) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        if (f != null) {
            int nextState = this.mCurState;
            if (f.mRemoving) {
                if (!f.isInBackStack()) {
                    nextState = Math.min(nextState, 0);
                } else {
                    nextState = Math.min(nextState, 1);
                }
            }
            moveToState(f, nextState, f.getNextTransition(), f.getNextTransitionStyle(), false);
            if (f.mView != null) {
                Fragment findFragmentUnder = findFragmentUnder(f);
                Fragment underFragment = findFragmentUnder;
                if (findFragmentUnder != null) {
                    View underView = underFragment.mView;
                    ViewGroup viewGroup = f.mContainer;
                    ViewGroup container = viewGroup;
                    int underIndex = viewGroup.indexOfChild(underView);
                    int indexOfChild = container.indexOfChild(f.mView);
                    int viewIndex = indexOfChild;
                    if (indexOfChild < underIndex) {
                        container.removeViewAt(viewIndex);
                        container.addView(f.mView, underIndex);
                    }
                }
                if (f.mIsNewlyAdded && f.mContainer != null) {
                    if (VERSION.SDK_INT < 11) {
                        f.mView.setVisibility(0);
                    } else if (f.mPostponedAlpha > 0.0f) {
                        f.mView.setAlpha(f.mPostponedAlpha);
                    }
                    f.mPostponedAlpha = 0.0f;
                    f.mIsNewlyAdded = false;
                    Animation loadAnimation = loadAnimation(f, f.getNextTransition(), true, f.getNextTransitionStyle());
                    Animation anim = loadAnimation;
                    if (loadAnimation != null) {
                        setHWLayerAnimListenerIfAlpha(f.mView, anim);
                        f.mView.startAnimation(anim);
                    }
                }
            }
            if (f.mHiddenChanged) {
                completeShowHideFragment(f);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void moveToState(int i, boolean z) {
        int newState = i;
        int i2 = newState;
        boolean always = z;
        if (this.mHost == null && newState != 0) {
            throw new IllegalStateException("No activity");
        } else if (always || newState != this.mCurState) {
            this.mCurState = newState;
            if (this.mActive != null) {
                boolean loadersRunning = false;
                if (this.mAdded != null) {
                    int numAdded = this.mAdded.size();
                    for (int i3 = 0; i3 < numAdded; i3++) {
                        Fragment f = (Fragment) this.mAdded.get(i3);
                        moveFragmentToExpectedState(f);
                        if (f.mLoaderManager != null) {
                            loadersRunning |= f.mLoaderManager.hasRunningLoaders();
                        }
                    }
                }
                int numActive = this.mActive.size();
                for (int i4 = 0; i4 < numActive; i4++) {
                    Fragment fragment = (Fragment) this.mActive.get(i4);
                    Fragment f2 = fragment;
                    if (fragment != null && ((f2.mRemoving || f2.mDetached) && !f2.mIsNewlyAdded)) {
                        moveFragmentToExpectedState(f2);
                        if (f2.mLoaderManager != null) {
                            loadersRunning |= f2.mLoaderManager.hasRunningLoaders();
                        }
                    }
                }
                if (!loadersRunning) {
                    startPendingDeferredFragments();
                }
                if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) {
                    this.mHost.onSupportInvalidateOptionsMenu();
                    this.mNeedMenuInvalidate = false;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void startPendingDeferredFragments() {
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragment = (Fragment) this.mActive.get(i);
                Fragment f = fragment;
                if (fragment != null) {
                    performPendingDeferredStart(f);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void makeActive(Fragment fragment) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        if (f.mIndex < 0) {
            if (this.mAvailIndices != null && this.mAvailIndices.size() > 0) {
                f.setIndex(((Integer) this.mAvailIndices.remove(this.mAvailIndices.size() - 1)).intValue(), this.mParent);
                Object obj = this.mActive.set(f.mIndex, f);
            } else {
                if (this.mActive == null) {
                    this.mActive = new ArrayList<>();
                }
                f.setIndex(this.mActive.size(), this.mParent);
                boolean add = this.mActive.add(f);
            }
            if (DEBUG) {
                int v = Log.v(TAG, "Allocated fragment index " + f);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void makeInactive(Fragment fragment) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        if (f.mIndex >= 0) {
            if (DEBUG) {
                int v = Log.v(TAG, "Freeing fragment index " + f);
            }
            Object obj = this.mActive.set(f.mIndex, null);
            if (this.mAvailIndices == null) {
                this.mAvailIndices = new ArrayList<>();
            }
            boolean add = this.mAvailIndices.add(Integer.valueOf(f.mIndex));
            this.mHost.inactivateFragment(f.mWho);
            f.initState();
        }
    }

    public void addFragment(Fragment fragment, boolean z) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        boolean moveToStateNow = z;
        if (this.mAdded == null) {
            this.mAdded = new ArrayList<>();
        }
        if (DEBUG) {
            int v = Log.v(TAG, "add: " + fragment2);
        }
        makeActive(fragment2);
        if (!fragment2.mDetached) {
            if (!this.mAdded.contains(fragment2)) {
                boolean add = this.mAdded.add(fragment2);
                fragment2.mAdded = true;
                fragment2.mRemoving = false;
                if (fragment2.mView == null) {
                    fragment2.mHiddenChanged = false;
                }
                if (fragment2.mHasMenu && fragment2.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                if (moveToStateNow) {
                    moveToState(fragment2);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Fragment already added: " + fragment2);
        }
    }

    public void removeFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        if (DEBUG) {
            int v = Log.v(TAG, "remove: " + fragment2 + " nesting=" + fragment2.mBackStackNesting);
        }
        boolean inactive = !fragment2.isInBackStack();
        if (!fragment2.mDetached || inactive) {
            if (this.mAdded != null) {
                boolean remove = this.mAdded.remove(fragment2);
            }
            if (fragment2.mHasMenu && fragment2.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment2.mAdded = false;
            fragment2.mRemoving = true;
        }
    }

    public void hideFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        if (DEBUG) {
            int v = Log.v(TAG, "hide: " + fragment2);
        }
        if (!fragment2.mHidden) {
            fragment2.mHidden = true;
            fragment2.mHiddenChanged = !fragment2.mHiddenChanged;
        }
    }

    public void showFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        if (DEBUG) {
            int v = Log.v(TAG, "show: " + fragment2);
        }
        if (fragment2.mHidden) {
            fragment2.mHidden = false;
            fragment2.mHiddenChanged = !fragment2.mHiddenChanged;
        }
    }

    public void detachFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        if (DEBUG) {
            int v = Log.v(TAG, "detach: " + fragment2);
        }
        if (!fragment2.mDetached) {
            fragment2.mDetached = true;
            if (fragment2.mAdded) {
                if (this.mAdded != null) {
                    if (DEBUG) {
                        int v2 = Log.v(TAG, "remove from detach: " + fragment2);
                    }
                    boolean remove = this.mAdded.remove(fragment2);
                }
                if (fragment2.mHasMenu && fragment2.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment2.mAdded = false;
            }
        }
    }

    public void attachFragment(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        if (DEBUG) {
            int v = Log.v(TAG, "attach: " + fragment2);
        }
        if (fragment2.mDetached) {
            fragment2.mDetached = false;
            if (!fragment2.mAdded) {
                if (this.mAdded == null) {
                    this.mAdded = new ArrayList<>();
                }
                if (!this.mAdded.contains(fragment2)) {
                    if (DEBUG) {
                        int v2 = Log.v(TAG, "add from attach: " + fragment2);
                    }
                    boolean add = this.mAdded.add(fragment2);
                    fragment2.mAdded = true;
                    if (fragment2.mHasMenu && fragment2.mMenuVisible) {
                        this.mNeedMenuInvalidate = true;
                        return;
                    }
                    return;
                }
                throw new IllegalStateException("Fragment already added: " + fragment2);
            }
        }
    }

    public Fragment findFragmentById(int i) {
        int id = i;
        int i2 = id;
        if (this.mAdded != null) {
            for (int i3 = this.mAdded.size() - 1; i3 >= 0; i3--) {
                Fragment fragment = (Fragment) this.mAdded.get(i3);
                Fragment f = fragment;
                if (fragment != null && f.mFragmentId == id) {
                    return f;
                }
            }
        }
        if (this.mActive != null) {
            for (int i4 = this.mActive.size() - 1; i4 >= 0; i4--) {
                Fragment fragment2 = (Fragment) this.mActive.get(i4);
                Fragment f2 = fragment2;
                if (fragment2 != null && f2.mFragmentId == id) {
                    return f2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String str) {
        String tag = str;
        String str2 = tag;
        if (!(this.mAdded == null || tag == null)) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null && tag.equals(f.mTag)) {
                    return f;
                }
            }
        }
        if (!(this.mActive == null || tag == null)) {
            for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
                Fragment fragment2 = (Fragment) this.mActive.get(i2);
                Fragment f2 = fragment2;
                if (fragment2 != null && tag.equals(f2.mTag)) {
                    return f2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String str) {
        String who = str;
        String str2 = who;
        if (!(this.mActive == null || who == null)) {
            for (int i = this.mActive.size() - 1; i >= 0; i--) {
                Fragment fragment = (Fragment) this.mActive.get(i);
                Fragment f = fragment;
                if (fragment != null) {
                    Fragment findFragmentByWho = f.findFragmentByWho(who);
                    Fragment f2 = findFragmentByWho;
                    if (findFragmentByWho != null) {
                        return findFragmentByWho;
                    }
                }
            }
        }
        return null;
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    /* JADX INFO: finally extract failed */
    public void enqueueAction(OpGenerator opGenerator, boolean z) {
        OpGenerator action = opGenerator;
        OpGenerator opGenerator2 = action;
        if (!z) {
            checkStateLoss();
        }
        synchronized (this) {
            try {
                if (!this.mDestroyed) {
                    if (this.mHost != null) {
                        if (this.mPendingActions == null) {
                            this.mPendingActions = new ArrayList<>();
                        }
                        boolean add = this.mPendingActions.add(action);
                        scheduleCommit();
                    }
                }
                throw new IllegalStateException("Activity has been destroyed");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003e A[Catch:{ all -> 0x004e }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004a A[Catch:{ all -> 0x004e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void scheduleCommit() {
        /*
            r14 = this;
            r0 = r14
            r1 = r0
            r2 = r0
            monitor-enter(r2)
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$StartEnterTransitionListener> r2 = r0.mPostponedTransactions     // Catch:{ all -> 0x004e }
            r3 = 0
            if (r2 != r3) goto L_0x0031
        L_0x0009:
            r5 = 0
        L_0x000a:
            r7 = r5
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r2 = r0.mPendingActions     // Catch:{ all -> 0x004e }
            r3 = 0
            if (r2 != r3) goto L_0x003e
        L_0x0010:
            r5 = 0
        L_0x0011:
            r8 = r5
            r6 = 0
            if (r7 == r6) goto L_0x004a
        L_0x0015:
            android.support.v4.app.FragmentHostCallback r2 = r0.mHost     // Catch:{ all -> 0x004e }
            android.os.Handler r2 = r2.getHandler()     // Catch:{ all -> 0x004e }
            java.lang.Runnable r9 = r0.mExecCommit     // Catch:{ all -> 0x004e }
            r10 = r9
            r2.removeCallbacks(r10)     // Catch:{ all -> 0x004e }
            android.support.v4.app.FragmentHostCallback r2 = r0.mHost     // Catch:{ all -> 0x004e }
            android.os.Handler r2 = r2.getHandler()     // Catch:{ all -> 0x004e }
            java.lang.Runnable r9 = r0.mExecCommit     // Catch:{ all -> 0x004e }
            r11 = r9
            boolean r12 = r2.post(r11)     // Catch:{ all -> 0x004e }
        L_0x002e:
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
            return
        L_0x0031:
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$StartEnterTransitionListener> r2 = r0.mPostponedTransactions     // Catch:{ all -> 0x004e }
            boolean r4 = r2.isEmpty()     // Catch:{ all -> 0x004e }
            r5 = r4
            r6 = 0
            if (r5 == r6) goto L_0x003c
            goto L_0x0009
        L_0x003c:
            r5 = 1
            goto L_0x000a
        L_0x003e:
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r2 = r0.mPendingActions     // Catch:{ all -> 0x004e }
            int r5 = r2.size()     // Catch:{ all -> 0x004e }
            r6 = 1
            if (r5 == r6) goto L_0x0048
            goto L_0x0010
        L_0x0048:
            r5 = 1
            goto L_0x0011
        L_0x004a:
            r6 = 0
            if (r8 != r6) goto L_0x0015
            goto L_0x002e
        L_0x004e:
            r13 = move-exception
            r2 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.app.FragmentManagerImpl.scheduleCommit():void");
    }

    /* JADX INFO: finally extract failed */
    public int allocBackStackIndex(BackStackRecord backStackRecord) {
        BackStackRecord bse = backStackRecord;
        BackStackRecord backStackRecord2 = bse;
        synchronized (this) {
            try {
                if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                    int index = ((Integer) this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1)).intValue();
                    if (DEBUG) {
                        int v = Log.v(TAG, "Adding back stack index " + index + " with " + bse);
                    }
                    Object obj = this.mBackStackIndices.set(index, bse);
                    return index;
                }
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = new ArrayList<>();
                }
                int index2 = this.mBackStackIndices.size();
                if (DEBUG) {
                    int v2 = Log.v(TAG, "Setting back stack index " + index2 + " to " + bse);
                }
                boolean add = this.mBackStackIndices.add(bse);
                return index2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setBackStackIndex(int i, BackStackRecord backStackRecord) {
        int N;
        int index = i;
        BackStackRecord bse = backStackRecord;
        int i2 = index;
        BackStackRecord backStackRecord2 = bse;
        synchronized (this) {
            try {
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = new ArrayList<>();
                }
                this = this.mBackStackIndices.size();
                if (index >= N) {
                    while (N < index) {
                        boolean add = this.mBackStackIndices.add(null);
                        if (this.mAvailBackStackIndices == null) {
                            this.mAvailBackStackIndices = new ArrayList<>();
                        }
                        if (DEBUG) {
                            int v = Log.v(TAG, "Adding available back stack index " + N);
                        }
                        boolean add2 = this.mAvailBackStackIndices.add(Integer.valueOf(N));
                        N++;
                    }
                    if (DEBUG) {
                        int v2 = Log.v(TAG, "Adding back stack index " + index + " with " + bse);
                    }
                    boolean add3 = this.mBackStackIndices.add(bse);
                } else {
                    if (DEBUG) {
                        int v3 = Log.v(TAG, "Setting back stack index " + index + " to " + bse);
                    }
                    Object obj = this.mBackStackIndices.set(index, bse);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void freeBackStackIndex(int i) {
        int index = i;
        int i2 = index;
        synchronized (this) {
            try {
                Object obj = this.mBackStackIndices.set(index, null);
                if (this.mAvailBackStackIndices == null) {
                    this.mAvailBackStackIndices = new ArrayList<>();
                }
                if (DEBUG) {
                    int v = Log.v(TAG, "Freeing back stack index " + index);
                }
                boolean add = this.mAvailBackStackIndices.add(Integer.valueOf(index));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void ensureExecReady(boolean z) {
        boolean allowStateLoss = z;
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
            if (!allowStateLoss) {
                checkStateLoss();
            }
            if (this.mTmpRecords == null) {
                this.mTmpRecords = new ArrayList<>();
                this.mTmpIsPop = new ArrayList<>();
            }
            executePostponedTransaction(null, null);
        } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
    }

    public void execSingleAction(OpGenerator opGenerator, boolean z) {
        OpGenerator action = opGenerator;
        OpGenerator opGenerator2 = action;
        ensureExecReady(z);
        if (action.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    /* JADX INFO: finally extract failed */
    public boolean execPendingActions() {
        ensureExecReady(true);
        boolean didSomething = false;
        while (true) {
            if (!generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
                doPendingDeferredStart();
                return didSomething;
            }
            this.mExecutingActions = true;
            try {
                optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                didSomething = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        int numPostponed = this.mPostponedTransactions != null ? this.mPostponedTransactions.size() : 0;
        int i = 0;
        while (i < numPostponed) {
            StartEnterTransitionListener listener = (StartEnterTransitionListener) this.mPostponedTransactions.get(i);
            if (records != null && !StartEnterTransitionListener.access$000(listener)) {
                int indexOf = records.indexOf(StartEnterTransitionListener.access$100(listener));
                int index = indexOf;
                if (indexOf != -1 && ((Boolean) isRecordPop.get(index)).booleanValue()) {
                    listener.cancelTransaction();
                    i++;
                }
            }
            if (listener.isReady() || (records != null && StartEnterTransitionListener.access$100(listener).interactsWith(records, 0, records.size()))) {
                Object remove = this.mPostponedTransactions.remove(i);
                i--;
                numPostponed--;
                if (records != null && !StartEnterTransitionListener.access$000(listener)) {
                    int indexOf2 = records.indexOf(StartEnterTransitionListener.access$100(listener));
                    int i2 = indexOf2;
                    if (indexOf2 != -1 && ((Boolean) isRecordPop.get(indexOf2)).booleanValue()) {
                        listener.cancelTransaction();
                    }
                }
                listener.completeTransaction();
            }
            i++;
        }
    }

    private void optimizeAndExecuteOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        if (records == null || records.isEmpty()) {
            return;
        }
        if (isRecordPop != null && records.size() == isRecordPop.size()) {
            executePostponedTransaction(records, isRecordPop);
            int numRecords = records.size();
            int startIndex = 0;
            int recordNum = 0;
            while (recordNum < numRecords) {
                boolean z = ((BackStackRecord) records.get(recordNum)).mAllowOptimization;
                boolean z2 = z;
                if (!z) {
                    if (startIndex != recordNum) {
                        executeOpsTogether(records, isRecordPop, startIndex, recordNum);
                    }
                    int optimizeEnd = recordNum + 1;
                    if (((Boolean) isRecordPop.get(recordNum)).booleanValue()) {
                        while (optimizeEnd < numRecords && ((Boolean) isRecordPop.get(optimizeEnd)).booleanValue() && !((BackStackRecord) records.get(optimizeEnd)).mAllowOptimization) {
                            optimizeEnd++;
                        }
                    }
                    executeOpsTogether(records, isRecordPop, recordNum, optimizeEnd);
                    startIndex = optimizeEnd;
                    recordNum = optimizeEnd - 1;
                }
                recordNum++;
            }
            if (startIndex != numRecords) {
                executeOpsTogether(records, isRecordPop, startIndex, numRecords);
            }
            return;
        }
        throw new IllegalStateException("Internal error with the back stack records");
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        boolean z;
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        int startIndex = i;
        int endIndex = i2;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        int i3 = startIndex;
        int i4 = endIndex;
        boolean allowOptimization = ((BackStackRecord) records.get(startIndex)).mAllowOptimization;
        boolean addToBackStack = false;
        if (this.mTmpAddedFragments != null) {
            this.mTmpAddedFragments.clear();
        } else {
            this.mTmpAddedFragments = new ArrayList();
        }
        if (this.mAdded != null) {
            boolean addAll = this.mTmpAddedFragments.addAll(this.mAdded);
        }
        for (int recordNum = startIndex; recordNum < endIndex; recordNum++) {
            BackStackRecord record = (BackStackRecord) records.get(recordNum);
            boolean booleanValue = ((Boolean) isRecordPop.get(recordNum)).booleanValue();
            boolean isPop = booleanValue;
            if (booleanValue) {
                record.trackAddedFragmentsInPop(this.mTmpAddedFragments);
            } else {
                record.expandReplaceOps(this.mTmpAddedFragments);
            }
            record.bumpBackStackNesting(!isPop ? 1 : -1);
            if (!addToBackStack && !record.mAddToBackStack) {
                z = false;
            } else {
                z = true;
            }
            addToBackStack = z;
        }
        this.mTmpAddedFragments.clear();
        if (!allowOptimization) {
            FragmentTransition.startTransitions(this, records, isRecordPop, startIndex, endIndex, false);
        }
        executeOps(records, isRecordPop, startIndex, endIndex);
        int postponeIndex = endIndex;
        if (allowOptimization) {
            ArraySet arraySet = new ArraySet();
            addAddedFragments(arraySet);
            postponeIndex = postponePostponableTransactions(records, isRecordPop, startIndex, endIndex, arraySet);
            makeRemovedFragmentsInvisible(arraySet);
        }
        if (postponeIndex != startIndex && allowOptimization) {
            FragmentTransition.startTransitions(this, records, isRecordPop, startIndex, postponeIndex, true);
            moveToState(this.mCurState, true);
        }
        for (int recordNum2 = startIndex; recordNum2 < endIndex; recordNum2++) {
            BackStackRecord record2 = (BackStackRecord) records.get(recordNum2);
            boolean booleanValue2 = ((Boolean) isRecordPop.get(recordNum2)).booleanValue();
            boolean z2 = booleanValue2;
            if (booleanValue2 && record2.mIndex >= 0) {
                freeBackStackIndex(record2.mIndex);
                record2.mIndex = -1;
            }
        }
        if (addToBackStack) {
            reportBackStackChanged();
        }
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> arraySet) {
        ArraySet<Fragment> fragments = arraySet;
        ArraySet<Fragment> arraySet2 = fragments;
        int numAdded = fragments.size();
        for (int i = 0; i < numAdded; i++) {
            Fragment fragment = (Fragment) fragments.valueAt(i);
            Fragment fragment2 = fragment;
            if (!fragment.mAdded) {
                View view = fragment2.getView();
                if (VERSION.SDK_INT >= 11) {
                    fragment2.mPostponedAlpha = view.getAlpha();
                    view.setAlpha(0.0f);
                } else {
                    fragment2.getView().setVisibility(4);
                }
            }
        }
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, ArraySet<Fragment> arraySet) {
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        int startIndex = i;
        int endIndex = i2;
        ArraySet<Fragment> added = arraySet;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        int i3 = startIndex;
        int i4 = endIndex;
        ArraySet<Fragment> arraySet2 = added;
        int postponeIndex = endIndex;
        for (int i5 = endIndex - 1; i5 >= startIndex; i5--) {
            BackStackRecord record = (BackStackRecord) records.get(i5);
            boolean isPop = ((Boolean) isRecordPop.get(i5)).booleanValue();
            if (record.isPostponed() && !record.interactsWith(records, i5 + 1, endIndex)) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(record, isPop);
                StartEnterTransitionListener listener = startEnterTransitionListener;
                boolean add = this.mPostponedTransactions.add(listener);
                record.setOnStartPostponedListener(listener);
                if (!isPop) {
                    record.executePopOps();
                } else {
                    record.executeOps();
                }
                postponeIndex--;
                if (i5 != postponeIndex) {
                    Object remove = records.remove(i5);
                    records.add(postponeIndex, record);
                }
                addAddedFragments(added);
            }
        }
        return postponeIndex;
    }

    private void completeExecute(BackStackRecord backStackRecord, boolean z, boolean z2, boolean z3) {
        BackStackRecord record = backStackRecord;
        BackStackRecord backStackRecord2 = record;
        boolean isPop = z;
        boolean runTransitions = z2;
        boolean moveToState = z3;
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        boolean add = arrayList.add(record);
        boolean add2 = arrayList2.add(Boolean.valueOf(isPop));
        executeOps(arrayList, arrayList2, 0, 1);
        if (runTransitions) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, 0, 1, true);
        }
        if (moveToState) {
            moveToState(this.mCurState, true);
        }
        if (this.mActive != null) {
            int numActive = this.mActive.size();
            for (int i = 0; i < numActive; i++) {
                Fragment fragment = (Fragment) this.mActive.get(i);
                Fragment fragment2 = fragment;
                if (fragment != null && fragment2.mView != null && fragment2.mIsNewlyAdded && record.interactsWith(fragment2.mContainerId)) {
                    if (VERSION.SDK_INT >= 11 && fragment2.mPostponedAlpha > 0.0f) {
                        fragment2.mView.setAlpha(fragment2.mPostponedAlpha);
                    }
                    if (!moveToState) {
                        fragment2.mPostponedAlpha = -1.0f;
                        fragment2.mIsNewlyAdded = false;
                    } else {
                        fragment2.mPostponedAlpha = 0.0f;
                    }
                }
            }
        }
    }

    private Fragment findFragmentUnder(Fragment fragment) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        ViewGroup container = f.mContainer;
        View view = f.mView;
        if (container == null || view == null) {
            return null;
        }
        int indexOf = this.mAdded.indexOf(f);
        int i = indexOf;
        for (int i2 = indexOf - 1; i2 >= 0; i2--) {
            Fragment fragment3 = (Fragment) this.mAdded.get(i2);
            Fragment underFragment = fragment3;
            if (fragment3.mContainer == container && underFragment.mView != null) {
                return underFragment;
            }
        }
        return null;
    }

    private static void executeOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        int startIndex = i;
        int endIndex = i2;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        int i3 = startIndex;
        int i4 = endIndex;
        for (int i5 = startIndex; i5 < endIndex; i5++) {
            BackStackRecord record = (BackStackRecord) records.get(i5);
            boolean booleanValue = ((Boolean) isRecordPop.get(i5)).booleanValue();
            boolean z = booleanValue;
            if (!booleanValue) {
                record.executeOps();
            } else {
                record.executePopOps();
            }
        }
    }

    private void addAddedFragments(ArraySet<Fragment> arraySet) {
        ArraySet<Fragment> added = arraySet;
        ArraySet<Fragment> arraySet2 = added;
        if (this.mCurState >= 1) {
            int state = Math.min(this.mCurState, 4);
            int numAdded = this.mAdded != null ? this.mAdded.size() : 0;
            for (int i = 0; i < numAdded; i++) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment fragment2 = fragment;
                if (fragment.mState < state) {
                    moveToState(fragment2, state, fragment2.getNextAnim(), fragment2.getNextTransition(), false);
                    if (fragment2.mView != null && !fragment2.mHidden && fragment2.mIsNewlyAdded) {
                        boolean add = added.add(fragment2);
                    }
                }
            }
        }
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                ((StartEnterTransitionListener) this.mPostponedTransactions.remove(0)).completeTransaction();
            }
        }
    }

    private void endAnimatingAwayFragments() {
        int numFragments = this.mActive != null ? this.mActive.size() : 0;
        for (int i = 0; i < numFragments; i++) {
            Fragment fragment = (Fragment) this.mActive.get(i);
            Fragment fragment2 = fragment;
            if (!(fragment == null || fragment2.getAnimatingAway() == null)) {
                int stateAfterAnimating = fragment2.getStateAfterAnimating();
                int i2 = stateAfterAnimating;
                View animatingAway = fragment2.getAnimatingAway();
                fragment2.setAnimatingAway(null);
                Animation animation = animatingAway.getAnimation();
                Animation animation2 = animation;
                if (animation != null) {
                    animation2.cancel();
                }
                moveToState(fragment2, stateAfterAnimating, 0, 0, false);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        if (r13 > 0) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0040, code lost:
        r9 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0043, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005c, code lost:
        r9 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean generateOpsForPendingActions(java.util.ArrayList<android.support.p000v4.app.BackStackRecord> r22, java.util.ArrayList<java.lang.Boolean> r23) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            r3 = r23
            r4 = r1
            r5 = r2
            r6 = r3
            r7 = r1
            monitor-enter(r7)
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r7 = r1.mPendingActions     // Catch:{ all -> 0x0058 }
            r8 = 0
            if (r7 != r8) goto L_0x0014
        L_0x0010:
            r11 = r1
            monitor-exit(r11)     // Catch:{ all -> 0x0058 }
            r12 = 0
            return r12
        L_0x0014:
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r7 = r1.mPendingActions     // Catch:{ all -> 0x0058 }
            int r9 = r7.size()     // Catch:{ all -> 0x0058 }
            r10 = 0
            if (r9 == r10) goto L_0x0010
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r7 = r1.mPendingActions     // Catch:{ all -> 0x0058 }
            int r9 = r7.size()     // Catch:{ all -> 0x0058 }
            r13 = r9
            r14 = 0
        L_0x0025:
            if (r14 < r13) goto L_0x0044
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r7 = r1.mPendingActions     // Catch:{ all -> 0x0058 }
            r7.clear()     // Catch:{ all -> 0x0058 }
            android.support.v4.app.FragmentHostCallback r7 = r1.mHost     // Catch:{ all -> 0x0058 }
            android.os.Handler r7 = r7.getHandler()     // Catch:{ all -> 0x0058 }
            java.lang.Runnable r11 = r1.mExecCommit     // Catch:{ all -> 0x0058 }
            r18 = r11
            r0 = r18
            r7.removeCallbacks(r0)     // Catch:{ all -> 0x0058 }
            r7 = r1
            monitor-exit(r7)     // Catch:{ all -> 0x0058 }
            r10 = 0
            if (r13 > r10) goto L_0x005c
            r9 = 0
        L_0x0041:
            r20 = r9
            return r20
        L_0x0044:
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r7 = r1.mPendingActions     // Catch:{ all -> 0x0058 }
            java.lang.Object r7 = r7.get(r14)     // Catch:{ all -> 0x0058 }
            android.support.v4.app.FragmentManagerImpl$OpGenerator r7 = (android.support.p000v4.app.FragmentManagerImpl.OpGenerator) r7     // Catch:{ all -> 0x0058 }
            r15 = r2
            r16 = r3
            r0 = r16
            boolean r17 = r7.generateOps(r15, r0)     // Catch:{ all -> 0x0058 }
            int r14 = r14 + 1
            goto L_0x0025
        L_0x0058:
            r19 = move-exception
            r7 = r1
            monitor-exit(r7)     // Catch:{ all -> 0x0058 }
            throw r19
        L_0x005c:
            r9 = 1
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.app.FragmentManagerImpl.generateOpsForPendingActions(java.util.ArrayList, java.util.ArrayList):boolean");
    }

    /* access modifiers changed from: 0000 */
    public void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            boolean loadersRunning = false;
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragment = (Fragment) this.mActive.get(i);
                Fragment f = fragment;
                if (!(fragment == null || f.mLoaderManager == null)) {
                    loadersRunning |= f.mLoaderManager.hasRunningLoaders();
                }
            }
            if (!loadersRunning) {
                this.mHavePendingDeferredStart = false;
                startPendingDeferredFragments();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                ((OnBackStackChangedListener) this.mBackStackChangeListeners.get(i)).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void addBackStackState(BackStackRecord backStackRecord) {
        BackStackRecord state = backStackRecord;
        BackStackRecord backStackRecord2 = state;
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        boolean add = this.mBackStack.add(state);
        reportBackStackChanged();
    }

    /* access modifiers changed from: 0000 */
    public boolean popBackStackState(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, String str, int i, int i2) {
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        String name = str;
        int id = i;
        int flags = i2;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        String str2 = name;
        int i3 = id;
        int i4 = flags;
        if (this.mBackStack == null) {
            return false;
        }
        if (name == null && id < 0 && (flags & 1) == 0) {
            int size = this.mBackStack.size() - 1;
            int last = size;
            if (size < 0) {
                return false;
            }
            boolean add = records.add(this.mBackStack.remove(last));
            boolean add2 = isRecordPop.add(Boolean.valueOf(true));
        } else {
            int index = -1;
            if (name != null || id >= 0) {
                index = this.mBackStack.size() - 1;
                while (index >= 0) {
                    BackStackRecord bss = (BackStackRecord) this.mBackStack.get(index);
                    if ((name != null && name.equals(bss.getName())) || (id >= 0 && id == bss.mIndex)) {
                        break;
                    }
                    index--;
                }
                if (index < 0) {
                    return false;
                }
                if ((flags & 1) != 0) {
                    while (true) {
                        index--;
                        if (index >= 0) {
                            BackStackRecord bss2 = (BackStackRecord) this.mBackStack.get(index);
                            if ((name == null || !name.equals(bss2.getName())) && (id < 0 || id != bss2.mIndex)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            if (index == this.mBackStack.size() - 1) {
                return false;
            }
            for (int i5 = this.mBackStack.size() - 1; i5 > index; i5--) {
                boolean add3 = records.add(this.mBackStack.remove(i5));
                boolean add4 = isRecordPop.add(Boolean.valueOf(true));
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public FragmentManagerNonConfig retainNonConfig() {
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragment = (Fragment) this.mActive.get(i);
                Fragment f = fragment;
                if (fragment != null) {
                    if (f.mRetainInstance) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        boolean add = arrayList.add(f);
                        f.mRetaining = true;
                        f.mTargetIndex = f.mTarget == null ? -1 : f.mTarget.mIndex;
                        if (DEBUG) {
                            int v = Log.v(TAG, "retainNonConfig: keeping retained " + f);
                        }
                    }
                    boolean addedChild = false;
                    if (f.mChildFragmentManager != null) {
                        FragmentManagerNonConfig retainNonConfig = f.mChildFragmentManager.retainNonConfig();
                        FragmentManagerNonConfig child = retainNonConfig;
                        if (retainNonConfig != null) {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                                for (int j = 0; j < i; j++) {
                                    boolean add2 = arrayList2.add(null);
                                }
                            }
                            boolean add3 = arrayList2.add(child);
                            addedChild = true;
                        }
                    }
                    if (arrayList2 != null && !addedChild) {
                        boolean add4 = arrayList2.add(null);
                    }
                }
            }
        }
        if (arrayList == null && arrayList2 == null) {
            return null;
        }
        return new FragmentManagerNonConfig(arrayList, arrayList2);
    }

    /* access modifiers changed from: 0000 */
    public void saveFragmentViewState(Fragment fragment) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        if (f.mInnerView != null) {
            if (this.mStateArray != null) {
                this.mStateArray.clear();
            } else {
                this.mStateArray = new SparseArray<>();
            }
            f.mInnerView.saveHierarchyState(this.mStateArray);
            if (this.mStateArray.size() > 0) {
                f.mSavedViewState = this.mStateArray;
                this.mStateArray = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Bundle saveFragmentBasicState(Fragment fragment) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        Bundle result = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        f.performSaveInstanceState(this.mStateBundle);
        dispatchOnFragmentSaveInstanceState(f, this.mStateBundle, false);
        if (!this.mStateBundle.isEmpty()) {
            result = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (f.mView != null) {
            saveFragmentViewState(f);
        }
        if (f.mSavedViewState != null) {
            if (result == null) {
                result = new Bundle();
            }
            result.putSparseParcelableArray(VIEW_STATE_TAG, f.mSavedViewState);
        }
        if (!f.mUserVisibleHint) {
            if (result == null) {
                result = new Bundle();
            }
            result.putBoolean(USER_VISIBLE_HINT_TAG, f.mUserVisibleHint);
        }
        return result;
    }

    /* access modifiers changed from: 0000 */
    public Parcelable saveAllState() {
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        boolean execPendingActions = execPendingActions();
        if (HONEYCOMB) {
            this.mStateSaved = true;
        }
        if (this.mActive == null || this.mActive.size() <= 0) {
            return null;
        }
        int size = this.mActive.size();
        int N = size;
        FragmentState[] active = new FragmentState[size];
        boolean haveFragments = false;
        for (int i = 0; i < N; i++) {
            Fragment fragment = (Fragment) this.mActive.get(i);
            Fragment f = fragment;
            if (fragment != null) {
                if (f.mIndex < 0) {
                    IllegalStateException illegalStateException = new IllegalStateException("Failure saving state: active " + f + " has cleared index: " + f.mIndex);
                    throwException(illegalStateException);
                }
                haveFragments = true;
                FragmentState fs = new FragmentState(f);
                active[i] = fs;
                if (f.mState > 0 && fs.mSavedFragmentState == null) {
                    fs.mSavedFragmentState = saveFragmentBasicState(f);
                    if (f.mTarget != null) {
                        if (f.mTarget.mIndex < 0) {
                            IllegalStateException illegalStateException2 = new IllegalStateException("Failure saving state: " + f + " has target not in fragment manager: " + f.mTarget);
                            throwException(illegalStateException2);
                        }
                        if (fs.mSavedFragmentState == null) {
                            fs.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fs.mSavedFragmentState, TARGET_STATE_TAG, f.mTarget);
                        if (f.mTargetRequestCode != 0) {
                            fs.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, f.mTargetRequestCode);
                        }
                    }
                } else {
                    fs.mSavedFragmentState = f.mSavedFragmentState;
                }
                if (DEBUG) {
                    int v = Log.v(TAG, "Saved state of " + f + ": " + fs.mSavedFragmentState);
                }
            }
        }
        if (haveFragments) {
            int[] added = null;
            BackStackState[] backStack = null;
            if (this.mAdded != null) {
                int size2 = this.mAdded.size();
                int N2 = size2;
                if (size2 > 0) {
                    added = new int[N2];
                    for (int i2 = 0; i2 < N2; i2++) {
                        added[i2] = ((Fragment) this.mAdded.get(i2)).mIndex;
                        if (added[i2] < 0) {
                            IllegalStateException illegalStateException3 = new IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + added[i2]);
                            throwException(illegalStateException3);
                        }
                        if (DEBUG) {
                            int v2 = Log.v(TAG, "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2));
                        }
                    }
                }
            }
            if (this.mBackStack != null) {
                int size3 = this.mBackStack.size();
                int N3 = size3;
                if (size3 > 0) {
                    backStack = new BackStackState[N3];
                    for (int i3 = 0; i3 < N3; i3++) {
                        int i4 = i3;
                        BackStackState backStackState = new BackStackState((BackStackRecord) this.mBackStack.get(i3));
                        backStack[i4] = backStackState;
                        if (DEBUG) {
                            int v3 = Log.v(TAG, "saveAllState: adding back stack #" + i3 + ": " + this.mBackStack.get(i3));
                        }
                    }
                }
            }
            FragmentManagerState fragmentManagerState = new FragmentManagerState();
            FragmentManagerState fms = fragmentManagerState;
            fragmentManagerState.mActive = active;
            fms.mAdded = added;
            fms.mBackStack = backStack;
            return fms;
        }
        if (DEBUG) {
            int v4 = Log.v(TAG, "saveAllState: no fragments!");
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        Parcelable state = parcelable;
        FragmentManagerNonConfig nonConfig = fragmentManagerNonConfig;
        Parcelable parcelable2 = state;
        FragmentManagerNonConfig fragmentManagerNonConfig2 = nonConfig;
        if (state != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) state;
            FragmentManagerState fms = fragmentManagerState;
            if (fragmentManagerState.mActive != null) {
                List list = null;
                if (nonConfig != null) {
                    List fragments = nonConfig.getFragments();
                    list = nonConfig.getChildNonConfigs();
                    int count = fragments == null ? 0 : fragments.size();
                    for (int i = 0; i < count; i++) {
                        Fragment f = (Fragment) fragments.get(i);
                        if (DEBUG) {
                            int v = Log.v(TAG, "restoreAllState: re-attaching retained " + f);
                        }
                        FragmentState fragmentState = fms.mActive[f.mIndex];
                        FragmentState fs = fragmentState;
                        fragmentState.mInstance = f;
                        f.mSavedViewState = null;
                        f.mBackStackNesting = 0;
                        f.mInLayout = false;
                        f.mAdded = false;
                        f.mTarget = null;
                        if (fs.mSavedFragmentState != null) {
                            fs.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                            f.mSavedViewState = fs.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            f.mSavedFragmentState = fs.mSavedFragmentState;
                        }
                    }
                }
                ArrayList arrayList = new ArrayList(fms.mActive.length);
                this.mActive = arrayList;
                if (this.mAvailIndices != null) {
                    this.mAvailIndices.clear();
                }
                for (int i2 = 0; i2 < fms.mActive.length; i2++) {
                    FragmentState fragmentState2 = fms.mActive[i2];
                    FragmentState fs2 = fragmentState2;
                    if (fragmentState2 == null) {
                        boolean add = this.mActive.add(null);
                        if (this.mAvailIndices == null) {
                            this.mAvailIndices = new ArrayList();
                        }
                        if (DEBUG) {
                            int v2 = Log.v(TAG, "restoreAllState: avail #" + i2);
                        }
                        boolean add2 = this.mAvailIndices.add(Integer.valueOf(i2));
                    } else {
                        FragmentManagerNonConfig childNonConfig = null;
                        if (list != null && i2 < list.size()) {
                            childNonConfig = (FragmentManagerNonConfig) list.get(i2);
                        }
                        Fragment f2 = fs2.instantiate(this.mHost, this.mParent, childNonConfig);
                        if (DEBUG) {
                            int v3 = Log.v(TAG, "restoreAllState: active #" + i2 + ": " + f2);
                        }
                        boolean add3 = this.mActive.add(f2);
                        fs2.mInstance = null;
                    }
                }
                if (nonConfig != null) {
                    List fragments2 = nonConfig.getFragments();
                    List list2 = fragments2;
                    int count2 = fragments2 == null ? 0 : list2.size();
                    for (int i3 = 0; i3 < count2; i3++) {
                        Fragment fragment = (Fragment) list2.get(i3);
                        Fragment f3 = fragment;
                        if (fragment.mTargetIndex >= 0) {
                            if (f3.mTargetIndex >= this.mActive.size()) {
                                int w = Log.w(TAG, "Re-attaching retained fragment " + f3 + " target no longer exists: " + f3.mTargetIndex);
                                f3.mTarget = null;
                            } else {
                                f3.mTarget = (Fragment) this.mActive.get(f3.mTargetIndex);
                            }
                        }
                    }
                }
                if (fms.mAdded == null) {
                    this.mAdded = null;
                } else {
                    ArrayList arrayList2 = new ArrayList(fms.mAdded.length);
                    this.mAdded = arrayList2;
                    int i4 = 0;
                    while (i4 < fms.mAdded.length) {
                        Fragment fragment2 = (Fragment) this.mActive.get(fms.mAdded[i4]);
                        Fragment f4 = fragment2;
                        if (fragment2 == null) {
                            IllegalStateException illegalStateException = new IllegalStateException("No instantiated fragment for index #" + fms.mAdded[i4]);
                            throwException(illegalStateException);
                        }
                        f4.mAdded = true;
                        if (DEBUG) {
                            int v4 = Log.v(TAG, "restoreAllState: added #" + i4 + ": " + f4);
                        }
                        if (!this.mAdded.contains(f4)) {
                            boolean add4 = this.mAdded.add(f4);
                            i4++;
                        } else {
                            throw new IllegalStateException("Already added!");
                        }
                    }
                }
                if (fms.mBackStack == null) {
                    this.mBackStack = null;
                } else {
                    ArrayList arrayList3 = new ArrayList(fms.mBackStack.length);
                    this.mBackStack = arrayList3;
                    for (int i5 = 0; i5 < fms.mBackStack.length; i5++) {
                        BackStackRecord bse = fms.mBackStack[i5].instantiate(this);
                        if (DEBUG) {
                            int v5 = Log.v(TAG, "restoreAllState: back stack #" + i5 + " (index " + bse.mIndex + "): " + bse);
                            bse.dump("  ", new PrintWriter(new LogWriter(TAG)), false);
                        }
                        boolean add5 = this.mBackStack.add(bse);
                        if (bse.mIndex >= 0) {
                            setBackStackIndex(bse.mIndex, bse);
                        }
                    }
                }
            }
        }
    }

    public void attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        FragmentHostCallback host = fragmentHostCallback;
        FragmentContainer container = fragmentContainer;
        Fragment parent = fragment;
        FragmentHostCallback fragmentHostCallback2 = host;
        FragmentContainer fragmentContainer2 = container;
        Fragment fragment2 = parent;
        if (this.mHost == null) {
            this.mHost = host;
            this.mContainer = container;
            this.mParent = parent;
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    public void noteStateNotSaved() {
        this.mStateSaved = false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        moveToState(1, false);
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        moveToState(2, false);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        moveToState(4, false);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        moveToState(5, false);
    }

    public void dispatchPause() {
        moveToState(4, false);
    }

    public void dispatchStop() {
        this.mStateSaved = true;
        moveToState(3, false);
    }

    public void dispatchReallyStop() {
        moveToState(2, false);
    }

    public void dispatchDestroyView() {
        moveToState(1, false);
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        boolean execPendingActions = execPendingActions();
        moveToState(0, false);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        boolean isInMultiWindowMode = z;
        if (this.mAdded != null) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null) {
                    f.performMultiWindowModeChanged(isInMultiWindowMode);
                }
            }
        }
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        boolean isInPictureInPictureMode = z;
        if (this.mAdded != null) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null) {
                    f.performPictureInPictureModeChanged(isInPictureInPictureMode);
                }
            }
        }
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        Configuration configuration2 = newConfig;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null) {
                    f.performConfigurationChanged(newConfig);
                }
            }
        }
    }

    public void dispatchLowMemory() {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null) {
                    f.performLowMemory();
                }
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Menu menu2 = menu;
        MenuInflater inflater = menuInflater;
        Menu menu3 = menu2;
        MenuInflater menuInflater2 = inflater;
        boolean show = false;
        ArrayList<Fragment> newMenus = null;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null && f.performCreateOptionsMenu(menu2, inflater)) {
                    show = true;
                    if (newMenus == null) {
                        newMenus = new ArrayList<>();
                    }
                    boolean add = newMenus.add(f);
                }
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                Fragment f2 = (Fragment) this.mCreatedMenus.get(i2);
                if (newMenus == null || !newMenus.contains(f2)) {
                    f2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = newMenus;
        return show;
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        boolean show = false;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null && f.performPrepareOptionsMenu(menu2)) {
                    show = true;
                }
            }
        }
        return show;
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null && f.performOptionsItemSelected(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        MenuItem menuItem2 = item;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null && f.performContextItemSelected(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        Menu menu2 = menu;
        Menu menu3 = menu2;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment fragment = (Fragment) this.mAdded.get(i);
                Fragment f = fragment;
                if (fragment != null) {
                    f.performOptionsMenuClosed(menu2);
                }
            }
        }
    }

    public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        FragmentLifecycleCallbacks cb = fragmentLifecycleCallbacks;
        FragmentLifecycleCallbacks fragmentLifecycleCallbacks2 = cb;
        boolean recursive = z;
        if (this.mLifecycleCallbacks == null) {
            this.mLifecycleCallbacks = new CopyOnWriteArrayList<>();
        }
        boolean add = this.mLifecycleCallbacks.add(new Pair(cb, Boolean.valueOf(recursive)));
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        FragmentLifecycleCallbacks cb = fragmentLifecycleCallbacks;
        FragmentLifecycleCallbacks fragmentLifecycleCallbacks2 = cb;
        if (this.mLifecycleCallbacks != null) {
            CopyOnWriteArrayList<Pair<FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
            CopyOnWriteArrayList<Pair<FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList2 = copyOnWriteArrayList;
            synchronized (copyOnWriteArrayList) {
                int i = 0;
                try {
                    int N = this.mLifecycleCallbacks.size();
                    while (true) {
                        if (i < N) {
                            if (((Pair) this.mLifecycleCallbacks.get(i)).first == cb) {
                                Object remove = this.mLifecycleCallbacks.remove(i);
                                break;
                            }
                            i++;
                        } else {
                            break;
                        }
                    }
                } finally {
                    CopyOnWriteArrayList<Pair<FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList3 = copyOnWriteArrayList2;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentPreAttached(Fragment fragment, Context context, boolean z) {
        Fragment f = fragment;
        Context context2 = context;
        Fragment fragment2 = f;
        Context context3 = context2;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentPreAttached(f, context2, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentPreAttached(this, f, context2);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentAttached(Fragment fragment, Context context, boolean z) {
        Fragment f = fragment;
        Context context2 = context;
        Fragment fragment2 = f;
        Context context3 = context2;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentAttached(f, context2, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentAttached(this, f, context2);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentCreated(Fragment fragment, Bundle bundle, boolean z) {
        Fragment f = fragment;
        Bundle savedInstanceState = bundle;
        Fragment fragment2 = f;
        Bundle bundle2 = savedInstanceState;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentCreated(f, savedInstanceState, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentCreated(this, f, savedInstanceState);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentActivityCreated(Fragment fragment, Bundle bundle, boolean z) {
        Fragment f = fragment;
        Bundle savedInstanceState = bundle;
        Fragment fragment2 = f;
        Bundle bundle2 = savedInstanceState;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentActivityCreated(f, savedInstanceState, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentActivityCreated(this, f, savedInstanceState);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentViewCreated(Fragment fragment, View view, Bundle bundle, boolean z) {
        Fragment f = fragment;
        View v = view;
        Bundle savedInstanceState = bundle;
        Fragment fragment2 = f;
        View view2 = v;
        Bundle bundle2 = savedInstanceState;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentViewCreated(f, v, savedInstanceState, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentViewCreated(this, f, v, savedInstanceState);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentStarted(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentStarted(f, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentStarted(this, f);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentResumed(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentResumed(f, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentResumed(this, f);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentPaused(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentPaused(f, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentPaused(this, f);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentStopped(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentStopped(f, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentStopped(this, f);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentSaveInstanceState(Fragment fragment, Bundle bundle, boolean z) {
        Fragment f = fragment;
        Bundle outState = bundle;
        Fragment fragment2 = f;
        Bundle bundle2 = outState;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentSaveInstanceState(f, outState, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentSaveInstanceState(this, f, outState);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentViewDestroyed(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentViewDestroyed(f, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentViewDestroyed(this, f);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentDestroyed(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentDestroyed(f, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentDestroyed(this, f);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnFragmentDetached(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        boolean onlyRecursive = z;
        if (this.mParent != null) {
            FragmentManager fragmentManager = this.mParent.getFragmentManager();
            FragmentManager parentManager = fragmentManager;
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) parentManager).dispatchOnFragmentDetached(f, true);
            }
        }
        if (this.mLifecycleCallbacks != null) {
            Iterator it = this.mLifecycleCallbacks.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                Pair pair2 = pair;
                if (!onlyRecursive || ((Boolean) pair.second).booleanValue()) {
                    ((FragmentLifecycleCallbacks) pair2.first).onFragmentDetached(this, f);
                }
            }
        }
    }

    public static int reverseTransit(int i) {
        int transit = i;
        int i2 = transit;
        int rev = 0;
        switch (transit) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                rev = 8194;
                break;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                rev = FragmentTransaction.TRANSIT_FRAGMENT_FADE;
                break;
            case 8194:
                rev = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
                break;
        }
        return rev;
    }

    public static int transitToStyleIndex(int i, boolean z) {
        int i2;
        int transit = i;
        int i3 = transit;
        boolean enter = z;
        int animAttr = -1;
        switch (transit) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                animAttr = !enter ? 2 : 1;
                break;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                if (!enter) {
                    i2 = 6;
                } else {
                    i2 = 5;
                }
                animAttr = i2;
                break;
            case 8194:
                animAttr = !enter ? 4 : 3;
                break;
        }
        return animAttr;
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View parent = view;
        String name = str;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        View view2 = parent;
        String str2 = name;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        if (!"fragment".equals(name)) {
            return null;
        }
        String fname = attrs.getAttributeValue(null, "class");
        TypedArray a = context2.obtainStyledAttributes(attrs, FragmentTag.Fragment);
        if (fname == null) {
            fname = a.getString(0);
        }
        int id = a.getResourceId(1, -1);
        String tag = a.getString(2);
        a.recycle();
        if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), fname)) {
            return null;
        }
        int containerId = parent == null ? 0 : parent.getId();
        if (containerId == -1 && id == -1 && tag == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(attrs.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + fname);
            throw illegalArgumentException;
        }
        Fragment fragment = id == -1 ? null : findFragmentById(id);
        if (fragment == null && tag != null) {
            fragment = findFragmentByTag(tag);
        }
        if (fragment == null && containerId != -1) {
            fragment = findFragmentById(containerId);
        }
        if (DEBUG) {
            int v = Log.v(TAG, "onCreateView: id=0x" + Integer.toHexString(id) + " fname=" + fname + " existing=" + fragment);
        }
        if (fragment == null) {
            Fragment instantiate = Fragment.instantiate(context2, fname);
            fragment = instantiate;
            instantiate.mFromLayout = true;
            fragment.mFragmentId = id == 0 ? containerId : id;
            fragment.mContainerId = containerId;
            fragment.mTag = tag;
            fragment.mInLayout = true;
            fragment.mFragmentManager = this;
            fragment.mHost = this.mHost;
            fragment.onInflate(this.mHost.getContext(), attrs, fragment.mSavedFragmentState);
            addFragment(fragment, true);
        } else if (!fragment.mInLayout) {
            fragment.mInLayout = true;
            fragment.mHost = this.mHost;
            if (!fragment.mRetaining) {
                fragment.onInflate(this.mHost.getContext(), attrs, fragment.mSavedFragmentState);
            }
        } else {
            IllegalArgumentException illegalArgumentException2 = new IllegalArgumentException(attrs.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(id) + ", tag " + tag + ", or parent id 0x" + Integer.toHexString(containerId) + " with another fragment for " + fname);
            throw illegalArgumentException2;
        }
        if (this.mCurState < 1 && fragment.mFromLayout) {
            moveToState(fragment, 1, 0, 0, false);
        } else {
            moveToState(fragment);
        }
        if (fragment.mView != null) {
            if (id != 0) {
                fragment.mView.setId(id);
            }
            if (fragment.mView.getTag() == null) {
                fragment.mView.setTag(tag);
            }
            return fragment.mView;
        }
        IllegalStateException illegalStateException = new IllegalStateException("Fragment " + fname + " did not create a view.");
        throw illegalStateException;
    }

    /* access modifiers changed from: 0000 */
    public LayoutInflaterFactory getLayoutInflaterFactory() {
        return this;
    }
}
