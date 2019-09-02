package android.support.v13.view;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.support.annotation.RequiresApi;
import android.support.p000v4.p002os.BuildCompat;
import android.view.View;
import android.view.View.DragShadowBuilder;

@TargetApi(13)
@RequiresApi(13)
public class ViewCompat extends android.support.p000v4.view.ViewCompat {
    static ViewCompatImpl IMPL;

    private static class Api24ViewCompatImpl implements ViewCompatImpl {
        Api24ViewCompatImpl() {
        }

        public boolean startDragAndDrop(View view, ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
            View v = view;
            ClipData data = clipData;
            DragShadowBuilder shadowBuilder = dragShadowBuilder;
            Object localState = obj;
            int flags = i;
            View view2 = v;
            ClipData clipData2 = data;
            DragShadowBuilder dragShadowBuilder2 = shadowBuilder;
            Object obj2 = localState;
            int i2 = flags;
            return ViewCompatApi24.startDragAndDrop(v, data, shadowBuilder, localState, flags);
        }

        public void cancelDragAndDrop(View view) {
            View v = view;
            View view2 = v;
            ViewCompatApi24.cancelDragAndDrop(v);
        }

        public void updateDragShadow(View view, DragShadowBuilder dragShadowBuilder) {
            View v = view;
            DragShadowBuilder shadowBuilder = dragShadowBuilder;
            View view2 = v;
            DragShadowBuilder dragShadowBuilder2 = shadowBuilder;
            ViewCompatApi24.updateDragShadow(v, shadowBuilder);
        }
    }

    private static class BaseViewCompatImpl implements ViewCompatImpl {
        BaseViewCompatImpl() {
        }

        public boolean startDragAndDrop(View view, ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
            View v = view;
            ClipData data = clipData;
            DragShadowBuilder shadowBuilder = dragShadowBuilder;
            Object localState = obj;
            int flags = i;
            View view2 = v;
            ClipData clipData2 = data;
            DragShadowBuilder dragShadowBuilder2 = shadowBuilder;
            Object obj2 = localState;
            int i2 = flags;
            return v.startDrag(data, shadowBuilder, localState, flags);
        }

        public void cancelDragAndDrop(View view) {
            View view2 = view;
        }

        public void updateDragShadow(View view, DragShadowBuilder dragShadowBuilder) {
            View view2 = view;
            DragShadowBuilder dragShadowBuilder2 = dragShadowBuilder;
        }
    }

    interface ViewCompatImpl {
        void cancelDragAndDrop(View view);

        boolean startDragAndDrop(View view, ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i);

        void updateDragShadow(View view, DragShadowBuilder dragShadowBuilder);
    }

    static {
        if (!BuildCompat.isAtLeastN()) {
            IMPL = new BaseViewCompatImpl();
        } else {
            IMPL = new Api24ViewCompatImpl();
        }
    }

    public static boolean startDragAndDrop(View view, ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        View v = view;
        ClipData data = clipData;
        DragShadowBuilder shadowBuilder = dragShadowBuilder;
        Object localState = obj;
        int flags = i;
        View view2 = v;
        ClipData clipData2 = data;
        DragShadowBuilder dragShadowBuilder2 = shadowBuilder;
        Object obj2 = localState;
        int i2 = flags;
        return IMPL.startDragAndDrop(v, data, shadowBuilder, localState, flags);
    }

    public static void cancelDragAndDrop(View view) {
        View v = view;
        View view2 = v;
        IMPL.cancelDragAndDrop(v);
    }

    public static void updateDragShadow(View view, DragShadowBuilder dragShadowBuilder) {
        View v = view;
        DragShadowBuilder shadowBuilder = dragShadowBuilder;
        View view2 = v;
        DragShadowBuilder dragShadowBuilder2 = shadowBuilder;
        IMPL.updateDragShadow(v, shadowBuilder);
    }

    private ViewCompat() {
    }
}
