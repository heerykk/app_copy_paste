package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.app.NotificationManagerCompatApi24 */
class NotificationManagerCompatApi24 {
    NotificationManagerCompatApi24() {
    }

    public static boolean areNotificationsEnabled(NotificationManager notificationManager) {
        NotificationManager notificationManager2 = notificationManager;
        NotificationManager notificationManager3 = notificationManager2;
        return notificationManager2.areNotificationsEnabled();
    }

    public static int getImportance(NotificationManager notificationManager) {
        NotificationManager notificationManager2 = notificationManager;
        NotificationManager notificationManager3 = notificationManager2;
        return notificationManager2.getImportance();
    }
}
