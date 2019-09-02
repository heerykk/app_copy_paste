package android.support.design.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.C0001R;
import android.support.p003v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;

public final class TabItem extends View {
    final int mCustomLayout;
    final Drawable mIcon;
    final CharSequence mText;

    public TabItem(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this(context2, null);
    }

    public TabItem(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        super(context2, attrs);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context2, attrs, C0001R.styleable.TabItem);
        this.mText = a.getText(C0001R.styleable.TabItem_android_text);
        this.mIcon = a.getDrawable(C0001R.styleable.TabItem_android_icon);
        this.mCustomLayout = a.getResourceId(C0001R.styleable.TabItem_android_layout, 0);
        a.recycle();
    }
}
