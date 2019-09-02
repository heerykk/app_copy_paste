package android.support.p000v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.view.View;
import java.util.List;
import java.util.Map;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.app.ActivityCompatApi21 */
class ActivityCompatApi21 {

    /* renamed from: android.support.v4.app.ActivityCompatApi21$SharedElementCallback21 */
    public static abstract class SharedElementCallback21 {
        public abstract Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF);

        public abstract View onCreateSnapshotView(Context context, Parcelable parcelable);

        public abstract void onMapSharedElements(List<String> list, Map<String, View> map);

        public abstract void onRejectSharedElements(List<View> list);

        public abstract void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3);

        public abstract void onSharedElementStart(List<String> list, List<View> list2, List<View> list3);

        public SharedElementCallback21() {
        }
    }

    /* renamed from: android.support.v4.app.ActivityCompatApi21$SharedElementCallbackImpl */
    private static class SharedElementCallbackImpl extends SharedElementCallback {
        private SharedElementCallback21 mCallback;

        public SharedElementCallbackImpl(SharedElementCallback21 sharedElementCallback21) {
            SharedElementCallback21 callback = sharedElementCallback21;
            SharedElementCallback21 sharedElementCallback212 = callback;
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

    ActivityCompatApi21() {
    }

    public static void finishAfterTransition(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        activity2.finishAfterTransition();
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback21 sharedElementCallback21) {
        Activity activity2 = activity;
        SharedElementCallback21 callback = sharedElementCallback21;
        Activity activity3 = activity2;
        SharedElementCallback21 sharedElementCallback212 = callback;
        activity2.setEnterSharedElementCallback(createCallback(callback));
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback21 sharedElementCallback21) {
        Activity activity2 = activity;
        SharedElementCallback21 callback = sharedElementCallback21;
        Activity activity3 = activity2;
        SharedElementCallback21 sharedElementCallback212 = callback;
        activity2.setExitSharedElementCallback(createCallback(callback));
    }

    public static void postponeEnterTransition(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        activity2.postponeEnterTransition();
    }

    public static void startPostponedEnterTransition(Activity activity) {
        Activity activity2 = activity;
        Activity activity3 = activity2;
        activity2.startPostponedEnterTransition();
    }

    private static SharedElementCallback createCallback(SharedElementCallback21 sharedElementCallback21) {
        SharedElementCallback21 callback = sharedElementCallback21;
        SharedElementCallback21 sharedElementCallback212 = callback;
        SharedElementCallbackImpl sharedElementCallbackImpl = null;
        if (callback != null) {
            sharedElementCallbackImpl = new SharedElementCallbackImpl(callback);
        }
        return sharedElementCallbackImpl;
    }
}
