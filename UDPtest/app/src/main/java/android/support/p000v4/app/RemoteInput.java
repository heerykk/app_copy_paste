package android.support.p000v4.app;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput.Factory;
import android.util.Log;

/* renamed from: android.support.v4.app.RemoteInput */
public final class RemoteInput extends android.support.p000v4.app.RemoteInputCompatBase.RemoteInput {
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final Factory FACTORY = new Factory() {
        public RemoteInput build(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle) {
            String resultKey = str;
            CharSequence label = charSequence;
            CharSequence[] choices = charSequenceArr;
            Bundle extras = bundle;
            String str2 = resultKey;
            CharSequence charSequence2 = label;
            CharSequence[] charSequenceArr2 = choices;
            Bundle bundle2 = extras;
            return new RemoteInput(resultKey, label, choices, z, extras);
        }

        public RemoteInput[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new RemoteInput[size];
        }
    };
    private static final Impl IMPL;
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
    private static final String TAG = "RemoteInput";
    private final boolean mAllowFreeFormInput;
    private final CharSequence[] mChoices;
    private final Bundle mExtras;
    private final CharSequence mLabel;
    private final String mResultKey;

    /* renamed from: android.support.v4.app.RemoteInput$Builder */
    public static final class Builder {
        private boolean mAllowFreeFormInput = true;
        private CharSequence[] mChoices;
        private Bundle mExtras = new Bundle();
        private CharSequence mLabel;
        private final String mResultKey;

        public Builder(String str) {
            String resultKey = str;
            String str2 = resultKey;
            if (resultKey != null) {
                this.mResultKey = resultKey;
                return;
            }
            throw new IllegalArgumentException("Result key can't be null");
        }

        public Builder setLabel(CharSequence charSequence) {
            CharSequence label = charSequence;
            CharSequence charSequence2 = label;
            this.mLabel = label;
            return this;
        }

        public Builder setChoices(CharSequence[] charSequenceArr) {
            CharSequence[] choices = charSequenceArr;
            CharSequence[] charSequenceArr2 = choices;
            this.mChoices = choices;
            return this;
        }

        public Builder setAllowFreeFormInput(boolean z) {
            this.mAllowFreeFormInput = z;
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            if (extras != null) {
                this.mExtras.putAll(extras);
            }
            return this;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public RemoteInput build() {
            RemoteInput remoteInput = new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormInput, this.mExtras);
            return remoteInput;
        }
    }

    /* renamed from: android.support.v4.app.RemoteInput$Impl */
    interface Impl {
        void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle);

        Bundle getResultsFromIntent(Intent intent);
    }

    /* renamed from: android.support.v4.app.RemoteInput$ImplApi20 */
    static class ImplApi20 implements Impl {
        ImplApi20() {
        }

        public Bundle getResultsFromIntent(Intent intent) {
            Intent intent2 = intent;
            Intent intent3 = intent2;
            return RemoteInputCompatApi20.getResultsFromIntent(intent2);
        }

        public void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
            RemoteInput[] remoteInputs = remoteInputArr;
            Intent intent2 = intent;
            Bundle results = bundle;
            RemoteInput[] remoteInputArr2 = remoteInputs;
            Intent intent3 = intent2;
            Bundle bundle2 = results;
            RemoteInputCompatApi20.addResultsToIntent(remoteInputs, intent2, results);
        }
    }

    /* renamed from: android.support.v4.app.RemoteInput$ImplBase */
    static class ImplBase implements Impl {
        ImplBase() {
        }

        public Bundle getResultsFromIntent(Intent intent) {
            Intent intent2 = intent;
            int w = Log.w(RemoteInput.TAG, "RemoteInput is only supported from API Level 16");
            return null;
        }

        public void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
            RemoteInput[] remoteInputArr2 = remoteInputArr;
            Intent intent2 = intent;
            Bundle bundle2 = bundle;
            int w = Log.w(RemoteInput.TAG, "RemoteInput is only supported from API Level 16");
        }
    }

    /* renamed from: android.support.v4.app.RemoteInput$ImplJellybean */
    static class ImplJellybean implements Impl {
        ImplJellybean() {
        }

        public Bundle getResultsFromIntent(Intent intent) {
            Intent intent2 = intent;
            Intent intent3 = intent2;
            return RemoteInputCompatJellybean.getResultsFromIntent(intent2);
        }

        public void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
            RemoteInput[] remoteInputs = remoteInputArr;
            Intent intent2 = intent;
            Bundle results = bundle;
            RemoteInput[] remoteInputArr2 = remoteInputs;
            Intent intent3 = intent2;
            Bundle bundle2 = results;
            RemoteInputCompatJellybean.addResultsToIntent(remoteInputs, intent2, results);
        }
    }

    RemoteInput(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle) {
        String resultKey = str;
        CharSequence label = charSequence;
        CharSequence[] choices = charSequenceArr;
        Bundle extras = bundle;
        String str2 = resultKey;
        CharSequence charSequence2 = label;
        CharSequence[] charSequenceArr2 = choices;
        boolean allowFreeFormInput = z;
        Bundle bundle2 = extras;
        this.mResultKey = resultKey;
        this.mLabel = label;
        this.mChoices = choices;
        this.mAllowFreeFormInput = allowFreeFormInput;
        this.mExtras = extras;
    }

    public String getResultKey() {
        return this.mResultKey;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public CharSequence[] getChoices() {
        return this.mChoices;
    }

    public boolean getAllowFreeFormInput() {
        return this.mAllowFreeFormInput;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public static Bundle getResultsFromIntent(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        return IMPL.getResultsFromIntent(intent2);
    }

    public static void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
        RemoteInput[] remoteInputs = remoteInputArr;
        Intent intent2 = intent;
        Bundle results = bundle;
        RemoteInput[] remoteInputArr2 = remoteInputs;
        Intent intent3 = intent2;
        Bundle bundle2 = results;
        IMPL.addResultsToIntent(remoteInputs, intent2, results);
    }

    static {
        if (VERSION.SDK_INT >= 20) {
            IMPL = new ImplApi20();
        } else if (VERSION.SDK_INT < 16) {
            IMPL = new ImplBase();
        } else {
            IMPL = new ImplJellybean();
        }
    }
}
