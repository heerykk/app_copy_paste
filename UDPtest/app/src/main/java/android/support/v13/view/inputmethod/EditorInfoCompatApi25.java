package android.support.v13.view.inputmethod;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.EditorInfo;

@TargetApi(25)
@RequiresApi(25)
final class EditorInfoCompatApi25 {
    EditorInfoCompatApi25() {
    }

    public static void setContentMimeTypes(EditorInfo editorInfo, String[] strArr) {
        EditorInfo editorInfo2 = editorInfo;
        String[] contentMimeTypes = strArr;
        EditorInfo editorInfo3 = editorInfo2;
        String[] strArr2 = contentMimeTypes;
        editorInfo2.contentMimeTypes = contentMimeTypes;
    }

    public static String[] getContentMimeTypes(EditorInfo editorInfo) {
        EditorInfo editorInfo2 = editorInfo;
        EditorInfo editorInfo3 = editorInfo2;
        return editorInfo2.contentMimeTypes;
    }
}
