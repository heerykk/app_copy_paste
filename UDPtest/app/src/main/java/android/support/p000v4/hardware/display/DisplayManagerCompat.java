package android.support.p000v4.hardware.display;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

/* renamed from: android.support.v4.hardware.display.DisplayManagerCompat */
public abstract class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap<>();

    /* renamed from: android.support.v4.hardware.display.DisplayManagerCompat$JellybeanMr1Impl */
    private static class JellybeanMr1Impl extends DisplayManagerCompat {
        private final Object mDisplayManagerObj;

        public JellybeanMr1Impl(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.mDisplayManagerObj = DisplayManagerJellybeanMr1.getDisplayManager(context2);
        }

        public Display getDisplay(int i) {
            int displayId = i;
            int i2 = displayId;
            return DisplayManagerJellybeanMr1.getDisplay(this.mDisplayManagerObj, displayId);
        }

        public Display[] getDisplays() {
            return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj);
        }

        public Display[] getDisplays(String str) {
            String category = str;
            String str2 = category;
            return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj, category);
        }
    }

    /* renamed from: android.support.v4.hardware.display.DisplayManagerCompat$LegacyImpl */
    private static class LegacyImpl extends DisplayManagerCompat {
        private final WindowManager mWindowManager;

        public LegacyImpl(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.mWindowManager = (WindowManager) context2.getSystemService("window");
        }

        public Display getDisplay(int i) {
            int displayId = i;
            int i2 = displayId;
            Display defaultDisplay = this.mWindowManager.getDefaultDisplay();
            Display display = defaultDisplay;
            if (defaultDisplay.getDisplayId() != displayId) {
                return null;
            }
            return display;
        }

        public Display[] getDisplays() {
            Display[] displayArr = new Display[1];
            displayArr[0] = this.mWindowManager.getDefaultDisplay();
            return displayArr;
        }

        public Display[] getDisplays(String str) {
            String category = str;
            String str2 = category;
            return category != null ? new Display[0] : getDisplays();
        }
    }

    public abstract Display getDisplay(int i);

    public abstract Display[] getDisplays();

    public abstract Display[] getDisplays(String str);

    DisplayManagerCompat() {
    }

    public static DisplayManagerCompat getInstance(Context context) {
        Context context2 = context;
        Context context3 = context2;
        WeakHashMap<Context, DisplayManagerCompat> weakHashMap = sInstances;
        WeakHashMap<Context, DisplayManagerCompat> weakHashMap2 = weakHashMap;
        synchronized (weakHashMap) {
            try {
                DisplayManagerCompat displayManagerCompat = (DisplayManagerCompat) sInstances.get(context2);
                DisplayManagerCompat instance = displayManagerCompat;
                if (displayManagerCompat == null) {
                    int i = VERSION.SDK_INT;
                    int i2 = i;
                    if (i < 17) {
                        instance = new LegacyImpl(context2);
                    } else {
                        instance = new JellybeanMr1Impl(context2);
                    }
                    Object put = sInstances.put(context2, instance);
                }
                DisplayManagerCompat displayManagerCompat2 = instance;
                return displayManagerCompat2;
            } finally {
                WeakHashMap<Context, DisplayManagerCompat> weakHashMap3 = weakHashMap2;
            }
        }
    }
}
