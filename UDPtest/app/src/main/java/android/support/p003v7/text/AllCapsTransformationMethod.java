package android.support.p003v7.text;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.text.method.TransformationMethod;
import android.view.View;
import java.util.Locale;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.text.AllCapsTransformationMethod */
public class AllCapsTransformationMethod implements TransformationMethod {
    private Locale mLocale;

    public AllCapsTransformationMethod(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.mLocale = context2.getResources().getConfiguration().locale;
    }

    public CharSequence getTransformation(CharSequence charSequence, View view) {
        CharSequence source = charSequence;
        CharSequence charSequence2 = source;
        View view2 = view;
        return source == null ? null : source.toString().toUpperCase(this.mLocale);
    }

    public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
        View view2 = view;
        CharSequence charSequence2 = charSequence;
        boolean z2 = z;
        int i2 = i;
        Rect rect2 = rect;
    }
}
