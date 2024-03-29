package android.support.p000v4.p002os;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.UserManager;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@TargetApi(24)
@RequiresApi(24)
@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.os.UserManagerCompatApi24 */
public class UserManagerCompatApi24 {
    public UserManagerCompatApi24() {
    }

    public static boolean isUserUnlocked(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return ((UserManager) context2.getSystemService(UserManager.class)).isUserUnlocked();
    }
}
