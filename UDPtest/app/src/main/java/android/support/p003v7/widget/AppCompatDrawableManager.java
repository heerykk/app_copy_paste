package android.support.p003v7.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.ColorUtils;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.util.ArrayMap;
import android.support.p000v4.util.LongSparseArray;
import android.support.p000v4.util.LruCache;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.AppCompatDrawableManager */
public final class AppCompatDrawableManager {
    private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
    private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
    private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
    private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    private static final boolean DEBUG = false;
    private static final Mode DEFAULT_MODE = Mode.SRC_IN;
    private static AppCompatDrawableManager INSTANCE = null;
    private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
    private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
    private static final String TAG = "AppCompatDrawableManager";
    private static final int[] TINT_CHECKABLE_BUTTON_LIST;
    private static final int[] TINT_COLOR_CONTROL_NORMAL;
    private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
    private ArrayMap<String, InflateDelegate> mDelegates;
    private final Object mDrawableCacheLock = new Object();
    private final WeakHashMap<Context, LongSparseArray<WeakReference<ConstantState>>> mDrawableCaches = new WeakHashMap<>(0);
    private boolean mHasCheckedVectorDrawableSetup;
    private SparseArray<String> mKnownDrawableIdTags;
    private WeakHashMap<Context, SparseArray<ColorStateList>> mTintLists;
    private TypedValue mTypedValue;

    @RequiresApi(11)
    @TargetApi(11)
    /* renamed from: android.support.v7.widget.AppCompatDrawableManager$AvdcInflateDelegate */
    private static class AvdcInflateDelegate implements InflateDelegate {
        AvdcInflateDelegate() {
        }

        @SuppressLint({"NewApi"})
        public Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme) {
            Context context2 = context;
            XmlPullParser parser = xmlPullParser;
            AttributeSet attrs = attributeSet;
            Theme theme2 = theme;
            Context context3 = context2;
            XmlPullParser xmlPullParser2 = parser;
            AttributeSet attributeSet2 = attrs;
            Theme theme3 = theme2;
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner(context2, context2.getResources(), parser, attrs, theme2);
            } catch (Exception e) {
                Exception exc = e;
                int e2 = Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    /* renamed from: android.support.v7.widget.AppCompatDrawableManager$ColorFilterLruCache */
    private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int i) {
            int maxSize = i;
            int i2 = maxSize;
            super(maxSize);
        }

        /* access modifiers changed from: 0000 */
        public PorterDuffColorFilter get(int i, Mode mode) {
            int color = i;
            Mode mode2 = mode;
            int i2 = color;
            Mode mode3 = mode2;
            return (PorterDuffColorFilter) get(Integer.valueOf(generateCacheKey(color, mode2)));
        }

        /* access modifiers changed from: 0000 */
        public PorterDuffColorFilter put(int i, Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            int color = i;
            Mode mode2 = mode;
            PorterDuffColorFilter filter = porterDuffColorFilter;
            int i2 = color;
            Mode mode3 = mode2;
            PorterDuffColorFilter porterDuffColorFilter2 = filter;
            return (PorterDuffColorFilter) put(Integer.valueOf(generateCacheKey(color, mode2)), filter);
        }

        private static int generateCacheKey(int i, Mode mode) {
            int color = i;
            Mode mode2 = mode;
            int i2 = color;
            Mode mode3 = mode2;
            int hashCode = (31 * (31 + color)) + mode2.hashCode();
            int hashCode2 = hashCode;
            return hashCode;
        }
    }

    /* renamed from: android.support.v7.widget.AppCompatDrawableManager$InflateDelegate */
    private interface InflateDelegate {
        Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme);
    }

    /* renamed from: android.support.v7.widget.AppCompatDrawableManager$VdcInflateDelegate */
    private static class VdcInflateDelegate implements InflateDelegate {
        VdcInflateDelegate() {
        }

        @SuppressLint({"NewApi"})
        public Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme) {
            Context context2 = context;
            XmlPullParser parser = xmlPullParser;
            AttributeSet attrs = attributeSet;
            Theme theme2 = theme;
            Context context3 = context2;
            XmlPullParser xmlPullParser2 = parser;
            AttributeSet attributeSet2 = attrs;
            Theme theme3 = theme2;
            try {
                return VectorDrawableCompat.createFromXmlInner(context2.getResources(), parser, attrs, theme2);
            } catch (Exception e) {
                Exception exc = e;
                int e2 = Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    public AppCompatDrawableManager() {
    }

    static {
        int[] iArr = new int[3];
        iArr[0] = C0268R.C0269drawable.abc_textfield_search_default_mtrl_alpha;
        iArr[1] = C0268R.C0269drawable.abc_textfield_default_mtrl_alpha;
        iArr[2] = C0268R.C0269drawable.abc_ab_share_pack_mtrl_alpha;
        COLORFILTER_TINT_COLOR_CONTROL_NORMAL = iArr;
        int[] iArr2 = new int[7];
        iArr2[0] = C0268R.C0269drawable.abc_ic_commit_search_api_mtrl_alpha;
        iArr2[1] = C0268R.C0269drawable.abc_seekbar_tick_mark_material;
        iArr2[2] = C0268R.C0269drawable.abc_ic_menu_share_mtrl_alpha;
        iArr2[3] = C0268R.C0269drawable.abc_ic_menu_copy_mtrl_am_alpha;
        iArr2[4] = C0268R.C0269drawable.abc_ic_menu_cut_mtrl_alpha;
        iArr2[5] = C0268R.C0269drawable.abc_ic_menu_selectall_mtrl_alpha;
        iArr2[6] = C0268R.C0269drawable.abc_ic_menu_paste_mtrl_am_alpha;
        TINT_COLOR_CONTROL_NORMAL = iArr2;
        int[] iArr3 = new int[10];
        iArr3[0] = C0268R.C0269drawable.abc_textfield_activated_mtrl_alpha;
        iArr3[1] = C0268R.C0269drawable.abc_textfield_search_activated_mtrl_alpha;
        iArr3[2] = C0268R.C0269drawable.abc_cab_background_top_mtrl_alpha;
        iArr3[3] = C0268R.C0269drawable.abc_text_cursor_material;
        iArr3[4] = C0268R.C0269drawable.abc_text_select_handle_left_mtrl_dark;
        iArr3[5] = C0268R.C0269drawable.abc_text_select_handle_middle_mtrl_dark;
        iArr3[6] = C0268R.C0269drawable.abc_text_select_handle_right_mtrl_dark;
        iArr3[7] = C0268R.C0269drawable.abc_text_select_handle_left_mtrl_light;
        iArr3[8] = C0268R.C0269drawable.abc_text_select_handle_middle_mtrl_light;
        iArr3[9] = C0268R.C0269drawable.abc_text_select_handle_right_mtrl_light;
        COLORFILTER_COLOR_CONTROL_ACTIVATED = iArr3;
        int[] iArr4 = new int[3];
        iArr4[0] = C0268R.C0269drawable.abc_popup_background_mtrl_mult;
        iArr4[1] = C0268R.C0269drawable.abc_cab_background_internal_bg;
        iArr4[2] = C0268R.C0269drawable.abc_menu_hardkey_panel_mtrl_mult;
        COLORFILTER_COLOR_BACKGROUND_MULTIPLY = iArr4;
        int[] iArr5 = new int[2];
        iArr5[0] = C0268R.C0269drawable.abc_tab_indicator_material;
        iArr5[1] = C0268R.C0269drawable.abc_textfield_search_material;
        TINT_COLOR_CONTROL_STATE_LIST = iArr5;
        int[] iArr6 = new int[2];
        iArr6[0] = C0268R.C0269drawable.abc_btn_check_material;
        iArr6[1] = C0268R.C0269drawable.abc_btn_radio_material;
        TINT_CHECKABLE_BUTTON_LIST = iArr6;
    }

    public static AppCompatDrawableManager get() {
        if (INSTANCE == null) {
            INSTANCE = new AppCompatDrawableManager();
            installDefaultInflateDelegates(INSTANCE);
        }
        return INSTANCE;
    }

    private static void installDefaultInflateDelegates(@NonNull AppCompatDrawableManager appCompatDrawableManager) {
        AppCompatDrawableManager manager = appCompatDrawableManager;
        AppCompatDrawableManager appCompatDrawableManager2 = manager;
        if (VERSION.SDK_INT < 24) {
            manager.addDelegate("vector", new VdcInflateDelegate());
            if (VERSION.SDK_INT >= 11) {
                manager.addDelegate("animated-vector", new AvdcInflateDelegate());
            }
        }
    }

    public Drawable getDrawable(@NonNull Context context, @DrawableRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        return getDrawable(context2, resId, false);
    }

    /* access modifiers changed from: 0000 */
    public Drawable getDrawable(@NonNull Context context, @DrawableRes int i, boolean z) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        boolean failIfNotKnown = z;
        checkVectorDrawableSetup(context2);
        Drawable loadDrawableFromDelegates = loadDrawableFromDelegates(context2, resId);
        Drawable drawable = loadDrawableFromDelegates;
        if (loadDrawableFromDelegates == null) {
            drawable = createDrawableIfNeeded(context2, resId);
        }
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(context2, resId);
        }
        if (drawable != null) {
            drawable = tintDrawable(context2, resId, failIfNotKnown, drawable);
        }
        if (drawable != null) {
            DrawableUtils.fixDrawable(drawable);
        }
        return drawable;
    }

    public void onConfigurationChanged(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        Object obj = this.mDrawableCacheLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                LongSparseArray longSparseArray = (LongSparseArray) this.mDrawableCaches.get(context2);
                LongSparseArray longSparseArray2 = longSparseArray;
                if (longSparseArray != null) {
                    longSparseArray2.clear();
                }
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    private static long createCacheKey(TypedValue typedValue) {
        TypedValue tv = typedValue;
        TypedValue typedValue2 = tv;
        return (((long) tv.assetCookie) << 32) | ((long) tv.data);
    }

    private Drawable createDrawableIfNeeded(@NonNull Context context, @DrawableRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue tv = this.mTypedValue;
        context2.getResources().getValue(resId, tv, true);
        long createCacheKey = createCacheKey(tv);
        long j = createCacheKey;
        Drawable cachedDrawable = getCachedDrawable(context2, createCacheKey);
        Drawable dr = cachedDrawable;
        if (cachedDrawable != null) {
            return dr;
        }
        if (resId == C0268R.C0269drawable.abc_cab_background_top_material) {
            Drawable[] drawableArr = new Drawable[2];
            drawableArr[0] = getDrawable(context2, C0268R.C0269drawable.abc_cab_background_internal_bg);
            drawableArr[1] = getDrawable(context2, C0268R.C0269drawable.abc_cab_background_top_mtrl_alpha);
            dr = new LayerDrawable(drawableArr);
        }
        if (dr != null) {
            dr.setChangingConfigurations(tv.changingConfigurations);
            boolean addDrawableToCache = addDrawableToCache(context2, createCacheKey, dr);
        }
        return dr;
    }

    private Drawable tintDrawable(@NonNull Context context, @DrawableRes int i, boolean z, @NonNull Drawable drawable) {
        Context context2 = context;
        int resId = i;
        Drawable drawable2 = drawable;
        Context context3 = context2;
        int i2 = resId;
        boolean failIfNotKnown = z;
        Drawable drawable3 = drawable2;
        ColorStateList tintList = getTintList(context2, resId);
        ColorStateList tintList2 = tintList;
        if (tintList != null) {
            if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
                drawable3 = drawable2.mutate();
            }
            Drawable wrap = DrawableCompat.wrap(drawable3);
            drawable3 = wrap;
            DrawableCompat.setTintList(wrap, tintList2);
            Mode tintMode = getTintMode(resId);
            Mode tintMode2 = tintMode;
            if (tintMode != null) {
                DrawableCompat.setTintMode(drawable3, tintMode2);
            }
        } else if (resId == C0268R.C0269drawable.abc_seekbar_track_material) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable2;
            LayerDrawable ld = layerDrawable;
            setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(context2, C0268R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context2, C0268R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context2, C0268R.attr.colorControlActivated), DEFAULT_MODE);
        } else if (resId == C0268R.C0269drawable.abc_ratingbar_material || resId == C0268R.C0269drawable.abc_ratingbar_indicator_material || resId == C0268R.C0269drawable.abc_ratingbar_small_material) {
            LayerDrawable layerDrawable2 = (LayerDrawable) drawable2;
            LayerDrawable ld2 = layerDrawable2;
            setPorterDuffColorFilter(layerDrawable2.findDrawableByLayerId(16908288), ThemeUtils.getDisabledThemeAttrColor(context2, C0268R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(ld2.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context2, C0268R.attr.colorControlActivated), DEFAULT_MODE);
            setPorterDuffColorFilter(ld2.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context2, C0268R.attr.colorControlActivated), DEFAULT_MODE);
        } else {
            boolean tintDrawableUsingColorFilter = tintDrawableUsingColorFilter(context2, resId, drawable2);
            boolean z2 = tintDrawableUsingColorFilter;
            if (!tintDrawableUsingColorFilter && failIfNotKnown) {
                drawable3 = null;
            }
        }
        return drawable3;
    }

    private Drawable loadDrawableFromDelegates(@NonNull Context context, @DrawableRes int i) {
        int type;
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        if (this.mDelegates == null || this.mDelegates.isEmpty()) {
            return null;
        }
        if (this.mKnownDrawableIdTags == null) {
            this.mKnownDrawableIdTags = new SparseArray();
        } else {
            String cachedTagName = (String) this.mKnownDrawableIdTags.get(resId);
            if (SKIP_DRAWABLE_TAG.equals(cachedTagName) || (cachedTagName != null && this.mDelegates.get(cachedTagName) == null)) {
                return null;
            }
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue tv = this.mTypedValue;
        Resources resources = context2.getResources();
        Resources res = resources;
        resources.getValue(resId, tv, true);
        long createCacheKey = createCacheKey(tv);
        long j = createCacheKey;
        Drawable cachedDrawable = getCachedDrawable(context2, createCacheKey);
        Drawable dr = cachedDrawable;
        if (cachedDrawable != null) {
            return dr;
        }
        if (tv.string != null && tv.string.toString().endsWith(".xml")) {
            try {
                XmlResourceParser xml = res.getXml(resId);
                XmlResourceParser xmlResourceParser = xml;
                AttributeSet attrs = Xml.asAttributeSet(xml);
                while (true) {
                    int next = xmlResourceParser.next();
                    type = next;
                    if (next == 2) {
                        break;
                    } else if (next == 1) {
                        break;
                    }
                }
                if (type == 2) {
                    String tagName = xmlResourceParser.getName();
                    this.mKnownDrawableIdTags.append(resId, tagName);
                    InflateDelegate inflateDelegate = (InflateDelegate) this.mDelegates.get(tagName);
                    InflateDelegate delegate = inflateDelegate;
                    if (inflateDelegate != null) {
                        dr = delegate.createFromXmlInner(context2, xmlResourceParser, attrs, context2.getTheme());
                    }
                    if (dr != null) {
                        dr.setChangingConfigurations(tv.changingConfigurations);
                        if (!addDrawableToCache(context2, createCacheKey, dr)) {
                        }
                    }
                } else {
                    throw new XmlPullParserException("No start tag found");
                }
            } catch (Exception e) {
                Exception exc = e;
                int e2 = Log.e(TAG, "Exception while inflating drawable", e);
            }
        }
        if (dr == null) {
            this.mKnownDrawableIdTags.append(resId, SKIP_DRAWABLE_TAG);
        }
        return dr;
    }

    /* JADX INFO: finally extract failed */
    private Drawable getCachedDrawable(@NonNull Context context, long j) {
        Context context2 = context;
        long key = j;
        Context context3 = context2;
        long j2 = key;
        Object obj = this.mDrawableCacheLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                LongSparseArray longSparseArray = (LongSparseArray) this.mDrawableCaches.get(context2);
                LongSparseArray longSparseArray2 = longSparseArray;
                if (longSparseArray != null) {
                    WeakReference weakReference = (WeakReference) longSparseArray2.get(key);
                    WeakReference weakReference2 = weakReference;
                    if (weakReference != null) {
                        ConstantState constantState = (ConstantState) weakReference2.get();
                        ConstantState entry = constantState;
                        if (constantState == null) {
                            longSparseArray2.delete(key);
                        } else {
                            Drawable newDrawable = entry.newDrawable(context2.getResources());
                            return newDrawable;
                        }
                    }
                    return null;
                }
                return null;
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
    }

    private boolean addDrawableToCache(@NonNull Context context, long j, @NonNull Drawable drawable) {
        Context context2 = context;
        long key = j;
        Drawable drawable2 = drawable;
        Context context3 = context2;
        long j2 = key;
        Drawable drawable3 = drawable2;
        ConstantState constantState = drawable2.getConstantState();
        ConstantState cs = constantState;
        if (constantState == null) {
            return false;
        }
        Object obj = this.mDrawableCacheLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                LongSparseArray longSparseArray = (LongSparseArray) this.mDrawableCaches.get(context2);
                LongSparseArray longSparseArray2 = longSparseArray;
                if (longSparseArray == null) {
                    longSparseArray2 = new LongSparseArray();
                    Object put = this.mDrawableCaches.put(context2, longSparseArray2);
                }
                longSparseArray2.put(key, new WeakReference(cs));
                return true;
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Drawable onDrawableLoadedFromResources(@NonNull Context context, @NonNull VectorEnabledTintResources vectorEnabledTintResources, @DrawableRes int i) {
        Context context2 = context;
        VectorEnabledTintResources resources = vectorEnabledTintResources;
        int resId = i;
        Context context3 = context2;
        VectorEnabledTintResources vectorEnabledTintResources2 = resources;
        int i2 = resId;
        Drawable loadDrawableFromDelegates = loadDrawableFromDelegates(context2, resId);
        Drawable drawable = loadDrawableFromDelegates;
        if (loadDrawableFromDelegates == null) {
            drawable = resources.superGetDrawable(resId);
        }
        if (drawable == null) {
            return null;
        }
        return tintDrawable(context2, resId, false, drawable);
    }

    static boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes int i, @NonNull Drawable drawable) {
        Context context2 = context;
        int resId = i;
        Drawable drawable2 = drawable;
        Context context3 = context2;
        int i2 = resId;
        Drawable drawable3 = drawable2;
        Mode tintMode = DEFAULT_MODE;
        boolean colorAttrSet = false;
        int colorAttr = 0;
        int alpha = -1;
        if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, resId)) {
            colorAttr = C0268R.attr.colorControlNormal;
            colorAttrSet = true;
        } else if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, resId)) {
            colorAttr = C0268R.attr.colorControlActivated;
            colorAttrSet = true;
        } else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, resId)) {
            colorAttr = 16842801;
            colorAttrSet = true;
            tintMode = Mode.MULTIPLY;
        } else if (resId == C0268R.C0269drawable.abc_list_divider_mtrl_alpha) {
            colorAttr = 16842800;
            colorAttrSet = true;
            alpha = Math.round(40.8f);
        } else if (resId == C0268R.C0269drawable.abc_dialog_material_background) {
            colorAttr = 16842801;
            colorAttrSet = true;
        }
        if (!colorAttrSet) {
            return false;
        }
        if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            drawable3 = drawable2.mutate();
        }
        int themeAttrColor = ThemeUtils.getThemeAttrColor(context2, colorAttr);
        int i3 = themeAttrColor;
        drawable3.setColorFilter(getPorterDuffColorFilter(themeAttrColor, tintMode));
        if (alpha != -1) {
            drawable3.setAlpha(alpha);
        }
        return true;
    }

    private void addDelegate(@NonNull String str, @NonNull InflateDelegate inflateDelegate) {
        String tagName = str;
        InflateDelegate delegate = inflateDelegate;
        String str2 = tagName;
        InflateDelegate inflateDelegate2 = delegate;
        if (this.mDelegates == null) {
            this.mDelegates = new ArrayMap<>();
        }
        Object put = this.mDelegates.put(tagName, delegate);
    }

    private void removeDelegate(@NonNull String str, @NonNull InflateDelegate inflateDelegate) {
        String tagName = str;
        InflateDelegate delegate = inflateDelegate;
        String str2 = tagName;
        InflateDelegate inflateDelegate2 = delegate;
        if (this.mDelegates != null && this.mDelegates.get(tagName) == delegate) {
            Object remove = this.mDelegates.remove(tagName);
        }
    }

    private static boolean arrayContains(int[] iArr, int i) {
        int[] array = iArr;
        int value = i;
        int[] iArr2 = array;
        int i2 = value;
        for (int i3 : array) {
            int i4 = i3;
            if (i3 == value) {
                return true;
            }
        }
        return false;
    }

    static Mode getTintMode(int i) {
        int resId = i;
        int i2 = resId;
        Mode mode = null;
        if (resId == C0268R.C0269drawable.abc_switch_thumb_material) {
            mode = Mode.MULTIPLY;
        }
        return mode;
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList getTintList(@NonNull Context context, @DrawableRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        ColorStateList tintListFromCache = getTintListFromCache(context2, resId);
        ColorStateList tint = tintListFromCache;
        if (tintListFromCache == null) {
            if (resId == C0268R.C0269drawable.abc_edit_text_material) {
                tint = AppCompatResources.getColorStateList(context2, C0268R.color.abc_tint_edittext);
            } else if (resId == C0268R.C0269drawable.abc_switch_track_mtrl_alpha) {
                tint = AppCompatResources.getColorStateList(context2, C0268R.color.abc_tint_switch_track);
            } else if (resId == C0268R.C0269drawable.abc_switch_thumb_material) {
                tint = AppCompatResources.getColorStateList(context2, C0268R.color.abc_tint_switch_thumb);
            } else if (resId == C0268R.C0269drawable.abc_btn_default_mtrl_shape) {
                tint = createDefaultButtonColorStateList(context2);
            } else if (resId == C0268R.C0269drawable.abc_btn_borderless_material) {
                tint = createBorderlessButtonColorStateList(context2);
            } else if (resId == C0268R.C0269drawable.abc_btn_colored_material) {
                tint = createColoredButtonColorStateList(context2);
            } else if (resId == C0268R.C0269drawable.abc_spinner_mtrl_am_alpha || resId == C0268R.C0269drawable.abc_spinner_textfield_background_material) {
                tint = AppCompatResources.getColorStateList(context2, C0268R.color.abc_tint_spinner);
            } else if (arrayContains(TINT_COLOR_CONTROL_NORMAL, resId)) {
                tint = ThemeUtils.getThemeAttrColorStateList(context2, C0268R.attr.colorControlNormal);
            } else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, resId)) {
                tint = AppCompatResources.getColorStateList(context2, C0268R.color.abc_tint_default);
            } else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, resId)) {
                tint = AppCompatResources.getColorStateList(context2, C0268R.color.abc_tint_btn_checkable);
            } else if (resId == C0268R.C0269drawable.abc_seekbar_thumb_material) {
                tint = AppCompatResources.getColorStateList(context2, C0268R.color.abc_tint_seek_thumb);
            }
            if (tint != null) {
                addTintListToCache(context2, resId, tint);
            }
        }
        return tint;
    }

    private ColorStateList getTintListFromCache(@NonNull Context context, @DrawableRes int i) {
        Context context2 = context;
        int resId = i;
        Context context3 = context2;
        int i2 = resId;
        if (this.mTintLists == null) {
            return null;
        }
        SparseArray sparseArray = (SparseArray) this.mTintLists.get(context2);
        return sparseArray == null ? null : (ColorStateList) sparseArray.get(resId);
    }

    private void addTintListToCache(@NonNull Context context, @DrawableRes int i, @NonNull ColorStateList colorStateList) {
        Context context2 = context;
        int resId = i;
        ColorStateList tintList = colorStateList;
        Context context3 = context2;
        int i2 = resId;
        ColorStateList colorStateList2 = tintList;
        if (this.mTintLists == null) {
            this.mTintLists = new WeakHashMap<>();
        }
        SparseArray sparseArray = (SparseArray) this.mTintLists.get(context2);
        SparseArray sparseArray2 = sparseArray;
        if (sparseArray == null) {
            sparseArray2 = new SparseArray();
            Object put = this.mTintLists.put(context2, sparseArray2);
        }
        sparseArray2.append(resId, tintList);
    }

    private ColorStateList createDefaultButtonColorStateList(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        return createButtonColorStateList(context2, ThemeUtils.getThemeAttrColor(context2, C0268R.attr.colorButtonNormal));
    }

    private ColorStateList createBorderlessButtonColorStateList(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        return createButtonColorStateList(context2, 0);
    }

    private ColorStateList createColoredButtonColorStateList(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        return createButtonColorStateList(context2, ThemeUtils.getThemeAttrColor(context2, C0268R.attr.colorAccent));
    }

    private ColorStateList createButtonColorStateList(@NonNull Context context, @ColorInt int i) {
        Context context2 = context;
        int baseColor = i;
        Context context3 = context2;
        int i2 = baseColor;
        int[][] states = new int[4][];
        int[] colors = new int[4];
        int colorControlHighlight = ThemeUtils.getThemeAttrColor(context2, C0268R.attr.colorControlHighlight);
        int disabledThemeAttrColor = ThemeUtils.getDisabledThemeAttrColor(context2, C0268R.attr.colorButtonNormal);
        int i3 = disabledThemeAttrColor;
        states[0] = ThemeUtils.DISABLED_STATE_SET;
        colors[0] = disabledThemeAttrColor;
        states[1] = ThemeUtils.PRESSED_STATE_SET;
        colors[1] = ColorUtils.compositeColors(colorControlHighlight, baseColor);
        int i4 = 1 + 1;
        states[i4] = ThemeUtils.FOCUSED_STATE_SET;
        colors[i4] = ColorUtils.compositeColors(colorControlHighlight, baseColor);
        int i5 = i4 + 1;
        states[i5] = ThemeUtils.EMPTY_STATE_SET;
        colors[i5] = baseColor;
        int i6 = i5 + 1;
        return new ColorStateList(states, colors);
    }

    static void tintDrawable(Drawable drawable, TintInfo tintInfo, int[] iArr) {
        Mode mode;
        Drawable drawable2 = drawable;
        TintInfo tint = tintInfo;
        int[] state = iArr;
        Drawable drawable3 = drawable2;
        TintInfo tintInfo2 = tint;
        int[] iArr2 = state;
        if (DrawableUtils.canSafelyMutateDrawable(drawable2) && drawable2.mutate() != drawable2) {
            int d = Log.d(TAG, "Mutated drawable is not the same instance as the input.");
            return;
        }
        if (!tint.mHasTintList && !tint.mHasTintMode) {
            drawable2.clearColorFilter();
        } else {
            ColorStateList colorStateList = !tint.mHasTintList ? null : tint.mTintList;
            if (!tint.mHasTintMode) {
                mode = DEFAULT_MODE;
            } else {
                mode = tint.mTintMode;
            }
            drawable2.setColorFilter(createTintFilter(colorStateList, mode, state));
        }
        if (VERSION.SDK_INT <= 23) {
            drawable2.invalidateSelf();
        }
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList colorStateList, Mode mode, int[] iArr) {
        ColorStateList tint = colorStateList;
        Mode tintMode = mode;
        int[] state = iArr;
        ColorStateList colorStateList2 = tint;
        Mode mode2 = tintMode;
        int[] iArr2 = state;
        if (tint == null || tintMode == null) {
            return null;
        }
        int colorForState = tint.getColorForState(state, 0);
        int i = colorForState;
        return getPorterDuffColorFilter(colorForState, tintMode);
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int i, Mode mode) {
        int color = i;
        Mode mode2 = mode;
        int i2 = color;
        Mode mode3 = mode2;
        PorterDuffColorFilter porterDuffColorFilter = COLOR_FILTER_CACHE.get(color, mode2);
        PorterDuffColorFilter filter = porterDuffColorFilter;
        if (porterDuffColorFilter == null) {
            filter = new PorterDuffColorFilter(color, mode2);
            PorterDuffColorFilter put = COLOR_FILTER_CACHE.put(color, mode2, filter);
        }
        return filter;
    }

    private static void setPorterDuffColorFilter(Drawable drawable, int i, Mode mode) {
        Drawable d = drawable;
        int color = i;
        Mode mode2 = mode;
        Drawable d2 = d;
        int i2 = color;
        Mode mode3 = mode2;
        if (DrawableUtils.canSafelyMutateDrawable(d)) {
            d2 = d.mutate();
        }
        d2.setColorFilter(getPorterDuffColorFilter(color, mode2 != null ? mode2 : DEFAULT_MODE));
    }

    private void checkVectorDrawableSetup(@NonNull Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (!this.mHasCheckedVectorDrawableSetup) {
            this.mHasCheckedVectorDrawableSetup = true;
            Drawable drawable = getDrawable(context2, C0268R.C0269drawable.abc_vector_test);
            Drawable d = drawable;
            if (drawable == null || !isVectorDrawable(d)) {
                this.mHasCheckedVectorDrawableSetup = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    private static boolean isVectorDrawable(@NonNull Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        return (d instanceof VectorDrawableCompat) || PLATFORM_VD_CLAZZ.equals(d.getClass().getName());
    }
}
