package android.support.design.widget;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

@TargetApi(11)
@RequiresApi(11)
class ViewGroupUtilsHoneycomb {
    private static final ThreadLocal<Matrix> sMatrix = new ThreadLocal<>();
    private static final ThreadLocal<RectF> sRectF = new ThreadLocal<>();

    ViewGroupUtilsHoneycomb() {
    }

    public static void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        ViewGroup group = viewGroup;
        View child = view;
        Rect rect2 = rect;
        ViewGroup viewGroup2 = group;
        View view2 = child;
        Rect rect3 = rect2;
        Matrix matrix = (Matrix) sMatrix.get();
        Matrix m = matrix;
        if (matrix != null) {
            m.reset();
        } else {
            m = new Matrix();
            sMatrix.set(m);
        }
        offsetDescendantMatrix(group, child, m);
        RectF rectF = (RectF) sRectF.get();
        RectF rectF2 = rectF;
        if (rectF == null) {
            rectF2 = new RectF();
            sRectF.set(rectF2);
        }
        rectF2.set(rect2);
        boolean mapRect = m.mapRect(rectF2);
        rect2.set((int) (rectF2.left + 0.5f), (int) (rectF2.top + 0.5f), (int) (rectF2.right + 0.5f), (int) (rectF2.bottom + 0.5f));
    }

    static void offsetDescendantMatrix(ViewParent viewParent, View view, Matrix matrix) {
        ViewParent target = viewParent;
        View view2 = view;
        Matrix m = matrix;
        ViewParent viewParent2 = target;
        View view3 = view2;
        Matrix matrix2 = m;
        ViewParent parent = view2.getParent();
        ViewParent parent2 = parent;
        if ((parent instanceof View) && parent2 != target) {
            View vp = (View) parent2;
            offsetDescendantMatrix(target, vp, m);
            boolean preTranslate = m.preTranslate((float) (-vp.getScrollX()), (float) (-vp.getScrollY()));
        }
        boolean preTranslate2 = m.preTranslate((float) view2.getLeft(), (float) view2.getTop());
        if (!view2.getMatrix().isIdentity()) {
            boolean preConcat = m.preConcat(view2.getMatrix());
        }
    }
}
