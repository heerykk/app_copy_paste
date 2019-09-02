package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Transition;

@TargetApi(23)
@RequiresApi(23)
class TransitionApi23 extends TransitionKitKat {
    TransitionApi23() {
    }

    public TransitionImpl removeTarget(int i) {
        int targetId = i;
        int i2 = targetId;
        Transition removeTarget = this.mTransition.removeTarget(targetId);
        return this;
    }
}
