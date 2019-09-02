package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationCompatBase.Action;
import android.support.p000v4.app.NotificationCompatBase.Action.Factory;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.app.NotificationCompatKitKat */
class NotificationCompatKitKat {

    /* renamed from: android.support.v4.app.NotificationCompatKitKat$Builder */
    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {

        /* renamed from: b */
        private android.app.Notification.Builder f11b;
        private List<Bundle> mActionExtrasList = new ArrayList();
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private Bundle mExtras;

        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, ArrayList<String> arrayList, Bundle bundle, String str, boolean z5, String str2, RemoteViews remoteViews2, RemoteViews remoteViews3) {
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
            ArrayList<String> people = arrayList;
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
            boolean showWhen = z2;
            boolean useChronometer = z3;
            int i8 = priority;
            CharSequence charSequence8 = subText;
            boolean localOnly = z4;
            ArrayList<String> arrayList2 = people;
            Bundle bundle2 = extras;
            String str3 = groupKey;
            boolean groupSummary = z5;
            String str4 = sortKey;
            RemoteViews remoteViews5 = contentView;
            RemoteViews remoteViews6 = bigContentView;
            this.f11b = new android.app.Notification.Builder(context2).setWhen(n.when).setShowWhen(showWhen).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(progressMax, progress, progressIndeterminate);
            this.mExtras = new Bundle();
            if (extras != null) {
                this.mExtras.putAll(extras);
            }
            if (people != null && !people.isEmpty()) {
                this.mExtras.putStringArray(NotificationCompat.EXTRA_PEOPLE, (String[]) people.toArray(new String[people.size()]));
            }
            if (localOnly) {
                this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
            }
            if (groupKey != null) {
                this.mExtras.putString(NotificationCompatExtras.EXTRA_GROUP_KEY, groupKey);
                if (!groupSummary) {
                    this.mExtras.putBoolean(NotificationManagerCompat.EXTRA_USE_SIDE_CHANNEL, true);
                } else {
                    this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY, true);
                }
            }
            if (sortKey != null) {
                this.mExtras.putString(NotificationCompatExtras.EXTRA_SORT_KEY, sortKey);
            }
            this.mContentView = contentView;
            this.mBigContentView = bigContentView;
        }

        public void addAction(Action action) {
            Action action2 = action;
            Action action3 = action2;
            boolean add = this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.f11b, action2));
        }

        public android.app.Notification.Builder getBuilder() {
            return this.f11b;
        }

        public Notification build() {
            SparseArray buildActionExtrasMap = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            SparseArray sparseArray = buildActionExtrasMap;
            if (buildActionExtrasMap != null) {
                this.mExtras.putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, sparseArray);
            }
            android.app.Notification.Builder extras = this.f11b.setExtras(this.mExtras);
            Notification notification = this.f11b.build();
            if (this.mContentView != null) {
                notification.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notification.bigContentView = this.mBigContentView;
            }
            return notification;
        }
    }

    NotificationCompatKitKat() {
    }

    public static Bundle getExtras(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.extras;
    }

    public static int getActionCount(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.actions == null ? 0 : notif.actions.length;
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
        Notification.Action action = notif.actions[actionIndex];
        Bundle actionExtras = null;
        SparseArray sparseParcelableArray = notif.extras.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
        SparseArray sparseArray = sparseParcelableArray;
        if (sparseParcelableArray != null) {
            actionExtras = (Bundle) sparseArray.get(actionIndex);
        }
        return NotificationCompatJellybean.readAction(factory3, remoteInputFactory, action.icon, action.title, action.actionIntent, actionExtras);
    }

    public static boolean getLocalOnly(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
    }

    public static String getGroup(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
    }

    public static boolean isGroupSummary(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
    }

    public static String getSortKey(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY);
    }
}
