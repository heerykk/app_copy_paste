package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v7.app.AppCompatDelegateImplV11 */
class AppCompatDelegateImplV11 extends AppCompatDelegateImplV9 {
    AppCompatDelegateImplV11(Context context, Window window, AppCompatCallback appCompatCallback) {
        Context context2 = context;
        Window window2 = window;
        AppCompatCallback callback = appCompatCallback;
        Context context3 = context2;
        Window window3 = window2;
        AppCompatCallback appCompatCallback2 = callback;
        super(context2, window2, callback);
    }

    public boolean hasWindowFeature(int i) {
        int featureId = i;
        int i2 = featureId;
        return super.hasWindowFeature(featureId) || this.mWindow.hasFeature(featureId);
    }

    /* access modifiers changed from: 0000 */
    public View callActivityOnCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View view2 = view;
        String str2 = str;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        return null;
    }
}
