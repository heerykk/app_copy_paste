package android.support.p000v4.widget;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.widget.ListView;

/* renamed from: android.support.v4.widget.ListViewCompat */
public final class ListViewCompat {
    public static void scrollListBy(@NonNull ListView listView, int i) {
        ListView listView2 = listView;
        int y = i;
        ListView listView3 = listView2;
        int i2 = y;
        if (VERSION.SDK_INT < 19) {
            ListViewCompatGingerbread.scrollListBy(listView2, y);
        } else {
            ListViewCompatKitKat.scrollListBy(listView2, y);
        }
    }

    private ListViewCompat() {
    }
}
