package android.support.p000v4.database;

import android.text.TextUtils;

/* renamed from: android.support.v4.database.DatabaseUtilsCompat */
public final class DatabaseUtilsCompat {
    private DatabaseUtilsCompat() {
    }

    public static String concatenateWhere(String str, String str2) {
        String a = str;
        String b = str2;
        String str3 = a;
        String str4 = b;
        if (TextUtils.isEmpty(a)) {
            return b;
        }
        if (!TextUtils.isEmpty(b)) {
            return "(" + a + ") AND (" + b + ")";
        }
        return a;
    }

    public static String[] appendSelectionArgs(String[] strArr, String[] strArr2) {
        String[] originalValues = strArr;
        String[] newValues = strArr2;
        String[] strArr3 = originalValues;
        String[] strArr4 = newValues;
        if (originalValues == null || originalValues.length == 0) {
            return newValues;
        }
        String[] result = new String[(originalValues.length + newValues.length)];
        System.arraycopy(originalValues, 0, result, 0, originalValues.length);
        System.arraycopy(newValues, 0, result, originalValues.length, newValues.length);
        return result;
    }
}
