package android.support.p000v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.app.ActivityCompatApi21.SharedElementCallback21;
import android.support.p000v4.app.ActivityCompatApi23.OnSharedElementsReadyListenerBridge;
import android.support.p000v4.app.ActivityCompatApi23.SharedElementCallback23;
import android.support.p000v4.app.SharedElementCallback.OnSharedElementsReadyListener;
import android.support.p000v4.content.ContextCompat;
import android.view.View;
import java.util.List;
import java.util.Map;

/* renamed from: android.support.v4.app.ActivityCompat */
public class ActivityCompat extends ContextCompat {

    /* renamed from: android.support.v4.app.ActivityCompat$OnRequestPermissionsResultCallback */
    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr);
    }

    /* renamed from: android.support.v4.app.ActivityCompat$SharedElementCallback21Impl */
    private static class SharedElementCallback21Impl extends SharedElementCallback21 {
        private SharedElementCallback mCallback;

        public SharedElementCallback21Impl(SharedElementCallback sharedElementCallback) {
            SharedElementCallback callback = sharedElementCallback;
            SharedElementCallback sharedElementCallback2 = callback;
            this.mCallback = callback;
        }

        public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
            List<String> sharedElementNames = list;
            List<View> sharedElements = list2;
            List<View> sharedElementSnapshots = list3;
            List<String> list4 = sharedElementNames;
            List<View> list5 = sharedElements;
            List<View> list6 = sharedElementSnapshots;
            this.mCallback.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
            List<String> sharedElementNames = list;
            List<View> sharedElements = list2;
            List<View> sharedElementSnapshots = list3;
            List<String> list4 = sharedElementNames;
            List<View> list5 = sharedElements;
            List<View> list6 = sharedElementSnapshots;
            this.mCallback.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onRejectSharedElements(List<View> list) {
            List<View> rejectedSharedElements = list;
            List<View> list2 = rejectedSharedElements;
            this.mCallback.onRejectSharedElements(rejectedSharedElements);
        }

        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            List<String> names = list;
            Map<String, View> sharedElements = map;
            List<String> list2 = names;
            Map<String, View> map2 = sharedElements;
            this.mCallback.onMapSharedElements(names, sharedElements);
        }

        public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
            View sharedElement = view;
            Matrix viewToGlobalMatrix = matrix;
            RectF screenBounds = rectF;
            View view2 = sharedElement;
            Matrix matrix2 = viewToGlobalMatrix;
            RectF rectF2 = screenBounds;
            return this.mCallback.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
        }

        public View onCreateSnapshotView(Context context, Parcelable parcelable) {
            Context context2 = context;
            Parcelable snapshot = parcelable;
            Context context3 = context2;
            Parcelable parcelable2 = snapshot;
            return this.mCallback.onCreateSnapshotView(context2, snapshot);
        }
    }

    /* renamed from: android.support.v4.app.ActivityCompat$SharedElementCallback23Impl */
    private static class SharedElementCallback23Impl extends SharedElementCallback23 {
        private SharedElementCallback mCallback;

        public SharedElementCallback23Impl(SharedElementCallback sharedElementCallback) {
            SharedElementCallback callback = sharedElementCallback;
            SharedElementCallback sharedElementCallback2 = callback;
            this.mCallback = callback;
        }

        public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
            List<String> sharedElementNames = list;
            List<View> sharedElements = list2;
            List<View> sharedElementSnapshots = list3;
            List<String> list4 = sharedElementNames;
            List<View> list5 = sharedElements;
            List<View> list6 = sharedElementSnapshots;
            this.mCallback.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
            List<String> sharedElementNames = list;
            List<View> sharedElements = list2;
            List<View> sharedElementSnapshots = list3;
            List<String> list4 = sharedElementNames;
            List<View> list5 = sharedElements;
            List<View> list6 = sharedElementSnapshots;
            this.mCallback.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onRejectSharedElements(List<View> list) {
            List<View> rejectedSharedElements = list;
            List<View> list2 = rejectedSharedElements;
            this.mCallback.onRejectSharedElements(rejectedSharedElements);
        }

        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            List<String> names = list;
            Map<String, View> sharedElements = map;
            List<String> list2 = names;
            Map<String, View> map2 = sharedElements;
            this.mCallback.onMapSharedElements(names, sharedElements);
        }

        public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
            View sharedElement = view;
            Matrix viewToGlobalMatrix = matrix;
            RectF screenBounds = rectF;
            View view2 = sharedElement;
            Matrix matrix2 = viewToGlobalMatrix;
            RectF rectF2 = screenBounds;
            return this.mCallback.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
        }

        public View onCreateSnapshotView(Context context, Parcelable parcelable) {
            Context context2 = context;
            Parcelable snapshot = parcelable;
            Context context3 = context2;
            Parcelable parcelable2 = snapshot;
            return this.mCallback.onCreateSnapshotView(context2, snapshot);
        }

        public void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListenerBridge onSharedElementsReadyListenerBridge) {
            List<String> sharedElementNames = list;
            List<View> sharedElements = list2;
            OnSharedElementsReadyListenerBridge listener = onSharedElementsReadyListenerBridge;
            List<String> list3 = sharedElementNames;
            List<View> list4 = sharedElements;
            OnSharedElementsReadyListenerBridge onSharedElementsReadyListenerBridge2 = listener;
            final OnSharedElementsReadyListenerBridge onSharedElementsReadyListenerBridge3 = listener;
            this.mCallback.onSharedElementsArrived(sharedElementNames, sharedElements, new OnSharedElementsReadyListener(this) {
                final /* synthetic */ SharedElementCallback23Impl this$0;

                {
                    SharedElementCallback23Impl this$02 = r6;
                    OnSharedElementsReadyListenerBridge onSharedElementsReadyListenerBridge = r7;
                    SharedElementCallback23Impl sharedElementCallback23Impl = this$02;
                    this.this$0 = this$02;
                }

                public void onSharedElementsReady() {
                    onSharedElementsReadyListenerBridge3.onSharedElementsReady();
                }
            });
        }
    }

    protected ActivityCompat() {
    }

    public static boolean invalidateOptionsMenu(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        ActivityCompatHoneycomb.invalidateOptionsMenu(activity2);
        return true;
    }

    public static void startActivityForResult(Activity activity, Intent intent, int i, @Nullable Bundle bundle) {
        Activity activity2 = activity;
        Intent intent2 = intent;
        int requestCode = i;
        Bundle options = bundle;
        Activity activity3 = activity2;
        Intent intent3 = intent2;
        int i2 = requestCode;
        Bundle bundle2 = options;
        if (VERSION.SDK_INT < 16) {
            activity2.startActivityForResult(intent2, requestCode);
        } else {
            ActivityCompatJB.startActivityForResult(activity2, intent2, requestCode, options);
        }
    }

    public static void startIntentSenderForResult(Activity activity, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, @Nullable Bundle bundle) throws SendIntentException {
        Activity activity2 = activity;
        IntentSender intent2 = intentSender;
        int requestCode = i;
        Intent fillInIntent = intent;
        int flagsMask = i2;
        int flagsValues = i3;
        int extraFlags = i4;
        Bundle options = bundle;
        Activity activity3 = activity2;
        IntentSender intentSender2 = intent2;
        int i5 = requestCode;
        Intent intent3 = fillInIntent;
        int i6 = flagsMask;
        int i7 = flagsValues;
        int i8 = extraFlags;
        Bundle bundle2 = options;
        if (VERSION.SDK_INT < 16) {
            activity2.startIntentSenderForResult(intent2, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
        } else {
            ActivityCompatJB.startIntentSenderForResult(activity2, intent2, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        }
    }

    public static void finishAffinity(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        if (VERSION.SDK_INT < 16) {
            activity2.finish();
        } else {
            ActivityCompatJB.finishAffinity(activity2);
        }
    }

    public static void finishAfterTransition(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        if (VERSION.SDK_INT < 21) {
            activity2.finish();
        } else {
            ActivityCompatApi21.finishAfterTransition(activity2);
        }
    }

    @Nullable
    public static Uri getReferrer(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        if (VERSION.SDK_INT >= 22) {
            return ActivityCompatApi22.getReferrer(activity2);
        }
        Intent intent = activity2.getIntent();
        Intent intent2 = intent;
        Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.REFERRER");
        Uri referrer = uri;
        if (uri != null) {
            return referrer;
        }
        String stringExtra = intent2.getStringExtra("android.intent.extra.REFERRER_NAME");
        String referrerName = stringExtra;
        if (stringExtra == null) {
            return null;
        }
        return Uri.parse(referrerName);
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback sharedElementCallback) {
        Activity activity2 = activity;
        SharedElementCallback callback = sharedElementCallback;
        Activity activity3 = activity2;
        SharedElementCallback sharedElementCallback2 = callback;
        if (VERSION.SDK_INT >= 23) {
            ActivityCompatApi23.setEnterSharedElementCallback(activity2, createCallback23(callback));
        } else if (VERSION.SDK_INT >= 21) {
            ActivityCompatApi21.setEnterSharedElementCallback(activity2, createCallback(callback));
        }
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback sharedElementCallback) {
        Activity activity2 = activity;
        SharedElementCallback callback = sharedElementCallback;
        Activity activity3 = activity2;
        SharedElementCallback sharedElementCallback2 = callback;
        if (VERSION.SDK_INT >= 23) {
            ActivityCompatApi23.setExitSharedElementCallback(activity2, createCallback23(callback));
        } else if (VERSION.SDK_INT >= 21) {
            ActivityCompatApi21.setExitSharedElementCallback(activity2, createCallback(callback));
        }
    }

    public static void postponeEnterTransition(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        if (VERSION.SDK_INT >= 21) {
            ActivityCompatApi21.postponeEnterTransition(activity2);
        }
    }

    public static void startPostponedEnterTransition(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        if (VERSION.SDK_INT >= 21) {
            ActivityCompatApi21.startPostponedEnterTransition(activity2);
        }
    }

    public static void requestPermissions(@NonNull Activity activity, @NonNull String[] strArr, @IntRange(from = 0) int i) {
        Activity activity2 = activity;
        String[] permissions = strArr;
        final int requestCode = i;
        Activity activity3 = activity2;
        String[] strArr2 = permissions;
        int i2 = requestCode;
        if (VERSION.SDK_INT >= 23) {
            ActivityCompatApi23.requestPermissions(activity2, permissions, requestCode);
        } else if (activity2 instanceof OnRequestPermissionsResultCallback) {
            Handler handler = new Handler(Looper.getMainLooper());
            Handler handler2 = handler;
            Handler handler3 = handler;
            final String[] strArr3 = permissions;
            final Activity activity4 = activity2;
            C00881 r0 = new Runnable() {
                {
                    Activity activity = r7;
                    int i = r8;
                }

                public void run() {
                    int[] grantResults = new int[strArr3.length];
                    PackageManager packageManager = activity4.getPackageManager();
                    String packageName = activity4.getPackageName();
                    int permissionCount = strArr3.length;
                    for (int i = 0; i < permissionCount; i++) {
                        grantResults[i] = packageManager.checkPermission(strArr3[i], packageName);
                    }
                    ((OnRequestPermissionsResultCallback) activity4).onRequestPermissionsResult(requestCode, strArr3, grantResults);
                }
            };
            boolean post = handler3.post(r0);
        }
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String str) {
        Activity activity2 = activity;
        String permission = str;
        Activity activity3 = activity2;
        String str2 = permission;
        if (VERSION.SDK_INT < 23) {
            return false;
        }
        return ActivityCompatApi23.shouldShowRequestPermissionRationale(activity2, permission);
    }

    private static SharedElementCallback21 createCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback callback = sharedElementCallback;
        SharedElementCallback sharedElementCallback2 = callback;
        SharedElementCallback21Impl sharedElementCallback21Impl = null;
        if (callback != null) {
            sharedElementCallback21Impl = new SharedElementCallback21Impl(callback);
        }
        return sharedElementCallback21Impl;
    }

    private static SharedElementCallback23 createCallback23(SharedElementCallback sharedElementCallback) {
        SharedElementCallback callback = sharedElementCallback;
        SharedElementCallback sharedElementCallback2 = callback;
        SharedElementCallback23Impl sharedElementCallback23Impl = null;
        if (callback != null) {
            sharedElementCallback23Impl = new SharedElementCallback23Impl(callback);
        }
        return sharedElementCallback23Impl;
    }
}
