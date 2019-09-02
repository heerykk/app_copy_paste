package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ListView;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.widget.ListViewCompatGingerbread */
class ListViewCompatGingerbread {
    ListViewCompatGingerbread() {
    }

    static void scrollListBy(ListView listView, int i) {
        ListView listView2 = listView;
        int y = i;
        ListView listView3 = listView2;
        int i2 = y;
        int firstVisiblePosition = listView2.getFirstVisiblePosition();
        int firstPosition = firstVisiblePosition;
        if (firstVisiblePosition != -1) {
            View childAt = listView2.getChildAt(0);
            View firstView = childAt;
            if (childAt != null) {
                int top = firstView.getTop() - y;
                int i3 = top;
                listView2.setSelectionFromTop(firstPosition, top);
            }
        }
    }
}
