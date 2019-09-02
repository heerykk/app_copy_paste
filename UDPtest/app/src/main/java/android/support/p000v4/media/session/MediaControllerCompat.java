package android.support.p000v4.media.session;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.p000v4.app.BundleCompat;
import android.support.p000v4.app.SupportActivity;
import android.support.p000v4.app.SupportActivity.ExtraData;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.RatingCompat;
import android.support.p000v4.media.session.IMediaControllerCallback.Stub;
import android.support.p000v4.media.session.MediaSessionCompat.QueueItem;
import android.support.p000v4.media.session.MediaSessionCompat.Token;
import android.support.p000v4.media.session.PlaybackStateCompat.CustomAction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: android.support.v4.media.session.MediaControllerCompat */
public final class MediaControllerCompat {
    static final String COMMAND_GET_EXTRA_BINDER = "android.support.v4.media.session.command.GET_EXTRA_BINDER";
    static final String TAG = "MediaControllerCompat";
    private final MediaControllerImpl mImpl;
    private final Token mToken;

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback */
    public static abstract class Callback implements DeathRecipient {
        private final Object mCallbackObj;
        MessageHandler mHandler;
        boolean mHasExtraCallback;
        boolean mRegistered = false;

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$MessageHandler */
        private class MessageHandler extends Handler {
            private static final int MSG_DESTROYED = 8;
            private static final int MSG_EVENT = 1;
            private static final int MSG_UPDATE_EXTRAS = 7;
            private static final int MSG_UPDATE_METADATA = 3;
            private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
            private static final int MSG_UPDATE_QUEUE = 5;
            private static final int MSG_UPDATE_QUEUE_TITLE = 6;
            private static final int MSG_UPDATE_VOLUME = 4;
            final /* synthetic */ Callback this$0;

            public MessageHandler(Callback callback, Looper looper) {
                Looper looper2 = looper;
                Looper looper3 = looper2;
                this.this$0 = callback;
                super(looper2);
            }

            public void handleMessage(Message message) {
                Message msg = message;
                Message message2 = msg;
                if (this.this$0.mRegistered) {
                    switch (msg.what) {
                        case 1:
                            this.this$0.onSessionEvent((String) msg.obj, msg.getData());
                            break;
                        case 2:
                            this.this$0.onPlaybackStateChanged((PlaybackStateCompat) msg.obj);
                            break;
                        case 3:
                            this.this$0.onMetadataChanged((MediaMetadataCompat) msg.obj);
                            break;
                        case 4:
                            this.this$0.onAudioInfoChanged((PlaybackInfo) msg.obj);
                            break;
                        case 5:
                            this.this$0.onQueueChanged((List) msg.obj);
                            break;
                        case 6:
                            this.this$0.onQueueTitleChanged((CharSequence) msg.obj);
                            break;
                        case 7:
                            this.this$0.onExtrasChanged((Bundle) msg.obj);
                            break;
                        case 8:
                            this.this$0.onSessionDestroyed();
                            break;
                    }
                }
            }

            public void post(int i, Object obj, Bundle bundle) {
                int what = i;
                Object obj2 = obj;
                Bundle data = bundle;
                int i2 = what;
                Object obj3 = obj2;
                Bundle bundle2 = data;
                Message obtainMessage = obtainMessage(what, obj2);
                Message msg = obtainMessage;
                obtainMessage.setData(data);
                msg.sendToTarget();
            }
        }

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$StubApi21 */
        private class StubApi21 implements android.support.p000v4.media.session.MediaControllerCompatApi21.Callback {
            final /* synthetic */ Callback this$0;

            StubApi21(Callback callback) {
                this.this$0 = callback;
            }

            public void onSessionDestroyed() {
                this.this$0.onSessionDestroyed();
            }

            public void onSessionEvent(String str, Bundle bundle) {
                String event = str;
                Bundle extras = bundle;
                String str2 = event;
                Bundle bundle2 = extras;
                if (!this.this$0.mHasExtraCallback || VERSION.SDK_INT >= 23) {
                    this.this$0.onSessionEvent(event, extras);
                }
            }

            public void onPlaybackStateChanged(Object obj) {
                Object stateObj = obj;
                Object obj2 = stateObj;
                if (!this.this$0.mHasExtraCallback || VERSION.SDK_INT >= 22) {
                    this.this$0.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(stateObj));
                }
            }

            public void onMetadataChanged(Object obj) {
                Object metadataObj = obj;
                Object obj2 = metadataObj;
                this.this$0.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(metadataObj));
            }

            public void onQueueChanged(List<?> list) {
                List<?> queue = list;
                List<?> list2 = queue;
                this.this$0.onQueueChanged(QueueItem.fromQueueItemList(queue));
            }

            public void onQueueTitleChanged(CharSequence charSequence) {
                CharSequence title = charSequence;
                CharSequence charSequence2 = title;
                this.this$0.onQueueTitleChanged(title);
            }

            public void onExtrasChanged(Bundle bundle) {
                Bundle extras = bundle;
                Bundle bundle2 = extras;
                this.this$0.onExtrasChanged(extras);
            }

            public void onAudioInfoChanged(int i, int i2, int i3, int i4, int i5) {
                int type = i;
                int stream = i2;
                int control = i3;
                int max = i4;
                int current = i5;
                int i6 = type;
                int i7 = stream;
                int i8 = control;
                int i9 = max;
                int i10 = current;
                Callback callback = this.this$0;
                PlaybackInfo playbackInfo = new PlaybackInfo(type, stream, control, max, current);
                callback.onAudioInfoChanged(playbackInfo);
            }
        }

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat */
        private class StubCompat extends Stub {
            final /* synthetic */ Callback this$0;

            StubCompat(Callback callback) {
                this.this$0 = callback;
            }

            public void onEvent(String str, Bundle bundle) throws RemoteException {
                String event = str;
                Bundle extras = bundle;
                String str2 = event;
                Bundle bundle2 = extras;
                this.this$0.mHandler.post(1, event, extras);
            }

            public void onSessionDestroyed() throws RemoteException {
                this.this$0.mHandler.post(8, null, null);
            }

            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                PlaybackStateCompat state = playbackStateCompat;
                PlaybackStateCompat playbackStateCompat2 = state;
                this.this$0.mHandler.post(2, state, null);
            }

            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                MediaMetadataCompat metadata = mediaMetadataCompat;
                MediaMetadataCompat mediaMetadataCompat2 = metadata;
                this.this$0.mHandler.post(3, metadata, null);
            }

            public void onQueueChanged(List<QueueItem> list) throws RemoteException {
                List<QueueItem> queue = list;
                List<QueueItem> list2 = queue;
                this.this$0.mHandler.post(5, queue, null);
            }

            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                CharSequence title = charSequence;
                CharSequence charSequence2 = title;
                this.this$0.mHandler.post(6, title, null);
            }

            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Bundle extras = bundle;
                Bundle bundle2 = extras;
                this.this$0.mHandler.post(7, extras, null);
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                ParcelableVolumeInfo info = parcelableVolumeInfo;
                ParcelableVolumeInfo parcelableVolumeInfo2 = info;
                PlaybackInfo pi = null;
                if (info != null) {
                    PlaybackInfo playbackInfo = new PlaybackInfo(info.volumeType, info.audioStream, info.controlType, info.maxVolume, info.currentVolume);
                    pi = playbackInfo;
                }
                this.this$0.mHandler.post(4, pi, null);
            }
        }

        static /* synthetic */ Object access$000(Callback callback) {
            Callback x0 = callback;
            Callback callback2 = x0;
            return x0.mCallbackObj;
        }

        static /* synthetic */ void access$100(Callback callback, Handler handler) {
            Callback x0 = callback;
            Handler x1 = handler;
            Callback callback2 = x0;
            Handler handler2 = x1;
            x0.setHandler(x1);
        }

        public Callback() {
            if (VERSION.SDK_INT < 21) {
                this.mCallbackObj = new StubCompat(this);
            } else {
                this.mCallbackObj = MediaControllerCompatApi21.createCallback(new StubApi21(this));
            }
        }

        public void onSessionDestroyed() {
        }

        public void onSessionEvent(String str, Bundle bundle) {
            String str2 = str;
            Bundle bundle2 = bundle;
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
            PlaybackStateCompat playbackStateCompat2 = playbackStateCompat;
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
            MediaMetadataCompat mediaMetadataCompat2 = mediaMetadataCompat;
        }

        public void onQueueChanged(List<QueueItem> list) {
            List<QueueItem> list2 = list;
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
            CharSequence charSequence2 = charSequence;
        }

        public void onExtrasChanged(Bundle bundle) {
            Bundle bundle2 = bundle;
        }

        public void onAudioInfoChanged(PlaybackInfo playbackInfo) {
            PlaybackInfo playbackInfo2 = playbackInfo;
        }

        public void binderDied() {
            onSessionDestroyed();
        }

        private void setHandler(Handler handler) {
            Handler handler2 = handler;
            Handler handler3 = handler2;
            this.mHandler = new MessageHandler(this, handler2.getLooper());
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerExtraData */
    private static class MediaControllerExtraData extends ExtraData {
        private final MediaControllerCompat mMediaController;

        MediaControllerExtraData(MediaControllerCompat mediaControllerCompat) {
            MediaControllerCompat mediaController = mediaControllerCompat;
            MediaControllerCompat mediaControllerCompat2 = mediaController;
            this.mMediaController = mediaController;
        }

        /* access modifiers changed from: 0000 */
        public MediaControllerCompat getMediaController() {
            return this.mMediaController;
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImpl */
    interface MediaControllerImpl {
        void adjustVolume(int i, int i2);

        boolean dispatchMediaButtonEvent(KeyEvent keyEvent);

        Bundle getExtras();

        long getFlags();

        Object getMediaController();

        MediaMetadataCompat getMetadata();

        String getPackageName();

        PlaybackInfo getPlaybackInfo();

        PlaybackStateCompat getPlaybackState();

        List<QueueItem> getQueue();

        CharSequence getQueueTitle();

        int getRatingType();

        PendingIntent getSessionActivity();

        TransportControls getTransportControls();

        void registerCallback(Callback callback, Handler handler);

        void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver);

        void setVolumeTo(int i, int i2);

        void unregisterCallback(Callback callback);
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21 */
    static class MediaControllerImplApi21 implements MediaControllerImpl {
        private HashMap<Callback, ExtraCallback> mCallbackMap = new HashMap<>();
        protected final Object mControllerObj;
        private IMediaSession mExtraBinder;
        private List<Callback> mPendingCallbacks;

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraCallback */
        private class ExtraCallback extends Stub {
            private Callback mCallback;
            final /* synthetic */ MediaControllerImplApi21 this$0;

            static /* synthetic */ Callback access$500(ExtraCallback extraCallback) {
                ExtraCallback x0 = extraCallback;
                ExtraCallback extraCallback2 = x0;
                return x0.mCallback;
            }

            ExtraCallback(MediaControllerImplApi21 mediaControllerImplApi21, Callback callback) {
                Callback callback2 = callback;
                Callback callback3 = callback2;
                this.this$0 = mediaControllerImplApi21;
                this.mCallback = callback2;
            }

            public void onEvent(String str, Bundle bundle) throws RemoteException {
                String event = str;
                Bundle extras = bundle;
                String str2 = event;
                Bundle bundle2 = extras;
                final String str3 = event;
                final Bundle bundle3 = extras;
                boolean post = this.mCallback.mHandler.post(new Runnable(this) {
                    final /* synthetic */ ExtraCallback this$1;

                    {
                        ExtraCallback this$12 = r7;
                        String str = r8;
                        Bundle bundle = r9;
                        ExtraCallback extraCallback = this$12;
                        this.this$1 = this$12;
                    }

                    public void run() {
                        ExtraCallback.access$500(this.this$1).onSessionEvent(str3, bundle3);
                    }
                });
            }

            public void onSessionDestroyed() throws RemoteException {
                throw new AssertionError();
            }

            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                PlaybackStateCompat state = playbackStateCompat;
                PlaybackStateCompat playbackStateCompat2 = state;
                final PlaybackStateCompat playbackStateCompat3 = state;
                boolean post = this.mCallback.mHandler.post(new Runnable(this) {
                    final /* synthetic */ ExtraCallback this$1;

                    {
                        ExtraCallback this$12 = r6;
                        PlaybackStateCompat playbackStateCompat = r7;
                        ExtraCallback extraCallback = this$12;
                        this.this$1 = this$12;
                    }

                    public void run() {
                        ExtraCallback.access$500(this.this$1).onPlaybackStateChanged(playbackStateCompat3);
                    }
                });
            }

            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                MediaMetadataCompat mediaMetadataCompat2 = mediaMetadataCompat;
                throw new AssertionError();
            }

            public void onQueueChanged(List<QueueItem> list) throws RemoteException {
                List<QueueItem> list2 = list;
                throw new AssertionError();
            }

            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                CharSequence charSequence2 = charSequence;
                throw new AssertionError();
            }

            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Bundle bundle2 = bundle;
                throw new AssertionError();
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                ParcelableVolumeInfo parcelableVolumeInfo2 = parcelableVolumeInfo;
                throw new AssertionError();
            }
        }

        static /* synthetic */ IMediaSession access$200(MediaControllerImplApi21 mediaControllerImplApi21) {
            MediaControllerImplApi21 x0 = mediaControllerImplApi21;
            MediaControllerImplApi21 mediaControllerImplApi212 = x0;
            return x0.mExtraBinder;
        }

        static /* synthetic */ IMediaSession access$202(MediaControllerImplApi21 mediaControllerImplApi21, IMediaSession iMediaSession) {
            MediaControllerImplApi21 x0 = mediaControllerImplApi21;
            IMediaSession x1 = iMediaSession;
            MediaControllerImplApi21 mediaControllerImplApi212 = x0;
            IMediaSession iMediaSession2 = x1;
            x0.mExtraBinder = x1;
            return x1;
        }

        static /* synthetic */ List access$300(MediaControllerImplApi21 mediaControllerImplApi21) {
            MediaControllerImplApi21 x0 = mediaControllerImplApi21;
            MediaControllerImplApi21 mediaControllerImplApi212 = x0;
            return x0.mPendingCallbacks;
        }

        static /* synthetic */ List access$302(MediaControllerImplApi21 mediaControllerImplApi21, List list) {
            MediaControllerImplApi21 x0 = mediaControllerImplApi21;
            List x1 = list;
            MediaControllerImplApi21 mediaControllerImplApi212 = x0;
            List list2 = x1;
            x0.mPendingCallbacks = x1;
            return x1;
        }

        static /* synthetic */ HashMap access$400(MediaControllerImplApi21 mediaControllerImplApi21) {
            MediaControllerImplApi21 x0 = mediaControllerImplApi21;
            MediaControllerImplApi21 mediaControllerImplApi212 = x0;
            return x0.mCallbackMap;
        }

        public MediaControllerImplApi21(Context context, Token token) throws RemoteException {
            Context context2 = context;
            Token sessionToken = token;
            Context context3 = context2;
            Token token2 = sessionToken;
            this.mControllerObj = MediaControllerCompatApi21.fromToken(context2, sessionToken.getToken());
            if (this.mControllerObj != null) {
                requestExtraBinder();
                return;
            }
            throw new RemoteException();
        }

        public MediaControllerImplApi21(Context context, MediaSessionCompat mediaSessionCompat) {
            Context context2 = context;
            MediaSessionCompat session = mediaSessionCompat;
            Context context3 = context2;
            MediaSessionCompat mediaSessionCompat2 = session;
            this.mControllerObj = MediaControllerCompatApi21.fromToken(context2, session.getSessionToken().getToken());
            requestExtraBinder();
        }

        public final void registerCallback(Callback callback, Handler handler) {
            Callback callback2 = callback;
            Handler handler2 = handler;
            Callback callback3 = callback2;
            Handler handler3 = handler2;
            MediaControllerCompatApi21.registerCallback(this.mControllerObj, Callback.access$000(callback2), handler2);
            if (this.mExtraBinder == null) {
                if (this.mPendingCallbacks == null) {
                    this.mPendingCallbacks = new ArrayList();
                }
                Callback.access$100(callback2, handler2);
                boolean add = this.mPendingCallbacks.add(callback2);
                return;
            }
            Callback.access$100(callback2, handler2);
            ExtraCallback extraCallback = new ExtraCallback(this, callback2);
            Object put = this.mCallbackMap.put(callback2, extraCallback);
            callback2.mHasExtraCallback = true;
            try {
                this.mExtraBinder.registerCallbackListener(extraCallback);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in registerCallback. " + e);
            }
        }

        public final void unregisterCallback(Callback callback) {
            Callback callback2 = callback;
            Callback callback3 = callback2;
            MediaControllerCompatApi21.unregisterCallback(this.mControllerObj, Callback.access$000(callback2));
            if (this.mExtraBinder == null) {
                if (this.mPendingCallbacks == null) {
                    this.mPendingCallbacks = new ArrayList();
                }
                boolean remove = this.mPendingCallbacks.remove(callback2);
                return;
            }
            try {
                ExtraCallback extraCallback = (ExtraCallback) this.mCallbackMap.remove(callback2);
                ExtraCallback extraCallback2 = extraCallback;
                if (extraCallback != null) {
                    this.mExtraBinder.unregisterCallbackListener(extraCallback2);
                }
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in unregisterCallback. " + e);
            }
        }

        public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
            KeyEvent event = keyEvent;
            KeyEvent keyEvent2 = event;
            return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, event);
        }

        public TransportControls getTransportControls() {
            Object transportControls = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
            return transportControls == null ? null : new TransportControlsApi21(transportControls);
        }

        public PlaybackStateCompat getPlaybackState() {
            PlaybackStateCompat fromPlaybackState;
            if (VERSION.SDK_INT < 22 && this.mExtraBinder != null) {
                try {
                    return this.mExtraBinder.getPlaybackState();
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getPlaybackState. " + e);
                }
            }
            Object playbackState = MediaControllerCompatApi21.getPlaybackState(this.mControllerObj);
            Object stateObj = playbackState;
            if (playbackState == null) {
                fromPlaybackState = null;
            } else {
                fromPlaybackState = PlaybackStateCompat.fromPlaybackState(stateObj);
            }
            return fromPlaybackState;
        }

        public MediaMetadataCompat getMetadata() {
            Object metadata = MediaControllerCompatApi21.getMetadata(this.mControllerObj);
            return metadata == null ? null : MediaMetadataCompat.fromMediaMetadata(metadata);
        }

        public List<QueueItem> getQueue() {
            List queue = MediaControllerCompatApi21.getQueue(this.mControllerObj);
            return queue == null ? null : QueueItem.fromQueueItemList(queue);
        }

        public CharSequence getQueueTitle() {
            return MediaControllerCompatApi21.getQueueTitle(this.mControllerObj);
        }

        public Bundle getExtras() {
            return MediaControllerCompatApi21.getExtras(this.mControllerObj);
        }

        public int getRatingType() {
            if (VERSION.SDK_INT < 22 && this.mExtraBinder != null) {
                try {
                    return this.mExtraBinder.getRatingType();
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getRatingType. " + e);
                }
            }
            return MediaControllerCompatApi21.getRatingType(this.mControllerObj);
        }

        public long getFlags() {
            return MediaControllerCompatApi21.getFlags(this.mControllerObj);
        }

        public PlaybackInfo getPlaybackInfo() {
            PlaybackInfo playbackInfo;
            Object playbackInfo2 = MediaControllerCompatApi21.getPlaybackInfo(this.mControllerObj);
            Object volumeInfoObj = playbackInfo2;
            if (playbackInfo2 == null) {
                playbackInfo = null;
            } else {
                playbackInfo = new PlaybackInfo(android.support.p000v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getPlaybackType(volumeInfoObj), android.support.p000v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(volumeInfoObj), android.support.p000v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getVolumeControl(volumeInfoObj), android.support.p000v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getMaxVolume(volumeInfoObj), android.support.p000v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume(volumeInfoObj));
            }
            return playbackInfo;
        }

        public PendingIntent getSessionActivity() {
            return MediaControllerCompatApi21.getSessionActivity(this.mControllerObj);
        }

        public void setVolumeTo(int i, int i2) {
            int value = i;
            int flags = i2;
            int i3 = value;
            int i4 = flags;
            MediaControllerCompatApi21.setVolumeTo(this.mControllerObj, value, flags);
        }

        public void adjustVolume(int i, int i2) {
            int direction = i;
            int flags = i2;
            int i3 = direction;
            int i4 = flags;
            MediaControllerCompatApi21.adjustVolume(this.mControllerObj, direction, flags);
        }

        public void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            String command = str;
            Bundle params = bundle;
            ResultReceiver cb = resultReceiver;
            String str2 = command;
            Bundle bundle2 = params;
            ResultReceiver resultReceiver2 = cb;
            MediaControllerCompatApi21.sendCommand(this.mControllerObj, command, params, cb);
        }

        public String getPackageName() {
            return MediaControllerCompatApi21.getPackageName(this.mControllerObj);
        }

        public Object getMediaController() {
            return this.mControllerObj;
        }

        private void requestExtraBinder() {
            sendCommand(MediaControllerCompat.COMMAND_GET_EXTRA_BINDER, null, new ResultReceiver(this, new Handler()) {
                final /* synthetic */ MediaControllerImplApi21 this$0;

                {
                    MediaControllerImplApi21 this$02 = r8;
                    Handler x0 = r9;
                    MediaControllerImplApi21 mediaControllerImplApi21 = this$02;
                    Handler handler = x0;
                    this.this$0 = this$02;
                }

                /* access modifiers changed from: protected */
                public void onReceiveResult(int i, Bundle bundle) {
                    Bundle resultData = bundle;
                    int i2 = i;
                    Bundle bundle2 = resultData;
                    if (resultData != null) {
                        IMediaSession access$202 = MediaControllerImplApi21.access$202(this.this$0, IMediaSession.Stub.asInterface(BundleCompat.getBinder(resultData, "android.support.v4.media.session.EXTRA_BINDER")));
                        if (MediaControllerImplApi21.access$300(this.this$0) != null) {
                            for (Callback callback : MediaControllerImplApi21.access$300(this.this$0)) {
                                ExtraCallback extraCallback = new ExtraCallback(this.this$0, callback);
                                Object put = MediaControllerImplApi21.access$400(this.this$0).put(callback, extraCallback);
                                callback.mHasExtraCallback = true;
                                try {
                                    MediaControllerImplApi21.access$200(this.this$0).registerCallbackListener(extraCallback);
                                } catch (RemoteException e) {
                                    RemoteException remoteException = e;
                                    int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in registerCallback. " + e);
                                }
                            }
                            List access$302 = MediaControllerImplApi21.access$302(this.this$0, null);
                        }
                    }
                }
            });
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi23 */
    static class MediaControllerImplApi23 extends MediaControllerImplApi21 {
        public MediaControllerImplApi23(Context context, MediaSessionCompat mediaSessionCompat) {
            Context context2 = context;
            MediaSessionCompat session = mediaSessionCompat;
            Context context3 = context2;
            MediaSessionCompat mediaSessionCompat2 = session;
            super(context2, session);
        }

        public MediaControllerImplApi23(Context context, Token token) throws RemoteException {
            Context context2 = context;
            Token sessionToken = token;
            Context context3 = context2;
            Token token2 = sessionToken;
            super(context2, sessionToken);
        }

        public TransportControls getTransportControls() {
            Object transportControls = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
            return transportControls == null ? null : new TransportControlsApi23(transportControls);
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi24 */
    static class MediaControllerImplApi24 extends MediaControllerImplApi23 {
        public MediaControllerImplApi24(Context context, MediaSessionCompat mediaSessionCompat) {
            Context context2 = context;
            MediaSessionCompat session = mediaSessionCompat;
            Context context3 = context2;
            MediaSessionCompat mediaSessionCompat2 = session;
            super(context2, session);
        }

        public MediaControllerImplApi24(Context context, Token token) throws RemoteException {
            Context context2 = context;
            Token sessionToken = token;
            Context context3 = context2;
            Token token2 = sessionToken;
            super(context2, sessionToken);
        }

        public TransportControls getTransportControls() {
            Object transportControls = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
            return transportControls == null ? null : new TransportControlsApi24(transportControls);
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplBase */
    static class MediaControllerImplBase implements MediaControllerImpl {
        private IMediaSession mBinder;
        private Token mToken;
        private TransportControls mTransportControls;

        public MediaControllerImplBase(Token token) {
            Token token2 = token;
            Token token3 = token2;
            this.mToken = token2;
            this.mBinder = IMediaSession.Stub.asInterface((IBinder) token2.getToken());
        }

        public void registerCallback(Callback callback, Handler handler) {
            Callback callback2 = callback;
            Handler handler2 = handler;
            Callback callback3 = callback2;
            Handler handler3 = handler2;
            if (callback2 != null) {
                try {
                    this.mBinder.asBinder().linkToDeath(callback2, 0);
                    this.mBinder.registerCallbackListener((IMediaControllerCallback) Callback.access$000(callback2));
                    Callback.access$100(callback2, handler2);
                    callback2.mRegistered = true;
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in registerCallback. " + e);
                    callback2.onSessionDestroyed();
                }
            } else {
                throw new IllegalArgumentException("callback may not be null.");
            }
        }

        public void unregisterCallback(Callback callback) {
            Callback callback2 = callback;
            Callback callback3 = callback2;
            if (callback2 != null) {
                try {
                    this.mBinder.unregisterCallbackListener((IMediaControllerCallback) Callback.access$000(callback2));
                    boolean unlinkToDeath = this.mBinder.asBinder().unlinkToDeath(callback2, 0);
                    callback2.mRegistered = false;
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in unregisterCallback. " + e);
                }
            } else {
                throw new IllegalArgumentException("callback may not be null.");
            }
        }

        public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
            KeyEvent event = keyEvent;
            KeyEvent keyEvent2 = event;
            if (event != null) {
                try {
                    boolean sendMediaButton = this.mBinder.sendMediaButton(event);
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in dispatchMediaButtonEvent. " + e);
                }
                return false;
            }
            throw new IllegalArgumentException("event may not be null.");
        }

        public TransportControls getTransportControls() {
            if (this.mTransportControls == null) {
                this.mTransportControls = new TransportControlsBase(this.mBinder);
            }
            return this.mTransportControls;
        }

        public PlaybackStateCompat getPlaybackState() {
            try {
                return this.mBinder.getPlaybackState();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getPlaybackState. " + e);
                return null;
            }
        }

        public MediaMetadataCompat getMetadata() {
            try {
                return this.mBinder.getMetadata();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getMetadata. " + e);
                return null;
            }
        }

        public List<QueueItem> getQueue() {
            try {
                return this.mBinder.getQueue();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getQueue. " + e);
                return null;
            }
        }

        public CharSequence getQueueTitle() {
            try {
                return this.mBinder.getQueueTitle();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getQueueTitle. " + e);
                return null;
            }
        }

        public Bundle getExtras() {
            try {
                return this.mBinder.getExtras();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getExtras. " + e);
                return null;
            }
        }

        public int getRatingType() {
            try {
                return this.mBinder.getRatingType();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getRatingType. " + e);
                return 0;
            }
        }

        public long getFlags() {
            try {
                return this.mBinder.getFlags();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getFlags. " + e);
                return 0;
            }
        }

        public PlaybackInfo getPlaybackInfo() {
            try {
                ParcelableVolumeInfo info = this.mBinder.getVolumeAttributes();
                PlaybackInfo playbackInfo = new PlaybackInfo(info.volumeType, info.audioStream, info.controlType, info.maxVolume, info.currentVolume);
                PlaybackInfo playbackInfo2 = playbackInfo;
                return playbackInfo;
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getPlaybackInfo. " + e);
                return null;
            }
        }

        public PendingIntent getSessionActivity() {
            try {
                return this.mBinder.getLaunchPendingIntent();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getSessionActivity. " + e);
                return null;
            }
        }

        public void setVolumeTo(int i, int i2) {
            int value = i;
            int flags = i2;
            int i3 = value;
            int i4 = flags;
            try {
                this.mBinder.setVolumeTo(value, flags, null);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in setVolumeTo. " + e);
            }
        }

        public void adjustVolume(int i, int i2) {
            int direction = i;
            int flags = i2;
            int i3 = direction;
            int i4 = flags;
            try {
                this.mBinder.adjustVolume(direction, flags, null);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in adjustVolume. " + e);
            }
        }

        public void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            String command = str;
            Bundle params = bundle;
            ResultReceiver cb = resultReceiver;
            String str2 = command;
            Bundle bundle2 = params;
            ResultReceiver resultReceiver2 = cb;
            try {
                this.mBinder.sendCommand(command, params, new ResultReceiverWrapper(cb));
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in sendCommand. " + e);
            }
        }

        public String getPackageName() {
            try {
                return this.mBinder.getPackageName();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in getPackageName. " + e);
                return null;
            }
        }

        public Object getMediaController() {
            return null;
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$PlaybackInfo */
    public static final class PlaybackInfo {
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        private final int mAudioStream;
        private final int mCurrentVolume;
        private final int mMaxVolume;
        private final int mPlaybackType;
        private final int mVolumeControl;

        PlaybackInfo(int i, int i2, int i3, int i4, int i5) {
            int type = i;
            int stream = i2;
            int control = i3;
            int max = i4;
            int current = i5;
            int i6 = type;
            int i7 = stream;
            int i8 = control;
            int i9 = max;
            int i10 = current;
            this.mPlaybackType = type;
            this.mAudioStream = stream;
            this.mVolumeControl = control;
            this.mMaxVolume = max;
            this.mCurrentVolume = current;
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getAudioStream() {
            return this.mAudioStream;
        }

        public int getVolumeControl() {
            return this.mVolumeControl;
        }

        public int getMaxVolume() {
            return this.mMaxVolume;
        }

        public int getCurrentVolume() {
            return this.mCurrentVolume;
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControls */
    public static abstract class TransportControls {
        public abstract void fastForward();

        public abstract void pause();

        public abstract void play();

        public abstract void playFromMediaId(String str, Bundle bundle);

        public abstract void playFromSearch(String str, Bundle bundle);

        public abstract void playFromUri(Uri uri, Bundle bundle);

        public abstract void prepare();

        public abstract void prepareFromMediaId(String str, Bundle bundle);

        public abstract void prepareFromSearch(String str, Bundle bundle);

        public abstract void prepareFromUri(Uri uri, Bundle bundle);

        public abstract void rewind();

        public abstract void seekTo(long j);

        public abstract void sendCustomAction(CustomAction customAction, Bundle bundle);

        public abstract void sendCustomAction(String str, Bundle bundle);

        public abstract void setRating(RatingCompat ratingCompat);

        public abstract void skipToNext();

        public abstract void skipToPrevious();

        public abstract void skipToQueueItem(long j);

        public abstract void stop();

        TransportControls() {
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControlsApi21 */
    static class TransportControlsApi21 extends TransportControls {
        protected final Object mControlsObj;

        public TransportControlsApi21(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            this.mControlsObj = controlsObj;
        }

        public void prepare() {
            sendCustomAction("android.support.v4.media.session.action.PREPARE", (Bundle) null);
        }

        public void prepareFromMediaId(String str, Bundle bundle) {
            String mediaId = str;
            Bundle extras = bundle;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            Bundle bundle3 = new Bundle();
            Bundle bundle4 = bundle3;
            bundle3.putString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID", mediaId);
            bundle4.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID", bundle4);
        }

        public void prepareFromSearch(String str, Bundle bundle) {
            String query = str;
            Bundle extras = bundle;
            String str2 = query;
            Bundle bundle2 = extras;
            Bundle bundle3 = new Bundle();
            Bundle bundle4 = bundle3;
            bundle3.putString("android.support.v4.media.session.action.ARGUMENT_QUERY", query);
            bundle4.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_SEARCH", bundle4);
        }

        public void prepareFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle extras = bundle;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            Bundle bundle3 = new Bundle();
            Bundle bundle4 = bundle3;
            bundle3.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri2);
            bundle4.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_URI", bundle4);
        }

        public void play() {
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.play(this.mControlsObj);
        }

        public void pause() {
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.pause(this.mControlsObj);
        }

        public void stop() {
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.stop(this.mControlsObj);
        }

        public void seekTo(long j) {
            long pos = j;
            long j2 = pos;
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.seekTo(this.mControlsObj, pos);
        }

        public void fastForward() {
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.fastForward(this.mControlsObj);
        }

        public void rewind() {
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.rewind(this.mControlsObj);
        }

        public void skipToNext() {
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.skipToNext(this.mControlsObj);
        }

        public void skipToPrevious() {
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.skipToPrevious(this.mControlsObj);
        }

        public void setRating(RatingCompat ratingCompat) {
            RatingCompat rating = ratingCompat;
            RatingCompat ratingCompat2 = rating;
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.setRating(this.mControlsObj, rating == null ? null : rating.getRating());
        }

        public void playFromMediaId(String str, Bundle bundle) {
            String mediaId = str;
            Bundle extras = bundle;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.playFromMediaId(this.mControlsObj, mediaId, extras);
        }

        public void playFromSearch(String str, Bundle bundle) {
            String query = str;
            Bundle extras = bundle;
            String str2 = query;
            Bundle bundle2 = extras;
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.playFromSearch(this.mControlsObj, query, extras);
        }

        public void playFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle extras = bundle;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            if (uri2 != null && !Uri.EMPTY.equals(uri2)) {
                Bundle bundle3 = new Bundle();
                Bundle bundle4 = bundle3;
                bundle3.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri2);
                bundle4.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", extras);
                sendCustomAction("android.support.v4.media.session.action.PLAY_FROM_URI", bundle4);
                return;
            }
            throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
        }

        public void skipToQueueItem(long j) {
            long id = j;
            long j2 = id;
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.mControlsObj, id);
        }

        public void sendCustomAction(CustomAction customAction, Bundle bundle) {
            CustomAction customAction2 = customAction;
            Bundle args = bundle;
            CustomAction customAction3 = customAction2;
            Bundle bundle2 = args;
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, customAction2.getAction(), args);
        }

        public void sendCustomAction(String str, Bundle bundle) {
            String action = str;
            Bundle args = bundle;
            String str2 = action;
            Bundle bundle2 = args;
            android.support.p000v4.media.session.MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, action, args);
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControlsApi23 */
    static class TransportControlsApi23 extends TransportControlsApi21 {
        public TransportControlsApi23(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            super(controlsObj);
        }

        public void playFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle extras = bundle;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            android.support.p000v4.media.session.MediaControllerCompatApi23.TransportControls.playFromUri(this.mControlsObj, uri2, extras);
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControlsApi24 */
    static class TransportControlsApi24 extends TransportControlsApi23 {
        public TransportControlsApi24(Object obj) {
            Object controlsObj = obj;
            Object obj2 = controlsObj;
            super(controlsObj);
        }

        public void prepare() {
            android.support.p000v4.media.session.MediaControllerCompatApi24.TransportControls.prepare(this.mControlsObj);
        }

        public void prepareFromMediaId(String str, Bundle bundle) {
            String mediaId = str;
            Bundle extras = bundle;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            android.support.p000v4.media.session.MediaControllerCompatApi24.TransportControls.prepareFromMediaId(this.mControlsObj, mediaId, extras);
        }

        public void prepareFromSearch(String str, Bundle bundle) {
            String query = str;
            Bundle extras = bundle;
            String str2 = query;
            Bundle bundle2 = extras;
            android.support.p000v4.media.session.MediaControllerCompatApi24.TransportControls.prepareFromSearch(this.mControlsObj, query, extras);
        }

        public void prepareFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle extras = bundle;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            android.support.p000v4.media.session.MediaControllerCompatApi24.TransportControls.prepareFromUri(this.mControlsObj, uri2, extras);
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControlsBase */
    static class TransportControlsBase extends TransportControls {
        private IMediaSession mBinder;

        public TransportControlsBase(IMediaSession iMediaSession) {
            IMediaSession binder = iMediaSession;
            IMediaSession iMediaSession2 = binder;
            this.mBinder = binder;
        }

        public void prepare() {
            try {
                this.mBinder.prepare();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in prepare. " + e);
            }
        }

        public void prepareFromMediaId(String str, Bundle bundle) {
            String mediaId = str;
            Bundle extras = bundle;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            try {
                this.mBinder.prepareFromMediaId(mediaId, extras);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in prepareFromMediaId. " + e);
            }
        }

        public void prepareFromSearch(String str, Bundle bundle) {
            String query = str;
            Bundle extras = bundle;
            String str2 = query;
            Bundle bundle2 = extras;
            try {
                this.mBinder.prepareFromSearch(query, extras);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in prepareFromSearch. " + e);
            }
        }

        public void prepareFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle extras = bundle;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            try {
                this.mBinder.prepareFromUri(uri2, extras);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in prepareFromUri. " + e);
            }
        }

        public void play() {
            try {
                this.mBinder.play();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in play. " + e);
            }
        }

        public void playFromMediaId(String str, Bundle bundle) {
            String mediaId = str;
            Bundle extras = bundle;
            String str2 = mediaId;
            Bundle bundle2 = extras;
            try {
                this.mBinder.playFromMediaId(mediaId, extras);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in playFromMediaId. " + e);
            }
        }

        public void playFromSearch(String str, Bundle bundle) {
            String query = str;
            Bundle extras = bundle;
            String str2 = query;
            Bundle bundle2 = extras;
            try {
                this.mBinder.playFromSearch(query, extras);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in playFromSearch. " + e);
            }
        }

        public void playFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle extras = bundle;
            Uri uri3 = uri2;
            Bundle bundle2 = extras;
            try {
                this.mBinder.playFromUri(uri2, extras);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in playFromUri. " + e);
            }
        }

        public void skipToQueueItem(long j) {
            long id = j;
            long j2 = id;
            try {
                this.mBinder.skipToQueueItem(id);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in skipToQueueItem. " + e);
            }
        }

        public void pause() {
            try {
                this.mBinder.pause();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in pause. " + e);
            }
        }

        public void stop() {
            try {
                this.mBinder.stop();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in stop. " + e);
            }
        }

        public void seekTo(long j) {
            long pos = j;
            long j2 = pos;
            try {
                this.mBinder.seekTo(pos);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in seekTo. " + e);
            }
        }

        public void fastForward() {
            try {
                this.mBinder.fastForward();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in fastForward. " + e);
            }
        }

        public void skipToNext() {
            try {
                this.mBinder.next();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in skipToNext. " + e);
            }
        }

        public void rewind() {
            try {
                this.mBinder.rewind();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in rewind. " + e);
            }
        }

        public void skipToPrevious() {
            try {
                this.mBinder.previous();
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in skipToPrevious. " + e);
            }
        }

        public void setRating(RatingCompat ratingCompat) {
            RatingCompat rating = ratingCompat;
            RatingCompat ratingCompat2 = rating;
            try {
                this.mBinder.rate(rating);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in setRating. " + e);
            }
        }

        public void sendCustomAction(CustomAction customAction, Bundle bundle) {
            CustomAction customAction2 = customAction;
            Bundle args = bundle;
            CustomAction customAction3 = customAction2;
            Bundle bundle2 = args;
            sendCustomAction(customAction2.getAction(), args);
        }

        public void sendCustomAction(String str, Bundle bundle) {
            String action = str;
            Bundle args = bundle;
            String str2 = action;
            Bundle bundle2 = args;
            try {
                this.mBinder.sendCustomAction(action, args);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                int e2 = Log.e(MediaControllerCompat.TAG, "Dead object in sendCustomAction. " + e);
            }
        }
    }

    public static void setMediaController(Activity activity, MediaControllerCompat mediaControllerCompat) {
        Activity activity2 = activity;
        MediaControllerCompat mediaController = mediaControllerCompat;
        Activity activity3 = activity2;
        MediaControllerCompat mediaControllerCompat2 = mediaController;
        if (activity2 instanceof SupportActivity) {
            ((SupportActivity) activity2).putExtraData(new MediaControllerExtraData(mediaController));
        }
        if (VERSION.SDK_INT >= 21) {
            Object controllerObj = null;
            if (mediaController != null) {
                controllerObj = MediaControllerCompatApi21.fromToken(activity2, mediaController.getSessionToken().getToken());
            }
            MediaControllerCompatApi21.setMediaController(activity2, controllerObj);
        }
    }

    public static MediaControllerCompat getMediaController(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        if (!(activity2 instanceof SupportActivity)) {
            if (VERSION.SDK_INT >= 21) {
                Object mediaController = MediaControllerCompatApi21.getMediaController(activity2);
                Object controllerObj = mediaController;
                if (mediaController == null) {
                    return null;
                }
                try {
                    return new MediaControllerCompat((Context) activity2, Token.fromToken(MediaControllerCompatApi21.getSessionToken(controllerObj)));
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int e2 = Log.e(TAG, "Dead object in getMediaController. " + e);
                }
            }
            return null;
        }
        MediaControllerExtraData mediaControllerExtraData = (MediaControllerExtraData) ((SupportActivity) activity2).getExtraData(MediaControllerExtraData.class);
        return mediaControllerExtraData == null ? null : mediaControllerExtraData.getMediaController();
    }

    public MediaControllerCompat(Context context, MediaSessionCompat mediaSessionCompat) {
        Context context2 = context;
        MediaSessionCompat session = mediaSessionCompat;
        Context context3 = context2;
        MediaSessionCompat mediaSessionCompat2 = session;
        if (session != null) {
            this.mToken = session.getSessionToken();
            if (VERSION.SDK_INT >= 24) {
                this.mImpl = new MediaControllerImplApi24(context2, session);
            } else if (VERSION.SDK_INT >= 23) {
                this.mImpl = new MediaControllerImplApi23(context2, session);
            } else if (VERSION.SDK_INT < 21) {
                this.mImpl = new MediaControllerImplBase(this.mToken);
            } else {
                this.mImpl = new MediaControllerImplApi21(context2, session);
            }
        } else {
            throw new IllegalArgumentException("session must not be null");
        }
    }

    public MediaControllerCompat(Context context, Token token) throws RemoteException {
        Context context2 = context;
        Token sessionToken = token;
        Context context3 = context2;
        Token token2 = sessionToken;
        if (sessionToken != null) {
            this.mToken = sessionToken;
            if (VERSION.SDK_INT >= 24) {
                this.mImpl = new MediaControllerImplApi24(context2, sessionToken);
            } else if (VERSION.SDK_INT >= 23) {
                this.mImpl = new MediaControllerImplApi23(context2, sessionToken);
            } else if (VERSION.SDK_INT < 21) {
                this.mImpl = new MediaControllerImplBase(this.mToken);
            } else {
                this.mImpl = new MediaControllerImplApi21(context2, sessionToken);
            }
        } else {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
    }

    public TransportControls getTransportControls() {
        return this.mImpl.getTransportControls();
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        KeyEvent keyEvent2 = keyEvent;
        KeyEvent keyEvent3 = keyEvent2;
        if (keyEvent2 != null) {
            return this.mImpl.dispatchMediaButtonEvent(keyEvent2);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public PlaybackStateCompat getPlaybackState() {
        return this.mImpl.getPlaybackState();
    }

    public MediaMetadataCompat getMetadata() {
        return this.mImpl.getMetadata();
    }

    public List<QueueItem> getQueue() {
        return this.mImpl.getQueue();
    }

    public CharSequence getQueueTitle() {
        return this.mImpl.getQueueTitle();
    }

    public Bundle getExtras() {
        return this.mImpl.getExtras();
    }

    public int getRatingType() {
        return this.mImpl.getRatingType();
    }

    public long getFlags() {
        return this.mImpl.getFlags();
    }

    public PlaybackInfo getPlaybackInfo() {
        return this.mImpl.getPlaybackInfo();
    }

    public PendingIntent getSessionActivity() {
        return this.mImpl.getSessionActivity();
    }

    public Token getSessionToken() {
        return this.mToken;
    }

    public void setVolumeTo(int i, int i2) {
        int value = i;
        int flags = i2;
        int i3 = value;
        int i4 = flags;
        this.mImpl.setVolumeTo(value, flags);
    }

    public void adjustVolume(int i, int i2) {
        int direction = i;
        int flags = i2;
        int i3 = direction;
        int i4 = flags;
        this.mImpl.adjustVolume(direction, flags);
    }

    public void registerCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        registerCallback(callback2, null);
    }

    public void registerCallback(Callback callback, Handler handler) {
        Callback callback2 = callback;
        Handler handler2 = handler;
        Callback callback3 = callback2;
        Handler handler3 = handler2;
        if (callback2 != null) {
            if (handler2 == null) {
                handler3 = new Handler();
            }
            this.mImpl.registerCallback(callback2, handler3);
            return;
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    public void unregisterCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        if (callback2 != null) {
            this.mImpl.unregisterCallback(callback2);
            return;
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    public void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        String command = str;
        Bundle params = bundle;
        ResultReceiver cb = resultReceiver;
        String str2 = command;
        Bundle bundle2 = params;
        ResultReceiver resultReceiver2 = cb;
        if (!TextUtils.isEmpty(command)) {
            this.mImpl.sendCommand(command, params, cb);
            return;
        }
        throw new IllegalArgumentException("command cannot be null or empty");
    }

    public String getPackageName() {
        return this.mImpl.getPackageName();
    }

    public Object getMediaController() {
        return this.mImpl.getMediaController();
    }
}
