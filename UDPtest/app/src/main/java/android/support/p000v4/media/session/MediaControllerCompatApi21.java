package android.support.p000v4.media.session;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession.QueueItem;
import android.media.session.MediaSession.Token;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.session.MediaControllerCompatApi21 */
class MediaControllerCompatApi21 {

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi21$Callback */
    public interface Callback {
        void onAudioInfoChanged(int i, int i2, int i3, int i4, int i5);

        void onExtrasChanged(Bundle bundle);

        void onMetadataChanged(Object obj);

        void onPlaybackStateChanged(Object obj);

        void onQueueChanged(List<?> list);

        void onQueueTitleChanged(CharSequence charSequence);

        void onSessionDestroyed();

        void onSessionEvent(String str, Bundle bundle);
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi21$CallbackProxy */
    static class CallbackProxy<T extends Callback> extends android.media.session.MediaController.Callback {
        protected final T mCallback;

        public CallbackProxy(T t) {
            T callback = t;
            T t2 = callback;
            this.mCallback = callback;
        }

        public void onSessionDestroyed() {
            this.mCallback.onSessionDestroyed();
        }

        public void onSessionEvent(String str, Bundle bundle) {
            String event = str;
            Bundle extras = bundle;
            String str2 = event;
            Bundle bundle2 = extras;
            this.mCallback.onSessionEvent(event, extras);
        }

        public void onPlaybackStateChanged(PlaybackState playbackState) {
            PlaybackState state = playbackState;
            PlaybackState playbackState2 = state;
            this.mCallback.onPlaybackStateChanged(state);
        }

        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            MediaMetadata metadata = mediaMetadata;
            MediaMetadata mediaMetadata2 = metadata;
            this.mCallback.onMetadataChanged(metadata);
        }

        public void onQueueChanged(List<QueueItem> list) {
            List<QueueItem> queue = list;
            List<QueueItem> list2 = queue;
            this.mCallback.onQueueChanged(queue);
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.mCallback.onQueueTitleChanged(title);
        }

        public void onExtrasChanged(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            this.mCallback.onExtrasChanged(extras);
        }

        public void onAudioInfoChanged(android.media.session.MediaController.PlaybackInfo playbackInfo) {
            android.media.session.MediaController.PlaybackInfo info = playbackInfo;
            android.media.session.MediaController.PlaybackInfo playbackInfo2 = info;
            this.mCallback.onAudioInfoChanged(info.getPlaybackType(), PlaybackInfo.getLegacyAudioStream(info), info.getVolumeControl(), info.getMaxVolume(), info.getCurrentVolume());
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi21$PlaybackInfo */
    public static class PlaybackInfo {
        private static final int FLAG_SCO = 4;
        private static final int STREAM_BLUETOOTH_SCO = 6;
        private static final int STREAM_SYSTEM_ENFORCED = 7;

        public PlaybackInfo() {
        }

        public static int getPlaybackType(Object obj) {
            Object volumeInfoObj = obj;
            Object obj2 = volumeInfoObj;
            return ((android.media.session.MediaController.PlaybackInfo) volumeInfoObj).getPlaybackType();
        }

        public static AudioAttributes getAudioAttributes(Object obj) {
            Object volumeInfoObj = obj;
            Object obj2 = volumeInfoObj;
            return ((android.media.session.MediaController.PlaybackInfo) volumeInfoObj).getAudioAttributes();
        }

        public static int getLegacyAudioStream(Object obj) {
            Object volumeInfoObj = obj;
            Object obj2 = volumeInfoObj;
            AudioAttributes audioAttributes = getAudioAttributes(volumeInfoObj);
            AudioAttributes audioAttributes2 = audioAttributes;
            return toLegacyStreamType(audioAttributes);
        }

        public static int getVolumeControl(Object obj) {
            Object volumeInfoObj = obj;
            Object obj2 = volumeInfoObj;
            return ((android.media.session.MediaController.PlaybackInfo) volumeInfoObj).getVolumeControl();
        }

        public static int getMaxVolume(Object obj) {
            Object volumeInfoObj = obj;
            Object obj2 = volumeInfoObj;
            return ((android.media.session.MediaController.PlaybackInfo) volumeInfoObj).getMaxVolume();
        }

        public static int getCurrentVolume(Object obj) {
            Object volumeInfoObj = obj;
            Object obj2 = volumeInfoObj;
            return ((android.media.session.MediaController.PlaybackInfo) volumeInfoObj).getCurrentVolume();
        }

        private static int toLegacyStreamType(AudioAttributes audioAttributes) {
            AudioAttributes aa = audioAttributes;
            AudioAttributes audioAttributes2 = aa;
            if ((aa.getFlags() & 1) == 1) {
                return 7;
            }
            if ((aa.getFlags() & 4) == 4) {
                return 6;
            }
            switch (aa.getUsage()) {
                case 1:
                case 11:
                case 12:
                case 14:
                    return 3;
                case 2:
                    return 0;
                case 3:
                    return 8;
                case 4:
                    return 4;
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                    return 5;
                case 6:
                    return 2;
                case 13:
                    return 1;
                default:
                    return 3;
            }
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi21$TransportControls */
    public static class TransportControls {
        public TransportControls() {
        }

        public static void play(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).play();
        }

        public static void pause(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).pause();
        }

        public static void stop(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).stop();
        }

        public static void seekTo(Object obj, long j) {
            Object controlsObj = obj;
            long pos = j;
            Object obj2 = controlsObj;
            long j2 = pos;
            ((android.media.session.MediaController.TransportControls) controlsObj).seekTo(pos);
        }

        public static void fastForward(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).fastForward();
        }

        public static void rewind(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).rewind();
        }

        public static void skipToNext(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).skipToNext();
        }

        public static void skipToPrevious(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).skipToPrevious();
        }

        public static void setRating(Object obj, Object obj2) {
            Object controlsObj = obj;
            Object ratingObj = obj2;
            Object obj3 = controlsObj;
            Object obj4 = ratingObj;
            ((android.media.session.MediaController.TransportControls) controlsObj).setRating((Rating) ratingObj);
        }

        public static void playFromMediaId(Object obj, String str, Bundle bundle) {
            Object controlsObj = obj;
            String mediaId = str;
            Bundle extras = bundle;
            Object obj2 = controlsObj;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            ((android.media.session.MediaController.TransportControls) controlsObj).playFromMediaId(mediaId, extras);
        }

        public static void playFromSearch(Object obj, String str, Bundle bundle) {
            Object controlsObj = obj;
            String query = str;
            Bundle extras = bundle;
            Object obj2 = controlsObj;
            String str2 = query;
            Bundle bundle2 = extras;
            ((android.media.session.MediaController.TransportControls) controlsObj).playFromSearch(query, extras);
        }

        public static void skipToQueueItem(Object obj, long j) {
            Object controlsObj = obj;
            long id = j;
            Object obj2 = controlsObj;
            long j2 = id;
            ((android.media.session.MediaController.TransportControls) controlsObj).skipToQueueItem(id);
        }

        public static void sendCustomAction(Object obj, String str, Bundle bundle) {
            Object controlsObj = obj;
            String action = str;
            Bundle args = bundle;
            Object obj2 = controlsObj;
            String str2 = action;
            Bundle bundle2 = args;
            ((android.media.session.MediaController.TransportControls) controlsObj).sendCustomAction(action, args);
        }
    }

    MediaControllerCompatApi21() {
    }

    public static Object fromToken(Context context, Object obj) {
        Context context2 = context;
        Object sessionToken = obj;
        Context context3 = context2;
        Object obj2 = sessionToken;
        return new MediaController(context2, (Token) sessionToken);
    }

    public static Object createCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new CallbackProxy(callback2);
    }

    public static void registerCallback(Object obj, Object obj2, Handler handler) {
        Object controllerObj = obj;
        Object callbackObj = obj2;
        Handler handler2 = handler;
        Object obj3 = controllerObj;
        Object obj4 = callbackObj;
        Handler handler3 = handler2;
        ((MediaController) controllerObj).registerCallback((android.media.session.MediaController.Callback) callbackObj, handler2);
    }

    public static void unregisterCallback(Object obj, Object obj2) {
        Object controllerObj = obj;
        Object callbackObj = obj2;
        Object obj3 = controllerObj;
        Object obj4 = callbackObj;
        ((MediaController) controllerObj).unregisterCallback((android.media.session.MediaController.Callback) callbackObj);
    }

    public static void setMediaController(Activity activity, Object obj) {
        Activity activity2 = activity;
        Object controllerObj = obj;
        Activity activity3 = activity2;
        Object obj2 = controllerObj;
        activity2.setMediaController((MediaController) controllerObj);
    }

    public static Object getMediaController(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        return activity2.getMediaController();
    }

    public static Object getSessionToken(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getSessionToken();
    }

    public static Object getTransportControls(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getTransportControls();
    }

    public static Object getPlaybackState(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getPlaybackState();
    }

    public static Object getMetadata(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getMetadata();
    }

    public static List<Object> getQueue(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        List queue = ((MediaController) controllerObj).getQueue();
        List list = queue;
        if (queue == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list);
        ArrayList arrayList2 = arrayList;
        return arrayList;
    }

    public static CharSequence getQueueTitle(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getQueueTitle();
    }

    public static Bundle getExtras(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getExtras();
    }

    public static int getRatingType(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getRatingType();
    }

    public static long getFlags(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getFlags();
    }

    public static Object getPlaybackInfo(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getPlaybackInfo();
    }

    public static PendingIntent getSessionActivity(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getSessionActivity();
    }

    public static boolean dispatchMediaButtonEvent(Object obj, KeyEvent keyEvent) {
        Object controllerObj = obj;
        KeyEvent event = keyEvent;
        Object obj2 = controllerObj;
        KeyEvent keyEvent2 = event;
        return ((MediaController) controllerObj).dispatchMediaButtonEvent(event);
    }

    public static void setVolumeTo(Object obj, int i, int i2) {
        Object controllerObj = obj;
        int value = i;
        int flags = i2;
        Object obj2 = controllerObj;
        int i3 = value;
        int i4 = flags;
        ((MediaController) controllerObj).setVolumeTo(value, flags);
    }

    public static void adjustVolume(Object obj, int i, int i2) {
        Object controllerObj = obj;
        int direction = i;
        int flags = i2;
        Object obj2 = controllerObj;
        int i3 = direction;
        int i4 = flags;
        ((MediaController) controllerObj).adjustVolume(direction, flags);
    }

    public static void sendCommand(Object obj, String str, Bundle bundle, ResultReceiver resultReceiver) {
        Object controllerObj = obj;
        String command = str;
        Bundle params = bundle;
        ResultReceiver cb = resultReceiver;
        Object obj2 = controllerObj;
        String str2 = command;
        Bundle bundle2 = params;
        ResultReceiver resultReceiver2 = cb;
        ((MediaController) controllerObj).sendCommand(command, params, cb);
    }

    public static String getPackageName(Object obj) {
        Object controllerObj = obj;
        Object obj2 = controllerObj;
        return ((MediaController) controllerObj).getPackageName();
    }
}
