package android.support.p000v4.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;

/* renamed from: android.support.v4.view.AbsSavedState */
public abstract class AbsSavedState implements Parcelable {
    public static final Creator<AbsSavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<AbsSavedState>() {
        public AbsSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            Parcel in = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = in;
            ClassLoader classLoader2 = loader;
            Parcelable readParcelable = in.readParcelable(loader);
            Parcelable parcelable = readParcelable;
            if (readParcelable == null) {
                return AbsSavedState.EMPTY_STATE;
            }
            throw new IllegalStateException("superState must be null");
        }

        public AbsSavedState[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new AbsSavedState[size];
        }
    });
    public static final AbsSavedState EMPTY_STATE = new AbsSavedState() {
    };
    private final Parcelable mSuperState;

    /* synthetic */ AbsSavedState(C01891 r5) {
        C01891 r3 = r5;
        this();
    }

    private AbsSavedState() {
        this.mSuperState = null;
    }

    protected AbsSavedState(Parcelable parcelable) {
        Parcelable superState = parcelable;
        Parcelable parcelable2 = superState;
        if (superState != null) {
            this.mSuperState = superState == EMPTY_STATE ? null : superState;
            return;
        }
        throw new IllegalArgumentException("superState must not be null");
    }

    protected AbsSavedState(Parcel parcel) {
        Parcel source = parcel;
        Parcel parcel2 = source;
        this(source, null);
    }

    protected AbsSavedState(Parcel parcel, ClassLoader classLoader) {
        Parcel source = parcel;
        ClassLoader loader = classLoader;
        Parcel parcel2 = source;
        ClassLoader classLoader2 = loader;
        Parcelable superState = source.readParcelable(loader);
        this.mSuperState = superState == null ? EMPTY_STATE : superState;
    }

    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel dest = parcel;
        int flags = i;
        Parcel parcel2 = dest;
        int i2 = flags;
        dest.writeParcelable(this.mSuperState, flags);
    }
}
