package android.support.p000v4.p002os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: android.support.v4.os.IResultReceiver */
public interface IResultReceiver extends IInterface {

    /* renamed from: android.support.v4.os.IResultReceiver$Stub */
    public static abstract class Stub extends Binder implements IResultReceiver {
        private static final String DESCRIPTOR = "android.support.v4.os.IResultReceiver";
        static final int TRANSACTION_send = 1;

        /* renamed from: android.support.v4.os.IResultReceiver$Stub$Proxy */
        private static class Proxy implements IResultReceiver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                IBinder remote = iBinder;
                IBinder iBinder2 = remote;
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void send(int i, Bundle bundle) throws RemoteException {
                int resultCode = i;
                Bundle resultData = bundle;
                int i2 = resultCode;
                Bundle bundle2 = resultData;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    if (resultData == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        resultData.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IResultReceiver asInterface(IBinder iBinder) {
            IBinder obj = iBinder;
            IBinder iBinder2 = obj;
            if (obj == null) {
                return null;
            }
            IInterface queryLocalInterface = obj.queryLocalInterface(DESCRIPTOR);
            IInterface iin = queryLocalInterface;
            if (queryLocalInterface != null && (iin instanceof IResultReceiver)) {
                return (IResultReceiver) iin;
            }
            return new Proxy(obj);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Bundle _arg1;
            int code = i;
            Parcel data = parcel;
            Parcel reply = parcel2;
            int flags = i2;
            int i3 = code;
            Parcel parcel3 = data;
            Parcel parcel4 = reply;
            int i4 = flags;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    if (0 == data.readInt()) {
                        _arg1 = null;
                    } else {
                        _arg1 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    send(_arg0, _arg1);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void send(int i, Bundle bundle) throws RemoteException;
}
