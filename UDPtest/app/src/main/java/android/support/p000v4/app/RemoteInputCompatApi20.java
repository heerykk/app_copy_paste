package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.RemoteInput.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput.Factory;

@TargetApi(20)
@RequiresApi(20)
/* renamed from: android.support.v4.app.RemoteInputCompatApi20 */
class RemoteInputCompatApi20 {
    RemoteInputCompatApi20() {
    }

    static RemoteInput[] toCompat(android.app.RemoteInput[] remoteInputArr, Factory factory) {
        android.app.RemoteInput[] srcArray = remoteInputArr;
        Factory factory2 = factory;
        android.app.RemoteInput[] remoteInputArr2 = srcArray;
        Factory factory3 = factory2;
        if (srcArray == null) {
            return null;
        }
        RemoteInput[] result = factory2.newArray(srcArray.length);
        for (int i = 0; i < srcArray.length; i++) {
            android.app.RemoteInput src = srcArray[i];
            RemoteInput[] remoteInputArr3 = result;
            remoteInputArr3[i] = factory2.build(src.getResultKey(), src.getLabel(), src.getChoices(), src.getAllowFreeFormInput(), src.getExtras());
        }
        return result;
    }

    static android.app.RemoteInput[] fromCompat(RemoteInput[] remoteInputArr) {
        RemoteInput[] srcArray = remoteInputArr;
        RemoteInput[] remoteInputArr2 = srcArray;
        if (srcArray == null) {
            return null;
        }
        android.app.RemoteInput[] result = new android.app.RemoteInput[srcArray.length];
        for (int i = 0; i < srcArray.length; i++) {
            RemoteInput src = srcArray[i];
            result[i] = new Builder(src.getResultKey()).setLabel(src.getLabel()).setChoices(src.getChoices()).setAllowFreeFormInput(src.getAllowFreeFormInput()).addExtras(src.getExtras()).build();
        }
        return result;
    }

    static Bundle getResultsFromIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        return android.app.RemoteInput.getResultsFromIntent(intent2);
    }

    static void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
        RemoteInput[] remoteInputs = remoteInputArr;
        Intent intent2 = intent;
        Bundle results = bundle;
        RemoteInput[] remoteInputArr2 = remoteInputs;
        Intent intent3 = intent2;
        Bundle bundle2 = results;
        android.app.RemoteInput.addResultsToIntent(fromCompat(remoteInputs), intent2, results);
    }
}
