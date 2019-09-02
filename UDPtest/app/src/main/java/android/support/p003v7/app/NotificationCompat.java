package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.BundleCompat;
import android.support.p000v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.p000v4.app.NotificationCompat.MessagingStyle;
import android.support.p000v4.app.NotificationCompat.MessagingStyle.Message;
import android.support.p000v4.app.NotificationCompat.Style;
import android.support.p000v4.media.session.MediaSessionCompat.Token;
import android.support.p000v4.text.BidiFormatter;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.RemoteViews;
import java.util.List;

/* renamed from: android.support.v7.app.NotificationCompat */
public class NotificationCompat extends android.support.p000v4.app.NotificationCompat {

    /* renamed from: android.support.v7.app.NotificationCompat$Api24Extender */
    private static class Api24Extender extends BuilderExtender {
        private Api24Extender() {
        }

        /* synthetic */ Api24Extender(C02621 r5) {
            C02621 r3 = r5;
            this();
        }

        public Notification build(android.support.p000v4.app.NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            android.support.p000v4.app.NotificationCompat.Builder b = builder;
            NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
            android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
            NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
            NotificationCompat.access$900(builder2, b);
            return builder2.build();
        }
    }

    /* renamed from: android.support.v7.app.NotificationCompat$Builder */
    public static class Builder extends android.support.p000v4.app.NotificationCompat.Builder {
        public Builder(Context context) {
            Context context2 = context;
            Context context3 = context2;
            super(context2);
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence resolveText() {
            CharSequence access$100;
            if (this.mStyle instanceof MessagingStyle) {
                MessagingStyle messagingStyle = (MessagingStyle) this.mStyle;
                MessagingStyle style = messagingStyle;
                Message m = NotificationCompat.access$000(messagingStyle);
                CharSequence conversationTitle = style.getConversationTitle();
                if (m != null) {
                    if (conversationTitle == null) {
                        access$100 = m.getText();
                    } else {
                        access$100 = NotificationCompat.access$100(this, style, m);
                    }
                    return access$100;
                }
            }
            return super.resolveText();
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence resolveTitle() {
            if (this.mStyle instanceof MessagingStyle) {
                MessagingStyle messagingStyle = (MessagingStyle) this.mStyle;
                MessagingStyle style = messagingStyle;
                Message m = NotificationCompat.access$000(messagingStyle);
                CharSequence conversationTitle = style.getConversationTitle();
                CharSequence conversationTitle2 = conversationTitle;
                if (!(conversationTitle == null && m == null)) {
                    return conversationTitle2 == null ? m.getSender() : conversationTitle2;
                }
            }
            return super.resolveTitle();
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public BuilderExtender getExtender() {
            if (VERSION.SDK_INT >= 24) {
                return new Api24Extender(null);
            }
            if (VERSION.SDK_INT >= 21) {
                return new LollipopExtender();
            }
            if (VERSION.SDK_INT >= 16) {
                return new JellybeanExtender();
            }
            if (VERSION.SDK_INT < 14) {
                return super.getExtender();
            }
            return new IceCreamSandwichExtender();
        }
    }

    /* renamed from: android.support.v7.app.NotificationCompat$DecoratedCustomViewStyle */
    public static class DecoratedCustomViewStyle extends Style {
        public DecoratedCustomViewStyle() {
        }
    }

    /* renamed from: android.support.v7.app.NotificationCompat$DecoratedMediaCustomViewStyle */
    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
        public DecoratedMediaCustomViewStyle() {
        }
    }

    /* renamed from: android.support.v7.app.NotificationCompat$IceCreamSandwichExtender */
    private static class IceCreamSandwichExtender extends BuilderExtender {
        IceCreamSandwichExtender() {
        }

        public Notification build(android.support.p000v4.app.NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            android.support.p000v4.app.NotificationCompat.Builder b = builder;
            NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
            android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
            NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
            RemoteViews contentView = NotificationCompat.access$300(builder2, b);
            Notification n = builder2.build();
            if (contentView != null) {
                n.contentView = contentView;
            } else if (b.getContentView() != null) {
                n.contentView = b.getContentView();
            }
            return n;
        }
    }

    /* renamed from: android.support.v7.app.NotificationCompat$JellybeanExtender */
    private static class JellybeanExtender extends BuilderExtender {
        JellybeanExtender() {
        }

        public Notification build(android.support.p000v4.app.NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            android.support.p000v4.app.NotificationCompat.Builder b = builder;
            NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
            android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
            NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
            RemoteViews contentView = NotificationCompat.access$400(builder2, b);
            Notification n = builder2.build();
            if (contentView != null) {
                n.contentView = contentView;
            }
            NotificationCompat.access$500(n, b);
            return n;
        }
    }

    /* renamed from: android.support.v7.app.NotificationCompat$LollipopExtender */
    private static class LollipopExtender extends BuilderExtender {
        LollipopExtender() {
        }

        public Notification build(android.support.p000v4.app.NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            android.support.p000v4.app.NotificationCompat.Builder b = builder;
            NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
            android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
            NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
            RemoteViews contentView = NotificationCompat.access$600(builder2, b);
            Notification n = builder2.build();
            if (contentView != null) {
                n.contentView = contentView;
            }
            NotificationCompat.access$700(n, b);
            NotificationCompat.access$800(n, b);
            return n;
        }
    }

    /* renamed from: android.support.v7.app.NotificationCompat$MediaStyle */
    public static class MediaStyle extends Style {
        int[] mActionsToShowInCompact = null;
        PendingIntent mCancelButtonIntent;
        boolean mShowCancelButton;
        Token mToken;

        public MediaStyle() {
        }

        public MediaStyle(android.support.p000v4.app.NotificationCompat.Builder builder) {
            android.support.p000v4.app.NotificationCompat.Builder builder2 = builder;
            android.support.p000v4.app.NotificationCompat.Builder builder3 = builder2;
            setBuilder(builder2);
        }

        public MediaStyle setShowActionsInCompactView(int... iArr) {
            int[] actions = iArr;
            int[] iArr2 = actions;
            this.mActionsToShowInCompact = actions;
            return this;
        }

        public MediaStyle setMediaSession(Token token) {
            Token token2 = token;
            Token token3 = token2;
            this.mToken = token2;
            return this;
        }

        public MediaStyle setShowCancelButton(boolean z) {
            this.mShowCancelButton = z;
            return this;
        }

        public MediaStyle setCancelButtonIntent(PendingIntent pendingIntent) {
            PendingIntent pendingIntent2 = pendingIntent;
            PendingIntent pendingIntent3 = pendingIntent2;
            this.mCancelButtonIntent = pendingIntent2;
            return this;
        }
    }

    public NotificationCompat() {
    }

    static /* synthetic */ Message access$000(MessagingStyle messagingStyle) {
        MessagingStyle x0 = messagingStyle;
        MessagingStyle messagingStyle2 = x0;
        return findLatestIncomingMessage(x0);
    }

    static /* synthetic */ CharSequence access$100(android.support.p000v4.app.NotificationCompat.Builder builder, MessagingStyle messagingStyle, Message message) {
        android.support.p000v4.app.NotificationCompat.Builder x0 = builder;
        MessagingStyle x1 = messagingStyle;
        Message x2 = message;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = x0;
        MessagingStyle messagingStyle2 = x1;
        Message message2 = x2;
        return makeMessageLine(x0, x1, x2);
    }

    static /* synthetic */ RemoteViews access$300(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        NotificationBuilderWithBuilderAccessor x0 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder x1 = builder;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = x0;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = x1;
        return addStyleGetContentViewIcs(x0, x1);
    }

    static /* synthetic */ RemoteViews access$400(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        NotificationBuilderWithBuilderAccessor x0 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder x1 = builder;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = x0;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = x1;
        return addStyleGetContentViewJellybean(x0, x1);
    }

    static /* synthetic */ void access$500(Notification notification, android.support.p000v4.app.NotificationCompat.Builder builder) {
        Notification x0 = notification;
        android.support.p000v4.app.NotificationCompat.Builder x1 = builder;
        Notification notification2 = x0;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = x1;
        addBigStyleToBuilderJellybean(x0, x1);
    }

    static /* synthetic */ RemoteViews access$600(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        NotificationBuilderWithBuilderAccessor x0 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder x1 = builder;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = x0;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = x1;
        return addStyleGetContentViewLollipop(x0, x1);
    }

    static /* synthetic */ void access$700(Notification notification, android.support.p000v4.app.NotificationCompat.Builder builder) {
        Notification x0 = notification;
        android.support.p000v4.app.NotificationCompat.Builder x1 = builder;
        Notification notification2 = x0;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = x1;
        addBigStyleToBuilderLollipop(x0, x1);
    }

    static /* synthetic */ void access$800(Notification notification, android.support.p000v4.app.NotificationCompat.Builder builder) {
        Notification x0 = notification;
        android.support.p000v4.app.NotificationCompat.Builder x1 = builder;
        Notification notification2 = x0;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = x1;
        addHeadsUpToBuilderLollipop(x0, x1);
    }

    static /* synthetic */ void access$900(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        NotificationBuilderWithBuilderAccessor x0 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder x1 = builder;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = x0;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = x1;
        addStyleToBuilderApi24(x0, x1);
    }

    public static Token getMediaSession(Notification notification) {
        Notification notification2 = notification;
        Notification notification3 = notification2;
        Bundle extras = getExtras(notification2);
        Bundle extras2 = extras;
        if (extras != null) {
            if (VERSION.SDK_INT < 21) {
                IBinder binder = BundleCompat.getBinder(extras2, android.support.p000v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
                IBinder tokenInner = binder;
                if (binder != null) {
                    Parcel obtain = Parcel.obtain();
                    Parcel p = obtain;
                    obtain.writeStrongBinder(tokenInner);
                    p.setDataPosition(0);
                    Token token = (Token) Token.CREATOR.createFromParcel(p);
                    p.recycle();
                    return token;
                }
            } else {
                Object parcelable = extras2.getParcelable(android.support.p000v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
                Object tokenInner2 = parcelable;
                if (parcelable != null) {
                    return Token.fromToken(tokenInner2);
                }
            }
        }
        return null;
    }

    @TargetApi(24)
    @RequiresApi(24)
    private static void addStyleToBuilderApi24(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
        android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
        if (b.mStyle instanceof DecoratedCustomViewStyle) {
            NotificationCompatImpl24.addDecoratedCustomViewStyle(builder2);
        } else if (b.mStyle instanceof DecoratedMediaCustomViewStyle) {
            NotificationCompatImpl24.addDecoratedMediaCustomViewStyle(builder2);
        } else if (!(b.mStyle instanceof MessagingStyle)) {
            RemoteViews addStyleGetContentViewLollipop = addStyleGetContentViewLollipop(builder2, b);
        }
    }

    @TargetApi(21)
    @RequiresApi(21)
    private static RemoteViews addStyleGetContentViewLollipop(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
        android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
        if (b.mStyle instanceof MediaStyle) {
            MediaStyle mediaStyle = (MediaStyle) b.mStyle;
            NotificationCompatImpl21.addMediaStyle(builder2, mediaStyle.mActionsToShowInCompact, mediaStyle.mToken == null ? null : mediaStyle.mToken.getToken());
            boolean hasContentView = b.getContentView() != null;
            boolean createCustomContent = hasContentView || ((VERSION.SDK_INT >= 21 && VERSION.SDK_INT <= 23) && b.getBigContentView() != null);
            if (!(b.mStyle instanceof DecoratedMediaCustomViewStyle) || !createCustomContent) {
                return null;
            }
            RemoteViews contentViewMedia = NotificationCompatImplBase.overrideContentViewMedia(builder2, b.mContext, b.mContentTitle, b.mContentText, b.mContentInfo, b.mNumber, b.mLargeIcon, b.mSubText, b.mUseChronometer, b.getWhenIfShowing(), b.getPriority(), b.mActions, mediaStyle.mActionsToShowInCompact, false, null, hasContentView);
            if (hasContentView) {
                NotificationCompatImplBase.buildIntoRemoteViews(b.mContext, contentViewMedia, b.getContentView());
            }
            setBackgroundColor(b.mContext, contentViewMedia, b.getColor());
            return contentViewMedia;
        } else if (!(b.mStyle instanceof DecoratedCustomViewStyle)) {
            return addStyleGetContentViewJellybean(builder2, b);
        } else {
            return getDecoratedContentView(b);
        }
    }

    @TargetApi(16)
    @RequiresApi(16)
    private static RemoteViews addStyleGetContentViewJellybean(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
        android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
        if (b.mStyle instanceof MessagingStyle) {
            addMessagingFallBackStyle((MessagingStyle) b.mStyle, builder2, b);
        }
        return addStyleGetContentViewIcs(builder2, b);
    }

    private static Message findLatestIncomingMessage(MessagingStyle messagingStyle) {
        MessagingStyle style = messagingStyle;
        MessagingStyle messagingStyle2 = style;
        List messages = style.getMessages();
        List list = messages;
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message message = (Message) list.get(i);
            Message m = message;
            if (!TextUtils.isEmpty(message.getSender())) {
                return m;
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return (Message) list.get(list.size() - 1);
    }

    private static CharSequence makeMessageLine(android.support.p000v4.app.NotificationCompat.Builder builder, MessagingStyle messagingStyle, Message message) {
        CharSequence charSequence;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        MessagingStyle style = messagingStyle;
        Message m = message;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = b;
        MessagingStyle messagingStyle2 = style;
        Message message2 = m;
        BidiFormatter bidi = BidiFormatter.getInstance();
        SpannableStringBuilder sb = new SpannableStringBuilder();
        boolean afterLollipop = VERSION.SDK_INT >= 21;
        int color = (!afterLollipop && VERSION.SDK_INT > 10) ? -1 : ViewCompat.MEASURED_STATE_MASK;
        CharSequence replyName = m.getSender();
        if (TextUtils.isEmpty(m.getSender())) {
            if (style.getUserDisplayName() != null) {
                charSequence = style.getUserDisplayName();
            } else {
                charSequence = "";
            }
            replyName = charSequence;
            color = (afterLollipop && b.getColor() != 0) ? b.getColor() : color;
        }
        CharSequence senderText = bidi.unicodeWrap(replyName);
        SpannableStringBuilder append = sb.append(senderText);
        sb.setSpan(makeFontColorSpan(color), sb.length() - senderText.length(), sb.length(), 33);
        SpannableStringBuilder append2 = sb.append("  ").append(bidi.unicodeWrap(m.getText() != null ? m.getText() : ""));
        return sb;
    }

    private static TextAppearanceSpan makeFontColorSpan(int i) {
        int color = i;
        int i2 = color;
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(color), null);
        return textAppearanceSpan;
    }

    private static void addMessagingFallBackStyle(MessagingStyle messagingStyle, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        MessagingStyle style = messagingStyle;
        NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        MessagingStyle messagingStyle2 = style;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
        android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
        SpannableStringBuilder completeMessage = new SpannableStringBuilder();
        List messages = style.getMessages();
        boolean showNames = style.getConversationTitle() != null || hasMessagesWithoutSender(style.getMessages());
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message message = (Message) messages.get(i);
            CharSequence line = !showNames ? message.getText() : makeMessageLine(b, style, message);
            if (i != messages.size() - 1) {
                SpannableStringBuilder insert = completeMessage.insert(0, "\n");
            }
            SpannableStringBuilder insert2 = completeMessage.insert(0, line);
        }
        NotificationCompatImplJellybean.addBigTextStyle(builder2, completeMessage);
    }

    private static boolean hasMessagesWithoutSender(List<Message> list) {
        List<Message> messages = list;
        List<Message> list2 = messages;
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message message = (Message) messages.get(i);
            Message message2 = message;
            if (message.getSender() == null) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(14)
    @RequiresApi(14)
    private static RemoteViews addStyleGetContentViewIcs(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, android.support.p000v4.app.NotificationCompat.Builder builder) {
        NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
        android.support.p000v4.app.NotificationCompat.Builder builder3 = b;
        if (b.mStyle instanceof MediaStyle) {
            MediaStyle mediaStyle = (MediaStyle) b.mStyle;
            boolean isDecorated = (b.mStyle instanceof DecoratedMediaCustomViewStyle) && b.getContentView() != null;
            RemoteViews contentViewMedia = NotificationCompatImplBase.overrideContentViewMedia(builder2, b.mContext, b.mContentTitle, b.mContentText, b.mContentInfo, b.mNumber, b.mLargeIcon, b.mSubText, b.mUseChronometer, b.getWhenIfShowing(), b.getPriority(), b.mActions, mediaStyle.mActionsToShowInCompact, mediaStyle.mShowCancelButton, mediaStyle.mCancelButtonIntent, isDecorated);
            if (isDecorated) {
                NotificationCompatImplBase.buildIntoRemoteViews(b.mContext, contentViewMedia, b.getContentView());
                return contentViewMedia;
            }
        } else if (b.mStyle instanceof DecoratedCustomViewStyle) {
            return getDecoratedContentView(b);
        }
        return null;
    }

    @TargetApi(16)
    @RequiresApi(16)
    private static void addBigStyleToBuilderJellybean(Notification notification, android.support.p000v4.app.NotificationCompat.Builder builder) {
        RemoteViews bigContentView;
        Notification n = notification;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        Notification notification2 = n;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = b;
        if (b.mStyle instanceof MediaStyle) {
            MediaStyle mediaStyle = (MediaStyle) b.mStyle;
            if (b.getBigContentView() == null) {
                bigContentView = b.getContentView();
            } else {
                bigContentView = b.getBigContentView();
            }
            RemoteViews innerView = bigContentView;
            boolean isDecorated = (b.mStyle instanceof DecoratedMediaCustomViewStyle) && innerView != null;
            NotificationCompatImplBase.overrideMediaBigContentView(n, b.mContext, b.mContentTitle, b.mContentText, b.mContentInfo, b.mNumber, b.mLargeIcon, b.mSubText, b.mUseChronometer, b.getWhenIfShowing(), b.getPriority(), 0, b.mActions, mediaStyle.mShowCancelButton, mediaStyle.mCancelButtonIntent, isDecorated);
            if (isDecorated) {
                NotificationCompatImplBase.buildIntoRemoteViews(b.mContext, n.bigContentView, innerView);
            }
        } else if (b.mStyle instanceof DecoratedCustomViewStyle) {
            addDecoratedBigStyleToBuilderJellybean(n, b);
        }
    }

    private static RemoteViews getDecoratedContentView(android.support.p000v4.app.NotificationCompat.Builder builder) {
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = b;
        if (b.getContentView() == null) {
            return null;
        }
        RemoteViews remoteViews = NotificationCompatImplBase.applyStandardTemplateWithActions(b.mContext, b.mContentTitle, b.mContentText, b.mContentInfo, b.mNumber, b.mNotification.icon, b.mLargeIcon, b.mSubText, b.mUseChronometer, b.getWhenIfShowing(), b.getPriority(), b.getColor(), C0268R.layout.notification_template_custom_big, false, null);
        NotificationCompatImplBase.buildIntoRemoteViews(b.mContext, remoteViews, b.getContentView());
        return remoteViews;
    }

    @TargetApi(16)
    @RequiresApi(16)
    private static void addDecoratedBigStyleToBuilderJellybean(Notification notification, android.support.p000v4.app.NotificationCompat.Builder builder) {
        Notification n = notification;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        Notification notification2 = n;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = b;
        RemoteViews bigContentView = b.getBigContentView();
        RemoteViews innerView = bigContentView == null ? b.getContentView() : bigContentView;
        if (innerView != null) {
            RemoteViews remoteViews = NotificationCompatImplBase.applyStandardTemplateWithActions(b.mContext, b.mContentTitle, b.mContentText, b.mContentInfo, b.mNumber, n.icon, b.mLargeIcon, b.mSubText, b.mUseChronometer, b.getWhenIfShowing(), b.getPriority(), b.getColor(), C0268R.layout.notification_template_custom_big, false, b.mActions);
            NotificationCompatImplBase.buildIntoRemoteViews(b.mContext, remoteViews, innerView);
            n.bigContentView = remoteViews;
        }
    }

    @TargetApi(21)
    @RequiresApi(21)
    private static void addDecoratedHeadsUpToBuilderLollipop(Notification notification, android.support.p000v4.app.NotificationCompat.Builder builder) {
        Notification n = notification;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        Notification notification2 = n;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = b;
        RemoteViews headsUpContentView = b.getHeadsUpContentView();
        RemoteViews headsUp = headsUpContentView;
        RemoteViews innerView = headsUpContentView == null ? b.getContentView() : headsUp;
        if (headsUp != null) {
            RemoteViews remoteViews = NotificationCompatImplBase.applyStandardTemplateWithActions(b.mContext, b.mContentTitle, b.mContentText, b.mContentInfo, b.mNumber, n.icon, b.mLargeIcon, b.mSubText, b.mUseChronometer, b.getWhenIfShowing(), b.getPriority(), b.getColor(), C0268R.layout.notification_template_custom_big, false, b.mActions);
            NotificationCompatImplBase.buildIntoRemoteViews(b.mContext, remoteViews, innerView);
            n.headsUpContentView = remoteViews;
        }
    }

    @TargetApi(21)
    @RequiresApi(21)
    private static void addBigStyleToBuilderLollipop(Notification notification, android.support.p000v4.app.NotificationCompat.Builder builder) {
        RemoteViews bigContentView;
        Notification n = notification;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        Notification notification2 = n;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = b;
        if (b.getBigContentView() == null) {
            bigContentView = b.getContentView();
        } else {
            bigContentView = b.getBigContentView();
        }
        RemoteViews innerView = bigContentView;
        if ((b.mStyle instanceof DecoratedMediaCustomViewStyle) && innerView != null) {
            NotificationCompatImplBase.overrideMediaBigContentView(n, b.mContext, b.mContentTitle, b.mContentText, b.mContentInfo, b.mNumber, b.mLargeIcon, b.mSubText, b.mUseChronometer, b.getWhenIfShowing(), b.getPriority(), 0, b.mActions, false, null, true);
            NotificationCompatImplBase.buildIntoRemoteViews(b.mContext, n.bigContentView, innerView);
            setBackgroundColor(b.mContext, n.bigContentView, b.getColor());
        } else if (b.mStyle instanceof DecoratedCustomViewStyle) {
            addDecoratedBigStyleToBuilderJellybean(n, b);
        }
    }

    private static void setBackgroundColor(Context context, RemoteViews remoteViews, int i) {
        Context context2 = context;
        RemoteViews views = remoteViews;
        int color = i;
        Context context3 = context2;
        RemoteViews remoteViews2 = views;
        int color2 = color;
        if (color == 0) {
            color2 = context2.getResources().getColor(C0268R.color.notification_material_background_media_default_color);
        }
        views.setInt(C0268R.C0270id.status_bar_latest_event_content, "setBackgroundColor", color2);
    }

    @TargetApi(21)
    @RequiresApi(21)
    private static void addHeadsUpToBuilderLollipop(Notification notification, android.support.p000v4.app.NotificationCompat.Builder builder) {
        RemoteViews headsUpContentView;
        Notification n = notification;
        android.support.p000v4.app.NotificationCompat.Builder b = builder;
        Notification notification2 = n;
        android.support.p000v4.app.NotificationCompat.Builder builder2 = b;
        if (b.getHeadsUpContentView() == null) {
            headsUpContentView = b.getContentView();
        } else {
            headsUpContentView = b.getHeadsUpContentView();
        }
        RemoteViews innerView = headsUpContentView;
        if ((b.mStyle instanceof DecoratedMediaCustomViewStyle) && innerView != null) {
            n.headsUpContentView = NotificationCompatImplBase.generateMediaBigView(b.mContext, b.mContentTitle, b.mContentText, b.mContentInfo, b.mNumber, b.mLargeIcon, b.mSubText, b.mUseChronometer, b.getWhenIfShowing(), b.getPriority(), 0, b.mActions, false, null, true);
            NotificationCompatImplBase.buildIntoRemoteViews(b.mContext, n.headsUpContentView, innerView);
            setBackgroundColor(b.mContext, n.headsUpContentView, b.getColor());
        } else if (b.mStyle instanceof DecoratedCustomViewStyle) {
            addDecoratedHeadsUpToBuilderLollipop(n, b);
        }
    }
}
