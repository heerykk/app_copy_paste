package android.support.p000v4.app;

import android.content.Context;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/* renamed from: android.support.v4.app.NoSaveStateFrameLayout */
class NoSaveStateFrameLayout extends FrameLayout {
    static ViewGroup wrap(View view) {
        View child = view;
        View view2 = child;
        NoSaveStateFrameLayout wrapper = new NoSaveStateFrameLayout(child.getContext());
        LayoutParams layoutParams = child.getLayoutParams();
        LayoutParams childParams = layoutParams;
        if (layoutParams != null) {
            wrapper.setLayoutParams(childParams);
        }
        child.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        wrapper.addView(child);
        return wrapper;
    }

    public NoSaveStateFrameLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> container = sparseArray;
        SparseArray<Parcelable> sparseArray2 = container;
        dispatchFreezeSelfOnly(container);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> container = sparseArray;
        SparseArray<Parcelable> sparseArray2 = container;
        dispatchThawSelfOnly(container);
    }
}
