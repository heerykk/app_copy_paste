package android.support.v13.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;

@TargetApi(24)
@RequiresApi(24)
class DragAndDropPermissionsCompatApi24 {
    DragAndDropPermissionsCompatApi24() {
    }

    public static Object request(Activity activity, DragEvent dragEvent) {
        Activity activity2 = activity;
        DragEvent dragEvent2 = dragEvent;
        Activity activity3 = activity2;
        DragEvent dragEvent3 = dragEvent2;
        return activity2.requestDragAndDropPermissions(dragEvent2);
    }

    public static void release(Object obj) {
        Object dragAndDropPermissions = obj;
        Object obj2 = dragAndDropPermissions;
        ((DragAndDropPermissions) dragAndDropPermissions).release();
    }
}
