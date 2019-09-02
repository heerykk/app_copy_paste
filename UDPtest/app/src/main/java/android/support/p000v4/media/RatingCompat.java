package android.support.p000v4.media;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: android.support.v4.media.RatingCompat */
public final class RatingCompat implements Parcelable {
    public static final Creator<RatingCompat> CREATOR = new Creator<RatingCompat>() {
        public RatingCompat createFromParcel(Parcel parcel) {
            Parcel p = parcel;
            Parcel parcel2 = p;
            return new RatingCompat(p.readInt(), p.readFloat());
        }

        public RatingCompat[] newArray(int i) {
            int size = i;
            int i2 = size;
            return new RatingCompat[size];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private Object mRatingObj;
    private final int mRatingStyle;
    private final float mRatingValue;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.RatingCompat$StarStyle */
    public @interface StarStyle {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.RatingCompat$Style */
    public @interface Style {
    }

    RatingCompat(int i, float f) {
        int ratingStyle = i;
        float rating = f;
        int i2 = ratingStyle;
        float f2 = rating;
        this.mRatingStyle = ratingStyle;
        this.mRatingValue = rating;
    }

    public String toString() {
        String valueOf;
        StringBuilder append = new StringBuilder().append("Rating:style=").append(this.mRatingStyle).append(" rating=");
        if (this.mRatingValue < 0.0f) {
            valueOf = "unrated";
        } else {
            valueOf = String.valueOf(this.mRatingValue);
        }
        return append.append(valueOf).toString();
    }

    public int describeContents() {
        return this.mRatingStyle;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel dest = parcel;
        Parcel parcel2 = dest;
        int i2 = i;
        dest.writeInt(this.mRatingStyle);
        dest.writeFloat(this.mRatingValue);
    }

    public static RatingCompat newUnratedRating(int i) {
        int ratingStyle = i;
        int i2 = ratingStyle;
        switch (ratingStyle) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new RatingCompat(ratingStyle, RATING_NOT_RATED);
            default:
                return null;
        }
    }

    public static RatingCompat newHeartRating(boolean z) {
        return new RatingCompat(1, !z ? 0.0f : 1.0f);
    }

    public static RatingCompat newThumbRating(boolean z) {
        return new RatingCompat(2, !z ? 0.0f : 1.0f);
    }

    public static RatingCompat newStarRating(int i, float f) {
        float maxRating;
        int starRatingStyle = i;
        float starRating = f;
        int i2 = starRatingStyle;
        float f2 = starRating;
        switch (starRatingStyle) {
            case 3:
                maxRating = 3.0f;
                break;
            case 4:
                maxRating = 4.0f;
                break;
            case 5:
                maxRating = 5.0f;
                break;
            default:
                int e = Log.e(TAG, "Invalid rating style (" + starRatingStyle + ") for a star rating");
                return null;
        }
        if (!(starRating < 0.0f) && starRating <= maxRating) {
            return new RatingCompat(starRatingStyle, starRating);
        }
        int e2 = Log.e(TAG, "Trying to set out of range star-based rating");
        return null;
    }

    public static RatingCompat newPercentageRating(float f) {
        float percent = f;
        float f2 = percent;
        if (!(percent < 0.0f) && percent <= 100.0f) {
            return new RatingCompat(6, percent);
        }
        int e = Log.e(TAG, "Invalid percentage-based rating value");
        return null;
    }

    public boolean isRated() {
        return this.mRatingValue >= 0.0f;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public boolean hasHeart() {
        if (this.mRatingStyle != 1) {
            return false;
        }
        return this.mRatingValue == 1.0f;
    }

    public boolean isThumbUp() {
        if (this.mRatingStyle != 2) {
            return false;
        }
        return this.mRatingValue == 1.0f;
    }

    public float getStarRating() {
        switch (this.mRatingStyle) {
            case 3:
            case 4:
            case 5:
                if (isRated()) {
                    return this.mRatingValue;
                }
                break;
        }
        return RATING_NOT_RATED;
    }

    public float getPercentRating() {
        if (this.mRatingStyle == 6 && isRated()) {
            return this.mRatingValue;
        }
        return RATING_NOT_RATED;
    }

    public static RatingCompat fromRating(Object obj) {
        RatingCompat rating;
        Object ratingObj = obj;
        Object obj2 = ratingObj;
        if (ratingObj == null || VERSION.SDK_INT < 19) {
            return null;
        }
        int ratingStyle = RatingCompatKitkat.getRatingStyle(ratingObj);
        if (!RatingCompatKitkat.isRated(ratingObj)) {
            rating = newUnratedRating(ratingStyle);
        } else {
            switch (ratingStyle) {
                case 1:
                    rating = newHeartRating(RatingCompatKitkat.hasHeart(ratingObj));
                    break;
                case 2:
                    rating = newThumbRating(RatingCompatKitkat.isThumbUp(ratingObj));
                    break;
                case 3:
                case 4:
                case 5:
                    rating = newStarRating(ratingStyle, RatingCompatKitkat.getStarRating(ratingObj));
                    break;
                case 6:
                    rating = newPercentageRating(RatingCompatKitkat.getPercentRating(ratingObj));
                    break;
                default:
                    return null;
            }
        }
        rating.mRatingObj = ratingObj;
        return rating;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002b, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getRating() {
        /*
            r16 = this;
            r0 = r16
            r1 = r0
            java.lang.Object r2 = r0.mRatingObj
            r3 = 0
            if (r2 == r3) goto L_0x000b
        L_0x0008:
            java.lang.Object r2 = r0.mRatingObj
            return r2
        L_0x000b:
            int r4 = android.os.Build.VERSION.SDK_INT
            r5 = 19
            if (r4 < r5) goto L_0x0008
            boolean r6 = r0.isRated()
            r4 = r6
            r5 = 0
            if (r4 != r5) goto L_0x0024
            int r8 = r0.mRatingStyle
            java.lang.Object r10 = android.support.p000v4.media.RatingCompatKitkat.newUnratedRating(r8)
            r0.mRatingObj = r10
        L_0x0021:
            java.lang.Object r2 = r0.mRatingObj
            return r2
        L_0x0024:
            int r4 = r0.mRatingStyle
            switch(r4) {
                case 1: goto L_0x002c;
                case 2: goto L_0x0039;
                case 3: goto L_0x0046;
                case 4: goto L_0x0053;
                case 5: goto L_0x0054;
                case 6: goto L_0x0055;
                default: goto L_0x0029;
            }
        L_0x0029:
            r2 = 0
            r15 = 0
            return r15
        L_0x002c:
            boolean r7 = r0.hasHeart()
            r8 = r7
            r9 = r8
            java.lang.Object r10 = android.support.p000v4.media.RatingCompatKitkat.newHeartRating(r9)
            r0.mRatingObj = r10
            goto L_0x0021
        L_0x0039:
            boolean r11 = r0.isThumbUp()
            r8 = r11
            r12 = r8
            java.lang.Object r10 = android.support.p000v4.media.RatingCompatKitkat.newThumbRating(r12)
            r0.mRatingObj = r10
            goto L_0x0021
        L_0x0046:
            int r8 = r0.mRatingStyle
            float r13 = r0.getStarRating()
            java.lang.Object r10 = android.support.p000v4.media.RatingCompatKitkat.newStarRating(r8, r13)
            r0.mRatingObj = r10
            goto L_0x0021
        L_0x0053:
            goto L_0x0046
        L_0x0054:
            goto L_0x0046
        L_0x0055:
            float r14 = r0.getPercentRating()
            java.lang.Object r10 = android.support.p000v4.media.RatingCompatKitkat.newPercentageRating(r14)
            r0.mRatingObj = r10
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.media.RatingCompat.getRating():java.lang.Object");
    }
}
