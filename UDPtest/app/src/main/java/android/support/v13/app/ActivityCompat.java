package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.support.v13.view.DragAndDropPermissionsCompat;
import android.view.DragEvent;

@TargetApi(13)
@RequiresApi(13)
public class ActivityCompat extends android.support.p000v4.app.ActivityCompat {
    public static DragAndDropPermissionsCompat requestDragAndDropPermissions(Activity activity, DragEvent dragEvent) {
        Activity activity2 = activity;
        DragEvent dragEvent2 = dragEvent;
        Activity activity3 = activity2;
        DragEvent dragEvent3 = dragEvent2;
        return DragAndDropPermissionsCompat.request(activity2, dragEvent2);
    }

    protected ActivityCompat() {
    }
}
