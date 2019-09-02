package android.support.p000v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.widget.TextView;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.widget.TextViewCompatApi23 */
class TextViewCompatApi23 {
    TextViewCompatApi23() {
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
        TextView textView2 = textView;
        int resId = i;
        TextView textView3 = textView2;
        int i2 = resId;
        textView2.setTextAppearance(resId);
    }
}
