package android.support.p000v4.text;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import java.util.Locale;

@TargetApi(17)
@RequiresApi(17)
/* renamed from: android.support.v4.text.TextUtilsCompatJellybeanMr1 */
class TextUtilsCompatJellybeanMr1 {
    TextUtilsCompatJellybeanMr1() {
    }

    @NonNull
    public static String htmlEncode(@NonNull String str) {
        String s = str;
        String str2 = s;
        return TextUtils.htmlEncode(s);
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        Locale locale2 = locale;
        Locale locale3 = locale2;
        return TextUtils.getLayoutDirectionFromLocale(locale2);
    }
}
