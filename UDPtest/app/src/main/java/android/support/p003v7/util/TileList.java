package android.support.p003v7.util;

import android.util.SparseArray;
import java.lang.reflect.Array;

/* renamed from: android.support.v7.util.TileList */
class TileList<T> {
    Tile<T> mLastAccessedTile;
    final int mTileSize;
    private final SparseArray<Tile<T>> mTiles = new SparseArray<>(10);

    /* renamed from: android.support.v7.util.TileList$Tile */
    public static class Tile<T> {
        public int mItemCount;
        public final T[] mItems;
        Tile<T> mNext;
        public int mStartPosition;

        public Tile(Class<T> cls, int i) {
            Class<T> klass = cls;
            int size = i;
            Class<T> cls2 = klass;
            int i2 = size;
            this.mItems = (Object[]) Array.newInstance(klass, size);
        }

        /* access modifiers changed from: 0000 */
        public boolean containsPosition(int i) {
            int pos = i;
            int i2 = pos;
            return this.mStartPosition <= pos && pos < this.mStartPosition + this.mItemCount;
        }

        /* access modifiers changed from: 0000 */
        public T getByPosition(int i) {
            int pos = i;
            int i2 = pos;
            return this.mItems[pos - this.mStartPosition];
        }
    }

    public TileList(int i) {
        int tileSize = i;
        int i2 = tileSize;
        this.mTileSize = tileSize;
    }

    public T getItemAt(int i) {
        int pos = i;
        int i2 = pos;
        if (this.mLastAccessedTile == null || !this.mLastAccessedTile.containsPosition(pos)) {
            int i3 = pos - (pos % this.mTileSize);
            int i4 = i3;
            int indexOfKey = this.mTiles.indexOfKey(i3);
            int index = indexOfKey;
            if (indexOfKey < 0) {
                return null;
            }
            this.mLastAccessedTile = (Tile) this.mTiles.valueAt(index);
        }
        return this.mLastAccessedTile.getByPosition(pos);
    }

    public int size() {
        return this.mTiles.size();
    }

    public void clear() {
        this.mTiles.clear();
    }

    public Tile<T> getAtIndex(int i) {
        int index = i;
        int i2 = index;
        return (Tile) this.mTiles.valueAt(index);
    }

    public Tile<T> addOrReplace(Tile<T> tile) {
        Tile<T> newTile = tile;
        Tile<T> tile2 = newTile;
        int indexOfKey = this.mTiles.indexOfKey(newTile.mStartPosition);
        int index = indexOfKey;
        if (indexOfKey >= 0) {
            Tile tile3 = (Tile) this.mTiles.valueAt(index);
            this.mTiles.setValueAt(index, newTile);
            if (this.mLastAccessedTile == tile3) {
                this.mLastAccessedTile = newTile;
            }
            return tile3;
        }
        this.mTiles.put(newTile.mStartPosition, newTile);
        return null;
    }

    public Tile<T> removeAtPos(int i) {
        int startPosition = i;
        int i2 = startPosition;
        Tile tile = (Tile) this.mTiles.get(startPosition);
        if (this.mLastAccessedTile == tile) {
            this.mLastAccessedTile = null;
        }
        this.mTiles.delete(startPosition);
        return tile;
    }
}
