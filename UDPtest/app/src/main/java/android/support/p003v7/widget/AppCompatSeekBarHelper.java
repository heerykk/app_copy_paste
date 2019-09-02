package android.support.p003v7.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.appcompat.C0268R;
import android.util.AttributeSet;
import android.widget.SeekBar;

/* renamed from: android.support.v7.widget.AppCompatSeekBarHelper */
class AppCompatSeekBarHelper extends AppCompatProgressBarHelper {
    private boolean mHasTickMarkTint = false;
    private boolean mHasTickMarkTintMode = false;
    private Drawable mTickMark;
    private ColorStateList mTickMarkTintList = null;
    private Mode mTickMarkTintMode = null;
    private final SeekBar mView;

    AppCompatSeekBarHelper(SeekBar seekBar) {
        SeekBar view = seekBar;
        SeekBar seekBar2 = view;
        super(view);
        this.mView = view;
    }

    /* access modifiers changed from: 0000 */
    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super.loadFromAttributes(attrs, defStyleAttr);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attrs, C0268R.styleable.AppCompatSeekBar, defStyleAttr, 0);
        TintTypedArray a = obtainStyledAttributes;
        Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(C0268R.styleable.AppCompatSeekBar_android_thumb);
        Drawable drawable = drawableIfKnown;
        if (drawableIfKnown != null) {
            this.mView.setThumb(drawable);
        }
        setTickMark(a.getDrawable(C0268R.styleable.AppCompatSeekBar_tickMark));
        if (a.hasValue(C0268R.styleable.AppCompatSeekBar_tickMarkTintMode)) {
            this.mTickMarkTintMode = DrawableUtils.parseTintMode(a.getInt(C0268R.styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.mTickMarkTintMode);
            this.mHasTickMarkTintMode = true;
        }
        if (a.hasValue(C0268R.styleable.AppCompatSeekBar_tickMarkTint)) {
            this.mTickMarkTintList = a.getColorStateList(C0268R.styleable.AppCompatSeekBar_tickMarkTint);
            this.mHasTickMarkTint = true;
        }
        a.recycle();
        applyTickMarkTint();
    }

    /* access modifiers changed from: 0000 */
    public void setTickMark(@Nullable Drawable drawable) {
        Drawable tickMark = drawable;
        Drawable drawable2 = tickMark;
        if (this.mTickMark != null) {
            this.mTickMark.setCallback(null);
        }
        this.mTickMark = tickMark;
        if (tickMark != null) {
            tickMark.setCallback(this.mView);
            boolean layoutDirection = DrawableCompat.setLayoutDirection(tickMark, ViewCompat.getLayoutDirection(this.mView));
            if (tickMark.isStateful()) {
                boolean state = tickMark.setState(this.mView.getDrawableState());
            }
            applyTickMarkTint();
        }
        this.mView.invalidate();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Drawable getTickMark() {
        return this.mTickMark;
    }

    /* access modifiers changed from: 0000 */
    public void setTickMarkTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        ColorStateList colorStateList2 = tint;
        this.mTickMarkTintList = tint;
        this.mHasTickMarkTint = true;
        applyTickMarkTint();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public ColorStateList getTickMarkTintList() {
        return this.mTickMarkTintList;
    }

    /* access modifiers changed from: 0000 */
    public void setTickMarkTintMode(@Nullable Mode mode) {
        Mode tintMode = mode;
        Mode mode2 = tintMode;
        this.mTickMarkTintMode = tintMode;
        this.mHasTickMarkTintMode = true;
        applyTickMarkTint();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Mode getTickMarkTintMode() {
        return this.mTickMarkTintMode;
    }

    private void applyTickMarkTint() {
        if (this.mTickMark != null) {
            if (this.mHasTickMarkTint || this.mHasTickMarkTintMode) {
                this.mTickMark = DrawableCompat.wrap(this.mTickMark.mutate());
                if (this.mHasTickMarkTint) {
                    DrawableCompat.setTintList(this.mTickMark, this.mTickMarkTintList);
                }
                if (this.mHasTickMarkTintMode) {
                    DrawableCompat.setTintMode(this.mTickMark, this.mTickMarkTintMode);
                }
                if (this.mTickMark.isStateful()) {
                    boolean state = this.mTickMark.setState(this.mView.getDrawableState());
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(11)
    @RequiresApi(11)
    public void jumpDrawablesToCurrentState() {
        if (this.mTickMark != null) {
            this.mTickMark.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: 0000 */
    public void drawableStateChanged() {
        Drawable drawable = this.mTickMark;
        Drawable tickMark = drawable;
        if (drawable != null && tickMark.isStateful() && tickMark.setState(this.mView.getDrawableState())) {
            this.mView.invalidateDrawable(tickMark);
        }
    }

    /* access modifiers changed from: 0000 */
    public void drawTickMarks(Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        if (this.mTickMark != null) {
            int max = this.mView.getMax();
            int count = max;
            if (max > 1) {
                int w = this.mTickMark.getIntrinsicWidth();
                int h = this.mTickMark.getIntrinsicHeight();
                int halfW = w < 0 ? 1 : w / 2;
                int halfH = h < 0 ? 1 : h / 2;
                this.mTickMark.setBounds(-halfW, -halfH, halfW, halfH);
                float width = ((float) ((this.mView.getWidth() - this.mView.getPaddingLeft()) - this.mView.getPaddingRight())) / ((float) count);
                float f = width;
                int saveCount = canvas2.save();
                canvas2.translate((float) this.mView.getPaddingLeft(), (float) (this.mView.getHeight() / 2));
                for (int i = 0; i <= count; i++) {
                    this.mTickMark.draw(canvas2);
                    canvas2.translate(width, 0.0f);
                }
                canvas2.restoreToCount(saveCount);
            }
        }
    }
}
