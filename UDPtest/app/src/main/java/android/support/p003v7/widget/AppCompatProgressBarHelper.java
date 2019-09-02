package android.support.p003v7.widget;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/* renamed from: android.support.v7.widget.AppCompatProgressBarHelper */
class AppCompatProgressBarHelper {
    private static final int[] TINT_ATTRS;
    private Bitmap mSampleTile;
    private final ProgressBar mView;

    static {
        int[] iArr = new int[2];
        iArr[0] = 16843067;
        iArr[1] = 16843068;
        TINT_ATTRS = iArr;
    }

    AppCompatProgressBarHelper(ProgressBar progressBar) {
        ProgressBar view = progressBar;
        ProgressBar progressBar2 = view;
        this.mView = view;
    }

    /* access modifiers changed from: 0000 */
    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attrs, TINT_ATTRS, defStyleAttr, 0);
        TintTypedArray a = obtainStyledAttributes;
        Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(0);
        Drawable drawable = drawableIfKnown;
        if (drawableIfKnown != null) {
            this.mView.setIndeterminateDrawable(tileifyIndeterminate(drawable));
        }
        Drawable drawableIfKnown2 = a.getDrawableIfKnown(1);
        Drawable drawable2 = drawableIfKnown2;
        if (drawableIfKnown2 != null) {
            this.mView.setProgressDrawable(tileify(drawable2, false));
        }
        a.recycle();
    }

    /* JADX WARNING: type inference failed for: r13v10, types: [android.graphics.drawable.ShapeDrawable] */
    /* JADX WARNING: type inference failed for: r22v1, types: [android.graphics.drawable.ShapeDrawable] */
    /* JADX WARNING: type inference failed for: r13v16, types: [android.graphics.drawable.ClipDrawable] */
    /* JADX WARNING: type inference failed for: r48v0 */
    /* JADX WARNING: type inference failed for: r0v25, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: type inference failed for: r13v17 */
    /* JADX WARNING: type inference failed for: r49v0, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: type inference failed for: r13v18 */
    /* JADX WARNING: type inference failed for: r13v19 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable tileify(android.graphics.drawable.Drawable r52, boolean r53) {
        /*
            r51 = this;
            r3 = r51
            r4 = r52
            r5 = r53
            r6 = r3
            r7 = r4
            r8 = r5
            boolean r9 = r4 instanceof android.support.p000v4.graphics.drawable.DrawableWrapper
            r10 = r9
            r11 = r10
            r12 = 0
            if (r11 != r12) goto L_0x0029
            boolean r0 = r4 instanceof android.graphics.drawable.LayerDrawable
            r19 = r0
            r20 = r19
            r11 = r20
            r12 = 0
            if (r11 != r12) goto L_0x004f
            boolean r0 = r4 instanceof android.graphics.drawable.BitmapDrawable
            r36 = r0
            r37 = r36
            r11 = r37
            r12 = 0
            if (r11 != r12) goto L_0x00c2
        L_0x0026:
            r50 = r4
            return r50
        L_0x0029:
            r0 = r4
            android.support.v4.graphics.drawable.DrawableWrapper r0 = (android.support.p000v4.graphics.drawable.DrawableWrapper) r0
            r13 = r0
            android.graphics.drawable.Drawable r13 = r13.getWrappedDrawable()
            r14 = r13
            r15 = 0
            if (r13 != r15) goto L_0x0036
        L_0x0035:
            goto L_0x0026
        L_0x0036:
            r16 = r14
            r17 = r8
            r0 = r16
            r1 = r17
            android.graphics.drawable.Drawable r13 = r3.tileify(r0, r1)
            r14 = r13
            r0 = r4
            android.support.v4.graphics.drawable.DrawableWrapper r0 = (android.support.p000v4.graphics.drawable.DrawableWrapper) r0
            r13 = r0
            r18 = r14
            r0 = r18
            r13.setWrappedDrawable(r0)
            goto L_0x0035
        L_0x004f:
            r0 = r4
            android.graphics.drawable.LayerDrawable r0 = (android.graphics.drawable.LayerDrawable) r0
            r13 = r0
            r14 = r13
            int r11 = r13.getNumberOfLayers()
            r21 = r11
            android.graphics.drawable.Drawable[] r13 = new android.graphics.drawable.Drawable[r11]
            r22 = r13
            r23 = 0
        L_0x0060:
            r0 = r23
            r1 = r21
            if (r0 < r1) goto L_0x007c
            android.graphics.drawable.LayerDrawable r13 = new android.graphics.drawable.LayerDrawable
            r32 = r22
            r0 = r32
            r13.<init>(r0)
            r33 = r13
            r24 = 0
        L_0x0073:
            r0 = r24
            r1 = r21
            if (r0 < r1) goto L_0x00ae
            r35 = r33
            return r35
        L_0x007c:
            r0 = r23
            int r11 = r14.getId(r0)
            r24 = r11
            r25 = r23
            r0 = r23
            android.graphics.drawable.Drawable r26 = r14.getDrawable(r0)
            r12 = 16908301(0x102000d, float:2.3877265E-38)
            if (r11 != r12) goto L_0x00a6
        L_0x0091:
            r27 = 1
        L_0x0093:
            r28 = r26
            r29 = r27
            r0 = r28
            r1 = r29
            android.graphics.drawable.Drawable r30 = r3.tileify(r0, r1)
            r31 = r22
            r31[r25] = r30
            int r23 = r23 + 1
            goto L_0x0060
        L_0x00a6:
            r12 = 16908303(0x102000f, float:2.387727E-38)
            if (r11 == r12) goto L_0x0091
            r27 = 0
            goto L_0x0093
        L_0x00ae:
            r25 = r24
            r0 = r24
            int r34 = r14.getId(r0)
            r0 = r33
            r1 = r25
            r2 = r34
            r0.setId(r1, r2)
            int r24 = r24 + 1
            goto L_0x0073
        L_0x00c2:
            r0 = r4
            android.graphics.drawable.BitmapDrawable r0 = (android.graphics.drawable.BitmapDrawable) r0
            r13 = r0
            r14 = r13
            android.graphics.Bitmap r13 = r13.getBitmap()
            r38 = r13
            android.graphics.Bitmap r13 = r3.mSampleTile
            r15 = 0
            if (r13 == r15) goto L_0x0120
        L_0x00d2:
            android.graphics.drawable.ShapeDrawable r13 = new android.graphics.drawable.ShapeDrawable
            android.graphics.drawable.shapes.Shape r30 = r3.getDrawableShape()
            r39 = r30
            r0 = r39
            r13.<init>(r0)
            r22 = r13
            android.graphics.BitmapShader r13 = new android.graphics.BitmapShader
            android.graphics.Shader$TileMode r26 = android.graphics.Shader.TileMode.REPEAT
            android.graphics.Shader$TileMode r40 = android.graphics.Shader.TileMode.CLAMP
            r41 = r38
            r42 = r26
            r43 = r40
            r0 = r41
            r1 = r42
            r2 = r43
            r13.<init>(r0, r1, r2)
            r33 = r13
            android.graphics.Paint r13 = r22.getPaint()
            r44 = r33
            r0 = r44
            android.graphics.Shader r13 = r13.setShader(r0)
            android.graphics.Paint r13 = r22.getPaint()
            android.graphics.Paint r45 = r14.getPaint()
            android.graphics.ColorFilter r45 = r45.getColorFilter()
            r46 = r45
            r0 = r46
            android.graphics.ColorFilter r13 = r13.setColorFilter(r0)
            r12 = 0
            if (r8 != r12) goto L_0x0125
            r13 = r22
        L_0x011d:
            r49 = r13
            return r49
        L_0x0120:
            r0 = r38
            r3.mSampleTile = r0
            goto L_0x00d2
        L_0x0125:
            android.graphics.drawable.ClipDrawable r13 = new android.graphics.drawable.ClipDrawable
            r47 = 3
            r27 = 1
            r48 = r22
            r0 = r48
            r1 = r47
            r2 = r27
            r13.<init>(r0, r1, r2)
            goto L_0x011d
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.AppCompatProgressBarHelper.tileify(android.graphics.drawable.Drawable, boolean):android.graphics.drawable.Drawable");
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r26v0, types: [android.graphics.drawable.Drawable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable tileifyIndeterminate(android.graphics.drawable.Drawable r28) {
        /*
            r27 = this;
            r2 = r27
            r3 = r28
            r4 = r2
            r5 = r3
            boolean r6 = r3 instanceof android.graphics.drawable.AnimationDrawable
            r7 = r6
            r8 = r7
            r9 = 0
            if (r8 != r9) goto L_0x0010
        L_0x000d:
            r26 = r5
            return r26
        L_0x0010:
            r0 = r3
            android.graphics.drawable.AnimationDrawable r0 = (android.graphics.drawable.AnimationDrawable) r0
            r10 = r0
            r11 = r10
            int r8 = r10.getNumberOfFrames()
            r12 = r8
            android.graphics.drawable.AnimationDrawable r10 = new android.graphics.drawable.AnimationDrawable
            r10.<init>()
            r13 = r10
            r10 = r10
            boolean r14 = r11.isOneShot()
            r15 = r14
            r16 = r15
            r0 = r16
            r10.setOneShot(r0)
            r17 = 0
        L_0x002f:
            r0 = r17
            if (r0 < r12) goto L_0x003b
            r15 = 10000(0x2710, float:1.4013E-41)
            boolean r25 = r13.setLevel(r15)
            r5 = r13
            goto L_0x000d
        L_0x003b:
            r0 = r17
            android.graphics.drawable.Drawable r18 = r11.getFrame(r0)
            r19 = r18
            r20 = 1
            r0 = r19
            r1 = r20
            android.graphics.drawable.Drawable r10 = r2.tileify(r0, r1)
            r21 = r10
            r15 = 10000(0x2710, float:1.4013E-41)
            boolean r22 = r10.setLevel(r15)
            r0 = r17
            int r23 = r11.getDuration(r0)
            r24 = r21
            r0 = r24
            r1 = r23
            r13.addFrame(r0, r1)
            int r17 = r17 + 1
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.AppCompatProgressBarHelper.tileifyIndeterminate(android.graphics.drawable.Drawable):android.graphics.drawable.Drawable");
    }

    private Shape getDrawableShape() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    /* access modifiers changed from: 0000 */
    public Bitmap getSampleTime() {
        return this.mSampleTile;
    }
}
