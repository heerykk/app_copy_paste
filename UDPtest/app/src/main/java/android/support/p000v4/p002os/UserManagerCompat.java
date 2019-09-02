package android.support.p000v4.p002os;

import android.content.Context;

/* renamed from: android.support.v4.os.UserManagerCompat */
public class UserManagerCompat {
    private UserManagerCompat() {
    }

    public static boolean isUserUnlocked(Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (!BuildCompat.isAtLeastN()) {
            return true;
        }
        return UserManagerCompatApi24.isUserUnlocked(context2);
    }
}
