package android.support.v13.view;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.support.annotation.RequiresApi;
import android.support.p000v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

@TargetApi(13)
@RequiresApi(13)
public class DragStartHelper {
    private boolean mDragging;
    private int mLastTouchX;
    private int mLastTouchY;
    private final OnDragStartListener mListener;
    private final OnLongClickListener mLongClickListener = new OnLongClickListener(this) {
        final /* synthetic */ DragStartHelper this$0;

        {
            DragStartHelper this$02 = r5;
            DragStartHelper dragStartHelper = this$02;
            this.this$0 = this$02;
        }

        public boolean onLongClick(View view) {
            View v = view;
            View view2 = v;
            return this.this$0.onLongClick(v);
        }
    };
    private final OnTouchListener mTouchListener = new OnTouchListener(this) {
        final /* synthetic */ DragStartHelper this$0;

        {
            DragStartHelper this$02 = r5;
            DragStartHelper dragStartHelper = this$02;
            this.this$0 = this$02;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            View v = view;
            MotionEvent event = motionEvent;
            View view2 = v;
            MotionEvent motionEvent2 = event;
            return this.this$0.onTouch(v, event);
        }
    };
    private final View mView;

    public interface OnDragStartListener {
        boolean onDragStart(View view, DragStartHelper dragStartHelper);
    }

    public DragStartHelper(View view, OnDragStartListener onDragStartListener) {
        View view2 = view;
        OnDragStartListener listener = onDragStartListener;
        View view3 = view2;
        OnDragStartListener onDragStartListener2 = listener;
        this.mView = view2;
        this.mListener = listener;
    }

    public void attach() {
        this.mView.setOnLongClickListener(this.mLongClickListener);
        this.mView.setOnTouchListener(this.mTouchListener);
    }

    public void detach() {
        this.mView.setOnLongClickListener(null);
        this.mView.setOnTouchListener(null);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        View v = view;
        MotionEvent event = motionEvent;
        View view2 = v;
        MotionEvent motionEvent2 = event;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case 0:
                this.mLastTouchX = x;
                this.mLastTouchY = y;
                break;
            case 1:
            case 3:
                this.mDragging = false;
                break;
            case 2:
                if (MotionEventCompat.isFromSource(event, 8194) && (MotionEventCompat.getButtonState(event) & 1) != 0 && !this.mDragging && !(this.mLastTouchX == x && this.mLastTouchY == y)) {
                    this.mLastTouchX = x;
                    this.mLastTouchY = y;
                    this.mDragging = this.mListener.onDragStart(v, this);
                    return this.mDragging;
                }
        }
        return false;
    }

    public boolean onLongClick(View view) {
        View v = view;
        View view2 = v;
        return this.mListener.onDragStart(v, this);
    }

    public void getTouchPosition(Point point) {
        Point point2 = point;
        Point point3 = point2;
        point2.set(this.mLastTouchX, this.mLastTouchY);
    }
}
