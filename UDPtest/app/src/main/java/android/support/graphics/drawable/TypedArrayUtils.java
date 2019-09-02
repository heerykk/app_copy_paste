package android.support.graphics.drawable;

import android.content.res.TypedArray;
import org.xmlpull.v1.XmlPullParser;

class TypedArrayUtils {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";

    TypedArrayUtils() {
    }

    public static boolean hasAttribute(XmlPullParser xmlPullParser, String str) {
        XmlPullParser parser = xmlPullParser;
        String attrName = str;
        XmlPullParser xmlPullParser2 = parser;
        String str2 = attrName;
        return parser.getAttributeValue(NAMESPACE, attrName) != null;
    }

    public static float getNamedFloat(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, float f) {
        TypedArray a = typedArray;
        XmlPullParser parser = xmlPullParser;
        String attrName = str;
        int resId = i;
        float defaultValue = f;
        TypedArray typedArray2 = a;
        XmlPullParser xmlPullParser2 = parser;
        String str2 = attrName;
        int i2 = resId;
        float f2 = defaultValue;
        boolean hasAttribute = hasAttribute(parser, attrName);
        boolean z = hasAttribute;
        if (hasAttribute) {
            return a.getFloat(resId, defaultValue);
        }
        return defaultValue;
    }

    public static boolean getNamedBoolean(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, boolean z) {
        TypedArray a = typedArray;
        XmlPullParser parser = xmlPullParser;
        String attrName = str;
        int resId = i;
        TypedArray typedArray2 = a;
        XmlPullParser xmlPullParser2 = parser;
        String str2 = attrName;
        int i2 = resId;
        boolean defaultValue = z;
        boolean hasAttribute = hasAttribute(parser, attrName);
        boolean z2 = hasAttribute;
        if (hasAttribute) {
            return a.getBoolean(resId, defaultValue);
        }
        return defaultValue;
    }

    public static int getNamedInt(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, int i2) {
        TypedArray a = typedArray;
        XmlPullParser parser = xmlPullParser;
        String attrName = str;
        int resId = i;
        int defaultValue = i2;
        TypedArray typedArray2 = a;
        XmlPullParser xmlPullParser2 = parser;
        String str2 = attrName;
        int i3 = resId;
        int i4 = defaultValue;
        boolean hasAttribute = hasAttribute(parser, attrName);
        boolean z = hasAttribute;
        if (hasAttribute) {
            return a.getInt(resId, defaultValue);
        }
        return defaultValue;
    }

    public static int getNamedColor(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, int i2) {
        TypedArray a = typedArray;
        XmlPullParser parser = xmlPullParser;
        String attrName = str;
        int resId = i;
        int defaultValue = i2;
        TypedArray typedArray2 = a;
        XmlPullParser xmlPullParser2 = parser;
        String str2 = attrName;
        int i3 = resId;
        int i4 = defaultValue;
        boolean hasAttribute = hasAttribute(parser, attrName);
        boolean z = hasAttribute;
        if (hasAttribute) {
            return a.getColor(resId, defaultValue);
        }
        return defaultValue;
    }
}
