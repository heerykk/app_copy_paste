package android.support.p000v4.widget;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.media.TransportMediator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* renamed from: android.support.v4.widget.FocusStrategy */
class FocusStrategy {

    /* renamed from: android.support.v4.widget.FocusStrategy$BoundsAdapter */
    public interface BoundsAdapter<T> {
        void obtainBounds(T t, Rect rect);
    }

    /* renamed from: android.support.v4.widget.FocusStrategy$CollectionAdapter */
    public interface CollectionAdapter<T, V> {
        V get(T t, int i);

        int size(T t);
    }

    /* renamed from: android.support.v4.widget.FocusStrategy$SequentialComparator */
    private static class SequentialComparator<T> implements Comparator<T> {
        private final BoundsAdapter<T> mAdapter;
        private final boolean mIsLayoutRtl;
        private final Rect mTemp1 = new Rect();
        private final Rect mTemp2 = new Rect();

        SequentialComparator(boolean z, BoundsAdapter<T> boundsAdapter) {
            BoundsAdapter<T> adapter = boundsAdapter;
            boolean isLayoutRtl = z;
            BoundsAdapter<T> boundsAdapter2 = adapter;
            this.mIsLayoutRtl = isLayoutRtl;
            this.mAdapter = adapter;
        }

        public int compare(T t, T t2) {
            T first = t;
            T second = t2;
            T t3 = first;
            T t4 = second;
            Rect firstRect = this.mTemp1;
            Rect secondRect = this.mTemp2;
            this.mAdapter.obtainBounds(first, firstRect);
            this.mAdapter.obtainBounds(second, secondRect);
            if (firstRect.top < secondRect.top) {
                return -1;
            }
            if (firstRect.top > secondRect.top) {
                return 1;
            }
            if (firstRect.left < secondRect.left) {
                return !this.mIsLayoutRtl ? -1 : 1;
            } else if (firstRect.left > secondRect.left) {
                return !this.mIsLayoutRtl ? 1 : -1;
            } else if (firstRect.bottom < secondRect.bottom) {
                return -1;
            } else {
                if (firstRect.bottom > secondRect.bottom) {
                    return 1;
                }
                if (firstRect.right < secondRect.right) {
                    return !this.mIsLayoutRtl ? -1 : 1;
                } else if (firstRect.right <= secondRect.right) {
                    return 0;
                } else {
                    return !this.mIsLayoutRtl ? 1 : -1;
                }
            }
        }
    }

    FocusStrategy() {
    }

    public static <L, T> T findNextFocusInRelativeDirection(@NonNull L l, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> boundsAdapter, @Nullable T t, int i, boolean z, boolean z2) {
        L focusables = l;
        CollectionAdapter<L, T> collectionAdapter2 = collectionAdapter;
        BoundsAdapter<T> adapter = boundsAdapter;
        T focused = t;
        int direction = i;
        L l2 = focusables;
        CollectionAdapter<L, T> collectionAdapter3 = collectionAdapter2;
        BoundsAdapter<T> boundsAdapter2 = adapter;
        T t2 = focused;
        int i2 = direction;
        boolean isLayoutRtl = z;
        boolean wrap = z2;
        int size = collectionAdapter2.size(focusables);
        int count = size;
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = arrayList;
        for (int i3 = 0; i3 < count; i3++) {
            boolean add = arrayList2.add(collectionAdapter2.get(focusables, i3));
        }
        SequentialComparator sequentialComparator = new SequentialComparator(isLayoutRtl, adapter);
        Collections.sort(arrayList2, sequentialComparator);
        switch (direction) {
            case 1:
                return getPreviousFocusable(focused, arrayList2, wrap);
            case 2:
                return getNextFocusable(focused, arrayList2, wrap);
            default:
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
                throw illegalArgumentException;
        }
    }

    private static <T> T getNextFocusable(T t, ArrayList<T> arrayList, boolean z) {
        T focused = t;
        ArrayList<T> focusables = arrayList;
        T t2 = focused;
        ArrayList<T> arrayList2 = focusables;
        boolean wrap = z;
        int count = focusables.size();
        int lastIndexOf = (focused != null ? focusables.lastIndexOf(focused) : -1) + 1;
        int position = lastIndexOf;
        if (lastIndexOf < count) {
            return focusables.get(position);
        }
        if (wrap && count > 0) {
            return focusables.get(0);
        }
        return null;
    }

    private static <T> T getPreviousFocusable(T t, ArrayList<T> arrayList, boolean z) {
        T focused = t;
        ArrayList<T> focusables = arrayList;
        T t2 = focused;
        ArrayList<T> arrayList2 = focusables;
        boolean wrap = z;
        int size = focusables.size();
        int count = size;
        int indexOf = (focused != null ? focusables.indexOf(focused) : size) - 1;
        int position = indexOf;
        if (indexOf >= 0) {
            return focusables.get(position);
        }
        if (wrap && count > 0) {
            return focusables.get(count - 1);
        }
        return null;
    }

    public static <L, T> T findNextFocusInAbsoluteDirection(@NonNull L l, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> boundsAdapter, @Nullable T t, @NonNull Rect rect, int i) {
        L focusables = l;
        CollectionAdapter<L, T> collectionAdapter2 = collectionAdapter;
        BoundsAdapter<T> adapter = boundsAdapter;
        T focused = t;
        Rect focusedRect = rect;
        int direction = i;
        L l2 = focusables;
        CollectionAdapter<L, T> collectionAdapter3 = collectionAdapter2;
        BoundsAdapter<T> boundsAdapter2 = adapter;
        T t2 = focused;
        Rect rect2 = focusedRect;
        int i2 = direction;
        Rect rect3 = new Rect(focusedRect);
        Rect bestCandidateRect = rect3;
        switch (direction) {
            case 17:
                rect3.offset(focusedRect.width() + 1, 0);
                break;
            case 33:
                rect3.offset(0, focusedRect.height() + 1);
                break;
            case 66:
                rect3.offset(-(focusedRect.width() + 1), 0);
                break;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                rect3.offset(0, -(focusedRect.height() + 1));
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        T closest = null;
        int count = collectionAdapter2.size(focusables);
        Rect focusableRect = new Rect();
        for (int i3 = 0; i3 < count; i3++) {
            T t3 = collectionAdapter2.get(focusables, i3);
            T focusable = t3;
            if (t3 != focused) {
                adapter.obtainBounds(focusable, focusableRect);
                if (isBetterCandidate(direction, focusedRect, focusableRect, bestCandidateRect)) {
                    bestCandidateRect.set(focusableRect);
                    closest = focusable;
                }
            }
        }
        return closest;
    }

    private static boolean isBetterCandidate(int i, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        boolean z;
        int direction = i;
        Rect source = rect;
        Rect candidate = rect2;
        Rect currentBest = rect3;
        int i2 = direction;
        Rect rect4 = source;
        Rect rect5 = candidate;
        Rect rect6 = currentBest;
        if (!isCandidate(source, candidate, direction)) {
            return false;
        }
        if (!isCandidate(source, currentBest, direction)) {
            return true;
        }
        if (beamBeats(direction, source, candidate, currentBest)) {
            return true;
        }
        if (beamBeats(direction, source, currentBest, candidate)) {
            return false;
        }
        if (getWeightedDistanceFor(majorAxisDistance(direction, source, candidate), minorAxisDistance(direction, source, candidate)) >= getWeightedDistanceFor(majorAxisDistance(direction, source, currentBest), minorAxisDistance(direction, source, currentBest))) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    private static boolean beamBeats(int i, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        int direction = i;
        Rect source = rect;
        Rect rect1 = rect2;
        Rect rect22 = rect3;
        int i2 = direction;
        Rect rect4 = source;
        Rect rect5 = rect1;
        Rect rect6 = rect22;
        boolean rect1InSrcBeam = beamsOverlap(direction, source, rect1);
        boolean beamsOverlap = beamsOverlap(direction, source, rect22);
        boolean z = beamsOverlap;
        if (beamsOverlap || !rect1InSrcBeam) {
            return false;
        }
        if (!isToDirectionOf(direction, source, rect22)) {
            return true;
        }
        if (direction == 17 || direction == 66) {
            return true;
        }
        return majorAxisDistance(direction, source, rect1) < majorAxisDistanceToFarEdge(direction, source, rect22);
    }

    private static int getWeightedDistanceFor(int i, int i2) {
        int majorAxisDistance = i;
        int minorAxisDistance = i2;
        int i3 = majorAxisDistance;
        int i4 = minorAxisDistance;
        return (13 * majorAxisDistance * majorAxisDistance) + (minorAxisDistance * minorAxisDistance);
    }

    private static boolean isCandidate(@NonNull Rect rect, @NonNull Rect rect2, int i) {
        boolean z;
        boolean z2;
        boolean z3;
        Rect srcRect = rect;
        Rect destRect = rect2;
        int direction = i;
        Rect rect3 = srcRect;
        Rect rect4 = destRect;
        int i2 = direction;
        switch (direction) {
            case 17:
                return (srcRect.right > destRect.right || srcRect.left >= destRect.right) && srcRect.left > destRect.left;
            case 33:
                if ((srcRect.bottom <= destRect.bottom && srcRect.top < destRect.bottom) || srcRect.top <= destRect.top) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                return z3;
            case 66:
                if ((srcRect.left >= destRect.left && srcRect.right > destRect.left) || srcRect.right >= destRect.right) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                return z2;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                if ((srcRect.top >= destRect.top && srcRect.bottom > destRect.top) || srcRect.bottom >= destRect.bottom) {
                    z = false;
                } else {
                    z = true;
                }
                return z;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static boolean beamsOverlap(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int direction = i;
        Rect rect1 = rect;
        Rect rect22 = rect2;
        int i2 = direction;
        Rect rect3 = rect1;
        Rect rect4 = rect22;
        switch (direction) {
            case 17:
            case 66:
                return rect22.bottom >= rect1.top && rect22.top <= rect1.bottom;
            case 33:
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                return rect22.right >= rect1.left && rect22.left <= rect1.right;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static boolean isToDirectionOf(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        boolean z;
        boolean z2;
        int direction = i;
        Rect src = rect;
        Rect dest = rect2;
        int i2 = direction;
        Rect rect3 = src;
        Rect rect4 = dest;
        switch (direction) {
            case 17:
                return src.left >= dest.right;
            case 33:
                if (src.top < dest.bottom) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                return z2;
            case 66:
                return src.right <= dest.left;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                if (src.bottom > dest.top) {
                    z = false;
                } else {
                    z = true;
                }
                return z;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static int majorAxisDistance(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int direction = i;
        Rect source = rect;
        Rect dest = rect2;
        int i2 = direction;
        Rect rect3 = source;
        Rect rect4 = dest;
        return Math.max(0, majorAxisDistanceRaw(direction, source, dest));
    }

    private static int majorAxisDistanceRaw(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int direction = i;
        Rect source = rect;
        Rect dest = rect2;
        int i2 = direction;
        Rect rect3 = source;
        Rect rect4 = dest;
        switch (direction) {
            case 17:
                return source.left - dest.right;
            case 33:
                return source.top - dest.bottom;
            case 66:
                return dest.left - source.right;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                return dest.top - source.bottom;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static int majorAxisDistanceToFarEdge(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int direction = i;
        Rect source = rect;
        Rect dest = rect2;
        int i2 = direction;
        Rect rect3 = source;
        Rect rect4 = dest;
        return Math.max(1, majorAxisDistanceToFarEdgeRaw(direction, source, dest));
    }

    private static int majorAxisDistanceToFarEdgeRaw(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int direction = i;
        Rect source = rect;
        Rect dest = rect2;
        int i2 = direction;
        Rect rect3 = source;
        Rect rect4 = dest;
        switch (direction) {
            case 17:
                return source.left - dest.left;
            case 33:
                return source.top - dest.top;
            case 66:
                return dest.right - source.right;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                return dest.bottom - source.bottom;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }

    private static int minorAxisDistance(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int direction = i;
        Rect source = rect;
        Rect dest = rect2;
        int i2 = direction;
        Rect rect3 = source;
        Rect rect4 = dest;
        switch (direction) {
            case 17:
            case 66:
                return Math.abs((source.top + (source.height() / 2)) - (dest.top + (dest.height() / 2)));
            case 33:
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                return Math.abs((source.left + (source.width() / 2)) - (dest.left + (dest.width() / 2)));
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
    }
}
