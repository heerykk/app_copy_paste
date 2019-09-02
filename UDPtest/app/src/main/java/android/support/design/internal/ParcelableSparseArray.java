package android.support.design.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.p002os.ParcelableCompat;
import android.support.p000v4.p002os.ParcelableCompatCreatorCallbacks;
import android.util.SparseArray;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ParcelableSparseArray extends SparseArray<Parcelable> implements Parcelable {
    public static final Creator<ParcelableSparseArray> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<ParcelableSparseArray>() {
        public ParcelableSparseArray createFromParcel(Parcel parcel, ClassLoader classLoader) {
            Parcel source = parcel;
            ClassLoader loader = classLoader;
            Parcel parcel2 = source;
            ClassLoader classLoader2 = loader;
            return new ParcelableSparseArray(source, loader);
        }

        public ParcelableSparseArray[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new ParcelableSparseArray[size];
        }
    });

    public ParcelableSparseArray() {
    }

    public ParcelableSparseArray(Parcel parcel, ClassLoader classLoader) {
        Parcel source = parcel;
        ClassLoader loader = classLoader;
        Parcel parcel2 = source;
        ClassLoader classLoader2 = loader;
        int readInt = source.readInt();
        int size = readInt;
        int[] keys = new int[readInt];
        source.readIntArray(keys);
        Parcelable[] values = source.readParcelableArray(loader);
        for (int i = 0; i < size; i++) {
            put(keys[i], values[i]);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel parcel2 = parcel;
        int flags = i;
        Parcel parcel3 = parcel2;
        int i2 = flags;
        int size = size();
        int size2 = size;
        int[] keys = new int[size];
        Parcelable[] values = new Parcelable[size2];
        for (int i3 = 0; i3 < size2; i3++) {
            keys[i3] = keyAt(i3);
            values[i3] = (Parcelable) valueAt(i3);
        }
        parcel2.writeInt(size2);
        parcel2.writeIntArray(keys);
        parcel2.writeParcelableArray(values, flags);
    }
}
