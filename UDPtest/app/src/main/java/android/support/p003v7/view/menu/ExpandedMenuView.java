package android.support.p003v7.view.menu;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p003v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.p003v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.menu.ExpandedMenuView */
public final class ExpandedMenuView extends ListView implements ItemInvoker, MenuView, OnItemClickListener {
    private static final int[] TINT_ATTRS;
    private int mAnimations;
    private MenuBuilder mMenu;

    static {
        int[] iArr = new int[2];
        iArr[0] = 16842964;
        iArr[1] = 16843049;
        TINT_ATTRS = iArr;
    }

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        this(context2, attrs, 16842868);
    }

    public ExpandedMenuView(Context context, AttributeSet attributeSet, int i) {
        Context context2 = context;
        AttributeSet attrs = attributeSet;
        int defStyleAttr = i;
        Context context3 = context2;
        AttributeSet attributeSet2 = attrs;
        int i2 = defStyleAttr;
        super(context2, attrs);
        setOnItemClickListener(this);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, attrs, TINT_ATTRS, defStyleAttr, 0);
        TintTypedArray a = obtainStyledAttributes;
        if (obtainStyledAttributes.hasValue(0)) {
            setBackgroundDrawable(a.getDrawable(0));
        }
        if (a.hasValue(1)) {
            setDivider(a.getDrawable(1));
        }
        a.recycle();
    }

    public void initialize(MenuBuilder menuBuilder) {
        MenuBuilder menu = menuBuilder;
        MenuBuilder menuBuilder2 = menu;
        this.mMenu = menu;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        MenuItemImpl item = menuItemImpl;
        MenuItemImpl menuItemImpl2 = item;
        return this.mMenu.performItemAction(item, 0);
    }

    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        int position = i;
        AdapterView adapterView2 = adapterView;
        View view2 = view;
        int i2 = position;
        long j2 = j;
        boolean invokeItem = invokeItem((MenuItemImpl) getAdapter().getItem(position));
    }

    public int getWindowAnimations() {
        return this.mAnimations;
    }
}
