package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.support.annotation.RequiresApi;

@TargetApi(15)
@RequiresApi(15)
class FragmentCompatICSMR1 {
    FragmentCompatICSMR1() {
    }

    public static void setUserVisibleHint(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        boolean isVisible = z;
        if (f.getFragmentManager() != null) {
            f.setUserVisibleHint(isVisible);
        }
    }
}
