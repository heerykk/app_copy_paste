package android.support.p000v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.view.ViewCompat;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

/* renamed from: android.support.v4.widget.CircleImageView */
class CircleImageView extends ImageView {
    private static final int FILL_SHADOW_COLOR = 1023410176;
    private static final int KEY_SHADOW_COLOR = 503316480;
    private static final int SHADOW_ELEVATION = 4;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final float X_OFFSET = 0.0f;
    private static final float Y_OFFSET = 1.75f;
    private AnimationListener mListener;
    int mShadowRadius;

    /* renamed from: android.support.v4.widget.CircleImageView$OvalShadow */
    private class OvalShadow extends OvalShape {
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint = new Paint();
        final /* synthetic */ CircleImageView this$0;

        OvalShadow(CircleImageView circleImageView, int i) {
            CircleImageView circleImageView2 = circleImageView;
            int shadowRadius = i;
            int i2 = shadowRadius;
            this.this$0 = circleImageView2;
            circleImageView2.mShadowRadius = shadowRadius;
            updateRadialGradient((int) rect().width());
        }

        /* access modifiers changed from: protected */
        public void onResize(float f, float f2) {
            float width = f;
            float height = f2;
            float f3 = width;
            float f4 = height;
            super.onResize(width, height);
            updateRadialGradient((int) width);
        }

        public void draw(Canvas canvas, Paint paint) {
            Canvas canvas2 = canvas;
            Paint paint2 = paint;
            Canvas canvas3 = canvas2;
            Paint paint3 = paint2;
            int viewWidth = this.this$0.getWidth();
            int height = this.this$0.getHeight();
            int i = height;
            canvas2.drawCircle((float) (viewWidth / 2), (float) (height / 2), (float) (viewWidth / 2), this.mShadowPaint);
            canvas2.drawCircle((float) (viewWidth / 2), (float) (height / 2), (float) ((viewWidth / 2) - this.this$0.mShadowRadius), paint2);
        }

        private void updateRadialGradient(int i) {
            int diameter = i;
            int i2 = diameter;
            RadialGradient radialGradient = new RadialGradient((float) (diameter / 2), (float) (diameter / 2), (float) this.this$0.mShadowRadius, new int[]{CircleImageView.FILL_SHADOW_COLOR, 0}, null, TileMode.CLAMP);
            this.mRadialGradient = radialGradient;
            Shader shader = this.mShadowPaint.setShader(this.mRadialGradient);
        }
    }

    CircleImageView(Context context, int i) {
        ShapeDrawable circle;
        Context context2 = context;
        int color = i;
        Context context3 = context2;
        int i2 = color;
        super(context2);
        float f = getContext().getResources().getDisplayMetrics().density;
        float density = f;
        int shadowYOffset = (int) (f * Y_OFFSET);
        int shadowXOffset = (int) (density * 0.0f);
        this.mShadowRadius = (int) (density * SHADOW_RADIUS);
        if (!elevationSupported()) {
            circle = new ShapeDrawable(new OvalShadow(this, this.mShadowRadius));
            ViewCompat.setLayerType(this, 1, circle.getPaint());
            circle.getPaint().setShadowLayer((float) this.mShadowRadius, (float) shadowXOffset, (float) shadowYOffset, KEY_SHADOW_COLOR);
            int i3 = this.mShadowRadius;
            int i4 = i3;
            setPadding(i3, i3, i3, i3);
        } else {
            circle = new ShapeDrawable(new OvalShape());
            ViewCompat.setElevation(this, 4.0f * density);
        }
        circle.getPaint().setColor(color);
        ViewCompat.setBackground(this, circle);
    }

    private boolean elevationSupported() {
        return VERSION.SDK_INT >= 21;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!elevationSupported()) {
            setMeasuredDimension(getMeasuredWidth() + (this.mShadowRadius * 2), getMeasuredHeight() + (this.mShadowRadius * 2));
        }
    }

    public void setAnimationListener(AnimationListener animationListener) {
        AnimationListener listener = animationListener;
        AnimationListener animationListener2 = listener;
        this.mListener = listener;
    }

    public void onAnimationStart() {
        super.onAnimationStart();
        if (this.mListener != null) {
            this.mListener.onAnimationStart(getAnimation());
        }
    }

    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (this.mListener != null) {
            this.mListener.onAnimationEnd(getAnimation());
        }
    }

    public void setBackgroundColorRes(int i) {
        int colorRes = i;
        int i2 = colorRes;
        setBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
    }

    public void setBackgroundColor(int i) {
        int color = i;
        int i2 = color;
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(color);
        }
    }
}
