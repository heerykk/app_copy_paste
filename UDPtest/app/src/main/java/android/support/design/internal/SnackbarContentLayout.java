package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.C0001R;
import android.support.design.widget.BaseTransientBottomBar.ContentViewCallback;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class SnackbarContentLayout extends LinearLayout implements ContentViewCallback {
    private Button mActionView;
    private int mMaxInlineActionWidth;
    private int mMaxWidth;
    private TextView mMessageView;

    public SnackbarContentLayout(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        TypedArray a = context2.obtainStyledAttributes(attrs, C0001R.styleable.SnackbarLayout);
        this.mMaxWidth = a.getDimensionPixelSize(C0001R.styleable.SnackbarLayout_android_maxWidth, -1);
        this.mMaxInlineActionWidth = a.getDimensionPixelSize(C0001R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mMessageView = (TextView) findViewById(C0001R.C0003id.snackbar_text);
        this.mActionView = (Button) findViewById(C0001R.C0003id.snackbar_action);
    }

    public TextView getMessageView() {
        return this.mMessageView;
    }

    public Button getActionView() {
        return this.mActionView;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int widthMeasureSpec2 = widthMeasureSpec;
        int i3 = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mMaxWidth > 0 && getMeasuredWidth() > this.mMaxWidth) {
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824);
            widthMeasureSpec2 = makeMeasureSpec;
            super.onMeasure(makeMeasureSpec, heightMeasureSpec);
        }
        int multiLineVPadding = getResources().getDimensionPixelSize(C0001R.dimen.design_snackbar_padding_vertical_2lines);
        int singleLineVPadding = getResources().getDimensionPixelSize(C0001R.dimen.design_snackbar_padding_vertical);
        boolean isMultiLine = this.mMessageView.getLayout().getLineCount() > 1;
        boolean remeasure = false;
        if (!isMultiLine || this.mMaxInlineActionWidth <= 0 || this.mActionView.getMeasuredWidth() <= this.mMaxInlineActionWidth) {
            int messagePadding = !isMultiLine ? singleLineVPadding : multiLineVPadding;
            if (updateViewsWithinLayout(0, messagePadding, messagePadding)) {
                remeasure = true;
            }
        } else if (updateViewsWithinLayout(1, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
            remeasure = true;
        }
        if (remeasure) {
            super.onMeasure(widthMeasureSpec2, heightMeasureSpec);
        }
    }

    private boolean updateViewsWithinLayout(int i, int i2, int i3) {
        int orientation = i;
        int messagePadTop = i2;
        int messagePadBottom = i3;
        int i4 = orientation;
        int i5 = messagePadTop;
        int i6 = messagePadBottom;
        boolean changed = false;
        if (orientation != getOrientation()) {
            setOrientation(orientation);
            changed = true;
        }
        if (!(this.mMessageView.getPaddingTop() == messagePadTop && this.mMessageView.getPaddingBottom() == messagePadBottom)) {
            updateTopBottomPadding(this.mMessageView, messagePadTop, messagePadBottom);
            changed = true;
        }
        return changed;
    }

    private static void updateTopBottomPadding(View view, int i, int i2) {
        View view2 = view;
        int topPadding = i;
        int bottomPadding = i2;
        View view3 = view2;
        int i3 = topPadding;
        int i4 = bottomPadding;
        if (!ViewCompat.isPaddingRelative(view2)) {
            view2.setPadding(view2.getPaddingLeft(), topPadding, view2.getPaddingRight(), bottomPadding);
            return;
        }
        ViewCompat.setPaddingRelative(view2, ViewCompat.getPaddingStart(view2), topPadding, ViewCompat.getPaddingEnd(view2), bottomPadding);
    }

    public void animateContentIn(int i, int i2) {
        int delay = i;
        int duration = i2;
        int i3 = delay;
        int i4 = duration;
        ViewCompat.setAlpha(this.mMessageView, 0.0f);
        ViewCompat.animate(this.mMessageView).alpha(1.0f).setDuration((long) duration).setStartDelay((long) delay).start();
        if (this.mActionView.getVisibility() == 0) {
            ViewCompat.setAlpha(this.mActionView, 0.0f);
            ViewCompat.animate(this.mActionView).alpha(1.0f).setDuration((long) duration).setStartDelay((long) delay).start();
        }
    }

    public void animateContentOut(int i, int i2) {
        int delay = i;
        int duration = i2;
        int i3 = delay;
        int i4 = duration;
        ViewCompat.setAlpha(this.mMessageView, 1.0f);
        ViewCompat.animate(this.mMessageView).alpha(0.0f).setDuration((long) duration).setStartDelay((long) delay).start();
        if (this.mActionView.getVisibility() == 0) {
            ViewCompat.setAlpha(this.mActionView, 1.0f);
            ViewCompat.animate(this.mActionView).alpha(0.0f).setDuration((long) duration).setStartDelay((long) delay).start();
        }
    }
}
