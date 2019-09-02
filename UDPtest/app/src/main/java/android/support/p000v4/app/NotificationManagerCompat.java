package android.support.p000v4.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.support.p000v4.app.INotificationSideChannel.Stub;
import android.support.p000v4.p002os.BuildCompat;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: android.support.v4.app.NotificationManagerCompat */
public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    private static final Impl IMPL;
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    static final int SIDE_CHANNEL_BIND_FLAGS = IMPL.getSideChannelBindFlags();
    private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
    private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
    private static final String TAG = "NotifManCompat";
    private static Set<String> sEnabledNotificationListenerPackages = new HashSet();
    private static String sEnabledNotificationListeners;
    private static final Object sEnabledNotificationListenersLock = new Object();
    private static final Object sLock = new Object();
    private static SideChannelManager sSideChannelManager;
    private final Context mContext;
    private final NotificationManager mNotificationManager = ((NotificationManager) this.mContext.getSystemService("notification"));

    /* renamed from: android.support.v4.app.NotificationManagerCompat$CancelTask */
    private static class CancelTask implements Task {
        final boolean all;

        /* renamed from: id */
        final int f12id;
        final String packageName;
        final String tag;

        public CancelTask(String str) {
            String packageName2 = str;
            String str2 = packageName2;
            this.packageName = packageName2;
            this.f12id = 0;
            this.tag = null;
            this.all = true;
        }

        public CancelTask(String str, int i, String str2) {
            String packageName2 = str;
            int id = i;
            String tag2 = str2;
            String str3 = packageName2;
            int i2 = id;
            String str4 = tag2;
            this.packageName = packageName2;
            this.f12id = id;
            this.tag = tag2;
            this.all = false;
        }

        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            INotificationSideChannel service = iNotificationSideChannel;
            INotificationSideChannel iNotificationSideChannel2 = service;
            if (!this.all) {
                service.cancel(this.packageName, this.f12id, this.tag);
                return;
            }
            service.cancelAll(this.packageName);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("CancelTask[");
            StringBuilder sb2 = sb;
            StringBuilder append = sb.append("packageName:").append(this.packageName);
            StringBuilder append2 = sb2.append(", id:").append(this.f12id);
            StringBuilder append3 = sb2.append(", tag:").append(this.tag);
            StringBuilder append4 = sb2.append(", all:").append(this.all);
            StringBuilder append5 = sb2.append("]");
            return sb2.toString();
        }
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$Impl */
    interface Impl {
        boolean areNotificationsEnabled(Context context, NotificationManager notificationManager);

        void cancelNotification(NotificationManager notificationManager, String str, int i);

        int getImportance(NotificationManager notificationManager);

        int getSideChannelBindFlags();

        void postNotification(NotificationManager notificationManager, String str, int i, Notification notification);
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$ImplApi24 */
    static class ImplApi24 extends ImplKitKat {
        ImplApi24() {
        }

        public boolean areNotificationsEnabled(Context context, NotificationManager notificationManager) {
            NotificationManager notificationManager2 = notificationManager;
            Context context2 = context;
            NotificationManager notificationManager3 = notificationManager2;
            return NotificationManagerCompatApi24.areNotificationsEnabled(notificationManager2);
        }

        public int getImportance(NotificationManager notificationManager) {
            NotificationManager notificationManager2 = notificationManager;
            NotificationManager notificationManager3 = notificationManager2;
            return NotificationManagerCompatApi24.getImportance(notificationManager2);
        }
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$ImplBase */
    static class ImplBase implements Impl {
        ImplBase() {
        }

        public void cancelNotification(NotificationManager notificationManager, String str, int i) {
            NotificationManager notificationManager2 = notificationManager;
            String tag = str;
            int id = i;
            NotificationManager notificationManager3 = notificationManager2;
            String str2 = tag;
            int i2 = id;
            notificationManager2.cancel(tag, id);
        }

        public void postNotification(NotificationManager notificationManager, String str, int i, Notification notification) {
            NotificationManager notificationManager2 = notificationManager;
            String tag = str;
            int id = i;
            Notification notification2 = notification;
            NotificationManager notificationManager3 = notificationManager2;
            String str2 = tag;
            int i2 = id;
            Notification notification3 = notification2;
            notificationManager2.notify(tag, id, notification2);
        }

        public int getSideChannelBindFlags() {
            return 1;
        }

        public boolean areNotificationsEnabled(Context context, NotificationManager notificationManager) {
            Context context2 = context;
            NotificationManager notificationManager2 = notificationManager;
            return true;
        }

        public int getImportance(NotificationManager notificationManager) {
            NotificationManager notificationManager2 = notificationManager;
            return NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$ImplIceCreamSandwich */
    static class ImplIceCreamSandwich extends ImplBase {
        ImplIceCreamSandwich() {
        }

        public int getSideChannelBindFlags() {
            return 33;
        }
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$ImplKitKat */
    static class ImplKitKat extends ImplIceCreamSandwich {
        ImplKitKat() {
        }

        public boolean areNotificationsEnabled(Context context, NotificationManager notificationManager) {
            Context context2 = context;
            Context context3 = context2;
            NotificationManager notificationManager2 = notificationManager;
            return NotificationManagerCompatKitKat.areNotificationsEnabled(context2);
        }
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$NotifyTask */
    private static class NotifyTask implements Task {

        /* renamed from: id */
        final int f13id;
        final Notification notif;
        final String packageName;
        final String tag;

        public NotifyTask(String str, int i, String str2, Notification notification) {
            String packageName2 = str;
            int id = i;
            String tag2 = str2;
            Notification notif2 = notification;
            String str3 = packageName2;
            int i2 = id;
            String str4 = tag2;
            Notification notification2 = notif2;
            this.packageName = packageName2;
            this.f13id = id;
            this.tag = tag2;
            this.notif = notif2;
        }

        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            INotificationSideChannel service = iNotificationSideChannel;
            INotificationSideChannel iNotificationSideChannel2 = service;
            service.notify(this.packageName, this.f13id, this.tag, this.notif);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("NotifyTask[");
            StringBuilder sb2 = sb;
            StringBuilder append = sb.append("packageName:").append(this.packageName);
            StringBuilder append2 = sb2.append(", id:").append(this.f13id);
            StringBuilder append3 = sb2.append(", tag:").append(this.tag);
            StringBuilder append4 = sb2.append("]");
            return sb2.toString();
        }
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$ServiceConnectedEvent */
    private static class ServiceConnectedEvent {
        final ComponentName componentName;
        final IBinder iBinder;

        public ServiceConnectedEvent(ComponentName componentName2, IBinder iBinder2) {
            ComponentName componentName3 = componentName2;
            IBinder iBinder3 = iBinder2;
            ComponentName componentName4 = componentName3;
            IBinder iBinder4 = iBinder3;
            this.componentName = componentName3;
            this.iBinder = iBinder3;
        }
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$SideChannelManager */
    private static class SideChannelManager implements Callback, ServiceConnection {
        private static final String KEY_BINDER = "binder";
        private static final int MSG_QUEUE_TASK = 0;
        private static final int MSG_RETRY_LISTENER_QUEUE = 3;
        private static final int MSG_SERVICE_CONNECTED = 1;
        private static final int MSG_SERVICE_DISCONNECTED = 2;
        private Set<String> mCachedEnabledPackages = new HashSet();
        private final Context mContext;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap();

        /* renamed from: android.support.v4.app.NotificationManagerCompat$SideChannelManager$ListenerRecord */
        private static class ListenerRecord {
            public boolean bound = false;
            public final ComponentName componentName;
            public int retryCount = 0;
            public INotificationSideChannel service;
            public LinkedList<Task> taskQueue = new LinkedList<>();

            public ListenerRecord(ComponentName componentName2) {
                ComponentName componentName3 = componentName2;
                ComponentName componentName4 = componentName3;
                this.componentName = componentName3;
            }
        }

        public SideChannelManager(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.mContext = context2;
            this.mHandlerThread = new HandlerThread("NotificationManagerCompat");
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper(), this);
        }

        public void queueTask(Task task) {
            Task task2 = task;
            Task task3 = task2;
            this.mHandler.obtainMessage(0, task2).sendToTarget();
        }

        public boolean handleMessage(Message message) {
            Message msg = message;
            Message message2 = msg;
            switch (msg.what) {
                case 0:
                    handleQueueTask((Task) msg.obj);
                    return true;
                case 1:
                    ServiceConnectedEvent event = (ServiceConnectedEvent) msg.obj;
                    handleServiceConnected(event.componentName, event.iBinder);
                    return true;
                case 2:
                    handleServiceDisconnected((ComponentName) msg.obj);
                    return true;
                case 3:
                    handleRetryListenerQueue((ComponentName) msg.obj);
                    return true;
                default:
                    return false;
            }
        }

        private void handleQueueTask(Task task) {
            Task task2 = task;
            Task task3 = task2;
            updateListenerMap();
            for (ListenerRecord listenerRecord : this.mRecordMap.values()) {
                ListenerRecord record = listenerRecord;
                boolean add = listenerRecord.taskQueue.add(task2);
                processListenerQueue(record);
            }
        }

        private void handleServiceConnected(ComponentName componentName, IBinder iBinder) {
            ComponentName componentName2 = componentName;
            IBinder iBinder2 = iBinder;
            ComponentName componentName3 = componentName2;
            IBinder iBinder3 = iBinder2;
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName2);
            ListenerRecord record = listenerRecord;
            if (listenerRecord != null) {
                record.service = Stub.asInterface(iBinder2);
                record.retryCount = 0;
                processListenerQueue(record);
            }
        }

        private void handleServiceDisconnected(ComponentName componentName) {
            ComponentName componentName2 = componentName;
            ComponentName componentName3 = componentName2;
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName2);
            ListenerRecord record = listenerRecord;
            if (listenerRecord != null) {
                ensureServiceUnbound(record);
            }
        }

        private void handleRetryListenerQueue(ComponentName componentName) {
            ComponentName componentName2 = componentName;
            ComponentName componentName3 = componentName2;
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName2);
            ListenerRecord record = listenerRecord;
            if (listenerRecord != null) {
                processListenerQueue(record);
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ComponentName componentName2 = componentName;
            IBinder iBinder2 = iBinder;
            ComponentName componentName3 = componentName2;
            IBinder iBinder3 = iBinder2;
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                int d = Log.d(NotificationManagerCompat.TAG, "Connected to service " + componentName2);
            }
            this.mHandler.obtainMessage(1, new ServiceConnectedEvent(componentName2, iBinder2)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            ComponentName componentName2 = componentName;
            ComponentName componentName3 = componentName2;
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                int d = Log.d(NotificationManagerCompat.TAG, "Disconnected from service " + componentName2);
            }
            this.mHandler.obtainMessage(2, componentName2).sendToTarget();
        }

        private void updateListenerMap() {
            Set enabledListenerPackages = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
            Set set = enabledListenerPackages;
            if (!enabledListenerPackages.equals(this.mCachedEnabledPackages)) {
                this.mCachedEnabledPackages = set;
                List<ResolveInfo> queryIntentServices = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 4);
                HashSet<ComponentName> hashSet = new HashSet<>();
                for (ResolveInfo resolveInfo : queryIntentServices) {
                    if (set.contains(resolveInfo.serviceInfo.packageName)) {
                        ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                        if (resolveInfo.serviceInfo.permission == null) {
                            boolean add = hashSet.add(componentName);
                        } else {
                            int w = Log.w(NotificationManagerCompat.TAG, "Permission present on component " + componentName + ", not adding listener record.");
                        }
                    }
                }
                for (ComponentName componentName2 : hashSet) {
                    if (!this.mRecordMap.containsKey(componentName2)) {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            int d = Log.d(NotificationManagerCompat.TAG, "Adding listener record for " + componentName2);
                        }
                        Object put = this.mRecordMap.put(componentName2, new ListenerRecord(componentName2));
                    }
                }
                Iterator it = this.mRecordMap.entrySet().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    if (!hashSet.contains(entry.getKey())) {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            int d2 = Log.d(NotificationManagerCompat.TAG, "Removing listener record for " + entry.getKey());
                        }
                        ensureServiceUnbound((ListenerRecord) entry.getValue());
                        it.remove();
                    }
                }
            }
        }

        private boolean ensureServiceBound(ListenerRecord listenerRecord) {
            ListenerRecord record = listenerRecord;
            ListenerRecord listenerRecord2 = record;
            if (record.bound) {
                return true;
            }
            record.bound = this.mContext.bindService(new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(record.componentName), this, NotificationManagerCompat.SIDE_CHANNEL_BIND_FLAGS);
            if (!record.bound) {
                int w = Log.w(NotificationManagerCompat.TAG, "Unable to bind to listener " + record.componentName);
                this.mContext.unbindService(this);
            } else {
                record.retryCount = 0;
            }
            return record.bound;
        }

        private void ensureServiceUnbound(ListenerRecord listenerRecord) {
            ListenerRecord record = listenerRecord;
            ListenerRecord listenerRecord2 = record;
            if (record.bound) {
                this.mContext.unbindService(this);
                record.bound = false;
            }
            record.service = null;
        }

        private void scheduleListenerRetry(ListenerRecord listenerRecord) {
            ListenerRecord record = listenerRecord;
            ListenerRecord listenerRecord2 = record;
            if (!this.mHandler.hasMessages(3, record.componentName)) {
                record.retryCount++;
                if (record.retryCount <= 6) {
                    int delayMs = 1000 * (1 << (record.retryCount - 1));
                    if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                        int d = Log.d(NotificationManagerCompat.TAG, "Scheduling retry for " + delayMs + " ms");
                    }
                    boolean sendMessageDelayed = this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, record.componentName), (long) delayMs);
                    return;
                }
                int w = Log.w(NotificationManagerCompat.TAG, "Giving up on delivering " + record.taskQueue.size() + " tasks to " + record.componentName + " after " + record.retryCount + " retries");
                record.taskQueue.clear();
            }
        }

        private void processListenerQueue(ListenerRecord listenerRecord) {
            ListenerRecord record = listenerRecord;
            ListenerRecord listenerRecord2 = record;
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                int d = Log.d(NotificationManagerCompat.TAG, "Processing component " + record.componentName + ", " + record.taskQueue.size() + " queued tasks");
            }
            if (record.taskQueue.isEmpty()) {
                return;
            }
            if (ensureServiceBound(record) && record.service != null) {
                while (true) {
                    Task task = (Task) record.taskQueue.peek();
                    Task task2 = task;
                    if (task == null) {
                        break;
                    }
                    try {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            int d2 = Log.d(NotificationManagerCompat.TAG, "Sending task " + task2);
                        }
                        task2.send(record.service);
                        Object remove = record.taskQueue.remove();
                    } catch (DeadObjectException e) {
                        DeadObjectException deadObjectException = e;
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            int d3 = Log.d(NotificationManagerCompat.TAG, "Remote service has died: " + record.componentName);
                        }
                    } catch (RemoteException e2) {
                        RemoteException remoteException = e2;
                        int w = Log.w(NotificationManagerCompat.TAG, "RemoteException communicating with " + record.componentName, e2);
                    }
                }
                if (!record.taskQueue.isEmpty()) {
                    scheduleListenerRetry(record);
                }
                return;
            }
            scheduleListenerRetry(record);
        }
    }

    /* renamed from: android.support.v4.app.NotificationManagerCompat$Task */
    private interface Task {
        void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException;
    }

    static {
        if (BuildCompat.isAtLeastN()) {
            IMPL = new ImplApi24();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new ImplKitKat();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new ImplBase();
        } else {
            IMPL = new ImplIceCreamSandwich();
        }
    }

    public static NotificationManagerCompat from(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return new NotificationManagerCompat(context2);
    }

    private NotificationManagerCompat(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.mContext = context2;
    }

    public void cancel(int i) {
        int id = i;
        int i2 = id;
        cancel(null, id);
    }

    public void cancel(String str, int i) {
        String tag = str;
        int id = i;
        String str2 = tag;
        int i2 = id;
        IMPL.cancelNotification(this.mNotificationManager, tag, id);
        if (VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), id, tag));
        }
    }

    public void cancelAll() {
        this.mNotificationManager.cancelAll();
        if (VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
        }
    }

    public void notify(int i, Notification notification) {
        int id = i;
        Notification notification2 = notification;
        int i2 = id;
        Notification notification3 = notification2;
        notify(null, id, notification2);
    }

    public void notify(String str, int i, Notification notification) {
        String tag = str;
        int id = i;
        Notification notification2 = notification;
        String str2 = tag;
        int i2 = id;
        Notification notification3 = notification2;
        if (!useSideChannelForNotification(notification2)) {
            IMPL.postNotification(this.mNotificationManager, tag, id, notification2);
            return;
        }
        NotifyTask notifyTask = new NotifyTask(this.mContext.getPackageName(), id, tag, notification2);
        pushSideChannelQueue(notifyTask);
        IMPL.cancelNotification(this.mNotificationManager, tag, id);
    }

    public boolean areNotificationsEnabled() {
        return IMPL.areNotificationsEnabled(this.mContext, this.mNotificationManager);
    }

    public int getImportance() {
        return IMPL.getImportance(this.mNotificationManager);
    }

    public static Set<String> getEnabledListenerPackages(Context context) {
        Context context2 = context;
        Context context3 = context2;
        String enabledNotificationListeners = Secure.getString(context2.getContentResolver(), SETTING_ENABLED_NOTIFICATION_LISTENERS);
        Object obj = sEnabledNotificationListenersLock;
        Set<String> set = obj;
        synchronized (obj) {
            if (enabledNotificationListeners != null) {
                if (!enabledNotificationListeners.equals(sEnabledNotificationListeners)) {
                    String[] components = enabledNotificationListeners.split(":");
                    HashSet hashSet = new HashSet(components.length);
                    int length = components.length;
                    for (int i = 0; i < length; i++) {
                        String str = components[i];
                        String str2 = str;
                        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                        ComponentName componentName = unflattenFromString;
                        if (unflattenFromString != null) {
                            boolean add = hashSet.add(componentName.getPackageName());
                        }
                    }
                    sEnabledNotificationListenerPackages = hashSet;
                    sEnabledNotificationListeners = enabledNotificationListeners;
                }
            }
            try {
                set = sEnabledNotificationListenerPackages;
                return set;
            } finally {
                Set<String> set2 = set;
            }
        }
    }

    private static boolean useSideChannelForNotification(Notification notification) {
        Notification notification2 = notification;
        Notification notification3 = notification2;
        Bundle extras = NotificationCompat.getExtras(notification2);
        return extras != null && extras.getBoolean(EXTRA_USE_SIDE_CHANNEL);
    }

    private void pushSideChannelQueue(Task task) {
        Task task2 = task;
        Task task3 = task2;
        Object obj = sLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (sSideChannelManager == null) {
                    sSideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
                }
                sSideChannelManager.queueTask(task2);
            } finally {
                Object obj3 = obj2;
            }
        }
    }
}
