package android.support.p003v7.widget;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v7.widget.ActionBarBackgroundDrawableV21 */
class ActionBarBackgroundDrawableV21 extends ActionBarBackgroundDrawable {
    public ActionBarBackgroundDrawableV21(ActionBarContainer actionBarContainer) {
        ActionBarContainer container = actionBarContainer;
        ActionBarContainer actionBarContainer2 = container;
        super(container);
    }

    public void getOutline(@NonNull Outline outline) {
        Outline outline2 = outline;
        Outline outline3 = outline2;
        if (!this.mContainer.mIsSplit) {
            if (this.mContainer.mBackground != null) {
                this.mContainer.mBackground.getOutline(outline2);
            }
        } else if (this.mContainer.mSplitBackground != null) {
            this.mContainer.mSplitBackground.getOutline(outline2);
        }
    }
}
