package android.support.p000v4.text;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.text.ICUCompatIcs */
class ICUCompatIcs {
    private static final String TAG = "ICUCompatIcs";
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    ICUCompatIcs() {
    }

    static {
        try {
            Class cls = Class.forName("libcore.icu.ICU");
            Class cls2 = cls;
            if (cls != null) {
                Class[] clsArr = new Class[1];
                clsArr[0] = String.class;
                sGetScriptMethod = cls2.getMethod("getScript", clsArr);
                Class[] clsArr2 = new Class[1];
                clsArr2[0] = String.class;
                sAddLikelySubtagsMethod = cls2.getMethod("addLikelySubtags", clsArr2);
            }
        } catch (Exception e) {
            Exception exc = e;
            sGetScriptMethod = null;
            sAddLikelySubtagsMethod = null;
            int w = Log.w(TAG, e);
        }
    }

    public static String maximizeAndGetScript(Locale locale) {
        Locale locale2 = locale;
        Locale locale3 = locale2;
        String addLikelySubtags = addLikelySubtags(locale2);
        String localeWithSubtags = addLikelySubtags;
        if (addLikelySubtags == null) {
            return null;
        }
        return getScript(localeWithSubtags);
    }

    private static String getScript(String str) {
        String localeStr = str;
        String str2 = localeStr;
        try {
            if (sGetScriptMethod != null) {
                Object[] objArr = new Object[1];
                objArr[0] = localeStr;
                return (String) sGetScriptMethod.invoke(null, objArr);
            }
        } catch (IllegalAccessException e) {
            IllegalAccessException illegalAccessException = e;
            int w = Log.w(TAG, e);
        } catch (InvocationTargetException e2) {
            InvocationTargetException invocationTargetException = e2;
            int w2 = Log.w(TAG, e2);
        }
        return null;
    }

    private static String addLikelySubtags(Locale locale) {
        Locale locale2 = locale;
        Locale locale3 = locale2;
        String localeStr = locale2.toString();
        try {
            if (sAddLikelySubtagsMethod != null) {
                Object[] objArr = new Object[1];
                objArr[0] = localeStr;
                return (String) sAddLikelySubtagsMethod.invoke(null, objArr);
            }
        } catch (IllegalAccessException e) {
            IllegalAccessException illegalAccessException = e;
            int w = Log.w(TAG, e);
        } catch (InvocationTargetException e2) {
            InvocationTargetException invocationTargetException = e2;
            int w2 = Log.w(TAG, e2);
        }
        return localeStr;
    }
}
