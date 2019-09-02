package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(9)
@RestrictTo({Scope.LIBRARY_GROUP})
@TargetApi(9)
/* renamed from: android.support.v4.app.NotificationCompatBase */
public class NotificationCompatBase {
    private static Method sSetLatestEventInfo;

    /* renamed from: android.support.v4.app.NotificationCompatBase$Action */
    public static abstract class Action {

        /* renamed from: android.support.v4.app.NotificationCompatBase$Action$Factory */
        public interface Factory {
            Action build(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, boolean z);

            Action[] newArray(int i);
        }

        public abstract PendingIntent getActionIntent();

        public abstract boolean getAllowGeneratedReplies();

        public abstract Bundle getExtras();

        public abstract int getIcon();

        public abstract RemoteInput[] getRemoteInputs();

        public abstract CharSequence getTitle();

        public Action() {
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompatBase$UnreadConversation */
    public static abstract class UnreadConversation {

        /* renamed from: android.support.v4.app.NotificationCompatBase$UnreadConversation$Factory */
        public interface Factory {
            UnreadConversation build(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j);
        }

        /* access modifiers changed from: 0000 */
        public abstract long getLatestTimestamp();

        /* access modifiers changed from: 0000 */
        public abstract String[] getMessages();

        /* access modifiers changed from: 0000 */
        public abstract String getParticipant();

        /* access modifiers changed from: 0000 */
        public abstract String[] getParticipants();

        /* access modifiers changed from: 0000 */
        public abstract PendingIntent getReadPendingIntent();

        /* access modifiers changed from: 0000 */
        public abstract RemoteInput getRemoteInput();

        /* access modifiers changed from: 0000 */
        public abstract PendingIntent getReplyPendingIntent();

        public UnreadConversation() {
        }
    }

    public NotificationCompatBase() {
    }

    public static Notification add(Notification notification, Context context, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        Notification notification2 = notification;
        Context context2 = context;
        CharSequence contentTitle = charSequence;
        CharSequence contentText = charSequence2;
        PendingIntent contentIntent = pendingIntent;
        PendingIntent fullScreenIntent = pendingIntent2;
        Notification notification3 = notification2;
        Context context3 = context2;
        CharSequence charSequence3 = contentTitle;
        CharSequence charSequence4 = contentText;
        PendingIntent pendingIntent3 = contentIntent;
        PendingIntent pendingIntent4 = fullScreenIntent;
        if (sSetLatestEventInfo == null) {
            Class<Notification> cls = Notification.class;
            String str = "setLatestEventInfo";
            try {
                Class[] clsArr = new Class[4];
                clsArr[0] = Context.class;
                clsArr[1] = CharSequence.class;
                clsArr[2] = CharSequence.class;
                clsArr[3] = PendingIntent.class;
                sSetLatestEventInfo = cls.getMethod(str, clsArr);
            } catch (NoSuchMethodException e) {
                NoSuchMethodException noSuchMethodException = e;
                throw new RuntimeException(e);
            }
        }
        try {
            Object invoke = sSetLatestEventInfo.invoke(notification2, new Object[]{context2, contentTitle, contentText, contentIntent});
            notification2.fullScreenIntent = fullScreenIntent;
            return notification2;
        } catch (IllegalAccessException | InvocationTargetException e2) {
            Throwable th = e2;
            throw new RuntimeException(e2);
        }
    }
}
