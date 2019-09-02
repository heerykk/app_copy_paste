package android.support.p000v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.Arrays;

/* renamed from: android.support.v4.app.BundleUtil */
class BundleUtil {
    BundleUtil() {
    }

    public static Bundle[] getBundleArrayFromBundle(Bundle bundle, String str) {
        Bundle bundle2 = bundle;
        String key = str;
        Bundle bundle3 = bundle2;
        String str2 = key;
        Parcelable[] parcelableArray = bundle2.getParcelableArray(key);
        Parcelable[] array = parcelableArray;
        if ((parcelableArray instanceof Bundle[]) || array == null) {
            return (Bundle[]) array;
        }
        Bundle[] typedArray = (Bundle[]) Arrays.copyOf(array, array.length, Bundle[].class);
        bundle2.putParcelableArray(key, typedArray);
        return typedArray;
    }
}
