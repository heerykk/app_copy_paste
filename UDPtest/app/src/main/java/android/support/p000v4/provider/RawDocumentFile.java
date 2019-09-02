package android.support.p000v4.provider;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/* renamed from: android.support.v4.provider.RawDocumentFile */
class RawDocumentFile extends DocumentFile {
    private File mFile;

    RawDocumentFile(DocumentFile documentFile, File file) {
        DocumentFile parent = documentFile;
        File file2 = file;
        DocumentFile documentFile2 = parent;
        File file3 = file2;
        super(parent);
        this.mFile = file2;
    }

    public DocumentFile createFile(String str, String str2) {
        String mimeType = str;
        String displayName = str2;
        String str3 = mimeType;
        String displayName2 = displayName;
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        String extension = extensionFromMimeType;
        if (extensionFromMimeType != null) {
            displayName2 = displayName + "." + extension;
        }
        File file = new File(this.mFile, displayName2);
        File target = file;
        try {
            boolean createNewFile = file.createNewFile();
            return new RawDocumentFile(this, target);
        } catch (IOException e) {
            IOException iOException = e;
            int w = Log.w("DocumentFile", "Failed to createFile: " + e);
            return null;
        }
    }

    public DocumentFile createDirectory(String str) {
        String displayName = str;
        String str2 = displayName;
        File file = new File(this.mFile, displayName);
        File target = file;
        if (!file.isDirectory() && !target.mkdir()) {
            return null;
        }
        return new RawDocumentFile(this, target);
    }

    public Uri getUri() {
        return Uri.fromFile(this.mFile);
    }

    public String getName() {
        return this.mFile.getName();
    }

    public String getType() {
        if (!this.mFile.isDirectory()) {
            return getTypeForName(this.mFile.getName());
        }
        return null;
    }

    public boolean isDirectory() {
        return this.mFile.isDirectory();
    }

    public boolean isFile() {
        return this.mFile.isFile();
    }

    public boolean isVirtual() {
        return false;
    }

    public long lastModified() {
        return this.mFile.lastModified();
    }

    public long length() {
        return this.mFile.length();
    }

    public boolean canRead() {
        return this.mFile.canRead();
    }

    public boolean canWrite() {
        return this.mFile.canWrite();
    }

    public boolean delete() {
        boolean deleteContents = deleteContents(this.mFile);
        return this.mFile.delete();
    }

    public boolean exists() {
        return this.mFile.exists();
    }

    public DocumentFile[] listFiles() {
        ArrayList arrayList = new ArrayList();
        File[] listFiles = this.mFile.listFiles();
        File[] files = listFiles;
        if (listFiles != null) {
            int length = files.length;
            for (int i = 0; i < length; i++) {
                boolean add = arrayList.add(new RawDocumentFile(this, files[i]));
            }
        }
        return (DocumentFile[]) arrayList.toArray(new DocumentFile[arrayList.size()]);
    }

    public boolean renameTo(String str) {
        String displayName = str;
        String str2 = displayName;
        File target = new File(this.mFile.getParentFile(), displayName);
        if (!this.mFile.renameTo(target)) {
            return false;
        }
        this.mFile = target;
        return true;
    }

    private static String getTypeForName(String str) {
        String name = str;
        String str2 = name;
        int lastIndexOf = name.lastIndexOf(46);
        int lastDot = lastIndexOf;
        if (lastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(lastDot + 1).toLowerCase());
            String mime = mimeTypeFromExtension;
            if (mimeTypeFromExtension != null) {
                return mime;
            }
        }
        return "application/octet-stream";
    }

    private static boolean deleteContents(File file) {
        File dir = file;
        File file2 = dir;
        File[] listFiles = dir.listFiles();
        File[] files = listFiles;
        boolean success = true;
        if (listFiles != null) {
            for (File file3 : files) {
                File file4 = file3;
                if (file3.isDirectory()) {
                    success &= deleteContents(file4);
                }
                if (!file4.delete()) {
                    int w = Log.w("DocumentFile", "Failed to delete " + file4);
                    success = false;
                }
            }
        }
        return success;
    }
}
