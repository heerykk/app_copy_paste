package android.support.design.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;

class ViewGroupUtils {
    private static final ViewGroupUtilsImpl IMPL;

    private interface ViewGroupUtilsImpl {
        void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect);
    }

    private static class ViewGroupUtilsImplBase implements ViewGroupUtilsImpl {
        ViewGroupUtilsImplBase() {
        }

        public void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
            ViewGroup parent = viewGroup;
            View child = view;
            Rect rect2 = rect;
            ViewGroup viewGroup2 = parent;
            View view2 = child;
            Rect rect3 = rect2;
            parent.offsetDescendantRectToMyCoords(child, rect2);
            rect2.offset(child.getScrollX(), child.getScrollY());
        }
    }

    private static class ViewGroupUtilsImplHoneycomb implements ViewGroupUtilsImpl {
        ViewGroupUtilsImplHoneycomb() {
        }

        public void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
            ViewGroup parent = viewGroup;
            View child = view;
            Rect rect2 = rect;
            ViewGroup viewGroup2 = parent;
            View view2 = child;
            Rect rect3 = rect2;
            ViewGroupUtilsHoneycomb.offsetDescendantRect(parent, child, rect2);
        }
    }

    ViewGroupUtils() {
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 11) {
            IMPL = new ViewGroupUtilsImplBase();
        } else {
            IMPL = new ViewGroupUtilsImplHoneycomb();
        }
    }

    static void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        ViewGroup parent = viewGroup;
        View descendant = view;
        Rect rect2 = rect;
        ViewGroup viewGroup2 = parent;
        View view2 = descendant;
        Rect rect3 = rect2;
        IMPL.offsetDescendantRect(parent, descendant, rect2);
    }

    static void getDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        ViewGroup parent = viewGroup;
        View descendant = view;
        Rect out = rect;
        ViewGroup viewGroup2 = parent;
        View view2 = descendant;
        Rect rect2 = out;
        out.set(0, 0, descendant.getWidth(), descendant.getHeight());
        offsetDescendantRect(parent, descendant, out);
    }
}
