package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationCompatBase.Action;
import android.support.p000v4.app.NotificationCompatBase.UnreadConversation;
import android.support.p000v4.app.NotificationCompatBase.UnreadConversation.Factory;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.app.NotificationCompatApi21 */
class NotificationCompatApi21 {
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_MESSAGES = "messages";
    private static final String KEY_ON_READ = "on_read";
    private static final String KEY_ON_REPLY = "on_reply";
    private static final String KEY_PARTICIPANTS = "participants";
    private static final String KEY_REMOTE_INPUT = "remote_input";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TIMESTAMP = "timestamp";

    /* renamed from: android.support.v4.app.NotificationCompatApi21$Builder */
    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {

        /* renamed from: b */
        private android.app.Notification.Builder f7b;
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private Bundle mExtras;
        private RemoteViews mHeadsUpContentView;

        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, String str, ArrayList<String> arrayList, Bundle bundle, int i5, int i6, Notification notification2, String str2, boolean z5, String str3, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4) {
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
            String category = str;
            ArrayList<String> people = arrayList;
            Bundle extras = bundle;
            int color = i5;
            int visibility = i6;
            Notification publicVersion = notification2;
            String groupKey = str2;
            String sortKey = str3;
            RemoteViews contentView = remoteViews2;
            RemoteViews bigContentView = remoteViews3;
            RemoteViews headsUpContentView = remoteViews4;
            Context context3 = context2;
            Notification notification3 = n;
            CharSequence charSequence5 = contentTitle;
            CharSequence charSequence6 = contentText;
            CharSequence charSequence7 = contentInfo;
            RemoteViews remoteViews5 = tickerView;
            int i7 = number;
            PendingIntent pendingIntent3 = contentIntent;
            PendingIntent pendingIntent4 = fullScreenIntent;
            Bitmap bitmap2 = largeIcon;
            int i8 = progressMax;
            int i9 = progress;
            int i10 = priority;
            CharSequence charSequence8 = subText;
            String str4 = category;
            ArrayList<String> arrayList2 = people;
            Bundle bundle2 = extras;
            int i11 = color;
            int i12 = visibility;
            Notification notification4 = publicVersion;
            String str5 = groupKey;
            String str6 = sortKey;
            RemoteViews remoteViews6 = contentView;
            RemoteViews remoteViews7 = bigContentView;
            RemoteViews remoteViews8 = headsUpContentView;
            this.f7b = new android.app.Notification.Builder(context2).setWhen(n.when).setShowWhen(z2).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(z3).setPriority(priority).setProgress(progressMax, progress, z).setLocalOnly(z4).setGroup(groupKey).setGroupSummary(z5).setSortKey(sortKey).setCategory(category).setColor(color).setVisibility(visibility).setPublicVersion(publicVersion);
            this.mExtras = new Bundle();
            if (extras != null) {
                this.mExtras.putAll(extras);
            }
            Iterator it = people.iterator();
            while (it.hasNext()) {
                android.app.Notification.Builder addPerson = this.f7b.addPerson((String) it.next());
            }
            this.mContentView = contentView;
            this.mBigContentView = bigContentView;
            this.mHeadsUpContentView = headsUpContentView;
        }

        public void addAction(Action action) {
            Action action2 = action;
            Action action3 = action2;
            NotificationCompatApi20.addAction(this.f7b, action2);
        }

        public android.app.Notification.Builder getBuilder() {
            return this.f7b;
        }

        public Notification build() {
            android.app.Notification.Builder extras = this.f7b.setExtras(this.mExtras);
            Notification notification = this.f7b.build();
            if (this.mContentView != null) {
                notification.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notification.bigContentView = this.mBigContentView;
            }
            if (this.mHeadsUpContentView != null) {
                notification.headsUpContentView = this.mHeadsUpContentView;
            }
            return notification;
        }
    }

    NotificationCompatApi21() {
    }

    public static String getCategory(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return notif.category;
    }

    static Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation) {
        UnreadConversation uc = unreadConversation;
        UnreadConversation unreadConversation2 = uc;
        if (uc == null) {
            return null;
        }
        Bundle b = new Bundle();
        String author = null;
        if (uc.getParticipants() != null && uc.getParticipants().length > 1) {
            author = uc.getParticipants()[0];
        }
        Parcelable[] messages = new Parcelable[uc.getMessages().length];
        for (int i = 0; i < messages.length; i++) {
            Bundle bundle = new Bundle();
            Bundle m = bundle;
            bundle.putString(KEY_TEXT, uc.getMessages()[i]);
            m.putString(KEY_AUTHOR, author);
            messages[i] = m;
        }
        b.putParcelableArray(KEY_MESSAGES, messages);
        RemoteInput remoteInput = uc.getRemoteInput();
        RemoteInput remoteInput2 = remoteInput;
        if (remoteInput != null) {
            b.putParcelable(KEY_REMOTE_INPUT, fromCompatRemoteInput(remoteInput2));
        }
        b.putParcelable(KEY_ON_REPLY, uc.getReplyPendingIntent());
        b.putParcelable(KEY_ON_READ, uc.getReadPendingIntent());
        b.putStringArray(KEY_PARTICIPANTS, uc.getParticipants());
        b.putLong(KEY_TIMESTAMP, uc.getLatestTimestamp());
        return b;
    }

    static UnreadConversation getUnreadConversationFromBundle(Bundle bundle, Factory factory, RemoteInput.Factory factory2) {
        Bundle b = bundle;
        Factory factory3 = factory;
        RemoteInput.Factory remoteInputFactory = factory2;
        Bundle bundle2 = b;
        Factory factory4 = factory3;
        RemoteInput.Factory factory5 = remoteInputFactory;
        if (b == null) {
            return null;
        }
        Parcelable[] parcelableMessages = b.getParcelableArray(KEY_MESSAGES);
        String[] messages = null;
        if (parcelableMessages != null) {
            String[] tmp = new String[parcelableMessages.length];
            boolean success = true;
            int i = 0;
            while (true) {
                if (i < tmp.length) {
                    if (!(parcelableMessages[i] instanceof Bundle)) {
                        success = false;
                        break;
                    }
                    tmp[i] = ((Bundle) parcelableMessages[i]).getString(KEY_TEXT);
                    if (tmp[i] == null) {
                        success = false;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (!success) {
                return null;
            }
            messages = tmp;
        }
        PendingIntent onRead = (PendingIntent) b.getParcelable(KEY_ON_READ);
        PendingIntent onReply = (PendingIntent) b.getParcelable(KEY_ON_REPLY);
        android.app.RemoteInput remoteInput = (android.app.RemoteInput) b.getParcelable(KEY_REMOTE_INPUT);
        String[] stringArray = b.getStringArray(KEY_PARTICIPANTS);
        String[] participants = stringArray;
        if (stringArray == null || participants.length != 1) {
            return null;
        }
        return factory3.build(messages, remoteInput == null ? null : toCompatRemoteInput(remoteInput, remoteInputFactory), onReply, onRead, participants, b.getLong(KEY_TIMESTAMP));
    }

    private static android.app.RemoteInput fromCompatRemoteInput(RemoteInput remoteInput) {
        RemoteInput src = remoteInput;
        RemoteInput remoteInput2 = src;
        return new android.app.RemoteInput.Builder(src.getResultKey()).setLabel(src.getLabel()).setChoices(src.getChoices()).setAllowFreeFormInput(src.getAllowFreeFormInput()).addExtras(src.getExtras()).build();
    }

    private static RemoteInput toCompatRemoteInput(android.app.RemoteInput remoteInput, RemoteInput.Factory factory) {
        android.app.RemoteInput remoteInput2 = remoteInput;
        RemoteInput.Factory factory2 = factory;
        android.app.RemoteInput remoteInput3 = remoteInput2;
        RemoteInput.Factory factory3 = factory2;
        return factory2.build(remoteInput2.getResultKey(), remoteInput2.getLabel(), remoteInput2.getChoices(), remoteInput2.getAllowFreeFormInput(), remoteInput2.getExtras());
    }
}
