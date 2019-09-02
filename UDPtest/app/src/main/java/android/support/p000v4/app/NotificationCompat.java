package android.support.p000v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.app.NotificationCompatBase.Action.Factory;
import android.support.p000v4.app.NotificationCompatBase.UnreadConversation;
import android.support.p000v4.app.RemoteInputCompatBase.RemoteInput;
import android.support.p000v4.p002os.BuildCompat;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* renamed from: android.support.v4.app.NotificationCompat */
public class NotificationCompat {
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    static final NotificationCompatImpl IMPL;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    /* renamed from: android.support.v4.app.NotificationCompat$Action */
    public static class Action extends android.support.p000v4.app.NotificationCompatBase.Action {
        @RestrictTo({Scope.LIBRARY_GROUP})
        public static final Factory FACTORY = new Factory() {
            public android.support.p000v4.app.NotificationCompatBase.Action build(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, boolean z) {
                int icon = i;
                CharSequence title = charSequence;
                PendingIntent actionIntent = pendingIntent;
                Bundle extras = bundle;
                RemoteInput[] remoteInputs = remoteInputArr;
                int i2 = icon;
                CharSequence charSequence2 = title;
                PendingIntent pendingIntent2 = actionIntent;
                Bundle bundle2 = extras;
                RemoteInput[] remoteInputArr2 = remoteInputs;
                Action action = new Action(icon, title, actionIntent, extras, (RemoteInput[]) remoteInputs, z);
                return action;
            }

            public Action[] newArray(int i) {
                int length = i;
                int i2 = length;
                return new Action[length];
            }
        };
        public PendingIntent actionIntent;
        public int icon;
        private boolean mAllowGeneratedReplies;
        final Bundle mExtras;
        private final RemoteInput[] mRemoteInputs;
        public CharSequence title;

        /* renamed from: android.support.v4.app.NotificationCompat$Action$Builder */
        public static final class Builder {
            private boolean mAllowGeneratedReplies;
            private final Bundle mExtras;
            private final int mIcon;
            private final PendingIntent mIntent;
            private ArrayList<RemoteInput> mRemoteInputs;
            private final CharSequence mTitle;

            private Builder(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, boolean z) {
                ArrayList arrayList;
                int icon = i;
                CharSequence title = charSequence;
                PendingIntent intent = pendingIntent;
                Bundle extras = bundle;
                RemoteInput[] remoteInputs = remoteInputArr;
                int i2 = icon;
                CharSequence charSequence2 = title;
                PendingIntent pendingIntent2 = intent;
                Bundle bundle2 = extras;
                RemoteInput[] remoteInputArr2 = remoteInputs;
                boolean allowGeneratedReplies = z;
                this.mAllowGeneratedReplies = true;
                this.mIcon = icon;
                this.mTitle = Builder.limitCharSequenceLength(title);
                this.mIntent = intent;
                this.mExtras = extras;
                if (remoteInputs != null) {
                    arrayList = new ArrayList(Arrays.asList(remoteInputs));
                } else {
                    arrayList = null;
                }
                this.mRemoteInputs = arrayList;
                this.mAllowGeneratedReplies = allowGeneratedReplies;
            }

            public Builder(int i, CharSequence charSequence, PendingIntent pendingIntent) {
                int icon = i;
                CharSequence title = charSequence;
                PendingIntent intent = pendingIntent;
                int i2 = icon;
                CharSequence charSequence2 = title;
                PendingIntent pendingIntent2 = intent;
                this(icon, title, intent, new Bundle(), null, true);
            }

            public Builder(Action action) {
                Action action2 = action;
                Action action3 = action2;
                this(action2.icon, action2.title, action2.actionIntent, new Bundle(action2.mExtras), action2.getRemoteInputs(), action2.getAllowGeneratedReplies());
            }

            public Builder addExtras(Bundle bundle) {
                Bundle extras = bundle;
                Bundle bundle2 = extras;
                if (extras != null) {
                    this.mExtras.putAll(extras);
                }
                return this;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public Builder addRemoteInput(RemoteInput remoteInput) {
                RemoteInput remoteInput2 = remoteInput;
                RemoteInput remoteInput3 = remoteInput2;
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList<>();
                }
                boolean add = this.mRemoteInputs.add(remoteInput2);
                return this;
            }

            public Builder setAllowGeneratedReplies(boolean z) {
                this.mAllowGeneratedReplies = z;
                return this;
            }

            public Builder extend(Extender extender) {
                Extender extender2 = extender;
                Extender extender3 = extender2;
                Builder extend = extender2.extend(this);
                return this;
            }

            public Action build() {
                Action action = new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, this.mRemoteInputs == null ? null : (RemoteInput[]) this.mRemoteInputs.toArray(new RemoteInput[this.mRemoteInputs.size()]), this.mAllowGeneratedReplies);
                return action;
            }
        }

        /* renamed from: android.support.v4.app.NotificationCompat$Action$Extender */
        public interface Extender {
            Builder extend(Builder builder);
        }

        /* renamed from: android.support.v4.app.NotificationCompat$Action$WearableExtender */
        public static final class WearableExtender implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags = 1;
            private CharSequence mInProgressLabel;

            public WearableExtender() {
            }

            public WearableExtender(Action action) {
                Action action2 = action;
                Action action3 = action2;
                Bundle bundle = action2.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                Bundle wearableBundle = bundle;
                if (bundle != null) {
                    this.mFlags = wearableBundle.getInt(KEY_FLAGS, 1);
                    this.mInProgressLabel = wearableBundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = wearableBundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = wearableBundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            public Builder extend(Builder builder) {
                Builder builder2 = builder;
                Builder builder3 = builder2;
                Bundle wearableBundle = new Bundle();
                if (this.mFlags != 1) {
                    wearableBundle.putInt(KEY_FLAGS, this.mFlags);
                }
                if (this.mInProgressLabel != null) {
                    wearableBundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
                }
                if (this.mConfirmLabel != null) {
                    wearableBundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
                }
                if (this.mCancelLabel != null) {
                    wearableBundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
                }
                builder2.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
                return builder2;
            }

            public WearableExtender clone() {
                WearableExtender wearableExtender = new WearableExtender();
                WearableExtender that = wearableExtender;
                wearableExtender.mFlags = this.mFlags;
                that.mInProgressLabel = this.mInProgressLabel;
                that.mConfirmLabel = this.mConfirmLabel;
                that.mCancelLabel = this.mCancelLabel;
                return that;
            }

            public WearableExtender setAvailableOffline(boolean z) {
                setFlag(1, z);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            private void setFlag(int i, boolean z) {
                int mask = i;
                int i2 = mask;
                if (!z) {
                    this.mFlags &= mask ^ -1;
                } else {
                    this.mFlags |= mask;
                }
            }

            public WearableExtender setInProgressLabel(CharSequence charSequence) {
                CharSequence label = charSequence;
                CharSequence charSequence2 = label;
                this.mInProgressLabel = label;
                return this;
            }

            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            public WearableExtender setConfirmLabel(CharSequence charSequence) {
                CharSequence label = charSequence;
                CharSequence charSequence2 = label;
                this.mConfirmLabel = label;
                return this;
            }

            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            public WearableExtender setCancelLabel(CharSequence charSequence) {
                CharSequence label = charSequence;
                CharSequence charSequence2 = label;
                this.mCancelLabel = label;
                return this;
            }

            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            public WearableExtender setHintLaunchesActivity(boolean z) {
                setFlag(2, z);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            public WearableExtender setHintDisplayActionInline(boolean z) {
                setFlag(4, z);
                return this;
            }

            public boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }
        }

        public Action(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            int icon2 = i;
            CharSequence title2 = charSequence;
            PendingIntent intent = pendingIntent;
            int i2 = icon2;
            CharSequence charSequence2 = title2;
            PendingIntent pendingIntent2 = intent;
            this(icon2, title2, intent, new Bundle(), null, true);
        }

        Action(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, boolean z) {
            int icon2 = i;
            CharSequence title2 = charSequence;
            PendingIntent intent = pendingIntent;
            Bundle extras = bundle;
            RemoteInput[] remoteInputs = remoteInputArr;
            int i2 = icon2;
            CharSequence charSequence2 = title2;
            PendingIntent pendingIntent2 = intent;
            Bundle bundle2 = extras;
            RemoteInput[] remoteInputArr2 = remoteInputs;
            boolean allowGeneratedReplies = z;
            this.icon = icon2;
            this.title = Builder.limitCharSequenceLength(title2);
            this.actionIntent = intent;
            this.mExtras = extras == null ? new Bundle() : extras;
            this.mRemoteInputs = remoteInputs;
            this.mAllowGeneratedReplies = allowGeneratedReplies;
        }

        public int getIcon() {
            return this.icon;
        }

        public CharSequence getTitle() {
            return this.title;
        }

        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$BigPictureStyle */
    public static class BigPictureStyle extends Style {
        Bitmap mBigLargeIcon;
        boolean mBigLargeIconSet;
        Bitmap mPicture;

        public BigPictureStyle() {
        }

        public BigPictureStyle(Builder builder) {
            Builder builder2 = builder;
            Builder builder3 = builder2;
            setBuilder(builder2);
        }

        public BigPictureStyle setBigContentTitle(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence charSequence) {
            CharSequence cs = charSequence;
            CharSequence charSequence2 = cs;
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public BigPictureStyle bigPicture(Bitmap bitmap) {
            Bitmap b = bitmap;
            Bitmap bitmap2 = b;
            this.mPicture = b;
            return this;
        }

        public BigPictureStyle bigLargeIcon(Bitmap bitmap) {
            Bitmap b = bitmap;
            Bitmap bitmap2 = b;
            this.mBigLargeIcon = b;
            this.mBigLargeIconSet = true;
            return this;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$BigTextStyle */
    public static class BigTextStyle extends Style {
        CharSequence mBigText;

        public BigTextStyle() {
        }

        public BigTextStyle(Builder builder) {
            Builder builder2 = builder;
            Builder builder3 = builder2;
            setBuilder(builder2);
        }

        public BigTextStyle setBigContentTitle(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence charSequence) {
            CharSequence cs = charSequence;
            CharSequence charSequence2 = cs;
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public BigTextStyle bigText(CharSequence charSequence) {
            CharSequence cs = charSequence;
            CharSequence charSequence2 = cs;
            this.mBigText = Builder.limitCharSequenceLength(cs);
            return this;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$Builder */
    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public ArrayList<Action> mActions = new ArrayList<>();
        RemoteViews mBigContentView;
        String mCategory;
        int mColor = 0;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentInfo;
        PendingIntent mContentIntent;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentText;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentTitle;
        RemoteViews mContentView;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Context mContext;
        Bundle mExtras;
        PendingIntent mFullScreenIntent;
        String mGroupKey;
        boolean mGroupSummary;
        RemoteViews mHeadsUpContentView;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Bitmap mLargeIcon;
        boolean mLocalOnly = false;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Notification mNotification = new Notification();
        @RestrictTo({Scope.LIBRARY_GROUP})
        public int mNumber;
        public ArrayList<String> mPeople;
        int mPriority;
        int mProgress;
        boolean mProgressIndeterminate;
        int mProgressMax;
        Notification mPublicVersion;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence[] mRemoteInputHistory;
        boolean mShowWhen = true;
        String mSortKey;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Style mStyle;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mSubText;
        RemoteViews mTickerView;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public boolean mUseChronometer;
        int mVisibility = 0;

        public Builder(Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.mContext = context2;
            this.mNotification.when = System.currentTimeMillis();
            this.mNotification.audioStreamType = -1;
            this.mPriority = 0;
            this.mPeople = new ArrayList<>();
        }

        public Builder setWhen(long j) {
            long when = j;
            long j2 = when;
            this.mNotification.when = when;
            return this;
        }

        public Builder setShowWhen(boolean z) {
            this.mShowWhen = z;
            return this;
        }

        public Builder setUsesChronometer(boolean z) {
            this.mUseChronometer = z;
            return this;
        }

        public Builder setSmallIcon(int i) {
            int icon = i;
            int i2 = icon;
            this.mNotification.icon = icon;
            return this;
        }

        public Builder setSmallIcon(int i, int i2) {
            int icon = i;
            int level = i2;
            int i3 = icon;
            int i4 = level;
            this.mNotification.icon = icon;
            this.mNotification.iconLevel = level;
            return this;
        }

        public Builder setContentTitle(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.mContentTitle = limitCharSequenceLength(title);
            return this;
        }

        public Builder setContentText(CharSequence charSequence) {
            CharSequence text = charSequence;
            CharSequence charSequence2 = text;
            this.mContentText = limitCharSequenceLength(text);
            return this;
        }

        public Builder setSubText(CharSequence charSequence) {
            CharSequence text = charSequence;
            CharSequence charSequence2 = text;
            this.mSubText = limitCharSequenceLength(text);
            return this;
        }

        public Builder setRemoteInputHistory(CharSequence[] charSequenceArr) {
            CharSequence[] text = charSequenceArr;
            CharSequence[] charSequenceArr2 = text;
            this.mRemoteInputHistory = text;
            return this;
        }

        public Builder setNumber(int i) {
            int number = i;
            int i2 = number;
            this.mNumber = number;
            return this;
        }

        public Builder setContentInfo(CharSequence charSequence) {
            CharSequence info = charSequence;
            CharSequence charSequence2 = info;
            this.mContentInfo = limitCharSequenceLength(info);
            return this;
        }

        public Builder setProgress(int i, int i2, boolean z) {
            int max = i;
            int progress = i2;
            int i3 = max;
            int i4 = progress;
            boolean indeterminate = z;
            this.mProgressMax = max;
            this.mProgress = progress;
            this.mProgressIndeterminate = indeterminate;
            return this;
        }

        public Builder setContent(RemoteViews remoteViews) {
            RemoteViews views = remoteViews;
            RemoteViews remoteViews2 = views;
            this.mNotification.contentView = views;
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingIntent) {
            PendingIntent intent = pendingIntent;
            PendingIntent pendingIntent2 = intent;
            this.mContentIntent = intent;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingIntent) {
            PendingIntent intent = pendingIntent;
            PendingIntent pendingIntent2 = intent;
            this.mNotification.deleteIntent = intent;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent pendingIntent, boolean z) {
            PendingIntent intent = pendingIntent;
            PendingIntent pendingIntent2 = intent;
            boolean highPriority = z;
            this.mFullScreenIntent = intent;
            setFlag(128, highPriority);
            return this;
        }

        public Builder setTicker(CharSequence charSequence) {
            CharSequence tickerText = charSequence;
            CharSequence charSequence2 = tickerText;
            this.mNotification.tickerText = limitCharSequenceLength(tickerText);
            return this;
        }

        public Builder setTicker(CharSequence charSequence, RemoteViews remoteViews) {
            CharSequence tickerText = charSequence;
            RemoteViews views = remoteViews;
            CharSequence charSequence2 = tickerText;
            RemoteViews remoteViews2 = views;
            this.mNotification.tickerText = limitCharSequenceLength(tickerText);
            this.mTickerView = views;
            return this;
        }

        public Builder setLargeIcon(Bitmap bitmap) {
            Bitmap icon = bitmap;
            Bitmap bitmap2 = icon;
            this.mLargeIcon = icon;
            return this;
        }

        public Builder setSound(Uri uri) {
            Uri sound = uri;
            Uri uri2 = sound;
            this.mNotification.sound = sound;
            this.mNotification.audioStreamType = -1;
            return this;
        }

        public Builder setSound(Uri uri, int i) {
            Uri sound = uri;
            int streamType = i;
            Uri uri2 = sound;
            int i2 = streamType;
            this.mNotification.sound = sound;
            this.mNotification.audioStreamType = streamType;
            return this;
        }

        public Builder setVibrate(long[] jArr) {
            long[] pattern = jArr;
            long[] jArr2 = pattern;
            this.mNotification.vibrate = pattern;
            return this;
        }

        public Builder setLights(@ColorInt int i, int i2, int i3) {
            int argb = i;
            int onMs = i2;
            int offMs = i3;
            int i4 = argb;
            int i5 = onMs;
            int i6 = offMs;
            this.mNotification.ledARGB = argb;
            this.mNotification.ledOnMS = onMs;
            this.mNotification.ledOffMS = offMs;
            this.mNotification.flags = (this.mNotification.flags & -2) | (!(this.mNotification.ledOnMS != 0 && this.mNotification.ledOffMS != 0) ? 0 : 1);
            return this;
        }

        public Builder setOngoing(boolean z) {
            setFlag(2, z);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean z) {
            setFlag(8, z);
            return this;
        }

        public Builder setAutoCancel(boolean z) {
            setFlag(16, z);
            return this;
        }

        public Builder setLocalOnly(boolean z) {
            this.mLocalOnly = z;
            return this;
        }

        public Builder setCategory(String str) {
            String category = str;
            String str2 = category;
            this.mCategory = category;
            return this;
        }

        public Builder setDefaults(int i) {
            int defaults = i;
            int i2 = defaults;
            this.mNotification.defaults = defaults;
            if ((defaults & 4) != 0) {
                this.mNotification.flags |= 1;
            }
            return this;
        }

        private void setFlag(int i, boolean z) {
            int mask = i;
            int i2 = mask;
            if (!z) {
                this.mNotification.flags &= mask ^ -1;
                return;
            }
            this.mNotification.flags |= mask;
        }

        public Builder setPriority(int i) {
            int pri = i;
            int i2 = pri;
            this.mPriority = pri;
            return this;
        }

        public Builder addPerson(String str) {
            String uri = str;
            String str2 = uri;
            boolean add = this.mPeople.add(uri);
            return this;
        }

        public Builder setGroup(String str) {
            String groupKey = str;
            String str2 = groupKey;
            this.mGroupKey = groupKey;
            return this;
        }

        public Builder setGroupSummary(boolean z) {
            this.mGroupSummary = z;
            return this;
        }

        public Builder setSortKey(String str) {
            String sortKey = str;
            String str2 = sortKey;
            this.mSortKey = sortKey;
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            if (extras != null) {
                if (this.mExtras != null) {
                    this.mExtras.putAll(extras);
                } else {
                    this.mExtras = new Bundle(extras);
                }
            }
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            this.mExtras = extras;
            return this;
        }

        public Bundle getExtras() {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            return this.mExtras;
        }

        public Builder addAction(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            int icon = i;
            CharSequence title = charSequence;
            PendingIntent intent = pendingIntent;
            int i2 = icon;
            CharSequence charSequence2 = title;
            PendingIntent pendingIntent2 = intent;
            boolean add = this.mActions.add(new Action(icon, title, intent));
            return this;
        }

        public Builder addAction(Action action) {
            Action action2 = action;
            Action action3 = action2;
            boolean add = this.mActions.add(action2);
            return this;
        }

        public Builder setStyle(Style style) {
            Style style2 = style;
            Style style3 = style2;
            if (this.mStyle != style2) {
                this.mStyle = style2;
                if (this.mStyle != null) {
                    this.mStyle.setBuilder(this);
                }
            }
            return this;
        }

        public Builder setColor(@ColorInt int i) {
            int argb = i;
            int i2 = argb;
            this.mColor = argb;
            return this;
        }

        public Builder setVisibility(int i) {
            int visibility = i;
            int i2 = visibility;
            this.mVisibility = visibility;
            return this;
        }

        public Builder setPublicVersion(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            this.mPublicVersion = n;
            return this;
        }

        public Builder setCustomContentView(RemoteViews remoteViews) {
            RemoteViews contentView = remoteViews;
            RemoteViews remoteViews2 = contentView;
            this.mContentView = contentView;
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews remoteViews) {
            RemoteViews contentView = remoteViews;
            RemoteViews remoteViews2 = contentView;
            this.mBigContentView = contentView;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews remoteViews) {
            RemoteViews contentView = remoteViews;
            RemoteViews remoteViews2 = contentView;
            this.mHeadsUpContentView = contentView;
            return this;
        }

        public Builder extend(Extender extender) {
            Extender extender2 = extender;
            Extender extender3 = extender2;
            Builder extend = extender2.extend(this);
            return this;
        }

        @Deprecated
        public Notification getNotification() {
            return build();
        }

        public Notification build() {
            return NotificationCompat.IMPL.build(this, getExtender());
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public BuilderExtender getExtender() {
            return new BuilderExtender();
        }

        protected static CharSequence limitCharSequenceLength(CharSequence charSequence) {
            CharSequence cs = charSequence;
            CharSequence cs2 = cs;
            if (cs == null) {
                return cs;
            }
            if (cs.length() > MAX_CHARSEQUENCE_LENGTH) {
                cs2 = cs.subSequence(0, MAX_CHARSEQUENCE_LENGTH);
            }
            return cs2;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getContentView() {
            return this.mContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getBigContentView() {
            return this.mBigContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getHeadsUpContentView() {
            return this.mHeadsUpContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public long getWhenIfShowing() {
            if (!this.mShowWhen) {
                return 0;
            }
            return this.mNotification.when;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getPriority() {
            return this.mPriority;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getColor() {
            return this.mColor;
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence resolveText() {
            return this.mContentText;
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence resolveTitle() {
            return this.mContentTitle;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v4.app.NotificationCompat$BuilderExtender */
    protected static class BuilderExtender {
        protected BuilderExtender() {
        }

        public Notification build(Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            Builder b = builder;
            NotificationBuilderWithBuilderAccessor builder2 = notificationBuilderWithBuilderAccessor;
            Builder builder3 = b;
            NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder2;
            Notification n = builder2.build();
            if (b.mContentView != null) {
                n.contentView = b.mContentView;
            }
            return n;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$CarExtender */
    public static final class CarExtender implements Extender {
        private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String TAG = "CarExtender";
        private int mColor = 0;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        /* renamed from: android.support.v4.app.NotificationCompat$CarExtender$UnreadConversation */
        public static class UnreadConversation extends android.support.p000v4.app.NotificationCompatBase.UnreadConversation {
            static final android.support.p000v4.app.NotificationCompatBase.UnreadConversation.Factory FACTORY = new android.support.p000v4.app.NotificationCompatBase.UnreadConversation.Factory() {
                public UnreadConversation build(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j) {
                    String[] messages = strArr;
                    RemoteInput remoteInput2 = remoteInput;
                    PendingIntent replyPendingIntent = pendingIntent;
                    PendingIntent readPendingIntent = pendingIntent2;
                    String[] participants = strArr2;
                    long latestTimestamp = j;
                    String[] strArr3 = messages;
                    RemoteInput remoteInput3 = remoteInput2;
                    PendingIntent pendingIntent3 = replyPendingIntent;
                    PendingIntent pendingIntent4 = readPendingIntent;
                    String[] strArr4 = participants;
                    long j2 = latestTimestamp;
                    UnreadConversation unreadConversation = new UnreadConversation(messages, (RemoteInput) remoteInput2, replyPendingIntent, readPendingIntent, participants, latestTimestamp);
                    return unreadConversation;
                }
            };
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            /* renamed from: android.support.v4.app.NotificationCompat$CarExtender$UnreadConversation$Builder */
            public static class Builder {
                private long mLatestTimestamp;
                private final List<String> mMessages = new ArrayList();
                private final String mParticipant;
                private PendingIntent mReadPendingIntent;
                private RemoteInput mRemoteInput;
                private PendingIntent mReplyPendingIntent;

                public Builder(String str) {
                    String name = str;
                    String str2 = name;
                    this.mParticipant = name;
                }

                public Builder addMessage(String str) {
                    String message = str;
                    String str2 = message;
                    boolean add = this.mMessages.add(message);
                    return this;
                }

                public Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
                    PendingIntent pendingIntent2 = pendingIntent;
                    RemoteInput remoteInput2 = remoteInput;
                    PendingIntent pendingIntent3 = pendingIntent2;
                    RemoteInput remoteInput3 = remoteInput2;
                    this.mRemoteInput = remoteInput2;
                    this.mReplyPendingIntent = pendingIntent2;
                    return this;
                }

                public Builder setReadPendingIntent(PendingIntent pendingIntent) {
                    PendingIntent pendingIntent2 = pendingIntent;
                    PendingIntent pendingIntent3 = pendingIntent2;
                    this.mReadPendingIntent = pendingIntent2;
                    return this;
                }

                public Builder setLatestTimestamp(long j) {
                    long timestamp = j;
                    long j2 = timestamp;
                    this.mLatestTimestamp = timestamp;
                    return this;
                }

                public UnreadConversation build() {
                    String[] messages = (String[]) this.mMessages.toArray(new String[this.mMessages.size()]);
                    String[] strArr = new String[1];
                    strArr[0] = this.mParticipant;
                    String[] participants = strArr;
                    UnreadConversation unreadConversation = new UnreadConversation(messages, this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, participants, this.mLatestTimestamp);
                    return unreadConversation;
                }
            }

            UnreadConversation(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j) {
                String[] messages = strArr;
                RemoteInput remoteInput2 = remoteInput;
                PendingIntent replyPendingIntent = pendingIntent;
                PendingIntent readPendingIntent = pendingIntent2;
                String[] participants = strArr2;
                long latestTimestamp = j;
                String[] strArr3 = messages;
                RemoteInput remoteInput3 = remoteInput2;
                PendingIntent pendingIntent3 = replyPendingIntent;
                PendingIntent pendingIntent4 = readPendingIntent;
                String[] strArr4 = participants;
                long j2 = latestTimestamp;
                this.mMessages = messages;
                this.mRemoteInput = remoteInput2;
                this.mReadPendingIntent = readPendingIntent;
                this.mReplyPendingIntent = replyPendingIntent;
                this.mParticipants = participants;
                this.mLatestTimestamp = latestTimestamp;
            }

            public String[] getMessages() {
                return this.mMessages;
            }

            public RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }

            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            public String[] getParticipants() {
                return this.mParticipants;
            }

            public String getParticipant() {
                return this.mParticipants.length <= 0 ? null : this.mParticipants[0];
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }
        }

        public CarExtender() {
        }

        public CarExtender(Notification notification) {
            Bundle bundle;
            Notification notif = notification;
            Notification notification2 = notif;
            if (VERSION.SDK_INT >= 21) {
                if (NotificationCompat.getExtras(notif) != null) {
                    bundle = NotificationCompat.getExtras(notif).getBundle(EXTRA_CAR_EXTENDER);
                } else {
                    bundle = null;
                }
                Bundle carBundle = bundle;
                if (carBundle != null) {
                    this.mLargeIcon = (Bitmap) carBundle.getParcelable(EXTRA_LARGE_ICON);
                    this.mColor = carBundle.getInt(EXTRA_COLOR, 0);
                    Bundle b = carBundle.getBundle(EXTRA_CONVERSATION);
                    this.mUnreadConversation = (UnreadConversation) NotificationCompat.IMPL.getUnreadConversationFromBundle(b, UnreadConversation.FACTORY, RemoteInput.FACTORY);
                }
            }
        }

        public Builder extend(Builder builder) {
            Builder builder2 = builder;
            Builder builder3 = builder2;
            if (VERSION.SDK_INT < 21) {
                return builder2;
            }
            Bundle carExtensions = new Bundle();
            if (this.mLargeIcon != null) {
                carExtensions.putParcelable(EXTRA_LARGE_ICON, this.mLargeIcon);
            }
            if (this.mColor != 0) {
                carExtensions.putInt(EXTRA_COLOR, this.mColor);
            }
            if (this.mUnreadConversation != null) {
                carExtensions.putBundle(EXTRA_CONVERSATION, NotificationCompat.IMPL.getBundleForUnreadConversation(this.mUnreadConversation));
            }
            builder2.getExtras().putBundle(EXTRA_CAR_EXTENDER, carExtensions);
            return builder2;
        }

        public CarExtender setColor(@ColorInt int i) {
            int color = i;
            int i2 = color;
            this.mColor = color;
            return this;
        }

        @ColorInt
        public int getColor() {
            return this.mColor;
        }

        public CarExtender setLargeIcon(Bitmap bitmap) {
            Bitmap largeIcon = bitmap;
            Bitmap bitmap2 = largeIcon;
            this.mLargeIcon = largeIcon;
            return this;
        }

        public Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        public CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            UnreadConversation unreadConversation2 = unreadConversation;
            UnreadConversation unreadConversation3 = unreadConversation2;
            this.mUnreadConversation = unreadConversation2;
            return this;
        }

        public UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$Extender */
    public interface Extender {
        Builder extend(Builder builder);
    }

    /* renamed from: android.support.v4.app.NotificationCompat$InboxStyle */
    public static class InboxStyle extends Style {
        ArrayList<CharSequence> mTexts = new ArrayList<>();

        public InboxStyle() {
        }

        public InboxStyle(Builder builder) {
            Builder builder2 = builder;
            Builder builder3 = builder2;
            setBuilder(builder2);
        }

        public InboxStyle setBigContentTitle(CharSequence charSequence) {
            CharSequence title = charSequence;
            CharSequence charSequence2 = title;
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public InboxStyle setSummaryText(CharSequence charSequence) {
            CharSequence cs = charSequence;
            CharSequence charSequence2 = cs;
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public InboxStyle addLine(CharSequence charSequence) {
            CharSequence cs = charSequence;
            CharSequence charSequence2 = cs;
            boolean add = this.mTexts.add(Builder.limitCharSequenceLength(cs));
            return this;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$MessagingStyle */
    public static class MessagingStyle extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence mConversationTitle;
        List<Message> mMessages = new ArrayList();
        CharSequence mUserDisplayName;

        /* renamed from: android.support.v4.app.NotificationCompat$MessagingStyle$Message */
        public static final class Message {
            static final String KEY_DATA_MIME_TYPE = "type";
            static final String KEY_DATA_URI = "uri";
            static final String KEY_SENDER = "sender";
            static final String KEY_TEXT = "text";
            static final String KEY_TIMESTAMP = "time";
            private String mDataMimeType;
            private Uri mDataUri;
            private final CharSequence mSender;
            private final CharSequence mText;
            private final long mTimestamp;

            public Message(CharSequence charSequence, long j, CharSequence charSequence2) {
                CharSequence text = charSequence;
                long timestamp = j;
                CharSequence sender = charSequence2;
                CharSequence charSequence3 = text;
                long j2 = timestamp;
                CharSequence charSequence4 = sender;
                this.mText = text;
                this.mTimestamp = timestamp;
                this.mSender = sender;
            }

            public Message setData(String str, Uri uri) {
                String dataMimeType = str;
                Uri dataUri = uri;
                String str2 = dataMimeType;
                Uri uri2 = dataUri;
                this.mDataMimeType = dataMimeType;
                this.mDataUri = dataUri;
                return this;
            }

            public CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            public CharSequence getSender() {
                return this.mSender;
            }

            public String getDataMimeType() {
                return this.mDataMimeType;
            }

            public Uri getDataUri() {
                return this.mDataUri;
            }

            private Bundle toBundle() {
                Bundle bundle = new Bundle();
                if (this.mText != null) {
                    bundle.putCharSequence(KEY_TEXT, this.mText);
                }
                bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
                if (this.mSender != null) {
                    bundle.putCharSequence(KEY_SENDER, this.mSender);
                }
                if (this.mDataMimeType != null) {
                    bundle.putString(KEY_DATA_MIME_TYPE, this.mDataMimeType);
                }
                if (this.mDataUri != null) {
                    bundle.putParcelable(KEY_DATA_URI, this.mDataUri);
                }
                return bundle;
            }

            static Bundle[] getBundleArrayForMessages(List<Message> list) {
                List<Message> messages = list;
                List<Message> list2 = messages;
                Bundle[] bundles = new Bundle[messages.size()];
                int N = messages.size();
                for (int i = 0; i < N; i++) {
                    bundles[i] = ((Message) messages.get(i)).toBundle();
                }
                return bundles;
            }

            static List<Message> getMessagesFromBundleArray(Parcelable[] parcelableArr) {
                Parcelable[] bundles = parcelableArr;
                Parcelable[] parcelableArr2 = bundles;
                ArrayList arrayList = new ArrayList(bundles.length);
                for (int i = 0; i < bundles.length; i++) {
                    if (bundles[i] instanceof Bundle) {
                        Message messageFromBundle = getMessageFromBundle((Bundle) bundles[i]);
                        Message message = messageFromBundle;
                        if (messageFromBundle != null) {
                            boolean add = arrayList.add(message);
                        }
                    }
                }
                return arrayList;
            }

            static Message getMessageFromBundle(Bundle bundle) {
                Bundle bundle2 = bundle;
                Bundle bundle3 = bundle2;
                try {
                    if (!bundle2.containsKey(KEY_TEXT) || !bundle2.containsKey(KEY_TIMESTAMP)) {
                        return null;
                    }
                    Message message = new Message(bundle2.getCharSequence(KEY_TEXT), bundle2.getLong(KEY_TIMESTAMP), bundle2.getCharSequence(KEY_SENDER));
                    if (bundle2.containsKey(KEY_DATA_MIME_TYPE)) {
                        if (bundle2.containsKey(KEY_DATA_URI)) {
                            Message data = message.setData(bundle2.getString(KEY_DATA_MIME_TYPE), (Uri) bundle2.getParcelable(KEY_DATA_URI));
                        }
                    }
                    return message;
                } catch (ClassCastException e) {
                    ClassCastException classCastException = e;
                    return null;
                }
            }
        }

        MessagingStyle() {
        }

        public MessagingStyle(@NonNull CharSequence charSequence) {
            CharSequence userDisplayName = charSequence;
            CharSequence charSequence2 = userDisplayName;
            this.mUserDisplayName = userDisplayName;
        }

        public CharSequence getUserDisplayName() {
            return this.mUserDisplayName;
        }

        public MessagingStyle setConversationTitle(CharSequence charSequence) {
            CharSequence conversationTitle = charSequence;
            CharSequence charSequence2 = conversationTitle;
            this.mConversationTitle = conversationTitle;
            return this;
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public MessagingStyle addMessage(CharSequence charSequence, long j, CharSequence charSequence2) {
            CharSequence text = charSequence;
            long timestamp = j;
            CharSequence sender = charSequence2;
            CharSequence charSequence3 = text;
            long j2 = timestamp;
            CharSequence charSequence4 = sender;
            boolean add = this.mMessages.add(new Message(text, timestamp, sender));
            if (this.mMessages.size() > 25) {
                Object remove = this.mMessages.remove(0);
            }
            return this;
        }

        public MessagingStyle addMessage(Message message) {
            Message message2 = message;
            Message message3 = message2;
            boolean add = this.mMessages.add(message2);
            if (this.mMessages.size() > 25) {
                Object remove = this.mMessages.remove(0);
            }
            return this;
        }

        public List<Message> getMessages() {
            return this.mMessages;
        }

        public static MessagingStyle extractMessagingStyleFromNotification(Notification notification) {
            MessagingStyle style;
            Notification notif = notification;
            Notification notification2 = notif;
            Bundle extras = NotificationCompat.IMPL.getExtras(notif);
            Bundle extras2 = extras;
            if (extras.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)) {
                try {
                    MessagingStyle messagingStyle = new MessagingStyle();
                    style = messagingStyle;
                    messagingStyle.restoreFromCompatExtras(extras2);
                } catch (ClassCastException e) {
                    ClassCastException classCastException = e;
                    style = null;
                }
            } else {
                style = null;
            }
            return style;
        }

        public void addCompatExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            super.addCompatExtras(extras);
            if (this.mUserDisplayName != null) {
                extras.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUserDisplayName);
            }
            if (this.mConversationTitle != null) {
                extras.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                extras.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, Message.getBundleArrayForMessages(this.mMessages));
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public void restoreFromCompatExtras(Bundle bundle) {
            Bundle extras = bundle;
            Bundle bundle2 = extras;
            this.mMessages.clear();
            this.mUserDisplayName = extras.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            this.mConversationTitle = extras.getString(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            Parcelable[] parcelableArray = extras.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            Parcelable[] parcelables = parcelableArray;
            if (parcelableArray != null) {
                this.mMessages = Message.getMessagesFromBundleArray(parcelables);
            }
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImpl */
    interface NotificationCompatImpl {
        Notification build(Builder builder, BuilderExtender builderExtender);

        Action getAction(Notification notification, int i);

        int getActionCount(Notification notification);

        Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList);

        Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation);

        String getCategory(Notification notification);

        Bundle getExtras(Notification notification);

        String getGroup(Notification notification);

        boolean getLocalOnly(Notification notification);

        ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr);

        String getSortKey(Notification notification);

        UnreadConversation getUnreadConversationFromBundle(Bundle bundle, UnreadConversation.Factory factory, RemoteInput.Factory factory2);

        boolean isGroupSummary(Notification notification);
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImplApi20 */
    static class NotificationCompatImplApi20 extends NotificationCompatImplKitKat {
        NotificationCompatImplApi20() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder b = builder;
            BuilderExtender extender = builderExtender;
            Builder builder2 = b;
            BuilderExtender builderExtender2 = extender;
            android.support.p000v4.app.NotificationCompatApi20.Builder builder3 = new android.support.p000v4.app.NotificationCompatApi20.Builder(b.mContext, b.mNotification, b.resolveTitle(), b.resolveText(), b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mPeople, b.mExtras, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mContentView, b.mBigContentView);
            android.support.p000v4.app.NotificationCompatApi20.Builder builder4 = builder3;
            NotificationCompat.addActionsToBuilder(builder3, b.mActions);
            NotificationCompat.addStyleToBuilderJellybean(builder4, b.mStyle);
            Notification notification = extender.build(b, builder4);
            if (b.mStyle != null) {
                b.mStyle.addCompatExtras(getExtras(notification));
            }
            return notification;
        }

        public Action getAction(Notification notification, int i) {
            Notification n = notification;
            int actionIndex = i;
            Notification notification2 = n;
            int i2 = actionIndex;
            return (Action) NotificationCompatApi20.getAction(n, actionIndex, Action.FACTORY, RemoteInput.FACTORY);
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            ArrayList<Parcelable> parcelables = arrayList;
            ArrayList<Parcelable> arrayList2 = parcelables;
            return (Action[]) NotificationCompatApi20.getActionsFromParcelableArrayList(parcelables, Action.FACTORY, RemoteInput.FACTORY);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr) {
            Action[] actions = actionArr;
            Action[] actionArr2 = actions;
            return NotificationCompatApi20.getParcelableArrayListForActions(actions);
        }

        public boolean getLocalOnly(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatApi20.getLocalOnly(n);
        }

        public String getGroup(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatApi20.getGroup(n);
        }

        public boolean isGroupSummary(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatApi20.isGroupSummary(n);
        }

        public String getSortKey(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatApi20.getSortKey(n);
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImplApi21 */
    static class NotificationCompatImplApi21 extends NotificationCompatImplApi20 {
        NotificationCompatImplApi21() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder b = builder;
            BuilderExtender extender = builderExtender;
            Builder builder2 = b;
            BuilderExtender builderExtender2 = extender;
            android.support.p000v4.app.NotificationCompatApi21.Builder builder3 = new android.support.p000v4.app.NotificationCompatApi21.Builder(b.mContext, b.mNotification, b.resolveTitle(), b.resolveText(), b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mCategory, b.mPeople, b.mExtras, b.mColor, b.mVisibility, b.mPublicVersion, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mContentView, b.mBigContentView, b.mHeadsUpContentView);
            android.support.p000v4.app.NotificationCompatApi21.Builder builder4 = builder3;
            NotificationCompat.addActionsToBuilder(builder3, b.mActions);
            NotificationCompat.addStyleToBuilderJellybean(builder4, b.mStyle);
            Notification notification = extender.build(b, builder4);
            if (b.mStyle != null) {
                b.mStyle.addCompatExtras(getExtras(notification));
            }
            return notification;
        }

        public String getCategory(Notification notification) {
            Notification notif = notification;
            Notification notification2 = notif;
            return NotificationCompatApi21.getCategory(notif);
        }

        public Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation) {
            UnreadConversation uc = unreadConversation;
            UnreadConversation unreadConversation2 = uc;
            return NotificationCompatApi21.getBundleForUnreadConversation(uc);
        }

        public UnreadConversation getUnreadConversationFromBundle(Bundle bundle, UnreadConversation.Factory factory, RemoteInput.Factory factory2) {
            Bundle b = bundle;
            UnreadConversation.Factory factory3 = factory;
            RemoteInput.Factory remoteInputFactory = factory2;
            Bundle bundle2 = b;
            UnreadConversation.Factory factory4 = factory3;
            RemoteInput.Factory factory5 = remoteInputFactory;
            return NotificationCompatApi21.getUnreadConversationFromBundle(b, factory3, remoteInputFactory);
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImplApi24 */
    static class NotificationCompatImplApi24 extends NotificationCompatImplApi21 {
        NotificationCompatImplApi24() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder b = builder;
            BuilderExtender extender = builderExtender;
            Builder builder2 = b;
            BuilderExtender builderExtender2 = extender;
            android.support.p000v4.app.NotificationCompatApi24.Builder builder3 = new android.support.p000v4.app.NotificationCompatApi24.Builder(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mCategory, b.mPeople, b.mExtras, b.mColor, b.mVisibility, b.mPublicVersion, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mRemoteInputHistory, b.mContentView, b.mBigContentView, b.mHeadsUpContentView);
            android.support.p000v4.app.NotificationCompatApi24.Builder builder4 = builder3;
            NotificationCompat.addActionsToBuilder(builder3, b.mActions);
            NotificationCompat.addStyleToBuilderApi24(builder4, b.mStyle);
            Notification notification = extender.build(b, builder4);
            if (b.mStyle != null) {
                b.mStyle.addCompatExtras(getExtras(notification));
            }
            return notification;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImplBase */
    static class NotificationCompatImplBase implements NotificationCompatImpl {
        NotificationCompatImplBase() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder b = builder;
            Builder builder2 = b;
            BuilderExtender builderExtender2 = builderExtender;
            Notification notification = b.mNotification;
            Notification notification2 = notification;
            Notification result = NotificationCompatBase.add(notification, b.mContext, b.resolveTitle(), b.resolveText(), b.mContentIntent, b.mFullScreenIntent);
            if (b.mPriority > 0) {
                result.flags |= 128;
            }
            if (b.mContentView != null) {
                result.contentView = b.mContentView;
            }
            return result;
        }

        public Bundle getExtras(Notification notification) {
            Notification notification2 = notification;
            return null;
        }

        public int getActionCount(Notification notification) {
            Notification notification2 = notification;
            return 0;
        }

        public Action getAction(Notification notification, int i) {
            Notification notification2 = notification;
            int i2 = i;
            return null;
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            ArrayList<Parcelable> arrayList2 = arrayList;
            return null;
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr) {
            Action[] actionArr2 = actionArr;
            return null;
        }

        public String getCategory(Notification notification) {
            Notification notification2 = notification;
            return null;
        }

        public boolean getLocalOnly(Notification notification) {
            Notification notification2 = notification;
            return false;
        }

        public String getGroup(Notification notification) {
            Notification notification2 = notification;
            return null;
        }

        public boolean isGroupSummary(Notification notification) {
            Notification notification2 = notification;
            return false;
        }

        public String getSortKey(Notification notification) {
            Notification notification2 = notification;
            return null;
        }

        public Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation) {
            UnreadConversation unreadConversation2 = unreadConversation;
            return null;
        }

        public UnreadConversation getUnreadConversationFromBundle(Bundle bundle, UnreadConversation.Factory factory, RemoteInput.Factory factory2) {
            Bundle bundle2 = bundle;
            UnreadConversation.Factory factory3 = factory;
            RemoteInput.Factory factory4 = factory2;
            return null;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImplHoneycomb */
    static class NotificationCompatImplHoneycomb extends NotificationCompatImplBase {
        NotificationCompatImplHoneycomb() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder b = builder;
            Builder builder2 = b;
            BuilderExtender builderExtender2 = builderExtender;
            Notification notification = NotificationCompatHoneycomb.add(b.mContext, b.mNotification, b.resolveTitle(), b.resolveText(), b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon);
            if (b.mContentView != null) {
                notification.contentView = b.mContentView;
            }
            return notification;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImplIceCreamSandwich */
    static class NotificationCompatImplIceCreamSandwich extends NotificationCompatImplBase {
        NotificationCompatImplIceCreamSandwich() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder b = builder;
            BuilderExtender extender = builderExtender;
            Builder builder2 = b;
            BuilderExtender builderExtender2 = extender;
            android.support.p000v4.app.NotificationCompatIceCreamSandwich.Builder builder3 = new android.support.p000v4.app.NotificationCompatIceCreamSandwich.Builder(b.mContext, b.mNotification, b.resolveTitle(), b.resolveText(), b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate);
            return extender.build(b, builder3);
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImplJellybean */
    static class NotificationCompatImplJellybean extends NotificationCompatImplBase {
        NotificationCompatImplJellybean() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder b = builder;
            BuilderExtender extender = builderExtender;
            Builder builder2 = b;
            BuilderExtender builderExtender2 = extender;
            android.support.p000v4.app.NotificationCompatJellybean.Builder builder3 = new android.support.p000v4.app.NotificationCompatJellybean.Builder(b.mContext, b.mNotification, b.resolveTitle(), b.resolveText(), b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mExtras, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mContentView, b.mBigContentView);
            android.support.p000v4.app.NotificationCompatJellybean.Builder builder4 = builder3;
            NotificationCompat.addActionsToBuilder(builder3, b.mActions);
            NotificationCompat.addStyleToBuilderJellybean(builder4, b.mStyle);
            Notification notification = extender.build(b, builder4);
            if (b.mStyle != null) {
                Bundle extras = getExtras(notification);
                Bundle extras2 = extras;
                if (extras != null) {
                    b.mStyle.addCompatExtras(extras2);
                }
            }
            return notification;
        }

        public Bundle getExtras(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatJellybean.getExtras(n);
        }

        public int getActionCount(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatJellybean.getActionCount(n);
        }

        public Action getAction(Notification notification, int i) {
            Notification n = notification;
            int actionIndex = i;
            Notification notification2 = n;
            int i2 = actionIndex;
            return (Action) NotificationCompatJellybean.getAction(n, actionIndex, Action.FACTORY, RemoteInput.FACTORY);
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            ArrayList<Parcelable> parcelables = arrayList;
            ArrayList<Parcelable> arrayList2 = parcelables;
            return (Action[]) NotificationCompatJellybean.getActionsFromParcelableArrayList(parcelables, Action.FACTORY, RemoteInput.FACTORY);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr) {
            Action[] actions = actionArr;
            Action[] actionArr2 = actions;
            return NotificationCompatJellybean.getParcelableArrayListForActions(actions);
        }

        public boolean getLocalOnly(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatJellybean.getLocalOnly(n);
        }

        public String getGroup(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatJellybean.getGroup(n);
        }

        public boolean isGroupSummary(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatJellybean.isGroupSummary(n);
        }

        public String getSortKey(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatJellybean.getSortKey(n);
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$NotificationCompatImplKitKat */
    static class NotificationCompatImplKitKat extends NotificationCompatImplJellybean {
        NotificationCompatImplKitKat() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder b = builder;
            BuilderExtender extender = builderExtender;
            Builder builder2 = b;
            BuilderExtender builderExtender2 = extender;
            android.support.p000v4.app.NotificationCompatKitKat.Builder builder3 = new android.support.p000v4.app.NotificationCompatKitKat.Builder(b.mContext, b.mNotification, b.resolveTitle(), b.resolveText(), b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mShowWhen, b.mUseChronometer, b.mPriority, b.mSubText, b.mLocalOnly, b.mPeople, b.mExtras, b.mGroupKey, b.mGroupSummary, b.mSortKey, b.mContentView, b.mBigContentView);
            android.support.p000v4.app.NotificationCompatKitKat.Builder builder4 = builder3;
            NotificationCompat.addActionsToBuilder(builder3, b.mActions);
            NotificationCompat.addStyleToBuilderJellybean(builder4, b.mStyle);
            return extender.build(b, builder4);
        }

        public Bundle getExtras(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatKitKat.getExtras(n);
        }

        public int getActionCount(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatKitKat.getActionCount(n);
        }

        public Action getAction(Notification notification, int i) {
            Notification n = notification;
            int actionIndex = i;
            Notification notification2 = n;
            int i2 = actionIndex;
            return (Action) NotificationCompatKitKat.getAction(n, actionIndex, Action.FACTORY, RemoteInput.FACTORY);
        }

        public boolean getLocalOnly(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatKitKat.getLocalOnly(n);
        }

        public String getGroup(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatKitKat.getGroup(n);
        }

        public boolean isGroupSummary(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatKitKat.isGroupSummary(n);
        }

        public String getSortKey(Notification notification) {
            Notification n = notification;
            Notification notification2 = n;
            return NotificationCompatKitKat.getSortKey(n);
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$Style */
    public static abstract class Style {
        CharSequence mBigContentTitle;
        Builder mBuilder;
        CharSequence mSummaryText;
        boolean mSummaryTextSet = false;

        public Style() {
        }

        public void setBuilder(Builder builder) {
            Builder builder2 = builder;
            Builder builder3 = builder2;
            if (this.mBuilder != builder2) {
                this.mBuilder = builder2;
                if (this.mBuilder != null) {
                    Builder style = this.mBuilder.setStyle(this);
                }
            }
        }

        public Notification build() {
            Notification notification = null;
            if (this.mBuilder != null) {
                notification = this.mBuilder.build();
            }
            return notification;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void addCompatExtras(Bundle bundle) {
            Bundle bundle2 = bundle;
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public void restoreFromCompatExtras(Bundle bundle) {
            Bundle bundle2 = bundle;
        }
    }

    /* renamed from: android.support.v4.app.NotificationCompat$WearableExtender */
    public static final class WearableExtender implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions = new ArrayList<>();
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex = -1;
        private int mContentIcon;
        private int mContentIconGravity = 8388613;
        private int mCustomContentHeight;
        private int mCustomSizePreset = 0;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags = 1;
        private int mGravity = 80;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages = new ArrayList<>();

        public WearableExtender() {
        }

        public WearableExtender(Notification notification) {
            Notification notif = notification;
            Notification notification2 = notif;
            Bundle extras = NotificationCompat.getExtras(notif);
            Bundle wearableBundle = extras == null ? null : extras.getBundle(EXTRA_WEARABLE_EXTENSIONS);
            if (wearableBundle != null) {
                Action[] actionsFromParcelableArrayList = NotificationCompat.IMPL.getActionsFromParcelableArrayList(wearableBundle.getParcelableArrayList(KEY_ACTIONS));
                Action[] actions = actionsFromParcelableArrayList;
                if (actionsFromParcelableArrayList != null) {
                    boolean addAll = Collections.addAll(this.mActions, actions);
                }
                this.mFlags = wearableBundle.getInt(KEY_FLAGS, 1);
                this.mDisplayIntent = (PendingIntent) wearableBundle.getParcelable(KEY_DISPLAY_INTENT);
                Notification[] notificationArrayFromBundle = NotificationCompat.getNotificationArrayFromBundle(wearableBundle, KEY_PAGES);
                Notification[] pages = notificationArrayFromBundle;
                if (notificationArrayFromBundle != null) {
                    boolean addAll2 = Collections.addAll(this.mPages, pages);
                }
                this.mBackground = (Bitmap) wearableBundle.getParcelable(KEY_BACKGROUND);
                this.mContentIcon = wearableBundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = wearableBundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = wearableBundle.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = wearableBundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = wearableBundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = wearableBundle.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = wearableBundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = wearableBundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = wearableBundle.getString(KEY_BRIDGE_TAG);
            }
        }

        public Builder extend(Builder builder) {
            Builder builder2 = builder;
            Builder builder3 = builder2;
            Bundle wearableBundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                wearableBundle.putParcelableArrayList(KEY_ACTIONS, NotificationCompat.IMPL.getParcelableArrayListForActions((Action[]) this.mActions.toArray(new Action[this.mActions.size()])));
            }
            if (this.mFlags != 1) {
                wearableBundle.putInt(KEY_FLAGS, this.mFlags);
            }
            if (this.mDisplayIntent != null) {
                wearableBundle.putParcelable(KEY_DISPLAY_INTENT, this.mDisplayIntent);
            }
            if (!this.mPages.isEmpty()) {
                wearableBundle.putParcelableArray(KEY_PAGES, (Parcelable[]) this.mPages.toArray(new Notification[this.mPages.size()]));
            }
            if (this.mBackground != null) {
                wearableBundle.putParcelable(KEY_BACKGROUND, this.mBackground);
            }
            if (this.mContentIcon != 0) {
                wearableBundle.putInt(KEY_CONTENT_ICON, this.mContentIcon);
            }
            if (this.mContentIconGravity != 8388613) {
                wearableBundle.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity);
            }
            if (this.mContentActionIndex != -1) {
                wearableBundle.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex);
            }
            if (this.mCustomSizePreset != 0) {
                wearableBundle.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset);
            }
            if (this.mCustomContentHeight != 0) {
                wearableBundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight);
            }
            if (this.mGravity != 80) {
                wearableBundle.putInt(KEY_GRAVITY, this.mGravity);
            }
            if (this.mHintScreenTimeout != 0) {
                wearableBundle.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout);
            }
            if (this.mDismissalId != null) {
                wearableBundle.putString(KEY_DISMISSAL_ID, this.mDismissalId);
            }
            if (this.mBridgeTag != null) {
                wearableBundle.putString(KEY_BRIDGE_TAG, this.mBridgeTag);
            }
            builder2.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
            return builder2;
        }

        public WearableExtender clone() {
            WearableExtender wearableExtender = new WearableExtender();
            WearableExtender that = wearableExtender;
            wearableExtender.mActions = new ArrayList<>(this.mActions);
            that.mFlags = this.mFlags;
            that.mDisplayIntent = this.mDisplayIntent;
            that.mPages = new ArrayList<>(this.mPages);
            that.mBackground = this.mBackground;
            that.mContentIcon = this.mContentIcon;
            that.mContentIconGravity = this.mContentIconGravity;
            that.mContentActionIndex = this.mContentActionIndex;
            that.mCustomSizePreset = this.mCustomSizePreset;
            that.mCustomContentHeight = this.mCustomContentHeight;
            that.mGravity = this.mGravity;
            that.mHintScreenTimeout = this.mHintScreenTimeout;
            that.mDismissalId = this.mDismissalId;
            that.mBridgeTag = this.mBridgeTag;
            return that;
        }

        public WearableExtender addAction(Action action) {
            Action action2 = action;
            Action action3 = action2;
            boolean add = this.mActions.add(action2);
            return this;
        }

        public WearableExtender addActions(List<Action> list) {
            List<Action> actions = list;
            List<Action> list2 = actions;
            boolean addAll = this.mActions.addAll(actions);
            return this;
        }

        public WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        public List<Action> getActions() {
            return this.mActions;
        }

        public WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
            PendingIntent intent = pendingIntent;
            PendingIntent pendingIntent2 = intent;
            this.mDisplayIntent = intent;
            return this;
        }

        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        public WearableExtender addPage(Notification notification) {
            Notification page = notification;
            Notification notification2 = page;
            boolean add = this.mPages.add(page);
            return this;
        }

        public WearableExtender addPages(List<Notification> list) {
            List<Notification> pages = list;
            List<Notification> list2 = pages;
            boolean addAll = this.mPages.addAll(pages);
            return this;
        }

        public WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        public List<Notification> getPages() {
            return this.mPages;
        }

        public WearableExtender setBackground(Bitmap bitmap) {
            Bitmap background = bitmap;
            Bitmap bitmap2 = background;
            this.mBackground = background;
            return this;
        }

        public Bitmap getBackground() {
            return this.mBackground;
        }

        public WearableExtender setContentIcon(int i) {
            int icon = i;
            int i2 = icon;
            this.mContentIcon = icon;
            return this;
        }

        public int getContentIcon() {
            return this.mContentIcon;
        }

        public WearableExtender setContentIconGravity(int i) {
            int contentIconGravity = i;
            int i2 = contentIconGravity;
            this.mContentIconGravity = contentIconGravity;
            return this;
        }

        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public WearableExtender setContentAction(int i) {
            int actionIndex = i;
            int i2 = actionIndex;
            this.mContentActionIndex = actionIndex;
            return this;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        public WearableExtender setGravity(int i) {
            int gravity = i;
            int i2 = gravity;
            this.mGravity = gravity;
            return this;
        }

        public int getGravity() {
            return this.mGravity;
        }

        public WearableExtender setCustomSizePreset(int i) {
            int sizePreset = i;
            int i2 = sizePreset;
            this.mCustomSizePreset = sizePreset;
            return this;
        }

        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        public WearableExtender setCustomContentHeight(int i) {
            int height = i;
            int i2 = height;
            this.mCustomContentHeight = height;
            return this;
        }

        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public WearableExtender setStartScrollBottom(boolean z) {
            setFlag(8, z);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean z) {
            setFlag(1, z);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        public WearableExtender setHintHideIcon(boolean z) {
            setFlag(2, z);
            return this;
        }

        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        public WearableExtender setHintShowBackgroundOnly(boolean z) {
            setFlag(4, z);
            return this;
        }

        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        public WearableExtender setHintAvoidBackgroundClipping(boolean z) {
            setFlag(16, z);
            return this;
        }

        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        public WearableExtender setHintScreenTimeout(int i) {
            int timeout = i;
            int i2 = timeout;
            this.mHintScreenTimeout = timeout;
            return this;
        }

        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        public WearableExtender setHintAmbientBigPicture(boolean z) {
            setFlag(32, z);
            return this;
        }

        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        public WearableExtender setHintContentIntentLaunchesActivity(boolean z) {
            setFlag(64, z);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        public WearableExtender setDismissalId(String str) {
            String dismissalId = str;
            String str2 = dismissalId;
            this.mDismissalId = dismissalId;
            return this;
        }

        public String getDismissalId() {
            return this.mDismissalId;
        }

        public WearableExtender setBridgeTag(String str) {
            String bridgeTag = str;
            String str2 = bridgeTag;
            this.mBridgeTag = bridgeTag;
            return this;
        }

        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        private void setFlag(int i, boolean z) {
            int mask = i;
            int i2 = mask;
            if (!z) {
                this.mFlags &= mask ^ -1;
            } else {
                this.mFlags |= mask;
            }
        }
    }

    public NotificationCompat() {
    }

    static void addActionsToBuilder(NotificationBuilderWithActions notificationBuilderWithActions, ArrayList<Action> arrayList) {
        NotificationBuilderWithActions builder = notificationBuilderWithActions;
        ArrayList<Action> actions = arrayList;
        NotificationBuilderWithActions notificationBuilderWithActions2 = builder;
        ArrayList<Action> arrayList2 = actions;
        Iterator it = actions.iterator();
        while (it.hasNext()) {
            builder.addAction((Action) it.next());
        }
    }

    static void addStyleToBuilderJellybean(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, Style style) {
        NotificationBuilderWithBuilderAccessor builder = notificationBuilderWithBuilderAccessor;
        Style style2 = style;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder;
        Style style3 = style2;
        if (style2 != null) {
            if (style2 instanceof BigTextStyle) {
                BigTextStyle bigTextStyle = (BigTextStyle) style2;
                NotificationCompatJellybean.addBigTextStyle(builder, bigTextStyle.mBigContentTitle, bigTextStyle.mSummaryTextSet, bigTextStyle.mSummaryText, bigTextStyle.mBigText);
            } else if (style2 instanceof InboxStyle) {
                InboxStyle inboxStyle = (InboxStyle) style2;
                NotificationCompatJellybean.addInboxStyle(builder, inboxStyle.mBigContentTitle, inboxStyle.mSummaryTextSet, inboxStyle.mSummaryText, inboxStyle.mTexts);
            } else if (style2 instanceof BigPictureStyle) {
                BigPictureStyle bigPictureStyle = (BigPictureStyle) style2;
                NotificationCompatJellybean.addBigPictureStyle(builder, bigPictureStyle.mBigContentTitle, bigPictureStyle.mSummaryTextSet, bigPictureStyle.mSummaryText, bigPictureStyle.mPicture, bigPictureStyle.mBigLargeIcon, bigPictureStyle.mBigLargeIconSet);
            }
        }
    }

    static void addStyleToBuilderApi24(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, Style style) {
        NotificationBuilderWithBuilderAccessor builder = notificationBuilderWithBuilderAccessor;
        Style style2 = style;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder;
        Style style3 = style2;
        if (style2 != null) {
            if (!(style2 instanceof MessagingStyle)) {
                addStyleToBuilderJellybean(builder, style2);
                return;
            }
            MessagingStyle messagingStyle = (MessagingStyle) style2;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            for (Message message : messagingStyle.mMessages) {
                boolean add = arrayList.add(message.getText());
                boolean add2 = arrayList2.add(Long.valueOf(message.getTimestamp()));
                boolean add3 = arrayList3.add(message.getSender());
                boolean add4 = arrayList4.add(message.getDataMimeType());
                boolean add5 = arrayList5.add(message.getDataUri());
            }
            NotificationCompatApi24.addMessagingStyle(builder, messagingStyle.mUserDisplayName, messagingStyle.mConversationTitle, arrayList, arrayList2, arrayList3, arrayList4, arrayList5);
        }
    }

    static {
        if (BuildCompat.isAtLeastN()) {
            IMPL = new NotificationCompatImplApi24();
        } else if (VERSION.SDK_INT >= 21) {
            IMPL = new NotificationCompatImplApi21();
        } else if (VERSION.SDK_INT >= 20) {
            IMPL = new NotificationCompatImplApi20();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new NotificationCompatImplKitKat();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new NotificationCompatImplJellybean();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new NotificationCompatImplIceCreamSandwich();
        } else if (VERSION.SDK_INT < 11) {
            IMPL = new NotificationCompatImplBase();
        } else {
            IMPL = new NotificationCompatImplHoneycomb();
        }
    }

    static Notification[] getNotificationArrayFromBundle(Bundle bundle, String str) {
        Bundle bundle2 = bundle;
        String key = str;
        Bundle bundle3 = bundle2;
        String str2 = key;
        Parcelable[] parcelableArray = bundle2.getParcelableArray(key);
        Parcelable[] array = parcelableArray;
        if ((parcelableArray instanceof Notification[]) || array == null) {
            return (Notification[]) array;
        }
        Notification[] typedArray = new Notification[array.length];
        for (int i = 0; i < array.length; i++) {
            typedArray[i] = (Notification) array[i];
        }
        bundle2.putParcelableArray(key, typedArray);
        return typedArray;
    }

    public static Bundle getExtras(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return IMPL.getExtras(notif);
    }

    public static int getActionCount(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return IMPL.getActionCount(notif);
    }

    public static Action getAction(Notification notification, int i) {
        Notification notif = notification;
        int actionIndex = i;
        Notification notification2 = notif;
        int i2 = actionIndex;
        return IMPL.getAction(notif, actionIndex);
    }

    public static String getCategory(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return IMPL.getCategory(notif);
    }

    public static boolean getLocalOnly(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return IMPL.getLocalOnly(notif);
    }

    public static String getGroup(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return IMPL.getGroup(notif);
    }

    public static boolean isGroupSummary(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return IMPL.isGroupSummary(notif);
    }

    public static String getSortKey(Notification notification) {
        Notification notif = notification;
        Notification notification2 = notif;
        return IMPL.getSortKey(notif);
    }
}
