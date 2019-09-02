package android.support.p000v4.widget;

import android.view.View;
import android.widget.ListView;

/* renamed from: android.support.v4.widget.ListViewAutoScrollHelper */
public class ListViewAutoScrollHelper extends AutoScrollHelper {
    private final ListView mTarget;

    public ListViewAutoScrollHelper(ListView listView) {
        ListView target = listView;
        ListView listView2 = target;
        super(target);
        this.mTarget = target;
    }

    public void scrollTargetBy(int i, int i2) {
        int deltaY = i2;
        int i3 = i;
        int i4 = deltaY;
        ListViewCompat.scrollListBy(this.mTarget, deltaY);
    }

    public boolean canTargetScrollHorizontally(int i) {
        int i2 = i;
        return false;
    }

    public boolean canTargetScrollVertically(int i) {
        int direction = i;
        int i2 = direction;
        ListView listView = this.mTarget;
        ListView target = listView;
        int count = listView.getCount();
        int itemCount = count;
        if (count == 0) {
            return false;
        }
        int childCount = target.getChildCount();
        int firstVisiblePosition = target.getFirstVisiblePosition();
        int firstPosition = firstVisiblePosition;
        int lastPosition = firstVisiblePosition + childCount;
        if (direction <= 0) {
            if (direction >= 0) {
                return false;
            }
            if (firstPosition <= 0) {
                View childAt = target.getChildAt(0);
                View view = childAt;
                if (childAt.getTop() >= 0) {
                    return false;
                }
            }
        } else if (lastPosition >= itemCount) {
            View childAt2 = target.getChildAt(childCount - 1);
            View view2 = childAt2;
            if (childAt2.getBottom() <= target.getHeight()) {
                return false;
            }
        }
        return true;
    }
}
