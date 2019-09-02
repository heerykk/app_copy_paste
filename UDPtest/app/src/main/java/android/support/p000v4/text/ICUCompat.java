package android.support.p000v4.text;

import android.os.Build.VERSION;
import java.util.Locale;

/* renamed from: android.support.v4.text.ICUCompat */
public final class ICUCompat {
    private static final ICUCompatImpl IMPL;

    /* renamed from: android.support.v4.text.ICUCompat$ICUCompatImpl */
    interface ICUCompatImpl {
        String maximizeAndGetScript(Locale locale);
    }

    /* renamed from: android.support.v4.text.ICUCompat$ICUCompatImplBase */
    static class ICUCompatImplBase implements ICUCompatImpl {
        ICUCompatImplBase() {
        }

        public String maximizeAndGetScript(Locale locale) {
            Locale locale2 = locale;
            return null;
        }
    }

    /* renamed from: android.support.v4.text.ICUCompat$ICUCompatImplIcs */
    static class ICUCompatImplIcs implements ICUCompatImpl {
        ICUCompatImplIcs() {
        }

        public String maximizeAndGetScript(Locale locale) {
            Locale locale2 = locale;
            Locale locale3 = locale2;
            return ICUCompatIcs.maximizeAndGetScript(locale2);
        }
    }

    /* renamed from: android.support.v4.text.ICUCompat$ICUCompatImplLollipop */
    static class ICUCompatImplLollipop implements ICUCompatImpl {
        ICUCompatImplLollipop() {
        }

        public String maximizeAndGetScript(Locale locale) {
            Locale locale2 = locale;
            Locale locale3 = locale2;
            return ICUCompatApi23.maximizeAndGetScript(locale2);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 21) {
            IMPL = new ICUCompatImplLollipop();
        } else if (version < 14) {
            IMPL = new ICUCompatImplBase();
        } else {
            IMPL = new ICUCompatImplIcs();
        }
    }

    public static String maximizeAndGetScript(Locale locale) {
        Locale locale2 = locale;
        Locale locale3 = locale2;
        return IMPL.maximizeAndGetScript(locale2);
    }

    private ICUCompat() {
    }
}
