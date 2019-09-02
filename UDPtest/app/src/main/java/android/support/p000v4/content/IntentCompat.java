package android.support.p000v4.content;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.content.IntentCompat */
public final class IntentCompat {
    public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
    public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
    public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
    public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
    public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
    public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
    public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
    private static final IntentCompatImpl IMPL;

    /* renamed from: android.support.v4.content.IntentCompat$IntentCompatImpl */
    interface IntentCompatImpl {
        Intent makeMainActivity(ComponentName componentName);

        Intent makeMainSelectorActivity(String str, String str2);

        Intent makeRestartActivityTask(ComponentName componentName);
    }

    /* renamed from: android.support.v4.content.IntentCompat$IntentCompatImplBase */
    static class IntentCompatImplBase implements IntentCompatImpl {
        IntentCompatImplBase() {
        }

        public Intent makeMainActivity(ComponentName componentName) {
            ComponentName componentName2 = componentName;
            ComponentName componentName3 = componentName2;
            Intent intent = new Intent("android.intent.action.MAIN");
            Intent intent2 = intent;
            Intent component = intent.setComponent(componentName2);
            Intent addCategory = intent2.addCategory("android.intent.category.LAUNCHER");
            return intent2;
        }

        public Intent makeMainSelectorActivity(String str, String str2) {
            String selectorAction = str;
            String selectorCategory = str2;
            String str3 = selectorAction;
            String str4 = selectorCategory;
            Intent intent = new Intent(selectorAction);
            Intent intent2 = intent;
            Intent addCategory = intent.addCategory(selectorCategory);
            return intent2;
        }

        public Intent makeRestartActivityTask(ComponentName componentName) {
            ComponentName mainActivity = componentName;
            ComponentName componentName2 = mainActivity;
            Intent makeMainActivity = makeMainActivity(mainActivity);
            Intent intent = makeMainActivity;
            Intent addFlags = makeMainActivity.addFlags(268468224);
            return intent;
        }
    }

    /* renamed from: android.support.v4.content.IntentCompat$IntentCompatImplHC */
    static class IntentCompatImplHC extends IntentCompatImplBase {
        IntentCompatImplHC() {
        }

        public Intent makeMainActivity(ComponentName componentName) {
            ComponentName componentName2 = componentName;
            ComponentName componentName3 = componentName2;
            return IntentCompatHoneycomb.makeMainActivity(componentName2);
        }

        public Intent makeRestartActivityTask(ComponentName componentName) {
            ComponentName componentName2 = componentName;
            ComponentName componentName3 = componentName2;
            return IntentCompatHoneycomb.makeRestartActivityTask(componentName2);
        }
    }

    /* renamed from: android.support.v4.content.IntentCompat$IntentCompatImplIcsMr1 */
    static class IntentCompatImplIcsMr1 extends IntentCompatImplHC {
        IntentCompatImplIcsMr1() {
        }

        public Intent makeMainSelectorActivity(String str, String str2) {
            String selectorAction = str;
            String selectorCategory = str2;
            String str3 = selectorAction;
            String str4 = selectorCategory;
            return IntentCompatIcsMr1.makeMainSelectorActivity(selectorAction, selectorCategory);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int version = i;
        if (i >= 15) {
            IMPL = new IntentCompatImplIcsMr1();
        } else if (version < 11) {
            IMPL = new IntentCompatImplBase();
        } else {
            IMPL = new IntentCompatImplHC();
        }
    }

    private IntentCompat() {
    }

    public static Intent makeMainActivity(ComponentName componentName) {
        ComponentName mainActivity = componentName;
        ComponentName componentName2 = mainActivity;
        return IMPL.makeMainActivity(mainActivity);
    }

    public static Intent makeMainSelectorActivity(String str, String str2) {
        String selectorAction = str;
        String selectorCategory = str2;
        String str3 = selectorAction;
        String str4 = selectorCategory;
        return IMPL.makeMainSelectorActivity(selectorAction, selectorCategory);
    }

    public static Intent makeRestartActivityTask(ComponentName componentName) {
        ComponentName mainActivity = componentName;
        ComponentName componentName2 = mainActivity;
        return IMPL.makeRestartActivityTask(mainActivity);
    }
}
