package android.support.transition;

import android.annotation.TargetApi;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

@TargetApi(14)
@RequiresApi(14)
class WindowIdPort {
    private final IBinder mToken;

    private WindowIdPort(IBinder iBinder) {
        IBinder token = iBinder;
        IBinder iBinder2 = token;
        this.mToken = token;
    }

    static WindowIdPort getWindowId(@NonNull View view) {
        View view2 = view;
        View view3 = view2;
        return new WindowIdPort(view2.getWindowToken());
    }

    public boolean equals(Object obj) {
        Object obj2 = obj;
        Object obj3 = obj2;
        return (obj2 instanceof WindowIdPort) && ((WindowIdPort) obj2).mToken.equals(this.mToken);
    }
}
