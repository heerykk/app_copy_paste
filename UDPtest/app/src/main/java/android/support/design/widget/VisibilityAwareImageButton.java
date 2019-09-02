package android.support.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

class VisibilityAwareImageButton extends ImageButton {
    private int mUserSetVisibility;

    public VisibilityAwareImageButton(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 0);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
        this.mUserSetVisibility = getVisibility();
    }

    public void setVisibility(int i) {
        int visibility = i;
        int i2 = visibility;
        internalSetVisibility(visibility, true);
    }

    /* access modifiers changed from: 0000 */
    public final void internalSetVisibility(int i, boolean z) {
        int visibility = i;
        int i2 = visibility;
        boolean fromUser = z;
        super.setVisibility(visibility);
        if (fromUser) {
            this.mUserSetVisibility = visibility;
        }
    }

    /* access modifiers changed from: 0000 */
    public final int getUserSetVisibility() {
        return this.mUserSetVisibility;
    }
}
