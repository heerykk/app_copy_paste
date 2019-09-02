package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.design.C0001R;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.p000v4.content.res.ConfigurationHelper;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.widget.AppCompatImageHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@DefaultBehavior(Behavior.class)
public class FloatingActionButton extends VisibilityAwareImageButton {
    private static final int AUTO_MINI_LARGEST_SCREEN_WIDTH = 470;
    private static final String LOG_TAG = "FloatingActionButton";
    public static final int SIZE_AUTO = -1;
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    private ColorStateList mBackgroundTint;
    private Mode mBackgroundTintMode;
    private int mBorderWidth;
    boolean mCompatPadding;
    private AppCompatImageHelper mImageHelper;
    int mImagePadding;
    private FloatingActionButtonImpl mImpl;
    private int mMaxImageSize;
    private int mRippleColor;
    final Rect mShadowPadding;
    private int mSize;
    private final Rect mTouchArea;

    public static class Behavior extends android.support.design.widget.CoordinatorLayout.Behavior<FloatingActionButton> {
        private static final boolean AUTO_HIDE_DEFAULT = true;
        private boolean mAutoHideEnabled;
        private OnVisibilityChangedListener mInternalAutoHideListener;
        private Rect mTmpRect;

        public /* bridge */ /* synthetic */ boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull Rect rect) {
            return getInsetDodgeRect(coordinatorLayout, (FloatingActionButton) view, rect);
        }

        public /* bridge */ /* synthetic */ boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return onDependentViewChanged(coordinatorLayout, (FloatingActionButton) view, view2);
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            return onLayoutChild(coordinatorLayout, (FloatingActionButton) view, i);
        }

        public Behavior() {
            this.mAutoHideEnabled = AUTO_HIDE_DEFAULT;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            Context context2 = context;
            AttributeSet attrs = attributeSet;
            Context context3 = context2;
            AttributeSet attributeSet2 = attrs;
            super(context2, attrs);
            TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.FloatingActionButton_Behavior_Layout);
            this.mAutoHideEnabled = a.getBoolean(C0001R.styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, AUTO_HIDE_DEFAULT);
            a.recycle();
        }

        public void setAutoHideEnabled(boolean z) {
            this.mAutoHideEnabled = z;
        }

        public boolean isAutoHideEnabled() {
            return this.mAutoHideEnabled;
        }

        public void onAttachedToLayoutParams(@NonNull LayoutParams layoutParams) {
            LayoutParams lp = layoutParams;
            LayoutParams layoutParams2 = lp;
            if (lp.dodgeInsetEdges == 0) {
                lp.dodgeInsetEdges = 80;
            }
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            CoordinatorLayout parent = coordinatorLayout;
            FloatingActionButton child = floatingActionButton;
            View dependency = view;
            CoordinatorLayout coordinatorLayout2 = parent;
            FloatingActionButton floatingActionButton2 = child;
            View view2 = dependency;
            if (dependency instanceof AppBarLayout) {
                boolean updateFabVisibilityForAppBarLayout = updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child);
            } else if (isBottomSheet(dependency)) {
                boolean updateFabVisibilityForBottomSheet = updateFabVisibilityForBottomSheet(dependency, child);
            }
            return false;
        }

        private static boolean isBottomSheet(@NonNull View view) {
            View view2 = view;
            View view3 = view2;
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            ViewGroup.LayoutParams lp = layoutParams;
            if (!(layoutParams instanceof LayoutParams)) {
                return false;
            }
            return ((LayoutParams) lp).getBehavior() instanceof BottomSheetBehavior;
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public void setInternalAutoHideListener(OnVisibilityChangedListener onVisibilityChangedListener) {
            OnVisibilityChangedListener listener = onVisibilityChangedListener;
            OnVisibilityChangedListener onVisibilityChangedListener2 = listener;
            this.mInternalAutoHideListener = listener;
        }

        private boolean shouldUpdateVisibility(View view, FloatingActionButton floatingActionButton) {
            View dependency = view;
            FloatingActionButton child = floatingActionButton;
            View view2 = dependency;
            FloatingActionButton floatingActionButton2 = child;
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (!this.mAutoHideEnabled) {
                return false;
            }
            if (lp.getAnchorId() != dependency.getId()) {
                return false;
            }
            if (child.getUserSetVisibility() == 0) {
                return AUTO_HIDE_DEFAULT;
            }
            return false;
        }

        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            CoordinatorLayout parent = coordinatorLayout;
            AppBarLayout appBarLayout2 = appBarLayout;
            FloatingActionButton child = floatingActionButton;
            CoordinatorLayout coordinatorLayout2 = parent;
            AppBarLayout appBarLayout3 = appBarLayout2;
            FloatingActionButton floatingActionButton2 = child;
            if (!shouldUpdateVisibility(appBarLayout2, child)) {
                return false;
            }
            if (this.mTmpRect == null) {
                this.mTmpRect = new Rect();
            }
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect(parent, appBarLayout2, rect);
            if (rect.bottom > appBarLayout2.getMinimumHeightForVisibleOverlappingContent()) {
                child.show(this.mInternalAutoHideListener, false);
            } else {
                child.hide(this.mInternalAutoHideListener, false);
            }
            return AUTO_HIDE_DEFAULT;
        }

        private boolean updateFabVisibilityForBottomSheet(View view, FloatingActionButton floatingActionButton) {
            View bottomSheet = view;
            FloatingActionButton child = floatingActionButton;
            View view2 = bottomSheet;
            FloatingActionButton floatingActionButton2 = child;
            if (!shouldUpdateVisibility(bottomSheet, child)) {
                return false;
            }
            if (bottomSheet.getTop() >= (child.getHeight() / 2) + ((LayoutParams) child.getLayoutParams()).topMargin) {
                child.show(this.mInternalAutoHideListener, false);
            } else {
                child.hide(this.mInternalAutoHideListener, false);
            }
            return AUTO_HIDE_DEFAULT;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int i) {
            CoordinatorLayout parent = coordinatorLayout;
            FloatingActionButton child = floatingActionButton;
            int layoutDirection = i;
            CoordinatorLayout coordinatorLayout2 = parent;
            FloatingActionButton floatingActionButton2 = child;
            int i2 = layoutDirection;
            List dependencies = parent.getDependencies(child);
            List list = dependencies;
            int count = dependencies.size();
            for (int i3 = 0; i3 < count; i3++) {
                View view = (View) list.get(i3);
                View dependency = view;
                if (view instanceof AppBarLayout) {
                    if (updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child)) {
                        break;
                    }
                } else if (isBottomSheet(dependency) && updateFabVisibilityForBottomSheet(dependency, child)) {
                    break;
                }
            }
            parent.onLayoutChild(child, layoutDirection);
            offsetIfNeeded(parent, child);
            return AUTO_HIDE_DEFAULT;
        }

        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton floatingActionButton, @NonNull Rect rect) {
            FloatingActionButton child = floatingActionButton;
            Rect rect2 = rect;
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            FloatingActionButton floatingActionButton2 = child;
            Rect rect3 = rect2;
            Rect shadowPadding = child.mShadowPadding;
            rect2.set(child.getLeft() + shadowPadding.left, child.getTop() + shadowPadding.top, child.getRight() - shadowPadding.right, child.getBottom() - shadowPadding.bottom);
            return AUTO_HIDE_DEFAULT;
        }

        private void offsetIfNeeded(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            CoordinatorLayout parent = coordinatorLayout;
            FloatingActionButton fab = floatingActionButton;
            CoordinatorLayout coordinatorLayout2 = parent;
            FloatingActionButton floatingActionButton2 = fab;
            Rect rect = fab.mShadowPadding;
            Rect padding = rect;
            if (rect != null && padding.centerX() > 0 && padding.centerY() > 0) {
                LayoutParams lp = (LayoutParams) fab.getLayoutParams();
                int offsetTB = 0;
                int offsetLR = 0;
                if (fab.getRight() >= parent.getWidth() - lp.rightMargin) {
                    offsetLR = padding.right;
                } else if (fab.getLeft() <= lp.leftMargin) {
                    offsetLR = -padding.left;
                }
                if (fab.getBottom() >= parent.getHeight() - lp.bottomMargin) {
                    offsetTB = padding.bottom;
                } else if (fab.getTop() <= lp.topMargin) {
                    offsetTB = -padding.top;
                }
                if (offsetTB != 0) {
                    ViewCompat.offsetTopAndBottom(fab, offsetTB);
                }
                if (offsetLR != 0) {
                    ViewCompat.offsetLeftAndRight(fab, offsetLR);
                }
            }
        }
    }

    public static abstract class OnVisibilityChangedListener {
        public OnVisibilityChangedListener() {
        }

        public void onShown(FloatingActionButton floatingActionButton) {
            FloatingActionButton floatingActionButton2 = floatingActionButton;
        }

        public void onHidden(FloatingActionButton floatingActionButton) {
            FloatingActionButton floatingActionButton2 = floatingActionButton;
        }
    }

    private class ShadowDelegateImpl implements ShadowViewDelegate {
        final /* synthetic */ FloatingActionButton this$0;

        ShadowDelegateImpl(FloatingActionButton floatingActionButton) {
            this.this$0 = floatingActionButton;
        }

        public float getRadius() {
            return ((float) this.this$0.getSizeDimension()) / 2.0f;
        }

        public void setShadowPadding(int i, int i2, int i3, int i4) {
            int left = i;
            int top = i2;
            int right = i3;
            int bottom = i4;
            int i5 = left;
            int i6 = top;
            int i7 = right;
            int i8 = bottom;
            this.this$0.mShadowPadding.set(left, top, right, bottom);
            this.this$0.setPadding(left + this.this$0.mImagePadding, top + this.this$0.mImagePadding, right + this.this$0.mImagePadding, bottom + this.this$0.mImagePadding);
        }

        public void setBackgroundDrawable(Drawable drawable) {
            Drawable background = drawable;
            Drawable drawable2 = background;
            FloatingActionButton.access$001(this.this$0, background);
        }

        public boolean isCompatPaddingEnabled() {
            return this.this$0.mCompatPadding;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Size {
    }

    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    static /* synthetic */ void access$001(FloatingActionButton floatingActionButton, Drawable drawable) {
        FloatingActionButton x0 = floatingActionButton;
        Drawable x1 = drawable;
        FloatingActionButton floatingActionButton2 = x0;
        Drawable drawable2 = x1;
        super.setBackgroundDrawable(x1);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mShadowPadding = new Rect();
        this.mTouchArea = new Rect();
        ThemeUtils.checkAppCompatTheme(context2);
        TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.FloatingActionButton, defStyleAttr, C0001R.style.Widget_Design_FloatingActionButton);
        this.mBackgroundTint = a.getColorStateList(C0001R.styleable.FloatingActionButton_backgroundTint);
        this.mBackgroundTintMode = ViewUtils.parseTintMode(a.getInt(C0001R.styleable.FloatingActionButton_backgroundTintMode, -1), null);
        this.mRippleColor = a.getColor(C0001R.styleable.FloatingActionButton_rippleColor, 0);
        this.mSize = a.getInt(C0001R.styleable.FloatingActionButton_fabSize, -1);
        this.mBorderWidth = a.getDimensionPixelSize(C0001R.styleable.FloatingActionButton_borderWidth, 0);
        float elevation = a.getDimension(C0001R.styleable.FloatingActionButton_elevation, 0.0f);
        float dimension = a.getDimension(C0001R.styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        float f = dimension;
        this.mCompatPadding = a.getBoolean(C0001R.styleable.FloatingActionButton_useCompatPadding, false);
        a.recycle();
        this.mImageHelper = new AppCompatImageHelper(this);
        this.mImageHelper.loadFromAttributes(attrs, defStyleAttr);
        this.mMaxImageSize = (int) getResources().getDimension(C0001R.dimen.design_fab_image_size);
        getImpl().setBackgroundDrawable(this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        getImpl().setElevation(elevation);
        getImpl().setPressedTranslationZ(dimension);
    }

    public FloatingActionButton(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        int sizeDimension = getSizeDimension();
        int preferredSize = sizeDimension;
        this.mImagePadding = (sizeDimension - this.mMaxImageSize) / 2;
        getImpl().updatePadding();
        int min = Math.min(resolveAdjustedSize(sizeDimension, widthMeasureSpec), resolveAdjustedSize(preferredSize, heightMeasureSpec));
        int i5 = min;
        setMeasuredDimension(min + this.mShadowPadding.left + this.mShadowPadding.right, min + this.mShadowPadding.top + this.mShadowPadding.bottom);
    }

    @ColorInt
    public int getRippleColor() {
        return this.mRippleColor;
    }

    public void setRippleColor(@ColorInt int i) {
        int color = i;
        int i2 = color;
        if (this.mRippleColor != color) {
            this.mRippleColor = color;
            getImpl().setRippleColor(color);
        }
    }

    @Nullable
    public ColorStateList getBackgroundTintList() {
        return this.mBackgroundTint;
    }

    public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        if (this.mBackgroundTint != tint) {
            this.mBackgroundTint = tint;
            getImpl().setBackgroundTintList(tint);
        }
    }

    @Nullable
    public Mode getBackgroundTintMode() {
        return this.mBackgroundTintMode;
    }

    public void setBackgroundTintMode(@Nullable Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        if (this.mBackgroundTintMode != tintMode) {
            this.mBackgroundTintMode = tintMode;
            getImpl().setBackgroundTintMode(tintMode);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
        int i = Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int i) {
        int i2 = i;
        int i3 = Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setBackgroundColor(int i) {
        int i2 = i;
        int i3 = Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setImageResource(@DrawableRes int i) {
        int resId = i;
        int i2 = resId;
        this.mImageHelper.setImageResource(resId);
    }

    public void show() {
        show(null);
    }

    public void show(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        OnVisibilityChangedListener listener = onVisibilityChangedListener;
        OnVisibilityChangedListener onVisibilityChangedListener2 = listener;
        show(listener, true);
    }

    /* access modifiers changed from: 0000 */
    public void show(OnVisibilityChangedListener onVisibilityChangedListener, boolean z) {
        OnVisibilityChangedListener listener = onVisibilityChangedListener;
        OnVisibilityChangedListener onVisibilityChangedListener2 = listener;
        getImpl().show(wrapOnVisibilityChangedListener(listener), z);
    }

    public void hide() {
        hide(null);
    }

    public void hide(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        OnVisibilityChangedListener listener = onVisibilityChangedListener;
        OnVisibilityChangedListener onVisibilityChangedListener2 = listener;
        hide(listener, true);
    }

    /* access modifiers changed from: 0000 */
    public void hide(@Nullable OnVisibilityChangedListener onVisibilityChangedListener, boolean z) {
        OnVisibilityChangedListener listener = onVisibilityChangedListener;
        OnVisibilityChangedListener onVisibilityChangedListener2 = listener;
        getImpl().hide(wrapOnVisibilityChangedListener(listener), z);
    }

    public void setUseCompatPadding(boolean z) {
        boolean useCompatPadding = z;
        if (this.mCompatPadding != useCompatPadding) {
            this.mCompatPadding = useCompatPadding;
            getImpl().onCompatShadowChanged();
        }
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    public void setSize(int i) {
        int size = i;
        int i2 = size;
        if (size != this.mSize) {
            this.mSize = size;
            requestLayout();
        }
    }

    public int getSize() {
        return this.mSize;
    }

    @Nullable
    private InternalVisibilityChangedListener wrapOnVisibilityChangedListener(@Nullable OnVisibilityChangedListener onVisibilityChangedListener) {
        OnVisibilityChangedListener listener = onVisibilityChangedListener;
        OnVisibilityChangedListener onVisibilityChangedListener2 = listener;
        if (listener == null) {
            return null;
        }
        final OnVisibilityChangedListener onVisibilityChangedListener3 = listener;
        return new InternalVisibilityChangedListener(this) {
            final /* synthetic */ FloatingActionButton this$0;

            {
                FloatingActionButton this$02 = r6;
                OnVisibilityChangedListener onVisibilityChangedListener = r7;
                FloatingActionButton floatingActionButton = this$02;
                this.this$0 = this$02;
            }

            public void onShown() {
                onVisibilityChangedListener3.onShown(this.this$0);
            }

            public void onHidden() {
                onVisibilityChangedListener3.onHidden(this.this$0);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public int getSizeDimension() {
        return getSizeDimension(this.mSize);
    }

    private int getSizeDimension(int i) {
        int sizeDimension;
        int size = i;
        int i2 = size;
        Resources resources = getResources();
        Resources res = resources;
        switch (size) {
            case -1:
                if (Math.max(ConfigurationHelper.getScreenWidthDp(resources), ConfigurationHelper.getScreenHeightDp(res)) >= AUTO_MINI_LARGEST_SCREEN_WIDTH) {
                    sizeDimension = getSizeDimension(0);
                } else {
                    sizeDimension = getSizeDimension(1);
                }
                return sizeDimension;
            case 1:
                return resources.getDimensionPixelSize(C0001R.dimen.design_fab_size_mini);
            default:
                return resources.getDimensionPixelSize(C0001R.dimen.design_fab_size_normal);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getImpl().onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getImpl().onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().onDrawableStateChanged(getDrawableState());
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().jumpDrawableToCurrentState();
    }

    public boolean getContentRect(@NonNull Rect rect) {
        Rect rect2 = rect;
        Rect rect3 = rect2;
        if (!ViewCompat.isLaidOut(this)) {
            return false;
        }
        rect2.set(0, 0, getWidth(), getHeight());
        rect2.left += this.mShadowPadding.left;
        rect2.top += this.mShadowPadding.top;
        rect2.right -= this.mShadowPadding.right;
        rect2.bottom -= this.mShadowPadding.bottom;
        return true;
    }

    @NonNull
    public Drawable getContentBackground() {
        return getImpl().getContentBackground();
    }

    private static int resolveAdjustedSize(int i, int i2) {
        int desiredSize = i;
        int measureSpec = i2;
        int i3 = desiredSize;
        int i4 = measureSpec;
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
                result = Math.min(desiredSize, specSize);
                break;
            case 0:
                result = desiredSize;
                break;
            case 1073741824:
                result = specSize;
                break;
        }
        return result;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        switch (ev.getAction()) {
            case 0:
                if (getContentRect(this.mTouchArea) && !this.mTouchArea.contains((int) ev.getX(), (int) ev.getY())) {
                    return false;
                }
        }
        return super.onTouchEvent(ev);
    }

    public float getCompatElevation() {
        return getImpl().getElevation();
    }

    public void setCompatElevation(float f) {
        float elevation = f;
        float f2 = elevation;
        getImpl().setElevation(elevation);
    }

    private FloatingActionButtonImpl getImpl() {
        if (this.mImpl == null) {
            this.mImpl = createImpl();
        }
        return this.mImpl;
    }

    private FloatingActionButtonImpl createImpl() {
        int i = VERSION.SDK_INT;
        int sdk = i;
        if (i >= 21) {
            return new FloatingActionButtonLollipop(this, new ShadowDelegateImpl(this), ViewUtils.DEFAULT_ANIMATOR_CREATOR);
        } else if (sdk < 14) {
            return new FloatingActionButtonGingerbread(this, new ShadowDelegateImpl(this), ViewUtils.DEFAULT_ANIMATOR_CREATOR);
        } else {
            return new FloatingActionButtonIcs(this, new ShadowDelegateImpl(this), ViewUtils.DEFAULT_ANIMATOR_CREATOR);
        }
    }
}
