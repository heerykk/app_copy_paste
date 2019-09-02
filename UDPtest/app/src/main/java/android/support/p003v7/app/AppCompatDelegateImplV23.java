package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.app.UiModeManager;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.ActionMode;
import android.view.Window;
import android.view.Window.Callback;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v7.app.AppCompatDelegateImplV23 */
class AppCompatDelegateImplV23 extends AppCompatDelegateImplV14 {
    private final UiModeManager mUiModeManager;

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV23$AppCompatWindowCallbackV23 */
    class AppCompatWindowCallbackV23 extends AppCompatWindowCallbackV14 {
        final /* synthetic */ AppCompatDelegateImplV23 this$0;

        AppCompatWindowCallbackV23(AppCompatDelegateImplV23 appCompatDelegateImplV23, Callback callback) {
            AppCompatDelegateImplV23 this$02 = appCompatDelegateImplV23;
            Callback callback2 = callback;
            AppCompatDelegateImplV23 appCompatDelegateImplV232 = this$02;
            Callback callback3 = callback2;
            this.this$0 = this$02;
            super(this$02, callback2);
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            ActionMode.Callback callback2 = callback;
            int type = i;
            ActionMode.Callback callback3 = callback2;
            int i2 = type;
            if (this.this$0.isHandleNativeActionModesEnabled()) {
                switch (type) {
                    case 0:
                        return startAsSupportActionMode(callback2);
                }
            }
            return super.onWindowStartingActionMode(callback2, type);
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            ActionMode.Callback callback2 = callback;
            return null;
        }
    }

    AppCompatDelegateImplV23(Context context, Window window, AppCompatCallback appCompatCallback) {
        Context context2 = context;
        Window window2 = window;
        AppCompatCallback callback = appCompatCallback;
        Context context3 = context2;
        Window window3 = window2;
        AppCompatCallback appCompatCallback2 = callback;
        super(context2, window2, callback);
        this.mUiModeManager = (UiModeManager) context2.getSystemService("uimode");
    }

    /* access modifiers changed from: 0000 */
    public Callback wrapWindowCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new AppCompatWindowCallbackV23(this, callback2);
    }

    /* access modifiers changed from: 0000 */
    public int mapNightMode(int i) {
        int mode = i;
        int i2 = mode;
        if (mode == 0 && this.mUiModeManager.getNightMode() == 0) {
            return -1;
        }
        return super.mapNightMode(mode);
    }
}
