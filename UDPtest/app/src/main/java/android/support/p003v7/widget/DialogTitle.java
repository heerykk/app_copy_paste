package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.appcompat.C0268R;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.DialogTitle */
public class DialogTitle extends TextView {
    public DialogTitle(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs, defStyleAttr);
    }

    public DialogTitle(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
    }

    public DialogTitle(Context context) {
        Context context2 = context;
        Context context3 = context2;
        super(context2);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        int i3 = widthMeasureSpec;
        int i4 = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        Layout layout2 = layout;
        if (layout != null) {
            int lineCount = layout2.getLineCount();
            int lineCount2 = lineCount;
            if (lineCount > 0) {
                int ellipsisCount = layout2.getEllipsisCount(lineCount2 - 1);
                int i5 = ellipsisCount;
                if (ellipsisCount > 0) {
                    setSingleLine(false);
                    setMaxLines(2);
                    TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, C0268R.styleable.TextAppearance, 16842817, 16973892);
                    TypedArray a = obtainStyledAttributes;
                    int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C0268R.styleable.TextAppearance_android_textSize, 0);
                    int textSize = dimensionPixelSize;
                    if (dimensionPixelSize != 0) {
                        setTextSize(0, (float) textSize);
                    }
                    a.recycle();
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }
    }
}
