package android.support.p000v4.util;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* renamed from: android.support.v4.util.AtomicFile */
public class AtomicFile {
    private final File mBackupName;
    private final File mBaseName;

    public AtomicFile(File file) {
        File baseName = file;
        File file2 = baseName;
        this.mBaseName = baseName;
        this.mBackupName = new File(baseName.getPath() + ".bak");
    }

    public File getBaseFile() {
        return this.mBaseName;
    }

    public void delete() {
        boolean delete = this.mBaseName.delete();
        boolean delete2 = this.mBackupName.delete();
    }

    public FileOutputStream startWrite() throws IOException {
        FileOutputStream str;
        if (this.mBaseName.exists()) {
            if (this.mBackupName.exists()) {
                boolean delete = this.mBaseName.delete();
            } else if (!this.mBaseName.renameTo(this.mBackupName)) {
                int w = Log.w("AtomicFile", "Couldn't rename file " + this.mBaseName + " to backup file " + this.mBackupName);
            }
        }
        try {
            str = new FileOutputStream(this.mBaseName);
        } catch (FileNotFoundException e) {
            FileNotFoundException fileNotFoundException = e;
            File parentFile = this.mBaseName.getParentFile();
            File file = parentFile;
            if (parentFile.mkdirs()) {
                try {
                    str = new FileOutputStream(this.mBaseName);
                } catch (FileNotFoundException e2) {
                    FileNotFoundException fileNotFoundException2 = e2;
                    throw new IOException("Couldn't create " + this.mBaseName);
                }
            } else {
                throw new IOException("Couldn't create directory " + this.mBaseName);
            }
        }
        return str;
    }

    public void finishWrite(FileOutputStream fileOutputStream) {
        FileOutputStream str = fileOutputStream;
        FileOutputStream fileOutputStream2 = str;
        if (str != null) {
            boolean sync = sync(str);
            try {
                str.close();
                boolean delete = this.mBackupName.delete();
            } catch (IOException e) {
                IOException iOException = e;
                int w = Log.w("AtomicFile", "finishWrite: Got exception:", e);
            }
        }
    }

    public void failWrite(FileOutputStream fileOutputStream) {
        FileOutputStream str = fileOutputStream;
        FileOutputStream fileOutputStream2 = str;
        if (str != null) {
            boolean sync = sync(str);
            try {
                str.close();
                boolean delete = this.mBaseName.delete();
                boolean renameTo = this.mBackupName.renameTo(this.mBaseName);
            } catch (IOException e) {
                IOException iOException = e;
                int w = Log.w("AtomicFile", "failWrite: Got exception:", e);
            }
        }
    }

    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mBackupName.exists()) {
            boolean delete = this.mBaseName.delete();
            boolean renameTo = this.mBackupName.renameTo(this.mBaseName);
        }
        return new FileInputStream(this.mBaseName);
    }

    /* JADX INFO: finally extract failed */
    public byte[] readFully() throws IOException {
        FileInputStream openRead = openRead();
        FileInputStream stream = openRead;
        int pos = 0;
        try {
            int available = openRead.available();
            int i = available;
            byte[] data = new byte[available];
            while (true) {
                int read = stream.read(data, pos, data.length - pos);
                int amt = read;
                if (read > 0) {
                    pos += amt;
                    int available2 = stream.available();
                    int avail = available2;
                    if (available2 > data.length - pos) {
                        byte[] bArr = new byte[(pos + avail)];
                        System.arraycopy(data, 0, bArr, 0, pos);
                        data = bArr;
                    }
                } else {
                    byte[] bArr2 = data;
                    stream.close();
                    return bArr2;
                }
            }
        } catch (Throwable th) {
            stream.close();
            throw th;
        }
    }

    static boolean sync(FileOutputStream fileOutputStream) {
        FileOutputStream stream = fileOutputStream;
        FileOutputStream fileOutputStream2 = stream;
        if (stream != null) {
            try {
                stream.getFD().sync();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
