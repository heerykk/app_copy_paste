package android.support.p000v4.media;

import android.annotation.TargetApi;
import android.media.Rating;
import android.support.annotation.RequiresApi;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.media.RatingCompatKitkat */
class RatingCompatKitkat {
    RatingCompatKitkat() {
    }

    public static Object newUnratedRating(int i) {
        int ratingStyle = i;
        int i2 = ratingStyle;
        return Rating.newUnratedRating(ratingStyle);
    }

    public static Object newHeartRating(boolean z) {
        return Rating.newHeartRating(z);
    }

    public static Object newThumbRating(boolean z) {
        return Rating.newThumbRating(z);
    }

    public static Object newStarRating(int i, float f) {
        int starRatingStyle = i;
        float starRating = f;
        int i2 = starRatingStyle;
        float f2 = starRating;
        return Rating.newStarRating(starRatingStyle, starRating);
    }

    public static Object newPercentageRating(float f) {
        float percent = f;
        float f2 = percent;
        return Rating.newPercentageRating(percent);
    }

    public static boolean isRated(Object obj) {
        Object ratingObj = obj;
        Object obj2 = ratingObj;
        return ((Rating) ratingObj).isRated();
    }

    public static int getRatingStyle(Object obj) {
        Object ratingObj = obj;
        Object obj2 = ratingObj;
        return ((Rating) ratingObj).getRatingStyle();
    }

    public static boolean hasHeart(Object obj) {
        Object ratingObj = obj;
        Object obj2 = ratingObj;
        return ((Rating) ratingObj).hasHeart();
    }

    public static boolean isThumbUp(Object obj) {
        Object ratingObj = obj;
        Object obj2 = ratingObj;
        return ((Rating) ratingObj).isThumbUp();
    }

    public static float getStarRating(Object obj) {
        Object ratingObj = obj;
        Object obj2 = ratingObj;
        return ((Rating) ratingObj).getStarRating();
    }

    public static float getPercentRating(Object obj) {
        Object ratingObj = obj;
        Object obj2 = ratingObj;
        return ((Rating) ratingObj).getPercentRating();
    }
}
