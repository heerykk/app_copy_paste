package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0001R;
import android.support.p000v4.view.OnApplyWindowInsetsListener;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ScrimInsetsFrameLayout extends FrameLayout {
    Drawable mInsetForeground;
    Rect mInsets;
    private Rect mTempRect;

    public ScrimInsetsFrameLayout(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mTempRect = new Rect();
        TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.ScrimInsetsFrameLayout, defStyleAttr, C0001R.style.Widget_Design_ScrimInsetsFrameLayout);
        this.mInsetForeground = a.getDrawable(C0001R.styleable.ScrimInsetsFrameLayout_insetForeground);
        a.recycle();
        setWillNotDraw(true);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener(this) {
            final /* synthetic */ ScrimInsetsFrameLayout this$0;

            {
                ScrimInsetsFrameLayout this$02 = r5;
                ScrimInsetsFrameLayout scrimInsetsFrameLayout = this$02;
                this.this$0 = this$02;
            }

            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat insets = windowInsetsCompat;
                View view2 = view;
                WindowInsetsCompat windowInsetsCompat2 = insets;
                if (null == this.this$0.mInsets) {
                    this.this$0.mInsets = new Rect();
                }
                this.this$0.mInsets.set(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                this.this$0.onInsetsChanged(insets);
                this.this$0.setWillNotDraw(!insets.hasSystemWindowInsets() || this.this$0.mInsetForeground == null);
                ViewCompat.postInvalidateOnAnimation(this.this$0);
                return insets.consumeSystemWindowInsets();
            }
        });
    }

    public ScrimInsetsFrameLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public void draw(@NonNull Canvas canvas) {
        Canvas canvas2 = canvas;
        Canvas canvas3 = canvas2;
        super.draw(canvas2);
        int width = getWidth();
        int height = getHeight();
        if (this.mInsets != null && this.mInsetForeground != null) {
            int save = canvas2.save();
            int i = save;
            canvas2.translate((float) getScrollX(), (float) getScrollY());
            this.mTempRect.set(0, 0, width, this.mInsets.top);
            this.mInsetForeground.setBounds(this.mTempRect);
            this.mInsetForeground.draw(canvas2);
            this.mTempRect.set(0, height - this.mInsets.bottom, width, height);
            this.mInsetForeground.setBounds(this.mTempRect);
            this.mInsetForeground.draw(canvas2);
            this.mTempRect.set(0, this.mInsets.top, this.mInsets.left, height - this.mInsets.bottom);
            this.mInsetForeground.setBounds(this.mTempRect);
            this.mInsetForeground.draw(canvas2);
            this.mTempRect.set(width - this.mInsets.right, this.mInsets.top, width, height - this.mInsets.bottom);
            this.mInsetForeground.setBounds(this.mTempRect);
            this.mInsetForeground.draw(canvas2);
            canvas2.restoreToCount(save);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mInsetForeground != null) {
            this.mInsetForeground.setCallback(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mInsetForeground != null) {
            this.mInsetForeground.setCallback(null);
        }
    }

    /* access modifiers changed from: protected */
    public void onInsetsChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
    }
}
