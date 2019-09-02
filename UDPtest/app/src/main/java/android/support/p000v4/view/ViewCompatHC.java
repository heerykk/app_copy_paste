package android.support.p000v4.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewParent;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.view.ViewCompatHC */
class ViewCompatHC {
    ViewCompatHC() {
    }

    static long getFrameTime() {
        return ValueAnimator.getFrameDelay();
    }

    public static float getAlpha(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getAlpha();
    }

    public static void setLayerType(View view, int i, Paint paint) {
        View view2 = view;
        int layerType = i;
        Paint paint2 = paint;
        View view3 = view2;
        int i2 = layerType;
        Paint paint3 = paint2;
        view2.setLayerType(layerType, paint2);
    }

    public static int getLayerType(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getLayerType();
    }

    public static int resolveSizeAndState(int i, int i2, int i3) {
        int size = i;
        int measureSpec = i2;
        int childMeasuredState = i3;
        int i4 = size;
        int i5 = measureSpec;
        int i6 = childMeasuredState;
        return View.resolveSizeAndState(size, measureSpec, childMeasuredState);
    }

    public static int getMeasuredWidthAndState(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getMeasuredWidthAndState();
    }

    public static int getMeasuredHeightAndState(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getMeasuredHeightAndState();
    }

    public static int getMeasuredState(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getMeasuredState();
    }

    public static float getTranslationX(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getTranslationX();
    }

    public static float getTranslationY(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getTranslationY();
    }

    public static float getX(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getX();
    }

    public static float getY(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getY();
    }

    public static float getRotation(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getRotation();
    }

    public static float getRotationX(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getRotationX();
    }

    public static float getRotationY(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getRotationY();
    }

    public static float getScaleX(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getScaleX();
    }

    public static float getScaleY(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getScaleY();
    }

    public static void setTranslationX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setTranslationX(value);
    }

    public static void setTranslationY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setTranslationY(value);
    }

    public static Matrix getMatrix(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getMatrix();
    }

    public static void setAlpha(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setAlpha(value);
    }

    public static void setX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setX(value);
    }

    public static void setY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setY(value);
    }

    public static void setRotation(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setRotation(value);
    }

    public static void setRotationX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setRotationX(value);
    }

    public static void setRotationY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setRotationY(value);
    }

    public static void setScaleX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setScaleX(value);
    }

    public static void setScaleY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setScaleY(value);
    }

    public static void setPivotX(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setPivotX(value);
    }

    public static void setPivotY(View view, float f) {
        View view2 = view;
        float value = f;
        View view3 = view2;
        float f2 = value;
        view2.setPivotY(value);
    }

    public static float getPivotX(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getPivotX();
    }

    public static float getPivotY(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getPivotY();
    }

    public static void jumpDrawablesToCurrentState(View view) {
        View view2 = view;
        View view3 = view2;
        view2.jumpDrawablesToCurrentState();
    }

    public static void setSaveFromParentEnabled(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        view2.setSaveFromParentEnabled(z);
    }

    public static void setActivated(View view, boolean z) {
        View view2 = view;
        View view3 = view2;
        view2.setActivated(z);
    }

    public static int combineMeasuredStates(int i, int i2) {
        int curState = i;
        int newState = i2;
        int i3 = curState;
        int i4 = newState;
        return View.combineMeasuredStates(curState, newState);
    }

    static void offsetTopAndBottom(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        view2.offsetTopAndBottom(offset);
        if (view2.getVisibility() == 0) {
            tickleInvalidationFlag(view2);
            ViewParent parent = view2.getParent();
            ViewParent parent2 = parent;
            if (parent instanceof View) {
                tickleInvalidationFlag((View) parent2);
            }
        }
    }

    static void offsetLeftAndRight(View view, int i) {
        View view2 = view;
        int offset = i;
        View view3 = view2;
        int i2 = offset;
        view2.offsetLeftAndRight(offset);
        if (view2.getVisibility() == 0) {
            tickleInvalidationFlag(view2);
            ViewParent parent = view2.getParent();
            ViewParent parent2 = parent;
            if (parent instanceof View) {
                tickleInvalidationFlag((View) parent2);
            }
        }
    }

    private static void tickleInvalidationFlag(View view) {
        View view2 = view;
        View view3 = view2;
        float translationY = view2.getTranslationY();
        float f = translationY;
        view2.setTranslationY(translationY + 1.0f);
        view2.setTranslationY(translationY);
    }
}
