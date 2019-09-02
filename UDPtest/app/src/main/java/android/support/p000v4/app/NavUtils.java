package android.support.p000v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.p000v4.content.IntentCompat;
import android.util.Log;

/* renamed from: android.support.v4.app.NavUtils */
public final class NavUtils {
    private static final NavUtilsImpl IMPL;
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    private static final String TAG = "NavUtils";

    /* renamed from: android.support.v4.app.NavUtils$NavUtilsImpl */
    interface NavUtilsImpl {
        Intent getParentActivityIntent(Activity activity);

        String getParentActivityName(Context context, ActivityInfo activityInfo);

        void navigateUpTo(Activity activity, Intent intent);

        boolean shouldUpRecreateTask(Activity activity, Intent intent);
    }

    /* renamed from: android.support.v4.app.NavUtils$NavUtilsImplBase */
    static class NavUtilsImplBase implements NavUtilsImpl {
        NavUtilsImplBase() {
        }

        public Intent getParentActivityIntent(Activity activity) {
            Intent makeMainActivity;
            Activity activity2 = activity;
            Activity activity3 = activity2;
            String parentActivityName = NavUtils.getParentActivityName(activity2);
            String parentName = parentActivityName;
            if (parentActivityName == null) {
                return null;
            }
            ComponentName target = new ComponentName(activity2, parentName);
            try {
                String parentActivityName2 = NavUtils.getParentActivityName(activity2, target);
                String str = parentActivityName2;
                if (parentActivityName2 != null) {
                    makeMainActivity = new Intent().setComponent(target);
                } else {
                    makeMainActivity = IntentCompat.makeMainActivity(target);
                }
                return makeMainActivity;
            } catch (NameNotFoundException e) {
                NameNotFoundException nameNotFoundException = e;
                int e2 = Log.e(NavUtils.TAG, "getParentActivityIntent: bad parentActivityName '" + parentName + "' in manifest");
                return null;
            }
        }

        public boolean shouldUpRecreateTask(Activity activity, Intent intent) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            Intent intent2 = intent;
            String action = activity2.getIntent().getAction();
            return action != null && !action.equals("android.intent.action.MAIN");
        }

        public void navigateUpTo(Activity activity, Intent intent) {
            Activity activity2 = activity;
            Intent upIntent = intent;
            Activity activity3 = activity2;
            Intent intent2 = upIntent;
            Intent addFlags = upIntent.addFlags(67108864);
            activity2.startActivity(upIntent);
            activity2.finish();
        }

        public String getParentActivityName(Context context, ActivityInfo activityInfo) {
            Context context2 = context;
            ActivityInfo info = activityInfo;
            Context context3 = context2;
            ActivityInfo activityInfo2 = info;
            if (info.metaData == null) {
                return null;
            }
            String string = info.metaData.getString(NavUtils.PARENT_ACTIVITY);
            String parentActivity = string;
            if (string == null) {
                return null;
            }
            if (parentActivity.charAt(0) == '.') {
                parentActivity = context2.getPackageName() + parentActivity;
            }
            return parentActivity;
        }
    }

    /* renamed from: android.support.v4.app.NavUtils$NavUtilsImplJB */
    static class NavUtilsImplJB extends NavUtilsImplBase {
        NavUtilsImplJB() {
        }

        public Intent getParentActivityIntent(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            Intent parentActivityIntent = NavUtilsJB.getParentActivityIntent(activity2);
            Intent result = parentActivityIntent;
            if (parentActivityIntent == null) {
                result = superGetParentActivityIntent(activity2);
            }
            return result;
        }

        /* access modifiers changed from: 0000 */
        public Intent superGetParentActivityIntent(Activity activity) {
            Activity activity2 = activity;
            Activity activity3 = activity2;
            return super.getParentActivityIntent(activity2);
        }

        public boolean shouldUpRecreateTask(Activity activity, Intent intent) {
            Activity activity2 = activity;
            Intent targetIntent = intent;
            Activity activity3 = activity2;
            Intent intent2 = targetIntent;
            return NavUtilsJB.shouldUpRecreateTask(activity2, targetIntent);
        }

        public void navigateUpTo(Activity activity, Intent intent) {
            Activity activity2 = activity;
            Intent upIntent = intent;
            Activity activity3 = activity2;
            Intent intent2 = upIntent;
            NavUtilsJB.navigateUpTo(activity2, upIntent);
        }

        public String getParentActivityName(Context context, ActivityInfo activityInfo) {
            Context context2 = context;
            ActivityInfo info = activityInfo;
            Context context3 = context2;
            ActivityInfo activityInfo2 = info;
            String parentActivityName = NavUtilsJB.getParentActivityName(info);
            String result = parentActivityName;
            if (parentActivityName == null) {
                result = super.getParentActivityName(context2, info);
            }
            return result;
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 16) {
            IMPL = new NavUtilsImplBase();
        } else {
            IMPL = new NavUtilsImplJB();
        }
    }

    public static boolean shouldUpRecreateTask(Activity activity, Intent intent) {
        Activity sourceActivity = activity;
        Intent targetIntent = intent;
        Activity activity2 = sourceActivity;
        Intent intent2 = targetIntent;
        return IMPL.shouldUpRecreateTask(sourceActivity, targetIntent);
    }

    public static void navigateUpFromSameTask(Activity activity) {
        Activity sourceActivity = activity;
        Activity activity2 = sourceActivity;
        Intent parentActivityIntent = getParentActivityIntent(sourceActivity);
        Intent upIntent = parentActivityIntent;
        if (parentActivityIntent != null) {
            navigateUpTo(sourceActivity, upIntent);
            return;
        }
        throw new IllegalArgumentException("Activity " + sourceActivity.getClass().getSimpleName() + " does not have a parent activity name specified." + " (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> " + " element in your manifest?)");
    }

    public static void navigateUpTo(Activity activity, Intent intent) {
        Activity sourceActivity = activity;
        Intent upIntent = intent;
        Activity activity2 = sourceActivity;
        Intent intent2 = upIntent;
        IMPL.navigateUpTo(sourceActivity, upIntent);
    }

    public static Intent getParentActivityIntent(Activity activity) {
        Activity sourceActivity = activity;
        Activity activity2 = sourceActivity;
        return IMPL.getParentActivityIntent(sourceActivity);
    }

    public static Intent getParentActivityIntent(Context context, Class<?> cls) throws NameNotFoundException {
        Intent makeMainActivity;
        Context context2 = context;
        Class<?> sourceActivityClass = cls;
        Context context3 = context2;
        Class<?> cls2 = sourceActivityClass;
        String parentActivityName = getParentActivityName(context2, new ComponentName(context2, sourceActivityClass));
        String parentActivity = parentActivityName;
        if (parentActivityName == null) {
            return null;
        }
        ComponentName target = new ComponentName(context2, parentActivity);
        String parentActivityName2 = getParentActivityName(context2, target);
        String str = parentActivityName2;
        if (parentActivityName2 != null) {
            makeMainActivity = new Intent().setComponent(target);
        } else {
            makeMainActivity = IntentCompat.makeMainActivity(target);
        }
        return makeMainActivity;
    }

    public static Intent getParentActivityIntent(Context context, ComponentName componentName) throws NameNotFoundException {
        Intent makeMainActivity;
        Context context2 = context;
        ComponentName componentName2 = componentName;
        Context context3 = context2;
        ComponentName componentName3 = componentName2;
        String parentActivityName = getParentActivityName(context2, componentName2);
        String parentActivity = parentActivityName;
        if (parentActivityName == null) {
            return null;
        }
        ComponentName target = new ComponentName(componentName2.getPackageName(), parentActivity);
        String parentActivityName2 = getParentActivityName(context2, target);
        String str = parentActivityName2;
        if (parentActivityName2 != null) {
            makeMainActivity = new Intent().setComponent(target);
        } else {
            makeMainActivity = IntentCompat.makeMainActivity(target);
        }
        return makeMainActivity;
    }

    @Nullable
    public static String getParentActivityName(Activity activity) {
        Activity sourceActivity = activity;
        Activity activity2 = sourceActivity;
        try {
            return getParentActivityName(sourceActivity, sourceActivity.getComponentName());
        } catch (NameNotFoundException e) {
            NameNotFoundException nameNotFoundException = e;
            throw new IllegalArgumentException(e);
        }
    }

    @Nullable
    public static String getParentActivityName(Context context, ComponentName componentName) throws NameNotFoundException {
        Context context2 = context;
        ComponentName componentName2 = componentName;
        Context context3 = context2;
        ComponentName componentName3 = componentName2;
        PackageManager packageManager = context2.getPackageManager();
        PackageManager packageManager2 = packageManager;
        String parentActivityName = IMPL.getParentActivityName(context2, packageManager.getActivityInfo(componentName2, 128));
        String str = parentActivityName;
        return parentActivityName;
    }

    private NavUtils() {
    }
}
