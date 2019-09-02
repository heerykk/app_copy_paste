package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.app.ActivityOptionsCompatJB */
class ActivityOptionsCompatJB {
    private final ActivityOptions mActivityOptions;

    public static ActivityOptionsCompatJB makeCustomAnimation(Context context, int i, int i2) {
        Context context2 = context;
        int enterResId = i;
        int exitResId = i2;
        Context context3 = context2;
        int i3 = enterResId;
        int i4 = exitResId;
        return new ActivityOptionsCompatJB(ActivityOptions.makeCustomAnimation(context2, enterResId, exitResId));
    }

    public static ActivityOptionsCompatJB makeScaleUpAnimation(View view, int i, int i2, int i3, int i4) {
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
        return new ActivityOptionsCompatJB(ActivityOptions.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight));
    }

    public static ActivityOptionsCompatJB makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int i, int i2) {
        View source = view;
        Bitmap thumbnail = bitmap;
        int startX = i;
        int startY = i2;
        View view2 = source;
        Bitmap bitmap2 = thumbnail;
        int i3 = startX;
        int i4 = startY;
        return new ActivityOptionsCompatJB(ActivityOptions.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY));
    }

    private ActivityOptionsCompatJB(ActivityOptions activityOptions) {
        ActivityOptions activityOptions2 = activityOptions;
        ActivityOptions activityOptions3 = activityOptions2;
        this.mActivityOptions = activityOptions2;
    }

    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    public void update(ActivityOptionsCompatJB activityOptionsCompatJB) {
        ActivityOptionsCompatJB otherOptions = activityOptionsCompatJB;
        ActivityOptionsCompatJB activityOptionsCompatJB2 = otherOptions;
        this.mActivityOptions.update(otherOptions.mActivityOptions);
    }
}
