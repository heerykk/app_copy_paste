package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.media.session.MediaControllerCompatApi24 */
class MediaControllerCompatApi24 {

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi24$TransportControls */
    public static class TransportControls extends android.support.p000v4.media.session.MediaControllerCompatApi23.TransportControls {
        public TransportControls() {
        }

        public static void prepare(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).prepare();
        }

        public static void prepareFromMediaId(Object obj, String str, Bundle bundle) {
            Object controlsObj = obj;
            String mediaId = str;
            Bundle extras = bundle;
            Object obj2 = controlsObj;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            ((android.media.session.MediaController.TransportControls) controlsObj).prepareFromMediaId(mediaId, extras);
        }

        public static void prepareFromSearch(Object obj, String str, Bundle bundle) {
            Object controlsObj = obj;
            String query = str;
            Bundle extras = bundle;
            Object obj2 = controlsObj;
            String str2 = query;
            Bundle bundle2 = extras;
            ((android.media.session.MediaController.TransportControls) controlsObj).prepareFromSearch(query, extras);
        }

        public static void prepareFromUri(Object obj, Uri uri, Bundle bundle) {
            Object controlsObj = obj;
            Uri uri2 = uri;
            Bundle extras = bundle;
            Object obj2 = controlsObj;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            ((android.media.session.MediaController.TransportControls) controlsObj).prepareFromUri(uri2, extras);
        }
    }

    MediaControllerCompatApi24() {
    }
}
