package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationCompatBase.Action;
import android.support.p000v4.app.NotificationCompatBase.Action.Factory;
import android.widget.RemoteViews;
import java.util.ArrayList;

@TargetApi(20)
@RequiresApi(20)
/* renamed from: android.support.v4.app.NotificationCompatApi20 */
class NotificationCompatApi20 {

    /* renamed from: android.support.v4.app.NotificationCompatApi20$Builder */
    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {

        /* renamed from: b */
        private android.app.Notification.Builder f6b;
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
            int i8 = priority;
            CharSequence charSequence8 = subText;
            ArrayList<String> arrayList2 = people;
            Bundle bundle2 = extras;
            String str3 = groupKey;
            String str4 = sortKey;
            RemoteViews remoteViews5 = contentView;
            RemoteViews remoteViews6 = bigContentView;
            this.f6b = new android.app.Notification.Builder(context2).setWhen(n.when).setShowWhen(z2).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(z3).setPriority(priority).setProgress(progressMax, progress, z).setLocalOnly(z4).setGroup(groupKey).setGroupSummary(z5).setSortKey(sortKey);
            this.mExtras = new Bundle();
            if (extras != null) {
                this.mExtras.putAll(extras);
            }
            if (people != null && !people.isEmpty()) {
                this.mExtras.putStringArray(NotificationCompat.EXTRA_PEOPLE, (String[]) people.toArray(new String[people.size()]));
            }
            this.mContentView = contentView;
            this.mBigContentView = bigContentView;
        }

        public void addAction(Action action) {
            Action action2 = action;
            Action action3 = action2;
            NotificationCompatApi20.addAction(this.f6b, action2);
        }

        public android.app.Notification.Builder getBuilder() {
            return this.f6b;
        }

        public Notification build() {
            android.app.Notification.Builder extras = this.f6b.setExtras(this.mExtras);
            Notification notification = this.f6b.build();
            if (this.mContentView != null) {
                notification.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notification.bigContentView = this.mBigContentView;
            }
            return notification;
        }
    }

    NotificationCompatApi20() {
    }

    public static void addAction(android.app.Notification.Builder builder, Action action) {
        Bundle bundle;
        android.app.Notification.Builder b = builder;
        Action action2 = action;
        android.app.Notification.Builder builder2 = b;
        Action action3 = action2;
        android.app.Notification.Action.Builder actionBuilder = new android.app.Notification.Action.Builder(action2.getIcon(), action2.getTitle(), action2.getActionIntent());
        if (action2.getRemoteInputs() != null) {
            RemoteInput[] fromCompat = RemoteInputCompatApi20.fromCompat(action2.getRemoteInputs());
            RemoteInput[] remoteInputArr = fromCompat;
            int length = fromCompat.length;
            for (int i = 0; i < length; i++) {
                android.app.Notification.Action.Builder addRemoteInput = actionBuilder.addRemoteInput(remoteInputArr[i]);
            }
        }
        if (action2.getExtras() == null) {
            bundle = new Bundle();
        } else {
            bundle = new Bundle(action2.getExtras());
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", action2.getAllowGeneratedReplies());
        android.app.Notification.Action.Builder addExtras = actionBuilder.addExtras(bundle);
        android.app.Notification.Builder addAction = b.addAction(actionBuilder.build());
    }

    public static Action getAction(Notification notification, int i, Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        Notification notif = notification;
        int actionIndex = i;
        Factory actionFactory = factory;
        RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory = factory2;
        Notification notification2 = notif;
        int i2 = actionIndex;
        Factory factory3 = actionFactory;
        RemoteInputCompatBase.RemoteInput.Factory factory4 = remoteInputFactory;
        return getActionCompatFromAction(notif.actions[actionIndex], actionFactory, remoteInputFactory);
    }

    private static Action getActionCompatFromAction(Notification.Action action, Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        Notification.Action action2 = action;
        Factory actionFactory = factory;
        RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory = factory2;
        Notification.Action action3 = action2;
        Factory factory3 = actionFactory;
        RemoteInputCompatBase.RemoteInput.Factory factory4 = remoteInputFactory;
        RemoteInputCompatBase.RemoteInput[] remoteInputs = RemoteInputCompatApi20.toCompat(action2.getRemoteInputs(), remoteInputFactory);
        boolean z = action2.getExtras().getBoolean("android.support.allowGeneratedReplies");
        boolean z2 = z;
        return actionFactory.build(action2.icon, action2.title, action2.actionIntent, action2.getExtras(), remoteInputs, z);
    }

    private static Notification.Action getActionFromActionCompat(Action action) {
        Action actionCompat = action;
        Action action2 = actionCompat;
        android.app.Notification.Action.Builder actionBuilder = new android.app.Notification.Action.Builder(actionCompat.getIcon(), actionCompat.getTitle(), actionCompat.getActionIntent()).addExtras(actionCompat.getExtras());
        RemoteInputCompatBase.RemoteInput[] remoteInputs = actionCompat.getRemoteInputs();
        RemoteInputCompatBase.RemoteInput[] remoteInputCompats = remoteInputs;
        if (remoteInputs != null) {
            RemoteInput[] fromCompat = RemoteInputCompatApi20.fromCompat(remoteInputCompats);
            RemoteInput[] remoteInputArr = fromCompat;
            RemoteInput[] remoteInputArr2 = fromCompat;
            int length = fromCompat.length;
            for (int i = 0; i < length; i++) {
                android.app.Notification.Action.Builder addRemoteInput = actionBuilder.addRemoteInput(remoteInputArr2[i]);
            }
        }
        return actionBuilder.build();
    }

    public static Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList, Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        ArrayList<Parcelable> parcelables = arrayList;
        Factory actionFactory = factory;
        RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory = factory2;
        ArrayList<Parcelable> arrayList2 = parcelables;
        Factory factory3 = actionFactory;
        RemoteInputCompatBase.RemoteInput.Factory factory4 = remoteInputFactory;
        if (parcelables == null) {
            return null;
        }
        Action[] actions = actionFactory.newArray(parcelables.size());
        for (int i = 0; i < actions.length; i++) {
            actions[i] = getActionCompatFromAction((Notification.Action) parcelables.get(i), actionFactory, remoteInputFactory);
        }
        return actions;
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
            boolean add = arrayList.add(getActionFromActionCompat(actions[i]));
        }
        return arrayList;
    }

    public static boolean getLocalOnly(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return (notif.flags & 256) != 0;
    }

    public static String getGroup(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.getGroup();
    }

    public static boolean isGroupSummary(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return (notif.flags & 512) != 0;
    }

    public static String getSortKey(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.getSortKey();
    }
}
