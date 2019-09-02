package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.SearchView;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.widget.SearchViewCompatIcs */
class SearchViewCompatIcs {

    /* renamed from: android.support.v4.widget.SearchViewCompatIcs$MySearchView */
    public static class MySearchView extends SearchView {
        public MySearchView(Context context) {
            Context context2 = context;
            Context context3 = context2;
            super(context2);
        }

        public void onActionViewCollapsed() {
            setQuery("", false);
            super.onActionViewCollapsed();
        }
    }

    SearchViewCompatIcs() {
    }

    public static View newSearchView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return new MySearchView(context2);
    }

    public static void setImeOptions(View view, int i) {
        View searchView = view;
        int imeOptions = i;
        View view2 = searchView;
        int i2 = imeOptions;
        ((SearchView) searchView).setImeOptions(imeOptions);
    }

    public static void setInputType(View view, int i) {
        View searchView = view;
        int inputType = i;
        View view2 = searchView;
        int i2 = inputType;
        ((SearchView) searchView).setInputType(inputType);
    }
}
