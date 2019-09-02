package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser.MediaItem;
import android.media.session.MediaSession.Token;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.service.media.MediaBrowserService.Result;
import android.support.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21 */
class MediaBrowserServiceCompatApi21 {

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21$BrowserRoot */
    static class BrowserRoot {
        final Bundle mExtras;
        final String mRootId;

        BrowserRoot(String str, Bundle bundle) {
            String rootId = str;
            Bundle extras = bundle;
            String str2 = rootId;
            Bundle bundle2 = extras;
            this.mRootId = rootId;
            this.mExtras = extras;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21$MediaBrowserServiceAdaptor */
    static class MediaBrowserServiceAdaptor extends MediaBrowserService {
        final ServiceCompatProxy mServiceProxy;

        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            Context context2 = context;
            ServiceCompatProxy serviceWrapper = serviceCompatProxy;
            Context context3 = context2;
            ServiceCompatProxy serviceCompatProxy2 = serviceWrapper;
            attachBaseContext(context2);
            this.mServiceProxy = serviceWrapper;
        }

        public android.service.media.MediaBrowserService.BrowserRoot onGetRoot(String str, int i, Bundle bundle) {
            android.service.media.MediaBrowserService.BrowserRoot browserRoot;
            String clientPackageName = str;
            int clientUid = i;
            Bundle rootHints = bundle;
            String str2 = clientPackageName;
            int i2 = clientUid;
            Bundle bundle2 = rootHints;
            BrowserRoot onGetRoot = this.mServiceProxy.onGetRoot(clientPackageName, clientUid, rootHints);
            BrowserRoot browserRoot2 = onGetRoot;
            if (onGetRoot != null) {
                browserRoot = new android.service.media.MediaBrowserService.BrowserRoot(browserRoot2.mRootId, browserRoot2.mExtras);
            } else {
                browserRoot = null;
            }
            return browserRoot;
        }

        public void onLoadChildren(String str, Result<List<MediaItem>> result) {
            String parentId = str;
            Result<List<MediaItem>> result2 = result;
            String str2 = parentId;
            Result<List<MediaItem>> result3 = result2;
            this.mServiceProxy.onLoadChildren(parentId, new ResultWrapper(result2));
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21$ResultWrapper */
    static class ResultWrapper<T> {
        Result mResultObj;

        ResultWrapper(Result result) {
            Result result2 = result;
            Result result3 = result2;
            this.mResultObj = result2;
        }

        public void sendResult(T t) {
            T result = t;
            T t2 = result;
            if (result instanceof List) {
                this.mResultObj.sendResult(parcelListToItemList((List) result));
            } else if (!(result instanceof Parcel)) {
                this.mResultObj.sendResult(null);
            } else {
                Parcel parcel = (Parcel) result;
                this.mResultObj.sendResult(MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
        }

        public void detach() {
            this.mResultObj.detach();
        }

        /* access modifiers changed from: 0000 */
        public List<MediaItem> parcelListToItemList(List<Parcel> list) {
            List<Parcel> parcelList = list;
            List<Parcel> list2 = parcelList;
            if (parcelList == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Parcel parcel : parcelList) {
                Parcel parcel2 = parcel;
                parcel.setDataPosition(0);
                boolean add = arrayList.add(MediaItem.CREATOR.createFromParcel(parcel2));
                parcel2.recycle();
            }
            return arrayList;
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi21$ServiceCompatProxy */
    public interface ServiceCompatProxy {
        BrowserRoot onGetRoot(String str, int i, Bundle bundle);

        void onLoadChildren(String str, ResultWrapper<List<Parcel>> resultWrapper);
    }

    MediaBrowserServiceCompatApi21() {
    }

    public static Object createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        Context context2 = context;
        ServiceCompatProxy serviceProxy = serviceCompatProxy;
        Context context3 = context2;
        ServiceCompatProxy serviceCompatProxy2 = serviceProxy;
        return new MediaBrowserServiceAdaptor(context2, serviceProxy);
    }

    public static void onCreate(Object obj) {
        Object serviceObj = obj;
        Object obj2 = serviceObj;
        ((MediaBrowserService) serviceObj).onCreate();
    }

    public static IBinder onBind(Object obj, Intent intent) {
        Object serviceObj = obj;
        Intent intent2 = intent;
        Object obj2 = serviceObj;
        Intent intent3 = intent2;
        return ((MediaBrowserService) serviceObj).onBind(intent2);
    }

    public static void setSessionToken(Object obj, Object obj2) {
        Object serviceObj = obj;
        Object token = obj2;
        Object obj3 = serviceObj;
        Object obj4 = token;
        ((MediaBrowserService) serviceObj).setSessionToken((Token) token);
    }

    public static void notifyChildrenChanged(Object obj, String str) {
        Object serviceObj = obj;
        String parentId = str;
        Object obj2 = serviceObj;
        String str2 = parentId;
        ((MediaBrowserService) serviceObj).notifyChildrenChanged(parentId);
    }
}
