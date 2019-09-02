package android.support.p000v4.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/* renamed from: android.support.v4.widget.ContentLoadingProgressBar */
public class ContentLoadingProgressBar extends ProgressBar {
    private static final int MIN_DELAY = 500;
    private static final int MIN_SHOW_TIME = 500;
    private final Runnable mDelayedHide;
    private final Runnable mDelayedShow;
    boolean mDismissed;
    boolean mPostedHide;
    boolean mPostedShow;
    long mStartTime;

    public ContentLoadingProgressBar(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs, 0);
        this.mStartTime = -1;
        this.mPostedHide = false;
        this.mPostedShow = false;
        this.mDismissed = false;
        this.mDelayedHide = new Runnable(this) {
            final /* synthetic */ ContentLoadingProgressBar this$0;

            {
                ContentLoadingProgressBar this$02 = r5;
                ContentLoadingProgressBar contentLoadingProgressBar = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                this.this$0.mPostedHide = false;
                this.this$0.mStartTime = -1;
                this.this$0.setVisibility(8);
            }
        };
        this.mDelayedShow = new Runnable(this) {
            final /* synthetic */ ContentLoadingProgressBar this$0;

            {
                ContentLoadingProgressBar this$02 = r5;
                ContentLoadingProgressBar contentLoadingProgressBar = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                this.this$0.mPostedShow = false;
                if (!this.this$0.mDismissed) {
                    this.this$0.mStartTime = System.currentTimeMillis();
                    this.this$0.setVisibility(0);
                }
            }
        };
    }

    public ContentLoadingProgressBar(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        boolean removeCallbacks = removeCallbacks(this.mDelayedHide);
        boolean removeCallbacks2 = removeCallbacks(this.mDelayedShow);
    }

    public void hide() {
        this.mDismissed = true;
        boolean removeCallbacks = removeCallbacks(this.mDelayedShow);
        long currentTimeMillis = System.currentTimeMillis() - this.mStartTime;
        long diff = currentTimeMillis;
        if ((currentTimeMillis >= 500) || this.mStartTime == -1) {
            setVisibility(8);
        } else if (!this.mPostedHide) {
            boolean postDelayed = postDelayed(this.mDelayedHide, 500 - diff);
            this.mPostedHide = true;
        }
    }

    public void show() {
        this.mStartTime = -1;
        this.mDismissed = false;
        boolean removeCallbacks = removeCallbacks(this.mDelayedHide);
        if (!this.mPostedShow) {
            boolean postDelayed = postDelayed(this.mDelayedShow, 500);
            this.mPostedShow = true;
        }
    }
}
