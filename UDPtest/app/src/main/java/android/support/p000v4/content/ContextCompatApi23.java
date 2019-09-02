package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.content.ContextCompatApi23 */
class ContextCompatApi23 {
    ContextCompatApi23() {
    }

    public static ColorStateList getColorStateList(Context context, int i) {
        Context context2 = context;
        int id = i;
        Context context3 = context2;
        int i2 = id;
        return context2.getColorStateList(id);
    }

    public static int getColor(Context context, int i) {
        Context context2 = context;
        int id = i;
        Context context3 = context2;
        int i2 = id;
        return context2.getColor(id);
    }
}
