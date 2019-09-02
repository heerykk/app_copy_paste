package android.support.p003v7.widget.helper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.p000v4.animation.AnimatorCompatHelper;
import android.support.p000v4.animation.AnimatorListenerCompat;
import android.support.p000v4.animation.AnimatorUpdateListenerCompat;
import android.support.p000v4.animation.ValueAnimatorCompat;
import android.support.p000v4.view.GestureDetectorCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.VelocityTrackerCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p003v7.recyclerview.C0271R;
import android.support.p003v7.widget.RecyclerView;
import android.support.p003v7.widget.RecyclerView.ChildDrawingOrderCallback;
import android.support.p003v7.widget.RecyclerView.ItemAnimator;
import android.support.p003v7.widget.RecyclerView.ItemDecoration;
import android.support.p003v7.widget.RecyclerView.LayoutManager;
import android.support.p003v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.p003v7.widget.RecyclerView.OnItemTouchListener;
import android.support.p003v7.widget.RecyclerView.State;
import android.support.p003v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v7.widget.helper.ItemTouchHelper */
public class ItemTouchHelper extends ItemDecoration implements OnChildAttachStateChangeListener {
    static final int ACTION_MODE_DRAG_MASK = 16711680;
    private static final int ACTION_MODE_IDLE_MASK = 255;
    static final int ACTION_MODE_SWIPE_MASK = 65280;
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    static final int ACTIVE_POINTER_ID_NONE = -1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    static final boolean DEBUG = false;
    static final int DIRECTION_FLAG_COUNT = 8;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    private static final int PIXELS_PER_SECOND = 1000;
    public static final int RIGHT = 8;
    public static final int START = 16;
    static final String TAG = "ItemTouchHelper";

    /* renamed from: UP */
    public static final int f26UP = 1;
    int mActionState = 0;
    int mActivePointerId = -1;
    Callback mCallback;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
    private List<Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    float mDx;
    float mDy;
    GestureDetectorCompat mGestureDetector;
    float mInitialTouchX;
    float mInitialTouchY;
    float mMaxSwipeVelocity;
    private final OnItemTouchListener mOnItemTouchListener = new OnItemTouchListener(this) {
        final /* synthetic */ ItemTouchHelper this$0;

        {
            ItemTouchHelper this$02 = r5;
            ItemTouchHelper itemTouchHelper = this$02;
            this.this$0 = this$02;
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            boolean z;
            MotionEvent event = motionEvent;
            RecyclerView recyclerView2 = recyclerView;
            MotionEvent motionEvent2 = event;
            boolean onTouchEvent = this.this$0.mGestureDetector.onTouchEvent(event);
            int actionMasked = MotionEventCompat.getActionMasked(event);
            int action = actionMasked;
            if (actionMasked == 0) {
                this.this$0.mActivePointerId = event.getPointerId(0);
                this.this$0.mInitialTouchX = event.getX();
                this.this$0.mInitialTouchY = event.getY();
                this.this$0.obtainVelocityTracker();
                if (this.this$0.mSelected == null) {
                    RecoverAnimation findAnimation = this.this$0.findAnimation(event);
                    RecoverAnimation animation = findAnimation;
                    if (findAnimation != null) {
                        this.this$0.mInitialTouchX -= animation.f27mX;
                        this.this$0.mInitialTouchY -= animation.f28mY;
                        int endRecoverAnimation = this.this$0.endRecoverAnimation(animation.mViewHolder, true);
                        if (this.this$0.mPendingCleanup.remove(animation.mViewHolder.itemView)) {
                            this.this$0.mCallback.clearView(this.this$0.mRecyclerView, animation.mViewHolder);
                        }
                        this.this$0.select(animation.mViewHolder, animation.mActionState);
                        this.this$0.updateDxDy(event, this.this$0.mSelectedFlags, 0);
                    }
                }
            } else if (action == 3 || action == 1) {
                this.this$0.mActivePointerId = -1;
                this.this$0.select(null, 0);
            } else if (this.this$0.mActivePointerId != -1) {
                int findPointerIndex = event.findPointerIndex(this.this$0.mActivePointerId);
                int index = findPointerIndex;
                if (findPointerIndex >= 0) {
                    boolean checkSelectForSwipe = this.this$0.checkSelectForSwipe(action, event, index);
                }
            }
            if (this.this$0.mVelocityTracker != null) {
                this.this$0.mVelocityTracker.addMovement(event);
            }
            if (this.this$0.mSelected == null) {
                z = false;
            } else {
                z = true;
            }
            return z;
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            MotionEvent event = motionEvent;
            RecyclerView recyclerView2 = recyclerView;
            MotionEvent motionEvent2 = event;
            boolean onTouchEvent = this.this$0.mGestureDetector.onTouchEvent(event);
            if (this.this$0.mVelocityTracker != null) {
                this.this$0.mVelocityTracker.addMovement(event);
            }
            if (this.this$0.mActivePointerId != -1) {
                int action = MotionEventCompat.getActionMasked(event);
                int findPointerIndex = event.findPointerIndex(this.this$0.mActivePointerId);
                int activePointerIndex = findPointerIndex;
                if (findPointerIndex >= 0) {
                    boolean checkSelectForSwipe = this.this$0.checkSelectForSwipe(action, event, activePointerIndex);
                }
                ViewHolder viewHolder = this.this$0.mSelected;
                ViewHolder viewHolder2 = viewHolder;
                if (viewHolder != null) {
                    switch (action) {
                        case 1:
                            break;
                        case 2:
                            if (activePointerIndex >= 0) {
                                this.this$0.updateDxDy(event, this.this$0.mSelectedFlags, activePointerIndex);
                                this.this$0.moveIfNecessary(viewHolder2);
                                boolean removeCallbacks = this.this$0.mRecyclerView.removeCallbacks(this.this$0.mScrollRunnable);
                                this.this$0.mScrollRunnable.run();
                                this.this$0.mRecyclerView.invalidate();
                                break;
                            }
                            break;
                        case 3:
                            if (this.this$0.mVelocityTracker != null) {
                                this.this$0.mVelocityTracker.clear();
                                break;
                            }
                            break;
                        case 6:
                            int actionIndex = MotionEventCompat.getActionIndex(event);
                            int pointerIndex = actionIndex;
                            int pointerId = event.getPointerId(actionIndex);
                            int i = pointerId;
                            if (pointerId == this.this$0.mActivePointerId) {
                                this.this$0.mActivePointerId = event.getPointerId(pointerIndex != 0 ? 0 : 1);
                                this.this$0.updateDxDy(event, this.this$0.mSelectedFlags, pointerIndex);
                                break;
                            }
                            break;
                    }
                    this.this$0.select(null, 0);
                    this.this$0.mActivePointerId = -1;
                }
            }
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (z) {
                this.this$0.select(null, 0);
            }
        }
    };
    View mOverdrawChild = null;
    int mOverdrawChildPosition = -1;
    final List<View> mPendingCleanup = new ArrayList();
    List<RecoverAnimation> mRecoverAnimations = new ArrayList();
    RecyclerView mRecyclerView;
    final Runnable mScrollRunnable = new Runnable(this) {
        final /* synthetic */ ItemTouchHelper this$0;

        {
            ItemTouchHelper this$02 = r5;
            ItemTouchHelper itemTouchHelper = this$02;
            this.this$0 = this$02;
        }

        public void run() {
            if (this.this$0.mSelected != null && this.this$0.scrollIfNecessary()) {
                if (this.this$0.mSelected != null) {
                    this.this$0.moveIfNecessary(this.this$0.mSelected);
                }
                boolean removeCallbacks = this.this$0.mRecyclerView.removeCallbacks(this.this$0.mScrollRunnable);
                ViewCompat.postOnAnimation(this.this$0.mRecyclerView, this);
            }
        }
    };
    ViewHolder mSelected = null;
    int mSelectedFlags;
    float mSelectedStartX;
    float mSelectedStartY;
    private int mSlop;
    private List<ViewHolder> mSwapTargets;
    float mSwipeEscapeVelocity;
    private final float[] mTmpPosition = new float[2];
    private Rect mTmpRect;
    VelocityTracker mVelocityTracker;

    /* renamed from: android.support.v7.widget.helper.ItemTouchHelper$Callback */
    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private static final Interpolator sDragScrollInterpolator = new Interpolator() {
            public float getInterpolation(float f) {
                float t = f;
                float f2 = t;
                return t * t * t * t * t;
            }
        };
        private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator() {
            public float getInterpolation(float f) {
                float t = f;
                float f2 = t;
                float f3 = t - 1.0f;
                float t2 = f3;
                return (f3 * t2 * t2 * t2 * t2) + 1.0f;
            }
        };
        private static final ItemTouchUIUtil sUICallback;
        private int mCachedMaxScrollSpeed = -1;

        public abstract int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder);

        public abstract boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2);

        public abstract void onSwiped(ViewHolder viewHolder, int i);

        public Callback() {
        }

        static {
            if (VERSION.SDK_INT >= 21) {
                sUICallback = new Lollipop();
            } else if (VERSION.SDK_INT < 11) {
                sUICallback = new Gingerbread();
            } else {
                sUICallback = new Honeycomb();
            }
        }

        public static ItemTouchUIUtil getDefaultUIUtil() {
            return sUICallback;
        }

        public static int convertToRelativeDirection(int i, int i2) {
            int flags = i;
            int layoutDirection = i2;
            int i3 = flags;
            int i4 = layoutDirection;
            int i5 = flags & ABS_HORIZONTAL_DIR_FLAGS;
            int masked = i5;
            if (i5 == 0) {
                return flags;
            }
            int flags2 = flags & (masked ^ -1);
            if (layoutDirection != 0) {
                int i6 = flags2 | ((masked << 1) & -789517);
                int flags3 = i6;
                int i7 = i6 | (((masked << 1) & ABS_HORIZONTAL_DIR_FLAGS) << 2);
                int flags4 = i7;
                return i7;
            }
            int i8 = flags2 | (masked << 2);
            int flags5 = i8;
            return i8;
        }

        public static int makeMovementFlags(int i, int i2) {
            int dragFlags = i;
            int swipeFlags = i2;
            int i3 = dragFlags;
            int i4 = swipeFlags;
            return makeFlag(0, swipeFlags | dragFlags) | makeFlag(1, swipeFlags) | makeFlag(2, dragFlags);
        }

        public static int makeFlag(int i, int i2) {
            int actionState = i;
            int directions = i2;
            int i3 = actionState;
            int i4 = directions;
            return directions << (actionState * 8);
        }

        public int convertToAbsoluteDirection(int i, int i2) {
            int flags = i;
            int layoutDirection = i2;
            int i3 = flags;
            int i4 = layoutDirection;
            int i5 = flags & RELATIVE_DIR_FLAGS;
            int masked = i5;
            if (i5 == 0) {
                return flags;
            }
            int flags2 = flags & (masked ^ -1);
            if (layoutDirection != 0) {
                int i6 = flags2 | ((masked >> 1) & -3158065);
                int flags3 = i6;
                int i7 = i6 | (((masked >> 1) & RELATIVE_DIR_FLAGS) >> 2);
                int flags4 = i7;
                return i7;
            }
            int i8 = flags2 | (masked >> 2);
            int flags5 = i8;
            return i8;
        }

        /* access modifiers changed from: 0000 */
        public final int getAbsoluteMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder2 = viewHolder;
            RecyclerView recyclerView3 = recyclerView2;
            ViewHolder viewHolder3 = viewHolder2;
            int movementFlags = getMovementFlags(recyclerView2, viewHolder2);
            int i = movementFlags;
            return convertToAbsoluteDirection(movementFlags, ViewCompat.getLayoutDirection(recyclerView2));
        }

        /* access modifiers changed from: 0000 */
        public boolean hasDragFlag(RecyclerView recyclerView, ViewHolder viewHolder) {
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder2 = viewHolder;
            RecyclerView recyclerView3 = recyclerView2;
            ViewHolder viewHolder3 = viewHolder2;
            int absoluteMovementFlags = getAbsoluteMovementFlags(recyclerView2, viewHolder2);
            int i = absoluteMovementFlags;
            return (absoluteMovementFlags & ItemTouchHelper.ACTION_MODE_DRAG_MASK) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasSwipeFlag(RecyclerView recyclerView, ViewHolder viewHolder) {
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder2 = viewHolder;
            RecyclerView recyclerView3 = recyclerView2;
            ViewHolder viewHolder3 = viewHolder2;
            int absoluteMovementFlags = getAbsoluteMovementFlags(recyclerView2, viewHolder2);
            int i = absoluteMovementFlags;
            return (absoluteMovementFlags & 65280) != 0;
        }

        public boolean canDropOver(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) {
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder3 = viewHolder;
            ViewHolder viewHolder4 = viewHolder2;
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getSwipeThreshold(ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
            return 0.5f;
        }

        public float getMoveThreshold(ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
            return 0.5f;
        }

        public float getSwipeEscapeVelocity(float f) {
            float defaultValue = f;
            float f2 = defaultValue;
            return defaultValue;
        }

        public float getSwipeVelocityThreshold(float f) {
            float defaultValue = f;
            float f2 = defaultValue;
            return defaultValue;
        }

        public ViewHolder chooseDropTarget(ViewHolder viewHolder, List<ViewHolder> list, int i, int i2) {
            ViewHolder selected = viewHolder;
            List<ViewHolder> dropTargets = list;
            int curX = i;
            int curY = i2;
            ViewHolder viewHolder2 = selected;
            List<ViewHolder> list2 = dropTargets;
            int i3 = curX;
            int i4 = curY;
            int right = curX + selected.itemView.getWidth();
            int bottom = curY + selected.itemView.getHeight();
            ViewHolder winner = null;
            int winnerScore = -1;
            int dx = curX - selected.itemView.getLeft();
            int dy = curY - selected.itemView.getTop();
            int targetsSize = dropTargets.size();
            for (int i5 = 0; i5 < targetsSize; i5++) {
                ViewHolder viewHolder3 = (ViewHolder) dropTargets.get(i5);
                ViewHolder target = viewHolder3;
                if (dx > 0) {
                    int right2 = viewHolder3.itemView.getRight() - right;
                    int diff = right2;
                    if (right2 < 0 && target.itemView.getRight() > selected.itemView.getRight()) {
                        int abs = Math.abs(diff);
                        int score = abs;
                        if (abs > winnerScore) {
                            winnerScore = score;
                            winner = target;
                        }
                    }
                }
                if (dx < 0) {
                    int left = target.itemView.getLeft() - curX;
                    int diff2 = left;
                    if (left > 0 && target.itemView.getLeft() < selected.itemView.getLeft()) {
                        int abs2 = Math.abs(diff2);
                        int score2 = abs2;
                        if (abs2 > winnerScore) {
                            winnerScore = score2;
                            winner = target;
                        }
                    }
                }
                if (dy < 0) {
                    int top = target.itemView.getTop() - curY;
                    int diff3 = top;
                    if (top > 0 && target.itemView.getTop() < selected.itemView.getTop()) {
                        int abs3 = Math.abs(diff3);
                        int score3 = abs3;
                        if (abs3 > winnerScore) {
                            winnerScore = score3;
                            winner = target;
                        }
                    }
                }
                if (dy > 0) {
                    int bottom2 = target.itemView.getBottom() - bottom;
                    int diff4 = bottom2;
                    if (bottom2 < 0 && target.itemView.getBottom() > selected.itemView.getBottom()) {
                        int abs4 = Math.abs(diff4);
                        int score4 = abs4;
                        if (abs4 > winnerScore) {
                            winnerScore = score4;
                            winner = target;
                        }
                    }
                }
            }
            return winner;
        }

        public void onSelectedChanged(ViewHolder viewHolder, int i) {
            ViewHolder viewHolder2 = viewHolder;
            ViewHolder viewHolder3 = viewHolder2;
            int i2 = i;
            if (viewHolder2 != null) {
                sUICallback.onSelected(viewHolder2.itemView);
            }
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            RecyclerView recyclerView2 = recyclerView;
            RecyclerView recyclerView3 = recyclerView2;
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView2.getResources().getDimensionPixelSize(C0271R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public void onMoved(RecyclerView recyclerView, ViewHolder viewHolder, int i, ViewHolder viewHolder2, int i2, int i3, int i4) {
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder3 = viewHolder;
            ViewHolder target = viewHolder2;
            int toPos = i2;
            int x = i3;
            int y = i4;
            RecyclerView recyclerView3 = recyclerView2;
            ViewHolder viewHolder4 = viewHolder3;
            int i5 = i;
            ViewHolder viewHolder5 = target;
            int i6 = toPos;
            int i7 = x;
            int i8 = y;
            LayoutManager layoutManager = recyclerView2.getLayoutManager();
            LayoutManager layoutManager2 = layoutManager;
            if (!(layoutManager instanceof ViewDropHandler)) {
                if (layoutManager2.canScrollHorizontally()) {
                    int decoratedLeft = layoutManager2.getDecoratedLeft(target.itemView);
                    int i9 = decoratedLeft;
                    if (decoratedLeft <= recyclerView2.getPaddingLeft()) {
                        recyclerView2.scrollToPosition(toPos);
                    }
                    int decoratedRight = layoutManager2.getDecoratedRight(target.itemView);
                    int i10 = decoratedRight;
                    if (decoratedRight >= recyclerView2.getWidth() - recyclerView2.getPaddingRight()) {
                        recyclerView2.scrollToPosition(toPos);
                    }
                }
                if (layoutManager2.canScrollVertically()) {
                    int decoratedTop = layoutManager2.getDecoratedTop(target.itemView);
                    int i11 = decoratedTop;
                    if (decoratedTop <= recyclerView2.getPaddingTop()) {
                        recyclerView2.scrollToPosition(toPos);
                    }
                    int decoratedBottom = layoutManager2.getDecoratedBottom(target.itemView);
                    int i12 = decoratedBottom;
                    if (decoratedBottom >= recyclerView2.getHeight() - recyclerView2.getPaddingBottom()) {
                        recyclerView2.scrollToPosition(toPos);
                    }
                }
                return;
            }
            ((ViewDropHandler) layoutManager2).prepareForDrop(viewHolder3.itemView, target.itemView, x, y);
        }

        /* access modifiers changed from: 0000 */
        public void onDraw(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, List<RecoverAnimation> list, int i, float f, float f2) {
            Canvas c = canvas;
            RecyclerView parent = recyclerView;
            ViewHolder selected = viewHolder;
            List<RecoverAnimation> recoverAnimationList = list;
            int actionState = i;
            float dX = f;
            float dY = f2;
            Canvas canvas2 = c;
            RecyclerView recyclerView2 = parent;
            ViewHolder viewHolder2 = selected;
            List<RecoverAnimation> list2 = recoverAnimationList;
            int i2 = actionState;
            float f3 = dX;
            float f4 = dY;
            int recoverAnimSize = recoverAnimationList.size();
            for (int i3 = 0; i3 < recoverAnimSize; i3++) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) recoverAnimationList.get(i3);
                RecoverAnimation anim = recoverAnimation;
                recoverAnimation.update();
                int save = c.save();
                int i4 = save;
                onChildDraw(c, parent, anim.mViewHolder, anim.f27mX, anim.f28mY, anim.mActionState, false);
                c.restoreToCount(save);
            }
            if (selected != null) {
                int save2 = c.save();
                int i5 = save2;
                onChildDraw(c, parent, selected, dX, dY, actionState, true);
                c.restoreToCount(save2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, List<RecoverAnimation> list, int i, float f, float f2) {
            Canvas c = canvas;
            RecyclerView parent = recyclerView;
            ViewHolder selected = viewHolder;
            List<RecoverAnimation> recoverAnimationList = list;
            int actionState = i;
            float dX = f;
            float dY = f2;
            Canvas canvas2 = c;
            RecyclerView recyclerView2 = parent;
            ViewHolder viewHolder2 = selected;
            List<RecoverAnimation> list2 = recoverAnimationList;
            int i2 = actionState;
            float f3 = dX;
            float f4 = dY;
            int recoverAnimSize = recoverAnimationList.size();
            for (int i3 = 0; i3 < recoverAnimSize; i3++) {
                RecoverAnimation anim = (RecoverAnimation) recoverAnimationList.get(i3);
                int save = c.save();
                int i4 = save;
                onChildDrawOver(c, parent, anim.mViewHolder, anim.f27mX, anim.f28mY, anim.mActionState, false);
                c.restoreToCount(save);
            }
            if (selected != null) {
                int save2 = c.save();
                int i5 = save2;
                onChildDrawOver(c, parent, selected, dX, dY, actionState, true);
                c.restoreToCount(save2);
            }
            boolean hasRunningAnimation = false;
            for (int i6 = recoverAnimSize - 1; i6 >= 0; i6--) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) recoverAnimationList.get(i6);
                RecoverAnimation anim2 = recoverAnimation;
                if (recoverAnimation.mEnded && !anim2.mIsPendingCleanup) {
                    Object remove = recoverAnimationList.remove(i6);
                } else if (!anim2.mEnded) {
                    hasRunningAnimation = true;
                }
            }
            if (hasRunningAnimation) {
                parent.invalidate();
            }
        }

        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            ViewHolder viewHolder2 = viewHolder;
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder3 = viewHolder2;
            sUICallback.clearView(viewHolder2.itemView);
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            Canvas c = canvas;
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder2 = viewHolder;
            float dX = f;
            float dY = f2;
            int actionState = i;
            Canvas canvas2 = c;
            RecyclerView recyclerView3 = recyclerView2;
            ViewHolder viewHolder3 = viewHolder2;
            float f3 = dX;
            float f4 = dY;
            int i2 = actionState;
            boolean isCurrentlyActive = z;
            sUICallback.onDraw(c, recyclerView2, viewHolder2.itemView, dX, dY, actionState, isCurrentlyActive);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            Canvas c = canvas;
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder2 = viewHolder;
            float dX = f;
            float dY = f2;
            int actionState = i;
            Canvas canvas2 = c;
            RecyclerView recyclerView3 = recyclerView2;
            ViewHolder viewHolder3 = viewHolder2;
            float f3 = dX;
            float f4 = dY;
            int i2 = actionState;
            boolean isCurrentlyActive = z;
            sUICallback.onDrawOver(c, recyclerView2, viewHolder2.itemView, dX, dY, actionState, isCurrentlyActive);
        }

        public long getAnimationDuration(RecyclerView recyclerView, int i, float f, float f2) {
            long moveDuration;
            RecyclerView recyclerView2 = recyclerView;
            int animationType = i;
            RecyclerView recyclerView3 = recyclerView2;
            int i2 = animationType;
            float f3 = f;
            float f4 = f2;
            ItemAnimator itemAnimator = recyclerView2.getItemAnimator();
            ItemAnimator itemAnimator2 = itemAnimator;
            if (itemAnimator != null) {
                if (animationType != 8) {
                    moveDuration = itemAnimator2.getRemoveDuration();
                } else {
                    moveDuration = itemAnimator2.getMoveDuration();
                }
                return moveDuration;
            }
            return animationType != 8 ? 250 : 200;
        }

        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, int i3, long j) {
            float timeRatio;
            RecyclerView recyclerView2 = recyclerView;
            int viewSize = i;
            int viewSizeOutOfBounds = i2;
            long msSinceStartScroll = j;
            RecyclerView recyclerView3 = recyclerView2;
            int i4 = viewSize;
            int i5 = viewSizeOutOfBounds;
            int i6 = i3;
            long j2 = msSinceStartScroll;
            int signum = (int) Math.signum((float) viewSizeOutOfBounds);
            int i7 = signum;
            int maxDragScroll = (int) (((float) (signum * getMaxDragScroll(recyclerView2))) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (1.0f * ((float) Math.abs(viewSizeOutOfBounds))) / ((float) viewSize))));
            int i8 = maxDragScroll;
            if (!(msSinceStartScroll <= DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS)) {
                timeRatio = 1.0f;
            } else {
                timeRatio = ((float) msSinceStartScroll) / 2000.0f;
            }
            int interpolation = (int) (((float) maxDragScroll) * sDragScrollInterpolator.getInterpolation(timeRatio));
            int value = interpolation;
            if (interpolation != 0) {
                return value;
            }
            return viewSizeOutOfBounds <= 0 ? -1 : 1;
        }
    }

    /* renamed from: android.support.v7.widget.helper.ItemTouchHelper$ItemTouchHelperGestureListener */
    private class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
        final /* synthetic */ ItemTouchHelper this$0;

        ItemTouchHelperGestureListener(ItemTouchHelper itemTouchHelper) {
            this.this$0 = itemTouchHelper;
        }

        public boolean onDown(MotionEvent motionEvent) {
            MotionEvent motionEvent2 = motionEvent;
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            MotionEvent e = motionEvent;
            MotionEvent motionEvent2 = e;
            View findChildView = this.this$0.findChildView(e);
            View child = findChildView;
            if (findChildView != null) {
                ViewHolder childViewHolder = this.this$0.mRecyclerView.getChildViewHolder(child);
                ViewHolder vh = childViewHolder;
                if (childViewHolder != null && this.this$0.mCallback.hasDragFlag(this.this$0.mRecyclerView, vh)) {
                    int pointerId = e.getPointerId(0);
                    int i = pointerId;
                    if (pointerId == this.this$0.mActivePointerId) {
                        int findPointerIndex = e.findPointerIndex(this.this$0.mActivePointerId);
                        int i2 = findPointerIndex;
                        float x = e.getX(findPointerIndex);
                        float y = e.getY(findPointerIndex);
                        float f = y;
                        this.this$0.mInitialTouchX = x;
                        this.this$0.mInitialTouchY = y;
                        ItemTouchHelper itemTouchHelper = this.this$0;
                        this.this$0.mDy = 0.0f;
                        itemTouchHelper.mDx = 0.0f;
                        if (this.this$0.mCallback.isLongPressDragEnabled()) {
                            this.this$0.select(vh, 2);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: android.support.v7.widget.helper.ItemTouchHelper$RecoverAnimation */
    private class RecoverAnimation implements AnimatorListenerCompat {
        final int mActionState;
        final int mAnimationType;
        boolean mEnded = false;
        private float mFraction;
        public boolean mIsPendingCleanup;
        boolean mOverridden = false;
        final float mStartDx;
        final float mStartDy;
        final float mTargetX;
        final float mTargetY;
        private final ValueAnimatorCompat mValueAnimator;
        final ViewHolder mViewHolder;

        /* renamed from: mX */
        float f27mX;

        /* renamed from: mY */
        float f28mY;
        final /* synthetic */ ItemTouchHelper this$0;

        public RecoverAnimation(ItemTouchHelper itemTouchHelper, ViewHolder viewHolder, int i, int i2, float f, float f2, float f3, float f4) {
            ItemTouchHelper itemTouchHelper2 = itemTouchHelper;
            ViewHolder viewHolder2 = viewHolder;
            int animationType = i;
            int actionState = i2;
            float startDx = f;
            float startDy = f2;
            float targetX = f3;
            float targetY = f4;
            ViewHolder viewHolder3 = viewHolder2;
            int i3 = animationType;
            int i4 = actionState;
            float f5 = startDx;
            float f6 = startDy;
            float f7 = targetX;
            float f8 = targetY;
            this.this$0 = itemTouchHelper2;
            this.mActionState = actionState;
            this.mAnimationType = animationType;
            this.mViewHolder = viewHolder2;
            this.mStartDx = startDx;
            this.mStartDy = startDy;
            this.mTargetX = targetX;
            this.mTargetY = targetY;
            this.mValueAnimator = AnimatorCompatHelper.emptyValueAnimator();
            ValueAnimatorCompat valueAnimatorCompat = this.mValueAnimator;
            final ItemTouchHelper itemTouchHelper3 = itemTouchHelper2;
            C03661 r0 = new AnimatorUpdateListenerCompat(this) {
                final /* synthetic */ RecoverAnimation this$1;

                {
                    RecoverAnimation this$12 = r6;
                    ItemTouchHelper itemTouchHelper = r7;
                    RecoverAnimation recoverAnimation = this$12;
                    this.this$1 = this$12;
                }

                public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                    ValueAnimatorCompat animation = valueAnimatorCompat;
                    ValueAnimatorCompat valueAnimatorCompat2 = animation;
                    this.this$1.setFraction(animation.getAnimatedFraction());
                }
            };
            valueAnimatorCompat.addUpdateListener(r0);
            this.mValueAnimator.setTarget(viewHolder2.itemView);
            this.mValueAnimator.addListener(this);
            setFraction(0.0f);
        }

        public void setDuration(long j) {
            long duration = j;
            long j2 = duration;
            this.mValueAnimator.setDuration(duration);
        }

        public void start() {
            this.mViewHolder.setIsRecyclable(false);
            this.mValueAnimator.start();
        }

        public void cancel() {
            this.mValueAnimator.cancel();
        }

        public void setFraction(float f) {
            float fraction = f;
            float f2 = fraction;
            this.mFraction = fraction;
        }

        public void update() {
            if (this.mStartDx == this.mTargetX) {
                this.f27mX = ViewCompat.getTranslationX(this.mViewHolder.itemView);
            } else {
                this.f27mX = this.mStartDx + (this.mFraction * (this.mTargetX - this.mStartDx));
            }
            if (this.mStartDy == this.mTargetY) {
                this.f28mY = ViewCompat.getTranslationY(this.mViewHolder.itemView);
            } else {
                this.f28mY = this.mStartDy + (this.mFraction * (this.mTargetY - this.mStartDy));
            }
        }

        public void onAnimationStart(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
        }

        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        public void onAnimationCancel(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
            setFraction(1.0f);
        }

        public void onAnimationRepeat(ValueAnimatorCompat valueAnimatorCompat) {
            ValueAnimatorCompat valueAnimatorCompat2 = valueAnimatorCompat;
        }
    }

    /* renamed from: android.support.v7.widget.helper.ItemTouchHelper$SimpleCallback */
    public static abstract class SimpleCallback extends Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int i, int i2) {
            int dragDirs = i;
            int swipeDirs = i2;
            int i3 = dragDirs;
            int i4 = swipeDirs;
            this.mDefaultSwipeDirs = swipeDirs;
            this.mDefaultDragDirs = dragDirs;
        }

        public void setDefaultSwipeDirs(int i) {
            int defaultSwipeDirs = i;
            int i2 = defaultSwipeDirs;
            this.mDefaultSwipeDirs = defaultSwipeDirs;
        }

        public void setDefaultDragDirs(int i) {
            int defaultDragDirs = i;
            int i2 = defaultDragDirs;
            this.mDefaultDragDirs = defaultDragDirs;
        }

        public int getSwipeDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder2 = viewHolder;
            return this.mDefaultSwipeDirs;
        }

        public int getDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder2 = viewHolder;
            return this.mDefaultDragDirs;
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            RecyclerView recyclerView2 = recyclerView;
            ViewHolder viewHolder2 = viewHolder;
            RecyclerView recyclerView3 = recyclerView2;
            ViewHolder viewHolder3 = viewHolder2;
            return makeMovementFlags(getDragDirs(recyclerView2, viewHolder2), getSwipeDirs(recyclerView2, viewHolder2));
        }
    }

    /* renamed from: android.support.v7.widget.helper.ItemTouchHelper$ViewDropHandler */
    public interface ViewDropHandler {
        void prepareForDrop(View view, View view2, int i, int i2);
    }

    public ItemTouchHelper(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        this.mCallback = callback2;
    }

    private static boolean hitTest(View view, float f, float f2, float f3, float f4) {
        View child = view;
        float x = f;
        float y = f2;
        float left = f3;
        float top = f4;
        View view2 = child;
        float f5 = x;
        float f6 = y;
        float f7 = left;
        float f8 = top;
        return x >= left && x <= left + ((float) child.getWidth()) && y >= top && y <= top + ((float) child.getHeight());
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        RecyclerView recyclerView2 = recyclerView;
        RecyclerView recyclerView3 = recyclerView2;
        if (this.mRecyclerView != recyclerView2) {
            if (this.mRecyclerView != null) {
                destroyCallbacks();
            }
            this.mRecyclerView = recyclerView2;
            if (this.mRecyclerView != null) {
                Resources resources = recyclerView2.getResources();
                this.mSwipeEscapeVelocity = resources.getDimension(C0271R.dimen.item_touch_helper_swipe_escape_velocity);
                this.mMaxSwipeVelocity = resources.getDimension(C0271R.dimen.item_touch_helper_swipe_escape_max_velocity);
                setupCallbacks();
            }
        }
    }

    private void setupCallbacks() {
        this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this);
        initGestureDetector();
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
        int size = this.mRecoverAnimations.size();
        int i = size;
        for (int i2 = size - 1; i2 >= 0; i2--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.mRecoverAnimations.get(0);
            this.mCallback.clearView(this.mRecyclerView, recoverAnimation.mViewHolder);
        }
        this.mRecoverAnimations.clear();
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        releaseVelocityTracker();
    }

    private void initGestureDetector() {
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), new ItemTouchHelperGestureListener(this));
        }
    }

    private void getSelectedDxDy(float[] fArr) {
        float[] outPosition = fArr;
        float[] fArr2 = outPosition;
        if ((this.mSelectedFlags & 12) == 0) {
            outPosition[0] = ViewCompat.getTranslationX(this.mSelected.itemView);
        } else {
            outPosition[0] = (this.mSelectedStartX + this.mDx) - ((float) this.mSelected.itemView.getLeft());
        }
        if ((this.mSelectedFlags & 3) == 0) {
            outPosition[1] = ViewCompat.getTranslationY(this.mSelected.itemView);
            return;
        }
        outPosition[1] = (this.mSelectedStartY + this.mDy) - ((float) this.mSelected.itemView.getTop());
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        Canvas c = canvas;
        RecyclerView parent = recyclerView;
        Canvas canvas2 = c;
        RecyclerView recyclerView2 = parent;
        State state2 = state;
        float dx = 0.0f;
        float dy = 0.0f;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            dx = this.mTmpPosition[0];
            dy = this.mTmpPosition[1];
        }
        this.mCallback.onDrawOver(c, parent, this.mSelected, this.mRecoverAnimations, this.mActionState, dx, dy);
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        Canvas c = canvas;
        RecyclerView parent = recyclerView;
        Canvas canvas2 = c;
        RecyclerView recyclerView2 = parent;
        State state2 = state;
        this.mOverdrawChildPosition = -1;
        float dx = 0.0f;
        float dy = 0.0f;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            dx = this.mTmpPosition[0];
            dy = this.mTmpPosition[1];
        }
        this.mCallback.onDraw(c, parent, this.mSelected, this.mRecoverAnimations, this.mActionState, dx, dy);
    }

    /* access modifiers changed from: 0000 */
    public void select(ViewHolder viewHolder, int i) {
        int i2;
        float targetTranslateY;
        float targetTranslateX;
        int animationType;
        ViewHolder selected = viewHolder;
        int actionState = i;
        ViewHolder viewHolder2 = selected;
        int i3 = actionState;
        if (selected != this.mSelected || actionState != this.mActionState) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            int prevActionState = this.mActionState;
            int endRecoverAnimation = endRecoverAnimation(selected, true);
            this.mActionState = actionState;
            if (actionState == 2) {
                this.mOverdrawChild = selected.itemView;
                addChildDrawingOrderCallback();
            }
            int actionStateMask = (1 << (8 + (8 * actionState))) - 1;
            boolean preventLayout = false;
            if (this.mSelected != null) {
                ViewHolder viewHolder3 = this.mSelected;
                ViewHolder prevSelected = viewHolder3;
                if (viewHolder3.itemView.getParent() == null) {
                    removeChildDrawingOrderCallbackIfNecessary(prevSelected.itemView);
                    this.mCallback.clearView(this.mRecyclerView, prevSelected);
                } else {
                    if (prevActionState != 2) {
                        i2 = swipeIfNecessary(prevSelected);
                    } else {
                        i2 = 0;
                    }
                    int swipeDir = i2;
                    releaseVelocityTracker();
                    switch (swipeDir) {
                        case 1:
                        case 2:
                            targetTranslateX = 0.0f;
                            targetTranslateY = Math.signum(this.mDy) * ((float) this.mRecyclerView.getHeight());
                            break;
                        case 4:
                        case 8:
                        case 16:
                        case 32:
                            targetTranslateY = 0.0f;
                            targetTranslateX = Math.signum(this.mDx) * ((float) this.mRecyclerView.getWidth());
                            break;
                        default:
                            targetTranslateX = 0.0f;
                            targetTranslateY = 0.0f;
                            break;
                    }
                    if (prevActionState == 2) {
                        animationType = 8;
                    } else if (swipeDir <= 0) {
                        animationType = 4;
                    } else {
                        animationType = 2;
                    }
                    getSelectedDxDy(this.mTmpPosition);
                    float currentTranslateX = this.mTmpPosition[0];
                    float f = this.mTmpPosition[1];
                    float f2 = f;
                    C03613 r0 = new RecoverAnimation(this, prevSelected, animationType, prevActionState, currentTranslateX, f, targetTranslateX, targetTranslateY, swipeDir, prevSelected) {
                        final /* synthetic */ ItemTouchHelper this$0;
                        final /* synthetic */ ViewHolder val$prevSelected;
                        final /* synthetic */ int val$swipeDir;

                        {
                            ItemTouchHelper this$02 = r32;
                            ViewHolder viewHolder = r33;
                            int animationType = r34;
                            int actionState = r35;
                            float startDx = r36;
                            float startDy = r37;
                            float targetX = r38;
                            float targetY = r39;
                            int i = r40;
                            ViewHolder viewHolder2 = r41;
                            ItemTouchHelper itemTouchHelper = this$02;
                            ViewHolder viewHolder3 = viewHolder;
                            int i2 = animationType;
                            int i3 = actionState;
                            float f = startDx;
                            float f2 = startDy;
                            float f3 = targetX;
                            float f4 = targetY;
                            this.this$0 = this$02;
                            this.val$swipeDir = i;
                            this.val$prevSelected = viewHolder2;
                        }

                        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
                            ValueAnimatorCompat animation = valueAnimatorCompat;
                            ValueAnimatorCompat valueAnimatorCompat2 = animation;
                            super.onAnimationEnd(animation);
                            if (!this.mOverridden) {
                                if (this.val$swipeDir > 0) {
                                    boolean add = this.this$0.mPendingCleanup.add(this.val$prevSelected.itemView);
                                    this.mIsPendingCleanup = true;
                                    if (this.val$swipeDir > 0) {
                                        this.this$0.postDispatchSwipe(this, this.val$swipeDir);
                                    }
                                } else {
                                    this.this$0.mCallback.clearView(this.this$0.mRecyclerView, this.val$prevSelected);
                                }
                                if (this.this$0.mOverdrawChild == this.val$prevSelected.itemView) {
                                    this.this$0.removeChildDrawingOrderCallbackIfNecessary(this.val$prevSelected.itemView);
                                }
                            }
                        }
                    };
                    C03613 r48 = r0;
                    long animationDuration = this.mCallback.getAnimationDuration(this.mRecyclerView, animationType, targetTranslateX - currentTranslateX, targetTranslateY - f);
                    long j = animationDuration;
                    r48.setDuration(animationDuration);
                    boolean add = this.mRecoverAnimations.add(r48);
                    r48.start();
                    preventLayout = true;
                }
                this.mSelected = null;
            }
            if (selected != null) {
                this.mSelectedFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, selected) & actionStateMask) >> (this.mActionState * 8);
                this.mSelectedStartX = (float) selected.itemView.getLeft();
                this.mSelectedStartY = (float) selected.itemView.getTop();
                this.mSelected = selected;
                if (actionState == 2) {
                    boolean performHapticFeedback = this.mSelected.itemView.performHapticFeedback(0);
                }
            }
            ViewParent parent = this.mRecyclerView.getParent();
            ViewParent rvParent = parent;
            if (parent != null) {
                rvParent.requestDisallowInterceptTouchEvent(this.mSelected != null);
            }
            if (!preventLayout) {
                this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
            }
            this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
            this.mRecyclerView.invalidate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void postDispatchSwipe(RecoverAnimation recoverAnimation, int i) {
        RecoverAnimation anim = recoverAnimation;
        final int swipeDir = i;
        RecoverAnimation recoverAnimation2 = anim;
        int i2 = swipeDir;
        final RecoverAnimation recoverAnimation3 = anim;
        boolean post = this.mRecyclerView.post(new Runnable(this) {
            final /* synthetic */ ItemTouchHelper this$0;

            {
                ItemTouchHelper this$02 = r7;
                RecoverAnimation recoverAnimation = r8;
                int i = r9;
                ItemTouchHelper itemTouchHelper = this$02;
                this.this$0 = this$02;
            }

            public void run() {
                if (this.this$0.mRecyclerView != null && this.this$0.mRecyclerView.isAttachedToWindow() && !recoverAnimation3.mOverridden && recoverAnimation3.mViewHolder.getAdapterPosition() != -1) {
                    ItemAnimator itemAnimator = this.this$0.mRecyclerView.getItemAnimator();
                    ItemAnimator animator = itemAnimator;
                    if ((itemAnimator != null && animator.isRunning(null)) || this.this$0.hasRunningRecoverAnim()) {
                        boolean post = this.this$0.mRecyclerView.post(this);
                        return;
                    }
                    this.this$0.mCallback.onSwiped(recoverAnimation3.mViewHolder, swipeDir);
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public boolean hasRunningRecoverAnim() {
        int size = this.mRecoverAnimations.size();
        for (int i = 0; i < size; i++) {
            if (!((RecoverAnimation) this.mRecoverAnimations.get(i)).mEnded) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean scrollIfNecessary() {
        if (this.mSelected != null) {
            long now = System.currentTimeMillis();
            long scrollDuration = this.mDragScrollStartTimeInMs == Long.MIN_VALUE ? 0 : now - this.mDragScrollStartTimeInMs;
            LayoutManager lm = this.mRecyclerView.getLayoutManager();
            if (this.mTmpRect == null) {
                this.mTmpRect = new Rect();
            }
            int scrollX = 0;
            int scrollY = 0;
            lm.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
            if (lm.canScrollHorizontally()) {
                int i = (int) (this.mSelectedStartX + this.mDx);
                int curX = i;
                int paddingLeft = (i - this.mTmpRect.left) - this.mRecyclerView.getPaddingLeft();
                int leftDiff = paddingLeft;
                if (this.mDx < 0.0f && paddingLeft < 0) {
                    scrollX = leftDiff;
                } else if (this.mDx > 0.0f) {
                    int width = ((curX + this.mSelected.itemView.getWidth()) + this.mTmpRect.right) - (this.mRecyclerView.getWidth() - this.mRecyclerView.getPaddingRight());
                    int rightDiff = width;
                    if (width > 0) {
                        scrollX = rightDiff;
                    }
                }
            }
            if (lm.canScrollVertically()) {
                int i2 = (int) (this.mSelectedStartY + this.mDy);
                int curY = i2;
                int paddingTop = (i2 - this.mTmpRect.top) - this.mRecyclerView.getPaddingTop();
                int topDiff = paddingTop;
                if (this.mDy < 0.0f && paddingTop < 0) {
                    scrollY = topDiff;
                } else if (this.mDy > 0.0f) {
                    int height = ((curY + this.mSelected.itemView.getHeight()) + this.mTmpRect.bottom) - (this.mRecyclerView.getHeight() - this.mRecyclerView.getPaddingBottom());
                    int bottomDiff = height;
                    if (height > 0) {
                        scrollY = bottomDiff;
                    }
                }
            }
            if (scrollX != 0) {
                scrollX = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getWidth(), scrollX, this.mRecyclerView.getWidth(), scrollDuration);
            }
            if (scrollY != 0) {
                scrollY = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getHeight(), scrollY, this.mRecyclerView.getHeight(), scrollDuration);
            }
            if (scrollX == 0 && scrollY == 0) {
                this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
                return false;
            }
            if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
                this.mDragScrollStartTimeInMs = now;
            }
            this.mRecyclerView.scrollBy(scrollX, scrollY);
            return true;
        }
        this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
        return false;
    }

    private List<ViewHolder> findSwapTargets(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        if (this.mSwapTargets != null) {
            this.mSwapTargets.clear();
            this.mDistances.clear();
        } else {
            this.mSwapTargets = new ArrayList();
            this.mDistances = new ArrayList();
        }
        int margin = this.mCallback.getBoundingBoxMargin();
        int left = Math.round(this.mSelectedStartX + this.mDx) - margin;
        int top = Math.round(this.mSelectedStartY + this.mDy) - margin;
        int right = left + viewHolder2.itemView.getWidth() + (2 * margin);
        int bottom = top + viewHolder2.itemView.getHeight() + (2 * margin);
        int centerX = (left + right) / 2;
        int centerY = (top + bottom) / 2;
        LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        LayoutManager lm = layoutManager;
        int childCount = layoutManager.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = lm.getChildAt(i);
            View other = childAt;
            if (childAt != viewHolder2.itemView && other.getBottom() >= top && other.getTop() <= bottom && other.getRight() >= left && other.getLeft() <= right) {
                ViewHolder otherVh = this.mRecyclerView.getChildViewHolder(other);
                if (this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, otherVh)) {
                    int dx = Math.abs(centerX - ((other.getLeft() + other.getRight()) / 2));
                    int dy = Math.abs(centerY - ((other.getTop() + other.getBottom()) / 2));
                    int dist = (dx * dx) + (dy * dy);
                    int pos = 0;
                    int cnt = this.mSwapTargets.size();
                    int j = 0;
                    while (j < cnt && dist > ((Integer) this.mDistances.get(j)).intValue()) {
                        pos++;
                        j++;
                    }
                    this.mSwapTargets.add(pos, otherVh);
                    this.mDistances.add(pos, Integer.valueOf(dist));
                }
            }
        }
        return this.mSwapTargets;
    }

    /* access modifiers changed from: 0000 */
    public void moveIfNecessary(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            float threshold = this.mCallback.getMoveThreshold(viewHolder2);
            int x = (int) (this.mSelectedStartX + this.mDx);
            int i = (int) (this.mSelectedStartY + this.mDy);
            int y = i;
            if (((float) Math.abs(i - viewHolder2.itemView.getTop())) >= ((float) viewHolder2.itemView.getHeight()) * threshold || ((float) Math.abs(x - viewHolder2.itemView.getLeft())) >= ((float) viewHolder2.itemView.getWidth()) * threshold) {
                List findSwapTargets = findSwapTargets(viewHolder2);
                List list = findSwapTargets;
                if (findSwapTargets.size() != 0) {
                    ViewHolder chooseDropTarget = this.mCallback.chooseDropTarget(viewHolder2, list, x, y);
                    ViewHolder target = chooseDropTarget;
                    if (chooseDropTarget != null) {
                        int toPosition = target.getAdapterPosition();
                        int fromPosition = viewHolder2.getAdapterPosition();
                        if (this.mCallback.onMove(this.mRecyclerView, viewHolder2, target)) {
                            this.mCallback.onMoved(this.mRecyclerView, viewHolder2, fromPosition, target, toPosition, x, y);
                        }
                        return;
                    }
                    this.mSwapTargets.clear();
                    this.mDistances.clear();
                }
            }
        }
    }

    public void onChildViewAttachedToWindow(View view) {
        View view2 = view;
    }

    public void onChildViewDetachedFromWindow(View view) {
        View view2 = view;
        View view3 = view2;
        removeChildDrawingOrderCallbackIfNecessary(view2);
        ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(view2);
        ViewHolder holder = childViewHolder;
        if (childViewHolder != null) {
            if (this.mSelected != null && holder == this.mSelected) {
                select(null, 0);
            } else {
                int endRecoverAnimation = endRecoverAnimation(holder, false);
                if (this.mPendingCleanup.remove(holder.itemView)) {
                    this.mCallback.clearView(this.mRecyclerView, holder);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int endRecoverAnimation(ViewHolder viewHolder, boolean z) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        boolean override = z;
        int size = this.mRecoverAnimations.size();
        int i = size;
        int i2 = size - 1;
        while (i2 >= 0) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.mRecoverAnimations.get(i2);
            RecoverAnimation anim = recoverAnimation;
            if (recoverAnimation.mViewHolder != viewHolder2) {
                i2--;
            } else {
                anim.mOverridden |= override;
                if (!anim.mEnded) {
                    anim.cancel();
                }
                Object remove = this.mRecoverAnimations.remove(i2);
                return anim.mAnimationType;
            }
        }
        return 0;
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        Rect outRect = rect;
        Rect rect2 = outRect;
        View view2 = view;
        RecyclerView recyclerView2 = recyclerView;
        State state2 = state;
        outRect.setEmpty();
    }

    /* access modifiers changed from: 0000 */
    public void obtainVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    private void releaseVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private ViewHolder findSwipedView(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        MotionEvent motionEvent3 = motionEvent2;
        LayoutManager lm = this.mRecyclerView.getLayoutManager();
        if (this.mActivePointerId == -1) {
            return null;
        }
        int findPointerIndex = motionEvent2.findPointerIndex(this.mActivePointerId);
        int i = findPointerIndex;
        float dy = motionEvent2.getY(findPointerIndex) - this.mInitialTouchY;
        float absDx = Math.abs(motionEvent2.getX(findPointerIndex) - this.mInitialTouchX);
        float absDy = Math.abs(dy);
        if (absDx < ((float) this.mSlop) && absDy < ((float) this.mSlop)) {
            return null;
        }
        if (absDx > absDy && lm.canScrollHorizontally()) {
            return null;
        }
        if (absDy > absDx && lm.canScrollVertically()) {
            return null;
        }
        View findChildView = findChildView(motionEvent2);
        View child = findChildView;
        if (findChildView != null) {
            return this.mRecyclerView.getChildViewHolder(child);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public boolean checkSelectForSwipe(int i, MotionEvent motionEvent, int i2) {
        int action = i;
        MotionEvent motionEvent2 = motionEvent;
        int pointerIndex = i2;
        int i3 = action;
        MotionEvent motionEvent3 = motionEvent2;
        int i4 = pointerIndex;
        if (this.mSelected != null || action != 2 || this.mActionState == 2 || !this.mCallback.isItemViewSwipeEnabled()) {
            return false;
        }
        if (this.mRecyclerView.getScrollState() == 1) {
            return false;
        }
        ViewHolder findSwipedView = findSwipedView(motionEvent2);
        ViewHolder vh = findSwipedView;
        if (findSwipedView == null) {
            return false;
        }
        int absoluteMovementFlags = this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, vh);
        int i5 = absoluteMovementFlags;
        int i6 = (absoluteMovementFlags & 65280) >> 8;
        int swipeFlags = i6;
        if (i6 == 0) {
            return false;
        }
        float x = motionEvent2.getX(pointerIndex);
        float dx = x - this.mInitialTouchX;
        float dy = motionEvent2.getY(pointerIndex) - this.mInitialTouchY;
        float absDx = Math.abs(dx);
        float absDy = Math.abs(dy);
        if (absDx < ((float) this.mSlop) && absDy < ((float) this.mSlop)) {
            return false;
        }
        if (absDx > absDy) {
            if (dx < 0.0f && (swipeFlags & 4) == 0) {
                return false;
            }
            if (dx > 0.0f && (swipeFlags & 8) == 0) {
                return false;
            }
        } else if (dy < 0.0f && (swipeFlags & 1) == 0) {
            return false;
        } else {
            if (dy > 0.0f && (swipeFlags & 2) == 0) {
                return false;
            }
        }
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        this.mActivePointerId = motionEvent2.getPointerId(0);
        select(vh, 1);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public View findChildView(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        float x = event.getX();
        float y = event.getY();
        float f = y;
        if (this.mSelected != null) {
            View view = this.mSelected.itemView;
            View selectedView = view;
            if (hitTest(view, x, y, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
                return selectedView;
            }
        }
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.mRecoverAnimations.get(i);
            RecoverAnimation anim = recoverAnimation;
            View view2 = recoverAnimation.mViewHolder.itemView;
            View view3 = view2;
            if (hitTest(view2, x, y, anim.f27mX, anim.f28mY)) {
                return view3;
            }
        }
        return this.mRecyclerView.findChildViewUnder(x, y);
    }

    public void startDrag(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        if (!this.mCallback.hasDragFlag(this.mRecyclerView, viewHolder2)) {
            int e = Log.e(TAG, "Start drag has been called but swiping is not enabled");
        } else if (viewHolder2.itemView.getParent() == this.mRecyclerView) {
            obtainVelocityTracker();
            this.mDy = 0.0f;
            this.mDx = 0.0f;
            select(viewHolder2, 2);
        } else {
            int e2 = Log.e(TAG, "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
        }
    }

    public void startSwipe(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, viewHolder2)) {
            int e = Log.e(TAG, "Start swipe has been called but dragging is not enabled");
        } else if (viewHolder2.itemView.getParent() == this.mRecyclerView) {
            obtainVelocityTracker();
            this.mDy = 0.0f;
            this.mDx = 0.0f;
            select(viewHolder2, 1);
        } else {
            int e2 = Log.e(TAG, "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
        }
    }

    /* access modifiers changed from: 0000 */
    public RecoverAnimation findAnimation(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        MotionEvent motionEvent2 = event;
        if (this.mRecoverAnimations.isEmpty()) {
            return null;
        }
        View target = findChildView(event);
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.mRecoverAnimations.get(i);
            RecoverAnimation anim = recoverAnimation;
            if (recoverAnimation.mViewHolder.itemView == target) {
                return anim;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void updateDxDy(MotionEvent motionEvent, int i, int i2) {
        MotionEvent ev = motionEvent;
        int directionFlags = i;
        int pointerIndex = i2;
        MotionEvent motionEvent2 = ev;
        int i3 = directionFlags;
        int i4 = pointerIndex;
        float x = ev.getX(pointerIndex);
        float y = ev.getY(pointerIndex);
        float f = y;
        this.mDx = x - this.mInitialTouchX;
        this.mDy = y - this.mInitialTouchY;
        if ((directionFlags & 4) == 0) {
            this.mDx = Math.max(0.0f, this.mDx);
        }
        if ((directionFlags & 8) == 0) {
            this.mDx = Math.min(0.0f, this.mDx);
        }
        if ((directionFlags & 1) == 0) {
            this.mDy = Math.max(0.0f, this.mDy);
        }
        if ((directionFlags & 2) == 0) {
            this.mDy = Math.min(0.0f, this.mDy);
        }
    }

    private int swipeIfNecessary(ViewHolder viewHolder) {
        ViewHolder viewHolder2 = viewHolder;
        ViewHolder viewHolder3 = viewHolder2;
        if (this.mActionState == 2) {
            return 0;
        }
        int movementFlags = this.mCallback.getMovementFlags(this.mRecyclerView, viewHolder2);
        int originalMovementFlags = movementFlags;
        int convertToAbsoluteDirection = this.mCallback.convertToAbsoluteDirection(movementFlags, ViewCompat.getLayoutDirection(this.mRecyclerView));
        int i = convertToAbsoluteDirection;
        int i2 = (convertToAbsoluteDirection & 65280) >> 8;
        int flags = i2;
        if (i2 == 0) {
            return 0;
        }
        int originalFlags = (originalMovementFlags & 65280) >> 8;
        if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
            int checkHorizontalSwipe = checkHorizontalSwipe(viewHolder2, flags);
            int swipeDir = checkHorizontalSwipe;
            if (checkHorizontalSwipe <= 0) {
                int checkVerticalSwipe = checkVerticalSwipe(viewHolder2, flags);
                int swipeDir2 = checkVerticalSwipe;
                if (checkVerticalSwipe > 0) {
                    return checkVerticalSwipe;
                }
            } else if ((originalFlags & swipeDir) != 0) {
                return swipeDir;
            } else {
                return Callback.convertToRelativeDirection(swipeDir, ViewCompat.getLayoutDirection(this.mRecyclerView));
            }
        } else {
            int checkVerticalSwipe2 = checkVerticalSwipe(viewHolder2, flags);
            int i3 = checkVerticalSwipe2;
            if (checkVerticalSwipe2 > 0) {
                return checkVerticalSwipe2;
            }
            int checkHorizontalSwipe2 = checkHorizontalSwipe(viewHolder2, flags);
            int swipeDir3 = checkHorizontalSwipe2;
            if (checkHorizontalSwipe2 > 0) {
                if ((originalFlags & swipeDir3) != 0) {
                    return swipeDir3;
                }
                return Callback.convertToRelativeDirection(swipeDir3, ViewCompat.getLayoutDirection(this.mRecyclerView));
            }
        }
        return 0;
    }

    private int checkHorizontalSwipe(ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = viewHolder;
        int flags = i;
        ViewHolder viewHolder3 = viewHolder2;
        int i2 = flags;
        if ((flags & 12) != 0) {
            int dirFlag = this.mDx > 0.0f ? 8 : 4;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                float xVelocity = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
                float yVelocity = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                int velDirFlag = xVelocity > 0.0f ? 8 : 4;
                float abs = Math.abs(xVelocity);
                float absXVelocity = abs;
                if ((velDirFlag & flags) != 0 && dirFlag == velDirFlag) {
                    if (abs >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && absXVelocity > Math.abs(yVelocity)) {
                        return velDirFlag;
                    }
                }
            }
            float threshold = ((float) this.mRecyclerView.getWidth()) * this.mCallback.getSwipeThreshold(viewHolder2);
            if ((flags & dirFlag) != 0 && Math.abs(this.mDx) > threshold) {
                return dirFlag;
            }
        }
        return 0;
    }

    private int checkVerticalSwipe(ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = viewHolder;
        int flags = i;
        ViewHolder viewHolder3 = viewHolder2;
        int i2 = flags;
        if ((flags & 3) != 0) {
            int dirFlag = this.mDy > 0.0f ? 2 : 1;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                float xVelocity = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
                float yVelocity = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                int velDirFlag = yVelocity > 0.0f ? 2 : 1;
                float abs = Math.abs(yVelocity);
                float absYVelocity = abs;
                if ((velDirFlag & flags) != 0 && velDirFlag == dirFlag) {
                    if (abs >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && absYVelocity > Math.abs(xVelocity)) {
                        return velDirFlag;
                    }
                }
            }
            float threshold = ((float) this.mRecyclerView.getHeight()) * this.mCallback.getSwipeThreshold(viewHolder2);
            if ((flags & dirFlag) != 0 && Math.abs(this.mDy) > threshold) {
                return dirFlag;
            }
        }
        return 0;
    }

    private void addChildDrawingOrderCallback() {
        if (VERSION.SDK_INT < 21) {
            if (this.mChildDrawingOrderCallback == null) {
                this.mChildDrawingOrderCallback = new ChildDrawingOrderCallback(this) {
                    final /* synthetic */ ItemTouchHelper this$0;

                    {
                        ItemTouchHelper this$02 = r5;
                        ItemTouchHelper itemTouchHelper = this$02;
                        this.this$0 = this$02;
                    }

                    public int onGetChildDrawingOrder(int i, int i2) {
                        int i3;
                        int childCount = i;
                        int i4 = i2;
                        int i5 = childCount;
                        int i6 = i4;
                        if (this.this$0.mOverdrawChild == null) {
                            return i4;
                        }
                        int i7 = this.this$0.mOverdrawChildPosition;
                        int childPosition = i7;
                        if (i7 == -1) {
                            int indexOfChild = this.this$0.mRecyclerView.indexOfChild(this.this$0.mOverdrawChild);
                            childPosition = indexOfChild;
                            this.this$0.mOverdrawChildPosition = indexOfChild;
                        }
                        if (i4 == childCount - 1) {
                            return childPosition;
                        }
                        if (i4 >= childPosition) {
                            i3 = i4 + 1;
                        } else {
                            i3 = i4;
                        }
                        return i3;
                    }
                };
            }
            this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
        }
    }

    /* access modifiers changed from: 0000 */
    public void removeChildDrawingOrderCallbackIfNecessary(View view) {
        View view2 = view;
        View view3 = view2;
        if (view2 == this.mOverdrawChild) {
            this.mOverdrawChild = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.mRecyclerView.setChildDrawingOrderCallback(null);
            }
        }
    }
}
