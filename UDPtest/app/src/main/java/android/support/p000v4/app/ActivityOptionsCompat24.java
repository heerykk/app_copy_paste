package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Pair;
import android.view.View;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v4.app.ActivityOptionsCompat24 */
class ActivityOptionsCompat24 {
    private final ActivityOptions mActivityOptions;

    public static ActivityOptionsCompat24 makeCustomAnimation(Context context, int i, int i2) {
        Context context2 = context;
        int enterResId = i;
        int exitResId = i2;
        Context context3 = context2;
        int i3 = enterResId;
        int i4 = exitResId;
        return new ActivityOptionsCompat24(ActivityOptions.makeCustomAnimation(context2, enterResId, exitResId));
    }

    public static ActivityOptionsCompat24 makeScaleUpAnimation(View view, int i, int i2, int i3, int i4) {
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
        return new ActivityOptionsCompat24(ActivityOptions.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
    }

    public static ActivityOptionsCompat24 makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int i, int i2) {
        View source = view;
        Bitmap thumbnail = bitmap;
        int startX = i;
        int startY = i2;
        View view2 = source;
        Bitmap bitmap2 = thumbnail;
        int i3 = startX;
        int i4 = startY;
        return new ActivityOptionsCompat24(ActivityOptions.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
    }

    public static ActivityOptionsCompat24 makeSceneTransitionAnimation(Activity activity, View view, String str) {
        Activity activity2 = activity;
        View sharedElement = view;
        String sharedElementName = str;
        Activity activity3 = activity2;
        View view2 = sharedElement;
        String str2 = sharedElementName;
        return new ActivityOptionsCompat24(ActivityOptions.makeSceneTransitionAnimation(activity2, sharedElement, sharedElementName));
    }

    public static ActivityOptionsCompat24 makeSceneTransitionAnimation(Activity activity, View[] viewArr, String[] strArr) {
        Activity activity2 = activity;
        View[] sharedElements = viewArr;
        String[] sharedElementNames = strArr;
        Activity activity3 = activity2;
        View[] viewArr2 = sharedElements;
        String[] strArr2 = sharedElementNames;
        Pair[] pairs = null;
        if (sharedElements != null) {
            pairs = new Pair[sharedElements.length];
            for (int i = 0; i < pairs.length; i++) {
                pairs[i] = Pair.create(sharedElements[i], sharedElementNames[i]);
            }
        }
        return new ActivityOptionsCompat24(ActivityOptions.makeSceneTransitionAnimation(activity2, pairs));
    }

    public static ActivityOptionsCompat24 makeClipRevealAnimation(View view, int i, int i2, int i3, int i4) {
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
        return new ActivityOptionsCompat24(ActivityOptions.makeClipRevealAnimation(source, startX, startY, width, height));
    }

    public static ActivityOptionsCompat24 makeTaskLaunchBehind() {
        return new ActivityOptionsCompat24(ActivityOptions.makeTaskLaunchBehind());
    }

    public static ActivityOptionsCompat24 makeBasic() {
        return new ActivityOptionsCompat24(ActivityOptions.makeBasic());
    }

    private ActivityOptionsCompat24(ActivityOptions activityOptions) {
        ActivityOptions activityOptions2 = activityOptions;
        ActivityOptions activityOptions3 = activityOptions2;
        this.mActivityOptions = activityOptions2;
    }

    public ActivityOptionsCompat24 setLaunchBounds(@Nullable Rect rect) {
        Rect screenSpacePixelRect = rect;
        Rect rect2 = screenSpacePixelRect;
        return new ActivityOptionsCompat24(this.mActivityOptions.setLaunchBounds(screenSpacePixelRect));
    }

    public Rect getLaunchBounds() {
        return this.mActivityOptions.getLaunchBounds();
    }

    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompat24 activityOptionsCompat24) {
        ActivityOptionsCompat24 otherOptions = activityOptionsCompat24;
        ActivityOptionsCompat24 activityOptionsCompat242 = otherOptions;
        this.mActivityOptions.update(otherOptions.mActivityOptions);
    }

    public void requestUsageTimeReport(PendingIntent pendingIntent) {
        PendingIntent receiver = pendingIntent;
        PendingIntent pendingIntent2 = receiver;
        this.mActivityOptions.requestUsageTimeReport(receiver);
    }
}
