package android.support.p000v4.text;

import java.nio.CharBuffer;
import java.util.Locale;

/* renamed from: android.support.v4.text.TextDirectionHeuristicsCompat */
public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, false);
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, true);
    public static final TextDirectionHeuristicCompat LOCALE = TextDirectionHeuristicLocale.INSTANCE;
    public static final TextDirectionHeuristicCompat LTR = new TextDirectionHeuristicInternal(null, false);
    public static final TextDirectionHeuristicCompat RTL = new TextDirectionHeuristicInternal(null, true);
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    /* renamed from: android.support.v4.text.TextDirectionHeuristicsCompat$AnyStrong */
    private static class AnyStrong implements TextDirectionAlgorithm {
        public static final AnyStrong INSTANCE_LTR = new AnyStrong(false);
        public static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
        private final boolean mLookForRtl;

        public int checkRtl(CharSequence charSequence, int i, int i2) {
            CharSequence cs = charSequence;
            int start = i;
            int count = i2;
            CharSequence charSequence2 = cs;
            int i3 = start;
            int i4 = count;
            boolean haveUnlookedFor = false;
            int e = start + count;
            for (int i5 = start; i5 < e; i5++) {
                switch (TextDirectionHeuristicsCompat.isRtlText(Character.getDirectionality(cs.charAt(i5)))) {
                    case 0:
                        if (!this.mLookForRtl) {
                            haveUnlookedFor = true;
                            break;
                        } else {
                            return 0;
                        }
                    case 1:
                        if (this.mLookForRtl) {
                            haveUnlookedFor = true;
                            break;
                        } else {
                            return 1;
                        }
                }
            }
            if (!haveUnlookedFor) {
                return 2;
            }
            return !this.mLookForRtl ? 0 : 1;
        }

        private AnyStrong(boolean z) {
            this.mLookForRtl = z;
        }
    }

    /* renamed from: android.support.v4.text.TextDirectionHeuristicsCompat$FirstStrong */
    private static class FirstStrong implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE = new FirstStrong();

        public int checkRtl(CharSequence charSequence, int i, int i2) {
            CharSequence cs = charSequence;
            int start = i;
            int count = i2;
            CharSequence charSequence2 = cs;
            int i3 = start;
            int i4 = count;
            int result = 2;
            int e = start + count;
            for (int i5 = start; i5 < e && result == 2; i5++) {
                result = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(cs.charAt(i5)));
            }
            return result;
        }

        private FirstStrong() {
        }
    }

    /* renamed from: android.support.v4.text.TextDirectionHeuristicsCompat$TextDirectionAlgorithm */
    private interface TextDirectionAlgorithm {
        int checkRtl(CharSequence charSequence, int i, int i2);
    }

    /* renamed from: android.support.v4.text.TextDirectionHeuristicsCompat$TextDirectionHeuristicImpl */
    private static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm mAlgorithm;

        /* access modifiers changed from: protected */
        public abstract boolean defaultIsRtl();

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            TextDirectionAlgorithm algorithm = textDirectionAlgorithm;
            TextDirectionAlgorithm textDirectionAlgorithm2 = algorithm;
            this.mAlgorithm = algorithm;
        }

        public boolean isRtl(char[] cArr, int i, int i2) {
            char[] array = cArr;
            int start = i;
            int count = i2;
            char[] cArr2 = array;
            int i3 = start;
            int i4 = count;
            return isRtl((CharSequence) CharBuffer.wrap(array), start, count);
        }

        public boolean isRtl(CharSequence charSequence, int i, int i2) {
            CharSequence cs = charSequence;
            int start = i;
            int count = i2;
            CharSequence charSequence2 = cs;
            int i3 = start;
            int i4 = count;
            if (cs == null || start < 0 || count < 0 || cs.length() - count < start) {
                throw new IllegalArgumentException();
            } else if (this.mAlgorithm != null) {
                return doCheck(cs, start, count);
            } else {
                return defaultIsRtl();
            }
        }

        private boolean doCheck(CharSequence charSequence, int i, int i2) {
            CharSequence cs = charSequence;
            int start = i;
            int count = i2;
            CharSequence charSequence2 = cs;
            int i3 = start;
            int i4 = count;
            switch (this.mAlgorithm.checkRtl(cs, start, count)) {
                case 0:
                    return true;
                case 1:
                    return false;
                default:
                    return defaultIsRtl();
            }
        }
    }

    /* renamed from: android.support.v4.text.TextDirectionHeuristicsCompat$TextDirectionHeuristicInternal */
    private static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean z) {
            TextDirectionAlgorithm algorithm = textDirectionAlgorithm;
            TextDirectionAlgorithm textDirectionAlgorithm2 = algorithm;
            boolean defaultIsRtl = z;
            super(algorithm);
            this.mDefaultIsRtl = defaultIsRtl;
        }

        /* access modifiers changed from: protected */
        public boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    /* renamed from: android.support.v4.text.TextDirectionHeuristicsCompat$TextDirectionHeuristicLocale */
    private static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        public static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        /* access modifiers changed from: protected */
        public boolean defaultIsRtl() {
            int layoutDirectionFromLocale = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
            int i = layoutDirectionFromLocale;
            return layoutDirectionFromLocale == 1;
        }
    }

    static int isRtlText(int i) {
        int directionality = i;
        int i2 = directionality;
        switch (directionality) {
            case 0:
                return 1;
            case 1:
            case 2:
                return 0;
            default:
                return 2;
        }
    }

    static int isRtlTextOrFormat(int i) {
        int directionality = i;
        int i2 = directionality;
        switch (directionality) {
            case 0:
            case 14:
            case 15:
                return 1;
            case 1:
            case 2:
            case 16:
            case 17:
                return 0;
            default:
                return 2;
        }
    }

    private TextDirectionHeuristicsCompat() {
    }
}
