package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.annotation.RequiresApi;

@TargetApi(15)
@RequiresApi(15)
/* renamed from: android.support.v4.content.IntentCompatIcsMr1 */
class IntentCompatIcsMr1 {
    IntentCompatIcsMr1() {
    }

    public static Intent makeMainSelectorActivity(String str, String str2) {
        String selectorAction = str;
        String selectorCategory = str2;
        String str3 = selectorAction;
        String str4 = selectorCategory;
        return Intent.makeMainSelectorActivity(selectorAction, selectorCategory);
    }
}
