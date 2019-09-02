package android.support.p000v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.p000v4.content.IntentCompat;
import android.support.p000v4.content.SharedPreferencesCompat.EditorCompat;

/* renamed from: android.support.v4.app.AppLaunchChecker */
public class AppLaunchChecker {
    private static final String KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher";
    private static final String SHARED_PREFS_NAME = "android.support.AppLaunchChecker";

    public AppLaunchChecker() {
    }

    public static boolean hasStartedFromLauncher(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getSharedPreferences(SHARED_PREFS_NAME, 0).getBoolean(KEY_STARTED_FROM_LAUNCHER, false);
    }

    public static void onActivityCreate(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        SharedPreferences sharedPreferences = activity2.getSharedPreferences(SHARED_PREFS_NAME, 0);
        SharedPreferences sp = sharedPreferences;
        if (!sharedPreferences.getBoolean(KEY_STARTED_FROM_LAUNCHER, false)) {
            Intent intent = activity2.getIntent();
            Intent launchIntent = intent;
            if (intent != null && "android.intent.action.MAIN".equals(launchIntent.getAction()) && (launchIntent.hasCategory("android.intent.category.LAUNCHER") || launchIntent.hasCategory(IntentCompat.CATEGORY_LEANBACK_LAUNCHER))) {
                EditorCompat.getInstance().apply(sp.edit().putBoolean(KEY_STARTED_FROM_LAUNCHER, true));
            }
        }
    }
}
