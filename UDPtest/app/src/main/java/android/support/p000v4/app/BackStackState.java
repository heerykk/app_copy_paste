package android.support.p000v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

/* renamed from: android.support.v4.app.BackStackState */
/* compiled from: BackStackRecord */
final class BackStackState implements Parcelable {
    public static final Creator<BackStackState> CREATOR = new Creator<BackStackState>() {
        public BackStackState createFromParcel(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            return new BackStackState(in);
        }

        public BackStackState[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new BackStackState[size];
        }
    };
    final boolean mAllowOptimization;
    final int mBreadCrumbShortTitleRes;
    final CharSequence mBreadCrumbShortTitleText;
    final int mBreadCrumbTitleRes;
    final CharSequence mBreadCrumbTitleText;
    final int mIndex;
    final String mName;
    final int[] mOps;
    final ArrayList<String> mSharedElementSourceNames;
    final ArrayList<String> mSharedElementTargetNames;
    final int mTransition;
    final int mTransitionStyle;

    public BackStackState(BackStackRecord backStackRecord) {
        BackStackRecord bse = backStackRecord;
        BackStackRecord backStackRecord2 = bse;
        int size = bse.mOps.size();
        int numOps = size;
        this.mOps = new int[(size * 6)];
        if (bse.mAddToBackStack) {
            int pos = 0;
            for (int opNum = 0; opNum < numOps; opNum++) {
                C0091Op op = (C0091Op) bse.mOps.get(opNum);
                int i = pos;
                int pos2 = pos + 1;
                this.mOps[i] = op.cmd;
                int i2 = pos2;
                int pos3 = pos2 + 1;
                this.mOps[i2] = op.fragment == null ? -1 : op.fragment.mIndex;
                int i3 = pos3;
                int pos4 = pos3 + 1;
                this.mOps[i3] = op.enterAnim;
                int i4 = pos4;
                int pos5 = pos4 + 1;
                this.mOps[i4] = op.exitAnim;
                int i5 = pos5;
                int pos6 = pos5 + 1;
                this.mOps[i5] = op.popEnterAnim;
                int i6 = pos6;
                pos = pos6 + 1;
                this.mOps[i6] = op.popExitAnim;
            }
            this.mTransition = bse.mTransition;
            this.mTransitionStyle = bse.mTransitionStyle;
            this.mName = bse.mName;
            this.mIndex = bse.mIndex;
            this.mBreadCrumbTitleRes = bse.mBreadCrumbTitleRes;
            this.mBreadCrumbTitleText = bse.mBreadCrumbTitleText;
            this.mBreadCrumbShortTitleRes = bse.mBreadCrumbShortTitleRes;
            this.mBreadCrumbShortTitleText = bse.mBreadCrumbShortTitleText;
            this.mSharedElementSourceNames = bse.mSharedElementSourceNames;
            this.mSharedElementTargetNames = bse.mSharedElementTargetNames;
            this.mAllowOptimization = bse.mAllowOptimization;
            return;
        }
        throw new IllegalStateException("Not on back stack");
    }

    public BackStackState(Parcel parcel) {
        Parcel in = parcel;
        Parcel parcel2 = in;
        this.mOps = in.createIntArray();
        this.mTransition = in.readInt();
        this.mTransitionStyle = in.readInt();
        this.mName = in.readString();
        this.mIndex = in.readInt();
        this.mBreadCrumbTitleRes = in.readInt();
        this.mBreadCrumbTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mBreadCrumbShortTitleRes = in.readInt();
        this.mBreadCrumbShortTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mSharedElementSourceNames = in.createStringArrayList();
        this.mSharedElementTargetNames = in.createStringArrayList();
        this.mAllowOptimization = in.readInt() != 0;
    }

    public BackStackRecord instantiate(FragmentManagerImpl fragmentManagerImpl) {
        FragmentManagerImpl fm = fragmentManagerImpl;
        FragmentManagerImpl fragmentManagerImpl2 = fm;
        BackStackRecord bse = new BackStackRecord(fm);
        int pos = 0;
        int num = 0;
        while (pos < this.mOps.length) {
            C0091Op op = new C0091Op();
            C0091Op op2 = op;
            int i = pos;
            int pos2 = pos + 1;
            op.cmd = this.mOps[i];
            if (FragmentManagerImpl.DEBUG) {
                int v = Log.v("FragmentManager", "Instantiate " + bse + " op #" + num + " base fragment #" + this.mOps[pos2]);
            }
            int i2 = pos2;
            int pos3 = pos2 + 1;
            int i3 = this.mOps[i2];
            int findex = i3;
            if (i3 < 0) {
                op2.fragment = null;
            } else {
                op2.fragment = (Fragment) fm.mActive.get(findex);
            }
            int i4 = pos3;
            int pos4 = pos3 + 1;
            op2.enterAnim = this.mOps[i4];
            int i5 = pos4;
            int pos5 = pos4 + 1;
            op2.exitAnim = this.mOps[i5];
            int i6 = pos5;
            int pos6 = pos5 + 1;
            op2.popEnterAnim = this.mOps[i6];
            int i7 = pos6;
            pos = pos6 + 1;
            op2.popExitAnim = this.mOps[i7];
            bse.mEnterAnim = op2.enterAnim;
            bse.mExitAnim = op2.exitAnim;
            bse.mPopEnterAnim = op2.popEnterAnim;
            bse.mPopExitAnim = op2.popExitAnim;
            bse.addOp(op2);
            num++;
        }
        bse.mTransition = this.mTransition;
        bse.mTransitionStyle = this.mTransitionStyle;
        bse.mName = this.mName;
        bse.mIndex = this.mIndex;
        bse.mAddToBackStack = true;
        bse.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
        bse.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
        bse.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
        bse.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
        bse.mSharedElementSourceNames = this.mSharedElementSourceNames;
        bse.mSharedElementTargetNames = this.mSharedElementTargetNames;
        bse.mAllowOptimization = this.mAllowOptimization;
        bse.bumpBackStackNesting(1);
        return bse;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel dest = parcel;
        Parcel parcel2 = dest;
        int i2 = i;
        dest.writeIntArray(this.mOps);
        dest.writeInt(this.mTransition);
        dest.writeInt(this.mTransitionStyle);
        dest.writeString(this.mName);
        dest.writeInt(this.mIndex);
        dest.writeInt(this.mBreadCrumbTitleRes);
        TextUtils.writeToParcel(this.mBreadCrumbTitleText, dest, 0);
        dest.writeInt(this.mBreadCrumbShortTitleRes);
        TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, dest, 0);
        dest.writeStringList(this.mSharedElementSourceNames);
        dest.writeStringList(this.mSharedElementTargetNames);
        dest.writeInt(!this.mAllowOptimization ? 0 : 1);
    }
}
