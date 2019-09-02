package android.support.p003v7.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.graphics.ColorUtils;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: android.support.v7.content.res.AppCompatColorStateListInflater */
final class AppCompatColorStateListInflater {
    private static final int DEFAULT_COLOR = -65536;

    private AppCompatColorStateListInflater() {
    }

    @NonNull
    public static ColorStateList createFromXml(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @Nullable Theme theme) throws XmlPullParserException, IOException {
        int next;
        int type;
        Resources r = resources;
        XmlPullParser parser = xmlPullParser;
        Theme theme2 = theme;
        Resources resources2 = r;
        XmlPullParser xmlPullParser2 = parser;
        Theme theme3 = theme2;
        AttributeSet attrs = Xml.asAttributeSet(parser);
        do {
            next = parser.next();
            type = next;
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (type == 2) {
            return createFromXmlInner(r, parser, attrs, theme2);
        }
        throw new XmlPullParserException("No start tag found");
    }

    @NonNull
    private static ColorStateList createFromXmlInner(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme) throws XmlPullParserException, IOException {
        Resources r = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Theme theme2 = theme;
        Resources resources2 = r;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Theme theme3 = theme2;
        String name = parser.getName();
        String name2 = name;
        if (name.equals("selector")) {
            return inflate(r, parser, attrs, theme2);
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": invalid color state list tag " + name2);
    }

    private static ColorStateList inflate(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme) throws XmlPullParserException, IOException {
        Resources r = resources;
        XmlPullParser parser = xmlPullParser;
        AttributeSet attrs = attributeSet;
        Theme theme2 = theme;
        Resources resources2 = r;
        XmlPullParser xmlPullParser2 = parser;
        AttributeSet attributeSet2 = attrs;
        Theme theme3 = theme2;
        int innerDepth = parser.getDepth() + 1;
        int[][] iArr = new int[20][];
        int[][] stateSpecList = iArr;
        int[] colorList = new int[iArr.length];
        int listSize = 0;
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 1) {
                break;
            }
            int depth = parser.getDepth();
            int depth2 = depth;
            if (depth < innerDepth && type == 3) {
                break;
            } else if (type == 2 && depth2 <= innerDepth && parser.getName().equals("item")) {
                TypedArray obtainAttributes = obtainAttributes(r, theme2, attrs, C0268R.styleable.ColorStateListItem);
                TypedArray a = obtainAttributes;
                int baseColor = obtainAttributes.getColor(C0268R.styleable.ColorStateListItem_android_color, -65281);
                float alphaMod = 1.0f;
                if (a.hasValue(C0268R.styleable.ColorStateListItem_android_alpha)) {
                    alphaMod = a.getFloat(C0268R.styleable.ColorStateListItem_android_alpha, 1.0f);
                } else if (a.hasValue(C0268R.styleable.ColorStateListItem_alpha)) {
                    alphaMod = a.getFloat(C0268R.styleable.ColorStateListItem_alpha, 1.0f);
                }
                a.recycle();
                int j = 0;
                int attributeCount = attrs.getAttributeCount();
                int numAttrs = attributeCount;
                int[] stateSpec = new int[attributeCount];
                for (int i = 0; i < numAttrs; i++) {
                    int attributeNameResource = attrs.getAttributeNameResource(i);
                    int stateResId = attributeNameResource;
                    if (!(attributeNameResource == 16843173 || stateResId == 16843551 || stateResId == C0268R.attr.alpha)) {
                        int i2 = j;
                        j++;
                        stateSpec[i2] = !attrs.getAttributeBooleanValue(i, false) ? -stateResId : stateResId;
                    }
                }
                int[] trimStateSet = StateSet.trimStateSet(stateSpec, j);
                int[] stateSpec2 = trimStateSet;
                int i3 = modulateColorAlpha(baseColor, alphaMod);
                if (listSize == 0 || trimStateSet.length == 0) {
                    int defaultColor = i3;
                }
                colorList = GrowingArrayUtils.append(colorList, listSize, i3);
                stateSpecList = (int[][]) GrowingArrayUtils.append((T[]) stateSpecList, listSize, stateSpec2);
                listSize++;
            }
        }
        int[] colors = new int[listSize];
        int[][] stateSpecs = new int[listSize][];
        System.arraycopy(colorList, 0, colors, 0, listSize);
        System.arraycopy(stateSpecList, 0, stateSpecs, 0, listSize);
        ColorStateList colorStateList = new ColorStateList(stateSpecs, colors);
        return colorStateList;
    }

    private static TypedArray obtainAttributes(Resources resources, Theme theme, AttributeSet attributeSet, int[] iArr) {
        TypedArray obtainAttributes;
        Resources res = resources;
        Theme theme2 = theme;
        AttributeSet set = attributeSet;
        int[] attrs = iArr;
        Resources resources2 = res;
        Theme theme3 = theme2;
        AttributeSet attributeSet2 = set;
        int[] iArr2 = attrs;
        if (theme2 != null) {
            obtainAttributes = theme2.obtainStyledAttributes(set, attrs, 0, 0);
        } else {
            obtainAttributes = res.obtainAttributes(set, attrs);
        }
        return obtainAttributes;
    }

    private static int modulateColorAlpha(int i, float f) {
        int color = i;
        float alphaMod = f;
        int i2 = color;
        float f2 = alphaMod;
        return ColorUtils.setAlphaComponent(color, Math.round(((float) Color.alpha(color)) * alphaMod));
    }
}
