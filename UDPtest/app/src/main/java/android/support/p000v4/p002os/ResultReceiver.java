package android.support.p000v4.p002os;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.p002os.IResultReceiver.Stub;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.os.ResultReceiver */
public class ResultReceiver implements Parcelable {
    public static final Creator<ResultReceiver> CREATOR = new Creator<ResultReceiver>() {
        public ResultReceiver createFromParcel(Parcel parcel) {
            Parcel in = parcel;
            Parcel parcel2 = in;
            return new ResultReceiver(in);
        }

        public ResultReceiver[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new ResultReceiver[size];
        }
    };
    final Handler mHandler;
    final boolean mLocal;
    IResultReceiver mReceiver;

    /* renamed from: android.support.v4.os.ResultReceiver$MyResultReceiver */
    class MyResultReceiver extends Stub {
        final /* synthetic */ ResultReceiver this$0;

        MyResultReceiver(ResultReceiver resultReceiver) {
            ResultReceiver this$02 = resultReceiver;
            ResultReceiver resultReceiver2 = this$02;
            this.this$0 = this$02;
        }

        public void send(int i, Bundle bundle) {
            int resultCode = i;
            Bundle resultData = bundle;
            int i2 = resultCode;
            Bundle bundle2 = resultData;
            if (this.this$0.mHandler == null) {
                this.this$0.onReceiveResult(resultCode, resultData);
            } else {
                boolean post = this.this$0.mHandler.post(new MyRunnable(this.this$0, resultCode, resultData));
            }
        }
    }

    /* renamed from: android.support.v4.os.ResultReceiver$MyRunnable */
    class MyRunnable implements Runnable {
        final int mResultCode;
        final Bundle mResultData;
        final /* synthetic */ ResultReceiver this$0;

        MyRunnable(ResultReceiver resultReceiver, int i, Bundle bundle) {
            ResultReceiver this$02 = resultReceiver;
            int resultCode = i;
            Bundle resultData = bundle;
            ResultReceiver resultReceiver2 = this$02;
            int i2 = resultCode;
            Bundle bundle2 = resultData;
            this.this$0 = this$02;
            this.mResultCode = resultCode;
            this.mResultData = resultData;
        }

        public void run() {
            this.this$0.onReceiveResult(this.mResultCode, this.mResultData);
        }
    }

    public ResultReceiver(Handler handler) {
        Handler handler2 = handler;
        Handler handler3 = handler2;
        this.mLocal = true;
        this.mHandler = handler2;
    }

    public void send(int i, Bundle bundle) {
        int resultCode = i;
        Bundle resultData = bundle;
        int i2 = resultCode;
        Bundle bundle2 = resultData;
        if (!this.mLocal) {
            if (this.mReceiver != null) {
                try {
                    this.mReceiver.send(resultCode, resultData);
                } catch (RemoteException e) {
                }
            }
            return;
        }
        if (this.mHandler == null) {
            onReceiveResult(resultCode, resultData);
        } else {
            boolean post = this.mHandler.post(new MyRunnable(this, resultCode, resultData));
        }
    }

    /* access modifiers changed from: protected */
    public void onReceiveResult(int i, Bundle bundle) {
        int i2 = i;
        Bundle bundle2 = bundle;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel out = parcel;
        Parcel parcel2 = out;
        int i2 = i;
        synchronized (this) {
            try {
                if (this.mReceiver == null) {
                    this.mReceiver = new MyResultReceiver(this);
                }
                out.writeStrongBinder(this.mReceiver.asBinder());
            } finally {
            }
        }
    }

    ResultReceiver(Parcel parcel) {
        Parcel in = parcel;
        Parcel parcel2 = in;
        this.mLocal = false;
        this.mHandler = null;
        this.mReceiver = Stub.asInterface(in.readStrongBinder());
    }
}
