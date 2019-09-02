package android.support.p003v7.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: android.support.v7.widget.ResourcesWrapper */
class ResourcesWrapper extends Resources {
    private final Resources mResources;

    public ResourcesWrapper(Resources resources) {
        Resources resources2 = resources;
        Resources resources3 = resources2;
        super(resources2.getAssets(), resources2.getDisplayMetrics(), resources2.getConfiguration());
        this.mResources = resources2;
    }

    public CharSequence getText(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getText(id);
    }

    public CharSequence getQuantityText(int i, int i2) throws NotFoundException {
        int id = i;
        int quantity = i2;
        int i3 = id;
        int i4 = quantity;
        return this.mResources.getQuantityText(id, quantity);
    }

    public String getString(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getString(id);
    }

    public String getString(int i, Object... objArr) throws NotFoundException {
        int id = i;
        Object[] formatArgs = objArr;
        int i2 = id;
        Object[] objArr2 = formatArgs;
        return this.mResources.getString(id, formatArgs);
    }

    public String getQuantityString(int i, int i2, Object... objArr) throws NotFoundException {
        int id = i;
        int quantity = i2;
        Object[] formatArgs = objArr;
        int i3 = id;
        int i4 = quantity;
        Object[] objArr2 = formatArgs;
        return this.mResources.getQuantityString(id, quantity, formatArgs);
    }

    public String getQuantityString(int i, int i2) throws NotFoundException {
        int id = i;
        int quantity = i2;
        int i3 = id;
        int i4 = quantity;
        return this.mResources.getQuantityString(id, quantity);
    }

    public CharSequence getText(int i, CharSequence charSequence) {
        int id = i;
        CharSequence def = charSequence;
        int i2 = id;
        CharSequence charSequence2 = def;
        return this.mResources.getText(id, def);
    }

    public CharSequence[] getTextArray(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getTextArray(id);
    }

    public String[] getStringArray(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getStringArray(id);
    }

    public int[] getIntArray(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getIntArray(id);
    }

    public TypedArray obtainTypedArray(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.obtainTypedArray(id);
    }

    public float getDimension(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getDimension(id);
    }

    public int getDimensionPixelOffset(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getDimensionPixelOffset(id);
    }

    public int getDimensionPixelSize(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getDimensionPixelSize(id);
    }

    public float getFraction(int i, int i2, int i3) {
        int id = i;
        int base = i2;
        int pbase = i3;
        int i4 = id;
        int i5 = base;
        int i6 = pbase;
        return this.mResources.getFraction(id, base, pbase);
    }

    public Drawable getDrawable(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getDrawable(id);
    }

    public Drawable getDrawable(int i, Theme theme) throws NotFoundException {
        int id = i;
        Theme theme2 = theme;
        int i2 = id;
        Theme theme3 = theme2;
        return this.mResources.getDrawable(id, theme2);
    }

    public Drawable getDrawableForDensity(int i, int i2) throws NotFoundException {
        int id = i;
        int density = i2;
        int i3 = id;
        int i4 = density;
        return this.mResources.getDrawableForDensity(id, density);
    }

    public Drawable getDrawableForDensity(int i, int i2, Theme theme) {
        int id = i;
        int density = i2;
        Theme theme2 = theme;
        int i3 = id;
        int i4 = density;
        Theme theme3 = theme2;
        return this.mResources.getDrawableForDensity(id, density, theme2);
    }

    public Movie getMovie(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getMovie(id);
    }

    public int getColor(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getColor(id);
    }

    public ColorStateList getColorStateList(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getColorStateList(id);
    }

    public boolean getBoolean(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getBoolean(id);
    }

    public int getInteger(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getInteger(id);
    }

    public XmlResourceParser getLayout(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getLayout(id);
    }

    public XmlResourceParser getAnimation(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getAnimation(id);
    }

    public XmlResourceParser getXml(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.getXml(id);
    }

    public InputStream openRawResource(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.openRawResource(id);
    }

    public InputStream openRawResource(int i, TypedValue typedValue) throws NotFoundException {
        int id = i;
        TypedValue value = typedValue;
        int i2 = id;
        TypedValue typedValue2 = value;
        return this.mResources.openRawResource(id, value);
    }

    public AssetFileDescriptor openRawResourceFd(int i) throws NotFoundException {
        int id = i;
        int i2 = id;
        return this.mResources.openRawResourceFd(id);
    }

    public void getValue(int i, TypedValue typedValue, boolean z) throws NotFoundException {
        int id = i;
        TypedValue outValue = typedValue;
        int i2 = id;
        TypedValue typedValue2 = outValue;
        this.mResources.getValue(id, outValue, z);
    }

    public void getValueForDensity(int i, int i2, TypedValue typedValue, boolean z) throws NotFoundException {
        int id = i;
        int density = i2;
        TypedValue outValue = typedValue;
        int i3 = id;
        int i4 = density;
        TypedValue typedValue2 = outValue;
        this.mResources.getValueForDensity(id, density, outValue, z);
    }

    public void getValue(String str, TypedValue typedValue, boolean z) throws NotFoundException {
        String name = str;
        TypedValue outValue = typedValue;
        String str2 = name;
        TypedValue typedValue2 = outValue;
        this.mResources.getValue(name, outValue, z);
    }

    public TypedArray obtainAttributes(AttributeSet attributeSet, int[] iArr) {
        AttributeSet set = attributeSet;
        int[] attrs = iArr;
        AttributeSet attributeSet2 = set;
        int[] iArr2 = attrs;
        return this.mResources.obtainAttributes(set, attrs);
    }

    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        Configuration config = configuration;
        DisplayMetrics metrics = displayMetrics;
        Configuration configuration2 = config;
        DisplayMetrics displayMetrics2 = metrics;
        super.updateConfiguration(config, metrics);
        if (this.mResources != null) {
            this.mResources.updateConfiguration(config, metrics);
        }
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.mResources.getDisplayMetrics();
    }

    public Configuration getConfiguration() {
        return this.mResources.getConfiguration();
    }

    public int getIdentifier(String str, String str2, String str3) {
        String name = str;
        String defType = str2;
        String defPackage = str3;
        String str4 = name;
        String str5 = defType;
        String str6 = defPackage;
        return this.mResources.getIdentifier(name, defType, defPackage);
    }

    public String getResourceName(int i) throws NotFoundException {
        int resid = i;
        int i2 = resid;
        return this.mResources.getResourceName(resid);
    }

    public String getResourcePackageName(int i) throws NotFoundException {
        int resid = i;
        int i2 = resid;
        return this.mResources.getResourcePackageName(resid);
    }

    public String getResourceTypeName(int i) throws NotFoundException {
        int resid = i;
        int i2 = resid;
        return this.mResources.getResourceTypeName(resid);
    }

    public String getResourceEntryName(int i) throws NotFoundException {
        int resid = i;
        int i2 = resid;
        return this.mResources.getResourceEntryName(resid);
    }

    public void parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) throws XmlPullParserException, IOException {
        XmlResourceParser parser = xmlResourceParser;
        Bundle outBundle = bundle;
        XmlResourceParser xmlResourceParser2 = parser;
        Bundle bundle2 = outBundle;
        this.mResources.parseBundleExtras(parser, outBundle);
    }

    public void parseBundleExtra(String str, AttributeSet attributeSet, Bundle bundle) throws XmlPullParserException {
        String tagName = str;
        AttributeSet attrs = attributeSet;
        Bundle outBundle = bundle;
        String str2 = tagName;
        AttributeSet attributeSet2 = attrs;
        Bundle bundle2 = outBundle;
        this.mResources.parseBundleExtra(tagName, attrs, outBundle);
    }
}
