package android.support.p003v7.widget;

import android.support.annotation.NonNull;
import android.support.p000v4.animation.AnimatorCompatHelper;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.ViewPropertyAnimatorCompat;
import android.support.p000v4.view.ViewPropertyAnimatorListener;
import android.support.p003v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: android.support.v7.widget.DefaultItemAnimator */
public class DefaultItemAnimator extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    ArrayList<ViewHolder> mAddAnimations = new ArrayList<>();
    ArrayList<ArrayList<ViewHolder>> mAdditionsList = new ArrayList<>();
    ArrayList<ViewHolder> mChangeAnimations = new ArrayList<>();
    ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList<>();
    ArrayList<ViewHolder> mMoveAnimations = new ArrayList<>();
    ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList<>();
    private ArrayList<ViewHolder> mPendingAdditions = new ArrayList<>();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList<>();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList<>();
    private ArrayList<ViewHolder> mPendingRemovals = new ArrayList<>();
    ArrayList<ViewHolder> mRemoveAnimations = new ArrayList<>();

    /* renamed from: android.support.v7.widget.DefaultItemAnimator$ChangeInfo */
    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public ViewHolder newHolder;
        public ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2) {
            ViewHolder oldHolder2 = viewHolder;
            ViewHolder newHolder2 = viewHolder2;
            ViewHolder viewHolder3 = oldHolder2;
            ViewHolder viewHolder4 = newHolder2;
            this.oldHolder = oldHolder2;
            this.newHolder = newHolder2;
        }

        ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
            ViewHolder oldHolder2 = viewHolder;
            ViewHolder newHolder2 = viewHolder2;
            int fromX2 = i;
            int fromY2 = i2;
            int toX2 = i3;
            int toY2 = i4;
            ViewHolder viewHolder3 = oldHolder2;
            ViewHolder viewHolder4 = newHolder2;
            int i5 = fromX2;
            int i6 = fromY2;
            int i7 = toX2;
            int i8 = toY2;
            this(oldHolder2, newHolder2);
            this.fromX = fromX2;
            this.fromY = fromY2;
            this.toX = toX2;
            this.toY = toY2;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator$MoveInfo */
    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public ViewHolder holder;
        public int toX;
        public int toY;

        MoveInfo(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
            ViewHolder holder2 = viewHolder;
            int fromX2 = i;
            int fromY2 = i2;
            int toX2 = i3;
            int toY2 = i4;
            ViewHolder viewHolder2 = holder2;
            int i5 = fromX2;
            int i6 = fromY2;
            int i7 = toX2;
            int i8 = toY2;
            this.holder = holder2;
            this.fromX = fromX2;
            this.fromY = fromY2;
            this.toX = toX2;
            this.toY = toY2;
        }
    }

    /* renamed from: android.support.v7.widget.DefaultItemAnimator$VpaListenerAdapter */
    private static class VpaListenerAdapter implements ViewPropertyAnimatorListener {
        VpaListenerAdapter() {
        }

        public void onAnimationStart(View view) {
            View view2 = view;
        }

        public void onAnimationEnd(View view) {
            View view2 = view;
        }

        public void onAnimationCancel(View view) {
            View view2 = view;
        }
    }

    public DefaultItemAnimator() {
    }

    public void runPendingAnimations() {
        boolean removalsPending = !this.mPendingRemovals.isEmpty();
        boolean movesPending = !this.mPendingMoves.isEmpty();
        boolean changesPending = !this.mPendingChanges.isEmpty();
        boolean additionsPending = !this.mPendingAdditions.isEmpty();
        if (removalsPending || movesPending || additionsPending || changesPending) {
            Iterator it = this.mPendingRemovals.iterator();
            while (it.hasNext()) {
                animateRemoveImpl((ViewHolder) it.next());
            }
            this.mPendingRemovals.clear();
            if (movesPending) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = arrayList;
                boolean addAll = arrayList.addAll(this.mPendingMoves);
                boolean add = this.mMovesList.add(arrayList2);
                this.mPendingMoves.clear();
                final ArrayList arrayList3 = arrayList2;
                C03041 r7 = new Runnable(this) {
                    final /* synthetic */ DefaultItemAnimator this$0;

                    {
                        DefaultItemAnimator this$02 = r6;
                        ArrayList arrayList = r7;
                        DefaultItemAnimator defaultItemAnimator = this$02;
                        this.this$0 = this$02;
                    }

                    public void run() {
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            MoveInfo moveInfo = (MoveInfo) it.next();
                            this.this$0.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                        }
                        arrayList3.clear();
                        boolean remove = this.this$0.mMovesList.remove(arrayList3);
                    }
                };
                C03041 r20 = r7;
                if (!removalsPending) {
                    r7.run();
                } else {
                    View view = ((MoveInfo) arrayList2.get(0)).holder.itemView;
                    View view2 = view;
                    ViewCompat.postOnAnimationDelayed(view, r20, getRemoveDuration());
                }
            }
            if (changesPending) {
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = arrayList4;
                boolean addAll2 = arrayList4.addAll(this.mPendingChanges);
                boolean add2 = this.mChangesList.add(arrayList5);
                this.mPendingChanges.clear();
                final ArrayList arrayList6 = arrayList5;
                C03052 r72 = new Runnable(this) {
                    final /* synthetic */ DefaultItemAnimator this$0;

                    {
                        DefaultItemAnimator this$02 = r6;
                        ArrayList arrayList = r7;
                        DefaultItemAnimator defaultItemAnimator = this$02;
                        this.this$0 = this$02;
                    }

                    public void run() {
                        Iterator it = arrayList6.iterator();
                        while (it.hasNext()) {
                            this.this$0.animateChangeImpl((ChangeInfo) it.next());
                        }
                        arrayList6.clear();
                        boolean remove = this.this$0.mChangesList.remove(arrayList6);
                    }
                };
                C03052 r202 = r72;
                if (!removalsPending) {
                    r72.run();
                } else {
                    ViewHolder viewHolder = ((ChangeInfo) arrayList5.get(0)).oldHolder;
                    ViewHolder viewHolder2 = viewHolder;
                    ViewCompat.postOnAnimationDelayed(viewHolder.itemView, r202, getRemoveDuration());
                }
            }
            if (additionsPending) {
                ArrayList arrayList7 = new ArrayList();
                ArrayList arrayList8 = arrayList7;
                boolean addAll3 = arrayList7.addAll(this.mPendingAdditions);
                boolean add3 = this.mAdditionsList.add(arrayList8);
                this.mPendingAdditions.clear();
                final ArrayList arrayList9 = arrayList8;
                C03063 r73 = new Runnable(this) {
                    final /* synthetic */ DefaultItemAnimator this$0;

                    {
                        DefaultItemAnimator this$02 = r6;
                        ArrayList arrayList = r7;
                        DefaultItemAnimator defaultItemAnimator = this$02;
                        this.this$0 = this$02;
                    }

                    public void run() {
                        Iterator it = arrayList9.iterator();
                        while (it.hasNext()) {
                            this.this$0.animateAddImpl((ViewHolder) it.next());
                        }
                        arrayList9.clear();
                        boolean remove = this.this$0.mAdditionsList.remove(arrayList9);
                    }
                };
                C03063 r203 = r73;
                if (!removalsPending && !movesPending && !changesPending) {
                    r73.run();
                } else {
                    long removeDuration = (!removalsPending ? 0 : getRemoveDuration()) + Math.max(!movesPending ? 0 : getMoveDuration(), !changesPending ? 0 : getChangeDuration());
                    long j = removeDuration;
                    View view3 = ((ViewHolder) arrayList8.get(0)).itemView;
                    View view4 = view3;
                    ViewCompat.postOnAnimationDelayed(view3, r203, removeDuration);
                }
            }
        }
    }

    public boolean animateRemove(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        resetAnimation(holder);
        boolean add = this.mPendingRemovals.add(holder);
        return true;
    }

    private void animateRemoveImpl(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        View view = holder.itemView;
        View view2 = view;
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        boolean add = this.mRemoveAnimations.add(holder);
        final ViewHolder viewHolder3 = holder;
        final ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = animation;
        animation.setDuration(getRemoveDuration()).alpha(0.0f).setListener(new VpaListenerAdapter(this) {
            final /* synthetic */ DefaultItemAnimator this$0;

            {
                DefaultItemAnimator this$02 = r7;
                ViewHolder viewHolder = r8;
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = r9;
                DefaultItemAnimator defaultItemAnimator = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(View view) {
                View view2 = view;
                this.this$0.dispatchRemoveStarting(viewHolder3);
            }

            public void onAnimationEnd(View view) {
                View view2 = view;
                View view3 = view2;
                ViewPropertyAnimatorCompat listener = viewPropertyAnimatorCompat.setListener(null);
                ViewCompat.setAlpha(view2, 1.0f);
                this.this$0.dispatchRemoveFinished(viewHolder3);
                boolean remove = this.this$0.mRemoveAnimations.remove(viewHolder3);
                this.this$0.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateAdd(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        resetAnimation(holder);
        ViewCompat.setAlpha(holder.itemView, 0.0f);
        boolean add = this.mPendingAdditions.add(holder);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void animateAddImpl(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        View view = holder.itemView;
        View view2 = view;
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        boolean add = this.mAddAnimations.add(holder);
        final ViewHolder viewHolder3 = holder;
        final ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = animation;
        animation.alpha(1.0f).setDuration(getAddDuration()).setListener(new VpaListenerAdapter(this) {
            final /* synthetic */ DefaultItemAnimator this$0;

            {
                DefaultItemAnimator this$02 = r7;
                ViewHolder viewHolder = r8;
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = r9;
                DefaultItemAnimator defaultItemAnimator = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(View view) {
                View view2 = view;
                this.this$0.dispatchAddStarting(viewHolder3);
            }

            public void onAnimationCancel(View view) {
                View view2 = view;
                View view3 = view2;
                ViewCompat.setAlpha(view2, 1.0f);
            }

            public void onAnimationEnd(View view) {
                View view2 = view;
                ViewPropertyAnimatorCompat listener = viewPropertyAnimatorCompat.setListener(null);
                this.this$0.dispatchAddFinished(viewHolder3);
                boolean remove = this.this$0.mAddAnimations.remove(viewHolder3);
                this.this$0.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateMove(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
        ViewHolder holder = viewHolder;
        int fromX = i;
        int fromY = i2;
        int toX = i3;
        int toY = i4;
        ViewHolder viewHolder2 = holder;
        int i5 = fromX;
        int i6 = fromY;
        int i7 = toX;
        int i8 = toY;
        View view = holder.itemView;
        int fromX2 = (int) (((float) fromX) + ViewCompat.getTranslationX(holder.itemView));
        int fromY2 = (int) (((float) fromY) + ViewCompat.getTranslationY(holder.itemView));
        resetAnimation(holder);
        int deltaX = toX - fromX2;
        int deltaY = toY - fromY2;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) {
            ViewCompat.setTranslationX(view, (float) (-deltaX));
        }
        if (deltaY != 0) {
            ViewCompat.setTranslationY(view, (float) (-deltaY));
        }
        ArrayList<MoveInfo> arrayList = this.mPendingMoves;
        MoveInfo moveInfo = new MoveInfo(holder, fromX2, fromY2, toX, toY);
        boolean add = arrayList.add(moveInfo);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void animateMoveImpl(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
        ViewHolder holder = viewHolder;
        int fromX = i;
        int fromY = i2;
        int toX = i3;
        int toY = i4;
        ViewHolder viewHolder2 = holder;
        int i5 = fromX;
        int i6 = fromY;
        int i7 = toX;
        int i8 = toY;
        View view = holder.itemView;
        View view2 = view;
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX != 0) {
            ViewPropertyAnimatorCompat translationX = ViewCompat.animate(view).translationX(0.0f);
        }
        if (deltaY != 0) {
            ViewPropertyAnimatorCompat translationY = ViewCompat.animate(view2).translationY(0.0f);
        }
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(view2);
        boolean add = this.mMoveAnimations.add(holder);
        ViewPropertyAnimatorCompat duration = animation.setDuration(getMoveDuration());
        final ViewHolder viewHolder3 = holder;
        final int i9 = deltaX;
        final int i10 = deltaY;
        final ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = animation;
        C03096 r0 = new VpaListenerAdapter(this) {
            final /* synthetic */ DefaultItemAnimator this$0;

            {
                DefaultItemAnimator this$02 = r9;
                ViewHolder viewHolder = r10;
                int i = r11;
                int i2 = r12;
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = r13;
                DefaultItemAnimator defaultItemAnimator = this$02;
                this.this$0 = this$02;
            }

            public void onAnimationStart(View view) {
                View view2 = view;
                this.this$0.dispatchMoveStarting(viewHolder3);
            }

            public void onAnimationCancel(View view) {
                View view2 = view;
                View view3 = view2;
                if (i9 != 0) {
                    ViewCompat.setTranslationX(view2, 0.0f);
                }
                if (i10 != 0) {
                    ViewCompat.setTranslationY(view2, 0.0f);
                }
            }

            public void onAnimationEnd(View view) {
                View view2 = view;
                ViewPropertyAnimatorCompat listener = viewPropertyAnimatorCompat.setListener(null);
                this.this$0.dispatchMoveFinished(viewHolder3);
                boolean remove = this.this$0.mMoveAnimations.remove(viewHolder3);
                this.this$0.dispatchFinishedWhenDone();
            }
        };
        duration.setListener(r0).start();
    }

    public boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
        ViewHolder oldHolder = viewHolder;
        ViewHolder newHolder = viewHolder2;
        int fromX = i;
        int fromY = i2;
        int toX = i3;
        int toY = i4;
        ViewHolder viewHolder3 = oldHolder;
        ViewHolder viewHolder4 = newHolder;
        int i5 = fromX;
        int i6 = fromY;
        int i7 = toX;
        int i8 = toY;
        if (oldHolder == newHolder) {
            return animateMove(oldHolder, fromX, fromY, toX, toY);
        }
        float prevTranslationX = ViewCompat.getTranslationX(oldHolder.itemView);
        float prevTranslationY = ViewCompat.getTranslationY(oldHolder.itemView);
        float prevAlpha = ViewCompat.getAlpha(oldHolder.itemView);
        resetAnimation(oldHolder);
        int deltaX = (int) (((float) (toX - fromX)) - prevTranslationX);
        int i9 = (int) (((float) (toY - fromY)) - prevTranslationY);
        int i10 = i9;
        ViewCompat.setTranslationX(oldHolder.itemView, prevTranslationX);
        ViewCompat.setTranslationY(oldHolder.itemView, prevTranslationY);
        ViewCompat.setAlpha(oldHolder.itemView, prevAlpha);
        if (newHolder != null) {
            resetAnimation(newHolder);
            ViewCompat.setTranslationX(newHolder.itemView, (float) (-deltaX));
            ViewCompat.setTranslationY(newHolder.itemView, (float) (-i9));
            ViewCompat.setAlpha(newHolder.itemView, 0.0f);
        }
        ArrayList<ChangeInfo> arrayList = this.mPendingChanges;
        ChangeInfo changeInfo = new ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY);
        boolean add = arrayList.add(changeInfo);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void animateChangeImpl(ChangeInfo changeInfo) {
        ChangeInfo changeInfo2 = changeInfo;
        ChangeInfo changeInfo3 = changeInfo2;
        ViewHolder viewHolder = changeInfo2.oldHolder;
        View view = viewHolder != null ? viewHolder.itemView : null;
        ViewHolder viewHolder2 = changeInfo2.newHolder;
        View newView = viewHolder2 == null ? null : viewHolder2.itemView;
        if (view != null) {
            ViewPropertyAnimatorCompat oldViewAnim = ViewCompat.animate(view).setDuration(getChangeDuration());
            boolean add = this.mChangeAnimations.add(changeInfo2.oldHolder);
            ViewPropertyAnimatorCompat translationX = oldViewAnim.translationX((float) (changeInfo2.toX - changeInfo2.fromX));
            ViewPropertyAnimatorCompat translationY = oldViewAnim.translationY((float) (changeInfo2.toY - changeInfo2.fromY));
            ViewPropertyAnimatorCompat alpha = oldViewAnim.alpha(0.0f);
            final ChangeInfo changeInfo4 = changeInfo2;
            final ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = oldViewAnim;
            C03107 r0 = new VpaListenerAdapter(this) {
                final /* synthetic */ DefaultItemAnimator this$0;

                {
                    DefaultItemAnimator this$02 = r7;
                    ChangeInfo changeInfo = r8;
                    ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = r9;
                    DefaultItemAnimator defaultItemAnimator = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationStart(View view) {
                    View view2 = view;
                    this.this$0.dispatchChangeStarting(changeInfo4.oldHolder, true);
                }

                public void onAnimationEnd(View view) {
                    View view2 = view;
                    View view3 = view2;
                    ViewPropertyAnimatorCompat listener = viewPropertyAnimatorCompat.setListener(null);
                    ViewCompat.setAlpha(view2, 1.0f);
                    ViewCompat.setTranslationX(view2, 0.0f);
                    ViewCompat.setTranslationY(view2, 0.0f);
                    this.this$0.dispatchChangeFinished(changeInfo4.oldHolder, true);
                    boolean remove = this.this$0.mChangeAnimations.remove(changeInfo4.oldHolder);
                    this.this$0.dispatchFinishedWhenDone();
                }
            };
            alpha.setListener(r0).start();
        }
        if (newView != null) {
            ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newView);
            boolean add2 = this.mChangeAnimations.add(changeInfo2.newHolder);
            ViewPropertyAnimatorCompat alpha2 = newViewAnimation.translationX(0.0f).translationY(0.0f).setDuration(getChangeDuration()).alpha(1.0f);
            final ChangeInfo changeInfo5 = changeInfo2;
            final ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = newViewAnimation;
            final View view2 = newView;
            C03118 r02 = new VpaListenerAdapter(this) {
                final /* synthetic */ DefaultItemAnimator this$0;

                {
                    DefaultItemAnimator this$02 = r8;
                    ChangeInfo changeInfo = r9;
                    ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = r10;
                    View view = r11;
                    DefaultItemAnimator defaultItemAnimator = this$02;
                    this.this$0 = this$02;
                }

                public void onAnimationStart(View view) {
                    View view2 = view;
                    this.this$0.dispatchChangeStarting(changeInfo5.newHolder, false);
                }

                public void onAnimationEnd(View view) {
                    View view2 = view;
                    ViewPropertyAnimatorCompat listener = viewPropertyAnimatorCompat2.setListener(null);
                    ViewCompat.setAlpha(view2, 1.0f);
                    ViewCompat.setTranslationX(view2, 0.0f);
                    ViewCompat.setTranslationY(view2, 0.0f);
                    this.this$0.dispatchChangeFinished(changeInfo5.newHolder, false);
                    boolean remove = this.this$0.mChangeAnimations.remove(changeInfo5.newHolder);
                    this.this$0.dispatchFinishedWhenDone();
                }
            };
            alpha2.setListener(r02).start();
        }
    }

    private void endChangeAnimation(List<ChangeInfo> list, ViewHolder viewHolder) {
        List<ChangeInfo> infoList = list;
        ViewHolder item = viewHolder;
        List<ChangeInfo> list2 = infoList;
        ViewHolder viewHolder2 = item;
        for (int i = infoList.size() - 1; i >= 0; i--) {
            ChangeInfo changeInfo = (ChangeInfo) infoList.get(i);
            if (endChangeAnimationIfNecessary(changeInfo, item) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                boolean remove = infoList.remove(changeInfo);
            }
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        ChangeInfo changeInfo2 = changeInfo;
        ChangeInfo changeInfo3 = changeInfo2;
        if (changeInfo2.oldHolder != null) {
            boolean endChangeAnimationIfNecessary = endChangeAnimationIfNecessary(changeInfo2, changeInfo2.oldHolder);
        }
        if (changeInfo2.newHolder != null) {
            boolean endChangeAnimationIfNecessary2 = endChangeAnimationIfNecessary(changeInfo2, changeInfo2.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, ViewHolder viewHolder) {
        ChangeInfo changeInfo2 = changeInfo;
        ViewHolder item = viewHolder;
        ChangeInfo changeInfo3 = changeInfo2;
        ViewHolder viewHolder2 = item;
        boolean oldItem = false;
        if (changeInfo2.newHolder == item) {
            changeInfo2.newHolder = null;
        } else if (changeInfo2.oldHolder != item) {
            return false;
        } else {
            changeInfo2.oldHolder = null;
            oldItem = true;
        }
        ViewCompat.setAlpha(item.itemView, 1.0f);
        ViewCompat.setTranslationX(item.itemView, 0.0f);
        ViewCompat.setTranslationY(item.itemView, 0.0f);
        dispatchChangeFinished(item, oldItem);
        return true;
    }

    public void endAnimation(ViewHolder viewHolder) {
        ViewHolder item = viewHolder;
        ViewHolder viewHolder2 = item;
        View view = item.itemView;
        View view2 = view;
        ViewCompat.animate(view).cancel();
        for (int i = this.mPendingMoves.size() - 1; i >= 0; i--) {
            MoveInfo moveInfo = (MoveInfo) this.mPendingMoves.get(i);
            MoveInfo moveInfo2 = moveInfo;
            if (moveInfo.holder == item) {
                ViewCompat.setTranslationY(view2, 0.0f);
                ViewCompat.setTranslationX(view2, 0.0f);
                dispatchMoveFinished(item);
                Object remove = this.mPendingMoves.remove(i);
            }
        }
        endChangeAnimation(this.mPendingChanges, item);
        if (this.mPendingRemovals.remove(item)) {
            ViewCompat.setAlpha(view2, 1.0f);
            dispatchRemoveFinished(item);
        }
        if (this.mPendingAdditions.remove(item)) {
            ViewCompat.setAlpha(view2, 1.0f);
            dispatchAddFinished(item);
        }
        for (int i2 = this.mChangesList.size() - 1; i2 >= 0; i2--) {
            ArrayList arrayList = (ArrayList) this.mChangesList.get(i2);
            endChangeAnimation(arrayList, item);
            if (arrayList.isEmpty()) {
                Object remove2 = this.mChangesList.remove(i2);
            }
        }
        for (int i3 = this.mMovesList.size() - 1; i3 >= 0; i3--) {
            ArrayList arrayList2 = (ArrayList) this.mMovesList.get(i3);
            ArrayList arrayList3 = arrayList2;
            int j = arrayList2.size() - 1;
            while (true) {
                if (j < 0) {
                    break;
                }
                MoveInfo moveInfo3 = (MoveInfo) arrayList3.get(j);
                MoveInfo moveInfo4 = moveInfo3;
                if (moveInfo3.holder != item) {
                    j--;
                } else {
                    ViewCompat.setTranslationY(view2, 0.0f);
                    ViewCompat.setTranslationX(view2, 0.0f);
                    dispatchMoveFinished(item);
                    Object remove3 = arrayList3.remove(j);
                    if (arrayList3.isEmpty()) {
                        Object remove4 = this.mMovesList.remove(i3);
                    }
                }
            }
        }
        for (int i4 = this.mAdditionsList.size() - 1; i4 >= 0; i4--) {
            ArrayList arrayList4 = (ArrayList) this.mAdditionsList.get(i4);
            ArrayList arrayList5 = arrayList4;
            if (arrayList4.remove(item)) {
                ViewCompat.setAlpha(view2, 1.0f);
                dispatchAddFinished(item);
                if (arrayList5.isEmpty()) {
                    Object remove5 = this.mAdditionsList.remove(i4);
                }
            }
        }
        if (!this.mRemoveAnimations.remove(item)) {
        }
        if (!this.mAddAnimations.remove(item)) {
        }
        if (!this.mChangeAnimations.remove(item)) {
        }
        if (!this.mMoveAnimations.remove(item)) {
        }
        dispatchFinishedWhenDone();
    }

    private void resetAnimation(ViewHolder viewHolder) {
        ViewHolder holder = viewHolder;
        ViewHolder viewHolder2 = holder;
        AnimatorCompatHelper.clearInterpolator(holder.itemView);
        endAnimation(holder);
    }

    public boolean isRunning() {
        return !this.mPendingAdditions.isEmpty() || !this.mPendingChanges.isEmpty() || !this.mPendingMoves.isEmpty() || !this.mPendingRemovals.isEmpty() || !this.mMoveAnimations.isEmpty() || !this.mRemoveAnimations.isEmpty() || !this.mAddAnimations.isEmpty() || !this.mChangeAnimations.isEmpty() || !this.mMovesList.isEmpty() || !this.mAdditionsList.isEmpty() || !this.mChangesList.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public void endAnimations() {
        int size = this.mPendingMoves.size();
        int i = size;
        for (int i2 = size - 1; i2 >= 0; i2--) {
            MoveInfo moveInfo = (MoveInfo) this.mPendingMoves.get(i2);
            MoveInfo item = moveInfo;
            View view = moveInfo.holder.itemView;
            View view2 = view;
            ViewCompat.setTranslationY(view, 0.0f);
            ViewCompat.setTranslationX(view2, 0.0f);
            dispatchMoveFinished(item.holder);
            Object remove = this.mPendingMoves.remove(i2);
        }
        int size2 = this.mPendingRemovals.size();
        int count = size2;
        for (int i3 = size2 - 1; i3 >= 0; i3--) {
            dispatchRemoveFinished((ViewHolder) this.mPendingRemovals.get(i3));
            Object remove2 = this.mPendingRemovals.remove(i3);
        }
        int size3 = this.mPendingAdditions.size();
        int count2 = size3;
        for (int i4 = size3 - 1; i4 >= 0; i4--) {
            ViewHolder viewHolder = (ViewHolder) this.mPendingAdditions.get(i4);
            ViewHolder item2 = viewHolder;
            View view3 = viewHolder.itemView;
            View view4 = view3;
            ViewCompat.setAlpha(view3, 1.0f);
            dispatchAddFinished(item2);
            Object remove3 = this.mPendingAdditions.remove(i4);
        }
        int size4 = this.mPendingChanges.size();
        int count3 = size4;
        for (int i5 = size4 - 1; i5 >= 0; i5--) {
            endChangeAnimationIfNecessary((ChangeInfo) this.mPendingChanges.get(i5));
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            int size5 = this.mMovesList.size();
            int i6 = size5;
            for (int i7 = size5 - 1; i7 >= 0; i7--) {
                ArrayList arrayList = (ArrayList) this.mMovesList.get(i7);
                ArrayList arrayList2 = arrayList;
                int size6 = arrayList.size();
                int count4 = size6;
                for (int j = size6 - 1; j >= 0; j--) {
                    MoveInfo moveInfo2 = (MoveInfo) arrayList2.get(j);
                    MoveInfo moveInfo3 = moveInfo2;
                    ViewHolder viewHolder2 = moveInfo2.holder;
                    ViewHolder viewHolder3 = viewHolder2;
                    View view5 = viewHolder2.itemView;
                    View view6 = view5;
                    ViewCompat.setTranslationY(view5, 0.0f);
                    ViewCompat.setTranslationX(view6, 0.0f);
                    dispatchMoveFinished(moveInfo3.holder);
                    Object remove4 = arrayList2.remove(j);
                    if (arrayList2.isEmpty()) {
                        boolean remove5 = this.mMovesList.remove(arrayList2);
                    }
                }
            }
            int size7 = this.mAdditionsList.size();
            int listCount = size7;
            for (int i8 = size7 - 1; i8 >= 0; i8--) {
                ArrayList arrayList3 = (ArrayList) this.mAdditionsList.get(i8);
                ArrayList arrayList4 = arrayList3;
                int size8 = arrayList3.size();
                int count5 = size8;
                for (int j2 = size8 - 1; j2 >= 0; j2--) {
                    ViewHolder viewHolder4 = (ViewHolder) arrayList4.get(j2);
                    ViewHolder item3 = viewHolder4;
                    View view7 = viewHolder4.itemView;
                    View view8 = view7;
                    ViewCompat.setAlpha(view7, 1.0f);
                    dispatchAddFinished(item3);
                    Object remove6 = arrayList4.remove(j2);
                    if (arrayList4.isEmpty()) {
                        boolean remove7 = this.mAdditionsList.remove(arrayList4);
                    }
                }
            }
            int size9 = this.mChangesList.size();
            int listCount2 = size9;
            for (int i9 = size9 - 1; i9 >= 0; i9--) {
                ArrayList arrayList5 = (ArrayList) this.mChangesList.get(i9);
                ArrayList arrayList6 = arrayList5;
                int size10 = arrayList5.size();
                int count6 = size10;
                for (int j3 = size10 - 1; j3 >= 0; j3--) {
                    endChangeAnimationIfNecessary((ChangeInfo) arrayList6.get(j3));
                    if (arrayList6.isEmpty()) {
                        boolean remove8 = this.mChangesList.remove(arrayList6);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    /* access modifiers changed from: 0000 */
    public void cancelAll(List<ViewHolder> list) {
        List<ViewHolder> viewHolders = list;
        List<ViewHolder> list2 = viewHolders;
        for (int i = viewHolders.size() - 1; i >= 0; i--) {
            ViewCompat.animate(((ViewHolder) viewHolders.get(i)).itemView).cancel();
        }
    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
        ViewHolder viewHolder2 = viewHolder;
        List<Object> payloads = list;
        ViewHolder viewHolder3 = viewHolder2;
        List<Object> list2 = payloads;
        return !payloads.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder2, payloads);
    }
}
