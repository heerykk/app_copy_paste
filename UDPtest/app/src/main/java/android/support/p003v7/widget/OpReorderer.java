package android.support.p003v7.widget;

import java.util.List;

/* renamed from: android.support.v7.widget.OpReorderer */
class OpReorderer {
    final Callback mCallback;

    /* renamed from: android.support.v7.widget.OpReorderer$Callback */
    interface Callback {
        UpdateOp obtainUpdateOp(int i, int i2, int i3, Object obj);

        void recycleUpdateOp(UpdateOp updateOp);
    }

    public OpReorderer(Callback callback) {
        Callback callback2 = callback;
        Callback callback3 = callback2;
        this.mCallback = callback2;
    }

    /* access modifiers changed from: 0000 */
    public void reorderOps(List<UpdateOp> list) {
        List<UpdateOp> ops = list;
        List<UpdateOp> list2 = ops;
        while (true) {
            int lastMoveOutOfOrder = getLastMoveOutOfOrder(ops);
            int i = lastMoveOutOfOrder;
            if (lastMoveOutOfOrder != -1) {
                swapMoveOp(ops, lastMoveOutOfOrder, lastMoveOutOfOrder + 1);
            } else {
                return;
            }
        }
    }

    private void swapMoveOp(List<UpdateOp> list, int i, int i2) {
        List<UpdateOp> list2 = list;
        int badMove = i;
        int next = i2;
        List<UpdateOp> list3 = list2;
        int i3 = badMove;
        int i4 = next;
        UpdateOp moveOp = (UpdateOp) list2.get(badMove);
        UpdateOp updateOp = (UpdateOp) list2.get(next);
        UpdateOp nextOp = updateOp;
        switch (updateOp.cmd) {
            case 1:
                swapMoveAdd(list2, badMove, moveOp, next, nextOp);
                return;
            case 2:
                swapMoveRemove(list2, badMove, moveOp, next, nextOp);
                return;
            case 4:
                swapMoveUpdate(list2, badMove, moveOp, next, nextOp);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: 0000 */
    public void swapMoveRemove(List<UpdateOp> list, int i, UpdateOp updateOp, int i2, UpdateOp updateOp2) {
        boolean moveIsBackwards;
        List<UpdateOp> list2 = list;
        int movePos = i;
        UpdateOp moveOp = updateOp;
        int removePos = i2;
        UpdateOp removeOp = updateOp2;
        List<UpdateOp> list3 = list2;
        int i3 = movePos;
        UpdateOp updateOp3 = moveOp;
        int i4 = removePos;
        UpdateOp updateOp4 = removeOp;
        UpdateOp extraRm = null;
        boolean revertedMove = false;
        if (moveOp.positionStart >= moveOp.itemCount) {
            moveIsBackwards = true;
            if (removeOp.positionStart == moveOp.itemCount + 1 && removeOp.itemCount == moveOp.positionStart - moveOp.itemCount) {
                revertedMove = true;
            }
        } else {
            moveIsBackwards = false;
            if (removeOp.positionStart == moveOp.positionStart && removeOp.itemCount == moveOp.itemCount - moveOp.positionStart) {
                revertedMove = true;
            }
        }
        if (moveOp.itemCount >= removeOp.positionStart) {
            if (moveOp.itemCount < removeOp.positionStart + removeOp.itemCount) {
                removeOp.itemCount--;
                moveOp.cmd = 2;
                moveOp.itemCount = 1;
                if (removeOp.itemCount == 0) {
                    Object remove = list2.remove(removePos);
                    this.mCallback.recycleUpdateOp(removeOp);
                }
                return;
            }
        } else {
            removeOp.positionStart--;
        }
        if (moveOp.positionStart > removeOp.positionStart) {
            if (moveOp.positionStart < removeOp.positionStart + removeOp.itemCount) {
                int i5 = (removeOp.positionStart + removeOp.itemCount) - moveOp.positionStart;
                int i6 = i5;
                extraRm = this.mCallback.obtainUpdateOp(2, moveOp.positionStart + 1, i5, null);
                removeOp.itemCount = moveOp.positionStart - removeOp.positionStart;
            }
        } else {
            removeOp.positionStart++;
        }
        if (!revertedMove) {
            if (!moveIsBackwards) {
                if (extraRm != null) {
                    if (moveOp.positionStart >= extraRm.positionStart) {
                        moveOp.positionStart -= extraRm.itemCount;
                    }
                    if (moveOp.itemCount >= extraRm.positionStart) {
                        moveOp.itemCount -= extraRm.itemCount;
                    }
                }
                if (moveOp.positionStart >= removeOp.positionStart) {
                    moveOp.positionStart -= removeOp.itemCount;
                }
                if (moveOp.itemCount >= removeOp.positionStart) {
                    moveOp.itemCount -= removeOp.itemCount;
                }
            } else {
                if (extraRm != null) {
                    if (moveOp.positionStart > extraRm.positionStart) {
                        moveOp.positionStart -= extraRm.itemCount;
                    }
                    if (moveOp.itemCount > extraRm.positionStart) {
                        moveOp.itemCount -= extraRm.itemCount;
                    }
                }
                if (moveOp.positionStart > removeOp.positionStart) {
                    moveOp.positionStart -= removeOp.itemCount;
                }
                if (moveOp.itemCount > removeOp.positionStart) {
                    moveOp.itemCount -= removeOp.itemCount;
                }
            }
            Object obj = list2.set(movePos, removeOp);
            if (moveOp.positionStart == moveOp.itemCount) {
                Object remove2 = list2.remove(removePos);
            } else {
                Object obj2 = list2.set(removePos, moveOp);
            }
            if (extraRm != null) {
                list2.add(movePos, extraRm);
            }
            return;
        }
        Object obj3 = list2.set(movePos, removeOp);
        Object remove3 = list2.remove(removePos);
        this.mCallback.recycleUpdateOp(moveOp);
    }

    private void swapMoveAdd(List<UpdateOp> list, int i, UpdateOp updateOp, int i2, UpdateOp updateOp2) {
        List<UpdateOp> list2 = list;
        int move = i;
        UpdateOp moveOp = updateOp;
        int add = i2;
        UpdateOp addOp = updateOp2;
        List<UpdateOp> list3 = list2;
        int i3 = move;
        UpdateOp updateOp3 = moveOp;
        int i4 = add;
        UpdateOp updateOp4 = addOp;
        int offset = 0;
        if (moveOp.itemCount < addOp.positionStart) {
            offset = -1;
        }
        if (moveOp.positionStart < addOp.positionStart) {
            offset++;
        }
        if (addOp.positionStart <= moveOp.positionStart) {
            moveOp.positionStart += addOp.itemCount;
        }
        if (addOp.positionStart <= moveOp.itemCount) {
            moveOp.itemCount += addOp.itemCount;
        }
        addOp.positionStart += offset;
        Object obj = list2.set(move, addOp);
        Object obj2 = list2.set(add, moveOp);
    }

    /* access modifiers changed from: 0000 */
    public void swapMoveUpdate(List<UpdateOp> list, int i, UpdateOp updateOp, int i2, UpdateOp updateOp2) {
        List<UpdateOp> list2 = list;
        int move = i;
        UpdateOp moveOp = updateOp;
        int update = i2;
        UpdateOp updateOp3 = updateOp2;
        List<UpdateOp> list3 = list2;
        int i3 = move;
        UpdateOp updateOp4 = moveOp;
        int i4 = update;
        UpdateOp updateOp5 = updateOp3;
        UpdateOp extraUp1 = null;
        UpdateOp extraUp2 = null;
        if (moveOp.itemCount >= updateOp3.positionStart) {
            if (moveOp.itemCount < updateOp3.positionStart + updateOp3.itemCount) {
                updateOp3.itemCount--;
                extraUp1 = this.mCallback.obtainUpdateOp(4, moveOp.positionStart, 1, updateOp3.payload);
            }
        } else {
            updateOp3.positionStart--;
        }
        if (moveOp.positionStart > updateOp3.positionStart) {
            if (moveOp.positionStart < updateOp3.positionStart + updateOp3.itemCount) {
                int i5 = (updateOp3.positionStart + updateOp3.itemCount) - moveOp.positionStart;
                int i6 = i5;
                extraUp2 = this.mCallback.obtainUpdateOp(4, moveOp.positionStart + 1, i5, updateOp3.payload);
                updateOp3.itemCount -= i5;
            }
        } else {
            updateOp3.positionStart++;
        }
        Object obj = list2.set(update, moveOp);
        if (updateOp3.itemCount <= 0) {
            Object remove = list2.remove(move);
            this.mCallback.recycleUpdateOp(updateOp3);
        } else {
            Object obj2 = list2.set(move, updateOp3);
        }
        if (extraUp1 != null) {
            list2.add(move, extraUp1);
        }
        if (extraUp2 != null) {
            list2.add(move, extraUp2);
        }
    }

    private int getLastMoveOutOfOrder(List<UpdateOp> list) {
        List<UpdateOp> list2 = list;
        List<UpdateOp> list3 = list2;
        boolean foundNonMove = false;
        for (int i = list2.size() - 1; i >= 0; i--) {
            UpdateOp updateOp = (UpdateOp) list2.get(i);
            UpdateOp updateOp2 = updateOp;
            if (updateOp.cmd != 8) {
                foundNonMove = true;
            } else if (foundNonMove) {
                return i;
            }
        }
        return -1;
    }
}
