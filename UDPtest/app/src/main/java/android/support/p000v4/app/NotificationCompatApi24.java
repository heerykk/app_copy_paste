package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.MessagingStyle;
import android.app.Notification.MessagingStyle.Message;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationCompatBase.Action;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.app.NotificationCompatApi24 */
class NotificationCompatApi24 {
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

    /* renamed from: android.support.v4.app.NotificationCompatApi24$Builder */
    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {

        /* renamed from: b */
        private android.app.Notification.Builder f8b;

        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, String str, ArrayList<String> arrayList, Bundle bundle, int i5, int i6, Notification notification2, String str2, boolean z5, String str3, CharSequence[] charSequenceArr, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4) {
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
            CharSequence[] remoteInputHistory = charSequenceArr;
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
            CharSequence[] charSequenceArr2 = remoteInputHistory;
            RemoteViews remoteViews6 = contentView;
            RemoteViews remoteViews7 = bigContentView;
            RemoteViews remoteViews8 = headsUpContentView;
            this.f8b = new android.app.Notification.Builder(context2).setWhen(n.when).setShowWhen(z2).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(z3).setPriority(priority).setProgress(progressMax, progress, z).setLocalOnly(z4).setExtras(extras).setGroup(groupKey).setGroupSummary(z5).setSortKey(sortKey).setCategory(category).setColor(color).setVisibility(visibility).setPublicVersion(publicVersion).setRemoteInputHistory(remoteInputHistory);
            if (contentView != null) {
                android.app.Notification.Builder customContentView = this.f8b.setCustomContentView(contentView);
            }
            if (bigContentView != null) {
                android.app.Notification.Builder customBigContentView = this.f8b.setCustomBigContentView(bigContentView);
            }
            if (headsUpContentView != null) {
                android.app.Notification.Builder customHeadsUpContentView = this.f8b.setCustomHeadsUpContentView(headsUpContentView);
            }
            Iterator it = people.iterator();
            while (it.hasNext()) {
                android.app.Notification.Builder addPerson = this.f8b.addPerson((String) it.next());
            }
        }

        public void addAction(Action action) {
            Bundle bundle;
            Action action2 = action;
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
            android.app.Notification.Action.Builder allowGeneratedReplies = actionBuilder.setAllowGeneratedReplies(action2.getAllowGeneratedReplies());
            android.app.Notification.Builder addAction = this.f8b.addAction(actionBuilder.build());
        }

        public android.app.Notification.Builder getBuilder() {
            return this.f8b;
        }

        public Notification build() {
            return this.f8b.build();
        }
    }

    NotificationCompatApi24() {
    }

    public static void addMessagingStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, CharSequence charSequence2, List<CharSequence> list, List<Long> list2, List<CharSequence> list3, List<String> list4, List<Uri> list5) {
        NotificationBuilderWithBuilderAccessor b = notificationBuilderWithBuilderAccessor;
        CharSequence userDisplayName = charSequence;
        CharSequence conversationTitle = charSequence2;
        List<CharSequence> texts = list;
        List<Long> timestamps = list2;
        List<CharSequence> senders = list3;
        List<String> dataMimeTypes = list4;
        List<Uri> dataUris = list5;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = b;
        CharSequence charSequence3 = userDisplayName;
        CharSequence charSequence4 = conversationTitle;
        List<CharSequence> list6 = texts;
        List<Long> list7 = timestamps;
        List<CharSequence> list8 = senders;
        List<String> list9 = dataMimeTypes;
        List<Uri> list10 = dataUris;
        MessagingStyle style = new MessagingStyle(userDisplayName).setConversationTitle(conversationTitle);
        for (int i = 0; i < texts.size(); i++) {
            Message message = new Message((CharSequence) texts.get(i), ((Long) timestamps.get(i)).longValue(), (CharSequence) senders.get(i));
            Message message2 = message;
            if (dataMimeTypes.get(i) != null) {
                Message data = message2.setData((String) dataMimeTypes.get(i), (Uri) dataUris.get(i));
            }
            MessagingStyle addMessage = style.addMessage(message2);
        }
        style.setBuilder(b.getBuilder());
    }
}
