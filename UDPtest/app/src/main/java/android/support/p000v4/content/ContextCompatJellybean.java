package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.content.ContextCompatJellybean */
class ContextCompatJellybean {
    ContextCompatJellybean() {
    }

    public static void startActivities(Context context, Intent[] intentArr, Bundle bundle) {
        Context context2 = context;
        Intent[] intents = intentArr;
        Bundle options = bundle;
        Context context3 = context2;
        Intent[] intentArr2 = intents;
        Bundle bundle2 = options;
        context2.startActivities(intents, options);
    }

    public static void startActivity(Context context, Intent intent, Bundle bundle) {
        Context context2 = context;
        Intent intent2 = intent;
        Bundle options = bundle;
        Context context3 = context2;
        Intent intent3 = intent2;
        Bundle bundle2 = options;
        context2.startActivity(intent2, options);
    }
}
