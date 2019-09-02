package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.text.AllCapsTransformationMethod;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.CompoundButton;

/* renamed from: android.support.v7.widget.SwitchCompat */
public class SwitchCompat extends CompoundButton {
    private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
    private static final int[] CHECKED_STATE_SET;
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private boolean mHasThumbTint;
    private boolean mHasThumbTintMode;
    private boolean mHasTrackTint;
    private boolean mHasTrackTintMode;
    private int mMinFlingVelocity;
    private Layout mOffLayout;
    private Layout mOnLayout;
    ThumbAnimation mPositionAnimator;
    private boolean mShowText;
    private boolean mSplitTrack;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private TransformationMethod mSwitchTransformationMethod;
    private int mSwitchWidth;
    private final Rect mTempRect;
    private ColorStateList mTextColors;
    private CharSequence mTextOff;
    private CharSequence mTextOn;
    private TextPaint mTextPaint;
    private Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbTextPadding;
    private ColorStateList mThumbTintList;
    private Mode mThumbTintMode;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackDrawable;
    private ColorStateList mTrackTintList;
    private Mode mTrackTintMode;
    private VelocityTracker mVelocityTracker;

    /* renamed from: android.support.v7.widget.SwitchCompat$ThumbAnimation */
    private class ThumbAnimation extends Animation {
        final float mDiff;
        final float mEndPosition;
        final float mStartPosition;
        final /* synthetic */ SwitchCompat this$0;

        ThumbAnimation(SwitchCompat switchCompat, float f, float f2) {
            float startPosition = f;
            float endPosition = f2;
            float f3 = startPosition;
            float f4 = endPosition;
            this.this$0 = switchCompat;
            this.mStartPosition = startPosition;
            this.mEndPosition = endPosition;
            this.mDiff = endPosition - startPosition;
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float f, Transformation transformation) {
            float interpolatedTime = f;
            float f2 = interpolatedTime;
            Transformation transformation2 = transformation;
            this.this$0.setThumbPosition(this.mStartPosition + (this.mDiff * interpolatedTime));
        }
    }

    public SwitchCompat(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mThumbTintList = null;
        this.mThumbTintMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mTrackTintList = null;
        this.mTrackTintMode = null;
        this.mHasTrackTint = false;
        this.mHasTrackTintMode = false;
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mTempRect = new Rect();
        this.mTextPaint = new TextPaint(1);
        Resources res = getResources();
        this.mTextPaint.density = res.getDisplayMetrics().density;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context2, attrs, C0268R.styleable.SwitchCompat, defStyleAttr, 0);
        this.mThumbDrawable = a.getDrawable(C0268R.styleable.SwitchCompat_android_thumb);
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(this);
        }
        this.mTrackDrawable = a.getDrawable(C0268R.styleable.SwitchCompat_track);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(this);
        }
        this.mTextOn = a.getText(C0268R.styleable.SwitchCompat_android_textOn);
        this.mTextOff = a.getText(C0268R.styleable.SwitchCompat_android_textOff);
        this.mShowText = a.getBoolean(C0268R.styleable.SwitchCompat_showText, true);
        this.mThumbTextPadding = a.getDimensionPixelSize(C0268R.styleable.SwitchCompat_thumbTextPadding, 0);
        this.mSwitchMinWidth = a.getDimensionPixelSize(C0268R.styleable.SwitchCompat_switchMinWidth, 0);
        this.mSwitchPadding = a.getDimensionPixelSize(C0268R.styleable.SwitchCompat_switchPadding, 0);
        this.mSplitTrack = a.getBoolean(C0268R.styleable.SwitchCompat_splitTrack, false);
        ColorStateList colorStateList = a.getColorStateList(C0268R.styleable.SwitchCompat_thumbTint);
        ColorStateList thumbTintList = colorStateList;
        if (colorStateList != null) {
            this.mThumbTintList = thumbTintList;
            this.mHasThumbTint = true;
        }
        Mode thumbTintMode = DrawableUtils.parseTintMode(a.getInt(C0268R.styleable.SwitchCompat_thumbTintMode, -1), null);
        if (this.mThumbTintMode != thumbTintMode) {
            this.mThumbTintMode = thumbTintMode;
            this.mHasThumbTintMode = true;
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            applyThumbTint();
        }
        ColorStateList colorStateList2 = a.getColorStateList(C0268R.styleable.SwitchCompat_trackTint);
        ColorStateList trackTintList = colorStateList2;
        if (colorStateList2 != null) {
            this.mTrackTintList = trackTintList;
            this.mHasTrackTint = true;
        }
        Mode trackTintMode = DrawableUtils.parseTintMode(a.getInt(C0268R.styleable.SwitchCompat_trackTintMode, -1), null);
        if (this.mTrackTintMode != trackTintMode) {
            this.mTrackTintMode = trackTintMode;
            this.mHasTrackTintMode = true;
        }
        if (this.mHasTrackTint || this.mHasTrackTintMode) {
            applyTrackTint();
        }
        int resourceId = a.getResourceId(C0268R.styleable.SwitchCompat_switchTextAppearance, 0);
        int appearance = resourceId;
        if (resourceId != 0) {
            setSwitchTextAppearance(context2, appearance);
        }
        a.recycle();
        ViewConfiguration config = ViewConfiguration.get(context2);
        this.mTouchSlop = config.getScaledTouchSlop();
        this.mMinFlingVelocity = config.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16842912;
        CHECKED_STATE_SET = iArr;
    }

    public SwitchCompat(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, C0268R.attr.switchStyle);
    }

    public void setSwitchTextAppearance(Context context, int i) {
        Context context2 = context;
        int resid = i;
        Context context3 = context2;
        int i2 = resid;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, resid, C0268R.styleable.TextAppearance);
        TintTypedArray appearance = obtainStyledAttributes;
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(C0268R.styleable.TextAppearance_android_textColor);
        ColorStateList colors = colorStateList;
        if (colorStateList == null) {
            this.mTextColors = getTextColors();
        } else {
            this.mTextColors = colors;
        }
        int dimensionPixelSize = appearance.getDimensionPixelSize(C0268R.styleable.TextAppearance_android_textSize, 0);
        int ts = dimensionPixelSize;
        if (!(dimensionPixelSize == 0 || ((float) ts) == this.mTextPaint.getTextSize())) {
            this.mTextPaint.setTextSize((float) ts);
            requestLayout();
        }
        int typefaceIndex = appearance.getInt(C0268R.styleable.TextAppearance_android_typeface, -1);
        int i3 = appearance.getInt(C0268R.styleable.TextAppearance_android_textStyle, -1);
        int i4 = i3;
        setSwitchTypefaceByIndex(typefaceIndex, i3);
        boolean z = appearance.getBoolean(C0268R.styleable.TextAppearance_textAllCaps, false);
        boolean z2 = z;
        if (!z) {
            this.mSwitchTransformationMethod = null;
        } else {
            AllCapsTransformationMethod allCapsTransformationMethod = new AllCapsTransformationMethod(getContext());
            this.mSwitchTransformationMethod = allCapsTransformationMethod;
        }
        appearance.recycle();
    }

    private void setSwitchTypefaceByIndex(int i, int i2) {
        int typefaceIndex = i;
        int styleIndex = i2;
        int i3 = typefaceIndex;
        int i4 = styleIndex;
        Typeface tf = null;
        switch (typefaceIndex) {
            case 1:
                tf = Typeface.SANS_SERIF;
                break;
            case 2:
                tf = Typeface.SERIF;
                break;
            case 3:
                tf = Typeface.MONOSPACE;
                break;
        }
        setSwitchTypeface(tf, styleIndex);
    }

    public void setSwitchTypeface(Typeface typeface, int i) {
        Typeface tf;
        Typeface tf2 = typeface;
        int style = i;
        Typeface typeface2 = tf2;
        int i2 = style;
        if (style <= 0) {
            this.mTextPaint.setFakeBoldText(false);
            this.mTextPaint.setTextSkewX(0.0f);
            setSwitchTypeface(tf2);
            return;
        }
        if (tf2 != null) {
            tf = Typeface.create(tf2, style);
        } else {
            tf = Typeface.defaultFromStyle(style);
        }
        setSwitchTypeface(tf);
        int style2 = style & ((tf == null ? 0 : tf.getStyle()) ^ -1);
        int i3 = style2;
        this.mTextPaint.setFakeBoldText((style2 & 1) != 0);
        this.mTextPaint.setTextSkewX((style2 & 2) == 0 ? 0.0f : -0.25f);
    }

    public void setSwitchTypeface(Typeface typeface) {
        Typeface tf = typeface;
        Typeface typeface2 = tf;
        if (this.mTextPaint.getTypeface() != tf) {
            Typeface typeface3 = this.mTextPaint.setTypeface(tf);
            requestLayout();
            invalidate();
        }
    }

    public void setSwitchPadding(int i) {
        int pixels = i;
        int i2 = pixels;
        this.mSwitchPadding = pixels;
        requestLayout();
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    public void setSwitchMinWidth(int i) {
        int pixels = i;
        int i2 = pixels;
        this.mSwitchMinWidth = pixels;
        requestLayout();
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    public void setThumbTextPadding(int i) {
        int pixels = i;
        int i2 = pixels;
        this.mThumbTextPadding = pixels;
        requestLayout();
    }

    public int getThumbTextPadding() {
        return this.mThumbTextPadding;
    }

    public void setTrackDrawable(Drawable drawable) {
        Drawable track = drawable;
        Drawable drawable2 = track;
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(null);
        }
        this.mTrackDrawable = track;
        if (track != null) {
            track.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int i) {
        int resId = i;
        int i2 = resId;
        setTrackDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    public void setTrackTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mTrackTintList = tint;
        this.mHasTrackTint = true;
        applyTrackTint();
    }

    @Nullable
    public ColorStateList getTrackTintList() {
        return this.mTrackTintList;
    }

    public void setTrackTintMode(@Nullable Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        this.mTrackTintMode = tintMode;
        this.mHasTrackTintMode = true;
        applyTrackTint();
    }

    @Nullable
    public Mode getTrackTintMode() {
        return this.mTrackTintMode;
    }

    private void applyTrackTint() {
        if (this.mTrackDrawable != null) {
            if (this.mHasTrackTint || this.mHasTrackTintMode) {
                this.mTrackDrawable = this.mTrackDrawable.mutate();
                if (this.mHasTrackTint) {
                    DrawableCompat.setTintList(this.mTrackDrawable, this.mTrackTintList);
                }
                if (this.mHasTrackTintMode) {
                    DrawableCompat.setTintMode(this.mTrackDrawable, this.mTrackTintMode);
                }
                if (this.mTrackDrawable.isStateful()) {
                    boolean state = this.mTrackDrawable.setState(getDrawableState());
                }
            }
        }
    }

    public void setThumbDrawable(Drawable drawable) {
        Drawable thumb = drawable;
        Drawable drawable2 = thumb;
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(null);
        }
        this.mThumbDrawable = thumb;
        if (thumb != null) {
            thumb.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbResource(int i) {
        int resId = i;
        int i2 = resId;
        setThumbDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public void setThumbTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mThumbTintList = tint;
        this.mHasThumbTint = true;
        applyThumbTint();
    }

    @Nullable
    public ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    public void setThumbTintMode(@Nullable Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        this.mThumbTintMode = tintMode;
        this.mHasThumbTintMode = true;
        applyThumbTint();
    }

    @Nullable
    public Mode getThumbTintMode() {
        return this.mThumbTintMode;
    }

    private void applyThumbTint() {
        if (this.mThumbDrawable != null) {
            if (this.mHasThumbTint || this.mHasThumbTintMode) {
                this.mThumbDrawable = this.mThumbDrawable.mutate();
                if (this.mHasThumbTint) {
                    DrawableCompat.setTintList(this.mThumbDrawable, this.mThumbTintList);
                }
                if (this.mHasThumbTintMode) {
                    DrawableCompat.setTintMode(this.mThumbDrawable, this.mThumbTintMode);
                }
                if (this.mThumbDrawable.isStateful()) {
                    boolean state = this.mThumbDrawable.setState(getDrawableState());
                }
            }
        }
    }

    public void setSplitTrack(boolean z) {
        this.mSplitTrack = z;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    public void setTextOn(CharSequence charSequence) {
        CharSequence textOn = charSequence;
        CharSequence charSequence2 = textOn;
        this.mTextOn = textOn;
        requestLayout();
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    public void setTextOff(CharSequence charSequence) {
        CharSequence textOff = charSequence;
        CharSequence charSequence2 = textOff;
        this.mTextOff = textOff;
        requestLayout();
    }

    public void setShowText(boolean z) {
        boolean showText = z;
        if (this.mShowText != showText) {
            this.mShowText = showText;
            requestLayout();
        }
    }

    public boolean getShowText() {
        return this.mShowText;
    }

    public void onMeasure(int i, int i2) {
        int thumbWidth;
        int thumbHeight;
        int maxTextWidth;
        int trackHeight;
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = makeLayout(this.mTextOn);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = makeLayout(this.mTextOff);
            }
        }
        Rect padding = this.mTempRect;
        if (this.mThumbDrawable == null) {
            thumbWidth = 0;
            thumbHeight = 0;
        } else {
            boolean padding2 = this.mThumbDrawable.getPadding(padding);
            thumbWidth = (this.mThumbDrawable.getIntrinsicWidth() - padding.left) - padding.right;
            thumbHeight = this.mThumbDrawable.getIntrinsicHeight();
        }
        if (!this.mShowText) {
            maxTextWidth = 0;
        } else {
            maxTextWidth = Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + (this.mThumbTextPadding * 2);
        }
        this.mThumbWidth = Math.max(maxTextWidth, thumbWidth);
        if (this.mTrackDrawable == null) {
            padding.setEmpty();
            trackHeight = 0;
        } else {
            boolean padding3 = this.mTrackDrawable.getPadding(padding);
            trackHeight = this.mTrackDrawable.getIntrinsicHeight();
        }
        int paddingLeft = padding.left;
        int paddingRight = padding.right;
        if (this.mThumbDrawable != null) {
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            Rect rect = opticalBounds;
            paddingLeft = Math.max(paddingLeft, opticalBounds.left);
            paddingRight = Math.max(paddingRight, opticalBounds.right);
        }
        int switchWidth = Math.max(this.mSwitchMinWidth, (2 * this.mThumbWidth) + paddingLeft + paddingRight);
        int max = Math.max(trackHeight, thumbHeight);
        int switchHeight = max;
        this.mSwitchWidth = switchWidth;
        this.mSwitchHeight = max;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int i5 = measuredHeight;
        if (measuredHeight < switchHeight) {
            setMeasuredDimension(ViewCompat.getMeasuredWidthAndState(this), switchHeight);
        }
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        super.onPopulateAccessibilityEvent(event);
        CharSequence text = !isChecked() ? this.mTextOff : this.mTextOn;
        if (text != null) {
            boolean add = event.getText().add(text);
        }
    }

    private Layout makeLayout(CharSequence charSequence) {
        CharSequence text = charSequence;
        CharSequence charSequence2 = text;
        CharSequence transformed = this.mSwitchTransformationMethod == null ? text : this.mSwitchTransformationMethod.getTransformation(text, this);
        StaticLayout staticLayout = new StaticLayout(transformed, this.mTextPaint, transformed == null ? 0 : (int) Math.ceil((double) Layout.getDesiredWidth(transformed, this.mTextPaint)), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        return staticLayout;
    }

    private boolean hitThumb(float f, float f2) {
        boolean z;
        float x = f;
        float y = f2;
        float f3 = x;
        float f4 = y;
        if (this.mThumbDrawable == null) {
            return false;
        }
        int thumbOffset = getThumbOffset();
        boolean padding = this.mThumbDrawable.getPadding(this.mTempRect);
        int thumbTop = this.mSwitchTop - this.mTouchSlop;
        int i = (this.mSwitchLeft + thumbOffset) - this.mTouchSlop;
        int thumbLeft = i;
        int thumbRight = i + this.mThumbWidth + this.mTempRect.left + this.mTempRect.right + this.mTouchSlop;
        int i2 = this.mSwitchBottom + this.mTouchSlop;
        int i3 = i2;
        if (x <= ((float) thumbLeft) || x >= ((float) thumbRight) || y <= ((float) thumbTop) || y >= ((float) i2)) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float dPos;
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        this.mVelocityTracker.addMovement(ev);
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        int i = actionMasked;
        switch (actionMasked) {
            case 0:
                float x = ev.getX();
                float y = ev.getY();
                float f = y;
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1;
                    this.mTouchX = x;
                    this.mTouchY = y;
                    break;
                }
            case 1:
            case 3:
                if (this.mTouchMode != 2) {
                    this.mTouchMode = 0;
                    this.mVelocityTracker.clear();
                    break;
                } else {
                    stopDrag(ev);
                    boolean onTouchEvent = super.onTouchEvent(ev);
                    return true;
                }
            case 2:
                switch (this.mTouchMode) {
                    case 1:
                        float x2 = ev.getX();
                        float y2 = ev.getY();
                        if ((Math.abs(x2 - this.mTouchX) > ((float) this.mTouchSlop)) || Math.abs(y2 - this.mTouchY) > ((float) this.mTouchSlop)) {
                            this.mTouchMode = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.mTouchX = x2;
                            this.mTouchY = y2;
                            return true;
                        }
                    case 2:
                        float x3 = ev.getX();
                        float x4 = x3;
                        int thumbScrollRange = getThumbScrollRange();
                        int thumbScrollRange2 = thumbScrollRange;
                        float f2 = x3 - this.mTouchX;
                        float f3 = f2;
                        if (thumbScrollRange == 0) {
                            dPos = f2 > 0.0f ? 1.0f : -1.0f;
                        } else {
                            dPos = f2 / ((float) thumbScrollRange2);
                        }
                        if (ViewUtils.isLayoutRtl(this)) {
                            dPos = -dPos;
                        }
                        float constrain = constrain(this.mThumbPosition + dPos, 0.0f, 1.0f);
                        float newPos = constrain;
                        if (constrain != this.mThumbPosition) {
                            this.mTouchX = x4;
                            setThumbPosition(newPos);
                        }
                        return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        MotionEvent obtain = MotionEvent.obtain(ev);
        MotionEvent cancel = obtain;
        obtain.setAction(3);
        boolean onTouchEvent = super.onTouchEvent(cancel);
        cancel.recycle();
    }

    private void stopDrag(MotionEvent motionEvent) {
        boolean newState;
        MotionEvent ev = motionEvent;
        MotionEvent motionEvent2 = ev;
        this.mTouchMode = 0;
        boolean commitChange = ev.getAction() == 1 && isEnabled();
        boolean oldState = isChecked();
        if (!commitChange) {
            newState = oldState;
        } else {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.mVelocityTracker.getXVelocity();
            float xvel = xVelocity;
            if (Math.abs(xVelocity) > ((float) this.mMinFlingVelocity)) {
                boolean z = !ViewUtils.isLayoutRtl(this) ? xvel > 0.0f : xvel < 0.0f;
                newState = z;
            } else {
                newState = getTargetCheckedState();
            }
        }
        if (newState != oldState) {
            playSoundEffect(0);
        }
        setChecked(newState);
        cancelSuperTouch(ev);
    }

    private void animateThumbToCheckedState(boolean z) {
        boolean newCheckedState = z;
        if (this.mPositionAnimator != null) {
            cancelPositionAnimator();
        }
        this.mPositionAnimator = new ThumbAnimation(this, this.mThumbPosition, !newCheckedState ? 0.0f : 1.0f);
        this.mPositionAnimator.setDuration(250);
        final boolean z2 = newCheckedState;
        this.mPositionAnimator.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ SwitchCompat this$0;

            {
                SwitchCompat this$02 = r8;
                SwitchCompat switchCompat = this$02;
                boolean z = r9;
                this.this$0 = this$02;
            }

            public void onAnimationStart(Animation animation) {
                Animation animation2 = animation;
            }

            public void onAnimationEnd(Animation animation) {
                Animation animation2 = animation;
                Animation animation3 = animation2;
                if (this.this$0.mPositionAnimator == animation2) {
                    this.this$0.setThumbPosition(!z2 ? 0.0f : 1.0f);
                    this.this$0.mPositionAnimator = null;
                }
            }

            public void onAnimationRepeat(Animation animation) {
                Animation animation2 = animation;
            }
        });
        startAnimation(this.mPositionAnimator);
    }

    private void cancelPositionAnimator() {
        if (this.mPositionAnimator != null) {
            clearAnimation();
            this.mPositionAnimator = null;
        }
    }

    private boolean getTargetCheckedState() {
        return this.mThumbPosition > 0.5f;
    }

    /* access modifiers changed from: 0000 */
    public void setThumbPosition(float f) {
        float position = f;
        float f2 = position;
        this.mThumbPosition = position;
        invalidate();
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setChecked(boolean z) {
        super.setChecked(z);
        boolean checked = isChecked();
        if (getWindowToken() != null && ViewCompat.isLaidOut(this) && isShown()) {
            animateThumbToCheckedState(checked);
            return;
        }
        cancelPositionAnimator();
        setThumbPosition(!checked ? 0.0f : 1.0f);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int switchLeft;
        int switchRight;
        int switchBottom;
        int switchTop;
        int left = i;
        int top = i2;
        int right = i3;
        int bottom = i4;
        int i5 = left;
        int i6 = top;
        int i7 = right;
        int i8 = bottom;
        super.onLayout(z, left, top, right, bottom);
        int opticalInsetLeft = 0;
        int opticalInsetRight = 0;
        if (this.mThumbDrawable != null) {
            Rect trackPadding = this.mTempRect;
            if (this.mTrackDrawable == null) {
                trackPadding.setEmpty();
            } else {
                boolean padding = this.mTrackDrawable.getPadding(trackPadding);
            }
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            Rect rect = opticalBounds;
            opticalInsetLeft = Math.max(0, opticalBounds.left - trackPadding.left);
            opticalInsetRight = Math.max(0, opticalBounds.right - trackPadding.right);
        }
        if (!ViewUtils.isLayoutRtl(this)) {
            int width = (getWidth() - getPaddingRight()) - opticalInsetRight;
            switchRight = width;
            switchLeft = (width - this.mSwitchWidth) + opticalInsetLeft + opticalInsetRight;
        } else {
            int paddingLeft = getPaddingLeft() + opticalInsetLeft;
            switchLeft = paddingLeft;
            switchRight = ((paddingLeft + this.mSwitchWidth) - opticalInsetLeft) - opticalInsetRight;
        }
        switch (getGravity() & 112) {
            case 16:
                int paddingTop = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.mSwitchHeight / 2);
                switchTop = paddingTop;
                switchBottom = paddingTop + this.mSwitchHeight;
                break;
            case 80:
                int height = getHeight() - getPaddingBottom();
                switchBottom = height;
                switchTop = height - this.mSwitchHeight;
                break;
            default:
                int paddingTop2 = getPaddingTop();
                switchTop = paddingTop2;
                switchBottom = paddingTop2 + this.mSwitchHeight;
                break;
        }
        this.mSwitchLeft = switchLeft;
        this.mSwitchTop = switchTop;
        this.mSwitchBottom = switchBottom;
        this.mSwitchRight = switchRight;
    }

    public void draw(Canvas canvas) {
        Rect thumbInsets;
        Canvas c = canvas;
        Canvas canvas2 = c;
        Rect padding = this.mTempRect;
        int switchLeft = this.mSwitchLeft;
        int switchTop = this.mSwitchTop;
        int switchRight = this.mSwitchRight;
        int switchBottom = this.mSwitchBottom;
        int thumbInitialLeft = switchLeft + getThumbOffset();
        if (this.mThumbDrawable == null) {
            thumbInsets = DrawableUtils.INSETS_NONE;
        } else {
            thumbInsets = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
        }
        if (this.mTrackDrawable != null) {
            boolean padding2 = this.mTrackDrawable.getPadding(padding);
            thumbInitialLeft += padding.left;
            int trackLeft = switchLeft;
            int trackTop = switchTop;
            int trackRight = switchRight;
            int trackBottom = switchBottom;
            if (thumbInsets != null) {
                if (thumbInsets.left > padding.left) {
                    trackLeft = switchLeft + (thumbInsets.left - padding.left);
                }
                if (thumbInsets.top > padding.top) {
                    trackTop = switchTop + (thumbInsets.top - padding.top);
                }
                if (thumbInsets.right > padding.right) {
                    trackRight = switchRight - (thumbInsets.right - padding.right);
                }
                if (thumbInsets.bottom > padding.bottom) {
                    trackBottom = switchBottom - (thumbInsets.bottom - padding.bottom);
                }
            }
            this.mTrackDrawable.setBounds(trackLeft, trackTop, trackRight, trackBottom);
        }
        if (this.mThumbDrawable != null) {
            boolean padding3 = this.mThumbDrawable.getPadding(padding);
            int thumbLeft = thumbInitialLeft - padding.left;
            int i = thumbInitialLeft + this.mThumbWidth + padding.right;
            int i2 = i;
            this.mThumbDrawable.setBounds(thumbLeft, switchTop, i, switchBottom);
            Drawable background = getBackground();
            Drawable background2 = background;
            if (background != null) {
                DrawableCompat.setHotspotBounds(background2, thumbLeft, switchTop, i, switchBottom);
            }
        }
        super.draw(c);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int cX;
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.onDraw(canvas2);
        Rect padding = this.mTempRect;
        Drawable drawable = this.mTrackDrawable;
        Drawable trackDrawable = drawable;
        if (drawable == null) {
            padding.setEmpty();
        } else {
            boolean padding2 = trackDrawable.getPadding(padding);
        }
        int switchInnerTop = this.mSwitchTop + padding.top;
        int switchInnerBottom = this.mSwitchBottom - padding.bottom;
        Drawable thumbDrawable = this.mThumbDrawable;
        if (trackDrawable != null) {
            if (this.mSplitTrack && thumbDrawable != null) {
                Rect insets = DrawableUtils.getOpticalBounds(thumbDrawable);
                thumbDrawable.copyBounds(padding);
                padding.left += insets.left;
                padding.right -= insets.right;
                int saveCount = canvas2.save();
                boolean clipRect = canvas2.clipRect(padding, Op.DIFFERENCE);
                trackDrawable.draw(canvas2);
                canvas2.restoreToCount(saveCount);
            } else {
                trackDrawable.draw(canvas2);
            }
        }
        int saveCount2 = canvas2.save();
        if (thumbDrawable != null) {
            thumbDrawable.draw(canvas2);
        }
        Layout switchText = !getTargetCheckedState() ? this.mOffLayout : this.mOnLayout;
        if (switchText != null) {
            int[] drawableState = getDrawableState();
            if (this.mTextColors != null) {
                this.mTextPaint.setColor(this.mTextColors.getColorForState(drawableState, 0));
            }
            this.mTextPaint.drawableState = drawableState;
            if (thumbDrawable == null) {
                cX = getWidth();
            } else {
                Rect bounds = thumbDrawable.getBounds();
                cX = bounds.left + bounds.right;
            }
            int height = ((switchInnerTop + switchInnerBottom) / 2) - (switchText.getHeight() / 2);
            int i = height;
            canvas2.translate((float) ((cX / 2) - (switchText.getWidth() / 2)), (float) height);
            switchText.draw(canvas2);
        }
        canvas2.restoreToCount(saveCount2);
    }

    public int getCompoundPaddingLeft() {
        if (!ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft();
        }
        int padding = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        if (!TextUtils.isEmpty(getText())) {
            padding += this.mSwitchPadding;
        }
        return padding;
    }

    public int getCompoundPaddingRight() {
        if (ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingRight();
        }
        int padding = super.getCompoundPaddingRight() + this.mSwitchWidth;
        if (!TextUtils.isEmpty(getText())) {
            padding += this.mSwitchPadding;
        }
        return padding;
    }

    private int getThumbOffset() {
        float thumbPosition;
        if (!ViewUtils.isLayoutRtl(this)) {
            thumbPosition = this.mThumbPosition;
        } else {
            thumbPosition = 1.0f - this.mThumbPosition;
        }
        return (int) ((thumbPosition * ((float) getThumbScrollRange())) + 0.5f);
    }

    private int getThumbScrollRange() {
        Rect insets;
        if (this.mTrackDrawable == null) {
            return 0;
        }
        Rect padding = this.mTempRect;
        boolean padding2 = this.mTrackDrawable.getPadding(padding);
        if (this.mThumbDrawable == null) {
            insets = DrawableUtils.INSETS_NONE;
        } else {
            insets = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
        }
        return ((((this.mSwitchWidth - this.mThumbWidth) - padding.left) - padding.right) - insets.left) - insets.right;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int extraSpace = i;
        int i2 = extraSpace;
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            int[] mergeDrawableStates = mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] state = getDrawableState();
        boolean changed = false;
        Drawable drawable = this.mThumbDrawable;
        Drawable thumbDrawable = drawable;
        if (drawable != null && thumbDrawable.isStateful()) {
            changed = false | thumbDrawable.setState(state);
        }
        Drawable drawable2 = this.mTrackDrawable;
        Drawable trackDrawable = drawable2;
        if (drawable2 != null && trackDrawable.isStateful()) {
            changed |= trackDrawable.setState(state);
        }
        if (changed) {
            invalidate();
        }
    }

    public void drawableHotspotChanged(float f, float f2) {
        float x = f;
        float y = f2;
        float f3 = x;
        float f4 = y;
        if (VERSION.SDK_INT >= 21) {
            super.drawableHotspotChanged(x, y);
        }
        if (this.mThumbDrawable != null) {
            DrawableCompat.setHotspot(this.mThumbDrawable, x, y);
        }
        if (this.mTrackDrawable != null) {
            DrawableCompat.setHotspot(this.mTrackDrawable, x, y);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        Drawable who = drawable;
        Drawable drawable2 = who;
        return super.verifyDrawable(who) || who == this.mThumbDrawable || who == this.mTrackDrawable;
    }

    public void jumpDrawablesToCurrentState() {
        float f;
        if (VERSION.SDK_INT >= 11) {
            super.jumpDrawablesToCurrentState();
            if (this.mThumbDrawable != null) {
                this.mThumbDrawable.jumpToCurrentState();
            }
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.jumpToCurrentState();
            }
            cancelPositionAnimator();
            if (!isChecked()) {
                f = 0.0f;
            } else {
                f = 1.0f;
            }
            setThumbPosition(f);
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(ACCESSIBILITY_EVENT_CLASS_NAME);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo info = accessibilityNodeInfo;
        AccessibilityNodeInfo accessibilityNodeInfo2 = info;
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(ACCESSIBILITY_EVENT_CLASS_NAME);
            CharSequence switchText = !isChecked() ? this.mTextOff : this.mTextOn;
            if (!TextUtils.isEmpty(switchText)) {
                CharSequence text = info.getText();
                CharSequence oldText = text;
                if (!TextUtils.isEmpty(text)) {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder newText = sb;
                    StringBuilder append = sb.append(oldText).append(' ').append(switchText);
                    info.setText(newText);
                    return;
                }
                info.setText(switchText);
            }
        }
    }

    private static float constrain(float f, float f2, float f3) {
        float amount = f;
        float low = f2;
        float high = f3;
        float f4 = amount;
        float f5 = low;
        float f6 = high;
        if (amount < low) {
            return low;
        }
        return amount > high ? high : amount;
    }
}
