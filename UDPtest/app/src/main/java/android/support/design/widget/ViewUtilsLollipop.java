package android.support.design.widget;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RequiresApi;
import android.support.design.C0001R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

@TargetApi(21)
@RequiresApi(21)
class ViewUtilsLollipop {
    private static final int[] STATE_LIST_ANIM_ATTRS;

    ViewUtilsLollipop() {
    }

    static {
        int[] iArr = new int[1];
        iArr[0] = 16843848;
        STATE_LIST_ANIM_ATTRS = iArr;
    }

    static void setBoundsViewOutlineProvider(View view) {
        View view2 = view;
        View view3 = view2;
        view2.setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

    static void setStateListAnimatorFromAttrs(View view, AttributeSet attributeSet, int i, int i2) {
        View view2 = view;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        int defStyleRes = i2;
        View view3 = view2;
        AttributeSet attributeSet2 = attrs;
        int i3 = defStyleAttr;
        int i4 = defStyleRes;
        Context context = view2.getContext();
        Context context2 = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, STATE_LIST_ANIM_ATTRS, defStyleAttr, defStyleRes);
        TypedArray a = obtainStyledAttributes;
        try {
            if (obtainStyledAttributes.hasValue(0)) {
                view2.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context2, a.getResourceId(0, 0)));
            }
        } finally {
            a.recycle();
        }
    }

    static void setDefaultAppBarLayoutStateListAnimator(View view, float f) {
        View view2 = view;
        float elevation = f;
        View view3 = view2;
        float f2 = elevation;
        int integer = view2.getResources().getInteger(C0001R.integer.app_bar_elevation_anim_duration);
        int i = integer;
        StateListAnimator stateListAnimator = new StateListAnimator();
        StateListAnimator sla = stateListAnimator;
        StateListAnimator stateListAnimator2 = stateListAnimator;
        int[] iArr = new int[3];
        iArr[0] = 16842766;
        iArr[1] = C0001R.attr.state_collapsible;
        iArr[2] = -C0001R.attr.state_collapsed;
        stateListAnimator2.addState(iArr, ObjectAnimator.ofFloat(view2, "elevation", new float[]{0.0f}).setDuration((long) integer));
        sla.addState(new int[]{16842766}, ObjectAnimator.ofFloat(view2, "elevation", new float[]{elevation}).setDuration((long) integer));
        sla.addState(new int[0], ObjectAnimator.ofFloat(view2, "elevation", new float[]{0.0f}).setDuration(0));
        view2.setStateListAnimator(sla);
    }
}
