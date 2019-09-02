package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.RequiresApi;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.MediaDescriptionCompatApi21 */
class MediaDescriptionCompatApi21 {

    /* renamed from: android.support.v4.media.MediaDescriptionCompatApi21$Builder */
    static class Builder {
        Builder() {
        }

        public static Object newInstance() {
            return new android.media.MediaDescription.Builder();
        }

        public static void setMediaId(Object obj, String str) {
            Object builderObj = obj;
            String mediaId = str;
            Object obj2 = builderObj;
            String str2 = mediaId;
            android.media.MediaDescription.Builder mediaId2 = ((android.media.MediaDescription.Builder) builderObj).setMediaId(mediaId);
        }

        public static void setTitle(Object obj, CharSequence charSequence) {
            Object builderObj = obj;
            CharSequence title = charSequence;
            Object obj2 = builderObj;
            CharSequence charSequence2 = title;
            android.media.MediaDescription.Builder title2 = ((android.media.MediaDescription.Builder) builderObj).setTitle(title);
        }

        public static void setSubtitle(Object obj, CharSequence charSequence) {
            Object builderObj = obj;
            CharSequence subtitle = charSequence;
            Object obj2 = builderObj;
            CharSequence charSequence2 = subtitle;
            android.media.MediaDescription.Builder subtitle2 = ((android.media.MediaDescription.Builder) builderObj).setSubtitle(subtitle);
        }

        public static void setDescription(Object obj, CharSequence charSequence) {
            Object builderObj = obj;
            CharSequence description = charSequence;
            Object obj2 = builderObj;
            CharSequence charSequence2 = description;
            android.media.MediaDescription.Builder description2 = ((android.media.MediaDescription.Builder) builderObj).setDescription(description);
        }

        public static void setIconBitmap(Object obj, Bitmap bitmap) {
            Object builderObj = obj;
            Bitmap iconBitmap = bitmap;
            Object obj2 = builderObj;
            Bitmap bitmap2 = iconBitmap;
            android.media.MediaDescription.Builder iconBitmap2 = ((android.media.MediaDescription.Builder) builderObj).setIconBitmap(iconBitmap);
        }

        public static void setIconUri(Object obj, Uri uri) {
            Object builderObj = obj;
            Uri iconUri = uri;
            Object obj2 = builderObj;
            Uri uri2 = iconUri;
            android.media.MediaDescription.Builder iconUri2 = ((android.media.MediaDescription.Builder) builderObj).setIconUri(iconUri);
        }

        public static void setExtras(Object obj, Bundle bundle) {
            Object builderObj = obj;
            Bundle extras = bundle;
            Object obj2 = builderObj;
            Bundle bundle2 = extras;
            android.media.MediaDescription.Builder extras2 = ((android.media.MediaDescription.Builder) builderObj).setExtras(extras);
        }

        public static Object build(Object obj) {
            Object builderObj = obj;
            Object obj2 = builderObj;
            return ((android.media.MediaDescription.Builder) builderObj).build();
        }
    }

    MediaDescriptionCompatApi21() {
    }

    public static String getMediaId(Object obj) {
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        return ((MediaDescription) descriptionObj).getMediaId();
    }

    public static CharSequence getTitle(Object obj) {
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        return ((MediaDescription) descriptionObj).getTitle();
    }

    public static CharSequence getSubtitle(Object obj) {
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        return ((MediaDescription) descriptionObj).getSubtitle();
    }

    public static CharSequence getDescription(Object obj) {
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        return ((MediaDescription) descriptionObj).getDescription();
    }

    public static Bitmap getIconBitmap(Object obj) {
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        return ((MediaDescription) descriptionObj).getIconBitmap();
    }

    public static Uri getIconUri(Object obj) {
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        return ((MediaDescription) descriptionObj).getIconUri();
    }

    public static Bundle getExtras(Object obj) {
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        return ((MediaDescription) descriptionObj).getExtras();
    }

    public static void writeToParcel(Object obj, Parcel parcel, int i) {
        Object descriptionObj = obj;
        Parcel dest = parcel;
        int flags = i;
        Object obj2 = descriptionObj;
        Parcel parcel2 = dest;
        int i2 = flags;
        ((MediaDescription) descriptionObj).writeToParcel(dest, flags);
    }

    public static Object fromParcel(Parcel parcel) {
        Parcel in = parcel;
        Parcel parcel2 = in;
        return MediaDescription.CREATOR.createFromParcel(in);
    }
}
