package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.media.session.MediaSession;
import android.support.annotation.RequiresApi;

@TargetApi(22)
@RequiresApi(22)
/* renamed from: android.support.v4.media.session.MediaSessionCompatApi22 */
class MediaSessionCompatApi22 {
    MediaSessionCompatApi22() {
    }

    public static void setRatingType(Object obj, int i) {
        Object sessionObj = obj;
        int type = i;
        Object obj2 = sessionObj;
        int i2 = type;
        ((MediaSession) sessionObj).setRatingType(type);
    }
}
