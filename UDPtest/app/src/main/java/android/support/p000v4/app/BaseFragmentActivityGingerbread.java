package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.view.View;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v4.app.BaseFragmentActivityGingerbread */
abstract class BaseFragmentActivityGingerbread extends SupportActivity {
    boolean mStartedIntentSenderFromFragment;

    /* access modifiers changed from: 0000 */
    public abstract View dispatchFragmentsOnCreateView(View view, String str, Context context, AttributeSet attributeSet);

    BaseFragmentActivityGingerbread() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        Bundle bundle2 = savedInstanceState;
        if (VERSION.SDK_INT < 11 && getLayoutInflater().getFactory() == null) {
            getLayoutInflater().setFactory(this);
        }
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        String name = str;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        String str2 = name;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        View dispatchFragmentsOnCreateView = dispatchFragmentsOnCreateView(null, name, context2, attrs);
        View v = dispatchFragmentsOnCreateView;
        if (dispatchFragmentsOnCreateView != null) {
            return v;
        }
        return super.onCreateView(name, context2, attrs);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4) throws SendIntentException {
        IntentSender intent2 = intentSender;
        int requestCode = i;
        Intent fillInIntent = intent;
        int flagsMask = i2;
        int flagsValues = i3;
        int extraFlags = i4;
        IntentSender intentSender2 = intent2;
        int i5 = requestCode;
        Intent intent3 = fillInIntent;
        int i6 = flagsMask;
        int i7 = flagsValues;
        int i8 = extraFlags;
        if (!this.mStartedIntentSenderFromFragment && requestCode != -1) {
            checkForValidRequestCode(requestCode);
        }
        super.startIntentSenderForResult(intent2, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    static void checkForValidRequestCode(int i) {
        int requestCode = i;
        int i2 = requestCode;
        if ((requestCode & SupportMenu.CATEGORY_MASK) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }
}
