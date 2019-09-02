package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.media.session.MediaSessionCompatApi18 */
class MediaSessionCompatApi18 {
    private static final long ACTION_SEEK_TO = 256;
    private static final String TAG = "MediaSessionCompatApi18";
    private static boolean sIsMbrPendingIntentSupported = true;

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi18$Callback */
    interface Callback {
        void onSeekTo(long j);
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi18$OnPlaybackPositionUpdateListener */
    static class OnPlaybackPositionUpdateListener<T extends Callback> implements android.media.RemoteControlClient.OnPlaybackPositionUpdateListener {
        protected final T mCallback;

        public OnPlaybackPositionUpdateListener(T t) {
            T callback = t;
            T t2 = callback;
            this.mCallback = callback;
        }

        public void onPlaybackPositionUpdate(long j) {
            long newPositionMs = j;
            long j2 = newPositionMs;
            this.mCallback.onSeekTo(newPositionMs);
        }
    }

    MediaSessionCompatApi18() {
    }

    public static Object createPlaybackPositionUpdateListener(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new OnPlaybackPositionUpdateListener(callback2);
    }

    public static void registerMediaButtonEventReceiver(Context context, PendingIntent pendingIntent, ComponentName componentName) {
        Context context2 = context;
        PendingIntent pi = pendingIntent;
        ComponentName cn = componentName;
        Context context3 = context2;
        PendingIntent pendingIntent2 = pi;
        ComponentName componentName2 = cn;
        AudioManager audioManager = (AudioManager) context2.getSystemService("audio");
        AudioManager am = audioManager;
        if (sIsMbrPendingIntentSupported) {
            try {
                audioManager.registerMediaButtonEventReceiver(pi);
            } catch (NullPointerException e) {
                NullPointerException nullPointerException = e;
                int w = Log.w(TAG, "Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
                sIsMbrPendingIntentSupported = false;
            }
        }
        if (!sIsMbrPendingIntentSupported) {
            am.registerMediaButtonEventReceiver(cn);
        }
    }

    public static void unregisterMediaButtonEventReceiver(Context context, PendingIntent pendingIntent, ComponentName componentName) {
        Context context2 = context;
        PendingIntent pi = pendingIntent;
        ComponentName cn = componentName;
        Context context3 = context2;
        PendingIntent pendingIntent2 = pi;
        ComponentName componentName2 = cn;
        AudioManager audioManager = (AudioManager) context2.getSystemService("audio");
        AudioManager audioManager2 = audioManager;
        if (!sIsMbrPendingIntentSupported) {
            audioManager.unregisterMediaButtonEventReceiver(cn);
        } else {
            audioManager.unregisterMediaButtonEventReceiver(pi);
        }
    }

    public static void setState(Object obj, int i, long j, float f, long j2) {
        Object rccObj = obj;
        int state = i;
        long position = j;
        float speed = f;
        long updateTime = j2;
        Object obj2 = rccObj;
        int i2 = state;
        long position2 = position;
        float f2 = speed;
        long j3 = updateTime;
        long currTime = SystemClock.elapsedRealtime();
        if (state == 3) {
            if (!(position <= 0)) {
                long diff = 0;
                if (!(updateTime <= 0)) {
                    long j4 = currTime - updateTime;
                    diff = j4;
                    if (speed > 0.0f && speed != 1.0f) {
                        diff = (long) (((float) j4) * speed);
                    }
                }
                position2 = position + diff;
            }
        }
        int rccStateFromState = MediaSessionCompatApi14.getRccStateFromState(state);
        int state2 = rccStateFromState;
        ((RemoteControlClient) rccObj).setPlaybackState(rccStateFromState, position2, speed);
    }

    public static void setTransportControlFlags(Object obj, long j) {
        Object rccObj = obj;
        long actions = j;
        Object obj2 = rccObj;
        long j2 = actions;
        ((RemoteControlClient) rccObj).setTransportControlFlags(getRccTransportControlFlagsFromActions(actions));
    }

    public static void setOnPlaybackPositionUpdateListener(Object obj, Object obj2) {
        Object rccObj = obj;
        Object onPositionUpdateObj = obj2;
        Object obj3 = rccObj;
        Object obj4 = onPositionUpdateObj;
        ((RemoteControlClient) rccObj).setPlaybackPositionUpdateListener((android.media.RemoteControlClient.OnPlaybackPositionUpdateListener) onPositionUpdateObj);
    }

    static int getRccTransportControlFlagsFromActions(long j) {
        long actions = j;
        long j2 = actions;
        int rccTransportControlFlagsFromActions = MediaSessionCompatApi14.getRccTransportControlFlagsFromActions(actions);
        int transportControlFlags = rccTransportControlFlagsFromActions;
        if ((actions & 256) != 0) {
            transportControlFlags = rccTransportControlFlagsFromActions | 256;
        }
        return transportControlFlags;
    }
}
