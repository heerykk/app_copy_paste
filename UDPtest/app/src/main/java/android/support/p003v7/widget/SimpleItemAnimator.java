package android.support.p003v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p003v7.widget.RecyclerView.ItemAnimator;
import android.support.p003v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import android.support.p003v7.widget.RecyclerView.ViewHolder;
import android.view.View;

/* renamed from: android.support.v7.widget.SimpleItemAnimator */
public abstract class SimpleItemAnimator extends ItemAnimator {
    private static final boolean DEBUG = false;
    private static final String TAG = "SimpleItemAnimator";
    boolean mSupportsChangeAnimations = true;

    public abstract boolean animateAdd(ViewHolder viewHolder);

    public abstract boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4);

    public abstract boolean animateMove(ViewHolder viewHolder, int i, int i2, int i3, int i4);

    public abstract boolean animateRemove(ViewHolder viewHolder);

    public SimpleItemAnimator() {
    }

    public boolean getSupportsChangeAnimations() {
        return this.mSupportsChangeAnimations;
    }

    public void setSupportsChangeAnimations(boolean z) {
        this.mSupportsChangeAnimations = z;
    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        return !this.mSupportsChangeAnimations || viewHolder2.isInvalid();
    }

    public boolean animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) {
        ViewHolder viewHolder2 = viewHolder;
        ItemHolderInfo preLayoutInfo = itemHolderInfo;
        ItemHolderInfo postLayoutInfo = itemHolderInfo2;
        ViewHolder viewHolder3 = viewHolder2;
        ItemHolderInfo itemHolderInfo3 = preLayoutInfo;
        ItemHolderInfo itemHolderInfo4 = postLayoutInfo;
        int oldLeft = preLayoutInfo.left;
        int oldTop = preLayoutInfo.top;
        View disappearingItemView = viewHolder2.itemView;
        int newLeft = postLayoutInfo != null ? postLayoutInfo.left : disappearingItemView.getLeft();
        int newTop = postLayoutInfo != null ? postLayoutInfo.top : disappearingItemView.getTop();
        if (viewHolder2.isRemoved() || (oldLeft == newLeft && oldTop == newTop)) {
            return animateRemove(viewHolder2);
        }
        disappearingItemView.layout(newLeft, newTop, newLeft + disappearingItemView.getWidth(), newTop + disappearingItemView.getHeight());
        return animateMove(viewHolder2, oldLeft, oldTop, newLeft, newTop);
    }

    public boolean animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        ViewHolder viewHolder2 = viewHolder;
        ItemHolderInfo preLayoutInfo = itemHolderInfo;
        ItemHolderInfo postLayoutInfo = itemHolderInfo2;
        ViewHolder viewHolder3 = viewHolder2;
        ItemHolderInfo itemHolderInfo3 = preLayoutInfo;
        ItemHolderInfo itemHolderInfo4 = postLayoutInfo;
        if (preLayoutInfo == null || (preLayoutInfo.left == postLayoutInfo.left && preLayoutInfo.top == postLayoutInfo.top)) {
            return animateAdd(viewHolder2);
        }
        return animateMove(viewHolder2, preLayoutInfo.left, preLayoutInfo.top, postLayoutInfo.left, postLayoutInfo.top);
    }

    public boolean animatePersistence(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        ViewHolder viewHolder2 = viewHolder;
        ItemHolderInfo preInfo = itemHolderInfo;
        ItemHolderInfo postInfo = itemHolderInfo2;
        ViewHolder viewHolder3 = viewHolder2;
        ItemHolderInfo itemHolderInfo3 = preInfo;
        ItemHolderInfo itemHolderInfo4 = postInfo;
        if (preInfo.left == postInfo.left && preInfo.top == postInfo.top) {
            dispatchMoveFinished(viewHolder2);
            return false;
        }
        return animateMove(viewHolder2, preInfo.left, preInfo.top, postInfo.left, postInfo.top);
    }

    public boolean animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        int toLeft;
        int toTop;
        ViewHolder oldHolder = viewHolder;
        ViewHolder newHolder = viewHolder2;
        ItemHolderInfo preInfo = itemHolderInfo;
        ItemHolderInfo postInfo = itemHolderInfo2;
        ViewHolder viewHolder3 = oldHolder;
        ViewHolder viewHolder4 = newHolder;
        ItemHolderInfo itemHolderInfo3 = preInfo;
        ItemHolderInfo itemHolderInfo4 = postInfo;
        int fromLeft = preInfo.left;
        int fromTop = preInfo.top;
        if (!newHolder.shouldIgnore()) {
            toLeft = postInfo.left;
            toTop = postInfo.top;
        } else {
            toLeft = preInfo.left;
            toTop = preInfo.top;
        }
        return animateChange(oldHolder, newHolder, fromLeft, fromTop, toLeft, toTop);
    }

    public final void dispatchRemoveFinished(ViewHolder viewHolder) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        onRemoveFinished(item);
        dispatchAnimationFinished(item);
    }

    public final void dispatchMoveFinished(ViewHolder viewHolder) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        onMoveFinished(item);
        dispatchAnimationFinished(item);
    }

    public final void dispatchAddFinished(ViewHolder viewHolder) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        onAddFinished(item);
        dispatchAnimationFinished(item);
    }

    public final void dispatchChangeFinished(ViewHolder viewHolder, boolean z) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        onChangeFinished(item, z);
        dispatchAnimationFinished(item);
    }

    public final void dispatchRemoveStarting(ViewHolder viewHolder) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        onRemoveStarting(item);
    }

    public final void dispatchMoveStarting(ViewHolder viewHolder) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        onMoveStarting(item);
    }

    public final void dispatchAddStarting(ViewHolder viewHolder) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        onAddStarting(item);
    }

    public final void dispatchChangeStarting(ViewHolder viewHolder, boolean z) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        onChangeStarting(item, z);
    }

    public void onRemoveStarting(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
    }

    public void onRemoveFinished(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
    }

    public void onAddStarting(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
    }

    public void onAddFinished(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
    }

    public void onMoveStarting(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
    }

    public void onMoveFinished(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
    }

    public void onChangeStarting(ViewHolder viewHolder, boolean z) {
        ViewHolder viewHolder2 = viewHolder;
        boolean z2 = z;
    }

    public void onChangeFinished(ViewHolder viewHolder, boolean z) {
        ViewHolder viewHolder2 = viewHolder;
        boolean z2 = z;
    }
}
