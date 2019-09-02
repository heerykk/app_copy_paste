package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.app.TaskStackBuilderJellybean */
class TaskStackBuilderJellybean {
    TaskStackBuilderJellybean() {
    }

    public static PendingIntent getActivitiesPendingIntent(Context context, int i, Intent[] intentArr, int i2, Bundle bundle) {
        Context context2 = context;
        int requestCode = i;
        Intent[] intents = intentArr;
        int flags = i2;
        Bundle options = bundle;
        Context context3 = context2;
        int i3 = requestCode;
        Intent[] intentArr2 = intents;
        int i4 = flags;
        Bundle bundle2 = options;
        return PendingIntent.getActivities(context2, requestCode, intents, flags, options);
    }
}
