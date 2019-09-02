package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput.Factory;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.app.RemoteInputCompatJellybean */
class RemoteInputCompatJellybean {
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
    private static final String KEY_CHOICES = "choices";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_LABEL = "label";
    private static final String KEY_RESULT_KEY = "resultKey";
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";

    RemoteInputCompatJellybean() {
    }

    static RemoteInput fromBundle(Bundle bundle, Factory factory) {
        Bundle data = bundle;
        Factory factory2 = factory;
        Bundle bundle2 = data;
        Factory factory3 = factory2;
        return factory2.build(data.getString(KEY_RESULT_KEY), data.getCharSequence(KEY_LABEL), data.getCharSequenceArray(KEY_CHOICES), data.getBoolean(KEY_ALLOW_FREE_FORM_INPUT), data.getBundle(KEY_EXTRAS));
    }

    static Bundle toBundle(RemoteInput remoteInput) {
        RemoteInput remoteInput2 = remoteInput;
        RemoteInput remoteInput3 = remoteInput2;
        Bundle bundle = new Bundle();
        Bundle data = bundle;
        bundle.putString(KEY_RESULT_KEY, remoteInput2.getResultKey());
        data.putCharSequence(KEY_LABEL, remoteInput2.getLabel());
        data.putCharSequenceArray(KEY_CHOICES, remoteInput2.getChoices());
        data.putBoolean(KEY_ALLOW_FREE_FORM_INPUT, remoteInput2.getAllowFreeFormInput());
        data.putBundle(KEY_EXTRAS, remoteInput2.getExtras());
        return data;
    }

    static RemoteInput[] fromBundleArray(Bundle[] bundleArr, Factory factory) {
        Bundle[] bundles = bundleArr;
        Factory factory2 = factory;
        Bundle[] bundleArr2 = bundles;
        Factory factory3 = factory2;
        if (bundles == null) {
            return null;
        }
        RemoteInput[] remoteInputs = factory2.newArray(bundles.length);
        for (int i = 0; i < bundles.length; i++) {
            remoteInputs[i] = fromBundle(bundles[i], factory2);
        }
        return remoteInputs;
    }

    static Bundle[] toBundleArray(RemoteInput[] remoteInputArr) {
        RemoteInput[] remoteInputs = remoteInputArr;
        RemoteInput[] remoteInputArr2 = remoteInputs;
        if (remoteInputs == null) {
            return null;
        }
        Bundle[] bundles = new Bundle[remoteInputs.length];
        for (int i = 0; i < remoteInputs.length; i++) {
            bundles[i] = toBundle(remoteInputs[i]);
        }
        return bundles;
    }

    static Bundle getResultsFromIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        ClipData clipData = intent2.getClipData();
        ClipData clipData2 = clipData;
        if (clipData == null) {
            return null;
        }
        ClipDescription description = clipData2.getDescription();
        ClipDescription clipDescription = description;
        if (!description.hasMimeType("text/vnd.android.intent")) {
            return null;
        }
        if (!clipDescription.getLabel().equals("android.remoteinput.results")) {
            return null;
        }
        return (Bundle) clipData2.getItemAt(0).getIntent().getExtras().getParcelable("android.remoteinput.resultsData");
    }

    static void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
        RemoteInput[] remoteInputs = remoteInputArr;
        Intent intent2 = intent;
        Bundle results = bundle;
        RemoteInput[] remoteInputArr2 = remoteInputs;
        Intent intent3 = intent2;
        Bundle bundle2 = results;
        Bundle resultsBundle = new Bundle();
        for (RemoteInput remoteInput : remoteInputs) {
            Object obj = results.get(remoteInput.getResultKey());
            Object result = obj;
            if (obj instanceof CharSequence) {
                resultsBundle.putCharSequence(remoteInput.getResultKey(), (CharSequence) result);
            }
        }
        Intent intent4 = new Intent();
        Intent intent5 = intent4;
        Intent putExtra = intent4.putExtra("android.remoteinput.resultsData", resultsBundle);
        intent2.setClipData(ClipData.newIntent("android.remoteinput.results", intent5));
    }
}
