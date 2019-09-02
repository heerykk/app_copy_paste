package android.support.p003v7.widget;

import android.os.Bundle;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v7.widget.RecyclerViewAccessibilityDelegate */
public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat {
    final AccessibilityDelegateCompat mItemDelegate = new AccessibilityDelegateCompat(this) {
        final /* synthetic */ RecyclerViewAccessibilityDelegate this$0;

        {
            RecyclerViewAccessibilityDelegate this$02 = r5;
            RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = this$02;
            this.this$0 = this$02;
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            View host = view;
            AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
            View view2 = host;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (!this.this$0.shouldIgnore() && this.this$0.mRecyclerView.getLayoutManager() != null) {
                this.this$0.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(host, info);
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            View host = view;
            int action = i;
            Bundle args = bundle;
            View view2 = host;
            int i2 = action;
            Bundle bundle2 = args;
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            if (!this.this$0.shouldIgnore() && this.this$0.mRecyclerView.getLayoutManager() != null) {
                return this.this$0.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(host, action, args);
            }
            return false;
        }
    };
    final RecyclerView mRecyclerView;

    public RecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = recyclerView;
        RecyclerView recyclerView3 = recyclerView2;
        this.mRecyclerView = recyclerView2;
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldIgnore() {
        return this.mRecyclerView.hasPendingAdapterUpdates();
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        View host = view;
        int action = i;
        Bundle args = bundle;
        View view2 = host;
        int i2 = action;
        Bundle bundle2 = args;
        if (super.performAccessibilityAction(host, action, args)) {
            return true;
        }
        if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) {
            return this.mRecyclerView.getLayoutManager().performAccessibilityAction(action, args);
        }
        return false;
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        View host = view;
        AccessibilityNodeInfoCompat info = accessibilityNodeInfoCompat;
        View view2 = host;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = info;
        super.onInitializeAccessibilityNodeInfo(host, info);
        info.setClassName(RecyclerView.class.getName());
        if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) {
            this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(info);
        }
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        View host = view;
        AccessibilityEvent event = accessibilityEvent;
        View view2 = host;
        AccessibilityEvent accessibilityEvent2 = event;
        super.onInitializeAccessibilityEvent(host, event);
        event.setClassName(RecyclerView.class.getName());
        if ((host instanceof RecyclerView) && !shouldIgnore()) {
            RecyclerView recyclerView = (RecyclerView) host;
            RecyclerView rv = recyclerView;
            if (recyclerView.getLayoutManager() != null) {
                rv.getLayoutManager().onInitializeAccessibilityEvent(event);
            }
        }
    }

    public AccessibilityDelegateCompat getItemDelegate() {
        return this.mItemDelegate;
    }
}
