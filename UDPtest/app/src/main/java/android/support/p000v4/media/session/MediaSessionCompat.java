package android.support.p000v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.BundleCompat;
import android.support.p000v4.media.MediaDescriptionCompat;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.MediaMetadataCompat.Builder;
import android.support.p000v4.media.RatingCompat;
import android.support.p000v4.media.VolumeProviderCompat;
import android.support.p000v4.media.session.IMediaSession.Stub;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: android.support.v4.media.session.MediaSessionCompat */
public class MediaSessionCompat {
    static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
    static final String ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID";
    static final String ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY";
    static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
    static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
    static final String ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE";
    static final String ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID";
    static final String ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH";
    static final String ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI";
    static final String EXTRA_BINDER = "android.support.v4.media.session.EXTRA_BINDER";
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    private static final int MAX_BITMAP_SIZE_IN_DP = 320;
    static final String TAG = "MediaSessionCompat";
    static int sMaxBitmapSize;
    private final ArrayList<OnActiveChangeListener> mActiveListeners;
    private final MediaControllerCompat mController;
    private final MediaSessionImpl mImpl;

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$Callback */
    public static abstract class Callback {
        final Object mCallbackObj;
        WeakReference<MediaSessionImpl> mSessionImpl;

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$Callback$StubApi21 */
        private class StubApi21 implements Callback {
            final /* synthetic */ Callback this$0;

            StubApi21(Callback callback) {
                this.this$0 = callback;
            }

            public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
                String command = str;
                Bundle extras = bundle;
                ResultReceiver cb = resultReceiver;
                String str2 = command;
                Bundle bundle2 = extras;
                ResultReceiver resultReceiver2 = cb;
                if (!command.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER")) {
                    this.this$0.onCommand(command, extras, cb);
                    return;
                }
                MediaSessionImplApi21 mediaSessionImplApi21 = (MediaSessionImplApi21) this.this$0.mSessionImpl.get();
                MediaSessionImplApi21 impl = mediaSessionImplApi21;
                if (mediaSessionImplApi21 != null) {
                    Bundle bundle3 = new Bundle();
                    Bundle result = bundle3;
                    BundleCompat.putBinder(bundle3, MediaSessionCompat.EXTRA_BINDER, impl.getExtraSessionBinder());
                    cb.send(0, result);
                }
            }

            public boolean onMediaButtonEvent(Intent intent) {
                Intent mediaButtonIntent = intent;
                Intent intent2 = mediaButtonIntent;
                return this.this$0.onMediaButtonEvent(mediaButtonIntent);
            }

            public void onPlay() {
                this.this$0.onPlay();
            }

            public void onPlayFromMediaId(String str, Bundle bundle) {
                String mediaId = str;
                Bundle extras = bundle;
                String str2 = mediaId;
                Bundle bundle2 = extras;
                this.this$0.onPlayFromMediaId(mediaId, extras);
            }

            public void onPlayFromSearch(String str, Bundle bundle) {
                String search = str;
                Bundle extras = bundle;
                String str2 = search;
                Bundle bundle2 = extras;
                this.this$0.onPlayFromSearch(search, extras);
            }

            public void onSkipToQueueItem(long j) {
                long id = j;
                long j2 = id;
                this.this$0.onSkipToQueueItem(id);
            }

            public void onPause() {
                this.this$0.onPause();
            }

            public void onSkipToNext() {
                this.this$0.onSkipToNext();
            }

            public void onSkipToPrevious() {
                this.this$0.onSkipToPrevious();
            }

            public void onFastForward() {
                this.this$0.onFastForward();
            }

            public void onRewind() {
                this.this$0.onRewind();
            }

            public void onStop() {
                this.this$0.onStop();
            }

            public void onSeekTo(long j) {
                long pos = j;
                long j2 = pos;
                this.this$0.onSeekTo(pos);
            }

            public void onSetRating(Object obj) {
                Object ratingObj = obj;
                Object obj2 = ratingObj;
                this.this$0.onSetRating(RatingCompat.fromRating(ratingObj));
            }

            public void onCustomAction(String str, Bundle bundle) {
                String action = str;
                Bundle extras = bundle;
                String str2 = action;
                Bundle bundle2 = extras;
                if (action.equals(MediaSessionCompat.ACTION_PLAY_FROM_URI)) {
                    this.this$0.onPlayFromUri((Uri) extras.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), (Bundle) extras.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS));
                } else if (action.equals(MediaSessionCompat.ACTION_PREPARE)) {
                    this.this$0.onPrepare();
                } else if (action.equals(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
                    this.this$0.onPrepareFromMediaId(extras.getString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID), extras.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS));
                } else if (action.equals(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH)) {
                    this.this$0.onPrepareFromSearch(extras.getString(MediaSessionCompat.ACTION_ARGUMENT_QUERY), extras.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS));
                } else if (!action.equals(MediaSessionCompat.ACTION_PREPARE_FROM_URI)) {
                    this.this$0.onCustomAction(action, extras);
                } else {
                    this.this$0.onPrepareFromUri((Uri) extras.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), extras.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS));
                }
            }
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$Callback$StubApi23 */
        private class StubApi23 extends StubApi21 implements android.support.p000v4.media.session.MediaSessionCompatApi23.Callback {
            final /* synthetic */ Callback this$0;

            StubApi23(Callback callback) {
                Callback callback2 = callback;
                this.this$0 = callback2;
                super(callback2);
            }

            public void onPlayFromUri(Uri uri, Bundle bundle) {
                Uri uri2 = uri;
                Bundle extras = bundle;
                Uri uri3 = uri2;
                Bundle bundle2 = extras;
                this.this$0.onPlayFromUri(uri2, extras);
            }
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$Callback$StubApi24 */
        private class StubApi24 extends StubApi23 implements android.support.p000v4.media.session.MediaSessionCompatApi24.Callback {
            final /* synthetic */ Callback this$0;

            StubApi24(Callback callback) {
                Callback callback2 = callback;
                this.this$0 = callback2;
                super(callback2);
            }

            public void onPrepare() {
                this.this$0.onPrepare();
            }

            public void onPrepareFromMediaId(String str, Bundle bundle) {
                String mediaId = str;
                Bundle extras = bundle;
                String str2 = mediaId;
                Bundle bundle2 = extras;
                this.this$0.onPrepareFromMediaId(mediaId, extras);
            }

            public void onPrepareFromSearch(String str, Bundle bundle) {
                String query = str;
                Bundle extras = bundle;
                String str2 = query;
                Bundle bundle2 = extras;
                this.this$0.onPrepareFromSearch(query, extras);
            }

            public void onPrepareFromUri(Uri uri, Bundle bundle) {
                Uri uri2 = uri;
                Bundle extras = bundle;
                Uri uri3 = uri2;
                Bundle bundle2 = extras;
                this.this$0.onPrepareFromUri(uri2, extras);
            }
        }

        public Callback() {
            if (VERSION.SDK_INT >= 24) {
                this.mCallbackObj = MediaSessionCompatApi24.createCallback(new StubApi24(this));
            } else if (VERSION.SDK_INT >= 23) {
                this.mCallbackObj = MediaSessionCompatApi23.createCallback(new StubApi23(this));
            } else if (VERSION.SDK_INT < 21) {
                this.mCallbackObj = null;
            } else {
                this.mCallbackObj = MediaSessionCompatApi21.createCallback(new StubApi21(this));
            }
        }

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            String str2 = str;
            Bundle bundle2 = bundle;
            ResultReceiver resultReceiver2 = resultReceiver;
        }

        public boolean onMediaButtonEvent(Intent intent) {
            Intent intent2 = intent;
            return false;
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String str, Bundle bundle) {
            String str2 = str;
            Bundle bundle2 = bundle;
        }

        public void onPrepareFromSearch(String str, Bundle bundle) {
            String str2 = str;
            Bundle bundle2 = bundle;
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle bundle2 = bundle;
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
            String str2 = str;
            Bundle bundle2 = bundle;
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
            String str2 = str;
            Bundle bundle2 = bundle;
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
            Uri uri2 = uri;
            Bundle bundle2 = bundle;
        }

        public void onSkipToQueueItem(long j) {
            long j2 = j;
        }

        public void onPause() {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onFastForward() {
        }

        public void onRewind() {
        }

        public void onStop() {
        }

        public void onSeekTo(long j) {
            long j2 = j;
        }

        public void onSetRating(RatingCompat ratingCompat) {
            RatingCompat ratingCompat2 = ratingCompat;
        }

        public void onCustomAction(String str, Bundle bundle) {
            String str2 = str;
            Bundle bundle2 = bundle;
        }
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImpl */
    interface MediaSessionImpl {
        String getCallingPackage();

        Object getMediaSession();

        Object getRemoteControlClient();

        Token getSessionToken();

        boolean isActive();

        void release();

        void sendSessionEvent(String str, Bundle bundle);

        void setActive(boolean z);

        void setCallback(Callback callback, Handler handler);

        void setExtras(Bundle bundle);

        void setFlags(int i);

        void setMediaButtonReceiver(PendingIntent pendingIntent);

        void setMetadata(MediaMetadataCompat mediaMetadataCompat);

        void setPlaybackState(PlaybackStateCompat playbackStateCompat);

        void setPlaybackToLocal(int i);

        void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat);

        void setQueue(List<QueueItem> list);

        void setQueueTitle(CharSequence charSequence);

        void setRatingType(int i);

        void setSessionActivity(PendingIntent pendingIntent);
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi21 */
    static class MediaSessionImplApi21 implements MediaSessionImpl {
        private boolean mDestroyed = false;
        private final RemoteCallbackList<IMediaControllerCallback> mExtraControllerCallbacks = new RemoteCallbackList<>();
        private ExtraSession mExtraSessionBinder;
        private PlaybackStateCompat mPlaybackState;
        int mRatingType;
        private final Object mSessionObj;
        private final Token mToken;

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi21$ExtraSession */
        class ExtraSession extends Stub {
            final /* synthetic */ MediaSessionImplApi21 this$0;

            ExtraSession(MediaSessionImplApi21 mediaSessionImplApi21) {
                MediaSessionImplApi21 this$02 = mediaSessionImplApi21;
                MediaSessionImplApi21 mediaSessionImplApi212 = this$02;
                this.this$0 = this$02;
            }

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                String str2 = str;
                Bundle bundle2 = bundle;
                ResultReceiverWrapper resultReceiverWrapper2 = resultReceiverWrapper;
                throw new AssertionError();
            }

            public boolean sendMediaButton(KeyEvent keyEvent) {
                KeyEvent keyEvent2 = keyEvent;
                throw new AssertionError();
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                IMediaControllerCallback cb = iMediaControllerCallback;
                IMediaControllerCallback iMediaControllerCallback2 = cb;
                if (!MediaSessionImplApi21.access$100(this.this$0)) {
                    boolean register = MediaSessionImplApi21.access$200(this.this$0).register(cb);
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                IMediaControllerCallback cb = iMediaControllerCallback;
                IMediaControllerCallback iMediaControllerCallback2 = cb;
                boolean unregister = MediaSessionImplApi21.access$200(this.this$0).unregister(cb);
            }

            public String getPackageName() {
                throw new AssertionError();
            }

            public String getTag() {
                throw new AssertionError();
            }

            public PendingIntent getLaunchPendingIntent() {
                throw new AssertionError();
            }

            public long getFlags() {
                throw new AssertionError();
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                throw new AssertionError();
            }

            public void adjustVolume(int i, int i2, String str) {
                int i3 = i;
                int i4 = i2;
                String str2 = str;
                throw new AssertionError();
            }

            public void setVolumeTo(int i, int i2, String str) {
                int i3 = i;
                int i4 = i2;
                String str2 = str;
                throw new AssertionError();
            }

            public void prepare() throws RemoteException {
                throw new AssertionError();
            }

            public void prepareFromMediaId(String str, Bundle bundle) throws RemoteException {
                String str2 = str;
                Bundle bundle2 = bundle;
                throw new AssertionError();
            }

            public void prepareFromSearch(String str, Bundle bundle) throws RemoteException {
                String str2 = str;
                Bundle bundle2 = bundle;
                throw new AssertionError();
            }

            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                Uri uri2 = uri;
                Bundle bundle2 = bundle;
                throw new AssertionError();
            }

            public void play() throws RemoteException {
                throw new AssertionError();
            }

            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                String str2 = str;
                Bundle bundle2 = bundle;
                throw new AssertionError();
            }

            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                String str2 = str;
                Bundle bundle2 = bundle;
                throw new AssertionError();
            }

            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                Uri uri2 = uri;
                Bundle bundle2 = bundle;
                throw new AssertionError();
            }

            public void skipToQueueItem(long j) {
                long j2 = j;
                throw new AssertionError();
            }

            public void pause() throws RemoteException {
                throw new AssertionError();
            }

            public void stop() throws RemoteException {
                throw new AssertionError();
            }

            public void next() throws RemoteException {
                throw new AssertionError();
            }

            public void previous() throws RemoteException {
                throw new AssertionError();
            }

            public void fastForward() throws RemoteException {
                throw new AssertionError();
            }

            public void rewind() throws RemoteException {
                throw new AssertionError();
            }

            public void seekTo(long j) throws RemoteException {
                long j2 = j;
                throw new AssertionError();
            }

            public void rate(RatingCompat ratingCompat) throws RemoteException {
                RatingCompat ratingCompat2 = ratingCompat;
                throw new AssertionError();
            }

            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                String str2 = str;
                Bundle bundle2 = bundle;
                throw new AssertionError();
            }

            public MediaMetadataCompat getMetadata() {
                throw new AssertionError();
            }

            public PlaybackStateCompat getPlaybackState() {
                return MediaSessionImplApi21.access$300(this.this$0);
            }

            public List<QueueItem> getQueue() {
                return null;
            }

            public CharSequence getQueueTitle() {
                throw new AssertionError();
            }

            public Bundle getExtras() {
                throw new AssertionError();
            }

            public int getRatingType() {
                return this.this$0.mRatingType;
            }

            public boolean isTransportControlEnabled() {
                throw new AssertionError();
            }
        }

        static /* synthetic */ boolean access$100(MediaSessionImplApi21 mediaSessionImplApi21) {
            MediaSessionImplApi21 x0 = mediaSessionImplApi21;
            MediaSessionImplApi21 mediaSessionImplApi212 = x0;
            return x0.mDestroyed;
        }

        static /* synthetic */ RemoteCallbackList access$200(MediaSessionImplApi21 mediaSessionImplApi21) {
            MediaSessionImplApi21 x0 = mediaSessionImplApi21;
            MediaSessionImplApi21 mediaSessionImplApi212 = x0;
            return x0.mExtraControllerCallbacks;
        }

        static /* synthetic */ PlaybackStateCompat access$300(MediaSessionImplApi21 mediaSessionImplApi21) {
            MediaSessionImplApi21 x0 = mediaSessionImplApi21;
            MediaSessionImplApi21 mediaSessionImplApi212 = x0;
            return x0.mPlaybackState;
        }

        public MediaSessionImplApi21(Context context, String str) {
            Context context2 = context;
            String tag = str;
            Context context3 = context2;
            String str2 = tag;
            this.mSessionObj = MediaSessionCompatApi21.createSession(context2, tag);
            this.mToken = new Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj));
        }

        public MediaSessionImplApi21(Object obj) {
            Object mediaSession = obj;
            Object obj2 = mediaSession;
            this.mSessionObj = MediaSessionCompatApi21.verifySession(mediaSession);
            this.mToken = new Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj));
        }

        public void setCallback(Callback callback, Handler handler) {
            Callback callback2 = callback;
            Handler handler2 = handler;
            Callback callback3 = callback2;
            Handler handler3 = handler2;
            MediaSessionCompatApi21.setCallback(this.mSessionObj, callback2 != null ? callback2.mCallbackObj : null, handler2);
            callback2.mSessionImpl = new WeakReference<>(this);
        }

        public void setFlags(int i) {
            int flags = i;
            int i2 = flags;
            MediaSessionCompatApi21.setFlags(this.mSessionObj, flags);
        }

        public void setPlaybackToLocal(int i) {
            int stream = i;
            int i2 = stream;
            MediaSessionCompatApi21.setPlaybackToLocal(this.mSessionObj, stream);
        }

        public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            VolumeProviderCompat volumeProvider = volumeProviderCompat;
            VolumeProviderCompat volumeProviderCompat2 = volumeProvider;
            MediaSessionCompatApi21.setPlaybackToRemote(this.mSessionObj, volumeProvider.getVolumeProvider());
        }

        public void setActive(boolean z) {
            MediaSessionCompatApi21.setActive(this.mSessionObj, z);
        }

        public boolean isActive() {
            return MediaSessionCompatApi21.isActive(this.mSessionObj);
        }

        public void sendSessionEvent(String str, Bundle bundle) {
            String event = str;
            Bundle extras = bundle;
            String str2 = event;
            Bundle bundle2 = extras;
            if (VERSION.SDK_INT < 23) {
                int beginBroadcast = this.mExtraControllerCallbacks.beginBroadcast();
                int i = beginBroadcast;
                for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                    IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mExtraControllerCallbacks.getBroadcastItem(i2);
                    IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                    try {
                        iMediaControllerCallback.onEvent(event, extras);
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast();
            }
            MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, event, extras);
        }

        public void release() {
            this.mDestroyed = true;
            MediaSessionCompatApi21.release(this.mSessionObj);
        }

        public Token getSessionToken() {
            return this.mToken;
        }

        public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
            Object obj;
            PlaybackStateCompat state = playbackStateCompat;
            PlaybackStateCompat playbackStateCompat2 = state;
            if (VERSION.SDK_INT < 22) {
                this.mPlaybackState = state;
                int beginBroadcast = this.mExtraControllerCallbacks.beginBroadcast();
                int i = beginBroadcast;
                for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                    IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mExtraControllerCallbacks.getBroadcastItem(i2);
                    IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                    try {
                        iMediaControllerCallback.onPlaybackStateChanged(state);
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast();
            }
            Object obj2 = this.mSessionObj;
            if (state != null) {
                obj = state.getPlaybackState();
            } else {
                obj = null;
            }
            MediaSessionCompatApi21.setPlaybackState(obj2, obj);
        }

        public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
            Object obj;
            MediaMetadataCompat metadata = mediaMetadataCompat;
            MediaMetadataCompat mediaMetadataCompat2 = metadata;
            Object obj2 = this.mSessionObj;
            if (metadata != null) {
                obj = metadata.getMediaMetadata();
            } else {
                obj = null;
            }
            MediaSessionCompatApi21.setMetadata(obj2, obj);
        }

        public void setSessionActivity(PendingIntent pendingIntent) {
            PendingIntent pi = pendingIntent;
            PendingIntent pendingIntent2 = pi;
            MediaSessionCompatApi21.setSessionActivity(this.mSessionObj, pi);
        }

        public void setMediaButtonReceiver(PendingIntent pendingIntent) {
            PendingIntent mbr = pendingIntent;
            PendingIntent pendingIntent2 = mbr;
            MediaSessionCompatApi21.setMediaButtonReceiver(this.mSessionObj, mbr);
        }

        public void setQueue(List<QueueItem> list) {
            List<QueueItem> queue = list;
            List<QueueItem> list2 = queue;
            ArrayList arrayList = null;
            if (queue != null) {
                arrayList = new ArrayList();
                for (QueueItem queueItem : queue) {
                    boolean add = arrayList.add(queueItem.getQueueItem());
                }
            }
            MediaSessionCompatApi21.setQueue(this.mSessionObj, arrayList);
        }

        public void setQueueTitle(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            MediaSessionCompatApi21.setQueueTitle(this.mSessionObj, title);
        }

        public void setRatingType(int i) {
            int type = i;
            int i2 = type;
            if (VERSION.SDK_INT >= 22) {
                MediaSessionCompatApi22.setRatingType(this.mSessionObj, type);
            } else {
                this.mRatingType = type;
            }
        }

        public void setExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            MediaSessionCompatApi21.setExtras(this.mSessionObj, extras);
        }

        public Object getMediaSession() {
            return this.mSessionObj;
        }

        public Object getRemoteControlClient() {
            return null;
        }

        public String getCallingPackage() {
            if (VERSION.SDK_INT >= 24) {
                return MediaSessionCompatApi24.getCallingPackage(this.mSessionObj);
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public ExtraSession getExtraSessionBinder() {
            if (this.mExtraSessionBinder == null) {
                this.mExtraSessionBinder = new ExtraSession(this);
            }
            return this.mExtraSessionBinder;
        }
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplBase */
    static class MediaSessionImplBase implements MediaSessionImpl {
        final AudioManager mAudioManager;
        volatile Callback mCallback;
        private final Context mContext;
        final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks = new RemoteCallbackList<>();
        boolean mDestroyed = false;
        Bundle mExtras;
        int mFlags;
        private MessageHandler mHandler;
        private boolean mIsActive = false;
        private boolean mIsMbrRegistered = false;
        private boolean mIsRccRegistered = false;
        int mLocalStream;
        final Object mLock = new Object();
        private final ComponentName mMediaButtonReceiverComponentName;
        private final PendingIntent mMediaButtonReceiverIntent;
        MediaMetadataCompat mMetadata;
        final String mPackageName;
        List<QueueItem> mQueue;
        CharSequence mQueueTitle;
        int mRatingType;
        private final Object mRccObj;
        PendingIntent mSessionActivity;
        PlaybackStateCompat mState;
        private final MediaSessionStub mStub;
        final String mTag;
        private final Token mToken;
        private android.support.p000v4.media.VolumeProviderCompat.Callback mVolumeCallback = new android.support.p000v4.media.VolumeProviderCompat.Callback(this) {
            final /* synthetic */ MediaSessionImplBase this$0;

            {
                MediaSessionImplBase this$02 = r5;
                MediaSessionImplBase mediaSessionImplBase = this$02;
                this.this$0 = this$02;
            }

            public void onVolumeChanged(VolumeProviderCompat volumeProviderCompat) {
                VolumeProviderCompat volumeProvider = volumeProviderCompat;
                VolumeProviderCompat volumeProviderCompat2 = volumeProvider;
                if (this.this$0.mVolumeProvider == volumeProvider) {
                    ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(this.this$0.mVolumeType, this.this$0.mLocalStream, volumeProvider.getVolumeControl(), volumeProvider.getMaxVolume(), volumeProvider.getCurrentVolume());
                    this.this$0.sendVolumeInfoChanged(parcelableVolumeInfo);
                }
            }
        };
        VolumeProviderCompat mVolumeProvider;
        int mVolumeType;

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplBase$Command */
        private static final class Command {
            public final String command;
            public final Bundle extras;
            public final ResultReceiver stub;

            public Command(String str, Bundle bundle, ResultReceiver resultReceiver) {
                String command2 = str;
                Bundle extras2 = bundle;
                ResultReceiver stub2 = resultReceiver;
                String str2 = command2;
                Bundle bundle2 = extras2;
                ResultReceiver resultReceiver2 = stub2;
                this.command = command2;
                this.extras = extras2;
                this.stub = stub2;
            }
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplBase$MediaSessionStub */
        class MediaSessionStub extends Stub {
            final /* synthetic */ MediaSessionImplBase this$0;

            MediaSessionStub(MediaSessionImplBase mediaSessionImplBase) {
                MediaSessionImplBase this$02 = mediaSessionImplBase;
                MediaSessionImplBase mediaSessionImplBase2 = this$02;
                this.this$0 = this$02;
            }

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                String command = str;
                Bundle args = bundle;
                ResultReceiverWrapper cb = resultReceiverWrapper;
                String str2 = command;
                Bundle bundle2 = args;
                ResultReceiverWrapper resultReceiverWrapper2 = cb;
                this.this$0.postToHandler(1, new Command(command, args, ResultReceiverWrapper.access$000(cb)));
            }

            public boolean sendMediaButton(KeyEvent keyEvent) {
                KeyEvent mediaButton = keyEvent;
                KeyEvent keyEvent2 = mediaButton;
                boolean handlesMediaButtons = (this.this$0.mFlags & 1) != 0;
                if (handlesMediaButtons) {
                    this.this$0.postToHandler(21, mediaButton);
                }
                return handlesMediaButtons;
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                IMediaControllerCallback cb = iMediaControllerCallback;
                IMediaControllerCallback iMediaControllerCallback2 = cb;
                if (!this.this$0.mDestroyed) {
                    boolean register = this.this$0.mControllerCallbacks.register(cb);
                } else {
                    try {
                        cb.onSessionDestroyed();
                    } catch (Exception e) {
                    }
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                IMediaControllerCallback cb = iMediaControllerCallback;
                IMediaControllerCallback iMediaControllerCallback2 = cb;
                boolean unregister = this.this$0.mControllerCallbacks.unregister(cb);
            }

            public String getPackageName() {
                return this.this$0.mPackageName;
            }

            public String getTag() {
                return this.this$0.mTag;
            }

            public PendingIntent getLaunchPendingIntent() {
                Object obj = this.this$0.mLock;
                PendingIntent pendingIntent = obj;
                synchronized (obj) {
                    try {
                        pendingIntent = this.this$0.mSessionActivity;
                        return pendingIntent;
                    } finally {
                        PendingIntent pendingIntent2 = pendingIntent;
                    }
                }
            }

            public long getFlags() {
                Object obj = this.this$0.mLock;
                Object obj2 = obj;
                synchronized (obj) {
                    try {
                        long j = (long) this.this$0.mFlags;
                        return j;
                    } catch (Throwable th) {
                        Object obj3 = obj2;
                        throw th;
                    }
                }
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                int current;
                int controlType;
                int max;
                int current2;
                Object obj = this.this$0.mLock;
                int i = obj;
                synchronized (obj) {
                    try {
                        int volumeType = this.this$0.mVolumeType;
                        int stream = this.this$0.mLocalStream;
                        VolumeProviderCompat volumeProviderCompat = this.this$0.mVolumeProvider;
                        VolumeProviderCompat vp = volumeProviderCompat;
                        if (volumeType != 2) {
                            controlType = 2;
                            max = this.this$0.mAudioManager.getStreamMaxVolume(stream);
                            i = this.this$0.mAudioManager.getStreamVolume(stream);
                        } else {
                            controlType = volumeProviderCompat.getVolumeControl();
                            max = vp.getMaxVolume();
                            current2 = vp.getCurrentVolume();
                        }
                        ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(volumeType, stream, controlType, max, current2);
                        return parcelableVolumeInfo;
                    } finally {
                        current = i;
                    }
                }
            }

            public void adjustVolume(int i, int i2, String str) {
                int direction = i;
                int flags = i2;
                int i3 = direction;
                int i4 = flags;
                String str2 = str;
                this.this$0.adjustVolume(direction, flags);
            }

            public void setVolumeTo(int i, int i2, String str) {
                int value = i;
                int flags = i2;
                int i3 = value;
                int i4 = flags;
                String str2 = str;
                this.this$0.setVolumeTo(value, flags);
            }

            public void prepare() throws RemoteException {
                this.this$0.postToHandler(3);
            }

            public void prepareFromMediaId(String str, Bundle bundle) throws RemoteException {
                String mediaId = str;
                Bundle extras = bundle;
                String str2 = mediaId;
                Bundle bundle2 = extras;
                this.this$0.postToHandler(4, mediaId, extras);
            }

            public void prepareFromSearch(String str, Bundle bundle) throws RemoteException {
                String query = str;
                Bundle extras = bundle;
                String str2 = query;
                Bundle bundle2 = extras;
                this.this$0.postToHandler(5, query, extras);
            }

            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                Uri uri2 = uri;
                Bundle extras = bundle;
                Uri uri3 = uri2;
                Bundle bundle2 = extras;
                this.this$0.postToHandler(6, uri2, extras);
            }

            public void play() throws RemoteException {
                this.this$0.postToHandler(7);
            }

            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                String mediaId = str;
                Bundle extras = bundle;
                String str2 = mediaId;
                Bundle bundle2 = extras;
                this.this$0.postToHandler(8, mediaId, extras);
            }

            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                String query = str;
                Bundle extras = bundle;
                String str2 = query;
                Bundle bundle2 = extras;
                this.this$0.postToHandler(9, query, extras);
            }

            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                Uri uri2 = uri;
                Bundle extras = bundle;
                Uri uri3 = uri2;
                Bundle bundle2 = extras;
                this.this$0.postToHandler(10, uri2, extras);
            }

            public void skipToQueueItem(long j) {
                long id = j;
                long j2 = id;
                this.this$0.postToHandler(11, Long.valueOf(id));
            }

            public void pause() throws RemoteException {
                this.this$0.postToHandler(12);
            }

            public void stop() throws RemoteException {
                this.this$0.postToHandler(13);
            }

            public void next() throws RemoteException {
                this.this$0.postToHandler(14);
            }

            public void previous() throws RemoteException {
                this.this$0.postToHandler(15);
            }

            public void fastForward() throws RemoteException {
                this.this$0.postToHandler(16);
            }

            public void rewind() throws RemoteException {
                this.this$0.postToHandler(17);
            }

            public void seekTo(long j) throws RemoteException {
                long pos = j;
                long j2 = pos;
                this.this$0.postToHandler(18, Long.valueOf(pos));
            }

            public void rate(RatingCompat ratingCompat) throws RemoteException {
                RatingCompat rating = ratingCompat;
                RatingCompat ratingCompat2 = rating;
                this.this$0.postToHandler(19, rating);
            }

            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                String action = str;
                Bundle args = bundle;
                String str2 = action;
                Bundle bundle2 = args;
                this.this$0.postToHandler(20, action, args);
            }

            public MediaMetadataCompat getMetadata() {
                return this.this$0.mMetadata;
            }

            public PlaybackStateCompat getPlaybackState() {
                return this.this$0.getStateWithUpdatedPosition();
            }

            public List<QueueItem> getQueue() {
                Object obj = this.this$0.mLock;
                List<QueueItem> list = obj;
                synchronized (obj) {
                    try {
                        list = this.this$0.mQueue;
                        return list;
                    } finally {
                        List<QueueItem> list2 = list;
                    }
                }
            }

            public CharSequence getQueueTitle() {
                return this.this$0.mQueueTitle;
            }

            public Bundle getExtras() {
                Object obj = this.this$0.mLock;
                Bundle bundle = obj;
                synchronized (obj) {
                    try {
                        bundle = this.this$0.mExtras;
                        return bundle;
                    } finally {
                        Bundle bundle2 = bundle;
                    }
                }
            }

            public int getRatingType() {
                return this.this$0.mRatingType;
            }

            public boolean isTransportControlEnabled() {
                return (this.this$0.mFlags & 2) != 0;
            }
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplBase$MessageHandler */
        private class MessageHandler extends Handler {
            private static final int KEYCODE_MEDIA_PAUSE = 127;
            private static final int KEYCODE_MEDIA_PLAY = 126;
            private static final int MSG_ADJUST_VOLUME = 2;
            private static final int MSG_COMMAND = 1;
            private static final int MSG_CUSTOM_ACTION = 20;
            private static final int MSG_FAST_FORWARD = 16;
            private static final int MSG_MEDIA_BUTTON = 21;
            private static final int MSG_NEXT = 14;
            private static final int MSG_PAUSE = 12;
            private static final int MSG_PLAY = 7;
            private static final int MSG_PLAY_MEDIA_ID = 8;
            private static final int MSG_PLAY_SEARCH = 9;
            private static final int MSG_PLAY_URI = 10;
            private static final int MSG_PREPARE = 3;
            private static final int MSG_PREPARE_MEDIA_ID = 4;
            private static final int MSG_PREPARE_SEARCH = 5;
            private static final int MSG_PREPARE_URI = 6;
            private static final int MSG_PREVIOUS = 15;
            private static final int MSG_RATE = 19;
            private static final int MSG_REWIND = 17;
            private static final int MSG_SEEK_TO = 18;
            private static final int MSG_SET_VOLUME = 22;
            private static final int MSG_SKIP_TO_ITEM = 11;
            private static final int MSG_STOP = 13;
            final /* synthetic */ MediaSessionImplBase this$0;

            public MessageHandler(MediaSessionImplBase mediaSessionImplBase, Looper looper) {
                Looper looper2 = looper;
                Looper looper3 = looper2;
                this.this$0 = mediaSessionImplBase;
                super(looper2);
            }

            public void post(int i, Object obj, Bundle bundle) {
                int what = i;
                Object obj2 = obj;
                Bundle bundle2 = bundle;
                int i2 = what;
                Object obj3 = obj2;
                Bundle bundle3 = bundle2;
                Message obtainMessage = obtainMessage(what, obj2);
                Message msg = obtainMessage;
                obtainMessage.setData(bundle2);
                msg.sendToTarget();
            }

            public void post(int i, Object obj) {
                int what = i;
                Object obj2 = obj;
                int i2 = what;
                Object obj3 = obj2;
                obtainMessage(what, obj2).sendToTarget();
            }

            public void post(int i) {
                int what = i;
                int i2 = what;
                post(what, null);
            }

            public void post(int i, Object obj, int i2) {
                int what = i;
                Object obj2 = obj;
                int arg1 = i2;
                int i3 = what;
                Object obj3 = obj2;
                int i4 = arg1;
                obtainMessage(what, arg1, 0, obj2).sendToTarget();
            }

            public void handleMessage(Message message) {
                Message msg = message;
                Message message2 = msg;
                Callback callback = this.this$0.mCallback;
                Callback cb = callback;
                if (callback != null) {
                    switch (msg.what) {
                        case 1:
                            Command cmd = (Command) msg.obj;
                            cb.onCommand(cmd.command, cmd.extras, cmd.stub);
                            break;
                        case 2:
                            this.this$0.adjustVolume(((Integer) msg.obj).intValue(), 0);
                            break;
                        case 3:
                            cb.onPrepare();
                            break;
                        case 4:
                            cb.onPrepareFromMediaId((String) msg.obj, msg.getData());
                            break;
                        case 5:
                            cb.onPrepareFromSearch((String) msg.obj, msg.getData());
                            break;
                        case 6:
                            cb.onPrepareFromUri((Uri) msg.obj, msg.getData());
                            break;
                        case 7:
                            cb.onPlay();
                            break;
                        case 8:
                            cb.onPlayFromMediaId((String) msg.obj, msg.getData());
                            break;
                        case 9:
                            cb.onPlayFromSearch((String) msg.obj, msg.getData());
                            break;
                        case 10:
                            cb.onPlayFromUri((Uri) msg.obj, msg.getData());
                            break;
                        case 11:
                            cb.onSkipToQueueItem(((Long) msg.obj).longValue());
                            break;
                        case 12:
                            cb.onPause();
                            break;
                        case 13:
                            cb.onStop();
                            break;
                        case 14:
                            cb.onSkipToNext();
                            break;
                        case 15:
                            cb.onSkipToPrevious();
                            break;
                        case 16:
                            cb.onFastForward();
                            break;
                        case 17:
                            cb.onRewind();
                            break;
                        case 18:
                            cb.onSeekTo(((Long) msg.obj).longValue());
                            break;
                        case 19:
                            cb.onSetRating((RatingCompat) msg.obj);
                            break;
                        case 20:
                            cb.onCustomAction((String) msg.obj, msg.getData());
                            break;
                        case 21:
                            KeyEvent keyEvent = (KeyEvent) msg.obj;
                            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                            Intent intent2 = intent;
                            Intent putExtra = intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                            if (!cb.onMediaButtonEvent(intent2)) {
                                onMediaButtonEvent(keyEvent, cb);
                                break;
                            }
                            break;
                        case 22:
                            this.this$0.setVolumeTo(((Integer) msg.obj).intValue(), 0);
                            break;
                    }
                }
            }

            private void onMediaButtonEvent(KeyEvent keyEvent, Callback callback) {
                KeyEvent ke = keyEvent;
                Callback cb = callback;
                KeyEvent keyEvent2 = ke;
                Callback callback2 = cb;
                if (ke != null && ke.getAction() == 0) {
                    long validActions = this.this$0.mState != null ? this.this$0.mState.getActions() : 0;
                    switch (ke.getKeyCode()) {
                        case 79:
                        case 85:
                            boolean isPlaying = this.this$0.mState != null && this.this$0.mState.getState() == 3;
                            boolean canPlay = (validActions & 516) != 0;
                            boolean canPause = (validActions & 514) != 0;
                            if (!isPlaying || !canPause) {
                                if (!isPlaying && canPlay) {
                                    cb.onPlay();
                                    break;
                                }
                            } else {
                                cb.onPause();
                                break;
                            }
                            break;
                        case 86:
                            if ((validActions & 1) != 0) {
                                cb.onStop();
                                break;
                            }
                            break;
                        case 87:
                            if ((validActions & 32) != 0) {
                                cb.onSkipToNext();
                                break;
                            }
                            break;
                        case 88:
                            if ((validActions & 16) != 0) {
                                cb.onSkipToPrevious();
                                break;
                            }
                            break;
                        case 89:
                            if ((validActions & 8) != 0) {
                                cb.onRewind();
                                break;
                            }
                            break;
                        case 90:
                            if ((validActions & 64) != 0) {
                                cb.onFastForward();
                                break;
                            }
                            break;
                        case 126:
                            if ((validActions & 4) != 0) {
                                cb.onPlay();
                                break;
                            }
                            break;
                        case 127:
                            if ((validActions & 2) != 0) {
                                cb.onPause();
                                break;
                            }
                            break;
                    }
                }
            }
        }

        public MediaSessionImplBase(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            Context context2 = context;
            String tag = str;
            ComponentName mbrComponent = componentName;
            PendingIntent mbrIntent = pendingIntent;
            Context context3 = context2;
            String str2 = tag;
            ComponentName mbrComponent2 = mbrComponent;
            PendingIntent mbrIntent2 = mbrIntent;
            if (mbrComponent == null) {
                ComponentName mediaButtonReceiverComponent = MediaButtonReceiver.getMediaButtonReceiverComponent(context2);
                mbrComponent2 = mediaButtonReceiverComponent;
                if (mediaButtonReceiverComponent == null) {
                    int w = Log.w(MediaSessionCompat.TAG, "Couldn't find a unique registered media button receiver in the given context.");
                }
            }
            if (mbrComponent2 != null && mbrIntent == null) {
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                Intent mediaButtonIntent = intent;
                Intent component = intent.setComponent(mbrComponent2);
                mbrIntent2 = PendingIntent.getBroadcast(context2, 0, mediaButtonIntent, 0);
            }
            if (mbrComponent2 != null) {
                this.mContext = context2;
                this.mPackageName = context2.getPackageName();
                this.mAudioManager = (AudioManager) context2.getSystemService("audio");
                this.mTag = tag;
                this.mMediaButtonReceiverComponentName = mbrComponent2;
                this.mMediaButtonReceiverIntent = mbrIntent2;
                this.mStub = new MediaSessionStub(this);
                this.mToken = new Token(this.mStub);
                this.mRatingType = 0;
                this.mVolumeType = 1;
                this.mLocalStream = 3;
                if (VERSION.SDK_INT < 14) {
                    this.mRccObj = null;
                } else {
                    this.mRccObj = MediaSessionCompatApi14.createRemoteControlClient(mbrIntent2);
                }
            } else {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("MediaButtonReceiver component may not be null.");
                throw illegalArgumentException;
            }
        }

        /* JADX INFO: finally extract failed */
        public void setCallback(Callback callback, Handler handler) {
            Callback callback2 = callback;
            Handler handler2 = handler;
            Callback callback3 = callback2;
            Handler handler3 = handler2;
            this.mCallback = callback2;
            if (callback2 != null) {
                if (handler2 == null) {
                    handler3 = new Handler();
                }
                Object obj = this.mLock;
                Object obj2 = obj;
                synchronized (obj) {
                    try {
                        this.mHandler = new MessageHandler(this, handler3.getLooper());
                        C01682 r11 = new Callback(this) {
                            final /* synthetic */ MediaSessionImplBase this$0;

                            {
                                MediaSessionImplBase this$02 = r5;
                                MediaSessionImplBase mediaSessionImplBase = this$02;
                                this.this$0 = this$02;
                            }

                            public void onSetRating(Object obj) {
                                Object ratingObj = obj;
                                Object obj2 = ratingObj;
                                this.this$0.postToHandler(19, RatingCompat.fromRating(ratingObj));
                            }

                            public void onSeekTo(long j) {
                                long pos = j;
                                long j2 = pos;
                                this.this$0.postToHandler(18, Long.valueOf(pos));
                            }
                        };
                        C01682 r15 = r11;
                        if (VERSION.SDK_INT >= 18) {
                            MediaSessionCompatApi18.setOnPlaybackPositionUpdateListener(this.mRccObj, MediaSessionCompatApi18.createPlaybackPositionUpdateListener(r11));
                        }
                        if (VERSION.SDK_INT >= 19) {
                            MediaSessionCompatApi19.setOnMetadataUpdateListener(this.mRccObj, MediaSessionCompatApi19.createMetadataUpdateListener(r15));
                        }
                    } catch (Throwable th) {
                        while (true) {
                            Object obj3 = obj2;
                            throw th;
                        }
                    }
                }
            } else {
                if (VERSION.SDK_INT >= 18) {
                    MediaSessionCompatApi18.setOnPlaybackPositionUpdateListener(this.mRccObj, null);
                }
                if (VERSION.SDK_INT >= 19) {
                    MediaSessionCompatApi19.setOnMetadataUpdateListener(this.mRccObj, null);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void postToHandler(int i) {
            int what = i;
            int i2 = what;
            postToHandler(what, null);
        }

        /* access modifiers changed from: 0000 */
        public void postToHandler(int i, Object obj) {
            int what = i;
            Object obj2 = obj;
            int i2 = what;
            Object obj3 = obj2;
            postToHandler(what, obj2, null);
        }

        /* access modifiers changed from: 0000 */
        public void postToHandler(int i, Object obj, Bundle bundle) {
            int what = i;
            Object obj2 = obj;
            Bundle extras = bundle;
            int i2 = what;
            Object obj3 = obj2;
            Bundle bundle2 = extras;
            Object obj4 = this.mLock;
            Object obj5 = obj4;
            synchronized (obj4) {
                try {
                    if (this.mHandler != null) {
                        this.mHandler.post(what, obj2, extras);
                    }
                } finally {
                    Object obj6 = obj5;
                }
            }
        }

        public void setFlags(int i) {
            int flags = i;
            int i2 = flags;
            Object obj = this.mLock;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    this.mFlags = flags;
                    boolean update = update();
                } catch (Throwable th) {
                    while (true) {
                        Object obj3 = obj2;
                        throw th;
                    }
                }
            }
        }

        public void setPlaybackToLocal(int i) {
            int i2 = i;
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null);
            }
            this.mVolumeType = 1;
            ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, 2, this.mAudioManager.getStreamMaxVolume(this.mLocalStream), this.mAudioManager.getStreamVolume(this.mLocalStream));
            sendVolumeInfoChanged(parcelableVolumeInfo);
        }

        public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            VolumeProviderCompat volumeProvider = volumeProviderCompat;
            VolumeProviderCompat volumeProviderCompat2 = volumeProvider;
            if (volumeProvider != null) {
                if (this.mVolumeProvider != null) {
                    this.mVolumeProvider.setCallback(null);
                }
                this.mVolumeType = 2;
                this.mVolumeProvider = volumeProvider;
                ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume());
                sendVolumeInfoChanged(parcelableVolumeInfo);
                volumeProvider.setCallback(this.mVolumeCallback);
                return;
            }
            throw new IllegalArgumentException("volumeProvider may not be null");
        }

        public void setActive(boolean z) {
            boolean active = z;
            if (active != this.mIsActive) {
                this.mIsActive = active;
                if (update()) {
                    setMetadata(this.mMetadata);
                    setPlaybackState(this.mState);
                }
            }
        }

        public boolean isActive() {
            return this.mIsActive;
        }

        public void sendSessionEvent(String str, Bundle bundle) {
            String event = str;
            Bundle extras = bundle;
            String str2 = event;
            Bundle bundle2 = extras;
            sendEvent(event, extras);
        }

        public void release() {
            this.mIsActive = false;
            this.mDestroyed = true;
            boolean update = update();
            sendSessionDestroyed();
        }

        public Token getSessionToken() {
            return this.mToken;
        }

        public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
            PlaybackStateCompat state = playbackStateCompat;
            PlaybackStateCompat playbackStateCompat2 = state;
            Object obj = this.mLock;
            boolean z = obj;
            synchronized (obj) {
                try {
                    this.mState = state;
                    sendState(state);
                    if (this.mIsActive) {
                        if (state != null) {
                            if (VERSION.SDK_INT >= 18) {
                                MediaSessionCompatApi18.setState(this.mRccObj, state.getState(), state.getPosition(), state.getPlaybackSpeed(), state.getLastPositionUpdateTime());
                            } else if (VERSION.SDK_INT >= 14) {
                                MediaSessionCompatApi14.setState(this.mRccObj, state.getState());
                            }
                            if (VERSION.SDK_INT >= 19) {
                                MediaSessionCompatApi19.setTransportControlFlags(this.mRccObj, state.getActions());
                            } else if (VERSION.SDK_INT >= 18) {
                                MediaSessionCompatApi18.setTransportControlFlags(this.mRccObj, state.getActions());
                            } else if (VERSION.SDK_INT >= 14) {
                                MediaSessionCompatApi14.setTransportControlFlags(this.mRccObj, state.getActions());
                            }
                        } else if (VERSION.SDK_INT >= 14) {
                            MediaSessionCompatApi14.setState(this.mRccObj, 0);
                            MediaSessionCompatApi14.setTransportControlFlags(this.mRccObj, 0);
                        }
                    }
                } finally {
                    while (true) {
                        boolean z2 = z;
                    }
                }
            }
        }

        public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
            Bundle bundle;
            long j;
            Bundle bundle2;
            MediaMetadataCompat metadata = mediaMetadataCompat;
            MediaMetadataCompat metadata2 = metadata;
            if (metadata != null) {
                metadata2 = new Builder(metadata, MediaSessionCompat.sMaxBitmapSize).build();
            }
            Object obj = this.mLock;
            boolean z = obj;
            synchronized (obj) {
                try {
                    this.mMetadata = metadata2;
                    sendMetadata(metadata2);
                    if (this.mIsActive) {
                        if (VERSION.SDK_INT >= 19) {
                            Object obj2 = this.mRccObj;
                            if (metadata2 != null) {
                                bundle = metadata2.getBundle();
                            } else {
                                bundle = null;
                            }
                            if (this.mState != null) {
                                j = this.mState.getActions();
                            } else {
                                j = 0;
                            }
                            MediaSessionCompatApi19.setMetadata(obj2, bundle, j);
                        } else if (VERSION.SDK_INT >= 14) {
                            Object obj3 = this.mRccObj;
                            if (metadata2 != null) {
                                bundle2 = metadata2.getBundle();
                            } else {
                                bundle2 = null;
                            }
                            MediaSessionCompatApi14.setMetadata(obj3, bundle2);
                        }
                    }
                } finally {
                    while (true) {
                        boolean z2 = z;
                    }
                }
            }
        }

        public void setSessionActivity(PendingIntent pendingIntent) {
            PendingIntent pi = pendingIntent;
            PendingIntent pendingIntent2 = pi;
            Object obj = this.mLock;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    this.mSessionActivity = pi;
                } catch (Throwable th) {
                    Object obj3 = obj2;
                    throw th;
                }
            }
        }

        public void setMediaButtonReceiver(PendingIntent pendingIntent) {
            PendingIntent pendingIntent2 = pendingIntent;
        }

        public void setQueue(List<QueueItem> list) {
            List<QueueItem> queue = list;
            List<QueueItem> list2 = queue;
            this.mQueue = queue;
            sendQueue(queue);
        }

        public void setQueueTitle(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.mQueueTitle = title;
            sendQueueTitle(title);
        }

        public Object getMediaSession() {
            return null;
        }

        public Object getRemoteControlClient() {
            return this.mRccObj;
        }

        public String getCallingPackage() {
            return null;
        }

        public void setRatingType(int i) {
            int type = i;
            int i2 = type;
            this.mRatingType = type;
        }

        public void setExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            this.mExtras = extras;
            sendExtras(extras);
        }

        private boolean update() {
            boolean registeredRcc = false;
            if (!this.mIsActive) {
                if (this.mIsMbrRegistered) {
                    if (VERSION.SDK_INT < 18) {
                        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
                        AudioManager audioManager2 = audioManager;
                        audioManager.unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                    } else {
                        MediaSessionCompatApi18.unregisterMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    }
                    this.mIsMbrRegistered = false;
                }
                if (this.mIsRccRegistered) {
                    MediaSessionCompatApi14.setState(this.mRccObj, 0);
                    MediaSessionCompatApi14.unregisterRemoteControlClient(this.mContext, this.mRccObj);
                    this.mIsRccRegistered = false;
                }
            } else {
                if (!this.mIsMbrRegistered && (this.mFlags & 1) != 0) {
                    if (VERSION.SDK_INT < 18) {
                        AudioManager audioManager3 = (AudioManager) this.mContext.getSystemService("audio");
                        AudioManager audioManager4 = audioManager3;
                        audioManager3.registerMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                    } else {
                        MediaSessionCompatApi18.registerMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    }
                    this.mIsMbrRegistered = true;
                } else if (this.mIsMbrRegistered && (this.mFlags & 1) == 0) {
                    if (VERSION.SDK_INT < 18) {
                        AudioManager audioManager5 = (AudioManager) this.mContext.getSystemService("audio");
                        AudioManager audioManager6 = audioManager5;
                        audioManager5.unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                    } else {
                        MediaSessionCompatApi18.unregisterMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    }
                    this.mIsMbrRegistered = false;
                }
                if (VERSION.SDK_INT >= 14) {
                    if (!this.mIsRccRegistered && (this.mFlags & 2) != 0) {
                        MediaSessionCompatApi14.registerRemoteControlClient(this.mContext, this.mRccObj);
                        this.mIsRccRegistered = true;
                        registeredRcc = true;
                    } else if (this.mIsRccRegistered && (this.mFlags & 2) == 0) {
                        MediaSessionCompatApi14.setState(this.mRccObj, 0);
                        MediaSessionCompatApi14.unregisterRemoteControlClient(this.mContext, this.mRccObj);
                        this.mIsRccRegistered = false;
                    }
                }
            }
            return registeredRcc;
        }

        /* access modifiers changed from: 0000 */
        public void adjustVolume(int i, int i2) {
            int direction = i;
            int flags = i2;
            int i3 = direction;
            int i4 = flags;
            if (this.mVolumeType != 2) {
                this.mAudioManager.adjustStreamVolume(this.mLocalStream, direction, flags);
            } else if (this.mVolumeProvider != null) {
                this.mVolumeProvider.onAdjustVolume(direction);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setVolumeTo(int i, int i2) {
            int value = i;
            int flags = i2;
            int i3 = value;
            int i4 = flags;
            if (this.mVolumeType != 2) {
                this.mAudioManager.setStreamVolume(this.mLocalStream, value, flags);
            } else if (this.mVolumeProvider != null) {
                this.mVolumeProvider.onSetVolumeTo(value);
            }
        }

        /* access modifiers changed from: 0000 */
        public PlaybackStateCompat getStateWithUpdatedPosition() {
            PlaybackStateCompat playbackStateCompat;
            long duration = -1;
            Object obj = this.mLock;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    PlaybackStateCompat state = this.mState;
                    if (this.mMetadata != null) {
                        if (this.mMetadata.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                            duration = this.mMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
                        }
                    }
                    PlaybackStateCompat playbackStateCompat2 = null;
                    if (state != null && (state.getState() == 3 || state.getState() == 4 || state.getState() == 5)) {
                        long updateTime = state.getLastPositionUpdateTime();
                        long currentTime = SystemClock.elapsedRealtime();
                        if (!(updateTime <= 0)) {
                            long position = ((long) (state.getPlaybackSpeed() * ((float) (currentTime - updateTime)))) + state.getPosition();
                            if (!(duration < 0)) {
                                if (!(position <= duration)) {
                                    position = duration;
                                    PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder(state);
                                    PlaybackStateCompat.Builder builder2 = builder;
                                    PlaybackStateCompat.Builder state2 = builder.setState(state.getState(), position, state.getPlaybackSpeed(), currentTime);
                                    playbackStateCompat2 = builder2.build();
                                }
                            }
                            if (!(position >= 0)) {
                                position = 0;
                            }
                            PlaybackStateCompat.Builder builder3 = new PlaybackStateCompat.Builder(state);
                            PlaybackStateCompat.Builder builder22 = builder3;
                            PlaybackStateCompat.Builder state22 = builder3.setState(state.getState(), position, state.getPlaybackSpeed(), currentTime);
                            playbackStateCompat2 = builder22.build();
                        }
                    }
                    if (playbackStateCompat2 != null) {
                        playbackStateCompat = playbackStateCompat2;
                    } else {
                        playbackStateCompat = state;
                    }
                    return playbackStateCompat;
                } finally {
                    Object obj3 = obj2;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void sendVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
            ParcelableVolumeInfo info = parcelableVolumeInfo;
            ParcelableVolumeInfo parcelableVolumeInfo2 = info;
            int beginBroadcast = this.mControllerCallbacks.beginBroadcast();
            int i = beginBroadcast;
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(i2);
                IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                try {
                    iMediaControllerCallback.onVolumeInfoChanged(info);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendSessionDestroyed() {
            int beginBroadcast = this.mControllerCallbacks.beginBroadcast();
            int i = beginBroadcast;
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(i2);
                IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                try {
                    iMediaControllerCallback.onSessionDestroyed();
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
            this.mControllerCallbacks.kill();
        }

        private void sendEvent(String str, Bundle bundle) {
            String event = str;
            Bundle extras = bundle;
            String str2 = event;
            Bundle bundle2 = extras;
            int beginBroadcast = this.mControllerCallbacks.beginBroadcast();
            int i = beginBroadcast;
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(i2);
                IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                try {
                    iMediaControllerCallback.onEvent(event, extras);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendState(PlaybackStateCompat playbackStateCompat) {
            PlaybackStateCompat state = playbackStateCompat;
            PlaybackStateCompat playbackStateCompat2 = state;
            int beginBroadcast = this.mControllerCallbacks.beginBroadcast();
            int i = beginBroadcast;
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(i2);
                IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                try {
                    iMediaControllerCallback.onPlaybackStateChanged(state);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendMetadata(MediaMetadataCompat mediaMetadataCompat) {
            MediaMetadataCompat metadata = mediaMetadataCompat;
            MediaMetadataCompat mediaMetadataCompat2 = metadata;
            int beginBroadcast = this.mControllerCallbacks.beginBroadcast();
            int i = beginBroadcast;
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(i2);
                IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                try {
                    iMediaControllerCallback.onMetadataChanged(metadata);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendQueue(List<QueueItem> list) {
            List<QueueItem> queue = list;
            List<QueueItem> list2 = queue;
            int beginBroadcast = this.mControllerCallbacks.beginBroadcast();
            int i = beginBroadcast;
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(i2);
                IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                try {
                    iMediaControllerCallback.onQueueChanged(queue);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendQueueTitle(CharSequence charSequence) {
            CharSequence queueTitle = charSequence;
            CharSequence charSequence2 = queueTitle;
            int beginBroadcast = this.mControllerCallbacks.beginBroadcast();
            int i = beginBroadcast;
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(i2);
                IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                try {
                    iMediaControllerCallback.onQueueTitleChanged(queueTitle);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            int beginBroadcast = this.mControllerCallbacks.beginBroadcast();
            int i = beginBroadcast;
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem(i2);
                IMediaControllerCallback iMediaControllerCallback2 = iMediaControllerCallback;
                try {
                    iMediaControllerCallback.onExtrasChanged(extras);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$OnActiveChangeListener */
    public interface OnActiveChangeListener {
        void onActiveChanged();
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$QueueItem */
    public static final class QueueItem implements Parcelable {
        public static final Creator<QueueItem> CREATOR = new Creator<QueueItem>() {
            public QueueItem createFromParcel(Parcel parcel) {
                Parcel p = parcel;
                Parcel parcel2 = p;
                return new QueueItem(p);
            }

            public QueueItem[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new QueueItem[size];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat mDescription;
        private final long mId;
        private Object mItem;

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long j) {
            MediaDescriptionCompat description = mediaDescriptionCompat;
            long id = j;
            MediaDescriptionCompat mediaDescriptionCompat2 = description;
            long j2 = id;
            this(null, description, id);
        }

        private QueueItem(Object obj, MediaDescriptionCompat mediaDescriptionCompat, long j) {
            Object queueItem = obj;
            MediaDescriptionCompat description = mediaDescriptionCompat;
            long id = j;
            Object obj2 = queueItem;
            MediaDescriptionCompat mediaDescriptionCompat2 = description;
            long j2 = id;
            if (description == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            } else if (id == -1) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            } else {
                this.mDescription = description;
                this.mId = id;
                this.mItem = queueItem;
            }
        }

        QueueItem(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(in);
            this.mId = in.readLong();
        }

        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        public long getQueueId() {
            return this.mId;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            this.mDescription.writeToParcel(dest, flags);
            dest.writeLong(this.mId);
        }

        public int describeContents() {
            return 0;
        }

        public Object getQueueItem() {
            if (this.mItem != null || VERSION.SDK_INT < 21) {
                return this.mItem;
            }
            this.mItem = QueueItem.createItem(this.mDescription.getMediaDescription(), this.mId);
            return this.mItem;
        }

        @Deprecated
        public static QueueItem obtain(Object obj) {
            Object queueItem = obj;
            Object obj2 = queueItem;
            return fromQueueItem(queueItem);
        }

        public static QueueItem fromQueueItem(Object obj) {
            Object queueItem = obj;
            Object obj2 = queueItem;
            if (queueItem == null || VERSION.SDK_INT < 21) {
                return null;
            }
            Object description = QueueItem.getDescription(queueItem);
            Object obj3 = description;
            MediaDescriptionCompat description2 = MediaDescriptionCompat.fromMediaDescription(description);
            long queueId = QueueItem.getQueueId(queueItem);
            long j = queueId;
            return new QueueItem(queueItem, description2, queueId);
        }

        public static List<QueueItem> fromQueueItemList(List<?> list) {
            List<?> itemList = list;
            List<?> list2 = itemList;
            if (itemList == null || VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object fromQueueItem : itemList) {
                boolean add = arrayList.add(fromQueueItem(fromQueueItem));
            }
            return arrayList;
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper */
    static final class ResultReceiverWrapper implements Parcelable {
        public static final Creator<ResultReceiverWrapper> CREATOR = new Creator<ResultReceiverWrapper>() {
            public ResultReceiverWrapper createFromParcel(Parcel parcel) {
                Parcel p = parcel;
                Parcel parcel2 = p;
                return new ResultReceiverWrapper(p);
            }

            public ResultReceiverWrapper[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new ResultReceiverWrapper[size];
            }
        };
        private ResultReceiver mResultReceiver;

        static /* synthetic */ ResultReceiver access$000(ResultReceiverWrapper resultReceiverWrapper) {
            ResultReceiverWrapper x0 = resultReceiverWrapper;
            ResultReceiverWrapper resultReceiverWrapper2 = x0;
            return x0.mResultReceiver;
        }

        public ResultReceiverWrapper(ResultReceiver resultReceiver) {
            ResultReceiver resultReceiver2 = resultReceiver;
            ResultReceiver resultReceiver3 = resultReceiver2;
            this.mResultReceiver = resultReceiver2;
        }

        ResultReceiverWrapper(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            this.mResultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(in);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            this.mResultReceiver.writeToParcel(dest, flags);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$SessionFlags */
    public @interface SessionFlags {
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$Token */
    public static final class Token implements Parcelable {
        public static final Creator<Token> CREATOR = new Creator<Token>() {
            public Token createFromParcel(Parcel parcel) {
                Object inner;
                Parcel in = parcel;
                Parcel parcel2 = in;
                if (VERSION.SDK_INT < 21) {
                    inner = in.readStrongBinder();
                } else {
                    inner = in.readParcelable(null);
                }
                return new Token(inner);
            }

            public Token[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new Token[size];
            }
        };
        private final Object mInner;

        Token(Object obj) {
            Object inner = obj;
            Object obj2 = inner;
            this.mInner = inner;
        }

        public static Token fromToken(Object obj) {
            Object token = obj;
            Object obj2 = token;
            if (token != null && VERSION.SDK_INT >= 21) {
                return new Token(MediaSessionCompatApi21.verifyToken(token));
            }
            return null;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel dest = parcel;
            int flags = i;
            Parcel parcel2 = dest;
            int i2 = flags;
            if (VERSION.SDK_INT < 21) {
                dest.writeStrongBinder((IBinder) this.mInner);
            } else {
                dest.writeParcelable((Parcelable) this.mInner, flags);
            }
        }

        public int hashCode() {
            if (this.mInner != null) {
                return this.mInner.hashCode();
            }
            return 0;
        }

        public boolean equals(Object obj) {
            Object obj2 = obj;
            Object obj3 = obj2;
            if (this == obj2) {
                return true;
            }
            if (!(obj2 instanceof Token)) {
                return false;
            }
            Token other = (Token) obj2;
            if (this.mInner == null) {
                return other.mInner == null;
            } else if (other.mInner != null) {
                return this.mInner.equals(other.mInner);
            } else {
                return false;
            }
        }

        public Object getToken() {
            return this.mInner;
        }
    }

    private MediaSessionCompat(Context context, MediaSessionImpl mediaSessionImpl) {
        Context context2 = context;
        MediaSessionImpl impl = mediaSessionImpl;
        Context context3 = context2;
        MediaSessionImpl mediaSessionImpl2 = impl;
        this.mActiveListeners = new ArrayList<>();
        this.mImpl = impl;
        if (VERSION.SDK_INT >= 21) {
            setCallback(new Callback(this) {
                final /* synthetic */ MediaSessionCompat this$0;

                {
                    MediaSessionCompat this$02 = r5;
                    MediaSessionCompat mediaSessionCompat = this$02;
                    this.this$0 = this$02;
                }
            });
        }
        this.mController = new MediaControllerCompat(context2, this);
    }

    public MediaSessionCompat(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
        Context context2 = context;
        String tag = str;
        ComponentName mbrComponent = componentName;
        PendingIntent mbrIntent = pendingIntent;
        Context context3 = context2;
        String str2 = tag;
        ComponentName componentName2 = mbrComponent;
        PendingIntent pendingIntent2 = mbrIntent;
        this.mActiveListeners = new ArrayList<>();
        if (context2 == null) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("context must not be null");
            throw illegalArgumentException;
        } else if (!TextUtils.isEmpty(tag)) {
            if (VERSION.SDK_INT < 21) {
                this.mImpl = new MediaSessionImplBase(context2, tag, mbrComponent, mbrIntent);
            } else {
                this.mImpl = new MediaSessionImplApi21(context2, tag);
            }
            this.mController = new MediaControllerCompat(context2, this);
            if (sMaxBitmapSize == 0) {
                sMaxBitmapSize = (int) TypedValue.applyDimension(1, 320.0f, context2.getResources().getDisplayMetrics());
            }
        } else {
            IllegalArgumentException illegalArgumentException2 = new IllegalArgumentException("tag must not be null or empty");
            throw illegalArgumentException2;
        }
    }

    public MediaSessionCompat(Context context, String str) {
        Context context2 = context;
        String tag = str;
        Context context3 = context2;
        String str2 = tag;
        this(context2, tag, null, null);
    }

    public void setCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        setCallback(callback2, null);
    }

    public void setCallback(Callback callback, Handler handler) {
        Callback callback2 = callback;
        Handler handler2 = handler;
        Callback callback3 = callback2;
        Handler handler3 = handler2;
        this.mImpl.setCallback(callback2, handler2 == null ? new Handler() : handler2);
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        PendingIntent pi = pendingIntent;
        PendingIntent pendingIntent2 = pi;
        this.mImpl.setSessionActivity(pi);
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        PendingIntent mbr = pendingIntent;
        PendingIntent pendingIntent2 = mbr;
        this.mImpl.setMediaButtonReceiver(mbr);
    }

    public void setFlags(int i) {
        int flags = i;
        int i2 = flags;
        this.mImpl.setFlags(flags);
    }

    public void setPlaybackToLocal(int i) {
        int stream = i;
        int i2 = stream;
        this.mImpl.setPlaybackToLocal(stream);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        VolumeProviderCompat volumeProvider = volumeProviderCompat;
        VolumeProviderCompat volumeProviderCompat2 = volumeProvider;
        if (volumeProvider != null) {
            this.mImpl.setPlaybackToRemote(volumeProvider);
            return;
        }
        throw new IllegalArgumentException("volumeProvider may not be null!");
    }

    public void setActive(boolean z) {
        this.mImpl.setActive(z);
        Iterator it = this.mActiveListeners.iterator();
        while (it.hasNext()) {
            OnActiveChangeListener onActiveChangeListener = (OnActiveChangeListener) it.next();
            OnActiveChangeListener onActiveChangeListener2 = onActiveChangeListener;
            onActiveChangeListener.onActiveChanged();
        }
    }

    public boolean isActive() {
        return this.mImpl.isActive();
    }

    public void sendSessionEvent(String str, Bundle bundle) {
        String event = str;
        Bundle extras = bundle;
        String str2 = event;
        Bundle bundle2 = extras;
        if (!TextUtils.isEmpty(event)) {
            this.mImpl.sendSessionEvent(event, extras);
            return;
        }
        throw new IllegalArgumentException("event cannot be null or empty");
    }

    public void release() {
        this.mImpl.release();
    }

    public Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    public MediaControllerCompat getController() {
        return this.mController;
    }

    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        PlaybackStateCompat state = playbackStateCompat;
        PlaybackStateCompat playbackStateCompat2 = state;
        this.mImpl.setPlaybackState(state);
    }

    public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        MediaMetadataCompat metadata = mediaMetadataCompat;
        MediaMetadataCompat mediaMetadataCompat2 = metadata;
        this.mImpl.setMetadata(metadata);
    }

    public void setQueue(List<QueueItem> list) {
        List<QueueItem> queue = list;
        List<QueueItem> list2 = queue;
        this.mImpl.setQueue(queue);
    }

    public void setQueueTitle(CharSequence charSequence) {
        CharSequence title = charSequence;
        CharSequence charSequence2 = title;
        this.mImpl.setQueueTitle(title);
    }

    public void setRatingType(int i) {
        int type = i;
        int i2 = type;
        this.mImpl.setRatingType(type);
    }

    public void setExtras(Bundle bundle) {
        Bundle extras = bundle;
        Bundle bundle2 = extras;
        this.mImpl.setExtras(extras);
    }

    public Object getMediaSession() {
        return this.mImpl.getMediaSession();
    }

    public Object getRemoteControlClient() {
        return this.mImpl.getRemoteControlClient();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public String getCallingPackage() {
        return this.mImpl.getCallingPackage();
    }

    public void addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        OnActiveChangeListener listener = onActiveChangeListener;
        OnActiveChangeListener onActiveChangeListener2 = listener;
        if (listener != null) {
            boolean add = this.mActiveListeners.add(listener);
            return;
        }
        throw new IllegalArgumentException("Listener may not be null");
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        OnActiveChangeListener listener = onActiveChangeListener;
        OnActiveChangeListener onActiveChangeListener2 = listener;
        if (listener != null) {
            boolean remove = this.mActiveListeners.remove(listener);
            return;
        }
        throw new IllegalArgumentException("Listener may not be null");
    }

    @Deprecated
    public static MediaSessionCompat obtain(Context context, Object obj) {
        Context context2 = context;
        Object mediaSession = obj;
        Context context3 = context2;
        Object obj2 = mediaSession;
        return fromMediaSession(context2, mediaSession);
    }

    public static MediaSessionCompat fromMediaSession(Context context, Object obj) {
        Context context2 = context;
        Object mediaSession = obj;
        Context context3 = context2;
        Object obj2 = mediaSession;
        if (context2 == null || mediaSession == null || VERSION.SDK_INT < 21) {
            return null;
        }
        return new MediaSessionCompat(context2, (MediaSessionImpl) new MediaSessionImplApi21(mediaSession));
    }
}
