package android.support.p003v7.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.widget.RatingBar;

/* renamed from: android.support.v7.widget.AppCompatRatingBar */
public class AppCompatRatingBar extends RatingBar {
    private AppCompatProgressBarHelper mAppCompatProgressBarHelper;

    public AppCompatRatingBar(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public AppCompatRatingBar(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.ratingBarStyle);
    }

    public AppCompatRatingBar(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mAppCompatProgressBarHelper = new AppCompatProgressBarHelper(this);
        this.mAppCompatProgressBarHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public synchronized void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        synchronized (this) {
            int i3 = widthMeasureSpec;
            int i4 = heightMeasureSpec;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            Bitmap sampleTime = this.mAppCompatProgressBarHelper.getSampleTime();
            Bitmap sampleTile = sampleTime;
            if (sampleTime != null) {
                int width = sampleTile.getWidth() * getNumStars();
                int i5 = width;
                setMeasuredDimension(ViewCompat.resolveSizeAndState(width, widthMeasureSpec, 0), getMeasuredHeight());
            }
        }
    }
}
