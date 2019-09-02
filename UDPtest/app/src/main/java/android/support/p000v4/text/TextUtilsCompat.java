package android.support.p000v4.text;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Locale;

/* renamed from: android.support.v4.text.TextUtilsCompat */
public final class TextUtilsCompat {
    static String ARAB_SCRIPT_SUBTAG = "Arab";
    static String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final TextUtilsCompatImpl IMPL;
    public static final Locale ROOT = new Locale("", "");

    /* renamed from: android.support.v4.text.TextUtilsCompat$TextUtilsCompatImpl */
    private static class TextUtilsCompatImpl {
        TextUtilsCompatImpl() {
        }

        @NonNull
        public String htmlEncode(@NonNull String str) {
            String s = str;
            String str2 = s;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char charAt = s.charAt(i);
                char c = charAt;
                switch (charAt) {
                    case '\"':
                        StringBuilder append = sb.append("&quot;");
                        break;
                    case '&':
                        StringBuilder append2 = sb.append("&amp;");
                        break;
                    case '\'':
                        StringBuilder append3 = sb.append("&#39;");
                        break;
                    case '<':
                        StringBuilder append4 = sb.append("&lt;");
                        break;
                    case '>':
                        StringBuilder append5 = sb.append("&gt;");
                        break;
                    default:
                        StringBuilder append6 = sb.append(c);
                        break;
                }
            }
            return sb.toString();
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            Locale locale2 = locale;
            Locale locale3 = locale2;
            if (locale2 != null && !locale2.equals(TextUtilsCompat.ROOT)) {
                String maximizeAndGetScript = ICUCompat.maximizeAndGetScript(locale2);
                String scriptSubtag = maximizeAndGetScript;
                if (maximizeAndGetScript == null) {
                    return getLayoutDirectionFromFirstChar(locale2);
                }
                if (scriptSubtag.equalsIgnoreCase(TextUtilsCompat.ARAB_SCRIPT_SUBTAG) || scriptSubtag.equalsIgnoreCase(TextUtilsCompat.HEBR_SCRIPT_SUBTAG)) {
                    return 1;
                }
            }
            return 0;
        }

        private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
            Locale locale2 = locale;
            Locale locale3 = locale2;
            switch (Character.getDirectionality(locale2.getDisplayName(locale2).charAt(0))) {
                case 1:
                case 2:
                    return 1;
                default:
                    return 0;
            }
        }
    }

    /* renamed from: android.support.v4.text.TextUtilsCompat$TextUtilsCompatJellybeanMr1Impl */
    private static class TextUtilsCompatJellybeanMr1Impl extends TextUtilsCompatImpl {
        TextUtilsCompatJellybeanMr1Impl() {
        }

        @NonNull
        public String htmlEncode(@NonNull String str) {
            String s = str;
            String str2 = s;
            return TextUtilsCompatJellybeanMr1.htmlEncode(s);
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            Locale locale2 = locale;
            Locale locale3 = locale2;
            return TextUtilsCompatJellybeanMr1.getLayoutDirectionFromLocale(locale2);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 17) {
            IMPL = new TextUtilsCompatImpl();
        } else {
            IMPL = new TextUtilsCompatJellybeanMr1Impl();
        }
    }

    @NonNull
    public static String htmlEncode(@NonNull String str) {
        String s = str;
        String str2 = s;
        return IMPL.htmlEncode(s);
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        Locale locale2 = locale;
        Locale locale3 = locale2;
        return IMPL.getLayoutDirectionFromLocale(locale2);
    }

    private TextUtilsCompat() {
    }
}
