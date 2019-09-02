package android.support.p003v7.widget.util;

import android.support.p003v7.util.SortedList.Callback;
import android.support.p003v7.widget.RecyclerView.Adapter;

/* renamed from: android.support.v7.widget.util.SortedListAdapterCallback */
public abstract class SortedListAdapterCallback<T2> extends Callback<T2> {
    final Adapter mAdapter;

    public SortedListAdapterCallback(Adapter adapter) {
        Adapter adapter2 = adapter;
        Adapter adapter3 = adapter2;
        this.mAdapter = adapter2;
    }

    public void onInserted(int i, int i2) {
        int position = i;
        int count = i2;
        int i3 = position;
        int i4 = count;
        this.mAdapter.notifyItemRangeInserted(position, count);
    }

    public void onRemoved(int i, int i2) {
        int position = i;
        int count = i2;
        int i3 = position;
        int i4 = count;
        this.mAdapter.notifyItemRangeRemoved(position, count);
    }

    public void onMoved(int i, int i2) {
        int fromPosition = i;
        int toPosition = i2;
        int i3 = fromPosition;
        int i4 = toPosition;
        this.mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    public void onChanged(int i, int i2) {
        int position = i;
        int count = i2;
        int i3 = position;
        int i4 = count;
        this.mAdapter.notifyItemRangeChanged(position, count);
    }
}
