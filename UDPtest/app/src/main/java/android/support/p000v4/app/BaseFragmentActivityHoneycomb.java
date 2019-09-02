package android.support.p000v4.app;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;

/* renamed from: android.support.v4.app.BaseFragmentActivityHoneycomb */
abstract class BaseFragmentActivityHoneycomb extends BaseFragmentActivityGingerbread {
    BaseFragmentActivityHoneycomb() {
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View parent = view;
        String name = str;
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        View view2 = parent;
        String str2 = name;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        View dispatchFragmentsOnCreateView = dispatchFragmentsOnCreateView(parent, name, context2, attrs);
        View v = dispatchFragmentsOnCreateView;
        if (dispatchFragmentsOnCreateView == null && VERSION.SDK_INT >= 11) {
            return super.onCreateView(parent, name, context2, attrs);
        }
        return v;
    }
}
