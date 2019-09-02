package android.support.p000v4.provider;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

@TargetApi(19)
@RequiresApi(19)
/* renamed from: android.support.v4.provider.DocumentsContractApi19 */
class DocumentsContractApi19 {
    private static final int FLAG_VIRTUAL_DOCUMENT = 512;
    private static final String TAG = "DocumentFile";

    DocumentsContractApi19() {
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        return DocumentsContract.isDocumentUri(context2, self);
    }

    public static boolean isVirtual(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        if (!isDocumentUri(context2, self)) {
            return false;
        }
        return (getFlags(context2, self) & 512) != 0;
    }

    public static String getName(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        return queryForString(context2, self, "_display_name", null);
    }

    private static String getRawType(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        return queryForString(context2, self, "mime_type", null);
    }

    public static String getType(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        String rawType = getRawType(context2, self);
        if (!"vnd.android.document/directory".equals(rawType)) {
            return rawType;
        }
        return null;
    }

    public static long getFlags(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        return queryForLong(context2, self, "flags", 0);
    }

    public static boolean isDirectory(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        return "vnd.android.document/directory".equals(getRawType(context2, self));
    }

    public static boolean isFile(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        String type = getRawType(context2, self);
        if (!"vnd.android.document/directory".equals(type) && !TextUtils.isEmpty(type)) {
            return true;
        }
        return false;
    }

    public static long lastModified(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        return queryForLong(context2, self, "last_modified", 0);
    }

    public static long length(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        return queryForLong(context2, self, "_size", 0);
    }

    public static boolean canRead(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        if (context2.checkCallingOrSelfUriPermission(self, 1) != 0) {
            return false;
        }
        if (!TextUtils.isEmpty(getRawType(context2, self))) {
            return true;
        }
        return false;
    }

    public static boolean canWrite(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        if (context2.checkCallingOrSelfUriPermission(self, 2) != 0) {
            return false;
        }
        String type = getRawType(context2, self);
        int flags = queryForInt(context2, self, "flags", 0);
        if (TextUtils.isEmpty(type)) {
            return false;
        }
        if ((flags & 4) != 0) {
            return true;
        }
        if ("vnd.android.document/directory".equals(type) && (flags & 8) != 0) {
            return true;
        }
        if (!TextUtils.isEmpty(type) && (flags & 2) != 0) {
            return true;
        }
        return false;
    }

    public static boolean delete(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        return DocumentsContract.deleteDocument(context2.getContentResolver(), self);
    }

    public static boolean exists(Context context, Uri uri) {
        Context context2 = context;
        Uri self = uri;
        Context context3 = context2;
        Uri uri2 = self;
        ContentResolver resolver = context2.getContentResolver();
        Cursor c = null;
        try {
            String[] strArr = new String[1];
            strArr[0] = "document_id";
            Cursor query = resolver.query(self, strArr, null, null, null);
            c = query;
            boolean z = query.getCount() > 0;
            closeQuietly(c);
            return z;
        } catch (Exception e) {
            Exception exc = e;
            int w = Log.w(TAG, "Failed query: " + e);
            closeQuietly(c);
            return false;
        } catch (Throwable th) {
            closeQuietly(c);
            throw th;
        }
    }

    private static String queryForString(Context context, Uri uri, String str, String str2) {
        Context context2 = context;
        Uri self = uri;
        String column = str;
        String defaultValue = str2;
        Context context3 = context2;
        Uri uri2 = self;
        String str3 = column;
        String str4 = defaultValue;
        Cursor c = null;
        try {
            Cursor query = context2.getContentResolver().query(self, new String[]{column}, null, null, null);
            c = query;
            if (query.moveToFirst()) {
                if (!c.isNull(0)) {
                    String string = c.getString(0);
                    closeQuietly(c);
                    return string;
                }
            }
            closeQuietly(c);
            return defaultValue;
        } catch (Exception e) {
            int w = Log.w(TAG, "Failed query: " + e);
            closeQuietly(c);
            return defaultValue;
        } catch (Throwable th) {
            closeQuietly(c);
            throw th;
        }
    }

    private static int queryForInt(Context context, Uri uri, String str, int i) {
        Context context2 = context;
        Uri self = uri;
        String column = str;
        int defaultValue = i;
        Context context3 = context2;
        Uri uri2 = self;
        String str2 = column;
        int i2 = defaultValue;
        return (int) queryForLong(context2, self, column, (long) defaultValue);
    }

    private static long queryForLong(Context context, Uri uri, String str, long j) {
        Context context2 = context;
        Uri self = uri;
        String column = str;
        long defaultValue = j;
        Context context3 = context2;
        Uri uri2 = self;
        String str2 = column;
        long j2 = defaultValue;
        Cursor c = null;
        try {
            Cursor query = context2.getContentResolver().query(self, new String[]{column}, null, null, null);
            c = query;
            if (query.moveToFirst()) {
                if (!c.isNull(0)) {
                    long j3 = c.getLong(0);
                    closeQuietly(c);
                    return j3;
                }
            }
            closeQuietly(c);
            return defaultValue;
        } catch (Exception e) {
            Exception exc = e;
            int w = Log.w(TAG, "Failed query: " + e);
            closeQuietly(c);
            return defaultValue;
        } catch (Throwable th) {
            closeQuietly(c);
            throw th;
        }
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
