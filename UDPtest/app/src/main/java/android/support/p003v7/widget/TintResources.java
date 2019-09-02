package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;

/* renamed from: android.support.v7.widget.TintResources */
class TintResources extends ResourcesWrapper {
    private final WeakReference<Context> mContextRef;

    public TintResources(@NonNull Context context, @NonNull Resources resources) {
        Context context2 = context;
        Resources res = resources;
        Context context3 = context2;
        Resources resources2 = res;
        super(res);
        this.mContextRef = new WeakReference<>(context2);
    }

    public Drawable getDrawable(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        Drawable d = super.getDrawable(id);
        Context context = (Context) this.mContextRef.get();
        if (!(d == null || context == null)) {
            AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
            boolean tintDrawableUsingColorFilter = AppCompatDrawableManager.tintDrawableUsingColorFilter(context, id, d);
        }
        return d;
    }
}
