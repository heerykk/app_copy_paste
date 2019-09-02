package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v7.app.ActionBarDrawerToggleHoneycomb */
class ActionBarDrawerToggleHoneycomb {
    private static final String TAG = "ActionBarDrawerToggleHoneycomb";
    private static final int[] THEME_ATTRS;

    /* renamed from: android.support.v7.app.ActionBarDrawerToggleHoneycomb$SetIndicatorInfo */
    static class SetIndicatorInfo {
        public Method setHomeActionContentDescription;
        public Method setHomeAsUpIndicator;
        public ImageView upIndicatorView;

        SetIndicatorInfo(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            Class<ActionBar> cls = ActionBar.class;
            String str = "setHomeAsUpIndicator";
            try {
                Class[] clsArr = new Class[1];
                clsArr[0] = Drawable.class;
                this.setHomeAsUpIndicator = cls.getDeclaredMethod(str, clsArr);
                Class[] clsArr2 = new Class[1];
                clsArr2[0] = Integer.TYPE;
                this.setHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", clsArr2);
            } catch (NoSuchMethodException e) {
                View findViewById = activity2.findViewById(16908332);
                View view = findViewById;
                if (findViewById != null) {
                    ViewGroup viewGroup = (ViewGroup) view.getParent();
                    ViewGroup parent = viewGroup;
                    int childCount = viewGroup.getChildCount();
                    int i = childCount;
                    if (childCount == 2) {
                        View first = parent.getChildAt(0);
                        View up = first.getId() != 16908332 ? first : parent.getChildAt(1);
                        if (up instanceof ImageView) {
                            this.upIndicatorView = (ImageView) up;
                        }
                    }
                }
            }
        }
    }

    ActionBarDrawerToggleHoneycomb() {
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16843531;
        THEME_ATTRS = iArr;
    }

    public static SetIndicatorInfo setActionBarUpIndicator(SetIndicatorInfo setIndicatorInfo, Activity activity, Drawable drawable, int i) {
        Activity activity2 = activity;
        Drawable drawable2 = drawable;
        int contentDescRes = i;
        SetIndicatorInfo setIndicatorInfo2 = setIndicatorInfo;
        Activity activity3 = activity2;
        Drawable drawable3 = drawable2;
        int i2 = contentDescRes;
        SetIndicatorInfo setIndicatorInfo3 = new SetIndicatorInfo(activity2);
        SetIndicatorInfo info = setIndicatorInfo3;
        if (setIndicatorInfo3.setHomeAsUpIndicator != null) {
            try {
                ActionBar actionBar = activity2.getActionBar();
                Object invoke = info.setHomeAsUpIndicator.invoke(actionBar, new Object[]{drawable2});
                Method method = info.setHomeActionContentDescription;
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(contentDescRes);
                Object invoke2 = method.invoke(actionBar, objArr);
            } catch (Exception e) {
                Exception exc = e;
                int w = Log.w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", e);
            }
        } else if (info.upIndicatorView == null) {
            int w2 = Log.w(TAG, "Couldn't set home-as-up indicator");
        } else {
            info.upIndicatorView.setImageDrawable(drawable2);
        }
        return info;
    }

    public static SetIndicatorInfo setActionBarDescription(SetIndicatorInfo setIndicatorInfo, Activity activity, int i) {
        SetIndicatorInfo info = setIndicatorInfo;
        Activity activity2 = activity;
        int contentDescRes = i;
        SetIndicatorInfo info2 = info;
        Activity activity3 = activity2;
        int i2 = contentDescRes;
        if (info == null) {
            info2 = new SetIndicatorInfo(activity2);
        }
        if (info2.setHomeAsUpIndicator != null) {
            try {
                ActionBar actionBar = activity2.getActionBar();
                Method method = info2.setHomeActionContentDescription;
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(contentDescRes);
                Object invoke = method.invoke(actionBar, objArr);
                if (VERSION.SDK_INT <= 19) {
                    actionBar.setSubtitle(actionBar.getSubtitle());
                }
            } catch (Exception e) {
                Exception exc = e;
                int w = Log.w(TAG, "Couldn't set content description via JB-MR2 API", e);
            }
        }
        return info2;
    }

    public static Drawable getThemeUpIndicator(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        TypedArray obtainStyledAttributes = activity2.obtainStyledAttributes(THEME_ATTRS);
        TypedArray a = obtainStyledAttributes;
        Drawable result = obtainStyledAttributes.getDrawable(0);
        a.recycle();
        return result;
    }
}
