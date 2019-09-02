package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.Rating;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import java.util.Set;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.MediaMetadataCompatApi21 */
class MediaMetadataCompatApi21 {

    /* renamed from: android.support.v4.media.MediaMetadataCompatApi21$Builder */
    public static class Builder {
        public Builder() {
        }

        public static Object newInstance() {
            return new android.media.MediaMetadata.Builder();
        }

        public static void putBitmap(Object obj, String str, Bitmap bitmap) {
            Object builderObj = obj;
            String key = str;
            Bitmap value = bitmap;
            Object obj2 = builderObj;
            String str2 = key;
            Bitmap bitmap2 = value;
            android.media.MediaMetadata.Builder putBitmap = ((android.media.MediaMetadata.Builder) builderObj).putBitmap(key, value);
        }

        public static void putLong(Object obj, String str, long j) {
            Object builderObj = obj;
            String key = str;
            long value = j;
            Object obj2 = builderObj;
            String str2 = key;
            long j2 = value;
            android.media.MediaMetadata.Builder putLong = ((android.media.MediaMetadata.Builder) builderObj).putLong(key, value);
        }

        public static void putRating(Object obj, String str, Object obj2) {
            Object builderObj = obj;
            String key = str;
            Object ratingObj = obj2;
            Object obj3 = builderObj;
            String str2 = key;
            Object obj4 = ratingObj;
            android.media.MediaMetadata.Builder putRating = ((android.media.MediaMetadata.Builder) builderObj).putRating(key, (Rating) ratingObj);
        }

        public static void putText(Object obj, String str, CharSequence charSequence) {
            Object builderObj = obj;
            String key = str;
            CharSequence value = charSequence;
            Object obj2 = builderObj;
            String str2 = key;
            CharSequence charSequence2 = value;
            android.media.MediaMetadata.Builder putText = ((android.media.MediaMetadata.Builder) builderObj).putText(key, value);
        }

        public static void putString(Object obj, String str, String str2) {
            Object builderObj = obj;
            String key = str;
            String value = str2;
            Object obj2 = builderObj;
            String str3 = key;
            String str4 = value;
            android.media.MediaMetadata.Builder putString = ((android.media.MediaMetadata.Builder) builderObj).putString(key, value);
        }

        public static Object build(Object obj) {
            Object builderObj = obj;
            Object obj2 = builderObj;
            return ((android.media.MediaMetadata.Builder) builderObj).build();
        }
    }

    MediaMetadataCompatApi21() {
    }

    public static Set<String> keySet(Object obj) {
        Object metadataObj = obj;
        Object obj2 = metadataObj;
        return ((MediaMetadata) metadataObj).keySet();
    }

    public static Bitmap getBitmap(Object obj, String str) {
        Object metadataObj = obj;
        String key = str;
        Object obj2 = metadataObj;
        String str2 = key;
        return ((MediaMetadata) metadataObj).getBitmap(key);
    }

    public static long getLong(Object obj, String str) {
        Object metadataObj = obj;
        String key = str;
        Object obj2 = metadataObj;
        String str2 = key;
        return ((MediaMetadata) metadataObj).getLong(key);
    }

    public static Object getRating(Object obj, String str) {
        Object metadataObj = obj;
        String key = str;
        Object obj2 = metadataObj;
        String str2 = key;
        return ((MediaMetadata) metadataObj).getRating(key);
    }

    public static CharSequence getText(Object obj, String str) {
        Object metadataObj = obj;
        String key = str;
        Object obj2 = metadataObj;
        String str2 = key;
        return ((MediaMetadata) metadataObj).getText(key);
    }

    public static void writeToParcel(Object obj, Parcel parcel, int i) {
        Object metadataObj = obj;
        Parcel dest = parcel;
        int flags = i;
        Object obj2 = metadataObj;
        Parcel parcel2 = dest;
        int i2 = flags;
        ((MediaMetadata) metadataObj).writeToParcel(dest, flags);
    }

    public static Object createFromParcel(Parcel parcel) {
        Parcel in = parcel;
        Parcel parcel2 = in;
        return MediaMetadata.CREATOR.createFromParcel(in);
    }
}
