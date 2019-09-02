package android.support.p000v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: android.support.v4.widget.ResourceCursorAdapter */
public abstract class ResourceCursorAdapter extends CursorAdapter {
    private int mDropDownLayout;
    private LayoutInflater mInflater;
    private int mLayout;

    @Deprecated
    public ResourceCursorAdapter(Context context, int i, Cursor cursor) {
        Context context2 = context;
        int layout = i;
        Cursor c = cursor;
        Context context3 = context2;
        int i2 = layout;
        Cursor cursor2 = c;
        super(context2, c);
        this.mDropDownLayout = layout;
        this.mLayout = layout;
        this.mInflater = (LayoutInflater) context2.getSystemService("layout_inflater");
    }

    @Deprecated
    public ResourceCursorAdapter(Context context, int i, Cursor cursor, boolean z) {
        Context context2 = context;
        int layout = i;
        Cursor c = cursor;
        Context context3 = context2;
        int i2 = layout;
        Cursor cursor2 = c;
        super(context2, c, z);
        this.mDropDownLayout = layout;
        this.mLayout = layout;
        this.mInflater = (LayoutInflater) context2.getSystemService("layout_inflater");
    }

    public ResourceCursorAdapter(Context context, int i, Cursor cursor, int i2) {
        Context context2 = context;
        int layout = i;
        Cursor c = cursor;
        int flags = i2;
        Context context3 = context2;
        int i3 = layout;
        Cursor cursor2 = c;
        int i4 = flags;
        super(context2, c, flags);
        this.mDropDownLayout = layout;
        this.mLayout = layout;
        this.mInflater = (LayoutInflater) context2.getSystemService("layout_inflater");
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        ViewGroup parent = viewGroup;
        Context context2 = context;
        Cursor cursor2 = cursor;
        ViewGroup viewGroup2 = parent;
        return this.mInflater.inflate(this.mLayout, parent, false);
    }

    public View newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        ViewGroup parent = viewGroup;
        Context context2 = context;
        Cursor cursor2 = cursor;
        ViewGroup viewGroup2 = parent;
        return this.mInflater.inflate(this.mDropDownLayout, parent, false);
    }

    public void setViewResource(int i) {
        int layout = i;
        int i2 = layout;
        this.mLayout = layout;
    }

    public void setDropDownViewResource(int i) {
        int dropDownLayout = i;
        int i2 = dropDownLayout;
        this.mDropDownLayout = dropDownLayout;
    }
}
