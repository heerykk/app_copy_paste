package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.media.MediaDescription;
import android.net.Uri;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.media.MediaDescriptionCompatApi23 */
class MediaDescriptionCompatApi23 extends MediaDescriptionCompatApi21 {

    /* renamed from: android.support.v4.media.MediaDescriptionCompatApi23$Builder */
    static class Builder extends Builder {
        Builder() {
        }

        public static void setMediaUri(Object obj, Uri uri) {
            Object builderObj = obj;
            Uri mediaUri = uri;
            Object obj2 = builderObj;
            Uri uri2 = mediaUri;
            android.media.MediaDescription.Builder mediaUri2 = ((android.media.MediaDescription.Builder) builderObj).setMediaUri(mediaUri);
        }
    }

    MediaDescriptionCompatApi23() {
    }

    public static Uri getMediaUri(Object obj) {
        Object descriptionObj = obj;
        Object obj2 = descriptionObj;
        return ((MediaDescription) descriptionObj).getMediaUri();
    }
}
