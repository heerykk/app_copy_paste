package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresApi;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.app.TaskStackBuilderHoneycomb */
class TaskStackBuilderHoneycomb {
    TaskStackBuilderHoneycomb() {
    }

    public static PendingIntent getActivitiesPendingIntent(Context context, int i, Intent[] intentArr, int i2) {
        Context context2 = context;
        int requestCode = i;
        Intent[] intents = intentArr;
        int flags = i2;
        Context context3 = context2;
        int i3 = requestCode;
        Intent[] intentArr2 = intents;
        int i4 = flags;
        return PendingIntent.getActivities(context2, requestCode, intents, flags);
    }
}
