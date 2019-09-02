package android.support.p000v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.BundleCompat;
import android.support.p000v4.media.session.MediaSessionCompat;
import android.support.p000v4.media.session.MediaSessionCompat.Token;
import android.support.p000v4.p002os.BuildCompat;
import android.support.p000v4.p002os.ResultReceiver;
import android.support.p000v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

/* renamed from: android.support.v4.media.MediaBrowserCompat */
public final class MediaBrowserCompat {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    static final String TAG = "MediaBrowserCompat";
    private final MediaBrowserImpl mImpl;

    /* renamed from: android.support.v4.media.MediaBrowserCompat$CallbackHandler */
    private static class CallbackHandler extends Handler {
        private final WeakReference<MediaBrowserServiceCallbackImpl> mCallbackImplRef;
        private WeakReference<Messenger> mCallbacksMessengerRef;

        CallbackHandler(MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl) {
            MediaBrowserServiceCallbackImpl callbackImpl = mediaBrowserServiceCallbackImpl;
            MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl2 = callbackImpl;
            this.mCallbackImplRef = new WeakReference<>(callbackImpl);
        }

        public void handleMessage(Message message) {
            Message msg = message;
            Message message2 = msg;
            if (this.mCallbacksMessengerRef != null && this.mCallbacksMessengerRef.get() != null && this.mCallbackImplRef.get() != null) {
                Bundle data = msg.getData();
                Bundle data2 = data;
                data.setClassLoader(MediaSessionCompat.class.getClassLoader());
                switch (msg.what) {
                    case 1:
                        ((MediaBrowserServiceCallbackImpl) this.mCallbackImplRef.get()).onServiceConnected((Messenger) this.mCallbacksMessengerRef.get(), data2.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (Token) data2.getParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN), data2.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS));
                        break;
                    case 2:
                        ((MediaBrowserServiceCallbackImpl) this.mCallbackImplRef.get()).onConnectionFailed((Messenger) this.mCallbacksMessengerRef.get());
                        break;
                    case 3:
                        ((MediaBrowserServiceCallbackImpl) this.mCallbackImplRef.get()).onLoadChildren((Messenger) this.mCallbacksMessengerRef.get(), data2.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), data2.getParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST), data2.getBundle(MediaBrowserProtocol.DATA_OPTIONS));
                        break;
                    default:
                        int w = Log.w(MediaBrowserCompat.TAG, "Unhandled message: " + msg + "\n  Client version: " + 1 + "\n  Service version: " + msg.arg1);
                        break;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void setCallbacksMessenger(Messenger messenger) {
            Messenger callbacksMessenger = messenger;
            Messenger messenger2 = callbacksMessenger;
            this.mCallbacksMessengerRef = new WeakReference<>(callbacksMessenger);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$ConnectionCallback */
    public static class ConnectionCallback {
        ConnectionCallbackInternal mConnectionCallbackInternal;
        final Object mConnectionCallbackObj;

        /* renamed from: android.support.v4.media.MediaBrowserCompat$ConnectionCallback$ConnectionCallbackInternal */
        interface ConnectionCallbackInternal {
            void onConnected();

            void onConnectionFailed();

            void onConnectionSuspended();
        }

        /* renamed from: android.support.v4.media.MediaBrowserCompat$ConnectionCallback$StubApi21 */
        private class StubApi21 implements ConnectionCallback {
            final /* synthetic */ ConnectionCallback this$0;

            StubApi21(ConnectionCallback connectionCallback) {
                this.this$0 = connectionCallback;
            }

            public void onConnected() {
                if (this.this$0.mConnectionCallbackInternal != null) {
                    this.this$0.mConnectionCallbackInternal.onConnected();
                }
                this.this$0.onConnected();
            }

            public void onConnectionSuspended() {
                if (this.this$0.mConnectionCallbackInternal != null) {
                    this.this$0.mConnectionCallbackInternal.onConnectionSuspended();
                }
                this.this$0.onConnectionSuspended();
            }

            public void onConnectionFailed() {
                if (this.this$0.mConnectionCallbackInternal != null) {
                    this.this$0.mConnectionCallbackInternal.onConnectionFailed();
                }
                this.this$0.onConnectionFailed();
            }
        }

        public ConnectionCallback() {
            if (VERSION.SDK_INT < 21) {
                this.mConnectionCallbackObj = null;
            } else {
                this.mConnectionCallbackObj = MediaBrowserCompatApi21.createConnectionCallback(new StubApi21(this));
            }
        }

        public void onConnected() {
        }

        public void onConnectionSuspended() {
        }

        public void onConnectionFailed() {
        }

        /* access modifiers changed from: 0000 */
        public void setInternalConnectionCallback(ConnectionCallbackInternal connectionCallbackInternal) {
            ConnectionCallbackInternal connectionCallbackInternal2 = connectionCallbackInternal;
            ConnectionCallbackInternal connectionCallbackInternal3 = connectionCallbackInternal2;
            this.mConnectionCallbackInternal = connectionCallbackInternal2;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$ItemCallback */
    public static abstract class ItemCallback {
        final Object mItemCallbackObj;

        /* renamed from: android.support.v4.media.MediaBrowserCompat$ItemCallback$StubApi23 */
        private class StubApi23 implements ItemCallback {
            final /* synthetic */ ItemCallback this$0;

            StubApi23(ItemCallback itemCallback) {
                this.this$0 = itemCallback;
            }

            public void onItemLoaded(Parcel parcel) {
                Parcel itemParcel = parcel;
                Parcel parcel2 = itemParcel;
                itemParcel.setDataPosition(0);
                MediaItem item = (MediaItem) MediaItem.CREATOR.createFromParcel(itemParcel);
                itemParcel.recycle();
                this.this$0.onItemLoaded(item);
            }

            public void onError(@NonNull String str) {
                String itemId = str;
                String str2 = itemId;
                this.this$0.onError(itemId);
            }
        }

        public ItemCallback() {
            if (VERSION.SDK_INT < 23) {
                this.mItemCallbackObj = null;
            } else {
                this.mItemCallbackObj = MediaBrowserCompatApi23.createItemCallback(new StubApi23(this));
            }
        }

        public void onItemLoaded(MediaItem mediaItem) {
            MediaItem mediaItem2 = mediaItem;
        }

        public void onError(@NonNull String str) {
            String str2 = str;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$ItemReceiver */
    private static class ItemReceiver extends ResultReceiver {
        private final ItemCallback mCallback;
        private final String mMediaId;

        ItemReceiver(String str, ItemCallback itemCallback, Handler handler) {
            String mediaId = str;
            ItemCallback callback = itemCallback;
            Handler handler2 = handler;
            String str2 = mediaId;
            ItemCallback itemCallback2 = callback;
            Handler handler3 = handler2;
            super(handler2);
            this.mMediaId = mediaId;
            this.mCallback = callback;
        }

        /* access modifiers changed from: protected */
        public void onReceiveResult(int i, Bundle bundle) {
            int resultCode = i;
            Bundle resultData = bundle;
            int i2 = resultCode;
            Bundle bundle2 = resultData;
            if (resultData != null) {
                resultData.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (resultCode == 0 && resultData != null && resultData.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                Parcelable parcelable = resultData.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
                Parcelable item = parcelable;
                if (parcelable != null && !(item instanceof MediaItem)) {
                    this.mCallback.onError(this.mMediaId);
                } else {
                    this.mCallback.onItemLoaded((MediaItem) item);
                }
                return;
            }
            this.mCallback.onError(this.mMediaId);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaBrowserImpl */
    interface MediaBrowserImpl {
        void connect();

        void disconnect();

        @Nullable
        Bundle getExtras();

        void getItem(@NonNull String str, @NonNull ItemCallback itemCallback);

        @NonNull
        String getRoot();

        ComponentName getServiceComponent();

        @NonNull
        Token getSessionToken();

        boolean isConnected();

        void subscribe(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback);

        void unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback);
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaBrowserImplApi21 */
    static class MediaBrowserImplApi21 implements MediaBrowserImpl, MediaBrowserServiceCallbackImpl, ConnectionCallbackInternal {
        protected final Object mBrowserObj;
        protected Messenger mCallbacksMessenger;
        protected final CallbackHandler mHandler = new CallbackHandler(this);
        protected final Bundle mRootHints;
        protected ServiceBinderWrapper mServiceBinderWrapper;
        private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap<>();

        public MediaBrowserImplApi21(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            Context context2 = context;
            ComponentName serviceComponent = componentName;
            ConnectionCallback callback = connectionCallback;
            Bundle rootHints = bundle;
            Context context3 = context2;
            ComponentName componentName2 = serviceComponent;
            ConnectionCallback connectionCallback2 = callback;
            Bundle rootHints2 = rootHints;
            if (VERSION.SDK_INT >= 25) {
                this.mRootHints = rootHints != null ? new Bundle(rootHints) : null;
            } else {
                if (rootHints == null) {
                    rootHints2 = new Bundle();
                }
                rootHints2.putInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 1);
                this.mRootHints = new Bundle(rootHints2);
            }
            callback.setInternalConnectionCallback(this);
            this.mBrowserObj = MediaBrowserCompatApi21.createBrowser(context2, serviceComponent, callback.mConnectionCallbackObj, this.mRootHints);
        }

        public void connect() {
            MediaBrowserCompatApi21.connect(this.mBrowserObj);
        }

        public void disconnect() {
            if (!(this.mServiceBinderWrapper == null || this.mCallbacksMessenger == null)) {
                try {
                    this.mServiceBinderWrapper.unregisterCallbackMessenger(this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int i = Log.i(MediaBrowserCompat.TAG, "Remote error unregistering client messenger.");
                }
            }
            MediaBrowserCompatApi21.disconnect(this.mBrowserObj);
        }

        public boolean isConnected() {
            return MediaBrowserCompatApi21.isConnected(this.mBrowserObj);
        }

        public ComponentName getServiceComponent() {
            return MediaBrowserCompatApi21.getServiceComponent(this.mBrowserObj);
        }

        @NonNull
        public String getRoot() {
            return MediaBrowserCompatApi21.getRoot(this.mBrowserObj);
        }

        @Nullable
        public Bundle getExtras() {
            return MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
        }

        @NonNull
        public Token getSessionToken() {
            return Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj));
        }

        public void subscribe(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            String parentId = str;
            Bundle options = bundle;
            SubscriptionCallback callback = subscriptionCallback;
            String str2 = parentId;
            Bundle bundle2 = options;
            SubscriptionCallback subscriptionCallback2 = callback;
            Subscription subscription = (Subscription) this.mSubscriptions.get(parentId);
            Subscription sub = subscription;
            if (subscription == null) {
                sub = new Subscription();
                Object put = this.mSubscriptions.put(parentId, sub);
            }
            SubscriptionCallback.access$100(callback, sub);
            sub.putCallback(options, callback);
            if (this.mServiceBinderWrapper != null) {
                try {
                    this.mServiceBinderWrapper.addSubscription(parentId, SubscriptionCallback.access$000(callback), options, this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int i = Log.i(MediaBrowserCompat.TAG, "Remote error subscribing media item: " + parentId);
                }
            } else {
                MediaBrowserCompatApi21.subscribe(this.mBrowserObj, parentId, SubscriptionCallback.access$200(callback));
            }
        }

        public void unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            String parentId = str;
            SubscriptionCallback callback = subscriptionCallback;
            String str2 = parentId;
            SubscriptionCallback subscriptionCallback2 = callback;
            Subscription subscription = (Subscription) this.mSubscriptions.get(parentId);
            Subscription sub = subscription;
            if (subscription != null) {
                if (this.mServiceBinderWrapper != null) {
                    if (callback != null) {
                        try {
                            List callbacks = sub.getCallbacks();
                            List optionsList = sub.getOptionsList();
                            for (int i = callbacks.size() - 1; i >= 0; i--) {
                                if (callbacks.get(i) == callback) {
                                    this.mServiceBinderWrapper.removeSubscription(parentId, SubscriptionCallback.access$000(callback), this.mCallbacksMessenger);
                                    Object remove = callbacks.remove(i);
                                    Object remove2 = optionsList.remove(i);
                                }
                            }
                        } catch (RemoteException e) {
                            RemoteException remoteException = e;
                            int d = Log.d(MediaBrowserCompat.TAG, "removeSubscription failed with RemoteException parentId=" + parentId);
                        }
                    } else {
                        this.mServiceBinderWrapper.removeSubscription(parentId, null, this.mCallbacksMessenger);
                    }
                } else if (callback != null) {
                    List callbacks2 = sub.getCallbacks();
                    List optionsList2 = sub.getOptionsList();
                    for (int i2 = callbacks2.size() - 1; i2 >= 0; i2--) {
                        if (callbacks2.get(i2) == callback) {
                            Object remove3 = callbacks2.remove(i2);
                            Object remove4 = optionsList2.remove(i2);
                        }
                    }
                    if (callbacks2.size() == 0) {
                        MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, parentId);
                    }
                } else {
                    MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, parentId);
                }
                if (sub.isEmpty() || callback == null) {
                    Object remove5 = this.mSubscriptions.remove(parentId);
                }
            }
        }

        public void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
            String mediaId = str;
            ItemCallback cb = itemCallback;
            String str2 = mediaId;
            ItemCallback itemCallback2 = cb;
            if (TextUtils.isEmpty(mediaId)) {
                throw new IllegalArgumentException("mediaId is empty");
            } else if (cb == null) {
                throw new IllegalArgumentException("cb is null");
            } else if (!MediaBrowserCompatApi21.isConnected(this.mBrowserObj)) {
                int i = Log.i(MediaBrowserCompat.TAG, "Not connected, unable to retrieve the MediaItem.");
                CallbackHandler callbackHandler = this.mHandler;
                final ItemCallback itemCallback3 = cb;
                final String str3 = mediaId;
                C01261 r0 = new Runnable(this) {
                    final /* synthetic */ MediaBrowserImplApi21 this$0;

                    {
                        MediaBrowserImplApi21 this$02 = r7;
                        ItemCallback itemCallback = r8;
                        String str = r9;
                        MediaBrowserImplApi21 mediaBrowserImplApi21 = this$02;
                        this.this$0 = this$02;
                    }

                    public void run() {
                        itemCallback3.onError(str3);
                    }
                };
                boolean post = callbackHandler.post(r0);
            } else if (this.mServiceBinderWrapper != null) {
                try {
                    this.mServiceBinderWrapper.getMediaItem(mediaId, new ItemReceiver(mediaId, cb, this.mHandler), this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int i2 = Log.i(MediaBrowserCompat.TAG, "Remote error getting media item: " + mediaId);
                    CallbackHandler callbackHandler2 = this.mHandler;
                    final ItemCallback itemCallback4 = cb;
                    final String str4 = mediaId;
                    C01283 r02 = new Runnable(this) {
                        final /* synthetic */ MediaBrowserImplApi21 this$0;

                        {
                            MediaBrowserImplApi21 this$02 = r7;
                            ItemCallback itemCallback = r8;
                            String str = r9;
                            MediaBrowserImplApi21 mediaBrowserImplApi21 = this$02;
                            this.this$0 = this$02;
                        }

                        public void run() {
                            itemCallback4.onError(str4);
                        }
                    };
                    boolean post2 = callbackHandler2.post(r02);
                }
            } else {
                CallbackHandler callbackHandler3 = this.mHandler;
                final ItemCallback itemCallback5 = cb;
                final String str5 = mediaId;
                C01272 r03 = new Runnable(this) {
                    final /* synthetic */ MediaBrowserImplApi21 this$0;

                    {
                        MediaBrowserImplApi21 this$02 = r7;
                        ItemCallback itemCallback = r8;
                        String str = r9;
                        MediaBrowserImplApi21 mediaBrowserImplApi21 = this$02;
                        this.this$0 = this$02;
                    }

                    public void run() {
                        itemCallback5.onError(str5);
                    }
                };
                boolean post3 = callbackHandler3.post(r03);
            }
        }

        public void onConnected() {
            Bundle extras = MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
            Bundle extras2 = extras;
            if (extras != null) {
                IBinder binder = BundleCompat.getBinder(extras2, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER);
                IBinder serviceBinder = binder;
                if (binder != null) {
                    this.mServiceBinderWrapper = new ServiceBinderWrapper(serviceBinder, this.mRootHints);
                    this.mCallbacksMessenger = new Messenger(this.mHandler);
                    this.mHandler.setCallbacksMessenger(this.mCallbacksMessenger);
                    try {
                        this.mServiceBinderWrapper.registerCallbackMessenger(this.mCallbacksMessenger);
                    } catch (RemoteException e) {
                        RemoteException remoteException = e;
                        int i = Log.i(MediaBrowserCompat.TAG, "Remote error registering client messenger.");
                    }
                }
            }
        }

        public void onConnectionSuspended() {
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mHandler.setCallbacksMessenger(null);
        }

        public void onConnectionFailed() {
        }

        public void onServiceConnected(Messenger messenger, String str, Token token, Bundle bundle) {
            Messenger messenger2 = messenger;
            String str2 = str;
            Token token2 = token;
            Bundle bundle2 = bundle;
        }

        public void onConnectionFailed(Messenger messenger) {
            Messenger messenger2 = messenger;
        }

        public void onLoadChildren(Messenger messenger, String str, List list, Bundle bundle) {
            Messenger callback = messenger;
            String parentId = str;
            List list2 = list;
            Bundle options = bundle;
            Messenger messenger2 = callback;
            String str2 = parentId;
            List list3 = list2;
            Bundle bundle2 = options;
            if (this.mCallbacksMessenger == callback) {
                Subscription subscription = (Subscription) this.mSubscriptions.get(parentId);
                Subscription subscription2 = subscription;
                if (subscription != null) {
                    SubscriptionCallback callback2 = subscription2.getCallback(options);
                    SubscriptionCallback subscriptionCallback = callback2;
                    if (callback2 != null) {
                        if (options != null) {
                            subscriptionCallback.onChildrenLoaded(parentId, list2, options);
                        } else {
                            subscriptionCallback.onChildrenLoaded(parentId, list2);
                        }
                    }
                    return;
                }
                if (MediaBrowserCompat.DEBUG) {
                    int d = Log.d(MediaBrowserCompat.TAG, "onLoadChildren for id that isn't subscribed id=" + parentId);
                }
            }
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaBrowserImplApi23 */
    static class MediaBrowserImplApi23 extends MediaBrowserImplApi21 {
        public MediaBrowserImplApi23(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            Context context2 = context;
            ComponentName serviceComponent = componentName;
            ConnectionCallback callback = connectionCallback;
            Bundle rootHints = bundle;
            Context context3 = context2;
            ComponentName componentName2 = serviceComponent;
            ConnectionCallback connectionCallback2 = callback;
            Bundle bundle2 = rootHints;
            super(context2, serviceComponent, callback, rootHints);
        }

        public void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
            String mediaId = str;
            ItemCallback cb = itemCallback;
            String str2 = mediaId;
            ItemCallback itemCallback2 = cb;
            if (this.mServiceBinderWrapper != null) {
                super.getItem(mediaId, cb);
                return;
            }
            MediaBrowserCompatApi23.getItem(this.mBrowserObj, mediaId, cb.mItemCallbackObj);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaBrowserImplApi24 */
    static class MediaBrowserImplApi24 extends MediaBrowserImplApi23 {
        public MediaBrowserImplApi24(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            Context context2 = context;
            ComponentName serviceComponent = componentName;
            ConnectionCallback callback = connectionCallback;
            Bundle rootHints = bundle;
            Context context3 = context2;
            ComponentName componentName2 = serviceComponent;
            ConnectionCallback connectionCallback2 = callback;
            Bundle bundle2 = rootHints;
            super(context2, serviceComponent, callback, rootHints);
        }

        public void subscribe(@NonNull String str, @NonNull Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            String parentId = str;
            Bundle options = bundle;
            SubscriptionCallback callback = subscriptionCallback;
            String str2 = parentId;
            Bundle bundle2 = options;
            SubscriptionCallback subscriptionCallback2 = callback;
            if (options != null) {
                MediaBrowserCompatApi24.subscribe(this.mBrowserObj, parentId, options, SubscriptionCallback.access$200(callback));
                return;
            }
            MediaBrowserCompatApi21.subscribe(this.mBrowserObj, parentId, SubscriptionCallback.access$200(callback));
        }

        public void unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            String parentId = str;
            SubscriptionCallback callback = subscriptionCallback;
            String str2 = parentId;
            SubscriptionCallback subscriptionCallback2 = callback;
            if (callback != null) {
                MediaBrowserCompatApi24.unsubscribe(this.mBrowserObj, parentId, SubscriptionCallback.access$200(callback));
                return;
            }
            MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, parentId);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase */
    static class MediaBrowserImplBase implements MediaBrowserImpl, MediaBrowserServiceCallbackImpl {
        private static final int CONNECT_STATE_CONNECTED = 2;
        static final int CONNECT_STATE_CONNECTING = 1;
        static final int CONNECT_STATE_DISCONNECTED = 0;
        static final int CONNECT_STATE_SUSPENDED = 3;
        final ConnectionCallback mCallback;
        Messenger mCallbacksMessenger;
        final Context mContext;
        private Bundle mExtras;
        final CallbackHandler mHandler = new CallbackHandler(this);
        private Token mMediaSessionToken;
        final Bundle mRootHints;
        private String mRootId;
        ServiceBinderWrapper mServiceBinderWrapper;
        final ComponentName mServiceComponent;
        MediaServiceConnection mServiceConnection;
        int mState = 0;
        private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap<>();

        /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection */
        private class MediaServiceConnection implements ServiceConnection {
            final /* synthetic */ MediaBrowserImplBase this$0;

            MediaServiceConnection(MediaBrowserImplBase mediaBrowserImplBase) {
                this.this$0 = mediaBrowserImplBase;
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ComponentName name = componentName;
                IBinder binder = iBinder;
                ComponentName componentName2 = name;
                IBinder iBinder2 = binder;
                final ComponentName componentName3 = name;
                final IBinder iBinder3 = binder;
                postOrRun(new Runnable(this) {
                    final /* synthetic */ MediaServiceConnection this$1;

                    {
                        MediaServiceConnection this$12 = r7;
                        ComponentName componentName = r8;
                        IBinder iBinder = r9;
                        MediaServiceConnection mediaServiceConnection = this$12;
                        this.this$1 = this$12;
                    }

                    public void run() {
                        if (MediaBrowserCompat.DEBUG) {
                            int d = Log.d(MediaBrowserCompat.TAG, "MediaServiceConnection.onServiceConnected name=" + componentName3 + " binder=" + iBinder3);
                            this.this$1.this$0.dump();
                        }
                        if (this.this$1.isCurrent("onServiceConnected")) {
                            this.this$1.this$0.mServiceBinderWrapper = new ServiceBinderWrapper(iBinder3, this.this$1.this$0.mRootHints);
                            this.this$1.this$0.mCallbacksMessenger = new Messenger(this.this$1.this$0.mHandler);
                            this.this$1.this$0.mHandler.setCallbacksMessenger(this.this$1.this$0.mCallbacksMessenger);
                            this.this$1.this$0.mState = 1;
                            try {
                                if (MediaBrowserCompat.DEBUG) {
                                    int d2 = Log.d(MediaBrowserCompat.TAG, "ServiceCallbacks.onConnect...");
                                    this.this$1.this$0.dump();
                                }
                                this.this$1.this$0.mServiceBinderWrapper.connect(this.this$1.this$0.mContext, this.this$1.this$0.mCallbacksMessenger);
                            } catch (RemoteException e) {
                                RemoteException remoteException = e;
                                int w = Log.w(MediaBrowserCompat.TAG, "RemoteException during connect for " + this.this$1.this$0.mServiceComponent);
                                if (MediaBrowserCompat.DEBUG) {
                                    int d3 = Log.d(MediaBrowserCompat.TAG, "ServiceCallbacks.onConnect...");
                                    this.this$1.this$0.dump();
                                }
                            }
                        }
                    }
                });
            }

            public void onServiceDisconnected(ComponentName componentName) {
                ComponentName name = componentName;
                ComponentName componentName2 = name;
                final ComponentName componentName3 = name;
                postOrRun(new Runnable(this) {
                    final /* synthetic */ MediaServiceConnection this$1;

                    {
                        MediaServiceConnection this$12 = r6;
                        ComponentName componentName = r7;
                        MediaServiceConnection mediaServiceConnection = this$12;
                        this.this$1 = this$12;
                    }

                    public void run() {
                        if (MediaBrowserCompat.DEBUG) {
                            int d = Log.d(MediaBrowserCompat.TAG, "MediaServiceConnection.onServiceDisconnected name=" + componentName3 + " this=" + this + " mServiceConnection=" + this.this$1.this$0.mServiceConnection);
                            this.this$1.this$0.dump();
                        }
                        if (this.this$1.isCurrent("onServiceDisconnected")) {
                            this.this$1.this$0.mServiceBinderWrapper = null;
                            this.this$1.this$0.mCallbacksMessenger = null;
                            this.this$1.this$0.mHandler.setCallbacksMessenger(null);
                            this.this$1.this$0.mState = 3;
                            this.this$1.this$0.mCallback.onConnectionSuspended();
                        }
                    }
                });
            }

            private void postOrRun(Runnable runnable) {
                Runnable r = runnable;
                Runnable runnable2 = r;
                if (Thread.currentThread() != this.this$0.mHandler.getLooper().getThread()) {
                    boolean post = this.this$0.mHandler.post(r);
                } else {
                    r.run();
                }
            }

            /* access modifiers changed from: 0000 */
            public boolean isCurrent(String str) {
                String funcName = str;
                String str2 = funcName;
                if (this.this$0.mServiceConnection == this) {
                    return true;
                }
                if (this.this$0.mState != 0) {
                    int i = Log.i(MediaBrowserCompat.TAG, funcName + " for " + this.this$0.mServiceComponent + " with mServiceConnection=" + this.this$0.mServiceConnection + " this=" + this);
                }
                return false;
            }
        }

        public MediaBrowserImplBase(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            Context context2 = context;
            ComponentName serviceComponent = componentName;
            ConnectionCallback callback = connectionCallback;
            Bundle rootHints = bundle;
            Context context3 = context2;
            ComponentName componentName2 = serviceComponent;
            ConnectionCallback connectionCallback2 = callback;
            Bundle bundle2 = rootHints;
            if (context2 == null) {
                throw new IllegalArgumentException("context must not be null");
            } else if (serviceComponent == null) {
                throw new IllegalArgumentException("service component must not be null");
            } else if (callback != null) {
                this.mContext = context2;
                this.mServiceComponent = serviceComponent;
                this.mCallback = callback;
                this.mRootHints = rootHints != null ? new Bundle(rootHints) : null;
            } else {
                throw new IllegalArgumentException("connection callback must not be null");
            }
        }

        public void connect() {
            if (this.mState != 0) {
                throw new IllegalStateException("connect() called while not disconnected (state=" + getStateLabel(this.mState) + ")");
            } else if (MediaBrowserCompat.DEBUG && this.mServiceConnection != null) {
                throw new RuntimeException("mServiceConnection should be null. Instead it is " + this.mServiceConnection);
            } else if (this.mServiceBinderWrapper != null) {
                throw new RuntimeException("mServiceBinderWrapper should be null. Instead it is " + this.mServiceBinderWrapper);
            } else if (this.mCallbacksMessenger == null) {
                this.mState = 1;
                Intent intent = new Intent(MediaBrowserServiceCompat.SERVICE_INTERFACE);
                Intent intent2 = intent;
                Intent component = intent.setComponent(this.mServiceComponent);
                MediaServiceConnection mediaServiceConnection = new MediaServiceConnection(this);
                MediaServiceConnection mediaServiceConnection2 = mediaServiceConnection;
                this.mServiceConnection = mediaServiceConnection2;
                MediaServiceConnection mediaServiceConnection3 = mediaServiceConnection2;
                boolean bound = false;
                try {
                    bound = this.mContext.bindService(intent2, this.mServiceConnection, 1);
                } catch (Exception e) {
                    Exception exc = e;
                    int e2 = Log.e(MediaBrowserCompat.TAG, "Failed binding to service " + this.mServiceComponent);
                }
                if (!bound) {
                    CallbackHandler callbackHandler = this.mHandler;
                    final MediaServiceConnection mediaServiceConnection4 = mediaServiceConnection3;
                    C01291 r0 = new Runnable(this) {
                        final /* synthetic */ MediaBrowserImplBase this$0;

                        {
                            MediaBrowserImplBase this$02 = r6;
                            ServiceConnection serviceConnection = r7;
                            MediaBrowserImplBase mediaBrowserImplBase = this$02;
                            this.this$0 = this$02;
                        }

                        public void run() {
                            if (mediaServiceConnection4 == this.this$0.mServiceConnection) {
                                this.this$0.forceCloseConnection();
                                this.this$0.mCallback.onConnectionFailed();
                            }
                        }
                    };
                    boolean post = callbackHandler.post(r0);
                }
                if (MediaBrowserCompat.DEBUG) {
                    int d = Log.d(MediaBrowserCompat.TAG, "connect...");
                    dump();
                }
            } else {
                throw new RuntimeException("mCallbacksMessenger should be null. Instead it is " + this.mCallbacksMessenger);
            }
        }

        public void disconnect() {
            if (this.mCallbacksMessenger != null) {
                try {
                    this.mServiceBinderWrapper.disconnect(this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int w = Log.w(MediaBrowserCompat.TAG, "RemoteException during connect for " + this.mServiceComponent);
                }
            }
            forceCloseConnection();
            if (MediaBrowserCompat.DEBUG) {
                int d = Log.d(MediaBrowserCompat.TAG, "disconnect...");
                dump();
            }
        }

        /* access modifiers changed from: 0000 */
        public void forceCloseConnection() {
            if (this.mServiceConnection != null) {
                this.mContext.unbindService(this.mServiceConnection);
            }
            this.mState = 0;
            this.mServiceConnection = null;
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mHandler.setCallbacksMessenger(null);
            this.mRootId = null;
            this.mMediaSessionToken = null;
        }

        public boolean isConnected() {
            return this.mState == 2;
        }

        @NonNull
        public ComponentName getServiceComponent() {
            if (isConnected()) {
                return this.mServiceComponent;
            }
            throw new IllegalStateException("getServiceComponent() called while not connected (state=" + this.mState + ")");
        }

        @NonNull
        public String getRoot() {
            if (isConnected()) {
                return this.mRootId;
            }
            throw new IllegalStateException("getRoot() called while not connected(state=" + getStateLabel(this.mState) + ")");
        }

        @Nullable
        public Bundle getExtras() {
            if (isConnected()) {
                return this.mExtras;
            }
            throw new IllegalStateException("getExtras() called while not connected (state=" + getStateLabel(this.mState) + ")");
        }

        @NonNull
        public Token getSessionToken() {
            if (isConnected()) {
                return this.mMediaSessionToken;
            }
            throw new IllegalStateException("getSessionToken() called while not connected(state=" + this.mState + ")");
        }

        public void subscribe(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            String parentId = str;
            Bundle options = bundle;
            SubscriptionCallback callback = subscriptionCallback;
            String str2 = parentId;
            Bundle bundle2 = options;
            SubscriptionCallback subscriptionCallback2 = callback;
            Subscription subscription = (Subscription) this.mSubscriptions.get(parentId);
            Subscription sub = subscription;
            if (subscription == null) {
                sub = new Subscription();
                Object put = this.mSubscriptions.put(parentId, sub);
            }
            sub.putCallback(options, callback);
            if (this.mState == 2) {
                try {
                    this.mServiceBinderWrapper.addSubscription(parentId, SubscriptionCallback.access$000(callback), options, this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int d = Log.d(MediaBrowserCompat.TAG, "addSubscription failed with RemoteException parentId=" + parentId);
                }
            }
        }

        public void unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            String parentId = str;
            SubscriptionCallback callback = subscriptionCallback;
            String str2 = parentId;
            SubscriptionCallback subscriptionCallback2 = callback;
            Subscription subscription = (Subscription) this.mSubscriptions.get(parentId);
            Subscription sub = subscription;
            if (subscription != null) {
                if (callback != null) {
                    try {
                        List callbacks = sub.getCallbacks();
                        List optionsList = sub.getOptionsList();
                        for (int i = callbacks.size() - 1; i >= 0; i--) {
                            if (callbacks.get(i) == callback) {
                                if (this.mState == 2) {
                                    this.mServiceBinderWrapper.removeSubscription(parentId, SubscriptionCallback.access$000(callback), this.mCallbacksMessenger);
                                }
                                Object remove = callbacks.remove(i);
                                Object remove2 = optionsList.remove(i);
                            }
                        }
                    } catch (RemoteException e) {
                        RemoteException remoteException = e;
                        int d = Log.d(MediaBrowserCompat.TAG, "removeSubscription failed with RemoteException parentId=" + parentId);
                    }
                } else if (this.mState == 2) {
                    this.mServiceBinderWrapper.removeSubscription(parentId, null, this.mCallbacksMessenger);
                }
                if (sub.isEmpty() || callback == null) {
                    Object remove3 = this.mSubscriptions.remove(parentId);
                }
            }
        }

        public void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
            String mediaId = str;
            ItemCallback cb = itemCallback;
            String str2 = mediaId;
            ItemCallback itemCallback2 = cb;
            if (TextUtils.isEmpty(mediaId)) {
                throw new IllegalArgumentException("mediaId is empty");
            } else if (cb == null) {
                throw new IllegalArgumentException("cb is null");
            } else if (this.mState == 2) {
                try {
                    this.mServiceBinderWrapper.getMediaItem(mediaId, new ItemReceiver(mediaId, cb, this.mHandler), this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    RemoteException remoteException = e;
                    int i = Log.i(MediaBrowserCompat.TAG, "Remote error getting media item.");
                    CallbackHandler callbackHandler = this.mHandler;
                    final ItemCallback itemCallback3 = cb;
                    final String str3 = mediaId;
                    C01313 r0 = new Runnable(this) {
                        final /* synthetic */ MediaBrowserImplBase this$0;

                        {
                            MediaBrowserImplBase this$02 = r7;
                            ItemCallback itemCallback = r8;
                            String str = r9;
                            MediaBrowserImplBase mediaBrowserImplBase = this$02;
                            this.this$0 = this$02;
                        }

                        public void run() {
                            itemCallback3.onError(str3);
                        }
                    };
                    boolean post = callbackHandler.post(r0);
                }
            } else {
                int i2 = Log.i(MediaBrowserCompat.TAG, "Not connected, unable to retrieve the MediaItem.");
                CallbackHandler callbackHandler2 = this.mHandler;
                final ItemCallback itemCallback4 = cb;
                final String str4 = mediaId;
                C01302 r02 = new Runnable(this) {
                    final /* synthetic */ MediaBrowserImplBase this$0;

                    {
                        MediaBrowserImplBase this$02 = r7;
                        ItemCallback itemCallback = r8;
                        String str = r9;
                        MediaBrowserImplBase mediaBrowserImplBase = this$02;
                        this.this$0 = this$02;
                    }

                    public void run() {
                        itemCallback4.onError(str4);
                    }
                };
                boolean post2 = callbackHandler2.post(r02);
            }
        }

        public void onServiceConnected(Messenger messenger, String str, Token token, Bundle bundle) {
            Messenger callback = messenger;
            String root = str;
            Token session = token;
            Bundle extra = bundle;
            Messenger messenger2 = callback;
            String str2 = root;
            Token token2 = session;
            Bundle bundle2 = extra;
            if (!isCurrent(callback, "onConnect")) {
                return;
            }
            if (this.mState == 1) {
                this.mRootId = root;
                this.mMediaSessionToken = session;
                this.mExtras = extra;
                this.mState = 2;
                if (MediaBrowserCompat.DEBUG) {
                    int d = Log.d(MediaBrowserCompat.TAG, "ServiceCallbacks.onConnect...");
                    dump();
                }
                this.mCallback.onConnected();
                try {
                    for (Entry entry : this.mSubscriptions.entrySet()) {
                        String id = (String) entry.getKey();
                        Subscription subscription = (Subscription) entry.getValue();
                        Subscription sub = subscription;
                        List callbacks = subscription.getCallbacks();
                        List optionsList = sub.getOptionsList();
                        for (int i = 0; i < callbacks.size(); i++) {
                            this.mServiceBinderWrapper.addSubscription(id, SubscriptionCallback.access$000((SubscriptionCallback) callbacks.get(i)), (Bundle) optionsList.get(i), this.mCallbacksMessenger);
                        }
                    }
                } catch (RemoteException e) {
                    int d2 = Log.d(MediaBrowserCompat.TAG, "addSubscription failed with RemoteException.");
                }
                return;
            }
            int w = Log.w(MediaBrowserCompat.TAG, "onConnect from service while mState=" + getStateLabel(this.mState) + "... ignoring");
        }

        public void onConnectionFailed(Messenger messenger) {
            Messenger callback = messenger;
            Messenger messenger2 = callback;
            int e = Log.e(MediaBrowserCompat.TAG, "onConnectFailed for " + this.mServiceComponent);
            if (!isCurrent(callback, "onConnectFailed")) {
                return;
            }
            if (this.mState == 1) {
                forceCloseConnection();
                this.mCallback.onConnectionFailed();
                return;
            }
            int w = Log.w(MediaBrowserCompat.TAG, "onConnect from service while mState=" + getStateLabel(this.mState) + "... ignoring");
        }

        public void onLoadChildren(Messenger messenger, String str, List list, Bundle bundle) {
            Messenger callback = messenger;
            String parentId = str;
            List list2 = list;
            Bundle options = bundle;
            Messenger messenger2 = callback;
            String str2 = parentId;
            List list3 = list2;
            Bundle bundle2 = options;
            if (isCurrent(callback, "onLoadChildren")) {
                List list4 = list2;
                if (MediaBrowserCompat.DEBUG) {
                    int d = Log.d(MediaBrowserCompat.TAG, "onLoadChildren for " + this.mServiceComponent + " id=" + parentId);
                }
                Subscription subscription = (Subscription) this.mSubscriptions.get(parentId);
                Subscription subscription2 = subscription;
                if (subscription != null) {
                    SubscriptionCallback callback2 = subscription2.getCallback(options);
                    SubscriptionCallback subscriptionCallback = callback2;
                    if (callback2 != null) {
                        if (options != null) {
                            subscriptionCallback.onChildrenLoaded(parentId, list2, options);
                        } else {
                            subscriptionCallback.onChildrenLoaded(parentId, list2);
                        }
                    }
                    return;
                }
                if (MediaBrowserCompat.DEBUG) {
                    int d2 = Log.d(MediaBrowserCompat.TAG, "onLoadChildren for id that isn't subscribed id=" + parentId);
                }
            }
        }

        private static String getStateLabel(int i) {
            int state = i;
            int i2 = state;
            switch (state) {
                case 0:
                    return "CONNECT_STATE_DISCONNECTED";
                case 1:
                    return "CONNECT_STATE_CONNECTING";
                case 2:
                    return "CONNECT_STATE_CONNECTED";
                case 3:
                    return "CONNECT_STATE_SUSPENDED";
                default:
                    return "UNKNOWN/" + state;
            }
        }

        private boolean isCurrent(Messenger messenger, String str) {
            Messenger callback = messenger;
            String funcName = str;
            Messenger messenger2 = callback;
            String str2 = funcName;
            if (this.mCallbacksMessenger == callback) {
                return true;
            }
            if (this.mState != 0) {
                int i = Log.i(MediaBrowserCompat.TAG, funcName + " for " + this.mServiceComponent + " with mCallbacksMessenger=" + this.mCallbacksMessenger + " this=" + this);
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void dump() {
            int d = Log.d(MediaBrowserCompat.TAG, "MediaBrowserCompat...");
            int d2 = Log.d(MediaBrowserCompat.TAG, "  mServiceComponent=" + this.mServiceComponent);
            int d3 = Log.d(MediaBrowserCompat.TAG, "  mCallback=" + this.mCallback);
            int d4 = Log.d(MediaBrowserCompat.TAG, "  mRootHints=" + this.mRootHints);
            int d5 = Log.d(MediaBrowserCompat.TAG, "  mState=" + getStateLabel(this.mState));
            int d6 = Log.d(MediaBrowserCompat.TAG, "  mServiceConnection=" + this.mServiceConnection);
            int d7 = Log.d(MediaBrowserCompat.TAG, "  mServiceBinderWrapper=" + this.mServiceBinderWrapper);
            int d8 = Log.d(MediaBrowserCompat.TAG, "  mCallbacksMessenger=" + this.mCallbacksMessenger);
            int d9 = Log.d(MediaBrowserCompat.TAG, "  mRootId=" + this.mRootId);
            int d10 = Log.d(MediaBrowserCompat.TAG, "  mMediaSessionToken=" + this.mMediaSessionToken);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaBrowserServiceCallbackImpl */
    interface MediaBrowserServiceCallbackImpl {
        void onConnectionFailed(Messenger messenger);

        void onLoadChildren(Messenger messenger, String str, List list, Bundle bundle);

        void onServiceConnected(Messenger messenger, String str, Token token, Bundle bundle);
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaItem */
    public static class MediaItem implements Parcelable {
        public static final Creator<MediaItem> CREATOR = new Creator<MediaItem>() {
            public MediaItem createFromParcel(Parcel parcel) {
                Parcel in = parcel;
                Parcel parcel2 = in;
                return new MediaItem(in);
            }

            public MediaItem[] newArray(int i) {
                int size = i;
                int i2 = size;
                return new MediaItem[size];
            }
        };
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final MediaDescriptionCompat mDescription;
        private final int mFlags;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        /* renamed from: android.support.v4.media.MediaBrowserCompat$MediaItem$Flags */
        public @interface Flags {
        }

        public static MediaItem fromMediaItem(Object obj) {
            Object itemObj = obj;
            Object obj2 = itemObj;
            if (itemObj == null || VERSION.SDK_INT < 21) {
                return null;
            }
            int flags = MediaItem.getFlags(itemObj);
            int i = flags;
            return new MediaItem(MediaDescriptionCompat.fromMediaDescription(MediaItem.getDescription(itemObj)), flags);
        }

        public static List<MediaItem> fromMediaItemList(List<?> list) {
            List<?> itemList = list;
            List<?> list2 = itemList;
            if (itemList == null || VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList(itemList.size());
            for (Object fromMediaItem : itemList) {
                boolean add = arrayList.add(fromMediaItem(fromMediaItem));
            }
            return arrayList;
        }

        public MediaItem(@NonNull MediaDescriptionCompat mediaDescriptionCompat, int i) {
            MediaDescriptionCompat description = mediaDescriptionCompat;
            int flags = i;
            MediaDescriptionCompat mediaDescriptionCompat2 = description;
            int i2 = flags;
            if (description == null) {
                throw new IllegalArgumentException("description cannot be null");
            } else if (!TextUtils.isEmpty(description.getMediaId())) {
                this.mFlags = flags;
                this.mDescription = description;
            } else {
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
        }

        MediaItem(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            this.mFlags = in.readInt();
            this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(in);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Parcel out = parcel;
            int flags = i;
            Parcel parcel2 = out;
            int i2 = flags;
            out.writeInt(this.mFlags);
            this.mDescription.writeToParcel(out, flags);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("MediaItem{");
            StringBuilder sb2 = sb;
            StringBuilder append = sb.append("mFlags=").append(this.mFlags);
            StringBuilder append2 = sb2.append(", mDescription=").append(this.mDescription);
            StringBuilder append3 = sb2.append('}');
            return sb2.toString();
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean isBrowsable() {
            return (this.mFlags & 1) != 0;
        }

        public boolean isPlayable() {
            return (this.mFlags & 2) != 0;
        }

        @NonNull
        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        @NonNull
        public String getMediaId() {
            return this.mDescription.getMediaId();
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$ServiceBinderWrapper */
    private static class ServiceBinderWrapper {
        private Messenger mMessenger;
        private Bundle mRootHints;

        public ServiceBinderWrapper(IBinder iBinder, Bundle bundle) {
            IBinder target = iBinder;
            Bundle rootHints = bundle;
            IBinder iBinder2 = target;
            Bundle bundle2 = rootHints;
            this.mMessenger = new Messenger(target);
            this.mRootHints = rootHints;
        }

        /* access modifiers changed from: 0000 */
        public void connect(Context context, Messenger messenger) throws RemoteException {
            Context context2 = context;
            Messenger callbacksMessenger = messenger;
            Context context3 = context2;
            Messenger messenger2 = callbacksMessenger;
            Bundle bundle = new Bundle();
            Bundle data = bundle;
            bundle.putString(MediaBrowserProtocol.DATA_PACKAGE_NAME, context2.getPackageName());
            data.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, this.mRootHints);
            sendRequest(1, data, callbacksMessenger);
        }

        /* access modifiers changed from: 0000 */
        public void disconnect(Messenger messenger) throws RemoteException {
            Messenger callbacksMessenger = messenger;
            Messenger messenger2 = callbacksMessenger;
            sendRequest(2, null, callbacksMessenger);
        }

        /* access modifiers changed from: 0000 */
        public void addSubscription(String str, IBinder iBinder, Bundle bundle, Messenger messenger) throws RemoteException {
            String parentId = str;
            IBinder callbackToken = iBinder;
            Bundle options = bundle;
            Messenger callbacksMessenger = messenger;
            String str2 = parentId;
            IBinder iBinder2 = callbackToken;
            Bundle bundle2 = options;
            Messenger messenger2 = callbacksMessenger;
            Bundle bundle3 = new Bundle();
            Bundle data = bundle3;
            bundle3.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, parentId);
            BundleCompat.putBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN, callbackToken);
            data.putBundle(MediaBrowserProtocol.DATA_OPTIONS, options);
            sendRequest(3, data, callbacksMessenger);
        }

        /* access modifiers changed from: 0000 */
        public void removeSubscription(String str, IBinder iBinder, Messenger messenger) throws RemoteException {
            String parentId = str;
            IBinder callbackToken = iBinder;
            Messenger callbacksMessenger = messenger;
            String str2 = parentId;
            IBinder iBinder2 = callbackToken;
            Messenger messenger2 = callbacksMessenger;
            Bundle bundle = new Bundle();
            Bundle data = bundle;
            bundle.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, parentId);
            BundleCompat.putBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN, callbackToken);
            sendRequest(4, data, callbacksMessenger);
        }

        /* access modifiers changed from: 0000 */
        public void getMediaItem(String str, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            String mediaId = str;
            ResultReceiver receiver = resultReceiver;
            Messenger callbacksMessenger = messenger;
            String str2 = mediaId;
            ResultReceiver resultReceiver2 = receiver;
            Messenger messenger2 = callbacksMessenger;
            Bundle bundle = new Bundle();
            Bundle data = bundle;
            bundle.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, mediaId);
            data.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, receiver);
            sendRequest(5, data, callbacksMessenger);
        }

        /* access modifiers changed from: 0000 */
        public void registerCallbackMessenger(Messenger messenger) throws RemoteException {
            Messenger callbackMessenger = messenger;
            Messenger messenger2 = callbackMessenger;
            Bundle bundle = new Bundle();
            Bundle data = bundle;
            bundle.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, this.mRootHints);
            sendRequest(6, data, callbackMessenger);
        }

        /* access modifiers changed from: 0000 */
        public void unregisterCallbackMessenger(Messenger messenger) throws RemoteException {
            Messenger callbackMessenger = messenger;
            Messenger messenger2 = callbackMessenger;
            sendRequest(7, null, callbackMessenger);
        }

        private void sendRequest(int i, Bundle bundle, Messenger messenger) throws RemoteException {
            int what = i;
            Bundle data = bundle;
            Messenger cbMessenger = messenger;
            int i2 = what;
            Bundle bundle2 = data;
            Messenger messenger2 = cbMessenger;
            Message obtain = Message.obtain();
            Message msg = obtain;
            obtain.what = what;
            msg.arg1 = 1;
            msg.setData(data);
            msg.replyTo = cbMessenger;
            this.mMessenger.send(msg);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$Subscription */
    private static class Subscription {
        private final List<SubscriptionCallback> mCallbacks = new ArrayList();
        private final List<Bundle> mOptionsList = new ArrayList();

        public Subscription() {
        }

        public boolean isEmpty() {
            return this.mCallbacks.isEmpty();
        }

        public List<Bundle> getOptionsList() {
            return this.mOptionsList;
        }

        public List<SubscriptionCallback> getCallbacks() {
            return this.mCallbacks;
        }

        public SubscriptionCallback getCallback(Bundle bundle) {
            Bundle options = bundle;
            Bundle bundle2 = options;
            for (int i = 0; i < this.mOptionsList.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get(i), options)) {
                    return (SubscriptionCallback) this.mCallbacks.get(i);
                }
            }
            return null;
        }

        public void putCallback(Bundle bundle, SubscriptionCallback subscriptionCallback) {
            Bundle options = bundle;
            SubscriptionCallback callback = subscriptionCallback;
            Bundle bundle2 = options;
            SubscriptionCallback subscriptionCallback2 = callback;
            int i = 0;
            while (i < this.mOptionsList.size()) {
                if (!MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get(i), options)) {
                    i++;
                } else {
                    Object obj = this.mCallbacks.set(i, callback);
                    return;
                }
            }
            boolean add = this.mCallbacks.add(callback);
            boolean add2 = this.mOptionsList.add(options);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompat$SubscriptionCallback */
    public static abstract class SubscriptionCallback {
        private final Object mSubscriptionCallbackObj;
        WeakReference<Subscription> mSubscriptionRef;
        private final IBinder mToken;

        /* renamed from: android.support.v4.media.MediaBrowserCompat$SubscriptionCallback$StubApi21 */
        private class StubApi21 implements SubscriptionCallback {
            final /* synthetic */ SubscriptionCallback this$0;

            StubApi21(SubscriptionCallback subscriptionCallback) {
                this.this$0 = subscriptionCallback;
            }

            public void onChildrenLoaded(@NonNull String str, List<?> list) {
                String parentId = str;
                List<?> children = list;
                String str2 = parentId;
                List<?> list2 = children;
                Subscription sub = this.this$0.mSubscriptionRef != null ? (Subscription) this.this$0.mSubscriptionRef.get() : null;
                if (sub != null) {
                    List fromMediaItemList = MediaItem.fromMediaItemList(children);
                    List callbacks = sub.getCallbacks();
                    List optionsList = sub.getOptionsList();
                    for (int i = 0; i < callbacks.size(); i++) {
                        Bundle bundle = (Bundle) optionsList.get(i);
                        Bundle options = bundle;
                        if (bundle != null) {
                            this.this$0.onChildrenLoaded(parentId, applyOptions(fromMediaItemList, options), options);
                        } else {
                            this.this$0.onChildrenLoaded(parentId, fromMediaItemList);
                        }
                    }
                    return;
                }
                this.this$0.onChildrenLoaded(parentId, MediaItem.fromMediaItemList(children));
            }

            public void onError(@NonNull String str) {
                String parentId = str;
                String str2 = parentId;
                this.this$0.onError(parentId);
            }

            /* access modifiers changed from: 0000 */
            public List<MediaItem> applyOptions(List<MediaItem> list, Bundle bundle) {
                List<MediaItem> list2 = list;
                Bundle options = bundle;
                List<MediaItem> list3 = list2;
                Bundle bundle2 = options;
                if (list2 == null) {
                    return null;
                }
                int page = options.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
                int pageSize = options.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
                if (page == -1 && pageSize == -1) {
                    return list2;
                }
                int i = pageSize * page;
                int fromIndex = i;
                int toIndex = i + pageSize;
                if (page < 0 || pageSize < 1 || fromIndex >= list2.size()) {
                    return Collections.EMPTY_LIST;
                }
                if (toIndex > list2.size()) {
                    toIndex = list2.size();
                }
                return list2.subList(fromIndex, toIndex);
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserCompat$SubscriptionCallback$StubApi24 */
        private class StubApi24 extends StubApi21 implements SubscriptionCallback {
            final /* synthetic */ SubscriptionCallback this$0;

            StubApi24(SubscriptionCallback subscriptionCallback) {
                SubscriptionCallback subscriptionCallback2 = subscriptionCallback;
                this.this$0 = subscriptionCallback2;
                super(subscriptionCallback2);
            }

            public void onChildrenLoaded(@NonNull String str, List<?> list, @NonNull Bundle bundle) {
                String parentId = str;
                List<?> children = list;
                Bundle options = bundle;
                String str2 = parentId;
                List<?> list2 = children;
                Bundle bundle2 = options;
                this.this$0.onChildrenLoaded(parentId, MediaItem.fromMediaItemList(children), options);
            }

            public void onError(@NonNull String str, @NonNull Bundle bundle) {
                String parentId = str;
                Bundle options = bundle;
                String str2 = parentId;
                Bundle bundle2 = options;
                this.this$0.onError(parentId, options);
            }
        }

        static /* synthetic */ IBinder access$000(SubscriptionCallback subscriptionCallback) {
            SubscriptionCallback x0 = subscriptionCallback;
            SubscriptionCallback subscriptionCallback2 = x0;
            return x0.mToken;
        }

        static /* synthetic */ void access$100(SubscriptionCallback subscriptionCallback, Subscription subscription) {
            SubscriptionCallback x0 = subscriptionCallback;
            Subscription x1 = subscription;
            SubscriptionCallback subscriptionCallback2 = x0;
            Subscription subscription2 = x1;
            x0.setSubscription(x1);
        }

        static /* synthetic */ Object access$200(SubscriptionCallback subscriptionCallback) {
            SubscriptionCallback x0 = subscriptionCallback;
            SubscriptionCallback subscriptionCallback2 = x0;
            return x0.mSubscriptionCallbackObj;
        }

        public SubscriptionCallback() {
            if (VERSION.SDK_INT >= 24 || BuildCompat.isAtLeastN()) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi24.createSubscriptionCallback(new StubApi24(this));
                this.mToken = null;
            } else if (VERSION.SDK_INT < 21) {
                this.mSubscriptionCallbackObj = null;
                this.mToken = new Binder();
            } else {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi21.createSubscriptionCallback(new StubApi21(this));
                this.mToken = new Binder();
            }
        }

        public void onChildrenLoaded(@NonNull String str, List<MediaItem> list) {
            String str2 = str;
            List<MediaItem> list2 = list;
        }

        public void onChildrenLoaded(@NonNull String str, List<MediaItem> list, @NonNull Bundle bundle) {
            String str2 = str;
            List<MediaItem> list2 = list;
            Bundle bundle2 = bundle;
        }

        public void onError(@NonNull String str) {
            String str2 = str;
        }

        public void onError(@NonNull String str, @NonNull Bundle bundle) {
            String str2 = str;
            Bundle bundle2 = bundle;
        }

        private void setSubscription(Subscription subscription) {
            Subscription subscription2 = subscription;
            Subscription subscription3 = subscription2;
            this.mSubscriptionRef = new WeakReference<>(subscription2);
        }
    }

    public MediaBrowserCompat(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        Context context2 = context;
        ComponentName serviceComponent = componentName;
        ConnectionCallback callback = connectionCallback;
        Bundle rootHints = bundle;
        Context context3 = context2;
        ComponentName componentName2 = serviceComponent;
        ConnectionCallback connectionCallback2 = callback;
        Bundle bundle2 = rootHints;
        if (VERSION.SDK_INT >= 24 || BuildCompat.isAtLeastN()) {
            this.mImpl = new MediaBrowserImplApi24(context2, serviceComponent, callback, rootHints);
        } else if (VERSION.SDK_INT >= 23) {
            MediaBrowserImplApi23 mediaBrowserImplApi23 = new MediaBrowserImplApi23(context2, serviceComponent, callback, rootHints);
            this.mImpl = mediaBrowserImplApi23;
        } else if (VERSION.SDK_INT < 21) {
            MediaBrowserImplBase mediaBrowserImplBase = new MediaBrowserImplBase(context2, serviceComponent, callback, rootHints);
            this.mImpl = mediaBrowserImplBase;
        } else {
            MediaBrowserImplApi21 mediaBrowserImplApi21 = new MediaBrowserImplApi21(context2, serviceComponent, callback, rootHints);
            this.mImpl = mediaBrowserImplApi21;
        }
    }

    public void connect() {
        this.mImpl.connect();
    }

    public void disconnect() {
        this.mImpl.disconnect();
    }

    public boolean isConnected() {
        return this.mImpl.isConnected();
    }

    @NonNull
    public ComponentName getServiceComponent() {
        return this.mImpl.getServiceComponent();
    }

    @NonNull
    public String getRoot() {
        return this.mImpl.getRoot();
    }

    @Nullable
    public Bundle getExtras() {
        return this.mImpl.getExtras();
    }

    @NonNull
    public Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    public void subscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        String parentId = str;
        SubscriptionCallback callback = subscriptionCallback;
        String str2 = parentId;
        SubscriptionCallback subscriptionCallback2 = callback;
        if (TextUtils.isEmpty(parentId)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (callback != null) {
            this.mImpl.subscribe(parentId, null, callback);
        } else {
            throw new IllegalArgumentException("callback is null");
        }
    }

    public void subscribe(@NonNull String str, @NonNull Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
        String parentId = str;
        Bundle options = bundle;
        SubscriptionCallback callback = subscriptionCallback;
        String str2 = parentId;
        Bundle bundle2 = options;
        SubscriptionCallback subscriptionCallback2 = callback;
        if (TextUtils.isEmpty(parentId)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback is null");
        } else if (options != null) {
            this.mImpl.subscribe(parentId, options, callback);
        } else {
            throw new IllegalArgumentException("options are null");
        }
    }

    public void unsubscribe(@NonNull String str) {
        String parentId = str;
        String str2 = parentId;
        if (!TextUtils.isEmpty(parentId)) {
            this.mImpl.unsubscribe(parentId, null);
            return;
        }
        throw new IllegalArgumentException("parentId is empty");
    }

    public void unsubscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        String parentId = str;
        SubscriptionCallback callback = subscriptionCallback;
        String str2 = parentId;
        SubscriptionCallback subscriptionCallback2 = callback;
        if (TextUtils.isEmpty(parentId)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (callback != null) {
            this.mImpl.unsubscribe(parentId, callback);
        } else {
            throw new IllegalArgumentException("callback is null");
        }
    }

    public void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
        String mediaId = str;
        ItemCallback cb = itemCallback;
        String str2 = mediaId;
        ItemCallback itemCallback2 = cb;
        this.mImpl.getItem(mediaId, cb);
    }
}
