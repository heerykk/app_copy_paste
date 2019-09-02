package android.support.p000v4.provider;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.util.ArrayList;

@TargetApi(21)
@RequiresApi(21)
/* renamed from: android.support.v4.provider.DocumentsContractApi21 */
class DocumentsContractApi21 {
    private static final String TAG = "DocumentFile";

    DocumentsContractApi21() {
    }

    public static Uri createFile(Context context, Uri uri, String str, String str2) {
        Context context2 = context;
        Uri self = uri;
        String mimeType = str;
        String displayName = str2;
        Context context3 = context2;
        Uri uri2 = self;
        String str3 = mimeType;
        String str4 = displayName;
        return DocumentsContract.createDocument(context2.getContentResolver(), self, mimeType, displayName);
    }

    public static Uri createDirectory(Context context, Uri uri, String str) {
        Context context2 = context;
        Uri self = uri;
        String displayName = str;
        Context context3 = context2;
        Uri uri2 = self;
        String str2 = displayName;
        return createFile(context2, self, "vnd.android.document/directory", displayName);
    }

    public static Uri prepareTreeUri(Uri uri) {
        Uri treeUri = uri;
        Uri uri2 = treeUri;
        return DocumentsContract.buildDocumentUriUsingTree(treeUri, DocumentsContract.getTreeDocumentId(treeUri));
    }

    public static Uri[] listFiles(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        ContentResolver resolver = context2.getContentResolver();
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(self, DocumentsContract.getDocumentId(self));
        ArrayList arrayList = new ArrayList();
        Cursor c = null;
        try {
            String[] strArr = new String[1];
            strArr[0] = "document_id";
            c = resolver.query(childrenUri, strArr, null, null, null);
            while (c.moveToNext()) {
                boolean add = arrayList.add(DocumentsContract.buildDocumentUriUsingTree(self, c.getString(0)));
            }
            closeQuietly(c);
        } catch (Exception e) {
            Exception exc = e;
            int w = Log.w(TAG, "Failed query: " + e);
            closeQuietly(c);
        } catch (Throwable th) {
            closeQuietly(c);
            throw th;
        }
        return (Uri[]) arrayList.toArray(new Uri[arrayList.size()]);
    }

    public static Uri renameTo(Context context, Uri uri, String str) {
        Context context2 = context;
        Uri self = uri;
        String displayName = str;
        Context context3 = context2;
        Uri uri2 = self;
        String str2 = displayName;
        return DocumentsContract.renameDocument(context2.getContentResolver(), self, displayName);
    }

    private static void closeQuietly(AutoCloseable autoCloseable) {
        AutoCloseable closeable = autoCloseable;
        AutoCloseable autoCloseable2 = closeable;
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                throw e;
            } catch (Exception e2) {
            }
        }
    }
}
