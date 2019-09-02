package android.support.p000v4.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: android.support.v4.content.FileProvider */
public class FileProvider extends ContentProvider {
    private static final String ATTR_NAME = "name";
    private static final String ATTR_PATH = "path";
    private static final String[] COLUMNS;
    private static final File DEVICE_ROOT = new File("/");
    private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
    private static final String TAG_CACHE_PATH = "cache-path";
    private static final String TAG_EXTERNAL = "external-path";
    private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
    private static final String TAG_EXTERNAL_FILES = "external-files-path";
    private static final String TAG_FILES_PATH = "files-path";
    private static final String TAG_ROOT_PATH = "root-path";
    private static HashMap<String, PathStrategy> sCache = new HashMap<>();
    private PathStrategy mStrategy;

    /* renamed from: android.support.v4.content.FileProvider$PathStrategy */
    interface PathStrategy {
        File getFileForUri(Uri uri);

        Uri getUriForFile(File file);
    }

    /* renamed from: android.support.v4.content.FileProvider$SimplePathStrategy */
    static class SimplePathStrategy implements PathStrategy {
        private final String mAuthority;
        private final HashMap<String, File> mRoots = new HashMap<>();

        public SimplePathStrategy(String str) {
            String authority = str;
            String str2 = authority;
            this.mAuthority = authority;
        }

        public void addRoot(String str, File file) {
            String name = str;
            File root = file;
            String str2 = name;
            File file2 = root;
            if (!TextUtils.isEmpty(name)) {
                try {
                    Object put = this.mRoots.put(name, root.getCanonicalFile());
                } catch (IOException e) {
                    IOException iOException = e;
                    throw new IllegalArgumentException("Failed to resolve canonical path for " + root, e);
                }
            } else {
                throw new IllegalArgumentException("Name must not be empty");
            }
        }

        public Uri getUriForFile(File file) {
            String path;
            File file2 = file;
            File file3 = file2;
            try {
                String path2 = file2.getCanonicalPath();
                Entry entry = null;
                for (Entry entry2 : this.mRoots.entrySet()) {
                    Entry entry3 = entry2;
                    String rootPath = ((File) entry2.getValue()).getPath();
                    if (path2.startsWith(rootPath) && (entry == null || rootPath.length() > ((File) entry.getValue()).getPath().length())) {
                        entry = entry3;
                    }
                }
                if (entry != null) {
                    String path3 = ((File) entry.getValue()).getPath();
                    String str = path3;
                    if (!path3.endsWith("/")) {
                        path = path2.substring(str.length() + 1);
                    } else {
                        path = path2.substring(str.length());
                    }
                    return new Builder().scheme("content").authority(this.mAuthority).encodedPath(Uri.encode((String) entry.getKey()) + '/' + Uri.encode(path, "/")).build();
                }
                throw new IllegalArgumentException("Failed to find configured root that contains " + path2);
            } catch (IOException e) {
                IOException iOException = e;
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file2);
            }
        }

        public File getFileForUri(Uri uri) {
            Uri uri2 = uri;
            Uri uri3 = uri2;
            String encodedPath = uri2.getEncodedPath();
            String path = encodedPath;
            int indexOf = encodedPath.indexOf(47, 1);
            int i = indexOf;
            String tag = Uri.decode(path.substring(1, indexOf));
            String path2 = Uri.decode(path.substring(indexOf + 1));
            File file = (File) this.mRoots.get(tag);
            File root = file;
            if (file != null) {
                File file2 = new File(root, path2);
                File file3 = file2;
                try {
                    File canonicalFile = file2.getCanonicalFile();
                    File file4 = canonicalFile;
                    if (canonicalFile.getPath().startsWith(root.getPath())) {
                        return file4;
                    }
                    throw new SecurityException("Resolved path jumped beyond configured root");
                } catch (IOException e) {
                    IOException iOException = e;
                    throw new IllegalArgumentException("Failed to resolve canonical path for " + file3);
                }
            } else {
                throw new IllegalArgumentException("Unable to find configured root for " + uri2);
            }
        }
    }

    public FileProvider() {
    }

    static {
        String[] strArr = new String[2];
        strArr[0] = "_display_name";
        strArr[1] = "_size";
        COLUMNS = strArr;
    }

    public boolean onCreate() {
        return true;
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        Context context2 = context;
        ProviderInfo info = providerInfo;
        Context context3 = context2;
        ProviderInfo providerInfo2 = info;
        super.attachInfo(context2, info);
        if (info.exported) {
            throw new SecurityException("Provider must not be exported");
        } else if (info.grantUriPermissions) {
            this.mStrategy = getPathStrategy(context2, info.authority);
        } else {
            throw new SecurityException("Provider must grant uri permissions");
        }
    }

    public static Uri getUriForFile(Context context, String str, File file) {
        Context context2 = context;
        String authority = str;
        File file2 = file;
        Context context3 = context2;
        String str2 = authority;
        File file3 = file2;
        PathStrategy pathStrategy = getPathStrategy(context2, authority);
        PathStrategy pathStrategy2 = pathStrategy;
        return pathStrategy.getUriForFile(file2);
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Uri uri2 = uri;
        String[] projection = strArr;
        Uri uri3 = uri2;
        String[] projection2 = projection;
        String str3 = str;
        String[] strArr3 = strArr2;
        String str4 = str2;
        File file = this.mStrategy.getFileForUri(uri2);
        if (projection == null) {
            projection2 = COLUMNS;
        }
        String[] cols = new String[projection2.length];
        Object[] values = new Object[projection2.length];
        int i = 0;
        String[] strArr4 = projection2;
        int length = projection2.length;
        for (int i2 = 0; i2 < length; i2++) {
            String col = strArr4[i2];
            if ("_display_name".equals(col)) {
                cols[i] = "_display_name";
                int i3 = i;
                i++;
                values[i3] = file.getName();
            } else if ("_size".equals(col)) {
                cols[i] = "_size";
                int i4 = i;
                i++;
                values[i4] = Long.valueOf(file.length());
            }
        }
        String[] cols2 = copyOf(cols, i);
        Object[] values2 = copyOf(values, i);
        MatrixCursor matrixCursor = new MatrixCursor(cols2, 1);
        MatrixCursor matrixCursor2 = matrixCursor;
        matrixCursor.addRow(values2);
        return matrixCursor2;
    }

    public String getType(Uri uri) {
        Uri uri2 = uri;
        Uri uri3 = uri2;
        File fileForUri = this.mStrategy.getFileForUri(uri2);
        File file = fileForUri;
        int lastIndexOf = fileForUri.getName().lastIndexOf(46);
        int lastDot = lastIndexOf;
        if (lastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName().substring(lastDot + 1));
            String mime = mimeTypeFromExtension;
            if (mimeTypeFromExtension != null) {
                return mime;
            }
        }
        return "application/octet-stream";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri uri2 = uri;
        ContentValues contentValues2 = contentValues;
        throw new UnsupportedOperationException("No external inserts");
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Uri uri2 = uri;
        ContentValues contentValues2 = contentValues;
        String str2 = str;
        String[] strArr2 = strArr;
        throw new UnsupportedOperationException("No external updates");
    }

    public int delete(Uri uri, String str, String[] strArr) {
        Uri uri2 = uri;
        Uri uri3 = uri2;
        String str2 = str;
        String[] strArr2 = strArr;
        File fileForUri = this.mStrategy.getFileForUri(uri2);
        File file = fileForUri;
        return !fileForUri.delete() ? 0 : 1;
    }

    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        Uri uri2 = uri;
        String mode = str;
        Uri uri3 = uri2;
        String str2 = mode;
        File file = this.mStrategy.getFileForUri(uri2);
        int modeToMode = modeToMode(mode);
        int i = modeToMode;
        return ParcelFileDescriptor.open(file, modeToMode);
    }

    /* JADX INFO: finally extract failed */
    private static PathStrategy getPathStrategy(Context context, String str) {
        Context context2 = context;
        String authority = str;
        Context context3 = context2;
        String str2 = authority;
        HashMap<String, PathStrategy> hashMap = sCache;
        HashMap<String, PathStrategy> hashMap2 = hashMap;
        synchronized (hashMap) {
            try {
                PathStrategy pathStrategy = (PathStrategy) sCache.get(authority);
                PathStrategy strat = pathStrategy;
                if (pathStrategy == null) {
                    strat = parsePathStrategy(context2, authority);
                    Object put = sCache.put(authority, strat);
                }
                return strat;
            } catch (IOException e) {
                IOException iOException = e;
                throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e);
            } catch (XmlPullParserException e2) {
                XmlPullParserException xmlPullParserException = e2;
                throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2);
            } catch (Throwable th) {
                HashMap<String, PathStrategy> hashMap3 = hashMap2;
                throw th;
            }
        }
    }

    private static PathStrategy parsePathStrategy(Context context, String str) throws IOException, XmlPullParserException {
        Context context2 = context;
        String authority = str;
        Context context3 = context2;
        String str2 = authority;
        SimplePathStrategy strat = new SimplePathStrategy(authority);
        ProviderInfo resolveContentProvider = context2.getPackageManager().resolveContentProvider(authority, 128);
        ProviderInfo providerInfo = resolveContentProvider;
        XmlResourceParser loadXmlMetaData = resolveContentProvider.loadXmlMetaData(context2.getPackageManager(), META_DATA_FILE_PROVIDER_PATHS);
        XmlResourceParser in = loadXmlMetaData;
        if (loadXmlMetaData != null) {
            while (true) {
                int next = in.next();
                int i = next;
                if (next == 1) {
                    return strat;
                }
                if (next == 2) {
                    String tag = in.getName();
                    String name = in.getAttributeValue(null, ATTR_NAME);
                    String path = in.getAttributeValue(null, ATTR_PATH);
                    File target = null;
                    if (TAG_ROOT_PATH.equals(tag)) {
                        target = DEVICE_ROOT;
                    } else if (TAG_FILES_PATH.equals(tag)) {
                        target = context2.getFilesDir();
                    } else if (TAG_CACHE_PATH.equals(tag)) {
                        target = context2.getCacheDir();
                    } else if (TAG_EXTERNAL.equals(tag)) {
                        target = Environment.getExternalStorageDirectory();
                    } else if (TAG_EXTERNAL_FILES.equals(tag)) {
                        File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(context2, null);
                        File[] externalFilesDirs2 = externalFilesDirs;
                        if (externalFilesDirs.length > 0) {
                            target = externalFilesDirs2[0];
                        }
                    } else if (TAG_EXTERNAL_CACHE.equals(tag)) {
                        File[] externalCacheDirs = ContextCompat.getExternalCacheDirs(context2);
                        File[] externalCacheDirs2 = externalCacheDirs;
                        if (externalCacheDirs.length > 0) {
                            target = externalCacheDirs2[0];
                        }
                    }
                    if (target != null) {
                        strat.addRoot(name, buildPath(target, path));
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
    }

    private static int modeToMode(String str) {
        int modeBits;
        String mode = str;
        String str2 = mode;
        if ("r".equals(mode)) {
            modeBits = 268435456;
        } else if ("w".equals(mode) || "wt".equals(mode)) {
            modeBits = 738197504;
        } else if ("wa".equals(mode)) {
            modeBits = 704643072;
        } else if ("rw".equals(mode)) {
            modeBits = 939524096;
        } else if (!"rwt".equals(mode)) {
            throw new IllegalArgumentException("Invalid mode: " + mode);
        } else {
            modeBits = 1006632960;
        }
        return modeBits;
    }

    private static File buildPath(File file, String... strArr) {
        File base = file;
        String[] segments = strArr;
        File file2 = base;
        String[] strArr2 = segments;
        File cur = base;
        for (String str : segments) {
            String segment = str;
            if (str != null) {
                cur = new File(cur, segment);
            }
        }
        return cur;
    }

    private static String[] copyOf(String[] strArr, int i) {
        String[] original = strArr;
        int newLength = i;
        String[] strArr2 = original;
        int i2 = newLength;
        String[] result = new String[newLength];
        System.arraycopy(original, 0, result, 0, newLength);
        return result;
    }

    private static Object[] copyOf(Object[] objArr, int i) {
        Object[] original = objArr;
        int newLength = i;
        Object[] objArr2 = original;
        int i2 = newLength;
        Object[] result = new Object[newLength];
        System.arraycopy(original, 0, result, 0, newLength);
        return result;
    }
}
