package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.KeyEvent.DispatcherState;
import android.view.View;

/* renamed from: android.support.v4.view.KeyEventCompat */
public final class KeyEventCompat {
    static final KeyEventVersionImpl IMPL;

    /* renamed from: android.support.v4.view.KeyEventCompat$BaseKeyEventVersionImpl */
    static class BaseKeyEventVersionImpl implements KeyEventVersionImpl {
        private static final int META_ALL_MASK = 247;
        private static final int META_MODIFIER_MASK = 247;

        BaseKeyEventVersionImpl() {
        }

        private static int metaStateFilterDirectionalModifiers(int i, int i2, int i3, int i4, int i5) {
            int metaState = i;
            int modifiers = i2;
            int basic = i3;
            int left = i4;
            int right = i5;
            int i6 = metaState;
            int i7 = modifiers;
            int i8 = basic;
            int i9 = left;
            int i10 = right;
            boolean wantBasic = (modifiers & basic) != 0;
            int directional = left | right;
            boolean wantLeftOrRight = (modifiers & directional) != 0;
            if (!wantBasic) {
                if (!wantLeftOrRight) {
                    return metaState;
                }
                return metaState & (basic ^ -1);
            } else if (!wantLeftOrRight) {
                return metaState & (directional ^ -1);
            } else {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("bad arguments");
                throw illegalArgumentException;
            }
        }

        public int normalizeMetaState(int i) {
            int metaState = i;
            int metaState2 = metaState;
            if ((metaState & 192) != 0) {
                metaState2 = metaState | 1;
            }
            if ((metaState2 & 48) != 0) {
                metaState2 |= 2;
            }
            return metaState2 & 247;
        }

        public boolean metaStateHasModifiers(int i, int i2) {
            int metaState = i;
            int modifiers = i2;
            int i3 = metaState;
            int i4 = modifiers;
            int normalizeMetaState = normalizeMetaState(metaState) & 247;
            int metaState2 = normalizeMetaState;
            int metaStateFilterDirectionalModifiers = metaStateFilterDirectionalModifiers(normalizeMetaState, modifiers, 1, 64, 128);
            int metaState3 = metaStateFilterDirectionalModifiers;
            int metaStateFilterDirectionalModifiers2 = metaStateFilterDirectionalModifiers(metaStateFilterDirectionalModifiers, modifiers, 2, 16, 32);
            int metaState4 = metaStateFilterDirectionalModifiers2;
            return metaStateFilterDirectionalModifiers2 == modifiers;
        }

        public boolean metaStateHasNoModifiers(int i) {
            int metaState = i;
            int i2 = metaState;
            return (normalizeMetaState(metaState) & 247) == 0;
        }

        public boolean isCtrlPressed(KeyEvent keyEvent) {
            KeyEvent keyEvent2 = keyEvent;
            return false;
        }
    }

    /* renamed from: android.support.v4.view.KeyEventCompat$HoneycombKeyEventVersionImpl */
    static class HoneycombKeyEventVersionImpl extends BaseKeyEventVersionImpl {
        HoneycombKeyEventVersionImpl() {
        }

        public int normalizeMetaState(int i) {
            int metaState = i;
            int i2 = metaState;
            return KeyEventCompatHoneycomb.normalizeMetaState(metaState);
        }

        public boolean metaStateHasModifiers(int i, int i2) {
            int metaState = i;
            int modifiers = i2;
            int i3 = metaState;
            int i4 = modifiers;
            return KeyEventCompatHoneycomb.metaStateHasModifiers(metaState, modifiers);
        }

        public boolean metaStateHasNoModifiers(int i) {
            int metaState = i;
            int i2 = metaState;
            return KeyEventCompatHoneycomb.metaStateHasNoModifiers(metaState);
        }

        public boolean isCtrlPressed(KeyEvent keyEvent) {
            KeyEvent event = keyEvent;
            KeyEvent keyEvent2 = event;
            return KeyEventCompatHoneycomb.isCtrlPressed(event);
        }
    }

    /* renamed from: android.support.v4.view.KeyEventCompat$KeyEventVersionImpl */
    interface KeyEventVersionImpl {
        boolean isCtrlPressed(KeyEvent keyEvent);

        boolean metaStateHasModifiers(int i, int i2);

        boolean metaStateHasNoModifiers(int i);

        int normalizeMetaState(int i);
    }

    static {
        if (VERSION.SDK_INT < 11) {
            IMPL = new BaseKeyEventVersionImpl();
        } else {
            IMPL = new HoneycombKeyEventVersionImpl();
        }
    }

    public static int normalizeMetaState(int i) {
        int metaState = i;
        int i2 = metaState;
        return IMPL.normalizeMetaState(metaState);
    }

    public static boolean metaStateHasModifiers(int i, int i2) {
        int metaState = i;
        int modifiers = i2;
        int i3 = metaState;
        int i4 = modifiers;
        return IMPL.metaStateHasModifiers(metaState, modifiers);
    }

    public static boolean metaStateHasNoModifiers(int i) {
        int metaState = i;
        int i2 = metaState;
        return IMPL.metaStateHasNoModifiers(metaState);
    }

    public static boolean hasModifiers(KeyEvent keyEvent, int i) {
        KeyEvent event = keyEvent;
        int modifiers = i;
        KeyEvent keyEvent2 = event;
        int i2 = modifiers;
        return IMPL.metaStateHasModifiers(event.getMetaState(), modifiers);
    }

    public static boolean hasNoModifiers(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return IMPL.metaStateHasNoModifiers(event.getMetaState());
    }

    @Deprecated
    public static void startTracking(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        event.startTracking();
    }

    @Deprecated
    public static boolean isTracking(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return event.isTracking();
    }

    @Deprecated
    public static Object getKeyDispatcherState(View view) {
        View view2 = view;
        View view3 = view2;
        return view2.getKeyDispatcherState();
    }

    @Deprecated
    public static boolean dispatch(KeyEvent keyEvent, Callback callback, Object obj, Object obj2) {
        KeyEvent event = keyEvent;
        Callback receiver = callback;
        Object state = obj;
        Object target = obj2;
        KeyEvent keyEvent2 = event;
        Callback callback2 = receiver;
        Object obj3 = state;
        Object obj4 = target;
        return event.dispatch(receiver, (DispatcherState) state, target);
    }

    public static boolean isCtrlPressed(KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        return IMPL.isCtrlPressed(event);
    }

    private KeyEventCompat() {
    }
}
