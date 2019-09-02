package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.InboxStyle;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationCompatBase.Action;
import android.support.p000v4.app.NotificationCompatBase.Action.Factory;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.app.NotificationCompatJellybean */
class NotificationCompatJellybean {
    static final String EXTRA_ACTION_EXTRAS = "android.support.actionExtras";
    static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
    static final String EXTRA_GROUP_KEY = "android.support.groupKey";
    static final String EXTRA_GROUP_SUMMARY = "android.support.isGroupSummary";
    static final String EXTRA_LOCAL_ONLY = "android.support.localOnly";
    static final String EXTRA_REMOTE_INPUTS = "android.support.remoteInputs";
    static final String EXTRA_SORT_KEY = "android.support.sortKey";
    static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    private static final String KEY_ACTION_INTENT = "actionIntent";
    private static final String KEY_ALLOW_GENERATED_REPLIES = "allowGeneratedReplies";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_ICON = "icon";
    private static final String KEY_REMOTE_INPUTS = "remoteInputs";
    private static final String KEY_TITLE = "title";
    public static final String TAG = "NotificationCompat";
    private static Class<?> sActionClass;
    private static Field sActionIconField;
    private static Field sActionIntentField;
    private static Field sActionTitleField;
    private static boolean sActionsAccessFailed;
    private static Field sActionsField;
    private static final Object sActionsLock = new Object();
    private static Field sExtrasField;
    private static boolean sExtrasFieldAccessFailed;
    private static final Object sExtrasLock = new Object();

    /* renamed from: android.support.v4.app.NotificationCompatJellybean$Builder */
    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {

        /* renamed from: b */
        private android.app.Notification.Builder f10b;
        private List<Bundle> mActionExtrasList = new ArrayList();
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private final Bundle mExtras;

        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, int i4, CharSequence charSequence4, boolean z3, Bundle bundle, String str, boolean z4, String str2, RemoteViews remoteViews2, RemoteViews remoteViews3) {
            Context context2 = context;
            Notification n = notification;
            CharSequence contentTitle = charSequence;
            CharSequence contentText = charSequence2;
            CharSequence contentInfo = charSequence3;
            RemoteViews tickerView = remoteViews;
            int number = i;
            PendingIntent contentIntent = pendingIntent;
            PendingIntent fullScreenIntent = pendingIntent2;
            Bitmap largeIcon = bitmap;
            int progressMax = i2;
            int progress = i3;
            int priority = i4;
            CharSequence subText = charSequence4;
            Bundle extras = bundle;
            String groupKey = str;
            String sortKey = str2;
            RemoteViews contentView = remoteViews2;
            RemoteViews bigContentView = remoteViews3;
            Context context3 = context2;
            Notification notification2 = n;
            CharSequence charSequence5 = contentTitle;
            CharSequence charSequence6 = contentText;
            CharSequence charSequence7 = contentInfo;
            RemoteViews remoteViews4 = tickerView;
            int i5 = number;
            PendingIntent pendingIntent3 = contentIntent;
            PendingIntent pendingIntent4 = fullScreenIntent;
            Bitmap bitmap2 = largeIcon;
            int i6 = progressMax;
            int i7 = progress;
            boolean progressIndeterminate = z;
            boolean useChronometer = z2;
            int i8 = priority;
            CharSequence charSequence8 = subText;
            boolean localOnly = z3;
            Bundle bundle2 = extras;
            String str3 = groupKey;
            boolean groupSummary = z4;
            String str4 = sortKey;
            RemoteViews remoteViews5 = contentView;
            RemoteViews remoteViews6 = bigContentView;
            this.f10b = new android.app.Notification.Builder(context2).setWhen(n.when).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(progressMax, progress, progressIndeterminate);
            this.mExtras = new Bundle();
            if (extras != null) {
                this.mExtras.putAll(extras);
            }
            if (localOnly) {
                this.mExtras.putBoolean("android.support.localOnly", true);
            }
            if (groupKey != null) {
                this.mExtras.putString("android.support.groupKey", groupKey);
                if (!groupSummary) {
                    this.mExtras.putBoolean("android.support.useSideChannel", true);
                } else {
                    this.mExtras.putBoolean("android.support.isGroupSummary", true);
                }
            }
            if (sortKey != null) {
                this.mExtras.putString("android.support.sortKey", sortKey);
            }
            this.mContentView = contentView;
            this.mBigContentView = bigContentView;
        }

        public void addAction(Action action) {
            Action action2 = action;
            Action action3 = action2;
            boolean add = this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.f10b, action2));
        }

        public android.app.Notification.Builder getBuilder() {
            return this.f10b;
        }

        public Notification build() {
            Notification build = this.f10b.build();
            Notification notif = build;
            Bundle extras = NotificationCompatJellybean.getExtras(build);
            Bundle mergeBundle = new Bundle(this.mExtras);
            for (String key : this.mExtras.keySet()) {
                if (extras.containsKey(key)) {
                    mergeBundle.remove(key);
                }
            }
            extras.putAll(mergeBundle);
            SparseArray buildActionExtrasMap = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            SparseArray sparseArray = buildActionExtrasMap;
            if (buildActionExtrasMap != null) {
                NotificationCompatJellybean.getExtras(notif).putSparseParcelableArray("android.support.actionExtras", sparseArray);
            }
            if (this.mContentView != null) {
                notif.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notif.bigContentView = this.mBigContentView;
            }
            return notif;
        }
    }

    NotificationCompatJellybean() {
    }

    public static void addBigTextStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean z, CharSequence charSequence2, CharSequence charSequence3) {
        NotificationBuilderWithBuilderAccessor b = notificationBuilderWithBuilderAccessor;
        CharSequence bigContentTitle = charSequence;
        CharSequence summaryText = charSequence2;
        CharSequence bigText = charSequence3;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = b;
        CharSequence charSequence4 = bigContentTitle;
        boolean useSummary = z;
        CharSequence charSequence5 = summaryText;
        CharSequence charSequence6 = bigText;
        BigTextStyle bigText2 = new BigTextStyle(b.getBuilder()).setBigContentTitle(bigContentTitle).bigText(bigText);
        BigTextStyle bigTextStyle = bigText2;
        if (useSummary) {
            BigTextStyle summaryText2 = bigText2.setSummaryText(summaryText);
        }
    }

    public static void addBigPictureStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean z, CharSequence charSequence2, Bitmap bitmap, Bitmap bitmap2, boolean z2) {
        NotificationBuilderWithBuilderAccessor b = notificationBuilderWithBuilderAccessor;
        CharSequence bigContentTitle = charSequence;
        CharSequence summaryText = charSequence2;
        Bitmap bigPicture = bitmap;
        Bitmap bigLargeIcon = bitmap2;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = b;
        CharSequence charSequence3 = bigContentTitle;
        boolean useSummary = z;
        CharSequence charSequence4 = summaryText;
        Bitmap bitmap3 = bigPicture;
        Bitmap bitmap4 = bigLargeIcon;
        boolean bigLargeIconSet = z2;
        BigPictureStyle bigPictureStyle = new BigPictureStyle(b.getBuilder());
        BigPictureStyle bigPicture2 = bigPictureStyle.setBigContentTitle(bigContentTitle).bigPicture(bigPicture);
        BigPictureStyle style = bigPicture2;
        if (bigLargeIconSet) {
            BigPictureStyle bigLargeIcon2 = bigPicture2.bigLargeIcon(bigLargeIcon);
        }
        if (useSummary) {
            BigPictureStyle summaryText2 = style.setSummaryText(summaryText);
        }
    }

    public static void addInboxStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean z, CharSequence charSequence2, ArrayList<CharSequence> arrayList) {
        NotificationBuilderWithBuilderAccessor b = notificationBuilderWithBuilderAccessor;
        CharSequence bigContentTitle = charSequence;
        CharSequence summaryText = charSequence2;
        ArrayList<CharSequence> texts = arrayList;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = b;
        CharSequence charSequence3 = bigContentTitle;
        boolean useSummary = z;
        CharSequence charSequence4 = summaryText;
        ArrayList<CharSequence> arrayList2 = texts;
        InboxStyle bigContentTitle2 = new InboxStyle(b.getBuilder()).setBigContentTitle(bigContentTitle);
        InboxStyle style = bigContentTitle2;
        if (useSummary) {
            InboxStyle summaryText2 = bigContentTitle2.setSummaryText(summaryText);
        }
        Iterator it = texts.iterator();
        while (it.hasNext()) {
            InboxStyle addLine = style.addLine((CharSequence) it.next());
        }
    }

    public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> list) {
        List<Bundle> actionExtrasList = list;
        List<Bundle> list2 = actionExtrasList;
        SparseArray sparseArray = null;
        int count = actionExtrasList.size();
        for (int i = 0; i < count; i++) {
            Bundle bundle = (Bundle) actionExtrasList.get(i);
            Bundle actionExtras = bundle;
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                }
                sparseArray.put(i, actionExtras);
            }
        }
        return sparseArray;
    }

    public static Bundle getExtras(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        Object obj = sExtrasLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (!sExtrasFieldAccessFailed) {
                    if (sExtrasField == null) {
                        Field extrasField = Notification.class.getDeclaredField(KEY_EXTRAS);
                        if (Bundle.class.isAssignableFrom(extrasField.getType())) {
                            extrasField.setAccessible(true);
                            sExtrasField = extrasField;
                        } else {
                            int e = Log.e(TAG, "Notification.extras field is not of type Bundle");
                            sExtrasFieldAccessFailed = true;
                            return null;
                        }
                    }
                    Bundle bundle = (Bundle) sExtrasField.get(notif);
                    Bundle extras = bundle;
                    if (bundle == null) {
                        extras = new Bundle();
                        sExtrasField.set(notif, extras);
                    }
                    Bundle bundle2 = extras;
                    return bundle2;
                }
                return null;
            } catch (IllegalAccessException e2) {
                IllegalAccessException illegalAccessException = e2;
                int e3 = Log.e(TAG, "Unable to access notification extras", e2);
            } catch (NoSuchFieldException e4) {
                NoSuchFieldException noSuchFieldException = e4;
                int e5 = Log.e(TAG, "Unable to access notification extras", e4);
            } catch (Throwable th) {
                Object obj3 = obj2;
                throw th;
            }
        }
        sExtrasFieldAccessFailed = true;
        return null;
    }

    public static Action readAction(Factory factory, RemoteInput.Factory factory2, int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle) {
        Factory factory3 = factory;
        RemoteInput.Factory remoteInputFactory = factory2;
        int icon = i;
        CharSequence title = charSequence;
        PendingIntent actionIntent = pendingIntent;
        Bundle extras = bundle;
        Factory factory4 = factory3;
        RemoteInput.Factory factory5 = remoteInputFactory;
        int i2 = icon;
        CharSequence charSequence2 = title;
        PendingIntent pendingIntent2 = actionIntent;
        Bundle bundle2 = extras;
        RemoteInput[] remoteInputs = null;
        boolean allowGeneratedReplies = false;
        if (extras != null) {
            remoteInputs = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(extras, "android.support.remoteInputs"), remoteInputFactory);
            allowGeneratedReplies = extras.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES);
        }
        return factory3.build(icon, title, actionIntent, extras, remoteInputs, allowGeneratedReplies);
    }

    public static Bundle writeActionAndGetExtras(android.app.Notification.Builder builder, Action action) {
        android.app.Notification.Builder builder2 = builder;
        Action action2 = action;
        android.app.Notification.Builder builder3 = builder2;
        Action action3 = action2;
        android.app.Notification.Builder addAction = builder2.addAction(action2.getIcon(), action2.getTitle(), action2.getActionIntent());
        Bundle actionExtras = new Bundle(action2.getExtras());
        if (action2.getRemoteInputs() != null) {
            actionExtras.putParcelableArray("android.support.remoteInputs", RemoteInputCompatJellybean.toBundleArray(action2.getRemoteInputs()));
        }
        actionExtras.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action2.getAllowGeneratedReplies());
        return actionExtras;
    }

    public static int getActionCount(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        Object obj = sActionsLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                Object[] actionObjectsLocked = getActionObjectsLocked(notif);
                int length = actionObjectsLocked == null ? 0 : actionObjectsLocked.length;
                return length;
            } finally {
                Object obj3 = obj2;
            }
        }
    }

    public static Action getAction(Notification notification, int i, Factory factory, RemoteInput.Factory factory2) {
        Notification notif = notification;
        int actionIndex = i;
        Factory factory3 = factory;
        RemoteInput.Factory remoteInputFactory = factory2;
        Notification notification2 = notif;
        int i2 = actionIndex;
        Factory factory4 = factory3;
        RemoteInput.Factory factory5 = remoteInputFactory;
        Object obj = sActionsLock;
        Action e = obj;
        synchronized (obj) {
            try {
                Object actionObject = getActionObjectsLocked(notif)[actionIndex];
                Bundle actionExtras = null;
                Bundle extras = getExtras(notif);
                Bundle extras2 = extras;
                if (extras != null) {
                    SparseArray sparseParcelableArray = extras2.getSparseParcelableArray("android.support.actionExtras");
                    SparseArray sparseArray = sparseParcelableArray;
                    if (sparseParcelableArray != null) {
                        actionExtras = (Bundle) sparseArray.get(actionIndex);
                    }
                }
                e = readAction(factory3, remoteInputFactory, sActionIconField.getInt(actionObject), (CharSequence) sActionTitleField.get(actionObject), (PendingIntent) sActionIntentField.get(actionObject), actionExtras);
                return e;
            } catch (IllegalAccessException e2) {
                IllegalAccessException illegalAccessException = e2;
                int e3 = Log.e(TAG, "Unable to access notification actions", e2);
                sActionsAccessFailed = true;
                Object obj2 = e2;
                return null;
            } finally {
                Action action = e2;
            }
        }
    }

    private static Object[] getActionObjectsLocked(Notification notification) {
        Object[] objArr;
        Notification notif = notification;
        Notification notification2 = notif;
        Object obj = sActionsLock;
        Object[] objArr2 = obj;
        synchronized (obj) {
            try {
                if (ensureActionReflectionReadyLocked()) {
                    objArr2 = (Object[]) sActionsField.get(notif);
                    return objArr2;
                }
                return null;
            } catch (IllegalAccessException e) {
                IllegalAccessException illegalAccessException = e;
                int e2 = Log.e(TAG, "Unable to access notification actions", e);
                sActionsAccessFailed = true;
                return null;
            } finally {
                objArr = objArr2;
            }
        }
    }

    private static boolean ensureActionReflectionReadyLocked() {
        boolean z;
        if (sActionsAccessFailed) {
            return false;
        }
        try {
            if (sActionsField == null) {
                sActionClass = Class.forName("android.app.Notification$Action");
                sActionIconField = sActionClass.getDeclaredField(KEY_ICON);
                sActionTitleField = sActionClass.getDeclaredField(KEY_TITLE);
                sActionIntentField = sActionClass.getDeclaredField(KEY_ACTION_INTENT);
                sActionsField = Notification.class.getDeclaredField("actions");
                sActionsField.setAccessible(true);
            }
        } catch (ClassNotFoundException e) {
            ClassNotFoundException classNotFoundException = e;
            int e2 = Log.e(TAG, "Unable to access notification actions", e);
            sActionsAccessFailed = true;
        } catch (NoSuchFieldException e3) {
            NoSuchFieldException noSuchFieldException = e3;
            int e4 = Log.e(TAG, "Unable to access notification actions", e3);
            sActionsAccessFailed = true;
        }
        if (sActionsAccessFailed) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public static Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList, Factory factory, RemoteInput.Factory factory2) {
        ArrayList<Parcelable> parcelables = arrayList;
        Factory actionFactory = factory;
        RemoteInput.Factory remoteInputFactory = factory2;
        ArrayList<Parcelable> arrayList2 = parcelables;
        Factory factory3 = actionFactory;
        RemoteInput.Factory factory4 = remoteInputFactory;
        if (parcelables == null) {
            return null;
        }
        Action[] actions = actionFactory.newArray(parcelables.size());
        for (int i = 0; i < actions.length; i++) {
            actions[i] = getActionFromBundle((Bundle) parcelables.get(i), actionFactory, remoteInputFactory);
        }
        return actions;
    }

    private static Action getActionFromBundle(Bundle bundle, Factory factory, RemoteInput.Factory factory2) {
        Bundle bundle2 = bundle;
        Factory actionFactory = factory;
        RemoteInput.Factory remoteInputFactory = factory2;
        Bundle bundle3 = bundle2;
        Factory factory3 = actionFactory;
        RemoteInput.Factory factory4 = remoteInputFactory;
        return actionFactory.build(bundle2.getInt(KEY_ICON), bundle2.getCharSequence(KEY_TITLE), (PendingIntent) bundle2.getParcelable(KEY_ACTION_INTENT), bundle2.getBundle(KEY_EXTRAS), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(bundle2, KEY_REMOTE_INPUTS), remoteInputFactory), bundle2.getBoolean(KEY_ALLOW_GENERATED_REPLIES));
    }

    public static ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr) {
        Action[] actions = actionArr;
        Action[] actionArr2 = actions;
        if (actions == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(actions.length);
        int length = actions.length;
        for (int i = 0; i < length; i++) {
            boolean add = arrayList.add(getBundleForAction(actions[i]));
        }
        return arrayList;
    }

    private static Bundle getBundleForAction(Action action) {
        Action action2 = action;
        Action action3 = action2;
        Bundle bundle = new Bundle();
        Bundle bundle2 = bundle;
        bundle.putInt(KEY_ICON, action2.getIcon());
        bundle2.putCharSequence(KEY_TITLE, action2.getTitle());
        bundle2.putParcelable(KEY_ACTION_INTENT, action2.getActionIntent());
        bundle2.putBundle(KEY_EXTRAS, action2.getExtras());
        bundle2.putParcelableArray(KEY_REMOTE_INPUTS, RemoteInputCompatJellybean.toBundleArray(action2.getRemoteInputs()));
        return bundle2;
    }

    public static boolean getLocalOnly(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return getExtras(notif).getBoolean("android.support.localOnly");
    }

    public static String getGroup(Notification notification) {
        Notification n = notification;
        Notification notification2 = n;
        return getExtras(n).getString("android.support.groupKey");
    }

    public static boolean isGroupSummary(Notification notification) {
        Notification n = notification;
        Notification notification2 = n;
        return getExtras(n).getBoolean("android.support.isGroupSummary");
    }

    public static String getSortKey(Notification notification) {
        Notification n = notification;
        Notification notification2 = n;
        return getExtras(n).getString("android.support.sortKey");
    }
}
