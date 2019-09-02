package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.widget.SearchViewCompatHoneycomb */
class SearchViewCompatHoneycomb {

    /* renamed from: android.support.v4.widget.SearchViewCompatHoneycomb$OnCloseListenerCompatBridge */
    interface OnCloseListenerCompatBridge {
        boolean onClose();
    }

    /* renamed from: android.support.v4.widget.SearchViewCompatHoneycomb$OnQueryTextListenerCompatBridge */
    interface OnQueryTextListenerCompatBridge {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    SearchViewCompatHoneycomb() {
    }

    public static void checkIfLegalArg(View view) {
        View searchView = view;
        View view2 = searchView;
        if (searchView == null) {
            throw new IllegalArgumentException("searchView must be non-null");
        } else if (!(searchView instanceof SearchView)) {
            throw new IllegalArgumentException("searchView must be an instance ofandroid.widget.SearchView");
        }
    }

    public static View newSearchView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return new SearchView(context2);
    }

    public static void setSearchableInfo(View view, ComponentName componentName) {
        View searchView = view;
        ComponentName searchableComponent = componentName;
        View view2 = searchView;
        ComponentName componentName2 = searchableComponent;
        SearchView searchView2 = (SearchView) searchView;
        searchView2.setSearchableInfo(((SearchManager) searchView2.getContext().getSystemService("search")).getSearchableInfo(searchableComponent));
    }

    public static Object newOnQueryTextListener(OnQueryTextListenerCompatBridge onQueryTextListenerCompatBridge) {
        OnQueryTextListenerCompatBridge listener = onQueryTextListenerCompatBridge;
        OnQueryTextListenerCompatBridge onQueryTextListenerCompatBridge2 = listener;
        final OnQueryTextListenerCompatBridge onQueryTextListenerCompatBridge3 = listener;
        return new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String str) {
                String query = str;
                String str2 = query;
                return onQueryTextListenerCompatBridge3.onQueryTextSubmit(query);
            }

            public boolean onQueryTextChange(String str) {
                String newText = str;
                String str2 = newText;
                return onQueryTextListenerCompatBridge3.onQueryTextChange(newText);
            }
        };
    }

    public static void setOnQueryTextListener(View view, Object obj) {
        View searchView = view;
        Object listener = obj;
        View view2 = searchView;
        Object obj2 = listener;
        ((SearchView) searchView).setOnQueryTextListener((OnQueryTextListener) listener);
    }

    public static Object newOnCloseListener(OnCloseListenerCompatBridge onCloseListenerCompatBridge) {
        OnCloseListenerCompatBridge listener = onCloseListenerCompatBridge;
        OnCloseListenerCompatBridge onCloseListenerCompatBridge2 = listener;
        final OnCloseListenerCompatBridge onCloseListenerCompatBridge3 = listener;
        return new OnCloseListener() {
            public boolean onClose() {
                return onCloseListenerCompatBridge3.onClose();
            }
        };
    }

    public static void setOnCloseListener(View view, Object obj) {
        View searchView = view;
        Object listener = obj;
        View view2 = searchView;
        Object obj2 = listener;
        ((SearchView) searchView).setOnCloseListener((OnCloseListener) listener);
    }

    public static CharSequence getQuery(View view) {
        View searchView = view;
        View view2 = searchView;
        return ((SearchView) searchView).getQuery();
    }

    public static void setQuery(View view, CharSequence charSequence, boolean z) {
        View searchView = view;
        CharSequence query = charSequence;
        View view2 = searchView;
        CharSequence charSequence2 = query;
        ((SearchView) searchView).setQuery(query, z);
    }

    public static void setQueryHint(View view, CharSequence charSequence) {
        View searchView = view;
        CharSequence hint = charSequence;
        View view2 = searchView;
        CharSequence charSequence2 = hint;
        ((SearchView) searchView).setQueryHint(hint);
    }

    public static void setIconified(View view, boolean z) {
        View searchView = view;
        View view2 = searchView;
        ((SearchView) searchView).setIconified(z);
    }

    public static boolean isIconified(View view) {
        View searchView = view;
        View view2 = searchView;
        return ((SearchView) searchView).isIconified();
    }

    public static void setSubmitButtonEnabled(View view, boolean z) {
        View searchView = view;
        View view2 = searchView;
        ((SearchView) searchView).setSubmitButtonEnabled(z);
    }

    public static boolean isSubmitButtonEnabled(View view) {
        View searchView = view;
        View view2 = searchView;
        return ((SearchView) searchView).isSubmitButtonEnabled();
    }

    public static void setQueryRefinementEnabled(View view, boolean z) {
        View searchView = view;
        View view2 = searchView;
        ((SearchView) searchView).setQueryRefinementEnabled(z);
    }

    public static boolean isQueryRefinementEnabled(View view) {
        View searchView = view;
        View view2 = searchView;
        return ((SearchView) searchView).isQueryRefinementEnabled();
    }

    public static void setMaxWidth(View view, int i) {
        View searchView = view;
        int maxpixels = i;
        View view2 = searchView;
        int i2 = maxpixels;
        ((SearchView) searchView).setMaxWidth(maxpixels);
    }
}
