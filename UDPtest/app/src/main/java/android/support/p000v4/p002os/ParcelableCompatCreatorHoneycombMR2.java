package android.support.p000v4.p002os;

import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;
import android.support.annotation.RequiresApi;

@TargetApi(13)
@RequiresApi(13)
/* renamed from: android.support.v4.os.ParcelableCompatCreatorHoneycombMR2 */
/* compiled from: ParcelableCompatHoneycombMR2 */
class ParcelableCompatCreatorHoneycombMR2<T> implements ClassLoaderCreator<T> {
    private final ParcelableCompatCreatorCallbacks<T> mCallbacks;

    public ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks) {
        ParcelableCompatCreatorCallbacks<T> callbacks = parcelableCompatCreatorCallbacks;
        ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks2 = callbacks;
        this.mCallbacks = callbacks;
    }

    public T createFromParcel(Parcel parcel) {
        Parcel in = parcel;
        Parcel parcel2 = in;
        return this.mCallbacks.createFromParcel(in, null);
    }

    public T createFromParcel(Parcel parcel, ClassLoader classLoader) {
        Parcel in = parcel;
        ClassLoader loader = classLoader;
        Parcel parcel2 = in;
        ClassLoader classLoader2 = loader;
        return this.mCallbacks.createFromParcel(in, loader);
    }

    public T[] newArray(int i) {
        int size = i;
        int i2 = size;
        return this.mCallbacks.newArray(size);
    }
}
