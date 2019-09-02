package android.support.p003v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.p000v4.widget.PopupWindowCompat;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/* renamed from: android.support.v7.widget.AppCompatPopupWindow */
class AppCompatPopupWindow extends PopupWindow {
    private static final boolean COMPAT_OVERLAP_ANCHOR = (VERSION.SDK_INT < 21);
    private static final String TAG = "AppCompatPopupWindow";
    private boolean mOverlapAnchor;

    public AppCompatPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        init(context2, attrs, defStyleAttr, 0);
    }

    @TargetApi(11)
    public AppCompatPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        super(context2, attrs, defStyleAttr, defStyleRes);
        init(context2, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, attrs, C0268R.styleable.PopupWindow, defStyleAttr, defStyleRes);
        TintTypedArray a = obtainStyledAttributes;
        if (obtainStyledAttributes.hasValue(C0268R.styleable.PopupWindow_overlapAnchor)) {
            setSupportOverlapAnchor(a.getBoolean(C0268R.styleable.PopupWindow_overlapAnchor, false));
        }
        setBackgroundDrawable(a.getDrawable(C0268R.styleable.PopupWindow_android_popupBackground));
        int sdk = VERSION.SDK_INT;
        if (defStyleRes != 0 && sdk < 11 && a.hasValue(C0268R.styleable.PopupWindow_android_popupAnimationStyle)) {
            setAnimationStyle(a.getResourceId(C0268R.styleable.PopupWindow_android_popupAnimationStyle, -1));
        }
        a.recycle();
        if (VERSION.SDK_INT < 14) {
            wrapOnScrollChangedListener(this);
        }
    }

    public void showAsDropDown(View view, int i, int i2) {
        View anchor = view;
        int xoff = i;
        int yoff = i2;
        View view2 = anchor;
        int i3 = xoff;
        int yoff2 = yoff;
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            yoff2 = yoff - anchor.getHeight();
        }
        super.showAsDropDown(anchor, xoff, yoff2);
    }

    @TargetApi(19)
    public void showAsDropDown(View view, int i, int i2, int i3) {
        View anchor = view;
        int xoff = i;
        int yoff = i2;
        int gravity = i3;
        View view2 = anchor;
        int i4 = xoff;
        int yoff2 = yoff;
        int i5 = gravity;
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            yoff2 = yoff - anchor.getHeight();
        }
        super.showAsDropDown(anchor, xoff, yoff2, gravity);
    }

    public void update(View view, int i, int i2, int i3, int i4) {
        View anchor = view;
        int xoff = i;
        int yoff = i2;
        int width = i3;
        int height = i4;
        View view2 = anchor;
        int i5 = xoff;
        int yoff2 = yoff;
        int i6 = width;
        int i7 = height;
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            yoff2 = yoff - anchor.getHeight();
        }
        super.update(anchor, xoff, yoff2, width, height);
    }

    private static void wrapOnScrollChangedListener(PopupWindow popupWindow) {
        PopupWindow popup = popupWindow;
        PopupWindow popupWindow2 = popup;
        try {
            Field declaredField = PopupWindow.class.getDeclaredField("mAnchor");
            Field fieldAnchor = declaredField;
            declaredField.setAccessible(true);
            Field declaredField2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            Field fieldListener = declaredField2;
            declaredField2.setAccessible(true);
            final Field field = fieldAnchor;
            final PopupWindow popupWindow3 = popup;
            final OnScrollChangedListener onScrollChangedListener = (OnScrollChangedListener) fieldListener.get(popup);
            fieldListener.set(popup, new OnScrollChangedListener() {
                {
                    PopupWindow popupWindow = r7;
                    OnScrollChangedListener onScrollChangedListener = r8;
                }

                public void onScrollChanged() {
                    try {
                        WeakReference weakReference = (WeakReference) field.get(popupWindow3);
                        WeakReference weakReference2 = weakReference;
                        if (weakReference != null && weakReference2.get() != null) {
                            onScrollChangedListener.onScrollChanged();
                        }
                    } catch (IllegalAccessException e) {
                        IllegalAccessException illegalAccessException = e;
                    }
                }
            });
        } catch (Exception e) {
            Exception exc = e;
            int d = Log.d(TAG, "Exception while installing workaround OnScrollChangedListener", e);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setSupportOverlapAnchor(boolean z) {
        boolean overlapAnchor = z;
        if (!COMPAT_OVERLAP_ANCHOR) {
            PopupWindowCompat.setOverlapAnchor(this, overlapAnchor);
        } else {
            this.mOverlapAnchor = overlapAnchor;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean getSupportOverlapAnchor() {
        if (!COMPAT_OVERLAP_ANCHOR) {
            return PopupWindowCompat.getOverlapAnchor(this);
        }
        return this.mOverlapAnchor;
    }
}
