package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.app.NotificationCompatHoneycomb */
class NotificationCompatHoneycomb {
    NotificationCompatHoneycomb() {
    }

    static Notification add(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap) {
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
        Context context3 = context2;
        Notification notification2 = n;
        CharSequence charSequence4 = contentTitle;
        CharSequence charSequence5 = contentText;
        CharSequence charSequence6 = contentInfo;
        RemoteViews remoteViews2 = tickerView;
        int i2 = number;
        PendingIntent pendingIntent3 = contentIntent;
        PendingIntent pendingIntent4 = fullScreenIntent;
        Bitmap bitmap2 = largeIcon;
        Builder number2 = new Builder(context2).setWhen(n.when).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number);
        Builder builder = number2;
        return number2.getNotification();
    }
}
