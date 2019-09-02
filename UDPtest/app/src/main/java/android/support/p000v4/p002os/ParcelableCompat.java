package android.support.p000v4.p002os;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;

/* renamed from: android.support.v4.os.ParcelableCompat */
public final class ParcelableCompat {

    /* renamed from: android.support.v4.os.ParcelableCompat$CompatCreator */
    static class CompatCreator<T> implements Creator<T> {
        final ParcelableCompatCreatorCallbacks<T> mCallbacks;

        public CompatCreator(ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks) {
            ParcelableCompatCreatorCallbacks<T> callbacks = parcelableCompatCreatorCallbacks;
            ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks2 = callbacks;
            this.mCallbacks = callbacks;
        }

        public T createFromParcel(Parcel parcel) {
            Parcel source = parcel;
            Parcel parcel2 = source;
            return this.mCallbacks.createFromParcel(source, null);
        }

        public T[] newArray(int i) {
            int size = i;
            int i2 = size;
            return this.mCallbacks.newArray(size);
        }
    }

    public static <T> Creator<T> newCreator(ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks) {
        ParcelableCompatCreatorCallbacks<T> callbacks = parcelableCompatCreatorCallbacks;
        ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks2 = callbacks;
        if (VERSION.SDK_INT < 13) {
            return new CompatCreator(callbacks);
        }
        return ParcelableCompatCreatorHoneycombMR2Stub.instantiate(callbacks);
    }

    private ParcelableCompat() {
    }
}
