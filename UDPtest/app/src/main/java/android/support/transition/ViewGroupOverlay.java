package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class ViewGroupOverlay extends ViewOverlay {
    ViewGroupOverlay(Context context, ViewGroup viewGroup, View view) {
        Context context2 = context;
        ViewGroup hostView = viewGroup;
        View requestingView = view;
        Context context3 = context2;
        ViewGroup viewGroup2 = hostView;
        View view2 = requestingView;
        super(context2, hostView, requestingView);
    }

    public static ViewGroupOverlay createFrom(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = viewGroup;
        ViewGroup viewGroup3 = viewGroup2;
        return (ViewGroupOverlay) ViewOverlay.createFrom(viewGroup2);
    }

    public void add(View view) {
        View view2 = view;
        View view3 = view2;
        this.mOverlayViewGroup.add(view2);
    }

    public void remove(View view) {
        View view2 = view;
        View view3 = view2;
        this.mOverlayViewGroup.remove(view2);
    }
}
