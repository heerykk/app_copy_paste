package android.support.p003v7.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window.Callback;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.WindowCallbackWrapper */
public class WindowCallbackWrapper implements Callback {
    final Callback mWrapped;

    public WindowCallbackWrapper(Callback callback) {
        Callback wrapped = callback;
        Callback callback2 = wrapped;
        if (wrapped != null) {
            this.mWrapped = wrapped;
            return;
        }
        throw new IllegalArgumentException("Window callback may not be null");
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return this.mWrapped.dispatchKeyEvent(event);
    }

    @TargetApi(11)
    @RequiresApi(11)
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return this.mWrapped.dispatchKeyShortcutEvent(event);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return this.mWrapped.dispatchTouchEvent(event);
    }

    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return this.mWrapped.dispatchTrackballEvent(event);
    }

    @TargetApi(12)
    @RequiresApi(12)
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        return this.mWrapped.dispatchGenericMotionEvent(event);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent event = accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = event;
        return this.mWrapped.dispatchPopulateAccessibilityEvent(event);
    }

    public View onCreatePanelView(int i) {
        int featureId = i;
        int i2 = featureId;
        return this.mWrapped.onCreatePanelView(featureId);
    }

    public boolean onCreatePanelMenu(int i, Menu menu) {
        int featureId = i;
        Menu menu2 = menu;
        int i2 = featureId;
        Menu menu3 = menu2;
        return this.mWrapped.onCreatePanelMenu(featureId, menu2);
    }

    public boolean onPreparePanel(int i, View view, Menu menu) {
        int featureId = i;
        View view2 = view;
        Menu menu2 = menu;
        int i2 = featureId;
        View view3 = view2;
        Menu menu3 = menu2;
        return this.mWrapped.onPreparePanel(featureId, view2, menu2);
    }

    public boolean onMenuOpened(int i, Menu menu) {
        int featureId = i;
        Menu menu2 = menu;
        int i2 = featureId;
        Menu menu3 = menu2;
        return this.mWrapped.onMenuOpened(featureId, menu2);
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        int featureId = i;
        MenuItem item = menuItem;
        int i2 = featureId;
        MenuItem menuItem2 = item;
        return this.mWrapped.onMenuItemSelected(featureId, item);
    }

    public void onWindowAttributesChanged(LayoutParams layoutParams) {
        LayoutParams attrs = layoutParams;
        LayoutParams layoutParams2 = attrs;
        this.mWrapped.onWindowAttributesChanged(attrs);
    }

    public void onContentChanged() {
        this.mWrapped.onContentChanged();
    }

    public void onWindowFocusChanged(boolean z) {
        this.mWrapped.onWindowFocusChanged(z);
    }

    public void onAttachedToWindow() {
        this.mWrapped.onAttachedToWindow();
    }

    public void onDetachedFromWindow() {
        this.mWrapped.onDetachedFromWindow();
    }

    public void onPanelClosed(int i, Menu menu) {
        int featureId = i;
        Menu menu2 = menu;
        int i2 = featureId;
        Menu menu3 = menu2;
        this.mWrapped.onPanelClosed(featureId, menu2);
    }

    @TargetApi(23)
    @RequiresApi(23)
    public boolean onSearchRequested(SearchEvent searchEvent) {
        SearchEvent searchEvent2 = searchEvent;
        SearchEvent searchEvent3 = searchEvent2;
        return this.mWrapped.onSearchRequested(searchEvent2);
    }

    public boolean onSearchRequested() {
        return this.mWrapped.onSearchRequested();
    }

    @TargetApi(11)
    @RequiresApi(11)
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        ActionMode.Callback callback2 = callback;
        ActionMode.Callback callback3 = callback2;
        return this.mWrapped.onWindowStartingActionMode(callback2);
    }

    @TargetApi(23)
    @RequiresApi(23)
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        ActionMode.Callback callback2 = callback;
        int type = i;
        ActionMode.Callback callback3 = callback2;
        int i2 = type;
        return this.mWrapped.onWindowStartingActionMode(callback2, type);
    }

    @TargetApi(11)
    @RequiresApi(11)
    public void onActionModeStarted(ActionMode actionMode) {
        ActionMode mode = actionMode;
        ActionMode actionMode2 = mode;
        this.mWrapped.onActionModeStarted(mode);
    }

    @TargetApi(11)
    @RequiresApi(11)
    public void onActionModeFinished(ActionMode actionMode) {
        ActionMode mode = actionMode;
        ActionMode actionMode2 = mode;
        this.mWrapped.onActionModeFinished(mode);
    }

    @TargetApi(24)
    @RequiresApi(24)
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
        List<KeyboardShortcutGroup> data = list;
        Menu menu2 = menu;
        int deviceId = i;
        List<KeyboardShortcutGroup> list2 = data;
        Menu menu3 = menu2;
        int i2 = deviceId;
        this.mWrapped.onProvideKeyboardShortcuts(data, menu2, deviceId);
    }
}
