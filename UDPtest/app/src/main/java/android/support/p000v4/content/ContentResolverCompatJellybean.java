package android.support.p000v4.content;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import android.support.annotation.RequiresApi;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.content.ContentResolverCompatJellybean */
class ContentResolverCompatJellybean {
    ContentResolverCompatJellybean() {
    }

    public static Cursor query(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, Object obj) {
        ContentResolver resolver = contentResolver;
        Uri uri2 = uri;
        String[] projection = strArr;
        String selection = str;
        String[] selectionArgs = strArr2;
        String sortOrder = str2;
        Object cancellationSignalObj = obj;
        ContentResolver contentResolver2 = resolver;
        Uri uri3 = uri2;
        String[] strArr3 = projection;
        String str3 = selection;
        String[] strArr4 = selectionArgs;
        String str4 = sortOrder;
        Object obj2 = cancellationSignalObj;
        return resolver.query(uri2, projection, selection, selectionArgs, sortOrder, (CancellationSignal) cancellationSignalObj);
    }

    static boolean isFrameworkOperationCanceledException(Exception exc) {
        Exception e = exc;
        Exception exc2 = e;
        return e instanceof OperationCanceledException;
    }
}
