package android.support.p003v7.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.Window;
import android.view.Window.Callback;
import java.util.List;

@TargetApi(24)
@RequiresApi(24)
/* renamed from: android.support.v7.app.AppCompatDelegateImplN */
class AppCompatDelegateImplN extends AppCompatDelegateImplV23 {

    /* renamed from: android.support.v7.app.AppCompatDelegateImplN$AppCompatWindowCallbackN */
    class AppCompatWindowCallbackN extends AppCompatWindowCallbackV23 {
        final /* synthetic */ AppCompatDelegateImplN this$0;

        AppCompatWindowCallbackN(AppCompatDelegateImplN appCompatDelegateImplN, Callback callback) {
            AppCompatDelegateImplN this$02 = appCompatDelegateImplN;
            Callback callback2 = callback;
            AppCompatDelegateImplN appCompatDelegateImplN2 = this$02;
            Callback callback3 = callback2;
            this.this$0 = this$02;
            super(this$02, callback2);
        }

        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            List<KeyboardShortcutGroup> data = list;
            Menu menu2 = menu;
            int deviceId = i;
            List<KeyboardShortcutGroup> list2 = data;
            Menu menu3 = menu2;
            int i2 = deviceId;
            PanelFeatureState panelState = this.this$0.getPanelState(0, true);
            PanelFeatureState panel = panelState;
            if (panelState == null || panel.menu == null) {
                super.onProvideKeyboardShortcuts(data, menu2, deviceId);
                return;
            }
            super.onProvideKeyboardShortcuts(data, panel.menu, deviceId);
        }
    }

    AppCompatDelegateImplN(Context context, Window window, AppCompatCallback appCompatCallback) {
        Context context2 = context;
        Window window2 = window;
        AppCompatCallback callback = appCompatCallback;
        Context context3 = context2;
        Window window3 = window2;
        AppCompatCallback appCompatCallback2 = callback;
        super(context2, window2, callback);
    }

    /* access modifiers changed from: 0000 */
    public Callback wrapWindowCallback(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        return new AppCompatWindowCallbackN(this, callback2);
    }
}
