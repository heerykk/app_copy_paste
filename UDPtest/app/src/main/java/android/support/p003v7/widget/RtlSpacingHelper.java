package android.support.p003v7.widget;

/* renamed from: android.support.v7.widget.RtlSpacingHelper */
class RtlSpacingHelper {
    public static final int UNDEFINED = Integer.MIN_VALUE;
    private int mEnd = Integer.MIN_VALUE;
    private int mExplicitLeft = 0;
    private int mExplicitRight = 0;
    private boolean mIsRelative = false;
    private boolean mIsRtl = false;
    private int mLeft = 0;
    private int mRight = 0;
    private int mStart = Integer.MIN_VALUE;

    RtlSpacingHelper() {
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getStart() {
        return !this.mIsRtl ? this.mLeft : this.mRight;
    }

    public int getEnd() {
        return !this.mIsRtl ? this.mRight : this.mLeft;
    }

    public void setRelative(int i, int i2) {
        int start = i;
        int end = i2;
        int i3 = start;
        int i4 = end;
        this.mStart = start;
        this.mEnd = end;
        this.mIsRelative = true;
        if (!this.mIsRtl) {
            if (start != Integer.MIN_VALUE) {
                this.mLeft = start;
            }
            if (end != Integer.MIN_VALUE) {
                this.mRight = end;
                return;
            }
            return;
        }
        if (end != Integer.MIN_VALUE) {
            this.mLeft = end;
        }
        if (start != Integer.MIN_VALUE) {
            this.mRight = start;
        }
    }

    public void setAbsolute(int i, int i2) {
        int left = i;
        int right = i2;
        int i3 = left;
        int i4 = right;
        this.mIsRelative = false;
        if (left != Integer.MIN_VALUE) {
            this.mExplicitLeft = left;
            this.mLeft = left;
        }
        if (right != Integer.MIN_VALUE) {
            this.mExplicitRight = right;
            this.mRight = right;
        }
    }

    public void setDirection(boolean z) {
        boolean isRtl = z;
        if (isRtl != this.mIsRtl) {
            this.mIsRtl = isRtl;
            if (!this.mIsRelative) {
                this.mLeft = this.mExplicitLeft;
                this.mRight = this.mExplicitRight;
            } else if (!isRtl) {
                this.mLeft = this.mStart == Integer.MIN_VALUE ? this.mExplicitLeft : this.mStart;
                this.mRight = this.mEnd == Integer.MIN_VALUE ? this.mExplicitRight : this.mEnd;
            } else {
                this.mLeft = this.mEnd == Integer.MIN_VALUE ? this.mExplicitLeft : this.mEnd;
                this.mRight = this.mStart == Integer.MIN_VALUE ? this.mExplicitRight : this.mStart;
            }
        }
    }
}
