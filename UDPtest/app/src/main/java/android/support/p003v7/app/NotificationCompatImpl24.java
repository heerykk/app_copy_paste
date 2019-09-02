package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.Notification.Builder;
import android.app.Notification.DecoratedCustomViewStyle;
import android.app.Notification.DecoratedMediaCustomViewStyle;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationBuilderWithBuilderAccessor;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v7.app.NotificationCompatImpl24 */
class NotificationCompatImpl24 {
    NotificationCompatImpl24() {
    }

    public static void addDecoratedCustomViewStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        NotificationBuilderWithBuilderAccessor b = notificationBuilderWithBuilderAccessor;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = b;
        Builder builder = b.getBuilder();
        Builder builder2 = builder;
        Builder style = builder.setStyle(new DecoratedCustomViewStyle());
    }

    public static void addDecoratedMediaCustomViewStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        NotificationBuilderWithBuilderAccessor b = notificationBuilderWithBuilderAccessor;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = b;
        Builder builder = b.getBuilder();
        Builder builder2 = builder;
        Builder style = builder.setStyle(new DecoratedMediaCustomViewStyle());
    }
}
