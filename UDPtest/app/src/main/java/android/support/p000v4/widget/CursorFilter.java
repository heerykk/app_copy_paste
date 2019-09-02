package android.support.p000v4.widget;

import android.database.Cursor;
import android.widget.Filter;
import android.widget.Filter.FilterResults;

/* renamed from: android.support.v4.widget.CursorFilter */
class CursorFilter extends Filter {
    CursorFilterClient mClient;

    /* renamed from: android.support.v4.widget.CursorFilter$CursorFilterClient */
    interface CursorFilterClient {
        void changeCursor(Cursor cursor);

        CharSequence convertToString(Cursor cursor);

        Cursor getCursor();

        Cursor runQueryOnBackgroundThread(CharSequence charSequence);
    }

    CursorFilter(CursorFilterClient cursorFilterClient) {
        CursorFilterClient client = cursorFilterClient;
        CursorFilterClient cursorFilterClient2 = client;
        this.mClient = client;
    }

    public CharSequence convertResultToString(Object obj) {
        Object resultValue = obj;
        Object obj2 = resultValue;
        return this.mClient.convertToString((Cursor) resultValue);
    }

    /* access modifiers changed from: protected */
    public FilterResults performFiltering(CharSequence charSequence) {
        CharSequence constraint = charSequence;
        CharSequence charSequence2 = constraint;
        Cursor cursor = this.mClient.runQueryOnBackgroundThread(constraint);
        FilterResults results = new FilterResults();
        if (cursor == null) {
            results.count = 0;
            results.values = null;
        } else {
            results.count = cursor.getCount();
            results.values = cursor;
        }
        return results;
    }

    /* access modifiers changed from: protected */
    public void publishResults(CharSequence charSequence, FilterResults filterResults) {
        FilterResults results = filterResults;
        CharSequence charSequence2 = charSequence;
        FilterResults filterResults2 = results;
        Cursor oldCursor = this.mClient.getCursor();
        if (results.values != null && results.values != oldCursor) {
            this.mClient.changeCursor((Cursor) results.values);
        }
    }
}
