package android.support.p003v7.widget.helper;

import android.graphics.Canvas;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.recyclerview.C0271R;
import android.support.p003v7.widget.RecyclerView;
import android.view.View;

/* renamed from: android.support.v7.widget.helper.ItemTouchUIUtilImpl */
class ItemTouchUIUtilImpl {

    /* renamed from: android.support.v7.widget.helper.ItemTouchUIUtilImpl$Gingerbread */
    static class Gingerbread implements ItemTouchUIUtil {
        Gingerbread() {
        }

        private void draw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2) {
            Canvas c = canvas;
            RecyclerView parent = recyclerView;
            View view2 = view;
            float dX = f;
            float dY = f2;
            Canvas canvas2 = c;
            RecyclerView recyclerView2 = parent;
            View view3 = view2;
            float f3 = dX;
            float f4 = dY;
            int save = c.save();
            c.translate(dX, dY);
            boolean drawChild = parent.drawChild(c, view2, 0);
            c.restore();
        }

        public void clearView(View view) {
            View view2 = view;
            View view3 = view2;
            view2.setVisibility(0);
        }

        public void onSelected(View view) {
            View view2 = view;
            View view3 = view2;
            view2.setVisibility(4);
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            Canvas c = canvas;
            RecyclerView recyclerView2 = recyclerView;
            View view2 = view;
            float dX = f;
            float dY = f2;
            int actionState = i;
            Canvas canvas2 = c;
            RecyclerView recyclerView3 = recyclerView2;
            View view3 = view2;
            float f3 = dX;
            float f4 = dY;
            int i2 = actionState;
            boolean z2 = z;
            if (actionState != 2) {
                draw(c, recyclerView2, view2, dX, dY);
            }
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            Canvas c = canvas;
            RecyclerView recyclerView2 = recyclerView;
            View view2 = view;
            float dX = f;
            float dY = f2;
            int actionState = i;
            Canvas canvas2 = c;
            RecyclerView recyclerView3 = recyclerView2;
            View view3 = view2;
            float f3 = dX;
            float f4 = dY;
            int i2 = actionState;
            boolean z2 = z;
            if (actionState == 2) {
                draw(c, recyclerView2, view2, dX, dY);
            }
        }
    }

    /* renamed from: android.support.v7.widget.helper.ItemTouchUIUtilImpl$Honeycomb */
    static class Honeycomb implements ItemTouchUIUtil {
        Honeycomb() {
        }

        public void clearView(View view) {
            View view2 = view;
            View view3 = view2;
            ViewCompat.setTranslationX(view2, 0.0f);
            ViewCompat.setTranslationY(view2, 0.0f);
        }

        public void onSelected(View view) {
            View view2 = view;
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            View view2 = view;
            float dX = f;
            float dY = f2;
            Canvas canvas2 = canvas;
            RecyclerView recyclerView2 = recyclerView;
            View view3 = view2;
            float f3 = dX;
            float f4 = dY;
            int i2 = i;
            boolean z2 = z;
            ViewCompat.setTranslationX(view2, dX);
            ViewCompat.setTranslationY(view2, dY);
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            Canvas canvas2 = canvas;
            RecyclerView recyclerView2 = recyclerView;
            View view2 = view;
            float f3 = f;
            float f4 = f2;
            int i2 = i;
            boolean z2 = z;
        }
    }

    /* renamed from: android.support.v7.widget.helper.ItemTouchUIUtilImpl$Lollipop */
    static class Lollipop extends Honeycomb {
        Lollipop() {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            Canvas c = canvas;
            RecyclerView recyclerView2 = recyclerView;
            View view2 = view;
            float dX = f;
            float dY = f2;
            int actionState = i;
            Canvas canvas2 = c;
            RecyclerView recyclerView3 = recyclerView2;
            View view3 = view2;
            float f3 = dX;
            float f4 = dY;
            int i2 = actionState;
            boolean isCurrentlyActive = z;
            if (isCurrentlyActive) {
                Object tag = view2.getTag(C0271R.C0272id.item_touch_helper_previous_elevation);
                Object obj = tag;
                if (tag == null) {
                    Float valueOf = Float.valueOf(ViewCompat.getElevation(view2));
                    float findMaxElevation = 1.0f + findMaxElevation(recyclerView2, view2);
                    float f5 = findMaxElevation;
                    ViewCompat.setElevation(view2, findMaxElevation);
                    view2.setTag(C0271R.C0272id.item_touch_helper_previous_elevation, valueOf);
                }
            }
            super.onDraw(c, recyclerView2, view2, dX, dY, actionState, isCurrentlyActive);
        }

        private float findMaxElevation(RecyclerView recyclerView, View view) {
            RecyclerView recyclerView2 = recyclerView;
            View itemView = view;
            RecyclerView recyclerView3 = recyclerView2;
            View view2 = itemView;
            int childCount = recyclerView2.getChildCount();
            float max = 0.0f;
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView2.getChildAt(i);
                View child = childAt;
                if (childAt != itemView) {
                    float elevation = ViewCompat.getElevation(child);
                    float elevation2 = elevation;
                    if (elevation > max) {
                        max = elevation2;
                    }
                }
            }
            return max;
        }

        public void clearView(View view) {
            View view2 = view;
            View view3 = view2;
            Object tag = view2.getTag(C0271R.C0272id.item_touch_helper_previous_elevation);
            Object tag2 = tag;
            if (tag != null && (tag2 instanceof Float)) {
                ViewCompat.setElevation(view2, ((Float) tag2).floatValue());
            }
            view2.setTag(C0271R.C0272id.item_touch_helper_previous_elevation, null);
            super.clearView(view2);
        }
    }

    ItemTouchUIUtilImpl() {
    }
}
