package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
class FragmentCompat23 {
    FragmentCompat23() {
    }

    public static void requestPermissions(Fragment fragment, String[] strArr, int i) {
        Fragment fragment2 = fragment;
        String[] permissions = strArr;
        int requestCode = i;
        Fragment fragment3 = fragment2;
        String[] strArr2 = permissions;
        int i2 = requestCode;
        fragment2.requestPermissions(permissions, requestCode);
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) {
        Fragment fragment2 = fragment;
        String permission = str;
        Fragment fragment3 = fragment2;
        String str2 = permission;
        return fragment2.shouldShowRequestPermissionRationale(permission);
    }
}
