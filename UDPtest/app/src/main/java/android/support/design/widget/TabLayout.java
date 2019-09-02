package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.design.C0001R;
import android.support.p000v4.util.Pools.Pool;
import android.support.p000v4.util.Pools.SimplePool;
import android.support.p000v4.util.Pools.SynchronizedPool;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.PagerAdapter;
import android.support.p000v4.view.PointerIconCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPager;
import android.support.p000v4.view.ViewPager.DecorView;
import android.support.p000v4.view.ViewPager.OnAdapterChangeListener;
import android.support.p000v4.view.ViewPager.OnPageChangeListener;
import android.support.p000v4.widget.TextViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@DecorView
public class TabLayout extends HorizontalScrollView {
    private static final int ANIMATION_DURATION = 300;
    static final int DEFAULT_GAP_TEXT_ICON = 8;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
    static final int FIXED_WRAP_GUTTER_MIN = 16;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    private static final int INVALID_WIDTH = -1;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    static final int MOTION_NON_ADJACENT_OFFSET = 24;
    private static final int TAB_MIN_WIDTH_MARGIN = 56;
    private static final Pool<Tab> sTabPool = new SynchronizedPool(16);
    private AdapterChangeListener mAdapterChangeListener;
    private int mContentInsetStart;
    private OnTabSelectedListener mCurrentVpSelectedListener;
    int mMode;
    private TabLayoutOnPageChangeListener mPageChangeListener;
    private PagerAdapter mPagerAdapter;
    private DataSetObserver mPagerAdapterObserver;
    private final int mRequestedTabMaxWidth;
    private final int mRequestedTabMinWidth;
    private ValueAnimatorCompat mScrollAnimator;
    private final int mScrollableTabMinWidth;
    private OnTabSelectedListener mSelectedListener;
    private final ArrayList<OnTabSelectedListener> mSelectedListeners;
    private Tab mSelectedTab;
    private boolean mSetupViewPagerImplicitly;
    final int mTabBackgroundResId;
    int mTabGravity;
    int mTabMaxWidth;
    int mTabPaddingBottom;
    int mTabPaddingEnd;
    int mTabPaddingStart;
    int mTabPaddingTop;
    private final SlidingTabStrip mTabStrip;
    int mTabTextAppearance;
    ColorStateList mTabTextColors;
    float mTabTextMultiLineSize;
    float mTabTextSize;
    private final Pool<TabView> mTabViewPool;
    private final ArrayList<Tab> mTabs;
    ViewPager mViewPager;

    private class AdapterChangeListener implements OnAdapterChangeListener {
        private boolean mAutoRefresh;
        final /* synthetic */ TabLayout this$0;

        AdapterChangeListener(TabLayout tabLayout) {
            this.this$0 = tabLayout;
        }

        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2) {
            ViewPager viewPager2 = viewPager;
            PagerAdapter newAdapter = pagerAdapter2;
            ViewPager viewPager3 = viewPager2;
            PagerAdapter pagerAdapter3 = pagerAdapter;
            PagerAdapter pagerAdapter4 = newAdapter;
            if (this.this$0.mViewPager == viewPager2) {
                this.this$0.setPagerAdapter(newAdapter, this.mAutoRefresh);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setAutoRefresh(boolean z) {
            this.mAutoRefresh = z;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public interface OnTabSelectedListener {
        void onTabReselected(Tab tab);

        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);
    }

    private class PagerAdapterObserver extends DataSetObserver {
        final /* synthetic */ TabLayout this$0;

        PagerAdapterObserver(TabLayout tabLayout) {
            this.this$0 = tabLayout;
        }

        public void onChanged() {
            this.this$0.populateFromPagerAdapter();
        }

        public void onInvalidated() {
            this.this$0.populateFromPagerAdapter();
        }
    }

    private class SlidingTabStrip extends LinearLayout {
        private ValueAnimatorCompat mIndicatorAnimator;
        private int mIndicatorLeft = -1;
        private int mIndicatorRight = -1;
        private int mSelectedIndicatorHeight;
        private final Paint mSelectedIndicatorPaint;
        int mSelectedPosition = -1;
        float mSelectionOffset;
        final /* synthetic */ TabLayout this$0;

        SlidingTabStrip(TabLayout tabLayout, Context context) {
            Context context2 = context;
            Context context3 = context2;
            this.this$0 = tabLayout;
            super(context2);
            setWillNotDraw(false);
            this.mSelectedIndicatorPaint = new Paint();
        }

        /* access modifiers changed from: 0000 */
        public void setSelectedIndicatorColor(int i) {
            int color = i;
            int i2 = color;
            if (this.mSelectedIndicatorPaint.getColor() != color) {
                this.mSelectedIndicatorPaint.setColor(color);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setSelectedIndicatorHeight(int i) {
            int height = i;
            int i2 = height;
            if (this.mSelectedIndicatorHeight != height) {
                this.mSelectedIndicatorHeight = height;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean childrenNeedLayout() {
            int z = getChildCount();
            for (int i = 0; i < z; i++) {
                View childAt = getChildAt(i);
                View view = childAt;
                if (childAt.getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void setIndicatorPositionFromTabPosition(int i, float f) {
            int position = i;
            float positionOffset = f;
            int i2 = position;
            float f2 = positionOffset;
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            this.mSelectedPosition = position;
            this.mSelectionOffset = positionOffset;
            updateIndicatorPosition();
        }

        /* access modifiers changed from: 0000 */
        public float getIndicatorPosition() {
            return ((float) this.mSelectedPosition) + this.mSelectionOffset;
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            int widthMeasureSpec = i;
            int heightMeasureSpec = i2;
            int i3 = widthMeasureSpec;
            int i4 = heightMeasureSpec;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (MeasureSpec.getMode(widthMeasureSpec) == 1073741824 && this.this$0.mMode == 1 && this.this$0.mTabGravity == 1) {
                int count = getChildCount();
                int largestTabWidth = 0;
                int i5 = count;
                for (int i6 = 0; i6 < count; i6++) {
                    View childAt = getChildAt(i6);
                    View child = childAt;
                    if (childAt.getVisibility() == 0) {
                        largestTabWidth = Math.max(largestTabWidth, child.getMeasuredWidth());
                    }
                }
                if (largestTabWidth > 0) {
                    boolean remeasure = false;
                    if (largestTabWidth * count > getMeasuredWidth() - (this.this$0.dpToPx(16) * 2)) {
                        this.this$0.mTabGravity = 0;
                        this.this$0.updateTabViews(false);
                        remeasure = true;
                    } else {
                        for (int i7 = 0; i7 < count; i7++) {
                            LayoutParams layoutParams = (LayoutParams) getChildAt(i7).getLayoutParams();
                            LayoutParams lp = layoutParams;
                            if (layoutParams.width != largestTabWidth || lp.weight != 0.0f) {
                                lp.width = largestTabWidth;
                                lp.weight = 0.0f;
                                remeasure = true;
                            }
                        }
                    }
                    if (remeasure) {
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int l = i;
            int t = i2;
            int r = i3;
            int b = i4;
            int i5 = l;
            int i6 = t;
            int i7 = r;
            int i8 = b;
            super.onLayout(z, l, t, r, b);
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
                long duration = this.mIndicatorAnimator.getDuration();
                long j = duration;
                animateIndicatorToPosition(this.mSelectedPosition, Math.round((1.0f - this.mIndicatorAnimator.getAnimatedFraction()) * ((float) duration)));
                return;
            }
            updateIndicatorPosition();
        }

        private void updateIndicatorPosition() {
            int left;
            int right;
            View childAt = getChildAt(this.mSelectedPosition);
            View selectedTitle = childAt;
            if (childAt != null && selectedTitle.getWidth() > 0) {
                left = selectedTitle.getLeft();
                right = selectedTitle.getRight();
                if (this.mSelectionOffset > 0.0f && this.mSelectedPosition < getChildCount() - 1) {
                    View nextTitle = getChildAt(this.mSelectedPosition + 1);
                    left = (int) ((this.mSelectionOffset * ((float) nextTitle.getLeft())) + ((1.0f - this.mSelectionOffset) * ((float) left)));
                    right = (int) ((this.mSelectionOffset * ((float) nextTitle.getRight())) + ((1.0f - this.mSelectionOffset) * ((float) right)));
                }
            } else {
                right = -1;
                left = -1;
            }
            setIndicatorPosition(left, right);
        }

        /* access modifiers changed from: 0000 */
        public void setIndicatorPosition(int i, int i2) {
            int left = i;
            int right = i2;
            int i3 = left;
            int i4 = right;
            if (left != this.mIndicatorLeft || right != this.mIndicatorRight) {
                this.mIndicatorLeft = left;
                this.mIndicatorRight = right;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void animateIndicatorToPosition(int i, int i2) {
            int startLeft;
            int startRight;
            final int position = i;
            int duration = i2;
            int i3 = position;
            int i4 = duration;
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
            View childAt = getChildAt(position);
            View targetView = childAt;
            if (childAt != null) {
                int targetLeft = targetView.getLeft();
                int targetRight = targetView.getRight();
                if (Math.abs(position - this.mSelectedPosition) > 1) {
                    int offset = this.this$0.dpToPx(24);
                    if (position >= this.mSelectedPosition) {
                        if (!isRtl) {
                            int i5 = targetLeft - offset;
                            startRight = i5;
                            startLeft = i5;
                        } else {
                            int i6 = targetRight + offset;
                            startRight = i6;
                            startLeft = i6;
                        }
                    } else if (!isRtl) {
                        int i7 = targetRight + offset;
                        startRight = i7;
                        startLeft = i7;
                    } else {
                        int i8 = targetLeft - offset;
                        startRight = i8;
                        startLeft = i8;
                    }
                } else {
                    startLeft = this.mIndicatorLeft;
                    startRight = this.mIndicatorRight;
                }
                if (!(startLeft == targetLeft && startRight == targetRight)) {
                    ValueAnimatorCompat createAnimator = ViewUtils.createAnimator();
                    this.mIndicatorAnimator = createAnimator;
                    ValueAnimatorCompat animator = createAnimator;
                    createAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                    createAnimator.setDuration((long) duration);
                    createAnimator.setFloatValues(0.0f, 1.0f);
                    ValueAnimatorCompat valueAnimatorCompat = createAnimator;
                    final int i9 = startLeft;
                    final int i10 = targetLeft;
                    final int i11 = startRight;
                    final int i12 = targetRight;
                    C00491 r0 = new AnimatorUpdateListener(this) {
                        final /* synthetic */ SlidingTabStrip this$1;

                        {
                            SlidingTabStrip this$12 = r9;
                            int i = r10;
                            int i2 = r11;
                            int i3 = r12;
                            int i4 = r13;
                            SlidingTabStrip slidingTabStrip = this$12;
                            this.this$1 = this$12;
                        }

                        public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                            ValueAnimatorCompat animator = valueAnimatorCompat;
                            ValueAnimatorCompat valueAnimatorCompat2 = animator;
                            float animatedFraction = animator.getAnimatedFraction();
                            float f = animatedFraction;
                            this.this$1.setIndicatorPosition(AnimationUtils.lerp(i9, i10, animatedFraction), AnimationUtils.lerp(i11, i12, animatedFraction));
                        }
                    };
                    valueAnimatorCompat.addUpdateListener(r0);
                    C00502 r02 = new AnimatorListenerAdapter(this) {
                        final /* synthetic */ SlidingTabStrip this$1;

                        {
                            SlidingTabStrip this$12 = r6;
                            int i = r7;
                            SlidingTabStrip slidingTabStrip = this$12;
                            this.this$1 = this$12;
                        }

                        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
                            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
                            this.this$1.mSelectedPosition = position;
                            this.this$1.mSelectionOffset = 0.0f;
                        }
                    };
                    animator.addListener(r02);
                    animator.start();
                }
                return;
            }
            updateIndicatorPosition();
        }

        public void draw(Canvas canvas) {
            Canvas canvas2 = canvas;
            Canvas canvas3 = canvas2;
            super.draw(canvas2);
            if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
                canvas2.drawRect((float) this.mIndicatorLeft, (float) (getHeight() - this.mSelectedIndicatorHeight), (float) this.mIndicatorRight, (float) getHeight(), this.mSelectedIndicatorPaint);
            }
        }
    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        TabLayout mParent;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;
        TabView mView;

        Tab() {
        }

        @Nullable
        public Object getTag() {
            return this.mTag;
        }

        @NonNull
        public Tab setTag(@Nullable Object obj) {
            Object tag = obj;
            Object obj2 = tag;
            this.mTag = tag;
            return this;
        }

        @Nullable
        public View getCustomView() {
            return this.mCustomView;
        }

        @NonNull
        public Tab setCustomView(@Nullable View view) {
            View view2 = view;
            View view3 = view2;
            this.mCustomView = view2;
            updateView();
            return this;
        }

        @NonNull
        public Tab setCustomView(@LayoutRes int i) {
            int resId = i;
            int i2 = resId;
            return setCustomView(LayoutInflater.from(this.mView.getContext()).inflate(resId, this.mView, false));
        }

        @Nullable
        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        /* access modifiers changed from: 0000 */
        public void setPosition(int i) {
            int position = i;
            int i2 = position;
            this.mPosition = position;
        }

        @Nullable
        public CharSequence getText() {
            return this.mText;
        }

        @NonNull
        public Tab setIcon(@Nullable Drawable drawable) {
            Drawable icon = drawable;
            Drawable drawable2 = icon;
            this.mIcon = icon;
            updateView();
            return this;
        }

        @NonNull
        public Tab setIcon(@DrawableRes int i) {
            int resId = i;
            int i2 = resId;
            if (this.mParent != null) {
                return setIcon(AppCompatResources.getDrawable(this.mParent.getContext(), resId));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setText(@Nullable CharSequence charSequence) {
            CharSequence text = charSequence;
            CharSequence charSequence2 = text;
            this.mText = text;
            updateView();
            return this;
        }

        @NonNull
        public Tab setText(@StringRes int i) {
            int resId = i;
            int i2 = resId;
            if (this.mParent != null) {
                return setText(this.mParent.getResources().getText(resId));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public void select() {
            if (this.mParent != null) {
                this.mParent.selectTab(this);
                return;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public boolean isSelected() {
            if (this.mParent != null) {
                return this.mParent.getSelectedTabPosition() == this.mPosition;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setContentDescription(@StringRes int i) {
            int resId = i;
            int i2 = resId;
            if (this.mParent != null) {
                return setContentDescription(this.mParent.getResources().getText(resId));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setContentDescription(@Nullable CharSequence charSequence) {
            CharSequence contentDesc = charSequence;
            CharSequence charSequence2 = contentDesc;
            this.mContentDesc = contentDesc;
            updateView();
            return this;
        }

        @Nullable
        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        /* access modifiers changed from: 0000 */
        public void updateView() {
            if (this.mView != null) {
                this.mView.update();
            }
        }

        /* access modifiers changed from: 0000 */
        public void reset() {
            this.mParent = null;
            this.mView = null;
            this.mTag = null;
            this.mIcon = null;
            this.mText = null;
            this.mContentDesc = null;
            this.mPosition = -1;
            this.mCustomView = null;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabGravity {
    }

    public static class TabLayoutOnPageChangeListener implements OnPageChangeListener {
        private int mPreviousScrollState;
        private int mScrollState;
        private final WeakReference<TabLayout> mTabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            TabLayout tabLayout2 = tabLayout;
            TabLayout tabLayout3 = tabLayout2;
            this.mTabLayoutRef = new WeakReference<>(tabLayout2);
        }

        public void onPageScrollStateChanged(int i) {
            int state = i;
            int i2 = state;
            this.mPreviousScrollState = this.mScrollState;
            this.mScrollState = state;
        }

        public void onPageScrolled(int i, float f, int i2) {
            int position = i;
            float positionOffset = f;
            int i3 = position;
            float f2 = positionOffset;
            int i4 = i2;
            TabLayout tabLayout = (TabLayout) this.mTabLayoutRef.get();
            TabLayout tabLayout2 = tabLayout;
            if (tabLayout != null) {
                tabLayout2.setScrollPosition(position, positionOffset, this.mScrollState != 2 || this.mPreviousScrollState == 1, (this.mScrollState == 2 && this.mPreviousScrollState == 0) ? false : true);
            }
        }

        public void onPageSelected(int i) {
            int position = i;
            int i2 = position;
            TabLayout tabLayout = (TabLayout) this.mTabLayoutRef.get();
            TabLayout tabLayout2 = tabLayout;
            if (tabLayout != null && tabLayout2.getSelectedTabPosition() != position && position < tabLayout2.getTabCount()) {
                tabLayout2.selectTab(tabLayout2.getTabAt(position), this.mScrollState == 0 || (this.mScrollState == 2 && this.mPreviousScrollState == 0));
            }
        }

        /* access modifiers changed from: 0000 */
        public void reset() {
            this.mScrollState = 0;
            this.mPreviousScrollState = 0;
        }
    }

    class TabView extends LinearLayout implements OnLongClickListener {
        private ImageView mCustomIconView;
        private TextView mCustomTextView;
        private View mCustomView;
        private int mDefaultMaxLines = 2;
        private ImageView mIconView;
        private Tab mTab;
        private TextView mTextView;
        final /* synthetic */ TabLayout this$0;

        public TabView(TabLayout tabLayout, Context context) {
            TabLayout this$02 = tabLayout;
            Context context2 = context;
            TabLayout tabLayout2 = this$02;
            Context context3 = context2;
            this.this$0 = this$02;
            super(context2);
            if (this$02.mTabBackgroundResId != 0) {
                ViewCompat.setBackground(this, AppCompatResources.getDrawable(context2, this$02.mTabBackgroundResId));
            }
            ViewCompat.setPaddingRelative(this, this$02.mTabPaddingStart, this$02.mTabPaddingTop, this$02.mTabPaddingEnd, this$02.mTabPaddingBottom);
            setGravity(17);
            setOrientation(1);
            setClickable(true);
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND));
        }

        public boolean performClick() {
            boolean performClick = super.performClick();
            boolean z = performClick;
            if (this.mTab == null) {
                return performClick;
            }
            if (!performClick) {
                playSoundEffect(0);
            }
            this.mTab.select();
            return true;
        }

        public void setSelected(boolean z) {
            boolean selected = z;
            boolean changed = isSelected() != selected;
            super.setSelected(selected);
            if (changed && selected && VERSION.SDK_INT < 16) {
                sendAccessibilityEvent(4);
            }
            if (this.mTextView != null) {
                this.mTextView.setSelected(selected);
            }
            if (this.mIconView != null) {
                this.mIconView.setSelected(selected);
            }
            if (this.mCustomView != null) {
                this.mCustomView.setSelected(selected);
            }
        }

        @TargetApi(14)
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            AccessibilityEvent event = accessibilityEvent;
            AccessibilityEvent accessibilityEvent2 = event;
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(android.support.p003v7.app.ActionBar.Tab.class.getName());
        }

        @TargetApi(14)
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            AccessibilityNodeInfo info = accessibilityNodeInfo;
            AccessibilityNodeInfo accessibilityNodeInfo2 = info;
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(android.support.p003v7.app.ActionBar.Tab.class.getName());
        }

        public void onMeasure(int i, int i2) {
            int widthMeasureSpec;
            int origWidthMeasureSpec = i;
            int origHeightMeasureSpec = i2;
            int i3 = origWidthMeasureSpec;
            int i4 = origHeightMeasureSpec;
            int specWidthSize = MeasureSpec.getSize(origWidthMeasureSpec);
            int specWidthMode = MeasureSpec.getMode(origWidthMeasureSpec);
            int maxWidth = this.this$0.getTabMaxWidth();
            int i5 = origHeightMeasureSpec;
            if (maxWidth > 0 && (specWidthMode == 0 || specWidthSize > maxWidth)) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.this$0.mTabMaxWidth, Integer.MIN_VALUE);
            } else {
                widthMeasureSpec = origWidthMeasureSpec;
            }
            super.onMeasure(widthMeasureSpec, origHeightMeasureSpec);
            if (this.mTextView != null) {
                Resources resources = getResources();
                float textSize = this.this$0.mTabTextSize;
                int maxLines = this.mDefaultMaxLines;
                if (this.mIconView != null && this.mIconView.getVisibility() == 0) {
                    maxLines = 1;
                } else if (this.mTextView != null && this.mTextView.getLineCount() > 1) {
                    textSize = this.this$0.mTabTextMultiLineSize;
                }
                float curTextSize = this.mTextView.getTextSize();
                int curLineCount = this.mTextView.getLineCount();
                int maxLines2 = TextViewCompat.getMaxLines(this.mTextView);
                int curMaxLines = maxLines2;
                if (textSize != curTextSize || (maxLines2 >= 0 && maxLines != curMaxLines)) {
                    boolean updateTextView = true;
                    if (this.this$0.mMode == 1 && textSize > curTextSize && curLineCount == 1) {
                        Layout layout = this.mTextView.getLayout();
                        Layout layout2 = layout;
                        if (layout == null || approximateLineWidth(layout2, 0, textSize) > ((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()))) {
                            updateTextView = false;
                        }
                    }
                    if (updateTextView) {
                        this.mTextView.setTextSize(0, textSize);
                        this.mTextView.setMaxLines(maxLines);
                        super.onMeasure(widthMeasureSpec, origHeightMeasureSpec);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void setTab(@Nullable Tab tab) {
            Tab tab2 = tab;
            Tab tab3 = tab2;
            if (tab2 != this.mTab) {
                this.mTab = tab2;
                update();
            }
        }

        /* access modifiers changed from: 0000 */
        public void reset() {
            setTab(null);
            setSelected(false);
        }

        /* access modifiers changed from: 0000 */
        public final void update() {
            Tab tab = this.mTab;
            Tab tab2 = tab;
            View custom = tab == null ? null : tab2.getCustomView();
            if (custom == null) {
                if (this.mCustomView != null) {
                    removeView(this.mCustomView);
                    this.mCustomView = null;
                }
                this.mCustomTextView = null;
                this.mCustomIconView = null;
            } else {
                ViewParent parent = custom.getParent();
                ViewParent customParent = parent;
                if (parent != this) {
                    if (customParent != null) {
                        ((ViewGroup) customParent).removeView(custom);
                    }
                    addView(custom);
                }
                this.mCustomView = custom;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
                this.mCustomTextView = (TextView) custom.findViewById(16908308);
                if (this.mCustomTextView != null) {
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(this.mCustomTextView);
                }
                this.mCustomIconView = (ImageView) custom.findViewById(16908294);
            }
            if (this.mCustomView == null) {
                if (this.mIconView == null) {
                    ImageView iconView = (ImageView) LayoutInflater.from(getContext()).inflate(C0001R.layout.design_layout_tab_icon, this, false);
                    addView(iconView, 0);
                    this.mIconView = iconView;
                }
                if (this.mTextView == null) {
                    TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(C0001R.layout.design_layout_tab_text, this, false);
                    addView(textView);
                    this.mTextView = textView;
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(this.mTextView);
                }
                TextViewCompat.setTextAppearance(this.mTextView, this.this$0.mTabTextAppearance);
                if (this.this$0.mTabTextColors != null) {
                    this.mTextView.setTextColor(this.this$0.mTabTextColors);
                }
                updateTextAndIcon(this.mTextView, this.mIconView);
            } else if (!(this.mCustomTextView == null && this.mCustomIconView == null)) {
                updateTextAndIcon(this.mCustomTextView, this.mCustomIconView);
            }
            setSelected(tab2 != null && tab2.isSelected());
        }

        private void updateTextAndIcon(@Nullable TextView textView, @Nullable ImageView imageView) {
            TextView textView2 = textView;
            ImageView iconView = imageView;
            TextView textView3 = textView2;
            ImageView imageView2 = iconView;
            Drawable icon = this.mTab == null ? null : this.mTab.getIcon();
            CharSequence text = this.mTab == null ? null : this.mTab.getText();
            CharSequence contentDesc = this.mTab == null ? null : this.mTab.getContentDescription();
            if (iconView != null) {
                if (icon == null) {
                    iconView.setVisibility(8);
                    iconView.setImageDrawable(null);
                } else {
                    iconView.setImageDrawable(icon);
                    iconView.setVisibility(0);
                    setVisibility(0);
                }
                iconView.setContentDescription(contentDesc);
            }
            boolean hasText = !TextUtils.isEmpty(text);
            if (textView2 != null) {
                if (!hasText) {
                    textView2.setVisibility(8);
                    textView2.setText(null);
                } else {
                    textView2.setText(text);
                    textView2.setVisibility(0);
                    setVisibility(0);
                }
                textView2.setContentDescription(contentDesc);
            }
            if (iconView != null) {
                MarginLayoutParams lp = (MarginLayoutParams) iconView.getLayoutParams();
                int bottomMargin = 0;
                if (hasText && iconView.getVisibility() == 0) {
                    bottomMargin = this.this$0.dpToPx(8);
                }
                if (bottomMargin != lp.bottomMargin) {
                    lp.bottomMargin = bottomMargin;
                    iconView.requestLayout();
                }
            }
            if (!hasText && !TextUtils.isEmpty(contentDesc)) {
                setOnLongClickListener(this);
                return;
            }
            setOnLongClickListener(null);
            setLongClickable(false);
        }

        public boolean onLongClick(View view) {
            View v = view;
            View view2 = v;
            int[] screenPos = new int[2];
            Rect displayFrame = new Rect();
            getLocationOnScreen(screenPos);
            getWindowVisibleDisplayFrame(displayFrame);
            Context context = getContext();
            int width = getWidth();
            int height = getHeight();
            int midy = screenPos[1] + (height / 2);
            int referenceX = screenPos[0] + (width / 2);
            if (ViewCompat.getLayoutDirection(v) == 0) {
                int i = context.getResources().getDisplayMetrics().widthPixels;
                int i2 = i;
                referenceX = i - referenceX;
            }
            Toast makeText = Toast.makeText(context, this.mTab.getContentDescription(), 0);
            Toast cheatSheet = makeText;
            if (midy >= displayFrame.height()) {
                makeText.setGravity(81, 0, height);
            } else {
                makeText.setGravity(8388661, referenceX, (screenPos[1] + height) - displayFrame.top);
            }
            cheatSheet.show();
            return true;
        }

        public Tab getTab() {
            return this.mTab;
        }

        private float approximateLineWidth(Layout layout, int i, float f) {
            Layout layout2 = layout;
            int line = i;
            float textSize = f;
            Layout layout3 = layout2;
            int i2 = line;
            float f2 = textSize;
            return layout2.getLineWidth(line) * (textSize / layout2.getPaint().getTextSize());
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            ViewPager viewPager2 = viewPager;
            ViewPager viewPager3 = viewPager2;
            this.mViewPager = viewPager2;
        }

        public void onTabSelected(Tab tab) {
            Tab tab2 = tab;
            Tab tab3 = tab2;
            this.mViewPager.setCurrentItem(tab2.getPosition());
        }

        public void onTabUnselected(Tab tab) {
            Tab tab2 = tab;
        }

        public void onTabReselected(Tab tab) {
            Tab tab2 = tab;
        }
    }

    /* JADX INFO: finally extract failed */
    public TabLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mTabs = new ArrayList<>();
        this.mTabMaxWidth = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mSelectedListeners = new ArrayList<>();
        this.mTabViewPool = new SimplePool(12);
        ThemeUtils.checkAppCompatTheme(context2);
        setHorizontalScrollBarEnabled(false);
        this.mTabStrip = new SlidingTabStrip(this, context2);
        super.addView(this.mTabStrip, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.TabLayout, defStyleAttr, C0001R.style.Widget_Design_TabLayout);
        this.mTabStrip.setSelectedIndicatorHeight(a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabIndicatorHeight, 0));
        this.mTabStrip.setSelectedIndicatorColor(a.getColor(C0001R.styleable.TabLayout_tabIndicatorColor, 0));
        int dimensionPixelSize = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPadding, 0);
        this.mTabPaddingBottom = dimensionPixelSize;
        int i3 = dimensionPixelSize;
        this.mTabPaddingEnd = dimensionPixelSize;
        this.mTabPaddingTop = i3;
        this.mTabPaddingStart = i3;
        this.mTabPaddingStart = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPaddingStart, this.mTabPaddingStart);
        this.mTabPaddingTop = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPaddingTop, this.mTabPaddingTop);
        this.mTabPaddingEnd = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPaddingEnd, this.mTabPaddingEnd);
        this.mTabPaddingBottom = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPaddingBottom, this.mTabPaddingBottom);
        this.mTabTextAppearance = a.getResourceId(C0001R.styleable.TabLayout_tabTextAppearance, C0001R.style.TextAppearance_Design_Tab);
        TypedArray ta = context2.obtainStyledAttributes(this.mTabTextAppearance, C0268R.styleable.TextAppearance);
        try {
            this.mTabTextSize = (float) ta.getDimensionPixelSize(C0268R.styleable.TextAppearance_android_textSize, 0);
            this.mTabTextColors = ta.getColorStateList(C0268R.styleable.TextAppearance_android_textColor);
            ta.recycle();
            if (a.hasValue(C0001R.styleable.TabLayout_tabTextColor)) {
                this.mTabTextColors = a.getColorStateList(C0001R.styleable.TabLayout_tabTextColor);
            }
            if (a.hasValue(C0001R.styleable.TabLayout_tabSelectedTextColor)) {
                int color = a.getColor(C0001R.styleable.TabLayout_tabSelectedTextColor, 0);
                int i4 = color;
                this.mTabTextColors = createColorStateList(this.mTabTextColors.getDefaultColor(), color);
            }
            this.mRequestedTabMinWidth = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabMinWidth, -1);
            this.mRequestedTabMaxWidth = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabMaxWidth, -1);
            this.mTabBackgroundResId = a.getResourceId(C0001R.styleable.TabLayout_tabBackground, 0);
            this.mContentInsetStart = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabContentStart, 0);
            this.mMode = a.getInt(C0001R.styleable.TabLayout_tabMode, 1);
            this.mTabGravity = a.getInt(C0001R.styleable.TabLayout_tabGravity, 0);
            a.recycle();
            Resources resources = getResources();
            this.mTabTextMultiLineSize = (float) resources.getDimensionPixelSize(C0001R.dimen.design_tab_text_size_2line);
            this.mScrollableTabMinWidth = resources.getDimensionPixelSize(C0001R.dimen.design_tab_scrollable_min_width);
            applyModeAndGravity();
        } catch (Throwable th) {
            ta.recycle();
            throw th;
        }
    }

    public TabLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void setSelectedTabIndicatorColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        this.mTabStrip.setSelectedIndicatorColor(color);
    }

    public void setSelectedTabIndicatorHeight(int i) {
        int height = i;
        int i2 = height;
        this.mTabStrip.setSelectedIndicatorHeight(height);
    }

    public void setScrollPosition(int i, float f, boolean z) {
        int position = i;
        float positionOffset = f;
        int i2 = position;
        float f2 = positionOffset;
        setScrollPosition(position, positionOffset, z, true);
    }

    /* access modifiers changed from: 0000 */
    public void setScrollPosition(int i, float f, boolean z, boolean z2) {
        int position = i;
        float positionOffset = f;
        int i2 = position;
        float f2 = positionOffset;
        boolean updateSelectedText = z;
        boolean updateIndicatorPosition = z2;
        int round = Math.round(((float) position) + positionOffset);
        int roundedPosition = round;
        if (round >= 0 && roundedPosition < this.mTabStrip.getChildCount()) {
            if (updateIndicatorPosition) {
                this.mTabStrip.setIndicatorPositionFromTabPosition(position, positionOffset);
            }
            if (this.mScrollAnimator != null && this.mScrollAnimator.isRunning()) {
                this.mScrollAnimator.cancel();
            }
            scrollTo(calculateScrollXForTab(position, positionOffset), 0);
            if (updateSelectedText) {
                setSelectedTabView(roundedPosition);
            }
        }
    }

    private float getScrollPosition() {
        return this.mTabStrip.getIndicatorPosition();
    }

    public void addTab(@NonNull Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        addTab(tab2, this.mTabs.isEmpty());
    }

    public void addTab(@NonNull Tab tab, int i) {
        Tab tab2 = tab;
        int position = i;
        Tab tab3 = tab2;
        int i2 = position;
        addTab(tab2, position, this.mTabs.isEmpty());
    }

    public void addTab(@NonNull Tab tab, boolean z) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        Tab tab4 = tab2;
        addTab(tab4, this.mTabs.size(), z);
    }

    public void addTab(@NonNull Tab tab, int i, boolean z) {
        Tab tab2 = tab;
        int position = i;
        Tab tab3 = tab2;
        int i2 = position;
        boolean setSelected = z;
        if (tab2.mParent == this) {
            configureTab(tab2, position);
            addTabView(tab2);
            if (setSelected) {
                tab2.select();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    private void addTabFromItemView(@NonNull TabItem tabItem) {
        TabItem item = tabItem;
        TabItem tabItem2 = item;
        Tab tab = newTab();
        if (item.mText != null) {
            Tab text = tab.setText(item.mText);
        }
        if (item.mIcon != null) {
            Tab icon = tab.setIcon(item.mIcon);
        }
        if (item.mCustomLayout != 0) {
            Tab customView = tab.setCustomView(item.mCustomLayout);
        }
        if (!TextUtils.isEmpty(item.getContentDescription())) {
            Tab contentDescription = tab.setContentDescription(item.getContentDescription());
        }
        addTab(tab);
    }

    @Deprecated
    public void setOnTabSelectedListener(@Nullable OnTabSelectedListener onTabSelectedListener) {
        OnTabSelectedListener listener = onTabSelectedListener;
        OnTabSelectedListener onTabSelectedListener2 = listener;
        if (this.mSelectedListener != null) {
            removeOnTabSelectedListener(this.mSelectedListener);
        }
        this.mSelectedListener = listener;
        if (listener != null) {
            addOnTabSelectedListener(listener);
        }
    }

    public void addOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        OnTabSelectedListener listener = onTabSelectedListener;
        OnTabSelectedListener onTabSelectedListener2 = listener;
        if (!this.mSelectedListeners.contains(listener)) {
            boolean add = this.mSelectedListeners.add(listener);
        }
    }

    public void removeOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        OnTabSelectedListener listener = onTabSelectedListener;
        OnTabSelectedListener onTabSelectedListener2 = listener;
        boolean remove = this.mSelectedListeners.remove(listener);
    }

    public void clearOnTabSelectedListeners() {
        this.mSelectedListeners.clear();
    }

    @NonNull
    public Tab newTab() {
        Tab tab = (Tab) sTabPool.acquire();
        Tab tab2 = tab;
        if (tab == null) {
            tab2 = new Tab();
        }
        tab2.mParent = this;
        tab2.mView = createTabView(tab2);
        return tab2;
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    @Nullable
    public Tab getTabAt(int i) {
        int index = i;
        int i2 = index;
        return (index >= 0 && index < getTabCount()) ? (Tab) this.mTabs.get(index) : null;
    }

    public int getSelectedTabPosition() {
        return this.mSelectedTab == null ? -1 : this.mSelectedTab.getPosition();
    }

    public void removeTab(Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        if (tab2.mParent == this) {
            removeTabAt(tab2.getPosition());
            return;
        }
        throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
    }

    public void removeTabAt(int i) {
        int position = i;
        int i2 = position;
        int selectedTabPosition = this.mSelectedTab == null ? 0 : this.mSelectedTab.getPosition();
        removeTabViewAt(position);
        Tab tab = (Tab) this.mTabs.remove(position);
        Tab removedTab = tab;
        if (tab != null) {
            removedTab.reset();
            boolean release = sTabPool.release(removedTab);
        }
        int newTabCount = this.mTabs.size();
        for (int i3 = position; i3 < newTabCount; i3++) {
            ((Tab) this.mTabs.get(i3)).setPosition(i3);
        }
        if (selectedTabPosition == position) {
            selectTab(!this.mTabs.isEmpty() ? (Tab) this.mTabs.get(Math.max(0, position - 1)) : null);
        }
    }

    public void removeAllTabs() {
        for (int i = this.mTabStrip.getChildCount() - 1; i >= 0; i--) {
            removeTabViewAt(i);
        }
        Iterator it = this.mTabs.iterator();
        while (it.hasNext()) {
            Tab tab = (Tab) it.next();
            it.remove();
            tab.reset();
            boolean release = sTabPool.release(tab);
        }
        this.mSelectedTab = null;
    }

    public void setTabMode(int i) {
        int mode = i;
        int i2 = mode;
        if (mode != this.mMode) {
            this.mMode = mode;
            applyModeAndGravity();
        }
    }

    public int getTabMode() {
        return this.mMode;
    }

    public void setTabGravity(int i) {
        int gravity = i;
        int i2 = gravity;
        if (this.mTabGravity != gravity) {
            this.mTabGravity = gravity;
            applyModeAndGravity();
        }
    }

    public int getTabGravity() {
        return this.mTabGravity;
    }

    public void setTabTextColors(@Nullable ColorStateList colorStateList) {
        ColorStateList textColor = colorStateList;
        ColorStateList colorStateList2 = textColor;
        if (this.mTabTextColors != textColor) {
            this.mTabTextColors = textColor;
            updateAllTabs();
        }
    }

    @Nullable
    public ColorStateList getTabTextColors() {
        return this.mTabTextColors;
    }

    public void setTabTextColors(int i, int i2) {
        int normalColor = i;
        int selectedColor = i2;
        int i3 = normalColor;
        int i4 = selectedColor;
        setTabTextColors(createColorStateList(normalColor, selectedColor));
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        ViewPager viewPager2 = viewPager;
        ViewPager viewPager3 = viewPager2;
        setupWithViewPager(viewPager2, true);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean z) {
        ViewPager viewPager2 = viewPager;
        ViewPager viewPager3 = viewPager2;
        setupWithViewPager(viewPager2, z, false);
    }

    private void setupWithViewPager(@Nullable ViewPager viewPager, boolean z, boolean z2) {
        ViewPager viewPager2 = viewPager;
        ViewPager viewPager3 = viewPager2;
        boolean autoRefresh = z;
        boolean implicitSetup = z2;
        if (this.mViewPager != null) {
            if (this.mPageChangeListener != null) {
                this.mViewPager.removeOnPageChangeListener(this.mPageChangeListener);
            }
            if (this.mAdapterChangeListener != null) {
                this.mViewPager.removeOnAdapterChangeListener(this.mAdapterChangeListener);
            }
        }
        if (this.mCurrentVpSelectedListener != null) {
            removeOnTabSelectedListener(this.mCurrentVpSelectedListener);
            this.mCurrentVpSelectedListener = null;
        }
        if (viewPager2 == null) {
            this.mViewPager = null;
            setPagerAdapter(null, false);
        } else {
            this.mViewPager = viewPager2;
            if (this.mPageChangeListener == null) {
                this.mPageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            this.mPageChangeListener.reset();
            viewPager2.addOnPageChangeListener(this.mPageChangeListener);
            this.mCurrentVpSelectedListener = new ViewPagerOnTabSelectedListener(viewPager2);
            addOnTabSelectedListener(this.mCurrentVpSelectedListener);
            PagerAdapter adapter = viewPager2.getAdapter();
            PagerAdapter adapter2 = adapter;
            if (adapter != null) {
                setPagerAdapter(adapter2, autoRefresh);
            }
            if (this.mAdapterChangeListener == null) {
                this.mAdapterChangeListener = new AdapterChangeListener(this);
            }
            this.mAdapterChangeListener.setAutoRefresh(autoRefresh);
            viewPager2.addOnAdapterChangeListener(this.mAdapterChangeListener);
            setScrollPosition(viewPager2.getCurrentItem(), 0.0f, true);
        }
        this.mSetupViewPagerImplicitly = implicitSetup;
    }

    @Deprecated
    public void setTabsFromPagerAdapter(@Nullable PagerAdapter pagerAdapter) {
        PagerAdapter adapter = pagerAdapter;
        PagerAdapter pagerAdapter2 = adapter;
        setPagerAdapter(adapter, false);
    }

    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mViewPager == null) {
            ViewParent parent = getParent();
            ViewParent vp = parent;
            if (parent instanceof ViewPager) {
                setupWithViewPager((ViewPager) vp, true, true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mSetupViewPagerImplicitly) {
            setupWithViewPager(null);
            this.mSetupViewPagerImplicitly = false;
        }
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.mTabStrip.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    /* access modifiers changed from: 0000 */
    public void setPagerAdapter(@Nullable PagerAdapter pagerAdapter, boolean z) {
        PagerAdapter adapter = pagerAdapter;
        PagerAdapter pagerAdapter2 = adapter;
        boolean addObserver = z;
        if (!(this.mPagerAdapter == null || this.mPagerAdapterObserver == null)) {
            this.mPagerAdapter.unregisterDataSetObserver(this.mPagerAdapterObserver);
        }
        this.mPagerAdapter = adapter;
        if (addObserver && adapter != null) {
            if (this.mPagerAdapterObserver == null) {
                this.mPagerAdapterObserver = new PagerAdapterObserver(this);
            }
            adapter.registerDataSetObserver(this.mPagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    /* access modifiers changed from: 0000 */
    public void populateFromPagerAdapter() {
        removeAllTabs();
        if (this.mPagerAdapter != null) {
            int adapterCount = this.mPagerAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                addTab(newTab().setText(this.mPagerAdapter.getPageTitle(i)), false);
            }
            if (this.mViewPager != null && adapterCount > 0) {
                int currentItem = this.mViewPager.getCurrentItem();
                int curItem = currentItem;
                if (currentItem != getSelectedTabPosition() && curItem < getTabCount()) {
                    selectTab(getTabAt(curItem));
                }
            }
        }
    }

    private void updateAllTabs() {
        int z = this.mTabs.size();
        for (int i = 0; i < z; i++) {
            ((Tab) this.mTabs.get(i)).updateView();
        }
    }

    private TabView createTabView(@NonNull Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        TabView tabView = this.mTabViewPool == null ? null : (TabView) this.mTabViewPool.acquire();
        if (tabView == null) {
            tabView = new TabView(this, getContext());
        }
        tabView.setTab(tab2);
        tabView.setFocusable(true);
        tabView.setMinimumWidth(getTabMinWidth());
        return tabView;
    }

    private void configureTab(Tab tab, int i) {
        Tab tab2 = tab;
        int position = i;
        Tab tab3 = tab2;
        int i2 = position;
        tab2.setPosition(position);
        this.mTabs.add(position, tab2);
        int count = this.mTabs.size();
        for (int i3 = position + 1; i3 < count; i3++) {
            ((Tab) this.mTabs.get(i3)).setPosition(i3);
        }
    }

    private void addTabView(Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        this.mTabStrip.addView(tab2.mView, tab2.getPosition(), createLayoutParamsForTabs());
    }

    public void addView(View view) {
        View child = view;
        View view2 = child;
        addViewInternal(child);
    }

    public void addView(View view, int i) {
        View child = view;
        View view2 = child;
        int i2 = i;
        addViewInternal(child);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        View child = view;
        View view2 = child;
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        addViewInternal(child);
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        View child = view;
        View view2 = child;
        int i2 = i;
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        addViewInternal(child);
    }

    private void addViewInternal(View view) {
        View child = view;
        View view2 = child;
        if (!(child instanceof TabItem)) {
            throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
        }
        addTabFromItemView((TabItem) child);
    }

    private LayoutParams createLayoutParamsForTabs() {
        LayoutParams lp = new LayoutParams(-2, -1);
        updateTabViewLayoutParams(lp);
        return lp;
    }

    private void updateTabViewLayoutParams(LayoutParams layoutParams) {
        LayoutParams lp = layoutParams;
        LayoutParams layoutParams2 = lp;
        if (this.mMode == 1 && this.mTabGravity == 0) {
            lp.width = 0;
            lp.weight = 1.0f;
            return;
        }
        lp.width = -2;
        lp.weight = 0.0f;
    }

    /* access modifiers changed from: 0000 */
    public int dpToPx(int i) {
        int dps = i;
        int i2 = dps;
        return Math.round(getResources().getDisplayMetrics().density * ((float) dps));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i4 = widthMeasureSpec;
        int heightMeasureSpec2 = heightMeasureSpec;
        int idealHeight = dpToPx(getDefaultHeight()) + getPaddingTop() + getPaddingBottom();
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case Integer.MIN_VALUE:
                heightMeasureSpec2 = MeasureSpec.makeMeasureSpec(Math.min(idealHeight, MeasureSpec.getSize(heightMeasureSpec)), 1073741824);
                break;
            case 0:
                heightMeasureSpec2 = MeasureSpec.makeMeasureSpec(idealHeight, 1073741824);
                break;
        }
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) != 0) {
            if (this.mRequestedTabMaxWidth <= 0) {
                i3 = specWidth - dpToPx(56);
            } else {
                i3 = this.mRequestedTabMaxWidth;
            }
            this.mTabMaxWidth = i3;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec2);
        if (getChildCount() == 1) {
            View child = getChildAt(0);
            boolean remeasure = false;
            switch (this.mMode) {
                case 0:
                    remeasure = child.getMeasuredWidth() < getMeasuredWidth();
                    break;
                case 1:
                    remeasure = child.getMeasuredWidth() != getMeasuredWidth();
                    break;
            }
            if (remeasure) {
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec2, getPaddingTop() + getPaddingBottom(), child.getLayoutParams().height);
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
                int i5 = makeMeasureSpec;
                child.measure(makeMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    private void removeTabViewAt(int i) {
        int position = i;
        int i2 = position;
        TabView view = (TabView) this.mTabStrip.getChildAt(position);
        this.mTabStrip.removeViewAt(position);
        if (view != null) {
            view.reset();
            boolean release = this.mTabViewPool.release(view);
        }
        requestLayout();
    }

    private void animateToTab(int i) {
        int newPosition = i;
        int i2 = newPosition;
        if (newPosition == -1) {
            return;
        }
        if (getWindowToken() != null && ViewCompat.isLaidOut(this) && !this.mTabStrip.childrenNeedLayout()) {
            int startScrollX = getScrollX();
            int targetScrollX = calculateScrollXForTab(newPosition, 0.0f);
            if (startScrollX != targetScrollX) {
                if (this.mScrollAnimator == null) {
                    this.mScrollAnimator = ViewUtils.createAnimator();
                    this.mScrollAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                    this.mScrollAnimator.setDuration(300);
                    ValueAnimatorCompat valueAnimatorCompat = this.mScrollAnimator;
                    C00481 r0 = new AnimatorUpdateListener(this) {
                        final /* synthetic */ TabLayout this$0;

                        {
                            TabLayout this$02 = r5;
                            TabLayout tabLayout = this$02;
                            this.this$0 = this$02;
                        }

                        public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                            ValueAnimatorCompat animator = valueAnimatorCompat;
                            ValueAnimatorCompat valueAnimatorCompat2 = animator;
                            this.this$0.scrollTo(animator.getAnimatedIntValue(), 0);
                        }
                    };
                    valueAnimatorCompat.addUpdateListener(r0);
                }
                this.mScrollAnimator.setIntValues(startScrollX, targetScrollX);
                this.mScrollAnimator.start();
            }
            this.mTabStrip.animateIndicatorToPosition(newPosition, ANIMATION_DURATION);
            return;
        }
        setScrollPosition(newPosition, 0.0f, true);
    }

    private void setSelectedTabView(int i) {
        int position = i;
        int i2 = position;
        int tabCount = this.mTabStrip.getChildCount();
        if (position < tabCount) {
            int i3 = 0;
            while (i3 < tabCount) {
                View childAt = this.mTabStrip.getChildAt(i3);
                View view = childAt;
                childAt.setSelected(i3 == position);
                i3++;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void selectTab(Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        selectTab(tab2, true);
    }

    /* access modifiers changed from: 0000 */
    public void selectTab(Tab tab, boolean z) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        boolean updateIndicator = z;
        Tab tab4 = this.mSelectedTab;
        Tab currentTab = tab4;
        if (tab4 != tab2) {
            int newPosition = tab2 == null ? -1 : tab2.getPosition();
            if (updateIndicator) {
                if (!(currentTab == null || currentTab.getPosition() == -1) || newPosition == -1) {
                    animateToTab(newPosition);
                } else {
                    setScrollPosition(newPosition, 0.0f, true);
                }
                if (newPosition != -1) {
                    setSelectedTabView(newPosition);
                }
            }
            if (currentTab != null) {
                dispatchTabUnselected(currentTab);
            }
            this.mSelectedTab = tab2;
            if (tab2 != null) {
                dispatchTabSelected(tab2);
            }
        } else if (currentTab != null) {
            dispatchTabReselected(tab2);
            animateToTab(tab2.getPosition());
        }
    }

    private void dispatchTabSelected(@NonNull Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--) {
            ((OnTabSelectedListener) this.mSelectedListeners.get(i)).onTabSelected(tab2);
        }
    }

    private void dispatchTabUnselected(@NonNull Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--) {
            ((OnTabSelectedListener) this.mSelectedListeners.get(i)).onTabUnselected(tab2);
        }
    }

    private void dispatchTabReselected(@NonNull Tab tab) {
        Tab tab2 = tab;
        Tab tab3 = tab2;
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--) {
            ((OnTabSelectedListener) this.mSelectedListeners.get(i)).onTabReselected(tab2);
        }
    }

    private int calculateScrollXForTab(int i, float f) {
        int position = i;
        float positionOffset = f;
        int i2 = position;
        float f2 = positionOffset;
        if (this.mMode != 0) {
            return 0;
        }
        View selectedChild = this.mTabStrip.getChildAt(position);
        View nextChild = position + 1 >= this.mTabStrip.getChildCount() ? null : this.mTabStrip.getChildAt(position + 1);
        return ((selectedChild.getLeft() + ((int) ((((float) ((selectedChild == null ? 0 : selectedChild.getWidth()) + (nextChild == null ? 0 : nextChild.getWidth()))) * positionOffset) * 0.5f))) + (selectedChild.getWidth() / 2)) - (getWidth() / 2);
    }

    private void applyModeAndGravity() {
        int paddingStart = 0;
        if (this.mMode == 0) {
            paddingStart = Math.max(0, this.mContentInsetStart - this.mTabPaddingStart);
        }
        ViewCompat.setPaddingRelative(this.mTabStrip, paddingStart, 0, 0, 0);
        switch (this.mMode) {
            case 0:
                this.mTabStrip.setGravity(GravityCompat.START);
                break;
            case 1:
                this.mTabStrip.setGravity(1);
                break;
        }
        updateTabViews(true);
    }

    /* access modifiers changed from: 0000 */
    public void updateTabViews(boolean z) {
        boolean requestLayout = z;
        for (int i = 0; i < this.mTabStrip.getChildCount(); i++) {
            View childAt = this.mTabStrip.getChildAt(i);
            View child = childAt;
            childAt.setMinimumWidth(getTabMinWidth());
            updateTabViewLayoutParams((LayoutParams) child.getLayoutParams());
            if (requestLayout) {
                child.requestLayout();
            }
        }
    }

    private static ColorStateList createColorStateList(int i, int i2) {
        int defaultColor = i;
        int selectedColor = i2;
        int i3 = defaultColor;
        int i4 = selectedColor;
        int[][] states = new int[2][];
        states[0] = SELECTED_STATE_SET;
        states[1] = EMPTY_STATE_SET;
        int i5 = 1 + 1;
        return new ColorStateList(states, new int[]{selectedColor, defaultColor});
    }

    private int getDefaultHeight() {
        int i;
        boolean hasIconAndText = false;
        int i2 = 0;
        int count = this.mTabs.size();
        while (true) {
            if (i2 >= count) {
                break;
            }
            Tab tab = (Tab) this.mTabs.get(i2);
            Tab tab2 = tab;
            if (tab != null && tab2.getIcon() != null && !TextUtils.isEmpty(tab2.getText())) {
                hasIconAndText = true;
                break;
            }
            i2++;
        }
        if (!hasIconAndText) {
            i = 48;
        } else {
            i = 72;
        }
        return i;
    }

    private int getTabMinWidth() {
        if (this.mRequestedTabMinWidth != -1) {
            return this.mRequestedTabMinWidth;
        }
        return this.mMode != 0 ? 0 : this.mScrollableTabMinWidth;
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AttributeSet attributeSet2 = attributeSet;
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: 0000 */
    public int getTabMaxWidth() {
        return this.mTabMaxWidth;
    }
}
