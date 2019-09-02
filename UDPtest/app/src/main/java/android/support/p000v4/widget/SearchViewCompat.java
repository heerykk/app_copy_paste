package android.support.p000v4.widget;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

/* renamed from: android.support.v4.widget.SearchViewCompat */
public final class SearchViewCompat {
    private static final SearchViewCompatImpl IMPL;

    /* renamed from: android.support.v4.widget.SearchViewCompat$OnCloseListener */
    public interface OnCloseListener {
        boolean onClose();
    }

    @Deprecated
    /* renamed from: android.support.v4.widget.SearchViewCompat$OnCloseListenerCompat */
    public static abstract class OnCloseListenerCompat implements OnCloseListener {
        public OnCloseListenerCompat() {
        }

        public boolean onClose() {
            return false;
        }
    }

    /* renamed from: android.support.v4.widget.SearchViewCompat$OnQueryTextListener */
    public interface OnQueryTextListener {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    @Deprecated
    /* renamed from: android.support.v4.widget.SearchViewCompat$OnQueryTextListenerCompat */
    public static abstract class OnQueryTextListenerCompat implements OnQueryTextListener {
        public OnQueryTextListenerCompat() {
        }

        public boolean onQueryTextSubmit(String str) {
            String str2 = str;
            return false;
        }

        public boolean onQueryTextChange(String str) {
            String str2 = str;
            return false;
        }
    }

    /* renamed from: android.support.v4.widget.SearchViewCompat$SearchViewCompatHoneycombImpl */
    static class SearchViewCompatHoneycombImpl extends SearchViewCompatStubImpl {
        SearchViewCompatHoneycombImpl() {
        }

        public View newSearchView(Context context) {
            Context context2 = context;
            Context context3 = context2;
            return SearchViewCompatHoneycomb.newSearchView(context2);
        }

        public void setSearchableInfo(View view, ComponentName componentName) {
            View searchView = view;
            ComponentName searchableComponent = componentName;
            View view2 = searchView;
            ComponentName componentName2 = searchableComponent;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setSearchableInfo(searchView, searchableComponent);
        }

        public Object newOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
            OnQueryTextListener listener = onQueryTextListener;
            OnQueryTextListener onQueryTextListener2 = listener;
            final OnQueryTextListener onQueryTextListener3 = listener;
            return SearchViewCompatHoneycomb.newOnQueryTextListener(new OnQueryTextListenerCompatBridge(this) {
                final /* synthetic */ SearchViewCompatHoneycombImpl this$0;

                {
                    SearchViewCompatHoneycombImpl this$02 = r6;
                    OnQueryTextListener onQueryTextListener = r7;
                    SearchViewCompatHoneycombImpl searchViewCompatHoneycombImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean onQueryTextSubmit(String str) {
                    String query = str;
                    String str2 = query;
                    return onQueryTextListener3.onQueryTextSubmit(query);
                }

                public boolean onQueryTextChange(String str) {
                    String newText = str;
                    String str2 = newText;
                    return onQueryTextListener3.onQueryTextChange(newText);
                }
            });
        }

        public void setOnQueryTextListener(View view, OnQueryTextListener onQueryTextListener) {
            View searchView = view;
            OnQueryTextListener listener = onQueryTextListener;
            View view2 = searchView;
            OnQueryTextListener onQueryTextListener2 = listener;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setOnQueryTextListener(searchView, newOnQueryTextListener(listener));
        }

        public Object newOnCloseListener(OnCloseListener onCloseListener) {
            OnCloseListener listener = onCloseListener;
            OnCloseListener onCloseListener2 = listener;
            final OnCloseListener onCloseListener3 = listener;
            return SearchViewCompatHoneycomb.newOnCloseListener(new OnCloseListenerCompatBridge(this) {
                final /* synthetic */ SearchViewCompatHoneycombImpl this$0;

                {
                    SearchViewCompatHoneycombImpl this$02 = r6;
                    OnCloseListener onCloseListener = r7;
                    SearchViewCompatHoneycombImpl searchViewCompatHoneycombImpl = this$02;
                    this.this$0 = this$02;
                }

                public boolean onClose() {
                    return onCloseListener3.onClose();
                }
            });
        }

        public void setOnCloseListener(View view, OnCloseListener onCloseListener) {
            View searchView = view;
            OnCloseListener listener = onCloseListener;
            View view2 = searchView;
            OnCloseListener onCloseListener2 = listener;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setOnCloseListener(searchView, newOnCloseListener(listener));
        }

        public CharSequence getQuery(View view) {
            View searchView = view;
            View view2 = searchView;
            checkIfLegalArg(searchView);
            return SearchViewCompatHoneycomb.getQuery(searchView);
        }

        public void setQuery(View view, CharSequence charSequence, boolean z) {
            View searchView = view;
            CharSequence query = charSequence;
            View view2 = searchView;
            CharSequence charSequence2 = query;
            boolean submit = z;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setQuery(searchView, query, submit);
        }

        public void setQueryHint(View view, CharSequence charSequence) {
            View searchView = view;
            CharSequence hint = charSequence;
            View view2 = searchView;
            CharSequence charSequence2 = hint;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setQueryHint(searchView, hint);
        }

        public void setIconified(View view, boolean z) {
            View searchView = view;
            View view2 = searchView;
            boolean iconify = z;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setIconified(searchView, iconify);
        }

        public boolean isIconified(View view) {
            View searchView = view;
            View view2 = searchView;
            checkIfLegalArg(searchView);
            return SearchViewCompatHoneycomb.isIconified(searchView);
        }

        public void setSubmitButtonEnabled(View view, boolean z) {
            View searchView = view;
            View view2 = searchView;
            boolean enabled = z;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setSubmitButtonEnabled(searchView, enabled);
        }

        public boolean isSubmitButtonEnabled(View view) {
            View searchView = view;
            View view2 = searchView;
            checkIfLegalArg(searchView);
            return SearchViewCompatHoneycomb.isSubmitButtonEnabled(searchView);
        }

        public void setQueryRefinementEnabled(View view, boolean z) {
            View searchView = view;
            View view2 = searchView;
            boolean enable = z;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setQueryRefinementEnabled(searchView, enable);
        }

        public boolean isQueryRefinementEnabled(View view) {
            View searchView = view;
            View view2 = searchView;
            checkIfLegalArg(searchView);
            return SearchViewCompatHoneycomb.isQueryRefinementEnabled(searchView);
        }

        public void setMaxWidth(View view, int i) {
            View searchView = view;
            int maxpixels = i;
            View view2 = searchView;
            int i2 = maxpixels;
            checkIfLegalArg(searchView);
            SearchViewCompatHoneycomb.setMaxWidth(searchView, maxpixels);
        }

        /* access modifiers changed from: protected */
        public void checkIfLegalArg(View view) {
            View searchView = view;
            View view2 = searchView;
            SearchViewCompatHoneycomb.checkIfLegalArg(searchView);
        }
    }

    /* renamed from: android.support.v4.widget.SearchViewCompat$SearchViewCompatIcsImpl */
    static class SearchViewCompatIcsImpl extends SearchViewCompatHoneycombImpl {
        SearchViewCompatIcsImpl() {
        }

        public View newSearchView(Context context) {
            Context context2 = context;
            Context context3 = context2;
            return SearchViewCompatIcs.newSearchView(context2);
        }

        public void setImeOptions(View view, int i) {
            View searchView = view;
            int imeOptions = i;
            View view2 = searchView;
            int i2 = imeOptions;
            checkIfLegalArg(searchView);
            SearchViewCompatIcs.setImeOptions(searchView, imeOptions);
        }

        public void setInputType(View view, int i) {
            View searchView = view;
            int inputType = i;
            View view2 = searchView;
            int i2 = inputType;
            checkIfLegalArg(searchView);
            SearchViewCompatIcs.setInputType(searchView, inputType);
        }
    }

    /* renamed from: android.support.v4.widget.SearchViewCompat$SearchViewCompatImpl */
    interface SearchViewCompatImpl {
        CharSequence getQuery(View view);

        boolean isIconified(View view);

        boolean isQueryRefinementEnabled(View view);

        boolean isSubmitButtonEnabled(View view);

        Object newOnCloseListener(OnCloseListener onCloseListener);

        Object newOnQueryTextListener(OnQueryTextListener onQueryTextListener);

        View newSearchView(Context context);

        void setIconified(View view, boolean z);

        void setImeOptions(View view, int i);

        void setInputType(View view, int i);

        void setMaxWidth(View view, int i);

        void setOnCloseListener(View view, OnCloseListener onCloseListener);

        void setOnQueryTextListener(View view, OnQueryTextListener onQueryTextListener);

        void setQuery(View view, CharSequence charSequence, boolean z);

        void setQueryHint(View view, CharSequence charSequence);

        void setQueryRefinementEnabled(View view, boolean z);

        void setSearchableInfo(View view, ComponentName componentName);

        void setSubmitButtonEnabled(View view, boolean z);
    }

    /* renamed from: android.support.v4.widget.SearchViewCompat$SearchViewCompatStubImpl */
    static class SearchViewCompatStubImpl implements SearchViewCompatImpl {
        SearchViewCompatStubImpl() {
        }

        public View newSearchView(Context context) {
            Context context2 = context;
            return null;
        }

        public void setSearchableInfo(View view, ComponentName componentName) {
            View view2 = view;
            ComponentName componentName2 = componentName;
        }

        public void setImeOptions(View view, int i) {
            View view2 = view;
            int i2 = i;
        }

        public void setInputType(View view, int i) {
            View view2 = view;
            int i2 = i;
        }

        public Object newOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
            OnQueryTextListener onQueryTextListener2 = onQueryTextListener;
            return null;
        }

        public void setOnQueryTextListener(View view, OnQueryTextListener onQueryTextListener) {
            View view2 = view;
            OnQueryTextListener onQueryTextListener2 = onQueryTextListener;
        }

        public Object newOnCloseListener(OnCloseListener onCloseListener) {
            OnCloseListener onCloseListener2 = onCloseListener;
            return null;
        }

        public void setOnCloseListener(View view, OnCloseListener onCloseListener) {
            View view2 = view;
            OnCloseListener onCloseListener2 = onCloseListener;
        }

        public CharSequence getQuery(View view) {
            View view2 = view;
            return null;
        }

        public void setQuery(View view, CharSequence charSequence, boolean z) {
            View view2 = view;
            CharSequence charSequence2 = charSequence;
            boolean z2 = z;
        }

        public void setQueryHint(View view, CharSequence charSequence) {
            View view2 = view;
            CharSequence charSequence2 = charSequence;
        }

        public void setIconified(View view, boolean z) {
            View view2 = view;
            boolean z2 = z;
        }

        public boolean isIconified(View view) {
            View view2 = view;
            return true;
        }

        public void setSubmitButtonEnabled(View view, boolean z) {
            View view2 = view;
            boolean z2 = z;
        }

        public boolean isSubmitButtonEnabled(View view) {
            View view2 = view;
            return false;
        }

        public void setQueryRefinementEnabled(View view, boolean z) {
            View view2 = view;
            boolean z2 = z;
        }

        public boolean isQueryRefinementEnabled(View view) {
            View view2 = view;
            return false;
        }

        public void setMaxWidth(View view, int i) {
            View view2 = view;
            int i2 = i;
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new SearchViewCompatIcsImpl();
        } else if (VERSION.SDK_INT < 11) {
            IMPL = new SearchViewCompatStubImpl();
        } else {
            IMPL = new SearchViewCompatHoneycombImpl();
        }
    }

    private SearchViewCompat(Context context) {
        Context context2 = context;
    }

    public static View newSearchView(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return IMPL.newSearchView(context2);
    }

    public static void setSearchableInfo(View view, ComponentName componentName) {
        View searchView = view;
        ComponentName searchableComponent = componentName;
        View view2 = searchView;
        ComponentName componentName2 = searchableComponent;
        IMPL.setSearchableInfo(searchView, searchableComponent);
    }

    public static void setImeOptions(View view, int i) {
        View searchView = view;
        int imeOptions = i;
        View view2 = searchView;
        int i2 = imeOptions;
        IMPL.setImeOptions(searchView, imeOptions);
    }

    public static void setInputType(View view, int i) {
        View searchView = view;
        int inputType = i;
        View view2 = searchView;
        int i2 = inputType;
        IMPL.setInputType(searchView, inputType);
    }

    public static void setOnQueryTextListener(View view, OnQueryTextListener onQueryTextListener) {
        View searchView = view;
        OnQueryTextListener listener = onQueryTextListener;
        View view2 = searchView;
        OnQueryTextListener onQueryTextListener2 = listener;
        IMPL.setOnQueryTextListener(searchView, listener);
    }

    public static void setOnCloseListener(View view, OnCloseListener onCloseListener) {
        View searchView = view;
        OnCloseListener listener = onCloseListener;
        View view2 = searchView;
        OnCloseListener onCloseListener2 = listener;
        IMPL.setOnCloseListener(searchView, listener);
    }

    public static CharSequence getQuery(View view) {
        View searchView = view;
        View view2 = searchView;
        return IMPL.getQuery(searchView);
    }

    public static void setQuery(View view, CharSequence charSequence, boolean z) {
        View searchView = view;
        CharSequence query = charSequence;
        View view2 = searchView;
        CharSequence charSequence2 = query;
        IMPL.setQuery(searchView, query, z);
    }

    public static void setQueryHint(View view, CharSequence charSequence) {
        View searchView = view;
        CharSequence hint = charSequence;
        View view2 = searchView;
        CharSequence charSequence2 = hint;
        IMPL.setQueryHint(searchView, hint);
    }

    public static void setIconified(View view, boolean z) {
        View searchView = view;
        View view2 = searchView;
        IMPL.setIconified(searchView, z);
    }

    public static boolean isIconified(View view) {
        View searchView = view;
        View view2 = searchView;
        return IMPL.isIconified(searchView);
    }

    public static void setSubmitButtonEnabled(View view, boolean z) {
        View searchView = view;
        View view2 = searchView;
        IMPL.setSubmitButtonEnabled(searchView, z);
    }

    public static boolean isSubmitButtonEnabled(View view) {
        View searchView = view;
        View view2 = searchView;
        return IMPL.isSubmitButtonEnabled(searchView);
    }

    public static void setQueryRefinementEnabled(View view, boolean z) {
        View searchView = view;
        View view2 = searchView;
        IMPL.setQueryRefinementEnabled(searchView, z);
    }

    public static boolean isQueryRefinementEnabled(View view) {
        View searchView = view;
        View view2 = searchView;
        return IMPL.isQueryRefinementEnabled(searchView);
    }

    public static void setMaxWidth(View view, int i) {
        View searchView = view;
        int maxpixels = i;
        View view2 = searchView;
        int i2 = maxpixels;
        IMPL.setMaxWidth(searchView, maxpixels);
    }
}
