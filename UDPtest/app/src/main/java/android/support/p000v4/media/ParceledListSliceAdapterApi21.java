package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.media.browse.MediaBrowser.MediaItem;
import android.support.annotation.RequiresApi;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.media.ParceledListSliceAdapterApi21 */
class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    ParceledListSliceAdapterApi21() {
    }

    static {
        try {
            Class cls = Class.forName("android.content.pm.ParceledListSlice");
            Class cls2 = cls;
            Class cls3 = cls;
            Class[] clsArr = new Class[1];
            clsArr[0] = List.class;
            sConstructor = cls3.getConstructor(clsArr);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            ReflectiveOperationException reflectiveOperationException = e;
            e.printStackTrace();
        }
    }

    static Object newInstance(List<MediaItem> list) {
        List<MediaItem> itemList = list;
        List<MediaItem> list2 = itemList;
        try {
            return sConstructor.newInstance(new Object[]{itemList});
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            ReflectiveOperationException reflectiveOperationException = e;
            e.printStackTrace();
            return null;
        }
    }
}
