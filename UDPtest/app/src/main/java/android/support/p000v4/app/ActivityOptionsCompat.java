package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.util.Pair;
import android.view.View;

/* renamed from: android.support.v4.app.ActivityOptionsCompat */
public class ActivityOptionsCompat {
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";

    @RequiresApi(21)
    @TargetApi(21)
    /* renamed from: android.support.v4.app.ActivityOptionsCompat$ActivityOptionsImpl21 */
    private static class ActivityOptionsImpl21 extends ActivityOptionsCompat {
        private final ActivityOptionsCompat21 mImpl;

        ActivityOptionsImpl21(ActivityOptionsCompat21 activityOptionsCompat21) {
            ActivityOptionsCompat21 impl = activityOptionsCompat21;
            ActivityOptionsCompat21 activityOptionsCompat212 = impl;
            this.mImpl = impl;
        }

        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat activityOptionsCompat) {
            ActivityOptionsCompat otherOptions = activityOptionsCompat;
            ActivityOptionsCompat activityOptionsCompat2 = otherOptions;
            if (otherOptions instanceof ActivityOptionsImpl21) {
                this.mImpl.update(((ActivityOptionsImpl21) otherOptions).mImpl);
            }
        }
    }

    @RequiresApi(23)
    @TargetApi(23)
    /* renamed from: android.support.v4.app.ActivityOptionsCompat$ActivityOptionsImpl23 */
    private static class ActivityOptionsImpl23 extends ActivityOptionsCompat {
        private final ActivityOptionsCompat23 mImpl;

        ActivityOptionsImpl23(ActivityOptionsCompat23 activityOptionsCompat23) {
            ActivityOptionsCompat23 impl = activityOptionsCompat23;
            ActivityOptionsCompat23 activityOptionsCompat232 = impl;
            this.mImpl = impl;
        }

        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat activityOptionsCompat) {
            ActivityOptionsCompat otherOptions = activityOptionsCompat;
            ActivityOptionsCompat activityOptionsCompat2 = otherOptions;
            if (otherOptions instanceof ActivityOptionsImpl23) {
                this.mImpl.update(((ActivityOptionsImpl23) otherOptions).mImpl);
            }
        }

        public void requestUsageTimeReport(PendingIntent pendingIntent) {
            PendingIntent receiver = pendingIntent;
            PendingIntent pendingIntent2 = receiver;
            this.mImpl.requestUsageTimeReport(receiver);
        }
    }

    @RequiresApi(24)
    @TargetApi(24)
    /* renamed from: android.support.v4.app.ActivityOptionsCompat$ActivityOptionsImpl24 */
    private static class ActivityOptionsImpl24 extends ActivityOptionsCompat {
        private final ActivityOptionsCompat24 mImpl;

        ActivityOptionsImpl24(ActivityOptionsCompat24 activityOptionsCompat24) {
            ActivityOptionsCompat24 impl = activityOptionsCompat24;
            ActivityOptionsCompat24 activityOptionsCompat242 = impl;
            this.mImpl = impl;
        }

        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat activityOptionsCompat) {
            ActivityOptionsCompat otherOptions = activityOptionsCompat;
            ActivityOptionsCompat activityOptionsCompat2 = otherOptions;
            if (otherOptions instanceof ActivityOptionsImpl24) {
                this.mImpl.update(((ActivityOptionsImpl24) otherOptions).mImpl);
            }
        }

        public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
            Rect screenSpacePixelRect = rect;
            Rect rect2 = screenSpacePixelRect;
            return new ActivityOptionsImpl24(this.mImpl.setLaunchBounds(screenSpacePixelRect));
        }

        public Rect getLaunchBounds() {
            return this.mImpl.getLaunchBounds();
        }

        public void requestUsageTimeReport(PendingIntent pendingIntent) {
            PendingIntent receiver = pendingIntent;
            PendingIntent pendingIntent2 = receiver;
            this.mImpl.requestUsageTimeReport(receiver);
        }
    }

    @RequiresApi(16)
    @TargetApi(16)
    /* renamed from: android.support.v4.app.ActivityOptionsCompat$ActivityOptionsImplJB */
    private static class ActivityOptionsImplJB extends ActivityOptionsCompat {
        private final ActivityOptionsCompatJB mImpl;

        ActivityOptionsImplJB(ActivityOptionsCompatJB activityOptionsCompatJB) {
            ActivityOptionsCompatJB impl = activityOptionsCompatJB;
            ActivityOptionsCompatJB activityOptionsCompatJB2 = impl;
            this.mImpl = impl;
        }

        public Bundle toBundle() {
            return this.mImpl.toBundle();
        }

        public void update(ActivityOptionsCompat activityOptionsCompat) {
            ActivityOptionsCompat otherOptions = activityOptionsCompat;
            ActivityOptionsCompat activityOptionsCompat2 = otherOptions;
            if (otherOptions instanceof ActivityOptionsImplJB) {
                this.mImpl.update(((ActivityOptionsImplJB) otherOptions).mImpl);
            }
        }
    }

    public static ActivityOptionsCompat makeCustomAnimation(Context context, int i, int i2) {
        Context context2 = context;
        int enterResId = i;
        int exitResId = i2;
        Context context3 = context2;
        int i3 = enterResId;
        int i4 = exitResId;
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeCustomAnimation(context2, enterResId, exitResId));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeCustomAnimation(context2, enterResId, exitResId));
        }
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeCustomAnimation(context2, enterResId, exitResId));
        }
        if (VERSION.SDK_INT < 16) {
            return new ActivityOptionsCompat();
        }
        return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeCustomAnimation(context2, enterResId, exitResId));
    }

    public static ActivityOptionsCompat makeScaleUpAnimation(View view, int i, int i2, int i3, int i4) {
        View source = view;
        int startX = i;
        int startY = i2;
        int startWidth = i3;
        int startHeight = i4;
        View view2 = source;
        int i5 = startX;
        int i6 = startY;
        int i7 = startWidth;
        int i8 = startHeight;
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
        }
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
        }
        if (VERSION.SDK_INT < 16) {
            return new ActivityOptionsCompat();
        }
        return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
    }

    public static ActivityOptionsCompat makeClipRevealAnimation(View view, int i, int i2, int i3, int i4) {
        View source = view;
        int startX = i;
        int startY = i2;
        int width = i3;
        int height = i4;
        View view2 = source;
        int i5 = startX;
        int i6 = startY;
        int i7 = width;
        int i8 = height;
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeClipRevealAnimation(source, startX, startY, width, height));
        }
        if (VERSION.SDK_INT < 23) {
            return new ActivityOptionsCompat();
        }
        return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeClipRevealAnimation(source, startX, startY, width, height));
    }

    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int i, int i2) {
        View source = view;
        Bitmap thumbnail = bitmap;
        int startX = i;
        int startY = i2;
        View view2 = source;
        Bitmap bitmap2 = thumbnail;
        int i3 = startX;
        int i4 = startY;
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
        }
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
        }
        if (VERSION.SDK_INT < 16) {
            return new ActivityOptionsCompat();
        }
        return new ActivityOptionsImplJB(ActivityOptionsCompatJB.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity, View view, String str) {
        Activity activity2 = activity;
        View sharedElement = view;
        String sharedElementName = str;
        Activity activity3 = activity2;
        View view2 = sharedElement;
        String str2 = sharedElementName;
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeSceneTransitionAnimation(activity2, sharedElement, sharedElementName));
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeSceneTransitionAnimation(activity2, sharedElement, sharedElementName));
        }
        if (VERSION.SDK_INT < 21) {
            return new ActivityOptionsCompat();
        }
        return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeSceneTransitionAnimation(activity2, sharedElement, sharedElementName));
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity, Pair<View, String>... pairArr) {
        Activity activity2 = activity;
        Pair<View, String>[] sharedElements = pairArr;
        Activity activity3 = activity2;
        Pair<View, String>[] pairArr2 = sharedElements;
        if (VERSION.SDK_INT < 21) {
            return new ActivityOptionsCompat();
        }
        View[] views = null;
        String[] names = null;
        if (sharedElements != null) {
            views = new View[sharedElements.length];
            names = new String[sharedElements.length];
            for (int i = 0; i < sharedElements.length; i++) {
                views[i] = (View) sharedElements[i].first;
                names[i] = (String) sharedElements[i].second;
            }
        }
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeSceneTransitionAnimation(activity2, views, names));
        }
        if (VERSION.SDK_INT < 23) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeSceneTransitionAnimation(activity2, views, names));
        }
        return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeSceneTransitionAnimation(activity2, views, names));
    }

    public static ActivityOptionsCompat makeTaskLaunchBehind() {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeTaskLaunchBehind());
        }
        if (VERSION.SDK_INT >= 23) {
            return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeTaskLaunchBehind());
        }
        if (VERSION.SDK_INT < 21) {
            return new ActivityOptionsCompat();
        }
        return new ActivityOptionsImpl21(ActivityOptionsCompat21.makeTaskLaunchBehind());
    }

    public static ActivityOptionsCompat makeBasic() {
        if (VERSION.SDK_INT >= 24) {
            return new ActivityOptionsImpl24(ActivityOptionsCompat24.makeBasic());
        }
        if (VERSION.SDK_INT < 23) {
            return new ActivityOptionsCompat();
        }
        return new ActivityOptionsImpl23(ActivityOptionsCompat23.makeBasic());
    }

    protected ActivityOptionsCompat() {
    }

    public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
        Rect rect2 = rect;
        return null;
    }

    @Nullable
    public Rect getLaunchBounds() {
        return null;
    }

    public Bundle toBundle() {
        return null;
    }

    public void update(ActivityOptionsCompat activityOptionsCompat) {
        ActivityOptionsCompat activityOptionsCompat2 = activityOptionsCompat;
    }

    public void requestUsageTimeReport(PendingIntent pendingIntent) {
        PendingIntent pendingIntent2 = pendingIntent;
    }
}
