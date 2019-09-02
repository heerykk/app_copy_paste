package android.support.p003v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.widget.RecyclerView.ItemDecoration;
import android.support.p003v7.widget.RecyclerView.State;
import android.view.View;

/* renamed from: android.support.v7.widget.DividerItemDecoration */
public class DividerItemDecoration extends ItemDecoration {
    private static final int[] ATTRS;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private final Rect mBounds = new Rect();
    private Drawable mDivider;
    private int mOrientation;

    static {
        int[] iArr = new int[1];
        iArr[0] = 16843284;
        ATTRS = iArr;
    }

    public DividerItemDecoration(Context context, int i) {
        Context context2 = context;
        int orientation = i;
        Context context3 = context2;
        int i2 = orientation;
        TypedArray a = context2.obtainStyledAttributes(ATTRS);
        this.mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int i) {
        int orientation = i;
        int i2 = orientation;
        if (orientation == 0 || orientation == 1) {
            this.mOrientation = orientation;
            return;
        }
        throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
    }

    public void setDrawable(@NonNull Drawable drawable) {
        Drawable drawable2 = drawable;
        Drawable drawable3 = drawable2;
        if (drawable2 != null) {
            this.mDivider = drawable2;
            return;
        }
        throw new IllegalArgumentException("Drawable cannot be null.");
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        Canvas c = canvas;
        RecyclerView parent = recyclerView;
        Canvas canvas2 = c;
        RecyclerView recyclerView2 = parent;
        State state2 = state;
        if (parent.getLayoutManager() != null) {
            if (this.mOrientation != 1) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int left;
        int right;
        Canvas canvas2 = canvas;
        RecyclerView parent = recyclerView;
        Canvas canvas3 = canvas2;
        RecyclerView recyclerView2 = parent;
        int save = canvas2.save();
        if (!parent.getClipToPadding()) {
            left = 0;
            right = parent.getWidth();
        } else {
            left = parent.getPaddingLeft();
            int width = parent.getWidth() - parent.getPaddingRight();
            right = width;
            boolean clipRect = canvas2.clipRect(left, parent.getPaddingTop(), width, parent.getHeight() - parent.getPaddingBottom());
        }
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, this.mBounds);
            int round = this.mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            int bottom = round;
            int intrinsicHeight = round - this.mDivider.getIntrinsicHeight();
            int i2 = intrinsicHeight;
            this.mDivider.setBounds(left, intrinsicHeight, right, bottom);
            this.mDivider.draw(canvas2);
        }
        canvas2.restore();
    }

    @SuppressLint({"NewApi"})
    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int top;
        int bottom;
        Canvas canvas2 = canvas;
        RecyclerView parent = recyclerView;
        Canvas canvas3 = canvas2;
        RecyclerView recyclerView2 = parent;
        int save = canvas2.save();
        if (!parent.getClipToPadding()) {
            top = 0;
            bottom = parent.getHeight();
        } else {
            top = parent.getPaddingTop();
            int height = parent.getHeight() - parent.getPaddingBottom();
            bottom = height;
            boolean clipRect = canvas2.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(), height);
        }
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, this.mBounds);
            int round = this.mBounds.right + Math.round(ViewCompat.getTranslationX(child));
            int right = round;
            int intrinsicWidth = round - this.mDivider.getIntrinsicWidth();
            int i2 = intrinsicWidth;
            this.mDivider.setBounds(intrinsicWidth, top, right, bottom);
            this.mDivider.draw(canvas2);
        }
        canvas2.restore();
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        Rect outRect = rect;
        Rect rect2 = outRect;
        View view2 = view;
        RecyclerView recyclerView2 = recyclerView;
        State state2 = state;
        if (this.mOrientation != 1) {
            outRect.set(0, 0, this.mDivider.getIntrinsicWidth(), 0);
        } else {
            outRect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
        }
    }
}
