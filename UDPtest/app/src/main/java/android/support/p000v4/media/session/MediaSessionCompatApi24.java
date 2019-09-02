package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.media.session.MediaSessionCompatApi24 */
class MediaSessionCompatApi24 {
    private static final String TAG = "MediaSessionCompatApi24";

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi24$Callback */
    public interface Callback extends android.support.p000v4.media.session.MediaSessionCompatApi23.Callback {
        void onPrepare();

        void onPrepareFromMediaId(String str, Bundle bundle);

        void onPrepareFromSearch(String str, Bundle bundle);

        void onPrepareFromUri(Uri uri, Bundle bundle);
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi24$CallbackProxy */
    static class CallbackProxy<T extends Callback> extends CallbackProxy<T> {
        public CallbackProxy(T t) {
            T callback = t;
            T t2 = callback;
            super(callback);
        }

        public void onPrepare() {
            ((Callback) this.mCallback).onPrepare();
        }

        public void onPrepareFromMediaId(String str, Bundle bundle) {
            String mediaId = str;
            Bundle extras = bundle;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            ((Callback) this.mCallback).onPrepareFromMediaId(mediaId, extras);
        }

        public void onPrepareFromSearch(String str, Bundle bundle) {
            String query = str;
            Bundle extras = bundle;
            String str2 = query;
            Bundle bundle2 = extras;
            ((Callback) this.mCallback).onPrepareFromSearch(query, extras);
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle extras = bundle;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            ((Callback) this.mCallback).onPrepareFromUri(uri2, extras);
        }
    }

    MediaSessionCompatApi24() {
    }

    public static Object createCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new CallbackProxy(callback2);
    }

    public static String getCallingPackage(Object obj) {
        Object sessionObj = obj;
        Object obj2 = sessionObj;
        MediaSession mediaSession = (MediaSession) sessionObj;
        MediaSession session = mediaSession;
        try {
            Method method = mediaSession.getClass().getMethod("getCallingPackage", new Class[0]);
            Method method2 = method;
            return (String) method.invoke(session, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Throwable th = e;
            int e2 = Log.e(TAG, "Cannot execute MediaSession.getCallingPackage()", e);
            return null;
        }
    }
}
