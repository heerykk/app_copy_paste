package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.RequiresApi;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.app.NavUtilsJB */
class NavUtilsJB {
    NavUtilsJB() {
    }

    public static Intent getParentActivityIntent(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        return activity2.getParentActivityIntent();
    }

    public static boolean shouldUpRecreateTask(Activity activity, Intent intent) {
        Activity activity2 = activity;
        Intent targetIntent = intent;
        Activity activity3 = activity2;
        Intent intent2 = targetIntent;
        return activity2.shouldUpRecreateTask(targetIntent);
    }

    public static void navigateUpTo(Activity activity, Intent intent) {
        Activity activity2 = activity;
        Intent upIntent = intent;
        Activity activity3 = activity2;
        Intent intent2 = upIntent;
        boolean navigateUpTo = activity2.navigateUpTo(upIntent);
    }

    public static String getParentActivityName(ActivityInfo activityInfo) {
        ActivityInfo info = activityInfo;
        ActivityInfo activityInfo2 = info;
        return info.parentActivityName;
    }
}
