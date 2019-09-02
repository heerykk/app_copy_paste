package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.app.ShareCompatICS */
class ShareCompatICS {
    private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";

    ShareCompatICS() {
    }

    public static void configureMenuItem(MenuItem menuItem, Activity activity, Intent intent) {
        ShareActionProvider provider;
        MenuItem item = menuItem;
        Activity callingActivity = activity;
        Intent intent2 = intent;
        MenuItem menuItem2 = item;
        Activity activity2 = callingActivity;
        Intent intent3 = intent2;
        ActionProvider itemProvider = item.getActionProvider();
        if (itemProvider instanceof ShareActionProvider) {
            provider = (ShareActionProvider) itemProvider;
        } else {
            provider = new ShareActionProvider(callingActivity);
        }
        provider.setShareHistoryFileName(HISTORY_FILENAME_PREFIX + callingActivity.getClass().getName());
        provider.setShareIntent(intent2);
        MenuItem actionProvider = item.setActionProvider(provider);
    }
}
