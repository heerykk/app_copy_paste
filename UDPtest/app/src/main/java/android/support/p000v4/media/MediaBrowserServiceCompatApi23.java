package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Parcel;
import android.service.media.MediaBrowserService.Result;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi23 */
class MediaBrowserServiceCompatApi23 {

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi23$MediaBrowserServiceAdaptor */
    static class MediaBrowserServiceAdaptor extends MediaBrowserServiceAdaptor {
        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            Context context2 = context;
            ServiceCompatProxy serviceWrapper = serviceCompatProxy;
            Context context3 = context2;
            ServiceCompatProxy serviceCompatProxy2 = serviceWrapper;
            super(context2, serviceWrapper);
        }

        public void onLoadItem(String str, Result<MediaItem> result) {
            String itemId = str;
            Result<MediaItem> result2 = result;
            String str2 = itemId;
            Result<MediaItem> result3 = result2;
            ((ServiceCompatProxy) this.mServiceProxy).onLoadItem(itemId, new ResultWrapper(result2));
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi23$ServiceCompatProxy */
    public interface ServiceCompatProxy extends android.support.p000v4.media.MediaBrowserServiceCompatApi21.ServiceCompatProxy {
        void onLoadItem(String str, ResultWrapper<Parcel> resultWrapper);
    }

    MediaBrowserServiceCompatApi23() {
    }

    public static Object createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        Context context2 = context;
        ServiceCompatProxy serviceProxy = serviceCompatProxy;
        Context context3 = context2;
        ServiceCompatProxy serviceCompatProxy2 = serviceProxy;
        return new MediaBrowserServiceAdaptor(context2, serviceProxy);
    }
}
