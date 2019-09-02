package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import java.util.List;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.media.MediaBrowserCompatApi24 */
class MediaBrowserCompatApi24 {

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi24$SubscriptionCallback */
    interface SubscriptionCallback extends SubscriptionCallback {
        void onChildrenLoaded(@NonNull String str, List<?> list, @NonNull Bundle bundle);

        void onError(@NonNull String str, @NonNull Bundle bundle);
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi24$SubscriptionCallbackProxy */
    static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends SubscriptionCallbackProxy<T> {
        public SubscriptionCallbackProxy(T t) {
            T callback = t;
            T t2 = callback;
            super(callback);
        }

        public void onChildrenLoaded(@NonNull String str, List<MediaItem> list, @NonNull Bundle bundle) {
            String parentId = str;
            List<MediaItem> children = list;
            Bundle options = bundle;
            String str2 = parentId;
            List<MediaItem> list2 = children;
            Bundle bundle2 = options;
            ((SubscriptionCallback) this.mSubscriptionCallback).onChildrenLoaded(parentId, children, options);
        }

        public void onError(@NonNull String str, @NonNull Bundle bundle) {
            String parentId = str;
            Bundle options = bundle;
            String str2 = parentId;
            Bundle bundle2 = options;
            ((SubscriptionCallback) this.mSubscriptionCallback).onError(parentId, options);
        }
    }

    MediaBrowserCompatApi24() {
    }

    public static Object createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        SubscriptionCallback callback = subscriptionCallback;
        SubscriptionCallback subscriptionCallback2 = callback;
        return new SubscriptionCallbackProxy(callback);
    }

    public static void subscribe(Object obj, String str, Bundle bundle, Object obj2) {
        Object browserObj = obj;
        String parentId = str;
        Bundle options = bundle;
        Object subscriptionCallbackObj = obj2;
        Object obj3 = browserObj;
        String str2 = parentId;
        Bundle bundle2 = options;
        Object obj4 = subscriptionCallbackObj;
        ((MediaBrowser) browserObj).subscribe(parentId, options, (android.media.browse.MediaBrowser.SubscriptionCallback) subscriptionCallbackObj);
    }

    public static void unsubscribe(Object obj, String str, Object obj2) {
        Object browserObj = obj;
        String parentId = str;
        Object subscriptionCallbackObj = obj2;
        Object obj3 = browserObj;
        String str2 = parentId;
        Object obj4 = subscriptionCallbackObj;
        ((MediaBrowser) browserObj).unsubscribe(parentId, (android.media.browse.MediaBrowser.SubscriptionCallback) subscriptionCallbackObj);
    }
}
