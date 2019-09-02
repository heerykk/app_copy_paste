package android.support.p000v4.app;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: android.support.v4.app.INotificationSideChannel */
public interface INotificationSideChannel extends IInterface {

    /* renamed from: android.support.v4.app.INotificationSideChannel$Stub */
    public static abstract class Stub extends Binder implements INotificationSideChannel {
        private static final String DESCRIPTOR = "android.support.v4.app.INotificationSideChannel";
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_cancelAll = 3;
        static final int TRANSACTION_notify = 1;

        /* renamed from: android.support.v4.app.INotificationSideChannel$Stub$Proxy */
        private static class Proxy implements INotificationSideChannel {
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

            public void notify(String str, int i, String str2, Notification notification) throws RemoteException {
                String packageName = str;
                int id = i;
                String tag = str2;
                Notification notification2 = notification;
                String str3 = packageName;
                int i2 = id;
                String str4 = tag;
                Notification notification3 = notification2;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(id);
                    _data.writeString(tag);
                    if (notification2 == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        notification2.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void cancel(String str, int i, String str2) throws RemoteException {
                String packageName = str;
                int id = i;
                String tag = str2;
                String str3 = packageName;
                int i2 = id;
                String str4 = tag;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(id);
                    _data.writeString(tag);
                    boolean transact = this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void cancelAll(String str) throws RemoteException {
                String packageName = str;
                String str2 = packageName;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean transact = this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INotificationSideChannel asInterface(IBinder iBinder) {
            IBinder obj = iBinder;
            IBinder iBinder2 = obj;
            if (obj == null) {
                return null;
            }
            IInterface queryLocalInterface = obj.queryLocalInterface(DESCRIPTOR);
            IInterface iin = queryLocalInterface;
            if (queryLocalInterface != null && (iin instanceof INotificationSideChannel)) {
                return (INotificationSideChannel) iin;
            }
            return new Proxy(obj);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Notification _arg3;
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
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    if (0 == data.readInt()) {
                        _arg3 = null;
                    } else {
                        _arg3 = (Notification) Notification.CREATOR.createFromParcel(data);
                    }
                    notify(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    int readInt = data.readInt();
                    int i5 = readInt;
                    cancel(_arg02, readInt, data.readString());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    cancelAll(data.readString());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void cancel(String str, int i, String str2) throws RemoteException;

    void cancelAll(String str) throws RemoteException;

    void notify(String str, int i, String str2, Notification notification) throws RemoteException;
}
