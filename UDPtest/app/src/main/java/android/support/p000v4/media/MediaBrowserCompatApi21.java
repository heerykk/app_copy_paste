package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import java.util.List;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.MediaBrowserCompatApi21 */
class MediaBrowserCompatApi21 {
    static final String NULL_MEDIA_ITEM_ID = "android.support.v4.media.MediaBrowserCompat.NULL_MEDIA_ITEM";

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi21$ConnectionCallback */
    interface ConnectionCallback {
        void onConnected();

        void onConnectionFailed();

        void onConnectionSuspended();
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi21$ConnectionCallbackProxy */
    static class ConnectionCallbackProxy<T extends ConnectionCallback> extends android.media.browse.MediaBrowser.ConnectionCallback {
        protected final T mConnectionCallback;

        public ConnectionCallbackProxy(T t) {
            T connectionCallback = t;
            T t2 = connectionCallback;
            this.mConnectionCallback = connectionCallback;
        }

        public void onConnected() {
            this.mConnectionCallback.onConnected();
        }

        public void onConnectionSuspended() {
            this.mConnectionCallback.onConnectionSuspended();
        }

        public void onConnectionFailed() {
            this.mConnectionCallback.onConnectionFailed();
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi21$MediaItem */
    static class MediaItem {
        MediaItem() {
        }

        public static int getFlags(Object obj) {
            Object itemObj = obj;
            Object obj2 = itemObj;
            return ((android.media.browse.MediaBrowser.MediaItem) itemObj).getFlags();
        }

        public static Object getDescription(Object obj) {
            Object itemObj = obj;
            Object obj2 = itemObj;
            return ((android.media.browse.MediaBrowser.MediaItem) itemObj).getDescription();
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi21$SubscriptionCallback */
    interface SubscriptionCallback {
        void onChildrenLoaded(@NonNull String str, List<?> list);

        void onError(@NonNull String str);
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi21$SubscriptionCallbackProxy */
    static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends android.media.browse.MediaBrowser.SubscriptionCallback {
        protected final T mSubscriptionCallback;

        public SubscriptionCallbackProxy(T t) {
            T callback = t;
            T t2 = callback;
            this.mSubscriptionCallback = callback;
        }

        public void onChildrenLoaded(@NonNull String str, List<android.media.browse.MediaBrowser.MediaItem> list) {
            String parentId = str;
            List<android.media.browse.MediaBrowser.MediaItem> children = list;
            String str2 = parentId;
            List<android.media.browse.MediaBrowser.MediaItem> list2 = children;
            this.mSubscriptionCallback.onChildrenLoaded(parentId, children);
        }

        public void onError(@NonNull String str) {
            String parentId = str;
            String str2 = parentId;
            this.mSubscriptionCallback.onError(parentId);
        }
    }

    MediaBrowserCompatApi21() {
    }

    public static Object createConnectionCallback(ConnectionCallback connectionCallback) {
        ConnectionCallback callback = connectionCallback;
        ConnectionCallback connectionCallback2 = callback;
        return new ConnectionCallbackProxy(callback);
    }

    public static Object createBrowser(Context context, ComponentName componentName, Object obj, Bundle bundle) {
        Context context2 = context;
        ComponentName serviceComponent = componentName;
        Object callback = obj;
        Bundle rootHints = bundle;
        Context context3 = context2;
        ComponentName componentName2 = serviceComponent;
        Object obj2 = callback;
        Bundle bundle2 = rootHints;
        return new MediaBrowser(context2, serviceComponent, (android.media.browse.MediaBrowser.ConnectionCallback) callback, rootHints);
    }

    public static void connect(Object obj) {
        Object browserObj = obj;
        Object obj2 = browserObj;
        ((MediaBrowser) browserObj).connect();
    }

    public static void disconnect(Object obj) {
        Object browserObj = obj;
        Object obj2 = browserObj;
        ((MediaBrowser) browserObj).disconnect();
    }

    public static boolean isConnected(Object obj) {
        Object browserObj = obj;
        Object obj2 = browserObj;
        return ((MediaBrowser) browserObj).isConnected();
    }

    public static ComponentName getServiceComponent(Object obj) {
        Object browserObj = obj;
        Object obj2 = browserObj;
        return ((MediaBrowser) browserObj).getServiceComponent();
    }

    public static String getRoot(Object obj) {
        Object browserObj = obj;
        Object obj2 = browserObj;
        return ((MediaBrowser) browserObj).getRoot();
    }

    public static Bundle getExtras(Object obj) {
        Object browserObj = obj;
        Object obj2 = browserObj;
        return ((MediaBrowser) browserObj).getExtras();
    }

    public static Object getSessionToken(Object obj) {
        Object browserObj = obj;
        Object obj2 = browserObj;
        return ((MediaBrowser) browserObj).getSessionToken();
    }

    public static Object createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        SubscriptionCallback callback = subscriptionCallback;
        SubscriptionCallback subscriptionCallback2 = callback;
        return new SubscriptionCallbackProxy(callback);
    }

    public static void subscribe(Object obj, String str, Object obj2) {
        Object browserObj = obj;
        String parentId = str;
        Object subscriptionCallbackObj = obj2;
        Object obj3 = browserObj;
        String str2 = parentId;
        Object obj4 = subscriptionCallbackObj;
        ((MediaBrowser) browserObj).subscribe(parentId, (android.media.browse.MediaBrowser.SubscriptionCallback) subscriptionCallbackObj);
    }

    public static void unsubscribe(Object obj, String str) {
        Object browserObj = obj;
        String parentId = str;
        Object obj2 = browserObj;
        String str2 = parentId;
        ((MediaBrowser) browserObj).unsubscribe(parentId);
    }
}
