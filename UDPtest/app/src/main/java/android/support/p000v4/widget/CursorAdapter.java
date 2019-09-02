package android.support.p000v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

/* renamed from: android.support.v4.widget.CursorAdapter */
public abstract class CursorAdapter extends BaseAdapter implements Filterable, CursorFilterClient {
    @Deprecated
    public static final int FLAG_AUTO_REQUERY = 1;
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected boolean mAutoRequery;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected ChangeObserver mChangeObserver;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected Context mContext;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected Cursor mCursor;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected CursorFilter mCursorFilter;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected DataSetObserver mDataSetObserver;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected boolean mDataValid;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected FilterQueryProvider mFilterQueryProvider;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected int mRowIDColumn;

    /* renamed from: android.support.v4.widget.CursorAdapter$ChangeObserver */
    private class ChangeObserver extends ContentObserver {
        final /* synthetic */ CursorAdapter this$0;

        ChangeObserver(CursorAdapter cursorAdapter) {
            this.this$0 = cursorAdapter;
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean z) {
            boolean z2 = z;
            this.this$0.onContentChanged();
        }
    }

    /* renamed from: android.support.v4.widget.CursorAdapter$MyDataSetObserver */
    private class MyDataSetObserver extends DataSetObserver {
        final /* synthetic */ CursorAdapter this$0;

        MyDataSetObserver(CursorAdapter cursorAdapter) {
            this.this$0 = cursorAdapter;
        }

        public void onChanged() {
            this.this$0.mDataValid = true;
            this.this$0.notifyDataSetChanged();
        }

        public void onInvalidated() {
            this.this$0.mDataValid = false;
            this.this$0.notifyDataSetInvalidated();
        }
    }

    public abstract void bindView(View view, Context context, Cursor cursor);

    public abstract View newView(Context context, Cursor cursor, ViewGroup viewGroup);

    @Deprecated
    public CursorAdapter(Context context, Cursor cursor) {
        Context context2 = context;
        Cursor c = cursor;
        Context context3 = context2;
        Cursor cursor2 = c;
        init(context2, c, 1);
    }

    public CursorAdapter(Context context, Cursor cursor, boolean z) {
        Context context2 = context;
        Cursor c = cursor;
        Context context3 = context2;
        Cursor cursor2 = c;
        init(context2, c, !z ? 2 : 1);
    }

    public CursorAdapter(Context context, Cursor cursor, int i) {
        Context context2 = context;
        Cursor c = cursor;
        int flags = i;
        Context context3 = context2;
        Cursor cursor2 = c;
        int i2 = flags;
        init(context2, c, flags);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void init(Context context, Cursor cursor, boolean z) {
        Context context2 = context;
        Cursor c = cursor;
        Context context3 = context2;
        Cursor cursor2 = c;
        init(context2, c, !z ? 2 : 1);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context, Cursor cursor, int i) {
        Context context2 = context;
        Cursor c = cursor;
        int flags = i;
        Context context3 = context2;
        Cursor cursor2 = c;
        int flags2 = flags;
        if ((flags & 1) != 1) {
            this.mAutoRequery = false;
        } else {
            flags2 = flags | 2;
            this.mAutoRequery = true;
        }
        boolean cursorPresent = c != null;
        this.mCursor = c;
        this.mDataValid = cursorPresent;
        this.mContext = context2;
        this.mRowIDColumn = !cursorPresent ? -1 : c.getColumnIndexOrThrow("_id");
        if ((flags2 & 2) != 2) {
            this.mChangeObserver = null;
            this.mDataSetObserver = null;
        } else {
            this.mChangeObserver = new ChangeObserver(this);
            MyDataSetObserver myDataSetObserver = new MyDataSetObserver(this);
            this.mDataSetObserver = myDataSetObserver;
        }
        if (cursorPresent) {
            if (this.mChangeObserver != null) {
                c.registerContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                c.registerDataSetObserver(this.mDataSetObserver);
            }
        }
    }

    public Cursor getCursor() {
        return this.mCursor;
    }

    public int getCount() {
        if (this.mDataValid && this.mCursor != null) {
            return this.mCursor.getCount();
        }
        return 0;
    }

    public Object getItem(int i) {
        int position = i;
        int i2 = position;
        if (!this.mDataValid || this.mCursor == null) {
            return null;
        }
        boolean moveToPosition = this.mCursor.moveToPosition(position);
        return this.mCursor;
    }

    public long getItemId(int i) {
        int position = i;
        int i2 = position;
        if (!this.mDataValid || this.mCursor == null) {
            return 0;
        }
        if (!this.mCursor.moveToPosition(position)) {
            return 0;
        }
        return this.mCursor.getLong(this.mRowIDColumn);
    }

    public boolean hasStableIds() {
        return true;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        int position = i;
        View convertView = view;
        ViewGroup parent = viewGroup;
        int i2 = position;
        View view2 = convertView;
        ViewGroup viewGroup2 = parent;
        if (!this.mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        } else if (this.mCursor.moveToPosition(position)) {
            if (convertView != null) {
                v = convertView;
            } else {
                v = newView(this.mContext, this.mCursor, parent);
            }
            bindView(v, this.mContext, this.mCursor);
            return v;
        } else {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View v;
        int position = i;
        View convertView = view;
        ViewGroup parent = viewGroup;
        int i2 = position;
        View view2 = convertView;
        ViewGroup viewGroup2 = parent;
        if (!this.mDataValid) {
            return null;
        }
        boolean moveToPosition = this.mCursor.moveToPosition(position);
        if (convertView != null) {
            v = convertView;
        } else {
            v = newDropDownView(this.mContext, this.mCursor, parent);
        }
        bindView(v, this.mContext, this.mCursor);
        return v;
    }

    public View newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Context context2 = context;
        Cursor cursor2 = cursor;
        ViewGroup parent = viewGroup;
        Context context3 = context2;
        Cursor cursor3 = cursor2;
        ViewGroup viewGroup2 = parent;
        return newView(context2, cursor2, parent);
    }

    public void changeCursor(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        Cursor swapCursor = swapCursor(cursor2);
        Cursor old = swapCursor;
        if (swapCursor != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor cursor) {
        Cursor newCursor = cursor;
        Cursor cursor2 = newCursor;
        if (newCursor == this.mCursor) {
            return null;
        }
        Cursor cursor3 = this.mCursor;
        Cursor oldCursor = cursor3;
        if (cursor3 != null) {
            if (this.mChangeObserver != null) {
                oldCursor.unregisterContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                oldCursor.unregisterDataSetObserver(this.mDataSetObserver);
            }
        }
        this.mCursor = newCursor;
        if (newCursor == null) {
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            notifyDataSetInvalidated();
        } else {
            if (this.mChangeObserver != null) {
                newCursor.registerContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                newCursor.registerDataSetObserver(this.mDataSetObserver);
            }
            this.mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            this.mDataValid = true;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    public CharSequence convertToString(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        return cursor2 != null ? cursor2.toString() : "";
    }

    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        CharSequence constraint = charSequence;
        CharSequence charSequence2 = constraint;
        if (this.mFilterQueryProvider == null) {
            return this.mCursor;
        }
        return this.mFilterQueryProvider.runQuery(constraint);
    }

    public Filter getFilter() {
        if (this.mCursorFilter == null) {
            this.mCursorFilter = new CursorFilter(this);
        }
        return this.mCursorFilter;
    }

    public FilterQueryProvider getFilterQueryProvider() {
        return this.mFilterQueryProvider;
    }

    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        FilterQueryProvider filterQueryProvider2 = filterQueryProvider;
        FilterQueryProvider filterQueryProvider3 = filterQueryProvider2;
        this.mFilterQueryProvider = filterQueryProvider2;
    }

    /* access modifiers changed from: protected */
    public void onContentChanged() {
        if (this.mAutoRequery && this.mCursor != null && !this.mCursor.isClosed()) {
            this.mDataValid = this.mCursor.requery();
        }
    }
}
