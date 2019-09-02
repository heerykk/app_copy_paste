package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.view.PagerTitleStripIcs */
class PagerTitleStripIcs {

    /* renamed from: android.support.v4.view.PagerTitleStripIcs$SingleLineAllCapsTransform */
    private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private static final String TAG = "SingleLineAllCapsTransform";
        private Locale mLocale;

        public SingleLineAllCapsTransform(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.mLocale = context2.getResources().getConfiguration().locale;
        }

        public CharSequence getTransformation(CharSequence charSequence, View view) {
            CharSequence source = charSequence;
            View view2 = view;
            CharSequence charSequence2 = source;
            View view3 = view2;
            CharSequence transformation = super.getTransformation(source, view2);
            return transformation == null ? null : transformation.toString().toUpperCase(this.mLocale);
        }
    }

    PagerTitleStripIcs() {
    }

    public static void setSingleLineAllCaps(TextView textView) {
        TextView text = textView;
        TextView textView2 = text;
        text.setTransformationMethod(new SingleLineAllCapsTransform(text.getContext()));
    }
}
