package android.support.v13.view.inputmethod;

import android.annotation.TargetApi;
import android.content.ClipDescription;
import android.net.Uri;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.InputContentInfo;

@TargetApi(25)
@RequiresApi(25)
final class InputContentInfoCompatApi25 {
    InputContentInfoCompatApi25() {
    }

    public static Object create(Uri uri, ClipDescription clipDescription, Uri uri2) {
        Uri contentUri = uri;
        ClipDescription description = clipDescription;
        Uri linkUri = uri2;
        Uri uri3 = contentUri;
        ClipDescription clipDescription2 = description;
        Uri uri4 = linkUri;
        return new InputContentInfo(contentUri, description, linkUri);
    }

    public static Uri getContentUri(Object obj) {
        Object inputContentInfo = obj;
        Object obj2 = inputContentInfo;
        return ((InputContentInfo) inputContentInfo).getContentUri();
    }

    public static ClipDescription getDescription(Object obj) {
        Object inputContentInfo = obj;
        Object obj2 = inputContentInfo;
        return ((InputContentInfo) inputContentInfo).getDescription();
    }

    public static Uri getLinkUri(Object obj) {
        Object inputContentInfo = obj;
        Object obj2 = inputContentInfo;
        return ((InputContentInfo) inputContentInfo).getLinkUri();
    }

    public static void requestPermission(Object obj) {
        Object inputContentInfo = obj;
        Object obj2 = inputContentInfo;
        ((InputContentInfo) inputContentInfo).requestPermission();
    }

    public static void releasePermission(Object obj) {
        Object inputContentInfo = obj;
        Object obj2 = inputContentInfo;
        ((InputContentInfo) inputContentInfo).releasePermission();
    }
}
