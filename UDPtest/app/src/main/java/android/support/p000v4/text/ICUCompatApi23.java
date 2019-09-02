package android.support.p000v4.text;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.text.ICUCompatApi23 */
class ICUCompatApi23 {
    private static final String TAG = "ICUCompatIcs";
    private static Method sAddLikelySubtagsMethod;

    ICUCompatApi23() {
    }

    static {
        try {
            Class cls = Class.forName("libcore.icu.ICU");
            Class cls2 = cls;
            Class cls3 = cls;
            Class[] clsArr = new Class[1];
            clsArr[0] = Locale.class;
            sAddLikelySubtagsMethod = cls3.getMethod("addLikelySubtags", clsArr);
        } catch (Exception e) {
            Exception exc = e;
            throw new IllegalStateException(e);
        }
    }

    public static String maximizeAndGetScript(Locale locale) {
        Locale locale2 = locale;
        Locale locale3 = locale2;
        try {
            Object[] objArr = new Object[1];
            objArr[0] = locale2;
            return ((Locale) sAddLikelySubtagsMethod.invoke(null, objArr)).getScript();
        } catch (InvocationTargetException e) {
            InvocationTargetException invocationTargetException = e;
            int w = Log.w(TAG, e);
            return locale2.getScript();
        } catch (IllegalAccessException e2) {
            IllegalAccessException illegalAccessException = e2;
            int w2 = Log.w(TAG, e2);
            return locale2.getScript();
        }
    }
}
