package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Pair;
import android.view.View;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.app.ActivityOptionsCompat21 */
class ActivityOptionsCompat21 {
    private final ActivityOptions mActivityOptions;

    public static ActivityOptionsCompat21 makeCustomAnimation(Context context, int i, int i2) {
        Context context2 = context;
        int enterResId = i;
        int exitResId = i2;
        Context context3 = context2;
        int i3 = enterResId;
        int i4 = exitResId;
        return new ActivityOptionsCompat21(ActivityOptions.makeCustomAnimation(context2, enterResId, exitResId));
    }

    public static ActivityOptionsCompat21 makeScaleUpAnimation(View view, int i, int i2, int i3, int i4) {
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
        return new ActivityOptionsCompat21(ActivityOptions.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
    }

    public static ActivityOptionsCompat21 makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int i, int i2) {
        View source = view;
        Bitmap thumbnail = bitmap;
        int startX = i;
        int startY = i2;
        View view2 = source;
        Bitmap bitmap2 = thumbnail;
        int i3 = startX;
        int i4 = startY;
        return new ActivityOptionsCompat21(ActivityOptions.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
    }

    public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity activity, View view, String str) {
        Activity activity2 = activity;
        View sharedElement = view;
        String sharedElementName = str;
        Activity activity3 = activity2;
        View view2 = sharedElement;
        String str2 = sharedElementName;
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation(activity2, sharedElement, sharedElementName));
    }

    public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity activity, View[] viewArr, String[] strArr) {
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
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation(activity2, pairs));
    }

    public static ActivityOptionsCompat21 makeTaskLaunchBehind() {
        return new ActivityOptionsCompat21(ActivityOptions.makeTaskLaunchBehind());
    }

    private ActivityOptionsCompat21(ActivityOptions activityOptions) {
        ActivityOptions activityOptions2 = activityOptions;
        ActivityOptions activityOptions3 = activityOptions2;
        this.mActivityOptions = activityOptions2;
    }

    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompat21 activityOptionsCompat21) {
        ActivityOptionsCompat21 otherOptions = activityOptionsCompat21;
        ActivityOptionsCompat21 activityOptionsCompat212 = otherOptions;
        this.mActivityOptions.update(otherOptions.mActivityOptions);
    }
}
