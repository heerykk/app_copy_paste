package android.support.p000v4.app;

import android.os.Build.VERSION;
import android.support.p000v4.app.FragmentManager.BackStackEntry;
import android.support.p000v4.util.LogWriter;
import android.support.p000v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* renamed from: android.support.v4.app.BackStackRecord */
final class BackStackRecord extends FragmentTransaction implements BackStackEntry, OpGenerator {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SHOW = 5;
    static final boolean SUPPORTS_TRANSITIONS = (VERSION.SDK_INT >= 21);
    static final String TAG = "FragmentManager";
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack = true;
    boolean mAllowOptimization = false;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    int mIndex = -1;
    final FragmentManagerImpl mManager;
    String mName;
    ArrayList<C0091Op> mOps = new ArrayList<>();
    int mPopEnterAnim;
    int mPopExitAnim;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    int mTransition;
    int mTransitionStyle;

    /* renamed from: android.support.v4.app.BackStackRecord$Op */
    static final class C0091Op {
        int cmd;
        int enterAnim;
        int exitAnim;
        Fragment fragment;
        int popEnterAnim;
        int popExitAnim;

        C0091Op() {
        }
    }

    public BackStackRecord(FragmentManagerImpl fragmentManagerImpl) {
        FragmentManagerImpl manager = fragmentManagerImpl;
        FragmentManagerImpl fragmentManagerImpl2 = manager;
        this.mManager = manager;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        StringBuilder sb2 = sb;
        StringBuilder append = sb.append("BackStackEntry{");
        StringBuilder append2 = sb2.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            StringBuilder append3 = sb2.append(" #");
            StringBuilder append4 = sb2.append(this.mIndex);
        }
        if (this.mName != null) {
            StringBuilder append5 = sb2.append(" ");
            StringBuilder append6 = sb2.append(this.mName);
        }
        StringBuilder append7 = sb2.append("}");
        return sb2.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String prefix = str;
        PrintWriter writer = printWriter;
        String str2 = prefix;
        FileDescriptor fileDescriptor2 = fileDescriptor;
        PrintWriter printWriter2 = writer;
        String[] strArr2 = strArr;
        dump(prefix, writer, true);
    }

    public void dump(String str, PrintWriter printWriter, boolean z) {
        String cmdStr;
        String prefix = str;
        PrintWriter writer = printWriter;
        String str2 = prefix;
        PrintWriter printWriter2 = writer;
        boolean full = z;
        if (full) {
            writer.print(prefix);
            writer.print("mName=");
            writer.print(this.mName);
            writer.print(" mIndex=");
            writer.print(this.mIndex);
            writer.print(" mCommitted=");
            writer.println(this.mCommitted);
            if (this.mTransition != 0) {
                writer.print(prefix);
                writer.print("mTransition=#");
                writer.print(Integer.toHexString(this.mTransition));
                writer.print(" mTransitionStyle=#");
                writer.println(Integer.toHexString(this.mTransitionStyle));
            }
            if (!(this.mEnterAnim == 0 && this.mExitAnim == 0)) {
                writer.print(prefix);
                writer.print("mEnterAnim=#");
                writer.print(Integer.toHexString(this.mEnterAnim));
                writer.print(" mExitAnim=#");
                writer.println(Integer.toHexString(this.mExitAnim));
            }
            if (!(this.mPopEnterAnim == 0 && this.mPopExitAnim == 0)) {
                writer.print(prefix);
                writer.print("mPopEnterAnim=#");
                writer.print(Integer.toHexString(this.mPopEnterAnim));
                writer.print(" mPopExitAnim=#");
                writer.println(Integer.toHexString(this.mPopExitAnim));
            }
            if (!(this.mBreadCrumbTitleRes == 0 && this.mBreadCrumbTitleText == null)) {
                writer.print(prefix);
                writer.print("mBreadCrumbTitleRes=#");
                writer.print(Integer.toHexString(this.mBreadCrumbTitleRes));
                writer.print(" mBreadCrumbTitleText=");
                writer.println(this.mBreadCrumbTitleText);
            }
            if (!(this.mBreadCrumbShortTitleRes == 0 && this.mBreadCrumbShortTitleText == null)) {
                writer.print(prefix);
                writer.print("mBreadCrumbShortTitleRes=#");
                writer.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
                writer.print(" mBreadCrumbShortTitleText=");
                writer.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (!this.mOps.isEmpty()) {
            writer.print(prefix);
            writer.println("Operations:");
            String str3 = prefix + "    ";
            int numOps = this.mOps.size();
            for (int opNum = 0; opNum < numOps; opNum++) {
                C0091Op op = (C0091Op) this.mOps.get(opNum);
                C0091Op op2 = op;
                switch (op.cmd) {
                    case 0:
                        cmdStr = "NULL";
                        break;
                    case 1:
                        cmdStr = "ADD";
                        break;
                    case 2:
                        cmdStr = "REPLACE";
                        break;
                    case 3:
                        cmdStr = "REMOVE";
                        break;
                    case 4:
                        cmdStr = "HIDE";
                        break;
                    case 5:
                        cmdStr = "SHOW";
                        break;
                    case 6:
                        cmdStr = "DETACH";
                        break;
                    case 7:
                        cmdStr = "ATTACH";
                        break;
                    default:
                        cmdStr = "cmd=" + op2.cmd;
                        break;
                }
                writer.print(prefix);
                writer.print("  Op #");
                writer.print(opNum);
                writer.print(": ");
                writer.print(cmdStr);
                writer.print(" ");
                writer.println(op2.fragment);
                if (full) {
                    if (!(op2.enterAnim == 0 && op2.exitAnim == 0)) {
                        writer.print(prefix);
                        writer.print("enterAnim=#");
                        writer.print(Integer.toHexString(op2.enterAnim));
                        writer.print(" exitAnim=#");
                        writer.println(Integer.toHexString(op2.exitAnim));
                    }
                    if (op2.popEnterAnim != 0 || op2.popExitAnim != 0) {
                        writer.print(prefix);
                        writer.print("popEnterAnim=#");
                        writer.print(Integer.toHexString(op2.popEnterAnim));
                        writer.print(" popExitAnim=#");
                        writer.println(Integer.toHexString(op2.popExitAnim));
                    }
                }
            }
        }
    }

    public int getId() {
        return this.mIndex;
    }

    public int getBreadCrumbTitleRes() {
        return this.mBreadCrumbTitleRes;
    }

    public int getBreadCrumbShortTitleRes() {
        return this.mBreadCrumbShortTitleRes;
    }

    public CharSequence getBreadCrumbTitle() {
        if (this.mBreadCrumbTitleRes == 0) {
            return this.mBreadCrumbTitleText;
        }
        return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
    }

    public CharSequence getBreadCrumbShortTitle() {
        if (this.mBreadCrumbShortTitleRes == 0) {
            return this.mBreadCrumbShortTitleText;
        }
        return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
    }

    /* access modifiers changed from: 0000 */
    public void addOp(C0091Op op) {
        C0091Op op2 = op;
        C0091Op op3 = op2;
        boolean add = this.mOps.add(op2);
        op2.enterAnim = this.mEnterAnim;
        op2.exitAnim = this.mExitAnim;
        op2.popEnterAnim = this.mPopEnterAnim;
        op2.popExitAnim = this.mPopExitAnim;
    }

    public FragmentTransaction add(Fragment fragment, String str) {
        Fragment fragment2 = fragment;
        String tag = str;
        Fragment fragment3 = fragment2;
        String str2 = tag;
        doAddOp(0, fragment2, tag, 1);
        return this;
    }

    public FragmentTransaction add(int i, Fragment fragment) {
        int containerViewId = i;
        Fragment fragment2 = fragment;
        int i2 = containerViewId;
        Fragment fragment3 = fragment2;
        doAddOp(containerViewId, fragment2, null, 1);
        return this;
    }

    public FragmentTransaction add(int i, Fragment fragment, String str) {
        int containerViewId = i;
        Fragment fragment2 = fragment;
        String tag = str;
        int i2 = containerViewId;
        Fragment fragment3 = fragment2;
        String str2 = tag;
        doAddOp(containerViewId, fragment2, tag, 1);
        return this;
    }

    private void doAddOp(int i, Fragment fragment, String str, int i2) {
        int containerViewId = i;
        Fragment fragment2 = fragment;
        String tag = str;
        int opcmd = i2;
        int i3 = containerViewId;
        Fragment fragment3 = fragment2;
        String str2 = tag;
        int i4 = opcmd;
        Class cls = fragment2.getClass();
        Class fragmentClass = cls;
        int modifiers = cls.getModifiers();
        if (!fragmentClass.isAnonymousClass() && Modifier.isPublic(modifiers) && (!fragmentClass.isMemberClass() || Modifier.isStatic(modifiers))) {
            fragment2.mFragmentManager = this.mManager;
            if (tag != null) {
                if (fragment2.mTag != null && !tag.equals(fragment2.mTag)) {
                    throw new IllegalStateException("Can't change tag of fragment " + fragment2 + ": was " + fragment2.mTag + " now " + tag);
                }
                fragment2.mTag = tag;
            }
            if (containerViewId != 0) {
                if (containerViewId == -1) {
                    throw new IllegalArgumentException("Can't add fragment " + fragment2 + " with tag " + tag + " to container view with no id");
                } else if (fragment2.mFragmentId == 0 || fragment2.mFragmentId == containerViewId) {
                    fragment2.mFragmentId = containerViewId;
                    fragment2.mContainerId = containerViewId;
                } else {
                    throw new IllegalStateException("Can't change container ID of fragment " + fragment2 + ": was " + fragment2.mFragmentId + " now " + containerViewId);
                }
            }
            C0091Op op = new C0091Op();
            C0091Op op2 = op;
            op.cmd = opcmd;
            op2.fragment = fragment2;
            addOp(op2);
            return;
        }
        throw new IllegalStateException("Fragment " + fragmentClass.getCanonicalName() + " must be a public static class to be  properly recreated from" + " instance state.");
    }

    public FragmentTransaction replace(int i, Fragment fragment) {
        int containerViewId = i;
        Fragment fragment2 = fragment;
        int i2 = containerViewId;
        Fragment fragment3 = fragment2;
        return replace(containerViewId, fragment2, null);
    }

    public FragmentTransaction replace(int i, Fragment fragment, String str) {
        int containerViewId = i;
        Fragment fragment2 = fragment;
        String tag = str;
        int i2 = containerViewId;
        Fragment fragment3 = fragment2;
        String str2 = tag;
        if (containerViewId != 0) {
            doAddOp(containerViewId, fragment2, tag, 2);
            return this;
        }
        throw new IllegalArgumentException("Must use non-zero containerViewId");
    }

    public FragmentTransaction remove(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        C0091Op op = new C0091Op();
        C0091Op op2 = op;
        op.cmd = 3;
        op2.fragment = fragment2;
        addOp(op2);
        return this;
    }

    public FragmentTransaction hide(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        C0091Op op = new C0091Op();
        C0091Op op2 = op;
        op.cmd = 4;
        op2.fragment = fragment2;
        addOp(op2);
        return this;
    }

    public FragmentTransaction show(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        C0091Op op = new C0091Op();
        C0091Op op2 = op;
        op.cmd = 5;
        op2.fragment = fragment2;
        addOp(op2);
        return this;
    }

    public FragmentTransaction detach(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        C0091Op op = new C0091Op();
        C0091Op op2 = op;
        op.cmd = 6;
        op2.fragment = fragment2;
        addOp(op2);
        return this;
    }

    public FragmentTransaction attach(Fragment fragment) {
        Fragment fragment2 = fragment;
        Fragment fragment3 = fragment2;
        C0091Op op = new C0091Op();
        C0091Op op2 = op;
        op.cmd = 7;
        op2.fragment = fragment2;
        addOp(op2);
        return this;
    }

    public FragmentTransaction setCustomAnimations(int i, int i2) {
        int enter = i;
        int exit = i2;
        int i3 = enter;
        int i4 = exit;
        return setCustomAnimations(enter, exit, 0, 0);
    }

    public FragmentTransaction setCustomAnimations(int i, int i2, int i3, int i4) {
        int enter = i;
        int exit = i2;
        int popEnter = i3;
        int popExit = i4;
        int i5 = enter;
        int i6 = exit;
        int i7 = popEnter;
        int i8 = popExit;
        this.mEnterAnim = enter;
        this.mExitAnim = exit;
        this.mPopEnterAnim = popEnter;
        this.mPopExitAnim = popExit;
        return this;
    }

    public FragmentTransaction setTransition(int i) {
        int transition = i;
        int i2 = transition;
        this.mTransition = transition;
        return this;
    }

    public FragmentTransaction addSharedElement(View view, String str) {
        View sharedElement = view;
        String name = str;
        View view2 = sharedElement;
        String str2 = name;
        if (SUPPORTS_TRANSITIONS) {
            String transitionName = ViewCompat.getTransitionName(sharedElement);
            String transitionName2 = transitionName;
            if (transitionName != null) {
                if (this.mSharedElementSourceNames == null) {
                    this.mSharedElementSourceNames = new ArrayList();
                    this.mSharedElementTargetNames = new ArrayList();
                } else if (this.mSharedElementTargetNames.contains(name)) {
                    throw new IllegalArgumentException("A shared element with the target name '" + name + "' has already been added to the transaction.");
                } else if (this.mSharedElementSourceNames.contains(transitionName2)) {
                    throw new IllegalArgumentException("A shared element with the source name '" + transitionName2 + " has already been added to the transaction.");
                }
                boolean add = this.mSharedElementSourceNames.add(transitionName2);
                boolean add2 = this.mSharedElementTargetNames.add(name);
            } else {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
        }
        return this;
    }

    public FragmentTransaction setTransitionStyle(int i) {
        int styleRes = i;
        int i2 = styleRes;
        this.mTransitionStyle = styleRes;
        return this;
    }

    public FragmentTransaction addToBackStack(String str) {
        String name = str;
        String str2 = name;
        if (this.mAllowAddToBackStack) {
            this.mAddToBackStack = true;
            this.mName = name;
            return this;
        }
        throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }

    public boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack;
    }

    public FragmentTransaction disallowAddToBackStack() {
        if (!this.mAddToBackStack) {
            this.mAllowAddToBackStack = false;
            return this;
        }
        throw new IllegalStateException("This transaction is already being added to the back stack");
    }

    public FragmentTransaction setBreadCrumbTitle(int i) {
        int res = i;
        int i2 = res;
        this.mBreadCrumbTitleRes = res;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    public FragmentTransaction setBreadCrumbTitle(CharSequence charSequence) {
        CharSequence text = charSequence;
        CharSequence charSequence2 = text;
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = text;
        return this;
    }

    public FragmentTransaction setBreadCrumbShortTitle(int i) {
        int res = i;
        int i2 = res;
        this.mBreadCrumbShortTitleRes = res;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    public FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence) {
        CharSequence text = charSequence;
        CharSequence charSequence2 = text;
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = text;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void bumpBackStackNesting(int i) {
        int amt = i;
        int i2 = amt;
        if (this.mAddToBackStack) {
            if (FragmentManagerImpl.DEBUG) {
                int v = Log.v(TAG, "Bump nesting in " + this + " by " + amt);
            }
            int numOps = this.mOps.size();
            for (int opNum = 0; opNum < numOps; opNum++) {
                C0091Op op = (C0091Op) this.mOps.get(opNum);
                C0091Op op2 = op;
                if (op.fragment != null) {
                    op2.fragment.mBackStackNesting += amt;
                    if (FragmentManagerImpl.DEBUG) {
                        int v2 = Log.v(TAG, "Bump nesting of " + op2.fragment + " to " + op2.fragment.mBackStackNesting);
                    }
                }
            }
        }
    }

    public int commit() {
        return commitInternal(false);
    }

    public int commitAllowingStateLoss() {
        return commitInternal(true);
    }

    public void commitNow() {
        FragmentTransaction disallowAddToBackStack = disallowAddToBackStack();
        this.mManager.execSingleAction(this, false);
    }

    public void commitNowAllowingStateLoss() {
        FragmentTransaction disallowAddToBackStack = disallowAddToBackStack();
        this.mManager.execSingleAction(this, true);
    }

    public FragmentTransaction setAllowOptimization(boolean z) {
        this.mAllowOptimization = z;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public int commitInternal(boolean z) {
        boolean allowStateLoss = z;
        if (!this.mCommitted) {
            if (FragmentManagerImpl.DEBUG) {
                int v = Log.v(TAG, "Commit: " + this);
                dump("  ", null, new PrintWriter(new LogWriter(TAG)), null);
            }
            this.mCommitted = true;
            if (!this.mAddToBackStack) {
                this.mIndex = -1;
            } else {
                this.mIndex = this.mManager.allocBackStackIndex(this);
            }
            this.mManager.enqueueAction(this, allowStateLoss);
            return this.mIndex;
        }
        throw new IllegalStateException("commit already called");
    }

    public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        ArrayList<BackStackRecord> records = arrayList;
        ArrayList<Boolean> isRecordPop = arrayList2;
        ArrayList<BackStackRecord> arrayList3 = records;
        ArrayList<Boolean> arrayList4 = isRecordPop;
        if (FragmentManagerImpl.DEBUG) {
            int v = Log.v(TAG, "Run: " + this);
        }
        boolean add = records.add(this);
        boolean add2 = isRecordPop.add(Boolean.valueOf(false));
        if (this.mAddToBackStack) {
            this.mManager.addBackStackState(this);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean interactsWith(int i) {
        int containerId = i;
        int i2 = containerId;
        int numOps = this.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            C0091Op op = (C0091Op) this.mOps.get(opNum);
            C0091Op op2 = op;
            if (op.fragment.mContainerId == containerId) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean interactsWith(ArrayList<BackStackRecord> arrayList, int i, int i2) {
        ArrayList<BackStackRecord> records = arrayList;
        int startIndex = i;
        int endIndex = i2;
        ArrayList<BackStackRecord> arrayList2 = records;
        int i3 = startIndex;
        int i4 = endIndex;
        if (endIndex == startIndex) {
            return false;
        }
        int numOps = this.mOps.size();
        int lastContainer = -1;
        for (int opNum = 0; opNum < numOps; opNum++) {
            C0091Op op = (C0091Op) this.mOps.get(opNum);
            C0091Op op2 = op;
            int i5 = op.fragment.mContainerId;
            int container = i5;
            if (!(i5 == 0 || container == lastContainer)) {
                lastContainer = container;
                for (int i6 = startIndex; i6 < endIndex; i6++) {
                    BackStackRecord backStackRecord = (BackStackRecord) records.get(i6);
                    BackStackRecord record = backStackRecord;
                    int numThoseOps = backStackRecord.mOps.size();
                    for (int thoseOpIndex = 0; thoseOpIndex < numThoseOps; thoseOpIndex++) {
                        C0091Op op3 = (C0091Op) record.mOps.get(thoseOpIndex);
                        C0091Op op4 = op3;
                        if (op3.fragment.mContainerId == container) {
                            return true;
                        }
                    }
                }
                continue;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void executeOps() {
        int numOps = this.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            C0091Op op = (C0091Op) this.mOps.get(opNum);
            C0091Op op2 = op;
            Fragment fragment = op.fragment;
            Fragment f = fragment;
            fragment.setNextTransition(this.mTransition, this.mTransitionStyle);
            switch (op2.cmd) {
                case 1:
                    f.setNextAnim(op2.enterAnim);
                    this.mManager.addFragment(f, false);
                    break;
                case 3:
                    f.setNextAnim(op2.exitAnim);
                    this.mManager.removeFragment(f);
                    break;
                case 4:
                    f.setNextAnim(op2.exitAnim);
                    this.mManager.hideFragment(f);
                    break;
                case 5:
                    f.setNextAnim(op2.enterAnim);
                    this.mManager.showFragment(f);
                    break;
                case 6:
                    f.setNextAnim(op2.exitAnim);
                    this.mManager.detachFragment(f);
                    break;
                case 7:
                    f.setNextAnim(op2.enterAnim);
                    this.mManager.attachFragment(f);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + op2.cmd);
            }
            if (!this.mAllowOptimization && op2.cmd != 1) {
                this.mManager.moveFragmentToExpectedState(f);
            }
        }
        if (!this.mAllowOptimization) {
            this.mManager.moveToState(this.mManager.mCurState, true);
        }
    }

    /* access modifiers changed from: 0000 */
    public void executePopOps() {
        for (int opNum = this.mOps.size() - 1; opNum >= 0; opNum--) {
            C0091Op op = (C0091Op) this.mOps.get(opNum);
            C0091Op op2 = op;
            Fragment fragment = op.fragment;
            Fragment f = fragment;
            fragment.setNextTransition(FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
            switch (op2.cmd) {
                case 1:
                    f.setNextAnim(op2.popExitAnim);
                    this.mManager.removeFragment(f);
                    break;
                case 3:
                    f.setNextAnim(op2.popEnterAnim);
                    this.mManager.addFragment(f, false);
                    break;
                case 4:
                    f.setNextAnim(op2.popEnterAnim);
                    this.mManager.showFragment(f);
                    break;
                case 5:
                    f.setNextAnim(op2.popExitAnim);
                    this.mManager.hideFragment(f);
                    break;
                case 6:
                    f.setNextAnim(op2.popEnterAnim);
                    this.mManager.attachFragment(f);
                    break;
                case 7:
                    f.setNextAnim(op2.popExitAnim);
                    this.mManager.detachFragment(f);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + op2.cmd);
            }
            if (!this.mAllowOptimization && op2.cmd != 3) {
                this.mManager.moveFragmentToExpectedState(f);
            }
        }
        if (!this.mAllowOptimization) {
            this.mManager.moveToState(this.mManager.mCurState, true);
        }
    }

    /* access modifiers changed from: 0000 */
    public void expandReplaceOps(ArrayList<Fragment> arrayList) {
        ArrayList<Fragment> added = arrayList;
        ArrayList<Fragment> arrayList2 = added;
        int opNum = 0;
        while (opNum < this.mOps.size()) {
            C0091Op op = (C0091Op) this.mOps.get(opNum);
            C0091Op op2 = op;
            switch (op.cmd) {
                case 1:
                case 7:
                    boolean add = added.add(op2.fragment);
                    break;
                case 2:
                    Fragment fragment = op2.fragment;
                    Fragment f = fragment;
                    int containerId = fragment.mContainerId;
                    boolean alreadyAdded = false;
                    for (int i = added.size() - 1; i >= 0; i--) {
                        Fragment fragment2 = (Fragment) added.get(i);
                        Fragment old = fragment2;
                        if (fragment2.mContainerId == containerId) {
                            if (old != f) {
                                C0091Op op3 = new C0091Op();
                                C0091Op removeOp = op3;
                                op3.cmd = 3;
                                removeOp.fragment = old;
                                removeOp.enterAnim = op2.enterAnim;
                                removeOp.popEnterAnim = op2.popEnterAnim;
                                removeOp.exitAnim = op2.exitAnim;
                                removeOp.popExitAnim = op2.popExitAnim;
                                this.mOps.add(opNum, removeOp);
                                boolean remove = added.remove(old);
                                opNum++;
                            } else {
                                alreadyAdded = true;
                            }
                        }
                    }
                    if (alreadyAdded) {
                        Object remove2 = this.mOps.remove(opNum);
                        opNum--;
                        break;
                    } else {
                        op2.cmd = 1;
                        boolean add2 = added.add(f);
                        break;
                    }
                case 3:
                case 6:
                    boolean remove3 = added.remove(op2.fragment);
                    break;
            }
            opNum++;
        }
    }

    /* access modifiers changed from: 0000 */
    public void trackAddedFragmentsInPop(ArrayList<Fragment> arrayList) {
        ArrayList<Fragment> added = arrayList;
        ArrayList<Fragment> arrayList2 = added;
        for (int opNum = 0; opNum < this.mOps.size(); opNum++) {
            C0091Op op = (C0091Op) this.mOps.get(opNum);
            C0091Op op2 = op;
            switch (op.cmd) {
                case 1:
                case 7:
                    boolean remove = added.remove(op2.fragment);
                    break;
                case 3:
                case 6:
                    boolean add = added.add(op2.fragment);
                    break;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isPostponed() {
        for (int opNum = 0; opNum < this.mOps.size(); opNum++) {
            C0091Op op = (C0091Op) this.mOps.get(opNum);
            C0091Op op2 = op;
            if (isFragmentPostponed(op)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void setOnStartPostponedListener(OnStartEnterTransitionListener onStartEnterTransitionListener) {
        OnStartEnterTransitionListener listener = onStartEnterTransitionListener;
        OnStartEnterTransitionListener onStartEnterTransitionListener2 = listener;
        for (int opNum = 0; opNum < this.mOps.size(); opNum++) {
            C0091Op op = (C0091Op) this.mOps.get(opNum);
            C0091Op op2 = op;
            if (isFragmentPostponed(op)) {
                op2.fragment.setOnStartEnterTransitionListener(listener);
            }
        }
    }

    private static boolean isFragmentPostponed(C0091Op op) {
        C0091Op op2 = op;
        C0091Op op3 = op2;
        Fragment fragment = op2.fragment;
        Fragment fragment2 = fragment;
        return fragment.mAdded && fragment2.mView != null && !fragment2.mDetached && !fragment2.mHidden && fragment2.isPostponed();
    }

    public String getName() {
        return this.mName;
    }

    public int getTransition() {
        return this.mTransition;
    }

    public int getTransitionStyle() {
        return this.mTransitionStyle;
    }

    public boolean isEmpty() {
        return this.mOps.isEmpty();
    }
}
