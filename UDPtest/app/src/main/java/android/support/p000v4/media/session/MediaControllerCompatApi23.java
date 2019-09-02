package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.media.session.MediaControllerCompatApi23 */
class MediaControllerCompatApi23 {

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi23$TransportControls */
    public static class TransportControls extends android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls {
        public TransportControls() {
        }

        public static void playFromUri(Object obj, Uri uri, Bundle bundle) {
            Object controlsObj = obj;
            Uri uri2 = uri;
            Bundle extras = bundle;
            Object obj2 = controlsObj;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            ((android.media.session.MediaController.TransportControls) controlsObj).playFromUri(uri2, extras);
        }
    }

    MediaControllerCompatApi23() {
    }
}
