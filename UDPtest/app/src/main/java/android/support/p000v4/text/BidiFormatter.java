package android.support.p000v4.text;

import android.text.SpannableStringBuilder;
import java.util.Locale;

/* renamed from: android.support.v4.text.BidiFormatter */
public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    /* access modifiers changed from: private */
    public static final BidiFormatter DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    /* access modifiers changed from: private */
    public static final BidiFormatter DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    /* access modifiers changed from: private */
    public static TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = '‪';
    private static final char LRM = '‎';
    private static final String LRM_STRING = Character.toString(LRM);
    private static final char PDF = '‬';
    private static final char RLE = '‫';
    private static final char RLM = '‏';
    private static final String RLM_STRING = Character.toString(RLM);
    private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
    private final int mFlags;
    private final boolean mIsRtlContext;

    /* renamed from: android.support.v4.text.BidiFormatter$Builder */
    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

        public Builder() {
            initialize(BidiFormatter.access$000(Locale.getDefault()));
        }

        public Builder(boolean z) {
            initialize(z);
        }

        public Builder(Locale locale) {
            Locale locale2 = locale;
            Locale locale3 = locale2;
            initialize(BidiFormatter.access$000(locale2));
        }

        private void initialize(boolean z) {
            this.mIsRtlContext = z;
            this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public Builder stereoReset(boolean z) {
            if (!z) {
                this.mFlags &= -3;
            } else {
                this.mFlags |= 2;
            }
            return this;
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            TextDirectionHeuristicCompat heuristic = textDirectionHeuristicCompat;
            TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = heuristic;
            this.mTextDirectionHeuristicCompat = heuristic;
            return this;
        }

        private static BidiFormatter getDefaultInstanceFromContext(boolean z) {
            return !z ? BidiFormatter.DEFAULT_LTR_INSTANCE : BidiFormatter.DEFAULT_RTL_INSTANCE;
        }

        public BidiFormatter build() {
            if (this.mFlags == 2 && this.mTextDirectionHeuristicCompat == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC) {
                return getDefaultInstanceFromContext(this.mIsRtlContext);
            }
            return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat, null);
        }
    }

    /* renamed from: android.support.v4.text.BidiFormatter$DirectionalityEstimator */
    private static class DirectionalityEstimator {
        private static final byte[] DIR_TYPE_CACHE = new byte[DIR_TYPE_CACHE_SIZE];
        private static final int DIR_TYPE_CACHE_SIZE = 1792;
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final CharSequence text;

        static {
            for (int i = 0; i < DIR_TYPE_CACHE_SIZE; i++) {
                DIR_TYPE_CACHE[i] = (byte) Character.getDirectionality(i);
            }
        }

        DirectionalityEstimator(CharSequence charSequence, boolean z) {
            CharSequence text2 = charSequence;
            CharSequence charSequence2 = text2;
            boolean isHtml2 = z;
            this.text = text2;
            this.isHtml = isHtml2;
            this.length = text2.length();
        }

        /* access modifiers changed from: 0000 */
        public int getEntryDir() {
            this.charIndex = 0;
            int embeddingLevel = 0;
            int embeddingLevelDir = 0;
            int firstNonEmptyEmbeddingLevel = 0;
            while (this.charIndex < this.length && firstNonEmptyEmbeddingLevel == 0) {
                switch (dirTypeForward()) {
                    case 0:
                        if (embeddingLevel != 0) {
                            firstNonEmptyEmbeddingLevel = embeddingLevel;
                            break;
                        } else {
                            return -1;
                        }
                    case 1:
                    case 2:
                        if (embeddingLevel != 0) {
                            firstNonEmptyEmbeddingLevel = embeddingLevel;
                            break;
                        } else {
                            return 1;
                        }
                    case 9:
                        break;
                    case 14:
                    case 15:
                        embeddingLevel++;
                        embeddingLevelDir = -1;
                        break;
                    case 16:
                    case 17:
                        embeddingLevel++;
                        embeddingLevelDir = 1;
                        break;
                    case 18:
                        embeddingLevel--;
                        embeddingLevelDir = 0;
                        break;
                    default:
                        firstNonEmptyEmbeddingLevel = embeddingLevel;
                        break;
                }
            }
            if (firstNonEmptyEmbeddingLevel == 0) {
                return 0;
            }
            if (embeddingLevelDir != 0) {
                return embeddingLevelDir;
            }
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case 14:
                    case 15:
                        if (firstNonEmptyEmbeddingLevel != embeddingLevel) {
                            embeddingLevel--;
                            break;
                        } else {
                            return -1;
                        }
                    case 16:
                    case 17:
                        if (firstNonEmptyEmbeddingLevel != embeddingLevel) {
                            embeddingLevel--;
                            break;
                        } else {
                            return 1;
                        }
                    case 18:
                        embeddingLevel++;
                        break;
                }
            }
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public int getExitDir() {
            this.charIndex = this.length;
            int embeddingLevel = 0;
            int lastNonEmptyEmbeddingLevel = 0;
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case 0:
                        if (embeddingLevel != 0) {
                            if (lastNonEmptyEmbeddingLevel == 0) {
                                lastNonEmptyEmbeddingLevel = embeddingLevel;
                                break;
                            } else {
                                break;
                            }
                        } else {
                            return -1;
                        }
                    case 1:
                    case 2:
                        if (embeddingLevel != 0) {
                            if (lastNonEmptyEmbeddingLevel == 0) {
                                lastNonEmptyEmbeddingLevel = embeddingLevel;
                                break;
                            } else {
                                break;
                            }
                        } else {
                            return 1;
                        }
                    case 9:
                        break;
                    case 14:
                    case 15:
                        if (lastNonEmptyEmbeddingLevel != embeddingLevel) {
                            embeddingLevel--;
                            break;
                        } else {
                            return -1;
                        }
                    case 16:
                    case 17:
                        if (lastNonEmptyEmbeddingLevel != embeddingLevel) {
                            embeddingLevel--;
                            break;
                        } else {
                            return 1;
                        }
                    case 18:
                        embeddingLevel++;
                        break;
                    default:
                        if (lastNonEmptyEmbeddingLevel == 0) {
                            lastNonEmptyEmbeddingLevel = embeddingLevel;
                            break;
                        } else {
                            break;
                        }
                }
            }
            return 0;
        }

        private static byte getCachedDirectionality(char c) {
            char c2 = c;
            return c2 >= DIR_TYPE_CACHE_SIZE ? Character.getDirectionality(c2) : DIR_TYPE_CACHE[c2];
        }

        /* access modifiers changed from: 0000 */
        public byte dirTypeForward() {
            this.lastChar = (char) this.text.charAt(this.charIndex);
            if (!Character.isHighSurrogate(this.lastChar)) {
                this.charIndex++;
                byte dirType = getCachedDirectionality(this.lastChar);
                if (this.isHtml) {
                    if (this.lastChar == '<') {
                        dirType = skipTagForward();
                    } else if (this.lastChar == '&') {
                        dirType = skipEntityForward();
                    }
                }
                return dirType;
            }
            int codePointAt = Character.codePointAt(this.text, this.charIndex);
            int i = codePointAt;
            this.charIndex += Character.charCount(codePointAt);
            return Character.getDirectionality(codePointAt);
        }

        /* access modifiers changed from: 0000 */
        public byte dirTypeBackward() {
            this.lastChar = (char) this.text.charAt(this.charIndex - 1);
            if (!Character.isLowSurrogate(this.lastChar)) {
                this.charIndex--;
                byte dirType = getCachedDirectionality(this.lastChar);
                if (this.isHtml) {
                    if (this.lastChar == '>') {
                        dirType = skipTagBackward();
                    } else if (this.lastChar == ';') {
                        dirType = skipEntityBackward();
                    }
                }
                return dirType;
            }
            int codePointBefore = Character.codePointBefore(this.text, this.charIndex);
            int i = codePointBefore;
            this.charIndex -= Character.charCount(codePointBefore);
            return Character.getDirectionality(codePointBefore);
        }

        private byte skipTagForward() {
            int initialCharIndex = this.charIndex;
            while (this.charIndex < this.length) {
                CharSequence charSequence = this.text;
                int i = this.charIndex;
                this.charIndex = i + 1;
                this.lastChar = (char) charSequence.charAt(i);
                if (this.lastChar != '>') {
                    if (this.lastChar == '\"' || this.lastChar == '\'') {
                        char quote = this.lastChar;
                        while (this.charIndex < this.length) {
                            CharSequence charSequence2 = this.text;
                            int i2 = this.charIndex;
                            this.charIndex = i2 + 1;
                            char charAt = charSequence2.charAt(i2);
                            char c = charAt;
                            this.lastChar = (char) charAt;
                            if (c == quote) {
                                break;
                            }
                        }
                    }
                } else {
                    return 12;
                }
            }
            this.charIndex = initialCharIndex;
            this.lastChar = '<';
            return 13;
        }

        private byte skipTagBackward() {
            int initialCharIndex = this.charIndex;
            while (this.charIndex > 0) {
                CharSequence charSequence = this.text;
                int i = this.charIndex - 1;
                this.charIndex = i;
                this.lastChar = (char) charSequence.charAt(i);
                if (this.lastChar != '<') {
                    if (this.lastChar != '>') {
                        if (this.lastChar == '\"' || this.lastChar == '\'') {
                            char quote = this.lastChar;
                            while (this.charIndex > 0) {
                                CharSequence charSequence2 = this.text;
                                int i2 = this.charIndex - 1;
                                this.charIndex = i2;
                                char charAt = charSequence2.charAt(i2);
                                char c = charAt;
                                this.lastChar = (char) charAt;
                                if (c == quote) {
                                    break;
                                }
                            }
                        }
                    } else {
                        break;
                    }
                } else {
                    return 12;
                }
            }
            this.charIndex = initialCharIndex;
            this.lastChar = '>';
            return 13;
        }

        private byte skipEntityForward() {
            while (this.charIndex < this.length) {
                CharSequence charSequence = this.text;
                int i = this.charIndex;
                this.charIndex = i + 1;
                char charAt = charSequence.charAt(i);
                char c = charAt;
                this.lastChar = (char) charAt;
                if (c == ';') {
                    break;
                }
            }
            return 12;
        }

        private byte skipEntityBackward() {
            int initialCharIndex = this.charIndex;
            while (this.charIndex > 0) {
                CharSequence charSequence = this.text;
                int i = this.charIndex - 1;
                this.charIndex = i;
                this.lastChar = (char) charSequence.charAt(i);
                if (this.lastChar != '&') {
                    if (this.lastChar == ';') {
                        break;
                    }
                } else {
                    return 12;
                }
            }
            this.charIndex = initialCharIndex;
            this.lastChar = ';';
            return 13;
        }
    }

    /* synthetic */ BidiFormatter(boolean z, int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat, C01851 r16) {
        int x1 = i;
        TextDirectionHeuristicCompat x2 = textDirectionHeuristicCompat;
        int i2 = x1;
        TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = x2;
        C01851 r9 = r16;
        this(z, x1, x2);
    }

    static /* synthetic */ boolean access$000(Locale locale) {
        Locale x0 = locale;
        Locale locale2 = x0;
        return isRtlLocale(x0);
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    public static BidiFormatter getInstance(boolean z) {
        return new Builder(z).build();
    }

    public static BidiFormatter getInstance(Locale locale) {
        Locale locale2 = locale;
        Locale locale3 = locale2;
        return new Builder(locale2).build();
    }

    private BidiFormatter(boolean z, int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        int flags = i;
        TextDirectionHeuristicCompat heuristic = textDirectionHeuristicCompat;
        int i2 = flags;
        TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = heuristic;
        this.mIsRtlContext = z;
        this.mFlags = flags;
        this.mDefaultTextDirectionHeuristicCompat = heuristic;
    }

    public boolean isRtlContext() {
        return this.mIsRtlContext;
    }

    public boolean getStereoReset() {
        return (this.mFlags & 2) != 0;
    }

    private String markAfter(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        CharSequence str = charSequence;
        TextDirectionHeuristicCompat heuristic = textDirectionHeuristicCompat;
        CharSequence charSequence2 = str;
        TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = heuristic;
        boolean isRtl = heuristic.isRtl(str, 0, str.length());
        if (!this.mIsRtlContext && (isRtl || getExitDir(str) == 1)) {
            return LRM_STRING;
        }
        if (this.mIsRtlContext && (!isRtl || getExitDir(str) == -1)) {
            return RLM_STRING;
        }
        return "";
    }

    private String markBefore(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        CharSequence str = charSequence;
        TextDirectionHeuristicCompat heuristic = textDirectionHeuristicCompat;
        CharSequence charSequence2 = str;
        TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = heuristic;
        boolean isRtl = heuristic.isRtl(str, 0, str.length());
        if (!this.mIsRtlContext && (isRtl || getEntryDir(str) == 1)) {
            return LRM_STRING;
        }
        if (this.mIsRtlContext && (!isRtl || getEntryDir(str) == -1)) {
            return RLM_STRING;
        }
        return "";
    }

    public boolean isRtl(String str) {
        String str2 = str;
        String str3 = str2;
        return isRtl((CharSequence) str2);
    }

    public boolean isRtl(CharSequence charSequence) {
        CharSequence str = charSequence;
        CharSequence charSequence2 = str;
        return this.mDefaultTextDirectionHeuristicCompat.isRtl(str, 0, str.length());
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        String str2 = str;
        TextDirectionHeuristicCompat heuristic = textDirectionHeuristicCompat;
        String str3 = str2;
        TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = heuristic;
        boolean isolate = z;
        if (str2 != null) {
            return unicodeWrap((CharSequence) str2, heuristic, isolate).toString();
        }
        return null;
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        CharSequence str = charSequence;
        TextDirectionHeuristicCompat heuristic = textDirectionHeuristicCompat;
        CharSequence charSequence2 = str;
        TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = heuristic;
        boolean isolate = z;
        if (str == null) {
            return null;
        }
        boolean isRtl = heuristic.isRtl(str, 0, str.length());
        SpannableStringBuilder result = new SpannableStringBuilder();
        if (getStereoReset() && isolate) {
            SpannableStringBuilder append = result.append(markBefore(str, !isRtl ? TextDirectionHeuristicsCompat.LTR : TextDirectionHeuristicsCompat.RTL));
        }
        if (isRtl == this.mIsRtlContext) {
            SpannableStringBuilder append2 = result.append(str);
        } else {
            SpannableStringBuilder append3 = result.append(!isRtl ? LRE : RLE);
            SpannableStringBuilder append4 = result.append(str);
            SpannableStringBuilder append5 = result.append(PDF);
        }
        if (isolate) {
            SpannableStringBuilder append6 = result.append(markAfter(str, !isRtl ? TextDirectionHeuristicsCompat.LTR : TextDirectionHeuristicsCompat.RTL));
        }
        return result;
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        String str2 = str;
        TextDirectionHeuristicCompat heuristic = textDirectionHeuristicCompat;
        String str3 = str2;
        TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = heuristic;
        return unicodeWrap(str2, heuristic, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        CharSequence str = charSequence;
        TextDirectionHeuristicCompat heuristic = textDirectionHeuristicCompat;
        CharSequence charSequence2 = str;
        TextDirectionHeuristicCompat textDirectionHeuristicCompat2 = heuristic;
        return unicodeWrap(str, heuristic, true);
    }

    public String unicodeWrap(String str, boolean z) {
        String str2 = str;
        String str3 = str2;
        return unicodeWrap(str2, this.mDefaultTextDirectionHeuristicCompat, z);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, boolean z) {
        CharSequence str = charSequence;
        CharSequence charSequence2 = str;
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, z);
    }

    public String unicodeWrap(String str) {
        String str2 = str;
        String str3 = str2;
        return unicodeWrap(str2, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        CharSequence str = charSequence;
        CharSequence charSequence2 = str;
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    private static boolean isRtlLocale(Locale locale) {
        Locale locale2 = locale;
        Locale locale3 = locale2;
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale2) == 1;
    }

    private static int getExitDir(CharSequence charSequence) {
        CharSequence str = charSequence;
        CharSequence charSequence2 = str;
        return new DirectionalityEstimator(str, false).getExitDir();
    }

    private static int getEntryDir(CharSequence charSequence) {
        CharSequence str = charSequence;
        CharSequence charSequence2 = str;
        return new DirectionalityEstimator(str, false).getEntryDir();
    }
}
