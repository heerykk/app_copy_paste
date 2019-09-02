package android.support.p000v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/* renamed from: android.support.v4.widget.SimpleCursorAdapter */
public class SimpleCursorAdapter extends ResourceCursorAdapter {
    private CursorToStringConverter mCursorToStringConverter;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected int[] mFrom;
    String[] mOriginalFrom;
    private int mStringConversionColumn = -1;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected int[] mTo;
    private ViewBinder mViewBinder;

    /* renamed from: android.support.v4.widget.SimpleCursorAdapter$CursorToStringConverter */
    public interface CursorToStringConverter {
        CharSequence convertToString(Cursor cursor);
    }

    /* renamed from: android.support.v4.widget.SimpleCursorAdapter$ViewBinder */
    public interface ViewBinder {
        boolean setViewValue(View view, Cursor cursor, int i);
    }

    @Deprecated
    public SimpleCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr) {
        Context context2 = context;
        int layout = i;
        Cursor c = cursor;
        String[] from = strArr;
        int[] to = iArr;
        Context context3 = context2;
        int i2 = layout;
        Cursor cursor2 = c;
        String[] strArr2 = from;
        int[] iArr2 = to;
        super(context2, layout, c);
        this.mTo = to;
        this.mOriginalFrom = from;
        findColumns(c, from);
    }

    public SimpleCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr, int i2) {
        Context context2 = context;
        int layout = i;
        Cursor c = cursor;
        String[] from = strArr;
        int[] to = iArr;
        int flags = i2;
        Context context3 = context2;
        int i3 = layout;
        Cursor cursor2 = c;
        String[] strArr2 = from;
        int[] iArr2 = to;
        int i4 = flags;
        super(context2, layout, c, flags);
        this.mTo = to;
        this.mOriginalFrom = from;
        findColumns(c, from);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        View view2 = view;
        Cursor cursor2 = cursor;
        View view3 = view2;
        Context context2 = context;
        Cursor cursor3 = cursor2;
        ViewBinder binder = this.mViewBinder;
        int count = this.mTo.length;
        int[] from = this.mFrom;
        int[] to = this.mTo;
        for (int i = 0; i < count; i++) {
            View findViewById = view2.findViewById(to[i]);
            View v = findViewById;
            if (findViewById != null) {
                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, cursor2, from[i]);
                }
                if (bound) {
                    continue;
                } else {
                    String string = cursor2.getString(from[i]);
                    String text = string;
                    if (string == null) {
                        text = "";
                    }
                    if (v instanceof TextView) {
                        setViewText((TextView) v, text);
                    } else if (!(v instanceof ImageView)) {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " + " view that can be bounds by this SimpleCursorAdapter");
                    } else {
                        setViewImage((ImageView) v, text);
                    }
                }
            }
        }
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        ViewBinder viewBinder2 = viewBinder;
        ViewBinder viewBinder3 = viewBinder2;
        this.mViewBinder = viewBinder2;
    }

    public void setViewImage(ImageView imageView, String str) {
        ImageView v = imageView;
        String value = str;
        ImageView imageView2 = v;
        String str2 = value;
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = e;
            v.setImageURI(Uri.parse(value));
        }
    }

    public void setViewText(TextView textView, String str) {
        TextView v = textView;
        String text = str;
        TextView textView2 = v;
        String str2 = text;
        v.setText(text);
    }

    public int getStringConversionColumn() {
        return this.mStringConversionColumn;
    }

    public void setStringConversionColumn(int i) {
        int stringConversionColumn = i;
        int i2 = stringConversionColumn;
        this.mStringConversionColumn = stringConversionColumn;
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return this.mCursorToStringConverter;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        CursorToStringConverter cursorToStringConverter2 = cursorToStringConverter;
        CursorToStringConverter cursorToStringConverter3 = cursorToStringConverter2;
        this.mCursorToStringConverter = cursorToStringConverter2;
    }

    public CharSequence convertToString(Cursor cursor) {
        Cursor cursor2 = cursor;
        Cursor cursor3 = cursor2;
        if (this.mCursorToStringConverter != null) {
            return this.mCursorToStringConverter.convertToString(cursor2);
        }
        if (this.mStringConversionColumn <= -1) {
            return super.convertToString(cursor2);
        }
        return cursor2.getString(this.mStringConversionColumn);
    }

    private void findColumns(Cursor cursor, String[] strArr) {
        Cursor c = cursor;
        String[] from = strArr;
        Cursor cursor2 = c;
        String[] strArr2 = from;
        if (c == null) {
            this.mFrom = null;
            return;
        }
        int count = from.length;
        if (this.mFrom == null || this.mFrom.length != count) {
            this.mFrom = new int[count];
        }
        for (int i = 0; i < count; i++) {
            this.mFrom[i] = c.getColumnIndexOrThrow(from[i]);
        }
    }

    public Cursor swapCursor(Cursor cursor) {
        Cursor c = cursor;
        Cursor cursor2 = c;
        findColumns(c, this.mOriginalFrom);
        return super.swapCursor(c);
    }

    public void changeCursorAndColumns(Cursor cursor, String[] strArr, int[] iArr) {
        Cursor c = cursor;
        String[] from = strArr;
        int[] to = iArr;
        Cursor cursor2 = c;
        String[] strArr2 = from;
        int[] iArr2 = to;
        this.mOriginalFrom = from;
        this.mTo = to;
        findColumns(c, this.mOriginalFrom);
        super.changeCursor(c);
    }
}
