package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.Notification.MediaStyle;
import android.media.session.MediaSession.Token;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationBuilderWithBuilderAccessor;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v7.app.NotificationCompatImpl21 */
class NotificationCompatImpl21 {
    NotificationCompatImpl21() {
    }

    public static void addMediaStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, int[] iArr, Object obj) {
        NotificationBuilderWithBuilderAccessor b = notificationBuilderWithBuilderAccessor;
        int[] actionsToShowInCompact = iArr;
        Object token = obj;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = b;
        int[] iArr2 = actionsToShowInCompact;
        Object obj2 = token;
        MediaStyle style = new MediaStyle(b.getBuilder());
        if (actionsToShowInCompact != null) {
            MediaStyle showActionsInCompactView = style.setShowActionsInCompactView(actionsToShowInCompact);
        }
        if (token != null) {
            MediaStyle mediaSession = style.setMediaSession((Token) token);
        }
    }
}
