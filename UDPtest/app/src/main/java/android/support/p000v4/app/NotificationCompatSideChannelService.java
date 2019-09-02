package android.support.p000v4.app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.p000v4.app.INotificationSideChannel.Stub;

/* renamed from: android.support.v4.app.NotificationCompatSideChannelService */
public abstract class NotificationCompatSideChannelService extends Service {

    /* renamed from: android.support.v4.app.NotificationCompatSideChannelService$NotificationSideChannelStub */
    private class NotificationSideChannelStub extends Stub {
        final /* synthetic */ NotificationCompatSideChannelService this$0;

        NotificationSideChannelStub(NotificationCompatSideChannelService notificationCompatSideChannelService) {
            this.this$0 = notificationCompatSideChannelService;
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
            this.this$0.checkPermission(getCallingUid(), packageName);
            long clearCallingIdentity = clearCallingIdentity();
            long j = clearCallingIdentity;
            try {
                this.this$0.notify(packageName, id, tag, notification2);
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancel(String str, int i, String str2) throws RemoteException {
            String packageName = str;
            int id = i;
            String tag = str2;
            String str3 = packageName;
            int i2 = id;
            String str4 = tag;
            this.this$0.checkPermission(getCallingUid(), packageName);
            long clearCallingIdentity = clearCallingIdentity();
            long j = clearCallingIdentity;
            try {
                this.this$0.cancel(packageName, id, tag);
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelAll(String str) {
            String packageName = str;
            String str2 = packageName;
            this.this$0.checkPermission(getCallingUid(), packageName);
            long clearCallingIdentity = clearCallingIdentity();
            long j = clearCallingIdentity;
            try {
                this.this$0.cancelAll(packageName);
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public abstract void cancel(String str, int i, String str2);

    public abstract void cancelAll(String str);

    public abstract void notify(String str, int i, String str2, Notification notification);

    public NotificationCompatSideChannelService() {
    }

    public IBinder onBind(Intent intent) {
        Intent intent2 = intent;
        Intent intent3 = intent2;
        if (!intent2.getAction().equals(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL)) {
            return null;
        }
        if (VERSION.SDK_INT <= 19) {
            return new NotificationSideChannelStub(this);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void checkPermission(int i, String str) {
        int callingUid = i;
        String packageName = str;
        int i2 = callingUid;
        String str2 = packageName;
        String[] packagesForUid = getPackageManager().getPackagesForUid(callingUid);
        String[] strArr = packagesForUid;
        int length = packagesForUid.length;
        int i3 = 0;
        while (i3 < length) {
            String str3 = strArr[i3];
            String str4 = str3;
            if (!str3.equals(packageName)) {
                i3++;
            } else {
                return;
            }
        }
        throw new SecurityException("NotificationSideChannelService: Uid " + callingUid + " is not authorized for package " + packageName);
    }
}
