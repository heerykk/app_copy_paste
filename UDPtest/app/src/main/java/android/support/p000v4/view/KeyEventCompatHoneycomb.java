package android.support.p000v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;

@TargetApi(11)
@RequiresApi(11)
/* renamed from: android.support.v4.view.KeyEventCompatHoneycomb */
class KeyEventCompatHoneycomb {
    KeyEventCompatHoneycomb() {
    }

    public static int normalizeMetaState(int i) {
        int metaState = i;
        int i2 = metaState;
        return KeyEvent.normalizeMetaState(metaState);
    }

    public static boolean metaStateHasModifiers(int i, int i2) {
        int metaState = i;
        int modifiers = i2;
        int i3 = metaState;
        int i4 = modifiers;
        return KeyEvent.metaStateHasModifiers(metaState, modifiers);
    }

    public static boolean metaStateHasNoModifiers(int i) {
        int metaState = i;
        int i2 = metaState;
        return KeyEvent.metaStateHasNoModifiers(metaState);
    }

    public static boolean isCtrlPressed(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return event.isCtrlPressed();
    }
}
