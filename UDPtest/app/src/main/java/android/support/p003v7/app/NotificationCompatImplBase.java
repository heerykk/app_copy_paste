package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.p000v4.app.NotificationCompat;
import android.support.p000v4.app.NotificationCompatBase.Action;
import android.support.p003v7.appcompat.C0268R;
import android.widget.RemoteViews;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@TargetApi(9)
@RequiresApi(9)
/* renamed from: android.support.v7.app.NotificationCompatImplBase */
class NotificationCompatImplBase {
    private static final int MAX_ACTION_BUTTONS = 3;
    static final int MAX_MEDIA_BUTTONS = 5;
    static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;

    NotificationCompatImplBase() {
    }

    @TargetApi(11)
    @RequiresApi(11)
    public static <T extends Action> RemoteViews overrideContentViewMedia(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i2, List<T> list, int[] iArr, boolean z2, PendingIntent pendingIntent, boolean z3) {
        NotificationBuilderWithBuilderAccessor builder = notificationBuilderWithBuilderAccessor;
        Context context2 = context;
        CharSequence contentTitle = charSequence;
        CharSequence contentText = charSequence2;
        CharSequence contentInfo = charSequence3;
        int number = i;
        Bitmap largeIcon = bitmap;
        CharSequence subText = charSequence4;
        long when = j;
        int priority = i2;
        List<T> actions = list;
        int[] actionsToShowInCompact = iArr;
        PendingIntent cancelButtonIntent = pendingIntent;
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2 = builder;
        Context context3 = context2;
        CharSequence charSequence5 = contentTitle;
        CharSequence charSequence6 = contentText;
        CharSequence charSequence7 = contentInfo;
        int i3 = number;
        Bitmap bitmap2 = largeIcon;
        CharSequence charSequence8 = subText;
        long j2 = when;
        int i4 = priority;
        List<T> list2 = actions;
        int[] iArr2 = actionsToShowInCompact;
        boolean showCancelButton = z2;
        PendingIntent pendingIntent2 = cancelButtonIntent;
        RemoteViews views = generateContentViewMedia(context2, contentTitle, contentText, contentInfo, number, largeIcon, subText, z, when, priority, actions, actionsToShowInCompact, showCancelButton, cancelButtonIntent, z3);
        Builder content = builder.getBuilder().setContent(views);
        if (showCancelButton) {
            Builder ongoing = builder.getBuilder().setOngoing(true);
        }
        return views;
    }

    @TargetApi(11)
    @RequiresApi(11)
    private static <T extends Action> RemoteViews generateContentViewMedia(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i2, List<T> list, int[] iArr, boolean z2, PendingIntent pendingIntent, boolean z3) {
        int i3;
        Context context2 = context;
        CharSequence contentTitle = charSequence;
        CharSequence contentText = charSequence2;
        CharSequence contentInfo = charSequence3;
        int number = i;
        Bitmap largeIcon = bitmap;
        CharSequence subText = charSequence4;
        long when = j;
        int priority = i2;
        List<T> actions = list;
        int[] actionsToShowInCompact = iArr;
        PendingIntent cancelButtonIntent = pendingIntent;
        Context context3 = context2;
        CharSequence charSequence5 = contentTitle;
        CharSequence charSequence6 = contentText;
        CharSequence charSequence7 = contentInfo;
        int i4 = number;
        Bitmap bitmap2 = largeIcon;
        CharSequence charSequence8 = subText;
        long j2 = when;
        int i5 = priority;
        List<T> list2 = actions;
        int[] iArr2 = actionsToShowInCompact;
        boolean showCancelButton = z2;
        PendingIntent pendingIntent2 = cancelButtonIntent;
        RemoteViews view = applyStandardTemplate(context2, contentTitle, contentText, contentInfo, number, 0, largeIcon, subText, z, when, priority, 0, !z3 ? C0268R.layout.notification_template_media : C0268R.layout.notification_template_media_custom, true);
        int numActions = actions.size();
        if (actionsToShowInCompact != null) {
            i3 = Math.min(actionsToShowInCompact.length, 3);
        } else {
            i3 = 0;
        }
        int N = i3;
        view.removeAllViews(C0268R.C0270id.media_actions);
        if (N > 0) {
            int i6 = 0;
            while (i6 < N) {
                if (i6 < numActions) {
                    view.addView(C0268R.C0270id.media_actions, generateMediaActionButton(context2, (Action) actions.get(actionsToShowInCompact[i6])));
                    i6++;
                } else {
                    Object[] objArr = new Object[2];
                    objArr[0] = Integer.valueOf(i6);
                    objArr[1] = Integer.valueOf(numActions - 1);
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", objArr));
                    throw illegalArgumentException;
                }
            }
        }
        if (!showCancelButton) {
            view.setViewVisibility(C0268R.C0270id.end_padder, 0);
            view.setViewVisibility(C0268R.C0270id.cancel_action, 8);
        } else {
            view.setViewVisibility(C0268R.C0270id.end_padder, 8);
            view.setViewVisibility(C0268R.C0270id.cancel_action, 0);
            view.setOnClickPendingIntent(C0268R.C0270id.cancel_action, cancelButtonIntent);
            view.setInt(C0268R.C0270id.cancel_action, "setAlpha", context2.getResources().getInteger(C0268R.integer.cancel_button_image_alpha));
        }
        return view;
    }

    @TargetApi(16)
    @RequiresApi(16)
    public static <T extends Action> void overrideMediaBigContentView(Notification notification, Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i2, int i3, List<T> list, boolean z2, PendingIntent pendingIntent, boolean z3) {
        Notification n = notification;
        Context context2 = context;
        CharSequence contentTitle = charSequence;
        CharSequence contentText = charSequence2;
        CharSequence contentInfo = charSequence3;
        int number = i;
        Bitmap largeIcon = bitmap;
        CharSequence subText = charSequence4;
        long when = j;
        int priority = i2;
        int color = i3;
        List<T> actions = list;
        PendingIntent cancelButtonIntent = pendingIntent;
        Notification notification2 = n;
        Context context3 = context2;
        CharSequence charSequence5 = contentTitle;
        CharSequence charSequence6 = contentText;
        CharSequence charSequence7 = contentInfo;
        int i4 = number;
        Bitmap bitmap2 = largeIcon;
        CharSequence charSequence8 = subText;
        long j2 = when;
        int i5 = priority;
        int i6 = color;
        List<T> list2 = actions;
        boolean showCancelButton = z2;
        PendingIntent pendingIntent2 = cancelButtonIntent;
        n.bigContentView = generateMediaBigView(context2, contentTitle, contentText, contentInfo, number, largeIcon, subText, z, when, priority, color, actions, showCancelButton, cancelButtonIntent, z3);
        if (showCancelButton) {
            n.flags |= 2;
        }
    }

    @TargetApi(11)
    @RequiresApi(11)
    public static <T extends Action> RemoteViews generateMediaBigView(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i2, int i3, List<T> list, boolean z2, PendingIntent pendingIntent, boolean z3) {
        Context context2 = context;
        CharSequence contentTitle = charSequence;
        CharSequence contentText = charSequence2;
        CharSequence contentInfo = charSequence3;
        int number = i;
        Bitmap largeIcon = bitmap;
        CharSequence subText = charSequence4;
        long when = j;
        int priority = i2;
        int color = i3;
        List<T> actions = list;
        PendingIntent cancelButtonIntent = pendingIntent;
        Context context3 = context2;
        CharSequence charSequence5 = contentTitle;
        CharSequence charSequence6 = contentText;
        CharSequence charSequence7 = contentInfo;
        int i4 = number;
        Bitmap bitmap2 = largeIcon;
        CharSequence charSequence8 = subText;
        boolean useChronometer = z;
        long j2 = when;
        int i5 = priority;
        int i6 = color;
        List<T> list2 = actions;
        boolean showCancelButton = z2;
        PendingIntent pendingIntent2 = cancelButtonIntent;
        boolean decoratedCustomView = z3;
        int min = Math.min(actions.size(), 5);
        int actionCount = min;
        RemoteViews applyStandardTemplate = applyStandardTemplate(context2, contentTitle, contentText, contentInfo, number, 0, largeIcon, subText, useChronometer, when, priority, color, getBigMediaLayoutResource(decoratedCustomView, min), false);
        RemoteViews big = applyStandardTemplate;
        applyStandardTemplate.removeAllViews(C0268R.C0270id.media_actions);
        if (min > 0) {
            for (int i7 = 0; i7 < actionCount; i7++) {
                big.addView(C0268R.C0270id.media_actions, generateMediaActionButton(context2, (Action) actions.get(i7)));
            }
        }
        if (!showCancelButton) {
            big.setViewVisibility(C0268R.C0270id.cancel_action, 8);
        } else {
            big.setViewVisibility(C0268R.C0270id.cancel_action, 0);
            big.setInt(C0268R.C0270id.cancel_action, "setAlpha", context2.getResources().getInteger(C0268R.integer.cancel_button_image_alpha));
            big.setOnClickPendingIntent(C0268R.C0270id.cancel_action, cancelButtonIntent);
        }
        return big;
    }

    @TargetApi(11)
    @RequiresApi(11)
    private static RemoteViews generateMediaActionButton(Context context, Action action) {
        Context context2 = context;
        Action action2 = action;
        Context context3 = context2;
        Action action3 = action2;
        boolean tombstone = action2.getActionIntent() == null;
        RemoteViews remoteViews = new RemoteViews(context2.getPackageName(), C0268R.layout.notification_media_action);
        RemoteViews button = remoteViews;
        remoteViews.setImageViewResource(C0268R.C0270id.action0, action2.getIcon());
        if (!tombstone) {
            button.setOnClickPendingIntent(C0268R.C0270id.action0, action2.getActionIntent());
        }
        if (VERSION.SDK_INT >= 15) {
            button.setContentDescription(C0268R.C0270id.action0, action2.getTitle());
        }
        return button;
    }

    @TargetApi(11)
    @RequiresApi(11)
    private static int getBigMediaLayoutResource(boolean z, int i) {
        int actionCount = i;
        boolean decoratedCustomView = z;
        int i2 = actionCount;
        if (actionCount > 3) {
            return !decoratedCustomView ? C0268R.layout.notification_template_big_media : C0268R.layout.notification_template_big_media_custom;
        }
        return !decoratedCustomView ? C0268R.layout.notification_template_big_media_narrow : C0268R.layout.notification_template_big_media_narrow_custom;
    }

    public static RemoteViews applyStandardTemplateWithActions(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, int i2, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i3, int i4, int i5, boolean z2, ArrayList<NotificationCompat.Action> arrayList) {
        Context context2 = context;
        CharSequence contentTitle = charSequence;
        CharSequence contentText = charSequence2;
        CharSequence contentInfo = charSequence3;
        int number = i;
        int smallIcon = i2;
        Bitmap largeIcon = bitmap;
        CharSequence subText = charSequence4;
        long when = j;
        int priority = i3;
        int color = i4;
        int resId = i5;
        ArrayList<NotificationCompat.Action> actions = arrayList;
        Context context3 = context2;
        CharSequence charSequence5 = contentTitle;
        CharSequence charSequence6 = contentText;
        CharSequence charSequence7 = contentInfo;
        int i6 = number;
        int i7 = smallIcon;
        Bitmap bitmap2 = largeIcon;
        CharSequence charSequence8 = subText;
        long j2 = when;
        int i8 = priority;
        int i9 = color;
        int i10 = resId;
        ArrayList<NotificationCompat.Action> arrayList2 = actions;
        RemoteViews applyStandardTemplate = applyStandardTemplate(context2, contentTitle, contentText, contentInfo, number, smallIcon, largeIcon, subText, z, when, priority, color, resId, z2);
        RemoteViews remoteViews = applyStandardTemplate;
        applyStandardTemplate.removeAllViews(C0268R.C0270id.actions);
        boolean actionsVisible = false;
        if (actions != null) {
            int size = actions.size();
            int N = size;
            if (size > 0) {
                actionsVisible = true;
                if (N > 3) {
                    N = 3;
                }
                for (int i11 = 0; i11 < N; i11++) {
                    remoteViews.addView(C0268R.C0270id.actions, generateActionButton(context2, (NotificationCompat.Action) actions.get(i11)));
                }
            }
        }
        int actionVisibility = !actionsVisible ? 8 : 0;
        remoteViews.setViewVisibility(C0268R.C0270id.actions, actionVisibility);
        remoteViews.setViewVisibility(C0268R.C0270id.action_divider, actionVisibility);
        return remoteViews;
    }

    private static RemoteViews generateActionButton(Context context, NotificationCompat.Action action) {
        int actionTombstoneLayoutResource;
        Context context2 = context;
        NotificationCompat.Action action2 = action;
        Context context3 = context2;
        NotificationCompat.Action action3 = action2;
        boolean tombstone = action2.actionIntent == null;
        String packageName = context2.getPackageName();
        if (!tombstone) {
            actionTombstoneLayoutResource = getActionLayoutResource();
        } else {
            actionTombstoneLayoutResource = getActionTombstoneLayoutResource();
        }
        RemoteViews remoteViews = new RemoteViews(packageName, actionTombstoneLayoutResource);
        RemoteViews button = remoteViews;
        remoteViews.setImageViewBitmap(C0268R.C0270id.action_image, createColoredBitmap(context2, action2.getIcon(), context2.getResources().getColor(C0268R.color.notification_action_color_filter)));
        button.setTextViewText(C0268R.C0270id.action_text, action2.title);
        if (!tombstone) {
            button.setOnClickPendingIntent(C0268R.C0270id.action_container, action2.actionIntent);
        }
        if (VERSION.SDK_INT >= 15) {
            button.setContentDescription(C0268R.C0270id.action_container, action2.title);
        }
        return button;
    }

    private static Bitmap createColoredBitmap(Context context, int i, int i2) {
        Context context2 = context;
        int iconId = i;
        int color = i2;
        Context context3 = context2;
        int i3 = iconId;
        int i4 = color;
        return createColoredBitmap(context2, iconId, color, 0);
    }

    private static Bitmap createColoredBitmap(Context context, int i, int i2, int i3) {
        Context context2 = context;
        int iconId = i;
        int color = i2;
        int size = i3;
        Context context3 = context2;
        int i4 = iconId;
        int i5 = color;
        int i6 = size;
        Drawable drawable = context2.getResources().getDrawable(iconId);
        Drawable drawable2 = drawable;
        int width = size != 0 ? size : drawable.getIntrinsicWidth();
        int height = size != 0 ? size : drawable2.getIntrinsicHeight();
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        drawable2.setBounds(0, 0, width, height);
        if (color != 0) {
            Drawable mutate = drawable2.mutate();
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(color, Mode.SRC_IN);
            mutate.setColorFilter(porterDuffColorFilter);
        }
        drawable2.draw(new Canvas(resultBitmap));
        return resultBitmap;
    }

    private static int getActionLayoutResource() {
        return C0268R.layout.notification_action;
    }

    private static int getActionTombstoneLayoutResource() {
        return C0268R.layout.notification_action_tombstone;
    }

    public static RemoteViews applyStandardTemplate(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, int i2, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i3, int i4, int i5, boolean z2) {
        Context context2 = context;
        CharSequence contentTitle = charSequence;
        CharSequence contentText = charSequence2;
        CharSequence contentInfo = charSequence3;
        int number = i;
        int smallIcon = i2;
        Bitmap largeIcon = bitmap;
        CharSequence subText = charSequence4;
        long when = j;
        int priority = i3;
        int color = i4;
        int resId = i5;
        Context context3 = context2;
        CharSequence charSequence5 = contentTitle;
        CharSequence charSequence6 = contentText;
        CharSequence charSequence7 = contentInfo;
        int i6 = number;
        int i7 = smallIcon;
        Bitmap bitmap2 = largeIcon;
        CharSequence charSequence8 = subText;
        boolean useChronometer = z;
        long j2 = when;
        int i8 = priority;
        int i9 = color;
        int i10 = resId;
        boolean fitIn1U = z2;
        Resources res = context2.getResources();
        RemoteViews remoteViews = new RemoteViews(context2.getPackageName(), resId);
        RemoteViews contentView = remoteViews;
        boolean showLine3 = false;
        boolean showLine2 = false;
        boolean minPriority = priority < -1;
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT < 21) {
            if (!minPriority) {
                remoteViews.setInt(C0268R.C0270id.notification_background, "setBackgroundResource", C0268R.C0269drawable.notification_bg);
                contentView.setInt(C0268R.C0270id.icon, "setBackgroundResource", C0268R.C0269drawable.notification_template_icon_bg);
            } else {
                remoteViews.setInt(C0268R.C0270id.notification_background, "setBackgroundResource", C0268R.C0269drawable.notification_bg_low);
                contentView.setInt(C0268R.C0270id.icon, "setBackgroundResource", C0268R.C0269drawable.notification_template_icon_low_bg);
            }
        }
        if (largeIcon != null) {
            if (VERSION.SDK_INT < 16) {
                contentView.setViewVisibility(C0268R.C0270id.icon, 8);
            } else {
                contentView.setViewVisibility(C0268R.C0270id.icon, 0);
                contentView.setImageViewBitmap(C0268R.C0270id.icon, largeIcon);
            }
            if (smallIcon != 0) {
                int dimensionPixelSize = res.getDimensionPixelSize(C0268R.dimen.notification_right_icon_size);
                int backgroundSize = dimensionPixelSize;
                int iconSize = dimensionPixelSize - (res.getDimensionPixelSize(C0268R.dimen.notification_small_icon_background_padding) * 2);
                if (VERSION.SDK_INT < 21) {
                    contentView.setImageViewBitmap(C0268R.C0270id.right_icon, createColoredBitmap(context2, smallIcon, -1));
                } else {
                    contentView.setImageViewBitmap(C0268R.C0270id.right_icon, createIconWithBackground(context2, smallIcon, backgroundSize, iconSize, color));
                }
                contentView.setViewVisibility(C0268R.C0270id.right_icon, 0);
            }
        } else if (smallIcon != 0) {
            contentView.setViewVisibility(C0268R.C0270id.icon, 0);
            if (VERSION.SDK_INT < 21) {
                contentView.setImageViewBitmap(C0268R.C0270id.icon, createColoredBitmap(context2, smallIcon, -1));
            } else {
                int backgroundSize2 = res.getDimensionPixelSize(C0268R.dimen.notification_large_icon_width) - res.getDimensionPixelSize(C0268R.dimen.notification_big_circle_margin);
                int dimensionPixelSize2 = res.getDimensionPixelSize(C0268R.dimen.notification_small_icon_size_as_large);
                int i11 = dimensionPixelSize2;
                contentView.setImageViewBitmap(C0268R.C0270id.icon, createIconWithBackground(context2, smallIcon, backgroundSize2, dimensionPixelSize2, color));
            }
        }
        if (contentTitle != null) {
            contentView.setTextViewText(C0268R.C0270id.title, contentTitle);
        }
        if (contentText != null) {
            contentView.setTextViewText(C0268R.C0270id.text, contentText);
            showLine3 = true;
        }
        boolean hasRightSide = VERSION.SDK_INT < 21 && largeIcon != null;
        if (contentInfo != null) {
            contentView.setTextViewText(C0268R.C0270id.info, contentInfo);
            contentView.setViewVisibility(C0268R.C0270id.info, 0);
            showLine3 = true;
            hasRightSide = true;
        } else if (number <= 0) {
            contentView.setViewVisibility(C0268R.C0270id.info, 8);
        } else {
            if (number <= res.getInteger(C0268R.integer.status_bar_notification_info_maxnum)) {
                contentView.setTextViewText(C0268R.C0270id.info, NumberFormat.getIntegerInstance().format((long) number));
            } else {
                contentView.setTextViewText(C0268R.C0270id.info, res.getString(C0268R.string.status_bar_notification_info_overflow));
            }
            contentView.setViewVisibility(C0268R.C0270id.info, 0);
            showLine3 = true;
            hasRightSide = true;
        }
        if (subText != null && VERSION.SDK_INT >= 16) {
            contentView.setTextViewText(C0268R.C0270id.text, subText);
            if (contentText == null) {
                contentView.setViewVisibility(C0268R.C0270id.text2, 8);
            } else {
                contentView.setTextViewText(C0268R.C0270id.text2, contentText);
                contentView.setViewVisibility(C0268R.C0270id.text2, 0);
                showLine2 = true;
            }
        }
        if (showLine2 && VERSION.SDK_INT >= 16) {
            if (fitIn1U) {
                float dimensionPixelSize3 = (float) res.getDimensionPixelSize(C0268R.dimen.notification_subtext_size);
                float f = dimensionPixelSize3;
                contentView.setTextViewTextSize(C0268R.C0270id.text, 0, dimensionPixelSize3);
            }
            contentView.setViewPadding(C0268R.C0270id.line1, 0, 0, 0, 0);
        }
        if (when != 0) {
            if (useChronometer && VERSION.SDK_INT >= 16) {
                contentView.setViewVisibility(C0268R.C0270id.chronometer, 0);
                contentView.setLong(C0268R.C0270id.chronometer, "setBase", when + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
                contentView.setBoolean(C0268R.C0270id.chronometer, "setStarted", true);
            } else {
                contentView.setViewVisibility(C0268R.C0270id.time, 0);
                contentView.setLong(C0268R.C0270id.time, "setTime", when);
            }
            hasRightSide = true;
        }
        contentView.setViewVisibility(C0268R.C0270id.right_side, !hasRightSide ? 8 : 0);
        contentView.setViewVisibility(C0268R.C0270id.line3, !showLine3 ? 8 : 0);
        return contentView;
    }

    public static Bitmap createIconWithBackground(Context context, int i, int i2, int i3, int i4) {
        Context ctx = context;
        int iconId = i;
        int size = i2;
        int iconSize = i3;
        int color = i4;
        Context context2 = ctx;
        int i5 = iconId;
        int i6 = size;
        int i7 = iconSize;
        int i8 = color;
        Bitmap coloredBitmap = createColoredBitmap(ctx, C0268R.C0269drawable.notification_icon_background, color != 0 ? color : 0, size);
        Canvas canvas = new Canvas(coloredBitmap);
        Canvas canvas2 = canvas;
        Drawable mutate = ctx.getResources().getDrawable(iconId).mutate();
        Drawable icon = mutate;
        mutate.setFilterBitmap(true);
        int i9 = (size - iconSize) / 2;
        int i10 = i9;
        icon.setBounds(i9, i9, iconSize + i9, iconSize + i9);
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(-1, Mode.SRC_ATOP);
        icon.setColorFilter(porterDuffColorFilter);
        icon.draw(canvas2);
        return coloredBitmap;
    }

    public static void buildIntoRemoteViews(Context context, RemoteViews remoteViews, RemoteViews remoteViews2) {
        Context ctx = context;
        RemoteViews outerView = remoteViews;
        RemoteViews innerView = remoteViews2;
        Context context2 = ctx;
        RemoteViews remoteViews3 = outerView;
        RemoteViews remoteViews4 = innerView;
        hideNormalContent(outerView);
        outerView.removeAllViews(C0268R.C0270id.notification_main_column);
        outerView.addView(C0268R.C0270id.notification_main_column, innerView.clone());
        outerView.setViewVisibility(C0268R.C0270id.notification_main_column, 0);
        if (VERSION.SDK_INT >= 21) {
            outerView.setViewPadding(C0268R.C0270id.notification_main_column_container, 0, calculateTopPadding(ctx), 0, 0);
        }
    }

    private static void hideNormalContent(RemoteViews remoteViews) {
        RemoteViews outerView = remoteViews;
        RemoteViews remoteViews2 = outerView;
        outerView.setViewVisibility(C0268R.C0270id.title, 8);
        outerView.setViewVisibility(C0268R.C0270id.text2, 8);
        outerView.setViewVisibility(C0268R.C0270id.text, 8);
    }

    public static int calculateTopPadding(Context context) {
        Context ctx = context;
        Context context2 = ctx;
        int padding = ctx.getResources().getDimensionPixelSize(C0268R.dimen.notification_top_pad);
        int dimensionPixelSize = ctx.getResources().getDimensionPixelSize(C0268R.dimen.notification_top_pad_large_text);
        int i = dimensionPixelSize;
        float f = ctx.getResources().getConfiguration().fontScale;
        float f2 = f;
        float largeFactor = (constrain(f, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
        return Math.round(((1.0f - largeFactor) * ((float) padding)) + (largeFactor * ((float) dimensionPixelSize)));
    }

    public static float constrain(float f, float f2, float f3) {
        float amount = f;
        float low = f2;
        float high = f3;
        float f4 = amount;
        float f5 = low;
        float f6 = high;
        if (amount < low) {
            return low;
        }
        return amount > high ? high : amount;
    }
}
