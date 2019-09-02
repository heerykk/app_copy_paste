package android.support.p000v4.util;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.util.DebugUtils */
public class DebugUtils {
    public DebugUtils() {
    }

    public static void buildShortClassTag(Object obj, StringBuilder sb) {
        Object cls = obj;
        StringBuilder out = sb;
        Object obj2 = cls;
        StringBuilder sb2 = out;
        if (cls != null) {
            String simpleName = cls.getClass().getSimpleName();
            String simpleName2 = simpleName;
            if (simpleName == null || simpleName2.length() <= 0) {
                String name = cls.getClass().getName();
                simpleName2 = name;
                int lastIndexOf = name.lastIndexOf(46);
                int end = lastIndexOf;
                if (lastIndexOf > 0) {
                    simpleName2 = simpleName2.substring(end + 1);
                }
            }
            StringBuilder append = out.append(simpleName2);
            StringBuilder append2 = out.append('{');
            StringBuilder append3 = out.append(Integer.toHexString(System.identityHashCode(cls)));
            return;
        }
        StringBuilder append4 = out.append("null");
    }
}
