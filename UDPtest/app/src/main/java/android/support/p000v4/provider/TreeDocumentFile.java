package android.support.p000v4.provider;

import android.content.Context;
import android.net.Uri;

/* renamed from: android.support.v4.provider.TreeDocumentFile */
class TreeDocumentFile extends DocumentFile {
    private Context mContext;
    private Uri mUri;

    TreeDocumentFile(DocumentFile documentFile, Context context, Uri uri) {
        DocumentFile parent = documentFile;
        Context context2 = context;
        Uri uri2 = uri;
        DocumentFile documentFile2 = parent;
        Context context3 = context2;
        Uri uri3 = uri2;
        super(parent);
        this.mContext = context2;
        this.mUri = uri2;
    }

    public DocumentFile createFile(String str, String str2) {
        TreeDocumentFile treeDocumentFile;
        String mimeType = str;
        String displayName = str2;
        String str3 = mimeType;
        String str4 = displayName;
        Uri createFile = DocumentsContractApi21.createFile(this.mContext, this.mUri, mimeType, displayName);
        Uri result = createFile;
        if (createFile == null) {
            treeDocumentFile = null;
        } else {
            treeDocumentFile = new TreeDocumentFile(this, this.mContext, result);
        }
        return treeDocumentFile;
    }

    public DocumentFile createDirectory(String str) {
        String displayName = str;
        String str2 = displayName;
        Uri createDirectory = DocumentsContractApi21.createDirectory(this.mContext, this.mUri, displayName);
        return createDirectory == null ? null : new TreeDocumentFile(this, this.mContext, createDirectory);
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String getName() {
        return DocumentsContractApi19.getName(this.mContext, this.mUri);
    }

    public String getType() {
        return DocumentsContractApi19.getType(this.mContext, this.mUri);
    }

    public boolean isDirectory() {
        return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
    }

    public boolean isFile() {
        return DocumentsContractApi19.isFile(this.mContext, this.mUri);
    }

    public boolean isVirtual() {
        return DocumentsContractApi19.isVirtual(this.mContext, this.mUri);
    }

    public long lastModified() {
        return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
    }

    public long length() {
        return DocumentsContractApi19.length(this.mContext, this.mUri);
    }

    public boolean canRead() {
        return DocumentsContractApi19.canRead(this.mContext, this.mUri);
    }

    public boolean canWrite() {
        return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
    }

    public boolean delete() {
        return DocumentsContractApi19.delete(this.mContext, this.mUri);
    }

    public boolean exists() {
        return DocumentsContractApi19.exists(this.mContext, this.mUri);
    }

    public DocumentFile[] listFiles() {
        Uri[] listFiles = DocumentsContractApi21.listFiles(this.mContext, this.mUri);
        Uri[] result = listFiles;
        DocumentFile[] resultFiles = new DocumentFile[listFiles.length];
        for (int i = 0; i < result.length; i++) {
            DocumentFile[] documentFileArr = resultFiles;
            documentFileArr[i] = new TreeDocumentFile(this, this.mContext, result[i]);
        }
        return resultFiles;
    }

    public boolean renameTo(String str) {
        String displayName = str;
        String str2 = displayName;
        Uri renameTo = DocumentsContractApi21.renameTo(this.mContext, this.mUri, displayName);
        Uri result = renameTo;
        if (renameTo == null) {
            return false;
        }
        this.mUri = result;
        return true;
    }
}
