package android.support.p000v4.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.p000v4.p002os.CancellationSignal;
import android.support.p000v4.p002os.OperationCanceledException;

/* renamed from: android.support.v4.content.ContentResolverCompat */
public final class ContentResolverCompat {
    private static final ContentResolverCompatImpl IMPL;

    /* renamed from: android.support.v4.content.ContentResolverCompat$ContentResolverCompatImpl */
    interface ContentResolverCompatImpl {
        Cursor query(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal);
    }

    /* renamed from: android.support.v4.content.ContentResolverCompat$ContentResolverCompatImplBase */
    static class ContentResolverCompatImplBase implements ContentResolverCompatImpl {
        ContentResolverCompatImplBase() {
        }

        public Cursor query(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
            ContentResolver resolver = contentResolver;
            Uri uri2 = uri;
            String[] projection = strArr;
            String selection = str;
            String[] selectionArgs = strArr2;
            String sortOrder = str2;
            CancellationSignal cancellationSignal2 = cancellationSignal;
            ContentResolver contentResolver2 = resolver;
            Uri uri3 = uri2;
            String[] strArr3 = projection;
            String str3 = selection;
            String[] strArr4 = selectionArgs;
            String str4 = sortOrder;
            CancellationSignal cancellationSignal3 = cancellationSignal2;
            if (cancellationSignal2 != null) {
                cancellationSignal2.throwIfCanceled();
            }
            return resolver.query(uri2, projection, selection, selectionArgs, sortOrder);
        }
    }

    /* renamed from: android.support.v4.content.ContentResolverCompat$ContentResolverCompatImplJB */
    static class ContentResolverCompatImplJB extends ContentResolverCompatImplBase {
        ContentResolverCompatImplJB() {
        }

        public Cursor query(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
            ContentResolver resolver = contentResolver;
            Uri uri2 = uri;
            String[] projection = strArr;
            String selection = str;
            String[] selectionArgs = strArr2;
            String sortOrder = str2;
            CancellationSignal cancellationSignal2 = cancellationSignal;
            ContentResolver contentResolver2 = resolver;
            Uri uri3 = uri2;
            String[] strArr3 = projection;
            String str3 = selection;
            String[] strArr4 = selectionArgs;
            String str4 = sortOrder;
            CancellationSignal cancellationSignal3 = cancellationSignal2;
            try {
                return ContentResolverCompatJellybean.query(resolver, uri2, projection, selection, selectionArgs, sortOrder, cancellationSignal2 == null ? null : cancellationSignal2.getCancellationSignalObject());
            } catch (Exception e) {
                Exception exc = e;
                if (!ContentResolverCompatJellybean.isFrameworkOperationCanceledException(e)) {
                    throw e;
                }
                throw new OperationCanceledException();
            }
        }
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 16) {
            IMPL = new ContentResolverCompatImplBase();
        } else {
            IMPL = new ContentResolverCompatImplJB();
        }
    }

    private ContentResolverCompat() {
    }

    public static Cursor query(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
        ContentResolver resolver = contentResolver;
        Uri uri2 = uri;
        String[] projection = strArr;
        String selection = str;
        String[] selectionArgs = strArr2;
        String sortOrder = str2;
        CancellationSignal cancellationSignal2 = cancellationSignal;
        ContentResolver contentResolver2 = resolver;
        Uri uri3 = uri2;
        String[] strArr3 = projection;
        String str3 = selection;
        String[] strArr4 = selectionArgs;
        String str4 = sortOrder;
        CancellationSignal cancellationSignal3 = cancellationSignal2;
        return IMPL.query(resolver, uri2, projection, selection, selectionArgs, sortOrder, cancellationSignal2);
    }
}
