package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import java.lang.ref.WeakReference;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ViewStubCompat */
public final class ViewStubCompat extends View {
    private OnInflateListener mInflateListener;
    private int mInflatedId;
    private WeakReference<View> mInflatedViewRef;
    private LayoutInflater mInflater;
    private int mLayoutResource;

    /* renamed from: android.support.v7.widget.ViewStubCompat$OnInflateListener */
    public interface OnInflateListener {
        void onInflate(ViewStubCompat viewStubCompat, View view);
    }

    public ViewStubCompat(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyle = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyle;
        super(context2, attrs, defStyle);
        this.mLayoutResource = 0;
        TypedArray a = context2.obtainStyledAttributes(attrs, C0268R.styleable.ViewStubCompat, defStyle, 0);
        this.mInflatedId = a.getResourceId(C0268R.styleable.ViewStubCompat_android_inflatedId, -1);
        this.mLayoutResource = a.getResourceId(C0268R.styleable.ViewStubCompat_android_layout, 0);
        setId(a.getResourceId(C0268R.styleable.ViewStubCompat_android_id, -1));
        a.recycle();
        setVisibility(8);
        setWillNotDraw(true);
    }

    public ViewStubCompat(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public int getInflatedId() {
        return this.mInflatedId;
    }

    public void setInflatedId(int i) {
        int inflatedId = i;
        int i2 = inflatedId;
        this.mInflatedId = inflatedId;
    }

    public int getLayoutResource() {
        return this.mLayoutResource;
    }

    public void setLayoutResource(int i) {
        int layoutResource = i;
        int i2 = layoutResource;
        this.mLayoutResource = layoutResource;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        LayoutInflater inflater = layoutInflater;
        LayoutInflater layoutInflater2 = inflater;
        this.mInflater = inflater;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3 = i;
        int i4 = i2;
        setMeasuredDimension(0, 0);
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
    }

    public void setVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        if (this.mInflatedViewRef == null) {
            super.setVisibility(visibility);
            if (visibility == 0 || visibility == 4) {
                View inflate = inflate();
                return;
            }
            return;
        }
        View view = (View) this.mInflatedViewRef.get();
        View view2 = view;
        if (view == null) {
            throw new IllegalStateException("setVisibility called on un-referenced view");
        }
        view2.setVisibility(visibility);
    }

    public View inflate() {
        LayoutInflater factory;
        ViewParent parent = getParent();
        ViewParent viewParent = parent;
        if (parent == null || !(viewParent instanceof ViewGroup)) {
            throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
        } else if (this.mLayoutResource == 0) {
            throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        } else {
            ViewGroup parent2 = (ViewGroup) viewParent;
            if (this.mInflater == null) {
                factory = LayoutInflater.from(getContext());
            } else {
                factory = this.mInflater;
            }
            View view = factory.inflate(this.mLayoutResource, parent2, false);
            if (this.mInflatedId != -1) {
                view.setId(this.mInflatedId);
            }
            int indexOfChild = parent2.indexOfChild(this);
            int i = indexOfChild;
            parent2.removeViewInLayout(this);
            LayoutParams layoutParams = getLayoutParams();
            LayoutParams layoutParams2 = layoutParams;
            if (layoutParams == null) {
                parent2.addView(view, indexOfChild);
            } else {
                parent2.addView(view, indexOfChild, layoutParams2);
            }
            WeakReference weakReference = new WeakReference(view);
            this.mInflatedViewRef = weakReference;
            if (this.mInflateListener != null) {
                this.mInflateListener.onInflate(this, view);
            }
            return view;
        }
    }

    public void setOnInflateListener(OnInflateListener onInflateListener) {
        OnInflateListener inflateListener = onInflateListener;
        OnInflateListener onInflateListener2 = inflateListener;
        this.mInflateListener = inflateListener;
    }
}
