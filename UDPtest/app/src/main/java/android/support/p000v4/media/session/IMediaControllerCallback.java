package android.support.p000v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.session.MediaSessionCompat.QueueItem;
import android.text.TextUtils;
import java.util.List;

/* renamed from: android.support.v4.media.session.IMediaControllerCallback */
public interface IMediaControllerCallback extends IInterface {

    /* renamed from: android.support.v4.media.session.IMediaControllerCallback$Stub */
    public static abstract class Stub extends Binder implements IMediaControllerCallback {
        private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaControllerCallback";
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onExtrasChanged = 7;
        static final int TRANSACTION_onMetadataChanged = 4;
        static final int TRANSACTION_onPlaybackStateChanged = 3;
        static final int TRANSACTION_onQueueChanged = 5;
        static final int TRANSACTION_onQueueTitleChanged = 6;
        static final int TRANSACTION_onSessionDestroyed = 2;
        static final int TRANSACTION_onVolumeInfoChanged = 8;

        /* renamed from: android.support.v4.media.session.IMediaControllerCallback$Stub$Proxy */
        private static class Proxy implements IMediaControllerCallback {
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

            public void onEvent(String str, Bundle bundle) throws RemoteException {
                String event = str;
                Bundle extras = bundle;
                String str2 = event;
                Bundle bundle2 = extras;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(event);
                    if (extras == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onSessionDestroyed() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                PlaybackStateCompat state = playbackStateCompat;
                PlaybackStateCompat playbackStateCompat2 = state;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (state == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                MediaMetadataCompat metadata = mediaMetadataCompat;
                MediaMetadataCompat mediaMetadataCompat2 = metadata;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (metadata == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        metadata.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onQueueChanged(List<QueueItem> list) throws RemoteException {
                List<QueueItem> queue = list;
                List<QueueItem> list2 = queue;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(queue);
                    boolean transact = this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                CharSequence title = charSequence;
                CharSequence charSequence2 = title;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (title == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(title, _data, 0);
                    }
                    boolean transact = this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Bundle extras = bundle;
                Bundle bundle2 = extras;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (extras == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                ParcelableVolumeInfo info = parcelableVolumeInfo;
                ParcelableVolumeInfo parcelableVolumeInfo2 = info;
                Parcel obtain = Parcel.obtain();
                Parcel _data = obtain;
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaControllerCallback asInterface(IBinder iBinder) {
            IBinder obj = iBinder;
            IBinder iBinder2 = obj;
            if (obj == null) {
                return null;
            }
            IInterface queryLocalInterface = obj.queryLocalInterface(DESCRIPTOR);
            IInterface iin = queryLocalInterface;
            if (queryLocalInterface != null && (iin instanceof IMediaControllerCallback)) {
                return (IMediaControllerCallback) iin;
            }
            return new Proxy(obj);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            ParcelableVolumeInfo _arg0;
            Bundle _arg02;
            CharSequence _arg03;
            MediaMetadataCompat _arg04;
            PlaybackStateCompat _arg05;
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
                    String _arg06 = data.readString();
                    if (0 == data.readInt()) {
                        _arg1 = null;
                    } else {
                        _arg1 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    onEvent(_arg06, _arg1);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onSessionDestroyed();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg05 = null;
                    } else {
                        _arg05 = (PlaybackStateCompat) PlaybackStateCompat.CREATOR.createFromParcel(data);
                    }
                    onPlaybackStateChanged(_arg05);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg04 = null;
                    } else {
                        _arg04 = (MediaMetadataCompat) MediaMetadataCompat.CREATOR.createFromParcel(data);
                    }
                    onMetadataChanged(_arg04);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    onQueueChanged(data.createTypedArrayList(QueueItem.CREATOR));
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg03 = null;
                    } else {
                        _arg03 = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
                    }
                    onQueueTitleChanged(_arg03);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg02 = null;
                    } else {
                        _arg02 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    onExtrasChanged(_arg02);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg0 = null;
                    } else {
                        _arg0 = (ParcelableVolumeInfo) ParcelableVolumeInfo.CREATOR.createFromParcel(data);
                    }
                    onVolumeInfoChanged(_arg0);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onEvent(String str, Bundle bundle) throws RemoteException;

    void onExtrasChanged(Bundle bundle) throws RemoteException;

    void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException;

    void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException;

    void onQueueChanged(List<QueueItem> list) throws RemoteException;

    void onQueueTitleChanged(CharSequence charSequence) throws RemoteException;

    void onSessionDestroyed() throws RemoteException;

    void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException;
}
