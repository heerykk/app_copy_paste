package android.support.p000v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.RatingCompat;
import android.support.p000v4.media.session.MediaSessionCompat.QueueItem;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.media.session.IMediaSession */
public interface IMediaSession extends IInterface {

    /* renamed from: android.support.v4.media.session.IMediaSession$Stub */
    public static abstract class Stub extends Binder implements IMediaSession {
        private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaSession";
        static final int TRANSACTION_adjustVolume = 11;
        static final int TRANSACTION_fastForward = 22;
        static final int TRANSACTION_getExtras = 31;
        static final int TRANSACTION_getFlags = 9;
        static final int TRANSACTION_getLaunchPendingIntent = 8;
        static final int TRANSACTION_getMetadata = 27;
        static final int TRANSACTION_getPackageName = 6;
        static final int TRANSACTION_getPlaybackState = 28;
        static final int TRANSACTION_getQueue = 29;
        static final int TRANSACTION_getQueueTitle = 30;
        static final int TRANSACTION_getRatingType = 32;
        static final int TRANSACTION_getTag = 7;
        static final int TRANSACTION_getVolumeAttributes = 10;
        static final int TRANSACTION_isTransportControlEnabled = 5;
        static final int TRANSACTION_next = 20;
        static final int TRANSACTION_pause = 18;
        static final int TRANSACTION_play = 13;
        static final int TRANSACTION_playFromMediaId = 14;
        static final int TRANSACTION_playFromSearch = 15;
        static final int TRANSACTION_playFromUri = 16;
        static final int TRANSACTION_prepare = 33;
        static final int TRANSACTION_prepareFromMediaId = 34;
        static final int TRANSACTION_prepareFromSearch = 35;
        static final int TRANSACTION_prepareFromUri = 36;
        static final int TRANSACTION_previous = 21;
        static final int TRANSACTION_rate = 25;
        static final int TRANSACTION_registerCallbackListener = 3;
        static final int TRANSACTION_rewind = 23;
        static final int TRANSACTION_seekTo = 24;
        static final int TRANSACTION_sendCommand = 1;
        static final int TRANSACTION_sendCustomAction = 26;
        static final int TRANSACTION_sendMediaButton = 2;
        static final int TRANSACTION_setVolumeTo = 12;
        static final int TRANSACTION_skipToQueueItem = 17;
        static final int TRANSACTION_stop = 19;
        static final int TRANSACTION_unregisterCallbackListener = 4;

        /* renamed from: android.support.v4.media.session.IMediaSession$Stub$Proxy */
        private static class Proxy implements IMediaSession {
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

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) throws RemoteException {
                String command = str;
                Bundle args = bundle;
                ResultReceiverWrapper cb = resultReceiverWrapper;
                String str2 = command;
                Bundle bundle2 = args;
                ResultReceiverWrapper resultReceiverWrapper2 = cb;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(command);
                    if (args == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    }
                    if (cb == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        cb.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /* JADX INFO: finally extract failed */
            public boolean sendMediaButton(KeyEvent keyEvent) throws RemoteException {
                KeyEvent mediaButton = keyEvent;
                KeyEvent keyEvent2 = mediaButton;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (mediaButton == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        mediaButton.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = 0 != _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException {
                IMediaControllerCallback cb = iMediaControllerCallback;
                IMediaControllerCallback iMediaControllerCallback2 = cb;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb == null ? null : cb.asBinder());
                    boolean transact = this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException {
                IMediaControllerCallback cb = iMediaControllerCallback;
                IMediaControllerCallback iMediaControllerCallback2 = cb;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb == null ? null : cb.asBinder());
                    boolean transact = this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /* JADX INFO: finally extract failed */
            public boolean isTransportControlEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = 0 != _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            /* JADX INFO: finally extract failed */
            public String getPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            /* JADX INFO: finally extract failed */
            public String getTag() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            /* JADX INFO: finally extract failed */
            public PendingIntent getLaunchPendingIntent() throws RemoteException {
                PendingIntent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    if (0 == _reply.readInt()) {
                        _result = null;
                    } else {
                        _result = (PendingIntent) PendingIntent.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public long getFlags() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    long readLong = _reply.readLong();
                    long j = readLong;
                    return readLong;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /* JADX INFO: finally extract failed */
            public ParcelableVolumeInfo getVolumeAttributes() throws RemoteException {
                ParcelableVolumeInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    if (0 == _reply.readInt()) {
                        _result = null;
                    } else {
                        _result = (ParcelableVolumeInfo) ParcelableVolumeInfo.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void adjustVolume(int i, int i2, String str) throws RemoteException {
                int direction = i;
                int flags = i2;
                String packageName = str;
                int i3 = direction;
                int i4 = flags;
                String str2 = packageName;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(packageName);
                    boolean transact = this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVolumeTo(int i, int i2, String str) throws RemoteException {
                int value = i;
                int flags = i2;
                String packageName = str;
                int i3 = value;
                int i4 = flags;
                String str2 = packageName;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    _data.writeInt(flags);
                    _data.writeString(packageName);
                    boolean transact = this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /* JADX INFO: finally extract failed */
            public MediaMetadataCompat getMetadata() throws RemoteException {
                MediaMetadataCompat _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    if (0 == _reply.readInt()) {
                        _result = null;
                    } else {
                        _result = (MediaMetadataCompat) MediaMetadataCompat.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            /* JADX INFO: finally extract failed */
            public PlaybackStateCompat getPlaybackState() throws RemoteException {
                PlaybackStateCompat _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    if (0 == _reply.readInt()) {
                        _result = null;
                    } else {
                        _result = (PlaybackStateCompat) PlaybackStateCompat.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            /* JADX INFO: finally extract failed */
            public List<QueueItem> getQueue() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    ArrayList createTypedArrayList = _reply.createTypedArrayList(QueueItem.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return createTypedArrayList;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            /* JADX INFO: finally extract failed */
            public CharSequence getQueueTitle() throws RemoteException {
                CharSequence _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    if (0 == _reply.readInt()) {
                        _result = null;
                    } else {
                        _result = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            /* JADX INFO: finally extract failed */
            public Bundle getExtras() throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    if (0 == _reply.readInt()) {
                        _result = null;
                    } else {
                        _result = (Bundle) Bundle.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            /* JADX INFO: finally extract failed */
            public int getRatingType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    int readInt = _reply.readInt();
                    int i = readInt;
                    _reply.recycle();
                    _data.recycle();
                    return readInt;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void prepare() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareFromMediaId(String str, Bundle bundle) throws RemoteException {
                String uri = str;
                Bundle extras = bundle;
                String str2 = uri;
                Bundle bundle2 = extras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    if (extras == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareFromSearch(String str, Bundle bundle) throws RemoteException {
                String string = str;
                Bundle extras = bundle;
                String str2 = string;
                Bundle bundle2 = extras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(string);
                    if (extras == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                Uri uri2 = uri;
                Bundle extras = bundle;
                Uri uri3 = uri2;
                Bundle bundle2 = extras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri2 == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        uri2.writeToParcel(_data, 0);
                    }
                    if (extras == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void play() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                String uri = str;
                Bundle extras = bundle;
                String str2 = uri;
                Bundle bundle2 = extras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uri);
                    if (extras == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                String string = str;
                Bundle extras = bundle;
                String str2 = string;
                Bundle bundle2 = extras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(string);
                    if (extras == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                Uri uri2 = uri;
                Bundle extras = bundle;
                Uri uri3 = uri2;
                Bundle bundle2 = extras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri2 == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        uri2.writeToParcel(_data, 0);
                    }
                    if (extras == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void skipToQueueItem(long j) throws RemoteException {
                long id = j;
                long j2 = id;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(id);
                    boolean transact = this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void pause() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stop() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void next() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void previous() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void fastForward() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void rewind() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean transact = this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void seekTo(long j) throws RemoteException {
                long pos = j;
                long j2 = pos;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(pos);
                    boolean transact = this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void rate(RatingCompat ratingCompat) throws RemoteException {
                RatingCompat rating = ratingCompat;
                RatingCompat ratingCompat2 = rating;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rating == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        rating.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                String action = str;
                Bundle args = bundle;
                String str2 = action;
                Bundle bundle2 = args;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(action);
                    if (args == null) {
                        _data.writeInt(0);
                    } else {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    }
                    boolean transact = this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaSession asInterface(IBinder iBinder) {
            IBinder obj = iBinder;
            IBinder iBinder2 = obj;
            if (obj == null) {
                return null;
            }
            IInterface queryLocalInterface = obj.queryLocalInterface(DESCRIPTOR);
            IInterface iin = queryLocalInterface;
            if (queryLocalInterface != null && (iin instanceof IMediaSession)) {
                return (IMediaSession) iin;
            }
            return new Proxy(obj);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Uri _arg0;
            Bundle _arg1;
            Bundle _arg12;
            Bundle _arg13;
            Bundle _arg14;
            RatingCompat _arg02;
            Uri _arg03;
            Bundle _arg15;
            Bundle _arg16;
            Bundle _arg17;
            KeyEvent _arg04;
            Bundle _arg18;
            ResultReceiverWrapper _arg2;
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
                    String _arg05 = data.readString();
                    if (0 == data.readInt()) {
                        _arg18 = null;
                    } else {
                        _arg18 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    if (0 == data.readInt()) {
                        _arg2 = null;
                    } else {
                        _arg2 = (ResultReceiverWrapper) ResultReceiverWrapper.CREATOR.createFromParcel(data);
                    }
                    sendCommand(_arg05, _arg18, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg04 = null;
                    } else {
                        _arg04 = (KeyEvent) KeyEvent.CREATOR.createFromParcel(data);
                    }
                    boolean sendMediaButton = sendMediaButton(_arg04);
                    boolean z = sendMediaButton;
                    reply.writeNoException();
                    reply.writeInt(!sendMediaButton ? 0 : 1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    registerCallbackListener(android.support.p000v4.media.session.IMediaControllerCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterCallbackListener(android.support.p000v4.media.session.IMediaControllerCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isTransportControlEnabled = isTransportControlEnabled();
                    boolean z2 = isTransportControlEnabled;
                    reply.writeNoException();
                    reply.writeInt(!isTransportControlEnabled ? 0 : 1);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    String _result = getPackageName();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    String _result2 = getTag();
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    PendingIntent _result3 = getLaunchPendingIntent();
                    reply.writeNoException();
                    if (_result3 == null) {
                        reply.writeInt(0);
                    } else {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    }
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    long flags2 = getFlags();
                    long j = flags2;
                    reply.writeNoException();
                    reply.writeLong(flags2);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    ParcelableVolumeInfo _result4 = getVolumeAttributes();
                    reply.writeNoException();
                    if (_result4 == null) {
                        reply.writeInt(0);
                    } else {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    }
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg06 = data.readInt();
                    int readInt = data.readInt();
                    int i5 = readInt;
                    adjustVolume(_arg06, readInt, data.readString());
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg07 = data.readInt();
                    int readInt2 = data.readInt();
                    int i6 = readInt2;
                    setVolumeTo(_arg07, readInt2, data.readString());
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    play();
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg08 = data.readString();
                    if (0 == data.readInt()) {
                        _arg17 = null;
                    } else {
                        _arg17 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    playFromMediaId(_arg08, _arg17);
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    if (0 == data.readInt()) {
                        _arg16 = null;
                    } else {
                        _arg16 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    playFromSearch(_arg09, _arg16);
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg03 = null;
                    } else {
                        _arg03 = (Uri) Uri.CREATOR.createFromParcel(data);
                    }
                    if (0 == data.readInt()) {
                        _arg15 = null;
                    } else {
                        _arg15 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    playFromUri(_arg03, _arg15);
                    reply.writeNoException();
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    long readLong = data.readLong();
                    long j2 = readLong;
                    skipToQueueItem(readLong);
                    reply.writeNoException();
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    pause();
                    reply.writeNoException();
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    stop();
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    next();
                    reply.writeNoException();
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    previous();
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    fastForward();
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    rewind();
                    reply.writeNoException();
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    long readLong2 = data.readLong();
                    long j3 = readLong2;
                    seekTo(readLong2);
                    reply.writeNoException();
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg02 = null;
                    } else {
                        _arg02 = (RatingCompat) RatingCompat.CREATOR.createFromParcel(data);
                    }
                    rate(_arg02);
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg010 = data.readString();
                    if (0 == data.readInt()) {
                        _arg14 = null;
                    } else {
                        _arg14 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    sendCustomAction(_arg010, _arg14);
                    reply.writeNoException();
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    MediaMetadataCompat _result5 = getMetadata();
                    reply.writeNoException();
                    if (_result5 == null) {
                        reply.writeInt(0);
                    } else {
                        reply.writeInt(1);
                        _result5.writeToParcel(reply, 1);
                    }
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    PlaybackStateCompat _result6 = getPlaybackState();
                    reply.writeNoException();
                    if (_result6 == null) {
                        reply.writeInt(0);
                    } else {
                        reply.writeInt(1);
                        _result6.writeToParcel(reply, 1);
                    }
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    List queue = getQueue();
                    reply.writeNoException();
                    reply.writeTypedList(queue);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    CharSequence _result7 = getQueueTitle();
                    reply.writeNoException();
                    if (_result7 == null) {
                        reply.writeInt(0);
                    } else {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result7, reply, 1);
                    }
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _result8 = getExtras();
                    reply.writeNoException();
                    if (_result8 == null) {
                        reply.writeInt(0);
                    } else {
                        reply.writeInt(1);
                        _result8.writeToParcel(reply, 1);
                    }
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    int ratingType = getRatingType();
                    int i7 = ratingType;
                    reply.writeNoException();
                    reply.writeInt(ratingType);
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    prepare();
                    reply.writeNoException();
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg011 = data.readString();
                    if (0 == data.readInt()) {
                        _arg13 = null;
                    } else {
                        _arg13 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    prepareFromMediaId(_arg011, _arg13);
                    reply.writeNoException();
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg012 = data.readString();
                    if (0 == data.readInt()) {
                        _arg12 = null;
                    } else {
                        _arg12 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    prepareFromSearch(_arg012, _arg12);
                    reply.writeNoException();
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    if (0 == data.readInt()) {
                        _arg0 = null;
                    } else {
                        _arg0 = (Uri) Uri.CREATOR.createFromParcel(data);
                    }
                    if (0 == data.readInt()) {
                        _arg1 = null;
                    } else {
                        _arg1 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    prepareFromUri(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void adjustVolume(int i, int i2, String str) throws RemoteException;

    void fastForward() throws RemoteException;

    Bundle getExtras() throws RemoteException;

    long getFlags() throws RemoteException;

    PendingIntent getLaunchPendingIntent() throws RemoteException;

    MediaMetadataCompat getMetadata() throws RemoteException;

    String getPackageName() throws RemoteException;

    PlaybackStateCompat getPlaybackState() throws RemoteException;

    List<QueueItem> getQueue() throws RemoteException;

    CharSequence getQueueTitle() throws RemoteException;

    int getRatingType() throws RemoteException;

    String getTag() throws RemoteException;

    ParcelableVolumeInfo getVolumeAttributes() throws RemoteException;

    boolean isTransportControlEnabled() throws RemoteException;

    void next() throws RemoteException;

    void pause() throws RemoteException;

    void play() throws RemoteException;

    void playFromMediaId(String str, Bundle bundle) throws RemoteException;

    void playFromSearch(String str, Bundle bundle) throws RemoteException;

    void playFromUri(Uri uri, Bundle bundle) throws RemoteException;

    void prepare() throws RemoteException;

    void prepareFromMediaId(String str, Bundle bundle) throws RemoteException;

    void prepareFromSearch(String str, Bundle bundle) throws RemoteException;

    void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException;

    void previous() throws RemoteException;

    void rate(RatingCompat ratingCompat) throws RemoteException;

    void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException;

    void rewind() throws RemoteException;

    void seekTo(long j) throws RemoteException;

    void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) throws RemoteException;

    void sendCustomAction(String str, Bundle bundle) throws RemoteException;

    boolean sendMediaButton(KeyEvent keyEvent) throws RemoteException;

    void setVolumeTo(int i, int i2, String str) throws RemoteException;

    void skipToQueueItem(long j) throws RemoteException;

    void stop() throws RemoteException;

    void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException;
}
