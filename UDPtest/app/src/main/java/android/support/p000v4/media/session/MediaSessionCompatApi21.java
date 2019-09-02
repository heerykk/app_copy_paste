package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes.Builder;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Token;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.session.MediaSessionCompatApi21 */
class MediaSessionCompatApi21 {

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi21$Callback */
    interface Callback extends Callback {
        void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver);

        void onCustomAction(String str, Bundle bundle);

        void onFastForward();

        boolean onMediaButtonEvent(Intent intent);

        void onPause();

        void onPlay();

        void onPlayFromMediaId(String str, Bundle bundle);

        void onPlayFromSearch(String str, Bundle bundle);

        void onRewind();

        void onSkipToNext();

        void onSkipToPrevious();

        void onSkipToQueueItem(long j);

        void onStop();
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi21$CallbackProxy */
    static class CallbackProxy<T extends Callback> extends android.media.session.MediaSession.Callback {
        protected final T mCallback;

        public CallbackProxy(T t) {
            T callback = t;
            T t2 = callback;
            this.mCallback = callback;
        }

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            String command = str;
            Bundle args = bundle;
            ResultReceiver cb = resultReceiver;
            String str2 = command;
            Bundle bundle2 = args;
            ResultReceiver resultReceiver2 = cb;
            this.mCallback.onCommand(command, args, cb);
        }

        public boolean onMediaButtonEvent(Intent intent) {
            Intent mediaButtonIntent = intent;
            Intent intent2 = mediaButtonIntent;
            return this.mCallback.onMediaButtonEvent(mediaButtonIntent) || super.onMediaButtonEvent(mediaButtonIntent);
        }

        public void onPlay() {
            this.mCallback.onPlay();
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
            String mediaId = str;
            Bundle extras = bundle;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            this.mCallback.onPlayFromMediaId(mediaId, extras);
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
            String search = str;
            Bundle extras = bundle;
            String str2 = search;
            Bundle bundle2 = extras;
            this.mCallback.onPlayFromSearch(search, extras);
        }

        public void onSkipToQueueItem(long j) {
            long id = j;
            long j2 = id;
            this.mCallback.onSkipToQueueItem(id);
        }

        public void onPause() {
            this.mCallback.onPause();
        }

        public void onSkipToNext() {
            this.mCallback.onSkipToNext();
        }

        public void onSkipToPrevious() {
            this.mCallback.onSkipToPrevious();
        }

        public void onFastForward() {
            this.mCallback.onFastForward();
        }

        public void onRewind() {
            this.mCallback.onRewind();
        }

        public void onStop() {
            this.mCallback.onStop();
        }

        public void onSeekTo(long j) {
            long pos = j;
            long j2 = pos;
            this.mCallback.onSeekTo(pos);
        }

        public void onSetRating(Rating rating) {
            Rating rating2 = rating;
            Rating rating3 = rating2;
            this.mCallback.onSetRating(rating2);
        }

        public void onCustomAction(String str, Bundle bundle) {
            String action = str;
            Bundle extras = bundle;
            String str2 = action;
            Bundle bundle2 = extras;
            this.mCallback.onCustomAction(action, extras);
        }
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompatApi21$QueueItem */
    static class QueueItem {
        QueueItem() {
        }

        public static Object createItem(Object obj, long j) {
            Object mediaDescription = obj;
            long id = j;
            Object obj2 = mediaDescription;
            long j2 = id;
            return new android.media.session.MediaSession.QueueItem((MediaDescription) mediaDescription, id);
        }

        public static Object getDescription(Object obj) {
            Object queueItem = obj;
            Object obj2 = queueItem;
            return ((android.media.session.MediaSession.QueueItem) queueItem).getDescription();
        }

        public static long getQueueId(Object obj) {
            Object queueItem = obj;
            Object obj2 = queueItem;
            return ((android.media.session.MediaSession.QueueItem) queueItem).getQueueId();
        }
    }

    MediaSessionCompatApi21() {
    }

    public static Object createSession(Context context, String str) {
        Context context2 = context;
        String tag = str;
        Context context3 = context2;
        String str2 = tag;
        return new MediaSession(context2, tag);
    }

    public static Object verifySession(Object obj) {
        Object mediaSession = obj;
        Object obj2 = mediaSession;
        if (mediaSession instanceof MediaSession) {
            return mediaSession;
        }
        throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
    }

    public static Object verifyToken(Object obj) {
        Object token = obj;
        Object obj2 = token;
        if (token instanceof Token) {
            return token;
        }
        throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
    }

    public static Object createCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new CallbackProxy(callback2);
    }

    public static void setCallback(Object obj, Object obj2, Handler handler) {
        Object sessionObj = obj;
        Object callbackObj = obj2;
        Handler handler2 = handler;
        Object obj3 = sessionObj;
        Object obj4 = callbackObj;
        Handler handler3 = handler2;
        ((MediaSession) sessionObj).setCallback((android.media.session.MediaSession.Callback) callbackObj, handler2);
    }

    public static void setFlags(Object obj, int i) {
        Object sessionObj = obj;
        int flags = i;
        Object obj2 = sessionObj;
        int i2 = flags;
        ((MediaSession) sessionObj).setFlags(flags);
    }

    public static void setPlaybackToLocal(Object obj, int i) {
        Object sessionObj = obj;
        int stream = i;
        Object obj2 = sessionObj;
        int i2 = stream;
        Builder builder = new Builder();
        Builder bob = builder;
        Builder legacyStreamType = builder.setLegacyStreamType(stream);
        ((MediaSession) sessionObj).setPlaybackToLocal(bob.build());
    }

    public static void setPlaybackToRemote(Object obj, Object obj2) {
        Object sessionObj = obj;
        Object volumeProviderObj = obj2;
        Object obj3 = sessionObj;
        Object obj4 = volumeProviderObj;
        ((MediaSession) sessionObj).setPlaybackToRemote((VolumeProvider) volumeProviderObj);
    }

    public static void setActive(Object obj, boolean z) {
        Object sessionObj = obj;
        Object obj2 = sessionObj;
        ((MediaSession) sessionObj).setActive(z);
    }

    public static boolean isActive(Object obj) {
        Object sessionObj = obj;
        Object obj2 = sessionObj;
        return ((MediaSession) sessionObj).isActive();
    }

    public static void sendSessionEvent(Object obj, String str, Bundle bundle) {
        Object sessionObj = obj;
        String event = str;
        Bundle extras = bundle;
        Object obj2 = sessionObj;
        String str2 = event;
        Bundle bundle2 = extras;
        ((MediaSession) sessionObj).sendSessionEvent(event, extras);
    }

    public static void release(Object obj) {
        Object sessionObj = obj;
        Object obj2 = sessionObj;
        ((MediaSession) sessionObj).release();
    }

    public static Parcelable getSessionToken(Object obj) {
        Object sessionObj = obj;
        Object obj2 = sessionObj;
        return ((MediaSession) sessionObj).getSessionToken();
    }

    public static void setPlaybackState(Object obj, Object obj2) {
        Object sessionObj = obj;
        Object stateObj = obj2;
        Object obj3 = sessionObj;
        Object obj4 = stateObj;
        ((MediaSession) sessionObj).setPlaybackState((PlaybackState) stateObj);
    }

    public static void setMetadata(Object obj, Object obj2) {
        Object sessionObj = obj;
        Object metadataObj = obj2;
        Object obj3 = sessionObj;
        Object obj4 = metadataObj;
        ((MediaSession) sessionObj).setMetadata((MediaMetadata) metadataObj);
    }

    public static void setSessionActivity(Object obj, PendingIntent pendingIntent) {
        Object sessionObj = obj;
        PendingIntent pi = pendingIntent;
        Object obj2 = sessionObj;
        PendingIntent pendingIntent2 = pi;
        ((MediaSession) sessionObj).setSessionActivity(pi);
    }

    public static void setMediaButtonReceiver(Object obj, PendingIntent pendingIntent) {
        Object sessionObj = obj;
        PendingIntent pi = pendingIntent;
        Object obj2 = sessionObj;
        PendingIntent pendingIntent2 = pi;
        ((MediaSession) sessionObj).setMediaButtonReceiver(pi);
    }

    public static void setQueue(Object obj, List<Object> list) {
        Object sessionObj = obj;
        List<Object> queueObjs = list;
        Object obj2 = sessionObj;
        List<Object> list2 = queueObjs;
        if (queueObjs != null) {
            ArrayList arrayList = new ArrayList();
            Iterator it = queueObjs.iterator();
            while (it.hasNext()) {
                boolean add = arrayList.add((android.media.session.MediaSession.QueueItem) it.next());
            }
            ((MediaSession) sessionObj).setQueue(arrayList);
            return;
        }
        ((MediaSession) sessionObj).setQueue(null);
    }

    public static void setQueueTitle(Object obj, CharSequence charSequence) {
        Object sessionObj = obj;
        CharSequence title = charSequence;
        Object obj2 = sessionObj;
        CharSequence charSequence2 = title;
        ((MediaSession) sessionObj).setQueueTitle(title);
    }

    public static void setExtras(Object obj, Bundle bundle) {
        Object sessionObj = obj;
        Bundle extras = bundle;
        Object obj2 = sessionObj;
        Bundle bundle2 = extras;
        ((MediaSession) sessionObj).setExtras(extras);
    }
}
