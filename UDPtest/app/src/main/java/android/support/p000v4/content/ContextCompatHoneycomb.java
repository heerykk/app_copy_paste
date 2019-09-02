package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresApi;
import java.io.File;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.content.ContextCompatHoneycomb */
class ContextCompatHoneycomb {
    ContextCompatHoneycomb() {
    }

    static void startActivities(Context context, Intent[] intentArr) {
        Context context2 = context;
        Intent[] intents = intentArr;
        Context context3 = context2;
        Intent[] intentArr2 = intents;
        context2.startActivities(intents);
    }

    public static File getObbDir(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return context2.getObbDir();
    }
}
