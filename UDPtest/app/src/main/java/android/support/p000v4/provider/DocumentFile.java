package android.support.p000v4.provider;

import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import java.io.File;

/* renamed from: android.support.v4.provider.DocumentFile */
public abstract class DocumentFile {
    static final String TAG = "DocumentFile";
    private final DocumentFile mParent;

    public abstract boolean canRead();

    public abstract boolean canWrite();

    public abstract DocumentFile createDirectory(String str);

    public abstract DocumentFile createFile(String str, String str2);

    public abstract boolean delete();

    public abstract boolean exists();

    public abstract String getName();

    public abstract String getType();

    public abstract Uri getUri();

    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public abstract boolean isVirtual();

    public abstract long lastModified();

    public abstract long length();

    public abstract DocumentFile[] listFiles();

    public abstract boolean renameTo(String str);

    DocumentFile(DocumentFile documentFile) {
        DocumentFile parent = documentFile;
        DocumentFile documentFile2 = parent;
        this.mParent = parent;
    }

    public static DocumentFile fromFile(File file) {
        File file2 = file;
        File file3 = file2;
        return new RawDocumentFile(null, file2);
    }

    public static DocumentFile fromSingleUri(Context context, Uri uri) {
        Context context2 = context;
        Uri singleUri = uri;
        Context context3 = context2;
        Uri uri2 = singleUri;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 19) {
            return null;
        }
        return new SingleDocumentFile(null, context2, singleUri);
    }

    public static DocumentFile fromTreeUri(Context context, Uri uri) {
        Context context2 = context;
        Uri treeUri = uri;
        Context context3 = context2;
        Uri uri2 = treeUri;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 21) {
            return null;
        }
        return new TreeDocumentFile(null, context2, DocumentsContractApi21.prepareTreeUri(treeUri));
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        Context context2 = context;
        Uri uri2 = uri;
        Context context3 = context2;
        Uri uri3 = uri2;
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 19) {
            return false;
        }
        return DocumentsContractApi19.isDocumentUri(context2, uri2);
    }

    public DocumentFile getParentFile() {
        return this.mParent;
    }

    public DocumentFile findFile(String str) {
        String displayName = str;
        String str2 = displayName;
        DocumentFile[] listFiles = listFiles();
        DocumentFile[] documentFileArr = listFiles;
        int length = listFiles.length;
        for (int i = 0; i < length; i++) {
            DocumentFile doc = documentFileArr[i];
            if (displayName.equals(doc.getName())) {
                return doc;
            }
        }
        return null;
    }
}
