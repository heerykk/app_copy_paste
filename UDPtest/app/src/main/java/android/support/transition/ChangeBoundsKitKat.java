package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;

@TargetApi(19)
@RequiresApi(19)
class ChangeBoundsKitKat extends TransitionKitKat implements ChangeBoundsInterface {
    public ChangeBoundsKitKat(TransitionInterface transitionInterface) {
        TransitionInterface transition = transitionInterface;
        TransitionInterface transitionInterface2 = transition;
        init(transition, new ChangeBounds());
    }

    public void setResizeClip(boolean z) {
        ((ChangeBounds) this.mTransition).setResizeClip(z);
    }
}
