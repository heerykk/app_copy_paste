package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SharedElementCallback;
import android.app.SharedElementCallback.OnSharedElementsReadyListener;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.ActivityCompatApi21.SharedElementCallback21;
import android.view.View;
import java.util.List;
import java.util.Map;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.app.ActivityCompatApi23 */
class ActivityCompatApi23 {

    /* renamed from: android.support.v4.app.ActivityCompatApi23$OnSharedElementsReadyListenerBridge */
    public interface OnSharedElementsReadyListenerBridge {
        void onSharedElementsReady();
    }

    /* renamed from: android.support.v4.app.ActivityCompatApi23$RequestPermissionsRequestCodeValidator */
    public interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i);
    }

    /* renamed from: android.support.v4.app.ActivityCompatApi23$SharedElementCallback23 */
    public static abstract class SharedElementCallback23 extends SharedElementCallback21 {
        public abstract void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListenerBridge onSharedElementsReadyListenerBridge);

        public SharedElementCallback23() {
        }
    }

    /* renamed from: android.support.v4.app.ActivityCompatApi23$SharedElementCallbackImpl */
    private static class SharedElementCallbackImpl extends SharedElementCallback {
        private SharedElementCallback23 mCallback;

        public SharedElementCallbackImpl(SharedElementCallback23 sharedElementCallback23) {
            SharedElementCallback23 callback = sharedElementCallback23;
            SharedElementCallback23 sharedElementCallback232 = callback;
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

        public void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListener onSharedElementsReadyListener) {
            List<String> sharedElementNames = list;
            List<View> sharedElements = list2;
            OnSharedElementsReadyListener listener = onSharedElementsReadyListener;
            List<String> list3 = sharedElementNames;
            List<View> list4 = sharedElements;
            OnSharedElementsReadyListener onSharedElementsReadyListener2 = listener;
            final OnSharedElementsReadyListener onSharedElementsReadyListener3 = listener;
            this.mCallback.onSharedElementsArrived(sharedElementNames, sharedElements, new OnSharedElementsReadyListenerBridge(this) {
                final /* synthetic */ SharedElementCallbackImpl this$0;

                {
                    SharedElementCallbackImpl this$02 = r6;
                    OnSharedElementsReadyListener onSharedElementsReadyListener = r7;
                    SharedElementCallbackImpl sharedElementCallbackImpl = this$02;
                    this.this$0 = this$02;
                }

                public void onSharedElementsReady() {
                    onSharedElementsReadyListener3.onSharedElementsReady();
                }
            });
        }
    }

    ActivityCompatApi23() {
    }

    public static void requestPermissions(Activity activity, String[] strArr, int i) {
        Activity activity2 = activity;
        String[] permissions = strArr;
        int requestCode = i;
        Activity activity3 = activity2;
        String[] strArr2 = permissions;
        int i2 = requestCode;
        if (activity2 instanceof RequestPermissionsRequestCodeValidator) {
            ((RequestPermissionsRequestCodeValidator) activity2).validateRequestPermissionsRequestCode(requestCode);
        }
        activity2.requestPermissions(permissions, requestCode);
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String str) {
        Activity activity2 = activity;
        String permission = str;
        Activity activity3 = activity2;
        String str2 = permission;
        return activity2.shouldShowRequestPermissionRationale(permission);
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback23 sharedElementCallback23) {
        Activity activity2 = activity;
        SharedElementCallback23 callback = sharedElementCallback23;
        Activity activity3 = activity2;
        SharedElementCallback23 sharedElementCallback232 = callback;
        activity2.setEnterSharedElementCallback(createCallback(callback));
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback23 sharedElementCallback23) {
        Activity activity2 = activity;
        SharedElementCallback23 callback = sharedElementCallback23;
        Activity activity3 = activity2;
        SharedElementCallback23 sharedElementCallback232 = callback;
        activity2.setExitSharedElementCallback(createCallback(callback));
    }

    private static SharedElementCallback createCallback(SharedElementCallback23 sharedElementCallback23) {
        SharedElementCallback23 callback = sharedElementCallback23;
        SharedElementCallback23 sharedElementCallback232 = callback;
        SharedElementCallbackImpl sharedElementCallbackImpl = null;
        if (callback != null) {
            sharedElementCallbackImpl = new SharedElementCallbackImpl(callback);
        }
        return sharedElementCallbackImpl;
    }
}
