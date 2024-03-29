package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.RequiresApi;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.content.IntentCompatHoneycomb */
class IntentCompatHoneycomb {
    IntentCompatHoneycomb() {
    }

    public static Intent makeMainActivity(ComponentName componentName) {
        ComponentName mainActivity = componentName;
        ComponentName componentName2 = mainActivity;
        return Intent.makeMainActivity(mainActivity);
    }

    public static Intent makeRestartActivityTask(ComponentName componentName) {
        ComponentName mainActivity = componentName;
        ComponentName componentName2 = mainActivity;
        return Intent.makeRestartActivityTask(mainActivity);
    }
}
