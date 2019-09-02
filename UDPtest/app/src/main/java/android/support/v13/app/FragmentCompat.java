package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.p000v4.p002os.BuildCompat;
import java.util.Arrays;

@TargetApi(13)
@RequiresApi(13)
public class FragmentCompat {
    static final FragmentCompatImpl IMPL;

    static class BaseFragmentCompatImpl implements FragmentCompatImpl {
        BaseFragmentCompatImpl() {
        }

        public void setMenuVisibility(Fragment fragment, boolean z) {
            Fragment fragment2 = fragment;
            boolean z2 = z;
        }

        public void setUserVisibleHint(Fragment fragment, boolean z) {
            Fragment fragment2 = fragment;
            boolean z2 = z;
        }

        public void requestPermissions(Fragment fragment, String[] strArr, int i) {
            Fragment fragment2 = fragment;
            String[] permissions = strArr;
            final int requestCode = i;
            Fragment fragment3 = fragment2;
            String[] strArr2 = permissions;
            int i2 = requestCode;
            Handler handler = new Handler(Looper.getMainLooper());
            Handler handler2 = handler;
            final String[] strArr3 = permissions;
            final Fragment fragment4 = fragment2;
            boolean post = handler.post(new Runnable(this) {
                final /* synthetic */ BaseFragmentCompatImpl this$0;

                {
                    BaseFragmentCompatImpl this$02 = r8;
                    String[] strArr = r9;
                    Fragment fragment = r10;
                    int i = r11;
                    BaseFragmentCompatImpl baseFragmentCompatImpl = this$02;
                    this.this$0 = this$02;
                }

                public void run() {
                    int[] grantResults = new int[strArr3.length];
                    Activity activity = fragment4.getActivity();
                    Activity activity2 = activity;
                    if (activity == null) {
                        Arrays.fill(grantResults, -1);
                    } else {
                        PackageManager packageManager = activity2.getPackageManager();
                        String packageName = activity2.getPackageName();
                        int permissionCount = strArr3.length;
                        for (int i = 0; i < permissionCount; i++) {
                            grantResults[i] = packageManager.checkPermission(strArr3[i], packageName);
                        }
                    }
                    ((OnRequestPermissionsResultCallback) fragment4).onRequestPermissionsResult(requestCode, strArr3, grantResults);
                }
            });
        }

        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) {
            Fragment fragment2 = fragment;
            String str2 = str;
            return false;
        }
    }

    interface FragmentCompatImpl {
        void requestPermissions(Fragment fragment, String[] strArr, int i);

        void setMenuVisibility(Fragment fragment, boolean z);

        void setUserVisibleHint(Fragment fragment, boolean z);

        boolean shouldShowRequestPermissionRationale(Fragment fragment, String str);
    }

    static class ICSFragmentCompatImpl extends BaseFragmentCompatImpl {
        ICSFragmentCompatImpl() {
        }

        public void setMenuVisibility(Fragment fragment, boolean z) {
            Fragment f = fragment;
            Fragment fragment2 = f;
            FragmentCompatICS.setMenuVisibility(f, z);
        }
    }

    static class ICSMR1FragmentCompatImpl extends ICSFragmentCompatImpl {
        ICSMR1FragmentCompatImpl() {
        }

        public void setUserVisibleHint(Fragment fragment, boolean z) {
            Fragment f = fragment;
            Fragment fragment2 = f;
            FragmentCompatICSMR1.setUserVisibleHint(f, z);
        }
    }

    static class MncFragmentCompatImpl extends ICSMR1FragmentCompatImpl {
        MncFragmentCompatImpl() {
        }

        public void requestPermissions(Fragment fragment, String[] strArr, int i) {
            Fragment fragment2 = fragment;
            String[] permissions = strArr;
            int requestCode = i;
            Fragment fragment3 = fragment2;
            String[] strArr2 = permissions;
            int i2 = requestCode;
            FragmentCompat23.requestPermissions(fragment2, permissions, requestCode);
        }

        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) {
            Fragment fragment2 = fragment;
            String permission = str;
            Fragment fragment3 = fragment2;
            String str2 = permission;
            return FragmentCompat23.shouldShowRequestPermissionRationale(fragment2, permission);
        }
    }

    static class NFragmentCompatImpl extends MncFragmentCompatImpl {
        NFragmentCompatImpl() {
        }

        public void setUserVisibleHint(Fragment fragment, boolean z) {
            Fragment f = fragment;
            Fragment fragment2 = f;
            FragmentCompatApi24.setUserVisibleHint(f, z);
        }
    }

    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr);
    }

    public FragmentCompat() {
    }

    static {
        if (BuildCompat.isAtLeastN()) {
            IMPL = new NFragmentCompatImpl();
        } else if (VERSION.SDK_INT >= 23) {
            IMPL = new MncFragmentCompatImpl();
        } else if (VERSION.SDK_INT >= 15) {
            IMPL = new ICSMR1FragmentCompatImpl();
        } else if (VERSION.SDK_INT < 14) {
            IMPL = new BaseFragmentCompatImpl();
        } else {
            IMPL = new ICSFragmentCompatImpl();
        }
    }

    public static void setMenuVisibility(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        IMPL.setMenuVisibility(f, z);
    }

    public static void setUserVisibleHint(Fragment fragment, boolean z) {
        Fragment f = fragment;
        Fragment fragment2 = f;
        IMPL.setUserVisibleHint(f, z);
    }

    public static void requestPermissions(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
        Fragment fragment2 = fragment;
        String[] permissions = strArr;
        int requestCode = i;
        Fragment fragment3 = fragment2;
        String[] strArr2 = permissions;
        int i2 = requestCode;
        IMPL.requestPermissions(fragment2, permissions, requestCode);
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Fragment fragment, @NonNull String str) {
        Fragment fragment2 = fragment;
        String permission = str;
        Fragment fragment3 = fragment2;
        String str2 = permission;
        return IMPL.shouldShowRequestPermissionRationale(fragment2, permission);
    }
}
