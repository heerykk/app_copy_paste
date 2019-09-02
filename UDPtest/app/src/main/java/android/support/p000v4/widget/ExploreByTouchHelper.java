package android.support.p000v4.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.media.TransportMediator;
import android.support.p000v4.util.SparseArrayCompat;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.KeyEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewParentCompat;
import android.support.p000v4.view.accessibility.AccessibilityEventCompat;
import android.support.p000v4.view.accessibility.AccessibilityManagerCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.support.p000v4.view.accessibility.AccessibilityRecordCompat;
import android.support.p000v4.widget.FocusStrategy.BoundsAdapter;
import android.support.p000v4.widget.FocusStrategy.CollectionAdapter;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.widget.ExploreByTouchHelper */
public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    private static final String DEFAULT_CLASS_NAME = "android.view.View";
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final BoundsAdapter<AccessibilityNodeInfoCompat> NODE_ADAPTER = new BoundsAdapter<AccessibilityNodeInfoCompat>() {
        public /* bridge */ /* synthetic */ void obtainBounds(Object obj, Rect rect) {
            obtainBounds((AccessibilityNodeInfoCompat) obj, rect);
        }

        public void obtainBounds(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, Rect rect) {
            AccessibilityNodeInfoCompat node = accessibilityNodeInfoCompat;
            Rect outBounds = rect;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = node;
            Rect rect2 = outBounds;
            node.getBoundsInParent(outBounds);
        }
    };
    private static final CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> SPARSE_VALUES_ADAPTER = new CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat>() {
        public /* bridge */ /* synthetic */ Object get(Object obj, int i) {
            return get((SparseArrayCompat) obj, i);
        }

        public /* bridge */ /* synthetic */ int size(Object obj) {
            return size((SparseArrayCompat) obj);
        }

        public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat, int i) {
            SparseArrayCompat<AccessibilityNodeInfoCompat> collection = sparseArrayCompat;
            int index = i;
            SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat2 = collection;
            int i2 = index;
            return (AccessibilityNodeInfoCompat) collection.valueAt(index);
        }

        public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat) {
            SparseArrayCompat<AccessibilityNodeInfoCompat> collection = sparseArrayCompat;
            SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat2 = collection;
            return collection.size();
        }
    };
    private int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
    private final View mHost;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;
    private int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
    private final AccessibilityManager mManager;
    private MyNodeProvider mNodeProvider;
    private final int[] mTempGlobalRect = new int[2];
    private final Rect mTempParentRect = new Rect();
    private final Rect mTempScreenRect = new Rect();
    private final Rect mTempVisibleRect = new Rect();

    /* renamed from: android.support.v4.widget.ExploreByTouchHelper$MyNodeProvider */
    private class MyNodeProvider extends AccessibilityNodeProviderCompat {
        final /* synthetic */ ExploreByTouchHelper this$0;

        MyNodeProvider(ExploreByTouchHelper exploreByTouchHelper) {
            this.this$0 = exploreByTouchHelper;
        }

        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
            int virtualViewId = i;
            int i2 = virtualViewId;
            AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo = this.this$0.obtainAccessibilityNodeInfo(virtualViewId);
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = obtainAccessibilityNodeInfo;
            return AccessibilityNodeInfoCompat.obtain(obtainAccessibilityNodeInfo);
        }

        public boolean performAction(int i, int i2, Bundle bundle) {
            int virtualViewId = i;
            int action = i2;
            Bundle arguments = bundle;
            int i3 = virtualViewId;
            int i4 = action;
            Bundle bundle2 = arguments;
            return this.this$0.performAction(virtualViewId, action, arguments);
        }

        public AccessibilityNodeInfoCompat findFocus(int i) {
            int focusType = i;
            int i2 = focusType;
            int focusedId = focusType != 2 ? ExploreByTouchHelper.access$100(this.this$0) : ExploreByTouchHelper.access$000(this.this$0);
            if (focusedId != Integer.MIN_VALUE) {
                return createAccessibilityNodeInfo(focusedId);
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public abstract int getVirtualViewAt(float f, float f2);

    /* access modifiers changed from: protected */
    public abstract void getVisibleVirtualViews(List<Integer> list);

    /* access modifiers changed from: protected */
    public abstract boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    static /* synthetic */ int access$000(ExploreByTouchHelper exploreByTouchHelper) {
        ExploreByTouchHelper x0 = exploreByTouchHelper;
        ExploreByTouchHelper exploreByTouchHelper2 = x0;
        return x0.mAccessibilityFocusedVirtualViewId;
    }

    static /* synthetic */ int access$100(ExploreByTouchHelper exploreByTouchHelper) {
        ExploreByTouchHelper x0 = exploreByTouchHelper;
        ExploreByTouchHelper exploreByTouchHelper2 = x0;
        return x0.mKeyboardFocusedVirtualViewId;
    }

    public ExploreByTouchHelper(View view) {
        View host = view;
        View view2 = host;
        if (host != null) {
            this.mHost = host;
            this.mManager = (AccessibilityManager) host.getContext().getSystemService("accessibility");
            host.setFocusable(true);
            if (ViewCompat.getImportantForAccessibility(host) == 0) {
                ViewCompat.setImportantForAccessibility(host, 1);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("View may not be null");
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        View view2 = view;
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new MyNodeProvider(this);
        }
        return this.mNodeProvider;
    }

    public final boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        boolean z;
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        if (!this.mManager.isEnabled() || !AccessibilityManagerCompat.isTouchExplorationEnabled(this.mManager)) {
            return false;
        }
        switch (event.getAction()) {
            case 7:
            case 9:
                int virtualViewAt = getVirtualViewAt(event.getX(), event.getY());
                int i = virtualViewAt;
                updateHoveredVirtualView(virtualViewAt);
                if (virtualViewAt == Integer.MIN_VALUE) {
                    z = false;
                } else {
                    z = true;
                }
                return z;
            case 10:
                if (this.mAccessibilityFocusedVirtualViewId == Integer.MIN_VALUE) {
                    return false;
                }
                updateHoveredVirtualView(Integer.MIN_VALUE);
                return true;
            default:
                return false;
        }
    }

    public final boolean dispatchKeyEvent(@NonNull KeyEvent keyEvent) {
        KeyEvent event = keyEvent;
        KeyEvent keyEvent2 = event;
        boolean handled = false;
        int action = event.getAction();
        int i = action;
        if (action != 1) {
            int keyCode = event.getKeyCode();
            int keyCode2 = keyCode;
            switch (keyCode) {
                case 19:
                case 20:
                case 21:
                case 22:
                    if (KeyEventCompat.hasNoModifiers(event)) {
                        int direction = keyToDirection(keyCode2);
                        int count = 1 + event.getRepeatCount();
                        for (int i2 = 0; i2 < count && moveFocus(direction, null); i2++) {
                            handled = true;
                        }
                    }
                    break;
                case 23:
                case 66:
                    if (KeyEventCompat.hasNoModifiers(event) && event.getRepeatCount() == 0) {
                        boolean clickKeyboardFocusedVirtualView = clickKeyboardFocusedVirtualView();
                        handled = true;
                        break;
                    }
                case 61:
                    if (!KeyEventCompat.hasNoModifiers(event)) {
                        if (KeyEventCompat.hasModifiers(event, 1)) {
                            handled = moveFocus(1, null);
                            break;
                        }
                    } else {
                        handled = moveFocus(2, null);
                        break;
                    }
                    break;
            }
        }
        return handled;
    }

    public final void onFocusChanged(boolean z, int i, @Nullable Rect rect) {
        int direction = i;
        Rect previouslyFocusedRect = rect;
        boolean gainFocus = z;
        int i2 = direction;
        Rect rect2 = previouslyFocusedRect;
        if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
            boolean clearKeyboardFocusForVirtualView = clearKeyboardFocusForVirtualView(this.mKeyboardFocusedVirtualViewId);
        }
        if (gainFocus) {
            boolean moveFocus = moveFocus(direction, previouslyFocusedRect);
        }
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.mAccessibilityFocusedVirtualViewId;
    }

    public final int getKeyboardFocusedVirtualViewId() {
        return this.mKeyboardFocusedVirtualViewId;
    }

    private static int keyToDirection(int i) {
        int keyCode = i;
        int i2 = keyCode;
        switch (keyCode) {
            case 19:
                return 33;
            case 21:
                return 17;
            case 22:
                return 66;
            default:
                return TransportMediator.KEYCODE_MEDIA_RECORD;
        }
    }

    private void getBoundsInParent(int i, Rect rect) {
        int virtualViewId = i;
        Rect outBounds = rect;
        int i2 = virtualViewId;
        Rect rect2 = outBounds;
        AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo = obtainAccessibilityNodeInfo(virtualViewId);
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = obtainAccessibilityNodeInfo;
        obtainAccessibilityNodeInfo.getBoundsInParent(outBounds);
    }

    private boolean moveFocus(int i, @Nullable Rect rect) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat;
        AccessibilityNodeInfoCompat nextFocusedNode;
        int nextFocusedNodeId;
        int direction = i;
        Rect previouslyFocusedRect = rect;
        int i2 = direction;
        Rect rect2 = previouslyFocusedRect;
        SparseArrayCompat allNodes = getAllNodes();
        int i3 = this.mKeyboardFocusedVirtualViewId;
        int focusedNodeId = i3;
        if (i3 != Integer.MIN_VALUE) {
            accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) allNodes.get(focusedNodeId);
        } else {
            accessibilityNodeInfoCompat = null;
        }
        AccessibilityNodeInfoCompat focusedNode = accessibilityNodeInfoCompat;
        switch (direction) {
            case 1:
            case 2:
                nextFocusedNode = (AccessibilityNodeInfoCompat) FocusStrategy.findNextFocusInRelativeDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, focusedNode, direction, ViewCompat.getLayoutDirection(this.mHost) == 1, false);
                break;
            case 17:
            case 33:
            case 66:
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                Rect selectedRect = new Rect();
                if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
                    getBoundsInParent(this.mKeyboardFocusedVirtualViewId, selectedRect);
                } else if (previouslyFocusedRect == null) {
                    Rect guessPreviouslyFocusedRect = guessPreviouslyFocusedRect(this.mHost, direction, selectedRect);
                } else {
                    selectedRect.set(previouslyFocusedRect);
                }
                nextFocusedNode = (AccessibilityNodeInfoCompat) FocusStrategy.findNextFocusInAbsoluteDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, focusedNode, selectedRect, direction);
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        if (nextFocusedNode != null) {
            int indexOfValue = allNodes.indexOfValue(nextFocusedNode);
            int i4 = indexOfValue;
            nextFocusedNodeId = allNodes.keyAt(indexOfValue);
        } else {
            nextFocusedNodeId = Integer.MIN_VALUE;
        }
        return requestKeyboardFocusForVirtualView(nextFocusedNodeId);
    }

    private SparseArrayCompat<AccessibilityNodeInfoCompat> getAllNodes() {
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        SparseArrayCompat sparseArrayCompat = new SparseArrayCompat();
        for (int virtualViewId = 0; virtualViewId < arrayList.size(); virtualViewId++) {
            sparseArrayCompat.put(virtualViewId, createNodeForChild(virtualViewId));
        }
        return sparseArrayCompat;
    }

    private static Rect guessPreviouslyFocusedRect(@NonNull View view, int i, @NonNull Rect rect) {
        View host = view;
        int direction = i;
        Rect outBounds = rect;
        View view2 = host;
        int i2 = direction;
        Rect rect2 = outBounds;
        int w = host.getWidth();
        int h = host.getHeight();
        switch (direction) {
            case 17:
                outBounds.set(w, 0, w, h);
                break;
            case 33:
                outBounds.set(0, h, w, h);
                break;
            case 66:
                outBounds.set(-1, 0, -1, h);
                break;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                outBounds.set(0, -1, w, -1);
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return outBounds;
    }

    private boolean clickKeyboardFocusedVirtualView() {
        return this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE && onPerformActionForVirtualView(this.mKeyboardFocusedVirtualViewId, 16, null);
    }

    public final boolean sendEventForVirtualView(int i, int i2) {
        int virtualViewId = i;
        int eventType = i2;
        int i3 = virtualViewId;
        int i4 = eventType;
        if (virtualViewId == Integer.MIN_VALUE || !this.mManager.isEnabled()) {
            return false;
        }
        ViewParent parent = this.mHost.getParent();
        ViewParent parent2 = parent;
        if (parent == null) {
            return false;
        }
        return ViewParentCompat.requestSendAccessibilityEvent(parent2, this.mHost, createEvent(virtualViewId, eventType));
    }

    public final void invalidateRoot() {
        invalidateVirtualView(-1, 1);
    }

    public final void invalidateVirtualView(int i) {
        int virtualViewId = i;
        int i2 = virtualViewId;
        invalidateVirtualView(virtualViewId, 0);
    }

    public final void invalidateVirtualView(int i, int i2) {
        int virtualViewId = i;
        int changeTypes = i2;
        int i3 = virtualViewId;
        int i4 = changeTypes;
        if (virtualViewId != Integer.MIN_VALUE && this.mManager.isEnabled()) {
            ViewParent parent = this.mHost.getParent();
            ViewParent parent2 = parent;
            if (parent != null) {
                AccessibilityEvent createEvent = createEvent(virtualViewId, 2048);
                AccessibilityEvent event = createEvent;
                AccessibilityEventCompat.setContentChangeTypes(createEvent, changeTypes);
                boolean requestSendAccessibilityEvent = ViewParentCompat.requestSendAccessibilityEvent(parent2, this.mHost, event);
            }
        }
    }

    @Deprecated
    public int getFocusedVirtualView() {
        return getAccessibilityFocusedVirtualViewId();
    }

    /* access modifiers changed from: protected */
    public void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
        int i2 = i;
        boolean z2 = z;
    }

    private void updateHoveredVirtualView(int i) {
        int virtualViewId = i;
        int i2 = virtualViewId;
        if (this.mHoveredVirtualViewId != virtualViewId) {
            int previousVirtualViewId = this.mHoveredVirtualViewId;
            this.mHoveredVirtualViewId = virtualViewId;
            boolean sendEventForVirtualView = sendEventForVirtualView(virtualViewId, 128);
            boolean sendEventForVirtualView2 = sendEventForVirtualView(previousVirtualViewId, 256);
        }
    }

    private AccessibilityEvent createEvent(int i, int i2) {
        int virtualViewId = i;
        int eventType = i2;
        int i3 = virtualViewId;
        int i4 = eventType;
        switch (virtualViewId) {
            case -1:
                return createEventForHost(eventType);
            default:
                return createEventForChild(virtualViewId, eventType);
        }
    }

    private AccessibilityEvent createEventForHost(int i) {
        int eventType = i;
        int i2 = eventType;
        AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        ViewCompat.onInitializeAccessibilityEvent(this.mHost, event);
        return event;
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        super.onInitializeAccessibilityEvent(host, event);
        onPopulateEventForHost(event);
    }

    private AccessibilityEvent createEventForChild(int i, int i2) {
        int virtualViewId = i;
        int eventType = i2;
        int i3 = virtualViewId;
        int i4 = eventType;
        AccessibilityEvent obtain = AccessibilityEvent.obtain(eventType);
        AccessibilityEvent event = obtain;
        AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(obtain);
        AccessibilityNodeInfoCompat node = obtainAccessibilityNodeInfo(virtualViewId);
        boolean add = record.getText().add(node.getText());
        record.setContentDescription(node.getContentDescription());
        record.setScrollable(node.isScrollable());
        record.setPassword(node.isPassword());
        record.setEnabled(node.isEnabled());
        record.setChecked(node.isChecked());
        onPopulateEventForVirtualView(virtualViewId, event);
        if (event.getText().isEmpty() && event.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        record.setClassName(node.getClassName());
        record.setSource(this.mHost, virtualViewId);
        event.setPackageName(this.mHost.getContext().getPackageName());
        return event;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int i) {
        int virtualViewId = i;
        int i2 = virtualViewId;
        if (virtualViewId != -1) {
            return createNodeForChild(virtualViewId);
        }
        return createNodeForHost();
    }

    @NonNull
    private AccessibilityNodeInfoCompat createNodeForHost() {
        AccessibilityNodeInfoCompat info = AccessibilityNodeInfoCompat.obtain(this.mHost);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.mHost, info);
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        int childCount = info.getChildCount();
        int i = childCount;
        if (childCount > 0 && arrayList.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int count = arrayList.size();
        for (int i2 = 0; i2 < count; i2++) {
            info.addChild(this.mHost, ((Integer) arrayList.get(i2)).intValue());
        }
        return info;
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        View host = view;
        AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
        View view2 = host;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
        super.onInitializeAccessibilityNodeInfo(host, info);
        onPopulateNodeForHost(info);
    }

    @NonNull
    private AccessibilityNodeInfoCompat createNodeForChild(int i) {
        int virtualViewId = i;
        int i2 = virtualViewId;
        AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain();
        AccessibilityNodeInfoCompat node = obtain;
        obtain.setEnabled(true);
        node.setFocusable(true);
        node.setClassName(DEFAULT_CLASS_NAME);
        node.setBoundsInParent(INVALID_PARENT_BOUNDS);
        node.setBoundsInScreen(INVALID_PARENT_BOUNDS);
        node.setParent(this.mHost);
        onPopulateNodeForVirtualView(virtualViewId, node);
        if (node.getText() == null && node.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        node.getBoundsInParent(this.mTempParentRect);
        if (!this.mTempParentRect.equals(INVALID_PARENT_BOUNDS)) {
            int actions = node.getActions();
            int actions2 = actions;
            if ((actions & 64) != 0) {
                throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            } else if ((actions2 & 128) == 0) {
                node.setPackageName(this.mHost.getContext().getPackageName());
                node.setSource(this.mHost, virtualViewId);
                if (this.mAccessibilityFocusedVirtualViewId != virtualViewId) {
                    node.setAccessibilityFocused(false);
                    node.addAction(64);
                } else {
                    node.setAccessibilityFocused(true);
                    node.addAction(128);
                }
                boolean isFocused = this.mKeyboardFocusedVirtualViewId == virtualViewId;
                if (isFocused) {
                    node.addAction(2);
                } else if (node.isFocusable()) {
                    node.addAction(1);
                }
                node.setFocused(isFocused);
                this.mHost.getLocationOnScreen(this.mTempGlobalRect);
                node.getBoundsInScreen(this.mTempScreenRect);
                if (this.mTempScreenRect.equals(INVALID_PARENT_BOUNDS)) {
                    node.getBoundsInParent(this.mTempScreenRect);
                    if (node.mParentVirtualDescendantId != -1) {
                        AccessibilityNodeInfoCompat parentNode = AccessibilityNodeInfoCompat.obtain();
                        int i3 = node.mParentVirtualDescendantId;
                        while (true) {
                            int virtualDescendantId = i3;
                            if (virtualDescendantId == -1) {
                                break;
                            }
                            parentNode.setParent(this.mHost, -1);
                            parentNode.setBoundsInParent(INVALID_PARENT_BOUNDS);
                            onPopulateNodeForVirtualView(virtualDescendantId, parentNode);
                            parentNode.getBoundsInParent(this.mTempParentRect);
                            this.mTempScreenRect.offset(this.mTempParentRect.left, this.mTempParentRect.top);
                            i3 = parentNode.mParentVirtualDescendantId;
                        }
                        parentNode.recycle();
                    }
                    this.mTempScreenRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
                }
                if (this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) {
                    this.mTempVisibleRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
                    boolean intersect = this.mTempScreenRect.intersect(this.mTempVisibleRect);
                    node.setBoundsInScreen(this.mTempScreenRect);
                    if (isVisibleToUser(this.mTempScreenRect)) {
                        node.setVisibleToUser(true);
                    }
                }
                return node;
            } else {
                throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            }
        } else {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean performAction(int i, int i2, Bundle bundle) {
        int virtualViewId = i;
        int action = i2;
        Bundle arguments = bundle;
        int i3 = virtualViewId;
        int i4 = action;
        Bundle bundle2 = arguments;
        switch (virtualViewId) {
            case -1:
                return performActionForHost(action, arguments);
            default:
                return performActionForChild(virtualViewId, action, arguments);
        }
    }

    private boolean performActionForHost(int i, Bundle bundle) {
        int action = i;
        Bundle arguments = bundle;
        int i2 = action;
        Bundle bundle2 = arguments;
        return ViewCompat.performAccessibilityAction(this.mHost, action, arguments);
    }

    private boolean performActionForChild(int i, int i2, Bundle bundle) {
        int virtualViewId = i;
        int action = i2;
        Bundle arguments = bundle;
        int i3 = virtualViewId;
        int i4 = action;
        Bundle bundle2 = arguments;
        switch (action) {
            case 1:
                return requestKeyboardFocusForVirtualView(virtualViewId);
            case 2:
                return clearKeyboardFocusForVirtualView(virtualViewId);
            case 64:
                return requestAccessibilityFocus(virtualViewId);
            case 128:
                return clearAccessibilityFocus(virtualViewId);
            default:
                return onPerformActionForVirtualView(virtualViewId, action, arguments);
        }
    }

    private boolean isVisibleToUser(Rect rect) {
        boolean z;
        Rect localRect = rect;
        Rect rect2 = localRect;
        if (localRect == null || localRect.isEmpty()) {
            return false;
        }
        if (this.mHost.getWindowVisibility() != 0) {
            return false;
        }
        ViewParent parent = this.mHost.getParent();
        while (true) {
            ViewParent viewParent = parent;
            if (!(viewParent instanceof View)) {
                if (viewParent == null) {
                    z = false;
                } else {
                    z = true;
                }
                return z;
            }
            View view = (View) viewParent;
            View view2 = view;
            if (!(ViewCompat.getAlpha(view) <= 0.0f) && view2.getVisibility() == 0) {
                parent = view2.getParent();
            }
        }
        return false;
    }

    private boolean requestAccessibilityFocus(int i) {
        int virtualViewId = i;
        int i2 = virtualViewId;
        if (!this.mManager.isEnabled() || !AccessibilityManagerCompat.isTouchExplorationEnabled(this.mManager)) {
            return false;
        }
        if (this.mAccessibilityFocusedVirtualViewId == virtualViewId) {
            return false;
        }
        if (this.mAccessibilityFocusedVirtualViewId != Integer.MIN_VALUE) {
            boolean clearAccessibilityFocus = clearAccessibilityFocus(this.mAccessibilityFocusedVirtualViewId);
        }
        this.mAccessibilityFocusedVirtualViewId = virtualViewId;
        this.mHost.invalidate();
        boolean sendEventForVirtualView = sendEventForVirtualView(virtualViewId, 32768);
        return true;
    }

    private boolean clearAccessibilityFocus(int i) {
        int virtualViewId = i;
        int i2 = virtualViewId;
        if (this.mAccessibilityFocusedVirtualViewId != virtualViewId) {
            return false;
        }
        this.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mHost.invalidate();
        boolean sendEventForVirtualView = sendEventForVirtualView(virtualViewId, 65536);
        return true;
    }

    public final boolean requestKeyboardFocusForVirtualView(int i) {
        int virtualViewId = i;
        int i2 = virtualViewId;
        if (!this.mHost.isFocused() && !this.mHost.requestFocus()) {
            return false;
        }
        if (this.mKeyboardFocusedVirtualViewId == virtualViewId) {
            return false;
        }
        if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
            boolean clearKeyboardFocusForVirtualView = clearKeyboardFocusForVirtualView(this.mKeyboardFocusedVirtualViewId);
        }
        this.mKeyboardFocusedVirtualViewId = virtualViewId;
        onVirtualViewKeyboardFocusChanged(virtualViewId, true);
        boolean sendEventForVirtualView = sendEventForVirtualView(virtualViewId, 8);
        return true;
    }

    public final boolean clearKeyboardFocusForVirtualView(int i) {
        int virtualViewId = i;
        int i2 = virtualViewId;
        if (this.mKeyboardFocusedVirtualViewId != virtualViewId) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(virtualViewId, false);
        boolean sendEventForVirtualView = sendEventForVirtualView(virtualViewId, 8);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
        int i2 = i;
        AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
    }

    /* access modifiers changed from: protected */
    public void onPopulateEventForHost(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent accessibilityEvent2 = accessibilityEvent;
    }

    /* access modifiers changed from: protected */
    public void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = accessibilityNodeInfoCompat;
    }
}
