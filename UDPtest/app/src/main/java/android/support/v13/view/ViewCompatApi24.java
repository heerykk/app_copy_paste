package android.support.v13.view;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.DragShadowBuilder;

@TargetApi(24)
@RequiresApi(24)
class ViewCompatApi24 {
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
        return v.startDragAndDrop(data, shadowBuilder, localState, flags);
    }

    public static void cancelDragAndDrop(View view) {
        View v = view;
        View view2 = v;
        v.cancelDragAndDrop();
    }

    public static void updateDragShadow(View view, DragShadowBuilder dragShadowBuilder) {
        View v = view;
        DragShadowBuilder shadowBuilder = dragShadowBuilder;
        View view2 = v;
        DragShadowBuilder dragShadowBuilder2 = shadowBuilder;
        v.updateDragShadow(shadowBuilder);
    }

    private ViewCompatApi24() {
    }
}
