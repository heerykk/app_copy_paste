package android.support.v13.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.p002os.BuildCompat;
import android.view.DragEvent;

@TargetApi(13)
@RequiresApi(13)
public final class DragAndDropPermissionsCompat {
    private static DragAndDropPermissionsCompatImpl IMPL;
    private Object mDragAndDropPermissions;

    static class Api24DragAndDropPermissionsCompatImpl extends BaseDragAndDropPermissionsCompatImpl {
        Api24DragAndDropPermissionsCompatImpl() {
        }

        public Object request(Activity activity, DragEvent dragEvent) {
            Activity activity2 = activity;
            DragEvent dragEvent2 = dragEvent;
            Activity activity3 = activity2;
            DragEvent dragEvent3 = dragEvent2;
            return DragAndDropPermissionsCompatApi24.request(activity2, dragEvent2);
        }

        public void release(Object obj) {
            Object dragAndDropPermissions = obj;
            Object obj2 = dragAndDropPermissions;
            DragAndDropPermissionsCompatApi24.release(dragAndDropPermissions);
        }
    }

    static class BaseDragAndDropPermissionsCompatImpl implements DragAndDropPermissionsCompatImpl {
        BaseDragAndDropPermissionsCompatImpl() {
        }

        public Object request(Activity activity, DragEvent dragEvent) {
            Activity activity2 = activity;
            DragEvent dragEvent2 = dragEvent;
            return null;
        }

        public void release(Object obj) {
            Object obj2 = obj;
        }
    }

    interface DragAndDropPermissionsCompatImpl {
        void release(Object obj);

        Object request(Activity activity, DragEvent dragEvent);
    }

    static {
        if (!BuildCompat.isAtLeastN()) {
            IMPL = new BaseDragAndDropPermissionsCompatImpl();
        } else {
            IMPL = new Api24DragAndDropPermissionsCompatImpl();
        }
    }

    private DragAndDropPermissionsCompat(Object obj) {
        Object dragAndDropPermissions = obj;
        Object obj2 = dragAndDropPermissions;
        this.mDragAndDropPermissions = dragAndDropPermissions;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static DragAndDropPermissionsCompat request(Activity activity, DragEvent dragEvent) {
        Activity activity2 = activity;
        DragEvent dragEvent2 = dragEvent;
        Activity activity3 = activity2;
        DragEvent dragEvent3 = dragEvent2;
        Object request = IMPL.request(activity2, dragEvent2);
        Object dragAndDropPermissions = request;
        if (request == null) {
            return null;
        }
        return new DragAndDropPermissionsCompat(dragAndDropPermissions);
    }

    public void release() {
        IMPL.release(this.mDragAndDropPermissions);
    }
}
