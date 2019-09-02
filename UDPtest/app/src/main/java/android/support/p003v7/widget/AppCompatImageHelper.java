package android.support.p003v7.widget;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.ImageView;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.AppCompatImageHelper */
public class AppCompatImageHelper {
    private final ImageView mView;

    public AppCompatImageHelper(ImageView imageView) {
        ImageView view = imageView;
        ImageView imageView2 = view;
        this.mView = view;
    }

    /* JADX INFO: finally extract failed */
    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        TintTypedArray a = null;
        try {
            Drawable drawable = this.mView.getDrawable();
            Drawable drawable2 = drawable;
            if (drawable == null) {
                TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attrs, C0268R.styleable.AppCompatImageView, defStyleAttr, 0);
                a = obtainStyledAttributes;
                int resourceId = obtainStyledAttributes.getResourceId(C0268R.styleable.AppCompatImageView_srcCompat, -1);
                int id = resourceId;
                if (resourceId != -1) {
                    Drawable drawable3 = AppCompatResources.getDrawable(this.mView.getContext(), id);
                    drawable2 = drawable3;
                    if (drawable3 != null) {
                        this.mView.setImageDrawable(drawable2);
                    }
                }
            }
            if (drawable2 != null) {
                DrawableUtils.fixDrawable(drawable2);
            }
            if (a != null) {
                a.recycle();
            }
        } catch (Throwable th) {
            if (a != null) {
                a.recycle();
            }
            throw th;
        }
    }

    public void setImageResource(int i) {
        int resId = i;
        int i2 = resId;
        if (resId == 0) {
            this.mView.setImageDrawable(null);
            return;
        }
        Drawable drawable = AppCompatResources.getDrawable(this.mView.getContext(), resId);
        Drawable d = drawable;
        if (drawable != null) {
            DrawableUtils.fixDrawable(d);
        }
        this.mView.setImageDrawable(d);
    }

    /* access modifiers changed from: 0000 */
    public boolean hasOverlappingRendering() {
        Drawable background = this.mView.getBackground();
        Drawable drawable = background;
        if (VERSION.SDK_INT >= 21 && (background instanceof RippleDrawable)) {
            return false;
        }
        return true;
    }
}
