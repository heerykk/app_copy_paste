package android.support.p003v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.widget.ResourceCursorAdapter;
import android.support.p003v7.appcompat.C0268R;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

/* renamed from: android.support.v7.widget.SuggestionsAdapter */
class SuggestionsAdapter extends ResourceCursorAdapter implements OnClickListener {
    private static final boolean DBG = false;
    static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = "SuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    static final int REFINE_ALL = 2;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_NONE = 0;
    private boolean mClosed = false;
    private final int mCommitIconResId;
    private int mFlagsCol = -1;
    private int mIconName1Col = -1;
    private int mIconName2Col = -1;
    private final WeakHashMap<String, ConstantState> mOutsideDrawablesCache;
    private final Context mProviderContext;
    private int mQueryRefinement = 1;
    private final SearchManager mSearchManager = ((SearchManager) this.mContext.getSystemService("search"));
    private final SearchView mSearchView;
    private final SearchableInfo mSearchable;
    private int mText1Col = -1;
    private int mText2Col = -1;
    private int mText2UrlCol = -1;
    private ColorStateList mUrlColor;

    /* renamed from: android.support.v7.widget.SuggestionsAdapter$ChildViewCache */
    private static final class ChildViewCache {
        public final ImageView mIcon1;
        public final ImageView mIcon2;
        public final ImageView mIconRefine;
        public final TextView mText1;
        public final TextView mText2;

        public ChildViewCache(View view) {
            View v = view;
            View view2 = v;
            this.mText1 = (TextView) v.findViewById(16908308);
            this.mText2 = (TextView) v.findViewById(16908309);
            this.mIcon1 = (ImageView) v.findViewById(16908295);
            this.mIcon2 = (ImageView) v.findViewById(16908296);
            this.mIconRefine = (ImageView) v.findViewById(C0268R.C0270id.edit_query);
        }
    }

    public SuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, ConstantState> weakHashMap) {
        Context context2 = context;
        SearchView searchView2 = searchView;
        SearchableInfo searchable = searchableInfo;
        WeakHashMap<String, ConstantState> outsideDrawablesCache = weakHashMap;
        Context context3 = context2;
        SearchView searchView3 = searchView2;
        SearchableInfo searchableInfo2 = searchable;
        WeakHashMap<String, ConstantState> weakHashMap2 = outsideDrawablesCache;
        super(context2, searchView2.getSuggestionRowLayout(), (Cursor) null, true);
        this.mSearchView = searchView2;
        this.mSearchable = searchable;
        this.mCommitIconResId = searchView2.getSuggestionCommitIconResId();
        this.mProviderContext = context2;
        this.mOutsideDrawablesCache = outsideDrawablesCache;
    }

    public void setQueryRefinement(int i) {
        int refineWhat = i;
        int i2 = refineWhat;
        this.mQueryRefinement = refineWhat;
    }

    public int getQueryRefinement() {
        return this.mQueryRefinement;
    }

    public boolean hasStableIds() {
        return false;
    }

    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        CharSequence constraint = charSequence;
        CharSequence charSequence2 = constraint;
        String query = constraint != null ? constraint.toString() : "";
        if (this.mSearchView.getVisibility() != 0 || this.mSearchView.getWindowVisibility() != 0) {
            return null;
        }
        try {
            Cursor searchManagerSuggestions = getSearchManagerSuggestions(this.mSearchable, query, 50);
            Cursor cursor = searchManagerSuggestions;
            if (searchManagerSuggestions != null) {
                int count = cursor.getCount();
                return cursor;
            }
        } catch (RuntimeException e) {
            RuntimeException runtimeException = e;
            int w = Log.w(LOG_TAG, "Search suggestions query threw an exception.", e);
        }
        return null;
    }

    public void close() {
        changeCursor(null);
        this.mClosed = true;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        updateSpinnerState(getCursor());
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        updateSpinnerState(getCursor());
    }

    private void updateSpinnerState(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        Bundle extras = cursor2 == null ? null : cursor2.getExtras();
        if (extras == null || extras.getBoolean("in_progress")) {
        }
    }

    public void changeCursor(Cursor cursor) {
        Cursor c = cursor;
        Cursor cursor2 = c;
        if (!this.mClosed) {
            try {
                super.changeCursor(c);
                if (c != null) {
                    this.mText1Col = c.getColumnIndex("suggest_text_1");
                    this.mText2Col = c.getColumnIndex("suggest_text_2");
                    this.mText2UrlCol = c.getColumnIndex("suggest_text_2_url");
                    this.mIconName1Col = c.getColumnIndex("suggest_icon_1");
                    this.mIconName2Col = c.getColumnIndex("suggest_icon_2");
                    this.mFlagsCol = c.getColumnIndex("suggest_flags");
                }
            } catch (Exception e) {
                Exception exc = e;
                int e2 = Log.e(LOG_TAG, "error changing cursor and caching columns", e);
            }
            return;
        }
        int w = Log.w(LOG_TAG, "Tried to change cursor after adapter was closed.");
        if (c != null) {
            c.close();
        }
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Context context2 = context;
        Cursor cursor2 = cursor;
        ViewGroup parent = viewGroup;
        Context context3 = context2;
        Cursor cursor3 = cursor2;
        ViewGroup viewGroup2 = parent;
        View newView = super.newView(context2, cursor2, parent);
        View v = newView;
        newView.setTag(new ChildViewCache(v));
        ImageView imageView = (ImageView) v.findViewById(C0268R.C0270id.edit_query);
        ImageView imageView2 = imageView;
        imageView.setImageResource(this.mCommitIconResId);
        return v;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        CharSequence text2;
        View view2 = view;
        Cursor cursor2 = cursor;
        View view3 = view2;
        Context context2 = context;
        Cursor cursor3 = cursor2;
        ChildViewCache views = (ChildViewCache) view2.getTag();
        int flags = 0;
        if (this.mFlagsCol != -1) {
            flags = cursor2.getInt(this.mFlagsCol);
        }
        if (views.mText1 != null) {
            setViewText(views.mText1, getStringOrNull(cursor2, this.mText1Col));
        }
        if (views.mText2 != null) {
            String stringOrNull = getStringOrNull(cursor2, this.mText2UrlCol);
            String str = stringOrNull;
            if (stringOrNull == null) {
                text2 = getStringOrNull(cursor2, this.mText2Col);
            } else {
                text2 = formatUrl(str);
            }
            if (!TextUtils.isEmpty(text2)) {
                if (views.mText1 != null) {
                    views.mText1.setSingleLine(true);
                    views.mText1.setMaxLines(1);
                }
            } else if (views.mText1 != null) {
                views.mText1.setSingleLine(false);
                views.mText1.setMaxLines(2);
            }
            setViewText(views.mText2, text2);
        }
        if (views.mIcon1 != null) {
            setViewDrawable(views.mIcon1, getIcon1(cursor2), 4);
        }
        if (views.mIcon2 != null) {
            setViewDrawable(views.mIcon2, getIcon2(cursor2), 8);
        }
        if (this.mQueryRefinement != 2 && (this.mQueryRefinement != 1 || (flags & 1) == 0)) {
            views.mIconRefine.setVisibility(8);
            return;
        }
        views.mIconRefine.setVisibility(0);
        views.mIconRefine.setTag(views.mText1.getText());
        views.mIconRefine.setOnClickListener(this);
    }

    public void onClick(View view) {
        View v = view;
        View view2 = v;
        Object tag = v.getTag();
        Object tag2 = tag;
        if (tag instanceof CharSequence) {
            this.mSearchView.onQueryRefine((CharSequence) tag2);
        }
    }

    private CharSequence formatUrl(CharSequence charSequence) {
        CharSequence url = charSequence;
        CharSequence charSequence2 = url;
        if (this.mUrlColor == null) {
            TypedValue colorValue = new TypedValue();
            boolean resolveAttribute = this.mContext.getTheme().resolveAttribute(C0268R.attr.textColorSearchUrl, colorValue, true);
            this.mUrlColor = this.mContext.getResources().getColorStateList(colorValue.resourceId);
        }
        SpannableString spannableString = new SpannableString(url);
        SpannableString text = spannableString;
        SpannableString spannableString2 = spannableString;
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null);
        spannableString2.setSpan(textAppearanceSpan, 0, url.length(), 33);
        return text;
    }

    private void setViewText(TextView textView, CharSequence charSequence) {
        TextView v = textView;
        CharSequence text = charSequence;
        TextView textView2 = v;
        CharSequence charSequence2 = text;
        v.setText(text);
        if (!TextUtils.isEmpty(text)) {
            v.setVisibility(0);
        } else {
            v.setVisibility(8);
        }
    }

    private Drawable getIcon1(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        if (this.mIconName1Col == -1) {
            return null;
        }
        Drawable drawableFromResourceValue = getDrawableFromResourceValue(cursor2.getString(this.mIconName1Col));
        Drawable drawable = drawableFromResourceValue;
        if (drawableFromResourceValue == null) {
            return getDefaultIcon1(cursor2);
        }
        return drawable;
    }

    private Drawable getIcon2(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        if (this.mIconName2Col != -1) {
            return getDrawableFromResourceValue(cursor2.getString(this.mIconName2Col));
        }
        return null;
    }

    private void setViewDrawable(ImageView imageView, Drawable drawable, int i) {
        ImageView v = imageView;
        Drawable drawable2 = drawable;
        int nullVisibility = i;
        ImageView imageView2 = v;
        Drawable drawable3 = drawable2;
        int i2 = nullVisibility;
        v.setImageDrawable(drawable2);
        if (drawable2 != null) {
            v.setVisibility(0);
            boolean visible = drawable2.setVisible(false, false);
            boolean visible2 = drawable2.setVisible(true, false);
            return;
        }
        v.setVisibility(nullVisibility);
    }

    public CharSequence convertToString(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        if (cursor2 == null) {
            return null;
        }
        String columnString = getColumnString(cursor2, "suggest_intent_query");
        String query = columnString;
        if (columnString != null) {
            return query;
        }
        if (this.mSearchable.shouldRewriteQueryFromData()) {
            String columnString2 = getColumnString(cursor2, "suggest_intent_data");
            String data = columnString2;
            if (columnString2 != null) {
                return data;
            }
        }
        if (this.mSearchable.shouldRewriteQueryFromText()) {
            String columnString3 = getColumnString(cursor2, "suggest_text_1");
            String text1 = columnString3;
            if (columnString3 != null) {
                return text1;
            }
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        int position = i;
        View convertView = view;
        ViewGroup parent = viewGroup;
        int i2 = position;
        View view2 = convertView;
        ViewGroup viewGroup2 = parent;
        try {
            return super.getView(position, convertView, parent);
        } catch (RuntimeException e) {
            RuntimeException runtimeException = e;
            int w = Log.w(LOG_TAG, "Search suggestions cursor threw exception.", e);
            View newView = newView(this.mContext, this.mCursor, parent);
            View v = newView;
            if (newView != null) {
                ChildViewCache childViewCache = (ChildViewCache) v.getTag();
                ChildViewCache childViewCache2 = childViewCache;
                TextView textView = childViewCache.mText1;
                TextView textView2 = textView;
                textView.setText(e.toString());
            }
            return v;
        }
    }

    private Drawable getDrawableFromResourceValue(String str) {
        String drawableId = str;
        String str2 = drawableId;
        if (drawableId == null || drawableId.length() == 0 || "0".equals(drawableId)) {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(drawableId);
            int i = parseInt;
            String drawableUri = "android.resource://" + this.mProviderContext.getPackageName() + "/" + parseInt;
            Drawable checkIconCache = checkIconCache(drawableUri);
            Drawable drawable = checkIconCache;
            if (checkIconCache != null) {
                return drawable;
            }
            Drawable drawable2 = ContextCompat.getDrawable(this.mProviderContext, parseInt);
            storeInIconCache(drawableUri, drawable2);
            return drawable2;
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = e;
            Drawable checkIconCache2 = checkIconCache(drawableId);
            Drawable drawable3 = checkIconCache2;
            if (checkIconCache2 != null) {
                return drawable3;
            }
            Drawable drawable4 = getDrawable(Uri.parse(drawableId));
            storeInIconCache(drawableId, drawable4);
            return drawable4;
        } catch (NotFoundException e2) {
            NotFoundException notFoundException = e2;
            int w = Log.w(LOG_TAG, "Icon resource not found: " + drawableId);
            return null;
        }
    }

    private Drawable getDrawable(Uri uri) {
        InputStream stream;
        Uri uri2 = uri;
        Uri uri3 = uri2;
        try {
            if ("android.resource".equals(uri2.getScheme())) {
                return getDrawableFromResourceUri(uri2);
            }
            InputStream openInputStream = this.mProviderContext.getContentResolver().openInputStream(uri2);
            stream = openInputStream;
            if (openInputStream != null) {
                Drawable createFromStream = Drawable.createFromStream(stream, null);
                try {
                    stream.close();
                } catch (IOException e) {
                    IOException iOException = e;
                    int e2 = Log.e(LOG_TAG, "Error closing icon stream for " + uri2, e);
                }
                return createFromStream;
            }
            throw new FileNotFoundException("Failed to open " + uri2);
        } catch (NotFoundException e3) {
            NotFoundException notFoundException = e3;
            throw new FileNotFoundException("Resource does not exist: " + uri2);
        } catch (FileNotFoundException e4) {
            FileNotFoundException fileNotFoundException = e4;
            int w = Log.w(LOG_TAG, "Icon not found: " + uri2 + ", " + e4.getMessage());
            return null;
        } catch (Throwable th) {
            try {
                stream.close();
            } catch (IOException e5) {
                IOException iOException2 = e5;
                int e6 = Log.e(LOG_TAG, "Error closing icon stream for " + uri2, e5);
            }
            throw th;
        }
    }

    private Drawable checkIconCache(String str) {
        String resourceUri = str;
        String str2 = resourceUri;
        ConstantState constantState = (ConstantState) this.mOutsideDrawablesCache.get(resourceUri);
        ConstantState cached = constantState;
        if (constantState != null) {
            return cached.newDrawable();
        }
        return null;
    }

    private void storeInIconCache(String str, Drawable drawable) {
        String resourceUri = str;
        Drawable drawable2 = drawable;
        String str2 = resourceUri;
        Drawable drawable3 = drawable2;
        if (drawable2 != null) {
            Object put = this.mOutsideDrawablesCache.put(resourceUri, drawable2.getConstantState());
        }
    }

    private Drawable getDefaultIcon1(Cursor cursor) {
        Cursor cursor2 = cursor;
        Drawable activityIconWithCache = getActivityIconWithCache(this.mSearchable.getSearchActivity());
        Drawable drawable = activityIconWithCache;
        if (activityIconWithCache == null) {
            return this.mContext.getPackageManager().getDefaultActivityIcon();
        }
        return drawable;
    }

    private Drawable getActivityIconWithCache(ComponentName componentName) {
        ComponentName component = componentName;
        ComponentName componentName2 = component;
        String componentIconKey = component.flattenToShortString();
        if (!this.mOutsideDrawablesCache.containsKey(componentIconKey)) {
            Drawable activityIcon = getActivityIcon(component);
            Drawable drawable = activityIcon;
            Object put = this.mOutsideDrawablesCache.put(componentIconKey, activityIcon != null ? drawable.getConstantState() : null);
            return drawable;
        }
        ConstantState constantState = (ConstantState) this.mOutsideDrawablesCache.get(componentIconKey);
        return constantState != null ? constantState.newDrawable(this.mProviderContext.getResources()) : null;
    }

    private Drawable getActivityIcon(ComponentName componentName) {
        ComponentName component = componentName;
        ComponentName componentName2 = component;
        PackageManager packageManager = this.mContext.getPackageManager();
        PackageManager pm = packageManager;
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(component, 128);
            ActivityInfo activityInfo2 = activityInfo;
            int iconResource = activityInfo.getIconResource();
            int iconId = iconResource;
            if (iconResource == 0) {
                return null;
            }
            Drawable drawable = pm.getDrawable(component.getPackageName(), iconId, activityInfo2.applicationInfo);
            Drawable drawable2 = drawable;
            if (drawable != null) {
                return drawable2;
            }
            int w = Log.w(LOG_TAG, "Invalid icon resource " + iconId + " for " + component.flattenToShortString());
            return null;
        } catch (NameNotFoundException e) {
            NameNotFoundException nameNotFoundException = e;
            int w2 = Log.w(LOG_TAG, e.toString());
            return null;
        }
    }

    public static String getColumnString(Cursor cursor, String str) {
        Cursor cursor2 = cursor;
        String columnName = str;
        Cursor cursor3 = cursor2;
        String str2 = columnName;
        int columnIndex = cursor2.getColumnIndex(columnName);
        int i = columnIndex;
        return getStringOrNull(cursor2, columnIndex);
    }

    private static String getStringOrNull(Cursor cursor, int i) {
        Cursor cursor2 = cursor;
        int col = i;
        Cursor cursor3 = cursor2;
        int i2 = col;
        if (col == -1) {
            return null;
        }
        try {
            return cursor2.getString(col);
        } catch (Exception e) {
            Exception exc = e;
            int e2 = Log.e(LOG_TAG, "unexpected error retrieving valid column from cursor, did the remote process die?", e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public Drawable getDrawableFromResourceUri(Uri uri) throws FileNotFoundException {
        int id;
        Uri uri2 = uri;
        Uri uri3 = uri2;
        String authority = uri2.getAuthority();
        String authority2 = authority;
        if (!TextUtils.isEmpty(authority)) {
            try {
                Resources r = this.mContext.getPackageManager().getResourcesForApplication(authority2);
                List pathSegments = uri2.getPathSegments();
                List list = pathSegments;
                if (pathSegments != null) {
                    int size = list.size();
                    int len = size;
                    if (size == 1) {
                        try {
                            id = Integer.parseInt((String) list.get(0));
                        } catch (NumberFormatException e) {
                            NumberFormatException numberFormatException = e;
                            throw new FileNotFoundException("Single path segment is not a resource ID: " + uri2);
                        }
                    } else if (len != 2) {
                        throw new FileNotFoundException("More than two path segments: " + uri2);
                    } else {
                        id = r.getIdentifier((String) list.get(1), (String) list.get(0), authority2);
                    }
                    if (id != 0) {
                        return r.getDrawable(id);
                    }
                    throw new FileNotFoundException("No resource found for: " + uri2);
                }
                throw new FileNotFoundException("No path: " + uri2);
            } catch (NameNotFoundException e2) {
                NameNotFoundException nameNotFoundException = e2;
                throw new FileNotFoundException("No package found for authority: " + uri2);
            }
        } else {
            throw new FileNotFoundException("No authority: " + uri2);
        }
    }

    /* access modifiers changed from: 0000 */
    public Cursor getSearchManagerSuggestions(SearchableInfo searchableInfo, String str, int i) {
        SearchableInfo searchable = searchableInfo;
        String query = str;
        int limit = i;
        SearchableInfo searchableInfo2 = searchable;
        String str2 = query;
        int i2 = limit;
        if (searchable == null) {
            return null;
        }
        String suggestAuthority = searchable.getSuggestAuthority();
        String authority = suggestAuthority;
        if (suggestAuthority == null) {
            return null;
        }
        Builder uriBuilder = new Builder().scheme("content").authority(authority).query("").fragment("");
        String suggestPath = searchable.getSuggestPath();
        String contentPath = suggestPath;
        if (suggestPath != null) {
            Builder appendEncodedPath = uriBuilder.appendEncodedPath(contentPath);
        }
        Builder appendPath = uriBuilder.appendPath("search_suggest_query");
        String selection = searchable.getSuggestSelection();
        String[] selArgs = null;
        if (selection == null) {
            Builder appendPath2 = uriBuilder.appendPath(query);
        } else {
            String[] strArr = new String[1];
            strArr[0] = query;
            selArgs = strArr;
        }
        if (limit > 0) {
            Builder appendQueryParameter = uriBuilder.appendQueryParameter("limit", String.valueOf(limit));
        }
        return this.mContext.getContentResolver().query(uriBuilder.build(), null, selection, selArgs, null);
    }
}
