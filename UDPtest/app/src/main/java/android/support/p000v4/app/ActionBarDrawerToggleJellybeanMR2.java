package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;

@TargetApi(18)
@RequiresApi(18)
/* renamed from: android.support.v4.app.ActionBarDrawerToggleJellybeanMR2 */
class ActionBarDrawerToggleJellybeanMR2 {
    private static final String TAG = "ActionBarDrawerToggleImplJellybeanMR2";
    private static final int[] THEME_ATTRS;

    ActionBarDrawerToggleJellybeanMR2() {
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16843531;
        THEME_ATTRS = iArr;
    }

    public static Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i) {
        Object info = obj;
        Activity activity2 = activity;
        Drawable drawable2 = drawable;
        int contentDescRes = i;
        Object obj2 = info;
        Activity activity3 = activity2;
        Drawable drawable3 = drawable2;
        int i2 = contentDescRes;
        ActionBar actionBar = activity2.getActionBar();
        ActionBar actionBar2 = actionBar;
        if (actionBar != null) {
            actionBar2.setHomeAsUpIndicator(drawable2);
            actionBar2.setHomeActionContentDescription(contentDescRes);
        }
        return info;
    }

    public static Object setActionBarDescription(Object obj, Activity activity, int i) {
        Object info = obj;
        Activity activity2 = activity;
        int contentDescRes = i;
        Object obj2 = info;
        Activity activity3 = activity2;
        int i2 = contentDescRes;
        ActionBar actionBar = activity2.getActionBar();
        ActionBar actionBar2 = actionBar;
        if (actionBar != null) {
            actionBar2.setHomeActionContentDescription(contentDescRes);
        }
        return info;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [android.app.Activity] */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [android.content.Context] */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r10v0, types: [android.content.Context] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.drawable.Drawable getThemeUpIndicator(android.app.Activity r17) {
        /*
            r0 = r17
            r1 = r0
            android.app.ActionBar r2 = r0.getActionBar()
            r3 = r2
            r4 = 0
            if (r2 != r4) goto L_0x0029
            r5 = r0
        L_0x000c:
            r2 = r5
            r6 = 0
            int[] r7 = THEME_ATTRS
            r8 = 16843470(0x10102ce, float:2.369557E-38)
            r9 = 0
            r10 = r2
            r4 = 0
            r11 = r4
            r12 = r7
            android.content.res.TypedArray r2 = r10.obtainStyledAttributes(r11, r12, r8, r9)
            r13 = r2
            r14 = 0
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r14)
            r15 = r2
            r13.recycle()
            r16 = r15
            return r16
        L_0x0029:
            android.content.Context r2 = r3.getThemedContext()
            r5 = r2
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.app.ActionBarDrawerToggleJellybeanMR2.getThemeUpIndicator(android.app.Activity):android.graphics.drawable.Drawable");
    }
}
