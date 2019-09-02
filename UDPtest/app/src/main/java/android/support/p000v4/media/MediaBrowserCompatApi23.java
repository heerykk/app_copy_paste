package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.media.MediaBrowserCompatApi23 */
class MediaBrowserCompatApi23 {

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi23$ItemCallback */
    interface ItemCallback {
        void onError(@NonNull String str);

        void onItemLoaded(Parcel parcel);
    }

    /* renamed from: android.support.v4.media.MediaBrowserCompatApi23$ItemCallbackProxy */
    static class ItemCallbackProxy<T extends ItemCallback> extends android.media.browse.MediaBrowser.ItemCallback {
        protected final T mItemCallback;

        public ItemCallbackProxy(T t) {
            T callback = t;
            T t2 = callback;
            this.mItemCallback = callback;
        }

        public void onItemLoaded(MediaItem mediaItem) {
            MediaItem item = mediaItem;
            MediaItem mediaItem2 = item;
            Parcel parcel = Parcel.obtain();
            item.writeToParcel(parcel, 0);
            this.mItemCallback.onItemLoaded(parcel);
        }

        public void onError(@NonNull String str) {
            String itemId = str;
            String str2 = itemId;
            this.mItemCallback.onError(itemId);
        }
    }

    MediaBrowserCompatApi23() {
    }

    public static Object createItemCallback(ItemCallback itemCallback) {
        ItemCallback callback = itemCallback;
        ItemCallback itemCallback2 = callback;
        return new ItemCallbackProxy(callback);
    }

    public static void getItem(Object obj, String str, Object obj2) {
        Object browserObj = obj;
        String mediaId = str;
        Object itemCallbackObj = obj2;
        Object obj3 = browserObj;
        String str2 = mediaId;
        Object obj4 = itemCallbackObj;
        ((MediaBrowser) browserObj).getItem(mediaId, (android.media.browse.MediaBrowser.ItemCallback) itemCallbackObj);
    }
}
