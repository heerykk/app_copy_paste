package android.support.p000v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.p000v4.view.ViewPager.DecorView;
import android.support.p000v4.view.ViewPager.OnAdapterChangeListener;
import android.support.p000v4.view.ViewPager.OnPageChangeListener;
import android.support.p000v4.widget.TextViewCompat;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import java.lang.ref.WeakReference;

@DecorView
/* renamed from: android.support.v4.view.PagerTitleStrip */
public class PagerTitleStrip extends ViewGroup {
    private static final int[] ATTRS;
    private static final PagerTitleStripImpl IMPL;
    private static final float SIDE_ALPHA = 0.6f;
    private static final String TAG = "PagerTitleStrip";
    private static final int[] TEXT_ATTRS;
    private static final int TEXT_SPACING = 16;
    TextView mCurrText;
    private int mGravity;
    private int mLastKnownCurrentPage;
    float mLastKnownPositionOffset;
    TextView mNextText;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener;
    ViewPager mPager;
    TextView mPrevText;
    private int mScaledTextSpacing;
    int mTextColor;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;

    /* renamed from: android.support.v4.view.PagerTitleStrip$PageListener */
    private class PageListener extends DataSetObserver implements OnPageChangeListener, OnAdapterChangeListener {
        private int mScrollState;
        final /* synthetic */ PagerTitleStrip this$0;

        PageListener(PagerTitleStrip pagerTitleStrip) {
            this.this$0 = pagerTitleStrip;
        }

        public void onPageScrolled(int i, float f, int i2) {
            int position = i;
            float positionOffset = f;
            int position2 = position;
            float f2 = positionOffset;
            int i3 = i2;
            if (positionOffset > 0.5f) {
                position2 = position + 1;
            }
            this.this$0.updateTextPositions(position2, positionOffset, false);
        }

        public void onPageSelected(int i) {
            int i2 = i;
            if (this.mScrollState == 0) {
                this.this$0.updateText(this.this$0.mPager.getCurrentItem(), this.this$0.mPager.getAdapter());
                this.this$0.updateTextPositions(this.this$0.mPager.getCurrentItem(), this.this$0.mLastKnownPositionOffset >= 0.0f ? this.this$0.mLastKnownPositionOffset : 0.0f, true);
            }
        }

        public void onPageScrollStateChanged(int i) {
            int state = i;
            int i2 = state;
            this.mScrollState = state;
        }

        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerAdapter oldAdapter = pagerAdapter;
            PagerAdapter newAdapter = pagerAdapter2;
            ViewPager viewPager2 = viewPager;
            PagerAdapter pagerAdapter3 = oldAdapter;
            PagerAdapter pagerAdapter4 = newAdapter;
            this.this$0.updateAdapter(oldAdapter, newAdapter);
        }

        public void onChanged() {
            this.this$0.updateText(this.this$0.mPager.getCurrentItem(), this.this$0.mPager.getAdapter());
            this.this$0.updateTextPositions(this.this$0.mPager.getCurrentItem(), this.this$0.mLastKnownPositionOffset >= 0.0f ? this.this$0.mLastKnownPositionOffset : 0.0f, true);
        }
    }

    /* renamed from: android.support.v4.view.PagerTitleStrip$PagerTitleStripImpl */
    interface PagerTitleStripImpl {
        void setSingleLineAllCaps(TextView textView);
    }

    /* renamed from: android.support.v4.view.PagerTitleStrip$PagerTitleStripImplBase */
    static class PagerTitleStripImplBase implements PagerTitleStripImpl {
        PagerTitleStripImplBase() {
        }

        public void setSingleLineAllCaps(TextView textView) {
            TextView text = textView;
            TextView textView2 = text;
            text.setSingleLine();
        }
    }

    /* renamed from: android.support.v4.view.PagerTitleStrip$PagerTitleStripImplIcs */
    static class PagerTitleStripImplIcs implements PagerTitleStripImpl {
        PagerTitleStripImplIcs() {
        }

        public void setSingleLineAllCaps(TextView textView) {
            TextView text = textView;
            TextView textView2 = text;
            PagerTitleStripIcs.setSingleLineAllCaps(text);
        }
    }

    public PagerTitleStrip(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        this.mLastKnownCurrentPage = -1;
        this.mLastKnownPositionOffset = -1.0f;
        this.mPageListener = new PageListener(this);
        TextView textView = new TextView(context2);
        this.mPrevText = textView;
        addView(textView);
        TextView textView2 = new TextView(context2);
        this.mCurrText = textView2;
        addView(textView2);
        TextView textView3 = new TextView(context2);
        this.mNextText = textView3;
        addView(textView3);
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attrs, ATTRS);
        TypedArray a = obtainStyledAttributes;
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        int textAppearance = resourceId;
        if (resourceId != 0) {
            TextViewCompat.setTextAppearance(this.mPrevText, textAppearance);
            TextViewCompat.setTextAppearance(this.mCurrText, textAppearance);
            TextViewCompat.setTextAppearance(this.mNextText, textAppearance);
        }
        int dimensionPixelSize = a.getDimensionPixelSize(1, 0);
        int textSize = dimensionPixelSize;
        if (dimensionPixelSize != 0) {
            setTextSize(0, (float) textSize);
        }
        if (a.hasValue(2)) {
            int color = a.getColor(2, 0);
            int i = color;
            this.mPrevText.setTextColor(color);
            this.mCurrText.setTextColor(color);
            this.mNextText.setTextColor(color);
        }
        this.mGravity = a.getInteger(3, 80);
        a.recycle();
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(SIDE_ALPHA);
        this.mPrevText.setEllipsize(TruncateAt.END);
        this.mCurrText.setEllipsize(TruncateAt.END);
        this.mNextText.setEllipsize(TruncateAt.END);
        boolean allCaps = false;
        if (textAppearance != 0) {
            TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(textAppearance, TEXT_ATTRS);
            TypedArray ta = obtainStyledAttributes2;
            allCaps = obtainStyledAttributes2.getBoolean(0, false);
            ta.recycle();
        }
        if (!allCaps) {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        } else {
            setSingleLineAllCaps(this.mPrevText);
            setSingleLineAllCaps(this.mCurrText);
            setSingleLineAllCaps(this.mNextText);
        }
        float f = context2.getResources().getDisplayMetrics().density;
        float f2 = f;
        this.mScaledTextSpacing = (int) (16.0f * f);
    }

    static {
        int[] iArr = new int[4];
        iArr[0] = 16842804;
        iArr[1] = 16842901;
        iArr[2] = 16842904;
        iArr[3] = 16842927;
        ATTRS = iArr;
        int[] iArr2 = new int[1];
        iArr2[0] = 16843660;
        TEXT_ATTRS = iArr2;
        if (VERSION.SDK_INT < 14) {
            IMPL = new PagerTitleStripImplBase();
        } else {
            IMPL = new PagerTitleStripImplIcs();
        }
    }

    private static void setSingleLineAllCaps(TextView textView) {
        TextView text = textView;
        TextView textView2 = text;
        IMPL.setSingleLineAllCaps(text);
    }

    public PagerTitleStrip(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public void setTextSpacing(int i) {
        int spacingPixels = i;
        int i2 = spacingPixels;
        this.mScaledTextSpacing = spacingPixels;
        requestLayout();
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    public void setNonPrimaryAlpha(@FloatRange(from = 0.0d, mo5to = 1.0d) float f) {
        float alpha = f;
        float f2 = alpha;
        this.mNonPrimaryAlpha = ((int) (alpha * 255.0f)) & 255;
        int i = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        int i2 = i;
        this.mPrevText.setTextColor(i);
        this.mNextText.setTextColor(i);
    }

    public void setTextColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mTextColor = color;
        this.mCurrText.setTextColor(color);
        int i3 = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        int i4 = i3;
        this.mPrevText.setTextColor(i3);
        this.mNextText.setTextColor(i3);
    }

    public void setTextSize(int i, float f) {
        int unit = i;
        float size = f;
        int i2 = unit;
        float f2 = size;
        this.mPrevText.setTextSize(unit, size);
        this.mCurrText.setTextSize(unit, size);
        this.mNextText.setTextSize(unit, size);
    }

    public void setGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        this.mGravity = gravity;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        ViewParent parent2 = parent;
        if (parent instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) parent2;
            ViewPager pager = viewPager;
            PagerAdapter adapter = viewPager.getAdapter();
            OnPageChangeListener internalPageChangeListener = pager.setInternalPageChangeListener(this.mPageListener);
            pager.addOnAdapterChangeListener(this.mPageListener);
            this.mPager = pager;
            updateAdapter(this.mWatchingAdapter == null ? null : (PagerAdapter) this.mWatchingAdapter.get(), adapter);
            return;
        }
        throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPager != null) {
            updateAdapter(this.mPager.getAdapter(), null);
            OnPageChangeListener internalPageChangeListener = this.mPager.setInternalPageChangeListener(null);
            this.mPager.removeOnAdapterChangeListener(this.mPageListener);
            this.mPager = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateText(int i, PagerAdapter pagerAdapter) {
        int currentItem = i;
        PagerAdapter adapter = pagerAdapter;
        int i2 = currentItem;
        PagerAdapter pagerAdapter2 = adapter;
        int itemCount = adapter == null ? 0 : adapter.getCount();
        this.mUpdatingText = true;
        CharSequence text = null;
        if (currentItem >= 1 && adapter != null) {
            text = adapter.getPageTitle(currentItem - 1);
        }
        this.mPrevText.setText(text);
        this.mCurrText.setText((adapter != null && currentItem < itemCount) ? adapter.getPageTitle(currentItem) : null);
        CharSequence text2 = null;
        if (currentItem + 1 < itemCount && adapter != null) {
            text2 = adapter.getPageTitle(currentItem + 1);
        }
        this.mNextText.setText(text2);
        int max = Math.max(0, (int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * 0.8f));
        int i3 = max;
        int childWidthSpec = MeasureSpec.makeMeasureSpec(max, Integer.MIN_VALUE);
        int max2 = Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom());
        int i4 = max2;
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(max2, Integer.MIN_VALUE);
        int i5 = makeMeasureSpec;
        this.mPrevText.measure(childWidthSpec, makeMeasureSpec);
        this.mCurrText.measure(childWidthSpec, makeMeasureSpec);
        this.mNextText.measure(childWidthSpec, makeMeasureSpec);
        this.mLastKnownCurrentPage = currentItem;
        if (!this.mUpdatingPositions) {
            updateTextPositions(currentItem, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    public void requestLayout() {
        if (!this.mUpdatingText) {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateAdapter(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        PagerAdapter oldAdapter = pagerAdapter;
        PagerAdapter newAdapter = pagerAdapter2;
        PagerAdapter pagerAdapter3 = oldAdapter;
        PagerAdapter pagerAdapter4 = newAdapter;
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (newAdapter != null) {
            newAdapter.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<>(newAdapter);
        }
        if (this.mPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.mLastKnownPositionOffset = -1.0f;
            updateText(this.mPager.getCurrentItem(), newAdapter);
            requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateTextPositions(int i, float f, boolean z) {
        int prevTop;
        int currTop;
        int nextTop;
        int position = i;
        float positionOffset = f;
        int i2 = position;
        float f2 = positionOffset;
        boolean force = z;
        if (position != this.mLastKnownCurrentPage) {
            updateText(position, this.mPager.getAdapter());
        } else if (!force && positionOffset == this.mLastKnownPositionOffset) {
            return;
        }
        this.mUpdatingPositions = true;
        int prevWidth = this.mPrevText.getMeasuredWidth();
        int currWidth = this.mCurrText.getMeasuredWidth();
        int nextWidth = this.mNextText.getMeasuredWidth();
        int halfCurrWidth = currWidth / 2;
        int stripWidth = getWidth();
        int stripHeight = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int textPaddedRight = paddingRight + halfCurrWidth;
        int contentWidth = (stripWidth - (paddingLeft + halfCurrWidth)) - textPaddedRight;
        float f3 = positionOffset + 0.5f;
        float currOffset = f3;
        if (f3 > 1.0f) {
            currOffset -= 1.0f;
        }
        int i3 = (stripWidth - textPaddedRight) - ((int) (((float) contentWidth) * currOffset));
        int i4 = i3;
        int i5 = i3 - (currWidth / 2);
        int currLeft = i5;
        int currRight = i5 + currWidth;
        int prevBaseline = this.mPrevText.getBaseline();
        int currBaseline = this.mCurrText.getBaseline();
        int nextBaseline = this.mNextText.getBaseline();
        int max = Math.max(Math.max(prevBaseline, currBaseline), nextBaseline);
        int maxBaseline = max;
        int prevTopOffset = max - prevBaseline;
        int currTopOffset = maxBaseline - currBaseline;
        int nextTopOffset = maxBaseline - nextBaseline;
        int alignedNextHeight = nextTopOffset + this.mNextText.getMeasuredHeight();
        int maxTextHeight = Math.max(Math.max(prevTopOffset + this.mPrevText.getMeasuredHeight(), currTopOffset + this.mCurrText.getMeasuredHeight()), alignedNextHeight);
        int i6 = this.mGravity & 112;
        int i7 = i6;
        switch (i6) {
            case 16:
                int i8 = (stripHeight - paddingTop) - paddingBottom;
                int i9 = i8;
                int i10 = (i8 - maxTextHeight) / 2;
                int centeredTop = i10;
                prevTop = i10 + prevTopOffset;
                currTop = centeredTop + currTopOffset;
                nextTop = centeredTop + nextTopOffset;
                break;
            case 80:
                int i11 = (stripHeight - paddingBottom) - maxTextHeight;
                int bottomGravTop = i11;
                prevTop = i11 + prevTopOffset;
                currTop = bottomGravTop + currTopOffset;
                nextTop = bottomGravTop + nextTopOffset;
                break;
            default:
                prevTop = paddingTop + prevTopOffset;
                currTop = paddingTop + currTopOffset;
                nextTop = paddingTop + nextTopOffset;
                break;
        }
        this.mCurrText.layout(currLeft, currTop, currRight, currTop + this.mCurrText.getMeasuredHeight());
        int min = Math.min(paddingLeft, (currLeft - this.mScaledTextSpacing) - prevWidth);
        int i12 = min;
        this.mPrevText.layout(min, prevTop, min + prevWidth, prevTop + this.mPrevText.getMeasuredHeight());
        int max2 = Math.max((stripWidth - paddingRight) - nextWidth, currRight + this.mScaledTextSpacing);
        int i13 = max2;
        this.mNextText.layout(max2, nextTop, max2 + nextWidth, nextTop + this.mNextText.getMeasuredHeight());
        this.mLastKnownPositionOffset = positionOffset;
        this.mUpdatingPositions = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int height;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int i5 = mode;
        if (mode == 1073741824) {
            int heightPadding = getPaddingTop() + getPaddingBottom();
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding, -2);
            int size = MeasureSpec.getSize(widthMeasureSpec);
            int widthSize = size;
            int childMeasureSpec = getChildMeasureSpec(widthMeasureSpec, (int) (((float) size) * 0.2f), -2);
            int i6 = childMeasureSpec;
            this.mPrevText.measure(childMeasureSpec, childHeightSpec);
            this.mCurrText.measure(childMeasureSpec, childHeightSpec);
            this.mNextText.measure(childMeasureSpec, childHeightSpec);
            int mode2 = MeasureSpec.getMode(heightMeasureSpec);
            int i7 = mode2;
            if (mode2 != 1073741824) {
                int textHeight = this.mCurrText.getMeasuredHeight();
                int minHeight = getMinHeight();
                int i8 = minHeight;
                height = Math.max(minHeight, textHeight + heightPadding);
            } else {
                height = MeasureSpec.getSize(heightMeasureSpec);
            }
            int resolveSizeAndState = ViewCompat.resolveSizeAndState(height, heightMeasureSpec, ViewCompat.getMeasuredState(this.mCurrText) << 16);
            int i9 = resolveSizeAndState;
            setMeasuredDimension(widthSize, resolveSizeAndState);
            return;
        }
        throw new IllegalStateException("Must measure with an exact width");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = z;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        if (this.mPager != null) {
            updateTextPositions(this.mLastKnownCurrentPage, this.mLastKnownPositionOffset >= 0.0f ? this.mLastKnownPositionOffset : 0.0f, true);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getMinHeight() {
        int minHeight = 0;
        Drawable background = getBackground();
        Drawable bg = background;
        if (background != null) {
            minHeight = bg.getIntrinsicHeight();
        }
        return minHeight;
    }
}
