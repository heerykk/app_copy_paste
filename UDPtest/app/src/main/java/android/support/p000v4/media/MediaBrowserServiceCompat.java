package android.support.p000v4.media;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.BundleCompat;
import android.support.p000v4.media.MediaBrowserCompat.MediaItem;
import android.support.p000v4.media.MediaBrowserServiceCompatApi21.ServiceCompatProxy;
import android.support.p000v4.media.session.MediaSessionCompat.Token;
import android.support.p000v4.p002os.BuildCompat;
import android.support.p000v4.p002os.ResultReceiver;
import android.support.p000v4.util.ArrayMap;
import android.support.p000v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* renamed from: android.support.v4.media.MediaBrowserServiceCompat */
public abstract class MediaBrowserServiceCompat extends Service {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final String KEY_MEDIA_ITEM = "media_item";
    static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
    static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    static final String TAG = "MBServiceCompat";
    final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap<>();
    ConnectionRecord mCurConnection;
    final ServiceHandler mHandler = new ServiceHandler(this);
    private MediaBrowserServiceImpl mImpl;
    Token mSession;

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$BrowserRoot */
    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(@NonNull String str, @Nullable Bundle bundle) {
            String rootId = str;
            Bundle extras = bundle;
            String str2 = rootId;
            Bundle bundle2 = extras;
            if (rootId != null) {
                this.mRootId = rootId;
                this.mExtras = extras;
                return;
            }
            throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
        }

        public String getRootId() {
            return this.mRootId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ConnectionRecord */
    private class ConnectionRecord {
        ServiceCallbacks callbacks;
        String pkg;
        BrowserRoot root;
        Bundle rootHints;
        HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap<>();
        final /* synthetic */ MediaBrowserServiceCompat this$0;

        ConnectionRecord(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            this.this$0 = mediaBrowserServiceCompat;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImpl */
    interface MediaBrowserServiceImpl {
        Bundle getBrowserRootHints();

        void notifyChildrenChanged(String str, Bundle bundle);

        IBinder onBind(Intent intent);

        void onCreate();

        void setSessionToken(Token token);
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi21 */
    class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl, ServiceCompatProxy {
        Messenger mMessenger;
        Object mServiceObj;
        final /* synthetic */ MediaBrowserServiceCompat this$0;

        MediaBrowserServiceImplApi21(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            MediaBrowserServiceCompat this$02 = mediaBrowserServiceCompat;
            MediaBrowserServiceCompat mediaBrowserServiceCompat2 = this$02;
            this.this$0 = this$02;
        }

        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi21.createService(this.this$0, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        public IBinder onBind(Intent intent) {
            Intent intent2 = intent;
            Intent intent3 = intent2;
            return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, intent2);
        }

        public void setSessionToken(Token token) {
            Token token2 = token;
            Token token3 = token2;
            MediaBrowserServiceCompatApi21.setSessionToken(this.mServiceObj, token2.getToken());
        }

        public void notifyChildrenChanged(String str, Bundle bundle) {
            String parentId = str;
            Bundle options = bundle;
            String str2 = parentId;
            Bundle bundle2 = options;
            if (this.mMessenger != null) {
                final String str3 = parentId;
                final Bundle bundle3 = options;
                boolean post = this.this$0.mHandler.post(new Runnable(this) {
                    final /* synthetic */ MediaBrowserServiceImplApi21 this$1;

                    {
                        MediaBrowserServiceImplApi21 this$12 = r7;
                        String str = r8;
                        Bundle bundle = r9;
                        MediaBrowserServiceImplApi21 mediaBrowserServiceImplApi21 = this$12;
                        this.this$1 = this$12;
                    }

                    public void run() {
                        for (IBinder binder : this.this$1.this$0.mConnections.keySet()) {
                            ConnectionRecord connectionRecord = (ConnectionRecord) this.this$1.this$0.mConnections.get(binder);
                            ConnectionRecord connection = connectionRecord;
                            List list = (List) connectionRecord.subscriptions.get(str3);
                            List<Pair> list2 = list;
                            if (list != null) {
                                for (Pair pair : list2) {
                                    if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle3, (Bundle) pair.second)) {
                                        this.this$1.this$0.performLoadChildren(str3, connection, (Bundle) pair.second);
                                    }
                                }
                            }
                        }
                    }
                });
                return;
            }
            MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, parentId);
        }

        public Bundle getBrowserRootHints() {
            Bundle bundle;
            if (this.mMessenger == null) {
                return null;
            }
            if (this.this$0.mCurConnection != null) {
                if (this.this$0.mCurConnection.rootHints != null) {
                    bundle = new Bundle(this.this$0.mCurConnection.rootHints);
                } else {
                    bundle = null;
                }
                return bundle;
            }
            throw new IllegalStateException("This should be called inside of onLoadChildren or onLoadItem methods");
        }

        public BrowserRoot onGetRoot(String str, int i, Bundle bundle) {
            String clientPackageName = str;
            int clientUid = i;
            Bundle rootHints = bundle;
            String str2 = clientPackageName;
            int i2 = clientUid;
            Bundle bundle2 = rootHints;
            Bundle rootExtras = null;
            if (!(rootHints == null || rootHints.getInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 0) == 0)) {
                rootHints.remove(MediaBrowserProtocol.EXTRA_CLIENT_VERSION);
                this.mMessenger = new Messenger(this.this$0.mHandler);
                Bundle bundle3 = new Bundle();
                rootExtras = bundle3;
                bundle3.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 1);
                BundleCompat.putBinder(rootExtras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.mMessenger.getBinder());
            }
            BrowserRoot onGetRoot = this.this$0.onGetRoot(clientPackageName, clientUid, rootHints);
            BrowserRoot root = onGetRoot;
            if (onGetRoot == null) {
                return null;
            }
            if (rootExtras == null) {
                rootExtras = root.getExtras();
            } else if (root.getExtras() != null) {
                rootExtras.putAll(root.getExtras());
            }
            return new BrowserRoot(root.getRootId(), rootExtras);
        }

        public void onLoadChildren(String str, ResultWrapper<List<Parcel>> resultWrapper) {
            String parentId = str;
            ResultWrapper<List<Parcel>> resultWrapper2 = resultWrapper;
            String str2 = parentId;
            ResultWrapper<List<Parcel>> resultWrapper3 = resultWrapper2;
            this.this$0.onLoadChildren(parentId, new Result<List<MediaItem>>(this, parentId, resultWrapper2) {
                final /* synthetic */ MediaBrowserServiceImplApi21 this$1;
                final /* synthetic */ ResultWrapper val$resultWrapper;

                {
                    MediaBrowserServiceImplApi21 this$12 = r8;
                    Object debug = r9;
                    ResultWrapper resultWrapper = r10;
                    MediaBrowserServiceImplApi21 mediaBrowserServiceImplApi21 = this$12;
                    Object obj = debug;
                    this.this$1 = this$12;
                    this.val$resultWrapper = resultWrapper;
                }

                /* access modifiers changed from: 0000 */
                public /* bridge */ /* synthetic */ void onResultSent(Object obj, int i) {
                    onResultSent((List) obj, i);
                }

                /* access modifiers changed from: 0000 */
                public void onResultSent(List<MediaItem> list, int i) {
                    List<MediaItem> list2 = list;
                    List<MediaItem> list3 = list2;
                    int i2 = i;
                    ArrayList arrayList = null;
                    if (list2 != null) {
                        arrayList = new ArrayList();
                        for (MediaItem item : list2) {
                            Parcel parcel = Parcel.obtain();
                            item.writeToParcel(parcel, 0);
                            boolean add = arrayList.add(parcel);
                        }
                    }
                    this.val$resultWrapper.sendResult(arrayList);
                }

                public void detach() {
                    this.val$resultWrapper.detach();
                }
            });
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi23 */
    class MediaBrowserServiceImplApi23 extends MediaBrowserServiceImplApi21 implements MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        final /* synthetic */ MediaBrowserServiceCompat this$0;

        MediaBrowserServiceImplApi23(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            MediaBrowserServiceCompat this$02 = mediaBrowserServiceCompat;
            MediaBrowserServiceCompat mediaBrowserServiceCompat2 = this$02;
            this.this$0 = this$02;
            super(this$02);
        }

        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi23.createService(this.this$0, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        public void onLoadItem(String str, ResultWrapper<Parcel> resultWrapper) {
            String itemId = str;
            ResultWrapper<Parcel> resultWrapper2 = resultWrapper;
            String str2 = itemId;
            ResultWrapper<Parcel> resultWrapper3 = resultWrapper2;
            this.this$0.onLoadItem(itemId, new Result<MediaItem>(this, itemId, resultWrapper2) {
                final /* synthetic */ MediaBrowserServiceImplApi23 this$1;
                final /* synthetic */ ResultWrapper val$resultWrapper;

                {
                    MediaBrowserServiceImplApi23 this$12 = r8;
                    Object debug = r9;
                    ResultWrapper resultWrapper = r10;
                    MediaBrowserServiceImplApi23 mediaBrowserServiceImplApi23 = this$12;
                    Object obj = debug;
                    this.this$1 = this$12;
                    this.val$resultWrapper = resultWrapper;
                }

                /* access modifiers changed from: 0000 */
                public /* bridge */ /* synthetic */ void onResultSent(Object obj, int i) {
                    onResultSent((MediaItem) obj, i);
                }

                /* access modifiers changed from: 0000 */
                public void onResultSent(MediaItem mediaItem, int i) {
                    MediaItem item = mediaItem;
                    MediaItem mediaItem2 = item;
                    int i2 = i;
                    if (item != null) {
                        Parcel parcelItem = Parcel.obtain();
                        item.writeToParcel(parcelItem, 0);
                        this.val$resultWrapper.sendResult(parcelItem);
                        return;
                    }
                    this.val$resultWrapper.sendResult(null);
                }

                public void detach() {
                    this.val$resultWrapper.detach();
                }
            });
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi24 */
    class MediaBrowserServiceImplApi24 extends MediaBrowserServiceImplApi23 implements MediaBrowserServiceCompatApi24.ServiceCompatProxy {
        final /* synthetic */ MediaBrowserServiceCompat this$0;

        MediaBrowserServiceImplApi24(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            MediaBrowserServiceCompat this$02 = mediaBrowserServiceCompat;
            MediaBrowserServiceCompat mediaBrowserServiceCompat2 = this$02;
            this.this$0 = this$02;
            super(this$02);
        }

        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi24.createService(this.this$0, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        public void notifyChildrenChanged(String str, Bundle bundle) {
            String parentId = str;
            Bundle options = bundle;
            String str2 = parentId;
            Bundle bundle2 = options;
            if (options != null) {
                MediaBrowserServiceCompatApi24.notifyChildrenChanged(this.mServiceObj, parentId, options);
            } else {
                MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, parentId);
            }
        }

        public void onLoadChildren(String str, ResultWrapper resultWrapper, Bundle bundle) {
            String parentId = str;
            ResultWrapper resultWrapper2 = resultWrapper;
            Bundle options = bundle;
            String str2 = parentId;
            ResultWrapper resultWrapper3 = resultWrapper2;
            Bundle bundle2 = options;
            this.this$0.onLoadChildren(parentId, new Result<List<MediaItem>>(this, parentId, resultWrapper2) {
                final /* synthetic */ MediaBrowserServiceImplApi24 this$1;
                final /* synthetic */ ResultWrapper val$resultWrapper;

                {
                    MediaBrowserServiceImplApi24 this$12 = r8;
                    Object debug = r9;
                    ResultWrapper resultWrapper = r10;
                    MediaBrowserServiceImplApi24 mediaBrowserServiceImplApi24 = this$12;
                    Object obj = debug;
                    this.this$1 = this$12;
                    this.val$resultWrapper = resultWrapper;
                }

                /* access modifiers changed from: 0000 */
                public /* bridge */ /* synthetic */ void onResultSent(Object obj, int i) {
                    onResultSent((List) obj, i);
                }

                /* access modifiers changed from: 0000 */
                public void onResultSent(List<MediaItem> list, int i) {
                    List<MediaItem> list2 = list;
                    int flags = i;
                    List<MediaItem> list3 = list2;
                    int i2 = flags;
                    ArrayList arrayList = null;
                    if (list2 != null) {
                        arrayList = new ArrayList();
                        for (MediaItem item : list2) {
                            Parcel parcel = Parcel.obtain();
                            item.writeToParcel(parcel, 0);
                            boolean add = arrayList.add(parcel);
                        }
                    }
                    this.val$resultWrapper.sendResult(arrayList, flags);
                }

                public void detach() {
                    this.val$resultWrapper.detach();
                }
            }, options);
        }

        public Bundle getBrowserRootHints() {
            return MediaBrowserServiceCompatApi24.getBrowserRootHints(this.mServiceObj);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$MediaBrowserServiceImplBase */
    class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger mMessenger;
        final /* synthetic */ MediaBrowserServiceCompat this$0;

        MediaBrowserServiceImplBase(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            MediaBrowserServiceCompat this$02 = mediaBrowserServiceCompat;
            MediaBrowserServiceCompat mediaBrowserServiceCompat2 = this$02;
            this.this$0 = this$02;
        }

        public void onCreate() {
            this.mMessenger = new Messenger(this.this$0.mHandler);
        }

        public IBinder onBind(Intent intent) {
            Intent intent2 = intent;
            Intent intent3 = intent2;
            if (!MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent2.getAction())) {
                return null;
            }
            return this.mMessenger.getBinder();
        }

        public void setSessionToken(Token token) {
            Token token2 = token;
            Token token3 = token2;
            final Token token4 = token2;
            boolean post = this.this$0.mHandler.post(new Runnable(this) {
                final /* synthetic */ MediaBrowserServiceImplBase this$1;

                {
                    MediaBrowserServiceImplBase this$12 = r6;
                    Token token = r7;
                    MediaBrowserServiceImplBase mediaBrowserServiceImplBase = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    Iterator it = this.this$1.this$0.mConnections.values().iterator();
                    while (it.hasNext()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) it.next();
                        ConnectionRecord connection = connectionRecord;
                        try {
                            connectionRecord.callbacks.onConnect(connection.root.getRootId(), token4, connection.root.getExtras());
                        } catch (RemoteException e) {
                            RemoteException remoteException = e;
                            int w = Log.w(MediaBrowserServiceCompat.TAG, "Connection for " + connection.pkg + " is no longer valid.");
                            it.remove();
                        }
                    }
                }
            });
        }

        public void notifyChildrenChanged(@NonNull String str, Bundle bundle) {
            String parentId = str;
            Bundle options = bundle;
            String str2 = parentId;
            Bundle bundle2 = options;
            final String str3 = parentId;
            final Bundle bundle3 = options;
            boolean post = this.this$0.mHandler.post(new Runnable(this) {
                final /* synthetic */ MediaBrowserServiceImplBase this$1;

                {
                    MediaBrowserServiceImplBase this$12 = r7;
                    String str = r8;
                    Bundle bundle = r9;
                    MediaBrowserServiceImplBase mediaBrowserServiceImplBase = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    for (IBinder binder : this.this$1.this$0.mConnections.keySet()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) this.this$1.this$0.mConnections.get(binder);
                        ConnectionRecord connection = connectionRecord;
                        List list = (List) connectionRecord.subscriptions.get(str3);
                        List<Pair> list2 = list;
                        if (list != null) {
                            for (Pair pair : list2) {
                                if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle3, (Bundle) pair.second)) {
                                    this.this$1.this$0.performLoadChildren(str3, connection, (Bundle) pair.second);
                                }
                            }
                        }
                    }
                }
            });
        }

        public Bundle getBrowserRootHints() {
            if (this.this$0.mCurConnection != null) {
                return this.this$0.mCurConnection.rootHints != null ? new Bundle(this.this$0.mCurConnection.rootHints) : null;
            }
            throw new IllegalStateException("This should be called inside of onLoadChildren or onLoadItem methods");
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$Result */
    public static class Result<T> {
        private Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendResultCalled;

        Result(Object obj) {
            Object debug = obj;
            Object obj2 = debug;
            this.mDebug = debug;
        }

        public void sendResult(T t) {
            T result = t;
            T t2 = result;
            if (!this.mSendResultCalled) {
                this.mSendResultCalled = true;
                onResultSent(result, this.mFlags);
                return;
            }
            throw new IllegalStateException("sendResult() called twice for: " + this.mDebug);
        }

        public void detach() {
            if (this.mDetachCalled) {
                throw new IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug);
            } else if (!this.mSendResultCalled) {
                this.mDetachCalled = true;
            } else {
                throw new IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled;
        }

        /* access modifiers changed from: 0000 */
        public void setFlags(int i) {
            int flags = i;
            int i2 = flags;
            this.mFlags = flags;
        }

        /* access modifiers changed from: 0000 */
        public void onResultSent(T t, int i) {
            T t2 = t;
            int i2 = i;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ServiceBinderImpl */
    private class ServiceBinderImpl {
        final /* synthetic */ MediaBrowserServiceCompat this$0;

        ServiceBinderImpl(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            this.this$0 = mediaBrowserServiceCompat;
        }

        public void connect(String str, int i, Bundle bundle, ServiceCallbacks serviceCallbacks) {
            String pkg = str;
            int uid = i;
            Bundle rootHints = bundle;
            ServiceCallbacks callbacks = serviceCallbacks;
            String str2 = pkg;
            int i2 = uid;
            Bundle bundle2 = rootHints;
            ServiceCallbacks serviceCallbacks2 = callbacks;
            if (this.this$0.isValidPackage(pkg, uid)) {
                ServiceHandler serviceHandler = this.this$0.mHandler;
                final ServiceCallbacks serviceCallbacks3 = callbacks;
                final String str3 = pkg;
                final Bundle bundle3 = rootHints;
                final int i3 = uid;
                C01431 r0 = new Runnable(this) {
                    final /* synthetic */ ServiceBinderImpl this$1;

                    {
                        ServiceBinderImpl this$12 = r9;
                        ServiceCallbacks serviceCallbacks = r10;
                        String str = r11;
                        Bundle bundle = r12;
                        int i = r13;
                        ServiceBinderImpl serviceBinderImpl = this$12;
                        this.this$1 = this$12;
                    }

                    public void run() {
                        IBinder b = serviceCallbacks3.asBinder();
                        Object remove = this.this$1.this$0.mConnections.remove(b);
                        ConnectionRecord connectionRecord = new ConnectionRecord(this.this$1.this$0);
                        ConnectionRecord connection = connectionRecord;
                        connectionRecord.pkg = str3;
                        connection.rootHints = bundle3;
                        connection.callbacks = serviceCallbacks3;
                        connection.root = this.this$1.this$0.onGetRoot(str3, i3, bundle3);
                        if (connection.root != null) {
                            try {
                                Object put = this.this$1.this$0.mConnections.put(b, connection);
                                if (this.this$1.this$0.mSession != null) {
                                    serviceCallbacks3.onConnect(connection.root.getRootId(), this.this$1.this$0.mSession, connection.root.getExtras());
                                }
                            } catch (RemoteException e) {
                                RemoteException remoteException = e;
                                int w = Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnect() failed. Dropping client. pkg=" + str3);
                                Object remove2 = this.this$1.this$0.mConnections.remove(b);
                            }
                        } else {
                            int i = Log.i(MediaBrowserServiceCompat.TAG, "No root for client " + str3 + " from service " + getClass().getName());
                            try {
                                serviceCallbacks3.onConnectFailed();
                            } catch (RemoteException e2) {
                                RemoteException remoteException2 = e2;
                                int w2 = Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnectFailed() failed. Ignoring. pkg=" + str3);
                            }
                        }
                    }
                };
                serviceHandler.postOrRun(r0);
                return;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Package/uid mismatch: uid=" + uid + " package=" + pkg);
            throw illegalArgumentException;
        }

        public void disconnect(ServiceCallbacks serviceCallbacks) {
            ServiceCallbacks callbacks = serviceCallbacks;
            ServiceCallbacks serviceCallbacks2 = callbacks;
            final ServiceCallbacks serviceCallbacks3 = callbacks;
            this.this$0.mHandler.postOrRun(new Runnable(this) {
                final /* synthetic */ ServiceBinderImpl this$1;

                {
                    ServiceBinderImpl this$12 = r6;
                    ServiceCallbacks serviceCallbacks = r7;
                    ServiceBinderImpl serviceBinderImpl = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) this.this$1.this$0.mConnections.remove(serviceCallbacks3.asBinder());
                    ConnectionRecord connectionRecord2 = connectionRecord;
                    if (connectionRecord == null) {
                    }
                }
            });
        }

        public void addSubscription(String str, IBinder iBinder, Bundle bundle, ServiceCallbacks serviceCallbacks) {
            String id = str;
            IBinder token = iBinder;
            Bundle options = bundle;
            ServiceCallbacks callbacks = serviceCallbacks;
            String str2 = id;
            IBinder iBinder2 = token;
            Bundle bundle2 = options;
            ServiceCallbacks serviceCallbacks2 = callbacks;
            final ServiceCallbacks serviceCallbacks3 = callbacks;
            final String str3 = id;
            final IBinder iBinder3 = token;
            final Bundle bundle3 = options;
            this.this$0.mHandler.postOrRun(new Runnable(this) {
                final /* synthetic */ ServiceBinderImpl this$1;

                {
                    ServiceBinderImpl this$12 = r9;
                    ServiceCallbacks serviceCallbacks = r10;
                    String str = r11;
                    IBinder iBinder = r12;
                    Bundle bundle = r13;
                    ServiceBinderImpl serviceBinderImpl = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) this.this$1.this$0.mConnections.get(serviceCallbacks3.asBinder());
                    ConnectionRecord connection = connectionRecord;
                    if (connectionRecord != null) {
                        this.this$1.this$0.addSubscription(str3, connection, iBinder3, bundle3);
                        return;
                    }
                    int w = Log.w(MediaBrowserServiceCompat.TAG, "addSubscription for callback that isn't registered id=" + str3);
                }
            });
        }

        public void removeSubscription(String str, IBinder iBinder, ServiceCallbacks serviceCallbacks) {
            String id = str;
            IBinder token = iBinder;
            ServiceCallbacks callbacks = serviceCallbacks;
            String str2 = id;
            IBinder iBinder2 = token;
            ServiceCallbacks serviceCallbacks2 = callbacks;
            final ServiceCallbacks serviceCallbacks3 = callbacks;
            final String str3 = id;
            final IBinder iBinder3 = token;
            this.this$0.mHandler.postOrRun(new Runnable(this) {
                final /* synthetic */ ServiceBinderImpl this$1;

                {
                    ServiceBinderImpl this$12 = r8;
                    ServiceCallbacks serviceCallbacks = r9;
                    String str = r10;
                    IBinder iBinder = r11;
                    ServiceBinderImpl serviceBinderImpl = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) this.this$1.this$0.mConnections.get(serviceCallbacks3.asBinder());
                    ConnectionRecord connection = connectionRecord;
                    if (connectionRecord != null) {
                        if (!this.this$1.this$0.removeSubscription(str3, connection, iBinder3)) {
                            int w = Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription called for " + str3 + " which is not subscribed");
                        }
                        return;
                    }
                    int w2 = Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription for callback that isn't registered id=" + str3);
                }
            });
        }

        public void getMediaItem(String str, ResultReceiver resultReceiver, ServiceCallbacks serviceCallbacks) {
            String mediaId = str;
            ResultReceiver receiver = resultReceiver;
            ServiceCallbacks callbacks = serviceCallbacks;
            String str2 = mediaId;
            ResultReceiver resultReceiver2 = receiver;
            ServiceCallbacks serviceCallbacks2 = callbacks;
            if (!TextUtils.isEmpty(mediaId) && receiver != null) {
                final ServiceCallbacks serviceCallbacks3 = callbacks;
                final String str3 = mediaId;
                final ResultReceiver resultReceiver3 = receiver;
                this.this$0.mHandler.postOrRun(new Runnable(this) {
                    final /* synthetic */ ServiceBinderImpl this$1;

                    {
                        ServiceBinderImpl this$12 = r8;
                        ServiceCallbacks serviceCallbacks = r9;
                        String str = r10;
                        ResultReceiver resultReceiver = r11;
                        ServiceBinderImpl serviceBinderImpl = this$12;
                        this.this$1 = this$12;
                    }

                    public void run() {
                        ConnectionRecord connectionRecord = (ConnectionRecord) this.this$1.this$0.mConnections.get(serviceCallbacks3.asBinder());
                        ConnectionRecord connection = connectionRecord;
                        if (connectionRecord != null) {
                            this.this$1.this$0.performLoadItem(str3, connection, resultReceiver3);
                            return;
                        }
                        int w = Log.w(MediaBrowserServiceCompat.TAG, "getMediaItem for callback that isn't registered id=" + str3);
                    }
                });
            }
        }

        public void registerCallbacks(ServiceCallbacks serviceCallbacks, Bundle bundle) {
            ServiceCallbacks callbacks = serviceCallbacks;
            Bundle rootHints = bundle;
            ServiceCallbacks serviceCallbacks2 = callbacks;
            Bundle bundle2 = rootHints;
            final ServiceCallbacks serviceCallbacks3 = callbacks;
            final Bundle bundle3 = rootHints;
            this.this$0.mHandler.postOrRun(new Runnable(this) {
                final /* synthetic */ ServiceBinderImpl this$1;

                {
                    ServiceBinderImpl this$12 = r7;
                    ServiceCallbacks serviceCallbacks = r8;
                    Bundle bundle = r9;
                    ServiceBinderImpl serviceBinderImpl = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    IBinder b = serviceCallbacks3.asBinder();
                    Object remove = this.this$1.this$0.mConnections.remove(b);
                    ConnectionRecord connectionRecord = new ConnectionRecord(this.this$1.this$0);
                    ConnectionRecord connection = connectionRecord;
                    connectionRecord.callbacks = serviceCallbacks3;
                    connection.rootHints = bundle3;
                    Object put = this.this$1.this$0.mConnections.put(b, connection);
                }
            });
        }

        public void unregisterCallbacks(ServiceCallbacks serviceCallbacks) {
            ServiceCallbacks callbacks = serviceCallbacks;
            ServiceCallbacks serviceCallbacks2 = callbacks;
            final ServiceCallbacks serviceCallbacks3 = callbacks;
            this.this$0.mHandler.postOrRun(new Runnable(this) {
                final /* synthetic */ ServiceBinderImpl this$1;

                {
                    ServiceBinderImpl this$12 = r6;
                    ServiceCallbacks serviceCallbacks = r7;
                    ServiceBinderImpl serviceBinderImpl = this$12;
                    this.this$1 = this$12;
                }

                public void run() {
                    Object remove = this.this$1.this$0.mConnections.remove(serviceCallbacks3.asBinder());
                }
            });
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ServiceCallbacks */
    private interface ServiceCallbacks {
        IBinder asBinder();

        void onConnect(String str, Token token, Bundle bundle) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(String str, List<MediaItem> list, Bundle bundle) throws RemoteException;
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ServiceCallbacksCompat */
    private class ServiceCallbacksCompat implements ServiceCallbacks {
        final Messenger mCallbacks;
        final /* synthetic */ MediaBrowserServiceCompat this$0;

        ServiceCallbacksCompat(MediaBrowserServiceCompat mediaBrowserServiceCompat, Messenger messenger) {
            Messenger callbacks = messenger;
            Messenger messenger2 = callbacks;
            this.this$0 = mediaBrowserServiceCompat;
            this.mCallbacks = callbacks;
        }

        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        public void onConnect(String str, Token token, Bundle bundle) throws RemoteException {
            String root = str;
            Token session = token;
            Bundle extras = bundle;
            String str2 = root;
            Token token2 = session;
            Bundle extras2 = extras;
            if (extras == null) {
                extras2 = new Bundle();
            }
            extras2.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 1);
            Bundle bundle2 = new Bundle();
            Bundle data = bundle2;
            bundle2.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, root);
            data.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, session);
            data.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, extras2);
            sendRequest(1, data);
        }

        public void onConnectFailed() throws RemoteException {
            sendRequest(2, null);
        }

        public void onLoadChildren(String str, List<MediaItem> list, Bundle bundle) throws RemoteException {
            String mediaId = str;
            List<MediaItem> list2 = list;
            Bundle options = bundle;
            String str2 = mediaId;
            List<MediaItem> list3 = list2;
            Bundle bundle2 = options;
            Bundle bundle3 = new Bundle();
            Bundle data = bundle3;
            bundle3.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, mediaId);
            data.putBundle(MediaBrowserProtocol.DATA_OPTIONS, options);
            if (list2 != null) {
                data.putParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST, !(list2 instanceof ArrayList) ? new ArrayList(list2) : (ArrayList) list2);
            }
            sendRequest(3, data);
        }

        private void sendRequest(int i, Bundle bundle) throws RemoteException {
            int what = i;
            Bundle data = bundle;
            int i2 = what;
            Bundle bundle2 = data;
            Message obtain = Message.obtain();
            Message msg = obtain;
            obtain.what = what;
            msg.arg1 = 1;
            msg.setData(data);
            this.mCallbacks.send(msg);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompat$ServiceHandler */
    private final class ServiceHandler extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl = new ServiceBinderImpl(this.this$0);
        final /* synthetic */ MediaBrowserServiceCompat this$0;

        ServiceHandler(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            this.this$0 = mediaBrowserServiceCompat;
        }

        public void handleMessage(Message message) {
            Message msg = message;
            Message message2 = msg;
            Bundle data = msg.getData();
            switch (msg.what) {
                case 1:
                    ServiceBinderImpl serviceBinderImpl = this.mServiceBinderImpl;
                    String string = data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME);
                    int i = data.getInt(MediaBrowserProtocol.DATA_CALLING_UID);
                    Bundle bundle = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    ServiceCallbacksCompat serviceCallbacksCompat = new ServiceCallbacksCompat(this.this$0, msg.replyTo);
                    serviceBinderImpl.connect(string, i, bundle, serviceCallbacksCompat);
                    return;
                case 2:
                    this.mServiceBinderImpl.disconnect(new ServiceCallbacksCompat(this.this$0, msg.replyTo));
                    return;
                case 3:
                    ServiceBinderImpl serviceBinderImpl2 = this.mServiceBinderImpl;
                    String string2 = data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID);
                    IBinder binder = BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN);
                    Bundle bundle2 = data.getBundle(MediaBrowserProtocol.DATA_OPTIONS);
                    ServiceCallbacksCompat serviceCallbacksCompat2 = new ServiceCallbacksCompat(this.this$0, msg.replyTo);
                    serviceBinderImpl2.addSubscription(string2, binder, bundle2, serviceCallbacksCompat2);
                    return;
                case 4:
                    this.mServiceBinderImpl.removeSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), new ServiceCallbacksCompat(this.this$0, msg.replyTo));
                    return;
                case 5:
                    this.mServiceBinderImpl.getMediaItem(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(this.this$0, msg.replyTo));
                    return;
                case 6:
                    this.mServiceBinderImpl.registerCallbacks(new ServiceCallbacksCompat(this.this$0, msg.replyTo), data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS));
                    return;
                case 7:
                    this.mServiceBinderImpl.unregisterCallbacks(new ServiceCallbacksCompat(this.this$0, msg.replyTo));
                    return;
                default:
                    int w = Log.w(MediaBrowserServiceCompat.TAG, "Unhandled message: " + msg + "\n  Service version: " + 1 + "\n  Client version: " + msg.arg1);
                    return;
            }
        }

        public boolean sendMessageAtTime(Message message, long j) {
            Message msg = message;
            long uptimeMillis = j;
            Message message2 = msg;
            long j2 = uptimeMillis;
            Bundle data = msg.getData();
            Bundle data2 = data;
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data2.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid());
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        public void postOrRun(Runnable runnable) {
            Runnable r = runnable;
            Runnable runnable2 = r;
            if (Thread.currentThread() != getLooper().getThread()) {
                boolean post = post(r);
            } else {
                r.run();
            }
        }
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaItem>> result);

    public MediaBrowserServiceCompat() {
    }

    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 24 || BuildCompat.isAtLeastN()) {
            this.mImpl = new MediaBrowserServiceImplApi24(this);
        } else if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserServiceImplApi23(this);
        } else if (VERSION.SDK_INT < 21) {
            this.mImpl = new MediaBrowserServiceImplBase(this);
        } else {
            this.mImpl = new MediaBrowserServiceImplApi21(this);
        }
        this.mImpl.onCreate();
    }

    public IBinder onBind(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        return this.mImpl.onBind(intent2);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        FileDescriptor fileDescriptor2 = fileDescriptor;
        PrintWriter printWriter2 = printWriter;
        String[] strArr2 = strArr;
    }

    public void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaItem>> result, @NonNull Bundle bundle) {
        String parentId = str;
        Result<List<MediaItem>> result2 = result;
        String str2 = parentId;
        Result<List<MediaItem>> result3 = result2;
        Bundle bundle2 = bundle;
        result2.setFlags(1);
        onLoadChildren(parentId, result2);
    }

    public void onLoadItem(String str, Result<MediaItem> result) {
        Result<MediaItem> result2 = result;
        String str2 = str;
        Result<MediaItem> result3 = result2;
        result2.setFlags(2);
        result2.sendResult(null);
    }

    public void setSessionToken(Token token) {
        Token token2 = token;
        Token token3 = token2;
        if (token2 == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        } else if (this.mSession == null) {
            this.mSession = token2;
            this.mImpl.setSessionToken(token2);
        } else {
            throw new IllegalStateException("The session token has already been set.");
        }
    }

    @Nullable
    public Token getSessionToken() {
        return this.mSession;
    }

    public final Bundle getBrowserRootHints() {
        return this.mImpl.getBrowserRootHints();
    }

    public void notifyChildrenChanged(@NonNull String str) {
        String parentId = str;
        String str2 = parentId;
        if (parentId != null) {
            this.mImpl.notifyChildrenChanged(parentId, null);
            return;
        }
        throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
    }

    public void notifyChildrenChanged(@NonNull String str, @NonNull Bundle bundle) {
        String parentId = str;
        Bundle options = bundle;
        String str2 = parentId;
        Bundle bundle2 = options;
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        } else if (options != null) {
            this.mImpl.notifyChildrenChanged(parentId, options);
        } else {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isValidPackage(String str, int i) {
        String pkg = str;
        int uid = i;
        String str2 = pkg;
        int i2 = uid;
        if (pkg == null) {
            return false;
        }
        PackageManager packageManager = getPackageManager();
        PackageManager packageManager2 = packageManager;
        String[] packagesForUid = packageManager.getPackagesForUid(uid);
        String[] packages = packagesForUid;
        int N = packagesForUid.length;
        for (int i3 = 0; i3 < N; i3++) {
            if (packages[i3].equals(pkg)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void addSubscription(String str, ConnectionRecord connectionRecord, IBinder iBinder, Bundle bundle) {
        String id = str;
        ConnectionRecord connection = connectionRecord;
        IBinder token = iBinder;
        Bundle options = bundle;
        String str2 = id;
        ConnectionRecord connectionRecord2 = connection;
        IBinder iBinder2 = token;
        Bundle bundle2 = options;
        List list = (List) connection.subscriptions.get(id);
        List<Pair> list2 = list;
        if (list == null) {
            list2 = new ArrayList<>();
        }
        for (Pair pair : list2) {
            if (token == pair.first && MediaBrowserCompatUtils.areSameOptions(options, (Bundle) pair.second)) {
                return;
            }
        }
        List list3 = list2;
        Pair pair2 = new Pair(token, options);
        boolean add = list3.add(pair2);
        Object put = connection.subscriptions.put(id, list2);
        performLoadChildren(id, connection, options);
    }

    /* access modifiers changed from: 0000 */
    public boolean removeSubscription(String str, ConnectionRecord connectionRecord, IBinder iBinder) {
        String id = str;
        ConnectionRecord connection = connectionRecord;
        IBinder token = iBinder;
        String str2 = id;
        ConnectionRecord connectionRecord2 = connection;
        IBinder iBinder2 = token;
        if (token != null) {
            boolean removed = false;
            List list = (List) connection.subscriptions.get(id);
            List list2 = list;
            if (list != null) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (token == ((Pair) it.next()).first) {
                        removed = true;
                        it.remove();
                    }
                }
                if (list2.size() == 0) {
                    Object remove = connection.subscriptions.remove(id);
                }
            }
            return removed;
        }
        return connection.subscriptions.remove(id) != null;
    }

    /* access modifiers changed from: 0000 */
    public void performLoadChildren(String str, ConnectionRecord connectionRecord, Bundle bundle) {
        String parentId = str;
        ConnectionRecord connection = connectionRecord;
        Bundle options = bundle;
        String str2 = parentId;
        ConnectionRecord connectionRecord2 = connection;
        Bundle bundle2 = options;
        C01351 r0 = new Result<List<MediaItem>>(this, parentId, connection, parentId, options) {
            final /* synthetic */ MediaBrowserServiceCompat this$0;
            final /* synthetic */ ConnectionRecord val$connection;
            final /* synthetic */ Bundle val$options;
            final /* synthetic */ String val$parentId;

            {
                MediaBrowserServiceCompat this$02 = r10;
                Object debug = r11;
                ConnectionRecord connectionRecord = r12;
                String str = r13;
                Bundle bundle = r14;
                MediaBrowserServiceCompat mediaBrowserServiceCompat = this$02;
                Object obj = debug;
                this.this$0 = this$02;
                this.val$connection = connectionRecord;
                this.val$parentId = str;
                this.val$options = bundle;
            }

            /* access modifiers changed from: 0000 */
            public /* bridge */ /* synthetic */ void onResultSent(Object obj, int i) {
                onResultSent((List) obj, i);
            }

            /* access modifiers changed from: 0000 */
            public void onResultSent(List<MediaItem> list, int i) {
                List<MediaItem> list2 = list;
                int flags = i;
                List<MediaItem> list3 = list2;
                int i2 = flags;
                if (this.this$0.mConnections.get(this.val$connection.callbacks.asBinder()) == this.val$connection) {
                    try {
                        this.val$connection.callbacks.onLoadChildren(this.val$parentId, (flags & 1) == 0 ? list2 : this.this$0.applyOptions(list2, this.val$options), this.val$options);
                    } catch (RemoteException e) {
                        RemoteException remoteException = e;
                        int w = Log.w(MediaBrowserServiceCompat.TAG, "Calling onLoadChildren() failed for id=" + this.val$parentId + " package=" + this.val$connection.pkg);
                    }
                    return;
                }
                if (MediaBrowserServiceCompat.DEBUG) {
                    int d = Log.d(MediaBrowserServiceCompat.TAG, "Not sending onLoadChildren result for connection that has been disconnected. pkg=" + this.val$connection.pkg + " id=" + this.val$parentId);
                }
            }
        };
        C01351 r19 = r0;
        this.mCurConnection = connection;
        if (options != null) {
            onLoadChildren(parentId, r19, options);
        } else {
            onLoadChildren(parentId, r19);
        }
        this.mCurConnection = null;
        if (!r19.isDone()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connection.pkg + " id=" + parentId);
        }
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

    /* access modifiers changed from: 0000 */
    public void performLoadItem(String str, ConnectionRecord connectionRecord, ResultReceiver resultReceiver) {
        String itemId = str;
        ConnectionRecord connection = connectionRecord;
        ResultReceiver receiver = resultReceiver;
        String str2 = itemId;
        ConnectionRecord connectionRecord2 = connection;
        ResultReceiver resultReceiver2 = receiver;
        C01362 r13 = new Result<MediaItem>(this, itemId, receiver) {
            final /* synthetic */ MediaBrowserServiceCompat this$0;
            final /* synthetic */ ResultReceiver val$receiver;

            {
                MediaBrowserServiceCompat this$02 = r8;
                Object debug = r9;
                ResultReceiver resultReceiver = r10;
                MediaBrowserServiceCompat mediaBrowserServiceCompat = this$02;
                Object obj = debug;
                this.this$0 = this$02;
                this.val$receiver = resultReceiver;
            }

            /* access modifiers changed from: 0000 */
            public /* bridge */ /* synthetic */ void onResultSent(Object obj, int i) {
                onResultSent((MediaItem) obj, i);
            }

            /* access modifiers changed from: 0000 */
            public void onResultSent(MediaItem mediaItem, int i) {
                MediaItem item = mediaItem;
                int flags = i;
                MediaItem mediaItem2 = item;
                int i2 = flags;
                if ((flags & 2) == 0) {
                    Bundle bundle = new Bundle();
                    Bundle bundle2 = bundle;
                    bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, item);
                    this.val$receiver.send(0, bundle2);
                    return;
                }
                this.val$receiver.send(-1, null);
            }
        };
        this.mCurConnection = connection;
        onLoadItem(itemId, r13);
        this.mCurConnection = null;
        if (!r13.isDone()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + itemId);
        }
    }
}
