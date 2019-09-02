package android.support.p000v4.app;

import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/* renamed from: android.support.v4.app.BaseFragmentActivityJB */
abstract class BaseFragmentActivityJB extends BaseFragmentActivityHoneycomb {
    boolean mStartedActivityFromFragment;

    BaseFragmentActivityJB() {
    }

    @RequiresApi(16)
    public void startActivityForResult(Intent intent, int i, @Nullable Bundle bundle) {
        Intent intent2 = intent;
        int requestCode = i;
        Bundle options = bundle;
        Intent intent3 = intent2;
        int i2 = requestCode;
        Bundle bundle2 = options;
        if (!this.mStartedActivityFromFragment && requestCode != -1) {
            checkForValidRequestCode(requestCode);
        }
        super.startActivityForResult(intent2, requestCode, options);
    }

    @RequiresApi(16)
    public void startIntentSenderForResult(IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, Bundle bundle) throws SendIntentException {
        IntentSender intent2 = intentSender;
        int requestCode = i;
        Intent fillInIntent = intent;
        int flagsMask = i2;
        int flagsValues = i3;
        int extraFlags = i4;
        Bundle options = bundle;
        IntentSender intentSender2 = intent2;
        int i5 = requestCode;
        Intent intent3 = fillInIntent;
        int i6 = flagsMask;
        int i7 = flagsValues;
        int i8 = extraFlags;
        Bundle bundle2 = options;
        if (!this.mStartedIntentSenderFromFragment && requestCode != -1) {
            checkForValidRequestCode(requestCode);
        }
        super.startIntentSenderForResult(intent2, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }
}
