package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.widget.ListView;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.widget.ListViewCompatKitKat */
class ListViewCompatKitKat {
    ListViewCompatKitKat() {
    }

    static void scrollListBy(ListView listView, int i) {
        ListView listView2 = listView;
        int y = i;
        ListView listView3 = listView2;
        int i2 = y;
        listView2.scrollListBy(y);
    }
}
