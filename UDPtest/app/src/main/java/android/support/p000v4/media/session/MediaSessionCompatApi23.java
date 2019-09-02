package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.media.session.MediaSessionCompatApi23 */
class MediaSessionCompatApi23 {

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi23$Callback */
    public interface Callback extends Callback {
        void onPlayFromUri(Uri uri, Bundle bundle);
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi23$CallbackProxy */
    static class CallbackProxy<T extends Callback> extends CallbackProxy<T> {
        public CallbackProxy(T t) {
            T callback = t;
            T t2 = callback;
            super(callback);
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle extras = bundle;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            ((Callback) this.mCallback).onPlayFromUri(uri2, extras);
        }
    }

    MediaSessionCompatApi23() {
    }

    public static Object createCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new CallbackProxy(callback2);
    }
}
