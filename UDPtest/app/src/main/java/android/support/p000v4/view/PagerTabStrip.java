package android.support.p000v4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.p000v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;

/* renamed from: android.support.v4.view.PagerTabStrip */
public class PagerTabStrip extends PagerTitleStrip {
    private static final int FULL_UNDERLINE_HEIGHT = 1;
    private static final int INDICATOR_HEIGHT = 3;
    private static final int MIN_PADDING_BOTTOM = 6;
    private static final int MIN_STRIP_HEIGHT = 32;
    private static final int MIN_TEXT_SPACING = 64;
    private static final int TAB_PADDING = 16;
    private static final int TAB_SPACING = 32;
    private static final String TAG = "PagerTabStrip";
    private boolean mDrawFullUnderline;
    private boolean mDrawFullUnderlineSet;
    private int mFullUnderlineHeight;
    private boolean mIgnoreTap;
    private int mIndicatorColor;
    private int mIndicatorHeight;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mMinPaddingBottom;
    private int mMinStripHeight;
    private int mMinTextSpacing;
    private int mTabAlpha;
    private int mTabPadding;
    private final Paint mTabPaint;
    private final Rect mTempRect;
    private int mTouchSlop;

    public PagerTabStrip(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        this.mTabPaint = new Paint();
        this.mTempRect = new Rect();
        this.mTabAlpha = 255;
        this.mDrawFullUnderline = false;
        this.mDrawFullUnderlineSet = false;
        this.mIndicatorColor = this.mTextColor;
        this.mTabPaint.setColor(this.mIndicatorColor);
        float f = context2.getResources().getDisplayMetrics().density;
        float f2 = f;
        this.mIndicatorHeight = (int) ((3.0f * f) + 0.5f);
        this.mMinPaddingBottom = (int) ((6.0f * f) + 0.5f);
        this.mMinTextSpacing = (int) (64.0f * f);
        this.mTabPadding = (int) ((16.0f * f) + 0.5f);
        this.mFullUnderlineHeight = (int) ((1.0f * f) + 0.5f);
        this.mMinStripHeight = (int) ((32.0f * f) + 0.5f);
        this.mTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(getTextSpacing());
        setWillNotDraw(false);
        this.mPrevText.setFocusable(true);
        this.mPrevText.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PagerTabStrip this$0;

            {
                PagerTabStrip this$02 = r5;
                PagerTabStrip pagerTabStrip = this$02;
                this.this$0 = this$02;
            }

            public void onClick(View view) {
                View view2 = view;
                this.this$0.mPager.setCurrentItem(this.this$0.mPager.getCurrentItem() - 1);
            }
        });
        this.mNextText.setFocusable(true);
        this.mNextText.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PagerTabStrip this$0;

            {
                PagerTabStrip this$02 = r5;
                PagerTabStrip pagerTabStrip = this$02;
                this.this$0 = this$02;
            }

            public void onClick(View view) {
                View view2 = view;
                this.this$0.mPager.setCurrentItem(this.this$0.mPager.getCurrentItem() + 1);
            }
        });
        if (getBackground() == null) {
            this.mDrawFullUnderline = true;
        }
    }

    public PagerTabStrip(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public void setTabIndicatorColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mIndicatorColor = color;
        this.mTabPaint.setColor(this.mIndicatorColor);
        invalidate();
    }

    public void setTabIndicatorColorResource(@ColorRes int i) {
        int resId = i;
        int i2 = resId;
        setTabIndicatorColor(ContextCompat.getColor(getContext(), resId));
    }

    @ColorInt
    public int getTabIndicatorColor() {
        return this.mIndicatorColor;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int bottom2 = bottom;
        if (bottom < this.mMinPaddingBottom) {
            bottom2 = this.mMinPaddingBottom;
        }
        super.setPadding(left, top, right, bottom2);
    }

    public void setTextSpacing(int i) {
        int textSpacing = i;
        int textSpacing2 = textSpacing;
        if (textSpacing < this.mMinTextSpacing) {
            textSpacing2 = this.mMinTextSpacing;
        }
        super.setTextSpacing(textSpacing2);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Drawable d = drawable;
        Drawable drawable2 = d;
        super.setBackgroundDrawable(d);
        if (!this.mDrawFullUnderlineSet) {
            this.mDrawFullUnderline = d == null;
        }
    }

    public void setBackgroundColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        super.setBackgroundColor(color);
        if (!this.mDrawFullUnderlineSet) {
            this.mDrawFullUnderline = (color & ViewCompat.MEASURED_STATE_MASK) == 0;
        }
    }

    public void setBackgroundResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        super.setBackgroundResource(resId);
        if (!this.mDrawFullUnderlineSet) {
            this.mDrawFullUnderline = resId == 0;
        }
    }

    public void setDrawFullUnderline(boolean z) {
        this.mDrawFullUnderline = z;
        this.mDrawFullUnderlineSet = true;
        invalidate();
    }

    public boolean getDrawFullUnderline() {
        return this.mDrawFullUnderline;
    }

    /* access modifiers changed from: 0000 */
    public int getMinHeight() {
        return Math.max(super.getMinHeight(), this.mMinStripHeight);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        int action = ev.getAction();
        int action2 = action;
        if (action != 0 && this.mIgnoreTap) {
            return false;
        }
        float x = ev.getX();
        float y = ev.getY();
        float y2 = y;
        switch (action2) {
            case 0:
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                this.mIgnoreTap = false;
                break;
            case 1:
                if (x >= ((float) (this.mCurrText.getLeft() - this.mTabPadding))) {
                    if (x > ((float) (this.mCurrText.getRight() + this.mTabPadding))) {
                        this.mPager.setCurrentItem(this.mPager.getCurrentItem() + 1);
                        break;
                    }
                } else {
                    this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1);
                    break;
                }
                break;
            case 2:
                if ((Math.abs(x - this.mInitialMotionX) > ((float) this.mTouchSlop)) || Math.abs(y2 - this.mInitialMotionY) > ((float) this.mTouchSlop)) {
                    this.mIgnoreTap = true;
                    break;
                }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.onDraw(canvas2);
        int height = getHeight();
        int height2 = height;
        int bottom = height;
        int left = this.mCurrText.getLeft() - this.mTabPadding;
        int right = this.mCurrText.getRight() + this.mTabPadding;
        int i = bottom - this.mIndicatorHeight;
        int i2 = i;
        this.mTabPaint.setColor((this.mTabAlpha << 24) | (this.mIndicatorColor & ViewCompat.MEASURED_SIZE_MASK));
        canvas2.drawRect((float) left, (float) i, (float) right, (float) bottom, this.mTabPaint);
        if (this.mDrawFullUnderline) {
            this.mTabPaint.setColor(-16777216 | (this.mIndicatorColor & ViewCompat.MEASURED_SIZE_MASK));
            canvas2.drawRect((float) getPaddingLeft(), (float) (height2 - this.mFullUnderlineHeight), (float) (getWidth() - getPaddingRight()), (float) height2, this.mTabPaint);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateTextPositions(int i, float f, boolean z) {
        int position = i;
        float positionOffset = f;
        int i2 = position;
        float f2 = positionOffset;
        boolean force = z;
        Rect r = this.mTempRect;
        int bottom = getHeight();
        int i3 = bottom - this.mIndicatorHeight;
        int top = i3;
        r.set(this.mCurrText.getLeft() - this.mTabPadding, i3, this.mCurrText.getRight() + this.mTabPadding, bottom);
        super.updateTextPositions(position, positionOffset, force);
        this.mTabAlpha = (int) (Math.abs(positionOffset - 0.5f) * 2.0f * 255.0f);
        int right = this.mCurrText.getRight() + this.mTabPadding;
        int right2 = right;
        r.union(this.mCurrText.getLeft() - this.mTabPadding, top, right, bottom);
        invalidate(r);
    }
}
