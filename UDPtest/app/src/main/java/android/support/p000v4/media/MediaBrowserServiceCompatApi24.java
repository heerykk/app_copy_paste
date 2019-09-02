package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Bundle;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.service.media.MediaBrowserService.Result;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi24 */
class MediaBrowserServiceCompatApi24 {
    private static final String TAG = "MBSCompatApi24";
    /* access modifiers changed from: private */
    public static Field sResultFlags;

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi24$MediaBrowserServiceAdaptor */
    static class MediaBrowserServiceAdaptor extends MediaBrowserServiceAdaptor {
        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            Context context2 = context;
            ServiceCompatProxy serviceWrapper = serviceCompatProxy;
            Context context3 = context2;
            ServiceCompatProxy serviceCompatProxy2 = serviceWrapper;
            super(context2, serviceWrapper);
        }

        public void onLoadChildren(String str, Result<List<MediaItem>> result, Bundle bundle) {
            String parentId = str;
            Result<List<MediaItem>> result2 = result;
            Bundle options = bundle;
            String str2 = parentId;
            Result<List<MediaItem>> result3 = result2;
            Bundle bundle2 = options;
            ((ServiceCompatProxy) this.mServiceProxy).onLoadChildren(parentId, new ResultWrapper(result2), options);
        }
    }

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi24$ResultWrapper */
    static class ResultWrapper {
        Result mResultObj;

        ResultWrapper(Result result) {
            Result result2 = result;
            Result result3 = result2;
            this.mResultObj = result2;
        }

        public void sendResult(List<Parcel> list, int i) {
            List<Parcel> result = list;
            int flags = i;
            List<Parcel> list2 = result;
            int i2 = flags;
            try {
                MediaBrowserServiceCompatApi24.sResultFlags.setInt(this.mResultObj, flags);
            } catch (IllegalAccessException e) {
                IllegalAccessException illegalAccessException = e;
                int w = Log.w(MediaBrowserServiceCompatApi24.TAG, e);
            }
            this.mResultObj.sendResult(parcelListToItemList(result));
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

    /* renamed from: android.support.v4.media.MediaBrowserServiceCompatApi24$ServiceCompatProxy */
    public interface ServiceCompatProxy extends android.support.p000v4.media.MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        void onLoadChildren(String str, ResultWrapper resultWrapper, Bundle bundle);
    }

    MediaBrowserServiceCompatApi24() {
    }

    static {
        try {
            sResultFlags = Result.class.getDeclaredField("mFlags");
            sResultFlags.setAccessible(true);
        } catch (NoSuchFieldException e) {
            NoSuchFieldException noSuchFieldException = e;
            int w = Log.w(TAG, e);
        }
    }

    public static Object createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        Context context2 = context;
        ServiceCompatProxy serviceProxy = serviceCompatProxy;
        Context context3 = context2;
        ServiceCompatProxy serviceCompatProxy2 = serviceProxy;
        return new MediaBrowserServiceAdaptor(context2, serviceProxy);
    }

    public static void notifyChildrenChanged(Object obj, String str, Bundle bundle) {
        Object serviceObj = obj;
        String parentId = str;
        Bundle options = bundle;
        Object obj2 = serviceObj;
        String str2 = parentId;
        Bundle bundle2 = options;
        ((MediaBrowserService) serviceObj).notifyChildrenChanged(parentId, options);
    }

    public static Bundle getBrowserRootHints(Object obj) {
        Object serviceObj = obj;
        Object obj2 = serviceObj;
        return ((MediaBrowserService) serviceObj).getBrowserRootHints();
    }
}
